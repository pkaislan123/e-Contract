
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
import main.java.cadastros.CadastroFuncionarioSalarioMinimo;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroContrato.CadastroPagamento;
import main.java.cadastros.CadastroContrato.CadastroPagamentoContratual;
import main.java.cadastros.CadastroContrato.Recebimento;
import main.java.cadastros.CadastroFuncionarioSalarioMinimo;
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
import main.java.conexaoBanco.GerenciarBancoFuncionariosCalculos;
import main.java.conexaoBanco.GerenciarBancoCartaoPonto;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoFuncionarios;
import main.java.conexaoBanco.GerenciarBancoNotasFiscais;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoRomaneios;
import main.java.conexaoBanco.GerenciarBancoSafras;
import main.java.conexaoBanco.GerenciarBancoSalarioMinimo;
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
import javax.swing.JTextField;

public class TelaFuncionariosCadastroSalarioMinimo extends JFrame {

	private ComboBoxPersonalizadoContratoTrabalho modelContratos = new ComboBoxPersonalizadoContratoTrabalho();

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

	CadastroFuncionarioSalarioMinimo funcionario_cadastrar = new CadastroFuncionarioSalarioMinimo();
	CadastroFuncionarioSalarioMinimo funcionario_atualizar = new CadastroFuncionarioSalarioMinimo();
	private JDialog telaPai;

	private JPanel painelCentral = new JPanel();
	private TelaFuncionariosCadastroSalarioMinimo isto;
	private CadastroFuncionarioSalarioMinimo desconto_selecinado;

	private CadastroFuncionarioSalarioMinimo salario_local;
	private JTextField entValor;
	private JTextField entDescricao;
	private JTextField entDataInicial;
	private JTextField entDataFinal;

