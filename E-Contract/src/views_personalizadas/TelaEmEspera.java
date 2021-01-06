package views_personalizadas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import outros.JPanelTransparent;
import javax.swing.JTextArea;




public class TelaEmEspera extends JDialog {
	
	
	private TelaEmEspera isto;
	private JTextArea textArea;

	private JLabel status_msg;
	public TelaEmEspera() {
		getContentPane().setBackground(new Color(240, 255, 240));
		
		setBounds(100, 100, 286, 98);
		 isto = this;
		getContentPane().setLayout(null);
		
		
		JLabel status_msg_1 = new JLabel("   Operação em andamento");
		status_msg_1.setBounds(0, 0, 286, 26);
		getContentPane().add(status_msg_1);
		status_msg_1.setOpaque(true);
		status_msg_1.setBackground(new Color(75, 0, 130));
		status_msg_1.setForeground(new Color(255, 255, 255));
		status_msg_1.setFont(new Font("Cambria", Font.BOLD, 14));
		
		 	
		 	 status_msg = new JLabel("Aguarde2...");
		 	 status_msg.setBounds(10, 48, 152, 26);
		 	 getContentPane().add(status_msg);
		 	 status_msg.setForeground(Color.BLACK);
		 	 status_msg.setFont(new Font("Cambria", Font.BOLD, 14));
		 	 
		 	 	status_msg.setText("");
		
		JPanelTransparent panel_1 = new JPanelTransparent();
		panel_1.setBounds(216,39, 48, 48);
		getContentPane().add(panel_1);
		URL url2 = getClass().getResource("/imagens/infinite.gif");
		ImageIcon img2 = new ImageIcon(url2);
		panel_1.setImg(img2);
		
		 textArea = new JTextArea();
		 textArea.setText("Teste");
		 textArea.setOpaque(false);
		 textArea.setBounds(10, 26, 191, 61);
		 textArea.setBorder(null);
		 textArea.setEditable(false);

		getContentPane().add(textArea);
		
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		//setModal(true);
		setResizable(false);
		setUndecorated(true);
	}
	
	
	public void setMsg(String texto) {
		textArea.setEditable(false);
		textArea.setEnabled(false);
		textArea.setVisible(false);
		status_msg.setText(texto);
		status_msg.repaint();
		status_msg.updateUI();
	}
	
	public void setMsg2(String texto) {
		
		java.awt.EventQueue.invokeLater(new Runnable() { 
		    public void run() { 
		    	status_msg.setEnabled(false);
				status_msg.setVisible(false);
				textArea.setText(texto);

				textArea.repaint();
				textArea.updateUI();
			  } 
		}); 
		
		


	}
	
	
	public void fechar() {
		isto.dispose();
	}
}
