
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
import main.java.cadastros.CadastroFuncionarioCalculo;
import main.java.cadastros.CadastroFuncionarioDepartamentos;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.classesExtras.Endereco;
import main.java.conexaoBanco.GerenciarBancoFuncionarios;
import main.java.conexaoBanco.GerenciarBancoFuncionariosCalculos;
import main.java.conexaoBanco.GerenciarBancoFuncionariosDepartamentos;
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
import javax.swing.JRadioButton;
import javax.swing.JTextPane;

public class TelaFuncionariosCadastroDepartamentos extends JFrame {

	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;

	private final JPanelBackground contentPanel = new JPanelBackground();
	private JTextPane entDescricao;
	private String uf;
	private JTextFieldPersonalizado entNome;
	private String tipoIdentificacao;

	private TelaFuncionariosCadastroDepartamentos isto;

	public TelaFuncionariosCadastroDepartamentos(Window janela_pai) {

		getContentPane().setBackground(Color.WHITE);
		getContentPane().setFont(new Font("Arial", Font.BOLD, 18));
		getContentPane().setForeground(Color.WHITE);
		setFont(new Font("Arial", Font.BOLD, 18));
		setForeground(new Color(255, 255, 255));

		getDadosGlobais();
		// setAlwaysOnTop(true);

		// setModal(true);
		isto = this;

		setResizable(false);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setBounds(100, 100, 578, 244);
		getContentPane().setLayout(new MigLayout("", "[80px][176px,grow][][]", "[][50px,grow][19px][][][][][][][]"));

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNome.setForeground(Color.BLACK);
		lblNome.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNome.setBackground(Color.ORANGE);
		getContentPane().add(lblNome, "cell 0 0,alignx right");

		entNome = new JTextFieldPersonalizado();
		getContentPane().add(entNome, "cell 1 0,growx,aligny top");
		entNome.setForeground(Color.BLACK);
		entNome.setFont(new Font("Arial", Font.BOLD, 20));
		entNome.setColumns(10);

		JLabel lblCpf = new JLabel("Descrição:");
		getContentPane().add(lblCpf, "cell 0 1,alignx right,aligny center");
		lblCpf.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCpf.setForeground(Color.BLACK);
		lblCpf.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCpf.setBackground(Color.ORANGE);
		lblCpf.setHorizontalAlignment(JLabel.RIGHT);

		JLabel lblCadastro_2_1 = new JLabel(" ----- Cadastro / Dados da Empresa");
		lblCadastro_2_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCadastro_2_1.setForeground(Color.BLACK);
		lblCadastro_2_1.setFont(new Font("Arial", Font.PLAIN, 14));
		lblCadastro_2_1.setBackground(Color.ORANGE);
		lblCadastro_2_1.setBounds(0, 0, 250, 33);
		lblCadastro_2_1.setHorizontalAlignment(JLabel.LEFT);

		entDescricao = new JTextPane();
		entDescricao.setFont(new Font("SansSerif", Font.BOLD, 14));
		entDescricao.setBorder(new LineBorder(new Color(0, 0, 0)));
		getContentPane().add(entDescricao, "cell 1 1 1 4,grow");

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				CadastroFuncionarioDepartamentos cad = new CadastroFuncionarioDepartamentos();
				cad = getDadosSalvar();
				if (cad != null) {
					GerenciarBancoFuncionariosDepartamentos gerenciar = new GerenciarBancoFuncionariosDepartamentos();
					int inserir = gerenciar.inserirDepartamento(cad);
					if(inserir > 0) {
						JOptionPane.showMessageDialog(isto, "Departamento Inserido!");
						((TelaFuncionariosDepartamentos) janela_pai).pesquisar();
						isto.dispose();

					}else {
						JOptionPane.showMessageDialog(isto, "Erro ao inserir o Departamento\nConsulte o administrador!");

					}
					
				}

			}
		});
		btnSalvar.setBackground(new Color(0, 51, 0));
		btnSalvar.setForeground(Color.WHITE);
		btnSalvar.setFont(new Font("Dialog", Font.BOLD, 16));

		getContentPane().add(btnSalvar, "cell 1 8,alignx right");

		adicionarFocus(isto.getComponents());

		this.setLocationRelativeTo(janela_pai);

	}

	private CadastroFuncionarioDepartamentos getDadosSalvar() {
		CadastroFuncionarioDepartamentos cad = new CadastroFuncionarioDepartamentos();

		String nome, descricao;

		nome = entNome.getText();
		descricao = entDescricao.getText();

		try {

			if (nome.length() > 7) {
				cad.setNome(nome);
			} else {
				JOptionPane.showMessageDialog(isto, "Nome muito curto");
				return null;
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(isto, "Nome Inválido");
			return null;
		}

		try {

			if (descricao.length() > 7) {
				cad.setDescricao(descricao);

				return cad;
			} else {
				JOptionPane.showMessageDialog(isto, "Descrição muito curta");
				return null;
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(isto, "Descrição Inválida");
			return null;
		}

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

}
