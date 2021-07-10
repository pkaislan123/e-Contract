
package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Locale;

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
import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.Window;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import main.java.cadastros.CadastroAcessoTemporario;
import main.java.cadastros.CadastroAditivo;
import main.java.cadastros.CadastroAviso;
import main.java.cadastros.CadastroBaseArquivos;
import main.java.cadastros.CadastroBaseDados;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroContrato.CadastroPagamento;
import main.java.cadastros.CadastroContrato.CadastroPagamentoContratual;
import main.java.cadastros.CadastroContrato.CadastroTarefa;
import main.java.cadastros.CadastroContrato.Recebimento;
import main.java.cadastros.CadastroDocumento;
import main.java.cadastros.CadastroFuncionario;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroLogin.Mensagem;
import main.java.cadastros.CadastroModelo;
import main.java.cadastros.CadastroNFe;
import main.java.cadastros.CadastroNota;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroRomaneio;
import main.java.cadastros.CadastroSafra;
import main.java.cadastros.CadastroSilo;
import main.java.cadastros.ContaBancaria;
import main.java.cadastros.Contato;
import main.java.cadastros.DadosCarregamento;
import main.java.cadastros.DadosContratos;
import main.java.cadastros.DadosRecebimento;
import main.java.cadastros.FinanceiroConta;
import main.java.cadastros.FinanceiroGrupoContas;
import main.java.cadastros.FinanceiroConta;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.classesExtras.Endereco;
import main.java.classesExtras.RenderizadorContato;
import main.java.conexaoBanco.GerenciarBancoAcessoTemporario;
import main.java.conexaoBanco.GerenciarBancoAditivos;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoDocumento;
import main.java.conexaoBanco.GerenciarBancoNotasFiscais;
import main.java.conexaoBanco.GerenciarBancoPadrao;
import main.java.conexaoBanco.GerenciarBancoPontuacao;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoRomaneios;
import main.java.conexaoBanco.GerenciarBancoSafras;
import main.java.conexaoBanco.GerenciarBancoSilo;
import main.java.conexaoBanco.GerenciarBancoTransferencias;
import main.java.conexaoBanco.GerenciarBancoTransferenciasCarga;
import main.java.conexoes.TesteConexao;
import main.java.graficos.GraficoLinha;
import main.java.graficos.JPanelGrafico;
import main.java.graficos.JPanelGraficoCarregamento;
import main.java.graficos.JPanelGraficoRecebimento;
import main.java.gui.TelaCadastroCliente;
import main.java.gui.TelaMain;
import main.java.gui.TelaRomaneios;
import main.java.manipular.ArquivoConfiguracoes;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.manipular.ConverterPdf;
import main.java.manipular.CopiarArquivo;
import main.java.manipular.EditarAditivo;
import main.java.manipular.EditarExcel;
import main.java.manipular.EditarWord;
import main.java.manipular.Email;
import main.java.manipular.GetDadosGlobais;
import main.java.manipular.ManipularArquivoTerceiros;
import main.java.manipular.ManipularNotasFiscais;
import main.java.manipular.ManipularRomaneios;
import main.java.manipular.ManipularTxt;
import main.java.manipular.MonitorarRomaneios;
import main.java.manipular.Nuvem;
import main.java.manipular.PorExtenso;
import main.java.manipular.Whatsapp;
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
import net.miginfocom.swing.MigLayout;
import outros.ValidaCNPj;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroNuvem;
import main.java.cadastros.CadastroPontuacao;
import main.java.cadastros.CadastroZapMessenger;
import main.java.outros.DadosGlobais;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroGrupo;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoGrupos;
import main.java.conexaoBanco.GerenciarBancoFinanceiroConta;
import main.java.conexaoBanco.GerenciarBancoFinanceiroGrupoContas;
import main.java.conexaoBanco.GerenciarBancoFuncionarios;
import main.java.conexaoBanco.GerenciarBancoLogin;
import main.java.conexaoBanco.GerenciarBancoNotas;
import main.java.cadastros.CadastroProduto;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroSafra;
import main.java.classesExtras.CBLocalRetiradaPersonalizado;
import main.java.classesExtras.CBLocalRetiradaRenderPersonalizado;
import main.java.classesExtras.CBProdutoPersonalizado;
import main.java.classesExtras.CBProdutoRenderPersonalizado;
import main.java.classesExtras.ComboBoxContato;
import main.java.classesExtras.ComboBoxPersonalizado;
import main.java.classesExtras.ComboBoxPersonalizadoContaBancaria;
import main.java.classesExtras.ComboBoxPersonalizadoFuncionario;
import main.java.classesExtras.ComboBoxPersonalizadoUsuario;
import main.java.classesExtras.ComboBoxRenderPersonalizado;
import main.java.classesExtras.ComboBoxRenderPersonalizadoContaBancaria;
import main.java.classesExtras.ComboBoxRenderPersonalizadoFuncionario;
import main.java.classesExtras.ComboBoxRenderPersonalizadoUsuario;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoSafras;

