package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	public Connection getConnection() {
		
		String url = "jdbc:mysql://localhost/livraria";
		String driver = "com.mysql.jdbc.Driver";
		String user = "Henrique";
		String password = "200901";
		try {
			Class.forName(driver);
			return DriverManager.getConnection(url, user, password);
		} catch(SQLException | ClassNotFoundException  e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String... args) throws SQLException {
		Connection conn = new ConnectionFactory().getConnection();
		System.out.println("Banco conectado!");
		conn.close();
	}
}
