package relatoria;

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

import cadastros.CadastroCliente;
import cadastros.CadastroContrato;
import cadastros.CadastroDocumento;
import cadastros.CadastroGrupo;
import cadastros.CadastroLogin;
import cadastros.CadastroModelo;
import cadastros.CadastroNFe;
import cadastros.CadastroProduto;
import cadastros.CadastroRomaneio;
import conexaoBanco.GerenciarBancoClientes;
import conexaoBanco.GerenciarBancoContratos;
import conexaoBanco.GerenciarBancoDocumento;
import conexaoBanco.GerenciarBancoProdutos;
import conexaoBanco.GerenciarBancoTransferencias;
import conexaoBanco.GerenciarBancoTransferenciasCarga;
import manipular.ConfiguracoesGlobais;
import manipular.ConverterPdf;
import manipular.ManipularNotasFiscais;
import manipular.ManipularRomaneios;
import manipular.Nuvem;
import manipular.PorExtenso;
import outros.DadosGlobais;
import outros.GetData;
import tratamento_proprio.Log;
import views_personalizadas.TelaEmEspera;
import views_personalizadas.TelaNotificacao;

public class RelatorioContratos {

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
	private boolean sub_contratos = false;
	private boolean incluir_comissao = false;
	private boolean incluir_ganhos_potencias = false;
	private CadastroGrupo grupo_alvo_global;
	private int tipo_contrato = -1;
	private boolean somar_sub_contratos = false;
	private boolean contrato_irmao = false;
	private boolean recebimento = false, recebimento_como_comprador = false, recebimento_como_vendedor = false, unir_recebimentos = false;

	public boolean isContrato_irmao() {
		return contrato_irmao;
	}

	public void setContrato_irmao(boolean contrato_irmao) {
		this.contrato_irmao = contrato_irmao;
	}

