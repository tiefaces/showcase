package org.tiefaces.showcase.websheet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.tiefaces.components.websheet.TieWebSheetValidation;
import org.tiefaces.showcase.websheet.datademo.Employee;

public class AliasValidationBean implements TieWebSheetValidation {

	/**
	 * This class demonstrate two ways for calling service:
	 * 1. Normal way: call service per validation. Since validation is triggered per cell, so it's result as multiple service call.
	 * 2. Optimized way. use a map to cache the result per submit. 
	 */
	private static final long serialVersionUID = 1L;

// Way 1: normal calling service per validation.
/*
  		@Override
	public String validate(Map<String, Object> dataContext, String dataField, String nestedDataFullName,
			String sheetName, int rowIndex, int colIndex, String inputValue) {

		return normalWayByCallingServicePerValidation(dataContext, dataField, nestedDataFullName, sheetName, rowIndex,
				colIndex, inputValue);
	}

  	private String normalWayByCallingServicePerValidation(Map<String, Object> dataContext, String dataField,
 
			String nestedDataFullName, String sheetName, int rowIndex, int colIndex, String inputValue) {

		ArrayList<String> list = new ArrayList<>();
		list.add(dataField);
		Map<String, String> resultMap = callService(list, dataContext);
		return finalMessage(dataField, resultMap);
	}

*/
	
// Way 2: use cached map.
	
	@Override
	public String validate(Map<String, Object> dataContext, String dataField, String nestedDataFullName,
			String sheetName, int rowIndex, int colIndex, String inputValue) {

		return optimizedWayByCallingServiceForAllFields(dataContext, dataField, nestedDataFullName, sheetName, rowIndex,
				colIndex, inputValue);

	}
	
	Map<String, String> cachedResultMap = null;
	Integer currentSubmitId = null;

	private String optimizedWayByCallingServiceForAllFields(Map<String, Object> dataContext, String dataField,
			String nestedDataFullName, String sheetName, int rowIndex, int colIndex, String inputValue) {

		Map<String, String> resultMap = checkCachedMap(dataContext);
		return finalMessage(dataField, resultMap);

	}

	private Map<String, String> checkCachedMap(Map<String, Object> dataContext) {

		Integer submitId = (Integer) dataContext.get("submitId");

		if ((currentSubmitId == null) || !currentSubmitId.equals(submitId)) {
			ArrayList<String> list = new ArrayList<>();
			list.add("employee.name");
			list.add("employee.birthDate");
			list.add("employee.sex");
			list.add("employee.worktime");
			list.add("employee.payment");
			list.add("employee.bonus");
			list.add("employee.total");
			cachedResultMap = callService(list, dataContext);
			currentSubmitId = submitId;
		}
		return cachedResultMap;
	}

	private Map<String, String> callService(ArrayList<String> list, Map<String, Object> dataContext) {

		Map<String, String> map = new HashMap<>();
		Employee employee = (Employee) dataContext.get("employee");
		for (String field : list) {
			switch (field) {
			case "employee.name":
				if ((employee.getName() == null) || (employee.getName().isEmpty())) {
					map.put(field, "name is required!");
				} else if (employee.getName().length() > 20) {
					map.put(field, "max name length is 20!");
				}
				break;
			case "employee.bonus":
				if (employee.getBonus() == null) {
					map.put(field, "bonus is required!");
				} else
				if (employee.getBonus() > 0.5d) {
					map.put(field, "bonus cannot over 50%!");
				}
				break;
			case "employee.total":
				if (employee.getTotal() == null) {
					map.put(field, "total is required!");
				} else
				if (employee.getTotal() > 3000d) {
					map.put(field, "total cannot over 3000!");
				}
				break;
			default:
				break;
			}
		}
		return map;
	}

	private String finalMessage(String dataField, Map<String, String> resultMap) {
		String returnMsg = "";
		if (resultMap != null) {
			returnMsg = resultMap.get(dataField);
		}
		if (returnMsg == null) {
			returnMsg = "";
		}
		return returnMsg;
	}
	
	
}
