package org.tiefaces.showcase.websheet;

import java.io.InputStream;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.tiefaces.components.websheet.TieWebSheetBean;

@ManagedBean
@ViewScoped
public class WebSheetValidation_Config extends TieWebSheetBean {

	private static final long serialVersionUID = 1L;

	@Override
	public void initialLoad() {
		InputStream stream = this.getClass().getClassLoader().getResourceAsStream("websheet/PRICELISTINPUTVALIDATION.xls");
		this.setConfigurationTab("None");
		loadWebSheet(stream);		
	}

}
