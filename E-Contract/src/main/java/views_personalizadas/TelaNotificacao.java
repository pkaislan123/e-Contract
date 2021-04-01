package main.java.views_personalizadas;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import javax.swing.JDialog;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;

public class TelaNotificacao extends JDialog {

	private TelaNotificacao isto;
	private JTextArea statusNotificacao;
	
	public TelaNotificacao() {
		
		
		isto = this;
		
		this.setBounds(0, 0, 300, 153);
		
		
		
		 JPanel panel = new  JPanel();
		panel.setBounds(0, 0, 295, 144);
		panel.setBackground(new Color(75, 0, 130));
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("   Nova Notificação");
		lblNewLabel.setBackground(new Color(0, 0, 153));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBounds(130, 11, 183, 24);
		panel.add(lblNewLabel);
		
		 statusNotificacao = new JTextArea();
			statusNotificacao.setBackground(new Color(0,0,0,0));
			statusNotificacao.setLineWrap(true);
			statusNotificacao.setWrapStyleWord(true);
		 statusNotificacao.setFont(new Font("Arial", Font.BOLD, 14));
		statusNotificacao.setForeground(new Color(255, 255, 255));
		statusNotificacao.setEditable(false);
		statusNotificacao.setOpaque(false);
		statusNotificacao.setBounds(37, 70, 231, 60);
		statusNotificacao.setBorder(null);
		panel.add(statusNotificacao);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(TelaNotificacao.class.getResource("/imagens/logo_64.png")));
		lblNewLabel_1.setBounds(10, 11, 54, 48);
		panel.add(lblNewLabel_1);

		   Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
	        java.awt.Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
	        int taskBarHeight = scrnSize.height - winSize.height;
	        System.out.printf("Altura: %d\n", taskBarHeight);
	        
	        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
	        java.awt.Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
	        int x = (int) rect.getMaxX() - getWidth( ) - 10;
	        int y = (int) rect.getMaxY() - getHeight() - taskBarHeight  - 10;
		
			setAlwaysOnTop(true);

			this.setUndecorated(true);

        this.setLocation(x, y);


	}
	
	
	public void fechar() {
		isto.dispose();
	}
	
	public void setMensagem(String msg) {
		java.awt.EventQueue.invokeLater(new Runnable() { 
		    public void run() { 
		    	statusNotificacao.setText(msg);
		    	statusNotificacao.setBorder(null);
				statusNotificacao.repaint();
				statusNotificacao.updateUI();
		    	statusNotificacao.setBorder(null);

						   
		    } 
		}); 
		
	
	}
}
