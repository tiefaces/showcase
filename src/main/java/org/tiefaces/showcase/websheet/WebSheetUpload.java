package org.tiefaces.showcase.websheet;

import java.io.InputStream;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;







import com.tiefaces.components.websheet.TieWebSheetBean;

@ManagedBean
@ViewScoped
public class WebSheetUpload extends TieWebSheetBean {

	private static final long serialVersionUID = 1L;

	public void doLoadFileAction(FileUploadEvent event)    {
		
		UploadedFile uploadedFile = event.getFile();
		if(uploadedFile != null) {		
			try {
				InputStream stream = uploadedFile.getInputstream();
				loadWebSheet(stream);
			} catch (Exception e) {
				System.out.println("Load File failed. Exception = "+e.getLocalizedMessage());
			}
		}		
		return ;
	}		
	
	public void refreshAfter()   {
		// do nothing
	}	
	
}
