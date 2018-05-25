package frames;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import backend.ControladorBackend;
import backend.Query;

public class PanelConsultar extends JPanel
{
	private FrameMain frameMain = null;
	private JLabel Llogin = null;
	private JLabel Lgrupo = null;
	private JLabel Lnome = null;
	private JLabel Lconsultas = null;
	private JLabel Lcaminho = null;
	private JFormattedTextField Tcaminho = null;
	private JButton Blistar = null;
	private JPanel Pindex = null;
	private JScrollPane Sindex = null;
	private JButton Bvoltar = null;
	
	protected PanelConsultar(String login, String grupo, String nome, FrameMain frameMain)
	{		
		this.frameMain = frameMain;
		
		Llogin = new JLabel("Login: "+login);
		Lgrupo = new JLabel("Grupo: "+grupo);
		Lnome = new JLabel("Nome: "+nome);				
		Lconsultas = new JLabel("Total de consultas do usuário: "+ ControladorInterface.getAccess(2));//Inserir função que pega consultas para printar neste label
		
		Lcaminho = new JLabel("Caminho da pasta: ");
		Tcaminho = new JFormattedTextField();
		//Tcaminho.setHorizontalAlignment(JFormattedTextField.CENTER);
		Tcaminho.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		Blistar = new JButton("Listar");
		Blistar.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				ControladorBackend.registrar(8003);
				
				if(Tcaminho.getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "Campo em branco.");
				}else{
					String str = ControladorInterface.getSecretArchives(Tcaminho.getText());
					if(str.contentEquals("na")){
						JOptionPane.showMessageDialog(null, "Arquivo nao encontrado");
						ControladorBackend.registrar(8006);
					}else if(str == null)
					{
						JOptionPane.showMessageDialog(null, "Chave privada não decripta o envelope digital");
					}
					else if(str.equals("dig_inv")){
						JOptionPane.showMessageDialog(null, "Assinatura Digital Inválida");
					}else{
						ControladorInterface.setAccess(2);
						fillIndexPanel(str);
						Pindex.revalidate();
						Pindex.repaint();
						ControladorBackend.registrar(8007);
					}
				}
			}
		});
		
		Pindex = new JPanel();
		Pindex.setLayout(new BoxLayout(Pindex, BoxLayout.PAGE_AXIS));
		Sindex = new JScrollPane(Pindex);
		Sindex.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		Sindex.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		Bvoltar = new JButton("Voltar para Menu Principal");
		Bvoltar.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				ControladorBackend.registrar(8002);
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
		this.add(Lconsultas, constraints);

		constraints.gridx = 0;
		constraints.gridy = 6;
		constraints.ipady = 1;
		constraints.gridwidth = 1;
		this.add(Lcaminho, constraints);

		constraints.gridx = 1;
		constraints.gridy = 6;
		constraints.ipadx = 200;
		constraints.gridwidth = 2;
		this.add(Tcaminho, constraints);	
		
		constraints.gridx = 0;
		constraints.gridy = 7;
		constraints.ipadx = 1;
		constraints.gridwidth = 3;
		this.add(Blistar, constraints);	
		
		constraints.gridx = 0;
		constraints.gridy = 8;
		constraints.ipadx = 400;
		constraints.ipady = 130;
		constraints.gridheight = 3;
		constraints.gridwidth = 3;
		this.add(Sindex, constraints);	
		
		constraints.gridx = 0;
		constraints.gridy = 11;
		constraints.ipadx = 1;
		constraints.ipady = 1;
		constraints.gridheight = 1;
		constraints.gridwidth = 3;
		this.add(Bvoltar, constraints);	
	}
	
	private void fillIndexPanel(String index)
	{
		int i_aux = 0;
		for(int i = 0; i<index.length(); i++)
		{
			if(index.charAt(i)=='\n')
			{			
				JButton b = new JButton(index.substring(i_aux, i));
				b.addActionListener(new ActionListener() { 
					@Override
					public void actionPerformed(ActionEvent e) 
					{
						//aqui entra uma função externa que verifica que o usuário
						//corrente é dono do arquivo ou pertence ao grupo do arquivo
						//(avisando-o caso não tenha permissão para acessá-lo),
						//e decripta o arquivo cujo nome é igual ao NOME_CODIGO_DO_ARQUIVO
						//(relatando para o usuário eventuais erros de integridade, 
						//autenticidade e sigilo), e gravar os dados decriptados em um novo 
						//arquivo que fica na pasta onde o arquivo encriptado original se
						//encontra.
						String[] dados = new String[5];
						String s = b.getText().trim();
						for(int j = 0; j<5; j++)
						{
							if(j == 1)
							{
								dados[j] = s.substring(0, s.indexOf('.'));
								s = s.substring(s.indexOf('.'), s.length());
								j++;
								dados[j] = s.substring(0, s.indexOf(' '));
								s = s.substring(s.indexOf(' ')+1, s.length());
							}
							else if(j == 4)
							{
								dados[j] = s;
							}
							else
							{
								dados[j] = s.substring(0, s.indexOf(' '));
								s = s.substring(s.indexOf(' ')+1, s.length());
							}
						}
						
						ControladorInterface.setArchive(dados[1]+dados[2]);
						ControladorBackend.registrar(8008);
						ControladorInterface.getArchive(dados[0], dados[1], dados[2], dados[3], dados[4], Tcaminho.getText());
						
					}
				});
				Pindex.add(b);
				i_aux = i+1;
			}
		}
	}
}