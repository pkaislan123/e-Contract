
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
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;

import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroFuncionario;
import main.java.cadastros.CadastroFuncionarioAdmissao;
import main.java.cadastros.CadastroFuncionarioEvento;
import main.java.cadastros.CadastroFuncionarioRotinaTrabalho;
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
import main.java.cadastros.Lancamento;
import main.java.cadastros.FinanceiroConta;
import main.java.cadastros.FinanceiroConta;
import main.java.conexaoBanco.GerenciarBancoCentroCustos;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoEventoGlobal;
import main.java.conexaoBanco.GerenciarBancoFinanceiroConta;
import main.java.conexaoBanco.GerenciarBancoFinanceiroGrupoContas;
import main.java.conexaoBanco.GerenciarBancoFuncionarios;
import main.java.conexaoBanco.GerenciarBancoFuncionariosContratoTrabalho;
import main.java.conexaoBanco.GerenciarBancoFuncionariosEventos;
import main.java.conexaoBanco.GerenciarBancoRegistroPonto;
import main.java.conexaoBanco.GerenciarBancoRotina;
import main.java.gui.TelaRecursosHumanos.CellRenderRPDiario;
import main.java.outros.GetData;
import main.java.outros.JTextFieldPersonalizado;
import main.java.views_personalizadas.TelaEscolhaRelatorioLancamentos;
import main.java.views_personalizadas.TelaEscolhaRelatorioRegistroPonto;
import main.java.conexaoBanco.GerenciarBancoFinanceiroConta;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.NumberFormat;
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

public class TelaFuncionarioDemonstrativoDePonto extends JFrame {

	private JLabel lblTotalHorasExtras, lblTotalHorasAtrazo, lblTotalHorasNormais;

	private final JPanel painelPrinciapl = new JPanel();
	private TelaFuncionarioDemonstrativoDePonto isto;
	private JTable tabela_demonstrativo;
	private JDialog telaPai;
	private JTextField entAno;
	private RegistroPontoMensalCompletoTableModel modeloRegistroPonto = new RegistroPontoMensalCompletoTableModel();
	private JComboBox cbColaborador;
	private ComboBoxPersonalizadoFuncionario modelFuncionarios = new ComboBoxPersonalizadoFuncionario();
	private ComboBoxRenderPersonalizadoFuncionario cbFuncionarioPersonalizados;
	private JLabel lblCargo, lblFuncao, lblDataAdmissao, lblTipoContrato, lblStatusContrato;
	JLabel lblJornadaSegunda, lblJornadaTerca, lblJornadaQuarta, lblJornadaQuinta, lblJornadaSexta, lblJornadaSabado,
			lblJornadaDomingo;
	private JComboBox cBMes;
	private JLabel lblTotalHorasMensal, lblTotalHoras100, lblTotalHoras50, lblTotalHoras60;


