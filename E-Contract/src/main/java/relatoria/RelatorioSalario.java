package main.java.relatoria;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDocument1;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTabStop;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTabJc;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroFuncionario;
import main.java.cadastros.CadastroFuncionarioAdmissao;
import main.java.cadastros.CadastroFuncionarioCalculo;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.Lancamento;
import main.java.cadastros.RegistroAuxiliarHoras;
import main.java.cadastros.RegistroPontoMensalCompleto;
import main.java.conexaoBanco.GerenciarBancoCondicaoPagamentos;
import main.java.cadastros.CadastroSafra;
import main.java.cadastros.CondicaoPagamento;
import main.java.cadastros.Lancamento;
import main.java.gui.TelaEnviarMsgMail;
import main.java.gui.TelaEnviarMsgWhatsapp;
import main.java.gui.TelaVizualizarPdf;
import main.java.gui_internal.TelaFinanceiroLancamentoInternal;
import main.java.manipular.ConverterPdf;
import main.java.manipular.ManipularTxt;

import javax.swing.JTextArea;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import net.miginfocom.swing.MigLayout;
import javax.swing.JRadioButton;
import javax.swing.border.LineBorder;

public class RelatorioSalario extends JDialog {

	private FileChooser fileChooser;
	private JRadioButton rdbtnPdf, rdbtnExcel;
	private String[] ref_cal = { "Sálario Base", "Sálario Líquido", "Sálario Bruto", "Valor Hora Trabalhada",
			"Nenhuma" };
	Locale ptBr = new Locale("pt", "BR");
	private Window isto;
	private String[] ref_valor = { "Porcentagem", "Fixo" };
	private double salario;
	private String mes;
	private int ano;
	private CadastroFuncionarioAdmissao ct_global;
	private CadastroFuncionario funcionario;
	private RegistroAuxiliarHoras registro_global;

