package manipular;

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

import cadastros.CadastroCliente;
import cadastros.CadastroContrato;
import cadastros.CadastroLogin;
import cadastros.CadastroModelo;
import outros.DadosGlobais;
import outros.GetData;
import tratamento_proprio.Log;
import views_personalizadas.TelaEmEspera;

public class EditarWord {

	private CadastroModelo modelo;
	private String path;

	CadastroContrato novo_contrato;
	private TelaEmEspera telaInformacoes;
	private CadastroCliente compradores[], vendedores[], corretores[];

	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private String servidor_unidade;
	private XWPFDocument document_global;
	private XWPFParagraph paragrafo_atual ;
	
	public EditarWord(CadastroModelo modelo, TelaEmEspera background, int tipoContrato, CadastroContrato contrato) {
		getDadosGlobais();
		this.modelo = modelo;
		this.telaInformacoes = background;
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
	
	public ByteArrayOutputStream preparar() {
		

		 compradores = novo_contrato.listaCompradores();
		  vendedores = novo_contrato.listaVendedores();
		  corretores = novo_contrato.listaCorretores();
		  
		// cria o paragrafo do rodape
		XWPFParagraph rodape = document_global.createParagraph();
		rodape.setAlignment(ParagraphAlignment.LEFT);
		


		
		//criar o paragrafo do titulo
		XWPFParagraph title = document_global.createParagraph();
		title.setAlignment(ParagraphAlignment.CENTER);

		XWPFRun titleRun = title.createRun();
		titleRun.setText("CONTRATO DE COMPRA E VENDA");
		titleRun.setColor("000000");
		titleRun.setBold(true);
		titleRun.setUnderline(UnderlinePatterns.SINGLE);
		titleRun.setFontFamily("Arial");
		titleRun.setFontSize(12);

		// numero do contrato

		XWPFParagraph num_contrato = document_global.createParagraph();
		num_contrato.setAlignment(ParagraphAlignment.CENTER);

		XWPFRun num_contratotitleRun = num_contrato.createRun();
		num_contratotitleRun.setText("n.º [" + novo_contrato.getCodigo() + "]");
		num_contratotitleRun.setColor("000000");
		num_contratotitleRun.setBold(true);
		num_contratotitleRun.setFontFamily("Arial");
		num_contratotitleRun.setFontSize(12);

		// traço de divisao de titulo
		adicionarTraco(true, 1);

		// adiciona informacoes de Corretorres
		for(CadastroCliente corretor : corretores) {
			if(corretor != null) {
			adicionarParte(0, corretor );

			// traço de divisao de corretor
			adicionarTraco(false, 0);
			}
		}


		// adicionar vendedores
		for(CadastroCliente vendedor : vendedores) {
			if(vendedor != null) {
			adicionarParte(1, vendedor );

			// traço de divisao de corretor
			adicionarTraco(false, 0);
			}

		}

		// adicionar compradores
		for(CadastroCliente comprador : compradores) {
			if(comprador != null) {
			adicionarParte(2, comprador );

			// traço de divisao de corretor
			adicionarTraco(false, 0);
			}

		}
		
		
		
		
		criarParagrafo(2);
		adicionarTextoParagrafoAtual("Têm as " , false);
		adicionarTextoParagrafoAtual("Partes" , true);
		adicionarTextoParagrafoAtual(" entre si justa e contratada a celebração do presente contrato de compra e venda (“" , false);
		adicionarTextoParagrafoAtual("Contrato" , true);
		adicionarTextoParagrafoAtual("”), o que fazem nos termos das seguintes cláusulas e condições:" , false);

		adicionarObjeto();
		adicionarEntrega(novo_contrato.getCliente_retirada());
		
		if(novo_contrato.getTipo_entrega() == 1) {
		adicionarClausula2_1();
		
		adicionarClausula3();
		
		substituirTexto("[3.1.] O [Vendedor] deverá iniciar a entrega do [Produto] ao [Comprador] imediatamente a partir do início da sua colheita. Na hipótese de, a partir do momento em que o [Produto] estiver apto a ser colhido, ser constatado o transporte ou entrega de qualquer quantidade do [Produto] a qualquer outro destino que não o [Local de Entrega], restará caracterizado seu desvio para terceiros, o que caracterizará inadimplemento contratual de pleno direito, de forma automática e independentemente de qualquer formalidade adicional.");
		
	}
		
		if(novo_contrato.getTipo_entrega() == 1) {
		substituirTexto("[4.] [Qualidade:] Quando de sua entrega pelo [Vendedor] ao [Comprador], o [Produto] objeto do presente [Contrato] deverá atender às especificações de qualidade indicadas na [Tabela 1] abaixo (“[Especificações]”). O [Comprador] poderá, a seu exclusivo critério, receber [Produto] que não atenda às [Especificações]. Nesta hipótese, o [Produto] entregue fora das [Especificações] estará sujeito aos descontos indicados na [Tabela] [1] abaixo (“[Descontos]”):");
	    }else {
			substituirTexto("[3.] [Qualidade:] Quando de sua entrega pelo [Vendedor] ao [Comprador], o [Produto] objeto do presente [Contrato] deverá atender às especificações de qualidade indicadas na [Tabela 1] abaixo (“[Especificações]”). O [Comprador] poderá, a seu exclusivo critério, receber [Produto] que não atenda às [Especificações]. Nesta hipótese, o [Produto] entregue fora das [Especificações] estará sujeito aos descontos indicados na [Tabela] [1] abaixo (“[Descontos]”):");

	    }
		
		substituirTexto("[Tabela] [1] – [Especificações] [e] [Descontos:]\r\n"
				+ "[Especificações] [Descontos]");
		
		
		criarTabela();
		
		if(novo_contrato.getTipo_entrega() == 1){
		substituirTexto("[4.1.] A adequação do [Produto] às [Especificações] será verificada pelo [Comprador] (diretamente ou por terceiros contratados), por meio de classificação (“[Classificação]”) a ser realizada no [Local de Entrega].\r\n"
				+
				"[4.2.] Fica desde já facultado ao [Vendedor] o acompanhamento da [Classificação]. Em caso de discordância, o Vendedor deverá manifestá-la no ato e por escrito, sob pena de preclusão.\r\n"
				+ 
				 "[4.3.] O [Comprador] recusará o recebimento de qualquer quantidade do [Produto] que não atenda às [Especificações]. Nesta hipótese, a quantidade de [Produto] que não atender às [Especificações] será considerada como não entregue pelo [Vendedor] ao [Comprador], devendo o [Vendedor] promover a sua substituição no prazo máximo de 2 (dois) dias contado a partir da [Classificação], respeitada, em qualquer caso, a [Data] [de] [Entrega].\r\n"
				+ 
				 "[4.4.] Promovida a [Classificação] do [Produto], caso o [Comprador] aceite receber qualquer quantidade de [Produto] que esteja fora das [Especificações], serão aplicados os [Descontos] estabelecidos na [Tabela] [1] da cláusula [5] sobre a [Quantidade] total do [Produto].\r\n"
				+ 
				 "[4.4.1.] Caso a aplicação dos [Descontos] prevista na cláusula [4.4] acima seja insuficiente para o cumprimento, pelo [Vendedor], de suas obrigações ao abrigo deste [Contrato], o [Vendedor] ficará obrigado entregar ao [Comprador] quantidade complementar de [Produto].\r\n"
				+ 
				 "[4.4.2.] Caso o [Vendedor] não cumpra o quanto disposto na cláusula [4.4.1] até a [Data] [de] [Entrega], o valor correspondente aos [Descontos] (a ser estabelecido pela multiplicação da quantidade de [Produto] objeto dos [Descontos] pelo [Preço]) será considerado crédito líquido, certo e exigível, de titularidade do Comprador contra o [Vendedor.]\r\n"
				+ 
				 "[5.] [Pesagem]:\r\n"
				+ "A fim de que seja aferida a quantidade de [Produto] efetivamente entregue pelo [Vendedor] ao [Comprador], a pesagem do [Produto] será realizada caminhão a caminhão, na balança rodoviária localizada no [Local] [de] [Entrega] ou, no local mais próximo possível."
				);
		}
		else {
			substituirTexto("[3.1.] A adequação do [Produto] às [Especificações] será verificada pelo [Comprador] (diretamente ou por terceiros contratados), por meio de classificação (“[Classificação]”) a ser realizada no [Local de Entrega].\r\n"
					+
					"[3.2.] Fica desde já facultado ao [Vendedor] o acompanhamento da [Classificação]. Em caso de discordância, o Vendedor deverá manifestá-la no ato e por escrito, sob pena de preclusão.\r\n"
					+ 
					 "[3.3.] O [Comprador] recusará o recebimento de qualquer quantidade do [Produto] que não atenda às [Especificações]. Nesta hipótese, a quantidade de [Produto] que não atender às [Especificações] será considerada como não entregue pelo [Vendedor] ao [Comprador], devendo o [Vendedor] promover a sua substituição no prazo máximo de 2 (dois) dias contado a partir da [Classificação], respeitada, em qualquer caso, a [Data] [de] [Entrega].\r\n"
					+ 
					 "[3.4.] Promovida a [Classificação] do [Produto], caso o [Comprador] aceite receber qualquer quantidade de [Produto] que esteja fora das [Especificações], serão aplicados os [Descontos] estabelecidos na [Tabela] [1] da cláusula [5] sobre a [Quantidade] total do [Produto].\r\n"
					+ 
					 "[3.4.1.] Caso a aplicação dos [Descontos] prevista na cláusula [4.4] acima seja insuficiente para o cumprimento, pelo [Vendedor], de suas obrigações ao abrigo deste [Contrato], o [Vendedor] ficará obrigado entregar ao [Comprador] quantidade complementar de [Produto].\r\n"
					+ 
					 "[3.4.2.] Caso o [Vendedor] não cumpra o quanto disposto na cláusula [4.4.1] até a [Data] [de] [Entrega], o valor correspondente aos [Descontos] (a ser estabelecido pela multiplicação da quantidade de [Produto] objeto dos [Descontos] pelo [Preço]) será considerado crédito líquido, certo e exigível, de titularidade do Comprador contra o [Vendedor.]\r\n"
					+ 
					 "[4.] [Pesagem]:\r\n"
					+ "A fim de que seja aferida a quantidade de [Produto] efetivamente entregue pelo [Vendedor] ao [Comprador], a pesagem do [Produto] será realizada caminhão a caminhão, na balança rodoviária localizada no [Local] [de] [Entrega] ou, no local mais próximo possível."
					);
		}
		

		
		if(novo_contrato.getTipo_entrega() == 1) {
		substituirTexto("[6.] [Preço.]\r\n"
				+ "O preço a ser pago pelo [Comprador] ao [Vendedor] em contraprestação pela efetiva entrega da [Quantidade] total do [Produto] ([“Preço”]), fica desde já fixado no valor de preco_decimal ( preco_extenso ) por saca de 60Kg (sessenta quilogramas).\r\n"
				);
		
		
		substituirTexto("[6.1]. O [Preço] relativamente à quantidade de [Produto] cujo [Preço] já foi fixado, nos termos deste [Contrato], será pago pelo [Comprador] ao [Vendedor] assim que cumprir a entrega total do produto.");
		}else {
			substituirTexto("[5.] [Preço.]\r\n"
					+ "O preço a ser pago pelo [Comprador] ao [Vendedor] em contraprestação pela efetiva entrega da [Quantidade] total do [Produto] ([“Preço”]), fica desde já fixado no valor de preco_decimal ( preco_extenso ) por saca de 60Kg (sessenta quilogramas).\r\n"
					);
			
			
			substituirTexto("[5.1]. O [Preço] relativamente à quantidade de [Produto] cujo [Preço] já foi fixado, nos termos deste [Contrato], será pago pelo [Comprador] ao [Vendedor] assim que cumprir a entrega total do produto.");
			
		}
		/*
		 * 6.2 clausula para adicionar valores
		
		*/
		boolean tem_pagamento_antecipado = false;
		for(CadastroContrato.CadastroPagamento pagamento : novo_contrato.getPagamentos()) {
             if(pagamento.getPagamento_adiantado() == 1) {
            	 tem_pagamento_antecipado = true;
            	 break;
             }else {
            	 tem_pagamento_antecipado = false;

             }
		}
		
		if(tem_pagamento_antecipado) {
			
			double quantidade_total_recebida_quilogramas = 0, quantidade_total_recebida_sacos = 0, valor_total_ja_recebido = 0;
			
			for(CadastroContrato.CadastroPagamento pagamento : novo_contrato.getPagamentos()) {
	             if(pagamento.getPagamento_adiantado() == 1) {
	            	 //se for um pagamento antecipado, soma seu valor no total
	            		
	         		if(novo_contrato.getMedida().equalsIgnoreCase("SACOS")) {
	         			
	         			double valor_pag = Double.parseDouble(pagamento.getValor().toPlainString());
	         			double resultado = valor_pag / novo_contrato.getValor_produto();
	         			quantidade_total_recebida_sacos += resultado;
	         			quantidade_total_recebida_quilogramas += (resultado * 60);
	         			
	         		}else if(novo_contrato.getMedida().equalsIgnoreCase("KG")) {
	         			double valor_pag = Double.parseDouble(pagamento.getValor().toPlainString());

	         			double resultado = valor_pag / novo_contrato.getValor_produto();
	         			quantidade_total_recebida_quilogramas += resultado;
	         			quantidade_total_recebida_sacos += (resultado/60);

	         		}
	         		
	         		valor_total_ja_recebido += Double.parseDouble(pagamento.getValor().toPlainString());
	             }
			}
			
			
			adicionarClausulaPagamentoAntecipado(quantidade_total_recebida_quilogramas, quantidade_total_recebida_sacos, valor_total_ja_recebido);
		//	adicionarClausulaPagamentoAntecipado(0, 0, 0);

		}else {
			adicionarClausulaPagamento();

		}


		for(CadastroContrato.CadastroPagamento pagamento : novo_contrato.getPagamentos()) {
			if(pagamento.getPagamento_adiantado() != 1) {
				criarTabelaPagamento(pagamento);

			}

		}
		
		
         //adicionar clausula de frete
		boolean tem_clausula_frete = false;
		boolean tem_clausula_armazenagem = false;
		
		  if(novo_contrato.getFrete() != null) {
	  	    	 if(!novo_contrato.getFrete().equals("") && novo_contrato.getFrete().length() > 5) {
	  	    		 
	  		  	   	if(novo_contrato.getTipo_entrega() == 1)
	  	    		 substituirTexto("[6.3.] " +  novo_contrato.getClausula_frete());
	  		  	   	else
		  	    		 substituirTexto("[5.3.] " +  novo_contrato.getClausula_frete());

	  	    		 tem_clausula_frete = true;

	  	    	 }
	  	     }
		  
	  	     //adicionar clausula armazenagem
	  	     if(novo_contrato.getArmazenagem() != null) {
	  	    	 if(!novo_contrato.getArmazenagem().equals("") && novo_contrato.getArmazenagem().length() > 5) {
	  	    		
	  	    		 if(tem_clausula_frete) {
	  	    			 if(novo_contrato.getTipo_entrega() == 1)
		  		  	   	 substituirTexto("[6.4.] " +  novo_contrato.getClausula_armazenagem());
	  	    			 else
			  		  	   	 substituirTexto("[5.4.] " +  novo_contrato.getClausula_armazenagem());

	  	    		 }
	  	    		 else{
	  	    			 if(novo_contrato.getTipo_entrega() == 1)
	  	    			 substituirTexto("[6.3.] " +  novo_contrato.getClausula_armazenagem());
	  	    			 else
		  	    			 substituirTexto("[5.3.] " +  novo_contrato.getClausula_armazenagem());

	  	    		 }

		  		  	 tem_clausula_armazenagem = true;

	  	    	 }
	  	     }
	  		  	 
	  	     
	  	     //adicionar clausula de comissão
	  	     
	  	     if(novo_contrato.getComissao() == 1) {
	  	    	 //tem comissao
	  	    	 if(novo_contrato.getClausula_comissao() == 1) {
	  	    		 
	  	    		 //pega a 3 clausula, sendo ela a clausula de comissao
	  	    		 ArrayList<String> clausulas_locais = novo_contrato.getClausulas();
	  	    		 String clausula_comissao = clausulas_locais.get(2);
	  	    		
	  	    		  	  	    		 
	  	    	 if(tem_clausula_frete &&  tem_clausula_armazenagem) {
	  	    		 if(novo_contrato.getTipo_entrega() == 1)
  	    			 substituirTexto("[6.5.] " +  clausula_comissao);
	  	    		 else
	  	    			 substituirTexto("[5.5.] " +  clausula_comissao);


	  	    	 }else if(tem_clausula_frete &&  !tem_clausula_armazenagem){
	  	    		 if(novo_contrato.getTipo_entrega() == 1)
  	    			 substituirTexto("[6.4.] " +  clausula_comissao);
	  	    		 else
	  	    			 substituirTexto("[5.4.] " +  clausula_comissao);

	  	    	 }else if(!tem_clausula_frete &&  tem_clausula_armazenagem){
	  	    		 if(novo_contrato.getTipo_entrega() == 1)
  	    			 substituirTexto("[6.4.] " +  clausula_comissao);
	  	    		 else
	  	    			 substituirTexto("[5.4.] " +  clausula_comissao);

	  	    	 }
	  	    	 else if(!tem_clausula_frete &&  !tem_clausula_armazenagem){
	  	    		 if(novo_contrato.getTipo_entrega() == 1)
  	    			 substituirTexto("[6.3.] " +  clausula_comissao);
	  	    		 else
	  	    			 substituirTexto("[5.3.] " +  clausula_comissao);

	  	    	 }
	  	    	 }
	  	     }
	  	     
	  	  if(novo_contrato.getTipo_entrega() == 1)   
		substituirTextoLongo();
	  	  else
	  		substituirTextoLongoTipoEntrega2();

		//local e data
		adicionarData();
		
		//campos de assinatura
		
		adicionarCamposAssinaturas();
		
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

			ctP = CTP.Factory.newInstance();
			t = ctP.addNewR().addNewT();

			// footer text
			t.setStringValue("Contrato Nº " + novo_contrato.getCodigo() + " LD ARMAZÉNS GERAIS");

			pars[0] = new XWPFParagraph(ctP, document_global);
			hfPolicy.createFooter(XWPFHeaderFooterPolicy.DEFAULT, pars);

			
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
	
	public void adicionarObjeto() {
		criarParagrafo(2);
		adicionarTextoParagrafoAtual("1.Objeto:                                                                                                                                                                                                      -" , true);

		adicionarTextoParagrafoAtual("Pelo presente " , false);
		adicionarTextoParagrafoAtual("Contrato".toUpperCase() , true);

		adicionarTextoParagrafoAtual(" e nos termos do artigo 481 e seguintes do Código Civil, o " , false);
		adicionarTextoParagrafoAtual("Vendedor(es)".toUpperCase() , true);

		adicionarTextoParagrafoAtual(" vende ao(s) " , false);
		adicionarTextoParagrafoAtual("Comprador(es)".toUpperCase() , true);

		NumberFormat z = NumberFormat.getNumberInstance();
		double quantidade_sacos =0;
		double quantidade_kg  = 0;

		adicionarTextoParagrafoAtual(" a quantidade de " , false);
		
		if(novo_contrato.getMedida().equalsIgnoreCase("SACOS")) {
			 quantidade_sacos = novo_contrato.getQuantidade();
			 quantidade_kg  = quantidade_sacos * 60;
			
			adicionarTextoParagrafoAtual(z.format(quantidade_kg).toUpperCase(), true);
			adicionarTextoParagrafoAtual(" KG" , false);
			adicionarTextoParagrafoAtual(" (" , false);
	    	String valor_extenso = new PorExtenso(quantidade_kg).toString();
			adicionarTextoParagrafoAtual(valor_extenso.toLowerCase().replaceAll("reais", "").replace("centavos", ""), false);


		}else if(novo_contrato.getMedida().equalsIgnoreCase("KG")) {
			 quantidade_kg  = novo_contrato.getQuantidade();
			 quantidade_sacos = quantidade_kg / 60;

			adicionarTextoParagrafoAtual(z.format(novo_contrato.getQuantidade()).toUpperCase(), true);
			adicionarTextoParagrafoAtual(" KG" , false);
			adicionarTextoParagrafoAtual(" (" , false);
	    	String valor_extenso = new PorExtenso(new BigDecimal(Double.toString(novo_contrato.getQuantidade()))).toString();
			adicionarTextoParagrafoAtual(valor_extenso.toLowerCase().replaceAll("reais", "").replace("centavos", "") , false);

		}
		


		adicionarTextoParagrafoAtual("quilogramas) ", false);
		adicionarTextoParagrafoAtual("(" + z.format(quantidade_sacos) + " sacos)", false);


		adicionarTextoParagrafoAtual(" de " , false);
		adicionarTextoParagrafoAtual(novo_contrato.getProduto().toUpperCase(), true);

		adicionarTextoParagrafoAtual(" em grãos, da safra " , false);
		adicionarTextoParagrafoAtual(Integer.toString(novo_contrato.getModelo_safra().getAno_colheita()), true);

		adicionarTextoParagrafoAtual("/" , false);
		adicionarTextoParagrafoAtual(Integer.toString(novo_contrato.getModelo_safra().getAno_plantio()), true);

		adicionarTextoParagrafoAtual(" conforme padrões de qualidade estabelecidos no presente " , false);

		adicionarTextoParagrafoAtual("Contrato (“Produto”)." , true);

		

		
	}
	
	
	public void adicionarEntrega(CadastroCliente local_entrega) {
		criarParagrafo(2);
		adicionarTextoParagrafoAtual("2. Entrega:                                                                                                                                                                                                   -" , true);

		if(novo_contrato.getTipo_entrega() == 1) {
		
		adicionarTextoParagrafoAtual("O ", false);
		adicionarTextoParagrafoAtual("Vendedor ", true);
		adicionarTextoParagrafoAtual("obriga-se a entregar o ", false);
		adicionarTextoParagrafoAtual("Produto ao Comprador ", true);
		adicionarTextoParagrafoAtual("na condição “posto sobre rodas”, no seguinte local de entrega: ", false);
		}
		else {
			adicionarTextoParagrafoAtual("A mercadoria se encontra depositada no seguinte local: ", false);

		}
		String nome = "";
		if(local_entrega.getTipo_pessoa() == 0) {
			//pessoa fisica
			nome = local_entrega.getNome_empresarial();
			adicionarTextoParagrafoAtual(nome, true);

			adicionarTextoParagrafoAtual( " CPF: " , false);
			adicionarTextoParagrafoAtual(local_entrega.getCpf(), true);


		}else {
			nome = local_entrega.getNome_fantaia();
			adicionarTextoParagrafoAtual(nome, true);
			adicionarTextoParagrafoAtual( " CNPJ: " , false);
			adicionarTextoParagrafoAtual(local_entrega.getCnpj(), true);

		}
		adicionarTextoParagrafoAtual( " - IE: " , false);
		adicionarTextoParagrafoAtual( local_entrega.getIe() , true);
		
		adicionarTextoParagrafoAtual(" localizado na " , false);
		adicionarTextoParagrafoAtual( local_entrega.getRua() , true);
		adicionarTextoParagrafoAtual( ", ", false);

		adicionarTextoParagrafoAtual( local_entrega.getNumero(), true);

		adicionarTextoParagrafoAtual( " Bairro: " , false);
		adicionarTextoParagrafoAtual( local_entrega.getBairro() , true);


		adicionarTextoParagrafoAtual( " Cidade: " , false);
		adicionarTextoParagrafoAtual( local_entrega.getCidade() , true);


		adicionarTextoParagrafoAtual( " " , false);
		adicionarTextoParagrafoAtual( local_entrega.getUf() , true);


		adicionarTextoParagrafoAtual( " CEP: " , true);
		adicionarTextoParagrafoAtual( local_entrega.getCep() , false);
		adicionarTextoParagrafoAtual( " (“Local de Entrega”). " , true);
		
		
		
		if(novo_contrato.getTipo_entrega() == 1) {
		criarParagrafo(2);
		adicionarTextoParagrafoAtual( "Correrão por conta exclusiva do Vendedor todos os custos e riscos relacionados ao Produto (incluindo, mas não se limitando os riscos relacionados ao plantio, cultivo, colheita, bem como a sua adequação às Especificações), até a sua efetiva entrega ao Comprador no Local de Entrega, na condição estabelecida nesta cláusula." , false);
		}
		
		
		
	}
	

	public void adicionarClausula2_1() {
		criarParagrafo(2);
	
		adicionarTextoParagrafoAtual("2.1. " , true);
		adicionarTextoParagrafoAtual("O " , false);

		adicionarTextoParagrafoAtual("Vendedor" , true);
		adicionarTextoParagrafoAtual(" obriga-se a informar ao " , false);
		adicionarTextoParagrafoAtual("Comprador" , true);
		adicionarTextoParagrafoAtual(", com antecedência mínima de 5 (cinco) dias úteis, a data em que irá entregar o " , false);
		adicionarTextoParagrafoAtual("Produto" , true);
		adicionarTextoParagrafoAtual(" no" , false);
		adicionarTextoParagrafoAtual(" Local de Entrega. " , true);



	}
	
	public void adicionarClausula3() {
		criarParagrafo(2);
		
		adicionarTextoParagrafoAtual("3. Prazo de Entrega: " , true);
		adicionarTextoParagrafoAtual("O " , false);
		adicionarTextoParagrafoAtual("Vendedor" , true);
		adicionarTextoParagrafoAtual(" deverá entregar a " , false);
		adicionarTextoParagrafoAtual("Quantidade " , true);
		adicionarTextoParagrafoAtual(" total do " , false);
		adicionarTextoParagrafoAtual("Produto" , true);
		adicionarTextoParagrafoAtual(" ao " , false);
		adicionarTextoParagrafoAtual("Comprador" , true);
		adicionarTextoParagrafoAtual(", livre e desembaraçado de quaisquer ônus ou gravames, impreterivelmente até " , false);
		adicionarTextoParagrafoAtual(novo_contrato.getData_entrega() , true);

		adicionarTextoParagrafoAtual(" (“Data de Entrega”)." , true);

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
	
	
	public void criarParagrafoTabela(XWPFParagraph paragraph, String texto, boolean negrito) {
		paragraph.setIndentationLeft(100);
		paragraph.setIndentationRight(100);
		 paragraph.setAlignment(ParagraphAlignment.CENTER);

		 XWPFRun run = paragraph.createRun();

         run.setFontFamily("Times New Roman");
          run.setFontSize(9);
          run.setBold(negrito);
          run.setText(texto);

		      
       
		
	}
	
	public void criarTabelaPagamento(CadastroContrato.CadastroPagamento pagamento) {
		criarParagrafo(1);
	    XWPFTable table = document_global.createTable(8, 2);
	    setTableAlign(table, ParagraphAlignment.LEFT);
	    
	    XWPFTableRow tableRowOne = table.getRow(0);
	    tableRowOne.getCell(0).removeParagraph(0);

        XWPFParagraph paragraph = tableRowOne.getCell(0).addParagraph();

        
        //linha 1, banco
	    tableRowOne = table.getRow(0);
	    tableRowOne.getCell(0).removeParagraph(0);
        paragraph = tableRowOne.getCell(0).addParagraph();
        criarParagrafoTabela(paragraph,"BANCO: ", false );
   
        tableRowOne = table.getRow(0);
	    tableRowOne.getCell(1).removeParagraph(0);
        paragraph = tableRowOne.getCell(1).addParagraph();
        criarParagrafoTabela(paragraph,pagamento.getConta().getBanco(), true );
   
        //linha 1, codigo banco
        tableRowOne = table.getRow(1);
	    tableRowOne.getCell(0).removeParagraph(0);
        paragraph = tableRowOne.getCell(0).addParagraph();
        criarParagrafoTabela(paragraph,"CÓDIGO: ", false );
   
        tableRowOne = table.getRow(1);
	    tableRowOne.getCell(1).removeParagraph(0);
        paragraph = tableRowOne.getCell(1).addParagraph();
        criarParagrafoTabela(paragraph, pagamento.getConta().getCodigo(), true );
        
        //linha 2, titual
        tableRowOne = table.getRow(2);
	    tableRowOne.getCell(0).removeParagraph(0);
        paragraph = tableRowOne.getCell(0).addParagraph();
        criarParagrafoTabela(paragraph,"TITULAR: ", false );
   
        tableRowOne = table.getRow(2);
	    tableRowOne.getCell(1).removeParagraph(0);
        paragraph = tableRowOne.getCell(1).addParagraph();
        criarParagrafoTabela(paragraph, pagamento.getConta().getNome().toUpperCase(), true );
        
        //linha 3, cpf
        tableRowOne = table.getRow(3);
	    tableRowOne.getCell(0).removeParagraph(0);
        paragraph = tableRowOne.getCell(0).addParagraph();
        criarParagrafoTabela(paragraph,"CPF: ", false );
   
        tableRowOne = table.getRow(3);
	    tableRowOne.getCell(1).removeParagraph(0);
        paragraph = tableRowOne.getCell(1).addParagraph();
        criarParagrafoTabela(paragraph, pagamento.getConta().getCpf_titular(), true );
   
        //linha 4, agencia
        tableRowOne = table.getRow(4);
	    tableRowOne.getCell(0).removeParagraph(0);
        paragraph = tableRowOne.getCell(0).addParagraph();
        criarParagrafoTabela(paragraph,"AGÊNCIA: ", false );
   
        tableRowOne = table.getRow(4);
	    tableRowOne.getCell(1).removeParagraph(0);
        paragraph = tableRowOne.getCell(1).addParagraph();
        criarParagrafoTabela(paragraph, pagamento.getConta().getAgencia(), true );
        
        //linha 5, conta
        tableRowOne = table.getRow(5);
	    tableRowOne.getCell(0).removeParagraph(0);
        paragraph = tableRowOne.getCell(0).addParagraph();
        criarParagrafoTabela(paragraph,"CONTA: ", false );
   
        tableRowOne = table.getRow(5);
	    tableRowOne.getCell(1).removeParagraph(0);
        paragraph = tableRowOne.getCell(1).addParagraph();
        criarParagrafoTabela(paragraph, pagamento.getConta().getConta(), true );
        
        //linha 5, valor
        tableRowOne = table.getRow(6);
	    tableRowOne.getCell(0).removeParagraph(0);
        paragraph = tableRowOne.getCell(0).addParagraph();
        criarParagrafoTabela(paragraph,"VALOR: ", false );
   
        tableRowOne = table.getRow(6);
	    tableRowOne.getCell(1).removeParagraph(0);
        paragraph = tableRowOne.getCell(1).addParagraph();
    	Locale ptBr = new Locale("pt", "BR");
   	   	String valorString = NumberFormat.getCurrencyInstance(ptBr).format((pagamento.getValor()));
   	   	System.out.println(valorString + "(" + new PorExtenso(pagamento.getValor()));   
        criarParagrafoTabela(paragraph,valorString , true );
        
        //linha 6, data
        tableRowOne = table.getRow(7);
	    tableRowOne.getCell(0).removeParagraph(0);
        paragraph = tableRowOne.getCell(0).addParagraph();
        criarParagrafoTabela(paragraph,"DATA: ", false );
   
        tableRowOne = table.getRow(7);
	    tableRowOne.getCell(1).removeParagraph(0);
        paragraph = tableRowOne.getCell(1).addParagraph();
    
        criarParagrafoTabela(paragraph,pagamento.getData_pagamento() , true );
       
	    
	    
	    
	    criarParagrafo(2);

	}
	
	public void criarTabela() {
		
		criarParagrafo(1);
		    XWPFTable table = document_global.createTable(9, 3);

		    setTableAlign(table, ParagraphAlignment.CENTER);
		    XWPFTableRow tableRowOne = table.getRow(0);
		    tableRowOne.getCell(0).removeParagraph(0);
		   
		    
            XWPFParagraph paragraph = tableRowOne.getCell(0).addParagraph();

            criarParagrafoTabela(paragraph,"Especificações e Descontos", true );
           
		      
		      //pega a linha 0, coluna 1
		    XWPFTableCell cellRow1 = table.getRow(0).getCell(0);

		    cellRow1.getCTTc().addNewTcPr();
		    cellRow1.getCTTc().getTcPr().addNewGridSpan();
		    cellRow1.getCTTc().getTcPr().getGridSpan().setVal(BigInteger.valueOf(3L));

		     tableRowOne = table.getRow(1);
		    tableRowOne.getCell(0).removeParagraph(0);

             paragraph = tableRowOne.getCell(0).addParagraph();
            criarParagrafoTabela(paragraph,"Especificações", true );
		   
		      XWPFTableCell cellRow2 = table.getRow(1).getCell(0);

			    cellRow2.getCTTc().addNewTcPr();
			    cellRow2.getCTTc().getTcPr().addNewGridSpan();
			    cellRow2.getCTTc().getTcPr().getGridSpan().setVal(BigInteger.valueOf(2L));
			    

			      tableRowOne = table.getRow(1);
				    tableRowOne.getCell(1).removeParagraph(0);

		             paragraph = tableRowOne.getCell(1).addParagraph();
		            criarParagrafoTabela(paragraph,"Descontos", true );
			      
			      
			        tableRowOne = table.getRow(2);
				    tableRowOne.getCell(0).removeParagraph(0);
                    paragraph = tableRowOne.getCell(0).addParagraph();
		            criarParagrafoTabela(paragraph,"Fator", true );
			      
		            
		            
		            tableRowOne = table.getRow(2);
				    tableRowOne.getCell(1).removeParagraph(0);
                    paragraph = tableRowOne.getCell(1).addParagraph();
		            criarParagrafoTabela(paragraph,"Tolerância (em %)", true );

		            
		            tableRowOne = table.getRow(2);
				    tableRowOne.getCell(2).removeParagraph(0);
                    paragraph = tableRowOne.getCell(2).addParagraph();
		            criarParagrafoTabela(paragraph,"Porcentagem a ser descontada da Quantidade em caso de entrega de Produto fora das Especificações", true );
		       

		            //linha 3
		            tableRowOne = table.getRow(3);
				    tableRowOne.getCell(0).removeParagraph(0);
                    paragraph = tableRowOne.getCell(0).addParagraph();
		            criarParagrafoTabela(paragraph,"Umidade", false );
		       
		            tableRowOne = table.getRow(3);
				    tableRowOne.getCell(1).removeParagraph(0);
                    paragraph = tableRowOne.getCell(1).addParagraph();
		            criarParagrafoTabela(paragraph,"Máxima de 14%", false );
		       
		            tableRowOne = table.getRow(3);
				    tableRowOne.getCell(2).removeParagraph(0);
                    paragraph = tableRowOne.getCell(2).addParagraph();
		            criarParagrafoTabela(paragraph,"1,5% para cada faixa de 1% excedente a 14%", false );
		       

		            //linha 4
		            
		            tableRowOne = table.getRow(4);
				    tableRowOne.getCell(0).removeParagraph(0);
                    paragraph = tableRowOne.getCell(0).addParagraph();
		            criarParagrafoTabela(paragraph,"Impureza", false );
		       
		            tableRowOne = table.getRow(4);
				    tableRowOne.getCell(1).removeParagraph(0);
                    paragraph = tableRowOne.getCell(1).addParagraph();
		            criarParagrafoTabela(paragraph,"Máxima de 1 %", false );
		       
		            tableRowOne = table.getRow(4);
				    tableRowOne.getCell(2).removeParagraph(0);
                    paragraph = tableRowOne.getCell(2).addParagraph();
		            criarParagrafoTabela(paragraph,"1,0% para cada faixa de 1% excedente a 1%", false );
			
		            //linha 5
		            
		            
		            tableRowOne = table.getRow(5);
				    tableRowOne.getCell(0).removeParagraph(0);
                    paragraph = tableRowOne.getCell(0).addParagraph();
		            criarParagrafoTabela(paragraph,"Grãos Ardidos e Avariados", false );
		       
		            tableRowOne = table.getRow(5);
				    tableRowOne.getCell(1).removeParagraph(0);
                    paragraph = tableRowOne.getCell(1).addParagraph();
		            criarParagrafoTabela(paragraph,"Máxima de 8 %", false );
		       
		            tableRowOne = table.getRow(5);
				    tableRowOne.getCell(2).removeParagraph(0);
                    paragraph = tableRowOne.getCell(2).addParagraph();
		            criarParagrafoTabela(paragraph,"1,0% para cada faixa de 1% excedente a 8%", false );
			
			      
			    //linha 6
		            
		            
		            tableRowOne = table.getRow(6);
				    tableRowOne.getCell(0).removeParagraph(0);
                    paragraph = tableRowOne.getCell(0).addParagraph();
		            criarParagrafoTabela(paragraph,"Deteriorado", false );
		       
		            tableRowOne = table.getRow(6);
				    tableRowOne.getCell(1).removeParagraph(0);
                    paragraph = tableRowOne.getCell(1).addParagraph();
		            criarParagrafoTabela(paragraph,"Máxima de 0 %", false );
		       
		            tableRowOne = table.getRow(6);
				    tableRowOne.getCell(2).removeParagraph(0);
                    paragraph = tableRowOne.getCell(2).addParagraph();
		            criarParagrafoTabela(paragraph,"1,0% para cada faixa de 1% excedente a 0%", false );
		            
		           //linha 7
		            
		            tableRowOne = table.getRow(7);
				    tableRowOne.getCell(0).removeParagraph(0);
                    paragraph = tableRowOne.getCell(0).addParagraph();
		            criarParagrafoTabela(paragraph,"Quebrados Máximo", false );
		       
		            tableRowOne = table.getRow(7);
				    tableRowOne.getCell(1).removeParagraph(0);
                    paragraph = tableRowOne.getCell(1).addParagraph();
		            criarParagrafoTabela(paragraph,"Máxima de 30 %", false );
		       
		            tableRowOne = table.getRow(7);
				    tableRowOne.getCell(2).removeParagraph(0);
                    paragraph = tableRowOne.getCell(2).addParagraph();
		            criarParagrafoTabela(paragraph,"1,0% para cada faixa de 1% excedente a 30%", false );

			       //linha 8
		            
		            tableRowOne = table.getRow(8);
				    tableRowOne.getCell(0).removeParagraph(0);
                    paragraph = tableRowOne.getCell(0).addParagraph();
		            criarParagrafoTabela(paragraph,"Tipo", false );
		       
		            tableRowOne = table.getRow(8);
				    tableRowOne.getCell(1).removeParagraph(0);
                    paragraph = tableRowOne.getCell(1).addParagraph();
		            criarParagrafoTabela(paragraph,"N/A", false );
		       
		            tableRowOne = table.getRow(8);
				    tableRowOne.getCell(2).removeParagraph(0);
                    paragraph = tableRowOne.getCell(2).addParagraph();
		            criarParagrafoTabela(paragraph,"N/A", false );
		            
			
			    
			    criarParagrafo(2);
		    
		    
			    
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
	
	
	
	public void adicionarParte(int tipo_parte, CadastroCliente cliente) {
		
		XWPFParagraph parte = document_global.createParagraph();
		parte.setAlignment(ParagraphAlignment.LEFT);

	
		XWPFRun titlerun = parte.createRun();
		titlerun.setColor("000000");
		titlerun.setBold(true);
		titlerun.setFontFamily("Times New Roman");
		titlerun.setFontSize(10);
		
		if(tipo_parte == 0) {
			//corretor
			titlerun.setText("Corretor:");

		}else if(tipo_parte == 1) {
			 //vendedor
			titlerun.setText("Vendedor:");

		}else {
			//comprador
			titlerun.setText("Comprador:");

		}
		titlerun.addBreak();
		
		
		String nome_corretor = "";
		if(cliente.getTipo_pessoa() == 0) {
			//pessoa fisica
			nome_corretor = cliente.getNome_empresarial();
		}else {
			nome_corretor = cliente.getNome_fantaia();

		}
		
		XWPFRun corretorNomerun = parte.createRun();
		corretorNomerun.setText(nome_corretor);
		corretorNomerun.setColor("000000");
		corretorNomerun.setFontFamily("Times New Roman");
		corretorNomerun.setFontSize(10);
		corretorNomerun.setBold(true);

		XWPFRun adicional1run = parte.createRun();
		adicional1run.setColor("000000");
		adicional1run.setFontFamily("Times New Roman");
		adicional1run.setFontSize(10);
		adicional1run.setBold(false);
		adicional1run.setText(", Produtor Rural, residente e domiciliado no endereço ");

		XWPFRun enderecoCorretorrun = parte.createRun();
		enderecoCorretorrun.setColor("000000");
		enderecoCorretorrun.setFontFamily("Times New Roman");
		enderecoCorretorrun.setFontSize(10);
		enderecoCorretorrun.setBold(true);
		enderecoCorretorrun.setText(cliente.getRua() + ", " + cliente.getBairro());

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
		cidadeRun.setText(cliente.getCidade());
		
		XWPFRun infoEstadoRun = parte.createRun();
		infoEstadoRun.setColor("000000");
		infoEstadoRun.setFontFamily("Times New Roman");
		infoEstadoRun.setFontSize(10);
		infoEstadoRun.setBold(true);
		infoEstadoRun.setText(" - ");
		
		XWPFRun estadoRUn = parte.createRun();
		estadoRUn.setColor("000000");
		estadoRUn.setFontFamily("Times New Roman");
		estadoRUn.setFontSize(10);
		estadoRUn.setBold(true);
		estadoRUn.setText(cliente.getUf());

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
		cepRun.setText(cliente.getCep());

		
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
			cpfRun.setText(cliente.getCpf());

		}else {
			adicionarCpfRun.setText(", inscrito no CNPJ sob nº ");
			cpfRun.setText(cliente.getCnpj());

		}
		

		

		XWPFRun finalRun = parte.createRun();
		finalRun.setColor("000000");
		finalRun.setFontFamily("Times New Roman");
		finalRun.setFontSize(10);
		finalRun.setBold(false);
		
		if(tipo_parte == 0) {
			//corretor
			finalRun.setText(".  A pessoa supra indicada será doravante denominada “Corretor”.");

		}else if(tipo_parte == 1) {
			 //vendedor
			finalRun.setText(".  A pessoa supra indicada será doravante denominada “Vendedor”.");

		}else {
			//comprador
			finalRun.setText(".  A pessoa supra indicada será doravante denominada “Comprador”.");

		}

	}

	public ByteArrayOutputStream alterar(CadastroContrato contrato) {

		System.out.println("Alterando contrato formal!");
		this.novo_contrato = contrato;

		compradores = novo_contrato.listaCompradores();
		vendedores = novo_contrato.listaVendedores();
		corretores = novo_contrato.listaCorretores();

		try {

			InputStream inputStream = new ByteArrayInputStream(modelo.getArquivo());
			System.out.println("arquivo aberto!");

			XWPFDocument doc = new XWPFDocument(OPCPackage.open(inputStream));
			for (XWPFParagraph p : doc.getParagraphs()) {
				List<XWPFRun> runs = p.getRuns();
				if (runs != null) {
					for (XWPFRun r : runs) {
						String text = r.getText(0);
						if (text != null && text.contains("vendedor")) {
							text = text.replace("vendedor", "sucesso");
							r.setText(text, 0);
						}
					}
				}
			}

			inputStream.close();

			ByteArrayOutputStream saida_apos_edicao = new ByteArrayOutputStream();

			doc.write(new FileOutputStream("c:\\arquivo.docx"));
			doc.write(saida_apos_edicao);
			// workbook.close();

			return saida_apos_edicao;
			// ConverterPdf conversor = new C

		} catch (Exception e) {
			System.out.println("houve um erro ao processar o arquivo .docx");
			return null;
		}
	}

	public String trocaVendedor(String texto_completo) {

		String nome_vendedor1 = "";

		if (vendedores[0] != null) {
			if (vendedores[0].getTipo_pessoa() == 0) {
				// pessoa fisica
				nome_vendedor1 = vendedores[0].getNome_empresarial();
			} else {
				nome_vendedor1 = vendedores[0].getNome_fantaia();

			}
		}

		texto_completo = texto_completo.replace("[nome_vendedor]", nome_vendedor1);
		return texto_completo;

	}
	
	


	public void getDadosGlobais() {
		// gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		GerenciadorLog = dados.getGerenciadorLog();
		configs_globais = dados.getConfigs_globais();

		// usuario logado
		login = dados.getLogin();

	}
	
	
	public void substituirTextoLongo() {
		substituirTexto("[7.] [Disposições] [Gerais.]\r\n"
				+ "O [Vendedor] é responsável pela manutenção e conservação do [Produto], bem como pela sua adequação às [Especificações], até sua entrega ao Comprador nos termos deste [Contrato], e responderá civil e criminalmente por eventual desvio, correndo por sua conta exclusiva todos os custos e riscos inerentes à sua produção, inclusive o risco do [Produto] não vir a existir em razão de pragas, intempéries, caso fortuito ou motivo de força maior.\r\n"
				+ 
				 "[7.1.] Se o [Vendedor], por qualquer motivo, deixar de colher, carregar ou transportar o [Produto] no tempo e condições previstas neste [Contrato], fica facultado ao [Comprador] realizar estes serviços, por si ou por terceiros, suprindo assim a omissão do [[Vendedor]]. Nessa hipótese, o [Vendedor] deverá pagar ao Comprador todos os valores por ela ([Compradora]) incorridos com estas atividades, acrescidos de 20% (vinte por cento) a título de taxa de administração, atualização monetária pelo IGPM-FGV e juros de 1% (um por cento) ao mês, calculados “pro rata die” desde a data de sua inocorrência pelo [Comprador] até o seu efetivo reembolso pelo [Vendedor], o que constituirá crédito líquido, certo e exigível do [Comprador] contra o [Vendedor].\r\n"
				+ 
				 "[7.2.] Os seguintes eventos acarretarão o vencimento antecipado de todas as obrigações atribuídas ao [Vendedor] no presente [Contrato], de forma automática e independentemente de qualquer formalidade ou notificação: \r\n"
				+ "[(i)] o descumprimento, pelo [Vendedor], de qualquer das obrigações estabelecidas neste [Contrato] ou em quaisquer outros contratos firmados entre o [Comprador] e o [Vendedor]; (ii) a verificação, pelo [Comprador], de que o [Vendedor] não realizou o plantio ou não adotou os corretos e adequados tratos das lavouras cuja produção deveria ser entregue em regular cumprimento às suas obrigações no presente[ Contrato]; ou ([iii]) a constatação, pelo [Comprador], de que o [Vendedor] realizou o desvio e/ou a entrega a terceiros de qualquer quantidade do [Produto].\r\n"
				+ 
				 "[7.3.] A instituição prévia, concomitante ou posterior à celebração deste [Contrato] de ônus, gravames ou constrições sobre o [Produto] sobrestará o vencimento da obrigação de pagamento do [Preço] até a apresentação, pelo [Vendedor] ao [Comprador], de prova definitiva da respectiva desoneração.\r\n"
				+ 
				 "[7.3.1.] Caso não haja a eliminação dos ônus, gravames ou constrições existentes sobre o [Produto] no prazo de 5 (cinco) dias contado a partir da [Data] [de] [Entrega], restará caracterizado o inadimplemento contratual do [Vendedor], hipótese em que o [Comprador] poderá rescindir este [Contrato] total ou parcialmente, sujeitando-se o [Vendedor] às multas e penalidades previstas na cláusula [08].\r\n"
				+ 
				 "[7.4.] As [Partes] declaram e aceitam, em caráter irrevogável e irretratável, que a ocorrência de grandes aumentos ou reduções das cotações e preços do [Produto] no mercado interno ou externo, assim como a incidência de pragas, doenças, intempéries ou variações climáticas nas lavouras onde será produzido o [Produto], são fatos absolutamente previsíveis, ordinários e inerentes à atividade agropecuária e ao agronegócio, e em nenhuma hipótese motivarão a resolução do presente [Contrato] ou a revisão de qualquer das obrigações aqui atribuídas às [Partes].\r\n"
				+ 
				 "[08.] [Multas] [e] [Penalidades].\r\n"
				+ 
				 "[08.1.] No caso de infração de quaisquer cláusulas deste [Contrato], ficará facultado à parte prejudicada o direito de, cumulativamente:\r\n"
				+ "[(i)] exigir da parte inadimplente a restituição dos valores pagos antecipadamente, acrescidos de atualização monetária e juros, na forma da cláusula [08.1.1];\r\n"
				+ "[(ii)] considerar o presente [Contrato] rescindido de pleno direito, total ou parcialmente, independentemente de notificação ou interpelação;\r\n"
				+ "[(iii)] exigir da parte inadimplente o pagamento de multa não compensatória de 15% (quinze por cento) do valor total deste [Contrato], calculado pela multiplicação da [Quantidade] pelo [Preço];\r\n"
				+ "(iv) exigir da parte inadimplente indenização das perdas e danos efetivamente incorridos, respeitados os valores mínimos estabelecidos na cláusula [08.1.2].\r\n"
				+ 
				 "[09.1.1.] Em se tratando de inadimplência de obrigações de pagamento, ficará ainda sujeita a parte inadimplente ao pagamento de juros de mora de 1% ao mês e atualizações monetárias pela variação do IGPM-FGV (Índice Geral de Preços de Mercado – Fundação Getúlio Vargas) calculados “pro rata die” entre a data do vencimento da obrigação e a data do efetivo pagamento. Considerar-se-á, também, rescindido de pleno direito o presente [Contrato], no caso de impetração, requerimento ou decretação de falência, insolvência, recuperação ou liquidação judicial ou extrajudicial do [Vendedor]. Na hipótese de extinção do IGPM-FGV aplicar-se-á o seu substituto na forma da lei e, se não existir substituto, será aplicado o índice Geral de Preços ao Consumidor (IPC) editado pela Fundação Instituto de Pesquisa Econômica (FIPE).\r\n"
				+ 
				"[08.1.2.] Fica convencionado entre as [Partes], em caráter irrevogável e irretratável, que o valor mínimo da indenização por perdas e danos devida pela [Parte] inadimplente à [Parte] inocente, nos termos do disposto no item [(iv)] da cláusula [08.1], nunca será inferior ao maior valor dentre os seguintes:\r\n"
				+ "[(i)] 20% (vinte por cento) [Preço] da quantidade de [Produto] não entregue pelo [Vendedor] ao [Comprador] nos termos deste [Contrato]; ou\r\n"
				+ "[(ii)] O valor correspondente à diferença entre o preço de mercado do [Produto] não entregue e o [Preço] estabelecido na forma do presente [Contrato]. O preço de mercado da quantidade de [Produto] não entregue será equivalente à média dos preços de compra do [Produto] que estiverem sendo praticados, no [Local] [de] [Entrega], na [Data] [de] [Entrega], pelo [Comprador] e pelas seguintes empresas, desde que operem no [Local] [de] [Entrega]: Cargill, Bunge, ADM e Cocari.\r\n"
				+ 
				 "[08.2.] Na hipótese de uma das [Partes] ver-se obrigada a recorrer aos meios judiciais para fazer valer o presente [Contrato], além da multa penal, das perdas e danos e da restituição das importâncias eventualmente antecipadas, acrescidas dos juros e correção monetária previstos na cláusula [08.1.1], a [Parte] inadimplente ficará sujeito ao pagamento das custas e despesas judiciais incorridas pela outra [Parte], bem como honorários advocatícios desde já fixados em 20% do montante devido.\r\n"
				+ 
				 "[08.3.] Não constituirão inadimplência deste [Contrato]: [(i)] eventuais atrasos bancários alheios à vontade do [Comprador]; [(ii)] o não pagamento do [Preço] em decorrência do descumprimento, pelo [Vendedor], de quaisquer das obrigações a ele atribuídas no presente [Contrato].\r\n"
				+ 
				 "[09.] [Disposições] [Finais].\r\n"
				+ "O presente [Contrato] constitui título executivo nos termos do artigo 585, inciso II, do Código de Processo Civil, considerando-se líquidos, certos e exigíveis todos os valores dele decorrentes, inclusive e em especial os montantes que venham a ser devidos pelo [Vendedor] nos termos das cláusulas [08.1] a [08.2] retro, desistindo desde já este último, em caráter irrevogável e irretratável, de qualquer processo especial de verificação de ditos montantes, bastando para sua comprovação os comprovantes dos pagamentos efetuados pelo [Comprador] ou um demonstrativo sintético da composição da dívida.\r\n"
				+ 
				"[09.1.] É vedado ao [Vendedor] ceder, dar em garantia, securitizar, transferir a terceiros ou emitir ou sacar títulos representativos de quaisquer créditos que detenha contra o [Comprador] e que sejam relacionados ao presente Contrato, sem a prévia e expressa autorização escrita do [Comprador]. Qualquer cessão realizada em descumprimento desta obrigação será considerada nula de pleno direito, sendo considerados regularmente realizados e liberatórios da obrigação de pagamento do [Comprador] todos os pagamentos feitos pelo [Comprador] diretamente ao [Vendedor] nos termos deste [Contrato]. O [Comprador] fica desde já autorizado pelo Vendedor a ceder ou transferir os direitos ou obrigações decorrentes deste [Contrato] a quaisquer terceiros, mediante simples comunicação escrita ao [Vendedor].\r\n"
				+ 
				 "[09.2.] A tolerância, por qualquer das [Partes], em relação ao descumprimento de quaisquer dos compromissos recíprocos aqui avençados, constituirá mera liberalidade e não poderá de forma alguma ser caracterizada como novação ou precedente invocável pela outra parte.\r\n"
				+ 
				"[09.3.] O presente [Contrato] é celebrado em caráter irrevogável e irretratável, perfeito e acabado, o qual obriga não só as [Partes] ora contratantes, como também seus herdeiros e/ou sucessores a qualquer título.\r\n"
				+ 
				"[09.4.] Todas as obrigações atribuídas ao [Comprador] ao abrigo do presente [Contrato] poderão ser adimplidas por meio de compensação, independentemente de prestação de contas e nos termos dos artigos 368 e seguintes do Código Civil. \r\n"
				+ 
				 "[09.5.] Para dirimir quaisquer questões decorrentes deste [Contrato], as [Partes] elegem o foro da Comarca de Vazante, Estado de Minas Gerais, renunciando qualquer outro, por mais privilegiado que seja. \r\n"
				+ 
				"E por estarem assim justas e contratadas, firmam as [Partes] o presente instrumento em 2 (duas) vias de igual teor e forma, na presença de 2 (duas) testemunha.\r\n"
				);
	}
	
	
	public void substituirTextoLongoTipoEntrega2() {
		substituirTexto("[6.] [Disposições] [Gerais.]\r\n"
				+ "O [Vendedor] é responsável pela manutenção e conservação do [Produto], bem como pela sua adequação às [Especificações], até sua entrega ao Comprador nos termos deste [Contrato], e responderá civil e criminalmente por eventual desvio, correndo por sua conta exclusiva todos os custos e riscos inerentes à sua produção, inclusive o risco do [Produto] não vir a existir em razão de pragas, intempéries, caso fortuito ou motivo de força maior.\r\n"
				+ 
				 "[6.1.] Se o [Vendedor], por qualquer motivo, deixar de colher, carregar ou transportar o [Produto] no tempo e condições previstas neste [Contrato], fica facultado ao [Comprador] realizar estes serviços, por si ou por terceiros, suprindo assim a omissão do [[Vendedor]]. Nessa hipótese, o [Vendedor] deverá pagar ao Comprador todos os valores por ela ([Compradora]) incorridos com estas atividades, acrescidos de 20% (vinte por cento) a título de taxa de administração, atualização monetária pelo IGPM-FGV e juros de 1% (um por cento) ao mês, calculados “pro rata die” desde a data de sua inocorrência pelo [Comprador] até o seu efetivo reembolso pelo [Vendedor], o que constituirá crédito líquido, certo e exigível do [Comprador] contra o [Vendedor].\r\n"
				+ 
				 "[6.2.] Os seguintes eventos acarretarão o vencimento antecipado de todas as obrigações atribuídas ao [Vendedor] no presente [Contrato], de forma automática e independentemente de qualquer formalidade ou notificação: \r\n"
				+ "[(i)] o descumprimento, pelo [Vendedor], de qualquer das obrigações estabelecidas neste [Contrato] ou em quaisquer outros contratos firmados entre o [Comprador] e o [Vendedor]; (ii) a verificação, pelo [Comprador], de que o [Vendedor] não realizou o plantio ou não adotou os corretos e adequados tratos das lavouras cuja produção deveria ser entregue em regular cumprimento às suas obrigações no presente[ Contrato]; ou ([iii]) a constatação, pelo [Comprador], de que o [Vendedor] realizou o desvio e/ou a entrega a terceiros de qualquer quantidade do [Produto].\r\n"
				+ 
				 "[6.3.] A instituição prévia, concomitante ou posterior à celebração deste [Contrato] de ônus, gravames ou constrições sobre o [Produto] sobrestará o vencimento da obrigação de pagamento do [Preço] até a apresentação, pelo [Vendedor] ao [Comprador], de prova definitiva da respectiva desoneração.\r\n"
				+ 
				 "[6.3.1.] Caso não haja a eliminação dos ônus, gravames ou constrições existentes sobre o [Produto] no prazo de 5 (cinco) dias contado a partir da [Data] [de] [Entrega], restará caracterizado o inadimplemento contratual do [Vendedor], hipótese em que o [Comprador] poderá rescindir este [Contrato] total ou parcialmente, sujeitando-se o [Vendedor] às multas e penalidades previstas na cláusula [08].\r\n"
				+ 
				 "[6.4.] As [Partes] declaram e aceitam, em caráter irrevogável e irretratável, que a ocorrência de grandes aumentos ou reduções das cotações e preços do [Produto] no mercado interno ou externo, assim como a incidência de pragas, doenças, intempéries ou variações climáticas nas lavouras onde será produzido o [Produto], são fatos absolutamente previsíveis, ordinários e inerentes à atividade agropecuária e ao agronegócio, e em nenhuma hipótese motivarão a resolução do presente [Contrato] ou a revisão de qualquer das obrigações aqui atribuídas às [Partes].\r\n"
				+ 
				 "[7.] [Multas] [e] [Penalidades].\r\n"
				+ 
				 "[7.1.] No caso de infração de quaisquer cláusulas deste [Contrato], ficará facultado à parte prejudicada o direito de, cumulativamente:\r\n"
				+ "[(i)] exigir da parte inadimplente a restituição dos valores pagos antecipadamente, acrescidos de atualização monetária e juros, na forma da cláusula [08.1.1];\r\n"
				+ "[(ii)] considerar o presente [Contrato] rescindido de pleno direito, total ou parcialmente, independentemente de notificação ou interpelação;\r\n"
				+ "[(iii)] exigir da parte inadimplente o pagamento de multa não compensatória de 15% (quinze por cento) do valor total deste [Contrato], calculado pela multiplicação da [Quantidade] pelo [Preço];\r\n"
				+ "(iv) exigir da parte inadimplente indenização das perdas e danos efetivamente incorridos, respeitados os valores mínimos estabelecidos na cláusula [08.1.2].\r\n"
				+ 
				 "[7.1.1.] Em se tratando de inadimplência de obrigações de pagamento, ficará ainda sujeita a parte inadimplente ao pagamento de juros de mora de 1% ao mês e atualizações monetárias pela variação do IGPM-FGV (Índice Geral de Preços de Mercado – Fundação Getúlio Vargas) calculados “pro rata die” entre a data do vencimento da obrigação e a data do efetivo pagamento. Considerar-se-á, também, rescindido de pleno direito o presente [Contrato], no caso de impetração, requerimento ou decretação de falência, insolvência, recuperação ou liquidação judicial ou extrajudicial do [Vendedor]. Na hipótese de extinção do IGPM-FGV aplicar-se-á o seu substituto na forma da lei e, se não existir substituto, será aplicado o índice Geral de Preços ao Consumidor (IPC) editado pela Fundação Instituto de Pesquisa Econômica (FIPE).\r\n"
				+ 
				"[7.1.2.] Fica convencionado entre as [Partes], em caráter irrevogável e irretratável, que o valor mínimo da indenização por perdas e danos devida pela [Parte] inadimplente à [Parte] inocente, nos termos do disposto no item [(iv)] da cláusula [08.1], nunca será inferior ao maior valor dentre os seguintes:\r\n"
				+ "[(i)] 20% (vinte por cento) [Preço] da quantidade de [Produto] não entregue pelo [Vendedor] ao [Comprador] nos termos deste [Contrato]; ou\r\n"
				+ "[(ii)] O valor correspondente à diferença entre o preço de mercado do [Produto] não entregue e o [Preço] estabelecido na forma do presente [Contrato]. O preço de mercado da quantidade de [Produto] não entregue será equivalente à média dos preços de compra do [Produto] que estiverem sendo praticados, no [Local] [de] [Entrega], na [Data] [de] [Entrega], pelo [Comprador] e pelas seguintes empresas, desde que operem no [Local] [de] [Entrega]: Cargill, Bunge, ADM e Cocari.\r\n"
				+ 
				 "[7.2.] Na hipótese de uma das [Partes] ver-se obrigada a recorrer aos meios judiciais para fazer valer o presente [Contrato], além da multa penal, das perdas e danos e da restituição das importâncias eventualmente antecipadas, acrescidas dos juros e correção monetária previstos na cláusula [08.1.1], a [Parte] inadimplente ficará sujeito ao pagamento das custas e despesas judiciais incorridas pela outra [Parte], bem como honorários advocatícios desde já fixados em 20% do montante devido.\r\n"
				+ 
				 "[7.3.] Não constituirão inadimplência deste [Contrato]: [(i)] eventuais atrasos bancários alheios à vontade do [Comprador]; [(ii)] o não pagamento do [Preço] em decorrência do descumprimento, pelo [Vendedor], de quaisquer das obrigações a ele atribuídas no presente [Contrato].\r\n"
				+ 
				 "[8.] [Disposições] [Finais].\r\n"
				+ "O presente [Contrato] constitui título executivo nos termos do artigo 585, inciso II, do Código de Processo Civil, considerando-se líquidos, certos e exigíveis todos os valores dele decorrentes, inclusive e em especial os montantes que venham a ser devidos pelo [Vendedor] nos termos das cláusulas [08.1] a [08.2] retro, desistindo desde já este último, em caráter irrevogável e irretratável, de qualquer processo especial de verificação de ditos montantes, bastando para sua comprovação os comprovantes dos pagamentos efetuados pelo [Comprador] ou um demonstrativo sintético da composição da dívida.\r\n"
				+ 
				"[8.1.] É vedado ao [Vendedor] ceder, dar em garantia, securitizar, transferir a terceiros ou emitir ou sacar títulos representativos de quaisquer créditos que detenha contra o [Comprador] e que sejam relacionados ao presente Contrato, sem a prévia e expressa autorização escrita do [Comprador]. Qualquer cessão realizada em descumprimento desta obrigação será considerada nula de pleno direito, sendo considerados regularmente realizados e liberatórios da obrigação de pagamento do [Comprador] todos os pagamentos feitos pelo [Comprador] diretamente ao [Vendedor] nos termos deste [Contrato]. O [Comprador] fica desde já autorizado pelo Vendedor a ceder ou transferir os direitos ou obrigações decorrentes deste [Contrato] a quaisquer terceiros, mediante simples comunicação escrita ao [Vendedor].\r\n"
				+ 
				 "[8.2.] A tolerância, por qualquer das [Partes], em relação ao descumprimento de quaisquer dos compromissos recíprocos aqui avençados, constituirá mera liberalidade e não poderá de forma alguma ser caracterizada como novação ou precedente invocável pela outra parte.\r\n"
				+ 
				"[8.3.] O presente [Contrato] é celebrado em caráter irrevogável e irretratável, perfeito e acabado, o qual obriga não só as [Partes] ora contratantes, como também seus herdeiros e/ou sucessores a qualquer título.\r\n"
				+ 
				"[8.4.] Todas as obrigações atribuídas ao [Comprador] ao abrigo do presente [Contrato] poderão ser adimplidas por meio de compensação, independentemente de prestação de contas e nos termos dos artigos 368 e seguintes do Código Civil. \r\n"
				+ 
				 "[8.5.] Para dirimir quaisquer questões decorrentes deste [Contrato], as [Partes] elegem o foro da Comarca de Vazante, Estado de Minas Gerais, renunciando qualquer outro, por mais privilegiado que seja. \r\n"
				+ 
				"E por estarem assim justas e contratadas, firmam as [Partes] o presente instrumento em 2 (duas) vias de igual teor e forma, na presença de 2 (duas) testemunha.\r\n"
				);
	}
	
	public void adicionarClausulaPagamento() {
		String text_amostra = "";
		
		if(novo_contrato.getTipo_entrega() == 1)
		 text_amostra = "[6.2.] O pagamento da quantidade de quanti_total ( quanti_total_extenso ) [quilogramas] | quanti_total_sacos ( quanti_total_sacos_extenso ) [sacos], no valor total de valor_total ( valor_total_extenso ) se dará mediante crédito conforme tabelas de pagamentos a seguir:";
		else
			 text_amostra = "[5.2.] O pagamento da quantidade de quanti_total ( quanti_total_extenso ) [quilogramas] | quanti_total_sacos ( quanti_total_sacos_extenso ) [sacos], no valor total de valor_total ( valor_total_extenso ) se dará mediante crédito conforme tabelas de pagamentos a seguir:";

		NumberFormat z = NumberFormat.getNumberInstance();

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
                if(palavra.equals("quanti_total")) {
                	 
                	if(novo_contrato.getMedida().equalsIgnoreCase("SACOS")) {
            			double quantidade_sacos = novo_contrato.getQuantidade();
            			double quantidade_kg  = quantidade_sacos * 60;

	    		        adicionarTextoParagrafoAtual(z.format(quantidade_kg) + " ", true);


            		}else if(novo_contrato.getMedida().equalsIgnoreCase("KG")) {
	    		        adicionarTextoParagrafoAtual(z.format(novo_contrato.getQuantidade()) + " ", true);

            		}
        	
                }else if(palavra.equals("quanti_total_extenso")) {

                	if(novo_contrato.getMedida().equalsIgnoreCase("SACOS")) {
            			double quantidade_sacos = novo_contrato.getQuantidade();
            			double quantidade_kg  = quantidade_sacos * 60;

	    		        adicionarTextoParagrafoAtual(new PorExtenso(quantidade_kg).toString().replace("reais", "").replace("centavos", "").toLowerCase() + " ", true);


            		}else if(novo_contrato.getMedida().equalsIgnoreCase("KG")) {
	    		        adicionarTextoParagrafoAtual(new PorExtenso(novo_contrato.getQuantidade()).toString().replace("reais", "").replace("centavos", "").toLowerCase() + " ", true);

            		}
                }else if(palavra.equals("quanti_total_sacos")) {
                	 
                	if(novo_contrato.getMedida().equalsIgnoreCase("SACOS")) {
            			double quantidade_sacos = novo_contrato.getQuantidade();

	    		        adicionarTextoParagrafoAtual(z.format(quantidade_sacos) + " ", true);


            		}else if(novo_contrato.getMedida().equalsIgnoreCase("KG")) {
            			double quantidade_sacos = novo_contrato.getQuantidade() / 60;

            			
	    		        adicionarTextoParagrafoAtual(z.format(quantidade_sacos) + " ", true);

            		}
        	
                }else if(palavra.equals("quanti_total_sacos_extenso")) {
                	 
                	if(novo_contrato.getMedida().equalsIgnoreCase("SACOS")) {
            			double quantidade_sacos = novo_contrato.getQuantidade();

	    		        adicionarTextoParagrafoAtual(new PorExtenso(quantidade_sacos).toString().replace("reais", "").replace("centavos", "").toLowerCase() + " ", true);

            		}else if(novo_contrato.getMedida().equalsIgnoreCase("KG")) {
            			double quantidade_sacos = novo_contrato.getQuantidade() / 60;

	    		        adicionarTextoParagrafoAtual(new PorExtenso(quantidade_sacos).toString().replace("reais", "").replace("centavos", "").toLowerCase() + " ", true);

            		}
        	
                }
                else if(palavra.equals("valor_total")) {
               	 
                	Locale ptBr = new Locale("pt", "BR");
		       	   	String valorString = NumberFormat.getCurrencyInstance(ptBr).format((novo_contrato.getValor_a_pagar()));
  	    		   adicionarTextoParagrafoAtual(valorString + " ", true);

            		
                }else if(palavra.equals("valor_total_extenso")){
 	    		   adicionarTextoParagrafoAtual(new PorExtenso(novo_contrato.getValor_a_pagar()).toString().toLowerCase() + " ", true);

                }
                
                else {
		        adicionarTextoParagrafoAtual(palavra + " ", false);
                }
			}

		}
		}

		
	}
	
