
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
import main.java.cadastros.CadastroClassificador;
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
import main.java.cadastros.CadastroSilo;
import main.java.cadastros.CadastroTarefaGeral;
import main.java.cadastros.CadastroTransgenia;
import main.java.cadastros.ContaBancaria;
import main.java.cadastros.Contato;
import main.java.cadastros.CalculoCompleto;
import main.java.cadastros.RegistroPonto;
import main.java.cadastros.CadastroFuncionarioEvento;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.classesExtras.CombBoxRenderPersonalizadoContratoTrabalho;
import main.java.classesExtras.ComboBoxPersonalizado;
import main.java.classesExtras.ComboBoxPersonalizadoContratoTrabalho;
import main.java.classesExtras.ComboBoxRenderPersonalizado;
import main.java.classesExtras.Endereco;
import main.java.conexaoBanco.GerenciarBancoFuncionarios;
import main.java.conexaoBanco.GerenciarBancoFuncionariosContratoTrabalho;
import main.java.conexaoBanco.GerenciarBancoFuncionariosCalculos;
import main.java.conexaoBanco.GerenciarBancoFuncionariosEventos;
import main.java.conexaoBanco.GerenciarBancoCartaoPonto;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoFuncionarios;
import main.java.conexaoBanco.GerenciarBancoNotasFiscais;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoRegistroPonto;
import main.java.conexaoBanco.GerenciarBancoRomaneios;
import main.java.conexaoBanco.GerenciarBancoSafras;
import main.java.conexaoBanco.GerenciarBancoTarefaGeral;
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
import main.java.cadastros.CartaoPonto;
import main.java.cadastros.CartaoPontoCompleto;
import main.java.outros.DadosGlobais;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

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
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class TelaFuncionariosCadastroRp extends JFrame {

	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private CartaoPonto cartao_ponto;
	private final JPanelBackground contentPanel = new JPanelBackground();
	// painel pai

	// painel filho1JPanelTransparent
	private JPanelBackground painelDadosIniciais = new JPanelBackground();

	ArrayList<Integer> descontos_a_excluir = new ArrayList<>();
	ArrayList<Integer> acrescimos_a_excluir = new ArrayList<>();

	CadastroFuncionario funcionario_cadastrar = new CadastroFuncionario();
	CadastroFuncionario funcionario_atualizar = new CadastroFuncionario();
	private JDialog telaPai;
	private JComboBox cBMovimentacao;
	private JPanel painelCentral = new JPanel();
	private TelaFuncionariosCadastroRp isto;
	private JComboBox cbContratoTrabalho;
	private CombBoxRenderPersonalizadoContratoTrabalho cbContratosPersonalizados;

	private ComboBoxPersonalizadoContratoTrabalho modelContratos = new ComboBoxPersonalizadoContratoTrabalho();
	private CadastroFuncionario funcionario_local;
	private JTextField entDataRp;
	private JTextField entDataDemissao;
	private JTextField entDataIdaFerias;
	private JTextField entDataVoltaFerias;
	private JTextField entNovoValorAlteracaoSalarial;
	private JTextField entData;
	private JTextField entHora;
	private JTextField entMotivo;

	public TelaFuncionariosCadastroRp(CadastroFuncionario funcionario, Window janela_pai) {

		funcionario_local = funcionario;
		this.setContentPane(painelDadosIniciais);

		setForeground(new Color(255, 255, 255));
		this.setTitle("Associar Cartão");

		getDadosGlobais();
		// setAlwaysOnTop(true);

		// setModal(true);
		isto = this;

		setResizable(false);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setBounds(100, 100, 665, 273);

		// configuracao de paineis
		// painel pai

		painelDadosIniciais.setBackground(Color.WHITE);

		// adiciona o painel filho1 no painel principal

		painelDadosIniciais.setLayout(new BorderLayout(0, 0));

		JPanel panel_3 = new JPanel();
		painelDadosIniciais.add(panel_3);
		panel_3.setBackground(Color.WHITE);
		panel_3.setLayout(new MigLayout("", "[151px][490px,grow]", "[26px][33px][][][][]"));

		JLabel lblCpf_1 = new JLabel("Contrato de Trabalho:");
		lblCpf_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCpf_1.setForeground(Color.BLACK);
		lblCpf_1.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCpf_1.setBackground(Color.ORANGE);
		panel_3.add(lblCpf_1, "cell 0 0,alignx left,aligny center");

		cbContratoTrabalho = new JComboBox();
		cbContratosPersonalizados = new CombBoxRenderPersonalizadoContratoTrabalho();

		cbContratoTrabalho.setModel(modelContratos);
		cbContratoTrabalho.setRenderer(cbContratosPersonalizados);
		panel_3.add(cbContratoTrabalho, "cell 1 0,growx,aligny top");

		JLabel lblCpf_1_1 = new JLabel("Data:");
		lblCpf_1_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCpf_1_1.setForeground(Color.BLACK);
		lblCpf_1_1.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCpf_1_1.setBackground(Color.ORANGE);
		panel_3.add(lblCpf_1_1, "cell 0 1,alignx trailing");

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				GerenciarBancoRegistroPonto gerenciar_rp = new GerenciarBancoRegistroPonto();
				RegistroPonto rp = getDadosSalvar();
				if(rp != null) {
					int inserir = gerenciar_rp.inserir_registro_ponto(rp);
					if(inserir > 0) {
						JOptionPane.showMessageDialog(isto, "Registro Ponto Inserido");
						
						//Criar tarefa
						GerenciarBancoTarefaGeral gerenciar_tarefa = new GerenciarBancoTarefaGeral();
						CadastroTarefaGeral tarefa = new CadastroTarefaGeral();
						
					
						tarefa.setNome_tarefa("Adição Manual de Registro de Ponto");
						tarefa.setDescricao_tarefa("O Registro de Ponto id: " + inserir + " Foi adicionado manualmente"  );
						tarefa.setMensagem("O Registro de Ponto id: " + inserir + " Foi adicionado manualmente"  );
						tarefa.setCriador(login);
						tarefa.setExecutor(login);
						tarefa.setStatus_tarefa(1);
						tarefa.setPrioridade(1);
						tarefa.setTipo(5);
						tarefa.setId_funcionario_pai(funcionario_local.getId_funcionario());
						
						GetData data = new GetData();
						tarefa.setHora(data.getHora());
						tarefa.setData(data.getData());
						tarefa.setHora_agendada(data.getHora());
						tarefa.setData_agendada(data.getData());
						
						boolean inseriu_tarefa = gerenciar_tarefa.inserirTarefaGeral(tarefa);
						if(inseriu_tarefa) {

						}else {
							JOptionPane.showMessageDialog(isto, "Tarefa Não Inserida, Consulte o administrador");

						}
						
						JOptionPane.showMessageDialog(isto, "Atualizado");
						((TelaGerenciarFuncionario) janela_pai).pesquisar_tarefas();
						((TelaGerenciarFuncionario) janela_pai).pesquisar_registros_ponto();
						((TelaGerenciarFuncionario) janela_pai).pesquisar_rp_diario();
						isto.dispose();

					}else {
						JOptionPane.showMessageDialog(isto, "Erro ao Inserir o Registro de Ponto!\nConsulte o administrador");
					}
				}

			}
		});

		entData = new JTextField();
		panel_3.add(entData, "cell 1 1,growx");
		entData.setColumns(10);

		JLabel lblCpf_1_1_1 = new JLabel("Movimentação:");
		lblCpf_1_1_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCpf_1_1_1.setForeground(Color.BLACK);
		lblCpf_1_1_1.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCpf_1_1_1.setBackground(Color.ORANGE);
		panel_3.add(lblCpf_1_1_1, "cell 0 2,alignx trailing");

		 cBMovimentacao = new JComboBox();
		cBMovimentacao.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_3.add(cBMovimentacao, "cell 1 2,growx");
		cBMovimentacao.addItem("ENTRADA 1");
		cBMovimentacao.addItem("SAÍDA 1");

		cBMovimentacao.addItem("ENTRADA 2");
		cBMovimentacao.addItem("SAÍDA 2");

		cBMovimentacao.addItem("ENTRADA 3");
		cBMovimentacao.addItem("SAÍDA 3");
		
		JLabel lblCpf_1_1_1_1 = new JLabel("Hora:");
		lblCpf_1_1_1_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCpf_1_1_1_1.setForeground(Color.BLACK);
		lblCpf_1_1_1_1.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCpf_1_1_1_1.setBackground(Color.ORANGE);
		panel_3.add(lblCpf_1_1_1_1, "cell 0 3,alignx trailing");
		
		entHora = new JTextField();
		entHora.setColumns(10);
		panel_3.add(entHora, "cell 1 3,growx");
		
		JLabel lblCpf_1_1_1_1_1 = new JLabel("Motivo:");
		lblCpf_1_1_1_1_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCpf_1_1_1_1_1.setForeground(Color.BLACK);
		lblCpf_1_1_1_1_1.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCpf_1_1_1_1_1.setBackground(Color.ORANGE);
		panel_3.add(lblCpf_1_1_1_1_1, "cell 0 4,alignx trailing");
		
		entMotivo = new JTextField();
		entMotivo.setColumns(10);
		panel_3.add(entMotivo, "cell 1 4,growx");


		btnSalvar.setForeground(Color.WHITE);
		btnSalvar.setBackground(new Color(0, 51, 0));
		btnSalvar.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_3.add(btnSalvar, "cell 1 5,alignx right,aligny top");
		painelCentral.setLayout(new BorderLayout(0, 0));

		// configura widgets no painel finalizar

		pesquisar_contratos();
		this.setLocationRelativeTo(janela_pai);

	}

	public void getDadosGlobais() {
		// gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		GerenciadorLog = dados.getGerenciadorLog();
		configs_globais = dados.getConfigs_globais();

		// usuario logado
		login = dados.getLogin();

	}
	
	
	public RegistroPonto getDadosSalvar() {
		RegistroPonto rp = new RegistroPonto();
		
		String data = entData.getText();
		String hora = entHora.getText();
		int movimentacao = cBMovimentacao.getSelectedIndex();

		try {
			if(isDateValid(data) ){
				rp.setData(data);
				
				rp.setId_funcionario(funcionario_local.getId_funcionario());
				rp.setMovimentacao(movimentacao + 1);
				
				try {
				LocalTime hora_lt  = LocalTime.parse(hora, DateTimeFormatter.ofPattern("HH:mm"));
				rp.setHora(hora);
				
				
				String motivo = entMotivo.getText();
				if(motivo != null && !motivo.equals("") && motivo.length() > 10) {
					rp.setMotivo(motivo);
					return rp;

				}else {
					JOptionPane.showMessageDialog(isto, "Motivo Definido Incorreto\nNão pode ser nulo!\nPrecisa ser maior que 10 caracteres");
					return null;
				}
				

				}
				catch(Exception y) {
					JOptionPane.showMessageDialog(isto, "Hora Inválida!");
					return null;
				}
				
			}else {
				JOptionPane.showMessageDialog(isto, "Hora Inválida");
				return null;
			}
			
			
			
		}catch(Exception e) {
			JOptionPane.showMessageDialog(isto, "Data Inválida!");
			return null;
		}
		
		
		
		
	}

	public void pesquisar_contratos() {

		GerenciarBancoFuncionariosContratoTrabalho gerenciar = new GerenciarBancoFuncionariosContratoTrabalho();

		ArrayList<CadastroFuncionarioAdmissao> lista_contratos = gerenciar
				.getcontratosPorColaborador(funcionario_local.getId_funcionario());

		for (CadastroFuncionarioAdmissao cad : lista_contratos) {
			modelContratos.addCC(cad);
		}

	}

	
	public static boolean isDateValid(String strDate) {
		String dateFormat = "dd/MM/uuuu";

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat)
				.withResolverStyle(ResolverStyle.STRICT);
		try {
			LocalDate date = LocalDate.parse(strDate, dateTimeFormatter);
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}
	}
	
	
}
