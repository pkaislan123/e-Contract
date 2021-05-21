package main.java.relatoria;

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
import org.apache.poi.ss.util.CellRangeAddress;
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

import main.java.cadastros.CadastroAviso;
import main.java.cadastros.CadastroBaseArquivos;
import main.java.cadastros.CadastroBaseDados;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroContrato.CadastroPagamento;
import main.java.cadastros.CadastroContrato.CadastroPagamentoContratual;
import main.java.cadastros.CadastroContrato.Recebimento;
import main.java.cadastros.CadastroDocumento;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroModelo;
import main.java.cadastros.CadastroNFe;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroRomaneio;
import main.java.cadastros.CadastroSafra;
import main.java.cadastros.ContaBancaria;
import main.java.cadastros.Contato;
import main.java.cadastros.DadosCarregamento;
import main.java.cadastros.DadosContratos;
import main.java.cadastros.PagamentoCompleto;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.classesExtras.Endereco;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoDocumento;
import main.java.conexaoBanco.GerenciarBancoNotasFiscais;
import main.java.conexaoBanco.GerenciarBancoPadrao;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoRomaneios;
import main.java.conexaoBanco.GerenciarBancoSafras;
import main.java.conexaoBanco.GerenciarBancoTransferencias;
import main.java.conexaoBanco.GerenciarBancoTransferenciasCarga;
import main.java.conexoes.TesteConexao;
import main.java.graficos.GraficoLinha;
import main.java.graficos.JPanelGrafico;
import main.java.graficos.JPanelGraficoCarregamento;
import main.java.gui.TelaCadastroCliente;
import main.java.gui.TelaMain;
import main.java.gui.TelaRomaneios;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.manipular.ConverterPdf;
import main.java.manipular.CopiarArquivo;
import main.java.manipular.GetDadosGlobais;
import main.java.manipular.ManipularNotasFiscais;
import main.java.manipular.ManipularRomaneios;
import main.java.manipular.ManipularTxt;
import main.java.manipular.Nuvem;
import main.java.manipular.PorExtenso;
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
import outros.ValidaCNPj;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroNuvem;
import main.java.cadastros.CadastroZapMessenger;
import main.java.outros.DadosGlobais;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroGrupo;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoGrupos;
import main.java.cadastros.CadastroProduto;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroSafra;
import main.java.classesExtras.CBProdutoPersonalizado;
import main.java.classesExtras.CBProdutoRenderPersonalizado;
import main.java.classesExtras.ComboBoxPersonalizado;
import main.java.classesExtras.ComboBoxRenderPersonalizado;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoSafras;

public class RelatorioContratoIndividual {

	private CadastroModelo modelo;
	private String path;

	CadastroContrato novo_contrato;
	private TelaEmEspera telaInformacoes;
	private int contador_comprovantes;
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private String servidor_unidade;
	private XWPFDocument document_global;
	private XWPFParagraph paragrafo_atual;
	private int posicao_global;
	private boolean interno, externo, incluir_carregamento, incluir_pagamento, incluir_comissao, incluir_sub_contratos,
			incluir_ganhos_potenciais, incluir_comprovantes_pagamentos, incluir_transferencias, incluir_recebimentos;

	public boolean isIncluir_transferencias() {
		return incluir_transferencias;
	}

	public boolean isIncluir_recebimentos() {
		return incluir_recebimentos;
	}

	public void setIncluir_recebimentos(boolean incluir_recebimentos) {
		this.incluir_recebimentos = incluir_recebimentos;
	}

	public void setIncluir_transferencias(boolean incluir_transferencias) {
		this.incluir_transferencias = incluir_transferencias;
	}

	public boolean isIncluir_comprovantes_pagamentos() {
		return incluir_comprovantes_pagamentos;
	}

	public void setIncluir_comprovantes_pagamentos(boolean incluir_comprovantes_pagamentos) {
		this.incluir_comprovantes_pagamentos = incluir_comprovantes_pagamentos;
	}

	public boolean isInterno() {
		return interno;
	}

	public void setInterno(boolean interno) {
		this.interno = interno;
	}

	public boolean isExterno() {
		return externo;
	}

	public void setExterno(boolean externo) {
		this.externo = externo;
	}

	public boolean isIncluir_carregamento() {
		return incluir_carregamento;
	}

	public void setIncluir_carregamento(boolean incluir_carregamento) {
		this.incluir_carregamento = incluir_carregamento;
	}

	public boolean isIncluir_pagamento() {
		return incluir_pagamento;
	}

	public void setIncluir_pagamento(boolean incluir_pagamento) {
		this.incluir_pagamento = incluir_pagamento;
	}

	public boolean isIncluir_comissao() {
		return incluir_comissao;
	}

	public void setIncluir_comissao(boolean incluir_comissao) {
		this.incluir_comissao = incluir_comissao;
	}

	public boolean isIncluir_sub_contratos() {
		return incluir_sub_contratos;
	}

	public void setIncluir_sub_contratos(boolean incluir_sub_contratos) {
		this.incluir_sub_contratos = incluir_sub_contratos;
	}

	public boolean isIncluir_ganhos_potenciais() {
		return incluir_ganhos_potenciais;
	}

	public void setIncluir_ganhos_potenciais(boolean incluir_ganhos_potenciais) {
		this.incluir_ganhos_potenciais = incluir_ganhos_potenciais;
	}

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

