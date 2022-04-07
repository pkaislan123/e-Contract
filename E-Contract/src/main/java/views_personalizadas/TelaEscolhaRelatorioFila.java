
package main.java.views_personalizadas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
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

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroFilaMovimento;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.Lancamento;
import main.java.cadastros.PagamentoCompleto;
import main.java.conexaoBanco.GerenciarBancoCondicaoPagamentos;
import main.java.cadastros.CadastroSafra;
import main.java.cadastros.CondicaoPagamento;
import main.java.cadastros.FinanceiroParcelaCompleto;
import main.java.cadastros.Lancamento;
import main.java.gui.TelaEnviarMsgMail;
import main.java.gui.TelaEnviarMsgWhatsapp;
import main.java.gui.TelaVizualizarPdf;
import main.java.gui_internal.TelaFinanceiroLancamentoInternal;
import main.java.gui_internal.TelaFinanceiroPagamentoInternal;
import main.java.gui_internal.TelaFinanceiroParcelaInternal;
import main.java.manipular.ConverterPdf;
import main.java.manipular.ManipularTxt;

import javax.swing.JTextArea;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import net.miginfocom.swing.MigLayout;
import javax.swing.JRadioButton;
import javax.swing.border.LineBorder;

public class TelaEscolhaRelatorioFila extends JDialog {

	private TelaEscolhaRelatorioFila isto;
	private FileChooser fileChooser;
	private JRadioButton rdbtnPdf, rdbtnExcel;

	/**
	 * @wbp.parser.constructor
	 */
	public TelaEscolhaRelatorioFila(ArrayList<CadastroFilaMovimento> unidades, int tipo_movimentacao,
			Window janela_pai) {
		getContentPane().setBackground(Color.WHITE);

		setBounds(100, 100, 331, 259);
		isto = this;
		getContentPane().setLayout(new MigLayout("", "[grow][]", "[grow][][grow][][grow][][grow][][][]"));

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(0, 51, 0));
		getContentPane().add(panel_2, "cell 0 0 2 1,grow");

		JLabel btnRelatrioDelancamentos = new JLabel("Relatório de Fila");
		panel_2.add(btnRelatrioDelancamentos);
		btnRelatrioDelancamentos.setOpaque(true);
		btnRelatrioDelancamentos.setForeground(Color.WHITE);
		btnRelatrioDelancamentos.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnRelatrioDelancamentos.setBorder(null);
		btnRelatrioDelancamentos.setBackground(new Color(0, 51, 0));

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBackground(Color.WHITE);
		getContentPane().add(panel_1, "cell 0 6 2 1,alignx center,growy");
		panel_1.setLayout(new MigLayout("", "[53px][43px][]", "[23px]"));