	public RelatorioContratos(int _tipo_contrato, boolean _contrato, boolean _contrato_como_comprador,
			boolean _pagamento, boolean _pagamento_como_depositante, boolean _pagamento_como_favorecido,
			boolean _carregamento, boolean _carregamento_como_comprador, boolean _carregamento_como_vendedor,
			boolean _recebimento, boolean _recebimento_como_comprador, boolean _recebimento_como_vendedor, boolean _unir_recebimentos,
			int _id_safra, boolean _sub_contratos, boolean _incluir_comissao, boolean _incluir_ganhos_potenciais,
			boolean _somar_sub_contratos, ArrayList<CadastroCliente> _clientes_globais, CadastroGrupo _grupo_alvo) {

		getDadosGlobais();
		this.modelo = modelo;
		servidor_unidade = configs_globais.getServidorUnidade();
		this.id_safra = _id_safra;
		this.contrato_como_comprador = _contrato_como_comprador;
		this.contrato = _contrato;
		this.pagamento = _pagamento;
		this.pagamento_como_depositante = _pagamento_como_depositante;
		this.pagamento_como_favorecido = _pagamento_como_favorecido;
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

		titleRun.setText("Relatório de Contratos" + " por " + login.getNome() + " " + login.getSobrenome() + " em " + data_criacao
				+ " ás " + data.getHora());
		titleRun.setColor("000000");
		titleRun.setBold(false);
		titleRun.setUnderline(UnderlinePatterns.SINGLE);
		titleRun.setFontFamily("Arial");
		titleRun.setFontSize(10);
		
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
		
		
		
		dadosPesquisaRun.setText("Filtros da pesquisa: \n" + texto_pesquisa );
		dadosPesquisaRun.setColor("000000");
		dadosPesquisaRun.setBold(false);
		dadosPesquisaRun.setFontFamily("Arial");
		dadosPesquisaRun.setFontSize(10);
		

		GerenciarBancoContratos procura_contratos_grupo = new GerenciarBancoContratos();
		if (grupo_alvo_global != null) {

			int num_total_contratos = 0;
			double quantidade_total_sacos = 0;
			BigDecimal valor_total_pagamentos = BigDecimal.ZERO;

			// quantidade total de sacos do grupo
			for (CadastroCliente cliente : grupo_alvo_global.getClientes()) {
				// numero de contratos desde clinete
				ArrayList<CadastroContrato> lista_contratos_como_comprador = new ArrayList<>();

				if (contrato_como_comprador) {
					// JOptionPane.showMessageDialog(null, "Tipo do contrato: " + tipo_contrato);
					if (tipo_contrato == 1) {
						lista_contratos_como_comprador = procura_contratos_grupo.getContratosPorCliente(1, id_safra,
								cliente.getId());
					} else if (tipo_contrato == 2) {
						lista_contratos_como_comprador = procura_contratos_grupo.getContratosPorCliente(4, id_safra,
								cliente.getId());
					}
				} else {
					// JOptionPane.showMessageDialog(null, "Pesquisa como vendedor");

					// JOptionPane.showMessageDialog(null, "Tipo do contrato: " + tipo_contrato);
					if (tipo_contrato == 1) {
						lista_contratos_como_comprador = procura_contratos_grupo.getContratosPorCliente(2, id_safra,
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

			String valorTotalString = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos);
			text = text + "\nValor Total: " + valorTotalString;
			substituirTexto(text, -1);

		}

		for (CadastroCliente cliente_alvo_global : clientes_globais) {

			String nome_cliente;
			if (cliente_alvo_global.getTipo_pessoa() == 0) {
				// pessoa fisica
				nome_cliente = cliente_alvo_global.getNome_empresarial();
			} else {
				nome_cliente = cliente_alvo_global.getNome_fantaia();
			}

			if (contrato) {
				// obter contratos desde cliente
				GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
				ArrayList<CadastroContrato> lista_contratos_como_vendedor = new ArrayList<>();

				// JOptionPane.showMessageDialog(null, "Pesquisa como vendedor");

				// JOptionPane.showMessageDialog(null, "Tipo do contrato: " + tipo_contrato);

				lista_contratos_como_vendedor = procura_contratos_grupo.getContratosPorCliente(2, id_safra,
						cliente_alvo_global.getId());

				ArrayList<CadastroContrato> lista_final = new ArrayList<>();

				if (tipo_contrato == 2) {
					this.somar_sub_contratos = true;

					// verifica pelos subcontratos de cada contrato retornado
					for (CadastroContrato contrato_encontrato_na_busca : lista_contratos_como_vendedor) {
						if (contrato_encontrato_na_busca.getSub_contrato() == 1
								|| contrato_encontrato_na_busca.getSub_contrato() == 6
								|| contrato_encontrato_na_busca.getSub_contrato() == 7) {
							// se este contrato for um subcontrato, adicione o na lista

							boolean ja_esta_na_lista = false;
							if (lista_final.size() > 0) {
								for (CadastroContrato objeto_na_lista : lista_final) {
									if (objeto_na_lista.getId() == contrato_encontrato_na_busca.getId()) {
										ja_esta_na_lista = true;
										break;
									}
								}
							}
							if (!ja_esta_na_lista)
								lista_final.add(contrato_encontrato_na_busca);
						} else {
							// e um contrato original, verifica seus subcontratos
							ArrayList<CadastroContrato> sub_contratos = procura_contratos_grupo
									.getSubContratos(contrato_encontrato_na_busca.getId());
							if (sub_contratos.size() > 0) {
								// existem subcontratos nesse contrato
								// pega os vendedeores do contrato pai
								CadastroCliente vendedores_contrato_original[] = contrato_encontrato_na_busca
										.getVendedores();
								boolean tem_contrato = false;
								for (CadastroContrato sub : sub_contratos) {
									// pega os vendedores deste subcontrato

									CadastroCliente vendedores_sub[] = sub.getVendedores();
									if (vendedores_sub != null) {
										for (int i = 0; i < vendedores_sub.length; i++) {
											for (int j = 0; j < vendedores_contrato_original.length; j++) {
												if (vendedores_sub[i] != null) {
													if (vendedores_contrato_original[j] != null) {
														if (vendedores_sub[i].getId() == vendedores_contrato_original[j]
																.getId()) {
															tem_contrato = true;
															break;
														}
													}
												}
											}
										}
									}
								}

								if (!tem_contrato) {
									// o contrato original nao possui subcontratos, no qual o vendedor do
									// subcontrato
									// seja tambem vendedor do contrato originaç
									lista_final.add(contrato_encontrato_na_busca);

								} else {
									for (CadastroContrato sub : sub_contratos) {
										// pega os vendedores deste subcontrato

										CadastroCliente vendedores_sub[] = sub.getVendedores();
										for (int i = 0; i < vendedores_sub.length; i++) {
											for (int j = 0; j < vendedores_contrato_original.length; j++) {
												if (vendedores_contrato_original[j] != null
														&& vendedores_sub[i] != null) {
													if (vendedores_sub[i].getId() == vendedores_contrato_original[j]
															.getId()) {
														lista_final.add(sub);
													}
												}
											}
										}

									}
								}

							} else {
								// esse contrato nao possui subcontratos, adicionar-lo na listra
								lista_final.add(contrato_encontrato_na_busca);

							}

						}

					}
				} else {
					this.incluir_comissao = true;
					this.somar_sub_contratos = false;
					this.contrato_irmao = true;

					// contrato interno
					// JOptionPane.showMessageDialog(null, "Relatorio interno");
					// verifica se este contrato e um subcontrato
					for (CadastroContrato contrato_na_lista : lista_contratos_como_vendedor) {
						if (contrato_na_lista.getSub_contrato() == 1) {

							boolean ja_esta_na_lista = false;
							// verifica se esse contrato esta na lista
							for (CadastroContrato objeto_na_lista : lista_final) {
								if (objeto_na_lista.getId() == contrato_na_lista.getId()) {
									ja_esta_na_lista = true;
								}
							}

							if (!ja_esta_na_lista) {
								// verifica quem e o contrato pai deste contrato
								CadastroContrato contrato_pai = procura_contratos_grupo
										.getContratoPai(contrato_na_lista.getId());
								lista_final.add(contrato_pai);
								// adiciona os subcontratos deste contrato na lista tambem
								ArrayList<CadastroContrato> subs = procura_contratos_grupo
										.getSubContratos(contrato_pai.getId());
								for (CadastroContrato contratos_sub_contratos : subs) {
									// apenas adiciona na lista o subcontrato no qual o cliente e o alvo da busca
									if (!contrato_irmao) {
										if (contratos_sub_contratos.getId() == contrato_na_lista.getId())
											lista_final.add(contratos_sub_contratos);
									} else {
										lista_final.add(contratos_sub_contratos);

									}
								}
							}

						} else {
							lista_final.add(contrato_na_lista);
							// procura seus subcontratos e adiciona tambem
							ArrayList<CadastroContrato> subs = procura_contratos_grupo
									.getSubContratos(contrato_na_lista.getId());
							for (CadastroContrato contratos_sub_contratos : subs) {
								// apenas adiciona na lista o subcontrato no qual o cliente e vendedor no
								// contrato
								if (!contrato_irmao) {
									boolean correto = false;
									CadastroCliente vendedores[] = contratos_sub_contratos.getVendedores();
									if (vendedores != null) {
										for (CadastroCliente vendedor : vendedores) {
											if (vendedor != null) {
												if (vendedor.getId() == cliente_alvo_global.getId()) {
													lista_final.add(contratos_sub_contratos);
												}
											}
										}
									}
								} else {
									lista_final.add(contratos_sub_contratos);

								}

							}

						}

					}

				}

				if (lista_final.size() > 0) {

					adicionarTraco(true, 0);

					XWPFParagraph titulo_contratos = document_global.createParagraph();
					titulo_contratos.setAlignment(ParagraphAlignment.CENTER);

					XWPFRun titulo_contratosRun = titulo_contratos.createRun();
					titulo_contratosRun.setText("Contratos");
					titulo_contratosRun.setColor("000000");
					titulo_contratosRun.setBold(true);
					titulo_contratosRun.setFontFamily("Arial");
					titulo_contratosRun.setFontSize(10);

					substituirTexto("Cliente: " + nome_cliente.toUpperCase(), -1);

					criarTabelaContrato(lista_final);

				}

			}

			if (pagamento) {

				// obter contratos desde cliente
				GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
				ArrayList<CadastroContrato.CadastroPagamentoContratual> lista_pagamento_como_depositante = new ArrayList<>();
				ArrayList<CadastroContrato.CadastroPagamentoContratual> lista_pagamento_como_favorecido = new ArrayList<>();

				if (pagamento_como_depositante) {
					lista_pagamento_como_depositante = gerenciar
							.getPagamentosPorDepositante(cliente_alvo_global.getId());
				}

				if (pagamento_como_favorecido) {
					lista_pagamento_como_favorecido = gerenciar.getPagamentosPorFavorecido(cliente_alvo_global.getId());

				}

				ArrayList<CadastroContrato.CadastroPagamentoContratual> lista_final = new ArrayList<>();

				lista_final = new ArrayList(
						lista_pagamento_como_depositante.size() + lista_pagamento_como_favorecido.size());

				if (lista_pagamento_como_depositante.size() > 0) {
					lista_final.addAll(lista_pagamento_como_depositante);

				}
				if (lista_pagamento_como_favorecido.size() > 0) {
					lista_final.addAll(lista_pagamento_como_favorecido);

				}

				if (lista_final.size() > 0) {

					adicionarTraco(true, 0);

					XWPFParagraph titulo_sub_contratos = document_global.createParagraph();
					titulo_sub_contratos.setAlignment(ParagraphAlignment.CENTER);

					XWPFRun titulo_sub_contratostitleRun = titulo_sub_contratos.createRun();
					titulo_sub_contratostitleRun.setText("Pagamentos");
					titulo_sub_contratostitleRun.setColor("000000");
					titulo_sub_contratostitleRun.setBold(true);
					titulo_sub_contratostitleRun.setFontFamily("Arial");
					titulo_sub_contratostitleRun.setFontSize(10);

					substituirTexto("\nNúmero de Pagamentos: " + lista_final.size(), 2);
					substituirTexto("", 2);
					if (!contrato) {
						substituirTexto("Cliente: " + nome_cliente.toUpperCase(), -1);

					}
					criarTabelaPagamentos(lista_final);

				}

			}

			
			/*
			if (carregamento) {

				// JOptionPane.showMessageDialog(null, "Carregamento selecionado");

				// obter carregamentos desde cliente
				GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
				ArrayList<CadastroContrato.Carregamento> lista_carregamento_como_comprador = new ArrayList<>();
				ArrayList<CadastroContrato.Carregamento> lista_carregamento_como_vendedor = new ArrayList<>();

				if (carregamento_como_comprador) {
					lista_carregamento_como_comprador = gerenciar
							.getCarregamentosPorComprador(cliente_alvo_global.getId());
				}
				if (carregamento_como_vendedor) {
					// JOptionPane.showMessageDialog(null, "Carregamento como vendedor
					// selecionado");
					lista_carregamento_como_vendedor = gerenciar
							.getCarregamentosPorVendedor(cliente_alvo_global.getId());

				}

				// revisa a lista retirando pagamentos replicados em sub-contratos;
				GerenciarBancoContratos gerenciar_cargas = new GerenciarBancoContratos();
				ArrayList<CadastroContrato.Carregamento> lista_final = new ArrayList<>();

				for (int i = 0; i < lista_carregamento_como_comprador.size(); i++) {
					CadastroContrato contrato_carga = gerenciar_cargas
							.getContrato(lista_carregamento_como_comprador.get(i).getId_contrato());

					if (contrato_carga.getSub_contrato() != 1) {

						lista_final.add(lista_carregamento_como_comprador.get(i));

					}

				}

				for (int i = 0; i < lista_carregamento_como_vendedor.size(); i++) {
					CadastroContrato contrato_carga = gerenciar_cargas
							.getContrato(lista_carregamento_como_vendedor.get(i).getId_contrato());

					if (contrato_carga.getSub_contrato() != 1) {
						// JOptionPane.showMessageDialog(null, "adicionando item na lista final");

						lista_final.add(lista_carregamento_como_vendedor.get(i));

					}

				}

				if (lista_final.size() > 0) {

					adicionarTraco(true, 0);

					XWPFParagraph titulo_sub_contratos = document_global.createParagraph();
					titulo_sub_contratos.setAlignment(ParagraphAlignment.CENTER);

					XWPFRun titulo_sub_contratostitleRun = titulo_sub_contratos.createRun();
					titulo_sub_contratostitleRun.setText("Carregamentos");
					titulo_sub_contratostitleRun.setColor("000000");
					titulo_sub_contratostitleRun.setBold(true);
					titulo_sub_contratostitleRun.setFontFamily("Arial");
					titulo_sub_contratostitleRun.setFontSize(10);
					if (!contrato) {
						substituirTexto("Cliente: " + nome_cliente.toUpperCase(), -1);

					}
					criarTabelaCarregamentos(contrato, lista_final);

				}

			}*/
 
			/**************************************carregamentos***********************/
			
			
			
			
			if (carregamento) {
				// carregamentos
				GerenciarBancoContratos gerenciar_carregamentos = new GerenciarBancoContratos();

				// contratos por comprador
				ArrayList<CadastroContrato> contratos_deste_cliente_comprador = gerenciar_carregamentos
						.getContratosPorCliente(1, id_safra, cliente_alvo_global.getId());

				// contratos por vendedor
				ArrayList<CadastroContrato> contratos_deste_cliente_vendedor = gerenciar_carregamentos
						.getContratosPorCliente(2, id_safra, cliente_alvo_global.getId());

				if (carregamento_como_comprador) {


					if (unir_recebimentos) {
						double soma_total_quantidade_contratos_kgs = 0;
						ArrayList<CadastroContrato.Carregamento> carregamentos_totais = new ArrayList<>();
						
						for (CadastroContrato contrato : contratos_deste_cliente_comprador) {
							ArrayList<CadastroContrato.Carregamento> carregamentos_locais= gerenciar_carregamentos
									.getCarregamentos(contrato.getId());
							carregamentos_totais.addAll(carregamentos_locais);
							
                          	double local_quantidade_kgs = 0;
							
							
							if(contrato.getMedida().equalsIgnoreCase("KG")) {
								local_quantidade_kgs = contrato.getQuantidade();
							}else if(contrato.getMedida().equalsIgnoreCase("Sacos")) {
								local_quantidade_kgs = contrato.getQuantidade() * 60;
							}
							
							soma_total_quantidade_contratos_kgs += local_quantidade_kgs;
						}
						
						
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

						}
						

					} else {

						for (CadastroContrato contrato : contratos_deste_cliente_comprador) {

							ArrayList<CadastroContrato.Carregamento> carregamentos = gerenciar_carregamentos
									.getCarregamentos(contrato.getId());

							if (carregamentos.size() > 0) {

								adicionarTraco(true, 0);

								XWPFParagraph titulo_recebimentos = document_global.createParagraph();
								titulo_recebimentos.setAlignment(ParagraphAlignment.CENTER);

								XWPFRun titulo_recebimentosRun = titulo_recebimentos.createRun();
								titulo_recebimentosRun.setText("Carregamentos");
								titulo_recebimentosRun.setColor("000000");
								titulo_recebimentosRun.setBold(true);
								titulo_recebimentosRun.setFontFamily("Arial");
								titulo_recebimentosRun.setFontSize(9);

								criarTabelaCarregamentos(carregamentos, 0);

							}
						}
					}
				}

				if (carregamento_como_vendedor) {


					if (unir_recebimentos) {
						double soma_total_quantidade_contratos_kgs = 0;
						ArrayList<CadastroContrato.Carregamento> carregamentos_totais = new ArrayList<>();
						
						for (CadastroContrato contrato : contratos_deste_cliente_vendedor) {
							ArrayList<CadastroContrato.Carregamento> carregamentos_locais= gerenciar_carregamentos
									.getCarregamentos(contrato.getId());
							carregamentos_totais.addAll(carregamentos_locais);
							
							double local_quantidade_kgs = 0;
							
							
							if(contrato.getMedida().equalsIgnoreCase("KG")) {
								local_quantidade_kgs = contrato.getQuantidade();
							}else if(contrato.getMedida().equalsIgnoreCase("Sacos")) {
								local_quantidade_kgs = contrato.getQuantidade() * 60;
							}
							
							soma_total_quantidade_contratos_kgs += local_quantidade_kgs;
							
							
						}
						
						//faz a soma das quantidades dos contratos
						
						
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

							criarTabelaCarregamentos(carregamentos_totais,soma_total_quantidade_contratos_kgs);

						}
						

					} else {

						for (CadastroContrato contrato : contratos_deste_cliente_vendedor) {

							ArrayList<CadastroContrato.Carregamento> carregamentos = gerenciar_carregamentos
									.getCarregamentos(contrato.getId());

							if (carregamentos.size() > 0) {

								adicionarTraco(true, 0);

								XWPFParagraph titulo_recebimentos = document_global.createParagraph();
								titulo_recebimentos.setAlignment(ParagraphAlignment.CENTER);

								XWPFRun titulo_recebimentosRun = titulo_recebimentos.createRun();
								titulo_recebimentosRun.setText("Carregamentos");
								titulo_recebimentosRun.setColor("000000");
								titulo_recebimentosRun.setBold(true);
								titulo_recebimentosRun.setFontFamily("Arial");
								titulo_recebimentosRun.setFontSize(9);

								criarTabelaCarregamentos(carregamentos, 0);

							}
						}
					}
					
					
					
					
				}
			}
			
			/*************************fim carregamentos********************/
			
			if (recebimento) {
				// recebimentos
				GerenciarBancoContratos gerenciar_recebimentos = new GerenciarBancoContratos();

				// contratos por comprador
				ArrayList<CadastroContrato> contratos_deste_cliente_comprador = gerenciar_recebimentos
						.getContratosPorCliente(1, id_safra, cliente_alvo_global.getId());

				// contratos por vendedor
				ArrayList<CadastroContrato> contratos_deste_cliente_vendedor = gerenciar_recebimentos
						.getContratosPorCliente(2, id_safra, cliente_alvo_global.getId());

				if (recebimento_como_comprador) {


					if (unir_recebimentos) {
						double soma_total_quantidade_contratos_kgs = 0;
						ArrayList<CadastroContrato.Recebimento> recebimentos_totais = new ArrayList<>();
						
						for (CadastroContrato contrato : contratos_deste_cliente_comprador) {
							ArrayList<CadastroContrato.Recebimento> recebimentos_locais= gerenciar_recebimentos
									.getRecebimentos(contrato.getId());
							recebimentos_totais.addAll(recebimentos_locais);
							
                          	double local_quantidade_kgs = 0;
							
							
							if(contrato.getMedida().equalsIgnoreCase("KG")) {
								local_quantidade_kgs = contrato.getQuantidade();
							}else if(contrato.getMedida().equalsIgnoreCase("Sacos")) {
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

						}
						

					} else {

						for (CadastroContrato contrato : contratos_deste_cliente_comprador) {

							ArrayList<CadastroContrato.Recebimento> recebimentos = gerenciar_recebimentos
									.getRecebimentos(contrato.getId());

							if (recebimentos.size() > 0) {

								adicionarTraco(true, 0);

								XWPFParagraph titulo_recebimentos = document_global.createParagraph();
								titulo_recebimentos.setAlignment(ParagraphAlignment.CENTER);

								XWPFRun titulo_recebimentosRun = titulo_recebimentos.createRun();
								titulo_recebimentosRun.setText("Recebimentos");
								titulo_recebimentosRun.setColor("000000");
								titulo_recebimentosRun.setBold(true);
								titulo_recebimentosRun.setFontFamily("Arial");
								titulo_recebimentosRun.setFontSize(9);

								criarTabelaRecebimentos(recebimentos, 0);

							}
						}
					}
				}

				if (recebimento_como_vendedor) {


					if (unir_recebimentos) {
						double soma_total_quantidade_contratos_kgs = 0;
						ArrayList<CadastroContrato.Recebimento> recebimentos_totais = new ArrayList<>();
						
						for (CadastroContrato contrato : contratos_deste_cliente_vendedor) {
							ArrayList<CadastroContrato.Recebimento> recebimentos_locais= gerenciar_recebimentos
									.getRecebimentos(contrato.getId());
							recebimentos_totais.addAll(recebimentos_locais);
							
							double local_quantidade_kgs = 0;
							
							
							if(contrato.getMedida().equalsIgnoreCase("KG")) {
								local_quantidade_kgs = contrato.getQuantidade();
							}else if(contrato.getMedida().equalsIgnoreCase("Sacos")) {
								local_quantidade_kgs = contrato.getQuantidade() * 60;
							}
							
							soma_total_quantidade_contratos_kgs += local_quantidade_kgs;
							
							
						}
						
						//faz a soma das quantidades dos contratos
						
						
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

							criarTabelaRecebimentos(recebimentos_totais,soma_total_quantidade_contratos_kgs);

						}
						

					} else {

						for (CadastroContrato contrato : contratos_deste_cliente_vendedor) {

							ArrayList<CadastroContrato.Recebimento> recebimentos = gerenciar_recebimentos
									.getRecebimentos(contrato.getId());

							if (recebimentos.size() > 0) {

								adicionarTraco(true, 0);

								XWPFParagraph titulo_recebimentos = document_global.createParagraph();
								titulo_recebimentos.setAlignment(ParagraphAlignment.CENTER);

								XWPFRun titulo_recebimentosRun = titulo_recebimentos.createRun();
								titulo_recebimentosRun.setText("Recebimentos");
								titulo_recebimentosRun.setColor("000000");
								titulo_recebimentosRun.setBold(true);
								titulo_recebimentosRun.setFontFamily("Arial");
								titulo_recebimentosRun.setFontSize(9);

								criarTabelaRecebimentos(recebimentos, 0);

							}
						}
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
			// JOptionPane.showMessageDialog(null,
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

	
	
	public void criarTabelaRecebimentos(ArrayList<CadastroContrato.Recebimento> recebimentos, double soma_total_quantidade_contratos) {
		// XWPFParagraph par = document_global.createParagraph();

		NumberFormat z = NumberFormat.getNumberInstance();

		Locale ptBr = new Locale("pt", "BR");

		// criarParagrafo(1);
		// linhas x colunas
		
		int num_linhas_recebimentos = -1;
		
		if(soma_total_quantidade_contratos == 0) {
			
		 num_linhas_recebimentos = recebimentos.size() + 1 + 1 + 1 + 1 + 1 + 1 + 1;
		}else {
			 num_linhas_recebimentos = recebimentos.size() + 1 + 1 + 1 + 1 + 1 + 1;

		}
		double soma_total_romaneio = 0;
		double soma_total_nf_venda = 0;
		
		double soma_total_nf_remessa = 0;

		BigDecimal valor_total_nf_venda =  BigDecimal.ZERO;
		BigDecimal valor_total_nf_remessa =  BigDecimal.ZERO;
		
		
		XWPFTable table = document_global.createTable(num_linhas_recebimentos,10);

		setTableAlign(table, ParagraphAlignment.CENTER);
		XWPFTableRow tableRowOne = table.getRow(0);
		tableRowOne.getCell(0).removeParagraph(0);

		XWPFParagraph paragraph = tableRowOne.getCell(0).addParagraph();
		
		//
		int cabecalho = 0;
		
		if(soma_total_quantidade_contratos != 0) {
			
			tableRowOne = table.getRow(cabecalho);
			tableRowOne.getCell(0).removeParagraph(0);
			paragraph = tableRowOne.getCell(0).addParagraph();
			
			criarParagrafoTabela(paragraph,"Quantidade Total: " + z.format(soma_total_quantidade_contratos) + " kgs | " + z.format(soma_total_quantidade_contratos/60) + " sacos", true);
			tableRowOne.getCell(0).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");
			CTHMerge hMerge = CTHMerge.Factory.newInstance();
			hMerge.setVal(STMerge.RESTART);
			table.getRow(cabecalho).getCell(0).getCTTc().getTcPr().setHMerge(hMerge);
			
			for(int celula = 1; celula <= 6; celula++) {
				tableRowOne = table.getRow(cabecalho);
				tableRowOne.getCell(celula).removeParagraph(0);
				paragraph = tableRowOne.getCell(celula).addParagraph();
				
				criarParagrafoTabela(paragraph,"", true);
				tableRowOne.getCell(celula).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");
				
				CTHMerge hMerge1 = CTHMerge.Factory.newInstance();
				hMerge1.setVal(STMerge.CONTINUE);
				table.getRow(cabecalho).getCell(celula).getCTTc().getTcPr().setHMerge(hMerge1);
				
			}
			
		
			cabecalho++;
			
			
			
		}else {
			
			CadastroContrato contrato_deste_recebimento = new GerenciarBancoContratos().getContrato(recebimentos.get(0).getId_contrato_recebimento());
			
			tableRowOne = table.getRow(cabecalho);
			tableRowOne.getCell(0).removeParagraph(0);
			paragraph = tableRowOne.getCell(0).addParagraph();
			
			
			double quantidade_kg = 0;
			double quantidade_sacos = 0;
			
			if(contrato_deste_recebimento.getMedida().equalsIgnoreCase("KG")) {
				quantidade_kg = contrato_deste_recebimento.getQuantidade();
				quantidade_sacos = quantidade_kg / 60;
			}else if(contrato_deste_recebimento.getMedida().equalsIgnoreCase("Sacos")) {
				quantidade_sacos = contrato_deste_recebimento.getQuantidade();
				quantidade_kg = quantidade_sacos * 60;
			}
			
			//compradores x vendedores
			
			
			// safra
			String safra = contrato_deste_recebimento.getModelo_safra().getProduto().getNome_produto() + " " + contrato_deste_recebimento.getModelo_safra().getProduto().getTransgenia() +
					" " + contrato_deste_recebimento.getModelo_safra().getAno_plantio() + "/" + contrato_deste_recebimento.getModelo_safra().getAno_colheita();
			
			criarParagrafoTabela(paragraph,"CTR: " + contrato_deste_recebimento.getCodigo() + " " + safra + " Quantidade Total: " + z.format(quantidade_kg) + " kgs | " + z.format(quantidade_sacos) + " sacos "
					+NumberFormat.getCurrencyInstance(ptBr).format(contrato_deste_recebimento.getValor_produto()) +
							" por " + contrato_deste_recebimento.getMedida() + " totalizando: " +  NumberFormat.getCurrencyInstance(ptBr).format(contrato_deste_recebimento.getValor_a_pagar().doubleValue()), true);
			tableRowOne.getCell(0).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");
			CTHMerge hMerge = CTHMerge.Factory.newInstance();
			hMerge.setVal(STMerge.RESTART);
			table.getRow(cabecalho).getCell(0).getCTTc().getTcPr().setHMerge(hMerge);
			
			for(int celula = 1; celula <= 6; celula++) {
				tableRowOne = table.getRow(cabecalho);
				tableRowOne.getCell(celula).removeParagraph(0);
				paragraph = tableRowOne.getCell(celula).addParagraph();
				
				criarParagrafoTabela(paragraph,"", true);
				tableRowOne.getCell(celula).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");
				
				CTHMerge hMerge1 = CTHMerge.Factory.newInstance();
				hMerge1.setVal(STMerge.CONTINUE);
				table.getRow(cabecalho).getCell(celula).getCTTc().getTcPr().setHMerge(hMerge1);
				
			}
			
			cabecalho++;

			//linha com nome compradores x vendedores
			
			tableRowOne = table.getRow(cabecalho);
			tableRowOne.getCell(0).removeParagraph(0);
			paragraph = tableRowOne.getCell(0).addParagraph();
			

			CadastroCliente compradores[] = contrato_deste_recebimento.getCompradores();
			CadastroCliente vendedores[] = contrato_deste_recebimento.getVendedores();


			

			String nome_vendedores = "";
			String nome_compradores= "";


			
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
			if(vendedores[0] != null) {
			if(vendedores[0].getTipo_pessoa() == 0) {
				nome_vendedores = vendedores[0].getNome_empresarial();
			}else {
				nome_vendedores = vendedores[0].getNome_fantaia();
			}
			}
			
			if(vendedores[1] != null) {
				if(vendedores[1].getTipo_pessoa() == 0) {
					nome_vendedores = nome_vendedores + ", " + vendedores[1].getNome_empresarial();
				}else {
					nome_vendedores = nome_vendedores + ", " + vendedores[1].getNome_fantaia();
				}
			}
			
			
			
			criarParagrafoTabela(paragraph,nome_compradores + " X "+ nome_vendedores, true);
			tableRowOne.getCell(0).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");
			 hMerge = CTHMerge.Factory.newInstance();
			hMerge.setVal(STMerge.RESTART);
			table.getRow(cabecalho).getCell(0).getCTTc().getTcPr().setHMerge(hMerge);
			
			for(int celula = 1; celula <= 6; celula++) {
				tableRowOne = table.getRow(cabecalho);
				tableRowOne.getCell(celula).removeParagraph(0);
				paragraph = tableRowOne.getCell(celula).addParagraph();
				
				criarParagrafoTabela(paragraph,"", true);
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
		
		//checkgens
				boolean nf_remessa_ativo = false;
				boolean nf_venda_ativo = false;

				//checka se ha no minimo uma nf remessa aplicavel
				for(CadastroContrato.Recebimento recebimento : recebimentos ) {
					if(recebimento.getNf_remessa_aplicavel() == 1) {
						nf_remessa_ativo = true;
						break;
					}
					
				}
				
				//checka se ha no minimo uma nf venda aplicavel
				for(CadastroContrato.Recebimento recebimento : recebimentos ) {
					if(recebimento.getNf_venda_aplicavel() == 1) {
						nf_venda_ativo = true;
						break;
					}
					
				}
		for (CadastroContrato.Recebimento recebimento : recebimentos) {

			String cor = "000000";
			
			if(checkString(recebimento.getCodigo_nf_venda()) && checkString(recebimento.getCodigo_nf_remessa())) {
				//ok
				cor = "FFFFFF";

			}
				
			else if( !(checkString(recebimento.getCodigo_nf_venda())) && !(checkString(recebimento.getCodigo_nf_remessa())) )	{
				//falta duas notas
				cor = "B0C4DE";

			}
			else if(!(checkString(recebimento.getCodigo_nf_venda())) && checkString(recebimento.getCodigo_nf_remessa()) ){
            	//falta apenas nf de venda
				cor = "FFFF00";

            }else if(!(checkString(recebimento.getCodigo_nf_remessa())) && checkString(recebimento.getCodigo_nf_venda()) ) {
            	//falta apenas nf remessa
				cor =  "FFD700"	;

            }
			
			
			
			// contrato ao qual esse recebimento pertence
			tableRowOne = table.getRow(i);
			tableRowOne.getCell(0).removeParagraph(0);
			paragraph = tableRowOne.getCell(0).addParagraph();
			criarParagrafoTabela(paragraph, gerenciar.getContrato(recebimento.getId_contrato_recebimento()).getCodigo(), false);
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
				criarParagrafoTabela(paragraph, z.format(recebimento.getPeso_nf_venda())  + " Kgs", false);
				soma_total_nf_venda += recebimento.getPeso_nf_venda();
				tableRowOne.getCell(5).getCTTc().addNewTcPr().addNewShd().setFill(cor);
				
				tableRowOne = table.getRow(i);
				tableRowOne.getCell(6).removeParagraph(0);
				paragraph = tableRowOne.getCell(6).addParagraph();
				criarParagrafoTabela(paragraph, NumberFormat.getCurrencyInstance(ptBr)
						.format((recebimento.getValor_nf_venda().doubleValue())), false);
				valor_total_nf_venda  = valor_total_nf_venda.add(recebimento.getValor_nf_venda());
				tableRowOne.getCell(6).getCTTc().addNewTcPr().addNewShd().setFill(cor);
				
				}else {
					tableRowOne = table.getRow(i);
					tableRowOne.getCell(4).removeParagraph(0);
					paragraph = tableRowOne.getCell(4).addParagraph();
					criarParagrafoTabela(paragraph, "Não Aplicável", false);

					tableRowOne = table.getRow(i);
					tableRowOne.getCell(5).removeParagraph(0);
					paragraph = tableRowOne.getCell(5).addParagraph();
					criarParagrafoTabela(paragraph,"Não Aplicável", false);
					
					tableRowOne = table.getRow(i);
					tableRowOne.getCell(6).removeParagraph(0);
					paragraph = tableRowOne.getCell(6).addParagraph();
					criarParagrafoTabela(paragraph,"Não Aplicável", false);
					
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
					criarParagrafoTabela(paragraph, z.format(recebimento.getPeso_nf_remessa())  + " Kgs", false);
					soma_total_nf_remessa += recebimento.getPeso_nf_remessa();
					tableRowOne.getCell(8).getCTTc().addNewTcPr().addNewShd().setFill(cor);
					
					tableRowOne = table.getRow(i);
					tableRowOne.getCell(9).removeParagraph(0);
					paragraph = tableRowOne.getCell(9).addParagraph();
					criarParagrafoTabela(paragraph, NumberFormat.getCurrencyInstance(ptBr)
							.format((recebimento.getValor_nf_remessa().doubleValue())), false);
					valor_total_nf_remessa = valor_total_nf_remessa.add(recebimento.getValor_nf_remessa());
					tableRowOne.getCell(9).getCTTc().addNewTcPr().addNewShd().setFill(cor);
					
					}else {
						tableRowOne = table.getRow(i);
						tableRowOne.getCell(7).removeParagraph(0);
						paragraph = tableRowOne.getCell(7).addParagraph();
						criarParagrafoTabela(paragraph, "Não Aplicável", false);

						tableRowOne = table.getRow(i);
						tableRowOne.getCell(8).removeParagraph(0);
						paragraph = tableRowOne.getCell(8).addParagraph();
						criarParagrafoTabela(paragraph,"Não Aplicável", false);
						
						tableRowOne = table.getRow(i);
						tableRowOne.getCell(9).removeParagraph(0);
						paragraph = tableRowOne.getCell(9).addParagraph();
						criarParagrafoTabela(paragraph,"Não Aplicável", false);
						
					}

			i++;
			
			
			
		}
		//somatorias
				//peso de romaneios
				
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

		//valor nf venda

		tableRowOne = table.getRow(i);
				tableRowOne.getCell(6).removeParagraph(0);
				paragraph = tableRowOne.getCell(6).addParagraph();
				criarParagrafoTabela(paragraph, NumberFormat.getCurrencyInstance(ptBr)
						.format(valor_total_nf_venda),true);

		//peso nf remessa

		tableRowOne = table.getRow(i);
				tableRowOne.getCell(8).removeParagraph(0);
				paragraph = tableRowOne.getCell(8).addParagraph();
				criarParagrafoTabela(paragraph,
						" " + z.format(soma_total_nf_remessa) + " kgs / " + (z.format((soma_total_nf_remessa / 60))) + " sacos ",
						true);

		//valor nf remessa

		tableRowOne = table.getRow(i);
				tableRowOne.getCell(9).removeParagraph(0);
				paragraph = tableRowOne.getCell(9).addParagraph();
				criarParagrafoTabela(paragraph, NumberFormat.getCurrencyInstance(ptBr)
						.format(valor_total_nf_remessa),true);
				
				
				// informacoes de total
		i+=2;
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
				if(nf_venda_ativo) {
				criarParagrafoTabela(paragraph,
						" " + z.format(soma_total_nf_venda) + " kgs / " + (z.format((soma_total_nf_venda / 60))) + " sacos ",
						true);
				}else {
					criarParagrafoTabela(paragraph,"Não Aplicável",true);
				}

				//valor nf venda
				
				tableRowOne = table.getRow(i);
				tableRowOne.getCell(4).removeParagraph(0);
				paragraph = tableRowOne.getCell(4).addParagraph();
				criarParagrafoTabela(paragraph, "Valor Total NFV's:", false);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(5).removeParagraph(0);
				paragraph = tableRowOne.getCell(5).addParagraph();
				if(nf_venda_ativo) {
				criarParagrafoTabela(paragraph, NumberFormat.getCurrencyInstance(ptBr)
						.format(valor_total_nf_venda),true);
				}else {
					criarParagrafoTabela(paragraph, "Não Aplicável",true);
				}
				//peso nf remessa
				tableRowOne = table.getRow(i);
				tableRowOne.getCell(6).removeParagraph(0);
				paragraph = tableRowOne.getCell(6).addParagraph();
				criarParagrafoTabela(paragraph, "Peso Total NFR's:", false);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(7).removeParagraph(0);
				paragraph = tableRowOne.getCell(7).addParagraph();
				if(nf_remessa_ativo) {
				criarParagrafoTabela(paragraph,
						" " + z.format(soma_total_nf_remessa) + " kgs / " + (z.format((soma_total_nf_remessa / 60))) + " sacos ",
						true);
				}else {
					criarParagrafoTabela(paragraph,"Não Aplicável", true);	
				}
				
				//valor nf remessa
				

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(8).removeParagraph(0);
				paragraph = tableRowOne.getCell(8).addParagraph();
				criarParagrafoTabela(paragraph, "Valor Total NFR's:", false);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(9).removeParagraph(0);
				paragraph = tableRowOne.getCell(9).addParagraph();
				if(nf_remessa_ativo) {
				criarParagrafoTabela(paragraph, NumberFormat.getCurrencyInstance(ptBr)
						.format(valor_total_nf_remessa),true);
				}else {
					criarParagrafoTabela(paragraph,"Não Aplicável",true);
				}
				i++;

				// total a receber em kgs

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(0).removeParagraph(0);
				paragraph = tableRowOne.getCell(0).addParagraph();
				criarParagrafoTabela(paragraph, "Total a Receber:", false);

				double quantidade_total_sacos = 0;
				double quantidade_total_kgs = 0;

				
				//quantidade para os totais
				
				if(soma_total_quantidade_contratos!= 0) {
					quantidade_total_kgs= soma_total_quantidade_contratos;
				}else {
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
				if(soma_total_quantidade_contratos != 0) {
				criarParagrafoTabela(paragraph, " " + z.format(soma_total_quantidade_contratos - soma_total_romaneio) + " kgs / "
						+ (z.format(((soma_total_quantidade_contratos - soma_total_romaneio) / 60))) + " sacos ", true);
				}else {
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
				if(nf_venda_ativo) {
				if(soma_total_quantidade_contratos != 0 ) {
					criarParagrafoTabela(paragraph, " " + z.format(soma_total_quantidade_contratos - soma_total_nf_venda) + " kgs / "
							+ (z.format(((soma_total_quantidade_contratos - soma_total_nf_venda) / 60))) + " sacos ", true);
				}else {
				criarParagrafoTabela(paragraph, " " + z.format(quantidade_total_kgs - soma_total_nf_venda) + " kgs / "
						+ (z.format(((quantidade_total_kgs - soma_total_nf_venda) / 60))) + " sacos ", true);
				}
				}else {
					criarParagrafoTabela(paragraph,"Não Aplicável", true);
					
				}

				// pesos de nf remessa
				tableRowOne = table.getRow(i);
				tableRowOne.getCell(6).removeParagraph(0);
				paragraph = tableRowOne.getCell(6).addParagraph();
				criarParagrafoTabela(paragraph, "Total a emitir NFR's:", false);

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(7).removeParagraph(0);
				paragraph = tableRowOne.getCell(7).addParagraph();
				if(nf_remessa_ativo) {
				if(soma_total_quantidade_contratos != 0 ) {
					criarParagrafoTabela(paragraph, " " + z.format(soma_total_quantidade_contratos - soma_total_nf_remessa) + " kgs / "
							+ (z.format(((soma_total_quantidade_contratos - soma_total_nf_remessa) / 60))) + " sacos ", true);
				}else {
				criarParagrafoTabela(paragraph, " " + z.format(quantidade_total_kgs - soma_total_nf_remessa) + " kgs / "
						+ (z.format(((quantidade_total_kgs - soma_total_nf_remessa) / 60))) + " sacos ", true);
				}
				}else {
					criarParagrafoTabela(paragraph, "Não Aplicável", true);
					
				}
				i++;
				
				//texto do status

				
				String texto = "Status Gerado automaticamente: \n";
				double diferenca = quantidade_total_kgs - soma_total_romaneio;
				if(diferenca == 0) {
					texto = texto + "Recebimento Concluído\n";
				}else if(diferenca > 0) {
					texto = texto + "Recebimento Incompleto, falta receber " + z.format(diferenca) + " Kgs\n";

				}else if(diferenca < 0) {
					texto = texto + "Recebimento Excedido, excedeu " + z.format(diferenca) + " Kgs\n";

				}
				

				String texto_nf_venda = "";
				if(nf_venda_ativo) {
				double diferenca_nf_venda = quantidade_total_kgs - soma_total_nf_venda;
				if(diferenca_nf_venda == 0) {
					texto_nf_venda = texto_nf_venda + "Emissão de NF's de Venda Concluído\n";
				}else if(diferenca_nf_venda > 0) {
					texto_nf_venda = texto_nf_venda + "Emissão de NF's de Venda Incompleto, falta emitir " + z.format(diferenca_nf_venda) + " Kgs\n";

				}else if(diferenca_nf_venda < 0) {
					texto_nf_venda = texto_nf_venda + "Emissão de NF's Venda Excedido, excedeu " + z.format(diferenca_nf_venda) + " Kgs\n";

				}
				}else {
					texto_nf_venda = "Emissão de NF's Venda Não Aplicável";
				}
				
				
				String texto_nf_remessa = "";
				if(nf_remessa_ativo) {
				double diferenca_nf_remessa = quantidade_total_kgs - soma_total_nf_remessa;
				if(diferenca_nf_remessa == 0) {
					texto_nf_remessa = texto_nf_remessa + "Emissão de NF's de Remessa Concluído\n";
				}else if(diferenca_nf_remessa > 0) {
					texto_nf_remessa = texto_nf_remessa + "Emissão de NF's de Remessa Incompleto, falta emitir " + z.format(diferenca_nf_remessa) + " Kgs\n";

				}else if(diferenca_nf_remessa < 0) {
					texto_nf_remessa = texto_nf_remessa + "Emissão de NF's Remessa Excedido, excedeu " + z.format(diferenca_nf_remessa) + " Kgs\n";

				}
				}else {
					texto_nf_remessa = "Emissão de NF's Remessa Não Aplicável";
				}

		         substituirTexto(texto + texto_nf_venda + texto_nf_remessa);
				
		
		

	}

	
	
	
	public void criarTabelaCarregamentos(ArrayList<CadastroContrato.Carregamento> carregamentos, double soma_total_quantidade_contratos) {
		// XWPFParagraph par = document_global.createParagraph();

		NumberFormat z = NumberFormat.getNumberInstance();

		Locale ptBr = new Locale("pt", "BR");

		// criarParagrafo(1);
		// linhas x colunas
		
		int num_linhas_carregamentos = -1;
		
		if(soma_total_quantidade_contratos == 0) {
			
			num_linhas_carregamentos = carregamentos.size() + 1 + 1 + 1 + 1;
		}else {
			num_linhas_carregamentos = carregamentos.size() + 1 + 1 + 1;

		}
		
		
		double soma_total_carga_real = 0;
		double soma_total_carga_nfa = 0;
		
	
		GerenciarBancoTransferenciasCarga gerenciar_trans = new GerenciarBancoTransferenciasCarga();
		ArrayList<CadastroContrato.CadastroTransferenciaCarga> transferencias_remetentes = null;
		ArrayList<CadastroContrato.CadastroTransferenciaCarga> transferencias_destinatarios = null;
		
		CadastroContrato contrato_deste_carregamento = new GerenciarBancoContratos().getContrato(carregamentos.get(0).getId_contrato());
		

		
		double quantidade_kg = 0;
		double quantidade_sacos = 0;
		
		if(contrato_deste_carregamento.getMedida().equalsIgnoreCase("KG")) {
			quantidade_kg = contrato_deste_carregamento.getQuantidade();
			quantidade_sacos = quantidade_kg / 60;
		}else if(contrato_deste_carregamento.getMedida().equalsIgnoreCase("Sacos")) {
			quantidade_sacos = contrato_deste_carregamento.getQuantidade();
			quantidade_kg = quantidade_sacos * 60;
		}
		
		
		transferencias_remetentes = gerenciar_trans
				.getTransferenciasRemetente(contrato_deste_carregamento.getId());
		transferencias_destinatarios = gerenciar_trans
				.getTransferenciaDestinatario(contrato_deste_carregamento.getId());
		
		
		 num_linhas_carregamentos = num_linhas_carregamentos  + transferencias_remetentes.size() + transferencias_destinatarios.size();
		
		
		XWPFTable table = document_global.createTable(num_linhas_carregamentos,16);

		setTableAlign(table, ParagraphAlignment.CENTER);
		XWPFTableRow tableRowOne = table.getRow(0);
		tableRowOne.getCell(0).removeParagraph(0);

		XWPFParagraph paragraph = tableRowOne.getCell(0).addParagraph();
		
		//
		int cabecalho = 0;
		
		if(soma_total_quantidade_contratos != 0) {
			
			tableRowOne = table.getRow(cabecalho);
			tableRowOne.getCell(0).removeParagraph(0);
			paragraph = tableRowOne.getCell(0).addParagraph();
			
			criarParagrafoTabela(paragraph,"Quantidade Total: " + z.format(soma_total_quantidade_contratos) + " kgs | " + z.format(soma_total_quantidade_contratos/60) + " sacos", true);
			tableRowOne.getCell(0).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");
			CTHMerge hMerge = CTHMerge.Factory.newInstance();
			hMerge.setVal(STMerge.RESTART);
			table.getRow(cabecalho).getCell(0).getCTTc().getTcPr().setHMerge(hMerge);
			
			for(int celula = 1; celula <= 15; celula++) {
				tableRowOne = table.getRow(cabecalho);
				tableRowOne.getCell(celula).removeParagraph(0);
				paragraph = tableRowOne.getCell(celula).addParagraph();
				
				criarParagrafoTabela(paragraph,"", true);
				tableRowOne.getCell(celula).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");
				
				CTHMerge hMerge1 = CTHMerge.Factory.newInstance();
				hMerge1.setVal(STMerge.CONTINUE);
				table.getRow(cabecalho).getCell(celula).getCTTc().getTcPr().setHMerge(hMerge1);
				
			}
			
		
			cabecalho++;
			
			
			
		}else {
			
		
			
			tableRowOne = table.getRow(cabecalho);
			tableRowOne.getCell(0).removeParagraph(0);
			paragraph = tableRowOne.getCell(0).addParagraph();
			
			//compradores x vendedores
			
			
			// safra
			String safra = contrato_deste_carregamento.getModelo_safra().getProduto().getNome_produto() + " " + contrato_deste_carregamento.getModelo_safra().getProduto().getTransgenia() +
					" " + contrato_deste_carregamento.getModelo_safra().getAno_plantio() + "/" + contrato_deste_carregamento.getModelo_safra().getAno_colheita();
			
			criarParagrafoTabela(paragraph,"CTR: " + contrato_deste_carregamento.getCodigo() + " " + safra + " Quantidade Total: " + z.format(quantidade_kg) + " kgs | " + z.format(quantidade_sacos) + " sacos "
					+NumberFormat.getCurrencyInstance(ptBr).format(contrato_deste_carregamento.getValor_produto()) +
							" por " + contrato_deste_carregamento.getMedida() + " totalizando: " +  NumberFormat.getCurrencyInstance(ptBr).format(contrato_deste_carregamento.getValor_a_pagar().doubleValue()), true);
			tableRowOne.getCell(0).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");
			CTHMerge hMerge = CTHMerge.Factory.newInstance();
			hMerge.setVal(STMerge.RESTART);
			table.getRow(cabecalho).getCell(0).getCTTc().getTcPr().setHMerge(hMerge);
			
			for(int celula = 1; celula <= 15; celula++) {
				tableRowOne = table.getRow(cabecalho);
				tableRowOne.getCell(celula).removeParagraph(0);
				paragraph = tableRowOne.getCell(celula).addParagraph();
				
				criarParagrafoTabela(paragraph,"", true);
				tableRowOne.getCell(celula).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");
				
				CTHMerge hMerge1 = CTHMerge.Factory.newInstance();
				hMerge1.setVal(STMerge.CONTINUE);
				table.getRow(cabecalho).getCell(celula).getCTTc().getTcPr().setHMerge(hMerge1);
				
			}
			
			cabecalho++;

			//linha com nome compradores x vendedores
			
			tableRowOne = table.getRow(cabecalho);
			tableRowOne.getCell(0).removeParagraph(0);
			paragraph = tableRowOne.getCell(0).addParagraph();
			

			CadastroCliente compradores[] = contrato_deste_carregamento.getCompradores();
			CadastroCliente vendedores[] = contrato_deste_carregamento.getVendedores();


			

			String nome_vendedores = "";
			String nome_compradores= "";


			
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
			if(vendedores[0] != null) {
			if(vendedores[0].getTipo_pessoa() == 0) {
				nome_vendedores = vendedores[0].getNome_empresarial();
			}else {
				nome_vendedores = vendedores[0].getNome_fantaia();
			}
			}
			
			if(vendedores[1] != null) {
				if(vendedores[1].getTipo_pessoa() == 0) {
					nome_vendedores = nome_vendedores + ", " + vendedores[1].getNome_empresarial();
				}else {
					nome_vendedores = nome_vendedores + ", " + vendedores[1].getNome_fantaia();
				}
			}
			
			
			
			criarParagrafoTabela(paragraph,nome_compradores + " X "+ nome_vendedores, true);
			tableRowOne.getCell(0).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");
			 hMerge = CTHMerge.Factory.newInstance();
			hMerge.setVal(STMerge.RESTART);
			table.getRow(cabecalho).getCell(0).getCTTc().getTcPr().setHMerge(hMerge);
			
			for(int celula = 1; celula <= 15; celula++) {
				tableRowOne = table.getRow(cabecalho);
				tableRowOne.getCell(celula).removeParagraph(0);
				paragraph = tableRowOne.getCell(celula).addParagraph();
				
				criarParagrafoTabela(paragraph,"", true);
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
		
		double peso_total_nf_interna = 0.0 ;
		double peso_total_romaneios= 0.0 ;
		double peso_total_nf_venda1 = 0.0;
		double peso_total_nf_complemento = 0.0;
		double peso_total_diferenca = 0.0;

		BigDecimal valor_total_nf_venda1 = BigDecimal.ZERO;
		BigDecimal valor_total_nf_complemento = BigDecimal.ZERO;
		
		//fazer checkagens

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
		
			
			if(nome_cliente_quebrado.length > 2) {
			    if(nome_cliente_quebrado[2].length() > 1) {
			    	nome_cliente = nome_cliente_quebrado[0] + " " + nome_cliente_quebrado[2];
			    }else {
			    	if(nome_cliente_quebrado[3].length() > 1) {
			    		nome_cliente = nome_cliente_quebrado[0] + " "+ nome_cliente_quebrado[3];

			    	}else {
			    		nome_cliente = nome_cliente_quebrado[0] + " "+ nome_cliente_quebrado[1];

			    	}
			    }
			}
			
			}catch(Exception v) {
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
		
			
			if(nome_vendedor_quebrado.length > 2) {
			    if(nome_vendedor_quebrado[2].length() > 1) {
			    	nome_vendedor = nome_vendedor_quebrado[0] + " " + nome_vendedor_quebrado[2];
			    }else {
			    	if(nome_vendedor_quebrado[3].length() > 1) {
			    		nome_vendedor = nome_vendedor_quebrado[0] + " "+ nome_vendedor_quebrado[3];

			    	}else {
			    		nome_vendedor = nome_vendedor_quebrado[0] + " "+ nome_vendedor_quebrado[1];

			    	}
			    }
			}
			
			}catch(Exception v) {
				nome_vendedor = nome_vendedor_completo;
			}
			
			String nome_transportador = "";
			String placa_trator = "";
			if(carregamento.getId_transportador() > 0 ) {
			// pegar transportador e veiculo
			CadastroCliente transportador_carregamento = gerenciar_clientes
					.getCliente(carregamento.getId_transportador());
			
			 if(transportador_carregamento != null) {
				 if(transportador_carregamento.getTipo_pessoa() == 0) {
					 nome_transportador = transportador_carregamento.getNome_empresarial();
				 }else {
					 nome_transportador = transportador_carregamento.getNome_fantaia();

				 }
			 }
			 CadastroCliente.Veiculo veiculo_carregamento = null ;
			 if(carregamento.getId_veiculo() > 0) {
			for (CadastroCliente.Veiculo veiculo : transportador_carregamento.getVeiculos()) {
				if (veiculo.getId_veiculo() == carregamento.getId_veiculo()) {
					 veiculo_carregamento = veiculo;
					break;
				}
			}
			
			if(veiculo_carregamento != null) {
				placa_trator = veiculo_carregamento.getPlaca_trator();
			}
			 }

			}
			// pegar o produto
			GerenciarBancoProdutos gerenciar_produtos = new GerenciarBancoProdutos();
			CadastroProduto produto_carregamento = gerenciar_produtos.getProduto(carregamento.getId_produto());

			//codigos
			String codigo_romaneio = "";
			String codigo_nf_venda1 = "", codigo_nf_complemento = "";
			//pesos
			
			double peso_romaneio = 0.0;
			double peso_nf_venda1 = 0.0;
			double peso_nf_interna = 0.0;

			BigDecimal valor_nf_venda1 = BigDecimal.ZERO;
			double peso_nf_complemento = 0.0;
			BigDecimal valor_nf_complemento = BigDecimal.ZERO;

			
			
			
			
			try {
			    if(checkString(carregamento.getCodigo_romaneio())){
			    	//procurar por romaneio
			    	if(checkString(carregamento.getCaminho_romaneio())){
			    		ManipularRomaneios manipular = new ManipularRomaneios("");

			        	CadastroRomaneio romaneio = manipular.filtrar(new File(servidor_unidade + carregamento.getCaminho_romaneio()));
			        	codigo_romaneio = Integer.toString(romaneio.getNumero_romaneio());
			        	peso_romaneio = romaneio.getPeso_liquido();
			        	
			    	}else {
			    		codigo_romaneio = carregamento.getCodigo_romaneio();
			    		peso_romaneio = carregamento.getPeso_romaneio();
			    	}
			    
			    }
				}catch(Exception e) {
					//JOptionPane.showMessageDialog(isto, "Romaneio não Localizado");
					codigo_romaneio = carregamento.getCodigo_romaneio();
		    		peso_romaneio = carregamento.getPeso_romaneio();
				}
				
			
				
				
				//nf venda 1
				try {
			        if(checkString(carregamento.getCodigo_nf_venda1())){
			        	if(carregamento.getCaminho_nf_venda1().length() > 10) {
			        		//procurar por nf venda
				        	ManipularNotasFiscais manipular = new ManipularNotasFiscais("");
				        	CadastroNFe nota_fiscal_venda = manipular.filtrar(new File(servidor_unidade + carregamento.getCodigo_nf_venda1()));
                            codigo_nf_venda1 = nota_fiscal_venda.getNfe();
                            peso_nf_venda1 = Double.parseDouble(nota_fiscal_venda.getQuantidade());
                            try {
                            valor_nf_venda1 = new BigDecimal(nota_fiscal_venda.getValor());
                            }catch(Exception e) {
                            	valor_nf_venda1 = BigDecimal.ZERO;
                            }
				        	
			        	}else {
			        		 codigo_nf_venda1 = carregamento.getCodigo_nf_venda1();
	                            peso_nf_venda1 = carregamento.getPeso_nf_venda1();
	                            valor_nf_venda1 = carregamento.getValor_nf_venda1();
					        	

			        	}
			        
			        	
			        }
					}catch(Exception e) {
						//JOptionPane.showMessageDialog(isto, "Nota Fiscal de venda não Localizado");
						 codigo_nf_venda1 = carregamento.getCodigo_nf_venda1();
                         peso_nf_venda1 = carregamento.getPeso_nf_venda1();
                         valor_nf_venda1 = carregamento.getValor_nf_venda1();

					}
				
				
				
				
						//nf complemento
						try {
					        if(checkString(carregamento.getCodigo_nf_complemento())){
					        	if(carregamento.getCaminho_nf_complemento().length() > 10) {
					        		//procurar por nf remessa
						        	ManipularNotasFiscais manipular = new ManipularNotasFiscais("");
						        	CadastroNFe nota_fiscal_complemento = manipular.filtrar(new File(servidor_unidade + carregamento.getCaminho_nf_complemento()));
						        	 codigo_nf_complemento = nota_fiscal_complemento.getNfe();
			                            peso_nf_complemento= Double.parseDouble(nota_fiscal_complemento.getQuantidade());
			                            try {
			                                valor_nf_complemento = new BigDecimal(nota_fiscal_complemento.getValor());
			                                }catch(Exception e) {
			                                	valor_nf_complemento = BigDecimal.ZERO;
			                                }
			                            
					        	}else {
					        		codigo_nf_complemento = carregamento.getCodigo_nf_complemento();
		                            peso_nf_complemento= carregamento.getPeso_nf_complemento();
		                            valor_nf_complemento = carregamento.getValor_nf_complemento();

					        	}
					        
					        
					        	
					        }
							}catch(Exception e) {
								//JOptionPane.showMessageDialog(isto, "Nota Fiscal de remessa não Localizado");

								codigo_nf_complemento = carregamento.getCodigo_nf_complemento();
	                            peso_nf_complemento= carregamento.getPeso_nf_complemento();
	                            valor_nf_complemento = carregamento.getValor_nf_complemento();

							}
				
				


            String cor = "000000";
            
            if((peso_nf_venda1 + peso_nf_complemento) >= peso_romaneio) {
            	//ok
            	cor = "FFFFFF";
            }else if((peso_nf_venda1 + peso_nf_complemento) < peso_romaneio) {
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
			criarParagrafoTabela(paragraph,
					nome_transportador, false);

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
			if(carregamento.getNf_venda1_aplicavel() == 1)
			criarParagrafoTabela(paragraph, codigo_nf_venda1, false);
			else
				criarParagrafoTabela(paragraph, "Não Aplicável", false);

				
			tableRowOne = table.getRow(i);
			tableRowOne.getCell(10).removeParagraph(0);
			paragraph = tableRowOne.getCell(10).addParagraph();
			if(carregamento.getNf_venda1_aplicavel() == 1) {
			criarParagrafoTabela(paragraph, z.format(peso_nf_venda1) + " Kgs", false);
			tableRowOne.getCell(10).getCTTc().addNewTcPr().addNewShd().setFill(cor);
			}else {
				criarParagrafoTabela(paragraph, "Não Aplicável", false);
			}

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(11).removeParagraph(0);
			paragraph = tableRowOne.getCell(11).addParagraph();
			if(carregamento.getNf_venda1_aplicavel() == 1) {
			criarParagrafoTabela(paragraph, NumberFormat.getCurrencyInstance(ptBr)
					.format(valor_nf_venda1), false);
			}else {
				criarParagrafoTabela(paragraph, "Não Aplicável", false);	
			}
			
			tableRowOne = table.getRow(i);
			tableRowOne.getCell(12).removeParagraph(0);
			paragraph = tableRowOne.getCell(12).addParagraph();
			if(carregamento.getNf_complemento_aplicavel() ==  1)
			criarParagrafoTabela(paragraph, codigo_nf_complemento, false);
			else
				criarParagrafoTabela(paragraph, "Não Aplicável", false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(13).removeParagraph(0);
			paragraph = tableRowOne.getCell(13).addParagraph();
			if(carregamento.getNf_complemento_aplicavel() == 1) {
			criarParagrafoTabela(paragraph, z.format(peso_nf_complemento) + " Kgs", false);
			tableRowOne.getCell(13).getCTTc().addNewTcPr().addNewShd().setFill(cor);
			}else {
				criarParagrafoTabela(paragraph, "Não Aplicável", false);

			}
			tableRowOne = table.getRow(i);
			tableRowOne.getCell(14).removeParagraph(0);
			paragraph = tableRowOne.getCell(14).addParagraph();
			if(carregamento.getNf_complemento_aplicavel() == 1)
			criarParagrafoTabela(paragraph, NumberFormat.getCurrencyInstance(ptBr)
					.format(valor_nf_complemento), false);
			else {
				criarParagrafoTabela(paragraph,"Não Aplicável", false);
			}
			

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(15).removeParagraph(0);
			paragraph = tableRowOne.getCell(15).addParagraph();
			if(carregamento.getNf_venda1_aplicavel() == 1 && carregamento.getNf_complemento_aplicavel() == 1) {
			criarParagrafoTabela(paragraph,z.format(peso_romaneio - (peso_nf_complemento + peso_nf_venda1)  ) + " Kgs", false);
			tableRowOne.getCell(15).getCTTc().addNewTcPr().addNewShd().setFill(cor);
			}else {
				criarParagrafoTabela(paragraph,"Não Aplicável", false);

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
		
		//transferencias negativas
				/***************************transferencias negativas*****************///////////
				
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
					criarParagrafoTabela(paragraph, transferencia.getData(), false);

					tableRowOne = table.getRow(i);
					tableRowOne.getCell(1).removeParagraph(0);
					paragraph = tableRowOne.getCell(1).addParagraph();
					criarParagrafoTabela(paragraph, texto_detalhado, false);

					CTHMerge hMerge = CTHMerge.Factory.newInstance();
					hMerge.setVal(STMerge.RESTART);
					if (table.getRow(i).getCell(1).getCTTc().getTcPr() == null) {
						 table.getRow(i).getCell(1).getCTTc().addNewTcPr();
						 table.getRow(i).getCell(1).getCTTc().getTcPr().setHMerge(hMerge);

					}else
					 table.getRow(i).getCell(1).getCTTc().getTcPr().setHMerge(hMerge);
					
					for(int celula = 2; celula <= 15; celula++) {
						tableRowOne = table.getRow(i);
						tableRowOne.getCell(celula).removeParagraph(0);
						paragraph = tableRowOne.getCell(celula).addParagraph();
						
						criarParagrafoTabela(paragraph,"", true);
						tableRowOne.getCell(celula).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");
						
						CTHMerge hMerge1 = CTHMerge.Factory.newInstance();
						hMerge1.setVal(STMerge.CONTINUE);
						table.getRow(i).getCell(celula).getCTTc().getTcPr().setHMerge(hMerge1);
						
					}
					
					peso_total_romaneios -= quantidade;			
					
					i++;

				}
				/***************************transferencias negativas*****************///////////
		
				//transfereicas positivas
				/***************************transferencias positivas*****************///////////
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
					texto_detalhado = texto_detalhado + destinatario.getCodigo() + " " + nome_compradores + " X "
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
					criarParagrafoTabela(paragraph, transferencia.getData(), false);

					tableRowOne = table.getRow(i);
					tableRowOne.getCell(1).removeParagraph(0);
					paragraph = tableRowOne.getCell(1).addParagraph();
					criarParagrafoTabela(paragraph, texto_detalhado, false);

					CTHMerge hMerge = CTHMerge.Factory.newInstance();
					hMerge.setVal(STMerge.RESTART);
					if (table.getRow(i).getCell(1).getCTTc().getTcPr() == null) {
						 table.getRow(i).getCell(1).getCTTc().addNewTcPr();
						 table.getRow(i).getCell(1).getCTTc().getTcPr().setHMerge(hMerge);

					}else
					 table.getRow(i).getCell(1).getCTTc().getTcPr().setHMerge(hMerge);
					
					for(int celula = 2; celula <= 15; celula++) {
						tableRowOne = table.getRow(i);
						tableRowOne.getCell(celula).removeParagraph(0);
						paragraph = tableRowOne.getCell(celula).addParagraph();
						
						criarParagrafoTabela(paragraph,"", true);
						tableRowOne.getCell(celula).getCTTc().addNewTcPr().addNewShd().setFill("FFFFFF");
						
						CTHMerge hMerge1 = CTHMerge.Factory.newInstance();
						hMerge1.setVal(STMerge.CONTINUE);
						table.getRow(i).getCell(celula).getCTTc().getTcPr().setHMerge(hMerge1);
						
					}
					
					
					peso_total_romaneios += quantidade;			
					
					i++;

				}
				
				/***************************transferencias positivas*****************///////////

				 //informacoes de total
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
						texto = texto + (z.format(peso_total_nf_venda1 + peso_total_nf_complemento)
								+ " Kgs]");

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
						texto = texto + z
								.format(peso_total_romaneios
										- (peso_total_nf_venda1 + peso_total_nf_complemento))
								+ " Kgs]";

					} else {
						texto = texto + " Não Aplicável";

					}
					texto = texto + "\n";

					texto = texto + "*Pesos restantes calculados a partir do peso total já carregado";

					// status baseado no peso total ja carregado

					texto = texto + "\n\n";

					
					texto = texto +	"Status parcial gerado de forma automatica calculados a partir do peso total já carregado";

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
					double diferenca_nf_venda = peso_total_romaneios
							- (peso_total_nf_venda1 + peso_total_nf_complemento);
					if (diferenca_nf_venda == 0) {
						texto_nf_venda = texto_nf_venda + "[Emissão de NF's de Venda Concluído]";
					} else if (diferenca_nf_venda > 0) {
						texto_nf_venda = texto_nf_venda + "Emissão de NF's de Venda Incompleto, [falta emitir "
								+ z.format(diferenca_nf_venda) + " Kgs]";

					} else if (diferenca_nf_venda < 0) {
						texto_nf_venda = texto_nf_venda + "Emissão de NF's Excedido, excedeu "
								+ z.format(diferenca_nf_venda) + " Kgs";

					}
					if (nf_venda_ativo || nf_complemento_ativo)
						texto = texto + (texto_nf_venda);
					else
						texto = texto + "Emissão de NF's de Venda Não Aplicável";

				}
				//informacoes de total na tabela
				//somatoria dos romaneios
				
				//peso total romaneios
				tableRowOne = table.getRow(i);
				tableRowOne.getCell(8).removeParagraph(0);
				paragraph = tableRowOne.getCell(8).addParagraph();
				criarParagrafoTabela(paragraph, z.format(peso_total_romaneios) + " Kgs", true);
				
				//peso total nf venda 1
				tableRowOne = table.getRow(i);
				tableRowOne.getCell(10).removeParagraph(0);
				paragraph = tableRowOne.getCell(10).addParagraph();
				criarParagrafoTabela(paragraph, z.format(peso_total_nf_venda1) + " Kgs", true);
				
				//valor total nf venda 1
				tableRowOne = table.getRow(i);
				tableRowOne.getCell(11).removeParagraph(0);
				paragraph = tableRowOne.getCell(11).addParagraph();
				criarParagrafoTabela(paragraph,NumberFormat.getCurrencyInstance(ptBr)
						.format(valor_total_nf_venda1) , true);
					
						//peso total nf complemento
						tableRowOne = table.getRow(i);
						tableRowOne.getCell(13).removeParagraph(0);
						paragraph = tableRowOne.getCell(13).addParagraph();
						criarParagrafoTabela(paragraph, z.format(peso_total_nf_complemento) + " Kgs", true);
						
						tableRowOne = table.getRow(i);
						tableRowOne.getCell(14).removeParagraph(0);
						paragraph = tableRowOne.getCell(14).addParagraph();
						criarParagrafoTabela(paragraph,NumberFormat.getCurrencyInstance(ptBr)
								.format(valor_total_nf_complemento) , true);
						
						//diferenca

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
					texto = texto + (z.format(peso_total_nf_venda1 + peso_total_nf_complemento)
							+ " Kgs]");

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
					texto = texto + z
							.format(quantidade_kg
									- (peso_total_nf_venda1 + peso_total_nf_complemento))
							+ " Kgs]";

				} else {
					texto = texto + " [Não Aplicável]";

				}
				texto = texto + "\n";

				texto = texto + "*Pesos restantes calculados a partir do peso total";

				// status baseado no peso total ja carregado

				texto = texto + "\n\n";

				
				texto = texto +	"[Status parcial gerado de forma automatica calculados a partir do peso total:]";

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
				double diferenca_nf_venda = quantidade_kg
						- (peso_total_nf_venda1 + peso_total_nf_complemento);
				if (diferenca_nf_venda == 0) {
					texto_nf_venda = texto_nf_venda + "[Emissão de NF's de Venda Concluído]";
				} else if (diferenca_nf_venda > 0) {
					texto_nf_venda = texto_nf_venda + "Emissão de NF's de Venda Incompleto, [falta emitir "
							+ z.format(diferenca_nf_venda) + " Kgs]";

				} else if (diferenca_nf_venda < 0) {
					texto_nf_venda = texto_nf_venda + "Emissão de NF's Excedido, [excedeu "
							+ z.format(diferenca_nf_venda) + " Kgs]";

				}
				if (nf_venda_ativo || nf_complemento_ativo)
					texto = texto + (texto_nf_venda);
				else
					texto = texto + "Emissão de NF's de Venda Não Aplicável";

			

		////////////////////////////////////////////////////////////////////////////////		
		substituirTexto(-1,texto);

		 texto = "";

		texto = "Peso Total Romaneios: [" + z.format(peso_total_romaneios) + " kgs ] | [" + z.format(peso_total_romaneios/60) + " sacos ]\n\n";
		if(nf_venda_ativo || nf_complemento_ativo) {
		texto = texto + "Peso Total NF's Venda 1: [" + z.format(peso_total_nf_venda1) + " kgs ] | [" + z.format(peso_total_nf_venda1/60) + " sacos ]\n";
		texto = texto + "Peso Total NF's Complemento: [" + z.format(peso_total_nf_complemento) + " kgs ] | [" + z.format(peso_total_nf_complemento/60) + " sacos ]\n";
		texto = texto + "Peso Total NF's: [" + z.format(peso_total_nf_complemento + peso_total_nf_venda1)+ " kgs ] | [" + z.format((peso_total_nf_complemento + peso_total_nf_venda1)/60) + " sacos ]\n";
		texto = texto + "Diferença Total: [" + z.format(peso_total_romaneios - (peso_total_nf_complemento + peso_total_nf_venda1)) + " kgs ] | [" + z.format((peso_total_romaneios - (peso_total_nf_complemento + peso_total_nf_venda1))/60) + " sacos ]\n";
		texto = texto + "Valor Total NF's Venda 1: [" +  NumberFormat.getCurrencyInstance(ptBr)
		.format(valor_total_nf_venda1) + "]\n";
		texto = texto + "Valor Total NF's Complemento: [" +  NumberFormat.getCurrencyInstance(ptBr)
		.format(valor_total_nf_complemento) + "]\n";
		texto = texto + "Valor Total NF's: [" +  NumberFormat.getCurrencyInstance(ptBr)
		.format(valor_total_nf_complemento.add(valor_total_nf_venda1)) + "]\n";
		}else {
			texto = texto + "NF's Venda Não Aplicável";
		}
		
		substituirTexto(1,texto);
	
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
					"_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ __ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ ");

		}
		tracotitleRun.setColor("000000");
		tracotitleRun.setBold(negrito);
		tracotitleRun.setFontFamily("Arial");
		tracotitleRun.setFontSize(12);

	}

/*	public void criarTabelaCarregamentos(CadastroContrato novo_contrato, ArrayList<CadastroContrato.Carregamento> carregamentos) {
		//XWPFParagraph par = document_global.createParagraph();

		NumberFormat z = NumberFormat.getNumberInstance();

		Locale ptBr = new Locale("pt", "BR");

		// criarParagrafo(1);
		// linhas x colunas
		
		int num_linhas_carregamentos = carregamentos.size() + 1 + 1 + 1 + 2;
		double soma_total_carga_real = 0;
		double soma_total_carga_nfa = 0;
		double quantidade_kg = 0;
		if(novo_contrato.getMedida().equalsIgnoreCase("KG")) {
			quantidade_kg =  novo_contrato.getQuantidade();;
		}else if(novo_contrato.getMedida().equalsIgnoreCase("Sacos")) {
			quantidade_kg =  novo_contrato.getQuantidade() * 60;

		}
		
		
		XWPFTable table = document_global.createTable(num_linhas_carregamentos, 16);

		setTableAlign(table, ParagraphAlignment.CENTER);
		XWPFTableRow tableRowOne = table.getRow(0);
		tableRowOne.getCell(0).removeParagraph(0);

		XWPFParagraph paragraph = tableRowOne.getCell(0).addParagraph();

		criarParagrafoTabela(paragraph, "CONTRATO", true);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(1).removeParagraph(0);
		paragraph = tableRowOne.getCell(1).addParagraph();
		criarParagrafoTabela(paragraph, "DATA", true);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(2).removeParagraph(0);
		paragraph = tableRowOne.getCell(2).addParagraph();
		criarParagrafoTabela(paragraph, "CLIENTE", true);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(3).removeParagraph(0);
		paragraph = tableRowOne.getCell(3).addParagraph();
		criarParagrafoTabela(paragraph, "VENDEDOR", true);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(4).removeParagraph(0);
		paragraph = tableRowOne.getCell(4).addParagraph();
		criarParagrafoTabela(paragraph, "TRANSPORTADOR", true);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(5).removeParagraph(0);
		paragraph = tableRowOne.getCell(5).addParagraph();
		criarParagrafoTabela(paragraph, "VEICULO", true);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(6).removeParagraph(0);
		paragraph = tableRowOne.getCell(6).addParagraph();
		criarParagrafoTabela(paragraph, "PRODUTO", true);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(7).removeParagraph(0);
		paragraph = tableRowOne.getCell(7).addParagraph();
		criarParagrafoTabela(paragraph, "ROM", true);

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(8).removeParagraph(0);
		paragraph = tableRowOne.getCell(8).addParagraph();
		criarParagrafoTabela(paragraph, "PESO ROM", true);
		
		
		tableRowOne = table.getRow(0);
		tableRowOne.getCell(9).removeParagraph(0);
		paragraph = tableRowOne.getCell(9).addParagraph();
		criarParagrafoTabela(paragraph, "NF 1", true);
		
		tableRowOne = table.getRow(0);
		tableRowOne.getCell(10).removeParagraph(0);
		paragraph = tableRowOne.getCell(10).addParagraph();
		criarParagrafoTabela(paragraph, "PESO NF 1", true);
		
		tableRowOne = table.getRow(0);
		tableRowOne.getCell(11).removeParagraph(0);
		paragraph = tableRowOne.getCell(11).addParagraph();
		criarParagrafoTabela(paragraph, "VALOR NF 1", true);
		
		tableRowOne = table.getRow(0);
		tableRowOne.getCell(12).removeParagraph(0);
		paragraph = tableRowOne.getCell(12).addParagraph();
		criarParagrafoTabela(paragraph, "NF 2", true);
		
		tableRowOne = table.getRow(0);
		tableRowOne.getCell(13).removeParagraph(0);
		paragraph = tableRowOne.getCell(13).addParagraph();
		criarParagrafoTabela(paragraph, "PESO NF 2", true);
		
		tableRowOne = table.getRow(0);
		tableRowOne.getCell(14).removeParagraph(0);
		paragraph = tableRowOne.getCell(14).addParagraph();
		criarParagrafoTabela(paragraph, "VALOR NF 2", true);
		
		tableRowOne = table.getRow(0);
		tableRowOne.getCell(15).removeParagraph(0);
		paragraph = tableRowOne.getCell(15).addParagraph();
		criarParagrafoTabela(paragraph, "DIFERENÇA", true);
		
		
	

		int i = 1;
		
		double peso_total_nf_interna = 0.0 ;
		double peso_total_romaneios= 0.0 ;
		double peso_total_nf_venda1 = 0.0;
		double peso_total_nf_complemento = 0.0;
		double peso_total_diferenca = 0.0;

		BigDecimal valor_total_nf_venda1 = BigDecimal.ZERO;
		BigDecimal valor_total_nf_complemento = BigDecimal.ZERO;
		
		//fazer checkagens

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
		
			
			if(nome_cliente_quebrado.length > 2) {
			    if(nome_cliente_quebrado[2].length() > 1) {
			    	nome_cliente = nome_cliente_quebrado[0] + " " + nome_cliente_quebrado[2];
			    }else {
			    	if(nome_cliente_quebrado[3].length() > 1) {
			    		nome_cliente = nome_cliente_quebrado[0] + " "+ nome_cliente_quebrado[3];

			    	}else {
			    		nome_cliente = nome_cliente_quebrado[0] + " "+ nome_cliente_quebrado[1];

			    	}
			    }
			}
			
			}catch(Exception v) {
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
		
			
			if(nome_vendedor_quebrado.length > 2) {
			    if(nome_vendedor_quebrado[2].length() > 1) {
			    	nome_vendedor = nome_vendedor_quebrado[0] + " " + nome_vendedor_quebrado[2];
			    }else {
			    	if(nome_vendedor_quebrado[3].length() > 1) {
			    		nome_vendedor = nome_vendedor_quebrado[0] + " "+ nome_vendedor_quebrado[3];

			    	}else {
			    		nome_vendedor = nome_vendedor_quebrado[0] + " "+ nome_vendedor_quebrado[1];

			    	}
			    }
			}
			
			}catch(Exception v) {
				nome_vendedor = nome_vendedor_completo;
			}
			
			String nome_transportador = "";
			String placa_trator = "";
			if(carregamento.getId_transportador() > 0 ) {
			// pegar transportador e veiculo
			CadastroCliente transportador_carregamento = gerenciar_clientes
					.getCliente(carregamento.getId_transportador());
			
			 if(transportador_carregamento != null) {
				 if(transportador_carregamento.getTipo_pessoa() == 0) {
					 nome_transportador = transportador_carregamento.getNome_empresarial();
				 }else {
					 nome_transportador = transportador_carregamento.getNome_fantaia();

				 }
			 }
			 CadastroCliente.Veiculo veiculo_carregamento = null ;
			 if(carregamento.getId_veiculo() > 0) {
			for (CadastroCliente.Veiculo veiculo : transportador_carregamento.getVeiculos()) {
				if (veiculo.getId_veiculo() == carregamento.getId_veiculo()) {
					 veiculo_carregamento = veiculo;
					break;
				}
			}
			
			if(veiculo_carregamento != null) {
				placa_trator = veiculo_carregamento.getPlaca_trator();
			}
			 }

			}
			// pegar o produto
			GerenciarBancoProdutos gerenciar_produtos = new GerenciarBancoProdutos();
			CadastroProduto produto_carregamento = gerenciar_produtos.getProduto(carregamento.getId_produto());

			//codigos
			String codigo_romaneio = "";
			String codigo_nf_venda1 = "", codigo_nf_complemento = "";
			//pesos
			
			double peso_romaneio = 0.0;
			double peso_nf_venda1 = 0.0;
			double peso_nf_interna = 0.0;

			BigDecimal valor_nf_venda1 = BigDecimal.ZERO;
			double peso_nf_complemento = 0.0;
			BigDecimal valor_nf_complemento = BigDecimal.ZERO;

			
			
			
			
			try {
			    if(checkString(carregamento.getCodigo_romaneio())){
			    	//procurar por romaneio
			    	if(checkString(carregamento.getCaminho_romaneio())){
			    		ManipularRomaneios manipular = new ManipularRomaneios("");

			        	CadastroRomaneio romaneio = manipular.filtrar(new File(servidor_unidade + carregamento.getCaminho_romaneio()));
			        	codigo_romaneio = Integer.toString(romaneio.getNumero_romaneio());
			        	peso_romaneio = romaneio.getPeso_liquido();
			        	
			    	}else {
			    		codigo_romaneio = carregamento.getCodigo_romaneio();
			    		peso_romaneio = carregamento.getPeso_romaneio();
			    	}
			    
			    }
				}catch(Exception e) {
					//JOptionPane.showMessageDialog(isto, "Romaneio não Localizado");
					codigo_romaneio = carregamento.getCodigo_romaneio();
		    		peso_romaneio = carregamento.getPeso_romaneio();
				}
				
			
				
				
				//nf venda 1
				try {
			        if(checkString(carregamento.getCodigo_nf_venda1())){
			        	if(carregamento.getCaminho_nf_venda1().length() > 10) {
			        		//procurar por nf venda
				        	ManipularNotasFiscais manipular = new ManipularNotasFiscais("");
				        	CadastroNFe nota_fiscal_venda = manipular.filtrar(new File(servidor_unidade + carregamento.getCodigo_nf_venda1()));
                            codigo_nf_venda1 = nota_fiscal_venda.getNfe();
                            peso_nf_venda1 = Double.parseDouble(nota_fiscal_venda.getQuantidade());
                            try {
                            valor_nf_venda1 = new BigDecimal(nota_fiscal_venda.getValor());
                            }catch(Exception e) {
                            	valor_nf_venda1 = BigDecimal.ZERO;
                            }
				        	
			        	}else {
			        		 codigo_nf_venda1 = carregamento.getCodigo_nf_venda1();
	                            peso_nf_venda1 = carregamento.getPeso_nf_venda1();
	                            valor_nf_venda1 = carregamento.getValor_nf_venda1();
					        	

			        	}
			        
			        	
			        }
					}catch(Exception e) {
						//JOptionPane.showMessageDialog(isto, "Nota Fiscal de venda não Localizado");
						 codigo_nf_venda1 = carregamento.getCodigo_nf_venda1();
                         peso_nf_venda1 = carregamento.getPeso_nf_venda1();
                         valor_nf_venda1 = carregamento.getValor_nf_venda1();

					}
				
				
				
				
						//nf complemento
						try {
					        if(checkString(carregamento.getCodigo_nf_complemento())){
					        	if(carregamento.getCaminho_nf_complemento().length() > 10) {
					        		//procurar por nf remessa
						        	ManipularNotasFiscais manipular = new ManipularNotasFiscais("");
						        	CadastroNFe nota_fiscal_complemento = manipular.filtrar(new File(servidor_unidade + carregamento.getCaminho_nf_complemento()));
						        	 codigo_nf_complemento = nota_fiscal_complemento.getNfe();
			                            peso_nf_complemento= Double.parseDouble(nota_fiscal_complemento.getQuantidade());
			                            try {
			                                valor_nf_complemento = new BigDecimal(nota_fiscal_complemento.getValor());
			                                }catch(Exception e) {
			                                	valor_nf_complemento = BigDecimal.ZERO;
			                                }
			                            
					        	}else {
					        		codigo_nf_complemento = carregamento.getCodigo_nf_complemento();
		                            peso_nf_complemento= carregamento.getPeso_nf_complemento();
		                            valor_nf_complemento = carregamento.getValor_nf_complemento();

					        	}
					        
					        
					        	
					        }
							}catch(Exception e) {
								//JOptionPane.showMessageDialog(isto, "Nota Fiscal de remessa não Localizado");

								codigo_nf_complemento = carregamento.getCodigo_nf_complemento();
	                            peso_nf_complemento= carregamento.getPeso_nf_complemento();
	                            valor_nf_complemento = carregamento.getValor_nf_complemento();

							}
				
				


            String cor = "000000";
            
            if((peso_nf_venda1 + peso_nf_complemento) >= peso_romaneio) {
            	//ok
            	cor = "FFFFFF";
            }else if((peso_nf_venda1 + peso_nf_complemento) < peso_romaneio) {
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
			criarParagrafoTabela(paragraph,
					nome_transportador, false);

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
			if(carregamento.getNf_venda1_aplicavel() == 1)
			criarParagrafoTabela(paragraph, codigo_nf_venda1, false);
			else
				criarParagrafoTabela(paragraph, "Não Aplicável", false);

				
			tableRowOne = table.getRow(i);
			tableRowOne.getCell(10).removeParagraph(0);
			paragraph = tableRowOne.getCell(10).addParagraph();
			if(carregamento.getNf_venda1_aplicavel() == 1) {
			criarParagrafoTabela(paragraph, z.format(peso_nf_venda1) + " Kgs", false);
			tableRowOne.getCell(10).getCTTc().addNewTcPr().addNewShd().setFill(cor);
			}else {
				criarParagrafoTabela(paragraph, "Não Aplicável", false);
			}

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(11).removeParagraph(0);
			paragraph = tableRowOne.getCell(11).addParagraph();
			if(carregamento.getNf_venda1_aplicavel() == 1) {
			criarParagrafoTabela(paragraph, NumberFormat.getCurrencyInstance(ptBr)
					.format(valor_nf_venda1), false);
			}else {
				criarParagrafoTabela(paragraph, "Não Aplicável", false);	
			}
			
			tableRowOne = table.getRow(i);
			tableRowOne.getCell(12).removeParagraph(0);
			paragraph = tableRowOne.getCell(12).addParagraph();
			if(carregamento.getNf_complemento_aplicavel() ==  1)
			criarParagrafoTabela(paragraph, codigo_nf_complemento, false);
			else
				criarParagrafoTabela(paragraph, "Não Aplicável", false);

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(13).removeParagraph(0);
			paragraph = tableRowOne.getCell(13).addParagraph();
			if(carregamento.getNf_complemento_aplicavel() == 1) {
			criarParagrafoTabela(paragraph, z.format(peso_nf_complemento) + " Kgs", false);
			tableRowOne.getCell(13).getCTTc().addNewTcPr().addNewShd().setFill(cor);
			}else {
				criarParagrafoTabela(paragraph, "Não Aplicável", false);

			}
			tableRowOne = table.getRow(i);
			tableRowOne.getCell(14).removeParagraph(0);
			paragraph = tableRowOne.getCell(14).addParagraph();
			if(carregamento.getNf_complemento_aplicavel() == 1)
			criarParagrafoTabela(paragraph, NumberFormat.getCurrencyInstance(ptBr)
					.format(valor_nf_complemento), false);
			else {
				criarParagrafoTabela(paragraph,"Não Aplicável", false);
			}
			

			tableRowOne = table.getRow(i);
			tableRowOne.getCell(15).removeParagraph(0);
			paragraph = tableRowOne.getCell(15).addParagraph();
			if(carregamento.getNf_venda1_aplicavel() == 1 && carregamento.getNf_complemento_aplicavel() == 1) {
			criarParagrafoTabela(paragraph,z.format(peso_romaneio - (peso_nf_complemento + peso_nf_venda1)  ) + " Kgs", false);
			tableRowOne.getCell(15).getCTTc().addNewTcPr().addNewShd().setFill(cor);
			}else {
				criarParagrafoTabela(paragraph,"Não Aplicável", false);

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
		//informacoes de total na tabela
		//somatoria dos romaneios
		
		//peso total romaneios
		tableRowOne = table.getRow(i);
		tableRowOne.getCell(8).removeParagraph(0);
		paragraph = tableRowOne.getCell(8).addParagraph();
		criarParagrafoTabela(paragraph, z.format(peso_total_romaneios) + " Kgs", true);
		
		//peso total nf venda 1
		tableRowOne = table.getRow(i);
		tableRowOne.getCell(10).removeParagraph(0);
		paragraph = tableRowOne.getCell(10).addParagraph();
		criarParagrafoTabela(paragraph, z.format(peso_total_nf_venda1) + " Kgs", true);
		
		//valor total nf venda 1
		tableRowOne = table.getRow(i);
		tableRowOne.getCell(11).removeParagraph(0);
		paragraph = tableRowOne.getCell(11).addParagraph();
		criarParagrafoTabela(paragraph,NumberFormat.getCurrencyInstance(ptBr)
				.format(valor_total_nf_venda1) , true);
			
				//peso total nf complemento
				tableRowOne = table.getRow(i);
				tableRowOne.getCell(13).removeParagraph(0);
				paragraph = tableRowOne.getCell(13).addParagraph();
				criarParagrafoTabela(paragraph, z.format(peso_total_nf_complemento) + " Kgs", true);
				
				tableRowOne = table.getRow(i);
				tableRowOne.getCell(14).removeParagraph(0);
				paragraph = tableRowOne.getCell(14).addParagraph();
				criarParagrafoTabela(paragraph,NumberFormat.getCurrencyInstance(ptBr)
						.format(valor_total_nf_complemento) , true);
				
				//diferenca

				tableRowOne = table.getRow(i);
				tableRowOne.getCell(15).removeParagraph(0);
				paragraph = tableRowOne.getCell(15).addParagraph();
				criarParagrafoTabela(paragraph, z.format(peso_total_diferenca) + " Kgs", true);
				
		
          //informacoes de total
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
						texto = texto + (z.format(peso_total_nf_venda1 + peso_total_nf_complemento)
								+ " Kgs]");

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
						texto = texto + z
								.format(peso_total_romaneios
										- (peso_total_nf_venda1 + peso_total_nf_complemento))
								+ " Kgs]";

					} else {
						texto = texto + " Não Aplicável";

					}
					texto = texto + "\n";

					texto = texto + "*Pesos restantes calculados a partir do peso total já carregado";

					// status baseado no peso total ja carregado

					texto = texto + "\n\n";

					
					texto = texto +	"Status parcial gerado de forma automatica calculados a partir do peso total já carregado";

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
					double diferenca_nf_venda = peso_total_romaneios
							- (peso_total_nf_venda1 + peso_total_nf_complemento);
					if (diferenca_nf_venda == 0) {
						texto_nf_venda = texto_nf_venda + "[Emissão de NF's de Venda Concluído]";
					} else if (diferenca_nf_venda > 0) {
						texto_nf_venda = texto_nf_venda + "Emissão de NF's de Venda Incompleto, [falta emitir "
								+ z.format(diferenca_nf_venda) + " Kgs]";

					} else if (diferenca_nf_venda < 0) {
						texto_nf_venda = texto_nf_venda + "Emissão de NF's Excedido, excedeu "
								+ z.format(diferenca_nf_venda) + " Kgs";

					}
					if (nf_venda_ativo || nf_complemento_ativo)
						texto = texto + (texto_nf_venda);
					else
						texto = texto + "Emissão de NF's de Venda Não Aplicável";

				}

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
					texto = texto + (z.format(peso_total_nf_venda1 + peso_total_nf_complemento)
							+ " Kgs]");

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
					texto = texto + z
							.format(quantidade_kg
									- (peso_total_nf_venda1 + peso_total_nf_complemento))
							+ " Kgs]";

				} else {
					texto = texto + " [Não Aplicável]";

				}
				texto = texto + "\n";

				texto = texto + "*Pesos restantes calculados a partir do peso total";

				// status baseado no peso total ja carregado

				texto = texto + "\n\n";

				
				texto = texto +	"[Status parcial gerado de forma automatica calculados a partir do peso total:]";

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
				double diferenca_nf_venda = quantidade_kg
						- (peso_total_nf_venda1 + peso_total_nf_complemento);
				if (diferenca_nf_venda == 0) {
					texto_nf_venda = texto_nf_venda + "[Emissão de NF's de Venda Concluído]";
				} else if (diferenca_nf_venda > 0) {
					texto_nf_venda = texto_nf_venda + "Emissão de NF's de Venda Incompleto, [falta emitir "
							+ z.format(diferenca_nf_venda) + " Kgs]";

				} else if (diferenca_nf_venda < 0) {
					texto_nf_venda = texto_nf_venda + "Emissão de NF's Excedido, [excedeu "
							+ z.format(diferenca_nf_venda) + " Kgs]";

				}
				if (nf_venda_ativo || nf_complemento_ativo)
					texto = texto + (texto_nf_venda);
				else
					texto = texto + "Emissão de NF's de Venda Não Aplicável";

			

		////////////////////////////////////////////////////////////////////////////////		
		substituirTexto(-1,texto);

		 texto = "";

		texto = "Peso Total Romaneios: [" + z.format(peso_total_romaneios) + " kgs ] | [" + z.format(peso_total_romaneios/60) + " sacos ]\n\n";
		if(nf_venda_ativo || nf_complemento_ativo) {
		texto = texto + "Peso Total NF's Venda 1: [" + z.format(peso_total_nf_venda1) + " kgs ] | [" + z.format(peso_total_nf_venda1/60) + " sacos ]\n";
		texto = texto + "Peso Total NF's Complemento: [" + z.format(peso_total_nf_complemento) + " kgs ] | [" + z.format(peso_total_nf_complemento/60) + " sacos ]\n";
		texto = texto + "Peso Total NF's: [" + z.format(peso_total_nf_complemento + peso_total_nf_venda1)+ " kgs ] | [" + z.format((peso_total_nf_complemento + peso_total_nf_venda1)/60) + " sacos ]\n";
		texto = texto + "Diferença Total: [" + z.format(peso_total_romaneios - (peso_total_nf_complemento + peso_total_nf_venda1)) + " kgs ] | [" + z.format((peso_total_romaneios - (peso_total_nf_complemento + peso_total_nf_venda1))/60) + " sacos ]\n";
		texto = texto + "Valor Total NF's Venda 1: [" +  NumberFormat.getCurrencyInstance(ptBr)
		.format(valor_total_nf_venda1) + "]\n";
		texto = texto + "Valor Total NF's Complemento: [" +  NumberFormat.getCurrencyInstance(ptBr)
		.format(valor_total_nf_complemento) + "]\n";
		texto = texto + "Valor Total NF's: [" +  NumberFormat.getCurrencyInstance(ptBr)
		.format(valor_total_nf_complemento.add(valor_total_nf_venda1)) + "]\n";
		}else {
			texto = texto + "NF's Venda Não Aplicável";
		}
		
		substituirTexto(1,texto);
		
	}*/


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


	public void criarTabelaContrato(ArrayList<CadastroContrato> lista_contratos) {

		NumberFormat z = NumberFormat.getNumberInstance();

		int numero_contratos = 0;
		Locale ptBr = new Locale("pt", "BR");
		// variaveis para soma de contratos de vendedor na geracao do relatorio interno
		double quantidade_total_sacos_vendedor = 0;
		double soma_total_valores_vendedor = 0;

		int num_total_linhas = lista_contratos.size();

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

				if (local.getSub_contrato() == 1) {
					// seta a cor vermelha
					cor_dados = "ff0000";
				}

				if (tipo_contrato == 1) {
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
					if (local.getSub_contrato() == 0)
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

				if (local.getSub_contrato() == 0) {
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

				XWPFTableCell cellRow1 = table.getRow(i).getCell(0);

				cellRow1.getCTTc().addNewTcPr();
				cellRow1.getCTTc().getTcPr().addNewGridSpan();
				cellRow1.getCTTc().getTcPr().getGridSpan().setVal(BigInteger.valueOf((long) num_colunas));

				String texto = "";
				String valorTotalString = NumberFormat.getCurrencyInstance(ptBr).format(local.getValor_a_pagar());

				texto = texto + "Total(contrato): " + valorTotalString;
				tableRowOne = table.getRow(i);
				tableRowOne.getCell(0).removeParagraph(0);

				paragraph = tableRowOne.getCell(0).addParagraph();

				valorTotalString = NumberFormat.getCurrencyInstance(ptBr).format(local.getValor_comissao());
				texto = texto + " Total(sub-contrato): " + valorTotalString;

				valorTotalString = NumberFormat.getCurrencyInstance(ptBr)
						.format(local.getValor_a_pagar().subtract(local.getValor_comissao()));
				texto = texto + " Diferença: " + valorTotalString;

				if (!incluir_comissao) {
					texto = texto + " Ganho Potencial: " + valorTotalString;

				} else {

					if (local.getValor_produto() > 0) {
						valorTotalString = NumberFormat.getCurrencyInstance(ptBr).format(local.getValor_produto());
						texto = texto + " Comissão: " + valorTotalString;

						BigDecimal diferenca = local.getValor_a_pagar().subtract(local.getValor_comissao());
						double valor_total_ganhos_potenciais = diferenca.doubleValue() + local.getValor_produto();
						valorTotalString = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_ganhos_potenciais);

						texto = texto + " Ganho Potencial: " + valorTotalString;
					} else {
						texto = texto + " Ganho Potencial: " + valorTotalString;

					}

				}
				criarParagrafoTabela(paragraph, texto, false, "00008b");

			}
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

	}

	public void criarTabelaPagamentos(ArrayList<CadastroContrato.CadastroPagamentoContratual> pagamentos) {

		NumberFormat z = NumberFormat.getNumberInstance();

		Locale ptBr = new Locale("pt", "BR");

		// criarParagrafo(1);
		// linhas x colunas
		int linha_titulo = 1;
		int linha_somatoria = 1;
		int num_linhas_pagamentos = pagamentos.size() + linha_titulo + linha_somatoria;

		XWPFTable table = document_global.createTable(num_linhas_pagamentos, 6);

		setTableAlign(table, ParagraphAlignment.CENTER);
		XWPFTableRow tableRowOne = table.getRow(0);
		tableRowOne.getCell(0).removeParagraph(0);

		XWPFParagraph paragraph = tableRowOne.getCell(0).addParagraph();

		criarParagrafoTabela(paragraph, "Contrato", false, "000000");

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(1).removeParagraph(0);
		paragraph = tableRowOne.getCell(1).addParagraph();
		criarParagrafoTabela(paragraph, "Data", false, "000000");

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(2).removeParagraph(0);
		paragraph = tableRowOne.getCell(2).addParagraph();
		criarParagrafoTabela(paragraph, "Descrição: ", false, "000000");

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(3).removeParagraph(0);
		paragraph = tableRowOne.getCell(3).addParagraph();
		criarParagrafoTabela(paragraph, "Valor: ", false, "000000");

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(4).removeParagraph(0);
		paragraph = tableRowOne.getCell(4).addParagraph();
		criarParagrafoTabela(paragraph, "Depositante", false, "000000");

		tableRowOne = table.getRow(0);
		tableRowOne.getCell(5).removeParagraph(0);
		paragraph = tableRowOne.getCell(5).addParagraph();
		criarParagrafoTabela(paragraph, "Favorecido", false, "000000");

		int i_global = 1;

		double valor_total = 0;

		for (CadastroContrato.CadastroPagamentoContratual local : pagamentos) {

			// CadastroContrato.CadastroPagamentoContratual local = pagamentos.get(indice);

			GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
			int id = gerenciar.getContratoDonoPagamento(local.getId_pagamento());
			CadastroContrato contrato = gerenciar.getContrato(id);

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
			tableRowOne.getCell(4).removeParagraph(0);
			paragraph = tableRowOne.getCell(4).addParagraph();
			criarParagrafoTabela(paragraph, nome_depositante, false, "000000");
			// celula favorecido

			tableRowOne = table.getRow(i_global);
			tableRowOne.getCell(5).removeParagraph(0);
			paragraph = tableRowOne.getCell(5).addParagraph();
			criarParagrafoTabela(paragraph, nome_favorecido, false, "000000");

			i_global++;

		}

		tableRowOne = table.getRow(i_global);
		tableRowOne.getCell(1).removeParagraph(0);
		paragraph = tableRowOne.getCell(1).addParagraph();
		criarParagrafoTabela(paragraph, "Total: ", false, "000000");

		String valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor_total);
		tableRowOne = table.getRow(i_global);
		tableRowOne.getCell(2).removeParagraph(0);
		paragraph = tableRowOne.getCell(2).addParagraph();
		criarParagrafoTabela(paragraph, valorString, false, "000000");

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
	
}
