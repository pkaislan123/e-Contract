package main.java.relatoria;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.JOptionPane;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;


import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import main.java.cadastros.CadastroAviso;
import main.java.cadastros.CadastroBaseArquivos;
import main.java.cadastros.CadastroBaseDados;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroContrato.CadastroPagamento;
import main.java.cadastros.CadastroContrato.CadastroPagamentoContratual;
import main.java.cadastros.CadastroContrato.CadastroTransferenciaCarga;
import main.java.cadastros.CadastroContrato.CadastroTransferenciaPagamentoContratual;
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

public class RelatorioContratoIndividualExcel {

	private CadastroModelo modelo;
	private String path;

	CadastroContrato contrato_local;
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

	public RelatorioContratoIndividualExcel(CadastroContrato contrato) {
		getDadosGlobais();
		contrato_local = contrato;
		servidor_unidade = configs_globais.getServidorUnidade();

	}

	public void gerar() {
		HSSFWorkbook workbook = new HSSFWorkbook();

		int rownum = 0;
		int cellnum = 0;
		Cell cell;
		Row row;

		CadastroCliente compradores[] = contrato_local.getCompradores();
		CadastroCliente vendedores[] = contrato_local.getVendedores();

		String nome_compradores;
		String nome_vendedores;
		if (compradores[0].getTipo_pessoa() == 0) {
			nome_compradores = compradores[0].getNome_empresarial();
		} else {
			nome_compradores = compradores[0].getNome_fantaia();
		}

		if (compradores[1] != null) {
			if (compradores[1].getTipo_pessoa() == 0) {
				nome_compradores = nome_compradores + ", " + compradores[1].getNome_empresarial();
			} else {
				nome_compradores = nome_compradores + ", " + compradores[1].getNome_fantaia();
			}
		}

		if (vendedores[0].getTipo_pessoa() == 0) {
			nome_vendedores = vendedores[0].getNome_empresarial();
		} else {
			nome_vendedores = vendedores[0].getNome_fantaia();
		}

		if (vendedores[1] != null) {
			if (vendedores[1].getTipo_pessoa() == 0) {
				nome_vendedores = nome_vendedores + ", " + vendedores[1].getNome_empresarial();
			} else {
				nome_vendedores = nome_vendedores + ", " + vendedores[1].getNome_fantaia();
			}
		}

		// Configurando estilos de células (Cores, alinhamento, formatação, etc..)
		HSSFDataFormat numberFormat = workbook.createDataFormat();

		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		// headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		// celula para texto alinhado ao centro
		CellStyle textStyle = workbook.createCellStyle();
		textStyle.setAlignment(HorizontalAlignment.CENTER);
		textStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		HSSFFont newFontforText = workbook.createFont();
		newFontforText.setBold(false);
		newFontforText.setColor(IndexedColors.BLACK.getIndex());
		newFontforText.setFontName("Calibri");
		newFontforText.setItalic(false);
		newFontforText.setFontHeight((short) (11 * 20));
		textStyle.setFont(newFontforText);
		
		// celula para texto alinhado ao esquerda
		CellStyle textStyleAlinhadoEsquerda = workbook.createCellStyle();
		textStyleAlinhadoEsquerda.setAlignment(HorizontalAlignment.LEFT);
		HSSFFont newFontforTextAlinhaEsquerda = workbook.createFont();
		newFontforTextAlinhaEsquerda.setBold(false);
		newFontforTextAlinhaEsquerda.setColor(IndexedColors.BLACK.getIndex());
		newFontforTextAlinhaEsquerda.setFontName("Calibri");
		newFontforTextAlinhaEsquerda.setItalic(false);
		newFontforTextAlinhaEsquerda.setFontHeight((short) (11 * 20));
		
		textStyleAlinhadoEsquerda.setFont(newFontforTextAlinhaEsquerda);

		//estilo para celula texto alinhado a esquerda
		CellStyle negrito_esquerda = workbook.createCellStyle();
		// textStyle.setAlignment(HorizontalAlignment.CENTER);
		negrito_esquerda.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		negrito_esquerda.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		negrito_esquerda.setAlignment(HorizontalAlignment.LEFT);
		negrito_esquerda.setVerticalAlignment(VerticalAlignment.CENTER);

		HSSFFont newFontNegritaEsquerda = workbook.createFont();
		newFontNegritaEsquerda.setBold(true);
		newFontNegritaEsquerda.setColor(IndexedColors.BLACK.getIndex());
		newFontNegritaEsquerda.setFontName("Arial");
		newFontNegritaEsquerda.setItalic(true);
		newFontNegritaEsquerda.setFontHeight((short) (11 * 20));

		negrito_esquerda.setFont(newFontNegritaEsquerda);
		
		// celula para numero alinhado ao centro
		CellStyle numberStyle = workbook.createCellStyle();
		numberStyle.setDataFormat(numberFormat.getFormat("#,##0.00"));
		numberStyle.setAlignment(HorizontalAlignment.CENTER);
		numberStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		// estilo para celula do tipo numero alinhado ao centro
		CellStyle valorStyle = workbook.createCellStyle();
		valorStyle.setDataFormat(numberFormat.getFormat("R$ #,##0.00"));
		valorStyle.setAlignment(HorizontalAlignment.CENTER);
		valorStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		// estilo para cabecalho fundo laranja
		CellStyle celula_fundo_laranja = workbook.createCellStyle();
		celula_fundo_laranja.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		celula_fundo_laranja.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
		celula_fundo_laranja.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_laranja.setVerticalAlignment(VerticalAlignment.CENTER);

		HSSFFont newFont = workbook.createFont();
		newFont.setBold(true);
		newFont.setColor(IndexedColors.BLACK.getIndex());
		newFont.setFontName("Calibri");
		newFont.setItalic(false);
		newFont.setFontHeight((short) (11 * 20));

		celula_fundo_laranja.setFont(newFont);

		// celula fundo branco em negritoasd
		CellStyle negrito = workbook.createCellStyle();
		negrito.setDataFormat(numberFormat.getFormat("R$ #,##0.00"));
		negrito.setAlignment(HorizontalAlignment.CENTER);
		negrito.setVerticalAlignment(VerticalAlignment.CENTER);

		HSSFFont newFontNegrito = workbook.createFont();
		newFontNegrito.setBold(true);
		newFontNegrito.setColor(IndexedColors.BLACK.getIndex());
		newFontNegrito.setFontName("Arial");
		newFontNegrito.setItalic(false);
		newFontNegrito.setFontHeight((short) (11 * 18));

		negrito.setFont(newFontNegrito);

		// celula fundo branco em vermelho
		CellStyle aviso = workbook.createCellStyle();
		aviso.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		aviso.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		aviso.setAlignment(HorizontalAlignment.LEFT);
		aviso.setVerticalAlignment(VerticalAlignment.CENTER);

		HSSFFont newFontVermelha = workbook.createFont();
		newFontVermelha.setBold(false);
		newFontVermelha.setColor(IndexedColors.RED.getIndex());
		newFontVermelha.setFontName("Arial");
		newFontVermelha.setItalic(true);
		newFontVermelha.setFontHeight((short) (11 * 20));

		aviso.setFont(newFontVermelha);

		// celula_number_amarelo_texto_preto
		// estilo para cabecalho fundo laranja
		CellStyle celula_number_amarelo_texto_preto = workbook.createCellStyle();
		celula_number_amarelo_texto_preto.setDataFormat(numberFormat.getFormat("#,##0.00"));
		celula_number_amarelo_texto_preto.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		celula_number_amarelo_texto_preto.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		celula_number_amarelo_texto_preto.setAlignment(HorizontalAlignment.CENTER);
		celula_number_amarelo_texto_preto.setVerticalAlignment(VerticalAlignment.CENTER);

		HSSFFont newFont_blabk = workbook.createFont();
		newFont_blabk.setBold(true);
		newFont_blabk.setColor(IndexedColors.BLACK.getIndex());
		newFont_blabk.setFontName("Calibri");
		newFont_blabk.setItalic(false);
		newFont_blabk.setFontHeight((short) (11 * 20));

		celula_number_amarelo_texto_preto.setFont(newFont_blabk);

		
		// celula de aviso
		CellStyle textStyleAlinhadoEsquerdaAviso = workbook.createCellStyle();
		textStyleAlinhadoEsquerdaAviso.setAlignment(HorizontalAlignment.LEFT);
		HSSFFont newFontforTextAlinhaEsquerdaAviso = workbook.createFont();
		newFontforTextAlinhaEsquerdaAviso.setBold(false);
		newFontforTextAlinhaEsquerdaAviso.setColor(IndexedColors.RED.getIndex());
		newFontforTextAlinhaEsquerdaAviso.setFontName("Calibri");
		newFontforTextAlinhaEsquerdaAviso.setItalic(false);
		newFontforTextAlinhaEsquerdaAviso.setFontHeight((short) (11 * 20));
		textStyleAlinhadoEsquerdaAviso.setFont(newFontforTextAlinhaEsquerdaAviso);

		// celula de aviso negrito
		CellStyle textStyleAlinhadoEsquerdaNegrito = workbook.createCellStyle();
		textStyleAlinhadoEsquerdaNegrito.setAlignment(HorizontalAlignment.LEFT);
		HSSFFont newFontforTextAlinhaEsquerdaNegrito = workbook.createFont();
		newFontforTextAlinhaEsquerdaNegrito.setBold(true);
		newFontforTextAlinhaEsquerdaNegrito.setColor(IndexedColors.BLACK.getIndex());
		newFontforTextAlinhaEsquerdaNegrito.setFontName("Calibri");
		newFontforTextAlinhaEsquerdaNegrito.setItalic(false);
		newFontforTextAlinhaEsquerdaNegrito.setFontHeight((short) (11 * 20));
		textStyleAlinhadoEsquerdaNegrito.setFont(newFontforTextAlinhaEsquerdaNegrito);
		
		// estilo para cabecalho fundo laranja
		CellStyle celula_fundo_laranja_texto_branco = workbook.createCellStyle();
		celula_fundo_laranja_texto_branco.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		celula_fundo_laranja_texto_branco.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
		celula_fundo_laranja_texto_branco.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_laranja_texto_branco.setVerticalAlignment(VerticalAlignment.CENTER);

		HSSFFont newFont_branca = workbook.createFont();
		newFont_branca.setBold(true);
		newFont_branca.setColor(IndexedColors.WHITE.getIndex());
		newFont_branca.setFontName("Calibri");
		newFont_branca.setItalic(false);
		newFont_branca.setFontHeight((short) (11 * 20));

		celula_fundo_laranja_texto_branco.setFont(newFont_branca);

		// estilo para cabecalho fundo verde
		CellStyle celula_fundo_verde_texto_branco = workbook.createCellStyle();
		celula_fundo_verde_texto_branco.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		celula_fundo_verde_texto_branco.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		celula_fundo_verde_texto_branco.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_verde_texto_branco.setVerticalAlignment(VerticalAlignment.CENTER);

		celula_fundo_verde_texto_branco.setFont(newFont_branca);

		// estilo para cabecalho fundo azul
		CellStyle celula_fundo_azul_texto_branco = workbook.createCellStyle();
		celula_fundo_azul_texto_branco.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		celula_fundo_azul_texto_branco.setFillForegroundColor(IndexedColors.BLUE.getIndex());
		celula_fundo_azul_texto_branco.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_azul_texto_branco.setVerticalAlignment(VerticalAlignment.CENTER);

		celula_fundo_azul_texto_branco.setFont(newFont_branca);

		double quantidade_total_kgs_recebido = 0;
		double quantidade_total_kgs_nf_venda = 0;
		double quantidade_kg = 0;
		double quantidade_sacos = 0;
		double quantidade_total_kgs_nf_remessa = 0;

		
		BigDecimal valor_total_nf_venda =  BigDecimal.ZERO;
		BigDecimal valor_total_nf_remessa =  BigDecimal.ZERO;
		
		Locale ptBr = new Locale("pt", "BR");
		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();

		if (interno) {
			if (incluir_sub_contratos) {
				ArrayList<CadastroContrato> lista_sub_contratos = new GerenciarBancoContratos()
						.getSubContratos(contrato_local.getId());

				if (lista_sub_contratos.size() > 0) {

					HSSFSheet sheet5 = workbook.createSheet("Dados Iniciais");

					// Definindo alguns padroes de layout
					sheet5.setDefaultColumnWidth(15);
					sheet5.setDefaultRowHeight((short) 400);

					// Configurando as informacoes
					row = sheet5.createRow(rownum++);
					cell = row.createCell(cellnum);
					cell.setCellStyle(celula_fundo_azul_texto_branco);
					cell.setCellValue("Contrato Original");
					sheet5.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

					rownum += 1;
					cellnum = 0;

					row = sheet5.createRow(rownum++);
					cell = row.createCell(cellnum);
					cell.setCellStyle(celula_fundo_laranja);
					cell.setCellValue(nome_compradores.toUpperCase() + " X " + nome_vendedores + " CTR "
							+ contrato_local.getCodigo() + " IE.: " + vendedores[0].getIe());
					sheet5.addMergedRegion(new CellRangeAddress(2, 2, cellnum, 5));

					// linha quantidade, safra, sacos, etc
					row = sheet5.createRow(rownum++);
					cellnum = 0;

					cell = row.createCell(cellnum++);
					cell.setCellStyle(celula_fundo_laranja);

					if (contrato_local.getMedida().equalsIgnoreCase("KG")) {
						quantidade_kg = contrato_local.getQuantidade();
						quantidade_sacos = quantidade_kg / 60;
					} else if (contrato_local.getMedida().equalsIgnoreCase("Sacos")) {
						quantidade_sacos = contrato_local.getQuantidade();
						quantidade_kg = quantidade_sacos * 60;
					}

					cell.setCellValue(
							"Quantidade: " + quantidade_kg + " KGS | " + quantidade_sacos + " Sacos no valor de "
									+ NumberFormat.getCurrencyInstance(ptBr).format(contrato_local.getValor_produto())
									+ " por " + contrato_local.getMedida() + " no valor total de: "
									+ NumberFormat.getCurrencyInstance(ptBr).format(contrato_local.getValor_a_pagar()));
					// cell.setCellValue("Quantidade: " + quantidade_kg + " KGS | " +
					// quantidade_sacos + " Sacos" );
					sheet5.addMergedRegion(new CellRangeAddress(3, 3, 0, 5));

					row = sheet5.createRow(rownum++);
					cellnum = 0;

					cell = row.createCell(cellnum++);
					cell.setCellStyle(celula_fundo_laranja);
					cell.setCellValue(contrato_local.getModelo_safra().getProduto().getNome_produto() + " "
							+ contrato_local.getModelo_safra().getProduto().getTransgenia() + " "
							+ contrato_local.getModelo_safra().getAno_plantio() + "/"
							+ contrato_local.getModelo_safra().getAno_colheita());
					sheet5.addMergedRegion(new CellRangeAddress(4, 4, 0, 5));

					rownum += 1;
					cellnum = 0;
					// Configurando as informacoes
					row = sheet5.createRow(rownum++);
					cell = row.createCell(cellnum);
					cell.setCellStyle(celula_fundo_azul_texto_branco);
					cell.setCellValue("Sub-Contratos");
					sheet5.addMergedRegion(new CellRangeAddress(rownum - 1, rownum - 1, 0, 5));

					rownum += 1;

					for (CadastroContrato sub : lista_sub_contratos) {
						cellnum = 0;

						CadastroCliente compradores_sub[] = sub.getCompradores();
						CadastroCliente vendedores_sub[] = sub.getVendedores();

						String nome_compradores_sub;
						String nome_vendedores_sub;
						if (compradores_sub[0].getTipo_pessoa() == 0) {
							nome_compradores_sub = compradores_sub[0].getNome_empresarial();
						} else {
							nome_compradores_sub = compradores_sub[0].getNome_fantaia();
						}

						if (compradores_sub[1] != null) {
							if (compradores[1].getTipo_pessoa() == 0) {
								nome_compradores_sub = nome_compradores_sub + ", "
										+ compradores_sub[1].getNome_empresarial();
							} else {
								nome_compradores_sub = nome_compradores_sub + ", "
										+ compradores_sub[1].getNome_fantaia();
							}
						}

						if (vendedores_sub[0].getTipo_pessoa() == 0) {
							nome_vendedores_sub = vendedores_sub[0].getNome_empresarial();
						} else {
							nome_vendedores_sub = vendedores_sub[0].getNome_fantaia();
						}

						if (vendedores_sub[1] != null) {
							if (vendedores_sub[1].getTipo_pessoa() == 0) {
								nome_vendedores_sub = nome_vendedores_sub + ", "
										+ vendedores_sub[1].getNome_empresarial();
							} else {
								nome_vendedores_sub = nome_vendedores_sub + ", " + vendedores_sub[1].getNome_fantaia();
							}
						}

						// Configurando as informacoes
						row = sheet5.createRow(rownum++);

						cell = row.createCell(cellnum);
						cell.setCellStyle(textStyle);
						cell.setCellValue(nome_compradores_sub.toUpperCase() + " X " + nome_vendedores_sub + " CTR "
								+ sub.getCodigo() + " IE.: " + vendedores_sub[0].getIe());
						sheet5.addMergedRegion(new CellRangeAddress(rownum - 1, rownum - 1, 0, 5));

						// linha quantidade, safra, sacos, etc
						row = sheet5.createRow(rownum++);
						cellnum = 0;

						cell = row.createCell(cellnum++);
						cell.setCellStyle(textStyle);

						if (sub.getMedida().equalsIgnoreCase("KG")) {
							quantidade_kg = sub.getQuantidade();
							quantidade_sacos = quantidade_kg / 60;
						} else if (sub.getMedida().equalsIgnoreCase("Sacos")) {
							quantidade_sacos = sub.getQuantidade();
							quantidade_kg = quantidade_sacos * 60;
						}

						cell.setCellValue(
								"Quantidade: " + quantidade_kg + " KGS | " + quantidade_sacos + " Sacos no valor de "
										+ NumberFormat.getCurrencyInstance(ptBr).format(sub.getValor_produto())
										+ " por " + sub.getMedida() + " no valor total de: "
										+ NumberFormat.getCurrencyInstance(ptBr).format(sub.getValor_a_pagar()));
						// cell.setCellValue("Quantidade: " + quantidade_kg + " KGS | " +
						// quantidade_sacos + " Sacos" );
						sheet5.addMergedRegion(new CellRangeAddress(rownum - 1, rownum - 1, 0, 5));

						row = sheet5.createRow(rownum++);
						cellnum = 0;

						cell = row.createCell(cellnum++);
						cell.setCellStyle(textStyle);
						cell.setCellValue(sub.getModelo_safra().getProduto().getNome_produto() + " "
								+ sub.getModelo_safra().getProduto().getTransgenia() + " "
								+ sub.getModelo_safra().getAno_plantio() + "/"
								+ sub.getModelo_safra().getAno_colheita());
						sheet5.addMergedRegion(new CellRangeAddress(rownum - 1, rownum - 1, 0, 5));

						rownum += 1;

					}

					cellnum = 0;

					if (incluir_ganhos_potenciais) {

						sheet5.setDefaultColumnWidth(20);
						sheet5.setDefaultRowHeight((short) 400);

						// Configurando as informacoes
						row = sheet5.createRow(rownum++);
						cell = row.createCell(cellnum);
						cell.setCellStyle(celula_fundo_azul_texto_branco);
						cell.setCellValue("Ganhos Potenciais");
						sheet5.addMergedRegion(new CellRangeAddress(rownum - 1, rownum - 1, 0, 5));

						// criar tabela de ganhos potenciais
						double valor_total_contrato_original = Double
								.parseDouble(contrato_local.getValor_a_pagar().toPlainString());

						String string_valor_total_contrato_original = NumberFormat.getCurrencyInstance(ptBr)
								.format(valor_total_contrato_original);

						double valor_total_sub_contratos = 0;

						for (CadastroContrato sub : lista_sub_contratos) {

							double valor_local = Double.parseDouble(sub.getValor_a_pagar().toPlainString());
							valor_total_sub_contratos += valor_local;
						}

						String string_valor_total_sub_contratos = NumberFormat.getCurrencyInstance(ptBr)
								.format(valor_total_sub_contratos);

						double valor_total_diferenca_contratos = valor_total_contrato_original
								- valor_total_sub_contratos;

						String string_valor_total_diferenca_contratos = NumberFormat.getCurrencyInstance(ptBr)
								.format(valor_total_diferenca_contratos);

						double valor_total_comissoes = 0;

						if (contrato_local.getComissao() == 1) {
							double valor_local = Double.parseDouble(contrato_local.getValor_comissao().toPlainString());
							valor_total_comissoes += valor_local;
						}

						for (CadastroContrato sub : lista_sub_contratos) {

							if (sub.getComissao() == 1) {
								double valor_local = Double.parseDouble(sub.getValor_comissao().toPlainString());
								valor_total_comissoes += valor_local;
							}

						}

						String string_valor_total_comissoes = NumberFormat.getCurrencyInstance(ptBr)
								.format(valor_total_comissoes);

						double valor_total_ganhos_potenciais = valor_total_diferenca_contratos + valor_total_comissoes;

						String string_valor_total_ganhos_potenciais = NumberFormat.getCurrencyInstance(ptBr)
								.format(valor_total_ganhos_potenciais);

						NumberFormat z = NumberFormat.getNumberInstance();
						cellnum = 0;
						row = sheet5.createRow(rownum);

						cell = row.createCell(cellnum++);
						cell.setCellStyle(celula_fundo_laranja_texto_branco);
						cell.setCellValue("      VALOR CONTRATOS       ");

						cell = row.createCell(cellnum++);
						cell.setCellStyle(celula_fundo_laranja_texto_branco);
						cell.setCellValue("      VALOR SUB-CONTRATOS       ");

						cell = row.createCell(cellnum++);
						cell.setCellStyle(celula_fundo_laranja_texto_branco);
						cell.setCellValue("      VALOR DIFERENÇA      ");

						cell = row.createCell(cellnum++);
						cell.setCellStyle(celula_fundo_laranja_texto_branco);
						cell.setCellValue("     VALOR COMISSÕES      ");

						cell = row.createCell(cellnum++);
						cell.setCellStyle(celula_fundo_laranja_texto_branco);
						cell.setCellValue("    POTENCIAIS GANHOS     ");

						row = sheet5.createRow(rownum += 1);
						cellnum = 0;
						cell = row.createCell(cellnum++);
						cell.setCellStyle(textStyle);
						cell.setCellValue(string_valor_total_contrato_original);

						cell = row.createCell(cellnum++);
						cell.setCellStyle(textStyle);
						cell.setCellValue(string_valor_total_sub_contratos);

						cell = row.createCell(cellnum++);
						cell.setCellStyle(textStyle);
						cell.setCellValue(string_valor_total_diferenca_contratos);

						cell = row.createCell(cellnum++);
						cell.setCellStyle(textStyle);
						cell.setCellValue(string_valor_total_comissoes);

						cell = row.createCell(cellnum++);
						cell.setCellStyle(textStyle);
						cell.setCellValue(string_valor_total_ganhos_potenciais);

						sheet5.setAutoFilter(CellRangeAddress.valueOf("A" + (rownum) + ":E" + (rownum)));

					}

				}
			}

		}

		rownum = 0;
		cellnum = 0;
		// incluir_recebimentos
		if (incluir_recebimentos) {

			HSSFSheet sheet = workbook.createSheet("Recebimentos");

			// Definindo alguns padroes de layout
			sheet.setDefaultColumnWidth(15);
			sheet.setDefaultRowHeight((short) 400);

			// Configurando as informacoes
			row = sheet.createRow(rownum++);

			cell = row.createCell(cellnum);
			cell.setCellStyle(celula_fundo_laranja);
			cell.setCellValue(nome_compradores.toUpperCase() + " X " + nome_vendedores + " CTR "
					+ contrato_local.getCodigo() + " IE.: " + vendedores[0].getIe());
			sheet.addMergedRegion(new CellRangeAddress(0, 0, cellnum, 5));

			// linha quantidade, safra, sacos, etc
			row = sheet.createRow(rownum++);
			cellnum = 0;

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja);

			if (contrato_local.getMedida().equalsIgnoreCase("KG")) {
				quantidade_kg = contrato_local.getQuantidade();
				quantidade_sacos = quantidade_kg / 60;
			} else if (contrato_local.getMedida().equalsIgnoreCase("Sacos")) {
				quantidade_sacos = contrato_local.getQuantidade();
				quantidade_kg = quantidade_sacos * 60;
			}

			cell.setCellValue("Quantidade: " + quantidade_kg + " KGS | " + quantidade_sacos + " Sacos no valor de "
					+ NumberFormat.getCurrencyInstance(ptBr).format(contrato_local.getValor_produto()) + " por "
					+ contrato_local.getMedida() + " no valor total de: "
					+ NumberFormat.getCurrencyInstance(ptBr).format(contrato_local.getValor_a_pagar()));
			// cell.setCellValue("Quantidade: " + quantidade_kg + " KGS | " +
			// quantidade_sacos + " Sacos" );
			sheet.addMergedRegion(new CellRangeAddress(rownum - 1, rownum - 1, cellnum - 1, 5));

			row = sheet.createRow(rownum++);
			cellnum = 0;

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja);
			cell.setCellValue(contrato_local.getModelo_safra().getProduto().getNome_produto() + " "
					+ contrato_local.getModelo_safra().getProduto().getTransgenia() + " "
					+ contrato_local.getModelo_safra().getAno_plantio() + "/"
					+ contrato_local.getModelo_safra().getAno_colheita());
			sheet.addMergedRegion(new CellRangeAddress(rownum - 1, rownum - 1, cellnum - 1, 5));

