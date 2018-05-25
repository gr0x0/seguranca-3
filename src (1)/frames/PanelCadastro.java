package frames;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRootPane;

import backend.ControladorBackend;
import backend.Signup;

public class PanelCadastro extends JPanel
{
	private FrameMain frameMain = null;
	private JLabel Llogin = null;
	private JLabel Lgrupo = null;
	private JLabel Lnome = null;
	private JLabel Lusuarios = null;
	private JLabel Lformulario = null;
	private JLabel Lcaminho = null;
	private JFormattedTextField Tcaminho = null;
	private JLabel LgrupoLista = null;
	private JComboBox TgrupoLista = null;
	private JLabel Lsenha = null;
	private JPasswordField Tsenha = null;
	private JLabel Lsenha2 = null;
	private JPasswordField Tsenha2 = null;
	private JButton Bcadastrar = null;
	private JButton Bvoltar = null;

	protected PanelCadastro(String login, String grupo, String nome, String totalUsuarios, FrameMain frameMain)
	{		
		this.frameMain = frameMain;
		
		Llogin = new JLabel("Login: "+login);
		Lgrupo = new JLabel("Grupo: "+grupo);
		Lnome = new JLabel("Nome: "+nome);
		Lusuarios = new JLabel("Total de usuários do sistema: "+totalUsuarios);
		Lformulario = new JLabel("Formulário de cadastro:");

		Lcaminho = new JLabel("Caminho do certificado digital: ");
		Tcaminho = new JFormattedTextField();
		Tcaminho.setHorizontalAlignment(JFormattedTextField.CENTER);
		Tcaminho.setAlignmentX(Component.CENTER_ALIGNMENT);

		LgrupoLista = new JLabel("Grupo: ");
		TgrupoLista = new JComboBox(new String[]{"Administrador","Usuário"});

		Lsenha = new JLabel("Senha pessoal: ");
		Tsenha = new JPasswordField();
		Tsenha.setHorizontalAlignment(JFormattedTextField.CENTER);
		Tsenha.setAlignmentX(Component.CENTER_ALIGNMENT);
		Tsenha.setToolTipText("6 a 8 dígitos, sem repetições de dígitos e sem sequências crescentes ou decrescentes");

		Lsenha2 = new JLabel("Confirmação senha pessoal: ");
		Tsenha2 = new JPasswordField();
		Tsenha2.setHorizontalAlignment(JFormattedTextField.CENTER);
		Tsenha2.setAlignmentX(Component.CENTER_ALIGNMENT);
		Tsenha.setToolTipText("6 a 8 dígitos, sem repetições de dígitos e sem sequências crescentes ou decrescentes");

		Bcadastrar = new JButton("Cadastrar");
		Bcadastrar.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				ControladorBackend.registrar(6002);
				if(Tsenha.getPassword().length < 6 || Tsenha.getPassword().length > 8)
				{
					JOptionPane.showMessageDialog(null, "Senha deve conter de 6 a 8 dígitos.");
					ControladorBackend.registrar(6003);
				}
				else if(hasRepetitions(Tsenha.getPassword()))
				{
					JOptionPane.showMessageDialog(null, "Senha não pode conter sequências de dígitos repetidos.");
					ControladorBackend.registrar(6003);
				}
				else if(hasSequences(Tsenha.getPassword()))
				{
					JOptionPane.showMessageDialog(null, "Senha não pode conter sequências de dígitos em ordem crescente ou decrescente.");
					ControladorBackend.registrar(6003);
				}
				else if(areDifferent(Tsenha.getPassword(), Tsenha2.getPassword()))
				{
					JOptionPane.showMessageDialog(null, "Senha diferente de confirmação.");
					ControladorBackend.registrar(6003);
				}
				else
					showConfirmationFrame();
			}
		});

		Bvoltar = new JButton("Voltar para Menu Principal");
		Bvoltar.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				ControladorBackend.registrar(6007);
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
		constraints.ipady = 60;
		this.add(Lusuarios, constraints);

		constraints.gridx = 0;
		constraints.gridy = 6;
		constraints.ipady = 1;
		constraints.gridwidth = 3;
		this.add(Lformulario, constraints);

		constraints.gridx = 0;
		constraints.gridy = 7;
		constraints.gridwidth = 1;
		this.add(Lcaminho, constraints);

		constraints.gridx = 1;
		constraints.gridy = 7;
		constraints.ipadx = 300;
		constraints.gridwidth = 2;
		this.add(Tcaminho, constraints);

		constraints.gridx = 0;
		constraints.gridy = 8;
		constraints.ipadx = 1;
		constraints.gridwidth = 1;
		this.add(LgrupoLista, constraints);

		constraints.gridx = 1;
		constraints.gridy = 8;
		constraints.ipadx = 100;
		constraints.gridwidth = 2;
		this.add(TgrupoLista, constraints);		

		constraints.gridx = 0;
		constraints.gridy = 9;
		constraints.ipadx = 1;
		constraints.gridwidth = 1;
		this.add(Lsenha, constraints);

		constraints.gridx = 1;
		constraints.gridy = 9;
		constraints.ipadx = 200;
		constraints.gridwidth = 2;
		this.add(Tsenha, constraints);	

		constraints.gridx = 0;
		constraints.gridy = 10;
		constraints.ipadx = 1;
		constraints.gridwidth = 1;
		this.add(Lsenha2, constraints);

		constraints.gridx = 1;
		constraints.gridy = 10;
		constraints.ipadx = 200;
		constraints.gridwidth = 2;
		this.add(Tsenha2, constraints);	

		constraints.gridx = 0;
		constraints.gridy = 11;
		constraints.ipadx = 1;
		constraints.gridwidth = 3;
		this.add(Bvoltar, constraints);	

		constraints.gridx = 0;
		constraints.gridy = 12;
		constraints.ipadx = 1;
		constraints.gridwidth = 3;
		this.add(Bcadastrar, constraints);
	}

	private boolean hasRepetitions(char[] c1)
	{		
		for(int i=1; i<c1.length; i++)
		{
			if(c1[i]==c1[i-1])
				return true;
		}		
		return false;
	}

	private boolean hasSequences(char[] c1)
	{
		for(int i=1; i<c1.length; i++)
		{
			if(c1[i] >= '0' && c1[i] <= '9' && c1[i-1] >= '0' && c1[i-1] <= '9')
			{
				if(c1[i]+1==c1[i-1] || c1[i]-1==c1[i-1])
					return true;
			}
		}		
		return false;
	}

	private boolean areDifferent(char[] c1, char[] c2)
	{
		if(c1.length!=c2.length)
			return true;

		for(int i=0; i<c1.length; i++)
		{
			if(c1[i] != c2[i])
				return true;
		}	
		return false;
	}
	
	private void emptyAll()
	{
		this.Tcaminho.setText("");
		this.TgrupoLista.setName("Administrador");
		this.Tsenha.setText("");
		this.Tsenha2.setText("");
	}
	
	private void showConfirmationFrame()
	{
		//Se passou por tudo, mostra uma tela com campos do certificado digital:
			//Versão, série, validade, tipo de assinatura, emissor, sujeito (Friendly Name) e E-mail
			//Admin tem que dar o ok nessa tela
			//Checa-se se o nome de usuário é único, voltando para cadastro caso não seja (com tudo ainda preenchido)
			//Se o cadastro foi bem sucedido, volta pra tela de cadastro com tudo em branco (nova tela)
		JFrame f = new JFrame();
		f.setResizable(false);
		f.setBounds(430, 150, 250, 250);
		f.setLocationRelativeTo(null);
		f.setUndecorated(true);
		f.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
		f.setVisible(true);
		
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
		
		String[] s = ControladorInterface.getCertificadoDigitalInfo(Tcaminho.getText());
		p.add(new JLabel("Grupo: "+TgrupoLista.getSelectedItem()));
		p.add(new JLabel("Senha pessoal: "+ Tsenha.getText()));
		p.add(new JLabel("Versão: "+s[0]));
		p.add(new JLabel("Série: "+s[1]));
		p.add(new JLabel("Validade: "+s[2]));
		p.add(new JLabel("Tipo de Assinatura: "+s[3]));
		p.add(new JLabel("Emissor: "+s[4]));
		p.add(new JLabel("Sujeito (Friendly Name): "+s[5]));
		p.add(new JLabel("E-mail: "+s[6]));
		
		JButton b1 = new JButton("Confirma");
		b1.addActionListener(new ActionListener() { 
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				//aqui deve-se enviar os dados para alguma função externa que
				//primeiro checa se o nome de usuário é único. Se não for, retorna
				//para a tela anterior com os dados ainda preenchidos. Se o nome
				//foi único, realiza o cadastro propriamente dito.
				ControladorBackend.registrar(6005);
				try {
					if(Signup.sign(Tcaminho.getText(), TgrupoLista.getSelectedIndex(), Tsenha.getText()) == false){
						JOptionPane.showMessageDialog(null, "Nome de usuário existente.");
					}
				} catch (HeadlessException | SQLException e1) {
					e1.printStackTrace();
				}
				f.dispose();
				emptyAll();
			}
		});
		
		JButton b2 = new JButton("Cancela");
		b2.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				ControladorBackend.registrar(6006);
				f.dispose();
			}
		});
		
		p.add(b1);
		p.add(b2);
		f.add(p);
	}
}
