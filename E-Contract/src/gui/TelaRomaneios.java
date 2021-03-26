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
import cadastros.CadastroCliente.Veiculo;
import cadastros.CadastroContrato;
import cadastros.CadastroLogin;
import cadastros.CadastroNFe;
import cadastros.CadastroProduto;
import cadastros.CadastroRomaneio;
import cadastros.CadastroSafra;
import conexaoBanco.GerenciarBancoClientes;
import conexaoBanco.GerenciarBancoContratos;
import conexaoBanco.GerenciarBancoRomaneios;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
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
	private ArrayList<CadastroRomaneio> lista_romaneios = new ArrayList<>();

	private JTable table_nfs;
	private TelaRomaneios isto;
	private JButton btnSelecionarNota;
	private JDialog telaPai;
    private CadastroCliente cliente_selecionado ;
	private JLabel lblStatusAdicionandoNotas;
	private int contador = 0;
	private JFileChooser fileChooser_global;
	private ArrayList<String> listadeArquivos = new ArrayList<>();

	private final JPanel painelPrincipal = new JPanel();

	private RomaneioTableModel modelo_romaneios = new RomaneioTableModel();
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
	private JButton btnNewButton_1;
	private String servidor_unidade;
	private JButton btnNewButton_2;
	private JButton btnReleitura;
	private JTextField entCodigo;
	private JTextField entIdentificacaoDestinatario;
	private JTextField entIdentificacaoRemetente;

	public TelaRomaneios( int flag_tipo_tela, Window janela_pai) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(TelaRomaneios.class.getResource("/imagens/icone_notas_fiscais.png")));
		//setAlwaysOnTop(true);

		//setModal(true);
		isto = this;
		getDadosGlobais();
		setResizable(true);
		setTitle("E-Contract - Romaneios");

		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1302, 691);
		painelPrincipal.setBackground(Color.WHITE);
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(26, 231, 1250, 340);
		painelPrincipal.add(panel);

		table_nfs = new JTable(modelo_romaneios);
		 sorter = new TableRowSorter<RomaneioTableModel>(modelo_romaneios);
        
		
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
		table_nfs.setRowHeight (30); 

		panel.setLayout(null);
		panel.setLayout(null);
		JScrollPane scrollPaneNFs = new JScrollPane(table_nfs);
		scrollPaneNFs.setBounds(10, 11, 1230, 329);
		panel.add(scrollPaneNFs);

		
		
		
	

		entProduto = new JTextField();
		
		entProduto.setBounds(186, 185, 265, 28);
		painelPrincipal.add(entProduto);
		entProduto.setColumns(10);
		
		entChavePesquisa = new JTextField();
		
		entChavePesquisa.setBounds(184, 146, 268, 28);
		painelPrincipal.add(entChavePesquisa);
		entChavePesquisa.setColumns(10);

		
		
		lblStatusAdicionandoNotas = new JLabel("Lendo Romaneios...");
		lblStatusAdicionandoNotas.setBounds(26, 618, 626, 23);
		painelPrincipal.add(lblStatusAdicionandoNotas);
		
		btnVizualizarNF = new JButton("Vizualizar");
		btnVizualizarNF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int rowSel = table_nfs.getSelectedRow();//pega o indice da linha na tabela
				int indexRowModel = table_nfs.getRowSorter().convertRowIndexToModel(rowSel);//converte pro indice do model
				CadastroRomaneio nota_vizualizar = lista_romaneios.get(indexRowModel);
				
				if (Desktop.isDesktopSupported()) {
					 try {
					     Desktop desktop = Desktop.getDesktop();
					     File myFile = new File(servidor_unidade + nota_vizualizar.getCaminho_arquivo());
					     desktop.open(myFile);
					     } catch (IOException ex) {}
					 }
			}
		});
		btnVizualizarNF.setBounds(1042, 592, 89, 23);
		painelPrincipal.add(btnVizualizarNF);
		

		
		
		lblNewLabel = new JLabel("Destinatario:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(102, 150, 77, 17);
		painelPrincipal.add(lblNewLabel);
		
		lblRemetente = new JLabel("Depositante:");
		lblRemetente.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRemetente.setBounds(102, 111, 78, 17);
		painelPrincipal.add(lblRemetente);
		
		entRemetente = new JTextField();
		entRemetente.setColumns(10);
		entRemetente.setBounds(185, 107, 268, 28);
		painelPrincipal.add(entRemetente);
		
		lblNatureza = new JLabel("Operação:");
		lblNatureza.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNatureza.setBounds(464, 189, 64, 17);
		painelPrincipal.add(lblNatureza);
		
		entNatureza = new JTextField();
		entNatureza.setColumns(10);
		entNatureza.setBounds(538, 185, 242, 28);
		painelPrincipal.add(entNatureza);
		
		lblProduto = new JLabel("Produto:");
		lblProduto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblProduto.setBounds(126, 190, 55, 17);
		painelPrincipal.add(lblProduto);
		
		lblNewLabel_1 = new JLabel("Periodo");
		lblNewLabel_1.setBounds(920, 83, 43, 16);
		painelPrincipal.add(lblNewLabel_1);
		
		lblD = new JLabel("Dé:");
		lblD.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblD.setBounds(830, 118, 22, 17);
		painelPrincipal.add(lblD);
		
		lblAt = new JLabel("Até:");
		lblAt.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAt.setBounds(827, 146, 25, 17);
		painelPrincipal.add(lblAt);
		
		entMenorData = new JTextField();
		entMenorData.setColumns(10);
		entMenorData.setBounds(866, 105, 169, 28);
		painelPrincipal.add(entMenorData);
		
		entMaiorData = new JTextField();
		entMaiorData.setColumns(10);
		entMaiorData.setBounds(866, 144, 169, 28);
		painelPrincipal.add(entMaiorData);
		
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				    limpar();
			}
		});
		btnLimpar.setBounds(1061, 144, 67, 28);
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
			}
		});
		btnImportar.setBounds(943, 592, 89, 23);
		painelPrincipal.add(btnImportar);
		
		JButton btnFiltrar_1 = new JButton("Filtrar");
		btnFiltrar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 filtrar();
			}
		});
		btnFiltrar_1.setBounds(1138, 142, 67, 28);
		painelPrincipal.add(btnFiltrar_1);
		
		JButton btnNewButton = new JButton("Exportar Arquivos");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exportar();
				
				
			}
		});
		btnNewButton.setBounds(791, 592, 139, 23);
		painelPrincipal.add(btnNewButton);
		
		btnNewButton_1 = new JButton("Excluir");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(isto, 
			            "Deseja excluir o Romaneio?", "Excluir Romaneio", 
			            JOptionPane.YES_NO_OPTION,
			            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					int rowSel = table_nfs.getSelectedRow();//pega o indice da linha na tabela
					int indexRowModel = table_nfs.getRowSorter().convertRowIndexToModel(rowSel);//converte pro indice do model
					ManipularTxt manipular = new ManipularTxt();
					boolean apagado = manipular.apagarArquivo(servidor_unidade + lista_romaneios.get(indexRowModel).getCaminho_arquivo());
					if(apagado) {
						
						//remover do banco de dados
						GerenciarBancoRomaneios gerenciar = new GerenciarBancoRomaneios();
						boolean excluir = gerenciar.removerRomaneio(lista_romaneios.get(indexRowModel).getId_romaneio());
						if(excluir) {
							JOptionPane.showMessageDialog(isto, "Romaneio Excluido");
							modelo_romaneios.onRemove(lista_romaneios.get(indexRowModel));

						}else {
							JOptionPane.showMessageDialog(isto, "Erro ao excluir este Romaneio\nConsulte o administrador");

						}

					}else {
						JOptionPane.showMessageDialog(isto, "Erro ao excluir este Romaneio\nConsulte o administrador");
					}
			        }
			}
		});
		btnNewButton_1.setBounds(691, 592, 89, 23);
		painelPrincipal.add(btnNewButton_1);
		
		btnNewButton_2 = new JButton("Excluir Todos os Romaneios");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(isto, 
			            "Deseja excluir todos os Romaneios?", "Excluir Romaneios", 
			            JOptionPane.YES_NO_OPTION,
			            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){

                   try {
                	   for(CadastroRomaneio rom : lista_romaneios) {
					ManipularTxt manipular = new ManipularTxt();
					boolean apagado = manipular.apagarArquivo(rom.getCaminho_arquivo());
					if(apagado) {
						modelo_romaneios.onRemove(rom);

					}else {
						JOptionPane.showMessageDialog(isto, "Erro ao excluir este Romaneio\nConsulte o administrador");
					}
			        }
						JOptionPane.showMessageDialog(isto, "Romaneios Excluidos");

				}
				catch(Exception i) {
					JOptionPane.showMessageDialog(isto, "Erro ao excluir este Romaneio\nConsulte o administrador");

				}
			}
			}
		});
		btnNewButton_2.setBounds(36, 582, 188, 23);
		painelPrincipal.add(btnNewButton_2);
		
		JButton btnSelecionar = new JButton("Selecionar");
		btnSelecionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rowSel = table_nfs.getSelectedRow();//pega o indice da linha na tabela
				int indexRowModel = table_nfs.getRowSorter().convertRowIndexToModel(rowSel);//converte pro indice do model
				if(telaPai instanceof TelaConfirmarRecebimento) {
					((TelaConfirmarRecebimento) telaPai).setRomaneio(lista_romaneios.get(indexRowModel));
				}else if(telaPai instanceof TelaConfirmarCarregamento) {
					((TelaConfirmarCarregamento) telaPai).setRomaneio(lista_romaneios.get(indexRowModel));

				}
				isto.dispose();

			}
		});
		btnSelecionar.setBounds(1141, 592, 89, 23);
		painelPrincipal.add(btnSelecionar);
		
		btnReleitura = new JButton("Refazer Pesquisar");
		btnReleitura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisarTodosOsRomaneios();
			}
		});
		btnReleitura.setBounds(1061, 107, 144, 23);
		painelPrincipal.add(btnReleitura);
		
		JLabel lblCdigo = new JLabel("Código:");
		lblCdigo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCdigo.setBounds(804, 189, 48, 17);
		painelPrincipal.add(lblCdigo);
		
		entCodigo = new JTextField();
		entCodigo.setColumns(10);
		entCodigo.setBounds(866, 183, 169, 28);
		painelPrincipal.add(entCodigo);
		
		JLabel lblCpfcnpj = new JLabel("CPF/CNPJ:");
		lblCpfcnpj.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCpfcnpj.setBounds(462, 150, 77, 17);
		painelPrincipal.add(lblCpfcnpj);
		
		entIdentificacaoDestinatario = new JTextField();
		entIdentificacaoDestinatario.setColumns(10);
		entIdentificacaoDestinatario.setBounds(539, 146, 241, 28);
		painelPrincipal.add(entIdentificacaoDestinatario);
		
		JLabel lblCpfcnpj_1 = new JLabel("CPF/CNPJ:");
		lblCpfcnpj_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCpfcnpj_1.setBounds(463, 112, 77, 17);
		painelPrincipal.add(lblCpfcnpj_1);
		
		entIdentificacaoRemetente = new JTextField();
		entIdentificacaoRemetente.setColumns(10);
		entIdentificacaoRemetente.setBounds(540, 107, 241, 28);
		painelPrincipal.add(entIdentificacaoRemetente);
		

	
		if(flag_tipo_tela == 1) {
			btnSelecionar.setVisible(false);
			btnSelecionar.setEnabled(false);
		}
		
	
		this.setLocationRelativeTo(janela_pai);

	}

	public void exportar() {

		ManipularTxt manipular = new ManipularTxt();

		if (table_nfs.getSelectedRows().length > 0) {
			if (table_nfs.getSelectedRows().length == 1) {

				try {
					JOptionPane.showMessageDialog(isto,
							"Na próxima tela, selecione o local e escreva o nome do arquivo sem extensão");
					File pasta_salvar = getDiretorioSalvar();
					int rowSel = table_nfs.getSelectedRows()[0];
					int indexRowModel = table_nfs.getRowSorter().convertRowIndexToModel(rowSel);// converte pro indice
																								// do model
					CadastroRomaneio rom = lista_romaneios.get(indexRowModel);

					try {
						boolean copiar = manipular.copiarNFe(rom.getCaminho_arquivo(),
								pasta_salvar.getAbsolutePath() + ".pdf");
						if (copiar) {
						} else {
							JOptionPane.showMessageDialog(isto, "Erro ao exportar notas fiscais");
						}

					} catch (IOException e) {
						JOptionPane.showMessageDialog(isto, "Erro ao exportar notas fiscais");
						e.printStackTrace();
					}

				} catch (Exception e) {
					JOptionPane.showMessageDialog(isto, "Erro ao exportar notas fiscais");

				}
				JOptionPane.showMessageDialog(null, "Sucesso ao exportar NF");

			} else {

				try {
					JOptionPane.showMessageDialog(isto,
							"Na próxima tela, selecione o local e escreva o nome da pasta para salvar os arquivos");

					File pasta_salvar = getDiretorioSalvar();

					int indices[] = table_nfs.getSelectedRows();

					if (!pasta_salvar.exists())
						manipular.criarDiretorio(pasta_salvar.getAbsolutePath());

					for (int i = 0; i < indices.length; i++) {
						int rowSel = indices[i];
						int indexRowModel = table_nfs.getRowSorter().convertRowIndexToModel(rowSel);// converte pro
																									// indice do model
						CadastroRomaneio rom = lista_romaneios.get(indexRowModel);

						try {
							boolean copiar = manipular.copiarNFe(rom.getCaminho_arquivo(),
									pasta_salvar.getAbsolutePath() + "\\romaneio" + rom.getNumero_romaneio() + ".pdf");
							if (copiar) {

							} else {
							}

						} catch (IOException e) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(isto, "Erro ao exportar notas fiscais");

							e.printStackTrace();
						}

					}
					JOptionPane.showMessageDialog(isto, "Sucesso ao exportar notas fiscais");

				} catch (Exception t) {
					JOptionPane.showMessageDialog(isto, "Erro ao exportar notas fiscais");

				}

			}
		} else {
			JOptionPane.showMessageDialog(isto, "Nenhuma linha selecionada");
		}

	}

	public File getDiretorioSalvar() {
		// Mostra a dialog de save file
		JFileChooser fileChooser = new JFileChooser();

		fileChooser.setMultiSelectionEnabled(true);
		fileChooser.setPreferredSize(new Dimension(800, 600));

		fileChooser.showSaveDialog(isto);

		File pasta_selecionada = fileChooser.getSelectedFile();

		JOptionPane.showMessageDialog(null, "Pasta para salvar: " + pasta_selecionada.getAbsolutePath());
		return pasta_selecionada;

	}

	

	

	public void getDadosGlobais() {
		// gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		GerenciadorLog = dados.getGerenciadorLog();
		configs_globais = dados.getConfigs_globais();

		 servidor_unidade = configs_globais.getServidorUnidade();
		// usuario logado
		login = dados.getLogin();

	}

	public void setPai(JDialog _pai) {
		this.tela_pai = _pai;
	}

	public void pesquisarTodosOsRomaneios() {
		new Thread() {
			@Override
			public void run() {
		GerenciarBancoRomaneios gerenciar = new GerenciarBancoRomaneios();
		lista_romaneios.clear();
		modelo_romaneios.onRemoveAll();
	    
		ArrayList<CadastroRomaneio> romaneios;
		
		if(cliente_selecionado == null) {
			romaneios =  gerenciar.listarRomaneiosMaisRapido();
		}else {
			 romaneios =  gerenciar.listarRomaneiosPorCliente(cliente_selecionado.getId());

		}
		for (CadastroRomaneio rom : romaneios) {
			 modelo_romaneios.onAdd(rom);
			 lista_romaneios.add(rom);
		}
		
			}
		}.start();
	}
	
	
	public static class RomaneioTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int numero_romaneio = 0;
		private final int operacao = 1;

		private final int data = 2;
		private final int produto = 3;
		private final int transgenia = 4;

		private final int safra = 5;
		private final int nome_remetente = 6;
		private final int id_remetente = 7;
		private final int nome_destinatario = 8;
		private final int id_destinatario = 9;
		private final int peso_bruto = 10;
		private final int tara = 11;
		private final int peso_liquido = 12;
		private final int umidade = 13;
		private final int impureza = 14;
		private final int ardidos = 15;
		private final int avariados = 16;
		private final int cfop = 17;
		private final int descricao = 18;

		private final int motorista = 19;
		private final int placa = 20;

		private final String colunas[] = { "Número", "Operação", "Data:", "Produto:", "Transgenia:", "Safra:",
				"Depositante:", "CPF/CNPJ Depositante", "Rementente/Destinatario", "CPF/CNPJ Rementente/Destinatario", "Peso Bruto:", "Tara:", "Peso Liquido:", "Umidade:", "Impureza:",
				"Ardidos", "Avariados", "CFOP", "Descrição", "Motorista", "Placa" };
		private final ArrayList<CadastroRomaneio> dados = new ArrayList<>();// usamos como dados uma lista genérica de
																			// nfs

		public RomaneioTableModel() {

		}

		@Override
		public int getColumnCount() {
			// retorna o total de colunas
			return colunas.length;
		}

		@Override
		public int getRowCount() {
			// retorna o total de linhas na tabela
			return dados.size();
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			// retorna o tipo de dado, para cada coluna
			switch (columnIndex) {
			case numero_romaneio:
				return int.class;
			case operacao:
				return String.class;
			case data:
				return Date.class;
			case produto:
				return String.class;
			case transgenia:
				return String.class;
			case safra:
				return String.class;
			case nome_remetente:
				return String.class;
			case id_remetente:
				return String.class;
			case nome_destinatario:
				return String.class;
			case id_destinatario:
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
			case cfop:
				return String.class;
			case descricao:
				return String.class;
			case motorista:
				return String.class;
			case placa:
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
			// retorna o valor conforme a coluna e linha

			// pega o dados corrente da linha
			CadastroRomaneio romaneio = dados.get(rowIndex);
			CadastroCliente remetente = romaneio.getRemetente();
			CadastroCliente destinatario = romaneio.getDestinatario();

			// retorna o valor da coluna
			switch (columnIndex) {
			case numero_romaneio:
				return romaneio.getNumero_romaneio();
			case operacao:
				return romaneio.getOperacao().toUpperCase();
			case data:
				return romaneio.getData();
			case produto: {
				try {
				CadastroProduto prod = romaneio.getProduto();
				return prod.getNome_produto();
				}catch(Exception h) {
				//	JOptionPane.showMessageDialog(null, "O romaneio codigo: " + romaneio.getNumero_romaneio() + " possui erro no produto");
				}
			}
			case transgenia:
				return romaneio.getProduto().getTransgenia().toUpperCase();
			case safra: {
				CadastroSafra safra = romaneio.getSafra();
				return safra.getAno_plantio() + "/" + safra.getAno_colheita();

			}
			case nome_remetente: {
				try {
				String nome_cliente = "";
              
				if(remetente != null) {
				if (remetente.getTipo_pessoa() == 0) {
					nome_cliente = remetente.getNome_empresarial().toUpperCase();
				} else
					nome_cliente = remetente.getNome_fantaia().toUpperCase();
				}
				return nome_cliente;
				}catch(Exception e) {
					return "";
				}
			}
			case id_remetente:{
				try {
				if(remetente.getTipo_pessoa() == 0)
					return remetente.getCpf();
				else
					return remetente.getCnpj();
				}catch(Exception e) {
					return "";
				}
			}
			case nome_destinatario: {
				try {
				String nome_cliente = "";

				if(destinatario != null) {
				if (destinatario.getTipo_pessoa() == 0) {
					nome_cliente = destinatario.getNome_empresarial().toUpperCase();
				} else
					nome_cliente = destinatario.getNome_fantaia().toUpperCase();
				}
				return nome_cliente;
				}catch (Exception e) {
					return "";
				}
			}
			case id_destinatario:{
				try {
				if(destinatario.getTipo_pessoa() == 0)
					return destinatario.getCpf();
				else
					return destinatario.getCnpj();
				}catch(Exception e) {
					return "";
				}
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
				return romaneio.getInpureza();
			case ardidos:
				return romaneio.getArdidos();
			case avariados:
				return romaneio.getAvariados();
			case cfop:{
				try {
				if( romaneio.getCfop() != null) {
					return romaneio.getCfop();

				}else {
						return "";
				}
			}catch(Exception e) {
				return "";

			}
			}
			case descricao:{
				try {
				if(romaneio.getDescricao_cfop().toUpperCase() != null) {
					return romaneio.getDescricao_cfop().toUpperCase();
				}else {
					return "";

				}
				}catch(Exception e) {
					return "";

				}
			}
			case motorista:{
				try {
				if(romaneio.getMotorista() != null) {
					return romaneio.getMotorista().getNome_empresarial().toUpperCase();

				}else 
					return "";
				}catch(Exception e) {
					return "";
				}
			}
			case placa: {
				try {
				if(romaneio.getMotorista() != null) {
				ArrayList<CadastroCliente.Veiculo> veiculos = romaneio.getMotorista().getVeiculos();
				return veiculos.get(0).getPlaca_trator().toUpperCase();
				}else 
					return "";
				}catch(Exception y) {
					return "";
				}
			}

			default:
				throw new IndexOutOfBoundsException("Coluna Inválida!!!");
			}
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			// metodo identifica qual coluna é editavel

			// só iremos editar a coluna BENEFICIO,
			// que será um checkbox por ser boolean

			return false;
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			CadastroRomaneio nota = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public CadastroRomaneio getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(CadastroRomaneio nota) {
			return dados.indexOf(nota);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(CadastroRomaneio nota) {
			dados.add(nota);
			fireTableRowsInserted(indexOf(nota), indexOf(nota));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<CadastroRomaneio> dadosIn) {
			dados.addAll(dadosIn);
			fireTableDataChanged();
		}

		/**
		 * remove um registro da lista, através do indice
		 * 
		 * @param rowIndex
		 */
		public void onRemove(int rowIndex) {
			dados.remove(rowIndex);
			fireTableRowsDeleted(rowIndex, rowIndex);
		}

		/**
		 * remove um registro da lista, através do objeto
		 * 
		 * @param empregado
		 */
		public void onRemove(CadastroRomaneio nota) {
			int indexBefore = indexOf(nota);// pega o indice antes de apagar
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


	public boolean checkString(String txt) {
		return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ");
	}
	
	
public void setDadosPesquisa(String destinatario, String remetente, String natureza, String produto, String codigo) {
		
    sorter.setRowFilter( RowFilter.regexFilter(""));

		entChavePesquisa.setText(destinatario);
		entRemetente.setText(remetente);
		entNatureza.setText(natureza);
		entProduto.setText(produto);
		entCodigo.setText(codigo);
	
		
		filtrar();
	}


public void filtrar() {
	 ArrayList<RowFilter<Object,Object>> filters = new ArrayList<RowFilter<Object,Object>>(2);

	    String produto = entProduto.getText().toUpperCase();
	    String destinatario =  entChavePesquisa.getText().toUpperCase();
	    String remetente = entRemetente.getText().toUpperCase();
	    String natureza = entNatureza.getText().toUpperCase();
	    String codigo = entCodigo.getText().toUpperCase();
	    String identificacaoRemetente = entIdentificacaoRemetente.getText().toUpperCase();
	    String identificacaoDestinatario = entIdentificacaoDestinatario.getText().toUpperCase();

	    String menor = entMenorData.getText();
	    String maior = entMaiorData.getText();
	    
	    if(checkString(menor) && checkString(maior) ) {
		Date data_menor = null;
		Date data_maior = null ;
		try {
			data_menor = new SimpleDateFormat("dd/MM/yyyy").parse(menor);
			data_maior = new SimpleDateFormat("dd/MM/yyyy").parse(maior);

		} catch (ParseException i) {
			// TODO Auto-generated catch block
			i.printStackTrace();
		}
		
		Set<RowFilter<Object, Object>> datas = new HashSet<>();
		datas.add(RowFilter.dateFilter(RowFilter.ComparisonType.AFTER,
				data_menor, 2));
		datas.add(RowFilter.dateFilter(RowFilter.ComparisonType.EQUAL,
				data_menor, 2));
		filters.add(RowFilter.orFilter(datas));
     
	  //  filters.add( RowFilter.dateFilter(ComparisonType.AFTER, data_menor, 5) );
	   // filters.add( RowFilter.dateFilter(ComparisonType.EQUAL, data_menor, 5) );

	   // filters.add( RowFilter.dateFilter(ComparisonType.BEFORE, data_maior, 5) );
	   // filters.add( RowFilter.dateFilter(ComparisonType.EQUAL, data_maior, 5) );
		Set<RowFilter<Object, Object>> datas_maior = new HashSet<>();
		datas_maior.add(RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE,
				data_maior, 2));
		datas_maior.add(RowFilter.dateFilter(RowFilter.ComparisonType.EQUAL,
				data_maior, 2));
		filters.add(RowFilter.orFilter(datas_maior));
	    }
	    if(checkString(remetente))
	    filters.add(RowFilter.regexFilter(remetente, 6));
	    
	    if(checkString(natureza))
	    filters.add(RowFilter.regexFilter(natureza, 1));

	    if(checkString(destinatario))
	    filters.add(RowFilter.regexFilter(destinatario, 8));
	    
	    if(checkString(produto))
	    filters.add(RowFilter.regexFilter(produto, 3));
	    
	    
	    if(checkString(codigo))
	    filters.add(RowFilter.regexFilter(codigo, 0));
	    
	    if(checkString(identificacaoRemetente))
		    filters.add(RowFilter.regexFilter(identificacaoRemetente, 7));
		    
	    
	    if(checkString(identificacaoDestinatario))
		    filters.add(RowFilter.regexFilter(identificacaoDestinatario, 9));
		    
	 
	    
	    sorter.setRowFilter( RowFilter.andFilter(filters));
}


public void limpar() {
    sorter.setRowFilter( RowFilter.regexFilter(""));

}

public void setClienteSelecionado(CadastroCliente cliente) {
	this.cliente_selecionado = cliente;
}


public void setTelaPai(JDialog _telaPai) {
	this.telaPai = _telaPai;
}
}