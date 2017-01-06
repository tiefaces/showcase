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

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;
import javax.faces.FacesException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.tiefaces.common.FacesUtility;

@ManagedBean
@RequestScoped
public class Page {

	// Constants
	// ------------------------------------------------------------------------------------------------------

	private static final long serialVersionUID = 1L;
	private static final String API_PATH = "org/tiefaces/";
	private static final String ERROR_LOADING_PAGE_DESCRIPTION = "Unable to load description of %s";
	private static final String ERROR_LOADING_PAGE_SOURCE = "Unable to load source code of %s";

	// Properties
	// -----------------------------------------------------------------------------------------------------

	private String title;
	private String path;
	private String viewId;
	private String description;
	private List<Source> sources;
	private Documentation documentation;

	@ManagedProperty("#{app.pages[view.viewId]}")
	private Page current;
	private AtomicBoolean loaded = new AtomicBoolean();

	// Constructors
	// ---------------------------------------------------------------------------------------------------

	public Page() {
		// Keep default c'tor alive for JSF @ManagedBean.
	}

	public Page(String title) {
		this.title = title;
	}

	public Page(String path, String viewId, String title) {
		this.path = path;
		this.viewId = viewId;
		this.title = title;
	}

	// Initialization
	// -------------------------------------------------------------------------------------------------

	@PostConstruct
	public void init() {
		checkCurrent();
		if (current != null) {
			current.loadIfNecessary();
		}
	}

	private void checkCurrent() {
		if (current == null) {
			String viewId = FacesUtility.getContext().getViewRoot().getViewId();
		}

	}

	private void loadIfNecessary() {
		if (loaded.getAndSet(true)) {
			return;
		}

		try {
			Map<String, Object> attributes = FacesUtility
					.getMetadataAttributes();
			List<String> apiPaths = new ArrayList<>(getCommaSeparatedAttribute(
					attributes, "api.path"));
			List<String> vdlPaths = getCommaSeparatedAttribute(attributes,
					"vdl.paths");
			List<String> srcPaths = getCommaSeparatedAttribute(attributes,
					"src.paths");
			// description = loadDescription(apiPaths);
			sources = loadSources(path, srcPaths);
			// documentation = (apiPaths.size() + vdlPaths.size() > 0) ? new
			// Documentation(apiPaths, vdlPaths) : null;
		} catch (Exception e) {
			loaded.set(false);
			throw e;
		}
	}

	private static List<String> getCommaSeparatedAttribute(
			Map<String, Object> attributes, String key) {
		String attribute = (String) attributes.get(key);

		if (attribute == null) {
			return emptyList();
		}

		return asList(attribute.trim().split("\\s*,\\s*"));
	}



	private static List<Source> loadSources(String pagePath,
			List<String> srcPaths) {
		List<Source> sources = new ArrayList<>(1 + srcPaths.size());
		String sourceCode = loadSourceCode(pagePath);
		String[] meta = sourceCode.split("<ui:define name=\"demo-meta\">");
		String[] demo = sourceCode.split("<ui:define name=\"demo\">");
		StringBuilder demoSourceCode = new StringBuilder();

		if (meta.length > 1) {
			// Yes, ugly, but it's faster than a XML parser and it's internal
			// code anyway.
			demoSourceCode.append(meta[1].split("</ui:define>")[0].trim())
					.append("\n\n");
		}

		if (demo.length > 1) {
			demoSourceCode.append(demo[1].split("</ui:define>")[0].trim());
		}

		if (demoSourceCode.length() > 0) {
			// The 8 leading spaces are trimmed so that the whole demo code
			// block is indented back.
			String code = demoSourceCode.toString().replace("\n        ", "\n")
					.trim();
			sources.add(new Source("Demo", "xhtml", code));
		}

		for (String srcPath : srcPaths) {
			String title = srcPath;

			if (title.contains("/")) {
				title = title.substring(title.lastIndexOf('/') + 1,
						title.length());
			}

			if (title.endsWith(".java")) {
				title = title.substring(0, title.indexOf('.'));
			}

			String type = srcPath.substring(srcPath.lastIndexOf('.') + 1);
			String code = loadSourceCode((srcPath.startsWith("/") ? ""
					: "/sources/") + srcPath);
			sources.add(new Source(title, type, code));
		}

		return sources;
	}

	private static String loadSourceCode(String path) {
		InputStream input = FacesUtility.getResourceAsStream(path);

		if (input == null) {
			return "Source code is not available at " + path;
		}

		try {
			return new String(FacesUtility.toByteArray(input), "UTF-8")
					.replace("\t", "    "); // Tabs are in HTML <pre> presented
											// as 8 spaces, which is too much.
		} catch (IOException e) {
			throw new FacesException(String.format(ERROR_LOADING_PAGE_SOURCE,
					path), e);
		}
	}

	// Getters/setters
	// ------------------------------------------------------------------------------------------------

	public String getTitle() {
		return title;
	}

	public String getPath() {
		return path;
	}

	public String getViewId() {
		return viewId;
	}

	public String getDescription() {
		return description;
	}

	public List<Source> getSources() {
		return sources;
	}

	public Documentation getDocumentation() {
		return documentation;
	}

	public Page getCurrent() {
		return current;
	}

	public void setCurrent(Page current) {
		this.current = current;
	}

	// Object overrides
	// -----------------------------------------------------------------------------------------------

	@Override
	public boolean equals(Object other) {
		return (other instanceof Page) && (title != null) ? title
				.equals(((Page) other).title) : (other == this);
	}

	@Override
	public int hashCode() {
		return (title != null) ? (getClass().hashCode() + title.hashCode())
				: super.hashCode();
	}

	@Override
	public String toString() {
		return String.format("Page[title=%s,viewId=%s]", title, viewId);
	}

	// Nested classes
	// -------------------------------------------------------------------------------------------------

	/**
	 * This class represents a source code snippet associated with the current
	 * page.
	 *
	 * @author Bauke Scholtz
	 */
	public static class Source implements Serializable {

		// Constants
		// --------------------------------------------------------------------------------------------------

		private static final long serialVersionUID = 1L;

		// Properties
		// -------------------------------------------------------------------------------------------------

		private String title;
		private String type;
		private String code;

		// Contructors
		// ------------------------------------------------------------------------------------------------

		public Source(String title, String type, String code) {
			this.title = title;
			this.type = type;
			this.code = code;
		}

		// Getters/setters
		// --------------------------------------------------------------------------------------------

		public String getTitle() {
			return title;
		}

		public String getType() {
			return type;
		}

		public String getCode() {
			return code;
		}

	}

	/**
	 * This class represents the API and VDL documentation paths associated with
	 * the current page.
	 *
	 * @author Bauke Scholtz
	 */
	public static class Documentation implements Serializable {

		// Constants
		// --------------------------------------------------------------------------------------------------

		private static final long serialVersionUID = 1L;

		// Properties
		// -------------------------------------------------------------------------------------------------

		private List<String> api;
		private List<String> vdl;

		// Contructors
		// ------------------------------------------------------------------------------------------------

		public Documentation(List<String> api, List<String> vdl) {
			this.api = api;
			this.vdl = vdl;
		}

		// Getters/setters
		// --------------------------------------------------------------------------------------------

		public List<String> getApi() {
			return api;
		}

		public List<String> getVdl() {
			return vdl;
		}

	}

}