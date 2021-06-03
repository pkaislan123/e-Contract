
package main.java.views_personalizadas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;
import java.net.URL;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
import main.java.cadastros.Lancamento;
import main.java.conexaoBanco.GerenciarBancoCondicaoPagamentos;
import main.java.cadastros.CadastroSafra;
import main.java.cadastros.CondicaoPagamento;
import main.java.cadastros.Lancamento;
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
import java.math.BigDecimal;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import net.miginfocom.swing.MigLayout;
import javax.swing.JRadioButton;

public class TelaEscolhaRelatorioLancamentos extends JDialog {

	private TelaEscolhaRelatorioLancamentos isto;
	private FileChooser fileChooser;
	private JRadioButton rdbtnCompleto, rdbtnSimples, rdbtnPdf, rdbtnExcel;

	public TelaEscolhaRelatorioLancamentos(ArrayList<Lancamento> lancamentos, Window janela_pai) {
		getContentPane().setBackground(new Color(0, 102, 102));

		setBounds(100, 100, 331, 239);
		isto = this;

		JButton btnNewButton = new JButton("X");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isto.dispose();
			}
		});
		getContentPane().setLayout(new MigLayout("", "[grow][]", "[grow][][grow][][grow][][]"));

		JButton btnRelatrioDelancamentos = new JButton("Relatório de lancamentos");
		btnRelatrioDelancamentos.setForeground(Color.WHITE);
		btnRelatrioDelancamentos.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnRelatrioDelancamentos.setBorder(null);
		btnRelatrioDelancamentos.setBackground(Color.BLACK);
		getContentPane().add(btnRelatrioDelancamentos, "cell 0 0,growx");
		btnNewButton.setBorder(null);
		btnNewButton.setBackground(Color.BLACK);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnNewButton.setForeground(Color.WHITE);
		getContentPane().add(btnNewButton, "cell 1 0,alignx right,growy");

		JLabel lblNewLabel = new JLabel("Tipo:");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		getContentPane().add(lblNewLabel, "cell 0 1,alignx left");

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 102, 102));
		getContentPane().add(panel, "cell 0 2,grow");
		panel.setLayout(new MigLayout("", "[][][]", "[]"));

		rdbtnCompleto = new JRadioButton("Completo");
		rdbtnCompleto.setBackground(new Color(0, 102, 102));
		rdbtnCompleto.setForeground(Color.WHITE);
		rdbtnCompleto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnCompleto.setSelected(true);
				rdbtnSimples.setSelected(false);
			}
		});

		rdbtnSimples = new JRadioButton("Simples");
		rdbtnSimples.setForeground(Color.WHITE);
		rdbtnSimples.setBackground(new Color(0, 102, 102));
		rdbtnSimples.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnSimples.setSelected(true);
				rdbtnCompleto.setSelected(false);
			}
		});
		rdbtnSimples.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel.add(rdbtnSimples, "cell 1 0");
		rdbtnCompleto.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel.add(rdbtnCompleto, "cell 2 0");

		JLabel lblSada = new JLabel("Saída:");
		lblSada.setForeground(Color.WHITE);
		lblSada.setFont(new Font("Tahoma", Font.PLAIN, 18));
		getContentPane().add(lblSada, "cell 0 3,alignx left");

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 102, 102));
		getContentPane().add(panel_1, "cell 0 4,grow");
		panel_1.setLayout(new MigLayout("", "[53px][43px][]", "[23px]"));

		rdbtnPdf = new JRadioButton("Pdf");
		rdbtnPdf.setBackground(new Color(0, 102, 102));
		rdbtnPdf.setForeground(Color.WHITE);
		rdbtnPdf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnExcel.setSelected(false);
				rdbtnPdf.setSelected(true);
			}
		});

		rdbtnExcel = new JRadioButton("Excel");
		rdbtnExcel.setBackground(new Color(0, 102, 102));
		rdbtnExcel.setForeground(Color.WHITE);
		rdbtnExcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnExcel.setSelected(true);
				rdbtnPdf.setSelected(false);

			}
		});
		rdbtnExcel.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_1.add(rdbtnExcel, "cell 1 0,alignx left,aligny top");
		rdbtnPdf.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_1.add(rdbtnPdf, "cell 2 0,alignx left,aligny top");

		JButton btnNewButton_1 = new JButton("Gerar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// gerar

				if (rdbtnCompleto.isSelected()) {
					// relatorio completo
					if (rdbtnExcel.isSelected()) {
						gerarExcel(preparar(lancamentos, 1));
					} else if (rdbtnPdf.isSelected()) {
						gerarPdf(preparar(lancamentos, 1));

					}

				} else if (rdbtnSimples.isSelected()) {
					// relatorio simples
					if (rdbtnExcel.isSelected()) {
						gerarExcel(preparar(lancamentos, 0));
					} else if (rdbtnPdf.isSelected()) {
						gerarPdf(preparar(lancamentos, 0));

					}
				}

			}
		});
		btnNewButton_1.setFont(new Font("Arial", Font.PLAIN, 14));
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.setBackground(new Color(0, 0, 51));
		getContentPane().add(btnNewButton_1, "cell 0 5,alignx right");
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

	public HSSFWorkbook preparar(ArrayList<Lancamento> lancamentos_selecionados, int flag) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Exportação de Dados de lancamentos");

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
		cell.setCellValue("TIPO");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("CLIENTE/FORNECEDOR");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("VALOR TOTAL".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("VALOR PAGO".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("VALOR TOTAL A PAGAR".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("VALOR A VENCER".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("JUROS".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("DATA PROX VENCIMENTO".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("SITUAÇÃO".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("CONDIÇÃO PAGAMENTO".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("STATUS ÚLTIMO PAGAMENTO".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("STATUS CONTADOR".toUpperCase());

		ArrayList<CondicaoPagamento> lista_condicoes = null;
		GerenciarBancoCondicaoPagamentos gerenciar = new GerenciarBancoCondicaoPagamentos();

		lista_condicoes = gerenciar.getCondicaoPagamentos();
		int numero_lancamentos = 0;
		int numero_despesas = 0;
		int numero_despesas_a_pagar = 0;
		int numero_despesas_pago = 0;
		int numero_receitas = 0;
		int numero_receitas_a_receber = 0;
		int numero_receitas_recebido = 0;

		// despesas
		BigDecimal valor_total_despesas = BigDecimal.ZERO;
		BigDecimal valor_a_pagar = BigDecimal.ZERO;
		BigDecimal valor_pago = BigDecimal.ZERO;
		BigDecimal valor_total_vencer_pagar = BigDecimal.ZERO;
		BigDecimal valor_total_juros_pago = BigDecimal.ZERO;

		// receitas
		BigDecimal valor_total_receitas = BigDecimal.ZERO;
		BigDecimal valor_a_receber = BigDecimal.ZERO;
		BigDecimal valor_recebido = BigDecimal.ZERO;
		BigDecimal valor_total_vencer_receber = BigDecimal.ZERO;
		BigDecimal valor_total_juros_recebido = BigDecimal.ZERO;

		for (Lancamento lancamento : lancamentos_selecionados) {

			if(lancamento.getStatus() == 0) {
				//despesas a pagar
				valor_total_despesas = valor_total_despesas.add(lancamento.getValor());
				valor_a_pagar = valor_a_pagar.add(lancamento.getValor().subtract(lancamento.getValor_ja_pago()));
				
				valor_pago = valor_pago.add(lancamento.getValor_ja_pago());
				numero_despesas_a_pagar++;
		
				BigDecimal valor_total = lancamento.getValor();
				BigDecimal valor__ja_pago = lancamento.getValor_ja_pago();
				
				BigDecimal valor_restante = valor__ja_pago.subtract(valor_total);
				
				if(valor__ja_pago.compareTo(valor_total) > 0) 
					valor_total_juros_pago = valor_total_juros_pago.add(valor__ja_pago.subtract(valor_total));
				else 
				 valor_total_vencer_pagar = valor_total_vencer_pagar.add(lancamento.getValor_proximo_pagamento_a_vencer());
						
			
			}else if(lancamento.getStatus() == 1) {
				//despesas ja paga
				valor_total_despesas = valor_total_despesas.add(lancamento.getValor());
				valor_pago = valor_pago.add(lancamento.getValor());
		

				BigDecimal valor_total = lancamento.getValor();
				BigDecimal valor__ja_pago = lancamento.getValor_ja_pago();
				
				BigDecimal valor_restante = valor__ja_pago.subtract(valor_total);
				
				if(valor__ja_pago.compareTo(valor_total) > 0) 
					valor_total_juros_pago = valor_total_juros_pago.add(valor__ja_pago.subtract(valor_total));
				else 
				 valor_total_vencer_pagar = valor_total_vencer_pagar.add(lancamento.getValor_proximo_pagamento_a_vencer());
						

				
				numero_despesas_pago++;
			}else if(lancamento.getStatus() == 2) {
				//receitas a receber
				valor_total_receitas = valor_total_receitas.add(lancamento.getValor());
				valor_a_receber = valor_a_receber.add(lancamento.getValor().subtract(lancamento.getValor_ja_pago()));
				
				valor_recebido = valor_recebido.add(lancamento.getValor_ja_pago());

				BigDecimal valor_total_a_receber = lancamento.getValor();
				BigDecimal valor__ja_recebido = lancamento.getValor_ja_pago();
				
				BigDecimal valor_restante = valor__ja_recebido.subtract(valor_total_a_receber);
				
				if(valor__ja_recebido.compareTo(valor_total_a_receber) > 0) 
					valor_total_juros_recebido = valor_total_juros_recebido.add(valor__ja_recebido.subtract(valor_total_a_receber));
				 else 
				    valor_total_vencer_receber = valor_total_vencer_receber.add(lancamento.getValor_proximo_pagamento_a_vencer());
					
				
				numero_receitas_a_receber++;
			}else if(lancamento.getStatus() == 3) {
				//receitas recebidas
				valor_total_receitas = valor_total_receitas.add(lancamento.getValor());
				valor_recebido = valor_recebido.add(lancamento.getValor());
		
				
				BigDecimal valor_total_a_receber = lancamento.getValor();
				BigDecimal valor__ja_recebido = lancamento.getValor_ja_pago();
				
				BigDecimal valor_restante = valor__ja_recebido.subtract(valor_total_a_receber);
				
				if(valor__ja_recebido.compareTo(valor_total_a_receber) > 0) 
					valor_total_juros_recebido = valor_total_juros_recebido.add(valor__ja_recebido.subtract(valor_total_a_receber));
				 else 
				    valor_total_vencer_receber = valor_total_vencer_receber.add(lancamento.getValor_proximo_pagamento_a_vencer());
					
				
				
				
				
				numero_receitas_recebido++;
			}
			

			row = sheet.createRow(rownum++);
			cellnum = 0;

			String tipo = "";
			if (lancamento.getTipo_lancamento() == 0) {
				tipo = "DESPESAS";
			} else if (lancamento.getTipo_lancamento() == 1) {
				tipo = "RECEITAS";
			} else if (lancamento.getTipo_lancamento() == 3) {
				tipo = "EMPRESTIMOS";

			}
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(tipo);

			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(lancamento.getNome_cliente_fornecedor());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(NumberFormat.getCurrencyInstance(ptBr).format(lancamento.getValor()));

			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(NumberFormat.getCurrencyInstance(ptBr).format(lancamento.getValor_ja_pago()));

			BigDecimal valor_a_pagar_lancamento = lancamento.getValor().subtract(lancamento.getValor_ja_pago());
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(NumberFormat.getCurrencyInstance(ptBr).format(valor_a_pagar_lancamento));

			if (lancamento.getValor_proximo_pagamento_a_vencer().compareTo(valor_a_pagar_lancamento) > 0) {
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue(NumberFormat.getCurrencyInstance(ptBr).format(valor_a_pagar_lancamento));

				if (lancamento.getTipo_lancamento() == 0) {
					// despesa
					valor_total_vencer_pagar = valor_total_vencer_pagar.add(valor_a_pagar_lancamento);

				} else if (lancamento.getTipo_lancamento() == 1) {
					valor_total_vencer_receber = valor_total_vencer_receber.add(valor_a_pagar_lancamento);

				}

			} else {
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue(NumberFormat.getCurrencyInstance(ptBr)
						.format(lancamento.getValor_proximo_pagamento_a_vencer()));
				if (lancamento.getTipo_lancamento() == 0) {
					valor_total_vencer_pagar = valor_total_vencer_pagar
							.add(lancamento.getValor_proximo_pagamento_a_vencer());

				} else if (lancamento.getTipo_lancamento() == 1) {
					valor_total_vencer_receber = valor_total_vencer_receber
							.add(lancamento.getValor_proximo_pagamento_a_vencer());

				}

			}

			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(lancamento.getData_vencimento());

			String situacao = "";
			try {
				// data hoje
				LocalDate hoje = LocalDate.now();

				// data vencimento
				Date data_vencimento = null;
				try {
					SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
					data_vencimento = formato.parse(lancamento.getData_vencimento());

					try {
						LocalDate ld_data_vencimento = data_vencimento.toInstant().atZone(ZoneId.systemDefault())
								.toLocalDate();

						if (ld_data_vencimento.isAfter(hoje)) {
							situacao = "Em dias";
						} else {
							situacao = "Atrazado";

						}
					} catch (Exception e) {
						situacao = "Datas Invalidas";
					}

				} catch (NullPointerException e) {
					situacao = "Datas Invalidas";

				} catch (Exception e) {
					situacao = "Datas Invalidas";

				}

			} catch (Exception e) {
				situacao = "Datas Invalidas";

			}
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(situacao);

			String condicoes = "";
			try {
				String array_condicoes_pagamento = lancamento.getIds_forma_pagamento();
				String ids[] = array_condicoes_pagamento.split(",");
				int id_ultima_condicao_pagamento = Integer.parseInt(ids[ids.length - 1]);

				if (id_ultima_condicao_pagamento > 0) {

					CondicaoPagamento condicao = null;
					for (CondicaoPagamento cond : lista_condicoes) {
						if (cond.getId_condicao_pagamento() == id_ultima_condicao_pagamento) {
							condicao = cond;
							break;
						}
					}

					if (condicao != null)
						;
					condicoes += (condicao.getNome_condicao_pagamento() + "|");
				}

			} catch (Exception e) {
				condicoes = "";
			}
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(condicoes);

			String retorno = "";
			try {
				String array_status = lancamento.getStatus_forma_pagamento();
				String status[] = array_status.split(",");
				int id_status = Integer.parseInt(status[status.length - 1]);

				if (id_status == 0) {
					// cbStatusCondicaoPagamento.addItem("A - Compensar|Realizar|Concluir");
					// cbStatusCondicaoPagamento.addItem("Compensado|Realizado|Concluído");
					retorno += ("A - Compensar|Realizar|Concluir;");
				} else if (id_status == 1) {
					retorno += ("Compensado|Realizado|Concluído;");

				}

			} catch (Exception e) {
				retorno = "";
			}
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(retorno);

			String status_contador = "";
			int status = lancamento.getContador();

			if (status == 0) {
				status_contador = "Não se aplica".toUpperCase();
			} else if (status == 1) {
				status_contador = "Não Enviado ao contador".toUpperCase();

			} else if (status == 2) {
				status_contador = "Enviado ao contador".toUpperCase();
			}

			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(status_contador);

		}
		sheet.setAutoFilter(CellRangeAddress.valueOf("A1:AF1"));

		row = sheet.createRow(rownum += 2);
		cellnum = 0;
		/*
		 * lblDespesasAPagar.setText(numero_despesas_a_pagar + "");
		 * lblDespesasPago.setText(numero_despesas_pago + "");
		 * lblTotalDespesas.setText((numero_despesas_a_pagar + numero_despesas_pago ) +
		 * ""); lblReceitasAReceber.setText(numero_receitas_a_receber + "");
		 * lblReceitasRecebido.setText(numero_receitas_recebido + "");
		 * lblTotalReceitas.setText((numero_receitas_a_receber +
		 * numero_receitas_recebido) + "");
		 * 
		 * 
		 * //valores Locale ptBr = new Locale("pt", "BR");
		 * lblValorRestanteAPagar.setText(NumberFormat.getCurrencyInstance(ptBr).format(
		 * valor_a_pagar));
		 * lblValorPago.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_pago
		 * ));
		 * lblValorTotalDespesas.setText(NumberFormat.getCurrencyInstance(ptBr).format(
		 * valor_total_despesas));
		 * 
		 * 
		 * //receitas
		 * lblValorAReceber.setText(NumberFormat.getCurrencyInstance(ptBr).format(
		 * valor_a_receber));
		 * lblValorRecebido.setText(NumberFormat.getCurrencyInstance(ptBr).format(
		 * valor_recebido));
		 * lblValorReceitas.setText(NumberFormat.getCurrencyInstance(ptBr).format(
		 * valor_total_receitas));
		 * 
		 */
		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("DESPESAS");
		// sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, cellnum, 5));

		cell = row.createCell(4);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("RECEITAS");

		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		// despesa
		cell = row.createCell(cellnum);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("Valor Total:");

		cell = row.createCell(cellnum += 1);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_despesas));

		// receitas
		cell = row.createCell(4);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("Valor Total:");

		cell = row.createCell(5);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_receitas));

		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		// despesa
		cell = row.createCell(cellnum);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("Valor Pago:");

		cell = row.createCell(cellnum += 1);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue(NumberFormat.getCurrencyInstance(ptBr).format(valor_pago));

		// receitas
		cell = row.createCell(4);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("Valor Recebido:");

		cell = row.createCell(5);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue(NumberFormat.getCurrencyInstance(ptBr).format(valor_recebido));

		row = sheet.createRow(rownum += 1);

		cellnum = 0;

		// despesa
		cell = row.createCell(cellnum);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("Valor a Pagar:");

		cell = row.createCell(cellnum += 1);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue(NumberFormat.getCurrencyInstance(ptBr).format(valor_a_pagar));

		// receitas
		cell = row.createCell(4);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("Valor a Receber:");

		cell = row.createCell(5);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue(NumberFormat.getCurrencyInstance(ptBr).format(valor_a_receber));

		// vencimentos

		row = sheet.createRow(rownum += 1);

		cellnum = 0;

		// despesa
		cell = row.createCell(cellnum);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("Valor a Vencer:");

		cell = row.createCell(cellnum += 1);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_vencer_pagar));

		// receitas
		cell = row.createCell(4);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("Valor a Vencer:");

		cell = row.createCell(5);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_vencer_receber));

		// juros

		row = sheet.createRow(rownum += 1);

		cellnum = 0;

		// despesa
		cell = row.createCell(cellnum);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("Valor Total Juros:");

		cell = row.createCell(cellnum += 1);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_juros_pago));

		// receitas
		cell = row.createCell(4);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("Valor Total Juros:");

		cell = row.createCell(5);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_juros_recebido));

		return workbook;
	}

	public void fechar() {
		isto.dispose();
	}
}
