package frames;

import java.awt.BorderLayout;
import java.awt.HeadlessException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;

import backend.ControladorBackend;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.security.cert.CertificateException;

class FrameAccount extends JFrame implements WindowListener
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel titleLabel = null;
	private JFormattedTextField accTextField = null;
	private JButton buttonSend = null;

	public FrameAccount()
	{					
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addWindowListener(this);
		setResizable(false);
		this.setBounds(430, 150, 300, 100);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		this.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
		this.setLayout(new BorderLayout());
		this.setVisible(true);	

		titleLabel = new JLabel("                         Insira nome de usuario:");
		this.add(titleLabel,BorderLayout.NORTH);
		titleLabel.setVisible(true);		

		accTextField = new JFormattedTextField();
		accTextField.setHorizontalAlignment(JFormattedTextField.CENTER);
		this.add(accTextField,BorderLayout.CENTER);
		accTextField.setVisible(true);

		buttonSend = new JButton("Enviar");
		buttonSend.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = accTextField.getText();
				if(username.contains("@")==false || username.contains(".")==false)
					JOptionPane.showMessageDialog(null, "Nome de usuario não possui formato de email");
				else
				{
					try {
						switch(ControladorInterface.checkAccount(username))
						{
						case 0:
							JOptionPane.showMessageDialog(null, "Nome de usuario não encontrado");
							ControladorBackend.registrar(2005);
							break;
						case 1:
							JOptionPane.showMessageDialog(null, "Nome de usuario bloqueado");
							ControladorBackend.registrar(2004);
							break;
						case 2:
							ControladorInterface.changePhase(true);
							ControladorBackend.registrar(2003);
							break;
						default:
							JOptionPane.showMessageDialog(null, "Erro: valor não reconhecido");
							break;
						}
					} catch (HeadlessException | CertificateException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			} 
		} );
		this.add(buttonSend,BorderLayout.SOUTH);
		buttonSend.setVisible(true);

		this.validate();

	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		ControladorBackend.encerrarSistema();
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
