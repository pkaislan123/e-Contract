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

import cadastros.CadastroContrato;
import gui.TelaEnviarMsgWhatsapp;
import outros.JPanelTransparent;
import javax.swing.JTextArea;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;




public class TelaEscolha extends JDialog {
	
	
	private TelaEscolha isto;
	private JLabel lblNewLabel;
	
	
	public TelaEscolha(CadastroContrato contrato) {
		getContentPane().setBackground(new Color(0, 102, 102));
		
		setBounds(100, 100, 286, 98);
		 isto = this;
		getContentPane().setLayout(null);
		
		
		JLabel status_msg_1 = new JLabel("     Enviar contrato via:");
		status_msg_1.setBounds(0, 0, 286, 26);
		getContentPane().add(status_msg_1);
		status_msg_1.setOpaque(true);
		status_msg_1.setBackground(new Color(0, 51, 51));
		status_msg_1.setForeground(new Color(255, 255, 255));
		status_msg_1.setFont(new Font("Cambria", Font.BOLD, 14));
		
		lblNewLabel = new JLabel("");
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				TelaEnviarMsgWhatsapp enviar = new TelaEnviarMsgWhatsapp(contrato);
                isto.dispose();
			}
		});
	
		lblNewLabel.setIcon(new ImageIcon(TelaEscolha.class.getResource("/imagens/waz.png")));
		lblNewLabel.setBounds(64, 37, 48, 48);
		getContentPane().add(lblNewLabel);
		URL url2 = getClass().getResource("/imagens/infinite.gif");
		ImageIcon img2 = new ImageIcon(url2);
		
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		setModal(true);
		setResizable(false);
		setUndecorated(true);
		setVisible(true);
	}
	
	

	
	public void fechar() {
		isto.dispose();
	}
}
