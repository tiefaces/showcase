package org.tiefaces.showcase.tablelookup;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.tiefaces.common.ColumnModel;
import com.tiefaces.components.common.lookup.tableLookupBean;

@ManagedBean
@ViewScoped
public class ItemSearchBean extends tableLookupBean {

	@Override
	protected void init() {
		
		this.setSearchItem(new Item());
		this.setColumns(new ArrayList<ColumnModel>());
		this.getColumns().add(new ColumnModel("Code", "code", "width:100px"));
		this.getColumns().add(new ColumnModel("Name", "name", "width:150px"));
		this.getColumns().add(new ColumnModel("Description", "description", "width:150px"));
		this.getColumns().add(new ColumnModel("Price", "price", "width:150px;text-align:right;"));
	}


	@Override
	public void search() {
		
		this.setResults(ItemSearchHelper.dosearch(this.getSearchItem()) );
		
	}






}
