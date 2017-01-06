package org.tiefaces.showcase.tablelookup;

import java.util.ArrayList;
import java.util.List;

public class ItemList {
	
	private List<Object> itemList = null;
	public ItemList() {
		super();
		 itemList = new ArrayList<Object>();
		 itemList.add(new Item("GC1020", "BMW", "BMW Car Black", "85438"));
		 itemList.add(new Item("GC1021", "Fiat", "Fiat Green ", "29328"));
		 itemList.add(new Item("GC1022", "Jaguar", "Jaguar Car Red", "98289"));
		 itemList.add(new Item("GC1040", "Mercedes", "Mercedes Car Blue", "99990"));
		 itemList.add(new Item("GC1060", "BMW", "BMW White", "75000"));
		 itemList.add(new Item("GC3020", "IBM Think Pad", "IBM V1200", "10200"));
		 itemList.add(new Item("GC3040", "IPAD AIR", "IPAD AIR V3.0", "38000"));
		 itemList.add(new Item("GC3060", "SOFTWARE", "WINDOWS 10", "95000"));
		 itemList.add(new Item("GC5020", "IPHONE", "IPHONE V11", "28000"));
		 itemList.add(new Item("GC5040", "IWATCH", "IWATCH V12", "59900"));
		 itemList.add(new Item("GC5060", "IBOOK", "IBOOK V15", "110000"));
		 itemList.add(new Item("IN7020", "Jaguar", "Jaguar Car Blue", "4000"));
		 itemList.add(new Item("IN7040", "Mercedes", "Mercedes Car White", "16000"));
		 itemList.add(new Item("IN7060", "BMW", "BMW Red", "42000"));
		 itemList.add(new Item("IN7080", "SOFTWARE", "WINDOWS x", "69000"));
		 itemList.add(new Item("SL9020", "IPAD AIR", "IPAD AIR V2.0", "4999"));
		 itemList.add(new Item("SL9040", "BMW", "BMW Black", "9999"));
		 itemList.add(new Item("SL9060", "IPHONE", "IPHONE Golden V2.0", "14999"));
		 itemList.add(new Item("SL9080", "IPHONE", "IPHONE Golden V2.2", "19999"));		 

	}
	public List<Object> getItemList() {
		return itemList;
	}
	
	

}
