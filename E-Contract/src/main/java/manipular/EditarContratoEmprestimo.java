
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
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSpacing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STLineSpacingRule;
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
import main.java.cadastros.CentroCusto;
import main.java.cadastros.CondicaoPagamento;
import main.java.cadastros.ContaBancaria;
import main.java.cadastros.FinanceiroPagamento;
import main.java.cadastros.InstituicaoBancaria;
import main.java.cadastros.Lancamento;
import main.java.cadastros.Parcela;
import main.java.cadastros.ParcelaEmprestimo;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.conexaoBanco.GerenciarBancoCentroCustos;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoCondicaoPagamentos;
import main.java.conexaoBanco.GerenciarBancoInstituicaoBancaria;
import main.java.conexaoBanco.GerenciarBancoLancamento;
import main.java.conexaoBanco.GerenciarBancoParcelas;
import main.java.conexaoBanco.GerenciarBancoParcelasEmprestimo;
import main.java.outros.DadosGlobais;
import main.java.outros.GetData;
import main.java.tratamento_proprio.Log;
import main.java.views_personalizadas.TelaEmEspera;

public class EditarContratoEmprestimo {

	private CadastroModelo modelo;
	private String path;

	private TelaEmEspera telaInformacoes;
	private CadastroCliente compradores[], vendedores[], corretores[];

	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private String servidor_unidade;
	private XWPFDocument document_global;
	private XWPFParagraph paragrafo_atual;
	private String texto;
	private Lancamento lancamento_global;

