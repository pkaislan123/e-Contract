package main.java.relatoria;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
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

import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroSafra;
import main.java.cadastros.CentroCusto;
import main.java.cadastros.CondicaoPagamento;
import main.java.cadastros.FinanceiroConta;
import main.java.cadastros.FinanceiroGrupoContas;
import main.java.cadastros.FinanceiroPagamento;
import main.java.cadastros.InstituicaoBancaria;
import main.java.cadastros.Lancamento;
import main.java.cadastros.Parcela;
import main.java.conexaoBanco.GerenciarBancoCentroCustos;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoCondicaoPagamentos;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoFinanceiroConta;
import main.java.conexaoBanco.GerenciarBancoFinanceiroGrupoContas;
import main.java.conexaoBanco.GerenciarBancoFinanceiroPagamento;
import main.java.conexaoBanco.GerenciarBancoInstituicaoBancaria;
import main.java.conexaoBanco.GerenciarBancoLancamento;
import main.java.conexaoBanco.GerenciarBancoParcelas;
import main.java.conexaoBanco.GerenciarBancoSafras;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.outros.DadosGlobais;
import main.java.outros.GetData;
import main.java.tratamento_proprio.Log;

public class RelatorioLancamento {

	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private String servidor_unidade;
	private XWPFDocument document_global;
	private XWPFParagraph paragrafo_atual;
	private Lancamento lancamento_global;
	private String cliente_fornecedor = "";
	public RelatorioLancamento(Lancamento lancamento) {
		this.lancamento_global = lancamento;
		getDadosGlobais();
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

		CTDocument1 document = document_global.getDocument();
		CTBody body = document.getBody();

		if (!body.isSetSectPr()) {
			body.addNewSectPr();
		}
		CTSectPr section = body.getSectPr();

		if (!section.isSetPgSz()) {
			section.addNewPgSz();
		}
		CTPageSz pageSize = section.getPgSz();

		pageSize.setOrient(STPageOrientation.PORTRAIT);
		pageSize.setW(BigInteger.valueOf(15840));
		pageSize.setH(BigInteger.valueOf(12240));
		document_global.createStyles();

	}

	
	public String preparar() {

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

		// criar o paragrafo do titulo
		XWPFParagraph title = document_global.createParagraph();
		title.setAlignment(ParagraphAlignment.LEFT);

		GetData data = new GetData();

		XWPFRun titleRun = title.createRun();
		String data_criacao = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

		titleRun.setText("Relatório de Lançamento" + " por " + login.getNome() + " " + login.getSobrenome() + " em "
				+ data_criacao + " ás " + data.getHora());
		titleRun.setColor("000000");
		titleRun.setBold(false);
		titleRun.setUnderline(UnderlinePatterns.SINGLE);
		titleRun.setFontFamily("Arial");
		titleRun.setFontSize(10);

		XWPFParagraph filtros = document_global.createParagraph();
		filtros.setAlignment(ParagraphAlignment.LEFT);

		XWPFRun dadosPesquisaRun = filtros.createRun();

	
		String status = "";
		int int_status = lancamento_global.getStatus();
		if(int_status == 0) {
			status = ("A Pagar");

		}else if(int_status == 1) {
			status = ("Pago");

		}else if(int_status == 2) {
			status = ("A Receber");

		}else if(int_status == 3) {
			status = ("Recebido");

		}
		
		String tipo_lancamento = "";
		if(lancamento_global.getTipo_lancamento() == 0) {
			tipo_lancamento = "DESPESA";
		}else if(lancamento_global.getTipo_lancamento() == 1) {
			tipo_lancamento = "RECEITA";

		}
		
		 int d_prioridade = lancamento_global.getPrioridade();
		 /*cbPrioridade.addItem("Alta Prioridade - Ainda esta semana");
			cbPrioridade.addItem("Média Prioridade - Em menos de 15 dias");
			cbPrioridade.addItem("Prioridade Leve - Ainda este mês");
			cbPrioridade.addItem("Baixa Prioridade - Ainda este ano");
			*/
		 String prioridade = "";
		 if(d_prioridade == 0) {
			 prioridade= ("Alta Prioridade - Ainda esta semana");
		 }else if(d_prioridade == 1) {
			 prioridade = ("Média Prioridade - Em menos de 15 dias");

		 }else if(d_prioridade == 2) {
			 prioridade = ("Prioridade Leve - Ainda este mês");

		 }else if(d_prioridade == 3) {
			 prioridade = ("Baixa Prioridade - Ainda este ano");

		 }
		
		String texto = " Lançamento do tipo: [" + tipo_lancamento + "]\n";
		texto = texto + " Status: [" + status + "]\n";
		texto = texto + " Data: [" + lancamento_global.getData_lancamento() + "]\n";
		texto = texto + " Prioridade: [" + prioridade + "]\n\n";
		
		

		  lancamento_global = new GerenciarBancoLancamento().getLancamento(lancamento_global.getId_lancamento());
			
		  CentroCusto cc = new CentroCusto();
		  CadastroCliente cliente = new CadastroCliente();
		  FinanceiroGrupoContas fgc = new FinanceiroGrupoContas();
		  FinanceiroConta fc = new FinanceiroConta();
		  
		 
			
			
		  String centro_custo = "";
		  cc = new GerenciarBancoCentroCustos().getCentroCusto(lancamento_global.getId_centro_custo());
		  centro_custo = (cc.getNome_centro_custo());
		  
		  
		  try {
		  cliente = new GerenciarBancoClientes().getCliente(lancamento_global.getId_cliente_fornecedor());
		  String nome_cliente = "";
		  if(cliente.getTipo_pessoa() == 0) {
			  nome_cliente = cliente.getNome_empresarial();
		  }else
			  nome_cliente = cliente.getNome_fantaia();
		  cliente_fornecedor = (nome_cliente);
		  }catch(Exception e) {
			  
		  }
		  
		  String conta = "";
		  String grupo_conta = "";
		  fc = new GerenciarBancoFinanceiroConta().getFinanceiroConta(lancamento_global.getId_conta());
		  conta = (fc.getNome());
		  
		  fgc = new GerenciarBancoFinanceiroGrupoContas().getFinanceiroGrupoContas(fc.getId_grupo_contas());
		  grupo_conta = (fgc.getNome());
		  
		
		
		if(lancamento_global.getTipo_lancamento() == 0) {
		texto = texto + "As Partes:\n";
		texto = texto + " Devedor: [" + centro_custo + "]\n";
		texto = texto + " Recebdor: [" + cliente_fornecedor + "]\n";
		}else if(lancamento_global.getTipo_lancamento() == 1) {
			texto = texto + "As Partes:\n";
			texto = texto + " Recebedor: [" + centro_custo + "]\n";
			texto = texto + " Devedor: [" + cliente_fornecedor + "]\n";
		}
		
		String status_contador = "";
		int contador = lancamento_global.getContador();
		if(contador == 0) {
			status_contador = ("Não se aplica");
		}else if(contador == 1) {
			status_contador = ("Não Enviado ao contador");
		}else if(contador == 2) {
			status_contador = ("Enviado ao contador");
		}
		
		String nome_destinatario_nf = "";
		if(lancamento_global.getId_detinatario_nf() > 0) {
			CadastroCliente destinatario = new GerenciarBancoClientes().getCliente(lancamento_global.getId_detinatario_nf());
			if(destinatario.getTipo_pessoa() == 0) {
				nome_destinatario_nf = destinatario.getNome_empresarial();
			}else {
				nome_destinatario_nf = destinatario.getNome_empresarial();
			}
			
		}else {
			nome_destinatario_nf = "Indefinido";
		}
		
		texto = texto + "\nA Conta:\n";
		texto = texto + " Grupo de Contas: [" + grupo_conta + "]\n";
		texto = texto + " Conta: [" + conta + "]\n";
		texto = texto + " Identificador: [" + lancamento_global.getIdentificacao() + "]\n";
		texto = texto + " Destinatário da NF: [" + nome_destinatario_nf + "]\n";

		texto = texto + " Data Primeiro Vencimento: [" + lancamento_global.getData_vencimento() + "]\n";
		texto = texto + " Valor: [" + NumberFormat.getCurrencyInstance(ptBr).format(lancamento_global.getValor()) + "]\n";
		texto = texto + " Número de Parcelas: [" + lancamento_global.getNumero_parcelas() + "]\n";
		texto = texto + " Intervalo: [" + lancamento_global.getIntervalo() + "]\n";
		texto = texto + " Descrição: [" + lancamento_global.getDescricao()+ "]\n";
		texto = texto + " Observação: [" + lancamento_global.getObservacao()+ "]\n";
		texto = texto + " Status Contador: [" + status_contador+ "]\n\n";
		
		
		substituirTexto(-1,texto);
		substituirTexto(-1,"");

		criarTabelaParcelasPagamentos();
		
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
					XWPFHeader header = hfPolicy.createHeader(XWPFHeaderFooterPolicy.DEFAULT, pars);

					// hfPolicy.createHeader(XWPFHeaderFooterPolicy.DEFAULT, pars);

					pars[0] = header.getParagraphArray(0);
					pars[0].setAlignment(ParagraphAlignment.LEFT);

					CTTabStop tabStop = pars[0].getCTP().getPPr().addNewTabs().addNewTab();
					tabStop.setVal(STTabJc.RIGHT);
					int twipsPerInch = 1440;
					tabStop.setPos(BigInteger.valueOf(6 * twipsPerInch));

					cabecalhoRun = pars[0].createRun();
					cabecalhoRun.addTab();

					cabecalhoRun = pars[0].createRun();
					URL url = getClass().getResource("/imagens/logo_para_relatorio.png");
					String imgFile = url.getFile();
					cabecalhoRun.addPicture(new FileInputStream(imgFile), XWPFDocument.PICTURE_TYPE_PNG, imgFile,
							Units.toEMU(30), Units.toEMU(30));

				} catch (Exception e) {
					// //JOptionPane.showMessageDialog(null,
					// "Erro ao criar cabecalho e rodape do contrato!\nConsulte o administrador do
					// sistema!");
					e.printStackTrace();
				}