	public void adicionarClausulaPagamentoAntecipado(double quantidade_total_recebidas_quilogramas, double quantidade_total_recebidas_sacos , double valor_total_ja_recebido) {
		String text_amostra = "";
		
		if(novo_contrato.getTipo_entrega() == 1)
		 text_amostra = "[6.2.] O pagamento da quantidade de quanti_total [quilogramas] | quanti_total_sacos [sacos], onde o [Vendedor] deste [Contrato] por modalidade de [pagamento antecipado] já recebeu o valor de valor_total_ja_recebido  referentes a quanti_total_recebida  [quilogramas] | quanti_total_recebida_sacos  sacos, sendo assim a quantidade a ser recebida será referente aos quanti_total_restante  [quilogramas] | quanti_total_sacos_restante  sacos restantes ainda não pagos, o valor restante a ser recebido no total de  valor_total_restante  ( valor_total_restante_extenso ) será de acordo com a tabela de pagamentos abaixo:";
		else
			 text_amostra = "[5.2.] O pagamento da quantidade de quanti_total [quilogramas] | quanti_total_sacos [sacos], onde o [Vendedor] deste [Contrato] por modalidade de [pagamento antecipado] já recebeu o valor de valor_total_ja_recebido  referentes a quanti_total_recebida  [quilogramas] | quanti_total_recebida_sacos  sacos, sendo assim a quantidade a ser recebida será referente aos quanti_total_restante  [quilogramas] | quanti_total_sacos_restante  sacos restantes ainda não pagos, o valor restante a ser recebido no total de  valor_total_restante  ( valor_total_restante_extenso ) será de acordo com a tabela de pagamentos abaixo:";

		//criarParagrafo(2);

		NumberFormat z = NumberFormat.getNumberInstance();

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
		                if(palavra.equals("quanti_total")) {
		                	 
		                	if(novo_contrato.getMedida().equalsIgnoreCase("SACOS")) {
		            			double quantidade_sacos = novo_contrato.getQuantidade();
		            			double quantidade_kg  = quantidade_sacos * 60;

			    		        adicionarTextoParagrafoAtual(z.format(quantidade_kg) + " ", true);


		            		}else if(novo_contrato.getMedida().equalsIgnoreCase("KG")) {
			    		        adicionarTextoParagrafoAtual(z.format(novo_contrato.getQuantidade()) + " ", true);

		            		}
		        	
		                }else if(palavra.equals("quanti_total_extenso")) {

		                	if(novo_contrato.getMedida().equalsIgnoreCase("SACOS")) {
		            			double quantidade_sacos = novo_contrato.getQuantidade();
		            			double quantidade_kg  = quantidade_sacos * 60;

			    		        adicionarTextoParagrafoAtual(new PorExtenso(quantidade_kg).toString().replace("reais", "").replace("centavos", "").toLowerCase() + " ", true);


		            		}else if(novo_contrato.getMedida().equalsIgnoreCase("KG")) {
			    		        adicionarTextoParagrafoAtual(new PorExtenso(novo_contrato.getQuantidade()).toString().replace("reais", "").replace("centavos", "").toLowerCase() + " ", true);

		            		}
		                }else if(palavra.equals("quanti_total_sacos")) {
		                	 
		                	if(novo_contrato.getMedida().equalsIgnoreCase("SACOS")) {
		            			double quantidade_sacos = novo_contrato.getQuantidade();

			    		        adicionarTextoParagrafoAtual(z.format(quantidade_sacos) + " ", true);


		            		}else if(novo_contrato.getMedida().equalsIgnoreCase("KG")) {
		            			double quantidade_sacos = novo_contrato.getQuantidade() / 60;

		            			
			    		        adicionarTextoParagrafoAtual(z.format(quantidade_sacos) + " ", true);

		            		}
		        	
		                }else if(palavra.equals("quanti_total_sacos_extenso")) {
		                	 
		                	if(novo_contrato.getMedida().equalsIgnoreCase("SACOS")) {
		            			double quantidade_sacos = novo_contrato.getQuantidade();

			    		        adicionarTextoParagrafoAtual(new PorExtenso(quantidade_sacos).toString().replace("reais", "").replace("centavos", "").toLowerCase() + " ", true);

		            		}else if(novo_contrato.getMedida().equalsIgnoreCase("KG")) {
		            			double quantidade_sacos = novo_contrato.getQuantidade() / 60;

			    		        adicionarTextoParagrafoAtual(new PorExtenso(quantidade_sacos).toString().replace("reais", "").replace("centavos", "").toLowerCase() + " ", true);

		            		}
		                }
		                else if(palavra.equals("quanti_total_recebida")) {
		    		        adicionarTextoParagrafoAtual(z.format(quantidade_total_recebidas_quilogramas), true);

		                }
		                else if(palavra.equals("quanti_total_recebida_extenso")) {
		    		        adicionarTextoParagrafoAtual(new PorExtenso(quantidade_total_recebidas_quilogramas).toString().replace("reais", "").replace("centavos", "").toLowerCase() + " ", true);


		                }
		                else if(palavra.equals("quanti_total_recebida_sacos")) {
		    		        adicionarTextoParagrafoAtual(z.format(quantidade_total_recebidas_sacos), true);

		                }
		                else if(palavra.equals("quanti_total_recebida_sacos_extenso")) {
		    		        adicionarTextoParagrafoAtual(new PorExtenso(quantidade_total_recebidas_sacos).toString().replace("reais", "").replace("centavos", "").toLowerCase() + " ", true);


		                }
		                else if(palavra.equals("valor_total_ja_recebido")) {
		            	  	Locale ptBr = new Locale("pt", "BR");
				       	   	String valorString = NumberFormat.getCurrencyInstance(ptBr).format((valor_total_ja_recebido));
				       	   	System.out.println(valorString);   
	        		
		    		        adicionarTextoParagrafoAtual(valorString, true);


		                }
		                else if(palavra.equals("valor_total_restante")) {
		            	  	Locale ptBr = new Locale("pt", "BR");
				       	   	String valorString = NumberFormat.getCurrencyInstance(ptBr).format((Double.parseDouble(novo_contrato.getValor_a_pagar().toPlainString()) - valor_total_ja_recebido));
				       	   	System.out.println(valorString);   
	        		
		    		        adicionarTextoParagrafoAtual(valorString, true);


		                }
		                else if(palavra.equals("valor_total_restante_extenso")) {
		            	  
	        		        double result = Double.parseDouble(novo_contrato.getValor_a_pagar().toPlainString()) - valor_total_ja_recebido;
		    		        adicionarTextoParagrafoAtual(new PorExtenso(result).toString().toLowerCase() + " ", true);


		                }
		                else if(palavra.equals("quanti_total_restante")) {
		                	
		                	if(novo_contrato.getMedida().equalsIgnoreCase("SACOS")) {
		                		double quant_sacos = novo_contrato.getQuantidade();
		                		double quant_kg = quant_sacos * 60;
		                			
				                 double result = quant_kg - quantidade_total_recebidas_quilogramas;
				    		        adicionarTextoParagrafoAtual(z.format(result), true);


		                	}else if(novo_contrato.getMedida().equalsIgnoreCase("KG")) {
		                		double quant_kg = novo_contrato.getQuantidade();
		                		double quant_sacos = quant_kg/60;

				                 double result = quant_kg - quantidade_total_recebidas_quilogramas;
				    		        adicionarTextoParagrafoAtual(z.format(result), true);
		                	}
		                	
		                	
		                	

		                }
		                else if(palavra.equals("quanti_total_restante_extenso")) {
		                	if(novo_contrato.getMedida().equalsIgnoreCase("SACOS")) {
		                		double quant_sacos = novo_contrato.getQuantidade();
		                		double quant_kg = quant_sacos * 60;
		                			
				                 double result = quant_kg - quantidade_total_recebidas_quilogramas;
				    		        adicionarTextoParagrafoAtual(new PorExtenso(result).toString().replace("reais", "").replace("centavos", "").toLowerCase() + " ", true);


		                	}else if(novo_contrato.getMedida().equalsIgnoreCase("KG")) {
		                		double quant_kg = novo_contrato.getQuantidade();
		                		double quant_sacos = quant_kg/60;

				                 double result = quant_kg - quantidade_total_recebidas_quilogramas;
				    		        adicionarTextoParagrafoAtual(new PorExtenso(result).toString().replace("reais", "").replace("centavos", "").toLowerCase() + " ", true);
		                	}



		                }
		                else if(palavra.equals("quanti_total_sacos_restante")) {
		                 	if(novo_contrato.getMedida().equalsIgnoreCase("SACOS")) {
		                		double quant_sacos = novo_contrato.getQuantidade();
		                		double quant_kg = quant_sacos * 60;
		                			
				                 double result = quant_sacos - quantidade_total_recebidas_sacos;
				    		        adicionarTextoParagrafoAtual(z.format(result), true);


		                	}else if(novo_contrato.getMedida().equalsIgnoreCase("KG")) {
		                		double quant_kg = novo_contrato.getQuantidade();
		                		double quant_sacos = quant_kg/60;

				                 double result = quant_sacos - quantidade_total_recebidas_sacos;
				    		        adicionarTextoParagrafoAtual(z.format(result), true);
		                	}

			                }
			                else if(palavra.equals("quanti_total_sacos_restante_extenso")) {
			                	if(novo_contrato.getMedida().equalsIgnoreCase("SACOS")) {
			                		double quant_sacos = novo_contrato.getQuantidade();
			                		double quant_kg = quant_sacos * 60;
			                			
					                 double result = quant_sacos - quantidade_total_recebidas_sacos;
					    		       adicionarTextoParagrafoAtual(new PorExtenso(result).toString().replaceAll("reais", "").replaceAll("centavos", "").toLowerCase() + " ", true);


			                	}else if(novo_contrato.getMedida().equalsIgnoreCase("KG")) {
			                		double quant_kg = novo_contrato.getQuantidade();
			                		double quant_sacos = quant_kg/60;

					                 double result = quant_sacos - quantidade_total_recebidas_sacos;
					                 String extenso = new PorExtenso(result).toString().replace("centavos", "");
					                 extenso =  extenso.replace("reais", "");
					    		        adicionarTextoParagrafoAtual( extenso, true);
			                	}



			                }
		               
		                else {
				        adicionarTextoParagrafoAtual(palavra + " ", false);
		                }
		                
					

				}
				}//fim do for
		
	}//fim for externo
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
	
	public void adicionarCamposAssinaturas() {
		
	
		 compradores = novo_contrato.listaCompradores();
		  vendedores = novo_contrato.listaVendedores();
		  corretores = novo_contrato.listaCorretores();
		
			// adiciona assinatura de Corretorres
			for(CadastroCliente corretor : corretores) {
				if(corretor != null) {
					String nome_assinatura = "";
					if (corretor.getTipo_pessoa() == 0) {
						// pessoa fisica
						nome_assinatura = corretor.getNome_empresarial();
					} else {
						nome_assinatura = corretor.getNome_fantaia();

					}
					String separados [] = nome_assinatura.split(" ");
					String nome_assinatura_negrito = "";
					
					for(String palavra : separados) {
						nome_assinatura_negrito += "[" + palavra + "]" + " ";
					}
					
					
					substituirTexto("[_______________________________________________________________]                                                                                         "
							+ "["  + nome_assinatura_negrito.toUpperCase() + "]\r\n"
							+ "");
				
				}
			}


			// adicionar assinatura de vendedores
			// adiciona assinatura de Corretorres
						for(CadastroCliente vendedor : vendedores) {
							if(vendedor != null) {
								String nome_assinatura = "";
								if (vendedor.getTipo_pessoa() == 0) {
									// pessoa fisica
									nome_assinatura = vendedor.getNome_empresarial();
								} else {
									nome_assinatura = vendedor.getNome_fantaia();

								}
								
								String separados [] = nome_assinatura.split(" ");
								String nome_assinatura_negrito = "";
								
								for(String palavra : separados) {
									nome_assinatura_negrito += "[" + palavra + "]" + " ";
								}
								
								
								substituirTexto("[_______________________________________________________________]                                                                                         "
										+ "["  + nome_assinatura_negrito.toUpperCase() + "]\r\n"
										+ "");
							
							}
						}


			// adicionar assinatura de compradores
						// adiciona assinatura de Corretorres
						for(CadastroCliente comprador : compradores) {
							if(comprador != null) {
								String nome_assinatura = "";
								if (comprador.getTipo_pessoa() == 0) {
									// pessoa fisica
									nome_assinatura = comprador.getNome_empresarial();
								} else {
									nome_assinatura = comprador.getNome_fantaia();

								}
								String separados [] = nome_assinatura.split(" ");
								String nome_assinatura_negrito = "";
								
								for(String palavra : separados) {
									nome_assinatura_negrito += "[" + palavra + "]" + " ";
								}
								
								
								substituirTexto("[_______________________________________________________________]                                                                                         "
										+ "["  + nome_assinatura_negrito.toUpperCase() + "]\r\n\n"
										+ "");
							
							}
						}

		
		//testemunhas
		substituirTexto("[TESTEMUNHAS:]\r\n"
				+ 
				"[1._____________________________________________________________]                                                                                         "
				+ "Nome:                                                                                                                                                                                                              "
				+ "CPF:                                                                                                                                                                                                                 "
				);
		
		substituirTexto(
				"[2._____________________________________________________________]                                                                                         "
				+ "Nome:                                                                                                                                                                                                              "
				+ "CPF:                                                                                                                                                                                                                 "
				);
		
		

	}
	
	public int salvar( int tipo_salvamento) 
	{
		int retorno_final = -1;
		boolean proceder = false;
		
		if(tipo_salvamento == 1) {
			//esta em edicao, apagar os arquivos fisicos e na nuvem
			String BaseDados_DiretorioArquivo =  servidor_unidade + novo_contrato.getCaminho_arquivo();
			System.out.println("caminho do arquivo completo para apagar: " + BaseDados_DiretorioArquivo);
			ManipularTxt manipular_apagar = new ManipularTxt();
			if(manipular_apagar.apagarArquivo(BaseDados_DiretorioArquivo)) {
				if(manipular_apagar.apagarArquivo(BaseDados_DiretorioArquivo.replace("pdf", "docx"))) {
					
					//apagar o diretorio
					if(manipular_apagar.limparDiretorio(new File(servidor_unidade + novo_contrato.getCaminho_diretorio_contrato()))) {
					  //Diretorio foi excluido
						Nuvem nuvem = new Nuvem();
				         nuvem.abrir();
				         nuvem.testar();
				         nuvem.listar();
				         nuvem.deletarArquivo(novo_contrato.getNome_arquivo());
				       JOptionPane.showMessageDialog(null, "Os arquivos foram apagados da memoria e da nuvem!");
					
				       proceder = true;
					}
					else {
						proceder = false;
						System.out.println("Erro ao excluir o direrorio do contrato");

					}
				}else {
					
					if(manipular_apagar.apagarArquivo(BaseDados_DiretorioArquivo.replace("pdf", "xlsx"))) {
						//apagar o diretorio
						if(manipular_apagar.limparDiretorio(new File(servidor_unidade + novo_contrato.getCaminho_diretorio_contrato()))) {
						  //Diretorio foi excluido
							Nuvem nuvem = new Nuvem();
					         nuvem.abrir();
					         nuvem.testar();
					         nuvem.listar();
					         nuvem.deletarArquivo(novo_contrato.getNome_arquivo());
					       JOptionPane.showMessageDialog(null, "Os arquivos foram apagados da memoria e da nuvem!");
						
					       proceder = true;
						}
						else {
							proceder = false;
							System.out.println("Erro ao excluir o direrorio do contrato");

						}
					}
					else {
						System.out.println("erro ao excluir arquivo docx, operação cancelada!");
						retorno_final = 4;
						proceder = false;
					}
					
					
				}
				
			}else {
				System.out.println("erro ao excluir arquivo .pdf, operação cancelada!");
				retorno_final = 4;
				proceder = false;
			}
			
		}

	  if(tipo_salvamento == 1 && proceder || tipo_salvamento == 0) {
        boolean arquivos_comprador_criado = false;
		boolean arquivos_vendedor1_criado = false;
		boolean arquivos_vendedor2_criado = false;

		
		String nome_comprador_arquivo ;
		String nome_vendedor1_arquivo ;
		String nome_vendedor2_arquivo;
		
		if(compradores[0].getTipo_pessoa() == 0)
			nome_comprador_arquivo = compradores[0].getNome_empresarial();
		else
			nome_comprador_arquivo = compradores[0].getNome_fantaia();

		if(vendedores[0].getTipo_pessoa() == 0)
			nome_vendedor1_arquivo = vendedores[0].getNome_empresarial();
		else
			nome_vendedor1_arquivo = vendedores[0].getNome_fantaia();
		
		if(vendedores[1] != null) {
		if(vendedores[1].getTipo_pessoa() == 0)
			nome_vendedor2_arquivo = vendedores[1].getNome_empresarial();
		else
			nome_vendedor2_arquivo = vendedores[1].getNome_fantaia();
		}else
			nome_vendedor2_arquivo = null;
		
		//E:\E-Contract\arquivos\clientes\Daniel Alves de Almeida\contratos\compra\2020\milho
		
		GetData data = new GetData();
		
	   if(novo_contrato.getSub_contrato() == 0) {
		   //é um comprato pai, salvar na pasta do comprador

			 String caminho_salvar_contrato__no_hd = servidor_unidade + "E-contract\\arquivos\\clientes\\" + nome_comprador_arquivo + "\\contratos" + "\\compra\\"  + data.getAnoAtual() + "\\" + novo_contrato.getModelo_safra().getProduto().getNome_produto() + "\\";
			System.out.println("caminho para salvar o contrato do comprador no hd: " + caminho_salvar_contrato__no_hd);	
			String caminho_salvar_contrato_no_banco_dados = "E-contract\\\\arquivos\\\\clientes\\\\" + nome_comprador_arquivo + "\\\\contratos" + "\\\\compra\\\\"  + data.getAnoAtual() + "\\\\" + novo_contrato.getModelo_safra().getProduto().getNome_produto() + "\\\\";

		      String nome_pasta_arquivo = novo_contrato.getCodigo();

		      String nome_arquivo = novo_contrato.getCodigo() + " "  + nome_comprador_arquivo + " x " + nome_vendedor1_arquivo ; 
		       
				if(nome_vendedor2_arquivo != null) {
					nome_arquivo +=  (" " + nome_vendedor2_arquivo);

				}
				
				String extensao_arquivo = ".docx";
				String caminho_completo_salvar_contrato_no_hd = caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\" + nome_arquivo ;
				
			    String caminho_completo_salvar_contrato_no_bando_dados = caminho_salvar_contrato_no_banco_dados + nome_pasta_arquivo +"\\\\" + nome_arquivo;
			    String nome_diretorio_arquivo_contrato = caminho_salvar_contrato_no_banco_dados + nome_pasta_arquivo;

				//criar o diretorio
				ManipularTxt manipular = new ManipularTxt();
				if(manipular.criarDiretorio(caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\"))
				{
					System.out.println("diretorio criado para o novo contrato");
					  if(criarArquivos(nome_arquivo,caminho_completo_salvar_contrato_no_hd,  caminho_completo_salvar_contrato_no_bando_dados,nome_diretorio_arquivo_contrato))
					  { 	
					 
						  
						  if(manipular.criarDiretorio(caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\comprovantes"))
						{
                                  //criar diretorio documentos
						  if(manipular.criarDiretorio(caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\documentos"))
							{
							  arquivos_comprador_criado = true;

							}else {
								arquivos_comprador_criado = false;

							}	
						}else {
							arquivos_comprador_criado = false;

						}	
				       } 
					  else
					    	arquivos_comprador_criado = false;
				}else {
					System.out.println("erro ao criar diretorio para o contrato ");
					arquivos_comprador_criado = false;
				}
				
				
			  

		   
	   }else {
		   //e um contrato filho, salvar nas pastas dos vendedores
		   if(vendedores[0] != null) {
			   System.out.println("vendedor 1 existe para este contrato");

			   if(vendedores[0].getNome_empresarial() != null || vendedores[0].getNome_fantaia() != null){
 
				   String caminho_salvar_contrato__no_hd = servidor_unidade + "E-contract\\arquivos\\clientes\\" + nome_vendedor1_arquivo + "\\contratos" + "\\venda\\"  + data.getAnoAtual() + "\\" + novo_contrato.getModelo_safra().getProduto().getNome_produto() + "\\";
					System.out.println("caminho para salvar o contrato do comprador no hd: " + caminho_salvar_contrato__no_hd);	
					String caminho_salvar_contrato_no_banco_dados = "E-contract\\\\arquivos\\\\clientes\\\\" + nome_vendedor1_arquivo + "\\\\contratos" + "\\\\venda\\\\"  + data.getAnoAtual() + "\\\\" + novo_contrato.getModelo_safra().getProduto().getNome_produto() + "\\\\";

				      String nome_pasta_arquivo = novo_contrato.getCodigo();
                     
				       
				      String nome_arquivo = novo_contrato.getCodigo() + " "  + nome_comprador_arquivo + " x " + nome_vendedor1_arquivo ; 
				       
						if(nome_vendedor2_arquivo != null) {
							nome_arquivo +=  (" " + nome_vendedor2_arquivo);

						}
						
						String extensao_arquivo = ".docx";
						String caminho_completo_salvar_contrato_no_hd = caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\" + nome_arquivo ;
						
					    String caminho_completo_salvar_contrato_no_bando_dados = caminho_salvar_contrato_no_banco_dados + nome_pasta_arquivo +"\\\\" + nome_arquivo;
					    String nome_diretorio_arquivo_sub_contrato1 = caminho_salvar_contrato_no_banco_dados + nome_pasta_arquivo;
						//criar o diretorio
						ManipularTxt manipular = new ManipularTxt();
						if(manipular.criarDiretorio(caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\"))
						{
							System.out.println("diretorio criado para o novo sub contrato vendedor1");
							  if(criarArquivos(nome_arquivo,caminho_completo_salvar_contrato_no_hd,  caminho_completo_salvar_contrato_no_bando_dados, nome_diretorio_arquivo_sub_contrato1))
							    	{
							    	
								  if(manipular.criarDiretorio(caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\comprovantes"))
									{
									  //criar diretorio documentos
									  if(manipular.criarDiretorio(caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\documentos"))
										{
										  arquivos_vendedor1_criado = true;

										}else {
											arquivos_vendedor1_criado = false;

										}	

									}else {
										arquivos_vendedor1_criado = false;

									}	
							    	
							    	}else
							    	arquivos_vendedor1_criado = false;
						}else {
							System.out.println("erro ao criar diretorio para o sub contrato vendedor1");
							arquivos_vendedor1_criado = false;
						}
					
				
			   }

			   
		   }
		   
		   if(vendedores[1] != null) {
			   System.out.println("vendedor 2 existe para este contrato");
			   
			   String caminho_salvar_contrato__no_hd = servidor_unidade + "E-contract\\arquivos\\clientes\\" + nome_vendedor2_arquivo + "\\contratos" + "\\venda\\"  + data.getAnoAtual() + "\\" + novo_contrato.getModelo_safra().getProduto().getNome_produto() + "\\";
				System.out.println("caminho para salvar o contrato do comprador no hd: " + caminho_salvar_contrato__no_hd);	
				String caminho_salvar_contrato_no_banco_dados = "E-contract\\\\arquivos\\\\clientes\\\\" + nome_vendedor2_arquivo + "\\\\contratos" + "\\\\venda\\\\"  + data.getAnoAtual() + "\\\\" + novo_contrato.getModelo_safra().getProduto().getNome_produto() + "\\\\";

			      String nome_pasta_arquivo = novo_contrato.getCodigo();

			      String nome_arquivo = novo_contrato.getCodigo() + " "  + nome_comprador_arquivo + " x " + nome_vendedor1_arquivo ; 
			       
					if(nome_vendedor2_arquivo != null) {
						nome_arquivo +=  (" " + nome_vendedor2_arquivo);

					}
					
					String extensao_arquivo = ".docx";
					String caminho_completo_salvar_contrato_no_hd = caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\" + nome_arquivo ;
					
				    String caminho_completo_salvar_contrato_no_bando_dados = caminho_salvar_contrato_no_banco_dados + nome_pasta_arquivo +"\\\\" + nome_arquivo;
				    String nome_diretorio_arquivo_sub_contrato2 = caminho_salvar_contrato_no_banco_dados + nome_pasta_arquivo;

					//criar o diretorio
					ManipularTxt manipular = new ManipularTxt();
					if(manipular.criarDiretorio(caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\"))
					{
						System.out.println("diretorio criado para o novo sub contrato vendedor2");
						  if(criarArquivos(nome_arquivo,caminho_completo_salvar_contrato_no_hd,  caminho_completo_salvar_contrato_no_bando_dados, nome_diretorio_arquivo_sub_contrato2))
						    	{
							  
							  if(manipular.criarDiretorio(caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\comprovantes"))
								{
								  //criar diretorio documentos
								  if(manipular.criarDiretorio(caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\documentos"))
									{
									  arquivos_vendedor2_criado = true;

									}else {
										arquivos_vendedor2_criado = false;

									}	

								}else {
									arquivos_vendedor2_criado = false;

								}	
							  
						    	}else
						    	arquivos_vendedor2_criado = false;
					}else {
						System.out.println("erro ao criar diretorio para o sub contrato vendedor2");
						arquivos_vendedor2_criado = false;
					}
				   

			   
		   }
		 
	   }
		
	   if(novo_contrato.getSub_contrato() == 0) {
		   //e comtrato pai, retorno apenas oa varaivel do comprador
		   if(arquivos_comprador_criado) {
			   retorno_final = 1; //avisa para quem chamou a funcao que o contrato do comprador foi salvo
		   GerenciadorLog.registrarLogDiario("aviso", "o contrato de numero " + novo_contrato.getCodigo() + " foi criado na base de arquivos fisico");
	        }else {
				   retorno_final = -1; //avisa para quem chamou a funcao que o contrato do comprador não foi salvo
				   GerenciadorLog.registrarLogDiario("falha", "o contrato de numero " + novo_contrato.getCodigo() + " não foi criado na base de arquivos fisico");

	        }
	   }else {
		   if(vendedores[0] != null && vendedores[1] != null) {
			   //ha dois vendedores, retorno as duas variaveis
			   if(arquivos_vendedor1_criado && arquivos_vendedor1_criado) {
				   retorno_final = 10; //avisa pra quem chamou a funcao que o contrato do vendedor1 e do vendedor2 foi salvo
				   GerenciadorLog.registrarLogDiario("aviso", "o sub_contrato de numero " + novo_contrato.getCodigo() + " foi criado na base de arquivos fisico");

			   }else {
				   retorno_final = 11; //avisa pra quem chamou a funcao que o contrato do vendedor1 e do vendedor2 não foram salvo
				   GerenciadorLog.registrarLogDiario("falha", "o sub_contrato de numero " + novo_contrato.getCodigo() + " não foi criado na base de arquivos fisico");

			   }
		   }else if (vendedores[0] != null && vendedores[1] == null){
			   if(arquivos_vendedor1_criado) {
				   retorno_final = 12; //avisa pra quem chamou a funcao que o contrato do vendedor1  foi salvo
				   GerenciadorLog.registrarLogDiario("aviso", "o sub_contrato de numero " + novo_contrato.getCodigo() + "  foi criado na base de arquivos fisico");

			   } else {
				   retorno_final = 13; //avisa pra quem chamou a funcao que o contrato do vendedor1  não foram salvo
				   GerenciadorLog.registrarLogDiario("falha", "o sub_contrato de numero " + novo_contrato.getCodigo() + " não foi criado na base de arquivos fisico");

			   }
		   }else if (vendedores[0] == null && vendedores[1] != null){
			   if(arquivos_vendedor2_criado) {
				   retorno_final = 14; //avisa pra quem chamou a funcao que o contrato do vendedor2  foi salvo
				   GerenciadorLog.registrarLogDiario("aviso", "o sub_contrato de numero " + novo_contrato.getCodigo() + "  foi criado na base de arquivos fisico");

			   } else {
				   retorno_final = 15; //avisa pra quem chamou a funcao que o contrato do vendedor2  não foram salvo
				   GerenciadorLog.registrarLogDiario("falha", "o sub_contrato de numero " + novo_contrato.getCodigo() + " não foi criado na base de arquivos fisico");

			   }
		   }

	   }
	  }else {
		  
	  }
		
		return retorno_final; 
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
