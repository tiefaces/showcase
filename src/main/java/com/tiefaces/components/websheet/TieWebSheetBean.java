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

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Workbook;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.tiefaces.common.FacesUtility;
import com.tiefaces.components.websheet.dataobjects.FacesCell;
import com.tiefaces.components.websheet.dataobjects.HeaderCell;
import com.tiefaces.components.websheet.dataobjects.SheetConfiguration;

import javax.script.ScriptEngine;


public abstract class TieWebSheetBean extends TieWebSheetView implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3495468356246589276L;

	private List<String> columns;
	private List<List<Object>> bodyRows;
	private List<List<Object>> headerRows;
	private Workbook wb;
	private FormulaEvaluator formulaEvaluator;
	private DataFormatter dataFormatter;

	private Map<String, Picture> picturesMap; 
	private Map<String, SheetConfiguration> sheetConfigMap;
	private ScriptEngine engine;
	private String currentTabName;
	private int currentTopRow;
	private Boolean fullValidation = false;

	private TieWebSheetLoader webSheetLoader = null;
	private TieWebSheetCellHelper cellHelper = null;
	private TieWebSheetPicturesHelper picHelper = null;
	private TieWebSheetDataHandler dataHandler = null;
	private TieWebSheetValidationHandler validationHandler = null;

	private String webFormClientId = null;
	private String excelType=null;

	private boolean debug = true;

	private void debug(String msg) {
		if (debug) {
			System.out.println("DEBUG: " + msg);
		}
	}

	public TieWebSheetBean() {
		debug("TieWebSheetBean Constructor");
	}

	@PostConstruct
	public final void init() {
		debug("TieWebSheetBean into postConstructor");
		columns = new ArrayList<String>();
		engine = (ScriptEngine) FacesUtility
				.evaluateExpressionGet("#{tieWebSheetApp.engine}");
		webSheetLoader = new TieWebSheetLoader(this);
		cellHelper = new TieWebSheetCellHelper(this);
		dataHandler = new TieWebSheetDataHandler(this);
		validationHandler = new TieWebSheetValidationHandler(this);
		picHelper = new TieWebSheetPicturesHelper(this);
		initialLoad();
	}


	public final void setWebFormClientId(String webFormClientId) {
		this.webFormClientId = webFormClientId;
	}

	public final String getWebFormClientId() {
		return webFormClientId;
	}

	public final List<List<Object>> getBodyRows() {
		return bodyRows;
	}

	public final void setBodyRows(List<List<Object>> bodyRows) {
		this.bodyRows = bodyRows;
	}

	public final List<List<Object>> getHeaderRows() {
		return headerRows;
	}

	public final void setHeaderRows(List<List<Object>> headerRows) {
		this.headerRows = headerRows;
	}

	public final void setColumns(List<String> columns) {
		this.columns = columns;
	}

	public final Workbook getWb() {
		return wb;
	}

	public final void setWb(Workbook wb) {
		this.wb = wb;
	}

	public final FormulaEvaluator getFormulaEvaluator() {
		return formulaEvaluator;
	}

	public final void setFormulaEvaluator(FormulaEvaluator formulaEvaluator) {
		this.formulaEvaluator = formulaEvaluator;
	}

	public final DataFormatter getDataFormatter() {
		return dataFormatter;
	}

	public final void setDataFormatter(DataFormatter dataFormatter) {
		this.dataFormatter = dataFormatter;
	}

	public final List<String> getColumns() {
		return columns;
	}

	public final String getCurrentTabName() {
		return currentTabName;
	}

	public final void setCurrentTabName(String currentTabName) {
		this.currentTabName = currentTabName;
	}

	public final Boolean getFullValidation() {
		return fullValidation;
	}

	public final void setFullValidation(Boolean fullValidation) {
		this.fullValidation = fullValidation;
	}

	public final TieWebSheetCellHelper getCellHelper() {
		return cellHelper;
	}

	public final ScriptEngine getEngine() {
		return engine;
	}

	public final TieWebSheetLoader getWebSheetLoader() {
		return webSheetLoader;
	}

	public final TieWebSheetDataHandler getDataHandler() {
		return dataHandler;
	}

	public final TieWebSheetValidationHandler getValidationHandler() {
		return validationHandler;
	}
	
	
	public TieWebSheetPicturesHelper getPicHelper() {
		return picHelper;
	}

	public final String getExcelType() {
		return excelType;
	}

	public final void setExcelType(String excelType) {
		this.excelType = excelType;
	}
	

	public int getCurrentTopRow() {
		return currentTopRow;
	}

	public void setCurrentTopRow(int currentTopRow) {
		this.currentTopRow = currentTopRow;
	}
	
	public Map<String, Picture> getPicturesMap() {
		return picturesMap;
	}

	public void setPicturesMap(Map<String, Picture> picturesMap) {
		this.picturesMap = picturesMap;
	}

	public final void initWorkBook() {
		if (this.getWb() == null) {

			int return_flag = webSheetLoader.loadWorkbook();
			if (return_flag <= 0) {
				debug(" loadworkbook failed, return = " + return_flag);
				return;
			}
		}
	}

	public final void loadWebSheet() {
		webSheetLoader.loadWorkbook();
	}

	public final void onTabChange(TabChangeEvent event) {
		String tabName = event.getTab().getTitle();

		int sheetId = findTabIndexWithName(tabName);

		if ((getSheetConfigMap() != null)
				&& (sheetId < getSheetConfigMap().size()))
			webSheetLoader.loadWorkSheet(tabName);
	}

	private StreamedContent exportFile;
	public final StreamedContent getExportFile() {
		return exportFile;
	}
	public final void doExportPrime() {
		try {

			webSheetLoader.loadAllFields();
			String fileName = FacesUtility
					.evaluateExpression(
							"#{submissionEdit.submission.submissionTypeCd}_#{submissionEdit.submissionRes.submission.organizationCode}_#{submissionEdit.submission.reportingPeriod}_#{submissionEdit.reportingYearCode}",
							String.class)
					+ ".xls";
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			wb.write(out);
			InputStream stream = new BufferedInputStream(
					new ByteArrayInputStream(out.toByteArray()));
			exportFile = new DefaultStreamedContent(stream,
					"application/force-download", fileName);

		} catch (Exception e) {
			e.printStackTrace();
			debug(e.getLocalizedMessage());
		}
		return;
	}

	// commented below is another way to do export excel file
	// public void doExport() throws IOException {
	//
	// loadAllFields();
	// String filename =
	// WebFormHelper.evaluateExpression("#{submissionEdit.submission.submissionTypeCd}_#{submissionEdit.submissionRes.submission.organizationCode}_#{submissionEdit.submission.reportingPeriod}_#{submissionEdit.reportingYearCode}",
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

	private int findTabIndexWithName(String tabname) {

		for (int i = 0; i < tabs.size(); i++) {
			if (tabs.get(i).getTitle().equalsIgnoreCase(tabname))
				return i;
		}
		return -1;

	}

	private boolean preValidation(boolean passEmptyCheck) {

		String tabName = validationHandler
				.findFirstInvalidSheet(passEmptyCheck);
		if (tabName != null) {
			if (!currentTabName.equalsIgnoreCase(tabName)) {
				int tabIndex = findTabIndexWithName(tabName);
				webFormTabView.setActiveIndex(tabIndex);
				// RequestContext.getCurrentInstance().update("frmSubmissionWebform:tabview");
			}
			webSheetLoader.loadWorkSheet(tabName);
			// RequestContext.getCurrentInstance().update(WebFormHelper.getWebFormClientId());
			return false;
		}
		return true;
	}

	private void setFullValidationInView(Boolean fullflag) {

		Map<String, Object> viewMap = FacesContext.getCurrentInstance()
				.getViewRoot().getViewMap();
		Boolean flag = (Boolean) viewMap.get("fullValidation");
		if ((flag == null) || (!flag.equals(fullflag))) {
			viewMap.put("fullValidation", fullflag);
		}
	}

	public final void doSave() {

		fullValidation = false;
		setFullValidationInView(fullValidation);
		if (!preValidation(true)) {
			debug("Validation failded before saving");
			return;
		}
		processSave();

	}

	
	private int processSave() {

		// String formid =
		// WebFormHelper.evaluateExpression("#{submissionEdit.formId}",
		// String.class);
		// SRIForm formobj = FormDataHandler.buildRequestForSaving(formid, wb,
		// sheetConfigMap, formulaEvaluator, dataFormatter);
		//
		// try {
		// SRISubmissionDataSaveRequest request = new
		// SRISubmissionDataSaveRequest(SRIWebUtils.getRemoteUserId(),SRIWebUtils.getLocale(),
		// SRIWebUtils.getRequestId());
		// request.setForm(formobj);
		// log.debug("WebFormBean doSave request formobj="+formobj);
		// SRISubmissionDataSaveResponse response =
		// SRIWebFacade.getSubmissionFacade().saveSubmissionData(request);
		// if(response.isResponseInError()) {
		// log.error("Web Form Saving Error response = "+ response);
		// FacesContext.getCurrentInstance().addMessage(null,
		// AppMessageHelper.getErrorMessage(SRIWebConstants.SRI_MESSAGE_ERROR_WEBFORM_SAVE));
		// } else {
		//
		// SRIForm formobj_response = response.getForm();
		// if (formobj_response != null)
		// FormDataHandler.populateDataToExcelSheet(formobj_response, wb,
		// sheetConfigMap, formulaEvaluator, true);
		//
		// FacesContext.getCurrentInstance().addMessage(null,
		// AppMessageHelper.getInfoMessage(SRIWebConstants.SRI_MESSAGE_INFO_WEBFORM_SAVE));
		// FormDataHandler.setUnsavedStatus(RequestContext.getCurrentInstance(),
		// false);
		// }
		// resetInvalidMsgInBodyRows();
		// } catch (Exception e) {
		// e.printStackTrace();
		// log.error("Web Form Saving Error Exception = "+
		// e.getLocalizedMessage());
		// FacesContext.getCurrentInstance().addMessage(null,
		// AppMessageHelper.getErrorMessage(SRIWebConstants.SRI_MESSAGE_ERROR_WEBFORM_SAVE));
		// return -1;
		// }
		return 1;
	}

	public void loadData() {

		// try {
		// String formid =
		// WebFormHelper.evaluateExpression("#{submissionEdit.formId}",
		// String.class);
		// SRISubmissionDataFetchRequest fetchRequest = new
		// SRISubmissionDataFetchRequest(SRIWebUtils.getRemoteUserId(),SRIWebUtils.getLocale(),
		// SRIWebUtils.getRequestId());
		// fetchRequest.setFormId(Long.parseLong(formid));
		// SRISubmissionDataFetchResponse fetchResponse =
		// SRIWebFacade.getSubmissionFacade().getSubmissionData(fetchRequest);
		// SRIForm formobj = null;
		// if (fetchResponse != null ){
		// formobj= fetchResponse.getForm();
		// log.debug("WebFormBean loadData formobj = "+formobj);
		// if(fetchResponse.isResponseInError()) {
		// log.error("Web Form Loading Error response = "+ fetchResponse);
		// FacesContext.getCurrentInstance().addMessage(null,
		// AppMessageHelper.getErrorMessage(SRIWebConstants.SRI_MESSAGE_ERROR_WEBFORM_LOAD));
		// return;
		// }
		// }
		// if (formobj != null)
		// FormDataHandler.populateDataToExcelSheet(formobj, wb, sheetConfigMap,
		// formulaEvaluator, false);
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// log.error("Web Form Loading Error Exception = "+
		// e.getLocalizedMessage());
		// FacesContext.getCurrentInstance().addMessage(null,
		// AppMessageHelper.getErrorMessage(SRIWebConstants.SRI_MESSAGE_ERROR_WEBFORM_LOAD));
		// }
		//

	}

	/*
	 * Needed a change handler on the note field, which doesn't need all the
	 * other code below
	 */
	public void noteChangeEvent(AjaxBehaviorEvent event) {
		dataHandler.setUnsavedStatus(RequestContext.getCurrentInstance(), true);
	}

	public final void valueChangeEvent(AjaxBehaviorEvent event) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		String tblName = getWebFormClientId();
		UIComponent target = event.getComponent();
		boolean pass = validationHandler.validateCell(target);
		if (pass) {
			// to improve performance, re-validate current row only
			// page validation take times. will happen when change tab(page) or
			// reload page.
			int[] rowcol = cellHelper.getRowColFromComponentName(target);
			validationHandler.validateRow(rowcol[0], this.getCurrentTabName(),
					true);
			// refresh current page calculation fields
			UIComponent s = facesContext.getViewRoot().findComponent(tblName);
			if (s != null) {
				DataTable webDataTable =(DataTable) s;
				int first = webDataTable.getFirst();
				int rowsToRender = webDataTable.getRowsToRender();
				int rowCounts = webDataTable.getRowCount();
				for (int i = first; i <= (first + rowsToRender); i++) {
					if (i < rowCounts) {
						for (Object cellobject : bodyRows.get(i)) {
							if (cellobject instanceof FacesCell) {
								FacesCell cell = (FacesCell) cellobject;
							if (cell.getPoiCell().getCellType() == Cell.CELL_TYPE_FORMULA) {
								debug("refresh obj name ="+tblName + ":" + i + ":cocalc"
												+ cell.getColumnIndex()+" formula = "+cell.getPoiCell().getCellFormula()+" value = "+cell.getCellValue());
								RequestContext.getCurrentInstance().update(
										tblName + ":" + i + ":cocalc"
												+ cell.getColumnIndex());
							}
							}
						}
					}

				}
			}
		}
		dataHandler.setUnsavedStatus(RequestContext.getCurrentInstance(), true);
	}

	public final boolean isMultiplePage() {
		if ((bodyRows != null) && (bodyRows.size() > this.getMaxRowsPerPage()))
			return true;
		return false;
	}

	@PreDestroy
	public final void finish() {
		debug("finishing view webformbean");
		if (FacesContext.getCurrentInstance() == null) {
			debug("session has gone");
		}

	}

	public final Map<String, SheetConfiguration> getSheetConfigMap() {
		return sheetConfigMap;
	}

	public final void setSheetConfigMap(Map<String, SheetConfiguration> sheetConfigMap) {
		this.sheetConfigMap = sheetConfigMap;
	}
	
	
	public abstract InputStream loadWebSheetTemplate();
	public abstract void initialLoad();

	   public void onCellEdit(CellEditEvent event) {
		   
		   System.out.println(" into celledit event row ="+event.getRowIndex());
	    }   

}
