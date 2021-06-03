package main.java.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
//import javax.swing.JTextFieldPersonalizado;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import br.com.caelum.stella.ValidationMessage;
import br.com.caelum.stella.validation.CPFValidator;

import main.java.cadastros.CadastroAviso;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroFuncionario;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroContrato.CadastroPagamento;
import main.java.cadastros.CadastroContrato.CadastroPagamentoContratual;
import main.java.cadastros.CadastroContrato.Recebimento;
import main.java.cadastros.CadastroFuncionario;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroModelo;
import main.java.cadastros.CadastroNFe;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroRomaneio;
import main.java.cadastros.CadastroSafra;
import main.java.cadastros.ContaBancaria;
import main.java.cadastros.Contato;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.classesExtras.Endereco;
import main.java.conexaoBanco.GerenciarBancoFuncionarios;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoFuncionarios;
import main.java.conexaoBanco.GerenciarBancoNotasFiscais;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoRomaneios;
import main.java.conexaoBanco.GerenciarBancoSafras;
import main.java.gui.TelaMain;
import main.java.gui.TelaRomaneios;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.manipular.ManipularTxt;
import main.java.outros.BuscarCep;
import main.java.outros.DadosGlobais;
import main.java.outros.GetData;
import main.java.outros.GetHttp;
import main.java.outros.GetSintegra;
import main.java.outros.JPanelBackground;
import main.java.outros.JPanelTransparent;
import main.java.outros.JTextFieldPersonalizado;
import main.java.outros.MyFileVisitor;
import main.java.outros.TratarDados;
import main.java.tratamento_proprio.Log;
import main.java.views_personalizadas.TelaEmEspera;
import main.java.views_personalizadas.TelaNotificacaoSuperiorModoBusca;
import outros.ValidaCNPj;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroNuvem;
import main.java.cadastros.CadastroZapMessenger;
import main.java.outros.DadosGlobais;

import java.io.IOException;
import java.net.URL;

import javax.swing.JTable;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import net.miginfocom.swing.MigLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JTextArea;
import javax.swing.border.MatteBorder;

public class TelaFuncionariosCadastroFuncionario extends JFrame {

	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;

	private final JPanelBackground contentPanel = new JPanelBackground();
	private JTextFieldPersonalizado entNaturalidade, entEstadoCivil, entNomeDoPai, entLogradouro, entCep,
			entNumEndereco, entCidade, entBairro;
	private JTextFieldPersonalizado entTituloEleitor, entSecao, entZona, entCertidaoMilitar,entSerieCertidaoMilitar,entCategoriaCertidaoMilitar,entPis,
	entNis, entCtpsSerie, entCategoriaCnh, entValidadeCnh,entNumRegistroCnh;
	private JTextArea entCursos, entHabilidades;

	// painel pai
	private JTabbedPane painelPrincipal;

	// painel filho1JPanelTransparent
	private JPanelBackground painelDadosIniciais = new JPanelBackground();

	// painel filho1JPanelTransparent
	private JPanelBackground painelDadosTrabalhistas = new JPanelBackground();

	// painel filho1JPanelTransparent
	private JPanelBackground painelInfoExtra = new JPanelBackground();

	// painel pessoa fisica e juridico
	private JPanelTransparent panelPessoaFisica = new JPanelTransparent();

	// outros paineis

	private JPanel painelDadosBancarios = new JPanel();
	private JPanel painelContato = new JPanel();
	private JPanel painelFinalizar = new JPanel();

	private String logradouro, bairro, cidade, estado;
	private int num, cep;
	private JTextFieldPersonalizado entBanco;
	private JTextFieldPersonalizado entCodBanco;
	private JTextFieldPersonalizado entConta;
	private JTextFieldPersonalizado entAgencia;
	private JTextFieldPersonalizado entCpfTitular;
	private JTextFieldPersonalizado entNome;
	private JTextFieldPersonalizado entSobrenome;
	private JTextFieldPersonalizado entNascimento;
	private JTextFieldPersonalizado entCpf;
	private JTextFieldPersonalizado entRg;

	private String uf;
	private JTextFieldPersonalizado entNacionalidade;
	private JTextFieldPersonalizado entNomeDaMae;
	private String tipoIdentificacao;
	private JTextFieldPersonalizado entApelido;
	private JTextFieldPersonalizado entCelularContato;
	private JTextFieldPersonalizado entNomeContato;
	private JTextFieldPersonalizado entFixoContato;
	private JTextFieldPersonalizado entEmailContato;
	private JTable table;

	private JPanel painel_table_cb;
	private JTable table_cb;

	private DefaultTableModel modelo = new DefaultTableModel();
	private DefaultTableModel modelo_cb = new DefaultTableModel();
	private JTextFieldPersonalizado entDescricaoContato;
	private JTextFieldPersonalizado entObservacaoContato;
	private JTextFieldPersonalizado entNomeContaBancaria;

	ArrayList<Integer> contatos_excluir = new ArrayList<>();
	ArrayList<Integer> contas_excluir = new ArrayList<>();

	CadastroFuncionario funcionario_cadastrar = new CadastroFuncionario();
	CadastroFuncionario funcionario_atualizar = new CadastroFuncionario();
	private JDialog telaPai;

	private JPanel painelCentral = new JPanel();
	private JLabel lblCodigoGerado, lblCodigo;
	private TelaFuncionariosCadastroFuncionario isto;
	private JComboBox cBUFPessoa, 	 cBGenero, cBEnsino,cBCtpsUf, cBUFEndereco;

