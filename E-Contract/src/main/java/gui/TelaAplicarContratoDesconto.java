
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
import main.java.cadastros.DescontoCompleto;
import main.java.cadastros.CadastroFuncionarioDescontos;
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

public class TelaAplicarContratoDesconto extends JFrame {
	
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
	private TelaAplicarContratoDesconto isto;
	private JComboBox cbContratoTrabalho;
	private CadastroFuncionarioDescontos desconto_selecinado;
	private JComboBox cbDesconto;
	
	private CombBoxRenderPersonalizadoContratoTrabalho cbContratosPersonalizados;
	private CadastroFuncionario funcionario_local;
	
	public TelaAplicarContratoDesconto(CadastroFuncionario funcionario, Window janela_pai) {

		funcionario_local = funcionario;
		this.setContentPane(painelDadosIniciais);

		setForeground(new Color(255, 255, 255));
		this.setTitle("Aplicar Desconto");

		getDadosGlobais();
		// setAlwaysOnTop(true);

		// setModal(true);
		isto = this;

		setResizable(false);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setBounds(100, 100, 762, 190);

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
		panel_3.setLayout(new MigLayout("", "[62px][2px][57px][1px][50px][10px][415px,grow][][][][]",
				"[27px][][33px][33px][33px][30px][][36px][][][]"));

		JLabel lblCpf = new JLabel("Contrato de Trabalho:");
		panel_3.add(lblCpf, "cell 0 1,alignx right,aligny center");
		lblCpf.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCpf.setForeground(Color.BLACK);
		lblCpf.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCpf.setBackground(Color.ORANGE);
		lblCpf.setHorizontalAlignment(JLabel.RIGHT);

		cbContratosPersonalizados = new CombBoxRenderPersonalizadoContratoTrabalho();

		cbContratoTrabalho = new JComboBox();
		cbContratoTrabalho.setModel(modelContratos);
		cbContratoTrabalho.setRenderer(cbContratosPersonalizados);
		
		
		panel_3.add(cbContratoTrabalho, "cell 1 1 6 1,growx,aligny top");
		cbContratoTrabalho.setFont(new Font("Arial", Font.BOLD, 14));

		JLabel lblCargo = new JLabel("Desconto:");
		lblCargo.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCargo.setForeground(Color.BLACK);
		lblCargo.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCargo.setBackground(Color.ORANGE);
		panel_3.add(lblCargo, "cell 0 2,alignx right,aligny center");

		 cbDesconto = new JComboBox();
		cbDesconto.setFont(new Font("Arial", Font.BOLD, 14));
		panel_3.add(cbDesconto, "cell 1 2 6 1,growx");

		JButton btnSelecionar_1 = new JButton("Selecionar");
		btnSelecionar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				TelaFuncionariosDescontos tela = new TelaFuncionariosDescontos(1, isto);
				tela.setVisible(true);
				
			}
		});
		btnSelecionar_1.setForeground(Color.WHITE);
		btnSelecionar_1.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnSelecionar_1.setBackground(new Color(0, 0, 102));
		panel_3.add(btnSelecionar_1, "cell 7 2");

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				GerenciarBancoFuncionariosDescontos gerenciar = new GerenciarBancoFuncionariosDescontos();
				DescontoCompleto desc = getDadosSalvar();
				if(desc != null) {
					boolean inseriu_relacao = gerenciar.inserirRelacaoContratoDesconto(desc);
					if(inseriu_relacao) {
						JOptionPane.showMessageDialog(isto, "Desconto Aplicado");
						((TelaGerenciarFuncionario) janela_pai).pesquisar_descontos_por_contrato();
						isto.dispose();
					}else {
						JOptionPane.showMessageDialog(isto, "Erro ao Aplicar o Desconto\nConsulte o administrador");

					}
				}
			}
		});
		btnSalvar.setForeground(Color.WHITE);
		btnSalvar.setBackground(new Color(0, 51, 0));
		btnSalvar.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_3.add(btnSalvar, "cell 6 6,alignx right");
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
	
	
	public void setDesconto(CadastroFuncionarioDescontos desc) {
		this.desconto_selecinado = desc;
		
		cbDesconto.removeAllItems();
		cbDesconto.addItem(desc.getDescricao() + " - " + desc.getReferencia());
	}
	
	
	public void pesquisar_contratos() {
		
 	GerenciarBancoFuncionariosContratoTrabalho gerenciar  = new GerenciarBancoFuncionariosContratoTrabalho();
    	
    	ArrayList<CadastroFuncionarioAdmissao> lista_contratos = gerenciar.getcontratosPorColaborador(funcionario_local.getId_funcionario());
    	
    	for(CadastroFuncionarioAdmissao cad : lista_contratos) {
    		modelContratos.addCC(cad);
    	}
    	
		

	}
	
	
	public DescontoCompleto getDadosSalvar() {
		
		DescontoCompleto desc = new DescontoCompleto();
		try {
		CadastroFuncionarioAdmissao contrato = (CadastroFuncionarioAdmissao) modelContratos.getSelectedItem();

		if(contrato != null) {
			desc.setId_contrato_trabalho(contrato.getId_contrato());
		}else{
			return null;
		}
		
		}catch(Exception e) {
			return null;
		}
		
		try {
			
			if(desconto_selecinado != null) {
				desc.setId_desconto(desconto_selecinado.getId_desconto());
				return desc;
			}else {
				return null;
			}
			
			 
			
		}catch (Exception t){
			return null;
		}
		
		
		
	}

}
