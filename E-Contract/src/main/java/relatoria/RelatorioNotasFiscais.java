package main.java.relatoria;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.RecebimentoCompleto;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.outros.DadosGlobais;
import main.java.outros.GetData;
import main.java.tratamento_proprio.Log;

public class RelatorioNotasFiscais {

	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private ArrayList<RecebimentoCompleto> lista_recebimentos;
	
	public RelatorioNotasFiscais(ArrayList<RecebimentoCompleto> _lista_recebimentos) {
		getDadosGlobais();
		this.lista_recebimentos = _lista_recebimentos;
	}
	
	public HSSFWorkbook prepararExcel() {
		HSSFWorkbook workbook = new HSSFWorkbook();

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

		// estilo para celula texto alinhado a esquerda
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
		numberStyle.setDataFormat(numberFormat.getFormat("R$ #,##0.00"));
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

		HSSFFont newFont_preta = workbook.createFont();
		newFont_preta.setColor(IndexedColors.BLACK.getIndex());
		newFont_preta.setFontName("Calibri");
		newFont_preta.setItalic(false);
		newFont_preta.setFontHeight((short) (11 * 20));

		CellStyle celula_fundo_branco_texto_preto = workbook.createCellStyle();
		celula_fundo_branco_texto_preto.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_branco_texto_preto.setVerticalAlignment(VerticalAlignment.CENTER);
		celula_fundo_branco_texto_preto.setFont(newFont_preta);

		CellStyle celula_fundo_branco_texto_preto_a_esquerda = workbook.createCellStyle();
		celula_fundo_branco_texto_preto_a_esquerda.setAlignment(HorizontalAlignment.LEFT);
		celula_fundo_branco_texto_preto_a_esquerda.setVerticalAlignment(VerticalAlignment.CENTER);
		celula_fundo_branco_texto_preto_a_esquerda.setFont(newFont_preta);

		// estilo para cabecalho fundo verde
		CellStyle celula_fundo_verde = workbook.createCellStyle();
		celula_fundo_verde.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		celula_fundo_verde.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		celula_fundo_verde.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_verde.setVerticalAlignment(VerticalAlignment.CENTER);
		celula_fundo_verde.setFont(newFont);

		// celula para numero alinhado ao centro
		CellStyle pesoStyle = workbook.createCellStyle();
		pesoStyle.setDataFormat(numberFormat.getFormat("#,##0.00"));
		pesoStyle.setAlignment(HorizontalAlignment.CENTER);
		pesoStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle valorStyleFundoVerdeTextoBranco = workbook.createCellStyle();
		valorStyleFundoVerdeTextoBranco.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		valorStyleFundoVerdeTextoBranco.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		valorStyleFundoVerdeTextoBranco.setAlignment(HorizontalAlignment.CENTER);
		valorStyleFundoVerdeTextoBranco.setVerticalAlignment(VerticalAlignment.CENTER);
		valorStyleFundoVerdeTextoBranco.setDataFormat(numberFormat.getFormat("R$ #,##0.00"));
		valorStyleFundoVerdeTextoBranco.setFont(newFont_branca);

		CellStyle numberStyleFundoVerdeTextoBranco = workbook.createCellStyle();
		numberStyleFundoVerdeTextoBranco.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		numberStyleFundoVerdeTextoBranco.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		numberStyleFundoVerdeTextoBranco.setAlignment(HorizontalAlignment.CENTER);
		numberStyleFundoVerdeTextoBranco.setVerticalAlignment(VerticalAlignment.CENTER);
		numberStyleFundoVerdeTextoBranco.setDataFormat(numberFormat.getFormat("#,##0.00"));

		numberStyleFundoVerdeTextoBranco.setFont(newFont_branca);

		int rownum = 0;
		int cellnum = 0;
		Cell cell;
		Row row;

		GetData data = new GetData();
		String data_criacao = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String criador = "Relatório de Controle de Notas Fiscais" + " por " + login.getNome() + " " + login.getSobrenome() + " em "
				+ data_criacao + " ás " + data.getHora();

		HSSFSheet sheet = workbook.createSheet("Relatorio de Controle de Notas Fiscais");

		// Definindo alguns padroes de layout
		sheet.setDefaultColumnWidth(25);
		sheet.setDefaultRowHeight((short) 400);

		row = sheet.createRow(rownum);
		cell = row.createCell(cellnum);
		cell.setCellStyle(celula_fundo_branco_texto_preto_a_esquerda);
		cell.setCellValue(criador);

		sheet.addMergedRegion(new CellRangeAddress(0, 0, cellnum, 6));
		
		rownum+=1;
		row = sheet.createRow(rownum);


		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("CONTRATO".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("ROMANEIO".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("PESO".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("DATA".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("Nº NF VENDA".toUpperCase());
		
		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("Nº NF REMESSA".toUpperCase());
		
		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("PRODUTO".toUpperCase());
		
		
		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("PESO NF(KGS)".toUpperCase());
		

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("VALOR NF".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("EMITENTE".toUpperCase());
		
		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("DESTINATARIO".toUpperCase());
				
	
		int primeira_linha = rownum++;
		int ultima_linha = primeira_linha;

		sheet.setAutoFilter(CellRangeAddress.valueOf("A2:K2"));
		
		for(RecebimentoCompleto recebimento : lista_recebimentos) {
			
			cellnum = 0;
			row = sheet.createRow(rownum);

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_branco_texto_preto);
			cell.setCellValue(recebimento.getContrato().getCodigo());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_branco_texto_preto);
			cell.setCellValue(recebimento.getCodigo_romaneio());
			
			cell = row.createCell(cellnum++);
			cell.setCellStyle(pesoStyle);
			cell.setCellValue(recebimento.getPeso_romaneio());
			
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_branco_texto_preto);
			cell.setCellValue(recebimento.getData_recebimento());
			
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_branco_texto_preto);
			cell.setCellValue(recebimento.getCodigo_nf_venda());
			
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_branco_texto_preto);
			cell.setCellValue(recebimento.getCodigo_nf_remessa());
			
			
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_branco_texto_preto);
			cell.setCellValue(recebimento.getContrato().getModelo_safra().getProduto().getNome_produto());
			
			cell = row.createCell(cellnum++);
			cell.setCellStyle(pesoStyle);
			cell.setCellValue(recebimento.getPeso_nf_venda());
			
			
			cell = row.createCell(cellnum++);
			cell.setCellStyle(numberStyle);
			cell.setCellValue(recebimento.getValor_nf_venda().doubleValue());
			
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_branco_texto_preto);
			cell.setCellValue(recebimento.getNome_remetente_nf_venda());
			
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_branco_texto_preto);
			cell.setCellValue(recebimento.getNome_destinatario_nf_venda());
			
			
			rownum++;
			ultima_linha = rownum;
			
		}
		
		// pular linha
				rownum += 1;

				// somatorias
				FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
				row = sheet.createRow(rownum += 1);
				cellnum = 0;


				cell = row.createCell(2);
				cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
				cell.setCellType(CellType.FORMULA);
				String formula = "SUM(C" + primeira_linha + ":C" + ultima_linha + ")";
				cell.setCellFormula(formula);


				cell = row.createCell(7);
				cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
				cell.setCellType(CellType.FORMULA);
				 formula = "SUM(H" + primeira_linha + ":H" + ultima_linha + ")";
				cell.setCellFormula(formula);
				
				
				cell = row.createCell(8);
				cell.setCellStyle(valorStyleFundoVerdeTextoBranco);
				cell.setCellType(CellType.FORMULA);
				 formula = "SUM(I" + primeira_linha + ":I" + ultima_linha + ")";
				cell.setCellFormula(formula);
				
				//em sacos
				row = sheet.createRow(rownum += 1);
				cellnum = 0;


				cell = row.createCell(2);
				cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
				cell.setCellType(CellType.FORMULA);
				 formula = "SUM(C" + primeira_linha + ":C" + ultima_linha + ")/60";
				cell.setCellFormula(formula);


				cell = row.createCell(7);
				cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
				cell.setCellType(CellType.FORMULA);
				 formula = "SUM(H" + primeira_linha + ":H" + ultima_linha + ")/60";
				cell.setCellFormula(formula);
				
				
				
		

		return workbook;


	}
	
	
	
	public void getDadosGlobais() {
		// gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		GerenciadorLog = dados.getGerenciadorLog();
		configs_globais = dados.getConfigs_globais();

		// usuario logado
		login = dados.getLogin();

	}

}
