package backend;

import java.security.cert.CertificateException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.security.cert.X509Certificate;

class Login {
	
	protected static Account VerifyAcc(String login) throws SQLException, CertificateException
	{
		ResultSet rs;
		int beginindex, endindex;
		String aux;

		rs = Connect.getStmt().executeQuery("SELECT * FROM USERS");

		while (rs.next()) {
			String s = rs.getString("CERTIFICATE");
			
			beginindex = s.indexOf("Subject:");
			endindex = s.indexOf("Subject Public");

			aux = s.substring(beginindex, endindex);
			aux = aux.substring(aux.lastIndexOf("emailAddress=")+13);

			aux = aux.trim();
			if(aux.equals(login)){
				System.out.println("LOGADO");
				Certificate.convertToX509Cert(s);
				Account acc = new Account(rs.getInt("id"), rs.getString("password"), Certificate.convertToX509Certificate(rs.getString("certificate")));
				return(acc);
			}
		}
		
		return null;
	}
}
