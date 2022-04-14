package main.java.gui;

import java.awt.Window;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.border.MatteBorder;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroFilaMovimento;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroMensagem;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroRomaneio;
import main.java.cadastros.CadastroSafra;
import main.java.cadastros.Contato;
import main.java.classesExtras.CBProdutoPersonalizado;
import main.java.classesExtras.CBProdutoRenderPersonalizado;
import main.java.classesExtras.ComboBoxPersonalizado;
import main.java.classesExtras.ComboBoxRenderPersonalizado;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoFilaMovimento;
import main.java.conexaoBanco.GerenciarBancoMensagem;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoRomaneios;
import main.java.conexaoBanco.GerenciarBancoSafras;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.manipular.ManipularTxt;
import main.java.manipular.Whatsapp;
import main.java.outros.DadosGlobais;
import main.java.outros.GetData;
import main.java.tratamento_proprio.Log;

import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import javax.swing.JTextArea;
import javax.swing.JCheckBox;
import javax.swing.ScrollPaneConstants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

public class TelaCadastrarRomaneio extends JFrame {

	private JPanel painelPrincipal;
	private JTextField entUmidade;
	private JTextField entImpureza;
	private CBProdutoPersonalizado modelProduto = new CBProdutoPersonalizado();
	private CBProdutoRenderPersonalizado cBProdutoPersonalizado;
	private TelaCadastrarRomaneio isto;
	private CadastroCliente produtor;
	private CadastroProduto produto;
	CadastroCliente.Veiculo veiculo;
	private CadastroCliente motorista;
	private CadastroCliente transportadora;
	private int tipo_movimentacao_global = -1;

	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;

	private JPanel painelPai;
	private JTextField entPesoBruto;
	private JTextField entPesoTara;

	private int flagEdicaoGlobal = -2;
	private JTextField entNumero;
	private JComboBox cBOperacao;
	private JTextField entData;
	private JTextField entAvariados;
	private JTextField entPesoDescontoUmidade;
	private JTextField entPesoDescontoImpureza;
	private JTextField entPesoDescontoAvariados;
	private JTextField entPesoRecepcao;
	private JTextField entDataEntrada;
	private JTextField entDataSaida;
	private JTextField entHoraEntrada;
	private JTextField entHoraSaida;
	private JTextField entDocFiscalEntrada;
	private JTextField entAmostra;
	private JTextField entCfop;
	private JTextField entCaminhoArquivo;
	private JLabel lblArmazem;
	private CadastroCliente armazem;
	private CadastroCliente remetente;
	private CadastroCliente destinatario;
	private JLabel lblRemetente;
	private JLabel lblDestinatario;

	private JTextArea entDescricaoCfop;
	private JTextField entPesoDescontoTotal;
	private JTextField entPesoLiquidoSemDesconto;
	private JTextField entPesoLiquidoFinal;
	private static ArrayList<CadastroSafra> safras = new ArrayList<>();

	private CadastroSafra safra;
	private ComboBoxRenderPersonalizado cBSafraPersonalizado;
	private ComboBoxPersonalizado modelSafra = new ComboBoxPersonalizado();
	private JTextField entPlaca;
	private JTextField entMotorista;
	private JTextField entCpfMotorista;

