package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import main.java.cadastros.CadastroAviso;
import main.java.cadastros.CadastroBaseArquivos;
import main.java.cadastros.CadastroBaseDados;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroContrato.CadastroPagamento;
import main.java.cadastros.CadastroContrato.CadastroPagamentoContratual;
import main.java.cadastros.CadastroContrato.Recebimento;
import main.java.cadastros.CadastroDocumento;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroModelo;
import main.java.cadastros.CadastroNFe;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroRomaneio;
import main.java.cadastros.CadastroSafra;
import main.java.cadastros.ContaBancaria;
import main.java.cadastros.Contato;
import main.java.cadastros.DadosCarregamento;
import main.java.cadastros.DadosContratos;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.classesExtras.Endereco;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoDocumento;
import main.java.conexaoBanco.GerenciarBancoNotasFiscais;
import main.java.conexaoBanco.GerenciarBancoPadrao;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoRomaneios;
import main.java.conexaoBanco.GerenciarBancoSafras;
import main.java.conexoes.TesteConexao;
import main.java.graficos.GraficoLinha;
import main.java.graficos.JPanelGrafico;
import main.java.graficos.JPanelGraficoCarregamento;
import main.java.gui.TelaCadastroCliente;
import main.java.gui.TelaMain;
import main.java.gui.TelaRomaneios;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.manipular.ConverterPdf;
import main.java.manipular.CopiarArquivo;
import main.java.manipular.GetDadosGlobais;
import main.java.manipular.ManipularNotasFiscais;
import main.java.manipular.ManipularRomaneios;
import main.java.manipular.ManipularTxt;
import main.java.outros.BaixarNotasFiscais;
import main.java.outros.BuscarCep;
import main.java.outros.DadosGlobais;
import main.java.outros.GetData;
import main.java.outros.GetHttp;
import main.java.outros.GetSintegra;
import main.java.outros.JPanelBackground;
import main.java.outros.JPanelTransparent;
import main.java.outros.JTextFieldPersonalizado;
import main.java.outros.MyFileVisitor;
import main.java.outros.ReproduzirAudio;
import main.java.outros.TratarDados;
import main.java.relatoria.RelatorioContratoComprador;
import main.java.relatoria.RelatorioContratoRecebimentoSimplificado;
import main.java.relatoria.RelatorioContratos;
import main.java.tratamento_proprio.Log;
import main.java.views_personalizadas.TelaEmEspera;
import main.java.views_personalizadas.TelaNotificacao;
import main.java.views_personalizadas.TelaNotificacaoSuperior;
import main.java.views_personalizadas.TelaNotificacaoSuperiorModoBusca;
import outros.ValidaCNPj;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroNuvem;
import main.java.cadastros.CadastroZapMessenger;
import main.java.outros.DadosGlobais;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroGrupo;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoGrupos;
import main.java.cadastros.CadastroProduto;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroSafra;
import main.java.classesExtras.CBProdutoPersonalizado;
import main.java.classesExtras.CBProdutoRenderPersonalizado;
import main.java.classesExtras.ComboBoxPersonalizado;
import main.java.classesExtras.ComboBoxRenderPersonalizado;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoSafras;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.Font;
import java.awt.Frame;
import net.miginfocom.swing.MigLayout;

public class TelaCliente extends JFrame {

	private Window telaPai;
	private JPanel contentPane;
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	DefaultTableModel modelo_grupos = new DefaultTableModel() {
		public boolean isCellEditable(int linha, int coluna) {
			return false;
		}
	};
	private ClienteTableModel modelo_cliente = new ClienteTableModel();
	private TableRowSorter<ClienteTableModel> sorter;
	private static ArrayList<CadastroCliente> clientes_pesquisados = new ArrayList<>();
	private static ArrayList<CadastroCliente> clientes_disponiveis = new ArrayList<>();
	private static ArrayList<CadastroGrupo> lista_grupos = new ArrayList<>();
	private CadastroCliente clienteSelecionado;
	private JTextField entNome;

	private TelaCliente isto;
	private JTable table, table_grupos;
	private JTextField entApelido;
	private JTextField entCpfCnpj;
	private JTextField entIe;
	private JTextField entNomeEmpresarialFantasia;

