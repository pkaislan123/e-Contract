

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
import main.java.cadastros.FuncionarioContaAssociada;
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
import main.java.conexaoBanco.GerenciarBancoFuncionariosCalculos;
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
import main.java.cadastros.CartaoPontoCompleto;
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

public class TelaFuncionariosAssosiacaoFuncionarioCliente extends JFrame {
	
	private ComboBoxPersonalizadoContratoTrabalho modelContratos = new ComboBoxPersonalizadoContratoTrabalho();

	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private final JPanelBackground contentPanel = new JPanelBackground();
	private CadastroCliente cliente;

	// painel pai

	// painel filho1JPanelTransparent
	private JPanelBackground painelDadosIniciais = new JPanelBackground();


	private JDialog telaPai;

	private JPanel painelCentral = new JPanel();
	private TelaFuncionariosAssosiacaoFuncionarioCliente isto;
	private JComboBox cBCliente;
	
	private CadastroFuncionario funcionario_local;
	
	public TelaFuncionariosAssosiacaoFuncionarioCliente(CadastroFuncionario funcionario, Window janela_pai) {

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

		setBounds(100, 100, 484, 132);

		// configuracao de paineis
		// painel pai

		painelDadosIniciais.setBackground(Color.WHITE);

		// adiciona o painel filho1 no painel principal

	
		painelDadosIniciais.setLayout(new BorderLayout(0, 0));

		JPanel panel_3 = new JPanel();
		painelDadosIniciais.add(panel_3);
		panel_3.setBackground(Color.WHITE);
		panel_3.setLayout(new MigLayout("", "[62px][2px][57px][1px][50px][10px][415px,grow][][][][]", "[27px][][33px][][33px][33px][30px][][36px][][][]"));

		JLabel lblCpf = new JLabel("Cliente:");
		panel_3.add(lblCpf, "cell 0 1,alignx right,aligny center");
		lblCpf.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCpf.setForeground(Color.BLACK);
		lblCpf.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCpf.setBackground(Color.ORANGE);
		lblCpf.setHorizontalAlignment(JLabel.RIGHT);


		cBCliente = new JComboBox();
		
		
		panel_3.add(cBCliente, "cell 1 1 6 1,grow");
		cBCliente.setFont(new Font("Arial", Font.BOLD, 14));
		
				JButton btnSelecionar_1 = new JButton("Selecionar");
				btnSelecionar_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						TelaCliente tela = new TelaCliente(0, 61, isto);
						tela.setVisible(true);
						
					}
				});
				btnSelecionar_1.setForeground(Color.WHITE);
				btnSelecionar_1.setFont(new Font("SansSerif", Font.BOLD, 16));
				btnSelecionar_1.setBackground(new Color(0, 0, 102));
				panel_3.add(btnSelecionar_1, "cell 7 1");
		
				JButton btnSalvar = new JButton("Salvar");
				btnSalvar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						GerenciarBancoFuncionarios gerenciar = new GerenciarBancoFuncionarios();
						FuncionarioContaAssociada fca = getDadosSalvar(funcionario.getId_funcionario());
						if(fca != null) {
							boolean inseriu_relacao = gerenciar.inserirFuncionarioContaAssociada(funcionario.getId_funcionario(), fca.getCliente().getId());
							if(inseriu_relacao) {
								JOptionPane.showMessageDialog(isto, "Cliente Associado ao Funcionário");
								((TelaGerenciarFuncionario) janela_pai).pesquisar_contas_associadas();
								isto.dispose();
							}else {
								JOptionPane.showMessageDialog(isto, "Erro ao Criar Relação Funcionário Cliente\nConsulte o administrador");

							}
						}
					}
				});
				btnSalvar.setForeground(Color.WHITE);
				btnSalvar.setBackground(new Color(0, 51, 0));
				btnSalvar.setFont(new Font("SansSerif", Font.BOLD, 16));
				panel_3.add(btnSalvar, "cell 6 3,alignx right");
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
	
	public void setCliente(CadastroCliente _cliente) {
		
		cliente = _cliente;
		cBCliente.removeAllItems();
		String nome_cliente= "";
		
		if(cliente.getTipo_pessoa() == 0) {
			nome_cliente = cliente.getNome_empresarial();
		}else
			nome_cliente = cliente.getNome_fantaia();
		
		cBCliente.addItem(nome_cliente);
		
	}
	
	
	public FuncionarioContaAssociada getDadosSalvar(int id_funcionario) {
		
		FuncionarioContaAssociada fca = new FuncionarioContaAssociada();
		
		if(cliente != null) {
			fca.setCliente(cliente);
			fca.setId_funcionario(id_funcionario);
			return fca;
		}else {
			JOptionPane.showMessageDialog(isto, "Selecione o Cliente");
			return null;
		}
		
		
	}

}

