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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Row;
import org.primefaces.model.StreamedContent;

import com.tiefaces.components.websheet.TieWebSheetBean;

/**
 * Row object used for JSF datatable.
 * 
 * @author Jason Jiang
 */
public class FacesRow {

	private boolean debug = true;

	private void debug(String msg) {
		if (debug) {
			System.out.println("debug: " + msg);
		}
	}

	private TieWebSheetBean parent = null; // reference to bean object	
	private int rowIndex;
	private boolean rendered;
	private float rowheight; 
	private boolean repeatZone;
	private List<FacesCell> cells;
	

	public FacesRow(int rowIndex, TieWebSheetBean parent) {
		super();
		this.rowIndex = rowIndex;
		this.parent = parent;
	}
	
	public int getRowIndex() {
		return rowIndex;
	}
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}
	public boolean isRendered() {
		return rendered;
	}
	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}
	public float getRowheight() {
		return rowheight;
	}
	public void setRowheight(float rowheight) {
		this.rowheight = rowheight;
	}
	public boolean isRepeatZone() {
		return repeatZone;
	}
	public void setRepeatZone(boolean repeatZone) {
		this.repeatZone = repeatZone;
	}

	public List<FacesCell> getCells() {
		return cells;
	}

	public void setCells(List<FacesCell> cells) {
		this.cells = cells;
	}
	
	public String cellValue(int colIndex) {
		
		Cell poiCell = parent.getWebSheetLoader().getPoiCellFromRowColumnIndex(rowIndex, colIndex);
		if (poiCell != null) {
			String result = parent.getCellHelper().getCellValueWithoutFormat(poiCell);
			 debug("Web Form FacesCell getCellValue = " + result);
			return result;
		}
		// return blank if null
        return "";		
		
	}

	public String cellFormatValue(int colIndex) {
		
		Cell poiCell = parent.getWebSheetLoader().getPoiCellFromRowColumnIndex(rowIndex, colIndex);
		if (poiCell != null) {
			String result = parent.getCellHelper().getCellValueWithFormat(poiCell);
			 debug("Web Form FacesCell getCellFormatValue = " + result);
			return result;
		}
		// return blank if null
        return "";		
	}
	
}
