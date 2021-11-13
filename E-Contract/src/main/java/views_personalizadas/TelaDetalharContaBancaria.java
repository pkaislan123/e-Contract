package main.java.views_personalizadas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;

import main.java.cadastros.ContaBancaria;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;

import net.miginfocom.swing.MigLayout;

public class TelaDetalharContaBancaria extends JFrame {

	private TelaDetalharContaBancaria isto;
	private JTextArea textArea ;
	public TelaDetalharContaBancaria(Window window, ContaBancaria conta_bancaria) {

		isto = this;
		this.setTitle("Detalhar Conta Bancária");
		this.setBounds(0, 0, 463, 269);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 295, 50);
		panel.setBackground(Color.WHITE);
		getContentPane().add(panel);
		panel.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		 textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setFont(new Font("Arial", Font.PLAIN, 16));
		panel.add(textArea, "cell 0 0,grow");

		detalharContaBancaria(conta_bancaria);
		this.setLocationRelativeTo(window);

	}
	
	
	public void detalharContaBancaria(ContaBancaria conta_bancaria) {
	   
		String texto = "Conta Bancária\nCpf Titular: " + conta_bancaria.getCpf_titular() + "\n";
		texto += ("Nome: " + conta_bancaria.getNome() + "\n");
		texto += ("Banco: " + conta_bancaria.getBanco() + "\n");
		texto += ("Código: " + conta_bancaria.getCodigo() + "\n");
		texto += ("Agência: " + conta_bancaria.getAgencia() + "\n");
		texto += ("Conta: " + conta_bancaria.getConta() + "\n");

		
		textArea.setText(texto);

	}
	
}
