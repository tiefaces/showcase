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


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import com.tiefaces.common.FacesUtility;

import com.tiefaces.components.common.sql.SQLRunner;





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
		testds();
	}
	
	//For file: new FileReader (inputfile)
	//FOR Inputstream:  new InputStreamReader( inputfile, "UTF8")
	private String readFile( Reader  input ) throws IOException {
	    BufferedReader reader = new BufferedReader( input);
	    String         line = null;
	    StringBuilder  stringBuilder = new StringBuilder();
	    String         ls = System.getProperty("line.separator");

	    while( ( line = reader.readLine() ) != null ) {
	        stringBuilder.append( line );
	        stringBuilder.append( ls );
	    }
	    return stringBuilder.toString();
	}
	
	private void testds() {
		
		Connection conn = null;
		try {

		    Context initialContext = new InitialContext();
		    DataSource datasource = (DataSource)initialContext.lookup("java:jboss/datasources/ExampleDS");
		    conn = datasource.getConnection();

			String sql = readFile( new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("sql/pricestable.sql"), "UTF8"));
			SQLRunner runner = new SQLRunner(conn, false);
			String results = runner.runSQLs(sql);
	        System.out.println("sql runner results = "+results);
		} catch (Exception ex) {
		    System.out.println("Exception: " + ex + ex.getMessage());
		} finally {
			try { conn.close(); } catch (Exception e) { /* ignored */ }
		}
	}
	
	
	private void initPage() {
		pages = new HashMap<>();
		Set<String> resourcePaths = FacesUtility.getResourcePaths(SHOWCASE_PATH);
		Set<String> groupPaths = new TreeSet<>(resourcePaths);

		for (String groupPath : groupPaths) {
			String groupName = groupPath.split("/")[2];
			Set<String> pagePaths = new TreeSet<>(FacesUtility.getResourcePaths(groupPath));

			for (String pagePath : pagePaths) {
				String viewId = FacesUtility.removePrefixPath(SHOWCASE_PATH, pagePath.split("\\.")[0]);
				String title = viewId.split("/")[2];
				pages.put(pagePath, new Page(pagePath, viewId, title));
			}
		}
	}

	private String initVersion() {
		String version = "0.2.1";
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
