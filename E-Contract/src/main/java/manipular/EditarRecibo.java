package main.java.manipular;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
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

import javax.swing.JOptionPane;
import javax.swing.text.MaskFormatter;

import org.apache.pdfbox.printing.Orientation;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.model.XWPFParagraphDecorator;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
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
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

import com.itextpdf.text.PageSize;

import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroContrato.CadastroPagamentoContratual;
import main.java.cadastros.CadastroContrato.Recebimento;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroModelo;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroSafra;
import main.java.cadastros.CondicaoPagamento;
import main.java.cadastros.ContaBancaria;
import main.java.cadastros.FinanceiroPagamento;
import main.java.cadastros.InstituicaoBancaria;
import main.java.cadastros.Lancamento;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoCondicaoPagamentos;
import main.java.conexaoBanco.GerenciarBancoInstituicaoBancaria;
import main.java.outros.DadosGlobais;
import main.java.outros.GetData;
import main.java.tratamento_proprio.Log;
import main.java.views_personalizadas.TelaEmEspera;

public class EditarRecibo {

	private CadastroModelo modelo;
	private String path;

	FinanceiroPagamento pagamento;
	private TelaEmEspera telaInformacoes;
	private CadastroCliente compradores[], vendedores[], corretores[];

	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private String servidor_unidade;
	private XWPFDocument document_global;
	private XWPFParagraph paragrafo_atual ;
	private String texto;
	private String data_recibo;
	private ArrayList<CadastroCliente> recebedores_intermediarios;

