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
import main.java.cadastros.ContaBancaria;
import main.java.cadastros.Contato;
import main.java.cadastros.DadosCarregamento;
import main.java.cadastros.DadosContratos;
import main.java.cadastros.DadosRecebimento;
import main.java.cadastros.InstituicaoBancaria;
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
import outros.ValidaCNPJ;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroNuvem;
import main.java.cadastros.CadastroPontuacao;
import main.java.cadastros.CadastroZapMessenger;
import main.java.outros.DadosGlobais;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroGrupo;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoGrupos;
import main.java.conexaoBanco.GerenciarBancoInstituicaoBancaria;
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



public class TelaFinanceiroCadastroInstituicaoBancaria extends JDialog {

	private final JPanel painelPrincipal = new JPanel();
	private final JPanel painelOdin = new JPanel();

    private JLabel lblTotalContratosConcluidos, lblTotalContratos, lblTotalContratosAbertos;
    private TelaFinanceiroCadastroInstituicaoBancaria isto;
    private JDialog telaPai;
    
    private JTextFieldPersonalizado entNome;
    private final JLabel lblSaldoInicial = new JLabel("Saldo Inicial:");
    private final JTextFieldPersonalizado entSaldoInicial = new JTextFieldPersonalizado();
    private final JTextFieldPersonalizado entTaxaIntermediacao = new JTextFieldPersonalizado();
    private final JTextFieldPersonalizado entTaxaParcelamento = new JTextFieldPersonalizado();
    private final JTextFieldPersonalizado entTarifaFixaTransacao = new JTextFieldPersonalizado();
    private final JLabel lbasdas = new JLabel("Taxa Intermediação:");
    private final JLabel lblTaxaDeParcelamento = new JLabel("Taxa de Parcelamento:");
    private final JLabel lblTaxaFixaPor = new JLabel("Taxa Fixa por Transação:");
    private final JButton btnCadastrar = new JButton("Cadastrar");
    private final JButton btnAtualizar = new JButton("Atualizar");
    private final JEditorPane entDescricao = new JEditorPane();
    private final JLabel lblDescrio = new JLabel("Descrição:");
    private final JLabel lblObservao = new JLabel("Observação:");
    private final JEditorPane entObservacao = new JEditorPane();
    private final JPanel panel = new JPanel();
    private final JLabel lblNewLabel = new JLabel("Cadastro de Instituição Bancária");
    private final JLabel lblCliente = new JLabel("Cliente:");
    private final JPanel panel_1 = new JPanel();
    private final JComboBox cbCliente = new JComboBox();
    private final JButton btnSelecionarCliente = new JButton("Selecionar");
    private final JLabel lblConta = new JLabel("Conta:");
    private final JComboBox cbContaBancaria = new JComboBox();
    private final JPanel panel_2 = new JPanel();
    private final JPanel panel_3 = new JPanel();
    private final JPanel panel_4 = new JPanel();
    private CadastroCliente cliente_selecionado = null;


private ComboBoxPersonalizadoContaBancaria modelCC= new ComboBoxPersonalizadoContaBancaria();
private ComboBoxRenderPersonalizadoContaBancaria renderCC  = new ComboBoxRenderPersonalizadoContaBancaria() ;
    
	public TelaFinanceiroCadastroInstituicaoBancaria(int modo_operacao,InstituicaoBancaria instituicao_bancaria, Window janela_pai) {

		
		
		
		 isto = this;
		if(modo_operacao == 0)
		setTitle("E-Contract - Cadastro Instituição Bancária");
		else
			setTitle("E-Contract - Edição de Cadastro Instituição Bancária");

		setContentPane(painelOdin);
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 675, 598);
		painelOdin.setLayout(new BorderLayout(0, 0));
		painelOdin.add(painelPrincipal);
		
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		painelPrincipal.setLayout(new MigLayout("", "[73px][379px][74px][8px][83px,grow][50px]", "[46px][16px][433px,grow][28px,grow]"));
		panel.setBackground(new Color(0, 0, 51));
		painelPrincipal.add(panel, "cell 0 0 5 1,grow");
		panel.setLayout(new MigLayout("", "[617px]", "[20px]"));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setForeground(Color.WHITE);
		
		panel.add(lblNewLabel, "cell 0 0,alignx left,aligny center");
		panel_2.setBackground(Color.WHITE);
		