	public EditarContratoEmprestimo(Lancamento _lancamento) {
		getDadosGlobais();
		servidor_unidade = configs_globais.getServidorUnidade();
		this.lancamento_global = _lancamento;
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

	public String preparar() {

		lancamento_global = new GerenciarBancoLancamento()
				.getLancamentoParaRelatorio(lancamento_global.getId_lancamento());

		// cria o paragrafo do rodape
		XWPFParagraph rodape = document_global.createParagraph();
		rodape.setAlignment(ParagraphAlignment.LEFT);

		// criar o paragrafo do titulo
		XWPFParagraph title = document_global.createParagraph();
		title.setAlignment(ParagraphAlignment.CENTER);

		Locale ptBr = new Locale("pt", "BR");

		XWPFRun titleRun = title.createRun();
		titleRun.setText(
				"CONTRATO DE EMPRÉSTIMO: " + new GetData().getAnoAtual() + "/" + lancamento_global.getId_lancamento());
		titleRun.setColor("000000");
		titleRun.setBold(true);
		titleRun.setUnderline(UnderlinePatterns.SINGLE);
		titleRun.setFontFamily("Arial");
		titleRun.setFontSize(14);

		// numero do contrato

		XWPFParagraph num_contrato = document_global.createParagraph();
		num_contrato.setAlignment(ParagraphAlignment.CENTER);

		String assinatura_recebedor = "";
		CadastroCliente mutuante = null;
		CadastroCliente tomador = null;
		adicionarTraco(true, 2);

		String nome_cliente = lancamento_global.getNome_cliente_fornecedor();
		String centro_custo = lancamento_global.getNome_centro_custo();

		texto = texto + "As Partes:\n";
		texto = texto + " Tomador: [" + nome_cliente + "]\n";
		texto = texto + " Mutuante: [" + centro_custo + "]\n";

		// mutuante

		 mutuante = new GerenciarBancoClientes()
				.getCliente(lancamento_global.getId_cliente_fornecedor());
		if (mutuante != null) {

			adicionarParte(0, mutuante);

		}

		adicionarTraco(true, 2);

		String assinatura_pagador = "";
		String nome_pagador = "";
		// tomador
		CentroCusto cc = new GerenciarBancoCentroCustos().getCentroCusto(lancamento_global.getId_centro_custo());
		 tomador = new GerenciarBancoClientes()
				.getCliente(cc.getId_cliente());
		if (tomador != null) {

			adicionarParte(1, tomador);

		}
		adicionarTraco(true, 2);

		
		//objeto
		String objeto = "Têm as Partes entre si justa e contratada a celebração do presente contrato de Empréstimo (“Contrato”), o que fazem nos termos das seguintes cláusulas e condições:\r\n"
				+ "[1]. [OBJETO:]\nPelo presente [CONTRATO] [DE] [EMPRÉSTIMO] e nos termos do artigos 586 a 592 do Código Civil, o [MUTUANTE] empresta ao [TOMADOR] o valor de valor_pagamento_decimal ( valor_pagamento_extenso ) resultante da soma das parcelas descritas abaixo sendo que, cada uma das linhas da tabela fica assim classificada como [OBJETO] [DO] [MÚTUO]:";
		
		substituirTexto( objeto);
		criarTabelaParcelasEmprestimos();
		
		String disposicoes_legais = "[2]. [DISPOSIÇÕES] [LEGAIS:] \nO [TOMADOR] é obrigado a restituir ao [MUTUANTE] o que dele recebeu em coisa do mesmo gênero, qualidade e quantidade.";
		
		substituirTexto( disposicoes_legais);

		String demais_clausulas = "3. [DA RESTITUIÇÃO]\r\n"
				+ "Quando um dos [OBJETOS] [DO] [MÚTUO], for da espécie \"produto\" que sofre variação de valor por quaisquer motivos, fica preterível que este deve ser restituido em mesma quantidade mutuada, a não ser por expressa vontade diferente do [MUTUANTE], a qual fica resguardado o direito de não sofrer percas devido a variação de valores no mercado financeiro.\n"
				+ "3.1 [DO] [PRAZO] [DE] [RESTITUIÇÃO]\r\n"
				+ "Cada [OBJETO] [DO] [MÚTUO] tem uma data de vencimento, estas podendo ser diferentes entre si, todo e qualquer atraso no(s) pagamento(s) devido(s) acarretará acréscimo de juros e correção monetária, cobrados da data de vencimento até a data da efetiva satisfação do crédito."
				+ "\r\n"
				+ "4. [DA] [TRANSFERÊNCIA]\r\n"
				+ "Fica vedada a cessão e transferência do presente contrato, seja a que título for, sem a expressa concordância das partes.\n"
				+ "5. [DA] [SUCESSÃO]\r\n"
				+ "O presente contrato é irrevogável e irretratável para ambas as partes, e em caso de óbito, obriga seus herdeiros e sucessores ao cumprimento de todas as suas condições.";
		substituirTexto( demais_clausulas);

		
		// local e data
		adicionarData(lancamento_global.getData_lancamento());

		// campos de assinatura

		if (tomador.getTipo_pessoa() == 0) {
			adicionarCamposAssinaturas(tomador.getNome_empresarial(), 0, tomador.getCpf());
		} else if (tomador.getTipo_pessoa() == 1) {
			adicionarCamposAssinaturas(tomador.getNome_fantaia(), 1, tomador.getCnpj());

		}
		
		if (mutuante.getTipo_pessoa() == 0) {
			adicionarCamposAssinaturas(mutuante.getNome_empresarial(), 0, mutuante.getCpf());
		} else if (mutuante.getTipo_pessoa() == 1) {
			adicionarCamposAssinaturas(mutuante.getNome_fantaia(), 1, mutuante.getCnpj());

		}

	
		//testemunhas
		substituirTexto("");
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

		// cabecalho e rodape

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
			JOptionPane.showMessageDialog(null,
					"Erro ao criar cabecalho e rodape do contrato!\nConsulte o administrador do sistema!");
			e.printStackTrace();
		}

		ByteArrayOutputStream saida_apos_edicao = new ByteArrayOutputStream();


		try {
			document_global.write(new FileOutputStream("c:\\temp\\arquivoteste_contrato_relatorio.docx"));
			// document_global.write(saida_apos_edicao);

		} catch (IOException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}


		return "c:\\temp\\arquivoteste_contrato_relatorio.docx";

	}

	public void adicionarTraco(boolean negrito, int flag) {

		XWPFParagraph traco = document_global.createParagraph();
		traco.setAlignment(ParagraphAlignment.CENTER);

		XWPFRun tracotitleRun = traco.createRun();
		if (flag == 1) {
			tracotitleRun.setText("________________________________________________________________________________");

			// continou
		} else {
			// tracejado
			tracotitleRun.setText(
					"_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ __ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ ");

		}
		tracotitleRun.setColor("000000");
		tracotitleRun.setBold(negrito);
		tracotitleRun.setFontFamily("Arial");
		tracotitleRun.setFontSize(12);

	}

