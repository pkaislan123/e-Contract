
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
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.classesExtras.Endereco;
import main.java.conexaoBanco.GerenciarBancoFuncionarios;
import main.java.conexaoBanco.GerenciarBancoFuncionariosContratoTrabalho;
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

public class TelaFuncionariosCadastroContratoTrabalho extends JFrame {

	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private double salario_minimo = 0;

	private final JPanelBackground contentPanel = new JPanelBackground();

	// painel pai

	// painel filho1JPanelTransparent
	private JPanelBackground painelDadosIniciais = new JPanelBackground();

	

	private JTextFieldPersonalizado entCargo, entFuncao,entSalario;

	ArrayList<Integer> descontos_a_excluir = new ArrayList<>();
	ArrayList<Integer> acrescimos_a_excluir = new ArrayList<>();

	CadastroFuncionario funcionario_cadastrar = new CadastroFuncionario();
	CadastroFuncionario funcionario_atualizar = new CadastroFuncionario();
	private JDialog telaPai;

	private JPanel painelCentral = new JPanel();
	private TelaFuncionariosCadastroContratoTrabalho isto;
	private JComboBox cBTipoContratoTrabalho;
	private JTextFieldPersonalizado entDataAdmissao ;
	 Locale ptBr = new Locale("pt", "BR");

	
	public TelaFuncionariosCadastroContratoTrabalho(int flag_tipo_tela,CadastroFuncionario funcionario, CadastroFuncionarioAdmissao contrato, Window janela_pai) {
	
		String data_atual = new GetData().getData();
		salario_minimo = new GerenciarBancoSalarioMinimo().getSalarioMinimoVigente(data_atual);
	
		this.setContentPane(painelDadosIniciais);
		
		setForeground(new Color(255, 255, 255));

		getDadosGlobais();
		// setAlwaysOnTop(true);

		// setModal(true);
		isto = this;

		setResizable(false);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setBounds(100, 100, 656, 400);
		
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
				panel_3.setLayout(new MigLayout("", "[62px][2px][57px][1px][50px][10px][415px][][][]", "[27px][][33px][33px][33px][30px][36px][][]"));
		
				JLabel lblCpf = new JLabel("Tipo Contrato Trabalho:");
				panel_3.add(lblCpf, "cell 4 1,alignx right,aligny center");
				lblCpf.setHorizontalAlignment(SwingConstants.TRAILING);
				lblCpf.setForeground(Color.BLACK);
				lblCpf.setFont(new Font("Arial", Font.PLAIN, 16));
				lblCpf.setBackground(Color.ORANGE);
				lblCpf.setHorizontalAlignment(JLabel.RIGHT);
		
				cBTipoContratoTrabalho = new JComboBox();
				cBTipoContratoTrabalho.addItem("Contrato de Trabalho por tempo Indeterminado");
				cBTipoContratoTrabalho.addItem("Contrato de Trabalho de Experiência");
				cBTipoContratoTrabalho.addItem("Contrato de Trabalho Eventual");
				cBTipoContratoTrabalho.addItem("Contrato de Trabalho Temporário");
				cBTipoContratoTrabalho.addItem("Jovem Aprendiz");
				cBTipoContratoTrabalho.addItem("Estágio");
				
						panel_3.add(cBTipoContratoTrabalho, "cell 6 1,growx,aligny top");
						cBTipoContratoTrabalho.setFont(new Font("Arial", Font.BOLD, 14));
		

		
		
		JLabel lblCargo = new JLabel("Cargo:");
		lblCargo.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCargo.setForeground(Color.BLACK);
		lblCargo.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCargo.setBackground(Color.ORANGE);
		panel_3.add(lblCargo, "cell 0 2 6 1,alignx right,aligny center");
		
		
		
		
		 entCargo = new JTextFieldPersonalizado();
		entCargo.setForeground(Color.black);
		panel_3.add(entCargo, "cell 6 2,growx,aligny top");
		
		JLabel lblOcupao_1_1 = new JLabel("Função:");
		lblOcupao_1_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblOcupao_1_1.setForeground(Color.BLACK);
		lblOcupao_1_1.setFont(new Font("Arial", Font.PLAIN, 16));
		lblOcupao_1_1.setBackground(Color.ORANGE);
		panel_3.add(lblOcupao_1_1, "cell 0 3 6 1,alignx right,aligny center");
		
		 entFuncao = new JTextFieldPersonalizado();
		entFuncao.setForeground(Color.black);
		panel_3.add(entFuncao, "cell 6 3,growx,aligny top");

		JLabel lblDataNascimento = new JLabel("Data Admissão:");
		panel_3.add(lblDataNascimento, "cell 0 6 6 1,alignx right,aligny center");
		lblDataNascimento.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDataNascimento.setForeground(Color.BLACK);
		lblDataNascimento.setFont(new Font("Arial", Font.PLAIN, 16));
		lblDataNascimento.setBackground(Color.ORANGE);
		lblDataNascimento.setHorizontalAlignment(JLabel.LEFT);

		 entDataAdmissao = new JTextFieldPersonalizado();
		panel_3.add(entDataAdmissao, "cell 6 6,growx,aligny top");
		entDataAdmissao.setForeground(Color.BLACK);
		entDataAdmissao.setFont(new Font("Arial", Font.BOLD, 20));
		entDataAdmissao.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				String caracteres = "0987654321\b";// lista de caracters que não devem ser aceitos
				String texto = entDataAdmissao.getText();
				if (!caracteres.contains(evt.getKeyChar() + "")) {
					evt.consume();// aciona esse propriedade para eliminar a ação do evento
				} else {
					if (texto.length() == 2 && evt.getKeyChar() != '\b') {
						entDataAdmissao.setText(entDataAdmissao.getText().concat("/"));
					}
					if (texto.length() == 5 && evt.getKeyChar() != '\b') {
						entDataAdmissao.setText(entDataAdmissao.getText().concat("/"));
					}

					if (entDataAdmissao.getText().length() >= 10) {
						
						evt.consume();
						entDataAdmissao.setText(entDataAdmissao.getText().substring(0, 10));
					}

				}

			}
		});
		entDataAdmissao.setColumns(10);
		
				JLabel lblOcupao = new JLabel("Salário:");
				panel_3.add(lblOcupao, "cell 0 4 6 1,alignx right,aligny center");
				lblOcupao.setHorizontalAlignment(SwingConstants.TRAILING);
				lblOcupao.setForeground(Color.BLACK);
				lblOcupao.setFont(new Font("Arial", Font.PLAIN, 16));
				lblOcupao.setBackground(Color.ORANGE);
				lblOcupao.setHorizontalAlignment(JLabel.LEFT);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel_3.add(panel, "cell 5 5 2 1,alignx left,aligny top");
		panel.setLayout(new MigLayout("", "[][]", "[]"));

		JLabel lblNewLabel = new JLabel("Valor Sálario Minimo:");
		panel.add(lblNewLabel, "cell 0 0");

		JLabel lblValorSalarioMinimo = new JLabel();
		String valor_salaro_minimo = NumberFormat.getCurrencyInstance(ptBr).format(salario_minimo);
		lblValorSalarioMinimo.setText(valor_salaro_minimo);
		lblValorSalarioMinimo.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblValorSalarioMinimo.setForeground(Color.BLACK);
		panel.add(lblValorSalarioMinimo, "cell 1 0");
		
		 entSalario = new JTextFieldPersonalizado();
		entSalario.setForeground(Color.black);
		panel_3.add(entSalario, "cell 6 4,growx,aligny top");
		
		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				CadastroFuncionarioAdmissao cad = getDadosAtualizar(contrato);
				if(cad != null) {
					cad.setId_colaborador(funcionario.getId_funcionario());

					GerenciarBancoFuncionariosContratoTrabalho gerenciar = new GerenciarBancoFuncionariosContratoTrabalho();
					boolean atualizou = gerenciar.atualizarcontrato(cad);
					if(atualizou) {
						JOptionPane.showMessageDialog(isto, "Contrato de Trabalho Atualizado!\nConsulte o administrador");
						((TelaGerenciarFuncionario) janela_pai).pesquisar_contratos();

						isto.dispose();
						
					}else {
						JOptionPane.showMessageDialog(isto, "Erro ao Atualizar!\nConsulte o administrador");
					}
					
				}
				
			}
		});
		btnAtualizar.setForeground(Color.WHITE);
		btnAtualizar.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnAtualizar.setBackground(Color.BLUE);
		panel_3.add(btnAtualizar, "flowx,cell 6 8,alignx right");
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				CadastroFuncionarioAdmissao cad = getDadosSalvar();
				if(cad != null) {
					cad.setId_colaborador(funcionario.getId_funcionario());
					GerenciarBancoFuncionariosContratoTrabalho gerenciar = new GerenciarBancoFuncionariosContratoTrabalho();
					int inseriu = gerenciar.inserircontrato(cad);
					if(inseriu > 0) {
						JOptionPane.showMessageDialog(isto, "Contrato de Trabalho Inserido!\nConsulte o administrador");
						((TelaGerenciarFuncionario) janela_pai).pesquisar_contratos();
						isto.dispose();
						
					}else {
						JOptionPane.showMessageDialog(isto, "Erro ao salvar!\nConsulte o administrador");
					}
					
				}
			}
		});
		btnSalvar.setForeground(Color.WHITE);
		btnSalvar.setBackground(new Color(0, 51, 0));
		btnSalvar.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_3.add(btnSalvar, "cell 6 8");
		painelCentral.setLayout(new BorderLayout(0, 0));

		// configura widgets no painel finalizar


		if (flag_tipo_tela == 0) {
			setTitle("E-Contract - Novo Contrato de Trabalho");
		
			btnAtualizar.setEnabled(false);
			btnAtualizar.setVisible(false);
			
		}
		else if (flag_tipo_tela == 1) {
			setTitle("E-Contract - Edição de Contrato de Trabalho");

			btnSalvar.setEnabled(false);
			btnSalvar.setVisible(false);
			
			rotinasEdicao(contrato);
			
		}
		
		
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

	public void setTelaPai(JDialog tela_pai) {
		this.telaPai = tela_pai;
	}
	
	

	public CadastroFuncionarioAdmissao getDadosAtualizar(CadastroFuncionarioAdmissao cad_antigo) {
		CadastroFuncionarioAdmissao cad = new CadastroFuncionarioAdmissao();
		
		cad.setId_contrato(cad_antigo.getId_contrato());
		
		String tipo_contrato, data_admissao, cargo, funcao;
		double salario;
		
		tipo_contrato = cBTipoContratoTrabalho.getSelectedItem().toString();
		
		cargo = entCargo.getText();
		funcao = entFuncao.getText();
		data_admissao = entDataAdmissao.getText();
		
		cad.setCargo(cargo);
		cad.setFuncao(funcao);
		cad.setStatus(1);
		cad.setTipo_contrato(tipo_contrato);

		
		try {
			
			salario = Double.parseDouble(entSalario.getText());
			
			cad.setSalario(salario);
			
		}catch(Exception e) {
			JOptionPane.showMessageDialog(isto, "Valor do Salário Invalido!");
			return null;
		}
		
		try {

			if (!isDateValid(data_admissao)) {
				JOptionPane.showMessageDialog(isto, "Data da Admissão Inválida");

				return null;
			} else {
				cad.setData_admissao(data_admissao);
				return cad;

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(isto, "Data da Admissão Inválida");
			return null;
		}
		
	}
	
	
	public CadastroFuncionarioAdmissao getDadosSalvar() {
		CadastroFuncionarioAdmissao cad = new CadastroFuncionarioAdmissao();
		
		
		String tipo_contrato, data_admissao, cargo, funcao;
		double salario;
		
		tipo_contrato = cBTipoContratoTrabalho.getSelectedItem().toString();
		
		cargo = entCargo.getText();
		funcao = entFuncao.getText();
		data_admissao = entDataAdmissao.getText();
		
		cad.setCargo(cargo);
		cad.setFuncao(funcao);
		cad.setTipo_contrato(tipo_contrato);
		cad.setStatus(1);
		
		try {
			
			salario = Double.parseDouble(entSalario.getText());
			
			cad.setSalario(salario);
			
		}catch(Exception e) {
			JOptionPane.showMessageDialog(isto, "Valor do Salário Invalido!");
			return null;
		}
		
		try {

			if (!isDateValid(data_admissao)) {
				JOptionPane.showMessageDialog(isto, "Data da Admissão Inválida");

				return null;
			} else {
				cad.setData_admissao(data_admissao);
				return cad;

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(isto, "Data da Admissão Inválida");
			return null;
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
	
	public void rotinasEdicao(CadastroFuncionarioAdmissao cad) {
		
		entDataAdmissao.setText(cad.getData_admissao());
		entCargo.setText(cad.getCargo());
		entFuncao.setText(cad.getFuncao());
		entSalario.setText(Double.toString(cad.getSalario()));
		cBTipoContratoTrabalho.setSelectedItem(cad.getTipo_contrato());
		
		
	}
	
}
