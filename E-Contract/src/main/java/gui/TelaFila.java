package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import main.java.cadastros.CadastroAcessoTemporario;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroFilaMovimento;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroMensagem;
import main.java.cadastros.CadastroRomaneio;
import main.java.cadastros.Contato;
import main.java.cadastros.Lancamento;
import main.java.conexaoBanco.GerenciarBancoAcessoTemporario;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoFilaMovimento;
import main.java.conexaoBanco.GerenciarBancoLancamento;
import main.java.conexaoBanco.GerenciarBancoMensagem;
import main.java.conexaoBanco.GerenciarBancoStatusArmazem;
import main.java.gui.TelaContratos.EvenOddRenderer;
import main.java.gui.TelaContratos.StatusRecebimentoCellRender;
import main.java.gui.TelaFinanceiroLancamento.LancamentoTableModel;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.manipular.Whatsapp;
import main.java.outros.DadosGlobais;
import main.java.outros.GetData;
import main.java.tratamento_proprio.Log;
import main.java.views_personalizadas.TelaEscolhaRelatorioFila;
import main.java.views_personalizadas.TelaEscolhaRelatorioRomaneios;
import net.miginfocom.swing.MigLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.swing.border.MatteBorder;
import javax.swing.border.LineBorder;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.FlowLayout;

public class TelaFila extends JFrame {

	private JTabbedPane painelPrincipal;
	private JPanel painelDescarga = new JPanel();
	private JPanel painelCarga = new JPanel();
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private JTable tabela_desembarque;
	private JButton btnMarcarVez;
	private TelaFila isto;
	private JScrollPane scrollPaneEmbarque;
	private JTable tabela_embarque;
	private ArrayList<CadastroFilaMovimento> lista_fila_desembarque = new ArrayList<>();
	private ArrayList<CadastroFilaMovimento> lista_fila_embarque = new ArrayList<>();

	private FilaDesembarqueTableModel modelo_fila_desembarque = new FilaDesembarqueTableModel();
	private FilaEmbarqueTableModel modelo_fila_embarque = new FilaEmbarqueTableModel();

	private String servidor_unidade;

	private TableRowSorter<FilaDesembarqueTableModel> sorterDesembarque;
	private TableRowSorter<FilaEmbarqueTableModel> sorterEmbarque;

	private JPanel panel;
	private JLabel lblNewLabel;
	private JTextField entDataMenor;
	private JLabel lblAt;
	private JTextField entDataMaior;
	private JLabel lblNewLabel_1;
	private JButton btnPesquisar;
	private JButton btnFiltrar;
	private JButton btnLimparBusca;
	private JLabel lblNewLabel_3;
	private JTextField entMotorista;
	private JPanel panel_1;
	private JLabel lblNewLabel_4;
	private JTextField entPlaca;
	private JLabel lblNewLabel_5;
	private JTextField entProdutor;
	private JLabel lblNewLabel_6;
	private JTextField entProduto;
	private JPanel panel_2;
	private JLabel lblNewLabel_7;
	private JLabel lblNewLabel_8;
	private JLabel lblNewLabel_9;
	private JLabel lblNewLabel_10;
	private JLabel lblNewLabel_11;
	private JLabel lblNewLabel_12;
	private JLabel lblNewLabel_13;
	private JLabel lblEmFila;
	private JLabel lblEmDesembarque;
	private JLabel lblDesembarcados;
	private JLabel lblNewLabel_14;
	private JLabel lblTotal;
	private JLabel lblAsd1;
	private JLabel lblTempoMedioEsperaDesembarque;
	private JLabel lblNewLabel_15;
	private JLabel lblUnidadesNaFrente;
	private JButton btnSubirFilaDesembarque;
	private JButton btnDescerFilaDesembarque;
	private JLabel lblNewLabel_16;

	private int flag_global = 0;
	private JButton btnEditar;
	private JLabel lblNewLabel_17;
	private JLabel lblNewLabel_2;
	private JTextField entMotoristaEmbarque;
	private JTextField entPlacaEmbarque;
	private JTextField entProdutoEmbarque;
	private JTextField entDataMaiorEmbarque, entDataMenorEmbarque;
	private JPanel panel_3;
	private JLabel lblNewLabel_3Embarque_1;
	private JTextField entTransportadoraEmbarque;
	private JPanel panel_5;
	private JPanel panel_4;
	private JLabel lblNewLabel_18;
	private JLabel lblNewLabel_19;
	private JLabel lblNewLabel_20;
	private JLabel lblNewLabel_21;
	private JLabel lblNewLabel_22;
	private JLabel lblNewLabel_23;
	private JLabel lblNewLabel_24;
	private JLabel lblEmFilaEmbarque;
	private JLabel lblEmEmbarque;
	private JLabel lblEmbarcados;
	private JLabel lblAsd1_1;
	private JLabel lblTempoMedioEsperaEmbarque;
	private JLabel lblNewLabel_25;
	private JLabel lblUnidadesNaFrenteEmbarque;
	private JLabel lblNewLabel_26;
	private JLabel lblMotivoEmbarque;
	private JButton btnMarcarVezEmbarque;
	private JButton btnEditarEmbarque;
	private JButton btnSubirFilaEmbarque;
	private JButton btnDescerFilaEmbarque;
	private JLabel lblNewLabel_27;
	private JLabel lblTotalEmbarque;
	private JButton btnPesquisarEmbarque;
	private JButton btnFiltrar_1;
	private JLabel lblNewLabel_3Embarque_2;
	private JTextField entClienteDestinatario;
	private JTextArea lblMotivo;
	private JButton btnNewButton;
	private JLabel lblNewLabel_28;
	private JComboBox cbStatusDesembarque;
	private JLabel lblNewLabel_29;
	private JComboBox cbStatusEmbarque;
	private JButton btnNewButton_1;
	private JPanel panel_6;
	private JButton btnTrocarDesembarque;
	private JPanel panel_7;
	private JButton btnTrocarEmbarque;
	private SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
	private JPanel panel_8;
	private JPanel panel_9;
	private JButton btnNewButton_2;
	private JButton btnNewButton_3;