				ByteArrayOutputStream saida_apos_edicao = new ByteArrayOutputStream();

				try {
					document_global.write(new FileOutputStream("c:\\temp\\arquivoteste.docx"));
					// document_global.write(saida_apos_edicao);

				} catch (IOException e) { // TODO Auto-generated catch block
					e.printStackTrace();
				}


				return "c:\\temp\\arquivoteste.docx";
	}
	
	
	public void getDadosGlobais() {
		// gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		GerenciadorLog = dados.getGerenciadorLog();
		configs_globais = dados.getConfigs_globais();

		// usuario logado
		login = dados.getLogin();

	}

	public boolean checkString(String txt) {
		return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ") && txt.length() > 0;
	}

	public void substituirTexto(int alinhamento, String text_amostra) {

		// criarParagrafo(2);

		// pegar os paragrafos
		String separador_paragrafo[] = text_amostra.split("\n");
		for (String paragrafo : separador_paragrafo) {
			criarParagrafo(alinhamento);

			paragrafo = paragrafo.replaceAll(" ", "&");

			String separador_palabras[] = paragrafo.split("&");
			for (String palavra : separador_palabras) {
				if (palavra.contains("[") || palavra.contains("]")) {
					adicionarTextoParagrafoAtual(palavra.replaceAll("[\\[\\]]", "") + " ", true);

				} else {

					adicionarTextoParagrafoAtual(palavra + " ", false);

				}

			}
		}

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
	public void criarParagrafo(int alinhamento) {
		XWPFParagraph paragrafo = document_global.createParagraph();

		setSingleLineSpacing(paragrafo);
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
	
	

	public void criarTabelaParcelasPagamentos() {

		NumberFormat z = NumberFormat.getNumberInstance();
		BigDecimal valor_total_parcelas = BigDecimal.ZERO;
		Locale ptBr = new Locale("pt", "BR");

		// criarParagrafo(1);
		// linhas x colunas
		ArrayList<Parcela> parcelas = new GerenciarBancoParcelas().getParcelasPorLancamento(lancamento_global.getId_lancamento());
		ArrayList<FinanceiroPagamento> pagamentos = new GerenciarBancoFinanceiroPagamento().getFinanceiroPagamentosPorLancamento(lancamento_global.getId_lancamento());

		int num_linhas_tabela  = 0;
		
		if(parcelas.size() > pagamentos.size()) {
			num_linhas_tabela = parcelas.size();
		}else 
			num_linhas_tabela = pagamentos.size();
		
		
		XWPFTable table = document_global.createTable(num_linhas_tabela + 3, 14);

		setTableAlign(table, ParagraphAlignment.CENTER);
		XWPFTableRow tableRowOne = table.getRow(0);
		XWPFParagraph paragraph = tableRowOne.getCell(0).addParagraph();
		int cabecalho = 0;

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(0).removeParagraph(0);
		paragraph = tableRowOne.getCell(0).addParagraph();
		criarParagrafoTabela(paragraph,"PARCELAS",true);
		tableRowOne.getCell(0).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");
		CTHMerge hMerge = CTHMerge.Factory.newInstance();
		hMerge.setVal(STMerge.RESTART);
		table.getRow(cabecalho).getCell(0).getCTTc().getTcPr().setHMerge(hMerge);

		for (int celula = 1; celula <= 4; celula++) {
			tableRowOne = table.getRow(cabecalho);
			tableRowOne.getCell(celula).removeParagraph(0);
			paragraph = tableRowOne.getCell(celula).addParagraph();

			criarParagrafoTabela(paragraph, "", true);
			tableRowOne.getCell(celula).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");

			CTHMerge hMerge1 = CTHMerge.Factory.newInstance();
			hMerge1.setVal(STMerge.CONTINUE);
			table.getRow(cabecalho).getCell(celula).getCTTc().getTcPr().setHMerge(hMerge1);

		}

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(6).removeParagraph(0);
		paragraph = tableRowOne.getCell(6).addParagraph();
		criarParagrafoTabela(paragraph,"PAGAMENTOS",true);
		tableRowOne.getCell(6).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");
		CTHMerge hMerge2 = CTHMerge.Factory.newInstance();

		hMerge2.setVal(STMerge.RESTART);
		table.getRow(cabecalho).getCell(6).getCTTc().getTcPr().setHMerge(hMerge2);

		for (int celula = 6; celula <= 13; celula++) {
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
		criarParagrafoTabela(paragraph, "Valor", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(4).removeParagraph(0);
		paragraph = tableRowOne.getCell(4).addParagraph();
		criarParagrafoTabela(paragraph, "Status", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(5).removeParagraph(0);
		paragraph = tableRowOne.getCell(5).addParagraph();
		criarParagrafoTabela(paragraph, "-----------", true);
		
		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(6).removeParagraph(0);
		paragraph = tableRowOne.getCell(6).addParagraph();
		criarParagrafoTabela(paragraph, "Identificador", true);
		
		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(7).removeParagraph(0);
		paragraph = tableRowOne.getCell(7).addParagraph();
		criarParagrafoTabela(paragraph, "Pagador", true);
		
		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(8).removeParagraph(0);
		paragraph = tableRowOne.getCell(8).addParagraph();
		criarParagrafoTabela(paragraph, "Recebedor", true);
		
		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(9).removeParagraph(0);
		paragraph = tableRowOne.getCell(9).addParagraph();
		criarParagrafoTabela(paragraph, "Descrição", true);

		
		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(10).removeParagraph(0);
		paragraph = tableRowOne.getCell(10).addParagraph();
		criarParagrafoTabela(paragraph, "Valor", true);
		
		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(11).removeParagraph(0);
		paragraph = tableRowOne.getCell(11).addParagraph();
		criarParagrafoTabela(paragraph, "Data Pagamento", true);
		
		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(12).removeParagraph(0);
		paragraph = tableRowOne.getCell(12).addParagraph();
		criarParagrafoTabela(paragraph, "Forma de Pagamento", true);
		
		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(13).removeParagraph(0);
		paragraph = tableRowOne.getCell(13).addParagraph();
		criarParagrafoTabela(paragraph, "Status", true);
		
		
		int i = 2;
		GerenciarBancoContratos procura_contratos_grupo = new GerenciarBancoContratos();

		int contador_linhas = 0;
		while (contador_linhas < num_linhas_tabela) {
			Parcela parcela = parcelas.get(contador_linhas);
			
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
			criarParagrafoTabela(paragraph,  NumberFormat.getCurrencyInstance(ptBr).format(parcela.getValor()), false);
			valor_total_parcelas = valor_total_parcelas.add(parcela.getValor());
			
			tableRowOne = table.getRow(i);
			tableRowOne.getCell(4).removeParagraph(0);
			paragraph = tableRowOne.getCell(4).addParagraph();
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
			
			FinanceiroPagamento pagamento = pagamentos.get(contador_linhas);
			if(pagamento != null) {
				tableRowOne = table.getRow(i);
				tableRowOne.getCell(6).removeParagraph(0);
				paragraph = tableRowOne.getCell(6).addParagraph();
				criarParagrafoTabela(paragraph, pagamento.getIdentificador(), false);
				
				tableRowOne = table.getRow(i);
				tableRowOne.getCell(7).removeParagraph(0);
				paragraph = tableRowOne.getCell(7).addParagraph();
				
				if(lancamento_global.getTipo_lancamento() == 0) {
					//despesa, o pagador é um cliente/fornecedor
					if(pagamento.getId_pagador() > 0 ) {
						String nome_cliente = "";
						CadastroCliente pagador = new GerenciarBancoClientes().getCliente(pagamento.getId_pagador());
						if(pagador.getTipo_pessoa() == 0) {
							nome_cliente = pagador.getNome_empresarial();
						}else {
							nome_cliente = pagador.getNome_fantaia();

						}
					criarParagrafoTabela(paragraph,nome_cliente, false);
					}else {
						criarParagrafoTabela(paragraph,"Indefinido", false);

					}

				}else if(lancamento_global.getTipo_lancamento() == 1) {
					//é uma receita
					if(pagamento.getTipo_pagador() == 1) {
						//cliente fornecedor
						if(pagamento.getId_pagador() > 0 ) {
							String nome_cliente = "";
							CadastroCliente pagador = new GerenciarBancoClientes().getCliente(pagamento.getId_pagador());
							if(pagador.getTipo_pessoa() == 0) {
								nome_cliente = pagador.getNome_empresarial();
							}else {
								nome_cliente = pagador.getNome_fantaia();

							}
						criarParagrafoTabela(paragraph,nome_cliente, false);
						}else {
							criarParagrafoTabela(paragraph,"Indefinido", false);

						}
					}else if(pagamento.getTipo_pagador() == 0) {
						//caixa ou banco
						if(pagamento.getId_pagador() > 0 ) {
							String nome_banco = "";
							InstituicaoBancaria pagador = new GerenciarBancoInstituicaoBancaria().getInstituicaoBancaria(pagamento.getId_pagador());
								nome_banco = pagador.getNome_instituicao_bancaria();
						criarParagrafoTabela(paragraph,nome_banco, false);
						}else {
							criarParagrafoTabela(paragraph,"Indefinido", false);

						}
					}
					
				}
				
				//recebedor
				tableRowOne = table.getRow(i);
				tableRowOne.getCell(8).removeParagraph(0);
				paragraph = tableRowOne.getCell(8).addParagraph();
				
				//no lancamento de despesa o recebedor e o recebdor do lancamento
				if(lancamento_global.getTipo_lancamento() == 0) {
					//despesa
					criarParagrafoTabela(paragraph,cliente_fornecedor, false);

					
				}else if(lancamento_global.getTipo_lancamento() == 1) {
					//receita o recebedor e uma caixa ou banco
					//caixa ou banco
					if(pagamento.getId_recebedor() > 0 ) {
						String nome_banco = "";
						InstituicaoBancaria pagador = new GerenciarBancoInstituicaoBancaria().getInstituicaoBancaria(pagamento.getId_recebedor());
							nome_banco = pagador.getNome_instituicao_bancaria();
					criarParagrafoTabela(paragraph,nome_banco, false);
					}else {
						criarParagrafoTabela(paragraph,"Indefinido", false);

					}
				}
				
				tableRowOne = table.getRow(i);
				tableRowOne.getCell(9).removeParagraph(0);
				paragraph = tableRowOne.getCell(9).addParagraph();
				criarParagrafoTabela(paragraph,pagamento.getDescricao(), false);
				
				tableRowOne = table.getRow(i);
				tableRowOne.getCell(10).removeParagraph(0);
				paragraph = tableRowOne.getCell(10).addParagraph();
				criarParagrafoTabela(paragraph,NumberFormat.getCurrencyInstance(ptBr).format(pagamento.getValor()), false);
				
				tableRowOne = table.getRow(i);
				tableRowOne.getCell(11).removeParagraph(0);
				paragraph = tableRowOne.getCell(11).addParagraph();
				criarParagrafoTabela(paragraph,pagamento.getData_pagamento(), false);
				
				tableRowOne = table.getRow(i);
				tableRowOne.getCell(12).removeParagraph(0);
				paragraph = tableRowOne.getCell(12).addParagraph();
				GerenciarBancoCondicaoPagamentos gerenciar = new GerenciarBancoCondicaoPagamentos();
				String forma_pagamento = "";
				if(pagamento.getId_condicao_pagamento() > 0) {
					CondicaoPagamento condicao  = gerenciar.getCondicaoPagamento(pagamento.getId_condicao_pagamento());

					if(condicao !=null)
						forma_pagamento =  condicao.getNome_condicao_pagamento();
				}
				criarParagrafoTabela(paragraph,forma_pagamento, false);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(13).removeParagraph(0);
				paragraph = tableRowOne.getCell(13).addParagraph();
				String status ="";
				if(pagamento.getStatus_pagamento() == 0) {
					status=  "A - Compensar|Realizar|Concluir";
					
				}else if(pagamento.getStatus_pagamento() == 1) {
					status=  "Compensado|Realizado|Concluído";
				}
				criarParagrafoTabela(paragraph,status, false);

				
			}
			
			i++;
			contador_linhas++;
		}

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(2).removeParagraph(0);
		paragraph = tableRowOne.getCell(2).addParagraph();
		criarParagrafoTabela(paragraph, "Total: ", false);
		
		tableRowOne = table.getRow(i);
		tableRowOne.getCell(3).removeParagraph(0);
		paragraph = tableRowOne.getCell(3).addParagraph();
		criarParagrafoTabela(paragraph,  NumberFormat.getCurrencyInstance(ptBr).format(valor_total_parcelas), true);

	}
	

	public void setTableAlign(XWPFTable table, ParagraphAlignment align) {
		CTTblPr tblPr = table.getCTTbl().getTblPr();
		CTJc jc = (tblPr.isSetJc() ? tblPr.getJc() : tblPr.addNewJc());
		STJc.Enum en = STJc.Enum.forInt(align.getValue());
		jc.setVal(en);
	}

}
