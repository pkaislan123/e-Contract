package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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

import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroFilaMovimento;
import main.java.cadastros.Contato;
import main.java.cadastros.Lancamento;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoFilaMovimento;
import main.java.conexaoBanco.GerenciarBancoLancamento;
import main.java.gui.TelaContratos.EvenOddRenderer;
import main.java.gui.TelaContratos.StatusRecebimentoCellRender;
import main.java.gui.TelaFinanceiroLancamento.LancamentoTableModel;
import main.java.manipular.Whatsapp;
import main.java.outros.GetData;
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
import java.net.URL;

import javax.swing.border.MatteBorder;

public class TelaFila extends JFrame {

	private JTabbedPane painelPrincipal;
	private JPanel painelDescarga = new JPanel();
	private JPanel painelCarga = new JPanel();

	private JTable tabela_desembarque;
	private JButton btnMarcarVez;
	private TelaFila isto;
	private JButton btnNewButton;
	private JScrollPane scrollPane_1;
	private JTable tabela_embarque;
	private ArrayList<CadastroFilaMovimento> lista_fila_desembarque = new ArrayList<>();
	private FilaTableModel modelo_fila_desembarque = new FilaTableModel();

	private TableRowSorter<FilaTableModel> sorterDesembarque;
	private JPanel panel;
	private JLabel lblNewLabel;
	private JTextField entDataMenor;
	private JLabel lblAt;
	private JTextField entDataMaior;
	private JLabel lblNewLabel_1;
	private JButton btnPesquisar;
	private JButton btnFiltrar;
	private JButton btnLimparBusca;
	private JLabel lblNewLabel_2;
	private JTextField entTransportadora;
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
	private JButton btnAbrirTelaDePesquisa;

	private int flag_global = 0;
	