	public RelatorioSalario(ArrayList<RegistroPontoMensalCompleto> registros_pontos,
			ArrayList<CadastroFuncionarioCalculo> acrescimos, ArrayList<CadastroFuncionarioCalculo> descontos,
			CadastroFuncionario funcionario, CadastroFuncionarioAdmissao ct_trabalho, String mes, int ano,
			double ultimo_salario, RegistroAuxiliarHoras registro_horas, Window janela_pai) {

		this.funcionario = funcionario;
		this.ct_global = ct_trabalho;
		this.ano = ano;
		this.salario = ultimo_salario;
		this.mes = mes;
		this.registro_global = registro_horas;
		this.isto = janela_pai;

		gerarPdf(prepararOlerite(descontos, acrescimos, registros_pontos, 0));

	}

	
	
	
	public void gerarExcel(HSSFWorkbook workbook) {
		try {

			new JFXPanel();
			Platform.runLater(() -> {

				// pegar ultima pasta
				ManipularTxt manipular_ultima_pasta = new ManipularTxt();
				String ultima_pasta = manipular_ultima_pasta
						.lerArquivo(new File("C:\\ProgramData\\E-Contract\\configs\\ultima_pasta.txt"));
				if (fileChooser == null) {
					fileChooser = new FileChooser();
				}
				fileChooser.setInitialDirectory(new File(ultima_pasta));
				fileChooser.getExtensionFilters().addAll(

						new FileChooser.ExtensionFilter("Excel", "*.xls"));
				File file = fileChooser.showSaveDialog(new Stage());
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

	public void gerarPdf(HSSFWorkbook workbook) {

		File file = new File("c:\\temp\\relatorio_temp.xls");
		String caminho_arquivo = "";

		FileOutputStream out;
		try {
			out = new FileOutputStream(file);
			workbook.write(out);
			workbook.close();
			out.close();
			// workbook.close();
			ConverterPdf converter_pdf = new ConverterPdf();
			String pdf_alterado = converter_pdf.excel_pdf_file2(file.getAbsolutePath().replaceAll(".xls", ""));
			TelaVizualizarPdf vizualizar = new TelaVizualizarPdf(null, isto, null, pdf_alterado, null, isto);

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public HSSFWorkbook prepararRegistroPonto(ArrayList<RegistroPontoMensalCompleto> registros_selecionados, int flag) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Exportação de Dados de Registro de Ponto");

		// Definindo alguns padroes de layout
		sheet.setDefaultColumnWidth(25);
		sheet.setDefaultRowHeight((short) 400);

		int rownum = 0;
		int cellnum = 0;
		Cell cell;
		Row row;

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

		// celula para numero alinhado ao centro
		CellStyle numberStyle = workbook.createCellStyle();
		numberStyle.setDataFormat(numberFormat.getFormat("#,##0.00"));
		numberStyle.setAlignment(HorizontalAlignment.CENTER);
		numberStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		// estilo de celula negrito
		CellStyle negrito = workbook.createCellStyle();
		// textStyle.setAlignment(HorizontalAlignment.CENTER);
		negrito.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		negrito.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		negrito.setAlignment(HorizontalAlignment.CENTER);
		negrito.setVerticalAlignment(VerticalAlignment.CENTER);

		HSSFFont newFontNegrita = workbook.createFont();
		newFontNegrita.setBold(true);
		newFontNegrita.setColor(IndexedColors.BLACK.getIndex());
		newFontNegrita.setFontName("Arial");
		newFontNegrita.setItalic(true);
		newFontNegrita.setFontHeight((short) (11 * 20));

		negrito.setFont(newFontNegrita);

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

		// estilo para celula do tipo numero alinhado ao centro
		CellStyle valorStyle = workbook.createCellStyle();
		valorStyle.setDataFormat(numberFormat.getFormat("R$ #,##0.00"));
		valorStyle.setAlignment(HorizontalAlignment.CENTER);
		valorStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		// estilo para cabecalho fundo laranja
		CellStyle celula_fundo_laranja = workbook.createCellStyle();
		celula_fundo_laranja.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		celula_fundo_laranja.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		celula_fundo_laranja.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_laranja.setVerticalAlignment(VerticalAlignment.CENTER);

		HSSFFont newFont = workbook.createFont();
		newFont.setBold(true);
		newFont.setColor(IndexedColors.BLACK.getIndex());
		newFont.setFontName("Calibri");
		newFont.setItalic(false);
		newFont.setFontHeight((short) (11 * 25));

		celula_fundo_laranja.setFont(newFont);

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

		// estilo para cabecalho fundo laranja
		CellStyle celula_fundo_laranja_texto_branco = workbook.createCellStyle();
		celula_fundo_laranja_texto_branco.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		celula_fundo_laranja_texto_branco.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		celula_fundo_laranja_texto_branco.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_laranja_texto_branco.setVerticalAlignment(VerticalAlignment.CENTER);

		HSSFFont newFont_branca = workbook.createFont();
		newFont_branca.setBold(true);
		newFont_branca.setColor(IndexedColors.WHITE.getIndex());
		newFont_branca.setFontName("Calibri");
		newFont_branca.setItalic(false);
		newFont_branca.setFontHeight((short) (11 * 20));
		Locale ptBr = new Locale("pt", "BR");

		celula_fundo_laranja_texto_branco.setFont(newFont_branca);

		HSSFFont newFont_titulo = workbook.createFont();
		newFont_titulo.setBold(true);
		newFont_titulo.setColor(IndexedColors.BLACK.getIndex());
		newFont_titulo.setFontName("Calibri");
		newFont_titulo.setItalic(true);
		newFont_titulo.setFontHeight((short) (11 * 32));

		// estilo para cabecalho
		CellStyle celula_titulo = workbook.createCellStyle();
		celula_titulo.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		celula_titulo.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		celula_titulo.setAlignment(HorizontalAlignment.CENTER);
		celula_titulo.setVerticalAlignment(VerticalAlignment.CENTER);
		celula_titulo.setFont(newFont_titulo);

		// Configurando as informacoes

		// Configurando as informacoes
		row = sheet.createRow(rownum++);

		// Configurando titulo
		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_titulo);
		cell.setCellValue("Relatório de Registro de Ponto");
		// criar celula de 1 a 5
		for (int i = 1; i < 6; i++) {
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_titulo);
			cell.setCellValue("");

		}
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

		cellnum = 0;

		// Configurando Header
		row = sheet.createRow(rownum++);
		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("DATA");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("DIA DA SEMANA");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("HORA ENTRADA 1".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("HORA SAIDA 1".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("HORA ENTRADA 2".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("HORA SAIDA 2".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("HORA ENTRADA 3".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("HORA SAIDA 3".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("ROTINA DIARIA".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("HORAS TRABALHADAS".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("HORAS EXTRAS".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("HORAS ATRAZO".toUpperCase());

		for (RegistroPontoMensalCompleto rp : registros_selecionados) {
			row = sheet.createRow(rownum++);
			cellnum = 0;

			// data
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(rp.getRp().getData());

			// dia da semana
			String dia_da_semana = "";
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);

			if (rp.getRp().getData() != null && !rp.getRp().getData().equals("")
					&& !rp.getRp().getData().equalsIgnoreCase("TOTAIS")) {
				try {
					DateTimeFormatter formatter = new DateTimeFormatterBuilder().toFormatter(new Locale("pt", "BR"));

					LocalDate data = LocalDate.parse(rp.getRp().getData(), formatter.ofPattern("dd/MM/yyyy"));
					DayOfWeek dia_s = data.getDayOfWeek();
					cell.setCellValue(dia_s.getDisplayName(TextStyle.FULL, new Locale("pt", "BR")).toUpperCase());
				} catch (Exception e) {
					cell.setCellValue("");
				}
			} else {
				cell.setCellValue("");
			}

			// hora entrada1
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			if (!rp.getRp().getEntrada1().equals("00:00"))
				cell.setCellValue(rp.getRp().getEntrada1());
			else
				cell.setCellValue("-");

			// hora saida1
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			if (!rp.getRp().getSaida1().equals("00:00"))
				cell.setCellValue(rp.getRp().getSaida1());
			else
				cell.setCellValue("-");

			// hora entrada 2
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			if (!rp.getRp().getEntrada2().equals("00:00"))
				cell.setCellValue(rp.getRp().getEntrada2());
			else
				cell.setCellValue("-");

			// hora saida2
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			if (!rp.getRp().getSaida2().equals("00:00"))
				cell.setCellValue(rp.getRp().getSaida2());
			else
				cell.setCellValue("-");

			// hora entrada 3
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			if (!rp.getRp().getEntrada3().equals("00:00"))
				cell.setCellValue(rp.getRp().getEntrada3());
			else
				cell.setCellValue("-");

			// hora saida 3
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			if (!rp.getRp().getSaida3().equals("00:00"))
				cell.setCellValue(rp.getRp().getSaida3());
			else
				cell.setCellValue("-");

			// rotina diaria
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			if (!rp.getHora_diaria().equals("00:00"))
				cell.setCellValue(rp.getHora_diaria());
			else
				cell.setCellValue("DESCANSO SEMANAL");

			// horas trabalhadas
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			if (!rp.getHora_trabalhada().equals("00:00"))
				cell.setCellValue(rp.getHora_trabalhada());
			else
				cell.setCellValue("");

			// horas extras
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			if (!rp.getHora_extra().equals("00:00"))
				cell.setCellValue(rp.getHora_extra());
			else
				cell.setCellValue("");

			// horas atrazo
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			if (!rp.getHora_atrazo().equals("00:00"))
				cell.setCellValue(rp.getHora_atrazo());
			else
				cell.setCellValue("");
		}
		sheet.setAutoFilter(CellRangeAddress.valueOf("A2:AF2"));
		for (int i = 0; i < 13; i++) {
			sheet.autoSizeColumn(i);

		}

		return workbook;
	}

	public HSSFWorkbook prepararOlerite(ArrayList<CadastroFuncionarioCalculo> descontos,
			ArrayList<CadastroFuncionarioCalculo> acrescimos, 
			ArrayList<RegistroPontoMensalCompleto> registros_selecionados,
			int flag) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Exportação de Dados de Folha de Pagamento");


		// Definindo alguns padroes de layout
		sheet.setDefaultColumnWidth(25);
		sheet.setDefaultRowHeight((short) 400);

		int rownum = 0;
		int cellnum = 0;
		Cell cell;
		Row row;

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

		// celula para numero alinhado ao centro
		CellStyle numberStyle = workbook.createCellStyle();
		numberStyle.setDataFormat(numberFormat.getFormat("#,##0.00"));
		numberStyle.setAlignment(HorizontalAlignment.CENTER);
		numberStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		// estilo de celula negrito
		CellStyle negrito = workbook.createCellStyle();
		// textStyle.setAlignment(HorizontalAlignment.CENTER);
		negrito.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		negrito.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		negrito.setAlignment(HorizontalAlignment.CENTER);
		negrito.setVerticalAlignment(VerticalAlignment.CENTER);

		HSSFFont newFontNegrita = workbook.createFont();
		newFontNegrita.setBold(true);
		newFontNegrita.setColor(IndexedColors.BLACK.getIndex());
		newFontNegrita.setFontName("Arial");
		newFontNegrita.setItalic(true);
		newFontNegrita.setFontHeight((short) (11 * 20));

		negrito.setFont(newFontNegrita);

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

		// estilo para celula do tipo numero alinhado ao centro
		CellStyle valorStyle = workbook.createCellStyle();
		valorStyle.setDataFormat(numberFormat.getFormat("R$ #,##0.00"));
		valorStyle.setAlignment(HorizontalAlignment.CENTER);
		valorStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		// estilo para cabecalho fundo laranja
		CellStyle celula_fundo_laranja = workbook.createCellStyle();
		celula_fundo_laranja.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		celula_fundo_laranja.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		celula_fundo_laranja.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_laranja.setVerticalAlignment(VerticalAlignment.CENTER);

		HSSFFont newFont = workbook.createFont();
		newFont.setBold(true);
		newFont.setColor(IndexedColors.BLACK.getIndex());
		newFont.setFontName("Calibri");
		newFont.setItalic(false);
		newFont.setFontHeight((short) (11 * 25));

		celula_fundo_laranja.setFont(newFont);

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

		// estilo para cabecalho fundo laranja
		CellStyle celula_fundo_laranja_texto_branco = workbook.createCellStyle();
		celula_fundo_laranja_texto_branco.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		celula_fundo_laranja_texto_branco.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		celula_fundo_laranja_texto_branco.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_laranja_texto_branco.setVerticalAlignment(VerticalAlignment.CENTER);

		HSSFFont newFont_branca = workbook.createFont();
		newFont_branca.setBold(true);
		newFont_branca.setColor(IndexedColors.WHITE.getIndex());
		newFont_branca.setFontName("Calibri");
		newFont_branca.setItalic(false);
		newFont_branca.setFontHeight((short) (11 * 20));
		Locale ptBr = new Locale("pt", "BR");

		celula_fundo_laranja_texto_branco.setFont(newFont_branca);

		HSSFFont newFont_titulo = workbook.createFont();
		newFont_titulo.setBold(true);
		newFont_titulo.setColor(IndexedColors.BLACK.getIndex());
		newFont_titulo.setFontName("Calibri");
		newFont_titulo.setItalic(true);
		newFont_titulo.setFontHeight((short) (11 * 32));

		// estilo para cabecalho
		CellStyle celula_titulo = workbook.createCellStyle();
		celula_titulo.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		celula_titulo.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		celula_titulo.setAlignment(HorizontalAlignment.CENTER);
		celula_titulo.setVerticalAlignment(VerticalAlignment.CENTER);
		celula_titulo.setFont(newFont_titulo);

		// Configurando as informacoes
		row = sheet.createRow(rownum++);

		// Configurando cabecalho com informacoes do funcionario
		cell = row.createCell(cellnum++);
		cell.setCellStyle(textStyle);
		cell.setCellValue("Colaborador:");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(negrito_esquerda);
		cell.setCellValue(funcionario.getNome() + " " + funcionario.getSobrenome());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(textStyle);
		cell.setCellValue("Data Admissão:");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(negrito_esquerda);
		cell.setCellValue(ct_global.getData_admissao());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(textStyle);
		cell.setCellValue("Sálario Base:");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(negrito_esquerda);
		cell.setCellValue(formatarValor(salario));

		row = sheet.createRow(rownum++);
		cellnum = 0;
		cell = row.createCell(cellnum++);
		cell.setCellStyle(textStyle);
		cell.setCellValue("Mês Refêrencia:");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(negrito_esquerda);
		cell.setCellValue(mes);

		cell = row.createCell(cellnum++);
		cell.setCellStyle(textStyle);
		cell.setCellValue("Ano Refêrencia:");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(negrito_esquerda);
		cell.setCellValue(ano + "");

		row = sheet.createRow(rownum++);
		cellnum = 0;
		cell = row.createCell(cellnum++);
		cell.setCellStyle(textStyle);
		cell.setCellValue("Cargo:");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(negrito_esquerda);
		cell.setCellValue(ct_global.getCargo());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(textStyle);
		cell.setCellValue("Função:");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(negrito_esquerda);
		cell.setCellValue(ct_global.getFuncao());

		row = sheet.createRow(rownum++);
		cellnum = 0;
		cell = row.createCell(cellnum++);
		cell.setCellStyle(textStyle);
		cell.setCellValue("Tipo Contrato:");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(negrito_esquerda);
		cell.setCellValue(ct_global.getTipo_contrato());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(textStyle);
		cell.setCellValue("Status:");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(negrito_esquerda);
		if (ct_global.getData_encerramento_contrato() != null) {
			cell.setCellValue("CT ENC em " + ct_global.getData_encerramento_contrato());

		} else {
			cell.setCellValue("ATIVO");

		}

		// vencimentos e horas extras

		// Configurando as informacoes
		row = sheet.createRow(rownum++);
		row = sheet.createRow(rownum++);
		cellnum = 0;
		// Configurando titulo
		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_titulo);
		cell.setCellValue("Vencimentos");
		// criar celula de 1 a 5
		for (int i = 1; i < 6; i++) {
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_titulo);
			cell.setCellValue("");

		}
		// sheet.addMergedRegion(new CellRangeAddress(rownum-1, rownum-1, 0, 5));

		// Configurando Header
		cellnum = 0;
		row = sheet.createRow(rownum++);

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("TIPO");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("DESCRIÇÃO");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("REFÊRENCIA");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("QUANTIDADE(Horas)");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("TOTAL");

		// linha salario
		row = sheet.createRow(rownum++);
		cellnum = 0;
		cell = row.createCell(cellnum++);
		cell.setCellStyle(textStyle);
		cell.setCellValue("SÁLARIO");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(textStyle);
		cell.setCellValue("sálario mensal");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(textStyle);
		cell.setCellValue(formatHora(registro_global.getTHMensal()));

		cell = row.createCell(cellnum++);
		cell.setCellStyle(textStyle);
		cell.setCellValue(formatHora(registro_global.getTHTrabalhadas()));

		cell = row.createCell(cellnum++);
		cell.setCellStyle(textStyle);
		cell.setCellValue(formatarValor(salario));

		if(registro_global.getOpcao_banco() != 1) {
		
		// linha hora 50%
		row = sheet.createRow(rownum++);
		cellnum = 0;
		cell = row.createCell(cellnum++);
		cell.setCellStyle(textStyle);
		cell.setCellValue("HORA EXTRAS");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(textStyle);
		cell.setCellValue("HORA 50%");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(textStyle);
		cell.setCellValue(formatarValor(registro_global.getValor_hora50()));

		cell = row.createCell(cellnum++);
		cell.setCellStyle(textStyle);
		cell.setCellValue(formatHora(registro_global.getTH50()));

		cell = row.createCell(cellnum++);
		cell.setCellStyle(textStyle);
		cell.setCellValue(formatarValor(registro_global.getValor_total_hora50()));

		// linha hora 60%
		row = sheet.createRow(rownum++);
		cellnum = 0;
		cell = row.createCell(cellnum++);
		cell.setCellStyle(textStyle);
		cell.setCellValue("HORA EXTRAS");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(textStyle);
		cell.setCellValue("HORA 60%");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(textStyle);
		cell.setCellValue(formatarValor(registro_global.getValor_hora60()));

		cell = row.createCell(cellnum++);
		cell.setCellStyle(textStyle);
		cell.setCellValue(formatHora(registro_global.getTH60()));

		cell = row.createCell(cellnum++);
		cell.setCellStyle(textStyle);
		cell.setCellValue(formatarValor(registro_global.getValor_total_hora60()));

		// linha hora 100%
		row = sheet.createRow(rownum++);
		cellnum = 0;
		cell = row.createCell(cellnum++);
		cell.setCellStyle(textStyle);
		cell.setCellValue("HORA EXTRAS");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(textStyle);
		cell.setCellValue("HORA 100%");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(textStyle);
		cell.setCellValue(formatarValor(registro_global.getValor_hora100()));

		cell = row.createCell(cellnum++);
		cell.setCellStyle(textStyle);
		cell.setCellValue(formatHora(registro_global.getTH100()));

		cell = row.createCell(cellnum++);
		cell.setCellStyle(textStyle);
		cell.setCellValue(formatarValor(registro_global.getValor_total_hora100()));
		
		}

		/****************************************************************************************/
		row = sheet.createRow(rownum++);

		double total_descontos = 0;
		double total_acrescimos = 0;

		if (acrescimos.size() > 0) {
			// tabela de acrescimos
			// Configurando as informacoes
			row = sheet.createRow(rownum++);
			cellnum = 0;
			// Configurando titulo
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_titulo);
			cell.setCellValue("Acréscimos");
			// criar celula de 1 a 5
			for (int i = 1; i < 6; i++) {
				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_titulo);
				cell.setCellValue("");

			}
			// sheet.addMergedRegion(new CellRangeAddress(rownum-1, rownum-1, 0, 5));

			cellnum = 0;

			// Configurando Header
			row = sheet.createRow(rownum++);
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja_texto_branco);
			cell.setCellValue("NOME");

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja_texto_branco);
			cell.setCellValue("DESCRIÇÃO");

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja_texto_branco);
			cell.setCellValue("REFERÊNCIA CÁLCULO".toUpperCase());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja_texto_branco);
			cell.setCellValue("QUANTIDADE".toUpperCase());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja_texto_branco);
			cell.setCellValue("REFERÊNCIA VALOR".toUpperCase());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja_texto_branco);
			cell.setCellValue("VALOR".toUpperCase());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja_texto_branco);
			cell.setCellValue("TOTAL".toUpperCase());

			for (CadastroFuncionarioCalculo acrescimo : acrescimos) {
				row = sheet.createRow(rownum++);
				cellnum = 0;

				// NOME
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue(acrescimo.getNome());

				// descricao
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue(acrescimo.getDescricao());

				// referencia calculo
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				int referencia_calculo = acrescimo.getReferencia_calculo();
				cell.setCellValue(ref_cal[referencia_calculo]);

				// quantidade
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue(acrescimo.getQuantidade());

				// referencia valor
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				int ref_val = acrescimo.getReferencia_valor();
				cell.setCellValue(ref_valor[ref_val]);

				// valor
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue(formatarValor(acrescimo.getValor()));

				// valor
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue(formatarValor(acrescimo.getTotal()));
				total_acrescimos += acrescimo.getTotal();
			}
			sheet.setAutoFilter(CellRangeAddress.valueOf("A2:AF2"));
			for (int i = 0; i < 13; i++) {
				sheet.autoSizeColumn(i);

			}
			
			row = sheet.createRow(rownum++);
			cellnum = 0;
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue("Total Acréscimos: ");
			
			cell = row.createCell(cellnum++);
			cell.setCellStyle(negrito_esquerda);
			cell.setCellValue(formatarValor(total_acrescimos));
			
		}

