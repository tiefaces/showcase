package org.tiefaces.showcase.websheet;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.tiefaces.components.websheet.TieWebSheetBean;
import org.tiefaces.showcase.tablelookup.Item;

@Named
@ViewScoped
public class WebSheetValidation extends TieWebSheetBean {

	private static final long serialVersionUID = 1L;

	private List<Item> itemList = null;
	
	@Override
	public void initialLoad() {
		
		itemList = 	new ArrayList<Item>();
		itemList.add(new Item());
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("items", itemList);
		InputStream stream = this.getClass().getClassLoader().getResourceAsStream("websheet/PRICELISTINPUTVALIDATION.xlsx");
		loadWebSheet(stream, context);				
	}

}
