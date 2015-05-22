/*
 * Copyright 2015 TieFaces.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.tiefaces.components.websheet;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.tiefaces.common.FacesUtility;
import com.tiefaces.common.TIEConstants;
import com.tiefaces.components.websheet.TieWebSheetView.tabModel;
import com.tiefaces.components.websheet.dataobjects.CellFormAttributes;
import com.tiefaces.components.websheet.dataobjects.FacesCell;
import com.tiefaces.components.websheet.dataobjects.HeaderCell;
import com.tiefaces.components.websheet.dataobjects.SheetConfiguration;

public class TieWebSheetLoader implements Serializable {

	private TieWebSheetBean parent = null;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean debug = true;

	private void debug(String msg) {
		if (debug) {
			System.out.println("DEBUG: " + msg);
		}
	}

	public TieWebSheetLoader(TieWebSheetBean parent) {
		this.parent = parent;
		debug("TieWebSheetBean Constructor");
	}

	private void loadHeaderRows(String tabName,
			Map<String, CellRangeAddress> cellRangeMap,
			List<String> skippedRegionCells) {

		SheetConfiguration sheetConfig = parent.getSheetConfigMap()
				.get(tabName);

		int top = sheetConfig.getHeaderCellRange().getTopRow();
		int bottom = sheetConfig.getHeaderCellRange().getBottomRow();
		int left = sheetConfig.getHeaderCellRange().getLeftCol();
		int right = sheetConfig.getHeaderCellRange().getRightCol();

		String sheetName = sheetConfig.getSheetName();
		Sheet sheet1 = parent.getWb().getSheet(sheetName);

		double totalWidth = parent.getCellHelper().calcTotalWidth(sheet1, left,
				right);
debug("totalwidth = "+totalWidth);		
		parent.setTableWidthStyle(parent.getCellHelper().widthUnits2Pixel(
				totalWidth)
				+ "px;");
debug("change to pixel = "+parent.getTableWidthStyle());
		CellRangeAddress caddress = null;
		parent.setHeaderRows(new ArrayList<List<HeaderCell>>());
		for (int i = top; i <= bottom; i++) {
			Row row = sheet1.getRow(i);
			List<HeaderCell> headercells = new ArrayList<HeaderCell>();
			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				// String columnstyle =
				// parent.getCellHelper().getCellStyle(wb,cell) +
				// parent.getCellHelper().getCellFontStyle(wb,cell) ; // +
				// "background-image: none ;color: #000000;";
				int cindex = cell.getColumnIndex();
				if ((cindex >= left) && (cindex <= right)) {
					String cellindex = "$" + cell.getColumnIndex() + "$"
							+ cell.getRowIndex();
					if (!skippedRegionCells.contains(cellindex)) {
						caddress = cellRangeMap.get(cellindex);
						// check whether the cell has rowspan or colspan
						if (caddress != null) {
							// has rowspan or colspan
							double rangeWidth = parent.getCellHelper()
									.calcTotalWidth(sheet1,
											caddress.getFirstColumn(),
											caddress.getLastColumn());
							headercells.add(new HeaderCell((caddress
									.getLastRow() - caddress.getFirstRow() + 1)
									+ "", (caddress.getLastColumn()
									- caddress.getFirstColumn() + 1)
									+ "", getHeaderColumnStyle(parent.getWb(),
									cell, rangeWidth, totalWidth), parent
									.getCellHelper().getCellValue(cell)));
						} else {
							// no rowspan or colspan
							String cellValue = parent.getCellHelper()
									.getCellValue(cell);
							headercells.add(new HeaderCell("1", "1",
									getHeaderColumnStyle(parent.getWb(), cell,
											sheet1.getColumnWidth(cell
													.getColumnIndex()),
											totalWidth), cellValue));

						}
					}
				}
			}
			parent.getHeaderRows().add(headercells);
			
		}

	}

	private String getHeaderColumnStyle(Workbook wb, Cell cell,
			double colWidth, double totalWidth) {

		String columnstyle = parent.getCellHelper().getCellStyle(wb, cell)
				+ parent.getCellHelper().getCellFontStyle(wb, cell); // +
		// "background-image: none ;color: #000000;";
		double percentage = FacesUtility.round(100 * colWidth / totalWidth, 2); 
debug(" percentage = "+percentage);
			columnstyle = columnstyle + " width : "
					+ percentage + "%;";
debug(" columnstyle = "+columnstyle);
		return columnstyle;
	}

	// return 0 -- No template
	// return -1 -- error in open form
	// return 1 -- success
	public int loadWorkbook() {

		parent.setWb(null);
		try {

			InputStream fis = parent.loadWebSheetTemplate();
			parent.setWb(WorkbookFactory.create(fis));
			if (parent.getWb() instanceof XSSFWorkbook) {
				parent.setExcelType(TIEConstants.EXCEL_2007_TYPE);
			} else if (parent.getWb() instanceof HSSFWorkbook) {
				parent.setExcelType(TIEConstants.EXCEL_2003_TYPE);
			}
			debug(" load excel type = " + parent.getExcelType());
			parent.setFormulaEvaluator(parent.getWb().getCreationHelper()
					.createFormulaEvaluator());
			parent.setDataFormatter(new DataFormatter());
			parent.setSheetConfigMap(new TieWebSheetConfigurationHandler(parent)
					.buildConfiguration());
			parent.loadData();
			initSheet();
			initTabs();
			if (parent.getTabs().size() > 0) {
				loadWorkSheet(parent.getTabs().get(0).getTitle());
			}
			fis.close();
			// remove configuration sheet
			parent.getWb().removeSheetAt(
					parent.getWb().getSheetIndex(
							TIEConstants.TIE_WEBSHEET_CONFIGURATION_SHEET));
		} catch (Exception e) {
			e.printStackTrace();
			debug("Web Form loadWorkbook Error Exception = "
					+ e.getLocalizedMessage());
			return -1;
		}
		return 1;

	}

	private void initTabs() {
		parent.setTabs(new ArrayList<tabModel>());
		if (parent.getSheetConfigMap() != null) {
			for (String key : parent.getSheetConfigMap().keySet()) {
				parent.getTabs().add(new tabModel("form_" + key, key, "form"));
			}
		}
	}

	private void initSheet() {
		for (SheetConfiguration sheetConfig : parent.getSheetConfigMap()
				.values()) {
			initPageData(sheetConfig, parent.getWb(),
					parent.getFormulaEvaluator());
		}
		parent.getCellHelper().reCalc();
	}

	private void initPageData(SheetConfiguration sheetConfig, Workbook wb,
			FormulaEvaluator formulaEvaluator) {

		boolean bodyPopulated = sheetConfig.isBodyPopulated();

		if (bodyPopulated)
			return; // do nothing if already populated.

		int initRows = parent.getCellHelper()
				.getInitRowsFromConfig(sheetConfig);
		int top = sheetConfig.getBodyCellRange().getTopRow();

		String sheetName = sheetConfig.getSheetName();
		Sheet sheet1 = wb.getSheet(sheetName);

		for (int i = top; i < (top + initRows); i++) {
			if ((!bodyPopulated) && (i > top))
				parent.getCellHelper().copyRow(sheet1, top, i);
			initExcelRow(i, top, sheet1, sheetConfig);
		}
		if (initRows > 0)
			sheetConfig.setBodyPopulated(true);
	}

	private void initExcelRow(int row, int topRow, Sheet sheet,
			SheetConfiguration sheetConfig) {

		for (Map.Entry<String, List<CellFormAttributes>> entry : sheetConfig
				.getCellFormAttributes().entrySet()) {
			String targetCell = entry.getKey();
			List<CellFormAttributes> attributeList = entry.getValue();
			for (CellFormAttributes cellAttribute : attributeList) {
				if (cellAttribute.getType().equalsIgnoreCase("load")) {
					String[] rowcol = parent.getCellHelper()
							.getRowColFromExcelReferenceName(targetCell);
					if (rowcol[0].isEmpty()) {
						targetCell = "$" + rowcol[1] + "$" + (row + 1);
					}
					Cell cell = parent.getCellHelper().getCellByReference(
							targetCell, sheet);
					// if has full target cell i.e. $B$9 only apply when row ==
					// topRow
					if ((cell != null)
							&& (rowcol[0].isEmpty() || (row == topRow))) {
						String attrValue = cellAttribute.getValue();
						attrValue = attrValue.replace("$rowIndex",
								(row - topRow) + "");
						if (attrValue.contains("#{"))
							attrValue = FacesUtility.evaluateExpression(
									attrValue, String.class);
						parent.getCellHelper().setCellValue(cell, attrValue);
					}
				}
			}
		}
	}

	public void loadWorkSheet(String tabName) {
		parent.setCurrentTabName(tabName);
		String sheetName = parent.getSheetConfigMap().get(tabName)
				.getSheetName();
		Sheet sheet1 = parent.getWb().getSheet(sheetName);
		Map<String, CellRangeAddress> cellRangeMap = parent.getCellHelper()
				.indexMergedRegion(sheet1);
		List<String> skippedRegionCells = parent.getCellHelper()
				.skippedRegionCells(sheet1);
		loadHeaderRows(tabName, cellRangeMap, skippedRegionCells);
		loadBodyRows(tabName, cellRangeMap, skippedRegionCells);
		parent.getValidationHandler().validatePage(tabName, parent.getWb(),
				parent.getSheetConfigMap(), parent.getFormulaEvaluator(),
				parent.getDataFormatter(), parent.getBodyRows(),
				parent.getEngine());
		createDynamicColumns(tabName);
		saveObjs();

	}

	private void saveObjs() {

		Map<String, Object> viewMap = FacesContext.getCurrentInstance()
				.getViewRoot().getViewMap();
		// viewMap.put("wb", wb);
		// viewMap.put("formulaEvaluator", formulaEvaluator);
		// viewMap.put("dataFormatter", dataFormatter);
		// viewMap.put("headerRows", headerRows);
		// viewMap.put("bodyRows", bodyRows);
		// viewMap.put("sheetConfigMap", sheetConfigMap);
		// viewMap.put("engine",engine);
		// viewMap.put("tabs", tabs);
		viewMap.put("currentTabName", parent.getCurrentTabName());
		// viewMap.put("templateName", templateName);
		viewMap.put("fullValidation", parent.getFullValidation());

		debug("************ saveobjs viewMap = " + viewMap);

	}

	private void loadBodyRows(String tabName,
			Map<String, CellRangeAddress> cellRangeMap,
			List<String> skippedRegionCells) {

		//
		SheetConfiguration sheetConfig = parent.getSheetConfigMap()
				.get(tabName);
		boolean bodyPopulated = sheetConfig.isBodyPopulated();
		boolean repeatbody = parent.getCellHelper().getRepeatBodyFromConfig(
				sheetConfig);
		int initRows = parent.getCellHelper()
				.getInitRowsFromConfig(sheetConfig);
		int top = sheetConfig.getBodyCellRange().getTopRow();
		int bottom = parent.getCellHelper().getBodyBottomFromConfig(
				sheetConfig, initRows);
		int left = sheetConfig.getBodyCellRange().getLeftCol();
		int right = sheetConfig.getBodyCellRange().getRightCol();

		String sheetName = sheetConfig.getSheetName();
		Sheet sheet1 = parent.getWb().getSheet(sheetName);

		double totalWidth = parent.getCellHelper().calcTotalWidth(sheet1, left,
				right);

		parent.setBodyRows(new ArrayList<List<FacesCell>>());
		Row row = null;
		boolean repeatZone = false;
		for (int i = top; i <= bottom; i++) {
			repeatZone = false;
			if ((repeatbody) && (i >= top) && (i < (top + initRows))) {
				repeatZone = true;
				if ((!bodyPopulated) && (i > top))
					parent.getCellHelper().copyRow(sheet1, top, i);
			}
			row = sheet1.getRow(i);
			List<FacesCell> bodycells = new ArrayList<FacesCell>();
			CellRangeAddress caddress = null;
			for (int cindex = left; cindex <= right; cindex++) {

				if ((caddress == null) || (cindex > caddress.getLastColumn())) {
					String key = parent.getCellHelper()
							.findCellRangeAddressWithOffset(row.getRowNum(),
									cindex, initRows, top, repeatZone,
									bodyPopulated);
					caddress = cellRangeMap.get(key);
				}

				if ((caddress != null)
						&& (caddress.getLastColumn() > caddress
								.getFirstColumn())
						&& (cindex > caddress.getFirstColumn())
						&& (cindex <= caddress.getLastColumn())) {
					// skip this cell
				} else {

					Cell cell = null;
					if (i < (top + initRows)) {
						cell = row.getCell(cindex, Row.CREATE_NULL_AS_BLANK);
					} else {
						cell = row.getCell(cindex);
					}
					if (cell != null) {
						FacesCell fcell = new FacesCell(cell, parent);
						parent.getCellHelper().convertCell(sheetConfig, fcell,
								(i - top), initRows, top, repeatZone,
								cellRangeMap);
						parent.getCellHelper().setupCellStyle(parent.getWb(),
								sheet1, fcell, totalWidth);
						fcell.setColumnIndex(cindex - left);
						bodycells.add(fcell);
					}
				}
			}
			parent.getBodyRows().add(bodycells);
		}
		sheetConfig.setBodyPopulated(true);

		debug("Web Form loading bodyRows = " + parent.getBodyRows());
	}

	private void createDynamicColumns(String tabName) {

		SheetConfiguration sheetConfig = parent.getSheetConfigMap()
				.get(tabName);

		int left = sheetConfig.getBodyCellRange().getLeftCol();
		int right = sheetConfig.getBodyCellRange().getRightCol();

		parent.getColumns().clear();

		for (int i = left; i <= right; i++) {
			parent.getColumns().add("column" + (i - left));
		}

	}

	public void loadAllFields() {
		if (parent.getSheetConfigMap() != null) {
			for (SheetConfiguration sheetConfig : parent.getSheetConfigMap()
					.values()) {
				Sheet sheet = parent.getWb().getSheet(
						sheetConfig.getSheetName());
				for (Map.Entry<String, List<CellFormAttributes>> entry : sheetConfig
						.getCellFormAttributes().entrySet()) {
					String targetCell = entry.getKey();
					String cellAddr = parent.getCellHelper()
							.findCellAddressAfterBodyPopulated(targetCell,
									sheetConfig);
					Cell cell = null;
					if (cellAddr != null)
						cell = parent.getCellHelper().getCellByReference(
								cellAddr, sheet);
					if (cell != null) {
						List<CellFormAttributes> attributeList = entry
								.getValue();
						for (CellFormAttributes cellAttribute : attributeList) {
							if (cellAttribute.getType()
									.equalsIgnoreCase("load")) {
								String attrValue = cellAttribute.getValue();
								attrValue = FacesUtility.evaluateExpression(
										attrValue, String.class);
								parent.getCellHelper().setCellValue(
										cell,
										FacesUtility.evaluateExpression(
												cellAttribute.getValue(),
												String.class));
							}
						}
					}

				}
			}
		}
	}

	// commented below is another way to do export excel file
	// public void doExport() throws IOException {
	//
	// loadAllFields();
	// String filename =
	// parent.getCellHelper().evaluateExpression("#{submissionEdit.submission.submissionTypeCd}_#{submissionEdit.submissionRes.submission.organizationCode}_#{submissionEdit.submission.reportingPeriod}_#{submissionEdit.reportingYearCode}",
	// String.class)+".xls";
	//
	// FacesContext facesContext = FacesContext.getCurrentInstance();
	// ExternalContext externalContext = facesContext.getExternalContext();
	// externalContext.setResponseContentType("application/vnd.ms-excel");
	// externalContext.setResponseHeader("Content-Disposition",
	// "attachment; filename=\""+filename+"\"");
	// wb.write(externalContext.getResponseOutputStream());
	// facesContext.responseComplete();
	// log.info("Web Form Exported file finished filename = "+filename);
	// }

}
