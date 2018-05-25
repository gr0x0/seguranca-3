package backend;
import java.security.*;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.crypto.*;
//
// Generate a Message Digest
class DigestCalculator {
	
	public static int VerifyPassword(String HexPassHash, String pass, int tam) throws Exception {
		ResultSet rs;
		String salt, NewPass;
		String [] password;
		rs = Connect.getStmt().executeQuery("SELECT * FROM USERS WHERE password = " + "'" + HexPassHash + "'");
		salt = rs.getString("salt");
		
		Combinacao comb2 = new Combinacao(pass.split(""), tam) ;
        while ( comb2.hasNext() ) {
        	password = comb2.next() ;
        	NewPass = "";
            // Itera em todos os elementos da combinacao
            for ( String e : password) {
            	NewPass = NewPass + e;
            }
            
            NewPass = NewPass + salt;
            
            //System.out.println(NewPass);
            
            byte[] PassByte = NewPass.getBytes("UTF8");

            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update(PassByte);

            byte [] NewPassHash = messageDigest.digest();

            // converte o a senha+salt em hash para hexadecimal
            StringBuffer NewHexPass = new StringBuffer();
            for(int i = 0; i < NewPassHash.length; i++) {
            	String hex = Integer.toHexString(0x0100 + (NewPassHash[i] & 0x00FF)).substring(1);
            	NewHexPass.append((hex.length() < 2 ? "0" : "") + hex);
            }
            
            //Compara senhas em Hexadecimal
            if(NewHexPass.toString().equals(HexPassHash)){
            	return 1;
            }
        }
//        System.out.println("TAM: " + tam);
	    return 0;
		
	}
	
	public static String PossiblesPassword(ArrayList<String> passwordValues) throws Exception 
	{
		String digits = "";
		
		for(int i=0; i<passwordValues.size(); i++){
			digits = digits + passwordValues.get(i);
		}
		
		digits = digits.replaceAll("-", "");
		
		System.out.println("Digitos: "+digits);
		
		return digits;
	}
}