		pageSize.setOrient(STPageOrientation.LANDSCAPE);
		pageSize.setW(BigInteger.valueOf(15840));
		pageSize.setH(BigInteger.valueOf(12240));
		document_global.createStyles();

	}

	public ByteArrayOutputStream preparar() {

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
		titleRun.setText("Relatório de Contrato Individual N.º:" + novo_contrato.getCodigo() + " emitido em "
				+ novo_contrato.getData_contrato() + " por " + login.getNome() + " " + login.getSobrenome() + " ás "
				+ data.getHora());
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

		if (compradores[1] != null) {
			if (compradores[1].getTipo_pessoa() == 0) {
				// pessoa fisica
				nome_compradores = nome_compradores + ", " + compradores[1].getNome_empresarial();
			} else {
				nome_compradores = nome_compradores + ", " + compradores[0].getNome_fantaia();

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

		if (novo_contrato.getMedida().equalsIgnoreCase("Sacos")) {
			quantidade_sacos = novo_contrato.getQuantidade();
			quantidade_quilogramas = novo_contrato.getQuantidade() * 60;
		} else if (novo_contrato.getMedida().equalsIgnoreCase("KG")) {
			quantidade_quilogramas = novo_contrato.getQuantidade();
			quantidade_sacos = novo_contrato.getQuantidade() / 60;

		}

		XWPFParagraph informacoes = document_global.createParagraph();
		informacoes.setAlignment(ParagraphAlignment.LEFT);

		XWPFRun informacoesRun = informacoes.createRun();
		informacoesRun.setColor("000000");
		informacoesRun.setBold(false);
		informacoesRun.setFontFamily("Arial");
		informacoesRun.setFontSize(10);

		setSingleLineSpacing(informacoes);

		ArrayList<CadastroContrato> contrato_original = new ArrayList<>();
		contrato_original.add(novo_contrato);
		criarTabelaContrato(contrato_original);

		if (interno) {
			if (incluir_sub_contratos) {
				ArrayList<CadastroContrato> lista_sub_contratos = new GerenciarBancoContratos()
						.getSubContratos(novo_contrato.getId());

				if (lista_sub_contratos.size() > 0) {

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

					if (incluir_ganhos_potenciais)
						criarTabelaGanhosPotenciais(lista_sub_contratos);

				}
			}

		}

		
		
		if (incluir_recebimentos) {
			// recebimentos
			GerenciarBancoContratos gerenciar_recebimentos = new GerenciarBancoContratos();

			ArrayList<CadastroContrato.Recebimento> recebimentos = gerenciar_recebimentos
					.getRecebimentos(novo_contrato.getId());

			if (recebimentos.size() > 0) {

				adicionarTraco(true, 0);

				XWPFParagraph titulo_recebimentos = document_global.createParagraph();
				titulo_recebimentos.setAlignment(ParagraphAlignment.CENTER);

				XWPFRun titulo_recebimentosRun = titulo_recebimentos.createRun();
				titulo_recebimentosRun.setText("Recebimentos");
				titulo_recebimentosRun.setColor("000000");
				titulo_recebimentosRun.setBold(true);
				titulo_recebimentosRun.setFontFamily("Arial");
				titulo_recebimentosRun.setFontSize(9);

				criarTabelaRecebimentos(recebimentos, 0);

			}
		}


		if (incluir_carregamento) {
			// carregamentos
			GerenciarBancoContratos gerenciar_pags = new GerenciarBancoContratos();

			ArrayList<CadastroContrato.Carregamento> carregamentos = gerenciar_pags
					.getCarregamentos(novo_contrato.getId());

			if (carregamentos.size() > 0) {

				adicionarTraco(true, 0);

				XWPFParagraph titulo_carregamentos = document_global.createParagraph();
				titulo_carregamentos.setAlignment(ParagraphAlignment.CENTER);

				XWPFRun titulo_carregamentosRun = titulo_carregamentos.createRun();
				titulo_carregamentosRun.setText("Carregamentos");
				titulo_carregamentosRun.setColor("000000");
				titulo_carregamentosRun.setBold(true);
				titulo_carregamentosRun.setFontFamily("Arial");
				titulo_carregamentosRun.setFontSize(9);

				criarTabelaCarregamentos(carregamentos);

			}
		}

		if (incluir_pagamento) {
			// pagamentos
			GerenciarBancoContratos gerenciar_pags = new GerenciarBancoContratos();
			ArrayList<PagamentoCompleto> pagamentos_contratuais = gerenciar_pags
					.getPagamentosContratuaisParaRelatorio(novo_contrato.getId());

			if (pagamentos_contratuais.size() > 0) {

				adicionarTraco(true, 0);
				XWPFParagraph titulo_sub_pagamentos = document_global.createParagraph();
				titulo_sub_pagamentos.setAlignment(ParagraphAlignment.CENTER);

				XWPFRun titulo_sub_pagamentostitleRun = titulo_sub_pagamentos.createRun();
				titulo_sub_pagamentostitleRun.setText("Pagamentos");
				titulo_sub_pagamentostitleRun.setColor("000000");
				titulo_sub_pagamentostitleRun.setBold(true);
				titulo_sub_pagamentostitleRun.setFontFamily("Arial");
				titulo_sub_pagamentostitleRun.setFontSize(9);
				criarTabelaPagamentos(pagamentos_contratuais);

				// adiciona comprovante de pagamentos
				if (incluir_comprovantes_pagamentos)
					adicionarComprovantesPagamentos(pagamentos_contratuais);

			}
		}
	
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
			// JOptionPane.showMessageDialog(null,
			// "Erro ao criar cabecalho e rodape do contrato!\nConsulte o administrador do
			// sistema!");
			e.printStackTrace();
		}

		ByteArrayOutputStream saida_apos_edicao = new ByteArrayOutputStream();

		/*
		 * try { document_global.write(new FileOutputStream("c:\\arquivoteste.docx"));
		 * document_global.write(saida_apos_edicao);
		 * 
		 * } catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
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
		if (ppr == null)
			ppr = para.getCTP().addNewPPr();
		CTSpacing spacing = ppr.isSetSpacing() ? ppr.getSpacing() : ppr.addNewSpacing();
		spacing.setAfter(BigInteger.valueOf(0));
		spacing.setBefore(BigInteger.valueOf(0));
		spacing.setLineRule(STLineSpacingRule.AUTO);
		spacing.setLine(BigInteger.valueOf(240));
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
		tracotitleRun.setFontSize(9);

	}

	public void adicionarComprovantesPagamentos(ArrayList<PagamentoCompleto> pagamentos) {

		XWPFParagraph par = document_global.createParagraph();

		// pega os documentos
		GerenciarBancoDocumento gerenciar = new GerenciarBancoDocumento();
		ArrayList<CadastroDocumento> docs = gerenciar.getDocumentos(novo_contrato.getId());

		int num_comprovantes = 0;

		for (CadastroDocumento doc : docs) {
			if (doc.getTipo() == 2) {
				// e um pagamento
				num_comprovantes++;
			}
		}

		// define o numero de linhas
		int num_linhas = (num_comprovantes / 3) + 1;

		XWPFTable table = document_global.createTable(num_linhas, 3);

		setTableAlign(table, ParagraphAlignment.CENTER);
		XWPFTableRow tableRowOne = table.getRow(0);
		tableRowOne.getCell(0).removeParagraph(0);

		XWPFParagraph paragraph = tableRowOne.getCell(0).addParagraph();

		int linha_comprovante = -1;
		int coluna_comprovante = 0;

		for (PagamentoCompleto pag : pagamentos) {
			// verifica se há algum documento do tipo 2(comprovante de pagamento) para este
			// id de pagamento

			boolean tem_comprovante = false;
			CadastroDocumento comprovante = new CadastroDocumento();

			for (CadastroDocumento doc : docs) {
				if (doc.getId_pai() == pag.getId_pagamento()) {
					comprovante = doc;
					tem_comprovante = true;
					break;
				}
			}

			if (tem_comprovante) {
				// verifica a extensao do arquivo
				String extensaoDoArquivo = FilenameUtils.getExtension(comprovante.getNome_arquivo());
				if (extensaoDoArquivo.equalsIgnoreCase("png")) {

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
					 * FileInputStream in; try { in = new FileInputStream(caminho_completo);
					 * run.addPicture(in, Document.PICTURE_TYPE_JPEG, comprovante.getNome_arquivo(),
					 * Units.toEMU(x/3), Units.toEMU(y/3)); in.close(); } catch
					 * (FileNotFoundException e) { // TODO Auto-generated catch block
					 * e.printStackTrace(); } catch (InvalidFormatException e) { // TODO
					 * Auto-generated catch block e.printStackTrace(); } catch (IOException e) { //
					 * TODO Auto-generated catch block e.printStackTrace(); }
					 */

					FileInputStream in;
					try {

						if (contador_comprovantes <= 2) {
							linha_comprovante = 0;
						} else if (contador_comprovantes <= 6) {
							linha_comprovante = 1;

						} else if (contador_comprovantes <= 10) {
							linha_comprovante = 2;

						} else if (contador_comprovantes <= 14) {
							linha_comprovante = 3;

						} else if (contador_comprovantes <= 18) {
							linha_comprovante = 4;

						} else if (contador_comprovantes <= 22) {
							linha_comprovante = 5;

						} else if (contador_comprovantes <= 26) {
							linha_comprovante = 6;

						} else if (contador_comprovantes <= 30) {
							linha_comprovante = 7;

						}

						tableRowOne = table.getRow(linha_comprovante);
						tableRowOne.getCell(coluna_comprovante).removeParagraph(0);
						paragraph = tableRowOne.getCell(coluna_comprovante).addParagraph();
						paragraph.setAlignment(ParagraphAlignment.CENTER);

						XWPFRun run = paragraph.createRun();

						in = new FileInputStream(caminho_completo);
						run.addPicture(in, Document.PICTURE_TYPE_JPEG, comprovante.getNome_arquivo(),
								Units.toEMU(x/2 ), Units.toEMU(y /2));
						in.close();
						contador_comprovantes++;
						coluna_comprovante++;

						if (coluna_comprovante >= 3) {
							coluna_comprovante = 0;
						}
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

			}

		}

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
					if (palavra.equals("preco_decimal")) {

						Locale ptBr = new Locale("pt", "BR");
						String valorString = NumberFormat.getCurrencyInstance(ptBr)
								.format((novo_contrato.getValor_produto()));
						System.out.println(valorString);

						adicionarTextoParagrafoAtual(valorString + " ", true);
					} else if (palavra.equals("preco_extenso")) {

						String valor_extenso = new PorExtenso(novo_contrato.getValor_produto()).toString();
						adicionarTextoParagrafoAtual(palavra.replace("preco_extenso", valor_extenso.toLowerCase() + ""),
								false);

					} else {
						adicionarTextoParagrafoAtual(palavra + " ", false);
					}
				}

			}
		}

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
					if (palavra.equals("preco_decimal")) {

						Locale ptBr = new Locale("pt", "BR");
						String valorString = NumberFormat.getCurrencyInstance(ptBr)
								.format((novo_contrato.getValor_produto()));
						System.out.println(valorString);

						adicionarTextoParagrafoAtual(valorString + " ", true);
					} else if (palavra.equals("preco_extenso")) {

						String valor_extenso = new PorExtenso(novo_contrato.getValor_produto()).toString();
						adicionarTextoParagrafoAtual(palavra.replace("preco_extenso", valor_extenso.toLowerCase() + ""),
								false);

					} else {
						adicionarTextoParagrafoAtual(palavra + " ", false);
					}
				}

			}
		}

	}

	private void setOrientacao(int flag) {
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

		if (flag == 1) {
			pageSize.setOrient(STPageOrientation.PORTRAIT);
			pageSize.setW(BigInteger.valueOf(842 * 20));
			pageSize.setH(BigInteger.valueOf(595 * 20));
		} else {
			pageSize.setOrient(STPageOrientation.LANDSCAPE);
			pageSize.setH(BigInteger.valueOf(842 * 20));
			pageSize.setW(BigInteger.valueOf(595 * 20));
		}
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

	public void criarParagrafoTabela(XWPFParagraph paragraph, String texto, boolean negrito) {
		// paragraph.setIndentationLeft(100);
		// paragraph.setIndentationRight(100);
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

		String string_valor_total_contrato_original = NumberFormat.getCurrencyInstance(ptBr)
				.format(valor_total_contrato_original);

		double valor_total_sub_contratos = 0;

		for (CadastroContrato sub : lista_sub_contratos) {

			double valor_local = Double.parseDouble(sub.getValor_a_pagar().toPlainString());
			valor_total_sub_contratos += valor_local;
		}

		String string_valor_total_sub_contratos = NumberFormat.getCurrencyInstance(ptBr)
				.format(valor_total_sub_contratos);

		double valor_total_diferenca_contratos = valor_total_sub_contratos-  valor_total_contrato_original ;

		String string_valor_total_diferenca_contratos = NumberFormat.getCurrencyInstance(ptBr)
				.format(valor_total_diferenca_contratos);

		double valor_total_comissoes = 0;

		if (novo_contrato.getComissao() == 1) {
			double valor_local = Double.parseDouble(novo_contrato.getValor_comissao().toPlainString());
			valor_total_comissoes += valor_local;
		}

		for (CadastroContrato sub : lista_sub_contratos) {

			if (sub.getComissao() == 1) {
				double valor_local = Double.parseDouble(sub.getValor_comissao().toPlainString());
				valor_total_comissoes += valor_local;
			}

		}

		String string_valor_total_comissoes = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_comissoes);

		double valor_total_ganhos_potenciais = valor_total_diferenca_contratos + valor_total_comissoes;

		String string_valor_total_ganhos_potenciais = NumberFormat.getCurrencyInstance(ptBr)
				.format(valor_total_ganhos_potenciais);

		NumberFormat z = NumberFormat.getNumberInstance();

		// criarParagrafo(1);
		// linhas x colunas
		XWPFTable table = document_global.createTable(2, 5);

		setTableAlign(table, ParagraphAlignment.CENTER);
		XWPFTableRow tableRowOne = table.getRow(0);
		tableRowOne.getCell(0).removeParagraph(0);

		XWPFParagraph paragraph = tableRowOne.getCell(0).addParagraph();

		criarParagrafoTabela(paragraph, "Valor Contratos:", false);

		tableRowOne = table.getRow(1);
		tableRowOne.getCell(0).removeParagraph(0);
		paragraph = tableRowOne.getCell(0).addParagraph();
		criarParagrafoTabela(paragraph, string_valor_total_contrato_original, false);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(1).removeParagraph(0);
		paragraph = tableRowOne.getCell(1).addParagraph();
		criarParagrafoTabela(paragraph, "Valor Sub-Contratos:", false);

		tableRowOne = table.getRow(1);
		tableRowOne.getCell(1).removeParagraph(0);
		paragraph = tableRowOne.getCell(1).addParagraph();
		criarParagrafoTabela(paragraph, string_valor_total_sub_contratos, false);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(2).removeParagraph(0);
		paragraph = tableRowOne.getCell(2).addParagraph();
		criarParagrafoTabela(paragraph, "Valor Diferença:", false);

		tableRowOne = table.getRow(1);
		tableRowOne.getCell(2).removeParagraph(0);
		paragraph = tableRowOne.getCell(2).addParagraph();
		criarParagrafoTabela(paragraph, string_valor_total_diferenca_contratos, false);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(3).removeParagraph(0);
		paragraph = tableRowOne.getCell(3).addParagraph();
		criarParagrafoTabela(paragraph, "Valor Comissões", false);

		tableRowOne = table.getRow(1);
		tableRowOne.getCell(3).removeParagraph(0);
		paragraph = tableRowOne.getCell(3).addParagraph();
		criarParagrafoTabela(paragraph, string_valor_total_comissoes, false);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(4).removeParagraph(0);
		paragraph = tableRowOne.getCell(4).addParagraph();
		criarParagrafoTabela(paragraph, "Potenciais Ganhos", false);

		tableRowOne = table.getRow(1);
		tableRowOne.getCell(4).removeParagraph(0);
		paragraph = tableRowOne.getCell(4).addParagraph();
		criarParagrafoTabela(paragraph, string_valor_total_ganhos_potenciais, false);

	}

	public void criarTabelaContrato(ArrayList<CadastroContrato> lista_sub_contratos) {

		NumberFormat z = NumberFormat.getNumberInstance();

		Locale ptBr = new Locale("pt", "BR");

		int numero_colunas = 0;

		if (incluir_comissao) {
			numero_colunas = 11;
		} else {
			numero_colunas = 9;

		}

		// criarParagrafo(1);
		// linhas x colunas
		XWPFTable table = document_global.createTable(lista_sub_contratos.size() + 1, numero_colunas);

		setTableAlign(table, ParagraphAlignment.CENTER);
		XWPFTableRow tableRowOne = table.getRow(0);
		tableRowOne.getCell(0).removeParagraph(0);

		XWPFParagraph paragraph = tableRowOne.getCell(0).addParagraph();

		criarParagrafoTabela(paragraph, "Codigo", false);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(1).removeParagraph(0);
		paragraph = tableRowOne.getCell(1).addParagraph();
		criarParagrafoTabela(paragraph, "Status", false);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(2).removeParagraph(0);
		paragraph = tableRowOne.getCell(2).addParagraph();
		criarParagrafoTabela(paragraph, "Comprador", false);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(3).removeParagraph(0);
		paragraph = tableRowOne.getCell(3).addParagraph();
		criarParagrafoTabela(paragraph, "Vendedores", false);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(4).removeParagraph(0);
		paragraph = tableRowOne.getCell(4).addParagraph();
		criarParagrafoTabela(paragraph, "Produto", false);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(5).removeParagraph(0);
		paragraph = tableRowOne.getCell(5).addParagraph();
		criarParagrafoTabela(paragraph, "Safra", false);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(6).removeParagraph(0);
		paragraph = tableRowOne.getCell(6).addParagraph();
		criarParagrafoTabela(paragraph, "Valor", false);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(7).removeParagraph(0);
		paragraph = tableRowOne.getCell(7).addParagraph();
		criarParagrafoTabela(paragraph, "Quantidade(sacos)", false);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(8).removeParagraph(0);
		paragraph = tableRowOne.getCell(8).addParagraph();
		criarParagrafoTabela(paragraph, "Valor Total", false);

		if (incluir_comissao) {
			tableRowOne = table.getRow(0);
			tableRowOne.getCell(9).removeParagraph(0);
			paragraph = tableRowOne.getCell(9).addParagraph();
			criarParagrafoTabela(paragraph, "Comissão(saco)", false);

			tableRowOne = table.getRow(0);
			tableRowOne.getCell(10).removeParagraph(0);
			paragraph = tableRowOne.getCell(10).addParagraph();
			criarParagrafoTabela(paragraph, "Comissão(Total)", false);

		}

		int indice = 0;

		for (int i = 1; i < lista_sub_contratos.size() + 1; i++) {

			CadastroContrato local = lista_sub_contratos.get(indice);

			double quantidade_sacos_sub = 0;
			double quantidade_quilogramas_sub = 0;

			if (local.getMedida().equalsIgnoreCase("Sacos")) {
				quantidade_sacos_sub = local.getQuantidade();
				quantidade_quilogramas_sub = local.getQuantidade() * 60;
			} else if (local.getMedida().equalsIgnoreCase("KG")) {
				quantidade_quilogramas_sub = local.getQuantidade();
				quantidade_sacos_sub = local.getQuantidade() / 60;

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

			// linha com dados
			tableRowOne = table.getRow(i);
			tableRowOne.getCell(0).removeParagraph(0);
			paragraph = tableRowOne.getCell(0).addParagraph();
			criarParagrafoTabela(paragraph, local.getCodigo(), false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(1).removeParagraph(0);
			paragraph = tableRowOne.getCell(1).addParagraph();

			int status = local.getStatus_contrato();
			String text_status = "";
			if (status == 1) {
				text_status = "Assinar";

			} else if (status == 2) {
				text_status = "Assinado";

			} else if (status == 3) {
				text_status = "Cumprindo";

			}
			criarParagrafoTabela(paragraph, text_status, false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(2).removeParagraph(0);
			paragraph = tableRowOne.getCell(2).addParagraph();
			criarParagrafoTabela(paragraph, nome_compradores_sub, false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(3).removeParagraph(0);
			paragraph = tableRowOne.getCell(3).addParagraph();
			criarParagrafoTabela(paragraph, nome_vendedores_sub, false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(4).removeParagraph(0);
			paragraph = tableRowOne.getCell(4).addParagraph();
			criarParagrafoTabela(paragraph, local.getModelo_safra().getProduto().getNome_produto(), false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(5).removeParagraph(0);
			paragraph = tableRowOne.getCell(5).addParagraph();
			criarParagrafoTabela(paragraph,
					local.getModelo_safra().getAno_plantio() + "/" + local.getModelo_safra().getAno_colheita(), false);

			String valorString = NumberFormat.getCurrencyInstance(ptBr).format(local.getValor_produto());
			tableRowOne = table.getRow(i);
			tableRowOne.getCell(6).removeParagraph(0);
			paragraph = tableRowOne.getCell(6).addParagraph();
			criarParagrafoTabela(paragraph, valorString, false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(7).removeParagraph(0);
			paragraph = tableRowOne.getCell(7).addParagraph();
			criarParagrafoTabela(paragraph, z.format(local.getQuantidade()), false);

			valorString = NumberFormat.getCurrencyInstance(ptBr).format(local.getValor_a_pagar());
			tableRowOne = table.getRow(i);
			tableRowOne.getCell(8).removeParagraph(0);
			paragraph = tableRowOne.getCell(8).addParagraph();
			criarParagrafoTabela(paragraph, valorString, false);

			if (incluir_comissao) {
				String comissao = "";
				if (local.getComissao() == 1) {
					comissao = NumberFormat.getCurrencyInstance(ptBr).format(local.getValor_comissao());
				} else {
					comissao = "Não";
				}

				String comissao_por_saco = "";
				if (local.getComissao() == 1) {
					BigDecimal valor_total_com = local.getValor_comissao();
					BigDecimal quantidade_total_sacos = new BigDecimal(Double.toString(quantidade_sacos_sub));
					BigDecimal valor_por_saco = valor_total_com.divide(quantidade_total_sacos, BigDecimal.ROUND_UP);
					comissao_por_saco = NumberFormat.getCurrencyInstance(ptBr).format(valor_por_saco);

				} else {
					comissao_por_saco = "Não";
				}

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(9).removeParagraph(0);
				paragraph = tableRowOne.getCell(9).addParagraph();
				criarParagrafoTabela(paragraph, comissao_por_saco, false);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(10).removeParagraph(0);
				paragraph = tableRowOne.getCell(10).addParagraph();
				criarParagrafoTabela(paragraph, comissao, false);
			} else {

			}

			indice++;
		}

		criarParagrafo(2);

	}

	public void criarTabelaPagamentos(ArrayList<PagamentoCompleto> pagamentos) {

		NumberFormat z = NumberFormat.getNumberInstance();

		Locale ptBr = new Locale("pt", "BR");

		// criarParagrafo(1);
		// linhas x colunas
		int num_total_linhas = 0;
		int num_linhas_comissao = 0;
		int num_linhas_trans = 0;
		int num_linhas_pag_normal = 0;

		for (PagamentoCompleto pag : pagamentos) {
			if (pag.getTipo() == 1) {
				num_linhas_pag_normal++;
			} else if (pag.getTipo() == 2) {
				num_linhas_comissao++;
			} else if (pag.getTipo() == 3) {
				num_linhas_trans++;
			}
		}

		if (incluir_comissao) {
			num_total_linhas += num_linhas_comissao;
		}
		if (incluir_transferencias) {
			num_total_linhas += num_linhas_trans;
		}

		num_total_linhas += num_linhas_pag_normal;

		XWPFTable table = document_global.createTable(num_total_linhas + 3, 12);

		setTableAlign(table, ParagraphAlignment.CENTER);
		XWPFTableRow tableRowOne = table.getRow(0);
		tableRowOne.getCell(0).removeParagraph(0);

		XWPFParagraph paragraph = tableRowOne.getCell(0).addParagraph();

		criarParagrafoTabela(paragraph, "Data", false);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(1).removeParagraph(0);
		paragraph = tableRowOne.getCell(1).addParagraph();
		criarParagrafoTabela(paragraph, "Tipo", false);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(2).removeParagraph(0);
		paragraph = tableRowOne.getCell(2).addParagraph();
		criarParagrafoTabela(paragraph, "Descrição", false);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(3).removeParagraph(0);
		paragraph = tableRowOne.getCell(3).addParagraph();
		criarParagrafoTabela(paragraph, "Valor Pagamento", false);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(4).removeParagraph(0);
		paragraph = tableRowOne.getCell(4).addParagraph();
		criarParagrafoTabela(paragraph, "Valor Unidade: ", false);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(5).removeParagraph(0);
		paragraph = tableRowOne.getCell(5).addParagraph();
		criarParagrafoTabela(paragraph, "Cobertura: ", false);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(6).removeParagraph(0);
		paragraph = tableRowOne.getCell(6).addParagraph();
		criarParagrafoTabela(paragraph, "Depositante", false);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(7).removeParagraph(0);
		paragraph = tableRowOne.getCell(7).addParagraph();
		criarParagrafoTabela(paragraph, "Conta Depositante", false);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(8).removeParagraph(0);
		paragraph = tableRowOne.getCell(8).addParagraph();
		criarParagrafoTabela(paragraph, "Favorecido", false);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(9).removeParagraph(0);
		paragraph = tableRowOne.getCell(9).addParagraph();
		criarParagrafoTabela(paragraph, "Conta Favorecido", false);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(10).removeParagraph(0);
		paragraph = tableRowOne.getCell(10).addParagraph();
		criarParagrafoTabela(paragraph, "Contrato Remetente", false);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(11).removeParagraph(0);
		paragraph = tableRowOne.getCell(11).addParagraph();
		criarParagrafoTabela(paragraph, "Contrato Destinatario", false);

		double valor_total_pagamentos = 0;
		double valor_total_pagamentos_efetuados = 0;
		double valor_total_transferencias_retiradas = 0;
		double valor_total_transferencias_recebidas = 0;
		double valor_total_pagamentos_restantes = 0;
		double valor_total_comissao = 0;
		double peso_total_cobertura = 0;
		double peso_total_cobertura_efetuados = 0;
		double peso_total_cobertura_transferencia_negativa = 0;
		double peso_total_cobertura_transferencia_positiva = 0;
		double peso_total_cobertura_restante = 0;
		double peso_total_cobertura_comissao = 0;
		double soma_total_pagamentos = 0.0;

		double quantidade_total_contrato_sacos = 0;
		double valor_por_saco = 0;

		if (novo_contrato.getMedida().equalsIgnoreCase("Kg")) {
			quantidade_total_contrato_sacos = novo_contrato.getQuantidade() / 60;
			valor_por_saco = novo_contrato.getValor_produto() * 60;
		} else if (novo_contrato.getMedida().equalsIgnoreCase("Sacos")) {
			quantidade_total_contrato_sacos = novo_contrato.getQuantidade();
			valor_por_saco = novo_contrato.getValor_produto();
		}
		GerenciarBancoContratos gerenciar_contratos = new GerenciarBancoContratos();

		//valor total e cobertura com base no total de sacos recebidos
		double quantidade_kgs_recebidos = gerenciar_contratos.getQuantidadeRecebida(novo_contrato.getId());
		peso_total_cobertura = quantidade_kgs_recebidos/60;
		valor_total_pagamentos = peso_total_cobertura * valor_por_saco;
		
		String valorSaco = NumberFormat.getCurrencyInstance(ptBr).format(valor_por_saco);
				int i_global = 1;
		for (PagamentoCompleto pagamento : pagamentos) {

			if (pagamento.getTipo() == 1 || pagamento.getTipo() == 2 && incluir_comissao
					|| pagamento.getTipo() == 3 && incluir_transferencias) {

				// celula data
				tableRowOne = table.getRow(i_global);
				tableRowOne.getCell(0).removeParagraph(0);
				paragraph = tableRowOne.getCell(0).addParagraph();
				criarParagrafoTabela(paragraph, pagamento.getData_pagamento(), false);

				// celula tipo
				int tipo = pagamento.getTipo();
				String s_tipo = "";
				if (pagamento.getTipo() == 1) {
					s_tipo = "NORMAL";
				} else if (pagamento.getTipo() == 2) {
					s_tipo = "COMISSÃO";
				} else if (pagamento.getTipo() == 3) {
					// é uma transferencia
					if (pagamento.getId_contrato_remetente() == novo_contrato.getId()) {
						s_tipo = "-TRANSFERENCIA";
					} else if (pagamento.getId_contrato_destinatario() == novo_contrato.getId()) {
						// é uma transferencia positiva
						s_tipo = "+TRANSFERENCIA";
					}

				}

				// celula tipo
				tableRowOne = table.getRow(i_global);
				tableRowOne.getCell(1).removeParagraph(0);
				paragraph = tableRowOne.getCell(1).addParagraph();
				criarParagrafoTabela(paragraph, s_tipo, false);

				// celula descricao
				tableRowOne = table.getRow(i_global);
				tableRowOne.getCell(2).removeParagraph(0);
				paragraph = tableRowOne.getCell(2).addParagraph();
				criarParagrafoTabela(paragraph, pagamento.getDescricao(), false);

				// valor pagamento
				double valor_pagamento = pagamento.getValor_pagamento();

				String valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor_pagamento);
				double cobertura = valor_pagamento / valor_por_saco;

				if (pagamento.getTipo() == 1) {
					valor_total_pagamentos_efetuados += valor_pagamento;
					soma_total_pagamentos += pagamento.getValor_pagamento();

				} else if (pagamento.getTipo() == 2) {
					// é uma comissão
					valor_total_comissao += valor_pagamento;

				} else if (pagamento.getTipo() == 3) {
					// é uma transferencia
					if (pagamento.getId_contrato_remetente() == novo_contrato.getId()) {
						// é uma transferencia negativa
						valor_total_transferencias_retiradas += valor_pagamento;
						soma_total_pagamentos -= pagamento.getValor_pagamento();

					} else if (pagamento.getId_contrato_destinatario() == novo_contrato.getId()) {
						// é uma transferencia positiva
						valor_total_transferencias_recebidas += valor_pagamento;
						soma_total_pagamentos += pagamento.getValor_pagamento();

					}

				}
				valorString = NumberFormat.getCurrencyInstance(ptBr).format(pagamento.getValor_pagamento());

				// celula pagamento
				tableRowOne = table.getRow(i_global);
				tableRowOne.getCell(3).removeParagraph(0);
				paragraph = tableRowOne.getCell(3).addParagraph();
				criarParagrafoTabela(paragraph, valorString, false);

				// valor da unidade
				CadastroContrato ct_remetente = pagamento.getContrato_remetente();
				CadastroContrato ct_destinatario = pagamento.getContrato_destinatario();

				valorString = NumberFormat.getCurrencyInstance(ptBr).format(ct_remetente.getValor_produto());
				if (pagamento.getTipo() == 1) {

				} else if (pagamento.getTipo() == 2) {

				} else if (pagamento.getTipo() == 3) {
					// é uma transferencia
					if (pagamento.getId_contrato_remetente() == novo_contrato.getId()) {

					} else if (pagamento.getId_contrato_destinatario() == novo_contrato.getId()) {
						// é uma transferencia positiva
						// pegar o preco da unidade do contrato que recebeu a transferencia
						valorString = NumberFormat.getCurrencyInstance(ptBr).format(novo_contrato.getValor_produto());

					}
				}
				// celula valor unidade
				tableRowOne = table.getRow(i_global);
				tableRowOne.getCell(4).removeParagraph(0);
				paragraph = tableRowOne.getCell(4).addParagraph();
				criarParagrafoTabela(paragraph, valorString, false);

				// cobertura
				cobertura = pagamento.getValor_pagamento() / ct_remetente.getValor_produto();
				if (ct_remetente.getMedida().equalsIgnoreCase("KG"))
					cobertura = cobertura / 60;

				String retorno = z.format(cobertura) + " sacos";

				if (pagamento.getTipo() == 1) {
					peso_total_cobertura_efetuados += cobertura;

				} else if (pagamento.getTipo() == 2) {
					peso_total_cobertura_comissao += cobertura;
				} else if (pagamento.getTipo() == 3) {
					// é uma transferencia
					if (pagamento.getId_contrato_remetente() == novo_contrato.getId()) {
						retorno = "-" + retorno;
						peso_total_cobertura_transferencia_negativa += cobertura;

					} else if (pagamento.getId_contrato_destinatario() == novo_contrato.getId()) {
						// é uma transferencia positiva
						// pegar o preco da unidade do contrato que recebeu a transferencia

						cobertura = pagamento.getValor_pagamento() / novo_contrato.getValor_produto();

						if (novo_contrato.getMedida().equalsIgnoreCase("KG"))
							cobertura = cobertura / 60;
						peso_total_cobertura_transferencia_positiva += cobertura;

						retorno = z.format(cobertura) + " sacos";
						retorno = "+" + retorno;
					}

				}

				// celula cobertura
				tableRowOne = table.getRow(i_global);
				tableRowOne.getCell(5).removeParagraph(0);
				paragraph = tableRowOne.getCell(5).addParagraph();
				criarParagrafoTabela(paragraph, retorno, false);

				// celula depositante
				tableRowOne = table.getRow(i_global);
				tableRowOne.getCell(6).removeParagraph(0);
				paragraph = tableRowOne.getCell(6).addParagraph();
				criarParagrafoTabela(paragraph, pagamento.getDepositante().toUpperCase(), false);

				// celula conta depositante
				tableRowOne = table.getRow(i_global);
				tableRowOne.getCell(7).removeParagraph(0);
				paragraph = tableRowOne.getCell(7).addParagraph();
				criarParagrafoTabela(paragraph, pagamento.getConta_bancaria_depositante().toUpperCase(), false);

				// celula favorecido
				tableRowOne = table.getRow(i_global);
				tableRowOne.getCell(8).removeParagraph(0);
				paragraph = tableRowOne.getCell(8).addParagraph();
				criarParagrafoTabela(paragraph, pagamento.getFavorecido().toUpperCase(), false);

				// celula conta favorecido
				tableRowOne = table.getRow(i_global);
				tableRowOne.getCell(9).removeParagraph(0);
				paragraph = tableRowOne.getCell(9).addParagraph();
				criarParagrafoTabela(paragraph, pagamento.getConta_bancaria_favorecido().toUpperCase(), false);

				// celula contrato remetente
				tableRowOne = table.getRow(i_global);
				tableRowOne.getCell(10).removeParagraph(0);
				paragraph = tableRowOne.getCell(10).addParagraph();
				criarParagrafoTabela(paragraph, ct_remetente.getCodigo(), false);

				// celula contrato destinataio
				tableRowOne = table.getRow(i_global);
				tableRowOne.getCell(11).removeParagraph(0);
				paragraph = tableRowOne.getCell(11).addParagraph();
				criarParagrafoTabela(paragraph, ct_destinatario.getCodigo(), false);

				i_global++;
			}

		}

		// somatorias
		tableRowOne = table.getRow(i_global);
		tableRowOne.getCell(2).removeParagraph(0);
		paragraph = tableRowOne.getCell(2).addParagraph();
		criarParagrafoTabela(paragraph, "TOTAL CONCLUÍDO:", false);

		tableRowOne = table.getRow(i_global);
		tableRowOne.getCell(3).removeParagraph(0);
		paragraph = tableRowOne.getCell(3).addParagraph();
		String valor = NumberFormat.getCurrencyInstance(ptBr).format(soma_total_pagamentos);

		criarParagrafoTabela(paragraph, valor, false);

		// somatorias
		tableRowOne = table.getRow(i_global);
		tableRowOne.getCell(4).removeParagraph(0);
		paragraph = tableRowOne.getCell(4).addParagraph();
		criarParagrafoTabela(paragraph, "COBERTURA TOTAL:", false);

		double peso_total_cobertura_concluida = peso_total_cobertura_efetuados
				- peso_total_cobertura_transferencia_negativa + peso_total_cobertura_transferencia_positiva;
		tableRowOne = table.getRow(i_global);
		tableRowOne.getCell(5).removeParagraph(0);
		paragraph = tableRowOne.getCell(5).addParagraph();
		criarParagrafoTabela(paragraph, z.format(peso_total_cobertura_concluida) + " sacos", false);

		i_global++;
		
		// somatorias
		tableRowOne = table.getRow(i_global);
		tableRowOne.getCell(2).removeParagraph(0);
		paragraph = tableRowOne.getCell(2).addParagraph();
		criarParagrafoTabela(paragraph, "VALOR RESTANTE:", false);

		valor_total_pagamentos_restantes = valor_total_pagamentos - valor_total_pagamentos_efetuados
				+ valor_total_transferencias_retiradas - valor_total_transferencias_recebidas;
		 valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos_restantes);
			tableRowOne = table.getRow(i_global);
			tableRowOne.getCell(3).removeParagraph(0);
			paragraph = tableRowOne.getCell(3).addParagraph();
			criarParagrafoTabela(paragraph, valor, false);
		
			tableRowOne = table.getRow(i_global);
			tableRowOne.getCell(4).removeParagraph(0);
			paragraph = tableRowOne.getCell(4).addParagraph();
			criarParagrafoTabela(paragraph, "COBERTURA RESTANTE:", false);
			
			peso_total_cobertura_restante = peso_total_cobertura - peso_total_cobertura_efetuados
					+ peso_total_cobertura_transferencia_negativa - peso_total_cobertura_transferencia_positiva;
			tableRowOne = table.getRow(i_global);
			tableRowOne.getCell(5).removeParagraph(0);
			paragraph = tableRowOne.getCell(5).addParagraph();
			criarParagrafoTabela(paragraph, z.format(peso_total_cobertura_restante) + " sacos", false);
			
			
		if (incluir_comissao) {
			XWPFParagraph paragrafo = document_global.createParagraph();
			XWPFRun run = paragrafo.createRun();
			run.setText("*Valor de comissão não é somado ao valor total");
			run.setColor("ff0000");
			run.setFontFamily("Times New Roman");
			run.setFontSize(8);

		}

		//adicionar valores
		
		//adicionais
		/******************* inicio adicionais ***********************/
	
		String texto_total = "\nTotal do Contrato: "; 
		valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos);
		texto_total += valor;
		texto_total += " Cobre: ";
		texto_total += z.format(peso_total_cobertura) + " sacos";

		String texto_efetuados = "Efetuados: ";
		valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos_efetuados);
		texto_efetuados += valor;
		texto_efetuados += " Cobre: ";
		texto_efetuados += z.format(peso_total_cobertura_efetuados) + " sacos";
		
		
		//status
		String texto_transferencias_negativas = "";
		if(incluir_transferencias) {
		//transferencias negativas
		texto_transferencias_negativas = "Transferencias:(-) ";
		valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_transferencias_retiradas);
		texto_transferencias_negativas += valor;
		texto_transferencias_negativas += " Cobre: ";
		texto_transferencias_negativas += z.format(peso_total_cobertura_transferencia_negativa) + " sacos";

		}
		//status
		double diferenca = (valor_total_pagamentos_efetuados - valor_total_transferencias_retiradas
				+ valor_total_transferencias_recebidas) - valor_total_pagamentos;
		String status_pagamento = "[Pagamentos:] ";
		if (diferenca == 0) {
			status_pagamento += "[Pagamento Concluído]";
		} else if (diferenca > 0) {
			status_pagamento += "[Excedeu] [em] [" + NumberFormat.getCurrencyInstance(ptBr).format(diferenca) + "]";

		} else if (diferenca < 0) {
			status_pagamento += "[Incompleto], [falta] [" + NumberFormat.getCurrencyInstance(ptBr).format(diferenca) + "]";

		}
		
		String texto_transferencias_positivas = "";
		if(incluir_transferencias) {
		//transferencias positivas
	    texto_transferencias_positivas = "Transferencias:(+) ";
		valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_transferencias_recebidas);
		texto_transferencias_positivas += valor;
		texto_transferencias_positivas += " Cobre: ";
		texto_transferencias_positivas += z.format(peso_total_cobertura_transferencia_positiva) + " sacos";

		
		}
		String status_cobertura = "[Cobertura:] ";
		double diferenca_pesos = peso_total_cobertura_restante;

		if (diferenca_pesos == 0 || diferenca_pesos == -0) {
			status_cobertura += "[Todos] [os] [sacos] [foram] [pagos]";
		} else if (diferenca_pesos < 0) {
			status_cobertura += "[Excedeu] [em] [" + z.format(diferenca_pesos) + "] [Sacos]";

		} else if (diferenca_pesos > 0) {
			status_cobertura += "[Incompleto], [falta] [pagar] [" + z.format(diferenca_pesos) + "] [Sacos]";

		}

		
		//comissão
		String texto_comissao = "";
		if(incluir_comissao) {
		 texto_comissao = "Comissão: ";
		valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_comissao);
		texto_comissao += valor;
		texto_comissao += " Cobre: ";
		texto_comissao += z.format(peso_total_cobertura_comissao) + " sacos";

	
		}
	
		
		//concluidos			

		String texto_concluida = "Concluída:";

		double valor_total_pagamentos_concluidos = valor_total_pagamentos_efetuados - valor_total_transferencias_retiradas + valor_total_transferencias_recebidas; 
		valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos_concluidos);
		texto_concluida += valor;
		texto_concluida += " Cobre: ";
		 peso_total_cobertura_concluida =  peso_total_cobertura_efetuados
					- peso_total_cobertura_transferencia_negativa + peso_total_cobertura_transferencia_positiva;
		texto_concluida += z.format(peso_total_cobertura_concluida) + " sacos";
		
		//restante
		
		String texto_restante = " Restante:";

		valor_total_pagamentos_restantes = valor_total_pagamentos - valor_total_pagamentos_efetuados
				+ valor_total_transferencias_retiradas - valor_total_transferencias_recebidas;
		valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos_restantes);
		texto_restante += valor;
		texto_restante += " Cobre: ";
		peso_total_cobertura_restante = peso_total_cobertura - peso_total_cobertura_efetuados
		+ peso_total_cobertura_transferencia_negativa - peso_total_cobertura_transferencia_positiva;
		texto_restante += z.format(peso_total_cobertura_restante) + " sacos";

		
		String texto_final = texto_total + "\n";
		
		if(incluir_transferencias) {
			texto_final = texto_final + texto_transferencias_negativas + "\n" + texto_transferencias_positivas + "\n";
		}
		if(incluir_comissao) {
			texto_final = texto_final + texto_comissao + "\n";
		}
		
		texto_final = texto_final + texto_concluida + "\n" + texto_restante;
		texto_final = texto_final + "\n\n [Status] [do] [Pagamento]:\n";
		texto_final = texto_final + status_pagamento + "\n" + status_cobertura;
		
		substituirTexto(texto_final);
		
		/*************fim adicionai*************************/
		
		
	}

	public void incluir_legenda_transferencias_negativas(
			ArrayList<CadastroContrato.CadastroTransferenciaPagamentoContratual> transferencias) {

		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
		for (CadastroContrato.CadastroTransferenciaPagamentoContratual transferencia : transferencias) {
			CadastroContrato contrato = gerenciar.getContrato(transferencia.getId_contrato_destinatario());

			String texto = "";
			CadastroCliente compradores[] = contrato.getCompradores();

			String nome_comprador = "";
			if (compradores[0].getTipo_pessoa() == 0)
				nome_comprador = compradores[0].getNome_empresarial().toUpperCase();
			else
				nome_comprador = compradores[0].getNome_fantaia().toUpperCase();

			CadastroCliente vendedores[] = contrato.getVendedores();

			String nome_vendedor = "";
			if (vendedores[0].getTipo_pessoa() == 0)
				nome_vendedor = vendedores[0].getNome_empresarial().toUpperCase();
			else
				nome_vendedor = vendedores[0].getNome_fantaia().toUpperCase();

			texto = "(*) Valor transferido para o contrato " + contrato.getCodigo() + " entre " + nome_comprador + " x "
					+ nome_vendedor + " " + contrato.getQuantidade() + " " + contrato.getMedida() + " de "
					+ contrato.getModelo_produto().getNome_produto() + " da safra "
					+ contrato.getModelo_safra().getAno_plantio() + "/" + contrato.getModelo_safra().getAno_colheita()

			;

			substituirTexto(texto);
		}

	}

	public void incluir_legenda_transferencias_positivas(
			ArrayList<CadastroContrato.CadastroTransferenciaPagamentoContratual> transferencias) {

		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
		for (CadastroContrato.CadastroTransferenciaPagamentoContratual transferencia : transferencias) {
			CadastroContrato contrato = gerenciar.getContrato(transferencia.getId_contrato_remetente());

			String texto = "";
			CadastroCliente compradores[] = contrato.getCompradores();

			String nome_comprador = "";
			if (compradores[0].getTipo_pessoa() == 0)
				nome_comprador = compradores[0].getNome_empresarial().toUpperCase();
			else
				nome_comprador = compradores[0].getNome_fantaia().toUpperCase();

			CadastroCliente vendedores[] = contrato.getVendedores();

			String nome_vendedor = "";
			if (vendedores[0].getTipo_pessoa() == 0)
				nome_vendedor = vendedores[0].getNome_empresarial().toUpperCase();
			else
				nome_vendedor = vendedores[0].getNome_fantaia().toUpperCase();

			texto = "(*) Valor recebido do contrato " + contrato.getCodigo() + " entre " + nome_comprador + " x "
					+ nome_vendedor + " " + contrato.getQuantidade() + " " + contrato.getMedida() + " de "
					+ contrato.getModelo_produto().getNome_produto() + " da safra "
					+ contrato.getModelo_safra().getAno_plantio() + "/" + contrato.getModelo_safra().getAno_colheita()

			;

			substituirTexto(texto);
		}

	}

	public void criarTabelaCarregamentos(ArrayList<CadastroContrato.Carregamento> carregamentos) {
		// XWPFParagraph par = document_global.createParagraph();

		NumberFormat z = NumberFormat.getNumberInstance();

		Locale ptBr = new Locale("pt", "BR");

		// criarParagrafo(1);
		// linhas x colunas

		GerenciarBancoTransferenciasCarga gerenciar_trans = new GerenciarBancoTransferenciasCarga();
		ArrayList<CadastroContrato.CadastroTransferenciaCarga> transferencias_remetentes = gerenciar_trans
				.getTransferenciasRemetente(novo_contrato.getId());
		ArrayList<CadastroContrato.CadastroTransferenciaCarga> transferencias_destinatarios = gerenciar_trans
				.getTransferenciaDestinatario(novo_contrato.getId());

		int num_linhas_carregamentos = carregamentos.size() + transferencias_remetentes.size()
				+ transferencias_destinatarios.size() + 1 + 1;
		double soma_total_carga_real = 0;
		double soma_total_carga_nfa = 0;
		double quantidade_kg = 0;

		if (novo_contrato.getMedida().equalsIgnoreCase("KG")) {
			quantidade_kg = novo_contrato.getQuantidade();
			;
		} else if (novo_contrato.getMedida().equalsIgnoreCase("Sacos")) {
			quantidade_kg = novo_contrato.getQuantidade() * 60;

		}
		
		GerenciarBancoContratos gerenciar_contratos = new GerenciarBancoContratos();

		double quantidade_kgs_recebidos = gerenciar_contratos.getQuantidadeRecebida(novo_contrato.getId());
		quantidade_kg = quantidade_kgs_recebidos;

		XWPFTable table = document_global.createTable(num_linhas_carregamentos, 16);

		setTableAlign(table, ParagraphAlignment.CENTER);
		XWPFTableRow tableRowOne = table.getRow(0);
		tableRowOne.getCell(0).removeParagraph(0);

		XWPFParagraph paragraph = tableRowOne.getCell(0).addParagraph();

		criarParagrafoTabela(paragraph, "CONTRATO", true);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(1).removeParagraph(0);
		paragraph = tableRowOne.getCell(1).addParagraph();
		criarParagrafoTabela(paragraph, "DATA", true);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(2).removeParagraph(0);
		paragraph = tableRowOne.getCell(2).addParagraph();
		criarParagrafoTabela(paragraph, "CLIENTE", true);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(3).removeParagraph(0);
		paragraph = tableRowOne.getCell(3).addParagraph();
		criarParagrafoTabela(paragraph, "VENDEDOR", true);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(4).removeParagraph(0);
		paragraph = tableRowOne.getCell(4).addParagraph();
		criarParagrafoTabela(paragraph, "TRANSPORTADOR", true);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(5).removeParagraph(0);
		paragraph = tableRowOne.getCell(5).addParagraph();
		criarParagrafoTabela(paragraph, "VEICULO", true);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(6).removeParagraph(0);
		paragraph = tableRowOne.getCell(6).addParagraph();
		criarParagrafoTabela(paragraph, "PRODUTO", true);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(7).removeParagraph(0);
		paragraph = tableRowOne.getCell(7).addParagraph();
		criarParagrafoTabela(paragraph, "ROM", true);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(8).removeParagraph(0);
		paragraph = tableRowOne.getCell(8).addParagraph();
		criarParagrafoTabela(paragraph, "PESO ROM", true);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(9).removeParagraph(0);
		paragraph = tableRowOne.getCell(9).addParagraph();
		criarParagrafoTabela(paragraph, "NF 1", true);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(10).removeParagraph(0);
		paragraph = tableRowOne.getCell(10).addParagraph();
		criarParagrafoTabela(paragraph, "PESO NF 1", true);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(11).removeParagraph(0);
		paragraph = tableRowOne.getCell(11).addParagraph();
		criarParagrafoTabela(paragraph, "VALOR NF 1", true);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(12).removeParagraph(0);
		paragraph = tableRowOne.getCell(12).addParagraph();
		criarParagrafoTabela(paragraph, "NF 2", true);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(13).removeParagraph(0);
		paragraph = tableRowOne.getCell(13).addParagraph();
		criarParagrafoTabela(paragraph, "PESO NF 2", true);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(14).removeParagraph(0);
		paragraph = tableRowOne.getCell(14).addParagraph();
		criarParagrafoTabela(paragraph, "VALOR NF 2", true);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(15).removeParagraph(0);
		paragraph = tableRowOne.getCell(15).addParagraph();
		criarParagrafoTabela(paragraph, "DIFERENÇA", true);

		int i = 1;

		double peso_total_romaneios = 0.0;
		double peso_total_nf_interna = 0.0;

		double peso_total_nf_venda1 = 0.0;
		double peso_total_nf_complemento = 0.0;
		double peso_total_diferenca = 0.0;

		BigDecimal valor_total_nf_venda1 = BigDecimal.ZERO;
		BigDecimal valor_total_nf_complemento = BigDecimal.ZERO;

		// fazer checkagens

		boolean nf_interna_ativo = false;
		boolean nf_venda_ativo = false;
		boolean nf_complemento_ativo = false;

		// checka se ha no minimo uma nf interna aplicavel
		for (CadastroContrato.Carregamento carregamento : carregamentos) {
			if (carregamento.getNf_interna_aplicavel() == 1) {
				nf_interna_ativo = true;
				break;
			}

		}

		// checka se ha no minimo uma nf venda aplicavel
		for (CadastroContrato.Carregamento carregamento : carregamentos) {
			if (carregamento.getNf_venda1_aplicavel() == 1) {
				nf_venda_ativo = true;
				break;
			}

		}

		// checka se ha no minimo uma nf complemento aplicavel
		for (CadastroContrato.Carregamento carregamento : carregamentos) {
			if (carregamento.getNf_complemento_aplicavel() == 1) {
				nf_complemento_ativo = true;
				break;
			}

		}

		for (CadastroContrato.Carregamento carregamento : carregamentos) {

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

			String nome_cliente_completo = nome_cliente;

			String nome_cliente_quebrado[] = nome_cliente.split(" ");
			try {

				if (nome_cliente_quebrado.length > 2) {
					if (nome_cliente_quebrado[2].length() > 1) {
						nome_cliente = nome_cliente_quebrado[0] + " " + nome_cliente_quebrado[2];
					} else {
						if (nome_cliente_quebrado[3].length() > 1) {
							nome_cliente = nome_cliente_quebrado[0] + " " + nome_cliente_quebrado[3];

						} else {
							nome_cliente = nome_cliente_quebrado[0] + " " + nome_cliente_quebrado[1];

						}
					}
				}

			} catch (Exception v) {
				nome_cliente = nome_cliente_completo;
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

			String nome_vendedor_completo = nome_vendedor;

			String nome_vendedor_quebrado[] = nome_vendedor.split(" ");
			try {

				if (nome_vendedor_quebrado.length > 2) {
					if (nome_vendedor_quebrado[2].length() > 1) {
						nome_vendedor = nome_vendedor_quebrado[0] + " " + nome_vendedor_quebrado[2];
					} else {
						if (nome_vendedor_quebrado[3].length() > 1) {
							nome_vendedor = nome_vendedor_quebrado[0] + " " + nome_vendedor_quebrado[3];

						} else {
							nome_vendedor = nome_vendedor_quebrado[0] + " " + nome_vendedor_quebrado[1];

						}
					}
				}

			} catch (Exception v) {
				nome_vendedor = nome_vendedor_completo;
			}

			String nome_transportador = "";
			String placa_trator = "";
			if (carregamento.getId_transportador() > 0) {
				// pegar transportador e veiculo
				CadastroCliente transportador_carregamento = gerenciar_clientes
						.getCliente(carregamento.getId_transportador());

				if (transportador_carregamento != null) {
					if (transportador_carregamento.getTipo_pessoa() == 0) {
						nome_transportador = transportador_carregamento.getNome_empresarial();
					} else {
						nome_transportador = transportador_carregamento.getNome_fantaia();

					}
				}
				CadastroCliente.Veiculo veiculo_carregamento = null;
				if (carregamento.getId_veiculo() > 0) {
					for (CadastroCliente.Veiculo veiculo : transportador_carregamento.getVeiculos()) {
						if (veiculo.getId_veiculo() == carregamento.getId_veiculo()) {
							veiculo_carregamento = veiculo;
							break;
						}
					}

					if (veiculo_carregamento != null) {
						placa_trator = veiculo_carregamento.getPlaca_trator();
					}
				}

			}
			// pegar o produto
			GerenciarBancoProdutos gerenciar_produtos = new GerenciarBancoProdutos();
			CadastroProduto produto_carregamento = gerenciar_produtos.getProduto(carregamento.getId_produto());

			// codigos
			String codigo_romaneio = "";
			String codigo_nf_venda1 = "", codigo_nf_complemento = "";
			String codigo_nf_interna = "";
			// pesos

			double peso_romaneio = 0.0;
			double peso_nf_venda1 = 0.0;
			double peso_nf_interna = 0.0;

			BigDecimal valor_nf_venda1 = BigDecimal.ZERO;
			double peso_nf_complemento = 0.0;
			BigDecimal valor_nf_complemento = BigDecimal.ZERO;

			try {
				if (checkString(carregamento.getCodigo_romaneio())) {
					// procurar por romaneio
					if (checkString(carregamento.getCaminho_romaneio())) {
						ManipularRomaneios manipular = new ManipularRomaneios("");

						CadastroRomaneio romaneio = manipular
								.filtrar(new File(servidor_unidade + carregamento.getCaminho_romaneio()));
						codigo_romaneio = Integer.toString(romaneio.getNumero_romaneio());
						peso_romaneio = romaneio.getPeso_liquido();

					} else {
						codigo_romaneio = carregamento.getCodigo_romaneio();
						peso_romaneio = carregamento.getPeso_romaneio();
					}

				}
			} catch (Exception e) {
				// JOptionPane.showMessageDialog(isto, "Romaneio não Localizado");
				codigo_romaneio = carregamento.getCodigo_romaneio();
				peso_romaneio = carregamento.getPeso_romaneio();
			}

			// nf venda interna
			try {
				if (checkString(carregamento.getCodigo_nf_interna())) {
					if (carregamento.getCaminho_nf_interna().length() > 10) {
						// procurar por nf venda
						ManipularNotasFiscais manipular = new ManipularNotasFiscais("");
						CadastroNFe nota_fiscal_interna = manipular
								.filtrar(new File(servidor_unidade + carregamento.getCodigo_nf_venda1()));
						codigo_nf_interna = nota_fiscal_interna.getNfe();
						peso_nf_interna = Double.parseDouble(nota_fiscal_interna.getQuantidade());

					} else {
						codigo_nf_interna = carregamento.getCodigo_nf_interna();
						peso_nf_interna = carregamento.getPeso_nf_interna();

					}

				}
			} catch (Exception e) {
				// JOptionPane.showMessageDialog(isto, "Nota Fiscal de venda não Localizado");
				codigo_nf_venda1 = carregamento.getCodigo_nf_venda1();
				peso_nf_venda1 = carregamento.getPeso_nf_venda1();
				valor_nf_venda1 = carregamento.getValor_nf_venda1();

			}

			// nf venda 1
			try {
				if (checkString(carregamento.getCodigo_nf_venda1())) {
					if (carregamento.getCaminho_nf_venda1().length() > 10) {
						// procurar por nf venda
						ManipularNotasFiscais manipular = new ManipularNotasFiscais("");
						CadastroNFe nota_fiscal_venda = manipular
								.filtrar(new File(servidor_unidade + carregamento.getCodigo_nf_venda1()));
						codigo_nf_venda1 = nota_fiscal_venda.getNfe();
						peso_nf_venda1 = Double.parseDouble(nota_fiscal_venda.getQuantidade());
						try {
							valor_nf_venda1 = new BigDecimal(nota_fiscal_venda.getValor());
						} catch (Exception e) {
							valor_nf_venda1 = BigDecimal.ZERO;
						}

					} else {
						codigo_nf_venda1 = carregamento.getCodigo_nf_venda1();
						peso_nf_venda1 = carregamento.getPeso_nf_venda1();
						valor_nf_venda1 = carregamento.getValor_nf_venda1();

					}

				}
			} catch (Exception e) {
				// JOptionPane.showMessageDialog(isto, "Nota Fiscal de venda não Localizado");
				codigo_nf_venda1 = carregamento.getCodigo_nf_venda1();
				peso_nf_venda1 = carregamento.getPeso_nf_venda1();
				valor_nf_venda1 = carregamento.getValor_nf_venda1();

			}

			// nf complemento
			try {
				if (checkString(carregamento.getCodigo_nf_complemento())) {
					if (carregamento.getCaminho_nf_complemento().length() > 10) {
						// procurar por nf remessa
						ManipularNotasFiscais manipular = new ManipularNotasFiscais("");
						CadastroNFe nota_fiscal_complemento = manipular
								.filtrar(new File(servidor_unidade + carregamento.getCaminho_nf_complemento()));
						codigo_nf_complemento = nota_fiscal_complemento.getNfe();
						peso_nf_complemento = Double.parseDouble(nota_fiscal_complemento.getQuantidade());
						try {
							valor_nf_complemento = new BigDecimal(nota_fiscal_complemento.getValor());
						} catch (Exception e) {
							valor_nf_complemento = BigDecimal.ZERO;
						}

					} else {
						codigo_nf_complemento = carregamento.getCodigo_nf_complemento();
						peso_nf_complemento = carregamento.getPeso_nf_complemento();
						valor_nf_complemento = carregamento.getValor_nf_complemento();

					}

				}
			} catch (Exception e) {
				// JOptionPane.showMessageDialog(isto, "Nota Fiscal de remessa não Localizado");

				codigo_nf_complemento = carregamento.getCodigo_nf_complemento();
				peso_nf_complemento = carregamento.getPeso_nf_complemento();
				valor_nf_complemento = carregamento.getValor_nf_complemento();

			}

			String cor = "000000";

			if ((peso_nf_venda1 + peso_nf_complemento) >= peso_romaneio) {
				// ok
				cor = "FFFFFF";
			} else if ((peso_nf_venda1 + peso_nf_complemento) < peso_romaneio) {
				cor = "FFFF00";
			}

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(0).removeParagraph(0);
			paragraph = tableRowOne.getCell(0).addParagraph();
			criarParagrafoTabela(paragraph, contrato_destinatario.getCodigo(), false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(1).removeParagraph(0);
			paragraph = tableRowOne.getCell(1).addParagraph();
			criarParagrafoTabela(paragraph, carregamento.getData(), false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(2).removeParagraph(0);
			paragraph = tableRowOne.getCell(2).addParagraph();
			criarParagrafoTabela(paragraph, nome_cliente, false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(3).removeParagraph(0);
			paragraph = tableRowOne.getCell(3).addParagraph();
			criarParagrafoTabela(paragraph, nome_vendedor, false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(4).removeParagraph(0);
			paragraph = tableRowOne.getCell(4).addParagraph();
			criarParagrafoTabela(paragraph, nome_transportador, false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(5).removeParagraph(0);
			paragraph = tableRowOne.getCell(5).addParagraph();
			criarParagrafoTabela(paragraph, placa_trator, false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(6).removeParagraph(0);
			paragraph = tableRowOne.getCell(6).addParagraph();
			criarParagrafoTabela(paragraph, produto_carregamento.getNome_produto(), false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(7).removeParagraph(0);
			paragraph = tableRowOne.getCell(7).addParagraph();
			criarParagrafoTabela(paragraph, carregamento.getCodigo_romaneio(), false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(8).removeParagraph(0);
			paragraph = tableRowOne.getCell(8).addParagraph();
			criarParagrafoTabela(paragraph, z.format(peso_romaneio) + " Kgs", false);
			tableRowOne.getCell(8).getCTTc().addNewTcPr().addNewShd().setFill(cor);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(9).removeParagraph(0);
			paragraph = tableRowOne.getCell(9).addParagraph();
			if (carregamento.getNf_venda1_aplicavel() == 1)
				criarParagrafoTabela(paragraph, codigo_nf_venda1, false);
			else
				criarParagrafoTabela(paragraph, "Não Aplicável", false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(10).removeParagraph(0);
			paragraph = tableRowOne.getCell(10).addParagraph();
			if (carregamento.getNf_venda1_aplicavel() == 1) {
				criarParagrafoTabela(paragraph, z.format(peso_nf_venda1) + " Kgs", false);
				tableRowOne.getCell(10).getCTTc().addNewTcPr().addNewShd().setFill(cor);
			} else {
				criarParagrafoTabela(paragraph, "Não Aplicável", false);
			}

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(11).removeParagraph(0);
			paragraph = tableRowOne.getCell(11).addParagraph();
			if (carregamento.getNf_venda1_aplicavel() == 1) {
				criarParagrafoTabela(paragraph, NumberFormat.getCurrencyInstance(ptBr).format(valor_nf_venda1), false);
			} else {
				criarParagrafoTabela(paragraph, "Não Aplicável", false);
			}

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(12).removeParagraph(0);
			paragraph = tableRowOne.getCell(12).addParagraph();
			if (carregamento.getNf_complemento_aplicavel() == 1)
				criarParagrafoTabela(paragraph, codigo_nf_complemento, false);
			else
				criarParagrafoTabela(paragraph, "Não Aplicável", false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(13).removeParagraph(0);
			paragraph = tableRowOne.getCell(13).addParagraph();
			if (carregamento.getNf_complemento_aplicavel() == 1) {
				criarParagrafoTabela(paragraph, z.format(peso_nf_complemento) + " Kgs", false);
				tableRowOne.getCell(13).getCTTc().addNewTcPr().addNewShd().setFill(cor);
			} else {
				criarParagrafoTabela(paragraph, "Não Aplicável", false);

			}
			tableRowOne = table.getRow(i);
			tableRowOne.getCell(14).removeParagraph(0);
			paragraph = tableRowOne.getCell(14).addParagraph();
			if (carregamento.getNf_complemento_aplicavel() == 1)
				criarParagrafoTabela(paragraph, NumberFormat.getCurrencyInstance(ptBr).format(valor_nf_complemento),
						false);
			else {
				criarParagrafoTabela(paragraph, "Não Aplicável", false);
			}

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(15).removeParagraph(0);
			paragraph = tableRowOne.getCell(15).addParagraph();
			if (carregamento.getNf_venda1_aplicavel() == 1 && carregamento.getNf_complemento_aplicavel() == 1) {
				criarParagrafoTabela(paragraph,
						z.format(peso_romaneio - (peso_nf_complemento + peso_nf_venda1)) + " Kgs", false);
				tableRowOne.getCell(15).getCTTc().addNewTcPr().addNewShd().setFill(cor);
			} else {
				criarParagrafoTabela(paragraph, "Não Aplicável", false);

			}

			peso_total_romaneios += peso_romaneio;
			peso_total_nf_interna += peso_nf_interna;
			peso_total_nf_venda1 += peso_nf_venda1;
			peso_total_nf_complemento += peso_nf_complemento;
			peso_total_diferenca += (peso_romaneio - (peso_nf_complemento + peso_nf_venda1));

			valor_total_nf_venda1 = valor_total_nf_venda1.add(valor_nf_venda1);
			valor_total_nf_complemento = valor_total_nf_complemento.add(valor_nf_complemento);

			i++;
		}
		// transferencias negativas
		/*************************** transferencias negativas *****************///////////

		for (CadastroContrato.CadastroTransferenciaCarga transferencia : transferencias_remetentes) {

			/*
			 * codigo compradores vendedores status quantidade medida produto transgenia
			 * safra valor_produto valor_total data_contrato local_retirada
			 */

			String texto_detalhado = "";

			GerenciarBancoContratos gerencia_contratos = new GerenciarBancoContratos();
			CadastroContrato remetente = gerencia_contratos.getContrato(transferencia.getId_contrato_remetente());
			CadastroContrato destinatario = gerencia_contratos.getContrato(transferencia.getId_contrato_destinatario());
			CadastroContrato.Carregamento carga = gerencia_contratos
					.getCarregamento(transferencia.getId_carregamento_remetente());

			CadastroCliente compradores_trans[] = destinatario.getCompradores();
			CadastroCliente vendedores_trans[] = destinatario.getVendedores();

			String nome_vendedores = "";
			String nome_compradores = "";

			if (compradores_trans[0] != null) {
				if (compradores_trans[0].getTipo_pessoa() == 0) {
					// pessoa fisica
					nome_compradores = compradores_trans[0].getNome_empresarial();
				} else {
					nome_compradores = compradores_trans[0].getNome_fantaia();

				}
			}

			if (compradores_trans[1] != null) {
				if (compradores_trans[1].getTipo_pessoa() == 0) {
					// pessoa fisica
					nome_compradores = nome_compradores + ", " + compradores_trans[1].getNome_empresarial();
				} else {
					nome_compradores = nome_compradores + ", " + compradores_trans[1].getNome_fantaia();

				}
			}

			for (CadastroCliente vendedor : vendedores_trans) {
				if (vendedor != null) {
					if (vendedor.getTipo_pessoa() == 0) {
						// pessoa fisica
						nome_vendedores += vendedor.getNome_empresarial();
					} else {
						nome_vendedores += vendedor.getNome_fantaia();

					}
					nome_vendedores += " ,";

				}
			}

			double quantidade = Double.parseDouble(transferencia.getQuantidade());

			texto_detalhado = "Transferência Negativa: Transferência do volume de " + z.format(quantidade) + " kgs | "
					+ z.format(quantidade / 60) + " sacos deste contrato para o contrato ";
			texto_detalhado = texto_detalhado + destinatario.getCodigo() + "\n" + nome_compradores + " X "
					+ nome_vendedores + " " + z.format(destinatario.getQuantidade()) + " " + destinatario.getMedida()
					+ " de " + destinatario.getModelo_safra().getProduto().getNome_produto() + " "
					+ destinatario.getModelo_safra().getProduto().getTransgenia() + " da safra "
					+ destinatario.getModelo_safra().getAno_plantio() + "/"
					+ destinatario.getModelo_safra().getAno_colheita();
			texto_detalhado = texto_detalhado + "";

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(0).removeParagraph(0);
			paragraph = tableRowOne.getCell(0).addParagraph();
			criarParagrafoTabela(paragraph, transferencia.getData(), false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(1).removeParagraph(0);
			paragraph = tableRowOne.getCell(1).addParagraph();
			criarParagrafoTabela(paragraph, texto_detalhado, false);

			CTHMerge hMerge = CTHMerge.Factory.newInstance();
			hMerge.setVal(STMerge.RESTART);
			if (table.getRow(i).getCell(1).getCTTc().getTcPr() == null) {
				table.getRow(i).getCell(1).getCTTc().addNewTcPr();
				table.getRow(i).getCell(1).getCTTc().getTcPr().setHMerge(hMerge);

			} else
				table.getRow(i).getCell(1).getCTTc().getTcPr().setHMerge(hMerge);

			for (int celula = 2; celula <= 15; celula++) {
				tableRowOne = table.getRow(i);
				tableRowOne.getCell(celula).removeParagraph(0);
				paragraph = tableRowOne.getCell(celula).addParagraph();

				criarParagrafoTabela(paragraph, "", true);
				tableRowOne.getCell(celula).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");

				CTHMerge hMerge1 = CTHMerge.Factory.newInstance();
				hMerge1.setVal(STMerge.CONTINUE);
				table.getRow(i).getCell(celula).getCTTc().getTcPr().setHMerge(hMerge1);

			}

			peso_total_romaneios -= quantidade;

			i++;

		}
		/*************************** transferencias negativas *****************///////////

		// transfereicas positivas
		/*************************** transferencias positivas *****************///////////
		for (CadastroContrato.CadastroTransferenciaCarga transferencia : transferencias_destinatarios) {

			String texto_detalhado = "";

			GerenciarBancoContratos gerencia_contratos = new GerenciarBancoContratos();
			CadastroContrato remetente = gerencia_contratos.getContrato(transferencia.getId_contrato_remetente());
			CadastroContrato destinatario = gerencia_contratos.getContrato(transferencia.getId_contrato_destinatario());
			CadastroContrato.Carregamento carga = gerencia_contratos
					.getCarregamento(transferencia.getId_carregamento_remetente());

			CadastroCliente compradores_trans[] = destinatario.getCompradores();
			CadastroCliente vendedores_trans[] = destinatario.getVendedores();

			String nome_vendedores = "";
			String nome_compradores = "";

			if (compradores_trans[0] != null) {
				if (compradores_trans[0].getTipo_pessoa() == 0) {
					// pessoa fisica
					nome_compradores = compradores_trans[0].getNome_empresarial();
				} else {
					nome_compradores = compradores_trans[0].getNome_fantaia();

				}
			}

			if (compradores_trans[1] != null) {
				if (compradores_trans[1].getTipo_pessoa() == 0) {
					// pessoa fisica
					nome_compradores = nome_compradores + ", " + compradores_trans[1].getNome_empresarial();
				} else {
					nome_compradores = nome_compradores + ", " + compradores_trans[1].getNome_fantaia();

				}
			}

			for (CadastroCliente vendedor : vendedores_trans) {
				if (vendedor != null) {
					if (vendedor.getTipo_pessoa() == 0) {
						// pessoa fisica
						nome_vendedores += vendedor.getNome_empresarial();
					} else {
						nome_vendedores += vendedor.getNome_fantaia();

					}
					nome_vendedores += ",";

				}
			}

			double quantidade = Double.parseDouble(transferencia.getQuantidade());

			texto_detalhado = "Transferência Positiva: Recebimento de volume de " + z.format(quantidade) + " kgs | "
					+ z.format(quantidade / 60) + " sacos recebidos do contrato ";
			texto_detalhado = texto_detalhado + destinatario.getCodigo() + " " + nome_compradores + " X "
					+ nome_vendedores + " " + z.format(destinatario.getQuantidade()) + " " + destinatario.getMedida()
					+ " de " + destinatario.getModelo_safra().getProduto().getNome_produto() + " "
					+ destinatario.getModelo_safra().getProduto().getTransgenia() + " da safra "
					+ destinatario.getModelo_safra().getAno_plantio() + "/"
					+ destinatario.getModelo_safra().getAno_colheita();
			texto_detalhado = texto_detalhado + "";

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(0).removeParagraph(0);
			paragraph = tableRowOne.getCell(0).addParagraph();
			criarParagrafoTabela(paragraph, transferencia.getData(), false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(1).removeParagraph(0);
			paragraph = tableRowOne.getCell(1).addParagraph();
			criarParagrafoTabela(paragraph, texto_detalhado, false);

			CTHMerge hMerge = CTHMerge.Factory.newInstance();
			hMerge.setVal(STMerge.RESTART);
			if (table.getRow(i).getCell(1).getCTTc().getTcPr() == null) {
				table.getRow(i).getCell(1).getCTTc().addNewTcPr();
				table.getRow(i).getCell(1).getCTTc().getTcPr().setHMerge(hMerge);

			} else
				table.getRow(i).getCell(1).getCTTc().getTcPr().setHMerge(hMerge);

			for (int celula = 2; celula <= 15; celula++) {
				tableRowOne = table.getRow(i);
				tableRowOne.getCell(celula).removeParagraph(0);
				paragraph = tableRowOne.getCell(celula).addParagraph();

				criarParagrafoTabela(paragraph, "", true);
				tableRowOne.getCell(celula).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");

				CTHMerge hMerge1 = CTHMerge.Factory.newInstance();
				hMerge1.setVal(STMerge.CONTINUE);
				table.getRow(i).getCell(celula).getCTTc().getTcPr().setHMerge(hMerge1);

			}

			peso_total_romaneios += quantidade;

			i++;

		}

		/*************************** transferencias positivas *****************///////////

		// informacoes de total na tabela
		// somatoria dos romaneios

		// peso total romaneios
		tableRowOne = table.getRow(i);
		tableRowOne.getCell(8).removeParagraph(0);
		paragraph = tableRowOne.getCell(8).addParagraph();
		criarParagrafoTabela(paragraph, z.format(peso_total_romaneios) + " Kgs", true);

		// peso total nf venda 1
		tableRowOne = table.getRow(i);
		tableRowOne.getCell(10).removeParagraph(0);
		paragraph = tableRowOne.getCell(10).addParagraph();
		criarParagrafoTabela(paragraph, z.format(peso_total_nf_venda1) + " Kgs", true);

		// valor total nf venda 1
		tableRowOne = table.getRow(i);
		tableRowOne.getCell(11).removeParagraph(0);
		paragraph = tableRowOne.getCell(11).addParagraph();
		criarParagrafoTabela(paragraph, NumberFormat.getCurrencyInstance(ptBr).format(valor_total_nf_venda1), true);

		// peso total nf complemento
		tableRowOne = table.getRow(i);
		tableRowOne.getCell(13).removeParagraph(0);
		paragraph = tableRowOne.getCell(13).addParagraph();
		criarParagrafoTabela(paragraph, z.format(peso_total_nf_complemento) + " Kgs", true);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(14).removeParagraph(0);
		paragraph = tableRowOne.getCell(14).addParagraph();
		criarParagrafoTabela(paragraph, NumberFormat.getCurrencyInstance(ptBr).format(valor_total_nf_complemento),
				true);

		// diferenca

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(15).removeParagraph(0);
		paragraph = tableRowOne.getCell(15).addParagraph();
		criarParagrafoTabela(paragraph, z.format(peso_total_diferenca) + " Kgs", true);

		// informacoes de total
		String texto = "";

		/////////////////////////////////////////////////////////////////
		
			// totais
			texto = texto + "\nPeso Carregado: [";
			texto = texto + z.format(peso_total_romaneios) + " Kgs]";

			texto = texto + " Peso NF's Interna: [";
			if (nf_interna_ativo) {

				texto = texto + z.format(peso_total_nf_interna) + " Kgs]";
			} else {
				texto = texto + " Não Aplicável";

			}

			texto = texto + " Peso NF's Venda: [";

			if (nf_venda_ativo || nf_complemento_ativo) {
				texto = texto + (z.format(peso_total_nf_venda1 + peso_total_nf_complemento) + " Kgs]");

			} else {
				texto = texto + " Não Aplicável";

			}
			texto = texto + " Valor NF's Venda: [";

			BigDecimal soma = valor_total_nf_venda1.add(valor_total_nf_complemento);

			if (nf_venda_ativo || nf_complemento_ativo) {
				texto = texto + NumberFormat.getCurrencyInstance(ptBr).format(soma.doubleValue()) + "]";

			} else {
				texto = texto + " Não Aplicável";

			}
			texto = texto + "\n";

			texto = texto + "Peso a Carregar: [";

			texto = texto + z.format(quantidade_kg - peso_total_romaneios) + " Kgs]";

			texto = texto + " Peso NF' Interna a Emitir: [";

			if (nf_interna_ativo) {
				texto = texto + z.format(peso_total_romaneios - peso_total_nf_interna) + " Kgs]";
			} else {
				texto = texto + " Não Aplicável";

			}

			texto = texto + " Peso NF's Venda a Emitir: [";

			if (nf_venda_ativo || nf_complemento_ativo) {
				texto = texto + z.format(peso_total_romaneios - (peso_total_nf_venda1 + peso_total_nf_complemento))
						+ " Kgs]";

			} else {
				texto = texto + " Não Aplicável";

			}
			texto = texto + "\n";

			texto = texto + "\n";

			texto = texto + "[Status parcial gerado de forma automatica calculados a partir do peso total já recebido:] [" + z.format(quantidade_kg) + "] [kgs] [|] [" + z.format(quantidade_kg/60) + "] [sacos]";

			texto = texto + "\n";

			double diferenca = quantidade_kg - peso_total_romaneios;
			if (diferenca == 0) {
				texto = texto + "Carregamento Concluído";
			} else if (diferenca > 0) {
				texto = texto + "Carregamento Incompleto, [falta carregar " + z.format(diferenca) + " Kgs]";

			} else if (diferenca < 0) {
				texto = texto + "Carregamento Excedido, [excedeu " + z.format(diferenca) + " Kgs]";

			}

			texto = texto + "\n";

			String texto_nf_remessa = "";
			double diferenca_nf_remessa = peso_total_romaneios - peso_total_nf_interna;
			if (diferenca_nf_remessa == 0) {
				texto_nf_remessa = texto_nf_remessa + "[Emissão de NF's Interna Concluído]";
			} else if (diferenca_nf_remessa > 0) {
				texto_nf_remessa = texto_nf_remessa + "Emissão de NF's Interna Incompleto, [falta emitir "
						+ z.format(diferenca_nf_remessa) + " Kgs]";

			} else if (diferenca_nf_remessa < 0) {
				texto_nf_remessa = texto_nf_remessa + "Emissão de NF's Interna, [excedeu "
						+ z.format(diferenca_nf_remessa) + " Kgs]";

			}

			if (nf_interna_ativo)
				texto = texto + (texto_nf_remessa);
			else
				texto = texto + "Emissão de NF's Interna Não Aplicável";

			texto = texto + "\n";

			// status de nf de venda

			String texto_nf_venda = "";
			double diferenca_nf_venda = peso_total_romaneios - (peso_total_nf_venda1 + peso_total_nf_complemento);
			if (diferenca_nf_venda == 0) {
				texto_nf_venda = texto_nf_venda + "[Emissão de NF's de Venda Concluído]";
			} else if (diferenca_nf_venda > 0) {
				texto_nf_venda = texto_nf_venda + "Emissão de NF's de Venda Incompleto, [falta emitir "
						+ z.format(diferenca_nf_venda) + " Kgs]";

			} else if (diferenca_nf_venda < 0) {
				texto_nf_venda = texto_nf_venda + "Emissão de NF's Excedido, excedeu " + z.format(diferenca_nf_venda)
						+ " Kgs";

			}
			if (nf_venda_ativo || nf_complemento_ativo)
				texto = texto + (texto_nf_venda);
			else
				texto = texto + "Emissão de NF's de Venda Não Aplicável";

		

		substituirTexto(-1, texto);

		

	}

	public void criarTabelaRecebimentos(ArrayList<CadastroContrato.Recebimento> recebimentos,
			double soma_total_quantidade_contratos) {
		// XWPFParagraph par = document_global.createParagraph();

		NumberFormat z = NumberFormat.getNumberInstance();

		Locale ptBr = new Locale("pt", "BR");

		// criarParagrafo(1);
		// linhas x colunas

		int num_linhas_recebimentos = -1;

		if (soma_total_quantidade_contratos == 0) {

			num_linhas_recebimentos = recebimentos.size() + 1 + 1 + 1 + 1 + 1 + 1;
		} else {
			num_linhas_recebimentos = recebimentos.size() + 1 + 1 + 1 + 1 + 1;

		}
		double soma_total_romaneio = 0;
		double soma_total_nf_venda = 0;
		double soma_total_nf_remessa = 0;

		BigDecimal valor_total_nf_venda = BigDecimal.ZERO;
		BigDecimal valor_total_nf_remessa = BigDecimal.ZERO;

		XWPFTable table = document_global.createTable(num_linhas_recebimentos, 10);

		setTableAlign(table, ParagraphAlignment.CENTER);
		XWPFTableRow tableRowOne = table.getRow(0);
		tableRowOne.getCell(0).removeParagraph(0);

		XWPFParagraph paragraph = tableRowOne.getCell(0).addParagraph();

		//
		int cabecalho = 0;

		if (soma_total_quantidade_contratos != 0) {

			tableRowOne = table.getRow(cabecalho);
			tableRowOne.getCell(0).removeParagraph(0);
			paragraph = tableRowOne.getCell(0).addParagraph();

			criarParagrafoTabela(paragraph, "Quantidade Total: " + z.format(soma_total_quantidade_contratos) + " kgs | "
					+ z.format(soma_total_quantidade_contratos / 60) + " sacos", true);
			tableRowOne.getCell(0).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");

			CTHMerge hMerge = CTHMerge.Factory.newInstance();
			hMerge.setVal(STMerge.RESTART);
			table.getRow(cabecalho).getCell(0).getCTTc().getTcPr().setHMerge(hMerge);

			for (int celula = 1; celula <= 6; celula++) {
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

		} else {

			CadastroContrato contrato_deste_recebimento = new GerenciarBancoContratos()
					.getContrato(recebimentos.get(0).getId_contrato_recebimento());

			tableRowOne = table.getRow(cabecalho);
			tableRowOne.getCell(0).removeParagraph(0);
			paragraph = tableRowOne.getCell(0).addParagraph();

			double quantidade_kg = 0;
			double quantidade_sacos = 0;

			if (contrato_deste_recebimento.getMedida().equalsIgnoreCase("KG")) {
				quantidade_kg = contrato_deste_recebimento.getQuantidade();
				quantidade_sacos = quantidade_kg / 60;
			} else if (contrato_deste_recebimento.getMedida().equalsIgnoreCase("Sacos")) {
				quantidade_sacos = contrato_deste_recebimento.getQuantidade();
				quantidade_kg = quantidade_sacos * 60;
			}
			
			

			// compradores x vendedores

			// safra
			String safra = contrato_deste_recebimento.getModelo_safra().getProduto().getNome_produto() + " "
					+ contrato_deste_recebimento.getModelo_safra().getProduto().getTransgenia() + " "
					+ contrato_deste_recebimento.getModelo_safra().getAno_plantio() + "/"
					+ contrato_deste_recebimento.getModelo_safra().getAno_colheita();

			criarParagrafoTabela(paragraph,
					"CTR: " + contrato_deste_recebimento.getCodigo() + " " + safra + " Quantidade Total: "
							+ z.format(quantidade_kg) + " kgs | " + z.format(quantidade_sacos) + " sacos",
					true);
			tableRowOne.getCell(0).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");
			CTHMerge hMerge = CTHMerge.Factory.newInstance();
			hMerge.setVal(STMerge.RESTART);
			table.getRow(cabecalho).getCell(0).getCTTc().getTcPr().setHMerge(hMerge);

			for (int celula = 1; celula <= 6; celula++) {
				tableRowOne = table.getRow(cabecalho);
				tableRowOne.getCell(celula).removeParagraph(0);
				paragraph = tableRowOne.getCell(celula).addParagraph();

				criarParagrafoTabela(paragraph, "", true);
				tableRowOne.getCell(celula).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");

				CTHMerge hMerge1 = CTHMerge.Factory.newInstance();
				hMerge1.setVal(STMerge.CONTINUE);
				table.getRow(cabecalho).getCell(celula).getCTTc().getTcPr().setHMerge(hMerge1);

			}

			// linha com nome compradores x vendedores

			tableRowOne = table.getRow(cabecalho);
			tableRowOne.getCell(0).removeParagraph(0);
			paragraph = tableRowOne.getCell(0).addParagraph();

			CadastroCliente compradores[] = contrato_deste_recebimento.getCompradores();
			CadastroCliente vendedores[] = contrato_deste_recebimento.getVendedores();

			String nome_vendedores = "";
			String nome_compradores = "";

			if (compradores[0] != null) {
				if (compradores[0].getTipo_pessoa() == 0) {
					// pessoa fisica
					nome_compradores = compradores[0].getNome_empresarial();
				} else {
					nome_compradores = compradores[0].getNome_fantaia();

				}
			}
			if (compradores[1] != null) {
				if (compradores[1].getTipo_pessoa() == 0) {
					// pessoa fisica
					nome_compradores = nome_compradores + ", " + compradores[1].getNome_empresarial();
				} else {
					nome_compradores = nome_compradores + ", " + compradores[1].getNome_fantaia();

				}
			}
			if (vendedores[0] != null) {
				if (vendedores[0].getTipo_pessoa() == 0) {
					nome_vendedores = vendedores[0].getNome_empresarial();
				} else {
					nome_vendedores = vendedores[0].getNome_fantaia();
				}
			}

			if (vendedores[1] != null) {
				if (vendedores[1].getTipo_pessoa() == 0) {
					nome_vendedores = nome_vendedores + ", " + vendedores[1].getNome_empresarial();
				} else {
					nome_vendedores = nome_vendedores + ", " + vendedores[1].getNome_fantaia();
				}
			}

			criarParagrafoTabela(paragraph, nome_compradores + " X " + nome_vendedores, true);
			tableRowOne.getCell(0).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");
			hMerge = CTHMerge.Factory.newInstance();
			hMerge.setVal(STMerge.RESTART);
			table.getRow(cabecalho).getCell(0).getCTTc().getTcPr().setHMerge(hMerge);

			for (int celula = 1; celula <= 6; celula++) {
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

		}

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(0).removeParagraph(0);
		paragraph = tableRowOne.getCell(0).addParagraph();
		criarParagrafoTabela(paragraph, "CONTRATO", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(1).removeParagraph(0);
		paragraph = tableRowOne.getCell(1).addParagraph();
		criarParagrafoTabela(paragraph, "DATA", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(2).removeParagraph(0);
		paragraph = tableRowOne.getCell(2).addParagraph();
		criarParagrafoTabela(paragraph, "ROMANEIO ", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(3).removeParagraph(0);
		paragraph = tableRowOne.getCell(3).addParagraph();
		criarParagrafoTabela(paragraph, "PESO ROMANEIO: ", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(4).removeParagraph(0);
		paragraph = tableRowOne.getCell(4).addParagraph();

		criarParagrafoTabela(paragraph, "NF VENDA", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(5).removeParagraph(0);
		paragraph = tableRowOne.getCell(5).addParagraph();
		criarParagrafoTabela(paragraph, "PESO NF VENDA", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(6).removeParagraph(0);
		paragraph = tableRowOne.getCell(6).addParagraph();
		criarParagrafoTabela(paragraph, "VALOR NF VENDA", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(7).removeParagraph(0);
		paragraph = tableRowOne.getCell(7).addParagraph();
		criarParagrafoTabela(paragraph, "NF REMESSA", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(8).removeParagraph(0);
		paragraph = tableRowOne.getCell(8).addParagraph();
		criarParagrafoTabela(paragraph, "PESO NF REMESSA", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(9).removeParagraph(0);
		paragraph = tableRowOne.getCell(9).addParagraph();
		criarParagrafoTabela(paragraph, "VALOR NF REMESSA", true);

		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();

		CadastroContrato novo_contrato = gerenciar.getContrato(recebimentos.get(0).getId_contrato_recebimento());
		cabecalho++;

		int i = cabecalho;

		// checkgens
		boolean nf_remessa_ativo = false;
		boolean nf_venda_ativo = false;

		// checka se ha no minimo uma nf remessa aplicavel
		for (CadastroContrato.Recebimento recebimento : recebimentos) {
			if (recebimento.getNf_remessa_aplicavel() == 1) {
				nf_remessa_ativo = true;
				break;
			}

		}

		// checka se ha no minimo uma nf venda aplicavel
		for (CadastroContrato.Recebimento recebimento : recebimentos) {
			if (recebimento.getNf_venda_aplicavel() == 1) {
				nf_venda_ativo = true;
				break;
			}

		}
		for (CadastroContrato.Recebimento recebimento : recebimentos) {

			String cor = "000000";

			if (checkString(recebimento.getCodigo_nf_venda()) && checkString(recebimento.getCodigo_nf_remessa())) {
				// ok
				cor = "FFFFFF";

			}

			else if (!(checkString(recebimento.getCodigo_nf_venda()))
					&& !(checkString(recebimento.getCodigo_nf_remessa()))) {
				// falta duas notas
				cor = "B0C4DE";

			} else if (!(checkString(recebimento.getCodigo_nf_venda()))
					&& checkString(recebimento.getCodigo_nf_remessa())) {
				// falta apenas nf de venda
				cor = "FFFF00";

			} else if (!(checkString(recebimento.getCodigo_nf_remessa()))
					&& checkString(recebimento.getCodigo_nf_venda())) {
				// falta apenas nf remessa
				cor = "FFD700";

			}

			// contrato ao qual esse recebimento pertence
			tableRowOne = table.getRow(i);
			tableRowOne.getCell(0).removeParagraph(0);
			paragraph = tableRowOne.getCell(0).addParagraph();
			criarParagrafoTabela(paragraph, gerenciar.getContrato(recebimento.getId_contrato_recebimento()).getCodigo(),
					false);
			tableRowOne.getCell(0).getCTTc().addNewTcPr().addNewShd().setFill(cor);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(1).removeParagraph(0);
			paragraph = tableRowOne.getCell(1).addParagraph();
			criarParagrafoTabela(paragraph, recebimento.getData_recebimento(), false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(2).removeParagraph(0);
			paragraph = tableRowOne.getCell(2).addParagraph();
			criarParagrafoTabela(paragraph, recebimento.getCodigo_romaneio(), false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(3).removeParagraph(0);
			paragraph = tableRowOne.getCell(3).addParagraph();
			criarParagrafoTabela(paragraph, z.format(recebimento.getPeso_romaneio()) + " Kgs", false);
			soma_total_romaneio += recebimento.getPeso_romaneio();

			if (recebimento.getNf_venda_aplicavel() == 1) {

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(4).removeParagraph(0);
				paragraph = tableRowOne.getCell(4).addParagraph();
				criarParagrafoTabela(paragraph, recebimento.getCodigo_nf_venda(), false);
				tableRowOne.getCell(4).getCTTc().addNewTcPr().addNewShd().setFill(cor);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(5).removeParagraph(0);
				paragraph = tableRowOne.getCell(5).addParagraph();
				criarParagrafoTabela(paragraph, z.format(recebimento.getPeso_nf_venda()) + " Kgs", false);
				soma_total_nf_venda += recebimento.getPeso_nf_venda();
				tableRowOne.getCell(5).getCTTc().addNewTcPr().addNewShd().setFill(cor);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(6).removeParagraph(0);
				paragraph = tableRowOne.getCell(6).addParagraph();
				criarParagrafoTabela(paragraph,
						NumberFormat.getCurrencyInstance(ptBr).format((recebimento.getValor_nf_venda().doubleValue())),
						false);
				valor_total_nf_venda = valor_total_nf_venda.add(recebimento.getValor_nf_venda());
				tableRowOne.getCell(6).getCTTc().addNewTcPr().addNewShd().setFill(cor);

			} else {
				tableRowOne = table.getRow(i);
				tableRowOne.getCell(4).removeParagraph(0);
				paragraph = tableRowOne.getCell(4).addParagraph();
				criarParagrafoTabela(paragraph, "Não Aplicável", false);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(5).removeParagraph(0);
				paragraph = tableRowOne.getCell(5).addParagraph();
				criarParagrafoTabela(paragraph, "Não Aplicável", false);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(6).removeParagraph(0);
				paragraph = tableRowOne.getCell(6).addParagraph();
				criarParagrafoTabela(paragraph, "Não Aplicável", false);

			}

			if (recebimento.getNf_remessa_aplicavel() == 1) {

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(7).removeParagraph(0);
				paragraph = tableRowOne.getCell(7).addParagraph();
				criarParagrafoTabela(paragraph, recebimento.getCodigo_nf_remessa(), false);
				tableRowOne.getCell(7).getCTTc().addNewTcPr().addNewShd().setFill(cor);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(8).removeParagraph(0);
				paragraph = tableRowOne.getCell(8).addParagraph();
				criarParagrafoTabela(paragraph, z.format(recebimento.getPeso_nf_remessa()) + " Kgs", false);
				soma_total_nf_remessa += recebimento.getPeso_nf_remessa();
				tableRowOne.getCell(8).getCTTc().addNewTcPr().addNewShd().setFill(cor);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(9).removeParagraph(0);
				paragraph = tableRowOne.getCell(9).addParagraph();
				criarParagrafoTabela(paragraph, NumberFormat.getCurrencyInstance(ptBr)
						.format((recebimento.getValor_nf_remessa().doubleValue())), false);
				valor_total_nf_remessa = valor_total_nf_remessa.add(recebimento.getValor_nf_remessa());
				tableRowOne.getCell(9).getCTTc().addNewTcPr().addNewShd().setFill(cor);

			} else {
				tableRowOne = table.getRow(i);
				tableRowOne.getCell(7).removeParagraph(0);
				paragraph = tableRowOne.getCell(7).addParagraph();
				criarParagrafoTabela(paragraph, "Não Aplicável", false);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(8).removeParagraph(0);
				paragraph = tableRowOne.getCell(8).addParagraph();
				criarParagrafoTabela(paragraph, "Não Aplicável", false);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(9).removeParagraph(0);
				paragraph = tableRowOne.getCell(9).addParagraph();
				criarParagrafoTabela(paragraph, "Não Aplicável", false);

			}

			i++;

		}
		// somatorias
		// peso de romaneios

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(3).removeParagraph(0);
		paragraph = tableRowOne.getCell(3).addParagraph();
		criarParagrafoTabela(paragraph,
				" " + z.format(soma_total_romaneio) + " kgs / " + (z.format((soma_total_romaneio / 60))) + " sacos ",
				true);
		// pesos de nf venda

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(5).removeParagraph(0);
		paragraph = tableRowOne.getCell(5).addParagraph();
		criarParagrafoTabela(paragraph,
				" " + z.format(soma_total_nf_venda) + " kgs / " + (z.format((soma_total_nf_venda / 60))) + " sacos ",
				true);

//valor nf venda

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(6).removeParagraph(0);
		paragraph = tableRowOne.getCell(6).addParagraph();
		criarParagrafoTabela(paragraph, NumberFormat.getCurrencyInstance(ptBr).format(valor_total_nf_venda), true);

//peso nf remessa

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(8).removeParagraph(0);
		paragraph = tableRowOne.getCell(8).addParagraph();
		criarParagrafoTabela(paragraph, " " + z.format(soma_total_nf_remessa) + " kgs / "
				+ (z.format((soma_total_nf_remessa / 60))) + " sacos ", true);

//valor nf remessa

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(9).removeParagraph(0);
		paragraph = tableRowOne.getCell(9).addParagraph();
		criarParagrafoTabela(paragraph, NumberFormat.getCurrencyInstance(ptBr).format(valor_total_nf_remessa), true);

		// informacoes de total
		i += 2;
		// peso real
		tableRowOne = table.getRow(i);
		tableRowOne.getCell(0).removeParagraph(0);
		paragraph = tableRowOne.getCell(0).addParagraph();
		criarParagrafoTabela(paragraph, "Peso Total Romaneios:", false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(1).removeParagraph(0);
		paragraph = tableRowOne.getCell(1).addParagraph();
		criarParagrafoTabela(paragraph,
				" " + z.format(soma_total_romaneio) + " kgs / " + (z.format((soma_total_romaneio / 60))) + " sacos ",
				true);

		// pesos de nf venda
		tableRowOne = table.getRow(i);
		tableRowOne.getCell(2).removeParagraph(0);
		paragraph = tableRowOne.getCell(2).addParagraph();
		criarParagrafoTabela(paragraph, "Peso Total NFV's:", false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(3).removeParagraph(0);
		paragraph = tableRowOne.getCell(3).addParagraph();
		if (nf_venda_ativo) {
			criarParagrafoTabela(paragraph, " " + z.format(soma_total_nf_venda) + " kgs / "
					+ (z.format((soma_total_nf_venda / 60))) + " sacos ", true);
		} else {
			criarParagrafoTabela(paragraph, "Não Aplicável", true);
		}

		// valor nf venda

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(4).removeParagraph(0);
		paragraph = tableRowOne.getCell(4).addParagraph();
		criarParagrafoTabela(paragraph, "Valor Total NFV's:", false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(5).removeParagraph(0);
		paragraph = tableRowOne.getCell(5).addParagraph();
		if (nf_venda_ativo) {
			criarParagrafoTabela(paragraph, NumberFormat.getCurrencyInstance(ptBr).format(valor_total_nf_venda), true);
		} else {
			criarParagrafoTabela(paragraph, "Não Aplicável", true);
		}
		// peso nf remessa
		tableRowOne = table.getRow(i);
		tableRowOne.getCell(6).removeParagraph(0);
		paragraph = tableRowOne.getCell(6).addParagraph();
		criarParagrafoTabela(paragraph, "Peso Total NFR's:", false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(7).removeParagraph(0);
		paragraph = tableRowOne.getCell(7).addParagraph();
		if (nf_remessa_ativo) {
			criarParagrafoTabela(paragraph, " " + z.format(soma_total_nf_remessa) + " kgs / "
					+ (z.format((soma_total_nf_remessa / 60))) + " sacos ", true);
		} else {
			criarParagrafoTabela(paragraph, "Não Aplicável", true);
		}

		// valor nf remessa

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(8).removeParagraph(0);
		paragraph = tableRowOne.getCell(8).addParagraph();
		criarParagrafoTabela(paragraph, "Valor Total NFR's:", false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(9).removeParagraph(0);
		paragraph = tableRowOne.getCell(9).addParagraph();
		if (nf_remessa_ativo) {
			criarParagrafoTabela(paragraph, NumberFormat.getCurrencyInstance(ptBr).format(valor_total_nf_remessa),
					true);
		} else {
			criarParagrafoTabela(paragraph, "Não Aplicável", true);
		}
		i++;

		// total a receber em kgs

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(0).removeParagraph(0);
		paragraph = tableRowOne.getCell(0).addParagraph();
		criarParagrafoTabela(paragraph, "Total a Receber:", false);

		double quantidade_total_sacos = 0;
		double quantidade_total_kgs = 0;

		if (novo_contrato.getMedida().equalsIgnoreCase("KG")) {
			quantidade_total_kgs = novo_contrato.getQuantidade();
			quantidade_total_sacos = quantidade_total_kgs / 60;
		} else if (novo_contrato.getMedida().equalsIgnoreCase("Sacos")) {
			quantidade_total_sacos = novo_contrato.getQuantidade();
			quantidade_total_kgs = quantidade_total_sacos * 60;
		}

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(1).removeParagraph(0);
		paragraph = tableRowOne.getCell(1).addParagraph();
		if (soma_total_quantidade_contratos != 0) {
			criarParagrafoTabela(paragraph,
					" " + z.format(soma_total_quantidade_contratos - soma_total_romaneio) + " kgs / "
							+ (z.format(((soma_total_quantidade_contratos - soma_total_romaneio) / 60))) + " sacos ",
					true);
		} else {
			criarParagrafoTabela(paragraph, " " + z.format(quantidade_total_kgs - soma_total_romaneio) + " kgs / "
					+ (z.format(((quantidade_total_kgs - soma_total_romaneio) / 60))) + " sacos ", true);
		}

		// pesos de nf
		tableRowOne = table.getRow(i);
		tableRowOne.getCell(2).removeParagraph(0);
		paragraph = tableRowOne.getCell(2).addParagraph();
		criarParagrafoTabela(paragraph, "Total a emitir NFV's:", false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(3).removeParagraph(0);
		paragraph = tableRowOne.getCell(3).addParagraph();
		if (nf_venda_ativo) {
			if (soma_total_quantidade_contratos != 0) {
				criarParagrafoTabela(paragraph,
						" " + z.format(soma_total_quantidade_contratos - soma_total_nf_venda) + " kgs / "
								+ (z.format(((soma_total_quantidade_contratos - soma_total_nf_venda) / 60)))
								+ " sacos ",
						true);
			} else {
				criarParagrafoTabela(paragraph, " " + z.format(quantidade_total_kgs - soma_total_nf_venda) + " kgs / "
						+ (z.format(((quantidade_total_kgs - soma_total_nf_venda) / 60))) + " sacos ", true);
			}
		} else {
			criarParagrafoTabela(paragraph, "Não Aplicável", true);

		}

		// pesos de nf remessa
		tableRowOne = table.getRow(i);
		tableRowOne.getCell(6).removeParagraph(0);
		paragraph = tableRowOne.getCell(6).addParagraph();
		criarParagrafoTabela(paragraph, "Total a emitir NFR's:", false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(7).removeParagraph(0);
		paragraph = tableRowOne.getCell(7).addParagraph();
		if (nf_remessa_ativo) {
			if (soma_total_quantidade_contratos != 0) {
				criarParagrafoTabela(paragraph,
						" " + z.format(soma_total_quantidade_contratos - soma_total_nf_remessa) + " kgs / "
								+ (z.format(((soma_total_quantidade_contratos - soma_total_nf_remessa) / 60)))
								+ " sacos ",
						true);
			} else {
				criarParagrafoTabela(paragraph, " " + z.format(quantidade_total_kgs - soma_total_nf_remessa) + " kgs / "
						+ (z.format(((quantidade_total_kgs - soma_total_nf_remessa) / 60))) + " sacos ", true);
			}
		} else {
			criarParagrafoTabela(paragraph, "Não Aplicável", true);

		}
		i++;

		// texto do status

		String texto = "Status Gerado automaticamente: \n";
		double diferenca = quantidade_total_kgs - soma_total_romaneio;
		if (diferenca == 0) {
			texto = texto + "Recebimento Concluído\n";
		} else if (diferenca > 0) {
			texto = texto + "Recebimento Incompleto, falta receber " + z.format(diferenca) + " Kgs\n";

		} else if (diferenca < 0) {
			texto = texto + "Recebimento Excedido, excedeu " + z.format(diferenca) + " Kgs\n";

		}

		String texto_nf_venda = "";
		if (nf_venda_ativo) {
			double diferenca_nf_venda = quantidade_total_kgs - soma_total_nf_venda;
			if (diferenca_nf_venda == 0) {
				texto_nf_venda = texto_nf_venda + "Emissão de NF's de Venda Concluído\n";
			} else if (diferenca_nf_venda > 0) {
				texto_nf_venda = texto_nf_venda + "Emissão de NF's de Venda Incompleto, falta emitir "
						+ z.format(diferenca_nf_venda) + " Kgs\n";

			} else if (diferenca_nf_venda < 0) {
				texto_nf_venda = texto_nf_venda + "Emissão de NF's Venda Excedido, excedeu "
						+ z.format(diferenca_nf_venda) + " Kgs\n";

			}
		} else {
			texto_nf_venda = "Emissão de NF's Venda Não Aplicável";
		}

		String texto_nf_remessa = "";
		if (nf_remessa_ativo) {
			double diferenca_nf_remessa = quantidade_total_kgs - soma_total_nf_remessa;
			if (diferenca_nf_remessa == 0) {
				texto_nf_remessa = texto_nf_remessa + "Emissão de NF's de Remessa Concluído\n";
			} else if (diferenca_nf_remessa > 0) {
				texto_nf_remessa = texto_nf_remessa + "Emissão de NF's de Remessa Incompleto, falta emitir "
						+ z.format(diferenca_nf_remessa) + " Kgs\n";

			} else if (diferenca_nf_remessa < 0) {
				texto_nf_remessa = texto_nf_remessa + "Emissão de NF's Remessa Excedido, excedeu "
						+ z.format(diferenca_nf_remessa) + " Kgs\n";

			}
		} else {
			texto_nf_remessa = "Emissão de NF's Remessa Não Aplicável";
		}

		substituirTexto(texto + texto_nf_venda + texto_nf_remessa);

	}

	public void setTableAlign(XWPFTable table, ParagraphAlignment align) {
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
		run.setFontSize(9);

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

	public void adicionarData() {
		// data contrato
		String data_extenso = "";

		SimpleDateFormat formato_data = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date data_formatada = formato_data.parse(novo_contrato.getData_contrato());
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

		substituirTexto("Guarda-Mor MG " + data_extenso);
	}

	public boolean criarArquivos(String nome_arquivo, String caminh_completo_salvar_no_hd,
			String caminho_completo_salvar_no_banco_dados, String caminho_completo_diretorio_arquivo) {

		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream(caminh_completo_salvar_no_hd + ".docx");

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
			if (converter_pdf.word_pdf_file(caminh_completo_salvar_no_hd)) {
				System.out.println("Arquivo pdf convertido e salvo!");

				System.out.println("caminho para salvar na nuvem: " + caminh_completo_salvar_no_hd);
				novo_contrato.setCaminho_arquivo(caminho_completo_salvar_no_banco_dados + ".pdf");
				novo_contrato.setCaminho_diretorio_contrato(caminho_completo_diretorio_arquivo);
				// salvar no drobox
				Nuvem nuvem = new Nuvem();
				nuvem.abrir();
				nuvem.testar();

				boolean retorno = nuvem.carregar(caminh_completo_salvar_no_hd + ".pdf", nome_arquivo + ".pdf");
				if (retorno) {
					System.out.println("Arquivo salvo na nuvem");
					novo_contrato.setNome_arquivo(nome_arquivo + ".pdf");
				}
				// System.out.println("link: " + nuvem.getUrlArquivo("/contratos));
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

	public boolean checkString(String txt) {
		return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ") && txt.length() > 0;
	}
}
