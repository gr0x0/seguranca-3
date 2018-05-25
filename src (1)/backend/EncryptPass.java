package backend;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class EncryptPass {

	public static String encrypt(String pass) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException{		
		
		byte[] plainText = pass.getBytes("UTF8");
		// get a message digest object using the MD5 algorithm
	    MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
	    //
	    // print out the provider used
	    System.out.println( "\n" + messageDigest.getProvider().getInfo() );
	    //
	    // calculate the digest and print it out
	    messageDigest.update( plainText);
	    byte [] digest = messageDigest.digest();
	    System.out.println( "\nDigest length: " + digest.length * 8 + "bits" );

	    // converte o digist para hexadecimal
	    StringBuffer buf = new StringBuffer();
	    for(int i = 0; i < digest.length; i++) {
	       String hex = Integer.toHexString(0x0100 + (digest[i] & 0x00FF)).substring(1);
	       buf.append((hex.length() < 2 ? "0" : "") + hex);
	    }

	    // imprime o digest em hexadecimal
	    System.out.println( "\nDigest(hex): " );
	    System.out.println( buf.toString() );
		
		return(buf.toString());
	}
}
