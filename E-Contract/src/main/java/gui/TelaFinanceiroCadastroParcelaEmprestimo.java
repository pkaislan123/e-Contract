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
import main.java.cadastros.FinanceiroPagamentoEmprestimo;
import main.java.cadastros.Lancamento;
import main.java.cadastros.Parcela;
import main.java.cadastros.ParcelaEmprestimo;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.classesExtras.Endereco;
import main.java.classesExtras.RenderizadorContato;
import main.java.conexaoBanco.GerenciarBancoAditivos;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoDocumento;
import main.java.conexaoBanco.GerenciarBancoFinanceiroPagamento;
import main.java.conexaoBanco.GerenciarBancoFinanceiroPagamentoEmprestimo;
import main.java.conexaoBanco.GerenciarBancoNotasFiscais;
import main.java.conexaoBanco.GerenciarBancoPadrao;
import main.java.conexaoBanco.GerenciarBancoParcelas;
import main.java.conexaoBanco.GerenciarBancoParcelasEmprestimo;
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
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroNuvem;
import main.java.cadastros.CadastroPontuacao;
import main.java.cadastros.CadastroZapMessenger;
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
import javax.swing.JRadioButton;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;



public class TelaFinanceiroCadastroParcelaEmprestimo extends JFrame {

	private final KGradientPanel painelPrincipal = new KGradientPanel();
    private JLabel lblTotalContratosConcluidos, lblTotalContratos, lblTotalContratosAbertos;
    private TelaFinanceiroCadastroParcelaEmprestimo isto;
    private JDialog telaPai;
    private JTextFieldPersonalizado entValorTotal;
    private JTextFieldPersonalizado entDescricao;
    private JTextFieldPersonalizado entDataVencimento;
	private JTextFieldPersonalizado entIdentificador = new JTextFieldPersonalizado();
	private JEditorPane entObservacao = new JEditorPane();
	private JComboBox cbStatus,cbObjeto;
	private String servidor_unidade;
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private JRadioButton rdbtnKgs,rdbtnSacos;
	private JTextFieldPersonalizado entEspecie,entQuantidade,entValorUnitario;
	private String unidade_medida_global;

	private JPanel painelPaiProduto ;
	private JRadioButton rdbtnFluxoDeCaixaSim, rdbtnFluxoDeCaixaNao;

	
	public TelaFinanceiroCadastroParcelaEmprestimo(int modo_operacao, ParcelaEmprestimo parcela, Lancamento lancamento_pai, Window janela_pai) {

		 isto = this;
		 getDadosGlobais();
		servidor_unidade = configs_globais.getServidorUnidade();
		setResizable(true);
		if(modo_operacao == 0)
		setTitle("E-Contract - Cadastro Parcela de Empréstimo");
		else if(modo_operacao == 1)
			setTitle("E-Contract - Edição Parcela de Empréstimo");
		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 540, 580);
		painelPrincipal.kStartColor = new Color(255, 255, 204);
		painelPrincipal.kEndColor = new Color(255, 204, 153);
		painelPrincipal.setBackground(new Color(0, 51, 51));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(new MigLayout("", "[grow][grow][grow][]", "[][][][grow][][][][][][][][grow][50px:n,grow][]"));
		
