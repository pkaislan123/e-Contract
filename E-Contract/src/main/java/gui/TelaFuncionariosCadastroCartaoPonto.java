

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
import main.java.cadastros.CalculoCompleto;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.classesExtras.CombBoxRenderPersonalizadoContratoTrabalho;
import main.java.classesExtras.ComboBoxPersonalizado;
import main.java.classesExtras.ComboBoxPersonalizadoContratoTrabalho;
import main.java.classesExtras.ComboBoxRenderPersonalizado;
import main.java.classesExtras.Endereco;
import main.java.conexaoBanco.GerenciarBancoFuncionarios;
import main.java.conexaoBanco.GerenciarBancoFuncionariosContratoTrabalho;
import main.java.conexaoBanco.GerenciarBancoCartaoPonto;
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
import main.java.cadastros.CartaoPonto;
import main.java.outros.DadosGlobais;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
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

public class TelaFuncionariosCadastroCartaoPonto extends JFrame {
	
	private ComboBoxPersonalizadoContratoTrabalho modelContratos = new ComboBoxPersonalizadoContratoTrabalho();

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
	private TelaFuncionariosCadastroCartaoPonto isto;
	
	private CombBoxRenderPersonalizadoContratoTrabalho cbContratosPersonalizados;
	private CadastroFuncionario funcionario_local;
	private JTextField entUid;
	
	public TelaFuncionariosCadastroCartaoPonto(Window janela_pai) {

		this.setContentPane(painelDadosIniciais);

		setForeground(new Color(255, 255, 255));
		this.setTitle("Cadastro Cartão de ponto");

		getDadosGlobais();
		// setAlwaysOnTop(true);

		// setModal(true);
		isto = this;

		setResizable(false);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setBounds(100, 100, 330, 129);

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
		panel_3.setLayout(new MigLayout("", "[62px][2px][57px,grow][1px][50px][10px][415px,grow][][][][]", "[27px][][][33px][33px][33px][30px][][36px][][][]"));

		JLabel lblCpf = new JLabel("UID:");
		panel_3.add(lblCpf, "cell 0 1,alignx right,aligny center");
		lblCpf.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCpf.setForeground(Color.BLACK);
		lblCpf.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCpf.setBackground(Color.ORANGE);
		lblCpf.setHorizontalAlignment(JLabel.RIGHT);

		cbContratosPersonalizados = new CombBoxRenderPersonalizadoContratoTrabalho();
		
		entUid = new JTextField();
		panel_3.add(entUid, "cell 2 1 9 1,growx");
		entUid.setColumns(10);
		
				JButton btnSalvar = new JButton("Salvar");
				btnSalvar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						GerenciarBancoCartaoPonto gerenciar = new GerenciarBancoCartaoPonto();
						CartaoPonto cartao = getDadosSalvar();
						if(cartao != null) {
							int inserir = gerenciar.inserircartao(cartao);
							if(inserir > 0) {
								JOptionPane.showMessageDialog(isto, "Cartão Inserido");
								((TelaFuncionariosCartoesPonto) janela_pai).pesquisar();
								isto.dispose();
							}else {
								JOptionPane.showMessageDialog(isto, "Erro ao inserir o Cartão\nConsulte o administrador");

							}
						}
					}
				});
				btnSalvar.setForeground(Color.WHITE);
				btnSalvar.setBackground(new Color(0, 51, 0));
				btnSalvar.setFont(new Font("SansSerif", Font.BOLD, 16));
				panel_3.add(btnSalvar, "cell 6 2,alignx right");
		painelCentral.setLayout(new BorderLayout(0, 0));

		// configura widgets no painel finalizar

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
	
	
	
	
	
	public CartaoPonto getDadosSalvar() {
		
		CartaoPonto ct = new CartaoPonto();
		ct.setUid(entUid.getText());
		return ct;
	}

}