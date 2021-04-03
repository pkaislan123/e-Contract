package main.java.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.Font;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaFinanceiro extends JFrame {

	private JPanel contentPane;
	private TelaFinanceiro isto;
	
	public TelaFinanceiro(Window window) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("Finanças");
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Cadastros");
		mnNewMenu.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Instituição Bancária");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaInstituicaoBancaria tela = new TelaInstituicaoBancaria(isto);
				tela.setVisible(true);
				
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[]", "[]"));
		
		
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		this.setLocationRelativeTo(window);
	}

}
