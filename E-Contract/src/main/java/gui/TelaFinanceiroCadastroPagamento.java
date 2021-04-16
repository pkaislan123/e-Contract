package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.Window;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.FileChooser;
import keeptoo.KGradientPanel;
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
import main.java.cadastros.FinanceiroPagamento;
import main.java.cadastros.Parcela;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.classesExtras.Endereco;
import main.java.classesExtras.RenderizadorContato;
import main.java.conexaoBanco.GerenciarBancoAditivos;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoCondicaoPagamentos;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoDocumento;
import main.java.conexaoBanco.GerenciarBancoFinanceiroPagamento;
import main.java.conexaoBanco.GerenciarBancoNotasFiscais;
import main.java.conexaoBanco.GerenciarBancoPadrao;
import main.java.conexaoBanco.GerenciarBancoParcelas;
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
import main.java.relatoria.RelatorioContratoSimplificado;
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
import main.java.conexaoBanco.GerenciarBancoLancamento;
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
import main.java.classesExtras.ComboBoxRenderPersonalizado;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoSafras;

import javax.swing.border.LineBorder;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;



public class TelaFinanceiroCadastroPagamento extends JFrame {

	private final KGradientPanel painelPrincipal = new KGradientPanel();
    private JLabel lblTotalContratosConcluidos, lblTotalContratos, lblTotalContratosAbertos;
    private TelaFinanceiroCadastroPagamento isto;
    private JDialog telaPai;
    private JTextFieldPersonalizado entValor;
    private JTextFieldPersonalizado entDescricao;
    private JTextFieldPersonalizado entDataVencimento;
    private JTextFieldPersonalizado entCaminhoArquivo;
	private JTextFieldPersonalizado entIdentificador = new JTextFieldPersonalizado();
	private JEditorPane entObservacao = new JEditorPane();
	private CondicaoPagamento condicao_pagamento;
	private JComboBox cbCondicaoPagamento ;

	public TelaFinanceiroCadastroPagamento(int modo_operacao, FinanceiroPagamento pagamento, int id_lancamento_pai, Window janela_pai) {

		 isto = this;
		
		setResizable(true);
		if(modo_operacao == 0)
		setTitle("E-Contract - Cadastro Parcela");
		else if(modo_operacao == 1)
			setTitle("E-Contract - Edição Parcela");
		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 571, 416);
		painelPrincipal.kStartColor = new Color(255, 255, 204);
		painelPrincipal.kEndColor = new Color(255, 204, 153);
		painelPrincipal.setBackground(new Color(0, 51, 51));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(new MigLayout("", "[][grow][][]", "[][][][][][][][50px:n,grow][]"));
		
