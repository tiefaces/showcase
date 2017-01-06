package org.tiefaces.showcase.websheet;

import java.io.Serializable;
import java.util.List;
import org.tiefaces.showcase.tablelookup.ItemList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


@ManagedBean
@ViewScoped
public class WebSheetReports_Data implements Serializable {

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
	
	

}
