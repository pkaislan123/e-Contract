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
import main.java.conexaoBanco.GerenciarBancoCondicaoPagamentos;
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
import main.java.cadastros.CondicaoPagamento;
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



public class TelaFinanceiroCadastroCondicaoPagamento extends JDialog {

	private final JPanel painelPrincipal = new JPanel();
	private final JPanel painelOdin = new JPanel();

    private JLabel lblTotalContratosConcluidos, lblTotalContratos, lblTotalContratosAbertos;
    private TelaFinanceiroCadastroCondicaoPagamento isto;
    private JDialog telaPai;
    
    private JTextFieldPersonalizado entNome;
    private final JLabel lblSaldoInicial = new JLabel("Número de Parcelas:");
    private final JTextFieldPersonalizado entNumeroParcelas = new JTextFieldPersonalizado();
    private final JTextFieldPersonalizado entIntervalo = new JTextFieldPersonalizado();
    private final JTextFieldPersonalizado entDiaRecebimento = new JTextFieldPersonalizado();
    private final JLabel lbasdas = new JLabel("Intervalo:");
    private final JLabel lblTaxaDeParcelamento = new JLabel("Dia do Recebimento:");
    private final JButton btnCadastrar = new JButton("Cadastrar");
    private final JButton btnAtualizar = new JButton("Atualizar");
    private final JEditorPane entDescricao = new JEditorPane();
    private final JLabel lblDescrio = new JLabel("Descrição:");
    private final JLabel lblObservao = new JLabel("Observação:");
    private final JEditorPane entObservacao = new JEditorPane();
    private final JPanel panel = new JPanel();
    private final JLabel lblNewLabel = new JLabel("Cadastro Condição de Pagamento");
    private final JLabel lblCliente = new JLabel("Forma de Pagamento:");
    private final JPanel panel_1 = new JPanel();
    private final JComboBox cbFormaPagamento = new JComboBox();
    private final JPanel panel_2 = new JPanel();
    private final JPanel panel_3 = new JPanel();
    private final JPanel panel_4 = new JPanel();
    private CadastroCliente cliente_selecionado = null;


private ComboBoxPersonalizadoContaBancaria modelCC= new ComboBoxPersonalizadoContaBancaria();
private ComboBoxRenderPersonalizadoContaBancaria renderCC  = new ComboBoxRenderPersonalizadoContaBancaria() ;
private final JLabel lblNewLabel_1 = new JLabel("dias");
    