import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;
import javax.swing.JEditorPane;
import java.awt.Insets;
import javax.swing.JComboBox;

public class TelaSegurancaCadastroAcessoTemporario extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4439945456659904420L;
	private final JPanel painelPrincipal = new JPanel();
	private final JPanel painelOdin = new JPanel();
	private ConfiguracoesGlobais configs_globais;

	private JLabel lblTotalContratosConcluidos, lblTotalContratos, lblTotalContratosAbertos;
	private TelaSegurancaCadastroAcessoTemporario isto;
	private JDialog telaPai;
	private CadastroCliente armazem;
	private JTextFieldPersonalizado entDataInicial;
	private final JButton btnCadastrar = new JButton("Cadastrar");
	private final JPanel panel = new JPanel();
	private final JLabel lblNewLabel = new JLabel("Concessão de acesso temporário");
	private final JLabel lblCliente = new JLabel("Módulo:");
	private final JPanel panel_2 = new JPanel();
	private final JPanel panel_3 = new JPanel();
	private final JPanel panel_4 = new JPanel();
	private FinanceiroGrupoContas financeiro_grupo_contas;
	private final JLabel lblTipoDeConta = new JLabel("Usuário:");
	private final JPanel panel_1_1 = new JPanel();
	private final JComboBox cbUsuario = new JComboBox();
	private final JLabel lblIdentificador = new JLabel("Data Final:");
	private final JTextFieldPersonalizado entDataFinal = new JTextFieldPersonalizado();
	private final JLabel lblHoraInicial = new JLabel("Hora Inicial:");
	private final JTextFieldPersonalizado entHoraInicial = new JTextFieldPersonalizado();
	private final JLabel lblHoraFinal = new JLabel("Hora Final:");
	private final JTextFieldPersonalizado entHoraFinal = new JTextFieldPersonalizado();
	private final JComboBox cbModulo = new JComboBox();
	private ComboBoxPersonalizadoUsuario modelUsuarios = new ComboBoxPersonalizadoUsuario();
	private ComboBoxRenderPersonalizadoUsuario cbUsuariosPersonalizados;
	private Log GerenciadorLog;
	private CadastroLogin login;
	
	public TelaSegurancaCadastroAcessoTemporario(Window janela_pai) {

		isto = this;
		setTitle("E-Contract - Acesso Temporário");
		getDadosGlobais();
		setContentPane(painelOdin);
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 675, 359);
		painelOdin.setLayout(new BorderLayout(0, 0));
		painelOdin.add(painelPrincipal);

		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		painelPrincipal.setLayout(
				new MigLayout("", "[73px][379px][74px][8px][83px,grow][50px]", "[46px][16px][433px,grow][28px,grow]"));
		panel.setBackground(new Color(0, 51, 0));
		painelPrincipal.add(panel, "cell 0 0 5 1,grow");
		panel.setLayout(new MigLayout("", "[617px]", "[20px]"));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setForeground(Color.WHITE);

		panel.add(lblNewLabel, "cell 0 0,alignx left,aligny center");
		panel_2.setBackground(Color.WHITE);

		painelPrincipal.add(panel_2, "cell 0 2 5 1,grow");
		panel_2.setLayout(new MigLayout("", "[grow][grow][][][grow]",
				"[][33px,grow][43px,grow][29px,grow][33px,grow][33px,grow][33px,grow][33px,grow][30px,grow][30px,grow]"));
		lblTipoDeConta.setHorizontalAlignment(SwingConstants.TRAILING);
		lblTipoDeConta.setForeground(Color.BLACK);
		lblTipoDeConta.setFont(new Font("Arial", Font.PLAIN, 16));
		lblTipoDeConta.setBackground(Color.ORANGE);

		panel_2.add(lblTipoDeConta, "cell 0 0,alignx right");
		panel_1_1.setBackground(Color.WHITE);

		panel_2.add(panel_1_1, "cell 1 0 4 1,grow");
		panel_1_1.setLayout(new MigLayout("", "[grow][]", "[]"));
		cbUsuario.setFont(new Font("SansSerif", Font.BOLD, 14));

		panel_1_1.add(cbUsuario, "cell 0 0,grow");
		cbUsuariosPersonalizados = new ComboBoxRenderPersonalizadoUsuario();
		cbUsuario.setModel(modelUsuarios);
		cbUsuario.setRenderer(cbUsuariosPersonalizados);


		JLabel lblNome = new JLabel("Data Inicial:");
		panel_2.add(lblNome, "cell 0 1,alignx right,aligny center");
		lblNome.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNome.setForeground(Color.BLACK);
		lblNome.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNome.setBackground(Color.ORANGE);

		entDataInicial = new JTextFieldPersonalizado();
		panel_2.add(entDataInicial, "cell 1 1 2 1,alignx left,aligny top");
		entDataInicial.setText(new GetData().getData());
		entDataInicial.setForeground(Color.BLACK);
		lblHoraInicial.setHorizontalAlignment(SwingConstants.TRAILING);
		lblHoraInicial.setForeground(Color.BLACK);
		lblHoraInicial.setFont(new Font("Arial", Font.PLAIN, 16));
		lblHoraInicial.setBackground(Color.ORANGE);

		panel_2.add(lblHoraInicial, "cell 3 1,alignx right");

		panel_2.add(entHoraInicial, "cell 4 1,alignx left");
		entHoraInicial.setForeground(Color.black);
		lblIdentificador.setHorizontalAlignment(SwingConstants.TRAILING);
		lblIdentificador.setForeground(Color.BLACK);
		lblIdentificador.setFont(new Font("Arial", Font.PLAIN, 16));
		lblIdentificador.setBackground(Color.ORANGE);

		panel_2.add(lblIdentificador, "cell 0 2,alignx right");
		entDataFinal.setForeground(Color.black);
		entDataFinal.setText(new GetData().getData());
		panel_2.add(entDataFinal, "cell 1 2 2 1,alignx left");
		lblHoraFinal.setHorizontalAlignment(SwingConstants.TRAILING);
		lblHoraFinal.setForeground(Color.BLACK);
		lblHoraFinal.setFont(new Font("Arial", Font.PLAIN, 16));
		lblHoraFinal.setBackground(Color.ORANGE);

		panel_2.add(lblHoraFinal, "cell 3 2,alignx right");

		panel_2.add(entHoraFinal, "cell 4 2,alignx left");
		entHoraFinal.setForeground(Color.black);
		panel_2.add(lblCliente, "cell 0 3,alignx trailing,aligny center");
		lblCliente.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCliente.setForeground(Color.BLACK);
		lblCliente.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCliente.setBackground(Color.ORANGE);
		cbModulo.setFont(new Font("SansSerif", Font.BOLD, 14));
		cbModulo.addItem("Recursos Humanos");
		cbModulo.addItem("Finanças");

		panel_2.add(cbModulo, "cell 1 3 4 1,growx");
		panel_4.setBackground(new Color(0, 51, 0));

		painelPrincipal.add(panel_4, "cell 5 0 1 4,grow");
		panel_3.setBackground(Color.WHITE);

		painelPrincipal.add(panel_3, "cell 2 3 3 1,grow");
		panel_3.setLayout(new MigLayout("", "[][]", "[]"));
		btnCadastrar.setBackground(new Color(0, 51, 0));
		btnCadastrar.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnCadastrar.setForeground(Color.WHITE);
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				CadastroAcessoTemporario cad = getDadosSalvar();
				GerenciarBancoAcessoTemporario gerenciar = new GerenciarBancoAcessoTemporario();
				int inserir = gerenciar.inserir_acesso(cad);
				if(inserir > 0) {
					JOptionPane.showMessageDialog(isto, "Acesso Temporário Cadastrado!");
					((TelaSeguranca) janela_pai).pesquisar();
					isto.dispose();

					
				}else {
					JOptionPane.showMessageDialog(isto, "Erro ao Cadastrar Acesso Temporário!\nConsulte o Administrador");
				}
				
			}
		});
		panel_3.add(btnCadastrar, "cell 1 0,growx");
		pesquisar_usuarios();
		adicionarFocus(isto.getComponents());
		this.setResizable(false);
		this.setLocationRelativeTo(janela_pai);

	}
	
	public void pesquisar_usuarios() {

		modelUsuarios.resetar();
		GerenciarBancoLogin gerenciar = new GerenciarBancoLogin();

		for (CadastroLogin usuario : gerenciar.getUsuarios()) {

			modelUsuarios.addCC(usuario);

		}
	}


	public void setTelaPai(JDialog _tela_pai) {
		this.telaPai = _tela_pai;
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

	public boolean checkString(String txt) {
		return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ") && txt.length() > 0;
	}
	
	public CadastroAcessoTemporario getDadosSalvar() {
		
		CadastroAcessoTemporario cad = new CadastroAcessoTemporario();
		
		CadastroLogin usuario = (CadastroLogin) modelUsuarios.getSelectedItem();
		cad.setId_usuario_executor(usuario.getId());
		
		cad.setId_usuario_criador(login.getId());
		
		cad.setModulo(cbModulo.getSelectedIndex());
		String data_inicial, data_final, hora_inicial, hora_final;
		
		
		data_inicial = entDataInicial.getText();
		data_final = entDataFinal.getText();
		hora_inicial = entHoraInicial.getText();
		hora_final = entHoraFinal.getText();
		
		try {
			LocalDate data  = LocalDate.parse(data_inicial, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			cad.setData_inicial(data_inicial);
		}catch(Exception e) {
			JOptionPane.showMessageDialog(isto, "Data Inicial Inválida!");
			return null;
		}
		
		try {
			LocalDate data  = LocalDate.parse(data_final, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			cad.setData_final(data_final);
		}catch(Exception e) {
			JOptionPane.showMessageDialog(isto, "Data Final Inválida!");
			return null;
		}
		
		try {
			LocalTime hora_lt  = LocalTime.parse(hora_inicial, DateTimeFormatter.ofPattern("HH:mm"));
			cad.setHora_inicial(hora_inicial);
			
		}catch(Exception e) {
			JOptionPane.showMessageDialog(isto, "Hora Inicial Inválida!");
			return null;
		}
		
		
		try {
			LocalTime hora_lt  = LocalTime.parse(hora_final, DateTimeFormatter.ofPattern("HH:mm"));
			cad.setHora_final(hora_final);
			
		}catch(Exception e) {
			JOptionPane.showMessageDialog(isto, "Hora Final Inválida!");
			return null;
		}
		
		
		
		return cad;
		
		
		
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

	





public void getDadosGlobais() {
		// gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		GerenciadorLog = dados.getGerenciadorLog();
		configs_globais = dados.getConfigs_globais();
		// usuario logado
		login = dados.getLogin();
		
	}

}
