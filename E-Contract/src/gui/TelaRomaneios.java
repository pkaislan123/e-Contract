package gui;

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
import cadastros.CadastroProduto;
import cadastros.CadastroRomaneio;
import cadastros.CadastroSafra;
import conexaoBanco.GerenciarBancoContratos;
import manipular.ConfiguracoesGlobais;
import manipular.ManipularNotasFiscais;
import manipular.ManipularRomaneios;
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

public class TelaRomaneios extends JDialog {

	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;

	private JDialog tela_pai;
	private ArrayList<CadastroRomaneio> romaneios_disponivel = new ArrayList<>();

	private JTable table_nfs;
	private TelaRomaneios isto;
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
	private RomaneioTableModel modelo_nfs = new RomaneioTableModel();
	private TableRowSorter<RomaneioTableModel> sorter;
	
	private JTextField entChavePesquisa;
	private JButton btnVizualizarNF;
	private JButton btnExportar;
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
	private JButton btnImportar;

	public TelaRomaneios(int flag,CadastroCliente vendedor) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(TelaRomaneios.class.getResource("/imagens/icone_notas_fiscais.png")));
		//setAlwaysOnTop(true);

		setModal(true);
		cliente_global= vendedor;
		isto = this;
		getDadosGlobais();
		setResizable(true);
		setTitle("E-Contract - Romaneios");

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
		 sorter = new TableRowSorter<RomaneioTableModel>(modelo_nfs);
        
		
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
		panel.setLayout(null);
		JScrollPane scrollPaneNFs = new JScrollPane(table_nfs);
		scrollPaneNFs.setBounds(10, 11, 906, 217);
		panel.add(scrollPaneNFs);

		
		
		
	

		entProduto = new JTextField();
		
		entProduto.setBounds(474, 113, 242, 28);
		painelPrincipal.add(entProduto);
		entProduto.setColumns(10);
		
		entChavePesquisa = new JTextField();
		
		entChavePesquisa.setBounds(130, 115, 268, 28);
		painelPrincipal.add(entChavePesquisa);
		entChavePesquisa.setColumns(10);

		
		
		lblStatusAdicionandoNotas = new JLabel("Lendo Romaneios...");
		lblStatusAdicionandoNotas.setBounds(26, 512, 626, 23);
		painelPrincipal.add(lblStatusAdicionandoNotas);
		
		btnVizualizarNF = new JButton("Vizualizar");
		btnVizualizarNF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int rowSel = table_nfs.getSelectedRow();//pega o indice da linha na tabela
				int indexRowModel = table_nfs.getRowSorter().convertRowIndexToModel(rowSel);//converte pro indice do model
				CadastroRomaneio nota_vizualizar = romaneios_disponivel.get(indexRowModel);
				
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
		lblNewLabel_2.setIcon(new ImageIcon(TelaRomaneios.class.getResource("/imagens/icone_notas_fiscais.png")));
		lblNewLabel_2.setBounds(0, 6, 64, 64);
		painelPrincipal.add(lblNewLabel_2);
		
		lblNewLabel_4 = new JLabel("     Romaneios");
		lblNewLabel_4.setOpaque(true);
		lblNewLabel_4.setForeground(Color.WHITE);
		lblNewLabel_4.setFont(new Font("Arial", Font.PLAIN, 18));
		lblNewLabel_4.setBackground(new Color(0, 51, 0));
		lblNewLabel_4.setBounds(48, 48, 146, 22);
		painelPrincipal.add(lblNewLabel_4);
		
		btnImportar = new JButton("Importar");
		btnImportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			importar();
			}
		});
		btnImportar.setBounds(661, 477, 89, 23);
		painelPrincipal.add(btnImportar);
		

	
		
		
		
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
        	   JOptionPane.showMessageDialog(null, "Erro ao listar romaneios\nCausa: " + f.getCause() + "\nErro: " + f.getMessage());
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

	public void addNota(CadastroRomaneio nota) {

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				modelo_nfs.onAdd(nota);
			/*	modelo_nfs.addRow(new Object[] { nota.getNfe(), nota.getSerie(), nota.getNome_remetente(),
						nota.getInscricao_remetente(), nota.getProtocolo(), nota.getData(), nota.getNatureza(),
						nota.getNome_destinatario(), nota.getInscricao_destinatario(), nota.getProduto(),
						nota.getQuantidade(), nota.getValor() });*/

				lblStatusAdicionandoNotas
						.setText("Aguarde, romaneio estão sendo carregados: Adicionando nota fiscal " + nota.getNumero_romaneio());
				lblStatusAdicionandoNotas.repaint();
				lblStatusAdicionandoNotas.updateUI();

				romaneios_disponivel.add(nota);

			}
		});

	}

	
	
	
	public static class RomaneioTableModel extends AbstractTableModel{
		 
	    //constantes p/identificar colunas
	    private final int numero_romaneio = 0;
	    private final int data = 1 ;
	    private final int produto = 2;
	    private final int safra = 3 ;
	    private final int nome_remetente= 4;
	    private final int nome_destinatario = 5;
	    private final int peso_bruto = 6;
	    private final int tara= 7;
	    private final int peso_liquido =8;
	    private final int umidade=9;
	    private final int impureza=10;
	    private final int ardidos=11;
	    private final int avariados=12;

	 
	    private final String colunas[]={"Número","Data:","Produto:","Safra:","Remetente:","Destinatario:",
	    		"Peso Bruto:", "Tara:", "Peso Liquido:", "Umidade:", "Impureza:", "Ardidos", "Avariados"};
	    private final ArrayList<CadastroRomaneio> dados = new ArrayList<>();//usamos como dados uma lista genérica de nfs
	 
	    public RomaneioTableModel() {
	        
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
	        case numero_romaneio:
	            return int.class;
	        case data:
	            return Date.class;
	        case produto:
	            return String.class;
	        case safra:
	            return String.class;
	        case nome_remetente:
	            return String.class;
	        case nome_destinatario:
	            return String.class;
	        case peso_bruto:
	            return Double.class;
	        case tara:
	            return Double.class;
	        case peso_liquido:
	            return Double.class;
	        case umidade:
	            return Double.class;
	        case impureza:
	            return Double.class;
	        case ardidos:
	            return Double.class;
	        case avariados:
	            return Double.class;
	            
	     
	       
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
	    	CadastroRomaneio romaneio =dados.get(rowIndex);
	 
	        //retorna o valor da coluna
	        switch (columnIndex) {
	        case numero_romaneio:
	            return romaneio.getNumero_romaneio();
	        case data:
	            return romaneio.getData();
	        case produto:{
	            CadastroProduto prod = romaneio.getProduto();
	            return prod.getNome_produto();
	            
	        }
	        case safra:{
               CadastroSafra safra = romaneio.getSafra();
               return safra.getAno_plantio() + "/" + safra.getAno_colheita();
	        	
	        }
	        case nome_remetente:{
	            String nome_cliente = "";
	            CadastroCliente remetente = romaneio.getRemetente();
	            
	            if(remetente.getTipo_pessoa() == 0) {
	            	nome_cliente = remetente.getNome_empresarial();
	            }else
	            	nome_cliente = remetente.getNome_fantaia();
	            
	            
	            return nome_cliente;
	            
	        }
	        case nome_destinatario:{
	        	 String nome_cliente = "";
		            CadastroCliente destinatario = romaneio.getDestinatario();
		            
		            if(destinatario.getTipo_pessoa() == 0) {
		            	nome_cliente = destinatario.getNome_empresarial();
		            }else
		            	nome_cliente = destinatario.getNome_fantaia();
		            
		            
		            return nome_cliente;	    
		            }
	        case peso_bruto:
	            return romaneio.getPeso_bruto();
	        case tara:
	            return romaneio.getTara();
	        case peso_liquido:
	            return romaneio.getPeso_liquido();
	        case umidade:
	            return romaneio.getUmidade();
	        case impureza:
	            return romaneio.getUmidade();
	        case ardidos:
	            return romaneio.getArdidos();
	        case avariados:
	            return romaneio.getAvariados();
	            
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
	        CadastroRomaneio nota=dados.get(rowIndex);
	 
	      
	    }
	 
	    //Métodos abaixo são para manipulação de dados
	 
	    /**
	     * retorna o valor da linha indicada
	     * @param rowIndex
	     * @return
	     */
	    public CadastroRomaneio getValue(int rowIndex){
	        return dados.get(rowIndex);
	    }
	 
	    /**
	     * retorna o indice do objeto
	     * @param empregado
	     * @return
	     */
	    public int indexOf(CadastroRomaneio nota) {
	        return dados.indexOf(nota);
	    }
	 
	    /**
	     * add um empregado á lista
	     * @param empregado
	     */
	    public void onAdd(CadastroRomaneio nota) {
	        dados.add(nota);
	        fireTableRowsInserted(indexOf(nota), indexOf(nota));
	    }
	 
	    /**
	     * add uma lista de empregados
	     * @param dadosIn
	     */
	    public void onAddAll(ArrayList<CadastroRomaneio> dadosIn) {
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
	    public void onRemove(CadastroRomaneio nota) {
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
			ManipularRomaneios manipular = new ManipularRomaneios("");

			try {
				CadastroRomaneio cadastro = manipular.filtrar(arquivo);
			
			//verifica se essa nota ja existe
			boolean ja_existe = false;
			for(CadastroRomaneio romaneio : romaneios_disponivel) {
				if(romaneio.getNumero_romaneio() == cadastro.getNumero_romaneio()) {
					ja_existe = true;
					break;
				}
			}
			
			if(!ja_existe) {
				
				//ie do remetente nf
				String ie_remetente_nf = cadastro.getRemetente().getIe();
				//ie do destinatario nf
				String ie_destinatario_nf = cadastro.getDestinatario().getIe();

				// ie cliente
				String ie_cliente = cliente_global.getIe();

				if(ie_remetente_nf.equals(ie_cliente)) {
					JOptionPane.showMessageDialog(null, "Romaneio pode ser adicionado, o cliente e remetente deste romaeio\n IE do romaneio: " + ie_remetente_nf + " IE do cliente: " + ie_cliente);

				
					
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
							+ "NOTAS FISCAIS" + "\\NFA-" + cadastro.getNumero_romaneio() + ".pdf";
					
					JOptionPane.showMessageDialog(null, "Copiando de :\n" + cadastro.getCaminho_arquivo()+ "\nPara:\n" + caminho_completo_nf);
					boolean copiar = manipular_arq.copiarNFe(cadastro.getCaminho_arquivo(), caminho_completo_nf );
					if(copiar) {
						//adiciona a nota no array local
						romaneios_disponivel.add(cadastro);
						
						//adiciona na tabela
						
						addNota(cadastro);
						
						//informa que adicionou a nota
						JOptionPane.showMessageDialog(null, "Arquivo selecionado:\n" + arquivo.getAbsolutePath() + "\nFoi adicionado");
					}else {
						JOptionPane.showMessageDialog(null, "Arquivo selecionado:\n" + arquivo.getAbsolutePath() + "\nErro ao efetuar a importação\nConsulte o Administrador");

					}
					
	                 
				}else if (ie_destinatario_nf.equals(ie_cliente)) {
					JOptionPane.showMessageDialog(null, "Romaneio pode ser adicionado, o cliente e destinatario deste romaneio\n IE da nota: " + ie_destinatario_nf + " IE do cliente: " + ie_cliente);

					//adiciona a nota no array local
					romaneios_disponivel.add(cadastro);
					
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
					
					String nome_destinatario = "";
		            CadastroCliente destinatario = cadastro.getDestinatario();
		            
		            if(destinatario.getTipo_pessoa() == 0) {
		            	nome_destinatario = destinatario.getNome_empresarial();
		            }else
		            	nome_destinatario = destinatario.getNome_fantaia();
		            
		            String nome_remetente = "";
		            CadastroCliente remetente = cadastro.getRemetente();
		            
		            if(remetente.getTipo_pessoa() == 0) {
		            	nome_remetente = remetente.getNome_empresarial();
		            }else
		            	nome_remetente = remetente.getNome_fantaia();
		            
		            
					
					JOptionPane.showMessageDialog(null, "Arquivo selecionado:\n" + arquivo.getAbsolutePath() + "\nNão é um romaneio para este cliente\nNome do cliente selecionado: " + nome_cliente_selecionado    + " IE do cliente selecionado: " + ie_cliente + "\nNome Destinatario:  " + nome_destinatario + " IE do Destinatario do romaneio:" + ie_destinatario_nf +"\nNome Remetente: " + nome_remetente  + " Inscrição Remetente: " + ie_remetente_nf);

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
		
		
	
	
	public boolean checkString(String txt) {
		return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ");
	}
	
	
	
}
