package org.tiefaces.showcase.tablelookup;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ItemSearchHelper {

	public ItemSearchHelper() {
		super();
	}


	private static String assembleWhereClause(String whereclause, String fieldclause, String value) {
		
		if ((value!=null)&&(!value.isEmpty())) {
		Object[] params={value};
		String sql = new MessageFormat(fieldclause).format(params, new StringBuffer(), null).toString();
		if ((whereclause != null)&&(whereclause.length()>0)) {
			whereclause += " and "+sql; 
		} else {
			whereclause = "where "+sql;
		}
		}
		return whereclause;	
	}
	
	public static List<Object> dosearch(Object searchItem) {
		// cast input object

		List<Object> result = new ArrayList<Object>();
		if (searchItem== null) {
			System.out.println(" search item is null return");
			return result;
		}
		Item item = (Item) searchItem;
		//create sql where clause
		String whereclause="";
		whereclause = assembleWhereClause(whereclause, " upper(code) like upper(''%{0}%'')", item.getCode());
		whereclause = assembleWhereClause(whereclause, " upper(name) like upper(''%{0}%'')", item.getName());
		whereclause = assembleWhereClause(whereclause, " upper(description) like upper(''%{0}%'')", item.getDescription());
		if (item.getPrice() != null) {
		whereclause = assembleWhereClause(whereclause, " price = {0}", item.getPrice().toString());
		}
		
		Connection conn = null;
		try {
		    Context initialContext = new InitialContext();
		    DataSource datasource = (DataSource)initialContext.lookup("java:jboss/datasources/ExampleDS");
		    conn = datasource.getConnection();
		    Statement stmt = conn.createStatement() ;
		    String query = "select * from pricestable "+whereclause ;
		    ResultSet rs = stmt.executeQuery(query) ;
		    while (rs.next()) {
				 Item dbitem = new Item();
				 dbitem.setCode(rs.getString(1));
				 dbitem.setName(rs.getString(2));
				 dbitem.setDescription(rs.getString(3));
				 dbitem.setPrice(rs.getDouble(4));
				 result.add(dbitem);
		    }
			rs.close();
			stmt.close();
		} catch (Exception ex) {
		    System.out.println("Exception: " + ex + ex.getMessage());
		} finally {
			try { conn.close(); } catch (Exception e) { /* ignored */ }
		}		
		return result;
	}	
	
	
}
