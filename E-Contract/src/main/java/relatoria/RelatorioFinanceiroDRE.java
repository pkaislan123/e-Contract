/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  org.apache.commons.io.IOUtils
 *  org.apache.poi.hssf.usermodel.HSSFCellStyle
 *  org.apache.poi.hssf.usermodel.HSSFCreationHelper
 *  org.apache.poi.hssf.usermodel.HSSFDataFormat
 *  org.apache.poi.hssf.usermodel.HSSFFont
 *  org.apache.poi.hssf.usermodel.HSSFPalette
 *  org.apache.poi.hssf.usermodel.HSSFPatriarch
 *  org.apache.poi.hssf.usermodel.HSSFRow
 *  org.apache.poi.hssf.usermodel.HSSFSheet
 *  org.apache.poi.hssf.usermodel.HSSFWorkbook
 *  org.apache.poi.hssf.util.HSSFColor
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.ClientAnchor
 *  org.apache.poi.ss.usermodel.FillPatternType
 *  org.apache.poi.ss.usermodel.Font
 *  org.apache.poi.ss.usermodel.HorizontalAlignment
 *  org.apache.poi.ss.usermodel.IndexedColors
 *  org.apache.poi.ss.usermodel.VerticalAlignment
 *  org.apache.poi.ss.util.CellRangeAddress
 */
package main.java.relatoria;

import java.awt.Window;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CentroCusto;
import main.java.cadastros.DreSimples;
import main.java.cadastros.InstituicaoBancaria;
import main.java.gui.TelaVizualizarPdf;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.manipular.ConverterPdf;
import main.java.outros.DadosGlobais;
import main.java.tratamento_proprio.Log;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFCreationHelper;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

public class RelatorioFinanceiroDRE {
    String[] meses = new String[]{"JANEIRO", "FEVEREIRO", "MAR\u00c7O", "ABRIL", "MAIO", "JUNHO", "JULHO", "AGOSTO", "SETEMBRO", "OUTUBRO", "NOVEMBRO", "DEZEMBRO"};
    Window isto;
    private Log GerenciadorLog;
    private CadastroLogin login;
    private ConfiguracoesGlobais configs_globais;

    public void RelatorioFinanceiroDRERC(Window janela_pai, ArrayList<DreSimples> dreList, CentroCusto centro_custo, InstituicaoBancaria instituicao_bancaria, int ano_fiscal) {
        this.getDadosGlobais();
        this.isto = janela_pai;
        this.gerarPdf(this.prepararRelatorioDreRegimeCaixa(dreList, centro_custo, instituicao_bancaria, ano_fiscal));
    }

