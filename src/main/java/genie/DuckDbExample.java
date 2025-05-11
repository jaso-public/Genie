package genie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DuckDbExample {

	public static void main(String[] args) throws SQLException {
		Properties connectionProperties = new Properties();
		//connectionProperties.setProperty("temp_directory", "/tmp/");
		Connection conn = DriverManager.getConnection("jdbc:duckdb:/Users/jaso/duckdb/my_database", connectionProperties);
		
		// create a table
		Statement stmt = conn.createStatement();
		stmt.execute("CREATE TABLE items (item VARCHAR, value DECIMAL(10, 2), count INTEGER)");
		
		// insert two items into the table
		stmt.execute("INSERT INTO items VALUES ('jeans', 20.0, 1), ('nail', 42.2, 2), ('hammer', 42.2, 2)");

		stmt.execute("CREATE TABLE table2 (id VARCHAR, value DECIMAL(10, 2), count INTEGER)");
		
		// insert two items into the table
		stmt.execute("INSERT INTO table2 VALUES ('jeans', 20.0, 1), ('nail', 42.2, 2), ('hammer', 42.2, 2)");

		try (ResultSet rs = stmt.executeQuery("SELECT * FROM items, table2 where items.item=table2.id")) {
			
		    ResultSetMetaData meta = rs.getMetaData();
		    int columnCount = meta.getColumnCount();

		    while (rs.next()) {
		        StringBuilder row = new StringBuilder();
		        for (int i = 1; i <= columnCount; i++) {
		            row.append(rs.getString(i));
		            if (i < columnCount) row.append(" | ");
		        }
		        System.out.println(row);
		    }
		}
		stmt.close();
	}

}
