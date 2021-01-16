package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

import cadastros.CadastroNFe;
import outros.MyFileVisitor;
import outros.TratarDados;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;




public class TelaPlanilhaNFeInternas extends JDialog {

	private final JPanel painelPrincipal = new JPanel();
	private JTextField entCaminhoPasta;
	   private int contador = 0;
      private JFileChooser fileChooserGlobal;
  	  private ArrayList<String> listadeArquivos = new ArrayList<>();

  	  private ArrayList<CadastroNFe> notas_fiscais = new ArrayList<>();
  	
  	private DefaultTableModel modelo = new DefaultTableModel();
  	private JTable table;
  	private int countArquivos;
  	private int countPDF;
  	private int countNotas;
	
	public TelaPlanilhaNFeInternas() {
		setModal(true);

		TelaPlanilhaNFeInternas isto = this;
		
		setResizable(false);
		setTitle("E-Contract - Tela Padrao");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1310, 571);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);
		
		JLabel lblCaminho = new JLabel("Caminho:");
		lblCaminho.setBounds(23, 35, 56, 21);
		painelPrincipal.add(lblCaminho);
		
		entCaminhoPasta = new JTextField();
		entCaminhoPasta.setBounds(89, 35, 172, 20);
		painelPrincipal.add(entCaminhoPasta);
		entCaminhoPasta.setColumns(10);
		
		JButton btnEscolherPasta = new JButton("Selecionar");
		btnEscolherPasta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//JFileChooser arquivo = new JFileChooser(); 
				
				
					JFileChooser fileChooser = new JFileChooser();
					if(contador == 0)
					{
						//fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
						fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	                    fileChooserGlobal = fileChooser;
	                    contador++;
					}
					else
					{
						fileChooser = fileChooserGlobal;
						
					}
					int result = fileChooser.showOpenDialog(isto);
					String caminho = fileChooser.getSelectedFile().toString();

					MyFileVisitor arquivos =  new MyFileVisitor();
					Path source = Paths.get(caminho);
				        try {
				            Files.walkFileTree(source,arquivos);
				            listadeArquivos = arquivos.getArquivos();
				            
				            
				        } catch (IOException ex) {
				            ex.printStackTrace();
				        }
				   for(int i = 0; i < listadeArquivos.size(); i++)
				   {
				    System.out.println(listadeArquivos.get(i).toString());    
				    countArquivos++;
				   }
				   
				 for(int i = 0; i < listadeArquivos.size(); i++)
				 {
	 				if(listadeArquivos.get(i).endsWith(".pdf") || listadeArquivos.get(i).endsWith(".Pdf") )
		       {
	 			   countPDF++;	 	
			       CadastroNFe cadastro = new CadastroNFe();
		
				   File file = new File(listadeArquivos.get(i).toString());
                   
			        try (PDDocument document = PDDocument.load( file )) {

			            if (!document.isEncrypted()) {
						
			                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
			                stripper.setSortByPosition(true);

			                PDFTextStripper tStripper = new PDFTextStripper();

			                String pdfFileInText = tStripper.getText(document);
			              
			                 String lines[] = pdfFileInText.split("\r\n");
			               // String linhas = Arrays.toString(lines);
			                String  demais = lines[118];
			               // System.out.println("Demais: " + demais);
			                String separados[] = demais.split(" ");
			              /*
			                for (String line : separados) {
				                   System.out.println(line);
				                }  
			                */
			              /*  
			              for (String line : lines) {
			                   System.out.println(line);
			                }
			                */
			              
			              String tratar = Arrays.toString(lines);
			             // tratar = tratar.replaceAll("\n", "*");
			              System.out.println(tratar);
			                TratarDados tratamentoDados  = new TratarDados(tratar );

			                String nfe = tratamentoDados.tratar("Nº",",");
			                //String nfe = null;
			                String natureza = tratamentoDados.tratar("NATUREZA DA OPERAÇÃO, ", ",");
			              /*
			                if(procura_nfe.equals("") || procura_nfe == null)
			                {
			                	procura_nfe = tratamentoDados.tratar("NFA-e","REMESSA");
			                	  if(procura_nfe.equals("") || procura_nfe == null)
					                {
			                		  //procurar por outro tipo de nota
					                	procura_nfe = tratamentoDados.tratar("NFA-e","OUTRAS SAÍDAS");
					                	 if(procura_nfe.equals("") || procura_nfe == null)
							                {
			                		  System.out.println("Número NFA não encontrado");
							                }
					                	 else
					                	 {
					                		 nfe = procura_nfe;
					                		 
					                		 
					                		 
					                		  natureza = "OUTRAS SAÍDAS";
					                		 
					                	 }
			                		  
					                }
			                	  else
			                	  {
			                		  nfe = procura_nfe;
			                		  natureza = "REMESSA";

			                	  }
			                }
			                else
			                {
			                	nfe = procura_nfe;
			                	natureza = "VENDA";
			                }
			                nfe = nfe.replaceAll(",", "");
			                		*/
			               // String nfe = lines[27].toString().replaceAll("[^0-9]+", "");
			                
			                
			               
			               
			                
			                String serie = tratamentoDados.tratar("DANFE,", ",");
			                //serie = serie.replaceAll("[^0-9]+", "");
			                //String serie = lines[90].toString().replaceAll("[^0-9]+", "");
				               
			               // String nome_remetente = lines[29].toString().replaceAll("[0-9]+", "");
			                // nome_remetente = nome_remetente.replaceAll("[^a-zA-Z ]", "");
			               // int linha_natureza = tratar.indexOf(tratar);
			               
			               /* String nome_remetente = null;
			                if(natureza.equals("VENDA"))
			                {
	                            nome_remetente = tratamentoDados.tratar("VENDA,", ",");
	                            nome_remetente = nome_remetente.replaceAll("[^a-zA-Z ]", "");

			                }
			                if(natureza.equals("REMESSA"))
			                {
	                            nome_remetente = tratamentoDados.tratar("REMESSA,", ",");
	                            nome_remetente = nome_remetente.replaceAll("[^a-zA-Z ]", "");

			                }
			                if(natureza.equals("OUTRAS SAÍDAS"))
			                {
	                            nome_remetente = tratamentoDados.tratar("OUTRAS SAÍDAS,", ",");
	                            nome_remetente = nome_remetente.replaceAll("[^a-zA-Z ]", "");

			                }
			                
                            
                            */
			               
			                
			               String nome_remetente = tratamentoDados.tratar("SERIE ,", ",");
			              
			               /* 
			                String inscricao_remetente  = lines[31].toString().replaceAll("[^0-9]+", "");
			                inscricao_remetente =  inscricao_remetente.substring(4, inscricao_remetente.length());
			                System.out.println("Inscricao: " + inscricao_remetente);
                             */
			                
			                //String procura_inscricao_remetente = tratamentoDados.tratar("BRASIL", ",");
			              
			               
			            
			                //String array_protocolo[] = lines[95].split(" ");
			                String procura_protocolo = tratamentoDados.tratar("CNPJ/CPF,", "DATA DE RECEBIMENTO");
			                String array_protocolo[] = procura_protocolo.split(" ");
			               // System.out.println("Possivel protocolo: "+array_protocolo[0] );

			              //  System.out.println("Possivel protocolo: "+array_protocolo[1] );
			               // System.out.println("Possivel protocolo: "+array_protocolo[2] );
			                //System.out.println("Possivel protocolo: "+array_protocolo[3] );
			                //System.out.println("Possivel protocolo: "+array_protocolo[4] );
			                //System.out.println("Possivel protocolo: "+array_protocolo[5] );
			                //System.out.println("Possivel protocolo: "+array_protocolo[6] );
			                //System.out.println("Possivel protocolo: "+array_protocolo[7] );



			                String protocolo = array_protocolo[2];
			                String inscricao_remetente = array_protocolo[7] ;
			                inscricao_remetente = inscricao_remetente.replaceAll(",", "");
			               
			                
			                String nome_destinatario = tratamentoDados.tratar("Destinatário", "Valor");
			             
			                //String data = lines[33].toString();
			               
			                String data = tratamentoDados.tratar("Emissão", "Dest");
			                data = data.replaceAll(" ", "");
			               
			                

			                String procura = nome_destinatario + data + ",";
			                //System.out.println("String de procura:" + procura);
			                String procura_inscriao_destinatario = tratamentoDados.tratar(procura, ",");
			              //  System.out.println("Procura inscricao: " + procura_inscriao_destinatario);
			               String inscricao_destinatario = procura_inscriao_destinatario;
			             //  String natureza = lines[28].toString();
			            
			                String valor = tratamentoDados.tratar("Total,", ", NÚMERO");
			                valor = valor.replace(".", "");
			               
			                boolean procura_produto = tratar.contains("SOJA");
			                String produto = null;
			                if(procura_produto)
			                {
			                	produto = "SOJA";
			                }
			                else
			                {
			                	procura_produto = tratar.contains("SORGO");
			                	if(procura_produto) {
			                	produto = "SORGO";
			                	}
			                	else
			                	{
			                		procura_produto = tratar.contains("MILHO");
				                	if(procura_produto) {
				                	produto = "MILHO";
				                	}
				                	else
				                	{
				                		System.out.println("Nenhum Produto Escontrado");
				                	}
			                		
			                	}
			                	
			                }
			                
			                boolean procura_unidade = tratar.contains("KG");
			                String unidade = null;
			                if(procura_unidade)
			                {
			                	unidade = "KG";
			                }
			                else
			                {
			                	procura_unidade = tratar.contains("SC");
			                	if(procura_unidade) {
			                		unidade = "SC";
			                	}
			                	else
			                	{
			                		procura_unidade = tratar.contains("TON");
				                	if(procura_unidade) {
				                		unidade = "TON";
				                	}
				                	else
				                	{
				                		System.out.println("Nenhum Produto Escontrado");
				                	}
			                		
			                	}
			                	
			                }
			                
			                String quantidade = tratamentoDados.tratar("ISS, ", " ");
			                quantidade = quantidade.replace(".", "");
			                
			                System.out.println("Numero nfe: " + nfe);

			                System.out.println("Serie: " + serie );
			                System.out.println("Remetente: " + nome_remetente);
			                System.out.println("Inscrição do remetente: " + inscricao_remetente);
			                
			                System.out.println("Protocolo: " + protocolo);
			                System.out.println("Data: " + data );
			                System.out.println("Natureza: " + natureza);

			                System.out.println("Nome do Destinatario: " + nome_destinatario);
			                
			               System.out.println("Inscricao Destinatario: "+ inscricao_destinatario);
			            
			               System.out.println("Produto: "+ produto);
			               System.out.println("Unidade: "+ unidade);
			               System.out.println("Quantidade: " + quantidade);
			               System.out.println("Valor: " + valor);
			               
			    			modelo.addRow(new Object[]{nfe, serie, nome_remetente, inscricao_remetente, protocolo, data, natureza,
								       nome_destinatario, inscricao_destinatario, produto, quantidade, valor});
			               
			    			cadastro.setNfe(nfe);
			    			cadastro.setSerie(serie);
			    			cadastro.setNome_remetente(nome_remetente);
			    			cadastro.setInscricao_remetente(inscricao_remetente);
			    			cadastro.setProtocolo(protocolo);
			    			try {
								
								
								Date date = new SimpleDateFormat("dd/MM/yyyy").parse(data);
								
							cadastro.setData(date);
							}catch(Exception t) {
								JOptionPane.showMessageDialog(null, "Erro ao listar NFA-e\nErro:  " + t.getMessage() + "\nConsulte o Administrador");
								
							}
			    			cadastro.setNatureza(natureza);
			    			cadastro.setNome_destinatario(nome_destinatario);
			    			cadastro.setInscricao_destinatario(inscricao_destinatario);
			    			cadastro.setProduto(produto);
			    			cadastro.setQuantidade(quantidade);
			    			cadastro.setValor(valor);
			    			
			    			notas_fiscais.add(cadastro);
			    			countNotas++;
			    			
			            }
			            
			        }catch(Exception e1)
			        {}
			        System.out.print("Numero de arquivos: "+countArquivos);
			        System.out.print("Numero de PDF's: "+countPDF);

			        System.out.print("Numero de Notas: "+countNotas);

				 }
	 				else
	 				{
	 					String nomeArquivo = listadeArquivos.get(i).toString();
	 					System.out.println("O arquivo " + nomeArquivo + " não é um PDF");
	 				}
				 }
				   
				   }
		});
		btnEscolherPasta.setBounds(269, 34, 89, 23);
		painelPrincipal.add(btnEscolherPasta);
		
 
		 
		 JPanel panel = new JPanel();
		 panel.setBounds(10, 86, 1262, 371);
		 painelPrincipal.add(panel);
		 

			table = new JTable(modelo);
			table.setBackground(new Color(255, 255, 255));
			modelo.addColumn("NFe");
	        modelo.addColumn("Serie");
	        modelo.addColumn("Remetente");
	        modelo.addColumn("Inscricao");
	        modelo.addColumn("Protocolo");
	        modelo.addColumn("Data");
	        modelo.addColumn("Natureza");
	        modelo.addColumn("Destinatario");
	        modelo.addColumn("Inscricao");
	        modelo.addColumn("Produto");
	        modelo.addColumn("Quantidade");
	        modelo.addColumn("Valor");

	        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	      
	       
	        table.getColumnModel().getColumn(0)
	        .setPreferredWidth(80);
	        table.getColumnModel().getColumn(1)
	        .setPreferredWidth(50);
	        table.getColumnModel().getColumn(2)
	        .setPreferredWidth(250);
	        table.getColumnModel().getColumn(3)
	        .setPreferredWidth(120);
	        table.getColumnModel().getColumn(4)
	        .setPreferredWidth(120);
	        table.getColumnModel().getColumn(5)
	        .setPreferredWidth(70);
	        table.getColumnModel().getColumn(6)
	        .setPreferredWidth(120);
	        table.getColumnModel().getColumn(7)
	        .setPreferredWidth(250);
	        table.getColumnModel().getColumn(8)
	        .setPreferredWidth(120);
	        table.getColumnModel().getColumn(9)
	        .setPreferredWidth(100);
	        table.getColumnModel().getColumn(10)
	        .setPreferredWidth(120);
	        table.getColumnModel().getColumn(11)
	        .setPreferredWidth(120);
	    
	        panel.setLayout(null);
	    	modelo.setNumRows(0);
	        JScrollPane scrollPane = new JScrollPane(table);
	        scrollPane.setBounds(10, 5, 1242, 348);
	        scrollPane.setAutoscrolls(true);
	        scrollPane.setBackground(new Color(255, 255, 255));
			panel.add(scrollPane);
			
			JButton btnExportar = new JButton("Exportar");
			btnExportar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					/*HSSFWorkbook workbook = new HSSFWorkbook();
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
					headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
					headerStyle.setAlignment(CellStyle.ALIGN_CENTER);
					headerStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

					CellStyle textStyle = workbook.createCellStyle();
					textStyle.setAlignment(CellStyle.ALIGN_CENTER);
					textStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

					CellStyle numberStyle = workbook.createCellStyle();
					numberStyle.setDataFormat(numberFormat.getFormat("#,##0.00"));
					numberStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

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
					
					
					for (CadastroNFe cadastro : notas_fiscais) {
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
						cell.setCellValue(cadastro.getData());
						
				
						
						
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

						//Escrevendo o arquivo em disco
						FileOutputStream out = new FileOutputStream(new File("C:\\Users\\aisla\\Documents\\products.xls"));
						workbook.write(out);
						out.close();
						//workbook.close();
						System.out.println("Success!!");

						} catch (FileNotFoundException e) {
						e.printStackTrace();
						} catch (IOException e) {
						e.printStackTrace();
						}*/

				}
			});
			btnExportar.setBounds(1183, 479, 89, 23);
			painelPrincipal.add(btnExportar);
		
		this.setLocationRelativeTo(null);

		this.setVisible(true);
	}
}
