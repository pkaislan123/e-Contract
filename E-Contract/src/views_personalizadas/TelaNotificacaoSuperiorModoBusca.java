package views_personalizadas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import javax.script.*;
import javax.swing.JDialog;

import outros.JPanelTransparent;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;
import java.awt.Insets;
import net.miginfocom.swing.MigLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TelaNotificacaoSuperiorModoBusca extends JDialog {

	private TelaNotificacaoSuperiorModoBusca isto;
	private JTextArea statusNotificacao;
	
	public TelaNotificacaoSuperiorModoBusca() {
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(TelaNotificacaoSuperiorModoBusca.class.getResource("/imagens/logo_icone4.png")));
		
		
		isto = this;
		
		this.setBounds(0, 0, 268, 135);
		
		
		
		 JPanel panel = new  JPanel();
		panel.setBounds(0, 0, 295, 100);
		panel.setBackground(new Color(0, 0, 139));
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Buscar novos romaneios\r\n");
		lblNewLabel.setBounds(69, 7, 183, 17);
		lblNewLabel.setBackground(new Color(0, 128, 128));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel.setOpaque(true);
		panel.add(lblNewLabel);
		
		 statusNotificacao = new JTextArea();
		 statusNotificacao.setBounds(81, 35, 159, 54);
		 statusNotificacao.setMargin(new Insets(8, 8, 8, 8));
		 statusNotificacao.setText("teste");
		 statusNotificacao.setToolTipText("teste");
			statusNotificacao.setBackground(new Color(0,0,0,0));
			statusNotificacao.setLineWrap(true);
			statusNotificacao.setWrapStyleWord(true);
		 statusNotificacao.setFont(new Font("Arial", Font.PLAIN, 14));
		statusNotificacao.setForeground(Color.WHITE);
		statusNotificacao.setEditable(false);
		statusNotificacao.setBorder(null);
		statusNotificacao.setOpaque(false);

		panel.add(statusNotificacao);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(7, 7, 64, 48);
		lblNewLabel_1.setIcon(new ImageIcon(TelaNotificacao.class.getResource("/imagens/logo_64.png")));
		panel.add(lblNewLabel_1);

		   Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
	        java.awt.Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
	        int taskBarHeight = scrnSize.height - winSize.height;
	        System.out.printf("Altura: %d\n", taskBarHeight);
	        
	        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
	        java.awt.Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
	        int x = (int) rect.getMaxX() - getWidth( ) - 10;
	        int y = (int) rect.getMaxY() - getHeight() - taskBarHeight  -90;
		
			setAlwaysOnTop(true);

			isto.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
			//Adicionando uma nova ação
			isto.addWindowListener(new WindowAdapter() {
			    public void windowClosing(WindowEvent we) {
			       
			    }
			});

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
