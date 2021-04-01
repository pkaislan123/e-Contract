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
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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
import org.apache.poi.xwpf.usermodel.XWPFChart;
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
import main.java.relatoria.RelatorioContratoSimplificado;
import main.java.relatoria.RelatorioContratos;
import main.java.tratamento_proprio.Log;
import main.java.views_personalizadas.TelaEmEspera;
import main.java.views_personalizadas.TelaNotificacao;
import main.java.views_personalizadas.TelaNotificacaoSuperior;
import main.java.views_personalizadas.TelaNotificacaoSuperiorModoBusca;
import outros.ValidaCNPJ;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroNuvem;
import main.java.cadastros.CadastroZapMessenger;
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

	private TelaEmEspera telaInformacoes;
	private boolean recebimentos_unidos_como_comprador = false;
	private boolean carregamentos_unidos_como_comprador = false;
	private boolean pagamentos_unidos_como_comprador = false;
	private boolean recebimentos_unidos_como_vendedor = false;
	private boolean carregamentos_unidos_como_vendedor = false;
	private boolean pagamentos_unidos_como_vendedor = false;
	private boolean incluir_transferencias_carregamentos = false;
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

	public boolean isContrato_irmao() {
		return contrato_irmao;
	}

	public void setContrato_irmao(boolean contrato_irmao) {
		this.contrato_irmao = contrato_irmao;
	}

	public RelatorioContratos(int _tipo_contrato, boolean _contrato, boolean _unir_contratos, boolean _contrato_como_comprador,
			boolean _pagamento, boolean _pagamento_como_depositante, boolean _pagamento_como_favorecido,
			boolean _incluir_sem_pagamentos, boolean _carregamento, boolean _carregamento_como_comprador,
			boolean _carregamento_como_vendedor, boolean _unir_carregamentos, boolean _controle_nf_venda_carregamentos,
			boolean _incluir_transferencias_carregamentos, boolean _incluir_sem_carregamentos, boolean _recebimento,
			boolean _recebimento_como_comprador, boolean _recebimento_como_vendedor, boolean _unir_recebimentos,
			boolean _controle_nf_venda_recebimentos, boolean _incluir_sem_recebimentos, int _id_safra,
			boolean _sub_contratos, boolean _incluir_comissao, boolean _incluir_ganhos_potenciais,
			boolean _somar_sub_contratos, ArrayList<CadastroCliente> _clientes_globais, CadastroGrupo _grupo_alvo) {

		getDadosGlobais();
		this.modelo = modelo;
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

	public ByteArrayOutputStream preparar() {

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

		String texto_pesquisa = "Busca por: \n";
		
		if(id_safra == 0) {
			texto_pesquisa = texto_pesquisa + "Safra: -TODAS AS SAFRAS- ";
		
			}else {
				GerenciarBancoSafras gerenciar = new GerenciarBancoSafras();
				CadastroSafra safra = gerenciar.getSafra(id_safra);
				String text_safra = safra.getProduto().getNome_produto() + " " + safra.getProduto().getTransgenia() + " " +
				safra.getAno_plantio() + "/" + safra.getAno_colheita();
				texto_pesquisa = texto_pesquisa + "Safra: " + text_safra;

			}
		
		texto_pesquisa = texto_pesquisa + "\n";
		
		if(contrato) {
			texto_pesquisa = texto_pesquisa + "\n*Contratos";
			if(contrato_como_comprador) {
				texto_pesquisa = texto_pesquisa + " como Comprador\n";

			}else {
				texto_pesquisa = texto_pesquisa + " como Vendedor\n";

			}
			//parametros
			texto_pesquisa = texto_pesquisa + "   ->Filtros: " ;

			if(unir_contratos)
				texto_pesquisa = texto_pesquisa + " |Unir os Contratos| ";
			if(sub_contratos)
				texto_pesquisa = texto_pesquisa + " |Incluir Sub-Contratos na Tabela| ";
			if(incluir_comissao)
				texto_pesquisa = texto_pesquisa + " |Incluir Coluna de Comissão| ";
			if(incluir_ganhos_potencias)
				texto_pesquisa = texto_pesquisa + " |Incluir Linhas e Tabela de Ganho Potencial| ";

		}
		texto_pesquisa = texto_pesquisa + "\n\n" ;


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
			
			//parametros
			texto_pesquisa = texto_pesquisa + "\n   ->Filtros: " ;

			if(unir_recebimentos)
				texto_pesquisa = texto_pesquisa + " |Unir Recebimentos| ";
			if(incluir_sem_recebimentos)
				texto_pesquisa = texto_pesquisa + " |Incluir Contratos Sem Recebimentos| ";
			if(controle_nf_venda_recebimentos)
				texto_pesquisa = texto_pesquisa + " |Incluir Tabela Controle NF Venda(Entrada)| ";
		
		}
		texto_pesquisa = texto_pesquisa + "\n\n";

		
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
			
			//parametros
			texto_pesquisa = texto_pesquisa + "\n   ->Filtros: " ;

			if(unir_carregamentos)
				texto_pesquisa = texto_pesquisa + " |Unir Carregamentos| ";
			if(incluir_sem_carregamentos)
				texto_pesquisa = texto_pesquisa + " |Incluir Contratos Sem Carregamentos| ";
			if(controle_nf_venda_carregamentos)
				texto_pesquisa = texto_pesquisa + " |Incluir Tabela Controle NF Venda(Saída)| ";
			if(incluir_transferencias_carregamentos)
				texto_pesquisa = texto_pesquisa + " |Incluir Transferencias de Carregamento entre Contratos| ";
			
			

		}
		texto_pesquisa = texto_pesquisa + "\n\n";
		
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
			texto_pesquisa = texto_pesquisa + "\n   ->Filtros: " ;

			//if(unir_pagamentos)
				texto_pesquisa = texto_pesquisa + " |Unir Pagamentos| ";
			if(incluir_sem_pagamentos)
				texto_pesquisa = texto_pesquisa + " |Incluir Contratos Sem Pagamentos| ";
			if(incluir_transferencias_pagamentos)
				texto_pesquisa = texto_pesquisa + " |Incluir Transferencias de Pagamentos entre Contratos| ";
			
		}
		texto_pesquisa = texto_pesquisa + "\n\n";

		dadosPesquisaRun.setText("Filtros da pesquisa: \n");
		dadosPesquisaRun.setColor("000000");
		dadosPesquisaRun.setBold(false);
		dadosPesquisaRun.setFontFamily("Arial");
		dadosPesquisaRun.setFontSize(10);
		substituirTexto(texto_pesquisa + "\n\n",  -1);
		substituirTexto("",  -1);

		GerenciarBancoContratos procura_contratos_grupo = new GerenciarBancoContratos();
		if (grupo_alvo_global != null) {

			int num_total_contratos = 0;
			double quantidade_total_sacos = 0;
			BigDecimal valor_total_pagamentos = BigDecimal.ZERO;

			// quantidade total de sacos do grupo
			for (CadastroCliente cliente : grupo_alvo_global.getClientes()) {
				// numero de contratos desde clinete
				ArrayList<CadastroContrato> lista_contratos= new ArrayList<>();

				if (contrato_como_comprador) {
					// //JOptionPane.showMessageDialog(null, "Tipo do contrato: " + tipo_contrato);
					if (tipo_contrato == 1) {
						lista_contratos = procura_contratos_grupo.getContratosPorCliente(4, id_safra,
								cliente.getId());
					} else if (tipo_contrato == 2) {
						lista_contratos = procura_contratos_grupo.getContratosPorCliente(4, id_safra,
								cliente.getId());
					}
				} else {
					// //JOptionPane.showMessageDialog(null, "Pesquisa como vendedor");

					// //JOptionPane.showMessageDialog(null, "Tipo do contrato: " + tipo_contrato);
					if (tipo_contrato == 1) {
						lista_contratos = procura_contratos_grupo.getContratosPorCliente(5, id_safra,
								cliente.getId());
					} else if (tipo_contrato == 2) {
						lista_contratos = procura_contratos_grupo.getContratosPorCliente(5, id_safra,
								cliente.getId());
					}

				}

				ArrayList<CadastroContrato> lista_final = new ArrayList<>();

				lista_final = new ArrayList(lista_contratos.size());

				if (lista_contratos.size() > 0) {
					lista_final.addAll(lista_contratos);

				}

				num_total_contratos += lista_final.size();
				// quantidade total de sacas
				for (CadastroContrato contrato : lista_final) {
					quantidade_total_sacos += contrato.getQuantidade();
					valor_total_pagamentos = valor_total_pagamentos.add(contrato.getValor_a_pagar());
				}

			}

			String text = "";
			text = text + "Grupo: " + grupo_alvo_global.getNome_grupo().toUpperCase();
			text = text + "\nQuantidade de Contratos: " + num_total_contratos;
			text = text + "\nTotal de Sacos: " + z.format(quantidade_total_sacos);

			// adicionar integrantes
			text = text + "\nIntegrantes: ";
			for (CadastroCliente cliente : grupo_alvo_global.getClientes()) {
				if (cliente.getTipo_pessoa() == 0) {
					text = text + cliente.getNome_empresarial().toUpperCase();
				} else {
					text = text + cliente.getNome_fantaia().toUpperCase();

				}
				text = text + " , ";
			}

			String valorTotalString = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos);
			text = text + "\nValor Total: " + valorTotalString;
			substituirTexto(text, -1);
			
			substituirTexto("", -1);

			criarTabelaInfoGrupo(num_total_contratos, quantidade_total_sacos, valor_total_pagamentos);
			substituirTexto("", -1);

		}

        {
        	
        	ArrayList<CadastroContrato> contratos_unidos = new ArrayList<>();	
        	int numero_clientes = clientes_globais.size();
        	int contador_clientes = 0;
		
		for (CadastroCliente cliente_alvo_global : clientes_globais) {
			String nome_cliente;

			
			if(unir_contratos && grupo_alvo_global != null) {
				nome_cliente = grupo_alvo_global.getNome_grupo();
			}else {
			if (cliente_alvo_global.getTipo_pessoa() == 0) {
				// pessoa fisica
				nome_cliente = cliente_alvo_global.getNome_empresarial();
			} else {
				nome_cliente = cliente_alvo_global.getNome_fantaia();
			}
			}

			/************************inicio contratos como vendedor ***********************************/

			if (contrato && !contrato_como_comprador) {
				// obter contratos desde cliente
				GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();

				// //JOptionPane.showMessageDialog(null, "Pesquisa como vendedor");

				// //JOptionPane.showMessageDialog(null, "Tipo do contrato: " + tipo_contrato);

				

				ArrayList<CadastroContrato> lista_final = new ArrayList<>();

				if (tipo_contrato == 1) {
					if (sub_contratos) {

						ArrayList<CadastroContrato> lista_contratos_como_vendedor = new ArrayList<>();
						lista_contratos_como_vendedor = procura_contratos_grupo.getContratosPorCliente(5, id_safra,
								cliente_alvo_global.getId());

						if (lista_contratos_como_vendedor.size() > 0) {

							// verifica pelos subcontratos de cada contrato retornado
							for (CadastroContrato contrato_encontrato_na_busca : lista_contratos_como_vendedor) {
								lista_final.add(contrato_encontrato_na_busca);

								ArrayList<CadastroContrato> sub_contratos = procura_contratos_grupo
										.getSubContratos(contrato_encontrato_na_busca.getId());

								BigDecimal somatoria_valor_sub_contrato = BigDecimal.ZERO;

								if (sub_contratos.size() > 0) {
									// existem subcontratos nesse contrato
									for (CadastroContrato sub : sub_contratos) {
										somatoria_valor_sub_contrato = somatoria_valor_sub_contrato
												.add(sub.getValor_a_pagar());
									
										lista_final.add(sub);
									}
									
									

									if (incluir_ganhos_potencias) {
										CadastroContrato linha_ganho_potencial = new CadastroContrato();
										linha_ganho_potencial
												.setValor_a_pagar(contrato_encontrato_na_busca.getValor_a_pagar());
										linha_ganho_potencial.setCodigo(contrato_encontrato_na_busca.getCodigo());
										linha_ganho_potencial.setValor_comissao(somatoria_valor_sub_contrato);
										linha_ganho_potencial.setSub_contrato(8);
										
										//seta o valor da comisao
										if (contrato_encontrato_na_busca.getValor_comissao() != null) {
											
											linha_ganho_potencial.setValor_produto(contrato_encontrato_na_busca.getValor_comissao().doubleValue());
										}else {
											linha_ganho_potencial.setValor_produto(0);
										}
											
									
										lista_final.add(linha_ganho_potencial);

									}

								}

							}
						}
					} else {
						// relatorio interno  sem subcontratos
						ArrayList<CadastroContrato> lista_contratos_como_vendedor = new ArrayList<>();
						lista_contratos_como_vendedor = procura_contratos_grupo.getContratosPorCliente(5, id_safra,
								cliente_alvo_global.getId());
						lista_final = lista_contratos_como_vendedor;
					}
					
					
					
				} else {
					// relatorio externo
					this.incluir_comissao = false;
					this.somar_sub_contratos = false;
					this.contrato_irmao = false;
					ArrayList<CadastroContrato> lista_contratos_como_vendedor = new ArrayList<>();
					lista_contratos_como_vendedor = procura_contratos_grupo.getContratosPorCliente(5, id_safra,
							cliente_alvo_global.getId());
					// contrato externo
					//JOptionPane.showMessageDialog(null, "Relatorio Externo");
					// verifica se este contrato e um subcontrato
					lista_final = lista_contratos_como_vendedor;
				}
				
				if(!unir_contratos) {
					if (lista_final.size() > 0) {

						XWPFParagraph titulo_contratos = document_global.createParagraph();
						titulo_contratos.setAlignment(ParagraphAlignment.CENTER);

						XWPFRun titulo_contratosRun = titulo_contratos.createRun();
						titulo_contratosRun.setText("\nContratos");
						titulo_contratosRun.setColor("000000");
						titulo_contratosRun.setBold(true);
						titulo_contratosRun.setFontFamily("Arial");
						titulo_contratosRun.setFontSize(10);

						substituirTexto("\nCliente: " + nome_cliente.toUpperCase() + "\n\n", 1);

						criarTabelaContrato(lista_final);

					}
					}else {
						contratos_unidos.addAll(lista_final);
					}

				/************************fim contratos como vendedor ***********************************/

/************************contratos como comprador ***********************************/
			} else if (contrato && contrato_como_comprador) {
				// obter contratos desde cliente como comprador
				GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();

				//JOptionPane.showMessageDialog(null, "Pesquisa como comprador");

				//JOptionPane.showMessageDialog(null, "Tipo do contrato: " + tipo_contrato);

				ArrayList<CadastroContrato> lista_final = new ArrayList<>();

				if (tipo_contrato == 1) {
					if (sub_contratos) {

						ArrayList<CadastroContrato> lista_contratos_como_comprador = new ArrayList<>();
						lista_contratos_como_comprador = procura_contratos_grupo.getContratosPorCliente(4, id_safra,
								cliente_alvo_global.getId());

						if (lista_contratos_como_comprador.size() > 0) {

							// verifica pelos subcontratos de cada contrato retornado
							for (CadastroContrato contrato_encontrato_na_busca : lista_contratos_como_comprador) {
								lista_final.add(contrato_encontrato_na_busca);

								ArrayList<CadastroContrato> sub_contratos = procura_contratos_grupo
										.getSubContratos(contrato_encontrato_na_busca.getId());

								BigDecimal somatoria_valor_sub_contrato = BigDecimal.ZERO;

								if (sub_contratos.size() > 0) {
									// existem subcontratos nesse contrato
									for (CadastroContrato sub : sub_contratos) {
										somatoria_valor_sub_contrato = somatoria_valor_sub_contrato
												.add(sub.getValor_a_pagar());
									
										lista_final.add(sub);
									}
									
									

									if (incluir_ganhos_potencias) {
										CadastroContrato linha_ganho_potencial = new CadastroContrato();
										linha_ganho_potencial
												.setValor_a_pagar(contrato_encontrato_na_busca.getValor_a_pagar());
										linha_ganho_potencial.setCodigo(contrato_encontrato_na_busca.getCodigo());
										linha_ganho_potencial.setValor_comissao(somatoria_valor_sub_contrato);
										linha_ganho_potencial.setSub_contrato(8);
										
										//seta o valor da comisao
										if (contrato_encontrato_na_busca.getValor_comissao() != null) {
											
											linha_ganho_potencial.setValor_produto(contrato_encontrato_na_busca.getValor_comissao().doubleValue());
										}else {
											linha_ganho_potencial.setValor_produto(0);
										}
											
									
										lista_final.add(linha_ganho_potencial);

									}

								}

							}
						}
					} else {
						// relatorio externo sem subcontratos
						ArrayList<CadastroContrato> lista_contratos_como_comprador = new ArrayList<>();
						lista_contratos_como_comprador = procura_contratos_grupo.getContratosPorCliente(4, id_safra,
								cliente_alvo_global.getId());
						lista_final = lista_contratos_como_comprador;
					}
				} else {
					// relatorio externo
					this.incluir_comissao = false;
					this.somar_sub_contratos = false;
					this.contrato_irmao = false;
					ArrayList<CadastroContrato> lista_contratos_como_comprador = new ArrayList<>();
					lista_contratos_como_comprador = procura_contratos_grupo.getContratosPorCliente(4, id_safra,
							cliente_alvo_global.getId());
					// contrato externo
					//JOptionPane.showMessageDialog(null, "Relatorio Externo");
					// verifica se este contrato e um subcontrato
					lista_final = lista_contratos_como_comprador;
				}

				if(!unir_contratos) {
				if (lista_final.size() > 0) {

					XWPFParagraph titulo_contratos = document_global.createParagraph();
					titulo_contratos.setAlignment(ParagraphAlignment.CENTER);

					XWPFRun titulo_contratosRun = titulo_contratos.createRun();
					titulo_contratosRun.setText("\nContratos");
					titulo_contratosRun.setColor("000000");
					titulo_contratosRun.setBold(true);
					titulo_contratosRun.setFontFamily("Arial");
					titulo_contratosRun.setFontSize(10);

					substituirTexto("\nCliente: " + nome_cliente.toUpperCase() + "\n\n", 1);

					criarTabelaContrato(lista_final);

				}
				}else {
					contratos_unidos.addAll(lista_final);
				}
			}
		    }
		
		
				if(unir_contratos && contrato) {
					if (contratos_unidos.size() > 0) {

						XWPFParagraph titulo_contratos = document_global.createParagraph();
						titulo_contratos.setAlignment(ParagraphAlignment.CENTER);

						XWPFRun titulo_contratosRun = titulo_contratos.createRun();
						titulo_contratosRun.setText("\nContratos");
						titulo_contratosRun.setColor("000000");
						titulo_contratosRun.setBold(true);
						titulo_contratosRun.setFontFamily("Arial");
						titulo_contratosRun.setFontSize(10);

						substituirTexto("\nCliente: " + grupo_alvo_global.getNome_grupo().toUpperCase() + "\n\n", 1);

						criarTabelaContrato(contratos_unidos);

					}
				}
			
		
			for (CadastroCliente cliente_alvo_global : clientes_globais) {
				String nome_cliente;
				if (cliente_alvo_global.getTipo_pessoa() == 0) {
					// pessoa fisica
					nome_cliente = cliente_alvo_global.getNome_empresarial();
				} else {
					nome_cliente = cliente_alvo_global.getNome_fantaia();
				}
			// carregamentos
			GerenciarBancoContratos gerenciar_carregamentos = new GerenciarBancoContratos();

			// recebimentos
			GerenciarBancoContratos gerenciar_recebimentos = new GerenciarBancoContratos();

			// obter contratos desde cliente
			GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();

			// contratos por comprador
			ArrayList<CadastroContrato> contratos_deste_cliente_comprador = gerenciar_recebimentos
					.getContratosPorCliente(1, id_safra, cliente_alvo_global.getId());

			// contratos por vendedor
			ArrayList<CadastroContrato> contratos_deste_cliente_vendedor = gerenciar_recebimentos
					.getContratosPorCliente(2, id_safra, cliente_alvo_global.getId());

			if (recebimento_como_comprador || carregamento_como_comprador || pagamento_como_comprador) {
				
				for (CadastroContrato contrato_cliente : contratos_deste_cliente_comprador) {
					if (!recebimentos_unidos_como_comprador && recebimento)
						adicionarTraco(true, 0);

					if (recebimento && recebimento_como_comprador && !unir_recebimentos) {
						if(contrato_cliente.getSub_contrato() == 0 || contrato_cliente.getSub_contrato() == 4 || contrato_cliente.getSub_contrato() == 3) {

						ArrayList<CadastroContrato.Recebimento> recebimentos_totais = new ArrayList<>();

						ArrayList<CadastroContrato.Recebimento> recebimentos_locais = gerenciar_recebimentos
								.getRecebimentos(contrato_cliente.getId());
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

							criarTabelaRecebimentos(recebimentos_totais, 0);
							// controle de nfs
							inserirControleNFVendaEntrada(recebimentos_totais);
						} else {
							if (incluir_sem_recebimentos)
								semRecebimentos(contrato_cliente);
						}
					}//fim do if de contrato_original
					} else {
						if (unir_recebimentos) {
							if (!recebimentos_unidos_como_comprador) {
								double soma_total_quantidade_contratos_kgs = 0;
								ArrayList<CadastroContrato.Recebimento> recebimentos_totais = new ArrayList<>();

								for (CadastroContrato contrato : contratos_deste_cliente_comprador) {
									if(contrato_cliente.getSub_contrato() == 0 || contrato_cliente.getSub_contrato() == 4 || contrato_cliente.getSub_contrato() == 3) {

									ArrayList<CadastroContrato.Recebimento> recebimentos_locais = gerenciar_recebimentos
											.getRecebimentos(contrato.getId());
									recebimentos_totais.addAll(recebimentos_locais);

									double local_quantidade_kgs = 0;

									if (contrato.getMedida().equalsIgnoreCase("KG")) {
										local_quantidade_kgs = contrato.getQuantidade();
									} else if (contrato.getMedida().equalsIgnoreCase("Sacos")) {
										local_quantidade_kgs = contrato.getQuantidade() * 60;
									}

									soma_total_quantidade_contratos_kgs += local_quantidade_kgs;
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

									criarTabelaRecebimentos(recebimentos_totais, soma_total_quantidade_contratos_kgs);
									// controle de nfs
									inserirControleNFVendaEntrada(recebimentos_totais);
								}
								recebimentos_unidos_como_comprador = true;
								}//fim da verifificacao por contrato original
							}
						}
					}

					////////////////// carregamentos
					if (carregamento && carregamento_como_comprador && !unir_carregamentos) {
						
						if(contrato_cliente.getSub_contrato() == 0 || contrato_cliente.getSub_contrato() == 4 || contrato_cliente.getSub_contrato() == 3) {

						ArrayList<CadastroContrato.Carregamento> carregamentos = gerenciar_carregamentos
								.getCarregamentos(contrato_cliente.getId());
						ArrayList<CadastroContrato.CadastroTransferenciaCarga> transferencias_destinatarios = null;
						GerenciarBancoTransferenciasCarga gerenciar_trans = new GerenciarBancoTransferenciasCarga();

						transferencias_destinatarios = gerenciar_trans
								.getTransferenciaDestinatario(contrato_cliente.getId());

						if (carregamentos.size() > 0) {

							XWPFParagraph titulo_recebimentos = document_global.createParagraph();
							titulo_recebimentos.setAlignment(ParagraphAlignment.CENTER);

							XWPFRun titulo_recebimentosRun = titulo_recebimentos.createRun();
							titulo_recebimentosRun.setText("Carregamentos");
							titulo_recebimentosRun.setColor("000000");
							titulo_recebimentosRun.setBold(true);
							titulo_recebimentosRun.setFontFamily("Arial");
							titulo_recebimentosRun.setFontSize(9);

							criarTabelaCarregamentos(carregamentos, 0);
							inserirControleNFVendaSaida(carregamentos);

						} else if (transferencias_destinatarios.size() > 0) {
							if (incluir_transferencias_carregamentos)
								criarTabelaInfoTransferencias(transferencias_destinatarios, contrato_cliente);
						} else {
							if (incluir_sem_carregamentos)
								semCarregamentos(carregamentos, contrato_cliente);

						}
						}
					} else if (carregamento && carregamento_como_comprador && unir_carregamentos) {
						if (unir_carregamentos) {
							if (!carregamentos_unidos_como_comprador) {
								double soma_total_quantidade_contratos_kgs = 0;
								ArrayList<CadastroContrato.Carregamento> carregamentos_totais = new ArrayList<>();

								for (CadastroContrato contrato : contratos_deste_cliente_comprador) {
									if(contrato_cliente.getSub_contrato() == 0 || contrato_cliente.getSub_contrato() == 4 || contrato_cliente.getSub_contrato() == 3) {

									ArrayList<CadastroContrato.Carregamento> carregamentos_locais = gerenciar_carregamentos
											.getCarregamentos(contrato.getId());
									carregamentos_totais.addAll(carregamentos_locais);

									double local_quantidade_kgs = 0;

									if (contrato.getMedida().equalsIgnoreCase("KG")) {
										local_quantidade_kgs = contrato.getQuantidade();
									} else if (contrato.getMedida().equalsIgnoreCase("Sacos")) {
										local_quantidade_kgs = contrato.getQuantidade() * 60;
									}

									soma_total_quantidade_contratos_kgs += local_quantidade_kgs;

								}

								// faz a soma das quantidades dos contratos

								if (carregamentos_totais.size() > 0) {

									XWPFParagraph titulo_recebimentos = document_global.createParagraph();
									titulo_recebimentos.setAlignment(ParagraphAlignment.CENTER);

									XWPFRun titulo_recebimentosRun = titulo_recebimentos.createRun();
									titulo_recebimentosRun.setText("Carregamentos");
									titulo_recebimentosRun.setColor("000000");
									titulo_recebimentosRun.setBold(true);
									titulo_recebimentosRun.setFontFamily("Arial");
									titulo_recebimentosRun.setFontSize(9);

									criarTabelaCarregamentos(carregamentos_totais, soma_total_quantidade_contratos_kgs);
									inserirControleNFVendaSaida(carregamentos_totais);

								}
								carregamentos_unidos_como_comprador = true;
								}//fim da verificacao por contrato original
							
							}
						}
					}

					// pagamentos
					if (pagamento && pagamento_como_comprador) {
						if(contrato_cliente.getSub_contrato() == 0 || contrato_cliente.getSub_contrato() == 4 || contrato_cliente.getSub_contrato() == 3) {

						ArrayList<CadastroContrato.CadastroPagamentoContratual> lista_pagamentos = gerenciar
								.getPagamentosContratuais(contrato_cliente.getId());

						if (lista_pagamentos.size() > 0) {

							XWPFParagraph titulo_sub_contratos = document_global.createParagraph();
							titulo_sub_contratos.setAlignment(ParagraphAlignment.CENTER);

							XWPFRun titulo_sub_contratostitleRun = titulo_sub_contratos.createRun();
							titulo_sub_contratostitleRun.setText("Pagamentos");
							titulo_sub_contratostitleRun.setColor("000000");
							titulo_sub_contratostitleRun.setBold(true);
							titulo_sub_contratostitleRun.setFontFamily("Arial");
							titulo_sub_contratostitleRun.setFontSize(10);

							substituirTexto("\nNúmero de Pagamentos: " + lista_pagamentos.size(), 2);
							substituirTexto("", 2);
							if (!contrato) {
								substituirTexto("Cliente: " + nome_cliente.toUpperCase(), -1);

							}
							criarTabelaPagamentos(lista_pagamentos, contrato_cliente);

						} else {
							if (incluir_sem_pagamentos)
								semPagamentos(contrato_cliente);
						}

					}
					}
				}
			}

			if (recebimento_como_vendedor || carregamento_como_vendedor || pagamento_como_vendedor) {
				for (CadastroContrato contrato_cliente : contratos_deste_cliente_vendedor) {
					if (!recebimentos_unidos_como_vendedor)
						adicionarTraco(true, 0);

					if (recebimento && recebimento_como_vendedor && !unir_recebimentos) {

						double soma_total_quantidade_contratos_kgs = 0;
						ArrayList<CadastroContrato.Recebimento> recebimentos_totais = new ArrayList<>();

						ArrayList<CadastroContrato.Recebimento> recebimentos_locais = gerenciar_recebimentos
								.getRecebimentos(contrato_cliente.getId());
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

							criarTabelaRecebimentos(recebimentos_totais, 0);
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
								ArrayList<CadastroContrato.Recebimento> recebimentos_totais = new ArrayList<>();

								for (CadastroContrato contrato : contratos_deste_cliente_vendedor) {
									ArrayList<CadastroContrato.Recebimento> recebimentos_locais = gerenciar_recebimentos
											.getRecebimentos(contrato.getId());
									recebimentos_totais.addAll(recebimentos_locais);

									double local_quantidade_kgs = 0;

									if (contrato.getMedida().equalsIgnoreCase("KG")) {
										local_quantidade_kgs = contrato.getQuantidade();
									} else if (contrato.getMedida().equalsIgnoreCase("Sacos")) {
										local_quantidade_kgs = contrato.getQuantidade() * 60;
									}

									soma_total_quantidade_contratos_kgs += local_quantidade_kgs;

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

									criarTabelaRecebimentos(recebimentos_totais, soma_total_quantidade_contratos_kgs);
									// controle de nfs
									inserirControleNFVendaEntrada(recebimentos_totais);
								}
								recebimentos_unidos_como_vendedor = true;
							}
						}
					}

					////////////////// carregamentos
					if (carregamento && carregamento_como_vendedor && !unir_carregamentos) {

						ArrayList<CadastroContrato.Carregamento> carregamentos = gerenciar_carregamentos
								.getCarregamentos(contrato_cliente.getId());

						if (carregamentos.size() > 0) {

							XWPFParagraph titulo_recebimentos = document_global.createParagraph();
							titulo_recebimentos.setAlignment(ParagraphAlignment.CENTER);

							XWPFRun titulo_recebimentosRun = titulo_recebimentos.createRun();
							titulo_recebimentosRun.setText("Carregamentos");
							titulo_recebimentosRun.setColor("000000");
							titulo_recebimentosRun.setBold(true);
							titulo_recebimentosRun.setFontFamily("Arial");
							titulo_recebimentosRun.setFontSize(9);

							criarTabelaCarregamentos(carregamentos, 0);
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
								ArrayList<CadastroContrato.Carregamento> carregamentos_totais = new ArrayList<>();

								for (CadastroContrato contrato : contratos_deste_cliente_vendedor) {
									ArrayList<CadastroContrato.Carregamento> carregamentos_locais = gerenciar_carregamentos
											.getCarregamentos(contrato.getId());
									carregamentos_totais.addAll(carregamentos_locais);

									double local_quantidade_kgs = 0;

									if (contrato.getMedida().equalsIgnoreCase("KG")) {
										local_quantidade_kgs = contrato.getQuantidade();
									} else if (contrato.getMedida().equalsIgnoreCase("Sacos")) {
										local_quantidade_kgs = contrato.getQuantidade() * 60;
									}

									soma_total_quantidade_contratos_kgs += local_quantidade_kgs;

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

									criarTabelaCarregamentos(carregamentos_totais, soma_total_quantidade_contratos_kgs);
									inserirControleNFVendaSaida(carregamentos_totais);

								}
								carregamentos_unidos_como_vendedor = true;
							}
						}
					}

					// pagamentos
					if (pagamento && pagamento_como_vendedor) {
						ArrayList<CadastroContrato.CadastroPagamentoContratual> lista_pagamentos = gerenciar
								.getPagamentosContratuais(contrato_cliente.getId());

						if (lista_pagamentos.size() > 0) {

							XWPFParagraph titulo_sub_contratos = document_global.createParagraph();
							titulo_sub_contratos.setAlignment(ParagraphAlignment.CENTER);

							XWPFRun titulo_sub_contratostitleRun = titulo_sub_contratos.createRun();
							titulo_sub_contratostitleRun.setText("Pagamentos");
							titulo_sub_contratostitleRun.setColor("000000");
							titulo_sub_contratostitleRun.setBold(true);
							titulo_sub_contratostitleRun.setFontFamily("Arial");
							titulo_sub_contratostitleRun.setFontSize(10);

							substituirTexto("\nNúmero de Pagamentos: " + lista_pagamentos.size(), 2);
							substituirTexto("", 2);
							substituirTexto("Cliente: " + nome_cliente.toUpperCase(), -1);
							
							criarTabelaPagamentos(lista_pagamentos, contrato_cliente);

						} else {
							if (incluir_sem_pagamentos)
								semPagamentos(contrato_cliente);
						}

					}

				}

			}
			contador_clientes++;
		}
	}

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
					Units.toEMU(30), Units.toEMU(30));

		} catch (Exception e) {
			// //JOptionPane.showMessageDialog(null,
			// "Erro ao criar cabecalho e rodape do contrato!\nConsulte o administrador do
			// sistema!");
			e.printStackTrace();
		}

		ByteArrayOutputStream saida_apos_edicao = new ByteArrayOutputStream();

		/*
		 * try { document_global.write(new FileOutputStream("c:\\arquivoteste.docx"));
		 * document_global.write(saida_apos_edicao);
		 * 
		 * } catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

		try {
			document_global.write(saida_apos_edicao);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return saida_apos_edicao;

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

	public void semCarregamentos(ArrayList<Carregamento> carregamentos, CadastroContrato contrato) {
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

	public void inserirControleNFVendaEntrada(ArrayList<CadastroContrato.Recebimento> recebimentos) {
		if (controle_nf_venda_recebimentos) {

			if (recebimentos.size() > 0) {

				XWPFParagraph titulo_controle_vendas = document_global.createParagraph();
				titulo_controle_vendas.setAlignment(ParagraphAlignment.CENTER);

				XWPFRun titulo_controle_vendasRun = titulo_controle_vendas.createRun();
				titulo_controle_vendasRun.setText("CONTROLE DE NOTAS DE VENDA(ENTRADA)");
				titulo_controle_vendasRun.setColor("000000");
				titulo_controle_vendasRun.setBold(true);
				titulo_controle_vendasRun.setFontFamily("Arial");
				titulo_controle_vendasRun.setFontSize(9);

				ArrayList<CadastroNFe> nfs_venda = new ArrayList<>();

				for (CadastroContrato.Recebimento recebimento : recebimentos) {

					if (recebimento.getNf_venda_aplicavel() == 1) {
						// este recebimento possui nfs_venda
						try {
							if (checkString(recebimento.getCodigo_nf_venda())) {
								if (recebimento.getCaminho_nf_venda().length() > 10) {
									// procurar por nf venda
									ManipularNotasFiscais manipular = new ManipularNotasFiscais("");
									CadastroNFe nota_fiscal_venda = manipular
											.filtrar(new File(servidor_unidade + recebimento.getCaminho_nf_venda()));
									nota_fiscal_venda.setContrato(new GerenciarBancoContratos()
											.getContrato(recebimento.getId_contrato_recebimento()));
									nfs_venda.add(nota_fiscal_venda);
								} else {
									CadastroNFe nota_fiscal_venda = new CadastroNFe();
									nota_fiscal_venda.setQuantidade_double(recebimento.getPeso_nf_venda());
									nota_fiscal_venda.setNfe(recebimento.getCodigo_nf_venda());
									nota_fiscal_venda.setValor(recebimento.getValor_nf_venda().toString());
									nota_fiscal_venda.setContrato(new GerenciarBancoContratos()
											.getContrato(recebimento.getId_contrato_recebimento()));
									if (checkString(recebimento.getNome_remetente_nf_venda())) {
										nota_fiscal_venda.setNome_remetente(recebimento.getNome_remetente_nf_venda());

									}
									if (checkString(recebimento.getNome_destinatario_nf_venda())) {
										nota_fiscal_venda
												.setNome_destinatario(recebimento.getNome_destinatario_nf_venda());

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

	public void inserirControleNFVendaSaida(ArrayList<CadastroContrato.Carregamento> carregamentos) {
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

	public void criarTabelaRecebimentos(ArrayList<CadastroContrato.Recebimento> recebimentos,
			double soma_total_quantidade_contratos) {
		// XWPFParagraph par = document_global.createParagraph();

		NumberFormat z = NumberFormat.getNumberInstance();

		Locale ptBr = new Locale("pt", "BR");

		// criarParagrafo(1);
		// linhas x colunas

		int num_linhas_recebimentos = -1;

		if (soma_total_quantidade_contratos == 0) {

			num_linhas_recebimentos = recebimentos.size() + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1;
		} else {
			num_linhas_recebimentos = recebimentos.size() + 1 + 1 + 1 + 1 + 1 + 1 + 1;

		}
		double soma_total_romaneio = 0;
		double soma_total_nf_venda = 0;

		double soma_total_nf_remessa = 0;

		BigDecimal valor_total_nf_venda = BigDecimal.ZERO;
		BigDecimal valor_total_nf_remessa = BigDecimal.ZERO;

		XWPFTable table = document_global.createTable(num_linhas_recebimentos, 10);

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

		} else {

			CadastroContrato contrato_deste_recebimento = new GerenciarBancoContratos()
					.getContrato(recebimentos.get(0).getId_contrato_recebimento());

			tableRowOne = table.getRow(cabecalho);
			tableRowOne.getCell(0).removeParagraph(0);
			paragraph = tableRowOne.getCell(0).addParagraph();

			double quantidade_kg = 0;
			double quantidade_sacos = 0;

			if (contrato_deste_recebimento.getMedida().equalsIgnoreCase("KG")) {
				quantidade_kg = contrato_deste_recebimento.getQuantidade();
				quantidade_sacos = quantidade_kg / 60;
			} else if (contrato_deste_recebimento.getMedida().equalsIgnoreCase("Sacos")) {
				quantidade_sacos = contrato_deste_recebimento.getQuantidade();
				quantidade_kg = quantidade_sacos * 60;
			}

			// compradores x vendedores

			// safra
			String safra = contrato_deste_recebimento.getModelo_safra().getProduto().getNome_produto() + " "
					+ contrato_deste_recebimento.getModelo_safra().getProduto().getTransgenia() + " "
					+ contrato_deste_recebimento.getModelo_safra().getAno_plantio() + "/"
					+ contrato_deste_recebimento.getModelo_safra().getAno_colheita();

			criarParagrafoTabela(paragraph,
					"CTR: " + contrato_deste_recebimento.getCodigo() + " " + safra + " Quantidade Total: "
							+ z.format(quantidade_kg) + " kgs | " + z.format(quantidade_sacos) + " sacos "
							+ NumberFormat.getCurrencyInstance(ptBr)
									.format(contrato_deste_recebimento.getValor_produto())
							+ " por " + contrato_deste_recebimento.getMedida() + " totalizando: "
							+ NumberFormat.getCurrencyInstance(ptBr)
									.format(contrato_deste_recebimento.getValor_a_pagar().doubleValue()),
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

			CadastroCliente compradores[] = contrato_deste_recebimento.getCompradores();
			CadastroCliente vendedores[] = contrato_deste_recebimento.getVendedores();

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

		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();

		CadastroContrato novo_contrato = gerenciar.getContrato(recebimentos.get(0).getId_contrato_recebimento());
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
			criarParagrafoTabela(paragraph, gerenciar.getContrato(recebimento.getId_contrato_recebimento()).getCodigo(),
					false);
			tableRowOne.getCell(0).getCTTc().addNewTcPr().addNewShd().setFill(cor);

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

		if (soma_total_quantidade_contratos != 0) {
			quantidade_total_kgs = soma_total_quantidade_contratos;
		} else {
			if (novo_contrato.getMedida().equalsIgnoreCase("KG")) {
				quantidade_total_kgs = novo_contrato.getQuantidade();
				quantidade_total_sacos = quantidade_total_kgs / 60;
			} else if (novo_contrato.getMedida().equalsIgnoreCase("Sacos")) {
				quantidade_total_sacos = novo_contrato.getQuantidade();
				quantidade_total_kgs = quantidade_total_sacos * 60;
			}
		}

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(1).removeParagraph(0);
		paragraph = tableRowOne.getCell(1).addParagraph();
		if (soma_total_quantidade_contratos != 0) {
			criarParagrafoTabela(paragraph,
					" " + z.format(soma_total_quantidade_contratos - soma_total_romaneio) + " kgs / "
							+ (z.format(((soma_total_quantidade_contratos - soma_total_romaneio) / 60))) + " sacos ",
					true);
		} else {
			criarParagrafoTabela(paragraph, " " + z.format(quantidade_total_kgs - soma_total_romaneio) + " kgs / "
					+ (z.format(((quantidade_total_kgs - soma_total_romaneio) / 60))) + " sacos ", true);
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

		String texto = "Status Gerado automaticamente: \n";
		double diferenca = quantidade_total_kgs - soma_total_romaneio;
		if (diferenca == 0) {
			texto = texto + "Recebimento Concluído\n";
		} else if (diferenca > 0) {
			texto = texto + "Recebimento Incompleto, falta receber " + z.format(diferenca) + " Kgs | "
					+ z.format(diferenca / 60) + " sacos\n";

		} else if (diferenca < 0) {
			texto = texto + "Recebimento Excedido, excedeu " + z.format(diferenca) + " Kgs | "
					+ z.format(diferenca / 60) + " sacos\n";

		}

		String texto_nf_venda = "";
		if (nf_venda_ativo) {
			double diferenca_nf_venda = quantidade_total_kgs - soma_total_nf_venda;
			if (diferenca_nf_venda == 0) {
				texto_nf_venda = texto_nf_venda + "Emissão de NF's de Venda Concluído\n";
			} else if (diferenca_nf_venda > 0) {
				texto_nf_venda = texto_nf_venda + "Emissão de NF's de Venda Incompleto, falta emitir "
						+ z.format(diferenca_nf_venda) + " Kgs | " + z.format(diferenca_nf_venda / 60) + " sacos\n";

			} else if (diferenca_nf_venda < 0) {
				texto_nf_venda = texto_nf_venda + "Emissão de NF's Venda Excedido, excedeu "
						+ z.format(diferenca_nf_venda) + " Kgs | " + z.format(diferenca_nf_venda / 60) + " sacos\n";

			}
		} else {
			texto_nf_venda = "Emissão de NF's Venda Não Aplicável";
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
		tableRowOne.getCell(1).removeParagraph(0);
		paragraph = tableRowOne.getCell(1).addParagraph();
		criarParagrafoTabela(paragraph, "EMITENTE", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(2).removeParagraph(0);
		paragraph = tableRowOne.getCell(2).addParagraph();
		criarParagrafoTabela(paragraph, "DESTINATARIO", true);

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(3).removeParagraph(0);
		paragraph = tableRowOne.getCell(3).addParagraph();
		criarParagrafoTabela(paragraph, "DATA", true);

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

		int i = cabecalho + 1;

		for (CadastroNFe nf_venda : nfs_venda) {

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(0).removeParagraph(0);
			paragraph = tableRowOne.getCell(0).addParagraph();
			criarParagrafoTabela(paragraph, nf_venda.getContrato().getCodigo(), false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(1).removeParagraph(0);
			paragraph = tableRowOne.getCell(1).addParagraph();
			criarParagrafoTabela(paragraph, nf_venda.getNome_remetente(), false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(2).removeParagraph(0);
			paragraph = tableRowOne.getCell(2).addParagraph();
			criarParagrafoTabela(paragraph, nf_venda.getNome_destinatario(), false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(3).removeParagraph(0);
			paragraph = tableRowOne.getCell(3).addParagraph();
			try {
				criarParagrafoTabela(paragraph, nf_venda.getData().toString(), false);
			} catch (NullPointerException e) {
				criarParagrafoTabela(paragraph, "Indefinido", false);

			}

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(4).removeParagraph(0);
			paragraph = tableRowOne.getCell(4).addParagraph();
			criarParagrafoTabela(paragraph, nf_venda.getNfe(), false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(5).removeParagraph(0);
			paragraph = tableRowOne.getCell(5).addParagraph();

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

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(6).removeParagraph(0);
			paragraph = tableRowOne.getCell(6).addParagraph();

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

	public void criarTabelaCarregamentos(ArrayList<CadastroContrato.Carregamento> carregamentos,
			double soma_total_quantidade_contratos) {
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

		GerenciarBancoTransferenciasCarga gerenciar_trans = new GerenciarBancoTransferenciasCarga();
		ArrayList<CadastroContrato.CadastroTransferenciaCarga> transferencias_remetentes = null;
		ArrayList<CadastroContrato.CadastroTransferenciaCarga> transferencias_destinatarios = null;

		CadastroContrato contrato_deste_carregamento = new GerenciarBancoContratos()
				.getContrato(carregamentos.get(0).getId_contrato());

		double quantidade_kg = 0;
		double quantidade_sacos = 0;

		if (contrato_deste_carregamento.getMedida().equalsIgnoreCase("KG")) {
			quantidade_kg = contrato_deste_carregamento.getQuantidade();
			quantidade_sacos = quantidade_kg / 60;
		} else if (contrato_deste_carregamento.getMedida().equalsIgnoreCase("Sacos")) {
			quantidade_sacos = contrato_deste_carregamento.getQuantidade();
			quantidade_kg = quantidade_sacos * 60;
		}

		transferencias_remetentes = gerenciar_trans.getTransferenciasRemetente(contrato_deste_carregamento.getId());

		transferencias_destinatarios = gerenciar_trans
				.getTransferenciaDestinatario(contrato_deste_carregamento.getId());

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

			criarParagrafoTabela(paragraph, "Quantidade Total: " + z.format(soma_total_quantidade_contratos) + " kgs | "
					+ z.format(soma_total_quantidade_contratos / 60) + " sacos", true);
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

			// safra
			String safra = contrato_deste_carregamento.getModelo_safra().getProduto().getNome_produto() + " "
					+ contrato_deste_carregamento.getModelo_safra().getProduto().getTransgenia() + " "
					+ contrato_deste_carregamento.getModelo_safra().getAno_plantio() + "/"
					+ contrato_deste_carregamento.getModelo_safra().getAno_colheita();

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

		for (CadastroContrato.Carregamento carregamento : carregamentos) {

			GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
			// pegar dados do contrato
			CadastroContrato contrato_destinatario = gerenciar.getContrato(carregamento.getId_contrato());

			// pegar cliente
			GerenciarBancoClientes gerenciar_clientes = new GerenciarBancoClientes();
			CadastroCliente cliente_carregamento = gerenciar_clientes.getCliente(carregamento.getId_cliente());
			String nome_cliente;
			if (cliente_carregamento.getTipo_pessoa() == 0) {
				// pessoa fisica
				nome_cliente = cliente_carregamento.getNome_empresarial();
			} else {
				nome_cliente = cliente_carregamento.getNome_fantaia();
			}

			String nome_cliente_completo = nome_cliente;

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
			CadastroCliente vendedor_carregamento = gerenciar_clientes.getCliente(carregamento.getId_vendedor());
			String nome_vendedor;
			if (vendedor_carregamento.getTipo_pessoa() == 0) {
				// pessoa fisica
				nome_vendedor = vendedor_carregamento.getNome_empresarial();
			} else {
				nome_vendedor = vendedor_carregamento.getNome_fantaia();
			}

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

			String nome_transportador = "";
			String placa_trator = "";
			if (carregamento.getId_transportador() > 0) {
				// pegar transportador e veiculo
				CadastroCliente transportador_carregamento = gerenciar_clientes
						.getCliente(carregamento.getId_transportador());

				if (transportador_carregamento != null) {
					if (transportador_carregamento.getTipo_pessoa() == 0) {
						nome_transportador = transportador_carregamento.getNome_empresarial();
					} else {
						nome_transportador = transportador_carregamento.getNome_fantaia();

					}
				}
				CadastroCliente.Veiculo veiculo_carregamento = null;
				if (carregamento.getId_veiculo() > 0) {
					for (CadastroCliente.Veiculo veiculo : transportador_carregamento.getVeiculos()) {
						if (veiculo.getId_veiculo() == carregamento.getId_veiculo()) {
							veiculo_carregamento = veiculo;
							break;
						}
					}

					if (veiculo_carregamento != null) {
						placa_trator = veiculo_carregamento.getPlaca_trator();
					}
				}

			}
			// pegar o produto
			GerenciarBancoProdutos gerenciar_produtos = new GerenciarBancoProdutos();
			CadastroProduto produto_carregamento = gerenciar_produtos.getProduto(carregamento.getId_produto());

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
				// //JOptionPane.showMessageDialog(isto, "Nota Fiscal de remessa não Localizado");

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
			criarParagrafoTabela(paragraph, nome_transportador, false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(5).removeParagraph(0);
			paragraph = tableRowOne.getCell(5).addParagraph();
			criarParagrafoTabela(paragraph, placa_trator, false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(6).removeParagraph(0);
			paragraph = tableRowOne.getCell(6).addParagraph();
			criarParagrafoTabela(paragraph, produto_carregamento.getNome_produto(), false);

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
				CadastroContrato.Carregamento carga = gerencia_contratos
						.getCarregamento(transferencia.getId_carregamento_remetente());

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
				CadastroContrato.Carregamento carga = gerencia_contratos
						.getCarregamento(transferencia.getId_carregamento_remetente());

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

		/////////////////////////////////////////////////////////////////
		if (peso_total_romaneios < quantidade_kg) {
			// totais
			texto = texto + "\nPeso Carregado: [";
			texto = texto + z.format(peso_total_romaneios) + " Kgs]";

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

			texto = texto + z.format(quantidade_kg - peso_total_romaneios) + " Kgs]";

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
			texto = texto + "\n";

			texto = texto + "*Pesos restantes calculados a partir do peso total já carregado";

			// status baseado no peso total ja carregado

			texto = texto + "\n\n";

			texto = texto + "Status parcial gerado de forma automatica calculados a partir do peso total já carregado";

			texto = texto + "\n";

			double diferenca = quantidade_kg - peso_total_romaneios;
			if (diferenca == 0) {
				texto = texto + "Carregamento Concluído";
			} else if (diferenca > 0) {
				texto = texto + "Carregamento Incompleto, [falta carregar " + z.format(diferenca) + " Kgs]";

			} else if (diferenca < 0) {
				texto = texto + "Carregamento Excedido, [excedeu " + z.format(diferenca) + " Kgs]";

			}

			texto = texto + "\n";

			String texto_nf_remessa = "";
			double diferenca_nf_remessa = peso_total_romaneios - peso_total_nf_interna;
			if (diferenca_nf_remessa == 0) {
				texto_nf_remessa = texto_nf_remessa + "[Emissão de NF's Interna Concluído]";
			} else if (diferenca_nf_remessa > 0) {
				texto_nf_remessa = texto_nf_remessa + "Emissão de NF's Interna Incompleto, [falta emitir "
						+ z.format(diferenca_nf_remessa) + " Kgs]";

			} else if (diferenca_nf_remessa < 0) {
				texto_nf_remessa = texto_nf_remessa + "Emissão de NF's Interna, [excedeu "
						+ z.format(diferenca_nf_remessa) + " Kgs]";

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
						+ z.format(diferenca_nf_venda) + " Kgs]";

			} else if (diferenca_nf_venda < 0) {
				texto_nf_venda = texto_nf_venda + "Emissão de NF's Excedido, excedeu " + z.format(diferenca_nf_venda)
						+ " Kgs";

			}
			if (nf_venda_ativo || nf_complemento_ativo)
				texto = texto + (texto_nf_venda);
			else
				texto = texto + "Emissão de NF's de Venda Não Aplicável";

		}
		// informacoes de total na tabela
		// somatoria dos romaneios

		// peso total romaneios
		tableRowOne = table.getRow(i);
		tableRowOne.getCell(8).removeParagraph(0);
		paragraph = tableRowOne.getCell(8).addParagraph();
		criarParagrafoTabela(paragraph, z.format(peso_total_romaneios) + " Kgs", true);

		// peso total nf venda 1
		tableRowOne = table.getRow(i);
		tableRowOne.getCell(10).removeParagraph(0);
		paragraph = tableRowOne.getCell(10).addParagraph();
		criarParagrafoTabela(paragraph, z.format(peso_total_nf_venda1) + " Kgs", true);

		// valor total nf venda 1
		tableRowOne = table.getRow(i);
		tableRowOne.getCell(11).removeParagraph(0);
		paragraph = tableRowOne.getCell(11).addParagraph();
		criarParagrafoTabela(paragraph, NumberFormat.getCurrencyInstance(ptBr).format(valor_total_nf_venda1), true);

		// peso total nf complemento
		tableRowOne = table.getRow(i);
		tableRowOne.getCell(13).removeParagraph(0);
		paragraph = tableRowOne.getCell(13).addParagraph();
		criarParagrafoTabela(paragraph, z.format(peso_total_nf_complemento) + " Kgs", true);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(14).removeParagraph(0);
		paragraph = tableRowOne.getCell(14).addParagraph();
		criarParagrafoTabela(paragraph, NumberFormat.getCurrencyInstance(ptBr).format(valor_total_nf_complemento),
				true);

		// diferenca

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(15).removeParagraph(0);
		paragraph = tableRowOne.getCell(15).addParagraph();
		criarParagrafoTabela(paragraph, z.format(peso_total_diferenca) + " Kgs", true);

		// pesos baseados no total
		texto = texto + "\n\n";
		// totais
		texto = texto + "Peso Carregado: [";
		texto = texto + z.format(peso_total_romaneios) + " Kgs]";

		texto = texto + " Peso NF's Interna: [";
		if (nf_interna_ativo) {

			texto = texto + z.format(peso_total_nf_interna) + " Kgs]";
		} else {
			texto = texto + " [Não Aplicável]";

		}

		texto = texto + " Peso NF's Venda: [";

		if (nf_venda_ativo || nf_complemento_ativo) {
			texto = texto + (z.format(peso_total_nf_venda1 + peso_total_nf_complemento) + " Kgs]");

		} else {
			texto = texto + " [Não Aplicável]";

		}
		texto = texto + " Valor NF's Venda: [";

		BigDecimal soma = valor_total_nf_venda1.add(valor_total_nf_complemento);

		if (nf_venda_ativo || nf_complemento_ativo) {
			texto = texto + NumberFormat.getCurrencyInstance(ptBr).format(soma.doubleValue()) + "]";

		} else {
			texto = texto + " [Não Aplicável]";

		}
		texto = texto + "\n";

		texto = texto + "Peso a Carregar: [";

		texto = texto + z.format(quantidade_kg - peso_total_romaneios) + " Kgs]";

		texto = texto + " Peso NF's Interna a Emitir: [";

		if (nf_interna_ativo) {
			texto = texto + z.format(quantidade_kg - peso_total_nf_interna) + " Kgs]";
		} else {
			texto = texto + " [Não Aplicável]";

		}

		texto = texto + " Peso NF's Venda a Emitir: [";

		if (nf_venda_ativo || nf_complemento_ativo) {
			texto = texto + z.format(quantidade_kg - (peso_total_nf_venda1 + peso_total_nf_complemento)) + " Kgs]";

		} else {
			texto = texto + " [Não Aplicável]";

		}
		texto = texto + "\n";

		texto = texto + "*Pesos restantes calculados a partir do peso total";

		// status baseado no peso total ja carregado

		texto = texto + "\n\n";

		texto = texto + "[Status parcial gerado de forma automatica calculados a partir do peso total:]";

		texto = texto + "\n";

		double diferenca = quantidade_kg - peso_total_romaneios;
		if (diferenca == 0) {
			texto = texto + "[Carregamento Concluído]";
		} else if (diferenca > 0) {
			texto = texto + "Carregamento Incompleto, [falta carregar " + z.format(diferenca) + " Kgs]";

		} else if (diferenca < 0) {
			texto = texto + "Carregamento Excedido, [excedeu " + z.format(diferenca) + " Kgs]";

		}

		texto = texto + "\n";

		String texto_nf_remessa = "";
		double diferenca_nf_remessa = quantidade_kg - peso_total_nf_interna;
		if (diferenca_nf_remessa == 0) {
			texto_nf_remessa = texto_nf_remessa + "[Emissão de NF's Interna Concluído]";
		} else if (diferenca_nf_remessa > 0) {
			texto_nf_remessa = texto_nf_remessa + "Emissão de NF's Interna Incompleto, [falta emitir "
					+ z.format(diferenca_nf_remessa) + " Kgs]";

		} else if (diferenca_nf_remessa < 0) {
			texto_nf_remessa = texto_nf_remessa + "Emissão de NF's Interna, [excedeu " + z.format(diferenca_nf_remessa)
					+ " Kgs]";

		}

		if (nf_interna_ativo)
			texto = texto + (texto_nf_remessa);
		else
			texto = texto + "Emissão de NF's Interna Não Aplicável";

		texto = texto + "\n";
		// status de nf de venda

		String texto_nf_venda = "";
		double diferenca_nf_venda = quantidade_kg - (peso_total_nf_venda1 + peso_total_nf_complemento);
		if (diferenca_nf_venda == 0) {
			texto_nf_venda = texto_nf_venda + "[Emissão de NF's de Venda Concluído]";
		} else if (diferenca_nf_venda > 0) {
			texto_nf_venda = texto_nf_venda + "Emissão de NF's de Venda Incompleto, [falta emitir "
					+ z.format(diferenca_nf_venda) + " Kgs]";

		} else if (diferenca_nf_venda < 0) {
			texto_nf_venda = texto_nf_venda + "Emissão de NF's Excedido, [excedeu " + z.format(diferenca_nf_venda)
					+ " Kgs]";

		}
		if (nf_venda_ativo || nf_complemento_ativo)
			texto = texto + (texto_nf_venda);
		else
			texto = texto + "Emissão de NF's de Venda Não Aplicável";

		////////////////////////////////////////////////////////////////////////////////
		substituirTexto(-1, texto);

		texto = "";

		texto = "Peso Total Romaneios: [" + z.format(peso_total_romaneios) + " kgs ] | ["
				+ z.format(peso_total_romaneios / 60) + " sacos ]\n\n";
		if (nf_venda_ativo || nf_complemento_ativo) {
			texto = texto + "Peso Total NF's Venda 1: [" + z.format(peso_total_nf_venda1) + " kgs ] | ["
					+ z.format(peso_total_nf_venda1 / 60) + " sacos ]\n";
			texto = texto + "Peso Total NF's Complemento: [" + z.format(peso_total_nf_complemento) + " kgs ] | ["
					+ z.format(peso_total_nf_complemento / 60) + " sacos ]\n";
			texto = texto + "Peso Total NF's: [" + z.format(peso_total_nf_complemento + peso_total_nf_venda1)
					+ " kgs ] | [" + z.format((peso_total_nf_complemento + peso_total_nf_venda1) / 60) + " sacos ]\n";
			texto = texto + "Diferença Total: ["
					+ z.format(peso_total_romaneios - (peso_total_nf_complemento + peso_total_nf_venda1)) + " kgs ] | ["
					+ z.format((peso_total_romaneios - (peso_total_nf_complemento + peso_total_nf_venda1)) / 60)
					+ " sacos ]\n";
			texto = texto + "Valor Total NF's Venda 1: ["
					+ NumberFormat.getCurrencyInstance(ptBr).format(valor_total_nf_venda1) + "]\n";
			texto = texto + "Valor Total NF's Complemento: ["
					+ NumberFormat.getCurrencyInstance(ptBr).format(valor_total_nf_complemento) + "]\n";
			texto = texto + "Valor Total NF's: [" + NumberFormat.getCurrencyInstance(ptBr)
					.format(valor_total_nf_complemento.add(valor_total_nf_venda1)) + "]\n";
		} else {
			texto = texto + "NF's Venda Não Aplicável";
		}

		substituirTexto(1, texto);

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

	/*
	 * public void criarTabelaCarregamentos(CadastroContrato novo_contrato,
	 * ArrayList<CadastroContrato.Carregamento> carregamentos) { //XWPFParagraph par
	 * = document_global.createParagraph();
	 * 
	 * NumberFormat z = NumberFormat.getNumberInstance();
	 * 
	 * Locale ptBr = new Locale("pt", "BR");
	 * 
	 * // criarParagrafo(1); // linhas x colunas
	 * 
	 * int num_linhas_carregamentos = carregamentos.size() + 1 + 1 + 1 + 2; double
	 * soma_total_carga_real = 0; double soma_total_carga_nfa = 0; double
	 * quantidade_kg = 0; if(novo_contrato.getMedida().equalsIgnoreCase("KG")) {
	 * quantidade_kg = novo_contrato.getQuantidade();; }else
	 * if(novo_contrato.getMedida().equalsIgnoreCase("Sacos")) { quantidade_kg =
	 * novo_contrato.getQuantidade() * 60;
	 * 
	 * }
	 * 
	 * 
	 * XWPFTable table = document_global.createTable(num_linhas_carregamentos, 16);
	 * 
	 * setTableAlign(table, ParagraphAlignment.CENTER); XWPFTableRow tableRowOne =
	 * table.getRow(0); tableRowOne.getCell(0).removeParagraph(0);
	 * 
	 * XWPFParagraph paragraph = tableRowOne.getCell(0).addParagraph();
	 * 
	 * criarParagrafoTabela(paragraph, "CONTRATO", true);
	 * 
	 * tableRowOne = table.getRow(0); tableRowOne.getCell(1).removeParagraph(0);
	 * paragraph = tableRowOne.getCell(1).addParagraph();
	 * criarParagrafoTabela(paragraph, "DATA", true);
	 * 
	 * tableRowOne = table.getRow(0); tableRowOne.getCell(2).removeParagraph(0);
	 * paragraph = tableRowOne.getCell(2).addParagraph();
	 * criarParagrafoTabela(paragraph, "CLIENTE", true);
	 * 
	 * tableRowOne = table.getRow(0); tableRowOne.getCell(3).removeParagraph(0);
	 * paragraph = tableRowOne.getCell(3).addParagraph();
	 * criarParagrafoTabela(paragraph, "VENDEDOR", true);
	 * 
	 * tableRowOne = table.getRow(0); tableRowOne.getCell(4).removeParagraph(0);
	 * paragraph = tableRowOne.getCell(4).addParagraph();
	 * criarParagrafoTabela(paragraph, "TRANSPORTADOR", true);
	 * 
	 * tableRowOne = table.getRow(0); tableRowOne.getCell(5).removeParagraph(0);
	 * paragraph = tableRowOne.getCell(5).addParagraph();
	 * criarParagrafoTabela(paragraph, "VEICULO", true);
	 * 
	 * tableRowOne = table.getRow(0); tableRowOne.getCell(6).removeParagraph(0);
	 * paragraph = tableRowOne.getCell(6).addParagraph();
	 * criarParagrafoTabela(paragraph, "PRODUTO", true);
	 * 
	 * tableRowOne = table.getRow(0); tableRowOne.getCell(7).removeParagraph(0);
	 * paragraph = tableRowOne.getCell(7).addParagraph();
	 * criarParagrafoTabela(paragraph, "ROM", true);
	 * 
	 * tableRowOne = table.getRow(0); tableRowOne.getCell(8).removeParagraph(0);
	 * paragraph = tableRowOne.getCell(8).addParagraph();
	 * criarParagrafoTabela(paragraph, "PESO ROM", true);
	 * 
	 * 
	 * tableRowOne = table.getRow(0); tableRowOne.getCell(9).removeParagraph(0);
	 * paragraph = tableRowOne.getCell(9).addParagraph();
	 * criarParagrafoTabela(paragraph, "NF 1", true);
	 * 
	 * tableRowOne = table.getRow(0); tableRowOne.getCell(10).removeParagraph(0);
	 * paragraph = tableRowOne.getCell(10).addParagraph();
	 * criarParagrafoTabela(paragraph, "PESO NF 1", true);
	 * 
	 * tableRowOne = table.getRow(0); tableRowOne.getCell(11).removeParagraph(0);
	 * paragraph = tableRowOne.getCell(11).addParagraph();
	 * criarParagrafoTabela(paragraph, "VALOR NF 1", true);
	 * 
	 * tableRowOne = table.getRow(0); tableRowOne.getCell(12).removeParagraph(0);
	 * paragraph = tableRowOne.getCell(12).addParagraph();
	 * criarParagrafoTabela(paragraph, "NF 2", true);
	 * 
	 * tableRowOne = table.getRow(0); tableRowOne.getCell(13).removeParagraph(0);
	 * paragraph = tableRowOne.getCell(13).addParagraph();
	 * criarParagrafoTabela(paragraph, "PESO NF 2", true);
	 * 
	 * tableRowOne = table.getRow(0); tableRowOne.getCell(14).removeParagraph(0);
	 * paragraph = tableRowOne.getCell(14).addParagraph();
	 * criarParagrafoTabela(paragraph, "VALOR NF 2", true);
	 * 
	 * tableRowOne = table.getRow(0); tableRowOne.getCell(15).removeParagraph(0);
	 * paragraph = tableRowOne.getCell(15).addParagraph();
	 * criarParagrafoTabela(paragraph, "DIFERENÇA", true);
	 * 
	 * 
	 * 
	 * 
	 * int i = 1;
	 * 
	 * double peso_total_nf_interna = 0.0 ; double peso_total_romaneios= 0.0 ;
	 * double peso_total_nf_venda1 = 0.0; double peso_total_nf_complemento = 0.0;
	 * double peso_total_diferenca = 0.0;
	 * 
	 * BigDecimal valor_total_nf_venda1 = BigDecimal.ZERO; BigDecimal
	 * valor_total_nf_complemento = BigDecimal.ZERO;
	 * 
	 * //fazer checkagens
	 * 
	 * boolean nf_interna_ativo = false; boolean nf_venda_ativo = false; boolean
	 * nf_complemento_ativo = false;
	 * 
	 * // checka se ha no minimo uma nf interna aplicavel for
	 * (CadastroContrato.Carregamento carregamento : carregamentos) { if
	 * (carregamento.getNf_interna_aplicavel() == 1) { nf_interna_ativo = true;
	 * break; }
	 * 
	 * }
	 * 
	 * // checka se ha no minimo uma nf venda aplicavel for
	 * (CadastroContrato.Carregamento carregamento : carregamentos) { if
	 * (carregamento.getNf_venda1_aplicavel() == 1) { nf_venda_ativo = true; break;
	 * }
	 * 
	 * }
	 * 
	 * // checka se ha no minimo uma nf complemento aplicavel for
	 * (CadastroContrato.Carregamento carregamento : carregamentos) { if
	 * (carregamento.getNf_complemento_aplicavel() == 1) { nf_complemento_ativo =
	 * true; break; }
	 * 
	 * }
	 * 
	 * 
	 * for (CadastroContrato.Carregamento carregamento : carregamentos) {
	 * 
	 * GerenciarBancoContratos gerenciar = new GerenciarBancoContratos(); // pegar
	 * dados do contrato CadastroContrato contrato_destinatario =
	 * gerenciar.getContrato(carregamento.getId_contrato());
	 * 
	 * // pegar cliente GerenciarBancoClientes gerenciar_clientes = new
	 * GerenciarBancoClientes(); CadastroCliente cliente_carregamento =
	 * gerenciar_clientes.getCliente(carregamento.getId_cliente()); String
	 * nome_cliente; if (cliente_carregamento.getTipo_pessoa() == 0) { // pessoa
	 * fisica nome_cliente = cliente_carregamento.getNome_empresarial(); } else {
	 * nome_cliente = cliente_carregamento.getNome_fantaia(); }
	 * 
	 * String nome_cliente_completo = nome_cliente;
	 * 
	 * String nome_cliente_quebrado[] = nome_cliente.split(" "); try {
	 * 
	 * 
	 * if(nome_cliente_quebrado.length > 2) { if(nome_cliente_quebrado[2].length() >
	 * 1) { nome_cliente = nome_cliente_quebrado[0] + " " +
	 * nome_cliente_quebrado[2]; }else { if(nome_cliente_quebrado[3].length() > 1) {
	 * nome_cliente = nome_cliente_quebrado[0] + " "+ nome_cliente_quebrado[3];
	 * 
	 * }else { nome_cliente = nome_cliente_quebrado[0] + " "+
	 * nome_cliente_quebrado[1];
	 * 
	 * } } }
	 * 
	 * }catch(Exception v) { nome_cliente = nome_cliente_completo; }
	 * 
	 * 
	 * // pegar vendedor CadastroCliente vendedor_carregamento =
	 * gerenciar_clientes.getCliente(carregamento.getId_vendedor()); String
	 * nome_vendedor; if (vendedor_carregamento.getTipo_pessoa() == 0) { // pessoa
	 * fisica nome_vendedor = vendedor_carregamento.getNome_empresarial(); } else {
	 * nome_vendedor = vendedor_carregamento.getNome_fantaia(); }
	 * 
	 * 
	 * String nome_vendedor_completo = nome_vendedor;
	 * 
	 * String nome_vendedor_quebrado[] = nome_vendedor.split(" "); try {
	 * 
	 * 
	 * if(nome_vendedor_quebrado.length > 2) { if(nome_vendedor_quebrado[2].length()
	 * > 1) { nome_vendedor = nome_vendedor_quebrado[0] + " " +
	 * nome_vendedor_quebrado[2]; }else { if(nome_vendedor_quebrado[3].length() > 1)
	 * { nome_vendedor = nome_vendedor_quebrado[0] + " "+ nome_vendedor_quebrado[3];
	 * 
	 * }else { nome_vendedor = nome_vendedor_quebrado[0] + " "+
	 * nome_vendedor_quebrado[1];
	 * 
	 * } } }
	 * 
	 * }catch(Exception v) { nome_vendedor = nome_vendedor_completo; }
	 * 
	 * String nome_transportador = ""; String placa_trator = "";
	 * if(carregamento.getId_transportador() > 0 ) { // pegar transportador e
	 * veiculo CadastroCliente transportador_carregamento = gerenciar_clientes
	 * .getCliente(carregamento.getId_transportador());
	 * 
	 * if(transportador_carregamento != null) {
	 * if(transportador_carregamento.getTipo_pessoa() == 0) { nome_transportador =
	 * transportador_carregamento.getNome_empresarial(); }else { nome_transportador
	 * = transportador_carregamento.getNome_fantaia();
	 * 
	 * } } CadastroCliente.Veiculo veiculo_carregamento = null ;
	 * if(carregamento.getId_veiculo() > 0) { for (CadastroCliente.Veiculo veiculo :
	 * transportador_carregamento.getVeiculos()) { if (veiculo.getId_veiculo() ==
	 * carregamento.getId_veiculo()) { veiculo_carregamento = veiculo; break; } }
	 * 
	 * if(veiculo_carregamento != null) { placa_trator =
	 * veiculo_carregamento.getPlaca_trator(); } }
	 * 
	 * } // pegar o produto GerenciarBancoProdutos gerenciar_produtos = new
	 * GerenciarBancoProdutos(); CadastroProduto produto_carregamento =
	 * gerenciar_produtos.getProduto(carregamento.getId_produto());
	 * 
	 * //codigos String codigo_romaneio = ""; String codigo_nf_venda1 = "",
	 * codigo_nf_complemento = ""; //pesos
	 * 
	 * double peso_romaneio = 0.0; double peso_nf_venda1 = 0.0; double
	 * peso_nf_interna = 0.0;
	 * 
	 * BigDecimal valor_nf_venda1 = BigDecimal.ZERO; double peso_nf_complemento =
	 * 0.0; BigDecimal valor_nf_complemento = BigDecimal.ZERO;
	 * 
	 * 
	 * 
	 * 
	 * 
	 * try { if(checkString(carregamento.getCodigo_romaneio())){ //procurar por
	 * romaneio if(checkString(carregamento.getCaminho_romaneio())){
	 * ManipularRomaneios manipular = new ManipularRomaneios("");
	 * 
	 * CadastroRomaneio romaneio = manipular.filtrar(new File(servidor_unidade +
	 * carregamento.getCaminho_romaneio())); codigo_romaneio =
	 * Integer.toString(romaneio.getNumero_romaneio()); peso_romaneio =
	 * romaneio.getPeso_liquido();
	 * 
	 * }else { codigo_romaneio = carregamento.getCodigo_romaneio(); peso_romaneio =
	 * carregamento.getPeso_romaneio(); }
	 * 
	 * } }catch(Exception e) { ////JOptionPane.showMessageDialog(isto,
	 * "Romaneio não Localizado"); codigo_romaneio =
	 * carregamento.getCodigo_romaneio(); peso_romaneio =
	 * carregamento.getPeso_romaneio(); }
	 * 
	 * 
	 * 
	 * 
	 * //nf venda 1 try { if(checkString(carregamento.getCodigo_nf_venda1())){
	 * if(carregamento.getCaminho_nf_venda1().length() > 10) { //procurar por nf
	 * venda ManipularNotasFiscais manipular = new ManipularNotasFiscais("");
	 * CadastroNFe nota_fiscal_venda = manipular.filtrar(new File(servidor_unidade +
	 * carregamento.getCodigo_nf_venda1())); codigo_nf_venda1 =
	 * nota_fiscal_venda.getNfe(); peso_nf_venda1 =
	 * Double.parseDouble(nota_fiscal_venda.getQuantidade()); try { valor_nf_venda1
	 * = new BigDecimal(nota_fiscal_venda.getValor()); }catch(Exception e) {
	 * valor_nf_venda1 = BigDecimal.ZERO; }
	 * 
	 * }else { codigo_nf_venda1 = carregamento.getCodigo_nf_venda1(); peso_nf_venda1
	 * = carregamento.getPeso_nf_venda1(); valor_nf_venda1 =
	 * carregamento.getValor_nf_venda1();
	 * 
	 * 
	 * }
	 * 
	 * 
	 * } }catch(Exception e) { ////JOptionPane.showMessageDialog(isto,
	 * "Nota Fiscal de venda não Localizado"); codigo_nf_venda1 =
	 * carregamento.getCodigo_nf_venda1(); peso_nf_venda1 =
	 * carregamento.getPeso_nf_venda1(); valor_nf_venda1 =
	 * carregamento.getValor_nf_venda1();
	 * 
	 * }
	 * 
	 * 
	 * 
	 * 
	 * //nf complemento try {
	 * if(checkString(carregamento.getCodigo_nf_complemento())){
	 * if(carregamento.getCaminho_nf_complemento().length() > 10) { //procurar por
	 * nf remessa ManipularNotasFiscais manipular = new ManipularNotasFiscais("");
	 * CadastroNFe nota_fiscal_complemento = manipular.filtrar(new
	 * File(servidor_unidade + carregamento.getCaminho_nf_complemento()));
	 * codigo_nf_complemento = nota_fiscal_complemento.getNfe();
	 * peso_nf_complemento=
	 * Double.parseDouble(nota_fiscal_complemento.getQuantidade()); try {
	 * valor_nf_complemento = new BigDecimal(nota_fiscal_complemento.getValor());
	 * }catch(Exception e) { valor_nf_complemento = BigDecimal.ZERO; }
	 * 
	 * }else { codigo_nf_complemento = carregamento.getCodigo_nf_complemento();
	 * peso_nf_complemento= carregamento.getPeso_nf_complemento();
	 * valor_nf_complemento = carregamento.getValor_nf_complemento();
	 * 
	 * }
	 * 
	 * 
	 * 
	 * } }catch(Exception e) { ////JOptionPane.showMessageDialog(isto,
	 * "Nota Fiscal de remessa não Localizado");
	 * 
	 * codigo_nf_complemento = carregamento.getCodigo_nf_complemento();
	 * peso_nf_complemento= carregamento.getPeso_nf_complemento();
	 * valor_nf_complemento = carregamento.getValor_nf_complemento();
	 * 
	 * }
	 * 
	 * 
	 * 
	 * 
	 * String cor = "000000";
	 * 
	 * if((peso_nf_venda1 + peso_nf_complemento) >= peso_romaneio) { //ok cor =
	 * "FFFFFF"; }else if((peso_nf_venda1 + peso_nf_complemento) < peso_romaneio) {
	 * cor = "FFFF00"; }
	 * 
	 * tableRowOne = table.getRow(i); tableRowOne.getCell(0).removeParagraph(0);
	 * paragraph = tableRowOne.getCell(0).addParagraph();
	 * criarParagrafoTabela(paragraph, contrato_destinatario.getCodigo(), false);
	 * 
	 * tableRowOne = table.getRow(i); tableRowOne.getCell(1).removeParagraph(0);
	 * paragraph = tableRowOne.getCell(1).addParagraph();
	 * criarParagrafoTabela(paragraph, carregamento.getData(), false);
	 * 
	 * tableRowOne = table.getRow(i); tableRowOne.getCell(2).removeParagraph(0);
	 * paragraph = tableRowOne.getCell(2).addParagraph();
	 * criarParagrafoTabela(paragraph, nome_cliente, false);
	 * 
	 * tableRowOne = table.getRow(i); tableRowOne.getCell(3).removeParagraph(0);
	 * paragraph = tableRowOne.getCell(3).addParagraph();
	 * criarParagrafoTabela(paragraph, nome_vendedor, false);
	 * 
	 * 
	 * tableRowOne = table.getRow(i); tableRowOne.getCell(4).removeParagraph(0);
	 * paragraph = tableRowOne.getCell(4).addParagraph();
	 * criarParagrafoTabela(paragraph, nome_transportador, false);
	 * 
	 * tableRowOne = table.getRow(i); tableRowOne.getCell(5).removeParagraph(0);
	 * paragraph = tableRowOne.getCell(5).addParagraph();
	 * criarParagrafoTabela(paragraph, placa_trator, false);
	 * 
	 * tableRowOne = table.getRow(i); tableRowOne.getCell(6).removeParagraph(0);
	 * paragraph = tableRowOne.getCell(6).addParagraph();
	 * criarParagrafoTabela(paragraph, produto_carregamento.getNome_produto(),
	 * false);
	 * 
	 * tableRowOne = table.getRow(i); tableRowOne.getCell(7).removeParagraph(0);
	 * paragraph = tableRowOne.getCell(7).addParagraph();
	 * criarParagrafoTabela(paragraph, carregamento.getCodigo_romaneio(), false);
	 * 
	 * 
	 * tableRowOne = table.getRow(i); tableRowOne.getCell(8).removeParagraph(0);
	 * paragraph = tableRowOne.getCell(8).addParagraph();
	 * criarParagrafoTabela(paragraph, z.format(peso_romaneio) + " Kgs", false);
	 * tableRowOne.getCell(8).getCTTc().addNewTcPr().addNewShd().setFill(cor);
	 * 
	 * tableRowOne = table.getRow(i); tableRowOne.getCell(9).removeParagraph(0);
	 * paragraph = tableRowOne.getCell(9).addParagraph();
	 * if(carregamento.getNf_venda1_aplicavel() == 1)
	 * criarParagrafoTabela(paragraph, codigo_nf_venda1, false); else
	 * criarParagrafoTabela(paragraph, "Não Aplicável", false);
	 * 
	 * 
	 * tableRowOne = table.getRow(i); tableRowOne.getCell(10).removeParagraph(0);
	 * paragraph = tableRowOne.getCell(10).addParagraph();
	 * if(carregamento.getNf_venda1_aplicavel() == 1) {
	 * criarParagrafoTabela(paragraph, z.format(peso_nf_venda1) + " Kgs", false);
	 * tableRowOne.getCell(10).getCTTc().addNewTcPr().addNewShd().setFill(cor);
	 * }else { criarParagrafoTabela(paragraph, "Não Aplicável", false); }
	 * 
	 * tableRowOne = table.getRow(i); tableRowOne.getCell(11).removeParagraph(0);
	 * paragraph = tableRowOne.getCell(11).addParagraph();
	 * if(carregamento.getNf_venda1_aplicavel() == 1) {
	 * criarParagrafoTabela(paragraph, NumberFormat.getCurrencyInstance(ptBr)
	 * .format(valor_nf_venda1), false); }else { criarParagrafoTabela(paragraph,
	 * "Não Aplicável", false); }
	 * 
	 * tableRowOne = table.getRow(i); tableRowOne.getCell(12).removeParagraph(0);
	 * paragraph = tableRowOne.getCell(12).addParagraph();
	 * if(carregamento.getNf_complemento_aplicavel() == 1)
	 * criarParagrafoTabela(paragraph, codigo_nf_complemento, false); else
	 * criarParagrafoTabela(paragraph, "Não Aplicável", false);
	 * 
	 * tableRowOne = table.getRow(i); tableRowOne.getCell(13).removeParagraph(0);
	 * paragraph = tableRowOne.getCell(13).addParagraph();
	 * if(carregamento.getNf_complemento_aplicavel() == 1) {
	 * criarParagrafoTabela(paragraph, z.format(peso_nf_complemento) + " Kgs",
	 * false);
	 * tableRowOne.getCell(13).getCTTc().addNewTcPr().addNewShd().setFill(cor);
	 * }else { criarParagrafoTabela(paragraph, "Não Aplicável", false);
	 * 
	 * } tableRowOne = table.getRow(i); tableRowOne.getCell(14).removeParagraph(0);
	 * paragraph = tableRowOne.getCell(14).addParagraph();
	 * if(carregamento.getNf_complemento_aplicavel() == 1)
	 * criarParagrafoTabela(paragraph, NumberFormat.getCurrencyInstance(ptBr)
	 * .format(valor_nf_complemento), false); else {
	 * criarParagrafoTabela(paragraph,"Não Aplicável", false); }
	 * 
	 * 
	 * tableRowOne = table.getRow(i); tableRowOne.getCell(15).removeParagraph(0);
	 * paragraph = tableRowOne.getCell(15).addParagraph();
	 * if(carregamento.getNf_venda1_aplicavel() == 1 &&
	 * carregamento.getNf_complemento_aplicavel() == 1) {
	 * criarParagrafoTabela(paragraph,z.format(peso_romaneio - (peso_nf_complemento
	 * + peso_nf_venda1) ) + " Kgs", false);
	 * tableRowOne.getCell(15).getCTTc().addNewTcPr().addNewShd().setFill(cor);
	 * }else { criarParagrafoTabela(paragraph,"Não Aplicável", false);
	 * 
	 * }
	 * 
	 * peso_total_romaneios += peso_romaneio; peso_total_nf_interna +=
	 * peso_nf_interna; peso_total_nf_venda1 += peso_nf_venda1;
	 * peso_total_nf_complemento += peso_nf_complemento; peso_total_diferenca +=
	 * (peso_romaneio - (peso_nf_complemento + peso_nf_venda1));
	 * 
	 * valor_total_nf_venda1 = valor_total_nf_venda1.add(valor_nf_venda1);
	 * valor_total_nf_complemento =
	 * valor_total_nf_complemento.add(valor_nf_complemento);
	 * 
	 * 
	 * i++; } //informacoes de total na tabela //somatoria dos romaneios
	 * 
	 * //peso total romaneios tableRowOne = table.getRow(i);
	 * tableRowOne.getCell(8).removeParagraph(0); paragraph =
	 * tableRowOne.getCell(8).addParagraph(); criarParagrafoTabela(paragraph,
	 * z.format(peso_total_romaneios) + " Kgs", true);
	 * 
	 * //peso total nf venda 1 tableRowOne = table.getRow(i);
	 * tableRowOne.getCell(10).removeParagraph(0); paragraph =
	 * tableRowOne.getCell(10).addParagraph(); criarParagrafoTabela(paragraph,
	 * z.format(peso_total_nf_venda1) + " Kgs", true);
	 * 
	 * //valor total nf venda 1 tableRowOne = table.getRow(i);
	 * tableRowOne.getCell(11).removeParagraph(0); paragraph =
	 * tableRowOne.getCell(11).addParagraph();
	 * criarParagrafoTabela(paragraph,NumberFormat.getCurrencyInstance(ptBr)
	 * .format(valor_total_nf_venda1) , true);
	 * 
	 * //peso total nf complemento tableRowOne = table.getRow(i);
	 * tableRowOne.getCell(13).removeParagraph(0); paragraph =
	 * tableRowOne.getCell(13).addParagraph(); criarParagrafoTabela(paragraph,
	 * z.format(peso_total_nf_complemento) + " Kgs", true);
	 * 
	 * tableRowOne = table.getRow(i); tableRowOne.getCell(14).removeParagraph(0);
	 * paragraph = tableRowOne.getCell(14).addParagraph();
	 * criarParagrafoTabela(paragraph,NumberFormat.getCurrencyInstance(ptBr)
	 * .format(valor_total_nf_complemento) , true);
	 * 
	 * //diferenca
	 * 
	 * tableRowOne = table.getRow(i); tableRowOne.getCell(15).removeParagraph(0);
	 * paragraph = tableRowOne.getCell(15).addParagraph();
	 * criarParagrafoTabela(paragraph, z.format(peso_total_diferenca) + " Kgs",
	 * true);
	 * 
	 * 
	 * //informacoes de total String texto = "";
	 * 
	 * ///////////////////////////////////////////////////////////////// if
	 * (peso_total_romaneios < quantidade_kg) { // totais texto = texto +
	 * "\nPeso Carregado: ["; texto = texto + z.format(peso_total_romaneios) +
	 * " Kgs]";
	 * 
	 * texto = texto + " Peso NF's Interna: ["; if (nf_interna_ativo) {
	 * 
	 * texto = texto + z.format(peso_total_nf_interna) + " Kgs]"; } else { texto =
	 * texto + " Não Aplicável";
	 * 
	 * }
	 * 
	 * texto = texto + " Peso NF's Venda: [";
	 * 
	 * 
	 * if (nf_venda_ativo || nf_complemento_ativo) { texto = texto +
	 * (z.format(peso_total_nf_venda1 + peso_total_nf_complemento) + " Kgs]");
	 * 
	 * } else { texto = texto + " Não Aplicável";
	 * 
	 * } texto = texto + " Valor NF's Venda: [";
	 * 
	 * 
	 * BigDecimal soma = valor_total_nf_venda1.add(valor_total_nf_complemento);
	 * 
	 * if (nf_venda_ativo || nf_complemento_ativo) { texto = texto +
	 * NumberFormat.getCurrencyInstance(ptBr).format(soma.doubleValue()) + "]";
	 * 
	 * } else { texto = texto + " Não Aplicável";
	 * 
	 * } texto = texto + "\n";
	 * 
	 * 
	 * texto = texto + "Peso a Carregar: [";
	 * 
	 * texto = texto + z.format(quantidade_kg - peso_total_romaneios) + " Kgs]";
	 * 
	 * texto = texto + " Peso NF' Interna a Emitir: [";
	 * 
	 * 
	 * if (nf_interna_ativo) { texto = texto + z.format(peso_total_romaneios -
	 * peso_total_nf_interna) + " Kgs]"; } else { texto = texto + " Não Aplicável";
	 * 
	 * }
	 * 
	 * texto = texto + " Peso NF's Venda a Emitir: [";
	 * 
	 * 
	 * 
	 * if (nf_venda_ativo || nf_complemento_ativo) { texto = texto + z
	 * .format(peso_total_romaneios - (peso_total_nf_venda1 +
	 * peso_total_nf_complemento)) + " Kgs]";
	 * 
	 * } else { texto = texto + " Não Aplicável";
	 * 
	 * } texto = texto + "\n";
	 * 
	 * texto = texto +
	 * "*Pesos restantes calculados a partir do peso total já carregado";
	 * 
	 * // status baseado no peso total ja carregado
	 * 
	 * texto = texto + "\n\n";
	 * 
	 * 
	 * texto = texto +
	 * "Status parcial gerado de forma automatica calculados a partir do peso total já carregado"
	 * ;
	 * 
	 * texto = texto + "\n";
	 * 
	 * 
	 * double diferenca = quantidade_kg - peso_total_romaneios; if (diferenca == 0)
	 * { texto = texto + "Carregamento Concluído"; } else if (diferenca > 0) { texto
	 * = texto + "Carregamento Incompleto, [falta carregar " + z.format(diferenca) +
	 * " Kgs]";
	 * 
	 * } else if (diferenca < 0) { texto = texto +
	 * "Carregamento Excedido, [excedeu " + z.format(diferenca) + " Kgs]";
	 * 
	 * }
	 * 
	 * texto = texto + "\n";
	 * 
	 * String texto_nf_remessa = ""; double diferenca_nf_remessa =
	 * peso_total_romaneios - peso_total_nf_interna; if (diferenca_nf_remessa == 0)
	 * { texto_nf_remessa = texto_nf_remessa +
	 * "[Emissão de NF's Interna Concluído]"; } else if (diferenca_nf_remessa > 0) {
	 * texto_nf_remessa = texto_nf_remessa +
	 * "Emissão de NF's Interna Incompleto, [falta emitir " +
	 * z.format(diferenca_nf_remessa) + " Kgs]";
	 * 
	 * } else if (diferenca_nf_remessa < 0) { texto_nf_remessa = texto_nf_remessa +
	 * "Emissão de NF's Interna, [excedeu " + z.format(diferenca_nf_remessa) +
	 * " Kgs]";
	 * 
	 * }
	 * 
	 * if (nf_interna_ativo) texto = texto + (texto_nf_remessa); else texto = texto
	 * + "Emissão de NF's Interna Não Aplicável";
	 * 
	 * texto = texto + "\n";
	 * 
	 * // status de nf de venda
	 * 
	 * String texto_nf_venda = ""; double diferenca_nf_venda = peso_total_romaneios
	 * - (peso_total_nf_venda1 + peso_total_nf_complemento); if (diferenca_nf_venda
	 * == 0) { texto_nf_venda = texto_nf_venda +
	 * "[Emissão de NF's de Venda Concluído]"; } else if (diferenca_nf_venda > 0) {
	 * texto_nf_venda = texto_nf_venda +
	 * "Emissão de NF's de Venda Incompleto, [falta emitir " +
	 * z.format(diferenca_nf_venda) + " Kgs]";
	 * 
	 * } else if (diferenca_nf_venda < 0) { texto_nf_venda = texto_nf_venda +
	 * "Emissão de NF's Excedido, excedeu " + z.format(diferenca_nf_venda) + " Kgs";
	 * 
	 * } if (nf_venda_ativo || nf_complemento_ativo) texto = texto +
	 * (texto_nf_venda); else texto = texto +
	 * "Emissão de NF's de Venda Não Aplicável";
	 * 
	 * }
	 * 
	 * // pesos baseados no total texto = texto + "\n\n"; // totais texto = texto +
	 * "Peso Carregado: ["; texto = texto + z.format(peso_total_romaneios) +
	 * " Kgs]";
	 * 
	 * texto = texto + " Peso NF's Interna: ["; if (nf_interna_ativo) {
	 * 
	 * texto = texto + z.format(peso_total_nf_interna) + " Kgs]"; } else { texto =
	 * texto + " [Não Aplicável]";
	 * 
	 * }
	 * 
	 * texto = texto + " Peso NF's Venda: [";
	 * 
	 * 
	 * if (nf_venda_ativo || nf_complemento_ativo) { texto = texto +
	 * (z.format(peso_total_nf_venda1 + peso_total_nf_complemento) + " Kgs]");
	 * 
	 * } else { texto = texto + " [Não Aplicável]";
	 * 
	 * } texto = texto + " Valor NF's Venda: [";
	 * 
	 * 
	 * BigDecimal soma = valor_total_nf_venda1.add(valor_total_nf_complemento);
	 * 
	 * if (nf_venda_ativo || nf_complemento_ativo) { texto = texto +
	 * NumberFormat.getCurrencyInstance(ptBr).format(soma.doubleValue()) + "]";
	 * 
	 * } else { texto = texto + " [Não Aplicável]";
	 * 
	 * } texto = texto + "\n";
	 * 
	 * 
	 * texto = texto + "Peso a Carregar: [";
	 * 
	 * texto = texto + z.format(quantidade_kg - peso_total_romaneios) + " Kgs]";
	 * 
	 * texto = texto + " Peso NF's Interna a Emitir: [";
	 * 
	 * 
	 * if (nf_interna_ativo) { texto = texto + z.format(quantidade_kg -
	 * peso_total_nf_interna) + " Kgs]"; } else { texto = texto +
	 * " [Não Aplicável]";
	 * 
	 * }
	 * 
	 * texto = texto + " Peso NF's Venda a Emitir: [";
	 * 
	 * 
	 * 
	 * if (nf_venda_ativo || nf_complemento_ativo) { texto = texto + z
	 * .format(quantidade_kg - (peso_total_nf_venda1 + peso_total_nf_complemento)) +
	 * " Kgs]";
	 * 
	 * } else { texto = texto + " [Não Aplicável]";
	 * 
	 * } texto = texto + "\n";
	 * 
	 * texto = texto + "*Pesos restantes calculados a partir do peso total";
	 * 
	 * // status baseado no peso total ja carregado
	 * 
	 * texto = texto + "\n\n";
	 * 
	 * 
	 * texto = texto +
	 * "[Status parcial gerado de forma automatica calculados a partir do peso total:]"
	 * ;
	 * 
	 * texto = texto + "\n";
	 * 
	 * 
	 * double diferenca = quantidade_kg - peso_total_romaneios; if (diferenca == 0)
	 * { texto = texto + "[Carregamento Concluído]"; } else if (diferenca > 0) {
	 * texto = texto + "Carregamento Incompleto, [falta carregar " +
	 * z.format(diferenca) + " Kgs]";
	 * 
	 * } else if (diferenca < 0) { texto = texto +
	 * "Carregamento Excedido, [excedeu " + z.format(diferenca) + " Kgs]";
	 * 
	 * }
	 * 
	 * texto = texto + "\n";
	 * 
	 * String texto_nf_remessa = ""; double diferenca_nf_remessa = quantidade_kg -
	 * peso_total_nf_interna; if (diferenca_nf_remessa == 0) { texto_nf_remessa =
	 * texto_nf_remessa + "[Emissão de NF's Interna Concluído]"; } else if
	 * (diferenca_nf_remessa > 0) { texto_nf_remessa = texto_nf_remessa +
	 * "Emissão de NF's Interna Incompleto, [falta emitir " +
	 * z.format(diferenca_nf_remessa) + " Kgs]";
	 * 
	 * } else if (diferenca_nf_remessa < 0) { texto_nf_remessa = texto_nf_remessa +
	 * "Emissão de NF's Interna, [excedeu " + z.format(diferenca_nf_remessa) +
	 * " Kgs]";
	 * 
	 * }
	 * 
	 * if (nf_interna_ativo) texto = texto + (texto_nf_remessa); else texto = texto
	 * + "Emissão de NF's Interna Não Aplicável";
	 * 
	 * texto = texto + "\n"; // status de nf de venda
	 * 
	 * String texto_nf_venda = ""; double diferenca_nf_venda = quantidade_kg -
	 * (peso_total_nf_venda1 + peso_total_nf_complemento); if (diferenca_nf_venda ==
	 * 0) { texto_nf_venda = texto_nf_venda +
	 * "[Emissão de NF's de Venda Concluído]"; } else if (diferenca_nf_venda > 0) {
	 * texto_nf_venda = texto_nf_venda +
	 * "Emissão de NF's de Venda Incompleto, [falta emitir " +
	 * z.format(diferenca_nf_venda) + " Kgs]";
	 * 
	 * } else if (diferenca_nf_venda < 0) { texto_nf_venda = texto_nf_venda +
	 * "Emissão de NF's Excedido, [excedeu " + z.format(diferenca_nf_venda) +
	 * " Kgs]";
	 * 
	 * } if (nf_venda_ativo || nf_complemento_ativo) texto = texto +
	 * (texto_nf_venda); else texto = texto +
	 * "Emissão de NF's de Venda Não Aplicável";
	 * 
	 * 
	 * 
	 * /////////////////////////////////////////////////////////////////////////////
	 * /// substituirTexto(-1,texto);
	 * 
	 * texto = "";
	 * 
	 * texto = "Peso Total Romaneios: [" + z.format(peso_total_romaneios) +
	 * " kgs ] | [" + z.format(peso_total_romaneios/60) + " sacos ]\n\n";
	 * if(nf_venda_ativo || nf_complemento_ativo) { texto = texto +
	 * "Peso Total NF's Venda 1: [" + z.format(peso_total_nf_venda1) + " kgs ] | ["
	 * + z.format(peso_total_nf_venda1/60) + " sacos ]\n"; texto = texto +
	 * "Peso Total NF's Complemento: [" + z.format(peso_total_nf_complemento) +
	 * " kgs ] | [" + z.format(peso_total_nf_complemento/60) + " sacos ]\n"; texto =
	 * texto + "Peso Total NF's: [" + z.format(peso_total_nf_complemento +
	 * peso_total_nf_venda1)+ " kgs ] | [" + z.format((peso_total_nf_complemento +
	 * peso_total_nf_venda1)/60) + " sacos ]\n"; texto = texto +
	 * "Diferença Total: [" + z.format(peso_total_romaneios -
	 * (peso_total_nf_complemento + peso_total_nf_venda1)) + " kgs ] | [" +
	 * z.format((peso_total_romaneios - (peso_total_nf_complemento +
	 * peso_total_nf_venda1))/60) + " sacos ]\n"; texto = texto +
	 * "Valor Total NF's Venda 1: [" + NumberFormat.getCurrencyInstance(ptBr)
	 * .format(valor_total_nf_venda1) + "]\n"; texto = texto +
	 * "Valor Total NF's Complemento: [" + NumberFormat.getCurrencyInstance(ptBr)
	 * .format(valor_total_nf_complemento) + "]\n"; texto = texto +
	 * "Valor Total NF's: [" + NumberFormat.getCurrencyInstance(ptBr)
	 * .format(valor_total_nf_complemento.add(valor_total_nf_venda1)) + "]\n"; }else
	 * { texto = texto + "NF's Venda Não Aplicável"; }
	 * 
	 * substituirTexto(1,texto);
	 * 
	 * }
	 */

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
			num_colunas = 12;
			table = document_global.createTable(num_total_linhas + 1, num_colunas);
		} else {
			num_colunas = 10;
			table = document_global.createTable(num_total_linhas + 1, num_colunas);

		}

		setTableAlign(table, ParagraphAlignment.CENTER);
		XWPFTableRow tableRowOne = table.getRow(0);
		tableRowOne.getCell(0).removeParagraph(0);

		XWPFParagraph paragraph = tableRowOne.getCell(0).addParagraph();

		criarParagrafoTabela(paragraph, "Codigo", false, "000000");

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(1).removeParagraph(0);
		paragraph = tableRowOne.getCell(1).addParagraph();
		criarParagrafoTabela(paragraph, "", false, "000000");

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(2).removeParagraph(0);
		paragraph = tableRowOne.getCell(2).addParagraph();
		criarParagrafoTabela(paragraph, "Comprador", false, "000000");

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(3).removeParagraph(0);
		paragraph = tableRowOne.getCell(3).addParagraph();
		criarParagrafoTabela(paragraph, "Vendedores", false, "000000");

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(4).removeParagraph(0);
		paragraph = tableRowOne.getCell(4).addParagraph();
		criarParagrafoTabela(paragraph, "Produto", false, "000000");

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(5).removeParagraph(0);
		paragraph = tableRowOne.getCell(5).addParagraph();
		criarParagrafoTabela(paragraph, "Safra", false, "000000");

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(6).removeParagraph(0);
		paragraph = tableRowOne.getCell(6).addParagraph();
		criarParagrafoTabela(paragraph, "Valor", false, "000000");

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(7).removeParagraph(0);
		paragraph = tableRowOne.getCell(7).addParagraph();
		criarParagrafoTabela(paragraph, "Quantidade", false, "000000");

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(8).removeParagraph(0);
		paragraph = tableRowOne.getCell(8).addParagraph();
		criarParagrafoTabela(paragraph, "Valor Total", false, "000000");

		if (incluir_comissao) {

			tableRowOne = table.getRow(0);
			tableRowOne.getCell(9).removeParagraph(0);
			paragraph = tableRowOne.getCell(9).addParagraph();
			criarParagrafoTabela(paragraph, "Comissão(saco)", false, "000000");

			tableRowOne = table.getRow(0);
			tableRowOne.getCell(10).removeParagraph(0);
			paragraph = tableRowOne.getCell(10).addParagraph();
			criarParagrafoTabela(paragraph, "Comissão(Total)", false, "000000");

			tableRowOne = table.getRow(0);
			tableRowOne.getCell(11).removeParagraph(0);
			paragraph = tableRowOne.getCell(11).addParagraph();
			criarParagrafoTabela(paragraph, "Status", false, "000000");
		} else {
			tableRowOne = table.getRow(0);
			tableRowOne.getCell(9).removeParagraph(0);
			paragraph = tableRowOne.getCell(9).addParagraph();
			criarParagrafoTabela(paragraph, "Status", false, "000000");
		}

		int indice = 0;

		double quantitade_total_sacos = 0;
		BigDecimal valor_total = BigDecimal.ZERO;
		BigDecimal valor_total_comissao = BigDecimal.ZERO;

		for (int i = 1; i < lista_contratos.size() + 1; i++) {
			String cor_dados = "000000";
			CadastroContrato local = lista_contratos.get(indice);

			if (local.getSub_contrato() != 8) {
				// é um linha normal
				if (local.getSub_contrato() == 1) {
					// seta a cor vermelha
					cor_dados = "ff0000";
				}

				/*
				if (tipo_contrato == 1) {
					// é um sub contratos
					double quantidade_sacos_sub = 0;
					double quantidade_quilogramas_sub = 0;

					if (local.getMedida().equalsIgnoreCase("Sacos")) {
						quantidade_sacos_sub = local.getQuantidade();
						quantidade_quilogramas_sub = local.getQuantidade() * 60;
					} else if (local.getMedida().equalsIgnoreCase("KG")) {
						quantidade_quilogramas_sub = local.getQuantidade();
						quantidade_sacos_sub = local.getQuantidade() / 60;

					}
					CadastroCliente vendedores[] = local.getVendedores();
					for (CadastroCliente vendedor : vendedores) {
						for (CadastroCliente cliente : clientes_globais) {
							if (vendedor != null) {
								if (vendedor.getId() == cliente.getId()) {
									if (local.getSub_contrato() == 1) {
										cor_dados = "008000";
										numero_contratos++;

										quantidade_total_sacos_vendedor += quantidade_sacos_sub;
										soma_total_valores_vendedor += local.getValor_a_pagar().doubleValue();

									} else {
										// verifica se esse contrato nao tem subcontratos
										GerenciarBancoContratos gerente = new GerenciarBancoContratos();
										ArrayList<CadastroContrato> subs = gerente.getSubContratos(local.getId());
										if (!(subs.size() > 0)) {
											cor_dados = "008000";
											quantidade_total_sacos_vendedor += quantidade_sacos_sub;
											soma_total_valores_vendedor += local.getValor_a_pagar().doubleValue();
											numero_contratos++;

										} else {
											boolean tem_cliente = false;
											// é um contrato original e tem subcontratos, coloque o destacado
											// se seus subcontratos nao tiver o cliente como vendedor
											for (CadastroContrato cont : subs) {
												CadastroCliente vendedores_sub[] = cont.getVendedores();
												for (CadastroCliente cliente_sub : vendedores_sub) {
													for (CadastroCliente cliente_alvo : clientes_globais) {
														if (cliente_sub != null) {
															if (cliente_sub.getId() == cliente_alvo.getId()) {
																tem_cliente = true;
																break;
															}
														}
													}
												}

											}
											if (!tem_cliente) {
												cor_dados = "008000";
												quantidade_total_sacos_vendedor += quantidade_sacos_sub;
												soma_total_valores_vendedor += local.getValor_a_pagar().doubleValue();
												numero_contratos++;

											}

										} // fim

									}

								}
							}
						}
					}
				}*/

				double quantidade_sacos_sub = 0;
				double quantidade_quilogramas_sub = 0;

				if (local.getMedida().equalsIgnoreCase("Sacos")) {
					quantidade_sacos_sub = local.getQuantidade();
					quantidade_quilogramas_sub = local.getQuantidade() * 60;
				} else if (local.getMedida().equalsIgnoreCase("KG")) {
					quantidade_quilogramas_sub = local.getQuantidade();
					quantidade_sacos_sub = local.getQuantidade() / 60;

				}

				CadastroCliente compradores_sub[] = local.getCompradores();
				CadastroCliente vendedores_sub[] = local.getVendedores();
				CadastroCliente corretores_sub[] = local.getCorretores();

				String nome_corretores_sub = "";
				String nome_vendedores_sub = "";
				String nome_compradores_sub = "";

				if (corretores_sub[0] != null) {
					if (corretores_sub[0].getTipo_pessoa() == 0) {
						// pessoa fisica
						nome_corretores_sub = corretores_sub[0].getNome_empresarial();
					} else {
						nome_corretores_sub = corretores_sub[0].getNome_fantaia();

					}
				}

				if (compradores_sub[0] != null) {
					if (compradores_sub[0].getTipo_pessoa() == 0) {
						// pessoa fisica
						nome_compradores_sub = compradores_sub[0].getNome_empresarial();
					} else {
						nome_compradores_sub = compradores_sub[0].getNome_fantaia();

					}
				}
				
				if (compradores_sub[1] != null) {
					if (compradores_sub[1].getTipo_pessoa() == 0) {
						// pessoa fisica
						nome_compradores_sub = nome_compradores_sub + " " + compradores_sub[1].getNome_empresarial();
					} else {
						nome_compradores_sub = nome_compradores_sub + " "+  compradores_sub[1].getNome_fantaia();

					}
				}

				for (CadastroCliente vendedor : vendedores_sub) {
					if (vendedor != null) {
						if (vendedor.getTipo_pessoa() == 0) {
							// pessoa fisica
							nome_vendedores_sub = nome_vendedores_sub + " | " + vendedor.getNome_empresarial();
						} else {
							nome_vendedores_sub = nome_vendedores_sub + " | " + vendedor.getNome_fantaia();

						}
					}
				}

				// linha com dados
				tableRowOne = table.getRow(i);
				tableRowOne.getCell(0).removeParagraph(0);
				paragraph = tableRowOne.getCell(0).addParagraph();
				criarParagrafoTabela(paragraph, local.getCodigo(), false, cor_dados);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(1).removeParagraph(0);
				paragraph = tableRowOne.getCell(1).addParagraph();
				criarParagrafoTabela(paragraph, nome_corretores_sub, false, cor_dados);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(2).removeParagraph(0);
				paragraph = tableRowOne.getCell(2).addParagraph();
				criarParagrafoTabela(paragraph, nome_compradores_sub, false, cor_dados);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(3).removeParagraph(0);
				paragraph = tableRowOne.getCell(3).addParagraph();
				criarParagrafoTabela(paragraph, nome_vendedores_sub, false, cor_dados);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(4).removeParagraph(0);
				paragraph = tableRowOne.getCell(4).addParagraph();
				criarParagrafoTabela(paragraph, local.getModelo_safra().getProduto().getNome_produto(), false,
						cor_dados);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(5).removeParagraph(0);
				paragraph = tableRowOne.getCell(5).addParagraph();
				criarParagrafoTabela(paragraph,
						local.getModelo_safra().getAno_plantio() + "/" + local.getModelo_safra().getAno_colheita(),
						false, cor_dados);

				String valorString = NumberFormat.getCurrencyInstance(ptBr).format(local.getValor_produto());
				tableRowOne = table.getRow(i);
				tableRowOne.getCell(6).removeParagraph(0);
				paragraph = tableRowOne.getCell(6).addParagraph();
				criarParagrafoTabela(paragraph, valorString, false, cor_dados);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(7).removeParagraph(0);
				paragraph = tableRowOne.getCell(7).addParagraph();
				criarParagrafoTabela(paragraph, z.format(local.getQuantidade()), false, cor_dados);

				valorString = NumberFormat.getCurrencyInstance(ptBr).format(local.getValor_a_pagar());
				tableRowOne = table.getRow(i);
				tableRowOne.getCell(8).removeParagraph(0);
				paragraph = tableRowOne.getCell(8).addParagraph();
				criarParagrafoTabela(paragraph, valorString, false, cor_dados);

				String comissao_total = "";
				if (local.getComissao() == 1) {
					comissao_total = NumberFormat.getCurrencyInstance(ptBr).format(local.getValor_comissao());
					if (local.getSub_contrato() == 0 || local.getSub_contrato() == 3 || local.getSub_contrato() == 4)
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
					text_status = "Assinar";

				} else if (status == 2) {
					text_status = "Assinado";

				} else if (status == 3) {
					text_status = "Cumprindo";

				}

				if (incluir_comissao) {
					tableRowOne = table.getRow(i);
					tableRowOne.getCell(9).removeParagraph(0);
					paragraph = tableRowOne.getCell(9).addParagraph();
					criarParagrafoTabela(paragraph, comissao_por_saco, false, cor_dados);

					tableRowOne = table.getRow(i);
					tableRowOne.getCell(10).removeParagraph(0);
					paragraph = tableRowOne.getCell(10).addParagraph();
					criarParagrafoTabela(paragraph, comissao_total, false, cor_dados);

					tableRowOne = table.getRow(i);
					tableRowOne.getCell(11).removeParagraph(0);
					paragraph = tableRowOne.getCell(11).addParagraph();
					criarParagrafoTabela(paragraph, text_status, false, cor_dados);

				} else {
					tableRowOne = table.getRow(i);
					tableRowOne.getCell(9).removeParagraph(0);
					paragraph = tableRowOne.getCell(9).addParagraph();
					criarParagrafoTabela(paragraph, text_status, false, cor_dados);
				}

				if (local.getSub_contrato() == 0 ||  local.getSub_contrato() == 3 || local.getSub_contrato() == 4 || local.getSub_contrato() == 5) {
					quantitade_total_sacos += quantidade_sacos_sub;
					valor_total = valor_total.add(local.getValor_a_pagar());
				} else {

					if (somar_sub_contratos) {
						quantitade_total_sacos += quantidade_sacos_sub;
						valor_total = valor_total.add(local.getValor_a_pagar());
					}

				}

			} else {
				// linha de ganhos potenciais
				// linha com dados

				GanhoPotencial ganho_potencial = new GanhoPotencial();
				String texto = "";
				String s_valor_total_contrato_original= NumberFormat.getCurrencyInstance(ptBr).format(local.getValor_a_pagar());
				ganho_potencial.setCodigo(local.getCodigo());
				ganho_potencial.setTotal_contrato_original(local.getValor_a_pagar());

				texto = texto + "Total(contrato): " + s_valor_total_contrato_original;
				tableRowOne = table.getRow(i);
				tableRowOne.getCell(0).removeParagraph(0);

				paragraph = tableRowOne.getCell(0).addParagraph();

				String s_valor_total_sub_contratos = NumberFormat.getCurrencyInstance(ptBr).format(local.getValor_comissao());
				texto = texto + " Total(sub-contrato): " + s_valor_total_sub_contratos;

				ganho_potencial.setTotal_sub_contratos(local.getValor_comissao());

				String s_diferenca = NumberFormat.getCurrencyInstance(ptBr)
						.format(local.getValor_a_pagar().subtract(local.getValor_comissao()));
				texto = texto + " Diferença: " + s_diferenca;

				ganho_potencial.setDiferenca(local.getValor_a_pagar().subtract(local.getValor_comissao()));

				if (!incluir_comissao) {
					texto = texto + " Ganho Potencial: " + s_diferenca;
					BigDecimal comissao = BigDecimal.ZERO;
					ganho_potencial.setTotal_comissao(comissao);
					ganho_potencial.setGanhos_potenciais(local.getValor_a_pagar().subtract(local.getValor_comissao()));

				} else {

					if (local.getValor_produto() > 0) {
						//tem comissao
						String s_valor_comissao = NumberFormat.getCurrencyInstance(ptBr).format(local.getValor_produto());
						texto = texto + " Comissão: " + s_valor_comissao;
						ganho_potencial.setTotal_comissao(new BigDecimal(local.getValor_produto()));

						BigDecimal diferenca = local.getValor_a_pagar().subtract(local.getValor_comissao());
						double valor_total_ganhos_potenciais = diferenca.doubleValue() + local.getValor_produto();
						String s_valor_ganhos_potenciais = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_ganhos_potenciais);

						texto = texto + "         Ganho Potencial: " + s_valor_ganhos_potenciais;
						ganho_potencial.setGanhos_potenciais(new BigDecimal(valor_total_ganhos_potenciais));

					} else {
						BigDecimal comissao = BigDecimal.ZERO;
						ganho_potencial.setTotal_comissao(comissao);
						BigDecimal diferenca = local.getValor_a_pagar().subtract(local.getValor_comissao());

						
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
			} // fim de linha ganho potencial
			indice++;
		}

		String valorTotalString = NumberFormat.getCurrencyInstance(ptBr).format(valor_total);

		String valorTotalComissaoString = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_comissao);
		criarParagrafo(1);

		if (incluir_comissao) {
			substituirTexto("Quantidade Total de Sacos: [" + z.format(quantitade_total_sacos) + "] sacos Valor Total: ["
					+ valorTotalString + "] Valor Total Comissão: [" + valorTotalComissaoString + "]", 1);

			// valores para relatorio de vendas interno
			if (tipo_contrato == 1) {
				substituirTexto("Quantidade de contratos: " + "[" + numero_contratos + "] contratos "
						+ " Quantidade de sacos: [" + z.format(quantidade_total_sacos_vendedor)
						+ "] sacos Valor Total: ["
						+ NumberFormat.getCurrencyInstance(ptBr).format(soma_total_valores_vendedor) + "]", 1);
			}
		} else {
			substituirTexto("Quantidade Total de Sacos: [" + z.format(quantitade_total_sacos) + "] sacos Valor Total: ["
					+ valorTotalString + "]", 1);
			if (tipo_contrato == 1) {
				substituirTexto("Quantidade de contratos: " + "[" + numero_contratos + "] contratos "
						+ " Quantidade de sacos: [" + z.format(quantidade_total_sacos_vendedor)
						+ "] sacos Valor Total: ["
						+ NumberFormat.getCurrencyInstance(ptBr).format(soma_total_valores_vendedor) + "]", 1);
			}
		}
		criarParagrafo(2);
		substituirTexto("\n\n");

		if (incluir_ganhos_potencias)
			criarTabelaGanhosPotenciais(lista_ganhos_potenciais);

	}

	public void criarTabelaPagamentos(ArrayList<CadastroContrato.CadastroPagamentoContratual> pagamentos,
			CadastroContrato contrato_deste_carregamento) {

		NumberFormat z = NumberFormat.getNumberInstance();

		Locale ptBr = new Locale("pt", "BR");

		// criarParagrafo(1);
		// linhas x colunas
		int linha_titulo = 1;
		int linha_somatoria = 1;

		int num_linhas_pagamentos = pagamentos.size() + linha_titulo + linha_somatoria;
		int num_linhas_carregamentos = -1;
		int soma_total_quantidade_contratos = 0;

		if (soma_total_quantidade_contratos == 0) {

			num_linhas_carregamentos = pagamentos.size() + 1 + 1 + 1 + 1;
		} else {
			num_linhas_carregamentos = pagamentos.size() + 1 + 1 + 1;

		}

		double soma_total_carga_real = 0;
		double soma_total_carga_nfa = 0;

		GerenciarBancoTransferenciasCarga gerenciar_trans = new GerenciarBancoTransferenciasCarga();
		ArrayList<CadastroContrato.CadastroTransferenciaCarga> transferencias_remetentes = null;
		ArrayList<CadastroContrato.CadastroTransferenciaCarga> transferencias_destinatarios = null;

		double quantidade_kg = 0;
		double quantidade_sacos = 0;

		if (contrato_deste_carregamento.getMedida().equalsIgnoreCase("KG")) {
			quantidade_kg = contrato_deste_carregamento.getQuantidade();
			quantidade_sacos = quantidade_kg / 60;
		} else if (contrato_deste_carregamento.getMedida().equalsIgnoreCase("Sacos")) {
			quantidade_sacos = contrato_deste_carregamento.getQuantidade();
			quantidade_kg = quantidade_sacos * 60;
		}

		XWPFTable table = document_global.createTable(num_linhas_carregamentos, 8);

		setTableAlign(table, ParagraphAlignment.CENTER);
		XWPFTableRow tableRowOne = table.getRow(0);
		tableRowOne.getCell(0).removeParagraph(0);

		XWPFParagraph paragraph = tableRowOne.getCell(0).addParagraph();

		//
		int cabecalho = 0;
		soma_total_quantidade_contratos = 0;

		if (soma_total_quantidade_contratos != 0) {

			tableRowOne = table.getRow(cabecalho);
			tableRowOne.getCell(0).removeParagraph(0);
			paragraph = tableRowOne.getCell(0).addParagraph();

			criarParagrafoTabela(paragraph, "Quantidade Total: " + z.format(soma_total_quantidade_contratos) + " kgs | "
					+ z.format(soma_total_quantidade_contratos / 60) + " sacos", true);
			tableRowOne.getCell(0).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");
			CTHMerge hMerge = CTHMerge.Factory.newInstance();
			hMerge.setVal(STMerge.RESTART);
			table.getRow(cabecalho).getCell(0).getCTTc().getTcPr().setHMerge(hMerge);

			for (int celula = 1; celula <= 7; celula++) {
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
			String safra = contrato_deste_carregamento.getModelo_safra().getProduto().getNome_produto() + " "
					+ contrato_deste_carregamento.getModelo_safra().getProduto().getTransgenia() + " "
					+ contrato_deste_carregamento.getModelo_safra().getAno_plantio() + "/"
					+ contrato_deste_carregamento.getModelo_safra().getAno_colheita();

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

			for (int celula = 1; celula <= 7; celula++) {
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

			for (int celula = 1; celula <= 7; celula++) {
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

		tableRowOne = table.getRow(cabecalho);
		tableRowOne.getCell(0).removeParagraph(0);
		paragraph = tableRowOne.getCell(0).addParagraph();
		criarParagrafoTabela(paragraph, "Contrato", false, "000000");

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(1).removeParagraph(0);
		paragraph = tableRowOne.getCell(1).addParagraph();
		criarParagrafoTabela(paragraph, "Data", false, "000000");

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(2).removeParagraph(0);
		paragraph = tableRowOne.getCell(2).addParagraph();
		criarParagrafoTabela(paragraph, "Descrição: ", false, "000000");

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(3).removeParagraph(0);
		paragraph = tableRowOne.getCell(3).addParagraph();
		criarParagrafoTabela(paragraph, "Valor Pagamento: ", false, "000000");

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(4).removeParagraph(0);
		paragraph = tableRowOne.getCell(4).addParagraph();
		criarParagrafoTabela(paragraph, "Valor por Saco: ", false, "000000");

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(5).removeParagraph(0);
		paragraph = tableRowOne.getCell(5).addParagraph();
		criarParagrafoTabela(paragraph, "Cobertura", false, "000000");

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(6).removeParagraph(0);
		paragraph = tableRowOne.getCell(6).addParagraph();
		criarParagrafoTabela(paragraph, "Depositante", false, "000000");

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(7).removeParagraph(0);
		paragraph = tableRowOne.getCell(7).addParagraph();
		criarParagrafoTabela(paragraph, "Favorecido", false, "000000");

		int i_global = i + 1;

		double valor_total = 0;
		double peso_total_cobertura = 0;

		for (CadastroContrato.CadastroPagamentoContratual local : pagamentos) {

			// CadastroContrato.CadastroPagamentoContratual local = pagamentos.get(indice);

			GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
			int id = gerenciar.getContratoDonoPagamento(local.getId_pagamento());
			CadastroContrato contrato = gerenciar.getContrato(id);

			double quantidade_total_contrato_sacos = 0;
			double valor_por_saco = 0;
			double cobertura = 0;

			if (contrato.getMedida().equalsIgnoreCase("Kg")) {
				quantidade_total_contrato_sacos = contrato.getQuantidade() / 60;
				valor_por_saco = contrato.getValor_produto() * 60;

			} else if (contrato.getMedida().equalsIgnoreCase("Sacos")) {
				quantidade_total_contrato_sacos = contrato.getQuantidade();
				valor_por_saco = contrato.getValor_produto();
			}

			cobertura = local.getValor_pagamento() / valor_por_saco;

			tableRowOne = table.getRow(i_global);
			tableRowOne.getCell(0).removeParagraph(0);
			paragraph = tableRowOne.getCell(0).addParagraph();
			criarParagrafoTabela(paragraph, contrato.getCodigo(), false, "000000");

			// celula data

			tableRowOne = table.getRow(i_global);
			tableRowOne.getCell(1).removeParagraph(0);
			paragraph = tableRowOne.getCell(1).addParagraph();
			criarParagrafoTabela(paragraph, local.getData_pagamento(), false, "000000");

			// celula descricao

			tableRowOne = table.getRow(i_global);
			tableRowOne.getCell(2).removeParagraph(0);
			paragraph = tableRowOne.getCell(2).addParagraph();
			criarParagrafoTabela(paragraph, "Pagamento", false, "000000");

			// celula valor
			String valorString = NumberFormat.getCurrencyInstance(ptBr).format(local.getValor_pagamento());
			valor_total += local.getValor_pagamento();

			tableRowOne = table.getRow(i_global);
			tableRowOne.getCell(3).removeParagraph(0);
			paragraph = tableRowOne.getCell(3).addParagraph();
			criarParagrafoTabela(paragraph, valorString, false, "000000");

			String valorStringsacos = NumberFormat.getCurrencyInstance(ptBr).format(valor_por_saco);

			tableRowOne = table.getRow(i_global);
			tableRowOne.getCell(4).removeParagraph(0);
			paragraph = tableRowOne.getCell(4).addParagraph();
			criarParagrafoTabela(paragraph, valorStringsacos, false, "000000");

			tableRowOne = table.getRow(i_global);
			tableRowOne.getCell(5).removeParagraph(0);
			paragraph = tableRowOne.getCell(5).addParagraph();
			criarParagrafoTabela(paragraph, z.format(cobertura) + " Sacos", false, "000000");

			// celula depositante

			GerenciarBancoClientes cliente = new GerenciarBancoClientes();
			CadastroCliente depositante = cliente.getCliente(local.getId_depositante());
			CadastroCliente favorecido = cliente.getCliente(local.getId_favorecido());

			String nome_depositante = "", nome_favorecido = "";

			if (depositante.getTipo_pessoa() == 0)
				nome_depositante = depositante.getNome_empresarial();
			else
				nome_depositante = depositante.getNome_fantaia();

			if (favorecido.getTipo_pessoa() == 0)
				nome_favorecido = favorecido.getNome_empresarial();
			else
				nome_favorecido = favorecido.getNome_fantaia();

			// depositante
			tableRowOne = table.getRow(i_global);
			tableRowOne.getCell(6).removeParagraph(0);
			paragraph = tableRowOne.getCell(6).addParagraph();
			criarParagrafoTabela(paragraph, nome_depositante, false, "000000");
			// celula favorecido

			tableRowOne = table.getRow(i_global);
			tableRowOne.getCell(7).removeParagraph(0);
			paragraph = tableRowOne.getCell(7).addParagraph();
			criarParagrafoTabela(paragraph, nome_favorecido, false, "000000");

			peso_total_cobertura += cobertura;
			i_global++;

		}

		tableRowOne = table.getRow(i_global);
		tableRowOne.getCell(2).removeParagraph(0);
		paragraph = tableRowOne.getCell(2).addParagraph();
		criarParagrafoTabela(paragraph, "Total: ", false, "000000");

		String valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor_total);
		tableRowOne = table.getRow(i_global);
		tableRowOne.getCell(3).removeParagraph(0);
		paragraph = tableRowOne.getCell(3).addParagraph();
		criarParagrafoTabela(paragraph, valorString, false, "000000");

		tableRowOne = table.getRow(i_global);
		tableRowOne.getCell(4).removeParagraph(0);
		paragraph = tableRowOne.getCell(4).addParagraph();
		criarParagrafoTabela(paragraph, "Cobertura: ", false, "000000");

		tableRowOne = table.getRow(i_global);
		tableRowOne.getCell(5).removeParagraph(0);
		paragraph = tableRowOne.getCell(5).addParagraph();
		criarParagrafoTabela(paragraph,
				z.format(peso_total_cobertura * 60) + " Kgs | " + z.format(peso_total_cobertura) + " SCs", false,
				"000000");

		// status automatico
		String texto_status = "\nStatus gerado de forma automatica: \n";
		double diferenca = valor_total - contrato_deste_carregamento.getValor_a_pagar().doubleValue();

		if (diferenca == 0) {
			texto_status = texto_status + ("Pagamento Concluído\n");
		} else if (diferenca > 0) {
			texto_status = texto_status + ("Valor dos pagamentos excederam em ["
					+ NumberFormat.getCurrencyInstance(ptBr).format(diferenca) + "]\n");

		} else if (diferenca < 0) {
			texto_status = texto_status + ("Valor dos total dos pagamentos não concluído, falta pagar ["
					+ NumberFormat.getCurrencyInstance(ptBr).format(diferenca) + "]\n");

		}

		double diferenca_pesos = peso_total_cobertura - quantidade_sacos;

		if (diferenca_pesos == 0 || diferenca_pesos == -0) {
			texto_status = texto_status + ("Todos os sacos foram pagos\n");
		} else if (diferenca_pesos > 0) {
			texto_status = texto_status
					+ ("Valor total da cobertura excedido em [" + z.format(diferenca_pesos) + " Sacos]\n");

		} else if (diferenca_pesos < 0) {
			texto_status = texto_status + ("Valor total dos pagamentos não concluído, falta pagar ["
					+ z.format(diferenca_pesos) + " Sacos]\n");

		}

		substituirTexto(texto_status + "\n");

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
		
		//variaveis de somatorias
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
			somatoria_total_contratos_originais = somatoria_total_contratos_originais.add(ganho.getTotal_contrato_original());

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(2).removeParagraph(0);
			paragraph = tableRowOne.getCell(2).addParagraph();
			String s_valor_total_sub_contratos = NumberFormat.getCurrencyInstance(ptBr)
					.format(ganho.getTotal_sub_contratos());

			criarParagrafoTabela(paragraph, s_valor_total_sub_contratos, false);
			somatoria_total_sub_contratos_originais  = somatoria_total_sub_contratos_originais .add(ganho.getTotal_sub_contratos());

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(3).removeParagraph(0);
			paragraph = tableRowOne.getCell(3).addParagraph();
			String s_total_comissao = NumberFormat.getCurrencyInstance(ptBr).format(ganho.getTotal_comissao());

			criarParagrafoTabela(paragraph, s_total_comissao, false);
			somatoria_total_comissao   = somatoria_total_comissao.add(ganho.getTotal_comissao());

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
			String s_ganho_potencial = NumberFormat.getCurrencyInstance(ptBr).format(ganho.getGanhos_potenciais());

			criarParagrafoTabela(paragraph, s_ganho_potencial, false);
			somatoria_total_ganho_potencial  = somatoria_total_ganho_potencial.add(ganho.getGanhos_potenciais());

			i++;
		}
		
		//criar somatorias
		// somatoria dos pesos
				tableRowOne = table.getRow(i);
				tableRowOne.getCell(0).removeParagraph(0);
				paragraph = tableRowOne.getCell(0).addParagraph();
				criarParagrafoTabela(paragraph,"Total:", true, "000000", 1);

				// somatoria dos pesos
				tableRowOne = table.getRow(i);
				tableRowOne.getCell(1).removeParagraph(0);
				paragraph = tableRowOne.getCell(1).addParagraph();
				String s_somatoria_total_contratos_originais  = NumberFormat.getCurrencyInstance(ptBr).format(somatoria_total_contratos_originais );
				criarParagrafoTabela(paragraph, s_somatoria_total_contratos_originais, true);
				
				tableRowOne = table.getRow(i);
				tableRowOne.getCell(2).removeParagraph(0);
				paragraph = tableRowOne.getCell(2).addParagraph();
				String s_somatoria_total_sub_contratos_originais    = NumberFormat.getCurrencyInstance(ptBr).format(somatoria_total_sub_contratos_originais   );
				criarParagrafoTabela(paragraph, s_somatoria_total_sub_contratos_originais, true);
				
				tableRowOne = table.getRow(i);
				tableRowOne.getCell(3).removeParagraph(0);
				paragraph = tableRowOne.getCell(3).addParagraph();
				String s_somatoria_total_comissao   = NumberFormat.getCurrencyInstance(ptBr).format(somatoria_total_comissao  );
				criarParagrafoTabela(paragraph, s_somatoria_total_comissao, true);
				
				tableRowOne = table.getRow(i);
				tableRowOne.getCell(4).removeParagraph(0);
				paragraph = tableRowOne.getCell(4).addParagraph();
				String s_somatoria_total_diferenca   = NumberFormat.getCurrencyInstance(ptBr).format(somatoria_total_diferenca );
				criarParagrafoTabela(paragraph, s_somatoria_total_diferenca, true);
				
				tableRowOne = table.getRow(i);
				tableRowOne.getCell(5).removeParagraph(0);
				paragraph = tableRowOne.getCell(5).addParagraph();
				String s_somatoria_total_ganho_potencial   = NumberFormat.getCurrencyInstance(ptBr).format(somatoria_total_ganho_potencial  );
				criarParagrafoTabela(paragraph, s_somatoria_total_ganho_potencial, true);
				
				substituirTexto("");

	}
	
	public void criarTabelaInfoGrupo(int numero_total_contratos, double volume_total_sacos, BigDecimal soma_total_pagamentos) {

		NumberFormat z = NumberFormat.getNumberInstance();

		Locale ptBr = new Locale("pt", "BR");

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
		criarParagrafoTabela(paragraph, "TOTAL DE CONTRATOS", true);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(2).removeParagraph(0);
		paragraph = tableRowOne.getCell(2).addParagraph();
		criarParagrafoTabela(paragraph, "% DE CONTRATOS SOBRE O TOTAL", true);

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

		int i = 1;
		GerenciarBancoContratos procura_contratos_grupo = new GerenciarBancoContratos();
		
		for (CadastroCliente cliente : grupo_alvo_global.getClientes()) {

			String nome_cliente = "";
			if (cliente.getTipo_pessoa() == 0) {
				nome_cliente = cliente.getNome_empresarial().toUpperCase();
			} else {
				nome_cliente = cliente.getNome_fantaia().toUpperCase();
			}

			// numero de contratos desde clinete
			ArrayList<CadastroContrato> lista_contratos_como_comprador = new ArrayList<>();

			if (contrato_como_comprador) {
				// //JOptionPane.showMessageDialog(null, "Tipo do contrato: " + tipo_contrato);
				if (tipo_contrato == 1) {
					lista_contratos_como_comprador = procura_contratos_grupo.getContratosPorCliente(4, id_safra,
							cliente.getId());
				} else if (tipo_contrato == 2) {
					lista_contratos_como_comprador = procura_contratos_grupo.getContratosPorCliente(4, id_safra,
							cliente.getId());
				}
			} else {
				// //JOptionPane.showMessageDialog(null, "Pesquisa como vendedor");

				// //JOptionPane.showMessageDialog(null, "Tipo do contrato: " + tipo_contrato);
				if (tipo_contrato == 1) {
					lista_contratos_como_comprador = procura_contratos_grupo.getContratosPorCliente(5, id_safra,
							cliente.getId());
				} else if (tipo_contrato == 2) {
					lista_contratos_como_comprador = procura_contratos_grupo.getContratosPorCliente(5, id_safra,
							cliente.getId());
				}

			}

			ArrayList<CadastroContrato> lista_final = new ArrayList<>();

			lista_final = new ArrayList(lista_contratos_como_comprador.size());

			if (lista_contratos_como_comprador.size() > 0) {
				lista_final.addAll(lista_contratos_como_comprador);

			}

			double quantidade_total_sacos = 0;
			BigDecimal valor_total_pagamentos = BigDecimal.ZERO;

			// quantidade total de sacas
			for (CadastroContrato contrato : lista_final) {
				quantidade_total_sacos += contrato.getQuantidade();
				valor_total_pagamentos = valor_total_pagamentos.add(contrato.getValor_a_pagar());
			}

		

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(0).removeParagraph(0);
		paragraph = tableRowOne.getCell(0).addParagraph();
		criarParagrafoTabela(paragraph, nome_cliente , false);

		
		tableRowOne = table.getRow(i);
		tableRowOne.getCell(1).removeParagraph(0);
		paragraph = tableRowOne.getCell(1).addParagraph();
		criarParagrafoTabela(paragraph,lista_final.size() + "" , false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(2).removeParagraph(0);
		paragraph = tableRowOne.getCell(2).addParagraph();
	
		criarParagrafoTabela(paragraph,( (int) (100 * lista_final.size() /  numero_total_contratos)) + " %", false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(3).removeParagraph(0);
		paragraph = tableRowOne.getCell(3).addParagraph();
		criarParagrafoTabela(paragraph, z.format(quantidade_total_sacos) + " sacos", false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(4).removeParagraph(0);
		paragraph = tableRowOne.getCell(4).addParagraph();
		criarParagrafoTabela(paragraph,( (int) (100 * quantidade_total_sacos / volume_total_sacos)) + " %", false);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(5).removeParagraph(0);
		paragraph = tableRowOne.getCell(5).addParagraph();
		String valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos);
		criarParagrafoTabela(paragraph, valorString, false);
		i++;
	}
		
		tableRowOne = table.getRow(i);
		tableRowOne.getCell(1).removeParagraph(0);
		paragraph = tableRowOne.getCell(1).addParagraph();
		criarParagrafoTabela(paragraph, z.format(numero_total_contratos) , false);
		
		tableRowOne = table.getRow(i);
		tableRowOne.getCell(2).removeParagraph(0);
		paragraph = tableRowOne.getCell(2).addParagraph();
		criarParagrafoTabela(paragraph, "100%", false);
		
		tableRowOne = table.getRow(i);
		tableRowOne.getCell(3).removeParagraph(0);
		paragraph = tableRowOne.getCell(3).addParagraph();
		criarParagrafoTabela(paragraph, z.format(volume_total_sacos) + " sacos", false);
		
		
		tableRowOne = table.getRow(i);
		tableRowOne.getCell(4).removeParagraph(0);
		paragraph = tableRowOne.getCell(4).addParagraph();
		criarParagrafoTabela(paragraph, "100%", false);
		
		tableRowOne = table.getRow(i);
		tableRowOne.getCell(5).removeParagraph(0);
		paragraph = tableRowOne.getCell(5).addParagraph();
		String valorString = NumberFormat.getCurrencyInstance(ptBr).format(soma_total_pagamentos.doubleValue());

		criarParagrafoTabela(paragraph,valorString, false);
		


	}

	public void criarTabelaInformacoes(ArrayList<RegistroQuantidade> quantidades_totais, ArrayList<RegistroRecebimento> quantidades_recebidas ) {
		

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

		
		for(int J = 0; J < quantidades_totais.size(); J++) {
			
			String comprador = quantidades_totais.get(J).getComprador();
			String vendedor = quantidades_totais.get(J).getVendedor();
			double quantidade_total =  quantidades_totais.get(J).getTotal() ;
			double quantidade_recebida =  quantidades_recebidas.get(J).getQuantidade_recebida();
			double restante = quantidades_totais.get(J).getTotal() - quantidades_recebidas.get(J).getQuantidade_recebida();
			
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
			criarParagrafoTabela(paragraph,  z.format(quantidade_recebida)  + " sacos", false);
			somatoria_quantidade_recebida += quantidade_recebida;


			tableRowOne = table.getRow(i);
			tableRowOne.getCell(4).removeParagraph(0);
			paragraph = tableRowOne.getCell(4).addParagraph();
			criarParagrafoTabela(paragraph,
					z.format(restante)  + " sacos", false);
			somatoria_quantidade_restante += restante;

			
			tableRowOne = table.getRow(i);
			tableRowOne.getCell(5).removeParagraph(0);
			paragraph = tableRowOne.getCell(5).addParagraph();
			if(restante == 0 || restante == -0 || ((int) restante) == 0 || ((int) quantidade_recebida) >= ((int) quantidade_total)) {
				criarParagrafoTabela(paragraph, "FINALIZADO", false);
				tableRowOne.getCell(5).getCTTc().addNewTcPr().addNewShd().setFill("2F4F4F");

				quantidade_clientes_finalizado++;
			}
			else if(quantidade_recebida == 0) {
				criarParagrafoTabela(paragraph,"PENDENTE", false);
				tableRowOne.getCell(5).getCTTc().addNewTcPr().addNewShd().setFill("A0522D");
				quantidade_clientes_pendente++;
			}else if(quantidade_recebida > 0 && quantidade_recebida < quantidade_total) {
				criarParagrafoTabela(paragraph, "ENTREGANDO", false);
				tableRowOne.getCell(5).getCTTc().addNewTcPr().addNewShd().setFill("ADFF2F");

				quantidade_clientes_entregando++;
			}
			
			
			
			i++;
			
		}
		i++;
		//somatoria da quantidade total
		
		
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
		criarParagrafoTabela(paragraph,  z.format(somatoria_quantidade_recebida)  + " sacos",true);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(4).removeParagraph(0);
		paragraph = tableRowOne.getCell(4).addParagraph();
		criarParagrafoTabela(paragraph,z.format(somatoria_quantidade_restante)  + " sacos", true);

		
		String texto = "Clientes Entregando: " + quantidade_clientes_entregando + "\n" +
				       "Clientes Pendente: " + quantidade_clientes_pendente + "\n" +
				       "Clientes Finalizado: " + quantidade_clientes_finalizado + "\n";
		
		
		
		substituirTexto(texto, -1);
		
	}
}
