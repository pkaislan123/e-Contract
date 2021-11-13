package main.java.views_personalizadas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import javax.script.*;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;

import main.java.cadastros.CadastroNota;

import javax.swing.ImageIcon;
import java.awt.Insets;
import net.miginfocom.swing.MigLayout;
import javax.swing.JScrollPane;
import javax.swing.border.MatteBorder;

public class TelaInformarNotificacao extends JFrame {

	private TelaInformarNotificacao isto;
	private JTextArea textoNotificacao;
	private JLabel lblNome;
	public TelaInformarNotificacao( ) {
		
		
		isto = this;
		
		this.setBounds(0, 0, 385, 221);
		
		
		
		 JPanel panel = new  JPanel();
		panel.setBounds(0, 0, 295, 100);
		panel.setBackground(new Color(0, 0, 204));
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("   Notificação de Anotação");
		lblNewLabel.setBounds(131, 7, 238, 17);
		lblNewLabel.setBackground(new Color(0, 128, 128));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel.setOpaque(true);
		panel.add(lblNewLabel);
		
		 textoNotificacao = new JTextArea();
		 textoNotificacao.setBounds(81, 58, 238, 73);
		 textoNotificacao.setMargin(new Insets(8, 8, 8, 8));
		 textoNotificacao.setText("teste");
		 textoNotificacao.setToolTipText("teste");
			textoNotificacao.setBackground(new Color(0, 0, 204));
			textoNotificacao.setLineWrap(true);
			textoNotificacao.setWrapStyleWord(true);
		 textoNotificacao.setFont(new Font("Arial", Font.BOLD, 16));
		textoNotificacao.setForeground(Color.WHITE);
		textoNotificacao.setEditable(false);
		textoNotificacao.setBorder(null);


		JScrollPane scrollPane = new JScrollPane(textoNotificacao);
		scrollPane.setBorder(null);
		scrollPane.setBounds(81, 89, 278, 82);
		panel.add(scrollPane);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(7, 7, 64, 48);
		lblNewLabel_1.setIcon(new ImageIcon(TelaNotificacao.class.getResource("/imagens/logo_64.png")));
		panel.add(lblNewLabel_1);
		
		 lblNome = new JLabel("Nome");
		lblNome.setBorder(new MatteBorder(0, 0, 2, 0, (Color) Color.WHITE));
		lblNome.setForeground(Color.WHITE);
		lblNome.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNome.setBounds(82, 53, 238, 14);
		panel.add(lblNome);
		

		
		 Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
	        java.awt.Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
	        int taskBarHeight = scrnSize.height - winSize.height;
	        System.out.printf("Altura: %d\n", taskBarHeight);
	        
	        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
	        java.awt.Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
	        int x = (int) rect.getMaxX() - getWidth( ) ;
	        int y = 10 ;
		
			setAlwaysOnTop(true);

			
		
			    GraphicsDevice[] gd = ge.getScreenDevices();
			    if(gd.length > 5) {
				        rect = gd[1].getDefaultConfiguration().getBounds();
				         x = (int) rect.getMaxX() - getWidth( ) ;
				         y = 80 ;
			           this.setLocation(x, y);

			    }else {
			           this.setLocation(x, y);

			    }
			
			
		

			this.setUndecorated(false);


        

	}
	
	
	public void fechar() {
		isto.dispose();
	}
	
	public void setMensagem(CadastroNota  nota) {
		java.awt.EventQueue.invokeLater(new Runnable() { 
		    public void run() { 
		    	
		    	
		    	lblNome.setText(nota.getNome());
		    	lblNome.setBorder(null);
		    	lblNome.repaint();
		    	lblNome.updateUI();
		    	lblNome.setBorder(null);

		    	
		    	textoNotificacao.setText(nota.getTexto());
		    	textoNotificacao.setBorder(null);
				textoNotificacao.repaint();
				textoNotificacao.updateUI();
		    	textoNotificacao.setBorder(null);

						   
		    } 
		}); 
		
	
	}
}
