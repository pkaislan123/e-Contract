
package main.java.views_personalizadas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.java.cadastros.CadastroContrato;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.gui.TelaEnviarMsgEmailDocsGeral;
import main.java.gui.TelaEnviarMsgMail;
import main.java.gui.TelaEnviarMsgWhatsapp;
import main.java.gui.TelaGerenciarContrato;

import javax.swing.JTextArea;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import main.java.gui.TelaConfirmarCarregamento;
import main.java.gui.TelaConfirmarRecebimento;



public class TelaOpcoes extends JDialog {
	
	
	private TelaOpcoes isto;
	private int id_contrato;
	
	public TelaOpcoes(int flag,int id_contrato_, Window janela_pai) {

		this.setModal(true);
		getContentPane().setBackground(Color.WHITE);
		
		setBounds(100, 100, 536, 266);
		 isto = this;
		getContentPane().setLayout(null);
		
		
		JLabel status_msg_1 = new JLabel(" Opções:");
		status_msg_1.setBounds(0, 2, 530, 40);
		getContentPane().add(status_msg_1);
		status_msg_1.setOpaque(true);
		status_msg_1.setBackground(new Color(0, 51, 51));
		status_msg_1.setForeground(new Color(255, 255, 255));
		status_msg_1.setFont(new Font("Cambria", Font.BOLD, 24));
		
		JLabel status_msg_1_1 = new JLabel("Romaneio já associado a outro controle");
		status_msg_1_1.setOpaque(true);
		status_msg_1_1.setForeground(new Color(153, 0, 0));
		status_msg_1_1.setFont(new Font("Cambria", Font.BOLD, 14));
		status_msg_1_1.setBackground(Color.WHITE);
		status_msg_1_1.setBounds(46, 58, 286, 40);
		getContentPane().add(status_msg_1_1);
		
		JButton btnNewButton_1 = new JButton("Prosseguir");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(flag == 1) {
					( (TelaConfirmarCarregamento) janela_pai).setProsseguir(true);
					isto.dispose();
					}else if(flag == 2) {
						( (TelaConfirmarRecebimento) janela_pai).setProsseguir(true);
						isto.dispose();
						
					}
			}
		});
		btnNewButton_1.setBackground(new Color(0, 51, 0));
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.setFont(new Font("Arial", Font.BOLD, 14));
		btnNewButton_1.setBounds(46, 139, 116, 31);
		getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_1_1 = new JButton("Abrir Carregamento");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CadastroContrato contrato_selecionado = new GerenciarBancoContratos().getContrato(id_contrato);
				TelaGerenciarContrato gerenciar_contrato = new TelaGerenciarContrato(contrato_selecionado, isto);
				gerenciar_contrato.setTelaRecebimentos(id_contrato);
			}
		});
		btnNewButton_1_1.setBackground(new Color(0, 0, 102));
		btnNewButton_1_1.setForeground(Color.WHITE);
		btnNewButton_1_1.setFont(new Font("Arial", Font.BOLD, 14));
		btnNewButton_1_1.setBounds(172, 139, 180, 31);
		getContentPane().add(btnNewButton_1_1);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(flag == 1) {
				( (TelaConfirmarCarregamento) janela_pai).setProsseguir(false);
				isto.dispose();
				}else if(flag == 2) {
					( (TelaConfirmarRecebimento) janela_pai).setProsseguir(false);
					isto.dispose();
					
				}
			}
		});
		btnCancelar.setBackground(new Color(204, 0, 0));
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setFont(new Font("Arial", Font.BOLD, 14));
		btnCancelar.setBounds(356, 139, 116, 31);
		getContentPane().add(btnCancelar);
		
		JLabel status_msg_1_1_1 = new JLabel("Se esta editando uma replica, clique em prosseguir");
		status_msg_1_1_1.setOpaque(true);
		status_msg_1_1_1.setForeground(Color.BLACK);
		status_msg_1_1_1.setFont(new Font("Cambria", Font.BOLD, 14));
		status_msg_1_1_1.setBackground(Color.WHITE);
		status_msg_1_1_1.setBounds(46, 90, 376, 40);
		getContentPane().add(status_msg_1_1_1);
		URL url2 = getClass().getResource("/imagens/infinite.gif");
		ImageIcon img2 = new ImageIcon(url2);
		
		setLocationRelativeTo(janela_pai);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		setResizable(true);
		setUndecorated(false);
	}

	
	public void fechar() {
		isto.dispose();
	}
}