	public TelaFuncionarioDemonstrativoDePonto(Window janela_pai) {
		setTitle("Demonstrativo de Registro de Ponto");

		isto = this;

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

		painelPrinciapl.setBackground(Color.WHITE);
		this.setContentPane(painelPrinciapl);
		painelPrinciapl.setLayout(new MigLayout("", "[grow]", "[][][][][grow][grow][grow][grow]"));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 102, 153));
		painelPrinciapl.add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[100px][128px][][][][]", "[]"));

		JLabel lblNewLabel = new JLabel("Demonstrativo de Ponto");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 40));
		panel.add(lblNewLabel, "cell 0 0,growx,aligny bottom");

		JPanel panel_1 = new JPanel();
		painelPrinciapl.add(panel_1, "cell 0 1,grow");
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBackground(Color.WHITE);
		panel_1.setLayout(
				new MigLayout("", "[106px][][38px][][38px][][][][][][][][][][][][][][][][][][][][]", "[33px]"));

		JLabel lblNewLabel_1 = new JLabel("Colaborador:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_1.add(lblNewLabel_1, "cell 0 0,alignx left,aligny center");

		cbColaborador = new JComboBox();
		cbFuncionarioPersonalizados = new ComboBoxRenderPersonalizadoFuncionario();
		cbColaborador.setModel(modelFuncionarios);
		cbColaborador.setRenderer(cbFuncionarioPersonalizados);

		panel_1.add(cbColaborador, "cell 1 0,growx,aligny center");

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
		painelPrinciapl.add(panel_3, "cell 0 2,grow");
		panel_3.setLayout(new MigLayout("",
				"[48px][1px][41px][56px][1px][41px][113px][1px][41px][99px][1px][41px][112px][1px]", "[21px]"));

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

		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelPrinciapl.add(panel_4, "cell 0 3");
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
		painelPrinciapl.add(panel_2, "cell 0 4 1 3,grow");
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
		painelPrinciapl.add(painelCalculos, "flowx,cell 0 7,alignx left,growy");
		painelCalculos.setLayout(new MigLayout("", "[152px][42px]", "[21px][21px][21px][21px]"));
		
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
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(new Color(204, 255, 255));
		painelPrinciapl.add(panel_5, "cell 0 7,grow");
		panel_5.setLayout(new MigLayout("", "[][][][][][][][][][][][][][][][][][][][][][][][][][][]", "[][][][]"));
		
		JLabel lblNewLabel_1_2_1_2_1 = new JLabel("Total Horas Extras:");
		lblNewLabel_1_2_1_2_1.setForeground(new Color(204, 255, 255));
		lblNewLabel_1_2_1_2_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_5.add(lblNewLabel_1_2_1_2_1, "cell 0 0");
		
		JLabel lblNewLabel_1_2_1_2 = new JLabel("Horas Extras");
		lblNewLabel_1_2_1_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_5.add(lblNewLabel_1_2_1_2, "cell 1 0 2 1,alignx center");
		
		JLabel lblNewLabel_1_2_1_3 = new JLabel("Total Horas 50%:");
		lblNewLabel_1_2_1_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_5.add(lblNewLabel_1_2_1_3, "cell 1 1,alignx right");
		
		 lblTotalHoras50 = new JLabel("00:00");
		lblTotalHoras50.setForeground(new Color(0, 0, 153));
		lblTotalHoras50.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_5.add(lblTotalHoras50, "cell 2 1");
		
		JButton btnNewButton = new JButton("Exportar");
		btnNewButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				ArrayList<RegistroPontoMensalCompleto> registros_selecionados = new ArrayList<>();
				int linhas_selecionadas[] = tabela_demonstrativo.getSelectedRows();// pega o indice da linha na tabela

				for (int i = 0; i < linhas_selecionadas.length; i++) {

					int indice = linhas_selecionadas[i];//

					RegistroPontoMensalCompleto rp = modeloRegistroPonto.getValue(indice);
					registros_selecionados.add(rp);
				}

				TelaEscolhaRelatorioRegistroPonto escolha_opcoes = new TelaEscolhaRelatorioRegistroPonto(
						registros_selecionados, isto);
				escolha_opcoes.setVisible(true);
			}
		});
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(new Color(0, 51, 0));
		btnNewButton.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_5.add(btnNewButton, "cell 26 1");
		
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
		pesquisar();
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

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

	public void setFuncionario(CadastroFuncionario func, int mes, String ano) {
		entAno.setText(ano);
		cBMes.setSelectedIndex(mes);
		modelFuncionarios.setSelectedItem(func);
		pesquisarDemostrativo();
	}

	public void pesquisar() {

		modelFuncionarios.resetar();
		GerenciarBancoFuncionarios gerenciar = new GerenciarBancoFuncionarios();

		for (CadastroFuncionario funcionario : gerenciar.getfuncionarios()) {

			modelFuncionarios.addCC(funcionario);

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

		private final String colunas[] = { "DATA", "DIA DA SEMANA", "HORA ENTRADA 1", "HORA SAIDA 1", "HORA ENTRADA 2",
				"HORA SAIDA 2", "HORA ENTRADA 3", "HORA SAIDA 3", "ROTINA DIARIA", "HORAS TRABALHADAS", "HORAS EXTRAS",
				"HORAS ATRAZO" };
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
				if(rp.getRp().getData() != null && !rp.getRp().getData().equals("") && !rp.getRp().getData().equalsIgnoreCase("TOTAIS"))
				return rp.getRp().getData();
				else {
					return "TOTAIS";
				}

			}
			case dia_semana: {

				if(rp.getRp().getData() != null && !rp.getRp().getData().equals("") && !rp.getRp().getData().equalsIgnoreCase("TOTAIS"))
					{
					try {
						DateTimeFormatter formatter = new DateTimeFormatterBuilder().toFormatter(new Locale("pt", "BR"));

						LocalDate data = LocalDate.parse(rp.getRp().getData(), formatter.ofPattern("dd/MM/yyyy"));
						DayOfWeek dia_s = data.getDayOfWeek();
						return dia_s.getDisplayName(TextStyle.FULL, new Locale("pt", "BR")).toUpperCase();
					} catch (Exception e) {
						return "";
					}
					}
					else {
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

				if (!rp.getHora_diaria().equals("00:00"))
					return rp.getHora_diaria();
				else
					return "DESCANSO SEMANAL";

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

		pesquisarContratoAtivo();

		int mes = cBMes.getSelectedIndex() + 1;
		int ano = Integer.parseInt(entAno.getText());

		Calendar datas = new GregorianCalendar();
		datas.set(Calendar.MONTH, mes - 1);// 2
		int ultimo_dia = datas.getActualMaximum(Calendar.DAY_OF_MONTH);
		CadastroFuncionario func = (CadastroFuncionario) modelFuncionarios.getSelectedItem();
		int dia = 1;

		
		long total_duracao = 0, total_horas_normais_trabalhadas = 0, total_horas_extras = 0, total_horas_atrazo = 0, total_horas_100 = 0, total_horas_50 = 0, total_horas_60 = 0;
		
		long duracao_rotina = 0;

		GerenciarBancoRegistroPonto gerenciar = new GerenciarBancoRegistroPonto();

		// buscar rotinad e trabalho

		modeloRegistroPonto.onRemoveAll();

		ArrayList<RegistroPontoDiario> listRpDiario = gerenciar
				.getDemonstrativoFuncionarioMesCompleto(func.getId_funcionario(), ultimo_dia, mes, ano);

		GerenciarBancoRotina gerenciar_rotina = new GerenciarBancoRotina();
		ArrayList<CadastroFuncionarioRotinaTrabalho> rotinas = gerenciar_rotina.getRotinas(func.getId_funcionario());
		DateTimeFormatter formatter = new DateTimeFormatterBuilder().toFormatter(new Locale("pt", "BR"));
		LocalDate dia_de_hoje  = LocalDate.now();

		for (RegistroPontoDiario rp : listRpDiario) {
			// verifique a rotina desse dia
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
			
			int dia_da_semana = 1;

			try {

				LocalDate data = LocalDate.parse(rp.getData(), formatter.ofPattern("dd/MM/yyyy"));
				dia_da_semana = data.getDayOfWeek().getValue();
			} catch (Exception e) {
				return;
			}

			boolean tem_rotina = false;

			rotina_deste_dia = gerenciar_rotina.getRotinaDiaSemana(func.getId_funcionario(), dia_da_semana);

			if (rotina_deste_dia != null)
				tem_rotina = true;
			/*
			 * for (CadastroFuncionarioRotinaTrabalho rtb : rotinas) { if
			 * (rtb.getDia_da_semana() == dia_da_semana) { rotina_deste_dia = rtb;
			 * tem_rotina = true; break; } }
			 */

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
				rp.setSaida1("00:00");
			}

			if (rp.getEntrada2() == null || rp.getEntrada2().equals("")) {
				rp.setEntrada2("00:00");
			}

			if (rp.getSaida2() == null || rp.getSaida2().equals("")) {
				rp.setSaida2("00:00");
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
			LocalDateTime entrada2 = LocalDateTime.parse(data_rp + " " + rp.getEntrada2(),
					DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
			LocalDateTime saida2 = LocalDateTime.parse(data_rp + " " + rp.getSaida2(),
					DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
			LocalDateTime entrada3 = LocalDateTime.parse(data_rp + " " + rp.getEntrada3(),
					DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
			LocalDateTime saida3 = LocalDateTime.parse(data_rp + " " + rp.getSaida3(),
					DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

			long periodAsMinutes1 = ChronoUnit.MINUTES.between(entrada1, saida1);
			long periodAsMinutes2 = ChronoUnit.MINUTES.between(entrada2, saida2);
			long periodAsMinutes3 = ChronoUnit.MINUTES.between(entrada3, saida3);

			long duracao_trabalho = periodAsMinutes1 + periodAsMinutes2 + periodAsMinutes3;
			total_horas_normais_trabalhadas  += duracao_trabalho;
			
			
			
			String tempo_total = LocalTime.MIN.plus(Duration.ofMinutes(duracao_trabalho)).toString();
			rpCompleto.setHora_trabalhada(tempo_total);
			/*
			 * 
			 * //calculo de hora extra if(duracao_rotina <= 0) { //nao houve rotina nesse
			 * dia if(duracao_trabalho > 0) { //trabalho esse dia, 100% hora extra String
			 * tempo_extra = LocalTime.MIN.plus( Duration.ofMinutes( duracao_trabalho )
			 * ).toString(); rpCompleto.setHora_extra(tempo_extra);
			 * rpCompleto.setHora_atrazo("00:00");
			 * 
			 * }else { //folgou rpCompleto.setHora_extra("00:00");
			 * rpCompleto.setHora_atrazo("00:00");
			 * 
			 * 
			 * }
			 * 
			 * }else { //houve rotina nesse dia
			 * 
			 * 
			 * 
			 * if(duracao_trabalho >= duracao_rotina) { long hora_extra = duracao_trabalho -
			 * duracao_rotina;
			 * 
			 * String tempo_extra = LocalTime.MIN.plus( Duration.ofMinutes( hora_extra )
			 * ).toString(); rpCompleto.setHora_extra(tempo_extra);
			 * rpCompleto.setHora_atrazo("00:00");
			 * 
			 * } else { long hora_atrazo = duracao_rotina - duracao_trabalho; String
			 * tempo_atrazo= LocalTime.MIN.plus( Duration.ofMinutes( hora_atrazo )
			 * ).toString(); rpCompleto.setHora_atrazo(tempo_atrazo);
			 * rpCompleto.setHora_extra("00:00");
			 * 
			 * } }
			 */

			if (!tem_folga && !tem_ferias && !tem_isencao && !tem_licenca && !tem_feriado && !tem_descanso
					&& duracao_trabalho <= 0 && duracao_rotina > 0) {
				
				if(data_do_registro.isBefore(dia_de_hoje)) {
					rp.setEntrada1("FALTA");
					rp.setSaida1("FALTA");
					rp.setEntrada2("FALTA");
					rp.setSaida2("FALTA");

					long hora_atrazo = duracao_rotina - duracao_trabalho;
					total_horas_atrazo  += hora_atrazo;
					
					String tempo_atrazo = LocalTime.MIN.plus(Duration.ofMinutes(hora_atrazo)).toString();
					rpCompleto.setHora_atrazo(tempo_atrazo);
					rpCompleto.setHora_extra("00:00");
				}else {
					rp.setEntrada1("00:00");
					rp.setSaida1("00:00");
					rp.setEntrada2("00:00");
					rp.setSaida2("00:00");

					
					rpCompleto.setHora_atrazo("00:00");
					rpCompleto.setHora_extra("00:00");
				}
				

			} else if (tem_folga && !tem_ferias && !tem_feriado) {
				// tem folga
				rp.setEntrada1("FOLGA");
				rp.setSaida1("FOLGA");
				rp.setEntrada2("FOLGA");
				rp.setSaida2("FOLGA");

				long hora_atrazo = duracao_rotina - duracao_trabalho;
				total_horas_atrazo  += hora_atrazo;

				String tempo_atrazo = LocalTime.MIN.plus(Duration.ofMinutes(hora_atrazo)).toString();
				rpCompleto.setHora_atrazo(tempo_atrazo);
				rpCompleto.setHora_extra("00:00");

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

				} else {
					// folgou
					rpCompleto.setHora_extra("00:00");
					rpCompleto.setHora_atrazo("00:00");

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

						//verifica se e sabado ou domindo
						if(rotina_deste_dia.getDia_da_semana() == 6) {
							//sabado
							total_horas_60 += duracao_trabalho;
						}else if(rotina_deste_dia.getDia_da_semana() == 7) {
							total_horas_100 += duracao_trabalho;

						}else {
							total_horas_50 += duracao_trabalho;
						}
						
						// trabalho esse dia hora extra
						String tempo_extra = LocalTime.MIN.plus(Duration.ofMinutes(duracao_trabalho)).toString();
						rpCompleto.setHora_extra(tempo_extra);
						rpCompleto.setHora_atrazo("00:00");

					} else {
						// folgou
						rpCompleto.setHora_extra("00:00");
						rpCompleto.setHora_atrazo("00:00");

					}
				} else {
					// nao e o dia de hora extra

					if (duracao_trabalho >= duracao_rotina) {
						long hora_extra = duracao_trabalho - duracao_rotina;
						total_horas_extras += hora_extra;

						
						if(rotina_deste_dia.getDia_da_semana() == 6) {
							//sabado
							total_horas_60 += hora_extra;
						}else if(rotina_deste_dia.getDia_da_semana() == 7) {
							//domingoi
							total_horas_100 += hora_extra;

						}else {
							//meio de semana
							total_horas_50 += hora_extra;
						}
						
						
						String tempo_extra = LocalTime.MIN.plus(Duration.ofMinutes(hora_extra)).toString();
						rpCompleto.setHora_extra(tempo_extra);
						rpCompleto.setHora_atrazo("00:00");

					} else {
						long hora_atrazo = duracao_rotina - duracao_trabalho;
						total_horas_atrazo += hora_atrazo;

						String tempo_atrazo = LocalTime.MIN.plus(Duration.ofMinutes(hora_atrazo)).toString();
						rpCompleto.setHora_atrazo(tempo_atrazo);
						rpCompleto.setHora_extra("00:00");

					}

				}

			} else {
				// dia normal de trabalho

				// calculo de hora extra
				if (duracao_rotina <= 0) {
					// nao houve rotina nesse dia
					if (duracao_trabalho > 0) {
						
						if(rotina_deste_dia.getDia_da_semana() == 6) {
							//sabado
							total_horas_60 += duracao_trabalho;
						}else if(rotina_deste_dia.getDia_da_semana() == 7) {
							//domingoi
							total_horas_100 += duracao_trabalho;

						}else {
							//meio de semana
							total_horas_50 += duracao_trabalho;
						}
						
						// trabalho esse dia, 100% hora extra
						total_horas_extras += duracao_trabalho;
						String tempo_extra = LocalTime.MIN.plus(Duration.ofMinutes(duracao_trabalho)).toString();
						rpCompleto.setHora_extra(tempo_extra);
						rpCompleto.setHora_atrazo("00:00");

					} else {
						// folgou
						rpCompleto.setHora_extra("00:00");
						rpCompleto.setHora_atrazo("00:00");

					}

				} else {
					// houve rotina nesse dia

					if (duracao_trabalho >= duracao_rotina) {
						long hora_extra = duracao_trabalho - duracao_rotina;
						total_horas_extras += hora_extra;
						
						if(rotina_deste_dia.getDia_da_semana() == 6) {
							//sabado
							total_horas_60 += hora_extra;
						}else if(rotina_deste_dia.getDia_da_semana() == 7) {
							//domingoi
							total_horas_100 += hora_extra;

						}else {
							//meio de semana
							total_horas_50 += hora_extra;
						}
						
						
						String tempo_extra = LocalTime.MIN.plus(Duration.ofMinutes(hora_extra)).toString();
						rpCompleto.setHora_extra(tempo_extra);
						rpCompleto.setHora_atrazo("00:00");

					} else {
						long hora_atrazo = duracao_rotina - duracao_trabalho;
						total_horas_atrazo += hora_atrazo;
						String tempo_atrazo = LocalTime.MIN.plus(Duration.ofMinutes(hora_atrazo)).toString();
						rpCompleto.setHora_atrazo(tempo_atrazo);
						rpCompleto.setHora_extra("00:00");

					}
				}

			}
			rpCompleto.setRp(rp);

			modeloRegistroPonto.onAdd(rpCompleto);
			dia++;
		
			
			
		}
		
		String s_total_h_ex =  formatHora(total_horas_extras);
		String s_total_h_normais =  formatHora(total_horas_normais_trabalhadas);
		String s_total_h_atraz =  formatHora(total_horas_atrazo);
		String s_total_h_mensal =  formatHora(total_duracao);

		
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

		
		//informar totais
		
		 lblTotalHorasMensal.setText(  s_total_h_mensal);
		lblTotalHorasExtras.setText( s_total_h_ex );
		lblTotalHorasAtrazo.setText(s_total_h_atraz );
		lblTotalHorasNormais.setText(s_total_h_normais);
		
		//JOptionPane.showMessageDialog(null, "Total horas normais: " + total_horas_normais_trabalhadas/60 +
			//	"\nTotal horas extas: " + total_horas_extras/60 + "\nTotal atrazo: " + total_horas_atrazo/60);
		
		
		
		
		 lblTotalHoras100.setText(  formatHora(total_horas_100) );
		 lblTotalHoras50.setText(  formatHora(total_horas_50) );
		 lblTotalHoras60.setText(  formatHora(total_horas_60) );

	}
	
	
	public String formatHora(long minutos) {
		Duration duracao_total_horas_normais = Duration.ofMinutes(minutos);
		return  String.format("%d:%02d",
				duracao_total_horas_normais.toHours(),
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
			else if (column >= 8 && column <= 11) {
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
				else
					this.setForeground(Color.black);

			} else {
				this.setBackground(Color.white);
				this.setForeground(Color.black);

			}

			if(row == table.getRowCount() - 1) {
				//esta na ultima lnha
				for(int i = 0; i < table.getColumnCount(); i++) {
					this.setFont(new Font("Tahoma", Font.BOLD, 16));

				}
			}

			
			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		}

	}

	public void pesquisarContratoAtivo() {
		GerenciarBancoFuncionariosContratoTrabalho gerenciar = new GerenciarBancoFuncionariosContratoTrabalho();
		CadastroFuncionario func = (CadastroFuncionario) modelFuncionarios.getSelectedItem();

		CadastroFuncionarioAdmissao ct = gerenciar.getcontratoAtivoPorFuncionario(func.getId_funcionario());
		if(ct != null) {
		
		lblCargo.setText(ct.getCargo());
		lblFuncao.setText(ct.getFuncao());
		lblDataAdmissao.setText(ct.getData_admissao());
		lblTipoContrato.setText(ct.getTipo_contrato());
		lblStatusContrato.setText("ATIVO");
		}else {
			lblCargo.setText("Sem Contrato Ativo");
			lblFuncao.setText("Sem Contrato Ativo");
			lblDataAdmissao.setText("Sem Contrato Ativo");
			lblTipoContrato.setText("Sem Contrato Ativo");
			lblStatusContrato.setText("Sem Contrato Ativo");;
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

	}

}