	public TelaFila(int flag , Window window) {

		isto = this;

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
		painelDescarga.setLayout(new MigLayout("", "[grow][][]", "[][][grow][]"));

		tabela_desembarque = new JTable(modelo_fila_desembarque);
		sorterDesembarque = new TableRowSorter<FilaTableModel>(modelo_fila_desembarque);
		tabela_desembarque.setRowSorter(sorterDesembarque);
		EvenOddRenderer renderer = new EvenOddRenderer();
		tabela_desembarque.setDefaultRenderer(Object.class, renderer);

		tabela_desembarque.setRowHeight(30);

		tabela_desembarque.getColumnModel().getColumn(0).setPreferredWidth(30); // id
		tabela_desembarque.getColumnModel().getColumn(1).setPreferredWidth(70); // data
		tabela_desembarque.getColumnModel().getColumn(2).setPreferredWidth(70); // hora
		tabela_desembarque.getColumnModel().getColumn(3).setPreferredWidth(70); // hora

		tabela_desembarque.getColumnModel().getColumn(4).setPreferredWidth(70); // hora

		tabela_desembarque.getColumnModel().getColumn(5).setPreferredWidth(200); // transportadora
		tabela_desembarque.getColumnModel().getColumn(6).setPreferredWidth(200); // motorista

		tabela_desembarque.getColumnModel().getColumn(7).setPreferredWidth(100); // placa
		tabela_desembarque.getColumnModel().getColumn(8).setPreferredWidth(200); // produtor
		tabela_desembarque.getColumnModel().getColumn(9).setPreferredWidth(150); // produto

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
				}

			}
		});
		
		lblNewLabel_16 = new JLabel("Fila para o Desembarque");
		lblNewLabel_16.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 25));
		painelDescarga.add(lblNewLabel_16, "cell 0 0,alignx center");

		// tabela_desembarque.getColumnModel().getColumn(14).setCellRenderer(new
		// StatusFilaRender());
		// tabela_desembarque.getColumnModel().getColumn(15).setCellRenderer(new
		// NotificacaoFilaRender());

		panel = new JPanel();
		panel.setForeground(Color.WHITE);
		panel.setBackground(Color.WHITE);
		painelDescarga.add(panel, "cell 0 1 3 1,grow");
		panel.setLayout(new MigLayout("",
				"[][grow][][grow][][grow][grow][][grow][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]",
				"[grow][][]"));

		btnPesquisar = new JButton("pesquisar");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisar_fila_desembarque();
			}
		});

		btnFiltrar = new JButton("filtrar");
		btnFiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filtrarDesembarque();
			}
		});

		lblNewLabel_2 = new JLabel("Transportadora:");
		lblNewLabel_2.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel.add(lblNewLabel_2, "cell 0 0,alignx trailing");

		entTransportadora = new JTextField();
		entTransportadora.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrarDesembarque();
			}
		});
		entTransportadora.setFont(new Font("SansSerif", Font.BOLD, 16));
		entTransportadora.setColumns(10);
		panel.add(entTransportadora, "cell 1 0,growx");

		lblNewLabel_3 = new JLabel("Motorista:");
		lblNewLabel_3.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel.add(lblNewLabel_3, "cell 2 0,alignx trailing");

		entMotorista = new JTextField();
		entMotorista.setFont(new Font("SansSerif", Font.BOLD, 16));
		entMotorista.setColumns(10);
		entMotorista.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrarDesembarque();
			}
		});

		panel.add(entMotorista, "cell 3 0,growx");

		lblNewLabel_4 = new JLabel("Placa:");
		lblNewLabel_4.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel.add(lblNewLabel_4, "cell 4 0,alignx trailing");

		entPlaca = new JTextField();
		entPlaca.setFont(new Font("SansSerif", Font.BOLD, 16));
		entPlaca.setColumns(10);
		entPlaca.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrarDesembarque();
			}
		});

		panel.add(entPlaca, "cell 5 0,growx");
		btnFiltrar.setForeground(Color.BLACK);
		btnFiltrar.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnFiltrar.setBackground(Color.ORANGE);
		panel.add(btnFiltrar, "cell 36 0,alignx right");

		btnLimparBusca = new JButton("limpar busca");
		btnLimparBusca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparFiltrosDesembarque();
				calcularDesembarque();
			}
		});
		btnLimparBusca.setForeground(Color.BLACK);
		btnLimparBusca.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnLimparBusca.setBackground(Color.RED);
		panel.add(btnLimparBusca, "cell 37 0,alignx center");
		btnPesquisar.setBackground(Color.BLUE);
		btnPesquisar.setForeground(Color.WHITE);
		btnPesquisar.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel.add(btnPesquisar, "cell 38 0,alignx right");

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
		entDataMenor.setEditable(false);
		entDataMenor.setEnabled(false);
		panel_1.add(entDataMenor, "cell 1 0");
		entDataMenor.setFont(new Font("SansSerif", Font.BOLD, 16));
		entDataMenor.setColumns(10);
		entDataMenor.setText(new GetData().getData());

		lblAt = new JLabel("Até");
		panel_1.add(lblAt, "cell 2 0");
		lblAt.setFont(new Font("SansSerif", Font.BOLD, 16));

		entDataMaior = new JTextField();
		entDataMaior.setEditable(false);
		entDataMaior.setEnabled(false);
		panel_1.add(entDataMaior, "cell 3 0");
		entDataMaior.setFont(new Font("SansSerif", Font.BOLD, 16));
		entDataMaior.setColumns(10);
		entDataMaior.setText(new GetData().getData());
		
		btnAbrirTelaDePesquisa = new JButton("abrir tela de pesquisa");
		btnAbrirTelaDePesquisa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new TelaFila(1, isto).setVisible(true);
				
			}
		});
		btnAbrirTelaDePesquisa.setForeground(Color.WHITE);
		btnAbrirTelaDePesquisa.setFont(new Font("SansSerif", Font.BOLD, 12));
		btnAbrirTelaDePesquisa.setBackground(new Color(51, 51, 0));
		panel.add(btnAbrirTelaDePesquisa, "cell 37 2");

		JScrollPane scrollPane = new JScrollPane(tabela_desembarque);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		painelDescarga.add(scrollPane, "cell 0 2,grow");
		
		btnSubirFilaDesembarque = new JButton("");
		btnSubirFilaDesembarque.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				int rowSel = tabela_desembarque.getSelectedRow();// pega o indice da linha na tabela
				int indiceDaLinha = tabela_desembarque.getRowSorter().convertRowIndexToModel(rowSel);
				
				CadastroFilaMovimento unidade_selecionada = modelo_fila_desembarque.getValue(indiceDaLinha);
				CadastroFilaMovimento unidade_anterior = modelo_fila_desembarque.getValue(indiceDaLinha -1);
				if(unidade_anterior != null) {
				
				GerenciarBancoFilaMovimento gerenciar = new GerenciarBancoFilaMovimento();

				int id_pivo = gerenciar.getMaxId();
				
				
				if(id_pivo > 0) {
					
					
					int id_unidade_selecionada = unidade_selecionada.getId();
					int id_unidade_anterior = unidade_anterior.getId();

					//1 passo
					boolean mudar_id_unidade_anterior = gerenciar.atualizarId(id_pivo, id_unidade_anterior);
					//colocar a unidade anterior no ultima posicao da fila
					if(mudar_id_unidade_anterior) {
						
						//2 passo
						//colocar a unidade selecionada na posicao acima
						boolean mudar_id_unidade_selecionada = gerenciar.atualizarId(id_unidade_anterior, id_unidade_selecionada);

						if(mudar_id_unidade_selecionada) {
							
							//3 passo
							//colocar a unidade anterior na posicao abaixo
							boolean mudar_id_unidade_pivo_para_unidade_selecionada = gerenciar.atualizarId(id_unidade_selecionada, id_pivo);
							if(mudar_id_unidade_pivo_para_unidade_selecionada) {
								JOptionPane.showMessageDialog(isto, "Fila reorganizada");
								pesquisar_fila_desembarque();
							}else {
								JOptionPane.showMessageDialog(isto, "Erro grave ao reorganizar a fila, não tente novamente!\nConsulte o administrador para que seja restaurado a fila!");
								pesquisar_fila_desembarque();

							}
							
							
						}else {
							//volte o id anterior da unidade anterior
							boolean voltar_estado_original = gerenciar.atualizarId(id_unidade_anterior, id_pivo);
							if(voltar_estado_original) {
								JOptionPane.showMessageDialog(isto, "Erro ao reorganizar a fila, tente novamente!\nSe o erro persistir, consulte o administrador!");

							}else{
								JOptionPane.showMessageDialog(isto, "Erro grave reorganizar a fila, não tente novamente!\nConsulte o administrador para que seja restaurado a fila!");

							}
						}
						
					}else {
						JOptionPane.showMessageDialog(isto, "Erro ao reorganizar a fila, não foi possivel mudar a posição, tente novamente!\nSe o erro persistir, consulte o administrador!");

					}
				}else {
					JOptionPane.showMessageDialog(isto, "Erro ao reorganizar a fila, pivo não encontrado, tente novamente!\nSe o erro persistir, consulte o administrador!");
				}
				
				}else {
					JOptionPane.showMessageDialog(isto, "Sem posições para alternar");

				}
				
			}
		});
		btnSubirFilaDesembarque.setBackground(Color.WHITE);
		btnSubirFilaDesembarque.setIcon(new ImageIcon(TelaFila.class.getResource("/imagens/seta-para-cima.png")));
		painelDescarga.add(btnSubirFilaDesembarque, "flowy,cell 1 2");

		panel_2 = new JPanel();
		panel_2.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel_2.setBackground(Color.WHITE);
		painelDescarga.add(panel_2, "flowx,cell 0 3,grow");
		panel_2.setLayout(new MigLayout("", "[][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]", "[][][][][]"));

		lblNewLabel_7 = new JLabel("Totais:");
		lblNewLabel_7.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_2.add(lblNewLabel_7, "cell 0 0");

		lblTempoMedioEsperaDesembarque = new JLabel("10 horas");
		lblTempoMedioEsperaDesembarque.setFont(new Font("SansSerif", Font.BOLD, 32));
		panel_2.add(lblTempoMedioEsperaDesembarque, "cell 13 0 2 3,alignx center,aligny center");

		btnMarcarVez = new JButton("Marcar");
		panel_2.add(btnMarcarVez, "cell 43 0,alignx right");
		btnMarcarVez.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaFilaCadastrarMovimento tela = new TelaFilaCadastrarMovimento(0, 0, isto);
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
		panel_2.add(lblNewLabel_11, "cell 1 1,growx");

		lblNewLabel_8 = new JLabel("Em Fila:");
		lblNewLabel_8.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_2.add(lblNewLabel_8, "cell 2 1,alignx right");

		lblEmFila = new JLabel("0 na fila");
		lblEmFila.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblEmFila.setForeground(Color.BLACK);
		panel_2.add(lblEmFila, "cell 3 1");

		lblAsd1 = new JLabel("Tempo Médio de Espera:");
		lblAsd1.setForeground(Color.BLACK);
		lblAsd1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_2.add(lblAsd1, "cell 12 1");

		lblNewLabel_12 = new JLabel("aaaaa");
		lblNewLabel_12.setOpaque(true);
		lblNewLabel_12.setForeground(Color.YELLOW);
		lblNewLabel_12.setBackground(Color.YELLOW);
		panel_2.add(lblNewLabel_12, "cell 1 2");

		lblNewLabel_9 = new JLabel("Entrada:");
		lblNewLabel_9.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_2.add(lblNewLabel_9, "cell 2 2");

		lblEmDesembarque = new JLabel("1 em Desembarque");
		lblEmDesembarque.setForeground(Color.BLACK);
		lblEmDesembarque.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_2.add(lblEmDesembarque, "cell 3 2");

		lblNewLabel_13 = new JLabel("aaaaa");
		lblNewLabel_13.setOpaque(true);
		lblNewLabel_13.setForeground(new Color(0, 51, 0));
		lblNewLabel_13.setBackground(new Color(0, 51, 0));
		panel_2.add(lblNewLabel_13, "cell 1 3");

		lblNewLabel_10 = new JLabel("Saída:");
		lblNewLabel_10.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_2.add(lblNewLabel_10, "cell 2 3,alignx right");

		lblDesembarcados = new JLabel("0 já Desembarcados");
		lblDesembarcados.setForeground(Color.BLACK);
		lblDesembarcados.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_2.add(lblDesembarcados, "cell 3 3");

		lblNewLabel_14 = new JLabel("Total:");
		lblNewLabel_14.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_2.add(lblNewLabel_14, "cell 2 4,alignx right");

		lblTotal = new JLabel("0 Caminhões");
		lblTotal.setForeground(Color.BLACK);
		lblTotal.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_2.add(lblTotal, "cell 3 4");

		lblNewLabel_15 = new JLabel("Unidades na Frente:");
		lblNewLabel_15.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_2.add(lblNewLabel_15, "cell 12 4,alignx right");

		lblUnidadesNaFrente = new JLabel("0 Caminhões");
		lblUnidadesNaFrente.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_2.add(lblUnidadesNaFrente, "cell 14 4");
		
		btnDescerFilaDesembarque = new JButton("");
		btnDescerFilaDesembarque.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				

				int rowSel = tabela_desembarque.getSelectedRow();// pega o indice da linha na tabela
				int indiceDaLinha = tabela_desembarque.getRowSorter().convertRowIndexToModel(rowSel);
				
				CadastroFilaMovimento unidade_selecionada = modelo_fila_desembarque.getValue(indiceDaLinha);
				CadastroFilaMovimento unidade_anterior = modelo_fila_desembarque.getValue(indiceDaLinha + 1);

				if(unidade_anterior != null) {
				
				GerenciarBancoFilaMovimento gerenciar = new GerenciarBancoFilaMovimento();

				int id_pivo = gerenciar.getMaxId();
				
				
				if(id_pivo > 0) {
					
					
					int id_unidade_selecionada = unidade_selecionada.getId();
					int id_unidade_anterior = unidade_anterior.getId();

					//1 passo
					boolean mudar_id_unidade_anterior = gerenciar.atualizarId(id_pivo, id_unidade_anterior);
					//colocar a unidade anterior no ultima posicao da fila
					if(mudar_id_unidade_anterior) {
						
						//2 passo
						//colocar a unidade selecionada na posicao acima
						boolean mudar_id_unidade_selecionada = gerenciar.atualizarId(id_unidade_anterior, id_unidade_selecionada);

						if(mudar_id_unidade_selecionada) {
							
							//3 passo
							//colocar a unidade anterior na posicao abaixo
							boolean mudar_id_unidade_pivo_para_unidade_selecionada = gerenciar.atualizarId(id_unidade_selecionada, id_pivo);
							if(mudar_id_unidade_pivo_para_unidade_selecionada) {
								JOptionPane.showMessageDialog(isto, "Fila reorganizada");
								pesquisar_fila_desembarque();
							}else {
								JOptionPane.showMessageDialog(isto, "Erro grave ao reorganizar a fila, não tente novamente!\nConsulte o administrador para que seja restaurado a fila!");
								pesquisar_fila_desembarque();

							}
							
							
						}else {
							//volte o id anterior da unidade anterior
							boolean voltar_estado_original = gerenciar.atualizarId(id_unidade_anterior, id_pivo);
							if(voltar_estado_original) {
								JOptionPane.showMessageDialog(isto, "Erro ao reorganizar a fila, tente novamente!\nSe o erro persistir, consulte o administrador!");

							}else{
								JOptionPane.showMessageDialog(isto, "Erro grave reorganizar a fila, não tente novamente!\nConsulte o administrador para que seja restaurado a fila!");

							}
						}
						
					}else {
						JOptionPane.showMessageDialog(isto, "Erro ao reorganizar a fila, não foi possivel mudar a posição, tente novamente!\nSe o erro persistir, consulte o administrador!");

					}
				}else {
					JOptionPane.showMessageDialog(isto, "Erro ao reorganizar a fila, pivo não encontrado, tente novamente!\nSe o erro persistir, consulte o administrador!");
				}
				
				}else {
					JOptionPane.showMessageDialog(isto, "Sem posições para alternar");

				}
				
				
			}
		});
		btnDescerFilaDesembarque.setBackground(Color.WHITE);
		btnDescerFilaDesembarque.setIcon(new ImageIcon(TelaFila.class.getResource("/imagens/seta-para-baixo.png")));
		painelDescarga.add(btnDescerFilaDesembarque, "cell 1 2,alignx right");

		painelCarga.setBackground(new Color(255, 255, 255));
		painelPrincipal.addTab("Fila de Embarque", painelCarga);
		painelCarga.setLayout(new MigLayout("", "[grow]", "[grow][]"));

		tabela_embarque = new JTable();

		scrollPane_1 = new JScrollPane(tabela_embarque);
		painelCarga.add(scrollPane_1, "cell 0 0,grow");

		btnNewButton = new JButton("Marcar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaFilaCadastrarMovimento tela = new TelaFilaCadastrarMovimento(0, 1, isto);
				tela.setVisible(true);
			}
		});
		btnNewButton.setBackground(new Color(0, 51, 0));
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelCarga.add(btnNewButton, "cell 0 1,alignx right");

		this.setContentPane(painelPrincipal);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		this.setLocationRelativeTo(window);

		pesquisar_fila_desembarque();
		
		
		
		
		if(flag == 1) {
			btnMarcarVez.setEnabled(false);
			btnMarcarVez.setVisible(false);
			
			
			btnDescerFilaDesembarque.setEnabled(false);
			btnDescerFilaDesembarque.setVisible(false);

			
			btnSubirFilaDesembarque.setEnabled(false);
			btnSubirFilaDesembarque.setVisible(false);
			
			btnAbrirTelaDePesquisa.setEnabled(false);
			btnAbrirTelaDePesquisa.setVisible(false);
			
			entDataMaior.setText("");
			entDataMaior.setEditable(true);
			entDataMaior.setEnabled(true);
			
			entDataMenor.setText("");
			entDataMenor.setEditable(true);
			entDataMenor.setEnabled(true);

		}else {
			setMenuDesembarque();
		}
		
		
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
	
	
	public int unidadesNaFrenteDesembarque( ) {
		int unidades_na_frente = 0;
		for (CadastroFilaMovimento unidade_na_tabela : modelo_fila_desembarque.getValues()) {

				if (unidade_na_tabela.getStatus() == 0) {
					unidades_na_frente++;
				}
			
		}
		unidades_na_frente--;
		return unidades_na_frente;
	}

	public void setMenuDesembarque() {
		JPopupMenu jPopupMenu = new JPopupMenu();
		JMenuItem jMenuItemAvançar = new JMenuItem();
		JMenuItem jMenuItemEnviarNotEmFila = new JMenuItem();

		jMenuItemAvançar.setText("Avançar Etapa");
		jMenuItemEnviarNotEmFila.setText("Enviar Notificação: Em Fila");

		jMenuItemAvançar.addActionListener(new java.awt.event.ActionListener() {
			// Importe a classe java.awt.event.ActionEvent
			public void actionPerformed(ActionEvent e) {
				try {
					GerenciarBancoFilaMovimento gerenciar = new GerenciarBancoFilaMovimento();

					int rowSel = tabela_desembarque.getSelectedRow();// pega o indice da linha na tabela
					int indiceDaLinha = tabela_desembarque.getRowSorter().convertRowIndexToModel(rowSel);// converte pro
																											// indice do
																											// model

					Whatsapp zap = new Whatsapp();

					CadastroFilaMovimento unidade = modelo_fila_desembarque.getValue(indiceDaLinha);
					String nome_motorista = unidade.getMotorista().getNome_empresarial().toUpperCase();

					String nome_produtor = unidade.getProdutor().getNome_empresarial().toUpperCase();

					String placa = unidade.getVeiculo().getPlaca_trator().toUpperCase();

					String produto = unidade.getProduto().getNome_produto().toUpperCase();
					ArrayList<Contato> lista_contratos = pesquisarContatos(unidade.getProdutor().getId());

					if (unidade.getNotificado_saida() == 1) {
						JOptionPane.showMessageDialog(isto, "Etapas Concluídas");

					} else if (unidade.getNotificado_entrada() == 1) {
						// entrada notificada, notificar saida
						// enviar notificacao
						String mensagem_enviar = "Mensagem Automatica dos Servidores da LD Armazens Gerais, nao e necessario responder! \\n \\n"
								+ nome_produtor + ", envio essa mensagem para notifica-lo que o motorista "
								+ nome_motorista + " no veiculo placa " + placa + " completou o desembarque de "
								+ produto + ".";

						for (Contato contato : lista_contratos) {
							try {
								boolean retorno2 = zap.enviarMensagem(contato.getCelular(), mensagem_enviar);

								if (retorno2) { // mensagem enviada
									boolean notificado = gerenciar.saidaNotificado(unidade.getId()); // notificacao
																										// mudada
									JOptionPane.showMessageDialog(isto,
											"Notificação de Desembarque Completo Enviada ao número: "
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

					} else if (unidade.getNotificado_em_fila() == 1) {
						// esta na fila, avancar para entrada
						// entrada notificada, notificar saida
						// enviar notificacao
						String mensagem_enviar = "Mensagem Automatica dos Servidores da LD Armazens Gerais, nao e necessario responder! \\n \\n"
								+ nome_produtor + ", envio essa mensagem para notifica-lo que o motorista "
								+ nome_motorista + " no veiculo placa " + placa + " entrou no armazém para desembarque "
								+ produto + ".";

						for (Contato contato : lista_contratos) {
							try {
								boolean retorno2 = zap.enviarMensagem(contato.getCelular(), mensagem_enviar);

								if (retorno2) { // mensagem enviada
									boolean notificado = gerenciar.entradaNotificada(unidade.getId()); // notificacao
																										// mudada
									JOptionPane.showMessageDialog(isto,
											"Notificação de entrada no armazém para Desembarque Enviada ao número: "
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

						int num_veiculos_na_frente = unidadesNaFrenteDesembarque();
						long tempoMedioEspera = longTempoMedioEsperoDesembarque();

						long previsao = tempoMedioEspera;
						if(num_veiculos_na_frente > 0)
						  previsao = tempoMedioEspera * ( (long ) num_veiculos_na_frente);
						
						LocalDateTime agora = LocalDateTime.now();
						agora = agora.plusMinutes(previsao);
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
						
						

						String sPrevisao =  agora.format(formatter);
						
						String sTempoMedioEspera = LocalTime.MIN.plus(Duration.ofMinutes(tempoMedioEspera)).toString() + " horas por caminhao";

						String mensagem_enviar = "Mensagem Automatica dos Servidores da LD Armazens Gerais, nao e necessario responder! \\n \\n"
								+ nome_produtor + ", envio essa mensagem para notifica-lo que o motorista "
								+ nome_motorista + " no veiculo placa " + placa
								+ " entrou na fila de desembarque de " + produto + ". \\n \\n->Ha " + num_veiculos_na_frente +
								" caminhoes na frente da fila \\n \\n->Tempo Medio de Espera: " + sTempoMedioEspera 
								+ " \\n \\n ->Previsao de Desembarque: "  + ( previsao == 0 ? " Sem dados disponiveis" : sPrevisao );



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
					String nome_motorista = unidade.getMotorista().getNome_empresarial();

					String nome_produtor = unidade.getProdutor().getNome_empresarial();

					String placa = unidade.getVeiculo().getPlaca_trator();

					String produto = unidade.getProduto().getNome_produto();
					ArrayList<Contato> lista_contatos = pesquisarContatos(unidade.getProdutor().getId());

					int num_veiculos_na_frente = unidadesNaFrenteDesembarque();
					long tempoMedioEspera = longTempoMedioEsperoDesembarque();

					long previsao = tempoMedioEspera;
					if(num_veiculos_na_frente > 0)
					  previsao = tempoMedioEspera * ( (long ) num_veiculos_na_frente);
					
					LocalDateTime agora = LocalDateTime.now();
					agora = agora.plusMinutes(previsao);
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
					
					

					String sPrevisao =  agora.format(formatter);
					
					String sTempoMedioEspera = LocalTime.MIN.plus(Duration.ofMinutes(tempoMedioEspera)).toString() + " horas por caminhao";

					String mensagem_enviar = "Mensagem Automatica dos Servidores da LD Armazens Gerais, nao e necessario responder! \\n \\n"
							+ nome_produtor + ", envio essa mensagem para notifica-lo que o motorista "
							+ nome_motorista + " no veiculo placa " + placa
							+ " entrou na fila de desembarque de " + produto + ". \\n \\n->Ha " + num_veiculos_na_frente +
							" caminhoes na frente da fila \\n \\n->Tempo Medio de Espera: " + sTempoMedioEspera 
							+ " \\n \\n ->Previsao de Desembarque: "  + ( previsao == 0 ? " Sem dados disponiveis" : sPrevisao );


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

					
				} catch (Exception f) {
					JOptionPane.showMessageDialog(null, "Erro:  " + f.getMessage() + "\nCausa: " + f.getCause());
				}

			}
		});

		jPopupMenu.add(jMenuItemAvançar);
		jPopupMenu.add(jMenuItemEnviarNotEmFila);

		
		
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

		
		if(flag_global == 1) {
			
			lista_fila_desembarque = gerenciar.getFila("%");
			
		}else {
			
			
			LocalDateTime currentDateTime = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			
			lista_fila_desembarque = gerenciar.getFila(currentDateTime.format(formatter));

		}
		
		for (CadastroFilaMovimento cc : lista_fila_desembarque) {
			modelo_fila_desembarque.onAdd(cc);
		}
		
		calcularDesembarque();

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
						dateToLocalDateTime(unidade.getData_hora_fila()),
						dateToLocalDateTime(unidade.getData_hora_saida()));

				total_tempo_entre_entrada_saida += tempo_entre_entrada_saida;
			}

		}
		
		if(saida > 0)
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
						dateToLocalDateTime(unidade.getData_hora_fila()),
						dateToLocalDateTime(unidade.getData_hora_saida()));

				total_tempo_entre_entrada_saida += tempo_entre_entrada_saida;
			}

		}

		lblEmFila.setText(em_fila + " na Fila");
		lblEmDesembarque.setText(entrada + " em Desembarque");
		lblDesembarcados.setText(saida + " já Desembarcados");
		int total = em_fila + entrada + saida;
		lblTotal.setText((total) + " Caminhões");

		if(saida > 0)
		total_tempo_entre_entrada_saida = total_tempo_entre_entrada_saida / (long) saida;
		lblTempoMedioEsperaDesembarque
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

		String data_inicial_filtrar_data_lancamento = entDataMenor.getText().replace(" ", "");
		String data_final_filtrar_data_lancamento = entDataMaior.getText().replace(" ", "");

		if (checkString(data_inicial_filtrar_data_lancamento) && checkString(data_final_filtrar_data_lancamento)) {
			Date data_menor = null;
			Date data_maior = null;
			try {
				data_menor = new SimpleDateFormat("dd/MM/yyyy").parse(data_inicial_filtrar_data_lancamento);
				data_maior = new SimpleDateFormat("dd/MM/yyyy").parse(data_final_filtrar_data_lancamento);

			} catch (ParseException i) {
				// TODO Auto-generated catch block
				i.printStackTrace();
			}

			Set<RowFilter<Object, Object>> datas = new HashSet<>();
			datas.add(RowFilter.dateFilter(RowFilter.ComparisonType.AFTER, data_menor, 1));
			datas.add(RowFilter.dateFilter(RowFilter.ComparisonType.EQUAL, data_menor, 1));
			filters.add(RowFilter.orFilter(datas));

			// filters.add( RowFilter.dateFilter(ComparisonType.AFTER, data_menor, 5) );
			// filters.add( RowFilter.dateFilter(ComparisonType.EQUAL, data_menor, 5) );

			// filters.add( RowFilter.dateFilter(ComparisonType.BEFORE, data_maior, 5) );
			// filters.add( RowFilter.dateFilter(ComparisonType.EQUAL, data_maior, 5) );
			Set<RowFilter<Object, Object>> datas_maior = new HashSet<>();
			datas_maior.add(RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE, data_maior, 1));
			datas_maior.add(RowFilter.dateFilter(RowFilter.ComparisonType.EQUAL, data_maior, 1));
			filters.add(RowFilter.orFilter(datas_maior));

		}

		// transportadora
		if (entTransportadora.getText() != null) {
			String s_trans = "";
			if (checkString(entTransportadora.getText())) {
				s_trans = entTransportadora.getText().toUpperCase();

				filters.add(RowFilter.regexFilter(s_trans, 5));
			}
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

				filters.add(RowFilter.regexFilter(s_produtor, 8));
			}
		}

		// produto
		if (entProduto.getText() != null) {
			String s_produto = "";
			if (checkString(entProduto.getText())) {
				s_produto = entProduto.getText().toUpperCase();

				filters.add(RowFilter.regexFilter(s_produto, 9));
			}
		}

		sorterDesembarque.setRowFilter(RowFilter.andFilter(filters));
		calcularDesembarque();
	}

	public void limparFiltrosDesembarque() {
		sorterDesembarque.setRowFilter(RowFilter.regexFilter(""));

	}

	class EvenOddRenderer implements TableCellRenderer {

		public final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
					column);
			((JLabel) renderer).setOpaque(true);

			renderer.setFont(new Font("SansSerif", Font.PLAIN, 16));

			String status = (String) table.getValueAt(row, 14);

			if (isSelected) {
				renderer.setBackground(new Color(139, 69, 19)); // marrom

			} else {

				if (status.equalsIgnoreCase("EM FILA")) {
					renderer.setBackground(new Color(255, 69, 0));
					renderer.setForeground(Color.white);

				} else if (status.equalsIgnoreCase("ENTRADA")) {
					renderer.setBackground(Color.yellow); // laranja
					renderer.setForeground(Color.black);

				} else if (status.equalsIgnoreCase("SAÍDA")) {
					renderer.setBackground(new Color(0, 51, 0)); // verde
					renderer.setForeground(Color.white);

				}

			}

			return renderer;
		}
	}

	class StatusFilaRender implements TableCellRenderer {

		public final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
					column);
			((JLabel) renderer).setOpaque(true);

			String status = (String) table.getValueAt(row, 14);

			if (isSelected) {
				renderer.setBackground(new Color(139, 69, 19)); // marrom
				renderer.setForeground(Color.white);

			} else {
				if (status.equalsIgnoreCase("EM FILA")) {
					renderer.setBackground(new Color(255, 69, 0));
					renderer.setForeground(Color.white);

				} else if (status.equalsIgnoreCase("ENTRADA")) {
					renderer.setBackground(Color.yellow); // laranja
					renderer.setForeground(Color.black);

				} else if (status.equalsIgnoreCase("SAÍDA")) {
					renderer.setBackground(new Color(0, 51, 0)); // verde
					renderer.setForeground(Color.white);

				}

			}

			return renderer;
		}
	}

	class NotificacaoFilaRender implements TableCellRenderer {

		public final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
					column);
			((JLabel) renderer).setOpaque(true);

			String status = (String) table.getValueAt(row, 15);

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

	public class FilaTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int id = 0;
		private final int data = 1;
		private final int hora_fila = 2;
		private final int hora_entrada = 3;
		private final int hora_saida = 4;

		private final int transportadora = 5;
		private final int motorista = 6;
		private final int placa = 7;
		private final int produtor = 8;
		private final int produto = 9;
		private final int origem = 10;
		private final int destino = 11;
		private final int tem_nf = 12;
		private final int tem_autorizacao = 13;
		private final int status = 14;
		private final int ultima_notificacao = 15;
		private final int umidade = 16;
		private final int impureza = 17;
		private final int ardidos = 18;
		private final int usuario = 19;

		List<Color> rowColours = Arrays.asList(Color.RED, Color.GREEN, Color.CYAN);

		private final String colunas[] = { "ID", "DATA", "HORA FILA", "HORA ENTRADA", "HORA SAÍDA", "TRANSPORTADORA",
				"MOTORISTA:", "PLACA:", "PRODUTOR:", "PRODUTO:", "ORIGEM", "DESTINO", "TEM NF", "TEM AUTORIZAÇÃO",
				"STATUS", "ÚLTIMA NOTIFICAÇÃO:", "UMIDADE", "IMPUREZA", "ARDIDOS", "USUARIO" };
		private final ArrayList<CadastroFilaMovimento> dados = new ArrayList<>();// usamos como dados uma lista genérica
																					// de

		public FilaTableModel() {

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
			case data:
				return Date.class;
			case hora_fila:
				return LocalTime.class;
			case hora_entrada:
				return LocalTime.class;
			case hora_saida:
				return LocalTime.class;
			case transportadora:
				return String.class;
			case motorista:
				return String.class;
			case placa:
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
			Locale ptBr = new Locale("pt", "BR");
			NumberFormat z = NumberFormat.getNumberInstance();

			// pega o dados corrente da linha
			CadastroFilaMovimento unidade = dados.get(rowIndex);

			// retorna o valor da coluna
			switch (columnIndex) {
			case id:
				return unidade.getId();
			case transportadora: {
				try {
					return unidade.getTransportadora().getNome_empresarial().toUpperCase();

				} catch (Exception e) {

					return "";
				}
			}
			case data: {

				SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
				String data_formatada = formatador.format(unidade.getData_hora_fila());

				Date data_menor;
				try {
					data_menor = formatador.parse(data_formatada);
					return data_menor;

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
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

			case motorista:
				return unidade.getMotorista().getNome_empresarial().toUpperCase();
			case placa:
				return unidade.getVeiculo().getPlaca_trator().toUpperCase();
			case produtor:
				return unidade.getProdutor().getNome_empresarial().toUpperCase();
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

}
