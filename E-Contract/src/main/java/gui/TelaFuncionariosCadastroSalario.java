
package main.java.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.DisplayMode;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.border.LineBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;

import org.apache.commons.io.FilenameUtils;

import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroDocumento;
import main.java.cadastros.CadastroFuncionario;
import main.java.cadastros.CadastroFuncionarioAdmissao;
import main.java.cadastros.CadastroFuncionarioBancoHoras;
import main.java.cadastros.CadastroFuncionarioCalculo;
import main.java.cadastros.CadastroFuncionarioDemissao;
import main.java.cadastros.CadastroFuncionarioEvento;
import main.java.cadastros.CadastroFuncionarioRotinaTrabalho;
import main.java.cadastros.CadastroFuncionarioSalario;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.EventoGlobal;
import main.java.cadastros.RegistroPontoDiario;
import main.java.cadastros.RegistroPontoDiarioCompleto;
import main.java.cadastros.RegistroPontoMensalCompleto;
import main.java.classesExtras.CombBoxRenderPersonalizadoContratoTrabalho;
import main.java.classesExtras.ComboBoxPersonalizadoContratoTrabalho;
import main.java.classesExtras.ComboBoxPersonalizadoFuncionario;
import main.java.classesExtras.ComboBoxRenderPersonalizadoFuncionario;
import main.java.cadastros.FinanceiroConta;
import main.java.cadastros.FinanceiroGrupoContas;
import main.java.cadastros.FinanceiroPagamento;
import main.java.cadastros.FinanceiroPagamentoCompleto;
import main.java.cadastros.FuncionarioContaAssociada;
import main.java.cadastros.Lancamento;
import main.java.cadastros.Parcela;
import main.java.cadastros.RegistroAuxiliarHoras;
import main.java.cadastros.FinanceiroConta;
import main.java.cadastros.FinanceiroConta;
import main.java.conexaoBanco.GerenciarBancoCentroCustos;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoDocumento;
import main.java.conexaoBanco.GerenciarBancoEventoGlobal;
import main.java.conexaoBanco.GerenciarBancoFinanceiroConta;
import main.java.conexaoBanco.GerenciarBancoFinanceiroGrupoContas;
import main.java.conexaoBanco.GerenciarBancoFinanceiroPagamento;
import main.java.conexaoBanco.GerenciarBancoFuncionarioBancoHoras;
import main.java.conexaoBanco.GerenciarBancoFuncionarioSalarios;
import main.java.conexaoBanco.GerenciarBancoFuncionarios;
import main.java.conexaoBanco.GerenciarBancoFuncionariosCalculos;
import main.java.conexaoBanco.GerenciarBancoFuncionariosContratoTrabalho;
import main.java.conexaoBanco.GerenciarBancoFuncionariosEventos;
import main.java.conexaoBanco.GerenciarBancoLancamento;
import main.java.conexaoBanco.GerenciarBancoParcelas;
import main.java.conexaoBanco.GerenciarBancoRegistroPonto;
import main.java.conexaoBanco.GerenciarBancoRotina;
import main.java.gui.TelaFuncionarioDemonstrativoDePonto.RegistroHoraLocal;
import main.java.gui.TelaFuncionarioDemonstrativoDePonto.Semana;
import main.java.gui.TelaRecursosHumanos.CellRenderRPDiario;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.manipular.ManipularTxt;
import main.java.outros.DadosGlobais;
import main.java.outros.GetData;
import main.java.outros.JTextFieldPersonalizado;
import main.java.relatoria.RelatorioSalario;
import main.java.tratamento_proprio.Log;
import main.java.conexaoBanco.GerenciarBancoFinanceiroConta;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

public class TelaFuncionariosCadastroSalario extends JFrame {
	private ArrayList<String> texto_atrazo;

	private JLabel lblTotalHorasExtras, lblTotalHorasAtrazo, lblTotalHorasNormais;
	Locale ptBr = new Locale("pt", "BR");
	private ArrayList<CadastroFuncionarioRotinaTrabalho> rotinas_global;
	private final JPanel painelPrincipal = new JPanel();
	private TelaFuncionariosCadastroSalario isto;
	private JTable tabela_demonstrativo;
	private JDialog telaPai;
	private int id_conta_associada_global = -1;
	private JTextField entAno;
	private long quantidade_banco_horas = 0;
	private RegistroPontoMensalCompletoTableModel modeloRegistroPonto = new RegistroPontoMensalCompletoTableModel();
	private JLabel lblCargo, lblFuncao, lblDataAdmissao, lblTipoContrato, lblStatusContrato;
	JLabel lblJornadaSegunda, lblJornadaTerca, lblJornadaQuarta, lblJornadaQuinta, lblJornadaSexta, lblJornadaSabado,
			lblJornadaDomingo;
	private String servidor_unidade;

	private int id_lancamento_global;
	private boolean tem_lancamento = false;
	private JComboBox cBMes;
	private int tipo_novo_banco_horas_global = -1;
	private long quantidade_novo_banco_horas_global = 0;
	private JRadioButton rdbtnDescontarAtrazosSim, rdbtnDescontarAtrazosNao;
	private AcrescimoTableModel modelAcrescimos = new AcrescimoTableModel();
	private DescontoTableModel modelDescontos = new DescontoTableModel();
	private JTable tabela_acrescimos, tabela_descontos;
	CadastroFuncionario func = null;
	private int tipo_banco_horas;
	private JLabel lblColaborador, lblValorTotalAcrescimosAReceber, lblValorTotalDescontosAAbater, lblTotalHorasMensal,
			lblUltimoValorSalarial, lblTotalHoras100, lblTotalHoras50, lblTotalHoras60;
	private JLabel lblDescricaoNovoBancoHoras, lblTipoNovoBancoHoras, lblQuantidadeNovoBancoHoras;
	private double inss_global = 0;
	private JLabel lblPisoSalarial, lblValorHoraNormal;
	private double piso_salarial = 0;
	private JLabel lblValorHora50, lblValorHora60, lblValorHoraNoturna, lblValorHora100;
	private JLabel lblQuantidadeHoras50, lblQuantidadeHoras60, lblQuantidadeHoras100, lblQuantidadeHorasExtras,
			lblValorTotalHorasExtras;
	private long tHTrabalhadas, tHMensal, tHAtrazo, tHExtras, tH100, tH50, tH60, tHNoturnas;
	private JLabel lblCalculoIrrf, lblQuantidadeHorasAtrazo, lblValorTotalHoras50, lblValorTotalHoras60,
			lblValorTotalHoras100, lblCalculoInss;
	private JLabel lblHorasNoturnas, lblTipoBancoHoras, lblTotalHorasNoturnas, lblDescricaoBancoHoras,
			lblQuantidadeBancoHoras, lblHorasExtrasAReceber;
	private boolean manter_global = true;
	private JRadioButton rdbtnDepositarSim, rdbtnDepositarNao, rdbtnInssSim, rdbtnIrrfSim, rdbtnIrrfNao, rdbtnInssNao;
	JLabel lblSalarioBaseAReceber;
	private JLabel lblValorTotalSalarioLiquido, lblValorTotalSalarioBruto, lblValorDiaria;
	private CadastroFuncionarioAdmissao ct_global;
	private double valor_total_descontos_global = 0, valor_total_acrescimos_global = 0, valor_total_horas_extras_global;
	private Window janela_pai_global;
	private RegistroAuxiliarHoras horas_global = new RegistroAuxiliarHoras();
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private JTextArea textAreaDescricaoAtrasos;
	private int total_dias_descontar = 0;

	private JRadioButton rdbtnManter, rdbtnPagar;
	private JTextArea lblInfoCalculoDepositoBH;

	public TelaFuncionariosCadastroSalario(CadastroFuncionario funcionario, int mes, String ano, Window janela_pai) {
		setTitle("Cálculo de Salário");
		getDadosGlobais();
		servidor_unidade = configs_globais.getServidorUnidade();

		isto = this;
		janela_pai_global = janela_pai;
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

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

		painelPrincipal.setBackground(Color.WHITE);
		this.setContentPane(tabbedPane);
		painelPrincipal.setLayout(new MigLayout("", "[grow]", "[grow][][grow][][][][grow][grow][grow][grow]"));

		tabbedPane.addTab("Demonstrativo de Ponto", null, painelPrincipal, null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 51, 0));
		painelPrincipal.add(panel, "cell 0 1,grow");
		panel.setLayout(new MigLayout("", "[100px][128px][][][][]", "[]"));

