package frames;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.HeadlessException;
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
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JRootPane;

import backend.ControladorBackend;

class FrameAutentication extends JFrame implements WindowListener
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel frasePanel = null;
	private JPanel barPanel = null;
	private JLabel titleFraseLabel = null;
	private JLabel titleBarLabel = null;
	private JFormattedTextField fraseTextField = null;
	private JProgressBar progressBar = null;
	private JLabel endFraseLabel = null;
	private JFormattedTextField endTextField = null;
	private JButton buttonSend = null;
	private boolean flagFrasePanel = true;
	private int qtWrongSP = 0;

	protected FrameAutentication()
	{					
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addWindowListener(this);
		setResizable(false);
		this.setBounds(430, 150, 300, 150);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		this.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
		this.setLayout(new BorderLayout());
		this.setVisible(true);

		initializePanels();
		this.add(frasePanel);

		this.validate();

	}

	private void initializePanels()
	{
		frasePanel = new JPanel();
		frasePanel.setLayout(new BoxLayout(frasePanel, BoxLayout.PAGE_AXIS));

		titleFraseLabel = new JLabel("Insira frase secreta:");
		titleFraseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		frasePanel.add(titleFraseLabel);
		titleFraseLabel.setVisible(true);		

		fraseTextField = new JFormattedTextField();
		fraseTextField.setHorizontalAlignment(JFormattedTextField.CENTER);
		fraseTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
		frasePanel.add(fraseTextField);
		fraseTextField.setVisible(true);
		
		endFraseLabel = new JLabel("Insira endereço da chave privada:");
		endFraseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		frasePanel.add(endFraseLabel);
		endFraseLabel.setVisible(true);		
		
		endTextField = new JFormattedTextField();
		endTextField.setHorizontalAlignment(JFormattedTextField.CENTER);
		endTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
		frasePanel.add(endTextField);
		endTextField.setVisible(true);

		buttonSend = new JButton("Autenticar");
		buttonSend.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent e) {
				swapPanels();
				System.out.print("Tentativa nr "+(qtWrongSP+1)+"\n");

				try {
					if(ControladorInterface.checkSecretPhrase(fraseTextField.getText(), endTextField.getText())==false)
					{
						qtWrongSP++;

						if(qtWrongSP<3)
						{
							JOptionPane.showMessageDialog(null, "Frase secreta incorreta (número de tentativas: "+qtWrongSP+")");
							swapPanels();
						}
						else if(qtWrongSP>=3)
						{
							JOptionPane.showMessageDialog(null, "Senha incorreta (usuário bloqueado por 2 minutos)");
							ControladorInterface.changePhase(false);
							//bloqueia user por 2 min
						}
					}
					else{
						ControladorInterface.changePhase(true);
					}
				} catch (HeadlessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				repaint();
				revalidate();
			} 
		} );
		buttonSend.setAlignmentX(Component.CENTER_ALIGNMENT);
		frasePanel.add(buttonSend);
		buttonSend.setVisible(true);

		barPanel = new JPanel();
		barPanel.setLayout(new BoxLayout(barPanel, BoxLayout.PAGE_AXIS));

		titleBarLabel = new JLabel("                         Autenticação em andamento");
		titleBarLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		barPanel.add(titleBarLabel);
		titleBarLabel.setVisible(true);		

		progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);
		barPanel.add(progressBar);
		progressBar.setVisible(true);

	}

	private void swapPanels()
	{
		if(flagFrasePanel == false)
		{
			this.getContentPane().removeAll();
			this.add(frasePanel);
			flagFrasePanel = true;
			this.revalidate();
			this.repaint();
		}
		else
		{
			this.getContentPane().removeAll();
			this.add(barPanel);
			flagFrasePanel = false;
			this.revalidate();
			this.repaint();
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
