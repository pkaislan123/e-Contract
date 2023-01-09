package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroNFe;
import main.java.cadastros.CadastroProduto;
import main.java.classesExtras.CBProdutoPersonalizado;
import main.java.classesExtras.CBProdutoRenderPersonalizado;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.outros.GetData;
import main.java.tratamento_proprio.Log;
import net.miginfocom.swing.MigLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class TelaFazendaCadastroCompra extends JDialog {

	private JTabbedPane painelPrincipal ;
	private JPanel painelNotaFiscal = new JPanel();
	private JPanel painelItens = new JPanel();
	private TelaFazendaCadastroCompra isto = this;
	private JTextField entCodigo;
	private JTextField entSerie;
	private JComboBox cbNatureza;
	private JTextField entProtocolo;
	private JTextField entDataNF;
	private JTextField entCaminhoArquivo;
	private JTextField entValor;
	private CadastroCliente remetente, destinatario;
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private CBProdutoPersonalizado modelProduto = new CBProdutoPersonalizado();

	private CBProdutoRenderPersonalizado cBProdutoPersonalizado;
	private JComboBox cbRemetente, cbDestinatario;
	private JTable table;
	 
	public TelaFazendaCadastroCompra(Window janela_pai) {
		
		setModal(true);

		
		setResizable(false);
	
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("E-Contract - Tela Padrao Abas");
		setBounds(100, 100, 993, 586);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		painelPrincipal = new JTabbedPane();
		
		painelNotaFiscal.setBackground(new Color(255, 255, 255));
		painelItens.setBackground(new Color(255, 255, 255));

		//adiciona novos paines e suas abas
		painelPrincipal.addTab("Nota Fiscal", painelNotaFiscal);
		painelNotaFiscal.setLayout(null);
		painelPrincipal.addTab("Itens", painelItens);
		
		getContentPane().add(painelPrincipal, BorderLayout.CENTER);

		painelNotaFiscal.setBackground(new Color(255, 255, 255));
		painelItens.setBackground(new Color(255, 255, 255));
		painelItens.setLayout(new MigLayout("", "[grow]", "[][grow]"));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		painelItens.add(panel, "cell 0 1,grow");
		panel.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		
		
		table = new JTable();
		JScrollPane scrollPane = new JScrollPane(table);
		panel.add(scrollPane, "cell 0 0,grow");

		painelNotaFiscal.setBackground(Color.WHITE);

		painelNotaFiscal.setLayout(new MigLayout("", "[][grow][]", "[][][][][][][][][][][][][][][][]"));

		JLabel lblNewLabel = new JLabel("Código:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelNotaFiscal.add(lblNewLabel, "cell 0 0,alignx trailing");

		entCodigo = new JTextField();
		entCodigo.setFont(new Font("Tahoma", Font.BOLD, 16));
		painelNotaFiscal.add(entCodigo, "cell 1 0,growx");
		entCodigo.setColumns(10);

		JLabel lblSrie = new JLabel("Série:");
		lblSrie.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelNotaFiscal.add(lblSrie, "cell 0 1,alignx trailing");

		entSerie = new JTextField();
		entSerie.setFont(new Font("Tahoma", Font.BOLD, 16));
		entSerie.setColumns(10);
		painelNotaFiscal.add(entSerie, "cell 1 1,growx");

		JLabel lblNatureza = new JLabel("Natureza:");
		lblNatureza.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelNotaFiscal.add(lblNatureza, "cell 0 2,alignx trailing");

		cbNatureza = new JComboBox();
		cbNatureza.setFont(new Font("SansSerif", Font.BOLD, 14));
		painelNotaFiscal.add(cbNatureza, "cell 1 2,growx");
		cbNatureza.addItem("RETORNO SIMBOLICO DE MERCADORIA DEPOSITADA EM DEPOSITO FECHADO");
		cbNatureza.addItem("COMPRA");
		cbNatureza.addItem("VENDA");
		cbNatureza.addItem("RETORNO DE MERCADORIA DEPOSITADA EM DEPOSITO FECHADO OU ARMA");
		cbNatureza.addItem("REMESSA");
		cbNatureza.addItem("Merc.Receb. P/ Deposito");
		cbNatureza.addItem("RETORNO MERC.DEP.ARMAZEM GERAL");

		JLabel lblProtocolo = new JLabel("Protocolo:");
		lblProtocolo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelNotaFiscal.add(lblProtocolo, "cell 0 3,alignx trailing");

		entProtocolo = new JTextField();
		entProtocolo.setFont(new Font("Tahoma", Font.BOLD, 16));
		entProtocolo.setColumns(10);
		painelNotaFiscal.add(entProtocolo, "cell 1 3,growx");

		JLabel lblDataDaNf = new JLabel("Data da NF:");
		lblDataDaNf.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelNotaFiscal.add(lblDataDaNf, "cell 0 4,alignx trailing");

		entDataNF = new JTextField();
		entDataNF.setFont(new Font("Tahoma", Font.BOLD, 16));
		entDataNF.setColumns(10);
		painelNotaFiscal.add(entDataNF, "cell 1 4,growx");
		entDataNF.setText(new GetData().getData());
		JLabel lblRemetente = new JLabel("Remetente:");
		lblRemetente.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelNotaFiscal.add(lblRemetente, "cell 0 5,alignx trailing");

		cbRemetente = new JComboBox();
		cbRemetente.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelNotaFiscal.add(cbRemetente, "cell 1 5,growx");

		JButton btnSelecionarRemetente = new JButton("Selecionar");
		btnSelecionarRemetente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaCliente tela = new TelaCliente(0, 90, isto);
				tela.setVisible(true);

			}
		});
		btnSelecionarRemetente.setBackground(new Color(0, 0, 153));
		btnSelecionarRemetente.setForeground(Color.WHITE);
		btnSelecionarRemetente.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelNotaFiscal.add(btnSelecionarRemetente, "cell 2 5");

		JLabel lblDestinatrio = new JLabel("Destinatário:");
		lblDestinatrio.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelNotaFiscal.add(lblDestinatrio, "cell 0 6,alignx trailing");

		cbDestinatario = new JComboBox();
		cbDestinatario.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelNotaFiscal.add(cbDestinatario, "cell 1 6,growx");

		JButton btnSelecionarDestinatario = new JButton("Selecionar");
		btnSelecionarDestinatario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaCliente tela = new TelaCliente(0, 91, isto);
				tela.setVisible(true);

			}
		});
		btnSelecionarDestinatario.setForeground(Color.WHITE);
		btnSelecionarDestinatario.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnSelecionarDestinatario.setBackground(new Color(0, 0, 153));
		painelNotaFiscal.add(btnSelecionarDestinatario, "cell 2 6");

		cBProdutoPersonalizado = new CBProdutoRenderPersonalizado();

		GerenciarBancoProdutos listaProdutos = new GerenciarBancoProdutos();
		ArrayList<CadastroProduto> produtos = listaProdutos.getProdutos();

		for (CadastroProduto produto : produtos) {
			modelProduto.addProduto(produto);

		}

		JLabel lblValor = new JLabel("Valor Total:");
		lblValor.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelNotaFiscal.add(lblValor, "cell 0 7 1 2,alignx trailing");

		entValor = new JTextField();
		entValor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = ",.0987654321\b";// lista de caracters que não devem ser aceitos
				String s_valor = "";

				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();// aciona esse propriedade para eliminar a ação do evento

				} 
			}
		});
		entValor.setFont(new Font("Tahoma", Font.BOLD, 16));
		entValor.setColumns(10);
		painelNotaFiscal.add(entValor, "cell 1 7 1 2,growx");
		
				JLabel lblCaminhoDoArquivo = new JLabel("Caminho do Arquivo:");
				lblCaminhoDoArquivo.setFont(new Font("Tahoma", Font.PLAIN, 16));
				painelNotaFiscal.add(lblCaminhoDoArquivo, "cell 0 9,alignx trailing");
		
				entCaminhoArquivo = new JTextField();
				entCaminhoArquivo.setFont(new Font("Tahoma", Font.BOLD, 16));
				entCaminhoArquivo.setColumns(10);
				painelNotaFiscal.add(entCaminhoArquivo, "cell 1 9,growx");
		
				JButton btnSelecionarRemetente_1 = new JButton("Selecionar");
				btnSelecionarRemetente_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {


					}
				});
				btnSelecionarRemetente_1.setForeground(Color.WHITE);
				btnSelecionarRemetente_1.setFont(new Font("SansSerif", Font.BOLD, 16));
				btnSelecionarRemetente_1.setBackground(new Color(0, 0, 153));
				painelNotaFiscal.add(btnSelecionarRemetente_1, "cell 2 9");

		

		
		this.setLocationRelativeTo(janela_pai);
		this.setResizable(true);
		
		
	}

	 
}
