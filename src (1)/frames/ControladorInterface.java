package frames;

import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.omg.CORBA.portable.ValueBase;

import backend.Account;
import backend.Certificate;
import backend.ControladorBackend;
import backend.DigitalSignature;
import backend.Query;
import backend.Signup;

public class ControladorInterface
{
	//ADMIN:
	//LOGIN: admin@inf1416.puc-rio.br
	//SENHA: 190719
	//SECRET PHRASE: admin

	//USER:
	//LOGIN: user01@inf1416.puc-rio.br
	//SENHA: 190719
	//SECRET PHRASE: user01
	
	//USER:
	//LOGIN: user01@inf1416.puc-rio.br
	//SENHA: 13579246
	//SECRET PHRASE: user04

	private static Account account;

	private static ControladorInterface ctrlIF = null;
	private static ControladorBackend ctrlBE = null;
	private static FrameAccount frameAccount = null;
	private static FramePassword framePassword = null;
	private static FrameAutentication frameAutentication = null;
	private static FrameMain frameMain = null;

	private static boolean isAccPhase = false;
	private static boolean isPWPhase = false;
	private static boolean isAutPhase = false;
	private static boolean isMainPhase = false;

	private static String username = null;
	private static String archive = null;

	private ControladorInterface()
	{}	

	public static ControladorInterface getCtrlIF() throws ClassNotFoundException
	{
		if(ctrlIF == null)
		{
			ctrlIF = new ControladorInterface();
			ctrlBE = ControladorBackend.getCtrlBE();
			changePhase(true);
		}
		return ctrlIF;
	}