		JLabel lblFluxoDeCaixa = new JLabel("Fluxo de Caixa?:");
		lblFluxoDeCaixa.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblFluxoDeCaixa, "cell 0 0,alignx right");
		
		JPanel panel_1 = new JPanel();
		panel_1.setOpaque(false);
		panel_1.setBorder(null);
		panel_1.setBackground(Color.BLACK);
		painelPrincipal.add(panel_1, "cell 1 0,grow");
		panel_1.setLayout(new MigLayout("", "[][]", "[]"));
		 
		
		 rdbtnFluxoDeCaixaSim = new JRadioButton("Sim");
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
		painelPrincipal.add(lblIdentificador, "cell 1 1,alignx trailing");
		
		entIdentificador.setForeground(Color.black);
		painelPrincipal.add(entIdentificador, "cell 2 1 2 1,growx");
		
		JLabel lblObjeto = new JLabel("Objeto:");
		lblObjeto.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblObjeto, "cell 1 2,alignx trailing");
		 cbObjeto = new JComboBox();
		 cbObjeto.addItem("Moeda");
			cbObjeto.addItem("Produto");
		 cbObjeto.addItemListener(new ItemListener() {
		 	public void itemStateChanged(ItemEvent evt) {
		 		if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
					if (evt.getItem().equals("Produto")) {
						setPainelProduto();

					
					}else if(evt.getItem().equals("Moeda")) {
						removerPainel();
					}
				}

			}

		 });
		painelPrincipal.add(cbObjeto, "cell 2 2 2 1,growx");
		
		
		 painelPaiProduto = new JPanel();
		painelPaiProduto.setBackground(new Color(255, 255, 204));
		painelPrincipal.add(painelPaiProduto, "cell 1 3 3 1,grow");
		painelPaiProduto.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		
		
		
		JLabel lblValor = new JLabel("Valor Total:");
		painelPrincipal.add(lblValor, "cell 1 4,alignx trailing");
		lblValor.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		entValorTotal = new JTextFieldPersonalizado();
		entValorTotal.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(entValorTotal, "cell 2 4 2 1,growx");
		entValorTotal.setForeground(Color.black);
		entValorTotal.setColumns(10);
		
		JLabel lblDataVencimento = new JLabel("Data Vencimento:");
		lblDataVencimento.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblDataVencimento, "cell 1 5,alignx trailing");
		
		entDataVencimento = new JTextFieldPersonalizado();
		entDataVencimento.setFont(new Font("Tahoma", Font.PLAIN, 16));
		entDataVencimento.setColumns(10);
		entDataVencimento.setForeground(Color.black);
		
				painelPrincipal.add(entDataVencimento, "cell 2 5 2 1,growx");
		
		JLabel lblNewLabel = new JLabel("Descrição:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblNewLabel, "cell 1 6,alignx right");
		
		entDescricao = new JTextFieldPersonalizado();
		entDescricao.setFont(new Font("Tahoma", Font.PLAIN, 16));
		entDescricao.setColumns(10);
		entDescricao.setForeground(Color.black);
		painelPrincipal.add(entDescricao, "cell 2 6 2 1,growx");
		
		JLabel lblStatus = new JLabel("Status:");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblStatus, "cell 1 7,alignx trailing");
		
		 cbStatus = new JComboBox();
		 cbStatus.addItem("A Pagar");
		 cbStatus.addItem("Pago ");
		 painelPrincipal.add(cbStatus, "cell 2 7 2 1,growx");
		
		JLabel lblObservao = new JLabel("Observação:");
		lblObservao.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblObservao, "cell 1 9");
		
		 entObservacao = new JEditorPane();
		 painelPrincipal.add(entObservacao, "cell 2 9 2 3,grow");
		
		JButton btnFinalizar = new JButton("Finalizar");
		btnFinalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GerenciarBancoParcelasEmprestimo gerenciar = new GerenciarBancoParcelasEmprestimo();
				ParcelaEmprestimo parcela = getDadosSalvar(lancamento_pai.getId_lancamento());
				if(parcela != null) {
				int result = gerenciar.inserirParcela(parcela);
				if (result > 0) {
					//verificar se a pasta da parcela existe
					ManipularTxt manipular = new ManipularTxt();
					File pasta_de_parcelas = new File(servidor_unidade + lancamento_pai.getDiretorio_lancamento() + "\\parcela_" + result);
					if(!(pasta_de_parcelas.exists())) {
						manipular.criarDiretorio(pasta_de_parcelas.getAbsolutePath());
					}
					
					//copiar o arquivo para a pasta da parcela
					//antes verificar se o arquivo nao e nulo
					if(parcela.getCaminho_arquivo() != null && parcela.getCaminho_arquivo().length() > 20) {
					boolean copiar_arquivo = false;
					String extensaoDoArquivo = FilenameUtils.getExtension(parcela.getCaminho_arquivo());			

					String arquivo_doc_parcela_final = pasta_de_parcelas + "\\doc_parcela_" + result + "." + extensaoDoArquivo;
					try {
						copiar_arquivo = manipular.copiarNFe(parcela.getCaminho_arquivo(), arquivo_doc_parcela_final);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if(copiar_arquivo) {
						//atualiza parcela no banco de dados
						boolean  atualizar =  gerenciar.atualizarArquivoDaParcela("doc_parcela_" + result + "." + extensaoDoArquivo, result);
						if(!atualizar)
							JOptionPane.showMessageDialog(isto, "Arquivo Copiado, Mas não atualizado no Banco de dados\nConsulte o administrador");

					}else {
						JOptionPane.showMessageDialog(isto, "Arquivo não copiado, consulte o administrador");

					}
					}
					JOptionPane.showMessageDialog(isto, "Parcela Inserida");
					//atualizar valor do lançamento
					
						
					atualizarValorLancamentoEmprestimo(lancamento_pai);
					
					
					((TelaFinanceiroGerenciarLancamento) janela_pai).atualizar_informacoes();
					isto.dispose();
					
				} else {
					JOptionPane.showMessageDialog(isto, "Erro ao Salvar\nConsulte o Administrador");

				}
				}
			}
		});
		btnFinalizar.setBackground(new Color(0, 0, 102));
		btnFinalizar.setForeground(Color.WHITE);
		btnFinalizar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(btnFinalizar, "cell 2 12,alignx right");
		
		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GerenciarBancoParcelasEmprestimo gerenciar = new GerenciarBancoParcelasEmprestimo();
				ParcelaEmprestimo parcela_atualizar = getDadosAtualizar(parcela);
				if(parcela_atualizar != null) {
				boolean result = gerenciar.atualizarParcela(parcela_atualizar);
				if (result) {

					JOptionPane.showMessageDialog(isto, "Parcela Atualizada");
					atualizarValorLancamentoEmprestimo(lancamento_pai);
					((TelaFinanceiroGerenciarLancamento) janela_pai).atualizar_informacoes();
					isto.dispose();
				} else {
					JOptionPane.showMessageDialog(isto, "Erro ao Atualizar\nConsulte o Administrador");

				}
				}
			}
		});
		btnAtualizar.setForeground(Color.WHITE);
		btnAtualizar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnAtualizar.setBackground(new Color(0, 0, 102));
		painelPrincipal.add(btnAtualizar, "cell 3 12");
		
		
		if(modo_operacao == 0) {
			//novo cadastro
			btnAtualizar.setEnabled(false);
			btnAtualizar.setVisible(false);
			
			
		}else if(modo_operacao == 1) {
			//edicao
		
			btnFinalizar.setEnabled(false);
			btnFinalizar.setVisible(false);
			rotinasEdicao(parcela);
			
		
		}
		
		this.setLocationRelativeTo(janela_pai);

		
		
		
	}
	
	public void rotinasEdicao(ParcelaEmprestimo parcela) {
   String s_valor, descricao, observacao, identificador, data_vencimento, caminho_arquivo;
		int status, objeto;
		identificador = parcela.getIdentificador();
		observacao = parcela.getObservacao();
		descricao = parcela.getDescricao();
		data_vencimento = parcela.getData_vencimento();
		caminho_arquivo = parcela.getCaminho_arquivo();
		status = parcela.getStatus();
		
		objeto = parcela.getObjeto();
		
		if(objeto == 0) {
			//moeada
		}else if(objeto == 1) {
			//produto
			cbObjeto.setSelectedIndex(objeto);
			
			
			String especie, unidade_medida ;
			double quantidade;
			
			BigDecimal valor_unitario;
			
			especie = parcela.getEspecie();
			quantidade = parcela.getQuantidade();
			valor_unitario = parcela.getValor_unitario();
			unidade_medida = parcela.getUnidade_medida();
			
			entEspecie.setText(especie);
			entQuantidade.setText(Double.toString(quantidade));
			entValorUnitario.setText(valor_unitario.toPlainString());
			
			if(unidade_medida.equalsIgnoreCase("KG")) {
				rdbtnKgs.setSelected(true);
			}else if(unidade_medida.equalsIgnoreCase("Sacos")) {
				rdbtnSacos.setSelected(true);
			}
			
		}
		
		BigDecimal valor = parcela.getValor();
		
		entIdentificador.setText(identificador);
		entObservacao.setText(observacao);
		entDescricao.setText(descricao);
		entDataVencimento.setText(data_vencimento);

		entValorTotal.setText(valor.toString());
		cbStatus.setSelectedIndex(status);
		
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

	public ParcelaEmprestimo getDadosSalvar(int id_lancamento_pai) {
		ParcelaEmprestimo parcela = new ParcelaEmprestimo();
		
		String s_valor, descricao, observacao, identificador, data_vencimento;
		int objeto;
		
		
		identificador = entIdentificador.getText();
		observacao = entObservacao.getText();
		descricao = entDescricao.getText();
		data_vencimento = entDataVencimento.getText();
		BigDecimal valor_total = BigDecimal.ZERO;

		objeto = cbObjeto.getSelectedIndex();
		
		if(rdbtnFluxoDeCaixaSim.isSelected()) {
			parcela.setFluxo_caixa(1);
		}else if(rdbtnFluxoDeCaixaNao.isSelected()) {
			parcela.setFluxo_caixa(0);

		}
		
		parcela.setObjeto(objeto);
		if(objeto == 0) {
			//moeda
		}else if(objeto == 1) {
			//produto
			
			String especie, unidade_medida = "",s_valor_unitario;
			double quantidade;
			BigDecimal valor_unitario = BigDecimal.ZERO;
			
			especie = entEspecie.getText();
			
			if(rdbtnKgs.isSelected()) {
				unidade_medida = "KG";
			}else if(rdbtnSacos.isSelected()) {
				unidade_medida = "Sacos";
			}
			
			quantidade = Double.parseDouble(entQuantidade.getText());
			
		
			
			parcela.setEspecie(especie);
			parcela.setUnidade_medida(unidade_medida);
			parcela.setQuantidade(quantidade);
			
			try {
				s_valor_unitario = entValorUnitario.getText();
				s_valor_unitario = s_valor_unitario.replace("[^0-9,]", "");
				s_valor_unitario = s_valor_unitario.replace(",", ".");
				valor_unitario = new BigDecimal(s_valor_unitario);
				parcela.setValor_unitario(valor_unitario);

			}catch(Exception e) {
				parcela.setValor_unitario(BigDecimal.ZERO);
			}
			
			

		}
		
		try {
			s_valor = entValorTotal.getText();
			s_valor = s_valor.replace("[^0-9,]", "");
			s_valor = s_valor.replace(",", ".");
			valor_total = new BigDecimal(s_valor);
			parcela.setValor(valor_total);

		}catch(Exception e) {
			parcela.setValor(BigDecimal.ZERO);
		}
		
		parcela.setDescricao(descricao);
		parcela.setStatus(0);
		parcela.setId_lancamento_pai(id_lancamento_pai);
		parcela.setIdentificador(identificador);
		parcela.setObservacao(observacao);
		
		try {

			if (!isDateValid(data_vencimento)) {
				JOptionPane.showMessageDialog(isto, "Data do Vencimento Inválida");

				return null;
			} else {
				parcela.setData_vencimento(data_vencimento);
				return parcela;

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(isto, "Datas Inválidas");
			return null;
		}
		
	
	}
	
	
	public ParcelaEmprestimo getDadosAtualizar(ParcelaEmprestimo parcela_antiga) {
		ParcelaEmprestimo parcela = new ParcelaEmprestimo();
		parcela.setId_parcela(parcela_antiga.getId_parcela());
		
		String s_valor, descricao, observacao, identificador, data_vencimento;
		int objeto, status;
		
		
		identificador = entIdentificador.getText();
		observacao = entObservacao.getText();
		descricao = entDescricao.getText();
		data_vencimento = entDataVencimento.getText();
		BigDecimal valor_total = BigDecimal.ZERO;
		status = cbStatus.getSelectedIndex();
		objeto = cbObjeto.getSelectedIndex();
		
		
		if(rdbtnFluxoDeCaixaSim.isSelected()) {
			parcela.setFluxo_caixa(1);
		}else if(rdbtnFluxoDeCaixaNao.isSelected()) {
			parcela.setFluxo_caixa(0);

		}
		
		parcela.setObjeto(objeto);
		if(objeto == 0) {
			//moeda
		}else if(objeto == 1) {
			//produto
			
			String especie, unidade_medida = "",s_valor_unitario;
			double quantidade;
			BigDecimal valor_unitario = BigDecimal.ZERO;
			
			especie = entEspecie.getText();
			
			if(rdbtnKgs.isSelected()) {
				unidade_medida = "KG";
			}else if(rdbtnSacos.isSelected()) {
				unidade_medida = "Sacos";
			}
			
			quantidade = Double.parseDouble(entQuantidade.getText());
			
		
			
			parcela.setEspecie(especie);
			parcela.setUnidade_medida(unidade_medida);
			parcela.setQuantidade(quantidade);
			
			try {
				s_valor_unitario = entValorUnitario.getText();
				s_valor_unitario = s_valor_unitario.replace("[^0-9,]", "");
				s_valor_unitario = s_valor_unitario.replace(",", ".");
				valor_unitario = new BigDecimal(s_valor_unitario);
				parcela.setValor_unitario(valor_unitario);

			}catch(Exception e) {
				parcela.setValor_unitario(BigDecimal.ZERO);
			}
			
			

		}
		
		try {
			s_valor = entValorTotal.getText();
			s_valor = s_valor.replace("[^0-9,]", "");
			s_valor = s_valor.replace(",", ".");
			valor_total = new BigDecimal(s_valor);
			parcela.setValor(valor_total);

		}catch(Exception e) {
			parcela.setValor(BigDecimal.ZERO);
		}
		
		parcela.setDescricao(descricao);
		parcela.setStatus(status);

		parcela.setIdentificador(identificador);
		parcela.setObservacao(observacao);
		
		try {

			if (!isDateValid(data_vencimento)) {
				JOptionPane.showMessageDialog(isto, "Data do Vencimento Inválida");

				return null;
			} else {
				parcela.setData_vencimento(data_vencimento);
				return parcela;

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(isto, "Datas Inválidas");
			return null;
		}
		
	}
	
	
	public void setTelaPai(JDialog _tela_pai) {
		this.telaPai = _tela_pai;
	}
	
	public void getDadosGlobais() {
		//gerenciador de log
				DadosGlobais dados = DadosGlobais.getInstance();
				 GerenciadorLog = dados.getGerenciadorLog();
				 configs_globais = dados.getConfigs_globais();
				 
				 //usuario logado
				  login = dados.getLogin();
		
	}
	
	
	public void setPainelProduto() {
		
		JPanel painelProduto = new JPanel();
		painelProduto.setBackground(new Color(255, 255, 204));
		painelProduto.setLayout(new MigLayout("", "[][grow][][grow]", "[][grow][][][]"));
		painelPrincipal.add(painelProduto, "cell 0 0,grow");

		JLabel lblEspecie = new JLabel("Espécie:");
		painelProduto.add(lblEspecie, "cell 0 0,alignx right");
		lblEspecie.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		
		
		 entEspecie = new JTextFieldPersonalizado();
		painelProduto.add(entEspecie, "cell 1 0 3 1,growx");
		entEspecie.setForeground(Color.black);
		
		JLabel lblUnidadeMedida = new JLabel("Unidade Medida:");
		painelProduto.add(lblUnidadeMedida, "cell 0 1");
		lblUnidadeMedida.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 204));
		painelProduto.add(panel_1, "cell 1 1 3 1,alignx center,growy");
		panel_1.setLayout(new MigLayout("", "[][]", "[]"));
		
		
		
		
		 rdbtnKgs = new JRadioButton("KG");
		rdbtnKgs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				rdbtnKgs.setSelected(true);
				rdbtnSacos.setSelected(false);
				
			}
		});
		panel_1.add(rdbtnKgs, "cell 0 0");
		rdbtnKgs.setFont(new Font("SansSerif", Font.BOLD, 16));
		
		 rdbtnSacos = new JRadioButton("Saco");
		 rdbtnSacos.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		
		 		rdbtnSacos.setSelected(true);
		 		rdbtnKgs.setSelected(false);
		 		
		 	}
		 });
		panel_1.add(rdbtnSacos, "cell 1 0");
		rdbtnSacos.setFont(new Font("SansSerif", Font.BOLD, 16));
		
		JLabel lblQuantidade = new JLabel("Quantidade:");
		painelProduto.add(lblQuantidade, "cell 0 2,alignx right");
		lblQuantidade.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		 entQuantidade = new JTextFieldPersonalizado();
		 entQuantidade.addKeyListener(new KeyAdapter() {
		 	@Override
		 	public void keyTyped(KeyEvent o) {
		 		String caracteres = ".0987654321\b";// lista de caracters que não devem ser aceitos
				String Squantidade;
				Squantidade = entQuantidade.getText();

				if (!caracteres.contains(o.getKeyChar() + "")) {
					o.consume();// aciona esse propriedade para eliminar a ação do evento

					// quantidade = entQuantidade.getText();
				} else {
					Squantidade = entQuantidade.getText() + o.getKeyChar();
					Squantidade = Squantidade.replaceAll("[^0-9]+", "");
				}

				

			}
		 	
		 });
		painelProduto.add(entQuantidade, "cell 1 2 3 1,growx");
		entQuantidade.setForeground(Color.black);
		
		JLabel lblValorUnitrio = new JLabel("Valor Unitário:");
		painelProduto.add(lblValorUnitrio, "cell 0 3,alignx right");
		lblValorUnitrio.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		 entValorUnitario = new JTextFieldPersonalizado();
		 entValorUnitario.addKeyListener(new KeyAdapter() {
		 	@Override
		 	public void keyTyped(KeyEvent o) {
		 		String caracteres = ".0987654321\b";// lista de caracters que não devem ser aceitos
				String Svalor_unitario = null, quantidade;
				quantidade = entQuantidade.getText();

				if (!caracteres.contains(o.getKeyChar() + "")) {
					o.consume();// aciona esse propriedade para eliminar a ação do evento

					// quantidade = entQuantidade.getText();
				} else {
					Svalor_unitario = entValorUnitario.getText() + o.getKeyChar();
					Svalor_unitario = Svalor_unitario.replaceAll("[^0-9.]+", "");
				}


				double quant = Double.parseDouble(quantidade);
				BigDecimal valor_unitario = new BigDecimal(Svalor_unitario);



				// if(quantidade != null && !(quantidade.length() <= 0) &&
				// !quantidade.equals("") && Spreco != null && !(Spreco.length() <= 0) &&
				// !Spreco.equals(""))
				{
					try {

						BigDecimal valor_total = valor_unitario.multiply(new BigDecimal(quantidade));

						
						String valorTotal = valor_total.toPlainString();
						entValorTotal.setText(valorTotal);

					} catch (NumberFormatException n) {
						

					}
				}

			}
		 		
		 		
		 	
		 });
		painelProduto.add(entValorUnitario, "cell 1 3 3 1,growx");
		entValorUnitario.setForeground(Color.black);
		
		painelPaiProduto.add(painelProduto);
	}
	
	public void removerPainel() {
		painelPaiProduto.removeAll();
		painelPaiProduto.repaint();
		painelPaiProduto.updateUI();

	}
	
		
	
	public void atualizarValorLancamentoEmprestimo(Lancamento lancamento_pai) {
		

		GerenciarBancoFinanceiroPagamentoEmprestimo gerenciar_pag = new GerenciarBancoFinanceiroPagamentoEmprestimo();
		BigDecimal valor_total_pagamentos =  BigDecimal.ZERO;
		
		for (FinanceiroPagamentoEmprestimo pagamento : gerenciar_pag.getFinanceiroPagamentosPorLancamento(lancamento_pai.getId_lancamento())) {

				
			valor_total_pagamentos = valor_total_pagamentos.add(pagamento.getValor());
				
		 }
		
		BigDecimal valor_pagamentos_retroativos = valor_total_pagamentos;
		GerenciarBancoParcelasEmprestimo gerenciar = new GerenciarBancoParcelasEmprestimo();

		BigDecimal valor_total_parcelas =  BigDecimal.ZERO;
		
		for (ParcelaEmprestimo parcela : gerenciar.getParcelasPorLancamento(lancamento_pai.getId_lancamento())) {
				BigDecimal valor_parcela = parcela.getValor();
					//é uma receita
					if(valor_pagamentos_retroativos.compareTo(valor_parcela) >=0) {
						gerenciar.atualizarStatusParcela(1, parcela.getId_parcela());
						
					}else if(valor_pagamentos_retroativos.compareTo(valor_parcela) < 0) {
						gerenciar.atualizarStatusParcela(0, parcela.getId_parcela());

					}
				
				
				valor_pagamentos_retroativos = valor_pagamentos_retroativos.subtract(valor_parcela);
				valor_total_parcelas = valor_total_parcelas.add(valor_parcela);
				
		 }
		new GerenciarBancoLancamento().atualizarValorLancamento(valor_total_parcelas.toString(), lancamento_pai.getId_lancamento());
		
		
		if(valor_total_pagamentos.compareTo(valor_total_parcelas) >= 0) {
			//pagamento concluido
			//atualizar status do lancmento
			
				new GerenciarBancoLancamento().atualizarStatusLancamento(3, lancamento_pai.getId_lancamento());

		}else if(valor_total_pagamentos.compareTo(valor_total_parcelas) < 0) {
			//pagamento incompleto

					new GerenciarBancoLancamento().atualizarStatusLancamento(2, lancamento_pai.getId_lancamento());

		}
		
			
	}
	
	


	
}
