package manipular;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

import org.apache.pdfbox.printing.Orientation;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDocument1;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;

import com.itextpdf.text.PageSize;

import cadastros.CadastroCliente;
import cadastros.CadastroContrato;
import cadastros.CadastroLogin;
import cadastros.CadastroModelo;
import outros.DadosGlobais;
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
		
	}
	
	public ByteArrayOutputStream preparar() {
		

		 compradores = novo_contrato.listaCompradores();
		  vendedores = novo_contrato.listaVendedores();
		  corretores = novo_contrato.listaCorretores();
		  
		// cria o paragrafo do titulo
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
		adicionarClausula2_1();
		adicionarClausula3();
		substituirTexto("[3.1.] O [Vendedor] deverá iniciar a entrega do [Produto] ao [Comprador] imediatamente a partir do início da sua colheita. Na hipótese de, a partir do momento em que o [Produto] estiver apto a ser colhido, ser constatado o transporte ou entrega de qualquer quantidade do [Produto] a qualquer outro destino que não o [Local de Entrega], restará caracterizado seu desvio para terceiros, o que caracterizará inadimplemento contratual de pleno direito, de forma automática e independentemente de qualquer formalidade adicional.");
		substituirTexto("[4.] [Qualidade:] Quando de sua entrega pelo [Vendedor] ao [Comprador], o [Produto] objeto do presente [Contrato] deverá atender às especificações de qualidade indicadas na [Tabela 1] abaixo (“[Especificações]”). O [Comprador] poderá, a seu exclusivo critério, receber [Produto] que não atenda às [Especificações]. Nesta hipótese, o [Produto] entregue fora das [Especificações] estará sujeito aos descontos indicados na [Tabela] [1] abaixo (“[Descontos]”):");
		
		
		substituirTexto("[Tabela] [1] – [Especificações] [e] [Descontos:]\r\n"
				+ "[Especificações] [Descontos]");
		
		/*
		 * tabela de descontos
		 * 
		 * 
		 */
		
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
		

		/*
		 * 6.0 clausula para adicionar preco do produto
		
		*/
		substituirTexto("[6.1]. O [Preço] relativamente à quantidade de [Produto] cujo [Preço] já foi fixado, nos termos deste [Contrato], será pago pelo [Comprador] ao [Vendedor] assim que cumprir a entrega total do produto.");
		/*
		 * 6.2 clausula para adicionar valores
		
		*/
		

		substituirTexto("[6.3.] Armazenagem e a comissão da venda será por conta do Comprador");
		substituirTextoLongo();

		//local e data
		adicionarData();
		
		//campos de assinatura
		
		adicionarCamposAssinaturas();
		
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


		adicionarTextoParagrafoAtual(" a quantidade de " , false);
		
		if(novo_contrato.getMedida().equalsIgnoreCase("SACOS")) {
			double quantidade_sacos = novo_contrato.getQuantidade();
			double quantidade_kg  = quantidade_sacos * 60;
			
			adicionarTextoParagrafoAtual(z.format(quantidade_kg).toUpperCase(), true);

		}else if(novo_contrato.getMedida().equalsIgnoreCase("KG")) {
			adicionarTextoParagrafoAtual(z.format(novo_contrato.getQuantidade()).toUpperCase(), true);

		}
		

		adicionarTextoParagrafoAtual(" " , false);
		adicionarTextoParagrafoAtual("quilogramas", true);

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
		adicionarTextoParagrafoAtual("O ", false);
		adicionarTextoParagrafoAtual("Vendedor ", true);
		adicionarTextoParagrafoAtual("obriga-se a entregar o ", false);
		adicionarTextoParagrafoAtual("Produto ao Comprador ", true);
		adicionarTextoParagrafoAtual("na condição “posto sobre rodas”, no seguinte local de entrega: ", false);

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
		
		criarParagrafo(2);
		adicionarTextoParagrafoAtual( "Correrão por conta exclusiva do Vendedor todos os custos e riscos relacionados ao Produto (incluindo, mas não se limitando os riscos relacionados ao plantio, cultivo, colheita, bem como a sua adequação às Especificações), até a sua efetiva entrega ao Comprador no Local de Entrega, na condição estabelecida nesta cláusula." , false);

		
		
		
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
		        adicionarTextoParagrafoAtual(palavra + " ", false);

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
					
					
					substituirTexto("[_______________________________________________________________]                                                                                      -"
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
								
								
								substituirTexto("[_______________________________________________________________]                                                                                      -"
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
								
								
								substituirTexto("[_______________________________________________________________]                                                                                      -"
										+ "["  + nome_assinatura_negrito.toUpperCase() + "]\r\n"
										+ "");
							
							}
						}

		
		//testemunhas
		substituirTexto("[TESTEMUNHAS:]\r\n"
				+ 
				"[1.] _________________________________                                                                                                                                                -"
				+ "Nome:                                                                                                                                                                                                           -"
				+ "CPF:                                                                                                                                                                                                             - "
				);
		
		substituirTexto(
				"[2.] _________________________________                                                                                                                                                -"
				+ "Nome:                                                                                                                                                                                                           -"
				+ "CPF:                                                                                                                                                                                                             - "
				);
		
		

	}
	
	

}