	public void substituirTexto(String text_amostra) {

		// criarParagrafo(2);

		// pegar os paragrafos
		String separador_paragrafo[] = text_amostra.split("\n");
		for (String paragrafo : separador_paragrafo) {
			criarParagrafo(2);

			paragrafo = paragrafo.replaceAll(" ", "&");

			String separador_palabras[] = paragrafo.split("&");
			for (String palavra : separador_palabras) {
				if (palavra.contains("[") || palavra.contains("]")) {
					adicionarTextoParagrafoAtual(palavra.replaceAll("[\\[\\]]", "") + " ", true);

				} else {
					if (palavra.equals("valor_pagamento_decimal")) {

						Locale ptBr = new Locale("pt", "BR");
						String valorString = NumberFormat.getCurrencyInstance(ptBr).format((lancamento_global.getValor()));
						System.out.println(valorString);

						adicionarTextoParagrafoAtual(valorString + " ", true);
						
					} else if (palavra.equals("valor_pagamento_extenso")) {

						String valor_extenso = new PorExtenso(lancamento_global.getValor()).toString();
						adicionarTextoParagrafoAtual(
								palavra.replace("valor_pagamento_extenso", valor_extenso.toLowerCase() + ""), false);

					} else {
						adicionarTextoParagrafoAtual(palavra + " ", false);
					}
				}

			}
		}

	}

	public void criarParagrafo(int alinhamento) {
		XWPFParagraph paragrafo = document_global.createParagraph();

		if (alinhamento == 0) {
			// centro
			paragrafo.setAlignment(ParagraphAlignment.CENTER);

		} else if (alinhamento == 1) {
			// direita
			paragrafo.setAlignment(ParagraphAlignment.RIGHT);

		} else if (alinhamento == -1) {
			// esquerda
			paragrafo.setAlignment(ParagraphAlignment.LEFT);

		} else if (alinhamento == 2) {
			paragrafo.setAlignment(ParagraphAlignment.BOTH);

		}

		paragrafo_atual = paragrafo;
	}

	public void adicionarTextoParagrafoAtual(String texto, boolean negrito) {
		XWPFRun run = paragrafo_atual.createRun();
		run.setText(texto);
		run.setColor("000000");
		run.setBold(negrito);
		run.setFontFamily("Times New Roman");
		run.setFontSize(10);

	}

	public void saltarLinhaParagrafo() {
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

	public void adicionarData(String data_emprestimo) {
		// data contrato
		String data_extenso = "";

		SimpleDateFormat formato_data = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date data_formatada = formato_data.parse(data_emprestimo);
			Date data = new Date();
			Locale local = new Locale("pt", "BR");
			DateFormat formato = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", local);

			LocalDate data_local = data_formatada.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			DayOfWeek dia_da_semana = data_local.getDayOfWeek();

			// dia da semna
			data_extenso = dia_da_semana.getDisplayName(TextStyle.FULL, local) + ", " + formato.format(data_formatada);

		} catch (ParseException e) {
			System.out.println("Nao foi possivel converter a data");
			e.printStackTrace();
		}

		substituirTexto("\n\nE por estarem assim justas e contratadas, firmam [TOMADOR] E [MUTUANTE] o presente instrumento em 2 (duas) vias de igual teor e forma, juntamente com as testemunhas abaixo");

		substituirTexto("Guarda-Mor MG " + data_extenso);
	}

	public void adicionarCamposAssinaturas(String assinatura_recebedor, int tipo_identificacao, String identificacao) {

		criarParagrafo(0);

		String separados[] = assinatura_recebedor.split(" ");
		String nome_assinatura_negrito = "";

		for (String palavra : separados) {
			nome_assinatura_negrito += "[" + palavra + "]" + " ";
		}

		if (tipo_identificacao == 0) {
			String cpf = "";
			try {

				MaskFormatter formater_cpf = new MaskFormatter("###.###.###-##");
				formater_cpf.setValueContainsLiteralCharacters(false);
				cpf = formater_cpf.valueToString(identificacao);

			} catch (Exception e) {
			}

			substituirTexto(
					"[_______________________________________________________________]                                                                                         "
							+ "[" + nome_assinatura_negrito.toUpperCase() + "]\nCPF: [" + cpf + "]");
		} else if (tipo_identificacao == 1) {

			String cnpj = "";
			try {

				MaskFormatter formater_cnpj = new MaskFormatter("##.###.###/####-##");
				formater_cnpj.setValueContainsLiteralCharacters(false);
				cnpj = formater_cnpj.valueToString(identificacao);

			} catch (Exception e) {
			}
			substituirTexto(
					"[_______________________________________________________________]                                                                                         "
							+ "[" + nome_assinatura_negrito.toUpperCase() + "]\nCNPJ: [" + cnpj + "]");
		}

	}

