package org.tiefaces.showcase.websheet;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.tiefaces.components.websheet.TieWebSheetBean;
import org.tiefaces.showcase.tablelookup.Item;



@Named
@ViewScoped
public class WebSheetCenterValidation extends TieWebSheetBean {

	private static final long serialVersionUID = 1L;

	private List<Item> itemList = null;
	
	@Override
	public void initialLoad() {
		
		itemList = 	new ArrayList<Item>();
		itemList.add(new Item());
		HashMap<String, Object> context = new HashMap<String, Object>();
		context.put("items", itemList);
		InputStream stream = this.getClass().getClassLoader().getResourceAsStream("websheet/ServerCenterValidation.xlsx");
		loadWebSheet(stream, context);
		this.setTieWebSheetValidationBean(new ValidationBean(),false);

	}
		
}
