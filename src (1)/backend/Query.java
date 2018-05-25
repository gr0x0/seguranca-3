package backend;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Query {

	public static boolean setConsult(String iduser) throws SQLException{
		int aux = getConsult(iduser);
		aux++;
		Connect.getStmt().executeUpdate("UPDATE USERS SET consult =" + aux + " WHERE ID = " + iduser);
		return(true);
	}

	public static boolean setCountList(String iduser) throws SQLException{
		int aux = getCountList(iduser);
		aux++;
		Connect.getStmt().executeUpdate("UPDATE USERS SET countlist =" + aux + " WHERE ID = " + iduser);
		return(true);
	}

	public static boolean setAccess(String iduser) throws SQLException{
		int aux = getAccess(iduser);
		aux++;
		Connect.getStmt().executeUpdate("UPDATE USERS SET access =" + aux + " WHERE ID = " + iduser);
		return(true);
	}

	public static int getConsult(String iduser) throws SQLException{
		ResultSet rs = null;
		try {
			rs = Connect.getStmt().executeQuery("SELECT * FROM USERS WHERE ID = "+ iduser);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return(rs.getInt("consult"));
	}

	public static int getCountList(String iduser) throws SQLException{
		ResultSet rs = null;
		try {
			rs = Connect.getStmt().executeQuery("SELECT * FROM USERS WHERE ID = "+ iduser);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return(rs.getInt("countlist"));
	}

	public static int getAccess(String iduser) throws SQLException{
		ResultSet rs = null;
		try {
			rs = Connect.getStmt().executeQuery("SELECT * FROM USERS WHERE ID = "+ iduser);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return(rs.getInt("access"));
	}


	public static int getGroup(String iduser) throws SQLException{
		ResultSet rs = null;
		try {
			rs = Connect.getStmt().executeQuery("SELECT * FROM GRUPO WHERE IDUSER = "+ iduser);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return(rs.getInt("ID"));

	}

	public static String getCountUsers() throws SQLException{
		ResultSet rs = null;
		try {
			rs = Connect.getStmt().executeQuery("SELECT count(*) as numero FROM USERS");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return(rs.getString("numero"));

	}

	public static void getReg() throws SQLException{
		ResultSet rs = null;
		rs = Connect.getStmt().executeQuery("SELECT * FROM REG "
				+ "LEFT JOIN USERS ON USERS.ID = REG.IDUSER "
				+ "INNER JOIN MESSAGES ON MESSAGES.ID = REG.IDMESSAGE ORDER BY REG.ID");
		while (rs.next()) {
			String s = rs.getString("certificate");
			String message = rs.getString("MESSAGE");
			String date = rs.getString("DATE");
			String arquivo = rs.getString("ARQ");
			String email = null;
			if(s == null){
			}else{
				email = s.substring(s.indexOf("Subject:"), s.indexOf("Subject Public"));
				email = email.substring(email.lastIndexOf("emailAddress=")+13);
				email = email.trim();
				message = message.replaceAll("<login_name>", email);
			}
			message = message.replaceAll("<arq_name>", arquivo);
			System.out.println(date + " " + rs.getString("IDMESSAGE") + " " + message);
		}	
	}

	public static boolean setReg(int id, int idmessage, String nameArq){
		try {
			Connect.getStmt().executeUpdate("INSERT INTO REG (IDUSER, IDMESSAGE, DATE, ARQ) VALUES ("+ id + "," + idmessage + ",DATETIME(CURRENT_TIMESTAMP),'" + nameArq + "')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return false;
		}
		return true;
	}

}