	public boolean criarArquivo(String caminho_completo) {

		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream(caminho_completo + ".docx");

			// novo_contrato.setCaminho_arquivo(url.toString());

			document_global.write(outputStream);
			// workbook_aberto.close();
			outputStream.close();

			// workbook.close();
			// Converter e salvar em pdf
			// criar pdf
			ConverterPdf converter_pdf = new ConverterPdf();
			// String url = converter_pdf.excel_pdf_file(contrato_alterado);
			// TelaVizualizarPdf vizualizar = new TelaVizualizarPdf(url);
			if (converter_pdf.word_pdf_file(caminho_completo)) {
				System.out.println("Arquivo pdf convertido e salvo!");

				return true;

			} else {
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

		if (flag == 0)
			titlerun.setText("TOMADOR:");
		else if (flag == 1)
			titlerun.setText("MUTUANTE:");

		titlerun.addBreak();

		String nome_corretor = "";
		if (cliente.getTipo_pessoa() == 0) {
			// pessoa fisica
			nome_corretor = cliente.getNome_empresarial().toUpperCase().trim();
		} else {
			nome_corretor = cliente.getNome_fantaia().toUpperCase().trim();

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
		adicional1run.setBold(true);

		try {

			String ie = "";
			MaskFormatter formater_ie = new MaskFormatter("#########.##-##");
			formater_ie.setValueContainsLiteralCharacters(false);
			ie = formater_ie.valueToString(cliente.getIe());

			adicional1run.setText(", Inscrição Estadual: " + ie + " ");
		} catch (Exception e) {
		}

		XWPFRun ocupacaoRun = parte.createRun();
		ocupacaoRun.setColor("000000");
		ocupacaoRun.setFontFamily("Times New Roman");
		ocupacaoRun.setFontSize(10);
		ocupacaoRun.setBold(false);

		try {
			ocupacaoRun.setText(", " + cliente.getOcupacao().toUpperCase().trim());
		} catch (Exception e) {
		}

		XWPFRun enderecoCorretorrun = parte.createRun();
		enderecoCorretorrun.setColor("000000");
		enderecoCorretorrun.setFontFamily("Times New Roman");
		enderecoCorretorrun.setFontSize(10);
		enderecoCorretorrun.setBold(true);
		enderecoCorretorrun.setText(" , residente no endereço " + cliente.getRua().toUpperCase().trim() + ", nº "
				+ cliente.getNumero() + ", Bairro: " + cliente.getBairro().toUpperCase().trim());

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
		try {

			MaskFormatter formater_cep = new MaskFormatter("#####-###");
			formater_cep.setValueContainsLiteralCharacters(false);
			cep = formater_cep.valueToString(cliente.getCep().replaceAll("[^0-9]", ""));

		} catch (Exception e) {
		}

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

		if (cliente.getTipo_pessoa() == 0) {
			adicionarCpfRun.setText(", inscrito no CPF sob nº ");
			String cpf = "";
			try {

				MaskFormatter formater_cpf = new MaskFormatter("###.###.###-##");
				formater_cpf.setValueContainsLiteralCharacters(false);
				cpf = formater_cpf.valueToString(cliente.getCpf());

			} catch (Exception e) {
			}
			cpfRun.setText(cpf);

		} else {
			adicionarCpfRun.setText(", inscrito no CNPJ sob nº ");
			String cnpj = "";
			try {

				MaskFormatter formater_cnpj = new MaskFormatter("##.###.###/####-##");
				formater_cnpj.setValueContainsLiteralCharacters(false);
				cnpj = formater_cnpj.valueToString(cliente.getCnpj());

			} catch (Exception e) {
			}
			cpfRun.setText(cnpj);

		}

		XWPFRun finalRun = parte.createRun();
		finalRun.setColor("000000");
		finalRun.setFontFamily("Times New Roman");
		finalRun.setFontSize(10);
		finalRun.setBold(false);

		if (flag == 0)
			finalRun.setText(".  A pessoa supra indicada será doravante denominada “RECEBEDOR”.");
		else if (flag == 1)
			finalRun.setText(".  A pessoa supra indicada será doravante denominada “DEVEDOR”.");

	}

	public String adicionarRecebedorIntermediario(CadastroCliente cliente) {

		String texto = "[";
		if (cliente.getTipo_pessoa() == 0) {
			// pessoa fisica
			texto = cliente.getNome_empresarial().toUpperCase().trim() + "]";
		} else {
			texto = cliente.getNome_fantaia().toUpperCase().trim() + "]";

		}

		try {

			String ie = "";
			MaskFormatter formater_ie = new MaskFormatter("#########.##-##");
			formater_ie.setValueContainsLiteralCharacters(false);
			ie = formater_ie.valueToString(cliente.getIe());

			texto += (", Inscrição Estadual: [" + ie + "] ");
		} catch (Exception e) {
		}

		try {
			texto += (", " + cliente.getOcupacao().toUpperCase().trim());
		} catch (Exception e) {
		}

		texto += " , residente no endereço [" + cliente.getRua().toUpperCase().trim() + "], nº [" + cliente.getNumero()
				+ "], Bairro: [" + cliente.getBairro().toUpperCase().trim();

		texto += ("], na Cidade de [");

		texto += cliente.getCidade().toUpperCase().trim() + "]";

		texto += (" - Estado de [");

		texto += (cliente.getUf().toUpperCase()) + "]";

		texto += (" CEP: [");

		String cep = "";
		try {

			MaskFormatter formater_cep = new MaskFormatter("#####-###");
			formater_cep.setValueContainsLiteralCharacters(false);
			cep = formater_cep.valueToString(cliente.getCep().replaceAll("[^0-9]", ""));

		} catch (Exception e) {
		}

		texto += (cep) + "]";

		if (cliente.getTipo_pessoa() == 0) {
			texto += (", inscrito no CPF sob nº [");
			String cpf = "";
			try {

				MaskFormatter formater_cpf = new MaskFormatter("###.###.###-##");
				formater_cpf.setValueContainsLiteralCharacters(false);
				cpf = formater_cpf.valueToString(cliente.getCpf());

			} catch (Exception e) {
			}
			texto += (cpf) + "]";

		} else {
			texto += (", inscrito no CNPJ sob nº [");
			String cnpj = "";
			try {

				MaskFormatter formater_cnpj = new MaskFormatter("##.###.###/####-##");
				formater_cnpj.setValueContainsLiteralCharacters(false);
				cnpj = formater_cnpj.valueToString(cliente.getCnpj());

			} catch (Exception e) {
			}
			texto += (cnpj) + "]";

		}

		return texto;

	}

	
	public BigDecimal criarTabelaParcelasEmprestimos() {

		NumberFormat z = NumberFormat.getNumberInstance();
		BigDecimal valor_total_parcelas = BigDecimal.ZERO;

		Locale ptBr = new Locale("pt", "BR");

		// criarParagrafo(1);
		// linhas x colunas
		
		ArrayList<ParcelaEmprestimo> parcelas = new GerenciarBancoParcelasEmprestimo().getParcelasPorLancamento(lancamento_global.getId_lancamento());

		int num_linhas_tabela  = 0;
		
			num_linhas_tabela = parcelas.size();
		
		XWPFTable table = document_global.createTable(num_linhas_tabela + 3, 9);

		setTableAlign(table, ParagraphAlignment.CENTER);
		XWPFTableRow tableRowOne = table.getRow(0);
		XWPFParagraph paragraph = tableRowOne.getCell(0).addParagraph();
		int cabecalho = 0;

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(0).removeParagraph(0);
		paragraph = tableRowOne.getCell(0).addParagraph();
		criarParagrafoTabela(paragraph,"PARCELAS",true, "000000", 0);
		tableRowOne.getCell(0).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");
		CTHMerge hMerge = CTHMerge.Factory.newInstance();
		hMerge.setVal(STMerge.RESTART);
		table.getRow(cabecalho).getCell(0).getCTTc().getTcPr().setHMerge(hMerge);

		for (int celula = 1; celula <= 8; celula++) {
			tableRowOne = table.getRow(cabecalho);
			tableRowOne.getCell(celula).removeParagraph(0);
			paragraph = tableRowOne.getCell(celula).addParagraph();

			criarParagrafoTabela(paragraph, "", true);
			tableRowOne.getCell(celula).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");

			CTHMerge hMerge1 = CTHMerge.Factory.newInstance();
			hMerge1.setVal(STMerge.CONTINUE);
			table.getRow(cabecalho).getCell(celula).getCTTc().getTcPr().setHMerge(hMerge1);

		}

		
		
	
		
		cabecalho++;

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(0).removeParagraph(0);
		paragraph = tableRowOne.getCell(0).addParagraph();
		criarParagrafoTabela(paragraph, "Identificador", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(1).removeParagraph(0);
		paragraph = tableRowOne.getCell(1).addParagraph();
		criarParagrafoTabela(paragraph, "Descrição", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(2).removeParagraph(0);
		paragraph = tableRowOne.getCell(2).addParagraph();
		criarParagrafoTabela(paragraph, "Data Vencimento", true);
		
		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(3).removeParagraph(0);
		paragraph = tableRowOne.getCell(3).addParagraph();
		criarParagrafoTabela(paragraph, "Espécie", true);
		
		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(4).removeParagraph(0);
		paragraph = tableRowOne.getCell(4).addParagraph();
		criarParagrafoTabela(paragraph, "Quantidade", true);
		
		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(5).removeParagraph(0);
		paragraph = tableRowOne.getCell(5).addParagraph();
		criarParagrafoTabela(paragraph, "Medida", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(6).removeParagraph(0);
		paragraph = tableRowOne.getCell(6).addParagraph();
		criarParagrafoTabela(paragraph, "Valor Unitário", true);
		
		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(7).removeParagraph(0);
		paragraph = tableRowOne.getCell(7).addParagraph();
		criarParagrafoTabela(paragraph, "Valor Total", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(8).removeParagraph(0);
		paragraph = tableRowOne.getCell(8).addParagraph();
		criarParagrafoTabela(paragraph, "Status", true);

		
		
		int i = 2;

		int contador_linhas = 0;
		while (contador_linhas < num_linhas_tabela) {
			try {
			ParcelaEmprestimo parcela = parcelas.get(contador_linhas);
			
			if(parcela != null) {
			tableRowOne = table.getRow(i);
			tableRowOne.getCell(0).removeParagraph(0);
			paragraph = tableRowOne.getCell(0).addParagraph();
			criarParagrafoTabela(paragraph, parcela.getIdentificador(), false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(1).removeParagraph(0);
			paragraph = tableRowOne.getCell(1).addParagraph();
			criarParagrafoTabela(paragraph, parcela.getDescricao(), false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(2).removeParagraph(0);
			paragraph = tableRowOne.getCell(2).addParagraph();
			criarParagrafoTabela(paragraph, parcela.getData_vencimento(), false);

			
			tableRowOne = table.getRow(i);
			tableRowOne.getCell(3).removeParagraph(0);
			paragraph = tableRowOne.getCell(3).addParagraph();
			if(parcela.getObjeto() == 0) {
				//moeda
			criarParagrafoTabela(paragraph,  "MOEDA", false);
			}else if(parcela.getObjeto() == 1) {
				//produto
				criarParagrafoTabela(paragraph, parcela.getEspecie(), false);

			}
			
			tableRowOne = table.getRow(i);
			tableRowOne.getCell(4).removeParagraph(0);
			paragraph = tableRowOne.getCell(4).addParagraph();
			if(parcela.getObjeto() == 0) {
				//moeda
			criarParagrafoTabela(paragraph, "MOEDA", false);
			}else if(parcela.getObjeto() == 1) {
				//produto
				criarParagrafoTabela(paragraph, Double.toString(parcela.getQuantidade()), false);

			}
			
			tableRowOne.getCell(5).removeParagraph(0);
			paragraph = tableRowOne.getCell(5).addParagraph();
			if(parcela.getObjeto() == 0) {
				//moeda
			criarParagrafoTabela(paragraph, "MOEDA", false);
			}else if(parcela.getObjeto() == 1) {
				//produto
				criarParagrafoTabela(paragraph, parcela.getUnidade_medida(), false);

			}
			
			tableRowOne.getCell(6).removeParagraph(0);
			paragraph = tableRowOne.getCell(6).addParagraph();
			if(parcela.getObjeto() == 0) {
				//moeda
			criarParagrafoTabela(paragraph, "MOEDA", false);
			}else if(parcela.getObjeto() == 1) {
				//produto
				criarParagrafoTabela(paragraph,NumberFormat.getCurrencyInstance(ptBr).format(parcela.getValor_unitario()) , false);

			}
			
			
			tableRowOne = table.getRow(i);
			tableRowOne.getCell(7).removeParagraph(0);
			paragraph = tableRowOne.getCell(7).addParagraph();
			criarParagrafoTabela(paragraph,  NumberFormat.getCurrencyInstance(ptBr).format(parcela.getValor()), false);
			valor_total_parcelas = valor_total_parcelas.add(parcela.getValor());
			
			tableRowOne = table.getRow(i);
			tableRowOne.getCell(8).removeParagraph(0);
			paragraph = tableRowOne.getCell(8).addParagraph();
			String status_lancamento = "";
			int status = parcela.getStatus();
			if(status == 0) {
				status_lancamento =  ("A Pagar");

			}else if(status == 1) {
				status_lancamento =  ("Pago");

			}else if(status == 2) {
				status_lancamento =  ("A Receber");

			}else if(status == 3) {
				status_lancamento =  ("Recebido");

			}
			criarParagrafoTabela(paragraph, status_lancamento, false);
			}
			}catch(Exception t) {
				
			}
			
			
			
			i++;
			contador_linhas++;
		}

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(6).removeParagraph(0);
		paragraph = tableRowOne.getCell(6).addParagraph();
		criarParagrafoTabela(paragraph, "Total: ", false);
		
		tableRowOne = table.getRow(i);
		tableRowOne.getCell(7).removeParagraph(0);
		paragraph = tableRowOne.getCell(7).addParagraph();
		criarParagrafoTabela(paragraph,  NumberFormat.getCurrencyInstance(ptBr).format(valor_total_parcelas), true);

		substituirTexto( "");
		
		return valor_total_parcelas;
				
	}
	
	public void criarParagrafoTabela(XWPFParagraph paragraph, String texto, boolean negrito) {
		paragraph.setIndentationLeft(100);
		// paragraph.setIndentationRight(100);
		paragraph.setAlignment(ParagraphAlignment.LEFT);

		XWPFRun run = paragraph.createRun();

		run.setFontFamily("Times New Roman");
		run.setFontSize(8);
		run.setBold(negrito);
		run.setText(texto);

	}

	public void setSingleLineSpacing(XWPFParagraph para) {
		CTPPr ppr = para.getCTP().getPPr();
		if (ppr == null)
			ppr = para.getCTP().addNewPPr();
		CTSpacing spacing = ppr.isSetSpacing() ? ppr.getSpacing() : ppr.addNewSpacing();
		spacing.setAfter(BigInteger.valueOf(0));
		spacing.setBefore(BigInteger.valueOf(0));
		spacing.setLineRule(STLineSpacingRule.AUTO);
		spacing.setLine(BigInteger.valueOf(240));
	}
	
	public void setTableAlign(XWPFTable table, ParagraphAlignment align) {
		CTTblPr tblPr = table.getCTTbl().getTblPr();
		CTJc jc = (tblPr.isSetJc() ? tblPr.getJc() : tblPr.addNewJc());
		STJc.Enum en = STJc.Enum.forInt(align.getValue());
		jc.setVal(en);
	}
	
	public void criarParagrafoTabela(XWPFParagraph paragraph, String texto, boolean negrito, String cor,
			int alinhamento) {
		paragraph.setIndentationLeft(100);
		// paragraph.setIndentationRight(100);
		if (alinhamento == -1)
			paragraph.setAlignment(ParagraphAlignment.LEFT);
		else if (alinhamento == 0)
			paragraph.setAlignment(ParagraphAlignment.CENTER);
		else
			paragraph.setAlignment(ParagraphAlignment.RIGHT);

		XWPFRun run = paragraph.createRun();

		run.setFontFamily("Times New Roman");
		run.setFontSize(8);
		run.setColor(cor);
		run.setBold(negrito);
		run.setText(texto);

	}

	
	public void criarParagrafoTabela(XWPFParagraph paragraph, String texto, boolean negrito, String cor) {
		paragraph.setIndentationLeft(100);
		// paragraph.setIndentationRight(100);
		paragraph.setAlignment(ParagraphAlignment.LEFT);

		XWPFRun run = paragraph.createRun();

		run.setFontFamily("Times New Roman");
		run.setFontSize(8);
		run.setColor(cor);
		run.setBold(negrito);
		run.setText(texto);

	}
	
	
	
	
}
