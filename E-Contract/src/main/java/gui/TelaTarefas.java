package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import net.miginfocom.swing.MigLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GraphicsEnvironment;

import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;

import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import main.java.cadastros.CadastroAviso;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroContrato.CadastroPagamento;
import main.java.cadastros.CadastroContrato.CadastroPagamentoContratual;
import main.java.cadastros.CadastroContrato.CadastroTarefa;
import main.java.cadastros.CadastroContrato.Recebimento;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroModelo;
import main.java.cadastros.CadastroNFe;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroRomaneio;
import main.java.cadastros.CadastroSafra;
import main.java.cadastros.CadastroTarefaGeral;
import main.java.cadastros.ContaBancaria;
import main.java.cadastros.Contato;
import main.java.cadastros.DreSimples;
import main.java.cadastros.FinanceiroPagamentoCompleto;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.classesExtras.Endereco;
import main.java.classesExtras.RenderizadorDreSimples;
import main.java.classesExtras.RenderizadorTarefas;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoFinanceiroPagamento;
import main.java.conexaoBanco.GerenciarBancoFinanceiroPagamentoEmprestimo;
import main.java.conexaoBanco.GerenciarBancoNotasFiscais;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoRomaneios;
import main.java.conexaoBanco.GerenciarBancoSafras;
import main.java.gui.TelaCadastroCliente;
import main.java.gui.TelaMain;
import main.java.gui.TelaRomaneios;
import main.java.gui_internal.TelaFinanceiroMostrarReceitasDespesas;
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
import main.java.cadastros.CentroCusto;
import main.java.outros.DadosGlobais;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroGrupo;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoGrupos;
import main.java.conexaoBanco.GerenciarBancoLogin;
import main.java.cadastros.CadastroProduto;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroSafra;
import main.java.classesExtras.CBProdutoPersonalizado;
import main.java.classesExtras.CBProdutoRenderPersonalizado;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoSafras;