	public TelaCadastrarRomaneio(int flagEdicao, CadastroFilaMovimento unidade, Window window) {

		painelPrincipal = new JPanel();
		painelPrincipal.setBackground(Color.WHITE);

		painelPai = new JPanel();
		painelPai.setBackground(Color.WHITE);
		flagEdicaoGlobal = flagEdicao;
		isto = this;
		this.setContentPane(painelPai);
		painelPai.setLayout(new MigLayout("", "[grow]", "[grow]"));

		if (flagEdicao == 0) {
			setTitle("Cadastrar Novo Romaneio");
		} else {
			setTitle("Editar Romaneio");
		}

		JScrollPane scrollMaster = new JScrollPane(painelPrincipal);

		painelPai.add(scrollMaster, "cell 0 0,grow");

		getDadosGlobais();

		tipo_movimentacao_global = 0;

		setBounds(0, 0, 1300, 658);
		painelPrincipal.setLayout(new MigLayout("", "[][::200px,grow][][::1px][grow][::200px,grow][grow][grow][grow][grow]", "[][][][][][][][][][][][][][][][][][][::1px][::1px]"));

		JLabel lblNewLabel_3_1_1 = new JLabel("Armazém:");
		lblNewLabel_3_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelPrincipal.add(lblNewLabel_3_1_1, "cell 0 0,alignx right");

		lblArmazem = new JLabel(" ");
		lblArmazem.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblArmazem.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		painelPrincipal.add(lblArmazem, "cell 1 0 4 1,growx");

		JButton btnSelecionarArmazem = new JButton("Selecionar");
		btnSelecionarArmazem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaArmazem tela = new TelaArmazem(2, isto);
				tela.setVisible(true);

			}
		});
		btnSelecionarArmazem.setForeground(Color.WHITE);
		btnSelecionarArmazem.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnSelecionarArmazem.setBackground(Color.BLUE);
		painelPrincipal.add(btnSelecionarArmazem, "cell 5 0");

		JLabel lblNewLabel_3_1 = new JLabel("Número:");
		lblNewLabel_3_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelPrincipal.add(lblNewLabel_3_1, "cell 0 1,alignx trailing");

		entNumero = new JTextField();
		entNumero.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0987654321\b";// lista de caracters que não devem ser aceitos

				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();// aciona esse propriedade para eliminar a ação do evento

				}
			}
		});
		painelPrincipal.add(entNumero, "cell 1 1 4 1,growx");
		entNumero.setColumns(10);

		JLabel lblNewLabel_3_1_2 = new JLabel("Doc Fiscal Entrada:");
		lblNewLabel_3_1_2.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelPrincipal.add(lblNewLabel_3_1_2, "cell 5 1,alignx trailing");

		entDocFiscalEntrada = new JTextField();
		entDocFiscalEntrada.setColumns(10);
		painelPrincipal.add(entDocFiscalEntrada, "cell 6 1,growx");

		JLabel lblNewLabel_3_1_2_1 = new JLabel("Amostra:");
		lblNewLabel_3_1_2_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelPrincipal.add(lblNewLabel_3_1_2_1, "cell 7 1,alignx trailing");

		entAmostra = new JTextField();
		entAmostra.setColumns(10);
		painelPrincipal.add(entAmostra, "cell 8 1,growx");

		JLabel lblNewLabel_3 = new JLabel("Operação:");
		lblNewLabel_3.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelPrincipal.add(lblNewLabel_3, "cell 0 2,alignx trailing");

		cBOperacao = new JComboBox();
		painelPrincipal.add(cBOperacao, "cell 1 2 4 1,growx");
		cBOperacao.addItem("ENTRADA NORMAL");
		cBOperacao.addItem("ENTRADA TRANSFERENCIA");
		cBOperacao.addItem("SAÍDA NORMAL");
		cBOperacao.addItem("SAÍDA QUEBRA TECNICA");
		cBOperacao.addItem("SAÍDA TRANSFERENCIA");

		JLabel lblNewLabel_3_3 = new JLabel("CFOP:");
		lblNewLabel_3_3.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelPrincipal.add(lblNewLabel_3_3, "cell 5 2,alignx trailing");

		entCfop = new JTextField();
		entCfop.setColumns(10);
		painelPrincipal.add(entCfop, "cell 6 2,growx");

		entDescricaoCfop = new JTextArea();
		entDescricaoCfop.setRows(3);
		entDescricaoCfop.setWrapStyleWord(true);
		entDescricaoCfop.setLineWrap(true);

		JScrollPane scrollPane = new JScrollPane(entDescricaoCfop);
		scrollPane.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		painelPrincipal.add(scrollPane, "cell 7 2 2 2,grow");

		JLabel lblNewLabel_3_2 = new JLabel("Data:");
		lblNewLabel_3_2.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelPrincipal.add(lblNewLabel_3_2, "cell 0 3,alignx trailing");

		entData = new JTextField();
		entData.setColumns(10);
		entData.setText(new GetData().getData());

		painelPrincipal.add(entData, "cell 1 3 4 1,growx,aligny top");
		cBProdutoPersonalizado = new CBProdutoRenderPersonalizado();

		GerenciarBancoProdutos listaProdutos = new GerenciarBancoProdutos();
		ArrayList<CadastroProduto> produtos = listaProdutos.getProdutos();

		for (CadastroProduto produto : produtos) {
			modelProduto.addProduto(produto);

		}

		JLabel lblNewLabel_3_3_1 = new JLabel("Descrição CFOP:");
		lblNewLabel_3_3_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelPrincipal.add(lblNewLabel_3_3_1, "cell 6 3,alignx trailing");

		JLabel lblNewLabel_3_2_1 = new JLabel("Data Entrada:");
		lblNewLabel_3_2_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelPrincipal.add(lblNewLabel_3_2_1, "cell 0 4,alignx trailing");

		entDataEntrada = new JTextField();
		entDataEntrada.setColumns(10);
		entDataEntrada.setText(new GetData().getData());

		painelPrincipal.add(entDataEntrada, "cell 1 4,growx");

		JLabel lblNewLabel_3_2_1_1_1 = new JLabel("Hora Entrada:");
		lblNewLabel_3_2_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelPrincipal.add(lblNewLabel_3_2_1_1_1, "cell 2 4,alignx right");

		entHoraEntrada = new JTextField();
		entHoraEntrada.setText("12:00");
		entHoraEntrada.setColumns(10);
		painelPrincipal.add(entHoraEntrada, "cell 4 4,growx");

		JLabel lblNewLabel_3_3_2 = new JLabel("Remetente:");
		painelPrincipal.add(lblNewLabel_3_3_2, "cell 5 4,alignx right");
		lblNewLabel_3_3_2.setFont(new Font("SansSerif", Font.PLAIN, 16));

		lblRemetente = new JLabel(" ");
		painelPrincipal.add(lblRemetente, "cell 6 4 3 1,growx");
		lblRemetente.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblRemetente.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));

		JButton btnSelecionarRemetente = new JButton("Selecionar");
		btnSelecionarRemetente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaCliente tela = new TelaCliente(0, 115, isto);
				tela.setVisible(true);

			}
		});
		btnSelecionarRemetente.setForeground(Color.WHITE);
		btnSelecionarRemetente.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnSelecionarRemetente.setBackground(Color.BLUE);
		painelPrincipal.add(btnSelecionarRemetente, "cell 9 4");

		JLabel lblNewLabel_3_2_1_1 = new JLabel("Data Saída:");
		lblNewLabel_3_2_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelPrincipal.add(lblNewLabel_3_2_1_1, "cell 0 5,alignx trailing");

		entDataSaida = new JTextField();
		entDataSaida.setColumns(10);
		entDataSaida.setText(new GetData().getData());

		painelPrincipal.add(entDataSaida, "cell 1 5,growx");

		JLabel lblNewLabel_3_2_1_1_1_1 = new JLabel("Hora Saída:");
		lblNewLabel_3_2_1_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelPrincipal.add(lblNewLabel_3_2_1_1_1_1, "cell 2 5,alignx right");

		entHoraSaida = new JTextField();
		entHoraSaida.setText("14:00");
		entHoraSaida.setColumns(10);
		painelPrincipal.add(entHoraSaida, "cell 4 5,growx");

		JLabel lblNewLabel_3_3_2_1 = new JLabel("Destinatario:");
		painelPrincipal.add(lblNewLabel_3_3_2_1, "cell 5 5,alignx right");
		lblNewLabel_3_3_2_1.setFont(new Font("SansSerif", Font.PLAIN, 16));

		lblDestinatario = new JLabel(" ");
		painelPrincipal.add(lblDestinatario, "cell 6 5 3 1,growx");
		lblDestinatario.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblDestinatario.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));

		JButton btnSelecionarDestinatario = new JButton("Selecionar");
		btnSelecionarDestinatario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaCliente tela = new TelaCliente(0, 120, isto);
				tela.setVisible(true);
			}
		});
		btnSelecionarDestinatario.setForeground(Color.WHITE);
		btnSelecionarDestinatario.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnSelecionarDestinatario.setBackground(Color.BLUE);
		painelPrincipal.add(btnSelecionarDestinatario, "cell 9 5");

		JLabel lblNewLabel = new JLabel("Motorista:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblNewLabel, "cell 0 6,alignx trailing");
		
		JComboBox cBSafra = new JComboBox();
		cBSafraPersonalizado = new ComboBoxRenderPersonalizado();
		
		entMotorista = new JTextField();
		entMotorista.setColumns(10);
		painelPrincipal.add(entMotorista, "cell 1 6 2 1,growx");
		
		JLabel lblCpfMotorista = new JLabel("CPF Motorista:");
		lblCpfMotorista.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblCpfMotorista, "cell 4 6,alignx trailing");
		
		entCpfMotorista = new JTextField();
		entCpfMotorista.setColumns(10);
		painelPrincipal.add(entCpfMotorista, "cell 5 6,growx");
		
		entPlaca = new JTextField();
		entPlaca.setColumns(10);
		painelPrincipal.add(entPlaca, "cell 1 7,growx");
		cBSafra = new JComboBox();
		cBSafra.setFont(new Font("SansSerif", Font.BOLD, 16));
		cBSafra.setModel(modelSafra);
		cBSafra.setRenderer(cBSafraPersonalizado);
		cBSafra.setBounds(586, 224, 593, 33);
		painelPrincipal.add(cBSafra, "cell 5 7 5 1,growx");

		JLabel lblVeculo = new JLabel("Placa:");
		lblVeculo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblVeculo, "cell 0 7,alignx trailing");
		
		JLabel lblSafra = new JLabel("Safra:");
		lblSafra.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblSafra, "cell 4 7,alignx trailing");

		JLabel lblNewLabel_1_1_1 = new JLabel("Classificação Prévia");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		painelPrincipal.add(lblNewLabel_1_1_1, "cell 0 8 3 2,alignx center");

		JLabel lblNewLabel_2 = new JLabel("Umidade:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblNewLabel_2, "cell 0 10,alignx trailing");

		entUmidade = new JTextField();
		entUmidade.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {

				String caracteres = ".0987654321\b";// lista de caracters que não devem ser aceitos
				String s_valor = "";

				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();// aciona esse propriedade para eliminar a ação do evento

				}
			}
		});
		entUmidade.setText("0.0");
		entUmidade.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		entUmidade.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelPrincipal.add(entUmidade, "cell 1 10,growx");
		entUmidade.setColumns(10);

		JLabel lblNewLabel_2_1 = new JLabel("Impureza:");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblNewLabel_2_1, "cell 0 11,alignx trailing");

		entImpureza = new JTextField();
		entImpureza.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = ".0987654321\b";// lista de caracters que não devem ser aceitos
				String s_valor = "";

				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();// aciona esse propriedade para eliminar a ação do evento

				}
			}
		});
		entImpureza.setText("0.0");
		entImpureza.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		entImpureza.setFont(new Font("SansSerif", Font.BOLD, 16));
		entImpureza.setColumns(10);
		painelPrincipal.add(entImpureza, "cell 1 11,growx");

		JLabel lblNewLabel_2_2 = new JLabel("Peso Bruto Total:");
		lblNewLabel_2_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblNewLabel_2_2, "cell 4 11,alignx trailing");

		entPesoBruto = new JTextField();
		entPesoBruto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = ".0987654321\b";// lista de caracters que não devem ser aceitos
				String s_valor = "";

				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();// aciona esse propriedade para eliminar a ação do evento

				}
			}
		});
		entPesoBruto.setText("0.0");
		entPesoBruto.setFont(new Font("SansSerif", Font.BOLD, 16));
		entPesoBruto.setColumns(10);
		entPesoBruto.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		painelPrincipal.add(entPesoBruto, "cell 5 11,growx");

		JLabel lblNewLabel_2_2_1_2 = new JLabel("Peso Desc Umidade:");
		lblNewLabel_2_2_1_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblNewLabel_2_2_1_2, "cell 6 11,alignx trailing");

		entPesoDescontoUmidade = new JTextField();
		entPesoDescontoUmidade.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = ".0987654321\b";// lista de caracters que não devem ser aceitos
				String s_valor = "";

				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();// aciona esse propriedade para eliminar a ação do evento

				}
			}
		});
		entPesoDescontoUmidade.setText("0.0");
		entPesoDescontoUmidade.setFont(new Font("SansSerif", Font.BOLD, 16));
		entPesoDescontoUmidade.setColumns(10);
		entPesoDescontoUmidade.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		painelPrincipal.add(entPesoDescontoUmidade, "cell 7 11,growx");

		JLabel lblNewLabel_1_1_1_1_1_1_1_1 = new JLabel("Peso Recepção:");
		lblNewLabel_1_1_1_1_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblNewLabel_1_1_1_1_1_1_1_1, "cell 8 11,alignx trailing");

		entPesoRecepcao = new JTextField();
		entPesoRecepcao.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = ".0987654321\b";// lista de caracters que não devem ser aceitos
				String s_valor = "";

				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();// aciona esse propriedade para eliminar a ação do evento

				}
			}
		});
		entPesoRecepcao.setText("0.0");
		entPesoRecepcao.setFont(new Font("SansSerif", Font.BOLD, 16));
		entPesoRecepcao.setColumns(10);
		entPesoRecepcao.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		painelPrincipal.add(entPesoRecepcao, "cell 9 11,growx");
		
				JLabel lblNewLabel_2_1_1_1 = new JLabel("Avariados:");
				lblNewLabel_2_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
				painelPrincipal.add(lblNewLabel_2_1_1_1, "cell 0 12,alignx trailing");
		
				entAvariados = new JTextField();
				entAvariados.addKeyListener(new KeyAdapter() {
					@Override
					public void keyTyped(KeyEvent e) {
						String caracteres = ".0987654321\b";// lista de caracters que não devem ser aceitos
						String s_valor = "";

						if (!caracteres.contains(e.getKeyChar() + "")) {
							e.consume();// aciona esse propriedade para eliminar a ação do evento

						}
					}
				});
				entAvariados.setText("0.0");
				entAvariados.setFont(new Font("SansSerif", Font.BOLD, 16));
				entAvariados.setColumns(10);
				entAvariados.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
				painelPrincipal.add(entAvariados, "cell 1 12,growx");

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setBackground(Color.BLACK);
		painelPrincipal.add(lblNewLabel_1, "cell 3 12 1 5,grow");

		JLabel lblNewLabel_1_1_1_1 = new JLabel("Pesagem");
		lblNewLabel_1_1_1_1.setBackground(new Color(0, 102, 153));
		lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		painelPrincipal.add(lblNewLabel_1_1_1_1, "cell 4 9 6 1,alignx center");

		JLabel lblNewLabel_2_2_1 = new JLabel("Peso Tara:");
		lblNewLabel_2_2_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblNewLabel_2_2_1, "cell 4 12,alignx trailing");

		entPesoTara = new JTextField();
		entPesoTara.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = ".0987654321\b";// lista de caracters que não devem ser aceitos
				String s_valor = "";

				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();// aciona esse propriedade para eliminar a ação do evento

				}
			}
		});
		entPesoTara.setText("0.0");
		entPesoTara.setFont(new Font("SansSerif", Font.BOLD, 16));
		entPesoTara.setColumns(10);
		entPesoTara.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		painelPrincipal.add(entPesoTara, "cell 5 12,growx");

		JLabel lblNewLabel_2_2_1_2_1 = new JLabel("Peso Desc Impureza:");
		lblNewLabel_2_2_1_2_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblNewLabel_2_2_1_2_1, "cell 6 12,alignx right");

		entPesoDescontoImpureza = new JTextField();
		entPesoDescontoImpureza.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = ".0987654321\b";// lista de caracters que não devem ser aceitos
				String s_valor = "";

				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();// aciona esse propriedade para eliminar a ação do evento

				}
			}
		});
		entPesoDescontoImpureza.setText("0.0");
		entPesoDescontoImpureza.setFont(new Font("SansSerif", Font.BOLD, 16));
		entPesoDescontoImpureza.setColumns(10);
		entPesoDescontoImpureza.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		painelPrincipal.add(entPesoDescontoImpureza, "cell 7 12,growx");

		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("Balança");
		lblNewLabel_1_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		painelPrincipal.add(lblNewLabel_1_1_1_1_1, "cell 4 10 2 1,alignx center");

		JLabel lblNewLabel_1_1_1_1_1_1 = new JLabel("Descontos");
		lblNewLabel_1_1_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		painelPrincipal.add(lblNewLabel_1_1_1_1_1_1, "cell 6 10 2 1,alignx center");

		JLabel lblNewLabel_1_1_1_1_1_1_1 = new JLabel("Recepção");
		lblNewLabel_1_1_1_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		painelPrincipal.add(lblNewLabel_1_1_1_1_1_1_1, "cell 8 10 2 1,alignx center");

		JLabel lblNewLabel_2_2_1_2_1_1_1 = new JLabel("Peso Desc Avariado:");
		lblNewLabel_2_2_1_2_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblNewLabel_2_2_1_2_1_1_1, "cell 6 13 1 2,alignx right");

		entPesoDescontoAvariados = new JTextField();
		entPesoDescontoAvariados.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = ".0987654321\b";// lista de caracters que não devem ser aceitos
				String s_valor = "";

				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();// aciona esse propriedade para eliminar a ação do evento

				}
			}
		});
		entPesoDescontoAvariados.setText("0.0");
		entPesoDescontoAvariados.setFont(new Font("SansSerif", Font.BOLD, 16));
		entPesoDescontoAvariados.setColumns(10);
		entPesoDescontoAvariados.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		painelPrincipal.add(entPesoDescontoAvariados, "cell 7 13 1 2,growx");

		JLabel lblNewLabel_2_2_1_2_1_1_1_1 = new JLabel("Peso Desconto Total:");
		lblNewLabel_2_2_1_2_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		painelPrincipal.add(lblNewLabel_2_2_1_2_1_1_1_1, "cell 6 15,alignx trailing");
		
		entPesoDescontoTotal = new JTextField();
		entPesoDescontoTotal.setText("0.0");
		entPesoDescontoTotal.setFont(new Font("SansSerif", Font.BOLD, 16));
		entPesoDescontoTotal.setColumns(10);
		entPesoDescontoTotal.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		painelPrincipal.add(entPesoDescontoTotal, "cell 7 15,growx");

		JLabel lblNewLabel_2_2_1_1 = new JLabel("Peso Liquido S/Desconto:");
		lblNewLabel_2_2_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblNewLabel_2_2_1_1, "cell 4 16,alignx trailing");
		
		entPesoLiquidoSemDesconto = new JTextField();
		entPesoLiquidoSemDesconto.setText("0.0");
		entPesoLiquidoSemDesconto.setFont(new Font("SansSerif", Font.BOLD, 16));
		entPesoLiquidoSemDesconto.setColumns(10);
		entPesoLiquidoSemDesconto.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		painelPrincipal.add(entPesoLiquidoSemDesconto, "cell 5 16,growx");

		JLabel lblNewLabel_2_2_1_1_1 = new JLabel("Peso Liquido Final:");
		lblNewLabel_2_2_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblNewLabel_2_2_1_1_1, "cell 7 16,alignx trailing");
		
		entPesoLiquidoFinal = new JTextField();
		entPesoLiquidoFinal.setText("0.0");
		entPesoLiquidoFinal.setFont(new Font("SansSerif", Font.BOLD, 16));
		entPesoLiquidoFinal.setColumns(10);
		entPesoLiquidoFinal.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		painelPrincipal.add(entPesoLiquidoFinal, "cell 8 16,growx");

		JLabel lblNewLabel_2_1_1_1_1 = new JLabel("Arquivo:");
		lblNewLabel_2_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblNewLabel_2_1_1_1_1, "cell 0 17,alignx trailing");

		entCaminhoArquivo = new JTextField();
		entCaminhoArquivo.setFont(new Font("SansSerif", Font.BOLD, 16));
		entCaminhoArquivo.setColumns(10);
		entCaminhoArquivo.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		painelPrincipal.add(entCaminhoArquivo, "cell 1 17 5 1,growx");

		JButton btnSelecionarCaminhoArquivo = new JButton("Selecionar");
		btnSelecionarCaminhoArquivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JOptionPane.showMessageDialog(isto, "Na próxima tela, importe o arquivo de romaneio de terceiros");

				new JFXPanel();
				Platform.runLater(() -> {

					// pegar ultima pasta
					ManipularTxt manipular_ultima_pasta = new ManipularTxt();
					String ultima_pasta = manipular_ultima_pasta
							.lerArquivo(new File("C:\\ProgramData\\E-Contract\\configs\\ultima_pasta.txt"));
					FileChooser d = new FileChooser();
					FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF (*.pdf)", "*.pdf");
					d.getExtensionFilters().add(extFilter);

					d.setInitialDirectory(new File(ultima_pasta));
					File file = d.showOpenDialog(new Stage());
					String caminho_arquivo = "";
					if (file != null) {
						caminho_arquivo = file.getAbsolutePath();

						manipular_ultima_pasta.rescreverArquivo(
								new File("C:\\ProgramData\\E-Contract\\configs\\ultima_pasta.txt"), file.getParent());
			
						entCaminhoArquivo.setText(caminho_arquivo);

					}

				});

				
			}
		});
		btnSelecionarCaminhoArquivo.setForeground(Color.WHITE);
		btnSelecionarCaminhoArquivo.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnSelecionarCaminhoArquivo.setBackground(Color.BLUE);
		painelPrincipal.add(btnSelecionarCaminhoArquivo, "cell 6 17");

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				boolean copiado = false;
				String caminho_final = "";
				CadastroRomaneio rom = getDadosSalvar();
				if(rom != null) {
					GerenciarBancoRomaneios gerenciar = new GerenciarBancoRomaneios();
					//verificar por romaneio ja cadastrado
					boolean ja_cadastrado = gerenciar.verificarRegistroRomaneio(rom.getNumero_romaneio());
					if(ja_cadastrado) {
						JOptionPane.showMessageDialog(isto, "Código de Romaneio Já Inserido");

					}else {
						
						//primeiro efetuar a copia
						//copiar arquivo da pasta original para a pasta de terceiros


						if (remetente != null && destinatario == null ) {
							// mover para a pasta do remetente
							ManipularTxt manipular_txt = new ManipularTxt();
							String nome_pasta;
							if (remetente.getTipo_pessoa() == 0) {
								nome_pasta = remetente.getNome_empresarial().toUpperCase();
							} else {

								nome_pasta = remetente.getNome_fantaia().toUpperCase();
							}
							String unidade_base_dados = configs_globais.getServidorUnidade();
							String sub_pasta = "E-Contract\\arquivos\\clientes";
							ManipularTxt manipular_arq = new ManipularTxt();
							nome_pasta = nome_pasta.trim();
							String caminho_completo_nf = unidade_base_dados + "\\" + sub_pasta + "\\"
									+ nome_pasta.toUpperCase() + "\\" + "ROMANEIOS" + "\\romaneio-"
									+ rom.getNumero_romaneio() + ".pdf";
							
							// primeiro veririca se nao existe um arquivo com esse nome
							File file = new File(caminho_completo_nf);
							if (!file.exists()) {
								boolean mover = false;
								try {
									mover = manipular_arq.copiarNFe(rom.getCaminho_arquivo(),
											caminho_completo_nf);
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								if (mover) {
									 copiado = true;
									 caminho_final = caminho_completo_nf;
								} 
							} 
						} else if (remetente == null && destinatario != null) {
							// mover para a pasta do destinatario
							ManipularTxt manipular_txt = new ManipularTxt();
							String nome_pasta;
							if (destinatario.getTipo_pessoa() == 0) {
								nome_pasta = destinatario.getNome_empresarial().toUpperCase();
							} else {

								nome_pasta = destinatario.getNome_fantaia().toUpperCase();
							}
							String unidade_base_dados = configs_globais.getServidorUnidade();
							String sub_pasta = "E-Contract\\arquivos\\clientes";
							ManipularTxt manipular_arq = new ManipularTxt();
							nome_pasta = nome_pasta.trim();
							String caminho_completo_nf = unidade_base_dados + "\\" + sub_pasta + "\\"
									+ nome_pasta.toUpperCase() + "\\" + "ROMANEIOS" + "\\romaneio-"
									+ rom.getNumero_romaneio() + ".pdf";
							// //JOptionPane.showMessageDialog(null, "Movendo de :\n" +
							// roms.getCaminho_arquivo()+ "\nPara:\n" + caminho_completo_nf);
							// primeiro veririca se nao existe um arquivo com esse nome
							File file = new File(caminho_completo_nf);
							if (!file.exists()) {
								boolean mover = manipular_arq.moverArquivo(rom.getCaminho_arquivo(),
										caminho_completo_nf);
								if (mover) {
									copiado = true;
									 caminho_final = caminho_completo_nf;
								} 
							}
						} else if (remetente != null && destinatario != null) {
							if (remetente.getIe().trim().equals(destinatario.getIe().trim())) {
								// mover para o remetente
								// copiar para o remetente
								ManipularTxt manipular_txt = new ManipularTxt();
								String nome_pasta;
								if (remetente.getTipo_pessoa() == 0) {

									nome_pasta = remetente.getNome_empresarial();
								} else {

									nome_pasta = remetente.getNome_fantaia();
								}
								String unidade_base_dados = configs_globais.getServidorUnidade();
								String sub_pasta = "E-Contract\\arquivos\\clientes";
								ManipularTxt manipular_arq = new ManipularTxt();
								nome_pasta = nome_pasta.trim();
								String caminho_completo_nf = unidade_base_dados + "\\" + sub_pasta + "\\"
										+ nome_pasta + "\\" + "ROMANEIOS" + "\\romaneio-"
										+ rom.getNumero_romaneio() + ".pdf";
								// //JOptionPane.showMessageDialog(null, "Copiando de :\n" +
								// roms.getCaminho_arquivo()+ "\nPara:\n" + caminho_completo_nf);
								// primeiro veririca se nao existe um arquivo com esse nome
								File file = new File(caminho_completo_nf);
								if (!file.exists()) {
									boolean copiar = manipular_arq.moverArquivo(rom.getCaminho_arquivo(),
											caminho_completo_nf);
									if (copiar) {
										copiado = true;
										 caminho_final = caminho_completo_nf;
									} 
								}
							} else {
								// Romaneio com destinatario e remetente diferente
								// copiar para o destinatario
								ManipularTxt manipular_txt = new ManipularTxt();
								String nome_pasta;

								if (destinatario.getTipo_pessoa() == 0) {
									nome_pasta = destinatario.getNome_empresarial();
								} else {
									nome_pasta = destinatario.getNome_fantaia();
								}

								String unidade_base_dados = configs_globais.getServidorUnidade();
								String sub_pasta = "E-Contract\\arquivos\\clientes";
								ManipularTxt manipular_arq = new ManipularTxt();
								nome_pasta = nome_pasta.trim();
								String caminho_completo_nf = unidade_base_dados + "\\" + sub_pasta + "\\"
										+ nome_pasta + "\\" + "ROMANEIOS" + "\\romaneio-"
										+ rom.getNumero_romaneio() + ".pdf";
								// //JOptionPane.showMessageDialog(null, "Copiando de :\n" +
								// roms.getCaminho_arquivo()+ "\nPara:\n" + caminho_completo_nf);
								// primeiro veririca se nao existe um arquivo com esse nome
								File file = new File(caminho_completo_nf);
								if (!file.exists()) {
									boolean copiar = false;
									try {
										copiar = manipular_arq.copiarNFe(rom.getCaminho_arquivo(),
												caminho_completo_nf);
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									if (copiar) {
										
										if (remetente.getTipo_pessoa() == 0) {
											nome_pasta = remetente.getNome_empresarial().toUpperCase();
										} else {

											nome_pasta = remetente.getNome_fantaia().toUpperCase();
										}
										unidade_base_dados = configs_globais.getServidorUnidade();
										sub_pasta = "E-Contract\\arquivos\\clientes";
										nome_pasta = nome_pasta.trim();
										caminho_completo_nf = unidade_base_dados + "\\" + sub_pasta + "\\"
												+ nome_pasta.toUpperCase() + "\\" + "ROMANEIOS" + "\\romaneio-"
												+ rom.getNumero_romaneio() + ".pdf";
										// //JOptionPane.showMessageDialog(null, "Movendo de :\n" +
										// roms.getCaminho_arquivo()+ "\nPara:\n" + caminho_completo_nf);
										boolean mover = manipular_arq.moverArquivo(rom.getCaminho_arquivo(),
												caminho_completo_nf);
										if (mover) {
											copiado =true;
											 caminho_final = caminho_completo_nf;

										} 
									} 
								}
							}
						} 
						
						
							if(copiado) {
								rom.setCaminho_arquivo(caminho_final);
								int inserir = gerenciar.inserir_romaneio(rom);
								if(inserir > 0) {
									JOptionPane.showMessageDialog(isto, "Romaneio Inserido");
									
									isto.dispose();
								}else {
									JOptionPane.showMessageDialog(isto, "Erro ao Inserir Romaneio");
									isto.dispose();
								}
							}//fim if copiado
							
					
					}//fim else nao cadastrad ainda
					}// fim if rom != null
			}	
			
		});
		btnSalvar.setBackground(new Color(0, 51, 0));
		btnSalvar.setForeground(Color.WHITE);
		btnSalvar.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelPrincipal.add(btnSalvar, "cell 8 17,growx");

		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.setForeground(Color.WHITE);
		btnAtualizar.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnAtualizar.setBackground(new Color(0, 0, 51));
		painelPrincipal.add(btnAtualizar, "cell 9 17,growx");

		
		pesquisarSafras();

		for (CadastroSafra safra : safras) {

			// cBSafra.addItem(safra.getProduto().getNome_produto() + " " +
			// safra.getAno_plantio() + "/" + safra.getAno_colheita());
			// cBSafra.addItem(safra);
			modelSafra.addSafra(safra);

		}
		
		
		if (flagEdicao == 0) {
			btnAtualizar.setEnabled(false);
			btnAtualizar.setVisible(false);
		} else {
			btnSalvar.setEnabled(false);
			btnSalvar.setVisible(false);
		}

		this.setLocationRelativeTo(window);

	}

	public static void pesquisarSafras() {
		GerenciarBancoSafras listaSafras = new GerenciarBancoSafras();
		safras = listaSafras.getSafras();
	}
	
	
	public CadastroRomaneio getDadosSalvar() {

		CadastroRomaneio novo_rom = new CadastroRomaneio();

		if (armazem != null) {
			novo_rom.setId_armazem(armazem.getId());

		} else {
			JOptionPane.showMessageDialog(isto, "Informe o Armazém");
			return null;
		}

		String codigo = entNumero.getText();
		if (codigo != null && codigo.length() > 0) {

			try {
				int numero = Integer.parseInt(codigo);

				novo_rom.setNumero_romaneio(numero);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(isto, "Número Inválido");
				return null;
			}
		} else {
			JOptionPane.showMessageDialog(isto, "Número Inválido");
			return null;
		}

		novo_rom.setAmostra(entAmostra.getText());
		novo_rom.setDoc_entrada(entDocFiscalEntrada.getText());

		novo_rom.setOperacao(cBOperacao.getSelectedItem().toString());

		if (remetente != null) {
			novo_rom.setRemetente(remetente);
		} else {
			JOptionPane.showMessageDialog(isto, "Remetente não pode ser em branco");
			return null;
		}

		if (destinatario != null) {
			novo_rom.setDestinatario(destinatario);
		} else {
			CadastroCliente dest = new CadastroCliente();
			dest.setId(0);
			novo_rom.setDestinatario(dest);
		}

		novo_rom.setCfop(entCfop.getText());
		novo_rom.setDescricao_cfop(entDescricaoCfop.getText());

		
			novo_rom.setNome_motorista(entMotorista.getText());
			novo_rom.setCpf_motorista(entCpfMotorista.getText());
			novo_rom.setPlaca(entPlaca.getText());
	

		double umidade, impureza, ardidos, avariados;
		// classificacao
		try {

			umidade = Double.parseDouble(entUmidade.getText());
			novo_rom.setUmidade(umidade);

			try {

				impureza = Double.parseDouble(entImpureza.getText());
				novo_rom.setImpureza(impureza);

					try {

						avariados = Double.parseDouble(entAvariados.getText());
						novo_rom.setAvariados(avariados);

					} catch (Exception e) {
						JOptionPane.showMessageDialog(isto, "Avariados inválido");
						return null;
					}

				
			} catch (Exception e) {
				JOptionPane.showMessageDialog(isto, "Impureza inválida");
				return null;
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(isto, "Umidade inválida");
			return null;
		}

		// data
		String data_romaneio = entData.getText();
		try {
			if (isDateValid(data_romaneio)) {
				SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
				Date dataFormatada = formato.parse(data_romaneio);

				novo_rom.setData(dataFormatada);
			} else {
				JOptionPane.showMessageDialog(isto, "Data Inválida");
				return null;
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(isto, "Data do Romaneio Inválida!");
			return null;
		}

		// data entrada
		String data_entrada_romaneio = entDataEntrada.getText();
		try {
			if (isDateValid(data_entrada_romaneio)) {
				novo_rom.setData_entrada(data_entrada_romaneio);
			} else {
				JOptionPane.showMessageDialog(isto, "Data de Entrada Inválida");
				return null;
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(isto, "Data de Entrada Inválida!");
			return null;
		}

		// hora entrada
		String hora_entrada = entHoraEntrada.getText() + ":00";
		try {
			LocalTime hora_lt = LocalTime.parse(hora_entrada, DateTimeFormatter.ofPattern("HH:mm:ss"));
			novo_rom.setHora_entrada(hora_entrada);
		} catch (Exception y) {
			JOptionPane.showMessageDialog(isto, "Hora de Entrada Inválida!");
			return null;
		}

		// data saida
		String data_saida_romaneio = entDataSaida.getText();
		try {
			if (isDateValid(data_saida_romaneio)) {
				novo_rom.setData_saida(data_saida_romaneio);
			} else {
				JOptionPane.showMessageDialog(isto, "Data de Saida Inválida");
				return null;
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(isto, "Data de Saida Inválida!");
			return null;
		}

		// hora saida
		String hora_saida = entHoraSaida.getText() + ":00";
		try {
			LocalTime hora_lt = LocalTime.parse(hora_saida, DateTimeFormatter.ofPattern("HH:mm:ss"));
			novo_rom.setHora_saida(hora_saida);
		} catch (Exception y) {
			JOptionPane.showMessageDialog(isto, "Hora de Saída Inválida!");
			return null;
		}

		// pesagem
		double peso_bruto, peso_tara, peso_liquido_final, peso_liquido_sem_desconto, peso_desconto_total, peso_desconto_impureza, peso_desconto_umidade, peso_desconto_avariado,peso_recepcao;
		
		try {
			
			peso_bruto = Double.parseDouble(entPesoBruto.getText());
			novo_rom.setPeso_bruto(peso_bruto);
			

			try {
				
				peso_tara = Double.parseDouble(entPesoTara.getText());
				novo_rom.setTara(peso_tara);
				


				try {
					
					peso_desconto_impureza = Double.parseDouble(entPesoDescontoImpureza.getText());
					novo_rom.setPeso_desconto_impureza(peso_desconto_impureza);
					

					try {
						
						peso_desconto_umidade = Double.parseDouble(entPesoDescontoUmidade.getText());
						novo_rom.setPeso_desconto_umidade(peso_desconto_umidade);
						
						try {
							
							peso_desconto_avariado = Double.parseDouble(entPesoDescontoAvariados.getText());
							novo_rom.setPeso_desconto_avariados(peso_desconto_avariado);
							

							try {
								
								peso_desconto_total = Double.parseDouble(entPesoDescontoTotal.getText());
								novo_rom.setPeso_desconto_total(peso_desconto_total);
								

								try {
									
									peso_liquido_sem_desconto = Double.parseDouble(entPesoLiquidoSemDesconto.getText());
									novo_rom.setPeso_liquido_sem_descontos(peso_liquido_sem_desconto);
									

									try {
										
										peso_liquido_final = Double.parseDouble(entPesoLiquidoFinal.getText());
										novo_rom.setPeso_liquido(peso_liquido_final);
										

										
										
										
									}catch(Exception e) {
										JOptionPane.showMessageDialog(isto, "Peso Liquido Final Inválido!");
										return null;
									}
									
									
									
								}catch(Exception e) {
									JOptionPane.showMessageDialog(isto, "Peso Liquido S/Desconto Inválido!");
									return null;
								}
								
								
							}catch(Exception e) {
								JOptionPane.showMessageDialog(isto, "Peso do Desconto Total Inválido!");
								return null;
							}
							
							
							
						}catch(Exception e) {
							JOptionPane.showMessageDialog(isto, "Peso do Desconto De Avariados Inválido!");
							return null;
						}
						
						
						
					}catch(Exception e) {
						JOptionPane.showMessageDialog(isto, "Peso do Desconto De Umidade Inválido!");
						return null;
					}
					
					
					
				}catch(Exception e) {
					JOptionPane.showMessageDialog(isto, "Peso do Desconto De Impureza Inválido!");
					return null;
				}
				
				
				
			}catch(Exception e) {
				JOptionPane.showMessageDialog(isto, "Peso da Tara Inválido!");
				return null;
			}
			
		}catch(Exception e) {
			JOptionPane.showMessageDialog(isto, "Peso Bruto Inválido!");
			return null;
		}
		
		//safra
		
		safra = (CadastroSafra) modelSafra.getSelectedItem();
		novo_rom.setSafra(safra);
		
		CadastroProduto produto = safra.getProduto();
		novo_rom.setProduto(produto);


		return novo_rom;

	}

	

	public void setRemetente(CadastroCliente cliente) {
		this.remetente = cliente;

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				if (cliente.getTipo_pessoa() == 0) {
					lblRemetente.setText(cliente.getNome_empresarial().toUpperCase().trim());
				} else {
					lblRemetente.setText(cliente.getRazao_social().toUpperCase());
				}

				lblRemetente.repaint();
				lblRemetente.updateUI();

			}
		});

	}

	public void setDestinatario(CadastroCliente cliente) {
		this.destinatario = cliente;

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				if (cliente.getTipo_pessoa() == 0) {
					lblDestinatario.setText(cliente.getNome_empresarial().toUpperCase().trim());
				} else {
					lblDestinatario.setText(cliente.getRazao_social().toUpperCase());
				}

				lblDestinatario.repaint();
				lblDestinatario.updateUI();

			}
		});

	}

	public void setArmazem(CadastroCliente _armazem) {
		this.armazem = _armazem;
		lblArmazem.setText(_armazem.getNome_fantaia());

	}

	public void getDadosGlobais() {
		// gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		GerenciadorLog = dados.getGerenciadorLog();
		configs_globais = dados.getConfigs_globais();

		// usuario logado
		login = dados.getLogin();

	}

	public static boolean isDateValid(String strDate) {
		String dateFormat = "dd/MM/uuuu";

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat)
				.withResolverStyle(ResolverStyle.STRICT);
		try {
			LocalDate date = LocalDate.parse(strDate, dateTimeFormatter);
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}
	}

}
