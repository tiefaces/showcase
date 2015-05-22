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

import org.apache.poi.ss.util.CellReference;

/**
 * Range object used to define header/body range in the web form for configuration.
 * @author Jason Jiang
 * @note This object corresponds to header/body/footer range columns in configuration tab.
 */

public class CellRange {
	
	private int topRow;
	private int bottomRow;
	private int leftCol;
	private int rightCol;
	public int getTopRow() {
		return topRow;
	}
	public void setTopRow(int topRow) {
		this.topRow = topRow;
	}
	public int getBottomRow() {
		return bottomRow;
	}
	public void setBottomRow(int bottomRow) {
		this.bottomRow = bottomRow;
	}
	public int getLeftCol() {
		return leftCol;
	}
	public void setLeftCol(int leftCol) {
		this.leftCol = leftCol;
	}
	public int getRightCol() {
		return rightCol;
	}
	public void setRightCol(int rightCol) {
		this.rightCol = rightCol;
	}
	
	public CellRange(String range)
 {
		
		if ((range != null) && range.contains(":")) {
			
			 String[] rlist = range.split(":");
			 
			 if (rlist.length == 2) {
				 
				 CellReference ref0 = new CellReference(rlist[0].trim());
				 CellReference ref1 = new CellReference(rlist[1].trim());
				 
				this.setTopRow(ref0.getRow());
				this.setLeftCol(ref0.getCol());
				this.setBottomRow(ref1.getRow());
				this.setRightCol(ref1.getCol());
				 
			 }
		}
	}
 
//  commented below is another way to get reference in POI. In case we need it.
//	private Cell getCellFromReference(HSSFSheet sheet, String refStr) {
//		 CellReference ref = new CellReference(refStr);
//		 Row r = sheet.getRow(ref.getRow());
//		 return r.getCell(ref.getCol(),Row.CREATE_NULL_AS_BLANK);
//	}

	/**
	 * Obtain a human readable representation.
	 * @return String Human readable label
	 */	
	public String toString(){
    	
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        sb.append("topRow = " + topRow);
        sb.append(",");
        sb.append("bottomRow = " + bottomRow);
        sb.append(",");        
        sb.append("leftCol = " + leftCol);
        sb.append(",");        
        sb.append("rightCol = " + rightCol);
        sb.append("}");
        return sb.toString();
    }		
	
}
