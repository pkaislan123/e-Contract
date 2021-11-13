package main.java.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.PatternSyntaxException;

import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.RowFilter.ComparisonType;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
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
import main.java.cadastros.Lancamento;
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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TelaNotasFiscais extends JFrame {

	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private FileChooser fileChooser;

	private JDialog tela_pai;
	private ArrayList<CadastroNFe> notas_fiscais_disponivel = new ArrayList<>();
	private String servidor_unidade;

	private JTable table_nfs;
	private TelaNotasFiscais isto;
	
	private JLabel lblStatusAdicionandoNotas;
	private int contador = 0;
	private JFileChooser fileChooser_global ;
	  private ArrayList<String> listadeArquivos = new ArrayList<>();

	private final JPanel painelPrincipal = new JPanel();
	/*DefaultTableModel modelo_nfs = new DefaultTableModel() {
		public boolean isCellEditable(int linha, int coluna) {
			return false;
		}
	};*/
	private NFeTableModel modelo_nfs = new NFeTableModel();
	private TableRowSorter<NFeTableModel> sorter;
	NumberFormat z = NumberFormat.getNumberInstance();

	private JTextField entChavePesquisa;
	private JButton btnVizualizarNF;
	private JButton btnExportar;
	private JButton btnImportarNFe;
	private CadastroCliente cliente_global;
	private JTextField entProduto;
	private JButton btnFiltrar;
	private JLabel lblNewLabel;
	private JLabel lblRemetente;
	private JTextField entRemetente;
	private JLabel lblNatureza;
	private JTextField entNatureza;
	private JLabel lblProduto;
	private JLabel lblNewLabel_1;
	private JLabel lblD;
	private JLabel lblAt;
	private JTextField entMenorData;
	private JTextField entMaiorData;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_4;
	private JLabel lblNumTotalNfs;
	private JButton btnNewButton;
	private JButton btnSelecionarNota;
	private JButton btnCadastrar;
	Locale ptBr = new Locale("pt", "BR");
	private JLabel lblNewLabel_5;
	private JLabel lblNewLabel_6;
	private JLabel lblPesoTotalNf;
	private JLabel lblValorTotalNf;


	public TelaNotasFiscais(int flag, int retorno,CadastroCliente vendedor, Window janela_pai) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(TelaNotasFiscais.class.getResource("/imagens/icone_notas_fiscais.png")));
		//setAlwaysOnTop(true);

		cliente_global= vendedor;
		isto = this;
		getDadosGlobais();
		setResizable(true);
		setTitle("E-Contract - Notas Fiscais");

		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1295, 706);
		painelPrincipal.setBackground(Color.WHITE);
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(new MigLayout("", "[109px][74px][77px][5px][268px][12px][59px][5px][242px][24px][25px][14px][169px][12px][67px][12px][93px]", "[64px][16px][31px][34px][grow][47px][23px][23px]"));

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		painelPrincipal.add(panel, "cell 0 4 17 1,grow");

		table_nfs = new JTable(modelo_nfs);
		 sorter = new TableRowSorter<NFeTableModel>(modelo_nfs);
        
		
		table_nfs.setRowSorter(sorter);

		table_nfs.setBackground(new Color(255, 255, 255));
		
		table_nfs.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		table_nfs.getColumnModel().getColumn(0).setPreferredWidth(80);
		table_nfs.getColumnModel().getColumn(1).setPreferredWidth(50);
		table_nfs.getColumnModel().getColumn(2).setPreferredWidth(250);
		table_nfs.getColumnModel().getColumn(3).setPreferredWidth(120);
		table_nfs.getColumnModel().getColumn(4).setPreferredWidth(120);
		table_nfs.getColumnModel().getColumn(5).setPreferredWidth(70);
		table_nfs.getColumnModel().getColumn(6).setPreferredWidth(120);
		table_nfs.getColumnModel().getColumn(7).setPreferredWidth(250);
		table_nfs.getColumnModel().getColumn(8).setPreferredWidth(120);
		table_nfs.getColumnModel().getColumn(9).setPreferredWidth(100);
		table_nfs.getColumnModel().getColumn(10).setPreferredWidth(120);
		table_nfs.getColumnModel().getColumn(11).setPreferredWidth(120);
		panel.setLayout(null);
		panel.setLayout(new BorderLayout(0, 0));
		JScrollPane scrollPaneNFs = new JScrollPane(table_nfs);
		panel.add(scrollPaneNFs);

		
		
		


		entProduto = new JTextField();
		entProduto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();

			}
		});
		entProduto.setFont(new Font("Arial", Font.BOLD, 16));
		painelPrincipal.add(entProduto, "cell 8 2,growx,aligny top");
		entProduto.setColumns(10);
		
		entChavePesquisa = new JTextField();
		entChavePesquisa.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();
			}
		});
		entChavePesquisa.setFont(new Font("Arial", Font.BOLD, 16));
		painelPrincipal.add(entChavePesquisa, "cell 4 2,growx,aligny center");
		entChavePesquisa.setColumns(10);

		
		
		lblStatusAdicionandoNotas = new JLabel("Adicionando Notas...");
		painelPrincipal.add(lblStatusAdicionandoNotas, "cell 8 7 9 1,grow");
		
		btnFiltrar = new JButton("Filtrar");
		btnFiltrar.setBackground(new Color(0, 0, 102));
		btnFiltrar.setForeground(Color.WHITE);
		btnFiltrar.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnFiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			   filtrar();
			}
		});
		painelPrincipal.add(btnFiltrar, "cell 16 3,alignx center,aligny center");
		
		lblNewLabel = new JLabel("Destinatario:");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		painelPrincipal.add(lblNewLabel, "cell 2 2,alignx left,aligny center");
		
		lblRemetente = new JLabel("Remetente:");
		lblRemetente.setFont(new Font("Arial", Font.PLAIN, 16));
		painelPrincipal.add(lblRemetente, "cell 2 3,alignx right,aligny center");
		
		entRemetente = new JTextField();
		entRemetente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();
			}
		});
		entRemetente.setFont(new Font("Arial", Font.BOLD, 16));
		entRemetente.setColumns(10);
		painelPrincipal.add(entRemetente, "cell 4 3,growx,aligny top");
		
		lblNatureza = new JLabel("Natureza:");
		lblNatureza.setFont(new Font("Arial", Font.PLAIN, 16));
		painelPrincipal.add(lblNatureza, "cell 6 3,alignx left,aligny center");
		
		entNatureza = new JTextField();
		entNatureza.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();

			}
		});
		entNatureza.setFont(new Font("Arial", Font.BOLD, 16));
		entNatureza.setColumns(10);
		painelPrincipal.add(entNatureza, "cell 8 3,growx,aligny top");
		
		lblProduto = new JLabel("Produto:");
		lblProduto.setFont(new Font("Arial", Font.PLAIN, 16));
		painelPrincipal.add(lblProduto, "cell 6 2,alignx right,aligny center");
		
		lblNewLabel_1 = new JLabel("Periodo");
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 16));
		painelPrincipal.add(lblNewLabel_1, "cell 12 1,alignx center,aligny top");
		
		lblD = new JLabel("Dé:");
		lblD.setFont(new Font("Arial", Font.PLAIN, 16));
		painelPrincipal.add(lblD, "cell 10 2,alignx right,aligny bottom");
		
		lblAt = new JLabel("Até:");
		lblAt.setFont(new Font("Arial", Font.PLAIN, 16));
		painelPrincipal.add(lblAt, "cell 10 3,alignx left,aligny center");
		
		entMenorData = new JTextField();
		entMenorData.setFont(new Font("Arial", Font.BOLD, 16));
		entMenorData.setColumns(10);
		painelPrincipal.add(entMenorData, "cell 12 2,growx,aligny center");
		
		entMaiorData = new JTextField();
		entMaiorData.setFont(new Font("Arial", Font.BOLD, 16));
		entMaiorData.setColumns(10);
		painelPrincipal.add(entMaiorData, "cell 12 3,growx,aligny bottom");
		
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.setBackground(new Color(204, 0, 0));
		btnLimpar.setForeground(Color.WHITE);
		btnLimpar.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				    
				    sorter.setRowFilter( RowFilter.regexFilter(""));
				    calcular();
			}
		});
		painelPrincipal.add(btnLimpar, "cell 14 3,growx,aligny center");
		
		lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(TelaNotasFiscais.class.getResource("/imagens/icone_notas_fiscais.png")));
		painelPrincipal.add(lblNewLabel_2, "cell 0 0,alignx left,aligny top");
		
		lblNewLabel_4 = new JLabel("     NF's");
		lblNewLabel_4.setOpaque(true);
		lblNewLabel_4.setForeground(Color.WHITE);
		lblNewLabel_4.setFont(new Font("Arial", Font.PLAIN, 18));
		lblNewLabel_4.setBackground(new Color(0, 51, 0));
		painelPrincipal.add(lblNewLabel_4, "cell 0 0,alignx right,aligny bottom");
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		painelPrincipal.add(panel_1, "cell 8 5 9 1,growx,aligny top");
		panel_1.setLayout(new MigLayout("", "[][][][][][][][][][][][][][][][][][][]", "[]"));
		
		btnNewButton = new JButton("Excluir");
		btnNewButton.setBackground(new Color(204, 0, 0));
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_1.add(btnNewButton, "cell 13 0");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (JOptionPane.showConfirmDialog(isto, "Deseja excluir a NF?", "Excluir NF",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					
					int rowSel = table_nfs.getSelectedRow();// pega o indice da linha na tabela
					int indexRowModel = table_nfs.getRowSorter().convertRowIndexToModel(rowSel);// converte pro indice do
																								// model
					CadastroNFe nota_vizualizar = notas_fiscais_disponivel.get(indexRowModel);

					
					ManipularTxt manipular = new ManipularTxt();
					String myFile = servidor_unidade + nota_vizualizar.getCaminho_arquivo();

					File arquivo = new File(myFile);

					if (arquivo.exists()) {

						boolean apagado = manipular.apagarArquivo(myFile);
						if (apagado) {

							// remover do banco de dados
							GerenciarBancoNotasFiscais gerenciar = new GerenciarBancoNotasFiscais();
							boolean excluir = gerenciar
									.removerNota(nota_vizualizar.getId());
							if (excluir) {
								JOptionPane.showMessageDialog(isto, "Nota Fiscal Excluída");
								pesquisarNotas(vendedor);
							} else {
								JOptionPane.showMessageDialog(isto,
										"Erro ao excluir a Nota Fiscal\nConsulte o administrador");

							}

						} else {
							JOptionPane.showMessageDialog(isto,
									"Erro ao excluir a Nota Fiscal\nO arquivo fisico não pode ser apagado");
						}
					} else {
						// remover do banco de dados
						GerenciarBancoNotasFiscais gerenciar = new GerenciarBancoNotasFiscais();
						boolean excluir = gerenciar
								.removerNota(nota_vizualizar.getId());
						if (excluir) {
							JOptionPane.showMessageDialog(isto, "NF Excluída");
							pesquisarNotas(vendedor);

						} else {
							JOptionPane.showMessageDialog(isto,
									"Erro ao excluir a NF\nConsulte o administrador");

						}
					}
				}
				
				
			}
		});
		
		btnImportarNFe = new JButton("Importar");
		btnImportarNFe.setBackground(new Color(0, 0, 153));
		btnImportarNFe.setForeground(Color.WHITE);
		btnImportarNFe.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_1.add(btnImportarNFe, "cell 15 0");
		btnImportarNFe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				importar();
				
				
			}
		});
		
		btnExportar = new JButton("Exportar");
		btnExportar.setBackground(new Color(0, 51, 0));
		btnExportar.setForeground(Color.WHITE);
		btnExportar.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_1.add(btnExportar, "cell 16 0");
		btnExportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ArrayList<CadastroNFe> notas_selecionadas = new ArrayList<>();
				int linhas_selecionadas[] = table_nfs.getSelectedRows();// pega o indice da linha na tabela

				for (int i = 0; i < linhas_selecionadas.length; i++) {

					int indice = linhas_selecionadas[i];//
					int indexRowModel = table_nfs.getRowSorter().convertRowIndexToModel(indice);

					CadastroNFe nf = modelo_nfs.getValue(indexRowModel);
					notas_selecionadas.add(nf);
				}
				
				gerarExcel(exportar(notas_selecionadas));
			}
		});
		
		btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				TelaCadastroNotaFiscal tela = new TelaCadastroNotaFiscal(0, null, isto);
				tela.setVisible(true);

			}
		});
		btnCadastrar.setForeground(Color.WHITE);
		btnCadastrar.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnCadastrar.setBackground(new Color(102, 51, 0));
		panel_1.add(btnCadastrar, "cell 17 0");
		
		btnVizualizarNF = new JButton("Vizualizar");
		btnVizualizarNF.setBackground(new Color(255, 102, 0));
		btnVizualizarNF.setForeground(Color.WHITE);
		btnVizualizarNF.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_1.add(btnVizualizarNF, "cell 18 0");
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		painelPrincipal.add(panel_2, "cell 0 5 5 3,grow");
		panel_2.setLayout(new MigLayout("", "[][]", "[][][]"));
		
		JLabel lblNewLabel_3 = new JLabel("Total de NF's:");
		lblNewLabel_3.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_2.add(lblNewLabel_3, "cell 0 0,alignx right");
		
		 lblNumTotalNfs = new JLabel("0000");
		lblNumTotalNfs.setFont(new Font("Dialog", Font.BOLD, 18));
		panel_2.add(lblNumTotalNfs, "cell 1 0");
		
		lblNewLabel_5 = new JLabel("Peso Total de NF's:");
		lblNewLabel_5.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_2.add(lblNewLabel_5, "cell 0 1");
		
		lblPesoTotalNf = new JLabel("0.0");
		lblPesoTotalNf.setFont(new Font("Dialog", Font.BOLD, 18));
		panel_2.add(lblPesoTotalNf, "cell 1 1");
		
		lblNewLabel_6 = new JLabel("Valor Total de NF's:");
		lblNewLabel_6.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_2.add(lblNewLabel_6, "cell 0 2");
		
		lblValorTotalNf = new JLabel("0.0");
		lblValorTotalNf.setFont(new Font("Dialog", Font.BOLD, 18));
		panel_2.add(lblValorTotalNf, "cell 1 2");
		btnVizualizarNF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int rowSel = table_nfs.getSelectedRow();//pega o indice da linha na tabela
				int indexRowModel = table_nfs.getRowSorter().convertRowIndexToModel(rowSel);//converte pro indice do model
				CadastroNFe nota_vizualizar = notas_fiscais_disponivel.get(indexRowModel);
				
				if (Desktop.isDesktopSupported()) {
					 try {
					     Desktop desktop = Desktop.getDesktop();
					     File myFile = new File(nota_vizualizar.getCaminho_arquivo());
					     desktop.open(myFile);
					     } catch (IOException ex) {}
					 }
			}
		});
		
		

		

		if(flag == 1) {
			//esconder o botao selecionar

		
			
		}else if(flag == 0) {
			//esconder o botão vizualizar nf
			//btnVizualizarNF.setVisible(false);
			//btnVizualizarNF.setEnabled(false);
			
		}
		
		
		
		new Thread() {
			@Override
			public void run() {
				pesquisarNotas(vendedor);
				calcular();

			}
		}.start();

		this.setLocationRelativeTo(janela_pai);

	}

	public void pesquisarNotas(CadastroCliente vendedor) {
		


		// acessar caminho desses vendedores
           try {
	
			String nome_pasta;

			if (vendedor.getTipo_pessoa() == 0) {
				nome_pasta = vendedor.getNome_empresarial().toUpperCase();
			} else {
				nome_pasta = vendedor.getNome_fantaia().toUpperCase();
			}

			String unidade_base_dados = configs_globais.getServidorUnidade();
			String sub_pasta = "E-Contract\\arquivos\\clientes";

			String caminho_completo_nf = unidade_base_dados + "\\" + sub_pasta + "\\" + nome_pasta.toUpperCase().trim() + "\\"
					+ "NOTAS FISCAIS";

			ManipularNotasFiscais manipular_notas = new ManipularNotasFiscais(caminho_completo_nf);
			manipular_notas.setPai(isto);
			ArrayList<CadastroNFe> notas_fiscais = manipular_notas.tratar();

			/*
			 * for (CadastroNFe nota : notas_fiscais) {
			 * 
			 * java.awt.EventQueue.invokeLater(new Runnable() { public void run() {
			 * modelo_nfs.addRow(new Object[] { nota.getNfe(), nota.getSerie(),
			 * nota.getNome_remetente(), nota.getInscricao_remetente(), nota.getProtocolo(),
			 * nota.getData(), nota.getNatureza(), nota.getNome_destinatario(),
			 * nota.getInscricao_destinatario(), nota.getProduto(), nota.getQuantidade(),
			 * nota.getValor() });
			 * 
			 * table_nfs.repaint(); table_nfs.updateUI();
			 * notas_fiscais_disponivel.add(nota);
			 * 
			 * } }); }
			*/
		
           }catch(Exception f) {
        	   JOptionPane.showMessageDialog(null, "Erro ao listar notas fiscaisz\nCausa: " + f.getCause() + "\nErro: " + f.getMessage());
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

	public void setPai(JDialog _pai) {
		this.tela_pai = _pai;
	}

	public void addNota(CadastroNFe nota) {

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				modelo_nfs.onAdd(nota);
			/*	modelo_nfs.addRow(new Object[] { nota.getNfe(), nota.getSerie(), nota.getNome_remetente(),
						nota.getInscricao_remetente(), nota.getProtocolo(), nota.getData(), nota.getNatureza(),
						nota.getNome_destinatario(), nota.getInscricao_destinatario(), nota.getProduto(),
						nota.getQuantidade(), nota.getValor() });*/

				lblStatusAdicionandoNotas
						.setText("Aguarde, notas estão sendo carregadas: Adicionando nota fiscal " + nota.getNfe());
				lblStatusAdicionandoNotas.repaint();
				lblStatusAdicionandoNotas.updateUI();

				notas_fiscais_disponivel.add(nota);

			}
		});

	}

	
	
	
	public static class NFeTableModel extends AbstractTableModel{
		 
	    //constantes p/identificar colunas
	    private final int nfe=0;
	    private final int serie=1;
	    private final int remetente=2;
	    private final int inscricao_rem=3;
	    private final int protocolo=4;
	    private final int data_nfe=5;
	    private final int natureza=6;
	    private final int destinatario=7;
	    private final int inscricao_dest=8;
	    private final int produto=9;
	    private final int medida = 10;
	    private final int quantidade=11;
	    private final int valor=12;

	 
	    private final String colunas[]={"NFe:","Serie:","Remetente:","Inscrição:","Protocolo:","Data:",
	    		"Natureza:", "Destinatario:", "Inscrição:", "Produto:", "Medida:", "Quantidade:", "Valor:"};
	    private final ArrayList<CadastroNFe> dados = new ArrayList<>();//usamos como dados uma lista genérica de nfs
	 
	    public NFeTableModel() {
	        
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
	        case nfe:
	            return String.class;
	        case serie:
	            return String.class;
	        case remetente:
	            return String.class;
	        case inscricao_rem:
	            return String.class;
	        case protocolo:
	            return String.class;
	        case data_nfe:
	            return Date.class;
	        case natureza:
	            return String.class;
	        case destinatario:
	            return String.class;
	        case inscricao_dest:
	            return String.class;
	        case produto:
	            return String.class;
	        case medida:
	            return String.class;
	        case quantidade:
	            return String.class;
	        case valor:
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
	        CadastroNFe nota=dados.get(rowIndex);
	 
	        //retorna o valor da coluna
	        switch (columnIndex) {
	        case nfe:
	            return nota.getNfe();
	        case serie:
	            return nota.getSerie();
	        case remetente:
	            return nota.getNome_remetente().trim().toUpperCase();
	        case inscricao_rem:
	            return nota.getInscricao_remetente();
	        case protocolo:
	            return nota.getProtocolo();
	        case data_nfe:{
	      
	        	return nota.getData();
	        }
	        case natureza:
	            return nota.getNatureza();
	        case destinatario:
	            return nota.getNome_destinatario().trim().toUpperCase();
	        case inscricao_dest:
	            return nota.getInscricao_destinatario();
	        case produto:
	            return nota.getProduto();
	        case medida:{	
				if (nota.getMedida() == null || nota.getMedida().equals("null")) {
					return "KG";
				}else {
					return nota.getMedida();
				}
			}
	        case quantidade:
	            return nota.getQuantidade();
	        case valor:
	            return nota.getValor();
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
	        CadastroNFe nota=dados.get(rowIndex);
	 
	      
	    }
	 
	    //Métodos abaixo são para manipulação de dados
	 
	    /**
	     * retorna o valor da linha indicada
	     * @param rowIndex
	     * @return
	     */
	    public CadastroNFe getValue(int rowIndex){
	        return dados.get(rowIndex);
	    }
	 
	    /**
	     * retorna o indice do objeto
	     * @param empregado
	     * @return
	     */
	    public int indexOf(CadastroNFe nota) {
	        return dados.indexOf(nota);
	    }
	 
	    /**
	     * add um empregado á lista
	     * @param empregado
	     */
	    public void onAdd(CadastroNFe nota) {
	        dados.add(nota);
	        fireTableRowsInserted(indexOf(nota), indexOf(nota));
	    }
	 
	    /**
	     * add uma lista de empregados
	     * @param dadosIn
	     */
	    public void onAddAll(ArrayList<CadastroNFe> dadosIn) {
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
	    public void onRemove(CadastroNFe nota) {
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

	public HSSFWorkbook exportar(ArrayList<CadastroNFe> notas_selecionadas) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Notas");

		// Definindo alguns padroes de layout
		sheet.setDefaultColumnWidth(15);
		sheet.setDefaultRowHeight((short) 400);

		int rownum = 0;
		int cellnum = 0;
		Cell cell;
		Row row;

		// Configurando estilos de células (Cores, alinhamento, formatação, etc..)
		HSSFDataFormat numberFormat = workbook.createDataFormat();
		HSSFFont newFont_blabk = workbook.createFont();
		newFont_blabk.setBold(true);
		newFont_blabk.setColor(IndexedColors.BLACK.getIndex());
		newFont_blabk.setFontName("Calibri");
		newFont_blabk.setItalic(false);
		newFont_blabk.setFontHeight((short) (11 * 24));
		// estilo para cabecalho
		CellStyle celula_cabecalho = workbook.createCellStyle();
		celula_cabecalho.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		celula_cabecalho.setFillForegroundColor(IndexedColors.BROWN.getIndex());
		celula_cabecalho.setAlignment(HorizontalAlignment.CENTER);
		celula_cabecalho.setVerticalAlignment(VerticalAlignment.CENTER);
		celula_cabecalho.setFont(newFont_blabk);

		CellStyle textStyle = workbook.createCellStyle();
		textStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		textStyle.setAlignment(HorizontalAlignment.CENTER);
		textStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		// celula para numero alinhado ao centro
		CellStyle valorStyle = workbook.createCellStyle();
		valorStyle.setDataFormat(numberFormat.getFormat("R$ #,##0.00"));
		valorStyle.setAlignment(HorizontalAlignment.CENTER);
		valorStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		// celula para numero alinhado ao centro
		CellStyle numberStyle = workbook.createCellStyle();
		numberStyle.setDataFormat(numberFormat.getFormat("#,##0.00"));
		numberStyle.setAlignment(HorizontalAlignment.CENTER);
		numberStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		HSSFFont newFont_titulo = workbook.createFont();
		newFont_titulo.setBold(true);
		newFont_titulo.setColor(IndexedColors.BLACK.getIndex());
		newFont_titulo.setFontName("Calibri");
		newFont_titulo.setItalic(true);
		newFont_titulo.setFontHeight((short) (11 * 32));

		// estilo para cabecalho
		CellStyle celula_titulo = workbook.createCellStyle();
		celula_titulo.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		celula_titulo.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		celula_titulo.setAlignment(HorizontalAlignment.CENTER);
		celula_titulo.setVerticalAlignment(VerticalAlignment.CENTER);
		celula_titulo.setFont(newFont_titulo);
		
		
		
		// estilo para cabecalho fundo laranja
		CellStyle celula_fundo_laranja_texto_branco = workbook.createCellStyle();
		celula_fundo_laranja_texto_branco.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		celula_fundo_laranja_texto_branco.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		celula_fundo_laranja_texto_branco.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_laranja_texto_branco.setVerticalAlignment(VerticalAlignment.CENTER);

		
		CellStyle valorStyleFundoVerdeTextoBranco = workbook.createCellStyle();
		valorStyleFundoVerdeTextoBranco.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		valorStyleFundoVerdeTextoBranco.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		valorStyleFundoVerdeTextoBranco.setAlignment(HorizontalAlignment.CENTER);
		valorStyleFundoVerdeTextoBranco.setVerticalAlignment(VerticalAlignment.CENTER);
		valorStyleFundoVerdeTextoBranco.setDataFormat(numberFormat.getFormat("R$ #,##0.00"));
		
		CellStyle numberStyleFundoVerdeTextoBranco = workbook.createCellStyle();
		numberStyleFundoVerdeTextoBranco.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		numberStyleFundoVerdeTextoBranco.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		numberStyleFundoVerdeTextoBranco.setAlignment(HorizontalAlignment.CENTER);
		numberStyleFundoVerdeTextoBranco.setVerticalAlignment(VerticalAlignment.CENTER);
		numberStyleFundoVerdeTextoBranco.setDataFormat(numberFormat.getFormat("#,##0.00"));
		
		HSSFFont newFont_branca = workbook.createFont();
		newFont_branca.setBold(true);
		newFont_branca.setColor(IndexedColors.WHITE.getIndex());
		newFont_branca.setFontName("Calibri");
		newFont_branca.setItalic(false);
		newFont_branca.setFontHeight((short) (11 * 20));
		Locale ptBr = new Locale("pt", "BR");

		celula_fundo_laranja_texto_branco.setFont(newFont_branca);
		numberStyleFundoVerdeTextoBranco.setFont(newFont_branca);
		valorStyleFundoVerdeTextoBranco.setFont(newFont_branca);

		// Configurando as informacoes
		row = sheet.createRow(rownum++);

		// Configurando titulo
		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_titulo);
		cell.setCellValue("Relatório de Notas Fiscais");
		// criar celula de 1 a 5
		for (int i = 1; i < 6; i++) {
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_titulo);
			cell.setCellValue("");

		}
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

		cellnum = 0;

		// Configurando Header
		row = sheet.createRow(rownum++);
		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_cabecalho);
		cell.setCellValue("NFE");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_cabecalho);
		cell.setCellValue("Serie");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_cabecalho);
		cell.setCellValue("Remetente");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_cabecalho);
		cell.setCellValue("Inscricao");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_cabecalho);
		cell.setCellValue("Protocolo");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_cabecalho);
		cell.setCellValue("Data");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_cabecalho);
		cell.setCellValue("Natureza");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_cabecalho);
		cell.setCellValue("Destinatario");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_cabecalho);
		cell.setCellValue("Inscricao");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_cabecalho);
		cell.setCellValue("Produto");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_cabecalho);
		cell.setCellValue("Quantidade");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_cabecalho);
		cell.setCellValue("Valor");
		
		int ultima_linha = 3;

		for (CadastroNFe cadastro : notas_selecionadas) {
			row = sheet.createRow(rownum++);
			cellnum = 0;

			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(cadastro.getNfe());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(cadastro.getSerie());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(cadastro.getNome_remetente());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(cadastro.getInscricao_remetente());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(cadastro.getProtocolo());

			// data
			SimpleDateFormat f = new SimpleDateFormat("dd/MMMM/yyyy");
			String data_formatada = f.format(cadastro.getData());
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(data_formatada);

			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(cadastro.getNatureza());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(cadastro.getNome_destinatario());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(cadastro.getInscricao_destinatario());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(cadastro.getProduto());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(numberStyle);
			double quantidade_local = 0.0;

			try {

				if (cadastro.getMedida() == null || cadastro.getMedida().equals("null")) {

					if (cadastro.getQuantidade().contains(",")) {
						quantidade_local= Double
								.parseDouble(cadastro.getQuantidade().replaceAll("[^0-9,]", "").replaceAll(",", "."));
					} else {
						quantidade_local += Double.parseDouble(cadastro.getQuantidade());

					}

				} else if (cadastro.getMedida().equalsIgnoreCase("KG")) {
					if (cadastro.getQuantidade().contains(",")) {
						quantidade_local= Double
								.parseDouble(cadastro.getQuantidade().replaceAll("[^0-9,]", "").replaceAll(",", "."));
					} else {
						quantidade_local = Double.parseDouble(cadastro.getQuantidade());

					}
				} else if (cadastro.getMedida().equalsIgnoreCase("SC")) {
					if (cadastro.getQuantidade().contains(",")) {
						quantidade_local = ((Double
								.parseDouble(cadastro.getQuantidade().replaceAll("[^0-9,]", "").replaceAll(",", "."))) * 60);
					} else {
						quantidade_local = (Double.parseDouble(cadastro.getQuantidade()) * 60);

					}
				}
				cell.setCellValue(quantidade_local);

			} catch (Exception e) {

			}
			
			
			try {
				cell = row.createCell(cellnum++);
				cell.setCellStyle(valorStyle);

				String valor_nota = cadastro.getValor().replaceAll("[^0-9,]", "");

				valor_nota = valor_nota.replaceAll(",", ".");

				cell.setCellValue(Double.parseDouble(valor_nota));
			} catch (Exception e) {

			}
			
			ultima_linha++;

		}

		sheet.setAutoFilter(CellRangeAddress.valueOf("A2:L2"));
		for (int i = 1; i < 13; i++) {
			sheet.autoSizeColumn(i);

		}
		
		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

		row = sheet.createRow(rownum += 2);
		cellnum = 0;
		

		cell = row.createCell(2);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("Total de NF's:");

		cell = row.createCell(3);
		cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
		cell.setCellType(CellType.FORMULA);
		String formula = "SUBTOTAL(3,C3:C" + (ultima_linha) + ")";
		cell.setCellFormula(formula);

		row = sheet.createRow(rownum += 1);
		cellnum = 0;
		
		cell = row.createCell(2);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("Quantidade Total(kgs):");
		
		cell = row.createCell(3);
		cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
		cell.setCellType(CellType.FORMULA);
		formula = "SUMPRODUCT(SUBTOTAL(9,(K3:K"+ ultima_linha + ")))";
		cell.setCellFormula(formula);
		
		cell = row.createCell(4);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("(sacos):");
		
		cell = row.createCell(5);
		cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
		cell.setCellType(CellType.FORMULA);
		formula = "SUMPRODUCT(SUBTOTAL(9,(K3:K"+ ultima_linha + "))) / 60";
		cell.setCellFormula(formula);
		
		row = sheet.createRow(rownum += 1);
		cellnum = 0;
		
		cell = row.createCell(2);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("Valor Total:");
		
		cell = row.createCell(3);
		cell.setCellStyle(valorStyleFundoVerdeTextoBranco);
		cell.setCellType(CellType.FORMULA);
		formula = "SUMPRODUCT(SUBTOTAL(9,(L3:L"+ ultima_linha + ")))";
		cell.setCellFormula(formula);
		
		
		
		return workbook;

	}

	
	public void gerarExcel(HSSFWorkbook workbook) {
		try {

			new JFXPanel();
			Platform.runLater(() -> {

				// pegar ultima pasta
				ManipularTxt manipular_ultima_pasta = new ManipularTxt();
				String ultima_pasta = manipular_ultima_pasta
						.lerArquivo(new File("C:\\ProgramData\\E-Contract\\configs\\ultima_pasta.txt"));
				if (fileChooser == null) {
					fileChooser = new FileChooser();
				}
				fileChooser.setInitialDirectory(new File(ultima_pasta));
				fileChooser.getExtensionFilters().addAll(

						new FileChooser.ExtensionFilter("Excel", "*.xls"));
				File file = fileChooser.showSaveDialog(new Stage());
				String caminho_arquivo = "";
				if (file != null) {
					caminho_arquivo = file.getAbsolutePath();

					manipular_ultima_pasta.rescreverArquivo(
							new File("C:\\ProgramData\\E-Contract\\configs\\ultima_pasta.txt"), file.getParent());
					// Escrevendo o arquivo em disco
					FileOutputStream out;
					try {
						out = new FileOutputStream(file);
						workbook.write(out);
						workbook.close();
						out.close();
						// workbook.close();

						Runtime.getRuntime().exec("explorer " + file.getAbsolutePath());

						System.out.println("Success!!");
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

			});

		} catch (Exception k) {
			k.printStackTrace();
		}
	}
	
	
	
	public void importar() {
		String unidade_base_dados = configs_globais.getServidorUnidade();
		String sub_pasta = "E-Contract\\arquivos\\arquivos_comuns";
		String pasta_final = unidade_base_dados + "\\" + sub_pasta;
		ArrayList<CadastroCliente> clientes  = new GerenciarBancoClientes().getClientes(0, 0, "");

		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setPreferredSize(new Dimension(800, 600));
		fileChooser.setMultiSelectionEnabled(true);
		FileNameExtensionFilter  filter = new FileNameExtensionFilter("Excel file", "xls", "xlsx");
		 fileChooser.addChoosableFileFilter(filter);
		if(contador == 0)
		{
			//fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
			//fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fileChooser_global = fileChooser;
            contador++;
		}
		else
		{
			fileChooser = fileChooser_global;
			
		}
		int result = fileChooser.showOpenDialog(isto);
		
		File[] files = fileChooser.getSelectedFiles();

		
		
		for(File arquivo : files) {
			ManipularNotasFiscais manipular = new ManipularNotasFiscais("");

			try {
			CadastroNFe cadastro = manipular.filtrar(arquivo);
			
			//verifica se essa nota ja existe
			boolean ja_existe = false;
			for(CadastroNFe nfe : notas_fiscais_disponivel) {
				if(nfe.getNfe().equals(cadastro.getNfe())) {
					ja_existe = true;
					break;
				}
			}
			
			if(!ja_existe) {
				
				boolean remetente_cadastrado = false;
				CadastroCliente remetente = null;
				
				boolean destinatario_cadastrado = false;
				CadastroCliente destinatario =null;
				
			    //verifica-se  o remetente esta cadastrao
			   for(CadastroCliente rem : clientes) {
				   if(rem.getIe().equals(cadastro.getInscricao_remetente())) {
					   JOptionPane.showMessageDialog(null, "remetente cadastrado");
					   remetente_cadastrado = true;
					   remetente = rem;
					   break;
				   }
			   }
			   
			 //verifica-se  o destinatario esta cadastrao
			   for(CadastroCliente dest : clientes) {
				   if(dest.getIe().equals(cadastro.getInscricao_destinatario())) {
					   JOptionPane.showMessageDialog(null, "destinatario cadastrado");
					   destinatario_cadastrado = true;
					   destinatario = dest;
					   break;
				   }
			   }
			   
			   
			   if (remetente_cadastrado && !destinatario_cadastrado) {
					//copiar para pasta do remetente
					ManipularTxt manipular_txt = new ManipularTxt();
					String nome_pasta;
					if (remetente.getTipo_pessoa() == 0) {
						nome_pasta = remetente.getNome_empresarial().toUpperCase();
					} else {

						nome_pasta = remetente.getNome_fantaia().toUpperCase();
					}
					unidade_base_dados = configs_globais.getServidorUnidade();
					sub_pasta = "E-Contract\\arquivos\\clientes";
					ManipularTxt manipular_arq = new ManipularTxt();
					nome_pasta = nome_pasta.trim();
					String caminho_completo_nf = unidade_base_dados + "\\" + sub_pasta + "\\" + nome_pasta.toUpperCase() + "\\"
							+ "NOTAS FISCAIS" + "\\NFA-" + cadastro.getNfe().trim() + ".pdf";
					
					// JOptionPane.showMessageDialog(null, "Movendo de :\n" +
					// roms.getCaminho_arquivo()+ "\nPara:\n" + caminho_completo_nf);
				   
					File file = new File(caminho_completo_nf);
					if (!file.exists()) {
						boolean mover = manipular_arq.copiarNFe(cadastro.getCaminho_arquivo(),
								caminho_completo_nf);
						if (mover) {
							
							 JOptionPane.showMessageDialog(null, "NF copiada para a pasta do remetente");
						} else {
							 JOptionPane.showMessageDialog(null, "Erro ao mover a nf para a pasta do remetente");
							
						}
					} else {
						JOptionPane.showMessageDialog(null, "NF já importada");
					}
			   }else if(!remetente_cadastrado && destinatario_cadastrado) {
				 //copiar para pasta do destinatario
					ManipularTxt manipular_txt = new ManipularTxt();
					String nome_pasta;
					if (remetente.getTipo_pessoa() == 0) {
						nome_pasta = destinatario.getNome_empresarial().toUpperCase();
					} else {

						nome_pasta = destinatario.getNome_fantaia().toUpperCase();
					}
					unidade_base_dados = configs_globais.getServidorUnidade();
					sub_pasta = "E-Contract\\arquivos\\clientes";
					ManipularTxt manipular_arq = new ManipularTxt();
					nome_pasta = nome_pasta.trim();
					String caminho_completo_nf = unidade_base_dados + "\\" + sub_pasta + "\\" + nome_pasta.toUpperCase() + "\\"
							+ "NOTAS FISCAIS" + "\\NFA-" + cadastro.getNfe().trim() + ".pdf";
					
					// JOptionPane.showMessageDialog(null, "Movendo de :\n" +
					// roms.getCaminho_arquivo()+ "\nPara:\n" + caminho_completo_nf);
				   
					File file = new File(caminho_completo_nf);
					if (!file.exists()) {
						boolean mover = manipular_arq.copiarNFe(cadastro.getCaminho_arquivo(),
								caminho_completo_nf);
						if (mover) {
							
							 JOptionPane.showMessageDialog(null, "NF copiada para a pasta do destinatario");
						} else {
							 JOptionPane.showMessageDialog(null, "Erro ao mover a nf para a pasta do destinatario");
							
						}
					} else {
						JOptionPane.showMessageDialog(null, "NF já importada");
					}
			   }else if(remetente_cadastrado && destinatario_cadastrado) {
					//copiar para pasta do remetente
					ManipularTxt manipular_txt = new ManipularTxt();
					String nome_pasta;
					if (remetente.getTipo_pessoa() == 0) {
						nome_pasta = remetente.getNome_empresarial().toUpperCase();
					} else {

						nome_pasta = remetente.getNome_fantaia().toUpperCase();
					}
					JOptionPane.showMessageDialog(null, "nome da pasta remetente: " + nome_pasta);

					unidade_base_dados = configs_globais.getServidorUnidade();
					sub_pasta = "E-Contract\\arquivos\\clientes";
					ManipularTxt manipular_arq = new ManipularTxt();
					nome_pasta = nome_pasta.trim();
					String caminho_completo_nf = unidade_base_dados + "\\" + sub_pasta + "\\" + nome_pasta.toUpperCase() + "\\"
							+ "NOTAS FISCAIS" + "\\NFA-" + cadastro.getNfe().trim() + ".pdf";
					
					// JOptionPane.showMessageDialog(null, "Movendo de :\n" +
					// roms.getCaminho_arquivo()+ "\nPara:\n" + caminho_completo_nf);
				   
					File file = new File(caminho_completo_nf);
					if (!file.exists()) {
						boolean mover = manipular_arq.copiarNFe(cadastro.getCaminho_arquivo(),
								caminho_completo_nf);
						if (mover) {
							
							 JOptionPane.showMessageDialog(null, "NF copiada para a pasta do remetente");
						} else {
							 JOptionPane.showMessageDialog(null, "Erro ao mover a nf para a pasta do remetente");
							
						}
					} else {
						JOptionPane.showMessageDialog(null, "NF já importada");
					}
					
					//copiar para pasta do destinatario
					if (destinatario.getTipo_pessoa() == 0) {
						nome_pasta = destinatario.getNome_empresarial().toUpperCase();
					} else {

						nome_pasta = destinatario.getNome_fantaia().toUpperCase();
					}
					JOptionPane.showMessageDialog(null, "nome da pasta destinatario: " + nome_pasta);

					unidade_base_dados = configs_globais.getServidorUnidade();
					sub_pasta = "E-Contract\\arquivos\\clientes";
					nome_pasta = nome_pasta.trim();
					 caminho_completo_nf = unidade_base_dados + "\\" + sub_pasta + "\\" + nome_pasta.toUpperCase() + "\\"
							+ "NOTAS FISCAIS" + "\\NFA-" + cadastro.getNfe().trim() + ".pdf";
					
					// JOptionPane.showMessageDialog(null, "Movendo de :\n" +
					// roms.getCaminho_arquivo()+ "\nPara:\n" + caminho_completo_nf);
				   
					 file = new File(caminho_completo_nf);
					if (!file.exists()) {
						boolean mover = manipular_arq.copiarNFe(cadastro.getCaminho_arquivo(),
								caminho_completo_nf);
						if (mover) {
							
							 JOptionPane.showMessageDialog(null, "NF copiada para a pasta do destinatario");
						} else {
							 JOptionPane.showMessageDialog(null, "Erro ao mover a nf para a pasta do destinatario");
							
						}
					} else {
						JOptionPane.showMessageDialog(null, "NF já importada");
					}
					
					
			   }else {
				JOptionPane.showMessageDialog(isto, "NF lida mas nem o remetente nem o destinatario esta cadastrado");
					 
			   }
				
				
			
			}else {
				JOptionPane.showMessageDialog(null, "Arquivo selecionado:\n" + arquivo.getAbsolutePath() + "\nJá está adicionado");

			}

			}catch(Exception e) {
				JOptionPane.showMessageDialog(null, "Arquivo selecionado:\n" + arquivo.getAbsolutePath() + "\nNão é uma nota fiscal valida, por isso não foi adicionado");

			}
		}
		
		//verifica se o arquivo e uma nota fiscal valida
		
	}
	
	public void filtrar() {
		 ArrayList<RowFilter<Object,Object>> filters = new ArrayList<RowFilter<Object,Object>>(2);

		    String produto = entProduto.getText().toUpperCase();
		    String destinatario =  entChavePesquisa.getText().toUpperCase();
		    String remetente = entRemetente.getText().toUpperCase();
		    String natureza = entNatureza.getText().toUpperCase();

		    String menor = entMenorData.getText();
		    String maior = entMaiorData.getText();
		    
		    if(checkString(menor) && checkString(maior) ) {
			Date data_menor = null;
			Date data_maior = null ;
			try {
				data_menor = new SimpleDateFormat("dd/MM/yyyy").parse(menor);
				data_maior = new SimpleDateFormat("dd/MM/yyyy").parse(maior);

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Set<RowFilter<Object, Object>> datas = new HashSet<>();
			datas.add(RowFilter.dateFilter(RowFilter.ComparisonType.AFTER,
					data_menor, 5));
			datas.add(RowFilter.dateFilter(RowFilter.ComparisonType.EQUAL,
					data_menor, 5));
			filters.add(RowFilter.orFilter(datas));
	        
		  //  filters.add( RowFilter.dateFilter(ComparisonType.AFTER, data_menor, 5) );
		   // filters.add( RowFilter.dateFilter(ComparisonType.EQUAL, data_menor, 5) );

		   // filters.add( RowFilter.dateFilter(ComparisonType.BEFORE, data_maior, 5) );
		   // filters.add( RowFilter.dateFilter(ComparisonType.EQUAL, data_maior, 5) );
			Set<RowFilter<Object, Object>> datas_maior = new HashSet<>();
			datas_maior.add(RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE,
					data_maior, 5));
			datas_maior.add(RowFilter.dateFilter(RowFilter.ComparisonType.EQUAL,
					data_maior, 5));
			filters.add(RowFilter.orFilter(datas_maior));
		    }
		    if(checkString(remetente))
		    filters.add(RowFilter.regexFilter(remetente, 2));
		    
		    if(checkString(natureza))
		    filters.add(RowFilter.regexFilter(natureza, 6));

		    if(checkString(destinatario))
		    filters.add(RowFilter.regexFilter(destinatario, 7));
		    
		    if(checkString(produto))
		    filters.add(RowFilter.regexFilter(produto, 9));
		    
		    sorter.setRowFilter( RowFilter.andFilter(filters));
		    calcular();
	}
	
	public boolean checkString(String txt) {
		return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ");
	}
	
	public void setDadosPesquisa(String destinatario, String remetente, String natureza, String produto) {
		
		entChavePesquisa.setText(destinatario);
		entRemetente.setText(remetente);
		entNatureza.setText(natureza);
		entProduto.setText(produto);
	
		
		filtrar();
		calcular();
	}
	
	public void calcular() {

		int num_total_nfs = 0;
		double quantidade_total = 0;
		BigDecimal valor_total = BigDecimal.ZERO;

		for (int row = 0; row < table_nfs.getRowCount(); row++) {

			int index = table_nfs.convertRowIndexToModel(row);
			CadastroNFe nf = modelo_nfs.getValue(index);
			try {

				if (nf.getMedida() == null || nf.getMedida().equals("null")) {

					if (nf.getQuantidade().contains(",")) {
						quantidade_total += Double
								.parseDouble(nf.getQuantidade().replaceAll("[^0-9,]", "").replaceAll(",", "."));
					} else {
						quantidade_total += Double.parseDouble(nf.getQuantidade());

					}

				} else if (nf.getMedida().equalsIgnoreCase("KG")) {
					if (nf.getQuantidade().contains(",")) {
						quantidade_total += Double
								.parseDouble(nf.getQuantidade().replaceAll("[^0-9,]", "").replaceAll(",", "."));
					} else {
						quantidade_total += Double.parseDouble(nf.getQuantidade());

					}
				} else if (nf.getMedida().equalsIgnoreCase("SC")) {
					if (nf.getQuantidade().contains(",")) {
						quantidade_total += ((Double
								.parseDouble(nf.getQuantidade().replaceAll("[^0-9,]", "").replaceAll(",", "."))) * 60);
					} else {
						quantidade_total += (Double.parseDouble(nf.getQuantidade()) * 60);

					}
				}

			} catch (Exception e) {

			}
			Number valor = null;
			try {
				valor = z.parse(nf.getValor().replaceAll("[^0-9.,]", ""));
				valor_total = valor_total.add(new BigDecimal(valor.doubleValue()));

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			num_total_nfs++;

		}

		lblNumTotalNfs.setText(num_total_nfs + "");

		String valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor_total);
		lblValorTotalNf.setText(valorString);

		lblPesoTotalNf.setText(z.format(quantidade_total) + " Kgs | " + z.format(quantidade_total / 60) + " sacos");
	}
}
