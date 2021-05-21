package main.java.gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
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
//import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

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

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;




public class TelaPlanilhaNFe extends JDialog {

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
	
	public TelaPlanilhaNFe() {
		setModal(true);

		TelaPlanilhaNFe isto = this;
		
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
				   cadastro.setCaminho_arquivo(file.getAbsolutePath());
				   System.out.println("caminho do arquivo: " + cadastro.getCaminho_arquivo());
                   
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

			                String procura_nfe = tratamentoDados.tratar("NFA-e","VENDA");
			                String nfe = null;
			                String natureza = null;
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
			                		
			               // String nfe = lines[27].toString().replaceAll("[^0-9]+", "");
			               System.out.println("Numero nfe: " + nfe);
			                
			                
			               
			               
			                
			                String serie = tratamentoDados.tratar("SÉRIE", "C");
			                serie = serie.replaceAll("[^0-9]+", "");
			                //String serie = lines[90].toString().replaceAll("[^0-9]+", "");
			                System.out.println("Serie: " + serie );
			                
			               // String nome_remetente = lines[29].toString().replaceAll("[0-9]+", "");
			                // nome_remetente = nome_remetente.replaceAll("[^a-zA-Z ]", "");
			               // int linha_natureza = tratar.indexOf(tratar);
			                
			                String nome_remetente = null;
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
			                
                            
                            
                            
			                System.out.println("Remetente: " + nome_remetente);
			               /* 
			                String inscricao_remetente  = lines[31].toString().replaceAll("[^0-9]+", "");
			                inscricao_remetente =  inscricao_remetente.substring(4, inscricao_remetente.length());
			                System.out.println("Inscricao: " + inscricao_remetente);
                             */
			                
			                //String procura_inscricao_remetente = tratamentoDados.tratar("BRASIL", ",");
			              
			                String teste_inscricao_remetente = tratar.replaceFirst("BRASIL", "XZYK");
			                TratarDados tratamentoDadosInscricaoRemetente = new TratarDados(teste_inscricao_remetente);
				               String inscricao_remetente =  tratamentoDadosInscricaoRemetente.tratar("XZYK", ",");
					             
				             //  System.out.println(teste_inscricao_remetente);
				              teste_inscricao_remetente = teste_inscricao_remetente.replaceFirst(inscricao_remetente, "");
				               System.out.println(teste_inscricao_remetente);
				                TratarDados tratamentoDadosNomeDestinatario = new TratarDados(teste_inscricao_remetente);
				                String nome_destinatario = tratamentoDadosNomeDestinatario.tratar("XZYK,", ",");
				                nome_destinatario = nome_destinatario.replaceAll("[^a-zA-Z ]", "");
				                
				             // teste_inscricao_remetente = tratar.replaceFirst(inscricao_remetente, "");
				              // System.out.println(teste_inscricao_remetente);
				             /*  String procura_nome_destinatario = tratamentoDadosInscricao.tratar("XZYK", ",");
				               procura_nome_destinatario = procura_nome_destinatario.replaceAll("[^a-zA-Z ]", "");
                               String nome_destinatario = procura_nome_destinatario;
			                */
                            String inscricao_destinatario = tratamentoDados.tratar("BRASIL", ",");
			                System.out.println("Inscricao: " + inscricao_remetente);

			            
			                //String array_protocolo[] = lines[95].split(" ");
			                String protocolo = tratamentoDados.tratar("USO", "-");
			                protocolo = protocolo.replaceAll("[^0-9]+", "");
			                //String protocolo = array_protocolo[0].toString();
			                
			                System.out.println("Protocolo: " + protocolo);
			                
			                //String data = lines[33].toString();
			                teste_inscricao_remetente = teste_inscricao_remetente.replaceFirst(protocolo, "DATA");
			                TratarDados tratarDadosData = new TratarDados(teste_inscricao_remetente);
			                String data = tratarDadosData.tratar("DATA", ",");
			                data = data.replace("-", "");
			                System.out.println("Data: " + data );

			             //  String natureza = lines[28].toString();
			                System.out.println("Natureza: " + natureza);
			                
			               // String nome_destinatario = lines[32].toString().replaceAll("[0-9]+", "");
			                //nome_destinatario = nome_destinatario.replaceAll("[^a-zA-Z ]", "");

			                System.out.println("Destinatario: " + nome_destinatario);
                          
			               /* String inscricao_destinatario = lines[36].toString().replaceAll("[^0-9]+", "");
			                inscricao_destinatario = inscricao_destinatario.substring(4, inscricao_destinatario.length());
			                System.out.println("Inscricao: " + inscricao_destinatario);
                          */
			               
			             //   String produto = separados[0].toString();
			                String produto = tratamentoDados.tratar("BC", "-");
			                produto = produto.replaceAll("[^a-zA-Z ]", "");
			                produto = produto.substring(5, produto.length());
			                System.out.println("Produto: " + produto );
			                
			            
			                String dados_valorados =  tratamentoDados.tratar("BC", "DADOS");
			                dados_valorados = dados_valorados.replaceAll(" ", "-");
			              //  dados_valorados = dados_valorados.replaceAll(",", "*");
			               // dados_valorados = dados_valorados.replaceAll(" ", ",");


			                System.out.println(dados_valorados);
			                TratarDados tratamentoDadosQuantidade = new TratarDados(dados_valorados);
			               // String dados_valorados_dividos[] = dados_valorados.split(" ");
			                
			               // String quantidade = separados[5].toString();
			                //String quantidade = dados_valorados_dividos[7];
			                String teste_procura_quantidade = tratamentoDadosQuantidade.tratar("-SC-", "-");
			                String quantidade = null ;
			                String quantidade_sem_formatacao = null;
			                String unidade = null;
			                System.out.println("Procuradando: "+ teste_procura_quantidade +"fim");
			                if(teste_procura_quantidade.length() < 2  || teste_procura_quantidade.equals("") || teste_procura_quantidade.equals(" ") || teste_procura_quantidade == null )
			                {
			                	teste_procura_quantidade = tratamentoDadosQuantidade.tratar("-KG-", "-");
				                System.out.println("Procuradando: "+ teste_procura_quantidade +"fim");

				                if(teste_procura_quantidade.length() < 2 || teste_procura_quantidade.equals("") || teste_procura_quantidade.equals(" ") || teste_procura_quantidade == null)
			                	{
				                	teste_procura_quantidade = tratamentoDadosQuantidade.tratar(" TON", "  ");
					                System.out.println("Procuradando: "+ teste_procura_quantidade +"fim");

				                	if(teste_procura_quantidade.equals("") || teste_procura_quantidade.equals(" ") || teste_procura_quantidade == null)
					                {
					                //System.out.print("Nenhum especie de unidade encontrada");	
					                	teste_procura_quantidade = tratamentoDadosQuantidade.tratar(" CB", "  ");
						                if(teste_procura_quantidade.equals("") || teste_procura_quantidade.equals(" ") || teste_procura_quantidade == null)
						                {
						                	System.out.print("Nenhum especie de unidade encontrada");	

						                }
						                else
						                {
						                	quantidade = teste_procura_quantidade;
						                	quantidade_sem_formatacao = quantidade;
						                	unidade = "CB";
						                }
					                
					                
					                }
					                else
					                {
					                	quantidade = teste_procura_quantidade;
					                	quantidade_sem_formatacao = quantidade;

					                	unidade = "TON";
					                }
				                	

				                	
			                	}else
			                	{
			                		quantidade = teste_procura_quantidade;
				                	quantidade_sem_formatacao = quantidade;

				                	unidade = "KQ";
			                	}
			                 	 
			                }
			                else
			                {
			                	 quantidade = teste_procura_quantidade;
				                	quantidade_sem_formatacao = quantidade;

			                 	 unidade = "SC";

			                }
			                
			                String unidade_quantidade[] = quantidade.split(" ");
			                //quantidade = unidade_quantidade[1];
		                	//quantidade_sem_formatacao = quantidade;

			                System.out.println("Quantidade: " + quantidade);
			                
			              //  String unidade = separados[4].toString();
			               // String unidade = dados_valorados_dividos[6].toString();
			               // String unidade = unidade_quantidade[0];
			                System.out.println(unidade);
			                if(unidade.equals("SC") || unidade.equals("sc") || unidade.equals(" SC "))
			                {
			                	//System.out.println("Tentando formatar quantidade em SC para KG");
			                	quantidade = quantidade.replaceAll("\\.", "");
		                		quantidade = quantidade.replaceAll(",", ".");
		                		System.out.println("Quantidade formatada: "+ quantidade);
			                	try {
			                		
			                    double quant = Double.parseDouble(quantidade); 
			                    System.out.println("quantidade inteira: " + quant);
			                    quant = quant * 60;
			                    quantidade = Double.toString(quant);
			                	}
			                	catch(Exception ev)
			                	{
				                    System.out.println("Erro ao converter quantidade para inteiros "+ ev.getMessage());

			                	}
			                  
			                }
			                
			                if(unidade.equals("TON") || unidade.equals("ton") || unidade.equals(" TON "))
			                {
			                	try {
			                		//quantidade = quantidade.replaceAll(".", "");
			                		quantidade = quantidade.replace(".", "");
			                		quantidade = quantidade.replace(",", ".");
			                   // double quant = Double.parseDouble(quantidade);
			                		BigDecimal quant = new BigDecimal(quantidade);
			                		System.out.println("quantidade inteira: " + quant);
			                   quant.multiply(new BigDecimal("100.00"));
			                    quantidade = quant.toPlainString();
			                	}
			                	catch(Exception ev)
			                	{
				                    System.out.println("Erro ao converter ton para double "+ ev.getMessage());

			                	}
			                  
			                }
			                
			                System.out.println("Quantidade: " + quantidade );
			                
			            
			                //String valor =  lines[82].toString();
			               // String valor = dados_valorados_dividos[8];
			              // int linha = Arrays.toString(lines).indexOf("R$");
			                teste_inscricao_remetente = teste_inscricao_remetente.replaceAll("12019000", "&");
			                teste_inscricao_remetente = teste_inscricao_remetente.replaceAll("23021000", "&");
			                teste_inscricao_remetente = teste_inscricao_remetente.replaceAll("14049010", "&");
			                teste_inscricao_remetente = teste_inscricao_remetente.replaceAll("11042300", "&");
			                teste_inscricao_remetente = teste_inscricao_remetente.replaceAll("12081000", "&");
			                teste_inscricao_remetente = teste_inscricao_remetente.replaceAll("11031900", "&");

			                
			                


			                teste_inscricao_remetente = teste_inscricao_remetente.replaceAll("KG " + quantidade_sem_formatacao, "INFOPRECO");
			                teste_inscricao_remetente = teste_inscricao_remetente.replaceAll("SC " + quantidade_sem_formatacao, "INFOPRECO");
			                teste_inscricao_remetente = teste_inscricao_remetente.replaceAll("TON " + quantidade_sem_formatacao, "INFOPRECO");

			                System.out.println(teste_inscricao_remetente);
			                TratarDados tratarDadosValor = new TratarDados(teste_inscricao_remetente);
                            String valor = tratarDadosValor.tratar("INFOPRECO", "&");
			               
			               // String valor = tratamentoDados.tratar("$" , "5");
			               //String valor = Arrays.toString(lines).substring(linha, linha + 13);
			               // valor = valor.replaceAll(",", "&");
			                System.out.println("Valor: " + valor);

			                //String valor_split[] = valor.split(" ");
			               // valor = valor_split[4];
			                //valor = valor.substring(0, valor.indexOf(","));
			                System.out.println("Valor: " + valor);

			                System.out.println("Fim do processo");
			    			modelo.addRow(new Object[]{nfe, serie, nome_remetente, inscricao_remetente, protocolo, data, natureza,
								       nome_destinatario, inscricao_destinatario, produto, quantidade, valor});
			               
			    			cadastro.setNfe(nfe);
			    			cadastro.setSerie(serie);
			    			cadastro.setNome_remetente(nome_remetente);
			    			cadastro.setInscricao_remetente(inscricao_remetente);
			    			cadastro.setProtocolo(protocolo);
			    			/*SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); 
							Date data_formatada = formato.parse(data);

			    			cadastro.setData(data_formatada);
			    			*/
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

						//Escrevendo o arquivo em disco
						FileOutputStream out = new FileOutputStream(new File("C:\\Users\\Aislan\\Documents\\products.xls"));
						workbook.write(out);
						workbook.close();
						out.close();
						//workbook.close();
						System.out.println("Success!!");

						} catch (FileNotFoundException e) {
						e.printStackTrace();
						} catch (IOException e) {
						e.printStackTrace();
						}

				}
			});
			btnExportar.setBounds(1183, 479, 89, 23);
			painelPrincipal.add(btnExportar);
		
		this.setLocationRelativeTo(null);

		this.setVisible(true);
	}
}
