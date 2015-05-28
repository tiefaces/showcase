package org.tiefaces.showcase.websheet;

import java.io.InputStream;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import com.tiefaces.components.websheet.TieWebSheetBean;

@ManagedBean
@ViewScoped
public class WebSheet2 extends TieWebSheetBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public InputStream loadWebSheetTemplate() {
		InputStream stream = this.getClass().getClassLoader().getResourceAsStream("websheet/PRICELISTWITHOUT.xls");
	//	((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream ("/WEB-INF/lib/tiefaces-"+version+".jar");
		return stream;         
	}

	@Override
	public void initialLoad() {
		loadWebSheet();		
	}

}
