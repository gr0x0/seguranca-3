package backend;

import java.security.cert.X509Certificate;
import java.security.PrivateKey;

public class Account {
	private int id, groupid;
	private String HexPass;
	private X509Certificate certificate;
	private PrivateKey PrivateKey;
	private String SecretPhrase;
	
	public Account(int id, String hexPass, X509Certificate certificate) {
		super();
		this.id = id;
		HexPass = hexPass;
		this.certificate = certificate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHexPass() {
		return HexPass;
	}

	public void setHexPass(String hexPass) {
		HexPass = hexPass;
	}

	public X509Certificate getCertificate() {
		return certificate;
	}

	public void setCertificate(X509Certificate certificate) {
		this.certificate = certificate;
	}

	public PrivateKey getPrivateKey() {
		return PrivateKey;
	}

	public void setPrivateKey(PrivateKey privateKey) {
		PrivateKey = privateKey;
	}
	
	public int getGroupid() {
		return groupid;
	}

	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}

	public String getSecretPhrase() {
		return SecretPhrase;
	}

	public void setSecretPhrase(String secretPhrase) {
		SecretPhrase = secretPhrase;
	}
	
}