	protected static void changePhase(boolean isOK)
	{			
		if(isOK == true && isAccPhase == false && isPWPhase == false && isAutPhase == false && isMainPhase == false)
		{
			//Início da fase de username
			ControladorBackend.registrar(2001);
			isAccPhase = true;
			frameAccount = new FrameAccount();
		}
		else if(isOK == true && isAccPhase == true && isPWPhase == false && isAutPhase == false && isMainPhase == false)
		{
			//Início da fase de password
			ControladorBackend.registrar(2002);
			ControladorBackend.registrar(3001);
			frameAccount.dispose();
			isPWPhase = true;
			framePassword = new FramePassword();
		}
		else if(isOK == false && isAccPhase == true && isPWPhase == true && isAutPhase == false && isMainPhase == false)
		{
			//Password incorreta 3 vezes - reseta para Fase de username
			ControladorBackend.registrar(3002);
			framePassword.dispose();
			isPWPhase = false;
			isAccPhase = false;
			changePhase(true);			
		}
		else if(isOK == true && isAccPhase == true && isPWPhase == true  && isAutPhase == false && isMainPhase == false)
		{
			//Início da fase de autenticação
			ControladorBackend.registrar(3003);
			ControladorBackend.registrar(3002);
			ControladorBackend.registrar(4001);
			framePassword.dispose();
			isAutPhase = true;
			frameAutentication = new FrameAutentication();
		}
		else if(isOK == false && isAccPhase == true && isPWPhase == true  && isAutPhase == true && isMainPhase == false)
		{
			//Frase secreta incorreta 3 vezes - reseta para Fase de username
			ControladorBackend.registrar(3010);
			frameAutentication.dispose();
			isPWPhase = false;
			isAccPhase = false;
			isAutPhase = false;
			changePhase(true);	
		}
		else if(isOK == true && isAccPhase == true && isPWPhase == true  && isAutPhase == true && isMainPhase == false)
		{
			//Início da fase principal
			ControladorBackend.registrar(4002);
			frameAutentication.dispose();
			isMainPhase = true;
			try {
				frameMain = new FrameMain(ControladorInterface.getLoginInfo());
				ControladorBackend.registrar(5001);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	protected static int checkAccount(String username) throws CertificateException
	{
		//aqui chama métodos externos para checar acc:
		//retorna 0 se user não existe
		//retorna 1 se user existe mas tá bloqueado
		//retorna 2 se user existe e normalizado
		ControladorInterface.username = username;
		
		try {
			return ctrlBE.setAccount(username);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	protected static boolean checkPassword(ArrayList<String> passwordValues) throws Exception
	{
		//aqui chama métodos externos para checar pw:
		//se senha correta, chama changePhase() e retorna true
		//se senha incorreta, retorna false

		int i = ctrlBE.setPassword(passwordValues);
		System.out.println(i);
		if(i == 1){
			return true;
		}else{
			return false;
		}
	}

	protected static boolean checkSecretPhrase(String secretPhrase, String endereco) throws Exception
	{
		//aqui chama métodos externos para checar a secret phrase
		//se secret phrase correta, chama changePhase() e retorna true
		//se senha secret phrase, retorna false

		if(ctrlBE.setSecret(secretPhrase, endereco)==0){
			return false;
		} else {
			return true;
		}
	}

	protected static String[] getCertificadoLoginInfo(){
		// 0 - Nome
		// 1 - Email
		// 2 - Serial
		// 3 - Tipo
		// 4 - Validade
		// 5 - Versao
		// 6 - Total de Usuarios
		// 7 - PrivateKey
		// 8 - Certificado
		// 9 - Emissor
		try {
			return new String[]{Certificate.getNome(0), Certificate.getEmail(0), Certificate.getSerial(0), Certificate.getType(0), Certificate.getValidity(0), Certificate.getVersao(0), String.valueOf(Query.getCountList(String.valueOf(account.getId()))), DigitalSignature.savePrivateKey(account.getPrivateKey()), String.valueOf(account.getCertificate()), Certificate.getNomeAuthor(0)};
		} catch (SQLException | GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}	

	protected static String[] getCertificadoDigitalInfo(String certificadoPath)
	{
		//aqui chama métodos externos para recuperar as seguintes informações
		//do certificado digital:
		//versão
		//série
		//validade
		//tipo de assinatura
		//emissor
		//sujeito (friendly name)
		//e-mail
		//essas informações devem ser retornadas numa String[] que as contenha
		//nessa exata ordem

		try {
			Certificate.convertToX509Cert(Signup.getFile(certificadoPath));
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String str[] = new String[]{Certificate.getVersao(1), Certificate.getSerial(1), Certificate.getValidity(1), Certificate.getType(1), Certificate.getNomeAuthor(1), Certificate.getNome(1), Certificate.getEmail(1)};

		Certificate.deleteCertificate();

		return str;
	}

	protected static String[] getLoginInfo() throws SQLException
	{
		//aqui chama métodos externos para recuperar as seguintes informações
		//sobre o usuário logado:
		//login
		//grupo
		//nome do usuário
		//total de acessos
		//adicionalmente às informações sobre o usuário logado, retornam-se as
		//seguintes informações sobre o sistema:
		//total de usuários do sistema
		//essas informações devem ser retornadas numa String[] que as contenha
		//nessa exata ordem
		if(account == null)
			account = ctrlBE.getAccount();

		String grupo;
		if(account.getGroupid() == 0 ){
			grupo = "AdministratorGroup";
		} else {
			grupo = "UserGroup";
		}

		setAccess(0);

		return new String[]{Certificate.getEmail(0), grupo, Certificate.getNome(0), String.valueOf(Query.getAccess(String.valueOf(account.getId()))), Query.getCountUsers()};
	}

	protected static String getSecretArchives(String path)
	{
		String str = ctrlBE.getListArchives(path);
		return str;
	}

	protected static String getArchive(String codname, String name, String type, String email, String group, String path)
	{
		String str = ctrlBE.getArchive(codname, name, type, email, group, path);
		return str;
	}

	protected static void setAccess(int i)
	{
		if(i==0){
			try {
				Query.setAccess(String.valueOf(account.getId()));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(i==1){
			try {
				Query.setCountList(String.valueOf(account.getId()));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(i==2){
			try {
				Query.setConsult(String.valueOf(account.getId()));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	protected static String getAccess(int i)
	{
		if(i==0){
			try {
				return(String.valueOf(Query.getAccess(String.valueOf(account.getId()))));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(i==1){
			try {
				return(String.valueOf(Query.getCountList(String.valueOf(account.getId()))));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(i==2){
			try {
				return(String.valueOf(Query.getConsult(String.valueOf(account.getId()))));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	protected static void setArchive(String a)
	{
		System.out.print(a+"AQUI!\n");
		ControladorInterface.archive = a;
	}
	
	public static String getArchive()
	{
		if(ControladorInterface.archive == null)
			return ControladorInterface.archive = new String();
		else return ControladorInterface.archive;
	}
	
	static protected void bloquearUsuario()
	{
		ControladorBackend.bloquearUsuario(ControladorInterface.username);
	}

}