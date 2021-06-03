
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
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import br.com.caelum.stella.ValidationMessage;
import br.com.caelum.stella.validation.CPFValidator;

import main.java.cadastros.CadastroAviso;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroFuncionario;
import main.java.cadastros.CadastroFuncionarioAdmissao;
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
import main.java.cadastros.DescontoCompleto;
import main.java.cadastros.CadastroFuncionariosHorarios;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.classesExtras.CombBoxRenderPersonalizadoContratoTrabalho;
import main.java.classesExtras.ComboBoxPersonalizado;
import main.java.classesExtras.ComboBoxPersonalizadoContratoTrabalho;
import main.java.classesExtras.ComboBoxRenderPersonalizado;
import main.java.classesExtras.Endereco;
import main.java.conexaoBanco.GerenciarBancoFuncionarios;
import main.java.conexaoBanco.GerenciarBancoFuncionariosContratoTrabalho;
import main.java.conexaoBanco.GerenciarBancoFuncionariosDescontos;
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
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoUnit;

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
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class TelaFuncionariosCadastroJornadaTrabalho extends JFrame {

	private ComboBoxPersonalizadoContratoTrabalho modelContratos = new ComboBoxPersonalizadoContratoTrabalho();

	private HorariosTableModel modelHorarios = new HorariosTableModel();
	private ArrayList<CadastroFuncionariosHorarios> listaHorarios = new ArrayList<>();
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;

	private final JPanelBackground contentPanel = new JPanelBackground();

	// painel pai

	// painel filho1JPanelTransparent
	private JPanelBackground painelDadosIniciais = new JPanelBackground();

	ArrayList<Integer> descontos_a_excluir = new ArrayList<>();
	ArrayList<Integer> acrescimos_a_excluir = new ArrayList<>();

	CadastroFuncionario funcionario_cadastrar = new CadastroFuncionario();
	CadastroFuncionario funcionario_atualizar = new CadastroFuncionario();
	private JDialog telaPai;

	private JPanel painelCentral = new JPanel();
	private TelaFuncionariosCadastroJornadaTrabalho isto;
	private JComboBox cbContratoTrabalho;
	private CadastroFuncionariosHorarios desconto_selecinado;
	private JComboBox cBTipoJornadaTrabalho;
	private JCheckBox[] chkFolga = new JCheckBox[7];
	private CombBoxRenderPersonalizadoContratoTrabalho cbContratosPersonalizados;
	private CadastroFuncionario funcionario_local;
	private JTable tabela_horarios;
	private JTextField entHoraEntrada;
	private JTextField entHoraSaida;
	private JTextField entHoraIntervalo;
	private JTextField entTempoIntervaloIntrajornada;
	private JLabel lblQuantidadeHorasSemanais;
	JLabel lblTipoRevezamento;
	JComboBox cbTipoRevezamento;
	private JTextField entDataInicialDeTrabalho;
	private JPanel painelFolgas;

	public TelaFuncionariosCadastroJornadaTrabalho(CadastroFuncionario funcionario, Window janela_pai) {

		funcionario_local = funcionario;
		this.setContentPane(painelDadosIniciais);

		setForeground(new Color(255, 255, 255));
		this.setTitle("Nova Jornada de Trabalho");

		getDadosGlobais();
		// setAlwaysOnTop(true);

		// setModal(true);
		isto = this;

		setResizable(false);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setBounds(100, 100, 980, 704);

		// configuracao de paineis
		// painel pai

		painelDadosIniciais.setBackground(Color.WHITE);

		// adiciona o painel filho1 no painel principal

		JLabel lblCadastro_2_1 = new JLabel(" ----- Cadastro / Dados da Empresa");
		lblCadastro_2_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCadastro_2_1.setForeground(Color.BLACK);
		lblCadastro_2_1.setFont(new Font("Arial", Font.PLAIN, 14));
		lblCadastro_2_1.setBackground(Color.ORANGE);
		lblCadastro_2_1.setBounds(0, 0, 250, 33);
		lblCadastro_2_1.setHorizontalAlignment(JLabel.LEFT);
		painelDadosIniciais.setLayout(new BorderLayout(0, 0));

		JPanel panel_3 = new JPanel();
		painelDadosIniciais.add(panel_3);
		panel_3.setBackground(Color.WHITE);
		panel_3.setLayout(new MigLayout("", "[159px,grow][][][][12px][752px,grow]",
				"[27px][][27px][][][65px,grow][33px][grow][][][][][236px][][][][]"));

		JLabel lblCpf = new JLabel("Contrato de Trabalho:");
		panel_3.add(lblCpf, "cell 0 0,alignx right,aligny center");
		lblCpf.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCpf.setForeground(Color.BLACK);
		lblCpf.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCpf.setBackground(Color.ORANGE);
		lblCpf.setHorizontalAlignment(JLabel.RIGHT);

		cbContratosPersonalizados = new CombBoxRenderPersonalizadoContratoTrabalho();

		cbContratoTrabalho = new JComboBox();
		cbContratoTrabalho.setModel(modelContratos);
		cbContratoTrabalho.setRenderer(cbContratosPersonalizados);

		panel_3.add(cbContratoTrabalho, "cell 1 0 5 1,growx,aligny top");
		cbContratoTrabalho.setFont(new Font("Arial", Font.BOLD, 14));

		JLabel lblDataInicialDe = new JLabel("Data Inicial de Trabalho:");
		lblDataInicialDe.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDataInicialDe.setForeground(Color.BLACK);
		lblDataInicialDe.setFont(new Font("Arial", Font.PLAIN, 16));
		lblDataInicialDe.setBackground(Color.ORANGE);
		panel_3.add(lblDataInicialDe, "cell 0 1");

		entDataInicialDeTrabalho = new JTextField();
		entDataInicialDeTrabalho.setText("1");
		entDataInicialDeTrabalho.setFont(new Font("SansSerif", Font.BOLD, 14));
		entDataInicialDeTrabalho.setColumns(10);
		panel_3.add(entDataInicialDeTrabalho, "cell 2 1 4 1,growx");

		JLabel lblCargo = new JLabel("Tipo de Jornada:");
		lblCargo.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCargo.setForeground(Color.BLACK);
		lblCargo.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCargo.setBackground(Color.ORANGE);
		panel_3.add(lblCargo, "cell 0 2,alignx right,aligny center");

		cBTipoJornadaTrabalho = new JComboBox();
		cBTipoJornadaTrabalho.addItem("Turno Fixo");
		cBTipoJornadaTrabalho.addItem("Turno Revezamento");
		cBTipoJornadaTrabalho.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				if (cBTipoJornadaTrabalho.getSelectedIndex() == 1) {
					if (lblTipoRevezamento == null) {
						lblTipoRevezamento = new JLabel("Tipo de Revezamento:");
						lblTipoRevezamento.setHorizontalAlignment(SwingConstants.TRAILING);
						lblTipoRevezamento.setForeground(Color.BLACK);
						lblTipoRevezamento.setFont(new Font("Arial", Font.PLAIN, 16));
						lblTipoRevezamento.setBackground(Color.ORANGE);
						panel_3.add(lblTipoRevezamento, "cell 0 3,alignx right");

					}

					if (cbTipoRevezamento == null) {
						cbTipoRevezamento = new JComboBox();
						cbTipoRevezamento.addItem("5x2");
						cbTipoRevezamento.setFont(new Font("Arial", Font.BOLD, 14));
						panel_3.add(cbTipoRevezamento, "cell 1 3 5 1,growx");
					}

					painelFolgas.setEnabled(false);
					painelFolgas.setVisible(false);

				} else if (cBTipoJornadaTrabalho.getSelectedIndex() == 0) {
					try {
						panel_3.remove(lblTipoRevezamento);
						panel_3.remove(cbTipoRevezamento);

						panel_3.repaint();
						panel_3.updateUI();

					} catch (Exception t) {

					}

					lblTipoRevezamento = null;
					cbTipoRevezamento = null;
					painelFolgas.setEnabled(true);
					painelFolgas.setVisible(true);
				}

			}
		});
		cBTipoJornadaTrabalho.setFont(new Font("Arial", Font.BOLD, 14));
		panel_3.add(cBTipoJornadaTrabalho, "cell 1 2 5 1,growx,aligny top");

		tabela_horarios = new JTable(modelHorarios);
		tabela_horarios.setRowHeight(30);

		JScrollPane scrollPane = new JScrollPane(tabela_horarios);
		scrollPane.getViewport().setBackground(Color.white);

		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setBackground(Color.WHITE);
		panel_3.add(panel, "cell 0 5 6 1,alignx center,growy");
		panel.setLayout(new MigLayout("", "[grow][grow][grow][grow]", "[grow]"));

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(Color.BLACK));
		panel_1.setBackground(Color.WHITE);
		panel.add(panel_1, "cell 0 0,grow");
		panel_1.setLayout(new MigLayout("", "[][grow][grow][][]", "[]"));

		JLabel lblNewLabel = new JLabel("Hora da Entrada:");
		lblNewLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_1.add(lblNewLabel, "cell 0 0,alignx trailing");

		entHoraEntrada = new JTextField();
		entHoraEntrada.setText("07:00");
		panel_1.add(entHoraEntrada, "cell 1 0,growx");
		entHoraEntrada.setColumns(10);

		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1_1.setBackground(Color.WHITE);
		panel.add(panel_1_1, "cell 1 0,grow");
		panel_1_1.setLayout(new MigLayout("", "[][grow][][grow]", "[]"));

		JLabel lblSada = new JLabel("Hora da Saída:");
		lblSada.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_1_1.add(lblSada, "cell 0 0,alignx trailing");

		entHoraSaida = new JTextField();
		entHoraSaida.setText("17:00");
		entHoraSaida.setColumns(10);
		panel_1_1.add(entHoraSaida, "cell 1 0,growx");

		JPanel panel_1_1_1_1 = new JPanel();
		panel_3.add(panel_1_1_1_1, "cell 0 6 6 1,alignx center");
		panel_1_1_1_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1_1_1_1.setBackground(Color.WHITE);
		panel_1_1_1_1.setLayout(new MigLayout("", "[][grow][grow][][grow][]", "[][][]"));

		JLabel lblIntervaloIntrajornada = new JLabel("Intervalo IntraJornada:");
		panel_1_1_1_1.add(lblIntervaloIntrajornada, "cell 0 0,alignx trailing");
		lblIntervaloIntrajornada.setFont(new Font("SansSerif", Font.PLAIN, 16));

		entTempoIntervaloIntrajornada = new JTextField();
		entTempoIntervaloIntrajornada.setFont(new Font("SansSerif", Font.BOLD, 14));
		entTempoIntervaloIntrajornada.setText("1");
		panel_1_1_1_1.add(entTempoIntervaloIntrajornada, "cell 1 0,growx");
		entTempoIntervaloIntrajornada.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Mínimo de 1 hora previsto por lei ");
		panel_1_1_1_1.add(lblNewLabel_2, "cell 1 1");
		lblNewLabel_2.setForeground(Color.RED);

		JLabel lblSada_1 = new JLabel("Hora Saída para Intevalo:");
		lblSada_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_1_1_1_1.add(lblSada_1, "cell 1 2,alignx trailing");

		entHoraIntervalo = new JTextField();
		entHoraIntervalo.setText("11:00");
		entHoraIntervalo.setColumns(10);
		panel_1_1_1_1.add(entHoraIntervalo, "cell 2 2,growx");

		JButton btnGerarEscala = new JButton("Gerar Escala");
		btnGerarEscala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				preencher_horarios();
			}
		});

		painelFolgas = new JPanel();
		painelFolgas.setBackground(Color.WHITE);
		panel_3.add(painelFolgas, "cell 0 7 6 1,alignx center,growy");
		painelFolgas.setLayout(new MigLayout("", "[][][][][][][][]", "[][]"));

		JLabel lblNewLabel_1 = new JLabel("Folgas:");
		lblNewLabel_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelFolgas.add(lblNewLabel_1, "cell 0 0");

		chkFolga[0] = new JCheckBox("Segunda-Feira");
		chkFolga[0].setFont(new Font("SansSerif", Font.PLAIN, 14));
		painelFolgas.add(chkFolga[0], "cell 1 0");

		chkFolga[1] = new JCheckBox("Terça-Feira");
		chkFolga[1].setFont(new Font("SansSerif", Font.PLAIN, 14));
		painelFolgas.add(chkFolga[1], "cell 2 0");

		chkFolga[2] = new JCheckBox("Quarta-Feira");
		chkFolga[2].setFont(new Font("SansSerif", Font.PLAIN, 14));
		painelFolgas.add(chkFolga[2], "cell 3 0");

		chkFolga[3] = new JCheckBox("Quinta-Feira");
		chkFolga[3].setFont(new Font("SansSerif", Font.PLAIN, 14));
		painelFolgas.add(chkFolga[3], "cell 4 0");

		chkFolga[4] = new JCheckBox("Sexta-Feira");
		chkFolga[4].setFont(new Font("SansSerif", Font.PLAIN, 14));
		painelFolgas.add(chkFolga[4], "cell 5 0");

		chkFolga[5] = new JCheckBox("Sábado");
		chkFolga[5].setSelected(true);
		chkFolga[5].setFont(new Font("SansSerif", Font.PLAIN, 14));
		painelFolgas.add(chkFolga[5], "cell 6 0");

		chkFolga[6] = new JCheckBox("Domingo");
		chkFolga[6].setSelected(true);
		chkFolga[6].setFont(new Font("SansSerif", Font.PLAIN, 14));
		painelFolgas.add(chkFolga[6], "cell 7 0");

		JLabel lblAviso = new JLabel("Configure para que a jornada de trabalho não ultrapasse 44 horas semanais");
		lblAviso.setForeground(Color.BLACK);
		panel_3.add(lblAviso, "cell 5 10,alignx right");

		btnGerarEscala.setForeground(Color.WHITE);
		btnGerarEscala.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnGerarEscala.setBackground(new Color(0, 0, 153));
		panel_3.add(btnGerarEscala, "cell 5 11,alignx right,aligny top");

		panel_3.add(scrollPane, "cell 0 12 6 4,grow");

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

		JLabel lblNewLabel_1_1 = new JLabel("Total Horas Semanais:");
		lblNewLabel_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_3.add(lblNewLabel_1_1, "cell 0 16,alignx right");

		lblQuantidadeHorasSemanais = new JLabel("0");
		lblQuantidadeHorasSemanais.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_3.add(lblQuantidadeHorasSemanais, "cell 1 16 4 1");

		btnSalvar.setForeground(Color.WHITE);
		btnSalvar.setBackground(new Color(0, 51, 0));
		btnSalvar.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_3.add(btnSalvar, "cell 5 16,alignx right,aligny top");
		painelCentral.setLayout(new BorderLayout(0, 0));

		// configura widgets no painel finalizar
		pesquisar_contratos();
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

	public void getDadosGlobais() {
		// gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		GerenciadorLog = dados.getGerenciadorLog();
		configs_globais = dados.getConfigs_globais();

		// usuario logado
		login = dados.getLogin();

	}

	public void pesquisar_contratos() {

		GerenciarBancoFuncionariosContratoTrabalho gerenciar = new GerenciarBancoFuncionariosContratoTrabalho();

		ArrayList<CadastroFuncionarioAdmissao> lista_contratos = gerenciar
				.getcontratosPorColaborador(funcionario_local.getId_funcionario());

		for (CadastroFuncionarioAdmissao cad : lista_contratos) {
			modelContratos.addCC(cad);
			entDataInicialDeTrabalho.setText(cad.getData_admissao());
		}

	}

	public void preencher_horarios() {

		modelHorarios.onRemoveAll();

		if (cBTipoJornadaTrabalho.getSelectedIndex() == 0) {
			long quantidade_horas = 0;
			// turno normal

			int minutos_tempo_intervalo = Integer.parseInt(entTempoIntervaloIntrajornada.getText());
			String s_hora_entrada = entHoraEntrada.getText();
			String s_hora_saida = entHoraSaida.getText();
			String s_hora_intervalo = entHoraIntervalo.getText();

			LocalTime hora_entrada1, hora_saida1, hora_entrada2, hora_saida2, hora_intervalo;

			try {

				hora_entrada1 = LocalTime.parse(s_hora_entrada, DateTimeFormatter.ofPattern("HH:mm"));

				hora_saida1 = LocalTime.parse(s_hora_intervalo, DateTimeFormatter.ofPattern("HH:mm"));

				hora_entrada2 = hora_saida1.plusMinutes(minutos_tempo_intervalo);

				hora_saida2 = LocalTime.parse(s_hora_saida, DateTimeFormatter.ofPattern("HH:mm"));

				SimpleDateFormat format = new SimpleDateFormat("HH:mm");

				LocalDate data_inicial = LocalDate.parse(entDataInicialDeTrabalho.getText(),
						DateTimeFormatter.ofPattern("dd/MM/yyyy"));

				for (int i = 0; i <= 365; i++) {

					CadastroFuncionariosHorarios dia_da_semana = new CadastroFuncionariosHorarios();

					String strLocalDate2 = data_inicial.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

					dia_da_semana.setData_inicial_trabalho(strLocalDate2);
					dia_da_semana.setDia_semana(data_inicial.getDayOfWeek().getValue());
					dia_da_semana.setHora_entrada1(hora_entrada1.toString());
					dia_da_semana.setHora_saida1(hora_saida1.toString());

					dia_da_semana.setHora_entrada2(hora_entrada2.toString());

					dia_da_semana.setHora_saida2(hora_saida2.toString());

					dia_da_semana.setTempo_intervalo(minutos_tempo_intervalo);

					if (data_inicial.getDayOfWeek().getValue() == 1 && chkFolga[0].isSelected()) {
						dia_da_semana.setFolga(true);

					} else if (data_inicial.getDayOfWeek().getValue() == 2 && chkFolga[1].isSelected()) {

						dia_da_semana.setFolga(true);

					} else if (data_inicial.getDayOfWeek().getValue() == 3 && chkFolga[2].isSelected()) {

						dia_da_semana.setFolga(true);

					} else if (data_inicial.getDayOfWeek().getValue() == 4 && chkFolga[3].isSelected()) {

						dia_da_semana.setFolga(true);

					} else if (data_inicial.getDayOfWeek().getValue() == 5 && chkFolga[4].isSelected()) {

						dia_da_semana.setFolga(true);

					} else if (data_inicial.getDayOfWeek().getValue() == 6 && chkFolga[5].isSelected()) {

						dia_da_semana.setFolga(true);

					} else if (data_inicial.getDayOfWeek().getValue() == 7 && chkFolga[6].isSelected()) {

						dia_da_semana.setFolga(true);

					} else {

						try {

							Date date1 = format.parse(hora_entrada1.toString());
							Date date2 = format.parse(hora_saida1.toString());

							long difference = date2.getTime() - date1.getTime();

							int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(difference);

							if (minutes < 0)
								minutes += 1440;

							quantidade_horas = quantidade_horas + minutes;

							Date date3 = format.parse(hora_entrada2.toString());
							Date date4 = format.parse(hora_saida2.toString());

							long difference2 = date4.getTime() - date3.getTime();

							int minutes2 = (int) TimeUnit.MILLISECONDS.toMinutes(difference2);

							if (minutes2 < 0)
								minutes2 += 1440;

							quantidade_horas = quantidade_horas + minutes2;

						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						/*
						 * 
						 * dia_da_semana.setFolga(false); long
						 * periodAsMinutesPrimeiroHorario,periodAsMinutesSegundoHorario;
						 * 
						 * periodAsMinutesPrimeiroHorario = ChronoUnit.MINUTES.between(hora_entrada1 ,
						 * hora_saida1); periodAsMinutesSegundoHorario =
						 * ChronoUnit.MINUTES.between(hora_entrada2 , hora_saida2);
						 * 
						 * quantidade_horas = quantidade_horas + periodAsMinutesPrimeiroHorario +
						 * periodAsMinutesSegundoHorario;
						 * 
						 */

					}
					modelHorarios.onAdd(dia_da_semana);
					data_inicial = data_inicial.plusDays(1);
				}

			} catch (Exception e) {
				JOptionPane.showMessageDialog(isto, "Horario Invalido");

			}

			lblQuantidadeHorasSemanais.setText(Long.toString(quantidade_horas / 60));

		} else if (cBTipoJornadaTrabalho.getSelectedIndex() == 1) {
			long quantidade_horas = 0;
			// turno normal

			int minutos_tempo_intervalo = Integer.parseInt(entTempoIntervaloIntrajornada.getText());
			String s_hora_entrada = entHoraEntrada.getText();
			String s_hora_saida = entHoraSaida.getText();
			String s_hora_intervalo = entHoraIntervalo.getText();

			LocalTime hora_entrada1, hora_saida1, hora_entrada2, hora_saida2, hora_intervalo;

			try {

				hora_entrada1 = LocalTime.parse(s_hora_entrada, DateTimeFormatter.ofPattern("HH:mm"));

				hora_saida1 = LocalTime.parse(s_hora_intervalo, DateTimeFormatter.ofPattern("HH:mm"));

				hora_entrada2 = hora_saida1.plusMinutes(minutos_tempo_intervalo);

				hora_saida2 = LocalTime.parse(s_hora_saida, DateTimeFormatter.ofPattern("HH:mm"));

				SimpleDateFormat format = new SimpleDateFormat("HH:mm");

				LocalDate data_inicial = LocalDate.parse(entDataInicialDeTrabalho.getText(),
						DateTimeFormatter.ofPattern("dd/MM/yyyy"));

				for (int j = 0; j <= 10; j++) {
					for (int i = 0; i <= 6; i++) {

						CadastroFuncionariosHorarios dia_da_semana = new CadastroFuncionariosHorarios();

						String strLocalDate2 = data_inicial.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

						dia_da_semana.setData_inicial_trabalho(strLocalDate2);
						dia_da_semana.setDia_semana(data_inicial.getDayOfWeek().getValue());
						dia_da_semana.setHora_entrada1(hora_entrada1.toString());
						dia_da_semana.setHora_saida1(hora_saida1.toString());

						dia_da_semana.setHora_entrada2(hora_entrada2.toString());

						dia_da_semana.setHora_saida2(hora_saida2.toString());

						dia_da_semana.setTempo_intervalo(minutos_tempo_intervalo);
						if (i == 5 || i == 6) {
							dia_da_semana.setFolga(true);

						} else {

							try {

								Date date1 = format.parse(hora_entrada1.toString());
								Date date2 = format.parse(hora_saida1.toString());

								long difference = date2.getTime() - date1.getTime();

								int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(difference);

								if (minutes < 0)
									minutes += 1440;

								quantidade_horas = quantidade_horas + minutes;

								Date date3 = format.parse(hora_entrada2.toString());
								Date date4 = format.parse(hora_saida2.toString());

								long difference2 = date4.getTime() - date3.getTime();

								int minutes2 = (int) TimeUnit.MILLISECONDS.toMinutes(difference2);

								if (minutes2 < 0)
									minutes2 += 1440;

								quantidade_horas = quantidade_horas + minutes2;

							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							/*
							 * 
							 * dia_da_semana.setFolga(false); long
							 * periodAsMinutesPrimeiroHorario,periodAsMinutesSegundoHorario;
							 * 
							 * periodAsMinutesPrimeiroHorario = ChronoUnit.MINUTES.between(hora_entrada1 ,
							 * hora_saida1); periodAsMinutesSegundoHorario =
							 * ChronoUnit.MINUTES.between(hora_entrada2 , hora_saida2);
							 * 
							 * quantidade_horas = quantidade_horas + periodAsMinutesPrimeiroHorario +
							 * periodAsMinutesSegundoHorario;
							 * 
							 */

						}
						modelHorarios.onAdd(dia_da_semana);
						data_inicial = data_inicial.plusDays(1);
					}
				}

			} catch (Exception e) {
				JOptionPane.showMessageDialog(isto, "Horario Invalido");

			}

			lblQuantidadeHorasSemanais.setText(Long.toString(quantidade_horas / 60));

		}

	}

	public class HorariosTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int data = 0;

		private final int movimentacao = 1;

		private final int hora1 = 2;
		private final int hora2 = 3;
		private final int hora3 = 4;
		private final int hora4 = 5;

		private final int coluna_hora = 0;

		private final String colunas[] = { "DATA", "DIA DA SEMANA", "HORA ENTRADA 1", "HORA SAÍDA 1", "HORA ENTRADA 2",
				"HORA SAÍDA 2" };
		Locale ptBr = new Locale("pt", "BR");

		private final ArrayList<CadastroFuncionariosHorarios> dados = new ArrayList<>();// usamos como dados uma lista
		// genérica de
		// nfs

		public HorariosTableModel() {

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
			case movimentacao:
				return String.class;
			case hora1:
				return String.class;
			case hora2:
				return String.class;
			case hora3:
				return String.class;
			case hora4:
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
			CadastroFuncionariosHorarios dado = dados.get(rowIndex);

			// retorna o valor da coluna
			switch (columnIndex) {
			case data:
				return dado.getData_inicial_trabalho();
			case movimentacao: {

				int dia_semana = dado.getDia_semana();

				if (dia_semana == 1) {
					return "SEGUNDA-FEIRA";
				} else if (dia_semana == 2) {
					return "TERÇA-FEIRA";
				} else if (dia_semana == 3) {
					return "QUARTA-FEIRA";
				} else if (dia_semana == 4) {
					return "QUINTA-FEIRA";
				} else if (dia_semana == 5) {
					return "SEXTA-FEIRA";
				}

				else if (dia_semana == 6) {
					return "SÁBADO";
				}

				else if (dia_semana == 7) {
					return "DOMINGO";
				}

			}
			case hora1: {
				if (!dado.isFolga())
					return dado.getHora_entrada1();
				else {
					return "FOLGA";
				}

			}
			case hora2: {
				if (!dado.isFolga())

					return dado.getHora_saida1();

				else {
					return "FOLGA";
				}
			}
			case hora3: {
				if (!dado.isFolga()) {

					return dado.getHora_entrada2();
				} else {
					return "FOLGA";
				}
			}
			case hora4: {

				if (!dado.isFolga())

					return dado.getHora_saida2();

				else {
					return "FOLGA";
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

		/*
		 * @Override public void setValueAt(Object aValue, int rowIndex, int
		 * columnIndex) { CadastroFuncionariosHorarios recebimento =
		 * dados.get(rowIndex); fireTableDataChanged();
		 * 
		 * }
		 */

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			CadastroFuncionariosHorarios recebimento = dados.get(rowIndex);

			if (columnIndex == 1) {
				if (String.valueOf(aValue).equalsIgnoreCase("FOLGA")) {
					recebimento.setHora_entrada1(String.valueOf(aValue));
					recebimento.setHora_saida1(String.valueOf(aValue));
				} else
					recebimento.setHora_entrada1(String.valueOf(aValue));

			} else if (columnIndex == 2) {
				if (String.valueOf(aValue).equalsIgnoreCase("FOLGA")) {

					recebimento.setHora_saida1(String.valueOf(aValue));
					recebimento.setHora_entrada1(String.valueOf(aValue));
				} else
					recebimento.setHora_saida1(String.valueOf(aValue));

			} else if (columnIndex == 3) {

				if (String.valueOf(aValue).equalsIgnoreCase("FOLGA")) {

					recebimento.setHora_entrada2(String.valueOf(aValue));
					recebimento.setHora_saida2(String.valueOf(aValue));

				} else
					recebimento.setHora_entrada2(String.valueOf(aValue));

			} else if (columnIndex == 4) {

				if (String.valueOf(aValue).equalsIgnoreCase("FOLGA")) {

					recebimento.setHora_saida2(String.valueOf(aValue));
					recebimento.setHora_entrada2(String.valueOf(aValue));

				} else
					recebimento.setHora_saida2(String.valueOf(aValue));

			}

			fireTableDataChanged();
			re_preencher_horarios();
		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public CadastroFuncionariosHorarios getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(CadastroFuncionariosHorarios nota) {
			return dados.indexOf(nota);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(CadastroFuncionariosHorarios nota) {
			dados.add(nota);
			fireTableRowsInserted(indexOf(nota), indexOf(nota));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<CadastroFuncionariosHorarios> dadosIn) {
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
		public void onRemove(CadastroFuncionariosHorarios nota) {
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

	public void re_preencher_horarios() {

		ArrayList<CadastroFuncionariosHorarios> horarios = new ArrayList<>();

		for (int i = 0; i < modelHorarios.getRowCount(); i++) {

			CadastroFuncionariosHorarios horario = modelHorarios.getValue(i);

			horarios.add(horario);

		}

		if (cBTipoJornadaTrabalho.getSelectedIndex() == 0) {

			long quantidade_horas = 0;

			for (CadastroFuncionariosHorarios horario : horarios) {

				int tempo_intervalo = horario.getTempo_intervalo();
				LocalTime hora_entrada1, hora_saida1, hora_entrada2, hora_saida2;
				boolean folga = horario.isFolga();

				try {

					hora_entrada1 = LocalTime.parse(horario.getHora_entrada1(), DateTimeFormatter.ofPattern("HH:mm"));
					hora_saida1 = LocalTime.parse(horario.getHora_saida1(), DateTimeFormatter.ofPattern("HH:mm"));
					hora_entrada2 = LocalTime.parse(horario.getHora_entrada2(), DateTimeFormatter.ofPattern("HH:mm"));
					hora_saida2 = LocalTime.parse(horario.getHora_saida2(), DateTimeFormatter.ofPattern("HH:mm"));

					if (!folga) {
						SimpleDateFormat format = new SimpleDateFormat("HH:mm");

						Date date1 = format.parse(hora_entrada1.toString());
						Date date2 = format.parse(hora_saida1.toString());

						long difference = date2.getTime() - date1.getTime();

						int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(difference);

						if (minutes < 0)
							minutes += 1440;

						quantidade_horas = quantidade_horas + minutes;

						Date date3 = format.parse(hora_entrada2.toString());
						Date date4 = format.parse(hora_saida2.toString());

						long difference2 = date4.getTime() - date3.getTime();

						int minutes2 = (int) TimeUnit.MILLISECONDS.toMinutes(difference2);

						if (minutes2 < 0)
							minutes2 += 1440;

						quantidade_horas = quantidade_horas + minutes2;

					}

				}

				catch (Exception e) {

					try {
						hora_entrada1 = LocalTime.parse(horario.getHora_entrada1(),
								DateTimeFormatter.ofPattern("HH:mm"));
						hora_saida1 = LocalTime.parse(horario.getHora_saida1(), DateTimeFormatter.ofPattern("HH:mm"));

						SimpleDateFormat format = new SimpleDateFormat("HH:mm");

						Date date1 = format.parse(hora_entrada1.toString());
						Date date2 = format.parse(hora_saida1.toString());

						long difference = date2.getTime() - date1.getTime();

						int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(difference);

						if (minutes < 0)
							minutes += 1440;

						quantidade_horas = quantidade_horas + minutes;

					} catch (Exception t) {

						try {
							hora_entrada2 = LocalTime.parse(horario.getHora_entrada2(),
									DateTimeFormatter.ofPattern("HH:mm"));
							hora_saida2 = LocalTime.parse(horario.getHora_saida2(),
									DateTimeFormatter.ofPattern("HH:mm"));

							SimpleDateFormat format = new SimpleDateFormat("HH:mm");

							Date date3 = format.parse(hora_entrada2.toString());
							Date date4 = format.parse(hora_saida2.toString());

							long difference2 = date4.getTime() - date3.getTime();

							int minutes2 = (int) TimeUnit.MILLISECONDS.toMinutes(difference2);

							if (minutes2 < 0)
								minutes2 += 1440;

							quantidade_horas = quantidade_horas + minutes2;

						} catch (Exception y) {

						}
					}

				}

			}

			lblQuantidadeHorasSemanais.setText(Long.toString(quantidade_horas / 60));

		}

	}

}
