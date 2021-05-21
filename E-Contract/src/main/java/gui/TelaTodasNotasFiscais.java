package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
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
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;



import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


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
import main.java.cadastros.CadastroAviso;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroContrato.CadastroPagamento;
import main.java.cadastros.CadastroContrato.CadastroPagamentoContratual;
import main.java.cadastros.CadastroContrato.Recebimento;
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
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoNotasFiscais;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoRomaneios;
import main.java.conexaoBanco.GerenciarBancoSafras;
import main.java.gui.TelaCadastroCliente;
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
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoSafras;
import net.miginfocom.swing.MigLayout;
public class TelaTodasNotasFiscais extends JFrame {

	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
    private int retorno_global;
	private JDialog tela_pai;
	private String servidor_unidade;
	private ArrayList<CadastroNFe> notas_fiscais_disponivel = new ArrayList<>();
	private JTable table_nfs;
	private TelaTodasNotasFiscais isto;
	private JButton btnSelecionarNota;
	private JLabel lblStatusAdicionandoNotas;
	private int contador = 0;
	Locale ptBr = new Locale("pt", "BR");
	NumberFormat z = NumberFormat.getNumberInstance();

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
	private JButton btnNewButton;
	private JButton btnReleitura;
	private JTextField entCodigo;
	private JTextField entIdentificacaoRemetente;
	private JTextField entIdentificacaoDestinatario;
	private CadastroCliente cliente_selecionado;
	private JPanel panel_1;
	private JPanel panel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblNumTotalNfs;
	private JLabel lblNewLabel_5;
	private JLabel lblNewLabel_6;
	private JLabel lblPesoTotalNFs;
	private JLabel lblValorTotalNfs;
	private JLabel lblInteficacaoAlvo;