		if (descontos.size() > 0) {
			// Configurando as informacoes
			row = sheet.createRow(rownum++);
			cellnum = 0;
			// Configurando titulo
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_titulo);
			cell.setCellValue("Descontos");
			// criar celula de 1 a 5
			for (int i = 1; i < 6; i++) {
				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_titulo);
				cell.setCellValue("");

			}
			// sheet.addMergedRegion(new CellRangeAddress(rownum-1, rownum-1, 0, 5));

			cellnum = 0;

			// Configurando Header
			row = sheet.createRow(rownum++);
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja_texto_branco);
			cell.setCellValue("NOME");

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja_texto_branco);
			cell.setCellValue("DESCRIÇÃO");

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja_texto_branco);
			cell.setCellValue("REFERÊNCIA CÁLCULO".toUpperCase());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja_texto_branco);
			cell.setCellValue("QUANTIDADE".toUpperCase());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja_texto_branco);
			cell.setCellValue("REFERÊNCIA VALOR".toUpperCase());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja_texto_branco);
			cell.setCellValue("VALOR".toUpperCase());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja_texto_branco);
			cell.setCellValue("TOTAL".toUpperCase());

			for (CadastroFuncionarioCalculo desconto : descontos) {
				row = sheet.createRow(rownum++);
				cellnum = 0;

				// NOME
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue(desconto.getNome());

				// descricao
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue(desconto.getDescricao());

				// referencia calculo
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				int referencia_calculo = desconto.getReferencia_calculo();
				cell.setCellValue(ref_cal[referencia_calculo]);

				// quantidade
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue(desconto.getQuantidade());

				// referencia valor
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				int ref_val = desconto.getReferencia_valor();
				cell.setCellValue(ref_valor[ref_val]);

				// valor
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue(formatarValor(desconto.getValor()));

				// valor
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue(formatarValor(desconto.getTotal()));
				total_descontos += desconto.getTotal();
			}
			sheet.setAutoFilter(CellRangeAddress.valueOf("A2:AF2"));
			for (int i = 0; i < 13; i++) {
				sheet.autoSizeColumn(i);

			}
			
			row = sheet.createRow(rownum++);
			cellnum = 0;
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue("TOTAL DESCONTOS: ");
			
			cell = row.createCell(cellnum++);
			cell.setCellStyle(negrito_esquerda);
			cell.setCellValue(formatarValor(total_descontos));
		}
		
		//totais
		double bruto = salario + registro_global.getValor_total_hora_extras() + total_acrescimos;
		double liquido = bruto -  total_descontos;

		row = sheet.createRow(rownum++);
		row = sheet.createRow(rownum++);
		cellnum = 0;
		cell = row.createCell(cellnum++);
		cell.setCellStyle(negrito_esquerda);
		cell.setCellValue("TOTAIS: ");
		//sheet.addMergedRegion(new CellRangeAddress(rownum-1, rownum-1, 0, 2));

		row = sheet.createRow(rownum++);
		cellnum = 0;
		cell = row.createCell(cellnum++);
		cell.setCellStyle(textStyle);
		cell.setCellValue("TOTAL VENCIMENTOS:");
	
		cell = row.createCell(cellnum++);
		cell.setCellStyle(negrito_esquerda);
		cell.setCellValue(formatarValor(bruto));

		
		row = sheet.createRow(rownum++);
		cellnum = 0;
		cell = row.createCell(cellnum++);
		cell.setCellStyle(textStyle);
		cell.setCellValue("TOTAL DESCONTOS:");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(negrito_esquerda);
		cell.setCellValue(formatarValor(total_descontos));
		
		
		row = sheet.createRow(rownum++);
		cellnum = 0;
		cell = row.createCell(cellnum++);
		cell.setCellStyle(textStyle);
		cell.setCellValue("TOTAL LIQUIDO:");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(negrito_esquerda);
		cell.setCellValue(formatarValor(liquido));
		
		
		if(registro_global.getOpcao_banco() == 1) {
			row = sheet.createRow(rownum++);
			cellnum = 0;
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue("OBS:");

			cell = row.createCell(cellnum++);
			cell.setCellStyle(negrito_esquerda);
			String s_tipo = "";
			if(registro_global.getTipo_banco() == 0)
				s_tipo = "NEGATIVO";
			else
				s_tipo = "POSITIVO";
			
			cell.setCellValue("BANCO DE HORAS para " + registro_global.getMes() + " " + s_tipo + " em " + formatHora(registro_global.getQuantidade())  + " horas");
		}

		/*************************************************************************************/
		//registro de ponto
		
		// Configurando as informacoes
		row = sheet.createRow(rownum++);

		// Configurando titulo
		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_titulo);
		cell.setCellValue("Relatório de Registro de Ponto");
		// criar celula de 1 a 5
		for (int i = 1; i < 6; i++) {
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_titulo);
			cell.setCellValue("");

		}
		//sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

		cellnum = 0;

		// Configurando Header
		row = sheet.createRow(rownum++);
		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("DATA");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("DIA DA SEMANA");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("HORA ENTRADA 1".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("HORA SAIDA 1".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("HORA ENTRADA 2".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("HORA SAIDA 2".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("HORA ENTRADA 3".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("HORA SAIDA 3".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("ROTINA DIARIA".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("HORAS TRABALHADAS".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("HORAS EXTRAS".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("HORAS ATRAZO".toUpperCase());

		for (RegistroPontoMensalCompleto rp : registros_selecionados) {
			row = sheet.createRow(rownum++);
			cellnum = 0;

			// data
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(rp.getRp().getData());

			// dia da semana
			String dia_da_semana = "";
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);

			if (rp.getRp().getData() != null && !rp.getRp().getData().equals("")
					&& !rp.getRp().getData().equalsIgnoreCase("TOTAIS")) {
				try {
					DateTimeFormatter formatter = new DateTimeFormatterBuilder().toFormatter(new Locale("pt", "BR"));

					LocalDate data = LocalDate.parse(rp.getRp().getData(), formatter.ofPattern("dd/MM/yyyy"));
					DayOfWeek dia_s = data.getDayOfWeek();
					cell.setCellValue(dia_s.getDisplayName(TextStyle.FULL, new Locale("pt", "BR")).toUpperCase());
				} catch (Exception e) {
					cell.setCellValue("");
				}
			} else {
				cell.setCellValue("");
			}

			// hora entrada1
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			if (!rp.getRp().getEntrada1().equals("00:00"))
				cell.setCellValue(rp.getRp().getEntrada1());
			else
				cell.setCellValue("-");

			// hora saida1
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			if (!rp.getRp().getSaida1().equals("00:00"))
				cell.setCellValue(rp.getRp().getSaida1());
			else
				cell.setCellValue("-");

			// hora entrada 2
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			if (!rp.getRp().getEntrada2().equals("00:00"))
				cell.setCellValue(rp.getRp().getEntrada2());
			else
				cell.setCellValue("-");

			// hora saida2
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			if (!rp.getRp().getSaida2().equals("00:00"))
				cell.setCellValue(rp.getRp().getSaida2());
			else
				cell.setCellValue("-");

			// hora entrada 3
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			if (!rp.getRp().getEntrada3().equals("00:00"))
				cell.setCellValue(rp.getRp().getEntrada3());
			else
				cell.setCellValue("-");

			// hora saida 3
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			if (!rp.getRp().getSaida3().equals("00:00"))
				cell.setCellValue(rp.getRp().getSaida3());
			else
				cell.setCellValue("-");

			// rotina diaria
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			if (!rp.getHora_diaria().equals("00:00"))
				cell.setCellValue(rp.getHora_diaria());
			else
				cell.setCellValue("DESCANSO SEMANAL");

			// horas trabalhadas
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			if (!rp.getHora_trabalhada().equals("00:00"))
				cell.setCellValue(rp.getHora_trabalhada());
			else
				cell.setCellValue("");

			// horas extras
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			if (!rp.getHora_extra().equals("00:00"))
				cell.setCellValue(rp.getHora_extra());
			else
				cell.setCellValue("");

			// horas atrazo
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			if (!rp.getHora_atrazo().equals("00:00"))
				cell.setCellValue(rp.getHora_atrazo());
			else
				cell.setCellValue("");
		}
		sheet.setAutoFilter(CellRangeAddress.valueOf("A2:AF2"));
		for (int i = 0; i < 13; i++) {
			sheet.autoSizeColumn(i);

		}

		
		
		return workbook;
	}

	

	public String formatarValor(double valor) {
		return NumberFormat.getCurrencyInstance(ptBr).format(valor);
	}

	public String formatHora(long minutos) {
		Duration duracao_total_horas_normais = Duration.ofMinutes(minutos);
		return String.format("%d:%02d", duracao_total_horas_normais.toHours(),
				duracao_total_horas_normais.toMinutesPart());

	}

}
