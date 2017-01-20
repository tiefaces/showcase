package org.tiefaces.showcase.websheet;

import java.io.InputStream;


import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.tiefaces.components.websheet.TieWebSheetBean;

import javax.faces.view.ViewScoped;
import javax.inject.Named;


@Named
@ViewScoped
public class WebSheetUpload extends TieWebSheetBean {

	private static final long serialVersionUID = 1L;

	public void doLoadFileAction(FileUploadEvent event) {

		UploadedFile uploadedFile = event.getFile();
		if (uploadedFile != null) {
			try {
				InputStream stream = uploadedFile.getInputstream();
				loadWebSheet(stream);
			} catch (Exception e) {
				System.out.println("Load File failed. Exception = "
						+ e.getLocalizedMessage());
			}
		}
		return;
	}

	public void refreshAfter() {
		// do nothing
	}

}