		JLabel lblIdentificador = new JLabel("Identificador:");
		lblIdentificador.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblIdentificador, "cell 0 1,alignx trailing");
		
		entIdentificador.setForeground(Color.black);
		painelPrincipal.add(entIdentificador, "cell 1 1 3 1,growx");
		
		JLabel lblValor = new JLabel("Valor:");
		lblValor.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblValor, "cell 0 2,alignx trailing");
		
		entValor = new JTextFieldPersonalizado();
		entValor.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(entValor, "cell 1 2 3 1,growx");
		entValor.setForeground(Color.black);
		entValor.setColumns(10);
		
		JLabel lblDataVencimento = new JLabel("Data Pagamento:");
		lblDataVencimento.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblDataVencimento, "cell 0 3,alignx trailing");
		
		entDataVencimento = new JTextFieldPersonalizado();
		entDataVencimento.setFont(new Font("Tahoma", Font.PLAIN, 16));
		entDataVencimento.setColumns(10);
		entDataVencimento.setForeground(Color.black);

		painelPrincipal.add(entDataVencimento, "cell 1 3 3 1,growx");
		
		JLabel lblNewLabel = new JLabel("Descrição:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblNewLabel, "cell 0 4,alignx right");
		
		entDescricao = new JTextFieldPersonalizado();
		entDescricao.setFont(new Font("Tahoma", Font.PLAIN, 16));
		entDescricao.setColumns(10);
		entDescricao.setForeground(Color.black);
		painelPrincipal.add(entDescricao, "cell 1 4 3 1,growx");
		
		JLabel lblCondioDoPagamento = new JLabel("Condição do Pagamento:");
		lblCondioDoPagamento.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblCondioDoPagamento, "cell 0 5,alignx trailing");
		
		 cbCondicaoPagamento = new JComboBox();
		cbCondicaoPagamento.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelPrincipal.add(cbCondicaoPagamento, "cell 1 5 2 1,growx");
		
		JButton btnNewButton_1_1 = new JButton("Selecionar");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				TelaFinanceiroCondicaoPagamento tela = new TelaFinanceiroCondicaoPagamento(0,1,isto);
				tela.setVisible(true);
			}
		});
		btnNewButton_1_1.setForeground(Color.WHITE);
		btnNewButton_1_1.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnNewButton_1_1.setBackground(new Color(0, 51, 0));
		painelPrincipal.add(btnNewButton_1_1, "cell 3 5");
		
		JLabel lblArquivo = new JLabel("Arquivo:");
		lblArquivo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblArquivo, "cell 0 6,alignx trailing");
		
		entCaminhoArquivo = new JTextFieldPersonalizado();
		entCaminhoArquivo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		entCaminhoArquivo.setColumns(10);
		entCaminhoArquivo.setForeground(Color.black);

		painelPrincipal.add(entCaminhoArquivo, "cell 1 6 2 1,growx");
		
		JButton btnFinalizar = new JButton("Finalizar");
		btnFinalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GerenciarBancoFinanceiroPagamento gerenciar = new GerenciarBancoFinanceiroPagamento();
				int result = gerenciar.inserirFinanceiroPagamento(getDadosSalvar(id_lancamento_pai));
				if (result > 0) {
					JOptionPane.showMessageDialog(isto, "Pagamento Inserido");
					((TelaFinanceiroGerenciarLancamento) janela_pai).atualizar_informacoes();
					isto.dispose();
				} else {
					JOptionPane.showMessageDialog(isto, "Erro ao Salvar\nConsulte o Administrador");

				}
			}
		});
		
		JButton btnNewButton_1 = new JButton("Selecionar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selecionarArquivo();
			}
		});
		btnNewButton_1.setBackground(new Color(0, 51, 0));
		btnNewButton_1.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnNewButton_1.setForeground(Color.WHITE);
		painelPrincipal.add(btnNewButton_1, "cell 3 6,alignx left");
		
		JLabel lblObservao = new JLabel("Observação:");
		lblObservao.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblObservao, "cell 0 7,alignx right");
		
		 entObservacao = new JEditorPane();
		painelPrincipal.add(entObservacao, "cell 1 7 2 1,grow");
		btnFinalizar.setBackground(new Color(0, 0, 102));
		btnFinalizar.setForeground(Color.WHITE);
		btnFinalizar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(btnFinalizar, "cell 1 8,alignx right");
		
		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GerenciarBancoFinanceiroPagamento gerenciar = new GerenciarBancoFinanceiroPagamento();
				boolean result = gerenciar.atualizarFinanceiroPagamento(getDadosAtualizar(pagamento));
				if (result) {
					JOptionPane.showMessageDialog(isto, "Pagamento Atualizado");
					((TelaFinanceiroGerenciarLancamento) janela_pai).atualizar_informacoes();
					isto.dispose();
				} else {
					JOptionPane.showMessageDialog(isto, "Erro ao Salvar\nConsulte o Administrador");

				}
			}
		});
		btnAtualizar.setForeground(Color.WHITE);
		btnAtualizar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnAtualizar.setBackground(new Color(0, 0, 102));
		painelPrincipal.add(btnAtualizar, "cell 2 8");
	
		
		if(modo_operacao == 0) {
			//novo cadastro
			btnAtualizar.setEnabled(false);
			btnAtualizar.setVisible(false);
		}else if(modo_operacao == 1) {
			//edicao
			
			rotinasEdicao(pagamento);
			
			btnFinalizar.setEnabled(false);
			btnFinalizar.setVisible(false);
		}
		

		this.setLocationRelativeTo(janela_pai);

		
		
		
	}
	
	public void rotinasEdicao(FinanceiroPagamento pagamento) {
   String s_valor, descricao, observacao, identificador, data_pagamento, caminho_arquivo;
		int status;
		identificador = pagamento.getIdentificador();
		observacao = pagamento.getObservacao();
		descricao = pagamento.getDescricao();
		data_pagamento = pagamento.getData_pagamento();
		caminho_arquivo = pagamento.getCaminho_arquivo();
		
		
		BigDecimal valor = pagamento.getValor();
		
		entIdentificador.setText(identificador);
		entObservacao.setText(observacao);
		entDescricao.setText(descricao);
		entDataVencimento.setText(data_pagamento);
		entCaminhoArquivo.setText(caminho_arquivo);
		entValor.setText(valor.toString());
		
		setCondicaoPagamento(new GerenciarBancoCondicaoPagamentos().getCondicaoPagamento(pagamento.getId_condicao_pagamento()));
		
	}
	
	public void selecionarArquivo() {

		JOptionPane.showMessageDialog(isto, "Na próxima tela, importe o arquivo referente a sua parcela!");

		new JFXPanel();
		Platform.runLater(() -> {
			FileChooser d = new FileChooser();
			File file = d.showOpenDialog(null);
			String caminho_arquivo = "";
			if (file != null) {
				caminho_arquivo = file.getAbsolutePath();
				entCaminhoArquivo.setText(caminho_arquivo);
				// JOptionPane.showMessageDialog(isto, "CAminho do arquivo selecionado: " +
				// file.getAbsolutePath());		
			}
		});
	}
	
	public FinanceiroPagamento getDadosSalvar(int id_lancamento_pai) {
		FinanceiroPagamento pagamento = new FinanceiroPagamento();
		
		String s_valor, descricao, observacao, identificador, data_pagamento, caminho_arquivo;
		
		identificador = entIdentificador.getText();
		observacao = entObservacao.getText();
		descricao = entDescricao.getText();
		data_pagamento = entDataVencimento.getText();
		caminho_arquivo = entCaminhoArquivo.getText();
		BigDecimal valor = BigDecimal.ZERO;
		
		try {
			s_valor = entValor.getText();
			s_valor = s_valor.replace("[^0-9,]", "");
			s_valor = s_valor.replace(",", ".");
			valor = new BigDecimal(s_valor);
			pagamento.setValor(valor);

		}catch(Exception e) {
			pagamento.setValor(BigDecimal.ZERO);
		}
		
		pagamento.setDescricao(descricao);
		pagamento.setData_pagamento(data_pagamento);
		pagamento.setCaminho_arquivo(caminho_arquivo);
		pagamento.setId_lancamento(id_lancamento_pai);
		pagamento.setIdentificador(identificador);
		pagamento.setObservacao(observacao);
		
		if(condicao_pagamento != null)
		 pagamento.setId_condicao_pagamento(condicao_pagamento.getId_condicao_pagamento());
		
		
		return pagamento;
	}
	
	
	public FinanceiroPagamento getDadosAtualizar(FinanceiroPagamento pagamento_antigo) {
FinanceiroPagamento pagamento = new FinanceiroPagamento();
 	pagamento.setId_pagamento(pagamento_antigo.getId_pagamento());
		
		String s_valor, descricao, observacao, identificador, data_pagamento, caminho_arquivo;
		
		identificador = entIdentificador.getText();
		observacao = entObservacao.getText();
		descricao = entDescricao.getText();
		data_pagamento = entDataVencimento.getText();
		caminho_arquivo = entCaminhoArquivo.getText();
		BigDecimal valor = BigDecimal.ZERO;
		
		try {
			s_valor = entValor.getText();
			s_valor = s_valor.replace("[^0-9,]", "");
			s_valor = s_valor.replace(",", ".");
			valor = new BigDecimal(s_valor);
			pagamento.setValor(valor);

		}catch(Exception e) {
			pagamento.setValor(BigDecimal.ZERO);
		}
		
		pagamento.setDescricao(descricao);
		pagamento.setData_pagamento(data_pagamento);
		pagamento.setCaminho_arquivo(caminho_arquivo);
		pagamento.setIdentificador(identificador);
		pagamento.setObservacao(observacao);
		
		if(condicao_pagamento != null)
		 pagamento.setId_condicao_pagamento(condicao_pagamento.getId_condicao_pagamento());
		
		
		return pagamento;
	}
	
	
	public void setTelaPai(JDialog _tela_pai) {
		this.telaPai = _tela_pai;
	}
	
	public void setCondicaoPagamento(CondicaoPagamento _condicao) {
		this.condicao_pagamento = _condicao;
		
		cbCondicaoPagamento.removeAllItems();
		cbCondicaoPagamento.addItem(_condicao.getNome_condicao_pagamento());
	}
	
	
}