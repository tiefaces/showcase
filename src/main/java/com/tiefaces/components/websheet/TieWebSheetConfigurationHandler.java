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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.tiefaces.common.FacesUtility;
import com.tiefaces.components.websheet.dataobjects.CellFormAttributes;
import com.tiefaces.components.websheet.dataobjects.CellRange;
import com.tiefaces.components.websheet.dataobjects.SheetConfiguration;

public class TieWebSheetConfigurationHandler {

	private TieWebSheetBean parent = null;
	
	private static boolean debug=false;
    private static void debug(String msg)
    {
        if (debug)
        {
            System.out.println("debug: " + msg);
        }
    }		
	
	// Basically configuration are dived into two parts: 1. form level 2. attributes level. 
	// attrCol is the first column which indicate the attributes level starting
	// setting attrCol into variable just for easy extend the form level range 
	private static final int attrCol =  9;  
	
	public TieWebSheetConfigurationHandler(TieWebSheetBean parent) {
		super();
		this.parent = parent;
	}
	
	private String rowCell(Row row, int cn) {
	return parent.getCellHelper().getCellValue(row.getCell(cn, Row.CREATE_NULL_AS_BLANK));
	}
	private void addAttributesToMap(Map<String, List<CellFormAttributes>> map, Row row) {
		List<CellFormAttributes> attributes =  map.get(rowCell(row,attrCol));
		if (attributes == null) {
			map.put(rowCell(row,attrCol), new ArrayList<CellFormAttributes>());
			attributes =  map.get(rowCell(row,attrCol));
		}
		CellFormAttributes cellattribute = new CellFormAttributes();
		cellattribute.setType(rowCell(row,attrCol + 1));
		cellattribute.setValue(rowCell(row,attrCol + 2));
		cellattribute.setMessage(rowCell(row,attrCol + 3));
		attributes.add(cellattribute);
	}
	

	public  HashMap<String, SheetConfiguration> buildConfiguration() {
		
		HashMap<String, SheetConfiguration> sheetConfigMap = new HashMap<String, SheetConfiguration>();
		Sheet sheet1 = parent.getWb().getSheet("Configuration");
		// Iterate through each rows from configuration sheet
		Iterator<Row> rowIterator = sheet1.iterator();

		String newTabName=null;
		String oldTabName=null;
		SheetConfiguration sheetConfig=null;

		while (rowIterator.hasNext()) {

			Row row = rowIterator.next();

			if (row.getRowNum()>0) {  // skip first header row   
				// For each row, iterate through each columns
				newTabName = rowCell(row, 0);
				if (oldTabName == null) oldTabName = newTabName;
				if (newTabName.isEmpty()) {
					addAttributesToMap(sheetConfig.getCellFormAttributes(), row);
				} else {
					if ( !newTabName.equalsIgnoreCase(oldTabName)) {
						sheetConfigMap.put(oldTabName, sheetConfig);
						oldTabName = newTabName;
					}
					sheetConfig = new SheetConfiguration();
					sheetConfig.setTabName(newTabName);
					sheetConfig.setSheetName(rowCell(row,1));
					String tempStr = rowCell(row,2);
					sheetConfig.setFormHeaderRange(tempStr);
					sheetConfig.setHeaderCellRange(new CellRange(tempStr));
					tempStr = rowCell(row,3);
					sheetConfig.setFormBodyRange(tempStr);
					sheetConfig.setBodyCellRange(new CellRange(tempStr));
					tempStr = rowCell(row,4);
					sheetConfig.setFormFooterRange(tempStr);
					sheetConfig.setFooterCellRange(new CellRange(tempStr));
					sheetConfig.setFormBodyType(rowCell(row,5));
					sheetConfig.setBodyAllowAddRows(rowCell(row,6));
					tempStr = rowCell(row,7).trim();
					
					if (tempStr.startsWith("#{")) {
						tempStr = FacesUtility.evaluateExpression(tempStr, String.class);
					}
					sheetConfig.setBodyInitialRows(tempStr);
					sheetConfig.setFormPageTypeId( rowCell(row,8)); 
					
					sheetConfig.setCellFormAttributes(new HashMap<String, List<CellFormAttributes>>());
					sheetConfig.getCellFormAttributes().put(rowCell(row,attrCol), new ArrayList<CellFormAttributes>());
					addAttributesToMap(sheetConfig.getCellFormAttributes(), row);
				}
			}
		}
		sheetConfigMap.put(oldTabName, sheetConfig);
debug("Web Form ConfigurationHandler after iteration sheetConfigmap = "+sheetConfigMap);					
		return sheetConfigMap;
	}	

}
