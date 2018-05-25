package frames;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRootPane;

import backend.ControladorBackend;

public class FrameMain extends JFrame implements WindowListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static PanelAdmin panelAdmin = null;
	private static PanelCadastro panelCadastro = null;
	private static PanelListar panelListar = null;
	private static PanelConsultar panelConsultar = null;
	private static PanelSaida panelSaida = null;
	private boolean flagFrasePanel = true;
	private int qtWrongSP = 0;

	String login, grupo, nome, acessos, totalUsuarios;

	protected FrameMain(String[] info)
	{
		this.login = info[0];
		this.grupo = info[1];
		this.nome = info[2];
		this.acessos = info[3];
		this.totalUsuarios = info[4];

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addWindowListener(this);
		setResizable(false);
		this.setBounds(430, 150, 350, 300);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		this.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
		this.setLayout(new BorderLayout());
		this.setVisible(true);

		panelAdmin = new PanelAdmin(login, grupo, nome, acessos, this);
		this.add(panelAdmin);

		this.validate();

	}

	protected void changePanel(int option)
	{
		switch(option)
		{
			case 0:
				this.getContentPane().removeAll();
				this.setBounds(430, 150, 350, 300);
				this.setLocationRelativeTo(null);
				panelAdmin = new PanelAdmin(login, grupo, nome, acessos, this);
				this.add(panelAdmin);
				panelAdmin.setVisible(true);
				this.revalidate();
				this.repaint();
				break;
			case 1:
				this.getContentPane().removeAll();
				this.setBounds(430, 150, 500, 320);
				this.setLocationRelativeTo(null);
				panelCadastro = new PanelCadastro(login, grupo, nome, totalUsuarios, this);
				this.add(panelCadastro);
				panelCadastro.setVisible(true);
				this.revalidate();
				this.repaint();
				ControladorBackend.registrar(6001);
				break;
			case 2:
				//incrementar no banco de dados uma consulta de chave privada/ certificado;
				this.getContentPane().removeAll();
				this.setBounds(430, 150, 650, 600);
				this.setLocationRelativeTo(null);
				panelListar = new PanelListar(login, grupo, nome, this);
				this.add(panelListar);
				panelListar.setVisible(true);
				this.revalidate();
				this.repaint();
				ControladorBackend.registrar(7001);
				break;
			case 3:
				this.getContentPane().removeAll();
				this.setBounds(430, 150, 500, 400);
				this.setLocationRelativeTo(null);
				panelConsultar = new PanelConsultar(login, grupo, nome, this);
				this.add(panelConsultar);
				panelConsultar.setVisible(true);
				this.revalidate();
				this.repaint();
				ControladorBackend.registrar(8001);
				break;
			case 4:
				this.getContentPane().removeAll();
				this.setBounds(430, 150, 300, 300);
				this.setLocationRelativeTo(null);
				panelSaida = new PanelSaida(login, grupo, nome, acessos, this);
				this.add(panelSaida);
				panelSaida.setVisible(true);
				this.revalidate();
				this.repaint();
				ControladorBackend.registrar(9001);
				break;
			default:
		}
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		ControladorBackend.encerrarSistema();
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}
