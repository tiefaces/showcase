/*
 * Copyright 2015 TieFaces.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.tiefaces.showcase;

import java.io.InputStream;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean
public class Download {

	private StreamedContent file;

	public Download() {
		String tieVersion = org.tiefaces.common.AppUtils.getBuildVersion(); 
				
		InputStream stream = ((ServletContext) FacesContext
				.getCurrentInstance().getExternalContext().getContext())
				.getResourceAsStream("https://search.maven.org/remotecontent?filepath=org/tiefaces/tiefaces/"+ tieVersion
						+ "/tiefaces-"+tieVersion+".jar");
		file = new DefaultStreamedContent(stream, "application/zip",
				"tiefaces-" + tieVersion + ".jar");
	}

	public StreamedContent getFile() {
		return file;
	}
	
	private String downloadLink = null;

	public String getDownloadLink() {
		if (downloadLink == null) {
			String tieVersion = org.tiefaces.common.AppUtils.getBuildVersion(); 
			downloadLink = "http://central.maven.org/maven2/org/tiefaces/tiefaces/"+ tieVersion
					+ "/tiefaces-"+tieVersion;
		}
		return downloadLink;
	}

	public void setDownloadLink(String downloadLink) {
		this.downloadLink = downloadLink;
	}
	
}