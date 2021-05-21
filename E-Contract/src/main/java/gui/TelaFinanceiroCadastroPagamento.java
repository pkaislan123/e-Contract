package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.commons.io.FilenameUtils;
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
import main.java.cadastros.InstituicaoBancaria;
import main.java.cadastros.Lancamento;
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
import main.java.cadastros.CondicaoPagamento;
import main.java.outros.DadosGlobais;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroGrupo;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoGrupos;
import main.java.conexaoBanco.GerenciarBancoInstituicaoBancaria;
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
import javax.swing.JRadioButton;



public class TelaFinanceiroCadastroPagamento extends JFrame {

	private final KGradientPanel painelPrincipal = new KGradientPanel();
    private JLabel lblTotalContratosConcluidos, lblTotalContratos, lblTotalContratosAbertos;
    private TelaFinanceiroCadastroPagamento isto;
    private JDialog telaPai;
    private JTextFieldPersonalizado entValor;
    private JTextFieldPersonalizado entDescricao;
    private JTextFieldPersonalizado entDataVencimento;
	private JTextFieldPersonalizado entIdentificador = new JTextFieldPersonalizado();
	private JEditorPane entObservacao = new JEditorPane();
	private CondicaoPagamento condicao_pagamento;
	private JComboBox cbCondicaoPagamento ,cbStatusCondicaoPagamento;
	private InstituicaoBancaria pagador_ib;
	private InstituicaoBancaria recebedor_ib;
	private JRadioButton rdbtnPagadorIB,rdbtnUmClienteforncedor;
	private JRadioButton rdbtnFluxoDeCaixaSim,rdbtnFluxoDeCaixaNao;

	private CadastroCliente pagador_cliente_fornecedor;
	private JComboBox cbPagador,cbRecebedor ;
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private String servidor_unidade;
	
	public TelaFinanceiroCadastroPagamento(int modo_operacao, FinanceiroPagamento pagamento, Lancamento lancamento_pai, Window janela_pai) {

		 isto = this;
		 getDadosGlobais();
			servidor_unidade = configs_globais.getServidorUnidade();
		setResizable(true);
		if(modo_operacao == 0)
		setTitle("E-Contract - Cadastro Pagamento");
		else if(modo_operacao == 1)
			setTitle("E-Contract - Edição Pagamento");
		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 809, 600);
		painelPrincipal.kStartColor = new Color(255, 255, 204);
		painelPrincipal.kEndColor = new Color(255, 204, 153);
		painelPrincipal.setBackground(new Color(0, 51, 51));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(new MigLayout("", "[][grow][][]", "[grow][][][][][][][][grow][][][][50px:n,grow][]"));
		
