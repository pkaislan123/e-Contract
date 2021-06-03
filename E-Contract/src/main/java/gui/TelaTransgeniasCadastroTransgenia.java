
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

import main.java.cadastros.CadastroAditivo;
import main.java.cadastros.CadastroAviso;
import main.java.cadastros.CadastroBaseArquivos;
import main.java.cadastros.CadastroBaseDados;
import main.java.cadastros.CadastroTransgenia;
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
import main.java.conexaoBanco.GerenciarBancoAditivos;
import main.java.conexaoBanco.GerenciarBancoTransgenia;
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
import main.java.classesExtras.ComboBoxRenderPersonalizado;
import main.java.classesExtras.ComboBoxRenderPersonalizadoContaBancaria;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoSafras;

import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;
import javax.swing.JEditorPane;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;



public class TelaTransgeniasCadastroTransgenia extends JFrame {

	private final JPanel painelPrincipal = new JPanel();
	private final JPanel painelOdin = new JPanel();

    private JLabel lblTotalContratosConcluidos, lblTotalContratos, lblTotalContratosAbertos;
    private TelaTransgeniasCadastroTransgenia isto;
    private JDialog telaPai;
    private CadastroFuncionario colaborador;
    private final JButton btnCadastrar = new JButton("Cadastrar");
    private final JButton btnAtualizar = new JButton("Atualizar");
    private final JEditorPane entDescricao = new JEditorPane();
    private final JLabel lblDescrio = new JLabel("Descrição:");
    private final JPanel panel = new JPanel();
    private final JLabel lblNewLabel = new JLabel("Cadastro de Transgenia");
    private final JPanel panel_2 = new JPanel();
    private final JPanel panel_3 = new JPanel();
    private final JPanel panel_4 = new JPanel();
    private FinanceiroGrupoContas financeiro_grupo_contas;
    private final JLabel lblTipoDeConta = new JLabel("Nome:");
    private final JEditorPane entNome = new JEditorPane();
    private final JLabel lblTeste = new JLabel("Tipo do Teste:");
    private final JLabel lblResultado = new JLabel("Resultado do Teste:");
    private final JRadioButton rdBtnDeclarado = new JRadioButton("Declarado em Nota");
    private final JRadioButton rdBtnTestado = new JRadioButton("Testado");
    private final JRadioButton rdBtnPositivo = new JRadioButton("Positivo");
    private final JRadioButton rdBtnNegativo = new JRadioButton("Negativo");
    private final JLabel lblRoy = new JLabel("Pagamento de Royalties:?");
    private final JRadioButton rdBtnPositivoRoyalties = new JRadioButton("Positivo");
    private final JRadioButton rdBtnNegativoRoyalties = new JRadioButton("Negativo");


    
	public TelaTransgeniasCadastroTransgenia(int modo_operacao,CadastroTransgenia transgenia, Window janela_pai) {

		
		
		
		 isto = this;
		if(modo_operacao == 0)
		setTitle("E-Contract - Cadastro de Transgenia");
		else
			setTitle("E-Contract - Edição de Cadastro de Transgenia");

		setContentPane(painelOdin);
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 675, 296);
		painelOdin.setLayout(new BorderLayout(0, 0));
		painelOdin.add(painelPrincipal);
		
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		painelPrincipal.setLayout(new MigLayout("", "[73px][379px][74px][8px][83px,grow][50px]", "[46px][16px][433px,grow][28px,grow]"));
		panel.setBackground(new Color(0, 51, 0));
		painelPrincipal.add(panel, "cell 0 0 5 1,grow");
		panel.setLayout(new MigLayout("", "[617px]", "[20px]"));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setForeground(Color.WHITE);
		
		panel.add(lblNewLabel, "cell 0 0,alignx left,aligny center");
		panel_2.setBackground(Color.WHITE);
		
		painelPrincipal.add(panel_2, "cell 0 2 5 1,grow");
		 panel_2.setLayout(new MigLayout("", "[grow][grow]", "[grow][33px,grow][][][][43px,grow][29px,grow][33px,grow][33px,grow][33px,grow][33px,grow][30px,grow][30px,grow]"));
		 lblTipoDeConta.setHorizontalAlignment(SwingConstants.TRAILING);
		 lblTipoDeConta.setForeground(Color.BLACK);
		 lblTipoDeConta.setFont(new Font("Arial", Font.PLAIN, 16));
		 lblTipoDeConta.setBackground(Color.ORANGE);
		 