	public TelaFuncionariosCadastroFuncionario(int flag_tipo_tela, CadastroFuncionario funcionario, Window janela_pai) {
		getContentPane().setBackground(new Color(0, 153, 0));
		getContentPane().setFont(new Font("Arial", Font.BOLD, 18));
		getContentPane().setForeground(Color.WHITE);
		setFont(new Font("Arial", Font.BOLD, 18));
		setForeground(new Color(255, 255, 255));

		getDadosGlobais();
		// setAlwaysOnTop(true);

		// setModal(true);
		isto = this;

		setResizable(false);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		if (flag_tipo_tela == 0)
			setTitle("E-Contract - Novo Colaborador");
		else if (flag_tipo_tela == 1)
			setTitle("E-Contract - Edição de Colaborador");

		setBounds(100, 100, 1152, 704);
		getContentPane().setLayout(new MigLayout("", "[grow]", "[grow]"));
		getContentPane().add(painelCentral, "cell 0 0,grow");

		// configuracao de paineis
		// painel pai

		painelPrincipal = new JTabbedPane();
		painelPrincipal.setFont(new Font("Arial", Font.BOLD, 14));
		// painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		// painelPrincipal.setBackground(Color.BLACK);
		painelPrincipal.setForeground(Color.BLACK);
		painelDadosIniciais.setBackground(Color.WHITE);
		/*
		 * URL url = getClass().getResource("/imagens/fundo_azul_escuro.jpg"); ImageIcon
		 * img = new ImageIcon(url); painelDadosIniciais.setImg(img);
		 * painelDadosIniciais.repaint();
		 */

		// painel filho1
		// painelDadosIniciais.setBackground(Color.WHITE);

		// painel de dados bancarios
		painelDadosBancarios.setBackground(new Color(255, 255, 255));

		// painel finalizar
		painelFinalizar.setBackground(new Color(255, 255, 255));
		painelFinalizar.setLayout(null);

		// painel de contatos
		painelContato.setBackground(new Color(255, 255, 255));

		// adiciona o painel filho1 no painel principal
		painelPrincipal.addTab("Dados Iniciais", painelDadosIniciais);

		JLabel lblCadastro_2_1 = new JLabel(" ----- Cadastro / Dados da Empresa");
		lblCadastro_2_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCadastro_2_1.setForeground(Color.BLACK);
		lblCadastro_2_1.setFont(new Font("Arial", Font.PLAIN, 14));
		lblCadastro_2_1.setBackground(Color.ORANGE);
		lblCadastro_2_1.setBounds(0, 0, 250, 33);
		lblCadastro_2_1.setHorizontalAlignment(JLabel.LEFT);

		// painel dados trabalhisyas
		painelDadosTrabalhistas.setBackground(new Color(255, 255, 255));

		// adiciona o painel filho2 no painel principal
		painelPrincipal.addTab("Dados Trabalhistas", painelDadosTrabalhistas);
		painelDadosTrabalhistas.setLayout(new MigLayout("", "[1096px]", "[607px]"));

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		painelDadosTrabalhistas.add(panel_2, "cell 0 0,grow");
		panel_2.setLayout(new MigLayout("", "[][grow][][][][][][]", "[][][][][][grow][][][][][][][][][]"));

		JLabel lblCadastro_2_2 = new JLabel(" ----- Cadastro / Dados Civicos e Trabalhistas");
		panel_2.add(lblCadastro_2_2, "cell 0 0 2 1");
		lblCadastro_2_2.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCadastro_2_2.setForeground(Color.BLACK);
		lblCadastro_2_2.setFont(new Font("Arial", Font.BOLD, 14));
		lblCadastro_2_2.setBackground(Color.ORANGE);

		JPanel panel_7 = new JPanel();
		panel_7.setBackground(Color.WHITE);
		panel_2.add(panel_7, "cell 1 5 1 9,grow");
		panel_7.setLayout(new MigLayout("", "[]", "[][][][][]"));

		JPanel panel_6 = new JPanel();
		panel_7.add(panel_6, "cell 0 0");
		panel_6.setBackground(Color.WHITE);
		panel_6.setLayout(new MigLayout("", "[111px][4px][318px][59px][231px][74px][230px]", "[][23px][33px][33px]"));

		JLabel lblObrigaesCvicas = new JLabel("Obrigações Cívicas");
		lblObrigaesCvicas.setHorizontalAlignment(SwingConstants.TRAILING);
		lblObrigaesCvicas.setForeground(Color.BLACK);
		lblObrigaesCvicas.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblObrigaesCvicas.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 0, 0)));
		lblObrigaesCvicas.setBackground(Color.ORANGE);
		panel_6.add(lblObrigaesCvicas, "cell 0 0 7 1,alignx center,aligny top");

		JLabel lblTtuloDeEleitor = new JLabel("Título de Eleitor:");
		lblTtuloDeEleitor.setHorizontalAlignment(SwingConstants.TRAILING);
		lblTtuloDeEleitor.setForeground(Color.BLACK);
		lblTtuloDeEleitor.setFont(new Font("Arial", Font.PLAIN, 16));
		lblTtuloDeEleitor.setBackground(Color.ORANGE);
		panel_6.add(lblTtuloDeEleitor, "cell 0 2,alignx left,aligny bottom");

		entTituloEleitor = new JTextFieldPersonalizado();
		entTituloEleitor.setForeground(Color.black);
		panel_6.add(entTituloEleitor, "cell 2 2,growx,aligny top");

		JLabel lblSeo = new JLabel("Seção:");
		lblSeo.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSeo.setForeground(Color.BLACK);
		lblSeo.setFont(new Font("Arial", Font.PLAIN, 16));
		lblSeo.setBackground(Color.ORANGE);
		panel_6.add(lblSeo, "cell 3 2,alignx right,aligny bottom");

		entSecao = new JTextFieldPersonalizado();
		entSecao.setForeground(Color.black);
		panel_6.add(entSecao, "cell 4 2,growx,aligny top");

		JLabel lblZona = new JLabel("Zona:");
		lblZona.setHorizontalAlignment(SwingConstants.TRAILING);
		lblZona.setForeground(Color.BLACK);
		lblZona.setFont(new Font("Arial", Font.PLAIN, 16));
		lblZona.setBackground(Color.ORANGE);
		panel_6.add(lblZona, "cell 5 2,alignx right,aligny center");

		entZona = new JTextFieldPersonalizado();
		entZona.setForeground(Color.black);

		panel_6.add(entZona, "cell 6 2,growx,aligny top");

		JLabel lblCertidoMilitar = new JLabel("Certidão Militar:");
		lblCertidoMilitar.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCertidoMilitar.setForeground(Color.BLACK);
		lblCertidoMilitar.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCertidoMilitar.setBackground(Color.ORANGE);
		panel_6.add(lblCertidoMilitar, "cell 0 3,alignx left,aligny top");

		entCertidaoMilitar = new JTextFieldPersonalizado();
		entCertidaoMilitar.setForeground(Color.black);

		panel_6.add(entCertidaoMilitar, "cell 2 3,growx,aligny top");

		JLabel lblSrie_1 = new JLabel("Série:");
		lblSrie_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSrie_1.setForeground(Color.BLACK);
		lblSrie_1.setFont(new Font("Arial", Font.PLAIN, 16));
		lblSrie_1.setBackground(Color.ORANGE);
		panel_6.add(lblSrie_1, "cell 3 3,alignx right,aligny top");

		entSerieCertidaoMilitar = new JTextFieldPersonalizado();
		entSerieCertidaoMilitar.setForeground(Color.black);

		panel_6.add(entSerieCertidaoMilitar, "cell 4 3,growx,aligny top");

		JLabel lblSrie_1_1 = new JLabel("Categoria:");
		lblSrie_1_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSrie_1_1.setForeground(Color.BLACK);
		lblSrie_1_1.setFont(new Font("Arial", Font.PLAIN, 16));
		lblSrie_1_1.setBackground(Color.ORANGE);
		panel_6.add(lblSrie_1_1, "cell 5 3,alignx left,aligny top");

		entCategoriaCertidaoMilitar = new JTextFieldPersonalizado();
		entCategoriaCertidaoMilitar.setForeground(Color.black);

		panel_6.add(entCategoriaCertidaoMilitar, "cell 6 3,growx,aligny top");

		JPanel panel_5 = new JPanel();
		panel_7.add(panel_5, "cell 0 2");
		panel_5.setBackground(Color.WHITE);
		panel_5.setLayout(
				new MigLayout("", "[88px][4px][266px][4px][29px][265px][42px][266px][25px][65px]", "[][21px][33px]"));

		JLabel lblCarteiraDeTrabalho = new JLabel("Carteira de Trabalho e Previdência Social(CTPS)");
		lblCarteiraDeTrabalho.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCarteiraDeTrabalho.setForeground(Color.BLACK);
		lblCarteiraDeTrabalho.setFont(new Font("Arial", Font.BOLD, 16));
		lblCarteiraDeTrabalho.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 0, 0)));
		lblCarteiraDeTrabalho.setBackground(Color.ORANGE);
		panel_5.add(lblCarteiraDeTrabalho, "cell 5 0,alignx center,aligny top");

		JLabel lblPispasep = new JLabel("PIS/PASEP:");
		lblPispasep.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPispasep.setForeground(Color.BLACK);
		lblPispasep.setFont(new Font("Arial", Font.PLAIN, 16));
		lblPispasep.setBackground(Color.ORANGE);
		panel_5.add(lblPispasep, "cell 0 2,alignx left,aligny center");

		

		 entPis = new JTextFieldPersonalizado();
		entPis.setForeground(Color.black);

		panel_5.add(entPis, "cell 2 2,growx,aligny top");

		JLabel lblNis = new JLabel("NIS:");
		lblNis.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNis.setForeground(Color.BLACK);
		lblNis.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNis.setBackground(Color.ORANGE);
		panel_5.add(lblNis, "cell 4 2,alignx left,aligny center");

		entNis  = new JTextFieldPersonalizado();
		entNis.setForeground(Color.black);

		panel_5.add(entNis, "cell 5 2,growx,aligny top");

		JLabel lblSrie = new JLabel("Série:");
		lblSrie.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSrie.setForeground(Color.BLACK);
		lblSrie.setFont(new Font("Arial", Font.PLAIN, 16));
		lblSrie.setBackground(Color.ORANGE);
		panel_5.add(lblSrie, "cell 6 2,alignx left,aligny center");

		 entCtpsSerie = new JTextFieldPersonalizado();
		entCtpsSerie.setForeground(Color.black);

		panel_5.add(entCtpsSerie, "cell 7 2,growx,aligny top");

		JLabel lblUf_1 = new JLabel("UF:");
		lblUf_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblUf_1.setForeground(Color.BLACK);
		lblUf_1.setFont(new Font("Arial", Font.PLAIN, 16));
		lblUf_1.setBackground(Color.ORANGE);
		panel_5.add(lblUf_1, "cell 8 2,alignx left,aligny center");

		 cBCtpsUf = new JComboBox();
		 cBCtpsUf.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MG", "MS", "MT", "PA", "PB", "PE", "PI", "PR", "RJ", "RN", "RO", "RS", "SC", "SE", "SP", "TO" }));

		cBCtpsUf.setFont(new Font("Arial", Font.BOLD, 14));
		panel_5.add(cBCtpsUf, "cell 9 2,growx,aligny center");

		JPanel panel_4 = new JPanel();
		panel_7.add(panel_4, "cell 0 4,growx");
		panel_4.setBackground(Color.WHITE);
		panel_4.setLayout(new MigLayout("", "[74px][4px][237px][67px][237px][99px][237px]", "[][21px][33px]"));

		JLabel lblCnh_1_2 = new JLabel("Carteira Nacional de Habilitação");
		lblCnh_1_2.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCnh_1_2.setForeground(Color.BLACK);
		lblCnh_1_2.setFont(new Font("Arial", Font.BOLD, 16));
		lblCnh_1_2.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 0, 0)));
		lblCnh_1_2.setBackground(Color.ORANGE);
		panel_4.add(lblCnh_1_2, "cell 4 0,alignx center,aligny top");

		JLabel lblCnh_1 = new JLabel("Categoria:");
		lblCnh_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCnh_1.setForeground(Color.BLACK);
		lblCnh_1.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCnh_1.setBackground(Color.ORANGE);
		panel_4.add(lblCnh_1, "cell 0 2,alignx left,aligny center");

		 entCategoriaCnh = new JTextFieldPersonalizado();
		entCategoriaCnh.setForeground(Color.black);

		panel_4.add(entCategoriaCnh, "cell 2 2,alignx left,aligny top");

		JLabel lblCnh_1_1 = new JLabel("Validade:");
		lblCnh_1_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCnh_1_1.setForeground(Color.BLACK);
		lblCnh_1_1.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCnh_1_1.setBackground(Color.ORANGE);
		panel_4.add(lblCnh_1_1, "cell 3 2,alignx left,aligny center");

		 entValidadeCnh = new JTextFieldPersonalizado();
		entValidadeCnh.setForeground(Color.black);

		panel_4.add(entValidadeCnh, "cell 4 2,alignx left,aligny top");

		JLabel lblCnh_1_1_1 = new JLabel("Num Registro:");
		lblCnh_1_1_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCnh_1_1_1.setForeground(Color.BLACK);
		lblCnh_1_1_1.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCnh_1_1_1.setBackground(Color.ORANGE);
		panel_4.add(lblCnh_1_1_1, "cell 5 2,alignx left,aligny center");

		
		 entNumRegistroCnh = new JTextFieldPersonalizado();
		entNumRegistroCnh.setForeground(Color.black);

		panel_4.add(entNumRegistroCnh, "cell 6 2,alignx left,aligny top");

		// painel dados de habilidades e escolaridade
		painelInfoExtra.setBackground(new Color(255, 255, 255));

		// adiciona o painel filho2 no painel principal
		painelPrincipal.addTab("Escolaridade e Habilidades", painelInfoExtra);
		painelInfoExtra.setLayout(new MigLayout("", "[][grow]", "[][][][][][grow][][][][][][][][][grow]"));

		JLabel lblCadastro_2_2_1 = new JLabel(" ----- Cadastro / Dados  de Escolaridade e Habilidades\r\n");
		lblCadastro_2_2_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCadastro_2_2_1.setForeground(Color.BLACK);
		lblCadastro_2_2_1.setFont(new Font("Arial", Font.BOLD, 14));
		lblCadastro_2_2_1.setBackground(Color.ORANGE);
		painelInfoExtra.add(lblCadastro_2_2_1, "cell 0 0 2 1");

		JLabel lblEscolaridade = new JLabel("Escolaridade");
		lblEscolaridade.setHorizontalAlignment(SwingConstants.TRAILING);
		lblEscolaridade.setForeground(Color.BLACK);
		lblEscolaridade.setFont(new Font("Arial", Font.BOLD, 16));
		lblEscolaridade.setBackground(Color.ORANGE);
		painelInfoExtra.add(lblEscolaridade, "cell 0 2 2 1,alignx center");

		JLabel lblEnsino = new JLabel("Grau de Escolaridade:");
		lblEnsino.setHorizontalAlignment(SwingConstants.TRAILING);
		lblEnsino.setForeground(Color.BLACK);
		lblEnsino.setFont(new Font("Arial", Font.PLAIN, 16));
		lblEnsino.setBackground(Color.ORANGE);
		painelInfoExtra.add(lblEnsino, "cell 0 4,alignx trailing");

		  cBEnsino = new JComboBox();
		painelInfoExtra.add(cBEnsino, "cell 1 4,growx");
		cBEnsino.addItem("Ensino Básico ");
		cBEnsino.addItem("Ensino Fundamental Incompleto");
		cBEnsino.addItem("Ensino Fundamental Completo");
		cBEnsino.addItem("Ensino Médio Incompleto");
		cBEnsino.addItem("Ensino Médio Completo");
		cBEnsino.addItem("Ensino Superior");
		cBEnsino.addItem("Pós-Graduação");
		cBEnsino.addItem("Mestrado");
		cBEnsino.addItem("Doutorado");


		JLabel lblCursos = new JLabel("Cursos:");
		lblCursos.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCursos.setForeground(Color.BLACK);
		lblCursos.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCursos.setBackground(Color.ORANGE);
		painelInfoExtra.add(lblCursos, "cell 0 5,alignx right");

		
		 entCursos = new JTextArea();
		entCursos.setWrapStyleWord(true);
		entCursos.setLineWrap(true);
		entCursos.setFont(new Font("SansSerif", Font.BOLD, 14));
		entCursos.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelInfoExtra.add(entCursos, "cell 1 5,grow");

		JLabel lblHabilidades = new JLabel("Habilidades:");
		lblHabilidades.setHorizontalAlignment(SwingConstants.TRAILING);
		lblHabilidades.setForeground(Color.BLACK);
		lblHabilidades.setFont(new Font("Arial", Font.PLAIN, 16));
		lblHabilidades.setBackground(Color.ORANGE);
		painelInfoExtra.add(lblHabilidades, "cell 0 6,alignx right");

		 entHabilidades = new JTextArea();
		entHabilidades.setBorder(new LineBorder(new Color(0, 0, 0)));
		entHabilidades.setFont(new Font("SansSerif", Font.BOLD, 14));
		entHabilidades.setLineWrap(true);
		entHabilidades.setWrapStyleWord(true);
		painelInfoExtra.add(entHabilidades, "cell 1 6 1 5,grow");

		// adiciona o painel de dados ao painel principal
		painelPrincipal.addTab("Dados Bancarios", painelDadosBancarios);

		// adicionar o painel de contato ao painel principal
		painelPrincipal.addTab("Dados Contato", painelContato);

		JButton btnExcluirContato = new JButton("Excluir");
		btnExcluirContato.setForeground(Color.WHITE);
		btnExcluirContato.setBackground(Color.RED);
		btnExcluirContato.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnExcluirContato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int indiceDaLinha = table.getSelectedRow();
				if (flag_tipo_tela == 1) {

					String id_excluir = table.getValueAt(indiceDaLinha, 0).toString();
					if (id_excluir.equals("0000")) {
					} else {
						contatos_excluir.add(Integer.parseInt(id_excluir));

					}

					((DefaultTableModel) table.getModel()).removeRow(indiceDaLinha);
					table.repaint();
					table.validate();

				} else {

					((DefaultTableModel) table.getModel()).removeRow(indiceDaLinha);
					table.repaint();
					table.validate();

				}
			}
		});
		painelContato.setLayout(new MigLayout("", "[113px][3px][131px][85px][4px][9px][94px][5px][220px][9px][grow]",
				"[33px][138px][54px][4px][33px][44px][33px][11px][3px][17px][33px][]"));
		btnExcluirContato.setHorizontalAlignment(SwingConstants.LEADING);
		painelContato.add(btnExcluirContato, "cell 10 2,alignx right,aligny top");

		JPanel panel = new JPanel();
		painelContato.add(panel, "cell 0 1 11 1,grow");

		modelo.addColumn("id");
		modelo.addColumn("Nome");
		modelo.addColumn("Cargo");
		modelo.addColumn("Celular");
		modelo.addColumn("Fixo");
		modelo.addColumn("E-mail");
		modelo.addColumn("Descrição");
		modelo.addColumn("Observação");
		modelo.setNumRows(0);

		table = new JTable(modelo);
		table.setBackground(new Color(255, 255, 255));

		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.getColumnModel().getColumn(1).setPreferredWidth(20);
		table.getColumnModel().getColumn(2).setPreferredWidth(130);
		table.getColumnModel().getColumn(3).setPreferredWidth(30);
		table.getColumnModel().getColumn(4).setPreferredWidth(30);
		table.getColumnModel().getColumn(5).setPreferredWidth(100);

		table.setRowHeight(30);

		panel.setLayout(new BorderLayout(0, 0));
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setAutoscrolls(true);
		scrollPane.getViewport().setBackground(Color.white);
		scrollPane.setBackground(new Color(255, 255, 255));
		panel.add(scrollPane);

		JLabel lblCelular = new JLabel("Celular:");
		lblCelular.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCelular.setForeground(Color.BLACK);
		lblCelular.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblCelular.setBackground(Color.ORANGE);
		painelContato.add(lblCelular, "cell 0 5,grow");

		entCelularContato = new JTextFieldPersonalizado();
		entCelularContato.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				String caracteres = "0987654321\b";// lista de caracters que não devem ser aceitos
				String texto = entCelularContato.getText();
				if (!caracteres.contains(evt.getKeyChar() + "")) {
					evt.consume();// aciona esse propriedade para eliminar a ação do evento
				} else {
					if (texto.length() == 1 && evt.getKeyChar() != '\b') {
						entCelularContato.setText("(" + entCelularContato.getText());
					}
					if (texto.length() == 3 && evt.getKeyChar() != '\b') {
						entCelularContato.setText(entCelularContato.getText().concat(") "));
					}

					if (texto.length() == 6 && evt.getKeyChar() != '\b') {
						entCelularContato.setText(entCelularContato.getText().concat(" "));
					}

					if (texto.length() == 11 && evt.getKeyChar() != '\b') {
						entCelularContato.setText(entCelularContato.getText().concat("-"));
					}

					if (entCelularContato.getText().length() >= 16) {
						// if para saber se precisa verificar também o tamanho da string do campo
						// maior ou igual ao tamanho máximo, cancela e nao deixa inserir mais
						evt.consume();
						entCelularContato.setText(entCelularContato.getText().substring(0, 16));
					}

				}
			}
		});

		entCelularContato.setForeground(Color.BLACK);
		entCelularContato.setColumns(10);
		painelContato.add(entCelularContato, "cell 2 5 3 1,growx,aligny top");

		JLabel lblNome_1 = new JLabel("Nome:");
		lblNome_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNome_1.setForeground(Color.BLACK);
		lblNome_1.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblNome_1.setBackground(Color.ORANGE);
		painelContato.add(lblNome_1, "cell 0 2,growx,aligny bottom");

		JLabel lblCargo = new JLabel("Cargo:");
		lblCargo.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCargo.setForeground(Color.BLACK);
		lblCargo.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblCargo.setBackground(Color.ORANGE);
		painelContato.add(lblCargo, "cell 0 4,grow");

		entNomeContato = new JTextFieldPersonalizado();
		entNomeContato.setForeground(Color.BLACK);
		entNomeContato.setColumns(10);
		painelContato.add(entNomeContato, "cell 2 2 3 1,growx,aligny bottom");

		JComboBox cBCargo = new JComboBox();
		cBCargo.addItem("Secretaria");
		cBCargo.addItem("Auxiliar de Escritorio");
		cBCargo.addItem("Contato Particular");
		painelContato.add(cBCargo, "cell 2 4 3 1,grow");

		JLabel lblFixo = new JLabel("Fixo:");
		lblFixo.setHorizontalAlignment(SwingConstants.TRAILING);
		lblFixo.setForeground(Color.BLACK);
		lblFixo.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblFixo.setBackground(Color.ORANGE);
		painelContato.add(lblFixo, "cell 0 6,grow");

		entFixoContato = new JTextFieldPersonalizado();
		entFixoContato.setForeground(Color.BLACK);
		entFixoContato.setColumns(10);
		painelContato.add(entFixoContato, "cell 2 6 3 1,growx,aligny top");

		JLabel lblEmail = new JLabel("E-mail:");
		lblEmail.setHorizontalAlignment(SwingConstants.TRAILING);
		lblEmail.setForeground(Color.BLACK);
		lblEmail.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblEmail.setBackground(Color.ORANGE);
		painelContato.add(lblEmail, "cell 0 8 1 3,growx,aligny top");

		entEmailContato = new JTextFieldPersonalizado();
		entEmailContato.setForeground(Color.BLACK);
		entEmailContato.setColumns(10);
		painelContato.add(entEmailContato, "cell 2 8 3 3,growx,aligny top");

		JLabel lblNome_1_1 = new JLabel("Descrição:");
		lblNome_1_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNome_1_1.setForeground(Color.BLACK);
		lblNome_1_1.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblNome_1_1.setBackground(Color.ORANGE);
		painelContato.add(lblNome_1_1, "cell 6 2,growx,aligny bottom");

		entDescricaoContato = new JTextFieldPersonalizado();
		entDescricaoContato.setForeground(Color.BLACK);
		entDescricaoContato.setColumns(10);
		painelContato.add(entDescricaoContato, "cell 8 2 1 3,grow");

		JLabel lblNome_1_1_1 = new JLabel("Observação:");
		lblNome_1_1_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNome_1_1_1.setForeground(Color.BLACK);
		lblNome_1_1_1.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblNome_1_1_1.setBackground(Color.ORANGE);
		painelContato.add(lblNome_1_1_1, "cell 4 5 3 1,grow");

		entObservacaoContato = new JTextFieldPersonalizado();
		entObservacaoContato.setForeground(Color.BLACK);
		entObservacaoContato.setColumns(10);
		painelContato.add(entObservacaoContato, "cell 8 5 1 4,grow");

		JLabel lblCadastro_1 = new JLabel(" ----- Cadastro / Dados de Contatos");
		lblCadastro_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCadastro_1.setForeground(Color.BLACK);
		lblCadastro_1.setFont(new Font("Arial", Font.BOLD, 14));
		lblCadastro_1.setBackground(Color.ORANGE);
		lblCadastro_1.setHorizontalAlignment(JLabel.LEFT);

		painelContato.add(lblCadastro_1, "cell 0 0 3 1,grow");

		JButton btnAdicionarNovoContato = new JButton("Adicionar");
		btnAdicionarNovoContato.setForeground(Color.WHITE);
		btnAdicionarNovoContato.setBackground(new Color(0, 51, 0));
		btnAdicionarNovoContato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nome, cargo, celular, fixo, email, observacao, descricao, id;

				id = "0000";
				nome = entNomeContato.getText().toString();
				cargo = cBCargo.getSelectedItem().toString();
				celular = entCelularContato.getText().toString();
				fixo = entFixoContato.getText().toString();
				email = entEmailContato.getText().toString();
				observacao = entObservacaoContato.getText().toString();
				descricao = entDescricaoContato.getText().toString();

				celular = celular.replace("(", "").replace(")", "").replace(" ", "").replace("-", "");
				if (celular.length() != 11) {
					JOptionPane.showMessageDialog(isto, "Contato com número de celular incorreto");
				} else {

					modelo.addRow(new Object[] { id, nome, cargo, celular, fixo, email, descricao, observacao });
				}

			}
		});
		painelContato.add(btnAdicionarNovoContato, "cell 8 11,alignx right,growy");

		// adiciona o painel finalizar no painel principal
		painelPrincipal.addTab("Finalizar Cadastro", painelFinalizar);

		lblCodigoGerado = new JLabel("Codigo Gerado:");
		lblCodigoGerado.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCodigoGerado.setForeground(Color.BLACK);
		lblCodigoGerado.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblCodigoGerado.setBackground(Color.ORANGE);
		lblCodigoGerado.setBounds(298, 193, 150, 33);
		painelFinalizar.add(lblCodigoGerado);

		JLabel lblNewLabel = new JLabel(
				"Revise os dados nas telas anteriores, quando tiver tudo pronto, clique em 'Salvar' para realizar o cadastro no Banco de Dados");
		lblNewLabel.setBounds(22, 11, 753, 32);
		painelFinalizar.add(lblNewLabel);

		JLabel lblApelido = new JLabel("Apelido:");
		lblApelido.setHorizontalAlignment(SwingConstants.TRAILING);
		lblApelido.setForeground(Color.BLACK);
		lblApelido.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblApelido.setBackground(Color.ORANGE);
		lblApelido.setBounds(298, 237, 150, 33);
		painelFinalizar.add(lblApelido);

		entApelido = new JTextFieldPersonalizado();
		entApelido.setForeground(Color.BLACK);
		entApelido.setColumns(10);
		entApelido.setBounds(458, 237, 220, 33);
		painelFinalizar.add(entApelido);

		lblCodigo = new JLabel("0000000000");
		lblCodigo.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCodigo.setForeground(Color.BLACK);
		lblCodigo.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblCodigo.setBackground(Color.ORANGE);
		lblCodigo.setBounds(458, 193, 150, 33);
		painelFinalizar.add(lblCodigo);

		JButton btnFinalizarCadastro = new JButton("Salvar");
		btnFinalizarCadastro.setBackground(new Color(0, 51, 0));
		btnFinalizarCadastro.setForeground(Color.WHITE);
		btnFinalizarCadastro.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnFinalizarCadastro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				 CadastroFuncionario funcionario = new CadastroFuncionario();
				 funcionario = getDadosSalvar();
				 if(funcionario != null) {
					 
				

				 GerenciarBancoFuncionarios gerenciar = new GerenciarBancoFuncionarios();
				 boolean inserir= gerenciar.inserir(funcionario);
				 if(inserir) {
					 JOptionPane.showMessageDialog(isto, "Cadastro Concluído");
						isto.dispose();

				 }else {
					 JOptionPane.showMessageDialog(isto, "Erro ao Cadastrar\nConsulte o administrador");
						isto.dispose();

				 }
				 }
				

			}
		});
		btnFinalizarCadastro.setBounds(589, 430, 76, 33);
		painelFinalizar.add(btnFinalizarCadastro);
		
		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 CadastroFuncionario funcionario_atualizar = getDadosAtualizar(funcionario);
				 if(funcionario_atualizar != null) {
					 
					 getDadosContato(funcionario_atualizar);
						getDadosBancarios(funcionario_atualizar);
						
						boolean permitir_cadastro = true;
						GerenciarBancoFuncionarios atualizar = new GerenciarBancoFuncionarios();

						
							// excluir contatos que tiver para excluir
							for (Integer id_contato : contatos_excluir) {
								if (atualizar.deleteContato(id_contato, funcionario_atualizar.getId_funcionario()) == false) {
									JOptionPane.showMessageDialog(isto, "Erro ao deletar os contatos, corrupção no banco de dados!");
									permitir_cadastro = false;
									break;
								} else {
									permitir_cadastro = true;

								}
							}
							
							// excluir contas que tiver para excluir

							if (permitir_cadastro) {
								for (Integer id_conta : contas_excluir) {
									if (atualizar.deleteConta(id_conta, funcionario_atualizar.getId_funcionario()) == false) {
										JOptionPane.showMessageDialog(isto,
												"Erro ao deletar as contas, corrupção no banco de dados!");
										permitir_cadastro = false;
										break;
									} else {
										permitir_cadastro = true;

									}
								}
							}

							// adicionar contas bancarias
							if (permitir_cadastro) {
								if (funcionario_atualizar.getContas().size() > 0) {
									GerenciarBancoFuncionarios.RegistroAdicionarContaBancaria adicionar_contas = null;
									adicionar_contas = atualizar.adicionarContaBancaria(funcionario_atualizar.getContas(),
											funcionario_atualizar.getId_funcionario());
									if (adicionar_contas.isResposta() == true && adicionar_contas.ids_contas.size() > 0) {
										permitir_cadastro = true;
									} else {
										permitir_cadastro = false;
									}
								}
							}

							// adicionar contatos
							if (permitir_cadastro) {
								if (funcionario_atualizar.getContatos().size() > 0) {
									GerenciarBancoFuncionarios.RegistroAdicionarContato adicionar_contatos = null;
									adicionar_contatos = atualizar.adicionarContato(funcionario_atualizar.getContatos(),
											funcionario_atualizar.getId_funcionario());
									if (adicionar_contatos.isResposta() == true && adicionar_contatos.ids_contatos.size() > 0) {
										permitir_cadastro = true;
									} else {
										permitir_cadastro = false;
									}
								}
							}

							if (permitir_cadastro) {
								GerenciarBancoFuncionarios gerenciar = new GerenciarBancoFuncionarios();
								 boolean inserir= gerenciar.atualizarfuncionario(funcionario_atualizar);
								 if(inserir) {
									 JOptionPane.showMessageDialog(isto, "Cadastro Atualizado");
									  ((TelaGerenciarFuncionario) janela_pai).atualizarFuncionario();
										isto.dispose();

								 }else {
									 JOptionPane.showMessageDialog(isto, "Erro ao atualizar\nConsulte o administrador");

								 }
							}
						}

		
			}
		});
		btnAtualizar.setBackground(new Color(0, 0, 153));
		btnAtualizar.setForeground(Color.WHITE);
		btnAtualizar.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnAtualizar.setBounds(480, 430, 95, 33);
		painelFinalizar.add(btnAtualizar);

	
		
		painelDadosIniciais.setLayout(new MigLayout("", "[1102px]", "[grow]"));

		// adiciona o painel pessoa fisica no painel dinamico
		painelDadosIniciais.add(panelPessoaFisica, "cell 0 0,grow");
		panelPessoaFisica.setLayout(new MigLayout("", "[1071px,grow]", "[][253px][][][grow]"));

		JLabel lblCadastro_2 = new JLabel(" ----- Cadastro / Dados de Pessoa");
		panelPessoaFisica.add(lblCadastro_2, "cell 0 0");
		lblCadastro_2.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCadastro_2.setForeground(Color.BLACK);
		lblCadastro_2.setFont(new Font("Arial", Font.BOLD, 14));
		lblCadastro_2.setBackground(Color.ORANGE);
		lblCadastro_2.setHorizontalAlignment(JLabel.LEFT);

		JPanel panel_3 = new JPanel();
		panelPessoaFisica.add(panel_3, "cell 0 1,alignx center,growy");
		panel_3.setBackground(Color.WHITE);
		panel_3.setLayout(
				new MigLayout("", "[grow][grow][][grow][grow][][][grow][]", "[][][][][][][][][][][][][grow][][][]"));

		JLabel lblCpf = new JLabel("CPF:");
		panel_3.add(lblCpf, "cell 0 1,alignx right");
		lblCpf.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCpf.setForeground(Color.BLACK);
		lblCpf.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCpf.setBackground(Color.ORANGE);
		lblCpf.setHorizontalAlignment(JLabel.RIGHT);

		entCpf = new JTextFieldPersonalizado();
		panel_3.add(entCpf, "cell 1 1");

		entCpf.setForeground(Color.BLACK);
		entCpf.setFont(new Font("Arial", Font.BOLD, 20));
		entCpf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				// 120.927.987-00
				String caracteres = "0987654321\b";// lista de caracters que não devem ser aceitos
				String texto = entCpf.getText();
				if (!caracteres.contains(evt.getKeyChar() + "")) {
					evt.consume();// aciona esse propriedade para eliminar a ação do evento
				} else {
					if (texto.length() == 3 && evt.getKeyChar() != '\b') {
						entCpf.setText(entCpf.getText().concat("."));
					}
					if (texto.length() == 7 && evt.getKeyChar() != '\b') {
						entCpf.setText(entCpf.getText().concat("."));
					}

					if (texto.length() == 11 && evt.getKeyChar() != '\b') {
						entCpf.setText(entCpf.getText().concat("-"));
					}

					if (entCpf.getText().length() >= 14) {
						// if para saber se precisa verificar também o tamanho da string do campo
						// maior ou igual ao tamanho máximo, cancela e nao deixa inserir mais
						evt.consume();
						entCpf.setText(entCpf.getText().substring(0, 14));
					}

				}

			}
		});
		entCpf.setColumns(10);

		JLabel lblUf = new JLabel("UF:");
		panel_3.add(lblUf, "cell 2 1,alignx right");
		lblUf.setHorizontalAlignment(SwingConstants.TRAILING);
		lblUf.setForeground(Color.BLACK);
		lblUf.setFont(new Font("Arial", Font.PLAIN, 16));
		lblUf.setBackground(Color.ORANGE);
		lblUf.setHorizontalAlignment(JLabel.LEFT);

		 cBUFPessoa = new JComboBox();
		 cBUFPessoa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MG", "MS", "MT", "PA", "PB", "PE", "PI", "PR", "RJ", "RN", "RO", "RS", "SC", "SE", "SP", "TO" }));
		panel_3.add(cBUFPessoa, "cell 3 1");
		cBUFPessoa.setFont(new Font("Arial", Font.BOLD, 14));
		cBUFPessoa.addItem("MG");
		cBUFPessoa.addItem("SP");
		cBUFPessoa.addItem("GO");
		cBUFPessoa.addItem("MA");

		JLabel lblRg = new JLabel("RG:");
		panel_3.add(lblRg, "cell 4 1,alignx right");
		lblRg.setHorizontalAlignment(SwingConstants.TRAILING);
		lblRg.setForeground(Color.BLACK);
		lblRg.setFont(new Font("Arial", Font.PLAIN, 16));
		lblRg.setBackground(Color.ORANGE);
		lblRg.setHorizontalAlignment(JLabel.LEFT);

		entRg = new JTextFieldPersonalizado();
		panel_3.add(entRg, "cell 5 1 3 1,growx");
		entRg.setForeground(Color.BLACK);
		entRg.setFont(new Font("Arial", Font.BOLD, 20));
		entRg.setColumns(10);

		JLabel lblNomeEmpresarial = new JLabel("Nacionalidade:");
		panel_3.add(lblNomeEmpresarial, "cell 0 2,alignx right");
		lblNomeEmpresarial.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNomeEmpresarial.setForeground(Color.BLACK);
		lblNomeEmpresarial.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNomeEmpresarial.setBackground(Color.ORANGE);
		lblNomeEmpresarial.setHorizontalAlignment(JLabel.LEFT);

		entNacionalidade = new JTextFieldPersonalizado();
		panel_3.add(entNacionalidade, "cell 1 2,growx");
		entNacionalidade.setForeground(Color.BLACK);
		entNacionalidade.setFont(new Font("Arial", Font.BOLD, 20));
		entNacionalidade.setColumns(10);

		JLabel lblNaturalidade = new JLabel("Naturalidade:");
		lblNaturalidade.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNaturalidade.setForeground(Color.BLACK);
		lblNaturalidade.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNaturalidade.setBackground(Color.ORANGE);
		panel_3.add(lblNaturalidade, "cell 2 2,alignx right");

		entNaturalidade = new JTextFieldPersonalizado();
		entNaturalidade.setForeground(Color.black);
		panel_3.add(entNaturalidade, "cell 3 2 5 1,growx");

		JLabel lblNome = new JLabel("Nome:");
		panel_3.add(lblNome, "cell 0 3,alignx right");
		lblNome.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNome.setForeground(Color.BLACK);
		lblNome.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNome.setBackground(Color.ORANGE);
		lblNome.setHorizontalAlignment(JLabel.LEFT);

		entNome = new JTextFieldPersonalizado();
		panel_3.add(entNome, "cell 1 3 4 1,growx");
		entNome.setForeground(Color.BLACK);
		entNome.setFont(new Font("Arial", Font.BOLD, 20));
		entNome.setColumns(10);

		JLabel lblSobrenome = new JLabel("Sobrenome:");
		panel_3.add(lblSobrenome, "cell 5 3,alignx right");
		lblSobrenome.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSobrenome.setForeground(Color.BLACK);
		lblSobrenome.setFont(new Font("Arial", Font.PLAIN, 16));
		lblSobrenome.setBackground(Color.ORANGE);
		lblSobrenome.setHorizontalAlignment(JLabel.LEFT);

		entSobrenome = new JTextFieldPersonalizado();
		panel_3.add(entSobrenome, "cell 6 3 2 1,growx");
		entSobrenome.setForeground(Color.BLACK);
		entSobrenome.setFont(new Font("Arial", Font.BOLD, 20));
		entSobrenome.setColumns(10);

		JLabel lblDataNascimento = new JLabel("Nascimento:");
		panel_3.add(lblDataNascimento, "cell 0 4,alignx right");
		lblDataNascimento.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDataNascimento.setForeground(Color.BLACK);
		lblDataNascimento.setFont(new Font("Arial", Font.PLAIN, 16));
		lblDataNascimento.setBackground(Color.ORANGE);
		lblDataNascimento.setHorizontalAlignment(JLabel.LEFT);

		entNascimento = new JTextFieldPersonalizado();
		panel_3.add(entNascimento, "cell 1 4");
		entNascimento.setForeground(Color.BLACK);
		entNascimento.setFont(new Font("Arial", Font.BOLD, 20));
		entNascimento.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				String caracteres = "0987654321\b";// lista de caracters que não devem ser aceitos
				String texto = entNascimento.getText();
				if (!caracteres.contains(evt.getKeyChar() + "")) {
					evt.consume();// aciona esse propriedade para eliminar a ação do evento
				} else {
					if (texto.length() == 2 && evt.getKeyChar() != '\b') {
						entNascimento.setText(entNascimento.getText().concat("/"));
					}
					if (texto.length() == 5 && evt.getKeyChar() != '\b') {
						entNascimento.setText(entNascimento.getText().concat("/"));
					}

					if (entNascimento.getText().length() >= 10) {
						// if para saber se precisa verificar também o tamanho da string do campo
						// maior ou igual ao tamanho máximo, cancela e nao deixa inserir mais
						evt.consume();
						entNascimento.setText(entNascimento.getText().substring(0, 10));
					}

				}

			}
		});
		entNascimento.setToolTipText("Data de Nascimento, somente números");
		entNascimento.setColumns(10);

		JLabel lblSexo = new JLabel("Gênero:");
		panel_3.add(lblSexo, "cell 2 4,alignx right");
		lblSexo.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSexo.setForeground(Color.BLACK);
		lblSexo.setFont(new Font("Arial", Font.PLAIN, 16));
		lblSexo.setBackground(Color.ORANGE);

		 cBGenero = new JComboBox();
		cBGenero.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_3.add(cBGenero, "cell 3 4 3 1,growx");
		cBGenero.addItem("Masculino");
		cBGenero.addItem("Feminino");

		JLabel lblEstadoCivil = new JLabel("Estado Civil:");
		lblEstadoCivil.setHorizontalAlignment(SwingConstants.TRAILING);
		lblEstadoCivil.setForeground(Color.BLACK);
		lblEstadoCivil.setFont(new Font("Arial", Font.PLAIN, 16));
		lblEstadoCivil.setBackground(Color.ORANGE);
		panel_3.add(lblEstadoCivil, "cell 6 4,alignx trailing");

		entEstadoCivil = new JTextFieldPersonalizado();
		entEstadoCivil.setForeground(Color.black);
		panel_3.add(entEstadoCivil, "cell 7 4,growx");

		JLabel lblFiliao = new JLabel("Filiação:");
		lblFiliao.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 0, 0)));
		lblFiliao.setHorizontalAlignment(SwingConstants.TRAILING);
		lblFiliao.setForeground(Color.BLACK);
		lblFiliao.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblFiliao.setBackground(Color.ORANGE);
		panel_3.add(lblFiliao, "cell 0 5 8 1,alignx center");

		JLabel lblOcupao = new JLabel("Nome da Mãe:");
		panel_3.add(lblOcupao, "cell 0 6,alignx right");
		lblOcupao.setHorizontalAlignment(SwingConstants.TRAILING);
		lblOcupao.setForeground(Color.BLACK);
		lblOcupao.setFont(new Font("Arial", Font.PLAIN, 16));
		lblOcupao.setBackground(Color.ORANGE);
		lblOcupao.setHorizontalAlignment(JLabel.LEFT);

		entNomeDaMae = new JTextFieldPersonalizado();
		panel_3.add(entNomeDaMae, "cell 1 6 7 1,growx");
		entNomeDaMae.setForeground(Color.BLACK);
		entNomeDaMae.setFont(new Font("Arial", Font.BOLD, 20));
		entNomeDaMae.setToolTipText("Data de Nascimento, somente números");
		entNomeDaMae.setColumns(10);

		JLabel lblNomeDaPai = new JLabel("Nome da Pai:");
		lblNomeDaPai.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNomeDaPai.setForeground(Color.BLACK);
		lblNomeDaPai.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNomeDaPai.setBackground(Color.ORANGE);
		panel_3.add(lblNomeDaPai, "cell 0 7,alignx trailing");

		entNomeDoPai = new JTextFieldPersonalizado();
		entNomeDoPai.setFont(new Font("Arial", Font.BOLD, 20));

		entNomeDoPai.setForeground(Color.black);
		panel_3.add(entNomeDoPai, "cell 1 7 7 1,growx");

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBackground(Color.WHITE);
		panelPessoaFisica.add(panel_1, "cell 0 4,grow");

		JLabel lblLogradouro_1 = new JLabel("Endereço:");
		lblLogradouro_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblLogradouro_1.setForeground(Color.BLACK);
		lblLogradouro_1.setFont(new Font("Arial", Font.BOLD, 14));
		lblLogradouro_1.setBackground(Color.ORANGE);
		lblLogradouro_1.setBounds(7, 7, 72, 17);
		panel_1.add(lblLogradouro_1);

		JLabel lblLogradouro = new JLabel("Rua:");
		lblLogradouro.setHorizontalAlignment(SwingConstants.TRAILING);
		lblLogradouro.setForeground(Color.BLACK);
		lblLogradouro.setFont(new Font("Arial", Font.PLAIN, 14));
		lblLogradouro.setBackground(Color.ORANGE);
		lblLogradouro.setBounds(49, 37, 30, 17);
		panel_1.add(lblLogradouro);

		entLogradouro = new JTextFieldPersonalizado();
		entLogradouro.setForeground(Color.black);

		entLogradouro.setBounds(83, 28, 531, 33);
		panel_1.add(entLogradouro);

		JLabel lblBairro_1_2 = new JLabel("CEP:");
		lblBairro_1_2.setHorizontalAlignment(SwingConstants.TRAILING);
		lblBairro_1_2.setForeground(Color.BLACK);
		lblBairro_1_2.setFont(new Font("Arial", Font.PLAIN, 14));
		lblBairro_1_2.setBackground(Color.ORANGE);
		lblBairro_1_2.setBounds(618, 37, 32, 17);
		panel_1.add(lblBairro_1_2);

		entCep = new JTextFieldPersonalizado();
		entCep.setForeground(Color.black);

		entCep.setBounds(654, 28, 237, 33);
		panel_1.add(entCep);

		JButton btnLocalizar = new JButton("Localizar por Cep");
		btnLocalizar.setToolTipText("Buscar Endereço por Cep");
		btnLocalizar.setFont(new Font("Algerian", Font.PLAIN, 16));
		btnLocalizar.setBounds(896, 29, 179, 34);
		panel_1.add(btnLocalizar);

		JLabel lblN = new JLabel("N.º:");
		lblN.setHorizontalAlignment(SwingConstants.TRAILING);
		lblN.setForeground(Color.BLACK);
		lblN.setFont(new Font("Arial", Font.PLAIN, 14));
		lblN.setBackground(Color.ORANGE);
		lblN.setBounds(57, 76, 22, 17);
		panel_1.add(lblN);

		entNumEndereco = new JTextFieldPersonalizado();
		entNumEndereco.setForeground(Color.black);

		entNumEndereco.setBounds(83, 67, 237, 33);
		panel_1.add(entNumEndereco);

		JLabel lblCidade = new JLabel("Cidade:");
		lblCidade.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCidade.setForeground(Color.BLACK);
		lblCidade.setFont(new Font("Arial", Font.PLAIN, 14));
		lblCidade.setBackground(Color.ORANGE);
		lblCidade.setBounds(324, 76, 49, 17);
		panel_1.add(lblCidade);

		entCidade = new JTextFieldPersonalizado();
		entCidade.setBounds(377, 67, 237, 33);
		entCidade.setForeground(Color.black);

		panel_1.add(entCidade);

		JButton btnLocalizarPorUfcidaderua = new JButton("Localizar por UF/Cidade/Rua");
		btnLocalizarPorUfcidaderua.setToolTipText("Buscar CEP por informação de UF/Cidade/Rua");
		btnLocalizarPorUfcidaderua.setFont(new Font("Algerian", Font.PLAIN, 16));
		btnLocalizarPorUfcidaderua.setBounds(618, 68, 274, 34);
		panel_1.add(btnLocalizarPorUfcidaderua);

		JLabel lblBairro = new JLabel("Bairro:");
		lblBairro.setHorizontalAlignment(SwingConstants.TRAILING);
		lblBairro.setForeground(Color.BLACK);
		lblBairro.setFont(new Font("Arial", Font.PLAIN, 14));
		lblBairro.setBackground(Color.ORANGE);
		lblBairro.setBounds(37, 115, 42, 17);
		panel_1.add(lblBairro);

		entBairro = new JTextFieldPersonalizado();
		entBairro.setBounds(83, 106, 237, 33);
		entBairro.setForeground(Color.black);

		panel_1.add(entBairro);

		JLabel lblBairro_1_1 = new JLabel("Estado:");
		lblBairro_1_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblBairro_1_1.setForeground(Color.BLACK);
		lblBairro_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		lblBairro_1_1.setBackground(Color.ORANGE);
		lblBairro_1_1.setBounds(325, 115, 48, 17);
		panel_1.add(lblBairro_1_1);
		
		 cBUFEndereco = new JComboBox();
		cBUFEndereco.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MG", "MS", "MT", "PA", "PB", "PE", "PI", "PR", "RJ", "RN", "RO", "RS", "SC", "SE", "SP", "TO" }));

		cBUFEndereco.setFont(new Font("Arial", Font.BOLD, 14));
		cBUFEndereco.setBounds(377, 110, 57, 27);
		panel_1.add(cBUFEndereco);

		cBUFPessoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				uf = cBUFPessoa.getSelectedItem().toString();

			}
		});

		uf = cBUFPessoa.getSelectedItem().toString();
		painelDadosBancarios.setLayout(new MigLayout("", "[124px][4px][220px][21px][89px][232px][grow]",
				"[33px][138px][53px][37px][35px][35px][35px][35px][]"));

		// configura os widgets no painel de dados Bancarios

		JLabel lblBanco = new JLabel("Banco:");
		lblBanco.setHorizontalAlignment(SwingConstants.TRAILING);
		lblBanco.setForeground(Color.BLACK);
		lblBanco.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblBanco.setBackground(Color.ORANGE);
		painelDadosBancarios.add(lblBanco, "cell 0 4,alignx right,growy");

		entBanco = new JTextFieldPersonalizado();
		entBanco.setForeground(Color.BLACK);
		entBanco.setColumns(10);
		painelDadosBancarios.add(entBanco, "cell 2 4 3 1,growx,aligny bottom");

		JLabel lblCdigo = new JLabel("Código:");
		lblCdigo.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCdigo.setForeground(Color.BLACK);
		lblCdigo.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblCdigo.setBackground(Color.ORANGE);
		painelDadosBancarios.add(lblCdigo, "cell 0 5,alignx right,growy");

		JLabel lblConta = new JLabel("Conta:");
		lblConta.setHorizontalAlignment(SwingConstants.TRAILING);
		lblConta.setForeground(Color.BLACK);
		lblConta.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblConta.setBackground(Color.ORANGE);
		painelDadosBancarios.add(lblConta, "cell 0 7,alignx right,growy");

		JLabel lblAgncia = new JLabel("Agência:");
		lblAgncia.setHorizontalAlignment(SwingConstants.TRAILING);
		lblAgncia.setForeground(Color.BLACK);
		lblAgncia.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblAgncia.setBackground(Color.ORANGE);
		painelDadosBancarios.add(lblAgncia, "cell 0 6,alignx right,growy");

		entCodBanco = new JTextFieldPersonalizado();
		entCodBanco.setForeground(Color.BLACK);
		entCodBanco.setColumns(10);
		painelDadosBancarios.add(entCodBanco, "cell 2 5 3 1,growx,aligny bottom");

		entConta = new JTextFieldPersonalizado();
		entConta.setForeground(Color.BLACK);
		entConta.setColumns(10);
		painelDadosBancarios.add(entConta, "cell 2 7 3 1,growx,aligny bottom");

		entAgencia = new JTextFieldPersonalizado();
		entAgencia.setForeground(Color.BLACK);
		entAgencia.setColumns(10);
		painelDadosBancarios.add(entAgencia, "cell 2 6 3 1,growx,aligny bottom");

		JLabel lblCpfTitular = new JLabel("CPF Titular:");
		lblCpfTitular.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCpfTitular.setForeground(Color.BLACK);
		lblCpfTitular.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblCpfTitular.setBackground(Color.ORANGE);
		painelDadosBancarios.add(lblCpfTitular, "cell 0 2,alignx right,aligny bottom");

		entCpfTitular = new JTextFieldPersonalizado();
		entCpfTitular.setForeground(Color.BLACK);
		entCpfTitular.setColumns(10);
		painelDadosBancarios.add(entCpfTitular, "cell 2 2 3 1,growx,aligny bottom");

		painel_table_cb = new JPanel();

		table_cb = new JTable(modelo_cb);
		table_cb.setBackground(new Color(255, 255, 255));

		modelo_cb.addColumn("Id");
		modelo_cb.addColumn("CPF");
		modelo_cb.addColumn("Nome");

		modelo_cb.addColumn("Banco");

		modelo_cb.addColumn("Codigo");
		modelo_cb.addColumn("Agência");

		modelo_cb.addColumn("Conta");

		table_cb.getColumnModel().getColumn(0).setPreferredWidth(20);
		table_cb.getColumnModel().getColumn(2).setPreferredWidth(100);
		table_cb.getColumnModel().getColumn(1).setPreferredWidth(20);
		table_cb.getColumnModel().getColumn(3).setPreferredWidth(130);
		table_cb.getColumnModel().getColumn(4).setPreferredWidth(30);
		table_cb.getColumnModel().getColumn(5).setPreferredWidth(30);
		table_cb.getColumnModel().getColumn(6).setPreferredWidth(100);
		modelo_cb.setNumRows(0);

		table_cb.setRowHeight(30);

		painel_table_cb.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPaneCB = new JScrollPane(table_cb);
		scrollPaneCB.setAutoscrolls(true);
		scrollPaneCB.getViewport().setBackground(Color.white);
		scrollPaneCB.setBackground(new Color(255, 255, 255));
		painel_table_cb.add(scrollPaneCB);

		painelDadosBancarios.add(painel_table_cb, "cell 0 1 7 1,grow");

		JButton btnExcluirCB = new JButton("Excluir");
		btnExcluirCB.setBackground(new Color(204, 0, 0));
		btnExcluirCB.setForeground(Color.WHITE);
		btnExcluirCB.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnExcluirCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int indiceDaLinha = table_cb.getSelectedRow();

				if (flag_tipo_tela == 1) {

					String id_excluir = table_cb.getValueAt(indiceDaLinha, 0).toString();
					if (id_excluir.equals("0000")) {
					} else {
						contas_excluir.add(Integer.parseInt(id_excluir));

					}

					((DefaultTableModel) table_cb.getModel()).removeRow(indiceDaLinha);
					table_cb.repaint();
					table_cb.validate();

				} else {

					((DefaultTableModel) table_cb.getModel()).removeRow(indiceDaLinha);
					table_cb.repaint();
					table_cb.validate();

				}

			}
		});
		painelDadosBancarios.add(btnExcluirCB, "cell 6 2,alignx right,aligny top");

		JLabel lblNomeTitular = new JLabel("Nome Titular:");
		lblNomeTitular.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNomeTitular.setForeground(Color.BLACK);
		lblNomeTitular.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblNomeTitular.setBackground(Color.ORANGE);
		painelDadosBancarios.add(lblNomeTitular, "cell 0 3,alignx right,growy");

		entNomeContaBancaria = new JTextFieldPersonalizado();
		entNomeContaBancaria.setForeground(Color.BLACK);
		entNomeContaBancaria.setColumns(10);
		painelDadosBancarios.add(entNomeContaBancaria, "cell 2 3 3 1,growx,aligny top");

		JLabel lblCadastro = new JLabel(" ----- Cadastro / Dados Bancários");
		lblCadastro.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCadastro.setForeground(Color.BLACK);
		lblCadastro.setFont(new Font("Arial", Font.BOLD, 14));
		lblCadastro.setBackground(Color.ORANGE);
		lblCadastro.setHorizontalAlignment(JLabel.LEFT);

		painelDadosBancarios.add(lblCadastro, "cell 0 0 3 1,alignx left,growy");

		JButton btnAdicionarCB = new JButton("Adicionar");
		btnAdicionarCB.setBackground(new Color(0, 51, 0));
		btnAdicionarCB.setForeground(Color.WHITE);
		btnAdicionarCB.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnAdicionarCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String cpf, banco, codigo, agencia, conta, id, nome;

				id = "0000";
				cpf = entCpfTitular.getText().toString();
				banco = entBanco.getText().toString();
				nome = entNomeContaBancaria.getText().toString();
				codigo = entCodBanco.getText().toString();
				agencia = entAgencia.getText().toString();
				conta = entConta.getText().toString();

				modelo_cb.addRow(new Object[] { id, cpf, nome, banco, codigo, agencia, conta });

			}
		});
		painelCentral.setLayout(new BorderLayout(0, 0));
		painelDadosBancarios.add(btnAdicionarCB, "cell 4 8,growx,aligny center");

		// configura widgets no painel finalizar

		// getContentPane().add(painelPrincipal);
		painelCentral.add(painelPrincipal);

		

		/*
		 * for(int i = 0; i < isto.getComponents().length; i++) {
		 * 
		 * 
		 * if(isto.getComponent(i) instanceof outros.JTextFieldPersonalizado) {
		 * 
		 * JTextFieldPersonalizado caixa_texto = (JTextFieldPersonalizado)
		 * panelPessoaFisica.getComponent(i); caixa_texto.addFocusListener(new
		 * FocusAdapter() {
		 * 
		 * @Override public void focusGained(FocusEvent e) {
		 * System.out.println("Ganhou focu"); caixa_texto.setFocusGained();
		 * 
		 * 
		 * }
		 * 
		 * @Override public void focusLost(FocusEvent e) {
		 * 
		 * caixa_texto.setFocusLost(); } }); } }
		 */
		
		if(flag_tipo_tela == 0) {
			
			btnAtualizar.setEnabled(false);
			btnAtualizar.setVisible(false);
			
		}else if(flag_tipo_tela == 1) {
			btnFinalizarCadastro.setEnabled(false);
			btnFinalizarCadastro.setVisible(false);
			rotinasEdicao(funcionario);
		}
		
		adicionarFocus(isto.getComponents());

		this.setLocationRelativeTo(janela_pai);

	}

	public void adicionarFocus(Component[] components) {
		for (Component c : components) {
			if (c instanceof JTextFieldPersonalizado) {
				if (c instanceof JTextFieldPersonalizado) {

					JTextFieldPersonalizado caixa_texto = (JTextFieldPersonalizado) c;
					caixa_texto.addFocusListener(new FocusAdapter() {
						@Override
						public void focusGained(FocusEvent e) {
							System.out.println("Ganhou focu");
							caixa_texto.setFocusGained();

						}

						@Override
						public void focusLost(FocusEvent e) {

							caixa_texto.setFocusLost();
						}
					});
				}
			} else {
				Container novo_container = (Container) c;
				// if (0 < novo_container.getComponents())
				{
					adicionarFocus(novo_container.getComponents());
				}
			}
		}
	}

	public void getDadosBancarios(CadastroFuncionario cadastro) {

		// dados conta bancaria
		int num_row_table_bc = table_cb.getRowCount();

		System.out.println("Numero de contas bancarias: " + num_row_table_bc);
		ArrayList<ContaBancaria> contas = new ArrayList<>();
		for (int i = 0; i < num_row_table_bc; i++) {
			String id = table_cb.getValueAt(i, 0).toString();
			String cpf = table_cb.getValueAt(i, 1).toString();
			String nome = table_cb.getValueAt(i, 2).toString();
			String banco = table_cb.getValueAt(i, 3).toString();
			String codigo = table_cb.getValueAt(i, 4).toString();
			String agencia = table_cb.getValueAt(i, 5).toString();
			String conta = table_cb.getValueAt(i, 6).toString();

			if (!id.equals("0000")) {

			} else {
				ContaBancaria ct = new ContaBancaria();

				ct.setNome(nome);
				ct.setCpf_titular(cpf);
				ct.setBanco(banco);
				ct.setCodigo(codigo);
				ct.setAgencia(agencia);
				ct.setConta(conta);

				contas.add(ct);
			}

		}
		// CadastroFuncionario funcionario : listafuncionarios.getfuncionarios()
		for (ContaBancaria conta : contas) {
			System.out.println("CPF: " + conta.getCpf_titular());
			System.out.println("Banco: " + conta.getBanco());
			System.out.println("Codigo: " + conta.getCodigo());
			System.out.println("Agencia: " + conta.getAgencia());
			System.out.println("Conta: " + conta.getConta());

		}

		cadastro.setContas(contas);

	}
