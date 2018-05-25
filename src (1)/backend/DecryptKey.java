package backend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;

public class DecryptKey {
	
	public static String Decrypt(String SecretSentence, String endereco) throws NoSuchAlgorithmException, UnsupportedEncodingException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		sr.setSeed(SecretSentence.getBytes("UTF8"));
		
		// gera uma chave para o DES
	    System.out.println( "\nStart generating DES key" );
	    KeyGenerator keyGen = KeyGenerator.getInstance("DES");
	    keyGen.init(56, sr);
	    Key key = keyGen.generateKey();
	    System.out.println( "Finish generating DES key" );
	    
	    //Pegar arquivo em byte[]
	    File arq = new File(endereco);
	    if(!arq.exists()){
	    	ControladorBackend.registrar(3004);
	    	return null;
	    }
	    byte [] vet = getBytes(arq);
	    
	    // define um objeto de cifra DES e imprime o provider utilizado
	    Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
	    
	    // desencripta o texto cifrado com a chave
	    System.out.println( "\nStart decryption" );
	    cipher.init(Cipher.DECRYPT_MODE, key);
	    byte[] newPlainText;
		try {
			newPlainText = cipher.doFinal(vet);
		} catch (Exception e) {
			ControladorBackend.registrar(3005);
			return null;			// Retorna NULL caso não seja a frase secreta correta
		}
	    System.out.println( "Finish decryption: " );
	    String privatekey = new String(newPlainText, "UTF8");
	    System.out.println( privatekey );
	    
		return privatekey;
	}
	
	public static String VerifyDigitalSig(PrivateKey pk, String path) throws NoSuchAlgorithmException, UnsupportedEncodingException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{	    
		//Pegar arquivo em byte[]
	    File arq = new File(path+"index.asd");
	    byte [] vet = getBytes(arq);
	    
	    // define o objeto de cifra RSA e imprime o provider utilizado   
	    Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
	    System.out.println( "\n" + cipher.getProvider().getInfo() );
	    
	    // desencripta o texto cifrado com a chave
	    System.out.println( "\nStart decryption" );
	    cipher.init(Cipher.DECRYPT_MODE, pk);
	    byte[] newPlainText;
		try {
			newPlainText = cipher.doFinal(vet);
		} catch (Exception e) {
			return null;
		}
	    System.out.println( "Finish decryption: " );
	    String decrypted = new String(newPlainText, "UTF8");
	    System.out.println( decrypted );
	    
		return decrypted;
	}
	
	
	public static String DecryptArchives(String SecretSentence, String path) throws NoSuchAlgorithmException, UnsupportedEncodingException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{
		if(SecretSentence==null){
			return null;
		}
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		sr.setSeed(SecretSentence.getBytes("UTF8"));
		
		// gera uma chave para o DES
	    System.out.println( "\nStart generating DES key" );
	    KeyGenerator keyGen = KeyGenerator.getInstance("DES");
	    keyGen.init(56, sr);
	    Key key = keyGen.generateKey();
	    System.out.println( "Finish generating DES key" );
	    
	    //Pegar arquivo em byte[]
	    File arq = new File(path + "index.enc");
	    byte [] vet = getBytes(arq);
	    
	    // define um objeto de cifra DES e imprime o provider utilizado
	    Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
	    
	    // desencripta o texto cifrado com a chave
	    System.out.println( "\nStart decryption" );
	    cipher.init(Cipher.DECRYPT_MODE, key);
	    byte[] newPlainText;
		try {
			newPlainText = cipher.doFinal(vet);
		} catch (Exception e) {
			return null;			// Retorna NULL caso não seja a frase secreta correta
		}
	    System.out.println( "Finish decryption: " );
	    String decrypted = new String(newPlainText, "UTF8");
	    System.out.println( decrypted );
	    
		return decrypted;
	}
	
	
	public static byte[] DecryptArchive(String SecretSentence, String path, String name) throws NoSuchAlgorithmException, UnsupportedEncodingException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{
		if(SecretSentence==null){
			return null;
		}
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		sr.setSeed(SecretSentence.getBytes("UTF8"));
		
		// gera uma chave para o DES
	    System.out.println( "\nStart generating DES key" );
	    KeyGenerator keyGen = KeyGenerator.getInstance("DES");
	    keyGen.init(56, sr);
	    Key key = keyGen.generateKey();
	    System.out.println( "Finish generating DES key" );
	    
	    //Pegar arquivo em byte[]
	    File arq = new File(path + name + ".enc");
	    byte [] vet = getBytes(arq);
	    
	    // define um objeto de cifra DES e imprime o provider utilizado
	    Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
	    
	    // desencripta o texto cifrado com a chave
	    System.out.println( "\nStart decryption" );
	    cipher.init(Cipher.DECRYPT_MODE, key);
	    byte[] newPlainText;
		try {
			newPlainText = cipher.doFinal(vet);
		} catch (Exception e) {
			ControladorBackend.registrar(8013);
			return null;			// Retorna NULL caso não seja a frase secreta correta
		}
		ControladorBackend.registrar(8011);
	    System.out.println( "Finish decryption: " );
	    /*
	    String decrypted = new String(newPlainText, "UTF8");
	    System.out.println( decrypted );
	    
		return decrypted;
		*/
	    return newPlainText;
	}
	
	
	public static String Decrypt(PrivateKey pk, String path) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException
	{		
	    //Pegar arquivo em byte[]
	    File arq = new File(path+"index.env");
	    if(!arq.exists()){
	    	return "na";
	    }
	    byte [] vet = getBytes(arq);
	    
	    // define o objeto de cifra RSA e imprime o provider utilizado   
	    Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
	    System.out.println( "\n" + cipher.getProvider().getInfo() );
	    
	    // desencripta o texto cifrado com a chave
	    System.out.println( "\nStart decryption" );
	    cipher.init(Cipher.DECRYPT_MODE, pk);
	    byte[] newPlainText;
		try {
			newPlainText = cipher.doFinal(vet);
		} catch (Exception e) {
			return null;			// Retorna NULL caso não seja a frase secreta correta
		}
	    System.out.println( "Finish decryption: " );
	    String decrypted = new String(newPlainText, "UTF8");
	    System.out.println( decrypted );
	    
		return decrypted;
	}
	
	
	public static String Decrypt(PrivateKey pk, String path, String name) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException
	{		
	    //Pegar arquivo em byte[]
	    File arq = new File(path + name + ".env");
	    byte [] vet = getBytes(arq);
	    
	    // define o objeto de cifra RSA e imprime o provider utilizado   
	    Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
	    System.out.println( "\n" + cipher.getProvider().getInfo() );
	    
	    // desencripta o texto cifrado com a chave
	    System.out.println( "\nStart decryption" );
	    cipher.init(Cipher.DECRYPT_MODE, pk);
	    byte[] newPlainText;
		try {
			newPlainText = cipher.doFinal(vet);
		} catch (Exception e) {
			return null;			// Retorna NULL caso não seja a frase secreta correta
		}
	    System.out.println( "Finish decryption: " );
	    String decrypted = new String(newPlainText, "UTF8");
	    System.out.println( decrypted );
	    
		return decrypted;
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
