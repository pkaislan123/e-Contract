package main.java.relatoria;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javax.imageio.ImageIO;
import javax.persistence.Entity;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.printing.Orientation;
import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.util.Units;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.model.XWPFParagraphDecorator;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTChart;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTTitle;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTTx;
import org.openxmlformats.schemas.drawingml.x2006.main.CTRegularTextRun;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextBody;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextParagraph;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDocument1;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHMerge;
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
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTabJc;

import com.itextpdf.text.PageSize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.java.cadastros.CadastroAviso;
import main.java.cadastros.CadastroBaseArquivos;
import main.java.cadastros.CadastroBaseDados;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroContrato.CadastroPagamento;
import main.java.cadastros.CadastroContrato.CadastroPagamentoContratual;
import main.java.cadastros.CadastroContrato.CadastroTransferenciaCarga;
import main.java.cadastros.CadastroContrato.CadastroTransferenciaPagamentoContratual;
import main.java.cadastros.CadastroContrato.Carregamento;
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
import main.java.cadastros.InstituicaoBancaria;
import main.java.cadastros.NFCompleto;
import main.java.cadastros.PagamentoCompleto;
import main.java.cadastros.RecebimentoCompleto;
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
import main.java.conexaoBanco.GerenciarBancoTransferenciaRecebimento;
import main.java.conexaoBanco.GerenciarBancoTransferencias;
import main.java.conexaoBanco.GerenciarBancoTransferenciasCarga;
import main.java.conexoes.TesteConexao;
import main.java.graficos.GraficoLinha;
import main.java.graficos.JPanelGrafico;
import main.java.graficos.JPanelGraficoCarregamento;
import main.java.gui.TelaCadastroCliente;
import main.java.gui.TelaLogin;
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
import main.java.views_personalizadas.TelaEmEsperaRelatoria;
import main.java.views_personalizadas.TelaNotificacao;
import main.java.views_personalizadas.TelaNotificacaoSuperior;
import main.java.views_personalizadas.TelaNotificacaoSuperiorModoBusca;
import outros.ValidaCNPj;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroNuvem;
import main.java.cadastros.CadastroZapMessenger;
import main.java.cadastros.CarregamentoCompleto;
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

public class RelatorioContratos {

	private CadastroModelo modelo;
	private String path;
	private boolean pagina_pagamentos_unidos_criada = false;
	private TelaEmEspera telaInformacoes;
	private boolean recebimentos_unidos_como_comprador = false;
	private boolean carregamentos_unidos_como_comprador = false;
	private boolean pagamentos_unidos_como_comprador = false;
	private boolean recebimentos_unidos_como_vendedor = false;
	private boolean carregamentos_unidos_como_vendedor = false;
	private boolean pagamentos_unidos_como_vendedor = false;
	private boolean incluir_transferencias_carregamentos = false;
	private boolean incluir_transferencias_recebimentos = false;
	private boolean titulo_pagamentos_criado = false;
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private String servidor_unidade;
	private XWPFDocument document_global;
	private XWPFParagraph paragrafo_atual;
	private ArrayList<CadastroCliente> clientes_globais;
	private int id_safra;

	private boolean contrato, contrato_como_comprador;
	private boolean pagamento = false, pagamento_como_comprador = false, pagamento_como_vendedor = false;
	private boolean carregamento = false, carregamento_como_comprador = false, carregamento_como_vendedor = false;
	private boolean sub_contratos = false;
	private boolean incluir_comissao = false;
	private boolean incluir_ganhos_potencias = false;
	private CadastroGrupo grupo_alvo_global;
	private int tipo_contrato = -1;
	private boolean somar_sub_contratos = false;
	private boolean contrato_irmao = false;
	private boolean unir_contratos = false;
	private boolean controle_nf_venda_recebimentos = false;
	private boolean recebimento = false, recebimento_como_comprador = false, recebimento_como_vendedor = false,
			unir_recebimentos = false;
	private boolean tabela_contratos_unidos_criada = false;
	private boolean unir_carregamentos = false;
	private boolean controle_nf_venda_carregamentos = false;
	private boolean incluir_sem_carregamentos = false;
	private boolean incluir_sem_pagamentos = false;
	private boolean incluir_sem_recebimentos = false;
	private boolean incluir_transferencias_pagamentos;
	private boolean unir_pagamentos = false;
	private TelaEmEsperaRelatoria telaEmEsperaRelatoria;
	private boolean incluir_comissao_pagamento = false;
	private CadastroCliente contra_parte_global;
	private CadastroCliente cliente_alvo2_global;
	private int participacao_global = -1;

	private double total_pagamentos = 0;
	private double total_recebidos_pagamentos = 0;
	private double somatoria_total_pagamentos = 0;
	private double somatoria_total_transferencias_negativas = 0;
	private double somatoria_total_transferencias_positivas = 0;

	private double total_cobertura = 0;
	private double total_recebidos_cobertura = 0;
	private double somatoria_cobertura_pagamentos = 0;
	private double somatoria_cobertura_transferencias_negativas = 0;
	private double somatoria_cobertura_transferencias_positivas = 0;
	private int id_local_retirada_global = -1;

	public boolean isContrato_irmao() {
		return contrato_irmao;
	}

	public void setContrato_irmao(boolean contrato_irmao) {
		this.contrato_irmao = contrato_irmao;
	}

	public RelatorioContratos(int _tipo_contrato, boolean _contrato, boolean _unir_contratos,
			boolean _contrato_como_comprador, boolean _pagamento, boolean _pagamento_como_depositante,
			boolean _pagamento_como_favorecido, boolean _incluir_sem_pagamentos,
			boolean _incluir_transferencias_pagamentos, boolean _unir_pagamentos, boolean _incluir_comissao_pagamento,
			boolean _carregamento, boolean _carregamento_como_comprador, boolean _carregamento_como_vendedor,
			boolean _unir_carregamentos, boolean _controle_nf_venda_carregamentos,
			boolean _incluir_transferencias_carregamentos, boolean _incluir_sem_carregamentos, boolean _recebimento,
			boolean _recebimento_como_comprador, boolean _recebimento_como_vendedor, boolean _unir_recebimentos,
			boolean _incluir_transferencias_recebimentos, boolean _controle_nf_venda_recebimentos,
			boolean _incluir_sem_recebimentos, int _id_safra, boolean _sub_contratos, boolean _incluir_comissao,
			boolean _incluir_ganhos_potenciais, boolean _somar_sub_contratos,
			ArrayList<CadastroCliente> _clientes_globais, CadastroCliente contra_parte,
			CadastroCliente cliente_alvo2_relatorio, CadastroGrupo _grupo_alvo, int _participacao,
			int _local_retirada) {

		getDadosGlobais();
		this.id_local_retirada_global = _local_retirada;
		this.participacao_global = _participacao;
		this.modelo = modelo;
		this.contra_parte_global = contra_parte;
		this.cliente_alvo2_global = cliente_alvo2_relatorio;
		servidor_unidade = configs_globais.getServidorUnidade();
		this.id_safra = _id_safra;
		this.contrato_como_comprador = _contrato_como_comprador;
		this.contrato = _contrato;
		this.pagamento = _pagamento;
		this.pagamento_como_comprador = _pagamento_como_depositante;
		this.pagamento_como_vendedor = _pagamento_como_favorecido;
		this.carregamento = _carregamento;
		this.carregamento_como_comprador = _carregamento_como_comprador;
		this.carregamento_como_vendedor = _carregamento_como_vendedor;

		this.recebimento = _recebimento;
		this.recebimento_como_comprador = _recebimento_como_comprador;
		this.recebimento_como_vendedor = _recebimento_como_vendedor;
		this.incluir_transferencias_recebimentos = _incluir_transferencias_recebimentos;
		this.unir_recebimentos = _unir_recebimentos;
		this.sub_contratos = _sub_contratos;
		this.incluir_comissao = _incluir_comissao;
		this.incluir_ganhos_potencias = _incluir_ganhos_potenciais;
		this.clientes_globais = _clientes_globais;
		this.grupo_alvo_global = _grupo_alvo;
		this.tipo_contrato = _tipo_contrato;
		this.somar_sub_contratos = _somar_sub_contratos;
		this.controle_nf_venda_recebimentos = _controle_nf_venda_recebimentos;
		this.controle_nf_venda_carregamentos = _controle_nf_venda_carregamentos;
		this.unir_carregamentos = _unir_carregamentos;
		this.incluir_transferencias_carregamentos = _incluir_transferencias_carregamentos;
		this.incluir_sem_carregamentos = _incluir_sem_carregamentos;
		this.incluir_sem_pagamentos = _incluir_sem_pagamentos;
		this.incluir_sem_recebimentos = _incluir_sem_recebimentos;
		this.unir_contratos = _unir_contratos;
		this.incluir_transferencias_pagamentos = _incluir_transferencias_pagamentos;
		this.unir_pagamentos = _unir_pagamentos;
		this.incluir_comissao_pagamento = _incluir_comissao_pagamento;
		criarDocumento();

	}

	public void criarDocumento() {
		document_global = new XWPFDocument();

		CTSectPr sectPr = document_global.getDocument().getBody().addNewSectPr();
		CTPageMar pageMar = sectPr.addNewPgMar();
		pageMar.setLeft(BigInteger.valueOf(720L));
		pageMar.setTop(BigInteger.valueOf(1440L));
		pageMar.setRight(BigInteger.valueOf(720L));
		pageMar.setBottom(BigInteger.valueOf(1440L));

		CTDocument1 document = document_global.getDocument();
		CTBody body = document.getBody();

		if (!body.isSetSectPr()) {
			body.addNewSectPr();
		}
		CTSectPr section = body.getSectPr();

		if (!section.isSetPgSz()) {
			section.addNewPgSz();
		}
		CTPageSz pageSize = section.getPgSz();

		pageSize.setOrient(STPageOrientation.LANDSCAPE);
		pageSize.setW(BigInteger.valueOf(15840));
		pageSize.setH(BigInteger.valueOf(12240));
		document_global.createStyles();

	}

	public HSSFWorkbook prepararExcel() {
		HSSFWorkbook workbook = new HSSFWorkbook();
		criarEsquemaEstilo(workbook);
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
		String criador = "Relatório de Contratos" + " por " + login.getNome() + " " + login.getSobrenome() + " em "
				+ data_criacao + " ás " + data.getHora();

		HSSFSheet sheet = workbook.createSheet("Contratos");

		// Definindo alguns padroes de layout
		sheet.setDefaultColumnWidth(25);
		sheet.setDefaultRowHeight((short) 400);

		row = sheet.createRow(rownum);
		cell = row.createCell(cellnum);
		cell.setCellStyle(celula_fundo_branco_texto_preto_a_esquerda);
		cell.setCellValue(criador);

		sheet.addMergedRegion(new CellRangeAddress(0, 0, cellnum, 6));

		// filtros da pesquisa
		rownum += 2;
		cellnum = 0;

		row = sheet.createRow(rownum);
		cell = row.createCell(cellnum);
		cell.setCellStyle(celula_fundo_branco_texto_preto_a_esquerda);
		cell.setCellValue(filtrosDaPesquisa());

		sheet.addMergedRegion(new CellRangeAddress(rownum, rownum += 8, cellnum, 6));
		// 2
		ArrayList<CadastroContrato> lista_global = retornarListaGlobalContratos();

		int num_total_contratos = lista_global.size();
		ArrayList<CadastroCliente> clientes_pesquisa = new ArrayList<>();
		if (this.grupo_alvo_global != null) {
			clientes_pesquisa = this.grupo_alvo_global.getClientes();
		} else {
			clientes_pesquisa = this.clientes_globais;
			this.grupo_alvo_global = new CadastroGrupo();
			this.grupo_alvo_global.setClientes(clientes_pesquisa);
			this.grupo_alvo_global.setNome_grupo("");
		}

		String text = "";
		text = text + "Grupo: " + grupo_alvo_global.getNome_grupo().toUpperCase();

		// adicionar integrantes
		text = text + "\nIntegrantes: ";
		for (CadastroCliente cliente : grupo_alvo_global.getClientes()) {
			if (cliente.getId() == 0) {
				text = text + "TODOS";

			} else {
				if (cliente.getTipo_pessoa() == 0) {
					text = text + cliente.getNome_empresarial().toUpperCase();
				} else {
					text = text + cliente.getNome_fantaia().toUpperCase();

				}
				text = text + " , ";
			}
		}

		// adicionar logo
		try {

			URL url = getClass().getResource("/imagens/logo_para_relatorio.png");
			String imgFile = url.getFile();
			InputStream inputStream = new FileInputStream(imgFile);

			byte[] imageBytes = IOUtils.toByteArray(inputStream);

			int pictureureIdx = workbook.addPicture(imageBytes, Workbook.PICTURE_TYPE_PNG);

			inputStream.close();

			CreationHelper helper = workbook.getCreationHelper();

			Drawing drawing = sheet.createDrawingPatriarch();

			ClientAnchor anchor = helper.createClientAnchor();

			anchor.setRow1(0);
			anchor.setRow2(3);
			anchor.setCol1(7);
			anchor.setCol2(9);

			drawing.createPicture(anchor, pictureureIdx);

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			// JOptionPane.showMessageDialog(isto, "Erro ao anexar imagem no xlsx");
			e1.printStackTrace();
		}

		rownum++;
		cellnum = 0;

		row = sheet.createRow(rownum);
		cell = row.createCell(cellnum);
		cell.setCellStyle(celula_fundo_branco_texto_preto_a_esquerda);
		cell.setCellValue(text);
		sheet.addMergedRegion(new CellRangeAddress(rownum, rownum += 3, cellnum, 6));

		NumberFormat z = NumberFormat.getNumberInstance();

		Locale ptBr = new Locale("pt", "BR");

		rownum += 2;
		cellnum = 0;

		if (num_total_contratos > 0) {

			double quantidade_global_sacos = 0.0;
			double quantidade_global_recebidos_sacos = 0.0;
			BigDecimal valor_global_pagamentos = BigDecimal.ZERO;

			BigDecimal valor_global_recebidos_pagamentos = BigDecimal.ZERO;

			int numero_global_contratos = 0;
			ArrayList<RegistroLocal> clientes = new ArrayList<>();

			// Configurando Header

			int filtro_tabela_info_contrato = rownum += 1;

			row = sheet.createRow(rownum++);

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_verde_texto_branco);
			cell.setCellValue("INTEGRANTE");

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_verde_texto_branco);
			cell.setCellValue("INSCRIÇÃO ESTADUAL".toUpperCase());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_verde_texto_branco);
			cell.setCellValue("TOTAL DE CONTRATOS".toUpperCase());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_verde_texto_branco);
			cell.setCellValue("VOLUME TOTAL".toUpperCase());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_verde_texto_branco);
			cell.setCellValue("% VOLUME SOBRE O TOTAL".toUpperCase());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_verde_texto_branco);
			cell.setCellValue("VALOR TOTAL".toUpperCase());

			GerenciarBancoContratos procura_contratos_grupo = new GerenciarBancoContratos();
			lista_global = new ArrayList<>();

			for (CadastroCliente cliente : grupo_alvo_global.getClientes()) {

				String nome_cliente = "";
				if (cliente.getTipo_pessoa() == 0) {
					nome_cliente = cliente.getNome_empresarial().toUpperCase();
				} else {
					nome_cliente = cliente.getNome_fantaia().toUpperCase();
				}

				String ie = "";
				ie = cliente.getIe();
				// numero de contratos desde clinete
				ArrayList<CadastroContrato> lista_contratos_encontrados_do_cliente = new ArrayList<>();
				ArrayList<CadastroContrato> lista_local_do_cliente = new ArrayList<>();
				ArrayList<CadastroContrato> lista_sub_contratos_encontrados_do_cliente = new ArrayList<>();

				if (contrato_como_comprador) {

					lista_contratos_encontrados_do_cliente = procura_contratos_grupo
							.getContratosPorClienteParaRelatorio(id_safra, cliente.getId(),
									cliente_alvo2_global.getId(), contra_parte_global.getId(), participacao_global,
									id_local_retirada_global);

					if (tipo_contrato != 1) {
						lista_sub_contratos_encontrados_do_cliente = procura_contratos_grupo
								.getSubContratosPorClienteParaRelatorio(id_safra, cliente.getId(),
										cliente_alvo2_global.getId(), contra_parte_global.getId(), participacao_global,
										id_local_retirada_global);

					}

				} else {

					lista_contratos_encontrados_do_cliente = procura_contratos_grupo
							.getContratosPorClienteParaRelatorio(id_safra, contra_parte_global.getId(),
									cliente_alvo2_global.getId(), cliente.getId(), participacao_global,
									id_local_retirada_global);
					if (tipo_contrato != 1) {
						lista_sub_contratos_encontrados_do_cliente = procura_contratos_grupo
								.getSubContratosPorClienteParaRelatorio(id_safra, contra_parte_global.getId(),
										cliente_alvo2_global.getId(), cliente.getId(), participacao_global,
										id_local_retirada_global);

					}

				}

				if (lista_contratos_encontrados_do_cliente.size() > 0) {
					for (CadastroContrato contrato_buscado : lista_contratos_encontrados_do_cliente) {

						// verifica se o contrato ja esta na lista global
						boolean ja_incluso = false;
						for (CadastroContrato contratos_na_lista_lista_global : lista_global) {

							if (contratos_na_lista_lista_global.getCodigo().equals(contrato_buscado.getCodigo())) {
								ja_incluso = true;
								break;
							}
						}

						// se nao esta na lista global, adiciona na lista global
						if (!ja_incluso) {
							lista_local_do_cliente.add(contrato_buscado);
						}
					}

				}

				if (lista_sub_contratos_encontrados_do_cliente.size() > 0) {
					for (CadastroContrato contrato_buscado : lista_sub_contratos_encontrados_do_cliente) {

						// verifica se o contrato ja esta na lista global
						boolean ja_incluso = false;
						for (CadastroContrato contratos_na_lista_lista_global : lista_global) {

							if (contratos_na_lista_lista_global.getCodigo().equals(contrato_buscado.getCodigo())) {
								ja_incluso = true;
								break;
							}
						}

						// se nao esta na lista global, adiciona na lista global
						if (!ja_incluso) {
							lista_local_do_cliente.add(contrato_buscado);
						}
					}

				}

				ArrayList<CadastroContrato> lista_final_do_cliente = new ArrayList<>();

				for (CadastroContrato contrato_lista_local : lista_local_do_cliente) {
					if (tipo_contrato == 1) {

						boolean ja_incluso = false;
						for (CadastroContrato contratos_na_lista_lista_final : lista_final_do_cliente) {

							if (contratos_na_lista_lista_final.getCodigo().equals(contrato_lista_local.getCodigo())) {
								ja_incluso = true;
								break;
							}
						}

						// se nao esta na lista global, adiciona na lista global
						if (!ja_incluso) {
							lista_final_do_cliente.add(contrato_lista_local);

						}

					} else {
						// relatorio externo ao comprador
						this.incluir_comissao = false;
						this.somar_sub_contratos = false;

						if (lista_local_do_cliente.size() > 0) {
							// verifica pelos subcontratos de cada contrato retornado

							ArrayList<CadastroContrato> sub_contratos = procura_contratos_grupo
									.getSubContratosParaRelatorio(contrato_lista_local.getId());
							telaEmEsperaRelatoria.setInfo("Lista de sub-contratos do alvo como comprador criada", 30);

							if (sub_contratos.size() > 0) {

								for (CadastroContrato sub : sub_contratos) {

									boolean ja_incluso = false;
									for (CadastroContrato contratos_na_lista_lista_final : lista_final_do_cliente) {

										if (contratos_na_lista_lista_final.getCodigo().equals(sub.getCodigo())) {
											ja_incluso = true;
											break;
										}
									}

									if (!ja_incluso) {

										boolean tem_id = false;

										for (CadastroCliente cliente_pesquisando : clientes_globais) {
											String s_id = Integer.toString(cliente_pesquisando.getId());
											if (sub.getIds_clientes_compradores().contains(s_id)) {
												tem_id = true;
												break;

											}

										}

										if (sub.getFilho() == 1) {
											if (tem_id) {
												lista_final_do_cliente.add(sub);

											}
										}
									}

								}

							} else {

								boolean ja_incluso = false;
								for (CadastroContrato contratos_na_lista_lista_final : lista_final_do_cliente) {

									if (contratos_na_lista_lista_final.getCodigo()
											.equals(contrato_lista_local.getCodigo())) {
										ja_incluso = true;
										break;
									}
								}

								if (!ja_incluso) {
									lista_final_do_cliente.add(contrato_lista_local);

								}

							}

						}

					}

				}

				ArrayList<CadastroContrato> lista_final_filtrada = new ArrayList<>();
				if (lista_final_do_cliente.size() > 0) {
					for (CadastroContrato contrato_buscado : lista_final_do_cliente) {

						// verifica se o contrato ja esta na lista global
						boolean ja_incluso = false;
						for (CadastroContrato contratos_na_lista_lista_global : lista_global) {

							if (contratos_na_lista_lista_global.getCodigo().equals(contrato_buscado.getCodigo())) {
								ja_incluso = true;
								break;
							}
						}

						// se nao esta na lista global, adiciona na lista global
						if (!ja_incluso) {
							lista_global.add(contrato_buscado);
							lista_final_filtrada.add(contrato_buscado);
						} else {
						}
					}

				}

				RegistroLocal reg = new RegistroLocal();
				// quantidade total de sacas
				int numero_total_contratos_do_cliente = 0;
				BigDecimal valor_total_pagamentos_do_cliente = BigDecimal.ZERO;
				BigDecimal valor_total_pagamentos_recebidos_do_cliente = BigDecimal.ZERO;

				double quantidade_total_sacos_do_cliente = 0.0;
				double quantidade_total_sacos_recebidos_do_cliente = 0.0;
				for (CadastroContrato contrato : lista_final_filtrada) {
					numero_total_contratos_do_cliente++;

					if (contrato.getMedida().equalsIgnoreCase("KG")) {
						quantidade_total_sacos_do_cliente += (contrato.getQuantidade() / 60);
					} else if (contrato.getMedida().equalsIgnoreCase("Sacos")) {
						quantidade_total_sacos_do_cliente += contrato.getQuantidade();
					}

					valor_total_pagamentos_do_cliente = valor_total_pagamentos_do_cliente
							.add(contrato.getValor_a_pagar());

					double valor_total_pagamentos = 0.0;
					double peso_total_cobertura = getPesoTotalRecebido(contrato) / 60;

					if (contrato.getMedida().equalsIgnoreCase("KG")) {
						valor_total_pagamentos = (peso_total_cobertura * 60) * contrato.getValor_produto();

					} else if (contrato.getMedida().equalsIgnoreCase("Sacos")) {
						valor_total_pagamentos = peso_total_cobertura * contrato.getValor_produto();
					}
					quantidade_total_sacos_recebidos_do_cliente += peso_total_cobertura;

					valor_total_pagamentos_recebidos_do_cliente = valor_total_pagamentos_recebidos_do_cliente
							.add(new BigDecimal(valor_total_pagamentos));

				}

				reg.setNome(nome_cliente);
				reg.setIe(ie);
				reg.setNum_contratos(numero_total_contratos_do_cliente);
				reg.setQuantidade_total(quantidade_total_sacos_do_cliente);
				reg.setValor_total(valor_total_pagamentos_do_cliente);

				numero_global_contratos += numero_total_contratos_do_cliente;

				quantidade_global_sacos += quantidade_total_sacos_do_cliente;
				quantidade_global_recebidos_sacos += quantidade_total_sacos_recebidos_do_cliente;

				valor_global_pagamentos = valor_global_pagamentos.add(valor_total_pagamentos_do_cliente);
				valor_global_recebidos_pagamentos = valor_global_recebidos_pagamentos
						.add(valor_total_pagamentos_recebidos_do_cliente);

				clientes.add(reg);
			}

			int ultima_linha = 19;

			for (RegistroLocal reg : clientes) {

				cellnum = 0;

				row = sheet.createRow(rownum);

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				cell.setCellValue(reg.getNome().toUpperCase());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				try {
					cell.setCellValue(reg.getIe().toUpperCase());
				} catch (Exception e) {
					cell.setCellValue("");
				}
				cell = row.createCell(cellnum++);
				cell.setCellStyle(pesoStyle);
				cell.setCellValue(reg.getNum_contratos());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(pesoStyle);
				cell.setCellValue(reg.getQuantidade_total());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(pesoStyle);
				cell.setCellValue(((100 * reg.getQuantidade_total() / quantidade_global_sacos)));

				cell = row.createCell(cellnum++);
				cell.setCellStyle(numberStyle);
				cell.setCellValue(reg.getValor_total().doubleValue());

				rownum++;
				ultima_linha = rownum;

			}

			sheet.setAutoFilter(CellRangeAddress
					.valueOf("A" + (filtro_tabela_info_contrato + 1) + ":F" + (filtro_tabela_info_contrato + 1)));
			for (int i = 0; i < 5; i++) {
				sheet.autoSizeColumn(i);

			}

			cellnum = 0;

			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
			row = sheet.createRow(rownum += 1);
			cellnum = 0;

			cell = row.createCell(2);
			cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
			cell.setCellType(CellType.FORMULA);
			String formula = "SUBTOTAL(9,C19:C" + (ultima_linha) + ")";
			cell.setCellFormula(formula);

			cell = row.createCell(3);
			cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
			cell.setCellType(CellType.FORMULA);
			formula = "SUBTOTAL(9,D19:D" + (ultima_linha) + ")";
			cell.setCellFormula(formula);

			cell = row.createCell(4);
			cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
			cell.setCellType(CellType.FORMULA);
			formula = "SUBTOTAL(9,E19:E" + (ultima_linha) + ")";
			cell.setCellFormula(formula);

			cell = row.createCell(5);
			cell.setCellStyle(valorStyleFundoVerdeTextoBranco);
			cell.setCellType(CellType.FORMULA);
			formula = "SUBTOTAL(9,F19:F" + (ultima_linha) + ")";
			cell.setCellFormula(formula);

		} // fim tabela info contratos

		// 2
		ArrayList<CadastroContrato> lista_final = retornarListaFinal(lista_global);

		rownum++;

		if (lista_final.size() > 0) {

			DadosTabelaExcel retornado = criarTabelaContratoExcel(workbook, sheet, rownum, lista_final);
			workbook = retornado.getWorkbook();
			sheet = retornado.getSheet();
			rownum = retornado.getRownum();

		}

		String nome_cliente = "";

		// carregamentos
		GerenciarBancoContratos gerenciar_carregamentos = new GerenciarBancoContratos();

		// transferencias
		GerenciarBancoTransferenciasCarga gerenciar_transferencias_carga = new GerenciarBancoTransferenciasCarga();

		// recebimentos
		GerenciarBancoContratos gerenciar_recebimentos = new GerenciarBancoContratos();

		// obter contratos desde cliente
		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();

		telaEmEsperaRelatoria.setInfo("Tabela de Contratos Criada", 50);

		if (recebimento_como_comprador) {
			// criar nova planilha de recebimentos
			sheet = workbook.createSheet("Recebimentos");
			rownum = 0;
			int contador = 1;

			for (CadastroContrato contrato_cliente : lista_final) {
				if (contrato_cliente.getSub_contrato() != 8 && contrato_cliente.getSub_contrato() != 9) {
					if (recebimento && recebimento_como_comprador && !unir_recebimentos) {
						// if (contrato_cliente.getSub_contrato() == 0 ||
						// contrato_cliente.getSub_contrato() == 4
						// || contrato_cliente.getSub_contrato() == 3) {

						ArrayList<RecebimentoCompleto> recebimentos_totais = new ArrayList<>();

						ArrayList<RecebimentoCompleto> recebimentos_locais = gerenciar_recebimentos
								.getRecebimentosParaRelatorio(contrato_cliente.getId());

						recebimentos_totais.addAll(recebimentos_locais);

						double local_quantidade_kgs = 0;
						System.out.println("id do ctr: " + contrato_cliente.getId());

						try {
							if (contrato_cliente.getMedida().equalsIgnoreCase("KG")) {
								local_quantidade_kgs = contrato_cliente.getQuantidade();
							} else if (contrato_cliente.getMedida().equalsIgnoreCase("Sacos")) {
								local_quantidade_kgs = contrato_cliente.getQuantidade() * 60;
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

						if (recebimentos_totais.size() > 0) {

							DadosTabelaExcel dados = criarTabelaRecebimentosExcel(workbook, sheet, rownum,
									recebimentos_totais, contrato_cliente);
							sheet = dados.getSheet();
							rownum = dados.getRownum();
							workbook = dados.getWorkbook();
							telaEmEsperaRelatoria.setInfo("Tabela de Recebimentos Individual Criada -> " + contador
									+ "/" + lista_final.size(), 25);
							contador++;
							// controle de nfs
							// inserirControleNFVendaEntrada(recebimentos_totais);
						} else {
							if (incluir_sem_recebimentos) {
								DadosTabelaExcel dados = semRecebimentosExcel(workbook, sheet, rownum,
										contrato_cliente);
								sheet = dados.getSheet();
								rownum = dados.getRownum();
								workbook = dados.getWorkbook();
								contador++;
							}
						}
						// } // fim do if de contrato_original
					} else {
						if (unir_recebimentos) {
							if (!recebimentos_unidos_como_comprador) {

								double soma_total_quantidade_contratos_kgs = 0;
								ArrayList<RecebimentoCompleto> recebimentos_totais = new ArrayList<>();
								GerenciarBancoTransferenciaRecebimento gerenciar_transferencias = new GerenciarBancoTransferenciaRecebimento();

								ArrayList<String> texto_contratos_sem_recebimentos = new ArrayList<>();

								for (CadastroContrato contrato : lista_final) {
									{

										ArrayList<RecebimentoCompleto> recebimentos_locais = gerenciar_recebimentos
												.getRecebimentosParaRelatorio(contrato.getId());
										recebimentos_totais.addAll(recebimentos_locais);

										double local_quantidade_kgs = 0;
										System.out.println("id do ctr: " + contrato.getId());
										try {
											if (contrato.getMedida().equalsIgnoreCase("KG")) {
												local_quantidade_kgs = contrato.getQuantidade();
											} else if (contrato.getMedida().equalsIgnoreCase("Sacos")) {
												local_quantidade_kgs = contrato.getQuantidade() * 60;
											}
										} catch (Exception e) {
											e.printStackTrace();
										}

										soma_total_quantidade_contratos_kgs += local_quantidade_kgs;

										if (incluir_transferencias_recebimentos) {

											ArrayList<CadastroContrato.CadastroTransferenciaRecebimento> lista_transferencias_recebimento_remetente_local = gerenciar_transferencias
													.getTransferenciasRemetente(contrato.getId());

											ArrayList<CadastroContrato.CadastroTransferenciaRecebimento> lista_transferencias_recebimento_destinatario_local = gerenciar_transferencias
													.getTransferenciaDestinatario(contrato.getId());

											for (CadastroContrato.CadastroTransferenciaRecebimento enviado_via_trans : lista_transferencias_recebimento_remetente_local) {

												RecebimentoCompleto recebimento = new RecebimentoCompleto();
												recebimento.setCodigo_romaneio(
														enviado_via_trans.getId_contrato_destinatario() + "");
												recebimento.setData_recebimento(enviado_via_trans.getData());
												recebimento.setPeso_romaneio(enviado_via_trans.getQuantidade());
												recebimento.setCodigo_romaneio("-Transferencia");
												recebimento.setContrato(contrato);

												recebimento.setNf_remessa_aplicavel(0);
												recebimento.setNf_venda_aplicavel(0);

												recebimentos_totais.add(recebimento);
											}

											for (CadastroContrato.CadastroTransferenciaRecebimento recebido_via_trans : lista_transferencias_recebimento_destinatario_local) {

												RecebimentoCompleto recebimento = new RecebimentoCompleto();
												recebimento.setCodigo_romaneio(
														recebido_via_trans.getId_contrato_destinatario() + "");
												recebimento.setData_recebimento(recebido_via_trans.getData());
												recebimento.setPeso_romaneio(recebido_via_trans.getQuantidade());
												recebimento.setCodigo_romaneio("+Transferencia");

												recebimento.setContrato(contrato);

												recebimento.setCodigo_nf_venda(contrato.getCodigo());
												recebimento.setNf_remessa_aplicavel(0);
												recebimento.setNf_venda_aplicavel(0);

												recebimentos_totais.add(recebimento);

											}

											if (recebimentos_locais.size() <= 0
													&& lista_transferencias_recebimento_remetente_local.size() <= 0
													&& lista_transferencias_recebimento_destinatario_local
															.size() <= 0) {
												String texto = infoContrato(contrato) + " -- SEM RECEBIMENTOS --";
												texto_contratos_sem_recebimentos.add(texto);
												texto = contrato.getNomes_compradores() + " X "
														+ contrato.getNomes_vendedores();
												texto_contratos_sem_recebimentos.add(texto);

											}

										} else {
											if (recebimentos_locais.size() <= 0) {
												String texto = infoContrato(contrato);
												texto_contratos_sem_recebimentos.add(texto);
											}
										}

									}
								}
								if (recebimentos_totais.size() > 0) {

									DadosTabelaExcel dados = criarTabelaRecebimentosUnidosExcel(workbook, sheet, rownum,
											recebimentos_totais, soma_total_quantidade_contratos_kgs);
									sheet = dados.getSheet();
									rownum = dados.getRownum();
									workbook = dados.getWorkbook();

								}

								// criar linhas com sem recebimentos
								if (texto_contratos_sem_recebimentos.size() > 0) {

									for (String texto : texto_contratos_sem_recebimentos) {
										DadosTabelaExcel dados = semRecebimentosExcelSemEspaco(workbook, sheet, rownum,
												texto);
										sheet = dados.getSheet();
										rownum = dados.getRownum();
										workbook = dados.getWorkbook();
									}
								}

								recebimentos_unidos_como_comprador = true;
							} // fim da verifificacao por contrato original
						}

					} // fim de recebimentos
				} // fim verificacao por linha de ganho potencial
			}
		}

		if (carregamento_como_comprador) {
			sheet = workbook.createSheet("Carregamentos");
			setarEstiloCarregamento(workbook);
			rownum = 0;
			int contador = 0;
			for (CadastroContrato contrato_cliente : lista_final) {
				if (contrato_cliente.getSub_contrato() != 8 && contrato_cliente.getSub_contrato() != 9) {

					if (carregamento && carregamento_como_comprador && !unir_carregamentos) {

						ArrayList<CarregamentoCompleto> carregamentos = gerenciar_carregamentos
								.getCarregamentoParaRelatorio(contrato_cliente.getId());
						ArrayList<CadastroContrato.CadastroTransferenciaCarga> transferencias_remetente_local = gerenciar_transferencias_carga
								.getTransferenciasRemetente(contrato_cliente.getId());

						ArrayList<CadastroContrato.CadastroTransferenciaCarga> transferencias_destinatario_local = gerenciar_transferencias_carga
								.getTransferenciaDestinatario(contrato_cliente.getId());

						if (carregamentos.size() > 0 || transferencias_remetente_local.size() > 0
								|| transferencias_destinatario_local.size() > 0) {

							double quantidade_kgs_recebidos = gerenciar.getQuantidadeRecebida(contrato_cliente.getId());

							DadosTabelaExcel dados = criarTabelaCarregamentosExcel(workbook, sheet, rownum,
									contrato_cliente, carregamentos, contrato_cliente.getQuantidade(),
									quantidade_kgs_recebidos, transferencias_remetente_local,
									transferencias_destinatario_local);
							sheet = dados.getSheet();
							rownum = dados.getRownum();
							workbook = dados.getWorkbook();
							telaEmEsperaRelatoria.setInfo("Tabela de Carregamentos Individual Criada -> " + contador
									+ "/" + lista_final.size(), 25);
							contador++;

						} else {
							// sem carregamentos

						}
					} else if (carregamento && carregamento_como_comprador && unir_carregamentos) {
						if (unir_carregamentos) {
							if (!carregamentos_unidos_como_comprador) {

								double soma_total_quantidade_contratos_kgs = 0;
								double soma_total_quantidade_recebida_kgs = 0;
								ArrayList<CarregamentoCompleto> carregamentos_totais = new ArrayList<>();
								ArrayList<CadastroTransferenciaCarga> transferencias_remetente_totais = new ArrayList<>();
								ArrayList<CadastroTransferenciaCarga> transferencias_destinatario_totais = new ArrayList<>();
								double quantidade__total_kgs_recebidos = 0;

								for (CadastroContrato contrato : lista_final) {
									ArrayList<CarregamentoCompleto> carregamentos_locais = gerenciar_carregamentos
											.getCarregamentoParaRelatorio(contrato.getId());
									carregamentos_totais.addAll(carregamentos_locais);
									ArrayList<CadastroContrato.CadastroTransferenciaCarga> transferencias_remetente_local = gerenciar_transferencias_carga
											.getTransferenciasRemetente(contrato.getId());
									transferencias_remetente_totais.addAll(transferencias_remetente_local);

									ArrayList<CadastroContrato.CadastroTransferenciaCarga> transferencias_destinatario_local = gerenciar_transferencias_carga
											.getTransferenciaDestinatario(contrato.getId());
									transferencias_destinatario_totais.addAll(transferencias_destinatario_local);

									double quantidade_kgs_recebidos = getPesoTotalRecebido(contrato);

									double local_quantidade_kgs = 0;

									if (contrato.getMedida().equalsIgnoreCase("KG")) {
										local_quantidade_kgs = contrato.getQuantidade();
									} else if (contrato.getMedida().equalsIgnoreCase("Sacos")) {
										local_quantidade_kgs = contrato.getQuantidade() * 60;
									}

									soma_total_quantidade_contratos_kgs += local_quantidade_kgs;
									quantidade__total_kgs_recebidos += quantidade_kgs_recebidos;
								}

								// faz a soma das quantidades dos contratos

								if (carregamentos_totais.size() > 0 || transferencias_remetente_totais.size() > 0
										|| transferencias_destinatario_totais.size() > 0) {

									DadosTabelaExcel dados = criarTabelaCarregamentosUnidosExcel(workbook, sheet,
											rownum, carregamentos_totais, soma_total_quantidade_contratos_kgs,
											quantidade__total_kgs_recebidos, transferencias_remetente_totais,
											transferencias_destinatario_totais);
									sheet = dados.getSheet();
									rownum = dados.getRownum();
									workbook = dados.getWorkbook();
									telaEmEsperaRelatoria.setInfo("Tabela de Carregamentos Unidos Criada", 40);
								}
								carregamentos_unidos_como_comprador = true;
							}
						}
					}

				}
			}
		}

		if (pagamento_como_comprador) {
			sheet = workbook.createSheet("Pagamentos");
			rownum = 0;
			int contador = 0;
			for (CadastroContrato contrato_cliente : lista_final) {
				if (contrato_cliente.getSub_contrato() != 8 && contrato_cliente.getSub_contrato() != 9) {

					// pagamentos
					if (pagamento && pagamento_como_comprador && !unir_pagamentos) {

						ArrayList<PagamentoCompleto> lista_pagamentos = gerenciar
								.getPagamentosContratuaisParaRelatorio(contrato_cliente.getId());

						if (lista_pagamentos.size() > 0) {

							// criarTabelaPagamentos(lista_pagamentos, contrato_cliente);

							DadosTabelaExcel dados = criarTabelaPagamentosExcel(workbook, sheet, rownum,
									lista_pagamentos, contrato_cliente);
							sheet = dados.getSheet();
							rownum = dados.getRownum();
							workbook = dados.getWorkbook();

							rownum += 2;
							contador++;
							telaEmEsperaRelatoria.setInfo(
									"Tabela de Pagamento Individual Criada - " + contador + "/" + lista_final.size(),
									50);

						}

					} else if (pagamento && pagamento_como_comprador && unir_pagamentos) {
						if (unir_pagamentos) {
							DadosTabelaExcel dados = criarTabelaPagamentosUnidosExcel(workbook, sheet, rownum,
									lista_final);
							sheet = dados.getSheet();
							rownum = dados.getRownum();
							workbook = dados.getWorkbook();

							telaEmEsperaRelatoria.setInfo("Tabela de Pagamentos Criada", 25);
							break;

						}
					}
				}
			}
		}

		return workbook;

	}

	public String filtrosDaPesquisa() {
		String texto_pesquisa = "Busca por: \n";

		if (id_safra == 0) {
			texto_pesquisa = texto_pesquisa + "Safra: -TODAS AS SAFRAS- ";

		} else {
			GerenciarBancoSafras gerenciar = new GerenciarBancoSafras();
			CadastroSafra safra = gerenciar.getSafra(id_safra);
			String text_safra = safra.getProduto().getNome_produto() + " " + safra.getProduto().getTransgenia() + " "
					+ safra.getAno_plantio() + "/" + safra.getAno_colheita();
			texto_pesquisa = texto_pesquisa + "Safra: " + text_safra;

		}

		if (contrato) {
			texto_pesquisa = texto_pesquisa + "*Contratos";
			if (contrato_como_comprador) {
				texto_pesquisa = texto_pesquisa + " como Comprador\n";

			} else {
				texto_pesquisa = texto_pesquisa + " como Vendedor\n";

			}
			// parametros
			texto_pesquisa = texto_pesquisa + "   ->Filtros: ";

			if (unir_contratos)
				texto_pesquisa = texto_pesquisa + " |Unir os Contratos| ";
			if (sub_contratos)
				texto_pesquisa = texto_pesquisa + " |Incluir Sub-Contratos na Tabela| ";
			if (incluir_comissao)
				texto_pesquisa = texto_pesquisa + " |Incluir Coluna de Comissão| ";
			if (incluir_ganhos_potencias)
				texto_pesquisa = texto_pesquisa + " |Incluir Linhas e Tabela de Ganho Potencial| ";

		}
		texto_pesquisa = texto_pesquisa + "\n";

		if (recebimento) {
			texto_pesquisa = texto_pesquisa + "*Recebimentos";
			if (recebimento_como_comprador && recebimento_como_vendedor) {
				texto_pesquisa = texto_pesquisa + " como Comprador e Vendedor";
			} else if (recebimento_como_comprador && !recebimento_como_vendedor) {
				texto_pesquisa = texto_pesquisa + " como Comprador";
			} else if (!recebimento_como_comprador && recebimento_como_vendedor) {
				texto_pesquisa = texto_pesquisa + " como Vendedor";

			} else {

			}

			// parametros
			texto_pesquisa = texto_pesquisa + "   ->Filtros: ";

			if (unir_recebimentos)
				texto_pesquisa = texto_pesquisa + " |Unir Recebimentos| ";
			if (incluir_sem_recebimentos)
				texto_pesquisa = texto_pesquisa + " |Incluir Contratos Sem Recebimentos| ";
			if (controle_nf_venda_recebimentos)
				texto_pesquisa = texto_pesquisa + " |Incluir Tabela Controle NF Venda(Entrada)| ";

		}
		texto_pesquisa = texto_pesquisa + "\n";

		if (carregamento) {
			texto_pesquisa = texto_pesquisa + "*Carregamentos";
			if (carregamento_como_comprador && carregamento_como_vendedor) {
				texto_pesquisa = texto_pesquisa + " como Comprador e Vendedor";
			} else if (carregamento_como_comprador && !carregamento_como_vendedor) {
				texto_pesquisa = texto_pesquisa + " como Comprador";
			} else if (!carregamento_como_comprador && carregamento_como_vendedor) {
				texto_pesquisa = texto_pesquisa + " como Vendedor";

			} else {

			}

			// parametros
			texto_pesquisa = texto_pesquisa + "   ->Filtros: ";

			if (unir_carregamentos)
				texto_pesquisa = texto_pesquisa + " |Unir Carregamentos| ";
			if (incluir_sem_carregamentos)
				texto_pesquisa = texto_pesquisa + " |Incluir Contratos Sem Carregamentos| ";
			if (controle_nf_venda_carregamentos)
				texto_pesquisa = texto_pesquisa + " |Incluir Tabela Controle NF Venda(Saída)| ";
			if (incluir_transferencias_carregamentos)
				texto_pesquisa = texto_pesquisa + " |Incluir Transferencias de Carregamento entre Contratos| ";

		}
		texto_pesquisa = texto_pesquisa + "\n";

		if (pagamento) {
			texto_pesquisa = texto_pesquisa + "*Pagamentos";
			if (pagamento_como_comprador && pagamento_como_vendedor) {
				texto_pesquisa = texto_pesquisa + " como Comprador e Vendedor";
			} else if (pagamento_como_comprador && !pagamento_como_vendedor) {
				texto_pesquisa = texto_pesquisa + " como Comprador";
			} else if (!pagamento_como_comprador && pagamento_como_vendedor) {
				texto_pesquisa = texto_pesquisa + " como Vendedor";

			} else {

			}
			texto_pesquisa = texto_pesquisa + "   ->Filtros: ";

			if (unir_pagamentos)
				texto_pesquisa = texto_pesquisa + " |Unir Pagamentos| ";
			if (incluir_sem_pagamentos)
				texto_pesquisa = texto_pesquisa + " |Incluir Contratos Sem Pagamentos| ";
			if (incluir_transferencias_pagamentos)
				texto_pesquisa = texto_pesquisa + " |Incluir Transferencias de Pagamentos entre Contratos| ";

		}
		return (texto_pesquisa + "\n");
	}

	public ArrayList<CadastroContrato> retornarListaGlobalContratos() {
		GerenciarBancoContratos procura_contratos_grupo = new GerenciarBancoContratos();
		// if (grupo_alvo_global != null && contrato)
		ArrayList<CadastroCliente> clientes_pesquisa = new ArrayList<>();

		if (grupo_alvo_global != null) {
			clientes_pesquisa = grupo_alvo_global.getClientes();
		} else {

			clientes_pesquisa = clientes_globais;
			grupo_alvo_global = new CadastroGrupo();
			grupo_alvo_global.setClientes(clientes_pesquisa);
			grupo_alvo_global.setNome_grupo("");
		}

		ArrayList<CadastroContrato> lista_global = new ArrayList<>();

		// quantidade total de sacos do grupo
		for (CadastroCliente cliente : clientes_pesquisa) {
			// numero de contratos desde clinete
			ArrayList<CadastroContrato> lista_contratos = new ArrayList<>();
			ArrayList<CadastroContrato> lista_sub_contratos_local = new ArrayList<>();

			if (contrato_como_comprador) {

				lista_contratos = procura_contratos_grupo.getContratosPorClienteParaRelatorio(id_safra, cliente.getId(),
						cliente_alvo2_global.getId(), contra_parte_global.getId(), participacao_global,
						id_local_retirada_global);
				// lista os subcontratos
				if (tipo_contrato != 1) {
					lista_sub_contratos_local = procura_contratos_grupo.getSubContratosPorClienteParaRelatorio(id_safra,
							cliente.getId(), cliente_alvo2_global.getId(), contra_parte_global.getId(),
							participacao_global, id_local_retirada_global);
				}

			} else {

				lista_contratos = procura_contratos_grupo.getContratosPorClienteParaRelatorio(id_safra,
						contra_parte_global.getId(), cliente_alvo2_global.getId(), cliente.getId(), participacao_global,
						id_local_retirada_global);

				if (tipo_contrato != 1) {
					lista_sub_contratos_local = procura_contratos_grupo.getSubContratosPorClienteParaRelatorio(id_safra,
							contra_parte_global.getId(), cliente_alvo2_global.getId(), cliente.getId(),
							participacao_global, id_local_retirada_global);

				}
			}

			if (lista_contratos.size() > 0) {
				for (CadastroContrato contrato_buscado : lista_contratos) {

					boolean ja_incluso = false;
					for (CadastroContrato contratos_na_lista_final : lista_global) {

						if (contratos_na_lista_final.getCodigo().equals(contrato_buscado.getCodigo())) {
							ja_incluso = true;
							break;
						}
					}

					if (!ja_incluso)
						lista_global.add(contrato_buscado);
				}

			}

			if (lista_sub_contratos_local.size() > 0) {
				for (CadastroContrato contrato_buscado : lista_sub_contratos_local) {

					boolean ja_incluso = false;
					for (CadastroContrato contratos_na_lista_final : lista_global) {

						if (contratos_na_lista_final.getCodigo().equals(contrato_buscado.getCodigo())) {
							ja_incluso = true;
							break;
						}
					}

					if (!ja_incluso)
						lista_global.add(contrato_buscado);
				}

			}

		}

		return lista_global;
	}

	public ArrayList<CadastroContrato> retornarListaFinal(ArrayList<CadastroContrato> lista_global) {

		ArrayList<CadastroContrato> lista_final = new ArrayList<>();
		GerenciarBancoContratos procura_contratos_grupo = new GerenciarBancoContratos();

		for (CadastroContrato contrato_lista_global : lista_global) {
			if (tipo_contrato == 1) {
				if (sub_contratos) {

					boolean ja_incluso = false;
					for (CadastroContrato contratos_na_lista_final : lista_final) {

						if (contratos_na_lista_final.getCodigo().equals(contrato_lista_global.getCodigo())) {
							ja_incluso = true;
							break;
						}
					}

					if (!ja_incluso) {

						lista_final.add(contrato_lista_global);
						telaEmEsperaRelatoria.setInfo("Lista de contratos do alvo como comprador criada", 30);

						// verifica pelos subcontratos de cada contrato retornado

						ArrayList<CadastroContrato> sub_contratos = procura_contratos_grupo
								.getSubContratosParaRelatorio(contrato_lista_global.getId());
						telaEmEsperaRelatoria.setInfo("Lista de sub-contratos do alvo como comprador criada", 30);

						BigDecimal somatoria_valor_sub_contrato = BigDecimal.ZERO;

						if (sub_contratos.size() > 0) {
							// existem subcontratos nesse contrato
							for (CadastroContrato sub : sub_contratos) {

								// verificar se esse contrato ja nao esta na lista

								ja_incluso = false;
								for (CadastroContrato contratos_na_lista_final : lista_final) {

									if (contratos_na_lista_final.getCodigo().equals(sub.getCodigo())) {
										ja_incluso = true;
										break;
									}
								}

								if (!ja_incluso) {
									somatoria_valor_sub_contrato = somatoria_valor_sub_contrato
											.add(sub.getValor_a_pagar());
									lista_final.add(sub);
								}

							}

							if (incluir_ganhos_potencias) {
								CadastroContrato linha_ganho_potencial = new CadastroContrato();
								linha_ganho_potencial.setValor_a_pagar(contrato_lista_global.getValor_a_pagar());
								linha_ganho_potencial.setCodigo(contrato_lista_global.getCodigo());
								linha_ganho_potencial.setValor_comissao(somatoria_valor_sub_contrato);
								linha_ganho_potencial.setSub_contrato(8);

								// seta o valor da comisao
								if (contrato_lista_global.getValor_comissao() != null) {

									linha_ganho_potencial
											.setValor_produto(contrato_lista_global.getValor_comissao().doubleValue());
								} else {
									linha_ganho_potencial.setValor_produto(0);
								}

								lista_final.add(linha_ganho_potencial);

							}

						} else {
							if (incluir_ganhos_potencias) {
								CadastroContrato linha_ganho_potencial = new CadastroContrato();
								linha_ganho_potencial.setValor_a_pagar(contrato_lista_global.getValor_a_pagar());
								linha_ganho_potencial.setCodigo(contrato_lista_global.getCodigo());
								linha_ganho_potencial.setValor_comissao(somatoria_valor_sub_contrato);
								linha_ganho_potencial.setSub_contrato(9);

								// seta o valor da comisao
								if (contrato_lista_global.getValor_comissao() != null) {

									linha_ganho_potencial
											.setValor_produto(contrato_lista_global.getValor_comissao().doubleValue());
								} else {
									linha_ganho_potencial.setValor_produto(0);
								}

								lista_final.add(linha_ganho_potencial);

							}
						}

					}

				} else {
					// relatorio externo sem subcontratos
					boolean ja_incluso = false;
					for (CadastroContrato contratos_na_lista_final : lista_final) {

						if (contratos_na_lista_final.getCodigo().equals(contrato_lista_global.getCodigo())) {
							ja_incluso = true;
							break;
						}
					}

					if (!ja_incluso) {

						lista_final.add(contrato_lista_global);
					}

				}
			} else {
				// relatorio externo
				this.incluir_comissao = false;
				this.somar_sub_contratos = false;

				if (lista_global.size() > 0) {
					// verifica pelos subcontratos de cada contrato retornado

					ArrayList<CadastroContrato> sub_contratos = procura_contratos_grupo
							.getSubContratosParaRelatorio(contrato_lista_global.getId());
					telaEmEsperaRelatoria.setInfo("Lista de sub-contratos do alvo como comprador criada", 30);

					BigDecimal somatoria_valor_sub_contrato = BigDecimal.ZERO;

					if (sub_contratos.size() > 0) {
						// existem subcontratos nesse contrato
						for (CadastroContrato sub : sub_contratos) {

							boolean ja_incluso = false;
							for (CadastroContrato contratos_na_lista_final : lista_final) {

								if (contratos_na_lista_final.getCodigo().equals(sub.getCodigo())) {
									ja_incluso = true;
									break;
								}
							}

							if (!ja_incluso) {

								boolean tem_id = false;

								for (CadastroCliente cliente_pesquisando : clientes_globais) {
									String s_id = Integer.toString(cliente_pesquisando.getId());
									if (sub.getIds_clientes_compradores().contains(s_id)) {
										// JOptionPane.showMessageDialog(null, sub.getIds_clientes_compradores() + "
										// contem o id " + s_id);
										tem_id = true;
										break;
									} else {
										// JOptionPane.showMessageDialog(null, sub.getIds_clientes_compradores() + " nao
										// contem o id " + s_id);

									}

								}

								if (sub.getFilho() == 1) {

									if (tem_id) {
										somatoria_valor_sub_contrato = somatoria_valor_sub_contrato
												.add(sub.getValor_a_pagar());

										lista_final.add(sub);
									}
								}

							}

						}

					} else {

						boolean ja_incluso = false;
						for (CadastroContrato contratos_na_lista_final : lista_final) {

							if (contratos_na_lista_final.getCodigo().equals(contrato_lista_global.getCodigo())) {
								ja_incluso = true;
								break;
							}
						}

						if (!ja_incluso) {

							somatoria_valor_sub_contrato = somatoria_valor_sub_contrato
									.add(contrato_lista_global.getValor_a_pagar());
							lista_final.add(contrato_lista_global);
						}

					}

				}
				// contrato externo
				// JOptionPane.showMessageDialog(null, "Relatorio Externo");
				// verifica se este contrato e um subcontrato
			}

		}

		return lista_final;
	}

	public String preparar() {

		Locale ptBr = new Locale("pt", "BR");

		NumberFormat z = NumberFormat.getNumberInstance();

		// cria o paragrafo do rodape
		XWPFParagraph rodape = document_global.createParagraph();
		rodape.setAlignment(ParagraphAlignment.LEFT);

		CTSectPr sectPr = document_global.getDocument().getBody().addNewSectPr();
		CTPageMar pageMar = sectPr.addNewPgMar();
		pageMar.setLeft(BigInteger.valueOf(100L));
		pageMar.setTop(BigInteger.valueOf(100L));
		pageMar.setRight(BigInteger.valueOf(100L));
		pageMar.setBottom(BigInteger.valueOf(100L));

		// criar o paragrafo do titulo
		XWPFParagraph title = document_global.createParagraph();
		title.setAlignment(ParagraphAlignment.LEFT);

		GetData data = new GetData();

		XWPFRun titleRun = title.createRun();
		String data_criacao = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

		titleRun.setText("Relatório de Contratos" + " por " + login.getNome() + " " + login.getSobrenome() + " em "
				+ data_criacao + " ás " + data.getHora());
		titleRun.setColor("000000");
		titleRun.setBold(false);
		titleRun.setUnderline(UnderlinePatterns.SINGLE);
		titleRun.setFontFamily("Arial");
		titleRun.setFontSize(10);

		XWPFParagraph filtros = document_global.createParagraph();
		filtros.setAlignment(ParagraphAlignment.LEFT);

		XWPFRun dadosPesquisaRun = filtros.createRun();

		dadosPesquisaRun.setText("Filtros da pesquisa: \n");
		dadosPesquisaRun.setColor("000000");
		dadosPesquisaRun.setBold(false);
		dadosPesquisaRun.setFontFamily("Arial");
		dadosPesquisaRun.setFontSize(10);
		substituirTexto(filtrosDaPesquisa() + "\n\n", -1);
		substituirTexto("", -1);

		GerenciarBancoContratos procura_contratos_grupo = new GerenciarBancoContratos();
		ArrayList<CadastroCliente> clientes_pesquisa = new ArrayList<>();
		if (this.grupo_alvo_global != null) {
			clientes_pesquisa = this.grupo_alvo_global.getClientes();
		} else {
			clientes_pesquisa = this.clientes_globais;
			this.grupo_alvo_global = new CadastroGrupo();
			this.grupo_alvo_global.setClientes(clientes_pesquisa);
			this.grupo_alvo_global.setNome_grupo("");
		}
		String text = "";
		text = text + "Grupo: " + grupo_alvo_global.getNome_grupo().toUpperCase();

		// adicionar integrantes
		text = text + "\nIntegrantes: ";
		for (CadastroCliente cliente : grupo_alvo_global.getClientes()) {
			if (cliente.getId() == 0) {
				text = text + "TODOS";

			} else {
				if (cliente.getTipo_pessoa() == 0) {
					text = text + cliente.getNome_empresarial().toUpperCase();
				} else {
					text = text + cliente.getNome_fantaia().toUpperCase();

				}
				text = text + " , ";
			}
		}

		substituirTexto(text, -1);

		substituirTexto("", -1);
		// 1
		ArrayList<CadastroContrato> lista_global = retornarListaGlobalContratos();

		int num_total_contratos = lista_global.size();

		if (num_total_contratos > 0) {
			criarTabelaInfoGrupo();
			substituirTexto("", -1);
		}

		// 1
		ArrayList<CadastroContrato> lista_final = retornarListaFinal(lista_global);

		if (lista_final.size() > 0) {

			XWPFParagraph titulo_contratos = document_global.createParagraph();
			titulo_contratos.setAlignment(ParagraphAlignment.CENTER);

			XWPFRun titulo_contratosRun = titulo_contratos.createRun();
			titulo_contratosRun.setText("\nContratos");
			titulo_contratosRun.setColor("000000");
			titulo_contratosRun.setBold(true);
			titulo_contratosRun.setFontFamily("Arial");
			titulo_contratosRun.setFontSize(10);

			criarTabelaContrato(lista_final);

		}

		String nome_cliente = "";
		/*
		 * if (cliente_alvo_global.getTipo_pessoa() == 0) { // pessoa fisica
		 * nome_cliente = cliente_alvo_global.getNome_empresarial(); } else {
		 * nome_cliente = cliente_alvo_global.getNome_fantaia(); }
		 */
		// carregamentos
		GerenciarBancoContratos gerenciar_carregamentos = new GerenciarBancoContratos();

		// transferencias
		GerenciarBancoTransferenciasCarga gerenciar_transferencias_carga = new GerenciarBancoTransferenciasCarga();

		// recebimentos
		GerenciarBancoContratos gerenciar_recebimentos = new GerenciarBancoContratos();

		// obter contratos desde cliente
		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();

		if (recebimento_como_comprador || carregamento_como_comprador || pagamento_como_comprador) {
			// contratos_deste_cliente = getContratos(cliente_alvo_global.getId(),true);
			int contador = 1;
			for (CadastroContrato contrato_cliente : lista_final) {

				if (!recebimentos_unidos_como_comprador && recebimento)
					adicionarTraco(true, 0);

				if (recebimento && recebimento_como_comprador && !unir_recebimentos) {
					// if (contrato_cliente.getSub_contrato() == 0 ||
					// contrato_cliente.getSub_contrato() == 4
					// || contrato_cliente.getSub_contrato() == 3) {

					ArrayList<RecebimentoCompleto> recebimentos_totais = new ArrayList<>();

					ArrayList<RecebimentoCompleto> recebimentos_locais = gerenciar_recebimentos
							.getRecebimentosParaRelatorio(contrato_cliente.getId());

					recebimentos_totais.addAll(recebimentos_locais);

					double local_quantidade_kgs = 0;

					if (contrato_cliente.getMedida().equalsIgnoreCase("KG")) {
						local_quantidade_kgs = contrato_cliente.getQuantidade();
					} else if (contrato_cliente.getMedida().equalsIgnoreCase("Sacos")) {
						local_quantidade_kgs = contrato_cliente.getQuantidade() * 60;
					}

					if (recebimentos_totais.size() > 0) {

						XWPFParagraph titulo_recebimentos = document_global.createParagraph();
						titulo_recebimentos.setAlignment(ParagraphAlignment.CENTER);

						XWPFRun titulo_recebimentosRun = titulo_recebimentos.createRun();
						titulo_recebimentosRun.setText("Recebimentos");
						titulo_recebimentosRun.setColor("000000");
						titulo_recebimentosRun.setBold(true);
						titulo_recebimentosRun.setFontFamily("Arial");
						titulo_recebimentosRun.setFontSize(9);

						criarTabelaRecebimentos(recebimentos_totais, contrato_cliente);
						telaEmEsperaRelatoria.setInfo(
								"Tabela de Recebimentos Individual Criada -> " + contador + "/" + lista_final.size(),
								25);

						// controle de nfs
						inserirControleNFVendaEntrada(recebimentos_totais);
					} else {
						if (incluir_sem_recebimentos) {
							semRecebimentos(contrato_cliente);
							contador++;
						}
					}
					// } // fim do if de contrato_original
				} else {
					if (unir_recebimentos) {
						if (!recebimentos_unidos_como_comprador) {

							String texto_contratos_sem_recebimentos = "CONTRATOS SEM RECEBIMENTOS:\n";
							double soma_total_quantidade_contratos_kgs = 0;
							ArrayList<RecebimentoCompleto> recebimentos_totais = new ArrayList<>();
							GerenciarBancoTransferenciaRecebimento gerenciar_transferencias = new GerenciarBancoTransferenciaRecebimento();

							for (CadastroContrato contrato : lista_final) {
								{

									ArrayList<RecebimentoCompleto> recebimentos_locais = gerenciar_recebimentos
											.getRecebimentosParaRelatorio(contrato.getId());
									recebimentos_totais.addAll(recebimentos_locais);

									if (recebimentos_locais.size() <= 0) {
										texto_contratos_sem_recebimentos = texto_contratos_sem_recebimentos
												+ infoContrato(contrato) + "\n";
									}

									double local_quantidade_kgs = 0;

									if (contrato.getMedida().equalsIgnoreCase("KG")) {
										local_quantidade_kgs = contrato.getQuantidade();
									} else if (contrato.getMedida().equalsIgnoreCase("Sacos")) {
										local_quantidade_kgs = contrato.getQuantidade() * 60;
									}
									soma_total_quantidade_contratos_kgs += local_quantidade_kgs;

									if (incluir_transferencias_recebimentos) {

										ArrayList<CadastroContrato.CadastroTransferenciaRecebimento> lista_transferencias_recebimento_remetente_local = gerenciar_transferencias
												.getTransferenciasRemetente(contrato.getId());

										ArrayList<CadastroContrato.CadastroTransferenciaRecebimento> lista_transferencias_recebimento_destinatario_local = gerenciar_transferencias
												.getTransferenciaDestinatario(contrato.getId());

										for (CadastroContrato.CadastroTransferenciaRecebimento enviado_via_trans : lista_transferencias_recebimento_remetente_local) {

											RecebimentoCompleto recebimento = new RecebimentoCompleto();
											recebimento.setCodigo_romaneio(
													enviado_via_trans.getId_contrato_destinatario() + "");
											recebimento.setData_recebimento(enviado_via_trans.getData());
											recebimento.setPeso_romaneio(enviado_via_trans.getQuantidade());
											recebimento.setCodigo_romaneio("-Transferencia");
											recebimento.setContrato(contrato);

											recebimento.setNf_remessa_aplicavel(0);
											recebimento.setNf_venda_aplicavel(0);

											recebimentos_totais.add(recebimento);
										}

										for (CadastroContrato.CadastroTransferenciaRecebimento recebido_via_trans : lista_transferencias_recebimento_destinatario_local) {

											RecebimentoCompleto recebimento = new RecebimentoCompleto();
											recebimento.setCodigo_romaneio(
													recebido_via_trans.getId_contrato_destinatario() + "");
											recebimento.setData_recebimento(recebido_via_trans.getData());
											recebimento.setPeso_romaneio(recebido_via_trans.getQuantidade());
											recebimento.setCodigo_romaneio("+Transferencia");

											recebimento.setContrato(contrato);

											recebimento.setCodigo_nf_venda(contrato.getCodigo());
											recebimento.setNf_remessa_aplicavel(0);
											recebimento.setNf_venda_aplicavel(0);

											recebimentos_totais.add(recebimento);

										}
									}

								}
							}
							if (recebimentos_totais.size() > 0) {

								adicionarTraco(true, 0);

								XWPFParagraph titulo_recebimentos = document_global.createParagraph();
								titulo_recebimentos.setAlignment(ParagraphAlignment.CENTER);

								XWPFRun titulo_recebimentosRun = titulo_recebimentos.createRun();
								titulo_recebimentosRun.setText("Recebimentos");
								titulo_recebimentosRun.setColor("000000");
								titulo_recebimentosRun.setBold(true);
								titulo_recebimentosRun.setFontFamily("Arial");
								titulo_recebimentosRun.setFontSize(9);

								criarTabelaRecebimentosUnidos(recebimentos_totais, soma_total_quantidade_contratos_kgs);
								if (incluir_sem_recebimentos) {

									substituirTexto(texto_contratos_sem_recebimentos);

								}
								telaEmEsperaRelatoria.setInfo("Tabela de Recebimentos Unidos Criada", 25);

								// controle de nfs
								inserirControleNFVendaEntrada(recebimentos_totais);
							}
							recebimentos_unidos_como_comprador = true;
						} // fim da verifificacao por contrato original
					}

				}

				if (carregamento && carregamento_como_comprador && !unir_carregamentos) {

					ArrayList<CarregamentoCompleto> carregamentos = gerenciar_carregamentos
							.getCarregamentoParaRelatorio(contrato_cliente.getId());
					ArrayList<CadastroContrato.CadastroTransferenciaCarga> transferencias_remetente_local = gerenciar_transferencias_carga
							.getTransferenciasRemetente(contrato_cliente.getId());

					ArrayList<CadastroContrato.CadastroTransferenciaCarga> transferencias_destinatario_local = gerenciar_transferencias_carga
							.getTransferenciaDestinatario(contrato_cliente.getId());

					if (carregamentos.size() > 0 || transferencias_remetente_local.size() > 0
							|| transferencias_destinatario_local.size() > 0) {

						XWPFParagraph titulo_recebimentos = document_global.createParagraph();
						titulo_recebimentos.setAlignment(ParagraphAlignment.CENTER);

						XWPFRun titulo_recebimentosRun = titulo_recebimentos.createRun();
						titulo_recebimentosRun.setText("Carregamentos");
						titulo_recebimentosRun.setColor("000000");
						titulo_recebimentosRun.setBold(true);
						titulo_recebimentosRun.setFontFamily("Arial");
						titulo_recebimentosRun.setFontSize(9);

						double quantidade_kgs_recebidos = gerenciar.getQuantidadeRecebida(contrato_cliente.getId());

						criarTabelaCarregamentos(carregamentos, 0, transferencias_remetente_local,
								transferencias_destinatario_local, getPesoTotalRecebido(contrato_cliente));
						inserirControleNFVendaSaida(carregamentos);

					} else {
						// sem carregamentos
						if (incluir_sem_carregamentos)
							semCarregamentos(carregamentos, contrato_cliente);
					}
				} else if (carregamento && carregamento_como_comprador && unir_carregamentos) {
					if (unir_carregamentos) {
						if (!carregamentos_unidos_como_comprador) {
							double soma_total_quantidade_contratos_kgs = 0;
							ArrayList<CarregamentoCompleto> carregamentos_totais = new ArrayList<>();
							ArrayList<CadastroTransferenciaCarga> transferencias_remetente_totais = new ArrayList<>();
							ArrayList<CadastroTransferenciaCarga> transferencias_destinatario_totais = new ArrayList<>();
							double quantidade__total_kgs_recebidos = 0;

							for (CadastroContrato contrato : lista_final) {
								ArrayList<CarregamentoCompleto> carregamentos_locais = gerenciar_carregamentos
										.getCarregamentoParaRelatorio(contrato.getId());
								carregamentos_totais.addAll(carregamentos_locais);
								ArrayList<CadastroContrato.CadastroTransferenciaCarga> transferencias_remetente_local = gerenciar_transferencias_carga
										.getTransferenciasRemetente(contrato.getId());
								transferencias_remetente_totais.addAll(transferencias_remetente_local);

								ArrayList<CadastroContrato.CadastroTransferenciaCarga> transferencias_destinatario_local = gerenciar_transferencias_carga
										.getTransferenciaDestinatario(contrato.getId());
								transferencias_destinatario_totais.addAll(transferencias_destinatario_local);

								double quantidade_kgs_recebidos = getPesoTotalRecebido(contrato);

								double local_quantidade_kgs = 0;

								if (contrato.getMedida().equalsIgnoreCase("KG")) {
									local_quantidade_kgs = contrato.getQuantidade();
								} else if (contrato.getMedida().equalsIgnoreCase("Sacos")) {
									local_quantidade_kgs = contrato.getQuantidade() * 60;
								}

								soma_total_quantidade_contratos_kgs += local_quantidade_kgs;
								quantidade__total_kgs_recebidos += quantidade_kgs_recebidos;
							}

							// faz a soma das quantidades dos contratos

							if (carregamentos_totais.size() > 0 || transferencias_remetente_totais.size() > 0
									|| transferencias_destinatario_totais.size() > 0) {

								adicionarTraco(true, 0);

								XWPFParagraph titulo_recebimentos = document_global.createParagraph();
								titulo_recebimentos.setAlignment(ParagraphAlignment.CENTER);

								XWPFRun titulo_recebimentosRun = titulo_recebimentos.createRun();
								titulo_recebimentosRun.setText("Carregamentos");
								titulo_recebimentosRun.setColor("000000");
								titulo_recebimentosRun.setBold(true);
								titulo_recebimentosRun.setFontFamily("Arial");
								titulo_recebimentosRun.setFontSize(9);

								criarTabelaCarregamentos(carregamentos_totais, soma_total_quantidade_contratos_kgs,
										transferencias_remetente_totais, transferencias_destinatario_totais,
										quantidade__total_kgs_recebidos);
								inserirControleNFVendaSaida(carregamentos_totais);

							}
							carregamentos_unidos_como_comprador = true;
						}
					}
				}

				// pagamentos
				if (pagamento && pagamento_como_comprador && !unir_pagamentos) {
					// if (contrato_cliente.getSub_contrato() == 0 ||
					// contrato_cliente.getSub_contrato() == 4
					// || contrato_cliente.getSub_contrato() == 3) {

					ArrayList<PagamentoCompleto> lista_pagamentos = gerenciar
							.getPagamentosContratuaisParaRelatorio(contrato_cliente.getId());

					if (lista_pagamentos.size() > 0) {

						XWPFParagraph titulo_sub_contratos = document_global.createParagraph();
						titulo_sub_contratos.setAlignment(ParagraphAlignment.CENTER);

						XWPFRun titulo_sub_contratostitleRun = titulo_sub_contratos.createRun();
						titulo_sub_contratostitleRun.setText("Pagamentos");
						titulo_sub_contratostitleRun.setColor("000000");
						titulo_sub_contratostitleRun.setBold(true);
						titulo_sub_contratostitleRun.setFontFamily("Arial");
						titulo_sub_contratostitleRun.setFontSize(10);

						if (!contrato) {
							substituirTexto("Cliente: " + nome_cliente.toUpperCase(), 0);

						}
						criarTabelaPagamentos(lista_pagamentos, contrato_cliente);

					} else {
						if (incluir_sem_pagamentos)
							semPagamentos(contrato_cliente);
					}
					// }
				} else if (pagamento && pagamento_como_comprador && unir_pagamentos) {
					if (unir_pagamentos) {

						ArrayList<PagamentoCompleto> lista_pagamentos = gerenciar
								.getPagamentosContratuaisParaRelatorio(contrato_cliente.getId());

						if (lista_pagamentos.size() > 0) {
							if (!titulo_pagamentos_criado) {

								titulo_pagamentos_criado = true;

								adicionarTraco(true, 0);

								XWPFParagraph titulo_sub_contratos = document_global.createParagraph();
								titulo_sub_contratos.setAlignment(ParagraphAlignment.CENTER);

								XWPFRun titulo_sub_contratostitleRun = titulo_sub_contratos.createRun();
								titulo_sub_contratostitleRun.setText("Pagamentos");
								titulo_sub_contratostitleRun.setColor("000000");
								titulo_sub_contratostitleRun.setBold(true);
								titulo_sub_contratostitleRun.setFontFamily("Arial");
								titulo_sub_contratostitleRun.setFontSize(10);
							}

							if (!contrato) {
								substituirTexto("Cliente: " + nome_cliente.toUpperCase(), 0);

							}
							criarTabelaPagamentosUnidos(lista_pagamentos, 0, 0, contrato_cliente);

						} else {
							if (incluir_sem_pagamentos)
								semPagamentos(contrato_cliente);
						}

					}
				}

				//////////////// fim pagamento unidos como comprador
			}
		}

		if (recebimento_como_vendedor || carregamento_como_vendedor || pagamento_como_vendedor) {
			// contratos_deste_cliente = getContratos(cliente_alvo_global.getId(),false);

			for (CadastroContrato contrato_cliente : lista_final) {
				if (!recebimentos_unidos_como_vendedor)
					adicionarTraco(true, 0);

				if (recebimento && recebimento_como_vendedor && !unir_recebimentos) {

					double soma_total_quantidade_contratos_kgs = 0;
					ArrayList<RecebimentoCompleto> recebimentos_totais = new ArrayList<>();

					ArrayList<RecebimentoCompleto> recebimentos_locais = gerenciar_recebimentos
							.getRecebimentosParaRelatorio(contrato_cliente.getId());
					recebimentos_totais.addAll(recebimentos_locais);

					double local_quantidade_kgs = 0;

					if (contrato_cliente.getMedida().equalsIgnoreCase("KG")) {
						local_quantidade_kgs = contrato_cliente.getQuantidade();
					} else if (contrato_cliente.getMedida().equalsIgnoreCase("Sacos")) {
						local_quantidade_kgs = contrato_cliente.getQuantidade() * 60;
					}

					soma_total_quantidade_contratos_kgs += local_quantidade_kgs;

					if (recebimentos_totais.size() > 0) {

						XWPFParagraph titulo_recebimentos = document_global.createParagraph();
						titulo_recebimentos.setAlignment(ParagraphAlignment.CENTER);

						XWPFRun titulo_recebimentosRun = titulo_recebimentos.createRun();
						titulo_recebimentosRun.setText("Recebimentos");
						titulo_recebimentosRun.setColor("000000");
						titulo_recebimentosRun.setBold(true);
						titulo_recebimentosRun.setFontFamily("Arial");
						titulo_recebimentosRun.setFontSize(9);

						criarTabelaRecebimentos(recebimentos_totais, contrato_cliente);
						// controle de nfs
						inserirControleNFVendaEntrada(recebimentos_totais);
					} else {
						if (incluir_sem_recebimentos)
							semRecebimentos(contrato_cliente);
					}

				} else if (recebimento && recebimento_como_vendedor && unir_recebimentos) {
					if (unir_recebimentos) {
						if (!recebimentos_unidos_como_vendedor) {
							double soma_total_quantidade_contratos_kgs = 0;
							ArrayList<RecebimentoCompleto> recebimentos_totais = new ArrayList<>();
							String texto_contratos_sem_recebimentos = "\nContratos Sem Nenhum Recebimento:\n";
							GerenciarBancoTransferenciaRecebimento gerenciar_transferencias = new GerenciarBancoTransferenciaRecebimento();

							for (CadastroContrato contrato : lista_final) {
								ArrayList<RecebimentoCompleto> recebimentos_locais = gerenciar_recebimentos
										.getRecebimentosParaRelatorio(contrato.getId());
								recebimentos_totais.addAll(recebimentos_locais);

								if (recebimentos_locais.size() <= 0) {
									texto_contratos_sem_recebimentos = texto_contratos_sem_recebimentos
											+ infoContrato(contrato) + "**** SEM RECEBIMENTOS ****\n";
								}
								double local_quantidade_kgs = 0;

								if (contrato.getMedida().equalsIgnoreCase("KG")) {
									local_quantidade_kgs = contrato.getQuantidade();
								} else if (contrato.getMedida().equalsIgnoreCase("Sacos")) {
									local_quantidade_kgs = contrato.getQuantidade() * 60;
								}

								soma_total_quantidade_contratos_kgs += local_quantidade_kgs;

								if (incluir_transferencias_recebimentos) {

									ArrayList<CadastroContrato.CadastroTransferenciaRecebimento> lista_transferencias_recebimento_remetente_local = gerenciar_transferencias
											.getTransferenciasRemetente(contrato.getId());

									ArrayList<CadastroContrato.CadastroTransferenciaRecebimento> lista_transferencias_recebimento_destinatario_local = gerenciar_transferencias
											.getTransferenciaDestinatario(contrato.getId());

									for (CadastroContrato.CadastroTransferenciaRecebimento enviado_via_trans : lista_transferencias_recebimento_remetente_local) {

										RecebimentoCompleto recebimento = new RecebimentoCompleto();
										recebimento.setCodigo_romaneio(
												enviado_via_trans.getId_contrato_destinatario() + "");
										recebimento.setData_recebimento(enviado_via_trans.getData());
										recebimento.setPeso_romaneio(enviado_via_trans.getQuantidade());
										recebimento.setCodigo_romaneio("-Transferencia");

										recebimento.setContrato(contrato);

										recebimento.setNf_remessa_aplicavel(0);
										recebimento.setNf_venda_aplicavel(0);

										recebimentos_totais.add(recebimento);
									}

									for (CadastroContrato.CadastroTransferenciaRecebimento recebido_via_trans : lista_transferencias_recebimento_destinatario_local) {

										RecebimentoCompleto recebimento = new RecebimentoCompleto();
										recebimento.setCodigo_romaneio(
												recebido_via_trans.getId_contrato_destinatario() + "");
										recebimento.setData_recebimento(recebido_via_trans.getData());
										recebimento.setPeso_romaneio(recebido_via_trans.getQuantidade());
										recebimento.setCodigo_romaneio("+Transferencia");

										recebimento.setContrato(contrato);

										recebimento.setNf_remessa_aplicavel(0);
										recebimento.setNf_venda_aplicavel(0);

										recebimentos_totais.add(recebimento);

									}
								}

							}

							// faz a soma das quantidades dos contratos

							if (recebimentos_totais.size() > 0) {

								adicionarTraco(true, 0);

								XWPFParagraph titulo_recebimentos = document_global.createParagraph();
								titulo_recebimentos.setAlignment(ParagraphAlignment.CENTER);

								XWPFRun titulo_recebimentosRun = titulo_recebimentos.createRun();
								titulo_recebimentosRun.setText("Recebimentos");
								titulo_recebimentosRun.setColor("000000");
								titulo_recebimentosRun.setBold(true);
								titulo_recebimentosRun.setFontFamily("Arial");
								titulo_recebimentosRun.setFontSize(9);

								criarTabelaRecebimentosUnidos(recebimentos_totais, soma_total_quantidade_contratos_kgs);

								if (incluir_sem_recebimentos) {

									substituirTexto(texto_contratos_sem_recebimentos);

								}
								// controle de nfs
								inserirControleNFVendaEntrada(recebimentos_totais);
							}
							recebimentos_unidos_como_vendedor = true;
						}
					}
				}

				////////////////// carregamentos
				if (carregamento && carregamento_como_vendedor && !unir_carregamentos) {

					ArrayList<CarregamentoCompleto> carregamentos = gerenciar_carregamentos
							.getCarregamentoParaRelatorio(contrato_cliente.getId());
					ArrayList<CadastroContrato.CadastroTransferenciaCarga> transferencias_remetente_local = gerenciar_transferencias_carga
							.getTransferenciasRemetente(contrato_cliente.getId());

					ArrayList<CadastroContrato.CadastroTransferenciaCarga> transferencias_destinatario_local = gerenciar_transferencias_carga
							.getTransferenciaDestinatario(contrato_cliente.getId());
					if (carregamentos.size() > 0 || transferencias_remetente_local.size() > 0
							|| transferencias_destinatario_local.size() > 0) {

						XWPFParagraph titulo_recebimentos = document_global.createParagraph();
						titulo_recebimentos.setAlignment(ParagraphAlignment.CENTER);

						XWPFRun titulo_recebimentosRun = titulo_recebimentos.createRun();
						titulo_recebimentosRun.setText("Carregamentos");
						titulo_recebimentosRun.setColor("000000");
						titulo_recebimentosRun.setBold(true);
						titulo_recebimentosRun.setFontFamily("Arial");
						titulo_recebimentosRun.setFontSize(9);

						double quantidade_kgs_recebidos = gerenciar.getQuantidadeRecebida(contrato_cliente.getId());

						criarTabelaCarregamentos(carregamentos, 0, transferencias_remetente_local,
								transferencias_destinatario_local, getPesoTotalRecebido(contrato_cliente));
						inserirControleNFVendaSaida(carregamentos);

					} else {
						// sem carregamentos
						if (incluir_sem_carregamentos)
							semCarregamentos(carregamentos, contrato_cliente);
					}
				} else if (carregamento && carregamento_como_vendedor && unir_carregamentos) {
					if (unir_carregamentos) {
						if (!carregamentos_unidos_como_vendedor) {
							double soma_total_quantidade_contratos_kgs = 0;
							ArrayList<CarregamentoCompleto> carregamentos_totais = new ArrayList<>();
							ArrayList<CadastroTransferenciaCarga> transferencias_remetente_totais = new ArrayList<>();
							ArrayList<CadastroTransferenciaCarga> transferencias_destinatario_totais = new ArrayList<>();
							double quantidade__total_kgs_recebidos = 0;

							for (CadastroContrato contrato : lista_final) {
								ArrayList<CarregamentoCompleto> carregamentos_locais = gerenciar_carregamentos
										.getCarregamentoParaRelatorio(contrato.getId());
								carregamentos_totais.addAll(carregamentos_locais);
								ArrayList<CadastroContrato.CadastroTransferenciaCarga> transferencias_remetente_local = gerenciar_transferencias_carga
										.getTransferenciasRemetente(contrato.getId());
								transferencias_remetente_totais.addAll(transferencias_remetente_local);

								ArrayList<CadastroContrato.CadastroTransferenciaCarga> transferencias_destinatario_local = gerenciar_transferencias_carga
										.getTransferenciaDestinatario(contrato.getId());
								transferencias_destinatario_totais.addAll(transferencias_destinatario_local);

								double quantidade_kgs_recebidos = getPesoTotalRecebido(contrato);

								double local_quantidade_kgs = 0;

								if (contrato.getMedida().equalsIgnoreCase("KG")) {
									local_quantidade_kgs = contrato.getQuantidade();
								} else if (contrato.getMedida().equalsIgnoreCase("Sacos")) {
									local_quantidade_kgs = contrato.getQuantidade() * 60;
								}

								soma_total_quantidade_contratos_kgs += local_quantidade_kgs;
								quantidade__total_kgs_recebidos += quantidade_kgs_recebidos;
							}

							// faz a soma das quantidades dos contratos

							if (carregamentos_totais.size() > 0) {

								adicionarTraco(true, 0);

								XWPFParagraph titulo_recebimentos = document_global.createParagraph();
								titulo_recebimentos.setAlignment(ParagraphAlignment.CENTER);

								XWPFRun titulo_recebimentosRun = titulo_recebimentos.createRun();
								titulo_recebimentosRun.setText("Carregamentos");
								titulo_recebimentosRun.setColor("000000");
								titulo_recebimentosRun.setBold(true);
								titulo_recebimentosRun.setFontFamily("Arial");
								titulo_recebimentosRun.setFontSize(9);

								criarTabelaCarregamentos(carregamentos_totais, soma_total_quantidade_contratos_kgs,
										transferencias_remetente_totais, transferencias_destinatario_totais,
										quantidade__total_kgs_recebidos);
								inserirControleNFVendaSaida(carregamentos_totais);

							}
							carregamentos_unidos_como_vendedor = true;
						}
					}
				}

				// pagamentos
				if (pagamento && pagamento_como_vendedor && !unir_pagamentos) {

					// if (contrato_cliente.getSub_contrato() == 0 ||
					// contrato_cliente.getSub_contrato() == 4
					// || contrato_cliente.getSub_contrato() == 3) {
					ArrayList<PagamentoCompleto> lista_pagamentos = gerenciar
							.getPagamentosContratuaisParaRelatorio(contrato_cliente.getId());

					if (lista_pagamentos.size() > 0) {

						XWPFParagraph titulo_sub_contratos = document_global.createParagraph();
						titulo_sub_contratos.setAlignment(ParagraphAlignment.CENTER);

						XWPFRun titulo_sub_contratostitleRun = titulo_sub_contratos.createRun();
						titulo_sub_contratostitleRun.setText("Pagamentos");
						titulo_sub_contratostitleRun.setColor("000000");
						titulo_sub_contratostitleRun.setBold(true);
						titulo_sub_contratostitleRun.setFontFamily("Arial");
						titulo_sub_contratostitleRun.setFontSize(10);

						substituirTexto("Cliente: " + nome_cliente.toUpperCase(), 0);

						criarTabelaPagamentos(lista_pagamentos, contrato_cliente);

					} else {
						if (incluir_sem_pagamentos)
							semPagamentos(contrato_cliente);
					}

					// }
				} else if (pagamento && pagamento_como_vendedor && unir_pagamentos) {

					if (unir_pagamentos) {
						ArrayList<PagamentoCompleto> lista_pagamentos = gerenciar
								.getPagamentosContratuaisParaRelatorio(contrato_cliente.getId());

						if (lista_pagamentos.size() > 0) {

							if (!titulo_pagamentos_criado) {

								titulo_pagamentos_criado = true;

								adicionarTraco(true, 0);

								XWPFParagraph titulo_sub_contratos = document_global.createParagraph();
								titulo_sub_contratos.setAlignment(ParagraphAlignment.CENTER);

								XWPFRun titulo_sub_contratostitleRun = titulo_sub_contratos.createRun();
								titulo_sub_contratostitleRun.setText("Pagamentos");
								titulo_sub_contratostitleRun.setColor("000000");
								titulo_sub_contratostitleRun.setBold(true);
								titulo_sub_contratostitleRun.setFontFamily("Arial");
								titulo_sub_contratostitleRun.setFontSize(10);
							}

							if (!contrato) {
								substituirTexto("Cliente: " + nome_cliente.toUpperCase(), 0);

							}
							criarTabelaPagamentosUnidos(lista_pagamentos, 0, 0, contrato_cliente);

						} else {
							if (incluir_sem_pagamentos)
								semPagamentos(contrato_cliente);
						}

					}

				}

			}

		}
		// contador_clientes++;

		if (pagamento && (pagamento_como_vendedor || pagamento_como_comprador))
			incluirSomatoriaTotalPagamentos();
		// cabecalho e rodape

		try {
			CTP ctP = CTP.Factory.newInstance();

			// header text
			CTText t = ctP.addNewR().addNewT();

			XWPFParagraph cabecalho = new XWPFParagraph(ctP, document_global);
			XWPFRun cabecalhoRun = cabecalho.createRun();
			cabecalhoRun.setFontSize(16);
			cabecalhoRun.setFontFamily("Arial Black");
			cabecalhoRun.setText("LD ARMAZÉNS GERAIS");
			cabecalhoRun.setUnderline(UnderlinePatterns.SINGLE);
			cabecalhoRun.setColor("00A000");

			XWPFParagraph pars[] = new XWPFParagraph[1];

			pars[0] = cabecalho;

			pars[0].setAlignment(ParagraphAlignment.LEFT);

			XWPFHeaderFooterPolicy hfPolicy = document_global.createHeaderFooterPolicy();
			XWPFHeader header = hfPolicy.createHeader(XWPFHeaderFooterPolicy.DEFAULT, pars);

			// hfPolicy.createHeader(XWPFHeaderFooterPolicy.DEFAULT, pars);

			pars[0] = header.getParagraphArray(0);
			pars[0].setAlignment(ParagraphAlignment.LEFT);

			CTTabStop tabStop = pars[0].getCTP().getPPr().addNewTabs().addNewTab();
			tabStop.setVal(STTabJc.RIGHT);
			int twipsPerInch = 1440;
			tabStop.setPos(BigInteger.valueOf(6 * twipsPerInch));

			cabecalhoRun = pars[0].createRun();
			cabecalhoRun.addTab();

			cabecalhoRun = pars[0].createRun();
			URL url = getClass().getResource("/imagens/logo_para_relatorio.png");
			String imgFile = url.getFile();
			cabecalhoRun.addPicture(new FileInputStream(imgFile), XWPFDocument.PICTURE_TYPE_PNG, imgFile,
					Units.toEMU(200), Units.toEMU(60));

		} catch (Exception e) {
			// //JOptionPane.showMessageDialog(null,
			// "Erro ao criar cabecalho e rodape do contrato!\nConsulte o administrador do
			// sistema!");
			e.printStackTrace();
		}

		telaEmEsperaRelatoria.setInfo("Preparando Arquivo", 50);

		ByteArrayOutputStream saida_apos_edicao = new ByteArrayOutputStream();

		try {
			document_global.write(new FileOutputStream("c:\\temp\\arquivoteste.docx"));
			// document_global.write(saida_apos_edicao);

		} catch (IOException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		telaEmEsperaRelatoria.setInfo("Arquivo MS Word Criado", 60);

		return "c:\\temp\\arquivoteste.docx";

	}

	public String infoContrato(CadastroContrato contrato) {
		// compradores x vendedores
		CadastroCliente compradores[] = contrato.getCompradores();
		CadastroCliente vendedores[] = contrato.getVendedores();

		String nome_vendedores = "";
		String nome_compradores = "";

		if (compradores[0] != null) {
			if (compradores[0].getTipo_pessoa() == 0) {
				// pessoa fisica
				nome_compradores = compradores[0].getNome_empresarial();
			} else {
				nome_compradores = compradores[0].getNome_fantaia();

			}
		}
		if (compradores[1] != null) {
			if (compradores[1].getTipo_pessoa() == 0) {
				// pessoa fisica
				nome_compradores = nome_compradores + ", " + compradores[1].getNome_empresarial();
			} else {
				nome_compradores = nome_compradores + ", " + compradores[1].getNome_fantaia();

			}
		}
		if (vendedores[0] != null) {
			if (vendedores[0].getTipo_pessoa() == 0) {
				nome_vendedores = vendedores[0].getNome_empresarial();
			} else {
				nome_vendedores = vendedores[0].getNome_fantaia();
			}
		}

		if (vendedores[1] != null) {
			if (vendedores[1].getTipo_pessoa() == 0) {
				nome_vendedores = nome_vendedores + ", " + vendedores[1].getNome_empresarial();
			} else {
				nome_vendedores = nome_vendedores + ", " + vendedores[1].getNome_fantaia();
			}
		}

		String partes = nome_compradores + " X " + nome_vendedores;

		NumberFormat z = NumberFormat.getNumberInstance();
		Locale ptBr = new Locale("pt", "BR");
		double quantidade_kg = 0;
		double quantidade_sacos = 0;

		if (contrato.getMedida().equalsIgnoreCase("KG")) {
			quantidade_kg = contrato.getQuantidade();
			quantidade_sacos = quantidade_kg / 60;
		} else if (contrato.getMedida().equalsIgnoreCase("Sacos")) {
			quantidade_sacos = contrato.getQuantidade();
			quantidade_kg = quantidade_sacos * 60;
		}
		// safra
		String safra = contrato.getModelo_safra().getProduto().getNome_produto() + " "
				+ contrato.getModelo_safra().getProduto().getTransgenia() + " "
				+ contrato.getModelo_safra().getAno_plantio() + "/" + contrato.getModelo_safra().getAno_colheita();

		String info = "CTR: " + contrato.getCodigo() + " " + partes + " " + safra + " Quantidade Total: "
				+ z.format(quantidade_kg) + " kgs | " + z.format(quantidade_sacos) + " sacos "
				+ NumberFormat.getCurrencyInstance(ptBr).format(contrato.getValor_produto()) + " por "
				+ contrato.getMedida() + " totalizando: "
				+ NumberFormat.getCurrencyInstance(ptBr).format(contrato.getValor_a_pagar().doubleValue());

		return info;
	}

	public void semCarregamentos(ArrayList<CarregamentoCompleto> carregamentos, CadastroContrato contrato) {
		XWPFParagraph titulo_recebimentos = document_global.createParagraph();
		titulo_recebimentos.setAlignment(ParagraphAlignment.CENTER);

		XWPFRun titulo_recebimentosRun = titulo_recebimentos.createRun();
		titulo_recebimentosRun.setText("\nCONTRATO " + infoContrato(contrato) + " -SEM CARREGAMENTOS-\n");
		titulo_recebimentosRun.setColor("000000");
		titulo_recebimentosRun.setBold(true);
		titulo_recebimentosRun.setFontFamily("Arial");
		titulo_recebimentosRun.setFontSize(9);

	}

	public void semPagamentos(CadastroContrato contrato) {
		XWPFParagraph titulo_sub_contratos = document_global.createParagraph();
		titulo_sub_contratos.setAlignment(ParagraphAlignment.CENTER);

		XWPFRun titulo_sub_contratostitleRun = titulo_sub_contratos.createRun();
		titulo_sub_contratostitleRun.setText("\nCONTRATO " + infoContrato(contrato) + " -SEM PAGAMENTOS-\n");
		titulo_sub_contratostitleRun.setColor("000000");
		titulo_sub_contratostitleRun.setBold(true);
		titulo_sub_contratostitleRun.setFontFamily("Arial");
		titulo_sub_contratostitleRun.setFontSize(10);

	}

	public void semRecebimentos(CadastroContrato contrato) {
		XWPFParagraph titulo_recebimentos = document_global.createParagraph();
		titulo_recebimentos.setAlignment(ParagraphAlignment.CENTER);

		XWPFRun titulo_recebimentosRun = titulo_recebimentos.createRun();
		titulo_recebimentosRun.setText("\nCONTRATO " + infoContrato(contrato) + " -SEM RECEBIMENTOS-\n");
		titulo_recebimentosRun.setColor("000000");
		titulo_recebimentosRun.setBold(true);
		titulo_recebimentosRun.setFontFamily("Arial");
		titulo_recebimentosRun.setFontSize(9);

	}

	public DadosTabelaExcel semRecebimentosExcel(HSSFWorkbook workbook, HSSFSheet sheet, int rownum,
			CadastroContrato contrato) {

		int cellnum = 0;

		Cell cell;
		Row row;

		HSSFFont newFont_branca = workbook.createFont();
		newFont_branca.setBold(true);
		newFont_branca.setColor(IndexedColors.WHITE.getIndex());
		newFont_branca.setFontName("Calibri");
		newFont_branca.setItalic(false);
		newFont_branca.setFontHeight((short) (11 * 20));

		// estilo para cabecalho fundo verde
		CellStyle celula_fundo_verde_texto_branco = workbook.createCellStyle();
		celula_fundo_verde_texto_branco.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		celula_fundo_verde_texto_branco.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		celula_fundo_verde_texto_branco.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_verde_texto_branco.setVerticalAlignment(VerticalAlignment.CENTER);

		celula_fundo_verde_texto_branco.setFont(newFont_branca);

		String texto = "CONTRATO " + infoContrato(contrato) + " -SEM RECEBIMENTOS";

		row = sheet.createRow(rownum);
		cell = row.createCell(cellnum);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue(texto);
		sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, cellnum, 10));

		rownum++;

		row = sheet.createRow(rownum);
		cell = row.createCell(cellnum);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue(contrato.getNomes_compradores() + " X " + contrato.getNomes_vendedores());
		sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, cellnum, 10));

		rownum += 3;

		DadosTabelaExcel retornar = new DadosTabelaExcel();
		retornar.setWorkbook(workbook);
		retornar.setSheet(sheet);
		retornar.setRownum(rownum);

		return retornar;

	}

	public DadosTabelaExcel semRecebimentosExcelSemEspaco(HSSFWorkbook workbook, HSSFSheet sheet, int rownum,
			String texto) {

		int cellnum = 0;

		Cell cell;
		Row row;

		HSSFFont newFont_branca = workbook.createFont();
		newFont_branca.setBold(true);
		newFont_branca.setColor(IndexedColors.WHITE.getIndex());
		newFont_branca.setFontName("Calibri");
		newFont_branca.setItalic(false);
		newFont_branca.setFontHeight((short) (11 * 20));

		// estilo para cabecalho fundo verde
		CellStyle celula_fundo_verde_texto_branco = workbook.createCellStyle();
		celula_fundo_verde_texto_branco.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		celula_fundo_verde_texto_branco.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		celula_fundo_verde_texto_branco.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_verde_texto_branco.setVerticalAlignment(VerticalAlignment.CENTER);

		celula_fundo_verde_texto_branco.setFont(newFont_branca);

		row = sheet.createRow(rownum);
		cell = row.createCell(cellnum);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue(texto);
		sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, cellnum, 10));

		rownum++;

		DadosTabelaExcel retornar = new DadosTabelaExcel();
		retornar.setWorkbook(workbook);
		retornar.setSheet(sheet);
		retornar.setRownum(rownum);

		return retornar;

	}

	public void inserirControleNFVendaEntrada(ArrayList<RecebimentoCompleto> recebimentos) {
		if (controle_nf_venda_recebimentos) {

			if (recebimentos.size() > 0) {

				XWPFParagraph titulo_controle_vendas = document_global.createParagraph();
				titulo_controle_vendas.setAlignment(ParagraphAlignment.CENTER);

				XWPFRun titulo_controle_vendasRun = titulo_controle_vendas.createRun();
				titulo_controle_vendasRun.setText("CONTROLE DE NOTAS DE VENDA E REMESSA(ENTRADA)");
				titulo_controle_vendasRun.setColor("000000");
				titulo_controle_vendasRun.setBold(true);
				titulo_controle_vendasRun.setFontFamily("Arial");
				titulo_controle_vendasRun.setFontSize(9);

				ArrayList<NFCompleto> nfs = new ArrayList<>();
				NumberFormat z = NumberFormat.getNumberInstance();

				for (RecebimentoCompleto recebimento : recebimentos) {

					if (recebimento.getNf_venda_aplicavel() == 1 || recebimento.getNf_remessa_aplicavel() == 1) {

						NFCompleto nota_deste_recebimento = new NFCompleto();
						nota_deste_recebimento.setCodigo_contrato(recebimento.getContrato().getCodigo());
						nota_deste_recebimento.setData(recebimento.getData_recebimento());
						nota_deste_recebimento.setPeso_romaneio(recebimento.getPeso_romaneio());
						nota_deste_recebimento.setCodigo_romaneio(recebimento.getCodigo_romaneio());
						nota_deste_recebimento
								.setProduto(recebimento.getContrato().getModelo_safra().getProduto().getNome_produto());
						// este recebimento possui nfs_venda
						try {
							if (checkString(recebimento.getCodigo_nf_venda())) {
								if (recebimento.getCaminho_nf_venda().length() > 10) {
									// procurar por nf venda
									ManipularNotasFiscais manipular = new ManipularNotasFiscais("");
									CadastroNFe nota_fiscal_venda = manipular
											.filtrar(new File(servidor_unidade + recebimento.getCaminho_nf_venda()));

									nota_fiscal_venda.setContrato(recebimento.getContrato());

									nota_deste_recebimento.setCodigo_nf_venda(nota_fiscal_venda.getNfe());

									Number number = null;
									try {
										number = z.parse(nota_fiscal_venda.getQuantidade());
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									double Dpeso = number.doubleValue();

									nota_deste_recebimento.setPeso(Dpeso);
									nota_deste_recebimento.setValor(nota_fiscal_venda.getValor());
									nota_deste_recebimento.setNome_emitende(nota_fiscal_venda.getNome_remetente());
									nota_deste_recebimento
											.setNome_destinatario(nota_fiscal_venda.getNome_destinatario());

								} else {
									CadastroNFe nota_fiscal_venda = new CadastroNFe();
									nota_fiscal_venda.setQuantidade_double(recebimento.getPeso_nf_venda());
									nota_fiscal_venda.setNfe(recebimento.getCodigo_nf_venda());
									nota_fiscal_venda.setValor(recebimento.getValor_nf_venda().toString());
									nota_fiscal_venda.setContrato(recebimento.getContrato());
									if (checkString(recebimento.getNome_remetente_nf_venda())) {
										nota_fiscal_venda.setNome_remetente(recebimento.getNome_remetente_nf_venda());

									}
									if (checkString(recebimento.getNome_destinatario_nf_venda())) {
										nota_fiscal_venda
												.setNome_destinatario(recebimento.getNome_destinatario_nf_venda());

									}

									nota_deste_recebimento.setCodigo_nf_venda(nota_fiscal_venda.getNfe());

									nota_deste_recebimento.setPeso(nota_fiscal_venda.getQuantidade_double());
									nota_deste_recebimento.setValor(nota_fiscal_venda.getValor());
									nota_deste_recebimento.setNome_emitende(nota_fiscal_venda.getNome_remetente());
									nota_deste_recebimento
											.setNome_destinatario(nota_fiscal_venda.getNome_destinatario());

								}

							}
						} catch (Exception e) {

						}

						try {
							if (checkString(recebimento.getCodigo_nf_remessa())) {
								if (recebimento.getCaminho_nf_remessa().length() > 10) {
									// procurar por nf venda
									ManipularNotasFiscais manipular = new ManipularNotasFiscais("");
									CadastroNFe nf_remessa = manipular
											.filtrar(new File(servidor_unidade + recebimento.getCaminho_nf_remessa()));

									nf_remessa.setContrato(recebimento.getContrato());

									nota_deste_recebimento.setCodigo_nf_remessa(nf_remessa.getNfe());

									Number number = null;
									try {
										number = z.parse(nf_remessa.getQuantidade());
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									double Dpeso = number.doubleValue();

									nota_deste_recebimento.setPeso(Dpeso);
									nota_deste_recebimento.setValor(nf_remessa.getValor());
									nota_deste_recebimento.setNome_emitende(nf_remessa.getNome_remetente());
									nota_deste_recebimento.setNome_destinatario(nf_remessa.getNome_destinatario());

								} else {
									CadastroNFe nf_remessa = new CadastroNFe();
									nf_remessa.setQuantidade_double(recebimento.getPeso_nf_remessa());
									nf_remessa.setNfe(recebimento.getCodigo_nf_remessa());
									nf_remessa.setValor(recebimento.getValor_nf_remessa().toString());
									nf_remessa.setContrato(recebimento.getContrato());
									if (checkString(recebimento.getNome_remetente_nf_venda())) {
										nf_remessa.setNome_remetente(recebimento.getNome_remetente_nf_venda());

									}
									if (checkString(recebimento.getNome_destinatario_nf_venda())) {
										nf_remessa.setNome_destinatario(recebimento.getNome_destinatario_nf_venda());

									}

									nota_deste_recebimento.setCodigo_nf_remessa(nf_remessa.getNfe());

									nota_deste_recebimento.setPeso(nf_remessa.getQuantidade_double());
									nota_deste_recebimento.setValor(nf_remessa.getValor());
									nota_deste_recebimento.setNome_emitende(nf_remessa.getNome_remetente());
									nota_deste_recebimento.setNome_destinatario(nf_remessa.getNome_destinatario());

								}

							}
						} catch (Exception e) {

						}

						nfs.add(nota_deste_recebimento);

					}

				}

				criarTabelaNFVendaERemessa(nfs);

			}

		}
	}

	public void inserirControleNFVendaSaida(ArrayList<CarregamentoCompleto> carregamentos) {
		if (controle_nf_venda_carregamentos) {

			if (carregamentos.size() > 0) {

				XWPFParagraph titulo_controle_vendas = document_global.createParagraph();
				titulo_controle_vendas.setAlignment(ParagraphAlignment.CENTER);

				XWPFRun titulo_controle_vendasRun = titulo_controle_vendas.createRun();
				titulo_controle_vendasRun.setText("CONTROLE DE NOTAS DE VENDA(SAÍDA)");
				titulo_controle_vendasRun.setColor("000000");
				titulo_controle_vendasRun.setBold(true);
				titulo_controle_vendasRun.setFontFamily("Arial");
				titulo_controle_vendasRun.setFontSize(9);

				ArrayList<CadastroNFe> nfs_venda = new ArrayList<>();

				for (CadastroContrato.Carregamento carregamento : carregamentos) {

					if (carregamento.getNf_venda1_aplicavel() == 1) {
						// este recebimento possui nfs_venda
						try {
							if (checkString(carregamento.getCaminho_nf_venda1())) {
								if (carregamento.getCaminho_nf_venda1().length() > 10) {
									// procurar por nf venda
									ManipularNotasFiscais manipular = new ManipularNotasFiscais("");
									CadastroNFe nota_fiscal_venda = manipular
											.filtrar(new File(servidor_unidade + carregamento.getCaminho_nf_venda1()));
									nota_fiscal_venda.setContrato(
											new GerenciarBancoContratos().getContrato(carregamento.getId_contrato()));
									nfs_venda.add(nota_fiscal_venda);
								} else {
									CadastroNFe nota_fiscal_venda = new CadastroNFe();
									nota_fiscal_venda.setQuantidade_double(carregamento.getPeso_nf_venda1());
									nota_fiscal_venda.setNfe(carregamento.getCodigo_nf_venda1());
									nota_fiscal_venda.setValor(carregamento.getValor_nf_venda1().toString());
									nota_fiscal_venda.setContrato(
											new GerenciarBancoContratos().getContrato(carregamento.getId_contrato()));
									if (checkString(carregamento.getNome_remetente_nf_venda1())) {
										nota_fiscal_venda.setNome_remetente(carregamento.getNome_remetente_nf_venda1());

									}
									if (checkString(carregamento.getNome_destinatario_nf_venda1())) {
										nota_fiscal_venda
												.setNome_destinatario(carregamento.getNome_destinatario_nf_venda1());

									}
									nfs_venda.add(nota_fiscal_venda);

								}

							}
						} catch (Exception e) {

						}
					}

					if (carregamento.getNf_complemento_aplicavel() == 1) {
						// este recebimento possui nfs_venda
						try {
							if (checkString(carregamento.getCaminho_nf_complemento())) {
								if (carregamento.getCaminho_nf_complemento().length() > 10) {
									// procurar por nf venda
									ManipularNotasFiscais manipular = new ManipularNotasFiscais("");
									CadastroNFe nota_fiscal_venda = manipular.filtrar(
											new File(servidor_unidade + carregamento.getCaminho_nf_complemento()));
									nota_fiscal_venda.setContrato(
											new GerenciarBancoContratos().getContrato(carregamento.getId_contrato()));
									nfs_venda.add(nota_fiscal_venda);
								} else {
									CadastroNFe nota_fiscal_venda = new CadastroNFe();
									nota_fiscal_venda.setQuantidade_double(carregamento.getPeso_nf_complemento());
									nota_fiscal_venda.setNfe(carregamento.getCodigo_nf_complemento());
									nota_fiscal_venda.setValor(carregamento.getValor_nf_complemento().toString());
									nota_fiscal_venda.setContrato(
											new GerenciarBancoContratos().getContrato(carregamento.getId_contrato()));
									if (checkString(carregamento.getNome_remetente_nf_complemento())) {
										nota_fiscal_venda
												.setNome_remetente(carregamento.getNome_remetente_nf_complemento());

									}
									if (checkString(carregamento.getNome_destinatario_nf_complemento())) {
										nota_fiscal_venda.setNome_destinatario(
												carregamento.getNome_destinatario_nf_complemento());

									}
									nfs_venda.add(nota_fiscal_venda);

								}

							}
						} catch (Exception e) {

						}
					}

				}

				criarTabelaNFVenda(nfs_venda);

			}

		}
	}

	public DadosTabelaExcel criarTabelaRecebimentosUnidosExcel(HSSFWorkbook workbook, HSSFSheet sheet, int rownum,
			ArrayList<RecebimentoCompleto> recebimentos, double soma_total_quantidade_contratos) {
		// XWPFParagraph par = document_global.createParagraph();

		NumberFormat z = NumberFormat.getNumberInstance();

		Locale ptBr = new Locale("pt", "BR");

		HSSFDataFormat numberFormat = workbook.createDataFormat();

		HSSFFont newFont_branca = workbook.createFont();
		newFont_branca.setBold(true);
		newFont_branca.setColor(IndexedColors.WHITE.getIndex());
		newFont_branca.setFontName("Calibri");
		newFont_branca.setItalic(false);
		newFont_branca.setFontHeight((short) (11 * 20));

		// estilo para cabecalho fundo verde
		CellStyle celula_fundo_verde_texto_branco = workbook.createCellStyle();
		celula_fundo_verde_texto_branco.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		celula_fundo_verde_texto_branco.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		celula_fundo_verde_texto_branco.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_verde_texto_branco.setVerticalAlignment(VerticalAlignment.CENTER);

		celula_fundo_verde_texto_branco.setFont(newFont_branca);

		HSSFFont newFont_preta = workbook.createFont();
		newFont_preta.setColor(IndexedColors.BLACK.getIndex());
		newFont_preta.setFontName("Calibri");
		newFont_preta.setItalic(false);
		newFont_preta.setFontHeight((short) (11 * 20));

		CellStyle celula_fundo_branco_texto_preto = workbook.createCellStyle();
		celula_fundo_branco_texto_preto.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_branco_texto_preto.setVerticalAlignment(VerticalAlignment.CENTER);
		celula_fundo_branco_texto_preto.setFont(newFont_preta);

		// celula para numero alinhado ao centro
		CellStyle numberStyle = workbook.createCellStyle();
		numberStyle.setDataFormat(numberFormat.getFormat("R$ #,##0.00"));
		numberStyle.setAlignment(HorizontalAlignment.CENTER);
		numberStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		// celula para numero alinhado ao centro
		CellStyle pesoStyle = workbook.createCellStyle();
		pesoStyle.setDataFormat(numberFormat.getFormat("#,##0.00"));
		pesoStyle.setAlignment(HorizontalAlignment.CENTER);
		pesoStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle numberStyleFundoVerdeTextoBranco = workbook.createCellStyle();
		numberStyleFundoVerdeTextoBranco.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		numberStyleFundoVerdeTextoBranco.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		numberStyleFundoVerdeTextoBranco.setAlignment(HorizontalAlignment.CENTER);
		numberStyleFundoVerdeTextoBranco.setVerticalAlignment(VerticalAlignment.CENTER);
		numberStyleFundoVerdeTextoBranco.setDataFormat(numberFormat.getFormat("#,##0.00"));

		numberStyleFundoVerdeTextoBranco.setFont(newFont_branca);

		CellStyle numberStyleFundoBrancoTextoPreto = workbook.createCellStyle();
		numberStyleFundoBrancoTextoPreto.setAlignment(HorizontalAlignment.LEFT);
		numberStyleFundoBrancoTextoPreto.setVerticalAlignment(VerticalAlignment.CENTER);
		numberStyleFundoBrancoTextoPreto.setDataFormat(numberFormat.getFormat("#,##0.00"));
		numberStyleFundoBrancoTextoPreto.setFont(newFont_preta);

		CellStyle valorStyleFundoVerdeTextoBranco = workbook.createCellStyle();
		valorStyleFundoVerdeTextoBranco.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		valorStyleFundoVerdeTextoBranco.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		valorStyleFundoVerdeTextoBranco.setAlignment(HorizontalAlignment.CENTER);
		valorStyleFundoVerdeTextoBranco.setVerticalAlignment(VerticalAlignment.CENTER);
		valorStyleFundoVerdeTextoBranco.setDataFormat(numberFormat.getFormat("R$ #,##0.00"));
		valorStyleFundoVerdeTextoBranco.setFont(newFont_branca);

		// celular de contrato normal
		HSSFFont newFont_verde = workbook.createFont();
		newFont_verde.setColor(IndexedColors.GREEN.getIndex());
		newFont_verde.setFontName("Calibri");
		newFont_verde.setItalic(false);
		newFont_verde.setFontHeight((short) (11 * 20));

		CellStyle celula_fundo_branco_texto_verde = workbook.createCellStyle();
		celula_fundo_branco_texto_verde.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_branco_texto_verde.setVerticalAlignment(VerticalAlignment.CENTER);
		celula_fundo_branco_texto_verde.setFont(newFont_verde);

		// celular de sub-contrato n
		HSSFFont newFont_vermelha = workbook.createFont();
		newFont_vermelha.setColor(IndexedColors.RED.getIndex());
		newFont_vermelha.setFontName("Calibri");
		newFont_vermelha.setItalic(false);
		newFont_vermelha.setFontHeight((short) (11 * 20));

		CellStyle celula_fundo_branco_texto_vermelho = workbook.createCellStyle();
		celula_fundo_branco_texto_vermelho.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_branco_texto_vermelho.setVerticalAlignment(VerticalAlignment.CENTER);
		celula_fundo_branco_texto_vermelho.setFont(newFont_vermelha);

		// celula de ganho potencial

		// celular de sub-contrato n
		HSSFFont newFont_azul = workbook.createFont();
		newFont_azul.setColor(IndexedColors.BLUE.getIndex());
		newFont_azul.setFontName("Calibri");
		newFont_azul.setItalic(false);
		newFont_azul.setFontHeight((short) (11 * 20));

		CellStyle celula_fundo_branco_texto_azul = workbook.createCellStyle();
		celula_fundo_branco_texto_azul.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_branco_texto_azul.setVerticalAlignment(VerticalAlignment.CENTER);
		celula_fundo_branco_texto_azul.setFont(newFont_azul);

		String texto = "Quantidade Total Contratada: " + z.format(soma_total_quantidade_contratos) + " kgs | "
				+ z.format(soma_total_quantidade_contratos / 60) + " sacos";

		int cellnum = 0;

		Cell cell;
		Row row;

		row = sheet.createRow(rownum);
		cell = row.createCell(cellnum);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue(texto);
		sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, cellnum, 10));

		rownum++;

		row = sheet.createRow(rownum);

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("CONTRATO");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("DATA");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("CODIGO ROMANEIO");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("PESO ROMANEIO:".toUpperCase());

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
		cell.setCellValue("NF REMESSA".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("PESO NF REMESSA".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("VALOR NF REMESSA".toUpperCase());

		int primeira_linha = rownum++;
		int ultima_linha = primeira_linha;

		sheet.setAutoFilter(CellRangeAddress.valueOf("A2:J2"));

		for (RecebimentoCompleto recebimento : recebimentos) {

			cellnum = 0;
			row = sheet.createRow(rownum);

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_branco_texto_preto);
			cell.setCellValue(recebimento.getContrato().getCodigo());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_branco_texto_preto);
			cell.setCellValue(recebimento.getData_recebimento());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_branco_texto_preto);
			String codigo = recebimento.getCodigo_romaneio();
			cell.setCellValue(codigo);

			cell = row.createCell(cellnum++);
			cell.setCellStyle(pesoStyle);
			if (codigo.equalsIgnoreCase("-Transferencia")) {
				cell.setCellValue(recebimento.getPeso_romaneio() * -1);

			} else if (codigo.equalsIgnoreCase("+Transferencia")) {
				cell.setCellValue(recebimento.getPeso_romaneio());
			} else {
				cell.setCellValue(recebimento.getPeso_romaneio());
			}

			if (recebimento.getNf_venda_aplicavel() == 1) {

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				cell.setCellValue(recebimento.getCodigo_nf_venda());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				cell.setCellValue(recebimento.getPeso_nf_venda());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(numberStyle);
				cell.setCellValue(recebimento.getValor_nf_venda().doubleValue());

			} else {

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				cell.setCellValue("Não Aplicável");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				cell.setCellValue("Não Aplicável");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				cell.setCellValue("Não Aplicável");

			}

			if (recebimento.getNf_remessa_aplicavel() == 1) {

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				cell.setCellValue(recebimento.getCodigo_nf_remessa());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				cell.setCellValue(recebimento.getPeso_nf_remessa());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(numberStyle);
				cell.setCellValue(recebimento.getValor_nf_remessa().doubleValue());

			} else {
				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				cell.setCellValue("Não Aplicável");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				cell.setCellValue("Não Aplicável");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				cell.setCellValue("Não Aplicável");

			}
			rownum++;
			ultima_linha = rownum;

		}

		// pular linha
		rownum += 1;

		// somatorias
		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		int celula_soma_peso = rownum + 1;

		// somatoria de pesos
		cell = row.createCell(2);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue("Soma Final:");

		cell = row.createCell(3);
		cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
		cell.setCellType(CellType.FORMULA);
		String formula = "SUM(D" + primeira_linha + ":D" + ultima_linha + ")";
		cell.setCellFormula(formula);

		cell = row.createCell(4);
		cell.setCellStyle(numberStyleFundoBrancoTextoPreto);
		cell.setCellType(CellType.FORMULA);
		formula = "SUM(D" + (rownum + 1) + "/60)";
		cell.setCellFormula(formula);

		// somatoria de peso de nf venda
		cell = row.createCell(5);
		cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
		cell.setCellType(CellType.FORMULA);
		formula = "SUMPRODUCT(SUBTOTAL(9,OFFSET(F" + primeira_linha + ":F" + ultima_linha + ",ROW(F" + primeira_linha
				+ ":F" + ultima_linha + ")-ROW(F" + primeira_linha + "),0,1,1)),-(E" + primeira_linha + ":E"
				+ ultima_linha + "<>\"Não Aplicável\")) * -1";
		cell.setCellFormula(formula);

		// peso de nf venda em sacos
		Row linha_saco_nf_venda = sheet.createRow(rownum + 1);
		cell = linha_saco_nf_venda.createCell(5);
		cell.setCellStyle(numberStyleFundoBrancoTextoPreto);
		cell.setCellType(CellType.FORMULA);
		formula = "SUM(F" + (rownum + 1) + "/60)";
		cell.setCellFormula(formula);

		// somatoria de valor de nf venda
		cell = row.createCell(6);
		cell.setCellStyle(valorStyleFundoVerdeTextoBranco);
		cell.setCellType(CellType.FORMULA);
		formula = "SUMPRODUCT(SUBTOTAL(9,OFFSET(G" + primeira_linha + ":G" + ultima_linha + ",ROW(G" + primeira_linha
				+ ":G" + ultima_linha + ")-ROW(G" + primeira_linha + "),0,1,1)),-(E" + primeira_linha + ":E"
				+ ultima_linha + "<>\"Não Aplicável\")) * -1";
		cell.setCellFormula(formula);

		// somatoria de peso de nf remessa
		cell = row.createCell(8);
		cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
		cell.setCellType(CellType.FORMULA);
		formula = "SUMPRODUCT(SUBTOTAL(9,OFFSET(I" + primeira_linha + ":I" + ultima_linha + ",ROW(I" + primeira_linha
				+ ":I" + ultima_linha + ")-ROW(I" + primeira_linha + "),0,1,1)),-(H" + primeira_linha + ":H"
				+ ultima_linha + "<>\"Não Aplicável\")) * -1";
		cell.setCellFormula(formula);

		// peso de nf remessa em sacos
		cell = linha_saco_nf_venda.createCell(8);
		cell.setCellStyle(numberStyleFundoBrancoTextoPreto);
		cell.setCellType(CellType.FORMULA);
		formula = "SUM(I" + (rownum + 1) + "/60)";
		cell.setCellFormula(formula);

		// somatoria de valor de nf remessa
		cell = row.createCell(9);
		cell.setCellStyle(valorStyleFundoVerdeTextoBranco);
		cell.setCellType(CellType.FORMULA);
		formula = "SUMPRODUCT(SUBTOTAL(9,OFFSET(J" + primeira_linha + ":J" + ultima_linha + ",ROW(J" + primeira_linha
				+ ":J" + ultima_linha + ")-ROW(J" + primeira_linha + "),0,1,1)),-(H" + primeira_linha + ":H"
				+ ultima_linha + "<>\"Não Aplicável\")) * -1";
		cell.setCellFormula(formula);

		rownum += 2;

		// somatoria de valor de peso normal
		row = sheet.createRow(rownum);
		cell = row.createCell(0);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue("Peso Normal:");

		celula_soma_peso = rownum + 1;

		cell = row.createCell(1);
		cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
		cell.setCellType(CellType.FORMULA);
		formula = "SUMPRODUCT(SUBTOTAL(9,OFFSET(D" + primeira_linha + ":D" + ultima_linha + ",ROW(D" + primeira_linha
				+ ":D" + ultima_linha + ")-ROW(D" + primeira_linha + "),0,1,1)),-(C" + primeira_linha + ":C"
				+ ultima_linha + "<>\"-Transferencia\"),-(C" + primeira_linha + ":C" + ultima_linha
				+ "<>\"+Transferencia\"))";
		cell.setCellFormula(formula);

		cell = row.createCell(2);
		cell.setCellStyle(numberStyleFundoBrancoTextoPreto);
		cell.setCellType(CellType.FORMULA);
		formula = "SUM(B" + (rownum + 1) + "/60)";
		cell.setCellFormula(formula);

		int linha_soma_final = 0;

		if (incluir_transferencias_recebimentos) {

			row = sheet.createRow(rownum += 1);
			cellnum = 0;

			// somatoria de pesos
			cell = row.createCell(0);
			cell.setCellStyle(celula_fundo_branco_texto_preto);
			cell.setCellValue("Peso Transferencias(-):");

			cell = row.createCell(1);
			cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
			cell.setCellType(CellType.FORMULA);
			formula = "SUMPRODUCT(SUBTOTAL(9,OFFSET(D" + primeira_linha + ":D" + ultima_linha + ",ROW(D"
					+ primeira_linha + ":D" + ultima_linha + ")-ROW(D" + primeira_linha + "),0,1,1)),-(C"
					+ primeira_linha + ":C" + ultima_linha + "=\"-Transferencia\")) * -1";
			cell.setCellFormula(formula);

			cell = row.createCell(2);
			cell.setCellStyle(numberStyleFundoBrancoTextoPreto);
			cell.setCellType(CellType.FORMULA);
			formula = "SUM(B" + (rownum + 1) + "/60)";
			cell.setCellFormula(formula);

			row = sheet.createRow(rownum += 1);
			cellnum = 0;

			// somatoria de pesos
			cell = row.createCell(0);
			cell.setCellStyle(celula_fundo_branco_texto_preto);
			cell.setCellValue("Peso Transferencias(+):");

			cell = row.createCell(1);
			cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
			cell.setCellType(CellType.FORMULA);
			formula = "SUMPRODUCT(SUBTOTAL(9,OFFSET(D" + primeira_linha + ":D" + ultima_linha + ",ROW(D"
					+ primeira_linha + ":D" + ultima_linha + ")-ROW(D" + primeira_linha + "),0,1,1)),-(C"
					+ primeira_linha + ":C" + ultima_linha + "=\"+Transferencia\")) * -1";
			cell.setCellFormula(formula);

			cell = row.createCell(2);
			cell.setCellStyle(numberStyleFundoBrancoTextoPreto);
			cell.setCellType(CellType.FORMULA);
			formula = "SUM(B" + (rownum + 1) + "/60)";
			cell.setCellFormula(formula);

			row = sheet.createRow(rownum += 1);
			cellnum = 0;

			linha_soma_final = rownum + 1;
			// somatoria de pesos
			cell = row.createCell(0);
			cell.setCellStyle(celula_fundo_branco_texto_preto);
			cell.setCellValue("Soma Final:");

			cell = row.createCell(1);
			cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
			cell.setCellType(CellType.FORMULA);
			formula = "SUM(D" + primeira_linha + ":D" + ultima_linha + ")";
			cell.setCellFormula(formula);

			cell = row.createCell(2);
			cell.setCellStyle(numberStyleFundoBrancoTextoPreto);
			cell.setCellType(CellType.FORMULA);
			formula = "SUM(B" + (rownum + 1) + "/60)";
			cell.setCellFormula(formula);

		}

		// total contratado

		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		int linha_total_contratado = rownum + 1;
		cell = row.createCell(0);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue("Total Contratado:");

		cell = row.createCell(1);
		cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
		cell.setCellValue(soma_total_quantidade_contratos);

		cell = row.createCell(2);
		cell.setCellStyle(numberStyleFundoBrancoTextoPreto);
		cell.setCellType(CellType.FORMULA);
		formula = "SUM(B" + (rownum + 1) + "/60)";
		cell.setCellFormula(formula);

		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		cell = row.createCell(0);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue("Restante:");

		// restante

		if (incluir_transferencias_recebimentos) {
			cell = row.createCell(1);
			cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
			cell.setCellType(CellType.FORMULA);
			formula = "SUM(B" + linha_total_contratado + "-B" + linha_soma_final + ")";
			cell.setCellFormula(formula);
		} else {

			cell = row.createCell(1);
			cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
			cell.setCellType(CellType.FORMULA);
			formula = "SUM(B" + linha_total_contratado + "-B" + celula_soma_peso + ")";
			cell.setCellFormula(formula);
		}
		cell = row.createCell(2);
		cell.setCellStyle(numberStyleFundoBrancoTextoPreto);
		cell.setCellType(CellType.FORMULA);
		formula = "SUM(B" + (rownum + 1) + "/60)";
		cell.setCellFormula(formula);

		for (int i = 0; i < 10; i++) {
			sheet.autoSizeColumn(i);

		}

		DadosTabelaExcel retornar = new DadosTabelaExcel();
		retornar.setWorkbook(workbook);
		retornar.setSheet(sheet);
		rownum += 3;
		retornar.setRownum(rownum);

		return retornar;

	}

	HSSFDataFormat numberFormatCarregamento;
	HSSFFont newFont_brancaCarregamento;
	HSSFFont newFont_pretaCarregamento;
	CellStyle celula_fundo_branco_texto_pretoCarregamento;
	CellStyle celula_fundo_verde_texto_brancoCarregamento;
	CellStyle numberStyleCarregamento;
	CellStyle pesoStyleCarregamento;
	CellStyle numberStyleFundoVerdeTextoBrancoCarregamento;
	CellStyle numberStyleFundoBrancoTextoPretoCarregamento;
	CellStyle valorStyleFundoVerdeTextoBrancoCarregamento;
	HSSFFont newFont_verdeCarregamento;
	CellStyle celula_fundo_branco_texto_verdeCarregamento;
	HSSFFont newFont_vermelhaCarregamento;
	CellStyle celula_fundo_branco_texto_vermelhoCarregamento;
	HSSFFont newFont_azulCarregamento;
	CellStyle celula_fundo_branco_texto_azulCarregamento;

	public void setarEstiloCarregamento(HSSFWorkbook workbook) {
		numberFormatCarregamento = workbook.createDataFormat();

		newFont_brancaCarregamento = workbook.createFont();
		newFont_brancaCarregamento.setBold(true);
		newFont_brancaCarregamento.setColor(IndexedColors.WHITE.getIndex());
		newFont_brancaCarregamento.setFontName("Calibri");
		newFont_brancaCarregamento.setItalic(false);
		newFont_brancaCarregamento.setFontHeight((short) (11 * 20));

		// estilo para cabecalho fundo verde
		celula_fundo_verde_texto_brancoCarregamento = workbook.createCellStyle();
		celula_fundo_verde_texto_brancoCarregamento.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		celula_fundo_verde_texto_brancoCarregamento.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		celula_fundo_verde_texto_brancoCarregamento.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_verde_texto_brancoCarregamento.setVerticalAlignment(VerticalAlignment.CENTER);

		celula_fundo_verde_texto_brancoCarregamento.setFont(newFont_brancaCarregamento);

		newFont_pretaCarregamento = workbook.createFont();
		newFont_pretaCarregamento.setColor(IndexedColors.BLACK.getIndex());
		newFont_pretaCarregamento.setFontName("Calibri");
		newFont_pretaCarregamento.setItalic(false);
		newFont_pretaCarregamento.setFontHeight((short) (11 * 20));

		celula_fundo_branco_texto_pretoCarregamento = workbook.createCellStyle();
		celula_fundo_branco_texto_pretoCarregamento.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_branco_texto_pretoCarregamento.setVerticalAlignment(VerticalAlignment.CENTER);
		celula_fundo_branco_texto_pretoCarregamento.setFont(newFont_pretaCarregamento);

		// celula para numero alinhado ao centro
		numberStyleCarregamento = workbook.createCellStyle();
		numberStyleCarregamento.setDataFormat(numberFormatCarregamento.getFormat("R$ #,##0.00"));
		numberStyleCarregamento.setAlignment(HorizontalAlignment.CENTER);
		numberStyleCarregamento.setVerticalAlignment(VerticalAlignment.CENTER);

		// celula para numero alinhado ao centro
		pesoStyleCarregamento = workbook.createCellStyle();
		pesoStyleCarregamento.setDataFormat(numberFormatCarregamento.getFormat("#,##0.00"));
		pesoStyleCarregamento.setAlignment(HorizontalAlignment.CENTER);
		pesoStyleCarregamento.setVerticalAlignment(VerticalAlignment.CENTER);

		numberStyleFundoVerdeTextoBrancoCarregamento = workbook.createCellStyle();
		numberStyleFundoVerdeTextoBrancoCarregamento.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		numberStyleFundoVerdeTextoBrancoCarregamento.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		numberStyleFundoVerdeTextoBrancoCarregamento.setAlignment(HorizontalAlignment.CENTER);
		numberStyleFundoVerdeTextoBrancoCarregamento.setVerticalAlignment(VerticalAlignment.CENTER);
		numberStyleFundoVerdeTextoBrancoCarregamento.setDataFormat(numberFormatCarregamento.getFormat("#,##0.00"));

		numberStyleFundoVerdeTextoBrancoCarregamento.setFont(newFont_brancaCarregamento);

		numberStyleFundoBrancoTextoPretoCarregamento = workbook.createCellStyle();
		numberStyleFundoBrancoTextoPretoCarregamento.setAlignment(HorizontalAlignment.LEFT);
		numberStyleFundoBrancoTextoPretoCarregamento.setVerticalAlignment(VerticalAlignment.CENTER);
		numberStyleFundoBrancoTextoPretoCarregamento.setDataFormat(numberFormatCarregamento.getFormat("#,##0.00"));
		numberStyleFundoBrancoTextoPretoCarregamento.setFont(newFont_pretaCarregamento);

		valorStyleFundoVerdeTextoBrancoCarregamento = workbook.createCellStyle();
		valorStyleFundoVerdeTextoBrancoCarregamento.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		valorStyleFundoVerdeTextoBrancoCarregamento.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		valorStyleFundoVerdeTextoBrancoCarregamento.setAlignment(HorizontalAlignment.CENTER);
		valorStyleFundoVerdeTextoBrancoCarregamento.setVerticalAlignment(VerticalAlignment.CENTER);
		valorStyleFundoVerdeTextoBrancoCarregamento.setDataFormat(numberFormatCarregamento.getFormat("R$ #,##0.00"));
		valorStyleFundoVerdeTextoBrancoCarregamento.setFont(newFont_brancaCarregamento);

		// celular de contrato normal
		newFont_verdeCarregamento = workbook.createFont();
		newFont_verdeCarregamento.setColor(IndexedColors.GREEN.getIndex());
		newFont_verdeCarregamento.setFontName("Calibri");
		newFont_verdeCarregamento.setItalic(false);
		newFont_verdeCarregamento.setFontHeight((short) (11 * 20));

		celula_fundo_branco_texto_verdeCarregamento = workbook.createCellStyle();
		celula_fundo_branco_texto_verdeCarregamento.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_branco_texto_verdeCarregamento.setVerticalAlignment(VerticalAlignment.CENTER);
		celula_fundo_branco_texto_verdeCarregamento.setFont(newFont_verdeCarregamento);

		// celular de sub-contrato n
		newFont_vermelhaCarregamento = workbook.createFont();
		newFont_vermelhaCarregamento.setColor(IndexedColors.RED.getIndex());
		newFont_vermelhaCarregamento.setFontName("Calibri");
		newFont_vermelhaCarregamento.setItalic(false);
		newFont_vermelhaCarregamento.setFontHeight((short) (11 * 20));

		celula_fundo_branco_texto_vermelhoCarregamento = workbook.createCellStyle();
		celula_fundo_branco_texto_vermelhoCarregamento.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_branco_texto_vermelhoCarregamento.setVerticalAlignment(VerticalAlignment.CENTER);
		celula_fundo_branco_texto_vermelhoCarregamento.setFont(newFont_vermelhaCarregamento);

		// celula de ganho potencial

		// celular de sub-contrato n
		newFont_azulCarregamento = workbook.createFont();
		newFont_azulCarregamento.setColor(IndexedColors.BLUE.getIndex());
		newFont_azulCarregamento.setFontName("Calibri");
		newFont_azulCarregamento.setItalic(false);
		newFont_azulCarregamento.setFontHeight((short) (11 * 20));

		celula_fundo_branco_texto_azulCarregamento = workbook.createCellStyle();
		celula_fundo_branco_texto_azulCarregamento.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_branco_texto_azulCarregamento.setVerticalAlignment(VerticalAlignment.CENTER);
		celula_fundo_branco_texto_azulCarregamento.setFont(newFont_azulCarregamento);
	}

	public DadosTabelaExcel criarTabelaCarregamentosExcel(HSSFWorkbook workbook, HSSFSheet sheet, int rownum,
			CadastroContrato contrato, ArrayList<CarregamentoCompleto> carregamentos,
			double soma_total_quantidade_contratos, double soma_total_quantidade_recebidas,
			ArrayList<CadastroContrato.CadastroTransferenciaCarga> transferencias_remetentes,
			ArrayList<CadastroContrato.CadastroTransferenciaCarga> transferencias_destinatarios) {
		// XWPFParagraph par = document_global.createParagraph();

		NumberFormat z = NumberFormat.getNumberInstance();

		Locale ptBr = new Locale("pt", "BR");

		int cellnum = 0;

		Cell cell;
		Row row;
		// compradores x vendedores

		double quantidade_kg = 0;
		double quantidade_sacos = 0;

		if (contrato.getMedida().equalsIgnoreCase("KG")) {
			quantidade_kg = contrato.getQuantidade();
			quantidade_sacos = quantidade_kg / 60;
		} else if (contrato.getMedida().equalsIgnoreCase("Sacos")) {
			quantidade_sacos = contrato.getQuantidade();
			quantidade_kg = quantidade_sacos * 60;
		}

		// safra
		String safra = contrato.getModelo_safra().getProduto().getNome_produto() + " "
				+ contrato.getModelo_safra().getProduto().getTransgenia() + " "
				+ contrato.getModelo_safra().getAno_plantio() + "/" + contrato.getModelo_safra().getAno_colheita();

		String texto_info_contrato = "CTR: " + contrato.getCodigo() + " " + safra + " Quantidade Total: "
				+ z.format(quantidade_kg) + " kgs | " + z.format(quantidade_sacos) + " sacos "
				+ NumberFormat.getCurrencyInstance(ptBr).format(contrato.getValor_produto()) + " por "
				+ contrato.getMedida() + " totalizando: "
				+ NumberFormat.getCurrencyInstance(ptBr).format(contrato.getValor_a_pagar().doubleValue());

		row = sheet.createRow(rownum);
		cell = row.createCell(cellnum);
		cell.setCellStyle(celula_fundo_branco_texto_pretoRecebimentos);
		cell.setCellValue(texto_info_contrato);
		sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, cellnum, 10));

		rownum++;

		// linha com nome compradores x vendedores

		CadastroCliente compradores[] = contrato.getCompradores();
		CadastroCliente vendedores[] = contrato.getVendedores();

		String nome_vendedores_contrato = "";
		String nome_compradores_contrato = "";

		if (compradores[0] != null) {
			if (compradores[0].getTipo_pessoa() == 0) {
				// pessoa fisica
				nome_compradores_contrato = compradores[0].getNome_empresarial();
			} else {
				nome_compradores_contrato = compradores[0].getNome_fantaia();

			}
		}
		if (compradores[1] != null) {
			if (compradores[1].getTipo_pessoa() == 0) {
				// pessoa fisica
				nome_compradores_contrato = nome_compradores_contrato + ", " + compradores[1].getNome_empresarial();
			} else {
				nome_compradores_contrato = nome_compradores_contrato + ", " + compradores[1].getNome_fantaia();

			}
		}
		if (vendedores[0] != null) {
			if (vendedores[0].getTipo_pessoa() == 0) {
				nome_vendedores_contrato = vendedores[0].getNome_empresarial();
			} else {
				nome_vendedores_contrato = vendedores[0].getNome_fantaia();
			}
		}

		if (vendedores[1] != null) {
			if (vendedores[1].getTipo_pessoa() == 0) {
				nome_vendedores_contrato = nome_vendedores_contrato + ", " + vendedores[1].getNome_empresarial();
			} else {
				nome_vendedores_contrato = nome_vendedores_contrato + ", " + vendedores[1].getNome_fantaia();
			}
		}

		String linha_nome_compradores_vendedores = contrato.getNomes_compradores().toUpperCase() + " X "
				+ contrato.getNomes_vendedores().toUpperCase();

		row = sheet.createRow(rownum);
		cell = row.createCell(cellnum);
		cell.setCellStyle(celula_fundo_branco_texto_pretoRecebimentos);
		cell.setCellValue(linha_nome_compradores_vendedores);
		sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, cellnum, 10));

		rownum++;

		row = sheet.createRow(rownum);
		cellnum = 0;
		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_brancoCarregamento);
		cell.setCellValue("CONTRATO");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_brancoCarregamento);
		cell.setCellValue("DATA");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_brancoCarregamento);
		cell.setCellValue("CLIENTE");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_brancoCarregamento);
		cell.setCellValue("VENDEDOR");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_brancoCarregamento);
		cell.setCellValue("TRANSPORTADOR");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_brancoCarregamento);
		cell.setCellValue("VEICULO");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_brancoCarregamento);
		cell.setCellValue("PRODUTO");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_brancoCarregamento);
		cell.setCellValue("CODIGO ROMANEIO");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_brancoCarregamento);
		cell.setCellValue("PESO ROMANEIO:".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_brancoCarregamento);
		cell.setCellValue("NF1");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_brancoCarregamento);
		cell.setCellValue("PESO NF1");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_brancoCarregamento);
		cell.setCellValue("VALOR NF 1");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_brancoCarregamento);
		cell.setCellValue("NF2");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_brancoCarregamento);
		cell.setCellValue("PESO NF2");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_brancoCarregamento);
		cell.setCellValue("VALOR NF2");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_brancoCarregamento);
		cell.setCellValue("DIFERENÇA");

		int primeira_linha = rownum++;
		int ultima_linha = primeira_linha;

		// fazer checkagens

		boolean nf_interna_ativo = false;
		boolean nf_venda_ativo = false;
		boolean nf_complemento_ativo = false;

		// checka se ha no minimo uma nf interna aplicavel
		for (CadastroContrato.Carregamento carregamento : carregamentos) {
			if (carregamento.getNf_interna_aplicavel() == 1) {
				nf_interna_ativo = true;
				break;
			}

		}

		// checka se ha no minimo uma nf venda aplicavel
		for (CadastroContrato.Carregamento carregamento : carregamentos) {
			if (carregamento.getNf_venda1_aplicavel() == 1) {
				nf_venda_ativo = true;
				break;
			}

		}

		// checka se ha no minimo uma nf complemento aplicavel
		for (CadastroContrato.Carregamento carregamento : carregamentos) {
			if (carregamento.getNf_complemento_aplicavel() == 1) {
				nf_complemento_ativo = true;
				break;
			}

		}

		for (CarregamentoCompleto carregamento : carregamentos) {

			cellnum = 0;
			row = sheet.createRow(rownum);

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
			cell.setCellValue(carregamento.getContrato().getCodigo());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
			cell.setCellValue(carregamento.getData());

			// pegar cliente

			String nome_cliente = carregamento.getCliente_carregamento();
			String nome_cliente_completo = carregamento.getCliente_carregamento();

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

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
			cell.setCellValue(nome_cliente);

			// pegar vendedor

			String nome_vendedor = carregamento.getVendedor_carregamento();

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

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
			cell.setCellValue(nome_vendedor);

			// transportador
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
			cell.setCellValue(carregamento.getNome_motorista());

			// veiculo
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
			cell.setCellValue(carregamento.getPlaca());

			// produto
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
			cell.setCellValue(carregamento.getContrato().getModelo_produto().getNome_produto());

			// romaneio
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
			String codigo = carregamento.getCodigo_romaneio();
			if (codigo.equalsIgnoreCase("-Transferencia")) {
				cell.setCellValue("-Transferencia");

			} else if (codigo.equalsIgnoreCase("+Transferencia")) {
				cell.setCellValue("+Transferencia");
			} else {
				cell.setCellValue(codigo);
			}

			cell = row.createCell(cellnum++);
			cell.setCellStyle(pesoStyleCarregamento);
			if (codigo.equalsIgnoreCase("-Transferencia")) {
				cell.setCellValue(carregamento.getPeso_romaneio() * -1);

			} else if (codigo.equalsIgnoreCase("+Transferencia")) {
				cell.setCellValue(carregamento.getPeso_romaneio());
			} else {
				cell.setCellValue(carregamento.getPeso_romaneio());
			}

			// nfs
			// codigos
			String codigo_romaneio = "";
			String codigo_nf_venda1 = "", codigo_nf_complemento = "";
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
			} catch (Exception e) {
				// //JOptionPane.showMessageDialog(isto, "Romaneio não Localizado");
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
						} catch (Exception e) {
							valor_nf_venda1 = BigDecimal.ZERO;
						}

					} else {
						codigo_nf_venda1 = carregamento.getCodigo_nf_venda1();
						peso_nf_venda1 = carregamento.getPeso_nf_venda1();
						valor_nf_venda1 = carregamento.getValor_nf_venda1();

					}

				}
			} catch (Exception e) {
				// //JOptionPane.showMessageDialog(isto, "Nota Fiscal de venda não Localizado");
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
						} catch (Exception e) {
							valor_nf_complemento = BigDecimal.ZERO;
						}

					} else {
						codigo_nf_complemento = carregamento.getCodigo_nf_complemento();
						peso_nf_complemento = carregamento.getPeso_nf_complemento();
						valor_nf_complemento = carregamento.getValor_nf_complemento();

					}

				}
			} catch (Exception e) {
				// //JOptionPane.showMessageDialog(isto, "Nota Fiscal de remessa não
				// Localizado");

				codigo_nf_complemento = carregamento.getCodigo_nf_complemento();
				peso_nf_complemento = carregamento.getPeso_nf_complemento();
				valor_nf_complemento = carregamento.getValor_nf_complemento();

			}

			// nfvenda1
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
			if (carregamento.getNf_venda1_aplicavel() == 1)
				cell.setCellValue(codigo_nf_venda1);
			else
				cell.setCellValue("Não Aplicável");

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
			if (carregamento.getNf_venda1_aplicavel() == 1) {
				cell.setCellValue(z.format(peso_nf_venda1));
			} else {
				cell.setCellValue("Não Aplicável");
			}

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
			if (carregamento.getNf_venda1_aplicavel() == 1) {
				cell.setCellValue(valor_nf_venda1.doubleValue());
			} else {
				cell.setCellValue("Não Aplicável");
			}

			// nfcomplemento
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
			if (carregamento.getNf_complemento_aplicavel() == 1)
				cell.setCellValue(codigo_nf_complemento);
			else
				cell.setCellValue("Não Aplicável");

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
			if (carregamento.getNf_complemento_aplicavel() == 1) {
				cell.setCellValue(z.format(peso_nf_complemento));
			} else {
				cell.setCellValue("Não Aplicável");
			}

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
			if (carregamento.getNf_complemento_aplicavel() == 1) {
				cell.setCellValue(valor_nf_complemento.doubleValue());
			} else {
				cell.setCellValue("Não Aplicável");
			}

			// diferenca
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
			if (carregamento.getNf_venda1_aplicavel() == 1 && carregamento.getNf_complemento_aplicavel() == 1) {
				cell.setCellValue(peso_romaneio - (peso_nf_complemento + peso_nf_venda1));
			} else {
				cell.setCellValue("Não Aplicável");
			}

			rownum++;
			ultima_linha = rownum;

		}

		if (incluir_transferencias_carregamentos) {
			// transferencias negativas
			/*************************** transferencias negativas *****************///////////

			for (CadastroContrato.CadastroTransferenciaCarga transferencia : transferencias_remetentes) {

				/*
				 * codigo compradores vendedores status quantidade medida produto transgenia
				 * safra valor_produto valor_total data_contrato local_retirada
				 */

				String texto_detalhado = "";

				GerenciarBancoContratos gerencia_contratos = new GerenciarBancoContratos();
				CadastroContrato remetente = gerencia_contratos.getContrato(transferencia.getId_contrato_remetente());
				CadastroContrato destinatario = gerencia_contratos
						.getContrato(transferencia.getId_contrato_destinatario());
				// CadastroContrato.Carregamento carga = gerencia_contratos
				// .getCarregamento(transferencia.getId_carregamento_remetente());

				CadastroCliente compradores_trans[] = destinatario.getCompradores();
				CadastroCliente vendedores_trans[] = destinatario.getVendedores();

				String nome_vendedores = "";
				String nome_compradores = "";

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

				cellnum = 0;
				row = sheet.createRow(rownum);

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
				cell.setCellValue(remetente.getCodigo());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
				cell.setCellValue(transferencia.getData());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
				cell.setCellValue(remetente.getCodigo());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
				cell.setCellValue(destinatario.getCodigo());

				// transportador
				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
				cell.setCellValue("");

				// veiculo
				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
				cell.setCellValue("");

				// produto
				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
				cell.setCellValue("");

				// romaneio
				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
				cell.setCellValue("-Transferencia");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(pesoStyleCarregamento);
				cell.setCellValue(Double.parseDouble(transferencia.getQuantidade()) * -1);

				// nfvenda1
				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
				cell.setCellValue("Não Aplicável");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
				cell.setCellValue("Não Aplicável");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
				cell.setCellValue("Não Aplicável");

				// nfcomplemento
				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
				cell.setCellValue("Não Aplicável");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
				cell.setCellValue("Não Aplicável");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
				cell.setCellValue("Não Aplicável");

				// diferenca
				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
				cell.setCellValue("Não Aplicável");

				rownum++;
				ultima_linha = rownum;
			}
		}
		/*************************** transferencias negativas *****************///////////

		// transfereicas positivas
		/*************************** transferencias positivas *****************///////////
		if (incluir_transferencias_carregamentos) {
			for (CadastroContrato.CadastroTransferenciaCarga transferencia : transferencias_destinatarios) {

				String texto_detalhado = "";

				GerenciarBancoContratos gerencia_contratos = new GerenciarBancoContratos();
				CadastroContrato remetente = gerencia_contratos.getContrato(transferencia.getId_contrato_remetente());
				CadastroContrato destinatario = gerencia_contratos
						.getContrato(transferencia.getId_contrato_destinatario());
				// CadastroContrato.Carregamento carga = gerencia_contratos
				// .getCarregamento(transferencia.getId_carregamento_remetente());

				CadastroCliente compradores_trans[] = destinatario.getCompradores();
				CadastroCliente vendedores_trans[] = destinatario.getVendedores();

				String nome_vendedores = "";
				String nome_compradores = "";

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
						nome_vendedores += ",";

					}
				}

				double quantidade = Double.parseDouble(transferencia.getQuantidade());

				texto_detalhado = "Transferência Positiva: Recebimento de volume de " + z.format(quantidade) + " kgs | "
						+ z.format(quantidade / 60) + " sacos recebidos do contrato ";
				texto_detalhado = texto_detalhado + remetente.getCodigo() + " " + nome_compradores + " X "
						+ nome_vendedores + " " + z.format(remetente.getQuantidade()) + " " + remetente.getMedida()
						+ " de " + remetente.getModelo_safra().getProduto().getNome_produto() + " "
						+ remetente.getModelo_safra().getProduto().getTransgenia() + " da safra "
						+ remetente.getModelo_safra().getAno_plantio() + "/"
						+ remetente.getModelo_safra().getAno_colheita();
				texto_detalhado = texto_detalhado + "";

				cellnum = 0;
				row = sheet.createRow(rownum);

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
				cell.setCellValue(destinatario.getCodigo());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
				cell.setCellValue(transferencia.getData());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
				cell.setCellValue(destinatario.getCodigo());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
				cell.setCellValue(remetente.getCodigo());

				// transportador
				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
				cell.setCellValue("");

				// veiculo
				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
				cell.setCellValue("");

				// produto
				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
				cell.setCellValue("");

				// romaneio
				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
				cell.setCellValue("+Transferencia");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(pesoStyleCarregamento);
				cell.setCellValue(Double.parseDouble(transferencia.getQuantidade()));

				// nfvenda1
				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
				cell.setCellValue("Não Aplicável");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
				cell.setCellValue("Não Aplicável");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
				cell.setCellValue("Não Aplicável");

				// nfcomplemento
				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
				cell.setCellValue("Não Aplicável");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
				cell.setCellValue("Não Aplicável");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
				cell.setCellValue("Não Aplicável");

				// diferenca
				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
				cell.setCellValue("Não Aplicável");

				rownum++;
				ultima_linha = rownum;
			}
		}

		/*************************** transferencias positivas *****************///////////

		// pular linha
		rownum += 1;

		// somatorias
		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		int celula_soma_peso = rownum + 1;

		// somatoria de pesos
		cell = row.createCell(7);
		cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
		cell.setCellValue("Soma Final:");

		cell = row.createCell(8);
		cell.setCellStyle(numberStyleFundoVerdeTextoBrancoCarregamento);
		cell.setCellType(CellType.FORMULA);
		String formula = "SUM(I" + primeira_linha + ":I" + ultima_linha + ")";
		cell.setCellFormula(formula);

		cell = row.createCell(9);
		cell.setCellStyle(numberStyleFundoBrancoTextoPretoCarregamento);
		cell.setCellType(CellType.FORMULA);
		formula = "SUM(I" + (rownum + 1) + "/60)";
		cell.setCellFormula(formula);

		// somatoria de peso de nf venda 1
		cell = row.createCell(10);
		cell.setCellStyle(numberStyleFundoVerdeTextoBrancoCarregamento);
		cell.setCellType(CellType.FORMULA);
		formula = "SUMPRODUCT(SUBTOTAL(9,OFFSET(K" + primeira_linha + ":K" + ultima_linha + ",ROW(K" + primeira_linha
				+ ":K" + ultima_linha + ")-ROW(K" + primeira_linha + "),0,1,1)),-(J" + primeira_linha + ":J"
				+ ultima_linha + "<>\"Não Aplicável\")) * -1";
		cell.setCellFormula(formula);

		// somatoria de valor de nf venda 1
		cell = row.createCell(11);
		cell.setCellStyle(numberStyleFundoVerdeTextoBrancoCarregamento);
		cell.setCellType(CellType.FORMULA);
		formula = "SUMPRODUCT(SUBTOTAL(9,OFFSET(L" + primeira_linha + ":L" + ultima_linha + ",ROW(L" + primeira_linha
				+ ":L" + ultima_linha + ")-ROW(L" + primeira_linha + "),0,1,1)),-(J" + primeira_linha + ":J"
				+ ultima_linha + "<>\"Não Aplicável\")) * -1";
		cell.setCellFormula(formula);

		// somatoria de PESO de nf venda 2
		cell = row.createCell(13);
		cell.setCellStyle(numberStyleFundoVerdeTextoBrancoCarregamento);
		cell.setCellType(CellType.FORMULA);
		formula = "SUMPRODUCT(SUBTOTAL(9,OFFSET(N" + primeira_linha + ":N" + ultima_linha + ",ROW(N" + primeira_linha
				+ ":N" + ultima_linha + ")-ROW(N" + primeira_linha + "),0,1,1)),-(M" + primeira_linha + ":M"
				+ ultima_linha + "<>\"Não Aplicável\")) * -1";
		cell.setCellFormula(formula);

		// somatoria de valor de nf venda 2
		cell = row.createCell(14);
		cell.setCellStyle(numberStyleFundoVerdeTextoBrancoCarregamento);
		cell.setCellType(CellType.FORMULA);
		formula = "SUMPRODUCT(SUBTOTAL(9,OFFSET(O" + primeira_linha + ":O" + ultima_linha + ",ROW(O" + primeira_linha
				+ ":O" + ultima_linha + ")-ROW(O" + primeira_linha + "),0,1,1)),-(M" + primeira_linha + ":M"
				+ ultima_linha + "<>\"Não Aplicável\")) * -1";
		cell.setCellFormula(formula);

		int linha_soma_final = 0;

		if (incluir_transferencias_carregamentos) {

			row = sheet.createRow(rownum += 1);
			cellnum = 0;

			// somatoria de pesos
			cell = row.createCell(0);
			cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
			cell.setCellValue("Peso Transferencias(-):");

			cell = row.createCell(1);
			cell.setCellStyle(numberStyleFundoVerdeTextoBrancoCarregamento);
			cell.setCellType(CellType.FORMULA);
			formula = "SUMPRODUCT(SUBTOTAL(9,OFFSET(I" + primeira_linha + ":I" + ultima_linha + ",ROW(I"
					+ primeira_linha + ":I" + ultima_linha + ")-ROW(I" + primeira_linha + "),0,1,1)),-(H"
					+ primeira_linha + ":H" + ultima_linha + "=\"-Transferencia\")) * -1";
			cell.setCellFormula(formula);

			cell = row.createCell(2);
			cell.setCellStyle(numberStyleFundoBrancoTextoPretoCarregamento);
			cell.setCellType(CellType.FORMULA);
			formula = "SUM(B" + (rownum + 1) + "/60)";
			cell.setCellFormula(formula);

			row = sheet.createRow(rownum += 1);
			cellnum = 0;

			// somatoria de pesos
			cell = row.createCell(0);
			cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
			cell.setCellValue("Peso Transferencias(+):");

			cell = row.createCell(1);
			cell.setCellStyle(numberStyleFundoVerdeTextoBrancoCarregamento);
			cell.setCellType(CellType.FORMULA);
			formula = "SUMPRODUCT(SUBTOTAL(9,OFFSET(I" + primeira_linha + ":I" + ultima_linha + ",ROW(I"
					+ primeira_linha + ":I" + ultima_linha + ")-ROW(I" + primeira_linha + "),0,1,1)),-(H"
					+ primeira_linha + ":H" + ultima_linha + "=\"+Transferencia\")) * -1";
			cell.setCellFormula(formula);

			cell = row.createCell(2);
			cell.setCellStyle(numberStyleFundoBrancoTextoPretoCarregamento);
			cell.setCellType(CellType.FORMULA);
			formula = "SUM(B" + (rownum + 1) + "/60)";
			cell.setCellFormula(formula);

		}

		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		linha_soma_final = rownum + 1;
		// somatoria de pesos
		cell = row.createCell(0);
		cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
		cell.setCellValue("Soma Final:");

		cell = row.createCell(1);
		cell.setCellStyle(numberStyleFundoVerdeTextoBrancoCarregamento);
		cell.setCellType(CellType.FORMULA);
		formula = "SUM(I" + primeira_linha + ":I" + ultima_linha + ")";
		cell.setCellFormula(formula);

		cell = row.createCell(2);
		cell.setCellStyle(numberStyleFundoBrancoTextoPretoCarregamento);
		cell.setCellType(CellType.FORMULA);
		formula = "SUM(B" + (rownum + 1) + "/60)";
		cell.setCellFormula(formula);

		// total contratado

		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		int linha_total_contratado = rownum + 1;
		cell = row.createCell(0);
		cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
		cell.setCellValue("Total Contratado:");

		cell = row.createCell(1);
		cell.setCellStyle(numberStyleFundoVerdeTextoBrancoCarregamento);
		cell.setCellValue(quantidade_kg);

		cell = row.createCell(2);
		cell.setCellStyle(numberStyleFundoBrancoTextoPretoCarregamento);
		cell.setCellType(CellType.FORMULA);
		formula = "SUM(B" + (rownum + 1) + "/60)";
		cell.setCellFormula(formula);

		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		cell = row.createCell(0);
		cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
		cell.setCellValue("Restante:");

		// restante

		if (incluir_transferencias_carregamentos) {
			cell = row.createCell(1);
			cell.setCellStyle(numberStyleFundoVerdeTextoBrancoCarregamento);
			cell.setCellType(CellType.FORMULA);
			formula = "SUM(B" + linha_total_contratado + "-B" + linha_soma_final + ")";
			cell.setCellFormula(formula);
		} else {

			cell = row.createCell(1);
			cell.setCellStyle(numberStyleFundoVerdeTextoBrancoCarregamento);
			cell.setCellType(CellType.FORMULA);
			formula = "SUM(B" + linha_total_contratado + "-B" + celula_soma_peso + ")";
			cell.setCellFormula(formula);
		}
		cell = row.createCell(2);
		cell.setCellStyle(numberStyleFundoBrancoTextoPretoCarregamento);
		cell.setCellType(CellType.FORMULA);
		formula = "SUM(B" + (rownum + 1) + "/60)";
		cell.setCellFormula(formula);

		// restante baseado no que foi recebido

		row = sheet.createRow(rownum += 2);
		cellnum = 0;

		int linha_total_recebido = rownum + 1;
		cell = row.createCell(0);
		cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
		cell.setCellValue("Total Recebido:");

		cell = row.createCell(1);
		cell.setCellStyle(numberStyleFundoVerdeTextoBrancoCarregamento);
		cell.setCellValue(soma_total_quantidade_recebidas);

		cell = row.createCell(2);
		cell.setCellStyle(numberStyleFundoBrancoTextoPretoCarregamento);
		cell.setCellType(CellType.FORMULA);
		formula = "SUM(B" + (rownum + 1) + "/60)";
		cell.setCellFormula(formula);

		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		cell = row.createCell(0);
		cell.setCellStyle(celula_fundo_branco_texto_pretoCarregamento);
		cell.setCellValue("Restante:");

		// restante

		if (incluir_transferencias_carregamentos) {
			cell = row.createCell(1);
			cell.setCellStyle(numberStyleFundoVerdeTextoBrancoCarregamento);
			cell.setCellType(CellType.FORMULA);
			formula = "SUM(B" + linha_total_recebido + "-B" + linha_soma_final + ")";
			cell.setCellFormula(formula);
		} else {

			cell = row.createCell(1);
			cell.setCellStyle(numberStyleFundoVerdeTextoBrancoCarregamento);
			cell.setCellType(CellType.FORMULA);
			formula = "SUM(B" + linha_total_recebido + "-B" + celula_soma_peso + ")";
			cell.setCellFormula(formula);
		}
		cell = row.createCell(2);
		cell.setCellStyle(numberStyleFundoBrancoTextoPretoCarregamento);
		cell.setCellType(CellType.FORMULA);
		formula = "SUM(B" + (rownum + 1) + "/60)";
		cell.setCellFormula(formula);

		for (int i = 0; i < 16; i++) {
			sheet.autoSizeColumn(i);

		}

		DadosTabelaExcel retornar = new DadosTabelaExcel();
		retornar.setWorkbook(workbook);
		retornar.setSheet(sheet);
		rownum += 3;
		retornar.setRownum(rownum);

		return retornar;

	}

	public DadosTabelaExcel criarTabelaCarregamentosUnidosExcel(HSSFWorkbook workbook, HSSFSheet sheet, int rownum,
			ArrayList<CarregamentoCompleto> carregamentos, double soma_total_quantidade_contratos,
			double soma_total_quantidade_recebidas,
			ArrayList<CadastroContrato.CadastroTransferenciaCarga> transferencias_remetentes,
			ArrayList<CadastroContrato.CadastroTransferenciaCarga> transferencias_destinatarios) {
		// XWPFParagraph par = document_global.createParagraph();

		NumberFormat z = NumberFormat.getNumberInstance();

		Locale ptBr = new Locale("pt", "BR");

		HSSFDataFormat numberFormat = workbook.createDataFormat();

		HSSFFont newFont_branca = workbook.createFont();
		newFont_branca.setBold(true);
		newFont_branca.setColor(IndexedColors.WHITE.getIndex());
		newFont_branca.setFontName("Calibri");
		newFont_branca.setItalic(false);
		newFont_branca.setFontHeight((short) (11 * 20));

		// estilo para cabecalho fundo verde
		CellStyle celula_fundo_verde_texto_branco = workbook.createCellStyle();
		celula_fundo_verde_texto_branco.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		celula_fundo_verde_texto_branco.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		celula_fundo_verde_texto_branco.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_verde_texto_branco.setVerticalAlignment(VerticalAlignment.CENTER);

		celula_fundo_verde_texto_branco.setFont(newFont_branca);

		HSSFFont newFont_preta = workbook.createFont();
		newFont_preta.setColor(IndexedColors.BLACK.getIndex());
		newFont_preta.setFontName("Calibri");
		newFont_preta.setItalic(false);
		newFont_preta.setFontHeight((short) (11 * 20));

		CellStyle celula_fundo_branco_texto_preto = workbook.createCellStyle();
		celula_fundo_branco_texto_preto.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_branco_texto_preto.setVerticalAlignment(VerticalAlignment.CENTER);
		celula_fundo_branco_texto_preto.setFont(newFont_preta);

		// celula para numero alinhado ao centro
		CellStyle numberStyle = workbook.createCellStyle();
		numberStyle.setDataFormat(numberFormat.getFormat("R$ #,##0.00"));
		numberStyle.setAlignment(HorizontalAlignment.CENTER);
		numberStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		// celula para numero alinhado ao centro
		CellStyle pesoStyle = workbook.createCellStyle();
		pesoStyle.setDataFormat(numberFormat.getFormat("#,##0.00"));
		pesoStyle.setAlignment(HorizontalAlignment.CENTER);
		pesoStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle numberStyleFundoVerdeTextoBranco = workbook.createCellStyle();
		numberStyleFundoVerdeTextoBranco.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		numberStyleFundoVerdeTextoBranco.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		numberStyleFundoVerdeTextoBranco.setAlignment(HorizontalAlignment.CENTER);
		numberStyleFundoVerdeTextoBranco.setVerticalAlignment(VerticalAlignment.CENTER);
		numberStyleFundoVerdeTextoBranco.setDataFormat(numberFormat.getFormat("#,##0.00"));

		numberStyleFundoVerdeTextoBranco.setFont(newFont_branca);

		CellStyle numberStyleFundoBrancoTextoPreto = workbook.createCellStyle();
		numberStyleFundoBrancoTextoPreto.setAlignment(HorizontalAlignment.LEFT);
		numberStyleFundoBrancoTextoPreto.setVerticalAlignment(VerticalAlignment.CENTER);
		numberStyleFundoBrancoTextoPreto.setDataFormat(numberFormat.getFormat("#,##0.00"));
		numberStyleFundoBrancoTextoPreto.setFont(newFont_preta);

		CellStyle valorStyleFundoVerdeTextoBranco = workbook.createCellStyle();
		valorStyleFundoVerdeTextoBranco.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		valorStyleFundoVerdeTextoBranco.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		valorStyleFundoVerdeTextoBranco.setAlignment(HorizontalAlignment.CENTER);
		valorStyleFundoVerdeTextoBranco.setVerticalAlignment(VerticalAlignment.CENTER);
		valorStyleFundoVerdeTextoBranco.setDataFormat(numberFormat.getFormat("R$ #,##0.00"));
		valorStyleFundoVerdeTextoBranco.setFont(newFont_branca);

		// celular de contrato normal
		HSSFFont newFont_verde = workbook.createFont();
		newFont_verde.setColor(IndexedColors.GREEN.getIndex());
		newFont_verde.setFontName("Calibri");
		newFont_verde.setItalic(false);
		newFont_verde.setFontHeight((short) (11 * 20));

		CellStyle celula_fundo_branco_texto_verde = workbook.createCellStyle();
		celula_fundo_branco_texto_verde.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_branco_texto_verde.setVerticalAlignment(VerticalAlignment.CENTER);
		celula_fundo_branco_texto_verde.setFont(newFont_verde);

		// celular de sub-contrato n
		HSSFFont newFont_vermelha = workbook.createFont();
		newFont_vermelha.setColor(IndexedColors.RED.getIndex());
		newFont_vermelha.setFontName("Calibri");
		newFont_vermelha.setItalic(false);
		newFont_vermelha.setFontHeight((short) (11 * 20));

		CellStyle celula_fundo_branco_texto_vermelho = workbook.createCellStyle();
		celula_fundo_branco_texto_vermelho.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_branco_texto_vermelho.setVerticalAlignment(VerticalAlignment.CENTER);
		celula_fundo_branco_texto_vermelho.setFont(newFont_vermelha);

		// celula de ganho potencial

		// celular de sub-contrato n
		HSSFFont newFont_azul = workbook.createFont();
		newFont_azul.setColor(IndexedColors.BLUE.getIndex());
		newFont_azul.setFontName("Calibri");
		newFont_azul.setItalic(false);
		newFont_azul.setFontHeight((short) (11 * 20));

		CellStyle celula_fundo_branco_texto_azul = workbook.createCellStyle();
		celula_fundo_branco_texto_azul.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_branco_texto_azul.setVerticalAlignment(VerticalAlignment.CENTER);
		celula_fundo_branco_texto_azul.setFont(newFont_azul);

		String texto_contratados = "Quantidade Total Contratada: " + z.format(soma_total_quantidade_contratos)
				+ " kgs | " + z.format(soma_total_quantidade_contratos / 60) + " sacos";

		String texto_recebidos = "Quantidade Total Recebida: " + z.format(soma_total_quantidade_recebidas) + " kgs | "
				+ z.format(soma_total_quantidade_recebidas / 60) + " sacos";

		int cellnum = 0;

		Cell cell;
		Row row;

		row = sheet.createRow(rownum);
		cell = row.createCell(cellnum);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue(texto_contratados);
		sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, cellnum, 10));

		rownum++;

		cellnum = 0;
		row = sheet.createRow(rownum);
		cell = row.createCell(cellnum);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue(texto_recebidos);
		sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, cellnum, 10));

		rownum++;

		row = sheet.createRow(rownum);
		cellnum = 0;
		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("CONTRATO");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("DATA");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("CLIENTE");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("VENDEDOR");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("TRANSPORTADOR");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("VEICULO");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("PRODUTO");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("CODIGO ROMANEIO");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("PESO ROMANEIO:".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("NF1");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("PESO NF1");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("VALOR NF 1");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("NF2");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("PESO NF2");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("VALOR NF2");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("DIFERENÇA");

		int primeira_linha = rownum++;
		int ultima_linha = primeira_linha;

		sheet.setAutoFilter(CellRangeAddress.valueOf("A3:P3"));

		// fazer checkagens

		boolean nf_interna_ativo = false;
		boolean nf_venda_ativo = false;
		boolean nf_complemento_ativo = false;

		// checka se ha no minimo uma nf interna aplicavel
		for (CadastroContrato.Carregamento carregamento : carregamentos) {
			if (carregamento.getNf_interna_aplicavel() == 1) {
				nf_interna_ativo = true;
				break;
			}

		}

		// checka se ha no minimo uma nf venda aplicavel
		for (CadastroContrato.Carregamento carregamento : carregamentos) {
			if (carregamento.getNf_venda1_aplicavel() == 1) {
				nf_venda_ativo = true;
				break;
			}

		}

		// checka se ha no minimo uma nf complemento aplicavel
		for (CadastroContrato.Carregamento carregamento : carregamentos) {
			if (carregamento.getNf_complemento_aplicavel() == 1) {
				nf_complemento_ativo = true;
				break;
			}

		}

		for (CarregamentoCompleto carregamento : carregamentos) {

			cellnum = 0;
			row = sheet.createRow(rownum);

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_branco_texto_preto);
			cell.setCellValue(carregamento.getContrato().getCodigo());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_branco_texto_preto);
			cell.setCellValue(carregamento.getData());

			// pegar cliente

			String nome_cliente = carregamento.getCliente_carregamento();
			String nome_cliente_completo = carregamento.getCliente_carregamento();

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

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_branco_texto_preto);
			cell.setCellValue(nome_cliente);

			// pegar vendedor

			String nome_vendedor = carregamento.getVendedor_carregamento();

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

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_branco_texto_preto);
			cell.setCellValue(nome_vendedor);

			// transportador
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_branco_texto_preto);
			cell.setCellValue(carregamento.getNome_motorista());

			// veiculo
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_branco_texto_preto);
			cell.setCellValue(carregamento.getPlaca());

			// produto
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_branco_texto_preto);
			cell.setCellValue(carregamento.getContrato().getModelo_produto().getNome_produto());

			// romaneio
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_branco_texto_preto);
			String codigo = carregamento.getCodigo_romaneio();
			if (codigo.equalsIgnoreCase("-Transferencia")) {
				cell.setCellValue("-Transferencia");

			} else if (codigo.equalsIgnoreCase("+Transferencia")) {
				cell.setCellValue("+Transferencia");
			} else {
				cell.setCellValue(codigo);
			}

			cell = row.createCell(cellnum++);
			cell.setCellStyle(pesoStyle);
			if (codigo.equalsIgnoreCase("-Transferencia")) {
				cell.setCellValue(carregamento.getPeso_romaneio() * -1);

			} else if (codigo.equalsIgnoreCase("+Transferencia")) {
				cell.setCellValue(carregamento.getPeso_romaneio());
			} else {
				cell.setCellValue(carregamento.getPeso_romaneio());
			}

			// nfs
			// codigos
			String codigo_romaneio = "";
			String codigo_nf_venda1 = "", codigo_nf_complemento = "";
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
			} catch (Exception e) {
				// //JOptionPane.showMessageDialog(isto, "Romaneio não Localizado");
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
						} catch (Exception e) {
							valor_nf_venda1 = BigDecimal.ZERO;
						}

					} else {
						codigo_nf_venda1 = carregamento.getCodigo_nf_venda1();
						peso_nf_venda1 = carregamento.getPeso_nf_venda1();
						valor_nf_venda1 = carregamento.getValor_nf_venda1();

					}

				}
			} catch (Exception e) {
				// //JOptionPane.showMessageDialog(isto, "Nota Fiscal de venda não Localizado");
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
						} catch (Exception e) {
							valor_nf_complemento = BigDecimal.ZERO;
						}

					} else {
						codigo_nf_complemento = carregamento.getCodigo_nf_complemento();
						peso_nf_complemento = carregamento.getPeso_nf_complemento();
						valor_nf_complemento = carregamento.getValor_nf_complemento();

					}

				}
			} catch (Exception e) {
				// //JOptionPane.showMessageDialog(isto, "Nota Fiscal de remessa não
				// Localizado");

				codigo_nf_complemento = carregamento.getCodigo_nf_complemento();
				peso_nf_complemento = carregamento.getPeso_nf_complemento();
				valor_nf_complemento = carregamento.getValor_nf_complemento();

			}

			// nfvenda1
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_branco_texto_preto);
			if (carregamento.getNf_venda1_aplicavel() == 1)
				cell.setCellValue(codigo_nf_venda1);
			else
				cell.setCellValue("Não Aplicável");

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_branco_texto_preto);
			if (carregamento.getNf_venda1_aplicavel() == 1) {
				cell.setCellValue(z.format(peso_nf_venda1));
			} else {
				cell.setCellValue("Não Aplicável");
			}

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_branco_texto_preto);
			if (carregamento.getNf_venda1_aplicavel() == 1) {
				cell.setCellValue(valor_nf_venda1.doubleValue());
			} else {
				cell.setCellValue("Não Aplicável");
			}

			// nfcomplemento
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_branco_texto_preto);
			if (carregamento.getNf_complemento_aplicavel() == 1)
				cell.setCellValue(codigo_nf_complemento);
			else
				cell.setCellValue("Não Aplicável");

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_branco_texto_preto);
			if (carregamento.getNf_complemento_aplicavel() == 1) {
				cell.setCellValue(z.format(peso_nf_complemento));
			} else {
				cell.setCellValue("Não Aplicável");
			}

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_branco_texto_preto);
			if (carregamento.getNf_complemento_aplicavel() == 1) {
				cell.setCellValue(valor_nf_complemento.doubleValue());
			} else {
				cell.setCellValue("Não Aplicável");
			}

			// diferenca
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_branco_texto_preto);
			if (carregamento.getNf_venda1_aplicavel() == 1 && carregamento.getNf_complemento_aplicavel() == 1) {
				cell.setCellValue(peso_romaneio - (peso_nf_complemento + peso_nf_venda1));
			} else {
				cell.setCellValue("Não Aplicável");
			}

			rownum++;
			ultima_linha = rownum;

		}

		if (incluir_transferencias_carregamentos) {
			// transferencias negativas
			/*************************** transferencias negativas *****************///////////

			for (CadastroContrato.CadastroTransferenciaCarga transferencia : transferencias_remetentes) {

				/*
				 * codigo compradores vendedores status quantidade medida produto transgenia
				 * safra valor_produto valor_total data_contrato local_retirada
				 */

				String texto_detalhado = "";

				GerenciarBancoContratos gerencia_contratos = new GerenciarBancoContratos();
				CadastroContrato remetente = gerencia_contratos.getContrato(transferencia.getId_contrato_remetente());
				CadastroContrato destinatario = gerencia_contratos
						.getContrato(transferencia.getId_contrato_destinatario());
				// CadastroContrato.Carregamento carga = gerencia_contratos
				// .getCarregamento(transferencia.getId_carregamento_remetente());

				CadastroCliente compradores_trans[] = destinatario.getCompradores();
				CadastroCliente vendedores_trans[] = destinatario.getVendedores();

				String nome_vendedores = "";
				String nome_compradores = "";

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

				cellnum = 0;
				row = sheet.createRow(rownum);

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				cell.setCellValue(remetente.getCodigo());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				cell.setCellValue(transferencia.getData());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				cell.setCellValue(remetente.getCodigo());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				cell.setCellValue(destinatario.getCodigo());

				// transportador
				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				cell.setCellValue("");

				// veiculo
				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				cell.setCellValue("");

				// produto
				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				cell.setCellValue("");

				// romaneio
				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				cell.setCellValue("-Transferencia");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(pesoStyle);
				cell.setCellValue(Double.parseDouble(transferencia.getQuantidade()) * -1);

				// nfvenda1
				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				cell.setCellValue("Não Aplicável");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				cell.setCellValue("Não Aplicável");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				cell.setCellValue("Não Aplicável");

				// nfcomplemento
				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				cell.setCellValue("Não Aplicável");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				cell.setCellValue("Não Aplicável");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				cell.setCellValue("Não Aplicável");

				// diferenca
				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				cell.setCellValue("Não Aplicável");

				rownum++;
				ultima_linha = rownum;
			}
		}
		/*************************** transferencias negativas *****************///////////

		// transfereicas positivas
		/*************************** transferencias positivas *****************///////////
		if (incluir_transferencias_carregamentos) {
			for (CadastroContrato.CadastroTransferenciaCarga transferencia : transferencias_destinatarios) {

				String texto_detalhado = "";

				GerenciarBancoContratos gerencia_contratos = new GerenciarBancoContratos();
				CadastroContrato remetente = gerencia_contratos.getContrato(transferencia.getId_contrato_remetente());
				CadastroContrato destinatario = gerencia_contratos
						.getContrato(transferencia.getId_contrato_destinatario());
				// CadastroContrato.Carregamento carga = gerencia_contratos
				// .getCarregamento(transferencia.getId_carregamento_remetente());

				CadastroCliente compradores_trans[] = destinatario.getCompradores();
				CadastroCliente vendedores_trans[] = destinatario.getVendedores();

				String nome_vendedores = "";
				String nome_compradores = "";

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
						nome_vendedores += ",";

					}
				}

				double quantidade = Double.parseDouble(transferencia.getQuantidade());

				texto_detalhado = "Transferência Positiva: Recebimento de volume de " + z.format(quantidade) + " kgs | "
						+ z.format(quantidade / 60) + " sacos recebidos do contrato ";
				texto_detalhado = texto_detalhado + remetente.getCodigo() + " " + nome_compradores + " X "
						+ nome_vendedores + " " + z.format(remetente.getQuantidade()) + " " + remetente.getMedida()
						+ " de " + remetente.getModelo_safra().getProduto().getNome_produto() + " "
						+ remetente.getModelo_safra().getProduto().getTransgenia() + " da safra "
						+ remetente.getModelo_safra().getAno_plantio() + "/"
						+ remetente.getModelo_safra().getAno_colheita();
				texto_detalhado = texto_detalhado + "";

				cellnum = 0;
				row = sheet.createRow(rownum);

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				cell.setCellValue(destinatario.getCodigo());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				cell.setCellValue(transferencia.getData());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				cell.setCellValue(destinatario.getCodigo());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				cell.setCellValue(remetente.getCodigo());

				// transportador
				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				cell.setCellValue("");

				// veiculo
				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				cell.setCellValue("");

				// produto
				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				cell.setCellValue("");

				// romaneio
				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				cell.setCellValue("+Transferencia");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(pesoStyle);
				cell.setCellValue(Double.parseDouble(transferencia.getQuantidade()));

				// nfvenda1
				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				cell.setCellValue("Não Aplicável");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				cell.setCellValue("Não Aplicável");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				cell.setCellValue("Não Aplicável");

				// nfcomplemento
				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				cell.setCellValue("Não Aplicável");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				cell.setCellValue("Não Aplicável");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				cell.setCellValue("Não Aplicável");

				// diferenca
				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				cell.setCellValue("Não Aplicável");

				rownum++;
				ultima_linha = rownum;
			}
		}

		/*************************** transferencias positivas *****************///////////

		// pular linha
		rownum += 1;

		// somatorias
		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		int celula_soma_peso = rownum + 1;

		// somatoria de pesos
		cell = row.createCell(7);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue("Soma Final:");

		cell = row.createCell(8);
		cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
		cell.setCellType(CellType.FORMULA);
		String formula = "SUM(I" + primeira_linha + ":I" + ultima_linha + ")";
		cell.setCellFormula(formula);

		cell = row.createCell(9);
		cell.setCellStyle(numberStyleFundoBrancoTextoPreto);
		cell.setCellType(CellType.FORMULA);
		formula = "SUM(I" + (rownum + 1) + "/60)";
		cell.setCellFormula(formula);

		// somatoria de peso de nf venda 1
		cell = row.createCell(10);
		cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
		cell.setCellType(CellType.FORMULA);
		formula = "SUMPRODUCT(SUBTOTAL(9,OFFSET(K" + primeira_linha + ":K" + ultima_linha + ",ROW(K" + primeira_linha
				+ ":K" + ultima_linha + ")-ROW(K" + primeira_linha + "),0,1,1)),-(J" + primeira_linha + ":J"
				+ ultima_linha + "<>\"Não Aplicável\")) * -1";
		cell.setCellFormula(formula);

		// somatoria de valor de nf venda 1
		cell = row.createCell(11);
		cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
		cell.setCellType(CellType.FORMULA);
		formula = "SUMPRODUCT(SUBTOTAL(9,OFFSET(L" + primeira_linha + ":L" + ultima_linha + ",ROW(L" + primeira_linha
				+ ":L" + ultima_linha + ")-ROW(L" + primeira_linha + "),0,1,1)),-(J" + primeira_linha + ":J"
				+ ultima_linha + "<>\"Não Aplicável\")) * -1";
		cell.setCellFormula(formula);

		// somatoria de PESO de nf venda 2
		cell = row.createCell(13);
		cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
		cell.setCellType(CellType.FORMULA);
		formula = "SUMPRODUCT(SUBTOTAL(9,OFFSET(N" + primeira_linha + ":N" + ultima_linha + ",ROW(N" + primeira_linha
				+ ":N" + ultima_linha + ")-ROW(N" + primeira_linha + "),0,1,1)),-(M" + primeira_linha + ":M"
				+ ultima_linha + "<>\"Não Aplicável\")) * -1";
		cell.setCellFormula(formula);

		// somatoria de valor de nf venda 2
		cell = row.createCell(14);
		cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
		cell.setCellType(CellType.FORMULA);
		formula = "SUMPRODUCT(SUBTOTAL(9,OFFSET(O" + primeira_linha + ":O" + ultima_linha + ",ROW(O" + primeira_linha
				+ ":O" + ultima_linha + ")-ROW(O" + primeira_linha + "),0,1,1)),-(M" + primeira_linha + ":M"
				+ ultima_linha + "<>\"Não Aplicável\")) * -1";
		cell.setCellFormula(formula);

		int linha_soma_final = 0;

		if (incluir_transferencias_carregamentos) {

			row = sheet.createRow(rownum += 1);
			cellnum = 0;

			// somatoria de pesos
			cell = row.createCell(0);
			cell.setCellStyle(celula_fundo_branco_texto_preto);
			cell.setCellValue("Peso Transferencias(-):");

			cell = row.createCell(1);
			cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
			cell.setCellType(CellType.FORMULA);
			formula = "SUMPRODUCT(SUBTOTAL(9,OFFSET(I" + primeira_linha + ":I" + ultima_linha + ",ROW(I"
					+ primeira_linha + ":I" + ultima_linha + ")-ROW(I" + primeira_linha + "),0,1,1)),-(H"
					+ primeira_linha + ":H" + ultima_linha + "=\"-Transferencia\")) * -1";
			cell.setCellFormula(formula);

			cell = row.createCell(2);
			cell.setCellStyle(numberStyleFundoBrancoTextoPreto);
			cell.setCellType(CellType.FORMULA);
			formula = "SUM(B" + (rownum + 1) + "/60)";
			cell.setCellFormula(formula);

			row = sheet.createRow(rownum += 1);
			cellnum = 0;

			// somatoria de pesos
			cell = row.createCell(0);
			cell.setCellStyle(celula_fundo_branco_texto_preto);
			cell.setCellValue("Peso Transferencias(+):");

			cell = row.createCell(1);
			cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
			cell.setCellType(CellType.FORMULA);
			formula = "SUMPRODUCT(SUBTOTAL(9,OFFSET(I" + primeira_linha + ":I" + ultima_linha + ",ROW(I"
					+ primeira_linha + ":I" + ultima_linha + ")-ROW(I" + primeira_linha + "),0,1,1)),-(H"
					+ primeira_linha + ":H" + ultima_linha + "=\"+Transferencia\")) * -1";
			cell.setCellFormula(formula);

			cell = row.createCell(2);
			cell.setCellStyle(numberStyleFundoBrancoTextoPreto);
			cell.setCellType(CellType.FORMULA);
			formula = "SUM(B" + (rownum + 1) + "/60)";
			cell.setCellFormula(formula);

		}

		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		linha_soma_final = rownum + 1;
		// somatoria de pesos
		cell = row.createCell(0);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue("Soma Final:");

		cell = row.createCell(1);
		cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
		cell.setCellType(CellType.FORMULA);
		formula = "SUM(I" + primeira_linha + ":I" + ultima_linha + ")";
		cell.setCellFormula(formula);

		cell = row.createCell(2);
		cell.setCellStyle(numberStyleFundoBrancoTextoPreto);
		cell.setCellType(CellType.FORMULA);
		formula = "SUM(B" + (rownum + 1) + "/60)";
		cell.setCellFormula(formula);

		// total contratado

		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		int linha_total_contratado = rownum + 1;
		cell = row.createCell(0);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue("Total Contratado:");

		cell = row.createCell(1);
		cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
		cell.setCellValue(soma_total_quantidade_contratos);

		cell = row.createCell(2);
		cell.setCellStyle(numberStyleFundoBrancoTextoPreto);
		cell.setCellType(CellType.FORMULA);
		formula = "SUM(B" + (rownum + 1) + "/60)";
		cell.setCellFormula(formula);

		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		cell = row.createCell(0);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue("Restante:");

		// restante

		if (incluir_transferencias_carregamentos) {
			cell = row.createCell(1);
			cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
			cell.setCellType(CellType.FORMULA);
			formula = "SUM(B" + linha_total_contratado + "-B" + linha_soma_final + ")";
			cell.setCellFormula(formula);
		} else {

			cell = row.createCell(1);
			cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
			cell.setCellType(CellType.FORMULA);
			formula = "SUM(B" + linha_total_contratado + "-B" + celula_soma_peso + ")";
			cell.setCellFormula(formula);
		}
		cell = row.createCell(2);
		cell.setCellStyle(numberStyleFundoBrancoTextoPreto);
		cell.setCellType(CellType.FORMULA);
		formula = "SUM(B" + (rownum + 1) + "/60)";
		cell.setCellFormula(formula);

		// restante baseado no que foi recebido

		row = sheet.createRow(rownum += 2);
		cellnum = 0;

		int linha_total_recebido = rownum + 1;
		cell = row.createCell(0);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue("Total Recebido:");

		cell = row.createCell(1);
		cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
		cell.setCellValue(soma_total_quantidade_recebidas);

		cell = row.createCell(2);
		cell.setCellStyle(numberStyleFundoBrancoTextoPreto);
		cell.setCellType(CellType.FORMULA);
		formula = "SUM(B" + (rownum + 1) + "/60)";
		cell.setCellFormula(formula);

		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		cell = row.createCell(0);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue("Restante:");

		// restante

		if (incluir_transferencias_carregamentos) {
			cell = row.createCell(1);
			cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
			cell.setCellType(CellType.FORMULA);
			formula = "SUM(B" + linha_total_recebido + "-B" + linha_soma_final + ")";
			cell.setCellFormula(formula);
		} else {

			cell = row.createCell(1);
			cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
			cell.setCellType(CellType.FORMULA);
			formula = "SUM(B" + linha_total_recebido + "-B" + celula_soma_peso + ")";
			cell.setCellFormula(formula);
		}
		cell = row.createCell(2);
		cell.setCellStyle(numberStyleFundoBrancoTextoPreto);
		cell.setCellType(CellType.FORMULA);
		formula = "SUM(B" + (rownum + 1) + "/60)";
		cell.setCellFormula(formula);

		for (int i = 0; i < 16; i++) {
			sheet.autoSizeColumn(i);

		}

		DadosTabelaExcel retornar = new DadosTabelaExcel();
		retornar.setWorkbook(workbook);
		retornar.setSheet(sheet);
		rownum += 3;
		retornar.setRownum(rownum);

		return retornar;

	}

	public void criarTabelaRecebimentosUnidos(ArrayList<RecebimentoCompleto> recebimentos,
			double soma_total_quantidade_contratos) {
		// XWPFParagraph par = document_global.createParagraph();

		NumberFormat z = NumberFormat.getNumberInstance();

		Locale ptBr = new Locale("pt", "BR");

		int num_linhas_recebimentos = -1;

		if (soma_total_quantidade_contratos == 0) {

			num_linhas_recebimentos = recebimentos.size() + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1;
		} else {
			num_linhas_recebimentos = recebimentos.size() + 1 + 1 + 1 + 1 + 1 + 1 + 1;

		}
		double soma_total_romaneio = 0;
		double soma_total_trans_negativa = 0;
		double soma_total_trans_positiva = 0;
		double soma_total_nf_venda = 0;

		double soma_total_nf_remessa = 0;

		BigDecimal valor_total_nf_venda = BigDecimal.ZERO;
		BigDecimal valor_total_nf_remessa = BigDecimal.ZERO;

		XWPFTable table = document_global.createTable(num_linhas_recebimentos, 10);

		setTableAlign(table, ParagraphAlignment.CENTER);
		XWPFTableRow tableRowOne = table.getRow(0);
		tableRowOne.getCell(0).removeParagraph(0);

		XWPFParagraph paragraph = tableRowOne.getCell(0).addParagraph();
		// CadastroContrato novo_contrato = recebimentos.get(0).getContrato();

		//
		int cabecalho = 0;

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(0).removeParagraph(0);
		paragraph = tableRowOne.getCell(0).addParagraph();

		criarParagrafoTabela(paragraph, "Quantidade Total: " + z.format(soma_total_quantidade_contratos) + " kgs | "
				+ z.format(soma_total_quantidade_contratos / 60) + " sacos", true);
		tableRowOne.getCell(0).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");
		CTHMerge hMerge = CTHMerge.Factory.newInstance();
		hMerge.setVal(STMerge.RESTART);
		table.getRow(cabecalho).getCell(0).getCTTc().getTcPr().setHMerge(hMerge);

		for (int celula = 1; celula <= 9; celula++) {
			tableRowOne = table.getRow(cabecalho);
			tableRowOne.getCell(celula).removeParagraph(0);
			paragraph = tableRowOne.getCell(celula).addParagraph();

			criarParagrafoTabela(paragraph, "", true);
			tableRowOne.getCell(celula).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");

			CTHMerge hMerge1 = CTHMerge.Factory.newInstance();
			hMerge1.setVal(STMerge.CONTINUE);
			table.getRow(cabecalho).getCell(celula).getCTTc().getTcPr().setHMerge(hMerge1);

		}

		cabecalho++;

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(0).removeParagraph(0);
		paragraph = tableRowOne.getCell(0).addParagraph();
		criarParagrafoTabela(paragraph, "CONTRATO", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(1).removeParagraph(0);
		paragraph = tableRowOne.getCell(1).addParagraph();
		criarParagrafoTabela(paragraph, "DATA", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(2).removeParagraph(0);
		paragraph = tableRowOne.getCell(2).addParagraph();
		criarParagrafoTabela(paragraph, "ROMANEIO ", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(3).removeParagraph(0);
		paragraph = tableRowOne.getCell(3).addParagraph();
		criarParagrafoTabela(paragraph, "PESO ROMANEIO: ", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(4).removeParagraph(0);
		paragraph = tableRowOne.getCell(4).addParagraph();

		criarParagrafoTabela(paragraph, "NF VENDA", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(5).removeParagraph(0);
		paragraph = tableRowOne.getCell(5).addParagraph();
		criarParagrafoTabela(paragraph, "PESO NF VENDA", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(6).removeParagraph(0);
		paragraph = tableRowOne.getCell(6).addParagraph();
		criarParagrafoTabela(paragraph, "VALOR NF VENDA", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(7).removeParagraph(0);
		paragraph = tableRowOne.getCell(7).addParagraph();
		criarParagrafoTabela(paragraph, "NF REMESSA", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(8).removeParagraph(0);
		paragraph = tableRowOne.getCell(8).addParagraph();
		criarParagrafoTabela(paragraph, "PESO NF REMESSA", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(9).removeParagraph(0);
		paragraph = tableRowOne.getCell(9).addParagraph();
		criarParagrafoTabela(paragraph, "VALOR NF REMESSA", true);

		// CadastroContrato novo_contrato =
		// gerenciar.getContratoSimplificado(recebimentos.get(0).getId_contrato_recebimento());
		cabecalho++;

		int i = cabecalho;

		// checkgens
		boolean nf_remessa_ativo = false;
		boolean nf_venda_ativo = false;

		// checka se ha no minimo uma nf remessa aplicavel
		for (CadastroContrato.Recebimento recebimento : recebimentos) {
			if (recebimento.getNf_remessa_aplicavel() == 1) {
				nf_remessa_ativo = true;
				break;
			}

		}

		// checka se ha no minimo uma nf venda aplicavel
		for (CadastroContrato.Recebimento recebimento : recebimentos) {
			if (recebimento.getNf_venda_aplicavel() == 1) {
				nf_venda_ativo = true;
				break;
			}

		}
		for (RecebimentoCompleto recebimento : recebimentos) {

			String cor = "000000";

			if (checkString(recebimento.getCodigo_nf_venda()) && checkString(recebimento.getCodigo_nf_remessa())) {
				// ok
				cor = "FFFFFF";

			}

			else if (!(checkString(recebimento.getCodigo_nf_venda()))
					&& !(checkString(recebimento.getCodigo_nf_remessa()))) {
				// falta duas notas
				cor = "B0C4DE";

			} else if (!(checkString(recebimento.getCodigo_nf_venda()))
					&& checkString(recebimento.getCodigo_nf_remessa())) {
				// falta apenas nf de venda
				cor = "FFFF00";

			} else if (!(checkString(recebimento.getCodigo_nf_remessa()))
					&& checkString(recebimento.getCodigo_nf_venda())) {
				// falta apenas nf remessa
				cor = "FFD700";

			}

			// contrato ao qual esse recebimento pertence
			tableRowOne = table.getRow(i);
			tableRowOne.getCell(0).removeParagraph(0);
			paragraph = tableRowOne.getCell(0).addParagraph();
			criarParagrafoTabela(paragraph, recebimento.getContrato().getCodigo(), false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(1).removeParagraph(0);
			paragraph = tableRowOne.getCell(1).addParagraph();
			criarParagrafoTabela(paragraph, recebimento.getData_recebimento(), false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(2).removeParagraph(0);
			paragraph = tableRowOne.getCell(2).addParagraph();
			criarParagrafoTabela(paragraph, recebimento.getCodigo_romaneio(), false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(3).removeParagraph(0);
			paragraph = tableRowOne.getCell(3).addParagraph();

			String codigo = recebimento.getCodigo_romaneio();

			if (codigo.equalsIgnoreCase("-Transferencia")) {
				criarParagrafoTabela(paragraph, "-" + z.format(recebimento.getPeso_romaneio()), false);

			} else if (codigo.equalsIgnoreCase("+Transferencia")) {
				criarParagrafoTabela(paragraph, "+" + z.format(recebimento.getPeso_romaneio()), false);

			} else {
				criarParagrafoTabela(paragraph, z.format(recebimento.getPeso_romaneio()), false);

			}

			if (codigo.equalsIgnoreCase("-Transferencia")) {
				soma_total_trans_negativa += recebimento.getPeso_romaneio();

			} else if (codigo.equalsIgnoreCase("+Transferencia")) {
				soma_total_trans_positiva += recebimento.getPeso_romaneio();

			} else {
				soma_total_romaneio += recebimento.getPeso_romaneio();

			}

			if (recebimento.getNf_venda_aplicavel() == 1) {

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(4).removeParagraph(0);
				paragraph = tableRowOne.getCell(4).addParagraph();
				criarParagrafoTabela(paragraph, recebimento.getCodigo_nf_venda(), false);
				tableRowOne.getCell(4).getCTTc().addNewTcPr().addNewShd().setFill(cor);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(5).removeParagraph(0);
				paragraph = tableRowOne.getCell(5).addParagraph();
				criarParagrafoTabela(paragraph, z.format(recebimento.getPeso_nf_venda()) + " Kgs", false);
				soma_total_nf_venda += recebimento.getPeso_nf_venda();
				tableRowOne.getCell(5).getCTTc().addNewTcPr().addNewShd().setFill(cor);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(6).removeParagraph(0);
				paragraph = tableRowOne.getCell(6).addParagraph();
				criarParagrafoTabela(paragraph,
						NumberFormat.getCurrencyInstance(ptBr).format((recebimento.getValor_nf_venda().doubleValue())),
						false);
				valor_total_nf_venda = valor_total_nf_venda.add(recebimento.getValor_nf_venda());
				tableRowOne.getCell(6).getCTTc().addNewTcPr().addNewShd().setFill(cor);

			} else {
				tableRowOne = table.getRow(i);
				tableRowOne.getCell(4).removeParagraph(0);
				paragraph = tableRowOne.getCell(4).addParagraph();
				criarParagrafoTabela(paragraph, "Não Aplicável", false);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(5).removeParagraph(0);
				paragraph = tableRowOne.getCell(5).addParagraph();
				criarParagrafoTabela(paragraph, "Não Aplicável", false);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(6).removeParagraph(0);
				paragraph = tableRowOne.getCell(6).addParagraph();
				criarParagrafoTabela(paragraph, "Não Aplicável", false);

			}

			if (recebimento.getNf_remessa_aplicavel() == 1) {

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(7).removeParagraph(0);
				paragraph = tableRowOne.getCell(7).addParagraph();
				criarParagrafoTabela(paragraph, recebimento.getCodigo_nf_remessa(), false);
				tableRowOne.getCell(7).getCTTc().addNewTcPr().addNewShd().setFill(cor);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(8).removeParagraph(0);
				paragraph = tableRowOne.getCell(8).addParagraph();
				criarParagrafoTabela(paragraph, z.format(recebimento.getPeso_nf_remessa()) + " Kgs", false);
				soma_total_nf_remessa += recebimento.getPeso_nf_remessa();
				tableRowOne.getCell(8).getCTTc().addNewTcPr().addNewShd().setFill(cor);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(9).removeParagraph(0);
				paragraph = tableRowOne.getCell(9).addParagraph();
				criarParagrafoTabela(paragraph, NumberFormat.getCurrencyInstance(ptBr)
						.format((recebimento.getValor_nf_remessa().doubleValue())), false);
				valor_total_nf_remessa = valor_total_nf_remessa.add(recebimento.getValor_nf_remessa());
				tableRowOne.getCell(9).getCTTc().addNewTcPr().addNewShd().setFill(cor);

			} else {
				tableRowOne = table.getRow(i);
				tableRowOne.getCell(7).removeParagraph(0);
				paragraph = tableRowOne.getCell(7).addParagraph();
				criarParagrafoTabela(paragraph, "Não Aplicável", false);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(8).removeParagraph(0);
				paragraph = tableRowOne.getCell(8).addParagraph();
				criarParagrafoTabela(paragraph, "Não Aplicável", false);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(9).removeParagraph(0);
				paragraph = tableRowOne.getCell(9).addParagraph();
				criarParagrafoTabela(paragraph, "Não Aplicável", false);

			}

			i++;

		}

		double peso_total_romaneios = soma_total_romaneio + soma_total_trans_positiva - soma_total_trans_negativa;

		// somatorias
		// peso de romaneios

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(3).removeParagraph(0);
		paragraph = tableRowOne.getCell(3).addParagraph();
		criarParagrafoTabela(paragraph,
				" " + z.format(peso_total_romaneios) + " kgs / " + (z.format((peso_total_romaneios / 60))) + " sacos ",
				true);
		// pesos de nf venda

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(5).removeParagraph(0);
		paragraph = tableRowOne.getCell(5).addParagraph();
		criarParagrafoTabela(paragraph,
				" " + z.format(soma_total_nf_venda) + " kgs / " + (z.format((soma_total_nf_venda / 60))) + " sacos ",
				true);

		// valor nf venda

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(6).removeParagraph(0);
		paragraph = tableRowOne.getCell(6).addParagraph();
		criarParagrafoTabela(paragraph, NumberFormat.getCurrencyInstance(ptBr).format(valor_total_nf_venda), true);

		// peso nf remessa

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(8).removeParagraph(0);
		paragraph = tableRowOne.getCell(8).addParagraph();
		criarParagrafoTabela(paragraph, " " + z.format(soma_total_nf_remessa) + " kgs / "
				+ (z.format((soma_total_nf_remessa / 60))) + " sacos ", true);

		// valor nf remessa

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(9).removeParagraph(0);
		paragraph = tableRowOne.getCell(9).addParagraph();
		criarParagrafoTabela(paragraph, NumberFormat.getCurrencyInstance(ptBr).format(valor_total_nf_remessa), true);

		// informacoes de total
		i += 2;
		// peso real
		tableRowOne = table.getRow(i);
		tableRowOne.getCell(0).removeParagraph(0);
		paragraph = tableRowOne.getCell(0).addParagraph();
		criarParagrafoTabela(paragraph, "Peso Total Romaneios:", false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(1).removeParagraph(0);
		paragraph = tableRowOne.getCell(1).addParagraph();
		criarParagrafoTabela(paragraph,
				" " + z.format(peso_total_romaneios) + " kgs / " + (z.format((peso_total_romaneios / 60))) + " sacos ",
				true);

		// pesos de nf venda
		tableRowOne = table.getRow(i);
		tableRowOne.getCell(2).removeParagraph(0);
		paragraph = tableRowOne.getCell(2).addParagraph();
		criarParagrafoTabela(paragraph, "Peso Total NFV's:", false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(3).removeParagraph(0);
		paragraph = tableRowOne.getCell(3).addParagraph();
		if (nf_venda_ativo) {
			criarParagrafoTabela(paragraph, " " + z.format(soma_total_nf_venda) + " kgs / "
					+ (z.format((soma_total_nf_venda / 60))) + " sacos ", true);
		} else {
			criarParagrafoTabela(paragraph, "Não Aplicável", true);
		}

		// valor nf venda

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(4).removeParagraph(0);
		paragraph = tableRowOne.getCell(4).addParagraph();
		criarParagrafoTabela(paragraph, "Valor Total NFV's:", false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(5).removeParagraph(0);
		paragraph = tableRowOne.getCell(5).addParagraph();
		if (nf_venda_ativo) {
			criarParagrafoTabela(paragraph, NumberFormat.getCurrencyInstance(ptBr).format(valor_total_nf_venda), true);
		} else {
			criarParagrafoTabela(paragraph, "Não Aplicável", true);
		}
		// peso nf remessa
		tableRowOne = table.getRow(i);
		tableRowOne.getCell(6).removeParagraph(0);
		paragraph = tableRowOne.getCell(6).addParagraph();
		criarParagrafoTabela(paragraph, "Peso Total NFR's:", false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(7).removeParagraph(0);
		paragraph = tableRowOne.getCell(7).addParagraph();
		if (nf_remessa_ativo) {
			criarParagrafoTabela(paragraph, " " + z.format(soma_total_nf_remessa) + " kgs / "
					+ (z.format((soma_total_nf_remessa / 60))) + " sacos ", true);
		} else {
			criarParagrafoTabela(paragraph, "Não Aplicável", true);
		}

		// valor nf remessa

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(8).removeParagraph(0);
		paragraph = tableRowOne.getCell(8).addParagraph();
		criarParagrafoTabela(paragraph, "Valor Total NFR's:", false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(9).removeParagraph(0);
		paragraph = tableRowOne.getCell(9).addParagraph();
		if (nf_remessa_ativo) {
			criarParagrafoTabela(paragraph, NumberFormat.getCurrencyInstance(ptBr).format(valor_total_nf_remessa),
					true);
		} else {
			criarParagrafoTabela(paragraph, "Não Aplicável", true);
		}
		i++;

		// total a receber em kgs

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(0).removeParagraph(0);
		paragraph = tableRowOne.getCell(0).addParagraph();
		criarParagrafoTabela(paragraph, "Total a Receber:", false);

		double quantidade_total_sacos = 0;
		double quantidade_total_kgs = 0;

		// quantidade para os totais

		quantidade_total_kgs = soma_total_quantidade_contratos;

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(1).removeParagraph(0);
		paragraph = tableRowOne.getCell(1).addParagraph();
		if (soma_total_quantidade_contratos != 0) {
			criarParagrafoTabela(paragraph,
					" " + z.format(soma_total_quantidade_contratos - peso_total_romaneios) + " kgs / "
							+ (z.format(((soma_total_quantidade_contratos - peso_total_romaneios) / 60))) + " sacos ",
					true);
		} else {
			criarParagrafoTabela(paragraph, " " + z.format(quantidade_total_kgs - peso_total_romaneios) + " kgs / "
					+ (z.format(((quantidade_total_kgs - peso_total_romaneios) / 60))) + " sacos ", true);
		}

		// pesos de nf
		tableRowOne = table.getRow(i);
		tableRowOne.getCell(2).removeParagraph(0);
		paragraph = tableRowOne.getCell(2).addParagraph();
		criarParagrafoTabela(paragraph, "Total a emitir NFV's:", false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(3).removeParagraph(0);
		paragraph = tableRowOne.getCell(3).addParagraph();
		if (nf_venda_ativo) {
			if (soma_total_quantidade_contratos != 0) {
				criarParagrafoTabela(paragraph,
						" " + z.format(soma_total_quantidade_contratos - soma_total_nf_venda) + " kgs / "
								+ (z.format(((soma_total_quantidade_contratos - soma_total_nf_venda) / 60)))
								+ " sacos ",
						true);
			} else {
				criarParagrafoTabela(paragraph, " " + z.format(quantidade_total_kgs - soma_total_nf_venda) + " kgs / "
						+ (z.format(((quantidade_total_kgs - soma_total_nf_venda) / 60))) + " sacos ", true);
			}
		} else {
			criarParagrafoTabela(paragraph, "Não Aplicável", true);

		}

		// pesos de nf remessa
		tableRowOne = table.getRow(i);
		tableRowOne.getCell(6).removeParagraph(0);
		paragraph = tableRowOne.getCell(6).addParagraph();
		criarParagrafoTabela(paragraph, "Total a emitir NFR's:", false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(7).removeParagraph(0);
		paragraph = tableRowOne.getCell(7).addParagraph();
		if (nf_remessa_ativo) {
			if (soma_total_quantidade_contratos != 0) {
				criarParagrafoTabela(paragraph,
						" " + z.format(soma_total_quantidade_contratos - soma_total_nf_remessa) + " kgs / "
								+ (z.format(((soma_total_quantidade_contratos - soma_total_nf_remessa) / 60)))
								+ " sacos ",
						true);
			} else {
				criarParagrafoTabela(paragraph, " " + z.format(quantidade_total_kgs - soma_total_nf_remessa) + " kgs / "
						+ (z.format(((quantidade_total_kgs - soma_total_nf_remessa) / 60))) + " sacos ", true);
			}
		} else {
			criarParagrafoTabela(paragraph, "Não Aplicável", true);

		}
		i++;

		// texto do status

		String texto = "";

		texto += ("\nQuantidade Total Contratada: " + z.format(soma_total_quantidade_contratos) + " kgs | "
				+ z.format(soma_total_quantidade_contratos / 60) + " sacos\n\n");

		texto += ("Soma do Total dos Romaneios: " + z.format(soma_total_romaneio) + " kgs | "
				+ z.format(soma_total_romaneio / 60) + " sacos\n");

		if (incluir_transferencias_recebimentos) {
			texto += ("Soma do Total dos Transferência Negativa: -" + z.format(soma_total_trans_negativa) + " kgs | "
					+ z.format(soma_total_trans_negativa / 60) + " sacos\n");

			texto += ("Soma do Total dos Transferência Positiva: +" + z.format(soma_total_trans_positiva) + " kgs | "
					+ z.format(soma_total_trans_positiva / 60) + " sacos\n");

			texto += ("Somatória Final dos Recebimentos: "
					+ z.format(soma_total_romaneio + soma_total_trans_positiva - soma_total_trans_negativa) + " kgs | "
					+ z.format((soma_total_romaneio + soma_total_trans_positiva - soma_total_trans_negativa) / 60)
					+ " sacos\n");

		} else {
			texto += ("Somatória Final dos Recebimentos: " + z.format(soma_total_romaneio) + " kgs | "
					+ z.format(soma_total_romaneio / 60) + " sacos\n");

		}

		double diferenca = soma_total_quantidade_contratos
				- (soma_total_romaneio + soma_total_trans_positiva - soma_total_trans_negativa);

		texto += "\nStatus Gerado automaticamente: \n";

		if (diferenca == 0) {
			texto = texto + "[Recebimento] [Concluído]\n";
		} else if (diferenca > 0) {
			texto = texto + "[Recebimento] [Incompleto], [falta] [receber] [[" + z.format(diferenca) + "] Kgs | "
					+ z.format(diferenca / 60) + " sacos\n";

		} else if (diferenca < 0) {
			texto = texto + "[Recebimento] [Excedido], [excedeu] [" + z.format(diferenca) + "] Kgs | "
					+ z.format(diferenca / 60) + " sacos\n";

		}

		String texto_nf_venda = "";
		if (nf_venda_ativo) {
			double diferenca_nf_venda = quantidade_total_kgs - soma_total_nf_venda;
			if (diferenca_nf_venda == 0) {
				texto_nf_venda = texto_nf_venda + "\nEmissão de NF's de Venda Concluído\n";
			} else if (diferenca_nf_venda > 0) {
				texto_nf_venda = texto_nf_venda + "\nEmissão de NF's de Venda Incompleto, falta emitir "
						+ z.format(diferenca_nf_venda) + " Kgs | " + z.format(diferenca_nf_venda / 60) + " sacos\n";

			} else if (diferenca_nf_venda < 0) {
				texto_nf_venda = texto_nf_venda + "\nEmissão de NF's Venda Excedido, excedeu "
						+ z.format(diferenca_nf_venda) + " Kgs | " + z.format(diferenca_nf_venda / 60) + " sacos\n";

			}
		} else {
			texto_nf_venda = "\nEmissão de NF's Venda Não Aplicável";
		}

		String texto_nf_remessa = "";
		if (nf_remessa_ativo) {
			double diferenca_nf_remessa = quantidade_total_kgs - soma_total_nf_remessa;
			if (diferenca_nf_remessa == 0) {
				texto_nf_remessa = texto_nf_remessa + "Emissão de NF's de Remessa Concluído\n";
			} else if (diferenca_nf_remessa > 0) {
				texto_nf_remessa = texto_nf_remessa + "Emissão de NF's de Remessa Incompleto, falta emitir "
						+ z.format(diferenca_nf_remessa) + " Kgs | " + z.format(diferenca_nf_remessa / 60) + " sacos\n";

			} else if (diferenca_nf_remessa < 0) {
				texto_nf_remessa = texto_nf_remessa + "Emissão de NF's Remessa Excedido, excedeu "
						+ z.format(diferenca_nf_remessa) + " Kgs | " + z.format(diferenca_nf_remessa / 60) + " sacos\n";

			}
		} else {
			texto_nf_remessa = "Emissão de NF's Remessa Não Aplicável";
		}

		substituirTexto(texto + texto_nf_venda + texto_nf_remessa);

	}

	public void criarTabelaRecebimentos(ArrayList<RecebimentoCompleto> recebimentos, CadastroContrato novo_contrato) {
		// XWPFParagraph par = document_global.createParagraph();

		NumberFormat z = NumberFormat.getNumberInstance();

		Locale ptBr = new Locale("pt", "BR");

		// criarParagrafo(1);
		// linhas x colunas

		GerenciarBancoTransferenciaRecebimento gerenciar_transferencias = null;
		ArrayList<CadastroContrato.CadastroTransferenciaRecebimento> lista_transferencias_recebimento_remetente_local = new ArrayList<>();
		ArrayList<CadastroContrato.CadastroTransferenciaRecebimento> lista_transferencias_recebimento_destinatario_local = new ArrayList<>();

		if (incluir_transferencias_recebimentos) {

			gerenciar_transferencias = new GerenciarBancoTransferenciaRecebimento();

			lista_transferencias_recebimento_remetente_local = gerenciar_transferencias
					.getTransferenciasRemetente(novo_contrato.getId());

			lista_transferencias_recebimento_destinatario_local = gerenciar_transferencias
					.getTransferenciaDestinatario(novo_contrato.getId());

		}

		int num_linhas_recebimentos = -1;

		if (incluir_transferencias_recebimentos) {
			num_linhas_recebimentos = recebimentos.size() + lista_transferencias_recebimento_remetente_local.size()
					+ lista_transferencias_recebimento_destinatario_local.size() + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1;
		} else {
			num_linhas_recebimentos = recebimentos.size() + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1;
		}

		double soma_total_romaneio = 0;
		double soma_total_trans_negativa = 0;
		double soma_total_trans_positiva = 0;
		double soma_total_nf_venda = 0;

		double soma_total_nf_remessa = 0;

		BigDecimal valor_total_nf_venda = BigDecimal.ZERO;
		BigDecimal valor_total_nf_remessa = BigDecimal.ZERO;

		XWPFTable table = document_global.createTable(num_linhas_recebimentos, 10);

		setTableAlign(table, ParagraphAlignment.CENTER);
		XWPFTableRow tableRowOne = table.getRow(0);
		tableRowOne.getCell(0).removeParagraph(0);

		XWPFParagraph paragraph = tableRowOne.getCell(0).addParagraph();
		// CadastroContrato novo_contrato = recebimentos.get(0).getContrato();

		//
		int cabecalho = 0;

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(0).removeParagraph(0);
		paragraph = tableRowOne.getCell(0).addParagraph();

		double quantidade_kg = 0;
		double quantidade_sacos = 0;

		if (novo_contrato.getMedida().equalsIgnoreCase("KG")) {
			quantidade_kg = novo_contrato.getQuantidade();
			quantidade_sacos = quantidade_kg / 60;
		} else if (novo_contrato.getMedida().equalsIgnoreCase("Sacos")) {
			quantidade_sacos = novo_contrato.getQuantidade();
			quantidade_kg = quantidade_sacos * 60;
		}

		// compradores x vendedores

		// safra
		String safra = novo_contrato.getModelo_safra().getProduto().getNome_produto() + " "
				+ novo_contrato.getModelo_safra().getProduto().getTransgenia() + " "
				+ novo_contrato.getModelo_safra().getAno_plantio() + "/"
				+ novo_contrato.getModelo_safra().getAno_colheita();

		criarParagrafoTabela(paragraph,
				"CTR: " + novo_contrato.getCodigo() + " " + safra + " Quantidade Total: " + z.format(quantidade_kg)
						+ " kgs | " + z.format(quantidade_sacos) + " sacos "
						+ NumberFormat.getCurrencyInstance(ptBr).format(novo_contrato.getValor_produto()) + " por "
						+ novo_contrato.getMedida() + " totalizando: "
						+ NumberFormat.getCurrencyInstance(ptBr).format(novo_contrato.getValor_a_pagar().doubleValue()),
				true);
		tableRowOne.getCell(0).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");
		CTHMerge hMerge = CTHMerge.Factory.newInstance();
		hMerge.setVal(STMerge.RESTART);
		table.getRow(cabecalho).getCell(0).getCTTc().getTcPr().setHMerge(hMerge);

		for (int celula = 1; celula <= 9; celula++) {
			tableRowOne = table.getRow(cabecalho);
			tableRowOne.getCell(celula).removeParagraph(0);
			paragraph = tableRowOne.getCell(celula).addParagraph();

			criarParagrafoTabela(paragraph, "", true);
			tableRowOne.getCell(celula).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");

			CTHMerge hMerge1 = CTHMerge.Factory.newInstance();
			hMerge1.setVal(STMerge.CONTINUE);
			table.getRow(cabecalho).getCell(celula).getCTTc().getTcPr().setHMerge(hMerge1);

		}

		cabecalho++;

		// linha com nome compradores x vendedores

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(0).removeParagraph(0);
		paragraph = tableRowOne.getCell(0).addParagraph();

		CadastroCliente compradores[] = novo_contrato.getCompradores();
		CadastroCliente vendedores[] = novo_contrato.getVendedores();

		String nome_vendedores = "";
		String nome_compradores = "";

		if (compradores[0] != null) {
			if (compradores[0].getTipo_pessoa() == 0) {
				// pessoa fisica
				nome_compradores = compradores[0].getNome_empresarial();
			} else {
				nome_compradores = compradores[0].getNome_fantaia();

			}
		}
		if (compradores[1] != null) {
			if (compradores[1].getTipo_pessoa() == 0) {
				// pessoa fisica
				nome_compradores = nome_compradores + ", " + compradores[1].getNome_empresarial();
			} else {
				nome_compradores = nome_compradores + ", " + compradores[1].getNome_fantaia();

			}
		}
		if (vendedores[0] != null) {
			if (vendedores[0].getTipo_pessoa() == 0) {
				nome_vendedores = vendedores[0].getNome_empresarial();
			} else {
				nome_vendedores = vendedores[0].getNome_fantaia();
			}
		}

		if (vendedores[1] != null) {
			if (vendedores[1].getTipo_pessoa() == 0) {
				nome_vendedores = nome_vendedores + ", " + vendedores[1].getNome_empresarial();
			} else {
				nome_vendedores = nome_vendedores + ", " + vendedores[1].getNome_fantaia();
			}
		}

		criarParagrafoTabela(paragraph, novo_contrato.getNomes_compradores().toUpperCase() + " X "
				+ novo_contrato.getNomes_vendedores().toUpperCase(), true);
		tableRowOne.getCell(0).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");
		hMerge = CTHMerge.Factory.newInstance();
		hMerge.setVal(STMerge.RESTART);
		table.getRow(cabecalho).getCell(0).getCTTc().getTcPr().setHMerge(hMerge);

		for (int celula = 1; celula <= 9; celula++) {
			tableRowOne = table.getRow(cabecalho);
			tableRowOne.getCell(celula).removeParagraph(0);
			paragraph = tableRowOne.getCell(celula).addParagraph();

			criarParagrafoTabela(paragraph, "", true);
			tableRowOne.getCell(celula).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");

			CTHMerge hMerge1 = CTHMerge.Factory.newInstance();
			hMerge1.setVal(STMerge.CONTINUE);
			table.getRow(cabecalho).getCell(celula).getCTTc().getTcPr().setHMerge(hMerge1);

		}

		cabecalho++;

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(0).removeParagraph(0);
		paragraph = tableRowOne.getCell(0).addParagraph();
		criarParagrafoTabela(paragraph, "CONTRATO", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(1).removeParagraph(0);
		paragraph = tableRowOne.getCell(1).addParagraph();
		criarParagrafoTabela(paragraph, "DATA", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(2).removeParagraph(0);
		paragraph = tableRowOne.getCell(2).addParagraph();
		criarParagrafoTabela(paragraph, "ROMANEIO ", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(3).removeParagraph(0);
		paragraph = tableRowOne.getCell(3).addParagraph();
		criarParagrafoTabela(paragraph, "PESO ROMANEIO: ", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(4).removeParagraph(0);
		paragraph = tableRowOne.getCell(4).addParagraph();

		criarParagrafoTabela(paragraph, "NF VENDA", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(5).removeParagraph(0);
		paragraph = tableRowOne.getCell(5).addParagraph();
		criarParagrafoTabela(paragraph, "PESO NF VENDA", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(6).removeParagraph(0);
		paragraph = tableRowOne.getCell(6).addParagraph();
		criarParagrafoTabela(paragraph, "VALOR NF VENDA", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(7).removeParagraph(0);
		paragraph = tableRowOne.getCell(7).addParagraph();
		criarParagrafoTabela(paragraph, "NF REMESSA", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(8).removeParagraph(0);
		paragraph = tableRowOne.getCell(8).addParagraph();
		criarParagrafoTabela(paragraph, "PESO NF REMESSA", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(9).removeParagraph(0);
		paragraph = tableRowOne.getCell(9).addParagraph();
		criarParagrafoTabela(paragraph, "VALOR NF REMESSA", true);

		// CadastroContrato novo_contrato =
		// gerenciar.getContratoSimplificado(recebimentos.get(0).getId_contrato_recebimento());
		cabecalho++;

		int i = cabecalho;

		// checkgens
		boolean nf_remessa_ativo = false;
		boolean nf_venda_ativo = false;

		// checka se ha no minimo uma nf remessa aplicavel
		for (CadastroContrato.Recebimento recebimento : recebimentos) {
			if (recebimento.getNf_remessa_aplicavel() == 1) {
				nf_remessa_ativo = true;
				break;
			}

		}

		// checka se ha no minimo uma nf venda aplicavel
		for (CadastroContrato.Recebimento recebimento : recebimentos) {
			if (recebimento.getNf_venda_aplicavel() == 1) {
				nf_venda_ativo = true;
				break;
			}

		}
		for (CadastroContrato.Recebimento recebimento : recebimentos) {

			String cor = "000000";

			if (checkString(recebimento.getCodigo_nf_venda()) && checkString(recebimento.getCodigo_nf_remessa())) {
				// ok
				cor = "FFFFFF";

			}

			else if (!(checkString(recebimento.getCodigo_nf_venda()))
					&& !(checkString(recebimento.getCodigo_nf_remessa()))) {
				// falta duas notas
				cor = "B0C4DE";

			} else if (!(checkString(recebimento.getCodigo_nf_venda()))
					&& checkString(recebimento.getCodigo_nf_remessa())) {
				// falta apenas nf de venda
				cor = "FFFF00";

			} else if (!(checkString(recebimento.getCodigo_nf_remessa()))
					&& checkString(recebimento.getCodigo_nf_venda())) {
				// falta apenas nf remessa
				cor = "FFD700";

			}

			// contrato ao qual esse recebimento pertence
			tableRowOne = table.getRow(i);
			tableRowOne.getCell(0).removeParagraph(0);
			paragraph = tableRowOne.getCell(0).addParagraph();
			criarParagrafoTabela(paragraph, novo_contrato.getCodigo(), false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(1).removeParagraph(0);
			paragraph = tableRowOne.getCell(1).addParagraph();
			criarParagrafoTabela(paragraph, recebimento.getData_recebimento(), false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(2).removeParagraph(0);
			paragraph = tableRowOne.getCell(2).addParagraph();
			criarParagrafoTabela(paragraph, recebimento.getCodigo_romaneio(), false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(3).removeParagraph(0);
			paragraph = tableRowOne.getCell(3).addParagraph();
			criarParagrafoTabela(paragraph, z.format(recebimento.getPeso_romaneio()), false);
			soma_total_romaneio += recebimento.getPeso_romaneio();

			if (recebimento.getNf_venda_aplicavel() == 1) {

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(4).removeParagraph(0);
				paragraph = tableRowOne.getCell(4).addParagraph();
				criarParagrafoTabela(paragraph, recebimento.getCodigo_nf_venda(), false);
				tableRowOne.getCell(4).getCTTc().addNewTcPr().addNewShd().setFill(cor);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(5).removeParagraph(0);
				paragraph = tableRowOne.getCell(5).addParagraph();
				criarParagrafoTabela(paragraph, z.format(recebimento.getPeso_nf_venda()) + " Kgs", false);
				soma_total_nf_venda += recebimento.getPeso_nf_venda();
				tableRowOne.getCell(5).getCTTc().addNewTcPr().addNewShd().setFill(cor);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(6).removeParagraph(0);
				paragraph = tableRowOne.getCell(6).addParagraph();
				criarParagrafoTabela(paragraph,
						NumberFormat.getCurrencyInstance(ptBr).format((recebimento.getValor_nf_venda().doubleValue())),
						false);
				valor_total_nf_venda = valor_total_nf_venda.add(recebimento.getValor_nf_venda());
				tableRowOne.getCell(6).getCTTc().addNewTcPr().addNewShd().setFill(cor);

			} else {
				tableRowOne = table.getRow(i);
				tableRowOne.getCell(4).removeParagraph(0);
				paragraph = tableRowOne.getCell(4).addParagraph();
				criarParagrafoTabela(paragraph, "Não Aplicável", false);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(5).removeParagraph(0);
				paragraph = tableRowOne.getCell(5).addParagraph();
				criarParagrafoTabela(paragraph, "Não Aplicável", false);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(6).removeParagraph(0);
				paragraph = tableRowOne.getCell(6).addParagraph();
				criarParagrafoTabela(paragraph, "Não Aplicável", false);

			}

			if (recebimento.getNf_remessa_aplicavel() == 1) {

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(7).removeParagraph(0);
				paragraph = tableRowOne.getCell(7).addParagraph();
				criarParagrafoTabela(paragraph, recebimento.getCodigo_nf_remessa(), false);
				tableRowOne.getCell(7).getCTTc().addNewTcPr().addNewShd().setFill(cor);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(8).removeParagraph(0);
				paragraph = tableRowOne.getCell(8).addParagraph();
				criarParagrafoTabela(paragraph, z.format(recebimento.getPeso_nf_remessa()) + " Kgs", false);
				soma_total_nf_remessa += recebimento.getPeso_nf_remessa();
				tableRowOne.getCell(8).getCTTc().addNewTcPr().addNewShd().setFill(cor);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(9).removeParagraph(0);
				paragraph = tableRowOne.getCell(9).addParagraph();
				criarParagrafoTabela(paragraph, NumberFormat.getCurrencyInstance(ptBr)
						.format((recebimento.getValor_nf_remessa().doubleValue())), false);
				valor_total_nf_remessa = valor_total_nf_remessa.add(recebimento.getValor_nf_remessa());
				tableRowOne.getCell(9).getCTTc().addNewTcPr().addNewShd().setFill(cor);

			} else {
				tableRowOne = table.getRow(i);
				tableRowOne.getCell(7).removeParagraph(0);
				paragraph = tableRowOne.getCell(7).addParagraph();
				criarParagrafoTabela(paragraph, "Não Aplicável", false);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(8).removeParagraph(0);
				paragraph = tableRowOne.getCell(8).addParagraph();
				criarParagrafoTabela(paragraph, "Não Aplicável", false);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(9).removeParagraph(0);
				paragraph = tableRowOne.getCell(9).addParagraph();
				criarParagrafoTabela(paragraph, "Não Aplicável", false);

			}

			i++;

		}
		if (incluir_transferencias_recebimentos) {

			for (CadastroContrato.CadastroTransferenciaRecebimento enviado_via_trans : lista_transferencias_recebimento_remetente_local) {

				String cor = "FFFFFF";

				// contrato ao qual esse recebimento pertence
				tableRowOne = table.getRow(i);
				tableRowOne.getCell(0).removeParagraph(0);
				paragraph = tableRowOne.getCell(0).addParagraph();
				criarParagrafoTabela(paragraph, novo_contrato.getCodigo(), false);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(1).removeParagraph(0);
				paragraph = tableRowOne.getCell(1).addParagraph();
				criarParagrafoTabela(paragraph, enviado_via_trans.getData(), false);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(2).removeParagraph(0);
				paragraph = tableRowOne.getCell(2).addParagraph();
				criarParagrafoTabela(paragraph, "-Transferencia", false);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(3).removeParagraph(0);
				paragraph = tableRowOne.getCell(3).addParagraph();
				criarParagrafoTabela(paragraph, "-" + z.format(enviado_via_trans.getQuantidade()), false);
				soma_total_trans_negativa += enviado_via_trans.getQuantidade();

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(4).removeParagraph(0);
				paragraph = tableRowOne.getCell(4).addParagraph();
				criarParagrafoTabela(paragraph, "Não Aplicável", false);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(5).removeParagraph(0);
				paragraph = tableRowOne.getCell(5).addParagraph();
				criarParagrafoTabela(paragraph, "Não Aplicável", false);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(6).removeParagraph(0);
				paragraph = tableRowOne.getCell(6).addParagraph();
				criarParagrafoTabela(paragraph, "Não Aplicável", false);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(7).removeParagraph(0);
				paragraph = tableRowOne.getCell(7).addParagraph();
				criarParagrafoTabela(paragraph, "Não Aplicável", false);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(8).removeParagraph(0);
				paragraph = tableRowOne.getCell(8).addParagraph();
				criarParagrafoTabela(paragraph, "Não Aplicável", false);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(9).removeParagraph(0);
				paragraph = tableRowOne.getCell(9).addParagraph();
				criarParagrafoTabela(paragraph, "Não Aplicável", false);

				i++;

			}

			for (CadastroContrato.CadastroTransferenciaRecebimento recebido_via_trans : lista_transferencias_recebimento_destinatario_local) {

				String cor = "FFFFFF";

				// contrato ao qual esse recebimento pertence
				tableRowOne = table.getRow(i);
				tableRowOne.getCell(0).removeParagraph(0);
				paragraph = tableRowOne.getCell(0).addParagraph();
				criarParagrafoTabela(paragraph, novo_contrato.getCodigo(), false);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(1).removeParagraph(0);
				paragraph = tableRowOne.getCell(1).addParagraph();
				criarParagrafoTabela(paragraph, recebido_via_trans.getData(), false);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(2).removeParagraph(0);
				paragraph = tableRowOne.getCell(2).addParagraph();
				criarParagrafoTabela(paragraph, "+Transferencia", false);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(3).removeParagraph(0);
				paragraph = tableRowOne.getCell(3).addParagraph();
				criarParagrafoTabela(paragraph, "+" + z.format(recebido_via_trans.getQuantidade()), false);
				soma_total_trans_positiva += recebido_via_trans.getQuantidade();

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(4).removeParagraph(0);
				paragraph = tableRowOne.getCell(4).addParagraph();
				criarParagrafoTabela(paragraph, "Não Aplicável", false);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(5).removeParagraph(0);
				paragraph = tableRowOne.getCell(5).addParagraph();
				criarParagrafoTabela(paragraph, "Não Aplicável", false);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(6).removeParagraph(0);
				paragraph = tableRowOne.getCell(6).addParagraph();
				criarParagrafoTabela(paragraph, "Não Aplicável", false);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(7).removeParagraph(0);
				paragraph = tableRowOne.getCell(7).addParagraph();
				criarParagrafoTabela(paragraph, "Não Aplicável", false);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(8).removeParagraph(0);
				paragraph = tableRowOne.getCell(8).addParagraph();
				criarParagrafoTabela(paragraph, "Não Aplicável", false);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(9).removeParagraph(0);
				paragraph = tableRowOne.getCell(9).addParagraph();
				criarParagrafoTabela(paragraph, "Não Aplicável", false);

				i++;
			}
		}
		// somatorias
		// peso de romaneios

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(3).removeParagraph(0);
		paragraph = tableRowOne.getCell(3).addParagraph();
		criarParagrafoTabela(paragraph,
				" " + z.format(soma_total_romaneio) + " kgs / " + (z.format((soma_total_romaneio / 60))) + " sacos ",
				true);
		// pesos de nf venda

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(5).removeParagraph(0);
		paragraph = tableRowOne.getCell(5).addParagraph();
		criarParagrafoTabela(paragraph,
				" " + z.format(soma_total_nf_venda) + " kgs / " + (z.format((soma_total_nf_venda / 60))) + " sacos ",
				true);

		// valor nf venda

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(6).removeParagraph(0);
		paragraph = tableRowOne.getCell(6).addParagraph();
		criarParagrafoTabela(paragraph, NumberFormat.getCurrencyInstance(ptBr).format(valor_total_nf_venda), true);

		// peso nf remessa

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(8).removeParagraph(0);
		paragraph = tableRowOne.getCell(8).addParagraph();
		criarParagrafoTabela(paragraph, " " + z.format(soma_total_nf_remessa) + " kgs / "
				+ (z.format((soma_total_nf_remessa / 60))) + " sacos ", true);

		// valor nf remessa

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(9).removeParagraph(0);
		paragraph = tableRowOne.getCell(9).addParagraph();
		criarParagrafoTabela(paragraph, NumberFormat.getCurrencyInstance(ptBr).format(valor_total_nf_remessa), true);

		// informacoes de total
		i += 2;
		// peso real
		tableRowOne = table.getRow(i);
		tableRowOne.getCell(0).removeParagraph(0);
		paragraph = tableRowOne.getCell(0).addParagraph();
		criarParagrafoTabela(paragraph, "Peso Total Romaneios:", false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(1).removeParagraph(0);
		paragraph = tableRowOne.getCell(1).addParagraph();
		criarParagrafoTabela(paragraph,
				" " + z.format(soma_total_romaneio) + " kgs / " + (z.format((soma_total_romaneio / 60))) + " sacos ",
				true);

		// pesos de nf venda
		tableRowOne = table.getRow(i);
		tableRowOne.getCell(2).removeParagraph(0);
		paragraph = tableRowOne.getCell(2).addParagraph();
		criarParagrafoTabela(paragraph, "Peso Total NFV's:", false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(3).removeParagraph(0);
		paragraph = tableRowOne.getCell(3).addParagraph();
		if (nf_venda_ativo) {
			criarParagrafoTabela(paragraph, " " + z.format(soma_total_nf_venda) + " kgs / "
					+ (z.format((soma_total_nf_venda / 60))) + " sacos ", true);
		} else {
			criarParagrafoTabela(paragraph, "Não Aplicável", true);
		}

		// valor nf venda

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(4).removeParagraph(0);
		paragraph = tableRowOne.getCell(4).addParagraph();
		criarParagrafoTabela(paragraph, "Valor Total NFV's:", false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(5).removeParagraph(0);
		paragraph = tableRowOne.getCell(5).addParagraph();
		if (nf_venda_ativo) {
			criarParagrafoTabela(paragraph, NumberFormat.getCurrencyInstance(ptBr).format(valor_total_nf_venda), true);
		} else {
			criarParagrafoTabela(paragraph, "Não Aplicável", true);
		}
		// peso nf remessa
		tableRowOne = table.getRow(i);
		tableRowOne.getCell(6).removeParagraph(0);
		paragraph = tableRowOne.getCell(6).addParagraph();
		criarParagrafoTabela(paragraph, "Peso Total NFR's:", false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(7).removeParagraph(0);
		paragraph = tableRowOne.getCell(7).addParagraph();
		if (nf_remessa_ativo) {
			criarParagrafoTabela(paragraph, " " + z.format(soma_total_nf_remessa) + " kgs / "
					+ (z.format((soma_total_nf_remessa / 60))) + " sacos ", true);
		} else {
			criarParagrafoTabela(paragraph, "Não Aplicável", true);
		}

		// valor nf remessa

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(8).removeParagraph(0);
		paragraph = tableRowOne.getCell(8).addParagraph();
		criarParagrafoTabela(paragraph, "Valor Total NFR's:", false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(9).removeParagraph(0);
		paragraph = tableRowOne.getCell(9).addParagraph();
		if (nf_remessa_ativo) {
			criarParagrafoTabela(paragraph, NumberFormat.getCurrencyInstance(ptBr).format(valor_total_nf_remessa),
					true);
		} else {
			criarParagrafoTabela(paragraph, "Não Aplicável", true);
		}
		i++;

		// total a receber em kgs

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(0).removeParagraph(0);
		paragraph = tableRowOne.getCell(0).addParagraph();
		criarParagrafoTabela(paragraph, "Total a Receber:", false);

		double quantidade_total_sacos = 0;
		double quantidade_total_kgs = 0;

		// quantidade para os totais

		if (novo_contrato.getMedida().equalsIgnoreCase("KG")) {
			quantidade_total_kgs = novo_contrato.getQuantidade();
			quantidade_total_sacos = quantidade_total_kgs / 60;
		} else if (novo_contrato.getMedida().equalsIgnoreCase("Sacos")) {
			quantidade_total_sacos = novo_contrato.getQuantidade();
			quantidade_total_kgs = quantidade_total_sacos * 60;
		}

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(1).removeParagraph(0);
		paragraph = tableRowOne.getCell(1).addParagraph();

		criarParagrafoTabela(paragraph, " " + z.format(quantidade_total_kgs - soma_total_romaneio) + " kgs / "
				+ (z.format(((quantidade_total_kgs - soma_total_romaneio) / 60))) + " sacos ", true);

		// pesos de nf
		tableRowOne = table.getRow(i);
		tableRowOne.getCell(2).removeParagraph(0);
		paragraph = tableRowOne.getCell(2).addParagraph();
		criarParagrafoTabela(paragraph, "Total a emitir NFV's:", false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(3).removeParagraph(0);
		paragraph = tableRowOne.getCell(3).addParagraph();
		if (nf_venda_ativo) {

			criarParagrafoTabela(paragraph, " " + z.format(quantidade_total_kgs - soma_total_nf_venda) + " kgs / "
					+ (z.format(((quantidade_total_kgs - soma_total_nf_venda) / 60))) + " sacos ", true);

		} else {
			criarParagrafoTabela(paragraph, "Não Aplicável", true);

		}

		// pesos de nf remessa
		tableRowOne = table.getRow(i);
		tableRowOne.getCell(6).removeParagraph(0);
		paragraph = tableRowOne.getCell(6).addParagraph();
		criarParagrafoTabela(paragraph, "Total a emitir NFR's:", false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(7).removeParagraph(0);
		paragraph = tableRowOne.getCell(7).addParagraph();
		if (nf_remessa_ativo) {

			criarParagrafoTabela(paragraph, " " + z.format(quantidade_total_kgs - soma_total_nf_remessa) + " kgs / "
					+ (z.format(((quantidade_total_kgs - soma_total_nf_remessa) / 60))) + " sacos ", true);

		} else {
			criarParagrafoTabela(paragraph, "Não Aplicável", true);

		}
		i++;

		// texto do status

		String texto = "";

		texto += ("\nQuantidade Total Contratada: " + z.format(quantidade_kg) + " kgs | " + z.format(quantidade_kg / 60)
				+ " sacos\n\n");

		texto += ("Soma do Total dos Romaneios: " + z.format(soma_total_romaneio) + " kgs | "
				+ z.format(soma_total_romaneio / 60) + " sacos\n");

		if (incluir_transferencias_recebimentos) {
			texto += ("Soma do Total dos Transferência Negativa: -" + z.format(soma_total_trans_negativa) + " kgs | "
					+ z.format(soma_total_trans_negativa / 60) + " sacos\n");

			texto += ("Soma do Total dos Transferência Positiva: +" + z.format(soma_total_trans_positiva) + " kgs | "
					+ z.format(soma_total_trans_positiva / 60) + " sacos\n");

			texto += ("Somatória Final dos Recebimentos: "
					+ z.format(soma_total_romaneio + soma_total_trans_positiva - soma_total_trans_negativa) + " kgs | "
					+ z.format((soma_total_romaneio + soma_total_trans_positiva - soma_total_trans_negativa) / 60)
					+ " sacos\n");

		} else {
			texto += ("Somatória Final dos Recebimentos: " + z.format(soma_total_romaneio) + " kgs | "
					+ z.format(soma_total_trans_positiva / 60) + " sacos\n");

		}

		double diferenca = quantidade_kg
				- (soma_total_romaneio + soma_total_trans_positiva - soma_total_trans_negativa);

		texto += "\nStatus Gerado automaticamente: \n";

		if (diferenca == 0) {
			texto = texto + "[Recebimento] [Concluído]\n";
		} else if (diferenca > 0) {
			texto = texto + "[Recebimento] [Incompleto], [falta] [receber] [[" + z.format(diferenca) + "] Kgs | "
					+ z.format(diferenca / 60) + " sacos\n";

		} else if (diferenca < 0) {
			texto = texto + "[Recebimento] [Excedido], [excedeu] [" + z.format(diferenca) + "] Kgs | "
					+ z.format(diferenca / 60) + " sacos\n";

		}

		String texto_nf_venda = "";
		if (nf_venda_ativo) {
			double diferenca_nf_venda = quantidade_total_kgs - soma_total_nf_venda;
			if (diferenca_nf_venda == 0) {
				texto_nf_venda = texto_nf_venda + "\nEmissão de NF's de Venda Concluído\n";
			} else if (diferenca_nf_venda > 0) {
				texto_nf_venda = texto_nf_venda + "\nEmissão de NF's de Venda Incompleto, falta emitir "
						+ z.format(diferenca_nf_venda) + " Kgs | " + z.format(diferenca_nf_venda / 60) + " sacos\n";

			} else if (diferenca_nf_venda < 0) {
				texto_nf_venda = texto_nf_venda + "\nEmissão de NF's Venda Excedido, excedeu "
						+ z.format(diferenca_nf_venda) + " Kgs | " + z.format(diferenca_nf_venda / 60) + " sacos\n";

			}
		} else {
			texto_nf_venda = "\nEmissão de NF's Venda Não Aplicável";
		}

		String texto_nf_remessa = "";
		if (nf_remessa_ativo) {
			double diferenca_nf_remessa = quantidade_total_kgs - soma_total_nf_remessa;
			if (diferenca_nf_remessa == 0) {
				texto_nf_remessa = texto_nf_remessa + "Emissão de NF's de Remessa Concluído\n";
			} else if (diferenca_nf_remessa > 0) {
				texto_nf_remessa = texto_nf_remessa + "Emissão de NF's de Remessa Incompleto, falta emitir "
						+ z.format(diferenca_nf_remessa) + " Kgs | " + z.format(diferenca_nf_remessa / 60) + " sacos\n";

			} else if (diferenca_nf_remessa < 0) {
				texto_nf_remessa = texto_nf_remessa + "Emissão de NF's Remessa Excedido, excedeu "
						+ z.format(diferenca_nf_remessa) + " Kgs | " + z.format(diferenca_nf_remessa / 60) + " sacos\n";

			}
		} else {
			texto_nf_remessa = "Emissão de NF's Remessa Não Aplicável";
		}

		substituirTexto(texto + texto_nf_venda + texto_nf_remessa);
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Entity
	class DadosTabelaExcel {

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Contratos");
		int rownum = 0;

	}

	// variaves de esquema
	HSSFDataFormat numberFormatRecebimentos;
	HSSFFont newFont_brancaRecebimentos;
	CellStyle celula_fundo_verde_texto_brancoRecebimentos;
	HSSFFont newFont_pretaRecebimentos;
	CellStyle celula_fundo_branco_texto_pretoRecebimentos;
	CellStyle numberStyleRecebimentos;
	CellStyle pesoStyleRecebimentos;
	CellStyle numberStyleFundoVerdeTextoBrancoRecebimentos;
	CellStyle numberStyleFundoBrancoTextoPretoRecebimentos;
	CellStyle celula_fundo_branco_texto_azulRecebimentos;
	HSSFFont newFont_azulRecebimentos;
	CellStyle celula_fundo_branco_texto_vermelhoRecebimentos;
	HSSFFont newFont_vermelhaRecebimentos;
	HSSFFont newFont_verdeRecebimentos;
	CellStyle celula_fundo_branco_texto_verdeRecebimentos;
	CellStyle valorStyleFundoVerdeTextoBrancoRecebimentos;

	public void criarEsquemaEstilo(HSSFWorkbook workbook) {
		numberFormatRecebimentos = workbook.createDataFormat();

		newFont_brancaRecebimentos = workbook.createFont();
		newFont_brancaRecebimentos.setBold(true);
		newFont_brancaRecebimentos.setColor(IndexedColors.WHITE.getIndex());
		newFont_brancaRecebimentos.setFontName("Calibri");
		newFont_brancaRecebimentos.setItalic(false);
		newFont_brancaRecebimentos.setFontHeight((short) (11 * 20));

		// estilo para cabecalho fundo verde
		celula_fundo_verde_texto_brancoRecebimentos = workbook.createCellStyle();
		celula_fundo_verde_texto_brancoRecebimentos.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		celula_fundo_verde_texto_brancoRecebimentos.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		celula_fundo_verde_texto_brancoRecebimentos.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_verde_texto_brancoRecebimentos.setVerticalAlignment(VerticalAlignment.CENTER);

		celula_fundo_verde_texto_brancoRecebimentos.setFont(newFont_brancaRecebimentos);

		newFont_pretaRecebimentos = workbook.createFont();
		newFont_pretaRecebimentos.setColor(IndexedColors.BLACK.getIndex());
		newFont_pretaRecebimentos.setFontName("Calibri");
		newFont_pretaRecebimentos.setItalic(false);
		newFont_pretaRecebimentos.setFontHeight((short) (11 * 20));

		celula_fundo_branco_texto_pretoRecebimentos = workbook.createCellStyle();
		celula_fundo_branco_texto_pretoRecebimentos.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_branco_texto_pretoRecebimentos.setVerticalAlignment(VerticalAlignment.CENTER);
		celula_fundo_branco_texto_pretoRecebimentos.setFont(newFont_pretaRecebimentos);

		// celula para numero alinhado ao centro
		numberStyleRecebimentos = workbook.createCellStyle();
		numberStyleRecebimentos.setDataFormat(numberFormatRecebimentos.getFormat("R$ #,##0.00"));
		numberStyleRecebimentos.setAlignment(HorizontalAlignment.CENTER);
		numberStyleRecebimentos.setVerticalAlignment(VerticalAlignment.CENTER);

		// celula para numero alinhado ao centro
		pesoStyleRecebimentos = workbook.createCellStyle();
		pesoStyleRecebimentos.setDataFormat(numberFormatRecebimentos.getFormat("#,##0.00"));
		pesoStyleRecebimentos.setAlignment(HorizontalAlignment.CENTER);
		pesoStyleRecebimentos.setVerticalAlignment(VerticalAlignment.CENTER);

		numberStyleFundoVerdeTextoBrancoRecebimentos = workbook.createCellStyle();
		numberStyleFundoVerdeTextoBrancoRecebimentos.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		numberStyleFundoVerdeTextoBrancoRecebimentos.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		numberStyleFundoVerdeTextoBrancoRecebimentos.setAlignment(HorizontalAlignment.CENTER);
		numberStyleFundoVerdeTextoBrancoRecebimentos.setVerticalAlignment(VerticalAlignment.CENTER);
		numberStyleFundoVerdeTextoBrancoRecebimentos.setDataFormat(numberFormatRecebimentos.getFormat("#,##0.00"));

		numberStyleFundoVerdeTextoBrancoRecebimentos.setFont(newFont_brancaRecebimentos);

		numberStyleFundoBrancoTextoPretoRecebimentos = workbook.createCellStyle();
		numberStyleFundoBrancoTextoPretoRecebimentos.setAlignment(HorizontalAlignment.LEFT);
		numberStyleFundoBrancoTextoPretoRecebimentos.setVerticalAlignment(VerticalAlignment.CENTER);
		numberStyleFundoBrancoTextoPretoRecebimentos.setDataFormat(numberFormatRecebimentos.getFormat("#,##0.00"));
		numberStyleFundoBrancoTextoPretoRecebimentos.setFont(newFont_pretaRecebimentos);

		valorStyleFundoVerdeTextoBrancoRecebimentos = workbook.createCellStyle();
		valorStyleFundoVerdeTextoBrancoRecebimentos.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		valorStyleFundoVerdeTextoBrancoRecebimentos.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		valorStyleFundoVerdeTextoBrancoRecebimentos.setAlignment(HorizontalAlignment.CENTER);
		valorStyleFundoVerdeTextoBrancoRecebimentos.setVerticalAlignment(VerticalAlignment.CENTER);
		valorStyleFundoVerdeTextoBrancoRecebimentos.setDataFormat(numberFormatRecebimentos.getFormat("R$ #,##0.00"));
		valorStyleFundoVerdeTextoBrancoRecebimentos.setFont(newFont_brancaRecebimentos);

		// celular de contrato normal
		newFont_verdeRecebimentos = workbook.createFont();
		newFont_verdeRecebimentos.setColor(IndexedColors.GREEN.getIndex());
		newFont_verdeRecebimentos.setFontName("Calibri");
		newFont_verdeRecebimentos.setItalic(false);
		newFont_verdeRecebimentos.setFontHeight((short) (11 * 20));

		celula_fundo_branco_texto_verdeRecebimentos = workbook.createCellStyle();
		celula_fundo_branco_texto_verdeRecebimentos.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_branco_texto_verdeRecebimentos.setVerticalAlignment(VerticalAlignment.CENTER);
		celula_fundo_branco_texto_verdeRecebimentos.setFont(newFont_verdeRecebimentos);

		// celular de sub-contrato n
		newFont_vermelhaRecebimentos = workbook.createFont();
		newFont_vermelhaRecebimentos.setColor(IndexedColors.RED.getIndex());
		newFont_vermelhaRecebimentos.setFontName("Calibri");
		newFont_vermelhaRecebimentos.setItalic(false);
		newFont_vermelhaRecebimentos.setFontHeight((short) (11 * 20));

		celula_fundo_branco_texto_vermelhoRecebimentos = workbook.createCellStyle();
		celula_fundo_branco_texto_vermelhoRecebimentos.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_branco_texto_vermelhoRecebimentos.setVerticalAlignment(VerticalAlignment.CENTER);
		celula_fundo_branco_texto_vermelhoRecebimentos.setFont(newFont_vermelhaRecebimentos);

		// celula de ganho potencial

		// celular de sub-contrato n
		newFont_azulRecebimentos = workbook.createFont();
		newFont_azulRecebimentos.setColor(IndexedColors.BLUE.getIndex());
		newFont_azulRecebimentos.setFontName("Calibri");
		newFont_azulRecebimentos.setItalic(false);
		newFont_azulRecebimentos.setFontHeight((short) (11 * 20));

		celula_fundo_branco_texto_azulRecebimentos = workbook.createCellStyle();
		celula_fundo_branco_texto_azulRecebimentos.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_branco_texto_azulRecebimentos.setVerticalAlignment(VerticalAlignment.CENTER);
		celula_fundo_branco_texto_azulRecebimentos.setFont(newFont_azulRecebimentos);
	}

	public DadosTabelaExcel criarTabelaRecebimentosExcel(HSSFWorkbook workbook, HSSFSheet sheet, int rownum,
			ArrayList<RecebimentoCompleto> recebimentos, CadastroContrato novo_contrato) {
		// XWPFParagraph par = document_global.createParagraph();

		NumberFormat z = NumberFormat.getNumberInstance();

		Locale ptBr = new Locale("pt", "BR");

		// criarParagrafo(1);
		// linhas x colunas

		GerenciarBancoTransferenciaRecebimento gerenciar_transferencias = null;
		ArrayList<CadastroContrato.CadastroTransferenciaRecebimento> lista_transferencias_recebimento_remetente_local = new ArrayList<>();
		ArrayList<CadastroContrato.CadastroTransferenciaRecebimento> lista_transferencias_recebimento_destinatario_local = new ArrayList<>();

		if (incluir_transferencias_recebimentos) {

			gerenciar_transferencias = new GerenciarBancoTransferenciaRecebimento();

			lista_transferencias_recebimento_remetente_local = gerenciar_transferencias
					.getTransferenciasRemetente(novo_contrato.getId());

			lista_transferencias_recebimento_destinatario_local = gerenciar_transferencias
					.getTransferenciaDestinatario(novo_contrato.getId());

		}

		double soma_total_romaneio = 0;
		double soma_total_trans_negativa = 0;
		double soma_total_trans_positiva = 0;
		double soma_total_nf_venda = 0;

		double soma_total_nf_remessa = 0;

		BigDecimal valor_total_nf_venda = BigDecimal.ZERO;
		BigDecimal valor_total_nf_remessa = BigDecimal.ZERO;

		double quantidade_kg = 0;
		double quantidade_sacos = 0;

		if (novo_contrato.getMedida().equalsIgnoreCase("KG")) {
			quantidade_kg = novo_contrato.getQuantidade();
			quantidade_sacos = quantidade_kg / 60;
		} else if (novo_contrato.getMedida().equalsIgnoreCase("Sacos")) {
			quantidade_sacos = novo_contrato.getQuantidade();
			quantidade_kg = quantidade_sacos * 60;
		}

		// compradores x vendedores

		// safra
		String safra = novo_contrato.getModelo_safra().getProduto().getNome_produto() + " "
				+ novo_contrato.getModelo_safra().getProduto().getTransgenia() + " "
				+ novo_contrato.getModelo_safra().getAno_plantio() + "/"
				+ novo_contrato.getModelo_safra().getAno_colheita();

		String texto_info_contrato = "CTR: " + novo_contrato.getCodigo() + " " + safra + " Quantidade Total: "
				+ z.format(quantidade_kg) + " kgs | " + z.format(quantidade_sacos) + " sacos "
				+ NumberFormat.getCurrencyInstance(ptBr).format(novo_contrato.getValor_produto()) + " por "
				+ novo_contrato.getMedida() + " totalizando: "
				+ NumberFormat.getCurrencyInstance(ptBr).format(novo_contrato.getValor_a_pagar().doubleValue());

		int cellnum = 0;

		Cell cell;
		Row row;

		row = sheet.createRow(rownum);
		cell = row.createCell(cellnum);
		cell.setCellStyle(celula_fundo_branco_texto_pretoRecebimentos);
		cell.setCellValue(texto_info_contrato);
		sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, cellnum, 10));

		rownum++;

		// linha com nome compradores x vendedores

		CadastroCliente compradores[] = novo_contrato.getCompradores();
		CadastroCliente vendedores[] = novo_contrato.getVendedores();

		String nome_vendedores = "";
		String nome_compradores = "";

		if (compradores[0] != null) {
			if (compradores[0].getTipo_pessoa() == 0) {
				// pessoa fisica
				nome_compradores = compradores[0].getNome_empresarial();
			} else {
				nome_compradores = compradores[0].getNome_fantaia();

			}
		}
		if (compradores[1] != null) {
			if (compradores[1].getTipo_pessoa() == 0) {
				// pessoa fisica
				nome_compradores = nome_compradores + ", " + compradores[1].getNome_empresarial();
			} else {
				nome_compradores = nome_compradores + ", " + compradores[1].getNome_fantaia();

			}
		}
		if (vendedores[0] != null) {
			if (vendedores[0].getTipo_pessoa() == 0) {
				nome_vendedores = vendedores[0].getNome_empresarial();
			} else {
				nome_vendedores = vendedores[0].getNome_fantaia();
			}
		}

		if (vendedores[1] != null) {
			if (vendedores[1].getTipo_pessoa() == 0) {
				nome_vendedores = nome_vendedores + ", " + vendedores[1].getNome_empresarial();
			} else {
				nome_vendedores = nome_vendedores + ", " + vendedores[1].getNome_fantaia();
			}
		}

		String linha_nome_compradores_vendedores = novo_contrato.getNomes_compradores().toUpperCase() + " X "
				+ novo_contrato.getNomes_vendedores().toUpperCase();

		row = sheet.createRow(rownum);
		cell = row.createCell(cellnum);
		cell.setCellStyle(celula_fundo_branco_texto_pretoRecebimentos);
		cell.setCellValue(linha_nome_compradores_vendedores);
		sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, cellnum, 10));

		rownum++;

		row = sheet.createRow(rownum);

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_brancoRecebimentos);
		cell.setCellValue("CONTRATO");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_brancoRecebimentos);
		cell.setCellValue("DATA");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_brancoRecebimentos);
		cell.setCellValue("CODIGO ROMANEIO");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_brancoRecebimentos);
		cell.setCellValue("PESO ROMANEIO:".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_brancoRecebimentos);
		cell.setCellValue("NF VENDA".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_brancoRecebimentos);
		cell.setCellValue("PESO NF VENDA".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_brancoRecebimentos);
		cell.setCellValue("VALOR NF VENDA".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_brancoRecebimentos);
		cell.setCellValue("NF REMESSA".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_brancoRecebimentos);
		cell.setCellValue("PESO NF REMESSA".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_brancoRecebimentos);
		cell.setCellValue("VALOR NF REMESSA".toUpperCase());

		rownum += 1;
		int primeira_linha = rownum + 1;
		int ultima_linha = primeira_linha;

		// checkgens
		boolean nf_remessa_ativo = false;
		boolean nf_venda_ativo = false;

		// checka se ha no minimo uma nf remessa aplicavel
		for (CadastroContrato.Recebimento recebimento : recebimentos) {
			if (recebimento.getNf_remessa_aplicavel() == 1) {
				nf_remessa_ativo = true;
				break;
			}

		}

		// checka se ha no minimo uma nf venda aplicavel
		for (CadastroContrato.Recebimento recebimento : recebimentos) {
			if (recebimento.getNf_venda_aplicavel() == 1) {
				nf_venda_ativo = true;
				break;
			}

		}

		for (CadastroContrato.Recebimento recebimento : recebimentos) {

			String cor = "000000";

			if (checkString(recebimento.getCodigo_nf_venda()) && checkString(recebimento.getCodigo_nf_remessa())) {
				// ok
				cor = "FFFFFF";

			}

			else if (!(checkString(recebimento.getCodigo_nf_venda()))
					&& !(checkString(recebimento.getCodigo_nf_remessa()))) {
				// falta duas notas
				cor = "B0C4DE";

			} else if (!(checkString(recebimento.getCodigo_nf_venda()))
					&& checkString(recebimento.getCodigo_nf_remessa())) {
				// falta apenas nf de venda
				cor = "FFFF00";

			} else if (!(checkString(recebimento.getCodigo_nf_remessa()))
					&& checkString(recebimento.getCodigo_nf_venda())) {
				// falta apenas nf remessa
				cor = "FFD700";

			}

			cellnum = 0;
			row = sheet.createRow(rownum);

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_branco_texto_pretoRecebimentos);
			cell.setCellValue(novo_contrato.getCodigo());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_branco_texto_pretoRecebimentos);
			cell.setCellValue(recebimento.getData_recebimento());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_branco_texto_pretoRecebimentos);
			cell.setCellValue(recebimento.getCodigo_romaneio());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(pesoStyleRecebimentos);
			cell.setCellValue(recebimento.getPeso_romaneio());
			soma_total_romaneio += recebimento.getPeso_romaneio();

			if (recebimento.getNf_venda_aplicavel() == 1) {

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoRecebimentos);
				cell.setCellValue(recebimento.getCodigo_nf_venda());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(pesoStyleRecebimentos);
				cell.setCellValue(recebimento.getPeso_nf_venda());
				soma_total_nf_venda += recebimento.getPeso_nf_venda();

				cell = row.createCell(cellnum++);
				cell.setCellStyle(numberStyleRecebimentos);
				cell.setCellValue(recebimento.getValor_nf_venda().doubleValue());
				valor_total_nf_venda = valor_total_nf_venda.add(recebimento.getValor_nf_venda());

			} else {
				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoRecebimentos);
				cell.setCellValue("Não Aplicavel");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoRecebimentos);
				cell.setCellValue("Não Aplicavel");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoRecebimentos);
				cell.setCellValue("Não Aplicavel");

			}

			if (recebimento.getNf_remessa_aplicavel() == 1) {

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoRecebimentos);
				cell.setCellValue(recebimento.getCodigo_nf_remessa());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(pesoStyleRecebimentos);
				cell.setCellValue(recebimento.getPeso_nf_remessa());
				soma_total_nf_remessa += recebimento.getPeso_nf_remessa();

				cell = row.createCell(cellnum++);
				cell.setCellStyle(numberStyleRecebimentos);
				cell.setCellValue(recebimento.getValor_nf_remessa().doubleValue());
				valor_total_nf_remessa = valor_total_nf_remessa.add(recebimento.getValor_nf_remessa());

			} else {

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoRecebimentos);
				cell.setCellValue("Não Aplicável");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoRecebimentos);
				cell.setCellValue("Não Aplicável");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoRecebimentos);
				cell.setCellValue("Não Aplicável");

			}

			rownum++;
			ultima_linha = rownum;
		}

		if (incluir_transferencias_recebimentos) {

			for (CadastroContrato.CadastroTransferenciaRecebimento enviado_via_trans : lista_transferencias_recebimento_remetente_local) {

				String cor = "FFFFFF";

				cellnum = 0;
				row = sheet.createRow(rownum);

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoRecebimentos);
				cell.setCellValue(novo_contrato.getCodigo());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoRecebimentos);
				cell.setCellValue(enviado_via_trans.getData());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoRecebimentos);
				cell.setCellValue("-Transferencia");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(pesoStyleRecebimentos);
				cell.setCellValue(enviado_via_trans.getQuantidade());
				soma_total_trans_negativa += enviado_via_trans.getQuantidade();

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoRecebimentos);
				cell.setCellValue("Não Aplicável");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoRecebimentos);
				cell.setCellValue("Não Aplicável");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoRecebimentos);
				cell.setCellValue("Não Aplicável");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoRecebimentos);
				cell.setCellValue("Não Aplicável");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoRecebimentos);
				cell.setCellValue("Não Aplicável");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoRecebimentos);
				cell.setCellValue("Não Aplicável");

				rownum++;
				ultima_linha = rownum;
			}

			for (CadastroContrato.CadastroTransferenciaRecebimento recebido_via_trans : lista_transferencias_recebimento_destinatario_local) {

				String cor = "FFFFFF";

				cellnum = 0;
				row = sheet.createRow(rownum);

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoRecebimentos);
				cell.setCellValue(novo_contrato.getCodigo());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoRecebimentos);
				cell.setCellValue(recebido_via_trans.getData());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoRecebimentos);
				cell.setCellValue("+Transferencia");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(pesoStyleRecebimentos);
				cell.setCellValue(recebido_via_trans.getQuantidade());
				soma_total_trans_positiva += recebido_via_trans.getQuantidade();

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoRecebimentos);
				cell.setCellValue("Não Aplicável");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoRecebimentos);
				cell.setCellValue("Não Aplicável");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoRecebimentos);
				cell.setCellValue("Não Aplicável");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoRecebimentos);
				cell.setCellValue("Não Aplicável");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoRecebimentos);
				cell.setCellValue("Não Aplicável");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_pretoRecebimentos);
				cell.setCellValue("Não Aplicável");

				rownum++;
				ultima_linha = rownum;
			}
		}

		// pular linha
		rownum += 1;

		// somatorias
		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		int celula_soma_peso = rownum + 1;

		// somatoria de pesos
		cell = row.createCell(2);
		cell.setCellStyle(celula_fundo_branco_texto_pretoRecebimentos);
		cell.setCellValue("Soma Final:");

		cell = row.createCell(3);
		cell.setCellStyle(numberStyleFundoVerdeTextoBrancoRecebimentos);
		cell.setCellType(CellType.FORMULA);
		String formula = "SUM(D" + primeira_linha + ":D" + ultima_linha + ")";
		cell.setCellFormula(formula);

		cell = row.createCell(4);
		cell.setCellStyle(numberStyleFundoBrancoTextoPretoRecebimentos);
		cell.setCellType(CellType.FORMULA);
		formula = "SUM(D" + (rownum + 1) + "/60)";
		cell.setCellFormula(formula);

		// somatoria de peso de nf venda
		cell = row.createCell(5);
		cell.setCellStyle(numberStyleFundoVerdeTextoBrancoRecebimentos);
		cell.setCellType(CellType.FORMULA);
		formula = "SUMPRODUCT(SUBTOTAL(9,OFFSET(F" + primeira_linha + ":F" + ultima_linha + ",ROW(F" + primeira_linha
				+ ":F" + ultima_linha + ")-ROW(F" + primeira_linha + "),0,1,1)),-(E" + primeira_linha + ":E"
				+ ultima_linha + "<>\"Não Aplicável\")) * -1";
		cell.setCellFormula(formula);

		// somatoria de valor de nf venda
		cell = row.createCell(6);
		cell.setCellStyle(valorStyleFundoVerdeTextoBrancoRecebimentos);
		cell.setCellType(CellType.FORMULA);
		formula = "SUMPRODUCT(SUBTOTAL(9,OFFSET(G" + primeira_linha + ":G" + ultima_linha + ",ROW(G" + primeira_linha
				+ ":G" + ultima_linha + ")-ROW(G" + primeira_linha + "),0,1,1)),-(E" + primeira_linha + ":E"
				+ ultima_linha + "<>\"Não Aplicável\")) * -1";
		cell.setCellFormula(formula);

		// somatoria de peso de nf remessa
		cell = row.createCell(8);
		cell.setCellStyle(numberStyleFundoVerdeTextoBrancoRecebimentos);
		cell.setCellType(CellType.FORMULA);
		formula = "SUMPRODUCT(SUBTOTAL(9,OFFSET(I" + primeira_linha + ":I" + ultima_linha + ",ROW(I" + primeira_linha
				+ ":I" + ultima_linha + ")-ROW(I" + primeira_linha + "),0,1,1)),-(H" + primeira_linha + ":H"
				+ ultima_linha + "<>\"Não Aplicável\")) * -1";
		cell.setCellFormula(formula);

		// somatoria de valor de nf remessa
		cell = row.createCell(9);
		cell.setCellStyle(valorStyleFundoVerdeTextoBrancoRecebimentos);
		cell.setCellType(CellType.FORMULA);
		formula = "SUMPRODUCT(SUBTOTAL(9,OFFSET(J" + primeira_linha + ":J" + ultima_linha + ",ROW(J" + primeira_linha
				+ ":J" + ultima_linha + ")-ROW(J" + primeira_linha + "),0,1,1)),-(H" + primeira_linha + ":H"
				+ ultima_linha + "<>\"Não Aplicável\")) * -1";
		cell.setCellFormula(formula);

		rownum += 2;

		// somatoria de valor de peso normal
		row = sheet.createRow(rownum);
		cell = row.createCell(0);
		cell.setCellStyle(celula_fundo_branco_texto_pretoRecebimentos);
		cell.setCellValue("Peso Normal:");

		celula_soma_peso = rownum + 1;

		cell = row.createCell(1);
		cell.setCellStyle(numberStyleFundoVerdeTextoBrancoRecebimentos);
		cell.setCellType(CellType.FORMULA);
		formula = "SUMPRODUCT(SUBTOTAL(9,OFFSET(D" + primeira_linha + ":D" + ultima_linha + ",ROW(D" + primeira_linha
				+ ":D" + ultima_linha + ")-ROW(D" + primeira_linha + "),0,1,1)),-(C" + primeira_linha + ":C"
				+ ultima_linha + "<>\"-Transferencia\"),-(C" + primeira_linha + ":C" + ultima_linha
				+ "<>\"+Transferencia\"))";
		cell.setCellFormula(formula);

		cell = row.createCell(2);
		cell.setCellStyle(numberStyleFundoBrancoTextoPretoRecebimentos);
		cell.setCellType(CellType.FORMULA);
		formula = "SUM(B" + (rownum + 1) + "/60)";
		cell.setCellFormula(formula);

		int linha_soma_final = 0;

		if (incluir_transferencias_recebimentos) {

			row = sheet.createRow(rownum += 1);
			cellnum = 0;

			// somatoria de pesos
			cell = row.createCell(0);
			cell.setCellStyle(celula_fundo_branco_texto_pretoRecebimentos);
			cell.setCellValue("Peso Transferencias(-):");

			cell = row.createCell(1);
			cell.setCellStyle(numberStyleFundoVerdeTextoBrancoRecebimentos);
			cell.setCellType(CellType.FORMULA);
			formula = "SUMPRODUCT(SUBTOTAL(9,OFFSET(D" + primeira_linha + ":D" + ultima_linha + ",ROW(D"
					+ primeira_linha + ":D" + ultima_linha + ")-ROW(D" + primeira_linha + "),0,1,1)),-(C"
					+ primeira_linha + ":C" + ultima_linha + "=\"-Transferencia\")) * -1";
			cell.setCellFormula(formula);

			cell = row.createCell(2);
			cell.setCellStyle(numberStyleFundoBrancoTextoPretoRecebimentos);
			cell.setCellType(CellType.FORMULA);
			formula = "SUM(B" + (rownum + 1) + "/60)";
			cell.setCellFormula(formula);

			row = sheet.createRow(rownum += 1);
			cellnum = 0;

			// somatoria de pesos
			cell = row.createCell(0);
			cell.setCellStyle(celula_fundo_branco_texto_pretoRecebimentos);
			cell.setCellValue("Peso Transferencias(+):");

			cell = row.createCell(1);
			cell.setCellStyle(numberStyleFundoVerdeTextoBrancoRecebimentos);
			cell.setCellType(CellType.FORMULA);
			formula = "SUMPRODUCT(SUBTOTAL(9,OFFSET(D" + primeira_linha + ":D" + ultima_linha + ",ROW(D"
					+ primeira_linha + ":D" + ultima_linha + ")-ROW(D" + primeira_linha + "),0,1,1)),-(C"
					+ primeira_linha + ":C" + ultima_linha + "=\"+Transferencia\")) * -1";
			cell.setCellFormula(formula);

			cell = row.createCell(2);
			cell.setCellStyle(numberStyleFundoBrancoTextoPretoRecebimentos);
			cell.setCellType(CellType.FORMULA);
			formula = "SUM(B" + (rownum + 1) + "/60)";
			cell.setCellFormula(formula);

			row = sheet.createRow(rownum += 1);
			cellnum = 0;

			linha_soma_final = rownum + 1;
			// somatoria de pesos
			cell = row.createCell(0);
			cell.setCellStyle(celula_fundo_branco_texto_pretoRecebimentos);
			cell.setCellValue("Soma Final:");

			cell = row.createCell(1);
			cell.setCellStyle(numberStyleFundoVerdeTextoBrancoRecebimentos);
			cell.setCellType(CellType.FORMULA);
			formula = "SUM(D" + primeira_linha + ":D" + ultima_linha + ")";
			cell.setCellFormula(formula);

			cell = row.createCell(2);
			cell.setCellStyle(numberStyleFundoBrancoTextoPretoRecebimentos);
			cell.setCellType(CellType.FORMULA);
			formula = "SUM(B" + (rownum + 1) + "/60)";
			cell.setCellFormula(formula);

		}

		// total contratado

		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		int linha_total_contratado = rownum + 1;
		cell = row.createCell(0);
		cell.setCellStyle(celula_fundo_branco_texto_pretoRecebimentos);
		cell.setCellValue("Total Contratado:");

		cell = row.createCell(1);
		cell.setCellStyle(numberStyleFundoVerdeTextoBrancoRecebimentos);
		if (novo_contrato.getMedida().equalsIgnoreCase("Sacos"))
			cell.setCellValue(novo_contrato.getQuantidade() * 60);
		else if (novo_contrato.getMedida().equalsIgnoreCase("KG")) {
			cell.setCellValue(novo_contrato.getQuantidade());

		}
		cell = row.createCell(2);
		cell.setCellStyle(numberStyleFundoBrancoTextoPretoRecebimentos);
		cell.setCellType(CellType.FORMULA);
		formula = "SUM(B" + (rownum + 1) + "/60)";
		cell.setCellFormula(formula);

		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		cell = row.createCell(0);
		cell.setCellStyle(celula_fundo_branco_texto_pretoRecebimentos);
		cell.setCellValue("Restante:");

		// restante

		if (incluir_transferencias_recebimentos) {
			cell = row.createCell(1);
			cell.setCellStyle(numberStyleFundoVerdeTextoBrancoRecebimentos);
			cell.setCellType(CellType.FORMULA);
			formula = "SUM(B" + linha_total_contratado + "-B" + linha_soma_final + ")";
			cell.setCellFormula(formula);
		} else {

			cell = row.createCell(1);
			cell.setCellStyle(numberStyleFundoVerdeTextoBrancoRecebimentos);
			cell.setCellType(CellType.FORMULA);
			formula = "SUM(B" + linha_total_contratado + "-B" + celula_soma_peso + ")";
			cell.setCellFormula(formula);
		}
		cell = row.createCell(2);
		cell.setCellStyle(numberStyleFundoBrancoTextoPretoRecebimentos);
		cell.setCellType(CellType.FORMULA);
		formula = "SUM(B" + (rownum + 1) + "/60)";
		cell.setCellFormula(formula);

		for (int i = 0; i < 10; i++) {
			sheet.autoSizeColumn(i);

		}

		DadosTabelaExcel retornar = new DadosTabelaExcel();
		retornar.setWorkbook(workbook);
		retornar.setSheet(sheet);
		rownum += 3;
		retornar.setRownum(rownum);

		return retornar;

	}

	public void criarTabelaNFVenda(ArrayList<CadastroNFe> nfs_venda) {
		// XWPFParagraph par = document_global.createParagraph();

		NumberFormat z = NumberFormat.getNumberInstance();

		Locale ptBr = new Locale("pt", "BR");

		// criarParagrafo(1);
		// linhas x colunas

		int num_linhas_recebimentos = nfs_venda.size() + 1 + 1;

		double peso_total_nf_venda = 0;

		BigDecimal valor_total_nf_venda = BigDecimal.ZERO;

		XWPFTable table = document_global.createTable(num_linhas_recebimentos, 7);

		setTableAlign(table, ParagraphAlignment.CENTER);
		XWPFTableRow tableRowOne = table.getRow(0);
		tableRowOne.getCell(0).removeParagraph(0);

		XWPFParagraph paragraph = tableRowOne.getCell(0).addParagraph();

		//
		int cabecalho = 0;

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(0).removeParagraph(0);
		paragraph = tableRowOne.getCell(0).addParagraph();
		criarParagrafoTabela(paragraph, "CONTRATO", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(3).removeParagraph(0);
		paragraph = tableRowOne.getCell(3).addParagraph();
		criarParagrafoTabela(paragraph, "DATA", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(4).removeParagraph(0);
		paragraph = tableRowOne.getCell(4).addParagraph();
		criarParagrafoTabela(paragraph, "Nº NF VENDA", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(4).removeParagraph(0);
		paragraph = tableRowOne.getCell(4).addParagraph();
		criarParagrafoTabela(paragraph, "Nº NF REMESSA", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(4).removeParagraph(0);
		paragraph = tableRowOne.getCell(4).addParagraph();
		criarParagrafoTabela(paragraph, "PRODUTO", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(5).removeParagraph(0);
		paragraph = tableRowOne.getCell(5).addParagraph();
		criarParagrafoTabela(paragraph, "PESO NF VENDA(kgs)", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(5).removeParagraph(0);
		paragraph = tableRowOne.getCell(5).addParagraph();
		criarParagrafoTabela(paragraph, "PESO NF VENDA(sacos)", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(6).removeParagraph(0);
		paragraph = tableRowOne.getCell(6).addParagraph();
		criarParagrafoTabela(paragraph, "VALOR NF", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(1).removeParagraph(0);
		paragraph = tableRowOne.getCell(1).addParagraph();
		criarParagrafoTabela(paragraph, "EMITENTE", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(2).removeParagraph(0);
		paragraph = tableRowOne.getCell(2).addParagraph();
		criarParagrafoTabela(paragraph, "DESTINATARIO", true);

		int i = cabecalho + 1;

		for (CadastroNFe nf_venda : nfs_venda) {

			// codigo contrato
			tableRowOne = table.getRow(i);
			tableRowOne.getCell(0).removeParagraph(0);
			paragraph = tableRowOne.getCell(0).addParagraph();
			criarParagrafoTabela(paragraph, nf_venda.getContrato().getCodigo(), false);

			// data
			tableRowOne = table.getRow(i);
			tableRowOne.getCell(1).removeParagraph(0);
			paragraph = tableRowOne.getCell(1).addParagraph();
			try {
				criarParagrafoTabela(paragraph, nf_venda.getData().toString(), false);
			} catch (NullPointerException e) {
				criarParagrafoTabela(paragraph, "Indefinido", false);

			}

			// numero nf venda
			tableRowOne = table.getRow(i);
			tableRowOne.getCell(2).removeParagraph(0);
			paragraph = tableRowOne.getCell(2).addParagraph();

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(3).removeParagraph(0);
			paragraph = tableRowOne.getCell(3).addParagraph();

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(4).removeParagraph(0);
			paragraph = tableRowOne.getCell(4).addParagraph();
			criarParagrafoTabela(paragraph, nf_venda.getNfe(), false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(5).removeParagraph(0);
			paragraph = tableRowOne.getCell(5).addParagraph();

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(6).removeParagraph(0);
			paragraph = tableRowOne.getCell(6).addParagraph();

			criarParagrafoTabela(paragraph, nf_venda.getNome_destinatario(), false);

			criarParagrafoTabela(paragraph, nf_venda.getNome_remetente(), false);

			if (nf_venda.getCaminho_arquivo() != null) {
				if (nf_venda.getCaminho_arquivo().length() > 10) {
					// é uma nf cadastrada
					Number number = null;
					try {
						number = z.parse(nf_venda.getQuantidade());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					double Dpeso = number.doubleValue();

					criarParagrafoTabela(paragraph, z.format(Dpeso) + " kgs", false);
					peso_total_nf_venda += Dpeso;
				} else {
					// não é uma nota cadastrada
					double Dpeso = nf_venda.getQuantidade_double();
					criarParagrafoTabela(paragraph, z.format(Dpeso) + " kgs", false);
					peso_total_nf_venda += Dpeso;

				}
			} else {
				// não é uma nota cadastrada
				double Dpeso = nf_venda.getQuantidade_double();
				criarParagrafoTabela(paragraph, z.format(Dpeso) + " kgs", false);
				peso_total_nf_venda += Dpeso;
			}

			try {
				String s_valor = nf_venda.getValor();
				BigDecimal valor = new BigDecimal(s_valor);
				String valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor);
				criarParagrafoTabela(paragraph, valorString, false);
				valor_total_nf_venda = valor_total_nf_venda.add(valor);
			} catch (Exception e) {
				BigDecimal valor = BigDecimal.ZERO;
				String valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor);
				criarParagrafoTabela(paragraph, valorString, false);
				valor_total_nf_venda = valor_total_nf_venda.add(valor);
			}

			i++;
		}

		// somatoria dos pesos
		tableRowOne = table.getRow(i);
		tableRowOne.getCell(5).removeParagraph(0);
		paragraph = tableRowOne.getCell(5).addParagraph();
		criarParagrafoTabela(paragraph,
				z.format(peso_total_nf_venda) + " Kgs | " + z.format(peso_total_nf_venda / 60) + " Sacos", true);

		// somatoria dos pesos
		tableRowOne = table.getRow(i);
		tableRowOne.getCell(6).removeParagraph(0);
		paragraph = tableRowOne.getCell(6).addParagraph();
		String valorStringTotal = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_nf_venda);
		criarParagrafoTabela(paragraph, valorStringTotal, true);

	}

	public void criarTabelaNFVendaERemessa(ArrayList<NFCompleto> nfs) {
		// XWPFParagraph par = document_global.createParagraph();

		NumberFormat z = NumberFormat.getNumberInstance();

		Locale ptBr = new Locale("pt", "BR");

		// criarParagrafo(1);
		// linhas x colunas

		int num_linhas_recebimentos = nfs.size() + 1 + 1;

		double peso_total_nf = 0;

		BigDecimal valor_total_nf = BigDecimal.ZERO;

		XWPFTable table = document_global.createTable(num_linhas_recebimentos, 13);

		setTableAlign(table, ParagraphAlignment.CENTER);
		XWPFTableRow tableRowOne = table.getRow(0);
		tableRowOne.getCell(0).removeParagraph(0);

		XWPFParagraph paragraph = tableRowOne.getCell(0).addParagraph();

		//
		int cabecalho = 0;

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(0).removeParagraph(0);
		paragraph = tableRowOne.getCell(0).addParagraph();
		criarParagrafoTabela(paragraph, "CONTRATO", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(1).removeParagraph(0);
		paragraph = tableRowOne.getCell(1).addParagraph();
		criarParagrafoTabela(paragraph, "ROMANEIO", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(2).removeParagraph(0);
		paragraph = tableRowOne.getCell(2).addParagraph();
		criarParagrafoTabela(paragraph, "PESO", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(3).removeParagraph(0);
		paragraph = tableRowOne.getCell(3).addParagraph();
		criarParagrafoTabela(paragraph, "DATA", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(4).removeParagraph(0);
		paragraph = tableRowOne.getCell(4).addParagraph();
		criarParagrafoTabela(paragraph, "Nº NF VENDA", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(5).removeParagraph(0);
		paragraph = tableRowOne.getCell(5).addParagraph();
		criarParagrafoTabela(paragraph, "Nº NF REMESSA", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(6).removeParagraph(0);
		paragraph = tableRowOne.getCell(6).addParagraph();
		criarParagrafoTabela(paragraph, "PRODUTO", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(7).removeParagraph(0);
		paragraph = tableRowOne.getCell(7).addParagraph();
		criarParagrafoTabela(paragraph, "PESO NF(KGS)", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(8).removeParagraph(0);
		paragraph = tableRowOne.getCell(8).addParagraph();
		criarParagrafoTabela(paragraph, "PESO NF(SACOS)", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(9).removeParagraph(0);
		paragraph = tableRowOne.getCell(9).addParagraph();
		criarParagrafoTabela(paragraph, "VALOR NF", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(10).removeParagraph(0);
		paragraph = tableRowOne.getCell(10).addParagraph();
		criarParagrafoTabela(paragraph, "EMITENTE", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(11).removeParagraph(0);
		paragraph = tableRowOne.getCell(11).addParagraph();
		criarParagrafoTabela(paragraph, "DESTINATARIO", true);

		int i = cabecalho + 1;

		for (NFCompleto nf : nfs) {

			// codigo contrato
			tableRowOne = table.getRow(i);
			tableRowOne.getCell(0).removeParagraph(0);
			paragraph = tableRowOne.getCell(0).addParagraph();
			criarParagrafoTabela(paragraph, nf.getCodigo_contrato(), false);

			// codigo rom
			tableRowOne = table.getRow(i);
			tableRowOne.getCell(1).removeParagraph(0);
			paragraph = tableRowOne.getCell(1).addParagraph();
			criarParagrafoTabela(paragraph, nf.getCodigo_romaneio(), false);

			// peso rom
			tableRowOne = table.getRow(i);
			tableRowOne.getCell(2).removeParagraph(0);
			paragraph = tableRowOne.getCell(2).addParagraph();
			criarParagrafoTabela(paragraph, z.format(nf.getPeso_romaneio()), false);

			// data
			tableRowOne = table.getRow(i);
			tableRowOne.getCell(3).removeParagraph(0);
			paragraph = tableRowOne.getCell(3).addParagraph();
			criarParagrafoTabela(paragraph, nf.getData(), false);

			// codigo nf venda
			tableRowOne = table.getRow(i);
			tableRowOne.getCell(4).removeParagraph(0);
			paragraph = tableRowOne.getCell(4).addParagraph();
			criarParagrafoTabela(paragraph, nf.getCodigo_nf_venda(), false);

			// codigo nf remessa
			tableRowOne = table.getRow(i);
			tableRowOne.getCell(5).removeParagraph(0);
			paragraph = tableRowOne.getCell(5).addParagraph();
			criarParagrafoTabela(paragraph, nf.getCodigo_nf_remessa(), false);

			// produto
			tableRowOne = table.getRow(i);
			tableRowOne.getCell(6).removeParagraph(0);
			paragraph = tableRowOne.getCell(6).addParagraph();
			criarParagrafoTabela(paragraph, nf.getProduto(), false);

			// peso
			tableRowOne = table.getRow(i);
			tableRowOne.getCell(7).removeParagraph(0);
			paragraph = tableRowOne.getCell(7).addParagraph();
			try {
				criarParagrafoTabela(paragraph, z.format(nf.getPeso()), false);
				peso_total_nf += nf.getPeso();
			} catch (Exception t) {
				criarParagrafoTabela(paragraph, "0", false);

			}

			// peso em sacos
			tableRowOne = table.getRow(i);
			tableRowOne.getCell(8).removeParagraph(0);
			paragraph = tableRowOne.getCell(8).addParagraph();
			try {
				criarParagrafoTabela(paragraph, z.format(nf.getPeso() / 60), false);
			} catch (Exception t) {
				criarParagrafoTabela(paragraph, nf.getPeso() + "", false);

			}
			// valor
			tableRowOne = table.getRow(i);
			tableRowOne.getCell(9).removeParagraph(0);
			paragraph = tableRowOne.getCell(9).addParagraph();
			try {
				String s_valor = nf.getValor();
				BigDecimal valor = new BigDecimal(s_valor);
				String valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor);
				criarParagrafoTabela(paragraph, valorString, false);
				valor_total_nf = valor_total_nf.add(valor);
			} catch (Exception e) {
				BigDecimal valor = BigDecimal.ZERO;
				String valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor);
				criarParagrafoTabela(paragraph, valorString, false);
				valor_total_nf = valor_total_nf.add(valor);
			}

			// emitente
			tableRowOne = table.getRow(i);
			tableRowOne.getCell(10).removeParagraph(0);
			paragraph = tableRowOne.getCell(10).addParagraph();
			criarParagrafoTabela(paragraph, nf.getNome_emitende(), false);

			// destinatario
			tableRowOne = table.getRow(i);
			tableRowOne.getCell(11).removeParagraph(0);
			paragraph = tableRowOne.getCell(11).addParagraph();
			criarParagrafoTabela(paragraph, nf.getNome_destinatario(), false);

			i++;
		}

		// somatoria dos pesos
		tableRowOne = table.getRow(i);
		tableRowOne.getCell(7).removeParagraph(0);
		paragraph = tableRowOne.getCell(7).addParagraph();
		criarParagrafoTabela(paragraph, z.format(peso_total_nf) + " Kgs", true);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(8).removeParagraph(0);
		paragraph = tableRowOne.getCell(8).addParagraph();
		criarParagrafoTabela(paragraph, z.format(peso_total_nf / 60) + " Sacos", true);

		// somatoria dos pesos
		tableRowOne = table.getRow(i);
		tableRowOne.getCell(9).removeParagraph(0);
		paragraph = tableRowOne.getCell(9).addParagraph();
		String valorStringTotal = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_nf);
		criarParagrafoTabela(paragraph, valorStringTotal, true);

	}

	public void criarTabelaCarregamentos(ArrayList<CarregamentoCompleto> carregamentos,
			double soma_total_quantidade_contratos,
			ArrayList<CadastroContrato.CadastroTransferenciaCarga> transferencias_remetentes,
			ArrayList<CadastroContrato.CadastroTransferenciaCarga> transferencias_destinatarios,
			double quantidade_kgs_recebidos) {
		// XWPFParagraph par = document_global.createParagraph();

		NumberFormat z = NumberFormat.getNumberInstance();

		Locale ptBr = new Locale("pt", "BR");

		// criarParagrafo(1);
		// linhas x colunas

		int num_linhas_carregamentos = -1;

		if (soma_total_quantidade_contratos == 0) {

			num_linhas_carregamentos = carregamentos.size() + 1 + 1 + 1 + 1;
		} else {
			num_linhas_carregamentos = carregamentos.size() + 1 + 1 + 1;

		}

		double soma_total_carga_real = 0;
		double soma_total_carga_nfa = 0;

		num_linhas_carregamentos = num_linhas_carregamentos + transferencias_remetentes.size()
				+ transferencias_destinatarios.size();

		XWPFTable table = document_global.createTable(num_linhas_carregamentos, 16);

		setTableAlign(table, ParagraphAlignment.CENTER);
		XWPFTableRow tableRowOne = table.getRow(0);
		tableRowOne.getCell(0).removeParagraph(0);

		XWPFParagraph paragraph = tableRowOne.getCell(0).addParagraph();

		//
		int cabecalho = 0;

		if (soma_total_quantidade_contratos != 0) {

			tableRowOne = table.getRow(cabecalho);
			tableRowOne.getCell(0).removeParagraph(0);
			paragraph = tableRowOne.getCell(0).addParagraph();

			criarParagrafoTabela(paragraph,
					"Quantidade Total Contratada: " + z.format(soma_total_quantidade_contratos) + " kgs | "
							+ z.format(soma_total_quantidade_contratos / 60) + " sacos"
							+ "   Quantidade Total Recebida: " + z.format(quantidade_kgs_recebidos) + " kgs | "
							+ z.format(quantidade_kgs_recebidos / 60) + " sacos",
					true);
			tableRowOne.getCell(0).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");
			CTHMerge hMerge = CTHMerge.Factory.newInstance();
			hMerge.setVal(STMerge.RESTART);
			table.getRow(cabecalho).getCell(0).getCTTc().getTcPr().setHMerge(hMerge);

			for (int celula = 1; celula <= 15; celula++) {
				tableRowOne = table.getRow(cabecalho);
				tableRowOne.getCell(celula).removeParagraph(0);
				paragraph = tableRowOne.getCell(celula).addParagraph();

				criarParagrafoTabela(paragraph, "", true);
				tableRowOne.getCell(celula).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");

				CTHMerge hMerge1 = CTHMerge.Factory.newInstance();
				hMerge1.setVal(STMerge.CONTINUE);
				table.getRow(cabecalho).getCell(celula).getCTTc().getTcPr().setHMerge(hMerge1);

			}

			cabecalho++;

		} else {

			tableRowOne = table.getRow(cabecalho);
			tableRowOne.getCell(0).removeParagraph(0);
			paragraph = tableRowOne.getCell(0).addParagraph();

			// compradores x vendedores
			CadastroContrato contrato_deste_carregamento = null;
			// safra
			if (carregamentos.size() > 0) {
				contrato_deste_carregamento = carregamentos.get(0).getContrato();
			} else if (transferencias_remetentes.size() > 0) {
				int id_contrato = transferencias_remetentes.get(0).getId_contrato_remetente();
				contrato_deste_carregamento = new GerenciarBancoContratos().getContrato(id_contrato);

			} else if (transferencias_destinatarios.size() > 0) {
				int id_contrato = transferencias_destinatarios.get(0).getId_contrato_destinatario();
				contrato_deste_carregamento = new GerenciarBancoContratos().getContrato(id_contrato);
			}

			String safra = contrato_deste_carregamento.getModelo_safra().getProduto().getNome_produto() + " "
					+ contrato_deste_carregamento.getModelo_safra().getProduto().getTransgenia() + " "
					+ contrato_deste_carregamento.getModelo_safra().getAno_plantio() + "/"
					+ contrato_deste_carregamento.getModelo_safra().getAno_colheita();

			double quantidade_kg = 0, quantidade_sacos = 0;
			if (contrato_deste_carregamento.getMedida().equals("KG")) {
				quantidade_kg = contrato_deste_carregamento.getQuantidade();
				quantidade_sacos = quantidade_kg / 60;
			} else if (contrato_deste_carregamento.getMedida().equals("Sacos")) {
				quantidade_sacos = contrato_deste_carregamento.getQuantidade();

				quantidade_kg = quantidade_sacos * 60;
			}

			criarParagrafoTabela(paragraph,
					"CTR: " + contrato_deste_carregamento.getCodigo() + " " + safra + " Quantidade Total: "
							+ z.format(quantidade_kg) + " kgs | " + z.format(quantidade_sacos) + " sacos "
							+ NumberFormat.getCurrencyInstance(ptBr)
									.format(contrato_deste_carregamento.getValor_produto())
							+ " por " + contrato_deste_carregamento.getMedida() + " totalizando: "
							+ NumberFormat.getCurrencyInstance(ptBr)
									.format(contrato_deste_carregamento.getValor_a_pagar().doubleValue()),
					true);
			tableRowOne.getCell(0).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");
			CTHMerge hMerge = CTHMerge.Factory.newInstance();
			hMerge.setVal(STMerge.RESTART);
			table.getRow(cabecalho).getCell(0).getCTTc().getTcPr().setHMerge(hMerge);

			for (int celula = 1; celula <= 15; celula++) {
				tableRowOne = table.getRow(cabecalho);
				tableRowOne.getCell(celula).removeParagraph(0);
				paragraph = tableRowOne.getCell(celula).addParagraph();

				criarParagrafoTabela(paragraph, "", true);
				tableRowOne.getCell(celula).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");

				CTHMerge hMerge1 = CTHMerge.Factory.newInstance();
				hMerge1.setVal(STMerge.CONTINUE);
				table.getRow(cabecalho).getCell(celula).getCTTc().getTcPr().setHMerge(hMerge1);

			}

			cabecalho++;

			// linha com nome compradores x vendedores

			tableRowOne = table.getRow(cabecalho);
			tableRowOne.getCell(0).removeParagraph(0);
			paragraph = tableRowOne.getCell(0).addParagraph();

			CadastroCliente compradores[] = contrato_deste_carregamento.getCompradores();
			CadastroCliente vendedores[] = contrato_deste_carregamento.getVendedores();

			String nome_vendedores = "";
			String nome_compradores = "";

			if (compradores[0] != null) {
				if (compradores[0].getTipo_pessoa() == 0) {
					// pessoa fisica
					nome_compradores = compradores[0].getNome_empresarial();
				} else {
					nome_compradores = compradores[0].getNome_fantaia();

				}
			}
			if (compradores[1] != null) {
				if (compradores[1].getTipo_pessoa() == 0) {
					// pessoa fisica
					nome_compradores = nome_compradores + ", " + compradores[1].getNome_empresarial();
				} else {
					nome_compradores = nome_compradores + ", " + compradores[1].getNome_fantaia();

				}
			}
			if (vendedores[0] != null) {
				if (vendedores[0].getTipo_pessoa() == 0) {
					nome_vendedores = vendedores[0].getNome_empresarial();
				} else {
					nome_vendedores = vendedores[0].getNome_fantaia();
				}
			}

			if (vendedores[1] != null) {
				if (vendedores[1].getTipo_pessoa() == 0) {
					nome_vendedores = nome_vendedores + ", " + vendedores[1].getNome_empresarial();
				} else {
					nome_vendedores = nome_vendedores + ", " + vendedores[1].getNome_fantaia();
				}
			}

			try {
				criarParagrafoTabela(paragraph, contrato_deste_carregamento.getNomes_compradores().toUpperCase() + " X "
						+ contrato_deste_carregamento.getNomes_vendedores().toUpperCase(), true);

			} catch (Exception e) {
				criarParagrafoTabela(paragraph, contrato_deste_carregamento.getNomes_compradores() + " X "
						+ contrato_deste_carregamento.getNomes_vendedores(), true);

			}

			tableRowOne.getCell(0).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");
			hMerge = CTHMerge.Factory.newInstance();
			hMerge.setVal(STMerge.RESTART);
			table.getRow(cabecalho).getCell(0).getCTTc().getTcPr().setHMerge(hMerge);

			for (int celula = 1; celula <= 15; celula++) {
				tableRowOne = table.getRow(cabecalho);
				tableRowOne.getCell(celula).removeParagraph(0);
				paragraph = tableRowOne.getCell(celula).addParagraph();

				criarParagrafoTabela(paragraph, "", true);
				tableRowOne.getCell(celula).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");

				CTHMerge hMerge1 = CTHMerge.Factory.newInstance();
				hMerge1.setVal(STMerge.CONTINUE);
				table.getRow(cabecalho).getCell(celula).getCTTc().getTcPr().setHMerge(hMerge1);

			}

			cabecalho++;

		}

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(0).removeParagraph(0);
		paragraph = tableRowOne.getCell(0).addParagraph();
		criarParagrafoTabela(paragraph, "CONTRATO", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(1).removeParagraph(0);
		paragraph = tableRowOne.getCell(1).addParagraph();
		criarParagrafoTabela(paragraph, "DATA", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(2).removeParagraph(0);
		paragraph = tableRowOne.getCell(2).addParagraph();
		criarParagrafoTabela(paragraph, "CLIENTE", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(3).removeParagraph(0);
		paragraph = tableRowOne.getCell(3).addParagraph();
		criarParagrafoTabela(paragraph, "VENDEDOR", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(4).removeParagraph(0);
		paragraph = tableRowOne.getCell(4).addParagraph();
		criarParagrafoTabela(paragraph, "TRANSPORTADOR", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(5).removeParagraph(0);
		paragraph = tableRowOne.getCell(5).addParagraph();
		criarParagrafoTabela(paragraph, "VEICULO", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(6).removeParagraph(0);
		paragraph = tableRowOne.getCell(6).addParagraph();
		criarParagrafoTabela(paragraph, "PRODUTO", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(7).removeParagraph(0);
		paragraph = tableRowOne.getCell(7).addParagraph();
		criarParagrafoTabela(paragraph, "ROM", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(8).removeParagraph(0);
		paragraph = tableRowOne.getCell(8).addParagraph();
		criarParagrafoTabela(paragraph, "PESO ROM", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(9).removeParagraph(0);
		paragraph = tableRowOne.getCell(9).addParagraph();
		criarParagrafoTabela(paragraph, "NF 1", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(10).removeParagraph(0);
		paragraph = tableRowOne.getCell(10).addParagraph();
		criarParagrafoTabela(paragraph, "PESO NF 1", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(11).removeParagraph(0);
		paragraph = tableRowOne.getCell(11).addParagraph();
		criarParagrafoTabela(paragraph, "VALOR NF 1", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(12).removeParagraph(0);
		paragraph = tableRowOne.getCell(12).addParagraph();
		criarParagrafoTabela(paragraph, "NF 2", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(13).removeParagraph(0);
		paragraph = tableRowOne.getCell(13).addParagraph();
		criarParagrafoTabela(paragraph, "PESO NF 2", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(14).removeParagraph(0);
		paragraph = tableRowOne.getCell(14).addParagraph();
		criarParagrafoTabela(paragraph, "VALOR NF 2", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(15).removeParagraph(0);
		paragraph = tableRowOne.getCell(15).addParagraph();
		criarParagrafoTabela(paragraph, "DIFERENÇA", true);

		int i = cabecalho + 1;

		double peso_total_nf_interna = 0.0;
		double peso_total_romaneios = 0.0;
		double peso_total_nf_venda1 = 0.0;
		double peso_total_nf_complemento = 0.0;
		double peso_total_diferenca = 0.0;

		BigDecimal valor_total_nf_venda1 = BigDecimal.ZERO;
		BigDecimal valor_total_nf_complemento = BigDecimal.ZERO;

		// fazer checkagens

		boolean nf_interna_ativo = false;
		boolean nf_venda_ativo = false;
		boolean nf_complemento_ativo = false;

		// checka se ha no minimo uma nf interna aplicavel
		for (CadastroContrato.Carregamento carregamento : carregamentos) {
			if (carregamento.getNf_interna_aplicavel() == 1) {
				nf_interna_ativo = true;
				break;
			}

		}

		// checka se ha no minimo uma nf venda aplicavel
		for (CadastroContrato.Carregamento carregamento : carregamentos) {
			if (carregamento.getNf_venda1_aplicavel() == 1) {
				nf_venda_ativo = true;
				break;
			}

		}

		// checka se ha no minimo uma nf complemento aplicavel
		for (CadastroContrato.Carregamento carregamento : carregamentos) {
			if (carregamento.getNf_complemento_aplicavel() == 1) {
				nf_complemento_ativo = true;
				break;
			}

		}

		for (CarregamentoCompleto carregamento : carregamentos) {

			GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
			// pegar dados do contrato
			CadastroContrato contrato_destinatario = carregamento.getContrato();
			// pegar cliente

			String nome_cliente = carregamento.getCliente_carregamento();
			String nome_cliente_completo = carregamento.getCliente_carregamento();

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

			String nome_vendedor = carregamento.getVendedor_carregamento();

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

			// codigos
			String codigo_romaneio = "";
			String codigo_nf_venda1 = "", codigo_nf_complemento = "";
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
			} catch (Exception e) {
				// //JOptionPane.showMessageDialog(isto, "Romaneio não Localizado");
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
						} catch (Exception e) {
							valor_nf_venda1 = BigDecimal.ZERO;
						}

					} else {
						codigo_nf_venda1 = carregamento.getCodigo_nf_venda1();
						peso_nf_venda1 = carregamento.getPeso_nf_venda1();
						valor_nf_venda1 = carregamento.getValor_nf_venda1();

					}

				}
			} catch (Exception e) {
				// //JOptionPane.showMessageDialog(isto, "Nota Fiscal de venda não Localizado");
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
						} catch (Exception e) {
							valor_nf_complemento = BigDecimal.ZERO;
						}

					} else {
						codigo_nf_complemento = carregamento.getCodigo_nf_complemento();
						peso_nf_complemento = carregamento.getPeso_nf_complemento();
						valor_nf_complemento = carregamento.getValor_nf_complemento();

					}

				}
			} catch (Exception e) {
				// //JOptionPane.showMessageDialog(isto, "Nota Fiscal de remessa não
				// Localizado");

				codigo_nf_complemento = carregamento.getCodigo_nf_complemento();
				peso_nf_complemento = carregamento.getPeso_nf_complemento();
				valor_nf_complemento = carregamento.getValor_nf_complemento();

			}

			String cor = "000000";

			if ((peso_nf_venda1 + peso_nf_complemento) >= peso_romaneio) {
				// ok
				cor = "FFFFFF";
			} else if ((peso_nf_venda1 + peso_nf_complemento) < peso_romaneio) {
				cor = "FFFF00";
			}

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(0).removeParagraph(0);
			paragraph = tableRowOne.getCell(0).addParagraph();
			criarParagrafoTabela(paragraph, contrato_destinatario.getCodigo(), false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(1).removeParagraph(0);
			paragraph = tableRowOne.getCell(1).addParagraph();
			criarParagrafoTabela(paragraph, carregamento.getData(), false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(2).removeParagraph(0);
			paragraph = tableRowOne.getCell(2).addParagraph();
			criarParagrafoTabela(paragraph, nome_cliente, false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(3).removeParagraph(0);
			paragraph = tableRowOne.getCell(3).addParagraph();
			criarParagrafoTabela(paragraph, nome_vendedor, false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(4).removeParagraph(0);
			paragraph = tableRowOne.getCell(4).addParagraph();
			criarParagrafoTabela(paragraph, carregamento.getNome_motorista(), false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(5).removeParagraph(0);
			paragraph = tableRowOne.getCell(5).addParagraph();
			criarParagrafoTabela(paragraph, carregamento.getPlaca(), false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(6).removeParagraph(0);
			paragraph = tableRowOne.getCell(6).addParagraph();
			criarParagrafoTabela(paragraph, carregamento.getContrato().getModelo_produto().getNome_produto(), false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(7).removeParagraph(0);
			paragraph = tableRowOne.getCell(7).addParagraph();
			criarParagrafoTabela(paragraph, carregamento.getCodigo_romaneio(), false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(8).removeParagraph(0);
			paragraph = tableRowOne.getCell(8).addParagraph();
			criarParagrafoTabela(paragraph, z.format(peso_romaneio) + " Kgs", false);
			tableRowOne.getCell(8).getCTTc().addNewTcPr().addNewShd().setFill(cor);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(9).removeParagraph(0);
			paragraph = tableRowOne.getCell(9).addParagraph();
			if (carregamento.getNf_venda1_aplicavel() == 1)
				criarParagrafoTabela(paragraph, codigo_nf_venda1, false);
			else
				criarParagrafoTabela(paragraph, "Não Aplicável", false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(10).removeParagraph(0);
			paragraph = tableRowOne.getCell(10).addParagraph();
			if (carregamento.getNf_venda1_aplicavel() == 1) {
				criarParagrafoTabela(paragraph, z.format(peso_nf_venda1) + " Kgs", false);
				tableRowOne.getCell(10).getCTTc().addNewTcPr().addNewShd().setFill(cor);
			} else {
				criarParagrafoTabela(paragraph, "Não Aplicável", false);
			}

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(11).removeParagraph(0);
			paragraph = tableRowOne.getCell(11).addParagraph();
			if (carregamento.getNf_venda1_aplicavel() == 1) {
				criarParagrafoTabela(paragraph, NumberFormat.getCurrencyInstance(ptBr).format(valor_nf_venda1), false);
			} else {
				criarParagrafoTabela(paragraph, "Não Aplicável", false);
			}

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(12).removeParagraph(0);
			paragraph = tableRowOne.getCell(12).addParagraph();
			if (carregamento.getNf_complemento_aplicavel() == 1)
				criarParagrafoTabela(paragraph, codigo_nf_complemento, false);
			else
				criarParagrafoTabela(paragraph, "Não Aplicável", false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(13).removeParagraph(0);
			paragraph = tableRowOne.getCell(13).addParagraph();
			if (carregamento.getNf_complemento_aplicavel() == 1) {
				criarParagrafoTabela(paragraph, z.format(peso_nf_complemento) + " Kgs", false);
				tableRowOne.getCell(13).getCTTc().addNewTcPr().addNewShd().setFill(cor);
			} else {
				criarParagrafoTabela(paragraph, "Não Aplicável", false);

			}
			tableRowOne = table.getRow(i);
			tableRowOne.getCell(14).removeParagraph(0);
			paragraph = tableRowOne.getCell(14).addParagraph();
			if (carregamento.getNf_complemento_aplicavel() == 1)
				criarParagrafoTabela(paragraph, NumberFormat.getCurrencyInstance(ptBr).format(valor_nf_complemento),
						false);
			else {
				criarParagrafoTabela(paragraph, "Não Aplicável", false);
			}

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(15).removeParagraph(0);
			paragraph = tableRowOne.getCell(15).addParagraph();
			if (carregamento.getNf_venda1_aplicavel() == 1 && carregamento.getNf_complemento_aplicavel() == 1) {
				criarParagrafoTabela(paragraph,
						z.format(peso_romaneio - (peso_nf_complemento + peso_nf_venda1)) + " Kgs", false);
				tableRowOne.getCell(15).getCTTc().addNewTcPr().addNewShd().setFill(cor);
			} else {
				criarParagrafoTabela(paragraph, "Não Aplicável", false);

			}

			peso_total_romaneios += peso_romaneio;
			peso_total_nf_interna += peso_nf_interna;
			peso_total_nf_venda1 += peso_nf_venda1;
			peso_total_nf_complemento += peso_nf_complemento;
			peso_total_diferenca += (peso_romaneio - (peso_nf_complemento + peso_nf_venda1));

			valor_total_nf_venda1 = valor_total_nf_venda1.add(valor_nf_venda1);
			valor_total_nf_complemento = valor_total_nf_complemento.add(valor_nf_complemento);

			i++;
		}

		if (incluir_transferencias_carregamentos) {
			// transferencias negativas
			/*************************** transferencias negativas *****************///////////

			for (CadastroContrato.CadastroTransferenciaCarga transferencia : transferencias_remetentes) {

				/*
				 * codigo compradores vendedores status quantidade medida produto transgenia
				 * safra valor_produto valor_total data_contrato local_retirada
				 */

				String texto_detalhado = "";

				GerenciarBancoContratos gerencia_contratos = new GerenciarBancoContratos();
				CadastroContrato remetente = gerencia_contratos.getContrato(transferencia.getId_contrato_remetente());
				CadastroContrato destinatario = gerencia_contratos
						.getContrato(transferencia.getId_contrato_destinatario());
				// CadastroContrato.Carregamento carga = gerencia_contratos
				// .getCarregamento(transferencia.getId_carregamento_remetente());

				CadastroCliente compradores_trans[] = destinatario.getCompradores();
				CadastroCliente vendedores_trans[] = destinatario.getVendedores();

				String nome_vendedores = "";
				String nome_compradores = "";

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

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(0).removeParagraph(0);
				paragraph = tableRowOne.getCell(0).addParagraph();
				criarParagrafoTabela(paragraph, remetente.getCodigo(), false);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(1).removeParagraph(0);
				paragraph = tableRowOne.getCell(1).addParagraph();
				criarParagrafoTabela(paragraph, texto_detalhado, false);

				CTHMerge hMerge = CTHMerge.Factory.newInstance();
				hMerge.setVal(STMerge.RESTART);
				if (table.getRow(i).getCell(1).getCTTc().getTcPr() == null) {
					table.getRow(i).getCell(1).getCTTc().addNewTcPr();
					table.getRow(i).getCell(1).getCTTc().getTcPr().setHMerge(hMerge);

				} else
					table.getRow(i).getCell(1).getCTTc().getTcPr().setHMerge(hMerge);

				for (int celula = 2; celula <= 15; celula++) {
					tableRowOne = table.getRow(i);
					tableRowOne.getCell(celula).removeParagraph(0);
					paragraph = tableRowOne.getCell(celula).addParagraph();

					criarParagrafoTabela(paragraph, "", true);
					tableRowOne.getCell(celula).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");

					CTHMerge hMerge1 = CTHMerge.Factory.newInstance();
					hMerge1.setVal(STMerge.CONTINUE);
					table.getRow(i).getCell(celula).getCTTc().getTcPr().setHMerge(hMerge1);

				}

				peso_total_romaneios -= quantidade;

				i++;

			}
		}
		/*************************** transferencias negativas *****************///////////

		// transfereicas positivas
		/*************************** transferencias positivas *****************///////////
		if (incluir_transferencias_carregamentos) {
			for (CadastroContrato.CadastroTransferenciaCarga transferencia : transferencias_destinatarios) {

				String texto_detalhado = "";

				GerenciarBancoContratos gerencia_contratos = new GerenciarBancoContratos();
				CadastroContrato remetente = gerencia_contratos.getContrato(transferencia.getId_contrato_remetente());
				CadastroContrato destinatario = gerencia_contratos
						.getContrato(transferencia.getId_contrato_destinatario());
				// CadastroContrato.Carregamento carga = gerencia_contratos
				// .getCarregamento(transferencia.getId_carregamento_remetente());

				CadastroCliente compradores_trans[] = destinatario.getCompradores();
				CadastroCliente vendedores_trans[] = destinatario.getVendedores();

				String nome_vendedores = "";
				String nome_compradores = "";

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
						nome_vendedores += ",";

					}
				}

				double quantidade = Double.parseDouble(transferencia.getQuantidade());

				texto_detalhado = "Transferência Positiva: Recebimento de volume de " + z.format(quantidade) + " kgs | "
						+ z.format(quantidade / 60) + " sacos recebidos do contrato ";
				texto_detalhado = texto_detalhado + remetente.getCodigo() + " " + nome_compradores + " X "
						+ nome_vendedores + " " + z.format(remetente.getQuantidade()) + " " + remetente.getMedida()
						+ " de " + remetente.getModelo_safra().getProduto().getNome_produto() + " "
						+ remetente.getModelo_safra().getProduto().getTransgenia() + " da safra "
						+ remetente.getModelo_safra().getAno_plantio() + "/"
						+ remetente.getModelo_safra().getAno_colheita();
				texto_detalhado = texto_detalhado + "";

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(0).removeParagraph(0);
				paragraph = tableRowOne.getCell(0).addParagraph();
				criarParagrafoTabela(paragraph, destinatario.getCodigo(), false);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(1).removeParagraph(0);
				paragraph = tableRowOne.getCell(1).addParagraph();
				criarParagrafoTabela(paragraph, texto_detalhado, false);

				CTHMerge hMerge = CTHMerge.Factory.newInstance();
				hMerge.setVal(STMerge.RESTART);
				if (table.getRow(i).getCell(1).getCTTc().getTcPr() == null) {
					table.getRow(i).getCell(1).getCTTc().addNewTcPr();
					table.getRow(i).getCell(1).getCTTc().getTcPr().setHMerge(hMerge);

				} else
					table.getRow(i).getCell(1).getCTTc().getTcPr().setHMerge(hMerge);

				for (int celula = 2; celula <= 15; celula++) {
					tableRowOne = table.getRow(i);
					tableRowOne.getCell(celula).removeParagraph(0);
					paragraph = tableRowOne.getCell(celula).addParagraph();

					criarParagrafoTabela(paragraph, "", true);
					tableRowOne.getCell(celula).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");

					CTHMerge hMerge1 = CTHMerge.Factory.newInstance();
					hMerge1.setVal(STMerge.CONTINUE);
					table.getRow(i).getCell(celula).getCTTc().getTcPr().setHMerge(hMerge1);

				}

				peso_total_romaneios += quantidade;

				i++;

			}
		}

		/*************************** transferencias positivas *****************///////////

		// informacoes de total
		String texto = "";

		// totais

		texto = texto + "\nPeso Recebido para Carregar: [";
		texto = texto + z.format(quantidade_kgs_recebidos) + " kgs | " + z.format(quantidade_kgs_recebidos / 60)
				+ " sacos";

		texto = texto + "\nPeso Carregado: [";
		texto = texto + z.format(peso_total_romaneios) + " Kgs] | [" + z.format(peso_total_romaneios / 60)
				+ "] [sacos]";

		texto = texto + " Peso NF's Interna: [";
		if (nf_interna_ativo) {

			texto = texto + z.format(peso_total_nf_interna) + " Kgs]";
		} else {
			texto = texto + " Não Aplicável";

		}

		texto = texto + " Peso NF's Venda: [";

		if (nf_venda_ativo || nf_complemento_ativo) {
			texto = texto + (z.format(peso_total_nf_venda1 + peso_total_nf_complemento) + " Kgs]");

		} else {
			texto = texto + " Não Aplicável";

		}
		texto = texto + " Valor NF's Venda: [";

		BigDecimal soma = valor_total_nf_venda1.add(valor_total_nf_complemento);

		if (nf_venda_ativo || nf_complemento_ativo) {
			texto = texto + NumberFormat.getCurrencyInstance(ptBr).format(soma.doubleValue()) + "]";

		} else {
			texto = texto + " Não Aplicável";

		}
		texto = texto + "\n";

		texto = texto + "Peso a Carregar: [";

		texto = texto + z.format(quantidade_kgs_recebidos - peso_total_romaneios) + " Kgs] | ["
				+ z.format((quantidade_kgs_recebidos - peso_total_romaneios) / 60) + "] [sacos]";

		texto = texto + " Peso NF' Interna a Emitir: [";

		if (nf_interna_ativo) {
			texto = texto + z.format(peso_total_romaneios - peso_total_nf_interna) + " Kgs]";
		} else {
			texto = texto + " Não Aplicável";

		}

		texto = texto + " Peso NF's Venda a Emitir: [";

		if (nf_venda_ativo || nf_complemento_ativo) {
			texto = texto + z.format(peso_total_romaneios - (peso_total_nf_venda1 + peso_total_nf_complemento))
					+ " Kgs]";

		} else {
			texto = texto + " Não Aplicável";

		}
		// status baseado no peso total ja carregado

		texto = texto + "\n\n";

		texto = texto + "Status parcial gerado de forma automatica calculados a partir do peso total já recebido: "
				+ z.format(quantidade_kgs_recebidos) + " kgs | " + z.format(quantidade_kgs_recebidos / 60) + " sacos";

		texto = texto + "\n";

		double diferenca = quantidade_kgs_recebidos - peso_total_romaneios;
		if (diferenca == 0) {
			texto = texto + "Carregamento Concluído";
		} else if (diferenca > 0) {
			texto = texto + "Carregamento Incompleto, [falta carregar " + z.format(diferenca) + " Kgs ]| "
					+ z.format(diferenca / 60) + " sacos";

		} else if (diferenca < 0) {
			texto = texto + "Carregamento Excedido, [excedeu " + z.format(diferenca) + " Kgs ]| "
					+ z.format(diferenca / 60) + " sacos";

		}

		texto = texto + "\n";

		String texto_nf_remessa = "";
		double diferenca_nf_remessa = peso_total_romaneios - peso_total_nf_interna;
		if (diferenca_nf_remessa == 0) {
			texto_nf_remessa = texto_nf_remessa + "[Emissão de NF's Interna Concluído]";
		} else if (diferenca_nf_remessa > 0) {
			texto_nf_remessa = texto_nf_remessa + "Emissão de NF's Interna Incompleto, [falta emitir "
					+ z.format(diferenca_nf_remessa) + " Kgs] | " + z.format(diferenca_nf_remessa / 60) + " sacos";

		} else if (diferenca_nf_remessa < 0) {
			texto_nf_remessa = texto_nf_remessa + "Emissão de NF's Interna, [excedeu " + z.format(diferenca_nf_remessa)
					+ " Kgs] | " + z.format(diferenca_nf_remessa / 60) + " sacos";

		}

		if (nf_interna_ativo)
			texto = texto + (texto_nf_remessa);
		else
			texto = texto + "Emissão de NF's Interna Não Aplicável";

		texto = texto + "\n";

		// status de nf de venda

		String texto_nf_venda = "";
		double diferenca_nf_venda = peso_total_romaneios - (peso_total_nf_venda1 + peso_total_nf_complemento);
		if (diferenca_nf_venda == 0) {
			texto_nf_venda = texto_nf_venda + "[Emissão de NF's de Venda Concluído]";
		} else if (diferenca_nf_venda > 0) {
			texto_nf_venda = texto_nf_venda + "Emissão de NF's de Venda Incompleto, [falta emitir "
					+ z.format(diferenca_nf_venda) + " Kgs] | " + z.format(diferenca_nf_venda / 60) + " sacos";

		} else if (diferenca_nf_venda < 0) {
			texto_nf_venda = texto_nf_venda + "Emissão de NF's Excedido, excedeu " + z.format(diferenca_nf_venda)
					+ " Kgs | " + z.format(diferenca_nf_venda / 60) + " sacos";

		}
		if (nf_venda_ativo || nf_complemento_ativo)
			texto = texto + (texto_nf_venda);
		else
			texto = texto + "Emissão de NF's de Venda Não Aplicável";

		////////////////////////////////////////////////////////////////////////////////
		substituirTexto(-1, texto);

	}

	public void criarTabelaInfoTransferencias(ArrayList<CadastroContrato.CadastroTransferenciaCarga> transferencias,
			CadastroContrato contrato_deste_carregamento) {

		substituirTexto("");
		// XWPFParagraph par = document_global.createParagraph();

		NumberFormat z = NumberFormat.getNumberInstance();

		Locale ptBr = new Locale("pt", "BR");

		// criarParagrafo(1);
		// linhas x colunas

		int num_linhas_carregamentos = -1;

		num_linhas_carregamentos = transferencias.size() + 1 + 1 + 1 + 1;

		double soma_total_carga_real = 0;
		double soma_total_carga_nfa = 0;

		double quantidade_kg = 0;
		double quantidade_sacos = 0;

		if (contrato_deste_carregamento.getMedida().equalsIgnoreCase("KG")) {
			quantidade_kg = contrato_deste_carregamento.getQuantidade();
			quantidade_sacos = quantidade_kg / 60;
		} else if (contrato_deste_carregamento.getMedida().equalsIgnoreCase("Sacos")) {
			quantidade_sacos = contrato_deste_carregamento.getQuantidade();
			quantidade_kg = quantidade_sacos * 60;
		}

		XWPFTable table = document_global.createTable(num_linhas_carregamentos, 16);

		setTableAlign(table, ParagraphAlignment.CENTER);
		XWPFTableRow tableRowOne = table.getRow(0);
		tableRowOne.getCell(0).removeParagraph(0);

		XWPFParagraph paragraph = tableRowOne.getCell(0).addParagraph();

		//
		int cabecalho = 0;

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(0).removeParagraph(0);
		paragraph = tableRowOne.getCell(0).addParagraph();

		// compradores x vendedores

		// safra
		String safra = contrato_deste_carregamento.getModelo_safra().getProduto().getNome_produto() + " "
				+ contrato_deste_carregamento.getModelo_safra().getProduto().getTransgenia() + " "
				+ contrato_deste_carregamento.getModelo_safra().getAno_plantio() + "/"
				+ contrato_deste_carregamento.getModelo_safra().getAno_colheita();

		criarParagrafoTabela(paragraph, "CTR: " + contrato_deste_carregamento.getCodigo() + " " + safra
				+ " Quantidade Total: " + z.format(quantidade_kg) + " kgs | " + z.format(quantidade_sacos) + " sacos "
				+ NumberFormat.getCurrencyInstance(ptBr).format(contrato_deste_carregamento.getValor_produto())
				+ " por " + contrato_deste_carregamento.getMedida() + " totalizando: " + NumberFormat
						.getCurrencyInstance(ptBr).format(contrato_deste_carregamento.getValor_a_pagar().doubleValue()),
				true);
		tableRowOne.getCell(0).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");
		CTHMerge hMerge = CTHMerge.Factory.newInstance();
		hMerge.setVal(STMerge.RESTART);
		table.getRow(cabecalho).getCell(0).getCTTc().getTcPr().setHMerge(hMerge);

		for (int celula = 1; celula <= 15; celula++) {
			tableRowOne = table.getRow(cabecalho);
			tableRowOne.getCell(celula).removeParagraph(0);
			paragraph = tableRowOne.getCell(celula).addParagraph();

			criarParagrafoTabela(paragraph, "", true);
			tableRowOne.getCell(celula).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");

			CTHMerge hMerge1 = CTHMerge.Factory.newInstance();
			hMerge1.setVal(STMerge.CONTINUE);
			table.getRow(cabecalho).getCell(celula).getCTTc().getTcPr().setHMerge(hMerge1);

		}

		cabecalho++;

		// linha com nome compradores x vendedores

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(0).removeParagraph(0);
		paragraph = tableRowOne.getCell(0).addParagraph();

		CadastroCliente compradores[] = contrato_deste_carregamento.getCompradores();
		CadastroCliente vendedores[] = contrato_deste_carregamento.getVendedores();

		String nome_vendedores = "";
		String nome_compradores = "";

		if (compradores[0] != null) {
			if (compradores[0].getTipo_pessoa() == 0) {
				// pessoa fisica
				nome_compradores = compradores[0].getNome_empresarial();
			} else {
				nome_compradores = compradores[0].getNome_fantaia();

			}
		}
		if (compradores[1] != null) {
			if (compradores[1].getTipo_pessoa() == 0) {
				// pessoa fisica
				nome_compradores = nome_compradores + ", " + compradores[1].getNome_empresarial();
			} else {
				nome_compradores = nome_compradores + ", " + compradores[1].getNome_fantaia();

			}
		}
		if (vendedores[0] != null) {
			if (vendedores[0].getTipo_pessoa() == 0) {
				nome_vendedores = vendedores[0].getNome_empresarial();
			} else {
				nome_vendedores = vendedores[0].getNome_fantaia();
			}
		}

		if (vendedores[1] != null) {
			if (vendedores[1].getTipo_pessoa() == 0) {
				nome_vendedores = nome_vendedores + ", " + vendedores[1].getNome_empresarial();
			} else {
				nome_vendedores = nome_vendedores + ", " + vendedores[1].getNome_fantaia();
			}
		}

		criarParagrafoTabela(paragraph, nome_compradores + " X " + nome_vendedores, true);
		tableRowOne.getCell(0).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");
		hMerge = CTHMerge.Factory.newInstance();
		hMerge.setVal(STMerge.RESTART);
		table.getRow(cabecalho).getCell(0).getCTTc().getTcPr().setHMerge(hMerge);

		for (int celula = 1; celula <= 15; celula++) {
			tableRowOne = table.getRow(cabecalho);
			tableRowOne.getCell(celula).removeParagraph(0);
			paragraph = tableRowOne.getCell(celula).addParagraph();

			criarParagrafoTabela(paragraph, "", true);
			tableRowOne.getCell(celula).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");

			CTHMerge hMerge1 = CTHMerge.Factory.newInstance();
			hMerge1.setVal(STMerge.CONTINUE);
			table.getRow(cabecalho).getCell(celula).getCTTc().getTcPr().setHMerge(hMerge1);

		}

		cabecalho++;

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(0).removeParagraph(0);
		paragraph = tableRowOne.getCell(0).addParagraph();
		criarParagrafoTabela(paragraph, "CONTRATO", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(1).removeParagraph(0);
		paragraph = tableRowOne.getCell(1).addParagraph();
		criarParagrafoTabela(paragraph, "DATA", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(2).removeParagraph(0);
		paragraph = tableRowOne.getCell(2).addParagraph();
		criarParagrafoTabela(paragraph, "CLIENTE", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(3).removeParagraph(0);
		paragraph = tableRowOne.getCell(3).addParagraph();
		criarParagrafoTabela(paragraph, "VENDEDOR", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(4).removeParagraph(0);
		paragraph = tableRowOne.getCell(4).addParagraph();
		criarParagrafoTabela(paragraph, "TRANSPORTADOR", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(5).removeParagraph(0);
		paragraph = tableRowOne.getCell(5).addParagraph();
		criarParagrafoTabela(paragraph, "VEICULO", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(6).removeParagraph(0);
		paragraph = tableRowOne.getCell(6).addParagraph();
		criarParagrafoTabela(paragraph, "PRODUTO", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(7).removeParagraph(0);
		paragraph = tableRowOne.getCell(7).addParagraph();
		criarParagrafoTabela(paragraph, "ROM", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(8).removeParagraph(0);
		paragraph = tableRowOne.getCell(8).addParagraph();
		criarParagrafoTabela(paragraph, "PESO ROM", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(9).removeParagraph(0);
		paragraph = tableRowOne.getCell(9).addParagraph();
		criarParagrafoTabela(paragraph, "NF 1", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(10).removeParagraph(0);
		paragraph = tableRowOne.getCell(10).addParagraph();
		criarParagrafoTabela(paragraph, "PESO NF 1", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(11).removeParagraph(0);
		paragraph = tableRowOne.getCell(11).addParagraph();
		criarParagrafoTabela(paragraph, "VALOR NF 1", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(12).removeParagraph(0);
		paragraph = tableRowOne.getCell(12).addParagraph();
		criarParagrafoTabela(paragraph, "NF 2", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(13).removeParagraph(0);
		paragraph = tableRowOne.getCell(13).addParagraph();
		criarParagrafoTabela(paragraph, "PESO NF 2", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(14).removeParagraph(0);
		paragraph = tableRowOne.getCell(14).addParagraph();
		criarParagrafoTabela(paragraph, "VALOR NF 2", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(15).removeParagraph(0);
		paragraph = tableRowOne.getCell(15).addParagraph();
		criarParagrafoTabela(paragraph, "DIFERENÇA", true);

		int i = cabecalho + 1;

		double peso_total_nf_interna = 0.0;
		double peso_total_romaneios = 0.0;
		double peso_total_nf_venda1 = 0.0;
		double peso_total_nf_complemento = 0.0;
		double peso_total_diferenca = 0.0;

		BigDecimal valor_total_nf_venda1 = BigDecimal.ZERO;
		BigDecimal valor_total_nf_complemento = BigDecimal.ZERO;

		for (CadastroContrato.CadastroTransferenciaCarga transferencia : transferencias) {

			String texto_detalhado = "";

			GerenciarBancoContratos gerencia_contratos = new GerenciarBancoContratos();
			CadastroContrato remetente = gerencia_contratos.getContrato(transferencia.getId_contrato_remetente());
			CadastroContrato destinatario = gerencia_contratos.getContrato(transferencia.getId_contrato_destinatario());
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
					nome_vendedores += ",";

				}
			}

			double quantidade = Double.parseDouble(transferencia.getQuantidade());

			texto_detalhado = "Transferência Positiva: Recebimento de volume de " + z.format(quantidade) + " kgs | "
					+ z.format(quantidade / 60) + " sacos recebidos do contrato ";
			texto_detalhado = texto_detalhado + remetente.getCodigo() + " " + nome_compradores + " X " + nome_vendedores
					+ " " + z.format(remetente.getQuantidade()) + " " + remetente.getMedida() + " de "
					+ remetente.getModelo_safra().getProduto().getNome_produto() + " "
					+ remetente.getModelo_safra().getProduto().getTransgenia() + " da safra "
					+ remetente.getModelo_safra().getAno_plantio() + "/"
					+ remetente.getModelo_safra().getAno_colheita();
			texto_detalhado = texto_detalhado + "";

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(0).removeParagraph(0);
			paragraph = tableRowOne.getCell(0).addParagraph();
			criarParagrafoTabela(paragraph, destinatario.getCodigo(), false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(1).removeParagraph(0);
			paragraph = tableRowOne.getCell(1).addParagraph();
			criarParagrafoTabela(paragraph, texto_detalhado, false);

			hMerge = CTHMerge.Factory.newInstance();
			hMerge.setVal(STMerge.RESTART);
			if (table.getRow(i).getCell(1).getCTTc().getTcPr() == null) {
				table.getRow(i).getCell(1).getCTTc().addNewTcPr();
				table.getRow(i).getCell(1).getCTTc().getTcPr().setHMerge(hMerge);

			} else
				table.getRow(i).getCell(1).getCTTc().getTcPr().setHMerge(hMerge);

			for (int celula = 2; celula <= 15; celula++) {
				tableRowOne = table.getRow(i);
				tableRowOne.getCell(celula).removeParagraph(0);
				paragraph = tableRowOne.getCell(celula).addParagraph();

				criarParagrafoTabela(paragraph, "", true);
				tableRowOne.getCell(celula).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");

				CTHMerge hMerge1 = CTHMerge.Factory.newInstance();
				hMerge1.setVal(STMerge.CONTINUE);
				table.getRow(i).getCell(celula).getCTTc().getTcPr().setHMerge(hMerge1);

			}

			peso_total_romaneios += quantidade;

			i++;

		}

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(1).removeParagraph(0);
		paragraph = tableRowOne.getCell(1).addParagraph();
		criarParagrafoTabela(paragraph, "PESO TOTAL: ", true);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(2).removeParagraph(0);
		paragraph = tableRowOne.getCell(2).addParagraph();
		criarParagrafoTabela(paragraph,
				z.format(peso_total_romaneios) + " Kgs | " + z.format(peso_total_romaneios / 60) + " SCs", true);

		substituirTexto("");

	}

	public void setSingleLineSpacing(XWPFParagraph para) {
		CTPPr ppr = para.getCTP().getPPr();
		if (ppr == null)
			ppr = para.getCTP().addNewPPr();
		CTSpacing spacing = ppr.isSetSpacing() ? ppr.getSpacing() : ppr.addNewSpacing();
		spacing.setAfter(BigInteger.valueOf(0));
		spacing.setBefore(BigInteger.valueOf(0));
		spacing.setLineRule(STLineSpacingRule.AUTO);
		spacing.setLine(BigInteger.valueOf(240));
	}

	public void adicionarTraco(boolean negrito, int flag) {

		XWPFParagraph traco = document_global.createParagraph();
		traco.setAlignment(ParagraphAlignment.CENTER);

		XWPFRun tracotitleRun = traco.createRun();
		if (flag == 1) {
			tracotitleRun.setText("________________________________________________________________________________");

			// continou
		} else {
			// tracejado
			tracotitleRun.setText(
					" _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ __ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ ");

		}
		tracotitleRun.setColor("000000");
		tracotitleRun.setBold(negrito);
		tracotitleRun.setFontFamily("Arial");
		tracotitleRun.setFontSize(12);

	}

	public void substituirTexto(String text_amostra, int alinhamento) {

		// criarParagrafo(2);

		// pegar os paragrafos
		String separador_paragrafo[] = text_amostra.split("\n");
		for (String paragrafo : separador_paragrafo) {
			criarParagrafo(alinhamento);

			paragrafo = paragrafo.replaceAll(" ", "&");

			String separador_palabras[] = paragrafo.split("&");
			for (String palavra : separador_palabras) {
				if (palavra.contains("[") || palavra.contains("]")) {
					adicionarTextoParagrafoAtual(palavra.replaceAll("[\\[\\]]", "") + " ", true);

				} else {

					adicionarTextoParagrafoAtual(palavra + " ", false);

				}

			}
		}

	}

	public void substituirTexto(String text_amostra) {

		// criarParagrafo(2);

		// pegar os paragrafos
		String separador_paragrafo[] = text_amostra.split("\n");
		for (String paragrafo : separador_paragrafo) {
			criarParagrafo(2);

			paragrafo = paragrafo.replaceAll(" ", "&");

			String separador_palabras[] = paragrafo.split("&");
			for (String palavra : separador_palabras) {
				if (palavra.contains("[") || palavra.contains("]")) {
					adicionarTextoParagrafoAtual(palavra.replaceAll("[\\[\\]]", "") + " ", true);

				} else {

					adicionarTextoParagrafoAtual(palavra + " ", false);

				}

			}
		}

	}

	private void setOrientacao(int flag) {
		CTDocument1 doc = document_global.getDocument();
		CTBody body = doc.getBody();
		CTSectPr section = body.addNewSectPr();
		XWPFParagraph para = document_global.createParagraph();
		CTP ctp = para.getCTP();
		CTPPr br = ctp.addNewPPr();
		br.setSectPr(section);
		CTPageSz pageSize;
		if (section.isSetPgSz()) {
			pageSize = section.getPgSz();
		} else {
			pageSize = section.addNewPgSz();
		}

		if (flag == 1) {
			pageSize.setOrient(STPageOrientation.PORTRAIT);
			pageSize.setW(BigInteger.valueOf(842 * 20));
			pageSize.setH(BigInteger.valueOf(595 * 20));
		} else {
			pageSize.setOrient(STPageOrientation.LANDSCAPE);
			pageSize.setH(BigInteger.valueOf(842 * 20));
			pageSize.setW(BigInteger.valueOf(595 * 20));
		}
	}

	public void criarParagrafo(int alinhamento) {
		XWPFParagraph paragrafo = document_global.createParagraph();

		setSingleLineSpacing(paragrafo);
		if (alinhamento == 0) {
			// centro
			paragrafo.setAlignment(ParagraphAlignment.CENTER);

		} else if (alinhamento == 1) {
			// direita
			paragrafo.setAlignment(ParagraphAlignment.RIGHT);

		} else if (alinhamento == -1) {
			// esquerda
			paragrafo.setAlignment(ParagraphAlignment.LEFT);

		} else if (alinhamento == 2) {
			paragrafo.setAlignment(ParagraphAlignment.BOTH);

		}

		paragrafo_atual = paragrafo;
	}

	public void criarParagrafoTabela(XWPFParagraph paragraph, String texto, boolean negrito, String cor) {
		paragraph.setIndentationLeft(100);
		// paragraph.setIndentationRight(100);
		paragraph.setAlignment(ParagraphAlignment.LEFT);

		XWPFRun run = paragraph.createRun();

		run.setFontFamily("Times New Roman");
		run.setFontSize(8);
		run.setColor(cor);
		run.setBold(negrito);
		run.setText(texto);

	}

	public void criarParagrafoTabela(XWPFParagraph paragraph, String texto, boolean negrito, String cor,
			int alinhamento) {
		paragraph.setIndentationLeft(100);
		// paragraph.setIndentationRight(100);
		if (alinhamento == -1)
			paragraph.setAlignment(ParagraphAlignment.LEFT);
		else if (alinhamento == 0)
			paragraph.setAlignment(ParagraphAlignment.CENTER);
		else
			paragraph.setAlignment(ParagraphAlignment.RIGHT);

		XWPFRun run = paragraph.createRun();

		run.setFontFamily("Times New Roman");
		run.setFontSize(8);
		run.setColor(cor);
		run.setBold(negrito);
		run.setText(texto);

	}

	public void criarParagrafoTabela(XWPFParagraph paragraph, String texto, boolean negrito) {
		paragraph.setIndentationLeft(100);
		// paragraph.setIndentationRight(100);
		paragraph.setAlignment(ParagraphAlignment.LEFT);

		XWPFRun run = paragraph.createRun();

		run.setFontFamily("Times New Roman");
		run.setFontSize(8);
		run.setBold(negrito);
		run.setText(texto);

	}

	public void criarTabelaContrato(ArrayList<CadastroContrato> lista_contratos) {

		NumberFormat z = NumberFormat.getNumberInstance();

		int numero_contratos = 0;
		Locale ptBr = new Locale("pt", "BR");
		// variaveis para soma de contratos de vendedor na geracao do relatorio interno
		double quantidade_total_sacos_vendedor = 0;
		double soma_total_valores_vendedor = 0;

		int num_total_linhas = lista_contratos.size();
		ArrayList<GanhoPotencial> lista_ganhos_potenciais = new ArrayList<>();

		int num_colunas = 0;

		XWPFTable table;
		if (incluir_comissao) {
			num_colunas = 16;
			table = document_global.createTable(num_total_linhas + 1, num_colunas);
		} else {
			num_colunas = 14;
			table = document_global.createTable(num_total_linhas + 1, num_colunas);

		}

		setTableAlign(table, ParagraphAlignment.CENTER);
		XWPFTableRow tableRowOne = table.getRow(0);
		tableRowOne.getCell(0).removeParagraph(0);

		XWPFParagraph paragraph = tableRowOne.getCell(0).addParagraph();

		criarParagrafoTabela(paragraph, "CÓDIGO", false, "000000");

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(1).removeParagraph(0);
		paragraph = tableRowOne.getCell(1).addParagraph();
		criarParagrafoTabela(paragraph, "", false, "000000");

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(2).removeParagraph(0);
		paragraph = tableRowOne.getCell(2).addParagraph();
		criarParagrafoTabela(paragraph, "COMPRADORES", false, "000000");

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(3).removeParagraph(0);
		paragraph = tableRowOne.getCell(3).addParagraph();
		criarParagrafoTabela(paragraph, "VENDEDORES", false, "000000");

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(4).removeParagraph(0);
		paragraph = tableRowOne.getCell(4).addParagraph();
		criarParagrafoTabela(paragraph, "PRODUTO", false, "000000");

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(5).removeParagraph(0);
		paragraph = tableRowOne.getCell(5).addParagraph();
		criarParagrafoTabela(paragraph, "TRANSGENIA", false, "000000");

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(6).removeParagraph(0);
		paragraph = tableRowOne.getCell(6).addParagraph();
		criarParagrafoTabela(paragraph, "SAFRA", false, "000000");

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(7).removeParagraph(0);
		paragraph = tableRowOne.getCell(7).addParagraph();
		criarParagrafoTabela(paragraph, "UNIDADE", false, "000000");

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(8).removeParagraph(0);
		paragraph = tableRowOne.getCell(8).addParagraph();
		criarParagrafoTabela(paragraph, "VALOR UNIDADE", false, "000000");

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(9).removeParagraph(0);
		paragraph = tableRowOne.getCell(9).addParagraph();
		criarParagrafoTabela(paragraph, "QUANTIDADE", false, "000000");

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(10).removeParagraph(0);
		paragraph = tableRowOne.getCell(10).addParagraph();
		criarParagrafoTabela(paragraph, "VALOR TOTAL", false, "000000");

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(11).removeParagraph(0);
		paragraph = tableRowOne.getCell(11).addParagraph();
		criarParagrafoTabela(paragraph, "PARTICIPAÇÃO", false, "000000");

		if (incluir_comissao) {

			tableRowOne = table.getRow(0);
			tableRowOne.getCell(12).removeParagraph(0);
			paragraph = tableRowOne.getCell(12).addParagraph();
			criarParagrafoTabela(paragraph, "COMISSÃO(SACO)", false, "000000");

			tableRowOne = table.getRow(0);
			tableRowOne.getCell(13).removeParagraph(0);
			paragraph = tableRowOne.getCell(13).addParagraph();
			criarParagrafoTabela(paragraph, "COMISSÃO(TOTAL)", false, "000000");

			tableRowOne = table.getRow(0);
			tableRowOne.getCell(14).removeParagraph(0);
			paragraph = tableRowOne.getCell(14).addParagraph();
			criarParagrafoTabela(paragraph, "STATUS", false, "000000");

			tableRowOne = table.getRow(0);
			tableRowOne.getCell(15).removeParagraph(0);
			paragraph = tableRowOne.getCell(15).addParagraph();
			criarParagrafoTabela(paragraph, "LOCAL RETIRADA", false, "000000");

		} else {
			tableRowOne = table.getRow(0);
			tableRowOne.getCell(12).removeParagraph(0);
			paragraph = tableRowOne.getCell(12).addParagraph();
			criarParagrafoTabela(paragraph, "STATUS", false, "000000");

			tableRowOne = table.getRow(0);
			tableRowOne.getCell(13).removeParagraph(0);
			paragraph = tableRowOne.getCell(13).addParagraph();
			criarParagrafoTabela(paragraph, "LOCAL RETIRADA", false, "000000");
		}

		int indice = 0;

		double quantitade_total_sacos = 0;
		BigDecimal valor_total = BigDecimal.ZERO;
		BigDecimal valor_total_comissao = BigDecimal.ZERO;

		for (int i = 1; i < lista_contratos.size() + 1; i++) {
			String cor_dados = "000000";
			CadastroContrato local = lista_contratos.get(indice);

			if (local.getSub_contrato() != 8 && local.getSub_contrato() != 9) {
				// é um linha normal
				if (local.getSub_contrato() == 1) {
					// seta a cor vermelha
					if (tipo_contrato == 1)
						cor_dados = "ff0000";
				}

				if (tipo_contrato == 1) {
					if (local.getSub_contrato() == 0 || local.getSub_contrato() == 3 || local.getSub_contrato() == 4
							|| local.getSub_contrato() == 5) {
						numero_contratos++;
					}

				} else {
					numero_contratos++;

				}

				double quantidade_sacos_sub = 0;
				double quantidade_quilogramas_sub = 0;

				if (local.getMedida().equalsIgnoreCase("Sacos")) {
					quantidade_sacos_sub = local.getQuantidade();
					quantidade_quilogramas_sub = local.getQuantidade() * 60;
				} else if (local.getMedida().equalsIgnoreCase("KG")) {
					quantidade_quilogramas_sub = local.getQuantidade();
					quantidade_sacos_sub = local.getQuantidade() / 60;

				}

				String nome_compradores = local.getNomes_compradores().toUpperCase();
				String nome_vendedores = local.getNomes_vendedores().toUpperCase();

				// linha com dados
				tableRowOne = table.getRow(i);
				tableRowOne.getCell(0).removeParagraph(0);
				paragraph = tableRowOne.getCell(0).addParagraph();
				criarParagrafoTabela(paragraph, local.getCodigo(), false, cor_dados);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(1).removeParagraph(0);
				paragraph = tableRowOne.getCell(1).addParagraph();
				criarParagrafoTabela(paragraph, "", false, cor_dados);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(2).removeParagraph(0);
				paragraph = tableRowOne.getCell(2).addParagraph();
				criarParagrafoTabela(paragraph, nome_compradores, false, cor_dados);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(3).removeParagraph(0);
				paragraph = tableRowOne.getCell(3).addParagraph();
				criarParagrafoTabela(paragraph, nome_vendedores.toUpperCase(), false, cor_dados);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(4).removeParagraph(0);
				paragraph = tableRowOne.getCell(4).addParagraph();
				criarParagrafoTabela(paragraph, local.getModelo_safra().getProduto().getNome_produto().toUpperCase(),
						false, cor_dados);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(5).removeParagraph(0);
				paragraph = tableRowOne.getCell(5).addParagraph();
				criarParagrafoTabela(paragraph, local.getModelo_safra().getProduto().getTransgenia().toUpperCase(),
						false, cor_dados);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(6).removeParagraph(0);
				paragraph = tableRowOne.getCell(6).addParagraph();
				criarParagrafoTabela(paragraph,
						local.getModelo_safra().getAno_plantio() + "/" + local.getModelo_safra().getAno_colheita(),
						false, cor_dados);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(7).removeParagraph(0);
				paragraph = tableRowOne.getCell(7).addParagraph();
				criarParagrafoTabela(paragraph, local.getMedida().toUpperCase(), false, cor_dados);

				String valorString = NumberFormat.getCurrencyInstance(ptBr).format(local.getValor_produto());
				tableRowOne = table.getRow(i);
				tableRowOne.getCell(8).removeParagraph(0);
				paragraph = tableRowOne.getCell(8).addParagraph();
				criarParagrafoTabela(paragraph, valorString, false, cor_dados);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(9).removeParagraph(0);
				paragraph = tableRowOne.getCell(9).addParagraph();
				criarParagrafoTabela(paragraph, z.format(local.getQuantidade()), false, cor_dados);

				valorString = NumberFormat.getCurrencyInstance(ptBr).format(local.getValor_a_pagar());
				tableRowOne = table.getRow(i);
				tableRowOne.getCell(10).removeParagraph(0);
				paragraph = tableRowOne.getCell(10).addParagraph();
				criarParagrafoTabela(paragraph, valorString, false, cor_dados);

				int i_participacao = local.getGrupo_particular();
				String s_participacao = "";
				if (i_participacao == 0) {
					s_participacao = "GRUPO";
				} else if (i_participacao == 1) {
					s_participacao = "PARTICULAR";
				} else if (i_participacao == 2) {
					s_participacao = "EMPRÉSTIMO";
				}
				tableRowOne = table.getRow(i);
				tableRowOne.getCell(11).removeParagraph(0);
				paragraph = tableRowOne.getCell(11).addParagraph();
				criarParagrafoTabela(paragraph, s_participacao, false, cor_dados);

				String comissao_total = "";
				if (local.getComissao() == 1) {
					comissao_total = NumberFormat.getCurrencyInstance(ptBr).format(local.getValor_comissao());
					if (local.getSub_contrato() == 0 || local.getSub_contrato() == 3 || local.getSub_contrato() == 4
							|| local.getSub_contrato() == 5)
						valor_total_comissao = valor_total_comissao.add(local.getValor_comissao());
				} else {
					comissao_total = "Não";
				}

				String comissao_por_saco = "";
				if (local.getComissao() == 1) {
					BigDecimal valor_total_com = local.getValor_comissao();
					BigDecimal quantidade_total_sacos = new BigDecimal(Double.toString(quantidade_sacos_sub));
					BigDecimal valor_por_saco = valor_total_com.divide(quantidade_total_sacos, BigDecimal.ROUND_UP);
					comissao_por_saco = NumberFormat.getCurrencyInstance(ptBr).format(valor_por_saco);

				} else {
					comissao_por_saco = "Não";
				}

				int status = local.getStatus_contrato();
				String text_status = "";
				if (status == 1) {
					text_status = "ASSINAR";

				} else if (status == 2) {
					text_status = "ASSINADO";

				} else if (status == 3) {
					text_status = "CONCLUÍDO";

				}

				if (incluir_comissao) {
					tableRowOne = table.getRow(i);
					tableRowOne.getCell(12).removeParagraph(0);
					paragraph = tableRowOne.getCell(12).addParagraph();
					criarParagrafoTabela(paragraph, comissao_por_saco, false, cor_dados);

					tableRowOne = table.getRow(i);
					tableRowOne.getCell(13).removeParagraph(0);
					paragraph = tableRowOne.getCell(13).addParagraph();
					criarParagrafoTabela(paragraph, comissao_total, false, cor_dados);

					tableRowOne = table.getRow(i);
					tableRowOne.getCell(14).removeParagraph(0);
					paragraph = tableRowOne.getCell(14).addParagraph();
					criarParagrafoTabela(paragraph, text_status, false, cor_dados);

					tableRowOne = table.getRow(i);
					tableRowOne.getCell(15).removeParagraph(0);
					paragraph = tableRowOne.getCell(15).addParagraph();
					criarParagrafoTabela(paragraph, local.getNome_local_retirada(), false, cor_dados);

				} else {
					tableRowOne = table.getRow(i);
					tableRowOne.getCell(12).removeParagraph(0);
					paragraph = tableRowOne.getCell(12).addParagraph();
					criarParagrafoTabela(paragraph, text_status, false, cor_dados);

					tableRowOne = table.getRow(i);
					tableRowOne.getCell(13).removeParagraph(0);
					paragraph = tableRowOne.getCell(13).addParagraph();
					criarParagrafoTabela(paragraph, local.getNome_local_retirada(), false, cor_dados);
				}

				/*
				 * if (local.getSub_contrato() == 0 || local.getSub_contrato() == 3 ||
				 * local.getSub_contrato() == 4 || local.getSub_contrato() == 5) {
				 * quantitade_total_sacos += quantidade_sacos_sub; valor_total =
				 * valor_total.add(local.getValor_a_pagar()); } else {
				 * 
				 * if (somar_sub_contratos) { quantitade_total_sacos += quantidade_sacos_sub;
				 * valor_total = valor_total.add(local.getValor_a_pagar()); }
				 * 
				 * }
				 */
				if (tipo_contrato == 1) {
					// relatorio interno, nao somar sub contratos
					if (local.getSub_contrato() == 0 || local.getSub_contrato() == 3 || local.getSub_contrato() == 4
							|| local.getSub_contrato() == 5) {
						quantitade_total_sacos += quantidade_sacos_sub;
						valor_total = valor_total.add(local.getValor_a_pagar());
					}
				} else {
					// relatorio externo, soma se subcontratos
					quantitade_total_sacos += quantidade_sacos_sub;
					valor_total = valor_total.add(local.getValor_a_pagar());
				}

			} else if (local.getSub_contrato() != 9) {
				// linha de ganhos potenciais
				// linha com dados

				GanhoPotencial ganho_potencial = new GanhoPotencial();
				ganho_potencial.setFlag_soma(8);

				String texto = "";
				String s_valor_total_contrato_original = NumberFormat.getCurrencyInstance(ptBr)
						.format(local.getValor_a_pagar());
				ganho_potencial.setCodigo(local.getCodigo());
				ganho_potencial.setTotal_contrato_original(local.getValor_a_pagar());

				texto = texto + "Total(contrato): " + s_valor_total_contrato_original;
				tableRowOne = table.getRow(i);
				tableRowOne.getCell(0).removeParagraph(0);

				paragraph = tableRowOne.getCell(0).addParagraph();

				String s_valor_total_sub_contratos = NumberFormat.getCurrencyInstance(ptBr)
						.format(local.getValor_comissao());
				texto = texto + " Total(sub-contrato): " + s_valor_total_sub_contratos;

				ganho_potencial.setTotal_sub_contratos(local.getValor_comissao());

				String s_diferenca = NumberFormat.getCurrencyInstance(ptBr)
						.format(local.getValor_comissao().subtract(local.getValor_a_pagar()));
				texto = texto + " Diferença: " + s_diferenca;

				ganho_potencial.setDiferenca(local.getValor_comissao().subtract(local.getValor_a_pagar()));

				if (!incluir_comissao) {
					texto = texto + " Ganho Potencial: " + s_diferenca;
					BigDecimal comissao = BigDecimal.ZERO;
					ganho_potencial.setTotal_comissao(comissao);
					ganho_potencial.setGanhos_potenciais(local.getValor_comissao().subtract(local.getValor_a_pagar()));

				} else {

					if (local.getValor_produto() > 0) {
						// tem comissao
						String s_valor_comissao = NumberFormat.getCurrencyInstance(ptBr)
								.format(local.getValor_produto());
						texto = texto + " Comissão: " + s_valor_comissao;
						ganho_potencial.setTotal_comissao(new BigDecimal(local.getValor_produto()));

						BigDecimal diferenca = local.getValor_comissao().subtract(local.getValor_a_pagar());
						double valor_total_ganhos_potenciais = diferenca.doubleValue() + local.getValor_produto();
						String s_valor_ganhos_potenciais = NumberFormat.getCurrencyInstance(ptBr)
								.format(valor_total_ganhos_potenciais);

						texto = texto + "         Ganho Potencial: " + s_valor_ganhos_potenciais;
						ganho_potencial.setGanhos_potenciais(new BigDecimal(valor_total_ganhos_potenciais));

					} else {
						BigDecimal comissao = BigDecimal.ZERO;
						ganho_potencial.setTotal_comissao(comissao);
						BigDecimal diferenca = local.getValor_comissao().subtract(local.getValor_a_pagar());

						String s_ganho_potencial = NumberFormat.getCurrencyInstance(ptBr).format(diferenca);
						texto = texto + "         Ganho Potencial: " + s_ganho_potencial;
						ganho_potencial.setGanhos_potenciais(diferenca);

					}

				}

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(0).removeParagraph(0);
				paragraph = tableRowOne.getCell(0).addParagraph();

				criarParagrafoTabela(paragraph, texto, true, "0000FF", 0);

				tableRowOne.getCell(0).setVerticalAlignment(XWPFVertAlign.CENTER);

				tableRowOne.getCell(0).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");
				CTHMerge hMerge = CTHMerge.Factory.newInstance();
				hMerge.setVal(STMerge.RESTART);
				table.getRow(i).getCell(0).getCTTc().getTcPr().setHMerge(hMerge);

				for (int celula = 1; celula <= num_colunas - 1; celula++) {
					tableRowOne = table.getRow(i);
					tableRowOne.getCell(celula).removeParagraph(0);
					paragraph = tableRowOne.getCell(celula).addParagraph();

					criarParagrafoTabela(paragraph, "", true);
					tableRowOne.getCell(celula).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");

					CTHMerge hMerge1 = CTHMerge.Factory.newInstance();
					hMerge1.setVal(STMerge.CONTINUE);
					table.getRow(i).getCell(celula).getCTTc().getTcPr().setHMerge(hMerge1);
					tableRowOne.getCell(celula).setVerticalAlignment(XWPFVertAlign.CENTER);

				}

				lista_ganhos_potenciais.add(ganho_potencial);
			} else if (local.getSub_contrato() == 9) {

				GanhoPotencial ganho_potencial = new GanhoPotencial();
				ganho_potencial.setFlag_soma(9);
				ganho_potencial.setCodigo(local.getCodigo());
				ganho_potencial.setTotal_contrato_original(local.getValor_a_pagar());
				ganho_potencial.setTotal_sub_contratos(local.getValor_a_pagar());
				ganho_potencial.setDiferenca(BigDecimal.ZERO);

				if (!incluir_comissao) {
					BigDecimal comissao = BigDecimal.ZERO;
					ganho_potencial.setTotal_comissao(comissao);
					ganho_potencial.setGanhos_potenciais(local.getValor_comissao().subtract(local.getValor_a_pagar()));

				} else {
					if (local.getValor_produto() > 0) {

						ganho_potencial.setTotal_comissao(new BigDecimal(local.getValor_produto()));

						BigDecimal diferenca = local.getValor_comissao().subtract(local.getValor_a_pagar());
						double valor_total_ganhos_potenciais = diferenca.doubleValue() + local.getValor_produto();

						ganho_potencial.setGanhos_potenciais(new BigDecimal(valor_total_ganhos_potenciais));

					} else {
						BigDecimal comissao = BigDecimal.ZERO;
						ganho_potencial.setTotal_comissao(comissao);
						BigDecimal diferenca = local.getValor_comissao().subtract(local.getValor_a_pagar());
						ganho_potencial.setGanhos_potenciais(diferenca);

					}
				}

				lista_ganhos_potenciais.add(ganho_potencial);

			} // fim de linha ganho potencial
			indice++;
		}

		String valorTotalString = NumberFormat.getCurrencyInstance(ptBr).format(valor_total);

		String valorTotalComissaoString = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_comissao);
		criarParagrafo(1);

		if (incluir_comissao) {
			substituirTexto("Quantidade de contratos: " + "[" + numero_contratos + "] contratos "
					+ "Quantidade Total de Sacos: [" + z.format(quantitade_total_sacos) + "] sacos Valor Total: ["
					+ valorTotalString + "] Valor Total Comissão: [" + valorTotalComissaoString + "]", 1);

		} else {
			substituirTexto("Quantidade de contratos: " + "[" + numero_contratos + "] contratos "
					+ "Quantidade Total de Sacos: [" + z.format(quantitade_total_sacos) + "] sacos Valor Total: ["
					+ valorTotalString + "]", 1);

		}
		criarParagrafo(2);
		substituirTexto("\n\n");

		if (incluir_ganhos_potencias)
			criarTabelaGanhosPotenciais(lista_ganhos_potenciais);

	}

	public DadosTabelaExcel criarTabelaContratoExcel(HSSFWorkbook workbook, HSSFSheet sheet, int rownum,
			ArrayList<CadastroContrato> lista_contratos) {

		HSSFDataFormat numberFormat = workbook.createDataFormat();

		HSSFFont newFont_branca = workbook.createFont();
		newFont_branca.setBold(true);
		newFont_branca.setColor(IndexedColors.WHITE.getIndex());
		newFont_branca.setFontName("Calibri");
		newFont_branca.setItalic(false);
		newFont_branca.setFontHeight((short) (11 * 20));

		// estilo para cabecalho fundo verde
		CellStyle celula_fundo_verde_texto_branco = workbook.createCellStyle();
		celula_fundo_verde_texto_branco.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		celula_fundo_verde_texto_branco.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		celula_fundo_verde_texto_branco.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_verde_texto_branco.setVerticalAlignment(VerticalAlignment.CENTER);

		celula_fundo_verde_texto_branco.setFont(newFont_branca);

		HSSFFont newFont_preta = workbook.createFont();
		newFont_preta.setColor(IndexedColors.BLACK.getIndex());
		newFont_preta.setFontName("Calibri");
		newFont_preta.setItalic(false);
		newFont_preta.setFontHeight((short) (11 * 20));

		CellStyle celula_fundo_branco_texto_preto = workbook.createCellStyle();
		celula_fundo_branco_texto_preto.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_branco_texto_preto.setVerticalAlignment(VerticalAlignment.CENTER);
		celula_fundo_branco_texto_preto.setFont(newFont_preta);

		// celula para numero alinhado ao centro
		CellStyle numberStyle = workbook.createCellStyle();
		numberStyle.setDataFormat(numberFormat.getFormat("R$ #,##0.00"));
		numberStyle.setAlignment(HorizontalAlignment.CENTER);
		numberStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		// celula para numero alinhado ao centro
		CellStyle pesoStyle = workbook.createCellStyle();
		pesoStyle.setDataFormat(numberFormat.getFormat("#,##0.00"));
		pesoStyle.setAlignment(HorizontalAlignment.CENTER);
		pesoStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle numberStyleFundoVerdeTextoBranco = workbook.createCellStyle();
		numberStyleFundoVerdeTextoBranco.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		numberStyleFundoVerdeTextoBranco.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		numberStyleFundoVerdeTextoBranco.setAlignment(HorizontalAlignment.CENTER);
		numberStyleFundoVerdeTextoBranco.setVerticalAlignment(VerticalAlignment.CENTER);
		numberStyleFundoVerdeTextoBranco.setDataFormat(numberFormat.getFormat("#,##0.00"));

		numberStyleFundoVerdeTextoBranco.setFont(newFont_branca);

		CellStyle valorStyleFundoVerdeTextoBranco = workbook.createCellStyle();
		valorStyleFundoVerdeTextoBranco.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		valorStyleFundoVerdeTextoBranco.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		valorStyleFundoVerdeTextoBranco.setAlignment(HorizontalAlignment.CENTER);
		valorStyleFundoVerdeTextoBranco.setVerticalAlignment(VerticalAlignment.CENTER);
		valorStyleFundoVerdeTextoBranco.setDataFormat(numberFormat.getFormat("R$ #,##0.00"));
		valorStyleFundoVerdeTextoBranco.setFont(newFont_branca);

		// celular de contrato normal
		HSSFFont newFont_verde = workbook.createFont();
		newFont_verde.setColor(IndexedColors.GREEN.getIndex());
		newFont_verde.setFontName("Calibri");
		newFont_verde.setItalic(false);
		newFont_verde.setFontHeight((short) (11 * 20));

		CellStyle celula_fundo_branco_texto_verde = workbook.createCellStyle();
		celula_fundo_branco_texto_verde.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_branco_texto_verde.setVerticalAlignment(VerticalAlignment.CENTER);
		celula_fundo_branco_texto_verde.setFont(newFont_verde);

		// celular de sub-contrato n
		HSSFFont newFont_vermelha = workbook.createFont();
		newFont_vermelha.setColor(IndexedColors.RED.getIndex());
		newFont_vermelha.setFontName("Calibri");
		newFont_vermelha.setItalic(false);
		newFont_vermelha.setFontHeight((short) (11 * 20));

		CellStyle celula_fundo_branco_texto_vermelho = workbook.createCellStyle();
		celula_fundo_branco_texto_vermelho.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_branco_texto_vermelho.setVerticalAlignment(VerticalAlignment.CENTER);
		celula_fundo_branco_texto_vermelho.setFont(newFont_vermelha);

		// celula de ganho potencial

		// celular de sub-contrato n
		HSSFFont newFont_azul = workbook.createFont();
		newFont_azul.setColor(IndexedColors.BLUE.getIndex());
		newFont_azul.setFontName("Calibri");
		newFont_azul.setItalic(false);
		newFont_azul.setFontHeight((short) (11 * 20));

		CellStyle celula_fundo_branco_texto_azul = workbook.createCellStyle();
		celula_fundo_branco_texto_azul.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_branco_texto_azul.setVerticalAlignment(VerticalAlignment.CENTER);
		celula_fundo_branco_texto_azul.setFont(newFont_azul);

		NumberFormat z = NumberFormat.getNumberInstance();

		int numero_contratos = 0;
		Locale ptBr = new Locale("pt", "BR");
		// variaveis para soma de contratos de vendedor na geracao do relatorio interno
		double quantidade_total_sacos_vendedor = 0;
		double soma_total_valores_vendedor = 0;

		int num_total_linhas = lista_contratos.size();
		ArrayList<GanhoPotencial> lista_ganhos_potenciais = new ArrayList<>();

		int num_colunas = 0;

		int filtro_tabela_info_contrato = rownum += 1;
		int cellnum = 0;

		Cell cell;
		Row row;
		row = sheet.createRow(rownum++);

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("CÓDIGO");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("TIPO");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("COMPRADORES".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("VENDEDORES".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("PRODUTO".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("TRANSGENIA".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("SAFRA".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("UNIDADE".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("VALOR UNIDADE".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("QUANTIDADE".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("VALOR TOTAL".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("PARTICIPAÇÃO".toUpperCase());

		if (incluir_comissao) {

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_verde_texto_branco);
			cell.setCellValue("COMISSÃO(SACO)".toUpperCase());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_verde_texto_branco);
			cell.setCellValue("COMISSÃO(TOTAL)".toUpperCase());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_verde_texto_branco);
			cell.setCellValue("STATUS".toUpperCase());

			num_colunas = 15;

		} else {
			cell = row.createCell(cellnum++);
			cell.setCellStyle(celula_fundo_verde_texto_branco);
			cell.setCellValue("STATUS".toUpperCase());

			num_colunas = 16;
		}

		int indice = 0;

		int primeira_linha = rownum;
		int ultima_linha = rownum;

		double quantitade_total_sacos = 0;
		BigDecimal valor_total = BigDecimal.ZERO;
		BigDecimal valor_total_comissao = BigDecimal.ZERO;

		for (int i = 1; i < lista_contratos.size() + 1; i++) {

			cellnum = 0;
			row = sheet.createRow(rownum);
			String tipo = "1";

			CellStyle estilo = celula_fundo_branco_texto_verde;

			CadastroContrato local = lista_contratos.get(indice);

			if (local.getSub_contrato() != 8 && local.getSub_contrato() != 9) {
				// é um linha normal
				if (local.getSub_contrato() == 1) {
					// seta a cor vermelha
					if (tipo_contrato == 1) {
						estilo = celula_fundo_branco_texto_vermelho;
						tipo = "0";
					}
				}

				if (tipo_contrato == 1) {
					if (local.getSub_contrato() == 0 || local.getSub_contrato() == 3 || local.getSub_contrato() == 4
							|| local.getSub_contrato() == 5) {
						numero_contratos++;
					}

				} else {
					numero_contratos++;

				}

				double quantidade_sacos_sub = 0;
				double quantidade_quilogramas_sub = 0;
				double valor_produto = 0.0;

				if (local.getMedida().equalsIgnoreCase("Sacos")) {
					quantidade_sacos_sub = local.getQuantidade();
					quantidade_quilogramas_sub = local.getQuantidade() * 60;
					valor_produto = local.getValor_produto();
				} else if (local.getMedida().equalsIgnoreCase("KG")) {

					// converter para sacos
					quantidade_quilogramas_sub = local.getQuantidade();
					quantidade_sacos_sub = local.getQuantidade() / 60;
					valor_produto = local.getValor_produto() * 60;

				}

				String nome_compradores = local.getNomes_compradores().toUpperCase();
				String nome_vendedores = local.getNomes_vendedores().toUpperCase();

				cell = row.createCell(cellnum++);
				cell.setCellStyle(estilo);
				cell.setCellValue(local.getCodigo());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(estilo);
				cell.setCellValue(tipo);

				cell = row.createCell(cellnum++);
				cell.setCellStyle(estilo);
				cell.setCellValue(nome_compradores.toUpperCase());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(estilo);
				cell.setCellValue(nome_vendedores.toUpperCase());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(estilo);
				cell.setCellValue(local.getModelo_safra().getProduto().getNome_produto().toUpperCase());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(estilo);
				cell.setCellValue(local.getModelo_safra().getProduto().getTransgenia().toUpperCase());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(estilo);
				cell.setCellValue(
						local.getModelo_safra().getAno_plantio() + "/" + local.getModelo_safra().getAno_colheita());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(estilo);
				cell.setCellValue(local.getMedida().toUpperCase());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(numberStyle);

				cell.setCellValue(valor_produto);

				cell = row.createCell(cellnum++);
				cell.setCellStyle(pesoStyle);
				cell.setCellValue(quantidade_sacos_sub);

				cell = row.createCell(cellnum++);
				cell.setCellStyle(numberStyle);
				cell.setCellValue(local.getValor_a_pagar().doubleValue());

				int i_participacao = local.getGrupo_particular();
				String s_participacao = "";
				if (i_participacao == 0) {
					s_participacao = "GRUPO";
				} else if (i_participacao == 1) {
					s_participacao = "PARTICULAR";
				} else if (i_participacao == 2) {
					s_participacao = "EMPRÉSTIMO";
				}

				cell = row.createCell(cellnum++);
				cell.setCellStyle(estilo);
				cell.setCellValue(s_participacao);

				int status = local.getStatus_contrato();
				String text_status = "";
				if (status == 1) {
					text_status = "ASSINAR";

				} else if (status == 2) {
					text_status = "ASSINADO";

				} else if (status == 3) {
					text_status = "CONCLUÍDO";

				}

				if (incluir_comissao) {
					if (local.getComissao() == 1) {

						BigDecimal valor_total_com = local.getValor_comissao();
						BigDecimal quantidade_total_sacos = new BigDecimal(Double.toString(quantidade_sacos_sub));
						BigDecimal valor_por_saco = valor_total_com.divide(quantidade_total_sacos, BigDecimal.ROUND_UP);

						cell = row.createCell(cellnum++);
						cell.setCellStyle(numberStyle);
						cell.setCellValue(valor_por_saco.doubleValue());

						cell = row.createCell(cellnum++);
						cell.setCellStyle(numberStyle);
						cell.setCellValue(valor_total_com.doubleValue());

						cell = row.createCell(cellnum++);
						cell.setCellStyle(estilo);
						cell.setCellValue(text_status);

					} else {
						String comissao_total = "Não";
						String comissao_por_saco = "Não";

						cell = row.createCell(cellnum++);
						cell.setCellStyle(numberStyle);
						cell.setCellValue(comissao_por_saco);

						cell = row.createCell(cellnum++);
						cell.setCellStyle(numberStyle);
						cell.setCellValue(comissao_total);

						cell = row.createCell(cellnum++);
						cell.setCellStyle(estilo);
						cell.setCellValue(text_status);

					}
				} else {
					cell = row.createCell(cellnum++);
					cell.setCellStyle(estilo);
					cell.setCellValue(text_status);

				}

				if (tipo_contrato == 1) {
					// relatorio interno, nao somar sub contratos
					if (local.getSub_contrato() == 0 || local.getSub_contrato() == 3 || local.getSub_contrato() == 4
							|| local.getSub_contrato() == 5) {
						quantitade_total_sacos += quantidade_sacos_sub;
						valor_total = valor_total.add(local.getValor_a_pagar());
					}
				} else {
					// relatorio externo, soma se subcontratos
					quantitade_total_sacos += quantidade_sacos_sub;
					valor_total = valor_total.add(local.getValor_a_pagar());
				}

			} else if (local.getSub_contrato() == 8) {
				// linha de ganhos potenciais
				// linha com dados

				GanhoPotencial ganho_potencial = new GanhoPotencial();
				String texto = "";
				String s_valor_total_contrato_original = NumberFormat.getCurrencyInstance(ptBr)
						.format(local.getValor_a_pagar());
				ganho_potencial.setCodigo(local.getCodigo());
				ganho_potencial.setTotal_contrato_original(local.getValor_a_pagar());

				texto = texto + "Total(contrato): " + s_valor_total_contrato_original;

				String s_valor_total_sub_contratos = NumberFormat.getCurrencyInstance(ptBr)
						.format(local.getValor_comissao());
				texto = texto + " Total(sub-contrato): " + s_valor_total_sub_contratos;

				ganho_potencial.setTotal_sub_contratos(local.getValor_comissao());

				String s_diferenca = NumberFormat.getCurrencyInstance(ptBr)
						.format(local.getValor_comissao().subtract(local.getValor_a_pagar()));
				texto = texto + " Diferença: " + s_diferenca;

				ganho_potencial.setDiferenca(local.getValor_comissao().subtract(local.getValor_a_pagar()));

				if (!incluir_comissao) {
					texto = texto + " Ganho Potencial: " + s_diferenca;
					BigDecimal comissao = BigDecimal.ZERO;
					ganho_potencial.setTotal_comissao(comissao);
					ganho_potencial.setGanhos_potenciais(local.getValor_comissao().subtract(local.getValor_a_pagar()));

				} else {

					if (local.getValor_produto() > 0) {
						// tem comissao
						String s_valor_comissao = NumberFormat.getCurrencyInstance(ptBr)
								.format(local.getValor_produto());
						texto = texto + " Comissão: " + s_valor_comissao;
						ganho_potencial.setTotal_comissao(new BigDecimal(local.getValor_produto()));

						BigDecimal diferenca = local.getValor_comissao().subtract(local.getValor_a_pagar());
						double valor_total_ganhos_potenciais = diferenca.doubleValue() + local.getValor_produto();
						String s_valor_ganhos_potenciais = NumberFormat.getCurrencyInstance(ptBr)
								.format(valor_total_ganhos_potenciais);

						texto = texto + "         Ganho Potencial: " + s_valor_ganhos_potenciais;
						ganho_potencial.setGanhos_potenciais(new BigDecimal(valor_total_ganhos_potenciais));

					} else {
						BigDecimal comissao = BigDecimal.ZERO;
						ganho_potencial.setTotal_comissao(comissao);
						BigDecimal diferenca = local.getValor_comissao().subtract(local.getValor_a_pagar());

						String s_ganho_potencial = NumberFormat.getCurrencyInstance(ptBr).format(diferenca);
						texto = texto + "         Ganho Potencial: " + s_ganho_potencial;
						ganho_potencial.setGanhos_potenciais(diferenca);

					}

				}

				cell = row.createCell(0);
				cell.setCellStyle(celula_fundo_branco_texto_azul);
				cell.setCellValue(texto);

				sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, cellnum, num_colunas));

				lista_ganhos_potenciais.add(ganho_potencial);
			} // fim de linha ganho potencial

			rownum++;
			ultima_linha = rownum;
			indice++;
		}

		if (incluir_comissao) {
			sheet.setAutoFilter(CellRangeAddress
					.valueOf("A" + (filtro_tabela_info_contrato + 1) + ":O" + (filtro_tabela_info_contrato + 1)));

		} else {
			sheet.setAutoFilter(CellRangeAddress
					.valueOf("A" + (filtro_tabela_info_contrato + 1) + ":M" + (filtro_tabela_info_contrato + 1)));
		}
		for (int i = 0; i < num_colunas; i++) {
			sheet.autoSizeColumn(i);

		}

		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		cell = row.createCell(9);
		cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
		cell.setCellType(CellType.FORMULA);
		// String formula = "SUBTOTAL(9,J" + primeira_linha + ":J" + (ultima_linha) +
		// ")";
		String formula = "SUMPRODUCT(SUBTOTAL(9,OFFSET(J" + primeira_linha + ":J" + ultima_linha + ",ROW(J"
				+ primeira_linha + ":J" + ultima_linha + ")-ROW(J" + primeira_linha + "),0,1,1)),-(B" + primeira_linha
				+ ":B" + ultima_linha + "=\"1\")) * -1";
		cell.setCellFormula(formula);

		cell = row.createCell(10);
		cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
		cell.setCellType(CellType.FORMULA);

		// formula = "SUBTOTAL(9,K" + primeira_linha + ":L" + (ultima_linha) + ")";
		formula = "SUMPRODUCT(SUBTOTAL(9,OFFSET(K" + primeira_linha + ":K" + ultima_linha + ",ROW(K" + primeira_linha
				+ ":K" + ultima_linha + ")-ROW(K" + primeira_linha + "),0,1,1)),-(B" + primeira_linha + ":B"
				+ ultima_linha + "=\"1\")) * -1 ";
		cell.setCellFormula(formula);

		if (incluir_comissao) {
			cell = row.createCell(13);
			cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
			cell.setCellType(CellType.FORMULA);

			formula = "SUMPRODUCT(SUBTOTAL(9,OFFSET(N" + primeira_linha + ":N" + ultima_linha + ",ROW(N"
					+ primeira_linha + ":N" + ultima_linha + ")-ROW(N" + primeira_linha + "),0,1,1)),-(B"
					+ primeira_linha + ":B" + ultima_linha + "=\"1\")) * -1 ";
			cell.setCellFormula(formula);

		}

		DadosTabelaExcel retornar = new DadosTabelaExcel();
		retornar.setWorkbook(workbook);
		retornar.setSheet(sheet);
		retornar.setRownum(rownum);

		return retornar;

	}

	public void criarTabelaPagamentos(ArrayList<PagamentoCompleto> pagamentos, CadastroContrato novo_contrato) {

		NumberFormat z = NumberFormat.getNumberInstance();

		Locale ptBr = new Locale("pt", "BR");

		// criarParagrafo(1);
		// linhas x colunas
		int num_total_linhas = 0;
		int num_linhas_comissao = 0;
		int num_linhas_trans = 0;
		int num_linhas_pag_normal = 0;

		for (PagamentoCompleto pag : pagamentos) {
			if (pag.getTipo() == 1) {
				num_linhas_pag_normal++;
			} else if (pag.getTipo() == 2) {
				num_linhas_comissao++;
			} else if (pag.getTipo() == 3) {
				num_linhas_trans++;
			}
		}

		if (incluir_comissao_pagamento) {
			num_total_linhas += num_linhas_comissao;
		}
		if (incluir_transferencias_pagamentos) {
			num_total_linhas += num_linhas_trans;
		}

		num_total_linhas += num_linhas_pag_normal;

		substituirTexto("Número de Pagamentos Normais: " + num_linhas_pag_normal, 2);
		if (incluir_comissao_pagamento)
			substituirTexto("Número de Pagamentos Comissão " + num_linhas_comissao, 2);
		if (incluir_transferencias_pagamentos)
			substituirTexto("Número de Transfêrencias Efetuadas: " + num_linhas_trans, 2);

		XWPFTable table = document_global.createTable(num_total_linhas + 5, 12);

		setTableAlign(table, ParagraphAlignment.CENTER);
		XWPFTableRow tableRowOne = table.getRow(0);
		tableRowOne.getCell(0).removeParagraph(0);

		XWPFParagraph paragraph = tableRowOne.getCell(0).addParagraph();

		//
		int cabecalho = 0;

		double valor_total_pagamentos = 0.0;
		double peso_total_cobertura = getPesoTotalRecebido(novo_contrato) / 60;

		if (novo_contrato.getMedida().equalsIgnoreCase("KG")) {
			valor_total_pagamentos = (peso_total_cobertura * 60) * novo_contrato.getValor_produto();
		} else if (novo_contrato.getMedida().equalsIgnoreCase("Sacos")) {
			valor_total_pagamentos = peso_total_cobertura * novo_contrato.getValor_produto();

		}

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(0).removeParagraph(0);
		paragraph = tableRowOne.getCell(0).addParagraph();

		// compradores x vendedores

		// safra
		String safra = novo_contrato.getModelo_safra().getProduto().getNome_produto() + " "
				+ novo_contrato.getModelo_safra().getProduto().getTransgenia() + " "
				+ novo_contrato.getModelo_safra().getAno_plantio() + "/"
				+ novo_contrato.getModelo_safra().getAno_colheita();

		double quantidade_kg = 0;
		double quantidade_sacos = 0;

		if (novo_contrato.getMedida().equalsIgnoreCase("KG")) {
			quantidade_kg = novo_contrato.getQuantidade();
			quantidade_sacos = quantidade_kg / 60;
		} else if (novo_contrato.getMedida().equalsIgnoreCase("Sacos")) {
			quantidade_sacos = novo_contrato.getQuantidade();
			quantidade_kg = quantidade_sacos * 60;
		}

		criarParagrafoTabela(paragraph,
				"CTR: " + novo_contrato.getCodigo() + " " + safra + " Quantidade Total: " + z.format(quantidade_kg)
						+ " kgs | " + z.format(quantidade_sacos) + " sacos "
						+ NumberFormat.getCurrencyInstance(ptBr).format(novo_contrato.getValor_produto()) + " por "
						+ novo_contrato.getMedida() + " totalizando: "
						+ NumberFormat.getCurrencyInstance(ptBr).format(novo_contrato.getValor_a_pagar().doubleValue()),
				true);
		tableRowOne.getCell(0).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");
		CTHMerge hMerge = CTHMerge.Factory.newInstance();
		hMerge.setVal(STMerge.RESTART);
		table.getRow(cabecalho).getCell(0).getCTTc().getTcPr().setHMerge(hMerge);

		for (int celula = 1; celula <= 11; celula++) {
			tableRowOne = table.getRow(cabecalho);
			tableRowOne.getCell(celula).removeParagraph(0);
			paragraph = tableRowOne.getCell(celula).addParagraph();

			criarParagrafoTabela(paragraph, "", true);
			tableRowOne.getCell(celula).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");

			CTHMerge hMerge1 = CTHMerge.Factory.newInstance();
			hMerge1.setVal(STMerge.CONTINUE);
			table.getRow(cabecalho).getCell(celula).getCTTc().getTcPr().setHMerge(hMerge1);

		}

		cabecalho++;

		// linha com nome compradores x vendedores

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(0).removeParagraph(0);
		paragraph = tableRowOne.getCell(0).addParagraph();

		CadastroCliente compradores[] = novo_contrato.getCompradores();
		CadastroCliente vendedores[] = novo_contrato.getVendedores();

		String nome_vendedores = "";
		String nome_compradores = "";

		if (compradores[0] != null) {
			if (compradores[0].getTipo_pessoa() == 0) {
				// pessoa fisica
				nome_compradores = compradores[0].getNome_empresarial();
			} else {
				nome_compradores = compradores[0].getNome_fantaia();

			}
		}
		if (compradores[1] != null) {
			if (compradores[1].getTipo_pessoa() == 0) {
				// pessoa fisica
				nome_compradores = nome_compradores + ", " + compradores[1].getNome_empresarial();
			} else {
				nome_compradores = nome_compradores + ", " + compradores[1].getNome_fantaia();

			}
		}
		if (vendedores[0] != null) {
			if (vendedores[0].getTipo_pessoa() == 0) {
				nome_vendedores = vendedores[0].getNome_empresarial();
			} else {
				nome_vendedores = vendedores[0].getNome_fantaia();
			}
		}

		if (vendedores[1] != null) {
			if (vendedores[1].getTipo_pessoa() == 0) {
				nome_vendedores = nome_vendedores + ", " + vendedores[1].getNome_empresarial();
			} else {
				nome_vendedores = nome_vendedores + ", " + vendedores[1].getNome_fantaia();
			}
		}

		criarParagrafoTabela(paragraph, novo_contrato.getNomes_compradores().toUpperCase() + " X "
				+ novo_contrato.getNomes_vendedores().toUpperCase(), true);
		tableRowOne.getCell(0).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");
		hMerge = CTHMerge.Factory.newInstance();
		hMerge.setVal(STMerge.RESTART);
		table.getRow(cabecalho).getCell(0).getCTTc().getTcPr().setHMerge(hMerge);

		for (int celula = 1; celula <= 11; celula++) {
			tableRowOne = table.getRow(cabecalho);
			tableRowOne.getCell(celula).removeParagraph(0);
			paragraph = tableRowOne.getCell(celula).addParagraph();

			criarParagrafoTabela(paragraph, "", true);
			tableRowOne.getCell(celula).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");

			CTHMerge hMerge1 = CTHMerge.Factory.newInstance();
			hMerge1.setVal(STMerge.CONTINUE);
			table.getRow(cabecalho).getCell(celula).getCTTc().getTcPr().setHMerge(hMerge1);

		}

		cabecalho++;

		int i = cabecalho;
		tableRowOne = table.getRow(i);
		tableRowOne.getCell(0).removeParagraph(0);
		paragraph = tableRowOne.getCell(0).addParagraph();

		criarParagrafoTabela(paragraph, "Data", false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(1).removeParagraph(0);
		paragraph = tableRowOne.getCell(1).addParagraph();
		criarParagrafoTabela(paragraph, "Tipo", false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(2).removeParagraph(0);
		paragraph = tableRowOne.getCell(2).addParagraph();
		criarParagrafoTabela(paragraph, "Descrição", false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(3).removeParagraph(0);
		paragraph = tableRowOne.getCell(3).addParagraph();
		criarParagrafoTabela(paragraph, "Valor Pagamento", false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(4).removeParagraph(0);
		paragraph = tableRowOne.getCell(4).addParagraph();
		criarParagrafoTabela(paragraph, "Valor Unidade: ", false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(5).removeParagraph(0);
		paragraph = tableRowOne.getCell(5).addParagraph();
		criarParagrafoTabela(paragraph, "Cobertura: ", false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(6).removeParagraph(0);
		paragraph = tableRowOne.getCell(6).addParagraph();
		criarParagrafoTabela(paragraph, "Depositante", false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(7).removeParagraph(0);
		paragraph = tableRowOne.getCell(7).addParagraph();
		criarParagrafoTabela(paragraph, "Conta Depositante", false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(8).removeParagraph(0);
		paragraph = tableRowOne.getCell(8).addParagraph();
		criarParagrafoTabela(paragraph, "Favorecido", false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(9).removeParagraph(0);
		paragraph = tableRowOne.getCell(9).addParagraph();
		criarParagrafoTabela(paragraph, "Conta Favorecido", false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(10).removeParagraph(0);
		paragraph = tableRowOne.getCell(10).addParagraph();
		criarParagrafoTabela(paragraph, "Contrato Remetente", false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(11).removeParagraph(0);
		paragraph = tableRowOne.getCell(11).addParagraph();
		criarParagrafoTabela(paragraph, "Contrato Destinatario", false);

		int i_global = i + 1;

		double valor_total_pagamentos_efetuados = 0;
		double valor_total_transferencias_retiradas = 0;
		double valor_total_transferencias_recebidas = 0;
		double valor_total_pagamentos_restantes = 0;
		double valor_total_comissao = 0;
		double peso_total_cobertura_efetuados = 0;
		double peso_total_cobertura_transferencia_negativa = 0;
		double peso_total_cobertura_transferencia_positiva = 0;
		double peso_total_cobertura_restante = 0;
		double peso_total_cobertura_comissao = 0;

		for (PagamentoCompleto pagamento : pagamentos) {

			if (pagamento.getTipo() == 1 || (pagamento.getTipo() == 2 && incluir_comissao_pagamento)
					|| (pagamento.getTipo() == 3 && incluir_transferencias_pagamentos)) {

				// celula data
				tableRowOne = table.getRow(i_global);
				tableRowOne.getCell(0).removeParagraph(0);
				paragraph = tableRowOne.getCell(0).addParagraph();
				criarParagrafoTabela(paragraph, pagamento.getData_pagamento(), false);

				double valor_por_saco = 0;
				// if(soma_total_pagamentos != 0)
				// novo_contrato = pagamento.getContrato_receptor();

				double quantidade_total_contrato_sacos = 0;

				if (novo_contrato.getMedida().equalsIgnoreCase("Kg")) {
					quantidade_total_contrato_sacos = novo_contrato.getQuantidade() / 60;
					valor_por_saco = novo_contrato.getValor_produto() * 60;
				} else if (novo_contrato.getMedida().equalsIgnoreCase("Sacos")) {
					quantidade_total_contrato_sacos = novo_contrato.getQuantidade();
					valor_por_saco = novo_contrato.getValor_produto();
				}
				GerenciarBancoContratos gerenciar_contratos = new GerenciarBancoContratos();

				int tipo = pagamento.getTipo();
				String s_tipo = "";
				if (pagamento.getTipo() == 1) {
					s_tipo = "NORMAL";
				} else if (pagamento.getTipo() == 2) {
					s_tipo = "COMISSÃO";
				} else if (pagamento.getTipo() == 3) {
					// é uma transferencia
					if (pagamento.getId_contrato_remetente() == novo_contrato.getId()) {
						s_tipo = "-TRANSFERENCIA";
					} else if (pagamento.getId_contrato_destinatario() == novo_contrato.getId()) {
						// é uma transferencia positiva
						s_tipo = "+TRANSFERENCIA";
					}

				}

				// celula tipo
				tableRowOne = table.getRow(i_global);
				tableRowOne.getCell(1).removeParagraph(0);
				paragraph = tableRowOne.getCell(1).addParagraph();
				criarParagrafoTabela(paragraph, s_tipo, false);

				// celula descricao
				tableRowOne = table.getRow(i_global);
				tableRowOne.getCell(2).removeParagraph(0);
				paragraph = tableRowOne.getCell(2).addParagraph();
				criarParagrafoTabela(paragraph, pagamento.getDescricao(), false);

				// valor pagamento
				double valor_pagamento = pagamento.getValor_pagamento();

				String valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor_pagamento);
				double cobertura = valor_pagamento / valor_por_saco;

				if (pagamento.getTipo() == 1) {
					valor_total_pagamentos_efetuados += valor_pagamento;

				} else if (pagamento.getTipo() == 2) {
					// é uma comissão
					valor_total_comissao += valor_pagamento;

				} else if (pagamento.getTipo() == 3) {
					// é uma transferencia
					if (pagamento.getId_contrato_remetente() == novo_contrato.getId()) {
						// é uma transferencia negativa
						valor_total_transferencias_retiradas += valor_pagamento;

					} else if (pagamento.getId_contrato_destinatario() == novo_contrato.getId()) {
						// é uma transferencia positiva
						valor_total_transferencias_recebidas += valor_pagamento;

					}

				}
				valorString = NumberFormat.getCurrencyInstance(ptBr).format(pagamento.getValor_pagamento());

				// celula pagamento
				tableRowOne = table.getRow(i_global);
				tableRowOne.getCell(3).removeParagraph(0);
				paragraph = tableRowOne.getCell(3).addParagraph();
				criarParagrafoTabela(paragraph, valorString, false);

				// valor da unidade
				CadastroContrato ct_remetente = pagamento.getContrato_remetente();
				CadastroContrato ct_destinatario = pagamento.getContrato_destinatario();

				valorString = NumberFormat.getCurrencyInstance(ptBr).format(ct_remetente.getValor_produto());
				if (pagamento.getTipo() == 1) {

				} else if (pagamento.getTipo() == 2) {

				} else if (pagamento.getTipo() == 3) {
					// é uma transferencia
					if (pagamento.getId_contrato_remetente() == novo_contrato.getId()) {

					} else if (pagamento.getId_contrato_destinatario() == novo_contrato.getId()) {
						// é uma transferencia positiva
						// pegar o preco da unidade do contrato que recebeu a transferencia
						valorString = NumberFormat.getCurrencyInstance(ptBr).format(novo_contrato.getValor_produto());

					}
				}
				// celula valor unidade
				tableRowOne = table.getRow(i_global);
				tableRowOne.getCell(4).removeParagraph(0);
				paragraph = tableRowOne.getCell(4).addParagraph();
				criarParagrafoTabela(paragraph, valorString, false);

				// cobertura
				cobertura = pagamento.getValor_pagamento() / ct_remetente.getValor_produto();
				if (ct_remetente.getMedida().equalsIgnoreCase("KG"))
					cobertura = cobertura / 60;

				String retorno = z.format(cobertura) + " sacos";

				if (pagamento.getTipo() == 1) {
					peso_total_cobertura_efetuados += cobertura;
					somatoria_cobertura_pagamentos += cobertura;

				} else if (pagamento.getTipo() == 2) {
					peso_total_cobertura_comissao += cobertura;
					somatoria_cobertura_pagamentos += cobertura;
				} else if (pagamento.getTipo() == 3) {
					// é uma transferencia
					if (pagamento.getId_contrato_remetente() == novo_contrato.getId()) {
						retorno = "-" + retorno;
						peso_total_cobertura_transferencia_negativa += cobertura;
						somatoria_cobertura_transferencias_negativas += cobertura;
					} else if (pagamento.getId_contrato_destinatario() == novo_contrato.getId()) {
						// é uma transferencia positiva
						// pegar o preco da unidade do contrato que recebeu a transferencia

						cobertura = pagamento.getValor_pagamento() / novo_contrato.getValor_produto();

						if (novo_contrato.getMedida().equalsIgnoreCase("KG"))
							cobertura = cobertura / 60;
						peso_total_cobertura_transferencia_positiva += cobertura;
						somatoria_cobertura_transferencias_positivas += cobertura;
						retorno = z.format(cobertura) + " sacos";
						retorno = "+" + retorno;
					}

				}

				// celula cobertura
				tableRowOne = table.getRow(i_global);
				tableRowOne.getCell(5).removeParagraph(0);
				paragraph = tableRowOne.getCell(5).addParagraph();
				criarParagrafoTabela(paragraph, retorno, false);

				// celula depositante
				tableRowOne = table.getRow(i_global);
				tableRowOne.getCell(6).removeParagraph(0);
				paragraph = tableRowOne.getCell(6).addParagraph();
				criarParagrafoTabela(paragraph, pagamento.getDepositante().toUpperCase(), false);

				// celula conta depositante
				tableRowOne = table.getRow(i_global);
				tableRowOne.getCell(7).removeParagraph(0);
				paragraph = tableRowOne.getCell(7).addParagraph();
				criarParagrafoTabela(paragraph, pagamento.getConta_bancaria_depositante().toUpperCase(), false);

				// celula favorecido
				tableRowOne = table.getRow(i_global);
				tableRowOne.getCell(8).removeParagraph(0);
				paragraph = tableRowOne.getCell(8).addParagraph();
				criarParagrafoTabela(paragraph, pagamento.getFavorecido().toUpperCase(), false);

				// celula conta favorecido
				tableRowOne = table.getRow(i_global);
				tableRowOne.getCell(9).removeParagraph(0);
				paragraph = tableRowOne.getCell(9).addParagraph();
				if (pagamento.getConta_bancaria_favorecido() != null) {
					criarParagrafoTabela(paragraph, pagamento.getConta_bancaria_favorecido().toUpperCase(), false);
				} else {
					criarParagrafoTabela(paragraph, "", false);

				}

				if (pagamento.getTipo() == 1) {
					// celula contrato remetente
					tableRowOne = table.getRow(i_global);
					tableRowOne.getCell(10).removeParagraph(0);
					paragraph = tableRowOne.getCell(10).addParagraph();
					criarParagrafoTabela(paragraph, "", false);

					// celula contrato destinataio
					tableRowOne = table.getRow(i_global);
					tableRowOne.getCell(11).removeParagraph(0);
					paragraph = tableRowOne.getCell(11).addParagraph();
					criarParagrafoTabela(paragraph, "", false);
				} else {
					// celula contrato remetente
					tableRowOne = table.getRow(i_global);
					tableRowOne.getCell(10).removeParagraph(0);
					paragraph = tableRowOne.getCell(10).addParagraph();
					criarParagrafoTabela(paragraph, ct_remetente.getCodigo(), false);

					// celula contrato destinataio
					tableRowOne = table.getRow(i_global);
					tableRowOne.getCell(11).removeParagraph(0);
					paragraph = tableRowOne.getCell(11).addParagraph();
					criarParagrafoTabela(paragraph, ct_destinatario.getCodigo(), false);
				}

				i_global++;

			}

		}
		// somatorias
		tableRowOne = table.getRow(i_global);
		tableRowOne.getCell(2).removeParagraph(0);
		paragraph = tableRowOne.getCell(2).addParagraph();
		criarParagrafoTabela(paragraph, "TOTAL CONCLUÍDO:", false);

		tableRowOne = table.getRow(i_global);
		tableRowOne.getCell(3).removeParagraph(0);
		paragraph = tableRowOne.getCell(3).addParagraph();
		double valor_total_pagamentos_concluidos = valor_total_pagamentos_efetuados
				- valor_total_transferencias_retiradas + valor_total_transferencias_recebidas;
		String valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos_concluidos);

		criarParagrafoTabela(paragraph, valor, false);

		// somatorias
		tableRowOne = table.getRow(i_global);
		tableRowOne.getCell(4).removeParagraph(0);
		paragraph = tableRowOne.getCell(4).addParagraph();
		criarParagrafoTabela(paragraph, "COBERTURA TOTAL:", false);

		double peso_total_cobertura_concluida = peso_total_cobertura_efetuados
				- peso_total_cobertura_transferencia_negativa + peso_total_cobertura_transferencia_positiva;
		tableRowOne = table.getRow(i_global);
		tableRowOne.getCell(5).removeParagraph(0);
		paragraph = tableRowOne.getCell(5).addParagraph();
		criarParagrafoTabela(paragraph, z.format(peso_total_cobertura_concluida) + " sacos", false);

		i_global++;

		// somatorias
		tableRowOne = table.getRow(i_global);
		tableRowOne.getCell(2).removeParagraph(0);
		paragraph = tableRowOne.getCell(2).addParagraph();
		criarParagrafoTabela(paragraph, "VALOR RESTANTE:", false);

		valor_total_pagamentos_restantes = valor_total_pagamentos - valor_total_pagamentos_efetuados
				+ valor_total_transferencias_retiradas - valor_total_transferencias_recebidas;
		valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos_restantes);
		tableRowOne = table.getRow(i_global);
		tableRowOne.getCell(3).removeParagraph(0);
		paragraph = tableRowOne.getCell(3).addParagraph();
		criarParagrafoTabela(paragraph, valor, false);

		tableRowOne = table.getRow(i_global);
		tableRowOne.getCell(4).removeParagraph(0);
		paragraph = tableRowOne.getCell(4).addParagraph();
		criarParagrafoTabela(paragraph, "COBERTURA RESTANTE:", false);

		peso_total_cobertura_restante = peso_total_cobertura - peso_total_cobertura_efetuados
				+ peso_total_cobertura_transferencia_negativa - peso_total_cobertura_transferencia_positiva;
		tableRowOne = table.getRow(i_global);
		tableRowOne.getCell(5).removeParagraph(0);
		paragraph = tableRowOne.getCell(5).addParagraph();
		criarParagrafoTabela(paragraph, z.format(peso_total_cobertura_restante) + " sacos", false);

		if (incluir_comissao_pagamento) {
			XWPFParagraph paragrafo = document_global.createParagraph();
			XWPFRun run = paragrafo.createRun();
			run.setText("*Valor de comissão não é somado ao valor total");
			run.setColor("ff0000");
			run.setFontFamily("Times New Roman");
			run.setFontSize(8);

		}

		// adicionar valores

		// adicionais
		/******************* inicio adicionais ***********************/

		String texto_total = "\nTotal a Pagar(de acordo com a quantidade recebida): ";
		valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos);
		texto_total += valor;
		texto_total += " Cobre: " + z.format(peso_total_cobertura * 60) + " kgs | ";
		texto_total += z.format(peso_total_cobertura) + " sacos";
		// total_cobertura += peso_total_cobertura;

		String texto_efetuados = "Efetuados: ";
		valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos_efetuados);
		somatoria_total_pagamentos += valor_total_pagamentos_efetuados;
		texto_efetuados += valor;
		texto_efetuados += " Cobre: " + z.format(peso_total_cobertura_efetuados * 60) + " kgs | ";
		texto_efetuados += z.format(peso_total_cobertura_efetuados) + " sacos";

		// status
		String texto_transferencias_negativas = "";
		if (incluir_transferencias_pagamentos) {
			// transferencias negativas
			texto_transferencias_negativas = "Transferencias:(-) ";
			valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_transferencias_retiradas);
			texto_transferencias_negativas += valor;
			texto_transferencias_negativas += " Cobre: " + z.format(peso_total_cobertura_transferencia_negativa * 60)
					+ " kgs | ";
			texto_transferencias_negativas += z.format(peso_total_cobertura_transferencia_negativa) + " sacos";
			somatoria_total_transferencias_negativas += valor_total_transferencias_retiradas;
		}
		// status
		double diferenca = (valor_total_pagamentos_efetuados - valor_total_transferencias_retiradas
				+ valor_total_transferencias_recebidas) - valor_total_pagamentos;
		String status_pagamento = "[Pagamentos:] ";
		if (diferenca == 0) {
			status_pagamento += "[Pagamento Concluído]";
		} else if (diferenca > 0) {
			status_pagamento += "[Excedeu] [em] [" + NumberFormat.getCurrencyInstance(ptBr).format(diferenca) + "]";

		} else if (diferenca < 0) {
			status_pagamento += "[Incompleto], [falta] [" + NumberFormat.getCurrencyInstance(ptBr).format(diferenca)
					+ "]";

		}

		String texto_transferencias_positivas = "";
		if (incluir_transferencias_pagamentos) {
			// transferencias positivas
			texto_transferencias_positivas = "Transferencias:(+) ";
			valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_transferencias_recebidas);
			texto_transferencias_positivas += valor;
			texto_transferencias_positivas += " Cobre: " + z.format(peso_total_cobertura_transferencia_positiva * 60)
					+ " kgs | ";
			texto_transferencias_positivas += z.format(peso_total_cobertura_transferencia_positiva) + " sacos";
			somatoria_total_transferencias_positivas += valor_total_transferencias_recebidas;

		}
		String status_cobertura = "[Cobertura:] ";
		double diferenca_pesos = peso_total_cobertura_restante;

		if (diferenca_pesos == 0 || diferenca_pesos == -0) {
			status_cobertura += "[Todos] [os] [sacos] [foram] [pagos]";
		} else if (diferenca_pesos < 0) {
			status_cobertura += "[Excedeu] [em] [" + z.format(diferenca_pesos * 60) + " kgs | "
					+ z.format(diferenca_pesos) + "] [Sacos]";

		} else if (diferenca_pesos > 0) {
			status_cobertura += "[Incompleto], [falta] [pagar] [" + z.format(diferenca_pesos) + "] [Sacos]";

		}

		// comissão
		String texto_comissao = "";
		if (incluir_comissao_pagamento) {
			texto_comissao = "Comissão: ";
			valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_comissao);
			texto_comissao += valor;
			texto_comissao += " Cobre: " + z.format(peso_total_cobertura_comissao * 60) + " kgs | ";
			texto_comissao += z.format(peso_total_cobertura_comissao) + " sacos";

		}

		// concluidos

		String texto_concluida = "Concluída:";

		valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos_concluidos);
		peso_total_cobertura_concluida = peso_total_cobertura_efetuados - peso_total_cobertura_transferencia_negativa
				+ peso_total_cobertura_transferencia_positiva;
		texto_concluida += valor;
		texto_concluida += " Cobre: " + z.format(peso_total_cobertura_concluida * 60) + " kgs | ";

		texto_concluida += z.format(peso_total_cobertura_concluida) + " sacos";

		// restante

		String texto_restante = " Restante:";

		valor_total_pagamentos_restantes = valor_total_pagamentos - valor_total_pagamentos_efetuados
				+ valor_total_transferencias_retiradas - valor_total_transferencias_recebidas;
		valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos_restantes);
		peso_total_cobertura_restante = peso_total_cobertura - peso_total_cobertura_efetuados
				+ peso_total_cobertura_transferencia_negativa - peso_total_cobertura_transferencia_positiva;
		texto_restante += valor;
		texto_restante += " Cobre: " + z.format(peso_total_cobertura_restante * 60) + " kgs | ";

		texto_restante += z.format(peso_total_cobertura_restante) + " sacos";

		String texto_final = texto_total + "\n";

		if (incluir_transferencias_pagamentos) {
			texto_final = texto_final + texto_transferencias_negativas + "\n" + texto_transferencias_positivas + "\n";
		}
		if (incluir_comissao_pagamento) {
			texto_final = texto_final + texto_comissao + "\n";
		}

		texto_final = texto_final + texto_concluida + "\n" + texto_restante;
		texto_final = texto_final + "\n\n [Status] [do] [Pagamento]:\n";
		texto_final = texto_final + status_pagamento + "\n" + status_cobertura;

		substituirTexto(texto_final);

		/************* fim adicionai *************************/

	}

	public void setTableAlign(XWPFTable table, ParagraphAlignment align) {
		CTTblPr tblPr = table.getCTTbl().getTblPr();
		CTJc jc = (tblPr.isSetJc() ? tblPr.getJc() : tblPr.addNewJc());
		STJc.Enum en = STJc.Enum.forInt(align.getValue());
		jc.setVal(en);
	}

	public void adicionarTextoParagrafoAtual(String texto, boolean negrito) {
		XWPFRun run = paragrafo_atual.createRun();
		run.setText(texto);
		run.setColor("000000");
		run.setBold(negrito);
		run.setFontFamily("Times New Roman");
		run.setFontSize(10);

	}

	public void saltarLinhaParagrafo() {
		XWPFRun corretortitleRun = paragrafo_atual.createRun();
		corretortitleRun.addBreak();

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

	public void substituirTexto(int alinhamento, String text_amostra) {

		// criarParagrafo(2);

		// pegar os paragrafos
		String separador_paragrafo[] = text_amostra.split("\n");
		for (String paragrafo : separador_paragrafo) {
			criarParagrafo(alinhamento);

			paragrafo = paragrafo.replaceAll(" ", "&");

			String separador_palabras[] = paragrafo.split("&");
			for (String palavra : separador_palabras) {
				if (palavra.contains("[") || palavra.contains("]")) {
					adicionarTextoParagrafoAtual(palavra.replaceAll("[\\[\\]]", "") + " ", true);

				} else {

					adicionarTextoParagrafoAtual(palavra + " ", false);

				}

			}
		}

	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Entity
	class GanhoPotencial {

		int flag_soma;
		String codigo;
		BigDecimal total_contrato_original, total_sub_contratos, total_comissao, diferenca, ganhos_potenciais;

		// Codigo TOTAL CONTRATO
		// TOTAL SUBCONTRATO TOTAL COMISSAO DIferenca GAnhos Potencias

	}

	public void criarTabelaGanhosPotenciais(ArrayList<GanhoPotencial> lista_ganhos_potenciais) {

		NumberFormat z = NumberFormat.getNumberInstance();

		Locale ptBr = new Locale("pt", "BR");

		// criarParagrafo(1);
		// linhas x colunas

		int num_linhas_recebimentos = lista_ganhos_potenciais.size() + 1 + 1;

		XWPFTable table = document_global.createTable(num_linhas_recebimentos, 6);

		setTableAlign(table, ParagraphAlignment.CENTER);
		XWPFTableRow tableRowOne = table.getRow(0);
		tableRowOne.getCell(0).removeParagraph(0);

		XWPFParagraph paragraph = tableRowOne.getCell(0).addParagraph();

		// variaveis de somatorias
		BigDecimal somatoria_total_contratos_originais = BigDecimal.ZERO;
		BigDecimal somatoria_total_sub_contratos_originais = BigDecimal.ZERO;
		BigDecimal somatoria_total_comissao = BigDecimal.ZERO;
		BigDecimal somatoria_total_diferenca = BigDecimal.ZERO;
		BigDecimal somatoria_total_ganho_potencial = BigDecimal.ZERO;

		//
		int cabecalho = 0;

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(0).removeParagraph(0);
		paragraph = tableRowOne.getCell(0).addParagraph();
		criarParagrafoTabela(paragraph, "CONTRATO", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(1).removeParagraph(0);
		paragraph = tableRowOne.getCell(1).addParagraph();
		criarParagrafoTabela(paragraph, "TOTAL CONTRATO", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(2).removeParagraph(0);
		paragraph = tableRowOne.getCell(2).addParagraph();
		criarParagrafoTabela(paragraph, "TOTAL SUB-CONTRATOS", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(3).removeParagraph(0);
		paragraph = tableRowOne.getCell(3).addParagraph();
		criarParagrafoTabela(paragraph, "TOTAL COMISSÃO", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(4).removeParagraph(0);
		paragraph = tableRowOne.getCell(4).addParagraph();

		criarParagrafoTabela(paragraph, "DIFERENÇA", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(5).removeParagraph(0);
		paragraph = tableRowOne.getCell(5).addParagraph();
		criarParagrafoTabela(paragraph, "GANHO POTENCIAL", true);

		int i = cabecalho + 1;

		for (GanhoPotencial ganho : lista_ganhos_potenciais) {

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(0).removeParagraph(0);
			paragraph = tableRowOne.getCell(0).addParagraph();
			criarParagrafoTabela(paragraph, ganho.getCodigo(), false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(1).removeParagraph(0);
			paragraph = tableRowOne.getCell(1).addParagraph();
			String s_valor_total_contrato = NumberFormat.getCurrencyInstance(ptBr)
					.format(ganho.getTotal_contrato_original());
			criarParagrafoTabela(paragraph, s_valor_total_contrato, false);
			somatoria_total_contratos_originais = somatoria_total_contratos_originais
					.add(ganho.getTotal_contrato_original());

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(2).removeParagraph(0);
			paragraph = tableRowOne.getCell(2).addParagraph();
			String s_valor_total_sub_contratos = NumberFormat.getCurrencyInstance(ptBr)
					.format(ganho.getTotal_sub_contratos());

			criarParagrafoTabela(paragraph, s_valor_total_sub_contratos, false);
			somatoria_total_sub_contratos_originais = somatoria_total_sub_contratos_originais
					.add(ganho.getTotal_sub_contratos());

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(3).removeParagraph(0);
			paragraph = tableRowOne.getCell(3).addParagraph();
			String s_total_comissao = NumberFormat.getCurrencyInstance(ptBr).format(ganho.getTotal_comissao());

			criarParagrafoTabela(paragraph, s_total_comissao, false);
			somatoria_total_comissao = somatoria_total_comissao.add(ganho.getTotal_comissao());

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(4).removeParagraph(0);
			paragraph = tableRowOne.getCell(4).addParagraph();
			String diferenca = NumberFormat.getCurrencyInstance(ptBr).format(ganho.getDiferenca());

			criarParagrafoTabela(paragraph, diferenca, false);
			somatoria_total_diferenca = somatoria_total_diferenca.add(ganho.getDiferenca());

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(5).removeParagraph(0);
			paragraph = tableRowOne.getCell(5).addParagraph();
			// BigDecimal ganho_potencial =
			// ganho.getDiferenca().add(ganho.getTotal_comissao());

			if (ganho.getFlag_soma() == 8) {
				// ganho real via diferenca de subcontratos
				String s_ganho_potencial = NumberFormat.getCurrencyInstance(ptBr).format(ganho.getGanhos_potenciais());
				criarParagrafoTabela(paragraph, s_ganho_potencial, false);
				somatoria_total_ganho_potencial = somatoria_total_ganho_potencial.add(ganho.getGanhos_potenciais());
			} else {
				// ganho real via comissao
				String s_ganho_potencial = NumberFormat.getCurrencyInstance(ptBr).format(ganho.getTotal_comissao());

				criarParagrafoTabela(paragraph, s_ganho_potencial, false);
			}

			i++;
		}

		// criar somatorias
		// somatoria dos pesos
		tableRowOne = table.getRow(i);
		tableRowOne.getCell(0).removeParagraph(0);
		paragraph = tableRowOne.getCell(0).addParagraph();
		criarParagrafoTabela(paragraph, "Total:", true, "000000", 1);

		// somatoria dos pesos
		tableRowOne = table.getRow(i);
		tableRowOne.getCell(1).removeParagraph(0);
		paragraph = tableRowOne.getCell(1).addParagraph();
		String s_somatoria_total_contratos_originais = NumberFormat.getCurrencyInstance(ptBr)
				.format(somatoria_total_contratos_originais);
		criarParagrafoTabela(paragraph, s_somatoria_total_contratos_originais, true);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(2).removeParagraph(0);
		paragraph = tableRowOne.getCell(2).addParagraph();
		String s_somatoria_total_sub_contratos_originais = NumberFormat.getCurrencyInstance(ptBr)
				.format(somatoria_total_sub_contratos_originais);
		criarParagrafoTabela(paragraph, s_somatoria_total_sub_contratos_originais, true);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(3).removeParagraph(0);
		paragraph = tableRowOne.getCell(3).addParagraph();
		String s_somatoria_total_comissao = NumberFormat.getCurrencyInstance(ptBr).format(somatoria_total_comissao);
		criarParagrafoTabela(paragraph, s_somatoria_total_comissao, true);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(4).removeParagraph(0);
		paragraph = tableRowOne.getCell(4).addParagraph();
		String s_somatoria_total_diferenca = NumberFormat.getCurrencyInstance(ptBr).format(somatoria_total_diferenca);
		criarParagrafoTabela(paragraph, s_somatoria_total_diferenca, true);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(5).removeParagraph(0);
		paragraph = tableRowOne.getCell(5).addParagraph();
		String s_somatoria_total_ganho_potencial = NumberFormat.getCurrencyInstance(ptBr)
				.format(somatoria_total_ganho_potencial);
		criarParagrafoTabela(paragraph, s_somatoria_total_ganho_potencial, true);

		substituirTexto("");

	}

	public void criarTabelaInfoGrupo() {

		NumberFormat z = NumberFormat.getNumberInstance();

		Locale ptBr = new Locale("pt", "BR");
		double quantidade_global_sacos = 0.0;
		double quantidade_global_recebidos_sacos = 0.0;
		BigDecimal valor_global_pagamentos = BigDecimal.ZERO;

		BigDecimal valor_global_recebidos_pagamentos = BigDecimal.ZERO;

		int numero_global_contratos = 0;
		ArrayList<RegistroLocal> clientes = new ArrayList<>();

		// criarParagrafo(1);
		// linhas x colunas
		int num_linhas_carregamentos = grupo_alvo_global.getClientes().size() + 1 + 1;

		XWPFTable table = document_global.createTable(num_linhas_carregamentos, 6);

		setTableAlign(table, ParagraphAlignment.CENTER);
		XWPFTableRow tableRowOne = table.getRow(0);
		tableRowOne.getCell(0).removeParagraph(0);

		XWPFParagraph paragraph = tableRowOne.getCell(0).addParagraph();

		criarParagrafoTabela(paragraph, "INTEGRANTE", true);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(1).removeParagraph(0);
		paragraph = tableRowOne.getCell(1).addParagraph();
		criarParagrafoTabela(paragraph, "I.E", true);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(2).removeParagraph(0);
		paragraph = tableRowOne.getCell(2).addParagraph();
		criarParagrafoTabela(paragraph, "TOTAL DE CONTRATOS", true);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(3).removeParagraph(0);
		paragraph = tableRowOne.getCell(3).addParagraph();
		criarParagrafoTabela(paragraph, "VOLUME TOTAL", true);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(4).removeParagraph(0);
		paragraph = tableRowOne.getCell(4).addParagraph();
		criarParagrafoTabela(paragraph, "% VOLUME SOBRE O TOTAL", true);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(5).removeParagraph(0);
		paragraph = tableRowOne.getCell(5).addParagraph();
		criarParagrafoTabela(paragraph, "VALOR TOTAL", true);

		GerenciarBancoContratos procura_contratos_grupo = new GerenciarBancoContratos();
		ArrayList<CadastroContrato> lista_global = new ArrayList<>();

		for (CadastroCliente cliente : grupo_alvo_global.getClientes()) {

			String nome_cliente = "";
			if (cliente.getTipo_pessoa() == 0) {
				nome_cliente = cliente.getNome_empresarial().toUpperCase();
			} else {
				nome_cliente = cliente.getNome_fantaia().toUpperCase();
			}

			String ie = "";
			ie = cliente.getIe();
			// numero de contratos desde clinete
			ArrayList<CadastroContrato> lista_contratos_encontrados_do_cliente = new ArrayList<>();
			ArrayList<CadastroContrato> lista_local_do_cliente = new ArrayList<>();
			ArrayList<CadastroContrato> lista_sub_contratos_encontrados_do_cliente = new ArrayList<>();

			if (contrato_como_comprador) {

				lista_contratos_encontrados_do_cliente = procura_contratos_grupo.getContratosPorClienteParaRelatorio(
						id_safra, cliente.getId(), cliente_alvo2_global.getId(), contra_parte_global.getId(),
						participacao_global, id_local_retirada_global);

				if (tipo_contrato != 1) {
					lista_sub_contratos_encontrados_do_cliente = procura_contratos_grupo
							.getSubContratosPorClienteParaRelatorio(id_safra, cliente.getId(),
									cliente_alvo2_global.getId(), contra_parte_global.getId(), participacao_global,
									id_local_retirada_global);

				}

			} else {

				lista_contratos_encontrados_do_cliente = procura_contratos_grupo.getContratosPorClienteParaRelatorio(
						id_safra, contra_parte_global.getId(), cliente_alvo2_global.getId(), cliente.getId(),
						participacao_global, id_local_retirada_global);
				if (tipo_contrato != 1) {
					lista_sub_contratos_encontrados_do_cliente = procura_contratos_grupo
							.getSubContratosPorClienteParaRelatorio(id_safra, contra_parte_global.getId(),
									cliente_alvo2_global.getId(), cliente.getId(), participacao_global,
									id_local_retirada_global);

				}

			}

			if (lista_contratos_encontrados_do_cliente.size() > 0) {
				for (CadastroContrato contrato_buscado : lista_contratos_encontrados_do_cliente) {

					// verifica se o contrato ja esta na lista global
					boolean ja_incluso = false;
					for (CadastroContrato contratos_na_lista_lista_global : lista_global) {

						if (contratos_na_lista_lista_global.getCodigo().equals(contrato_buscado.getCodigo())) {
							ja_incluso = true;
							break;
						}
					}

					// se nao esta na lista global, adiciona na lista global
					if (!ja_incluso) {
						lista_local_do_cliente.add(contrato_buscado);
					}
				}

			}

			if (lista_sub_contratos_encontrados_do_cliente.size() > 0) {
				for (CadastroContrato contrato_buscado : lista_sub_contratos_encontrados_do_cliente) {

					// verifica se o contrato ja esta na lista global
					boolean ja_incluso = false;
					for (CadastroContrato contratos_na_lista_lista_global : lista_global) {

						if (contratos_na_lista_lista_global.getCodigo().equals(contrato_buscado.getCodigo())) {
							ja_incluso = true;
							break;
						}
					}

					// se nao esta na lista global, adiciona na lista global
					if (!ja_incluso) {
						lista_local_do_cliente.add(contrato_buscado);
					}
				}

			}

			ArrayList<CadastroContrato> lista_final_do_cliente = new ArrayList<>();

			for (CadastroContrato contrato_lista_local : lista_local_do_cliente) {
				if (tipo_contrato == 1) {

					boolean ja_incluso = false;
					for (CadastroContrato contratos_na_lista_lista_final : lista_final_do_cliente) {

						if (contratos_na_lista_lista_final.getCodigo().equals(contrato_lista_local.getCodigo())) {
							ja_incluso = true;
							break;
						}
					}

					// se nao esta na lista global, adiciona na lista global
					if (!ja_incluso) {
						lista_final_do_cliente.add(contrato_lista_local);

					}

				} else {
					// relatorio externo ao comprador
					this.incluir_comissao = false;
					this.somar_sub_contratos = false;

					if (lista_local_do_cliente.size() > 0) {
						// verifica pelos subcontratos de cada contrato retornado

						ArrayList<CadastroContrato> sub_contratos = procura_contratos_grupo
								.getSubContratosParaRelatorio(contrato_lista_local.getId());
						telaEmEsperaRelatoria.setInfo("Lista de sub-contratos do alvo como comprador criada", 30);

						if (sub_contratos.size() > 0) {

							for (CadastroContrato sub : sub_contratos) {

								boolean ja_incluso = false;
								for (CadastroContrato contratos_na_lista_lista_final : lista_final_do_cliente) {

									if (contratos_na_lista_lista_final.getCodigo().equals(sub.getCodigo())) {
										ja_incluso = true;
										break;
									}
								}

								if (!ja_incluso) {

									boolean tem_id = false;

									for (CadastroCliente cliente_pesquisando : clientes_globais) {
										String s_id = Integer.toString(cliente_pesquisando.getId());
										if (sub.getIds_clientes_compradores().contains(s_id)) {
											tem_id = true;
											break;

										}

									}

									if (sub.getFilho() == 1) {
										if (tem_id) {
											lista_final_do_cliente.add(sub);

										}
									}

								}

							}

						} else {

							boolean ja_incluso = false;
							for (CadastroContrato contratos_na_lista_lista_final : lista_final_do_cliente) {

								if (contratos_na_lista_lista_final.getCodigo()
										.equals(contrato_lista_local.getCodigo())) {
									ja_incluso = true;
									break;
								}
							}

							if (!ja_incluso) {
								lista_final_do_cliente.add(contrato_lista_local);

							}

						}

					}

				}

			}

			ArrayList<CadastroContrato> lista_final_filtrada = new ArrayList<>();
			if (lista_final_do_cliente.size() > 0) {
				for (CadastroContrato contrato_buscado : lista_final_do_cliente) {

					// verifica se o contrato ja esta na lista global
					boolean ja_incluso = false;
					for (CadastroContrato contratos_na_lista_lista_global : lista_global) {

						if (contratos_na_lista_lista_global.getCodigo().equals(contrato_buscado.getCodigo())) {
							ja_incluso = true;
							break;
						}
					}

					// se nao esta na lista global, adiciona na lista global
					if (!ja_incluso) {
						lista_global.add(contrato_buscado);
						lista_final_filtrada.add(contrato_buscado);
					} else {
					}
				}

			}

			RegistroLocal reg = new RegistroLocal();
			// quantidade total de sacas
			int numero_total_contratos_do_cliente = 0;
			BigDecimal valor_total_pagamentos_do_cliente = BigDecimal.ZERO;
			BigDecimal valor_total_pagamentos_recebidos_do_cliente = BigDecimal.ZERO;

			double quantidade_total_sacos_do_cliente = 0.0;
			double quantidade_total_sacos_recebidos_do_cliente = 0.0;
			for (CadastroContrato contrato : lista_final_filtrada) {
				numero_total_contratos_do_cliente++;

				if (contrato.getMedida().equalsIgnoreCase("KG")) {
					quantidade_total_sacos_do_cliente += (contrato.getQuantidade() / 60);
				} else if (contrato.getMedida().equalsIgnoreCase("Sacos")) {
					quantidade_total_sacos_do_cliente += contrato.getQuantidade();
				}

				valor_total_pagamentos_do_cliente = valor_total_pagamentos_do_cliente.add(contrato.getValor_a_pagar());

				double valor_total_pagamentos = 0.0;
				double peso_total_cobertura = getPesoTotalRecebido(contrato) / 60;

				if (contrato.getMedida().equalsIgnoreCase("KG")) {
					valor_total_pagamentos = (peso_total_cobertura * 60) * contrato.getValor_produto();

				} else if (contrato.getMedida().equalsIgnoreCase("Sacos")) {
					valor_total_pagamentos = peso_total_cobertura * contrato.getValor_produto();
				}
				quantidade_total_sacos_recebidos_do_cliente += peso_total_cobertura;

				valor_total_pagamentos_recebidos_do_cliente = valor_total_pagamentos_recebidos_do_cliente
						.add(new BigDecimal(valor_total_pagamentos));

			}

			reg.setNome(nome_cliente);
			reg.setIe(ie);
			reg.setNum_contratos(numero_total_contratos_do_cliente);
			reg.setQuantidade_total(quantidade_total_sacos_do_cliente);
			reg.setValor_total(valor_total_pagamentos_do_cliente);

			numero_global_contratos += numero_total_contratos_do_cliente;

			quantidade_global_sacos += quantidade_total_sacos_do_cliente;
			quantidade_global_recebidos_sacos += quantidade_total_sacos_recebidos_do_cliente;

			valor_global_pagamentos = valor_global_pagamentos.add(valor_total_pagamentos_do_cliente);
			valor_global_recebidos_pagamentos = valor_global_recebidos_pagamentos
					.add(valor_total_pagamentos_recebidos_do_cliente);

			clientes.add(reg);
		}

		int i = 1;

		for (RegistroLocal reg : clientes) {
			// preenchar dados
			tableRowOne = table.getRow(i);
			tableRowOne.getCell(0).removeParagraph(0);
			paragraph = tableRowOne.getCell(0).addParagraph();
			criarParagrafoTabela(paragraph, reg.getNome(), false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(1).removeParagraph(0);
			paragraph = tableRowOne.getCell(1).addParagraph();
			criarParagrafoTabela(paragraph, reg.getIe(), false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(2).removeParagraph(0);
			paragraph = tableRowOne.getCell(2).addParagraph();
			criarParagrafoTabela(paragraph, reg.getNum_contratos() + "", false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(3).removeParagraph(0);
			paragraph = tableRowOne.getCell(3).addParagraph();
			criarParagrafoTabela(paragraph, z.format(reg.getQuantidade_total()) + " sacos", false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(4).removeParagraph(0);
			paragraph = tableRowOne.getCell(4).addParagraph();
			criarParagrafoTabela(paragraph, ((int) (100 * reg.getQuantidade_total() / quantidade_global_sacos)) + " %",
					false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(5).removeParagraph(0);
			paragraph = tableRowOne.getCell(5).addParagraph();
			String valorString = NumberFormat.getCurrencyInstance(ptBr).format(reg.getValor_total());
			criarParagrafoTabela(paragraph, valorString, false);

			i++;
		}

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(2).removeParagraph(0);
		paragraph = tableRowOne.getCell(2).addParagraph();
		criarParagrafoTabela(paragraph, z.format(numero_global_contratos), false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(3).removeParagraph(0);
		paragraph = tableRowOne.getCell(3).addParagraph();
		criarParagrafoTabela(paragraph, z.format(quantidade_global_sacos) + " sacos", false);
		total_cobertura = quantidade_global_sacos;
		total_recebidos_cobertura = quantidade_global_recebidos_sacos;

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(4).removeParagraph(0);
		paragraph = tableRowOne.getCell(4).addParagraph();
		criarParagrafoTabela(paragraph, "100%", false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(5).removeParagraph(0);
		paragraph = tableRowOne.getCell(5).addParagraph();
		String valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor_global_pagamentos);
		total_pagamentos = valor_global_pagamentos.doubleValue();

		total_recebidos_pagamentos = valor_global_recebidos_pagamentos.doubleValue();

		criarParagrafoTabela(paragraph, valorString, false);

	}

	class RegistroLocal {
		BigDecimal valor_total;
		double quantidade_total;
		String nome;
		String ie;
		int num_contratos;

		public String getIe() {
			return ie;
		}

		public void setIe(String ie) {
			this.ie = ie;
		}

		public int getNum_contratos() {
			return num_contratos;
		}

		public void setNum_contratos(int num_contratos) {
			this.num_contratos = num_contratos;
		}

		public BigDecimal getValor_total() {
			return valor_total;
		}

		public void setValor_total(BigDecimal valor_total) {
			this.valor_total = valor_total;
		}

		public double getQuantidade_total() {
			return quantidade_total;
		}

		public void setQuantidade_total(double quantidade_total) {
			this.quantidade_total = quantidade_total;
		}

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

	}

	public void criarTabelaInformacoes(ArrayList<RegistroQuantidade> quantidades_totais,
			ArrayList<RegistroRecebimento> quantidades_recebidas) {

		NumberFormat z = NumberFormat.getNumberInstance();

		Locale ptBr = new Locale("pt", "BR");

		// criarParagrafo(1);
		// linhas x colunas
		int num_linhas_registros = quantidades_totais.size() + 4;

		XWPFTable table = document_global.createTable(num_linhas_registros, 6);

		setTableAlign(table, ParagraphAlignment.CENTER);
		XWPFTableRow tableRowOne = table.getRow(0);
		tableRowOne.getCell(0).removeParagraph(0);

		XWPFParagraph paragraph = tableRowOne.getCell(0).addParagraph();

		criarParagrafoTabela(paragraph, "COMPRADOR", true);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(1).removeParagraph(0);
		paragraph = tableRowOne.getCell(1).addParagraph();
		criarParagrafoTabela(paragraph, "VENDEDOR", true);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(2).removeParagraph(0);
		paragraph = tableRowOne.getCell(2).addParagraph();
		criarParagrafoTabela(paragraph, "TOTAL CONTRATADO", true);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(3).removeParagraph(0);
		paragraph = tableRowOne.getCell(3).addParagraph();
		criarParagrafoTabela(paragraph, "TOTAL RECEBIDO", true);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(4).removeParagraph(0);
		paragraph = tableRowOne.getCell(4).addParagraph();
		criarParagrafoTabela(paragraph, "FALTA", true);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(5).removeParagraph(0);
		paragraph = tableRowOne.getCell(5).addParagraph();
		criarParagrafoTabela(paragraph, "SITUAÇÃO", true);

		int i = 1;

		double somatoria_quantidade_total = 0;
		double somatoria_quantidade_recebida = 0;
		double somatoria_quantidade_restante = 0;

		int quantidade_clientes_entregando = 0;
		int quantidade_clientes_pendente = 0;
		int quantidade_clientes_finalizado = 0;

		for (int J = 0; J < quantidades_totais.size(); J++) {

			String comprador = quantidades_totais.get(J).getComprador();
			String vendedor = quantidades_totais.get(J).getVendedor();
			double quantidade_total = quantidades_totais.get(J).getTotal();
			double quantidade_recebida = quantidades_recebidas.get(J).getQuantidade_recebida();
			double restante = quantidades_totais.get(J).getTotal()
					- quantidades_recebidas.get(J).getQuantidade_recebida();

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(0).removeParagraph(0);
			paragraph = tableRowOne.getCell(0).addParagraph();
			criarParagrafoTabela(paragraph, comprador, false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(1).removeParagraph(0);
			paragraph = tableRowOne.getCell(1).addParagraph();
			criarParagrafoTabela(paragraph, vendedor, false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(2).removeParagraph(0);
			paragraph = tableRowOne.getCell(2).addParagraph();
			criarParagrafoTabela(paragraph, z.format(quantidade_total) + " sacos", false);
			somatoria_quantidade_total += quantidade_total;
			tableRowOne = table.getRow(i);
			tableRowOne.getCell(3).removeParagraph(0);
			paragraph = tableRowOne.getCell(3).addParagraph();
			criarParagrafoTabela(paragraph, z.format(quantidade_recebida) + " sacos", false);
			somatoria_quantidade_recebida += quantidade_recebida;

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(4).removeParagraph(0);
			paragraph = tableRowOne.getCell(4).addParagraph();
			criarParagrafoTabela(paragraph, z.format(restante) + " sacos", false);
			somatoria_quantidade_restante += restante;

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(5).removeParagraph(0);
			paragraph = tableRowOne.getCell(5).addParagraph();
			if (restante == 0 || restante == -0 || ((int) restante) == 0
					|| ((int) quantidade_recebida) >= ((int) quantidade_total)) {
				criarParagrafoTabela(paragraph, "FINALIZADO", false);
				tableRowOne.getCell(5).getCTTc().addNewTcPr().addNewShd().setFill("2F4F4F");

				quantidade_clientes_finalizado++;
			} else if (quantidade_recebida == 0) {
				criarParagrafoTabela(paragraph, "PENDENTE", false);
				tableRowOne.getCell(5).getCTTc().addNewTcPr().addNewShd().setFill("A0522D");
				quantidade_clientes_pendente++;
			} else if (quantidade_recebida > 0 && quantidade_recebida < quantidade_total) {
				criarParagrafoTabela(paragraph, "ENTREGANDO", false);
				tableRowOne.getCell(5).getCTTc().addNewTcPr().addNewShd().setFill("ADFF2F");

				quantidade_clientes_entregando++;
			}

			i++;

		}
		i++;
		// somatoria da quantidade total

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(1).removeParagraph(0);
		paragraph = tableRowOne.getCell(1).addParagraph();
		criarParagrafoTabela(paragraph, "Somatório", false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(2).removeParagraph(0);
		paragraph = tableRowOne.getCell(2).addParagraph();
		criarParagrafoTabela(paragraph, z.format(somatoria_quantidade_total) + " sacos", true);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(3).removeParagraph(0);
		paragraph = tableRowOne.getCell(3).addParagraph();
		criarParagrafoTabela(paragraph, z.format(somatoria_quantidade_recebida) + " sacos", true);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(4).removeParagraph(0);
		paragraph = tableRowOne.getCell(4).addParagraph();
		criarParagrafoTabela(paragraph, z.format(somatoria_quantidade_restante) + " sacos", true);

		String texto = "Clientes Entregando: " + quantidade_clientes_entregando + "\n" + "Clientes Pendente: "
				+ quantidade_clientes_pendente + "\n" + "Clientes Finalizado: " + quantidade_clientes_finalizado + "\n";

		substituirTexto(texto, -1);

	}

	public void setTelaEmEsperaRelatoria(TelaEmEsperaRelatoria _tela) {
		this.telaEmEsperaRelatoria = _tela;
	}

	public void incluirSomatoriaTotalPagamentos() {
		Locale ptBr = new Locale("pt", "BR");

		NumberFormat z = NumberFormat.getNumberInstance();

		String texto_efetuados = "[Valor] [Total] [dos] [Pagamentos] [Efetuados]: [";
		String valor = NumberFormat.getCurrencyInstance(ptBr).format(somatoria_total_pagamentos);
		texto_efetuados += valor + "] | ";

		texto_efetuados += "[Cobertura] [dos] [Pagamentos:] [" + z.format(somatoria_cobertura_pagamentos * 60)
				+ " kgs | " + z.format(somatoria_cobertura_pagamentos) + " sacos ";

		String texto_transferencias_negativas = "[Valor] [Total] [de] [Transferencias:(-)] [";
		valor = NumberFormat.getCurrencyInstance(ptBr).format(somatoria_total_transferencias_negativas);
		texto_transferencias_negativas += valor + "] | ";

		texto_transferencias_negativas += "[Cobertura] [das] [Transferencias:(-):] ["
				+ z.format(somatoria_cobertura_transferencias_negativas * 60) + " kgs | "
				+ z.format(somatoria_cobertura_transferencias_negativas) + " sacos ";

		String texto_transferencias_positivas = "";

		texto_transferencias_positivas = "[Valor] [Total] [de] [Transferencias:(+)] [";
		valor = NumberFormat.getCurrencyInstance(ptBr).format(somatoria_total_transferencias_positivas);
		texto_transferencias_positivas += valor + "] | ";

		texto_transferencias_positivas += "[Cobertura] [das] [Transferencias:(+):] ["
				+ z.format(somatoria_cobertura_transferencias_positivas * 60) + " kgs | "
				+ z.format(somatoria_cobertura_transferencias_positivas) + " sacos ";

		String texto_total_a_ser_pago = "[Valor] [Total] [a] [pagar](de acordo com a quantidade contratada): [";
		valor = NumberFormat.getCurrencyInstance(ptBr).format(total_pagamentos);
		texto_total_a_ser_pago += valor + "] | ";

		texto_total_a_ser_pago += "[Cobertura] [Total](de acordo com a quantidade contratada): ["
				+ z.format(total_cobertura * 60) + " kgs | " + z.format(total_cobertura) + "] sacos ";

		texto_total_a_ser_pago += "\n[Valor] [Total] [a] [Pagar](de acordo com a quantidade recebida): [";
		valor = NumberFormat.getCurrencyInstance(ptBr).format(total_recebidos_pagamentos);
		texto_total_a_ser_pago += valor + "] | ";
		texto_total_a_ser_pago += "[Cobertura] [Total](de acordo com a quantidade recebida): ["
				+ z.format(total_recebidos_cobertura * 60) + " kgs | " + z.format(total_recebidos_cobertura)
				+ "] sacos ";

		String texto_valor_final = texto_total_a_ser_pago;

		String texto_valor_pagamentos_efetuados = "[Somatória] [Final] [dos] [Pagamentos] [Efetuados](Pagamentos Efetuados + Transferencias Positivas - Transferencias Negativas): [";
		valor = NumberFormat.getCurrencyInstance(ptBr)
				.format((somatoria_total_pagamentos + somatoria_total_transferencias_positivas)
						- somatoria_total_transferencias_negativas);
		texto_valor_pagamentos_efetuados += valor + "] | ";

		texto_valor_pagamentos_efetuados += "[Cobertura] [Final Efetuada:] ["
				+ z.format(((somatoria_cobertura_pagamentos + somatoria_cobertura_transferencias_positivas)
						- somatoria_cobertura_transferencias_negativas) * 60)
				+ " kgs | " + z.format((somatoria_cobertura_pagamentos + somatoria_cobertura_transferencias_positivas)
						- somatoria_cobertura_transferencias_negativas)
				+ "] sacos ";

		String texto_valor_restante = "[Valor] [Total] [Restante] [a] [Pagar:] [";
		double valor_total_pago = somatoria_total_pagamentos + somatoria_total_transferencias_positivas
				- somatoria_total_transferencias_negativas;
		valor = NumberFormat.getCurrencyInstance(ptBr).format(total_recebidos_pagamentos - valor_total_pago);

		texto_valor_restante += valor + "] | ";
		double cobertura_total_pago = somatoria_cobertura_pagamentos + somatoria_cobertura_transferencias_positivas
				- somatoria_cobertura_transferencias_negativas;
		texto_valor_restante += "[Cobertura] [Restante:] ["
				+ z.format((total_recebidos_cobertura - cobertura_total_pago) * 60) + " kgs | "
				+ z.format(total_recebidos_cobertura - cobertura_total_pago) + "] sacos ";

		String texto_final = "\n\n" + texto_efetuados + "\n" + texto_transferencias_negativas + "\n"
				+ texto_transferencias_positivas + "\n\n" + texto_valor_final + "\n" + texto_valor_pagamentos_efetuados
				+ "\n\n" + texto_valor_restante;

		substituirTexto(texto_final);
	}

	public void criarTabelaPagamentosUnidos(ArrayList<PagamentoCompleto> pagamentos, double valor_total_pagamentos,
			double peso_total_cobertura, CadastroContrato novo_contrato) {

		NumberFormat z = NumberFormat.getNumberInstance();

		Locale ptBr = new Locale("pt", "BR");

		// criarParagrafo(1);
		// linhas x colunas
		int num_total_linhas = 0;
		int num_linhas_comissao = 0;
		int num_linhas_trans = 0;
		int num_linhas_pag_normal = 0;

		for (PagamentoCompleto pag : pagamentos) {
			if (pag.getTipo() == 1) {
				num_linhas_pag_normal++;
			} else if (pag.getTipo() == 2) {
				num_linhas_comissao++;
			} else if (pag.getTipo() == 3) {
				num_linhas_trans++;
			}
		}

		if (incluir_comissao_pagamento) {
			num_total_linhas += num_linhas_comissao;
		}
		if (incluir_transferencias_pagamentos) {
			num_total_linhas += num_linhas_trans;
		}

		num_total_linhas += num_linhas_pag_normal;

		XWPFTable table = document_global.createTable(num_total_linhas + 2, 12);

		setTableAlign(table, ParagraphAlignment.CENTER);
		XWPFTableRow tableRowOne = table.getRow(0);
		tableRowOne.getCell(0).removeParagraph(0);

		XWPFParagraph paragraph = tableRowOne.getCell(0).addParagraph();

		//
		int cabecalho = 0;

		if (valor_total_pagamentos != 0) {

			tableRowOne = table.getRow(cabecalho);
			tableRowOne.getCell(0).removeParagraph(0);
			paragraph = tableRowOne.getCell(0).addParagraph();

			criarParagrafoTabela(paragraph,
					"Valor Total: " + NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos), true);
			tableRowOne.getCell(0).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");
			CTHMerge hMerge = CTHMerge.Factory.newInstance();
			hMerge.setVal(STMerge.RESTART);
			table.getRow(cabecalho).getCell(0).getCTTc().getTcPr().setHMerge(hMerge);

			for (int celula = 1; celula <= 11; celula++) {
				tableRowOne = table.getRow(cabecalho);
				tableRowOne.getCell(celula).removeParagraph(0);
				paragraph = tableRowOne.getCell(celula).addParagraph();

				criarParagrafoTabela(paragraph, "", true);
				tableRowOne.getCell(celula).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");

				CTHMerge hMerge1 = CTHMerge.Factory.newInstance();
				hMerge1.setVal(STMerge.CONTINUE);
				table.getRow(cabecalho).getCell(celula).getCTTc().getTcPr().setHMerge(hMerge1);

			}

			cabecalho++;

		} else {

			tableRowOne = table.getRow(cabecalho);
			tableRowOne.getCell(0).removeParagraph(0);
			paragraph = tableRowOne.getCell(0).addParagraph();

			// compradores x vendedores

			// safra
			String safra = novo_contrato.getModelo_safra().getProduto().getNome_produto() + " "
					+ novo_contrato.getModelo_safra().getProduto().getTransgenia() + " "
					+ novo_contrato.getModelo_safra().getAno_plantio() + "/"
					+ novo_contrato.getModelo_safra().getAno_colheita();

			double quantidade_kg = 0;
			double quantidade_sacos = 0;

			if (novo_contrato.getMedida().equalsIgnoreCase("KG")) {
				quantidade_kg = novo_contrato.getQuantidade();
				quantidade_sacos = quantidade_kg / 60;
			} else if (novo_contrato.getMedida().equalsIgnoreCase("Sacos")) {
				quantidade_sacos = novo_contrato.getQuantidade();
				quantidade_kg = quantidade_sacos * 60;
			}

			criarParagrafoTabela(paragraph,
					"CTR: " + novo_contrato.getCodigo() + " " + safra + " Quantidade Total: " + z.format(quantidade_kg)
							+ " kgs | " + z.format(quantidade_sacos) + " sacos "
							+ NumberFormat.getCurrencyInstance(ptBr).format(novo_contrato.getValor_produto()) + " por "
							+ novo_contrato.getMedida() + " totalizando: "
							+ NumberFormat.getCurrencyInstance(ptBr)
									.format(novo_contrato.getValor_a_pagar().doubleValue())
							+ " " + novo_contrato.getNomes_compradores().toUpperCase() + " X "
							+ novo_contrato.getNomes_vendedores().toUpperCase(),
					true);
			tableRowOne.getCell(0).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");
			CTHMerge hMerge = CTHMerge.Factory.newInstance();
			hMerge.setVal(STMerge.RESTART);
			table.getRow(cabecalho).getCell(0).getCTTc().getTcPr().setHMerge(hMerge);

			for (int celula = 1; celula <= 11; celula++) {
				tableRowOne = table.getRow(cabecalho);
				tableRowOne.getCell(celula).removeParagraph(0);
				paragraph = tableRowOne.getCell(celula).addParagraph();

				criarParagrafoTabela(paragraph, "", true);
				tableRowOne.getCell(celula).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");

				CTHMerge hMerge1 = CTHMerge.Factory.newInstance();
				hMerge1.setVal(STMerge.CONTINUE);
				table.getRow(cabecalho).getCell(celula).getCTTc().getTcPr().setHMerge(hMerge1);

			}

			cabecalho++;

		}

		int i = cabecalho;
		tableRowOne = table.getRow(i);
		tableRowOne.getCell(0).removeParagraph(0);
		paragraph = tableRowOne.getCell(0).addParagraph();

		criarParagrafoTabela(paragraph, "Data", false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(1).removeParagraph(0);
		paragraph = tableRowOne.getCell(1).addParagraph();
		criarParagrafoTabela(paragraph, "Tipo", false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(2).removeParagraph(0);
		paragraph = tableRowOne.getCell(2).addParagraph();
		criarParagrafoTabela(paragraph, "Descrição", false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(3).removeParagraph(0);
		paragraph = tableRowOne.getCell(3).addParagraph();
		criarParagrafoTabela(paragraph, "Valor Pagamento", false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(4).removeParagraph(0);
		paragraph = tableRowOne.getCell(4).addParagraph();
		criarParagrafoTabela(paragraph, "Valor Unidade: ", false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(5).removeParagraph(0);
		paragraph = tableRowOne.getCell(5).addParagraph();
		criarParagrafoTabela(paragraph, "Cobertura: ", false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(6).removeParagraph(0);
		paragraph = tableRowOne.getCell(6).addParagraph();
		criarParagrafoTabela(paragraph, "Depositante", false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(7).removeParagraph(0);
		paragraph = tableRowOne.getCell(7).addParagraph();
		criarParagrafoTabela(paragraph, "Conta Depositante", false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(8).removeParagraph(0);
		paragraph = tableRowOne.getCell(8).addParagraph();
		criarParagrafoTabela(paragraph, "Favorecido", false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(9).removeParagraph(0);
		paragraph = tableRowOne.getCell(9).addParagraph();
		criarParagrafoTabela(paragraph, "Conta Favorecido", false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(10).removeParagraph(0);
		paragraph = tableRowOne.getCell(10).addParagraph();
		criarParagrafoTabela(paragraph, "Contrato Remetente", false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(11).removeParagraph(0);
		paragraph = tableRowOne.getCell(11).addParagraph();
		criarParagrafoTabela(paragraph, "Contrato Destinatario", false);

		int i_global = i + 1;

		double valor_total_pagamentos_efetuados = 0;
		double valor_total_transferencias_retiradas = 0;
		double valor_total_transferencias_recebidas = 0;
		double valor_total_pagamentos_restantes = 0;
		double valor_total_comissao = 0;
		double peso_total_cobertura_efetuados = 0;
		double peso_total_cobertura_transferencia_negativa = 0;
		double peso_total_cobertura_transferencia_positiva = 0;
		double peso_total_cobertura_restante = 0;
		double peso_total_cobertura_comissao = 0;

		for (PagamentoCompleto pagamento : pagamentos) {

			if (pagamento.getTipo() == 1 || (pagamento.getTipo() == 2 && incluir_comissao_pagamento)
					|| (pagamento.getTipo() == 3 && incluir_transferencias_pagamentos)) {

				// celula data
				tableRowOne = table.getRow(i_global);
				tableRowOne.getCell(0).removeParagraph(0);
				paragraph = tableRowOne.getCell(0).addParagraph();
				criarParagrafoTabela(paragraph, pagamento.getData_pagamento(), false);

				double valor_por_saco = 0;
				// if(soma_total_pagamentos != 0)
				// novo_contrato = pagamento.getContrato_receptor();

				double quantidade_total_contrato_sacos = 0;

				if (novo_contrato.getMedida().equalsIgnoreCase("Kg")) {
					quantidade_total_contrato_sacos = novo_contrato.getQuantidade() / 60;
					valor_por_saco = novo_contrato.getValor_produto() * 60;
				} else if (novo_contrato.getMedida().equalsIgnoreCase("Sacos")) {
					quantidade_total_contrato_sacos = novo_contrato.getQuantidade();
					valor_por_saco = novo_contrato.getValor_produto();
				}
				GerenciarBancoContratos gerenciar_contratos = new GerenciarBancoContratos();

				int tipo = pagamento.getTipo();
				String s_tipo = "";
				if (pagamento.getTipo() == 1) {
					s_tipo = "NORMAL";
				} else if (pagamento.getTipo() == 2) {
					s_tipo = "COMISSÃO";
				} else if (pagamento.getTipo() == 3) {
					// é uma transferencia
					if (pagamento.getId_contrato_remetente() == novo_contrato.getId()) {
						s_tipo = "-TRANSFERENCIA";
					} else if (pagamento.getId_contrato_destinatario() == novo_contrato.getId()) {
						// é uma transferencia positiva
						s_tipo = "+TRANSFERENCIA";
					}

				}

				// celula tipo
				tableRowOne = table.getRow(i_global);
				tableRowOne.getCell(1).removeParagraph(0);
				paragraph = tableRowOne.getCell(1).addParagraph();
				criarParagrafoTabela(paragraph, s_tipo, false);

				// celula descricao
				tableRowOne = table.getRow(i_global);
				tableRowOne.getCell(2).removeParagraph(0);
				paragraph = tableRowOne.getCell(2).addParagraph();
				criarParagrafoTabela(paragraph, pagamento.getDescricao(), false);

				// valor pagamento
				double valor_pagamento = pagamento.getValor_pagamento();

				String valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor_pagamento);
				double cobertura = valor_pagamento / valor_por_saco;

				if (pagamento.getTipo() == 1) {
					valor_total_pagamentos_efetuados += valor_pagamento;

				} else if (pagamento.getTipo() == 2) {
					// é uma comissão
					valor_total_comissao += valor_pagamento;

				} else if (pagamento.getTipo() == 3) {
					// é uma transferencia
					if (pagamento.getId_contrato_remetente() == novo_contrato.getId()) {
						// é uma transferencia negativa
						valor_total_transferencias_retiradas += valor_pagamento;

					} else if (pagamento.getId_contrato_destinatario() == novo_contrato.getId()) {
						// é uma transferencia positiva
						valor_total_transferencias_recebidas += valor_pagamento;

					}

				}
				valorString = NumberFormat.getCurrencyInstance(ptBr).format(pagamento.getValor_pagamento());

				// celula pagamento
				tableRowOne = table.getRow(i_global);
				tableRowOne.getCell(3).removeParagraph(0);
				paragraph = tableRowOne.getCell(3).addParagraph();
				criarParagrafoTabela(paragraph, valorString, false);

				// valor da unidade
				CadastroContrato ct_remetente = pagamento.getContrato_remetente();
				CadastroContrato ct_destinatario = pagamento.getContrato_destinatario();

				valorString = NumberFormat.getCurrencyInstance(ptBr).format(ct_remetente.getValor_produto());
				if (pagamento.getTipo() == 1) {

				} else if (pagamento.getTipo() == 2) {

				} else if (pagamento.getTipo() == 3) {
					// é uma transferencia
					if (pagamento.getId_contrato_remetente() == novo_contrato.getId()) {

					} else if (pagamento.getId_contrato_destinatario() == novo_contrato.getId()) {
						// é uma transferencia positiva
						// pegar o preco da unidade do contrato que recebeu a transferencia
						valorString = NumberFormat.getCurrencyInstance(ptBr).format(novo_contrato.getValor_produto());

					}
				}
				// celula valor unidade
				tableRowOne = table.getRow(i_global);
				tableRowOne.getCell(4).removeParagraph(0);
				paragraph = tableRowOne.getCell(4).addParagraph();
				criarParagrafoTabela(paragraph, valorString, false);

				// cobertura
				cobertura = pagamento.getValor_pagamento() / ct_remetente.getValor_produto();
				if (ct_remetente.getMedida().equalsIgnoreCase("KG"))
					cobertura = cobertura / 60;

				String retorno = z.format(cobertura) + " sacos";

				if (pagamento.getTipo() == 1) {
					peso_total_cobertura_efetuados += cobertura;
					somatoria_cobertura_pagamentos += cobertura;
				} else if (pagamento.getTipo() == 2) {
					peso_total_cobertura_comissao += cobertura;
					somatoria_cobertura_pagamentos += cobertura;
				} else if (pagamento.getTipo() == 3) {
					// é uma transferencia
					if (pagamento.getId_contrato_remetente() == novo_contrato.getId()) {
						retorno = "-" + retorno;
						peso_total_cobertura_transferencia_negativa += cobertura;
						somatoria_cobertura_transferencias_negativas += cobertura;
					} else if (pagamento.getId_contrato_destinatario() == novo_contrato.getId()) {
						// é uma transferencia positiva
						// pegar o preco da unidade do contrato que recebeu a transferencia

						cobertura = pagamento.getValor_pagamento() / novo_contrato.getValor_produto();

						if (novo_contrato.getMedida().equalsIgnoreCase("KG"))
							cobertura = cobertura / 60;
						peso_total_cobertura_transferencia_positiva += cobertura;
						somatoria_cobertura_transferencias_positivas += cobertura;
						retorno = z.format(cobertura) + " sacos";
						retorno = "+" + retorno;
					}

				}

				// celula cobertura
				tableRowOne = table.getRow(i_global);
				tableRowOne.getCell(5).removeParagraph(0);
				paragraph = tableRowOne.getCell(5).addParagraph();
				criarParagrafoTabela(paragraph, retorno, false);

				// celula depositante
				tableRowOne = table.getRow(i_global);
				tableRowOne.getCell(6).removeParagraph(0);
				paragraph = tableRowOne.getCell(6).addParagraph();
				criarParagrafoTabela(paragraph, pagamento.getDepositante().toUpperCase(), false);

				// celula conta depositante
				tableRowOne = table.getRow(i_global);
				tableRowOne.getCell(7).removeParagraph(0);
				paragraph = tableRowOne.getCell(7).addParagraph();
				criarParagrafoTabela(paragraph, pagamento.getConta_bancaria_depositante().toUpperCase(), false);

				// celula favorecido
				tableRowOne = table.getRow(i_global);
				tableRowOne.getCell(8).removeParagraph(0);
				paragraph = tableRowOne.getCell(8).addParagraph();
				criarParagrafoTabela(paragraph, pagamento.getFavorecido().toUpperCase(), false);

				// celula conta favorecido
				tableRowOne = table.getRow(i_global);
				tableRowOne.getCell(9).removeParagraph(0);
				paragraph = tableRowOne.getCell(9).addParagraph();
				if (pagamento.getConta_bancaria_favorecido() != null) {
					criarParagrafoTabela(paragraph, pagamento.getConta_bancaria_favorecido().toUpperCase(), false);
				} else {
					criarParagrafoTabela(paragraph, "", false);

				}

				if (pagamento.getTipo() == 1) {
					// celula contrato remetente
					tableRowOne = table.getRow(i_global);
					tableRowOne.getCell(10).removeParagraph(0);
					paragraph = tableRowOne.getCell(10).addParagraph();
					criarParagrafoTabela(paragraph, "", false);

					// celula contrato destinataio
					tableRowOne = table.getRow(i_global);
					tableRowOne.getCell(11).removeParagraph(0);
					paragraph = tableRowOne.getCell(11).addParagraph();
					criarParagrafoTabela(paragraph, "", false);
				} else {
					// celula contrato remetente
					tableRowOne = table.getRow(i_global);
					tableRowOne.getCell(10).removeParagraph(0);
					paragraph = tableRowOne.getCell(10).addParagraph();
					criarParagrafoTabela(paragraph, ct_remetente.getCodigo(), false);

					// celula contrato destinataio
					tableRowOne = table.getRow(i_global);
					tableRowOne.getCell(11).removeParagraph(0);
					paragraph = tableRowOne.getCell(11).addParagraph();
					criarParagrafoTabela(paragraph, ct_destinatario.getCodigo(), false);
				}

				i_global++;

			}

		}
		// somatorias

		double valor_total_pagamentos_concluidos = valor_total_pagamentos_efetuados
				- valor_total_transferencias_retiradas + valor_total_transferencias_recebidas;
		String valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos_concluidos);

		double peso_total_cobertura_concluida = peso_total_cobertura_efetuados
				- peso_total_cobertura_transferencia_negativa + peso_total_cobertura_transferencia_positiva;

		i_global++;

		valor_total_pagamentos_restantes = valor_total_pagamentos - valor_total_pagamentos_efetuados
				+ valor_total_transferencias_retiradas - valor_total_transferencias_recebidas;
		valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos_restantes);

		peso_total_cobertura_restante = peso_total_cobertura - peso_total_cobertura_efetuados
				+ peso_total_cobertura_transferencia_negativa - peso_total_cobertura_transferencia_positiva;

		if (incluir_comissao_pagamento) {
			XWPFParagraph paragrafo = document_global.createParagraph();
			XWPFRun run = paragrafo.createRun();
			run.setText("*Valor de comissão não é somado ao valor total");
			run.setColor("ff0000");
			run.setFontFamily("Times New Roman");
			run.setFontSize(8);

		}

		// adicionar valores

		// adicionais
		/******************* inicio adicionais ***********************/

		String texto_total = "\nTotal do Contrato: ";
		valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos);
		texto_total += valor;
		texto_total += " Cobre: " + z.format(peso_total_cobertura * 60) + " kgs | ";
		texto_total += z.format(peso_total_cobertura) + " sacos";
		// total_cobertura += peso_total_cobertura;

		String texto_efetuados = "Efetuados: ";
		valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos_efetuados);
		somatoria_total_pagamentos += valor_total_pagamentos_efetuados;
		texto_efetuados += valor;
		texto_efetuados += " Cobre: " + z.format(peso_total_cobertura_efetuados * 60) + " kgs | ";
		texto_efetuados += z.format(peso_total_cobertura_efetuados) + " sacos";

		// status
		String texto_transferencias_negativas = "";
		if (incluir_transferencias_pagamentos) {
			// transferencias negativas
			texto_transferencias_negativas = "Transferencias:(-) ";
			valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_transferencias_retiradas);
			texto_transferencias_negativas += valor;
			texto_transferencias_negativas += " Cobre: " + z.format(peso_total_cobertura_transferencia_negativa * 60)
					+ " kgs | ";
			texto_transferencias_negativas += z.format(peso_total_cobertura_transferencia_negativa) + " sacos";
			somatoria_total_transferencias_negativas += valor_total_transferencias_retiradas;
		}
		// status
		double diferenca = (valor_total_pagamentos_efetuados - valor_total_transferencias_retiradas
				+ valor_total_transferencias_recebidas) - valor_total_pagamentos;
		String status_pagamento = "[Pagamentos:] ";
		if (diferenca == 0) {
			status_pagamento += "[Pagamento Concluído]";
		} else if (diferenca > 0) {
			status_pagamento += "[Excedeu] [em] [" + NumberFormat.getCurrencyInstance(ptBr).format(diferenca) + "]";

		} else if (diferenca < 0) {
			status_pagamento += "[Incompleto], [falta] [" + NumberFormat.getCurrencyInstance(ptBr).format(diferenca)
					+ "]";

		}

		String texto_transferencias_positivas = "";
		if (incluir_transferencias_pagamentos) {
			// transferencias positivas
			texto_transferencias_positivas = "Transferencias:(+) ";
			valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_transferencias_recebidas);
			texto_transferencias_positivas += valor;
			texto_transferencias_positivas += " Cobre: " + z.format(peso_total_cobertura_transferencia_positiva * 60)
					+ " kgs | ";
			texto_transferencias_positivas += z.format(peso_total_cobertura_transferencia_positiva) + " sacos";
			somatoria_total_transferencias_positivas += valor_total_transferencias_recebidas;

		}
		String status_cobertura = "[Cobertura:] ";
		double diferenca_pesos = peso_total_cobertura_restante;

		if (diferenca_pesos == 0 || diferenca_pesos == -0) {
			status_cobertura += "[Todos] [os] [sacos] [foram] [pagos]";
		} else if (diferenca_pesos < 0) {
			status_cobertura += "[Excedeu] [em] [" + z.format(diferenca_pesos * 60) + " kgs | "
					+ z.format(diferenca_pesos) + "] [Sacos]";

		} else if (diferenca_pesos > 0) {
			status_cobertura += "[Incompleto], [falta] [pagar] [" + z.format(diferenca_pesos) + "] [Sacos]";

		}

		// comissão
		String texto_comissao = "";
		if (incluir_comissao_pagamento) {
			texto_comissao = "Comissão: ";
			valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_comissao);
			texto_comissao += valor;
			texto_comissao += " Cobre: " + z.format(peso_total_cobertura_comissao * 60) + " kgs | ";
			texto_comissao += z.format(peso_total_cobertura_comissao) + " sacos";

		}

		// concluidos

		String texto_concluida = "Concluída:";

		valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos_concluidos);
		peso_total_cobertura_concluida = peso_total_cobertura_efetuados - peso_total_cobertura_transferencia_negativa
				+ peso_total_cobertura_transferencia_positiva;
		texto_concluida += valor;
		texto_concluida += " Cobre: " + z.format(peso_total_cobertura_concluida * 60) + " kgs | ";

		texto_concluida += z.format(peso_total_cobertura_concluida) + " sacos";

		// restante

		String texto_restante = " Restante:";

		valor_total_pagamentos_restantes = valor_total_pagamentos - valor_total_pagamentos_efetuados
				+ valor_total_transferencias_retiradas - valor_total_transferencias_recebidas;
		valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos_restantes);
		peso_total_cobertura_restante = peso_total_cobertura - peso_total_cobertura_efetuados
				+ peso_total_cobertura_transferencia_negativa - peso_total_cobertura_transferencia_positiva;
		texto_restante += valor;
		texto_restante += " Cobre: " + z.format(peso_total_cobertura_restante * 60) + " kgs | ";

		texto_restante += z.format(peso_total_cobertura_restante) + " sacos";

		String texto_final = texto_total + "\n";

		if (incluir_transferencias_pagamentos) {
			texto_final = texto_final + texto_transferencias_negativas + "\n" + texto_transferencias_positivas + "\n";
		}
		if (incluir_comissao_pagamento) {
			texto_final = texto_final + texto_comissao + "\n";
		}

		texto_final = texto_final + texto_concluida + "\n" + texto_restante;
		texto_final = texto_final + "\n\n [Status] [do] [Pagamento]:\n";
		texto_final = texto_final + status_pagamento + "\n" + status_cobertura;

		/************* fim adicionai *************************/

	}

	public DadosTabelaExcel criarTabelaPagamentosUnidosExcel(HSSFWorkbook workbook, HSSFSheet sheet, int rownum,
			ArrayList<CadastroContrato> contratos) {

		NumberFormat z = NumberFormat.getNumberInstance();

		Locale ptBr = new Locale("pt", "BR");

		HSSFDataFormat numberFormat = workbook.createDataFormat();

		HSSFFont newFont_branca = workbook.createFont();
		newFont_branca.setBold(true);
		newFont_branca.setColor(IndexedColors.WHITE.getIndex());
		newFont_branca.setFontName("Calibri");
		newFont_branca.setItalic(false);
		newFont_branca.setFontHeight((short) (11 * 20));

		// estilo para cabecalho fundo verde
		CellStyle celula_fundo_verde_texto_branco = workbook.createCellStyle();
		celula_fundo_verde_texto_branco.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		celula_fundo_verde_texto_branco.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		celula_fundo_verde_texto_branco.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_verde_texto_branco.setVerticalAlignment(VerticalAlignment.CENTER);

		celula_fundo_verde_texto_branco.setFont(newFont_branca);

		HSSFFont newFont_preta = workbook.createFont();
		newFont_preta.setColor(IndexedColors.BLACK.getIndex());
		newFont_preta.setFontName("Calibri");
		newFont_preta.setItalic(false);
		newFont_preta.setFontHeight((short) (11 * 20));

		CellStyle celula_fundo_branco_texto_preto = workbook.createCellStyle();
		celula_fundo_branco_texto_preto.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_branco_texto_preto.setVerticalAlignment(VerticalAlignment.CENTER);
		celula_fundo_branco_texto_preto.setFont(newFont_preta);

		// celula para numero alinhado ao centro
		CellStyle numberStyle = workbook.createCellStyle();
		numberStyle.setDataFormat(numberFormat.getFormat("R$ #,##0.00"));
		numberStyle.setAlignment(HorizontalAlignment.CENTER);
		numberStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		// celula para numero alinhado ao centro
		CellStyle pesoStyle = workbook.createCellStyle();
		pesoStyle.setDataFormat(numberFormat.getFormat("#,##0.00"));
		pesoStyle.setAlignment(HorizontalAlignment.CENTER);
		pesoStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle numberStyleFundoVerdeTextoBranco = workbook.createCellStyle();
		numberStyleFundoVerdeTextoBranco.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		numberStyleFundoVerdeTextoBranco.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		numberStyleFundoVerdeTextoBranco.setAlignment(HorizontalAlignment.CENTER);
		numberStyleFundoVerdeTextoBranco.setVerticalAlignment(VerticalAlignment.CENTER);
		numberStyleFundoVerdeTextoBranco.setDataFormat(numberFormat.getFormat("#,##0.00"));

		numberStyleFundoVerdeTextoBranco.setFont(newFont_branca);

		CellStyle valorStyleFundoVerdeTextoBranco = workbook.createCellStyle();
		valorStyleFundoVerdeTextoBranco.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		valorStyleFundoVerdeTextoBranco.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		valorStyleFundoVerdeTextoBranco.setAlignment(HorizontalAlignment.CENTER);
		valorStyleFundoVerdeTextoBranco.setVerticalAlignment(VerticalAlignment.CENTER);
		valorStyleFundoVerdeTextoBranco.setDataFormat(numberFormat.getFormat("R$ #,##0.00"));
		valorStyleFundoVerdeTextoBranco.setFont(newFont_branca);

		// celular de contrato normal
		HSSFFont newFont_verde = workbook.createFont();
		newFont_verde.setColor(IndexedColors.GREEN.getIndex());
		newFont_verde.setFontName("Calibri");
		newFont_verde.setItalic(false);
		newFont_verde.setFontHeight((short) (11 * 20));

		CellStyle celula_fundo_branco_texto_verde = workbook.createCellStyle();
		celula_fundo_branco_texto_verde.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_branco_texto_verde.setVerticalAlignment(VerticalAlignment.CENTER);
		celula_fundo_branco_texto_verde.setFont(newFont_verde);

		// celular de sub-contrato n
		HSSFFont newFont_vermelha = workbook.createFont();
		newFont_vermelha.setColor(IndexedColors.RED.getIndex());
		newFont_vermelha.setFontName("Calibri");
		newFont_vermelha.setItalic(false);
		newFont_vermelha.setFontHeight((short) (11 * 20));

		CellStyle celula_fundo_branco_texto_vermelho = workbook.createCellStyle();
		celula_fundo_branco_texto_vermelho.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_branco_texto_vermelho.setVerticalAlignment(VerticalAlignment.CENTER);
		celula_fundo_branco_texto_vermelho.setFont(newFont_vermelha);

		// celula de ganho potencial

		// celular de sub-contrato n
		HSSFFont newFont_azul = workbook.createFont();
		newFont_azul.setColor(IndexedColors.BLUE.getIndex());
		newFont_azul.setFontName("Calibri");
		newFont_azul.setItalic(false);
		newFont_azul.setFontHeight((short) (11 * 20));

		CellStyle celula_fundo_branco_texto_azul = workbook.createCellStyle();
		celula_fundo_branco_texto_azul.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_branco_texto_azul.setVerticalAlignment(VerticalAlignment.CENTER);
		celula_fundo_branco_texto_azul.setFont(newFont_azul);

		int cellnum = 0;

		Cell cell;
		Row row;

		cellnum = 0;
		row = sheet.createRow(rownum);

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("CONTRATO");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("DATA");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("TIPO");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("VALOR PAGAMENTO");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("VALOR UNIDADE");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("COBERTURA");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("DESCRIÇÃO");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("DEPOSITANTE");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("CONTA DEPOSITANTE");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("FAVORECIDO");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("CONTA FAVORECIDO");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("CONTRATO REMETENTE");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("CONTRATO DESTINATARIO");

		sheet.setAutoFilter(CellRangeAddress.valueOf("A" + (rownum) + ":M" + (rownum)));

		rownum++;

		int primeira_linha = rownum;
		int ultima_linha = primeira_linha;

		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();

		double valor_total_contratado = 0.0;
		double peso_total_cobertura_contratada = 0.0;

		double valor_total_a_receber = 0.0;
		double peso_total_cobertura_recebido = 0.0;

		for (CadastroContrato novo_contrato : contratos) {

			if (novo_contrato.getSub_contrato() != 8 && novo_contrato.getSub_contrato() != 9) {

				ArrayList<PagamentoCompleto> pagamentos = gerenciar
						.getPagamentosContratuaisParaRelatorio(novo_contrato.getId());

				if (novo_contrato.getMedida().equalsIgnoreCase("Sacos")) {
					peso_total_cobertura_contratada += novo_contrato.getQuantidade();

				} else if (novo_contrato.getMedida().equalsIgnoreCase("KG")) {
					double peso_em_sacos = novo_contrato.getQuantidade() / 60;
					peso_total_cobertura_contratada += peso_em_sacos;

				}

				valor_total_contratado += novo_contrato.getValor_a_pagar().doubleValue();

				double total_recebido = getPesoTotalRecebido(novo_contrato) / 60;
				peso_total_cobertura_recebido += (total_recebido);

				if (novo_contrato.getMedida().equalsIgnoreCase("Sacos")) {
					valor_total_a_receber += (total_recebido * novo_contrato.getValor_produto());

				} else if (novo_contrato.getMedida().equalsIgnoreCase("KG")) {
					double valor_por_saco = novo_contrato.getValor_produto() * 60;
					valor_total_a_receber += (total_recebido * valor_por_saco);

				}

				for (PagamentoCompleto pagamento : pagamentos) {

					if (pagamento.getTipo() == 1 || (pagamento.getTipo() == 2 && incluir_comissao_pagamento)
							|| (pagamento.getTipo() == 3 && incluir_transferencias_pagamentos)) {

						cellnum = 0;
						row = sheet.createRow(rownum);

						// celula ctr
						cell = row.createCell(cellnum++);
						cell.setCellStyle(celula_fundo_branco_texto_preto);
						cell.setCellValue(pagamento.getContrato_remetente().getCodigo());

						// celula data
						cell = row.createCell(cellnum++);
						cell.setCellStyle(celula_fundo_branco_texto_preto);
						cell.setCellValue(pagamento.getData_pagamento());

						double valor_por_saco = 0;
						// if(soma_total_pagamentos != 0)
						// novo_contrato = pagamento.getContrato_receptor();

						double quantidade_total_contrato_sacos = 0;

						if (novo_contrato.getMedida().equalsIgnoreCase("Kg")) {
							quantidade_total_contrato_sacos = novo_contrato.getQuantidade() / 60;
							valor_por_saco = novo_contrato.getValor_produto() * 60;
						} else if (novo_contrato.getMedida().equalsIgnoreCase("Sacos")) {
							quantidade_total_contrato_sacos = novo_contrato.getQuantidade();
							valor_por_saco = novo_contrato.getValor_produto();
						}
						GerenciarBancoContratos gerenciar_contratos = new GerenciarBancoContratos();

						int tipo = pagamento.getTipo();
						String s_tipo = "";
						if (pagamento.getTipo() == 1) {
							s_tipo = "NORMAL";
						} else if (pagamento.getTipo() == 2) {
							s_tipo = "COMISSÃO";
						} else if (pagamento.getTipo() == 3) {
							// é uma transferencia
							if (pagamento.getId_contrato_remetente() == novo_contrato.getId()) {
								s_tipo = "-TRANSFERENCIA";
							} else if (pagamento.getId_contrato_destinatario() == novo_contrato.getId()) {
								// é uma transferencia positiva
								s_tipo = "+TRANSFERENCIA";
							}

						}

						// celula tipo
						cell = row.createCell(cellnum++);
						cell.setCellStyle(celula_fundo_branco_texto_preto);
						cell.setCellValue(s_tipo);

						// valor pagamento
						double valor_pagamento = pagamento.getValor_pagamento();

						String valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor_pagamento);
						double cobertura = valor_pagamento / valor_por_saco;

						if (pagamento.getTipo() == 1) {

						} else if (pagamento.getTipo() == 2) {
							// é uma comissão
							valor_pagamento = valor_pagamento * -1;

						} else if (pagamento.getTipo() == 3) {
							// é uma transferencia
							if (pagamento.getId_contrato_remetente() == novo_contrato.getId()) {
								// é uma transferencia negativa
								valor_pagamento = valor_pagamento * -1;
							} else if (pagamento.getId_contrato_destinatario() == novo_contrato.getId()) {
								// é uma transferencia positiva

							}

						}
						valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor_pagamento);

						// celula pagamento
						cell = row.createCell(cellnum++);
						cell.setCellStyle(numberStyle);
						cell.setCellValue(valor_pagamento);

						// valor da unidade
						CadastroContrato ct_remetente = pagamento.getContrato_remetente();
						CadastroContrato ct_destinatario = pagamento.getContrato_destinatario();

						double valor_produto = ct_remetente.getValor_produto();

						if (pagamento.getTipo() == 1) {

						} else if (pagamento.getTipo() == 2) {

						} else if (pagamento.getTipo() == 3) {
							// é uma transferencia
							if (pagamento.getId_contrato_remetente() == novo_contrato.getId()) {

							} else if (pagamento.getId_contrato_destinatario() == novo_contrato.getId()) {
								// é uma transferencia positiva
								// pegar o preco da unidade do contrato que recebeu a transferencia
								valor_produto = novo_contrato.getValor_produto();

							}
						}
						// celula valor unidade
						cell = row.createCell(cellnum++);
						cell.setCellStyle(numberStyle);
						cell.setCellValue(valor_produto);

						// cobertura
						cobertura = pagamento.getValor_pagamento() / ct_remetente.getValor_produto();
						if (ct_remetente.getMedida().equalsIgnoreCase("KG"))
							cobertura = cobertura / 60;

						if (pagamento.getTipo() == 1) {
							somatoria_cobertura_pagamentos += cobertura;
						} else if (pagamento.getTipo() == 2) {
							somatoria_cobertura_pagamentos += cobertura;
						} else if (pagamento.getTipo() == 3) {
							// é uma transferencia
							if (pagamento.getId_contrato_remetente() == novo_contrato.getId()) {
								cobertura = cobertura * -1;
								somatoria_cobertura_transferencias_negativas += cobertura;
							} else if (pagamento.getId_contrato_destinatario() == novo_contrato.getId()) {
								// é uma transferencia positiva
								// pegar o preco da unidade do contrato que recebeu a transferencia

								cobertura = pagamento.getValor_pagamento() / novo_contrato.getValor_produto();

								if (novo_contrato.getMedida().equalsIgnoreCase("KG"))
									cobertura = cobertura / 60;
								somatoria_cobertura_transferencias_positivas += cobertura;

							}

						}

						// celula cobertura
						cell = row.createCell(cellnum++);
						cell.setCellStyle(pesoStyle);
						cell.setCellValue(cobertura);

						// celula descricao
						cell = row.createCell(cellnum++);
						cell.setCellStyle(celula_fundo_branco_texto_preto);
						cell.setCellValue(pagamento.getDescricao());

						// celula depositante
						cell = row.createCell(cellnum++);
						cell.setCellStyle(celula_fundo_branco_texto_preto);
						cell.setCellValue(pagamento.getDepositante().toUpperCase());

						// celula conta depositante
						cell = row.createCell(cellnum++);
						cell.setCellStyle(celula_fundo_branco_texto_preto);
						cell.setCellValue(pagamento.getConta_bancaria_depositante().toUpperCase());

						// celula favorecido
						cell = row.createCell(cellnum++);
						cell.setCellStyle(celula_fundo_branco_texto_preto);
						cell.setCellValue(pagamento.getFavorecido().toUpperCase());

						// celula conta favorecido
						cell = row.createCell(cellnum++);
						cell.setCellStyle(celula_fundo_branco_texto_preto);

						if (pagamento.getConta_bancaria_favorecido() != null) {
							cell.setCellValue(pagamento.getConta_bancaria_favorecido().toUpperCase());

						} else {
							cell.setCellValue("");

						}

						if (pagamento.getTipo() == 1) {
							// celula contrato remetente
							cell = row.createCell(cellnum++);
							cell.setCellStyle(celula_fundo_branco_texto_preto);
							cell.setCellValue("");

							// celula contrato destinataio
							cell = row.createCell(cellnum++);
							cell.setCellStyle(celula_fundo_branco_texto_preto);
							cell.setCellValue("");

						} else {
							// celula contrato remetente
							cell = row.createCell(cellnum++);
							cell.setCellStyle(celula_fundo_branco_texto_preto);
							cell.setCellValue(ct_remetente.getCodigo());

							// celula contrato destinataio
							cell = row.createCell(cellnum++);
							cell.setCellStyle(celula_fundo_branco_texto_preto);
							cell.setCellValue(ct_destinatario.getCodigo());

						}
						rownum++;
						ultima_linha = rownum;

					}

				}
			}
		}

		// somatorias
		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		// somatoria de valores
		cell = row.createCell(2);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue("Valor Total:");

		cell = row.createCell(3);
		cell.setCellStyle(valorStyleFundoVerdeTextoBranco);
		cell.setCellType(CellType.FORMULA);
		String formula = "SUM(D" + primeira_linha + ":D" + ultima_linha + ")";
		cell.setCellFormula(formula);

		cell = row.createCell(4);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue("Cobertura Total:");

		cell = row.createCell(5);
		cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
		cell.setCellType(CellType.FORMULA);
		formula = "SUM(F" + primeira_linha + ":F" + ultima_linha + ")";
		cell.setCellFormula(formula);

		row = sheet.createRow(rownum += 3);
		cellnum = 0;

		// somatoria de pg normal
		cell = row.createCell(0);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue("Total Normal:");

		cell = row.createCell(1);
		cell.setCellStyle(valorStyleFundoVerdeTextoBranco);
		cell.setCellType(CellType.FORMULA);
		formula = "SUMPRODUCT(SUBTOTAL(9,OFFSET(D" + primeira_linha + ":D" + ultima_linha + ",ROW(D" + primeira_linha
				+ ":D" + ultima_linha + ")-ROW(D" + primeira_linha + "),0,1,1)),-(C" + primeira_linha + ":C"
				+ ultima_linha + "=\"Normal\")) * -1";
		cell.setCellFormula(formula);

		cell = row.createCell(2);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue("Cobertura Normal:");

		cell = row.createCell(3);
		cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
		cell.setCellType(CellType.FORMULA);
		formula = "SUMPRODUCT(SUBTOTAL(9,OFFSET(F" + primeira_linha + ":F" + ultima_linha + ",ROW(F" + primeira_linha
				+ ":F" + ultima_linha + ")-ROW(F" + primeira_linha + "),0,1,1)),-(C" + primeira_linha + ":C"
				+ ultima_linha + "=\"Normal\")) * -1";
		cell.setCellFormula(formula);

		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		if (incluir_transferencias_pagamentos) {

			// somatoria de pg -transferencias
			cell = row.createCell(0);
			cell.setCellStyle(celula_fundo_branco_texto_preto);
			cell.setCellValue("Total Transferencia(-):");

			cell = row.createCell(1);
			cell.setCellStyle(valorStyleFundoVerdeTextoBranco);
			cell.setCellType(CellType.FORMULA);
			formula = "SUMPRODUCT(SUBTOTAL(9,OFFSET(D" + primeira_linha + ":D" + ultima_linha + ",ROW(D"
					+ primeira_linha + ":D" + ultima_linha + ")-ROW(D" + primeira_linha + "),0,1,1)),-(C"
					+ primeira_linha + ":C" + ultima_linha + "=\"-TRANSFERENCIA\")) *-1";
			cell.setCellFormula(formula);

			cell = row.createCell(2);
			cell.setCellStyle(celula_fundo_branco_texto_preto);
			cell.setCellValue("Cobertura Transferencia(-):");

			cell = row.createCell(3);
			cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
			cell.setCellType(CellType.FORMULA);
			formula = "SUMPRODUCT(SUBTOTAL(9,OFFSET(F" + primeira_linha + ":F" + ultima_linha + ",ROW(F"
					+ primeira_linha + ":F" + ultima_linha + ")-ROW(F" + primeira_linha + "),0,1,1)),-(C"
					+ primeira_linha + ":C" + ultima_linha + "=\"-TRANSFERENCIA\")) *-1";
			cell.setCellFormula(formula);

			row = sheet.createRow(rownum += 1);
			cellnum = 0;

			// somatoria de pg +transferencias
			cell = row.createCell(0);
			cell.setCellStyle(celula_fundo_branco_texto_preto);
			cell.setCellValue("Total Transferencia(+):");

			cell = row.createCell(1);
			cell.setCellStyle(valorStyleFundoVerdeTextoBranco);
			cell.setCellType(CellType.FORMULA);
			formula = "SUMPRODUCT(SUBTOTAL(9,OFFSET(D" + primeira_linha + ":D" + ultima_linha + ",ROW(D"
					+ primeira_linha + ":D" + ultima_linha + ")-ROW(D" + primeira_linha + "),0,1,1)),-(C"
					+ primeira_linha + ":C" + ultima_linha + "=\"+TRANSFERENCIA\")) * -1";
			cell.setCellFormula(formula);

			cell = row.createCell(2);
			cell.setCellStyle(celula_fundo_branco_texto_preto);
			cell.setCellValue("Cobertura Transferencia(+):");

			cell = row.createCell(3);
			cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
			cell.setCellType(CellType.FORMULA);
			formula = "SUMPRODUCT(SUBTOTAL(9,OFFSET(F" + primeira_linha + ":F" + ultima_linha + ",ROW(F"
					+ primeira_linha + ":F" + ultima_linha + ")-ROW(F" + primeira_linha + "),0,1,1)),-(C"
					+ primeira_linha + ":C" + ultima_linha + "=\"+TRANSFERENCIA\")) * -1";
			cell.setCellFormula(formula);

		}

		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		// somatoria de pg +transferencias
		cell = row.createCell(0);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue("Total já Pago:");

		cell = row.createCell(1);
		cell.setCellStyle(valorStyleFundoVerdeTextoBranco);
		cell.setCellType(CellType.FORMULA);
		formula = "SUM(D" + primeira_linha + ":D" + ultima_linha + ")";
		cell.setCellFormula(formula);

		cell = row.createCell(2);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue("Cobertura Paga:");

		cell = row.createCell(3);
		cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
		cell.setCellType(CellType.FORMULA);
		formula = "SUM(F" + primeira_linha + ":F" + ultima_linha + ")";
		cell.setCellFormula(formula);

		// somatoria da contratada
		row = sheet.createRow(rownum += 3);
		cellnum = 0;

		int celula_total_contratado = rownum + 1;
		cell = row.createCell(0);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue("Total Contratado:");

		cell = row.createCell(1);
		cell.setCellStyle(valorStyleFundoVerdeTextoBranco);
		cell.setCellValue(valor_total_contratado);

		cell = row.createCell(2);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue("Cobertura Contratada:");

		cell = row.createCell(3);
		cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
		cell.setCellValue(peso_total_cobertura_contratada);

		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		int celula_soma_final = rownum + 1;
		cell = row.createCell(0);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue("Total já Pago:");

		cell = row.createCell(1);
		cell.setCellStyle(valorStyleFundoVerdeTextoBranco);
		cell.setCellType(CellType.FORMULA);
		formula = "SUM(D" + primeira_linha + ":D" + ultima_linha + ")";
		cell.setCellFormula(formula);

		cell = row.createCell(2);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue("Cobertura Paga:");

		cell = row.createCell(3);
		cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
		cell.setCellType(CellType.FORMULA);
		formula = "SUM(F" + primeira_linha + ":F" + ultima_linha + ")";
		cell.setCellFormula(formula);

		// restante a pagar
		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		cell = row.createCell(0);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue("Valor a Pagar:");

		cell = row.createCell(1);
		cell.setCellStyle(valorStyleFundoVerdeTextoBranco);
		cell.setCellType(CellType.FORMULA);
		formula = "SUM(B" + celula_total_contratado + "-B" + celula_soma_final + ")";
		cell.setCellFormula(formula);

		cell = row.createCell(2);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue("Cobertura a Pagar:");

		cell = row.createCell(3);
		cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
		cell.setCellType(CellType.FORMULA);
		formula = "SUM(D" + celula_total_contratado + "-D" + celula_soma_final + ")";
		cell.setCellFormula(formula);

		// somatoria recebida

		// somatoria da contratada
		row = sheet.createRow(rownum += 3);
		cellnum = 0;

		int celula_total_recebido = rownum + 1;
		cell = row.createCell(0);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue("Total Recebido:");

		cell = row.createCell(1);
		cell.setCellStyle(valorStyleFundoVerdeTextoBranco);
		cell.setCellValue(valor_total_a_receber);

		cell = row.createCell(2);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue("Cobertura Recebida:");

		cell = row.createCell(3);
		cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
		cell.setCellValue(peso_total_cobertura_recebido);

		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		celula_soma_final = rownum + 1;
		cell = row.createCell(0);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue("Total já Pago:");

		cell = row.createCell(1);
		cell.setCellStyle(valorStyleFundoVerdeTextoBranco);
		cell.setCellType(CellType.FORMULA);
		formula = "SUM(D" + primeira_linha + ":D" + ultima_linha + ")";
		cell.setCellFormula(formula);

		cell = row.createCell(2);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue("Cobertura Paga:");

		cell = row.createCell(3);
		cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
		cell.setCellType(CellType.FORMULA);
		formula = "SUM(F" + primeira_linha + ":F" + ultima_linha + ")";
		cell.setCellFormula(formula);

		// restante a pagar
		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		cell = row.createCell(0);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue("Valor a Pagar:");

		cell = row.createCell(1);
		cell.setCellStyle(valorStyleFundoVerdeTextoBranco);
		cell.setCellType(CellType.FORMULA);
		formula = "SUM(B" + celula_total_recebido + "-B" + celula_soma_final + ")";
		cell.setCellFormula(formula);

		cell = row.createCell(2);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue("Cobertura a Pagar:");

		cell = row.createCell(3);
		cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
		cell.setCellType(CellType.FORMULA);
		formula = "SUM(D" + celula_total_recebido + "-D" + celula_soma_final + ")";
		cell.setCellFormula(formula);

		DadosTabelaExcel retornar = new DadosTabelaExcel();
		retornar.setWorkbook(workbook);
		retornar.setSheet(sheet);
		retornar.setRownum(rownum);

		for (int i = 0; i < 13; i++) {
			sheet.autoSizeColumn(i);

		}

		return retornar;

	}

	public DadosTabelaExcel criarTabelaPagamentosExcel(HSSFWorkbook workbook, HSSFSheet sheet, int rownum,
			ArrayList<PagamentoCompleto> pagamentos, CadastroContrato novo_contrato) {

		NumberFormat z = NumberFormat.getNumberInstance();

		Locale ptBr = new Locale("pt", "BR");

		HSSFDataFormat numberFormat = workbook.createDataFormat();

		HSSFFont newFont_branca = workbook.createFont();
		newFont_branca.setBold(true);
		newFont_branca.setColor(IndexedColors.WHITE.getIndex());
		newFont_branca.setFontName("Calibri");
		newFont_branca.setItalic(false);
		newFont_branca.setFontHeight((short) (11 * 20));

		// estilo para cabecalho fundo verde
		CellStyle celula_fundo_verde_texto_branco = workbook.createCellStyle();
		celula_fundo_verde_texto_branco.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		celula_fundo_verde_texto_branco.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		celula_fundo_verde_texto_branco.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_verde_texto_branco.setVerticalAlignment(VerticalAlignment.CENTER);

		celula_fundo_verde_texto_branco.setFont(newFont_branca);

		HSSFFont newFont_preta = workbook.createFont();
		newFont_preta.setColor(IndexedColors.BLACK.getIndex());
		newFont_preta.setFontName("Calibri");
		newFont_preta.setItalic(false);
		newFont_preta.setFontHeight((short) (11 * 20));

		CellStyle celula_fundo_branco_texto_preto = workbook.createCellStyle();
		celula_fundo_branco_texto_preto.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_branco_texto_preto.setVerticalAlignment(VerticalAlignment.CENTER);
		celula_fundo_branco_texto_preto.setFont(newFont_preta);

		// celula para numero alinhado ao centro
		CellStyle numberStyle = workbook.createCellStyle();
		numberStyle.setDataFormat(numberFormat.getFormat("R$ #,##0.00"));
		numberStyle.setAlignment(HorizontalAlignment.CENTER);
		numberStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		// celula para numero alinhado ao centro
		CellStyle pesoStyle = workbook.createCellStyle();
		pesoStyle.setDataFormat(numberFormat.getFormat("#,##0.00"));
		pesoStyle.setAlignment(HorizontalAlignment.CENTER);
		pesoStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle numberStyleFundoVerdeTextoBranco = workbook.createCellStyle();
		numberStyleFundoVerdeTextoBranco.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		numberStyleFundoVerdeTextoBranco.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		numberStyleFundoVerdeTextoBranco.setAlignment(HorizontalAlignment.CENTER);
		numberStyleFundoVerdeTextoBranco.setVerticalAlignment(VerticalAlignment.CENTER);
		numberStyleFundoVerdeTextoBranco.setDataFormat(numberFormat.getFormat("#,##0.00"));

		numberStyleFundoVerdeTextoBranco.setFont(newFont_branca);

		CellStyle valorStyleFundoVerdeTextoBranco = workbook.createCellStyle();
		valorStyleFundoVerdeTextoBranco.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		valorStyleFundoVerdeTextoBranco.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		valorStyleFundoVerdeTextoBranco.setAlignment(HorizontalAlignment.CENTER);
		valorStyleFundoVerdeTextoBranco.setVerticalAlignment(VerticalAlignment.CENTER);
		valorStyleFundoVerdeTextoBranco.setDataFormat(numberFormat.getFormat("R$ #,##0.00"));
		valorStyleFundoVerdeTextoBranco.setFont(newFont_branca);

		// celular de contrato normal
		HSSFFont newFont_verde = workbook.createFont();
		newFont_verde.setColor(IndexedColors.GREEN.getIndex());
		newFont_verde.setFontName("Calibri");
		newFont_verde.setItalic(false);
		newFont_verde.setFontHeight((short) (11 * 20));

		CellStyle celula_fundo_branco_texto_verde = workbook.createCellStyle();
		celula_fundo_branco_texto_verde.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_branco_texto_verde.setVerticalAlignment(VerticalAlignment.CENTER);
		celula_fundo_branco_texto_verde.setFont(newFont_verde);

		// celular de sub-contrato n
		HSSFFont newFont_vermelha = workbook.createFont();
		newFont_vermelha.setColor(IndexedColors.RED.getIndex());
		newFont_vermelha.setFontName("Calibri");
		newFont_vermelha.setItalic(false);
		newFont_vermelha.setFontHeight((short) (11 * 20));

		CellStyle celula_fundo_branco_texto_vermelho = workbook.createCellStyle();
		celula_fundo_branco_texto_vermelho.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_branco_texto_vermelho.setVerticalAlignment(VerticalAlignment.CENTER);
		celula_fundo_branco_texto_vermelho.setFont(newFont_vermelha);

		// celula de ganho potencial

		// celular de sub-contrato n
		HSSFFont newFont_azul = workbook.createFont();
		newFont_azul.setColor(IndexedColors.BLUE.getIndex());
		newFont_azul.setFontName("Calibri");
		newFont_azul.setItalic(false);
		newFont_azul.setFontHeight((short) (11 * 20));

		CellStyle celula_fundo_branco_texto_azul = workbook.createCellStyle();
		celula_fundo_branco_texto_azul.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_branco_texto_azul.setVerticalAlignment(VerticalAlignment.CENTER);
		celula_fundo_branco_texto_azul.setFont(newFont_azul);

		int cellnum = 0;

		Cell cell;
		Row row;

		cellnum = 0;
		row = sheet.createRow(rownum);
		//

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue(infoContrato(novo_contrato));
		sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 0, 6));

		rownum++;

		cellnum = 0;
		row = sheet.createRow(rownum);
		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue(novo_contrato.getNomes_compradores() + " X " + novo_contrato.getNomes_vendedores());
		sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 0, 6));

		rownum++;
		cellnum = 0;

		row = sheet.createRow(rownum);
		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("CONTRATO");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("DATA");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("TIPO");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("VALOR PAGAMENTO");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("VALOR UNIDADE");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("COBERTURA");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("DESCRIÇÃO");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("DEPOSITANTE");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("CONTA DEPOSITANTE");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("FAVORECIDO");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("CONTA FAVORECIDO");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("CONTRATO REMETENTE");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("CONTRATO DESTINATARIO");

		sheet.setAutoFilter(CellRangeAddress.valueOf("A" + (rownum) + ":M" + (rownum)));

		rownum++;

		int primeira_linha = rownum;
		int ultima_linha = primeira_linha;

		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();

		double valor_total_contratado = 0.0;
		double peso_total_cobertura_contratada = 0.0;

		double valor_total_a_receber = 0.0;
		double peso_total_cobertura_recebido = 0.0;

		if (novo_contrato.getMedida().equalsIgnoreCase("Sacos")) {
			peso_total_cobertura_contratada += novo_contrato.getQuantidade();

		} else if (novo_contrato.getMedida().equalsIgnoreCase("KG")) {
			double peso_em_sacos = novo_contrato.getQuantidade() / 60;
			peso_total_cobertura_contratada += peso_em_sacos;

		}

		valor_total_contratado += novo_contrato.getValor_a_pagar().doubleValue();

		double total_recebido = getPesoTotalRecebido(novo_contrato) / 60;
		peso_total_cobertura_recebido += (total_recebido);

		if (novo_contrato.getMedida().equalsIgnoreCase("Sacos")) {
			valor_total_a_receber += (total_recebido * novo_contrato.getValor_produto());

		} else if (novo_contrato.getMedida().equalsIgnoreCase("KG")) {
			double valor_por_saco = novo_contrato.getValor_produto() * 60;
			valor_total_a_receber += (total_recebido * valor_por_saco);

		}

		for (PagamentoCompleto pagamento : pagamentos) {

			if (pagamento.getTipo() == 1 || (pagamento.getTipo() == 2 && incluir_comissao_pagamento)
					|| (pagamento.getTipo() == 3 && incluir_transferencias_pagamentos)) {

				cellnum = 0;
				row = sheet.createRow(rownum);

				// celula ctr
				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				cell.setCellValue(pagamento.getContrato_remetente().getCodigo());

				// celula data
				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				cell.setCellValue(pagamento.getData_pagamento());

				double valor_por_saco = 0;
				// if(soma_total_pagamentos != 0)
				// novo_contrato = pagamento.getContrato_receptor();

				double quantidade_total_contrato_sacos = 0;

				if (novo_contrato.getMedida().equalsIgnoreCase("Kg")) {
					quantidade_total_contrato_sacos = novo_contrato.getQuantidade() / 60;
					valor_por_saco = novo_contrato.getValor_produto() * 60;
				} else if (novo_contrato.getMedida().equalsIgnoreCase("Sacos")) {
					quantidade_total_contrato_sacos = novo_contrato.getQuantidade();
					valor_por_saco = novo_contrato.getValor_produto();
				}
				GerenciarBancoContratos gerenciar_contratos = new GerenciarBancoContratos();

				int tipo = pagamento.getTipo();
				String s_tipo = "";
				if (pagamento.getTipo() == 1) {
					s_tipo = "NORMAL";
				} else if (pagamento.getTipo() == 2) {
					s_tipo = "COMISSÃO";
				} else if (pagamento.getTipo() == 3) {
					// é uma transferencia
					if (pagamento.getId_contrato_remetente() == novo_contrato.getId()) {
						s_tipo = "-TRANSFERENCIA";
					} else if (pagamento.getId_contrato_destinatario() == novo_contrato.getId()) {
						// é uma transferencia positiva
						s_tipo = "+TRANSFERENCIA";
					}

				}

				// celula tipo
				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				cell.setCellValue(s_tipo);

				// valor pagamento
				double valor_pagamento = pagamento.getValor_pagamento();

				String valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor_pagamento);
				double cobertura = valor_pagamento / valor_por_saco;

				if (pagamento.getTipo() == 1) {

				} else if (pagamento.getTipo() == 2) {
					// é uma comissão
					valor_pagamento = valor_pagamento * -1;

				} else if (pagamento.getTipo() == 3) {
					// é uma transferencia
					if (pagamento.getId_contrato_remetente() == novo_contrato.getId()) {
						// é uma transferencia negativa
						valor_pagamento = valor_pagamento * -1;
					} else if (pagamento.getId_contrato_destinatario() == novo_contrato.getId()) {
						// é uma transferencia positiva

					}

				}
				valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor_pagamento);

				// celula pagamento
				cell = row.createCell(cellnum++);
				cell.setCellStyle(numberStyle);
				cell.setCellValue(valor_pagamento);

				// valor da unidade
				CadastroContrato ct_remetente = pagamento.getContrato_remetente();
				CadastroContrato ct_destinatario = pagamento.getContrato_destinatario();

				double valor_produto = ct_remetente.getValor_produto();

				if (pagamento.getTipo() == 1) {

				} else if (pagamento.getTipo() == 2) {

				} else if (pagamento.getTipo() == 3) {
					// é uma transferencia
					if (pagamento.getId_contrato_remetente() == novo_contrato.getId()) {

					} else if (pagamento.getId_contrato_destinatario() == novo_contrato.getId()) {
						// é uma transferencia positiva
						// pegar o preco da unidade do contrato que recebeu a transferencia
						valor_produto = novo_contrato.getValor_produto();

					}
				}
				// celula valor unidade
				cell = row.createCell(cellnum++);
				cell.setCellStyle(numberStyle);
				cell.setCellValue(valor_produto);

				// cobertura
				cobertura = pagamento.getValor_pagamento() / ct_remetente.getValor_produto();
				if (ct_remetente.getMedida().equalsIgnoreCase("KG"))
					cobertura = cobertura / 60;

				if (pagamento.getTipo() == 1) {
					somatoria_cobertura_pagamentos += cobertura;
				} else if (pagamento.getTipo() == 2) {
					somatoria_cobertura_pagamentos += cobertura;
				} else if (pagamento.getTipo() == 3) {
					// é uma transferencia
					if (pagamento.getId_contrato_remetente() == novo_contrato.getId()) {
						cobertura = cobertura * -1;
						somatoria_cobertura_transferencias_negativas += cobertura;
					} else if (pagamento.getId_contrato_destinatario() == novo_contrato.getId()) {
						// é uma transferencia positiva
						// pegar o preco da unidade do contrato que recebeu a transferencia

						cobertura = pagamento.getValor_pagamento() / novo_contrato.getValor_produto();

						if (novo_contrato.getMedida().equalsIgnoreCase("KG"))
							cobertura = cobertura / 60;
						somatoria_cobertura_transferencias_positivas += cobertura;

					}

				}

				// celula cobertura
				cell = row.createCell(cellnum++);
				cell.setCellStyle(pesoStyle);
				cell.setCellValue(cobertura);

				// celula descricao
				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				cell.setCellValue(pagamento.getDescricao());

				// celula depositante
				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				cell.setCellValue(pagamento.getDepositante().toUpperCase());

				// celula conta depositante
				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				cell.setCellValue(pagamento.getConta_bancaria_depositante().toUpperCase());

				// celula favorecido
				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);
				cell.setCellValue(pagamento.getFavorecido().toUpperCase());

				// celula conta favorecido
				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_branco_texto_preto);

				if (pagamento.getConta_bancaria_favorecido() != null) {
					cell.setCellValue(pagamento.getConta_bancaria_favorecido().toUpperCase());

				} else {
					cell.setCellValue("");

				}

				if (pagamento.getTipo() == 1) {
					// celula contrato remetente
					cell = row.createCell(cellnum++);
					cell.setCellStyle(celula_fundo_branco_texto_preto);
					cell.setCellValue("");

					// celula contrato destinataio
					cell = row.createCell(cellnum++);
					cell.setCellStyle(celula_fundo_branco_texto_preto);
					cell.setCellValue("");

				} else {
					// celula contrato remetente
					cell = row.createCell(cellnum++);
					cell.setCellStyle(celula_fundo_branco_texto_preto);
					cell.setCellValue(ct_remetente.getCodigo());

					// celula contrato destinataio
					cell = row.createCell(cellnum++);
					cell.setCellStyle(celula_fundo_branco_texto_preto);
					cell.setCellValue(ct_destinatario.getCodigo());

				}
				rownum++;
				ultima_linha = rownum;

			}

		}

		// somatorias
		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		// somatoria de valores
		cell = row.createCell(2);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue("Valor Total:");

		cell = row.createCell(3);
		cell.setCellStyle(valorStyleFundoVerdeTextoBranco);
		cell.setCellType(CellType.FORMULA);
		String formula = "SUM(D" + primeira_linha + ":D" + ultima_linha + ")";
		cell.setCellFormula(formula);

		cell = row.createCell(4);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue("Cobertura Total:");

		cell = row.createCell(5);
		cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
		cell.setCellType(CellType.FORMULA);
		formula = "SUM(F" + primeira_linha + ":F" + ultima_linha + ")";
		cell.setCellFormula(formula);

		row = sheet.createRow(rownum += 3);
		cellnum = 0;

		// somatoria de pg normal
		cell = row.createCell(0);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue("Total Normal:");

		cell = row.createCell(1);
		cell.setCellStyle(valorStyleFundoVerdeTextoBranco);
		cell.setCellType(CellType.FORMULA);
		formula = "SUMPRODUCT(SUBTOTAL(9,OFFSET(D" + primeira_linha + ":D" + ultima_linha + ",ROW(D" + primeira_linha
				+ ":D" + ultima_linha + ")-ROW(D" + primeira_linha + "),0,1,1)),-(C" + primeira_linha + ":C"
				+ ultima_linha + "=\"Normal\")) * -1";
		cell.setCellFormula(formula);

		cell = row.createCell(2);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue("Cobertura Normal:");

		cell = row.createCell(3);
		cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
		cell.setCellType(CellType.FORMULA);
		formula = "SUMPRODUCT(SUBTOTAL(9,OFFSET(F" + primeira_linha + ":F" + ultima_linha + ",ROW(F" + primeira_linha
				+ ":F" + ultima_linha + ")-ROW(F" + primeira_linha + "),0,1,1)),-(C" + primeira_linha + ":C"
				+ ultima_linha + "=\"Normal\")) * -1";
		cell.setCellFormula(formula);

		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		if (incluir_transferencias_pagamentos) {
			// somatoria de pg -transferencias
			cell = row.createCell(0);
			cell.setCellStyle(celula_fundo_branco_texto_preto);
			cell.setCellValue("Total Transferencia(-):");

			cell = row.createCell(1);
			cell.setCellStyle(valorStyleFundoVerdeTextoBranco);
			cell.setCellType(CellType.FORMULA);
			formula = "SUMPRODUCT(SUBTOTAL(9,OFFSET(D" + primeira_linha + ":D" + ultima_linha + ",ROW(D"
					+ primeira_linha + ":D" + ultima_linha + ")-ROW(D" + primeira_linha + "),0,1,1)),-(C"
					+ primeira_linha + ":C" + ultima_linha + "=\"-TRANSFERENCIA\")) *-1";
			cell.setCellFormula(formula);

			cell = row.createCell(2);
			cell.setCellStyle(celula_fundo_branco_texto_preto);
			cell.setCellValue("Cobertura Transferencia(-):");

			cell = row.createCell(3);
			cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
			cell.setCellType(CellType.FORMULA);
			formula = "SUMPRODUCT(SUBTOTAL(9,OFFSET(F" + primeira_linha + ":F" + ultima_linha + ",ROW(F"
					+ primeira_linha + ":F" + ultima_linha + ")-ROW(F" + primeira_linha + "),0,1,1)),-(C"
					+ primeira_linha + ":C" + ultima_linha + "=\"-TRANSFERENCIA\")) *-1";
			cell.setCellFormula(formula);

			row = sheet.createRow(rownum += 1);
			cellnum = 0;

			// somatoria de pg +transferencias
			cell = row.createCell(0);
			cell.setCellStyle(celula_fundo_branco_texto_preto);
			cell.setCellValue("Total Transferencia(+):");

			cell = row.createCell(1);
			cell.setCellStyle(valorStyleFundoVerdeTextoBranco);
			cell.setCellType(CellType.FORMULA);
			formula = "SUMPRODUCT(SUBTOTAL(9,OFFSET(D" + primeira_linha + ":D" + ultima_linha + ",ROW(D"
					+ primeira_linha + ":D" + ultima_linha + ")-ROW(D" + primeira_linha + "),0,1,1)),-(C"
					+ primeira_linha + ":C" + ultima_linha + "=\"+TRANSFERENCIA\")) * -1";
			cell.setCellFormula(formula);

			cell = row.createCell(2);
			cell.setCellStyle(celula_fundo_branco_texto_preto);
			cell.setCellValue("Cobertura Transferencia(+):");

			cell = row.createCell(3);
			cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
			cell.setCellType(CellType.FORMULA);
			formula = "SUMPRODUCT(SUBTOTAL(9,OFFSET(F" + primeira_linha + ":F" + ultima_linha + ",ROW(F"
					+ primeira_linha + ":F" + ultima_linha + ")-ROW(F" + primeira_linha + "),0,1,1)),-(C"
					+ primeira_linha + ":C" + ultima_linha + "=\"+TRANSFERENCIA\")) * -1";
			cell.setCellFormula(formula);
		}

		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		// somatoria de pg +transferencias
		cell = row.createCell(0);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue("Total já Pago:");

		cell = row.createCell(1);
		cell.setCellStyle(valorStyleFundoVerdeTextoBranco);
		cell.setCellType(CellType.FORMULA);
		formula = "SUM(D" + primeira_linha + ":D" + ultima_linha + ")";
		cell.setCellFormula(formula);

		cell = row.createCell(2);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue("Cobertura Paga:");

		cell = row.createCell(3);
		cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
		cell.setCellType(CellType.FORMULA);
		formula = "SUM(F" + primeira_linha + ":F" + ultima_linha + ")";
		cell.setCellFormula(formula);

		// somatoria da contratada
		row = sheet.createRow(rownum += 3);
		cellnum = 0;

		int celula_total_contratado = rownum + 1;
		cell = row.createCell(0);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue("Total Contratado:");

		cell = row.createCell(1);
		cell.setCellStyle(valorStyleFundoVerdeTextoBranco);
		cell.setCellValue(valor_total_contratado);

		cell = row.createCell(2);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue("Cobertura Contratada:");

		cell = row.createCell(3);
		cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
		cell.setCellValue(peso_total_cobertura_contratada);

		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		int celula_soma_final = rownum + 1;
		cell = row.createCell(0);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue("Total já Pago:");

		cell = row.createCell(1);
		cell.setCellStyle(valorStyleFundoVerdeTextoBranco);
		cell.setCellType(CellType.FORMULA);
		formula = "SUM(D" + primeira_linha + ":D" + ultima_linha + ")";
		cell.setCellFormula(formula);

		cell = row.createCell(2);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue("Cobertura Paga:");

		cell = row.createCell(3);
		cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
		cell.setCellType(CellType.FORMULA);
		formula = "SUM(F" + primeira_linha + ":F" + ultima_linha + ")";
		cell.setCellFormula(formula);

		// restante a pagar
		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		cell = row.createCell(0);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue("Valor a Pagar:");

		cell = row.createCell(1);
		cell.setCellStyle(valorStyleFundoVerdeTextoBranco);
		cell.setCellType(CellType.FORMULA);
		formula = "SUM(B" + celula_total_contratado + "-B" + celula_soma_final + ")";
		cell.setCellFormula(formula);

		cell = row.createCell(2);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue("Cobertura a Pagar:");

		cell = row.createCell(3);
		cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
		cell.setCellType(CellType.FORMULA);
		formula = "SUM(D" + celula_total_contratado + "-D" + celula_soma_final + ")";
		cell.setCellFormula(formula);

		// somatoria recebida

		// somatoria da contratada
		row = sheet.createRow(rownum += 3);
		cellnum = 0;

		int celula_total_recebido = rownum + 1;
		cell = row.createCell(0);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue("Total Recebido:");

		cell = row.createCell(1);
		cell.setCellStyle(valorStyleFundoVerdeTextoBranco);
		cell.setCellValue(valor_total_a_receber);

		cell = row.createCell(2);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue("Cobertura Recebida:");

		cell = row.createCell(3);
		cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
		cell.setCellValue(peso_total_cobertura_recebido);

		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		celula_soma_final = rownum + 1;
		cell = row.createCell(0);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue("Total já Pago:");

		cell = row.createCell(1);
		cell.setCellStyle(valorStyleFundoVerdeTextoBranco);
		cell.setCellType(CellType.FORMULA);
		formula = "SUM(D" + primeira_linha + ":D" + ultima_linha + ")";
		cell.setCellFormula(formula);

		cell = row.createCell(2);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue("Cobertura Paga:");

		cell = row.createCell(3);
		cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
		cell.setCellType(CellType.FORMULA);
		formula = "SUM(F" + primeira_linha + ":F" + ultima_linha + ")";
		cell.setCellFormula(formula);

		// restante a pagar
		row = sheet.createRow(rownum += 1);
		cellnum = 0;

		cell = row.createCell(0);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue("Valor a Pagar:");

		cell = row.createCell(1);
		cell.setCellStyle(valorStyleFundoVerdeTextoBranco);
		cell.setCellType(CellType.FORMULA);
		formula = "SUM(B" + celula_total_recebido + "-B" + celula_soma_final + ")";
		cell.setCellFormula(formula);

		cell = row.createCell(2);
		cell.setCellStyle(celula_fundo_branco_texto_preto);
		cell.setCellValue("Cobertura a Pagar:");

		cell = row.createCell(3);
		cell.setCellStyle(numberStyleFundoVerdeTextoBranco);
		cell.setCellType(CellType.FORMULA);
		formula = "SUM(D" + celula_total_recebido + "-D" + celula_soma_final + ")";
		cell.setCellFormula(formula);

		DadosTabelaExcel retornar = new DadosTabelaExcel();
		retornar.setWorkbook(workbook);
		retornar.setSheet(sheet);
		retornar.setRownum(rownum);

		for (int i = 0; i < 13; i++) {
			sheet.autoSizeColumn(i);

		}

		return retornar;

	}
	
	
	public double getPesoTotalRecebido(CadastroContrato contrato) {
		double peso_total_recebido = 0.0, peso_total_trans_negativo = 0.0, peso_total_trans_positivo = 0.0;

		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
		ArrayList<CadastroContrato.Recebimento> lista_recebimentos_local = gerenciar.getRecebimentos(contrato.getId());

		GerenciarBancoTransferenciaRecebimento gerenciar_transferencias = new GerenciarBancoTransferenciaRecebimento();

		ArrayList<CadastroContrato.CadastroTransferenciaRecebimento> lista_transferencias_recebimento_remetente_local = gerenciar_transferencias
				.getTransferenciasRemetente(contrato.getId());

		ArrayList<CadastroContrato.CadastroTransferenciaRecebimento> lista_transferencias_recebimento_destinatario_local = gerenciar_transferencias
				.getTransferenciaDestinatario(contrato.getId());

		for (CadastroContrato.Recebimento recebimento : lista_recebimentos_local) {
			if (recebimento != null)
				peso_total_recebido = peso_total_recebido + recebimento.getPeso_romaneio();
		}
		for (CadastroContrato.CadastroTransferenciaRecebimento enviado_via_trans : lista_transferencias_recebimento_remetente_local) {
			peso_total_trans_negativo += enviado_via_trans.getQuantidade();
		}

		for (CadastroContrato.CadastroTransferenciaRecebimento recebido_via_trans : lista_transferencias_recebimento_destinatario_local) {
			peso_total_trans_positivo = peso_total_trans_positivo + recebido_via_trans.getQuantidade();
		}

		return (peso_total_recebido + peso_total_trans_positivo - peso_total_trans_negativo);

	}

	

}