/*
	public boolean atualizar(int flag_armazem) {
		boolean permitir_cadastro = false;

		if (flag_armazem == 5 || flag_armazem == 6)
			funcionario_atualizar.setArmazem(1);
		else
			funcionario_atualizar.setArmazem(0);

		funcionario_atualizar.setTransportador(0);

		permitir_cadastro = getDadosPessoaFisica(funcionario_atualizar);
		System.out.println("Dados de pessoa fisica foram adicionados para atualizar? - " + permitir_cadastro);

		if (permitir_cadastro) {
			getDadosContato(funcionario_atualizar);
			getDadosBancarios(funcionario_atualizar);

		}
		// dados de apelido e finalizar contrato

		permitir_cadastro = getDadosFinais(funcionario_atualizar);
		System.out.println("Dados finais foram adicionados para atualizar? - " + permitir_cadastro);

		GerenciarBancoFuncionarios atualizar = new GerenciarBancoFuncionarios();

		if (permitir_cadastro) {
			// excluir contatos que tiver para excluir
			for (Integer id_contato : contatos_excluir) {
				if (atualizar.deleteContato(id_contato, funcionario_atualizar.getId()) == false) {
					JOptionPane.showMessageDialog(isto, "Erro ao deletar os contatos, corrupção no banco de dados!");
					permitir_cadastro = false;
					break;
				} else {
					permitir_cadastro = true;

				}
			}
			// excluir contas que tiver para excluir

			if (permitir_cadastro) {
				for (Integer id_conta : contas_excluir) {
					if (atualizar.deleteConta(id_conta, funcionario_atualizar.getId()) == false) {
						JOptionPane.showMessageDialog(isto,
								"Erro ao deletar os contatos, corrupção no banco de dados!");
						permitir_cadastro = false;
						break;
					} else {
						permitir_cadastro = true;

					}
				}
			}

			// adicionar contas bancarias
			if (permitir_cadastro) {
				if (funcionario_atualizar.getContas().size() > 0) {
					GerenciarBancoFuncionarios.RegistroAdicionarContaBancaria adicionar_contas = null;
					adicionar_contas = atualizar.adicionarContaBancaria(funcionario_atualizar.getContas(),
							funcionario_atualizar.getId());
					if (adicionar_contas.isResposta() == true && adicionar_contas.ids_contas.size() > 0) {
						permitir_cadastro = true;
					} else {
						permitir_cadastro = false;
					}
				}
			}

			// adicionar contatos
			if (permitir_cadastro) {
				if (funcionario_atualizar.getContatos().size() > 0) {
					GerenciarBancoFuncionarios.RegistroAdicionarContato adicionar_contatos = null;
					adicionar_contatos = atualizar.adicionarContato(funcionario_atualizar.getContatos(),
							funcionario_atualizar.getId());
					if (adicionar_contatos.isResposta() == true && adicionar_contatos.ids_contatos.size() > 0) {
						permitir_cadastro = true;
					} else {
						permitir_cadastro = false;
					}
				}
			}

			if (permitir_cadastro) {
				System.out.println("tentando atualizar funcionario");
				boolean atualizou = atualizar.atualizarfuncionario(funcionario_atualizar);
				if (atualizou) {
					JOptionPane.showMessageDialog(isto, "Cadastro Atualizado!");
					// isto.dispose();
					permitir_cadastro = true;

				} else {
					JOptionPane.showMessageDialog(isto, "Erro ao atualizar");
					permitir_cadastro = false;

				}

			}
		}

		return permitir_cadastro;

	}*/

	
	public void getDadosContato(CadastroFuncionario cadastro) {
		// dados contato
		int num_row_table = table.getRowCount();
		System.out.println("Numero de Contatos : " + num_row_table);
		ArrayList<Contato> contatos = new ArrayList<>();
		for (int i = 0; i < num_row_table; i++) {

			String id = table.getValueAt(i, 0).toString();
			String nome = table.getValueAt(i, 1).toString();
			String cargo = table.getValueAt(i, 2).toString();
			String celular = table.getValueAt(i, 3).toString();
			String fixo = table.getValueAt(i, 4).toString();
			String e_mail = table.getValueAt(i, 5).toString();
			String descricao = table.getValueAt(i, 6).toString();
			String observacao = table.getValueAt(i, 7).toString();

			if (!id.equals("0000")) {

			} else {
				Contato ct = new Contato();
				ct.setId(0000);
				ct.setNome(nome);
				ct.setCargo(cargo);
				ct.setCelular(celular);
				ct.setFixo(fixo);
				ct.setE_mail(e_mail);
				ct.setDescricao(descricao);
				ct.setObservacao(observacao);

				contatos.add(ct);
			}

		}
		// CadastroFuncionario funcionario : listafuncionarios.getfuncionarios()
		for (Contato contato : contatos) {
			System.out.println("Nome: " + contato.getNome());
			System.out.println("Cargo: " + contato.getCargo());
			System.out.println("Celular: " + contato.getCelular());
			System.out.println("Fixo: " + contato.getFixo());
			System.out.println("E-mail: " + contato.getE_mail());
			System.out.println("Descrição: " + contato.getDescricao());
			System.out.println("Observação: " + contato.getObservacao());

		}

		cadastro.setContatos(contatos);

	}

	/*

	public void gerarPastas() {
		ManipularTxt manipularArquivos = new ManipularTxt();
		String nome_pasta;

		if (funcionario_cadastrar.getTipo_pessoa() == 0) {
			nome_pasta = funcionario_cadastrar.getNome_empresarial().toUpperCase();
		} else {
			nome_pasta = funcionario_cadastrar.getNome_fantaia().toUpperCase();
		}

		int ano_atual = new GetData().getAnoAtual();

		String ano_passado_pasta = Integer.toString(ano_atual - 1);
		String ano_atual_pasta = Integer.toString(ano_atual);
		String ano_que_vem_pasta = Integer.toString(ano_atual + 1);

		DadosGlobais dados = DadosGlobais.getInstance();
		configs_globais = dados.getConfigs_globais();

		String unidade_base_dados = configs_globais.getServidorUnidade();
		String sub_pasta = "E-Contract\\arquivos\\funcionarios";

		String compra = "COMPRA";
		String venda = "VENDA";

		String soja = "SOJA";
		String sorgo = "SORGO";
		String milho = "MILHO";

		String compra_venda[] = { "COMPRA", "VENDA" };
		String produtos[] = { "SOJA", "MILHO", "SORGO" };
		String anos[] = { ano_passado_pasta, ano_atual_pasta, ano_que_vem_pasta };

		for (int cv = 0; cv <= 1; cv++) {
			for (int ano = 0; ano <= 2; ano++) {
				for (int produto = 0; produto <= 2; produto++) {
					String caminho_completo = unidade_base_dados + "\\" + sub_pasta + "\\" + nome_pasta.toUpperCase()
							+ "\\" + "CONTRATOS" + "\\" + compra_venda[cv] + "\\" + anos[ano] + "\\"
							+ produtos[produto];
					manipularArquivos.criarDiretorio(caminho_completo);
				}

			}
		}

		// criar diretorio de dados pessoas

		String caminho_completo = unidade_base_dados + "\\" + sub_pasta + "\\" + nome_pasta.toUpperCase() + "\\"
				+ "DADOS PESSOAS";

		manipularArquivos.criarDiretorio(caminho_completo);

		// criar diretorio de notas fiscais
		String caminho_completo_nf = unidade_base_dados + "\\" + sub_pasta + "\\" + nome_pasta.toUpperCase() + "\\"
				+ "NOTAS FISCAIS";

		manipularArquivos.criarDiretorio(caminho_completo_nf);

		// criar diretorio de romaneios
		String caminho_completo_romaneios = unidade_base_dados + "\\" + sub_pasta + "\\" + nome_pasta.toUpperCase()
				+ "\\" + "ROMANEIOS";

		manipularArquivos.criarDiretorio(caminho_completo_romaneios);

	}

	public void gerarPastasAtualizar() {
		ManipularTxt manipularArquivos = new ManipularTxt();
		String nome_pasta;

		if (funcionario_atualizar.getTipo_pessoa() == 0) {
			nome_pasta = funcionario_atualizar.getNome_empresarial().toUpperCase();
		} else {
			nome_pasta = funcionario_atualizar.getNome_fantaia().toUpperCase();
		}

		int ano_atual = new GetData().getAnoAtual();

		String ano_passado_pasta = Integer.toString(ano_atual - 1);
		String ano_atual_pasta = Integer.toString(ano_atual);
		String ano_que_vem_pasta = Integer.toString(ano_atual + 1);

		DadosGlobais dados = DadosGlobais.getInstance();
		configs_globais = dados.getConfigs_globais();

		String unidade_base_dados = configs_globais.getServidorUnidade();
		String sub_pasta = "E-Contract\\arquivos\\funcionarios";

		String compra = "COMPRA";
		String venda = "VENDA";

		String soja = "SOJA";
		String sorgo = "SORGO";
		String milho = "MILHO";

		String compra_venda[] = { "COMPRA", "VENDA" };
		String produtos[] = { "SOJA", "MILHO", "SORGO" };
		String anos[] = { ano_passado_pasta, ano_atual_pasta, ano_que_vem_pasta };

		for (int cv = 0; cv <= 1; cv++) {
			for (int ano = 0; ano <= 2; ano++) {
				for (int produto = 0; produto <= 2; produto++) {
					String caminho_completo = unidade_base_dados + "\\" + sub_pasta + "\\" + nome_pasta.toUpperCase()
							+ "\\" + "CONTRATOS" + "\\" + compra_venda[cv] + "\\" + anos[ano] + "\\"
							+ produtos[produto];
					manipularArquivos.criarDiretorio(caminho_completo);
				}

			}
		}

		// criar diretorio de dados pessoas

		String caminho_completo = unidade_base_dados + "\\" + sub_pasta + "\\" + nome_pasta.toUpperCase() + "\\"
				+ "DADOS PESSOAS";

		manipularArquivos.criarDiretorio(caminho_completo);

		// criar diretorio de notas fiscais
		String caminho_completo_nf = unidade_base_dados + "\\" + sub_pasta + "\\" + nome_pasta.toUpperCase() + "\\"
				+ "NOTAS FISCAIS";

		manipularArquivos.criarDiretorio(caminho_completo_nf);

		// criar diretorio de romaneios
		String caminho_completo_romaneios = unidade_base_dados + "\\" + sub_pasta + "\\" + nome_pasta.toUpperCase()
				+ "\\" + "ROMANEIOS";

		manipularArquivos.criarDiretorio(caminho_completo_romaneios);
	}*/

	public void getDadosGlobais() {
		// gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		GerenciadorLog = dados.getGerenciadorLog();
		configs_globais = dados.getConfigs_globais();

		// usuario logado
		login = dados.getLogin();

	}

	public void setTelaPai(JDialog tela_pai) {
		this.telaPai = tela_pai;
	}

	
		

		
	
	
	public CadastroFuncionario getDadosSalvar() {
		CadastroFuncionario novo_funcionario = new CadastroFuncionario();
		
		
		String cpf = entCpf.getText();
		CPFValidator cpfValidator = new CPFValidator();
		List<ValidationMessage> erros = cpfValidator.invalidMessagesFor(cpf);
		if (erros.size() > 0) {
			JOptionPane.showMessageDialog(isto, "CPF Inválido!");

		return null;
		
		}
		
		
		 String nome, sobrenome, rg, estado, nacionalidade, naturalidade, nascimento, genero, estado_civil;
		 String nome_mae, nome_pai;
		
		
		 nome = entNome.getText();
		 sobrenome = entSobrenome.getText();
		 rg = entRg.getText();
		 estado = cBUFPessoa.getSelectedItem().toString();
		 nacionalidade = entNacionalidade.getText();
		 naturalidade = entNaturalidade.getText();
		 nascimento = entNascimento.getText();
		 genero =cBGenero.getSelectedItem().toString();
		 
		 estado_civil = entEstadoCivil.getText();
		 nome_mae = entNomeDaMae.getText();
		 nome_pai = entNomeDoPai.getText();
		 
		 
		 novo_funcionario.setNome(nome);
		 novo_funcionario.setSobrenome(sobrenome);
		 novo_funcionario.setRg(rg);
		 novo_funcionario.setEstado(estado);
		 novo_funcionario.setNacionalidade(nacionalidade);
		 novo_funcionario.setNaturalidade(naturalidade);
		 novo_funcionario.setNascimento(nascimento);
		 novo_funcionario.setGenero(genero);
		 novo_funcionario.setEstado_civil(estado_civil);
		 novo_funcionario.setEstado_civil(estado_civil);
		 novo_funcionario.setNome_mae(nome_mae);
		 novo_funcionario.setNome_pai(nome_pai);
		 
		//dados de endereco
		 String rua, bairro, num_endereco, cidade, estado_endereco, cep;
		 rua = entLogradouro.getText();
		 bairro = entBairro.getText();
		 cidade = entCidade.getText();
		 estado_endereco = cBUFEndereco.getSelectedItem().toString();
		 num_endereco = entNumEndereco.getText();
		 cep = entCep.getText();
		 
		 
		 novo_funcionario.setRua(rua);
		 novo_funcionario.setBairro(bairro);
		 novo_funcionario.setNumero(num_endereco);
		 novo_funcionario.setCidade(cidade);
		 novo_funcionario.setEstado_endereco(estado_endereco);
		 novo_funcionario.setCep(cep);
		 
		//obrigacoes civicas
		 String titulo_eleitor, titulo_secao, titulo_zona;
		 titulo_eleitor = entTituloEleitor.getText();
		 titulo_secao = entSecao.getText();
		 titulo_zona = entZona.getText();
		 
		 
		 novo_funcionario.setTitulo_eleitor(titulo_eleitor);
		 novo_funcionario.setTitulo_secao(titulo_secao);
		 novo_funcionario.setTitulo_zona(titulo_zona);
		 
		 
		 String certida_militar, certidao_serie, certidao_categoria;
		
		 certida_militar = entCertidaoMilitar.getText();
		 certidao_serie = entSerieCertidaoMilitar.getText();
		 certidao_categoria = entCategoriaCertidaoMilitar.getText();
		 
		 novo_funcionario.setCertida_militar(certida_militar);
		 novo_funcionario.setCertiao_serie(certidao_serie);
		 novo_funcionario.setCertidao_categoria(certidao_categoria);
		 
		//ctps
		 String pis, nis, ctps_serie, ctps_estado;
		 pis = entPis.getText();
		 nis = entNis.getText();
		 ctps_serie = entCtpsSerie.getText();
		 ctps_estado = cBCtpsUf.getSelectedItem().toString();
		 
		 novo_funcionario.setPis(pis);
		 novo_funcionario.setNis(nis);
		 novo_funcionario.setCtps_serie(ctps_serie);
		 novo_funcionario.setCtps_estado(ctps_estado);
		 
		
		//cnh
		 String cnh_categoria, cnh_validade, cnh_num_registro;
		 
		 cnh_categoria = entCategoriaCnh.getText();
		 cnh_validade = entValidadeCnh.getText();
		 cnh_num_registro = entNumRegistroCnh.getText();
		 
		 novo_funcionario.setCnh_categoria(cnh_categoria);
		 novo_funcionario.setCnh_validade(cnh_validade);
		 novo_funcionario.setCnh_num_registro(cnh_num_registro);
		 
		//escolaridade
		 String grau_escolaridade, cursos, habilidades;
		 grau_escolaridade = cBEnsino.getSelectedItem().toString();
		 cursos = entCursos.getText();
		 habilidades = entHabilidades.getText();
		 
		 novo_funcionario.setGrau_escolaridade(grau_escolaridade);
		 novo_funcionario.setCursos(cursos);
		 novo_funcionario.setHabilidades(habilidades);
		 
		 novo_funcionario.setApelido(entApelido.getText());
		 
		 novo_funcionario.setCpf(cpf);

		 
		 
		 
		return novo_funcionario;	
		
		
		
		
		
	}
	
	public CadastroFuncionario getDadosAtualizar(CadastroFuncionario funcionario_antigo) {
	
		CadastroFuncionario novo_funcionario = new CadastroFuncionario();
		
	novo_funcionario.setId_funcionario(funcionario_antigo.getId_funcionario());
		
		 String nome, sobrenome, rg, estado, nacionalidade, naturalidade, nascimento, genero, estado_civil;
		 String nome_mae, nome_pai;
		
		
		 nome = entNome.getText();
		 sobrenome = entSobrenome.getText();
		 rg = entRg.getText();
		 estado = cBUFPessoa.getSelectedItem().toString();
		 nacionalidade = entNacionalidade.getText();
		 naturalidade = entNaturalidade.getText();
		 nascimento = entNascimento.getText();
		 genero =cBGenero.getSelectedItem().toString();
		 
		 estado_civil = entEstadoCivil.getText();
		 nome_mae = entNomeDaMae.getText();
		 nome_pai = entNomeDoPai.getText();
		 
		 
		 novo_funcionario.setNome(nome);
		 novo_funcionario.setSobrenome(sobrenome);
		 novo_funcionario.setRg(rg);
		 novo_funcionario.setEstado(estado);
		 novo_funcionario.setNacionalidade(nacionalidade);
		 novo_funcionario.setNaturalidade(naturalidade);
		 novo_funcionario.setNascimento(nascimento);
		 novo_funcionario.setGenero(genero);
		 novo_funcionario.setEstado_civil(estado_civil);
		 novo_funcionario.setEstado_civil(estado_civil);
		 novo_funcionario.setNome_mae(nome_mae);
		 novo_funcionario.setNome_pai(nome_pai);
		 
		//dados de endereco
		 String rua, bairro, num_endereco, cidade, estado_endereco, cep;
		 rua = entLogradouro.getText();
		 bairro = entBairro.getText();
		 cidade = entCidade.getText();
		 estado_endereco = cBUFEndereco.getSelectedItem().toString();
		 num_endereco = entNumEndereco.getText();
		 cep = entCep.getText();
		 
		 
		 novo_funcionario.setRua(rua);
		 novo_funcionario.setBairro(bairro);
		 novo_funcionario.setNumero(num_endereco);
		 novo_funcionario.setCidade(cidade);
		 novo_funcionario.setEstado_endereco(estado_endereco);
		 novo_funcionario.setCep(cep);
		 
		//obrigacoes civicas
		 String titulo_eleitor, titulo_secao, titulo_zona;
		 titulo_eleitor = entTituloEleitor.getText();
		 titulo_secao = entSecao.getText();
		 titulo_zona = entZona.getText();
		 
		 
		 novo_funcionario.setTitulo_eleitor(titulo_eleitor);
		 novo_funcionario.setTitulo_secao(titulo_secao);
		 novo_funcionario.setTitulo_zona(titulo_zona);
		 
		 
		 String certida_militar, certidao_serie, certidao_categoria;
		
		 certida_militar = entCertidaoMilitar.getText();
		 certidao_serie = entSerieCertidaoMilitar.getText();
		 certidao_categoria = entCategoriaCertidaoMilitar.getText();
		 
		 novo_funcionario.setCertida_militar(certida_militar);
		 novo_funcionario.setCertiao_serie(certidao_serie);
		 novo_funcionario.setCertidao_categoria(certidao_categoria);
		 
		//ctps
		 String pis, nis, ctps_serie, ctps_estado;
		 pis = entPis.getText();
		 nis = entNis.getText();
		 ctps_serie = entCtpsSerie.getText();
		 ctps_estado = cBCtpsUf.getSelectedItem().toString();
		 
		 novo_funcionario.setPis(pis);
		 novo_funcionario.setNis(nis);
		 novo_funcionario.setCtps_serie(ctps_serie);
		 novo_funcionario.setCtps_estado(ctps_estado);
		 
		
		//cnh
		 String cnh_categoria, cnh_validade, cnh_num_registro;
		 cnh_categoria = entCategoriaCnh.getText();
		 cnh_validade = entValidadeCnh.getText();
		 cnh_num_registro = entNumRegistroCnh.getText();
		 
		 novo_funcionario.setCnh_categoria(cnh_categoria);
		 novo_funcionario.setCnh_validade(cnh_validade);
		 novo_funcionario.setCnh_num_registro(cnh_num_registro);
		 
		//escolaridade
		 String grau_escolaridade, cursos, habilidades;
		 grau_escolaridade = cBEnsino.getSelectedItem().toString();
		 cursos = entCursos.getText();
		 habilidades = entHabilidades.getText();
		 
		 novo_funcionario.setGrau_escolaridade(grau_escolaridade);
		 novo_funcionario.setCursos(cursos);
		 novo_funcionario.setHabilidades(habilidades);
		 
		 novo_funcionario.setApelido(entApelido.getText());
		 
		 
		 
		 
		 
		return novo_funcionario;	
		
		
		
	}
	
	public void rotinasEdicao(CadastroFuncionario funcionario) {
		
		
			lblCodigo.setText(Integer.toString(funcionario.getId_funcionario()));

			entCpf.setText(funcionario.getCpf());
			entCpf.setEditable(false);
			cBUFPessoa.setSelectedItem(funcionario.getEstado());
			cBUFPessoa.setEnabled(false);
			entNome.setText(funcionario.getNome());
			entSobrenome.setText(funcionario.getSobrenome());
			entNascimento.setText(funcionario.getNascimento());
			entNacionalidade.setText(funcionario.getNacionalidade());
			entNaturalidade.setText(funcionario.getNaturalidade());

			entNomeDaMae.setText(funcionario.getNome_mae());
			entNomeDoPai.setText(funcionario.getNome_pai());
			entEstadoCivil.setText(funcionario.getEstado_civil());
			cBGenero.setSelectedItem(funcionario.getGenero());
			entRg.setText(funcionario.getRg());

			entLogradouro.setText(funcionario.getRua());
			entNumEndereco.setText(funcionario.getNumero());
			entBairro.setText(funcionario.getBairro());
			entCidade.setText(funcionario.getCidade());
			cBUFEndereco.setSelectedItem(funcionario.getEstado_endereco());
			entCep.setText(funcionario.getCep());

			entApelido.setText(funcionario.getApelido());
			
			//cnh
			 entCategoriaCnh.setText(funcionario.getCnh_categoria());
			 entValidadeCnh.setText(funcionario.getCnh_validade());
			entNumRegistroCnh.setText(funcionario.getCnh_num_registro());
			 
			 //escolaridade
			cBEnsino.setSelectedItem(funcionario.getGrau_escolaridade());
			entCursos.setText(funcionario.getCursos());
			entHabilidades.setText(funcionario.getHabilidades());
			 
			//ctps
			 entPis.setText(funcionario.getPis());
			 entNis.setText(funcionario.getNis());
			 entCtpsSerie.setText(funcionario.getCtps_serie());
			 cBCtpsUf.setSelectedItem(funcionario.getCtps_estado());
			
			 //certidao militar
			 entCertidaoMilitar.setText(funcionario.getCertida_militar());
			 entSerieCertidaoMilitar.setText(funcionario.getCertiao_serie());
			 entCategoriaCertidaoMilitar.setText(funcionario.getCertidao_categoria());
			 
			 //titulo eleitoal
			entTituloEleitor.setText(funcionario.getTitulo_eleitor());
			 entSecao.setText(funcionario.getTitulo_secao());
			 entZona.setText(funcionario.getTitulo_zona());
			 
			// dados contatos

			ArrayList<Contato> contatos = new ArrayList<>();
			GerenciarBancoFuncionarios gerenciar = new GerenciarBancoFuncionarios();
			contatos = gerenciar.getContatos(funcionario.getId_funcionario());

			for (Contato contato : contatos) {
				String nome, cargo, celular, fixo, email, observacao, descricao, id;

				id = Integer.toString(contato.getId());
				nome = contato.getNome();
				cargo = contato.getCargo();
				celular = contato.getCelular();
				fixo = contato.getFixo();
				email = contato.getE_mail();
				observacao = contato.getObservacao();
				descricao = contato.getDescricao();

				modelo.addRow(new Object[] { id, nome, cargo, celular, fixo, email, descricao, observacao });

			}

			// dados conta bancaria

			ArrayList<ContaBancaria> contas_bancaria = new ArrayList<>();
			contas_bancaria = gerenciar.getContas(funcionario.getId_funcionario());

			for (ContaBancaria conta_bancaria : contas_bancaria) {

				String cpf, banco, codigo, agencia, conta, id, nome;

				id = Integer.toString(conta_bancaria.getId_conta());
				cpf = conta_bancaria.getCpf_titular();
				nome = conta_bancaria.getNome();
				banco = conta_bancaria.getBanco();
				codigo = conta_bancaria.getCodigo();
				agencia = conta_bancaria.getAgencia();
				conta = conta_bancaria.getConta();

				modelo_cb.addRow(new Object[] { id, cpf, nome, banco, codigo, agencia, conta });
			}

		
	}
	
	

	public void buscarCepPorInfo(Endereco end) {
		try {

			BuscarCep busca = new BuscarCep(123);

			end = busca.buscarCep(end);

			// entCep.setText(end.getCep());

		} catch (Exception e1) {
			JOptionPane.showMessageDialog(isto, "Erro na consulta de cep");
		}

	}
}
