package relatoria;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javax.imageio.ImageIO;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.printing.Orientation;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.model.XWPFParagraphDecorator;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDocument1;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSpacing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTabStop;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STLineSpacingRule;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTabJc;

import com.itextpdf.text.PageSize;

import cadastros.CadastroCliente;
import cadastros.CadastroContrato;
import cadastros.CadastroDocumento;
import cadastros.CadastroLogin;
import cadastros.CadastroModelo;
import cadastros.CadastroNFe;
import cadastros.CadastroProduto;
import conexaoBanco.GerenciarBancoClientes;
import conexaoBanco.GerenciarBancoContratos;
import conexaoBanco.GerenciarBancoDocumento;
import conexaoBanco.GerenciarBancoProdutos;
import conexaoBanco.GerenciarBancoTransferencias;
import manipular.ConfiguracoesGlobais;
import manipular.ConverterPdf;
import manipular.ManipularNotasFiscais;
import manipular.Nuvem;
import manipular.PorExtenso;
import outros.DadosGlobais;
import outros.GetData;
import tratamento_proprio.Log;
import views_personalizadas.TelaEmEspera;
import views_personalizadas.TelaNotificacao;

public class RelatorioContratoIndividual {

	private CadastroModelo modelo;
	private String path;

	CadastroContrato novo_contrato;
	private TelaEmEspera telaInformacoes;

	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private String servidor_unidade;
	private XWPFDocument document_global;
	private XWPFParagraph paragrafo_atual ;
	
	public RelatorioContratoIndividual(CadastroContrato contrato) {
		getDadosGlobais();
		this.modelo = modelo;
		servidor_unidade = configs_globais.getServidorUnidade();
		this.novo_contrato = contrato;
		criarDocumento();		
		
		
	}



	public void criarDocumento() {
		document_global = new XWPFDocument();

		CTSectPr sectPr = document_global.getDocument().getBody().addNewSectPr();
	    CTPageMar pageMar = sectPr.addNewPgMar();
	    pageMar.setLeft(BigInteger.valueOf(720L));
	    pageMar.setTop(BigInteger.valueOf(1440L));
	    pageMar.setRight(BigInteger.valueOf(720L));
	    pageMar.setBottom(BigInteger.valueOf(1440L));
	    
	    document_global.createStyles();
	    


		
	}
	
