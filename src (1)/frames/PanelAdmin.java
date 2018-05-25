package frames;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import backend.ControladorBackend;

public class PanelAdmin extends JPanel
{
	private JLabel Llogin = null;
	private JLabel Lgrupo = null;
	private JLabel Lnome = null;
	private JLabel Lacessos = null;
	private JLabel Lmenu = null;
	private JButton Bcadastrar = null;
	private JButton Bchave = null;
	private JButton Bpasta = null;
	private JButton Bsair = null;

	protected PanelAdmin(String login, String grupo, String nome, String acessos, FrameMain frameMain)
	{		
		Llogin = new JLabel("Login: "+login);
		Lgrupo = new JLabel("Grupo: "+grupo);
		Lnome = new JLabel("Nome: "+nome);
		Lacessos = new JLabel("Número de acessos: "+acessos);
		Lmenu = new JLabel("Menu principal:");

		if(grupo.contentEquals("AdministratorGroup"))
		{
			Bcadastrar = new JButton("Cadastrar novo usuário");
			Bcadastrar.addActionListener(new ActionListener() { 
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					ControladorBackend.registrar(5002);
					frameMain.changePanel(1);
				}
			});
		}

		Bchave = new JButton("Listar chave privada e certificado digital do usuário");
		Bchave.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				ControladorBackend.registrar(5003);
				ControladorInterface.setAccess(1);
				frameMain.changePanel(2);
			}
		});

		Bpasta = new JButton("Consultar pasta de arquivos secretos do usuário");
		Bpasta.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				ControladorBackend.registrar(5004);
				frameMain.changePanel(3);
			}
		});

		Bsair = new JButton("Sair do sistema");
		Bsair.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				ControladorBackend.registrar(5005);
				frameMain.changePanel(4);
			}
		});

		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		constraints.gridx = 0;
		constraints.gridy = 0;
		this.add(Llogin, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		this.add(Lgrupo, constraints);

		constraints.gridx = 0;
		constraints.gridy = 2;
		this.add(Lnome, constraints);

		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.ipady = 60;
		this.add(Lacessos, constraints);

		constraints.gridx = 0;
		constraints.gridy = 6;
		constraints.ipady = 1;
		this.add(Lmenu, constraints);
		
		if(grupo.contentEquals("AdministratorGroup"))
		{
			constraints.gridx = 0;
			constraints.gridy = 7;
			this.add(Bcadastrar, constraints);
		}
		
		constraints.gridx = 0;
		constraints.gridy = 8;
		this.add(Bchave, constraints);

		constraints.gridx = 0;
		constraints.gridy = 9;
		this.add(Bpasta, constraints);

		constraints.gridx = 0;
		constraints.gridy = 10;
		this.add(Bsair, constraints);			

	}


}