		JLabel lblNewLabel = new JLabel("Cálculo de Salário");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 40));
		panel.add(lblNewLabel, "cell 0 0,growx,aligny bottom");

		JPanel panel_1 = new JPanel();
		painelPrincipal.add(panel_1, "cell 0 3,grow");
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBackground(Color.WHITE);
		panel_1.setLayout(
				new MigLayout("", "[106px][][38px][][38px][][][][][][][][][][][][][][][][][][][][]", "[33px]"));

		JLabel lblNewLabel_1 = new JLabel("Colaborador:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_1.add(lblNewLabel_1, "cell 0 0,alignx left,aligny center");

		lblColaborador = new JLabel("Colaborador:");
		lblColaborador.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_1.add(lblColaborador, "cell 1 0");

		JLabel lblNewLabel_1_1 = new JLabel("Mês:");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_1.add(lblNewLabel_1_1, "cell 2 0,alignx left,aligny center");

		cBMes = new JComboBox();
		panel_1.add(cBMes, "cell 3 0,growx,aligny center");
		cBMes.addItem("JANEIRO");
		cBMes.addItem("FEVEREIRO");
		cBMes.addItem("MARÇO");
		cBMes.addItem("ABRIL");
		cBMes.addItem("MAIO");
		cBMes.addItem("JUNHO");
		cBMes.addItem("JULHO");
		cBMes.addItem("AGOSTO");
		cBMes.addItem("SETEMBRO");
		cBMes.addItem("OUTUBRO");
		cBMes.addItem("NOVEMBRO");
		cBMes.addItem("DEZEMBRO");

		JLabel lblNewLabel_1_1_1 = new JLabel("Ano:");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_1.add(lblNewLabel_1_1_1, "cell 4 0,alignx left,aligny center");

		entAno = new JTextField();
		entAno.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_1.add(entAno, "cell 5 0,growx,aligny top");
		entAno.setColumns(10);
		entAno.setText(new GetData().getAnoAtual() + "");

		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisarDemostrativo();
			}
		});
		btnAtualizar.setForeground(Color.WHITE);
		btnAtualizar.setBackground(new Color(0, 51, 102));
		btnAtualizar.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_1.add(btnAtualizar, "cell 6 0");

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_3.setBackground(Color.WHITE);
		painelPrincipal.add(panel_3, "cell 0 4,grow");
		panel_3.setLayout(new MigLayout("",
				"[48px][1px][41px][56px][1px][41px][113px][1px][41px][99px][1px][41px][112px][1px][][][][]", "[21px]"));

		JLabel lblNewLabel_3 = new JLabel("Cargo:");
		lblNewLabel_3.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_3.add(lblNewLabel_3, "cell 0 0,alignx left,aligny top");

		lblCargo = new JLabel("");
		lblCargo.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_3.add(lblCargo, "cell 1 0,alignx left,aligny center");

		JLabel lblNewLabel_4 = new JLabel("espaco");
		lblNewLabel_4.setForeground(Color.WHITE);
		panel_3.add(lblNewLabel_4, "cell 2 0,alignx left,aligny bottom");

		JLabel lblNewLabel_3_1 = new JLabel("Função:");
		lblNewLabel_3_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_3.add(lblNewLabel_3_1, "cell 3 0,alignx left,aligny top");

		lblFuncao = new JLabel("");
		lblFuncao.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_3.add(lblFuncao, "cell 4 0,alignx left,aligny center");

		JLabel lblNewLabel_4_1 = new JLabel("espaco");
		lblNewLabel_4_1.setForeground(Color.WHITE);
		panel_3.add(lblNewLabel_4_1, "cell 5 0,alignx left,aligny bottom");

		JLabel lblNewLabel_3_1_1 = new JLabel("Data Admissão:");
		lblNewLabel_3_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_3.add(lblNewLabel_3_1_1, "cell 6 0,alignx left,aligny top");

		lblDataAdmissao = new JLabel("");
		lblDataAdmissao.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_3.add(lblDataAdmissao, "cell 7 0,alignx left,aligny center");

		JLabel lblNewLabel_4_1_1 = new JLabel("espaco");
		lblNewLabel_4_1_1.setForeground(Color.WHITE);
		panel_3.add(lblNewLabel_4_1_1, "cell 8 0,alignx left,aligny bottom");

		JLabel lblNewLabel_3_1_1_1 = new JLabel("Tipo Contrato:");
		lblNewLabel_3_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_3.add(lblNewLabel_3_1_1_1, "cell 9 0,alignx left,aligny top");

		lblTipoContrato = new JLabel("");
		lblTipoContrato.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_3.add(lblTipoContrato, "cell 10 0,alignx left,aligny center");

		JLabel lblNewLabel_4_1_1_1 = new JLabel("espaco");
		lblNewLabel_4_1_1_1.setForeground(Color.WHITE);
		panel_3.add(lblNewLabel_4_1_1_1, "cell 11 0,alignx left,aligny bottom");

		JLabel lblNewLabel_3_1_1_1_1 = new JLabel("Status Contrato:");
		lblNewLabel_3_1_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_3.add(lblNewLabel_3_1_1_1_1, "cell 12 0,alignx left,aligny top");

		lblStatusContrato = new JLabel("");
		lblStatusContrato.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_3.add(lblStatusContrato, "cell 13 0,alignx left,aligny center");

		JLabel lblNewLabel_4_1_2 = new JLabel("espaco");
		lblNewLabel_4_1_2.setForeground(Color.WHITE);
		panel_3.add(lblNewLabel_4_1_2, "cell 15 0");

		JLabel lblNewLabel_3_1_1_1_1_1 = new JLabel("Último Valor Salarial:");
		lblNewLabel_3_1_1_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_3.add(lblNewLabel_3_1_1_1_1_1, "cell 16 0");

		lblUltimoValorSalarial = new JLabel("");
		lblUltimoValorSalarial.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_3.add(lblUltimoValorSalarial, "cell 17 0");

		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelPrincipal.add(panel_4, "cell 0 5");
		panel_4.setBackground(Color.WHITE);
		panel_4.setLayout(
				new MigLayout("", "[164px][21px][92px][21px][102px][21px][95px][21px][89px][21px][57px][21px][66px]",
						"[21px][19px][19px]"));

		JLabel lblNewLabel_3_2 = new JLabel("Jornada de Trabalho:");
		panel_4.add(lblNewLabel_3_2, "cell 0 0 13 1,alignx center,aligny top");
		lblNewLabel_3_2.setFont(new Font("SansSerif", Font.BOLD, 16));

		JLabel lblNewLabel_3_3 = new JLabel("SEGUNDA-FEIRA");
		panel_4.add(lblNewLabel_3_3, "cell 0 1,alignx center,aligny top");
		lblNewLabel_3_3.setFont(new Font("SansSerif", Font.PLAIN, 14));

		JLabel lblNewLabel_4_3 = new JLabel("esp");
		lblNewLabel_4_3.setForeground(Color.WHITE);
		panel_4.add(lblNewLabel_4_3, "cell 1 1,alignx left,aligny center");

		JLabel lblNewLabel_3_3_1 = new JLabel("TERÇA-FEIRA");
		panel_4.add(lblNewLabel_3_3_1, "cell 2 1,alignx left,aligny top");
		lblNewLabel_3_3_1.setFont(new Font("SansSerif", Font.PLAIN, 14));

		JLabel lblNewLabel_4_3_1 = new JLabel("esp");
		lblNewLabel_4_3_1.setForeground(Color.WHITE);
		panel_4.add(lblNewLabel_4_3_1, "cell 3 1,alignx left,aligny center");

		JLabel lblNewLabel_3_3_1_1 = new JLabel("QUARTA-FEIRA");
		panel_4.add(lblNewLabel_3_3_1_1, "cell 4 1,alignx left,aligny top");
		lblNewLabel_3_3_1_1.setFont(new Font("SansSerif", Font.PLAIN, 14));

		JLabel lblNewLabel_4_3_1_1 = new JLabel("esp");
		lblNewLabel_4_3_1_1.setForeground(Color.WHITE);
		panel_4.add(lblNewLabel_4_3_1_1, "cell 5 1,alignx left,aligny center");

		JLabel lblNewLabel_3_3_1_1_1 = new JLabel("QUINTA-FEIRA");
		panel_4.add(lblNewLabel_3_3_1_1_1, "cell 6 1,alignx left,aligny top");
		lblNewLabel_3_3_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 14));

		JLabel lblNewLabel_4_3_1_1_1 = new JLabel("esp");
		lblNewLabel_4_3_1_1_1.setForeground(Color.WHITE);
		panel_4.add(lblNewLabel_4_3_1_1_1, "cell 7 1,alignx left,aligny center");

		JLabel lblNewLabel_3_3_1_1_1_1 = new JLabel("SEXTA-FEIRA");
		panel_4.add(lblNewLabel_3_3_1_1_1_1, "cell 8 1,alignx left,aligny top");
		lblNewLabel_3_3_1_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 14));

		JLabel lblNewLabel_4_3_1_1_1_1 = new JLabel("esp");
		lblNewLabel_4_3_1_1_1_1.setForeground(Color.WHITE);
		panel_4.add(lblNewLabel_4_3_1_1_1_1, "cell 9 1,alignx left,aligny center");

		JLabel lblNewLabel_3_3_1_1_1_1_1 = new JLabel("SABÁDO");
		panel_4.add(lblNewLabel_3_3_1_1_1_1_1, "cell 10 1,alignx left,aligny top");
		lblNewLabel_3_3_1_1_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 14));

		JLabel lblNewLabel_4_3_1_1_1_1_1 = new JLabel("esp");
		lblNewLabel_4_3_1_1_1_1_1.setForeground(Color.WHITE);
		panel_4.add(lblNewLabel_4_3_1_1_1_1_1, "cell 11 1,alignx left,aligny center");

		JLabel lblNewLabel_3_3_1_1_1_1_1_1 = new JLabel("DOMINGO");
		panel_4.add(lblNewLabel_3_3_1_1_1_1_1_1, "cell 12 1,alignx left,aligny top");
		lblNewLabel_3_3_1_1_1_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 14));

		lblJornadaSegunda = new JLabel("jornada");
		lblJornadaSegunda.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_4.add(lblJornadaSegunda, "cell 0 2,alignx center,aligny top");

		lblJornadaTerca = new JLabel("jornada");
		lblJornadaTerca.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_4.add(lblJornadaTerca, "cell 2 2,alignx center,aligny top");

		lblJornadaQuarta = new JLabel("jornada");
		lblJornadaQuarta.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_4.add(lblJornadaQuarta, "cell 4 2,alignx center,aligny top");

		lblJornadaQuinta = new JLabel("jornada");
		lblJornadaQuinta.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_4.add(lblJornadaQuinta, "cell 6 2,alignx center,aligny top");

		lblJornadaSexta = new JLabel("jornada");
		lblJornadaSexta.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_4.add(lblJornadaSexta, "cell 8 2,alignx center,aligny top");

		lblJornadaSabado = new JLabel("jornada");
		lblJornadaSabado.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_4.add(lblJornadaSabado, "cell 10 2,alignx center,aligny top");

		lblJornadaDomingo = new JLabel("jornada");
		lblJornadaDomingo.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_4.add(lblJornadaDomingo, "cell 12 2,alignx center,aligny top");

		JPanel panel_2 = new JPanel();
		painelPrincipal.add(panel_2, "cell 0 6 1 3,grow");
		panel_2.setBackground(Color.WHITE);
		panel_2.setLayout(new BorderLayout(0, 0));

		tabela_demonstrativo = new JTable(modeloRegistroPonto);
		CellRenderRPMensal renderer = new CellRenderRPMensal();
		tabela_demonstrativo.setDefaultRenderer(Object.class, renderer);

		// instancia o sorter

		// define o sorter na tablea
		tabela_demonstrativo.setRowHeight(30);

		JScrollPane scrollPaneDemostrativo = new JScrollPane(tabela_demonstrativo);
		panel_2.add(scrollPaneDemostrativo);

		JPanel painelCalculos = new JPanel();
		painelCalculos.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelCalculos.setBackground(Color.WHITE);
		painelPrincipal.add(painelCalculos, "flowx,cell 0 9,alignx left,growy");
		painelCalculos.setLayout(new MigLayout("", "[152px][42px]", "[21px][21px][21px][21px][]"));

		JLabel lblNewLabel_1_2_2 = new JLabel("Total Horas Mensal:");
		lblNewLabel_1_2_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelCalculos.add(lblNewLabel_1_2_2, "cell 0 0,alignx right,aligny bottom");

		lblTotalHorasMensal = new JLabel("00:00");
		lblTotalHorasMensal.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelCalculos.add(lblTotalHorasMensal, "cell 1 0,alignx left,aligny top");

		JLabel lblNewLabel_1_2 = new JLabel("Total Horas Normais:");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelCalculos.add(lblNewLabel_1_2, "cell 0 1,alignx left,aligny bottom");

		lblTotalHorasNormais = new JLabel("00:00");
		lblTotalHorasNormais.setForeground(new Color(0, 51, 51));
		lblTotalHorasNormais.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelCalculos.add(lblTotalHorasNormais, "cell 1 1,alignx left,aligny top");

		JLabel lblNewLabel_1_2_1 = new JLabel("Total Horas Extras:");
		lblNewLabel_1_2_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelCalculos.add(lblNewLabel_1_2_1, "cell 0 2,alignx right,aligny bottom");

		lblTotalHorasExtras = new JLabel("00:00");
		lblTotalHorasExtras.setForeground(new Color(0, 0, 153));
		lblTotalHorasExtras.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelCalculos.add(lblTotalHorasExtras, "cell 1 2,alignx left,aligny top");

		JLabel lblNewLabel_1_2_1_1 = new JLabel("Total Horas Atrazo:");
		lblNewLabel_1_2_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelCalculos.add(lblNewLabel_1_2_1_1, "cell 0 3,alignx right,aligny bottom");

		lblTotalHorasAtrazo = new JLabel("00:00");
		lblTotalHorasAtrazo.setForeground(Color.RED);
		lblTotalHorasAtrazo.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelCalculos.add(lblTotalHorasAtrazo, "cell 1 3,alignx left,aligny top");

		JLabel lblNewLabel_1_2_1_1_1 = new JLabel("Total Horas Noturnas:");
		lblNewLabel_1_2_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelCalculos.add(lblNewLabel_1_2_1_1_1, "cell 0 4,alignx right");

		lblTotalHorasNoturnas = new JLabel("0:00");
		lblTotalHorasNoturnas.setForeground(new Color(102, 51, 0));
		lblTotalHorasNoturnas.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelCalculos.add(lblTotalHorasNoturnas, "cell 1 4");

		JPanel panel_5 = new JPanel();
		panel_5.setBackground(new Color(204, 255, 255));
		painelPrincipal.add(panel_5, "cell 0 9,grow");
		panel_5.setLayout(new MigLayout("", "[][][][][grow]", "[grow][][][]"));

		JLabel lblNewLabel_1_2_1_2_1 = new JLabel("Total Horas Extras:");
		lblNewLabel_1_2_1_2_1.setForeground(new Color(204, 255, 255));
		lblNewLabel_1_2_1_2_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_5.add(lblNewLabel_1_2_1_2_1, "cell 0 0");

		JLabel lblNewLabel_1_2_1_2 = new JLabel("Horas Extras");
		lblNewLabel_1_2_1_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_5.add(lblNewLabel_1_2_1_2, "cell 1 0 2 1,alignx center");

		JLabel lblNewLabel_1_2_1_2_2 = new JLabel("Descrição de Atrasos");
		lblNewLabel_1_2_1_2_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_5.add(lblNewLabel_1_2_1_2_2, "cell 4 0,alignx center");

		textAreaDescricaoAtrasos = new JTextArea();
		textAreaDescricaoAtrasos.setWrapStyleWord(true);
		textAreaDescricaoAtrasos.setLineWrap(true);
		textAreaDescricaoAtrasos.setFont(new Font("SansSerif", Font.BOLD, 16));
		textAreaDescricaoAtrasos.setBackground(new Color(204, 255, 255));
		panel_5.add(textAreaDescricaoAtrasos, "cell 4 1 1 3,grow");

		JLabel lblNewLabel_1_2_1_3 = new JLabel("Total Horas 50%:");
		lblNewLabel_1_2_1_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_5.add(lblNewLabel_1_2_1_3, "cell 1 1,alignx right");

		lblTotalHoras50 = new JLabel("00:00");
		lblTotalHoras50.setForeground(new Color(0, 0, 153));
		lblTotalHoras50.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_5.add(lblTotalHoras50, "cell 2 1");

		JLabel lblNewLabel_1_2_1_3_1 = new JLabel("Total Horas 60%:");
		lblNewLabel_1_2_1_3_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_5.add(lblNewLabel_1_2_1_3_1, "cell 1 2,alignx right");

		lblTotalHoras60 = new JLabel("00:00");
		lblTotalHoras60.setForeground(new Color(0, 0, 153));
		lblTotalHoras60.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_5.add(lblTotalHoras60, "cell 2 2");

		JLabel lblNewLabel_1_2_1_3_1_1 = new JLabel("Total Horas 100%:");
		lblNewLabel_1_2_1_3_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_5.add(lblNewLabel_1_2_1_3_1_1, "cell 1 3,alignx right");

		lblTotalHoras100 = new JLabel("00:00");
		lblTotalHoras100.setForeground(new Color(0, 0, 153));
		lblTotalHoras100.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_5.add(lblTotalHoras100, "cell 2 3");

		JPanel painelCalculo = new JPanel();
		painelCalculo.setBackground(Color.WHITE);
		tabbedPane.addTab("Cálculo de Salário", null, painelCalculo, null);
		painelCalculo.setLayout(new MigLayout("", "[grow][grow][grow]", "[][][grow][][][][][]"));

		JPanel panel_8 = new JPanel();
		panel_8.setBackground(Color.WHITE);
		painelCalculo.add(panel_8, "cell 0 0,alignx center,growy");
		panel_8.setLayout(new MigLayout("", "[][][][][][][][grow]", "[grow][][][][][][][grow][][grow][][][][][grow]"));

		JPanel panel_11 = new JPanel();
		panel_11.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_11.setBackground(Color.WHITE);
		panel_8.add(panel_11, "cell 0 0 8 8,alignx center,aligny center");
		panel_11.setLayout(new MigLayout("", "[][]", "[][][][][][][][][]"));

		JLabel lblNewLabel_1_3 = new JLabel("Piso Salarial:");
		panel_11.add(lblNewLabel_1_3, "cell 0 0,alignx right");
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.PLAIN, 16));

		lblPisoSalarial = new JLabel("R$ 0.000,00");
		panel_11.add(lblPisoSalarial, "cell 1 0");
		lblPisoSalarial.setFont(new Font("SansSerif", Font.BOLD, 18));

		JLabel lblNewLabel_1_3_2 = new JLabel("Valor Hora:");
		panel_11.add(lblNewLabel_1_3_2, "cell 0 1,alignx right");
		lblNewLabel_1_3_2.setFont(new Font("Tahoma", Font.PLAIN, 16));

		lblValorHoraNormal = new JLabel("R$ 0.000,00");
		panel_11.add(lblValorHoraNormal, "cell 1 1");
		lblValorHoraNormal.setFont(new Font("SansSerif", Font.BOLD, 18));

		JLabel lblNewLabel_1_3_2_2 = new JLabel("Valor Hora Noturna:");
		lblNewLabel_1_3_2_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_11.add(lblNewLabel_1_3_2_2, "cell 0 2,alignx right");

		lblValorHoraNoturna = new JLabel("R$ 0,00");
		lblValorHoraNoturna.setFont(new Font("SansSerif", Font.BOLD, 18));
		panel_11.add(lblValorHoraNoturna, "cell 1 2");

		JLabel lblNewLabel_1_3_2_1 = new JLabel("Valor da Diária:");
		lblNewLabel_1_3_2_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_11.add(lblNewLabel_1_3_2_1, "cell 0 3,alignx right");

		lblValorDiaria = new JLabel("R$ 0.000,00");
		lblValorDiaria.setFont(new Font("SansSerif", Font.BOLD, 18));
		panel_11.add(lblValorDiaria, "cell 1 3");

		JLabel lblNewLabel_1_3_1 = new JLabel("Descontar Atrazos?:");
		panel_11.add(lblNewLabel_1_3_1, "cell 0 4");
		lblNewLabel_1_3_1.setFont(new Font("Tahoma", Font.PLAIN, 16));

		rdbtnDescontarAtrazosSim = new JRadioButton("Sim");
		panel_11.add(rdbtnDescontarAtrazosSim, "flowx,cell 1 4");
		rdbtnDescontarAtrazosSim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				rdbtnDescontarAtrazosSim.setSelected(true);
				rdbtnDescontarAtrazosNao.setSelected(false);

			}
		});
		rdbtnDescontarAtrazosSim.setFont(new Font("SansSerif", Font.PLAIN, 16));

		rdbtnDescontarAtrazosNao = new JRadioButton("Não");
		panel_11.add(rdbtnDescontarAtrazosNao, "cell 1 4");
		rdbtnDescontarAtrazosNao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				rdbtnDescontarAtrazosSim.setSelected(false);
				rdbtnDescontarAtrazosNao.setSelected(true);
			}
		});
		rdbtnDescontarAtrazosNao.setSelected(true);
		rdbtnDescontarAtrazosNao.setFont(new Font("SansSerif", Font.PLAIN, 16));

		JLabel lblNewLabel_1_3_1_2 = new JLabel("INSS:");
		panel_11.add(lblNewLabel_1_3_1_2, "cell 0 5,alignx right");
		lblNewLabel_1_3_1_2.setFont(new Font("Tahoma", Font.PLAIN, 16));

		rdbtnInssSim = new JRadioButton("Sim");
		panel_11.add(rdbtnInssSim, "flowx,cell 1 5");
		rdbtnInssSim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				rdbtnInssSim.setSelected(true);
				rdbtnInssNao.setSelected(false);

			}
		});
		rdbtnInssSim.setFont(new Font("SansSerif", Font.PLAIN, 16));

		rdbtnInssNao = new JRadioButton("Não");
		rdbtnInssNao.setSelected(true);
		panel_11.add(rdbtnInssNao, "cell 1 5");
		rdbtnInssNao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				rdbtnInssNao.setSelected(true);
				rdbtnInssSim.setSelected(false);
			}
		});
		rdbtnInssNao.setFont(new Font("SansSerif", Font.PLAIN, 16));

		JLabel lblNewLabel_1_3_1_2_1 = new JLabel("IRRF:");
		panel_11.add(lblNewLabel_1_3_1_2_1, "cell 0 6,alignx right");
		lblNewLabel_1_3_1_2_1.setFont(new Font("Tahoma", Font.PLAIN, 16));

		rdbtnIrrfSim = new JRadioButton("Sim");
		panel_11.add(rdbtnIrrfSim, "flowx,cell 1 6");
		rdbtnIrrfSim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				rdbtnIrrfSim.setSelected(true);
				rdbtnIrrfNao.setSelected(false);

			}
		});
		rdbtnIrrfSim.setFont(new Font("SansSerif", Font.PLAIN, 16));

		rdbtnIrrfNao = new JRadioButton("Não");
		rdbtnIrrfNao.setSelected(true);
		panel_11.add(rdbtnIrrfNao, "cell 1 6");
		rdbtnIrrfNao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				rdbtnIrrfNao.setSelected(true);
				rdbtnIrrfSim.setSelected(false);
			}
		});
		rdbtnIrrfNao.setFont(new Font("SansSerif", Font.PLAIN, 16));

		JPanel panel_6_1 = new JPanel();
		panel_6_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_6_1.setBackground(Color.WHITE);
		panel_8.add(panel_6_1, "cell 0 9,alignx center,growy");
		panel_6_1.setLayout(new MigLayout("", "[][][][][][][][][][][][][][]", "[][][]"));

		JLabel lblNewLabel_1_3_1_1_3 = new JLabel("Horas Faltantes");
		lblNewLabel_1_3_1_1_3.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_6_1.add(lblNewLabel_1_3_1_1_3, "cell 0 0 14 1,alignx center");

		JLabel lblNewLabel_1_3_1_1_1_4 = new JLabel("Descrição");
		lblNewLabel_1_3_1_1_1_4.setForeground(Color.BLACK);
		lblNewLabel_1_3_1_1_1_4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_6_1.add(lblNewLabel_1_3_1_1_1_4, "cell 1 1,alignx center");

		JLabel lblNewLabel_1_3_1_1_1_2_2 = new JLabel("esp");
		lblNewLabel_1_3_1_1_1_2_2.setForeground(Color.WHITE);
		lblNewLabel_1_3_1_1_1_2_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_6_1.add(lblNewLabel_1_3_1_1_1_2_2, "cell 2 1");

		JLabel lblNewLabel_1_3_1_1_1_1_1_2 = new JLabel("Quantidade");
		lblNewLabel_1_3_1_1_1_1_1_2.setForeground(Color.BLACK);
		lblNewLabel_1_3_1_1_1_1_1_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_6_1.add(lblNewLabel_1_3_1_1_1_1_1_2, "cell 4 1,alignx center");

		JLabel lblNewLabel_1_3_1_1_1_2_2_1_1 = new JLabel("esp");
		lblNewLabel_1_3_1_1_1_2_2_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_3_1_1_1_2_2_1_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_6_1.add(lblNewLabel_1_3_1_1_1_2_2_1_1, "cell 6 1");

		JLabel lblNewLabel_1_3_1_1_1_2_2_1_1_1 = new JLabel("esp");
		lblNewLabel_1_3_1_1_1_2_2_1_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_3_1_1_1_2_2_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_6_1.add(lblNewLabel_1_3_1_1_1_2_2_1_1_1, "cell 8 1");

		JLabel lblNewLabel_1_3_1_1_1_3_2 = new JLabel("Horas de Atrazo");
		lblNewLabel_1_3_1_1_1_3_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_6_1.add(lblNewLabel_1_3_1_1_1_3_2, "cell 1 2");

		lblQuantidadeHorasAtrazo = new JLabel("0");
		lblQuantidadeHorasAtrazo.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_6_1.add(lblQuantidadeHorasAtrazo, "cell 4 2,alignx center");

		JPanel panel_14 = new JPanel();
		panel_14.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_14.setBackground(Color.WHITE);
		panel_8.add(panel_14, "cell 1 9 7 1,grow");
		panel_14.setLayout(new MigLayout("", "[]", "[]"));

		JLabel lblNewLabel_1_3_1_1_3_1 = new JLabel("Horas Faltantes");
		lblNewLabel_1_3_1_1_3_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_14.add(lblNewLabel_1_3_1_1_3_1, "cell 0 0,alignx center");

		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_8.add(panel_6, "cell 0 10 8 1,alignx center");
		panel_6.setBackground(Color.WHITE);
		panel_6.setLayout(new MigLayout("", "[][][][][][][]", "[][][][][][][]"));

		JLabel lblNewLabel_1_3_1_1 = new JLabel("Horas Extras");
		lblNewLabel_1_3_1_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_6.add(lblNewLabel_1_3_1_1, "cell 0 0 7 1,alignx center");

		JLabel lblNewLabel_1_3_1_1_1 = new JLabel("Descrição");
		lblNewLabel_1_3_1_1_1.setForeground(Color.BLACK);
		lblNewLabel_1_3_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_6.add(lblNewLabel_1_3_1_1_1, "cell 0 2,alignx center");

		JLabel lblNewLabel_1_3_1_1_1_2 = new JLabel("esp");
		lblNewLabel_1_3_1_1_1_2.setForeground(Color.WHITE);
		lblNewLabel_1_3_1_1_1_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_6.add(lblNewLabel_1_3_1_1_1_2, "cell 1 2");

		JLabel lblNewLabel_1_3_1_1_1_1 = new JLabel("Valor");
		lblNewLabel_1_3_1_1_1_1.setForeground(Color.BLACK);
		lblNewLabel_1_3_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_6.add(lblNewLabel_1_3_1_1_1_1, "cell 2 2,alignx center");

		JLabel lblNewLabel_1_3_1_1_1_2_1 = new JLabel("esp");
		lblNewLabel_1_3_1_1_1_2_1.setForeground(Color.WHITE);
		lblNewLabel_1_3_1_1_1_2_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_6.add(lblNewLabel_1_3_1_1_1_2_1, "cell 3 2");

		JLabel lblNewLabel_1_3_1_1_1_1_1 = new JLabel("Quantidade");
		lblNewLabel_1_3_1_1_1_1_1.setForeground(Color.BLACK);
		lblNewLabel_1_3_1_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_6.add(lblNewLabel_1_3_1_1_1_1_1, "cell 4 2,alignx center");

		JLabel lblNewLabel_1_3_1_1_1_2_1_1 = new JLabel("esp");
		lblNewLabel_1_3_1_1_1_2_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_3_1_1_1_2_1_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_6.add(lblNewLabel_1_3_1_1_1_2_1_1, "cell 5 2");

		JLabel lblNewLabel_1_3_1_1_1_1_1_1 = new JLabel("Valor Total:");
		lblNewLabel_1_3_1_1_1_1_1_1.setForeground(Color.BLACK);
		lblNewLabel_1_3_1_1_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_6.add(lblNewLabel_1_3_1_1_1_1_1_1, "cell 6 2,alignx center");

		JLabel lblNewLabel_1_3_1_1_1_3 = new JLabel("Hora 50%");
		lblNewLabel_1_3_1_1_1_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_6.add(lblNewLabel_1_3_1_1_1_3, "cell 0 3,alignx center");

		lblValorHora50 = new JLabel("R$ 00,00");
		lblValorHora50.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_6.add(lblValorHora50, "cell 2 3,alignx center");

		lblQuantidadeHoras50 = new JLabel("0");
		lblQuantidadeHoras50.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_6.add(lblQuantidadeHoras50, "cell 4 3,alignx center");

		lblValorTotalHoras50 = new JLabel("R$ 000,00");
		lblValorTotalHoras50.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_6.add(lblValorTotalHoras50, "cell 6 3,alignx center");

		JLabel lblNewLabel_1_3_1_1_1_3_1 = new JLabel("Hora 60%");
		lblNewLabel_1_3_1_1_1_3_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_6.add(lblNewLabel_1_3_1_1_1_3_1, "cell 0 4,alignx center");

		lblValorHora60 = new JLabel("R$ 00,00");
		lblValorHora60.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_6.add(lblValorHora60, "cell 2 4,alignx center");

		lblQuantidadeHoras60 = new JLabel("0");
		lblQuantidadeHoras60.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_6.add(lblQuantidadeHoras60, "cell 4 4,alignx center");

		lblValorTotalHoras60 = new JLabel("R$ 000,00");
		lblValorTotalHoras60.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_6.add(lblValorTotalHoras60, "cell 6 4,alignx center");

		JLabel lblNewLabel_1_3_1_1_1_3_1_1 = new JLabel("Hora 100%");
		lblNewLabel_1_3_1_1_1_3_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_6.add(lblNewLabel_1_3_1_1_1_3_1_1, "cell 0 5,alignx center");

		lblValorHora100 = new JLabel("R$ 00,00");
		lblValorHora100.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_6.add(lblValorHora100, "cell 2 5,alignx center");

		lblQuantidadeHoras100 = new JLabel("0");
		lblQuantidadeHoras100.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_6.add(lblQuantidadeHoras100, "cell 4 5,alignx center");

		lblValorTotalHoras100 = new JLabel("R$ 000,00");
		lblValorTotalHoras100.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_6.add(lblValorTotalHoras100, "cell 6 5,alignx center");

		JLabel lblNewLabel_1_3_1_1_1_3_1_1_1 = new JLabel("Total:");
		lblNewLabel_1_3_1_1_1_3_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_6.add(lblNewLabel_1_3_1_1_1_3_1_1_1, "cell 0 6,alignx right");

		lblQuantidadeHorasExtras = new JLabel("0");
		lblQuantidadeHorasExtras.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_6.add(lblQuantidadeHorasExtras, "cell 4 6,alignx center");

		lblValorTotalHorasExtras = new JLabel("R$ 000,00");
		lblValorTotalHorasExtras.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_6.add(lblValorTotalHorasExtras, "cell 6 6,alignx center");

		JPanel panel_6_2 = new JPanel();
		panel_6_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_6_2.setBackground(Color.WHITE);
		panel_8.add(panel_6_2, "cell 0 11,alignx center,growy");
		panel_6_2.setLayout(new MigLayout("", "[130px][41px][150px]", "[20px][20px][20px]"));

		JLabel lblNewLabel_1_3_1_1_4 = new JLabel("Banco Horas Atual");
		lblNewLabel_1_3_1_1_4.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_6_2.add(lblNewLabel_1_3_1_1_4, "cell 0 0 3 1,alignx center,aligny top");

		JLabel lblNewLabel_1_3_1_1_1_6 = new JLabel("Descrição");
		lblNewLabel_1_3_1_1_1_6.setForeground(Color.BLACK);
		lblNewLabel_1_3_1_1_1_6.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_6_2.add(lblNewLabel_1_3_1_1_1_6, "cell 0 1,alignx center,aligny top");

		JLabel lblNewLabel_1_3_1_1_1_1_1_3 = new JLabel("Tipo");
		lblNewLabel_1_3_1_1_1_1_1_3.setForeground(Color.BLACK);
		lblNewLabel_1_3_1_1_1_1_1_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_6_2.add(lblNewLabel_1_3_1_1_1_1_1_3, "cell 1 1,alignx left,aligny top");

		JLabel lblNewLabel_1_3_1_1_1_1_1_3_1 = new JLabel("Quantidade");
		lblNewLabel_1_3_1_1_1_1_1_3_1.setForeground(Color.BLACK);
		lblNewLabel_1_3_1_1_1_1_1_3_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_6_2.add(lblNewLabel_1_3_1_1_1_1_1_3_1, "cell 2 1,alignx center,aligny top");

		lblDescricaoBancoHoras = new JLabel("BH Mês Passado");
		lblDescricaoBancoHoras.setForeground(Color.BLACK);
		lblDescricaoBancoHoras.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_6_2.add(lblDescricaoBancoHoras, "cell 0 2,alignx left,aligny top");

		lblTipoBancoHoras = new JLabel("TIPO");
		lblTipoBancoHoras.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_6_2.add(lblTipoBancoHoras, "cell 1 2,alignx left,aligny top");

		lblQuantidadeBancoHoras = new JLabel("0");
		lblQuantidadeBancoHoras.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_6_2.add(lblQuantidadeBancoHoras, "cell 2 2,alignx center,aligny top");

		JPanel panel_6_2_1 = new JPanel();
		panel_6_2_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_6_2_1.setBackground(Color.WHITE);
		panel_8.add(panel_6_2_1, "cell 1 11 7 1,alignx center,growy");
		panel_6_2_1.setLayout(new MigLayout("", "[][grow][][][][]", "[][][][][][grow][][]"));

		JLabel lblNewLabel_1_3_1_4_1 = new JLabel("Depositar no BHs?:");
		lblNewLabel_1_3_1_4_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_6_2_1.add(lblNewLabel_1_3_1_4_1, "cell 0 0 3 1");

		rdbtnDepositarSim = new JRadioButton("Sim");
		rdbtnDepositarSim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				rdbtnDepositarSim.setSelected(true);
				rdbtnDepositarNao.setSelected(false);

			}
		});
		rdbtnDepositarSim.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_6_2_1.add(rdbtnDepositarSim, "flowx,cell 3 0 3 1");

		JLabel lblNewLabel_1_3_1_1_4_1 = new JLabel("Novo Banco de Horas");
		lblNewLabel_1_3_1_1_4_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_6_2_1.add(lblNewLabel_1_3_1_1_4_1, "cell 0 1 6 1,alignx center");

		JLabel lblNewLabel_1_3_1_1_1_6_1 = new JLabel("Descrição");
		lblNewLabel_1_3_1_1_1_6_1.setForeground(Color.BLACK);
		lblNewLabel_1_3_1_1_1_6_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_6_2_1.add(lblNewLabel_1_3_1_1_1_6_1, "cell 0 3 3 1,alignx center");

		JLabel lblNewLabel_1_3_1_1_1_1_1_3_2 = new JLabel("Tipo");
		lblNewLabel_1_3_1_1_1_1_1_3_2.setForeground(Color.BLACK);
		lblNewLabel_1_3_1_1_1_1_1_3_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_6_2_1.add(lblNewLabel_1_3_1_1_1_1_1_3_2, "cell 3 3,alignx center");

		JLabel lblNewLabel_1_3_1_1_1_2_2_1_2 = new JLabel("esp");
		lblNewLabel_1_3_1_1_1_2_2_1_2.setForeground(Color.WHITE);
		lblNewLabel_1_3_1_1_1_2_2_1_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_6_2_1.add(lblNewLabel_1_3_1_1_1_2_2_1_2, "cell 4 3");

		JLabel lblNewLabel_1_3_1_1_1_1_1_3_1_1 = new JLabel("Quantidade");
		lblNewLabel_1_3_1_1_1_1_1_3_1_1.setForeground(Color.BLACK);
		lblNewLabel_1_3_1_1_1_1_1_3_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_6_2_1.add(lblNewLabel_1_3_1_1_1_1_1_3_1_1, "cell 5 3,alignx center");

		rdbtnDepositarNao = new JRadioButton("Não");
		rdbtnDepositarNao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				rdbtnDepositarNao.setSelected(true);
				rdbtnDepositarSim.setSelected(false);
			}
		});
		rdbtnDepositarNao.setSelected(true);
		rdbtnDepositarNao.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_6_2_1.add(rdbtnDepositarNao, "cell 3 0 3 1");

		lblDescricaoNovoBancoHoras = new JLabel("BH Mês");
		lblDescricaoNovoBancoHoras.setForeground(Color.BLACK);
		lblDescricaoNovoBancoHoras.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_6_2_1.add(lblDescricaoNovoBancoHoras, "cell 0 4 3 1,alignx center");

		lblTipoNovoBancoHoras = new JLabel("TIPO");
		lblTipoNovoBancoHoras.setForeground(Color.BLACK);
		lblTipoNovoBancoHoras.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_6_2_1.add(lblTipoNovoBancoHoras, "cell 3 4,alignx center");

		lblQuantidadeNovoBancoHoras = new JLabel("0");
		lblQuantidadeNovoBancoHoras.setForeground(Color.BLACK);
		lblQuantidadeNovoBancoHoras.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_6_2_1.add(lblQuantidadeNovoBancoHoras, "cell 5 4,alignx center");

		lblInfoCalculoDepositoBH = new JTextArea();
		lblInfoCalculoDepositoBH.setLineWrap(true);
		lblInfoCalculoDepositoBH.setWrapStyleWord(true);
		panel_6_2_1.add(lblInfoCalculoDepositoBH, "cell 1 5 5 1,grow");

		JLabel lblNewLabel_1_3_1_1_1_6_2 = new JLabel("Opção Banco:");
		lblNewLabel_1_3_1_1_1_6_2.setForeground(Color.BLACK);
		lblNewLabel_1_3_1_1_1_6_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_8.add(lblNewLabel_1_3_1_1_1_6_2, "flowx,cell 0 12");

		rdbtnManter = new JRadioButton("Manter");
		rdbtnManter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnManter.setSelected(true);
				rdbtnPagar.setSelected(false);
				manter_global = true;

			}
		});
		rdbtnManter.setSelected(true);
		rdbtnManter.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_8.add(rdbtnManter, "cell 0 12");

		rdbtnPagar = new JRadioButton("Pagar");
		rdbtnPagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				rdbtnManter.setSelected(false);
				rdbtnPagar.setSelected(true);
				manter_global = false;
			}
		});
		rdbtnPagar.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_8.add(rdbtnPagar, "cell 0 12");

		JPanel panel_12 = new JPanel();
		panel_12.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_12.setBackground(Color.WHITE);
		painelCalculo.add(panel_12, "cell 1 0 2 1,grow");
		panel_12.setLayout(new MigLayout("", "[grow][]", "[][][][][][][][]"));

		JLabel lblNewLabel_1_3_1_1_4_2 = new JLabel("Vencimentos:");
		lblNewLabel_1_3_1_1_4_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_12.add(lblNewLabel_1_3_1_1_4_2, "cell 0 0");

		JLabel lblNewLabel_1_3_1_1_4_2_1 = new JLabel("Salário Base:");
		lblNewLabel_1_3_1_1_4_2_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_12.add(lblNewLabel_1_3_1_1_4_2_1, "flowx,cell 0 2,alignx left");

		JLabel lblNewLabel_1_3_1_1_4_2_1_1 = new JLabel("Horas Extras:");
		lblNewLabel_1_3_1_1_4_2_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_12.add(lblNewLabel_1_3_1_1_4_2_1_1, "flowx,cell 0 3,alignx left");

		JLabel lblNewLabel_1_3_1_1_4_2_1_1_1 = new JLabel("Horas Noturnas:");
		lblNewLabel_1_3_1_1_4_2_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_12.add(lblNewLabel_1_3_1_1_4_2_1_1_1, "flowx,cell 0 4");

		JPanel panel_13 = new JPanel();
		panel_12.add(panel_13, "cell 0 5 2 1,grow");
		panel_13.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_13.setBackground(Color.WHITE);
		panel_13.setLayout(new MigLayout("", "[grow]", "[][][][]"));

		JLabel lblNewLabel_1_3_1_1_2_1 = new JLabel("Acréscimos/Benefícios");
		panel_13.add(lblNewLabel_1_3_1_1_2_1, "cell 0 0");
		lblNewLabel_1_3_1_1_2_1.setFont(new Font("Tahoma", Font.BOLD, 16));

		tabela_acrescimos = new JTable(modelAcrescimos);
		tabela_acrescimos.setRowHeight(30);

		modelAcrescimos.addTableModelListener(new TableModelListener() {

			public void tableChanged(TableModelEvent e) {

				double valor_total_acrescimos = 0;
				for (int i = 0; i < modelAcrescimos.getRowCount(); i++) {
					valor_total_acrescimos += modelAcrescimos.getValue(i).getTotal();

				}
				lblValorTotalAcrescimosAReceber.setText(formatarValor(valor_total_acrescimos));
				valor_total_acrescimos_global = valor_total_acrescimos;
				calculoFinal();
			}
		});

		JScrollPane scrollPaneAcrescimos = new JScrollPane(tabela_acrescimos);
		panel_13.add(scrollPaneAcrescimos, "cell 0 1,grow");

		JLabel lblValorTotalAcrscimos = new JLabel("Valor Total Acréscimos:");
		lblValorTotalAcrscimos.setFont(new Font("Tahoma", Font.BOLD, 18));
		panel_13.add(lblValorTotalAcrscimos, "flowx,cell 0 2,alignx left");

		JButton btnRemoverAcrescimo = new JButton("Remover");
		panel_13.add(btnRemoverAcrescimo, "flowx,cell 0 3,alignx right");
		btnRemoverAcrescimo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modelAcrescimos.onRemove(getAcrescimoSelecionado());

			}
		});
		btnRemoverAcrescimo.setForeground(Color.WHITE);
		btnRemoverAcrescimo.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnRemoverAcrescimo.setBackground(new Color(153, 0, 0));

		JButton btnAdicionarAcrescimo = new JButton("Adicionar");
		btnAdicionarAcrescimo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaFuncionariosCadastroAcrescimo tela = new TelaFuncionariosCadastroAcrescimo(0, null, isto);
				tela.setVisible(true);
			}
		});
		panel_13.add(btnAdicionarAcrescimo, "cell 0 3,alignx right");
		btnAdicionarAcrescimo.setForeground(Color.WHITE);
		btnAdicionarAcrescimo.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnAdicionarAcrescimo.setBackground(new Color(0, 51, 0));

		lblValorTotalAcrescimosAReceber = new JLabel("R$ 0");
		lblValorTotalAcrescimosAReceber.setFont(new Font("Tahoma", Font.BOLD, 18));
		panel_13.add(lblValorTotalAcrescimosAReceber, "cell 0 2");
		scrollPaneAcrescimos.getViewport().setBackground(Color.white);

		JPanel painelPai7 = new JPanel();
		painelPai7.setBackground(Color.WHITE);
		panel_12.add(painelPai7, "cell 0 7 2 1,grow");
		painelPai7.setLayout(new MigLayout("", "[grow]", "[][][][grow]"));

		JPanel panel_7 = new JPanel();
		panel_7.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_7.setBackground(Color.WHITE);
		panel_7.setLayout(new MigLayout("", "[grow][][::10px][grow][]", "[][][grow][][]"));
		JLabel lblNewLabel_1_3_1_1_2 = new JLabel("Descontos:");
		panel_7.add(lblNewLabel_1_3_1_1_2, "cell 0 0");
		lblNewLabel_1_3_1_1_2.setFont(new Font("Tahoma", Font.BOLD, 16));

		painelPai7.add(panel_7, "cell 0 0,growx");

		tabela_descontos = new JTable(modelDescontos);
		modelDescontos.addTableModelListener(new TableModelListener() {

			public void tableChanged(TableModelEvent e) {

				double valor_total_descontos = 0;
				for (int i = 0; i < modelDescontos.getRowCount(); i++) {
					valor_total_descontos += modelDescontos.getValue(i).getTotal();

				}
				lblValorTotalDescontosAAbater.setText(formatarValor(valor_total_descontos));
				valor_total_descontos_global = valor_total_descontos;
				calculoFinal();
			}
		});
		tabela_descontos.setRowHeight(30);

		JScrollPane scrollPaneDescontos = new JScrollPane(tabela_descontos);
		panel_7.add(scrollPaneDescontos, "cell 0 1 5 2,grow");

		JButton btnRemoverDesconto = new JButton("Remover");
		btnRemoverDesconto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modelDescontos.onRemove(getDescontoSelecionado());
			}
		});

		JLabel lblValorTotalDescontos = new JLabel("Valor Total Descontos:");
		lblValorTotalDescontos.setFont(new Font("Tahoma", Font.BOLD, 18));
		panel_7.add(lblValorTotalDescontos, "flowx,cell 0 3");

		lblValorTotalDescontosAAbater = new JLabel("R$ 0");
		lblValorTotalDescontosAAbater.setFont(new Font("Tahoma", Font.BOLD, 18));
		panel_7.add(lblValorTotalDescontosAAbater, "cell 0 3");
		btnRemoverDesconto.setBackground(new Color(153, 0, 0));
		btnRemoverDesconto.setForeground(Color.WHITE);
		btnRemoverDesconto.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_7.add(btnRemoverDesconto, "flowx,cell 0 4 5 1,alignx right");

		JButton btnAdicionarDesconto = new JButton("Adicionar");
		btnAdicionarDesconto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaFuncionariosCadastroDesconto tela = new TelaFuncionariosCadastroDesconto(0, null, isto);
				tela.setVisible(true);
			}
		});
		btnAdicionarDesconto.setBackground(new Color(0, 51, 0));
		btnAdicionarDesconto.setForeground(Color.WHITE);
		btnAdicionarDesconto.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_7.add(btnAdicionarDesconto, "cell 0 4 5 1,alignx right");

		JLabel lblSalrioBruto = new JLabel("Salário Bruto:");
		lblSalrioBruto.setFont(new Font("Tahoma", Font.BOLD, 18));
		painelPai7.add(lblSalrioBruto, "flowx,cell 0 1");

		JLabel lblSalrioLquido = new JLabel("Salário Líquido:");
		lblSalrioLquido.setFont(new Font("Tahoma", Font.BOLD, 18));
		painelPai7.add(lblSalrioLquido, "flowx,cell 0 2");

		lblValorTotalSalarioBruto = new JLabel("R$ 0");
		lblValorTotalSalarioBruto.setFont(new Font("Tahoma", Font.BOLD, 18));
		painelPai7.add(lblValorTotalSalarioBruto, "cell 0 1");

		lblValorTotalSalarioLiquido = new JLabel("R$ 0");
		lblValorTotalSalarioLiquido.setFont(new Font("Tahoma", Font.BOLD, 18));
		painelPai7.add(lblValorTotalSalarioLiquido, "cell 0 2");

		lblSalarioBaseAReceber = new JLabel("R$ 1000,00");
		lblSalarioBaseAReceber.setFont(new Font("Tahoma", Font.BOLD, 18));
		panel_12.add(lblSalarioBaseAReceber, "cell 0 2");

		lblHorasExtrasAReceber = new JLabel("R$ 1000,00");
		lblHorasExtrasAReceber.setFont(new Font("Tahoma", Font.BOLD, 18));
		panel_12.add(lblHorasExtrasAReceber, "cell 0 3");

		lblHorasNoturnas = new JLabel("R$ 0,00");
		lblHorasNoturnas.setFont(new Font("Tahoma", Font.BOLD, 18));
		panel_12.add(lblHorasNoturnas, "cell 0 4");

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// gerar os arquivos para impressao

				horas_global.setTHMensal(tHMensal);
				horas_global.setTHTrabalhadas(tHTrabalhadas);
				horas_global.setTHAtrazo(tHAtrazo);
				horas_global.setTH50(tH50);
				horas_global.setTH60(tH60);
				horas_global.setTH100(tH100);
				horas_global.setTHExtras(tHExtras);
				horas_global.setTHNoturnas(tHNoturnas);

				ArrayList<RegistroPontoMensalCompleto> registro_ponto = getRegistrosPonto();
				ArrayList<CadastroFuncionarioCalculo> acrescimos = getAcrescimos();
				ArrayList<CadastroFuncionarioCalculo> descontos = getDescontos();
				int mes = cBMes.getSelectedIndex();

				RelatorioSalario relatorio = new RelatorioSalario(registro_ponto, acrescimos, descontos, func,
						ct_global, cBMes.getItemAt(mes).toString(), Integer.parseInt(entAno.getText()), texto_atrazo,
						piso_salarial, horas_global, isto);
				/*
				 * CadastroFuncionarioSalario salario = getDadosSalvar();
				 * GerenciarBancoFuncionarioSalarios gerenciar = new
				 * GerenciarBancoFuncionarioSalarios(); int inserir =
				 * gerenciar.inserirsalario(salario); if(inserir > 0) {
				 * JOptionPane.showMessageDialog(isto, "Sálario Cadastrado na Base de Dados");
				 * 
				 * if(rdbtnDepositarSim.isSelected()) { //criar novo banco de horas
				 * GerenciarBancoFuncionarioBancoHoras gerenciar_banco = new
				 * GerenciarBancoFuncionarioBancoHoras(); CadastroFuncionarioBancoHoras banco =
				 * getDadosBanco(); if (banco != null) { int inserir_banco =
				 * gerenciar_banco.inserirbanco_horas(banco); if (inserir > 0) {
				 * JOptionPane.showMessageDialog(isto, "Novo Banco de Horas Registrado");
				 * ((TelaGerenciarFuncionario) janela_pai).pesquisar_banco_horas();
				 * isto.dispose(); } else { JOptionPane.showMessageDialog(isto,
				 * "Erro ao Cadastrar Banco de Horas\nConsulte o administrador");
				 * isto.dispose();
				 * 
				 * } } }
				 * 
				 * 
				 * isto.dispose(); }else { JOptionPane.showMessageDialog(isto,
				 * "Erro ao Salvar o Contrato na Base de Dados\nConsulte o Administrador"); }
				 */
			}

		});

		JButton btnAtualizarClculo = new JButton("Atualizar Cálculo");
		btnAtualizarClculo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atualizar();
			}
		});

		btnAtualizarClculo.setForeground(Color.WHITE);
		btnAtualizarClculo.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnAtualizarClculo.setBackground(new Color(0, 0, 102));
		painelCalculo.add(btnAtualizarClculo, "cell 2 1,alignx right");
		btnSalvar.setForeground(Color.WHITE);
		btnSalvar.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnSalvar.setBackground(new Color(0, 51, 0));
		painelCalculo.add(btnSalvar, "cell 2 7,alignx right");

		JPanel panel_9 = new JPanel();
		panel_9.setBackground(Color.WHITE);
		tabbedPane.addTab("Cálculos", null, panel_9, null);
		panel_9.setLayout(new MigLayout("", "[][grow]", "[grow][][grow]"));

		JPanel panel_10 = new JPanel();
		panel_10.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_10.setBackground(Color.WHITE);
		panel_9.add(panel_10, "cell 0 0,grow");
		panel_10.setLayout(new MigLayout("", "[]", "[][][][][][][][][][][]"));

		JLabel lblClculoInss = new JLabel("Cálculo INSS 2021");
		lblClculoInss.setFont(new Font("SansSerif", Font.BOLD, 18));
		panel_10.add(lblClculoInss, "cell 0 0,alignx center");

		JLabel lblNewLabel_1_3_1_3_2 = new JLabel("esp");
		lblNewLabel_1_3_1_3_2.setForeground(Color.WHITE);
		lblNewLabel_1_3_1_3_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_10.add(lblNewLabel_1_3_1_3_2, "cell 0 1");

		JLabel lblNewLabel_1_3_1_3 = new JLabel("Calculado Sobre o Piso Salarial");
		lblNewLabel_1_3_1_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_10.add(lblNewLabel_1_3_1_3, "cell 0 2");

		JLabel lblNewLabel_1_3_1_3_2_1 = new JLabel("esp");
		lblNewLabel_1_3_1_3_2_1.setForeground(Color.WHITE);
		lblNewLabel_1_3_1_3_2_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_10.add(lblNewLabel_1_3_1_3_2_1, "cell 0 3");

		JLabel lblNewLabel_1_3_1_3_1 = new JLabel("1ª faixa salarial: R$ 1.100,00 x 7,5% = R$ 82,50\r\n");
		lblNewLabel_1_3_1_3_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_10.add(lblNewLabel_1_3_1_3_1, "cell 0 4");

		JLabel lblNewLabel_1_3_1_3_1_1 = new JLabel(
				"2ª faixa salarial: (R$ 2.203,48 – R$ 1.100,01) x 9% = R$ 1.044,60 x 9% = R$ 99,31\r\n");
		lblNewLabel_1_3_1_3_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_10.add(lblNewLabel_1_3_1_3_1_1, "cell 0 5");

		JLabel lblNewLabel_1_3_1_3_1_1_1 = new JLabel(
				"3ª faixa salarial: (R$ 3.305,22 – R$ 2.203,49) x 12% = R$ 1.101,73 x 12% = R$ 132,21\r\n");
		lblNewLabel_1_3_1_3_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_10.add(lblNewLabel_1_3_1_3_1_1_1, "cell 0 6");

		JLabel lblNewLabel_1_3_1_3_1_1_1_1 = new JLabel(
				"4ª faixa salarial: (R$ 6.433,57 – R$ 3.305,23) x 14% = R$ 3.128,34 x 14% = R$ 437,97\r\n");
		lblNewLabel_1_3_1_3_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_10.add(lblNewLabel_1_3_1_3_1_1_1_1, "cell 0 7");

		JLabel lblNewLabel_1_3_1_3_1_1_1_1_1 = new JLabel(
				"Total máximo a recolher: R$ 82,50 + R$ 99,31 + R$ 132,21 + R$ 437,97 = R$ 751,99\r\n");
		lblNewLabel_1_3_1_3_1_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_10.add(lblNewLabel_1_3_1_3_1_1_1_1_1, "cell 0 8");

		lblCalculoInss = new JLabel("R$ 0.000,00");
		lblCalculoInss.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_10.add(lblCalculoInss, "cell 0 9,alignx center");

		JPanel panel_10_1 = new JPanel();
		panel_10_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_10_1.setBackground(Color.WHITE);
		panel_9.add(panel_10_1, "cell 0 1,grow");
		panel_10_1.setLayout(new MigLayout("", "[grow]", "[][][][][][][][][][][grow]"));

		JLabel lblClculoIrrf = new JLabel("Cálculo IRRF 2021");
		lblClculoIrrf.setFont(new Font("SansSerif", Font.BOLD, 18));
		panel_10_1.add(lblClculoIrrf, "cell 0 0,alignx center");

		JLabel lblNewLabel_1_3_1_3_2_1_1 = new JLabel("esp");
		lblNewLabel_1_3_1_3_2_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_3_1_3_2_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_10_1.add(lblNewLabel_1_3_1_3_2_1_1, "cell 0 1");

		JLabel lblNewLabel_1_3_1_3_3 = new JLabel("Calculado Sobre o Salário Bruto REDUZIDO do Desconto do INSS");
		lblNewLabel_1_3_1_3_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_10_1.add(lblNewLabel_1_3_1_3_3, "cell 0 2");

		JLabel lblNewLabel_1_3_1_3_2_1_1_1 = new JLabel("esp");
		lblNewLabel_1_3_1_3_2_1_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_3_1_3_2_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_10_1.add(lblNewLabel_1_3_1_3_2_1_1_1, "cell 0 3");

		JLabel lblNewLabel_1_3_1_3_1_2 = new JLabel("Até 1.903,98 — alíquota de 0% (isento) — Taxa ISENTO\r\n");
		lblNewLabel_1_3_1_3_1_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_10_1.add(lblNewLabel_1_3_1_3_1_2, "cell 0 4");

		JLabel lblNewLabel_1_3_1_3_1_2_1 = new JLabel(
				"De R$ 1.903,99 até R$ 2.826,65 — alíquota de 7,5% — Taxa R$ 142,80\r\n");
		lblNewLabel_1_3_1_3_1_2_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_10_1.add(lblNewLabel_1_3_1_3_1_2_1, "cell 0 5");

		JLabel lblNewLabel_1_3_1_3_1_2_1_1 = new JLabel(
				"De R$ 2.826,66 até R$ 3,751,05 — alíquota de 15% — Taxa R$ 354,80\r\n");
		lblNewLabel_1_3_1_3_1_2_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_10_1.add(lblNewLabel_1_3_1_3_1_2_1_1, "cell 0 6");

		JLabel lblNewLabel_1_3_1_3_1_2_1_2 = new JLabel(
				"De R$ 3.751,06 até R$ 4.664,68 — alíquota de 22,5% — Taxa R$ 636,13\r\n");
		lblNewLabel_1_3_1_3_1_2_1_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_10_1.add(lblNewLabel_1_3_1_3_1_2_1_2, "cell 0 7");

		JLabel lblNewLabel_1_3_1_3_1_2_1_2_1 = new JLabel(
				"Acima de R$ 4.664,68 — alíquota de 27,5% — Taxa R$ 869,36\r\n");
		lblNewLabel_1_3_1_3_1_2_1_2_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_10_1.add(lblNewLabel_1_3_1_3_1_2_1_2_1, "cell 0 8");

		lblCalculoIrrf = new JLabel("R$ 0.000,00");
		lblCalculoIrrf.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_10_1.add(lblCalculoIrrf, "cell 0 9");

		JPanel panel_10_1_1 = new JPanel();
		panel_10_1_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_10_1_1.setBackground(Color.WHITE);
		panel_9.add(panel_10_1_1, "cell 0 2,grow");
		panel_10_1_1.setLayout(new MigLayout("", "[]", "[][][][][][][][][]"));

		JLabel lblAdicionalNoturo = new JLabel("Adicional Noturno");
		lblAdicionalNoturo.setFont(new Font("SansSerif", Font.BOLD, 18));
		panel_10_1_1.add(lblAdicionalNoturo, "cell 0 0,alignx center");

		JLabel lblNewLabel_1_3_1_3_3_1 = new JLabel("Calculado Sobre o Valor da Hora Trabalhada");
		lblNewLabel_1_3_1_3_3_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_10_1_1.add(lblNewLabel_1_3_1_3_3_1, "cell 0 2");

		JLabel lblNewLabel_1_3_1_3_2_1_1_2 = new JLabel("esp");
		lblNewLabel_1_3_1_3_2_1_1_2.setForeground(Color.WHITE);
		lblNewLabel_1_3_1_3_2_1_1_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_10_1_1.add(lblNewLabel_1_3_1_3_2_1_1_2, "cell 0 3");

		JLabel lblNewLabel_1_3_1_3_3_1_1_2 = new JLabel("Regras");
		lblNewLabel_1_3_1_3_3_1_1_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_10_1_1.add(lblNewLabel_1_3_1_3_3_1_1_2, "cell 0 4");

		JLabel lblNewLabel_1_3_1_3_3_1_1 = new JLabel(
				"Urbano: a partir das 22h de um dia às 5h do dia seguinte; Hora equivate a: 52m e 30s; 20% a mais sobre a hora trabalhada");
		lblNewLabel_1_3_1_3_3_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_10_1_1.add(lblNewLabel_1_3_1_3_3_1_1, "cell 0 6");

		JLabel lblNewLabel_1_3_1_3_3_1_1_1 = new JLabel(
				"Rural(agricultura): a partir das 21h de um dia às 5h do dia seguinte; Hora equivate a: 60m; 25% a mais sobre a hora trabalhada");
		lblNewLabel_1_3_1_3_3_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_10_1_1.add(lblNewLabel_1_3_1_3_3_1_1_1, "cell 0 7");

		JLabel lblNewLabel_1_3_1_3_3_1_1_1_1 = new JLabel(
				"Rural(pecuária): a partir das 21h de um dia às 4h do dia seguinte; Hora equivate a: 60m; 25% a mais sobre a hora trabalhada");
		lblNewLabel_1_3_1_3_3_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_10_1_1.add(lblNewLabel_1_3_1_3_3_1_1_1_1, "cell 0 8");

		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		this.func = funcionario;
		entAno.setText(ano);
		cBMes.setSelectedIndex(mes);
		// modelFuncionarios.setSelectedItem(func);
		lblColaborador.setText(funcionario.getNome() + " " + funcionario.getSobrenome());
		// pesquisarDemostrativo();
		atualizar();
		this.setResizable(true);
		this.setLocationRelativeTo(janela_pai);

	}

	public boolean checkString(String txt) {
		return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ");
	}

	public void setTelaPai(JDialog _telaPai) {
		this.telaPai = _telaPai;
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

	public class RegistroPontoMensalCompletoTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int data = 0;
		private final int dia_semana = 1;
		private final int hora_entrada1 = 2;
		private final int hora_saida1 = 3;
		private final int hora_entrada2 = 4;
		private final int hora_saida2 = 5;
		private final int hora_entrada3 = 6;
		private final int hora_saida3 = 7;
		private final int rotina_diaria = 8;

		private final int horas_trabalhadas = 9;
		private final int horas_extras = 10;
		private final int horas_atrazo = 11;
		private final int horas_noturnas = 12;

		private final String colunas[] = { "DATA", "DIA DA SEMANA", "HORA ENTRADA 1", "HORA SAIDA 1", "HORA ENTRADA 2",
				"HORA SAIDA 2", "HORA ENTRADA 3", "HORA SAIDA 3", "ROTINA DIARIA", "HORAS TRABALHADAS", "HORAS EXTRAS",
				"HORAS ATRAZO", "HORAS NOTURNAS" };
		Locale ptBr = new Locale("pt", "BR");

		private final ArrayList<RegistroPontoMensalCompleto> dados = new ArrayList<>();// usamos como dados uma lista
		// genérica de
		// nfs

		public RegistroPontoMensalCompletoTableModel() {

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

			case data:
				return String.class;
			case dia_semana:
				return String.class;
			case hora_entrada1:
				return String.class;
			case hora_saida1:
				return String.class;
			case hora_entrada2:
				return String.class;
			case hora_saida2:
				return String.class;
			case hora_entrada3:
				return String.class;
			case hora_saida3:
				return String.class;
			case rotina_diaria:
				return String.class;
			case horas_trabalhadas:
				return String.class;
			case horas_extras:
				return String.class;

			case horas_atrazo:
				return String.class;
			case horas_noturnas:
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
			NumberFormat z = NumberFormat.getNumberInstance();

			// pega o dados corrente da linha
			RegistroPontoMensalCompleto rp = dados.get(rowIndex);

			// retorna o valor da coluna
			switch (columnIndex) {

			case data: {
				if (rp.getRp().getData() != null && !rp.getRp().getData().equals("")
						&& !rp.getRp().getData().equalsIgnoreCase("TOTAIS"))
					return rp.getRp().getData();
				else {
					return "TOTAIS";
				}

			}
			case dia_semana: {

				if (rp.getRp().getData() != null && !rp.getRp().getData().equals("")
						&& !rp.getRp().getData().equalsIgnoreCase("TOTAIS")) {
					try {
						DateTimeFormatter formatter = new DateTimeFormatterBuilder()
								.toFormatter(new Locale("pt", "BR"));

						LocalDate data = LocalDate.parse(rp.getRp().getData(), formatter.ofPattern("dd/MM/yyyy"));
						DayOfWeek dia_s = data.getDayOfWeek();
						return dia_s.getDisplayName(TextStyle.FULL, new Locale("pt", "BR")).toUpperCase();
					} catch (Exception e) {
						return "";
					}
				} else {
					return "";
				}

			}
			case hora_entrada1: {
				if (!rp.getRp().getEntrada1().equals("00:00"))
					return rp.getRp().getEntrada1();
				else
					return "-";
			}
			case hora_saida1: {

				if (!rp.getRp().getSaida1().equals("00:00"))
					return rp.getRp().getSaida1();
				else
					return "-";

			}
			case hora_entrada2: {

				if (!rp.getRp().getEntrada2().equals("00:00"))
					return rp.getRp().getEntrada2();
				else
					return "-";

			}

			case hora_saida2: {

				if (!rp.getRp().getSaida2().equals("00:00"))
					return rp.getRp().getSaida2();
				else
					return "-";

			}
			case hora_entrada3: {

				if (!rp.getRp().getEntrada3().equals("00:00"))
					return rp.getRp().getEntrada3();
				else
					return "";

			}
			case hora_saida3: {
				if (!rp.getRp().getSaida3().equals("00:00"))
					return rp.getRp().getSaida3();
				else
					return "";

			}

			case rotina_diaria: {

				if (rp.getHora_diaria() != null) {
					if (!rp.getHora_diaria().equals("00:00"))
						return rp.getHora_diaria();
					else
						return "DESCANSO SEMANAL";
				} else {
					return "";
				}

			}
			case horas_trabalhadas: {

				if (!rp.getHora_trabalhada().equals("00:00"))
					return rp.getHora_trabalhada();
				else
					return "";

			}
			case horas_extras: {
				if (!rp.getHora_extra().equals("00:00"))
					return rp.getHora_extra();
				else
					return "";

			}

			case horas_atrazo: {
				if (!rp.getHora_atrazo().equals("00:00"))
					return rp.getHora_atrazo();
				else
					return "";

			}
			case horas_noturnas: {
				if (rp.getHora_noturna() != null) {
					if (!rp.getHora_noturna().equals("00:00"))
						return rp.getHora_noturna();
					else
						return "";
				} else
					return "";

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
			RegistroPontoMensalCompleto recebimento = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public RegistroPontoMensalCompleto getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(RegistroPontoMensalCompleto nota) {
			return dados.indexOf(nota);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(RegistroPontoMensalCompleto nota) {
			dados.add(nota);
			fireTableRowsInserted(indexOf(nota), indexOf(nota));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<RegistroPontoMensalCompleto> dadosIn) {
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
		public void onRemove(RegistroPontoMensalCompleto nota) {
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

	public void pesquisarDemostrativo() {

		pesquisarContratoMesPagamento();


		
		int mes = cBMes.getSelectedIndex() + 1;
		int ano = Integer.parseInt(entAno.getText());

		int contador_dias = 0;

		Calendar datas = new GregorianCalendar();
		datas.set(Calendar.MONTH, mes - 1);// 2
		int ultimo_dia = datas.getActualMaximum(Calendar.DAY_OF_MONTH);
		int dia = 1;

		// informar valor da diaria
		double valor_diaria = piso_salarial / (double) ultimo_dia;
		lblValorDiaria.setText(formatarValor(valor_diaria));

		long total_duracao = 0, total_horas_normais_trabalhadas = 0, total_horas_extras = 0, total_horas_atrazo = 0,
				total_horas_100 = 0, total_horas_50 = 0, total_horas_60 = 0, total_horas_adicional_noturno = 0;

		long duracao_rotina = 0;

		GerenciarBancoRegistroPonto gerenciar = new GerenciarBancoRegistroPonto();

		// buscar rotinas e trabalho

		modeloRegistroPonto.onRemoveAll();

		ArrayList<RegistroPontoDiario> listRpDiario = gerenciar
				.getDemonstrativoFuncionarioMesCompleto(func.getId_funcionario(), ultimo_dia, mes, ano);

		// ArrayList<CadastroFuncionarioRotinaTrabalho> rotinas =
		// gerenciar_rotina.getRotinas(func.getId_funcionario());
		DateTimeFormatter formatter = new DateTimeFormatterBuilder().toFormatter(new Locale("pt", "BR"));
		LocalDate dia_de_hoje = LocalDate.now();

		
		ArrayList<RegistroHoraLocal> registros_locais = new ArrayList<>();
		
		
		boolean prosseguir = false;
		int contador_nao_nulos = 0;

		for (RegistroPontoDiario rp : listRpDiario) {
			if(rp.getData() != null &&  rp.getEntrada1() != null && rp.getSaida1() != null )  {
				prosseguir = true;
				contador_nao_nulos++;
				if(contador_nao_nulos >= 7) {
					break;
				}
			}
		}
		
		
		//procurar por isencao de ponto
		GerenciarBancoFuncionariosEventos gerenciar_eventos_isencao = new GerenciarBancoFuncionariosEventos();
		ArrayList<CadastroFuncionarioEvento> eventos_isencao = gerenciar_eventos_isencao
				.getEventosPorColaborador(func.getId_funcionario());
		
		boolean tem_evt_isencao = procurarIsencao(eventos_isencao);
		boolean tem_evt_ferias = procurarFerias(eventos_isencao, mes, ano);
		boolean tem_evt_licenca = procurarLicencas(eventos_isencao, mes, ano);

		
		if( (prosseguir &&  contador_nao_nulos >= 7 ) || tem_evt_isencao || tem_evt_ferias || tem_evt_licenca) {
			

		
		for (RegistroPontoDiario rp : listRpDiario) {

			CadastroFuncionarioEvento evt_global = null;

			// verifique a rotina desse dia
			RegistroHoraLocal registro_local = new RegistroHoraLocal();
			RegistroPontoMensalCompleto rpCompleto = new RegistroPontoMensalCompleto();

			RegistroPontoDiario original = rp;
			String data_rp = "";
			CadastroFuncionarioRotinaTrabalho rotina_deste_dia = null;

			if (rp.getData() == null) {

				String s_mes = "", s_dia = "", s_ano = "";

				if (mes <= 9)
					s_mes = "0" + mes;
				else
					s_mes = mes + "";

				if (dia <= 9)
					s_dia = "0" + dia;
				else
					s_dia = dia + "";

				data_rp = s_dia + "/" + s_mes + "/" + ano;

				rp.setData(data_rp);
			} else {
				data_rp = rp.getData();
			}

			LocalDate data_do_registro = LocalDate.parse(data_rp, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

			int dia_da_semana = 0;

			try {

				LocalDate data = LocalDate.parse(rp.getData(), formatter.ofPattern("dd/MM/yyyy"));
				dia_da_semana = data.getDayOfWeek().getValue();
			} catch (Exception e) {
				return;
			}

			registro_local.setDia_da_semana(dia_da_semana);
			boolean tem_rotina = false;

			for (CadastroFuncionarioRotinaTrabalho rotina : rotinas_global) {
				if (rotina.getDia_da_semana() == dia_da_semana) {
					// rotina encontrada
					rotina_deste_dia = rotina;
					break;

				}
			}

			if (rotina_deste_dia != null)
				tem_rotina = true;

			// verificar por evento de folga ou ferias
			GerenciarBancoFuncionariosEventos gerenciar_eventos = new GerenciarBancoFuncionariosEventos();
			ArrayList<CadastroFuncionarioEvento> eventos = gerenciar_eventos
					.getEventosPorColaborador(func.getId_funcionario());

			GerenciarBancoEventoGlobal gerenciar_eventos_global = new GerenciarBancoEventoGlobal();
			ArrayList<EventoGlobal> listaEventosGlobais = gerenciar_eventos_global.getEventosPorData(data_rp);

			boolean tem_folga = false;
			boolean tem_ferias = false;
			boolean tem_isencao = false;
			boolean tem_licenca = false;
			boolean tem_descanso = false;
			boolean tem_feriado = false;
			boolean tem_saida_especial = false;

			for (CadastroFuncionarioEvento evt : eventos) {

				if (evt.getTipo_evento() == 0) {
					// folga
					if (evt.getData_folga().equalsIgnoreCase(data_rp)) {
						// tem folga nesse dia

						tem_folga = true;
						break;
					}
				} else if (evt.getTipo_evento() == 3) {
					LocalDate hoje = LocalDate.parse(data_rp, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

					LocalDate dataInicialFerias = LocalDate.parse(evt.getData_ferias_ida(),
							DateTimeFormatter.ofPattern("dd/MM/yyyy"));
					LocalDate dataFinalFerias = LocalDate.parse(evt.getData_ferias_volta(),
							DateTimeFormatter.ofPattern("dd/MM/yyyy"));

					if (hoje.isEqual(dataInicialFerias)) {
						tem_ferias = true;
						break;

					} else if (hoje.isEqual(dataFinalFerias)) {
						tem_ferias = true;
						break;
					} else if (hoje.isAfter(dataInicialFerias) && hoje.isBefore(dataFinalFerias)) {
						tem_ferias = true;
						break;
					}

				} else if (evt.getTipo_evento() == 4) {
					// isencao de ponto
					LocalDate hoje = LocalDate.parse(data_rp, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

					LocalDate dataInicialFerias = LocalDate.parse(evt.getData_ferias_ida(),
							DateTimeFormatter.ofPattern("dd/MM/yyyy"));
					LocalDate dataFinalFerias = LocalDate.parse(evt.getData_ferias_volta(),
							DateTimeFormatter.ofPattern("dd/MM/yyyy"));

					if (hoje.isEqual(dataInicialFerias)) {
						tem_isencao = true;
						break;

					} else if (hoje.isEqual(dataFinalFerias)) {
						tem_isencao = true;
						break;
					} else if (hoje.isAfter(dataInicialFerias) && hoje.isBefore(dataFinalFerias)) {
						tem_isencao = true;
						break;
					}
				} else if (evt.getTipo_evento() == 5) {
					// licenca
					LocalDate hoje = LocalDate.parse(data_rp, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

					LocalDate dataInicialFerias = LocalDate.parse(evt.getData_ferias_ida(),
							DateTimeFormatter.ofPattern("dd/MM/yyyy"));
					LocalDate dataFinalFerias = LocalDate.parse(evt.getData_ferias_volta(),
							DateTimeFormatter.ofPattern("dd/MM/yyyy"));

					if (hoje.isEqual(dataInicialFerias)) {
						tem_licenca = true;
						break;

					} else if (hoje.isEqual(dataFinalFerias)) {
						tem_licenca = true;
						break;
					} else if (hoje.isAfter(dataInicialFerias) && hoje.isBefore(dataFinalFerias)) {
						tem_licenca = true;
						break;
					}
				} else if (evt.getTipo_evento() == 6) {
					// evento de saida especial
					LocalDate hoje = LocalDate.parse(data_rp, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

					String data_saida = evt.getData_saida();

					LocalDate date_saida = LocalDate.parse(data_saida, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

					if (date_saida.isEqual(hoje)) {
						tem_saida_especial = true;
						evt_global = evt;
					}

				}

			}

			for (EventoGlobal evt : listaEventosGlobais) {
				if (evt.getTipo_evento() == 0) {
					// tem feriado
					tem_feriado = true;
					break;
				}
			}

			if (tem_rotina) {

				// verificar hora diaria

				if (rotina_deste_dia.getHora_entrada1().equalsIgnoreCase("DESCANSO SEMANAL")
						|| rotina_deste_dia.getHora_saida1().equalsIgnoreCase("DESCANSO SEMANAL")
						|| rotina_deste_dia.getHora_entrada2().equalsIgnoreCase("DESCANSO SEMANAL")
						|| rotina_deste_dia.getHora_saida2().equalsIgnoreCase("DESCANSO SEMANAL")

				) {
					tem_descanso = true;
				}

				if (rotina_deste_dia.getHora_entrada1().equalsIgnoreCase("DESCANSO SEMANAL")) {
					rotina_deste_dia.setHora_entrada1("00:00");
				}

				if (rotina_deste_dia.getHora_saida1().equalsIgnoreCase("DESCANSO SEMANAL")) {
					rotina_deste_dia.setHora_saida1("00:00");
				}

				if (rotina_deste_dia.getHora_entrada2().equalsIgnoreCase("DESCANSO SEMANAL")) {
					rotina_deste_dia.setHora_entrada2("00:00");
				}

				if (rotina_deste_dia.getHora_saida2().equalsIgnoreCase("DESCANSO SEMANAL")) {
					rotina_deste_dia.setHora_saida2("00:00");
				}

				LocalDateTime entrada1 = LocalDateTime.parse(data_rp + " " + rotina_deste_dia.getHora_entrada1(),
						DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
				LocalDateTime saida1 = LocalDateTime.parse(data_rp + " " + rotina_deste_dia.getHora_saida1(),
						DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
				LocalDateTime entrada2 = LocalDateTime.parse(data_rp + " " + rotina_deste_dia.getHora_entrada2(),
						DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
				LocalDateTime saida2 = LocalDateTime.parse(data_rp + " " + rotina_deste_dia.getHora_saida2(),
						DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

				long periodAsMinutes1 = ChronoUnit.MINUTES.between(entrada1, saida1);
				long periodAsMinutes2 = ChronoUnit.MINUTES.between(entrada2, saida2);

				long duracao = periodAsMinutes1 + periodAsMinutes2;
				total_duracao += duracao;

				duracao_rotina = duracao;

				String tempo_total = LocalTime.MIN.plus(Duration.ofMinutes(duracao)).toString();
				rpCompleto.setHora_diaria(tempo_total);

			}

			// calculo de horas trabalhadas

			if (rp.getEntrada1() == null || rp.getEntrada1().equals("")) {
				rp.setEntrada1("00:00");
			}

			if (rp.getSaida1() == null || rp.getSaida1().equals("")) {

				if (tem_saida_especial) {
					if ((evt_global.getMovimentacao() + 1) == 2) {
						if (rp.getData().equals(evt_global.getData_saida())) {

							rp.setSaida1(evt_global.getHora_saida());

						} else {
							rp.setSaida1("00:00");

						}
					} else {
						rp.setSaida1("00:00");

					}

				} else {
					rp.setSaida1("00:00");
				}

			}

			if (rp.getEntrada2() == null || rp.getEntrada2().equals("")) {
				rp.setEntrada2("00:00");
			}

			if (rp.getSaida2() == null || rp.getSaida2().equals("")) {

				if (tem_saida_especial) {
					if ((evt_global.getMovimentacao() + 1) == 4) {
						if (rp.getData().equals(evt_global.getData_saida())) {

							rp.setSaida2(evt_global.getHora_saida());

						} else {
							rp.setSaida2("00:00");

						}
					} else {
						rp.setSaida2("00:00");

					}

				} else {
					rp.setSaida2("00:00");
				}

			}

			if (rp.getEntrada3() == null || rp.getEntrada3().equals("")) {
				rp.setEntrada3("00:00");
			}

			if (rp.getSaida3() == null || rp.getSaida3().equals("")) {
				rp.setSaida3("00:00");
			}

			LocalDateTime entrada1 = LocalDateTime.parse(data_rp + " " + rp.getEntrada1(),
					DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

			LocalDateTime saida1 = LocalDateTime.parse(data_rp + " " + rp.getSaida1(),
					DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

			if (tem_saida_especial) {
				if ((evt_global.getMovimentacao() + 1) == 2) {
					LocalDate hoje = LocalDate.parse(data_rp, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

					LocalDate dia_seguinte = hoje.plusDays(1);
					String nova_data = dia_seguinte.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

					saida1 = LocalDateTime.parse(nova_data + " " + rp.getSaida1(),
							DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

					// verifica por hora noturna
					long minutos_adicional_noturno = calculateWorkingHours(data_rp + " " + rp.getEntrada1(),
							nova_data + " " + rp.getSaida1());
					String tempo_total = LocalTime.MIN.plus(Duration.ofMinutes(minutos_adicional_noturno)).toString();

					rpCompleto.setHora_noturna(tempo_total);
					total_horas_adicional_noturno += minutos_adicional_noturno;

					tHNoturnas += minutos_adicional_noturno;
				}
			} else {
				saida1 = LocalDateTime.parse(data_rp + " " + rp.getSaida1(),
						DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
			}

			LocalDateTime entrada2 = LocalDateTime.parse(data_rp + " " + rp.getEntrada2(),
					DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

			LocalDateTime saida2 = LocalDateTime.parse(data_rp + " " + rp.getSaida2(),
					DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

			if (tem_saida_especial) {
				if ((evt_global.getMovimentacao() + 1) == 4) {
					LocalDate hoje = LocalDate.parse(data_rp, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

					LocalDate dia_seguinte = hoje.plusDays(1);
					String nova_data = dia_seguinte.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

					saida2 = LocalDateTime.parse(nova_data + " " + rp.getSaida2(),
							DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

					// verifica por hora noturna
					long minutos_adicional_noturno = calculateWorkingHours(data_rp + " " + rp.getEntrada1(),
							nova_data + " " + rp.getSaida2());
					String tempo_total = LocalTime.MIN.plus(Duration.ofMinutes(minutos_adicional_noturno)).toString();

					rpCompleto.setHora_noturna(tempo_total);
					total_horas_adicional_noturno += minutos_adicional_noturno;
					tHNoturnas += minutos_adicional_noturno;

				}
			} else {
				saida2 = LocalDateTime.parse(data_rp + " " + rp.getSaida2(),
						DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
			}

			LocalDateTime entrada3 = LocalDateTime.parse(data_rp + " " + rp.getEntrada3(),
					DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

			LocalDateTime saida3 = LocalDateTime.parse(data_rp + " " + rp.getSaida3(),
					DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

			long periodAsMinutes1 = ChronoUnit.MINUTES.between(entrada1, saida1);
			long periodAsMinutes2 = ChronoUnit.MINUTES.between(entrada2, saida2);
			long periodAsMinutes3 = ChronoUnit.MINUTES.between(entrada3, saida3);

			long duracao_trabalho = periodAsMinutes1 + periodAsMinutes2 + periodAsMinutes3;
			total_horas_normais_trabalhadas += duracao_trabalho;

			String tempo_total = LocalTime.MIN.plus(Duration.ofMinutes(duracao_trabalho)).toString();
			rpCompleto.setHora_trabalhada(tempo_total);

			if (!tem_folga && !tem_ferias && !tem_isencao && !tem_licenca && !tem_feriado && !tem_descanso
					&& duracao_trabalho <= 0 && duracao_rotina > 0) {

				if (data_do_registro.isBefore(dia_de_hoje)) {
					rp.setEntrada1("FALTA");
					rp.setSaida1("FALTA");
					rp.setEntrada2("FALTA");
					rp.setSaida2("FALTA");

					long hora_atrazo = duracao_rotina - duracao_trabalho;
					total_horas_atrazo += hora_atrazo;

					String tempo_atrazo = LocalTime.MIN.plus(Duration.ofMinutes(hora_atrazo)).toString();
					rpCompleto.setHora_atrazo(tempo_atrazo);
					rpCompleto.setHora_extra("00:00");

					registro_local.setHoras_atrazos(hora_atrazo);
					registro_local.setHoras_extras(0);

				} else {
					rp.setEntrada1("00:00");
					rp.setSaida1("00:00");
					rp.setEntrada2("00:00");
					rp.setSaida2("00:00");

					rpCompleto.setHora_atrazo("00:00");
					rpCompleto.setHora_extra("00:00");

					registro_local.setHoras_atrazos(0);
					registro_local.setHoras_extras(0);
				}

			} else if (tem_folga && !tem_ferias && !tem_feriado) {
				// tem folga
				rp.setEntrada1("FOLGA");
				rp.setSaida1("FOLGA");
				rp.setEntrada2("FOLGA");
				rp.setSaida2("FOLGA");

				long hora_atrazo = duracao_rotina - duracao_trabalho;
				total_horas_atrazo += hora_atrazo;

				String tempo_atrazo = LocalTime.MIN.plus(Duration.ofMinutes(hora_atrazo)).toString();
				rpCompleto.setHora_atrazo(tempo_atrazo);
				rpCompleto.setHora_extra("00:00");

				registro_local.setHoras_atrazos(hora_atrazo);
				registro_local.setHoras_extras(0);

			} else if (!tem_feriado && !tem_folga && tem_ferias) {
				// tem ferias
				rp.setEntrada1("FÉRIAS");
				rp.setSaida1("FÉRIAS");
				rp.setEntrada2("FÉRIAS");
				rp.setSaida2("FÉRIAS");

				long hora_atrazo = duracao_rotina - duracao_trabalho;
				String tempo_atrazo = LocalTime.MIN.plus(Duration.ofMinutes(hora_atrazo)).toString();
				rpCompleto.setHora_atrazo("00:00");
				rpCompleto.setHora_extra("00:00");

				registro_local.setHoras_atrazos(0);
				registro_local.setHoras_extras(0);

			} else if (tem_isencao) {
				// tem isencao
				rp.setEntrada1("ISENÇÃO DE PONTO");
				rp.setSaida1("ISENÇÃO DE PONTO");
				rp.setEntrada2("ISENÇÃO DE PONTO");
				rp.setSaida2("ISENÇÃO DE PONTO");

				long hora_atrazo = duracao_rotina - duracao_trabalho;
				String tempo_atrazo = LocalTime.MIN.plus(Duration.ofMinutes(hora_atrazo)).toString();
				rpCompleto.setHora_atrazo("00:00");
				rpCompleto.setHora_extra("00:00");

				registro_local.setHoras_atrazos(0);
				registro_local.setHoras_extras(0);

			} else if (tem_licenca) {
				// tem licenca
				rp.setEntrada1("LICENÇA");
				rp.setSaida1("LICENÇA");
				rp.setEntrada2("LICENÇA");
				rp.setSaida2("LICENÇA");

				long hora_atrazo = duracao_rotina - duracao_trabalho;
				String tempo_atrazo = LocalTime.MIN.plus(Duration.ofMinutes(hora_atrazo)).toString();
				rpCompleto.setHora_atrazo("00:00");
				rpCompleto.setHora_extra("00:00");

				registro_local.setHoras_atrazos(0);
				registro_local.setHoras_extras(0);

			} else if (tem_feriado) {

				if (original.getEntrada1() != null && !rp.getEntrada1().equals("00:00"))
					rp.setEntrada1(original.getEntrada1());
				else
					rp.setEntrada1("FERIADO");

				if (original.getSaida1() != null && !rp.getSaida1().equals("00:00"))
					rp.setSaida1(original.getSaida1());
				else
					rp.setSaida1("FERIADO");

				if (original.getEntrada2() != null && !rp.getEntrada2().equals("00:00"))
					rp.setEntrada2(original.getEntrada2());
				else
					rp.setEntrada2("FERIADO");

				if (original.getSaida2() != null && !rp.getSaida2().equals("00:00"))
					rp.setSaida2(original.getSaida2());
				else
					rp.setSaida2("FERIADO");

				// feriado, 100%
				if (duracao_trabalho > 0) {
					total_horas_extras += duracao_trabalho;
					total_horas_100 += duracao_trabalho;

					// trabalho esse dia, 100% hora extra
					String tempo_extra = LocalTime.MIN.plus(Duration.ofMinutes(duracao_trabalho)).toString();
					rpCompleto.setHora_extra(tempo_extra);
					rpCompleto.setHora_atrazo("00:00");

					registro_local.setHoras_atrazos(0);
					registro_local.setHoras_extras(duracao_trabalho);

				} else {
					// folgou
					rpCompleto.setHora_extra("00:00");
					rpCompleto.setHora_atrazo("00:00");
					registro_local.setHoras_atrazos(0);
					registro_local.setHoras_extras(0);
				}

			} else if (tem_descanso) {
				if (original.getEntrada1() != null && !rp.getEntrada1().equals("00:00"))
					rp.setEntrada1(original.getEntrada1());
				else
					rp.setEntrada1("DS");

				if (original.getSaida1() != null && !rp.getSaida1().equals("00:00"))
					rp.setSaida1(original.getSaida1());
				else
					rp.setSaida1("DS");

				if (original.getEntrada2() != null && !rp.getEntrada2().equals("00:00"))
					rp.setEntrada2(original.getEntrada2());
				else
					rp.setEntrada2("DS");

				if (original.getSaida2() != null && !rp.getSaida2().equals("00:00"))
					rp.setSaida2(original.getSaida2());
				else
					rp.setSaida2("DS");

				if (rotina_deste_dia.getHora_entrada1().equalsIgnoreCase("DESCANSO SEMANAL")
						|| rotina_deste_dia.getHora_saida1().equalsIgnoreCase("DESCANSO SEMANAL")
						|| rotina_deste_dia.getHora_entrada2().equalsIgnoreCase("DESCANSO SEMANAL")
						|| rotina_deste_dia.getHora_saida2().equalsIgnoreCase("DESCANSO SEMANAL")

				) {
					// se o dia inteiro for descanso, entao o dia inteiro e hora extra
					// ds
					if (duracao_trabalho > 0) {
						total_horas_extras += duracao_trabalho;

						// verifica se e sabado ou domindo
						if (rotina_deste_dia.getDia_da_semana() == 6) {
							// sabado
							total_horas_60 += duracao_trabalho;
						} else if (rotina_deste_dia.getDia_da_semana() == 7) {
							total_horas_100 += duracao_trabalho;

						} else {
							total_horas_50 += duracao_trabalho;
						}

						// trabalho esse dia hora extra
						String tempo_extra = LocalTime.MIN.plus(Duration.ofMinutes(duracao_trabalho)).toString();
						rpCompleto.setHora_extra(tempo_extra);
						rpCompleto.setHora_atrazo("00:00");

						registro_local.setHoras_atrazos(0);
						registro_local.setHoras_extras(duracao_trabalho);

					} else {
						// folgou
						rpCompleto.setHora_extra("00:00");
						rpCompleto.setHora_atrazo("00:00");

						registro_local.setHoras_atrazos(0);
						registro_local.setHoras_extras(0);
					}
				} else {
					// nao e o dia de hora extra

					if (duracao_trabalho >= duracao_rotina) {
						long hora_extra = duracao_trabalho - duracao_rotina;
						total_horas_extras += hora_extra;

						if (rotina_deste_dia.getDia_da_semana() == 6) {
							// sabado
							total_horas_60 += hora_extra;
						} else if (rotina_deste_dia.getDia_da_semana() == 7) {
							// domingoi
							total_horas_100 += hora_extra;

						} else {
							// meio de semana
							total_horas_50 += hora_extra;
						}

						String tempo_extra = LocalTime.MIN.plus(Duration.ofMinutes(hora_extra)).toString();
						rpCompleto.setHora_extra(tempo_extra);
						rpCompleto.setHora_atrazo("00:00");

						registro_local.setHoras_atrazos(0);
						registro_local.setHoras_extras(hora_extra);

					} else {
						long hora_atrazo = duracao_rotina - duracao_trabalho;
						total_horas_atrazo += hora_atrazo;

						String tempo_atrazo = LocalTime.MIN.plus(Duration.ofMinutes(hora_atrazo)).toString();
						rpCompleto.setHora_atrazo(tempo_atrazo);
						rpCompleto.setHora_extra("00:00");

						registro_local.setHoras_atrazos(0);
						registro_local.setHoras_extras(hora_atrazo);
					}

				}

			} else {
				// dia normal de trabalho

				// calculo de hora extra
				if (duracao_rotina <= 0) {
					// nao houve rotina nesse dia
					if (duracao_trabalho > 0) {

						if (rotina_deste_dia.getDia_da_semana() == 6) {
							// sabado
							total_horas_60 += duracao_trabalho;
						} else if (rotina_deste_dia.getDia_da_semana() == 7) {
							// domingoi
							total_horas_100 += duracao_trabalho;

						} else {
							// meio de semana
							total_horas_50 += duracao_trabalho;
						}

						// trabalho esse dia, 100% hora extra
						total_horas_extras += duracao_trabalho;
						String tempo_extra = LocalTime.MIN.plus(Duration.ofMinutes(duracao_trabalho)).toString();
						rpCompleto.setHora_extra(tempo_extra);
						rpCompleto.setHora_atrazo("00:00");

						registro_local.setHoras_atrazos(0);
						registro_local.setHoras_extras(duracao_trabalho);

					} else {
						// folgou
						rpCompleto.setHora_extra("00:00");
						rpCompleto.setHora_atrazo("00:00");

						registro_local.setHoras_atrazos(0);
						registro_local.setHoras_extras(0);
					}

				} else {
					// houve rotina nesse dia

					if (duracao_trabalho >= duracao_rotina) {
						long hora_extra = duracao_trabalho - duracao_rotina;
						total_horas_extras += hora_extra;

						if (rotina_deste_dia.getDia_da_semana() == 6) {
							// sabado
							total_horas_60 += hora_extra;
						} else if (rotina_deste_dia.getDia_da_semana() == 7) {
							// domingoi
							total_horas_100 += hora_extra;

						} else {
							// meio de semana
							total_horas_50 += hora_extra;
						}

						String tempo_extra = LocalTime.MIN.plus(Duration.ofMinutes(hora_extra)).toString();
						rpCompleto.setHora_extra(tempo_extra);
						rpCompleto.setHora_atrazo("00:00");

						registro_local.setHoras_atrazos(0);
						registro_local.setHoras_extras(hora_extra);

					} else {
						long hora_atrazo = duracao_rotina - duracao_trabalho;
						total_horas_atrazo += hora_atrazo;
						String tempo_atrazo = LocalTime.MIN.plus(Duration.ofMinutes(hora_atrazo)).toString();
						rpCompleto.setHora_atrazo(tempo_atrazo);
						rpCompleto.setHora_extra("00:00");

						registro_local.setHoras_atrazos(hora_atrazo);
						registro_local.setHoras_extras(0);

					}
				}

			}
			rpCompleto.setRp(rp);

			modeloRegistroPonto.onAdd(rpCompleto);
			dia++;

			registro_local.setData(data_rp);
			registros_locais.add(registro_local);

		}
		}else {
			JOptionPane.showMessageDialog(isto, "Nenhum Registro de Ponto Encontrado para o Mês selecionado");

			isto.dispose();
		}
		


		tHTrabalhadas = total_horas_normais_trabalhadas;
		tHMensal = total_duracao;
		tHAtrazo = total_horas_atrazo;
		tHExtras = total_horas_extras;
		tH100 = total_horas_100;
		tH50 = total_horas_50;
		tH60 = total_horas_60;
		tHNoturnas = total_horas_adicional_noturno;

		String s_total_h_ex = formatHora(total_horas_extras);
		String s_total_h_normais = formatHora(total_horas_normais_trabalhadas);
		String s_total_h_atraz = formatHora(total_horas_atrazo);
		String s_total_h_mensal = formatHora(total_duracao);
		String s_total_h_noturnas = formatHora(total_horas_adicional_noturno);

		// coloca os valores em global

		RegistroPontoMensalCompleto rpCompletoTotal = new RegistroPontoMensalCompleto();

		RegistroPontoDiario somatoria = new RegistroPontoDiario();
		somatoria.setData("TOTAIS");
		somatoria.setEntrada1("00:00");
		somatoria.setEntrada2("00:00");
		somatoria.setEntrada3("00:00");
		somatoria.setSaida1("00:00");
		somatoria.setSaida2("00:00");
		somatoria.setSaida3("00:00");

		rpCompletoTotal.setHora_atrazo(s_total_h_atraz);
		rpCompletoTotal.setHora_extra(s_total_h_ex);
		rpCompletoTotal.setHora_diaria(s_total_h_mensal);
		rpCompletoTotal.setHora_trabalhada(s_total_h_normais);
		rpCompletoTotal.setRp(somatoria);
		modeloRegistroPonto.onAdd(rpCompletoTotal);

		// informar totais

		lblTotalHorasMensal.setText(s_total_h_mensal);
		lblTotalHorasExtras.setText(s_total_h_ex);
		lblTotalHorasAtrazo.setText(s_total_h_atraz);
		lblTotalHorasNormais.setText(s_total_h_normais);
		lblTotalHorasNoturnas.setText(s_total_h_noturnas);

		// JOptionPane.showMessageDialog(null, "Total horas normais: " +
		// total_horas_normais_trabalhadas/60 +
		// "\nTotal horas extas: " + total_horas_extras/60 + "\nTotal atrazo: " +
		// total_horas_atrazo/60);

		lblTotalHoras100.setText(formatHora(total_horas_100));
		lblTotalHoras50.setText(formatHora(total_horas_50));
		lblTotalHoras60.setText(formatHora(total_horas_60));


		informarAtrazos(registros_locais, ultimo_dia);



		if (rdbtnDescontarAtrazosSim.isSelected())
			setInfoAtrazos(registros_locais, ultimo_dia);
		

	}

	public void setInfoAtrazos(ArrayList<RegistroHoraLocal> registros, int ultimo_dia) {

		int posicao = 0;
		ArrayList<Semana> semanas = new ArrayList<>();

		while (posicao < ultimo_dia) {
			RegistroHoraLocal registro = registros.get(posicao);
			Semana semana = new Semana();

			int dia_da_semana = registro.getDia_da_semana();
			semana.setData_inicial(registro.getData());
			if (dia_da_semana <= 7) {
				// dia de semana normal
				long acumulador = 0;
				int contador = dia_da_semana;
				while (contador <= 7) {

					if (!(posicao < ultimo_dia))
						break;
					else {
						long atrazo = registros.get(posicao).getHoras_atrazos();
						acumulador += atrazo;

						posicao++;
					}

					contador++;
				}
				semana.setData_final(registros.get(posicao - 1).getData());
				semana.setQuantidade_minutos(acumulador);
				semanas.add(semana);

			}

		}

		String texto = "";
		int count_semana = 1;
		for (Semana semana : semanas) {

			if (semana.getQuantidade_minutos() >= 480) {

				inserirDescontoDomingo(formatHora(semana.getQuantidade_minutos()) + " de atrasos na semana "
						+ count_semana + " resulta em Desconto do Domingo", piso_salarial / (double) ultimo_dia);

			}

			texto += ("Semana " + count_semana + ": De " + semana.getData_inicial() + " até " + semana.getData_final()
					+ " -> Total de Atrasos: " + formatHora(semana.getQuantidade_minutos()) + "\n");
			count_semana++;
		}

	}

	public void informarAtrazos(ArrayList<RegistroHoraLocal> registros, int ultimo_dia) {

		int posicao = 0;
		ArrayList<Semana> semanas = new ArrayList<>();

		while (posicao < ultimo_dia) {
			RegistroHoraLocal registro = registros.get(posicao);
			Semana semana = new Semana();

			int dia_da_semana = registro.getDia_da_semana();
			semana.setData_inicial(registro.getData());
			if (dia_da_semana <= 7) {
				// dia de semana normal
				long acumulador = 0;
				int contador = dia_da_semana;
				while (contador <= 7) {

					if (!(posicao < ultimo_dia))
						break;
					else {
						long atrazo = registros.get(posicao).getHoras_atrazos();
						acumulador += atrazo;

						posicao++;
					}

					contador++;
				}
				semana.setData_final(registros.get(posicao - 1).getData());
				semana.setQuantidade_minutos(acumulador);
				semanas.add(semana);

			}

		}

		String texto = "";
		int count_semana = 1;
		texto_atrazo = new ArrayList<>();
		long total_atrazo = 0;
		for (Semana semana : semanas) {
			total_atrazo += semana.getQuantidade_minutos();

			String texto_semana = "";
			if (semana.getQuantidade_minutos() >= 480) {
				texto_semana = "Semana " + count_semana + ": De " + semana.getData_inicial() + " até "
						+ semana.getData_final() + " -> Total de Atrasos: " + formatHora(semana.getQuantidade_minutos())
						+ " | SUJEITO AO DESCONTO DO DOMINGO";

				texto += ("Semana " + count_semana + ": De " + semana.getData_inicial() + " até "
						+ semana.getData_final() + " -> Total de Atrasos: " + formatHora(semana.getQuantidade_minutos())
						+ " | SUJEITO AO DESCONTO DO DOMINGO\n");
				count_semana++;

			} else {
				texto_semana = "Semana " + count_semana + ": De " + semana.getData_inicial() + " até "
						+ semana.getData_final() + " -> Total de Atrasos: "
						+ formatHora(semana.getQuantidade_minutos());

				texto += ("Semana " + count_semana + ": De " + semana.getData_inicial() + " até "
						+ semana.getData_final() + " -> Total de Atrasos: " + formatHora(semana.getQuantidade_minutos())
						+ "\n");
				count_semana++;
			}
			texto_atrazo.add(texto_semana);

		}

		texto_atrazo.add("Total de Atrazos: " + formatHora(total_atrazo));
		texto += ("Total de Atrazos: " + formatHora(total_atrazo));
		textAreaDescricaoAtrasos.setText(texto);
		// JOptionPane.showMessageDialog(isto, texto);

	}

	class Semana {

		String data_inicial, data_final;
		long quantidade_minutos;

		public String getData_inicial() {
			return data_inicial;
		}

		public void setData_inicial(String data_inicial) {
			this.data_inicial = data_inicial;
		}

		public String getData_final() {
			return data_final;
		}

		public void setData_final(String data_final) {
			this.data_final = data_final;
		}

		public long getQuantidade_minutos() {
			return quantidade_minutos;
		}

		public void setQuantidade_minutos(long quantidade_minutos) {
			this.quantidade_minutos = quantidade_minutos;
		}

	}

	class RegistroHoraLocal {

		String data;

		int dia_da_semana;
		long horas_extras, horas_atrazos;

		public int getDia_da_semana() {
			return dia_da_semana;
		}

		public String getData() {
			return data;
		}

		public void setData(String data) {
			this.data = data;
		}

		public void setDia_da_semana(int dia_da_semana) {
			this.dia_da_semana = dia_da_semana;
		}

		public long getHoras_extras() {
			return horas_extras;
		}

		public void setHoras_extras(long horas_extras) {
			this.horas_extras = horas_extras;
		}

		public long getHoras_atrazos() {
			return horas_atrazos;
		}

		public void setHoras_atrazos(long horas_atrazos) {
			this.horas_atrazos = horas_atrazos;
		}

	}

	public String formatHora(long minutos) {
		Duration duracao_total_horas_normais = Duration.ofMinutes(minutos);
		return String.format("%d:%02d", duracao_total_horas_normais.toHours(),
				duracao_total_horas_normais.toMinutesPart());

	}

	class CellRenderRPMensal extends DefaultTableCellRenderer {

		public CellRenderRPMensal() {
			super();
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			this.setHorizontalAlignment(CENTER);
			this.setFont(new Font("Tahoma", Font.PLAIN, 16));

			if (column >= 2 && column <= 7)
				this.setBackground(new Color(255, 255, 204));
			else if (column >= 8 && column <= 12) {
				this.setBackground(new Color(153, 204, 255));

				if (column == 8) {
					// coluna de rotina diaria
					this.setBackground(new Color(204, 255, 204));

				}

				if (column == 9)
					this.setForeground(new Color(0, 51, 51));// green
				else if (column == 10)
					this.setForeground(Color.BLUE);
				else if (column == 11)
					this.setForeground(Color.red);
				else if (column == 12)
					this.setForeground(new Color(102, 51, 1));
				else
					this.setForeground(Color.black);

			} else {
				this.setBackground(Color.white);
				this.setForeground(Color.black);

			}

			if (row == table.getRowCount() - 1) {
				// esta na ultima lnha
				for (int i = 0; i < table.getColumnCount(); i++) {
					this.setFont(new Font("Tahoma", Font.BOLD, 16));

				}
			}

			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		}

	}

	public void pesquisarContratoMesPagamento() {

		GerenciarBancoFuncionariosContratoTrabalho gerenciar = new GerenciarBancoFuncionariosContratoTrabalho();

		int mes = cBMes.getSelectedIndex() + 1;
		int ano = Integer.parseInt(entAno.getText());

		String s_mes = "";
		if (mes <= 9)
			s_mes = "0" + mes;
		else
			s_mes = mes + "";

		String dat_pri_dia_pag = "01/" + s_mes + "/" + ano;

		CadastroFuncionarioAdmissao ct = gerenciar.getcontratoMesPagamento(func.getId_funcionario(), dat_pri_dia_pag);
		if (ct != null) {

			ct_global = ct;

			lblCargo.setText(ct.getCargo());
			lblFuncao.setText(ct.getFuncao());
			lblDataAdmissao.setText(ct.getData_admissao());
			lblTipoContrato.setText(ct.getTipo_contrato());

			if (ct.getData_encerramento_contrato() != null) {
				lblStatusContrato.setText("CT ENC em " + ct.getData_encerramento_contrato());

			} else {
				lblStatusContrato.setText("ATIVO");

			}

			if (ct.getUltimo_salario() > 0) {
				lblUltimoValorSalarial.setText(NumberFormat.getCurrencyInstance(ptBr).format(ct.getUltimo_salario()));
				lblPisoSalarial.setText(NumberFormat.getCurrencyInstance(ptBr).format(ct.getUltimo_salario()));
				piso_salarial = ct.getUltimo_salario();
			} else {
				lblUltimoValorSalarial.setText(NumberFormat.getCurrencyInstance(ptBr).format(ct.getSalario()));
				lblPisoSalarial.setText(NumberFormat.getCurrencyInstance(ptBr).format(ct.getSalario()));
				piso_salarial = ct.getSalario();
			}

		} else {
			
			
			//buscar contrato inativo
			try {
			CadastroFuncionarioDemissao demissao = gerenciar.getcontratoInaAtivoPorFuncionario(func.getId_funcionario());
			
			CadastroFuncionarioAdmissao contrato = demissao.getContrato_trabalho();
			CadastroFuncionarioEvento evt = demissao.getEvento_demissao();

			
			lblCargo.setText(contrato.getCargo());
			lblFuncao.setText(contrato.getFuncao());
			lblDataAdmissao.setText(contrato.getData_admissao());
			lblTipoContrato.setText(contrato.getTipo_contrato());
			
			

			int motivo = evt.getMotivo_demissao();
			String s_motivo = "";
			if (motivo == 0) {
				s_motivo = "ENCERRAMENTO DE CONTRATO em " + evt.getData_folga();
			} else if (motivo == 1) {
				s_motivo = "JUSTA CAUSA em "+ evt.getData_folga();
			} else if (motivo == 2) {
				s_motivo = "SEM JUSTA CAUSA em "+ evt.getData_folga();
			} else if (motivo == 3) {
				s_motivo = "PEDIDO DE DISPENSA PELO COLABORADOR em "+ evt.getData_folga();
			}
			
			lblStatusContrato.setText(s_motivo);
			
			}catch(Exception e) {
				JOptionPane.showMessageDialog(isto, "Erro ao consultar ultimo contrato ativo do Colaborador!\nConsulte o administrador");
				isto.dispose();
			}
			
			
			if (ct.getUltimo_salario() > 0) {
				lblUltimoValorSalarial.setText(NumberFormat.getCurrencyInstance(ptBr).format(ct.getUltimo_salario()));
				lblPisoSalarial.setText(NumberFormat.getCurrencyInstance(ptBr).format(ct.getUltimo_salario()));
				piso_salarial = ct.getUltimo_salario();
			} else {
				lblUltimoValorSalarial.setText(NumberFormat.getCurrencyInstance(ptBr).format(ct.getSalario()));
				lblPisoSalarial.setText(NumberFormat.getCurrencyInstance(ptBr).format(ct.getSalario()));
				piso_salarial = ct.getSalario();
			}
		}

		GerenciarBancoRotina gerenciar_rotina = new GerenciarBancoRotina();
		ArrayList<CadastroFuncionarioRotinaTrabalho> rotinas = gerenciar_rotina.getRotinas(func.getId_funcionario());

		for (CadastroFuncionarioRotinaTrabalho rtb : rotinas) {
			String jornada = rtb.getHora_entrada1().replaceAll("DESCANSO SEMANAL", "DS") + " até "
					+ rtb.getHora_saida1().replaceAll("DESCANSO SEMANAL", "DS") + " e "
					+ rtb.getHora_entrada2().replaceAll("DESCANSO SEMANAL", "DS") + " até "
					+ rtb.getHora_saida2().replaceAll("DESCANSO SEMANAL", "DS");
			if (rtb.getDia_da_semana() == 1)
				lblJornadaSegunda.setText(jornada);
			else if (rtb.getDia_da_semana() == 2)
				lblJornadaTerca.setText(jornada);

			else if (rtb.getDia_da_semana() == 3)
				lblJornadaQuarta.setText(jornada);

			else if (rtb.getDia_da_semana() == 4)
				lblJornadaQuinta.setText(jornada);

			else if (rtb.getDia_da_semana() == 5)
				lblJornadaSexta.setText(jornada);

			else if (rtb.getDia_da_semana() == 6)
				lblJornadaSabado.setText(jornada);

			else if (rtb.getDia_da_semana() == 7)
				lblJornadaDomingo.setText(jornada);

		}

		rotinas_global = rotinas;
	}

	public void calcularAtrazos() {
		String s_total_h_atraz = formatHora(tHAtrazo);
		lblQuantidadeHorasAtrazo.setText(s_total_h_atraz);

		if (tHAtrazo > tH50) {

			long dif = tHAtrazo - tH50;
			tH50 = 0;
			if (dif > 0 && dif > tH60) {
				long dif2 = dif - tH60;
				tH60 = 0;

				if (dif2 > 0 && dif2 > tH100) {

					long dif3 = dif2 - tH100;
					tH100 = 0;
					// tHTrabalhadas = tHTrabalhadas - dif3;
					// inserir desconto de horas faltantes
					double valor_desconto = 0;
					double valor_hora = piso_salarial / 220.0;
					double valor_minuto = valor_hora / 60.0;

					valor_desconto = (dif3 * valor_minuto);

					inserirDescontoAtrazo(formatHora(dif3) + " de atrazos", valor_desconto);

				} else {
					tH100 = tH100 - dif2;
					lblQuantidadeHorasAtrazo.setText("0");
				}

			} else {
				tH60 = tH60 - dif;
				lblQuantidadeHorasAtrazo.setText("0");

			}

		} else {
			tH50 = tH50 - tHAtrazo;
			lblQuantidadeHorasAtrazo.setText("0");

		}

	}

	public void setInfoCalculoSalarial() {
		/*
		 * if (tipo_banco_horas == 0) { // banco negativo tHAtrazo = tHAtrazo +
		 * quantidade_banco_horas;
		 * 
		 * } else if (tipo_banco_horas == 1) { // tipo positivo tHAtrazo = tHAtrazo -
		 * quantidade_banco_horas;
		 * 
		 * }
		 */
		if (rdbtnDescontarAtrazosSim.isSelected()) {
			calcularAtrazos();

			if (rdbtnManter.isSelected()) {
				// manter banco atual
				tHExtras = tH50 + tH60 + tH100;

				manterBancoHoras();
			} else if (rdbtnPagar.isSelected()) {
				// pagar o banco
				tH50 = tH50 + quantidade_banco_horas;
				tHExtras = tH50 + tH60 + tH100;

			}

		} else {

			String s_total_h_atraz = formatHora(tHAtrazo);
			lblQuantidadeHorasAtrazo.setText(s_total_h_atraz);
		}

		double valor_hora = piso_salarial / 220.0;
		double valor_hora_noturna = (((piso_salarial / 220.0) * 1.2) - valor_hora);
		double valor_hora50 = valor_hora + (valor_hora * 0.5);
		double valor_hora60 = valor_hora + (valor_hora * 0.6);
		double valor_hora100 = valor_hora * 2.0;

		horas_global.setValor_hora50(valor_hora50);
		horas_global.setValor_hora60(valor_hora60);
		horas_global.setValor_hora100(valor_hora100);
		horas_global.setValor_hora_noturna(valor_hora_noturna);

		// valores de horas
		lblValorHoraNormal.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_hora));
		lblValorHoraNoturna.setText(
				"Adicional de " + NumberFormat.getCurrencyInstance(ptBr).format(valor_hora_noturna) + " por hora");

		lblValorHora50.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_hora50));
		lblValorHora60.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_hora60));
		lblValorHora100.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_hora100));

		String s_total_h_ex = formatHora(tHExtras);
		String s_total_h_normais = formatHora(tHTrabalhadas);
		// String s_total_h_atraz = formatHora(tHAtrazo);
		String s_total_h_mensal = formatHora(tHMensal);
		String s_total_h_50 = formatHora(tH50);
		String s_total_h_60 = formatHora(tH60);
		String s_total_h_100 = formatHora(tH100);

		// quantidade de horas
		lblQuantidadeHoras50.setText(s_total_h_50);
		lblQuantidadeHoras60.setText(s_total_h_60);
		lblQuantidadeHoras100.setText(s_total_h_100);

		lblQuantidadeHorasExtras.setText(s_total_h_ex);

		// calculo de horas
		double valor_total_horas_50 = tH50 * (valor_hora50 / 60.0);
		double valor_total_horas_60 = tH60 * (valor_hora60 / 60.0);
		double valor_total_horas_100 = tH100 * (valor_hora100 / 60.0);
		double valor_total_horas_noturnas = tHNoturnas * (valor_hora_noturna / 60.0);

		horas_global.setValor_hora(valor_hora);
		horas_global.setValor_total_hora50(valor_total_horas_50);
		horas_global.setValor_total_hora60(valor_total_horas_60);
		horas_global.setValor_total_hora100(valor_total_horas_100);
		horas_global.setValor_total_hora_noturnas(valor_total_horas_noturnas);

		double valor_total_horas_extras = valor_total_horas_50 + valor_total_horas_60 + valor_total_horas_100;
		valor_total_horas_extras_global = valor_total_horas_extras;
		horas_global.setValor_total_hora_extras(valor_total_horas_extras);

		lblValorTotalHoras50.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_horas_50));
		lblValorTotalHoras60.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_horas_60));
		lblValorTotalHoras100.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_horas_100));
		lblValorTotalHorasExtras.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_horas_extras));
		lblHorasNoturnas.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_horas_noturnas));

		// inserir acrescimo de hora noturna

		if (tHNoturnas > 0) {
			inserirAcrescimoHoraNoturna("Hora Noturna",
					"Valor Referente ao total  de " + formatHora(tHNoturnas) + " noturnas trabalhadas",
					valor_total_horas_noturnas);

		}
		// somatorias

		// if (tHExtras > tHAtrazo) {
		lblSalarioBaseAReceber.setText(NumberFormat.getCurrencyInstance(ptBr).format(piso_salarial));

		lblHorasExtrasAReceber.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_horas_extras));

		if (rdbtnInssSim.isSelected())
			calculoInss();
		else
			lblCalculoInss.setText("Cálculo não Selecionado");

		if (rdbtnIrrfSim.isSelected())
			calculoIrrf();
		else
			lblCalculoIrrf.setText("Cálculo não Selecionado");

		if (rdbtnDepositarSim.isSelected())
			depositarBancoHoras(true);
		else {

			if (!rdbtnManter.isSelected())
				depositarBancoHoras(false);

		}

	}

	public void calculoFinal() {

		double total_bruto = piso_salarial + valor_total_acrescimos_global + valor_total_horas_extras_global;
		double total_liquido = total_bruto - valor_total_descontos_global;

		lblValorTotalSalarioBruto.setText(formatarValor(total_bruto));

		lblValorTotalSalarioLiquido.setText(formatarValor(total_liquido));

	}

	public double calculoInss() {
		double inss = 0;
		double aliquota1 = 7.5 / 100;
		double aliquota2 = 9.0 / 100.0;
		double aliquota3 = 12.0 / 100.0;
		double aliquota4 = 14.0 / 100.0;

		String descricao = "";

		if (piso_salarial <= 1100) {
			// aliquota 7.5%
			double inss_faixa1 = 82.5;
			inss = inss_faixa1;

			descricao = "Sálario Base encontra-se na 1ª Faixa: " + NumberFormat.getCurrencyInstance(ptBr).format(inss);
			lblCalculoInss.setText(descricao);

		} else if (piso_salarial >= 1101.01 && piso_salarial <= 2203.08) {
			// aliquota de 9%
			double inss_faixa1 = 82.5;
			double inss_faixa2 = (piso_salarial - 1100.01) * aliquota2;

			inss = inss_faixa1 + inss_faixa2;
			descricao = "Sálario Base encontra-se na 2ª Faixa: (R$ 82,50 + ("
					+ NumberFormat.getCurrencyInstance(ptBr).format(piso_salarial) + " - R$ 1101,01 X 9%)): "
					+ NumberFormat.getCurrencyInstance(ptBr).format(inss);
			lblCalculoInss.setText(descricao);

		} else if (piso_salarial >= 2203.49 && piso_salarial <= 3305.22) {
			// aliquota de 12%
			double inss_faixa1 = 82.5;
			double inss_faixa2 = 99.31;
			double inss_faixa3 = (piso_salarial - 2203.49) * aliquota3;

			inss = inss_faixa1 + inss_faixa2 + inss_faixa3;
			descricao = "Sálario Base encontra-se na 3ª Faixa: (R$ 82,50 + R$ 99,31 + ("
					+ NumberFormat.getCurrencyInstance(ptBr).format(piso_salarial) + " - R$ 2203,49 X 12%)): "
					+ NumberFormat.getCurrencyInstance(ptBr).format(inss);
			lblCalculoInss.setText(descricao);

		} else if (piso_salarial >= 3305.23 && piso_salarial <= 6433.27) {
			// aliquota de 14%
			double inss_faixa1 = 82.5;
			double inss_faixa2 = 99.31;
			double inss_faixa3 = 132.21;
			double inss_faixa4 = (piso_salarial - 3305.23) * aliquota4;

			inss = inss_faixa1 + inss_faixa2 + inss_faixa3 + inss_faixa4;
			descricao = "Sálario Base encontra-se na 4ª Faixa: (R$ 82,50 + R$ 99,31 + R$ 132,21 + ("
					+ NumberFormat.getCurrencyInstance(ptBr).format(piso_salarial) + " - R$ 3305,23 X 14%)): "
					+ NumberFormat.getCurrencyInstance(ptBr).format(inss);
			lblCalculoInss.setText(descricao);

		} else if (piso_salarial > 6433.27) {
			descricao = "Sálario Base encontra-se acima da 4ª Faixa: (R$ 82,50 + R$ 99,31 + R$ 132,21 + 437,97: R$ 751,99";
			lblCalculoInss.setText(descricao);

			inss = 751.99;
		}

		inss_global = inss;
		inserirDescontoInss(descricao, inss);

		return inss;
	}

	public void inserirDescontoInss(String descricao, double valor) {

		// verifica se ja existe este desconto na tabela
		boolean tem_na_tabela = false;
		for (CadastroFuncionarioCalculo cad : modelDescontos.getLista()) {
			if (cad.getNome().equalsIgnoreCase("DESCONTO INSS")) {
				tem_na_tabela = true;
				break;
			}
		}

		if (!tem_na_tabela) {
			CadastroFuncionarioCalculo cad = new CadastroFuncionarioCalculo();

			cad.setTipo_calculo(0);
			String nome;
			int referencia_calculo, referencia_valor;
			double total;
			nome = "DESCONTO INSS";
			cad.setDescricao(descricao);
			cad.setNome(nome);
			cad.setReferencia_calculo(0);
			cad.setReferencia_valor(1);
			cad.setValor(valor);
			cad.setTotal(valor);
			cad.setQuantidade(1);

			this.adicionarDesconto(cad);
		}

	}

	public void inserirDescontoAtrazo(String descricao, double valor) {
		CadastroFuncionarioCalculo cad = new CadastroFuncionarioCalculo();

		cad.setTipo_calculo(0);
		String nome;
		int referencia_calculo, referencia_valor;
		double total;
		nome = "DESCONTO POR ATRAZO";
		cad.setDescricao(descricao);
		cad.setNome(nome);
		cad.setReferencia_calculo(0);
		cad.setReferencia_valor(1);
		cad.setValor(valor);
		cad.setTotal(valor);
		cad.setQuantidade(1);

		this.adicionarDesconto(cad);

	}

	public void inserirDescontoDomingo(String descricao, double valor) {
		CadastroFuncionarioCalculo cad = new CadastroFuncionarioCalculo();

		cad.setTipo_calculo(0);
		String nome;
		int referencia_calculo, referencia_valor;
		double total;
		nome = "DESCONTO DE DOMINGO POR ATRASO SEMANAL SUPERIOR A 8 HORAS";
		cad.setDescricao(descricao);
		cad.setNome(nome);
		cad.setReferencia_calculo(0);
		cad.setReferencia_valor(1);
		cad.setValor(valor);
		cad.setTotal(valor);
		cad.setQuantidade(1);

		this.adicionarDesconto(cad);

	}

	public void inserirDescontoIrrf(String descricao, double valor) {
		// verifica se ja existe este desconto na tabela
		boolean tem_na_tabela = false;
		for (CadastroFuncionarioCalculo cad : modelDescontos.getLista()) {
			if (cad.getNome().equalsIgnoreCase("DESCONTO IRFF")) {
				tem_na_tabela = true;
				break;
			}
		}

		if (!tem_na_tabela) {
			CadastroFuncionarioCalculo cad = new CadastroFuncionarioCalculo();

			cad.setTipo_calculo(0);
			String nome;
			int referencia_calculo, referencia_valor;
			double total;
			nome = "DESCONTO IRFF";
			cad.setDescricao(descricao);
			cad.setNome(nome);
			cad.setReferencia_calculo(0);
			cad.setReferencia_valor(1);
			cad.setValor(valor);
			cad.setTotal(valor);
			cad.setQuantidade(1);

			this.adicionarDesconto(cad);
		}

	}

	public double calculoIrrf() {
		// double inss = calculoInss();
		double base_calculo = piso_salarial - inss_global;
		double irrf = 0;

		double aliquota1 = 7.5 / 100;
		double aliquota2 = 15.0 / 100.0;
		double aliquota3 = 22.5 / 100.0;
		double aliquota4 = 27.5 / 100.0;

		double taxa1 = 142.80;
		double taxa2 = 354.80;
		double taxa3 = 636.13;
		double taxa4 = 869.36;

		String descricao = "";

		if (base_calculo <= 1903.98) {
			irrf = 0;
			descricao = "Sálario Base encontra-se na 1ª Faixa: ISENTO DE IRRF";
			lblCalculoIrrf.setText(descricao);

		} else if (base_calculo >= 1903.99 && base_calculo <= 2826.65) {
			// aliquota 7.5% e taxa 142.80

			irrf = (base_calculo * aliquota1) - taxa1;
			descricao = "Sálario Base encontra-se na 2ª Faixa: (" + formatarValor(piso_salarial) + " - "
					+ formatarValor(inss_global) + ") X 7,5% - R$ 142,80: " + formatarValor(irrf);
			lblCalculoIrrf.setText(descricao);

		} else if (base_calculo >= 2826.66 && base_calculo <= 3751.05) {
			// aliquota 15% e taxa 354.80
			irrf = (base_calculo * aliquota2) - taxa2;
			descricao = "Sálario Base encontra-se na 3ª Faixa: (" + formatarValor(piso_salarial) + " - "
					+ formatarValor(inss_global) + ") X 15% - R$ 354,80: " + formatarValor(irrf);
			lblCalculoIrrf.setText(descricao);
		} else if (base_calculo >= 3751.06 && base_calculo <= 4664.68) {
			// aliquota 22.5% e taxa 636.13

			irrf = (base_calculo * aliquota3) - taxa3;
			descricao = "Sálario Base encontra-se na 4ª Faixa: (" + formatarValor(piso_salarial) + " - "
					+ formatarValor(inss_global) + ") X 22,5% - R$ 636,13: " + formatarValor(irrf);
			lblCalculoIrrf.setText(descricao);
		} else if (base_calculo > 4664.68) {
			// aliquota 27.5% e taxa 869.36
			irrf = (base_calculo * aliquota4) - taxa4;
			descricao = "Sálario Base encontra-se acima da 4ª Faixa: (" + formatarValor(piso_salarial) + " - "
					+ formatarValor(inss_global) + ") X 27,5% - R$ 869,36: " + formatarValor(irrf);
			lblCalculoIrrf.setText(descricao);
		}

		inserirDescontoIrrf(descricao, irrf);
		return irrf;

	}

	public String formatarValor(double valor) {
		return NumberFormat.getCurrencyInstance(ptBr).format(valor);
	}

	public class DescontoTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int id = 0;
		private final int nome = 1;
		private final int descricao = 2;
		private final int referencia_calculo = 3;
		private final int quantidade = 4;
		private final int referencia_valor = 5;
		private final int valor = 6;
		private final int total = 7;

		private final String colunas[] = { "ID", "Nome", "Descrição", "Referência Cálculo", "Quantidade",
				"Referência Valor", "Valor", "Total" };
		Locale ptBr = new Locale("pt", "BR");

		private final ArrayList<CadastroFuncionarioCalculo> dados = new ArrayList<>();// usamos como dados uma lista
		// genérica de
		// nfs

		private String[] ref_cal = { "Sálario Base", "Sálario Líquido", "Sálario Bruto", "Valor Hora Trabalhada",
				"Nenhuma" };

		private String[] ref_valor = { "Porcentagem", "Fixo" };

		public DescontoTableModel() {

		}

		public ArrayList<CadastroFuncionarioCalculo> getLista() {
			return dados;
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
				return String.class;
			case nome:
				return String.class;
			case descricao:
				return String.class;
			case referencia_calculo:
				return String.class;
			case quantidade:
				return String.class;
			case referencia_valor:
				return String.class;
			case valor:
				return String.class;
			case total:
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
			NumberFormat z = NumberFormat.getNumberInstance();

			// pega o dados corrente da linha
			CadastroFuncionarioCalculo desconto = dados.get(rowIndex);

			// retorna o valor da coluna
			switch (columnIndex) {

			case id:
				return desconto.getId_calculo();
			case nome:
				return desconto.getNome();
			case descricao:
				return desconto.getDescricao();
			case referencia_calculo: {
				int referencia_calculo = desconto.getReferencia_calculo();
				return ref_cal[referencia_calculo];

			}
			case quantidade: {
				return desconto.getQuantidade();
			}
			case referencia_valor: {
				int ref_val = desconto.getReferencia_valor();
				return ref_valor[ref_val];
			}
			case valor: {
				return formatarValor(desconto.getValor());
			}

			case total: {
				return formatarValor(desconto.getTotal());

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
			CadastroFuncionarioCalculo recebimento = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public CadastroFuncionarioCalculo getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(CadastroFuncionarioCalculo nota) {
			return dados.indexOf(nota);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(CadastroFuncionarioCalculo nota) {
			dados.add(nota);
			fireTableRowsInserted(indexOf(nota), indexOf(nota));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<CadastroFuncionarioCalculo> dadosIn) {
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
		public void onRemove(CadastroFuncionarioCalculo nota) {
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

	public class AcrescimoTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int id = 0;
		private final int nome = 1;
		private final int descricao = 2;
		private final int referencia_calculo = 3;
		private final int quantidade = 4;
		private final int referencia_valor = 5;
		private final int valor = 6;
		private final int total = 7;

		private final String colunas[] = { "ID", "Nome", "Descrição", "Referência Cálculo", "Quantidade",
				"Referência Valor", "Valor", "Total" };
		Locale ptBr = new Locale("pt", "BR");

		private final ArrayList<CadastroFuncionarioCalculo> dados = new ArrayList<>();// usamos como dados uma lista
		// genérica de
		// nfs

		private String[] ref_cal = { "Sálario Base", "Sálario Líquido", "Sálario Bruto", "Valor Hora Trabalhada",
				"Nenhuma" };

		private String[] ref_valor = { "Porcentagem", "Fixo" };

		public AcrescimoTableModel() {

		}

		public ArrayList<CadastroFuncionarioCalculo> getLista() {
			return dados;
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
				return String.class;
			case nome:
				return String.class;
			case descricao:
				return String.class;
			case referencia_calculo:
				return String.class;
			case quantidade:
				return String.class;
			case referencia_valor:
				return String.class;
			case valor:
				return String.class;
			case total:
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
			NumberFormat z = NumberFormat.getNumberInstance();

			// pega o dados corrente da linha
			CadastroFuncionarioCalculo desconto = dados.get(rowIndex);

			// retorna o valor da coluna
			switch (columnIndex) {

			case id:
				return desconto.getId_calculo();
			case nome:
				return desconto.getNome();
			case descricao:
				return desconto.getDescricao();
			case referencia_calculo: {
				int referencia_calculo = desconto.getReferencia_calculo();
				return ref_cal[referencia_calculo];

			}
			case quantidade: {
				return desconto.getQuantidade();
			}
			case referencia_valor: {
				int ref_val = desconto.getReferencia_valor();
				return ref_valor[ref_val];
			}
			case valor: {
				return formatarValor(desconto.getValor());
			}
			case total: {
				return formatarValor(desconto.getTotal());

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
			CadastroFuncionarioCalculo recebimento = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public CadastroFuncionarioCalculo getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(CadastroFuncionarioCalculo nota) {
			return dados.indexOf(nota);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(CadastroFuncionarioCalculo nota) {
			dados.add(nota);
			fireTableRowsInserted(indexOf(nota), indexOf(nota));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<CadastroFuncionarioCalculo> dadosIn) {
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
		public void onRemove(CadastroFuncionarioCalculo nota) {
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

	public void adicionarDesconto(CadastroFuncionarioCalculo desconto) {
		modelDescontos.onAdd(desconto);
	}

	public void adicionarAcrescimo(CadastroFuncionarioCalculo acrescimo) {
		modelAcrescimos.onAdd(acrescimo);
	}

	public CadastroFuncionarioCalculo getAcrescimoSelecionado() {
		int indiceDaLinha = tabela_acrescimos.getSelectedRow();

		return modelAcrescimos.getValue(indiceDaLinha);

	}

	public CadastroFuncionarioCalculo getDescontoSelecionado() {
		int indiceDaLinha = tabela_descontos.getSelectedRow();

		return modelDescontos.getValue(indiceDaLinha);

	}

	public void pesquisarBancoHoras() {

		GerenciarBancoFuncionarioBancoHoras gerenciar = new GerenciarBancoFuncionarioBancoHoras();
		int mes = cBMes.getSelectedIndex();
		if(mes == 0) {
			lblDescricaoBancoHoras.setText("BH de DEZEMBRO");

		}else {
			lblDescricaoBancoHoras.setText("BH de " + cBMes.getItemAt(mes - 1).toString());

		}
		
		String ano = entAno.getText();
		
		
		CadastroFuncionarioBancoHoras banco = null;

		if(mes == 0) {
			//se for janeiro, pega o banco do mes de dezembro do ano passado
			int ano_atual = new GetData().getAnoAtual();
			try {
				
			 ano_atual = Integer.parseInt(ano);
			
			}catch(Exception e) {
				ano_atual = new GetData().getAnoAtual();
			}
			
			int ano_passado = ano_atual - 1;
			banco = gerenciar.getBancoHorasPorFuncionarioPorMesAno(func.getId_funcionario(),
					12, Integer.toString(ano_passado));

		}else {
			banco = gerenciar.getBancoHorasPorFuncionarioPorMesAno(func.getId_funcionario(),
					mes, ano);

		}
		
		if (banco != null) {

			String quanti = banco.getQuantidade_horas();
			long long_quanti = Long.parseLong(quanti);
			lblQuantidadeBancoHoras.setText(formatHora(long_quanti));

			if (banco.getTipo_banco() == 0) {
				lblTipoBancoHoras.setText("NEGATIVO");
			} else if (banco.getTipo_banco() == 1) {
				lblTipoBancoHoras.setText("POSITIVO");
			}

			tipo_banco_horas = banco.getTipo_banco();
			if(long_quanti < 0)
				long_quanti = long_quanti * -1;
			quantidade_banco_horas = long_quanti;

		} else {
			lblQuantidadeBancoHoras.setText("0");

		}
	}

	public void atualizar() {

try {
		pesquisarDemostrativo();

		pesquisarBancoHoras();

		setInfoCalculoSalarial();
		calculoFinal();
		pesquisar_contas_associadas();
}catch(Exception e) {
	e.printStackTrace();
}
		
	}

	public void depositarBancoHoras(boolean depositar) {
		int mes = cBMes.getSelectedIndex() + 1;
		String s_mes = "";
		if(mes > 11) {
			 s_mes = cBMes.getItemAt(11).toString();

		}else {
		 s_mes = cBMes.getItemAt(mes).toString();
		}
		
		lblDescricaoNovoBancoHoras.setText("BH de " + s_mes + "/" + entAno.getText());

		if (depositar) {

			String texto_calculo = "";
			// zera os valores
			lblQuantidadeHoras50.setText("0");

			horas_global.setTH50(0);
			horas_global.setTH60(0);
			horas_global.setTH100(0);

			horas_global.setValor_total_hora_extras(0);

			horas_global.setOpcao_banco(1);
			horas_global.setMes(s_mes);

			lblValorTotalHoras50.setText("0");

			lblQuantidadeHoras60.setText("0");
			lblValorTotalHoras60.setText("0");

			lblQuantidadeHoras100.setText("0");
			lblValorTotalHoras100.setText("0");

			lblQuantidadeHorasExtras.setText("0");
			lblValorTotalHorasExtras.setText("0");
			lblHorasExtrasAReceber.setText("");

			long dif = tHExtras - tHAtrazo;
			if (dif > 0) {

				texto_calculo = "Total de Horas Extras(" + formatHora(tHExtras) + ")"
						+ " é Maior que o Total de Horas de Atrazo(" + formatHora(tHAtrazo)
						+ "), Hora é Dobrada, Parcial: " + formatHora(tHExtras) + " - " + formatHora(tHAtrazo) + "= "
						+ formatHora(dif) + " X 2 = ";

				dif = dif * 2;
				texto_calculo += (formatHora(dif) + "\n");

			} else {
				texto_calculo = "Total de Horas Extras(" + formatHora(tHExtras) + ")"
						+ " é Menor que o Total de Horas de Atrazo(" + formatHora(tHAtrazo)
						+ "), Hora não é dobrada, Parcial: " + formatHora(tHExtras) + " - " + formatHora(tHAtrazo)
						+ "\n";
			}

			if (tipo_banco_horas == 0 ) {
				// horas negativas no banco e horas positivas
				texto_calculo += ("Soma Final: " + formatHora(dif) + " - " + formatHora(quantidade_banco_horas)
						+ "(BH)");

				dif = dif - quantidade_banco_horas; //
				texto_calculo += ("= " + formatHora(dif) + "\n");

			}
			else if (tipo_banco_horas == 1) {
				// horas positivas no banco
				texto_calculo += ("Soma Final: " + formatHora(dif) + " + " + formatHora(quantidade_banco_horas)
						+ "(BH)");

				dif = dif + quantidade_banco_horas;
				texto_calculo += ("= " + formatHora(dif) + "\n");

			}

			if (dif > 0) {
				lblTipoNovoBancoHoras.setText("POSITIVO");
				tipo_novo_banco_horas_global = 1;
				horas_global.setTipo_banco(1);
				
			} else {
				lblTipoNovoBancoHoras.setText("NEGATIVO");
				tipo_novo_banco_horas_global = 0;
				horas_global.setTipo_banco(0);
				dif = dif * -1;
			}

			quantidade_novo_banco_horas_global = dif;
			lblQuantidadeNovoBancoHoras.setText(formatHora(dif));

			horas_global.setQuantidade(dif);
			valor_total_horas_extras_global = 0;

			lblInfoCalculoDepositoBH.setText(texto_calculo);

		} else {
			horas_global.setOpcao_banco(0);

			lblTipoNovoBancoHoras.setText("TIPO");
			lblQuantidadeNovoBancoHoras.setText("0");
			lblInfoCalculoDepositoBH.setText("");

		}

	}

	public void manterBancoHoras() {

		int mes = cBMes.getSelectedIndex() + 1;
		String s_mes = "";
		if(mes > 11) {
			 s_mes = cBMes.getItemAt(11).toString();

		}else {
		 s_mes = cBMes.getItemAt(mes).toString();
		}
		
		lblDescricaoNovoBancoHoras.setText("BH de " + s_mes + "/" + entAno.getText());

		
		horas_global.setOpcao_banco(1);
		horas_global.setMes(s_mes);

		if (quantidade_banco_horas > 0) {
			lblTipoNovoBancoHoras.setText("POSITIVO");
			tipo_novo_banco_horas_global = 1;
			horas_global.setTipo_banco(1);

		} else if (quantidade_banco_horas < 0) {
			lblTipoNovoBancoHoras.setText("NEGATIVO");
			tipo_novo_banco_horas_global = 0;
			horas_global.setTipo_banco(0);

		}

		quantidade_novo_banco_horas_global = quantidade_banco_horas;
		lblQuantidadeNovoBancoHoras.setText(formatHora(quantidade_banco_horas));

		horas_global.setQuantidade(quantidade_banco_horas);

	}

	public ArrayList<RegistroPontoMensalCompleto> getRegistrosPonto() {
		ArrayList<RegistroPontoMensalCompleto> registros_selecionados = new ArrayList<>();

		for (int i = 0; i < tabela_demonstrativo.getRowCount(); i++) {

			RegistroPontoMensalCompleto rp = modeloRegistroPonto.getValue(i);
			registros_selecionados.add(rp);
		}

		return registros_selecionados;

	}

	public ArrayList<CadastroFuncionarioCalculo> getDescontos() {
		ArrayList<CadastroFuncionarioCalculo> descontos_selecionados = new ArrayList<>();

		for (int i = 0; i < tabela_descontos.getRowCount(); i++) {

			CadastroFuncionarioCalculo desconto = modelDescontos.getValue(i);
			descontos_selecionados.add(desconto);
		}

		return descontos_selecionados;

	}

	public ArrayList<CadastroFuncionarioCalculo> getAcrescimos() {
		ArrayList<CadastroFuncionarioCalculo> acrescimos_selecionados = new ArrayList<>();

		for (int i = 0; i < tabela_acrescimos.getRowCount(); i++) {

			CadastroFuncionarioCalculo acrescimo = modelAcrescimos.getValue(i);
			acrescimos_selecionados.add(acrescimo);
		}

		return acrescimos_selecionados;

	}

	public CadastroFuncionarioBancoHoras getDadosBanco() {

		CadastroFuncionarioBancoHoras banco = new CadastroFuncionarioBancoHoras();

		banco.setId_funcionario(func.getId_funcionario());
		banco.setMes_referencia(cBMes.getSelectedIndex() + 1);

		banco.setTipo_banco(tipo_novo_banco_horas_global);

		
		if(quantidade_novo_banco_horas_global < 0){
			quantidade_novo_banco_horas_global = quantidade_novo_banco_horas_global * -1;
		}
		
		banco.setQuantidade_horas(Long.toString(quantidade_novo_banco_horas_global));
		banco.setAno(entAno.getText());
		return banco;

	}

	private CadastroFuncionarioSalario getDadosSalvar() {
		CadastroFuncionarioSalario cad = new CadastroFuncionarioSalario();

		cad.setId_funcionario(func.getId_funcionario());
		cad.setSalario_base(piso_salarial);
		cad.setTotal_acrescimos(valor_total_acrescimos_global);
		cad.setTotal_descontos(valor_total_descontos_global);
		cad.setTotal_hora_extras(valor_total_horas_extras_global);

		cad.setAno(Integer.parseInt(entAno.getText()));
		cad.setMes(cBMes.getSelectedIndex());
		cad.setId_ct_trabalho(ct_global.getId_contrato());

		return cad;
	}

	public void salvar(String arquivo) {
		CadastroFuncionarioSalario salario = getDadosSalvar();
		GerenciarBancoFuncionarioSalarios gerenciar = new GerenciarBancoFuncionarioSalarios();
		int inserir = gerenciar.inserirsalario(salario);
		if (inserir > 0) {
			JOptionPane.showMessageDialog(isto, "Sálario Cadastrado na Base de Dados");

			// salvar o arquivo

			salvarArquivo(arquivo);
			if (rdbtnDepositarSim.isSelected() || manter_global) {
				// criar novo banco de horas
				GerenciarBancoFuncionarioBancoHoras gerenciar_banco = new GerenciarBancoFuncionarioBancoHoras();
				CadastroFuncionarioBancoHoras banco = getDadosBanco();
				if (banco != null) {
					int inserir_banco = gerenciar_banco.inserirbanco_horas(banco);
					if (inserir_banco > 0) {
						JOptionPane.showMessageDialog(isto, "Novo Banco de Horas Registrado");
						((TelaGerenciarFuncionario) janela_pai_global).pesquisar_banco_horas();
						((TelaGerenciarFuncionario) janela_pai_global).pesquisar_salarios();
					} else {
						JOptionPane.showMessageDialog(isto,
								"Erro ao Cadastrar Banco de Horas\nConsulte o administrador");
						((TelaGerenciarFuncionario) janela_pai_global).pesquisar_salarios();

					}
				} else {
					JOptionPane.showMessageDialog(isto,
							"Erro ao recuperar informações do novo banco de horas!\nConsulte o administrador");

				}
			} else {
				((TelaGerenciarFuncionario) janela_pai_global).pesquisar_salarios();

			}
			double liquido = (salario.getSalario_base() + salario.getTotal_acrescimos()
			+ salario.getTotal_hora_extras()) - salario.getTotal_descontos();
			// criar o lancamento
			// verifica se ja existe um lancamento deste mes
			if (tem_lancamento) {

				// criar a parcela
				
				Parcela parcela_salario_liquido = getDadosParcela(id_lancamento_global, liquido);

				Lancamento lancamento = new GerenciarBancoLancamento().getLancamento(id_lancamento_global);
				boolean salvar_parcela = salvarParcela(parcela_salario_liquido, lancamento);
				if (salvar_parcela) {
					// criar o pagamento
					TelaFinanceiroGerenciarLancamento tela = new TelaFinanceiroGerenciarLancamento(lancamento, isto);
					tela.setVisible(true);

				} else {
					JOptionPane.showMessageDialog(isto,
							"Erro ao atualizar o lançamento já existente!\nErro: Parcela não inserida\nConsulte o administrador!");
				}

			} else {
				Lancamento lancamento = getSalvarLancamento(liquido);
				int salvar_lancamento = salvarLancamento(lancamento);
				if (salvar_lancamento > 0) {
					Lancamento lancamento_criado = new GerenciarBancoLancamento().getLancamento(salvar_lancamento);

					// criar o pagamento
					TelaFinanceiroGerenciarLancamento tela = new TelaFinanceiroGerenciarLancamento(lancamento_criado, isto);
					tela.setVisible(true);

				} else {
					JOptionPane.showMessageDialog(isto,
							"Erro ao criar o lançamento para este pagamento!\nErro: Lançamento não inserido\nConsulte o administrador!");
				}
			}

			isto.dispose();

		} else {
			JOptionPane.showMessageDialog(isto, "Erro ao Salvar o Salário na Base de Dados\nConsulte o Administrador");
		}
	}

	public void salvarArquivo(String url) {

		String nome, descricao, nome_arquivo, caminho_arquivo;
		int id_contrato_pai;

		int mes = cBMes.getSelectedIndex();
		String s_mes = cBMes.getItemAt(mes).toString();
		nome = "salario_mes_" + s_mes + "_" + entAno.getText();
		descricao = "relatorio mensal de salario";
		caminho_arquivo = url;

		String nome_arquivo_original_conteudo[] = caminho_arquivo.split("\"");
		String nome_arquivo_original = nome_arquivo_original_conteudo[nome_arquivo_original_conteudo.length - 1];
		String extensaoDoArquivo = FilenameUtils.getExtension(nome_arquivo_original);

		// copiar o arquivo para a nova pasta

		try {
			// copiar o arquivo para a pasta do contrato
			ManipularTxt manipular = new ManipularTxt();
			String unidade_base_dados = configs_globais.getServidorUnidade();

			// pegar nome da pasta
			String nome_pasta = "colaborador_" + func.getCpf();

			String caminho_salvar = unidade_base_dados + "\\" + "E-Contract\\arquivos\\colaboradores\\" + nome_pasta
					+ "\\documentos";
			manipular.criarDiretorio(caminho_salvar);

			GetData dados = new GetData();
			String dataString = dados.getData();
			String horaString = dados.getHora();

			if (caminho_arquivo.length() > 10) {
				if (nome.length() != 0 && !nome.equals("") && !nome.equals(" ") && !nome.equals("          ")) {
					nome_arquivo = func.getApelido() + "_" + nome + "_" + horaString.replaceAll(":", "_") + "."
							+ extensaoDoArquivo;

					String caminho_completo = caminho_salvar + "\\" + nome_arquivo;

					boolean movido = manipular.copiarNFe(caminho_arquivo, caminho_completo);

					if (movido) {

						// atualizar status do contrato
						CadastroDocumento novo_documento = new CadastroDocumento();
						novo_documento.setDescricao(descricao);
						novo_documento.setNome(nome);

						String s_tipo_documento = "Comprovantes";
						int tipo_documento = -1;

						if (s_tipo_documento.equalsIgnoreCase("Documento Pessoal")) {
							tipo_documento = 1;
						} else if (s_tipo_documento.equalsIgnoreCase("Comprovantes")) {
							tipo_documento = 2;
						} else if (s_tipo_documento.equalsIgnoreCase("Outros")) {
							tipo_documento = 3;
						}

						novo_documento.setTipo(tipo_documento);
						novo_documento.setId_pai(0);
						novo_documento.setNome_arquivo(nome_arquivo);
						novo_documento.setId_funcionario(func.getId_funcionario());

						GerenciarBancoDocumento gerenciar_doc = new GerenciarBancoDocumento();
						int cadastrar = gerenciar_doc.inserir_documento_padrao_funcionario(novo_documento);
						if (cadastrar > 0) {
							JOptionPane.showMessageDialog(isto, "Arquivo copiado e salvo na base de dados\nOrigem: "
									+ caminho_arquivo + "\nDestino: " + caminho_completo);

							((TelaGerenciarFuncionario) janela_pai_global).atualizarArvoreDocumentos();
						} else {
							JOptionPane.showMessageDialog(isto,
									"Arquivo copiado, mas não pode ser salvo\nConsulte o adiministrador do sistema!");
							// cancelar operacao e excluir o arquivo
							if (manipular.apagarArquivo(caminho_completo)) {

							} else {
								JOptionPane.showMessageDialog(isto,
										"Erro ao excluir arquivo!\nConsulte o administrador do sistema");
							}
						}

					} else {
						JOptionPane.showMessageDialog(isto, "Arquivo  não pode ser copiado\nOrigem: " + caminho_arquivo
								+ "\nDestino: " + caminho_completo + "\n Consulte o administrador!");

					}
				} else {
					JOptionPane.showMessageDialog(isto, "Nome do arquivo invalido!");

				}
			} else {
				JOptionPane.showMessageDialog(isto, "Caminho do arquivo invalido!");
			}

		} catch (IOException f) {

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

	public void pesquisar_contas_associadas() {

		GerenciarBancoFuncionarios gerenciar = new GerenciarBancoFuncionarios();

		int mes = cBMes.getSelectedIndex() + 1;
		int ano = 0;
		try {
			ano = Integer.parseInt(entAno.getText());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(isto, "Ano Inválido!");
			return;
		}
		ArrayList<FuncionarioContaAssociada> lista_contas = new ArrayList<>();
		lista_contas = gerenciar.buscarContasAssociadas(func.getId_funcionario());

		GerenciarBancoFinanceiroPagamento gerenciar_pags = new GerenciarBancoFinanceiroPagamento();
		for (FuncionarioContaAssociada fca : lista_contas) {

			id_conta_associada_global = fca.getCliente().getId();
			ArrayList<FinanceiroPagamentoCompleto> pagamentos = gerenciar_pags
					.getFinanceiroPagamentosPorCliente(fca.getCliente().getId(), mes, ano);
			// inserir desconto de adiantamento

			for (FinanceiroPagamentoCompleto pags : pagamentos) {
				tem_lancamento = true;
				id_lancamento_global = pags.getFpag().getId_lancamento();
				inserirDescontoAdiantamento("SALARIO - ADIANTAMENTO em " + pags.getFpag().getData_pagamento(),
						pags.getFpag().getDescricao(), pags.getFpag().getValor().doubleValue());

			}

		}

	}

	public void inserirDescontoAdiantamento(String nome, String descricao, double valor) {

		// verifica se ja existe este desconto na tabela
		boolean tem_na_tabela = false;
		for (CadastroFuncionarioCalculo cad : modelDescontos.getLista()) {
			if (cad.getNome().equalsIgnoreCase(nome)) {
				tem_na_tabela = true;
				break;
			}
		}

		if (!tem_na_tabela) {
			CadastroFuncionarioCalculo cad = new CadastroFuncionarioCalculo();

			cad.setTipo_calculo(0);
			int referencia_calculo, referencia_valor;
			double total;
			cad.setDescricao(descricao);
			cad.setNome(nome);
			cad.setReferencia_calculo(0);
			cad.setReferencia_valor(1);
			cad.setValor(valor);
			cad.setTotal(valor);
			cad.setQuantidade(1);

			this.adicionarDesconto(cad);
		}

	}

	public void inserirAcrescimoHoraNoturna(String nome, String descricao, double valor) {

		// verifica se ja existe este desconto na tabela
		boolean tem_na_tabela = false;
		for (CadastroFuncionarioCalculo cad : modelAcrescimos.getLista()) {
			if (cad.getNome().equalsIgnoreCase(nome)) {
				tem_na_tabela = true;
				break;
			}
		}

		if (!tem_na_tabela) {
			CadastroFuncionarioCalculo cad = new CadastroFuncionarioCalculo();

			cad.setTipo_calculo(1);
			int referencia_calculo, referencia_valor;
			double total;
			cad.setDescricao(descricao);
			cad.setNome(nome);
			cad.setReferencia_calculo(0);
			cad.setReferencia_valor(1);
			cad.setValor(valor);
			cad.setTotal(valor);
			cad.setQuantidade(1);

			this.adicionarAcrescimo(cad);
		}

	}

	public long calculateWorkingHours(String workStartString, String workEndString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

		LocalDateTime workStart = LocalDateTime.parse(workStartString, formatter);
		LocalDateTime workEnd = LocalDateTime.parse(workEndString, formatter);

		if (workEnd.isBefore(workStart)) {
			throw new IllegalArgumentException("Work end must not be before work start");
		}

		LocalDate workStartDate = workStart.toLocalDate();
		LocalDate workEndDate = workEnd.toLocalDate();

		Duration workedDaytime = Duration.ZERO;
		// first calculate work at nighttime before the start date, that is, work before
		// 06:00
		Duration workedNighttime = calculateNightTime(workStartDate.minusDays(1), workStart, workEnd);

		for (LocalDate d = workStartDate; !d.isAfter(workEndDate); d = d.plusDays(1)) {
			workedDaytime = workedDaytime.plus(calculateDayTime(d, workStart, workEnd));
			workedNighttime = workedNighttime.plus(calculateNightTime(d, workStart, workEnd));
		}

		// double dayHours = workedDaytime.toMinutes() / (double)
		// TimeUnit.HOURS.toMinutes(1);
		long nightHours = workedNighttime.toMinutes();

		return nightHours;

	}

	private Duration calculateDayTime(LocalDate d, LocalDateTime workStart, LocalDateTime workEnd) {
		LocalDateTime dayStartToday = d.atTime(dayStart);
		LocalDateTime dayEndToday = d.atTime(dayEnd);
		if (workStart.isAfter(dayEndToday) || workEnd.isBefore(dayStartToday)) {
			return Duration.ZERO;
		}

		// restrict calculation to daytime on d
		if (workStart.isBefore(dayStartToday)) {
			workStart = dayStartToday;
		}
		if (workEnd.isAfter(dayEndToday)) {
			workEnd = dayEndToday;
		}

		return Duration.between(workStart, workEnd);
	}

	LocalTime dayStart = LocalTime.of(5, 0);
	LocalTime dayEnd = LocalTime.of(22, 0);

	private Duration calculateNightTime(LocalDate d, LocalDateTime workStart, LocalDateTime workEnd) {
		assert !workEnd.isBefore(workStart);

		LocalDateTime nightStart = d.atTime(dayEnd);
		LocalDateTime nightEnd = d.plusDays(1).atTime(dayStart);

		if (workEnd.isBefore(nightStart) || workStart.isAfter(nightEnd)) {
			return Duration.ZERO;
		}

		// restrict calculation to the night after d
		if (workStart.isBefore(nightStart)) {
			workStart = nightStart;
		}
		if (workEnd.isAfter(nightEnd)) {
			workEnd = nightEnd;
		}

		return Duration.between(workStart, workEnd);
	}

	public boolean salvarParcela(Parcela parcela, Lancamento lancamento_pai) {
		GerenciarBancoParcelas gerenciar = new GerenciarBancoParcelas();
		int result = gerenciar.inserirParcela(parcela);
		if (result > 0) {
			// verificar se a pasta da parcela existe
			ManipularTxt manipular = new ManipularTxt();
			File pasta_de_parcelas = new File(
					servidor_unidade + lancamento_pai.getDiretorio_lancamento() + "\\parcela_" + result);
			if (!(pasta_de_parcelas.exists())) {
				manipular.criarDiretorio(pasta_de_parcelas.getAbsolutePath());
			}

			// copiar o arquivo para a pasta da parcela
			// antes verificar se o arquivo nao e nulo
			if (parcela.getCaminho_arquivo() != null && parcela.getCaminho_arquivo().length() > 20) {
				boolean copiar_arquivo = false;
				String extensaoDoArquivo = FilenameUtils.getExtension(parcela.getCaminho_arquivo());

				String arquivo_doc_parcela_final = pasta_de_parcelas + "\\doc_parcela_" + result + "."
						+ extensaoDoArquivo;
				try {
					copiar_arquivo = manipular.copiarNFe(parcela.getCaminho_arquivo(), arquivo_doc_parcela_final);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (copiar_arquivo) {
					// atualiza parcela no banco de dados
					boolean atualizar = gerenciar
							.atualizarArquivoDaParcela("doc_parcela_" + result + "." + extensaoDoArquivo, result);
					if (!atualizar) {
						JOptionPane.showMessageDialog(isto,
								"Arquivo Copiado, Mas não atualizado no Banco de dados\nConsulte o administrador");
					}
				} else {
					JOptionPane.showMessageDialog(isto, "Arquivo não copiado, consulte o administrador");

				}
			}
			// JOptionPane.showMessageDialog(isto, "Parcela Inserida");
			atualizarValorLancamento(lancamento_pai);
			return true;
		} else {
			JOptionPane.showMessageDialog(isto, "Erro ao Salvar\nConsulte o Administrador");
			return false;
		}

	}

	public Parcela getDadosParcela(int id_lancamento_pai, double Dvalor) {
		Parcela parcela = new Parcela();

		String  data_vencimento, caminho_arquivo;

		
		data_vencimento = new GetData().getData();

		parcela.setFluxo_caixa(1);

		BigDecimal valor = new BigDecimal(Dvalor).setScale(2, RoundingMode.HALF_EVEN);
		parcela.setValor(valor);

		int mes = cBMes.getSelectedIndex();
		String s_mes = cBMes.getItemAt(mes).toString();
		String texto = "Salario Restante Referente ao Mês de " + s_mes + " de " + (new GetData().getAnoAtual());
		
		parcela.setDescricao(texto);
		parcela.setStatus(1);
		parcela.setId_lancamento_pai(id_lancamento_pai);
		parcela.setIdentificador(texto);
		parcela.setObservacao(texto);

		parcela.setData_vencimento(data_vencimento);
		return parcela;

	}

	public void atualizarValorLancamento(Lancamento lancamento_pai) {

		GerenciarBancoFinanceiroPagamento gerenciar_pag = new GerenciarBancoFinanceiroPagamento();
		BigDecimal valor_total_pagamentos = BigDecimal.ZERO;

		for (FinanceiroPagamento pagamento : gerenciar_pag
				.getFinanceiroPagamentosPorLancamento(lancamento_pai.getId_lancamento())) {

			valor_total_pagamentos = valor_total_pagamentos.add(pagamento.getValor());

		}

		BigDecimal valor_pagamentos_retroativos = valor_total_pagamentos;
		GerenciarBancoParcelas gerenciar = new GerenciarBancoParcelas();

		BigDecimal valor_total_parcelas = BigDecimal.ZERO;

		for (Parcela parcela : gerenciar.getParcelasPorLancamento(lancamento_pai.getId_lancamento())) {
			BigDecimal valor_parcela = parcela.getValor();
			if (lancamento_pai.getTipo_lancamento() == 0) {
				// é uma despesa
				if (valor_pagamentos_retroativos.compareTo(valor_parcela) >= 0) {
					gerenciar.atualizarStatusParcela(1, parcela.getId_parcela());

				} else if (valor_pagamentos_retroativos.compareTo(valor_parcela) < 0) {
					gerenciar.atualizarStatusParcela(0, parcela.getId_parcela());

				}
			} else if (lancamento_pai.getTipo_lancamento() == 1) {
				// é uma receita
				if (valor_pagamentos_retroativos.compareTo(valor_parcela) >= 0) {
					gerenciar.atualizarStatusParcela(1, parcela.getId_parcela());

				} else if (valor_pagamentos_retroativos.compareTo(valor_parcela) < 0) {
					gerenciar.atualizarStatusParcela(0, parcela.getId_parcela());

				}
			}

			valor_pagamentos_retroativos = valor_pagamentos_retroativos.subtract(valor_parcela);
			valor_total_parcelas = valor_total_parcelas.add(valor_parcela);

		}
		new GerenciarBancoLancamento().atualizarValorLancamento(valor_total_parcelas.toString(),
				lancamento_pai.getId_lancamento());

		if (valor_total_pagamentos.compareTo(valor_total_parcelas) >= 0) {
			// pagamento concluido
			// atualizar status do lancmento
			if (lancamento_pai.getTipo_lancamento() == 0) // é uma despesa
				new GerenciarBancoLancamento().atualizarStatusLancamento(1, lancamento_pai.getId_lancamento());
			else if (lancamento_pai.getTipo_lancamento() == 1)// é uma receita
				new GerenciarBancoLancamento().atualizarStatusLancamento(3, lancamento_pai.getId_lancamento());

		} else if (valor_total_pagamentos.compareTo(valor_total_parcelas) < 0) {
			// pagamento incompleto
			if (lancamento_pai.getTipo_lancamento() == 0) // é uma despesa
				new GerenciarBancoLancamento().atualizarStatusLancamento(0, lancamento_pai.getId_lancamento());
			else if (lancamento_pai.getTipo_lancamento() == 1)// é uma receita
				new GerenciarBancoLancamento().atualizarStatusLancamento(2, lancamento_pai.getId_lancamento());

		}

	}

	public Lancamento getSalvarLancamento(double valor) {

		Lancamento lancamento = new Lancamento();
		int status, prioridade, gerar_parcelas, intervalo;
		String identificacao, caminho_arquivo, data_lancamento, data_vencimento, data_pagamento;

		lancamento.setId_centro_custo(5);

		lancamento.setId_cliente_fornecedor(id_conta_associada_global);

		lancamento.setId_conta(21);
		lancamento.setValor(new BigDecimal(valor, MathContext.DECIMAL64));

		// tela concluir
		status = 1;
	

		int mes = cBMes.getSelectedIndex();
		String s_mes = cBMes.getItemAt(mes).toString();
		String texto = "Salario Referente ao Mês de " + s_mes + " de " + entAno.getText();
		
		lancamento.setDescricao(texto);
		lancamento.setObservacao(texto);

		lancamento.setTipo_lancamento(0);

		lancamento.setStatus(0);

		lancamento.setPrioridade(1);

		lancamento.setIntervalo(0);

		lancamento.setNumero_parcelas(1);

		lancamento.setGerar_parcelas(1);

		lancamento.setIdentificacao("");

		String data = new GetData().getData();
		data_vencimento = data;
		data_lancamento = data;

		lancamento.setData_vencimento(data_vencimento);

		lancamento.setData_lancamento(data_lancamento);
		return lancamento;

	}

	
	public int salvarLancamento(Lancamento lancamento) {
		GerenciarBancoLancamento gerenciar = new GerenciarBancoLancamento();
			int result = gerenciar.inserirLancamento(lancamento);
			if (result > 0) {

				// adicionar parcelas
				if (lancamento.getGerar_parcelas() == 1) {
					int numero_parcelas = lancamento.getNumero_parcelas();
					BigDecimal valor_por_parcela = lancamento.getValor()
							.divide(new BigDecimal((double) numero_parcelas));
					LocalDate primeiro_vencimento = LocalDate.parse(lancamento.getData_vencimento(),
							DateTimeFormatter.ofPattern("dd/MM/yyyy"));
					int intervalo = lancamento.getIntervalo();
					LocalDate datas = primeiro_vencimento;

					GerenciarBancoParcelas gerenciar_parcelas = new GerenciarBancoParcelas();

					for (int i = 1; i <= lancamento.getNumero_parcelas(); i++) {
						Parcela parcela = new Parcela();
						parcela.setValor(valor_por_parcela);
						parcela.setDescricao("Parcela Número " + i + " gerada automaticamente");
						parcela.setIdentificador(i + "");
						parcela.setObservacao(
								"Um número aleatorio foi criado para o identificador desta parcela, troque o se necessário: "
										+ i);
						String data_desta_parcela_formatada = datas
								.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
						parcela.setData_vencimento(data_desta_parcela_formatada);
						parcela.setFluxo_caixa(1);
						parcela.setId_lancamento_pai(result);

						gerenciar_parcelas.inserirParcela(parcela);

						datas = datas.plusDays(intervalo);

					}
				}

				// criar pasta, mover arquivo
				ManipularTxt manipular = new ManipularTxt();
				String caminho_diretorio = "\\E-Contract\\arquivos\\financas\\lancamentos\\lancamento_"
						+ result;
				String caminho_diretorio_bd = "\\\\E-Contract\\\\arquivos\\\\financas\\\\lancamentos\\\\lancamento_"
						+ result;
				boolean criar_diretorio = manipular.criarDiretorio(servidor_unidade + caminho_diretorio);
				if (criar_diretorio) {

					boolean atualizar_diretorio = gerenciar.atualizarDiretorioLancamento(caminho_diretorio_bd,
							result);
					if (atualizar_diretorio) {

						// copiar o arquivo selecionado

						if (lancamento.getCaminho_arquivo() != null
								&& lancamento.getCaminho_arquivo().length() > 20) {
							boolean copiar_arquivo = false;
							String extensaoDoArquivo = FilenameUtils
									.getExtension(lancamento.getCaminho_arquivo());

							String arquivo_doc_documento_final = servidor_unidade + caminho_diretorio
									+ "\\doc_lancamento_" + result + "." + extensaoDoArquivo;
							try {
								copiar_arquivo = manipular.copiarNFe(lancamento.getCaminho_arquivo(),
										arquivo_doc_documento_final);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							if (copiar_arquivo) {
								// atualiza parcela no banco de dados
								boolean atualizar = gerenciar.atualizarCaminhoLancamento(
										"doc_lancamento_" + result + "." + extensaoDoArquivo, result);
								if (!atualizar)
									JOptionPane.showMessageDialog(isto,
											"Arquivo Copiado, Mas não atualizado no Banco de dados\nConsulte o administrador");

							} else {
								JOptionPane.showMessageDialog(isto,
										"Arquivo não copiado, consulte o administrador");

							}
						}

						JOptionPane.showMessageDialog(isto, "Cadastro Concluído");
						
							((TelaGerenciarFuncionario) janela_pai_global).pesquisar_contas_associadas();
					
					}

				} else {
					JOptionPane.showMessageDialog(isto,
							"Cadastro Conluido, mas houve erro ao criar o diretorio\nConsulte o Administrador");

				}

				return result;
			} else {
				JOptionPane.showMessageDialog(isto, "Erro ao Salvar\nConsulte o Administrador");
				return 0;

			}
		
	}
	
	public boolean procurarIsencao(ArrayList<CadastroFuncionarioEvento> eventos) {
		int count_isencao = 0;
		for (CadastroFuncionarioEvento evt : eventos) {

			  if (evt.getTipo_evento() == 4) {
				// isencao de ponto
				LocalDate hoje = LocalDate.parse(new GetData().getData(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

				LocalDate dataInicialFerias = LocalDate.parse(evt.getData_ferias_ida(),
						DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				LocalDate dataFinalFerias = LocalDate.parse(evt.getData_ferias_volta(),
						DateTimeFormatter.ofPattern("dd/MM/yyyy"));

				if (hoje.isEqual(dataInicialFerias)) {
					count_isencao++;
					

				} else if (hoje.isEqual(dataFinalFerias)) {
					count_isencao++;
					
				} else if (hoje.isAfter(dataInicialFerias) && hoje.isBefore(dataFinalFerias)) {
					count_isencao++;
					
				}
			} 

		}
		
		if(count_isencao > 0) {
			return true;
		}else
			return false;
	}
	
	
	public boolean procurarFerias(ArrayList<CadastroFuncionarioEvento> eventos, int mes, int ano) {
		int count_ferias = 0;
		for (CadastroFuncionarioEvento evt : eventos) {

			  if (evt.getTipo_evento() == 3) {

				LocalDate dataInicialFerias = LocalDate.parse(evt.getData_ferias_ida(),
						DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				LocalDate dataFinalFerias = LocalDate.parse(evt.getData_ferias_volta(),
						DateTimeFormatter.ofPattern("dd/MM/yyyy"));

				
				int mes_inicio_ferias = dataInicialFerias.getMonthValue();
				int ano_inicio_ferias = dataInicialFerias.getYear();
				int mes_final_ferias = dataFinalFerias.getMonthValue();
				int ano_final_ferias = dataFinalFerias.getYear();
				
				if (mes_inicio_ferias == mes && ano_inicio_ferias == ano || mes_final_ferias == mes && ano_final_ferias == ano) {
					count_ferias++;
					

				} 
			} 

		}
		
		if(count_ferias > 0) {
			return true;
		}else
			return false;
	}
	
	
	public boolean procurarLicencas(ArrayList<CadastroFuncionarioEvento> eventos, int mes, int ano) {
		int count_licencas = 0;
		for (CadastroFuncionarioEvento evt : eventos) {

			  if (evt.getTipo_evento() == 5) {

				LocalDate dataInicialFerias = LocalDate.parse(evt.getData_ferias_ida(),
						DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				LocalDate dataFinalFerias = LocalDate.parse(evt.getData_ferias_volta(),
						DateTimeFormatter.ofPattern("dd/MM/yyyy"));

				
				int mes_inicio_ferias = dataInicialFerias.getMonthValue();
				int ano_inicio_ferias = dataInicialFerias.getYear();
				int mes_final_ferias = dataFinalFerias.getMonthValue();
				int ano_final_ferias = dataFinalFerias.getYear();
				
				if (mes_inicio_ferias == mes && ano_inicio_ferias == ano || mes_final_ferias == mes && ano_final_ferias == ano) {
					count_licencas++;
					

				} 
			} 

		}
		
		if(count_licencas > 0) {
			return true;
		}else
			return false;
	}
	
	
}