	public ByteArrayOutputStream preparar(int tipo_relatorio) {
		

		Locale ptBr = new Locale("pt", "BR");
		
		NumberFormat z = NumberFormat.getNumberInstance();

		
		  
		// cria o paragrafo do rodape
		XWPFParagraph rodape = document_global.createParagraph();
		rodape.setAlignment(ParagraphAlignment.LEFT);
		
		CTSectPr sectPr = document_global.getDocument().getBody().addNewSectPr();
        CTPageMar pageMar = sectPr.addNewPgMar();
        pageMar.setLeft(BigInteger.valueOf(100L));
        pageMar.setTop(BigInteger.valueOf(100L));
        pageMar.setRight(BigInteger.valueOf(100L));
        pageMar.setBottom(BigInteger.valueOf(100L));

		//criar o paragrafo do titulo
		XWPFParagraph title = document_global.createParagraph();
		title.setAlignment(ParagraphAlignment.LEFT);

        GetData data = new GetData();

		XWPFRun titleRun = title.createRun();
		titleRun.setText("Relatório de Contrato Individual N.º:" + novo_contrato.getCodigo() 
		 + " emitido em " + novo_contrato.getData_contrato() + " por " + login.getNome() + " " 
			+ login.getSobrenome() + " ás " + data.getHora());
		titleRun.setColor("000000");
		titleRun.setBold(false);
		titleRun.setUnderline(UnderlinePatterns.SINGLE);
		titleRun.setFontFamily("Arial");
		titleRun.setFontSize(10);

		
		CadastroCliente compradores[] = novo_contrato.getCompradores();
		CadastroCliente vendedores[] = novo_contrato.getVendedores();
		CadastroCliente corretores[] = novo_contrato.getCorretores();

		String nome_corretores = "";
		String nome_vendedores = "";
		String nome_compradores = "";

		if (corretores[0] != null) {
			if (corretores[0].getTipo_pessoa() == 0) {
				// pessoa fisica
				nome_corretores = corretores[0].getNome_empresarial();
			} else {
				nome_corretores = corretores[0].getNome_fantaia();

			}
		}

		
		if (compradores[0] != null) {
			if (compradores[0].getTipo_pessoa() == 0) {
				// pessoa fisica
				nome_compradores = compradores[0].getNome_empresarial();
			} else {
				nome_compradores = compradores[0].getNome_fantaia();

			}
		}

		for (CadastroCliente vendedor : vendedores) {
			if (vendedor != null) {
				if (vendedor.getTipo_pessoa() == 0) {
					// pessoa fisica
					nome_vendedores = nome_vendedores + " | " + vendedor.getNome_empresarial();
				} else {
					nome_vendedores = nome_vendedores + " | " + vendedor.getNome_fantaia();

			

				}
			}
		}
		
		double quantidade_sacos = 0;
		double quantidade_quilogramas = 0;

		
		if(novo_contrato.getMedida().equalsIgnoreCase("Sacos")) {
			quantidade_sacos = novo_contrato.getQuantidade();
			quantidade_quilogramas = novo_contrato.getQuantidade() * 60;
		}else if(novo_contrato.getMedida().equalsIgnoreCase("KG")) {
			quantidade_quilogramas = novo_contrato.getQuantidade();
			quantidade_sacos = novo_contrato.getQuantidade() /60;

		}

		XWPFParagraph informacoes = document_global.createParagraph();
		informacoes.setAlignment(ParagraphAlignment.LEFT);

		XWPFRun informacoesRun = informacoes.createRun();
		informacoesRun.setColor("000000");
		informacoesRun.setBold(false);
		informacoesRun.setFontFamily("Arial");
		informacoesRun.setFontSize(10);
		
		setSingleLineSpacing(informacoes);
		/*
		substituirTexto("Informações gerais sobre o contrato:\r\n"
				+ "Corretores: " + nome_corretores.toUpperCase() + "\r\n" 
				+ "Compradores: " + nome_compradores.toUpperCase()  + "\r\n" 
				+ "Vendedores: " + nome_vendedores.toUpperCase() + "\r\n" 
				+ "Data do Contrato: " + novo_contrato.getData_contrato() +"          Data da Entrega: " + novo_contrato.getData_entrega() 
				+ "          Produto: " + novo_contrato.getModelo_safra().getProduto().getNome_produto() 
				+ "          Safra: " + novo_contrato.getModelo_safra().getAno_plantio() + "/" + novo_contrato.getModelo_safra().getAno_colheita()
				+ "          Valor saco:  "+ NumberFormat.getCurrencyInstance(ptBr)
				.format(novo_contrato.getValor_produto()) 
				+ "          Quantidade: " + z.format(quantidade_sacos)+ " sacos                Valor Total: " +  NumberFormat.getCurrencyInstance(ptBr)
				.format(novo_contrato.getValor_a_pagar()));
		*/
		
		ArrayList<CadastroContrato> contrato_original = new ArrayList<>();
		contrato_original.add(novo_contrato);
		criarTabelaContrato(contrato_original);

		
		ArrayList<CadastroContrato> lista_sub_contratos = new GerenciarBancoContratos().getSubContratos(novo_contrato.getId());
        
        if(lista_sub_contratos.size() > 0 && tipo_relatorio == 1) {

		adicionarTraco(true, 0);

		
		XWPFParagraph titulo_sub_contratos = document_global.createParagraph();
		titulo_sub_contratos.setAlignment(ParagraphAlignment.CENTER);

		XWPFRun titulo_sub_contratostitleRun = titulo_sub_contratos.createRun();
		titulo_sub_contratostitleRun.setText("Sub-Contratos");
		titulo_sub_contratostitleRun.setColor("000000");
		titulo_sub_contratostitleRun.setBold(true);
		titulo_sub_contratostitleRun.setFontFamily("Arial");
		titulo_sub_contratostitleRun.setFontSize(10);
	  	
        criarTabelaContrato(lista_sub_contratos);
        
        criarTabelaGanhosPotenciais(lista_sub_contratos);
            
   		
		
        }
	  	
        
         //pagamentos
		GerenciarBancoContratos gerenciar_pags = new GerenciarBancoContratos();
		ArrayList<CadastroContrato.CadastroPagamentoContratual> pagamentos_contratuais = gerenciar_pags.getPagamentosContratuais(novo_contrato.getId());
        GerenciarBancoTransferencias gerenciar_trans = new GerenciarBancoTransferencias();   
		ArrayList<CadastroContrato.CadastroTransferenciaPagamentoContratual> transferencias_remetente = gerenciar_trans.getTransferenciasRemetente(novo_contrato.getId());
		ArrayList<CadastroContrato.CadastroTransferenciaPagamentoContratual> transferencias_destinatario = gerenciar_trans.getTransferenciaDestinatario(novo_contrato.getId());

		if(pagamentos_contratuais.size() > 0 || transferencias_remetente.size() > 0 || transferencias_destinatario.size() > 0) {

		    adicionarTraco(true, 0);
	   		XWPFParagraph titulo_sub_pagamentos = document_global.createParagraph();
	   		titulo_sub_pagamentos.setAlignment(ParagraphAlignment.CENTER);

			XWPFRun titulo_sub_pagamentostitleRun = titulo_sub_pagamentos.createRun();
			titulo_sub_pagamentostitleRun.setText("Pagamentos");
			titulo_sub_pagamentostitleRun.setColor("000000");
			titulo_sub_pagamentostitleRun.setBold(true);
			titulo_sub_pagamentostitleRun.setFontFamily("Arial");
			titulo_sub_pagamentostitleRun.setFontSize(10);
		criarTabelaPagamentos(pagamentos_contratuais, transferencias_remetente, transferencias_destinatario);
       
		//adiciona comprovante de pagamentos
		adicionarComprovantesPagamentos(pagamentos_contratuais);
		
		}
		
		
		//carregamentos
				ArrayList<CadastroContrato.Carregamento> carregamentos = gerenciar_pags.getCarregamentos(novo_contrato.getId());
				
				if(carregamentos.size() > 0) {
				
				
       adicionarTraco(true, 0);

		
		XWPFParagraph titulo_carregamentos = document_global.createParagraph();
		titulo_carregamentos.setAlignment(ParagraphAlignment.CENTER);

		XWPFRun titulo_carregamentosRun = titulo_carregamentos.createRun();
		titulo_carregamentosRun.setText("Carregamentos");
		titulo_carregamentosRun.setColor("000000");
		titulo_carregamentosRun.setBold(true);
		titulo_carregamentosRun.setFontFamily("Arial");
		titulo_carregamentosRun.setFontSize(10); 
		
		criarTabelaCarregamentos(carregamentos);

		
				}
		
		
		//cabecalho e rodape

		try {
			CTP ctP = CTP.Factory.newInstance();

			// header text
			CTText t = ctP.addNewR().addNewT();


			XWPFParagraph cabecalho = new XWPFParagraph(ctP, document_global);
			XWPFRun cabecalhoRun = cabecalho.createRun();
			cabecalhoRun.setFontSize(16);
			cabecalhoRun.setFontFamily("Arial Black");
			cabecalhoRun.setText("LD ARMAZÉNS GERAIS");
			cabecalhoRun.setUnderline(UnderlinePatterns.SINGLE);
			cabecalhoRun.setColor("00A000");

			XWPFParagraph pars[] = new XWPFParagraph[1];

			pars[0] = cabecalho;

			pars[0].setAlignment(ParagraphAlignment.LEFT);
		
			XWPFHeaderFooterPolicy hfPolicy = document_global.createHeaderFooterPolicy();
			  XWPFHeader header = hfPolicy.createHeader(XWPFHeaderFooterPolicy.DEFAULT,pars);

			//hfPolicy.createHeader(XWPFHeaderFooterPolicy.DEFAULT, pars);
			
			  pars[0]  = header.getParagraphArray(0);
			  pars[0].setAlignment(ParagraphAlignment.LEFT);

			  CTTabStop tabStop = pars[0] .getCTP().getPPr().addNewTabs().addNewTab();
			  tabStop.setVal(STTabJc.RIGHT);
			  int twipsPerInch =  1440;
			  tabStop.setPos(BigInteger.valueOf(6 * twipsPerInch));

			  cabecalhoRun =  pars[0].createRun();  
			  cabecalhoRun.addTab();

			  cabecalhoRun =  pars[0].createRun();
			  URL url = getClass().getResource("/imagens/logo_para_relatorio.png");
			  String imgFile=url.getFile();
			  cabecalhoRun.addPicture(new FileInputStream(imgFile), XWPFDocument.PICTURE_TYPE_PNG, imgFile, Units.toEMU(30), Units.toEMU(30));
			
			
			
			

			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao criar cabecalho e rodape do contrato!\nConsulte o administrador do sistema!");
			e.printStackTrace();
		}
		

		
		ByteArrayOutputStream saida_apos_edicao = new ByteArrayOutputStream();

		/*try {
			document_global.write(new FileOutputStream("c:\\arquivoteste.docx"));
			document_global.write(saida_apos_edicao);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/

		try {
			document_global.write(saida_apos_edicao);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return saida_apos_edicao;

	}
	
	
	public void setSingleLineSpacing(XWPFParagraph para) {
	    CTPPr ppr = para.getCTP().getPPr();
	    if (ppr == null) ppr = para.getCTP().addNewPPr();
	    CTSpacing spacing  = ppr.isSetSpacing()? ppr.getSpacing() : ppr.addNewSpacing();
	    spacing.setAfter(BigInteger.valueOf(0));
	    spacing.setBefore(BigInteger.valueOf(0));
	    spacing.setLineRule(STLineSpacingRule.AUTO);
	    spacing.setLine(BigInteger.valueOf(240));
	}
	
	
	public void adicionarTraco(boolean negrito, int flag) {

		XWPFParagraph traco = document_global.createParagraph();
		traco.setAlignment(ParagraphAlignment.CENTER);

		XWPFRun tracotitleRun = traco.createRun();
		if(flag == 1) {
			tracotitleRun.setText("________________________________________________________________________________");

			//continou
		}else {
			//tracejado
			tracotitleRun.setText("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ __ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ ");

			 
		}
		tracotitleRun.setColor("000000");
		tracotitleRun.setBold(negrito);
		tracotitleRun.setFontFamily("Arial");
		tracotitleRun.setFontSize(12);

	}
	
	
public void adicionarComprovantesPagamentos(ArrayList<CadastroContrato.CadastroPagamentoContratual> pagamentos) {
	
	XWPFParagraph par = document_global.createParagraph();
	
	//pega os documentos
	GerenciarBancoDocumento gerenciar = new GerenciarBancoDocumento();
	ArrayList<CadastroDocumento> docs = gerenciar.getDocumentos(novo_contrato.getId());
	
	int num_comprovantes = 0;
	
	for(CadastroDocumento doc : docs) {
		if(doc.getTipo() == 2) {
			//e um pagamento
			num_comprovantes++;
		}
	}
	
	  XWPFTable table = document_global.createTable(1 , num_comprovantes);

	    setTableAlign(table, ParagraphAlignment.CENTER);
	    XWPFTableRow tableRowOne = table.getRow(0);
	    tableRowOne.getCell(0).removeParagraph(0);
	   
	    
      XWPFParagraph paragraph = tableRowOne.getCell(0).addParagraph();

      
      int celula_global = 0;
     
	
	for(CadastroContrato.CadastroPagamentoContratual pag : pagamentos) {
		//verifica se há algum documento do tipo 2(comprovante de pagamento) para este id de pagamento
		
		boolean tem_comprovante = false;
		CadastroDocumento comprovante = new CadastroDocumento();
		
		for(CadastroDocumento doc : docs) {
			if(doc.getId_pai() == pag.getId_pagamento()) {
				comprovante = doc;
				tem_comprovante = true;
				break;
			}
		}
		
		
		if(tem_comprovante) {
			//verifica a extensao do arquivo
			String extensaoDoArquivo = FilenameUtils.getExtension(comprovante.getNome_arquivo());
			if(extensaoDoArquivo.equalsIgnoreCase("png")) {
				
				String unidade_base_dados = configs_globais.getServidorUnidade();
				String caminho_salvar = unidade_base_dados + "\\" + novo_contrato.getCaminho_diretorio_contrato();
				String caminho_completo = caminho_salvar + "\\comprovantes\\" + comprovante.getNome_arquivo();
				
				int x = 0, y = 0;
				
				try {
					BufferedImage img = ImageIO.read(new File(caminho_completo));
					x = img.getWidth();
					y = img.getHeight();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
             /*
				  FileInputStream in;
				try {
					in = new FileInputStream(caminho_completo);
					  run.addPicture(in, Document.PICTURE_TYPE_JPEG, comprovante.getNome_arquivo(), Units.toEMU(x/3), Units.toEMU(y/3));
					  in.close();  
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/

				  FileInputStream in;
					try {
						
						 tableRowOne = table.getRow(0);
						    tableRowOne.getCell(celula_global).removeParagraph(0);
					      paragraph = tableRowOne.getCell(celula_global).addParagraph();
							 paragraph.setAlignment(ParagraphAlignment.CENTER);

							 XWPFRun run = paragraph.createRun();

					      
					       
						in = new FileInputStream(caminho_completo);
						  run.addPicture(in, Document.PICTURE_TYPE_JPEG, comprovante.getNome_arquivo(), Units.toEMU(x/2), Units.toEMU(y/2));
						  in.close();  
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvalidFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				  
				  
		
			}

			celula_global++;
		}
		
		
		
	}

	
}
	

	
	public void substituirTexto(String text_amostra){
	
		//criarParagrafo(2);

		
		//pegar os paragrafos
		String separador_paragrafo[] = text_amostra.split("\n");
		for(String paragrafo : separador_paragrafo) {
			criarParagrafo(2);

			paragrafo = paragrafo.replaceAll(" ", "&");
		
		String separador_palabras[] = paragrafo.split("&");
		for(String palavra : separador_palabras) {
			if(palavra.contains("[") || palavra.contains("]")) {
		        adicionarTextoParagrafoAtual(palavra.replaceAll("[\\[\\]]", "") + " ", true);
                	  
                  
			}else {
                if(palavra.equals("preco_decimal")) {
                	 
			       	  	Locale ptBr = new Locale("pt", "BR");
			       	   	String valorString = NumberFormat.getCurrencyInstance(ptBr).format((novo_contrato.getValor_produto()));
			       	   	System.out.println(valorString);   
        		
    		        adicionarTextoParagrafoAtual(valorString + " ", true);
                }
                else if(palavra.equals("preco_extenso")){
                	
                	String valor_extenso = new PorExtenso(novo_contrato.getValor_produto()).toString();
    		        adicionarTextoParagrafoAtual(palavra.replace("preco_extenso" ,valor_extenso.toLowerCase() + ""), false);

                }else {
		        adicionarTextoParagrafoAtual(palavra + " ", false);
                }
			}

		}
		}
        
        
	}
	
	private void setOrientacao(int flag){
	    CTDocument1 doc = document_global.getDocument();
	    CTBody body = doc.getBody();
	    CTSectPr section = body.addNewSectPr();
	    XWPFParagraph para = document_global.createParagraph();
	    CTP ctp = para.getCTP();
	    CTPPr br = ctp.addNewPPr();
	    br.setSectPr(section);
	    CTPageSz pageSize;
	    if (section.isSetPgSz()) {
	       pageSize = section.getPgSz();
	    } else {
	       pageSize = section.addNewPgSz();
	    }	  
	    
	    if(flag == 1){
	        pageSize.setOrient(STPageOrientation.PORTRAIT);
	        pageSize.setW(BigInteger.valueOf(842 * 20));
	        pageSize.setH(BigInteger.valueOf(595 * 20));
	    }
	    else{
	        pageSize.setOrient(STPageOrientation.LANDSCAPE);
	        pageSize.setH(BigInteger.valueOf(842 * 20));
	        pageSize.setW(BigInteger.valueOf(595 * 20));
	    }
	}
	
	
	
	public void criarParagrafo(int alinhamento) {
		XWPFParagraph paragrafo = document_global.createParagraph();
	
		setSingleLineSpacing(paragrafo);
		if(alinhamento == 0) {
			//centro
			paragrafo.setAlignment(ParagraphAlignment.CENTER);

		}else if(alinhamento == 1) {
			//direita
			paragrafo.setAlignment(ParagraphAlignment.RIGHT);

		}
		else if(alinhamento == -1) {
			//esquerda
			paragrafo.setAlignment(ParagraphAlignment.LEFT);

		}else if(alinhamento == 2) {
			paragrafo.setAlignment(ParagraphAlignment.BOTH);

		}
		
		paragrafo_atual  = paragrafo;
	}
	
	
	public void criarParagrafoTabela(XWPFParagraph paragraph, String texto, boolean negrito) {
		//paragraph.setIndentationLeft(100);
		//paragraph.setIndentationRight(100);
		 paragraph.setAlignment(ParagraphAlignment.LEFT);

		 XWPFRun run = paragraph.createRun();

         run.setFontFamily("Times New Roman");
          run.setFontSize(9);
          run.setBold(negrito);
          run.setText(texto);

		      
       
		
	}
	
	public void criarTabelaGanhosPotenciais(ArrayList<CadastroContrato> lista_sub_contratos) {
		Locale ptBr = new Locale("pt", "BR");

		   double valor_total_contrato_original = Double.parseDouble(novo_contrato.getValor_a_pagar().toPlainString());
	   		
			String string_valor_total_contrato_original = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_contrato_original);

		   double valor_total_sub_contratos = 0;
	   		
	   		for(CadastroContrato sub : lista_sub_contratos) {
	   			
	   			double valor_local = Double.parseDouble(sub.getValor_a_pagar().toPlainString());
	   			valor_total_sub_contratos += valor_local;
	   		}
	   		
			String string_valor_total_sub_contratos = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_sub_contratos);

			
	   		double valor_total_diferenca_contratos = valor_total_contrato_original - valor_total_sub_contratos;
	   		
			String string_valor_total_diferenca_contratos = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_diferenca_contratos);

			
	   		double valor_total_comissoes = 0;
	   		
	   		if(novo_contrato.getComissao() == 1) {
	   			double valor_local = Double.parseDouble(novo_contrato.getValor_comissao().toPlainString());
	   			valor_total_comissoes += valor_local;
	   		}
	   		
	              for(CadastroContrato sub : lista_sub_contratos) {
	   			
	           	   if(sub.getComissao() == 1) {
	          			double valor_local = Double.parseDouble(sub.getValor_comissao().toPlainString());
	          			valor_total_comissoes += valor_local;
	          		}
	   		
	   		}
	              
	  			String string_valor_total_comissoes = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_comissoes);

	            double valor_total_ganhos_potenciais =   valor_total_diferenca_contratos + valor_total_comissoes;
	   		           
	  			String string_valor_total_ganhos_potenciais  = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_ganhos_potenciais);

	            
		NumberFormat z = NumberFormat.getNumberInstance();


		
		//criarParagrafo(1);
		//linhas x colunas
		    XWPFTable table = document_global.createTable(2 , 5);

		    setTableAlign(table, ParagraphAlignment.CENTER);
		    XWPFTableRow tableRowOne = table.getRow(0);
		    tableRowOne.getCell(0).removeParagraph(0);
		   
		    
            XWPFParagraph paragraph = tableRowOne.getCell(0).addParagraph();

            criarParagrafoTabela(paragraph,"Valor Contratos:", false );
           
            tableRowOne = table.getRow(1);
		    tableRowOne.getCell(0).removeParagraph(0);
            paragraph = tableRowOne.getCell(0).addParagraph();
            criarParagrafoTabela(paragraph,string_valor_total_contrato_original, false );
           
            
            
			        tableRowOne = table.getRow(0);
				    tableRowOne.getCell(1).removeParagraph(0);
                    paragraph = tableRowOne.getCell(1).addParagraph();
		            criarParagrafoTabela(paragraph,"Valor Sub-Contratos:", false );
		            
		            tableRowOne = table.getRow(1);
				    tableRowOne.getCell(1).removeParagraph(0);
                    paragraph = tableRowOne.getCell(1).addParagraph();
		            criarParagrafoTabela(paragraph,string_valor_total_sub_contratos, false );
			      
		            
		            tableRowOne = table.getRow(0);
				    tableRowOne.getCell(2).removeParagraph(0);
                    paragraph = tableRowOne.getCell(2).addParagraph();
		            criarParagrafoTabela(paragraph,"Valor Diferença:", false );
		            
		            tableRowOne = table.getRow(1);
				    tableRowOne.getCell(2).removeParagraph(0);
                    paragraph = tableRowOne.getCell(2).addParagraph();
		            criarParagrafoTabela(paragraph,string_valor_total_diferenca_contratos, false );
		            
		            tableRowOne = table.getRow(0);
				    tableRowOne.getCell(3).removeParagraph(0);
                    paragraph = tableRowOne.getCell(3).addParagraph();
		            criarParagrafoTabela(paragraph,"Valor Comissões", false );
		            
		            tableRowOne = table.getRow(1);
				    tableRowOne.getCell(3).removeParagraph(0);
                    paragraph = tableRowOne.getCell(3).addParagraph();
		            criarParagrafoTabela(paragraph,string_valor_total_comissoes, false );
		            
		            tableRowOne = table.getRow(0);
				    tableRowOne.getCell(4).removeParagraph(0);
                    paragraph = tableRowOne.getCell(4).addParagraph();
		            criarParagrafoTabela(paragraph,"Potenciais Ganhos", false );

		            tableRowOne = table.getRow(1);
				    tableRowOne.getCell(4).removeParagraph(0);
                    paragraph = tableRowOne.getCell(4).addParagraph();
		            criarParagrafoTabela(paragraph,string_valor_total_ganhos_potenciais, false );
		     
		 		
		
		
	}
	
	public void criarTabelaContrato(ArrayList<CadastroContrato> lista_sub_contratos ) {
		

		NumberFormat z = NumberFormat.getNumberInstance();

		Locale ptBr = new Locale("pt", "BR");

		
		//criarParagrafo(1);
		//linhas x colunas
		    XWPFTable table = document_global.createTable(lista_sub_contratos.size() +1 , 10);

		    setTableAlign(table, ParagraphAlignment.CENTER);
		    XWPFTableRow tableRowOne = table.getRow(0);
		    tableRowOne.getCell(0).removeParagraph(0);
		   
		    
            XWPFParagraph paragraph = tableRowOne.getCell(0).addParagraph();

            criarParagrafoTabela(paragraph,"Codigo", false );
           
           
			        tableRowOne = table.getRow(0);
				    tableRowOne.getCell(1).removeParagraph(0);
                    paragraph = tableRowOne.getCell(1).addParagraph();
		            criarParagrafoTabela(paragraph,"Corretor", false );
			      
		            
		            tableRowOne = table.getRow(0);
				    tableRowOne.getCell(2).removeParagraph(0);
                    paragraph = tableRowOne.getCell(2).addParagraph();
		            criarParagrafoTabela(paragraph,"Comprador", false );
		            
		            
		            tableRowOne = table.getRow(0);
				    tableRowOne.getCell(3).removeParagraph(0);
                    paragraph = tableRowOne.getCell(3).addParagraph();
		            criarParagrafoTabela(paragraph,"Vendedores", false );
		            
		            
		            tableRowOne = table.getRow(0);
				    tableRowOne.getCell(4).removeParagraph(0);
                    paragraph = tableRowOne.getCell(4).addParagraph();
		            criarParagrafoTabela(paragraph,"Produto", false );

		            
		            tableRowOne = table.getRow(0);
				    tableRowOne.getCell(5).removeParagraph(0);
                    paragraph = tableRowOne.getCell(5).addParagraph();
		            criarParagrafoTabela(paragraph,"Safra", false );

		            tableRowOne = table.getRow(0);
				    tableRowOne.getCell(6).removeParagraph(0);
                    paragraph = tableRowOne.getCell(6).addParagraph();
		            criarParagrafoTabela(paragraph,"Valor", false );
		            
		            tableRowOne = table.getRow(0);
				    tableRowOne.getCell(7).removeParagraph(0);
                    paragraph = tableRowOne.getCell(7).addParagraph();
		            criarParagrafoTabela(paragraph,"Quantidade(sacos)", false );

		            
		            tableRowOne = table.getRow(0);
		 			tableRowOne.getCell(8).removeParagraph(0);
		            paragraph = tableRowOne.getCell(8).addParagraph();
		 		    criarParagrafoTabela(paragraph,"Valor Total", false );
		 		    

		            tableRowOne = table.getRow(0);
		 			tableRowOne.getCell(9).removeParagraph(0);
		            paragraph = tableRowOne.getCell(9).addParagraph();
		 		    criarParagrafoTabela(paragraph,"Comissão(Total)", false );
		 		
		 		
	 		    	int indice = 0;

		 		    for(int i = 1; i < lista_sub_contratos.size()+1; i++ ) {
		 		    	
		 		    CadastroContrato local = lista_sub_contratos.get(indice);
		 		    
		 		   double quantidade_sacos_sub = 0;
		 			double quantidade_quilogramas_sub = 0;

		 			
		 			if(local.getMedida().equalsIgnoreCase("Sacos")) {
		 				quantidade_sacos_sub = local.getQuantidade();
		 				quantidade_quilogramas_sub = local.getQuantidade() * 60;
		 			}else if(local.getMedida().equalsIgnoreCase("KG")) {
		 				quantidade_quilogramas_sub = local.getQuantidade();
		 				quantidade_sacos_sub = local.getQuantidade() /60;

		 			}
		 			
		 			
		 			CadastroCliente compradores_sub[] = local.getCompradores();
		 			CadastroCliente vendedores_sub[] = local.getVendedores();
		 			CadastroCliente corretores_sub[] = local.getCorretores();

		 			String nome_corretores_sub = "";
		 			String nome_vendedores_sub = "";
		 			String nome_compradores_sub = "";

		 			if (corretores_sub[0] != null) {
		 				if (corretores_sub[0].getTipo_pessoa() == 0) {
		 					// pessoa fisica
		 					nome_corretores_sub = corretores_sub[0].getNome_empresarial();
		 				} else {
		 					nome_corretores_sub = corretores_sub[0].getNome_fantaia();

		 				}
		 			}

		 			
		 			if (compradores_sub[0] != null) {
		 				if (compradores_sub[0].getTipo_pessoa() == 0) {
		 					// pessoa fisica
		 					nome_compradores_sub = compradores_sub[0].getNome_empresarial();
		 				} else {
		 					nome_compradores_sub = compradores_sub[0].getNome_fantaia();

		 				}
		 			}

		 			for (CadastroCliente vendedor : vendedores_sub) {
		 				if (vendedor != null) {
		 					if (vendedor.getTipo_pessoa() == 0) {
		 						// pessoa fisica
		 						nome_vendedores_sub = nome_vendedores_sub + " | " + vendedor.getNome_empresarial();
		 					} else {
		 						nome_vendedores_sub = nome_vendedores_sub + " | " + vendedor.getNome_fantaia();

		 				

		 					}
		 				}
		 			}
		 
		 			
		 		    //linha com dados
		 		   tableRowOne = table.getRow(i);
				    tableRowOne.getCell(0).removeParagraph(0);
                   paragraph = tableRowOne.getCell(0).addParagraph();
		            criarParagrafoTabela(paragraph,local.getCodigo(), false );
		 		    
		 		      tableRowOne = table.getRow(i);
					    tableRowOne.getCell(1).removeParagraph(0);
	                    paragraph = tableRowOne.getCell(1).addParagraph();
			            criarParagrafoTabela(paragraph,nome_corretores_sub, false );
				      
			            
			            tableRowOne = table.getRow(i);
					    tableRowOne.getCell(2).removeParagraph(0);
	                    paragraph = tableRowOne.getCell(2).addParagraph();
			            criarParagrafoTabela(paragraph,nome_compradores_sub, false );
			            
			            
			            tableRowOne = table.getRow(i);
					    tableRowOne.getCell(3).removeParagraph(0);
	                    paragraph = tableRowOne.getCell(3).addParagraph();
			            criarParagrafoTabela(paragraph,nome_vendedores_sub, false );
			            
			            
			            tableRowOne = table.getRow(i);
					    tableRowOne.getCell(4).removeParagraph(0);
	                    paragraph = tableRowOne.getCell(4).addParagraph();
			            criarParagrafoTabela(paragraph,local.getModelo_safra().getProduto().getNome_produto(), false );

			            
			            tableRowOne = table.getRow(i);
					    tableRowOne.getCell(5).removeParagraph(0);
	                    paragraph = tableRowOne.getCell(5).addParagraph();
			            criarParagrafoTabela(paragraph,local.getModelo_safra().getAno_plantio()+"/"+local.getModelo_safra().getAno_colheita(), false );

			            

						String valorString = NumberFormat.getCurrencyInstance(ptBr)
								.format(local.getValor_produto());
			            tableRowOne = table.getRow(i);
					    tableRowOne.getCell(6).removeParagraph(0);
	                    paragraph = tableRowOne.getCell(6).addParagraph();
			            criarParagrafoTabela(paragraph,valorString, false );
			            
			            
			            tableRowOne = table.getRow(i);
					    tableRowOne.getCell(7).removeParagraph(0);
	                    paragraph = tableRowOne.getCell(7).addParagraph();
			            criarParagrafoTabela(paragraph,z.format(local.getQuantidade()), false );

			            valorString = NumberFormat.getCurrencyInstance(ptBr)
								.format(local.getValor_a_pagar());
			            tableRowOne = table.getRow(i);
			 			tableRowOne.getCell(8).removeParagraph(0);
			            paragraph = tableRowOne.getCell(8).addParagraph();
			 		    criarParagrafoTabela(paragraph,valorString, false );
			 		    
			 		    String comissao = "";
			 		    if(local.getComissao() == 1) {
			 		    	comissao  = NumberFormat.getCurrencyInstance(ptBr)
									.format(local.getValor_comissao());
			 		    }else {
			 		    	comissao = "Não";
			 		    }
			 		   tableRowOne = table.getRow(i);
			 			tableRowOne.getCell(9).removeParagraph(0);
			            paragraph = tableRowOne.getCell(9).addParagraph();
			 		    criarParagrafoTabela(paragraph,comissao, false );
		 		    
			 		   indice++;
		 		    }
		 		    
			    criarParagrafo(2);
		    
		    
			    
	}
	
	public void criarTabelaPagamentos(ArrayList<CadastroContrato.CadastroPagamentoContratual> pagamentos ,
			ArrayList<CadastroContrato.CadastroTransferenciaPagamentoContratual> transferencias_remetente ,
			ArrayList<CadastroContrato.CadastroTransferenciaPagamentoContratual> transferencias_destinatario 
			) {
		
		NumberFormat z = NumberFormat.getNumberInstance();

		Locale ptBr = new Locale("pt", "BR");

		
		//criarParagrafo(1);
		//linhas x colunas
		int num_linhas_pagamentos = pagamentos.size();
		int num_linhas_trans_remetente = transferencias_remetente.size();
		int num_linhas_trans_destinatario = transferencias_destinatario.size();
		int num_total_linhas = num_linhas_pagamentos + num_linhas_trans_remetente + num_linhas_trans_destinatario + 1;

		    XWPFTable table = document_global.createTable(num_total_linhas , 5);

		    setTableAlign(table, ParagraphAlignment.CENTER);
		    XWPFTableRow tableRowOne = table.getRow(0);
		    tableRowOne.getCell(0).removeParagraph(0);
		   
		    

		    
            XWPFParagraph paragraph = tableRowOne.getCell(0).addParagraph();

            criarParagrafoTabela(paragraph,"Data", false );
           
           
			        tableRowOne = table.getRow(0);
				    tableRowOne.getCell(1).removeParagraph(0);
                    paragraph = tableRowOne.getCell(1).addParagraph();
		            criarParagrafoTabela(paragraph,"Descrição", false );
		            
		            tableRowOne = table.getRow(0);
				    tableRowOne.getCell(2).removeParagraph(0);
                    paragraph = tableRowOne.getCell(2).addParagraph();
		            criarParagrafoTabela(paragraph,"Valor: ", false );
		            
		            
		            tableRowOne = table.getRow(0);
				    tableRowOne.getCell(3).removeParagraph(0);
                    paragraph = tableRowOne.getCell(3).addParagraph();
		            criarParagrafoTabela(paragraph,"Depositante: ", false );
		            
		            
		            tableRowOne = table.getRow(0);
				    tableRowOne.getCell(4).removeParagraph(0);
                    paragraph = tableRowOne.getCell(4).addParagraph();
		            criarParagrafoTabela(paragraph,"Favorecido", false );
		            
		        	int i_global = 1;

		 		    for(CadastroContrato.CadastroPagamentoContratual local:pagamentos ) {
		 		    	
		 		    //	CadastroContrato.CadastroPagamentoContratual local = pagamentos.get(indice);
		 		    	
		 		    	//celula data
		 		       tableRowOne = table.getRow(i_global);
					    tableRowOne.getCell(0).removeParagraph(0);
	                   paragraph = tableRowOne.getCell(0).addParagraph();
			            criarParagrafoTabela(paragraph,local.getData_pagamento(), false );
			 		    
			            //celula descricao
			 		      tableRowOne = table.getRow(i_global);
						    tableRowOne.getCell(1).removeParagraph(0);
		                    paragraph = tableRowOne.getCell(1).addParagraph();
				            criarParagrafoTabela(paragraph,"Pagamento", false );
				            
				            //celula valor
				           String valorString = NumberFormat.getCurrencyInstance(ptBr)
									.format(local.getValor_pagamento());
				 		      tableRowOne = table.getRow(i_global);
							    tableRowOne.getCell(2).removeParagraph(0);
			                    paragraph = tableRowOne.getCell(2).addParagraph();
					            criarParagrafoTabela(paragraph,valorString, false );
					            
					            //celula depositante
						        
					            GerenciarBancoClientes cliente = new GerenciarBancoClientes();
					            CadastroCliente depositante = cliente.getCliente(local.getId_depositante());
					            CadastroCliente favorecido = cliente.getCliente(local.getId_favorecido());

					            String nome_depositante = "", nome_favorecido = "";
					            
					            if(depositante.getTipo_pessoa() == 0)
					            	nome_depositante = depositante.getNome_empresarial();
					            else
						            nome_depositante = depositante.getNome_fantaia();
					            
					            if(favorecido.getTipo_pessoa() == 0)
					            	nome_favorecido = favorecido.getNome_empresarial();
					            else
					            	nome_favorecido = favorecido.getNome_fantaia();

					            
						 		      tableRowOne = table.getRow(i_global);
									    tableRowOne.getCell(3).removeParagraph(0);
					                    paragraph = tableRowOne.getCell(3).addParagraph();
							            criarParagrafoTabela(paragraph,nome_depositante, false );
							            
							            //celula favorecido
								        
							 		      tableRowOne = table.getRow(i_global);
										    tableRowOne.getCell(4).removeParagraph(0);
						                    paragraph = tableRowOne.getCell(4).addParagraph();
								            criarParagrafoTabela(paragraph,nome_favorecido, false );
		 		    	
		 		    	
		 		    	
		 		    i_global++;
		 		
		 		    }
		 		    
		 		   for(CadastroContrato.CadastroTransferenciaPagamentoContratual local : transferencias_remetente) {
		 		    	
		 		    	//CadastroContrato.CadastroTransferenciaPagamentoContratual local = transferencias_remetente.get(indice);
		 		    	
		 		    	//celula data
		 		       tableRowOne = table.getRow(i_global);
					    tableRowOne.getCell(0).removeParagraph(0);
	                   paragraph = tableRowOne.getCell(0).addParagraph();
			            criarParagrafoTabela(paragraph,local.getData(), false );
			 		    
			            //celula descricao
			 		      tableRowOne = table.getRow(i_global);
						    tableRowOne.getCell(1).removeParagraph(0);
		                    paragraph = tableRowOne.getCell(1).addParagraph();
				            criarParagrafoTabela(paragraph,"-Transferencia", false );
				            
				            //pega o pagamento
				            GerenciarBancoContratos gerenciar_contratos = new GerenciarBancoContratos();
				            CadastroContrato.CadastroPagamentoContratual pag = gerenciar_contratos.getPagamentoContratual(local.getId_pagamento_contratual());
				            
				            //celula valor
				           String valorString = NumberFormat.getCurrencyInstance(ptBr)
									.format(pag.getValor_pagamento());
				 		      tableRowOne = table.getRow(i_global);
							    tableRowOne.getCell(2).removeParagraph(0);
			                    paragraph = tableRowOne.getCell(2).addParagraph();
					            criarParagrafoTabela(paragraph,valorString, false );
					            
					            //celula depositante
						       //pegar codigo dos contratos
					            CadastroContrato favorecido = gerenciar_contratos.getContrato(local.getId_contrato_destinatario());
					          
						 		      tableRowOne = table.getRow(i_global);
									    tableRowOne.getCell(3).removeParagraph(0);
					                    paragraph = tableRowOne.getCell(3).addParagraph();
							            criarParagrafoTabela(paragraph,"Este contrato " + novo_contrato.getCodigo(), false );
							            
							            //celula favorecido
								        
							 		      tableRowOne = table.getRow(i_global);
										    tableRowOne.getCell(4).removeParagraph(0);
						                    paragraph = tableRowOne.getCell(4).addParagraph();
								            criarParagrafoTabela(paragraph,favorecido.getCodigo(), false );
		 		    	
								            i_global++;
		 		    }
		 		   
		 		   for(CadastroContrato.CadastroTransferenciaPagamentoContratual local : transferencias_destinatario) {
		 		    	
		 		    	//CadastroContrato.CadastroTransferenciaPagamentoContratual local = transferencias_remetente.get(indice);
		 		    	
		 		    	//celula data
		 		       tableRowOne = table.getRow(i_global);
					    tableRowOne.getCell(0).removeParagraph(0);
	                   paragraph = tableRowOne.getCell(0).addParagraph();
			            criarParagrafoTabela(paragraph,local.getData(), false );
			 		    
			            //celula descricao
			 		      tableRowOne = table.getRow(i_global);
						    tableRowOne.getCell(1).removeParagraph(0);
		                    paragraph = tableRowOne.getCell(1).addParagraph();
				            criarParagrafoTabela(paragraph,"+Transferencia", false );
				            
				            //pega o pagamento
				            GerenciarBancoContratos gerenciar_contratos = new GerenciarBancoContratos();
				            CadastroContrato.CadastroPagamentoContratual pag = gerenciar_contratos.getPagamentoContratual(local.getId_pagamento_contratual());
				            
				            //celula valor
				           String valorString = NumberFormat.getCurrencyInstance(ptBr)
									.format(pag.getValor_pagamento());
				 		      tableRowOne = table.getRow(i_global);
							    tableRowOne.getCell(2).removeParagraph(0);
			                    paragraph = tableRowOne.getCell(2).addParagraph();
					            criarParagrafoTabela(paragraph,valorString, false );
					            
					            //celula depositante
						       //pegar codigo dos contratos
					            CadastroContrato depositante = gerenciar_contratos.getContrato(local.getId_contrato_remetente());
					          
						 		      tableRowOne = table.getRow(i_global);
									    tableRowOne.getCell(3).removeParagraph(0);
					                    paragraph = tableRowOne.getCell(3).addParagraph();
							            criarParagrafoTabela(paragraph,depositante.getCodigo(),  false );
							            
							            //celula favorecido
								        
							 		      tableRowOne = table.getRow(i_global);
										    tableRowOne.getCell(4).removeParagraph(0);
						                    paragraph = tableRowOne.getCell(4).addParagraph();
								            criarParagrafoTabela(paragraph, "Este contrato " + novo_contrato.getCodigo(),true );
		 		    	
								            i_global++;
		 		    }
		 		    
		
	}
	
	
	
	public void criarTabelaCarregamentos(ArrayList<CadastroContrato.Carregamento> carregamentos ) {
		XWPFParagraph par = document_global.createParagraph();

		NumberFormat z = NumberFormat.getNumberInstance();

		Locale ptBr = new Locale("pt", "BR");

		
		//criarParagrafo(1);
		//linhas x colunas
		int num_linhas_carregamentos = carregamentos.size() + 1;
		

		    XWPFTable table = document_global.createTable(num_linhas_carregamentos , 9);

		    setTableAlign(table, ParagraphAlignment.CENTER);
		    XWPFTableRow tableRowOne = table.getRow(0);
		    tableRowOne.getCell(0).removeParagraph(0);
		   
		    

		    
            XWPFParagraph paragraph = tableRowOne.getCell(0).addParagraph();

            criarParagrafoTabela(paragraph,"Contrato", false );
           
           
			        tableRowOne = table.getRow(0);
				    tableRowOne.getCell(1).removeParagraph(0);
                    paragraph = tableRowOne.getCell(1).addParagraph();
		            criarParagrafoTabela(paragraph,"Cliente", false );
		            
		            tableRowOne = table.getRow(0);
				    tableRowOne.getCell(2).removeParagraph(0);
                    paragraph = tableRowOne.getCell(2).addParagraph();
		            criarParagrafoTabela(paragraph,"Vendedor ", false );
		            
		            
		            tableRowOne = table.getRow(0);
				    tableRowOne.getCell(3).removeParagraph(0);
                    paragraph = tableRowOne.getCell(3).addParagraph();
		            criarParagrafoTabela(paragraph,"Transportador: ", false );
		            
		            
		            tableRowOne = table.getRow(0);
				    tableRowOne.getCell(4).removeParagraph(0);
                    paragraph = tableRowOne.getCell(4).addParagraph();
		            criarParagrafoTabela(paragraph,"Veiculo", false );
		            
		            tableRowOne = table.getRow(0);
				    tableRowOne.getCell(5).removeParagraph(0);
                    paragraph = tableRowOne.getCell(5).addParagraph();
		            criarParagrafoTabela(paragraph,"Produto", false );
		            
		            tableRowOne = table.getRow(0);
				    tableRowOne.getCell(6).removeParagraph(0);
                    paragraph = tableRowOne.getCell(6).addParagraph();
		            criarParagrafoTabela(paragraph,"Peso Real", false );
		            
		            tableRowOne = table.getRow(0);
				    tableRowOne.getCell(7).removeParagraph(0);
                    paragraph = tableRowOne.getCell(7).addParagraph();
		            criarParagrafoTabela(paragraph,"Nota Fiscal", false );
		            
		            tableRowOne = table.getRow(0);
				    tableRowOne.getCell(8).removeParagraph(0);
                    paragraph = tableRowOne.getCell(8).addParagraph();
		            criarParagrafoTabela(paragraph,"Peso NF", false );
		            
		           int i = 1;
		            for(CadastroContrato.Carregamento carregamento : carregamentos) {
		            	
		            	     GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
		        			// pegar dados do contrato
		        			CadastroContrato contrato_destinatario = gerenciar.getContrato(carregamento.getId_contrato());

		        			// pegar cliente
		        			GerenciarBancoClientes gerenciar_clientes = new GerenciarBancoClientes();
		        			CadastroCliente cliente_carregamento = gerenciar_clientes.getCliente(carregamento.getId_cliente());
		        			String nome_cliente;
		        			if (cliente_carregamento.getTipo_pessoa() == 0) {
		        				// pessoa fisica
		        				nome_cliente = cliente_carregamento.getNome_empresarial();
		        			} else {
		        				nome_cliente = cliente_carregamento.getNome_fantaia();
		        			}
		        			
		        			
		        			// pegar vendedor
		        						CadastroCliente vendedor_carregamento = gerenciar_clientes.getCliente(carregamento.getId_vendedor());
		        						String nome_vendedor;
		        						if (vendedor_carregamento.getTipo_pessoa() == 0) {
		        							// pessoa fisica
		        							nome_vendedor = vendedor_carregamento.getNome_empresarial();
		        						} else {
		        							nome_vendedor = vendedor_carregamento.getNome_fantaia();
		        						}

		        			// pegar transportador e veiculo
		        			CadastroCliente transportador_carregamento = gerenciar_clientes
		        					.getCliente(carregamento.getId_transportador());
		        			CadastroCliente.Veiculo veiculo_carregamento = null;
		        			for (CadastroCliente.Veiculo veiculo : transportador_carregamento.getVeiculos()) {
		        				if (veiculo.getId_veiculo() == carregamento.getId_veiculo()) {
		        					veiculo_carregamento = veiculo;
		        					break;
		        				}
		        			}

		        			// pegar o produto
		        			GerenciarBancoProdutos gerenciar_produtos = new GerenciarBancoProdutos();
		        			CadastroProduto produto_carregamento = gerenciar_produtos.getProduto(carregamento.getId_produto());

		        			// pegar a nota
		        			ManipularNotasFiscais manipular = new ManipularNotasFiscais("");
		        			CadastroNFe nota = manipular.getNotaFiscal(carregamento.getCodigo_nota_fiscal());

		        			// definir peso carregamento
		        			double peso_carregado = carregamento.getPeso_real_carga();
		        			// definir peso da nota
		        			double peso_nota = Double.parseDouble(nota.getQuantidade().replace(".", "").replace(",", "."));
		        			// definir peso restante para nota
		        			double peso_nota_restante = peso_carregado - peso_nota;

		        			/*modelo_carregamentos.addRow(new Object[] { carregamento.getId_carregamento(), carregamento.getData(),
		        					contrato_destinatario.getCodigo(), nome_cliente, nome_vendedor,
		        					transportador_carregamento.getNome() + " " + transportador_carregamento.getSobrenome(),
		        					veiculo_carregamento.getPlaca_trator(), produto_carregamento.getNome_produto(),
		        					z.format(peso_carregado) + " Kg", z.format(peso_nota) + " Kg", z.format(peso_nota_restante) + " Kg",
		        					carregamento.getCodigo_nota_fiscal()

		        			});*/
		        		

		            	
		            	  tableRowOne = table.getRow(i);
						    tableRowOne.getCell(0).removeParagraph(0);
		                   paragraph = tableRowOne.getCell(0).addParagraph();
				            criarParagrafoTabela(paragraph,contrato_destinatario.getCodigo(), false );
				 		    
				 		      tableRowOne = table.getRow(i);
							    tableRowOne.getCell(1).removeParagraph(0);
			                    paragraph = tableRowOne.getCell(1).addParagraph();
					            criarParagrafoTabela(paragraph,nome_cliente, false );
						      
					            
					            tableRowOne = table.getRow(i);
							    tableRowOne.getCell(2).removeParagraph(0);
			                    paragraph = tableRowOne.getCell(2).addParagraph();
					            criarParagrafoTabela(paragraph,nome_vendedor, false );
					            
					            
					            tableRowOne = table.getRow(i);
							    tableRowOne.getCell(3).removeParagraph(0);
			                    paragraph = tableRowOne.getCell(3).addParagraph();
					            criarParagrafoTabela(paragraph,transportador_carregamento.getNome() + " " + transportador_carregamento.getSobrenome(), false );
					            
					            
					            tableRowOne = table.getRow(i);
							    tableRowOne.getCell(4).removeParagraph(0);
			                    paragraph = tableRowOne.getCell(4).addParagraph();
					            criarParagrafoTabela(paragraph,veiculo_carregamento.getPlaca_trator(), false );

					            
					            tableRowOne = table.getRow(i);
							    tableRowOne.getCell(5).removeParagraph(0);
			                    paragraph = tableRowOne.getCell(5).addParagraph();
					            criarParagrafoTabela(paragraph,produto_carregamento.getNome_produto(), false );

					            

								
					            tableRowOne = table.getRow(i);
							    tableRowOne.getCell(6).removeParagraph(0);
			                    paragraph = tableRowOne.getCell(6).addParagraph();
					            criarParagrafoTabela(paragraph,z.format(peso_carregado) + " Kg", false );
					            
					            
					            tableRowOne = table.getRow(i);
							    tableRowOne.getCell(7).removeParagraph(0);
			                    paragraph = tableRowOne.getCell(7).addParagraph();
					            criarParagrafoTabela(paragraph,carregamento.getCodigo_nota_fiscal(), false );

					           
					            tableRowOne = table.getRow(i);
					 			tableRowOne.getCell(8).removeParagraph(0);
					            paragraph = tableRowOne.getCell(8).addParagraph();
					 		    criarParagrafoTabela(paragraph, z.format(peso_nota), false );
				 		    
		            	i++;
		            }

		
	}
	
	public void setTableAlign(XWPFTable table,ParagraphAlignment align) {
	    CTTblPr tblPr = table.getCTTbl().getTblPr();
	    CTJc jc = (tblPr.isSetJc() ? tblPr.getJc() : tblPr.addNewJc());
	    STJc.Enum en = STJc.Enum.forInt(align.getValue());
	    jc.setVal(en);
	}
	
	public void adicionarTextoParagrafoAtual(String texto, boolean negrito) {
		XWPFRun run = paragrafo_atual.createRun();
		run.setText(texto);
		run.setColor("000000");
		run.setBold(negrito);
		run.setFontFamily("Times New Roman");
		run.setFontSize(10);
		

	}
	
	
	public void saltarLinhaParagrafo( ) {
		XWPFRun corretortitleRun = paragrafo_atual.createRun();
		corretortitleRun.addBreak();

	}
	
	
	
	
	


	public void getDadosGlobais() {
		// gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		GerenciadorLog = dados.getGerenciadorLog();
		configs_globais = dados.getConfigs_globais();

		// usuario logado
		login = dados.getLogin();

	}
	

	public void adicionarData() {
		//data contrato
				String data_extenso = "";
				
				SimpleDateFormat formato_data = new SimpleDateFormat("dd/MM/yyyy");
				try {
					Date data_formatada = formato_data.parse(novo_contrato.getData_contrato());
				    Date data = new Date();
				    Locale local = new Locale("pt", "BR");
				    DateFormat formato = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", local);
				    
				    LocalDate data_local = data_formatada.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				    DayOfWeek dia_da_semana = data_local.getDayOfWeek();
				    
				    //dia da semna
				    data_extenso = dia_da_semana.getDisplayName(TextStyle.FULL, local) + ", " + formato.format(data_formatada);
				
				} catch (ParseException e) {
					System.out.println("Nao foi possivel converter a data");
					e.printStackTrace();
				}
				
				substituirTexto("Guarda-Mor MG " + data_extenso);
	}
	
	
	
	public boolean criarArquivos(String nome_arquivo, String caminh_completo_salvar_no_hd, String caminho_completo_salvar_no_banco_dados, String caminho_completo_diretorio_arquivo) {
		
		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream (caminh_completo_salvar_no_hd + ".docx");
			
			//novo_contrato.setCaminho_arquivo(url.toString());
			
			document_global.write(outputStream);
			 //  workbook_aberto.close();
  				outputStream.close();

			
				
				//workbook.close();
			//Converter e salvar em pdf
             //criar pdf
             ConverterPdf converter_pdf = new ConverterPdf();
           //  String url = converter_pdf.excel_pdf_file(contrato_alterado);
           //TelaVizualizarPdf  vizualizar =  new TelaVizualizarPdf(url);
             if (converter_pdf.word_pdf_file(caminh_completo_salvar_no_hd)) {
            	 System.out.println("Arquivo pdf convertido e salvo!");
            	
            	 
            	 System.out.println("caminho para salvar na nuvem: " + caminh_completo_salvar_no_hd);
            	 novo_contrato.setCaminho_arquivo(caminho_completo_salvar_no_banco_dados + ".pdf") ;
            	 novo_contrato.setCaminho_diretorio_contrato(caminho_completo_diretorio_arquivo);
            	 //salvar no drobox
            	 Nuvem nuvem = new Nuvem();
            	 nuvem.abrir();
                 nuvem.testar();
                
                boolean retorno = nuvem.carregar(caminh_completo_salvar_no_hd + ".pdf", nome_arquivo + ".pdf");
                 if(retorno) {
                	 System.out.println("Arquivo salvo na nuvem");
                	 novo_contrato.setNome_arquivo(nome_arquivo + ".pdf" );
                 }
        	    // System.out.println("link: " + nuvem.getUrlArquivo("/contratos));
     			return true;

             }else {
            	 return false;
             }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("erro ao criar o arquivo fisico, erro: " + e.getMessage());
			e.printStackTrace();
			
			return false;
		} 
	}
	

}
