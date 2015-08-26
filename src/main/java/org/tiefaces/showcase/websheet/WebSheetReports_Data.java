package org.tiefaces.showcase.websheet;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.tiefaces.showcase.tablelookup.Item;
import org.tiefaces.showcase.tablelookup.ItemSearchHelper;


@ManagedBean
@ViewScoped
public class WebSheetReports_Data implements Serializable{

	private static final long serialVersionUID = 1L;

	private List<Object> itemList=null;
	
	public List<Object> getItemList() {
		if (itemList == null) {
			this.setItemList(ItemSearchHelper.dosearch(new Item()));
		}
		return itemList;
	}

	
	public int getRowCount() {
		this.getItemList();
		if (itemList == null) return 0;
		else return itemList.size();
	}


	public void setItemList(List<Object> itemList) {
		this.itemList = itemList;
	}

}
