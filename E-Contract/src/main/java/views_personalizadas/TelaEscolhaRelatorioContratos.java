
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
import org.apache.poi.hssf.usermodel.HSSFPalette;
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
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroRomaneio;
import main.java.cadastros.CadastroSafra;
import main.java.classesExtras.CBLocalRetiradaPersonalizado;
import main.java.classesExtras.CBLocalRetiradaRenderPersonalizado;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.gui.TelaEnviarMsgMail;
import main.java.gui.TelaEnviarMsgWhatsapp;
import main.java.gui.TelaVizualizarPdf;
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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import net.miginfocom.swing.MigLayout;
import javax.swing.JRadioButton;
import javax.swing.border.LineBorder;
import javax.swing.text.MaskFormatter;
import javax.swing.JComboBox;

public class TelaEscolhaRelatorioContratos extends JDialog {

	private TelaEscolhaRelatorioContratos isto;
	private FileChooser fileChooser;
	private JRadioButton rdbtnCompleto, rdbtnSimples, rdbtnPdf, rdbtnExcel;
	private JComboBox cbArmazem;
	private CBLocalRetiradaPersonalizado modelLocalRetirada = new CBLocalRetiradaPersonalizado();
	private static ArrayList<CadastroCliente> armazens = new ArrayList<>();

