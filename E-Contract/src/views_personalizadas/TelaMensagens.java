package views_personalizadas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import outros.JPanelTransparent;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;
import javax.swing.JDialog;

public class TelaMensagens extends JDialog {

	private JDialog isto;
	
	public TelaMensagens() {
		
		
		isto = this;
		
		this.setBounds(0, 0, 230, 50);
		
		
		
		 JPanel panel = new  JPanel();
		panel.setBounds(0, 0, 295, 50);
		panel.setBackground(new Color(75, 0, 130));
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("     Mensagens");
		lblNewLabel.setBackground(new Color(0, 0, 153));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBounds(104, 11, 180, 24);
		panel.add(lblNewLabel);
		
		
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
            this.setVisible(true);


	}
}
