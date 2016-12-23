package org.tiefaces.showcase.websheet;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.tiefaces.showcase.websheet.datademo.Department;
import org.tiefaces.showcase.websheet.datademo.WebSheetDataDemo;

import com.tiefaces.components.websheet.TieWebSheetBean;

@ManagedBean
@ViewScoped
public class WebSheetDataAnnotation extends TieWebSheetBean {

	private static final long serialVersionUID = 1L;

	@Override
	public void initialLoad()  {
		Map<String, Object> context = new HashMap<String, Object>();
		List<Department> departments = WebSheetDataDemo.createDepartments();
		context.put("departments", departments);
		InputStream stream = this.getClass().getClassLoader().getResourceAsStream("websheet/datacommentdemo.xlsx");
		loadWebSheet(stream, context);		
	}

}