	public TelaEscolhaRelatorioContratos(ArrayList<CadastroContrato> contratos, Window janela_pai) {
		getContentPane().setBackground(Color.WHITE);

		setBounds(100, 100, 490, 293);
		isto = this;
		getContentPane().setLayout(new MigLayout("", "[grow][]", "[grow][grow][][][grow][][grow][][]"));

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(0, 51, 0));
		getContentPane().add(panel_2, "cell 0 0 2 1,grow");
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblNewLabel_1 = new JLabel("Relatório de Contratos");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("SansSerif", Font.BOLD, 18));
		panel_2.add(lblNewLabel_1);

		JLabel lblArmazm = new JLabel("Armazém:");
		lblArmazm.setForeground(Color.BLACK);
		lblArmazm.setFont(new Font("Tahoma", Font.PLAIN, 18));
		getContentPane().add(lblArmazm, "flowx,cell 0 2");

		JLabel lblNewLabel = new JLabel("Tipo:");
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		getContentPane().add(lblNewLabel, "flowx,cell 0 3,alignx left");

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBackground(Color.WHITE);
		getContentPane().add(panel, "cell 0 4 2 1,alignx center,growy");
		panel.setLayout(new MigLayout("", "[][][][][]", "[]"));

		rdbtnCompleto = new JRadioButton("Completo");
		rdbtnCompleto.setBackground(Color.WHITE);
		rdbtnCompleto.setForeground(Color.BLACK);

		rdbtnSimples = new JRadioButton("Simples");
		rdbtnSimples.setForeground(Color.BLACK);
		rdbtnSimples.setBackground(Color.WHITE);

		rdbtnSimples.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel.add(rdbtnSimples, "cell 1 0");
		rdbtnCompleto.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel.add(rdbtnCompleto, "cell 2 0");

		JLabel lblSada = new JLabel("Saída:");
		lblSada.setForeground(Color.BLACK);
		lblSada.setFont(new Font("Tahoma", Font.PLAIN, 18));
		getContentPane().add(lblSada, "cell 0 5,alignx left");

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBackground(Color.WHITE);
		getContentPane().add(panel_1, "cell 0 6 2 1,alignx center,growy");
		panel_1.setLayout(new MigLayout("", "[53px][43px][]", "[23px]"));

		rdbtnPdf = new JRadioButton("Pdf");
		rdbtnPdf.setBackground(Color.WHITE);
		rdbtnPdf.setForeground(Color.BLACK);
		rdbtnPdf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnExcel.setSelected(false);
				rdbtnPdf.setSelected(true);
			}
		});

		rdbtnExcel = new JRadioButton("Excel");
		rdbtnExcel.setBackground(Color.WHITE);
		rdbtnExcel.setForeground(Color.BLACK);
		rdbtnExcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnExcel.setSelected(true);
				rdbtnPdf.setSelected(false);

			}
		});
		rdbtnExcel.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_1.add(rdbtnExcel, "cell 0 0,alignx left,aligny top");
		rdbtnPdf.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_1.add(rdbtnPdf, "cell 2 0,alignx left,aligny top");

		JButton btnNewButton_1 = new JButton("Gerar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// gerar

				if (rdbtnCompleto.isSelected()) {
					// relatorio completo
					if (rdbtnExcel.isSelected()) {
						gerarExcel(prepararCompleto(contratos, 1));
					} else if (rdbtnPdf.isSelected()) {
						gerarPdf(prepararCompleto(contratos, 1));

					}

				} else if (rdbtnSimples.isSelected()) {
					// relatorio simples
					if (rdbtnExcel.isSelected()) {
						gerarExcel(preparar(contratos, 0));
					} else if (rdbtnPdf.isSelected()) {
						gerarPdf(preparar(contratos, 0));

					}
				}

			}
		});
		btnNewButton_1.setFont(new Font("Arial", Font.PLAIN, 14));
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.setBackground(new Color(0, 0, 51));
		getContentPane().add(btnNewButton_1, "cell 1 8,alignx right");

		cbArmazem = new JComboBox();
		getContentPane().add(cbArmazem, "cell 0 2,growx");
		cbArmazem.setBounds(585, 269, 174, 36);
		cbArmazem.setModel(modelLocalRetirada);
		cbArmazem.setRenderer(new CBLocalRetiradaRenderPersonalizado());

		pesquisarArmazens();

		for (CadastroCliente armazem : armazens) {
			if (armazem.getArmazem() == 1)
				modelLocalRetirada.addArmazem(armazem);

		}

		setLocationRelativeTo(janela_pai);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		setResizable(false);
		setVisible(true);
	}

	public static void pesquisarArmazens() {
		GerenciarBancoClientes listaArmazens = new GerenciarBancoClientes();
		armazens = listaArmazens.getClientes(-1, -1, null);

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

	public HSSFWorkbook preparar(ArrayList<CadastroContrato> contratos, int flag) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Exportação de Dados de Contratos");

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
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		headerStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());

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

		HSSFFont newFont = workbook.createFont();
		newFont.setBold(true);
		newFont.setColor(IndexedColors.BLACK.getIndex());
		newFont.setFontName("Calibri");
		newFont.setItalic(false);
		newFont.setFontHeight((short) (11 * 25));
		
		HSSFFont newFont_branca = workbook.createFont();
		newFont_branca.setBold(true);
		newFont_branca.setColor(IndexedColors.WHITE.getIndex());
		newFont_branca.setFontName("Calibri");
		newFont_branca.setItalic(false);
		newFont_branca.setFontHeight((short) (11 * 28));

		// estilo para cabecalho fundo laranja
		CellStyle celula_fundo_laranja = workbook.createCellStyle();
		celula_fundo_laranja.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		celula_fundo_laranja.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		celula_fundo_laranja.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_laranja.setVerticalAlignment(VerticalAlignment.CENTER);
		celula_fundo_laranja.setFont(newFont);

		// estilo para celula do tipo numero alinhado ao centroas
		CellStyle valorStyle = workbook.createCellStyle();
		valorStyle.setDataFormat(numberFormat.getFormat("R$ #,##0.00"));
		valorStyle.setAlignment(HorizontalAlignment.CENTER);
		valorStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		
		// estilo para celula do tipo numero alinhado ao centro
		CellStyle valorStyle_total = workbook.createCellStyle();
		valorStyle_total.setDataFormat(numberFormat.getFormat("R$ #,##0.00"));
		valorStyle_total.setAlignment(HorizontalAlignment.CENTER);
		valorStyle_total.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		valorStyle_total.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		valorStyle_total.setVerticalAlignment(VerticalAlignment.CENTER);
		valorStyle_total.setFont(newFont_branca);

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

		

		headerStyle.setFont(newFont_branca);
		celula_fundo_laranja_texto_branco.setFont(newFont_branca);

		// estilo para celula fundo azul
		CellStyle celula_fundo_azul = workbook.createCellStyle();
		celula_fundo_azul.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		celula_fundo_azul.setFillForegroundColor(IndexedColors.AQUA.getIndex());
		celula_fundo_azul.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_azul.setVerticalAlignment(VerticalAlignment.CENTER);
		celula_fundo_azul.setFont(newFont_blabk);

		// estilo para celula fundo azul
		CellStyle celula_fundo_amarelo = workbook.createCellStyle();
		celula_fundo_amarelo.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		celula_fundo_amarelo.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
		celula_fundo_amarelo.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_amarelo.setVerticalAlignment(VerticalAlignment.CENTER);
		celula_fundo_amarelo.setFont(newFont_blabk);

		// estilo para celula fundo verde
		CellStyle celula_fundo_verde = workbook.createCellStyle();
		celula_fundo_verde.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		celula_fundo_verde.setFillForegroundColor(IndexedColors.DARK_GREEN.getIndex());
		celula_fundo_verde.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_verde.setVerticalAlignment(VerticalAlignment.CENTER);
		celula_fundo_verde.setFont(newFont_blabk);

		// estilo para celula fundo azul
		CellStyle celula_fundo_vermelho = workbook.createCellStyle();
		celula_fundo_vermelho.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		celula_fundo_vermelho.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
		celula_fundo_vermelho.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_vermelho.setVerticalAlignment(VerticalAlignment.CENTER);
		celula_fundo_vermelho.setFont(newFont_blabk);

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

		Locale ptBr = new Locale("pt", "BR");
		MaskFormatter formater_cnpj = null;
		try {
			formater_cnpj = new MaskFormatter("##.###.###/####-##");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Configurando as informacoes
		row = sheet.createRow(rownum++);

		// Configurando titulo
		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_titulo);
		cell.setCellValue("Relatório de Contratos");
		// criar celula de 1 a 5
		for (int i = 1; i < 6; i++) {
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_titulo);
			cell.setCellValue("");

		}
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

		// dados do armazem
		row = sheet.createRow(rownum++);
		cellnum = 0;
		CadastroCliente localRetirada = (CadastroCliente) modelLocalRetirada.getSelectedItem();
		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_titulo);
		cell.setCellValue("Razão Social Participante: " + localRetirada.getRazao_social());
		// criar celula de 1 a 5
		for (int i = 1; i < 6; i++) {
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_titulo);
			cell.setCellValue("");

		}
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 5));

		row = sheet.createRow(rownum++);
		cellnum = 0;
		// cnpj do armazem
		cell = row.createCell(cellnum++);

		formater_cnpj.setValueContainsLiteralCharacters(false);
		cell.setCellStyle(celula_titulo);
		try {
			cell.setCellValue("Cnpj Unidade: " + formater_cnpj.valueToString(localRetirada.getCnpj()));
		} catch (ParseException e) {
			cell.setCellValue("Cnpj Unidade: ");

		}
		// criar celula de 1 a 5
		for (int i = 1; i < 6; i++) {
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_titulo);
			cell.setCellValue("");

		}
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 5));

		row = sheet.createRow(rownum++);
		cellnum = 0;
		// Configurando as informacoes
		row = sheet.createRow(rownum);

		// Configurando Header
		// Configurando Header
		row = sheet.createRow(rownum++);
		cell = row.createCell(cellnum++);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Código");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Comprador");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Vendedores");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Status");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Volume");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Uni");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Produto");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Transgenia");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Safra");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Preço");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Total");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Data CTR");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Local Retirada");

		double valor_total = 0;
		double quantidade_total_sacos = 0;
		double quantidade_total_kgs = 0;

		for (CadastroContrato cadastro : contratos) {
			row = sheet.createRow(rownum++);
			cellnum = 0;
			/*
			 * codigo compradores vendedores status quantidade medida produto transgenia
			 * safra valor_produto valor_total data_contrato local_retirada
			 */
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(cadastro.getCodigo());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			// cell.setCellValue(trimar(cadastro.getNomes_compradores().toUpperCase()));
			cell.setCellValue(trimar(encutarNomes(cadastro.getNomes_compradores().toUpperCase())));

			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			// cell.setCellValue(trimar(cadastro.getNomes_vendedores().toUpperCase()));
			cell.setCellValue(trimar(encutarNomes(cadastro.getNomes_vendedores().toUpperCase())));

			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);

			String status = "";
			int status_contrato = cadastro.getStatus_contrato();

			if (status_contrato == 1) {
				status = "ASSINAR";
				cell.setCellStyle(celula_fundo_amarelo);

			} else if (status_contrato == 0) {
				status = "A APROVAR";
				cell.setCellStyle(celula_fundo_amarelo);

			} else if (status_contrato == 2) {
				status = "ASSINADO";
				cell.setCellStyle(celula_fundo_azul);

			} else if (status_contrato == 3) {
				status = "CONCLUIDO";
				cell.setCellStyle(celula_fundo_verde);

			}

			cell.setCellValue(status);

			cell = row.createCell(cellnum++);
			cell.setCellStyle(numberStyle);
			cell.setCellValue(cadastro.getQuantidade());

			double quantidade_local_kg = 0;
			double quantidade_local_sacos = 0;

			if (cadastro.getMedida().equalsIgnoreCase("KG")) {
				quantidade_local_kg = cadastro.getQuantidade();
				quantidade_local_sacos = quantidade_local_kg / 60;
			} else if (cadastro.getMedida().equalsIgnoreCase("Sacos")) {
				quantidade_local_sacos = cadastro.getQuantidade();
				quantidade_local_kg = cadastro.getQuantidade() * 60;
			}

			quantidade_total_sacos = quantidade_total_sacos += quantidade_local_sacos;
			quantidade_total_kgs = quantidade_total_kgs += quantidade_local_kg;

			cell = row.createCell(cellnum++);
			cell.setCellStyle(numberStyle);
			String medida = "";
			if (cadastro.getMedida().equalsIgnoreCase("Sacos")) {
				medida = "SC";
			} else if (cadastro.getMedida().equalsIgnoreCase("Kg")) {
				medida = "KG";

			}
			cell.setCellValue(medida);

			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			String produto = "";
			cell.setCellValue(cadastro.getProduto().replaceAll(" NON-GMO", "").replaceAll("GMO", ""));

			cell = row.createCell(cellnum++);
			cell.setCellStyle(numberStyle);
			String transgenia = "";
			if (cadastro.getModelo_safra().getProduto().getTransgenia().contains("CONVENCIONAL")) {
				transgenia = "CONVEN.";
			} else {
				transgenia = "TRANSG.";

			}
			cell.setCellValue(transgenia);

			cell = row.createCell(cellnum++);
			cell.setCellStyle(numberStyle);
			String ano_plantio = Integer.toString(cadastro.getModelo_safra().getAno_plantio()).replaceAll("[^0-9]", "");
			String ano_colheita = Integer.toString(cadastro.getModelo_safra().getAno_colheita()).replaceAll("[^0-9]",
					"");

			cell.setCellValue(ano_plantio.substring(ano_plantio.length() - 2) + "/"
					+ ano_colheita.substring(ano_plantio.length() - 2));
			/*
			 * codigo compradores vendedores status quantidade medida produto transgenia
			 * safra valor_produto valor_total data_contrato local_retirada
			 */
			cell = row.createCell(cellnum++);
			cell.setCellStyle(valorStyle);
			cell.setCellValue(cadastro.getValor_produto());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(valorStyle);
			cell.setCellValue(cadastro.getValor_a_pagar().doubleValue());
			valor_total = valor_total += cadastro.getValor_a_pagar().doubleValue();

			cell = row.createCell(cellnum++);
			cell.setCellStyle(numberStyle);
			cell.setCellValue(cadastro.getData_contrato());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(numberStyle);

			GerenciarBancoClientes gerenciar = new GerenciarBancoClientes();
			CadastroCliente local_retirada = gerenciar.getCliente(cadastro.getId_local_retirada());
			String s_local_retirada = "";
			if (local_retirada != null) {
				if (local_retirada.getTipo_pessoa() == 0) {
					s_local_retirada = local_retirada.getNome_empresarial();
				} else {
					s_local_retirada = local_retirada.getNome_fantaia();
				}
			}
			cell.setCellValue(s_local_retirada);

		}
		sheet.setAutoFilter(CellRangeAddress.valueOf("A5:M5"));

		for (int i = 0; i < 13; i++) {
			sheet.autoSizeColumn(i);

		}

		// registra total de sacos, quilogramas, e valor total
		// total de contratos

		NumberFormat z = NumberFormat.getNumberInstance();

		row = sheet.createRow(rownum += 2);
		cellnum = 0;

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("Total Contratos");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue(contratos.size());
		sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 1, 2));

		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		cell = row.createCell(0);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("Total Sacos:");

		cell = row.createCell(1);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue(
				z.format(quantidade_total_sacos / 60) + " Kgs | " + z.format(quantidade_total_sacos) + " sacos");
		sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 1, 2));

		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		cell = row.createCell(0);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("Valor Total:");

		cell = row.createCell(1);
		cell.setCellStyle(valorStyle_total);
		cell.setCellValue(valor_total);
		sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 1, 2));

		return workbook;
	}

	public String encutarNomes(String nomes) {

		String nomes_encurtados = "";
		String nomes_compradores_separados[] = nomes.split(",");

		for (String nome_individual_para_encurtar : nomes_compradores_separados) {

			String nome_destinatario_quebrado[] = nome_individual_para_encurtar.split(" ");
			try {

				if (nome_destinatario_quebrado.length > 2) {
					if (nome_destinatario_quebrado[2].length() > 3) {
						nome_individual_para_encurtar = nome_destinatario_quebrado[0] + " "
								+ nome_destinatario_quebrado[2];
					} else {
						if (nome_destinatario_quebrado[3].length() > 3) {
							nome_individual_para_encurtar = nome_destinatario_quebrado[0] + " "
									+ nome_destinatario_quebrado[3];

						} else {
							nome_individual_para_encurtar = nome_destinatario_quebrado[0] + " "
									+ nome_destinatario_quebrado[1];

						}
					}
				}

			} catch (Exception v) {
				nome_individual_para_encurtar = nome_individual_para_encurtar;
			}

			nomes_encurtados += (nome_individual_para_encurtar + ", ");
		}

		return nomes_encurtados;
	}

	public String trimar(String texto) {
		String aplicar_rtrim = texto.replaceAll("\\s+$", "");
		String aplicar_ltrim = aplicar_rtrim.replaceAll("^\\s+", "");
		return aplicar_ltrim;

	}

	public void fechar() {
		isto.dispose();
	}

	public HSSFWorkbook prepararCompleto(ArrayList<CadastroContrato> contratos, int flag) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Exportação de Dados de Contratos");

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
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		headerStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());

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

		HSSFFont newFont = workbook.createFont();
		newFont.setBold(true);
		newFont.setColor(IndexedColors.BLACK.getIndex());
		newFont.setFontName("Calibri");
		newFont.setItalic(false);
		newFont.setFontHeight((short) (11 * 25));
		
		HSSFFont newFont_branca = workbook.createFont();
		newFont_branca.setBold(true);
		newFont_branca.setColor(IndexedColors.WHITE.getIndex());
		newFont_branca.setFontName("Calibri");
		newFont_branca.setItalic(false);
		newFont_branca.setFontHeight((short) (11 * 28));

		// estilo para cabecalho fundo laranja
		CellStyle celula_fundo_laranja = workbook.createCellStyle();
		celula_fundo_laranja.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		celula_fundo_laranja.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		celula_fundo_laranja.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_laranja.setVerticalAlignment(VerticalAlignment.CENTER);
		celula_fundo_laranja.setFont(newFont);

		// estilo para celula do tipo numero alinhado ao centroas
		CellStyle valorStyle = workbook.createCellStyle();
		valorStyle.setDataFormat(numberFormat.getFormat("R$ #,##0.00"));
		valorStyle.setAlignment(HorizontalAlignment.CENTER);
		valorStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		
		// estilo para celula do tipo numero alinhado ao centro
		CellStyle valorStyle_total = workbook.createCellStyle();
		valorStyle_total.setDataFormat(numberFormat.getFormat("R$ #,##0.00"));
		valorStyle_total.setAlignment(HorizontalAlignment.CENTER);
		valorStyle_total.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		valorStyle_total.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		valorStyle_total.setVerticalAlignment(VerticalAlignment.CENTER);
		valorStyle_total.setFont(newFont_branca);

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

		

		headerStyle.setFont(newFont_branca);
		celula_fundo_laranja_texto_branco.setFont(newFont_branca);

		// estilo para celula fundo azul
		CellStyle celula_fundo_azul = workbook.createCellStyle();
		celula_fundo_azul.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		celula_fundo_azul.setFillForegroundColor(IndexedColors.AQUA.getIndex());
		celula_fundo_azul.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_azul.setVerticalAlignment(VerticalAlignment.CENTER);
		celula_fundo_azul.setFont(newFont_blabk);

		// estilo para celula fundo azul
		CellStyle celula_fundo_amarelo = workbook.createCellStyle();
		celula_fundo_amarelo.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		celula_fundo_amarelo.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
		celula_fundo_amarelo.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_amarelo.setVerticalAlignment(VerticalAlignment.CENTER);
		celula_fundo_amarelo.setFont(newFont_blabk);

		// estilo para celula fundo verde
		CellStyle celula_fundo_verde = workbook.createCellStyle();
		celula_fundo_verde.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		celula_fundo_verde.setFillForegroundColor(IndexedColors.DARK_GREEN.getIndex());
		celula_fundo_verde.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_verde.setVerticalAlignment(VerticalAlignment.CENTER);
		celula_fundo_verde.setFont(newFont_blabk);

		// estilo para celula fundo azul
		CellStyle celula_fundo_vermelho = workbook.createCellStyle();
		celula_fundo_vermelho.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		celula_fundo_vermelho.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
		celula_fundo_vermelho.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_vermelho.setVerticalAlignment(VerticalAlignment.CENTER);
		celula_fundo_vermelho.setFont(newFont_blabk);

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

		Locale ptBr = new Locale("pt", "BR");
		MaskFormatter formater_cnpj = null;
		try {
			formater_cnpj = new MaskFormatter("##.###.###/####-##");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Configurando as informacoes
		row = sheet.createRow(rownum++);

		// Configurando titulo
		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_titulo);
		cell.setCellValue("Relatório de Contratos");
		// criar celula de 1 a 5
		for (int i = 1; i < 6; i++) {
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_titulo);
			cell.setCellValue("");

		}
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

		// dados do armazem
		row = sheet.createRow(rownum++);
		cellnum = 0;
		CadastroCliente localRetirada = (CadastroCliente) modelLocalRetirada.getSelectedItem();
		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_titulo);
		cell.setCellValue("Razão Social Participante: " + localRetirada.getRazao_social());
		// criar celula de 1 a 5
		for (int i = 1; i < 6; i++) {
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_titulo);
			cell.setCellValue("");

		}
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 5));

		row = sheet.createRow(rownum++);
		cellnum = 0;
		// cnpj do armazem
		cell = row.createCell(cellnum++);

		formater_cnpj.setValueContainsLiteralCharacters(false);
		cell.setCellStyle(celula_titulo);
		try {
			cell.setCellValue("Cnpj Unidade: " + formater_cnpj.valueToString(localRetirada.getCnpj()));
		} catch (ParseException e) {
			cell.setCellValue("Cnpj Unidade: ");

		}
		// criar celula de 1 a 5
		for (int i = 1; i < 6; i++) {
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_titulo);
			cell.setCellValue("");

		}
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 5));

		row = sheet.createRow(rownum++);
		cellnum = 0;
		// Configurando as informacoes
		row = sheet.createRow(rownum);

		// Configurando Header
		// Configurando Header
		row = sheet.createRow(rownum++);
		cell = row.createCell(cellnum++);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Código");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Comprador");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Vendedores");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Status");
		
		cell = row.createCell(cellnum++);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Status Recebimento");
		
		cell = row.createCell(cellnum++);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Status Carregamento");
		
		cell = row.createCell(cellnum++);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Status Pagamento");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Volume");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Uni");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Produto");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Transgenia");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Safra");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Preço");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Total");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Data CTR");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Local Retirada");

		double valor_total = 0;
		double quantidade_total_sacos = 0;
		double quantidade_total_kgs = 0;
		double total_carregado = 0, total_recebido = 0, total_contratado = 0, total_comissao=0, total_pago=0;


		for (CadastroContrato cadastro : contratos) {
			row = sheet.createRow(rownum++);
			cellnum = 0;
			/*
			 * codigo compradores vendedores status quantidade medida produto transgenia
			 * safra valor_produto valor_total data_contrato local_retirada
			 */
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(cadastro.getCodigo());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			// cell.setCellValue(trimar(cadastro.getNomes_compradores().toUpperCase()));
			cell.setCellValue(trimar(encutarNomes(cadastro.getNomes_compradores().toUpperCase())));

			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			// cell.setCellValue(trimar(cadastro.getNomes_vendedores().toUpperCase()));
			cell.setCellValue(trimar(encutarNomes(cadastro.getNomes_vendedores().toUpperCase())));

			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);

			String status = "";
			int status_contrato = cadastro.getStatus_contrato();

			if (status_contrato == 1) {
				status = "ASSINAR";
				cell.setCellStyle(celula_fundo_amarelo);

			} else if (status_contrato == 0) {
				status = "A APROVAR";
				cell.setCellStyle(celula_fundo_amarelo);

			} else if (status_contrato == 2) {
				status = "ASSINADO";
				cell.setCellStyle(celula_fundo_azul);

			} else if (status_contrato == 3) {
				status = "CONCLUIDO";
				cell.setCellStyle(celula_fundo_verde);

			}

			cell.setCellValue(status);
			
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			//status recebimento
			/*****************************************/
			double quantidade_recebida = cadastro.getQuantidade_recebida();
			double quantidade_sacos_sub = 0;
			double quantidade_quilogramas_sub = 0;
			String status_rec = "";
			if (cadastro.getMedida().equalsIgnoreCase("Sacos")) {
				quantidade_sacos_sub = cadastro.getQuantidade();
				quantidade_quilogramas_sub = cadastro.getQuantidade() * 60;
			} else if (cadastro.getMedida().equalsIgnoreCase("KG")) {
				quantidade_quilogramas_sub = cadastro.getQuantidade();
				quantidade_sacos_sub = cadastro.getQuantidade() / 60;

			}

			if (quantidade_recebida >= (quantidade_sacos_sub - 0.5)) {
				status_rec = "CONCLUIDO";
				cell.setCellStyle(celula_fundo_verde);

			} else if (quantidade_recebida < quantidade_sacos_sub && quantidade_recebida > 0) {
				status_rec = "RECEBENDO";
				cell.setCellStyle(celula_fundo_azul);


			} else if (quantidade_recebida == 0) {
				status_rec = "A RECEBER";
				cell.setCellStyle(celula_fundo_amarelo);


			}
			/******************************************/
			cell.setCellValue(status_rec);
			

			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			//status CARREGAMENTO
			/**************************************************/
			String status_carg = "";
			double quantidade_carregada = cadastro.getQuantidade_carregada();


			if (cadastro.getMedida().equalsIgnoreCase("Sacos")) {
				quantidade_sacos_sub = cadastro.getQuantidade();
				quantidade_quilogramas_sub = cadastro.getQuantidade() * 60;
			} else if (cadastro.getMedida().equalsIgnoreCase("KG")) {
				quantidade_quilogramas_sub = cadastro.getQuantidade();
				quantidade_sacos_sub = cadastro.getQuantidade() / 60;

			}

			if (quantidade_recebida >= (quantidade_sacos_sub - 0.5)) {
				// recebimento concluido
				if (quantidade_carregada >= (quantidade_recebida - 0.5)) {
					status_carg = "CONCLUIDO";
					cell.setCellStyle(celula_fundo_verde);

				} else if (quantidade_carregada < quantidade_recebida && quantidade_carregada > 0) {
					status_carg =  "CARREGANDO";
					cell.setCellStyle(celula_fundo_azul);


				} else if (quantidade_carregada == 0) {
					status_carg =  "A CARREGAR";
					cell.setCellStyle(celula_fundo_amarelo);

				}

			} else if (quantidade_recebida < quantidade_sacos_sub && quantidade_recebida > 0) {
				if (quantidade_carregada >= (quantidade_recebida - 0.5)) {
					status_carg =  "CARREGADO PARC";
				} else if (quantidade_carregada < quantidade_recebida
						&& quantidade_carregada > (quantidade_recebida / 2)) {
					status_carg =  "CARREGANDO";
					cell.setCellStyle(celula_fundo_azul);

				} else if (quantidade_carregada == 0) {
					status_carg =  "A CARREGAR";
					cell.setCellStyle(celula_fundo_amarelo);


				}
			}else if(quantidade_recebida == 0) {
				if (quantidade_carregada > 0) {
					status_carg =  "CARREGANDO";
					cell.setCellStyle(celula_fundo_azul);

				} else if (quantidade_carregada <= 0) {
					status_carg =  "AG RECEBER";
					cell.setCellStyle(celula_fundo_amarelo);

				}

			

			}
			cell.setCellValue(status_carg);

			
			/***************************************************/


			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			//status pagamento
			String status_pag = "";
			double quantidade_paga = cadastro.getTotal_pago();
			
			double valor_total_pago = cadastro.getValor_a_pagar().doubleValue();

			if (quantidade_paga >= (valor_total_pago - 0.5)) {
				status_pag = "CONCLUIDO";
				cell.setCellStyle(celula_fundo_verde);

			} else if (quantidade_paga < valor_total_pago && quantidade_paga > 0) {
				status_pag = "PAGANDO";
				cell.setCellStyle(celula_fundo_azul);


			} else if (quantidade_paga == 0) {
				status_pag = "A PAGAR";
				cell.setCellStyle(celula_fundo_amarelo);


			}
			
			cell.setCellValue(status_pag);

			cell = row.createCell(cellnum++);
			cell.setCellStyle(numberStyle);
			cell.setCellValue(cadastro.getQuantidade());

			double quantidade_local_kg = 0;
			double quantidade_local_sacos = 0;

			if (cadastro.getMedida().equalsIgnoreCase("KG")) {
				quantidade_local_kg = cadastro.getQuantidade();
				quantidade_local_sacos = quantidade_local_kg / 60;
			} else if (cadastro.getMedida().equalsIgnoreCase("Sacos")) {
				quantidade_local_sacos = cadastro.getQuantidade();
				quantidade_local_kg = cadastro.getQuantidade() * 60;
			}

			quantidade_total_sacos = quantidade_total_sacos += quantidade_local_sacos;
			quantidade_total_kgs = quantidade_total_kgs += quantidade_local_kg;

			cell = row.createCell(cellnum++);
			cell.setCellStyle(numberStyle);
			String medida = "";
			if (cadastro.getMedida().equalsIgnoreCase("Sacos")) {
				medida = "SC";
			} else if (cadastro.getMedida().equalsIgnoreCase("Kg")) {
				medida = "KG";

			}
			cell.setCellValue(medida);

			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			String produto = "";
			cell.setCellValue(cadastro.getProduto().replaceAll(" NON-GMO", "").replaceAll("GMO", ""));

			cell = row.createCell(cellnum++);
			cell.setCellStyle(numberStyle);
			String transgenia = "";
			if (cadastro.getModelo_safra().getProduto().getTransgenia().contains("CONVENCIONAL")) {
				transgenia = "CONVEN.";
			} else {
				transgenia = "TRANSG.";

			}
			cell.setCellValue(transgenia);

			cell = row.createCell(cellnum++);
			cell.setCellStyle(numberStyle);
			String ano_plantio = Integer.toString(cadastro.getModelo_safra().getAno_plantio()).replaceAll("[^0-9]", "");
			String ano_colheita = Integer.toString(cadastro.getModelo_safra().getAno_colheita()).replaceAll("[^0-9]",
					"");

			cell.setCellValue(ano_plantio.substring(ano_plantio.length() - 2) + "/"
					+ ano_colheita.substring(ano_plantio.length() - 2));
			/*
			 * codigo compradores vendedores status quantidade medida produto transgenia
			 * safra valor_produto valor_total data_contrato local_retirada
			 */
			cell = row.createCell(cellnum++);
			cell.setCellStyle(valorStyle);
			cell.setCellValue(cadastro.getValor_produto());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(valorStyle);
			cell.setCellValue(cadastro.getValor_a_pagar().doubleValue());
			valor_total = valor_total += cadastro.getValor_a_pagar().doubleValue();

			cell = row.createCell(cellnum++);
			cell.setCellStyle(numberStyle);
			cell.setCellValue(cadastro.getData_contrato());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(numberStyle);

			GerenciarBancoClientes gerenciar = new GerenciarBancoClientes();
			CadastroCliente local_retirada = gerenciar.getCliente(cadastro.getId_local_retirada());
			String s_local_retirada = "";
			if (local_retirada != null) {
				if (local_retirada.getTipo_pessoa() == 0) {
					s_local_retirada = local_retirada.getNome_empresarial();
				} else {
					s_local_retirada = local_retirada.getNome_fantaia();
				}
			}
			cell.setCellValue(s_local_retirada);

			
			total_recebido += cadastro.getQuantidade_recebida();
			
			total_carregado += cadastro.getQuantidade_carregada();
			
			total_pago += cadastro.getTotal_pago();
			
			
		}
		sheet.setAutoFilter(CellRangeAddress.valueOf("A5:P5"));

		for (int i = 0; i < 16; i++) {
			sheet.autoSizeColumn(i);

		}

		// registra total de sacos, quilogramas, e valor total
		// total de contratos

		NumberFormat z = NumberFormat.getNumberInstance();

		row = sheet.createRow(rownum += 2);
		cellnum = 0;

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("Total Contratos");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue(contratos.size());
		sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 1, 2));

		
		cell = row.createCell(4);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("Total Recebido:");

		cell = row.createCell(5);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue(z.format(total_recebido / 60) + " Kgs | " + z.format(total_recebido) + " sacos");
		sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 5, 6));

		
		
		
		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		cell = row.createCell(0);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("Total Sacos:");

		cell = row.createCell(1);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue(
				z.format(quantidade_total_sacos / 60) + " Kgs | " + z.format(quantidade_total_sacos) + " sacos");
		sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 1, 2));

		
		cell = row.createCell(4);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("Total Carregado:");

		cell = row.createCell(5);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue(z.format(total_carregado / 60) + " Kgs | " + z.format(total_carregado) + " sacos");
		sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 5, 6));

		
		
		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		cell = row.createCell(0);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("Valor Total:");

		cell = row.createCell(1);
		cell.setCellStyle(valorStyle_total);
		cell.setCellValue(valor_total);
		sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 1, 2));

		cell = row.createCell(4);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("Total Pago:");

		cell = row.createCell(5);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue(NumberFormat.getCurrencyInstance(ptBr).format(total_pago));
		sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 5, 6));

		
		
		return workbook;
	}

	
	
	
}

