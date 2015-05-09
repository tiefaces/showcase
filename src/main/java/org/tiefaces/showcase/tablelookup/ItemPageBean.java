package org.tiefaces.showcase.tablelookup;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.tiefaces.common.ColumnModel;
import com.tiefaces.components.common.lookup.TieTableLookupBean;

@ManagedBean
@ViewScoped
public class ItemPageBean  {


	private Item item1 = null;
	private Item item2 = null;

	public void selectSearchResultForItem1(SelectEvent event) {
        Item item = (Item) event.getObject();
		if (item != null) {
			this.setItem1(item);
		}
	}

	public void selectSearchResultForItem2(SelectEvent event) {
        Item item = (Item) event.getObject();
		if (item != null) {
			this.setItem2(item);
		}
	}
	public void selectSearchResultForItem2(Object result) {
		if (result != null) {
			this.setItem2((Item) result);
		}
	}
	
	public Item getItem1() {
		return item1;
	}

	public void setItem1(Item item1) {
		this.item1 = item1;
	}

	public Item getItem2() {
		return item2;
	}

	public void setItem2(Item item2) {
		this.item2 = item2;
	}


}