	public TelaFuncionariosCadastroSalarioMinimo(int modo_operacao, CadastroFuncionarioSalarioMinimo salario_minimo,
			Window janela_pai) {

		salario_local = salario_minimo;
		this.setContentPane(painelDadosIniciais);

		setForeground(new Color(255, 255, 255));

		getDadosGlobais();
		// setAlwaysOnTop(true);

		// setModal(true);
		isto = this;

		setResizable(false);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setBounds(100, 100, 484, 281);

		// configuracao de paineis
		// painel pai

		painelDadosIniciais.setBackground(Color.WHITE);

		// adiciona o painel filho1 no painel principal

		painelDadosIniciais.setLayout(new BorderLayout(0, 0));

		JPanel panel_3 = new JPanel();
		painelDadosIniciais.add(panel_3);
		panel_3.setBackground(Color.WHITE);
		panel_3.setLayout(
				new MigLayout("", "[62px][grow][grow][grow][grow][2px][57px][1px][50px][10px][415px,grow][][][][][]",
						"[27px][][][][50px:n][33px][][][33px][33px][30px][][36px][][][]"));

		JLabel lblCpf = new JLabel("Valor:");
		panel_3.add(lblCpf, "cell 0 1,alignx trailing,aligny center");
		lblCpf.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCpf.setForeground(Color.BLACK);
		lblCpf.setFont(new Font("Arial", Font.PLAIN, 14));
		lblCpf.setBackground(Color.ORANGE);
		lblCpf.setHorizontalAlignment(JLabel.RIGHT);

		entValor = new JTextField();
		entValor.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_3.add(entValor, "cell 1 1 10 1,growx");
		entValor.setColumns(10);

		JLabel lblDataInicial = new JLabel("Data Inicial:");
		lblDataInicial.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDataInicial.setForeground(Color.BLACK);
		lblDataInicial.setFont(new Font("Arial", Font.PLAIN, 14));
		lblDataInicial.setBackground(Color.ORANGE);
		panel_3.add(lblDataInicial, "cell 0 2,alignx trailing");

		entDataInicial = new JTextField();
		entDataInicial.setFont(new Font("SansSerif", Font.BOLD, 14));
		entDataInicial.setColumns(10);
		panel_3.add(entDataInicial, "cell 1 2 10 1,growx");

		JLabel lblDataFinal = new JLabel("Data Final:");
		lblDataFinal.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDataFinal.setForeground(Color.BLACK);
		lblDataFinal.setFont(new Font("Arial", Font.PLAIN, 14));
		lblDataFinal.setBackground(Color.ORANGE);
		panel_3.add(lblDataFinal, "cell 0 3,alignx trailing");

		entDataFinal = new JTextField();
		entDataFinal.setFont(new Font("SansSerif", Font.BOLD, 14));
		entDataFinal.setColumns(10);
		panel_3.add(entDataFinal, "cell 1 3 10 1,growx");

		JLabel lblDescrio = new JLabel("Descrição:");
		lblDescrio.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDescrio.setForeground(Color.BLACK);
		lblDescrio.setFont(new Font("Arial", Font.PLAIN, 14));
		lblDescrio.setBackground(Color.ORANGE);
		panel_3.add(lblDescrio, "cell 0 4,alignx trailing");

		entDescricao = new JTextField();
		entDescricao.setFont(new Font("SansSerif", Font.BOLD, 14));
		entDescricao.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_3.add(entDescricao, "cell 1 4 15 3,grow");
		entDescricao.setColumns(10);

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				GerenciarBancoSalarioMinimo gerenciar = new GerenciarBancoSalarioMinimo();
				CadastroFuncionarioSalarioMinimo minimo = getDadosSalvar();
				if (minimo != null) {
					int inseriu_relacao = gerenciar.inserir_salario_minimo(minimo);
					if (inseriu_relacao > 0) {
						JOptionPane.showMessageDialog(isto, "Salário Minímo Inserido");
						((TelaFuncionariosSalarioMinimo) janela_pai).pesquisar();
						isto.dispose();
					} else {
						JOptionPane.showMessageDialog(isto, "Erro ao inserir Salário Minimo\nConsulte o administrador");

					}
				}
			}
		});
		btnSalvar.setForeground(Color.WHITE);
		btnSalvar.setBackground(new Color(0, 51, 0));
		btnSalvar.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_3.add(btnSalvar, "cell 10 7,alignx right");

		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GerenciarBancoSalarioMinimo gerenciar = new GerenciarBancoSalarioMinimo();
				CadastroFuncionarioSalarioMinimo minimo = getDadosAtualizar(salario_local);
				if (minimo != null) {
					boolean inseriu_relacao = gerenciar.atualizarSalarioMinimo(minimo);
					if (inseriu_relacao) {
						JOptionPane.showMessageDialog(isto, "Salário Minímo Atualizado");
						((TelaFuncionariosSalarioMinimo) janela_pai).pesquisar();
						isto.dispose();
					} else {
						JOptionPane.showMessageDialog(isto, "Erro ao Atualizar Salário Minimo\nConsulte o administrador");

					}
				}	
				
				
			}
		});
		btnAtualizar.setForeground(Color.WHITE);
		btnAtualizar.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnAtualizar.setBackground(new Color(0, 0, 153));
		panel_3.add(btnAtualizar, "cell 11 7");
		painelCentral.setLayout(new BorderLayout(0, 0));

		// configura widgets no painel finalizar

		if (modo_operacao == 0) {
			this.setTitle("Cadastro Novo Salário Mínimo");
			btnAtualizar.setEnabled(false);
			btnAtualizar.setVisible(false);
		} else if (modo_operacao == 1) {
			this.setTitle("Edição Salário Mínimo");
			btnSalvar.setEnabled(false);
			btnSalvar.setVisible(false);
			rotinasEdicao();
		}
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

	public void rotinasEdicao() {
		entDescricao.setText(salario_local.getDescricao());
		entValor.setText(Double.toString(salario_local.getValor()));
		entDataInicial.setText(salario_local.getData_inicial());
		
		entDataFinal.setText(salario_local.getData_final());

	}
	

	public CadastroFuncionarioSalarioMinimo getDadosSalvar() {

		CadastroFuncionarioSalarioMinimo salario = new CadastroFuncionarioSalarioMinimo();
		
		String data_inicial, data_final, descricao;
		double valor;
		
		
		try {
			valor = Double.parseDouble(entValor.getText());
			salario.setValor(valor);
		}catch(Exception e) {
			JOptionPane.showMessageDialog(isto, "Valor Inválido!");
			return null;
		}

		data_inicial = entDataInicial.getText();
		data_final = entDataFinal.getText();
		 descricao = entDescricao.getText();
		
		 try {

				if (!isDateValid(data_inicial)) {
					JOptionPane.showMessageDialog(isto, "Data Inicial Inválida");

					return null;
				} else {
					salario.setData_inicial(data_inicial);

				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(isto, "Data Inicial Inválida");
				return null;
			}
		 
		 try {

				if (!isDateValid(data_final)) {
					JOptionPane.showMessageDialog(isto, "Data Final Inválida");

					return null;
				} else {
					salario.setData_final(data_final);

				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(isto, "Data Final Inválida");
				return null;
			}
		 
		 salario.setDescricao(descricao);
		 
		 return salario;
		 

	}
	
	
	public CadastroFuncionarioSalarioMinimo getDadosAtualizar(CadastroFuncionarioSalarioMinimo salario_antigo) {

		
		CadastroFuncionarioSalarioMinimo salario = new CadastroFuncionarioSalarioMinimo();
		salario.setId(salario_antigo.getId());
		String data_inicial, data_final, descricao;
		double valor;
		
		
		try {
			valor = Double.parseDouble(entValor.getText());
			salario.setValor(valor);
		}catch(Exception e) {
			JOptionPane.showMessageDialog(isto, "Valor Inválido!");
			return null;
		}

		data_inicial = entDataInicial.getText();
		data_final = entDataFinal.getText();
		 descricao = entDescricao.getText();
		
		 try {

				if (!isDateValid(data_inicial)) {
					JOptionPane.showMessageDialog(isto, "Data Inicial Inválida");

					return null;
				} else {
					salario.setData_inicial(data_inicial);

				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(isto, "Data Inicial Inválida");
				return null;
			}
		 
		 try {

				if (!isDateValid(data_final)) {
					JOptionPane.showMessageDialog(isto, "Data Final Inválida");

					return null;
				} else {
					salario.setData_final(data_final);

				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(isto, "Data Final Inválida");
				return null;
			}
		 
		 salario.setDescricao(descricao);
		 
		 return salario;
		 

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
