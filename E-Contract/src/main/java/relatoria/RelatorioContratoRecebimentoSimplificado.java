
package main.java.relatoria;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroGrupo;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroModelo;
import main.java.cadastros.CadastroSafra;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoSafras;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.outros.DadosGlobais;
import main.java.outros.GetData;
import main.java.tratamento_proprio.Log;
import main.java.views_personalizadas.TelaEmEspera;
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
import org.apache.poi.util.IOUtils;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.IBody;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDocument1;
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
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTabJc;

public class RelatorioContratoRecebimentoSimplificado {
    private CadastroModelo modelo;
    private String path;
    private TelaEmEspera telaInformacoes;
    private Log GerenciadorLog;
    private CadastroLogin login;
    private ConfiguracoesGlobais configs_globais;
    private String servidor_unidade;
    private XWPFDocument document_global;
    private XWPFParagraph paragrafo_atual;
    private ArrayList<CadastroCliente> clientes_globais;
    private int id_safra;
    private boolean contrato;
    private boolean contrato_como_comprador;
    private boolean pagamento = false;
    private boolean pagamento_como_depositante = false;
    private boolean pagamento_como_favorecido = false;
    private boolean carregamento = false;
    private boolean carregamento_como_comprador = false;
    private boolean carregamento_como_vendedor = false;
    private boolean recebimento = false;
    private boolean recebimento_como_comprador = false;
    private boolean recebimento_como_vendedor = false;
    private boolean unir_recebimentos = false;
    private boolean sub_contratos = false;
    private boolean incluir_comissao = false;
    private boolean incluir_ganhos_potencias = false;
    private CadastroGrupo grupo_alvo_global;
    private int tipo_contrato = -1;
    private boolean somar_sub_contratos = false;
    private String safra_evidencia = "2020/2021";
    private CadastroCliente contra_parte;
    HSSFFont newFont_branca;
    CellStyle celula_fundo_verde_texto_branco;
    HSSFFont newFont_preta;
    HSSFDataFormat numberFormat;
    CellStyle celula_fundo_branco_texto_preto;
    CellStyle pesoStyle;
    HSSFDataFormat numberFormatRecebimentos;
    HSSFFont newFont_brancaRecebimentos;
    CellStyle numberStyleFundoVerdeTextoBrancoRecebimentos;

    public CadastroCliente getContra_parte() {
        return this.contra_parte;
    }

    public void setContra_parte(CadastroCliente contra_parte) {
        this.contra_parte = contra_parte;
    }

    public boolean isUnir_recebimentos() {
        return this.unir_recebimentos;
    }

    public void setUnir_recebimentos(boolean unir_recebimentos) {
        this.unir_recebimentos = unir_recebimentos;
    }

    public boolean isRecebimento() {
        return this.recebimento;
    }

    public void setRecebimento(boolean recebimento) {
        this.recebimento = recebimento;
    }

    public boolean isRecebimento_como_comprador() {
        return this.recebimento_como_comprador;
    }

    public void setRecebimento_como_comprador(boolean recebimento_como_comprador) {
        this.recebimento_como_comprador = recebimento_como_comprador;
    }

    public boolean isRecebimento_como_vendedor() {
        return this.recebimento_como_vendedor;
    }

    public void setRecebimento_como_vendedor(boolean recebimento_como_vendedor) {
        this.recebimento_como_vendedor = recebimento_como_vendedor;
    }

    public ArrayList<CadastroCliente> getClientes_globais() {
        return this.clientes_globais;
    }

    public void setClientes_globais(ArrayList<CadastroCliente> clientes_globais) {
        this.clientes_globais = clientes_globais;
    }

    public int getId_safra() {
        return this.id_safra;
    }

    public void setId_safra(int id_safra) {
        this.id_safra = id_safra;
    }

    public boolean isContrato() {
        return this.contrato;
    }

    public void setContrato(boolean contrato) {
        this.contrato = contrato;
    }

    public boolean isContrato_como_comprador() {
        return this.contrato_como_comprador;
    }

    public void setContrato_como_comprador(boolean contrato_como_comprador) {
        this.contrato_como_comprador = contrato_como_comprador;
    }

    public boolean isPagamento() {
        return this.pagamento;
    }

    public void setPagamento(boolean pagamento) {
        this.pagamento = pagamento;
    }

    public boolean isPagamento_como_depositante() {
        return this.pagamento_como_depositante;
    }

    public void setPagamento_como_depositante(boolean pagamento_como_depositante) {
        this.pagamento_como_depositante = pagamento_como_depositante;
    }

    public boolean isPagamento_como_favorecido() {
        return this.pagamento_como_favorecido;
    }

    public void setPagamento_como_favorecido(boolean pagamento_como_favorecido) {
        this.pagamento_como_favorecido = pagamento_como_favorecido;
    }

    public boolean isCarregamento() {
        return this.carregamento;
    }

    public void setCarregamento(boolean carregamento) {
        this.carregamento = carregamento;
    }

    public boolean isCarregamento_como_comprador() {
        return this.carregamento_como_comprador;
    }

    public void setCarregamento_como_comprador(boolean carregamento_como_comprador) {
        this.carregamento_como_comprador = carregamento_como_comprador;
    }

    public boolean isCarregamento_como_vendedor() {
        return this.carregamento_como_vendedor;
    }

    public void setCarregamento_como_vendedor(boolean carregamento_como_vendedor) {
        this.carregamento_como_vendedor = carregamento_como_vendedor;
    }

    public boolean isSub_contratos() {
        return this.sub_contratos;
    }

    public void setSub_contratos(boolean sub_contratos) {
        this.sub_contratos = sub_contratos;
    }

    public boolean isIncluir_comissao() {
        return this.incluir_comissao;
    }

    public void setIncluir_comissao(boolean incluir_comissao) {
        this.incluir_comissao = incluir_comissao;
    }

    public boolean isIncluir_ganhos_potencias() {
        return this.incluir_ganhos_potencias;
    }

    public void setIncluir_ganhos_potencias(boolean incluir_ganhos_potencias) {
        this.incluir_ganhos_potencias = incluir_ganhos_potencias;
    }

    public int getTipo_contrato() {
        return this.tipo_contrato;
    }

    public void setTipo_contrato(int tipo_contrato) {
        this.tipo_contrato = tipo_contrato;
    }

    public boolean isSomar_sub_contratos() {
        return this.somar_sub_contratos;
    }

    public void setSomar_sub_contratos(boolean somar_sub_contratos) {
        this.somar_sub_contratos = somar_sub_contratos;
    }

    public RelatorioContratoRecebimentoSimplificado() {
        this.getDadosGlobais();
        this.servidor_unidade = this.configs_globais.getServidorUnidade();
        this.criarDocumento();
    }

    public void criarDocumento() {
        CTSectPr section;
        this.document_global = new XWPFDocument();
        CTSectPr sectPr = this.document_global.getDocument().getBody().addNewSectPr();
        CTPageMar pageMar = sectPr.addNewPgMar();
        pageMar.setLeft(BigInteger.valueOf(720L));
        pageMar.setTop(BigInteger.valueOf(1440L));
        pageMar.setRight(BigInteger.valueOf(720L));
        pageMar.setBottom(BigInteger.valueOf(1440L));
        CTDocument1 document = this.document_global.getDocument();
        CTBody body = document.getBody();
        if (!body.isSetSectPr()) {
            body.addNewSectPr();
        }
        if (!(section = body.getSectPr()).isSetPgSz()) {
            section.addNewPgSz();
        }
        CTPageSz pageSize = section.getPgSz();
        pageSize.setOrient(STPageOrientation.LANDSCAPE);
        pageSize.setW(BigInteger.valueOf(15840L));
        pageSize.setH(BigInteger.valueOf(12240L));
        this.document_global.createStyles();
    }