		rdbtnExcel = new JRadioButton("Excel");
		rdbtnExcel.setBackground(Color.WHITE);
		rdbtnExcel.setForeground(Color.BLACK);
		rdbtnExcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnExcel.setSelected(true);
				rdbtnPdf.setSelected(false);

			}
		});
		rdbtnExcel.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_1.add(rdbtnExcel, "cell 0 0,alignx left,aligny top");

		rdbtnPdf = new JRadioButton("Pdf");
		rdbtnPdf.setSelected(true);
		rdbtnPdf.setBackground(Color.WHITE);
		rdbtnPdf.setForeground(Color.BLACK);
		rdbtnPdf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnExcel.setSelected(false);
				rdbtnPdf.setSelected(true);
			}
		});
		rdbtnPdf.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_1.add(rdbtnPdf, "cell 1 0,alignx left,aligny top");

		JButton btnNewButton_1 = new JButton("Gerar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// gerar

				if (rdbtnExcel.isSelected()) {
					if (tipo_movimentacao == 0) {
						gerarExcel(prepararDesembarque(unidades));
					} else {
						gerarExcel(prepararEmbarque(unidades));

					}
				} else if (rdbtnPdf.isSelected()) {
					if (tipo_movimentacao == 0) {
						gerarPdf(prepararDesembarque(unidades));
					} else {
						gerarPdf(prepararEmbarque(unidades));

					}
				}

			}
		});
		btnNewButton_1.setFont(new Font("Arial", Font.PLAIN, 16));
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.setBackground(new Color(0, 0, 51));
		getContentPane().add(btnNewButton_1, "cell 1 8,alignx right");
		URL url2 = getClass().getResource("/imagens/infinite.gif");
		ImageIcon img2 = new ImageIcon(url2);

		setLocationRelativeTo(janela_pai);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		setResizable(false);
		setVisible(true);
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

	public HSSFWorkbook prepararDesembarque(ArrayList<CadastroFilaMovimento> unidades) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Exportação de Dados de Fila de Desembarque");

		// Definindo alguns padroes de layout
		sheet.setDefaultColumnWidth(25);
		sheet.setDefaultRowHeight((short) 400);

		sheet.getPrintSetup().setLandscape(false);
		sheet.getPrintSetup().setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
		
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

		CellStyle negrito_direita = workbook.createCellStyle();
		// textStyle.setAlignment(HorizontalAlignment.CENTER);
		negrito_direita.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		negrito_direita.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		negrito_direita.setAlignment(HorizontalAlignment.RIGHT);
		negrito_direita.setVerticalAlignment(VerticalAlignment.CENTER);

		negrito_direita.setFont(newFontNegrita);

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

		HSSFFont newFontNegritaAzul = workbook.createFont();
		newFontNegritaAzul.setBold(true);
		newFontNegritaAzul.setColor(IndexedColors.BLUE.getIndex());
		newFontNegritaAzul.setFontName("Arial");
		newFontNegritaAzul.setItalic(true);
		newFontNegritaAzul.setFontHeight((short) (11 * 20));

		HSSFFont newFontNegritoVermelho = workbook.createFont();
		newFontNegritoVermelho.setBold(true);
		newFontNegritoVermelho.setColor(IndexedColors.RED.getIndex());
		newFontNegritoVermelho.setFontName("Arial");
		newFontNegritoVermelho.setItalic(true);
		newFontNegritoVermelho.setFontHeight((short) (11 * 20));

		HSSFFont newFontNegritoVerde = workbook.createFont();
		newFontNegritoVerde.setBold(true);
		newFontNegritoVerde.setColor(IndexedColors.GREEN.getIndex());
		newFontNegritoVerde.setFontName("Arial");
		newFontNegritoVerde.setItalic(true);
		newFontNegritoVerde.setFontHeight((short) (11 * 20));

		CellStyle textStyleAzul = workbook.createCellStyle();
		textStyleAzul.setAlignment(HorizontalAlignment.CENTER);
		textStyleAzul.setVerticalAlignment(VerticalAlignment.CENTER);
		textStyleAzul.setFont(newFontNegritaAzul);

		CellStyle textStyleVerde = workbook.createCellStyle();
		textStyleVerde.setAlignment(HorizontalAlignment.CENTER);
		textStyleVerde.setVerticalAlignment(VerticalAlignment.CENTER);
		textStyleVerde.setFont(newFontNegritoVerde);

		CellStyle textStyleVermelho = workbook.createCellStyle();
		textStyleVermelho.setAlignment(HorizontalAlignment.CENTER);
		textStyleVermelho.setVerticalAlignment(VerticalAlignment.CENTER);
		textStyleVermelho.setFont(newFontNegritoVermelho);

		// Configurando as informacoes
		row = sheet.createRow(rownum++);

		// Configurando titulo
		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_titulo);
		cell.setCellValue("Relatório de Fila de Desembarque");
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
		cell.setCellValue("ID");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("DATA");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("HORA CHEGADA");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("STATUS".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("MOTORISTA".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("PLACA".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("PRODUTOR".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("PRODUTO".toUpperCase());

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		int num_em_fila = 0, num_entrada = 0, num_saida = 0, num_cancelados = 0;

		for (CadastroFilaMovimento unidade : unidades) {

			row = sheet.createRow(rownum++);
			cellnum = 0;

			// id
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(unidade.getId());

			// data
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			String data = formatter.format(unidade.getSomente_data_fila());
			cell.setCellValue(data);

			// hora chegada
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(LocalDateTime.ofInstant(unidade.getData_hora_fila().toInstant(), ZoneId.systemDefault())
					.toLocalTime().toString());

			// status

			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);

			int sts = unidade.getStatus();
			if (sts == 0) {
				cell.setCellValue("EM FILA");
				num_em_fila++;

			} else if (sts == 1) {
				cell.setCellValue("ENTRADA");
				num_entrada++;

			} else if (sts == 2) {
				cell.setCellValue("SAÍDA");
				num_saida++;

			} else if (sts == -1) {
				cell.setCellValue("CANCELADO");
				num_cancelados++;

			}

			// motorista
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(unidade.getMotorista().getNome_empresarial().toUpperCase());

			// placa
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(unidade.getVeiculo().getPlaca_trator().toUpperCase());

			// produtor
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(unidade.getProdutor().getNome_empresarial().toUpperCase());


			// produto
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(unidade.getProduto().getNome_produto().toUpperCase());

		}

		sheet.setAutoFilter(CellRangeAddress.valueOf("A2:H2"));
		for (int i = 0; i < 13; i++) {
			sheet.autoSizeColumn(i);

		}

		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		cell = row.createCell(cellnum += 2);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("Em Fila: ");

		cell = row.createCell(cellnum += 1);
		cell.setCellStyle(numberStyle);
		cell.setCellValue(num_em_fila);

		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		cell = row.createCell(cellnum += 2);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("Desembarcando: ");

		cell = row.createCell(cellnum += 1);
		cell.setCellStyle(numberStyle);
		cell.setCellValue(num_entrada);

		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		cell = row.createCell(cellnum += 2);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("Desembarcados: ");

		cell = row.createCell(cellnum += 1);
		cell.setCellStyle(numberStyle);
		cell.setCellValue(num_saida);

		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		cell = row.createCell(cellnum += 2);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("Cancelados: ");

		cell = row.createCell(cellnum += 1);
		cell.setCellStyle(numberStyle);
		cell.setCellValue(num_cancelados);

		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		cell = row.createCell(cellnum += 2);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("Total: ");

		cell = row.createCell(cellnum += 1);
		cell.setCellStyle(numberStyle);
		cell.setCellValue(num_cancelados + num_entrada + num_em_fila + num_saida);

		return workbook;
	}

	public HSSFWorkbook prepararEmbarque(ArrayList<CadastroFilaMovimento> unidades) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Exportação de Dados de Fila de Embarque");

		// Definindo alguns padroes de layout
		sheet.setDefaultColumnWidth(25);
		sheet.setDefaultRowHeight((short) 400);

		int rownum = 0;
		int cellnum = 0;
		Cell cell;
		Row row;

		sheet.getPrintSetup().setLandscape(false);
		sheet.getPrintSetup().setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
		
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

		CellStyle negrito_direita = workbook.createCellStyle();
		// textStyle.setAlignment(HorizontalAlignment.CENTER);
		negrito_direita.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		negrito_direita.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		negrito_direita.setAlignment(HorizontalAlignment.RIGHT);
		negrito_direita.setVerticalAlignment(VerticalAlignment.CENTER);

		negrito_direita.setFont(newFontNegrita);

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

		HSSFFont newFontNegritaAzul = workbook.createFont();
		newFontNegritaAzul.setBold(true);
		newFontNegritaAzul.setColor(IndexedColors.BLUE.getIndex());
		newFontNegritaAzul.setFontName("Arial");
		newFontNegritaAzul.setItalic(true);
		newFontNegritaAzul.setFontHeight((short) (11 * 20));

		HSSFFont newFontNegritoVermelho = workbook.createFont();
		newFontNegritoVermelho.setBold(true);
		newFontNegritoVermelho.setColor(IndexedColors.RED.getIndex());
		newFontNegritoVermelho.setFontName("Arial");
		newFontNegritoVermelho.setItalic(true);
		newFontNegritoVermelho.setFontHeight((short) (11 * 20));

		HSSFFont newFontNegritoVerde = workbook.createFont();
		newFontNegritoVerde.setBold(true);
		newFontNegritoVerde.setColor(IndexedColors.GREEN.getIndex());
		newFontNegritoVerde.setFontName("Arial");
		newFontNegritoVerde.setItalic(true);
		newFontNegritoVerde.setFontHeight((short) (11 * 20));

		CellStyle textStyleAzul = workbook.createCellStyle();
		textStyleAzul.setAlignment(HorizontalAlignment.CENTER);
		textStyleAzul.setVerticalAlignment(VerticalAlignment.CENTER);
		textStyleAzul.setFont(newFontNegritaAzul);

		CellStyle textStyleVerde = workbook.createCellStyle();
		textStyleVerde.setAlignment(HorizontalAlignment.CENTER);
		textStyleVerde.setVerticalAlignment(VerticalAlignment.CENTER);
		textStyleVerde.setFont(newFontNegritoVerde);

		CellStyle textStyleVermelho = workbook.createCellStyle();
		textStyleVermelho.setAlignment(HorizontalAlignment.CENTER);
		textStyleVermelho.setVerticalAlignment(VerticalAlignment.CENTER);
		textStyleVermelho.setFont(newFontNegritoVermelho);

		// Configurando as informacoes
		row = sheet.createRow(rownum++);

		// Configurando titulo
		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_titulo);
		cell.setCellValue("Relatório de Fila de Embarque");
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
		cell.setCellValue("ID");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("DATA");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("HORA CHEGADA");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("STATUS".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("MOTORISTA".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("PLACA".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("CLIENTE".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("PRODUTO".toUpperCase());

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		int num_em_fila = 0, num_entrada = 0, num_saida = 0, num_cancelados = 0;

		for (CadastroFilaMovimento unidade : unidades) {

			row = sheet.createRow(rownum++);
			cellnum = 0;

			// id
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(unidade.getId());

			// data
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			String data = formatter.format(unidade.getSomente_data_fila());
			cell.setCellValue(data);

			// hora chegada
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(LocalDateTime.ofInstant(unidade.getData_hora_fila().toInstant(), ZoneId.systemDefault())
					.toLocalTime().toString());

			// status

			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);

			int sts = unidade.getStatus();
			if (sts == 0) {
				cell.setCellValue("EM FILA");
				num_em_fila++;

			} else if (sts == 1) {
				cell.setCellValue("ENTRADA");
				num_entrada++;

			} else if (sts == 2) {
				cell.setCellValue("SAÍDA");
				num_saida++;

			} else if (sts == -1) {
				cell.setCellValue("CANCELADO");
				num_cancelados++;

			}

			// motorista
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(unidade.getMotorista().getNome_empresarial().toUpperCase());

			// placa
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(unidade.getVeiculo().getPlaca_trator().toUpperCase());

			// produtor
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(unidade.getProdutor().getNome_empresarial().toUpperCase());

			// produto
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(unidade.getProduto().getNome_produto().toUpperCase());

		}

		sheet.setAutoFilter(CellRangeAddress.valueOf("A2:H2"));
		for (int i = 0; i < 13; i++) {
			sheet.autoSizeColumn(i);

		}

		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		cell = row.createCell(cellnum += 2);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("Em Fila: ");

		cell = row.createCell(cellnum += 1);
		cell.setCellStyle(numberStyle);
		cell.setCellValue(num_em_fila);

		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		cell = row.createCell(cellnum += 2);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("Carregando: ");

		cell = row.createCell(cellnum += 1);
		cell.setCellStyle(numberStyle);
		cell.setCellValue(num_entrada);

		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		cell = row.createCell(cellnum += 2);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("Carregados: ");

		cell = row.createCell(cellnum += 1);
		cell.setCellStyle(numberStyle);
		cell.setCellValue(num_saida);

		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		cell = row.createCell(cellnum += 2);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("Cancelados: ");

		cell = row.createCell(cellnum += 1);
		cell.setCellStyle(numberStyle);
		cell.setCellValue(num_cancelados);

		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		cell = row.createCell(cellnum += 2);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("Total: ");

		cell = row.createCell(cellnum += 1);
		cell.setCellStyle(numberStyle);
		cell.setCellValue(num_cancelados + num_entrada + num_em_fila + num_saida);

		return workbook;
	}

	public void fechar() {
		isto.dispose();
	}
}
