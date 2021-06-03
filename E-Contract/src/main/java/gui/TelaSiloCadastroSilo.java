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
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroContrato.CadastroPagamento;
import main.java.cadastros.CadastroContrato.CadastroPagamentoContratual;
import main.java.cadastros.CadastroContrato.CadastroTarefa;
import main.java.cadastros.CadastroContrato.Recebimento;
import main.java.cadastros.CadastroDocumento;
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



public class TelaSiloCadastroSilo extends JFrame {

	private final JPanel painelPrincipal = new JPanel();
	private final JPanel painelOdin = new JPanel();

    private JLabel lblTotalContratosConcluidos, lblTotalContratos, lblTotalContratosAbertos;
    private TelaSiloCadastroSilo isto;
    private JDialog telaPai;
    private CadastroCliente armazem;
    private JTextFieldPersonalizado entNome;
    private final JButton btnCadastrar = new JButton("Cadastrar");
    private final JButton btnAtualizar = new JButton("Atualizar");
    private final JEditorPane entDescricao = new JEditorPane();
    private final JLabel lblDescrio = new JLabel("Descrição:");
    private final JPanel panel = new JPanel();
    private final JLabel lblNewLabel = new JLabel("Cadastro de Silo");
    private final JLabel lblCliente = new JLabel("Capacidade:");
    private final JPanel panel_2 = new JPanel();
    private final JPanel panel_3 = new JPanel();
    private final JPanel panel_4 = new JPanel();
    private FinanceiroGrupoContas financeiro_grupo_contas;
    private final JLabel lblTipoDeConta = new JLabel("Armazém");
    private final JPanel panel_1_1 = new JPanel();
    private final JComboBox cbArmazem = new JComboBox();
    private final JLabel lblIdentificador = new JLabel("Identificador:");
    private final JTextFieldPersonalizado entCapacidade = new JTextFieldPersonalizado();
    private final JTextFieldPersonalizado entIdentificador = new JTextFieldPersonalizado();
    private final JButton btnSelecionarArmazem = new JButton("Selecionar");
    private final JLabel lblNewLabel_1 = new JLabel("sacos");


    
	public TelaSiloCadastroSilo(int modo_operacao,CadastroSilo silo, Window janela_pai) {

		
		
		
		 isto = this;
		if(modo_operacao == 0)
		setTitle("E-Contract - Cadastro de Silo");
		else
			setTitle("E-Contract - Edição de Cadastro de Silo");

		setContentPane(painelOdin);
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 675, 598);
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
		 panel_2.setLayout(new MigLayout("", "[grow][grow][]", "[][33px,grow][43px,grow][29px,grow][33px,grow][33px,grow][33px,grow][33px,grow][30px,grow][30px,grow]"));
		 lblTipoDeConta.setHorizontalAlignment(SwingConstants.TRAILING);
		 lblTipoDeConta.setForeground(Color.BLACK);
		 lblTipoDeConta.setFont(new Font("Arial", Font.PLAIN, 16));
		 lblTipoDeConta.setBackground(Color.ORANGE);
		 
		 panel_2.add(lblTipoDeConta, "cell 0 0,alignx right");
		 panel_1_1.setBackground(Color.WHITE);
		 
