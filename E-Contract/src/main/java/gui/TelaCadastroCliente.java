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
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroContrato.CadastroPagamento;
import main.java.cadastros.CadastroContrato.CadastroPagamentoContratual;
import main.java.cadastros.CadastroContrato.Recebimento;
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
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoNotasFiscais;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoRomaneios;
import main.java.conexaoBanco.GerenciarBancoSafras;
import main.java.gui.TelaCadastroCliente;
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

public class TelaCadastroCliente extends JFrame {

	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;

	private final JPanelBackground contentPanel = new JPanelBackground();

	// painel pai
	private JTabbedPane painelPrincipal;

	// painel filho1JPanelTransparent
	private JPanelBackground painelDadosIniciais = new JPanelBackground();

	// painel dinamico, dentro de filho1
	private JPanelTransparent panelDinamico = new JPanelTransparent();;

	// painel pessoa fisica e juridico
	private JPanelTransparent panelPessoaFisica = new JPanelTransparent();
	private JPanelTransparent panelPessoaJuridica = new JPanelTransparent();
	private JComboBox cBTipoIdentificacao, cBUFIE;

	// outros paineis

	private JPanel painelEmpresa = new JPanel();
	private JPanel painelDadosBancarios = new JPanel();
	private JPanel painelSefaz = new JPanel();
	private JPanel painelContato = new JPanel();
	private JPanel painelFinalizar = new JPanel();

	private JTextFieldPersonalizado entLogradouro;
	private JTextFieldPersonalizado entNumEndereco;
	private JTextFieldPersonalizado entBairro;

	private String logradouro, bairro, cidade, estado;
	private int num, cep;
	private JTextFieldPersonalizado entCep;
	private JTextFieldPersonalizado entCidade;
	private JTextFieldPersonalizado entEstado;
	private JTextFieldPersonalizado entIE;
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
	private JTextFieldPersonalizado entOcupacao;
	private JTextFieldPersonalizado entCNPJ;
	private JTextFieldPersonalizado entRazaoSocial;
	private JTextFieldPersonalizado entNomeFantasia;
	private JTextFieldPersonalizado entStatus;
	private JTextFieldPersonalizado entDescricao;
	private JTextFieldPersonalizado entAtividadesPri;
	private JTextFieldPersonalizado entAtividadeSec;
	private JTextFieldPersonalizado entStatusIE;

	private String uf;
	private JTextFieldPersonalizado entNomeEmpresarial;
	private JTextFieldPersonalizado entPorte;
	private JTextFieldPersonalizado entCnae;
	private JTextFieldPersonalizado entCpfResponsavel;
	private JTextFieldPersonalizado entIdentificacaoSiare;
	private JTextFieldPersonalizado entSenhaSiare;
	private String tipoIdentificacao;
	private JTextFieldPersonalizado entApelido;
	private JTextFieldPersonalizado entCelularContato;
	private JTextFieldPersonalizado entNomeContato;
	private JTextFieldPersonalizado entFixoContato;
	private JTextFieldPersonalizado entEmailContato;
	private JTable table;
	JComboBox cBPessoa = new JComboBox();

	private JPanel painel_table_cb;
	private JTable table_cb;

	private DefaultTableModel modelo = new DefaultTableModel();
	private DefaultTableModel modelo_cb = new DefaultTableModel();
	private JTextFieldPersonalizado entDescricaoContato;
	private JTextFieldPersonalizado entObservacaoContato;
	private JTextFieldPersonalizado entNomeContaBancaria;
	private JButton btnSalvar;

	ArrayList<Integer> contatos_excluir = new ArrayList<>();
	ArrayList<Integer> contas_excluir = new ArrayList<>();

	CadastroCliente cliente_cadastrar = new CadastroCliente();
	CadastroCliente cliente_atualizar = new CadastroCliente();
	private JDialog telaPai;

	private JPanel painelCentral = new JPanel();
	private JLabel lblCodigoGerado, lblCodigo;
	private TelaCadastroCliente isto;