	public EditarRecibo(FinanceiroPagamento _pagamento, String _texto, String _data_recibo, ArrayList<CadastroCliente> clientes) {
		getDadosGlobais();
		servidor_unidade = configs_globais.getServidorUnidade();
		this.pagamento = _pagamento;
		this.data_recibo = _data_recibo;
		this.texto = _texto;
		this.recebedores_intermediarios = clientes;
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
	
	public ByteArrayOutputStream preparar() {
		

		  
		// cria o paragrafo do rodape
		XWPFParagraph rodape = document_global.createParagraph();
		rodape.setAlignment(ParagraphAlignment.LEFT);
		
		//criar o paragrafo do titulo
		XWPFParagraph title = document_global.createParagraph();
		title.setAlignment(ParagraphAlignment.CENTER);
		
		Locale ptBr = new Locale("pt", "BR");
		String valorString = NumberFormat.getCurrencyInstance(ptBr).format(pagamento.getValor());
		
		XWPFRun titleRun = title.createRun();
		titleRun.setText("RECIBO: " + new GetData().getAnoAtual() + "/" + pagamento.getId_pagamento() + " - " + valorString);
		titleRun.setColor("000000");
		titleRun.setBold(true);
		titleRun.setUnderline(UnderlinePatterns.SINGLE);
		titleRun.setFontFamily("Arial");
		titleRun.setFontSize(14);

		// numero do contrato

		XWPFParagraph num_contrato = document_global.createParagraph();
		num_contrato.setAlignment(ParagraphAlignment.CENTER);

		String assinatura_recebedor= "";
		CadastroCliente recebedor = null ;
		CadastroCliente devedor ;
		adicionarTraco(true, 2);

		//recebedor
		if(pagamento.getTipo_recebedor() == 0) {
			//ib
			if(pagamento.getId_recebedor() > 0) {
				InstituicaoBancaria ib = new GerenciarBancoInstituicaoBancaria().getInstituicaoBancaria(pagamento.getId_recebedor());
				if(ib != null) {
					if(ib.getId_cliente() > 0) {
						CadastroCliente cli = new GerenciarBancoClientes().getCliente(ib.getId_cliente());
						if(cli != null) {

							adicionarParte(0,cli);
							recebedor = cli;


						}
					
					}
					
					
				}
			}
			
		}else if(pagamento.getTipo_recebedor() == 1) {
			if(pagamento.getId_recebedor() > 0) {
				CadastroCliente cli = new GerenciarBancoClientes().getCliente(pagamento.getId_recebedor());
				if(cli != null) {
					

					adicionarParte(0,cli);
					recebedor = cli;


				}
			}
		}
		adicionarTraco(true, 2);

		String assinatura_pagador= "";
		String nome_pagador = "";
		//pagador
		if(pagamento.getTipo_pagador() == 0) {
			//ib
			if(pagamento.getId_pagador() > 0) {
				InstituicaoBancaria ib = new GerenciarBancoInstituicaoBancaria().getInstituicaoBancaria(pagamento.getId_pagador());
				if(ib != null) {
					assinatura_pagador = ib.getNome_instituicao_bancaria();
					CadastroCliente cli = new GerenciarBancoClientes().getCliente(ib.getId_cliente());
					if(cli != null) {

						adicionarParte(1,cli);
						devedor = cli;

					}
				}
			}
			
		}else if(pagamento.getTipo_pagador() == 1) {
			if(pagamento.getId_pagador() > 0) {
				CadastroCliente cli = new GerenciarBancoClientes().getCliente(pagamento.getId_pagador());
				if(cli != null) {
					

					adicionarParte(1,cli);
					devedor = cli;


				}
			}
		}
		adicionarTraco(true, 2);

		String forma_pagamento = "";
		GerenciarBancoCondicaoPagamentos gerenciar = new GerenciarBancoCondicaoPagamentos();
		if (pagamento.getId_condicao_pagamento() > 0) {
			CondicaoPagamento condicao = gerenciar.getCondicaoPagamento(pagamento.getId_condicao_pagamento());

			if (condicao != null)
				forma_pagamento = condicao.getNome_condicao_pagamento();
		}
		texto = texto.replace("[forma_pagamento]", forma_pagamento);

		texto = texto.replace("[data_pagamento]", pagamento.getData_pagamento());
		texto = texto.replace("[descricao_pagamento]", pagamento.getDescricao());
		
		
		
		
		
		
		if(recebedores_intermediarios != null) {
			if(recebedores_intermediarios.size() > 0) {
			if(recebedores_intermediarios.size() == 1) {
				
				texto = texto + "\nA importância desse recibo foi paga ao [RECEBEDOR] através de um [RECEBEDOR INTERMEDIÁRIO], descrito a seguir:\n";
				texto += "[_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ __ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _]\n";

				for(CadastroCliente recebedor_inter : recebedores_intermediarios) {
					texto = texto + adicionarRecebedorIntermediario(recebedor_inter);
					
				}
				texto += "[_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ __ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _]\n";

			}
			if(recebedores_intermediarios.size() > 1) {
				
				texto = texto + "A importância desse recibo foi paga ao [RECEBEDOR] através de [RECEBEDORES INTERMEDIÁRIOS], descritos a seguir:\n";
				texto += "[_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ __ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _]\n";

				for(CadastroCliente recebedor_inter : recebedores_intermediarios) {
					texto = texto + adicionarRecebedorIntermediario(recebedor_inter) + "\n";
					texto += "[_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ __ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _]\n";


				}
				
			}
			
			}
		}

        substituirTexto(texto);
	
		
	
		//local e data
		adicionarData();
		
		//campos de assinatura
		
		if(recebedor.getTipo_pessoa() == 0) {
		adicionarCamposAssinaturas(recebedor.getNome_empresarial(), 0, recebedor.getCpf());
		}else if(recebedor.getTipo_pessoa() == 1) {
			adicionarCamposAssinaturas(recebedor.getNome_fantaia(),1, recebedor.getCnpj());

		}
		
		if(recebedores_intermediarios != null) {
			if(recebedores_intermediarios.size() > 0) {
				
				for(CadastroCliente recebedor_inter : recebedores_intermediarios) {
					if(recebedor_inter.getTipo_pessoa() == 0) {
						adicionarCamposAssinaturas(recebedor_inter.getNome_empresarial(), 0, recebedor_inter.getCpf());
						}else if(recebedor_inter.getTipo_pessoa() == 1) {
							adicionarCamposAssinaturas(recebedor_inter.getNome_fantaia(),1, recebedor_inter.getCnpj());

						}					
				}
				
			}
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
			hfPolicy.createHeader(XWPFHeaderFooterPolicy.DEFAULT, pars);
			


			
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
                if(palavra.equals("valor_pagamento_decimal")) {
                	 
			       	  	Locale ptBr = new Locale("pt", "BR");
			       	   	String valorString = NumberFormat.getCurrencyInstance(ptBr).format((pagamento.getValor()));
			       	   	System.out.println(valorString);   
        		
    		        adicionarTextoParagrafoAtual(valorString + " ", true);
                }
                else if(palavra.equals("valor_pagamento_extenso")){
                	
                	String valor_extenso = new PorExtenso(pagamento.getValor()).toString();
    		        adicionarTextoParagrafoAtual(palavra.replace("valor_pagamento_extenso" ,valor_extenso.toLowerCase() + ""), false);

                }else {
		        adicionarTextoParagrafoAtual(palavra + " ", false);
                }
			}

		}
		}
        
        
	}
	
	public void criarParagrafo(int alinhamento) {
		XWPFParagraph paragrafo = document_global.createParagraph();
	
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
					Date data_formatada = formato_data.parse(data_recibo);
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
				
				substituirTexto("\n\nPor ser verdade, firma-se o presente.");

				substituirTexto("Guarda-Mor MG " + data_extenso);
	}
	
	public void adicionarCamposAssinaturas(String assinatura_recebedor, int tipo_identificacao, String identificacao) {
		
			  criarParagrafo(0);

		

			String separados [] = assinatura_recebedor.split(" ");
			String nome_assinatura_negrito = "";
			
			for(String palavra : separados) {
				nome_assinatura_negrito += "[" + palavra + "]" + " ";
			}
			
			if(tipo_identificacao == 0) {
				 String cpf = ""; 
			       try{

			           MaskFormatter formater_cpf = new MaskFormatter("###.###.###-##");
			           formater_cpf.setValueContainsLiteralCharacters(false);
			           cpf = formater_cpf.valueToString(identificacao);


			        }catch (Exception e){}
				
				
				substituirTexto("[_______________________________________________________________]                                                                                         "
					+ "["  + nome_assinatura_negrito.toUpperCase() + "]\nCPF: [" + cpf +"]");
			}else if(tipo_identificacao == 1) {
				
				String cnpj = ""; 
			       try{

			           MaskFormatter formater_cnpj = new MaskFormatter("##.###.###/####-##");
			           formater_cnpj.setValueContainsLiteralCharacters(false);
			           cnpj = formater_cnpj.valueToString(identificacao);


			        }catch (Exception e){}
				substituirTexto("[_______________________________________________________________]                                                                                         "
						+ "["  + nome_assinatura_negrito.toUpperCase() + "]\nCNPJ: [" + cnpj + "]");
			}


	}
	
	
	
	public boolean criarArquivo(String caminho_completo) {
		
		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream (caminho_completo + ".docx");
			
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
             if (converter_pdf.word_pdf_file(caminho_completo)) {
            	 System.out.println("Arquivo pdf convertido e salvo!");
            	
            	 
            	
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
	

	public void adicionarParte(int flag, CadastroCliente cliente) {

		XWPFParagraph parte = document_global.createParagraph();
		parte.setAlignment(ParagraphAlignment.LEFT);

	
		XWPFRun titlerun = parte.createRun();
		titlerun.setColor("000000");
		titlerun.setBold(true);
		titlerun.setFontFamily("Times New Roman");
		titlerun.setFontSize(10);
		
		if(flag == 0)
			titlerun.setText("RECEBEDOR:");
		else if(flag == 1)
			titlerun.setText("DEVEDOR:");

		titlerun.addBreak();
		
		String nome_corretor = "";
		if(cliente.getTipo_pessoa() == 0) {
			//pessoa fisica
			nome_corretor = cliente.getNome_empresarial().toUpperCase().trim();
		}else {
			nome_corretor = cliente.getNome_fantaia().toUpperCase().trim();

		}
		
		XWPFRun corretorNomerun = parte.createRun();
		corretorNomerun.setText( nome_corretor);
		corretorNomerun.setColor("000000");
		corretorNomerun.setFontFamily("Times New Roman");
		corretorNomerun.setFontSize(10);
		corretorNomerun.setBold(true);

	
		XWPFRun adicional1run = parte.createRun();
		adicional1run.setColor("000000");
		adicional1run.setFontFamily("Times New Roman");
		adicional1run.setFontSize(10);
		adicional1run.setBold(true);
		
	try {
			
			String ie = "";
	           MaskFormatter formater_ie = new MaskFormatter("#########.##-##");
	           formater_ie.setValueContainsLiteralCharacters(false);
	           ie = formater_ie.valueToString(cliente.getIe());
			
	           adicional1run.setText(", Inscrição Estadual: " + ie + " ");
		}catch(Exception e) {
		}
		
	
	XWPFRun ocupacaoRun = parte.createRun();
	ocupacaoRun.setColor("000000");
	ocupacaoRun.setFontFamily("Times New Roman");
	ocupacaoRun.setFontSize(10);
	ocupacaoRun.setBold(false);
	
	
		try {
			ocupacaoRun.setText (", " + cliente.getOcupacao().toUpperCase().trim() );
		}catch(Exception e) {
		}
		

	
		XWPFRun enderecoCorretorrun = parte.createRun();
		enderecoCorretorrun.setColor("000000");
		enderecoCorretorrun.setFontFamily("Times New Roman");
		enderecoCorretorrun.setFontSize(10);
		enderecoCorretorrun.setBold(true);
		enderecoCorretorrun.setText(" , residente no endereço " + cliente.getRua().toUpperCase().trim() + ", nº " + cliente.getNumero() +  ", Bairro: " + cliente.getBairro().toUpperCase().trim());

		XWPFRun adicional2run = parte.createRun();
		adicional2run.setColor("000000");
		adicional2run.setFontFamily("Times New Roman");
		adicional2run.setFontSize(10);
		adicional2run.setBold(false);
		adicional2run.setText(", na Cidade de ");

		XWPFRun cidadeRun = parte.createRun();
		cidadeRun.setColor("000000");
		cidadeRun.setFontFamily("Times New Roman");
		cidadeRun.setFontSize(10);
		cidadeRun.setBold(true);
		cidadeRun.setText(cliente.getCidade().toUpperCase().trim());
		
		XWPFRun infoEstadoRun = parte.createRun();
		infoEstadoRun.setColor("000000");
		infoEstadoRun.setFontFamily("Times New Roman");
		infoEstadoRun.setFontSize(10);
		infoEstadoRun.setBold(false);
		infoEstadoRun.setText(" - Estado de ");
		
		XWPFRun estadoRUn = parte.createRun();
		estadoRUn.setColor("000000");
		estadoRUn.setFontFamily("Times New Roman");
		estadoRUn.setFontSize(10);
		estadoRUn.setBold(true);
		estadoRUn.setText(cliente.getUf().toUpperCase());

		XWPFRun adicional3ceprun = parte.createRun();
		adicional3ceprun.setColor("000000");
		adicional3ceprun.setFontFamily("Times New Roman");
		adicional3ceprun.setFontSize(10);
		adicional3ceprun.setBold(false);
		adicional3ceprun.setText(" CEP: ");

		XWPFRun cepRun = parte.createRun();
		cepRun.setColor("000000");
		cepRun.setFontFamily("Times New Roman");
		cepRun.setFontSize(10);
		cepRun.setBold(true);
		
		 String cep = ""; 
	       try{

	           MaskFormatter formater_cep = new MaskFormatter("#####-###");
	           formater_cep.setValueContainsLiteralCharacters(false);
	           cep = formater_cep.valueToString(cliente.getCep().replaceAll("[^0-9]", ""));


	        }catch (Exception e){}
		
		cepRun.setText(cep);

		
		XWPFRun adicionarCpfRun = parte.createRun();
		adicionarCpfRun.setColor("000000");
		adicionarCpfRun.setFontFamily("Times New Roman");
		adicionarCpfRun.setFontSize(10);
		adicionarCpfRun.setBold(false);
		
		XWPFRun cpfRun = parte.createRun();
		cpfRun.setColor("000000");
		cpfRun.setFontFamily("Times New Roman");
		cpfRun.setFontSize(10);
		cpfRun.setBold(true);
		
		if(cliente.getTipo_pessoa() == 0) {
			adicionarCpfRun.setText(", inscrito no CPF sob nº ");
			 String cpf = ""; 
		       try{

		           MaskFormatter formater_cpf = new MaskFormatter("###.###.###-##");
		           formater_cpf.setValueContainsLiteralCharacters(false);
		           cpf = formater_cpf.valueToString(cliente.getCpf());


		        }catch (Exception e){}
			cpfRun.setText(cpf);

		}else {
			adicionarCpfRun.setText(", inscrito no CNPJ sob nº ");
			String cnpj = ""; 
		       try{

		           MaskFormatter formater_cnpj = new MaskFormatter("##.###.###/####-##");
		           formater_cnpj.setValueContainsLiteralCharacters(false);
		           cnpj = formater_cnpj.valueToString(cliente.getCnpj());


		        }catch (Exception e){}
			cpfRun.setText(cnpj);

		}
		
	
	    

		

	
		XWPFRun finalRun = parte.createRun();
		finalRun.setColor("000000");
		finalRun.setFontFamily("Times New Roman");
		finalRun.setFontSize(10);
		finalRun.setBold(false);
		
		if(flag == 0)
			finalRun.setText(".  A pessoa supra indicada será doravante denominada “RECEBEDOR”.");
		else if(flag == 1)
			finalRun.setText(".  A pessoa supra indicada será doravante denominada “DEVEDOR”.");



	}
	
	

public String adicionarRecebedorIntermediario(CadastroCliente cliente) {
		
		
		
		String texto = "[";
		if(cliente.getTipo_pessoa() == 0) {
			//pessoa fisica
			texto = cliente.getNome_empresarial().toUpperCase().trim() + "]";
		}else {
			texto = cliente.getNome_fantaia().toUpperCase().trim() + "]";

		}
		
		try {
			
			String ie = "";
	           MaskFormatter formater_ie = new MaskFormatter("#########.##-##");
	           formater_ie.setValueContainsLiteralCharacters(false);
	           ie = formater_ie.valueToString(cliente.getIe());
			
			texto += (", Inscrição Estadual: [" + ie + "] ");
		}catch(Exception e) {
		}
		
		
	try {
		texto += (", " + cliente.getOcupacao().toUpperCase().trim() );
	}catch(Exception e) {
	}


		texto += " , residente no endereço [" + cliente.getRua().toUpperCase().trim() + "], nº [" + cliente.getNumero() +  "], Bairro: [" + cliente.getBairro().toUpperCase().trim();

		texto +=  ("], na Cidade de [");

		texto +=  cliente.getCidade().toUpperCase().trim() + "]";
		
		texto +=  (" - Estado de [");
		
		texto +=  (cliente.getUf().toUpperCase()) + "]";

		texto +=  (" CEP: [");

		
		
		 String cep = ""; 
	       try{

	           MaskFormatter formater_cep = new MaskFormatter("#####-###");
	           formater_cep.setValueContainsLiteralCharacters(false);
	           cep = formater_cep.valueToString(cliente.getCep().replaceAll("[^0-9]", ""));


	        }catch (Exception e){}
		
	       texto +=  (cep) + "]";

		
		
		
		if(cliente.getTipo_pessoa() == 0) {
			texto += (", inscrito no CPF sob nº [");
			 String cpf = ""; 
		       try{

		           MaskFormatter formater_cpf = new MaskFormatter("###.###.###-##");
		           formater_cpf.setValueContainsLiteralCharacters(false);
		           cpf = formater_cpf.valueToString(cliente.getCpf());


		        }catch (Exception e){}
		       texto += (cpf) + "]";

		}else {
			texto += (", inscrito no CNPJ sob nº [");
			String cnpj = ""; 
		       try{

		    	   
		           MaskFormatter formater_cnpj = new MaskFormatter("##.###.###/####-##");
		           formater_cnpj.setValueContainsLiteralCharacters(false);
		           cnpj = formater_cnpj.valueToString(cliente.getCnpj());


		        }catch (Exception e){}
		       texto += (cnpj) + "]";

		}
		
	
		
	
		return texto;

	}




}
