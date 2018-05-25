package frames;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JToolBar;

import backend.ControladorBackend;

class FramePassword extends JFrame implements ActionListener, WindowListener
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel titleLabel = null;
	private String textButton1 = null;
	private String textButton2 = null;
	private String textButton3 = null;
	private String textButton4 = null;
	private String textButton5 = null;
	private JToolBar buttonsToolBar = null;
	private JButton button1 = null;
	private JButton button2 = null;
	private JButton button3 = null;
	private JButton button4 = null;
	private JButton button5 = null;
	private JButton buttonSend = null;
	private ArrayList<String> passwordValues = null;
	private int qtButtonsClicked = 0;
	private int qtWrongPW = 0;

	protected FramePassword()
	{					
		//Formatando o frame e inicializando variaveis
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addWindowListener(this);
		setResizable(false);
		this.setBounds(430, 150, 253, 100);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		this.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
		this.setLayout(new BorderLayout());
		this.setVisible(true);
		passwordValues = new ArrayList<String>();

		//Criando e inserindo o label de titulo
		titleLabel = new JLabel("                            Insira senha:");
		this.add(titleLabel,BorderLayout.NORTH);
		titleLabel.setVisible(true);	

		//Inicializando valores
		textButton1 = new String();
		textButton2 = new String();
		textButton3 = new String();
		textButton4 = new String();
		textButton5 = new String();
		randomizeValuesToolBar();

		//Criando a toolbar de buttons
		buttonsToolBar = new JToolBar();
		buttonsToolBar.setOrientation(JToolBar.HORIZONTAL);
		buttonsToolBar.setFloatable(false);
		buttonsToolBar.setVisible(true);
		initializeButtonsToolBar();
		this.add(buttonsToolBar,BorderLayout.CENTER);

		//Criando button de enviar senha
		buttonSend = new JButton("Enviar");
		buttonSend.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent e) {
							
				System.out.print("Tentativa nr "+(qtWrongPW+1)+": ");
				for(int i=0; i<passwordValues.size();i++)
				{
					System.out.print(passwordValues.get(i)+" ");					
				}System.out.print("\n");
				
				try {
					if(ControladorInterface.checkPassword(passwordValues)==true)
					{
						qtWrongPW = 0;
						buttonSend.setEnabled(false);
						button1.setEnabled(false);
						button2.setEnabled(false);
						button3.setEnabled(false);
						button4.setEnabled(false);
						button5.setEnabled(false);
						ControladorInterface.changePhase(true);
					}
					else
					{
						qtWrongPW++;
						
						if(qtWrongPW<3)
						{
							JOptionPane.showMessageDialog(null, "Senha incorreta (número de tentativas: "+qtWrongPW+")");
							if(qtWrongPW == 1) 
								ControladorBackend.registrar(3007);
							else if(qtWrongPW == 2) 
								ControladorBackend.registrar(3008);
							resetFrame();
						}
						else if(qtWrongPW>=3)
						{
							ControladorBackend.registrar(3009);
							ControladorBackend.registrar(3010);
							JOptionPane.showMessageDialog(null, "Senha incorreta (usuário bloqueado por 2 minutos)");
							ControladorInterface.changePhase(false);
							ControladorInterface.bloquearUsuario();
						}
					}
				} catch (HeadlessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				

			} 
		} );
		buttonSend.setEnabled(false);
		buttonSend.setBorderPainted(false);
		buttonSend.setFocusPainted(false);
		this.add(buttonSend,BorderLayout.SOUTH);
		buttonSend.setVisible(true);

		this.validate();

	}

	private void randomizeValuesToolBar()
	{
		ArrayList<Integer> values = new ArrayList<Integer>();
		Integer[] aux = new Integer[] {1,2,3,4,5,6,7,8,9,0};
		values.addAll(Arrays.asList(aux));
		Random random = new Random();

		for (int i = 0; values.size()>0; i++) 
		{ 
			int idx = random.nextInt(values.size());
			int value1 = values.get(idx);
			values.remove(idx);
			idx = random.nextInt(values.size());
			int value2 = values.get(idx);
			values.remove(idx);

			switch(i){
			case(0):
				textButton1 = Integer.toString(value1)+"-"+Integer.toString(value2);
			break;
			case(1):
				textButton2 = Integer.toString(value1)+"-"+Integer.toString(value2);
			break;
			case(2):
				textButton3 = Integer.toString(value1)+"-"+Integer.toString(value2);
			break;
			case(3):
				textButton4 = Integer.toString(value1)+"-"+Integer.toString(value2);
			break;
			case(4):
				textButton5 = Integer.toString(value1)+"-"+Integer.toString(value2);
			break;
			}
		} 
	}

	private void repaintButtons()
	{
		button1.setText(textButton1);
		button1.revalidate();
		button1.repaint();
		button2.setText(textButton2);
		button2.revalidate();
		button2.repaint();
		button3.setText(textButton3);
		button3.revalidate();
		button3.repaint();
		button4.setText(textButton4);
		button4.revalidate();
		button4.repaint();
		button5.setText(textButton5);
		button5.revalidate();
		button5.repaint();
		buttonsToolBar.revalidate();
		buttonsToolBar.repaint();
		this.revalidate();
		this.repaint();
	}

	private void initializeButtonsToolBar()
	{
		//Inicializando botão 1
		button1 = new JButton(textButton1);
		button1.setMargin(new Insets(9, 12, 9, 12));
		button1.setHorizontalTextPosition(JButton.CENTER);
		button1.setVerticalTextPosition(JButton.BOTTOM);
		button1.addActionListener(this);   
		buttonsToolBar.add(button1, BorderLayout.CENTER);

		//Inicializando botão 2
		button2 = new JButton(textButton2);
		button2.setMargin(new Insets(9, 12, 9, 12));
		button2.setHorizontalTextPosition(JButton.CENTER);
		button2.setVerticalTextPosition(JButton.BOTTOM);		
		button2.addActionListener(this);  
		buttonsToolBar.add(button2, BorderLayout.CENTER);

		//Inicializando botão 3
		button3 = new JButton(textButton3);
		button3.setMargin(new Insets(9, 12, 9, 12));
		button3.setHorizontalTextPosition(JButton.CENTER);
		button3.setVerticalTextPosition(JButton.BOTTOM);
		button3.addActionListener(this);
		buttonsToolBar.add(button3, BorderLayout.CENTER);

		//Inicializando botão 4
		button4 = new JButton(textButton4);
		button4.setMargin(new Insets(9, 12, 9, 12));
		button4.setHorizontalTextPosition(JButton.CENTER);
		button4.setVerticalTextPosition(JButton.BOTTOM);
		button4.addActionListener(this);
		buttonsToolBar.add(button4, BorderLayout.CENTER);

		//Inicializando botão 5
		button5 = new JButton(textButton5);
		button5.setMargin(new Insets(9, 12, 9, 12));
		button5.setHorizontalTextPosition(JButton.CENTER);
		button5.setVerticalTextPosition(JButton.BOTTOM);
		button5.addActionListener(this);
		buttonsToolBar.add(button5, BorderLayout.CENTER);

	}
	
	private void resetFrame()
	{
		passwordValues = new ArrayList<String>();
		textButton1 = new String();
		textButton2 = new String();
		textButton3 = new String();
		textButton4 = new String();
		textButton5 = new String();
		randomizeValuesToolBar();
		repaintButtons();
		button1.setEnabled(true);
		button2.setEnabled(true);
		button3.setEnabled(true);
		button4.setEnabled(true);
		button5.setEnabled(true);
		qtButtonsClicked = 0;
		buttonSend.setEnabled(false);
		buttonSend.setBorderPainted(false);
		buttonSend.setFocusPainted(false);
		
	}

	public void actionPerformed(ActionEvent e) //function to handle click
	{
		qtButtonsClicked++;
		
		if(qtButtonsClicked == 6)
		{
			buttonSend.setEnabled(true);
			buttonSend.setBorderPainted(true);
			buttonSend.setFocusPainted(true);
		}
		if(qtButtonsClicked<=8)
		{		
			JButton b = (JButton)e.getSource();
			passwordValues.add(b.getText());
			randomizeValuesToolBar();
			repaintButtons();
		}
		else if(qtButtonsClicked == 8)
		{
			button1.setEnabled(false);
			button2.setEnabled(false);
			button3.setEnabled(false);
			button4.setEnabled(false);
			button5.setEnabled(false);
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
