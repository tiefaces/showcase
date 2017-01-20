package org.tiefaces.showcase.websheet;

import java.io.InputStream;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.tiefaces.components.websheet.TieWebSheetBean;

@Named
@ViewScoped
public class WebSheetViewer extends TieWebSheetBean {

	private static final long serialVersionUID = 1L;

	@Override
	public void initialLoad() {
		InputStream stream = this.getClass().getClassLoader().getResourceAsStream("websheet/Simple Budget Planner.xlsx");
		loadWebSheet(stream);		
	}

}