	public TelaFila(int flag, Window window) {

		isto = this;
		getDadosGlobais();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("E-Contract - Fila");

		flag_global = flag;

		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		System.out.println("Screen width = " + dim.width);
		System.out.println("Screen height = " + dim.height);

		// pega o tamanho da barra de tarefas
		Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
		java.awt.Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		int taskBarHeight = scrnSize.height - winSize.height;
		System.out.printf("Altura: %d\n", taskBarHeight);

		DisplayMode display = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDisplayMode();

		int display_x = display.getWidth();
		int display_y = display.getHeight();
		setBounds(0, 0, dim.width, dim.height - taskBarHeight);

		painelPrincipal = new JTabbedPane();
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		painelPrincipal.setBackground(new Color(255, 255, 255));

		painelDescarga.setBackground(new Color(255, 255, 255));
		painelPrincipal.addTab("Fila de Desembarque", painelDescarga);
		painelDescarga.setLayout(new MigLayout("", "[grow]", "[grow][][grow][]"));
		sorterDesembarque = new TableRowSorter<FilaDesembarqueTableModel>(modelo_fila_desembarque);
		StatusFilaRenderDesembarque renderer = new StatusFilaRenderDesembarque();

		lblNewLabel_16 = new JLabel("Fila para o Desembarque");
		lblNewLabel_16.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 25));
		painelDescarga.add(lblNewLabel_16, "flowx,cell 1 0 2 1,alignx center");

		// tabela_desembarque.getColumnModel().getColumn(14).setCellRenderer(new
		// StatusFilaRender());
		// tabela_desembarque.getColumnModel().getColumn(15).setCellRenderer(new
		// NotificacaoFilaRender());

		panel = new JPanel();
		panel.setForeground(Color.WHITE);
		panel.setBackground(Color.WHITE);
		painelDescarga.add(panel, "cell 1 1,grow");
		panel.setLayout(new MigLayout("", "[][][][][][][][]", "[grow][][]"));

		lblNewLabel_3 = new JLabel("Motorista:");
		lblNewLabel_3.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel.add(lblNewLabel_3, "cell 0 0,alignx trailing");

		entMotorista = new JTextField();
		entMotorista.setFont(new Font("SansSerif", Font.BOLD, 16));
		entMotorista.setColumns(10);
		entMotorista.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrarDesembarque();
			}
		});

		panel.add(entMotorista, "cell 1 0,growx");

		lblNewLabel_4 = new JLabel("Placa:");
		lblNewLabel_4.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel.add(lblNewLabel_4, "cell 2 0,alignx trailing");

		entPlaca = new JTextField();
		entPlaca.setFont(new Font("SansSerif", Font.BOLD, 16));
		entPlaca.setColumns(10);
		entPlaca.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrarDesembarque();
			}
		});

		panel.add(entPlaca, "cell 3 0,growx");

		lblNewLabel_28 = new JLabel("Status:");
		lblNewLabel_28.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel.add(lblNewLabel_28, "cell 4 0,alignx trailing");

		cbStatusDesembarque = new JComboBox();
		cbStatusDesembarque.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				try {
					filtrarDesembarque();
				} catch (Exception y) {

				}
			}
		});
		cbStatusDesembarque.addItem("TODOS");
		cbStatusDesembarque.addItem("EM FILA");
		cbStatusDesembarque.addItem("ENTRADA");
		cbStatusDesembarque.addItem("SAÍDA");
		cbStatusDesembarque.addItem("CANCELADO");

		panel.add(cbStatusDesembarque, "cell 5 0,growx");

		lblNewLabel_5 = new JLabel("Produtor:");
		lblNewLabel_5.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel.add(lblNewLabel_5, "cell 0 1,alignx trailing");

		entProdutor = new JTextField();
		entProdutor.setFont(new Font("SansSerif", Font.BOLD, 16));
		entProdutor.setColumns(10);
		entProdutor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrarDesembarque();
			}
		});

		panel.add(entProdutor, "cell 1 1,growx");

		lblNewLabel_6 = new JLabel("Produto:");
		lblNewLabel_6.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel.add(lblNewLabel_6, "cell 2 1,alignx trailing");

		entProduto = new JTextField();
		entProduto.setFont(new Font("SansSerif", Font.BOLD, 16));
		entProduto.setColumns(10);
		entProduto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrarDesembarque();
			}
		});

		panel.add(entProduto, "cell 3 1,growx");
		
		btnNewButton_3 = new JButton("Configurar Mensagem");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (login != null) {
					if (login.getConfigs_privilegios().getNivel_privilegios() <= 2) {
						TelaFilaConfigurarMensagem tela = new TelaFilaConfigurarMensagem(
								new GerenciarBancoMensagem().getMensagem() , isto);
						tela.setVisible(true);
					} else {

						// verifica se tem acesso temporario
						GerenciarBancoAcessoTemporario gerenciar = new GerenciarBancoAcessoTemporario();
						ArrayList<CadastroAcessoTemporario> acessos = gerenciar
								.getAcessosTemporariosPorExecutor(login.getId());

						boolean tem_acesso = false;

						for (CadastroAcessoTemporario acesso : acessos) {

							int modulo = acesso.getModulo();
							if (modulo == 1) {
								// modulo e recursos humanos
								LocalDateTime inicio = LocalDateTime.parse(
										acesso.getData_inicial() + " " + acesso.getHora_inicial(),
										DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
								LocalDateTime fim = LocalDateTime.parse(
										acesso.getData_final() + " " + acesso.getHora_final(),
										DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

								LocalDateTime agora = LocalDateTime.now();

								if (agora.isAfter(inicio) && agora.isBefore(fim)) {
									tem_acesso = true;
									break;
								}

							}

						}

						if (!tem_acesso)
							JOptionPane.showMessageDialog(isto, "Requer Elevação de Direitos");
						else {
							TelaFilaConfigurarMensagem tela = new TelaFilaConfigurarMensagem(
									new GerenciarBancoMensagem().getMensagem() , isto);
							tela.setVisible(true);
						}
					}

				}
				
			}
		});
		btnNewButton_3.setForeground(Color.WHITE);
		btnNewButton_3.setFont(new Font("SansSerif", Font.BOLD, 12));
		btnNewButton_3.setBackground(new Color(0, 0, 102));
		panel.add(btnNewButton_3, "cell 7 1");

		lblNewLabel_1 = new JLabel("Período:");
		lblNewLabel_1.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel.add(lblNewLabel_1, "cell 0 2,alignx right");

		panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel.add(panel_1, "cell 1 2,grow");
		panel_1.setLayout(new MigLayout("", "[][][][][]", "[]"));

		lblNewLabel = new JLabel("De:");
		panel_1.add(lblNewLabel, "cell 0 0");
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 16));

		entDataMenor = new JTextField();
		panel_1.add(entDataMenor, "cell 1 0");
		entDataMenor.setFont(new Font("SansSerif", Font.BOLD, 16));
		entDataMenor.setColumns(10);
		entDataMenor.setText(new GetData().getData());

		lblAt = new JLabel("Até");
		panel_1.add(lblAt, "cell 2 0");
		lblAt.setFont(new Font("SansSerif", Font.BOLD, 16));

		entDataMaior = new JTextField();
		panel_1.add(entDataMaior, "cell 3 0");
		entDataMaior.setFont(new Font("SansSerif", Font.BOLD, 16));
		entDataMaior.setColumns(10);
		entDataMaior.setText(new GetData().getData());

		btnLimparBusca = new JButton("limpar busca");
		btnLimparBusca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparFiltrosDesembarque();
				calcularDesembarque();
			}
		});

		btnFiltrar = new JButton("filtrar");
		btnFiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filtrarDesembarque();
			}
		});
		btnFiltrar.setForeground(Color.BLACK);
		btnFiltrar.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnFiltrar.setBackground(Color.ORANGE);
		panel.add(btnFiltrar, "flowx,cell 5 2,alignx right");
		btnLimparBusca.setForeground(Color.BLACK);
		btnLimparBusca.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnLimparBusca.setBackground(Color.RED);
		panel.add(btnLimparBusca, "cell 5 2,alignx center");

		btnPesquisar = new JButton("pesquisar");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisar_fila_desembarque();
			}
		});
		btnPesquisar.setBackground(Color.BLUE);
		btnPesquisar.setForeground(Color.WHITE);
		btnPesquisar.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel.add(btnPesquisar, "cell 5 2,alignx right");

		btnNewButton_2 = new JButton("Configurar Status");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (login != null) {
					if (login.getConfigs_privilegios().getNivel_privilegios() <= 2) {
						TelaFilaConfigurarStatus tela = new TelaFilaConfigurarStatus(
								new GerenciarBancoStatusArmazem().getStatusArmazem(), isto);
						tela.setVisible(true);
					} else {

						// verifica se tem acesso temporario
						GerenciarBancoAcessoTemporario gerenciar = new GerenciarBancoAcessoTemporario();
						ArrayList<CadastroAcessoTemporario> acessos = gerenciar
								.getAcessosTemporariosPorExecutor(login.getId());

						boolean tem_acesso = false;

						for (CadastroAcessoTemporario acesso : acessos) {

							int modulo = acesso.getModulo();
							if (modulo == 1) {
								// modulo e recursos humanos
								LocalDateTime inicio = LocalDateTime.parse(
										acesso.getData_inicial() + " " + acesso.getHora_inicial(),
										DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
								LocalDateTime fim = LocalDateTime.parse(
										acesso.getData_final() + " " + acesso.getHora_final(),
										DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

								LocalDateTime agora = LocalDateTime.now();

								if (agora.isAfter(inicio) && agora.isBefore(fim)) {
									tem_acesso = true;
									break;
								}

							}

						}

						if (!tem_acesso)
							JOptionPane.showMessageDialog(isto, "Requer Elevação de Direitos");
						else {
							TelaFilaConfigurarStatus tela = new TelaFilaConfigurarStatus(
									new GerenciarBancoStatusArmazem().getStatusArmazem(), isto);
							tela.setVisible(true);
						}
					}

				}

			}
		});
		btnNewButton_2.setBackground(new Color(0, 51, 0));
		btnNewButton_2.setForeground(Color.WHITE);
		btnNewButton_2.setFont(new Font("SansSerif", Font.BOLD, 12));
		panel.add(btnNewButton_2, "cell 7 2");

		panel_9 = new JPanel();
		panel_9.setBackground(Color.WHITE);
		painelDescarga.add(panel_9, "cell 0 2 2 1,grow");

		tabela_desembarque = new JTable(modelo_fila_desembarque);
		tabela_desembarque.setRowSorter(sorterDesembarque);
		tabela_desembarque.setDefaultRenderer(Object.class, renderer);
		tabela_desembarque.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		tabela_desembarque.setRowHeight(30);

		tabela_desembarque.getColumnModel().getColumn(0).setPreferredWidth(40); // id
		tabela_desembarque.getColumnModel().getColumn(1).setPreferredWidth(180); // romaneio
		tabela_desembarque.getColumnModel().getColumn(2).setPreferredWidth(100); // data
		tabela_desembarque.getColumnModel().getColumn(3).setPreferredWidth(80); // hora
		tabela_desembarque.getColumnModel().getColumn(4).setPreferredWidth(80); // hora
		tabela_desembarque.getColumnModel().getColumn(5).setPreferredWidth(80); // hora

		tabela_desembarque.getColumnModel().getColumn(6).setPreferredWidth(200); // motorista
		tabela_desembarque.getColumnModel().getColumn(7).setPreferredWidth(100); // placa
		tabela_desembarque.getColumnModel().getColumn(8).setPreferredWidth(100); // inscricao

		tabela_desembarque.getColumnModel().getColumn(9).setPreferredWidth(200); // produtor
		tabela_desembarque.getColumnModel().getColumn(10).setPreferredWidth(100); // produto
		tabela_desembarque.getColumnModel().getColumn(11).setPreferredWidth(150); // origem

		tabela_desembarque.addMouseListener(new java.awt.event.MouseAdapter() {
			// Importe a classe java.awt.event.MouseEvent
			public void mouseClicked(MouseEvent e) {
				// Se o botão direito do mouse foi pressionado
				if (e.getButton() == MouseEvent.BUTTON1) {
					// Exibe o popup menu na posição do mouse.

					int rowSel = tabela_desembarque.getSelectedRow();// pega o indice da linha na tabela
					int indiceDaLinha = tabela_desembarque.getRowSorter().convertRowIndexToModel(rowSel);
					CadastroFilaMovimento unidade = modelo_fila_desembarque.getValue(indiceDaLinha);

					lblUnidadesNaFrente.setText(unidadesNaFrenteDesembarque(unidade) + " Caminhões ");
					lblMotivo.setText(unidade.getMotivo());
				}

			}
		});
		panel_9.setLayout(new MigLayout("", "[grow][84px]", "[443px]"));

		JScrollPane scrollPane = new JScrollPane(tabela_desembarque);
		panel_9.add(scrollPane, "cell 0 0,grow");
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		panel_8 = new JPanel();
		panel_9.add(panel_8, "cell 1 0,growx,aligny center");
		panel_8.setBackground(Color.WHITE);
		panel_8.setLayout(new MigLayout("", "[74px]", "[154px]"));

		panel_6 = new JPanel();
		panel_8.add(panel_6, "cell 0 0,alignx left,aligny top");
		panel_6.setBackground(Color.WHITE);
		panel_6.setLayout(new MigLayout("", "[]", "[][][]"));

		btnSubirFilaDesembarque = new JButton("");
		panel_6.add(btnSubirFilaDesembarque, "cell 0 0");
		btnSubirFilaDesembarque.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int rowSel = tabela_desembarque.getSelectedRow();// pega o indice da linha na tabela
				int indiceDaLinha = tabela_desembarque.getRowSorter().convertRowIndexToModel(rowSel);

				CadastroFilaMovimento unidade_selecionada = modelo_fila_desembarque.getValue(indiceDaLinha);
				CadastroFilaMovimento unidade_anterior = modelo_fila_desembarque.getValue(indiceDaLinha - 1);
				if (unidade_anterior != null) {

					GerenciarBancoFilaMovimento gerenciar = new GerenciarBancoFilaMovimento();

					int id_pivo = gerenciar.getMaxId();

					if (id_pivo > 0) {

						int id_unidade_selecionada = unidade_selecionada.getId();
						int id_unidade_anterior = unidade_anterior.getId();

						// 1 passo
						boolean mudar_id_unidade_anterior = gerenciar.atualizarId(id_pivo, id_unidade_anterior);
						// colocar a unidade anterior no ultima posicao da fila
						if (mudar_id_unidade_anterior) {

							// 2 passo
							// colocar a unidade selecionada na posicao acima
							boolean mudar_id_unidade_selecionada = gerenciar.atualizarId(id_unidade_anterior,
									id_unidade_selecionada);

							if (mudar_id_unidade_selecionada) {

								// 3 passo
								// colocar a unidade anterior na posicao abaixo
								boolean mudar_id_unidade_pivo_para_unidade_selecionada = gerenciar
										.atualizarId(id_unidade_selecionada, id_pivo);
								if (mudar_id_unidade_pivo_para_unidade_selecionada) {
									JOptionPane.showMessageDialog(isto, "Fila de desembarque reorganizada");
									pesquisar_fila_desembarque();
								} else {
									JOptionPane.showMessageDialog(isto,
											"Erro grave ao reorganizar a Fila de desembarque, não tente novamente!\nConsulte o administrador para que seja restaurado a fila!");
									pesquisar_fila_desembarque();

								}

							} else {
								// volte o id anterior da unidade anterior
								boolean voltar_estado_original = gerenciar.atualizarId(id_unidade_anterior, id_pivo);
								if (voltar_estado_original) {
									JOptionPane.showMessageDialog(isto,
											"Erro ao reorganizar a Fila de desembarque, tente novamente!\nSe o erro persistir, consulte o administrador!");

								} else {
									JOptionPane.showMessageDialog(isto,
											"Erro grave reorganizar a Fila de desembarque, não tente novamente!\nConsulte o administrador para que seja restaurado a fila!");

								}
							}

						} else {
							JOptionPane.showMessageDialog(isto,
									"Erro ao reorganizar a Fila de desembarque, não foi possivel mudar a posição, tente novamente!\nSe o erro persistir, consulte o administrador!");

						}
					} else {
						JOptionPane.showMessageDialog(isto,
								"Erro ao reorganizar a Fila de desembarque, pivo não encontrado, tente novamente!\nSe o erro persistir, consulte o administrador!");
					}

				} else {
					JOptionPane.showMessageDialog(isto, "Sem posições na Fila de desembarque para alternar");

				}

			}
		});
		btnSubirFilaDesembarque.setBackground(Color.WHITE);
		btnSubirFilaDesembarque.setIcon(new ImageIcon(TelaFila.class.getResource("/imagens/seta-para-cima.png")));

		btnDescerFilaDesembarque = new JButton("");
		panel_6.add(btnDescerFilaDesembarque, "cell 0 1");
		btnDescerFilaDesembarque.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int rowSel = tabela_desembarque.getSelectedRow();// pega o indice da linha na tabela
				int indiceDaLinha = tabela_desembarque.getRowSorter().convertRowIndexToModel(rowSel);

				CadastroFilaMovimento unidade_selecionada = modelo_fila_desembarque.getValue(indiceDaLinha);
				CadastroFilaMovimento unidade_anterior = modelo_fila_desembarque.getValue(indiceDaLinha + 1);

				if (unidade_anterior != null) {

					GerenciarBancoFilaMovimento gerenciar = new GerenciarBancoFilaMovimento();

					int id_pivo = gerenciar.getMaxId();

					if (id_pivo > 0) {

						int id_unidade_selecionada = unidade_selecionada.getId();
						int id_unidade_anterior = unidade_anterior.getId();

						// 1 passo
						boolean mudar_id_unidade_anterior = gerenciar.atualizarId(id_pivo, id_unidade_anterior);
						// colocar a unidade anterior no ultima posicao da fila
						if (mudar_id_unidade_anterior) {

							// 2 passo
							// colocar a unidade selecionada na posicao acima
							boolean mudar_id_unidade_selecionada = gerenciar.atualizarId(id_unidade_anterior,
									id_unidade_selecionada);

							if (mudar_id_unidade_selecionada) {

								// 3 passo
								// colocar a unidade anterior na posicao abaixo
								boolean mudar_id_unidade_pivo_para_unidade_selecionada = gerenciar
										.atualizarId(id_unidade_selecionada, id_pivo);
								if (mudar_id_unidade_pivo_para_unidade_selecionada) {
									JOptionPane.showMessageDialog(isto, "Fila de desembarque reorganizada");
									pesquisar_fila_desembarque();
								} else {
									JOptionPane.showMessageDialog(isto,
											"Erro grave ao reorganizar a Fila de desembarque, não tente novamente!\nConsulte o administrador para que seja restaurado a fila!");
									pesquisar_fila_desembarque();

								}

							} else {
								// volte o id anterior da unidade anterior
								boolean voltar_estado_original = gerenciar.atualizarId(id_unidade_anterior, id_pivo);
								if (voltar_estado_original) {
									JOptionPane.showMessageDialog(isto,
											"Erro ao reorganizar a Fila de desembarque, tente novamente!\nSe o erro persistir, consulte o administrador!");

								} else {
									JOptionPane.showMessageDialog(isto,
											"Erro grave reorganizar a Fila de desembarque, não tente novamente!\nConsulte o administrador para que seja restaurado a fila!");

								}
							}

						} else {
							JOptionPane.showMessageDialog(isto,
									"Erro ao reorganizar a Fila de desembarque, não foi possivel mudar a posição, tente novamente!\nSe o erro persistir, consulte o administrador!");

						}
					} else {
						JOptionPane.showMessageDialog(isto,
								"Erro ao reorganizar a Fila de desembarque, pivo não encontrado, tente novamente!\nSe o erro persistir, consulte o administrador!");
					}

				} else {
					JOptionPane.showMessageDialog(isto, "Sem posições para alternar na Fila de desembarque");

				}

			}
		});
		btnDescerFilaDesembarque.setBackground(Color.WHITE);
		btnDescerFilaDesembarque.setIcon(new ImageIcon(TelaFila.class.getResource("/imagens/seta-para-baixo.png")));

		btnTrocarDesembarque = new JButton("");
		btnTrocarDesembarque.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ArrayList<CadastroFilaMovimento> unidades_selecionadas = new ArrayList<>();
				int linhas_selecionadas[] = tabela_desembarque.getSelectedRows();// pega o indice da linha na tabela

				for (int i = 0; i < linhas_selecionadas.length; i++) {

					int indice = linhas_selecionadas[i];//
					int indexRowModel = tabela_desembarque.getRowSorter().convertRowIndexToModel(indice);

					CadastroFilaMovimento rom = lista_fila_desembarque.get(indexRowModel);
					unidades_selecionadas.add(rom);
				}

				if (unidades_selecionadas.size() != 2) {
					JOptionPane.showMessageDialog(isto, "Para efetuar a troca rápida, selecione somente duas posições");

				} else {

					CadastroFilaMovimento unidade_selecionada = unidades_selecionadas.get(0);
					CadastroFilaMovimento unidade_anterior = unidades_selecionadas.get(1);
					if (unidade_anterior != null) {

						GerenciarBancoFilaMovimento gerenciar = new GerenciarBancoFilaMovimento();

						int id_pivo = gerenciar.getMaxId();

						if (id_pivo > 0) {

							int id_unidade_selecionada = unidade_selecionada.getId();
							int id_unidade_anterior = unidade_anterior.getId();

							// 1 passo
							boolean mudar_id_unidade_anterior = gerenciar.atualizarId(id_pivo, id_unidade_anterior);
							// colocar a unidade anterior no ultima posicao da fila
							if (mudar_id_unidade_anterior) {

								// 2 passo
								// colocar a unidade selecionada na posicao acima
								boolean mudar_id_unidade_selecionada = gerenciar.atualizarId(id_unidade_anterior,
										id_unidade_selecionada);

								if (mudar_id_unidade_selecionada) {

									// 3 passo
									// colocar a unidade anterior na posicao abaixo
									boolean mudar_id_unidade_pivo_para_unidade_selecionada = gerenciar
											.atualizarId(id_unidade_selecionada, id_pivo);
									if (mudar_id_unidade_pivo_para_unidade_selecionada) {
										JOptionPane.showMessageDialog(isto, "Fila de Embarque reorganizada");
										pesquisar_fila_desembarque();
									} else {
										JOptionPane.showMessageDialog(isto,
												"Erro grave ao reorganizar a fila de embarque, não tente novamente!\nConsulte o administrador para que seja restaurado a fila!");
										pesquisar_fila_desembarque();

									}

								} else {
									// volte o id anterior da unidade anterior
									boolean voltar_estado_original = gerenciar.atualizarId(id_unidade_anterior,
											id_pivo);
									if (voltar_estado_original) {
										JOptionPane.showMessageDialog(isto,
												"Erro ao reorganizar a fila de embarque, tente novamente!\nSe o erro persistir, consulte o administrador!");

									} else {
										JOptionPane.showMessageDialog(isto,
												"Erro grave reorganizar a fila de embarque, não tente novamente!\nConsulte o administrador para que seja restaurado a fila!");

									}
								}

							} else {
								JOptionPane.showMessageDialog(isto,
										"Erro ao reorganizar a fila de embarque, não foi possivel mudar a posição, tente novamente!\nSe o erro persistir, consulte o administrador!");

							}
						} else {
							JOptionPane.showMessageDialog(isto,
									"Erro ao reorganizar a fila de embarque, pivo não encontrado, tente novamente!\nSe o erro persistir, consulte o administrador!");
						}

					} else {
						JOptionPane.showMessageDialog(isto, "Sem posições para alternar na fila de embarque");

					}

				}

			}
		});
		btnTrocarDesembarque.setIcon(new ImageIcon(TelaFila.class.getResource("/imagens/substituir.png")));
		btnTrocarDesembarque.setBackground(Color.WHITE);
		panel_6.add(btnTrocarDesembarque, "cell 0 2");

		panel_2 = new JPanel();
		panel_2.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel_2.setBackground(Color.WHITE);
		painelDescarga.add(panel_2, "flowx,cell 1 3,grow");
		panel_2.setLayout(new MigLayout("",
				"[][][][][][][][][][][][][][][][][grow][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]",
				"[][][][][][grow]"));

		lblNewLabel_7 = new JLabel("Totais:");
		lblNewLabel_7.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_2.add(lblNewLabel_7, "cell 0 1");

		lblTempoMedioEsperaDesembarque = new JLabel("10 horas");
		lblTempoMedioEsperaDesembarque.setFont(new Font("SansSerif", Font.BOLD, 32));
		panel_2.add(lblTempoMedioEsperaDesembarque, "cell 13 1 2 3,alignx center,aligny center");

		btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int rowSel = tabela_desembarque.getSelectedRow();// pega o indice da linha na tabela
				int indiceDaLinha = tabela_desembarque.getRowSorter().convertRowIndexToModel(rowSel);

				CadastroFilaMovimento unidade_selecionada = modelo_fila_desembarque.getValue(indiceDaLinha);

				if (unidade_selecionada.getStatus() == -1) {
					JOptionPane.showMessageDialog(isto, "Posição na fila está cancelada");

				} else {

					CadastroFilaMovimento unidade_completa = new GerenciarBancoFilaMovimento()
							.getUnidadeDesembarque(unidade_selecionada.getId());

					TelaFilaCadastrarMovimentoDesembarque tela = new TelaFilaCadastrarMovimentoDesembarque(1,
							unidade_completa, isto);
					tela.setVisible(true);
				}
			}
		});

		btnNewButton = new JButton("EXPORTAR");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ArrayList<CadastroFilaMovimento> unidades_selecionadas = new ArrayList<>();
				int linhas_selecionadas[] = tabela_desembarque.getSelectedRows();// pega o indice da linha na tabela

				for (int i = 0; i < linhas_selecionadas.length; i++) {

					int indice = linhas_selecionadas[i];//
					int indexRowModel = tabela_desembarque.getRowSorter().convertRowIndexToModel(indice);

					CadastroFilaMovimento rom = lista_fila_desembarque.get(indexRowModel);
					unidades_selecionadas.add(rom);
				}

				TelaEscolhaRelatorioFila escolha_opcoes = new TelaEscolhaRelatorioFila(unidades_selecionadas, 0, isto);
				escolha_opcoes.setVisible(true);

			}
		});
		btnNewButton.setBackground(new Color(0, 0, 153));
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_2.add(btnNewButton, "cell 16 1 30 1,alignx center,growy");
		btnEditar.setForeground(Color.BLACK);
		btnEditar.setFont(new Font("Tahoma", Font.BOLD, 22));
		btnEditar.setBackground(Color.ORANGE);
		panel_2.add(btnEditar, "cell 46 1");

		btnMarcarVez = new JButton("Marcar");
		panel_2.add(btnMarcarVez, "cell 48 1,alignx right");
		btnMarcarVez.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaFilaCadastrarMovimentoDesembarque tela = new TelaFilaCadastrarMovimentoDesembarque(0, null, isto);
				tela.setVisible(true);

			}
		});
		btnMarcarVez.setBackground(new Color(0, 51, 0));
		btnMarcarVez.setForeground(Color.WHITE);
		btnMarcarVez.setFont(new Font("Tahoma", Font.BOLD, 22));

		lblNewLabel_11 = new JLabel("aaaaa");
		lblNewLabel_11.setOpaque(true);
		lblNewLabel_11.setBackground(new Color(255, 102, 0));
		lblNewLabel_11.setForeground(new Color(255, 102, 0));
		panel_2.add(lblNewLabel_11, "cell 1 2,growx");

		lblNewLabel_8 = new JLabel("Em Fila:");
		lblNewLabel_8.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_2.add(lblNewLabel_8, "cell 2 2,alignx right");

		lblEmFila = new JLabel("0 na fila");
		lblEmFila.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblEmFila.setForeground(Color.BLACK);
		panel_2.add(lblEmFila, "cell 3 2");

		lblAsd1 = new JLabel("Tempo Médio de Espera:");
		lblAsd1.setForeground(Color.BLACK);
		lblAsd1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_2.add(lblAsd1, "cell 12 2");

		lblNewLabel_12 = new JLabel("aaaaa");
		lblNewLabel_12.setOpaque(true);
		lblNewLabel_12.setForeground(Color.YELLOW);
		lblNewLabel_12.setBackground(Color.YELLOW);
		panel_2.add(lblNewLabel_12, "cell 1 3");

		lblNewLabel_9 = new JLabel("Entrada:");
		lblNewLabel_9.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_2.add(lblNewLabel_9, "cell 2 3");

		lblEmDesembarque = new JLabel("1 em Desembarque");
		lblEmDesembarque.setForeground(Color.BLACK);
		lblEmDesembarque.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_2.add(lblEmDesembarque, "cell 3 3");

		lblNewLabel_13 = new JLabel("aaaaa");
		lblNewLabel_13.setOpaque(true);
		lblNewLabel_13.setForeground(new Color(0, 51, 0));
		lblNewLabel_13.setBackground(new Color(0, 51, 0));
		panel_2.add(lblNewLabel_13, "cell 1 4");

		lblNewLabel_10 = new JLabel("Saída:");
		lblNewLabel_10.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_2.add(lblNewLabel_10, "cell 2 4,alignx right");

		lblDesembarcados = new JLabel("0 já Desembarcados");
		lblDesembarcados.setForeground(Color.BLACK);
		lblDesembarcados.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_2.add(lblDesembarcados, "cell 3 4");

		lblNewLabel_14 = new JLabel("Total:");
		lblNewLabel_14.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_2.add(lblNewLabel_14, "cell 2 5,alignx right");

		lblTotal = new JLabel("0 Caminhões");
		lblTotal.setForeground(Color.BLACK);
		lblTotal.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_2.add(lblTotal, "cell 3 5");

		lblNewLabel_15 = new JLabel("Unidades na Frente:");
		lblNewLabel_15.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_2.add(lblNewLabel_15, "cell 12 5,alignx right");

		lblUnidadesNaFrente = new JLabel("0 Caminhões");
		lblUnidadesNaFrente.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_2.add(lblUnidadesNaFrente, "cell 14 5");

		lblNewLabel_17 = new JLabel("Motivo Cancelamento:");
		lblNewLabel_17.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_2.add(lblNewLabel_17, "cell 15 5");

		lblMotivo = new JTextArea();
		lblMotivo.setWrapStyleWord(true);
		lblMotivo.setLineWrap(true);
		panel_2.add(lblMotivo, "cell 16 5 33 1,grow");

		painelCarga.setBackground(new Color(255, 255, 255));
		painelPrincipal.addTab("Fila de Embarque", painelCarga);
		painelCarga.setLayout(new MigLayout("", "[grow]", "[][grow][grow][grow][][]"));
		sorterEmbarque = new TableRowSorter<FilaEmbarqueTableModel>(modelo_fila_embarque);

		lblNewLabel_2 = new JLabel("Fila para o Embarque");
		lblNewLabel_2.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 25));
		painelCarga.add(lblNewLabel_2, "cell 0 0,alignx center");

		panel_5 = new JPanel();
		panel_5.setBackground(Color.WHITE);
		painelCarga.add(panel_5, "cell 0 1,grow");
		panel_5.setLayout(new MigLayout("", "[][][][][][][][][][][][]", "[][][][]"));

		lblNewLabel_3Embarque_1 = new JLabel("Transportadora:");
		panel_5.add(lblNewLabel_3Embarque_1, "cell 0 0,alignx right");
		lblNewLabel_3Embarque_1.setFont(new Font("SansSerif", Font.PLAIN, 16));

		entTransportadoraEmbarque = new JTextField();
		entTransportadoraEmbarque.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrarEmbarque();
			}
		});
		panel_5.add(entTransportadoraEmbarque, "cell 1 0");
		entTransportadoraEmbarque.setFont(new Font("SansSerif", Font.BOLD, 16));
		entTransportadoraEmbarque.setColumns(10);

		JLabel lblNewLabel_3Embarque = new JLabel("Motorista:");
		panel_5.add(lblNewLabel_3Embarque, "cell 2 0,alignx right");
		lblNewLabel_3Embarque.setFont(new Font("SansSerif", Font.PLAIN, 16));

		entMotoristaEmbarque = new JTextField();
		panel_5.add(entMotoristaEmbarque, "cell 3 0");
		entMotoristaEmbarque.setFont(new Font("SansSerif", Font.BOLD, 16));
		entMotoristaEmbarque.setColumns(10);
		entMotoristaEmbarque.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrarEmbarque();
			}
		});

		JLabel lblNewLabel_4Embarque = new JLabel("Placa:");
		panel_5.add(lblNewLabel_4Embarque, "cell 4 0");
		lblNewLabel_4Embarque.setFont(new Font("SansSerif", Font.PLAIN, 16));

		entPlacaEmbarque = new JTextField();
		panel_5.add(entPlacaEmbarque, "cell 5 0");
		entPlacaEmbarque.setFont(new Font("SansSerif", Font.BOLD, 16));
		entPlacaEmbarque.setColumns(10);
		entPlacaEmbarque.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrarEmbarque();
			}
		});

		btnFiltrar_1 = new JButton("filtrar");
		btnFiltrar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filtrarEmbarque();
			}

		});

		lblNewLabel_29 = new JLabel("Status:");
		lblNewLabel_29.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_5.add(lblNewLabel_29, "cell 7 0,alignx trailing");

		cbStatusEmbarque = new JComboBox();
		cbStatusEmbarque.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				try {
					filtrarEmbarque();
				} catch (Exception y) {

				}
			}
		});
		cbStatusEmbarque.addItem("TODOS");
		cbStatusEmbarque.addItem("EM FILA");
		cbStatusEmbarque.addItem("ENTRADA");
		cbStatusEmbarque.addItem("SAÍDA");
		cbStatusEmbarque.addItem("CANCELADO");
		panel_5.add(cbStatusEmbarque, "cell 8 0 2 1,growx");

		lblNewLabel_3Embarque_2 = new JLabel("Cliente:");
		lblNewLabel_3Embarque_2.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_5.add(lblNewLabel_3Embarque_2, "cell 0 1,alignx trailing");

		entClienteDestinatario = new JTextField();
		entClienteDestinatario.setFont(new Font("SansSerif", Font.BOLD, 16));
		entClienteDestinatario.setColumns(10);
		panel_5.add(entClienteDestinatario, "cell 1 1,growx");

		JLabel lblNewLabel_6Produto = new JLabel("Produto:");
		panel_5.add(lblNewLabel_6Produto, "cell 2 1,alignx right");
		lblNewLabel_6Produto.setFont(new Font("SansSerif", Font.PLAIN, 16));

		entProdutoEmbarque = new JTextField();
		panel_5.add(entProdutoEmbarque, "cell 3 1");
		entProdutoEmbarque.setFont(new Font("SansSerif", Font.BOLD, 16));
		entProdutoEmbarque.setColumns(10);
		entProdutoEmbarque.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrarEmbarque();
			}
		});
		btnFiltrar_1.setForeground(Color.BLACK);
		btnFiltrar_1.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnFiltrar_1.setBackground(Color.ORANGE);
		panel_5.add(btnFiltrar_1, "cell 7 1");

		JButton btnLimparBuscaEmbarque = new JButton("limpar busca");
		panel_5.add(btnLimparBuscaEmbarque, "cell 8 1");
		btnLimparBuscaEmbarque.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparFiltrosEmbarque();
				calcularEmbarque();
			}
		});
		btnLimparBuscaEmbarque.setForeground(Color.BLACK);
		btnLimparBuscaEmbarque.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnLimparBuscaEmbarque.setBackground(Color.RED);

		btnPesquisarEmbarque = new JButton("pesquisar");
		btnPesquisarEmbarque.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisar_fila_embarque();
			}
		});
		btnPesquisarEmbarque.setForeground(Color.WHITE);
		btnPesquisarEmbarque.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnPesquisarEmbarque.setBackground(Color.BLUE);
		panel_5.add(btnPesquisarEmbarque, "cell 9 1 3 1");

		JLabel lblNewLabel_1Periodo = new JLabel("Período:");
		panel_5.add(lblNewLabel_1Periodo, "cell 0 2,alignx right");
		lblNewLabel_1Periodo.setFont(new Font("SansSerif", Font.BOLD, 16));

		JPanel panel_1Embarque = new JPanel();
		panel_5.add(panel_1Embarque, "cell 1 2 3 1");
		panel_1Embarque.setBackground(Color.WHITE);
		panel_1Embarque.setLayout(new MigLayout("", "[][][][][]", "[]"));

		JLabel lblNewLabelDe = new JLabel("De:");
		panel_1Embarque.add(lblNewLabelDe, "cell 0 0");
		lblNewLabelDe.setFont(new Font("SansSerif", Font.BOLD, 16));

		entDataMenorEmbarque = new JTextField();
		panel_1Embarque.add(entDataMenorEmbarque, "cell 1 0");
		entDataMenorEmbarque.setFont(new Font("SansSerif", Font.BOLD, 16));
		entDataMenorEmbarque.setColumns(10);
		entDataMenorEmbarque.setText(new GetData().getData());

		JLabel lblAtAte = new JLabel("Até");
		panel_1Embarque.add(lblAtAte, "cell 2 0");
		lblAtAte.setFont(new Font("SansSerif", Font.BOLD, 16));

		entDataMaiorEmbarque = new JTextField();
		panel_1Embarque.add(entDataMaiorEmbarque, "cell 3 0");
		entDataMaiorEmbarque.setFont(new Font("SansSerif", Font.BOLD, 16));
		entDataMaiorEmbarque.setColumns(10);
		entDataMaiorEmbarque.setText(new GetData().getData());

		panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		painelCarga.add(panel_3, "cell 0 2,grow");
		panel_3.setLayout(
				new MigLayout("", "[grow][][][][][][][][][][][][][][][][][][][][][][]", "[][grow][][grow][grow]"));

		tabela_embarque = new JTable(modelo_fila_embarque);
		tabela_embarque.setRowSorter(sorterEmbarque);
		StatusFilaRenderEmbarque rendererEmbarque = new StatusFilaRenderEmbarque();
		tabela_embarque.setDefaultRenderer(Object.class, rendererEmbarque);
		tabela_embarque.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		tabela_embarque.setRowHeight(30);

		tabela_embarque.getColumnModel().getColumn(0).setPreferredWidth(40); // id
		tabela_embarque.getColumnModel().getColumn(1).setPreferredWidth(180); // data
		tabela_embarque.getColumnModel().getColumn(2).setPreferredWidth(100); // hora
		tabela_embarque.getColumnModel().getColumn(3).setPreferredWidth(80); // hora
		tabela_embarque.getColumnModel().getColumn(4).setPreferredWidth(80); // hora
		tabela_embarque.getColumnModel().getColumn(5).setPreferredWidth(80); // hora

		tabela_embarque.getColumnModel().getColumn(6).setPreferredWidth(200); // transportadora
		tabela_embarque.getColumnModel().getColumn(7).setPreferredWidth(200); // motorista

		tabela_embarque.getColumnModel().getColumn(8).setPreferredWidth(100); // placa
		tabela_embarque.getColumnModel().getColumn(9).setPreferredWidth(200); // produtor
		tabela_embarque.getColumnModel().getColumn(10).setPreferredWidth(150); // produto

		tabela_embarque.addMouseListener(new java.awt.event.MouseAdapter() {
			// Importe a classe java.awt.event.MouseEvent
			public void mouseClicked(MouseEvent e) {
				// Se o botão direito do mouse foi pressionado
				if (e.getButton() == MouseEvent.BUTTON1) {
					// Exibe o popup menu na posição do mouse.

					int rowSel = tabela_embarque.getSelectedRow();// pega o indice da linha na tabela
					int indiceDaLinha = tabela_embarque.getRowSorter().convertRowIndexToModel(rowSel);
					CadastroFilaMovimento unidade = modelo_fila_embarque.getValue(indiceDaLinha);

					// lblUnidadesNaFrente.setText(unidadesNaFrenteDesembarque(unidade) + "
					// Caminhões ");
					// lblMotivo.setText(unidade.getMotivo());
				}

			}
		});

		scrollPaneEmbarque = new JScrollPane(tabela_embarque);
		panel_3.add(scrollPaneEmbarque, "cell 0 0 21 2,grow");

		panel_7 = new JPanel();
		panel_7.setBackground(Color.WHITE);
		panel_3.add(panel_7, "cell 21 1 2 1,growx,aligny center");
		panel_7.setLayout(new MigLayout("", "[]", "[][][]"));

		btnSubirFilaEmbarque = new JButton("");
		panel_7.add(btnSubirFilaEmbarque, "cell 0 0");
		btnSubirFilaEmbarque.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int rowSel = tabela_embarque.getSelectedRow();// pega o indice da linha na tabela
				int indiceDaLinha = tabela_embarque.getRowSorter().convertRowIndexToModel(rowSel);

				CadastroFilaMovimento unidade_selecionada = modelo_fila_embarque.getValue(indiceDaLinha);
				CadastroFilaMovimento unidade_anterior = modelo_fila_embarque.getValue(indiceDaLinha - 1);
				if (unidade_anterior != null) {

					GerenciarBancoFilaMovimento gerenciar = new GerenciarBancoFilaMovimento();

					int id_pivo = gerenciar.getMaxId();

					if (id_pivo > 0) {

						int id_unidade_selecionada = unidade_selecionada.getId();
						int id_unidade_anterior = unidade_anterior.getId();

						// 1 passo
						boolean mudar_id_unidade_anterior = gerenciar.atualizarId(id_pivo, id_unidade_anterior);
						// colocar a unidade anterior no ultima posicao da fila
						if (mudar_id_unidade_anterior) {

							// 2 passo
							// colocar a unidade selecionada na posicao acima
							boolean mudar_id_unidade_selecionada = gerenciar.atualizarId(id_unidade_anterior,
									id_unidade_selecionada);

							if (mudar_id_unidade_selecionada) {

								// 3 passo
								// colocar a unidade anterior na posicao abaixo
								boolean mudar_id_unidade_pivo_para_unidade_selecionada = gerenciar
										.atualizarId(id_unidade_selecionada, id_pivo);
								if (mudar_id_unidade_pivo_para_unidade_selecionada) {
									JOptionPane.showMessageDialog(isto, "Fila de Embarque reorganizada");
									pesquisar_fila_desembarque();
								} else {
									JOptionPane.showMessageDialog(isto,
											"Erro grave ao reorganizar a fila de embarque, não tente novamente!\nConsulte o administrador para que seja restaurado a fila!");
									pesquisar_fila_desembarque();

								}

							} else {
								// volte o id anterior da unidade anterior
								boolean voltar_estado_original = gerenciar.atualizarId(id_unidade_anterior, id_pivo);
								if (voltar_estado_original) {
									JOptionPane.showMessageDialog(isto,
											"Erro ao reorganizar a fila de embarque, tente novamente!\nSe o erro persistir, consulte o administrador!");

								} else {
									JOptionPane.showMessageDialog(isto,
											"Erro grave reorganizar a fila de embarque, não tente novamente!\nConsulte o administrador para que seja restaurado a fila!");

								}
							}

						} else {
							JOptionPane.showMessageDialog(isto,
									"Erro ao reorganizar a fila de embarque, não foi possivel mudar a posição, tente novamente!\nSe o erro persistir, consulte o administrador!");

						}
					} else {
						JOptionPane.showMessageDialog(isto,
								"Erro ao reorganizar a fila de embarque, pivo não encontrado, tente novamente!\nSe o erro persistir, consulte o administrador!");
					}

				} else {
					JOptionPane.showMessageDialog(isto, "Sem posições para alternar na fila de embarque");

				}

			}
		});
		btnSubirFilaEmbarque.setIcon(new ImageIcon(TelaFila.class.getResource("/imagens/seta-para-cima.png")));
		btnSubirFilaEmbarque.setBackground(Color.WHITE);

		btnDescerFilaEmbarque = new JButton("");
		panel_7.add(btnDescerFilaEmbarque, "cell 0 1");
		btnDescerFilaEmbarque.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int rowSel = tabela_embarque.getSelectedRow();// pega o indice da linha na tabela
				int indiceDaLinha = tabela_embarque.getRowSorter().convertRowIndexToModel(rowSel);

				CadastroFilaMovimento unidade_selecionada = modelo_fila_embarque.getValue(indiceDaLinha);
				CadastroFilaMovimento unidade_anterior = modelo_fila_embarque.getValue(indiceDaLinha + 1);

				if (unidade_anterior != null) {

					GerenciarBancoFilaMovimento gerenciar = new GerenciarBancoFilaMovimento();

					int id_pivo = gerenciar.getMaxId();

					if (id_pivo > 0) {

						int id_unidade_selecionada = unidade_selecionada.getId();
						int id_unidade_anterior = unidade_anterior.getId();

						// 1 passo
						boolean mudar_id_unidade_anterior = gerenciar.atualizarId(id_pivo, id_unidade_anterior);
						// colocar a unidade anterior no ultima posicao da fila
						if (mudar_id_unidade_anterior) {

							// 2 passo
							// colocar a unidade selecionada na posicao acima
							boolean mudar_id_unidade_selecionada = gerenciar.atualizarId(id_unidade_anterior,
									id_unidade_selecionada);

							if (mudar_id_unidade_selecionada) {

								// 3 passo
								// colocar a unidade anterior na posicao abaixo
								boolean mudar_id_unidade_pivo_para_unidade_selecionada = gerenciar
										.atualizarId(id_unidade_selecionada, id_pivo);
								if (mudar_id_unidade_pivo_para_unidade_selecionada) {
									JOptionPane.showMessageDialog(isto, "Fila de embarque reorganizada");
									pesquisar_fila_desembarque();
								} else {
									JOptionPane.showMessageDialog(isto,
											"Erro grave ao reorganizar a fila de embarque, não tente novamente!\nConsulte o administrador para que seja restaurado a fila!");
									pesquisar_fila_desembarque();

								}

							} else {
								// volte o id anterior da unidade anterior
								boolean voltar_estado_original = gerenciar.atualizarId(id_unidade_anterior, id_pivo);
								if (voltar_estado_original) {
									JOptionPane.showMessageDialog(isto,
											"Erro ao reorganizar a fila de embarque, tente novamente!\nSe o erro persistir, consulte o administrador!");

								} else {
									JOptionPane.showMessageDialog(isto,
											"Erro grave reorganizar a fila de embarque, não tente novamente!\nConsulte o administrador para que seja restaurado a fila!");

								}
							}

						} else {
							JOptionPane.showMessageDialog(isto,
									"Erro ao reorganizar a fila de embarque, não foi possivel mudar a posição, tente novamente!\nSe o erro persistir, consulte o administrador!");

						}
					} else {
						JOptionPane.showMessageDialog(isto,
								"Erro ao reorganizar a fila de embarque, pivo não encontrado, tente novamente!\nSe o erro persistir, consulte o administrador!");
					}

				} else {
					JOptionPane.showMessageDialog(isto, "Sem posições para alternar na fila de embarque");

				}

			}
		});
		btnDescerFilaEmbarque.setIcon(new ImageIcon(TelaFila.class.getResource("/imagens/seta-para-baixo.png")));
		btnDescerFilaEmbarque.setBackground(Color.WHITE);

		btnTrocarEmbarque = new JButton("");
		btnTrocarEmbarque.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ArrayList<CadastroFilaMovimento> unidades_selecionadas = new ArrayList<>();
				int linhas_selecionadas[] = tabela_embarque.getSelectedRows();// pega o indice da linha na tabela

				for (int i = 0; i < linhas_selecionadas.length; i++) {

					int indice = linhas_selecionadas[i];//
					int indexRowModel = tabela_embarque.getRowSorter().convertRowIndexToModel(indice);

					CadastroFilaMovimento rom = lista_fila_embarque.get(indexRowModel);
					unidades_selecionadas.add(rom);
				}

				if (unidades_selecionadas.size() != 2) {
					JOptionPane.showMessageDialog(isto, "Para efetuar a troca rápida, selecione somente duas posições");

				} else {

					CadastroFilaMovimento unidade_selecionada = unidades_selecionadas.get(0);
					CadastroFilaMovimento unidade_anterior = unidades_selecionadas.get(1);
					if (unidade_anterior != null) {

						GerenciarBancoFilaMovimento gerenciar = new GerenciarBancoFilaMovimento();

						int id_pivo = gerenciar.getMaxId();

						if (id_pivo > 0) {

							int id_unidade_selecionada = unidade_selecionada.getId();
							int id_unidade_anterior = unidade_anterior.getId();

							// 1 passo
							boolean mudar_id_unidade_anterior = gerenciar.atualizarId(id_pivo, id_unidade_anterior);
							// colocar a unidade anterior no ultima posicao da fila
							if (mudar_id_unidade_anterior) {

								// 2 passo
								// colocar a unidade selecionada na posicao acima
								boolean mudar_id_unidade_selecionada = gerenciar.atualizarId(id_unidade_anterior,
										id_unidade_selecionada);

								if (mudar_id_unidade_selecionada) {

									// 3 passo
									// colocar a unidade anterior na posicao abaixo
									boolean mudar_id_unidade_pivo_para_unidade_selecionada = gerenciar
											.atualizarId(id_unidade_selecionada, id_pivo);
									if (mudar_id_unidade_pivo_para_unidade_selecionada) {
										JOptionPane.showMessageDialog(isto, "Fila de Embarque reorganizada");
										pesquisar_fila_embarque();
									} else {
										JOptionPane.showMessageDialog(isto,
												"Erro grave ao reorganizar a fila de embarque, não tente novamente!\nConsulte o administrador para que seja restaurado a fila!");
										pesquisar_fila_embarque();

									}

								} else {
									// volte o id anterior da unidade anterior
									boolean voltar_estado_original = gerenciar.atualizarId(id_unidade_anterior,
											id_pivo);
									if (voltar_estado_original) {
										JOptionPane.showMessageDialog(isto,
												"Erro ao reorganizar a fila de embarque, tente novamente!\nSe o erro persistir, consulte o administrador!");

									} else {
										JOptionPane.showMessageDialog(isto,
												"Erro grave reorganizar a fila de embarque, não tente novamente!\nConsulte o administrador para que seja restaurado a fila!");

									}
								}

							} else {
								JOptionPane.showMessageDialog(isto,
										"Erro ao reorganizar a fila de embarque, não foi possivel mudar a posição, tente novamente!\nSe o erro persistir, consulte o administrador!");

							}
						} else {
							JOptionPane.showMessageDialog(isto,
									"Erro ao reorganizar a fila de embarque, pivo não encontrado, tente novamente!\nSe o erro persistir, consulte o administrador!");
						}

					} else {
						JOptionPane.showMessageDialog(isto, "Sem posições para alternar na fila de embarque");

					}

				}

			}
		});
		btnTrocarEmbarque.setIcon(new ImageIcon(TelaFila.class.getResource("/imagens/substituir.png")));
		btnTrocarEmbarque.setBackground(Color.WHITE);
		panel_7.add(btnTrocarEmbarque, "cell 0 2");

		panel_4 = new JPanel();
		panel_4.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel_4.setBackground(Color.WHITE);
		painelCarga.add(panel_4, "cell 0 3,grow");
		panel_4.setLayout(new MigLayout("", "[][][][][][][][][][][][][][][][][][][][][][][][]", "[][][][][][]"));

		lblNewLabel_18 = new JLabel("Totais:");
		lblNewLabel_18.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_4.add(lblNewLabel_18, "cell 0 0");

		btnEditarEmbarque = new JButton("Editar");
		btnEditarEmbarque.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rowSel = tabela_embarque.getSelectedRow();// pega o indice da linha na tabela
				int indiceDaLinha = tabela_embarque.getRowSorter().convertRowIndexToModel(rowSel);

				CadastroFilaMovimento unidade_selecionada = modelo_fila_embarque.getValue(indiceDaLinha);

				if (unidade_selecionada.getStatus() == -1) {
					JOptionPane.showMessageDialog(isto, "Posição na fila está cancelada");

				} else {

					CadastroFilaMovimento unidade_completa = new GerenciarBancoFilaMovimento()
							.getUnidadeEmbarque(unidade_selecionada.getId());

					TelaFilaCadastrarMovimentoEmbarque tela = new TelaFilaCadastrarMovimentoEmbarque(1,
							unidade_completa, isto);
					tela.setVisible(true);
				}
			}
		});

		btnNewButton_1 = new JButton("EXPORTAR");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<CadastroFilaMovimento> unidades_selecionadas = new ArrayList<>();
				int linhas_selecionadas[] = tabela_embarque.getSelectedRows();// pega o indice da linha na tabela

				for (int i = 0; i < linhas_selecionadas.length; i++) {

					int indice = linhas_selecionadas[i];//
					int indexRowModel = tabela_embarque.getRowSorter().convertRowIndexToModel(indice);

					CadastroFilaMovimento rom = lista_fila_embarque.get(indexRowModel);
					unidades_selecionadas.add(rom);
				}

				TelaEscolhaRelatorioFila escolha_opcoes = new TelaEscolhaRelatorioFila(unidades_selecionadas, 1, isto);
				escolha_opcoes.setVisible(true);
			}
		});
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnNewButton_1.setBackground(new Color(0, 0, 153));
		panel_4.add(btnNewButton_1, "cell 17 0 5 1,alignx center");
		btnEditarEmbarque.setForeground(Color.BLACK);
		btnEditarEmbarque.setFont(new Font("Tahoma", Font.BOLD, 22));
		btnEditarEmbarque.setBackground(Color.ORANGE);
		panel_4.add(btnEditarEmbarque, "cell 22 0");

		btnMarcarVezEmbarque = new JButton("Marcar");
		btnMarcarVezEmbarque.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaFilaCadastrarMovimentoEmbarque tela = new TelaFilaCadastrarMovimentoEmbarque(0, null, isto);
				tela.setVisible(true);
			}
		});
		btnMarcarVezEmbarque.setForeground(Color.WHITE);
		btnMarcarVezEmbarque.setFont(new Font("Tahoma", Font.BOLD, 22));
		btnMarcarVezEmbarque.setBackground(new Color(0, 51, 0));
		panel_4.add(btnMarcarVezEmbarque, "cell 23 0");

		lblNewLabel_22 = new JLabel("aaaaa");
		lblNewLabel_22.setOpaque(true);
		lblNewLabel_22.setForeground(new Color(255, 102, 0));
		lblNewLabel_22.setBackground(new Color(255, 102, 0));
		panel_4.add(lblNewLabel_22, "cell 1 1");

		lblNewLabel_19 = new JLabel("Em Fila:");
		lblNewLabel_19.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_4.add(lblNewLabel_19, "cell 2 1");

		lblEmFilaEmbarque = new JLabel("0 na Fila");
		lblEmFilaEmbarque.setForeground(Color.BLACK);
		lblEmFilaEmbarque.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_4.add(lblEmFilaEmbarque, "cell 3 1");

		lblAsd1_1 = new JLabel("Tempo Médio de Espera:");
		lblAsd1_1.setForeground(Color.BLACK);
		lblAsd1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_4.add(lblAsd1_1, "cell 5 1");

		lblTempoMedioEsperaEmbarque = new JLabel("00:00 horas");
		lblTempoMedioEsperaEmbarque.setFont(new Font("SansSerif", Font.BOLD, 32));
		panel_4.add(lblTempoMedioEsperaEmbarque, "cell 6 0 1 3");

		lblNewLabel_23 = new JLabel("aaaaa");
		lblNewLabel_23.setOpaque(true);
		lblNewLabel_23.setForeground(Color.YELLOW);
		lblNewLabel_23.setBackground(Color.YELLOW);
		panel_4.add(lblNewLabel_23, "cell 1 2");

		lblNewLabel_20 = new JLabel("Entrada:");
		lblNewLabel_20.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_4.add(lblNewLabel_20, "cell 2 2");

		lblEmEmbarque = new JLabel("0 em Embarque");
		lblEmEmbarque.setForeground(Color.BLACK);
		lblEmEmbarque.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_4.add(lblEmEmbarque, "cell 3 2");

		lblNewLabel_24 = new JLabel("aaaaa");
		lblNewLabel_24.setOpaque(true);
		lblNewLabel_24.setForeground(new Color(0, 51, 0));
		lblNewLabel_24.setBackground(new Color(0, 51, 0));
		panel_4.add(lblNewLabel_24, "cell 1 3");

		lblNewLabel_21 = new JLabel("Saída:");
		lblNewLabel_21.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_4.add(lblNewLabel_21, "cell 2 3,alignx right");

		lblEmbarcados = new JLabel("0 já Embarcados");
		lblEmbarcados.setForeground(Color.BLACK);
		lblEmbarcados.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_4.add(lblEmbarcados, "cell 3 3");

		lblNewLabel_25 = new JLabel("Unidades na Frente:");
		lblNewLabel_25.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_4.add(lblNewLabel_25, "cell 5 3,alignx right");

		lblUnidadesNaFrenteEmbarque = new JLabel("0 Caminhões");
		lblUnidadesNaFrenteEmbarque.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_4.add(lblUnidadesNaFrenteEmbarque, "cell 6 3");

		lblNewLabel_26 = new JLabel("Motivo Cancelamento:");
		lblNewLabel_26.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_4.add(lblNewLabel_26, "cell 7 3");

		lblMotivoEmbarque = new JLabel("");
		lblMotivoEmbarque.setFont(new Font("SansSerif", Font.BOLD, 12));
		panel_4.add(lblMotivoEmbarque, "cell 8 3");

		lblNewLabel_27 = new JLabel("Total:");
		lblNewLabel_27.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_4.add(lblNewLabel_27, "cell 2 4,alignx right");

		lblTotalEmbarque = new JLabel("0 Caminhões");
		lblTotalEmbarque.setForeground(Color.BLACK);
		lblTotalEmbarque.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_4.add(lblTotalEmbarque, "cell 3 4");

		this.setContentPane(painelPrincipal);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		this.setLocationRelativeTo(window);

		pesquisar_fila_desembarque();
		pesquisar_fila_embarque();

		setMenuDesembarque();
		setMenuEmbarque();
	}

	public int unidadesNaFrenteDesembarque(CadastroFilaMovimento unidade) {
		int unidades_na_frente = 0;
		for (CadastroFilaMovimento unidade_na_tabela : modelo_fila_desembarque.getValues()) {

			if (!unidade_na_tabela.equals(unidade)) {
				if (unidade_na_tabela.getStatus() == 0) {
					unidades_na_frente++;
				}
			} else {
				break;
			}
		}

		return unidades_na_frente;
	}

	public int unidadesNaFrenteEmbarque(CadastroFilaMovimento unidade) {
		int unidades_na_frente = 0;
		for (CadastroFilaMovimento unidade_na_tabela : modelo_fila_embarque.getValues()) {

			if (!unidade_na_tabela.equals(unidade)) {
				if (unidade_na_tabela.getStatus() == 0) {
					unidades_na_frente++;
				}
			} else {
				break;
			}
		}

		return unidades_na_frente;
	}

	public int unidadesNaFrenteDesembarque() {
		int unidades_na_frente = 0;
		for (CadastroFilaMovimento unidade_na_tabela : modelo_fila_desembarque.getValues()) {

			if (unidade_na_tabela.getStatus() == 0) {
				unidades_na_frente++;
			}

		}
		unidades_na_frente--;
		return unidades_na_frente;
	}

	public int unidadesNaFrenteEmbarque() {
		int unidades_na_frente = 0;
		for (CadastroFilaMovimento unidade_na_tabela : modelo_fila_embarque.getValues()) {

			if (unidade_na_tabela.getStatus() == 0) {
				unidades_na_frente++;
			}

		}
		unidades_na_frente--;
		return unidades_na_frente;
	}

	public void setMenuDesembarque() {
		JPopupMenu jPopupMenu = new JPopupMenu();
		JMenuItem jMenuItemAvancar = new JMenuItem();
		JMenuItem jMenuItemEnviarNotEmFila = new JMenuItem();
		JMenuItem jMenuItemEnviarNotEntrada = new JMenuItem();
		JMenuItem jMenuItemEnviarNotSaida = new JMenuItem();
		JMenuItem jMenuItemCancelar = new JMenuItem();
		JMenuItem jMenuItemRomaneio = new JMenuItem();

		jMenuItemAvancar.setText("Avançar Etapa");
		jMenuItemEnviarNotEmFila.setText("Enviar Notificação: Em Fila");
		jMenuItemEnviarNotEntrada.setText("Enviar Notificação: Entrada");
		jMenuItemEnviarNotSaida.setText("Enviar Notificação: Saída");
		jMenuItemCancelar.setText("Cancelar");
		jMenuItemRomaneio.setText("Vizualizar Romaneio");

		jMenuItemAvancar.addActionListener(new java.awt.event.ActionListener() {
			// Importe a classe java.awt.event.ActionEvent
			public void actionPerformed(ActionEvent e) {
				try {
					GerenciarBancoFilaMovimento gerenciar = new GerenciarBancoFilaMovimento();

					int rowSel = tabela_desembarque.getSelectedRow();// pega o indice da linha na tabela
					int indiceDaLinha = tabela_desembarque.getRowSorter().convertRowIndexToModel(rowSel);// converte pro

					CadastroFilaMovimento unidade_selecionada = modelo_fila_desembarque.getValue(indiceDaLinha);

					Whatsapp zap = new Whatsapp();

					CadastroFilaMovimento unidade = modelo_fila_desembarque.getValue(indiceDaLinha);
					if (unidade.getStatus() != -1) {

						String nome_motorista = unidade.getMotorista().getNome_empresarial().toUpperCase();

						String nome_produtor = unidade.getProdutor().getNome_empresarial().toUpperCase();

						String placa = unidade.getVeiculo().getPlaca_trator().toUpperCase();

						String produto = unidade.getProduto().getNome_produto().toUpperCase();
						ArrayList<Contato> lista_contratos = pesquisarContatos(unidade.getProdutor().getId());

						if (unidade.getNotificado_saida() == 1) {
							JOptionPane.showMessageDialog(isto, "Etapas Concluídas");

						} else if (unidade.getNotificado_entrada() == 1 || unidade.getStatus() == 1) {

							// entrada notificada, notificar saida
							// enviar notificacao

							CadastroFilaMovimento unidade_completa = new GerenciarBancoFilaMovimento()
									.getUnidadeDesembarque(unidade_selecionada.getId());

							TelaFilaCadastrarMovimentoDesembarque tela = new TelaFilaCadastrarMovimentoDesembarque(3,
									unidade_completa, isto);
							tela.setVisible(true);

						} else if (unidade.getNotificado_em_fila() == 1 || unidade.getStatus() == 0) {
							// esta na fila, avancar para entrada
							// entrada notificada, notificar saida
							// enviar notificacao

							CadastroFilaMovimento unidade_completa = new GerenciarBancoFilaMovimento()
									.getUnidadeDesembarque(unidade_selecionada.getId());

							TelaFilaCadastrarMovimentoDesembarque tela = new TelaFilaCadastrarMovimentoDesembarque(2,
									unidade_completa, isto);
							tela.setVisible(true);

						} else {

							int num_veiculos_na_frente = unidadesNaFrenteDesembarque();
							long tempoMedioEspera = longTempoMedioEsperoDesembarque();

							long previsao = tempoMedioEspera;
							if (num_veiculos_na_frente > 0)
								previsao = tempoMedioEspera * ((long) num_veiculos_na_frente);

							LocalDateTime agora = LocalDateTime.now();
							agora = agora.plusMinutes(previsao);
							DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

							String sPrevisao = agora.format(formatter);

							String sTempoMedioEspera = LocalTime.MIN.plus(Duration.ofMinutes(tempoMedioEspera))
									.toString() + " horas por caminhao";

							String mensagem_enviar = "Mensagem Automatica dos Servidores da LD Armazens Gerais, nao e necessario responder! \\n \\n"
									+ nome_produtor + ", envio essa mensagem para notifica-lo que o motorista "
									+ nome_motorista + " no veiculo placa " + placa
									+ " entrou na fila de desembarque de " + produto + ". \\n \\n->Ha "
									+ num_veiculos_na_frente
									+ " caminhoes na frente da fila \\n \\n->Tempo Medio de Espera: "
									+ sTempoMedioEspera + " \\n \\n ->Previsao de Desembarque: "
									+ (previsao == 0 ? " Sem dados disponiveis" : sPrevisao);

							for (Contato contato : lista_contratos) {
								try {
									boolean retorno2 = zap.enviarMensagem(contato.getCelular(), mensagem_enviar);

									if (retorno2) { // mensagem enviada
										boolean notificado = gerenciar.emFilaNotificado(unidade.getId()); // notificacao
																											// mudada
										JOptionPane.showMessageDialog(isto,
												"Notificação de Entrada em Fila Enviada ao número: "
														+ contato.getCelular());
									} else {
										JOptionPane.showMessageDialog(isto,
												"Notificação não enviada, tente manualmente! Consulte a disponibilidade da Api Chat Pro");

									}
								} catch (Exception t) {
									t.printStackTrace();
								}
							}

							pesquisar_fila_desembarque();

						}

					} else {
						JOptionPane.showMessageDialog(isto, "Posição na fila está cancelada");

					}
				} catch (Exception f) {
					JOptionPane.showMessageDialog(null, "Erro:  " + f.getMessage() + "\nCausa: " + f.getCause());
				}

			}
		});

		jMenuItemEnviarNotEmFila.addActionListener(new java.awt.event.ActionListener() {
			// Importe a classe java.awt.event.ActionEvent
			public void actionPerformed(ActionEvent e) {
				try {
					GerenciarBancoFilaMovimento gerenciar = new GerenciarBancoFilaMovimento();

					int rowSel = tabela_desembarque.getSelectedRow();// pega o indice da linha na tabela
					int indiceDaLinha = tabela_desembarque.getRowSorter().convertRowIndexToModel(rowSel);// converte pro
																											// indice do
					Whatsapp zap = new Whatsapp();

					CadastroFilaMovimento unidade = modelo_fila_desembarque.getValue(indiceDaLinha);
					if (unidade.getStatus() != -1) {
						String nome_motorista = unidade.getMotorista().getNome_empresarial();

						String nome_produtor = unidade.getProdutor().getNome_empresarial();

						String placa = unidade.getVeiculo().getPlaca_trator();

						String produto = unidade.getProduto().getNome_produto();
						ArrayList<Contato> lista_contatos = pesquisarContatos(unidade.getProdutor().getId());

						int num_veiculos_na_frente = unidadesNaFrenteDesembarque();
						long tempoMedioEspera = longTempoMedioEsperoDesembarque();

						long previsao = tempoMedioEspera;
						if (num_veiculos_na_frente > 0)
							previsao = tempoMedioEspera * ((long) num_veiculos_na_frente);

						LocalDateTime agora = LocalDateTime.now();
						agora = agora.plusMinutes(previsao);
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

						String sPrevisao = agora.format(formatter);

						String sTempoMedioEspera = LocalTime.MIN.plus(Duration.ofMinutes(tempoMedioEspera)).toString()
								+ " horas por caminhao";

						String mensagem_enviar = "Mensagem Automatica dos Servidores da LD Armazens Gerais, nao e necessario responder! \\n \\n"
								+ nome_produtor + ", envio essa mensagem para notifica-lo que o motorista "
								+ nome_motorista + " no veiculo placa " + placa + " entrou na fila de desembarque de "
								+ produto + ". \\n \\n->Ha " + num_veiculos_na_frente
								+ " caminhoes na frente da fila \\n \\n->Tempo Medio de Espera: " + sTempoMedioEspera
								+ " \\n \\n ->Previsao de Desembarque: "
								+ (previsao == 0 ? " Sem dados disponiveis" : sPrevisao);

						for (Contato contato : lista_contatos) {
							try {
								boolean retorno2 = zap.enviarMensagem(contato.getCelular(), mensagem_enviar);

								if (retorno2) { // mensagem enviada
									boolean notificado = gerenciar.emFilaNotificado(unidade.getId()); // notificacao
																										// mudada
									JOptionPane.showMessageDialog(isto,
											"Notificação de Entrada em Fila Enviada ao número: "
													+ contato.getCelular());
								} else {
									JOptionPane.showMessageDialog(isto,
											"Notificação não enviada, tente manualmente! Consulte a disponibilidade da Api Chat Pro");

								}
							} catch (Exception t) {
								t.printStackTrace();
							}
						}

						pesquisar_fila_desembarque();
					} else {
						JOptionPane.showMessageDialog(isto, "Posição na fila está cancelada");

					}
				} catch (Exception f) {
					JOptionPane.showMessageDialog(null, "Erro:  " + f.getMessage() + "\nCausa: " + f.getCause());
				}

			}
		});

		jMenuItemEnviarNotEntrada.addActionListener(new java.awt.event.ActionListener() {
			// Importe a classe java.awt.event.ActionEvent
			public void actionPerformed(ActionEvent e) {
				try {
					GerenciarBancoFilaMovimento gerenciar = new GerenciarBancoFilaMovimento();

					int rowSel = tabela_desembarque.getSelectedRow();// pega o indice da linha na tabela
					int indiceDaLinha = tabela_desembarque.getRowSorter().convertRowIndexToModel(rowSel);// converte pro
																											// indice do
					Whatsapp zap = new Whatsapp();

					CadastroFilaMovimento unidade = modelo_fila_desembarque.getValue(indiceDaLinha);

					if (unidade.getStatus() != -1) {
						String nome_motorista = unidade.getMotorista().getNome_empresarial();

						String nome_produtor = unidade.getProdutor().getNome_empresarial();

						String placa = unidade.getVeiculo().getPlaca_trator();

						String produto = unidade.getProduto().getNome_produto();
						ArrayList<Contato> lista_contatos = pesquisarContatos(unidade.getProdutor().getId());

						String mensagem_enviar = "Mensagem Automatica dos Servidores da LD Armazens Gerais, nao e necessario responder! \\n \\n"
								+ nome_produtor + ", envio essa mensagem para notifica-lo que o motorista "
								+ nome_motorista + " no veiculo placa " + placa + " entrou no armazém para desembarque "
								+ produto + ".";

						for (Contato contato : lista_contatos) {
							try {
								boolean retorno2 = zap.enviarMensagem(contato.getCelular(), mensagem_enviar);

								if (retorno2) { // mensagem enviada
									boolean notificado = gerenciar.emFilaNotificado(unidade.getId()); // notificacao
																										// mudada
									JOptionPane.showMessageDialog(isto,
											"Notificação de Entrada no Armazém Enviada ao número: "
													+ contato.getCelular());
								} else {
									JOptionPane.showMessageDialog(isto,
											"Notificação não enviada, tente manualmente! Consulte a disponibilidade da Api Chat Pro");

								}
							} catch (Exception t) {
								t.printStackTrace();
							}
						}

						pesquisar_fila_desembarque();
					} else {
						JOptionPane.showMessageDialog(isto, "Posição na fila está cancelada");

					}
				} catch (Exception f) {
					JOptionPane.showMessageDialog(null, "Erro:  " + f.getMessage() + "\nCausa: " + f.getCause());
				}

			}
		});

		jMenuItemEnviarNotSaida.addActionListener(new java.awt.event.ActionListener() {
			// Importe a classe java.awt.event.ActionEvent
			public void actionPerformed(ActionEvent e) {
				try {

					GerenciarBancoFilaMovimento gerenciar = new GerenciarBancoFilaMovimento();

					int rowSel = tabela_desembarque.getSelectedRow();// pega o indice da linha na tabela
					int indiceDaLinha = tabela_desembarque.getRowSorter().convertRowIndexToModel(rowSel);// converte pro
																											// indice do
					Whatsapp zap = new Whatsapp();

					CadastroFilaMovimento unidade = modelo_fila_desembarque.getValue(indiceDaLinha);

					if (unidade.getStatus() != -1) {

						String nome_motorista = unidade.getMotorista().getNome_empresarial();

						String nome_produtor = unidade.getProdutor().getNome_empresarial();

						String placa = unidade.getVeiculo().getPlaca_trator();

						String produto = unidade.getProduto().getNome_produto();
						ArrayList<Contato> lista_contatos = pesquisarContatos(unidade.getProdutor().getId());

						String mensagem_enviar = "Mensagem Automatica dos Servidores da LD Armazens Gerais, nao e necessario responder! \\n \\n"
								+ nome_produtor + ", envio essa mensagem para notifica-lo que o motorista "
								+ nome_motorista + " no veiculo placa " + placa + " completou o desembarque de "
								+ produto + ".";

						mensagem_enviar = mensagemAdicional(mensagem_enviar);

						
						for (Contato contato : lista_contatos) {
							try {
								boolean retorno2 = zap.enviarMensagem(contato.getCelular(), mensagem_enviar);

								if (retorno2) { // mensagem enviada
									boolean notificado = gerenciar.emFilaNotificado(unidade.getId()); // notificacao
																										// mudada
									JOptionPane.showMessageDialog(isto,
											"Notificação de Entrada no Armazém Enviada ao número: "
													+ contato.getCelular());
								} else {
									JOptionPane.showMessageDialog(isto,
											"Notificação não enviada, tente manualmente! Consulte a disponibilidade da Api Chat Pro");

								}
							} catch (Exception t) {
								t.printStackTrace();
							}
						}

						pesquisar_fila_desembarque();

					} else {
						JOptionPane.showMessageDialog(isto, "Posição na fila está cancelada");

					}

				} catch (Exception f) {
					JOptionPane.showMessageDialog(null, "Erro:  " + f.getMessage() + "\nCausa: " + f.getCause());
				}

			}
		});

		jMenuItemCancelar.addActionListener(new java.awt.event.ActionListener() {
			// Importe a classe java.awt.event.ActionEvent
			public void actionPerformed(ActionEvent e) {
				try {

					GerenciarBancoFilaMovimento gerenciar = new GerenciarBancoFilaMovimento();

					int rowSel = tabela_desembarque.getSelectedRow();// pega o indice da linha na tabela
					int indiceDaLinha = tabela_desembarque.getRowSorter().convertRowIndexToModel(rowSel);// converte pro
																											// indice do

					CadastroFilaMovimento unidade = modelo_fila_desembarque.getValue(indiceDaLinha);

					if (unidade.getStatus() != -1) {

						if (JOptionPane.showConfirmDialog(isto, "Deseja cancelar a posição selecionada?",
								"Cancelar posição", JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
							String motivo;

							motivo = JOptionPane.showInputDialog("Motivo do cancelamento:");

							if (motivo != null) {
								unidade.setStatus(-1);
								unidade.setObservacao(motivo);

								boolean atualizou = gerenciar.cancelar(unidade.getId(), motivo);
								if (atualizou) {
									JOptionPane.showMessageDialog(null, "Cancelamento Efetuado");

								} else {
									JOptionPane.showMessageDialog(null,
											"Erro ao efetuar o cancelamento, consulte o administrador");

								}
								pesquisar_fila_desembarque();

							}

						}

					} else {
						JOptionPane.showMessageDialog(null, "Posição na fila já está cancelada");

					}

				} catch (Exception f) {
					JOptionPane.showMessageDialog(null, "Erro:  " + f.getMessage() + "\nCausa: " + f.getCause());
				}

			}
		});

		jMenuItemRomaneio.addActionListener(new java.awt.event.ActionListener() {
			// Importe a classe java.awt.event.ActionEvent
			public void actionPerformed(ActionEvent e) {
				try {

					GerenciarBancoFilaMovimento gerenciar = new GerenciarBancoFilaMovimento();

					int rowSel = tabela_desembarque.getSelectedRow();// pega o indice da linha na tabela
					int indiceDaLinha = tabela_desembarque.getRowSorter().convertRowIndexToModel(rowSel);// converte pro
																											// indice do

					CadastroFilaMovimento unidade = modelo_fila_desembarque.getValue(indiceDaLinha);

					if (unidade.getStatus() != -1) {

						CadastroRomaneio rom = unidade.getRomaneio();

						if (Desktop.isDesktopSupported()) {
							try {
								Desktop desktop = Desktop.getDesktop();
								File myFile = new File(servidor_unidade + rom.getCaminho_arquivo());
								desktop.open(myFile);
							} catch (IOException ex) {
							}
						}

					} else {
						JOptionPane.showMessageDialog(null, "Posição na fila já está cancelada");

					}

				} catch (Exception f) {
					JOptionPane.showMessageDialog(null, "Erro:  " + f.getMessage() + "\nCausa: " + f.getCause());
				}

			}
		});

		jPopupMenu.add(jMenuItemAvancar);
		jPopupMenu.add(jMenuItemEnviarNotEmFila);
		jPopupMenu.add(jMenuItemEnviarNotEntrada);
		jPopupMenu.add(jMenuItemEnviarNotSaida);
		jPopupMenu.add(jMenuItemCancelar);
		jPopupMenu.add(jMenuItemRomaneio);

		tabela_desembarque.addMouseListener(new java.awt.event.MouseAdapter() {
			// Importe a classe java.awt.event.MouseEvent
			public void mouseClicked(MouseEvent e) {
				// Se o botão direito do mouse foi pressionado
				if (e.getButton() == MouseEvent.BUTTON3) {
					// Exibe o popup menu na posição do mouse.
					jPopupMenu.show(tabela_desembarque, e.getX(), e.getY());
				}
			}
		});
	}

	public void setMenuEmbarque() {
		JPopupMenu jPopupMenu = new JPopupMenu();
		JMenuItem jMenuItemAvancar = new JMenuItem();
		JMenuItem jMenuItemEnviarNotEmFila = new JMenuItem();
		JMenuItem jMenuItemEnviarNotEntrada = new JMenuItem();
		JMenuItem jMenuItemEnviarNotSaida = new JMenuItem();
		JMenuItem jMenuItemCancelar = new JMenuItem();
		JMenuItem jMenuItemRomaneio = new JMenuItem();

		jMenuItemAvancar.setText("Avançar Etapa");
		jMenuItemEnviarNotEmFila.setText("Enviar Notificação: Em Fila");
		jMenuItemEnviarNotEntrada.setText("Enviar Notificação: Entrada");
		jMenuItemEnviarNotSaida.setText("Enviar Notificação: Saída");
		jMenuItemCancelar.setText("Cancelar");
		jMenuItemRomaneio.setText("Vizualizar Romaneio");

		jMenuItemAvancar.addActionListener(new java.awt.event.ActionListener() {
			// Importe a classe java.awt.event.ActionEvent
			public void actionPerformed(ActionEvent e) {
				try {
					GerenciarBancoFilaMovimento gerenciar = new GerenciarBancoFilaMovimento();

					int rowSel = tabela_embarque.getSelectedRow();// pega o indice da linha na tabela
					int indiceDaLinha = tabela_embarque.getRowSorter().convertRowIndexToModel(rowSel);// converte pro

					CadastroFilaMovimento unidade_selecionada = modelo_fila_embarque.getValue(indiceDaLinha);

					Whatsapp zap = new Whatsapp();

					CadastroFilaMovimento unidade = modelo_fila_embarque.getValue(indiceDaLinha);
					if (unidade.getStatus() != -1) {

						String nome_motorista = (unidade.getMotorista().getNome_empresarial().toUpperCase().trim());

						String nome_cliente = (unidade.getProdutor().getNome_empresarial().toUpperCase().trim());

						String nome_transportadora = (unidade.getTransportadora().getNome_empresarial().toUpperCase()
								.trim());

						String placa = unidade.getVeiculo().getPlaca_trator().toUpperCase();

						String produto = unidade.getProduto().getNome_produto().toUpperCase();
						ArrayList<Contato> lista_contratos = pesquisarContatos(unidade.getProdutor().getId());

						if (unidade.getNotificado_saida() == 1) {
							JOptionPane.showMessageDialog(isto, "Etapas Concluídas");

						} else if (unidade.getNotificado_entrada() == 1 || unidade.getStatus() == 1) {

							// entrada notificada, notificar saida
							// enviar notificacao

							CadastroFilaMovimento unidade_completa = new GerenciarBancoFilaMovimento()
									.getUnidadeEmbarque(unidade_selecionada.getId());

							TelaFilaCadastrarMovimentoEmbarque tela = new TelaFilaCadastrarMovimentoEmbarque(3,
									unidade_completa, isto);
							tela.setVisible(true);

						} else if (unidade.getNotificado_em_fila() == 1 || unidade.getStatus() == 0) {
							// esta na fila, avancar para entrada
							// entrada notificada, notificar saida
							// enviar notificacao

							CadastroFilaMovimento unidade_completa = new GerenciarBancoFilaMovimento()
									.getUnidadeEmbarque(unidade_selecionada.getId());

							TelaFilaCadastrarMovimentoEmbarque tela = new TelaFilaCadastrarMovimentoEmbarque(2,
									unidade_completa, isto);
							tela.setVisible(true);

						} else {

							int num_veiculos_na_frente = unidadesNaFrenteEmbarque();
							long tempoMedioEspera = longTempoMedioEsperoEmbarque();

							long previsao = tempoMedioEspera;
							if (num_veiculos_na_frente > 0)
								previsao = tempoMedioEspera * ((long) num_veiculos_na_frente);

							LocalDateTime agora = LocalDateTime.now();
							agora = agora.plusMinutes(previsao);
							DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

							String sPrevisao = agora.format(formatter);

							String sTempoMedioEspera = LocalTime.MIN.plus(Duration.ofMinutes(tempoMedioEspera))
									.toString() + " horas por caminhao";

							String mensagem_enviar = "Mensagem Automatica dos Servidores da LD Armazens Gerais, nao e necessario responder! \\n \\n"
									+ nome_cliente + ", envio essa mensagem para notifica-lo que o motorista "
									+ nome_motorista + " no veiculo placa " + placa + " entrou na fila de embarque de "
									+ produto + ". \\n \\n->Ha " + num_veiculos_na_frente
									+ " caminhoes na frente da fila \\n \\n->Tempo Medio de Espera: "
									+ sTempoMedioEspera + " \\n \\n ->Previsao de Embarque: "
									+ (previsao == 0 ? " Sem dados disponiveis" : sPrevisao);
							
							
						
							
							for (Contato contato : lista_contratos) {
								try {
									boolean retorno2 = zap.enviarMensagem(contato.getCelular(), mensagem_enviar);

									if (retorno2) { // mensagem enviada
										boolean notificado = gerenciar.emFilaNotificado(unidade.getId()); // notificacao
																											// mudada
										JOptionPane.showMessageDialog(isto,
												"Notificação de Entrada em Fila para embarque Enviada ao número: "
														+ contato.getCelular());
									} else {
										JOptionPane.showMessageDialog(isto,
												"Notificação de Entrada em Fila para embarque não enviada, tente manualmente! Consulte a disponibilidade da Api Chat Pro");

									}
								} catch (Exception t) {
									t.printStackTrace();
								}
							}

							pesquisar_fila_embarque();

						}

					} else {
						JOptionPane.showMessageDialog(isto, "Posição na fila de embarque está cancelada");

					}
				} catch (Exception f) {
					JOptionPane.showMessageDialog(null, "Erro:  " + f.getMessage() + "\nCausa: " + f.getCause());
				}

			}
		});

		jMenuItemEnviarNotEmFila.addActionListener(new java.awt.event.ActionListener() {
			// Importe a classe java.awt.event.ActionEvent
			public void actionPerformed(ActionEvent e) {
				try {
					GerenciarBancoFilaMovimento gerenciar = new GerenciarBancoFilaMovimento();

					int rowSel = tabela_embarque.getSelectedRow();// pega o indice da linha na tabela
					int indiceDaLinha = tabela_embarque.getRowSorter().convertRowIndexToModel(rowSel);// converte pro
																										// indice do
					Whatsapp zap = new Whatsapp();

					CadastroFilaMovimento unidade = modelo_fila_embarque.getValue(indiceDaLinha);
					if (unidade.getStatus() != -1) {

						String nome_motorista = (unidade.getMotorista().getNome_empresarial().toUpperCase().trim());

						String nome_transportadora = (unidade.getTransportadora().getNome_empresarial().toUpperCase()
								.trim());

						String nome_cliente = (unidade.getProdutor().getNome_empresarial().toUpperCase().trim());

						String placa = unidade.getVeiculo().getPlaca_trator();

						String produto = unidade.getProduto().getNome_produto();
						ArrayList<Contato> lista_contatos = pesquisarContatos(unidade.getProdutor().getId());

						int num_veiculos_na_frente = unidadesNaFrenteEmbarque();
						long tempoMedioEspera = longTempoMedioEsperoEmbarque();

						long previsao = tempoMedioEspera;
						if (num_veiculos_na_frente > 0)
							previsao = tempoMedioEspera * ((long) num_veiculos_na_frente);

						LocalDateTime agora = LocalDateTime.now();
						agora = agora.plusMinutes(previsao);
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

						String sPrevisao = agora.format(formatter);

						String sTempoMedioEspera = LocalTime.MIN.plus(Duration.ofMinutes(tempoMedioEspera)).toString()
								+ " horas por caminhao";

						String mensagem_enviar = "Mensagem Automatica dos Servidores da LD Armazens Gerais, nao e necessario responder! \\n \\n"
								+ nome_cliente + ", envio essa mensagem para notifica-lo que o motorista "
								+ nome_motorista + " no veiculo placa " + placa + " entrou na fila de embarque de "
								+ produto + ". \\n \\n->Ha " + num_veiculos_na_frente
								+ " caminhoes na frente da fila \\n \\n->Tempo Medio de Espera: " + sTempoMedioEspera
								+ " \\n \\n ->Previsao de Embarque: "
								+ (previsao == 0 ? " Sem dados disponiveis" : sPrevisao);


						
						for (Contato contato : lista_contatos) {
							try {
								boolean retorno2 = zap.enviarMensagem(contato.getCelular(), mensagem_enviar);

								if (retorno2) { // mensagem enviada
									boolean notificado = gerenciar.emFilaNotificado(unidade.getId()); // notificacao
																										// mudada
									JOptionPane.showMessageDialog(isto,
											"Notificação de Entrada em Fila de Embarque Enviada ao número: "
													+ contato.getCelular());
								} else {
									JOptionPane.showMessageDialog(isto,
											"Notificação de Entrada em Fila de Embarque não enviada, tente manualmente! Consulte a disponibilidade da Api Chat Pro");

								}
							} catch (Exception t) {
								t.printStackTrace();
							}
						}

						pesquisar_fila_embarque();
					} else {
						JOptionPane.showMessageDialog(isto, "Posição na fila de embarque está cancelada");

					}
				} catch (Exception f) {
					JOptionPane.showMessageDialog(null, "Erro:  " + f.getMessage() + "\nCausa: " + f.getCause());
				}

			}
		});

		jMenuItemEnviarNotEntrada.addActionListener(new java.awt.event.ActionListener() {
			// Importe a classe java.awt.event.ActionEvent
			public void actionPerformed(ActionEvent e) {
				try {
					GerenciarBancoFilaMovimento gerenciar = new GerenciarBancoFilaMovimento();

					int rowSel = tabela_embarque.getSelectedRow();// pega o indice da linha na tabela
					int indiceDaLinha = tabela_embarque.getRowSorter().convertRowIndexToModel(rowSel);// converte pro
																										// indice do
					Whatsapp zap = new Whatsapp();

					CadastroFilaMovimento unidade = modelo_fila_embarque.getValue(indiceDaLinha);

					if (unidade.getStatus() != -1) {

						String nome_motorista = (unidade.getMotorista().getNome_empresarial().toUpperCase().trim());

						String nome_transportadora = (unidade.getTransportadora().getNome_empresarial().toUpperCase()
								.trim());

						String nome_cliente = (unidade.getProdutor().getNome_empresarial().toUpperCase().trim());

						String placa = unidade.getVeiculo().getPlaca_trator();

						String produto = unidade.getProduto().getNome_produto();
						ArrayList<Contato> lista_contatos = pesquisarContatos(unidade.getProdutor().getId());

						String mensagem_enviar = "Mensagem Automatica dos Servidores da LD Armazens Gerais, nao e necessario responder! \\n \\n"
								+ nome_cliente + ", envio essa mensagem para notifica-lo que o motorista "
								+ nome_motorista + " no veiculo placa " + placa + " entrou no armazém para embarque "
								+ produto + ".";

						
						for (Contato contato : lista_contatos) {
							try {
								boolean retorno2 = zap.enviarMensagem(contato.getCelular(), mensagem_enviar);

								if (retorno2) { // mensagem enviada
									boolean notificado = gerenciar.emFilaNotificado(unidade.getId()); // notificacao
																										// mudada
									JOptionPane.showMessageDialog(isto,
											"Notificação de Entrada para Embarque no Armazém Enviada ao número: "
													+ contato.getCelular());
								} else {
									JOptionPane.showMessageDialog(isto,
											"Notificação de Entrada para Embarque no Armazém não enviada, tente manualmente! Consulte a disponibilidade da Api Chat Pro");

								}
							} catch (Exception t) {
								t.printStackTrace();
							}
						}

						pesquisar_fila_embarque();
					} else {
						JOptionPane.showMessageDialog(isto, "Posição na fila de embarque está cancelada");

					}
				} catch (Exception f) {
					JOptionPane.showMessageDialog(null, "Erro:  " + f.getMessage() + "\nCausa: " + f.getCause());
				}

			}
		});

		jMenuItemEnviarNotSaida.addActionListener(new java.awt.event.ActionListener() {
			// Importe a classe java.awt.event.ActionEvent
			public void actionPerformed(ActionEvent e) {
				try {

					GerenciarBancoFilaMovimento gerenciar = new GerenciarBancoFilaMovimento();

					int rowSel = tabela_embarque.getSelectedRow();// pega o indice da linha na tabela
					int indiceDaLinha = tabela_embarque.getRowSorter().convertRowIndexToModel(rowSel);// converte pro
																										// indice do
					Whatsapp zap = new Whatsapp();

					CadastroFilaMovimento unidade = modelo_fila_embarque.getValue(indiceDaLinha);

					if (unidade.getStatus() != -1) {

						String nome_motorista = (unidade.getMotorista().getNome_empresarial().toUpperCase().trim());

						String nome_transportadora = (unidade.getTransportadora().getNome_empresarial().toUpperCase()
								.trim());

						String nome_cliente = (unidade.getProdutor().getNome_empresarial().toUpperCase().trim());

						String placa = unidade.getVeiculo().getPlaca_trator();

						String produto = unidade.getProduto().getNome_produto();
						ArrayList<Contato> lista_contatos = pesquisarContatos(unidade.getProdutor().getId());

						String mensagem_enviar = "Mensagem Automatica dos Servidores da LD Armazens Gerais, nao e necessario responder! \\n \\n"
								+ nome_cliente + ", envio essa mensagem para notifica-lo que o motorista "
								+ nome_motorista + " no veiculo placa " + placa + " completou o embarque de " + produto
								+ ".";
						
						mensagem_enviar = mensagemAdicional(mensagem_enviar);


						for (Contato contato : lista_contatos) {
							try {
								boolean retorno2 = zap.enviarMensagem(contato.getCelular(), mensagem_enviar);

								if (retorno2) { // mensagem enviada
									boolean notificado = gerenciar.emFilaNotificado(unidade.getId()); // notificacao
																										// mudada
									JOptionPane.showMessageDialog(isto,
											"Notificação de Embarque completo no Armazém Enviada ao número: "
													+ contato.getCelular());
								} else {
									JOptionPane.showMessageDialog(isto,
											"Notificação de Embarque completo não enviada, tente manualmente! Consulte a disponibilidade da Api Chat Pro");

								}
							} catch (Exception t) {
								t.printStackTrace();
							}
						}

						pesquisar_fila_embarque();

					} else {
						JOptionPane.showMessageDialog(isto, "Posição na fila de embarque está cancelada");

					}

				} catch (Exception f) {
					JOptionPane.showMessageDialog(null, "Erro:  " + f.getMessage() + "\nCausa: " + f.getCause());
				}

			}
		});

		jMenuItemCancelar.addActionListener(new java.awt.event.ActionListener() {
			// Importe a classe java.awt.event.ActionEvent
			public void actionPerformed(ActionEvent e) {
				try {

					GerenciarBancoFilaMovimento gerenciar = new GerenciarBancoFilaMovimento();

					int rowSel = tabela_embarque.getSelectedRow();// pega o indice da linha na tabela
					int indiceDaLinha = tabela_embarque.getRowSorter().convertRowIndexToModel(rowSel);// converte pro
																										// indice do

					CadastroFilaMovimento unidade = modelo_fila_embarque.getValue(indiceDaLinha);

					if (unidade.getStatus() != -1) {

						if (JOptionPane.showConfirmDialog(isto, "Deseja cancelar a posição selecionada?",
								"Cancelar posição", JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
							String motivo;

							motivo = JOptionPane.showInputDialog("Motivo do cancelamento:");

							if (motivo != null) {
								unidade.setStatus(-1);
								unidade.setObservacao(motivo);

								boolean atualizou = gerenciar.cancelar(unidade.getId(), motivo);
								if (atualizou) {
									JOptionPane.showMessageDialog(null, "Cancelamento Efetuado");

								} else {
									JOptionPane.showMessageDialog(null,
											"Erro ao efetuar o cancelamento, consulte o administrador");

								}
								pesquisar_fila_desembarque();

							}

						}

					} else {
						JOptionPane.showMessageDialog(null, "Posição na fila de embarque já está cancelada");

					}

				} catch (Exception f) {
					JOptionPane.showMessageDialog(null, "Erro:  " + f.getMessage() + "\nCausa: " + f.getCause());
				}

			}
		});

		jMenuItemRomaneio.addActionListener(new java.awt.event.ActionListener() {
			// Importe a classe java.awt.event.ActionEvent
			public void actionPerformed(ActionEvent e) {
				try {

					GerenciarBancoFilaMovimento gerenciar = new GerenciarBancoFilaMovimento();

					int rowSel = tabela_embarque.getSelectedRow();// pega o indice da linha na tabela
					int indiceDaLinha = tabela_embarque.getRowSorter().convertRowIndexToModel(rowSel);// converte pro
																										// indice do

					CadastroFilaMovimento unidade = modelo_fila_embarque.getValue(indiceDaLinha);

					if (unidade.getStatus() != -1) {

						CadastroRomaneio rom = unidade.getRomaneio();

						if (Desktop.isDesktopSupported()) {
							try {
								Desktop desktop = Desktop.getDesktop();
								File myFile = new File(servidor_unidade + rom.getCaminho_arquivo());
								desktop.open(myFile);
							} catch (IOException ex) {
							}
						}

					} else {
						JOptionPane.showMessageDialog(null, "Posição na fila de embarque já está cancelada");

					}

				} catch (Exception f) {
					JOptionPane.showMessageDialog(null, "Erro:  " + f.getMessage() + "\nCausa: " + f.getCause());
				}

			}
		});

		jPopupMenu.add(jMenuItemAvancar);
		jPopupMenu.add(jMenuItemEnviarNotEmFila);
		jPopupMenu.add(jMenuItemEnviarNotEntrada);
		jPopupMenu.add(jMenuItemEnviarNotSaida);
		jPopupMenu.add(jMenuItemCancelar);
		jPopupMenu.add(jMenuItemRomaneio);

		tabela_embarque.addMouseListener(new java.awt.event.MouseAdapter() {
			// Importe a classe java.awt.event.MouseEvent
			public void mouseClicked(MouseEvent e) {
				// Se o botão direito do mouse foi pressionado
				if (e.getButton() == MouseEvent.BUTTON3) {
					// Exibe o popup menu na posição do mouse.
					jPopupMenu.show(tabela_embarque, e.getX(), e.getY());
				}
			}
		});
	}

	public ArrayList<Contato> pesquisarContatos(int id_cliente) {
		GerenciarBancoClientes gerenciar = new GerenciarBancoClientes();
		ArrayList<Contato> lista_contatos = gerenciar.getContatos(id_cliente);

		ArrayList<Contato> lista_contatos_aceita = new ArrayList<>();

		for (Contato contato : lista_contatos) {
			if (contato != null) {
				String celular = contato.getCelular();
				if (celular != null && celular.length() == 11) {
					// celular aceito
					System.out.println("Celular:" + celular);

					lista_contatos_aceita.add(contato);

				}
			}
		}

		return lista_contatos_aceita;
	}

	public void pesquisar_fila_desembarque() {
		

		GerenciarBancoFilaMovimento gerenciar = new GerenciarBancoFilaMovimento();
		lista_fila_desembarque.clear();
		modelo_fila_desembarque.onRemoveAll();

		if (flag_global == 1) {

			lista_fila_desembarque = gerenciar.getFila(0, "%");

		} else {

			LocalDateTime currentDateTime = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			// lista_fila_desembarque =
			// gerenciar.getFila(currentDateTime.format(formatter));
			lista_fila_desembarque = gerenciar.getFilaMaisRapido(0);
		}

		for (CadastroFilaMovimento cc : lista_fila_desembarque) {

			modelo_fila_desembarque.onAdd(cc);
		}

		filtrarDesembarque();

	}

	public void pesquisar_fila_embarque() {
		GerenciarBancoFilaMovimento gerenciar = new GerenciarBancoFilaMovimento();
		lista_fila_embarque.clear();
		modelo_fila_embarque.onRemoveAll();

		if (flag_global == 1) {

			lista_fila_embarque = gerenciar.getFila(1, "%");

		} else {

			LocalDateTime currentDateTime = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			// lista_fila_desembarque =
			// gerenciar.getFila(currentDateTime.format(formatter));
			lista_fila_embarque = gerenciar.getFilaMaisRapido(1);
		}

		for (CadastroFilaMovimento cc : lista_fila_embarque) {

			modelo_fila_embarque.onAdd(cc);
		}

		filtrarEmbarque();

	}

	public String tempoMedioEsperoDesembarque() {
		long total_tempo_entre_entrada_saida = 0;
		int em_fila = 0, entrada = 0, saida = 0;

		for (int row = 0; row < tabela_desembarque.getRowCount(); row++) {

			int index = tabela_desembarque.convertRowIndexToModel(row);
			CadastroFilaMovimento unidade = modelo_fila_desembarque.getValue(index);

			int sts = unidade.getStatus();
			if (sts == 0) {
				em_fila++;
			} else if (sts == 1) {
				entrada++;
			} else if (sts == 2) {
				saida++;

				long tempo_entre_entrada_saida = ChronoUnit.MINUTES.between(
						dateToLocalDateTime(unidade.getData_hora_entrada()),
						dateToLocalDateTime(unidade.getData_hora_saida()));

				total_tempo_entre_entrada_saida += tempo_entre_entrada_saida;
			}

		}

		total_tempo_entre_entrada_saida = total_tempo_entre_entrada_saida / (long) saida;
		return LocalTime.MIN.plus(Duration.ofMinutes(total_tempo_entre_entrada_saida)).toString() + " horas";

	}

	public String tempoMedioEmbarque() {
		long total_tempo_entre_entrada_saida = 0;
		int em_fila = 0, entrada = 0, saida = 0;

		for (int row = 0; row < tabela_embarque.getRowCount(); row++) {

			int index = tabela_embarque.convertRowIndexToModel(row);
			CadastroFilaMovimento unidade = modelo_fila_embarque.getValue(index);

			int sts = unidade.getStatus();
			if (sts == 0) {
				em_fila++;
			} else if (sts == 1) {
				entrada++;
			} else if (sts == 2) {
				saida++;

				long tempo_entre_entrada_saida = ChronoUnit.MINUTES.between(
						dateToLocalDateTime(unidade.getData_hora_fila()),
						dateToLocalDateTime(unidade.getData_hora_saida()));

				total_tempo_entre_entrada_saida += tempo_entre_entrada_saida;
			}

		}

		total_tempo_entre_entrada_saida = total_tempo_entre_entrada_saida / (long) saida;
		return LocalTime.MIN.plus(Duration.ofMinutes(total_tempo_entre_entrada_saida)).toString() + " horas";

	}

	public long longTempoMedioEsperoDesembarque() {
		long total_tempo_entre_entrada_saida = 0;
		int em_fila = 0, entrada = 0, saida = 0;

		for (int row = 0; row < tabela_desembarque.getRowCount(); row++) {

			int index = tabela_desembarque.convertRowIndexToModel(row);
			CadastroFilaMovimento unidade = modelo_fila_desembarque.getValue(index);

			int sts = unidade.getStatus();
			if (sts == 0) {
				em_fila++;
			} else if (sts == 1) {
				entrada++;
			} else if (sts == 2) {
				saida++;

				long tempo_entre_entrada_saida = ChronoUnit.MINUTES.between(
						dateToLocalDateTime(unidade.getData_hora_entrada()),
						dateToLocalDateTime(unidade.getData_hora_saida()));

				total_tempo_entre_entrada_saida += tempo_entre_entrada_saida;
			}

		}

		if (saida > 0)
			total_tempo_entre_entrada_saida = total_tempo_entre_entrada_saida / (long) saida;
		return total_tempo_entre_entrada_saida;
	}

	public long longTempoMedioEsperoEmbarque() {
		long total_tempo_entre_entrada_saida = 0;
		int em_fila = 0, entrada = 0, saida = 0;

		for (int row = 0; row < tabela_embarque.getRowCount(); row++) {

			int index = tabela_embarque.convertRowIndexToModel(row);
			CadastroFilaMovimento unidade = modelo_fila_embarque.getValue(index);

			int sts = unidade.getStatus();
			if (sts == 0) {
				em_fila++;
			} else if (sts == 1) {
				entrada++;
			} else if (sts == 2) {
				saida++;

				long tempo_entre_entrada_saida = ChronoUnit.MINUTES.between(
						dateToLocalDateTime(unidade.getData_hora_fila()),
						dateToLocalDateTime(unidade.getData_hora_saida()));

				total_tempo_entre_entrada_saida += tempo_entre_entrada_saida;
			}

		}

		if (saida > 0)
			total_tempo_entre_entrada_saida = total_tempo_entre_entrada_saida / (long) saida;
		return total_tempo_entre_entrada_saida;
	}

	public void calcularDesembarque() {

		int em_fila = 0, entrada = 0, saida = 0;

		long total_tempo_entre_entrada_saida = 0;

		for (int row = 0; row < tabela_desembarque.getRowCount(); row++) {

			int index = tabela_desembarque.convertRowIndexToModel(row);
			CadastroFilaMovimento unidade = modelo_fila_desembarque.getValue(index);

			int sts = unidade.getStatus();
			if (sts == 0) {
				em_fila++;
			} else if (sts == 1) {
				entrada++;
			} else if (sts == 2) {
				saida++;

				long tempo_entre_entrada_saida = ChronoUnit.MINUTES.between(
						dateToLocalDateTime(unidade.getData_hora_entrada()),
						dateToLocalDateTime(unidade.getData_hora_saida()));

				total_tempo_entre_entrada_saida += tempo_entre_entrada_saida;
			}

		}

		lblEmFila.setText(em_fila + " na Fila");
		lblEmDesembarque.setText(entrada + " em Desembarque");
		lblDesembarcados.setText(saida + " já Desembarcados");
		int total = em_fila + entrada + saida;
		lblTotal.setText((total) + " Caminhões");

		if (saida > 0)
			total_tempo_entre_entrada_saida = total_tempo_entre_entrada_saida / (long) saida;
		lblTempoMedioEsperaDesembarque
				.setText(LocalTime.MIN.plus(Duration.ofMinutes(total_tempo_entre_entrada_saida)).toString() + " horas");
	}

	public void calcularEmbarque() {

		int em_fila = 0, entrada = 0, saida = 0;

		long total_tempo_entre_entrada_saida = 0;

		for (int row = 0; row < tabela_embarque.getRowCount(); row++) {

			int index = tabela_embarque.convertRowIndexToModel(row);
			CadastroFilaMovimento unidade = modelo_fila_embarque.getValue(index);

			int sts = unidade.getStatus();
			if (sts == 0) {
				em_fila++;
			} else if (sts == 1) {
				entrada++;
			} else if (sts == 2) {
				saida++;

				long tempo_entre_entrada_saida = ChronoUnit.MINUTES.between(
						dateToLocalDateTime(unidade.getData_hora_fila()),
						dateToLocalDateTime(unidade.getData_hora_saida()));

				total_tempo_entre_entrada_saida += tempo_entre_entrada_saida;
			}

		}

		lblEmFilaEmbarque.setText(em_fila + " na Fila");
		lblEmEmbarque.setText(entrada + " em Embarque");
		lblEmbarcados.setText(saida + " já Embarcados");
		int total = em_fila + entrada + saida;
		lblTotalEmbarque.setText((total) + " Caminhões");

		if (saida > 0)
			total_tempo_entre_entrada_saida = total_tempo_entre_entrada_saida / (long) saida;
		lblTempoMedioEsperaEmbarque
				.setText(LocalTime.MIN.plus(Duration.ofMinutes(total_tempo_entre_entrada_saida)).toString() + " horas");
	}

	public LocalDateTime dateToLocalDateTime(Date dateToConvert) {
		return Instant.ofEpochMilli(dateToConvert.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	public boolean checkString(String txt) {
		return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ");
	}

	public void filtrarDesembarque() {

		ArrayList<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>(2);

		String menor = entDataMenor.getText();
		String maior = entDataMaior.getText();

		if (checkString(menor) && checkString(maior)) {
			Date data_menor = null;
			Date data_maior = null;
			try {
				data_menor = new SimpleDateFormat("dd/MM/yyyy").parse(menor);
				data_maior = new SimpleDateFormat("dd/MM/yyyy").parse(maior);

			} catch (ParseException i) {
				// TODO Auto-generated catch block
				i.printStackTrace();
			}

			Set<RowFilter<Object, Object>> datas = new HashSet<>();
			datas.add(RowFilter.dateFilter(RowFilter.ComparisonType.AFTER, data_menor, 2));
			datas.add(RowFilter.dateFilter(RowFilter.ComparisonType.EQUAL, data_menor, 2));
			filters.add(RowFilter.orFilter(datas));

			// filters.add( RowFilter.dateFilter(ComparisonType.AFTER, data_menor, 5) );
			// filters.add( RowFilter.dateFilter(ComparisonType.EQUAL, data_menor, 5) );

			// filters.add( RowFilter.dateFilter(ComparisonType.BEFORE, data_maior, 5) );
			// filters.add( RowFilter.dateFilter(ComparisonType.EQUAL, data_maior, 5) );
			Set<RowFilter<Object, Object>> datas_maior = new HashSet<>();
			datas_maior.add(RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE, data_maior, 2));
			datas_maior.add(RowFilter.dateFilter(RowFilter.ComparisonType.EQUAL, data_maior, 2));
			filters.add(RowFilter.orFilter(datas_maior));
		}

		// motorista
		if (entMotorista.getText() != null) {
			String s_motor = "";
			if (checkString(entMotorista.getText())) {
				s_motor = entMotorista.getText().toUpperCase();

				filters.add(RowFilter.regexFilter(s_motor, 6));
			}
		}

		// veiculo
		if (entPlaca.getText() != null) {
			String s_placa = "";
			if (checkString(entPlaca.getText())) {
				s_placa = entPlaca.getText().toUpperCase();

				filters.add(RowFilter.regexFilter(s_placa, 7));
			}
		}

		// produtor
		if (entProdutor.getText() != null) {
			String s_produtor = "";
			if (checkString(entProdutor.getText())) {
				s_produtor = entProdutor.getText().toUpperCase();

				filters.add(RowFilter.regexFilter(s_produtor, 9));
			}
		}

		// produto
		if (entProduto.getText() != null) {
			String s_produto = "";
			if (checkString(entProduto.getText())) {
				s_produto = entProduto.getText().toUpperCase();

				filters.add(RowFilter.regexFilter(s_produto, 10));
			}
		}

		// status
		if (!cbStatusDesembarque.getSelectedItem().toString().equalsIgnoreCase("TODOS")) {
			String s_status = "";
			if (checkString(cbStatusDesembarque.getSelectedItem().toString())) {
				s_status = cbStatusDesembarque.getSelectedItem().toString().toUpperCase();

				filters.add(RowFilter.regexFilter(s_status, 15));
			}
		}

		sorterDesembarque.setRowFilter(RowFilter.andFilter(filters));
		calcularDesembarque();
	}

	public void filtrarEmbarque() {

		/*
		 * 
		 * private final int id = 0; private final int romaneio = 1; private final int
		 * data = 2; private final int hora_fila = 3; private final int hora_entrada =
		 * 4; private final int hora_saida = 5; private final int cliente = 6; private
		 * final int transportadora = 7; private final int motorista = 8; private final
		 * int placa = 9; private final int produto = 10; private final int origem = 11;
		 * private final int destino = 12; private final int tem_nf = 13; private final
		 * int tem_autorizacao = 14; private final int status = 15; private final int
		 * ultima_notificacao = 16; private final int umidade = 17; private final int
		 * impureza = 18; private final int ardidos = 19; private final int peso_bruto =
		 * 20; private final int peso_tara = 21; private final int peso_liquido = 22;
		 * private final int usuario = 23;
		 * 
		 */
		ArrayList<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>(2);

		String menor = entDataMenorEmbarque.getText();
		String maior = entDataMaiorEmbarque.getText();

		if (checkString(menor) && checkString(maior)) {
			Date data_menor = null;
			Date data_maior = null;
			try {
				data_menor = new SimpleDateFormat("dd/MM/yyyy").parse(menor);
				data_maior = new SimpleDateFormat("dd/MM/yyyy").parse(maior);

			} catch (ParseException i) {
				// TODO Auto-generated catch block
				i.printStackTrace();
			}

			Set<RowFilter<Object, Object>> datas = new HashSet<>();
			datas.add(RowFilter.dateFilter(RowFilter.ComparisonType.AFTER, data_menor, 2));
			datas.add(RowFilter.dateFilter(RowFilter.ComparisonType.EQUAL, data_menor, 2));
			filters.add(RowFilter.orFilter(datas));

			// filters.add( RowFilter.dateFilter(ComparisonType.AFTER, data_menor, 5) );
			// filters.add( RowFilter.dateFilter(ComparisonType.EQUAL, data_menor, 5) );

			// filters.add( RowFilter.dateFilter(ComparisonType.BEFORE, data_maior, 5) );
			// filters.add( RowFilter.dateFilter(ComparisonType.EQUAL, data_maior, 5) );
			Set<RowFilter<Object, Object>> datas_maior = new HashSet<>();
			datas_maior.add(RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE, data_maior, 2));
			datas_maior.add(RowFilter.dateFilter(RowFilter.ComparisonType.EQUAL, data_maior, 2));
			filters.add(RowFilter.orFilter(datas_maior));
		}

		// cliente destinatario
		if (entClienteDestinatario.getText() != null) {
			String s_motor = "";
			if (checkString(entClienteDestinatario.getText())) {
				s_motor = entClienteDestinatario.getText().toUpperCase();

				filters.add(RowFilter.regexFilter(s_motor, 6));
			}
		}

		// transportadora
		if (entTransportadoraEmbarque.getText() != null) {
			String s_motor = "";
			if (checkString(entTransportadoraEmbarque.getText())) {
				s_motor = entTransportadoraEmbarque.getText().toUpperCase();

				filters.add(RowFilter.regexFilter(s_motor, 7));
			}
		}

		// motorista
		if (entMotoristaEmbarque.getText() != null) {
			String s_motor = "";
			if (checkString(entMotoristaEmbarque.getText())) {
				s_motor = entMotoristaEmbarque.getText().toUpperCase();

				filters.add(RowFilter.regexFilter(s_motor, 8));
			}
		}

		// veiculo
		if (entPlacaEmbarque.getText() != null) {
			String s_placa = "";
			if (checkString(entPlacaEmbarque.getText())) {
				s_placa = entPlacaEmbarque.getText().toUpperCase();

				filters.add(RowFilter.regexFilter(s_placa, 9));
			}
		}

		// produto
		if (entProdutoEmbarque.getText() != null) {
			String s_produto = "";
			if (checkString(entProdutoEmbarque.getText())) {
				s_produto = entProdutoEmbarque.getText().toUpperCase();

				filters.add(RowFilter.regexFilter(s_produto, 10));
			}
		}

		// status
		if (!cbStatusEmbarque.getSelectedItem().toString().equalsIgnoreCase("TODOS")) {
			String s_status = "";
			if (checkString(cbStatusEmbarque.getSelectedItem().toString())) {
				s_status = cbStatusEmbarque.getSelectedItem().toString().toUpperCase();

				filters.add(RowFilter.regexFilter(s_status, 15));
			}
		}

		sorterEmbarque.setRowFilter(RowFilter.andFilter(filters));
		calcularEmbarque();
	}

	public void limparFiltrosDesembarque() {
		sorterDesembarque.setRowFilter(RowFilter.regexFilter(""));

	}

	public void limparFiltrosEmbarque() {
		sorterEmbarque.setRowFilter(RowFilter.regexFilter(""));

	}

	public static class FilaEmbarqueTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int id = 0;
		private final int romaneio = 1;
		private final int data = 2;
		private final int hora_fila = 3;
		private final int hora_entrada = 4;
		private final int hora_saida = 5;
		private final int cliente = 6;
		private final int transportadora = 7;
		private final int motorista = 8;
		private final int placa = 9;
		private final int produto = 10;
		private final int origem = 11;
		private final int destino = 12;
		private final int tem_nf = 13;
		private final int tem_autorizacao = 14;
		private final int status = 15;
		private final int ultima_notificacao = 16;
		private final int umidade = 17;
		private final int impureza = 18;
		private final int ardidos = 19;
		private final int peso_bruto = 20;
		private final int peso_tara = 21;
		private final int peso_liquido = 22;
		private final int usuario = 23;

		List<Color> rowColours = Arrays.asList(Color.RED, Color.GREEN, Color.CYAN);

		private final String colunas[] = { "ID", "ROMANEIO", "DATA", "HORA FILA", "HORA ENTRADA", "HORA SAÍDA",
				"CLIENTE", "TRANSPORTADORA", "MOTORISTA:", "PLACA:", "PRODUTO:", "ORIGEM", "DESTINO", "TEM NF",
				"TEM AUTORIZAÇÃO", "STATUS", "ÚLTIMA NOTIFICAÇÃO:", "UMIDADE", "IMPUREZA", "ARDIDOS", "PESO BRUTO(kgs)",
				"PESO TARA(kgs)", "PESO LIQUIDO(kgs)", "USUARIO" };
		private final ArrayList<CadastroFilaMovimento> dados = new ArrayList<>();// usamos como dados uma lista genérica
		private Locale ptBr = new Locale("pt", "BR");
		private NumberFormat z = NumberFormat.getNumberInstance(); // de

		public FilaEmbarqueTableModel() {

		}

		@Override
		public int getColumnCount() {
			// retorna o total de colunas
			return colunas.length;
		}

		@Override
		public int getRowCount() {
			// retorna o total de linhas na tabela
			return dados.size();
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			// retorna o tipo de dado, para cada coluna
			switch (columnIndex) {
			case id:
				return Integer.class;
			case romaneio:
				return String.class;
			case data:
				return Date.class;
			case hora_fila:
				return LocalTime.class;
			case hora_entrada:
				return LocalTime.class;
			case hora_saida:
				return LocalTime.class;
			case cliente:
				return String.class;
			case transportadora:
				return String.class;
			case motorista:
				return String.class;
			case placa:
				return String.class;
			case produto:
				return String.class;
			case origem:
				return String.class;
			case destino:
				return String.class;
			case tem_nf:
				return String.class;
			case tem_autorizacao:
				return String.class;
			case status:
				return String.class;
			case ultima_notificacao:
				return String.class;
			case umidade:
				return String.class;
			case impureza:
				return String.class;
			case ardidos:
				return String.class;
			case peso_bruto:
				return String.class;
			case peso_tara:
				return String.class;
			case peso_liquido:
				return String.class;
			case usuario:
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
			CadastroFilaMovimento unidade = dados.get(rowIndex);

			// retorna o valor da coluna
			switch (columnIndex) {
			case id:
				return unidade.getId();
			case romaneio: {
				if (unidade.getRomaneio() != null) {
					return unidade.getRomaneio().getNumero_romaneio() + " DISPONIVEL";
				} else {
					return "NÃO ENCONTRADO";
				}
			}
			case data:
				return unidade.getSomente_data_fila();
			case hora_fila: {
				return LocalDateTime.ofInstant(unidade.getData_hora_fila().toInstant(), ZoneId.systemDefault())
						.toLocalTime();

			}
			case hora_entrada: {
				try {
					return LocalDateTime.ofInstant(unidade.getData_hora_entrada().toInstant(), ZoneId.systemDefault())
							.toLocalTime();
				} catch (Exception e) {
					return "";
				}
			}
			case hora_saida: {
				try {
					return LocalDateTime.ofInstant(unidade.getData_hora_saida().toInstant(), ZoneId.systemDefault())
							.toLocalTime();
				} catch (Exception e) {
					return "";
				}
			}
			case cliente: {
				return unidade.getProdutor().getNome_empresarial().toUpperCase();

			}
			case transportadora: {
				return unidade.getTransportadora().getNome_empresarial().toUpperCase();

			}
			case motorista: {
				return unidade.getMotorista().getNome_empresarial().toUpperCase();

			}
			case placa:
				return unidade.getVeiculo().getPlaca_trator().toUpperCase();
			case produto:
				return unidade.getProduto().getNome_produto().toUpperCase();
			case origem:
				return unidade.getOrigem();
			case destino:
				return unidade.getDestino();
			case tem_nf: {
				if (unidade.getTem_nf() == 1) {
					return "S";
				} else {
					return "N";
				}
			}
			case tem_autorizacao: {
				if (unidade.getAutorizacao_movimentacao() == 1) {
					return "S";
				} else {
					return "N";
				}
			}
			case status: {
				int sts = unidade.getStatus();
				if (sts == 0) {
					return "EM FILA";
				} else if (sts == 1) {
					return "ENTRADA";
				} else if (sts == 2) {
					return "SAÍDA";
				} else if (sts == -1) {
					return "CANCELADO";
				}
			}

			case ultima_notificacao: {
				if (unidade.getNotificado_saida() == 1) {
					return "SAÍDA NOTIFICADA";
				} else if (unidade.getNotificado_entrada() == 1) {
					return "ENTRADA NOTIFICADA";
				} else if (unidade.getNotificado_em_fila() == 1) {
					return "EM FILA";
				} else {
					return "SEM NOTIFICAÇÕES";
				}
			}
			case umidade:
				return unidade.getUmidade() + "";
			case impureza:
				return unidade.getImpureza() + "";
			case ardidos:
				return unidade.getArdidos() + "";
			case peso_bruto:
				return unidade.getPeso_bruto() + "";
			case peso_tara:
				return unidade.getPeso_tara() + "";
			case peso_liquido:
				return unidade.getPeso_liquido() + "";
			case usuario:
				return unidade.getLogin().getNome();

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
			CadastroFilaMovimento contrato = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public CadastroFilaMovimento getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		public ArrayList<CadastroFilaMovimento> getValues() {
			return dados;
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(CadastroFilaMovimento contrato) {
			return dados.indexOf(contrato);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(CadastroFilaMovimento contrato) {
			dados.add(contrato);
			fireTableRowsInserted(indexOf(contrato), indexOf(contrato));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<CadastroFilaMovimento> dadosIn) {
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
		public void onRemove(CadastroFilaMovimento contrato) {
			int indexBefore = indexOf(contrato);// pega o indice antes de apagar
			dados.remove(contrato);
			fireTableRowsDeleted(indexBefore, indexBefore);
		}

		/**
		 * remove todos registros da lista
		 */
		public void onRemoveAll() {
			dados.clear();
			fireTableDataChanged();
		}

		public CadastroFilaMovimento onGet(int row) {
			return dados.get(row);
		}
	}

	class StatusFilaRenderEmbarque implements TableCellRenderer {

		public final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {

			if (value instanceof Date) {
				value = f.format(value);
			}

			Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
					column);
			((JLabel) renderer).setOpaque(true);

			String status = (String) table.getValueAt(row, 15);

			if (isSelected) {
				renderer.setBackground(new Color(139, 69, 19)); // marrom
				renderer.setForeground(Color.white);

			} else {
				if (status.equalsIgnoreCase("EM FILA")) {
					renderer.setBackground(new Color(255, 69, 0));
					renderer.setForeground(Color.black);

				} else if (status.equalsIgnoreCase("ENTRADA")) {
					renderer.setBackground(Color.yellow); // laranja
					renderer.setForeground(Color.black);

				} else if (status.equalsIgnoreCase("SAÍDA")) {
					renderer.setBackground(new Color(0, 51, 0)); // verde
					renderer.setForeground(Color.white);

				} else if (status.equalsIgnoreCase("CANCELADO")) {
					renderer.setBackground(new Color(154, 0, 0)); // vermelho
					renderer.setForeground(Color.white);
				} else {
					renderer.setBackground(new Color(154, 0, 0)); // vermelho
					renderer.setForeground(Color.black);
				}

			}

			return renderer;
		}
	}

	class NotificacaoFilaRenderEmbarque implements TableCellRenderer {

		public final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
					column);
			((JLabel) renderer).setOpaque(true);

			String status = (String) table.getValueAt(row, 16);

			if (isSelected) {
				renderer.setBackground(new Color(139, 69, 19)); // marrom
				renderer.setForeground(Color.white);

			} else {
				if (status.equalsIgnoreCase("SEM NOTIFICAÇÕES")) {
					renderer.setBackground(Color.red);
					renderer.setForeground(Color.white);

				} else if (status.equalsIgnoreCase("EM FILA")) {
					renderer.setBackground(Color.orange); // laranja
					renderer.setForeground(Color.black);

				} else if (status.equalsIgnoreCase("ENTRADA NOTIFICADA")) {
					renderer.setBackground(Color.yellow); // verde
					renderer.setForeground(Color.black);

				} else if (status.equalsIgnoreCase("SAÍDA NOTIFICADA")) {
					renderer.setBackground(Color.green); // verde
					renderer.setForeground(Color.white);

				}

			}

			return renderer;
		}
	}

	public static class FilaDesembarqueTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int id = 0;
		private final int romaneio = 1;
		private final int data = 2;
		private final int hora_fila = 3;
		private final int hora_entrada = 4;
		private final int hora_saida = 5;
		private final int motorista = 6;
		private final int placa = 7;
		private final int inscricao = 8;
		private final int produtor = 9;
		private final int produto = 10;
		private final int origem = 11;
		private final int destino = 12;
		private final int tem_nf = 13;
		private final int tem_autorizacao = 14;
		private final int status = 15;
		private final int ultima_notificacao = 16;
		private final int umidade = 17;
		private final int impureza = 18;
		private final int ardidos = 19;
		private final int peso_bruto = 20;
		private final int peso_tara = 21;
		private final int peso_liquido = 22;
		private final int usuario = 23;

		List<Color> rowColours = Arrays.asList(Color.RED, Color.GREEN, Color.CYAN);

		private final String colunas[] = { "ID", "ROMANEIO", "DATA", "HORA FILA", "HORA ENTRADA", "HORA SAÍDA",
				"MOTORISTA:", "PLACA:", "I.E", "PRODUTOR:", "PRODUTO:", "ORIGEM", "DESTINO", "TEM NF",
				"TEM AUTORIZAÇÃO", "STATUS", "ÚLTIMA NOTIFICAÇÃO:", "UMIDADE", "IMPUREZA", "ARDIDOS", "PESO BRUTO(kgs)",
				"PESO TARA(kgs)", "PESO LIQUIDO(kgs)", "USUARIO" };
		private final ArrayList<CadastroFilaMovimento> dados = new ArrayList<>();// usamos como dados uma lista genérica
		private Locale ptBr = new Locale("pt", "BR");
		private NumberFormat z = NumberFormat.getNumberInstance(); // de

		public FilaDesembarqueTableModel() {

		}

		@Override
		public int getColumnCount() {
			// retorna o total de colunas
			return colunas.length;
		}

		@Override
		public int getRowCount() {
			// retorna o total de linhas na tabela
			return dados.size();
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			// retorna o tipo de dado, para cada coluna
			switch (columnIndex) {
			case id:
				return Integer.class;
			case romaneio:
				return String.class;
			case data:
				return Date.class;
			case hora_fila:
				return LocalTime.class;
			case hora_entrada:
				return LocalTime.class;
			case hora_saida:
				return LocalTime.class;

			case motorista:
				return String.class;
			case placa:
				return String.class;
			case inscricao:
				return String.class;
			case produtor:
				return String.class;
			case produto:
				return String.class;
			case origem:
				return String.class;
			case destino:
				return String.class;
			case tem_nf:
				return String.class;
			case tem_autorizacao:
				return String.class;
			case status:
				return String.class;
			case ultima_notificacao:
				return String.class;
			case umidade:
				return String.class;
			case impureza:
				return String.class;
			case ardidos:
				return String.class;
			case peso_bruto:
				return String.class;
			case peso_tara:
				return String.class;
			case peso_liquido:
				return String.class;
			case usuario:
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
			CadastroFilaMovimento unidade = dados.get(rowIndex);

			// retorna o valor da coluna
			switch (columnIndex) {
			case id:
				return unidade.getId();
			case romaneio: {
				if (unidade.getRomaneio() != null && unidade.getRomaneio().getNumero_romaneio() > 0) {
					return unidade.getRomaneio().getNumero_romaneio() + " DISPONIVEL";
				} else {
					return "NÃO ENCONTRADO";
				}
			}
			case data:
				return unidade.getSomente_data_fila();

			case hora_fila: {
				return LocalDateTime.ofInstant(unidade.getData_hora_fila().toInstant(), ZoneId.systemDefault())
						.toLocalTime();

			}
			case hora_entrada: {
				try {
					return LocalDateTime.ofInstant(unidade.getData_hora_entrada().toInstant(), ZoneId.systemDefault())
							.toLocalTime();
				} catch (Exception e) {
					return "";
				}
			}
			case hora_saida: {
				try {
					return LocalDateTime.ofInstant(unidade.getData_hora_saida().toInstant(), ZoneId.systemDefault())
							.toLocalTime();
				} catch (Exception e) {
					return "";
				}
			}

			case motorista: {
				try {
					return unidade.getMotorista().getNome_empresarial().toUpperCase();
				}catch(Exception e) {
					return "Nome Motorista Inválido!";
				}
			}
			case placa: {
				try {
					return unidade.getVeiculo().getPlaca_trator().toUpperCase();
				} catch (Exception e) {
					return "Placa Inválida";
				}
			}
			case inscricao:
				return unidade.getProdutor().getIe();
			case produtor: {
					try {
						if(unidade.getProdutor().getTipo_pessoa() == 0){
							
							return unidade.getProdutor().getNome().trim().toUpperCase() + " "
									+ unidade.getProdutor().getSobrenome().trim().toUpperCase() + "/"
									+ unidade.getProdutor().getNome_empresarial().toUpperCase();
						}else {
							return unidade.getProdutor().getNome_empresarial().toUpperCase();

						}
					
					} catch (Exception e) {
						e.printStackTrace();
						try {
							return unidade.getProdutor().getNome_empresarial().toUpperCase();
						}catch(Exception t) {
							t.printStackTrace();

							return "Nome Produtor Inválido";

						}

					}

				
			}
			case produto:{
				return unidade.getProduto().getNome_produto().toUpperCase();
			}
			case origem:
				return unidade.getOrigem();
			case destino:
				return unidade.getDestino();
			case tem_nf: {
				if (unidade.getTem_nf() == 1) {
					return "S";
				} else {
					return "N";
				}
			}
			case tem_autorizacao: {
				if (unidade.getAutorizacao_movimentacao() == 1) {
					return "S";
				} else {
					return "N";
				}
			}
			case status: {
				int sts = unidade.getStatus();
				if (sts == 0) {
					return "EM FILA";
				} else if (sts == 1) {
					return "ENTRADA";
				} else if (sts == 2) {
					return "SAÍDA";
				} else if (sts == -1) {
					return "CANCELADO";
				}
			}

			case ultima_notificacao: {
				if (unidade.getNotificado_saida() == 1) {
					return "SAÍDA NOTIFICADA";
				} else if (unidade.getNotificado_entrada() == 1) {
					return "ENTRADA NOTIFICADA";
				} else if (unidade.getNotificado_em_fila() == 1) {
					return "EM FILA";
				} else {
					return "SEM NOTIFICAÇÕES";
				}
			}
			case umidade:
				return unidade.getUmidade() + "";
			case impureza:
				return unidade.getImpureza() + "";
			case ardidos:
				return unidade.getArdidos() + "";
			case peso_bruto:
				return unidade.getPeso_bruto() + "";
			case peso_tara:
				return unidade.getPeso_tara() + "";
			case peso_liquido:
				return unidade.getPeso_liquido() + "";
			case usuario:
				return unidade.getLogin().getNome();

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
			CadastroFilaMovimento contrato = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public CadastroFilaMovimento getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		public ArrayList<CadastroFilaMovimento> getValues() {
			return dados;
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(CadastroFilaMovimento contrato) {
			return dados.indexOf(contrato);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(CadastroFilaMovimento contrato) {
			dados.add(contrato);
			fireTableRowsInserted(indexOf(contrato), indexOf(contrato));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<CadastroFilaMovimento> dadosIn) {
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
		public void onRemove(CadastroFilaMovimento contrato) {
			int indexBefore = indexOf(contrato);// pega o indice antes de apagar
			dados.remove(contrato);
			fireTableRowsDeleted(indexBefore, indexBefore);
		}

		/**
		 * remove todos registros da lista
		 */
		public void onRemoveAll() {
			dados.clear();
			fireTableDataChanged();
		}

		public CadastroFilaMovimento onGet(int row) {
			return dados.get(row);
		}
	}

	class StatusFilaRenderDesembarque implements TableCellRenderer {

		public final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {

			if (value instanceof Date) {
				value = f.format(value);
			}

			Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
					column);
			((JLabel) renderer).setOpaque(true);

			String status = (String) table.getValueAt(row, 15);

			if (isSelected) {
				renderer.setBackground(new Color(139, 69, 19)); // marrom
				renderer.setForeground(Color.white);

			} else {
				if (status.equalsIgnoreCase("EM FILA")) {
					renderer.setBackground(new Color(255, 69, 0));
					renderer.setForeground(Color.black);

				} else if (status.equalsIgnoreCase("ENTRADA")) {
					renderer.setBackground(Color.yellow); // laranja
					renderer.setForeground(Color.black);

				} else if (status.equalsIgnoreCase("SAÍDA")) {
					renderer.setBackground(new Color(0, 51, 0)); // verde
					renderer.setForeground(Color.white);

				} else if (status.equalsIgnoreCase("CANCELADO")) {
					renderer.setBackground(new Color(153, 0, 0)); // vermelho
					renderer.setForeground(Color.white);
				} else {
					renderer.setBackground(new Color(153, 0, 0)); // vermelho
					renderer.setForeground(Color.black);
				}

			}

			return renderer;
		}
	}

	class NotificacaoFilaRenderDesembarque implements TableCellRenderer {

		public final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
					column);
			((JLabel) renderer).setOpaque(true);

			String status = (String) table.getValueAt(row, 16);

			if (isSelected) {
				renderer.setBackground(new Color(139, 69, 19)); // marrom
				renderer.setForeground(Color.white);

			} else {
				if (status.equalsIgnoreCase("SEM NOTIFICAÇÕES")) {
					renderer.setBackground(Color.red);
					renderer.setForeground(Color.white);

				} else if (status.equalsIgnoreCase("EM FILA")) {
					renderer.setBackground(Color.yellow); // laranja
					renderer.setForeground(Color.black);

				} else if (status.equalsIgnoreCase("ENTRADA NOTIFICADA")) {
					renderer.setBackground(Color.blue); // verde
					renderer.setForeground(Color.white);

				} else if (status.equalsIgnoreCase("SAÍDA NOTIFICADA")) {
					renderer.setBackground(Color.green); // verde
					renderer.setForeground(Color.white);

				}

			}

			return renderer;
		}
	}
	
	
	public String mensagemAdicional(String mensagem_a_enviar) {
		
		CadastroMensagem msg_adicional = new GerenciarBancoMensagem().getMensagem();
		if(msg_adicional != null) {
			System.out.println("mensagem adicional nao é nula");
			if(msg_adicional.getConteudo() != null && msg_adicional.getConteudo().length() > 0 ) {
				System.out.println("conteudo da mensagem adicional nao é nula");

				 String mensagem_adicional = msg_adicional.getConteudo();
				 mensagem_adicional= Normalizer.normalize(mensagem_adicional, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
				  
				  String mensagem_adicional_quebrada[] = mensagem_adicional.split("\n");
				  String mensagem_adicional_final  = "";
				  for(int i = 0; i < mensagem_adicional_quebrada.length; i++) {
					  mensagem_adicional_final = mensagem_adicional_final + mensagem_adicional_quebrada[i] + "\\n";
				  }
				  mensagem_adicional = mensagem_adicional_final;
				  
				  mensagem_a_enviar += ("\\n" + mensagem_adicional);
				  return mensagem_a_enviar;
				  
				  
			}else {
				System.out.println("conteudo da mensagem adicional  é nula");

				return mensagem_a_enviar;

			}
		
	
		
		}else {
			System.out.println("mensagem adicional é nula");

			return mensagem_a_enviar;
		}

	}

	public void getDadosGlobais() {
		// gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		GerenciadorLog = dados.getGerenciadorLog();
		configs_globais = dados.getConfigs_globais();

		servidor_unidade = configs_globais.getServidorUnidade();
		// usuario logado
		login = dados.getLogin();

	}
}
