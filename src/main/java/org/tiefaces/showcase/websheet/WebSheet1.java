package org.tiefaces.showcase.websheet;

import java.io.InputStream;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.tiefaces.components.websheet.TieWebSheetBean;

@ManagedBean
@ViewScoped
public class WebSheet1 extends TieWebSheetBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -516253181041018373L;

	/**
	 * 
	 */
	

	@Override
	public InputStream loadWebSheetTemplate() {
		InputStream stream = this.getClass().getClassLoader().getResourceAsStream("websheet/CCAC2.xls");
	//	((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream ("/WEB-INF/lib/tiefaces-"+version+".jar");
		return stream;         
	}

	@Override
	public void initialLoad() {
		loadWebSheet();		
	}

}
