package org.tiefaces.showcase.websheet;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.tiefaces.components.websheet.TieWebSheetBean;
import org.tiefaces.showcase.tablelookup.ItemList;
import org.tiefaces.showcase.websheet.datademo.Department;
import org.tiefaces.showcase.websheet.datademo.WebSheetDataDemo;

@ManagedBean
@ViewScoped
public class WebSheetReports extends TieWebSheetBean {

	private static final long serialVersionUID = 1L;
	
	
	private List<Object> itemList = new ItemList().getItemList();

	
	public int getRowCount() {
		
		if (itemList == null) {
			return 0;
		}
		else
			return itemList.size();
	}

	public void setItemList(List<Object> itemList) {
		this.itemList = itemList;
	}

	public List<Object> getItemList() {
		return itemList;
	}
	

	@Override
	public void initialLoad() {
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("items", itemList);
		InputStream stream = this.getClass().getClassLoader().getResourceAsStream("websheet/PRICELIST.xlsx");
		loadWebSheet(stream, context);		
	}

}