	public TelaCadastroCliente(int flag_tipo_tela, CadastroCliente cliente, Window janela_pai) {
		getContentPane().setBackground(new Color(0, 153, 0));
		getContentPane().setFont(new Font("Arial", Font.BOLD, 18));
		getContentPane().setForeground(Color.WHITE);
		setFont(new Font("Arial", Font.BOLD, 18));
		setForeground(new Color(255, 255, 255));

		getDadosGlobais();
		// setAlwaysOnTop(true);

		//setModal(true);
		isto = this;

		setResizable(false);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		if (flag_tipo_tela == 1 )
			setTitle("E-Contract - Novo Cliente");
		else if (flag_tipo_tela == 5)
			setTitle("E-Contract - Novo Armazém");
		else if (flag_tipo_tela == 6)
			setTitle("E-Contract - Editar Armazém");
		else if (flag_tipo_tela == 0) {
			setTitle("E-Contract - Editar Cliente");
			cliente_atualizar = cliente;
		}

		setBounds(100, 100, 1036, 587);
		painelCentral.setLayout(null);
		painelCentral.setBounds(208, 0, 822, 560);
		getContentPane().add(painelCentral);

		// configuracao de paineis
		// painel pai

		painelPrincipal = new JTabbedPane();

		painelPrincipal.setBounds(0, 0, 913, 560);
		painelPrincipal.setFont(new Font("Arial", Font.BOLD, 14));
		// painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		// painelPrincipal.setBackground(Color.BLACK);
		painelPrincipal.setForeground(Color.BLACK);
		painelDadosIniciais.setBackground(Color.WHITE);

		painelDadosIniciais.setLayout(null);
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

		// adiciona o painel de enderecos
		painelEmpresa.setBackground(new Color(255, 255, 255));
		painelEmpresa.setLayout(null);
		// adiciona o painel empresa no painel principal
		painelPrincipal.addTab("Empresa", painelEmpresa);

		// painel Siare
		painelSefaz.setBackground(new Color(255, 255, 255));

		// adiciona o painel siare no painel principal
		painelPrincipal.addTab("Dados Sefaz", painelSefaz);
		painelSefaz.setLayout(new MigLayout("", "[][335px][]", "[33px][36px][33px][35px][35px][][][][][][][][]"));

		JLabel lblCadastroCliente = new JLabel(" ----- Cadastro / Dados Sefaz");
		lblCadastroCliente.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCadastroCliente.setForeground(Color.BLACK);
		lblCadastroCliente.setFont(new Font("Arial", Font.BOLD, 14));
		lblCadastroCliente.setBackground(Color.ORANGE);
		lblCadastroCliente.setHorizontalAlignment(JLabel.LEFT);

		painelSefaz.add(lblCadastroCliente, "cell 0 0 2 1,alignx left,growy");
												
														JLabel lblTipoIdentificao = new JLabel("Tipo Identificação:");
														lblTipoIdentificao.setHorizontalAlignment(SwingConstants.TRAILING);
														lblTipoIdentificao.setForeground(Color.BLACK);
														lblTipoIdentificao.setFont(new Font("Arial", Font.PLAIN, 16));
														lblTipoIdentificao.setBackground(Color.ORANGE);
														painelSefaz.add(lblTipoIdentificao, "cell 0 4,alignx right,growy");
										
												cBTipoIdentificacao = new JComboBox();
												cBTipoIdentificacao.addItem("Produtor Rural");
												cBTipoIdentificacao.addItem("Inscrição Estadual");
												cBTipoIdentificacao.addItem("Protocolo");
												cBTipoIdentificacao.addActionListener(new ActionListener() {
													public void actionPerformed(ActionEvent arg0) {
														tipoIdentificacao = cBTipoIdentificacao.getSelectedItem().toString();

													}
												});
												
														painelSefaz.add(cBTipoIdentificacao, "cell 1 4 2 1,alignx left,growy");
								
										JLabel lblIdentificao = new JLabel("Identificação:");
										lblIdentificao.setHorizontalAlignment(SwingConstants.TRAILING);
										lblIdentificao.setForeground(Color.BLACK);
										lblIdentificao.setFont(new Font("Arial", Font.PLAIN, 16));
										lblIdentificao.setBackground(Color.ORANGE);
										painelSefaz.add(lblIdentificao, "cell 0 5,alignx right,growy");
										
												entIdentificacaoSiare = new JTextFieldPersonalizado();
												entIdentificacaoSiare.setForeground(Color.BLACK);
												entIdentificacaoSiare.setColumns(10);
												painelSefaz.add(entIdentificacaoSiare, "cell 1 5,growx,aligny top");
								
										JLabel lblCpfResponsavel = new JLabel("CPF Responsavel:");
										lblCpfResponsavel.setHorizontalAlignment(SwingConstants.TRAILING);
										lblCpfResponsavel.setForeground(Color.BLACK);
										lblCpfResponsavel.setFont(new Font("Arial", Font.PLAIN, 16));
										lblCpfResponsavel.setBackground(Color.ORANGE);
										painelSefaz.add(lblCpfResponsavel, "cell 0 6,alignx right,growy");
										
												entCpfResponsavel = new JTextFieldPersonalizado();
												entCpfResponsavel.setForeground(Color.BLACK);
												entCpfResponsavel.setColumns(10);
												painelSefaz.add(entCpfResponsavel, "cell 1 6,growx,aligny bottom");
								
										JLabel lblSenh = new JLabel("Senha:");
										lblSenh.setHorizontalAlignment(SwingConstants.TRAILING);
										lblSenh.setForeground(Color.BLACK);
										lblSenh.setFont(new Font("Arial", Font.PLAIN, 16));
										lblSenh.setBackground(Color.ORANGE);
										painelSefaz.add(lblSenh, "cell 0 7,alignx right,growy");
						
								entSenhaSiare = new JTextFieldPersonalizado();
								entSenhaSiare.setForeground(Color.BLACK);
								entSenhaSiare.setColumns(10);
								painelSefaz.add(entSenhaSiare, "cell 1 7,growx,aligny bottom");
		// adiciona o painel de dados ao painel principal
		painelPrincipal.addTab("Dados Bancarios", painelDadosBancarios);

		// adicionar o painel de contato ao painel principal
		painelPrincipal.addTab("Dados Contato", painelContato);

		JButton btnExcluirContato = new JButton("Excluir");
		btnExcluirContato.setForeground(Color.WHITE);
		btnExcluirContato.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnExcluirContato.setBackground(new Color(204, 0, 0));
		btnExcluirContato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int indiceDaLinha = table.getSelectedRow();
				if (flag_tipo_tela == 0 || flag_tipo_tela == 6) {

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
		painelContato.setLayout(new MigLayout("", "[113px][3px][131px][85px][4px][9px][94px][5px][220px][9px][89px]", "[33px][138px][54px][4px][33px][44px][33px][11px][3px][17px][33px][]"));
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
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.getViewport().setBackground(Color.white);
		panel.setLayout(new BorderLayout(0, 0));
		scrollPane.setAutoscrolls(true);
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
		painelContato.add(entEmailContato, "cell 2 8 3 2,growx,aligny top");

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
			btnAdicionarNovoContato.setBackground(new Color(0, 51, 0));
			btnAdicionarNovoContato.setForeground(Color.WHITE);
			btnAdicionarNovoContato.setFont(new Font("SansSerif", Font.BOLD, 14));
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
			painelContato.add(btnAdicionarNovoContato, "cell 8 11,grow");

		// adiciona o painel finalizar no painel principal
		painelPrincipal.addTab("Finalizar Cadastro", painelFinalizar);

		lblCodigoGerado = new JLabel("Codigo Gerado:");
		lblCodigoGerado.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCodigoGerado.setForeground(Color.BLACK);
		lblCodigoGerado.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblCodigoGerado.setBackground(Color.ORANGE);
		lblCodigoGerado.setBounds(77, 144, 150, 33);
		painelFinalizar.add(lblCodigoGerado);

		JLabel lblNewLabel = new JLabel(
				"Revise os dados nas telas anteriores, quando tiver tudo pronto, clique em 'Salvar' para realizar o cadastro no Banco de Dados");
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblNewLabel.setBounds(22, 11, 753, 32);
		painelFinalizar.add(lblNewLabel);

		JLabel lblApelido = new JLabel("Apelido:");
		lblApelido.setHorizontalAlignment(SwingConstants.TRAILING);
		lblApelido.setForeground(Color.BLACK);
		lblApelido.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblApelido.setBackground(Color.ORANGE);
		lblApelido.setBounds(77, 188, 150, 33);
		painelFinalizar.add(lblApelido);

		entApelido = new JTextFieldPersonalizado();
		entApelido.setForeground(Color.BLACK);
		entApelido.setColumns(10);
		entApelido.setBounds(237, 188, 220, 33);
		painelFinalizar.add(entApelido);

		lblCodigo = new JLabel("0000000000");
		lblCodigo.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCodigo.setForeground(Color.BLACK);
		lblCodigo.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblCodigo.setBackground(Color.ORANGE);
		lblCodigo.setBounds(237, 144, 150, 33);
		painelFinalizar.add(lblCodigo);

		btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(963, 502, 89, 23);
		painelFinalizar.add(btnSalvar);

		JButton btnFinalizarCadastro = new JButton("Salvar");
		btnFinalizarCadastro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (salvar(flag_tipo_tela) == true) {

					if (janela_pai != null) {
						if (flag_tipo_tela == 1 || flag_tipo_tela == 0) {
							// salvar novo cliente comun
							if(janela_pai != null)
							((TelaCliente) janela_pai).atualizaTabela();

						} else if (flag_tipo_tela == 5 || flag_tipo_tela == 6) {
							((TelaArmazem) janela_pai).atualizaTabela();

						}
					}

					gerarPastas();

					if (cliente_cadastrar.getIdentificacao_sefaz() != null
							&& cliente_cadastrar.getIdentificacao_sefaz().length() > 3
							&& !cliente_cadastrar.getIdentificacao_sefaz().equals(" ")) {

						if (cliente_cadastrar.getSenha() != null && cliente_cadastrar.getSenha().length() > 3
								&& !cliente_cadastrar.getSenha().equals(" ")) {

							/*BaixarNotasFiscais baixar = new BaixarNotasFiscais(cliente_cadastrar, "VENDA");
							baixar.iniciarPesquisas();
							*/
						}

					}

					isto.dispose();
				}

			}
		});
		btnFinalizarCadastro.setBounds(654, 410, 89, 23);
		painelFinalizar.add(btnFinalizarCadastro);

		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (atualizar(flag_tipo_tela) == true) {
					if (flag_tipo_tela == 0 || flag_tipo_tela == 1) {
						if(janela_pai != null)
							if(janela_pai instanceof TelaCliente) {
								((TelaCliente) janela_pai).atualizaTabela();

							}else if(janela_pai instanceof TelaGerenciarCliente) {
								((TelaGerenciarCliente) janela_pai).atualizarInfo();

							}
							
					} else if (flag_tipo_tela == 5 || flag_tipo_tela == 6) {
						((TelaArmazem) janela_pai).atualizaTabela();

					}
					gerarPastasAtualizar();
					/*BaixarNotasFiscais baixar = new BaixarNotasFiscais(cliente_atualizar,
					"VENDA");
					 baixar.iniciarPesquisas();
                     */
					isto.dispose();
				}
			}
		});
		btnAtualizar.setBounds(555, 410, 89, 23);
		painelFinalizar.add(btnAtualizar);

		if (flag_tipo_tela == 0 || flag_tipo_tela == 6) {
			btnAtualizar.setVisible(true);
			btnAtualizar.setEnabled(true);
		} else {
			btnAtualizar.setVisible(false);
			btnAtualizar.setEnabled(false);
		}
		// painel fisico e juridico

		// panelPessoaJuridica.setBackground(Color.WHITE);
		panelPessoaJuridica.setBounds(20, 70, 924, 355);
		panelPessoaJuridica.setLayout(null);
		panelPessoaJuridica.setBounds(22, 71, 1020, 471);

		// panelPessoaFisica.setBackground(new Color(139, 0, 139));

		panelPessoaFisica.setBounds(22, 71, 1020, 471);

		panelDinamico.setBounds(22, 71, 1020, 543);
		panelDinamico.setLayout(new CardLayout());

		// adiciona o painel pessoa fisica no painel dinamico
		panelDinamico.add(panelPessoaFisica, "PessoaFisica");

		// adiciona o painel pessoa juridic no painel dinamico
		panelDinamico.add(panelPessoaJuridica, "PessoaJuridica");

		JLabel lblCNPJ = new JLabel("CNPJ:");
		lblCNPJ.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCNPJ.setForeground(Color.BLACK);
		lblCNPJ.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCNPJ.setBackground(Color.ORANGE);
		lblCNPJ.setBounds(36, 126, 64, 33);
		lblCNPJ.setHorizontalAlignment(JLabel.LEFT);

		panelPessoaJuridica.add(lblCNPJ);

		entCNPJ = new JTextFieldPersonalizado();
		entCNPJ.setForeground(Color.BLACK);
		entCNPJ.setFont(new Font("Arial", Font.BOLD, 18));
		entCNPJ.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				// 00.969.790/0005-41
				String caracteres = "0987654321\b";// lista de caracters que não devem ser aceitos
				String texto = entCNPJ.getText();
				if (!caracteres.contains(evt.getKeyChar() + "")) {
					evt.consume();// aciona esse propriedade para eliminar a ação do evento
				} else {
					if (texto.length() == 2 && evt.getKeyChar() != '\b') {
						entCNPJ.setText(entCNPJ.getText().concat("."));
					}
					if (texto.length() == 6 && evt.getKeyChar() != '\b') {
						entCNPJ.setText(entCNPJ.getText().concat("."));
					}

					if (texto.length() == 10 && evt.getKeyChar() != '\b') {
						entCNPJ.setText(entCNPJ.getText().concat("/"));
					}

					if (texto.length() == 15 && evt.getKeyChar() != '\b') {
						entCNPJ.setText(entCNPJ.getText().concat("-"));
					}

					if (entCNPJ.getText().length() >= 18) {
						// if para saber se precisa verificar também o tamanho da string do campo
						// maior ou igual ao tamanho máximo, cancela e nao deixa inserir mais
						evt.consume();
						entCNPJ.setText(entCNPJ.getText().substring(0, 18));
					}

				}
			}
		});
		entCNPJ.setColumns(10);
		entCNPJ.setBounds(99, 126, 440, 33);
		panelPessoaJuridica.add(entCNPJ);

		JLabel lblRazoSocial = new JLabel("Razão Social:");
		lblRazoSocial.setHorizontalAlignment(SwingConstants.TRAILING);
		lblRazoSocial.setForeground(Color.BLACK);
		lblRazoSocial.setFont(new Font("Arial", Font.PLAIN, 16));
		lblRazoSocial.setBackground(Color.ORANGE);
		lblRazoSocial.setBounds(36, 170, 153, 33);
		lblRazoSocial.setHorizontalAlignment(JLabel.LEFT);

		panelPessoaJuridica.add(lblRazoSocial);

		JLabel lblNomeFantasia = new JLabel("Nome Fantasia:");
		lblNomeFantasia.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNomeFantasia.setForeground(Color.BLACK);
		lblNomeFantasia.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNomeFantasia.setBackground(Color.ORANGE);
		lblNomeFantasia.setBounds(36, 214, 138, 33);
		lblNomeFantasia.setHorizontalAlignment(JLabel.LEFT);

		panelPessoaJuridica.add(lblNomeFantasia);

		entRazaoSocial = new JTextFieldPersonalizado();
		entRazaoSocial.setForeground(Color.BLACK);
		entRazaoSocial.setFont(new Font("Arial", Font.BOLD, 18));
		entRazaoSocial.setColumns(10);
		entRazaoSocial.setBounds(146, 170, 558, 33);
		panelPessoaJuridica.add(entRazaoSocial);

		entNomeFantasia = new JTextFieldPersonalizado();
		entNomeFantasia.setForeground(Color.BLACK);
		entNomeFantasia.setFont(new Font("Arial", Font.BOLD, 18));
		entNomeFantasia.setColumns(10);
		entNomeFantasia.setBounds(158, 214, 546, 33);
		panelPessoaJuridica.add(entNomeFantasia);

		JButton btnVerificarCNPJ = new JButton("Verificar");
		btnVerificarCNPJ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String cnpj = entCNPJ.getText();

				pesquisarCNPJ(cnpj);

			}
		});
		btnVerificarCNPJ.setBounds(549, 126, 155, 33);
		panelPessoaJuridica.add(btnVerificarCNPJ);

		JLabel lblStatus = new JLabel("Status:");
		lblStatus.setHorizontalAlignment(SwingConstants.TRAILING);
		lblStatus.setForeground(Color.BLACK);
		lblStatus.setFont(new Font("Arial", Font.PLAIN, 16));
		lblStatus.setBackground(Color.ORANGE);
		lblStatus.setBounds(36, 258, 64, 33);
		lblStatus.setHorizontalAlignment(JLabel.LEFT);

		panelPessoaJuridica.add(lblStatus);

		entStatus = new JTextFieldPersonalizado();
		entStatus.setForeground(Color.BLACK);
		entStatus.setFont(new Font("Arial", Font.BOLD, 18));
		entStatus.setColumns(10);
		entStatus.setBounds(99, 258, 220, 33);
		panelPessoaJuridica.add(entStatus);

		JLabel lblDescrio = new JLabel("Descrição:");
		lblDescrio.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDescrio.setForeground(Color.BLACK);
		lblDescrio.setFont(new Font("Arial", Font.PLAIN, 16));
		lblDescrio.setBackground(Color.ORANGE);
		lblDescrio.setBounds(329, 258, 119, 33);
		lblDescrio.setHorizontalAlignment(JLabel.LEFT);

		panelPessoaJuridica.add(lblDescrio);

		entDescricao = new JTextFieldPersonalizado();
		entDescricao.setForeground(Color.BLACK);
		entDescricao.setFont(new Font("Arial", Font.BOLD, 18));
		entDescricao.setColumns(10);
		entDescricao.setBounds(414, 258, 290, 33);
		panelPessoaJuridica.add(entDescricao);

		JLabel lblAtividades = new JLabel("Atividades:");
		lblAtividades.setHorizontalAlignment(SwingConstants.TRAILING);
		lblAtividades.setForeground(Color.BLACK);
		lblAtividades.setFont(new Font("Arial", Font.PLAIN, 16));
		lblAtividades.setBackground(Color.ORANGE);
		lblAtividades.setBounds(36, 302, 119, 33);
		lblAtividades.setHorizontalAlignment(JLabel.LEFT);

		panelPessoaJuridica.add(lblAtividades);

		entAtividadesPri = new JTextFieldPersonalizado();
		entAtividadesPri.setForeground(Color.BLACK);
		entAtividadesPri.setFont(new Font("Arial", Font.BOLD, 18));
		entAtividadesPri.setColumns(10);
		entAtividadesPri.setBounds(131, 302, 573, 33);
		panelPessoaJuridica.add(entAtividadesPri);

		entAtividadeSec = new JTextFieldPersonalizado();
		entAtividadeSec.setForeground(Color.BLACK);
		entAtividadeSec.setFont(new Font("Arial", Font.BOLD, 18));
		entAtividadeSec.setColumns(10);
		entAtividadeSec.setBounds(131, 345, 573, 33);
		panelPessoaJuridica.add(entAtividadeSec);

		// adiciona o painel dinamico no painel filho 1
		painelDadosIniciais.add(panelDinamico);
		panelPessoaFisica.setLayout(null);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNome.setForeground(Color.BLACK);
		lblNome.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNome.setBackground(Color.ORANGE);
		lblNome.setBounds(21, 156, 72, 21);
		lblNome.setHorizontalAlignment(JLabel.LEFT);
		panelPessoaFisica.add(lblNome);

		entNome = new JTextFieldPersonalizado();
		entNome.setForeground(Color.BLACK);
		entNome.setFont(new Font("Arial", Font.BOLD, 20));
		entNome.setColumns(10);
		entNome.setBounds(74, 151, 242, 28);
		panelPessoaFisica.add(entNome);

		JLabel lblSobrenome = new JLabel("Sobrenome:");
		lblSobrenome.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSobrenome.setForeground(Color.BLACK);
		lblSobrenome.setFont(new Font("Arial", Font.PLAIN, 16));
		lblSobrenome.setBackground(Color.ORANGE);
		lblSobrenome.setBounds(326, 156, 103, 21);
		lblSobrenome.setHorizontalAlignment(JLabel.LEFT);
		panelPessoaFisica.add(lblSobrenome);

		entSobrenome = new JTextFieldPersonalizado();
		entSobrenome.setForeground(Color.BLACK);
		entSobrenome.setFont(new Font("Arial", Font.BOLD, 20));
		entSobrenome.setColumns(10);
		entSobrenome.setBounds(430, 151, 267, 28);
		panelPessoaFisica.add(entSobrenome);

		entNascimento = new JTextFieldPersonalizado();
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
		entNascimento.setBounds(124, 190, 242, 28);
		panelPessoaFisica.add(entNascimento);

		JLabel lblDataNascimento = new JLabel("Nascimento:");
		lblDataNascimento.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDataNascimento.setForeground(Color.BLACK);
		lblDataNascimento.setFont(new Font("Arial", Font.PLAIN, 16));
		lblDataNascimento.setBackground(Color.ORANGE);
		lblDataNascimento.setBounds(21, 195, 112, 21);
		lblDataNascimento.setHorizontalAlignment(JLabel.LEFT);
		panelPessoaFisica.add(lblDataNascimento);

		JLabel lblCpf = new JLabel("CPF:");
		lblCpf.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCpf.setForeground(Color.BLACK);
		lblCpf.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCpf.setBackground(Color.ORANGE);
		lblCpf.setHorizontalAlignment(JLabel.RIGHT);
		lblCpf.setBounds(22, 118, 43, 24);
		panelPessoaFisica.add(lblCpf);

		entCpf = new JTextFieldPersonalizado();

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
		entCpf.setBounds(74, 112, 347, 28);

		panelPessoaFisica.add(entCpf);

		entRg = new JTextFieldPersonalizado();
		entRg.setForeground(Color.BLACK);
		entRg.setFont(new Font("Arial", Font.BOLD, 20));
		entRg.setColumns(10);
		entRg.setBounds(412, 190, 285, 28);
		panelPessoaFisica.add(entRg);

		JLabel lblRg = new JLabel("RG:");
		lblRg.setHorizontalAlignment(SwingConstants.TRAILING);
		lblRg.setForeground(Color.BLACK);
		lblRg.setFont(new Font("Arial", Font.PLAIN, 16));
		lblRg.setBackground(Color.ORANGE);
		lblRg.setBounds(376, 195, 43, 21);
		lblRg.setHorizontalAlignment(JLabel.LEFT);
		panelPessoaFisica.add(lblRg);

		JLabel lblOcupao = new JLabel("Ocupação:");
		lblOcupao.setHorizontalAlignment(SwingConstants.TRAILING);
		lblOcupao.setForeground(Color.BLACK);
		lblOcupao.setFont(new Font("Arial", Font.PLAIN, 16));
		lblOcupao.setBackground(Color.ORANGE);
		lblOcupao.setBounds(399, 234, 91, 21);
		lblOcupao.setHorizontalAlignment(JLabel.LEFT);

		panelPessoaFisica.add(lblOcupao);

		entOcupacao = new JTextFieldPersonalizado();
		entOcupacao.setForeground(Color.BLACK);
		entOcupacao.setFont(new Font("Arial", Font.BOLD, 20));
		entOcupacao.setColumns(10);
		entOcupacao.setBounds(486, 229, 211, 28);
		panelPessoaFisica.add(entOcupacao);

		JButton btnPesquisarCPF = new JButton("Pesquisar");
		btnPesquisarCPF.setFont(new Font("Arial", Font.BOLD, 16));
		btnPesquisarCPF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String cpf = entCpf.getText().toString();
				pesquisarCpf(cpf);

				
			}
		});
		btnPesquisarCPF.setBounds(550, 113, 147, 34);
		panelPessoaFisica.add(btnPesquisarCPF);

		JLabel lblUf = new JLabel("UF:");
		lblUf.setHorizontalAlignment(SwingConstants.TRAILING);
		lblUf.setForeground(Color.BLACK);
		lblUf.setFont(new Font("Arial", Font.PLAIN, 16));
		lblUf.setBackground(Color.ORANGE);
		lblUf.setBounds(430, 116, 35, 29);
		lblUf.setHorizontalAlignment(JLabel.LEFT);

		panelPessoaFisica.add(lblUf);

		JComboBox cBUF = new JComboBox();
		cBUF.setFont(new Font("Arial", Font.BOLD, 14));
		cBUF.addItem("MG");
		cBUF.addItem("SP");
		cBUF.addItem("GO");
		cBUF.addItem("MA");
		cBUF.setBounds(468, 114, 72, 35);
		cBUF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				uf = cBUF.getSelectedItem().toString();

			}
		});
		panelPessoaFisica.add(cBUF);

		JLabel lblNomeEmpresarial = new JLabel("Nome Empresarial:");
		lblNomeEmpresarial.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNomeEmpresarial.setForeground(Color.BLACK);
		lblNomeEmpresarial.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNomeEmpresarial.setBackground(Color.ORANGE);
		lblNomeEmpresarial.setBounds(21, 227, 153, 34);
		lblNomeEmpresarial.setHorizontalAlignment(JLabel.LEFT);
		panelPessoaFisica.add(lblNomeEmpresarial);

		entNomeEmpresarial = new JTextFieldPersonalizado();
		entNomeEmpresarial.setForeground(Color.BLACK);
		entNomeEmpresarial.setFont(new Font("Arial", Font.BOLD, 20));
		entNomeEmpresarial.setColumns(10);
		entNomeEmpresarial.setBounds(171, 229, 215, 28);
		panelPessoaFisica.add(entNomeEmpresarial);

		JLabel lblPorte = new JLabel("Porte:");
		lblPorte.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPorte.setForeground(Color.BLACK);
		lblPorte.setFont(new Font("Arial", Font.PLAIN, 16));
		lblPorte.setBackground(Color.ORANGE);
		lblPorte.setBounds(21, 272, 60, 28);
		lblPorte.setHorizontalAlignment(JLabel.LEFT);

		panelPessoaFisica.add(lblPorte);

		entPorte = new JTextFieldPersonalizado();
		entPorte.setForeground(Color.BLACK);
		entPorte.setFont(new Font("Arial", Font.BOLD, 20));
		entPorte.setToolTipText("Data de Nascimento, somente números");
		entPorte.setColumns(10);
		entPorte.setBounds(74, 271, 623, 28);
		panelPessoaFisica.add(entPorte);

		JLabel lblCnae = new JLabel("Atividades:");
		lblCnae.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCnae.setForeground(Color.BLACK);
		lblCnae.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCnae.setBackground(Color.ORANGE);
		lblCnae.setBounds(21, 324, 91, 21);
		lblCnae.setHorizontalAlignment(JLabel.LEFT);

		panelPessoaFisica.add(lblCnae);

		entCnae = new JTextFieldPersonalizado();
		entCnae.setForeground(Color.BLACK);
		entCnae.setFont(new Font("Arial", Font.BOLD, 20));
		entCnae.setColumns(10);
		entCnae.setBounds(110, 322, 587, 61);
		panelPessoaFisica.add(entCnae);
		cBPessoa.setBounds(364, 33, 126, 35);
		painelDadosIniciais.add(cBPessoa);
		cBPessoa.addItem("Física");
		cBPessoa.addItem("Jurídica");

		JLabel lblPessoa = new JLabel("Pessoa:");
		lblPessoa.setBounds(290, 32, 64, 33);
		painelDadosIniciais.add(lblPessoa);
		lblPessoa.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPessoa.setForeground(Color.BLACK);
		lblPessoa.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblPessoa.setBackground(Color.ORANGE);

		JLabel lblCadastro_2 = new JLabel(" ----- Cadastro / Dados de Pessoa");
		lblCadastro_2.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCadastro_2.setForeground(Color.BLACK);
		lblCadastro_2.setFont(new Font("Arial", Font.BOLD, 14));
		lblCadastro_2.setBackground(Color.ORANGE);
		lblCadastro_2.setHorizontalAlignment(JLabel.LEFT);

		lblCadastro_2.setBounds(0, 0, 250, 33);

		painelDadosIniciais.add(lblCadastro_2);

		cBPessoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (cBPessoa.getSelectedItem() == "Física") {
					CardLayout cardLayout = (CardLayout) panelDinamico.getLayout();
					cardLayout.show(panelDinamico, "PessoaFisica");

				}
				if (cBPessoa.getSelectedItem() == "Jurídica") {
					CardLayout cardLayout = (CardLayout) panelDinamico.getLayout();
					cardLayout.show(panelDinamico, "PessoaJuridica");

				}

			}
		});

		// configura widgets no painel Empresa
		entLogradouro = new JTextFieldPersonalizado();
		entLogradouro.setForeground(Color.BLACK);
		entLogradouro.setColumns(10);
		entLogradouro.setBounds(108, 291, 220, 33);
		painelEmpresa.add(entLogradouro);

		JLabel lblLogradouro = new JLabel("Rua:");
		lblLogradouro.setHorizontalAlignment(SwingConstants.TRAILING);
		lblLogradouro.setForeground(Color.BLACK);
		lblLogradouro.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblLogradouro.setBackground(Color.ORANGE);
		lblLogradouro.setBounds(23, 289, 75, 33);
		painelEmpresa.add(lblLogradouro);

		JLabel lblN = new JLabel("N.\u00BA:");
		lblN.setHorizontalAlignment(SwingConstants.TRAILING);
		lblN.setForeground(Color.BLACK);
		lblN.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblN.setBackground(Color.ORANGE);
		lblN.setBounds(33, 333, 64, 33);
		painelEmpresa.add(lblN);

		entNumEndereco = new JTextFieldPersonalizado();
		entNumEndereco.setForeground(Color.BLACK);
		entNumEndereco.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {

				String caracteres = "0987654321";// lista de caracters que não devem ser aceitos
				if (!caracteres.contains(evt.getKeyChar() + "")) {
					evt.consume();// aciona esse propriedade para eliminar a ação do evento
				}
				if (entNumEndereco.getText().length() >= 6) {
					// if para saber se precisa verificar também o tamanho da string do campo
					// maior ou igual ao tamanho máximo, cancela e nao deixa inserir mais
					evt.consume();
					entNumEndereco.setText(entNumEndereco.getText().substring(0, 6));

				}
			}
		});
		entNumEndereco.setColumns(10);
		entNumEndereco.setBounds(108, 335, 220, 33);
		painelEmpresa.add(entNumEndereco);

		JLabel lblBairro = new JLabel("Bairro:");
		lblBairro.setHorizontalAlignment(SwingConstants.TRAILING);
		lblBairro.setForeground(Color.BLACK);
		lblBairro.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblBairro.setBackground(Color.ORANGE);
		lblBairro.setBounds(34, 377, 64, 33);
		painelEmpresa.add(lblBairro);

		entBairro = new JTextFieldPersonalizado();
		entBairro.setForeground(Color.BLACK);
		entBairro.setColumns(10);
		entBairro.setBounds(108, 379, 220, 33);
		painelEmpresa.add(entBairro);

		JLabel lblCidade = new JLabel("Cidade:");
		lblCidade.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCidade.setForeground(Color.BLACK);
		lblCidade.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblCidade.setBackground(Color.ORANGE);
		lblCidade.setBounds(338, 333, 64, 33);
		painelEmpresa.add(lblCidade);

		JLabel lblBairro_1_1 = new JLabel("Estado:");
		lblBairro_1_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblBairro_1_1.setForeground(Color.BLACK);
		lblBairro_1_1.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblBairro_1_1.setBackground(Color.ORANGE);
		lblBairro_1_1.setBounds(338, 377, 64, 33);
		painelEmpresa.add(lblBairro_1_1);

		JLabel lblBairro_1_2 = new JLabel("CEP:");
		lblBairro_1_2.setHorizontalAlignment(SwingConstants.TRAILING);
		lblBairro_1_2.setForeground(Color.BLACK);
		lblBairro_1_2.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblBairro_1_2.setBackground(Color.ORANGE);
		lblBairro_1_2.setBounds(338, 289, 64, 33);
		painelEmpresa.add(lblBairro_1_2);

		entCep = new JTextFieldPersonalizado();
		entCep.setForeground(Color.BLACK);
		entCep.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {

				String caracteres = "0987654321";// lista de caracters que não devem ser aceitos
				if (!caracteres.contains(evt.getKeyChar() + "")) {
					evt.consume();// aciona esse propriedade para eliminar a ação do evento
				}
				if (entCep.getText().length() >= 8) {
					// if para saber se precisa verificar também o tamanho da string do campo
					// maior ou igual ao tamanho máximo, cancela e nao deixa inserir mais
					evt.consume();
					entCep.setText(entCep.getText().substring(0, 8));

				}
			}
		});
		entCep.setColumns(10);
		entCep.setBounds(412, 291, 220, 33);
		painelEmpresa.add(entCep);

		JButton btnLocalizar = new JButton("Localizar por Cep");
		btnLocalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					int cep = Integer.parseInt(entCep.getText().toString());
					if (entCep.getText().toString().length() != 8) {
						JOptionPane.showMessageDialog(isto, "Cep Invalido!");

					} else {
						BuscarCep busca = new BuscarCep(cep);
						Endereco endereco = busca.buscar();
						entLogradouro.setText(endereco.getLogradouro());
						entBairro.setText(endereco.getBairro());
						entCidade.setText(endereco.getCidade());
						entEstado.setText(endereco.getUf());

					}

				} catch (Exception e1) {

				}

			}
		});
		btnLocalizar.setToolTipText("Buscar Endereço por Cep");
		// btnLocalizar.setIcon(new
		// ImageIcon(TelaNovoCliente.class.getResource("/imagens/procurar.png")));
		btnLocalizar.setBounds(642, 291, 161, 23);
		painelEmpresa.add(btnLocalizar);

		entCidade = new JTextFieldPersonalizado();
		entCidade.setForeground(Color.BLACK);
		entCidade.setColumns(10);
		entCidade.setBounds(412, 335, 220, 33);
		painelEmpresa.add(entCidade);

		entEstado = new JTextFieldPersonalizado();
		entEstado.setForeground(Color.BLACK);
		entEstado.setColumns(10);
		entEstado.setBounds(412, 379, 220, 33);
		painelEmpresa.add(entEstado);

		JLabel lblIe = new JLabel("IE:");
		lblIe.setHorizontalAlignment(SwingConstants.TRAILING);
		lblIe.setForeground(Color.BLACK);
		lblIe.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblIe.setBackground(Color.ORANGE);
		lblIe.setBounds(129, 77, 64, 33);
		painelEmpresa.add(lblIe);

		entIE = new JTextFieldPersonalizado();
		entIE.setForeground(Color.BLACK);
		entIE.setColumns(10);
		entIE.setBounds(203, 79, 220, 33);
		painelEmpresa.add(entIE);

		JLabel lblStatus_1 = new JLabel("Status:");
		lblStatus_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblStatus_1.setForeground(Color.BLACK);
		lblStatus_1.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblStatus_1.setBackground(Color.ORANGE);
		lblStatus_1.setBounds(129, 121, 64, 33);
		painelEmpresa.add(lblStatus_1);

		entStatusIE = new JTextFieldPersonalizado();
		entStatusIE.setForeground(Color.BLACK);
		entStatusIE.setColumns(10);
		entStatusIE.setBounds(203, 123, 220, 33);
		painelEmpresa.add(entStatusIE);

		JLabel lblLogradouro_1 = new JLabel("Logradouro:");
		lblLogradouro_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblLogradouro_1.setForeground(Color.BLACK);
		lblLogradouro_1.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblLogradouro_1.setBackground(Color.ORANGE);
		lblLogradouro_1.setBounds(23, 210, 110, 33);
		painelEmpresa.add(lblLogradouro_1);

		JLabel lblCadastro_2_1 = new JLabel(" ----- Cadastro / Dados da Empresa");
		lblCadastro_2_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCadastro_2_1.setForeground(Color.BLACK);
		lblCadastro_2_1.setFont(new Font("Arial", Font.BOLD, 14));
		lblCadastro_2_1.setBackground(Color.ORANGE);
		lblCadastro_2_1.setBounds(0, 0, 250, 33);
		lblCadastro_2_1.setHorizontalAlignment(JLabel.LEFT);

		painelEmpresa.add(lblCadastro_2_1);

		cBUFIE = new JComboBox();
		cBUFIE.setFont(new Font("Arial", Font.BOLD, 14));
		cBUFIE.setBounds(471, 78, 72, 35);
		cBUFIE.addItem("MG");
		cBUFIE.addItem("SP");
		cBUFIE.addItem("MT");
		cBUFIE.addItem("SC");
		cBUFIE.addItem("GO");

		painelEmpresa.add(cBUFIE);

		JButton btnPesquisarIE = new JButton("Pesquisar");
		btnPesquisarIE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String ie = entIE.getText().replaceAll("[^0-9]+", "");
				String uf = cBUFIE.getSelectedItem().toString();
				pesquisarIE(ie, uf);
			}
		});
		btnPesquisarIE.setFont(new Font("Arial", Font.BOLD, 16));
		btnPesquisarIE.setBounds(553, 77, 147, 34);
		painelEmpresa.add(btnPesquisarIE);

		JLabel lblUf_1 = new JLabel("UF:");
		lblUf_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblUf_1.setForeground(Color.BLACK);
		lblUf_1.setFont(new Font("Arial", Font.PLAIN, 16));
		lblUf_1.setBackground(Color.ORANGE);
		lblUf_1.setBounds(433, 80, 35, 29);
		painelEmpresa.add(lblUf_1);
		
		JButton btnLocalizarPorUfcidaderua = new JButton("Localizar por UF/Cidade/Rua");
		btnLocalizarPorUfcidaderua.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Endereco end = new Endereco();
				end.setLogradouro(entLogradouro.getText());
			    end.setUf(entEstado.getText());
			    end.setCidade(entCidade.getText());
			    
				buscarCepPorInfo(end);
			}
		});
		btnLocalizarPorUfcidaderua.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnLocalizarPorUfcidaderua.setToolTipText("Buscar CEP por informação de UF/Cidade/Rua");
		btnLocalizarPorUfcidaderua.setBounds(642, 325, 161, 21);
		painelEmpresa.add(btnLocalizarPorUfcidaderua);
		painelDadosBancarios.setLayout(new MigLayout("", "[124px][4px][220px][21px][89px][232px][89px]", "[33px][138px][53px][37px][35px][35px][35px][35px][]"));

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
		table_cb.setRowHeight(30);
		
		modelo_cb.setNumRows(0);
		painel_table_cb.setLayout(new BorderLayout(0, 0));
		JScrollPane scrollPaneCB = new JScrollPane(table_cb);
		scrollPaneCB.setAutoscrolls(true);
		scrollPaneCB.getViewport().setBackground(Color.white);
		scrollPaneCB.setBackground(new Color(255, 255, 255));
		painel_table_cb.add(scrollPaneCB);

		painelDadosBancarios.add(painel_table_cb, "cell 0 1 7 1,grow");

		JButton btnExcluirCB = new JButton("Excluir");
		btnExcluirCB.setBackground(Color.RED);
		btnExcluirCB.setForeground(Color.WHITE);
		btnExcluirCB.setFont(new Font("SansSerif", Font.BOLD, 12));
		btnExcluirCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int indiceDaLinha = table_cb.getSelectedRow();

				if (flag_tipo_tela == 0 || flag_tipo_tela == 6) {

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
		painelDadosBancarios.add(btnExcluirCB, "cell 6 2,growx,aligny top");

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
				btnAdicionarCB.setFont(new Font("SansSerif", Font.BOLD, 14));
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
				painelDadosBancarios.add(btnAdicionarCB, "cell 2 8 3 1,alignx right,aligny center");

		uf = cBUF.getSelectedItem().toString();
		getContentPane().setLayout(null);

		// configura widgets no painel finalizar

		// getContentPane().add(painelPrincipal);
		painelCentral.add(painelPrincipal);

		if (flag_tipo_tela == 0 || flag_tipo_tela == 6) // 0 atualuzar //1 novo cliente
		{
			btnFinalizarCadastro.setVisible(false);

			btnFinalizarCadastro.setEnabled(false);
			lblCodigo.setText(Integer.toString(cliente.getId()));

			if (cliente.getTipo_pessoa() == 1) {
				// pesoa juridica
				cBPessoa.setSelectedItem("Jurídica");
				cBPessoa.setEnabled(false);

				entCNPJ.setText(cliente.getCnpj());
				entCNPJ.setEditable(false);
				btnVerificarCNPJ.setEnabled(false);
				entRazaoSocial.setText(cliente.getRazao_social());
				entAtividadesPri.setText(cliente.getAt_primaria());
				entAtividadeSec.setText(cliente.getAt_secundaria());
				entStatus.setText(cliente.getStatus());
				entNomeFantasia.setText(cliente.getNome_fantaia());
				entDescricao.setText(cliente.getDescricao());

			} else {
				cBPessoa.setSelectedItem("Física");
				cBPessoa.setEnabled(false);
				entCpf.setText(cliente.getCpf());
				entCpf.setEditable(true);
				cBUF.setSelectedItem(cliente.getUf());
				cBUF.setEnabled(false);
				btnPesquisarCPF.setEnabled(false);

				entOcupacao.setText(cliente.getOcupacao());
				entNome.setText(cliente.getNome());
				entSobrenome.setText(cliente.getSobrenome());
				entNascimento.setText(cliente.getNascimento());
				entNomeEmpresarial.setText(cliente.getNome_empresarial());
				entPorte.setText(cliente.getPorte());
				entCnae.setText(cliente.getAtividade());
				entRg.setText(cliente.getRg());

			}
			entIE.setText(cliente.getIe());
			entStatusIE.setText(cliente.getStatus_ie());

			entLogradouro.setText(cliente.getRua());
			entNumEndereco.setText(cliente.getNumero());
			entBairro.setText(cliente.getBairro());
			entCidade.setText(cliente.getCidade());
			entEstado.setText(cliente.getUf());
			entCep.setText(cliente.getCep());

			cBTipoIdentificacao.setSelectedItem(cliente.getTipo_identificacao());
			entIdentificacaoSiare.setText(cliente.getIdentificacao_sefaz());
			entCpfResponsavel.setText(cliente.getCpf_responsavel());
			entSenhaSiare.setText(cliente.getSenha());

			entApelido.setText(cliente.getApelido());

			// dados contatos

			ArrayList<Contato> contatos = new ArrayList<>();
			GerenciarBancoClientes gerenciar = new GerenciarBancoClientes();
			contatos = gerenciar.getContatos(cliente.getId());

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
			contas_bancaria = gerenciar.getContas(cliente.getId());

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

		else {

		}

		if (flag_tipo_tela == 5) {

			cBPessoa.setSelectedItem("Jurídica");
			cBPessoa.setEnabled(false);
			CardLayout cardLayout = (CardLayout) panelDinamico.getLayout();
			cardLayout.show(panelDinamico, "PessoaJuridica");

		}

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
		if (flag_tipo_tela == 0) {
			// travar para nao alterar os campos nomefantasia e nomeempresarial
			if (cliente_atualizar.getTipo_pessoa() == 0) {
				// pessoa fisica
				entNomeEmpresarial.setEditable(false);
			} else {
				entNomeFantasia.setEditable(false);
			}

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

	public void getDadosBancarios(CadastroCliente cadastro) {

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
		// CadastroCliente cliente : listaClientes.getClientes()
		for (ContaBancaria conta : contas) {
			System.out.println("CPF: " + conta.getCpf_titular());
			System.out.println("Banco: " + conta.getBanco());
			System.out.println("Codigo: " + conta.getCodigo());
			System.out.println("Agencia: " + conta.getAgencia());
			System.out.println("Conta: " + conta.getConta());

		}

		cadastro.setContas(contas);

	}

	public boolean atualizar(int flag_armazem) {
		boolean permitir_cadastro = false;

		if (flag_armazem == 5 || flag_armazem == 6)
			cliente_atualizar.setArmazem(1);
		else
			cliente_atualizar.setArmazem(0);

		cliente_atualizar.setTransportador(0);

		if (cBPessoa.getSelectedItem().equals("Física")) {

			permitir_cadastro = getDadosPessoaFisica(cliente_atualizar);
			System.out.println("Dados de pessoa fisica foram adicionados para atualizar? - " + permitir_cadastro);

		} else // dados pessoa juridica
		{
			permitir_cadastro = getDadosPessoaJuridica(cliente_atualizar);
			System.out.println("Dados de pessoa juridica foram adicionados para atualizar? - " + permitir_cadastro);

		} // fim de dados pessoa juridica

		if (permitir_cadastro) {
			getDadosSefaz(cliente_atualizar);
			getDadosContato(cliente_atualizar);
			getDadosEmpresa(cliente_atualizar);
			getDadosBancarios(cliente_atualizar);

		}
		// dados de apelidoa e finalizar contrato

		permitir_cadastro = getDadosFinais(cliente_atualizar);
		System.out.println("Dados finais foram adicionados para atualizar? - " + permitir_cadastro);

		GerenciarBancoClientes atualizar = new GerenciarBancoClientes();

		if (permitir_cadastro) {
			// excluir contatos que tiver para excluir
			for (Integer id_contato : contatos_excluir) {
				if (atualizar.deleteContato(id_contato, cliente_atualizar.getId()) == false) {
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
					if (atualizar.deleteConta(id_conta, cliente_atualizar.getId()) == false) {
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
				if (cliente_atualizar.getContas().size() > 0) {
					GerenciarBancoClientes.RegistroAdicionarContaBancaria adicionar_contas = null;
					adicionar_contas = atualizar.adicionarContaBancaria(cliente_atualizar.getContas(),
							cliente_atualizar.getId());
					if (adicionar_contas.isResposta() == true && adicionar_contas.ids_contas.size() > 0) {
						permitir_cadastro = true;
					} else {
						permitir_cadastro = false;
					}
				}
			}

			// adicionar contatos
			if (permitir_cadastro) {
				if (cliente_atualizar.getContatos().size() > 0) {
					GerenciarBancoClientes.RegistroAdicionarContato adicionar_contatos = null;
					adicionar_contatos = atualizar.adicionarContato(cliente_atualizar.getContatos(),
							cliente_atualizar.getId());
					if (adicionar_contatos.isResposta() == true && adicionar_contatos.ids_contatos.size() > 0) {
						permitir_cadastro = true;
					} else {
						permitir_cadastro = false;
					}
				}
			}

			if (permitir_cadastro) {
				System.out.println("tentando atualizar cliente");
				boolean atualizou = atualizar.atualizarCliente(cliente_atualizar);
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

	}

	public boolean getDadosFinais(CadastroCliente cadastro) {
		boolean retorno;

		String apelido = entApelido.getText().toString();
		if (apelido == null || apelido.equals("") || apelido.equals(" ")) {
			retorno = false;
			JOptionPane.showMessageDialog(isto, "Informe um Alias para o novo Cliente");

		} else {
			String id_cliente = lblCodigo.getText();
			int id = Integer.parseInt(id_cliente);
			if (id > 0)
				cadastro.setId(id);
			cadastro.setApelido(apelido);

			retorno = true;

		}
		return retorno;
	}

	public boolean getDadosPessoaFisica(CadastroCliente cadastro) {

		boolean retorno = false;

		String cpf = entCpf.getText().toString();
		
		if(cpf.length() == 0) {
			//cpf em branco, permite o cadastro
			
		
			cadastro.setTipo_pessoa(0);

			String nome_empresarial = entNomeEmpresarial.getText().toString();
			String nome = entNome.getText().toString();
			String sobrenome = entSobrenome.getText().toString();
			String data_nascimento = entNascimento.getText().toString();
			String rg = entRg.getText().toString();
			String ocupacao = entOcupacao.getText().toString();
			String porte = entPorte.getText().toString();
			String cnae = entCnae.getText().toString();

			cadastro.setNome_empresarial(nome_empresarial);

			cadastro.setNome(nome);
			cadastro.setSobrenome(sobrenome);

			cadastro.setNascimento(data_nascimento);

			cadastro.setRg(rg);

			cadastro.setOcupacao(ocupacao);

			cadastro.setPorte(porte);

			cadastro.setAtividade(cnae);
			retorno = true;
		}else {
		
		CPFValidator cpfValidator = new CPFValidator();
		List<ValidationMessage> erros = cpfValidator.invalidMessagesFor(cpf);
		if (erros.size() > 0) {
			JOptionPane.showMessageDialog(isto, "CPF Inválido!");
			retorno = false;
		} else {
			cpf = cpf.replace(".", "");
			cpf = cpf.replace("-", "");
			System.out.println(cpf);
			cadastro.setCpf(cpf);
			cadastro.setTipo_pessoa(0);

			String nome_empresarial = entNomeEmpresarial.getText().toString();
			String nome = entNome.getText().toString();
			String sobrenome = entSobrenome.getText().toString();
			String data_nascimento = entNascimento.getText().toString();
			String rg = entRg.getText().toString();
			String ocupacao = entOcupacao.getText().toString();
			String porte = entPorte.getText().toString();
			String cnae = entCnae.getText().toString();

			cadastro.setNome_empresarial(nome_empresarial);

			cadastro.setNome(nome);
			cadastro.setSobrenome(sobrenome);

			cadastro.setNascimento(data_nascimento);

			cadastro.setRg(rg);

			cadastro.setOcupacao(ocupacao);

			cadastro.setPorte(porte);

			cadastro.setAtividade(cnae);
			retorno = true;
		}
		}
		return retorno;
	}

	public boolean getDadosPessoaJuridica(CadastroCliente cadastro) {
		boolean retorno = false;
		String cnpj = entCNPJ.getText();
		cnpj = cnpj.replaceAll("[^0-9]+", "");
		ValidaCNPj valida = new ValidaCNPj();

		if (cnpj.length() != 14) {
			JOptionPane.showMessageDialog(isto, "CNPJ Invalido!");
			retorno = false;

		} else {
			if (valida.isCNPJ(cnpj)) {
				cadastro.setTipo_pessoa(1);

				cadastro.setCnpj(cnpj.replaceAll("[^0-9]", ""));
				String razao_social = entRazaoSocial.getText().toString();
				String nome_fantasia = entNomeFantasia.getText().toString();
				String status = entStatus.getText().toString();
				String descricao = entDescricao.getText().toString();
				String ati_pri = entAtividadesPri.getText().toString();
				String ati_sec = entAtividadeSec.getText().toString();

				cadastro.setRazao_social(razao_social);

				cadastro.setNome_fantaia(nome_fantasia);

				cadastro.setStatus(status);

				cadastro.setDescricao(descricao);

				cadastro.setAt_primaria(ati_pri);
				cadastro.setAt_secundaria(ati_sec);
				retorno = true;

			} else {
				JOptionPane.showMessageDialog(isto, "CNPJ Invalido!");
				retorno = false;

			}
		}
		return retorno;
	}

	public void getDadosSefaz(CadastroCliente cadastro) {
		// dados sefaz

		String tipo_id = cBTipoIdentificacao.getSelectedItem().toString();
		String id_siare = entIdentificacaoSiare.getText().toString();

		cadastro.setTipo_identificacao(tipo_id);
		cadastro.setIdentificacao_sefaz(id_siare);
		String cpf_siare = entCpfResponsavel.getText().toString();

		cadastro.setCpf_responsavel(cpf_siare);
		String senha = entSenhaSiare.getText().toString();

		cadastro.setSenha(senha);

	}

	public void getDadosContato(CadastroCliente cadastro) {
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
		// CadastroCliente cliente : listaClientes.getClientes()
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

	public void getDadosEmpresa(CadastroCliente cadastro) {
		// dados da empresa
		String ie = entIE.getText().toString().trim();

		cadastro.setIe(ie);
		String status_ie = entStatusIE.getText().toString();

		cadastro.setStatus_ie(status_ie);
		String rua = entLogradouro.getText().toString();

		cadastro.setRua(rua);

		String numero = entNumEndereco.getText().toString();

		cadastro.setNumero(numero);

		String cep = entCep.getText().toString();

		cadastro.setCep(cep);
		String bairro = entBairro.getText().toString();

		cadastro.setBairro(bairro);
		String cidade = entCidade.getText().toString();

		cadastro.setCidade(cidade);
		String uf = entEstado.getText().toString();

		cadastro.setUf(uf);

	}

	public boolean salvar(int flag_armazem) {

		boolean permitir_cadastro = false;
		boolean retorno = false;

		if (flag_armazem == 5 || flag_armazem == 6)
			cliente_cadastrar.setArmazem(1);
		else
			cliente_cadastrar.setArmazem(0);

		cliente_cadastrar.setTransportador(0);

		if (cBPessoa.getSelectedItem().equals("Física")) {

			permitir_cadastro = getDadosPessoaFisica(cliente_cadastrar);

		} else // dados pessoa juridica
		{
			permitir_cadastro = getDadosPessoaJuridica(cliente_cadastrar);

		} // fim de dados pessoa juridica

		if (permitir_cadastro) {
			getDadosSefaz(cliente_cadastrar);
			getDadosContato(cliente_cadastrar);
			getDadosEmpresa(cliente_cadastrar);
			getDadosBancarios(cliente_cadastrar);
		}
		// dados de apelido e finalizar contrato

		if (permitir_cadastro) {

			permitir_cadastro = getDadosFinais(cliente_cadastrar);

			if (permitir_cadastro) {
				retorno = false;
				if (permitir_cadastro) {
					GerenciarBancoClientes cadastrar = new GerenciarBancoClientes();
					boolean cadastrou = cadastrar.inserir(cliente_cadastrar);
					if (cadastrou) {
						JOptionPane.showMessageDialog(isto, "Cadastro Completo!");
						// isto.dispose();
						gerarPastas();
						retorno = true;

					} else {
						JOptionPane.showMessageDialog(isto, "Erro ao Cadastrar");
						retorno = false;

					}

				}
			}
		}
		return retorno;

	}

	public void gerarPastas() {
		ManipularTxt manipularArquivos = new ManipularTxt();
		String nome_pasta;

		if (cliente_cadastrar.getTipo_pessoa() == 0) {
			nome_pasta = cliente_cadastrar.getNome_empresarial().toUpperCase();
		} else {
			nome_pasta = cliente_cadastrar.getNome_fantaia().toUpperCase();
		}

		int ano_atual = new GetData().getAnoAtual();

		String ano_passado_pasta = Integer.toString(ano_atual - 1);
		String ano_atual_pasta = Integer.toString(ano_atual);
		String ano_que_vem_pasta = Integer.toString(ano_atual + 1);

		DadosGlobais dados = DadosGlobais.getInstance();
		configs_globais = dados.getConfigs_globais();

		String unidade_base_dados = configs_globais.getServidorUnidade();
		String sub_pasta = "E-Contract\\arquivos\\clientes";

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
				String caminho_completo_romaneios = unidade_base_dados + "\\" + sub_pasta + "\\" + nome_pasta.toUpperCase() + "\\"
						+ "ROMANEIOS";
				
				manipularArquivos.criarDiretorio(caminho_completo_romaneios);

		
	}

	public void gerarPastasAtualizar() {
		ManipularTxt manipularArquivos = new ManipularTxt();
		String nome_pasta;

		if (cliente_atualizar.getTipo_pessoa() == 0) {
			nome_pasta = cliente_atualizar.getNome_empresarial().toUpperCase();
		} else {
			nome_pasta = cliente_atualizar.getNome_fantaia().toUpperCase();
		}

		int ano_atual = new GetData().getAnoAtual();

		String ano_passado_pasta = Integer.toString(ano_atual - 1);
		String ano_atual_pasta = Integer.toString(ano_atual);
		String ano_que_vem_pasta = Integer.toString(ano_atual + 1);

		DadosGlobais dados = DadosGlobais.getInstance();
		configs_globais = dados.getConfigs_globais();

		String unidade_base_dados = configs_globais.getServidorUnidade();
		String sub_pasta = "E-Contract\\arquivos\\clientes";

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
				String caminho_completo_romaneios = unidade_base_dados + "\\" + sub_pasta + "\\" + nome_pasta.toUpperCase() + "\\"
						+ "ROMANEIOS";
				
				manipularArquivos.criarDiretorio(caminho_completo_romaneios);
	}

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

	public void setInformacoesNovoCliente(CadastroCliente vendedor_contrato, String uf_inscricao, ContaBancaria conta) {

		if (vendedor_contrato.getTipo_pessoa() == 0) {
			entCpf.setText(vendedor_contrato.getCpf());
			entIE.setText(vendedor_contrato.getIe());
			entNomeEmpresarial.setText(vendedor_contrato.getNome_empresarial());

			//pesquisarCpf(vendedor_contrato.getCpf());
		} else {
			CardLayout cardLayout = (CardLayout) panelDinamico.getLayout();
			cardLayout.show(panelDinamico, "PessoaJuridica");
			entCNPJ.setText(vendedor_contrato.getCnpj());
			entIE.setText(vendedor_contrato.getIe());
			entNomeFantasia.setText(vendedor_contrato.getNome_fantaia());

		//	pesquisarCNPJ(vendedor_contrato.getCnpj());
		}

		
		// pesquisar a ie
		//pesquisarIE(vendedor_contrato.getIe().replaceAll("[^0-9+]", ""), uf_inscricao);

		//dados de painel inicial
		entNome.setText(vendedor_contrato.getNome());
		entSobrenome.setText(vendedor_contrato.getSobrenome());
		
		//dados de endereço
		entLogradouro.setText(vendedor_contrato.getRua());
		entBairro.setText(vendedor_contrato.getBairro());
		entCidade.setText(vendedor_contrato.getCidade());
		entEstado.setText(vendedor_contrato.getUf());
		
		Endereco endereco = new Endereco();
		endereco.setLogradouro(vendedor_contrato.getRua());
		endereco.setBairro(vendedor_contrato.getBairro());
		endereco.setCidade(vendedor_contrato.getCidade());
		endereco.setUf(vendedor_contrato.getUf());
		
		buscarCepPorInfo(endereco);
		
		
		// adicionar uma conta conta bancaria

		entCpfTitular.setText(conta.getCpf_titular());
		entBanco.setText(conta.getBanco());
		entCodBanco.setText(conta.getCodigo());
		entAgencia.setText(conta.getAgencia());
		entConta.setText(conta.getConta());
		entNomeContaBancaria.setText(conta.getNome());

	}

	public void pesquisarCNPJ(String cnpj) {

		cnpj = cnpj.replaceAll("[^0-9]+", "");
		ValidaCNPj valida = new ValidaCNPj();

		if (cnpj.length() != 14) {
			JOptionPane.showMessageDialog(isto, "CNPJ Invalido!");

		} else {
			if (valida.isCNPJ(cnpj)) {

				String cnpj_formatado = cnpj;
				// 24.986679000106
				// 24.986.679/0001-06
				StringBuilder stringBuilder = new StringBuilder(cnpj_formatado);
				stringBuilder.insert(cnpj_formatado.length() - 12, '.');
				stringBuilder.insert(cnpj_formatado.length() - 8, '.');
				stringBuilder.insert(cnpj_formatado.length() - 4, '/');
				stringBuilder.insert(cnpj_formatado.length() + 1, '-');

				entCNPJ.setText(stringBuilder.toString());

				String entcnpj = cnpj;
				String result = null;
				try {
					GetHttp buscarCnpj = new GetHttp(entcnpj);

					result = buscarCnpj.captura();
				} catch (Exception e) {

				}
				if (result != null) {
					result = result.replaceAll("\"", "");
					result = result.replaceAll("\n", "&");

					System.out.println(result);

					TratarDados separar = new TratarDados(result);

					String nome = separar.tratar("name:", "&al");
					String nomeFantasia = separar.tratar("alias:", "&");

					if (!nome.equals("null"))
						entRazaoSocial.setText(nome);

					if (!nomeFantasia.equals("null"))
						entNomeFantasia.setText(nomeFantasia);
					else
						entNomeFantasia.setText(nome);

					// tratando dados de enderecos
					String bairro = separar.tratar("neighborhood:", "&");
					entBairro.setText(bairro);
					String cep = separar.tratar("zip:", "&");
					entCep.setText(cep);
					String estado = separar.tratar("state:", "&");
					entEstado.setText(estado);
					String cidade = separar.tratar("city:", "&");
					entCidade.setText(cidade);
					String numero = separar.tratar("number:", "&details");
					entNumEndereco.setText(numero);
					String endereco = separar.tratar("street:", "&");
					entLogradouro.setText(endereco);

					// tratando dados de atividades primaria
					String at_pri = separar.tratar("primary_activity:", "secondary");
					// System.out.println(at_pri);
					TratarDados atividades = new TratarDados(at_pri);
					at_pri = atividades.tratar("description:", "}&");
					entAtividadesPri.setText("Primária: " + at_pri);

					String at_sec = separar.tratar("secondary_activities:", "membership");
					System.out.println(at_sec);
					TratarDados sec_atividades = new TratarDados(at_sec);
					at_sec = sec_atividades.tratar("description:", "}&");
					entAtividadeSec.setText("Secundária: " + at_sec);

					// tratando dados status
					String status = separar.tratar("federal_entity:", "}");
					// System.out.println(status);
					TratarDados status_ = new TratarDados(status);
					status = status_.tratar("status:", "&status_date");
					entStatus.setText(status);

					// tratando dados descricao
					String descricao = separar.tratar("legal_nature:", "&pri");
					System.out.println(descricao);
					TratarDados descricao_ = new TratarDados(descricao);
					descricao = descricao_.tratar("description:", "}");
					entDescricao.setText(descricao);

					// tratar dados de inscricao estadual
					String inscricao_estadual = separar.tratar("registrations:", "]");
					System.out.println(inscricao_estadual);
					TratarDados inscricao_estadual_ = new TratarDados(inscricao_estadual);
					inscricao_estadual = inscricao_estadual_.tratar("number:", "&");
					entIE.setText(inscricao_estadual);

					String statusIE = inscricao_estadual_.tratar("enabled:", "}");
					if (statusIE.equals("true"))
						entStatusIE.setText("Ativo");
					else
						entStatusIE.setText("Não Habilitado");

				} else {
					System.out.println("Erro ao realizar a consulta \n Tente Novamente!");

				}

			} else
				JOptionPane.showMessageDialog(isto, "CNPJ Invalido!");
		}
	}

	public void pesquisarCpf( String _cpf) {
		 final String icpf = _cpf;
		
		TelaEmEspera esperar = new TelaEmEspera(1);

		
		CPFValidator cpfValidator = new CPFValidator();
		List<ValidationMessage> erros = cpfValidator.invalidMessagesFor(icpf);
		if (erros.size() > 0)
			JOptionPane.showMessageDialog(isto, "CPF Inválido!");

		else {

			new Thread() {

				@Override
				public void run() {
					esperar.setVisible(true);
				}

			}.start();

		
			new Thread() {

				String cpf = icpf;
				
				@Override
				public void run() {
					// JOptionPane.showMessageDialog(null, "CPF Válido!");
					cpf = cpf.replace(".", "");
					cpf = cpf.replace("-", "");
					System.out.println(uf);
					System.out.println(cpf);

					GetSintegra sintegra = new GetSintegra(cpf, uf, 0);
					esperar.setMsg2("Aguarde, Pesquisando...");

					String result = sintegra.captura();
					System.out.println(result);

					// result = result.replaceAll ("{", "");
					result = result.replaceAll("\"", "");
					result = result.replaceAll(",", "&");
					System.out.println(result);

					TratarDados separar = new TratarDados(result);

					String code = separar.tratar("code:", "&s");
					System.out.println("Codigo: " + code);
					if (code.equals("0")) {
						entCpfResponsavel.setText(cpf);
						String nome_empresarial = separar.tratar("nome_empresarial:", "&");
						System.out.println(nome_empresarial);
						entNomeEmpresarial.setText(nome_empresarial);

						String ie = separar.tratar("inscricao_estadual:", "&");
						System.out.println(ie);
						entIE.setText(ie);

						String status = separar.tratar("situacao_ie:", "&");
						System.out.println(status);
						entStatusIE.setText(status);

						String nome_fantasia = separar.tratar("nome_fantasia:", "&");
						System.out.println(nome_fantasia);
						entNomeFantasia.setText(nome_fantasia);

						String porte = separar.tratar("porte_empresa:", "&");
						System.out.println(porte);
						entPorte.setText(porte);

						String cnae = separar.tratar("text:", "&");
						System.out.println(cnae);
						entCnae.setText(cnae);

						String ocupacao = separar.tratar("tipo_inscricao:", "&");
						System.out.println(ocupacao);
						entOcupacao.setText(ocupacao);

						// tratando dados de enderecos
						String bairro = separar.tratar("bairro:", "&");
						entBairro.setText(bairro);
						String cep = separar.tratar("cep:", "&");
						entCep.setText(cep);
						// String estado = separar.tratar("state:", "&");
						entEstado.setText(uf);
						String cidade = separar.tratar("municipio:", "&log");
						entCidade.setText(cidade);
						String numero = separar.tratar("numero:", "&");
						entNumEndereco.setText(numero);
						String endereco = separar.tratar("logradouro:", "&");
						entLogradouro.setText(endereco);
					} else {
						//JOptionPane.showMessageDialog(null, "Erro ao consultar dados de CPF no Sintegra");
						esperar.setMsg2("Erro na consulta!\nInstabilidade no site do Sintegra AWS");
                        try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					esperar.fechar();
					
					
					
				}

			}.start();
			

		}
	}

	public void pesquisarIE(String ie_, String uf) {
		TelaEmEspera esperar = new TelaEmEspera(1);

		new Thread() {

			@Override
			public void run() {
				esperar.setVisible(true);
				
			}

		}.start();
		

		new Thread() {
			
			@Override
			public void run() {
				GetSintegra sintegra = new GetSintegra(ie_, uf, 1);
				String result = sintegra.captura();
				System.out.println(result);

				result = result.replaceAll("\"", "");
				result = result.replaceAll(",", "&");
				System.out.println("Resultado da pesquisa de ie: " + result);

				TratarDados separar = new TratarDados(result);

				String code = separar.tratar("code:", "&s");
				System.out.println("Codigo: " + code);
				if (code.equals("0")) {
					entCpfResponsavel.setText(ie_);
					String nome_empresarial = separar.tratar("nome_empresarial:", "&");
					System.out.println(nome_empresarial);
					entNomeEmpresarial.setText(nome_empresarial);

					String ie = separar.tratar("inscricao_estadual:", "&");
					System.out.println(ie);
					entIE.setText(ie);

					String status = separar.tratar("situacao_ie:", "&");
					System.out.println(status);
					entStatusIE.setText(status);

					String nome_fantasia = separar.tratar("nome_fantasia:", "&");
					System.out.println(nome_fantasia);
					entNomeFantasia.setText(nome_fantasia);

					String porte = separar.tratar("porte_empresa:", "&");
					System.out.println(porte);
					entPorte.setText(porte);

					String cnae = separar.tratar("text:", "&");
					System.out.println(cnae);
					entCnae.setText(cnae);

					String ocupacao = separar.tratar("tipo_inscricao:", "&");
					System.out.println(ocupacao);
					entOcupacao.setText(ocupacao);

					// tratando dados de enderecos
					String bairro = separar.tratar("bairro:", "&");
					entBairro.setText(bairro);
					String cep = separar.tratar("cep:", "&");
					entCep.setText(cep);
					// String estado = separar.tratar("state:", "&");
					entEstado.setText(uf);
					String cidade = separar.tratar("municipio:", "&log");
					entCidade.setText(cidade);
					String numero = separar.tratar("numero:", "&");
					entNumEndereco.setText(numero);
					String endereco = separar.tratar("logradouro:", "&");
					entLogradouro.setText(endereco);
				} else {
					//JOptionPane.showMessageDialog(null, "Erro ao consultar dados de CPF no Sintegra");
					esperar.setMsg2("Erro na consulta!\nInstabilidade no site do Sintegra AWS");
                    try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				esperar.fechar();
				
			}

		}.start();
		
	

	}
	
	public void buscarCepPorInfo(Endereco end) {
		try {
			
				BuscarCep busca = new BuscarCep(123);
				
			    
			    end = busca.buscarCep(end);
			    
			    entCep.setText(end.getCep());

			

		} catch (Exception e1) {
             JOptionPane.showMessageDialog(isto, "Erro na consulta de cep");
		}

	}
}
