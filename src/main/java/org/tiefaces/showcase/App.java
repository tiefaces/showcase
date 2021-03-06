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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.config.PrimeConfiguration;
import org.tiefaces.common.FacesUtility;

@ManagedBean(eager = true)
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
		initData();
	}

	// For file: new FileReader (inputfile)
	// FOR Inputstream: new InputStreamReader( inputfile, "UTF8")

	private void initPage() {
		pages = new HashMap<>();
		Set<String> resourcePaths = FacesUtility
				.getResourcePaths(SHOWCASE_PATH);
		Set<String> groupPaths = new TreeSet<>(resourcePaths);

		for (String groupPath : groupPaths) {
			//String groupName = groupPath.split("/")[2];
			Set<String> pagePaths = new TreeSet<>(
					FacesUtility.getResourcePaths(groupPath));

			for (String pagePath : pagePaths) {
				String viewId = FacesUtility.removePrefixPath(SHOWCASE_PATH,
						pagePath.split("\\.")[0]);
				String title = viewId.split("/")[2];
				pages.put(pagePath, new Page(pagePath, viewId, title));
			}
		}
	}

	// private ScheduledExecutorService scheduler;
	// private void initScheduler() {
	// scheduler = Executors.newSingleThreadScheduledExecutor();
	// scheduler.scheduleAtFixedRate(new DbJobs(getJNDIDataSource()), 0, 20,
	// TimeUnit.MINUTES);
	// }

	// looks like don't need refresh data source any more.
	// So use this to run the job one at start
	private void initData() {

	}

	/*
	private DataSource getJNDIDataSource() {
		String DATASOURCE_CONTEXT = "java:jboss/datasources/ExampleDS";

		DataSource datasource = null;
		try {
			Context initialContext = new InitialContext();
			datasource = (DataSource) initialContext
					.lookup(DATASOURCE_CONTEXT);

			if (datasource == null) {
				System.out.println("Failed to lookup datasource.");
			}
		} catch (NamingException ex) {
			System.out.println("Cannot get connection: " + ex);
		}
		return datasource;
	}
*/
	private String initVersion() {
		String version = org.tiefaces.common.AppUtils.getBuildVersion();
		return version.replaceAll("-\\d+$", "");
	}

	public String getPoweredBy() {
		return poweredBy;
	}

	public String getVersion() {
		return version;
	}

	private String initPoweredBy() {
	    try{
				String primeVersion = new PrimeConfiguration(FacesContext.getCurrentInstance()).getBuildVersion();
				String tieVersion = org.tiefaces.common.AppUtils.getBuildVersion();
				String jsfVersion = FacesContext.class.getPackage()
						.getImplementationVersion();				
				return String.format("%s   TieFaces %s   PrimeFaces %s",
						"Mojarra-" + jsfVersion, tieVersion, primeVersion);
			} catch (Exception e) {
				e.printStackTrace();
			}
		return "";
	}

	public boolean isSnapshot() {
		return snapshot;
	}

	public Map<String, Page> getPages() {
		return pages;
	}

}