			// Configurando Header
			row = sheet.createRow(rownum++);
			cellnum = 0;
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja_texto_branco);
			cell.setCellValue("DATA");

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja_texto_branco);
			cell.setCellValue("Romaneio".toUpperCase());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja_texto_branco);
			cell.setCellValue("Peso Romaneio".toUpperCase());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja_texto_branco);
			cell.setCellValue("NF Venda".toUpperCase());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja_texto_branco);
			cell.setCellValue("Peso NF Venda".toUpperCase());
			
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja_texto_branco);
			cell.setCellValue("Valor NF Venda".toUpperCase());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja_texto_branco);
			cell.setCellValue("NF Remessa".toUpperCase());
			
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja_texto_branco);
			cell.setCellValue("Peso NF Remessa".toUpperCase());
			
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja_texto_branco);
			cell.setCellValue("Valor NF Remessa".toUpperCase());

			ArrayList<CadastroContrato.Recebimento> lista_recebimentos = gerenciar
					.getRecebimentos(contrato_local.getId());

			boolean nf_remessa_ativo = false;
			boolean nf_venda_ativo = false;

			//checka se ha no minimo uma nf remessa aplicavel
			for(CadastroContrato.Recebimento recebimento : lista_recebimentos ) {
				if(recebimento.getNf_remessa_aplicavel() == 1) {
					nf_remessa_ativo = true;
					break;
				}
				
			}
			
			//checka se ha no minimo uma nf venda aplicavel
			for(CadastroContrato.Recebimento recebimento : lista_recebimentos ) {
				if(recebimento.getNf_venda_aplicavel() == 1) {
					nf_venda_ativo = true;
					break;
				}
				
			}
			
			
			for (CadastroContrato.Recebimento cadastro : lista_recebimentos) {

				row = sheet.createRow(rownum++);
				cellnum = 0;
				/*
				 * codigo compradores vendedores status quantidade medida produto transgenia
				 * safra valor_produto valor_total data_contrato local_retirada
				 */
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue(cadastro.getData_recebimento());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue(cadastro.getCodigo_romaneio());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(numberStyle);
				cell.setCellValue(cadastro.getPeso_romaneio());

				if (cadastro.getNf_venda_aplicavel() == 1) {
					cell = row.createCell(cellnum++);
					cell.setCellStyle(textStyle);
					cell.setCellValue(cadastro.getCodigo_nf_venda());

					cell = row.createCell(cellnum++);
					cell.setCellStyle(numberStyle);
					cell.setCellValue(cadastro.getPeso_nf_venda());
					quantidade_total_kgs_nf_venda = quantidade_total_kgs_nf_venda + cadastro.getPeso_nf_venda();

					cell = row.createCell(cellnum++);
					cell.setCellStyle(valorStyle);
					cell.setCellValue(cadastro.getValor_nf_venda().doubleValue());
					valor_total_nf_venda = valor_total_nf_venda.add(cadastro.getValor_nf_venda());

					
				} else {
					cell = row.createCell(cellnum++);
					cell.setCellStyle(textStyle);
					cell.setCellValue("Não Aplicável");

					cell = row.createCell(cellnum++);
					cell.setCellStyle(textStyle);
					cell.setCellValue("Não Aplicável");
					
					cell = row.createCell(cellnum++);
					cell.setCellStyle(textStyle);
					cell.setCellValue("Não Aplicável");
				}

				if (cadastro.getNf_remessa_aplicavel() == 1) {
					cell = row.createCell(cellnum++);
					cell.setCellStyle(numberStyle);
					cell.setCellValue(cadastro.getCodigo_nf_remessa());
					
					cell = row.createCell(cellnum++);
					cell.setCellStyle(numberStyle);
					cell.setCellValue(cadastro.getPeso_nf_remessa());
					quantidade_total_kgs_nf_remessa = quantidade_total_kgs_nf_remessa + cadastro.getPeso_nf_remessa();

					cell = row.createCell(cellnum++);
					cell.setCellStyle(valorStyle);
					cell.setCellValue(cadastro.getValor_nf_remessa().doubleValue());
					valor_total_nf_remessa = valor_total_nf_remessa.add(cadastro.getValor_nf_remessa());
					
				} else {
					cell = row.createCell(cellnum++);
					cell.setCellStyle(textStyle);
					cell.setCellValue("Não Aplicável");
					
					cell = row.createCell(cellnum++);
					cell.setCellStyle(textStyle);
					cell.setCellValue("Não Aplicável");
					
					cell = row.createCell(cellnum++);
					cell.setCellStyle(textStyle);
					cell.setCellValue("Não Aplicável");
				}


				quantidade_total_kgs_recebido = quantidade_total_kgs_recebido + cadastro.getPeso_romaneio();

			}
			sheet.setAutoFilter(CellRangeAddress.valueOf("A4:I4"));
			NumberFormat z = NumberFormat.getNumberInstance();

			//somatorias
			row = sheet.createRow(rownum++);
		
			cell = row.createCell(2);
			cell.setCellStyle(negrito);
			cell.setCellValue(z.format(quantidade_total_kgs_recebido) + " Kgs");
			
			cell = row.createCell(4);
			cell.setCellStyle(negrito);
			cell.setCellValue(z.format(quantidade_total_kgs_nf_venda) + " Kgs");
			
			cell = row.createCell(5);
			cell.setCellStyle(negrito);
			cell.setCellValue(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_nf_venda.doubleValue()));
					
			
			cell = row.createCell(7);
			cell.setCellStyle(negrito);
			cell.setCellValue(z.format(quantidade_total_kgs_nf_remessa) + " Kgs");
			
			cell = row.createCell(8);
			cell.setCellStyle(negrito);
			cell.setCellValue(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_nf_remessa.doubleValue()));
			
			//////////////////////
			row = sheet.createRow(rownum += 2);
			cellnum = 0;

			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue("");

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja_texto_branco);
			cell.setCellValue("Peso Recebido: ");

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_number_amarelo_texto_preto);
			cell.setCellValue(quantidade_total_kgs_recebido);

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja_texto_branco);
			cell.setCellValue("Peso NF's Venda: ");

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_number_amarelo_texto_preto);
			if(nf_venda_ativo)
			cell.setCellValue(quantidade_total_kgs_nf_venda);
			else
				cell.setCellValue("Não Aplicável");

			
				
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja_texto_branco);
			cell.setCellValue("Valor NF's Venda: ");

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_number_amarelo_texto_preto);
			if(nf_venda_ativo)
			cell.setCellValue(valor_total_nf_venda.doubleValue());
			else
				cell.setCellValue("Não Aplicável");

				
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja_texto_branco);
			cell.setCellValue("Peso NF's Remessa: ");

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_number_amarelo_texto_preto);
			if(nf_remessa_ativo)
			cell.setCellValue(quantidade_total_kgs_nf_remessa);
			else
				cell.setCellValue("Não Aplicável");

				
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja_texto_branco);
			cell.setCellValue("Valor NF's Remessa: ");

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_number_amarelo_texto_preto);
			if(nf_remessa_ativo)
			cell.setCellValue(valor_total_nf_remessa.doubleValue());
			else
				cell.setCellValue("Não Aplicável");
	

			row = sheet.createRow(rownum += 2);
			cellnum = 0;

			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue("");

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja_texto_branco);
			cell.setCellValue("Peso a Receber: ");

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_number_amarelo_texto_preto);
			cell.setCellValue(quantidade_kg - quantidade_total_kgs_recebido);

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja_texto_branco);
			cell.setCellValue("Peso a emitir: ");

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_number_amarelo_texto_preto);
			if(nf_venda_ativo)
			cell.setCellValue(quantidade_kg - quantidade_total_kgs_nf_venda);
			else
				cell.setCellValue("Não Aplicável");

				
			cell = row.createCell(7);
			cell.setCellStyle(celula_fundo_laranja_texto_branco);
			cell.setCellValue("Peso a emitir: ");

			cell = row.createCell(8);
			cell.setCellStyle(celula_number_amarelo_texto_preto);
			if(nf_remessa_ativo)
			cell.setCellValue(quantidade_kg - quantidade_total_kgs_nf_remessa);
			else
				cell.setCellValue("Não Aplicável");


			row = sheet.createRow(rownum += 2);
			cellnum = 0;
			
			cell = row.createCell(cellnum);
			cell.setCellStyle(negrito_esquerda);
			cell.setCellValue("Status gerado de forma automatica: ");
			
			sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, cellnum, 2));
			
			row = sheet.createRow(rownum+=1);
			cellnum = 0;
			
			cell = row.createCell(cellnum);
			cell.setCellStyle(negrito_esquerda);
			
			String texto = "";
			double diferenca = quantidade_kg - quantidade_total_kgs_recebido;
			if(diferenca == 0) {
				texto = texto + "Recebimento Concluído";
			}else if(diferenca > 0) {
				texto = texto + "Recebimento Incompleto, falta receber " + z.format(diferenca) + " Kgs";

			}else if(diferenca < 0) {
				texto = texto + "Recebimento Excedido, excedeu " + z.format(diferenca) + " Kgs";

			}
			
			cell.setCellValue(texto);
			
			sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, cellnum, 2));
			
			row = sheet.createRow(rownum+=1);
			cellnum = 0;
			
			cell = row.createCell(cellnum);
			cell.setCellStyle(negrito_esquerda);
			if(nf_venda_ativo) {
			//status de nf de venda
			
			
			String texto_nf_venda = "";
			double diferenca_nf_venda = quantidade_kg - quantidade_total_kgs_nf_venda;
			if(diferenca_nf_venda == 0) {
				texto_nf_venda = texto_nf_venda + "Emissão de NF's de Venda Concluído";
			}else if(diferenca_nf_venda > 0) {
				texto_nf_venda = texto_nf_venda + "Emissão de NF's de Venda Incompleto, falta emitir " + z.format(diferenca_nf_venda) + " Kgs";

			}else if(diferenca_nf_venda < 0) {
				texto_nf_venda = texto_nf_venda + "Emissão de NF's Venda Excedido, excedeu " + z.format(diferenca_nf_venda) + " Kgs";

			}
			
			cell.setCellValue(texto_nf_venda);
			
			sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, cellnum, 3));
			
			}else {
				cell.setCellValue("Emissão de NF's de Venda Não Aplicável");
				
				sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, cellnum, 3));
			}
			//status nf remessa
			row = sheet.createRow(rownum+=1);
			cellnum = 0;
			
			cell = row.createCell(cellnum);
			cell.setCellStyle(negrito_esquerda);
			if(nf_remessa_ativo) {
			
			
			String texto_nf_remessa = "";
			double diferenca_nf_remessa = quantidade_kg - quantidade_total_kgs_nf_remessa;
			if(diferenca_nf_remessa == 0) {
				texto_nf_remessa = texto_nf_remessa + "Emissão de NF's de Remessa Concluído";
			}else if(diferenca_nf_remessa > 0) {
				texto_nf_remessa = texto_nf_remessa + "Emissão de NF's de Remessa Incompleto, falta emitir " + z.format(diferenca_nf_remessa) + " Kgs";

			}else if(diferenca_nf_remessa < 0) {
				texto_nf_remessa = texto_nf_remessa + "Emissão de NF's Remessa, excedeu " + z.format(diferenca_nf_remessa) + " Kgs";

			}
			
			cell.setCellValue(texto_nf_remessa);
			
			sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, cellnum, 3));
			}else {
				cell.setCellValue("Emissão de NF's Remessa Não Aplicável");
				
				sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, cellnum, 3));
			}
			

		}

		// incluir carregamentos
		if (incluir_carregamento) {
			HSSFSheet sheet2 = workbook.createSheet("Carregamentos");

			// Definindo alguns padroes de layout
			sheet2.setDefaultColumnWidth(20);
			sheet2.setDefaultRowHeight((short) 400);

			rownum = 0;
			cellnum = 0;

			celula_fundo_laranja.setFont(newFont);

			celula_fundo_laranja_texto_branco.setFont(newFont_branca);

			// Configurando as informacoes
			row = sheet2.createRow(rownum++);

			cell = row.createCell(cellnum);
			cell.setCellStyle(celula_fundo_verde_texto_branco);
			cell.setCellValue(nome_compradores.toUpperCase() + " X " + nome_vendedores + " CTR "
					+ contrato_local.getCodigo() + " IE.: " + vendedores[0].getIe());
			sheet2.addMergedRegion(new CellRangeAddress(0, 0, cellnum, 5));

			// linha quantidade, safra, sacos, etc
			row = sheet2.createRow(rownum++);
			cellnum = 0;

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_verde_texto_branco);
			quantidade_kg = 0;
			quantidade_sacos = 0;

			if (contrato_local.getMedida().equalsIgnoreCase("KG")) {
				quantidade_kg = contrato_local.getQuantidade();
				quantidade_sacos = quantidade_kg / 60;
			} else if (contrato_local.getMedida().equalsIgnoreCase("Sacos")) {
				quantidade_sacos = contrato_local.getQuantidade();
				quantidade_kg = quantidade_sacos * 60;
			}

			cell.setCellValue("Quantidade: " + quantidade_kg + " KGS | " + quantidade_sacos + " Sacos no valor de "
					+ NumberFormat.getCurrencyInstance(ptBr).format(contrato_local.getValor_produto()) + " por "
					+ contrato_local.getMedida() + " no valor total de: "
					+ NumberFormat.getCurrencyInstance(ptBr).format(contrato_local.getValor_a_pagar()));
			sheet2.addMergedRegion(new CellRangeAddress(rownum - 1, rownum - 1, cellnum - 1, 5));

			
			GerenciarBancoContratos gerenciar_contratos = new GerenciarBancoContratos();

			double quantidade_kgs_recebidos = gerenciar_contratos.getQuantidadeRecebida(contrato_local.getId());
			quantidade_kg = quantidade_kgs_recebidos;

			
			row = sheet2.createRow(rownum++);
			cellnum = 0;

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_verde_texto_branco);
			cell.setCellValue(contrato_local.getModelo_safra().getProduto().getNome_produto() + " "
					+ contrato_local.getModelo_safra().getProduto().getTransgenia() + " "
					+ contrato_local.getModelo_safra().getAno_plantio() + "/"
					+ contrato_local.getModelo_safra().getAno_colheita());
			sheet2.addMergedRegion(new CellRangeAddress(rownum - 1, rownum - 1, cellnum - 1, 5));

			// Configurando Header
			// Configurando Header
			row = sheet2.createRow(rownum++);
			cellnum = 0;
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_verde_texto_branco);
			cell.setCellValue("DATA");

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_verde_texto_branco);
			cell.setCellValue("CLIENTE".toUpperCase());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_verde_texto_branco);
			cell.setCellValue("MOTORISTA".toUpperCase());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_verde_texto_branco);
			cell.setCellValue("PLACA".toUpperCase());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_verde_texto_branco);
			cell.setCellValue("ROMANEIO".toUpperCase());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_verde_texto_branco);
			cell.setCellValue("PESO".toUpperCase());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_verde_texto_branco);
			cell.setCellValue("NF INTERNA".toUpperCase());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_verde_texto_branco);
			cell.setCellValue("PESO NF INTERNA".toUpperCase());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_verde_texto_branco);
			cell.setCellValue("NF VENDA".toUpperCase());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_verde_texto_branco);
			cell.setCellValue("PESO NF VENDA".toUpperCase());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_verde_texto_branco);
			cell.setCellValue("VALOR NF VENDA".toUpperCase());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_verde_texto_branco);
			cell.setCellValue("DIFERENÇA".toUpperCase());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_verde_texto_branco);
			cell.setCellValue("NF COMPLEMENTO".toUpperCase());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_verde_texto_branco);
			cell.setCellValue("PESO NF COMPLEMENTO".toUpperCase());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_verde_texto_branco);
			cell.setCellValue("VALOR NF COMPLEMENTO".toUpperCase());

			double quantidade_total_kgs_carregados = 0;
			double quantidade_total_kgs_nf_venda1 = 0;
			double quantidade_total_kgs_nf_complemento = 0;
			double quantidade_total_kgs_diferenca = 0;
			double quantidade_total_kgs_nf_interna = 0;
			BigDecimal valor_total_nf_venda1 = BigDecimal.ZERO;
			BigDecimal valor_total_nf_complemento = BigDecimal.ZERO;

			ArrayList<CadastroContrato.Carregamento> lista_carregamentos = gerenciar
					.getCarregamentos(contrato_local.getId());
			

			boolean nf_interna_ativo = false;
			boolean nf_venda_ativo = false;
			boolean nf_complemento_ativo = false;

			// checka se ha no minimo uma nf interna aplicavel
			for (CadastroContrato.Carregamento carregamento : lista_carregamentos) {
				if (carregamento.getNf_interna_aplicavel() == 1) {
					nf_interna_ativo = true;
					break;
				}

			}

			// checka se ha no minimo uma nf venda aplicavel
			for (CadastroContrato.Carregamento carregamento : lista_carregamentos) {
				if (carregamento.getNf_venda1_aplicavel() == 1) {
					nf_venda_ativo = true;
					break;
				}

			}

			// checka se ha no minimo uma nf complemento aplicavel
			for (CadastroContrato.Carregamento carregamento : lista_carregamentos) {
				if (carregamento.getNf_complemento_aplicavel() == 1) {
					nf_complemento_ativo = true;
					break;
				}

			}
			for (CadastroContrato.Carregamento carregamento : lista_carregamentos) {
				if (carregamento.getDescricao() == null) {

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
					CadastroCliente vendedor_carregamento = gerenciar_clientes
							.getCliente(carregamento.getId_vendedor());
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
					String codigo_nf_venda1 = "", codigo_nf_complemento = "", codigo_nf_interna = "";
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
					} catch (Exception t) {
						// JOptionPane.showMessageDialog(isto, "Romaneio não Localizado");
						codigo_romaneio = carregamento.getCodigo_romaneio();
						peso_romaneio = carregamento.getPeso_romaneio();
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
								} catch (Exception u) {
									valor_nf_venda1 = BigDecimal.ZERO;
								}

							} else {
								codigo_nf_venda1 = carregamento.getCodigo_nf_venda1();
								peso_nf_venda1 = carregamento.getPeso_nf_venda1();
								valor_nf_venda1 = carregamento.getValor_nf_venda1();

							}

						}
					} catch (Exception g) {
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
								} catch (Exception l) {
									valor_nf_complemento = BigDecimal.ZERO;
								}

							} else {
								codigo_nf_complemento = carregamento.getCodigo_nf_complemento();
								peso_nf_complemento = carregamento.getPeso_nf_complemento();
								valor_nf_complemento = carregamento.getValor_nf_complemento();

							}

						}
					} catch (Exception y) {
						// JOptionPane.showMessageDialog(isto, "Nota Fiscal de remessa não Localizado");

						codigo_nf_complemento = carregamento.getCodigo_nf_complemento();
						peso_nf_complemento = carregamento.getPeso_nf_complemento();
						valor_nf_complemento = carregamento.getValor_nf_complemento();

					}

					// nf interna
					try {
						if (checkString(carregamento.getCodigo_nf_interna())) {
							if (carregamento.getCaminho_nf_interna().length() > 10) {
								// procurar por nf remessa
								ManipularNotasFiscais manipular = new ManipularNotasFiscais("");
								CadastroNFe nota_fiscal_interna = manipular
										.filtrar(new File(servidor_unidade + carregamento.getCaminho_nf_complemento()));
								codigo_nf_interna = nota_fiscal_interna.getNfe();
								peso_nf_interna = Double.parseDouble(nota_fiscal_interna.getQuantidade());

							} else {
								codigo_nf_interna = carregamento.getCodigo_nf_interna();
								peso_nf_interna = carregamento.getPeso_nf_interna();

							}

						}
					} catch (Exception y) {
						// JOptionPane.showMessageDialog(isto, "Nota Fiscal de remessa não Localizado");

						codigo_nf_interna = carregamento.getCodigo_nf_interna();
						peso_nf_interna = carregamento.getPeso_nf_interna();

					}

					row = sheet2.createRow(rownum++);
					cellnum = 0;
					/*
					 * codigo compradores vendedores status quantidade medida produto transgenia
					 * safra valor_produto valor_total data_contrato local_retirada
					 */
					cell = row.createCell(cellnum++);
					cell.setCellStyle(textStyle);
					cell.setCellValue(carregamento.getData());

					cell = row.createCell(cellnum++);
					cell.setCellStyle(textStyle);
					cell.setCellValue(nome_cliente);

					cell = row.createCell(cellnum++);
					cell.setCellStyle(textStyle);
					cell.setCellValue(nome_transportador);

					cell = row.createCell(cellnum++);
					cell.setCellStyle(textStyle);
					cell.setCellValue(placa_trator);

					cell = row.createCell(cellnum++);
					cell.setCellStyle(textStyle);
					cell.setCellValue(codigo_romaneio);

					cell = row.createCell(cellnum++);
					cell.setCellStyle(numberStyle);
					cell.setCellValue(peso_romaneio);

					if (carregamento.getNf_interna_aplicavel() == 1) {
						cell = row.createCell(cellnum++);
						cell.setCellStyle(textStyle);
						cell.setCellValue(codigo_nf_interna);
					} else {
						cell = row.createCell(cellnum++);
						cell.setCellStyle(textStyle);
						cell.setCellValue("Não Aplicável");
					}

					if (carregamento.getNf_interna_aplicavel() == 1) {
						cell = row.createCell(cellnum++);
						cell.setCellStyle(numberStyle);
						cell.setCellValue(peso_nf_interna);
						quantidade_total_kgs_nf_interna += peso_nf_interna;
					} else {
						cell = row.createCell(cellnum++);
						cell.setCellStyle(textStyle);
						cell.setCellValue("Não Aplicável");
					}

					if (carregamento.getNf_venda1_aplicavel() == 1) {
						cell = row.createCell(cellnum++);
						cell.setCellStyle(textStyle);
						cell.setCellValue(codigo_nf_venda1);
					} else {
						cell = row.createCell(cellnum++);
						cell.setCellStyle(textStyle);
						cell.setCellValue("Não Aplicável");
					}

					if (carregamento.getNf_venda1_aplicavel() == 1) {
						cell = row.createCell(cellnum++);
						cell.setCellStyle(numberStyle);
						cell.setCellValue(peso_nf_venda1);
						quantidade_total_kgs_nf_venda1 += peso_nf_venda1;

					} else {
						cell = row.createCell(cellnum++);
						cell.setCellStyle(textStyle);
						cell.setCellValue("Não Aplicável");
					}

					if (carregamento.getNf_venda1_aplicavel() == 1) {
						cell = row.createCell(cellnum++);
						cell.setCellStyle(valorStyle);
						cell.setCellValue(valor_nf_venda1.doubleValue());
						valor_total_nf_venda1 = valor_total_nf_venda1.add(valor_nf_venda1);

					} else {
						cell = row.createCell(cellnum++);
						cell.setCellStyle(textStyle);
						cell.setCellValue("Não Aplicável");
					}

					if (carregamento.getNf_venda1_aplicavel() == 1 && carregamento.getNf_complemento_aplicavel() == 1) {
						cell = row.createCell(cellnum++);
						cell.setCellStyle(numberStyle);
						cell.setCellValue(peso_romaneio - (peso_nf_venda1 + peso_nf_complemento));
					} else if (carregamento.getNf_venda1_aplicavel() == 1
							&& carregamento.getNf_complemento_aplicavel() == 0) {
						cell = row.createCell(cellnum++);
						cell.setCellStyle(numberStyle);
						cell.setCellValue(peso_romaneio - (peso_nf_venda1));
					} else if (carregamento.getNf_venda1_aplicavel() == 0
							&& carregamento.getNf_complemento_aplicavel() == 1) {
						cell = row.createCell(cellnum++);
						cell.setCellStyle(numberStyle);
						cell.setCellValue(peso_romaneio - (peso_nf_complemento));
					} else {
						cell = row.createCell(cellnum++);
						cell.setCellStyle(textStyle);
						cell.setCellValue("Não Aplicável");
					}

					if (carregamento.getNf_complemento_aplicavel() == 1) {
						cell = row.createCell(cellnum++);
						cell.setCellStyle(textStyle);
						cell.setCellValue(codigo_nf_complemento);
					} else {
						cell = row.createCell(cellnum++);
						cell.setCellStyle(textStyle);
						cell.setCellValue("Não Aplicável");
					}

					if (carregamento.getNf_complemento_aplicavel() == 1) {
						cell = row.createCell(cellnum++);
						cell.setCellStyle(numberStyle);
						cell.setCellValue(peso_nf_complemento);
						quantidade_total_kgs_nf_complemento += peso_nf_complemento;
					} else {
						cell = row.createCell(cellnum++);
						cell.setCellStyle(textStyle);
						cell.setCellValue("Não Aplicável");
					}

					if (carregamento.getNf_complemento_aplicavel() == 1) {
						cell = row.createCell(cellnum++);
						cell.setCellStyle(valorStyle);
						cell.setCellValue(valor_nf_complemento.doubleValue());
						valor_total_nf_complemento = valor_total_nf_complemento.add(valor_nf_complemento);
					} else {
						cell = row.createCell(cellnum++);
						cell.setCellStyle(textStyle);
						cell.setCellValue("Não Aplicável");
					}

					quantidade_total_kgs_carregados += peso_romaneio;
					if (carregamento.getNf_venda1_aplicavel() == 1 && carregamento.getNf_complemento_aplicavel() == 1) {
						quantidade_total_kgs_diferenca += (peso_romaneio - (peso_nf_venda1 + peso_nf_complemento));
					} else if (carregamento.getNf_venda1_aplicavel() == 1
							&& carregamento.getNf_complemento_aplicavel() == 0) {
						quantidade_total_kgs_diferenca += (peso_romaneio - (peso_nf_venda1));

					} else if (carregamento.getNf_venda1_aplicavel() == 0
							&& carregamento.getNf_complemento_aplicavel() == 1) {
						quantidade_total_kgs_diferenca += (peso_romaneio - (peso_nf_complemento));

					}

				}
			} // fim de configurar carregamento normal

			GerenciarBancoTransferenciasCarga gerenciar_trans = new GerenciarBancoTransferenciasCarga();
			ArrayList<CadastroTransferenciaCarga> transferencias_remetentes = gerenciar_trans
					.getTransferenciasRemetente(contrato_local.getId());
			for (CadastroContrato.CadastroTransferenciaCarga transferencia : transferencias_remetentes) {

				row = sheet2.createRow(rownum++);
				cellnum = 0;
				/*
				 * codigo compradores vendedores status quantidade medida produto transgenia
				 * safra valor_produto valor_total data_contrato local_retirada
				 */
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue(transferencia.getData());

				String texto_detalhado = "";

				GerenciarBancoContratos gerencia_contratos = new GerenciarBancoContratos();
				CadastroContrato remetente = gerencia_contratos.getContrato(transferencia.getId_contrato_remetente());
				CadastroContrato destinatario = gerencia_contratos
						.getContrato(transferencia.getId_contrato_destinatario());
				CadastroContrato.Carregamento carga = gerencia_contratos
						.getCarregamento(transferencia.getId_carregamento_remetente());

				CadastroCliente compradores_trans[] = destinatario.getCompradores();
				CadastroCliente vendedores_trans[] = destinatario.getVendedores();

				nome_vendedores = "";
				nome_compradores = "";

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

				NumberFormat z = NumberFormat.getNumberInstance();

				texto_detalhado = "Transferência Negativa: Transferência do volume de " + z.format(quantidade)
						+ " kgs | " + z.format(quantidade / 60) + " sacos deste contrato para o contrato ";
				texto_detalhado = texto_detalhado + destinatario.getCodigo() + "\n" + nome_compradores + " X "
						+ nome_vendedores + " " + z.format(destinatario.getQuantidade()) + " "
						+ destinatario.getMedida() + " de "
						+ destinatario.getModelo_safra().getProduto().getNome_produto() + " "
						+ destinatario.getModelo_safra().getProduto().getTransgenia() + " da safra "
						+ destinatario.getModelo_safra().getAno_plantio() + "/"
						+ destinatario.getModelo_safra().getAno_colheita();
				texto_detalhado = texto_detalhado + "";
				sheet2.addMergedRegion(new CellRangeAddress(rownum - 1, rownum - 1, 1, 16));
				
				
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyleAlinhadoEsquerda);

				cell.setCellValue(texto_detalhado);

				quantidade_total_kgs_carregados -= quantidade;

			}
			ArrayList<CadastroTransferenciaCarga> transferencias_destinatario = gerenciar_trans
					.getTransferenciaDestinatario(contrato_local.getId());
			for (CadastroContrato.CadastroTransferenciaCarga transferencia : transferencias_destinatario) {
				row = sheet2.createRow(rownum++);
				cellnum = 0;
				/*
				 * codigo compradores vendedores status quantidade medida produto transgenia
				 * safra valor_produto valor_total data_contrato local_retirada
				 */
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue(transferencia.getData());

				String texto_detalhado = "";

				GerenciarBancoContratos gerencia_contratos = new GerenciarBancoContratos();
				CadastroContrato remetente = gerencia_contratos.getContrato(transferencia.getId_contrato_remetente());
				CadastroContrato destinatario = gerencia_contratos
						.getContrato(transferencia.getId_contrato_destinatario());
				CadastroContrato.Carregamento carga = gerencia_contratos
						.getCarregamento(transferencia.getId_carregamento_remetente());

				CadastroCliente compradores_trans[] = destinatario.getCompradores();
				CadastroCliente vendedores_trans[] = destinatario.getVendedores();

				nome_vendedores = "";
				nome_compradores = "";

				if (compradores_trans[0] != null) {
					if (compradores_trans[0].getTipo_pessoa() == 0) {
						// pessoa fisica
						nome_compradores = compradores_trans[0].getNome_empresarial();
					} else {
						nome_compradores = compradores_trans[0].getNome_fantaia();

					}
				}

				if (compradores_trans[1] != null) {
					if (compradores[1].getTipo_pessoa() == 0) {
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

				NumberFormat z = NumberFormat.getNumberInstance();

				
				texto_detalhado = "Transferência Positiva: Recebimento de volume de " + z.format(quantidade) + " kgs | "
						+ z.format(quantidade / 60) + " sacos recebidos do contrato ";
				texto_detalhado = texto_detalhado + destinatario.getCodigo() + " " + nome_compradores + " X "
						+ nome_vendedores + " " + z.format(destinatario.getQuantidade()) + " "
						+ destinatario.getMedida() + " de "
						+ destinatario.getModelo_safra().getProduto().getNome_produto() + " "
						+ destinatario.getModelo_safra().getProduto().getTransgenia() + " da safra "
						+ destinatario.getModelo_safra().getAno_plantio() + "/"
						+ destinatario.getModelo_safra().getAno_colheita();
				texto_detalhado = texto_detalhado + "";
				sheet2.addMergedRegion(new CellRangeAddress(rownum - 1, rownum - 1, 1, 16));

				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyleAlinhadoEsquerda);

				cell.setCellValue(texto_detalhado);
				quantidade_total_kgs_carregados += quantidade;

			}

			sheet2.setAutoFilter(CellRangeAddress.valueOf("A4:M4"));
			NumberFormat z = NumberFormat.getNumberInstance();
			
row = sheet2.createRow(rownum++);
			cellnum = 0;

			cell = row.createCell(4);
			cell.setCellStyle(textStyle);
			cell.setCellValue("Totais(Kg's):");

			cell = row.createCell(5);
			cell.setCellStyle(numberStyle);
			cell.setCellValue(quantidade_total_kgs_carregados);

			cell = row.createCell(7);
			cell.setCellStyle(numberStyle);
			cell.setCellValue(quantidade_total_kgs_nf_interna);

			cell = row.createCell(9);
			cell.setCellStyle(numberStyle);
			cell.setCellValue(quantidade_total_kgs_nf_venda1);

			cell = row.createCell(10);
			cell.setCellStyle(valorStyle);
			cell.setCellValue(valor_total_nf_venda1.doubleValue());

			cell = row.createCell(11);
			cell.setCellStyle(numberStyle);
			cell.setCellValue(quantidade_total_kgs_diferenca);

			cell = row.createCell(13);
			cell.setCellStyle(numberStyle);
			cell.setCellValue(quantidade_total_kgs_nf_complemento);

			cell = row.createCell(14);
			cell.setCellStyle(valorStyle);
			cell.setCellValue(valor_total_nf_complemento.doubleValue());
			row = sheet2.createRow(rownum++);

			cell = row.createCell(4);
			cell.setCellStyle(textStyle);
			cell.setCellValue("Totais(sacos):");

			cell = row.createCell(5);
			cell.setCellStyle(numberStyle);
			cell.setCellValue(quantidade_total_kgs_carregados / 60);

			cell = row.createCell(7);
			cell.setCellStyle(numberStyle);
			cell.setCellValue(quantidade_total_kgs_nf_interna / 60);

			cell = row.createCell(9);
			cell.setCellStyle(numberStyle);
			cell.setCellValue(quantidade_total_kgs_nf_venda1 / 60);

			cell = row.createCell(11);
			cell.setCellStyle(numberStyle);
			cell.setCellValue(quantidade_total_kgs_diferenca / 60);

			cell = row.createCell(13);
			cell.setCellStyle(numberStyle);
			cell.setCellValue(quantidade_total_kgs_nf_complemento / 60);

				// totais
				row = sheet2.createRow(rownum += 1);
				cellnum = 0;

				cell = row.createCell(0);
				cell.setCellStyle(textStyle);
				cell.setCellValue("Peso Carregado");

				cell = row.createCell(1);
				cell.setCellStyle(numberStyle);
				cell.setCellValue(z.format(quantidade_total_kgs_carregados) + " Kgs");

				cell = row.createCell(2);
				cell.setCellStyle(textStyle);
				cell.setCellValue("Peso NF's Interna:");

				cell = row.createCell(3);
				cell.setCellStyle(numberStyle);
				if (nf_interna_ativo) {

					cell.setCellValue(z.format(quantidade_total_kgs_nf_interna) + " Kgs");
				} else {
					cell.setCellValue("Não Aplicável");

				}

				cell = row.createCell(4);
				cell.setCellStyle(textStyle);
				cell.setCellValue("Peso NF's Venda:");

				cell = row.createCell(5);
				cell.setCellStyle(numberStyle);
				if (nf_venda_ativo || nf_complemento_ativo) {
					cell.setCellValue(z.format(quantidade_total_kgs_nf_venda1 + quantidade_total_kgs_nf_complemento)
							+ " Kgs");

				} else {
					cell.setCellValue("Não Aplicável");

				}

				cell = row.createCell(6);
				cell.setCellStyle(textStyle);
				cell.setCellValue("Valor NF's Venda:");

				cell = row.createCell(7);
				cell.setCellStyle(numberStyle);

				BigDecimal soma = valor_total_nf_venda1.add(valor_total_nf_complemento);

				if (nf_venda_ativo || nf_complemento_ativo) {
					cell.setCellValue(NumberFormat.getCurrencyInstance(ptBr).format(soma.doubleValue()));

				} else {
					cell.setCellValue("Não Aplicável");

				}

				rownum++;
				row = sheet2.createRow(rownum);

				cell = row.createCell(0);
				cell.setCellStyle(textStyle);
				cell.setCellValue("Peso a Carregar");

				cell = row.createCell(1);
				cell.setCellStyle(numberStyle);
				cell.setCellValue(z.format(quantidade_kg - quantidade_total_kgs_carregados) + " Kgs");

				cell = row.createCell(2);
				cell.setCellStyle(textStyle);
				cell.setCellValue("Peso a Emitir:");

				cell = row.createCell(3);
				cell.setCellStyle(numberStyle);
				if (nf_interna_ativo) {
					cell.setCellValue(
							z.format(quantidade_total_kgs_carregados - quantidade_total_kgs_nf_interna) + " Kgs");
				} else {
					cell.setCellValue("Não Aplicável");

				}

				cell = row.createCell(4);
				cell.setCellStyle(textStyle);
				cell.setCellValue("Peso a Emitir:");

				cell = row.createCell(5);
				cell.setCellStyle(numberStyle);
				if (nf_venda_ativo || nf_complemento_ativo) {
					cell.setCellValue(z
							.format(quantidade_total_kgs_carregados
									- (quantidade_total_kgs_nf_venda1 + quantidade_total_kgs_nf_complemento))
							+ " Kgs");

				} else {
					cell.setCellValue("Não Aplicável");

				}

	
				// status baseado no peso total ja carregado

				rownum += 2;

				row = sheet2.createRow(rownum);

				cell = row.createCell(cellnum);
				cell.setCellStyle(textStyleAlinhadoEsquerdaNegrito);
				cell.setCellValue(
						"Status parcial gerado de forma automatica calculados a partir do peso total já recebido: " + z.format(quantidade_kg) + " kgs | " + z.format(quantidade_kg/60) + " sacos") ;

				sheet2.addMergedRegion(new CellRangeAddress(rownum, rownum, cellnum, 5));

				rownum++;
				row = sheet2.createRow(rownum);
				cellnum = 0;

				cell = row.createCell(cellnum);
				cell.setCellStyle(textStyleAlinhadoEsquerdaNegrito);

				String texto = "";
				double diferenca = quantidade_kg - quantidade_total_kgs_carregados;
				if (diferenca == 0) {
					texto = texto + "Carregamento Concluído";
				} else if (diferenca > 0) {
					texto = texto + "Carregamento Incompleto, falta carregar " + z.format(diferenca) + " Kgs";

				} else if (diferenca < 0) {
					texto = texto + "Carregamento Excedido, excedeu " + z.format(diferenca) + " Kgs";

				}

				cell.setCellValue(texto);

				sheet2.addMergedRegion(new CellRangeAddress(rownum, rownum, cellnum, 2));

				// status nf remessa
				rownum += 1;
				row = sheet2.createRow(rownum);
				cellnum = 0;

				cell = row.createCell(cellnum);
				cell.setCellStyle(textStyleAlinhadoEsquerdaNegrito);

				String texto_nf_remessa = "";
				double diferenca_nf_remessa = quantidade_total_kgs_carregados - quantidade_total_kgs_nf_interna;
				if (diferenca_nf_remessa == 0) {
					texto_nf_remessa = texto_nf_remessa + "Emissão de NF's Interna Concluído";
				} else if (diferenca_nf_remessa > 0) {
					texto_nf_remessa = texto_nf_remessa + "Emissão de NF's Interna Incompleto, falta emitir "
							+ z.format(diferenca_nf_remessa) + " Kgs";

				} else if (diferenca_nf_remessa < 0) {
					texto_nf_remessa = texto_nf_remessa + "Emissão de NF's Interna, excedeu "
							+ z.format(diferenca_nf_remessa) + " Kgs";

				}

				if (nf_interna_ativo)
					cell.setCellValue(texto_nf_remessa);
				else
					cell.setCellValue("Emissão de NF's Interna Não Aplicável");

				sheet2.addMergedRegion(new CellRangeAddress(rownum, rownum, cellnum, 3));

				// status de nf de venda
				rownum += 1;
				row = sheet2.createRow(rownum);
				cellnum = 0;

				cell = row.createCell(cellnum);
				cell.setCellStyle(textStyleAlinhadoEsquerdaNegrito);

				String texto_nf_venda = "";
				double diferenca_nf_venda = quantidade_total_kgs_carregados
						- (quantidade_total_kgs_nf_venda1 + quantidade_total_kgs_nf_complemento);
				if (diferenca_nf_venda == 0) {
					texto_nf_venda = texto_nf_venda + "Emissão de NF's de Venda Concluído";
				} else if (diferenca_nf_venda > 0) {
					texto_nf_venda = texto_nf_venda + "Emissão de NF's de Venda Incompleto, falta emitir "
							+ z.format(diferenca_nf_venda) + " Kgs";

				} else if (diferenca_nf_venda < 0) {
					texto_nf_venda = texto_nf_venda + "Emissão de NF's Excedido, excedeu "
							+ z.format(diferenca_nf_venda) + " Kgs";

				}
				if (nf_venda_ativo || nf_complemento_ativo)
					cell.setCellValue(texto_nf_venda);
				else
					cell.setCellValue("Emissão de NF's de Venda Não Aplicável");

				sheet2.addMergedRegion(new CellRangeAddress(rownum, rownum, cellnum, 3));

			
			
		}
		// incluir_pagamento
		if (incluir_pagamento) {
			HSSFSheet sheet3 = workbook.createSheet("Pagamentos");

			// Definindo alguns padroes de layout
			sheet3.setDefaultColumnWidth(20);
			sheet3.setDefaultRowHeight((short) 400);

			rownum = 0;
			cellnum = 0;

			// Configurando estilos de células (Cores, alinhamento, formatação, etc..)

			// Configurando as informacoes
			row = sheet3.createRow(rownum++);

			cell = row.createCell(cellnum);
			cell.setCellStyle(celula_fundo_verde_texto_branco);
			cell.setCellValue(nome_compradores.toUpperCase() + " X " + nome_vendedores + " CTR "
					+ contrato_local.getCodigo() + " IE.: " + vendedores[0].getIe());
			sheet3.addMergedRegion(new CellRangeAddress(0, 0, cellnum, 5));

			// linha quantidade, safra, sacos, etc
			row = sheet3.createRow(rownum++);
			cellnum = 0;

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_verde_texto_branco);

			quantidade_kg = 0;
			quantidade_sacos = 0;

			if (contrato_local.getMedida().equalsIgnoreCase("KG")) {
				quantidade_kg = contrato_local.getQuantidade();
				quantidade_sacos = quantidade_kg / 60;
			} else if (contrato_local.getMedida().equalsIgnoreCase("Sacos")) {
				quantidade_sacos = contrato_local.getQuantidade();
				quantidade_kg = quantidade_sacos * 60;
			}

			cell.setCellValue("Quantidade: " + quantidade_kg + " KGS | " + quantidade_sacos + " Sacos no valor de "
					+ NumberFormat.getCurrencyInstance(ptBr).format(contrato_local.getValor_produto()) + " por "
					+ contrato_local.getMedida() + " no valor total de: "
					+ NumberFormat.getCurrencyInstance(ptBr).format(contrato_local.getValor_a_pagar()));
			sheet3.addMergedRegion(new CellRangeAddress(rownum - 1, rownum - 1, cellnum - 1, 5));

			row = sheet3.createRow(rownum++);
			cellnum = 0;

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_verde_texto_branco);
			cell.setCellValue(contrato_local.getModelo_safra().getProduto().getNome_produto() + " "
					+ contrato_local.getModelo_safra().getProduto().getTransgenia() + " "
					+ contrato_local.getModelo_safra().getAno_plantio() + "/"
					+ contrato_local.getModelo_safra().getAno_colheita());
			sheet3.addMergedRegion(new CellRangeAddress(rownum - 1, rownum - 1, cellnum - 1, 5));

			
			// Configurando Header
			row = sheet3.createRow(rownum++);
			cellnum = 0;
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_verde_texto_branco);
			cell.setCellValue("DATA");

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_verde_texto_branco);
			cell.setCellValue("TIPO".toUpperCase());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_verde_texto_branco);
			cell.setCellValue("DESCRIÇÃO".toUpperCase());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_verde_texto_branco);
			cell.setCellValue("VALOR PAGAMENTO".toUpperCase());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_verde_texto_branco);
			cell.setCellValue("VALOR UNIDADE".toUpperCase());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_verde_texto_branco);
			cell.setCellValue("COBERTURA".toUpperCase());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_verde_texto_branco);
			cell.setCellValue("DEPOSITANTE".toUpperCase());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_verde_texto_branco);
			cell.setCellValue("CONTA DEPOSITANTE".toUpperCase());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_verde_texto_branco);
			cell.setCellValue("FAVORECIDO".toUpperCase());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_verde_texto_branco);
			cell.setCellValue("CONTA FAVORECIDO".toUpperCase());
			
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_verde_texto_branco);
			cell.setCellValue("CONTRATO REMETENTE".toUpperCase());
			
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_verde_texto_branco);
			cell.setCellValue("CONTRATO DESTINATARIO".toUpperCase());
			
			double soma_total_pagamentos = 0.0;
			// pega os documentos
			GerenciarBancoDocumento gerenciar_docs = new GerenciarBancoDocumento();
			ArrayList<CadastroDocumento> docs = gerenciar_docs.getDocumentos(contrato_local.getId());

			int num_comprovantes = 0;

			for (CadastroDocumento doc : docs) {
				if (doc.getTipo() == 2) {
					// e um pagamento
					num_comprovantes++;
				}
			}
			
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

			double quantidade_total_contrato_sacos = 0;
			double valor_por_saco = 0;
			
	

			if (contrato_local.getMedida().equalsIgnoreCase("Kg")) {
				quantidade_total_contrato_sacos = contrato_local.getQuantidade() / 60;
				valor_por_saco = contrato_local.getValor_produto() * 60;
			} else if (contrato_local.getMedida().equalsIgnoreCase("Sacos")) {
				quantidade_total_contrato_sacos = contrato_local.getQuantidade();
				valor_por_saco = contrato_local.getValor_produto();
			}

			GerenciarBancoContratos gerenciar_contratos = new GerenciarBancoContratos();

			//valor total e cobertura com base no total de sacos recebidos
			double quantidade_kgs_recebidos = gerenciar_contratos.getQuantidadeRecebida(contrato_local.getId());
			peso_total_cobertura = quantidade_kgs_recebidos/60;
			valor_total_pagamentos = peso_total_cobertura * valor_por_saco;
			
			String valorSaco = NumberFormat.getCurrencyInstance(ptBr).format(valor_por_saco);

			ArrayList<PagamentoCompleto> lista_pagamentos_contratuais = gerenciar
					.getPagamentosContratuaisParaRelatorio(contrato_local.getId());

			for (PagamentoCompleto pagamento : lista_pagamentos_contratuais) {
				
				
				if(pagamento.getTipo() == 1 || pagamento.getTipo() == 2 && incluir_comissao || pagamento.getTipo() == 3 && incluir_transferencias){
				row = sheet3.createRow(rownum++);
				cellnum = 0;
				/*
				 * codigo compradores vendedores status quantidade medida produto transgenia
				 * safra valor_produto valor_total data_contrato local_retirada
				 */
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue(pagamento.getData_pagamento());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				int tipo = pagamento.getTipo();
				String s_tipo = "";
				if (pagamento.getTipo() == 1) {
					s_tipo = "NORMAL";
				} else if (pagamento.getTipo() == 2) {
					s_tipo = "COMISSÃO";
				} else if (pagamento.getTipo() == 3) {
					// é uma transferencia
					if (pagamento.getId_contrato_remetente() == contrato_local.getId()) {
						s_tipo = "-Transferencia";
					} else if (pagamento.getId_contrato_destinatario() == contrato_local.getId()) {
						// é uma transferencia positiva
						s_tipo = "+Transferencia";
					}

				}

				cell.setCellValue(s_tipo);
				
				//descricao
				cell = row.createCell(cellnum++);
				cell.setCellStyle(valorStyle);
				cell.setCellValue(pagamento.getDescricao());

				//valor pagamento
				double valor_pagamento = pagamento.getValor_pagamento();

				String valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor_pagamento);
				double cobertura = valor_pagamento / valor_por_saco;

				if (pagamento.getTipo() == 1) {
					valor_total_pagamentos_efetuados += valor_pagamento;
					soma_total_pagamentos += pagamento.getValor_pagamento();

				} else if(pagamento.getTipo() == 2) {
					// é uma comissão
					valor_total_comissao += valor_pagamento;
					
				} else if (pagamento.getTipo() == 3) {
					// é uma transferencia
					if (pagamento.getId_contrato_remetente() == contrato_local.getId()) {
						// é uma transferencia negativa
						valor_total_transferencias_retiradas += valor_pagamento;
						soma_total_pagamentos -= pagamento.getValor_pagamento();

					} else if (pagamento.getId_contrato_destinatario() == contrato_local.getId()) {
						// é uma transferencia positiva
						valor_total_transferencias_recebidas += valor_pagamento;
						soma_total_pagamentos += pagamento.getValor_pagamento();

					}

				}
				cell = row.createCell(cellnum++);
				cell.setCellStyle(valorStyle);
				cell.setCellValue(pagamento.getValor_pagamento());

				// valor da unidade
				CadastroContrato ct_remetente = pagamento.getContrato_remetente();
				CadastroContrato ct_destinatario = pagamento.getContrato_destinatario();

				 valorString = NumberFormat.getCurrencyInstance(ptBr).format(ct_remetente.getValor_produto());
				if (pagamento.getTipo() == 1) {

				} else if (pagamento.getTipo() == 2) {

				} else if (pagamento.getTipo() == 3) {
					// é uma transferencia
					if (pagamento.getId_contrato_remetente() == contrato_local.getId()) {

					} else if (pagamento.getId_contrato_destinatario() == contrato_local.getId()) {
						// é uma transferencia positiva
						// pegar o preco da unidade do contrato que recebeu a transferencia
						valorString = NumberFormat.getCurrencyInstance(ptBr)
								.format(contrato_local.getValor_produto());

					}
				}
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue(valorString);

				//cobertura
				 cobertura = pagamento.getValor_pagamento() / ct_remetente.getValor_produto();
				if(ct_remetente.getMedida().equalsIgnoreCase("KG"))
					cobertura = cobertura / 60;
				NumberFormat z = NumberFormat.getNumberInstance();

				String retorno =  z.format(cobertura)  + " sacos";
				
				if (pagamento.getTipo() == 1) {
					peso_total_cobertura_efetuados += cobertura;

				}else if(pagamento.getTipo() == 2) {
					peso_total_cobertura_comissao += cobertura;
				}
				else if(pagamento.getTipo() == 3) {
					//é uma transferencia
					if(pagamento.getId_contrato_remetente() == contrato_local.getId()) {
						retorno = "-" + retorno;
						peso_total_cobertura_transferencia_negativa += cobertura;

					}else if(pagamento.getId_contrato_destinatario() == contrato_local.getId()) {
						//é uma transferencia positiva
						//pegar o preco da unidade do contrato que recebeu a transferencia
				
						
						 cobertura = pagamento.getValor_pagamento() / contrato_local.getValor_produto();
						
						 if(contrato_local.getMedida().equalsIgnoreCase("KG"))
							cobertura = cobertura / 60;
							peso_total_cobertura_transferencia_positiva += cobertura;

						 retorno =  z.format(cobertura)  + " sacos";
						retorno = "+" + retorno;
					}
					
				}
				
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue(retorno);

				//depositante
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue(pagamento.getDepositante().toUpperCase());
				
				//conta depositante
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue(pagamento.getConta_bancaria_depositante());
				
				//favorecido
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue(pagamento.getFavorecido().toUpperCase());
				
				//conta favorecido
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue(pagamento.getConta_bancaria_favorecido());

				//contrato remetente
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue(ct_remetente.getCodigo());
				
				
				//contrato destinatario
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue(ct_destinatario.getCodigo());

				}
			}

			sheet3.setAutoFilter(CellRangeAddress.valueOf("A4:L4"));


			row = sheet3.createRow(rownum += 1);
			cellnum = 0;

			cell = row.createCell(2);
			cell.setCellStyle(textStyle);
			cell.setCellValue("TOTAL CONCLUÍDO:");

			 negrito = workbook.createCellStyle();
			// textStyle.setAlignment(HorizontalAlignment.CENTER);
			negrito.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			negrito.setFillForegroundColor(IndexedColors.WHITE.getIndex());
			negrito.setAlignment(HorizontalAlignment.CENTER);
			negrito.setVerticalAlignment(VerticalAlignment.CENTER);
			negrito.setDataFormat(numberFormat.getFormat("R$ #,##0.00"));

			HSSFFont newFontNegrita = workbook.createFont();
			newFontNegrita.setBold(true);
			newFontNegrita.setColor(IndexedColors.BLACK.getIndex());
			newFontNegrita.setFontName("Arial");
			newFontNegrita.setItalic(true);
			newFontNegrita.setFontHeight((short) (11 * 20));

			negrito.setFont(newFontNegrita);

			cell = row.createCell(3);
			cell.setCellStyle(negrito);
			//total dos pagamentos
			cell.setCellValue(soma_total_pagamentos);

			cell = row.createCell(4);
			cell.setCellStyle(textStyle);
			cell.setCellValue("COBERTURA TOTAL:");
			
			double peso_total_cobertura_concluida =  peso_total_cobertura_efetuados
					- peso_total_cobertura_transferencia_negativa + peso_total_cobertura_transferencia_positiva;
			NumberFormat z = NumberFormat.getNumberInstance();

			cell = row.createCell(5);
			cell.setCellStyle(negrito);
			cell.setCellValue(z.format(peso_total_cobertura_concluida) + " sacos");
			
			row = sheet3.createRow(rownum += 1);
			cellnum = 0;
			
			cell = row.createCell(2);
			cell.setCellStyle(textStyle);
			cell.setCellValue("VALOR RESTANTE:");

			cell = row.createCell(3);
			cell.setCellStyle(negrito);
			//total dos pagamentos restante
			valor_total_pagamentos_restantes = valor_total_pagamentos - valor_total_pagamentos_efetuados
					+ valor_total_transferencias_retiradas - valor_total_transferencias_recebidas;
			String valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos_restantes);

			cell.setCellValue(valor);
			
			cell = row.createCell(4);
			cell.setCellStyle(textStyle);
			cell.setCellValue("COBERTURA RESTANTE:");
			
			peso_total_cobertura_restante = peso_total_cobertura - peso_total_cobertura_efetuados
					+ peso_total_cobertura_transferencia_negativa - peso_total_cobertura_transferencia_positiva;
			
			cell = row.createCell(5);
			cell.setCellStyle(negrito);
			cell.setCellValue(z.format(peso_total_cobertura_restante) + " sacos");
			
			//adicionais
			/******************* inicio adicionais ***********************/
			rownum = rownum +=2;
			row = sheet3.createRow(rownum);
			sheet3.addMergedRegion(new CellRangeAddress(rownum, rownum, 0, 2));

			
			String texto_total = "Total do Contrato: "; 
			valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos);
			texto_total += valor;
			texto_total += " Cobre: ";
			texto_total += z.format(peso_total_cobertura) + " sacos";

			cell = row.createCell(0);
			cell.setCellStyle(textStyle);
			cell.setCellValue(texto_total);
			
			rownum = rownum += 1;
			row = sheet3.createRow(rownum);
			sheet3.addMergedRegion(new CellRangeAddress(rownum, rownum, 0, 2));

			String texto_efetuados = "Efetuados: ";
			valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos_efetuados);
			texto_efetuados += valor;
			texto_efetuados += " Cobre: ";
			texto_efetuados += z.format(peso_total_cobertura_efetuados) + " sacos";
			
			cell = row.createCell(0);
			cell.setCellStyle(textStyle);
			cell.setCellValue(texto_efetuados);
			
			//status
			cell = row.createCell(3);
			cell.setCellStyle(celula_fundo_verde_texto_branco);
			cell.setCellValue("Status do Pagamento");
			sheet3.addMergedRegion(new CellRangeAddress(rownum, rownum, 3, 6));


			rownum = rownum += 1;
			row = sheet3.createRow(rownum);
			if(incluir_transferencias) {
			//transferencias negativas
			
			sheet3.addMergedRegion(new CellRangeAddress(rownum, rownum, 0, 2));
			
			String texto_transferencias_negativas = "Transferencias:(-) ";
			valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_transferencias_retiradas);
			texto_transferencias_negativas += valor;
			texto_transferencias_negativas += " Cobre: ";
			texto_transferencias_negativas += z.format(peso_total_cobertura_transferencia_negativa) + " sacos";

			cell = row.createCell(0);
			cell.setCellStyle(textStyle);
			cell.setCellValue(texto_transferencias_negativas);
			}
			//status
			double diferenca = (valor_total_pagamentos_efetuados - valor_total_transferencias_retiradas
					+ valor_total_transferencias_recebidas) - valor_total_pagamentos;
			String status_pagamento = "Pagamentos: ";
			if (diferenca == 0) {
				status_pagamento += "Pagamento Concluído";
			} else if (diferenca > 0) {
				status_pagamento += "Excedeu em " + NumberFormat.getCurrencyInstance(ptBr).format(diferenca);

			} else if (diferenca < 0) {
				status_pagamento += "Incompleto, falta " + NumberFormat.getCurrencyInstance(ptBr).format(diferenca);

			}
			
			cell = row.createCell(3);
			cell.setCellStyle(celula_fundo_verde_texto_branco);
			cell.setCellValue(status_pagamento);
			sheet3.addMergedRegion(new CellRangeAddress(rownum, rownum, 3, 6));
			
			rownum = rownum += 1;
			row = sheet3.createRow(rownum);
			
			if(incluir_transferencias) {
			//transferencias positivas
			
			sheet3.addMergedRegion(new CellRangeAddress(rownum, rownum, 0, 2));				

			String texto_transferencias_positivas = "Transferencias:(+) ";
			valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_transferencias_recebidas);
			texto_transferencias_positivas += valor;
			texto_transferencias_positivas += " Cobre: ";
			texto_transferencias_positivas += z.format(peso_total_cobertura_transferencia_positiva) + " sacos";

			cell = row.createCell(0);
			cell.setCellStyle(textStyle);
			cell.setCellValue(texto_transferencias_positivas);
			}
			String status_cobertura = "Cobertura: ";
			double diferenca_pesos = peso_total_cobertura_restante;

			if (diferenca_pesos == 0 || diferenca_pesos == -0) {
				status_cobertura += "Todos os sacos foram pagos";
			} else if (diferenca_pesos < 0) {
				status_cobertura += "Excedeu em " + z.format(diferenca_pesos) + " Sacos";

			} else if (diferenca_pesos > 0) {
				status_cobertura += "Incompleto, falta pagar " + z.format(diferenca_pesos) + " Sacos";

			}
			cell = row.createCell(3);
			cell.setCellStyle(celula_fundo_verde_texto_branco);
			cell.setCellValue(status_cobertura);
			sheet3.addMergedRegion(new CellRangeAddress(rownum, rownum, 3, 6));
			
			//comissão
			
			if(incluir_comissao) {
			
			rownum = rownum += 1;
			row = sheet3.createRow(rownum);
			sheet3.addMergedRegion(new CellRangeAddress(rownum, rownum, 0, 2));				

			String texto_comissao = "Comissão: ";
			valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_comissao);
			texto_comissao += valor;
			texto_comissao += " Cobre: ";
			texto_comissao += z.format(peso_total_cobertura_comissao) + " sacos";

			cell = row.createCell(0);
			cell.setCellStyle(textStyle);
			cell.setCellValue(texto_comissao);
			}
		
			
			//concluidos
			
			rownum = rownum += 1;
			row = sheet3.createRow(rownum);
			sheet3.addMergedRegion(new CellRangeAddress(rownum, rownum, 0, 2));				

			String texto_concluida = "Concluída:";

			double valor_total_pagamentos_concluidos = valor_total_pagamentos_efetuados - valor_total_transferencias_retiradas + valor_total_transferencias_recebidas; 
			valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos_concluidos);
			texto_concluida += valor;
			texto_concluida += " Cobre: ";
			 peso_total_cobertura_concluida =  peso_total_cobertura_efetuados
						- peso_total_cobertura_transferencia_negativa + peso_total_cobertura_transferencia_positiva;
			texto_concluida += z.format(peso_total_cobertura_concluida) + " sacos";

		
			cell = row.createCell(0);
			cell.setCellStyle(textStyle);
			cell.setCellValue(texto_concluida);
			
			
			//restante
			
			rownum = rownum += 1;
			row = sheet3.createRow(rownum);
			sheet3.addMergedRegion(new CellRangeAddress(rownum, rownum, 0, 2));				

			String texto_restante = " Restante:";

			valor_total_pagamentos_restantes = valor_total_pagamentos - valor_total_pagamentos_efetuados
					+ valor_total_transferencias_retiradas - valor_total_transferencias_recebidas;
			valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos_restantes);
			texto_restante += valor;
			texto_restante += " Cobre: ";
			peso_total_cobertura_restante = peso_total_cobertura - peso_total_cobertura_efetuados
			+ peso_total_cobertura_transferencia_negativa - peso_total_cobertura_transferencia_positiva;
			texto_restante += z.format(peso_total_cobertura_restante) + " sacos";

		
			cell = row.createCell(0);
			cell.setCellStyle(textStyle);
			cell.setCellValue(texto_restante);
			
			
			
			/*************fim adicionai*************************/
			rownum += 2;
			int column = 0;
			int contador_comprovantes = 0;

			// adicionar comprovantes
			if (incluir_comprovantes_pagamentos) {
				// adicionar comprovantes
				// adicionar comprovantes
				boolean linha2_criada = false;
				boolean linha3_criada = false;
				boolean linha4_criada = false;
				boolean linha5_criada = false;
				boolean linha6_criada = false;
				boolean linha7_criada = false;
				boolean linha8_criada = false;
				boolean linha9_criada = false;
				boolean linha10_criada = false;

				for (CadastroContrato.CadastroPagamentoContratual pag : lista_pagamentos_contratuais) {
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
							String caminho_salvar = unidade_base_dados + "\\"
									+ contrato_local.getCaminho_diretorio_contrato();
							String caminho_completo = caminho_salvar + "\\comprovantes\\"
									+ comprovante.getNome_arquivo();

							try {
								if (contador_comprovantes <= 3) {
									rownum = rownum;
								} else if (contador_comprovantes <= 7) {
									if (!linha2_criada) {
										rownum += 13;
										linha2_criada = true;
									}

								} else if (contador_comprovantes <= 11) {
									if (!linha3_criada) {
										rownum += 13;
										linha3_criada = true;
									}

								} else if (contador_comprovantes <= 15) {
									if (!linha4_criada) {
										rownum += 13;
										linha4_criada = true;
									}

								} else if (contador_comprovantes <= 19) {
									if (!linha5_criada) {
										rownum += 13;
										linha5_criada = true;
									}

								} else if (contador_comprovantes <= 23) {
									if (!linha6_criada) {
										rownum += 13;
										linha6_criada = true;
									}

								} else if (contador_comprovantes <= 27) {
									if (!linha7_criada) {
										rownum += 13;
										linha7_criada = true;
									}

								} else if (contador_comprovantes <= 31) {
									if (!linha8_criada) {
										rownum += 13;
										linha8_criada = true;
									}

								} else if (contador_comprovantes <= 35) {
									if (!linha9_criada) {
										rownum += 13;
										linha9_criada = true;
									}

								} else if (contador_comprovantes <= 39) {
									if (!linha10_criada) {
										rownum += 13;
										linha10_criada = true;
									}

								}
								InputStream inputStream = new FileInputStream(caminho_completo);

								byte[] imageBytes = IOUtils.toByteArray(inputStream);

								int pictureureIdx = workbook.addPicture(imageBytes, Workbook.PICTURE_TYPE_PNG);

								inputStream.close();

								CreationHelper helper = workbook.getCreationHelper();

								Drawing drawing = sheet3.createDrawingPatriarch();

								ClientAnchor anchor = helper.createClientAnchor();

								anchor.setCol1(column);
								anchor.setCol2(column + 2);
								anchor.setRow1(rownum);
								anchor.setRow2(rownum + 12);

								drawing.createPicture(anchor, pictureureIdx);

								column += 2;

								if (column > 7)
									column = 0;

							} catch (IOException e1) {
								// TODO Auto-generated catch block
								JOptionPane.showMessageDialog(null, "Erro ao anexar imagem no xlsx");
								e1.printStackTrace();
							}

						}

					}
					contador_comprovantes++;

				}
			}
		}

		try {

			new JFXPanel();
			Platform.runLater(() -> {

				// pegar ultima pasta
				ManipularTxt manipular_ultima_pasta = new ManipularTxt();
				String ultima_pasta = manipular_ultima_pasta
						.lerArquivo(new File("C:\\ProgramData\\E-Contract\\configs\\ultima_pasta.txt"));

				FileChooser d = new FileChooser();

				d.setInitialDirectory(new File(ultima_pasta));
				d.getExtensionFilters().addAll(

						new FileChooser.ExtensionFilter("Excel", "*.xls"));
				File file = d.showSaveDialog(new Stage());
				String caminho_arquivo = "";
				if (file != null) {
					caminho_arquivo = file.getAbsolutePath();

					manipular_ultima_pasta.rescreverArquivo(
							new File("C:\\ProgramData\\E-Contract\\configs\\ultima_pasta.txt"), file.getParent());
					// Escrevendo o arquivo em disco
					FileOutputStream out;
					try {
						out = new FileOutputStream(file);
						workbook.write(out);
						workbook.close();
						out.close();
						// workbook.close();

						Runtime.getRuntime().exec("explorer " + file.getAbsolutePath());

						System.out.println("Success!!");
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

			});

		} catch (Exception k) {
			k.printStackTrace();
		}

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

	public String incluir_legenda_transferencias_negativas(
			CadastroContrato.CadastroTransferenciaPagamentoContratual transferencia) {
		String texto = "";

		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
		CadastroContrato contrato = gerenciar.getContrato(transferencia.getId_contrato_destinatario());

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

		texto = texto + "\n";

		return texto;

	}

	public String incluir_legenda_transferencias_positivas(CadastroTransferenciaPagamentoContratual transferencia) {

		String texto = "";

		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
		CadastroContrato contrato = gerenciar.getContrato(transferencia.getId_contrato_remetente());

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

		return texto;

	}

}
