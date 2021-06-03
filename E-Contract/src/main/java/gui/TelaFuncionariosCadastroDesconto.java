
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
import main.java.cadastros.CadastroFuncionarioDescontos;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.classesExtras.Endereco;
import main.java.conexaoBanco.GerenciarBancoFuncionarios;
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

public class TelaFuncionariosCadastroDesconto extends JFrame {

	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;

	private final JPanelBackground contentPanel = new JPanelBackground();

	private String logradouro, bairro, cidade, estado;
	private int num, cep;
	private JTextFieldPersonalizado entReferencia, entPorcentagem, entValor;

	private String uf;
	private JTextFieldPersonalizado entDescricao;
	private String tipoIdentificacao;

	private DefaultTableModel modelo = new DefaultTableModel();
	private DefaultTableModel modelo_cb = new DefaultTableModel();

	ArrayList<Integer> contatos_excluir = new ArrayList<>();
	ArrayList<Integer> contas_excluir = new ArrayList<>();

	CadastroFuncionario funcionario_cadastrar = new CadastroFuncionario();
	CadastroFuncionario funcionario_atualizar = new CadastroFuncionario();
	private JDialog telaPai;
	private TelaFuncionariosCadastroDesconto isto;

	
	
	public TelaFuncionariosCadastroDesconto(int flag_tipo_tela, CadastroFuncionarioDescontos desconto, Window janela_pai) {
		
		
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
		
		setBounds(100, 100, 405, 260);
				getContentPane().setLayout(new MigLayout("", "[80px][176px,grow][]", "[30px][19px][][][][]"));
		
				JLabel lblCpf = new JLabel("Descrição:");
				getContentPane().add(lblCpf, "cell 0 0,alignx right,aligny center");
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

		modelo.addColumn("id");
		modelo.addColumn("Nome");
		modelo.addColumn("Cargo");
		modelo.addColumn("Celular");
		modelo.addColumn("Fixo");
		modelo.addColumn("E-mail");
		modelo.addColumn("Descrição");
		modelo.addColumn("Observação");
		modelo.setNumRows(0);
		
				entDescricao = new JTextFieldPersonalizado();
				getContentPane().add(entDescricao, "cell 1 0 2 1,growx,aligny top");
				entDescricao.setForeground(Color.BLACK);
				entDescricao.setFont(new Font("Arial", Font.BOLD, 20));
				entDescricao.setToolTipText("Data de Nascimento, somente números");
				entDescricao.setColumns(10);
				
				JLabel lblReferncia = new JLabel("Referência:");
				lblReferncia.setHorizontalAlignment(SwingConstants.TRAILING);
				lblReferncia.setForeground(Color.BLACK);
				lblReferncia.setFont(new Font("Arial", Font.PLAIN, 16));
				lblReferncia.setBackground(Color.ORANGE);
				getContentPane().add(lblReferncia, "cell 0 1,growx,aligny center");
				
				
				
				 entReferencia = new JTextFieldPersonalizado();
				entReferencia.setForeground(Color.black);
				getContentPane().add(entReferencia, "cell 1 1,growx");
				
				JLabel lblPorcentagem = new JLabel("Porcentagem:");
				lblPorcentagem.setHorizontalAlignment(SwingConstants.TRAILING);
				lblPorcentagem.setForeground(Color.BLACK);
				lblPorcentagem.setFont(new Font("Arial", Font.PLAIN, 16));
				lblPorcentagem.setBackground(Color.ORANGE);
				getContentPane().add(lblPorcentagem, "cell 0 2,alignx trailing");
				
				 entPorcentagem = new JTextFieldPersonalizado();
				 entPorcentagem.setText("0");
				entPorcentagem.setForeground(Color.black);
				getContentPane().add(entPorcentagem, "cell 1 2,growx");
				
				JLabel lblValor = new JLabel("Valor:");
				lblValor.setHorizontalAlignment(SwingConstants.TRAILING);
				lblValor.setForeground(Color.BLACK);
				lblValor.setFont(new Font("Arial", Font.PLAIN, 16));
				lblValor.setBackground(Color.ORANGE);
				getContentPane().add(lblValor, "cell 0 3,alignx trailing");
				
				 entValor = new JTextFieldPersonalizado();
				entValor.setForeground(Color.black);
				entValor.setText("0");

				getContentPane().add(entValor, "cell 1 3,growx");
				
				JButton btnAtualizar = new JButton("Atualizar");
				btnAtualizar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						
						CadastroFuncionarioDescontos desconto_atualizar = new CadastroFuncionarioDescontos();
						desconto_atualizar = getDadosAtualizar(desconto);
						if(desconto_atualizar != null) {
							GerenciarBancoFuncionariosDescontos gerenciar = new GerenciarBancoFuncionariosDescontos();
							boolean atualizou = gerenciar.atualizarDesconto(desconto_atualizar);
							if(atualizou) {
								JOptionPane.showMessageDialog(isto, "Desconto Atualizado!");
								((TelaFuncionariosDescontos) janela_pai).pesquisar();
								isto.dispose();
							}else {
								JOptionPane.showMessageDialog(isto, "Erro ao atualizar o Desconto\nConsulte o administrador!");
							}
							
						}
						
						
					}
				});
				btnAtualizar.setBackground(new Color(0, 0, 102));
				btnAtualizar.setForeground(Color.WHITE);
				btnAtualizar.setFont(new Font("Dialog", Font.BOLD, 16));
				getContentPane().add(btnAtualizar, "cell 1 5,alignx right");
				
				JButton btnSalvar = new JButton("Salvar");
				btnSalvar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						CadastroFuncionarioDescontos desconto = new CadastroFuncionarioDescontos();
						desconto = getDadosSalvar();
						if(desconto != null) {
							GerenciarBancoFuncionariosDescontos gerenciar = new GerenciarBancoFuncionariosDescontos();
							int cadastrou = gerenciar.inserirdesconto(desconto);
							if(cadastrou > 0) {
								JOptionPane.showMessageDialog(isto, "Desconto Inserido!");
								((TelaFuncionariosDescontos) janela_pai).pesquisar();
								isto.dispose();
							}else {
								JOptionPane.showMessageDialog(isto, "Erro ao inserir Desconto\nConsulte o administrador!");
							}
							
						}
						
					}
				});
				btnSalvar.setBackground(new Color(0, 51, 0));
				btnSalvar.setForeground(Color.WHITE);
				btnSalvar.setFont(new Font("Dialog", Font.BOLD, 16));
				getContentPane().add(btnSalvar, "cell 2 5");

				if (flag_tipo_tela == 0) {
					setTitle("E-Contract - Inserção de Desconto");

					
					btnAtualizar.setEnabled(false);
					btnAtualizar.setVisible(false);
					
				}
				else if (flag_tipo_tela == 1) {
					setTitle("E-Contract - Edição de Desconto");
					
					btnSalvar.setEnabled(false);
					btnSalvar.setVisible(false);
					rotinasEdicao(desconto);
					
				}

	
		adicionarFocus(isto.getComponents());

		this.setLocationRelativeTo(janela_pai);

	}

	
	private CadastroFuncionarioDescontos getDadosSalvar() {
		CadastroFuncionarioDescontos cad = new CadastroFuncionarioDescontos();
		
		String descricao, referencia;
		double porcentagem, valor;
		
		
		descricao = entDescricao.getText();
		referencia  = entReferencia.getText();
		
		
		cad.setDescricao(descricao);
		cad.setReferencia(referencia);
		
		try {
			
			porcentagem = Double.parseDouble(entPorcentagem.getText());
			cad.setPorcentagem(porcentagem);
			
		
			
			valor = Double.parseDouble(entValor.getText());
			cad.setValor(valor);
			
			return cad;
			
		}catch (Exception e) {
			return null;
		}
		
		
		
	}

	
	private CadastroFuncionarioDescontos getDadosAtualizar(CadastroFuncionarioDescontos desconto_antigo) {
		CadastroFuncionarioDescontos cad = new CadastroFuncionarioDescontos();
		cad.setId_desconto(desconto_antigo.getId_desconto());
		
		String descricao, referencia;
		double porcentagem, valor;
		
		
		descricao = entDescricao.getText();
		referencia  = entReferencia.getText();
		
		
		cad.setDescricao(descricao);
		cad.setReferencia(referencia);
		
		try {
			
			porcentagem = Double.parseDouble(entPorcentagem.getText());
			cad.setPorcentagem(porcentagem);
			
		
			
			valor = Double.parseDouble(entValor.getText());
			cad.setValor(valor);
			
			return cad;
			
		}catch (Exception e) {
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

	public void setTelaPai(JDialog tela_pai) {
		this.telaPai = tela_pai;
	}
	
	public void rotinasEdicao(CadastroFuncionarioDescontos cad) {
		
		entDescricao.setText(cad.getDescricao());
		entReferencia.setText(cad.getReferencia());
		
		entValor.setText(Double.toString(cad.getValor()));
		entPorcentagem.setText(Double.toString(cad.getPorcentagem()));
		
		
	}
	

}
