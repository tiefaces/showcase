package com.tiefaces.components.common.lookup;

import java.util.List;

import javax.annotation.PostConstruct;

import org.tiefaces.showcase.tablelookup.Item;

import com.tiefaces.common.ColumnModel;

public abstract class tableLookupBean {

		private List<ColumnModel> columns;
		private List<Object> results;
		private Object searchItem;

		public tableLookupBean() {
			super();
		}

		@PostConstruct
		public void postinit() {
			init();
		}
		
		protected abstract void init();

		public abstract void search();

		public List<ColumnModel> getColumns() {
			return columns;
		}

		public void setColumns(List<ColumnModel> columns) {
			this.columns = columns;
		}

		public List<Object> getResults() {
			return results;
		}

		public void setResults(List<Object> results) {
			this.results = results;
		}

		public Object getSearchItem() {
			return searchItem;
		}

		public void setSearchItem(Object searchItem) {
			this.searchItem = searchItem;
		}
		
		public abstract void selectSearchResult(Object result);

	}