	public TelaFinanceiroCadastroCondicaoPagamento(int modo_operacao,CondicaoPagamento condicao_pagamento, Window janela_pai) {

		
		
		
		 isto = this;
		if(modo_operacao == 0)
		setTitle("E-Contract - Cadastro Condição de Pagamento");
		else
			setTitle("E-Contract - Edição de Cadastro Condição de Pagamento");

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
		 entNome.setText("");
		 entNome.setForeground(Color.BLACK);
		panel_2.add(lblCliente, "cell 0 1,growx,aligny center");
		lblCliente.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCliente.setForeground(Color.BLACK);
		lblCliente.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCliente.setBackground(Color.ORANGE);
		panel_2.add(panel_1, "cell 1 1,growx,aligny top");
		panel_1.setBackground(new Color(0, 51, 102));
		panel_1.setLayout(new MigLayout("", "[grow]", "[]"));
		cbFormaPagamento.setOpaque(false);
		cbFormaPagamento.setBackground(Color.WHITE);
		cbFormaPagamento.setFont(new Font("SansSerif", Font.BOLD, 16));
		cbFormaPagamento.addItem("Dinheiro");
		cbFormaPagamento.addItem("Cheque");
		cbFormaPagamento.addItem("Cheque Pré");
		cbFormaPagamento.addItem("Cartão Crédito");
		cbFormaPagamento.addItem("Cartão Debito");
		cbFormaPagamento.addItem("Prazo");
		cbFormaPagamento.addItem("Vale Crédito");
		cbFormaPagamento.addItem("Outro");

		
		panel_1.add(cbFormaPagamento, "flowx,cell 0 0,growx");
		
	

		panel_2.add(lblSaldoInicial, "cell 0 2,growx,aligny center");
		lblSaldoInicial.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSaldoInicial.setForeground(Color.BLACK);
		lblSaldoInicial.setFont(new Font("Arial", Font.PLAIN, 16));
		lblSaldoInicial.setBackground(Color.ORANGE);
		panel_2.add(entNumeroParcelas, "cell 1 2,growx,aligny top");
		
		entNumeroParcelas.setForeground(Color.BLACK);
		panel_2.add(lbasdas, "cell 0 3,growx,aligny center");
		lbasdas.setHorizontalAlignment(SwingConstants.TRAILING);
		lbasdas.setForeground(Color.BLACK);
		lbasdas.setFont(new Font("Arial", Font.PLAIN, 16));
		lbasdas.setBackground(Color.ORANGE);
		panel_2.add(entIntervalo, "flowx,cell 1 3,growx,aligny top");
		
		entIntervalo.setForeground(Color.BLACK);
		panel_2.add(lblTaxaDeParcelamento, "cell 0 4,growx,aligny center");
		lblTaxaDeParcelamento.setHorizontalAlignment(SwingConstants.TRAILING);
		lblTaxaDeParcelamento.setForeground(Color.BLACK);
		lblTaxaDeParcelamento.setFont(new Font("Arial", Font.PLAIN, 16));
		lblTaxaDeParcelamento.setBackground(Color.ORANGE);
		panel_2.add(entDiaRecebimento, "cell 1 4,growx,aligny top");
		
		entDiaRecebimento.setForeground(Color.BLACK);
		panel_2.add(lblDescrio, "cell 0 5,growx,aligny top");
		lblDescrio.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDescrio.setForeground(Color.BLACK);
		lblDescrio.setFont(new Font("Arial", Font.PLAIN, 16));
		lblDescrio.setBackground(Color.ORANGE);
		panel_2.add(entDescricao, "cell 1 5,grow");
		entDescricao.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_2.add(lblObservao, "cell 0 6,growx,aligny top");
		lblObservao.setHorizontalAlignment(SwingConstants.TRAILING);
		lblObservao.setForeground(Color.BLACK);
		lblObservao.setFont(new Font("Arial", Font.PLAIN, 16));
		lblObservao.setBackground(Color.ORANGE);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		panel_2.add(lblNewLabel_1, "cell 1 3");
		panel_2.add(entObservacao, "cell 1 6,grow");
		entObservacao.setForeground(Color.BLACK);
		entObservacao.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_4.setBackground(new Color(0, 0, 51));
		
		painelPrincipal.add(panel_4, "cell 5 0 1 4,grow");
		panel_3.setBackground(Color.WHITE);
		
		painelPrincipal.add(panel_3, "cell 2 3 3 1,grow");
		panel_3.setLayout(new MigLayout("", "[][]", "[]"));
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 GerenciarBancoCondicaoPagamentos gerenciar = new GerenciarBancoCondicaoPagamentos();
				 boolean result = gerenciar.atualizarCondicaoPagamento(getDadosAtualizar(condicao_pagamento));
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
				
				GerenciarBancoCondicaoPagamentos gerenciar = new GerenciarBancoCondicaoPagamentos();
			 int result = gerenciar.inserirCondicaoPagamento(getDadosSalvar());
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
			rotinasEdicao(condicao_pagamento);
	
			
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
	
	public void rotinasEdicao(CondicaoPagamento condicao_pagamento) {
	
		entNome.setText(condicao_pagamento.getNome_condicao_pagamento());
		cbFormaPagamento.setSelectedIndex(condicao_pagamento.getForma_pagamento() -1);
		entIntervalo.setText(condicao_pagamento.getIntervalo() + "");
		entDiaRecebimento.setText(condicao_pagamento.getDia_recebimento() + "");
		entNumeroParcelas.setText(condicao_pagamento.getNumero_parcelas() + "");
		entObservacao.setText(condicao_pagamento.getObservacao());
		entDescricao.setText(condicao_pagamento.getDescricao());


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
	
	
	
	
	
	public CondicaoPagamento getDadosAtualizar(CondicaoPagamento condicao_pagamento_antiga) {
		
		CondicaoPagamento condicao_pagamento = new CondicaoPagamento();
		condicao_pagamento.setId_condicao_pagamento(condicao_pagamento_antiga.getId_condicao_pagamento());
		
		String nome, observacao, descricao;
		int numero_parcelas, intervalo, dia_recebimento, forma_pagamento;
		
		nome = entNome.getText();
		observacao = entObservacao.getText();
		descricao = entDescricao.getText();
		
		condicao_pagamento.setNome_condicao_pagamento(nome);
		condicao_pagamento.setObservacao(observacao);
		condicao_pagamento.setDescricao(descricao);
		
		try {
			numero_parcelas = Integer.parseInt(entNumeroParcelas.getText());
			condicao_pagamento.setNumero_parcelas(numero_parcelas);
			
		}catch(Exception e) {
		}
		
		try {
			intervalo = Integer.parseInt(entIntervalo.getText());
			condicao_pagamento.setIntervalo(intervalo);
			
		}catch(Exception e) {
		}
		
		try {
			dia_recebimento = Integer.parseInt(entDiaRecebimento.getText());
			condicao_pagamento.setDia_recebimento(dia_recebimento);
			
		}catch(Exception e) {

		}
		
		
		try {
			forma_pagamento = cbFormaPagamento.getSelectedIndex() + 1;
			condicao_pagamento.setForma_pagamento(forma_pagamento);
			
		}catch(Exception e) {
			
		}
		
		 
		return condicao_pagamento;
	}
	
	public CondicaoPagamento getDadosSalvar() {
		
		CondicaoPagamento condicao_pagamento = new CondicaoPagamento();
		
		String nome, observacao, descricao;
		int numero_parcelas, intervalo, dia_recebimento, forma_pagamento;
		
		nome = entNome.getText();
		observacao = entObservacao.getText();
		descricao = entDescricao.getText();
		
		condicao_pagamento.setNome_condicao_pagamento(nome);
		condicao_pagamento.setObservacao(observacao);
		condicao_pagamento.setDescricao(descricao);
		
		try {
			numero_parcelas = Integer.parseInt(entNumeroParcelas.getText());
			condicao_pagamento.setNumero_parcelas(numero_parcelas);
			
		}catch(Exception e) {
			
		}
		
		try {
			intervalo = Integer.parseInt(entIntervalo.getText());
			condicao_pagamento.setIntervalo(intervalo);
			
		}catch(Exception e) {
			
		}
		
		try {
			dia_recebimento = Integer.parseInt(entDiaRecebimento.getText());
			condicao_pagamento.setDia_recebimento(dia_recebimento);
			
		}catch(Exception e) {
			
		}
		
		
		
		try {
			forma_pagamento = cbFormaPagamento.getSelectedIndex() + 1;
			condicao_pagamento.setForma_pagamento(forma_pagamento);
			
		}catch(Exception e) {
			
		}
		
		 
		return condicao_pagamento;
	}
	
}