    public String filtrosDaPesquisa() {
        String texto_pesquisa = "Busca por: \n";
        if (this.id_safra == 0) {
            texto_pesquisa = String.valueOf(texto_pesquisa) + "Safra: -TODAS AS SAFRAS- ";
        } else {
            GerenciarBancoSafras gerenciar = new GerenciarBancoSafras();
            CadastroSafra safra = gerenciar.getSafra(this.id_safra);
            String text_safra = String.valueOf(safra.getProduto().getNome_produto()) + " " + safra.getProduto().getTransgenia() + " " + safra.getAno_plantio() + "/" + safra.getAno_colheita();
            texto_pesquisa = String.valueOf(texto_pesquisa) + "Safra: " + text_safra;
        }
        texto_pesquisa = String.valueOf(texto_pesquisa) + "\n";
        if (this.recebimento) {
            texto_pesquisa = String.valueOf(texto_pesquisa) + "*Recebimentos";
            if (this.recebimento_como_comprador && this.recebimento_como_vendedor) {
                texto_pesquisa = String.valueOf(texto_pesquisa) + " como Comprador e Vendedor";
            } else if (this.recebimento_como_comprador && !this.recebimento_como_vendedor) {
                texto_pesquisa = String.valueOf(texto_pesquisa) + " como Comprador";
            } else if (!this.recebimento_como_comprador && this.recebimento_como_vendedor) {
                texto_pesquisa = String.valueOf(texto_pesquisa) + " como Vendedor";
            }
            texto_pesquisa = String.valueOf(texto_pesquisa) + "   ->Filtros: ";
        }
        texto_pesquisa = String.valueOf(texto_pesquisa) + "\n";
        return String.valueOf(texto_pesquisa) + "\n";
    }

