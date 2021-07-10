
package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import javax.swing.JOptionPane;
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

import main.java.cadastros.CadastroRecibo;
import main.java.cadastros.CadastroAviso;
import main.java.cadastros.CadastroBaseArquivos;
import main.java.cadastros.CadastroBaseDados;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroContrato.CadastroPagamento;
import main.java.cadastros.CadastroContrato.CadastroPagamentoContratual;
import main.java.cadastros.CadastroContrato.Recebimento;
import main.java.cadastros.CadastroDocumento;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroModelo;
import main.java.cadastros.CadastroNFe;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroRomaneio;
import main.java.cadastros.CadastroSafra;
import main.java.cadastros.ContaBancaria;
import main.java.cadastros.Contato;
import main.java.cadastros.DadosCarregamento;
import main.java.cadastros.DadosContratos;
import main.java.cadastros.FinanceiroPagamento;
import main.java.cadastros.FinanceiroPagamentoEmprestimo;
import main.java.cadastros.Lancamento;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.classesExtras.Endereco;
import main.java.conexaoBanco.GerenciarBancoRecibos;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoDocumento;
import main.java.conexaoBanco.GerenciarBancoNotasFiscais;
import main.java.conexaoBanco.GerenciarBancoPadrao;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoRecibos;
import main.java.conexaoBanco.GerenciarBancoRomaneios;
import main.java.conexaoBanco.GerenciarBancoSafras;
import main.java.conexaoBanco.GerenciarBancoTransferencias;
import main.java.conexaoBanco.GerenciarBancoTransferenciasCarga;
import main.java.conexoes.TesteConexao;
import main.java.graficos.GraficoLinha;
import main.java.graficos.JPanelGrafico;
import main.java.graficos.JPanelGraficoCarregamento;
import main.java.gui.TelaCadastroCliente;
import main.java.gui.TelaMain;
import main.java.gui.TelaRomaneios;
import main.java.gui.TelaCliente.ClienteTableModel;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.manipular.ConverterPdf;
import main.java.manipular.CopiarArquivo;
import main.java.manipular.EditarRecibo;
import main.java.manipular.GetDadosGlobais;
import main.java.manipular.ManipularArquivoTerceiros;
import main.java.manipular.ManipularNotasFiscais;
import main.java.manipular.ManipularRomaneios;
import main.java.manipular.ManipularTxt;
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
import outros.ValidaCNPj;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroNuvem;
import main.java.cadastros.CadastroZapMessenger;
import main.java.outros.DadosGlobais;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroGrupo;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoGrupos;
import main.java.cadastros.CadastroProduto;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroSafra;
import main.java.classesExtras.CBProdutoPersonalizado;
import main.java.classesExtras.CBProdutoRenderPersonalizado;
import main.java.classesExtras.ComboBoxPersonalizado;
import main.java.classesExtras.ComboBoxRenderPersonalizado;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoSafras;

import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;



public class TelaCriarRecibo extends JFrame {

	private final JPanel painelPrincipal = new JPanel();
	private JTextField entData;
	private JTextArea textAreaTextoFinal;
	private JDialog telaPai;
	private JFrame telaPaiJrame;
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private JCheckBox chkBoxDataAtual;
	private FinanceiroPagamento pagamento_local;
	private TelaCriarRecibo isto;
	EditarRecibo editarWord ;
	private Window janela_pai_global;
 	private ClienteTableModel modelo_cliente = new ClienteTableModel();
	
	public TelaCriarRecibo(FinanceiroPagamento pagamento, Lancamento lancamento, Window janela_pai) {
		getDadosGlobais();
		
		pagamento_local = pagamento;

		 isto = this;
		 janela_pai_global = janela_pai;
		
		setResizable(true);
		setTitle("E-Contract - Criar Recibo");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1036, 709);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(new MigLayout("", "[72px,grow][10px][185px][8px][97px][66px][grow]", "[26px][14px][][][200px][][][grow][][23px]"));
		
