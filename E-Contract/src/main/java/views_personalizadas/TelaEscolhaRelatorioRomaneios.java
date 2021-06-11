
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

public class TelaEscolhaRelatorioRomaneios extends JDialog {

	private TelaEscolhaRelatorioRomaneios isto;
	private FileChooser fileChooser;
	private JRadioButton rdbtnCompleto, rdbtnSimples, rdbtnPdf, rdbtnExcel;
	private JRadioButton rdbtnMonsanto, rdbtnUmidade;
	private JComboBox cbArmazem;
	private CBLocalRetiradaPersonalizado modelLocalRetirada = new CBLocalRetiradaPersonalizado();
	private static ArrayList<CadastroCliente> armazens = new ArrayList<>();

	public TelaEscolhaRelatorioRomaneios(ArrayList<CadastroRomaneio> romaneios, Window janela_pai) {
		getContentPane().setBackground(Color.WHITE);

		setBounds(100, 100, 490, 276);
		isto = this;
		getContentPane().setLayout(new MigLayout("", "[grow][]", "[grow][][][grow][][grow][][]"));

		JButton btnRelatrioDeRomaneios = new JButton("Relatório de Romaneios");
		btnRelatrioDeRomaneios.setForeground(Color.WHITE);
		btnRelatrioDeRomaneios.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnRelatrioDeRomaneios.setBorder(null);
		btnRelatrioDeRomaneios.setBackground(new Color(0, 51, 0));
		getContentPane().add(btnRelatrioDeRomaneios, "cell 0 0 2 1,grow");

		JLabel lblArmazm = new JLabel("Armazém:");
		lblArmazm.setForeground(Color.BLACK);
		lblArmazm.setFont(new Font("Tahoma", Font.PLAIN, 18));
		getContentPane().add(lblArmazm, "flowx,cell 0 1");

		JLabel lblNewLabel = new JLabel("Tipo:");
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		getContentPane().add(lblNewLabel, "flowx,cell 0 2,alignx left");

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBackground(Color.WHITE);
		getContentPane().add(panel, "cell 0 3 2 1,grow");
		panel.setLayout(new MigLayout("", "[][][][][]", "[]"));

		rdbtnCompleto = new JRadioButton("Completo");
		rdbtnCompleto.setBackground(new Color(0, 102, 102));
		rdbtnCompleto.setForeground(Color.BLACK);

		rdbtnSimples = new JRadioButton("Simples");
		rdbtnSimples.setForeground(Color.BLACK);
		rdbtnSimples.setBackground(new Color(0, 102, 102));

		rdbtnSimples.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel.add(rdbtnSimples, "cell 1 0");
		rdbtnCompleto.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel.add(rdbtnCompleto, "cell 2 0");

		rdbtnMonsanto = new JRadioButton("Monsanto");
		rdbtnMonsanto.setForeground(Color.BLACK);
		rdbtnMonsanto.setFont(new Font("Tahoma", Font.BOLD, 16));
		rdbtnMonsanto.setBackground(new Color(0, 102, 102));
		panel.add(rdbtnMonsanto, "cell 3 0");

		rdbtnUmidade = new JRadioButton("Classificação 2");
		rdbtnUmidade.setForeground(Color.BLACK);
		rdbtnUmidade.setFont(new Font("Tahoma", Font.BOLD, 16));
		rdbtnUmidade.setBackground(new Color(0, 102, 102));
		panel.add(rdbtnUmidade, "cell 4 0");

		JLabel lblSada = new JLabel("Saída:");
		lblSada.setForeground(Color.BLACK);
		lblSada.setFont(new Font("Tahoma", Font.PLAIN, 18));
		getContentPane().add(lblSada, "cell 0 4,alignx left");

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBackground(Color.WHITE);
		getContentPane().add(panel_1, "cell 0 5 2 1,alignx center,growy");
		panel_1.setLayout(new MigLayout("", "[53px][43px][]", "[23px]"));

		rdbtnPdf = new JRadioButton("Pdf");
		rdbtnPdf.setBackground(new Color(0, 102, 102));
		rdbtnPdf.setForeground(Color.BLACK);
		rdbtnPdf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnExcel.setSelected(false);
				rdbtnPdf.setSelected(true);
			}
		});

		rdbtnExcel = new JRadioButton("Excel");
		rdbtnExcel.setBackground(new Color(0, 102, 102));
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
						gerarExcel(preparar(romaneios, 1));
					} else if (rdbtnPdf.isSelected()) {
						gerarPdf(preparar(romaneios, 1));

					}

				} else if (rdbtnSimples.isSelected()) {
					// relatorio simples
					if (rdbtnExcel.isSelected()) {
						gerarExcel(preparar(romaneios, 0));
					} else if (rdbtnPdf.isSelected()) {
						gerarPdf(preparar(romaneios, 0));

					}
				} else if (rdbtnMonsanto.isSelected()) {

					if (rdbtnExcel.isSelected()) {
						gerarExcel(prepararMonsanto(romaneios, 0));
					} else if (rdbtnPdf.isSelected()) {
						gerarPdf(prepararMonsanto(romaneios, 0));

					}
				}

			}
		});
		btnNewButton_1.setFont(new Font("Arial", Font.PLAIN, 14));
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.setBackground(new Color(0, 0, 51));
		getContentPane().add(btnNewButton_1, "cell 1 7,alignx right");

		cbArmazem = new JComboBox();
		getContentPane().add(cbArmazem, "cell 0 1 2 1,growx");
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

	public HSSFWorkbook preparar(ArrayList<CadastroRomaneio> romaneios_selecionados, int flag) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Exportação de Dados de Romaneios");

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

		celula_fundo_laranja_texto_branco.setFont(newFont_branca);

		// Configurando as informacoes
		row = sheet.createRow(rownum);

		Locale ptBr = new Locale("pt", "BR");

		// Configurando Header
		row = sheet.createRow(rownum++);
		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("DATA");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("CÓDIGO ROMANEIO".toUpperCase());

		if (flag == 1) {
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja_texto_branco);
			cell.setCellValue("OPERAÇÃO".toUpperCase());
		}

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("PRODUTO".toUpperCase());

		if (flag == 1) {
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja_texto_branco);
			cell.setCellValue("TRANSGENIA".toUpperCase());
		}

		if (flag == 1) {
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja_texto_branco);
			cell.setCellValue("SAFRA".toUpperCase());
		}

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("NOME REMETENTE".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("CPF/CNPJ REMETENTE".toUpperCase());

		if (flag == 1) {
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja_texto_branco);
			cell.setCellValue("DESTINATÁRIO".toUpperCase());
		}

		if (flag == 1) {
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja_texto_branco);
			cell.setCellValue("CPF/CNPJ DESTINATÁRIO".toUpperCase());
		}

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("PESO BRUTO".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("PESO TARA".toUpperCase());

		if (flag == 1) {
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja_texto_branco);
			cell.setCellValue("PESO LIQ. S/ DESC".toUpperCase());
		}

		if (flag == 1) {
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja_texto_branco);
			cell.setCellValue("PESO DESC. UMI.".toUpperCase());
		}

		if (flag == 1) {
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja_texto_branco);
			cell.setCellValue("PESO DESC. IMP.".toUpperCase());
		}

		if (flag == 1) {
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja_texto_branco);
			cell.setCellValue("PESO DESC. AVA.".toUpperCase());
		}

		if (flag == 1) {
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja_texto_branco);
			cell.setCellValue("PESO DESC. TOTAL.".toUpperCase());
		}

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("PESO LIQ FINAL".toUpperCase());

		if (flag == 1) {
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja_texto_branco);
			cell.setCellValue("PESO RECEPÇÃO".toUpperCase());
		}

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("UMIDADE".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("IMPUREZA".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("ARDIDOS".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("AVARIADO".toUpperCase());

		if (flag == 1) {
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja_texto_branco);
			cell.setCellValue("CFOP".toUpperCase());
		}

		if (flag == 1) {
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja_texto_branco);
			cell.setCellValue("DESCRICAO".toUpperCase());
		}

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("MOTORISTA".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("CPF MOTORISTA".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("PLACA".toUpperCase());

		if (flag == 1) {
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja_texto_branco);
			cell.setCellValue("DOC ENTRADA".toUpperCase());
		}

		if (flag == 1) {
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja_texto_branco);
			cell.setCellValue("AMOSTRA".toUpperCase());
		}

		if (flag == 1) {
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja_texto_branco);
			cell.setCellValue("SILO".toUpperCase());
		}

		if (flag == 1) {
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_laranja_texto_branco);
			cell.setCellValue("TRANSGENESE".toUpperCase());
		}

		int numero_romaneios = 0;

		double peso_bruto_total = 0, peso_tara_total = 0, peso_liquido_total_sem_desconto = 0, peso_liquido_total = 0;
		double peso_desconto_umidade = 0;
		double peso_desconto_impureza = 0;
		double peso_desconto_avariado = 0;
		double peso_desconto_total = 0;
		double peso_recepcao = 0;

		for (CadastroRomaneio romaneio : romaneios_selecionados) {

			peso_bruto_total += romaneio.getPeso_bruto();
			peso_tara_total += romaneio.getTara();
			peso_liquido_total += romaneio.getPeso_liquido();
			peso_liquido_total_sem_desconto += romaneio.getPeso_liquido_sem_descontos();

			peso_desconto_umidade += romaneio.getPeso_desconto_umidade();
			peso_desconto_impureza += romaneio.getPeso_desconto_impureza();
			peso_desconto_avariado += romaneio.getPeso_desconto_avariados();
			peso_desconto_total += romaneio.getPeso_desconto_total();
			peso_recepcao += romaneio.getDespesa_recepcao();

			numero_romaneios++;
			CadastroCliente remetente = romaneio.getRemetente();
			CadastroCliente destinatario = romaneio.getDestinatario();

			row = sheet.createRow(rownum++);
			cellnum = 0;
			SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
			Date data = romaneio.getData();
			String data_formatada = "";
			if (data instanceof Date) {
				data_formatada = f.format(data);
			}
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(data_formatada);

			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(romaneio.getNumero_romaneio());

			if (flag == 1) {
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue(romaneio.getOperacao());
			}

			// produto
			try {
				CadastroProduto prod = romaneio.getProduto();
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue(prod.getNome_produto());

			} catch (Exception h) {
				// JOptionPane.showMessageDialog(null, "O romaneio codigo: " +
				// romaneio.getNumero_romaneio() + " possui erro no produto");
			}

			if (flag == 1) {
				// transgenia
				CadastroProduto prod = romaneio.getProduto();
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue(romaneio.getProduto().getTransgenia().toUpperCase());
			}

			if (flag == 1) {
				// safra

				CadastroSafra safra = romaneio.getSafra();
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue(safra.getAno_plantio() + "/" + safra.getAno_colheita());
			}

			// nome remetente

			try {
				String nome_cliente = "";

				if (remetente != null) {
					if (remetente.getTipo_pessoa() == 0) {
						nome_cliente = remetente.getNome_empresarial().toUpperCase();
					} else
						nome_cliente = remetente.getNome_fantaia().toUpperCase();
				}
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue(nome_cliente);

			} catch (Exception t) {
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue("");
			}

			// cpf_cnpj_remetente
			try {
				if (remetente.getTipo_pessoa() == 0) {
					cell = row.createCell(cellnum++);
					cell.setCellStyle(textStyle);
					cell.setCellValue(remetente.getCpf());

				} else {
					cell = row.createCell(cellnum++);
					cell.setCellStyle(textStyle);
					cell.setCellValue(remetente.getCnpj());
				}
			} catch (Exception y) {
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue("");
			}

			if (flag == 1) {
				// nome destinatario
				String nome_cliente = "";
				try {

					if (destinatario != null) {
						if (destinatario.getTipo_pessoa() == 0) {
							if (destinatario.getNome_empresarial() != null) {
								nome_cliente = destinatario.getNome_empresarial().toUpperCase();
							}
						} else {
							if (destinatario.getNome_fantaia() != null) {
								nome_cliente = destinatario.getNome_fantaia().toUpperCase();
							}
						}

					} else {
						nome_cliente = ("");

					}
				} catch (Exception u) {
					nome_cliente = ("");

				}

				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue(nome_cliente);
			}

			if (flag == 1) {
				// cpf_cnpj destinatario
				String identificacao_destinatario = "";
				try {
					if (destinatario.getTipo_pessoa() == 0) {
						identificacao_destinatario = destinatario.getCpf();

					} else {
						identificacao_destinatario = destinatario.getCnpj();
					}
				} catch (Exception p) {
					identificacao_destinatario = ("");

				}
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue(identificacao_destinatario);
			}

			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(romaneio.getPeso_bruto());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(romaneio.getTara());

			if (flag == 1) {
				// peso liquido sem desconto
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue(romaneio.getPeso_liquido_sem_descontos());
			}

			if (flag == 1) {
				// pseo desconto umidade
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue(romaneio.getPeso_desconto_umidade());
			}

			if (flag == 1) {
				// peso desconto impureza
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue(romaneio.getPeso_desconto_impureza());
			}

			if (flag == 1) {
				// pseo desconto avariado
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue(romaneio.getPeso_desconto_avariados());
			}

			if (flag == 1) {
				// peso desconto total
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue(romaneio.getPeso_desconto_total());
			}

			// peso liquido final
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(romaneio.getPeso_liquido());

			if (flag == 1) {
				// peso recepção
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue(romaneio.getDespesa_recepcao());
			}

			// umidade
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(romaneio.getUmidade());

			// impureza
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(romaneio.getInpureza());

			// ardidos
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(romaneio.getArdidos());

			// avariados
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(romaneio.getAvariados());

			if (flag == 1) {
				// cfop
				try {
					if (romaneio.getCfop() != null) {
						cell = row.createCell(cellnum++);
						cell.setCellStyle(textStyle);
						cell.setCellValue(romaneio.getCfop());

					} else {
						cell = row.createCell(cellnum++);
						cell.setCellStyle(textStyle);
						cell.setCellValue("");

					}
				} catch (Exception k) {
					cell = row.createCell(cellnum++);
					cell.setCellStyle(textStyle);
					cell.setCellValue("");

				}
			}

			if (flag == 1) {
				// descricao
				try {
					if (romaneio.getDescricao_cfop().toUpperCase() != null) {
						cell = row.createCell(cellnum++);
						cell.setCellStyle(textStyle);
						cell.setCellValue(romaneio.getDescricao_cfop().toUpperCase());

					} else {
						cell = row.createCell(cellnum++);
						cell.setCellStyle(textStyle);
						cell.setCellValue("");

					}
				} catch (Exception w) {
					cell = row.createCell(cellnum++);
					cell.setCellStyle(textStyle);
					cell.setCellValue("");

				}
			}

			// motorista
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(romaneio.getNome_motorista());

			// cpf_motorista
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(romaneio.getCpf_motorista());

			// placa
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(romaneio.getPlaca());

			if (flag == 1) {
				// doc_entrda
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue(romaneio.getDoc_entrada());
			}

			if (flag == 1) {
				// amostra
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue(romaneio.getAmostra());
			}

			if (flag == 1) {
				// silo
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue(romaneio.getSilo());
			}

			if (flag == 1) {
				// transgenese
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue(romaneio.getTransgenia());
			}

		}
		sheet.setAutoFilter(CellRangeAddress.valueOf("A1:AF1"));
		NumberFormat z = NumberFormat.getNumberInstance();

		// somatorias
		/*
		 * // valores
		 * 
		 * lblPesoBrutoTotal.setText(z.format(peso_bruto_total) + " Kgs | " +
		 * z.format(peso_bruto_total / 60) + " sacos"); lblPesoLiquidoTotal
		 * .setText(z.format(peso_liquido_total) + " Kgs | " +
		 * z.format(peso_liquido_total / 60) + " sacos");
		 * lblPesoLiquidoTotalSemDesconto.setText(z.format(
		 * peso_liquido_total_sem_desconto) + " Kgs | " +
		 * z.format(peso_liquido_total_sem_desconto / 60) + " sacos");
		 * 
		 * lblDescontoTotalUmidade .setText(z.format(peso_desconto_umidade) + " Kgs | "
		 * + z.format(peso_desconto_umidade / 60) + " sacos");
		 * lblDescontoTotalImpureza.setText( z.format(peso_desconto_impureza) +
		 * " Kgs | " + z.format(peso_desconto_impureza / 60) + " sacos");
		 * lblDescontoTotalAvariado.setText( z.format(peso_desconto_avariado) +
		 * " Kgs | " + z.format(peso_desconto_avariado / 60) + " sacos");
		 * lblPesoTotalDesconto .setText(z.format(peso_desconto_total) + " Kgs | " +
		 * z.format(peso_desconto_total / 60) + " sacos");
		 * 
		 * lblPesoTaraTotal.setText(z.format(peso_tara_total) + " Kgs | " +
		 * z.format(peso_tara_total / 60) + " sacos");
		 * lblNumeroTotalRomaneios.setText(numero_romaneios + " Romaneios");
		 */
		row = sheet.createRow(rownum += 2);
		cellnum = 0;

		cell = row.createCell(cellnum++);
		cell.setCellStyle(textStyle);
		cell.setCellValue("ROMANEIO");
		sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, cellnum, 5));

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue(numero_romaneios + " Romaneios");

		// LINHA PESO BRUTO TOTAL
		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		cell = row.createCell(cellnum++);
		cell.setCellStyle(textStyle);
		cell.setCellValue("P.B.Total:");
		sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, cellnum, 5));

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue(z.format(peso_bruto_total) + " Kgs | " + z.format(peso_bruto_total / 60) + " sacos");

		// LINHA peso tara
		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		cell = row.createCell(cellnum++);
		cell.setCellStyle(textStyle);
		cell.setCellValue("P.B.Tara:");
		sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, cellnum, 5));

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue(z.format(peso_tara_total) + " Kgs | " + z.format(peso_tara_total / 60) + " sacos");

		// LINHA peso liq sem desconto
		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		cell = row.createCell(cellnum++);
		cell.setCellStyle(textStyle);
		cell.setCellValue("P. LIQ. FINAL S/ DESC:");
		sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, cellnum, 5));

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue(z.format(peso_liquido_total_sem_desconto) + " Kgs | "
				+ z.format(peso_liquido_total_sem_desconto / 60) + " sacos");

		// LINHA peso liq final
		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		cell = row.createCell(cellnum++);
		cell.setCellStyle(textStyle);
		cell.setCellValue("P. LIQ. FINAL");
		sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, cellnum, 5));

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue(z.format(peso_liquido_total) + " Kgs | " + z.format(peso_liquido_total / 60) + " sacos");

		return workbook;
	}

	public HSSFWorkbook prepararMonsanto(ArrayList<CadastroRomaneio> romaneios_selecionados, int flag) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Exportação de Dados de Monsanto");

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
		HSSFFont newFont_blabk = workbook.createFont();
		newFont_blabk.setBold(true);
		newFont_blabk.setColor(IndexedColors.BLACK.getIndex());
		newFont_blabk.setFontName("Calibri");
		newFont_blabk.setItalic(false);
		newFont_blabk.setFontHeight((short) (11 * 24));

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
		celula_fundo_verde.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
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

		// estilo para cabecalho
		CellStyle celula_cabecalho = workbook.createCellStyle();
		celula_cabecalho.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		celula_cabecalho.setFillForegroundColor(IndexedColors.BROWN.getIndex());
		celula_cabecalho.setAlignment(HorizontalAlignment.CENTER);
		celula_cabecalho.setVerticalAlignment(VerticalAlignment.CENTER);
		celula_cabecalho.setFont(newFont_blabk);

		HSSFFont newFont_titulo = workbook.createFont();
		newFont_titulo.setBold(true);
		newFont_titulo.setColor(IndexedColors.WHITE.getIndex());
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
		newFont_branca.setFontHeight((short) (11 * 24));

		celula_fundo_laranja_texto_branco.setFont(newFont_branca);
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
		cell.setCellValue("Controle Fitas de Testes Identificação Tecnologia em SOJA - MONSANTO");
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
			cell.setCellValue("Cnpj Unidade: " );

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

		// Configurando Header
		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_cabecalho);
		cell.setCellValue("DATA");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_cabecalho);
		cell.setCellValue("CÓDIGO ROMANEIO".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_cabecalho);
		cell.setCellValue("PLACA".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_cabecalho);
		cell.setCellValue("PESO COM DESCONTO".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_cabecalho);
		cell.setCellValue("NOME PRODUTOR".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_cabecalho);
		cell.setCellValue("CPF/CNPJ".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_cabecalho);
		cell.setCellValue("TRANSGENIA".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_cabecalho);
		cell.setCellValue("ROYALTIES".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_cabecalho);
		cell.setCellValue("STATUS MONSANTO".toUpperCase());
		NumberFormat z = NumberFormat.getNumberInstance();

		int numero_romaneios = 0;

		double peso_bruto_total = 0, peso_tara_total = 0, peso_liquido_total_sem_desconto = 0, peso_liquido_total = 0;
		double peso_desconto_umidade = 0;
		double peso_desconto_impureza = 0;
		double peso_desconto_avariado = 0;
		double peso_desconto_total = 0;
		double peso_recepcao = 0;
		
		double peso_total_royalties = 0;
		double peso_total_participante = 0;
		double peso_total_paticular = 0;
		double peso_total_outros_particular = 0;
		double peso_total_outros_participante = 0;

		
		double peso_total_its_declarado = 0;
		double peso_total__its_a_declarar = 0;


		for (CadastroRomaneio romaneio : romaneios_selecionados) {

			peso_bruto_total += romaneio.getPeso_bruto();
			peso_tara_total += romaneio.getTara();
			peso_liquido_total += romaneio.getPeso_liquido();
			peso_liquido_total_sem_desconto += romaneio.getPeso_liquido_sem_descontos();

			peso_desconto_umidade += romaneio.getPeso_desconto_umidade();
			peso_desconto_impureza += romaneio.getPeso_desconto_impureza();
			peso_desconto_avariado += romaneio.getPeso_desconto_avariados();
			peso_desconto_total += romaneio.getPeso_desconto_total();
			peso_recepcao += romaneio.getDespesa_recepcao();
			
			//monsanto
			if(romaneio.getRoyalties() == 1) {
				//e monsanto
				peso_total_royalties += romaneio.getPeso_liquido();
				
				//particular
				if(romaneio.getStatus_monsanto() == 0 || romaneio.getStatus_monsanto() == 1) {
					peso_total_paticular +=  romaneio.getPeso_liquido();
					
				if(romaneio.getStatus_monsanto() == 0) {
					//falta its
					peso_total__its_a_declarar += romaneio.getPeso_liquido();
					
				}else if(romaneio.getStatus_monsanto() == 1) {
					//ok its
					peso_total_its_declarado += romaneio.getPeso_liquido();
				
				}
				
				}
				 if(romaneio.getStatus_monsanto() == 2) {
					//participante cj
					peso_total_participante += romaneio.getPeso_liquido();
				}else if (romaneio.getStatus_monsanto() == 3) {
					//não aplicavel
				}
				
			}else {
				 if(romaneio.getStatus_monsanto() == 2) {
						//participante cj
					 peso_total_outros_participante += romaneio.getPeso_liquido();
					}else {
						peso_total_outros_particular += romaneio.getPeso_liquido();
					}
			}

			numero_romaneios++;
			CadastroCliente remetente = romaneio.getRemetente();
			CadastroCliente destinatario = romaneio.getDestinatario();

			row = sheet.createRow(rownum++);
			cellnum = 0;
			SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
			Date data = romaneio.getData();
			String data_formatada = "";
			if (data instanceof Date) {
				data_formatada = f.format(data);
			}

			// DATA
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(data_formatada);

			// numero romaneio
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(romaneio.getNumero_romaneio());

			// placa
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(romaneio.getPlaca());

			// peso liquido final
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(z.format(romaneio.getPeso_liquido()));

			// nome remetente
			String nome_cliente = "";
			String identificacao = "";

			if (romaneio.getStatus_monsanto() == 2) {
				// nome destinatario
				try {

					if (destinatario != null) {
						if (destinatario.getTipo_pessoa() == 0) {
							if (destinatario.getNome_empresarial() != null) {
								nome_cliente = destinatario.getNome_empresarial().toUpperCase();
							}
						} else {
							if (destinatario.getNome_fantaia() != null) {
								nome_cliente = destinatario.getNome_fantaia().toUpperCase();
							}
						}

					} else {
						nome_cliente = ("");

					}
				} catch (Exception u) {
					nome_cliente = ("");

				}

				// cpf_cnpj destinatario
				try {
					if (destinatario.getTipo_pessoa() == 0) {
						identificacao = destinatario.getCpf();
						MaskFormatter formater_cpf = new MaskFormatter("###.###.###-##");
						formater_cpf.setValueContainsLiteralCharacters(false);
						identificacao = formater_cpf.valueToString(identificacao);

					} else {
						identificacao = destinatario.getCnpj();
						formater_cnpj.setValueContainsLiteralCharacters(false);
						identificacao = formater_cnpj.valueToString(identificacao);

					}
				} catch (Exception p) {
					identificacao = ("");

				}

			} else {
				// retorna nome do remetente
				// participante cj, retorna o nome do destinatario
				try {

					if (remetente != null) {
						if (remetente.getTipo_pessoa() == 0) {
							nome_cliente = remetente.getNome_empresarial().toUpperCase();
						} else
							nome_cliente = remetente.getNome_fantaia().toUpperCase();
					}

				} catch (Exception t) {

					nome_cliente = "";
				}

				// cpf_cnpj_remetente
				try {
					if (remetente.getTipo_pessoa() == 0) {

						identificacao = remetente.getCpf();
						MaskFormatter formater_cpf = new MaskFormatter("###.###.###-##");
						formater_cpf.setValueContainsLiteralCharacters(false);
						identificacao = formater_cpf.valueToString(identificacao);

					} else {

						identificacao = remetente.getCnpj();
						formater_cnpj.setValueContainsLiteralCharacters(false);
						identificacao = formater_cnpj.valueToString(identificacao);

					}
				} catch (Exception y) {

					identificacao = "";
				}

			}

			// prdutor rural
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(nome_cliente);

			// cpf/cnpj
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(identificacao);

			// transgenia
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(romaneio.getTransgenia());

			// royalties
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			if (romaneio.getRoyalties() == 0) {
				cell.setCellValue("NÃO");

			} else {
				cell.setCellValue("SIM");

			}

			// status monsanto
			cell = row.createCell(cellnum++);
			int sts_monsanto = romaneio.getStatus_monsanto();
			if (sts_monsanto == 0) {
				cell.setCellStyle(celula_fundo_vermelho);

				cell.setCellValue("FALTA ITS");

			} else if (sts_monsanto == 1) {
				cell.setCellStyle(celula_fundo_verde);

				cell.setCellValue("OK ITS");

			} else if (sts_monsanto == 2) {
				cell.setCellStyle(celula_fundo_azul);

				cell.setCellValue("PARTICIPANTE");

			} else if (sts_monsanto == 3) {
				cell.setCellStyle(celula_fundo_amarelo);

				cell.setCellValue("NÃO APLICÁVEL");

			}

		}
		sheet.setAutoFilter(CellRangeAddress.valueOf("A4:AF4"));
		for (int i = 1; i < 9; i++) {
			sheet.autoSizeColumn(i);

		}

		// somatorias
		/*
		 * // valores
		 * 
		 * lblPesoBrutoTotal.setText(z.format(peso_bruto_total) + " Kgs | " +
		 * z.format(peso_bruto_total / 60) + " sacos"); lblPesoLiquidoTotal
		 * .setText(z.format(peso_liquido_total) + " Kgs | " +
		 * z.format(peso_liquido_total / 60) + " sacos");
		 * lblPesoLiquidoTotalSemDesconto.setText(z.format(
		 * peso_liquido_total_sem_desconto) + " Kgs | " +
		 * z.format(peso_liquido_total_sem_desconto / 60) + " sacos");
		 * 
		 * lblDescontoTotalUmidade .setText(z.format(peso_desconto_umidade) + " Kgs | "
		 * + z.format(peso_desconto_umidade / 60) + " sacos");
		 * lblDescontoTotalImpureza.setText( z.format(peso_desconto_impureza) +
		 * " Kgs | " + z.format(peso_desconto_impureza / 60) + " sacos");
		 * lblDescontoTotalAvariado.setText( z.format(peso_desconto_avariado) +
		 * " Kgs | " + z.format(peso_desconto_avariado / 60) + " sacos");
		 * lblPesoTotalDesconto .setText(z.format(peso_desconto_total) + " Kgs | " +
		 * z.format(peso_desconto_total / 60) + " sacos");
		 * 
		 * lblPesoTaraTotal.setText(z.format(peso_tara_total) + " Kgs | " +
		 * z.format(peso_tara_total / 60) + " sacos");
		 * lblNumeroTotalRomaneios.setText(numero_romaneios + " Romaneios");
		 */
		row = sheet.createRow(rownum += 2);
		cellnum = 0;

		cell = row.createCell(cellnum++);
		cell.setCellStyle(textStyle);
		cell.setCellValue("ROMANEIO");
		sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, cellnum, 3));

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue(numero_romaneios + " Romaneios");

		
		cell = row.createCell(5);
		cell.setCellStyle(textStyle);
		cell.setCellValue("Total Royalties:");
		sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 6, 8));

		cell = row.createCell(6);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue(z.format(peso_total_royalties ) + " Kgs | " + z.format(peso_total_royalties  / 60) + " sacos");

		
		
		
		
		// LINHA PESO BRUTO TOTAL
		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		cell = row.createCell(cellnum++);
		cell.setCellStyle(textStyle);
		cell.setCellValue("P.B.Total:");
		sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, cellnum, 3));

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue(z.format(peso_bruto_total) + " Kgs | " + z.format(peso_bruto_total / 60) + " sacos");

		cell = row.createCell(5);
		cell.setCellStyle(textStyle);
		cell.setCellValue("Royalties Participante:");
		sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 6, 8));

		cell = row.createCell(6);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue(z.format(peso_total_participante ) + " Kgs | " + z.format(peso_total_participante  / 60) + " sacos");

		
		
		// LINHA peso tara
		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		cell = row.createCell(cellnum++);
		cell.setCellStyle(textStyle);
		cell.setCellValue("P.B.Tara:");
		sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, cellnum, 3));

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue(z.format(peso_tara_total) + " Kgs | " + z.format(peso_tara_total / 60) + " sacos");

		cell = row.createCell(5);
		cell.setCellStyle(textStyle);
		cell.setCellValue("Royalties Particular:");
		sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 6, 8));

		cell = row.createCell(6);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue(z.format(peso_total_paticular  ) + " Kgs | " + z.format(peso_total_paticular   / 60) + " sacos");

		
		// LINHA peso liq sem desconto
		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		cell = row.createCell(cellnum++);
		cell.setCellStyle(textStyle);
		cell.setCellValue("P. LIQ. FINAL S/ DESC:");
		sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, cellnum, 3));

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue(z.format(peso_liquido_total_sem_desconto) + " Kgs | "
				+ z.format(peso_liquido_total_sem_desconto / 60) + " sacos");

		cell = row.createCell(5);
		cell.setCellStyle(textStyle);
		cell.setCellValue("Outros Participante:");
		sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 6, 8));

		cell = row.createCell(6);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue(z.format(peso_total_outros_participante   ) + " Kgs | " + z.format(peso_total_outros_participante     / 60) + " sacos");

		
		// LINHA peso liq final
		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		cell = row.createCell(cellnum++);
		cell.setCellStyle(textStyle);
		cell.setCellValue("P. LIQ. FINAL");
		sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, cellnum, 3));

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue(z.format(peso_liquido_total) + " Kgs | " + z.format(peso_liquido_total / 60) + " sacos");

		cell = row.createCell(5);
		cell.setCellStyle(textStyle);
		cell.setCellValue("Outros Particular:");
		sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 6, 8));

		cell = row.createCell(6);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue(z.format(peso_total_outros_particular  ) + " Kgs | " + z.format(peso_total_outros_particular    / 60) + " sacos");

		
		//linha total its baixado
		row = sheet.createRow(rownum += 1);

		cell = row.createCell(5);
		cell.setCellStyle(textStyle);
		cell.setCellValue("Total ITS Baixado:");
		sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 6, 8));

		cell = row.createCell(6);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue(z.format(peso_total_its_declarado ) + " Kgs | " + z.format(peso_total_its_declarado  / 60) + " sacos");

		
		//linha total its a declarar
		row = sheet.createRow(rownum += 1);

		cell = row.createCell(5);
		cell.setCellStyle(textStyle);
		cell.setCellValue("Total ITS a Baixar:");
		sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 6, 8));

		cell = row.createCell(6);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue(z.format(peso_total__its_a_declarar  ) + " Kgs | " + z.format(peso_total__its_a_declarar   / 60) + " sacos");

		
		return workbook;
	}

	public void fechar() {
		isto.dispose();
	}
}