	public void pesquisar() {

		modelo_cliente.onRemoveAll();
		GerenciarBancoClientes listaClientes = new GerenciarBancoClientes();
		clientes_pesquisados = listaClientes.getClientes(-1, -1, null);
		clientes_disponiveis.clear();

		/*
		 * modelo.addColumn("Id"); modelo.addColumn("IE"); modelo.addColumn("Apelido");
		 * modelo.addColumn("CPF/CNPJ"); modelo.addColumn("Nome");
		 * 
		 */
		for (CadastroCliente cliente : listaClientes.getClientes(-1, -1, null)) {
			String cpf, cnpj, nome;

			if (cliente.getArmazem() == 1 || cliente.getTransportador() == 1) {

			} else {

				modelo_cliente.onAdd(cliente);
				clientes_disponiveis.add(cliente);
			}
		}

	}

	public TelaCliente(int flag_tipo_tela, int flag_tipo_cliente, Window janela_pai) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(TelaCliente.class.getResource("/imagens/equipe.png")));
		getDadosGlobais();
		// flag_tipo_tela == 1 //modo cliente edição
		// flag_tipo_tela == 0 //modo cliente seleção

		// flag_tipo_cliente == 1 //retorna comprador
		// flag_tipo_cliente == 2 //retorna vendedor
		// setAlwaysOnTop(true);

		// setModal(true);

		isto = this;
		setTitle("E-Contract - Clientes");

		setResizable(false);

		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1273, 712);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JPanel painelClientes = new JPanel();
		painelClientes.setBackground(Color.WHITE);
		painelClientes.setBounds(10, 11, 739, 446);

		JPanel painelGrupos = new JPanel();
		painelGrupos.setBackground(Color.WHITE);
		painelGrupos.setBounds(10, 11, 739, 446);
		painelClientes
				.setLayout(new MigLayout("", "[grow][2px][grow][grow][grow][4px][grow][grow][grow][grow][10px][grow]",
						"[33px][][37px][28px][grow][grow]"));

		JButton btnLimparCampos = new JButton("Limpar Campos");
		btnLimparCampos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				entNome.setText("");
				entApelido.setText("");
				entIe.setText("");
				entCpfCnpj.setText("");
			}
		});

		JLabel lblNomeEmpresarialfantasia = new JLabel("Nome Empresarial/Fantasia");
		lblNomeEmpresarialfantasia.setFont(new Font("Arial", Font.PLAIN, 16));
		painelClientes.add(lblNomeEmpresarialfantasia, "cell 0 1,alignx right");

		entNomeEmpresarialFantasia = new JTextField();
		entNomeEmpresarialFantasia.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();
			}
		});
		entNomeEmpresarialFantasia.setFont(new Font("SansSerif", Font.BOLD, 16));
		entNomeEmpresarialFantasia.setColumns(10);
		painelClientes.add(entNomeEmpresarialFantasia, "cell 2 1 2 1,growx");
		btnLimparCampos.setForeground(Color.WHITE);
		btnLimparCampos.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnLimparCampos.setBackground(Color.RED);
		painelClientes.add(btnLimparCampos, "cell 8 3,alignx right");
		// panel.setLayout(null);

		table = new JTable(modelo_cliente);
		sorter = new TableRowSorter<ClienteTableModel>(modelo_cliente);

		table.setRowSorter(sorter);
		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					processarSelecao(flag_tipo_tela, flag_tipo_cliente, table, janela_pai);
				}
			}
		});
		table.setBackground(new Color(255, 255, 255));
		table.setRowHeight(30);

		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(4).setPreferredWidth(120);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					processarSelecao(flag_tipo_tela, flag_tipo_cliente, table, janela_pai);

				}

			}
		});

		JScrollPane scrollPane = new JScrollPane(table);
		painelClientes.add(scrollPane, "cell 0 4 12 1,grow");
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// pesquisar(modelo);

			}
		});
		scrollPane.getViewport().setBackground(Color.white);
		scrollPane.setAutoscrolls(true);
		scrollPane.setBackground(new Color(255, 255, 255));

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		painelClientes.add(panel, "cell 0 5 12 1,alignx right,growy");

		pesquisar();

		panel.setLayout(
				new MigLayout("", "[271px][][12px][][108px][9px][110px][7px][109px][10px][105px]", "[grow][36px]"));

		JButton btnContasBancrias = new JButton("Contas Bancárias");
		btnContasBancrias.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaContaBancaria tela = new TelaContaBancaria(isto);
				tela.setVisible(true);

			}
		});
		
		JButton btnContatos = new JButton("Contatos");
		btnContatos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				TelaContato tela = new TelaContato(isto);
				tela.setVisible(true);
			}
		});
		btnContatos.setForeground(Color.BLACK);
		btnContatos.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnContatos.setBackground(Color.WHITE);
		panel.add(btnContatos, "cell 1 1");
		btnContasBancrias.setForeground(Color.BLACK);
		btnContasBancrias.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnContasBancrias.setBackground(Color.WHITE);
		panel.add(btnContasBancrias, "cell 2 1 2 1");

		JButton btnUsurio = new JButton("+ Cliente");
		btnUsurio.setBackground(Color.WHITE);
		btnUsurio.setForeground(Color.BLACK);
		btnUsurio.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel.add(btnUsurio, "cell 10 1,alignx left,aligny center");
		btnUsurio.setIcon(new ImageIcon(TelaCliente.class.getResource("/imagens/add_cliente.png")));

		JButton btnSelecionar = new JButton("Selecionar");
		btnSelecionar.setBackground(Color.WHITE);
		btnSelecionar.setForeground(Color.BLACK);
		btnSelecionar.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel.add(btnSelecionar, "cell 8 1,growx,aligny center");
		btnSelecionar.setIcon(new ImageIcon(TelaCliente.class.getResource("/imagens/lista.png")));

		JButton btnEditar = new JButton("Gerenciar");
		btnEditar.setForeground(Color.BLACK);
		btnEditar.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel.add(btnEditar, "cell 6 1,alignx left,aligny center");
		btnEditar.setBackground(Color.WHITE);
		btnEditar.setIcon(new ImageIcon(TelaCliente.class.getResource("/imagens/editar.png")));

		JButton btnVerNotasFiscais = new JButton("Todas as NF's");
		btnVerNotasFiscais.setForeground(Color.BLACK);
		btnVerNotasFiscais.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel.add(btnVerNotasFiscais, "cell 4 1,alignx left,aligny center");
		btnVerNotasFiscais.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaTodasNotasFiscais telaTodasNotasFiscais = new TelaTodasNotasFiscais(0, 1, isto);
				// telaTodasNotasFiscais.setTelaPai(isto);
				telaTodasNotasFiscais.limpar();
				telaTodasNotasFiscais.desabilitarBtnSelecionar();
				telaTodasNotasFiscais.pesquisar_notas();
				telaTodasNotasFiscais.setVisible(true);

			}
		});
		btnVerNotasFiscais.setBackground(Color.WHITE);

		JButton btnNewButton_1 = new JButton("Todos os Romaneios");
		btnNewButton_1.setForeground(Color.BLACK);
		btnNewButton_1.setBackground(Color.WHITE);
		btnNewButton_1.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaRomaneios telaRomaneio;
				telaRomaneio = new TelaRomaneios(1, isto);
				// telaRomaneio.setTelaPai(isto);
				telaRomaneio.pesquisarTodosOsRomaneios();

				telaRomaneio.setVisible(true);

			}
		});
		panel.add(btnNewButton_1, "cell 0 1,alignx right,aligny center");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int rowSel = table.getSelectedRow();// pega o indice da linha na table
				int indexRowModel = table.getRowSorter().convertRowIndexToModel(rowSel);// converte pro indice do model
				TelaGerenciarCliente telagerenciar = new TelaGerenciarCliente(clientes_disponiveis.get(indexRowModel),
						isto);
				telagerenciar.setVisible(true);
				// TelaCadastroCliente telaEdicao = new TelaCadastroCliente(0,
				// clientes_disponiveis.get(indiceDaLinha));
				// editarCliente(indiceDaLinha);

			}
		});
		btnSelecionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				processarSelecao(flag_tipo_tela, flag_tipo_cliente, table, janela_pai);

			}
		});
		btnUsurio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCadastroCliente novoCliente = new TelaCadastroCliente(1, null, isto);
				// novoCliente.setTelaPai(isto);
				novoCliente.setVisible(true);
			}
		});

		URL url = getClass().getResource("/imagens/pesquisar.png");
		ImageIcon img_botao = new ImageIcon(url);

		entNome = new JTextField();
		entNome.setFont(new Font("SansSerif", Font.BOLD, 16));
		entNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();
			}
		});
		painelClientes.add(entNome, "cell 2 0 2 1,grow");
		entNome.setColumns(10);

		JTabbedPane painelPrincipal = new JTabbedPane();
		;
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));

		painelPrincipal.addTab("Clientes", painelClientes);

		JLabel lblNewLabel = new JLabel("Nome e Sobrenome:");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		painelClientes.add(lblNewLabel, "cell 0 0,alignx right,aligny center");

		JLabel lblApelido = new JLabel("Apelido:");
		lblApelido.setFont(new Font("Arial", Font.PLAIN, 16));
		painelClientes.add(lblApelido, "cell 0 2,alignx right,aligny center");

		entApelido = new JTextField();
		entApelido.setFont(new Font("SansSerif", Font.BOLD, 16));
		entApelido.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();
			}
		});
		entApelido.setColumns(10);
		painelClientes.add(entApelido, "cell 2 2 2 1,grow");

		JLabel lblCpfcnpj = new JLabel("CPF/CNPJ:");
		lblCpfcnpj.setFont(new Font("Arial", Font.PLAIN, 16));
		painelClientes.add(lblCpfcnpj, "cell 4 0,alignx right,aligny center");

		entCpfCnpj = new JTextField();
		entCpfCnpj.setFont(new Font("SansSerif", Font.BOLD, 16));
		entCpfCnpj.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();
			}
		});
		entCpfCnpj.setColumns(10);
		painelClientes.add(entCpfCnpj, "cell 6 0 6 1,grow");

		JButton btnFiltrar = new JButton("Filtrar");
		btnFiltrar.setBackground(new Color(0, 51, 0));
		btnFiltrar.setForeground(Color.WHITE);
		btnFiltrar.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnFiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filtrar();
			}
		});
		painelClientes.add(btnFiltrar, "cell 11 3,alignx left,growy");

		JButton btnLimpar = new JButton("Limpar Pesquisa");
		btnLimpar.setBackground(new Color(204, 51, 0));
		btnLimpar.setForeground(Color.WHITE);
		btnLimpar.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sorter.setRowFilter(RowFilter.regexFilter(""));

			}
		});
		painelClientes.add(btnLimpar, "cell 9 3,grow");

		JLabel lblIe = new JLabel("IE:");
		lblIe.setFont(new Font("Arial", Font.PLAIN, 16));
		painelClientes.add(lblIe, "cell 4 2,alignx right,aligny center");

		entIe = new JTextField();
		entIe.setFont(new Font("SansSerif", Font.BOLD, 16));
		entIe.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();
			}
		});
		entIe.setColumns(10);
		painelClientes.add(entIe, "cell 6 2 6 1,grow");

		JButton btnRefazerPesquisa = new JButton("Refazer Pesquisa");
		btnRefazerPesquisa.setBackground(Color.BLUE);
		btnRefazerPesquisa.setForeground(Color.WHITE);
		btnRefazerPesquisa.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnRefazerPesquisa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisar();
			}
		});
		painelClientes.add(btnRefazerPesquisa, "cell 4 3 4 1,alignx right,growy");
		painelPrincipal.addTab("Grupos", painelGrupos);
		painelGrupos.setLayout(new MigLayout("", "[][grow][12px][grow][7px][grow]", "[grow][28px]"));

		table_grupos = new JTable(modelo_grupos);
		table_grupos.setRowHeight(30);

		JScrollPane scrollPaneGrupos = new JScrollPane(table_grupos);
		painelGrupos.add(scrollPaneGrupos, "cell 0 0 6 1,grow");
		scrollPaneGrupos.getViewport().setBackground(Color.white);

		modelo_grupos.addColumn("Id");
		modelo_grupos.addColumn("Nome");
		modelo_grupos.addColumn("Descrição");

		JButton btnNewButton = new JButton("+Grupo");
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(new Color(0, 51, 0));
		btnNewButton.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCadastroGrupo tela = new TelaCadastroGrupo(0, null, isto);
				// tela.setTelaPai(isto);
				tela.setVisible(true);
			}
		});

		JButton btnSelecionarGrupo = new JButton("Selecionar");
		btnSelecionarGrupo.setForeground(Color.WHITE);
		btnSelecionarGrupo.setBackground(new Color(0, 0, 102));
		btnSelecionarGrupo.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnSelecionarGrupo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (flag_tipo_tela == 0) {
					int indiceDaLinha = table_grupos.getSelectedRow();
					CadastroGrupo grupo_selecionado = lista_grupos.get(indiceDaLinha);
					((TelaRelatoriaContratos) telaPai).setGrupoAlvo(grupo_selecionado);
					isto.dispose();
				}

			}
		});

		JButton btnEditarGrupo = new JButton("Editar");
		btnEditarGrupo.setForeground(Color.WHITE);
		btnEditarGrupo.setBackground(new Color(255, 102, 0));
		btnEditarGrupo.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnEditarGrupo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int indiceDaLinha = table_grupos.getSelectedRow();
				CadastroGrupo grupo_selecionado = lista_grupos.get(indiceDaLinha);

				TelaCadastroGrupo tela_edicao_grupo = new TelaCadastroGrupo(1, grupo_selecionado, isto);
				tela_edicao_grupo.setVisible(true);

			}
		});
		painelGrupos.add(btnEditarGrupo, "flowx,cell 5 1,alignx right,aligny center");

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int indiceDaLinha = table_grupos.getSelectedRow();
				CadastroGrupo grupo_selecionado = lista_grupos.get(indiceDaLinha);

				if (JOptionPane.showConfirmDialog(isto, "Deseja excluir este Grupo?", "Excluir Grupo",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

					GerenciarBancoGrupos gerenciar = new GerenciarBancoGrupos();
					boolean remover = gerenciar.removerGrupo(grupo_selecionado.getId_grupo());
					if (remover) {
						JOptionPane.showMessageDialog(isto, "Grupo Removido");
						atualizarTabelaGrupos();
					} else {
						JOptionPane.showMessageDialog(isto, "Erro ao Remover o Grupo\nConsulte o administrador!");
					}

				}

			}
		});
		btnExcluir.setForeground(Color.WHITE);
		btnExcluir.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnExcluir.setBackground(new Color(204, 0, 0));
		painelGrupos.add(btnExcluir, "cell 5 1");
		painelGrupos.add(btnSelecionarGrupo, "cell 5 1,alignx right,aligny center");
		painelGrupos.add(btnNewButton, "cell 5 1,alignx right,aligny center");
		contentPane.setLayout(new MigLayout("", "[grow]", "[grow]"));

		contentPane.add(painelPrincipal, "cell 0 0,grow");

		if (flag_tipo_tela == 1) {
			// em modo cliente
			btnSelecionar.setEnabled(false);
			btnSelecionar.setVisible(false);
		} else {
			// em modo selecao
			// btnEditar.setEnabled(false);
			// btnEditar.setVisible(false);

			// btnUsurio.setEnabled(false);
			// btnUsurio.setVisible(false);

		}
		if (flag_tipo_tela == 0) {
			// modo selecao

		} else {
			btnSelecionar.setEnabled(false);
			btnSelecionar.setVisible(false);

			btnSelecionarGrupo.setEnabled(false);
			btnSelecionarGrupo.setVisible(false);
		}
		atualizarTabelaGrupos();

		this.setLocationRelativeTo(janela_pai);
		this.addWindowListener( new WindowAdapter() {
		    public void windowOpened( WindowEvent e ){
		        entNome.requestFocus();
		    }
		}); 
	}

	public void editarCliente(int indiceDaLinha) {
		TelaCadastroCliente telaEdicao = new TelaCadastroCliente(0, clientes_disponiveis.get(indiceDaLinha), isto);
		// telaEdicao.setTelaPai(isto);
		telaEdicao.setVisible(true);

	}

	public void atualizaTabela() {
		pesquisar();
	}

	public CadastroCliente selecionaCliente() {
		return clienteSelecionado;

	}

	public void setTelaPai(Window _telapai) {
		this.telaPai = _telapai;
	}

	public void atualizarTabelaGrupos() {
		modelo_grupos.setNumRows(0);
		GerenciarBancoGrupos gerenciar = new GerenciarBancoGrupos();
		lista_grupos.clear();
		lista_grupos = gerenciar.getGrupos();

		for (CadastroGrupo grupo : lista_grupos) {

			modelo_grupos
					.addRow(new Object[] { grupo.getId_grupo(), grupo.getNome_grupo(), grupo.getDescricao_grupo() });

		}
	}

	public static class ClienteTableModel extends AbstractTableModel {

		private final int id = 0;
		private final int ie = 1;
		private final int apelido = 2;
		private final int cpf_cnpj = 3;
		private final int nome_sobrenome = 4;
		private final int nome_empresarial_fantasia = 5;

		private final String colunas[] = { "ID:", "IE:", "Apelido:", "CPF/CNPJ:", "Nome e Sobrenome:",
				"Nome Empresarial/Fantasia" };
		private final ArrayList<CadastroCliente> dados = new ArrayList<>();// usamos como dados uma lista genérica de
																			// nfs

		public ClienteTableModel() {

		}

		@Override
		public int getColumnCount() {
			// retorna o total de colunas
			return colunas.length;
		}

		@Override
		public int getRowCount() {
			// retorna o total de linhas na table
			return dados.size();
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			// retorna o tipo de dado, para cada coluna
			switch (columnIndex) {
			case id:
				return Integer.class;
			case ie:
				return String.class;
			case apelido:
				return String.class;
			case cpf_cnpj:
				return String.class;
			case nome_sobrenome:
				return String.class;
			case nome_empresarial_fantasia:
				return String.class;

			default:
				throw new IndexOutOfBoundsException("Coluna Inválida!!!");
			}
		}

		@Override
		public String getColumnName(int columnIndex) {
			return colunas[columnIndex];
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			// retorna o valor conforme a coluna e linha

			// pega o dados corrente da linha
			CadastroCliente nota = dados.get(rowIndex);

			// retorna o valor da coluna
			switch (columnIndex) {
			case id:
				return nota.getId();
			case ie:
				return nota.getIe();
			case apelido:
				return nota.getApelido().toUpperCase();
			case cpf_cnpj: {
				if (nota.getTipo_pessoa() == 0)
					return nota.getCpf();
				else
					return nota.getCnpj();

			}
			case nome_sobrenome: {
				if (nota.getTipo_pessoa() == 0) {
					return nota.getNome().toUpperCase() + " " + nota.getSobrenome().toUpperCase();
				} else {
					return nota.getRazao_social().toUpperCase();
				}

			}
			case nome_empresarial_fantasia: {
				if (nota.getTipo_pessoa() == 0) {
					return nota.getNome_empresarial().toUpperCase();
				} else {
					return nota.getNome_fantaia().toUpperCase();
				}
			}
			default:
				throw new IndexOutOfBoundsException("Coluna Inválida!!!");
			}
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			// metodo identifica qual coluna é editavel

			// só iremos editar a coluna BENEFICIO,
			// que será um checkbox por ser boolean

			return false;
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			CadastroCliente nota = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public CadastroCliente getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(CadastroCliente nota) {
			return dados.indexOf(nota);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(CadastroCliente nota) {
			dados.add(nota);
			fireTableRowsInserted(indexOf(nota), indexOf(nota));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<CadastroCliente> dadosIn) {
			dados.addAll(dadosIn);
			fireTableDataChanged();
		}

		/**
		 * remove um registro da lista, através do indice
		 * 
		 * @param rowIndex
		 */
		public void onRemove(int rowIndex) {
			dados.remove(rowIndex);
			fireTableRowsDeleted(rowIndex, rowIndex);
		}

		/**
		 * remove um registro da lista, através do objeto
		 * 
		 * @param empregado
		 */
		public void onRemove(CadastroCliente nota) {
			int indexBefore = indexOf(nota);// pega o indice antes de apagar
			dados.remove(nota);
			fireTableRowsDeleted(indexBefore, indexBefore);
		}

		/**
		 * remove todos registros da lista
		 */
		public void onRemoveAll() {
			dados.clear();
			fireTableDataChanged();
		}

	}

	public void filtrar() {

		ArrayList<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>(2);

		String nome = entNome.getText().toUpperCase();
		String apelido = entApelido.getText().toUpperCase();
		String ie = entIe.getText().toUpperCase();
		String cpnf_cnpj = entCpfCnpj.getText().toUpperCase();
		String nome_empresarial_fantasia = entNomeEmpresarialFantasia.getText().toUpperCase();

		if (checkString(ie))
			filters.add(RowFilter.regexFilter(ie, 1));

		if (checkString(apelido))
			filters.add(RowFilter.regexFilter(apelido, 2));

		if (checkString(cpnf_cnpj))
			filters.add(RowFilter.regexFilter(cpnf_cnpj, 3));

		if (checkString(nome))
			filters.add(RowFilter.regexFilter(nome, 4));

		if (checkString(nome_empresarial_fantasia))
			filters.add(RowFilter.regexFilter(nome_empresarial_fantasia, 5));

		try {
			sorter.setRowFilter(RowFilter.andFilter(filters));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean checkString(String txt) {
		return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ");
	}

	public void getDadosGlobais() {
		// gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		GerenciadorLog = dados.getGerenciadorLog();
		configs_globais = dados.getConfigs_globais();

		// usuario logado
		login = dados.getLogin();

	}

	public void processarSelecao(int flag_tipo_tela, int flag_tipo_cliente, JTable table, Window janela_pai) {
		if (flag_tipo_tela == 0) {
			int rowSel = table.getSelectedRow();// pega o indice da linha na table
			int indiceDaLinha = table.getRowSorter().convertRowIndexToModel(rowSel);// converte pro indice do model
			clienteSelecionado = clientes_disponiveis.get(indiceDaLinha);
			if (flag_tipo_cliente == 1)
				((TelaElaborarNovoContrato) telaPai).setComprador1(clienteSelecionado);
			else if (flag_tipo_cliente == 2)
				((TelaElaborarNovoContrato) telaPai).setVendedor1(clienteSelecionado);
			else if (flag_tipo_cliente == 3)
				((TelaElaborarNovoContrato) telaPai).setVendedor2(clienteSelecionado);
			else if (flag_tipo_cliente == 4)
				((TelaElaborarNovoContrato) telaPai).setCorretor(clienteSelecionado);
			else if (flag_tipo_cliente == 5)
				((TelaConfirmarCarregamento) telaPai).setClienteCarregamento(clienteSelecionado);
			else if (flag_tipo_cliente == 6)
				((TelaConfirmarCarregamento) telaPai).setVendedor(clienteSelecionado);
			else if (flag_tipo_cliente == 8)
				((TelaConfirmarPagamentoContratual) telaPai).setDepositante(clienteSelecionado);
			else if (flag_tipo_cliente == 9)
				((TelaConfirmarPagamentoContratual) telaPai).setFavorecido(clienteSelecionado);
			else if (flag_tipo_cliente == 10) {
				ArrayList<CadastroCliente> lista = new ArrayList<>();
				lista = getClientesSelecionados();
				((TelaCadastroGrupo) telaPai).adicionarIntegrantes(lista);

			} else if (flag_tipo_cliente == 11)
				((TelaRelatoriaContratos) telaPai).setClienteAlvo(clienteSelecionado);
			else if (flag_tipo_cliente == 12)
				((TelaImportarContratoManual) telaPai).setCorretor(clienteSelecionado);
			else if (flag_tipo_cliente == 13)
				((TelaImportarContratoManual) telaPai).setComprador1(clienteSelecionado);
			else if (flag_tipo_cliente == 14)
				((TelaImportarContratoManual) telaPai).setVendedor1(clienteSelecionado);
			else if (flag_tipo_cliente == 15)
				((TelaImportarContratoManual) telaPai).setVendedor2(clienteSelecionado);
			else if (flag_tipo_cliente == 16)
				((TelaConfirmarRecebimento) telaPai).setClienteRecebimento(clienteSelecionado);
			else if (flag_tipo_cliente == 17)
				((TelaConfirmarRecebimento) telaPai).setVendedor(clienteSelecionado);
			else if (flag_tipo_cliente == 18)
				((TelaElaborarNovoContrato) telaPai).setComprador2(clienteSelecionado);
			else if (flag_tipo_cliente == 20)
				((TelaImportarContratoManual) telaPai).setComprador2(clienteSelecionado);
			else if (flag_tipo_cliente == 25)
				((TelaFinanceiroCadastroInstituicaoBancaria) telaPai).setCliente(clienteSelecionado);
			else if (flag_tipo_cliente == 26)
				((TelaFinanceiroCadastroCentroCusto) telaPai).setCliente(clienteSelecionado);
			else if (flag_tipo_cliente == 27) {

				if (janela_pai instanceof TelaFinanceiroCadastroLancamento)
					((TelaFinanceiroCadastroLancamento) janela_pai).setCliente(clienteSelecionado);
				else if (janela_pai instanceof TelaFinanceiroCadastroEmprestimo)
					((TelaFinanceiroCadastroEmprestimo) janela_pai).setCliente(clienteSelecionado);

			} else if (flag_tipo_cliente == 28)
				((TelaConfirmarTransferenciaPagamentoContratual) janela_pai).setDepositante(clienteSelecionado);
			else if (flag_tipo_cliente == 29)
				((TelaConfirmarTransferenciaPagamentoContratual) janela_pai).setFavorecido(clienteSelecionado);
			else if (flag_tipo_cliente == 30)
				((TelaRelatoriaContratos) telaPai).setClienteContraParte1(clienteSelecionado);
			else if (flag_tipo_cliente == 31) {
				if (janela_pai instanceof TelaFinanceiroCadastroPagamento)
					((TelaFinanceiroCadastroPagamento) janela_pai).setPagadorClienteFornecedor(clienteSelecionado);
				else if (janela_pai instanceof TelaFinanceiroCadastroPagamentoEmprestimo)
					((TelaFinanceiroCadastroPagamentoEmprestimo) janela_pai)
							.setPagadorClienteFornecedor(clienteSelecionado);

			} else if (flag_tipo_cliente == 32)
				((TelaRelatoriaContratos) telaPai).setClienteAlvo2(clienteSelecionado);
			else if (flag_tipo_cliente == 35)
				((TelaFinanceiroGerenciarLancamento) janela_pai).setDestinatarioNF(clienteSelecionado);
			else if (flag_tipo_cliente == 36) {
				if (janela_pai instanceof TelaFinanceiroCadastroPagamento)
					((TelaFinanceiroCadastroPagamento) janela_pai).setRecebedorClienteFornecedor(clienteSelecionado);
				else if (janela_pai instanceof TelaFinanceiroCadastroPagamentoEmprestimo)
					((TelaFinanceiroCadastroPagamentoEmprestimo) janela_pai)
							.setRecebedorClienteFornecedor(clienteSelecionado);

			} else if (flag_tipo_cliente == 37) {
				
				 if (janela_pai instanceof TelaEnviarAoContador)
					((TelaEnviarAoContador) janela_pai).setCliente(clienteSelecionado);

			} else if (flag_tipo_cliente == 50) {
				((TelaFinanceiroCadastroParcelaEmprestimo) janela_pai).setRecebedor(clienteSelecionado);

			} else if (flag_tipo_cliente == 60) {
				((TelaCriarRecibo) janela_pai).addRecebedorIntermediario(clienteSelecionado);

			} else if (flag_tipo_cliente == 61) {
				((TelaFuncionariosAssosiacaoFuncionarioCliente) janela_pai).setCliente(clienteSelecionado);

			} else if (flag_tipo_cliente == 90) {
				((TelaCadastroNotaFiscal) janela_pai).setRemetente(clienteSelecionado);

			} else if (flag_tipo_cliente == 91) {
				((TelaCadastroNotaFiscal) janela_pai).setDestinatario(clienteSelecionado);

			}else if (flag_tipo_cliente == 100) {
				((TelaFilaCadastrarMovimentoDesembarque) janela_pai).setProdutor(clienteSelecionado);

			}else if (flag_tipo_cliente == 110) {
				((TelaFilaCadastrarMovimentoEmbarque) janela_pai).setCliente(clienteSelecionado);

			}else if (flag_tipo_cliente == 115) {
				((TelaCadastrarRomaneio) janela_pai).setRemetente(clienteSelecionado);

			}else if (flag_tipo_cliente == 120) {
				((TelaCadastrarRomaneio) janela_pai).setDestinatario(clienteSelecionado);

			}



			isto.dispose();

		} else {
			int indiceDaLinha = 0;
			indiceDaLinha = table.getSelectedRow();
			System.out.println("Indice da linha selecionado: " + indiceDaLinha);
			// TelaCadastroCliente telaEdicao = new TelaCadastroCliente(0,
			// clientes_disponiveis.get(indiceDaLinha));
			editarCliente(indiceDaLinha);

		}
	}

	public ArrayList<CadastroCliente> getClientesSelecionados() {
		ArrayList<CadastroCliente> clientes_selecionados = new ArrayList<>();
		int linhas_selecionadas[] = table.getSelectedRows();// pega o indice da linha na table

		for (int i = 0; i < linhas_selecionadas.length; i++) {

			int indice = table.getRowSorter().convertRowIndexToModel(linhas_selecionadas[i]);// converte pro
																								// indice do
																								// model
			clientes_selecionados.add(modelo_cliente.getValue(indice));
		}

		return clientes_selecionados;
	}
}
