package frames;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import backend.Certificate;
import backend.ControladorBackend;
import backend.Query;

public class PanelListar extends JPanel
{
	private FrameMain frameMain = null;
	private JLabel Llogin = null;
	private JLabel Lgrupo = null;
	private JLabel Lnome = null;
	private JLabel Llistagens = null;
	private JLabel Lchave = null;
	private JTextArea Achave = null;
	private JScrollPane Schave = null;
	private JLabel Lcertificado = null;
	private JLabel Lversao = null;
	private JLabel Lserie = null;
	private JLabel Lvalidade = null;
	private JLabel Ltipoassinatura = null;
	private JLabel Lemissor = null;
	private JLabel Lsujeito = null;
	private JButton Bvoltar = null;
	
	protected PanelListar(String login, String grupo, String nome, FrameMain frameMain)
	{		
		this.frameMain = frameMain;
		String s[] = ControladorInterface.getCertificadoLoginInfo();
		Llogin = new JLabel("Login: "+login);
		Lgrupo = new JLabel("Grupo: "+grupo);
		Lnome = new JLabel("Nome: "+nome);		
		
		Llistagens = new JLabel("Total de listagem do usuário: " + s[6]);	
		
		Lchave = new JLabel("Chave Privada: ");
		Achave = new JTextArea(s[7]);
		Achave.setEditable(false);  
		Achave.setCursor(null);  
		Achave.setOpaque(false);  
		Achave.setFocusable(false);
		Achave.setLineWrap(true);
		Achave.setWrapStyleWord(true);		
		Schave = new JScrollPane(Achave);
		Schave.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		Schave.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
	    
		Lcertificado = new JLabel("Certificado: ");
		Lversao = new JLabel("Versão: " + s[5]);
		Lserie = new JLabel("Série: " + s[2]);
		Lvalidade = new JLabel("Validade: " + s[4]);
		Ltipoassinatura = new JLabel("Tipo de Assinatura: " + s[3]);
		Lemissor = new JLabel("Emissor: " + s[9]);
		Lsujeito = new JLabel("Sujeito (Friendly Name): " + s[0]);
		
		Bvoltar = new JButton("Voltar para Menu Principal");
		Bvoltar.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				ControladorBackend.registrar(7002);
				frameMain.changePanel(0);
			}
		});

		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 3;
		this.add(Llogin, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 3;
		this.add(Lgrupo, constraints);

		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 3;
		this.add(Lnome, constraints);

		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.ipady = 30;
		this.add(Llistagens, constraints);

		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.ipady = 1;
		constraints.ipady = 20;
		constraints.gridwidth = 3;
		this.add(Lchave, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 5;
		constraints.ipadx = 400;
		constraints.ipady = 200;
		constraints.gridheight = 2;
		constraints.gridwidth = 3;
		this.add(Schave, constraints);

		constraints.gridx = 0;
		constraints.gridy = 7;
		constraints.gridheight = 1;
		constraints.gridwidth = 3;
		constraints.ipadx = 1;
		constraints.ipady = 20;
		this.add(Lcertificado, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 8;
		constraints.gridwidth = 3;
		constraints.ipady = 1;
		this.add(Lversao, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 9;
		constraints.gridwidth = 3;
		this.add(Lserie, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 10;
		constraints.gridwidth = 3;
		this.add(Lvalidade, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 11;
		constraints.gridwidth = 3;
		this.add(Ltipoassinatura, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 12;
		constraints.gridwidth = 3;
		this.add(Lemissor, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 13;
		constraints.gridwidth = 3;
		this.add(Lsujeito, constraints);

		constraints.gridx = 1;
		constraints.gridy = 14;
		constraints.ipadx = 1;
		constraints.gridwidth = 3;
		this.add(Bvoltar, constraints);	
	}

}
