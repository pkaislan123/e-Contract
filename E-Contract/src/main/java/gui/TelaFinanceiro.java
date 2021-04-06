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
import java.awt.Insets;
import javax.swing.ImageIcon;

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
		mnNewMenu.setMargin(new Insets(0, 10, 0, 0));
		mnNewMenu.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Instituição Bancária");
		mntmNewMenuItem.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmNewMenuItem.setMargin(new Insets(0, 10, 0, 0));
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaFinanceiroInstituicaoBancaria tela = new TelaFinanceiroInstituicaoBancaria(-1,-1,isto);
				tela.setVisible(true);
				
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Centro de Custo");
		mntmNewMenuItem_1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmNewMenuItem_1.setIcon(new ImageIcon(TelaFinanceiro.class.getResource("/imagens/centro_custo_24x24.jpg")));
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaFinanceiroCentroCusto tela = new TelaFinanceiroCentroCusto(-1,-1,isto);
				tela.setVisible(true);
			}
		});
		mntmNewMenuItem_1.setMargin(new Insets(0, 10, 0, 0));
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Condição de Pagamento");
		mntmNewMenuItem_2.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaFinanceiroCondicaoPagamento tela = new TelaFinanceiroCondicaoPagamento(-1,-1,isto);
				tela.setVisible(true);
			}
		});
		mntmNewMenuItem_2.setMargin(new Insets(0, 10, 0, 0));
		mnNewMenu.add(mntmNewMenuItem_2);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Grupo de Contas");
		mntmNewMenuItem_3.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaFinanceiroGrupoConta tela = new TelaFinanceiroGrupoConta(-1,-1,isto);
				tela.setVisible(true);
			}
		});
		mntmNewMenuItem_3.setMargin(new Insets(0, 10, 0, 0));
		mnNewMenu.add(mntmNewMenuItem_3);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Contas");
		mntmNewMenuItem_4.setIcon(new ImageIcon(TelaFinanceiro.class.getResource("/imagens/conta-bancaria_24x24.png")));
		mntmNewMenuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaFinanceiroConta tela = new TelaFinanceiroConta(-1,-1,isto);
				tela.setVisible(true);
			}
		});
		mntmNewMenuItem_4.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmNewMenuItem_4.setMargin(new Insets(0, 10, 0, 0));
		mnNewMenu.add(mntmNewMenuItem_4);
		
		JMenu mnNewMenu_1 = new JMenu("Lançamentos");
		mnNewMenu_1.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuBar.add(mnNewMenu_1);
		
		JMenuItem lancamentos = new JMenuItem("Lançamentos");
		lancamentos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			   TelaFinanceiroLancamento tela = new TelaFinanceiroLancamento(-1,-1,isto);
			   tela.setVisible(true);
			}
		});
		mnNewMenu_1.add(lancamentos);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[]", "[]"));
		
		
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		this.setLocationRelativeTo(window);
	}

}