    public HSSFWorkbook prepararExcel() {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFDataFormat numberFormat = workbook.createDataFormat();
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        HSSFCellStyle textStyle = workbook.createCellStyle();
        textStyle.setAlignment(HorizontalAlignment.CENTER);
        textStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        HSSFFont newFontforText = workbook.createFont();
        newFontforText.setBold(false);
        newFontforText.setColor(IndexedColors.BLACK.getIndex());
        newFontforText.setFontName("Calibri");
        newFontforText.setItalic(false);
        newFontforText.setFontHeight((short)220);
        textStyle.setFont((Font)newFontforText);
        HSSFCellStyle textStyleAlinhadoEsquerda = workbook.createCellStyle();
        textStyleAlinhadoEsquerda.setAlignment(HorizontalAlignment.LEFT);
        HSSFFont newFontforTextAlinhaEsquerda = workbook.createFont();
        newFontforTextAlinhaEsquerda.setBold(false);
        newFontforTextAlinhaEsquerda.setColor(IndexedColors.BLACK.getIndex());
        newFontforTextAlinhaEsquerda.setFontName("Calibri");
        newFontforTextAlinhaEsquerda.setItalic(false);
        newFontforTextAlinhaEsquerda.setFontHeight((short)220);
        textStyleAlinhadoEsquerda.setFont((Font)newFontforTextAlinhaEsquerda);
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
        HSSFCellStyle numberStyle = workbook.createCellStyle();
        numberStyle.setDataFormat(numberFormat.getFormat("R$ #,##0.00"));
        numberStyle.setAlignment(HorizontalAlignment.CENTER);
        numberStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        HSSFCellStyle valorStyle = workbook.createCellStyle();
        valorStyle.setDataFormat(numberFormat.getFormat("R$ #,##0.00"));
        valorStyle.setAlignment(HorizontalAlignment.CENTER);
        valorStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        HSSFCellStyle celula_fundo_laranja = workbook.createCellStyle();
        celula_fundo_laranja.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        celula_fundo_laranja.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
        celula_fundo_laranja.setAlignment(HorizontalAlignment.CENTER);
        celula_fundo_laranja.setVerticalAlignment(VerticalAlignment.CENTER);
        HSSFFont newFont = workbook.createFont();
        newFont.setBold(true);
        newFont.setColor(IndexedColors.BLACK.getIndex());
        newFont.setFontName("Calibri");
        newFont.setItalic(false);
        newFont.setFontHeight((short)220);
        celula_fundo_laranja.setFont((Font)newFont);
        HSSFCellStyle negrito = workbook.createCellStyle();
        negrito.setDataFormat(numberFormat.getFormat("R$ #,##0.00"));
        negrito.setAlignment(HorizontalAlignment.CENTER);
        negrito.setVerticalAlignment(VerticalAlignment.CENTER);
        HSSFFont newFontNegrito = workbook.createFont();
        newFontNegrito.setBold(true);
        newFontNegrito.setColor(IndexedColors.BLACK.getIndex());
        newFontNegrito.setFontName("Arial");
        newFontNegrito.setItalic(false);
        newFontNegrito.setFontHeight((short)198);
        negrito.setFont((Font)newFontNegrito);
        HSSFCellStyle aviso = workbook.createCellStyle();
        aviso.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        aviso.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        aviso.setAlignment(HorizontalAlignment.LEFT);
        aviso.setVerticalAlignment(VerticalAlignment.CENTER);
        HSSFFont newFontVermelha = workbook.createFont();
        newFontVermelha.setBold(false);
        newFontVermelha.setColor(IndexedColors.RED.getIndex());
        newFontVermelha.setFontName("Arial");
        newFontVermelha.setItalic(true);
        newFontVermelha.setFontHeight((short)220);
        aviso.setFont((Font)newFontVermelha);
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
        HSSFCellStyle textStyleAlinhadoEsquerdaAviso = workbook.createCellStyle();
        textStyleAlinhadoEsquerdaAviso.setAlignment(HorizontalAlignment.LEFT);
        HSSFFont newFontforTextAlinhaEsquerdaAviso = workbook.createFont();
        newFontforTextAlinhaEsquerdaAviso.setBold(false);
        newFontforTextAlinhaEsquerdaAviso.setColor(IndexedColors.RED.getIndex());
        newFontforTextAlinhaEsquerdaAviso.setFontName("Calibri");
        newFontforTextAlinhaEsquerdaAviso.setItalic(false);
        newFontforTextAlinhaEsquerdaAviso.setFontHeight((short)220);
        textStyleAlinhadoEsquerdaAviso.setFont((Font)newFontforTextAlinhaEsquerdaAviso);
        HSSFCellStyle textStyleAlinhadoEsquerdaNegrito = workbook.createCellStyle();
        textStyleAlinhadoEsquerdaNegrito.setAlignment(HorizontalAlignment.LEFT);
        HSSFFont newFontforTextAlinhaEsquerdaNegrito = workbook.createFont();
        newFontforTextAlinhaEsquerdaNegrito.setBold(true);
        newFontforTextAlinhaEsquerdaNegrito.setColor(IndexedColors.BLACK.getIndex());
        newFontforTextAlinhaEsquerdaNegrito.setFontName("Calibri");
        newFontforTextAlinhaEsquerdaNegrito.setItalic(false);
        newFontforTextAlinhaEsquerdaNegrito.setFontHeight((short)220);
        textStyleAlinhadoEsquerdaNegrito.setFont((Font)newFontforTextAlinhaEsquerdaNegrito);
        HSSFCellStyle celula_fundo_laranja_texto_branco = workbook.createCellStyle();
        celula_fundo_laranja_texto_branco.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        celula_fundo_laranja_texto_branco.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
        celula_fundo_laranja_texto_branco.setAlignment(HorizontalAlignment.CENTER);
        celula_fundo_laranja_texto_branco.setVerticalAlignment(VerticalAlignment.CENTER);
        HSSFFont newFont_branca = workbook.createFont();
        newFont_branca.setBold(true);
        newFont_branca.setColor(IndexedColors.WHITE.getIndex());
        newFont_branca.setFontName("Calibri");
        newFont_branca.setItalic(false);
        newFont_branca.setFontHeight((short)220);
        celula_fundo_laranja_texto_branco.setFont((Font)newFont_branca);
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
        HSSFFont newFont_preta = workbook.createFont();
        newFont_preta.setColor(IndexedColors.BLACK.getIndex());
        newFont_preta.setFontName("Calibri");
        newFont_preta.setItalic(false);
        newFont_preta.setFontHeight((short)220);
        HSSFCellStyle celula_fundo_branco_texto_preto = workbook.createCellStyle();
        celula_fundo_branco_texto_preto.setAlignment(HorizontalAlignment.CENTER);
        celula_fundo_branco_texto_preto.setVerticalAlignment(VerticalAlignment.CENTER);
        celula_fundo_branco_texto_preto.setFont((Font)newFont_preta);
        HSSFCellStyle celula_fundo_branco_texto_preto_a_esquerda = workbook.createCellStyle();
        celula_fundo_branco_texto_preto_a_esquerda.setAlignment(HorizontalAlignment.LEFT);
        celula_fundo_branco_texto_preto_a_esquerda.setVerticalAlignment(VerticalAlignment.CENTER);
        celula_fundo_branco_texto_preto_a_esquerda.setFont((Font)newFont_preta);
        HSSFCellStyle celula_fundo_verde = workbook.createCellStyle();
        celula_fundo_verde.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        celula_fundo_verde.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        celula_fundo_verde.setAlignment(HorizontalAlignment.CENTER);
        celula_fundo_verde.setVerticalAlignment(VerticalAlignment.CENTER);
        celula_fundo_verde.setFont((Font)newFont);
        HSSFCellStyle pesoStyle = workbook.createCellStyle();
        pesoStyle.setDataFormat(numberFormat.getFormat("#,##0.00"));
        pesoStyle.setAlignment(HorizontalAlignment.CENTER);
        pesoStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        HSSFCellStyle valorStyleFundoVerdeTextoBranco = workbook.createCellStyle();
        valorStyleFundoVerdeTextoBranco.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        valorStyleFundoVerdeTextoBranco.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        valorStyleFundoVerdeTextoBranco.setAlignment(HorizontalAlignment.CENTER);
        valorStyleFundoVerdeTextoBranco.setVerticalAlignment(VerticalAlignment.CENTER);
        valorStyleFundoVerdeTextoBranco.setDataFormat(numberFormat.getFormat("R$ #,##0.00"));
        valorStyleFundoVerdeTextoBranco.setFont((Font)newFont_branca);
        HSSFCellStyle numberStyleFundoVerdeTextoBranco = workbook.createCellStyle();
        numberStyleFundoVerdeTextoBranco.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        numberStyleFundoVerdeTextoBranco.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        numberStyleFundoVerdeTextoBranco.setAlignment(HorizontalAlignment.CENTER);
        numberStyleFundoVerdeTextoBranco.setVerticalAlignment(VerticalAlignment.CENTER);
        numberStyleFundoVerdeTextoBranco.setDataFormat(numberFormat.getFormat("#,##0.00"));
        numberStyleFundoVerdeTextoBranco.setFont((Font)newFont_branca);
        int rownum = 0;
        int cellnum = 0;
        GetData data = new GetData();
        String data_criacao = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String criador = "Relat\u00f3rio de Recebimento Simplificado por " + this.login.getNome() + " " + this.login.getSobrenome() + " em " + data_criacao + " \u00e1s " + data.getHora();
        HSSFSheet sheet = workbook.createSheet("Recebimento Simplificado");
        sheet.setDefaultColumnWidth(25);
        sheet.setDefaultRowHeight((short)400);
        HSSFRow row = sheet.createRow(rownum);
        Cell cell = row.createCell(cellnum);
        cell.setCellStyle((CellStyle)celula_fundo_branco_texto_preto_a_esquerda);
        cell.setCellValue(criador);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, cellnum, 6));
        cellnum = 0;
        row = sheet.createRow(rownum += 2);
        cell = row.createCell(cellnum);
        cell.setCellStyle((CellStyle)celula_fundo_branco_texto_preto_a_esquerda);
        cell.setCellValue(this.filtrosDaPesquisa());
        sheet.addMergedRegion(new CellRangeAddress(rownum, rownum += 3, cellnum, 6));
        ArrayList clientes_pesquisa = new ArrayList();
        if (this.grupo_alvo_global != null) {
            clientes_pesquisa = this.grupo_alvo_global.getClientes();
        } else {
            clientes_pesquisa = this.clientes_globais;
            this.grupo_alvo_global = new CadastroGrupo();
            this.grupo_alvo_global.setClientes(clientes_pesquisa);
            this.grupo_alvo_global.setNome_grupo("");
        }
        String text = "";
        text = String.valueOf(text) + "Grupo: " + this.grupo_alvo_global.getNome_grupo().toUpperCase();
        text = String.valueOf(text) + "\nIntegrantes: ";
        for (CadastroCliente cliente : this.grupo_alvo_global.getClientes()) {
            if (cliente.getId() == 0) {
                text = String.valueOf(text) + "TODOS";
                continue;
            }
            text = cliente.getTipo_pessoa() == 0 ? String.valueOf(text) + cliente.getNome_empresarial().toUpperCase() : String.valueOf(text) + cliente.getNome_fantaia().toUpperCase();
            text = String.valueOf(text) + " , ";
        }
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
            anchor.setRow2(3);
            anchor.setCol1(7);
            anchor.setCol2(9);
            drawing.createPicture(anchor, pictureureIdx);
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
        cellnum = 0;
        row = sheet.createRow(++rownum);
        cell = row.createCell(cellnum);
        cell.setCellStyle((CellStyle)celula_fundo_branco_texto_preto_a_esquerda);
        cell.setCellValue(text);
        sheet.addMergedRegion(new CellRangeAddress(rownum, rownum += 3, cellnum, 6));
        NumberFormat z = NumberFormat.getNumberInstance();
        Locale ptBr = new Locale("pt", "BR");
        rownum += 2;
        cellnum = 0;
        GerenciarBancoContratos procura_contratos = new GerenciarBancoContratos();
        for (CadastroCliente cliente : this.clientes_globais) {
            double soma_quantidade_total;
            ArrayList quantidades_recebidas;
            ArrayList quantidades_totais;
            GerenciarBancoContratos gerenciar;
            if (cliente == null || !this.recebimento) continue;
            if (this.recebimento_como_comprador) {
                gerenciar = new GerenciarBancoContratos();
                quantidades_totais = gerenciar.getQuantidades(this.id_safra, cliente.getId(), this.contra_parte.getId(), 1);
                quantidades_recebidas = gerenciar.getRecebidas(this.id_safra, cliente.getId(), this.contra_parte.getId(), 1);
                soma_quantidade_total = 0.0;
                for (int i = 0; i < quantidades_totais.size(); ++i) {
                    soma_quantidade_total += ((RegistroQuantidade)quantidades_totais.get(i)).getTotal();
                }
                if (!(soma_quantidade_total > 0.0)) continue;
                DadosTabelaExcel dados = this.criarTabelaInformacoesExcel(workbook, sheet, rownum, quantidades_totais, quantidades_recebidas);
                sheet = dados.getSheet();
                rownum = dados.getRownum();
                workbook = dados.getWorkbook();
                continue;
            }
            if (!this.recebimento_como_vendedor) continue;
            gerenciar = new GerenciarBancoContratos();
            quantidades_totais = gerenciar.getQuantidades(this.id_safra, cliente.getId(), this.contra_parte.getId(), 2);
            quantidades_recebidas = gerenciar.getRecebidas(this.id_safra, cliente.getId(), this.contra_parte.getId(), 2);
            soma_quantidade_total = 0.0;
            for (int i = 0; i < quantidades_totais.size(); ++i) {
                soma_quantidade_total += ((RegistroQuantidade)quantidades_totais.get(i)).getTotal();
            }
            if (!(soma_quantidade_total > 0.0)) continue;
            DadosTabelaExcel dados = this.criarTabelaInformacoesExcel(workbook, sheet, rownum, quantidades_totais, quantidades_recebidas);
            sheet = dados.getSheet();
            rownum = dados.getRownum();
            workbook = dados.getWorkbook();
        }
        return workbook;
    }

    public String reduzirNome(String texto) {
        String nome_remetente_completo = texto;
        String[] nome_remetente_quebrado = texto.split(" ");
        String nome_remetente = null;
        try {
            if (nome_remetente_quebrado.length > 1) {
                nome_remetente = nome_remetente_quebrado[2].length() > 2 ? String.valueOf(nome_remetente_quebrado[0]) + " " + nome_remetente_quebrado[2] : (nome_remetente_quebrado[3].length() > 1 ? String.valueOf(nome_remetente_quebrado[0]) + " " + nome_remetente_quebrado[3] : String.valueOf(nome_remetente_quebrado[0]) + " " + nome_remetente_quebrado[1]);
            }
        }
        catch (Exception y) {
            nome_remetente = nome_remetente_completo;
        }
        return nome_remetente;
    }

    public String preparar() {
        Locale ptBr = new Locale("pt", "BR");
        NumberFormat z = NumberFormat.getNumberInstance();
        XWPFParagraph rodape = this.document_global.createParagraph();
        rodape.setAlignment(ParagraphAlignment.LEFT);
        CTSectPr sectPr = this.document_global.getDocument().getBody().addNewSectPr();
        CTPageMar pageMar = sectPr.addNewPgMar();
        pageMar.setLeft(BigInteger.valueOf(100L));
        pageMar.setTop(BigInteger.valueOf(100L));
        pageMar.setRight(BigInteger.valueOf(100L));
        pageMar.setBottom(BigInteger.valueOf(100L));
        XWPFParagraph title = this.document_global.createParagraph();
        title.setAlignment(ParagraphAlignment.LEFT);
        GetData data = new GetData();
        XWPFRun titleRun = title.createRun();
        String data_criacao = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        titleRun.setText("Relat\u00f3rio de Contratos por " + this.login.getNome() + " " + this.login.getSobrenome() + " em " + data_criacao + " \u00e1s " + data.getHora());
        titleRun.setColor("000000");
        titleRun.setBold(false);
        titleRun.setUnderline(UnderlinePatterns.SINGLE);
        titleRun.setFontFamily("Arial");
        titleRun.setFontSize(9);
        XWPFParagraph filtros = this.document_global.createParagraph();
        filtros.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun dadosPesquisaRun = filtros.createRun();
        String texto_pesquisa = "Busca por: ";
        if (this.carregamento) {
            texto_pesquisa = String.valueOf(texto_pesquisa) + "Carregamentos";
            if (this.carregamento_como_comprador && this.carregamento_como_vendedor) {
                texto_pesquisa = String.valueOf(texto_pesquisa) + " como Comprador e Vendedor";
            } else if (this.carregamento_como_comprador && !this.carregamento_como_vendedor) {
                texto_pesquisa = String.valueOf(texto_pesquisa) + " como Comprador";
            } else if (!this.carregamento_como_comprador && this.carregamento_como_vendedor) {
                texto_pesquisa = String.valueOf(texto_pesquisa) + " como Vendedor";
            }
        }
        texto_pesquisa = String.valueOf(texto_pesquisa) + "\n";
        if (this.pagamento) {
            texto_pesquisa = String.valueOf(texto_pesquisa) + " | Pagamentos";
            if (this.pagamento_como_depositante && this.pagamento_como_favorecido) {
                texto_pesquisa = String.valueOf(texto_pesquisa) + " como Depositante e Favorecido";
            } else if (this.pagamento_como_depositante && !this.pagamento_como_favorecido) {
                texto_pesquisa = String.valueOf(texto_pesquisa) + " como Depositante";
            } else if (!this.pagamento_como_depositante && this.pagamento_como_favorecido) {
                texto_pesquisa = String.valueOf(texto_pesquisa) + " como Favorecido";
            }
        }
        texto_pesquisa = String.valueOf(texto_pesquisa) + "\n";
        if (this.recebimento) {
            texto_pesquisa = String.valueOf(texto_pesquisa) + " | Recebimentos";
            if (this.recebimento_como_comprador && this.recebimento_como_vendedor) {
                texto_pesquisa = String.valueOf(texto_pesquisa) + " como Comprador e Vendedor";
            } else if (this.recebimento_como_comprador && !this.recebimento_como_vendedor) {
                texto_pesquisa = String.valueOf(texto_pesquisa) + " como Comprador";
            } else if (!this.recebimento_como_comprador && this.recebimento_como_vendedor) {
                texto_pesquisa = String.valueOf(texto_pesquisa) + " como Vendedor";
            }
        }
        if (this.id_safra == 0) {
            texto_pesquisa = String.valueOf(texto_pesquisa) + " | Safra: TODAS AS SAFRAS / Evid\u00eancia: " + this.safra_evidencia;
        } else {
            GerenciarBancoSafras gerenciar = new GerenciarBancoSafras();
            CadastroSafra safra = gerenciar.getSafra(this.id_safra);
            String text_safra = String.valueOf(safra.getProduto().getNome_produto()) + " " + safra.getProduto().getTransgenia() + " " + safra.getAno_plantio() + "/" + safra.getAno_colheita();
            texto_pesquisa = String.valueOf(texto_pesquisa) + " Safra: " + (String)text_safra;
        }
        dadosPesquisaRun.setText("Filtros da pesquisa: \n" + texto_pesquisa);
        dadosPesquisaRun.setColor("000000");
        dadosPesquisaRun.setBold(false);
        dadosPesquisaRun.setFontFamily("Arial");
        dadosPesquisaRun.setFontSize(10);
        GerenciarBancoContratos procura_contratos = new GerenciarBancoContratos();
        for (CadastroCliente cliente : this.clientes_globais) {
            ArrayList quantidades_recebidas;
            ArrayList quantidades_totais;
            GerenciarBancoContratos gerenciar;
            if (cliente == null || !this.recebimento) continue;
            if (this.recebimento_como_comprador) {
                gerenciar = new GerenciarBancoContratos();
                quantidades_totais = gerenciar.getQuantidades(this.id_safra, cliente.getId(), this.contra_parte.getId(), 1);
                quantidades_recebidas = gerenciar.getRecebidas(this.id_safra, cliente.getId(), this.contra_parte.getId(), 1);
                this.criarTabelaInformacoes(quantidades_totais, quantidades_recebidas);
                continue;
            }
            if (!this.recebimento_como_vendedor) continue;
            gerenciar = new GerenciarBancoContratos();
            quantidades_totais = gerenciar.getQuantidades(this.id_safra, cliente.getId(), this.contra_parte.getId(), 2);
            quantidades_recebidas = gerenciar.getRecebidas(this.id_safra, cliente.getId(), this.contra_parte.getId(), 2);
            this.criarTabelaInformacoes(quantidades_totais, quantidades_recebidas);
        }
        try {
            CTP ctP = CTP.Factory.newInstance();
            CTText t = ctP.addNewR().addNewT();
            XWPFParagraph cabecalho = new XWPFParagraph(ctP, (IBody)this.document_global);
            XWPFRun cabecalhoRun = cabecalho.createRun();
            cabecalhoRun.setFontSize(16);
            cabecalhoRun.setFontFamily("Arial Black");
            cabecalhoRun.setText("LD ARMAZ\u00c9NS GERAIS");
            cabecalhoRun.setUnderline(UnderlinePatterns.SINGLE);
            cabecalhoRun.setColor("00A000");
            XWPFParagraph[] pars = new XWPFParagraph[]{cabecalho};
            pars[0].setAlignment(ParagraphAlignment.LEFT);
            XWPFHeaderFooterPolicy hfPolicy = this.document_global.createHeaderFooterPolicy();
            XWPFHeader header = hfPolicy.createHeader(XWPFHeaderFooterPolicy.DEFAULT, pars);
            pars[0] = header.getParagraphArray(0);
            pars[0].setAlignment(ParagraphAlignment.LEFT);
            CTTabStop tabStop = pars[0].getCTP().getPPr().addNewTabs().addNewTab();
            tabStop.setVal(STTabJc.RIGHT);
            int twipsPerInch = 1440;
            tabStop.setPos(BigInteger.valueOf(6 * twipsPerInch));
            cabecalhoRun = pars[0].createRun();
            cabecalhoRun.addTab();
            String imgFile = String.valueOf(this.configs_globais.getProps().getPasta_instalacao()) + "\\imagens\\logo_para_relatorio.png";
            cabecalhoRun = pars[0].createRun();
            cabecalhoRun.addPicture((InputStream)new FileInputStream(imgFile), 6, imgFile, Units.toEMU((double)30.0), Units.toEMU((double)30.0));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            this.document_global.write((OutputStream)new FileOutputStream("c:\\temp\\arquivoteste.docx"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return "c:\\temp\\arquivoteste.docx";
    }

    public void setSingleLineSpacing(XWPFParagraph para) {
        CTPPr ppr = para.getCTP().getPPr();
        if (ppr == null) {
            ppr = para.getCTP().addNewPPr();
        }
        CTSpacing spacing = ppr.isSetSpacing() ? ppr.getSpacing() : ppr.addNewSpacing();
        spacing.setAfter(BigInteger.valueOf(0L));
        spacing.setBefore(BigInteger.valueOf(0L));
        spacing.setLineRule(STLineSpacingRule.AUTO);
        spacing.setLine(BigInteger.valueOf(240L));
    }

    public void criarTabelaInformacoes(ArrayList<RegistroQuantidade> quantidades_totais, ArrayList<RegistroRecebimento> quantidades_recebidas) {
        NumberFormat z = NumberFormat.getNumberInstance();
        Locale ptBr = new Locale("pt", "BR");
        int num_linhas_registros = quantidades_totais.size() + 4;
        XWPFTable table = this.document_global.createTable(num_linhas_registros, 6);
        this.setTableAlign(table, ParagraphAlignment.CENTER);
        XWPFTableRow tableRowOne = table.getRow(0);
        tableRowOne.getCell(0).removeParagraph(0);
        XWPFParagraph paragraph = tableRowOne.getCell(0).addParagraph();
        this.criarParagrafoTabela(paragraph, "COMPRADOR", true);
        tableRowOne = table.getRow(0);
        tableRowOne.getCell(1).removeParagraph(0);
        paragraph = tableRowOne.getCell(1).addParagraph();
        this.criarParagrafoTabela(paragraph, "VENDEDOR", true);
        tableRowOne = table.getRow(0);
        tableRowOne.getCell(2).removeParagraph(0);
        paragraph = tableRowOne.getCell(2).addParagraph();
        this.criarParagrafoTabela(paragraph, "TOTAL CONTRATADO", true);
        tableRowOne = table.getRow(0);
        tableRowOne.getCell(3).removeParagraph(0);
        paragraph = tableRowOne.getCell(3).addParagraph();
        this.criarParagrafoTabela(paragraph, "TOTAL RECEBIDO", true);
        tableRowOne = table.getRow(0);
        tableRowOne.getCell(4).removeParagraph(0);
        paragraph = tableRowOne.getCell(4).addParagraph();
        this.criarParagrafoTabela(paragraph, "FALTA", true);
        tableRowOne = table.getRow(0);
        tableRowOne.getCell(5).removeParagraph(0);
        paragraph = tableRowOne.getCell(5).addParagraph();
        this.criarParagrafoTabela(paragraph, "SITUA\u00c7\u00c3O", true);
        int i = 1;
        double somatoria_quantidade_total = 0.0;
        double somatoria_quantidade_recebida = 0.0;
        double somatoria_quantidade_restante = 0.0;
        int quantidade_clientes_entregando = 0;
        int quantidade_clientes_pendente = 0;
        int quantidade_clientes_finalizado = 0;
        for (int J = 0; J < quantidades_totais.size(); ++J) {
            String comprador = quantidades_totais.get(J).getComprador();
            String vendedor = quantidades_totais.get(J).getVendedor();
            double quantidade_total = quantidades_totais.get(J).getTotal();
            double quantidade_recebida = quantidades_recebidas.get(J).getQuantidade_recebida();
            double restante = quantidades_totais.get(J).getTotal() - quantidades_recebidas.get(J).getQuantidade_recebida();
            tableRowOne = table.getRow(i);
            tableRowOne.getCell(0).removeParagraph(0);
            paragraph = tableRowOne.getCell(0).addParagraph();
            this.criarParagrafoTabela(paragraph, comprador, false);
            tableRowOne = table.getRow(i);
            tableRowOne.getCell(1).removeParagraph(0);
            paragraph = tableRowOne.getCell(1).addParagraph();
            this.criarParagrafoTabela(paragraph, vendedor, false);
            tableRowOne = table.getRow(i);
            tableRowOne.getCell(2).removeParagraph(0);
            paragraph = tableRowOne.getCell(2).addParagraph();
            this.criarParagrafoTabela(paragraph, String.valueOf(z.format(quantidade_total)) + " sacos", false);
            somatoria_quantidade_total += quantidade_total;
            tableRowOne = table.getRow(i);
            tableRowOne.getCell(3).removeParagraph(0);
            paragraph = tableRowOne.getCell(3).addParagraph();
            this.criarParagrafoTabela(paragraph, String.valueOf(z.format(quantidade_recebida)) + " sacos", false);
            somatoria_quantidade_recebida += quantidade_recebida;
            tableRowOne = table.getRow(i);
            tableRowOne.getCell(4).removeParagraph(0);
            paragraph = tableRowOne.getCell(4).addParagraph();
            this.criarParagrafoTabela(paragraph, String.valueOf(z.format(restante)) + " sacos", false);
            somatoria_quantidade_restante += restante;
            tableRowOne = table.getRow(i);
            tableRowOne.getCell(5).removeParagraph(0);
            paragraph = tableRowOne.getCell(5).addParagraph();
            if (restante == 0.0 || restante == 0.0 || (int)restante == 0 || (int)quantidade_recebida >= (int)quantidade_total) {
                this.criarParagrafoTabela(paragraph, "FINALIZADO", false);
                tableRowOne.getCell(5).getCTTc().addNewTcPr().addNewShd().setFill((Object)"2F4F4F");
                ++quantidade_clientes_finalizado;
            } else if (quantidade_recebida == 0.0) {
                this.criarParagrafoTabela(paragraph, "PENDENTE", false);
                tableRowOne.getCell(5).getCTTc().addNewTcPr().addNewShd().setFill((Object)"A0522D");
                ++quantidade_clientes_pendente;
            } else if (quantidade_recebida > 0.0 && quantidade_recebida < quantidade_total) {
                this.criarParagrafoTabela(paragraph, "ENTREGANDO", false);
                tableRowOne.getCell(5).getCTTc().addNewTcPr().addNewShd().setFill((Object)"ADFF2F");
                ++quantidade_clientes_entregando;
            }
            ++i;
        }
        tableRowOne = table.getRow(++i);
        tableRowOne.getCell(1).removeParagraph(0);
        paragraph = tableRowOne.getCell(1).addParagraph();
        this.criarParagrafoTabela(paragraph, "Somat\u00f3rio", false);
        tableRowOne = table.getRow(i);
        tableRowOne.getCell(2).removeParagraph(0);
        paragraph = tableRowOne.getCell(2).addParagraph();
        this.criarParagrafoTabela(paragraph, String.valueOf(z.format(somatoria_quantidade_total)) + " sacos", true);
        tableRowOne = table.getRow(i);
        tableRowOne.getCell(3).removeParagraph(0);
        paragraph = tableRowOne.getCell(3).addParagraph();
        this.criarParagrafoTabela(paragraph, String.valueOf(z.format(somatoria_quantidade_recebida)) + " sacos", true);
        tableRowOne = table.getRow(i);
        tableRowOne.getCell(4).removeParagraph(0);
        paragraph = tableRowOne.getCell(4).addParagraph();
        this.criarParagrafoTabela(paragraph, String.valueOf(z.format(somatoria_quantidade_restante)) + " sacos", true);
        String texto = "Clientes Entregando: " + quantidade_clientes_entregando + "\n" + "Clientes Pendente: " + quantidade_clientes_pendente + "\n" + "Clientes Finalizado: " + quantidade_clientes_finalizado + "\n";
        this.substituirTexto(texto, -1);
    }

    public DadosTabelaExcel criarTabelaInformacoesExcel(HSSFWorkbook workbook, HSSFSheet sheet, int rownum, ArrayList<RegistroQuantidade> quantidades_totais, ArrayList<RegistroRecebimento> quantidades_recebidas) {
        NumberFormat z = NumberFormat.getNumberInstance();
        Locale ptBr = new Locale("pt", "BR");
        this.numberFormat = workbook.createDataFormat();
        this.newFont_branca = workbook.createFont();
        this.newFont_branca.setBold(true);
        this.newFont_branca.setColor(IndexedColors.WHITE.getIndex());
        this.newFont_branca.setFontName("Calibri");
        this.newFont_branca.setItalic(false);
        this.newFont_branca.setFontHeight((short)220);
        this.celula_fundo_verde_texto_branco = workbook.createCellStyle();
        this.celula_fundo_verde_texto_branco.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        this.celula_fundo_verde_texto_branco.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        this.celula_fundo_verde_texto_branco.setAlignment(HorizontalAlignment.CENTER);
        this.celula_fundo_verde_texto_branco.setVerticalAlignment(VerticalAlignment.CENTER);
        this.celula_fundo_verde_texto_branco.setFont((Font)this.newFont_branca);
        HSSFCellStyle celula_fundo_vermelho_texto_branco = workbook.createCellStyle();
        celula_fundo_vermelho_texto_branco.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        celula_fundo_vermelho_texto_branco.setFillForegroundColor(IndexedColors.RED.getIndex());
        celula_fundo_vermelho_texto_branco.setAlignment(HorizontalAlignment.CENTER);
        celula_fundo_vermelho_texto_branco.setVerticalAlignment(VerticalAlignment.CENTER);
        celula_fundo_vermelho_texto_branco.setFont((Font)this.newFont_branca);
        this.newFont_preta = workbook.createFont();
        this.newFont_preta.setColor(IndexedColors.BLACK.getIndex());
        this.newFont_preta.setFontName("Calibri");
        this.newFont_preta.setItalic(false);
        this.newFont_preta.setFontHeight((short)220);
        this.celula_fundo_branco_texto_preto = workbook.createCellStyle();
        this.celula_fundo_branco_texto_preto.setAlignment(HorizontalAlignment.CENTER);
        this.celula_fundo_branco_texto_preto.setVerticalAlignment(VerticalAlignment.CENTER);
        this.celula_fundo_branco_texto_preto.setFont((Font)this.newFont_preta);
        this.pesoStyle = workbook.createCellStyle();
        this.pesoStyle.setDataFormat(this.numberFormat.getFormat("#,##0.00"));
        this.pesoStyle.setAlignment(HorizontalAlignment.CENTER);
        this.pesoStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        this.numberFormatRecebimentos = workbook.createDataFormat();
        this.newFont_brancaRecebimentos = workbook.createFont();
        this.newFont_brancaRecebimentos.setBold(true);
        this.newFont_brancaRecebimentos.setColor(IndexedColors.WHITE.getIndex());
        this.newFont_brancaRecebimentos.setFontName("Calibri");
        this.newFont_brancaRecebimentos.setItalic(false);
        this.newFont_brancaRecebimentos.setFontHeight((short)220);
        this.numberStyleFundoVerdeTextoBrancoRecebimentos = workbook.createCellStyle();
        this.numberStyleFundoVerdeTextoBrancoRecebimentos.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        this.numberStyleFundoVerdeTextoBrancoRecebimentos.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        this.numberStyleFundoVerdeTextoBrancoRecebimentos.setAlignment(HorizontalAlignment.CENTER);
        this.numberStyleFundoVerdeTextoBrancoRecebimentos.setVerticalAlignment(VerticalAlignment.CENTER);
        this.numberStyleFundoVerdeTextoBrancoRecebimentos.setDataFormat(this.numberFormatRecebimentos.getFormat("#,##0.00"));
        this.numberStyleFundoVerdeTextoBrancoRecebimentos.setFont((Font)this.newFont_brancaRecebimentos);
        HSSFCellStyle celula_fundo_amarelo_texto_preto = workbook.createCellStyle();
        celula_fundo_amarelo_texto_preto.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        celula_fundo_amarelo_texto_preto.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        celula_fundo_amarelo_texto_preto.setAlignment(HorizontalAlignment.CENTER);
        celula_fundo_amarelo_texto_preto.setVerticalAlignment(VerticalAlignment.CENTER);
        celula_fundo_amarelo_texto_preto.setFont((Font)this.newFont_preta);
        int cellnum = 0;
        HSSFRow row = sheet.createRow(rownum);
        cellnum = 0;
        Cell cell = row.createCell(cellnum++);
        cell.setCellStyle(this.celula_fundo_verde_texto_branco);
        cell.setCellValue("COMPRADOR");
        cell = row.createCell(cellnum++);
        cell.setCellStyle(this.celula_fundo_verde_texto_branco);
        cell.setCellValue("VENDEDOR");
        cell = row.createCell(cellnum++);
        cell.setCellStyle(this.celula_fundo_verde_texto_branco);
        cell.setCellValue("TOTAL CONTRATADO");
        cell = row.createCell(cellnum++);
        cell.setCellStyle(this.celula_fundo_verde_texto_branco);
        cell.setCellValue("TOTAL RECEBIDO");
        cell = row.createCell(cellnum++);
        cell.setCellStyle(this.celula_fundo_verde_texto_branco);
        cell.setCellValue("FALTA");
        cell = row.createCell(cellnum++);
        cell.setCellStyle(this.celula_fundo_verde_texto_branco);
        cell.setCellValue("SITUA\u00c7\u00c3O");
        int linha_cabecalho = rownum++;
        double somatoria_quantidade_total = 0.0;
        double somatoria_quantidade_recebida = 0.0;
        double somatoria_quantidade_restante = 0.0;
        int quantidade_clientes_entregando = 0;
        int quantidade_clientes_pendente = 0;
        int quantidade_clientes_finalizado = 0;
        int primeiraLinha = rownum;
        int ultimaLinha = rownum;
        for (int J = 0; J < quantidades_totais.size(); ++J) {
            String comprador = quantidades_totais.get(J).getComprador();
            String vendedor = quantidades_totais.get(J).getVendedor();
            double quantidade_total = quantidades_totais.get(J).getTotal();
            double quantidade_recebida = quantidades_recebidas.get(J).getQuantidade_recebida();
            double restante = quantidades_totais.get(J).getTotal() - quantidades_recebidas.get(J).getQuantidade_recebida();
            cellnum = 0;
            row = sheet.createRow(rownum);
            cell = row.createCell(cellnum++);
            cell.setCellStyle(this.celula_fundo_branco_texto_preto);
            cell.setCellValue(comprador);
            cell = row.createCell(cellnum++);
            cell.setCellStyle(this.celula_fundo_branco_texto_preto);
            cell.setCellValue(vendedor);
            cell = row.createCell(cellnum++);
            cell.setCellStyle(this.pesoStyle);
            cell.setCellValue(quantidade_total);
            somatoria_quantidade_total += quantidade_total;
            cell = row.createCell(cellnum++);
            cell.setCellStyle(this.pesoStyle);
            cell.setCellValue(quantidade_recebida);
            somatoria_quantidade_recebida += quantidade_recebida;
            cell = row.createCell(cellnum++);
            cell.setCellStyle(this.pesoStyle);
            cell.setCellValue(restante);
            somatoria_quantidade_restante += restante;
            cell = row.createCell(cellnum++);
            if (restante == 0.0 || restante == 0.0 || (int)restante == 0 || (int)quantidade_recebida >= (int)quantidade_total) {
                cell.setCellStyle(this.celula_fundo_verde_texto_branco);
                cell.setCellValue("FINALIZADO");
                ++quantidade_clientes_finalizado;
            } else if (quantidade_recebida == 0.0) {
                cell.setCellStyle((CellStyle)celula_fundo_vermelho_texto_branco);
                cell.setCellValue("PENDENTE");
                ++quantidade_clientes_pendente;
            } else if (quantidade_recebida > 0.0 && quantidade_recebida < quantidade_total) {
                cell.setCellStyle((CellStyle)celula_fundo_amarelo_texto_preto);
                cell.setCellValue("ENTREGANDO");
                ++quantidade_clientes_entregando;
            }
            ultimaLinha = ++rownum;
        }
        HSSFFormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
        row = sheet.createRow(++rownum);
        cellnum = 0;
        cell = row.createCell(1);
        cell.setCellStyle(this.celula_fundo_branco_texto_preto);
        cell.setCellValue("Valores Totais:");
        cell = row.createCell(2);
        cell.setCellStyle(this.numberStyleFundoVerdeTextoBrancoRecebimentos);
        cell.setCellType(CellType.FORMULA);
        String formula = "SUM(C" + primeiraLinha + ":C" + ultimaLinha + ")";
        cell.setCellFormula(formula);
        cell = row.createCell(3);
        cell.setCellStyle(this.numberStyleFundoVerdeTextoBrancoRecebimentos);
        cell.setCellType(CellType.FORMULA);
        formula = "SUM(D" + primeiraLinha + ":D" + ultimaLinha + ")";
        cell.setCellFormula(formula);
        cell = row.createCell(4);
        cell.setCellStyle(this.numberStyleFundoVerdeTextoBrancoRecebimentos);
        cell.setCellType(CellType.FORMULA);
        formula = "SUM(E" + primeiraLinha + ":E" + ultimaLinha + ")";
        cell.setCellFormula(formula);
        String texto = "Clientes Entregando: " + quantidade_clientes_entregando + "      " + "Clientes Pendente: " + quantidade_clientes_pendente + "        " + "Clientes Finalizado: " + quantidade_clientes_finalizado + "\n";
        row = sheet.createRow(++rownum);
        cell = row.createCell(0);
        cell.setCellStyle(this.celula_fundo_branco_texto_preto);
        cell.setCellValue(texto);
        sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 0, 6));
        ++rownum;
        for (int i = 0; i < 10; ++i) {
            sheet.autoSizeColumn(i);
        }
        DadosTabelaExcel retornar = new DadosTabelaExcel();
        retornar.setWorkbook(workbook);
        retornar.setSheet(sheet);
        retornar.setRownum(++rownum);
        return retornar;
    }

    public void adicionarTraco(boolean negrito, int flag) {
        XWPFParagraph traco = this.document_global.createParagraph();
        traco.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun tracotitleRun = traco.createRun();
        if (flag == 1) {
            tracotitleRun.setText("________________________________________________________________________________");
        } else {
            tracotitleRun.setText("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ __ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ ");
        }
        tracotitleRun.setColor("000000");
        tracotitleRun.setBold(negrito);
        tracotitleRun.setFontFamily("Arial");
        tracotitleRun.setFontSize(12);
    }

    public void substituirTexto(String text_amostra, int alinhamento) {
        String[] separador_paragrafo;
        String[] arrstring = separador_paragrafo = text_amostra.split("\n");
        int n = separador_paragrafo.length;
        for (int i = 0; i < n; ++i) {
            String[] separador_palabras;
            String paragrafo = arrstring[i];
            this.criarParagrafo(alinhamento);
            paragrafo = paragrafo.replaceAll(" ", "&");
            String[] arrstring2 = separador_palabras = paragrafo.split("&");
            int n2 = separador_palabras.length;
            for (int j = 0; j < n2; ++j) {
                String palavra = arrstring2[j];
                if (palavra.contains("[") || palavra.contains("]")) {
                    this.adicionarTextoParagrafoAtual(String.valueOf(palavra.replaceAll("[\\[\\]]", "")) + " ", true);
                    continue;
                }
                this.adicionarTextoParagrafoAtual(String.valueOf(palavra) + " ", false);
            }
        }
    }

    public void substituirTexto(String text_amostra) {
        String[] separador_paragrafo;
        String[] arrstring = separador_paragrafo = text_amostra.split("\n");
        int n = separador_paragrafo.length;
        for (int i = 0; i < n; ++i) {
            String[] separador_palabras;
            String paragrafo = arrstring[i];
            this.criarParagrafo(2);
            paragrafo = paragrafo.replaceAll(" ", "&");
            String[] arrstring2 = separador_palabras = paragrafo.split("&");
            int n2 = separador_palabras.length;
            for (int j = 0; j < n2; ++j) {
                String palavra = arrstring2[j];
                if (palavra.contains("[") || palavra.contains("]")) {
                    this.adicionarTextoParagrafoAtual(String.valueOf(palavra.replaceAll("[\\[\\]]", "")) + " ", true);
                    continue;
                }
                this.adicionarTextoParagrafoAtual(String.valueOf(palavra) + " ", false);
            }
        }
    }

    private void setOrientacao(int flag) {
        CTDocument1 doc = this.document_global.getDocument();
        CTBody body = doc.getBody();
        CTSectPr section = body.addNewSectPr();
        XWPFParagraph para = this.document_global.createParagraph();
        CTP ctp = para.getCTP();
        CTPPr br = ctp.addNewPPr();
        br.setSectPr(section);
        CTPageSz pageSize = section.isSetPgSz() ? section.getPgSz() : section.addNewPgSz();
        if (flag == 1) {
            pageSize.setOrient(STPageOrientation.PORTRAIT);
            pageSize.setW(BigInteger.valueOf(16840L));
            pageSize.setH(BigInteger.valueOf(11900L));
        } else {
            pageSize.setOrient(STPageOrientation.LANDSCAPE);
            pageSize.setH(BigInteger.valueOf(16840L));
            pageSize.setW(BigInteger.valueOf(11900L));
        }
    }

    public void criarParagrafo(int alinhamento) {
        XWPFParagraph paragrafo = this.document_global.createParagraph();
        this.setSingleLineSpacing(paragrafo);
        if (alinhamento == 0) {
            paragrafo.setAlignment(ParagraphAlignment.CENTER);
        } else if (alinhamento == 1) {
            paragrafo.setAlignment(ParagraphAlignment.RIGHT);
        } else if (alinhamento == -1) {
            paragrafo.setAlignment(ParagraphAlignment.LEFT);
        } else if (alinhamento == 2) {
            paragrafo.setAlignment(ParagraphAlignment.BOTH);
        }
        this.paragrafo_atual = paragrafo;
    }

    public void criarParagrafoTabela(XWPFParagraph paragraph, String texto, boolean negrito, String cor) {
        paragraph.setIndentationLeft(100);
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun run = paragraph.createRun();
        run.setFontFamily("Times New Roman");
        run.setFontSize(8);
        run.setColor(cor);
        run.setBold(negrito);
        run.setText(texto);
    }

    public void criarParagrafoTabela(XWPFParagraph paragraph, String texto, boolean negrito) {
        paragraph.setIndentationLeft(100);
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun run = paragraph.createRun();
        run.setFontFamily("Times New Roman");
        run.setFontSize(8);
        run.setBold(negrito);
        run.setText(texto);
    }

    public void setTableAlign(XWPFTable table, ParagraphAlignment align) {
        CTTblPr tblPr = table.getCTTbl().getTblPr();
        CTJc jc = tblPr.isSetJc() ? tblPr.getJc() : tblPr.addNewJc();
        STJc.Enum en = STJc.Enum.forInt((int)align.getValue());
        jc.setVal(en);
    }

    public void adicionarTextoParagrafoAtual(String texto, boolean negrito) {
        XWPFRun run = this.paragrafo_atual.createRun();
        run.setText(texto);
        run.setColor("000000");
        run.setBold(negrito);
        run.setFontFamily("Times New Roman");
        run.setFontSize(10);
    }

    public void saltarLinhaParagrafo() {
        XWPFRun corretortitleRun = this.paragrafo_atual.createRun();
        corretortitleRun.addBreak();
    }

    public void getDadosGlobais() {
        DadosGlobais dados = DadosGlobais.getInstance();
        this.GerenciadorLog = dados.getGerenciadorLog();
        this.configs_globais = dados.getConfigs_globais();
        this.login = dados.getLogin();
    }

    public boolean checkString(String txt) {
        return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ") && txt.length() > 0;
    }

    class DadosTabelaExcel {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = this.workbook.createSheet("Contratos");
        int rownum = 0;

        public HSSFWorkbook getWorkbook() {
            return this.workbook;
        }

        public HSSFSheet getSheet() {
            return this.sheet;
        }

        public int getRownum() {
            return this.rownum;
        }

        public void setWorkbook(HSSFWorkbook workbook) {
            this.workbook = workbook;
        }

        public void setSheet(HSSFSheet sheet) {
            this.sheet = sheet;
        }

        public void setRownum(int rownum) {
            this.rownum = rownum;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof DadosTabelaExcel)) {
                return false;
            }
            DadosTabelaExcel other = (DadosTabelaExcel)o;
            if (!other.canEqual(this)) {
                return false;
            }
            if (this.getRownum() != other.getRownum()) {
                return false;
            }
            HSSFWorkbook this$workbook = this.getWorkbook();
            HSSFWorkbook other$workbook = other.getWorkbook();
            if (this$workbook == null ? other$workbook != null : !this$workbook.equals((Object)other$workbook)) {
                return false;
            }
            HSSFSheet this$sheet = this.getSheet();
            HSSFSheet other$sheet = other.getSheet();
            return !(this$sheet == null ? other$sheet != null : !this$sheet.equals((Object)other$sheet));
        }

        protected boolean canEqual(Object other) {
            return other instanceof DadosTabelaExcel;
        }

        public int hashCode() {
            int PRIME = 59;
            int result = 1;
            result = result * 59 + this.getRownum();
            HSSFWorkbook $workbook = this.getWorkbook();
            result = result * 59 + ($workbook == null ? 43 : $workbook.hashCode());
            HSSFSheet $sheet = this.getSheet();
            result = result * 59 + ($sheet == null ? 43 : $sheet.hashCode());
            return result;
        }

        public String toString() {
            return "RelatorioContratoRecebimentoSimplificado.DadosTabelaExcel(workbook=" + (Object)this.getWorkbook() + ", sheet=" + (Object)this.getSheet() + ", rownum=" + this.getRownum() + ")";
        }

        public DadosTabelaExcel(HSSFWorkbook workbook, HSSFSheet sheet, int rownum) {
            this.workbook = workbook;
            this.sheet = sheet;
            this.rownum = rownum;
        }

        public DadosTabelaExcel() {
        }
    }
}