		 panel_2.add(panel_1_1, "cell 1 0 2 1,grow");
		 panel_1_1.setLayout(new MigLayout("", "[grow][]", "[]"));
		 cbArmazem.setFont(new Font("SansSerif", Font.PLAIN, 14));
	
		 
		 panel_1_1.add(cbArmazem, "cell 0 0,grow");
		 btnSelecionarArmazem.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		
		 		TelaArmazem tela= new TelaArmazem(1, isto);
		 		tela.setVisible(true);
		 		
		 	}
		 });
		 btnSelecionarArmazem.setBackground(new Color(0, 0, 102));
		 btnSelecionarArmazem.setForeground(Color.WHITE);
		 
		 panel_1_1.add(btnSelecionarArmazem, "cell 1 0");
		 
		 JLabel lblNome = new JLabel("Nome:");
		 panel_2.add(lblNome, "cell 0 1,growx,aligny center");
		 lblNome.setHorizontalAlignment(SwingConstants.TRAILING);
		 lblNome.setForeground(Color.BLACK);
		 lblNome.setFont(new Font("Arial", Font.PLAIN, 16));
		 lblNome.setBackground(Color.ORANGE);
		
		 entNome = new JTextFieldPersonalizado();
		 panel_2.add(entNome, "cell 1 1 2 1,growx,aligny top");
		 entNome.setText("");
		 entNome.setForeground(Color.BLACK);
		lblIdentificador.setHorizontalAlignment(SwingConstants.TRAILING);
		lblIdentificador.setForeground(Color.BLACK);
		lblIdentificador.setFont(new Font("Arial", Font.PLAIN, 16));
		lblIdentificador.setBackground(Color.ORANGE);
		
		panel_2.add(lblIdentificador, "cell 0 2,alignx trailing");
		entIdentificador.setForeground(Color.black);
		panel_2.add(entIdentificador, "cell 1 2 2 1,growx");
		panel_2.add(lblCliente, "cell 0 3,alignx trailing,aligny center");
		lblCliente.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCliente.setForeground(Color.BLACK);
		lblCliente.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCliente.setBackground(Color.ORANGE);
		
		entCapacidade.setForeground(Color.BLACK);
		panel_2.add(entCapacidade, "cell 1 3,growx");
		lblNewLabel_1.setFont(new Font("SansSerif", Font.BOLD, 18));
		
		panel_2.add(lblNewLabel_1, "cell 2 3");
		panel_2.add(lblDescrio, "cell 0 4,growx,aligny top");
		lblDescrio.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDescrio.setForeground(Color.BLACK);
		lblDescrio.setFont(new Font("Arial", Font.PLAIN, 16));
		lblDescrio.setBackground(Color.ORANGE);
		entDescricao.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_2.add(entDescricao, "cell 1 4 2 2,grow");
		entDescricao.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_4.setBackground(new Color(0, 51, 0));
		
		painelPrincipal.add(panel_4, "cell 5 0 1 4,grow");
		panel_3.setBackground(Color.WHITE);
		
		painelPrincipal.add(panel_3, "cell 2 3 3 1,grow");
		panel_3.setLayout(new MigLayout("", "[][]", "[]"));
		btnAtualizar.setBackground(new Color(0, 51, 153));
		btnAtualizar.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnAtualizar.setForeground(Color.WHITE);
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GerenciarBancoSilo gerenciar = new GerenciarBancoSilo();
				 boolean result = gerenciar.atualizarSilo(getDadosAtualizar(silo));
				 if(result) {
					 JOptionPane.showMessageDialog(isto, "Cadastro Atualizado");
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
				
				GerenciarBancoSilo gerenciar = new GerenciarBancoSilo();
			 int result = gerenciar.inserir_silo(getDadosSalvar());
			 if(result > 0) {
				 JOptionPane.showMessageDialog(isto, "Cadastro Concluído");
				 isto.dispose();
			 }else {
				 JOptionPane.showMessageDialog(isto, "Erro ao Salvar\nConsulte o Administrador");

			 }
				
			}
		});
		panel_3.add(btnCadastrar, "cell 1 0,growx");
	
		if(modo_operacao == 1) {
			rotinasEdicao(silo);
	
			
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
	
	public void rotinasEdicao(CadastroSilo silo) {
	
		
			
		
		
		entNome.setText(silo.getNome_silo());
		entDescricao.setText(silo.getDescricao());
		entIdentificador.setText(silo.getIdentificador());
		entCapacidade.setText(Double.toString(silo.getCapacidade()));

		setArmazem(new GerenciarBancoClientes().getCliente(silo.getId_armazem()));
		
		
		
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
	
	
	
	
	
	
	public CadastroSilo getDadosAtualizar(CadastroSilo silo_antigo) {
		

		CadastroSilo silo = new CadastroSilo();
		silo.setId_silo(silo_antigo.getId_silo());
		
		String nome_silo, identificador, descricao;
		
		
		nome_silo = entNome.getText().toString();
		identificador = entIdentificador.getText().toString();
		descricao = entDescricao.getText();
		
		if(armazem != null) {
			
			if(armazem.getId() > 0) {
				silo.setId_armazem(armazem.getId());
			}else {
				JOptionPane.showMessageDialog(null, "Selecione o armazém no qual o silo esta localizado!");
				return null;
			
			}
			
			
		}
		
		
		try {
			double capacidade = Double.parseDouble(entCapacidade.getText());
			silo.setCapacidade(capacidade);
			
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Valor da capacidade incorreto!");

			return null;
		}
		
		if(checkString(nome_silo)) {
			silo.setNome_silo(nome_silo);
		}else {
			JOptionPane.showMessageDialog(null, "Nome Incorreto!");

			return null;
		}
			
		if(checkString(identificador)) {
			silo.setIdentificador(identificador);
		}else {
			JOptionPane.showMessageDialog(null, "Identificador Incorreto");

			return null;
		}
			
		
		
		silo.setDescricao(descricao);

		
		return silo;
	}
	
	public CadastroSilo getDadosSalvar() {
		
		CadastroSilo silo = new CadastroSilo();
		
		String nome_silo, identificador, descricao;
		
		
		nome_silo = entNome.getText().toString();
		identificador = entIdentificador.getText().toString();
		descricao = entDescricao.getText();
		
		if(armazem != null) {
			
			if(armazem.getId() > 0) {
				silo.setId_armazem(armazem.getId());
			}else {
				JOptionPane.showMessageDialog(null, "Selecione o armazém no qual o silo esta localizado!");
				return null;
			
			}
			
			
		}
		
		
		try {
			double capacidade = Double.parseDouble(entCapacidade.getText());
			silo.setCapacidade(capacidade);
			
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Valor da capacidade incorreto!");

			return null;
		}
		
		if(checkString(nome_silo)) {
			silo.setNome_silo(nome_silo);
		}else {
			JOptionPane.showMessageDialog(null, "Nome Incorreto!");

			return null;
		}
			
		if(checkString(identificador)) {
			silo.setIdentificador(identificador);
		}else {
			JOptionPane.showMessageDialog(null, "Identificador Incorreto");

			return null;
		}
			
		
		silo.setDescricao(descricao);
		
		
		return silo;
	}
	
	
	public boolean checkString(String txt) {
		return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ") && txt.length() > 0;
	}
	
	
	
	public void setArmazem(CadastroCliente _armazem) {
		this.armazem = _armazem;
		
		cbArmazem.removeAllItems();
		
		cbArmazem.addItem(_armazem.getNome_fantaia());
		
	}
	
}
