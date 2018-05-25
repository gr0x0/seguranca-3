package backend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Signup {
	
	public static boolean sign(String path, int idgroup, String password) throws SQLException{
		ResultSet rs = null;
		Random generate = new Random();
		String file = null;
		String salt = StringRand.generateVerificationCode();
		password = password + salt;
		
		String pass = null;
		
		if(hasLogin(path)){
			return false;
		}
		
		file = getFile(path);
		
		System.out.println(password);
		
		try {
			pass = EncryptPass.encrypt(password);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Connect.getStmt().executeUpdate("INSERT INTO USERS (certificate, password, salt, access, countlist, consult) VALUES ('"+ file +"','" + pass + "','" + salt +"', " + 0 + ", " + 0 + ", " + 0 + ")");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			rs = Connect.getStmt().executeQuery("SELECT * FROM USERS WHERE password = '" + pass + "'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Connect.getStmt().executeUpdate("INSERT INTO GRUPO(ID, IDUSER) VALUES (" + idgroup + "," + rs.getString("ID") + ")");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
	
	public static boolean hasLogin(String path) throws SQLException{
		ResultSet rs = null;
		String email, emailfile = null;
		
	    emailfile = getFile(path);
	    if(emailfile == null){
	    	return true;
	    }
	    emailfile = TakeEmail(emailfile);
		

		try {
			rs = Connect.getStmt().executeQuery("SELECT * FROM USERS");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while (rs.next()) {
			String certificate = rs.getString("CERTIFICATE");
			
			email = TakeEmail(certificate);  
			
			if(email.equals(emailfile)){
				return true;
			}
		}		
		return false;
	}
	
	public static String getFile(String path){
		String file = null;
		
		File arq = new File(path);
		if(!arq.exists()){
			ControladorBackend.registrar(6004);
	    	return null;
	    }
	    byte [] vet = getBytes(arq);
	    try {
			file = new String(vet, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return file;
	}
	
	public static byte[] getBytes(File file) {
		int len = (int)file.length();  
		byte[] sendBuf = new byte[len];
		FileInputStream inFile = null;
		try {
			inFile = new FileInputStream(file);         
			inFile.read(sendBuf, 0, len);  
		} catch (FileNotFoundException fnfex) {

		} catch (IOException ioex) {

		}
		return sendBuf;
	}
	
	public static String TakeEmail(String certificate){
		String email=null;
		email = certificate.substring(certificate.indexOf("Subject:"), certificate.indexOf("Subject Public"));
		email = email.substring(email.lastIndexOf("emailAddress=")+13);
		email = email.trim();
		
		return email;
	}
	
}