		JLabel lblNewLabel_2 = new JLabel("Data:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		painelPrincipal.add(lblNewLabel_2, "cell 0 0,alignx right,aligny center");
		
		entData = new JTextField();
		entData.setEditable(false);
		entData.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelPrincipal.add(entData, "cell 2 0,grow");
		entData.setColumns(10);
		
		entData.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				String caracteres = "0987654321\b";// lista de caracters que não devem ser aceitos
				String texto = entData.getText();
				if (!caracteres.contains(evt.getKeyChar() + "")) {
					evt.consume();// aciona esse propriedade para eliminar a ação do evento
				} else {
					if (texto.length() == 2 && evt.getKeyChar() != '\b') {
						entData.setText(entData.getText().concat("/"));
					}
					if (texto.length() == 5 && evt.getKeyChar() != '\b') {
						entData.setText(entData.getText().concat("/"));
					}

					if (entData.getText().length() >= 10) {
						// if para saber se precisa verificar também o tamanho da string do campo
						// maior ou igual ao tamanho máximo, cancela e nao deixa inserir mais
						evt.consume();
						entData.setText(entData.getText().substring(0, 10));
					}

				}
			}
		});
		entData.setColumns(10);

		
		entData.setText(pegarData());

		
		
		
		 chkBoxDataAtual = new JCheckBox("Data Atual");
		chkBoxDataAtual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(chkBoxDataAtual.isSelected()) {
					chkBoxDataAtual.setSelected(true);
					pegarData();
					entData.setEditable(false);

				}else {
					chkBoxDataAtual.setSelected(false);
					pegarData();
					entData.setEditable(true);
				}
			}
		});
		chkBoxDataAtual.setSelected(true);
		painelPrincipal.add(chkBoxDataAtual, "cell 4 0,growx,aligny top");
		
		JButton btnCriarrecibo = new JButton("Gerar");
		btnCriarrecibo.setBackground(new Color(0, 51, 0));
		btnCriarrecibo.setForeground(Color.WHITE);
		btnCriarrecibo.setFont(new Font("SansSerif", Font.ITALIC, 14));
		btnCriarrecibo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ByteArrayOutputStream contrato_alterado = null;

				System.out.println("Preparando para elaborar novo recibo");
				String text = textAreaTextoFinal.getText();
				 pagamento_local.setData_pagamento(entData.getText());
				 editarWord = new EditarRecibo(pagamento_local,text ,entData.getText(), modelo_cliente.getClientes());
				 
				contrato_alterado = editarWord.preparar();

				ConverterPdf converter_pdf = new ConverterPdf();
				String pdf_alterado = converter_pdf.word_pdf_stream(contrato_alterado);
				TelaVizualizarPdf vizualizar = new TelaVizualizarPdf(null, isto, null,
						pdf_alterado, null, isto);
			}
		});
		
		JTextArea textCabecalhoFixo = new JTextArea();
		textCabecalhoFixo.setEditable(false);
		textCabecalhoFixo.setWrapStyleWord(true);
		textCabecalhoFixo.setText("RECEBEDOR DESTINATÁRIO:\r\nEU, [NOME RECEBEDOR], [OCUPACAO RECEBEDOR], residente e domiciliado no endereço [ENDEREÇO RECEBEDOR], nº [N RECEBEDOR], Bairro: [BAIRRO RECEBEDOR], na Cidade de [CIDADE RECEBEDOR]- Estado de MG CEP: [CEP RECEBEDOR], inscrito no CPF sob nº [CPF RECEBEDOR]. A pessoa supra indicada será doravante denominada “RECEBEDOR DESTINATÁRIO”");
		textCabecalhoFixo.setLineWrap(true);
		textCabecalhoFixo.setFont(new Font("SansSerif", Font.PLAIN, 14));
		textCabecalhoFixo.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelPrincipal.add(textCabecalhoFixo, "cell 0 2 7 1,grow");
		
		JLabel lblNewLabel = new JLabel("RECEBEDORES INTERMEDIÁRIOS:");
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		painelPrincipal.add(lblNewLabel, "cell 0 3");
		
		JTable tabelaRecebedores = new JTable(modelo_cliente);
		tabelaRecebedores.setRowHeight(30);
		
		JScrollPane scrollPane_1 = new JScrollPane(tabelaRecebedores);
		scrollPane_1.getViewport().setBackground(Color.white);
		painelPrincipal.add(scrollPane_1, "cell 0 4 7 1,grow");
		
		JButton btnNewButton = new JButton("Inserir Recebedor");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				

				TelaCliente tela = new TelaCliente(0, 60, isto);
				tela.setVisible(true);
			}
		});
		
		JButton btnExcluirRecebedor = new JButton("Excluir Recebedor");
		btnExcluirRecebedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				modelo_cliente.onRemove(tabelaRecebedores.getSelectedRow());
				
			}
		});
		btnExcluirRecebedor.setBackground(new Color(153, 0, 51));
		btnExcluirRecebedor.setForeground(Color.WHITE);
		btnExcluirRecebedor.setFont(new Font("SansSerif", Font.BOLD, 14));
		painelPrincipal.add(btnExcluirRecebedor, "flowx,cell 6 5");
		btnNewButton.setBackground(new Color(0, 51, 0));
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setFont(new Font("SansSerif", Font.BOLD, 14));
		painelPrincipal.add(btnNewButton, "cell 6 5,alignx right");
		
		JTextArea txtrDevedorEunome = new JTextArea();
		txtrDevedorEunome.setWrapStyleWord(true);
		txtrDevedorEunome.setText("DEVEDOR:\r\nEU, [NOME DEVEDOR], [OCUPACAO DEVEDOR], residente e domiciliado no endereço [ENDEREÇO DEVEDOR], nº [N DEVEDOR], Bairro: [BAIRRO DEVEDOR], na Cidade de [CIDADE DEVEDOR]- Estado de MG CEP: [CEP DEVEDOR], inscrito no CPF sob nº [CPF DEVEDOR]. A pessoa supra indicada será doravante denominada “DEVEDOR”");
		txtrDevedorEunome.setLineWrap(true);
		txtrDevedorEunome.setFont(new Font("SansSerif", Font.PLAIN, 14));
		txtrDevedorEunome.setEditable(false);
		txtrDevedorEunome.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelPrincipal.add(txtrDevedorEunome, "cell 0 6 7 1,grow");
		
		JLabel lblNewLabel_3_1 = new JLabel("Não exclua as palavras chaves entre []");
		lblNewLabel_3_1.setForeground(Color.RED);
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		painelPrincipal.add(lblNewLabel_3_1, "cell 0 8 7 1");
		painelPrincipal.add(btnCriarrecibo, "cell 6 9,growx,aligny top");
		
		JLabel lblNewLabel_3 = new JLabel("Prévia do texto:");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		painelPrincipal.add(lblNewLabel_3, "cell 0 1 3 1,alignx left,growy");
		
		JPanel panel_1 = new JPanel();
		painelPrincipal.add(panel_1, "cell 0 7 7 1,grow");
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane);
		
		 textAreaTextoFinal = new JTextArea();
		 textAreaTextoFinal.setFont(new Font("SansSerif", Font.PLAIN, 22));
		
		 if(lancamento.getTipo_lancamento() != 3) {
			 //despesa, receita, transferencia
			 
			 //dados do objeto
			 //String uni = pagamento.get
			 
			 
			 
		 textAreaTextoFinal.setText("O [RECEBEDOR] vem por meio deste recibo [DECLARAR] [TER] [RECEBIDO] do [DEVEDOR], através de um(a) [forma_pagamento], a importância de valor_pagamento_decimal ( valor_pagamento_extenso ) referente a [descricao_pagamento] na data de [data_pagamento],sendo assim fica firmado entre as partes.\r\n");
		 }else if(lancamento.getTipo_lancamento() == 3) {
			 //emprestimo
			 textAreaTextoFinal.setText("O [RECEBEDOR] vem por meio deste recibo [DECLARAR] [TER] [RECEBIDO] do [DEVEDOR], através de um(a) [forma_pagamento], a importância de valor_pagamento_decimal ( valor_pagamento_extenso ) referente a [descricao_pagamento] na data de [data_pagamento],sendo assim fica firmado entre as partes.\r\n");

		 }
		 scrollPane.setViewportView(textAreaTextoFinal);
		 textAreaTextoFinal.setLineWrap(true);
		 textAreaTextoFinal.setWrapStyleWord(true);
		 textAreaTextoFinal.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		
	
		
		
		

		this.setLocationRelativeTo(janela_pai);

		
		
		
	}
	
	
	
	public TelaCriarRecibo(FinanceiroPagamentoEmprestimo pagamento, Lancamento lancamento, Window janela_pai) {
		getDadosGlobais();
		
		pagamento_local = pagamento;

		 isto = this;
		 janela_pai_global = janela_pai;
		
		setResizable(true);
		setTitle("E-Contract - Criar Recibo");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1036, 709);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(new MigLayout("", "[72px,grow][10px][185px][8px][97px][66px][grow]", "[26px][14px][][][200px][][][grow][][23px]"));
		
		JLabel lblNewLabel_2 = new JLabel("Data:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		painelPrincipal.add(lblNewLabel_2, "cell 0 0,alignx right,aligny center");
		
		entData = new JTextField();
		entData.setEditable(false);
		entData.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelPrincipal.add(entData, "cell 2 0,grow");
		entData.setColumns(10);
		
		entData.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				String caracteres = "0987654321\b";// lista de caracters que não devem ser aceitos
				String texto = entData.getText();
				if (!caracteres.contains(evt.getKeyChar() + "")) {
					evt.consume();// aciona esse propriedade para eliminar a ação do evento
				} else {
					if (texto.length() == 2 && evt.getKeyChar() != '\b') {
						entData.setText(entData.getText().concat("/"));
					}
					if (texto.length() == 5 && evt.getKeyChar() != '\b') {
						entData.setText(entData.getText().concat("/"));
					}

					if (entData.getText().length() >= 10) {
						// if para saber se precisa verificar também o tamanho da string do campo
						// maior ou igual ao tamanho máximo, cancela e nao deixa inserir mais
						evt.consume();
						entData.setText(entData.getText().substring(0, 10));
					}

				}
			}
		});
		entData.setColumns(10);

		
		entData.setText(pegarData());

		
		
		
		 chkBoxDataAtual = new JCheckBox("Data Atual");
		chkBoxDataAtual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(chkBoxDataAtual.isSelected()) {
					chkBoxDataAtual.setSelected(true);
					pegarData();
					entData.setEditable(false);

				}else {
					chkBoxDataAtual.setSelected(false);
					pegarData();
					entData.setEditable(true);
				}
			}
		});
		chkBoxDataAtual.setSelected(true);
		painelPrincipal.add(chkBoxDataAtual, "cell 4 0,growx,aligny top");
		
		JButton btnCriarrecibo = new JButton("Gerar");
		btnCriarrecibo.setBackground(new Color(0, 51, 0));
		btnCriarrecibo.setForeground(Color.WHITE);
		btnCriarrecibo.setFont(new Font("SansSerif", Font.ITALIC, 14));
		btnCriarrecibo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ByteArrayOutputStream contrato_alterado = null;

				System.out.println("Preparando para elaborar novo recibo");
				String text = textAreaTextoFinal.getText();
				 pagamento_local.setData_pagamento(entData.getText());
				 editarWord = new EditarRecibo(pagamento_local,text ,entData.getText(), modelo_cliente.getClientes());
				 
				contrato_alterado = editarWord.preparar();

				ConverterPdf converter_pdf = new ConverterPdf();
				String pdf_alterado = converter_pdf.word_pdf_stream(contrato_alterado);
				TelaVizualizarPdf vizualizar = new TelaVizualizarPdf(null, isto, null,
						pdf_alterado, null, isto);
			}
		});
		
		JTextArea textCabecalhoFixo = new JTextArea();
		textCabecalhoFixo.setEditable(false);
		textCabecalhoFixo.setWrapStyleWord(true);
		textCabecalhoFixo.setText("RECEBEDOR DESTINATÁRIO:\r\nEU, [NOME RECEBEDOR], [OCUPACAO RECEBEDOR], residente e domiciliado no endereço [ENDEREÇO RECEBEDOR], nº [N RECEBEDOR], Bairro: [BAIRRO RECEBEDOR], na Cidade de [CIDADE RECEBEDOR]- Estado de MG CEP: [CEP RECEBEDOR], inscrito no CPF sob nº [CPF RECEBEDOR]. A pessoa supra indicada será doravante denominada “RECEBEDOR DESTINATÁRIO”");
		textCabecalhoFixo.setLineWrap(true);
		textCabecalhoFixo.setFont(new Font("SansSerif", Font.PLAIN, 14));
		textCabecalhoFixo.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelPrincipal.add(textCabecalhoFixo, "cell 0 2 7 1,grow");
		
		JLabel lblNewLabel = new JLabel("RECEBEDORES INTERMEDIÁRIOS:");
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		painelPrincipal.add(lblNewLabel, "cell 0 3");
		
		JTable tabelaRecebedores = new JTable(modelo_cliente);
		tabelaRecebedores.setRowHeight(30);
		
		JScrollPane scrollPane_1 = new JScrollPane(tabelaRecebedores);
		scrollPane_1.getViewport().setBackground(Color.white);
		painelPrincipal.add(scrollPane_1, "cell 0 4 7 1,grow");
		
		JButton btnNewButton = new JButton("Inserir Recebedor");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				

				TelaCliente tela = new TelaCliente(0, 60, isto);
				tela.setVisible(true);
			}
		});
		
		JButton btnExcluirRecebedor = new JButton("Excluir Recebedor");
		btnExcluirRecebedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				modelo_cliente.onRemove(tabelaRecebedores.getSelectedRow());
				
			}
		});
		btnExcluirRecebedor.setBackground(new Color(153, 0, 51));
		btnExcluirRecebedor.setForeground(Color.WHITE);
		btnExcluirRecebedor.setFont(new Font("SansSerif", Font.BOLD, 14));
		painelPrincipal.add(btnExcluirRecebedor, "flowx,cell 6 5");
		btnNewButton.setBackground(new Color(0, 51, 0));
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setFont(new Font("SansSerif", Font.BOLD, 14));
		painelPrincipal.add(btnNewButton, "cell 6 5,alignx right");
		
		JTextArea txtrDevedorEunome = new JTextArea();
		txtrDevedorEunome.setWrapStyleWord(true);
		txtrDevedorEunome.setText("DEVEDOR:\r\nEU, [NOME DEVEDOR], [OCUPACAO DEVEDOR], residente e domiciliado no endereço [ENDEREÇO DEVEDOR], nº [N DEVEDOR], Bairro: [BAIRRO DEVEDOR], na Cidade de [CIDADE DEVEDOR]- Estado de MG CEP: [CEP DEVEDOR], inscrito no CPF sob nº [CPF DEVEDOR]. A pessoa supra indicada será doravante denominada “DEVEDOR”");
		txtrDevedorEunome.setLineWrap(true);
		txtrDevedorEunome.setFont(new Font("SansSerif", Font.PLAIN, 14));
		txtrDevedorEunome.setEditable(false);
		txtrDevedorEunome.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelPrincipal.add(txtrDevedorEunome, "cell 0 6 7 1,grow");
		
		JLabel lblNewLabel_3_1 = new JLabel("Não exclua as palavras chaves entre []");
		lblNewLabel_3_1.setForeground(Color.RED);
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		painelPrincipal.add(lblNewLabel_3_1, "cell 0 8 7 1");
		painelPrincipal.add(btnCriarrecibo, "cell 6 9,growx,aligny top");
		
		JLabel lblNewLabel_3 = new JLabel("Prévia do texto:");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		painelPrincipal.add(lblNewLabel_3, "cell 0 1 3 1,alignx left,growy");
		
		JPanel panel_1 = new JPanel();
		painelPrincipal.add(panel_1, "cell 0 7 7 1,grow");
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane);
		
		 textAreaTextoFinal = new JTextArea();
		 textAreaTextoFinal.setFont(new Font("SansSerif", Font.PLAIN, 22));
		
		 if(lancamento.getTipo_lancamento() != 3) {
			 //despesa, receita, transferencia
			 
			
			 
			 
		 textAreaTextoFinal.setText("O [RECEBEDOR] vem por meio deste recibo [DECLARAR] [TER] [RECEBIDO] do [DEVEDOR], através de um(a) [forma_pagamento], a importância de valor_pagamento_decimal ( valor_pagamento_extenso ) referente a [descricao_pagamento] na data de [data_pagamento],sendo assim fica firmado entre as partes.\r\n");
		 }else if(lancamento.getTipo_lancamento() == 3) {
			 //emprestimo
			 //dados do objeto			 
			 int tipo_objeto = pagamento.getObjeto();
			 String objeto = "";
			 
			 if(tipo_objeto == 0){
				 //moeda
				 textAreaTextoFinal.setText("O [RECEBEDOR] vem por meio deste recibo [DECLARAR] [TER] [RECEBIDO] do [DEVEDOR], através de um(a) [forma_pagamento], a importância de valor_pagamento_decimal ( valor_pagamento_extenso ) referente a [descricao_pagamento] na data de [data_pagamento],sendo assim fica firmado entre as partes.\r\n");
			 }else if(tipo_objeto == 1) {
				 //produto
				 String uni = pagamento.getUnidade_medida();
				 double quantidade = pagamento.getQuantidade();
				 String produto = pagamento.getEspecie();
					NumberFormat z = NumberFormat.getNumberInstance();
					Locale ptBr = new Locale("pt", "BR");
					String valor_unitario_string = NumberFormat.getCurrencyInstance(ptBr).format(pagamento.getValor_unitario());
					String valor_total_string = NumberFormat.getCurrencyInstance(ptBr).format(pagamento.getValor());

					
				 
				  objeto = z.format(quantidade) + " " + uni + " de " + produto + " no valor unitário de " +  valor_unitario_string + " perfazendo um total de " + valor_total_string;
					 textAreaTextoFinal.setText("[OBJETO]:  " + objeto + "\nO [RECEBEDOR] vem por meio deste recibo [DECLARAR] [TER] [RECEBIDO] do [DEVEDOR] o [OBJETO], através de um(a) [forma_pagamento], a importância de valor_pagamento_decimal ( valor_pagamento_extenso ) referente a [descricao_pagamento] na data de [data_pagamento],sendo assim fica firmado entre as partes.\r\n");

				
			 }
			 
			 

			 
		 }
		 scrollPane.setViewportView(textAreaTextoFinal);
		 textAreaTextoFinal.setLineWrap(true);
		 textAreaTextoFinal.setWrapStyleWord(true);
		 textAreaTextoFinal.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		
	
		
		
		

		this.setLocationRelativeTo(janela_pai);

		
		
		
	}
	public void setTelaPai(JDialog tela_pai) {
		this.telaPai = tela_pai;
	}
	
	
	
	public void getDadosGlobais() {
		//gerenciador de log
				DadosGlobais dados = DadosGlobais.getInstance();
				 GerenciadorLog = dados.getGerenciadorLog();
				 configs_globais = dados.getConfigs_globais();
				 
				 //usuario logado
				  login = dados.getLogin();
		
	}
	
	public String pegarData() {

		Date hoje = new Date();
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return df.format(hoje);
	}
	
	public void salvar(String caminho_arquivo_temp) {
		
		
		GetData hj = new GetData();
		String hora =  hj.getHora().replace(":", " ");
		
	     //criar o documento
		String unidade_base_dados = configs_globais.getServidorUnidade();
        String nome_arquivo = "doc_recibo_pagamento_" + hj.getAnoAtual() +"_"+ pagamento_local.getId_pagamento() +  "_" + hora;
		//criar pasta documentos
        ManipularTxt manipular = new ManipularTxt();
        
        
        String caminho_salvar = unidade_base_dados + "E-Contract\\arquivos\\financas\\lancamentos\\lancamento_"
        + pagamento_local.getId_lancamento() + "\\documentos\\";
		manipular.criarDiretorio(caminho_salvar);
        
        String caminho_completo = caminho_salvar  + nome_arquivo;
		boolean salvar = editarWord.criarArquivo(caminho_completo);

		if(salvar) {

			CadastroRecibo recibo = new CadastroRecibo();
			recibo.setData_recibo(new GetData().getData());
			recibo.setStatus_recibo(0);
			recibo.setId_pai(pagamento_local.getId_pagamento());
			recibo.setId_lancamento_pai(pagamento_local.getId_lancamento());

			recibo.setNome_arquivo(nome_arquivo + ".pdf");
			
			GerenciarBancoRecibos gerenciar = new GerenciarBancoRecibos();
			int guardar = gerenciar.inserirrecibo(recibo);
			
			if(guardar > 0) {
				
                boolean deletar_arquivo = manipular.apagarArquivo(caminho_completo + ".docx");
				JOptionPane.showMessageDialog(isto, "Recibo Criado com sucesso");
				//((TelaGerenciarContrato) telaPai).setInformacoesrecibos();
				//salvar documento
				GerenciarBancoDocumento gerenciar_doc = new GerenciarBancoDocumento();
		
		
				CadastroDocumento novo_documento = new CadastroDocumento();
				novo_documento.setDescricao("Arquivo de Recibo de Pagamento");
				novo_documento.setNome("doc_recibo_pagamento_" + pagamento_local.getId_pagamento());

				novo_documento.setTipo(5);
				novo_documento.setId_pai(pagamento_local.getId_pagamento());
				novo_documento.setNome_arquivo(nome_arquivo +   ".pdf");
				novo_documento.setId_lancamento(pagamento_local.getId_lancamento());

				int cadastrar = gerenciar_doc.inserirDocumentoLancamento(novo_documento);
				if (cadastrar > 0 ) {
					((TelaFinanceiroGerenciarLancamento) janela_pai_global).atualizarArvoreDocumentos();
					

				} else {
					JOptionPane.showMessageDialog(isto, "Documento não inserido no banco de dados");

				}
				((TelaFinanceiroGerenciarLancamento) janela_pai_global).pesquisar_recibos();

				isto.dispose();

			}else {
				JOptionPane.showMessageDialog(isto, "Erro ao guardar o recibo na base de dados!\nConsulte o administrador");
                boolean deletar_arquivo = manipular.apagarArquivo(caminho_completo + ".docx");
                boolean deletar_pdf = manipular.apagarArquivo(caminho_completo + ".pdf");

			}
			
			
			
			
		}else {
			JOptionPane.showMessageDialog(isto, "Erro ao salvar o arquivo fisico!\nConsulte o administrador");

		}
		
		
	}
	
	public void setTelaPai(JFrame tela_pai) {
		this.telaPaiJrame = tela_pai;
	}	
	
	
	public static class ClienteTableModel extends AbstractTableModel{
		 

	    private final int id=0;
	    private final int ie=1;
	    private final int apelido=2;
	    private final int cpf_cnpj=3;
	    private final int nome=4;
	  
	 
	    private final String colunas[]={"ID:","IE:","Apelido:","CPF/CNPJ:","Nome:"};
	    private final ArrayList<CadastroCliente> dados = new ArrayList<>();//usamos como dados uma lista genérica de nfs
	 
	    public ClienteTableModel() {
	        
	    }
	 
	    @Override
	    public int getColumnCount() {
	        //retorna o total de colunas
	        return colunas.length;
	    }
	 
	    @Override
	    public int getRowCount() {
	        //retorna o total de linhas na tabela
	        return dados.size();
	    }
	 
	    @Override
	    public Class<?> getColumnClass(int columnIndex) {
	        //retorna o tipo de dado, para cada coluna
	        switch (columnIndex) {
	        case id:
	            return Integer.class;
	        case ie:
	            return String.class;
	        case apelido:
	            return String.class;
	        case cpf_cnpj:
	            return String.class;
	        case nome:
	            return String.class;
	      
	        default:
	            throw new IndexOutOfBoundsException("Coluna Inválida!!!");
	        }
	    }
	 
	    @Override
	    public String getColumnName(int columnIndex) {
	        return colunas[columnIndex];
	    }
	 
	    @Override
	    public Object getValueAt(int rowIndex, int columnIndex) {
	        //retorna o valor conforme a coluna e linha
	 
	        //pega o dados corrente da linha
	    	CadastroCliente nota=dados.get(rowIndex);
	 
	        //retorna o valor da coluna
	        switch (columnIndex) {
	        case id:
	            return nota.getId();
	        case ie:
	            return nota.getIe();
	        case apelido:
	            return nota.getApelido().toUpperCase();
	        case cpf_cnpj:{
	        	if(nota.getTipo_pessoa() == 0)
	            return nota.getCpf();
	        	else
		            return nota.getCnpj();

	            
	        }
	        case nome:
	        	if(nota.getTipo_pessoa() == 0)
		            return nota.getNome_empresarial().toUpperCase();
		        	else
			            return nota.getNome_fantaia().toUpperCase();
	       
	        default:
	            throw new IndexOutOfBoundsException("Coluna Inválida!!!");
	        }
	    }
	 
	    @Override
	    public boolean isCellEditable(int rowIndex, int columnIndex) {
	        //metodo identifica qual coluna é editavel
	 
	        //só iremos editar a coluna BENEFICIO, 
	        //que será um checkbox por ser boolean
	      
	 
	        return false;
	    }
	 
	    @Override
	    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
	    	CadastroCliente nota=dados.get(rowIndex);
	 
	      
	    }
	 
	    public ArrayList<CadastroCliente> getClientes() {
	    	return dados;
	    }
	    
	    //Métodos abaixo são para manipulação de dados
	 
	    /**
	     * retorna o valor da linha indicada
	     * @param rowIndex
	     * @return
	     */
	    public CadastroCliente getValue(int rowIndex){
	        return dados.get(rowIndex);
	    }
	 
	    /**
	     * retorna o indice do objeto
	     * @param empregado
	     * @return
	     */
	    public int indexOf(CadastroCliente nota) {
	        return dados.indexOf(nota);
	    }
	 
	    /**
	     * add um empregado á lista
	     * @param empregado
	     */
	    public void onAdd(CadastroCliente nota) {
	        dados.add(nota);
	        fireTableRowsInserted(indexOf(nota), indexOf(nota));
	    }
	 
	    /**
	     * add uma lista de empregados
	     * @param dadosIn
	     */
	    public void onAddAll(ArrayList<CadastroCliente> dadosIn) {
	        dados.addAll(dadosIn);
	        fireTableDataChanged();
	    }
	 
	    /**
	     * remove um registro da lista, através do indice
	     * @param rowIndex
	     */
	    public void onRemove(int rowIndex) {
	        dados.remove(rowIndex);
	        fireTableRowsDeleted(rowIndex, rowIndex);
	    }
	 
	    /**
	     * remove um registro da lista, através do objeto
	     * @param empregado
	     */
	    public void onRemove(CadastroCliente nota) {
	        int indexBefore=indexOf(nota);//pega o indice antes de apagar
	        dados.remove(nota);  
	        fireTableRowsDeleted(indexBefore, indexBefore);
	    }
	 
	    /**
	     * remove todos registros da lista
	     */
	    public void onRemoveAll() {
	        dados.clear();
	        fireTableDataChanged();
	    }
	 
	}
	

	public void addRecebedorIntermediario(CadastroCliente cli) {
		modelo_cliente.onAdd(cli);
		
	}
	
}
