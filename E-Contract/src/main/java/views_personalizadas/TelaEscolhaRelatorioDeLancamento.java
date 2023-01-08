package main.java.views_personalizadas;

import java.awt.Color;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.LineBorder;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.FinanceiroPagamentoCompleto;
import main.java.cadastros.FinanceiroPagamentoEmprestimoCompleto;
import main.java.cadastros.Lancamento;
import main.java.cadastros.Parcela;
import main.java.cadastros.ParcelaEmprestimo;
import main.java.conexaoBanco.GerenciarBancoFinanceiroPagamento;
import main.java.conexaoBanco.GerenciarBancoFinanceiroPagamentoEmprestimo;
import main.java.conexaoBanco.GerenciarBancoLancamento;
import main.java.conexaoBanco.GerenciarBancoParcelas;
import main.java.conexaoBanco.GerenciarBancoParcelasEmprestimo;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.manipular.ManipularTxt;
import main.java.outros.DadosGlobais;
import main.java.outros.GetData;
import main.java.tratamento_proprio.Log;
import net.miginfocom.swing.MigLayout;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFCreationHelper;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

public class TelaEscolhaRelatorioDeLancamento
extends JDialog {
    private TelaEscolhaRelatorioDeLancamento isto;
    private FileChooser fileChooser;
    private JRadioButton rdbtnPdf;
    private JRadioButton rdbtnExcel;
    private Log GerenciadorLog;
    private CadastroLogin login;
    private ConfiguracoesGlobais configs_globais;

    public TelaEscolhaRelatorioDeLancamento(Lancamento lancamento, java.awt.Window janela_pai) {
        this.getContentPane().setBackground(Color.WHITE);
        this.getDadosGlobais();
        this.setBounds(100, 100, 331, 259);
        this.isto = this;
        this.getContentPane().setLayout((LayoutManager)new MigLayout("", "[grow][]", "[grow][][grow][][grow][][grow][][][]"));
        JPanel panel_2 = new JPanel();
        panel_2.setBackground(new Color(0, 51, 0));
        this.getContentPane().add((Component)panel_2, "cell 0 0 2 1,grow");
        JLabel btnRelatrioDelancamentos = new JLabel("Relat\u00f3rio de Lan\u00e7amento");
        panel_2.add(btnRelatrioDelancamentos);
        btnRelatrioDelancamentos.setOpaque(true);
        btnRelatrioDelancamentos.setForeground(Color.WHITE);
        btnRelatrioDelancamentos.setFont(new java.awt.Font("Tahoma", 1, 16));
        btnRelatrioDelancamentos.setBorder(null);
        btnRelatrioDelancamentos.setBackground(new Color(0, 51, 0));
        JLabel lblSada = new JLabel("Sa\u00edda:");
        lblSada.setForeground(Color.BLACK);
        lblSada.setFont(new java.awt.Font("Tahoma", 0, 18));
        this.getContentPane().add((Component)lblSada, "cell 0 5,alignx left");
        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel_1.setBackground(Color.WHITE);
        this.getContentPane().add((Component)panel_1, "cell 0 6 2 1,alignx center,growy");
        panel_1.setLayout((LayoutManager)new MigLayout("", "[53px][43px]", "[23px]"));
        this.rdbtnExcel = new JRadioButton("Excel");
        this.rdbtnExcel.setSelected(true);
        this.rdbtnExcel.setBackground(Color.WHITE);
        this.rdbtnExcel.setForeground(Color.BLACK);

        this.rdbtnExcel.setFont(new java.awt.Font("Tahoma", 1, 14));
        panel_1.add((Component)this.rdbtnExcel, "cell 0 0,alignx left,aligny top");
        this.rdbtnPdf = new JRadioButton("Pdf");
        this.rdbtnPdf.setBackground(Color.WHITE);
        this.rdbtnPdf.setForeground(Color.BLACK);

        this.rdbtnPdf.setFont(new java.awt.Font("Tahoma", 1, 14));
        panel_1.add((Component)this.rdbtnPdf, "cell 1 0,alignx left,aligny top");
        JButton btnNewButton_1 = new JButton("Gerar");

        btnNewButton_1.setFont(new java.awt.Font("Arial", 0, 16));
        btnNewButton_1.setForeground(Color.WHITE);
        btnNewButton_1.setBackground(new Color(0, 0, 51));
        this.getContentPane().add((Component)btnNewButton_1, "cell 1 8,alignx right");
        URL url2 = this.getClass().getResource("/imagens/infinite.gif");
        ImageIcon img2 = new ImageIcon(url2);
        this.setLocationRelativeTo(janela_pai);
        this.setDefaultCloseOperation(2);
        this.setResizable(false);
    }

    public void gerarExcel(HSSFWorkbook workbook) {
        try {
            new JFXPanel();
            Platform.runLater(() -> {
                ManipularTxt manipular_ultima_pasta = new ManipularTxt();
                String ultima_pasta = manipular_ultima_pasta.lerArquivo(new File("C:\\ProgramData\\E-Contract\\configs\\ultima_pasta.txt"));
                if (this.fileChooser == null) {
                    this.fileChooser = new FileChooser();
                }
                fileChooser.setInitialDirectory(new File(ultima_pasta));
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter[]{new FileChooser.ExtensionFilter("Excel", new String[]{"*.xls"})});
                File file = this.fileChooser.showSaveDialog((Window)new Stage());
                String caminho_arquivo = "";
                if (file != null) {
                    caminho_arquivo = file.getAbsolutePath();
                    manipular_ultima_pasta.rescreverArquivo(new File("C:\\ProgramData\\E-Contract\\configs\\ultima_pasta.txt"), file.getParent());
                    try {
                        FileOutputStream out = new FileOutputStream(file);
                        workbook.write((OutputStream)out);
                        workbook.close();
                        out.close();
                        Runtime.getRuntime().exec("explorer " + file.getAbsolutePath());
                        System.out.println("Success!!");
                    }
                    catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }
                    catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            });
        }
        catch (Exception k) {
            k.printStackTrace();
        }
    }

    public HSSFWorkbook prepararCompletoExcel(Lancamento lancamento, int flag) throws ParseException {
        int i;
        int i2;
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Exporta\u00e7\u00e3o de Dados de Lan\u00e7amento");
        sheet.setDefaultColumnWidth(25);
        sheet.setDefaultRowHeight((short)400);
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
        HSSFCellStyle styleDescricao = workbook.createCellStyle();
        styleDescricao.setDataFormat(numberFormat.getFormat("#,##0.00"));
        styleDescricao.setAlignment(HorizontalAlignment.LEFT);
        styleDescricao.setVerticalAlignment(VerticalAlignment.CENTER);
        styleDescricao.setWrapText(true);
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
        HSSFCellStyle numberStyleFundoVerdeTextoBranco = workbook.createCellStyle();
        numberStyleFundoVerdeTextoBranco.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        numberStyleFundoVerdeTextoBranco.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        numberStyleFundoVerdeTextoBranco.setAlignment(HorizontalAlignment.CENTER);
        numberStyleFundoVerdeTextoBranco.setVerticalAlignment(VerticalAlignment.CENTER);
        numberStyleFundoVerdeTextoBranco.setDataFormat(numberFormat.getFormat("R$ #,##0.00"));
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
        HSSFCellStyle celula_fundo_verde_texto_branco = workbook.createCellStyle();
        celula_fundo_verde_texto_branco.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        celula_fundo_verde_texto_branco.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        celula_fundo_verde_texto_branco.setAlignment(HorizontalAlignment.CENTER);
        celula_fundo_verde_texto_branco.setVerticalAlignment(VerticalAlignment.CENTER);
        celula_fundo_verde_texto_branco.setFont((Font)newFont_branca);
        HSSFCellStyle celula_fundo_azul_texto_branco = workbook.createCellStyle();
        celula_fundo_azul_texto_branco.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        celula_fundo_azul_texto_branco.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        celula_fundo_azul_texto_branco.setAlignment(HorizontalAlignment.CENTER);
        celula_fundo_azul_texto_branco.setVerticalAlignment(VerticalAlignment.CENTER);
        celula_fundo_azul_texto_branco.setFont((Font)newFont_branca);
        HSSFCellStyle celula_fundo_preto_texto_branco = workbook.createCellStyle();
        celula_fundo_preto_texto_branco.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        celula_fundo_preto_texto_branco.setFillForegroundColor(IndexedColors.BLACK.getIndex());
        celula_fundo_preto_texto_branco.setAlignment(HorizontalAlignment.CENTER);
        celula_fundo_preto_texto_branco.setVerticalAlignment(VerticalAlignment.CENTER);
        celula_fundo_preto_texto_branco.setFont((Font)newFont_branca);
        HSSFCellStyle celula_titulo = workbook.createCellStyle();
        celula_titulo.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        celula_titulo.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        celula_titulo.setAlignment(HorizontalAlignment.CENTER);
        celula_titulo.setVerticalAlignment(VerticalAlignment.CENTER);
        celula_titulo.setFont((Font)newFont_titulo);
        numberStyleFundoVerdeTextoBranco.setFont((Font)newFont_branca);
        HSSFFont newFont_normal = workbook.createFont();
        newFont_normal.setBold(false);
        newFont_normal.setColor(IndexedColors.BLACK.getIndex());
        newFont_normal.setFontName("Calibri");
        newFont_normal.setItalic(false);
        newFont_normal.setFontHeight((short)198);
        HSSFCellStyle celula_normal = workbook.createCellStyle();
        celula_normal.setAlignment(HorizontalAlignment.LEFT);
        celula_normal.setVerticalAlignment(VerticalAlignment.CENTER);
        celula_normal.setFont((Font)newFont_normal);
        HSSFFont newFont_preta = workbook.createFont();
        newFont_preta.setColor(IndexedColors.BLACK.getIndex());
        newFont_preta.setFontName("Calibri");
        newFont_preta.setItalic(false);
        newFont_preta.setFontHeight((short)220);
        HSSFCellStyle celula_fundo_branco_texto_preto = workbook.createCellStyle();
        celula_fundo_branco_texto_preto.setAlignment(HorizontalAlignment.CENTER);
        celula_fundo_branco_texto_preto.setVerticalAlignment(VerticalAlignment.CENTER);
        celula_fundo_branco_texto_preto.setFont((Font)newFont_preta);
        HSSFCellStyle pesoStyle = workbook.createCellStyle();
        pesoStyle.setDataFormat(numberFormat.getFormat("#,##0.00"));
        pesoStyle.setAlignment(HorizontalAlignment.CENTER);
        pesoStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        numberStyleFundoVerdeTextoBranco.setFont((Font)newFont_branca);
        HSSFCellStyle valorStyleFundoVerdeTextoBranco = workbook.createCellStyle();
        valorStyleFundoVerdeTextoBranco.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        valorStyleFundoVerdeTextoBranco.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        valorStyleFundoVerdeTextoBranco.setAlignment(HorizontalAlignment.CENTER);
        valorStyleFundoVerdeTextoBranco.setVerticalAlignment(VerticalAlignment.CENTER);
        valorStyleFundoVerdeTextoBranco.setDataFormat(numberFormat.getFormat("R$ #,##0.00"));
        valorStyleFundoVerdeTextoBranco.setFont((Font)newFont_branca);
        HSSFCellStyle valorStyleFundoBrancoTextopreto = workbook.createCellStyle();
        valorStyleFundoBrancoTextopreto.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        valorStyleFundoBrancoTextopreto.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        valorStyleFundoBrancoTextopreto.setAlignment(HorizontalAlignment.CENTER);
        valorStyleFundoBrancoTextopreto.setVerticalAlignment(VerticalAlignment.CENTER);
        valorStyleFundoBrancoTextopreto.setDataFormat(numberFormat.getFormat("R$ #,##0.00"));
        valorStyleFundoBrancoTextopreto.setFont((Font)newFont_preta);
        HSSFRow row = sheet.createRow(rownum++);
        cellnum = 0;
        Cell cell = row.createCell(cellnum++);
        cell.setCellStyle((CellStyle)celula_titulo);
        cell.setCellValue("Relat\u00f3rio de Lan\u00e7amentos");
        for (int i3 = 0; i3 < 4; ++i3) {
            cell = row.createCell(cellnum++);
            cell.setCellStyle((CellStyle)celula_titulo);
            cell.setCellValue("");
        }
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
        cellnum = 0;
        try {
            URL url = this.getClass().getResource("/imagens/logo_para_relatorio.png");
            String imgFile = url.getFile();
            FileInputStream inputStream = new FileInputStream(imgFile);
            byte[] imageBytes = IOUtils.toByteArray((InputStream)inputStream);
            int pictureureIdx = workbook.addPicture(imageBytes, 6);
            ((InputStream)inputStream).close();
            HSSFCreationHelper helper = workbook.getCreationHelper();
            HSSFPatriarch drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = helper.createClientAnchor();
            anchor.setRow1(0);
            anchor.setRow2(3);
            anchor.setCol1(6);
            anchor.setCol2(8);
            drawing.createPicture(anchor, pictureureIdx);
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
        GetData data = new GetData();
        String data_criacao = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String info_relator = "Relat\u00f3rio de Lan\u00e7amento por " + this.login.getNome() + " " + this.login.getSobrenome() + " em " + data_criacao + " \u00e1s " + data.getHora();
        row = sheet.createRow(++rownum);
        cell = row.createCell(cellnum++);
        cell.setCellStyle((CellStyle)celula_normal);
        cell.setCellValue(info_relator);
        for (int i4 = 0; i4 < 6; ++i4) {
            cell = row.createCell(cellnum++);
            cell.setCellStyle((CellStyle)celula_normal);
            cell.setCellValue("");
        }
        sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 0, 5));
        cellnum = 0;
        ++rownum;
        ++rownum;
        String status = "";
        int int_status = lancamento.getStatus();
        if (int_status == 0) {
            status = "A Pagar";
        } else if (int_status == 1) {
            status = "Pago";
        } else if (int_status == 2) {
            status = "A Receber";
        } else if (int_status == 3) {
            status = "Recebido";
        }
        String tipo_lancamento = "";
        if (lancamento.getTipo_lancamento() == 0) {
            tipo_lancamento = "DESPESA";
        } else if (lancamento.getTipo_lancamento() == 1) {
            tipo_lancamento = "RECEITA";
        } else if (lancamento.getTipo_lancamento() == 2) {
            tipo_lancamento = "TRANSFER\u00caNCIA";
        } else if (lancamento.getTipo_lancamento() == 3) {
            tipo_lancamento = "EMPR\u00c9STIMO MUTUADO";
        } else if (lancamento.getTipo_lancamento() == 4) {
            tipo_lancamento = "EMPR\u00c9STIMO TOMADO";
        }
        int d_prioridade = lancamento.getPrioridade();
        String prioridade = "";
        if (d_prioridade == 0) {
            prioridade = "Alta Prioridade - Ainda esta semana";
        } else if (d_prioridade == 1) {
            prioridade = "M\u00e9dia Prioridade - Em menos de 15 dias";
        } else if (d_prioridade == 2) {
            prioridade = "Prioridade Leve - Ainda este m\u00eas";
        } else if (d_prioridade == 3) {
            prioridade = "Baixa Prioridade - Ainda este ano";
        }
        String texto = " Lan\u00e7amento do tipo: " + tipo_lancamento + " | ";
        texto = String.valueOf(texto) + " Status: " + status + " | ";
        texto = String.valueOf(texto) + " Data: " + lancamento.getData_lancamento() + " | ";
        texto = String.valueOf(texto) + " Prioridade: " + prioridade;
        lancamento = new GerenciarBancoLancamento().getLancamentoParaRelatorio(lancamento.getId_lancamento());
        row = sheet.createRow(rownum);
        cell = row.createCell(cellnum++);
        cell.setCellStyle((CellStyle)celula_normal);
        cell.setCellValue(texto);
        for (int i5 = 0; i5 < 6; ++i5) {
            cell = row.createCell(cellnum++);
            cell.setCellStyle((CellStyle)celula_normal);
            cell.setCellValue("");
        }
        sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 0, 5));
        cellnum = 0;
        ++rownum;
        row = sheet.createRow(++rownum);
        cell = row.createCell(cellnum++);
        cell.setCellStyle((CellStyle)celula_normal);
        cell.setCellValue("As Partes:");
        String centro_custo = lancamento.getNome_centro_custo();
        String nome_cliente = lancamento.getNome_cliente_fornecedor();
        String conta = lancamento.getNome_conta();
        String grupo_conta = lancamento.getNome_grupo_contas();
        String devedor = "";
        String recebedor = "";
        if (lancamento.getTipo_lancamento() == 0) {
            devedor = " Devedor: " + centro_custo;
            recebedor = " Recebedor: " + nome_cliente;
        } else if (lancamento.getTipo_lancamento() == 1) {
            recebedor = " Recebedor: " + centro_custo;
            devedor = " Devedor: " + nome_cliente;
        } else if (lancamento.getTipo_lancamento() == 2) {
            devedor = " Remetente: " + centro_custo;
            recebedor = " Destinat\u00e1rio: " + nome_cliente;
        } else if (lancamento.getTipo_lancamento() == 3) {
            devedor = " Tomador: " + nome_cliente;
            recebedor = " Mutuante: " + centro_custo;
        } else if (lancamento.getTipo_lancamento() == 4) {
            devedor = " Tomador: " + centro_custo;
            recebedor = " Mutuante: " + nome_cliente;
        }
        cellnum = 0;
        row = sheet.createRow(++rownum);
        cell = row.createCell(cellnum++);
        cell.setCellStyle((CellStyle)celula_normal);
        cell.setCellValue(devedor);
        for (i2 = 0; i2 < 4; ++i2) {
            cell = row.createCell(cellnum++);
            cell.setCellStyle((CellStyle)celula_normal);
            cell.setCellValue("");
        }
        sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 0, 3));
        cellnum = 0;
        row = sheet.createRow(++rownum);
        cell = row.createCell(cellnum++);
        cell.setCellStyle((CellStyle)celula_normal);
        cell.setCellValue(recebedor);
        for (i2 = 0; i2 < 4; ++i2) {
            cell = row.createCell(cellnum++);
            cell.setCellStyle((CellStyle)celula_normal);
            cell.setCellValue("");
        }
        sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 0, 3));
        String status_contador = "";
        int contador = lancamento.getContador();
        if (contador == 0) {
            status_contador = "N\u00e3o se aplica";
        } else if (contador == 1) {
            status_contador = "N\u00e3o Enviado ao contador";
        } else if (contador == 2) {
            status_contador = "Enviado ao contador";
        }
        String nome_destinatario_nf = lancamento.getNome_destinatario_nf();
        texto = "";
        texto = String.valueOf(texto) + " Grupo de Contas: " + grupo_conta + " | ";
        texto = String.valueOf(texto) + " Conta: " + conta + " | ";
        texto = String.valueOf(texto) + " Identificador: " + lancamento.getIdentificacao() + " | ";
        texto = String.valueOf(texto) + " Destinat\u00e1rio da NF: " + nome_destinatario_nf;
        cellnum = 0;
        ++rownum;
        row = sheet.createRow(++rownum);
        cell = row.createCell(cellnum++);
        cell.setCellStyle((CellStyle)celula_normal);
        cell.setCellValue("A Conta:");
        cellnum = 0;
        row = sheet.createRow(++rownum);
        cell = row.createCell(cellnum++);
        cell.setCellStyle((CellStyle)celula_normal);
        cell.setCellValue(texto);
        for (i = 0; i < 9; ++i) {
            cell = row.createCell(cellnum++);
            cell.setCellStyle((CellStyle)celula_normal);
            cell.setCellValue("");
        }
        sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 0, 8));
        texto = "";
        texto = String.valueOf(texto) + " Data Primeiro Vencimento: " + lancamento.getData_vencimento() + " | ";
        texto = String.valueOf(texto) + " Valor: " + NumberFormat.getCurrencyInstance(ptBr).format(lancamento.getValor()) + " | ";
        texto = String.valueOf(texto) + " N\u00famero de Parcelas: " + lancamento.getNumero_parcelas() + " | ";
        texto = String.valueOf(texto) + " Intervalo: " + lancamento.getIntervalo() + " | ";
        texto = String.valueOf(texto) + " Descri\u00e7\u00e3o: " + lancamento.getDescricao() + " | ";
        texto = String.valueOf(texto) + " Observa\u00e7\u00e3o: " + lancamento.getObservacao() + " | ";
        texto = String.valueOf(texto) + " Status Contador: " + status_contador;
        cellnum = 0;
        row = sheet.createRow(++rownum);
        cell = row.createCell(cellnum++);
        cell.setCellStyle((CellStyle)celula_normal);
        cell.setCellValue(texto);
        for (i = 0; i < 9; ++i) {
            cell = row.createCell(cellnum++);
            cell.setCellStyle((CellStyle)celula_normal);
            cell.setCellValue("");
        }
        sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 0, 8));
        cellnum = 0;
        ++rownum;
        row = sheet.createRow(++rownum);
        cellnum = 0;
        cell = row.createCell(cellnum++);
        cell.setCellStyle((CellStyle)celula_fundo_verde_texto_branco);
        cell.setCellValue("PARCELAS");
        for (i = 0; i < 5; ++i) {
            cell = row.createCell(cellnum++);
            cell.setCellStyle((CellStyle)celula_fundo_verde_texto_branco);
            cell.setCellValue("");
        }
        sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 0, 4));
        cell = row.createCell(5);
        cell.setCellStyle((CellStyle)celula_fundo_preto_texto_branco);
        cell.setCellValue("--------");
        cellnum = 6;
        cell = row.createCell(6);
        cell.setCellStyle((CellStyle)celula_fundo_verde_texto_branco);
        cell.setCellValue("PAGAMENTOS");
        for (i = 7; i <= 13; ++i) {
            cell = row.createCell(i);
            cell.setCellStyle((CellStyle)celula_fundo_verde_texto_branco);
            cell.setCellValue("");
        }
        sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 6, 13));
        cellnum = 0;
        row = sheet.createRow(++rownum);
        cell = row.createCell(cellnum++);
        cell.setCellStyle((CellStyle)celula_fundo_azul_texto_branco);
        cell.setCellValue("IDENTIFICADOR");
        cell = row.createCell(cellnum++);
        cell.setCellStyle((CellStyle)celula_fundo_azul_texto_branco);
        cell.setCellValue("DESCRI\u00c7\u00c3O");
        cell = row.createCell(cellnum++);
        cell.setCellStyle((CellStyle)celula_fundo_azul_texto_branco);
        cell.setCellValue("DATA VENC.");
        cell = row.createCell(cellnum++);
        cell.setCellStyle((CellStyle)celula_fundo_azul_texto_branco);
        cell.setCellValue("VALOR");
        cell = row.createCell(cellnum++);
        cell.setCellStyle((CellStyle)celula_fundo_azul_texto_branco);
        cell.setCellValue("STATUS");
        cell = row.createCell(cellnum++);
        cell.setCellStyle((CellStyle)celula_fundo_preto_texto_branco);
        cell.setCellValue("--------");
        cell = row.createCell(cellnum++);
        cell.setCellStyle((CellStyle)celula_fundo_azul_texto_branco);
        cell.setCellValue("IDENTIFICADOR");
        cell = row.createCell(cellnum++);
        cell.setCellStyle((CellStyle)celula_fundo_azul_texto_branco);
        cell.setCellValue("PAGADOR");
        cell = row.createCell(cellnum++);
        cell.setCellStyle((CellStyle)celula_fundo_azul_texto_branco);
        cell.setCellValue("RECEBEDOR");
        cell = row.createCell(cellnum++);
        cell.setCellStyle((CellStyle)celula_fundo_azul_texto_branco);
        cell.setCellValue("DESCRI\u00c7\u00c3O");
        cell = row.createCell(cellnum++);
        cell.setCellStyle((CellStyle)celula_fundo_azul_texto_branco);
        cell.setCellValue("VALOR");
        cell = row.createCell(cellnum++);
        cell.setCellStyle((CellStyle)celula_fundo_azul_texto_branco);
        cell.setCellValue("DATA PAG.");
        cell = row.createCell(cellnum++);
        cell.setCellStyle((CellStyle)celula_fundo_azul_texto_branco);
        cell.setCellValue("FORMA PAG.");
        cell = row.createCell(cellnum++);
        cell.setCellStyle((CellStyle)celula_fundo_azul_texto_branco);
        cell.setCellValue("STATUS");
        int primeira_linha = 0;
        int ultima_linha = 0;
        if (lancamento.getTipo_lancamento() != 3 && lancamento.getTipo_lancamento() != 4) {
        	ArrayList<Parcela> parcelas = new GerenciarBancoParcelas().getParcelasPorLancamento(lancamento.getId_lancamento());
            ArrayList pagamentos = new GerenciarBancoFinanceiroPagamento().getFinanceiroPagamentosPorLancamentoParaRelatorio(lancamento.getId_lancamento());
            int num_linhas_tabela = 0;
            num_linhas_tabela = parcelas.size() > pagamentos.size() ? parcelas.size() : pagamentos.size();
            ultima_linha = primeira_linha = rownum;
            for (int contador_linhas = 0; contador_linhas < num_linhas_tabela; ++contador_linhas) {
                cellnum = 0;
                row = sheet.createRow(++rownum);
                try {
                    Parcela parcela = (Parcela)parcelas.get(contador_linhas);
                    if (parcela != null) {
                        cell = row.createCell(cellnum++);
                        cell.setCellStyle((CellStyle)celula_normal);
                        cell.setCellValue(parcela.getIdentificador());
                        cell = row.createCell(cellnum++);
                        cell.setCellStyle((CellStyle)celula_normal);
                        cell.setCellValue(parcela.getDescricao());
                        cell = row.createCell(cellnum++);
                        cell.setCellStyle((CellStyle)celula_normal);
                        cell.setCellValue(parcela.getData_vencimento());
                        cell = row.createCell(cellnum++);
                        cell.setCellStyle((CellStyle)valorStyleFundoBrancoTextopreto);
                        cell.setCellValue(parcela.getValor().doubleValue());
                        String status_lancamento = "";
                        int istatus = parcela.getStatus();
                        if (istatus == 0) {
                            status_lancamento = "A Pagar";
                        } else if (istatus == 1) {
                            status_lancamento = "Pago";
                        } else if (istatus == 2) {
                            status_lancamento = "A Receber";
                        } else if (istatus == 3) {
                            status_lancamento = "Recebido";
                        }
                        cell = row.createCell(cellnum++);
                        cell.setCellStyle((CellStyle)celula_normal);
                        cell.setCellValue(status_lancamento);
                    }
                }
                catch (Exception parcela) {
                    // empty catch block
                }
                try {
                    FinanceiroPagamentoCompleto pagamento = (FinanceiroPagamentoCompleto)pagamentos.get(contador_linhas);
                    if (pagamento != null) {
                        cellnum = 6;
                        cell = row.createCell(cellnum++);
                        cell.setCellStyle((CellStyle)celula_normal);
                        cell.setCellValue(pagamento.getFpag().getIdentificador());
                        cell = row.createCell(cellnum++);
                        cell.setCellStyle((CellStyle)celula_normal);
                        cell.setCellValue(pagamento.getNome_pagador());
                        cell = row.createCell(cellnum++);
                        cell.setCellStyle((CellStyle)celula_normal);
                        cell.setCellValue(pagamento.getNome_recebedor());
                        cell = row.createCell(cellnum++);
                        cell.setCellStyle((CellStyle)celula_normal);
                        cell.setCellValue(pagamento.getFpag().getDescricao());
                        cell = row.createCell(cellnum++);
                        cell.setCellStyle((CellStyle)valorStyleFundoBrancoTextopreto);
                        cell.setCellValue(pagamento.getFpag().getValor().doubleValue());
                        cell = row.createCell(cellnum++);
                        cell.setCellStyle((CellStyle)celula_normal);
                        cell.setCellValue(pagamento.getFpag().getData_pagamento());
                        cell = row.createCell(cellnum++);
                        cell.setCellStyle((CellStyle)celula_normal);
                        cell.setCellValue(pagamento.getNome_forma_pagamento());
                        status = "";
                        if (pagamento.getFpag().getStatus_pagamento() == 0) {
                            status = "A - Compensar|Realizar|Concluir";
                        } else if (pagamento.getFpag().getStatus_pagamento() == 1) {
                            status = "Compensado|Realizado|Conclu\u00eddo";
                        }
                        cell = row.createCell(cellnum++);
                        cell.setCellStyle((CellStyle)celula_normal);
                        cell.setCellValue(status);
                    }
                }
                catch (Exception pagamento) {
                    // empty catch block
                }
                cell = row.createCell(5);
                cell.setCellStyle((CellStyle)celula_fundo_preto_texto_branco);
                cell.setCellValue("-----------");
            }
            ultima_linha = rownum;
        } else {
            ArrayList<ParcelaEmprestimo> parcelas = new GerenciarBancoParcelasEmprestimo().getParcelasPorLancamento(lancamento.getId_lancamento());
            GerenciarBancoFinanceiroPagamento gerenciar_pags = new GerenciarBancoFinanceiroPagamento();
            int num_linhas_descricao_pagamento_parcela = 0;
            for (ParcelaEmprestimo parcela : parcelas) {
                if (parcela.getCriar_pagamento() != 1 || parcela.getId_pagamento() <= 0) continue;
                ++num_linhas_descricao_pagamento_parcela;
            }
            ArrayList pagamentos = new GerenciarBancoFinanceiroPagamentoEmprestimo().getFinanceiroPagamentosPorLancamentoParaRelatorio(lancamento.getId_lancamento());
            int num_linhas_tabela = 0;
            num_linhas_tabela = parcelas.size() > pagamentos.size() ? parcelas.size() : pagamentos.size();
            ultima_linha = primeira_linha = rownum;
            for (int contador_linhas = 0; contador_linhas < (num_linhas_tabela += num_linhas_descricao_pagamento_parcela); ++contador_linhas) {
                //FinanceiroPagamentoEmprestimoCompleto pagamento = null;
                cellnum = 0;
                row = sheet.createRow(++rownum);
                try {
                    Parcela parcela = (Parcela)parcelas.get(contador_linhas);
                    if (parcela != null) {
                        cell = row.createCell(cellnum++);
                        cell.setCellStyle((CellStyle)celula_normal);
                        cell.setCellValue(parcela.getIdentificador());
                        cell = row.createCell(cellnum++);
                        cell.setCellStyle((CellStyle)celula_normal);
                        cell.setCellValue(parcela.getDescricao());
                        cell = row.createCell(cellnum++);
                        cell.setCellStyle((CellStyle)celula_normal);
                        cell.setCellValue(parcela.getData_vencimento());
                        cell = row.createCell(cellnum++);
                        cell.setCellStyle((CellStyle)valorStyleFundoBrancoTextopreto);
                        cell.setCellValue(parcela.getValor().doubleValue());
                        String status_lancamento = "";
                        int istatus = parcela.getStatus();
                        if (istatus == 0) {
                            status_lancamento = "A Pagar";
                        } else if (istatus == 1) {
                            status_lancamento = "Pago";
                        } else if (istatus == 2) {
                            status_lancamento = "A Receber";
                        } else if (istatus == 3) {
                            status_lancamento = "Recebido";
                        }
                        cell = row.createCell(cellnum++);
                        cell.setCellStyle((CellStyle)celula_normal);
                        cell.setCellValue(status_lancamento);
                    }
                }
                catch (Exception parcela) {
                    // empty catch block
                }
                FinanceiroPagamentoCompleto pagamento;
				try {
                    if ((parcelas.get(contador_linhas)).getCriar_pagamento() == 1 && (parcelas.get(contador_linhas)).getId_pagamento() > 0) {
                       
                    	pagamento = gerenciar_pags.getFinanceiroPagamentosCompletoPorId((parcelas.get(contador_linhas)).getId_pagamento());
                    	cellnum = 0;
                        row = sheet.createRow(++rownum);
                        String valor_pag = NumberFormat.getCurrencyInstance(ptBr).format(((ParcelaEmprestimo)parcelas.get(contador_linhas)).getValor());
                        String descricao_pagamento = "Pagamento desta parcela no valor de " + valor_pag + " pago na data de " + pagamento.getFpag().getData_pagamento() + " por " + pagamento.getNome_pagador() + " para " + pagamento.getNome_recebedor() + " na forma de pagamento de " + pagamento.getNome_forma_pagamento() + " proveniente de " + pagamento.getConta_pagador();
                        cell = row.createCell(cellnum++);
                        cell.setCellStyle((CellStyle)styleDescricao);
                        cell.setCellValue(descricao_pagamento);
                        for (int i6 = 0; i6 < 5; ++i6) {
                            cell = row.createCell(cellnum++);
                            cell.setCellStyle((CellStyle)celula_fundo_verde_texto_branco);
                            cell.setCellValue("");
                        }
                        sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 0, 4));
                    }
                }
                catch (Exception pagamento2) {
                    // empty catch block
                }
                cell = row.createCell(5);
                cell.setCellStyle((CellStyle)celula_fundo_preto_texto_branco);
                cell.setCellValue("-----------");
                try {
                	 pagamento = (FinanceiroPagamentoCompleto) pagamentos.get(contador_linhas);
                    if (pagamento == null) continue;
                    cellnum = 6;
                    cell = row.createCell(cellnum++);
                    cell.setCellStyle((CellStyle)celula_normal);
                    cell.setCellValue(pagamento.getFpag().getIdentificador());
                    cell = row.createCell(cellnum++);
                    cell.setCellStyle((CellStyle)celula_normal);
                    cell.setCellValue(pagamento.getNome_pagador());
                    cell = row.createCell(cellnum++);
                    cell.setCellStyle((CellStyle)celula_normal);
                    cell.setCellValue(pagamento.getNome_recebedor());
                    cell = row.createCell(cellnum++);
                    cell.setCellStyle((CellStyle)celula_normal);
                    cell.setCellValue(pagamento.getFpag().getDescricao());
                    cell = row.createCell(cellnum++);
                    cell.setCellStyle((CellStyle)valorStyleFundoBrancoTextopreto);
                    cell.setCellValue(pagamento.getFpag().getValor().doubleValue());
                    cell = row.createCell(cellnum++);
                    cell.setCellStyle((CellStyle)celula_normal);
                    cell.setCellValue(pagamento.getFpag().getData_pagamento());
                    cell = row.createCell(cellnum++);
                    cell.setCellStyle((CellStyle)celula_normal);
                    cell.setCellValue(pagamento.getNome_forma_pagamento());
                    status = "";
                    if (pagamento.getFpag().getStatus_pagamento() == 0) {
                        status = "A - Compensar|Realizar|Concluir";
                    } else if (pagamento.getFpag().getStatus_pagamento() == 1) {
                        status = "Compensado|Realizado|Conclu\u00eddo";
                    }
                    cell = row.createCell(cellnum++);
                    cell.setCellStyle((CellStyle)celula_normal);
                    cell.setCellValue(status);
                    continue;
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            ultima_linha = rownum;
        }
        HSSFFormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
        row = sheet.createRow(++rownum);
        cellnum = 0;
        cell = row.createCell(2);
        cell.setCellStyle((CellStyle)celula_fundo_branco_texto_preto);
        cell.setCellValue("Valor Total:");
        int linha_valor_total = rownum++;
        cell = row.createCell(3);
        cell.setCellStyle((CellStyle)valorStyleFundoVerdeTextoBranco);
        cell.setCellType(CellType.FORMULA);
        String formula = "SUM(D" + primeira_linha + ":D" + (ultima_linha + 1) + ")";
        cell.setCellFormula(formula);
        cell = row.createCell(9);
        cell.setCellStyle((CellStyle)celula_fundo_branco_texto_preto);
        cell.setCellValue("Valor Total:");
        cell = row.createCell(10);
        cell.setCellStyle((CellStyle)valorStyleFundoVerdeTextoBranco);
        cell.setCellType(CellType.FORMULA);
        formula = "SUM(K" + primeira_linha + ":K" + (ultima_linha + 1) + ")";
        cell.setCellFormula(formula);
        row = sheet.createRow(++rownum);
        cell = row.createCell(2);
        cell.setCellStyle((CellStyle)celula_fundo_branco_texto_preto);
        cell.setCellValue("Valor Restante:");
        cell = row.createCell(3);
        cell.setCellStyle((CellStyle)valorStyleFundoVerdeTextoBranco);
        cell.setCellType(CellType.FORMULA);
        formula = "SUM(D" + (linha_valor_total + 1) + "-K" + (linha_valor_total + 1) + ")";
        cell.setCellFormula(formula);
        for (int i7 = 0; i7 < 20; ++i7) {
            sheet.autoSizeColumn(i7);
        }
        return workbook;
    }

    public void getDadosGlobais() {
        DadosGlobais dados = DadosGlobais.getInstance();
        this.GerenciadorLog = dados.getGerenciadorLog();
        this.configs_globais = dados.getConfigs_globais();
        this.login = dados.getLogin();
    }

    public void fechar() {
        this.isto.dispose();
    }
}