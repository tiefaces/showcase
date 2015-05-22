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
package com.tiefaces.components.websheet.dataobjects;

import org.apache.poi.ss.usermodel.Cell;

import com.tiefaces.components.websheet.TieWebSheetBean;

/**
 * Cell object used for JSF datatable. This object hold an reference to POI cell
 * object
 * 
 * @author Jason Jiang
 */
public class FacesCell {

	private boolean debug = false;

	private void debug(String msg) {
		if (debug) {
			System.out.println("debug: " + msg);
		}
	}

	private TieWebSheetBean parent = null; // reference to bean object
	private Cell poiCell; // reference to poi cell object
	private String style = ""; // cell web css style  
	private String colspan = "1"; // cell column span default set to 1
	private int columnIndex; // column index in the datatable.
	private boolean invalid = false; // indicate the cell hold invalid data when set to true
	private String errormsg; // hold error message when the cell is invalid
	private String inputType = ""; // data type for input cell. could be text/text area/number etc

	/**
	 * Constructor.
	 * @param poiCell POI cell object
	 * @param parent TieWebSheetBean object
	 */
	public FacesCell(Cell poiCell, TieWebSheetBean parent) {
		super();
		this.poiCell = poiCell;
		this.parent = parent;
	}
	
	public Cell getPoiCell() {
		return poiCell;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getValidStyle() {
		if (invalid)
			return style + "border-color: red;";
		else
			return style;
	}
	public String getCellValue() {

		String result = parent.getCellHelper().getCellValue(poiCell);
		 debug("Web Form FacesCell getCellValue = " + result
		 +" input type = "+ inputType);
		return result;
	}
	public void setCellValue(String cellValue) {

		String oldValue = parent.getCellHelper().getCellValue(poiCell);
		String newValue = cellValue;
		if (this.getInputType().equalsIgnoreCase("textarea")
				&& (newValue != null)) {
			// remove "\r" because excel issue
			newValue = newValue.replace("\r\n", "\n");
		}
		if (newValue != null && !newValue.equals(oldValue)) {
			debug("Web Form FacesCell setCellValue Old: " + oldValue
					+ ", New: " + newValue + ", row =" + poiCell.getRowIndex()
					+ " col =" + poiCell.getColumnIndex() + " inputtype = "
					+ this.getInputType());
			parent.getCellHelper().setCellValue(poiCell, newValue);
			parent.getCellHelper().reCalc();
		}
	}

	public String getInputType() {
		return inputType;
	}
	public void setInputType(String inputType) {
		this.inputType = inputType;
	}
	public String getColspan() {
		return colspan;
	}
	public void setColspan(String colspan) {
		this.colspan = colspan;
	}
	public int getColumnIndex() {
		return columnIndex;
	}
	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}
	public boolean isInvalid() {
		return invalid;
	}
	public void setInvalid(boolean invalid) {
		this.invalid = invalid;
	}
	public String getErrormsg() {
		return errormsg;
	}
	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}

}
