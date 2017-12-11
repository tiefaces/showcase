package org.tiefaces.showcase.websheet;

import org.tiefaces.components.websheet.TieWebSheetValidation;


public class ValidationBean implements TieWebSheetValidation {


	private boolean checkRule1(double value) {
		if (value > 0) {
			return true;
		}
		return false;
	}

	private boolean checkRule2(double value) {
		if (value < 500000) {
			return true;
		}
		return false;
	}

	@Override
	public String validate(String sheetName, int rowIndex, int colIndex, String inputValue) {

		boolean pass = true;
		switch (colIndex) {
		case 3:
			if (!isNumeric(inputValue)) {
				return "line " + (rowIndex + 1) + " error : please input a number.";
			}
			pass = checkRule2(Double.parseDouble(inputValue));
			if (!pass) {
				return "line " + (rowIndex + 1) + " error : value must less than 50,000.";
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
}
