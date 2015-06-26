package org.tiefaces.showcase.tablelookup;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.tiefaces.common.ColumnModel;
import com.tiefaces.components.common.lookup.TieTableLookupBean;

@ManagedBean
@ViewScoped
public class ItemSearchBean extends TieTableLookupBean {

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
	public List<Object> search() {
		
		return ItemSearchHelper.dosearch(this.getSearchItem());
		
	}


	// Below are only used for pop up window search
	private Item selectedItem = null;
	@Override
	public void selectSearchResult(Object result) {
		if (result != null) {
			this.setSelectedItem((Item) result);
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('tielookupDialog').hide();");
			context.update("form:panel");
		}
	}

	public Item getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(Item selectedItem) {
		this.selectedItem = selectedItem;
	}

}
