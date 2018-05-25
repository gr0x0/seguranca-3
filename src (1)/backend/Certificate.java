package backend;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Base64;

public class Certificate {
	private static ArrayList<X509Certificate> certificate = new ArrayList<X509Certificate>();
	
	public static void convertToX509Cert(String certificateString) throws CertificateException {
	    CertificateFactory cf = null;
	    try {
	        if (certificateString != null && !certificateString.trim().isEmpty()) {
	        	certificateString = certificateString.substring(certificateString.indexOf("-----BEGIN CERTIFICATE-----\n"));
	            certificateString = certificateString.replace("-----BEGIN CERTIFICATE-----\n", "").replace("-----END CERTIFICATE-----", ""); // NEED FOR PEM FORMAT CERT STRING
	            certificateString = certificateString.replace("\n", "");
	            byte[] certificateData = Base64.getDecoder().decode(certificateString.getBytes("UTF-8"));
	            cf = CertificateFactory.getInstance("X.509");
	            certificate.add((X509Certificate) cf.generateCertificate(new ByteArrayInputStream(certificateData)));
	        }
	    } catch (CertificateException e) {
	        throw new CertificateException(e);
	    } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static X509Certificate convertToX509Certificate(String certificateString) throws CertificateException {
		X509Certificate certificate = null;
	    CertificateFactory cf = null;
	    try {
	        if (certificateString != null && !certificateString.trim().isEmpty()) {
	        	certificateString = certificateString.substring(certificateString.indexOf("-----BEGIN CERTIFICATE-----\n"));
	            certificateString = certificateString.replace("-----BEGIN CERTIFICATE-----\n", "").replace("-----END CERTIFICATE-----", ""); // NEED FOR PEM FORMAT CERT STRING
	            certificateString = certificateString.replace("\n", "");
	            byte[] certificateData = Base64.getDecoder().decode(certificateString.getBytes("UTF-8"));
	            cf = CertificateFactory.getInstance("X.509");
	            certificate = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(certificateData));
	        }
	    } catch (CertificateException e) {
	        throw new CertificateException(e);
	    } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return certificate;
	}
	
	public static String getVersao(int which){
		return(String.valueOf(certificate.get(which).getVersion()));
	}
	
	public static String getNome(int which){
		String str = certificate.get(which).getSubjectDN().getName();
		str = str.substring(str.indexOf("CN=")+3, str.indexOf(", OU"));
		return(str);
	}
	
	public static String getEmail(int which){
		String str = certificate.get(which).getSubjectDN().getName();
		str = str.substring(str.indexOf("EMAILADDRESS=")+13, str.indexOf(", CN"));
		return(str);
	}
	
	public static String getNomeAuthor(int which){
		String str = certificate.get(which).getIssuerDN().getName();
		str = str.substring(str.indexOf("CN=")+3, str.indexOf(", OU"));
		return(str);
	}
	
	public static String getEmailAuthor(int which){
		String str = certificate.get(which).getIssuerDN().getName();
		str = str.substring(str.indexOf("EMAILADDRESS=")+13, str.indexOf(", CN"));
		return(str);
	}
	
	public static String getSerial(int which){
		return(String.valueOf(certificate.get(which).getSerialNumber()));
	}
	
	public static String getType(int which){
		return(certificate.get(which).getType());
	}
	
	public static String getValidity(int which){
		return(certificate.get(which).getNotBefore().toString());
	}
	
	public static String getAlg(int which){
		return(certificate.get(which).getSigAlgName());
	}

	public static X509Certificate getCertificate(int which) {
		return certificate.get(which);
	}

	public static void setCertificate(X509Certificate certificate, int which) {
		Certificate.certificate.set(which, certificate);
	}
	
	public static boolean deleteCertificate(){
		return (certificate.remove(certificate.size()-1)) != null;
	}
	
}
