package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
	private static Connection connect;
	private static java.sql.Statement stmt;
	
	public Connect(String src) throws ClassNotFoundException{
		// load the sqlite-JDBC driver using the current class loader
	    Class.forName("org.sqlite.JDBC");
	    try {
			connect = DriverManager.getConnection("jdbc:sqlite:"+src);
			setStmt(connect.createStatement());
			System.out.println("Conectado no banco");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Não foi possivel conectar ao banco");
		}
	}
	
	public static Connection getConnect() {
		return connect;
	}

	public static java.sql.Statement getStmt() {
		return stmt;
	}

	public static void setStmt(java.sql.Statement stmt) {
		Connect.stmt = stmt;
	}
	
	public static void Close() {
		try {
			connect.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Erro Fechamento de Conexão");
		}
	}
	
}