import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class TelaTarefas extends JFrame {

	private final JPanel painelPrincipal = new JPanel();
	private TelaTarefas isto;
	private JDialog telaPai;

	private DefaultListModel<CadastroContrato.CadastroTarefa> listModelMinhasTarefas;
	private RenderizadorTarefas renderMinhasTarefas;

	private DefaultListModel<CadastroContrato.CadastroTarefa> listModelTarefasDesignadas;
	private RenderizadorTarefas renderTarefasDesignadas;

	private JComboBox cbStatusTarefa;
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;

	private JLabel lblTotalTarefasEmAndamento, lblTotal, lblTotalTarefasConcluidas;
	private JTextField entCriador;
	private JTextField entExecutor;

	public TelaTarefas(Window janela_pai) {
		// setModal(true);

		getDadosGlobais();
		isto = this;
		setResizable(true);
		setTitle("E-Contract - Minhas Tarefas");

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

		painelPrincipal.setBackground(new Color(0, 0, 102));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(new MigLayout("", "[grow][309px,grow][grow][grow]", "[][][26px][grow][][grow][][26px][grow][grow][][][]"));

		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setBackground(Color.WHITE);
		painelPrincipal.add(panel, "flowx,cell 0 0 4 1,grow");
		panel.setLayout(new MigLayout("", "[141px][grow]", "[64px]"));

		JLabel lblNewLabel = new JLabel("Tarefas");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 40));
		panel.add(lblNewLabel, "cell 0 0,alignx left,aligny bottom");

		JLabel lblNewLabel_2 = new JLabel("");
		panel.add(lblNewLabel_2, "cell 1 0,growx,aligny top");
		lblNewLabel_2.setIcon(new ImageIcon(TelaTarefas.class.getResource("/imagens/icone_tarefa.png")));

		JPanel panel_1 = new JPanel();
		panel_1.setOpaque(false);
		panel_1.setForeground(new Color(0, 0, 102));
		panel_1.setBackground(new Color(0, 102, 153));
		painelPrincipal.add(panel_1, "cell 0 1 4 1,grow");
		panel_1.setLayout(new MigLayout("", "[][][grow]", "[grow]"));

		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, "flowx,cell 0 0,alignx left,growy");
		panel_2.setBackground(Color.WHITE);
		panel_2.setLayout(new MigLayout("", "[55px][11px][55px][87px][11px][55px][117px][11px]", "[16px][8px][21px]"));

		JLabel lblNewLabel_4 = new JLabel("New label");
		lblNewLabel_4.setForeground(Color.WHITE);
		panel_2.add(lblNewLabel_4, "cell 0 0,alignx left,aligny top");

		lblTotal = new JLabel("0");
		lblTotal.setBorder(new LineBorder(new Color(255, 255, 255), 1, true));
		lblTotal.setOpaque(true);
		lblTotal.setBackground(Color.WHITE);
		lblTotal.setForeground(new Color(0, 0, 51));
		lblTotal.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_2.add(lblTotal, "cell 1 0 1 3,growx,aligny center");

		JLabel lblNewLabel_4_1 = new JLabel("New label");
		lblNewLabel_4_1.setForeground(Color.WHITE);
		panel_2.add(lblNewLabel_4_1, "cell 2 0,alignx left,aligny top");

		JLabel lblNewLabel_4_1_1 = new JLabel("New label");
		lblNewLabel_4_1_1.setForeground(Color.WHITE);
		panel_2.add(lblNewLabel_4_1_1, "cell 5 0,alignx left,aligny top");

		JLabel lblNewLabel_3_2 = new JLabel("Tudo");
		lblNewLabel_3_2.setForeground(new Color(0, 0, 51));
		lblNewLabel_3_2.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_2.add(lblNewLabel_3_2, "cell 0 2,alignx right,aligny top");

		JLabel lblNewLabel_3 = new JLabel("Concluídas");
		lblNewLabel_3.setForeground(new Color(0, 51, 0));
		lblNewLabel_3.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_2.add(lblNewLabel_3, "cell 3 2,alignx left,aligny top");

		lblTotalTarefasConcluidas = new JLabel("0");
		lblTotalTarefasConcluidas.setBorder(new LineBorder(Color.WHITE, 1, true));
		lblTotalTarefasConcluidas.setOpaque(true);
		lblTotalTarefasConcluidas.setForeground(new Color(0, 51, 0));
		lblTotalTarefasConcluidas.setBackground(Color.WHITE);
		panel_2.add(lblTotalTarefasConcluidas, "cell 4 0 1 3,alignx left,aligny center");
		lblTotalTarefasConcluidas.setFont(new Font("SansSerif", Font.BOLD, 16));

		JLabel lblNewLabel_3_1 = new JLabel("Em Andamento");
		lblNewLabel_3_1.setForeground(new Color(255, 102, 0));
		panel_2.add(lblNewLabel_3_1, "cell 6 2,alignx left,aligny top");
		lblNewLabel_3_1.setFont(new Font("SansSerif", Font.BOLD, 16));

		lblTotalTarefasEmAndamento = new JLabel("0");
		lblTotalTarefasEmAndamento.setBorder(new LineBorder(Color.WHITE, 1, true));
		lblTotalTarefasEmAndamento.setOpaque(true);
		lblTotalTarefasEmAndamento.setBackground(Color.WHITE);
		lblTotalTarefasEmAndamento.setForeground(new Color(255, 102, 0));
		panel_2.add(lblTotalTarefasEmAndamento, "cell 7 0 1 3,alignx left,aligny center");
		lblTotalTarefasEmAndamento.setFont(new Font("SansSerif", Font.BOLD, 16));
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		panel_1.add(panel_3, "cell 1 0 2 1,grow");
		panel_3.setLayout(new MigLayout("", "[][grow][][grow][][][][grow]", "[][]"));
		
		JLabel lblNewLabel_5 = new JLabel("Criador:");
		lblNewLabel_5.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_3.add(lblNewLabel_5, "cell 0 0,alignx trailing");
		
		entCriador = new JTextField();
		entCriador.setFont(new Font("SansSerif", Font.BOLD, 16));
		entCriador.setColumns(10);
		panel_3.add(entCriador, "cell 1 0,growx");
		
		JLabel lblNewLabel_5_2_2 = new JLabel("Status:");
		lblNewLabel_5_2_2.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_3.add(lblNewLabel_5_2_2, "cell 2 0,alignx trailing");
		
		 cbStatusTarefa = new JComboBox();
		 cbStatusTarefa.setFont(new Font("SansSerif", Font.BOLD, 16));
		 panel_3.add(cbStatusTarefa, "cell 3 0,growx");
		 cbStatusTarefa.addItem("Todos");
		 cbStatusTarefa.addItem("Concluida");
		 cbStatusTarefa.addItem("Em Andamento");
		 
		 cbStatusTarefa.setSelectedIndex(2);
		 		
		JLabel lblNewLabel_5_1 = new JLabel("Executor:");
		lblNewLabel_5_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_3.add(lblNewLabel_5_1, "cell 0 1,alignx trailing");
		
		entExecutor = new JTextField();
		entExecutor.setFont(new Font("SansSerif", Font.BOLD, 16));
		entExecutor.setColumns(10);
		panel_3.add(entExecutor, "cell 1 1,growx");
		
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.setBackground(new Color(255, 0, 0));
		btnLimpar.setForeground(Color.WHITE);
		btnLimpar.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_3.add(btnLimpar, "cell 4 1,alignx center");
		
		JButton btnFiltrar = new JButton("Filtrar");
		btnFiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filtrar();
			}
		});
		btnFiltrar.setForeground(Color.WHITE);
		btnFiltrar.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnFiltrar.setBackground(new Color(0, 0, 153));
		panel_3.add(btnFiltrar, "cell 5 1,alignx center");
		
		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisar();
			}
		});
		btnPesquisar.setForeground(Color.WHITE);
		btnPesquisar.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnPesquisar.setBackground(new Color(0, 51, 0));
		panel_3.add(btnPesquisar, "cell 7 1,alignx center");
		
		JLabel lblNewLabel_1 = new JLabel("Criados por mim:");
		lblNewLabel_1.setBackground(Color.WHITE);
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("SansSerif", Font.BOLD, 20));
		painelPrincipal.add(lblNewLabel_1, "cell 0 2 2 1,alignx left,growy");

		JLabel lblNewLabel_1_1 = new JLabel("Designadas a mim:");
		lblNewLabel_1_1.setBackground(Color.WHITE);
		lblNewLabel_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1.setFont(new Font("SansSerif", Font.BOLD, 20));
		painelPrincipal.add(lblNewLabel_1_1, "cell 2 2,alignx left,aligny top");

		JList listMinhasTarefas = new JList();

		JScrollPane scrollPaneMinhasTarefas = new JScrollPane(listMinhasTarefas);
		painelPrincipal.add(scrollPaneMinhasTarefas, "cell 0 3 2 10,grow");

		listMinhasTarefas.setOpaque(false);
		listMinhasTarefas.setBackground(Color.WHITE);

		listModelMinhasTarefas = new DefaultListModel<CadastroContrato.CadastroTarefa>();
		renderMinhasTarefas = new RenderizadorTarefas();

		listMinhasTarefas.setModel(listModelMinhasTarefas);
		listMinhasTarefas.setCellRenderer(renderMinhasTarefas);

		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {

					int index = listMinhasTarefas.locationToIndex(e.getPoint());
					if (index >= 0) {
						CadastroContrato.CadastroTarefa tarefa = (CadastroContrato.CadastroTarefa) listMinhasTarefas.getModel().getElementAt(index);
						
						GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
						int cont = gerenciar.getContratoPorTarefa(tarefa.getId_tarefa());

						CadastroContrato contrato = gerenciar.getContrato(cont);

						if (contrato.getSub_contrato() == 1) {
							// e um subcontrato
							// pegar contrato pai
							CadastroContrato contratopai = gerenciar.getContratoPai(contrato.getId());
							TelaGerenciarContrato telagerenciar = new TelaGerenciarContrato(contrato, isto);
							telagerenciar.abrirAbaTarefasSubContrato(contrato);
							telagerenciar.setVisible(true);

						} else {

							TelaGerenciarContrato telagerenciar = new TelaGerenciarContrato(contrato, isto);
							telagerenciar.abrirAbaTarefasContrato();
							telagerenciar.setVisible(true);
						}
						
					}
				}
			}
		};
		listMinhasTarefas.addMouseListener(mouseListener);
		
		JList listTarefasDesignadas = new JList();
		JScrollPane scrollPaneTarefasDesignadas = new JScrollPane(listTarefasDesignadas);
		painelPrincipal.add(scrollPaneTarefasDesignadas, "cell 2 3 2 10,grow");
		
		listModelTarefasDesignadas = new DefaultListModel<CadastroContrato.CadastroTarefa>();
		renderTarefasDesignadas = new RenderizadorTarefas();

		listTarefasDesignadas.setModel(listModelTarefasDesignadas);
		listTarefasDesignadas.setCellRenderer(renderTarefasDesignadas);


		MouseListener mouseListener2 = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {

					int index = listTarefasDesignadas.locationToIndex(e.getPoint());
					if (index >= 0) {
						CadastroContrato.CadastroTarefa tarefa = (CadastroContrato.CadastroTarefa) listTarefasDesignadas.getModel().getElementAt(index);
						
						GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
						int cont = gerenciar.getContratoPorTarefa(tarefa.getId_tarefa());

						CadastroContrato contrato = gerenciar.getContrato(cont);

						if (contrato.getSub_contrato() == 1) {
							// e um subcontrato
							// pegar contrato pai
							CadastroContrato contratopai = gerenciar.getContratoPai(contrato.getId());
							TelaGerenciarContrato telagerenciar = new TelaGerenciarContrato(contrato, isto);
							telagerenciar.abrirAbaTarefasSubContrato(contrato);
							telagerenciar.setVisible(true);

						} else {

							TelaGerenciarContrato telagerenciar = new TelaGerenciarContrato(contrato, isto);
							telagerenciar.abrirAbaTarefasContrato();
							telagerenciar.setVisible(true);
						}
					
						
					}
				}
			}
		};
		listTarefasDesignadas.addMouseListener(mouseListener2);
		
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		pesquisarSomenteEmAndamento();
		this.setLocationRelativeTo(janela_pai);

	}

	public void pesquisar() {

		int em_andamento = 0;
		int concluida = 0;

		listModelMinhasTarefas.clear();
		listModelTarefasDesignadas.clear();
		
		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
		ArrayList<CadastroContrato.CadastroTarefa> minhas_tarefas = gerenciar.getTarefasPorCriador(login.getId());
		ArrayList<CadastroContrato.CadastroTarefa> tarefas_designadas = gerenciar.getTarefasComoExecutor(login.getId());


		
		for (CadastroContrato.CadastroTarefa tf : minhas_tarefas) {

			if (tf.getStatus_tarefa() == 2) {
				// em andamento
				em_andamento++;

			} else if (tf.getStatus_tarefa() == 1) {
				// concluida
				concluida++;
			}
			
			listModelMinhasTarefas.addElement(tf);

		}

		
		for (CadastroContrato.CadastroTarefa tf : tarefas_designadas) {

			if (tf.getStatus_tarefa() == 2) {
				// em andamento
				em_andamento++;

			} else if (tf.getStatus_tarefa() == 1) {
				// concluida
				concluida++;
			}
			listModelTarefasDesignadas.addElement(tf);

		}
		

		
		lblTotalTarefasEmAndamento.setText(em_andamento + "");
		lblTotalTarefasConcluidas.setText(concluida + "");
		lblTotal.setText((concluida + em_andamento) + "");

	}

	
	public void pesquisarSomenteEmAndamento() {

		int em_andamento = 0;
		int concluida = 0;

		listModelMinhasTarefas.clear();
		listModelTarefasDesignadas.clear();
		
		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
		ArrayList<CadastroContrato.CadastroTarefa> minhas_tarefas = gerenciar.getTarefasPorCriador(login.getId());
		ArrayList<CadastroContrato.CadastroTarefa> tarefas_designadas = gerenciar.getTarefasComoExecutor(login.getId());


		
		for (CadastroContrato.CadastroTarefa tf : minhas_tarefas) {

			if (tf.getStatus_tarefa() == 2) {
				// em andamento
				em_andamento++;
				listModelMinhasTarefas.addElement(tf);

			} else if (tf.getStatus_tarefa() == 1) {
				// concluida
				concluida++;
			}
			

		}

		
		for (CadastroContrato.CadastroTarefa tf : tarefas_designadas) {

			if (tf.getStatus_tarefa() == 2) {
				// em andamento
				em_andamento++;
				listModelTarefasDesignadas.addElement(tf);

			} else if (tf.getStatus_tarefa() == 1) {
				// concluida
				concluida++;
			}

		}
		

		
		lblTotalTarefasEmAndamento.setText(em_andamento + "");
		lblTotalTarefasConcluidas.setText(concluida + "");
		lblTotal.setText((concluida + em_andamento) + "");

	}

	
	public void incluir( ArrayList<CadastroContrato.CadastroTarefa> minhas_tarefas, ArrayList<CadastroContrato.CadastroTarefa> tarefas_designadas)  {
		
		int em_andamento = 0;
		int concluida = 0;

		listModelMinhasTarefas.clear();
		listModelTarefasDesignadas.clear();

		
		for (CadastroContrato.CadastroTarefa tf : minhas_tarefas) {

			if (tf.getStatus_tarefa() == 2) {
				// em andamento
				em_andamento++;

			} else if (tf.getStatus_tarefa() == 1) {
				// concluida
				concluida++;
			}
			
			listModelMinhasTarefas.addElement(tf);

		}

		
		for (CadastroContrato.CadastroTarefa tf : tarefas_designadas) {

			if (tf.getStatus_tarefa() == 2) {
				// em andamento
				em_andamento++;

			} else if (tf.getStatus_tarefa() == 1) {
				// concluida
				concluida++;
			}
			listModelTarefasDesignadas.addElement(tf);

		}
		

		
		
	}
	
	public void filtrar() {
		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
		
		/********************************************minhas tarefas**********************************/

		ArrayList<CadastroContrato.CadastroTarefa> minhas_tarefas = gerenciar.getTarefasPorCriador(login.getId());

		
		
		List<CadastroContrato.CadastroTarefa> lista_filtrada = minhas_tarefas;

		int status_tarefa = cbStatusTarefa.getSelectedIndex();

		if (status_tarefa != 0) {

			lista_filtrada = minhas_tarefas.stream()
					.filter(p -> p.getStatus_tarefa() == status_tarefa)
					.collect(Collectors.toList());

			minhas_tarefas = (ArrayList<CadastroContrato.CadastroTarefa>) lista_filtrada;

		}
		
		

		String nome_criador = entCriador.getText();
		if (checkString(nome_criador)) {
			lista_filtrada = minhas_tarefas.stream()
					.filter(p -> (p.getCriador().getNome() + " " + p.getCriador().getSobrenome()).contains(nome_criador))
					.collect(Collectors.toList());
			minhas_tarefas = (ArrayList<CadastroContrato.CadastroTarefa>) lista_filtrada;

		}

		String nome_executor = entExecutor.getText();
		if (checkString(nome_executor)) {
			lista_filtrada = minhas_tarefas.stream()
					.filter(p -> (p.getExecutor().getNome() + " " + p.getExecutor().getSobrenome()).contains(nome_executor))
					.collect(Collectors.toList());
			minhas_tarefas = (ArrayList<CadastroContrato.CadastroTarefa>) lista_filtrada;

		}

	

		
		/********************************************tarefas designadas**********************************/
		
		ArrayList<CadastroContrato.CadastroTarefa> tarefas_designadas = gerenciar.getTarefasComoExecutor(login.getId());

		
		 lista_filtrada = tarefas_designadas;

		if (status_tarefa != 0) {

			lista_filtrada = tarefas_designadas.stream()
					.filter(p -> p.getStatus_tarefa() == status_tarefa)
					.collect(Collectors.toList());

			tarefas_designadas = (ArrayList<CadastroContrato.CadastroTarefa>) lista_filtrada;

		}
		
		
		String nome_criador_designado = entCriador.getText();
		if (checkString(nome_criador_designado)) {
			lista_filtrada = tarefas_designadas.stream()
					.filter(p -> (p.getCriador().getNome() + " " + p.getCriador().getSobrenome()).contains(nome_criador_designado))
					.collect(Collectors.toList());
			tarefas_designadas = (ArrayList<CadastroContrato.CadastroTarefa>) lista_filtrada;

		}

		String nome_executor_designado = entExecutor.getText();
		if (checkString(nome_executor_designado)) {
			lista_filtrada = tarefas_designadas.stream()
					.filter(p -> (p.getExecutor().getNome() + " " + p.getExecutor().getSobrenome()).contains(nome_executor_designado))
					.collect(Collectors.toList());
			tarefas_designadas = (ArrayList<CadastroContrato.CadastroTarefa>) lista_filtrada;

		}

	
		
		
		
		incluir(minhas_tarefas,tarefas_designadas);
	
	}
	
	public class TarefaTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int id_tarefa = 0;
		private final int status = 1;
		private final int nome = 2;
		private final int descricao = 3;
		private final int mensagem = 4;
		private final int resposta = 5;
		private final int data = 6;
		private final int hora = 7;
		private final int criador = 8;
		private final int executor = 9;

		private final int hora_agendada = 10;

		private final int data_agendada = 11;
		private final int prioridade = 12;

		List<Color> rowColours = Arrays.asList(Color.RED, Color.GREEN, Color.CYAN);

		private final String colunas[] = { "ID", "Status", "Nome", "Descrição", "Mensagem", "Resposta", "Data", "Hora",
				"Criador", "Executor", "Hora Agendada", "Data Agendada", "Prioridade" };

		private final ArrayList<CadastroContrato.CadastroTarefa> dados = new ArrayList<>();// usamos como dados uma
																							// lista genérica
		// de
		// nfs

		public TarefaTableModel() {

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
			case id_tarefa: {
				return Integer.class;

			}
			case status: {
				return String.class;
			}
			case nome: {
				return String.class;
			}
			case descricao: {
				return String.class;
			}
			case mensagem: {
				return String.class;
			}
			case resposta: {
				return String.class;
			}
			case data: {
				return String.class;
			}
			case hora: {
				return String.class;
			}
			case criador: {
				return String.class;
			}
			case executor: {
				return String.class;
			}

			case hora_agendada: {
				return String.class;
			}
			case data_agendada: {
				return String.class;
			}
			case prioridade: {
				return String.class;
			}
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
			CadastroContrato.CadastroTarefa dado = dados.get(rowIndex);

			switch (columnIndex) {
			case id_tarefa: {
				dado.getId_tarefa();
			}
			case status: {
				if (dado.getStatus_tarefa() == 1) {
					return "Concluida";

				} else if (dado.getStatus_tarefa() == 2) {
					return "Em Andamento";

				}
			}
			case nome: {
				return dado.getNome_tarefa();
			}
			case descricao: {
				return dado.getDescricao_tarefa();
			}
			case mensagem: {
				return dado.getMensagem();
			}
			case resposta: {
				return dado.getResposta();
			}
			case data: {
				return dado.getData();
			}
			case hora: {
				return dado.getHora();
			}
			case criador: {
				return dado.getCriador().getNome() + " " + dado.getCriador().getSobrenome();
			}
			case executor: {
				return dado.getExecutor().getNome() + " " + dado.getExecutor().getSobrenome();

			}

			case hora_agendada: {
				return dado.getHora_agendada();
			}
			case data_agendada: {
				return dado.getData_agendada();
			}
			case prioridade: {

				if (dado.getPrioridade() == 1) {
					return "Imediata - Neste Momento";
				} else if (dado.getPrioridade() == 2) {
					return "Urgente - Nesta Hora";
				} else if (dado.getPrioridade() == 3) {
					return "Quanto Antes - Ainda Hoje";
				} else if (dado.getPrioridade() == 4) {
					return "Média - Ainda essa semana";
				} else if (dado.getPrioridade() == 5) {
					return "Leve - Ainda esse mês";
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

			return true;
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			CadastroContrato.CadastroTarefa ib = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public CadastroContrato.CadastroTarefa getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(CadastroContrato.CadastroTarefa dado) {
			return dados.indexOf(dado);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(CadastroContrato.CadastroTarefa dado) {
			dados.add(dado);
			fireTableRowsInserted(indexOf(dado), indexOf(dado));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<CadastroContrato.CadastroTarefa> dadosIn) {
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
		public void onRemove(CadastroContrato.CadastroTarefa dado) {
			int indexBefore = indexOf(dado);// pega o indice antes de apagar
			dados.remove(dado);
			fireTableRowsDeleted(indexBefore, indexBefore);
		}

		/**
		 * remove todos registros da lista
		 */
		public void onRemoveAll() {
			dados.clear();
			fireTableDataChanged();
		}

		public CadastroContrato.CadastroTarefa onGet(int row) {
			return dados.get(row);
		}
	}

	public void getDadosGlobais() {
		// gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		GerenciadorLog = dados.getGerenciadorLog();
		configs_globais = dados.getConfigs_globais();

		// usuario logado
		login = dados.getLogin();

	}

	public void setTelaPai(JDialog _tela_pai) {
		this.telaPai = _tela_pai;
	}

	public boolean checkString(String txt) {
		return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ");
	}
}
