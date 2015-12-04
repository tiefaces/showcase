package org.tiefaces.showcase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.tiefaces.components.common.sql.SQLRunner;

public class DbJobs {
	// implements Runnable
	private DataSource datasource = null;

	public DbJobs(DataSource datasource) {
		super();
		this.datasource = datasource;
	}

	private String readFile(Reader input) throws IOException {
		BufferedReader reader = new BufferedReader(input);
		String line = null;
		StringBuilder stringBuilder = new StringBuilder();
		String ls = System.getProperty("line.separator");

		while ((line = reader.readLine()) != null) {
			stringBuilder.append(line);
			stringBuilder.append(ls);
		}
		return stringBuilder.toString();
	}

	// @Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println(" i am running ......");
		System.out.println(" current datasource = " + datasource);
		Connection connection = null;
		try {
			connection = datasource.getConnection();
			System.out.println(" conneccted succefully connection = "
					+ connection);

			String sql = readFile(new InputStreamReader(this.getClass()
					.getClassLoader()
					.getResourceAsStream("sql/pricestable.sql"), "UTF8"));
			SQLRunner runner = new SQLRunner(connection, false);
			String results = runner.runSQLs(sql);
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (Exception e) { /* ignored */
			}
		}

	}

}
