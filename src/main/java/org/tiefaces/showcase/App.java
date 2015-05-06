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


import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.tiefaces.utility.FacesUtility;




@ManagedBean(eager=true)
@ApplicationScoped
public class App {

	private static final String SHOWCASE_PATH = "/showcase/";
	
	private String poweredBy;
	private String version;
	private boolean snapshot;
	private Map<String, Page> pages;
	

	
	@PostConstruct
	public void init() {
		version = initVersion();
		snapshot = version.contains("-SNAPSHOT") || version.contains("-RC");
		poweredBy = initPoweredBy();
		initPage();
		//testds();
	}
	
//	private void testds() {
//		
//		Connection result = null;
//		try {
//		    Context initialContext = new InitialContext();
//		    DataSource datasource = (DataSource)initialContext.lookup("java:jboss/datasources/MySQLDS");
//		    result = datasource.getConnection();
//		    Statement stmt = result.createStatement() ;
//		    String query = "select * from pricestable" ;
//		    ResultSet rs = stmt.executeQuery(query) ;
//		    while (rs.next()) {
//		        System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + "<br />");
//		    }
//		} catch (Exception ex) {
//		    System.out.println("Exception: " + ex + ex.getMessage());
//		}		
//	}
	
	private void initPage() {
		pages = new HashMap<>();
		Set<String> resourcePaths = FacesUtility.getResourcePaths(SHOWCASE_PATH);
		Set<String> groupPaths = new TreeSet<>(resourcePaths);

		for (String groupPath : groupPaths) {
			String groupName = groupPath.split("/")[2];
			Set<String> pagePaths = new TreeSet<>(FacesUtility.getResourcePaths(groupPath));

			for (String pagePath : pagePaths) {
				String viewId = FacesUtility.stripPrefixPath(SHOWCASE_PATH, pagePath.split("\\.")[0]);
				String title = viewId.split("/")[2];
				pages.put(pagePath, new Page(pagePath, viewId, title));
			}
		}
	}

	private String initVersion() {
		String version = "0.1";
		return version.replaceAll("-\\d+$", "");
	}

	public String getPoweredBy() {
		return poweredBy;
	}

	public String getVersion() {
		return version;
	}

	private String initPoweredBy() {
		return String.format("%s%nTieFaces %s%nPrimeFaces %s%n",
			"Mojarra-2.2.8",
			getVersion(),
			"5.1");
	}

	public boolean isSnapshot() {
		return snapshot;
	}	
	
	static Elements scrape(String url, String selector) throws IOException {
		System.setProperty("http.proxyHost", "206.177.43.90");
		System.setProperty("http.proxyPort", "3128");		
		System.setProperty("https.proxyHost", "206.177.43.90");
		System.setProperty("https.proxyPort", "3128");		
		return Jsoup.connect(url).userAgent("OmniBot 0.1 (+http://showcase.omnifaces.org)").get().select(selector);
	}	
	
	public Map<String, Page> getPages() {
		return pages;
	}
	
	
}
