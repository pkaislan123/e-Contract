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
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.util.Units;
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
import org.codehaus.groovy.runtime.dgmimpl.arrays.CharacterArrayGetAtMetaMethod;
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

import main.java.cadastros.CadastroAviso;
import main.java.cadastros.CadastroBaseArquivos;
import main.java.cadastros.CadastroBaseDados;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroContrato.CadastroPagamento;
import main.java.cadastros.CadastroContrato.CadastroPagamentoContratual;
import main.java.cadastros.CadastroContrato.CadastroTransferenciaCarga;
import main.java.cadastros.CadastroContrato.CadastroTransferenciaPagamentoContratual;
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
import main.java.views_personalizadas.TelaNotificacao;
import main.java.views_personalizadas.TelaNotificacaoSuperior;
import main.java.views_personalizadas.TelaNotificacaoSuperiorModoBusca;
import outros.ValidaCNPj;
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


public class RelatorioContratoCarregamentoSimplificado {

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

	private boolean contrato, contrato_como_comprador;
	private boolean pagamento = false, pagamento_como_depositante = false, pagamento_como_favorecido = false;
	private boolean carregamento = false, carregamento_como_comprador = false, carregamento_como_vendedor = false;
	private boolean recebimento = false, recebimento_como_comprador = false, recebimento_como_vendedor = false, unir_recebimentos = false;

	private boolean sub_contratos = false;
	private boolean incluir_comissao = false;
	private boolean incluir_ganhos_potencias = false;
	private CadastroGrupo grupo_alvo_global;
	private int tipo_contrato = -1;
	private boolean somar_sub_contratos = false;

	private CadastroCliente contra_parte;
	
	
	
	
	public CadastroCliente getContra_parte() {
		return contra_parte;
	}

	public void setContra_parte(CadastroCliente contra_parte) {
		this.contra_parte = contra_parte;
	}



	private String safra_evidencia = "2020/2021";
	
	
	
	public boolean isUnir_recebimentos() {
		return unir_recebimentos;
	}

	public void setUnir_recebimentos(boolean unir_recebimentos) {
		this.unir_recebimentos = unir_recebimentos;
	}

	public boolean isRecebimento() {
		return recebimento;
	}

	public void setRecebimento(boolean recebimento) {
		this.recebimento = recebimento;
	}

	public boolean isRecebimento_como_comprador() {
		return recebimento_como_comprador;
	}

	public void setRecebimento_como_comprador(boolean recebimento_como_comprador) {
		this.recebimento_como_comprador = recebimento_como_comprador;
	}

	public boolean isRecebimento_como_vendedor() {
		return recebimento_como_vendedor;
	}

	public void setRecebimento_como_vendedor(boolean recebimento_como_vendedor) {
		this.recebimento_como_vendedor = recebimento_como_vendedor;
	}
	
	public ArrayList<CadastroCliente> getClientes_globais() {
		return clientes_globais;
	}

	public void setClientes_globais(ArrayList<CadastroCliente> clientes_globais) {
		this.clientes_globais = clientes_globais;
	}

	public int getId_safra() {
		return id_safra;
	}

	public void setId_safra(int id_safra) {
		this.id_safra = id_safra;
	}

	public boolean isContrato() {
		return contrato;
	}

	public void setContrato(boolean contrato) {
		this.contrato = contrato;
	}

	public boolean isContrato_como_comprador() {
		return contrato_como_comprador;
	}

	public void setContrato_como_comprador(boolean contrato_como_comprador) {
		this.contrato_como_comprador = contrato_como_comprador;
	}

	public boolean isPagamento() {
		return pagamento;
	}

	public void setPagamento(boolean pagamento) {
		this.pagamento = pagamento;
	}

	public boolean isPagamento_como_depositante() {
		return pagamento_como_depositante;
	}

	public void setPagamento_como_depositante(boolean pagamento_como_depositante) {
		this.pagamento_como_depositante = pagamento_como_depositante;
	}

	public boolean isPagamento_como_favorecido() {
		return pagamento_como_favorecido;
	}

	public void setPagamento_como_favorecido(boolean pagamento_como_favorecido) {
		this.pagamento_como_favorecido = pagamento_como_favorecido;
	}

	public boolean isCarregamento() {
		return carregamento;
	}

