package backend;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import frames.ControladorInterface;
import backend.Login;

public class ControladorBackend 
{
	static ControladorBackend ctrlBE = null;
	static ControladorInterface ctrlIF = null;
	static private Account account = null;	
	private static BlockedList blockedList = null;

	private ControladorBackend()
	{}

	public static ControladorBackend getCtrlBE() throws ClassNotFoundException
	{
		if(ctrlBE == null)
		{
			blockedList = BlockedList.getBlockedList();
			new Connect("C:/Sqlite/BDSeg");
			ControladorBackend.registrar(1001);
			ctrlBE = new ControladorBackend();
			ctrlIF = ControladorInterface.getCtrlIF();
		}
		return ctrlBE;
	}


	public int setAccount(String acc) throws SQLException, CertificateException
	{
		if(isUsuarioBloqueado(acc)==true)
			return 1;
		else{
			account = Login.VerifyAcc(acc);
			if(account != null){
				System.out.println(account.getHexPass());
				return 2;
			}
		}
		return 0;
	}

	public int setPassword(ArrayList<String> passwordValues) throws Exception
	{
		return(DigestCalculator.VerifyPassword(account.getHexPass(), DigestCalculator.PossiblesPassword(passwordValues), passwordValues.size()));
	}

	public int setSecret(String secretPhrase, String endereco) throws Exception
	{
		String privatekey = DecryptKey.Decrypt(secretPhrase, endereco);
		if(privatekey == null){
			return 0;
		}
		account.setPrivateKey(DigitalSignature.loadPrivateKey(privatekey));
		if(account.getPrivateKey() == null){
			return 0;
		} else {
			if(DigitalSignature.TestSignature(account.getPrivateKey(), account.getCertificate().getPublicKey()) == 1){
				account.setGroupid(Query.getGroup(String.valueOf(account.getId())));
				account.setSecretPhrase(secretPhrase);
				return 1;
			}else{
				ControladorBackend.registrar(3006);
				return 0;
			}
		}

	}

	public Account getAccount(){
		return account;
	}

	public String getListArchives(String path)
	{
		String secret = null;
		int digest = 0;
		try {

			secret = DecryptKey.Decrypt(account.getPrivateKey(), path);
			if(secret.contentEquals("na"))
				return "na";

		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {

			secret = DecryptKey.DecryptArchives(secret, path);
			digest = DigitalSignature.VerifyDigitalSignature(secret, account.getCertificate().getPublicKey(), path);

		} catch (InvalidKeyException | NoSuchAlgorithmException | UnsupportedEncodingException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeySpecException | SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(digest==0){
			return "dig_inv";
		}

		return secret;
	}
	
	public String getArchive(String codname, String name, String type, String email, String group, String path)
	{
		String secret = null;
		int digest = 0;
		byte [] archive = null;
		String grupo = null;
		if(account.getGroupid() == 0){
			grupo = "administrador";
		}else if(account.getGroupid() == 1){
			grupo = "usuario";
		}

		if(Certificate.getEmail(0).equals(email) || grupo.equals(group) || grupo.equals("administrador")){

			ControladorBackend.registrar(8009);

			try {

				secret = DecryptKey.Decrypt(account.getPrivateKey(), path, codname);

			} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				archive = DecryptKey.DecryptArchive(secret, path, codname);
			} catch (InvalidKeyException | NoSuchAlgorithmException
					| UnsupportedEncodingException | NoSuchPaddingException
					| IllegalBlockSizeException | BadPaddingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if(archive==null){
//				ControladorBackend.registrar(8013);
				return "NotDecrypt";
			}else{
//				ControladorBackend.registrar(8011);
			}

			try {
				digest = DigitalSignature.VerifyDigitalSignature(archive, account.getCertificate().getPublicKey(), path, codname);
			} catch (InvalidKeyException | UnsupportedEncodingException
					| NoSuchAlgorithmException | InvalidKeySpecException
					| SignatureException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			if(digest==0){
				return "dig_inv";
			}else{
				ControladorBackend.registrar(8012);
			}
			
			Archive.WriteArchive(archive, name, type, path);

		}
		else ControladorBackend.registrar(8010);
		return "ok";
	}

	static public void bloquearUsuario(String username)
	{
		blockedList.addBlockedUser(username);
	}

	static protected boolean isUsuarioBloqueado(String username)
	{
		return blockedList.isUserBlocked(username);
	}

	static public void encerrarSistema()
	{
		ControladorBackend.registrar(1002);
	}

	static public void registrar(int msg)
	{
		if(msg == 1001 || msg == 1002 || msg == 2001 || msg == 2002 || msg == 2005)
		{
			Query.setReg(-1, msg, null);
		}
		else Query.setReg(account.getId(), msg, ControladorInterface.getArchive());
	}

}
