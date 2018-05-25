package backend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

public class DigitalSignature {
	
	static PrivateKey loadPrivateKey(String key64) throws NoSuchAlgorithmException, InvalidKeySpecException{
		key64 = key64.substring(key64.indexOf("-----BEGIN PRIVATE KEY-----\n"));
        key64 = key64.replace("-----BEGIN PRIVATE KEY-----\n", "").replace("-----END PRIVATE KEY-----", ""); // NEED FOR PEM FORMAT CERT STRING
        key64 = key64.trim();
        key64 = key64.replace("\n", "");
		
		byte[] clear = Base64.getDecoder().decode(key64);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(clear);
		System.out.println(Security.getProviders());
		KeyFactory kf = KeyFactory.getInstance("RSA");
		PrivateKey pk = kf.generatePrivate(keySpec);
		return pk;
	}
	
	static PublicKey loadPublicKey(String key64) throws NoSuchAlgorithmException, InvalidKeySpecException{
		byte[] clear = Base64.getDecoder().decode(key64);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(clear);
		KeyFactory kf = KeyFactory.getInstance("DES");
		PublicKey pk = kf.generatePublic(keySpec);
		return pk;
	}
	
	public static String savePrivateKey(PrivateKey priv) throws GeneralSecurityException {
	    KeyFactory fact = KeyFactory.getInstance("RSA");
	    PKCS8EncodedKeySpec spec = fact.getKeySpec(priv,PKCS8EncodedKeySpec.class);
	    byte[] packed = spec.getEncoded();
	    String PK = Base64.getEncoder().encodeToString(packed);
	    return PK;
	}

	
	public static int TestSignature(PrivateKey PrivateKey, PublicKey pubk) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException{
		String test = "This is a test message!";
	    byte[] plainText = test.getBytes("UTF8");
	    
		Signature sig = Signature.getInstance("MD5WithRSA");
	    sig.initSign(PrivateKey);
	    sig.update(plainText);
	    byte[] signature = sig.sign();
	    
	    // verifica a assinatura com a chave publica
	    System.out.println( "\nStart signature verification" );
	    sig.initVerify(pubk);
	    sig.update(plainText);
	    try {
	      if (sig.verify(signature)) {
	    	  System.out.println( "Signature verified" );
	    	  return 1;
	      } else { 
	    	  System.out.println( "Signature failed" );
	    	  return 0;
	      }
	    } catch (SignatureException se) {
	      System.out.println( "Singature failed" );
	      return 0;
	    }
	}
	
	
	public static int VerifyDigitalSignature(String text, PublicKey PublicKey, String path) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException{
		//Pegar arquivo em byte[]
	    File arq = new File(path + "index.asd");
	    byte [] signature = getBytes(arq);
	    
	    if(text==null){
	    	return 1;
	    }
	    
	    byte [] byteText = text.getBytes();
	    
		Signature sig = Signature.getInstance("MD5WithRSA");
	    
	    // verifica a assinatura com a chave publica
	    System.out.println( "\nStart signature verification" );
	    sig.initVerify(PublicKey);
	    sig.update(byteText);
	    try {
	      if (sig.verify(signature)) {
	    	  System.out.println( "Signature verified" );
	    	  return 1;
	      } else { 
	    	  System.out.println( "Signature failed" );
	    	  ControladorBackend.registrar(8014);
	    	  return 0;
	      }
	    } catch (SignatureException se) {
	      System.out.println( "Singature failed" );
	      ControladorBackend.registrar(8014);
	      return 0;
	    }
	}
	
	
	public static int VerifyDigitalSignature(byte[] byteText, PublicKey PublicKey, String path, String name) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException{
		//Pegar arquivo em byte[]
	    File arq = new File(path + name + ".asd");
	    byte [] signature = getBytes(arq);
	    
	    if(byteText==null){
	    	return 1;
	    }
	    
		Signature sig = Signature.getInstance("MD5WithRSA");
	    
	    // verifica a assinatura com a chave publica
	    System.out.println( "\nStart signature verification" );
	    sig.initVerify(PublicKey);
	    sig.update(byteText);
	    try {
	      if (sig.verify(signature)) {
	    	  System.out.println( "Signature verified" );
	    	  return 1;
	      } else { 
	    	  System.out.println( "Signature failed" );
	    	  return 0;
	      }
	    } catch (SignatureException se) {
	      System.out.println( "Singature failed" );
	      return 0;
	    }
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
}