    public void gerarPdf(HSSFWorkbook workbook) {
        File file = new File("c:\\temp\\relatorio_temp.xls");
        String caminho_arquivo = "";
        try {
            FileOutputStream out = new FileOutputStream(file);
            workbook.write((OutputStream)out);
            workbook.close();
            out.close();
            ConverterPdf converter_pdf = new ConverterPdf();
            String pdf_alterado = converter_pdf.excel_pdf_file2(file.getAbsolutePath().replaceAll(".xls", ""));
            TelaVizualizarPdf telaVizualizarPdf = new TelaVizualizarPdf(null, this.isto, null, pdf_alterado, null, this.isto);
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public HSSFWorkbook prepararRelatorioDreRegimeCaixa(ArrayList<DreSimples> dreList, CentroCusto centro_custo, InstituicaoBancaria instituicao_bancaria, int ano_fiscal) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Exporta\u00e7\u00e3o de Dados DRE Regime de Caixa");
        sheet.setDefaultColumnWidth(25);
        sheet.setDefaultRowHeight((short)400);
        sheet.getPrintSetup().setLandscape(false);
        sheet.getPrintSetup().setPaperSize((short)9);
        int rownum = 0;
        int cellnum = 0;
        HSSFDataFormat numberFormat = workbook.createDataFormat();
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        HSSFCellStyle textStyle = workbook.createCellStyle();
        textStyle.setAlignment(HorizontalAlignment.CENTER);
        textStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        HSSFCellStyle numberStyle = workbook.createCellStyle();
        numberStyle.setDataFormat(numberFormat.getFormat("#,##0.00"));
        numberStyle.setAlignment(HorizontalAlignment.CENTER);
        numberStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        HSSFCellStyle negrito = workbook.createCellStyle();
        negrito.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        negrito.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        negrito.setAlignment(HorizontalAlignment.CENTER);
        negrito.setVerticalAlignment(VerticalAlignment.CENTER);
        HSSFFont newFontNegrita = workbook.createFont();
        newFontNegrita.setBold(true);
        newFontNegrita.setColor(IndexedColors.BLACK.getIndex());
        newFontNegrita.setFontName("Arial");
        newFontNegrita.setItalic(true);
        newFontNegrita.setFontHeight((short)220);
        negrito.setFont((Font)newFontNegrita);
        HSSFCellStyle negrito_esquerda = workbook.createCellStyle();
        negrito_esquerda.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        negrito_esquerda.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        negrito_esquerda.setAlignment(HorizontalAlignment.LEFT);
        negrito_esquerda.setVerticalAlignment(VerticalAlignment.CENTER);
        HSSFFont newFontNegritaEsquerda = workbook.createFont();
        newFontNegritaEsquerda.setBold(true);
        newFontNegritaEsquerda.setColor(IndexedColors.BLACK.getIndex());
        newFontNegritaEsquerda.setFontName("Arial");
        newFontNegritaEsquerda.setItalic(true);
        newFontNegritaEsquerda.setFontHeight((short)220);
        negrito_esquerda.setFont((Font)newFontNegritaEsquerda);
        HSSFCellStyle negrito_direita = workbook.createCellStyle();
        negrito_direita.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        negrito_direita.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        negrito_direita.setAlignment(HorizontalAlignment.RIGHT);
        negrito_direita.setVerticalAlignment(VerticalAlignment.CENTER);
        negrito_direita.setFont((Font)newFontNegrita);
        HSSFCellStyle valorStyle = workbook.createCellStyle();
        valorStyle.setDataFormat(numberFormat.getFormat("R$ #,##0.00"));
        valorStyle.setAlignment(HorizontalAlignment.CENTER);
        valorStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        HSSFCellStyle celula_fundo_laranja = workbook.createCellStyle();
        celula_fundo_laranja.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        celula_fundo_laranja.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        celula_fundo_laranja.setAlignment(HorizontalAlignment.CENTER);
        celula_fundo_laranja.setVerticalAlignment(VerticalAlignment.CENTER);
        HSSFFont newFont = workbook.createFont();
        newFont.setBold(true);
        newFont.setColor(IndexedColors.BLACK.getIndex());
        newFont.setFontName("Calibri");
        newFont.setItalic(false);
        newFont.setFontHeight((short)275);
        celula_fundo_laranja.setFont((Font)newFont);
        HSSFCellStyle celula_number_amarelo_texto_preto = workbook.createCellStyle();
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
        newFont_blabk.setFontHeight((short)220);
        celula_number_amarelo_texto_preto.setFont((Font)newFont_blabk);
        HSSFCellStyle celula_fundo_laranja_texto_branco = workbook.createCellStyle();
        celula_fundo_laranja_texto_branco.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        celula_fundo_laranja_texto_branco.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        celula_fundo_laranja_texto_branco.setAlignment(HorizontalAlignment.CENTER);
        celula_fundo_laranja_texto_branco.setVerticalAlignment(VerticalAlignment.CENTER);
        HSSFFont newFont_branca = workbook.createFont();
        newFont_branca.setBold(true);
        newFont_branca.setColor(IndexedColors.WHITE.getIndex());
        newFont_branca.setFontName("Calibri");
        newFont_branca.setItalic(false);
        newFont_branca.setFontHeight((short)220);
        Locale ptBr = new Locale("pt", "BR");
        celula_fundo_laranja_texto_branco.setFont((Font)newFont_branca);
        HSSFFont newFont_titulo = workbook.createFont();
        newFont_titulo.setBold(true);
        newFont_titulo.setColor(IndexedColors.BLACK.getIndex());
        newFont_titulo.setFontName("Calibri");
        newFont_titulo.setItalic(true);
        newFont_titulo.setFontHeight((short)352);
        HSSFCellStyle celula_titulo = workbook.createCellStyle();
        celula_titulo.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        celula_titulo.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        celula_titulo.setAlignment(HorizontalAlignment.CENTER);
        celula_titulo.setVerticalAlignment(VerticalAlignment.CENTER);
        celula_titulo.setFont((Font)newFont_titulo);
        HSSFFont newFontsub_titulo = workbook.createFont();
        newFontsub_titulo.setBold(false);
        newFontsub_titulo.setColor(IndexedColors.BLACK.getIndex());
        newFontsub_titulo.setFontName("Calibri");
        newFontsub_titulo.setItalic(false);
        newFontsub_titulo.setFontHeight((short)220);
        HSSFCellStyle sub_celula_titulo = workbook.createCellStyle();
        sub_celula_titulo.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        sub_celula_titulo.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        sub_celula_titulo.setAlignment(HorizontalAlignment.LEFT);
        sub_celula_titulo.setVerticalAlignment(VerticalAlignment.CENTER);
        sub_celula_titulo.setFont((Font)newFontsub_titulo);
        HSSFFont newFontNegritaAzul = workbook.createFont();
        newFontNegritaAzul.setBold(true);
        newFontNegritaAzul.setColor(IndexedColors.BLUE.getIndex());
        newFontNegritaAzul.setFontName("Arial");
        newFontNegritaAzul.setItalic(true);
        newFontNegritaAzul.setFontHeight((short)220);
        HSSFFont newFontNegritoVermelho = workbook.createFont();
        newFontNegritoVermelho.setBold(true);
        newFontNegritoVermelho.setColor(IndexedColors.RED.getIndex());
        newFontNegritoVermelho.setFontName("Arial");
        newFontNegritoVermelho.setItalic(true);
        newFontNegritoVermelho.setFontHeight((short)220);
        HSSFFont newFontNegritoVerde = workbook.createFont();
        newFontNegritoVerde.setBold(true);
        newFontNegritoVerde.setColor(IndexedColors.GREEN.getIndex());
        newFontNegritoVerde.setFontName("Arial");
        newFontNegritoVerde.setItalic(true);
        newFontNegritoVerde.setFontHeight((short)220);
        HSSFCellStyle textStyleAzul = workbook.createCellStyle();
        textStyleAzul.setAlignment(HorizontalAlignment.CENTER);
        textStyleAzul.setVerticalAlignment(VerticalAlignment.CENTER);
        textStyleAzul.setFont((Font)newFontNegritaAzul);
        HSSFCellStyle textStyleVerde = workbook.createCellStyle();
        textStyleVerde.setAlignment(HorizontalAlignment.CENTER);
        textStyleVerde.setVerticalAlignment(VerticalAlignment.CENTER);
        textStyleVerde.setFont((Font)newFontNegritoVerde);
        HSSFCellStyle textStyleVermelho = workbook.createCellStyle();
        textStyleVermelho.setAlignment(HorizontalAlignment.CENTER);
        textStyleVermelho.setVerticalAlignment(VerticalAlignment.CENTER);
        textStyleVermelho.setFont((Font)newFontNegritoVermelho);
        HSSFRow row = sheet.createRow(rownum++);
        Cell cell = row.createCell(cellnum++);
        cell.setCellStyle((CellStyle)celula_titulo);
        cell.setCellValue("Relat\u00f3rio DRE Regime de Caixa");
        int i = 1;
        while (i < 4) {
            cell = row.createCell(cellnum++);
            cell.setCellStyle((CellStyle)celula_titulo);
            cell.setCellValue("");
            ++i;
        }
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
        cellnum = 0;
        row = sheet.createRow(rownum++);
        row = sheet.createRow(rownum++);
        cell = row.createCell(cellnum++);
        cell.setCellStyle((CellStyle)sub_celula_titulo);
        cell.setCellValue("Centro de Custo: " + (centro_custo != null ? centro_custo.getNome_centro_custo() : "TODOS"));
        i = 1;
        while (i < 3) {
            cell = row.createCell(cellnum++);
            cell.setCellStyle((CellStyle)sub_celula_titulo);
            cell.setCellValue("");
            ++i;
        }
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 2));
        row = sheet.createRow(rownum++);
        cellnum = 0;
        cell = row.createCell(cellnum++);
        cell.setCellStyle((CellStyle)sub_celula_titulo);
        cell.setCellValue("Institui\u00e7\u00e3o Banc\u00e1ria: " + (instituicao_bancaria != null ? instituicao_bancaria.getNome_instituicao_bancaria() : "TODAS"));
        i = 1;
        while (i < 2) {
            cell = row.createCell(cellnum++);
            cell.setCellStyle((CellStyle)sub_celula_titulo);
            cell.setCellValue("");
            ++i;
        }
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 2));
        row = sheet.createRow(rownum++);
        cellnum = 0;
        cell = row.createCell(cellnum++);
        cell.setCellStyle((CellStyle)sub_celula_titulo);
        cell.setCellValue("Ano Fiscal: " + ano_fiscal);
        i = 1;
        while (i < 2) {
            cell = row.createCell(cellnum++);
            cell.setCellStyle((CellStyle)sub_celula_titulo);
            cell.setCellValue("");
            ++i;
        }
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 2));
        row = sheet.createRow(rownum++);
        try {
            String imgFile = String.valueOf(this.configs_globais.getProps().getPasta_instalacao()) + "\\imagens\\logo_para_relatorio.png";
            FileInputStream inputStream = new FileInputStream(imgFile);
            byte[] imageBytes = IOUtils.toByteArray((InputStream)inputStream);
            int pictureureIdx = workbook.addPicture(imageBytes, 6);
            ((InputStream)inputStream).close();
            HSSFCreationHelper helper = workbook.getCreationHelper();
            HSSFPatriarch drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = helper.createClientAnchor();
            anchor.setRow1(0);
            anchor.setRow2(4);
            anchor.setCol1(4);
            anchor.setCol2(7);
            drawing.createPicture(anchor, pictureureIdx);
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
        rownum = 6;
        cellnum = 0;
        row = sheet.createRow(rownum++);
        cell = row.createCell(cellnum++);
        cell.setCellStyle(this.retornarCelulaFundoPersonalizado(128, 128, 0, workbook, true, true));
        cell.setCellValue("M\u00eaS");
        cell = row.createCell(cellnum++);
        cell.setCellStyle(this.retornarCelulaFundoPersonalizado(255, 165, 0, workbook, true, true));
        cell.setCellValue("SALDO INICIAL");
        cell = row.createCell(cellnum++);
        cell.setCellStyle(this.retornarCelulaFundoPersonalizado(0, 100, 0, workbook, true, true));
        cell.setCellValue("RECEITAS");
        cell = row.createCell(cellnum++);
        cell.setCellStyle(this.retornarCelulaFundoPersonalizado(160, 82, 45, workbook, true, true));
        cell.setCellValue("DESPESAS".toUpperCase());
        cell = row.createCell(cellnum++);
        cell.setCellStyle(this.retornarCelulaFundoPersonalizado(0, 191, 255, workbook, true, true));
        cell.setCellValue("TOTAL".toUpperCase());
        cell = row.createCell(cellnum++);
        cell.setCellStyle(this.retornarCelulaFundoPersonalizado(50, 205, 50, workbook, true, true));
        cell.setCellValue("LUCRO".toUpperCase());
        cell = row.createCell(cellnum++);
        cell.setCellStyle(this.retornarCelulaFundoPersonalizado(0, 0, 255, workbook, true, false));
        cell.setCellValue("LUCRATIVIDADE".toUpperCase());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        DecimalFormat df = new DecimalFormat("#,###.00");
        int mes = 0;
        double saldo_inicial = 0.0;
        double receitas = 0.0;
        double despesas = 0.0;
        saldo_inicial = dreList.get(0).getSaldo_inicial();
        for (DreSimples dre : dreList) {
            row = sheet.createRow(rownum++);
            cellnum = 0;
            cell = row.createCell(cellnum++);
            cell.setCellStyle(this.retornarCelulaFundoPersonalizado(0, 191, 255, workbook, true, false));
            cell.setCellValue(this.meses[mes]);
            cell = row.createCell(cellnum++);
            cell.setCellStyle((CellStyle)valorStyle);
            cell.setCellValue(dre.getSaldo_inicial());
            cell = row.createCell(cellnum++);
            cell.setCellStyle((CellStyle)valorStyle);
            cell.setCellValue(dre.getReceitas());
            cell = row.createCell(cellnum++);
            cell.setCellStyle((CellStyle)valorStyle);
            cell.setCellValue(dre.getDespesas());
            cell = row.createCell(cellnum++);
            cell.setCellStyle((CellStyle)valorStyle);
            cell.setCellValue(dre.getTotal());
            cell = row.createCell(cellnum++);
            cell.setCellStyle((CellStyle)valorStyle);
            cell.setCellValue(dre.getLucro());
            cell = row.createCell(cellnum++);
            cell.setCellStyle((CellStyle)textStyle);
            cell.setCellValue(String.valueOf(df.format(dre.getLucratividade())) + "%");
            receitas += dre.getReceitas();
            despesas += dre.getDespesas();
            ++mes;
        }
        sheet.setAutoFilter(CellRangeAddress.valueOf((String)"A4:H4"));
        int i2 = 0;
        while (i2 < 13) {
            sheet.autoSizeColumn(i2);
            ++i2;
        }
        row = sheet.createRow(rownum++);
        cellnum = 0;
        row = sheet.createRow(rownum++);
        cell = row.createCell(cellnum++);
        cell.setCellStyle(this.retornarCelulaFundoPersonalizado(128, 128, 0, workbook, true, false));
        cell.setCellValue("TOTAIS:");
        cell = row.createCell(cellnum++);
        cell.setCellStyle(this.retornarCelulaFundoPersonalizado(255, 165, 0, workbook, true, true));
        cell.setCellValue(saldo_inicial);
        cell = row.createCell(cellnum++);
        cell.setCellStyle(this.retornarCelulaFundoPersonalizado(0, 100, 0, workbook, true, true));
        cell.setCellValue(receitas);
        cell = row.createCell(cellnum++);
        cell.setCellStyle(this.retornarCelulaFundoPersonalizado(160, 82, 45, workbook, true, true));
        cell.setCellValue(despesas);
        double valor_total = saldo_inicial + receitas - -despesas;
        cell = row.createCell(cellnum++);
        cell.setCellStyle(this.retornarCelulaFundoPersonalizado(0, 191, 255, workbook, true, true));
        cell.setCellValue(valor_total);
        double lucro_total = receitas - -despesas;
        cell = row.createCell(cellnum++);
        cell.setCellStyle(this.retornarCelulaFundoPersonalizado(50, 205, 50, workbook, true, true));
        cell.setCellValue(lucro_total);
        double lucratividade_total = lucro_total * 100.0 / receitas;
        cell = row.createCell(cellnum++);
        cell.setCellStyle(this.retornarCelulaFundoPersonalizado(0, 0, 255, workbook, true, false));
        cell.setCellValue(String.valueOf(df.format(lucratividade_total)) + "%");
        return workbook;
    }

    public CellStyle retornarCelulaFundoPersonalizado(int r, int g, int b, HSSFWorkbook workbook, boolean negrito, boolean valor) {
        HSSFDataFormat numberFormat = workbook.createDataFormat();
        HSSFFont newFont_branca = workbook.createFont();
        newFont_branca.setBold(true);
        newFont_branca.setColor(IndexedColors.WHITE.getIndex());
        newFont_branca.setFontName("Calibri");
        newFont_branca.setItalic(false);
        newFont_branca.setFontHeight((short)220);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        HSSFPalette palette = workbook.getCustomPalette();
        HSSFColor myColor = palette.findSimilarColor(r, g, b);
        short palIndex = myColor.getIndex();
        style.setFillForegroundColor(palIndex);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        if (valor) {
            style.setDataFormat(numberFormat.getFormat("R$ #,##0.00"));
        }
        style.setFont((Font)newFont_branca);
        return style;
    }

    public void getDadosGlobais() {
        DadosGlobais dados = DadosGlobais.getInstance();
        this.GerenciadorLog = dados.getGerenciadorLog();
        this.configs_globais = dados.getConfigs_globais();
        this.login = dados.getLogin();
    }
}

