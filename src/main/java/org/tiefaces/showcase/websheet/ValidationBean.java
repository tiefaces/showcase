package org.tiefaces.showcase.websheet;

import java.util.Map;

import org.tiefaces.components.websheet.TieWebSheetValidation;


public class ValidationBean implements TieWebSheetValidation {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean checkRule1(double value) {
		if (value > 0) {
			return true;
		}
		return false;
	}

	private boolean checkRule2(double value) {
		if (value >0 && value < 500000) {
			return true;
		}
		return false;
	}

	
	private boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}


	@Override
	public String validate(Map<String, Object> dataContext, String dataField, String nestedDataFullName,
			String sheetName, int rowIndex, int colIndex, String inputValue) {
		boolean pass = true;
		switch (colIndex) {
		case 3:
			if (!isNumeric(inputValue)) {
				return "line " + (rowIndex + 1) + " error : please input a number.";
			}
			pass = checkRule2(Double.parseDouble(inputValue));
			if (!pass) {
				return "line " + (rowIndex + 1) + " error : value must greater than 0 and less than 50,000.";
			}
			break;
		case 4:
			if (!isNumeric(inputValue)) {
				return "line " + (rowIndex + 1) + " error : please input a number.";
			}
			pass = checkRule1(Double.parseDouble(inputValue));
			if (!pass) {
				return "line " + (rowIndex + 1) + " error : value must greater than 0.";
			}
			break;
		default:
			break;
		}
		return "";	
	}	
}
