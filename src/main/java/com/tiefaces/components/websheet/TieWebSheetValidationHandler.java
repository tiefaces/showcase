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

import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.script.ScriptEngine;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.primefaces.context.RequestContext;

import com.tiefaces.common.FacesUtility;
import com.tiefaces.components.websheet.dataobjects.CellFormAttributes;
import com.tiefaces.components.websheet.dataobjects.FacesCell;
import com.tiefaces.components.websheet.dataobjects.FacesRow;
import com.tiefaces.components.websheet.dataobjects.SheetConfiguration;

public class TieWebSheetValidationHandler {
	
	private TieWebSheetBean parent = null;
	
	private static boolean debug=false;
    private static void debug(String msg)
    {
        if (debug)
        {
            System.out.println("SRISQL: " + msg);
        }
    }
	public TieWebSheetValidationHandler(TieWebSheetBean parent) {
		super();
		this.parent = parent;
		// TODO Auto-generated constructor stub
	}

	/// <summary>
	/// Description:  validate single cell
	/// </summary>
	/// <param name="component">UIComponent which be validated</param>
	/// <param name="value">value will be validated</param>
	/// <param name="wb">Workbook reference</param>
	/// <param name="formulaEvaluator">FormulaEvaluator reference</param>
	/// <param name="sheetConfigMap"> Map which contain configuration</param>
	/// <param name="bodyRows">manage bean for datatable</param>
	/// <param name="engine">ScriptEngine for validation</param>
	/// <param name="currentTabName">current tab</param>
	/// <param name="useExistingValue"> if true, use cell value to replace the value.  </param>
	/// <param name="passEmptyCheck"> if true and value is empty, then pass the validation</param>
	public void validate(UIComponent component,
			String value,
			boolean useExistingValue, boolean passEmptyCheck) throws ValidatorException {
//UIComponent client id = frmSubmissionWebform:webformtables:0:column2
//                        frmSubmissionWebform:webformtables:0:j_idt107:2:column	
		int[] rowcol = parent.getCellHelper().getRowColFromComponentName(component);
		int row = rowcol[0];
		int col = rowcol[1];
		validateWithRowCol( row, col, value, useExistingValue, passEmptyCheck);
	}		

	
private void refreshAfterStatusChanged(boolean oldStatus, boolean newStatus, int irow, int icol, FacesCell cell) {
	
	if (!newStatus) cell.setErrormsg("");
	cell.setInvalid(newStatus);
	if (oldStatus != newStatus) {
		RequestContext.getCurrentInstance().update(parent.getWebFormClientId()+":"+irow+":column"+icol);
		RequestContext.getCurrentInstance().update(parent.getWebFormClientId()+":"+irow+":msgcolumn"+icol);
	}
	
}
public void validateWithRowCol( int row, int col,
		String value,
		boolean useExistingValue, boolean passEmptyCheck) throws ValidatorException {
		
		SheetConfiguration sheetConfig = parent.getSheetConfigMap().get(parent.getCurrentTabName()); 
		
		Sheet sheet = parent.getWb().getSheet(parent.getSheetConfigMap().get(parent.getCurrentTabName()).getSheetName());
		
		FacesCell cell = parent.getCellHelper().getFacesCellFromBodyRow(row, col, parent.getBodyRows());
		if (cell == null) return;
		boolean oldStatus = cell.isInvalid();
		
		if (useExistingValue) {
			value = parent.getCellHelper().getCellValueWithoutFormat(cell.getPoiCell());
		}
		
		if (value==null) value = "";
		else value = value.trim();  
		if (passEmptyCheck&&value.isEmpty()) {
				refreshAfterStatusChanged(oldStatus,false, row, col, cell); 				
				return;
		}
		
		List<CellFormAttributes> cellAttributes = parent.getCellHelper().findCellAttributes(sheetConfig, cell.getPoiCell(), row, sheetConfig.getBodyCellRange().getTopRow() );
		if (cellAttributes != null) {
			for( CellFormAttributes attr: cellAttributes) {
				boolean pass = doValidation(value,attr,cell.getPoiCell().getRowIndex(),sheet);
				if (!pass) {
					String errmsg = attr.getMessage();
					if (errmsg== null) errmsg = "Invalid input";
					cell.setErrormsg(errmsg);
					refreshAfterStatusChanged(false,true, row, col, cell); 				
			        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Valication", errmsg);
			        debug("Web Form ValidationHandler validate failed = "+errmsg+"; row ="+row+" col= "+ col);
			        throw new ValidatorException(msg);
				}
				
			} 
		}
		refreshAfterStatusChanged(oldStatus,false, row, col, cell); 				
 
}	
	


private boolean doValidation(Object value, CellFormAttributes attr, int rowIndex,Sheet sheet) throws ValidatorException  {
	String attrType = attr.getType().trim();
	boolean pass = true;
	if (attrType.equalsIgnoreCase("input")) {
		pass = FacesUtility.evalInputType(value.toString(),attr.getValue());
		
	}else if (attrType.equalsIgnoreCase("check")) {
		String attrValue = attr.getValue();
		attrValue = attrValue.replace("$value", value.toString()+"");
		attrValue = parent.getCellHelper().replaceExpressionWithCellValue(attrValue, rowIndex,sheet);
		pass = parent.getCellHelper().evalBoolExpression(attrValue);
	}
	return pass;
	
}


public boolean validateCell(final UIComponent target ) {

    		
try {
	validate( target, null, true, true);
}
catch (ValidatorException ex) {
	// use log.debug because mostly they are expected 
	debug("Web Form ValidationHandler validateCell failed error = "+ ex.getLocalizedMessage());
	return false;
}
return true;
}	



//public static boolean validateTab(final String tabName, final Workbook wb, final Map<String, SheetConfiguration> sheetConfigMap, final FormulaEvaluator formulaEvaluator,final List<List<FacesCell>> bodyRows, final ScriptEngine engine) {
//
//	
//	
//FacesContext facesContext = FacesContext.getCurrentInstance();
//facesContext.getViewRoot().getViewMap().put("validateTabStatus", "PASS");
//UIData table = (UIData) facesContext.getViewRoot().findComponent(WebFormHelper.getWebFormClientId());
//table.visitTree(VisitContext.createVisitContext(FacesContext.getCurrentInstance()), new VisitCallback() {
//    @Override
//    public VisitResult visit(VisitContext context, UIComponent target) {
//    	String id = target.getId();
//    	if (id.startsWith("column")) {
//    		
//    		try {
//    		ValidationHandler.validate(target, null, wb, formulaEvaluator, sheetConfigMap, bodyRows, engine, tabName, true, true);
//    		}
//    		catch (ValidatorException ex) {
//				if (target instanceof UIInput) {
//			             //((UIInput)target).setValid(false);
//			 			FacesContext.getCurrentInstance().addMessage(target.getClientId(), ex.getFacesMessage());
//			 			RequestContext.getCurrentInstance().update(target.getClientId());	
//			 			int[] rowcol = WebFormHelper.getRowColFromComponentName(target);
//			 			System.out.println(" update msg component = "+WebFormHelper.getWebFormClientId()+":"+rowcol[0]+":msgcolumn"+rowcol[1]);	
//			 			RequestContext.getCurrentInstance().update(WebFormHelper.getWebFormClientId()+":"+rowcol[0]+":msgcolumn"+rowcol[1]);
//			 			Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
//			 			String validateStatus = (String) viewMap.get("validateTabStatus");
//			 			if (!validateStatus.equalsIgnoreCase("FAILED")) viewMap.put("validateTabStatus", "FAILED");
//				}        
//			             
//    		}
//    		
//    	}
//        return VisitResult.ACCEPT;
//    }
//});	
//String status = (String) facesContext.getViewRoot().getViewMap().get("validateTabStatus");
//System.out.println(" validation tab "+tabName+" status = "+status);
//if (status.equalsIgnoreCase("PASS")) 
//	return true;
//else
//	return false;
//}


public  boolean validatePage(String tabName,Workbook wb, Map<String, SheetConfiguration> sheetConfigMap, FormulaEvaluator formulaEvaluator,DataFormatter dataFormatter, List<FacesRow> bodyRows,ScriptEngine engine) {
boolean allpass = true;

boolean passEmptyCheck=true;

Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
Boolean fullvalidation = (Boolean) viewMap.get("fullValidation");
if ((fullvalidation!=null)&&(fullvalidation)) passEmptyCheck = false;

SheetConfiguration sheetConfig = sheetConfigMap.get(tabName);
if (sheetConfig != null) {
	for (int irow=0; irow< bodyRows.size(); irow++) {
		if (!validateRow(irow,tabName,passEmptyCheck)) allpass=false;
	}
}
return allpass;
}

public boolean validateRow(int irow, String tabName, boolean passEmptyCheck) {
boolean pass = true;
SheetConfiguration sheetConfig = parent.getSheetConfigMap().get(tabName);
if (sheetConfig != null) {
		List<FacesCell> cellRow =  parent.getBodyRows().get(irow).getCells();
		for (int icol=0; icol < cellRow.size(); icol++) {
			try {
				validateWithRowCol( irow, icol, null, true, passEmptyCheck);
			}
			catch (ValidatorException ex) {
				pass=false;
				//RequestContext.getCurrentInstance().update(WebFormHelper.getWebFormClientId()+":"+irow+":column"+icol);
				//RequestContext.getCurrentInstance().update(WebFormHelper.getWebFormClientId()+":"+irow+":msgcolumn"+icol);
			}
		}
}		
return pass;
}


public String findFirstInvalidSheet(boolean passEmptyCheck) {
	for (Map.Entry<String, SheetConfiguration> entry : parent.getSheetConfigMap().entrySet()) {
		SheetConfiguration sheetConfig = entry.getValue();
		String tabName = entry.getKey();
		Sheet sheet = parent.getWb().getSheet(sheetConfig.getSheetName());				
		int initialRows = sheetConfig.getBodyInitialRows();
		int topRow = sheetConfig.getBodyCellRange().getTopRow();
		for (int datarow=0; datarow< initialRows; datarow++) {
			if (!validateDataRow(datarow,initialRows,topRow,sheet,sheetConfig,passEmptyCheck)) return tabName;
		}
	}
	return null;
}


private boolean validateDataRow(int datarow,int initialRows,int topRow, Sheet sheet, SheetConfiguration sheetConfig, boolean passEmptyCheck) {
	
	 boolean rowpass = true;
	 for (Map.Entry<String, List<CellFormAttributes>> entry : sheetConfig.getCellFormAttributes().entrySet()) {
		    String targetCell = entry.getKey();
	    	List<CellFormAttributes> attributeList = entry.getValue();
	    	Cell cell = null;
	    	for ( CellFormAttributes attr: attributeList) {
	    		if ((attr.getType().equalsIgnoreCase("input")||attr.getType().equalsIgnoreCase("check"))) {
	    			if (cell==null) cell = parent.getCellHelper().getCellReferenceWithConfig(targetCell, datarow,initialRows, sheetConfig, sheet);
		    		if (cell != null) {
			    		String cellValue = parent.getCellHelper().getCellValueWithoutFormat(cell);
			    		if (!(passEmptyCheck&&cellValue.isEmpty())) {
				    		if (!doValidation(cellValue,attr, topRow + datarow ,sheet)) {
				    			debug("Web Form ValidationHandler validateDatarow targetCell = "+ targetCell+" validation failed; datarow="+datarow);		    
				    			rowpass = false;
				    			break;
				    		}
			    		}
		    		}
	    		}
	    	}
	 }    	
   return rowpass;	
}



}