		JLabel lblFluxoDeCaixa = new JLabel("Fluxo de Caixa?:");
		lblFluxoDeCaixa.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblFluxoDeCaixa, "cell 0 0,alignx right");
		
		JPanel panel_1 = new JPanel();
		panel_1.setOpaque(false);
		panel_1.setBorder(null);
		panel_1.setBackground(Color.BLACK);
		painelPrincipal.add(panel_1, "cell 1 0 2 1,grow");
		panel_1.setLayout(new MigLayout("", "[][]", "[]"));
		
		
		rdbtnFluxoDeCaixaSim	  = new JRadioButton("Sim");
		rdbtnFluxoDeCaixaSim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnFluxoDeCaixaSim.setSelected(true);
				rdbtnFluxoDeCaixaNao.setSelected(false);

			}
		});
		rdbtnFluxoDeCaixaSim.setSelected(true);
		rdbtnFluxoDeCaixaSim.setFont(new Font("SansSerif", Font.PLAIN, 14));
		panel_1.add(rdbtnFluxoDeCaixaSim, "cell 0 0");
		
		 rdbtnFluxoDeCaixaNao = new JRadioButton("Não");
		 rdbtnFluxoDeCaixaNao.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		rdbtnFluxoDeCaixaSim.setSelected(false);
				rdbtnFluxoDeCaixaNao.setSelected(true);
		 	}
		 });
		rdbtnFluxoDeCaixaNao.setFont(new Font("SansSerif", Font.PLAIN, 14));
		panel_1.add(rdbtnFluxoDeCaixaNao, "cell 1 0");
		
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
		
		JLabel lblStatus = new JLabel("Status:");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblStatus, "cell 0 6,alignx trailing");
		
		 cbStatusCondicaoPagamento = new JComboBox();
		cbStatusCondicaoPagamento.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelPrincipal.add(cbStatusCondicaoPagamento, "cell 1 6 2 1,growx");
		cbStatusCondicaoPagamento.addItem("A - Compensar|Realizar|Concluir");
		cbStatusCondicaoPagamento.addItem("Compensado|Realizado|Concluído");
		
		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setOpaque(false);
		panel.setBackground(new Color(0, 0, 0));
		painelPrincipal.add(panel, "cell 1 8 2 1,grow");
		panel.setLayout(new MigLayout("", "[][grow]", "[grow]"));
		
		
		 rdbtnPagadorIB = new JRadioButton("Uma Instituição Bancaria?");
		rdbtnPagadorIB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnPagadorIB.setSelected(true);
				rdbtnUmClienteforncedor.setSelected(false);
			}
		});
		rdbtnPagadorIB.setSelected(true);
		panel.add(rdbtnPagadorIB, "cell 0 0");
		rdbtnPagadorIB.setFont(new Font("SansSerif", Font.PLAIN, 14));
		
		 rdbtnUmClienteforncedor = new JRadioButton("Um Cliente/Forncedor?");
		 rdbtnUmClienteforncedor.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		
		 		rdbtnPagadorIB.setSelected(false);
				rdbtnUmClienteforncedor.setSelected(true);
		 	}
		 });
		rdbtnUmClienteforncedor.setFont(new Font("SansSerif", Font.PLAIN, 14));
		panel.add(rdbtnUmClienteforncedor, "cell 1 0");
		
		JLabel lblPagador = new JLabel("Pagador:");
		lblPagador.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblPagador, "cell 0 9,alignx trailing");
		
		 cbPagador = new JComboBox();
		cbPagador.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelPrincipal.add(cbPagador, "cell 1 9 2 1,growx");
		
		JButton btnSelecionarPagador = new JButton("Selecionar");
		btnSelecionarPagador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(lancamento_pai.getTipo_lancamento() == 0) {
					//despesa, selecionar I.B
					if(rdbtnPagadorIB.isSelected()) {
					TelaFinanceiroInstituicaoBancaria tela =  new  TelaFinanceiroInstituicaoBancaria ( 0 , 1 , isto);
					tela . setVisible ( true );
					}else if(rdbtnUmClienteforncedor.isSelected()) {
						TelaCliente tela = new TelaCliente(0, 31, isto);
						tela.setVisible(true);
					}
				}else if(lancamento_pai.getTipo_lancamento() == 1) {
					//receita
					TelaCliente tela = new TelaCliente(0, 31, isto);
					tela.setVisible(true);
				}
			}
		});
		btnSelecionarPagador.setForeground(Color.WHITE);
		btnSelecionarPagador.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnSelecionarPagador.setBackground(new Color(0, 51, 0));
		painelPrincipal.add(btnSelecionarPagador, "cell 3 9");
		
		JLabel lblRecebedor = new JLabel("Recebedor:");
		lblRecebedor.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblRecebedor, "cell 0 10,alignx trailing");
		
		 cbRecebedor = new JComboBox();
		cbRecebedor.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelPrincipal.add(cbRecebedor, "cell 1 10 2 1,growx");
		
		JButton btnSelecionarRecebedor = new JButton("Selecionar");
		btnSelecionarRecebedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 if(lancamento_pai.getTipo_lancamento() == 1) {
						//despesa, selecionar I.B
						TelaFinanceiroInstituicaoBancaria tela =  new  TelaFinanceiroInstituicaoBancaria ( 0 ,2 , isto);
						tela . setVisible ( true );
						
					}
			}
		});
		btnSelecionarRecebedor.setForeground(Color.WHITE);
		btnSelecionarRecebedor.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnSelecionarRecebedor.setBackground(new Color(0, 51, 0));
		painelPrincipal.add(btnSelecionarRecebedor, "cell 3 10");
		
		JButton btnFinalizar = new JButton("Finalizar");
		btnFinalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GerenciarBancoFinanceiroPagamento gerenciar = new GerenciarBancoFinanceiroPagamento();
				FinanceiroPagamento pag = getDadosSalvar(lancamento_pai);
				if(pag != null) {
				int result = gerenciar.inserirFinanceiroPagamento(pag);
				if (result > 0) {
					/////*****************
					//verificar se a pasta do pagamento existe
					ManipularTxt manipular = new ManipularTxt();
					File pasta_de_pagamentos = new File(servidor_unidade + lancamento_pai.getDiretorio_lancamento() + "\\pagamento_" + result);
					if(!(pasta_de_pagamentos.exists())) {
						manipular.criarDiretorio(pasta_de_pagamentos.getAbsolutePath());
					}
					
					//copiar o arquivo para a pasta da parcela
					//antes verificar se o arquivo nao e nulo
					if(pag.getCaminho_arquivo() != null && pag.getCaminho_arquivo().length() > 20) {
					boolean copiar_arquivo = false;
					String extensaoDoArquivo = FilenameUtils.getExtension(pag.getCaminho_arquivo());			

					String arquivo_doc_pagamento_final = pasta_de_pagamentos + "\\doc_comprovante_pagamento_" + result + "." + extensaoDoArquivo;
					try {
						copiar_arquivo = manipular.copiarNFe(pag.getCaminho_arquivo(), arquivo_doc_pagamento_final);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if(copiar_arquivo) {
						//atualiza parcela no banco de dados
						boolean  atualizar =  gerenciar.atualizarArquivoDoPagamento("doc_comprovante_pagamento_" + result + "." + extensaoDoArquivo, result);
						if(!atualizar)
							JOptionPane.showMessageDialog(isto, "Arquivo Copiado, Mas não atualizado no Banco de dados\nConsulte o administrador");

					}else {
						JOptionPane.showMessageDialog(isto, "Arquivo não copiado, consulte o administrador");

					}
					}

					
					////***********
					JOptionPane.showMessageDialog(isto, "Pagamento Inserido");
					atualizarValorLancamento(lancamento_pai);

					((TelaFinanceiroGerenciarLancamento) janela_pai).atualizar_informacoes();
					isto.dispose();
				} else {
					JOptionPane.showMessageDialog(isto, "Erro ao Salvar\nConsulte o Administrador");

				}
				}
			}
		});
		
		JLabel lblObservao = new JLabel("Observação:");
		lblObservao.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblObservao, "cell 0 12,alignx right");
		
		 entObservacao = new JEditorPane();
		painelPrincipal.add(entObservacao, "cell 1 12 2 1,grow");
		btnFinalizar.setBackground(new Color(0, 0, 102));
		btnFinalizar.setForeground(Color.WHITE);
		btnFinalizar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(btnFinalizar, "cell 1 13,alignx right");
		
		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GerenciarBancoFinanceiroPagamento gerenciar = new GerenciarBancoFinanceiroPagamento();
				FinanceiroPagamento pag = getDadosAtualizar(pagamento, lancamento_pai);
				if(pag != null) {
				boolean result = gerenciar.atualizarFinanceiroPagamento(pag);
				if (result) {
				
					JOptionPane.showMessageDialog(isto, "Pagamento Atualizado");
					atualizarValorLancamento(lancamento_pai);
					((TelaFinanceiroGerenciarLancamento) janela_pai).atualizar_informacoes();
					isto.dispose();
				} else {
					JOptionPane.showMessageDialog(isto, "Erro ao Salvar\nConsulte o Administrador");

				}
				}
			}
		});
		btnAtualizar.setForeground(Color.WHITE);
		btnAtualizar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnAtualizar.setBackground(new Color(0, 0, 102));
		painelPrincipal.add(btnAtualizar, "cell 2 13");
	
		
		if(modo_operacao == 0) {
			//novo cadastro
			
			
			btnAtualizar.setEnabled(false);
			btnAtualizar.setVisible(false);
		}else if(modo_operacao == 1) {
			//edicao
		
			btnFinalizar.setEnabled(false);
			btnFinalizar.setVisible(false);
			rotinasEdicao(pagamento,lancamento_pai);
		
		}
		

		if(lancamento_pai.getTipo_lancamento() == 0 ) {
			//é uma despesa
			lblRecebedor.setEnabled(false);
			lblRecebedor.setVisible(false);
			
			cbRecebedor.setEnabled(false);
			cbRecebedor.setVisible(false);
			
			btnSelecionarRecebedor.setEnabled(false);
			btnSelecionarRecebedor.setVisible(false);

		}
		

		this.setLocationRelativeTo(janela_pai);

		
		
		
	}
	
	public void rotinasEdicao(FinanceiroPagamento pagamento,Lancamento lancamento_pai) {
   String s_valor, descricao, observacao, identificador, data_pagamento;
		int status, tipo_pagador, fluxo_caixa;
		identificador = pagamento.getIdentificador();
		observacao = pagamento.getObservacao();
		descricao = pagamento.getDescricao();
		data_pagamento = pagamento.getData_pagamento();
		
		
GerenciarBancoInstituicaoBancaria gerenciar_ibs = new GerenciarBancoInstituicaoBancaria();
GerenciarBancoClientes gerenciar_clientes = new GerenciarBancoClientes();

		BigDecimal valor = pagamento.getValor();
		
		entIdentificador.setText(identificador);
		entObservacao.setText(observacao);
		entDescricao.setText(descricao);
		entDataVencimento.setText(data_pagamento);
		entValor.setText(valor.toString());
		cbStatusCondicaoPagamento.setSelectedIndex(pagamento.getStatus_pagamento());
		
		
		if(pagamento.getFluxo_caixa() == 1) {
			rdbtnFluxoDeCaixaSim.setSelected(true);
			rdbtnFluxoDeCaixaNao.setSelected(false);

		}else if(pagamento.getFluxo_caixa() == 0) {
			rdbtnFluxoDeCaixaSim.setSelected(false);
			rdbtnFluxoDeCaixaNao.setSelected(true);

		}
		
		
		if(pagamento.getTipo_pagador() == 0) {
			rdbtnPagadorIB.setSelected(true);
			rdbtnUmClienteforncedor.setSelected(false);
		}else if(pagamento.getTipo_pagador() == 1) {
			rdbtnPagadorIB.setSelected(false);
			rdbtnUmClienteforncedor.setSelected(true);

		}
		
		
		setCondicaoPagamento(new GerenciarBancoCondicaoPagamentos().getCondicaoPagamento(pagamento.getId_condicao_pagamento()));
		
		if(lancamento_pai.getTipo_lancamento() == 0) {
			//é uma despesa, retornar o id da ib
			
			if(pagamento.getTipo_pagador() == 0) {
				if(pagamento.getId_pagador()  > 0) {
					InstituicaoBancaria ib = gerenciar_ibs.getInstituicaoBancaria(pagamento.getId_pagador());
					if(ib != null)
						setInstituicaoBancaria(ib);
					}
			}else if(pagamento.getTipo_pagador() == 1) {
				if(pagamento.getId_pagador() > 0) {
					CadastroCliente cli = gerenciar_clientes.getCliente(pagamento.getId_pagador());
					if(cli != null)
						setClienteFornecedor(cli);
			}
			
			}
		}else if(lancamento_pai.getTipo_lancamento() == 1) {
			//é uma receita, retorna o id do cliente
			if(pagamento.getId_pagador() > 0) {
			CadastroCliente cli = gerenciar_clientes.getCliente(pagamento.getId_pagador());
			if(cli != null)
				setClienteFornecedor(cli);
		}
			if(pagamento.getId_recebedor() > 0) {
				InstituicaoBancaria ib = gerenciar_ibs.getInstituicaoBancaria(pagamento.getId_recebedor());
				if(ib != null)
					setInstituicaoBancariaRecebedora(ib);
			}
		}
		
	}
	
	public FinanceiroPagamento getDadosSalvar(Lancamento lancamento_pai) {
		FinanceiroPagamento pagamento = new FinanceiroPagamento();
		
		String s_valor, descricao, observacao, identificador, data_pagamento, caminho_arquivo;
		
		identificador = entIdentificador.getText();
		observacao = entObservacao.getText();
		descricao = entDescricao.getText();
		data_pagamento = entDataVencimento.getText();
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
		pagamento.setId_lancamento(lancamento_pai.getId_lancamento());
	
		pagamento.setIdentificador(identificador);
		pagamento.setObservacao(observacao);
		
		
		if(rdbtnFluxoDeCaixaSim.isSelected()) {
			pagamento.setFluxo_caixa(1);
		}else if(rdbtnFluxoDeCaixaNao.isSelected()) {
			pagamento.setFluxo_caixa(0);

		}
		
		if(condicao_pagamento != null)
		 pagamento.setId_condicao_pagamento(condicao_pagamento.getId_condicao_pagamento());
		else {
			JOptionPane.showMessageDialog(isto, "Selecione o pagador");

			return null;
		}
		pagamento.setStatus_pagamento(cbStatusCondicaoPagamento.getSelectedIndex());
		
		if(lancamento_pai.getTipo_lancamento() == 0) {
			//despesa
			
			if(rdbtnPagadorIB.isSelected()) {
			if(pagador_ib != null) {
				 pagamento.setTipo_pagador(0);

				pagamento.setId_pagador(pagador_ib.getId_instituicao_bancaria());
			}else {
				JOptionPane.showMessageDialog(isto, "Selecione a Instituição Bancária pagadora");

				return null;
			}
			
			}else if(rdbtnUmClienteforncedor.isSelected()) {

				if(pagador_cliente_fornecedor != null) {
					 pagamento.setTipo_pagador(1);

					pagamento.setId_pagador(pagador_cliente_fornecedor.getId());

				}else {
					JOptionPane.showMessageDialog(isto, "Selecione o Cliente/Fornecedor pagador");

					return null;
				}
			}
			
			
		}else if(lancamento_pai.getTipo_lancamento() == 1) {
			//receita
			if(pagador_cliente_fornecedor != null) {
				 pagamento.setTipo_pagador(1);

				pagamento.setId_pagador(pagador_cliente_fornecedor.getId());

			}else {
				JOptionPane.showMessageDialog(isto, "Selecione o pagador");

				return null;
			}
		}
		
		// recebedor
		if(lancamento_pai.getTipo_lancamento() == 1) {
			//é uma receita
			if(recebedor_ib != null) {
				pagamento.setId_recebedor(recebedor_ib.getId_instituicao_bancaria());

			}else {
				JOptionPane.showMessageDialog(isto, "Selecione o recebedor");

				return null;
			}
		}else {
			pagamento.setId_recebedor(0);
		}
		
		
		try {

			if (!isDateValid(data_pagamento)) {
				JOptionPane.showMessageDialog(isto, "Data do Pagamento Inválida");

				return null;
			} else {
				pagamento.setData_pagamento(data_pagamento);
				return pagamento;

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(isto, "Data do Pagamento Inválida");
			return null;
		}
	}
	
	
	public FinanceiroPagamento getDadosAtualizar(FinanceiroPagamento pagamento_antigo, Lancamento lancamento_pai) {
FinanceiroPagamento pagamento = new FinanceiroPagamento();
 	pagamento.setId_pagamento(pagamento_antigo.getId_pagamento());
		
		String s_valor, descricao, observacao, identificador, data_pagamento;
		
		identificador = entIdentificador.getText();
		observacao = entObservacao.getText();
		descricao = entDescricao.getText();
		data_pagamento = entDataVencimento.getText();
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

		pagamento.setIdentificador(identificador);
		pagamento.setObservacao(observacao);
		
		if(rdbtnFluxoDeCaixaSim.isSelected()) {
			pagamento.setFluxo_caixa(1);
		}else if(rdbtnFluxoDeCaixaNao.isSelected()) {
			pagamento.setFluxo_caixa(0);

		}
		
		if(condicao_pagamento != null)
			 pagamento.setId_condicao_pagamento(condicao_pagamento.getId_condicao_pagamento());
			else {
				JOptionPane.showMessageDialog(isto, "Selecione o pagador");

				return null;
			}
			pagamento.setStatus_pagamento(cbStatusCondicaoPagamento.getSelectedIndex());
			
			if(lancamento_pai.getTipo_lancamento() == 0) {
				//despesa
				
				if(rdbtnPagadorIB.isSelected()) {
				if(pagador_ib != null) {
					 pagamento.setTipo_pagador(0);

					pagamento.setId_pagador(pagador_ib.getId_instituicao_bancaria());
				}else {
					JOptionPane.showMessageDialog(isto, "Selecione a Instituição Bancária pagadora");

					return null;
				}
				
				}else if(rdbtnUmClienteforncedor.isSelected()) {

					if(pagador_cliente_fornecedor != null) {
						 pagamento.setTipo_pagador(1);

						pagamento.setId_pagador(pagador_cliente_fornecedor.getId());

					}else {
						JOptionPane.showMessageDialog(isto, "Selecione o Cliente/Fornecedor pagador");

						return null;
					}
				}
				
				
			}else if(lancamento_pai.getTipo_lancamento() == 1) {
				//receita
				if(pagador_cliente_fornecedor != null) {
					 pagamento.setTipo_pagador(1);

					pagamento.setId_pagador(pagador_cliente_fornecedor.getId());

				}else {
					JOptionPane.showMessageDialog(isto, "Selecione o pagador");

					return null;
				}
			}
			
			
			//recebedor
			
			if(lancamento_pai.getTipo_lancamento() == 1) {
				//é uma receita
				if(recebedor_ib != null) {
					pagamento.setId_recebedor(recebedor_ib.getId_instituicao_bancaria());

				}else {
					JOptionPane.showMessageDialog(isto, "Selecione o recebedor");

					return null;
				}
			}else {
				pagamento.setId_recebedor(0);
			}
			
			
			try {

				if (!isDateValid(data_pagamento)) {
					JOptionPane.showMessageDialog(isto, "Data do Pagamento Inválida");

					return null;
				} else {
					pagamento.setData_pagamento(data_pagamento);
					return pagamento;

				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(isto, "Data do Pagamento Inválida");
				return null;
			}
	}
	
	
	public void setTelaPai(JDialog _tela_pai) {
		this.telaPai = _tela_pai;
	}
	
	public void setCondicaoPagamento(CondicaoPagamento _condicao) {
		this.condicao_pagamento = _condicao;
		
		cbCondicaoPagamento.removeAllItems();
		cbCondicaoPagamento.addItem(_condicao.getNome_condicao_pagamento());
	}
	
	
	public  void  setInstituicaoBancaria ( InstituicaoBancaria  _instituicao_bancaria ) {
		pagador_ib = _instituicao_bancaria;

		cbPagador . removeAllItems ();
		cbPagador . addItem (_instituicao_bancaria . getNome_instituicao_bancaria ());
	}
	
	public  void  setInstituicaoBancariaRecebedora ( InstituicaoBancaria  _instituicao_bancaria ) {
		recebedor_ib = _instituicao_bancaria;

		cbRecebedor . removeAllItems ();
		cbRecebedor . addItem (_instituicao_bancaria . getNome_instituicao_bancaria ());
	}
	
	public void setClienteFornecedor (CadastroCliente _cliente_fornecedor) {
		pagador_cliente_fornecedor = _cliente_fornecedor;
		String nome_cliente = "";
		if(pagador_cliente_fornecedor.getTipo_pessoa() == 0) {
			nome_cliente = pagador_cliente_fornecedor.getNome_empresarial();
		}else {
			nome_cliente = pagador_cliente_fornecedor.getNome_fantaia();

		}
		cbPagador . removeAllItems ();
		cbPagador . addItem (nome_cliente);
		
	}
	
	public void getDadosGlobais() {
		//gerenciador de log
				DadosGlobais dados = DadosGlobais.getInstance();
				 GerenciadorLog = dados.getGerenciadorLog();
				 configs_globais = dados.getConfigs_globais();
				 
				 //usuario logado
				  login = dados.getLogin();
		
	}
	
public void atualizarValorLancamento(Lancamento lancamento_pai) {
		

		GerenciarBancoFinanceiroPagamento gerenciar_pag = new GerenciarBancoFinanceiroPagamento();
		BigDecimal valor_total_pagamentos =  BigDecimal.ZERO;
		
		for (FinanceiroPagamento pagamento : gerenciar_pag.getFinanceiroPagamentosPorLancamento(lancamento_pai.getId_lancamento())) {

				
			valor_total_pagamentos = valor_total_pagamentos.add(pagamento.getValor());
				
		 }
		
		BigDecimal valor_pagamentos_retroativos = valor_total_pagamentos;
		GerenciarBancoParcelas gerenciar = new GerenciarBancoParcelas();

		BigDecimal valor_total_parcelas =  BigDecimal.ZERO;
		
		for (Parcela parcela : gerenciar.getParcelasPorLancamento(lancamento_pai.getId_lancamento())) {
				BigDecimal valor_parcela = parcela.getValor();
				if(lancamento_pai.getTipo_lancamento() == 0) {
					//é uma despesa
				if(valor_pagamentos_retroativos.compareTo(valor_parcela) >=0) {
					gerenciar.atualizarStatusParcela(1, parcela.getId_parcela());
					
				}else if (valor_pagamentos_retroativos.compareTo(valor_parcela) < 0) {
					gerenciar.atualizarStatusParcela(0, parcela.getId_parcela());

				}
				}else if(lancamento_pai.getTipo_lancamento() == 1) {
					//é uma receita
					if(valor_pagamentos_retroativos.compareTo(valor_parcela) >=0) {
						gerenciar.atualizarStatusParcela(1, parcela.getId_parcela());
						
					}else if(valor_pagamentos_retroativos.compareTo(valor_parcela) < 0) {
						gerenciar.atualizarStatusParcela(0, parcela.getId_parcela());

					}
				}
				
				valor_pagamentos_retroativos = valor_pagamentos_retroativos.subtract(valor_parcela);
				valor_total_parcelas = valor_total_parcelas.add(valor_parcela);
				
		 }
		new GerenciarBancoLancamento().atualizarValorLancamento(valor_total_parcelas.toString(), lancamento_pai.getId_lancamento());
		
		
		if(valor_total_pagamentos.compareTo(valor_total_parcelas) >= 0) {
			//pagamento concluido
			//atualizar status do lancmento
			if(lancamento_pai.getTipo_lancamento() == 0) //é uma despesa
			new GerenciarBancoLancamento().atualizarStatusLancamento(1, lancamento_pai.getId_lancamento());
			else if(lancamento_pai.getTipo_lancamento() == 1)//é uma receita
				new GerenciarBancoLancamento().atualizarStatusLancamento(3, lancamento_pai.getId_lancamento());

		}else if(valor_total_pagamentos.compareTo(valor_total_parcelas) < 0) {
			//pagamento incompleto
			if(lancamento_pai.getTipo_lancamento() == 0) //é uma despesa
				new GerenciarBancoLancamento().atualizarStatusLancamento(0, lancamento_pai.getId_lancamento());
				else if(lancamento_pai.getTipo_lancamento() == 1)//é uma receita
					new GerenciarBancoLancamento().atualizarStatusLancamento(2, lancamento_pai.getId_lancamento());

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
}
