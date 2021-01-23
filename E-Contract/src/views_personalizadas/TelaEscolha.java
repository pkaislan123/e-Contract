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
import gui.TelaEnviarMsgMail;
import gui.TelaEnviarMsgWhatsapp;
import outros.JPanelTransparent;
import javax.swing.JTextArea;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;




public class TelaEscolha extends JDialog {
	
	
	private TelaEscolha isto;
	private JLabel lblNewLabel;
	
	
	public TelaEscolha(int flag, CadastroContrato contrato, File documento) {
		getContentPane().setBackground(new Color(0, 102, 102));
		
		setBounds(100, 100, 286, 98);
		 isto = this;
		getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("X");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isto.dispose();
			}
		});
		btnNewButton.setBorder(null);
		btnNewButton.setBackground(Color.BLACK);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBounds(248, 0, 38, 29);
		getContentPane().add(btnNewButton);
		
		
		JLabel status_msg_1 = new JLabel("     Enviar contrato via:");
		status_msg_1.setBounds(0, 2, 286, 26);
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
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				TelaEnviarMsgMail tela = new TelaEnviarMsgMail(flag, contrato, documento);
				tela.setVisible(true);
				isto.dispose();
			}
		});
		lblNewLabel_1.setIcon(new ImageIcon(TelaEscolha.class.getResource("/imagens/icone_gmail.png")));
		lblNewLabel_1.setBounds(136, 37, 48, 48);
		getContentPane().add(lblNewLabel_1);
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