		 panel_2.add(lblTipoDeConta, "cell 0 0,alignx right");
		entNome.setFont(new Font("SansSerif", Font.BOLD, 16));
		entNome.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		panel_2.add(entNome, "cell 1 0,grow");
		panel_2.add(lblDescrio, "cell 0 1,growx,aligny top");
		lblDescrio.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDescrio.setForeground(Color.BLACK);
		lblDescrio.setFont(new Font("Arial", Font.PLAIN, 16));
		lblDescrio.setBackground(Color.ORANGE);
		entDescricao.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_2.add(entDescricao, "cell 1 1,grow");
		entDescricao.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblTeste.setHorizontalAlignment(SwingConstants.TRAILING);
		lblTeste.setForeground(Color.BLACK);
		lblTeste.setFont(new Font("Arial", Font.PLAIN, 16));
		lblTeste.setBackground(Color.ORANGE);
		
		panel_2.add(lblTeste, "cell 0 2,alignx right");
		
		
		rdBtnDeclarado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdBtnTestado.setSelected(false);
				rdBtnDeclarado.setSelected(true);
				
			}
		});
		rdBtnDeclarado.setSelected(true);
		rdBtnDeclarado.setFont(new Font("SansSerif", Font.PLAIN, 14));
		
		panel_2.add(rdBtnDeclarado, "flowx,cell 1 2");
		lblResultado.setHorizontalAlignment(SwingConstants.TRAILING);
		lblResultado.setForeground(Color.BLACK);
		lblResultado.setFont(new Font("Arial", Font.PLAIN, 16));
		lblResultado.setBackground(Color.ORANGE);
		
		panel_2.add(lblResultado, "cell 0 3,alignx right");
		rdBtnPositivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				rdBtnPositivo.setSelected(true);
				rdBtnNegativo.setSelected(false);
			}
		});
		rdBtnPositivo.setSelected(true);
		rdBtnPositivo.setFont(new Font("SansSerif", Font.PLAIN, 14));
		
		panel_2.add(rdBtnPositivo, "flowx,cell 1 3");
		lblRoy.setHorizontalAlignment(SwingConstants.TRAILING);
		lblRoy.setForeground(Color.BLACK);
		lblRoy.setFont(new Font("Arial", Font.PLAIN, 16));
		lblRoy.setBackground(Color.ORANGE);
		
		panel_2.add(lblRoy, "cell 0 4,alignx right");
		rdBtnPositivoRoyalties.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				rdBtnPositivoRoyalties.setSelected(true);
				rdBtnNegativoRoyalties.setSelected(false);
				
			}
		});
		rdBtnPositivoRoyalties.setSelected(true);
		rdBtnPositivoRoyalties.setFont(new Font("SansSerif", Font.PLAIN, 14));
		
		panel_2.add(rdBtnPositivoRoyalties, "flowx,cell 1 4");
		panel_2.add(panel_3, "cell 1 5,alignx right");
		panel_3.setBackground(Color.WHITE);
		panel_3.setLayout(new MigLayout("", "[][]", "[]"));
		btnAtualizar.setBackground(new Color(0, 51, 153));
		btnAtualizar.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnAtualizar.setForeground(Color.WHITE);
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GerenciarBancoTransgenia gerenciar = new GerenciarBancoTransgenia();
				 boolean result = gerenciar.atualizartransgenia(getDadosAtualizar(transgenia));
				 if(result) {
					 JOptionPane.showMessageDialog(isto, "Cadastro Atualizado");
					 ((TelaTransgenias) janela_pai).pesquisar();
					 isto.dispose();
				 }else {
					 JOptionPane.showMessageDialog(isto, "Erro ao atualizar\nConsulte o Administrador");

				 }
			}
		});
		panel_3.add(btnAtualizar, "cell 0 0,growx");
		btnCadastrar.setBackground(new Color(0, 51, 0));
		btnCadastrar.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnCadastrar.setForeground(Color.WHITE);
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				GerenciarBancoTransgenia gerenciar = new GerenciarBancoTransgenia();
			 int result = gerenciar.inserir_transgenia(getDadosSalvar());
			 if(result > 0) {
				 JOptionPane.showMessageDialog(isto, "Cadastro Concluído");
				 ((TelaTransgenias) janela_pai).pesquisar();

				 isto.dispose();
			 }else {
				 JOptionPane.showMessageDialog(isto, "Erro ao Salvar\nConsulte o Administrador");

			 }
				
			}
		});
		panel_3.add(btnCadastrar, "cell 1 0,growx");
		rdBtnTestado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				rdBtnTestado.setSelected(true);
				rdBtnDeclarado.setSelected(false);
				
			}
		});
		rdBtnTestado.setFont(new Font("SansSerif", Font.PLAIN, 14));
		
		panel_2.add(rdBtnTestado, "cell 1 2,alignx right,aligny top");
		rdBtnNegativo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				rdBtnPositivo.setSelected(false);
				rdBtnNegativo.setSelected(true);
				
			}
		});
		rdBtnNegativo.setFont(new Font("SansSerif", Font.PLAIN, 14));
		
		panel_2.add(rdBtnNegativo, "cell 1 3");
		rdBtnNegativoRoyalties.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				rdBtnPositivoRoyalties.setSelected(false);
				rdBtnNegativoRoyalties.setSelected(true);
				
				
			}
			
		});
		rdBtnNegativoRoyalties.setFont(new Font("SansSerif", Font.PLAIN, 14));
		
		panel_2.add(rdBtnNegativoRoyalties, "cell 1 4");
		panel_4.setBackground(new Color(0, 51, 0));
		
		painelPrincipal.add(panel_4, "cell 5 0 1 4,grow");
	
		if(modo_operacao == 1) {
			rotinasEdicao(transgenia);
	
			
			btnCadastrar.setEnabled(false);
			btnCadastrar.setVisible(false);
		}else {
			btnAtualizar.setEnabled(false);
			btnAtualizar.setVisible(false);
		}
		
		
		adicionarFocus(isto.getComponents());
		this.setResizable(false);
		this.setLocationRelativeTo(janela_pai);

		
	}
	
	public void rotinasEdicao(CadastroTransgenia transgenia) {
	
		
			
		
		entNome.setText(transgenia.getNome());
		entDescricao.setText(transgenia.getDescricao());

		
		if(transgenia.getTeste() == 0) {
			rdBtnTestado.setSelected(false);
			rdBtnDeclarado.setSelected(true);
		}else if(transgenia.getTeste() == 1) {
			rdBtnTestado.setSelected(true);
			rdBtnDeclarado.setSelected(false);
		}
		
		
		if(transgenia.getResultado() == 0) {
		rdBtnPositivo.setSelected(false);
		rdBtnNegativo.setSelected(true);
		}else if(transgenia.getResultado() == 1) {
			rdBtnPositivo.setSelected(true);
			rdBtnNegativo.setSelected(false);
		
		}
		
		
		
		if(transgenia.getRoyalties() == 0) {
			rdBtnPositivoRoyalties.setSelected(false);
			rdBtnNegativoRoyalties.setSelected(true);
			}else if(transgenia.getRoyalties() == 1) {
				rdBtnPositivoRoyalties.setSelected(true);
				rdBtnNegativoRoyalties.setSelected(false);
			
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
	
	
	
	
	
	
	public CadastroTransgenia getDadosAtualizar(CadastroTransgenia transgenia_antigo) {
		

		CadastroTransgenia transgenia = new CadastroTransgenia();
		transgenia.setId_transgenia(transgenia_antigo.getId_transgenia());
		
		
		String  descricao, nome;
		int teste = 0, resultado = 0, royalties = 0;
		
		descricao = entDescricao.getText();
		nome = entNome.getText();
		
		
		if(rdBtnDeclarado.isSelected()) {
			teste = 0;
		}else if(rdBtnTestado.isSelected()) {
			teste = 1;
		}
		
		if(rdBtnPositivo.isSelected()) {
			resultado = 1;
		}else if(rdBtnNegativo.isSelected()) {
			resultado = 0;
		}
	
		if(rdBtnPositivoRoyalties.isSelected()) {
			royalties = 1;
		}else if(rdBtnNegativoRoyalties.isSelected()) {
			royalties = 0;
		}
	
		
		
		transgenia.setDescricao(descricao);
		transgenia.setNome(nome);
		transgenia.setTeste(teste);
		transgenia.setResultado(resultado);
		transgenia.setRoyalties(royalties);
		return transgenia;
	}
	
	public CadastroTransgenia getDadosSalvar() {
		
		CadastroTransgenia transgenia = new CadastroTransgenia();
		
		String  descricao, nome;
		int teste = 0, resultado = 0, royalties = 0;
		
		descricao = entDescricao.getText();
		nome = entNome.getText();
		
		
		if(rdBtnDeclarado.isSelected()) {
			teste = 0;
		}else if(rdBtnTestado.isSelected()) {
			teste = 1;
		}
		
		if(rdBtnPositivo.isSelected()) {
			resultado = 1;
		}else if(rdBtnNegativo.isSelected()) {
			resultado = 0;
		}
	
		
		if(rdBtnPositivoRoyalties.isSelected()) {
			royalties = 1;
		}else if(rdBtnNegativoRoyalties.isSelected()) {
			royalties = 0;
		}
	
		
		
		transgenia.setDescricao(descricao);
		transgenia.setNome(nome);
		transgenia.setTeste(teste);
		transgenia.setResultado(resultado);
		transgenia.setRoyalties(royalties);
		return transgenia;
	}
	
	
	
	
	
	

	
}
