package org.tiefaces.showcase.websheet;

import java.io.InputStream;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.tiefaces.components.websheet.TieWebSheetBean;

@ManagedBean
@ViewScoped
public class WebSheet2 extends TieWebSheetBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Override
	public void initialLoad() {
		InputStream stream = this.getClass().getClassLoader().getResourceAsStream("websheet/CCAC1.xls");
		loadWebSheet(stream);		
	}

}