		painelPrincipal.add(panel_2, "cell 0 2 5 1,grow");
		 panel_2.setLayout(new MigLayout("", "[grow][grow]", "[33px,grow][43px,grow][29px,grow][33px,grow][33px,grow][33px,grow][33px,grow][30px, grow][30px,grow]"));
		 
		 JLabel lblNome = new JLabel("Nome:");
		 panel_2.add(lblNome, "cell 0 0,growx,aligny center");
		 lblNome.setHorizontalAlignment(SwingConstants.TRAILING);
		 lblNome.setForeground(Color.BLACK);
		 lblNome.setFont(new Font("Arial", Font.PLAIN, 16));
		 lblNome.setBackground(Color.ORANGE);
		
		 entNome = new JTextFieldPersonalizado();
		 panel_2.add(entNome, "cell 1 0,growx,aligny top");
		 entNome.setText("Ex: Banco do Brasil");
		 entNome.setForeground(Color.BLACK);
		panel_2.add(lblCliente, "cell 0 1,growx,aligny center");
		lblCliente.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCliente.setForeground(Color.BLACK);
		lblCliente.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCliente.setBackground(Color.ORANGE);
		panel_2.add(panel_1, "cell 1 1,growx,aligny top");
		panel_1.setBackground(new Color(0, 51, 102));
		panel_1.setLayout(new MigLayout("", "[grow]", "[]"));
		cbCliente.setFont(new Font("SansSerif", Font.PLAIN, 14));
		