	public void setCarregamento(boolean carregamento) {
		this.carregamento = carregamento;
	}

	public boolean isCarregamento_como_comprador() {
		return carregamento_como_comprador;
	}

	public void setCarregamento_como_comprador(boolean carregamento_como_comprador) {
		this.carregamento_como_comprador = carregamento_como_comprador;
	}

	public boolean isCarregamento_como_vendedor() {
		return carregamento_como_vendedor;
	}

	public void setCarregamento_como_vendedor(boolean carregamento_como_vendedor) {
		this.carregamento_como_vendedor = carregamento_como_vendedor;
	}

	public boolean isSub_contratos() {
		return sub_contratos;
	}

	public void setSub_contratos(boolean sub_contratos) {
		this.sub_contratos = sub_contratos;
	}

	public boolean isIncluir_comissao() {
		return incluir_comissao;
	}

	public void setIncluir_comissao(boolean incluir_comissao) {
		this.incluir_comissao = incluir_comissao;
	}

	public boolean isIncluir_ganhos_potencias() {
		return incluir_ganhos_potencias;
	}

	public void setIncluir_ganhos_potencias(boolean incluir_ganhos_potencias) {
		this.incluir_ganhos_potencias = incluir_ganhos_potencias;
	}

	public int getTipo_contrato() {
		return tipo_contrato;
	}

	public void setTipo_contrato(int tipo_contrato) {
		this.tipo_contrato = tipo_contrato;
	}

	public boolean isSomar_sub_contratos() {
		return somar_sub_contratos;
	}

	public void setSomar_sub_contratos(boolean somar_sub_contratos) {
		this.somar_sub_contratos = somar_sub_contratos;
	}

