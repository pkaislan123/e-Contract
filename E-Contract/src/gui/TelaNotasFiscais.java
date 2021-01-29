package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
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

import cadastros.CadastroCliente;
import cadastros.CadastroContrato;
import cadastros.CadastroLogin;
import cadastros.CadastroNFe;
import conexaoBanco.GerenciarBancoContratos;
import manipular.ConfiguracoesGlobais;
import manipular.ManipularNotasFiscais;
import manipular.ManipularTxt;
import outros.DadosGlobais;
import outros.MyFileVisitor;
import tratamento_proprio.Log;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TelaNotasFiscais extends JDialog {

	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;

	private JDialog tela_pai;
	private ArrayList<CadastroNFe> notas_fiscais_disponivel = new ArrayList<>();

	private JTable table_nfs;
	private TelaNotasFiscais isto;
	private JButton btnSelecionarNota;
	
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

	public TelaNotasFiscais(int flag,CadastroCliente vendedor) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(TelaNotasFiscais.class.getResource("/imagens/icone_notas_fiscais.png")));
		//setAlwaysOnTop(true);

		setModal(true);
		cliente_global= vendedor;
		isto = this;
		getDadosGlobais();
		setResizable(true);
		setTitle("E-Contract - Notas Fiscais");

		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 580);
		painelPrincipal.setBackground(Color.WHITE);
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(26, 231, 922, 234);
		painelPrincipal.add(panel);

		table_nfs = new JTable(modelo_nfs);
		 sorter = new TableRowSorter<NFeTableModel>(modelo_nfs);
        
		
		table_nfs.setRowSorter(sorter);

		table_nfs.setBackground(new Color(255, 255, 255));
		
		/*
		modelo_nfs.addColumn("NFe");
		modelo_nfs.addColumn("Serie");
		modelo_nfs.addColumn("Remetente");
		modelo_nfs.addColumn("Inscricao");
		modelo_nfs.addColumn("Protocolo");
		modelo_nfs.addColumn("Data");
		modelo_nfs.addColumn("Natureza");
		modelo_nfs.addColumn("Destinatario");
		modelo_nfs.addColumn("Inscricao");
		modelo_nfs.addColumn("Produto");
		modelo_nfs.addColumn("Quantidade");
		modelo_nfs.addColumn("Valor");
		*/

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
		panel.setLayout(null);
		JScrollPane scrollPaneNFs = new JScrollPane(table_nfs);
		scrollPaneNFs.setBounds(10, 11, 906, 217);
		panel.add(scrollPaneNFs);

		
		
		 btnSelecionarNota = new JButton("Selecionar");
		btnSelecionarNota.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rowSel = table_nfs.getSelectedRow();//pega o indice da linha na tabela
				int indexRowModel = table_nfs.getRowSorter().convertRowIndexToModel(rowSel);//converte pro indice do model
				((TelaConfirmarCarregamento) tela_pai).setNotaFiscal(notas_fiscais_disponivel.get(indexRowModel));
				isto.dispose();

			}
		});
		btnSelecionarNota.setBounds(859, 477, 89, 23);
		painelPrincipal.add(btnSelecionarNota);


		entProduto = new JTextField();
		
		entProduto.setBounds(474, 113, 242, 28);
		painelPrincipal.add(entProduto);
		entProduto.setColumns(10);
		
		entChavePesquisa = new JTextField();
		
		entChavePesquisa.setBounds(130, 115, 268, 28);
		painelPrincipal.add(entChavePesquisa);
		entChavePesquisa.setColumns(10);

		
		
		lblStatusAdicionandoNotas = new JLabel("Adicionando Notas...");
		lblStatusAdicionandoNotas.setBounds(26, 512, 626, 23);
		painelPrincipal.add(lblStatusAdicionandoNotas);
		
		btnVizualizarNF = new JButton("Vizualizar");
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
		btnVizualizarNF.setBounds(760, 477, 89, 23);
		painelPrincipal.add(btnVizualizarNF);
		
		btnExportar = new JButton("Exportar");
		btnExportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exportar();
			}
		});
		btnExportar.setBounds(661, 477, 89, 23);
		painelPrincipal.add(btnExportar);
		
		btnImportarNFe = new JButton("Importar");
		btnImportarNFe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				importar();
				
				
			}
		});
		btnImportarNFe.setBounds(565, 477, 89, 23);
		painelPrincipal.add(btnImportarNFe);
		
		btnFiltrar = new JButton("Filtrar");
		btnFiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			   filtrar();
			}
		});
		btnFiltrar.setBounds(889, 191, 59, 28);
		painelPrincipal.add(btnFiltrar);
		
		lblNewLabel = new JLabel("Destinatario:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(48, 119, 77, 17);
		painelPrincipal.add(lblNewLabel);
		
		lblRemetente = new JLabel("Remetente:");
		lblRemetente.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRemetente.setBounds(53, 152, 72, 17);
		painelPrincipal.add(lblRemetente);
		
		entRemetente = new JTextField();
		entRemetente.setColumns(10);
		entRemetente.setBounds(130, 147, 268, 28);
		painelPrincipal.add(entRemetente);
		
		lblNatureza = new JLabel("Natureza:");
		lblNatureza.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNatureza.setBounds(410, 152, 59, 17);
		painelPrincipal.add(lblNatureza);
		
		entNatureza = new JTextField();
		entNatureza.setColumns(10);
		entNatureza.setBounds(474, 147, 242, 28);
		painelPrincipal.add(entNatureza);
		
		lblProduto = new JLabel("Produto:");
		lblProduto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblProduto.setBounds(414, 118, 55, 17);
		painelPrincipal.add(lblProduto);
		
		lblNewLabel_1 = new JLabel("Periodo");
		lblNewLabel_1.setBounds(833, 92, 43, 16);
		painelPrincipal.add(lblNewLabel_1);
		
		lblD = new JLabel("Dé:");
		lblD.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblD.setBounds(743, 127, 22, 17);
		painelPrincipal.add(lblD);
		
		lblAt = new JLabel("Até:");
		lblAt.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAt.setBounds(740, 155, 25, 17);
		painelPrincipal.add(lblAt);
		
		entMenorData = new JTextField();
		entMenorData.setColumns(10);
		entMenorData.setBounds(779, 114, 169, 28);
		painelPrincipal.add(entMenorData);
		
		entMaiorData = new JTextField();
		entMaiorData.setColumns(10);
		entMaiorData.setBounds(779, 153, 169, 28);
		painelPrincipal.add(entMaiorData);
		
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				    
				    sorter.setRowFilter( RowFilter.regexFilter(""));
			}
		});
		btnLimpar.setBounds(818, 191, 67, 28);
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
		

		if(flag == 1) {
			//esconder o botao selecionar

			 btnSelecionarNota.setVisible(false);

			 btnSelecionarNota.setEnabled(false);
			
		}else if(flag == 0) {
			//esconder o botão vizualizar nf
			//btnVizualizarNF.setVisible(false);
			//btnVizualizarNF.setEnabled(false);
			
		}
		
		
		
		new Thread() {
			@Override
			public void run() {
				pesquisarNotas(vendedor);

			}
		}.start();

		this.setLocationRelativeTo(null);

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

			String caminho_completo_nf = unidade_base_dados + "\\" + sub_pasta + "\\" + nome_pasta.toUpperCase() + "\\"
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
	        case nfe:
	            return nota.getNfe();
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
		
	
	public void importar() {
		

		
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
				
				//ie do remetente nf
				String ie_remetente_nf = cadastro.getInscricao_remetente().replaceAll("[^0-9]+", "");
				//ie do destinatario nf
				String ie_destinatario_nf = cadastro.getInscricao_destinatario().replaceAll("[^0-9]+", "");

				// ie cliente
				String ie_cliente = cliente_global.getIe();

				if(ie_remetente_nf.equals(ie_cliente)) {
					JOptionPane.showMessageDialog(null, "Nota pode ser adicionado, o cliente e remetente desta nota fiscal\n IE da nota: " + ie_remetente_nf + " IE do cliente: " + ie_cliente);

				
					
					//copia o arquivo para a basta de notas fiscais do cliente
					
					String nome_pasta;

					if (cliente_global.getTipo_pessoa() == 0) {
						nome_pasta = cliente_global.getNome_empresarial().toUpperCase();
					} else {
						nome_pasta = cliente_global.getNome_fantaia().toUpperCase();
					}

					String unidade_base_dados = configs_globais.getServidorUnidade();
					String sub_pasta = "E-Contract\\arquivos\\clientes";
					
					ManipularTxt manipular_arq = new ManipularTxt();
					
					String caminho_completo_nf = unidade_base_dados + "\\" + sub_pasta + "\\" + nome_pasta.toUpperCase() + "\\"
							+ "NOTAS FISCAIS" + "\\NFA-" + cadastro.getNfe().trim() + ".pdf";
					
					JOptionPane.showMessageDialog(null, "Copiando de :\n" + cadastro.getCaminho_arquivo()+ "\nPara:\n" + caminho_completo_nf);
					boolean copiar = manipular_arq.copiarNFe(cadastro.getCaminho_arquivo(), caminho_completo_nf );
					if(copiar) {
						//adiciona a nota no array local
						notas_fiscais_disponivel.add(cadastro);
						
						//adiciona na tabela
						
						addNota(cadastro);
						
						//informa que adicionou a nota
						JOptionPane.showMessageDialog(null, "Arquivo selecionado:\n" + arquivo.getAbsolutePath() + "\nFoi adicionado");
					}else {
						JOptionPane.showMessageDialog(null, "Arquivo selecionado:\n" + arquivo.getAbsolutePath() + "\nErro ao efetuar a importação\nConsulte o Administrador");

					}
					
	                 
				}else if (ie_destinatario_nf.equals(ie_cliente)) {
					JOptionPane.showMessageDialog(null, "Nota pode ser adicionado, o cliente e destinatario desta nota fiscal\n IE da nota: " + ie_destinatario_nf + " IE do cliente: " + ie_cliente);

					//adiciona a nota no array local
					notas_fiscais_disponivel.add(cadastro);
					
					//adiciona na tabela
					addNota(cadastro);
					
					//informa que adicionou a nota
					JOptionPane.showMessageDialog(null, "Arquivo selecionado:\n" + arquivo.getAbsolutePath() + "\nFoi adicionado");
	                 
				}else {
					String nome_cliente_selecionado = "";
					if(cliente_global.getTipo_pessoa() == 0)
						nome_cliente_selecionado = cliente_global.getNome_empresarial();
					else
						nome_cliente_selecionado = cliente_global.getNome_fantaia();

					//nome destinatario
					String nome_destinatario = cadastro.getNome_destinatario();
					String nome_remetente = cadastro.getNome_remetente();
					
					JOptionPane.showMessageDialog(null, "Arquivo selecionado:\n" + arquivo.getAbsolutePath() + "\nNão é uma nota fiscal para este cliente\nNome do cliente selecionado: " + nome_cliente_selecionado    + " IE do cliente selecionado: " + ie_cliente + "\nNome Destinatario:  " + nome_destinatario + " IE do Destinatario da nota:" + ie_destinatario_nf +"\nNome Remetente: " + nome_remetente  + " Inscrição Remetente: " + ie_remetente_nf);

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
	}
}