		panel_1.add(cbCliente, "flowx,cell 0 0,growx");
		btnSelecionarCliente.setForeground(Color.WHITE);
		btnSelecionarCliente.setBackground(new Color(0, 0, 102));
		btnSelecionarCliente.setFont(new Font("Arial", Font.PLAIN, 12));
		btnSelecionarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCliente tela = new TelaCliente(0,25, null);
				tela.setTelaPai(isto);
				tela.setVisible(true);
			}
		});
		
		panel_1.add(btnSelecionarCliente, "cell 0 0");
		panel_2.add(lblConta, "cell 0 2,growx,aligny center");
		lblConta.setHorizontalAlignment(SwingConstants.TRAILING);
		lblConta.setForeground(Color.BLACK);
		lblConta.setFont(new Font("Arial", Font.PLAIN, 16));
		lblConta.setBackground(Color.ORANGE);
		
		cbContaBancaria.setModel(modelCC);
		cbContaBancaria.setRenderer(renderCC);
		cbContaBancaria.setFont(new Font("Arial", Font.PLAIN, 18));
		panel_2.add(cbContaBancaria, "cell 1 2,growx,aligny top");
		
	

		panel_2.add(lblSaldoInicial, "cell 0 3,growx,aligny center");
		lblSaldoInicial.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSaldoInicial.setForeground(Color.BLACK);
		lblSaldoInicial.setFont(new Font("Arial", Font.PLAIN, 16));
		lblSaldoInicial.setBackground(Color.ORANGE);
		panel_2.add(entSaldoInicial, "cell 1 3,growx,aligny top");
		
		entSaldoInicial.setForeground(Color.BLACK);
		panel_2.add(lbasdas, "cell 0 4,growx,aligny center");
		lbasdas.setHorizontalAlignment(SwingConstants.TRAILING);
		lbasdas.setForeground(Color.BLACK);
		lbasdas.setFont(new Font("Arial", Font.PLAIN, 16));
		lbasdas.setBackground(Color.ORANGE);
		panel_2.add(entTaxaIntermediacao, "cell 1 4,growx,aligny top");
		
		entTaxaIntermediacao.setForeground(Color.BLACK);
		panel_2.add(lblTaxaDeParcelamento, "cell 0 5,growx,aligny center");
		lblTaxaDeParcelamento.setHorizontalAlignment(SwingConstants.TRAILING);
		lblTaxaDeParcelamento.setForeground(Color.BLACK);
		lblTaxaDeParcelamento.setFont(new Font("Arial", Font.PLAIN, 16));
		lblTaxaDeParcelamento.setBackground(Color.ORANGE);
		panel_2.add(entTaxaParcelamento, "cell 1 5,growx,aligny top");
		
		entTaxaParcelamento.setForeground(Color.BLACK);
		panel_2.add(lblTaxaFixaPor, "cell 0 6,growx,aligny center");
		lblTaxaFixaPor.setHorizontalAlignment(SwingConstants.TRAILING);
		lblTaxaFixaPor.setForeground(Color.BLACK);
		lblTaxaFixaPor.setFont(new Font("Arial", Font.PLAIN, 16));
		lblTaxaFixaPor.setBackground(Color.ORANGE);
		panel_2.add(entTarifaFixaTransacao, "cell 1 6,growx,aligny top");
		
		entTarifaFixaTransacao.setForeground(Color.BLACK);
		panel_2.add(lblDescrio, "cell 0 7,growx,aligny top");
		lblDescrio.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDescrio.setForeground(Color.BLACK);
		lblDescrio.setFont(new Font("Arial", Font.PLAIN, 16));
		lblDescrio.setBackground(Color.ORANGE);
		panel_2.add(entDescricao, "cell 1 7,grow");
		entDescricao.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_2.add(lblObservao, "cell 0 8,growx,aligny top");
		lblObservao.setHorizontalAlignment(SwingConstants.TRAILING);
		lblObservao.setForeground(Color.BLACK);
		lblObservao.setFont(new Font("Arial", Font.PLAIN, 16));
		lblObservao.setBackground(Color.ORANGE);
		panel_2.add(entObservacao, "cell 1 8,grow");
		entObservacao.setForeground(Color.BLACK);
		entObservacao.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_4.setBackground(new Color(0, 0, 51));
		
		painelPrincipal.add(panel_4, "cell 5 0 1 4,grow");
		panel_3.setBackground(Color.WHITE);
		
		painelPrincipal.add(panel_3, "cell 2 3 3 1,grow");
		panel_3.setLayout(new MigLayout("", "[][]", "[]"));
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 GerenciarBancoInstituicaoBancaria gerenciar = new GerenciarBancoInstituicaoBancaria();
				 boolean result = gerenciar.atualizarIB(getDadosAtualizar(instituicao_bancaria));
				 if(result) {
					 JOptionPane.showMessageDialog(isto, "Cadastro Atualizado");
					 isto.dispose();
				 }else {
					 JOptionPane.showMessageDialog(isto, "Erro ao Atualizar\nConsulte o Administrador");

				 }
			}
		});
		panel_3.add(btnAtualizar, "cell 0 0,growx");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			 GerenciarBancoInstituicaoBancaria gerenciar = new GerenciarBancoInstituicaoBancaria();
			 int result = gerenciar.inserirIB(getDadosSalvar());
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
			rotinasEdicao(instituicao_bancaria);
	
			
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
	
	public void rotinasEdicao(InstituicaoBancaria ib) {
	
		if(ib.getId_cliente() > 0) {
		CadastroCliente cliente = new GerenciarBancoClientes().getCliente(ib.getId_cliente());
		if(cliente != null) {
			setCliente(cliente);
			
			
		if(ib.getId_conta() > 0) {
			   ContaBancaria cb =  new GerenciarBancoClientes().getConta(ib.getId_conta());
		 	
		 	 if(cb != null) {
		 		 modelCC.setSelectedItem(cb);
		 	 }
			
		}
	  }
	}
		
	////	
		Locale ptBr = new Locale("pt", "BR");
		
		String nome = ib.getNome_instituicao_bancaria();
		String s_saldo_inicial = NumberFormat.getCurrencyInstance(ptBr).format(ib.getSaldo_inicial()).replaceAll("[^0-9,.]", "");
		String s_taxa_intermediacao = NumberFormat.getCurrencyInstance(ptBr).format(ib.getTaxa_intermediacao()).replaceAll("[^0-9,.]", "");
		String s_taxa_parcelamento_mensal = NumberFormat.getCurrencyInstance(ptBr).format(ib.getTaxa_parcelamento_mensal()).replaceAll("[^0-9,.]", "");
				
		String s_tarifa_fixa_transacao = NumberFormat.getCurrencyInstance(ptBr).format(ib.getTaxa_fixa_transacao()).replaceAll("[^0-9,.]", "");
		
		String descricao = ib.getDescricao();
		String observacao = ib.getObservacao();
		
		entNome.setText(nome);
		entSaldoInicial.setText(s_saldo_inicial);
		entTaxaIntermediacao.setText(s_taxa_intermediacao);
		entTaxaParcelamento.setText(s_taxa_parcelamento_mensal);
		entTarifaFixaTransacao.setText(s_tarifa_fixa_transacao);
		entDescricao.setText(descricao);
		entObservacao.setText(observacao);
		
		
		
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
	
	
	
	public void setCliente(CadastroCliente cliente) {
		cliente_selecionado = cliente;

		cbCliente.removeAllItems();

		if (cliente.getTipo_pessoa() == 0) // pessoa fisica
		{
			cbCliente.addItem(cliente.getNome() + " " + cliente.getSobrenome());
			cbCliente.setSelectedItem(cliente.getNome() + " " + cliente.getSobrenome());
			cbCliente.addItem("Indefinido");


		} else // pessoa juridica
		{

			cbCliente.addItem(cliente.getRazao_social());
			cbCliente.setSelectedItem(cliente.getRazao_social());
			cbCliente.addItem("Indefinido");

		

		}
		
		
		modelCC.resetar();
		GerenciarBancoClientes gerenciar = new GerenciarBancoClientes();
		ArrayList<ContaBancaria> contas = gerenciar.getContas(cliente.getId());
		
		for(ContaBancaria conta : contas) {
			
			modelCC.addCC(conta);
 		
		}
		

	}
	
	
	public InstituicaoBancaria getDadosAtualizar(InstituicaoBancaria ib_antigo) {
		
		InstituicaoBancaria ib = new InstituicaoBancaria();
		ib.setId_instituicao_bancaria(ib_antigo.getId_instituicao_bancaria());
		
		int id_cliente = 0, id_conta = 0;
		
		String nome = entNome.getText();
		
		
		if(cliente_selecionado != null) {
			id_cliente = cliente_selecionado.getId();
		
			ContaBancaria cb_selecionada = (ContaBancaria) modelCC.getSelectedItem();	

			if(cb_selecionada != null)
				id_conta = cb_selecionada.getId_conta();
			else 
				id_conta = 0;
			
			
		}else {
			id_cliente = 0;
			id_conta = 0;
			
		}
		
		ib.setNome_instituicao_bancaria(nome);
		String descricao = entDescricao.getText();
		String observacao = entObservacao.getText();

		try {
			String s_saldo_inicial = entSaldoInicial.getText();

			BigDecimal saldo_inicial = BigDecimal.ZERO;

			s_saldo_inicial = s_saldo_inicial.replaceAll("[^0-9,]", "");
			s_saldo_inicial = s_saldo_inicial.replaceAll(",", ".");
			saldo_inicial = new BigDecimal(s_saldo_inicial);
			ib.setSaldo_inicial(saldo_inicial);

		}catch(Exception t) {
			ib.setSaldo_inicial(BigDecimal.ZERO);

		}
		
		try {
			String s_taxa_intermediacao = entTaxaIntermediacao.getText();

			BigDecimal taxa_intermediacao = BigDecimal.ZERO;

			s_taxa_intermediacao = s_taxa_intermediacao.replace("[^0-9,]", "");
			s_taxa_intermediacao = s_taxa_intermediacao.replace(",", ".");
			
			taxa_intermediacao = new BigDecimal(s_taxa_intermediacao);
			ib.setTaxa_intermediacao(taxa_intermediacao);

		}catch(Exception v) {
			ib.setTaxa_intermediacao(BigDecimal.ZERO);

		}
		
		try {
			String s_taxa_parcelamento_mensal = entTaxaParcelamento.getText();

			BigDecimal taxa_parcelamento_mensal = BigDecimal.ZERO;

			s_taxa_parcelamento_mensal = s_taxa_parcelamento_mensal.replace("[^0-9,]", "");
			s_taxa_parcelamento_mensal = s_taxa_parcelamento_mensal.replace(",", ".");
			taxa_parcelamento_mensal = new BigDecimal(s_taxa_parcelamento_mensal);
			ib.setTaxa_parcelamento_mensal(taxa_parcelamento_mensal);

		}catch(Exception i) {
			ib.setTaxa_parcelamento_mensal(BigDecimal.ZERO);

		}	
		
		try {
			String s_tarifa_fixa_transacao = entTarifaFixaTransacao.getText();

			BigDecimal tarifa_fixa_transacao = BigDecimal.ZERO;

			s_tarifa_fixa_transacao = s_tarifa_fixa_transacao.replace("[^0-9,]", "");
			s_tarifa_fixa_transacao = s_tarifa_fixa_transacao.replace(",", ".");
			tarifa_fixa_transacao = new BigDecimal(s_tarifa_fixa_transacao);
			ib.setTaxa_fixa_transacao(tarifa_fixa_transacao);

		}catch(Exception k) {
			ib.setTaxa_fixa_transacao(BigDecimal.ZERO);

		}	
			
		
		
		try {
		ib.setId_cliente(id_cliente);
		
		}catch(Exception y) {
			ib.setId_cliente(0);
		}
		
		try {
			ib.setId_conta(id_conta);
			
			}catch(Exception q) {
				ib.setId_conta(0);

			}
			
		
		 
		return ib;
	}
	
	public InstituicaoBancaria getDadosSalvar() {
		
		InstituicaoBancaria ib = new InstituicaoBancaria();
		
		String nome,  s_saldo_inicial, s_taxa_intermediacao, s_taxa_parcelamento_mensal, s_tarifa_fixa_transacao, descricao, observacao;
		int id_cliente = 0, id_conta = 0;
		
		nome = entNome.getText();
		s_saldo_inicial = entSaldoInicial.getText();
		s_taxa_intermediacao =    entTaxaIntermediacao.getText();
		s_taxa_parcelamento_mensal = entTaxaParcelamento.getText();
		s_tarifa_fixa_transacao = entTarifaFixaTransacao.getText();
		descricao = entDescricao.getText();
		observacao = entObservacao.getText();
		
		if(cliente_selecionado != null) {
			id_cliente = cliente_selecionado.getId();
		
			ContaBancaria cb_selecionada = (ContaBancaria) modelCC.getSelectedItem();	

			if(cb_selecionada != null)
				id_conta = cb_selecionada.getId_conta();
			else 
				id_conta = 0;
			
			
		}else {
			id_cliente = 0;
			id_conta = 0;
			
		}
		
		ib.setNome_instituicao_bancaria(nome);
		
		BigDecimal saldo_inicial = BigDecimal.ZERO;
		BigDecimal taxa_intermediacao = BigDecimal.ZERO;
		BigDecimal taxa_parcelamento_mensal = BigDecimal.ZERO;
		BigDecimal tarifa_fixa_transacao = BigDecimal.ZERO;

		s_saldo_inicial = s_saldo_inicial.replace("[^0-9,]", "");
		s_saldo_inicial = s_saldo_inicial.replace(",", ".");
		
		s_taxa_intermediacao = s_taxa_intermediacao.replace("[^0-9,]", "");
		s_taxa_intermediacao = s_taxa_intermediacao.replace(",", ".");
		
		s_taxa_parcelamento_mensal = s_taxa_parcelamento_mensal.replace("[^0-9,]", "");
		s_taxa_parcelamento_mensal = s_taxa_parcelamento_mensal.replace(",", ".");
		
		s_tarifa_fixa_transacao = s_tarifa_fixa_transacao.replace("[^0-9,]", "");
		s_tarifa_fixa_transacao = s_tarifa_fixa_transacao.replace(",", ".");
		
		

		try {
			saldo_inicial = new BigDecimal(s_saldo_inicial);
			ib.setSaldo_inicial(saldo_inicial);

		}catch(Exception e) {
			ib.setSaldo_inicial(BigDecimal.ZERO);

		}
		
		try {
			taxa_intermediacao = new BigDecimal(s_taxa_intermediacao);
			ib.setTaxa_intermediacao(taxa_intermediacao);

		}catch(Exception e) {
			ib.setTaxa_intermediacao(BigDecimal.ZERO);

		}
		
		try {
			taxa_parcelamento_mensal = new BigDecimal(s_taxa_parcelamento_mensal);
			ib.setTaxa_parcelamento_mensal(taxa_parcelamento_mensal);

		}catch(Exception e) {
			ib.setTaxa_parcelamento_mensal(BigDecimal.ZERO);

		}	
		
		try {
			tarifa_fixa_transacao = new BigDecimal(s_tarifa_fixa_transacao);
			ib.setTaxa_fixa_transacao(tarifa_fixa_transacao);

		}catch(Exception e) {
			ib.setTaxa_fixa_transacao(BigDecimal.ZERO);

		}	
			
		
		
		try {
		ib.setId_cliente(id_cliente);
		
		}catch(Exception e) {
			ib.setId_cliente(0);
		}
		
		try {
			ib.setId_conta(id_conta);
			
			}catch(Exception e) {
				ib.setId_conta(0);

			}
			
		
		 
		return ib;
	}
	
}