	public TelaTodasNotasFiscais(int flag, int retorno, Window janela_pai) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(TelaNotasFiscais.class.getResource("/imagens/icone_notas_fiscais.png")));
		//setAlwaysOnTop(true);

		//setModal(true);
		isto = this;
		getDadosGlobais();
		setResizable(false);
		setTitle("E-Contract - Notas Fiscais");

		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1299, 663);
		painelPrincipal.setBackground(Color.WHITE);
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(26, 209, 1247, 310);
		painelPrincipal.add(panel);

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
		
		entProduto.setBounds(146, 170, 242, 28);
		painelPrincipal.add(entProduto);
		entProduto.setColumns(10);
		
		entChavePesquisa = new JTextField();
		
		entChavePesquisa.setBounds(145, 137, 268, 28);
		painelPrincipal.add(entChavePesquisa);
		entChavePesquisa.setColumns(10);

		
		
		lblStatusAdicionandoNotas = new JLabel("Adicionando Notas...");
		lblStatusAdicionandoNotas.setBounds(853, 590, 420, 23);
		painelPrincipal.add(lblStatusAdicionandoNotas);
		
		btnFiltrar = new JButton("Filtrar");
		btnFiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
					   filtrar();
					   calcular();

				
			}
		});
		btnFiltrar.setBounds(1149, 137, 59, 28);
		painelPrincipal.add(btnFiltrar);
		
		lblNewLabel = new JLabel("Destinatario:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(63, 141, 77, 17);
		painelPrincipal.add(lblNewLabel);
		
		lblRemetente = new JLabel("Remetente:");
		lblRemetente.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRemetente.setBounds(68, 110, 72, 17);
		painelPrincipal.add(lblRemetente);
		
		entRemetente = new JTextField();
		entRemetente.setColumns(10);
		entRemetente.setBounds(145, 105, 268, 28);
		painelPrincipal.add(entRemetente);
		
		lblNatureza = new JLabel("Natureza:");
		lblNatureza.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNatureza.setBounds(403, 175, 59, 17);
		painelPrincipal.add(lblNatureza);
		
		entNatureza = new JTextField();
		entNatureza.setColumns(10);
		entNatureza.setBounds(464, 170, 237, 28);
		painelPrincipal.add(entNatureza);
		
		lblProduto = new JLabel("Produto:");
		lblProduto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblProduto.setBounds(86, 175, 55, 17);
		painelPrincipal.add(lblProduto);
		
		lblNewLabel_1 = new JLabel("Periodo");
		lblNewLabel_1.setBounds(825, 76, 43, 16);
		painelPrincipal.add(lblNewLabel_1);
		
		lblD = new JLabel("Dé:");
		lblD.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblD.setBounds(735, 111, 22, 17);
		painelPrincipal.add(lblD);
		
		lblAt = new JLabel("Até:");
		lblAt.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAt.setBounds(732, 139, 25, 17);
		painelPrincipal.add(lblAt);
		
		entMenorData = new JTextField();
		entMenorData.setColumns(10);
		entMenorData.setBounds(771, 98, 169, 28);
		painelPrincipal.add(entMenorData);
		
		entMaiorData = new JTextField();
		entMaiorData.setColumns(10);
		entMaiorData.setBounds(771, 137, 169, 28);
		painelPrincipal.add(entMaiorData);
		
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				    limpar();
				    calcular();
			}
		});
		btnLimpar.setBounds(1078, 137, 67, 28);
		painelPrincipal.add(btnLimpar);
		
		lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(TelaNotasFiscais.class.getResource("/imagens/icone_notas_fiscais.png")));
		lblNewLabel_2.setBounds(0, 6, 64, 64);
		painelPrincipal.add(lblNewLabel_2);
		
		lblNewLabel_4 = new JLabel("     NF's");
		lblNewLabel_4.setOpaque(true);
		lblNewLabel_4.setForeground(Color.WHITE);
		lblNewLabel_4.setFont(new Font("Arial", Font.PLAIN, 18));
		lblNewLabel_4.setBackground(new Color(0, 51, 0));
		lblNewLabel_4.setBounds(48, 48, 61, 22);
		painelPrincipal.add(lblNewLabel_4);
		
		btnReleitura = new JButton("Refazer Pesquisa");
		btnReleitura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			  pesquisar_notas();
			 
			}
		});
		btnReleitura.setBounds(973, 137, 90, 28);
		painelPrincipal.add(btnReleitura);
		

		if(flag == 1) {
			//esconder o botao selecionar

			 btnSelecionarNota.setVisible(false);

			 btnSelecionarNota.setEnabled(false);
			
		}else if(flag == 0) {
			//esconder o botão vizualizar nf
			//btnVizualizarNF.setVisible(false);
			//btnVizualizarNF.setEnabled(false);
			
		}
		
		
		JLabel lblCdigo = new JLabel("Código:");
		lblCdigo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCdigo.setBounds(718, 175, 48, 17);
		painelPrincipal.add(lblCdigo);
		
		entCodigo = new JTextField();
		entCodigo.setColumns(10);
		entCodigo.setBounds(771, 170, 169, 28);
		painelPrincipal.add(entCodigo);
		
		entIdentificacaoRemetente = new JTextField();
		entIdentificacaoRemetente.setColumns(10);
		entIdentificacaoRemetente.setBounds(464, 105, 237, 28);
		painelPrincipal.add(entIdentificacaoRemetente);
		
		JLabel lblCpfcnpj = new JLabel("IE:");
		lblCpfcnpj.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCpfcnpj.setBounds(445, 110, 17, 17);
		painelPrincipal.add(lblCpfcnpj);
		
		JLabel lblCpfcnpj_1 = new JLabel("IE:");
		lblCpfcnpj_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCpfcnpj_1.setBounds(445, 142, 17, 17);
		painelPrincipal.add(lblCpfcnpj_1);
		
		entIdentificacaoDestinatario = new JTextField();
		entIdentificacaoDestinatario.setColumns(10);
		entIdentificacaoDestinatario.setBounds(464, 137, 237, 28);
		painelPrincipal.add(entIdentificacaoDestinatario);
		
		panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(803, 530, 470, 49);
		painelPrincipal.add(panel_1);
		panel_1.setLayout(new MigLayout("", "[][][][][][][][][][][][][][][][][][][][][]", "[][][]"));
				 
				 btnNewButton = new JButton("Excluir");
				 panel_1.add(btnNewButton, "cell 13 0");
				 btnNewButton.addActionListener(new ActionListener() {
				 	public void actionPerformed(ActionEvent e) {
				 		
				 		if (JOptionPane.showConfirmDialog(isto, 
				 	            "Deseja excluir a NF?", "Excluir NF", 
				 	            JOptionPane.YES_NO_OPTION,
				 	            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
				 			int rowSel = table_nfs.getSelectedRow();//pega o indice da linha na tabela
				 			int indexRowModel = table_nfs.getRowSorter().convertRowIndexToModel(rowSel);//converte pro indice do model
				 			ManipularTxt manipular = new ManipularTxt();
				 			boolean apagado = manipular.apagarArquivo(notas_fiscais_disponivel.get(indexRowModel).getCaminho_arquivo());
				 			if(apagado) {
				 				modelo_nfs.onRemove(notas_fiscais_disponivel.get(indexRowModel));
				 				JOptionPane.showMessageDialog(isto, "NF Excluida");

				 			}else {
				 				JOptionPane.showMessageDialog(isto, "Erro ao excluir esta NF\nConsulte o administrador");
				 			}
				 	        }
				 		
				 		
				 		
				 	}
				 });
				 
				 btnImportarNFe = new JButton("Importar");
				 panel_1.add(btnImportarNFe, "cell 15 0");
				 btnImportarNFe.addActionListener(new ActionListener() {
				 	public void actionPerformed(ActionEvent e) {
				 		
				 		
				 	}
				 });
				 
				 btnExportar = new JButton("Exportar");
				 panel_1.add(btnExportar, "cell 17 0");
				 btnExportar.addActionListener(new ActionListener() {
				 	public void actionPerformed(ActionEvent e) {
				 		exportar();
				 	}
				 });
				 
				 btnVizualizarNF = new JButton("Vizualizar");
				 panel_1.add(btnVizualizarNF, "cell 19 0");
				 btnVizualizarNF.addActionListener(new ActionListener() {
				 	public void actionPerformed(ActionEvent e) {
				 		
				 		int rowSel = table_nfs.getSelectedRow();//pega o indice da linha na tabela
				 		int indexRowModel = table_nfs.getRowSorter().convertRowIndexToModel(rowSel);//converte pro indice do model
				 		CadastroNFe nota_vizualizar = notas_fiscais_disponivel.get(indexRowModel);
				 		
				 		if (Desktop.isDesktopSupported()) {
				 			 try {
				 			     Desktop desktop = Desktop.getDesktop();
				 			     File myFile = new File(servidor_unidade +   nota_vizualizar.getCaminho_arquivo());
				 			     desktop.open(myFile);
				 			     } catch (IOException ex) {}
				 			 }
				 	}
				 });
		
				
				
				 btnSelecionarNota = new JButton("Selecionar");
				 panel_1.add(btnSelecionarNota, "cell 20 0");
				 
				 panel_2 = new JPanel();
				 panel_2.setBackground(Color.WHITE);
				 panel_2.setBounds(26, 530, 578, 98);
				 painelPrincipal.add(panel_2);
				 panel_2.setLayout(new MigLayout("", "[][]", "[][][]"));
				 
				 lblNewLabel_3 = new JLabel("Total NF's:");
				 lblNewLabel_3.setFont(new Font("SansSerif", Font.PLAIN, 16));
				 panel_2.add(lblNewLabel_3, "cell 0 0");
				 
				 lblNumTotalNfs = new JLabel("000");
				 lblNumTotalNfs.setFont(new Font("SansSerif", Font.BOLD, 14));
				 panel_2.add(lblNumTotalNfs, "cell 1 0");
				 
				 lblNewLabel_5 = new JLabel("Peso Total NF's:");
				 lblNewLabel_5.setFont(new Font("SansSerif", Font.PLAIN, 16));
				 panel_2.add(lblNewLabel_5, "cell 0 1");
				 
				 lblPesoTotalNFs = new JLabel("000");
				 lblPesoTotalNFs.setFont(new Font("SansSerif", Font.BOLD, 14));
				 panel_2.add(lblPesoTotalNFs, "cell 1 1");
				 
				 lblNewLabel_6 = new JLabel("Valor Total NF's:");
				 lblNewLabel_6.setFont(new Font("SansSerif", Font.PLAIN, 16));
				 panel_2.add(lblNewLabel_6, "cell 0 2");
				 
				 lblValorTotalNfs = new JLabel("000");
				 lblValorTotalNfs.setFont(new Font("SansSerif", Font.BOLD, 14));
				 panel_2.add(lblValorTotalNfs, "cell 1 2");
				 
				 lblInteficacaoAlvo = new JLabel("   ");
				 lblInteficacaoAlvo.setVisible(false);
				 lblInteficacaoAlvo.setFont(new Font("SansSerif", Font.BOLD, 14));
				 lblInteficacaoAlvo.setBounds(145, 62, 380, 16);
				 painelPrincipal.add(lblInteficacaoAlvo);
				 btnSelecionarNota.addActionListener(new ActionListener() {
				 	public void actionPerformed(ActionEvent e) {
				 		
				 		int rowSel = table_nfs.getSelectedRow();//pega o indice da linha na tabela
				 		int indexRowModel = table_nfs.getRowSorter().convertRowIndexToModel(rowSel);//converte pro indice do model
				 		if(tela_pai instanceof TelaConfirmarCarregamento) {
				 			
				 			if(retorno_global == 1) {
				 				//retorna a nf interna
				 				((TelaConfirmarCarregamento) tela_pai).setNotaFiscalInterna(notas_fiscais_disponivel.get(indexRowModel));

				 			}else if(retorno_global == 2) {
				 				//retorna a nf de venda 1
				 				((TelaConfirmarCarregamento) tela_pai).setNotaFiscalVenda1(notas_fiscais_disponivel.get(indexRowModel));

				 			}else if(retorno_global == 3) {
				 				//retorna a nf de complemento
				 				((TelaConfirmarCarregamento) tela_pai).setNotaFiscalComplemento(notas_fiscais_disponivel.get(indexRowModel));

				 			}
				 			

				 		}
				 		else if(tela_pai instanceof TelaConfirmarRecebimento) {
				 			if(retorno_global == 1)
				 			((TelaConfirmarRecebimento) tela_pai).setNotaFiscalVenda(notas_fiscais_disponivel.get(indexRowModel));
				 			else if(retorno_global == 2)
				 				((TelaConfirmarRecebimento) tela_pai).setNotaFiscalRemessa(notas_fiscais_disponivel.get(indexRowModel));

				 		}
				 		
				 		isto.dispose();

				 	}
				 });
		
		
			

		this.setLocationRelativeTo(janela_pai);

	}



	public void getDadosGlobais() {
		// gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		GerenciadorLog = dados.getGerenciadorLog();
		configs_globais = dados.getConfigs_globais();

		// usuario logado
		login = dados.getLogin();
		
		servidor_unidade = configs_globais.getServidorUnidade();

	}

	
	public void pesquisar_notas( ) {

		new Thread() {
			@Override
			public void run() {
				notas_fiscais_disponivel.clear();
				modelo_nfs.onRemoveAll();
				
				GerenciarBancoNotasFiscais gerenciar = new GerenciarBancoNotasFiscais();
				ArrayList<CadastroNFe> lista_nfs ;

				
				if(cliente_selecionado == null) {
					lista_nfs = gerenciar.listarNFs();
				}else {
					lblInteficacaoAlvo.setVisible(true);
					String nome = "";
					if(cliente_selecionado.getTipo_pessoa() ==0 ) {
						nome = cliente_selecionado.getNome_empresarial();
					}else
						nome = cliente_selecionado.getNome_fantaia();
					
					lblInteficacaoAlvo.setText(nome + " I.E:  " + cliente_selecionado.getIe());
					
					lista_nfs = gerenciar.listarNFsPorCliente(cliente_selecionado.getIe());
					
				}
				
				for(CadastroNFe nf : lista_nfs) {
					notas_fiscais_disponivel.add(nf);
					modelo_nfs.onAdd(nf);
				}
				
				 calcular();
			}
		}.start();
				
			

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
	    private final int quantidade=10;
	    private final int valor=11;

	 
	    private final String colunas[]={"NFe:","Serie:","Remetente:","Inscrição:","Protocolo:","Data:",
	    		"Natureza:", "Destinatario:", "Inscrição:", "Produto:", "Quantidade:", "Valor:"};
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
	        case nfe:{
	            String codigo =  nota.getNfe().replaceAll("[^0-9]", "");
	            return Integer.valueOf(codigo).toString();

	        }
	        case serie:
	            return nota.getSerie();
	        case remetente:
	            return nota.getNome_remetente();
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
	            return nota.getNome_destinatario();
	        case inscricao_dest:
	            return nota.getInscricao_destinatario();
	        case produto:
	            return nota.getProduto();
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

	
	public void exportar() {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Notas");
		
		// Definindo alguns padroes de layout
		sheet.setDefaultColumnWidth(15);
		sheet.setDefaultRowHeight((short)400);
		
		int rownum = 0;
		int cellnum = 0;
		Cell cell;
		Row row;

		//Configurando estilos de células (Cores, alinhamento, formatação, etc..)
		HSSFDataFormat numberFormat = workbook.createDataFormat();
	
		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		//headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle textStyle = workbook.createCellStyle();
		//textStyle.setAlignment(Alignment.CENTER);
		textStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle numberStyle = workbook.createCellStyle();
		numberStyle.setDataFormat(numberFormat.getFormat("#,##0.00"));
		numberStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		// Configurando Header
		row = sheet.createRow(rownum++);
		cell = row.createCell(cellnum++);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("NFE");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Serie");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Remetente");
		

		cell = row.createCell(cellnum++);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Inscricao");
		

		cell = row.createCell(cellnum++);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Protocolo");
		

		cell = row.createCell(cellnum++);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Data");
				

		cell = row.createCell(cellnum++);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Natureza");
		

		cell = row.createCell(cellnum++);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Destinatario");
		

		cell = row.createCell(cellnum++);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Inscricao");
		

		cell = row.createCell(cellnum++);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Produto");
		

		cell = row.createCell(cellnum++);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Quantidade");
		
		cell = row.createCell(cellnum++);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Valor");
		
		
		for (CadastroNFe cadastro : notas_fiscais_disponivel) {
			row = sheet.createRow(rownum++);
			cellnum = 0;

			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(cadastro.getNfe());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(cadastro.getSerie());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(numberStyle);
			cell.setCellValue(cadastro.getNome_remetente());
			
			cell = row.createCell(cellnum++);
			cell.setCellStyle(numberStyle);
			cell.setCellValue(cadastro.getInscricao_remetente());
			
			cell = row.createCell(cellnum++);
			cell.setCellStyle(numberStyle);
			cell.setCellValue(cadastro.getProtocolo());
			
			
			cell = row.createCell(cellnum++);
			cell.setCellStyle(numberStyle);
			cell.setCellValue(cadastro.getData().toString());
			
	
			
			
			cell = row.createCell(cellnum++);
			cell.setCellStyle(numberStyle);
			cell.setCellValue(cadastro.getNatureza());
			
			
			
			cell = row.createCell(cellnum++);
			cell.setCellStyle(numberStyle);
			cell.setCellValue(cadastro.getNome_destinatario());
			
			
			cell = row.createCell(cellnum++);
			cell.setCellStyle(numberStyle);
			cell.setCellValue(cadastro.getInscricao_destinatario());
			
			cell = row.createCell(cellnum++);
			cell.setCellStyle(numberStyle);
			cell.setCellValue(cadastro.getProduto());
			
			
			cell = row.createCell(cellnum++);
			cell.setCellStyle(numberStyle);
			cell.setCellValue(cadastro.getQuantidade());
			
			
			cell = row.createCell(cellnum++);
			cell.setCellStyle(numberStyle);
			cell.setCellValue(cadastro.getValor());
			
			}
		
		try {

		   //pega a pasta meus documentos
			String caminho_raiz = javax.swing.filechooser.FileSystemView.getFileSystemView().getDefaultDirectory().toString();
			//criar o diretorio de exportacoes
			ManipularTxt manipular = new ManipularTxt();
			System.out.println("Cminho raiz: " + caminho_raiz);
			manipular.criarDiretorio(caminho_raiz + "\\E-Contract\\Exportacoes"); 
			
			String nome_arquivo = notas_fiscais_disponivel.get(0).getNome_remetente();
			//Escrevendo o arquivo em disco
			FileOutputStream out = new FileOutputStream(new File(caminho_raiz + "\\E-Contract\\Exportacoes"+ "\\" + nome_arquivo + ".xls"));
			workbook.write(out);
			workbook.close();
			out.close();
			//workbook.close();
			JOptionPane.showMessageDialog(null, "Exportação concluida\nArquivo pode ser encontrado na pasta:\n"+ caminho_raiz + "\\E-Contract\\Exportacoes" + nome_arquivo + ".xls");
			System.out.println("Success!!");

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao Exportar!\nConsulte o Administrador");

			e.printStackTrace();
			}

	}
		
	
	
	
	public void setTelaPai(JDialog _telaPai) {
		this.tela_pai = _telaPai;
	}
	
	public void filtrar() {
		 ArrayList<RowFilter<Object,Object>> filters = new ArrayList<RowFilter<Object,Object>>(2);

		    String produto = entProduto.getText().toUpperCase();
		    String destinatario =  entChavePesquisa.getText().toUpperCase();
		    String remetente = entRemetente.getText().toUpperCase();
		    String natureza = entNatureza.getText().toUpperCase();
		    
		    String codigo = entCodigo.getText().toUpperCase();

		    String menor = entMenorData.getText();
		    String maior = entMaiorData.getText();
		    String insc_remetetente = entIdentificacaoRemetente.getText().toUpperCase();
		    String insc_destinatario = entIdentificacaoDestinatario.getText().toUpperCase();

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
		    
		    if(checkString(codigo))
			    filters.add(RowFilter.regexFilter(codigo, 0));
		    
		    
		    if(checkString(insc_remetetente))
			    filters.add(RowFilter.regexFilter(insc_remetetente, 3));
		    
		    if(checkString(insc_destinatario))
			    filters.add(RowFilter.regexFilter(insc_destinatario, 8));
		    
		    sorter.setRowFilter( RowFilter.andFilter(filters));
	}
	
	public boolean checkString(String txt) {
		return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ");
	}
	
	public void setDadosPesquisa(String destinatario, String remetente, String natureza, String produto, String codigo) {
		
		entChavePesquisa.setText(destinatario);
		entRemetente.setText(remetente);
		entNatureza.setText(natureza);
		entProduto.setText(produto);
		entCodigo.setText(codigo);
	
		
		filtrar();
		calcular();
	}
	

	public void desabilitarBtnSelecionar() {
		 btnSelecionarNota.setVisible(false);

		 btnSelecionarNota.setEnabled(false);
	}
	
	public void habilitarBtnSelecionar() {
		btnSelecionarNota.setVisible(true);

		 btnSelecionarNota.setEnabled(true);
	}

	public void setRetornoGlobal(int retorno) {
		retorno_global = retorno;
	}
	
	public void limpar() {
	    sorter.setRowFilter( RowFilter.regexFilter(""));
	    calcular();
	}
	
	public void setClienteSelecionado(CadastroCliente cliente) {
		this.cliente_selecionado = cliente;
	}
	
	public void calcular() {

		int num_total_nfs = 0;
		double quantidade_total = 0;
		BigDecimal valor_total = BigDecimal.ZERO;

		for (int row = 0; row < table_nfs.getRowCount(); row++) {

			int index = table_nfs.convertRowIndexToModel(row);
			CadastroNFe nf = modelo_nfs.getValue(index);
			try {			
		        if(nf.getQuantidade().contains(",")) { 
				quantidade_total += Double.parseDouble(nf.getQuantidade().replaceAll("[^0-9,]", "").replaceAll(",", "."));
		        }else {
					quantidade_total += Double.parseDouble(nf.getQuantidade());

		        }
			}catch(Exception e) {
				
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
		lblValorTotalNfs.setText(valorString);
		
		lblPesoTotalNFs.setText(z.format(quantidade_total) + " Kgs | " + z.format(quantidade_total/60) + " sacos");
	}
}
