package org.tiefaces.showcase.websheet;

import java.io.InputStream;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.tiefaces.components.websheet.TieWebSheetBean;

@ManagedBean
@ViewScoped
public class WebSheetNormal extends TieWebSheetBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -516253181041018373L;

	/**
	 * 
	 */


	@Override
	public void initialLoad() {
		InputStream stream = this.getClass().getClassLoader().getResourceAsStream("websheet/PRICELISTWITHOUT.xls");
		loadWebSheet(stream);		
	}

}
