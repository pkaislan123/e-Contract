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

import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroFilaMovimento;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroRomaneio;
import main.java.cadastros.Contato;
import main.java.cadastros.Lancamento;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoFilaMovimento;
import main.java.conexaoBanco.GerenciarBancoLancamento;
import main.java.gui.TelaContratos.EvenOddRenderer;
import main.java.gui.TelaContratos.StatusRecebimentoCellRender;
import main.java.gui.TelaFinanceiroLancamento.LancamentoTableModel;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.manipular.Whatsapp;
import main.java.outros.DadosGlobais;
import main.java.outros.GetData;
import main.java.tratamento_proprio.Log;
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
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.swing.border.MatteBorder;
import javax.swing.border.LineBorder;

public class TelaFilaMonitoria extends JFrame {

	private JTabbedPane painelPrincipal;
	private JPanel painelDescarga = new JPanel();
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private JTable tabela_desembarque;
	private TelaFilaMonitoria isto;
	private JButton btnNewButton;
	private JScrollPane scrollPane_1;
	private ArrayList<CadastroFilaMovimento> lista_fila_desembarque = new ArrayList<>();
	private FilaTableModel modelo_fila_desembarque = new FilaTableModel();
	private String servidor_unidade;

	private TableRowSorter<FilaTableModel> sorterDesembarque;
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