	public RelatorioContratoCarregamentoSimplificado() {

		getDadosGlobais();
		
		servidor_unidade = configs_globais.getServidorUnidade();
	
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

		if(!section.isSetPgSz()) {
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

		titleRun.setText("Relatório de Contratos" + " por " + login.getNome() +" "+ login.getSobrenome()  + " em " + data_criacao
				+ " ás " + data.getHora());
		titleRun.setColor("000000");
		titleRun.setBold(false);
		titleRun.setUnderline(UnderlinePatterns.SINGLE);
		titleRun.setFontFamily("Arial");
		titleRun.setFontSize(9);

		
		XWPFParagraph filtros = document_global.createParagraph();
		filtros.setAlignment(ParagraphAlignment.LEFT);

		XWPFRun dadosPesquisaRun = filtros.createRun();

		String texto_pesquisa = "Busca por: ";
		if(carregamento) {
			texto_pesquisa = texto_pesquisa + "Carregamentos";
			if(carregamento_como_comprador && carregamento_como_vendedor) {
				texto_pesquisa = texto_pesquisa + " como Comprador e Vendedor";
			}else if(carregamento_como_comprador && !carregamento_como_vendedor) {
				texto_pesquisa = texto_pesquisa + " como Comprador";
			}else if(!carregamento_como_comprador && carregamento_como_vendedor) {
				texto_pesquisa = texto_pesquisa + " como Vendedor";

			}else {
				
			}
		}
		texto_pesquisa = texto_pesquisa + "\n";
		if(pagamento) {
			texto_pesquisa = texto_pesquisa + " | Pagamentos";
			if(pagamento_como_depositante && pagamento_como_favorecido) {
				texto_pesquisa = texto_pesquisa + " como Depositante e Favorecido";
			}else if(pagamento_como_depositante && !pagamento_como_favorecido) {
				texto_pesquisa = texto_pesquisa + " como Depositante";
			}else if(!pagamento_como_depositante && pagamento_como_favorecido) {
				texto_pesquisa = texto_pesquisa + " como Favorecido";

			}else {
				
			}
		}
		texto_pesquisa = texto_pesquisa + "\n";

		if(recebimento) {
			texto_pesquisa = texto_pesquisa + " | Recebimentos";
			if(recebimento_como_comprador && recebimento_como_vendedor) {
				texto_pesquisa = texto_pesquisa + " como Comprador e Vendedor";
			}else if(recebimento_como_comprador && !recebimento_como_vendedor) {
				texto_pesquisa = texto_pesquisa + " como Comprador";
			}else if(!recebimento_como_comprador && recebimento_como_vendedor) {
				texto_pesquisa = texto_pesquisa + " como Vendedor";

			}else {
				
			}
		}
		
		if(id_safra == 0) {
			texto_pesquisa = texto_pesquisa + " | Safra: TODAS AS SAFRAS / Evidência: " + safra_evidencia;
		
			}else {
				GerenciarBancoSafras gerenciar = new GerenciarBancoSafras();
				CadastroSafra safra = gerenciar.getSafra(id_safra);
				String text_safra = safra.getProduto().getNome_produto() + " " + safra.getProduto().getTransgenia() + " " +
				safra.getAno_plantio() + "/" + safra.getAno_colheita();
				texto_pesquisa = texto_pesquisa + " Safra: " + text_safra;

			}
		
		
		
		
		dadosPesquisaRun.setText("Filtros da pesquisa: \n" + texto_pesquisa );
		dadosPesquisaRun.setColor("000000");
		dadosPesquisaRun.setBold(false);
		dadosPesquisaRun.setFontFamily("Arial");
		dadosPesquisaRun.setFontSize(10);
		
		
		GerenciarBancoContratos procura_contratos = new GerenciarBancoContratos();
		
		for(CadastroCliente cliente : clientes_globais) {
			if(cliente != null) {
				
				if(carregamento) {
				if(carregamento_como_comprador) {
					
					GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
					
					
					ArrayList<RegistroQuantidade> quantidades_carregadas = gerenciar.getQuantidadesCarregadas(id_safra, cliente.getId(), contra_parte.getId(),1);
					ArrayList<RegistroRecebimento> quantidades_recebidas = gerenciar.getRecebidas(id_safra, cliente.getId(),contra_parte.getId(),1);
					ArrayList<RegistroQuantidade> quantidades_trans_negativa = gerenciar.getQuantidadesTransNegativa(id_safra, cliente.getId(), contra_parte.getId(),1);
					ArrayList<RegistroQuantidade> quantidades_trans_positiva = gerenciar.getQuantidadesTransPositiva(id_safra, cliente.getId(), contra_parte.getId(),1);

					criarTabelaInformacoes(quantidades_recebidas, quantidades_carregadas,quantidades_trans_negativa,quantidades_trans_positiva);
                  /*
					for(int i = 0; i < quantidades_totais.size(); i++) {ades_totais.get(i).getComprador() + " "+
								"Vendedor: " + quantidades_totais
						
						System.out.println("Comprador: " + quantid.get(i).getVendedor()+ " "+
								"Quantidade contratada: " + quantidades_totais.get(i).getTotal() + " "+
								"Quantidade recebida: " + quantidades_recebidas.get(i).getQuantidade_recebida() + " " +
								"Falta: " +( quantidades_totais.get(i).getTotal() - quantidades_recebidas.get(i).getQuantidade_recebida()) + " "
			
								);
					}
					*/
					
					
				}else if(carregamento_como_vendedor) {
	GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
					
					
	ArrayList<RegistroQuantidade> quantidades_carregadas = gerenciar.getQuantidadesCarregadas(id_safra, cliente.getId(), contra_parte.getId(),1);
	ArrayList<RegistroRecebimento> quantidades_recebidas = gerenciar.getRecebidas(id_safra, cliente.getId(),contra_parte.getId(),1);
	ArrayList<RegistroQuantidade> quantidades_trans_negativa = gerenciar.getQuantidadesTransNegativa(id_safra, cliente.getId(), contra_parte.getId(),1);
	ArrayList<RegistroQuantidade> quantidades_trans_positiva = gerenciar.getQuantidadesTransPositiva(id_safra, cliente.getId(), contra_parte.getId(),1);

	criarTabelaInformacoes(quantidades_recebidas, quantidades_carregadas,quantidades_trans_negativa,quantidades_trans_positiva);
                  /*
					for(int i = 0; i < quantidades_totais.size(); i++) {
						
						System.out.println("Comprador: " + quantidades_totais.get(i).getComprador() + " "+
								"Vendedor: " + quantidades_totais.get(i).getVendedor()+ " "+
								"Quantidade contratada: " + quantidades_totais.get(i).getTotal() + " "+
								"Quantidade recebida: " + quantidades_recebidas.get(i).getQuantidade_recebida() + " " +
								"Falta: " +( quantidades_totais.get(i).getTotal() - quantidades_recebidas.get(i).getQuantidade_recebida()) + " "
			
								);
					}
					*/
					
						
				}
				}
				
				
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
			//JOptionPane.showMessageDialog(null,
			//	"Erro ao criar cabecalho e rodape do contrato!\nConsulte o administrador do sistema!");
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

	public void criarTabelaInformacoes( ArrayList<RegistroRecebimento> quantidades_recebidas, ArrayList<RegistroQuantidade> quantidades_totais , ArrayList<RegistroQuantidade> quantidades_trans_negativa, ArrayList<RegistroQuantidade> quantidades_trans_positiva) {
	

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
		criarParagrafoTabela(paragraph, "TOTAL RECEBIDO", true);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(3).removeParagraph(0);
		paragraph = tableRowOne.getCell(3).addParagraph();
		criarParagrafoTabela(paragraph, "TOTAL CARREGADO", true);

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
			double quantidade_carregada =  quantidades_totais.get(J).getTotal() ;
			double quantidade_recebida =  quantidades_recebidas.get(J).getQuantidade_recebida();
			double quantidade_trans_negativa = quantidades_trans_negativa.get(J).getTotal();
			double quantidade_trans_positiva = quantidades_trans_positiva.get(J).getTotal();
			double quantidade_final = quantidade_carregada - quantidade_trans_negativa + quantidade_trans_positiva;

			double restante = quantidade_recebida - quantidade_final ;
			
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
			criarParagrafoTabela(paragraph, z.format(quantidade_recebida) + " sacos", false);
			somatoria_quantidade_recebida += quantidade_recebida;
			
			
			tableRowOne = table.getRow(i);
			tableRowOne.getCell(3).removeParagraph(0);
			paragraph = tableRowOne.getCell(3).addParagraph();
			criarParagrafoTabela(paragraph,  z.format(quantidade_final)  + " sacos", false);
			somatoria_quantidade_total += quantidade_final;


			tableRowOne = table.getRow(i);
			tableRowOne.getCell(4).removeParagraph(0);
			paragraph = tableRowOne.getCell(4).addParagraph();
			criarParagrafoTabela(paragraph,
					z.format(restante)  + " sacos", false);
			somatoria_quantidade_restante += restante;

			
			tableRowOne = table.getRow(i);
			tableRowOne.getCell(5).removeParagraph(0);
			paragraph = tableRowOne.getCell(5).addParagraph();
			
			if(restante == 0 || restante == -0 || ((int) restante) == 0 || ((int) quantidade_final ) >= ((int) quantidade_recebida)) {
				criarParagrafoTabela(paragraph, "FINALIZADO", false);
				tableRowOne.getCell(5).getCTTc().addNewTcPr().addNewShd().setFill("2F4F4F");

				quantidade_clientes_finalizado++;
			}
			else if(quantidade_final == 0) {
				criarParagrafoTabela(paragraph,"PENDENTE", false);
				tableRowOne.getCell(5).getCTTc().addNewTcPr().addNewShd().setFill("A0522D");
				quantidade_clientes_pendente++;
			}else if(quantidade_final > 0 && quantidade_final  < quantidade_recebida) {
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
		criarParagrafoTabela(paragraph, z.format(somatoria_quantidade_recebida ) + " sacos", true);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(3).removeParagraph(0);
		paragraph = tableRowOne.getCell(3).addParagraph();
		criarParagrafoTabela(paragraph,  z.format(somatoria_quantidade_total)  + " sacos",true);

		tableRowOne = table.getRow(i);
		tableRowOne.getCell(4).removeParagraph(0);
		paragraph = tableRowOne.getCell(4).addParagraph();
		criarParagrafoTabela(paragraph,z.format(somatoria_quantidade_restante)  + " sacos", true);

		
		String texto = "Clientes Carregando: " + quantidade_clientes_entregando + "\n" +
				       "Clientes Pendente: " + quantidade_clientes_pendente + "\n" +
				       "Clientes Finalizado: " + quantidade_clientes_finalizado + "\n";
		
		
		
		substituirTexto(texto, -1);
		
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
					"_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ __ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ ");

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


}
