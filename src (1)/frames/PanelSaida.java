package frames;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import backend.Connect;
import backend.ControladorBackend;

public class PanelSaida extends JPanel
{
	private JLabel Llogin = null;
	private JLabel Lgrupo = null;
	private JLabel Lnome = null;
	private JLabel Lacessos = null;
	private JLabel Lsaida = null;
	private JLabel Lmsgsaida = null;
	private JButton Bsair = null;
	private JButton Bvoltar = null;

	protected PanelSaida(String login, String grupo, String nome, String acessos, FrameMain frameMain)
	{		
		Llogin = new JLabel("Login: "+login);
		Lgrupo = new JLabel("Grupo: "+grupo);
		Lnome = new JLabel("Nome: "+nome);
		Lacessos = new JLabel("Número de acessos: "+acessos);
		Lsaida = new JLabel("Saída do sistema:");
		Lmsgsaida = new JLabel("Pressione o botão Sair para confirmar.");

		Bsair = new JButton("Sair");
		Bsair.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				ControladorBackend.registrar(9002);
				ControladorBackend.registrar(1002);
				Connect.Close();
				System.exit(0);
			}
		});

		Bvoltar = new JButton("Voltar para Menu Principal");
		Bvoltar.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				ControladorBackend.registrar(9003);
				frameMain.changePanel(0);
			}
		});

		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 2;
		this.add(Llogin, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 2;
		this.add(Lgrupo, constraints);

		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 2;
		this.add(Lnome, constraints);

		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.ipady = 60;
		constraints.gridwidth = 2;
		this.add(Lacessos, constraints);

		constraints.gridx = 0;
		constraints.gridy = 6;
		constraints.ipady = 1;
		constraints.gridwidth = 2;
		this.add(Lsaida, constraints);

		constraints.gridx = 0;
		constraints.gridy = 7;
		constraints.gridwidth = 2;
		this.add(Lmsgsaida, constraints);

		constraints.gridx = 0;
		constraints.gridy = 10;
		constraints.gridwidth = 1;
		this.add(Bsair, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 10;
		constraints.gridwidth = 1;
		this.add(Bvoltar, constraints);	

	}


}