	private int flag_global = 0;
	private JLabel lblMotivo;
	private JPanel panel_3;
	private JLabel lblNewLabel_2;
	private JPanel panel_4;
	private JLabel lblNewLabel;
	private JScrollPane scrollPaneEmbarque;
	private JTable tabela_embarque;
	
	
	public TelaFilaMonitoria(int flag, Window window) {

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
		painelPrincipal.addTab("Fila", painelDescarga);
		painelDescarga.setLayout(new MigLayout("", "[grow][grow][][]", "[][][grow][grow][]"));
		sorterDesembarque = new TableRowSorter<FilaTableModel>(modelo_fila_desembarque);
		EvenOddRenderer renderer = new EvenOddRenderer();

		panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_3.setBackground(Color.WHITE);
		painelDescarga.add(panel_3, "cell 0 0 1 4,grow");
		panel_3.setLayout(new MigLayout("", "[grow]", "[][grow][][]"));

		lblNewLabel_2 = new JLabel("Fila de Desembarque");
		lblNewLabel_2.setFont(new Font("SansSerif", Font.BOLD, 22));
		panel_3.add(lblNewLabel_2, "cell 0 0,alignx center");

		tabela_desembarque = new JTable(modelo_fila_desembarque);
		tabela_desembarque.setRowSorter(sorterDesembarque);
		tabela_desembarque.setDefaultRenderer(Object.class, renderer);
		tabela_desembarque.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		tabela_desembarque.setRowHeight(30);

		tabela_desembarque.getColumnModel().getColumn(0).setPreferredWidth(40); // id
		tabela_desembarque.getColumnModel().getColumn(1).setPreferredWidth(180); // data
		tabela_desembarque.getColumnModel().getColumn(2).setPreferredWidth(100); // hora
		tabela_desembarque.getColumnModel().getColumn(3).setPreferredWidth(80); // hora
		tabela_desembarque.getColumnModel().getColumn(4).setPreferredWidth(80); // hora
		tabela_desembarque.getColumnModel().getColumn(5).setPreferredWidth(80); // hora

		tabela_desembarque.getColumnModel().getColumn(6).setPreferredWidth(200); // transportadora
		tabela_desembarque.getColumnModel().getColumn(7).setPreferredWidth(200); // motorista

		tabela_desembarque.getColumnModel().getColumn(8).setPreferredWidth(100); // placa
		tabela_desembarque.getColumnModel().getColumn(9).setPreferredWidth(200); // produtor
		tabela_desembarque.getColumnModel().getColumn(10).setPreferredWidth(150); // produto

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

		JScrollPane scrollPaneDesembarque = new JScrollPane(tabela_desembarque);
		panel_3.add(scrollPaneDesembarque, "cell 0 1,grow");
		scrollPaneDesembarque.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		panel_2 = new JPanel();
		panel_3.add(panel_2, "cell 0 2");
		panel_2.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel_2.setBackground(Color.WHITE);
		panel_2.setLayout(new MigLayout("",
				"[][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]",
				"[][][][][]"));

		lblNewLabel_7 = new JLabel("Totais:");
		lblNewLabel_7.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_2.add(lblNewLabel_7, "cell 0 0");

		lblTempoMedioEsperaDesembarque = new JLabel("10 horas");
		lblTempoMedioEsperaDesembarque.setFont(new Font("SansSerif", Font.BOLD, 32));
		panel_2.add(lblTempoMedioEsperaDesembarque, "cell 13 0 2 3,alignx center,aligny center");

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

		lblMotivo = new JLabel("");
		lblMotivo.setFont(new Font("SansSerif", Font.BOLD, 12));
		panel_2.add(lblMotivo, "cell 16 4 31 1,growx");

		panel_4 = new JPanel();
		panel_4.setBackground(Color.WHITE);
		painelDescarga.add(panel_4, "cell 1 0 1 5,grow");
		panel_4.setLayout(new MigLayout("", "[grow][]", "[][grow]"));

		lblNewLabel = new JLabel("Fila de Embarque");
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
		panel_4.add(lblNewLabel, "cell 0 0");

		
		tabela_embarque = new JTable(modelo_fila_desembarque);
		
		scrollPaneEmbarque = new JScrollPane(tabela_embarque);
		scrollPaneEmbarque.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		panel_4.add(scrollPaneEmbarque, "cell 0 1,grow");

		this.setContentPane(painelPrincipal);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		this.setLocationRelativeTo(window);

		pesquisar_fila_desembarque();

		if (flag == 1) {

		} else {
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

			lista_fila_desembarque = gerenciar.getFila(0,"%");

		} else {

			LocalDateTime currentDateTime = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			// lista_fila_desembarque =
			// gerenciar.getFila(currentDateTime.format(formatter));
			lista_fila_desembarque = gerenciar.getFila(0);
		}

		for (CadastroFilaMovimento cc : lista_fila_desembarque) {

			try {

				CadastroRomaneio anexo = gerenciar.getRomaneio(cc);
				if (anexo != null) {
					System.out.println("Romaneio encontrado: " + anexo.getNumero_romaneio());
					cc.setRomaneio(anexo);
				} else {
					System.out.println("Romaneio nao encontrado: ");

				}

			} catch (Exception e) {

			}

			modelo_fila_desembarque.onAdd(cc);
		}

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

		if (saida > 0)
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

	class EvenOddRenderer implements TableCellRenderer {

		public final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
					column);
			((JLabel) renderer).setOpaque(true);

			renderer.setFont(new Font("SansSerif", Font.PLAIN, 16));

			String status = (String) table.getValueAt(row, 15);

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

				} else if (status.equalsIgnoreCase("CANCELADO")) {
					renderer.setBackground(Color.red); // vermelho
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

			String status = (String) table.getValueAt(row, 15);

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

				} else if (status.equalsIgnoreCase("CANCELADO")) {
					renderer.setBackground(Color.red); // vermelho
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

	public static class FilaTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int id = 0;
		private final int romaneio = 1;
		private final int data = 2;
		private final int hora_fila = 3;
		private final int hora_entrada = 4;
		private final int hora_saida = 5;

		private final int transportadora = 6;
		private final int motorista = 7;
		private final int placa = 8;
		private final int produtor = 9;
		private final int produto = 10;
		private final int origem = 11;
		private final int destino = 12;
		private final int status = 13;

		List<Color> rowColours = Arrays.asList(Color.RED, Color.GREEN, Color.CYAN);

		private final String colunas[] = { "ID", "ROMANEIO", "DATA", "HORA FILA", "HORA ENTRADA", "HORA SAÍDA",
				"TRANSPORTADORA", "MOTORISTA:", "PLACA:", "PRODUTOR:", "PRODUTO:", "ORIGEM", "DESTINO", "STATUS"};
		private final ArrayList<CadastroFilaMovimento> dados = new ArrayList<>();// usamos como dados uma lista genérica
		private Locale ptBr = new Locale("pt", "BR");
		private NumberFormat z = NumberFormat.getNumberInstance(); // de

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
			case status:
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
			case transportadora: {
				try {
					if (unidade.getTransportadora().getTipo_pessoa() == 0) {
						return unidade.getTransportadora().getNome_empresarial().toUpperCase();

					} else {
						return unidade.getTransportadora().getNome_fantaia().toUpperCase();

					}
				} catch (Exception e) {

					return "";
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

			case motorista: {
				if (unidade.getProdutor().getTipo_pessoa() == 0) {
					return unidade.getMotorista().getNome_empresarial().toUpperCase();

				} else {
					return unidade.getMotorista().getNome_fantaia().toUpperCase();

				}
			}
			case placa:
				return unidade.getVeiculo().getPlaca_trator().toUpperCase();
			case produtor: {
				if (unidade.getProdutor().getTipo_pessoa() == 0) {
					return unidade.getProdutor().getNome_empresarial().toUpperCase();

				} else {
					return unidade.getProdutor().getNome_fantaia().toUpperCase();

				}
			}
			case produto:
				return unidade.getProduto().getNome_produto().toUpperCase();
			case origem:
				return unidade.getOrigem();
			case destino:
				return unidade.getDestino();
			
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
