package relatoria;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.JOptionPane;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import cadastros.CadastroCliente;
import cadastros.CadastroContrato;
import cadastros.CadastroContrato.CadastroPagamento;
import cadastros.CadastroContrato.CadastroPagamentoContratual;
import cadastros.CadastroContrato.CadastroTransferenciaPagamentoContratual;
import cadastros.CadastroDocumento;
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
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import manipular.ConfiguracoesGlobais;
import manipular.ManipularNotasFiscais;
import manipular.ManipularRomaneios;
import manipular.ManipularTxt;
import outros.DadosGlobais;
import tratamento_proprio.Log;
import views_personalizadas.TelaEmEspera;

public class RelatorioContratoIndividualExcel {

	private CadastroModelo modelo;
	private String path;

	CadastroContrato contrato_local;
	private TelaEmEspera telaInformacoes;
    private int contador_comprovantes;
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private String servidor_unidade;
	private XWPFDocument document_global;
	private XWPFParagraph paragrafo_atual;
    private int posicao_global;
	private boolean interno, externo, incluir_carregamento, incluir_pagamento, incluir_comissao, incluir_sub_contratos,
			incluir_ganhos_potenciais, incluir_comprovantes_pagamentos, incluir_transferencias, incluir_recebimentos;

	public boolean isIncluir_transferencias() {
		return incluir_transferencias;
	}
	
	

	public boolean isIncluir_recebimentos() {
		return incluir_recebimentos;
	}



	public void setIncluir_recebimentos(boolean incluir_recebimentos) {
		this.incluir_recebimentos = incluir_recebimentos;
	}



	public void setIncluir_transferencias(boolean incluir_transferencias) {
		this.incluir_transferencias = incluir_transferencias;
	}

	public boolean isIncluir_comprovantes_pagamentos() {
		return incluir_comprovantes_pagamentos;
	}

	public void setIncluir_comprovantes_pagamentos(boolean incluir_comprovantes_pagamentos) {
		this.incluir_comprovantes_pagamentos = incluir_comprovantes_pagamentos;
	}

	public boolean isInterno() {
		return interno;
	}

	public void setInterno(boolean interno) {
		this.interno = interno;
	}

	public boolean isExterno() {
		return externo;
	}

	public void setExterno(boolean externo) {
		this.externo = externo;
	}

	public boolean isIncluir_carregamento() {
		return incluir_carregamento;
	}

	public void setIncluir_carregamento(boolean incluir_carregamento) {
		this.incluir_carregamento = incluir_carregamento;
	}

	public boolean isIncluir_pagamento() {
		return incluir_pagamento;
	}

	public void setIncluir_pagamento(boolean incluir_pagamento) {
		this.incluir_pagamento = incluir_pagamento;
	}

	public boolean isIncluir_comissao() {
		return incluir_comissao;
	}

	public void setIncluir_comissao(boolean incluir_comissao) {
		this.incluir_comissao = incluir_comissao;
	}

	public boolean isIncluir_sub_contratos() {
		return incluir_sub_contratos;
	}

	public void setIncluir_sub_contratos(boolean incluir_sub_contratos) {
		this.incluir_sub_contratos = incluir_sub_contratos;
	}

	public boolean isIncluir_ganhos_potenciais() {
		return incluir_ganhos_potenciais;
	}

	public void setIncluir_ganhos_potenciais(boolean incluir_ganhos_potenciais) {
		this.incluir_ganhos_potenciais = incluir_ganhos_potenciais;
	}

	public RelatorioContratoIndividualExcel(CadastroContrato contrato) {
		getDadosGlobais();
		contrato_local = contrato;
		servidor_unidade = configs_globais.getServidorUnidade();

	}
	
	
	public void gerar() {
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		int rownum = 0;
		int cellnum = 0;
		Cell cell;
		Row row;
		
		CadastroCliente compradores [] = contrato_local.getCompradores();
		CadastroCliente vendedores [] = contrato_local.getVendedores();

		String nome_compradores;
		String nome_vendedores;
		if(compradores[0].getTipo_pessoa() == 0) {
			nome_compradores = compradores[0].getNome_empresarial();
		}else {
			nome_compradores = compradores[0].getNome_fantaia();
		}
		
		if(compradores[1] != null) {
		if(compradores[1].getTipo_pessoa() == 0) {
			nome_compradores = nome_compradores + ", " + compradores[1].getNome_empresarial();
		}else {
			nome_compradores = nome_compradores + ", " +  compradores[1].getNome_fantaia();
		}
		}
		
		
		if(vendedores[0].getTipo_pessoa() == 0) {
			nome_vendedores = vendedores[0].getNome_empresarial();
		}else {
			nome_vendedores = vendedores[0].getNome_fantaia();
		}
		
		if(vendedores[1] != null) {
		if(vendedores[1].getTipo_pessoa() == 0) {
			nome_vendedores = nome_vendedores + ", " + vendedores[1].getNome_empresarial();
		}else {
			nome_vendedores = nome_vendedores + ", " +  vendedores[1].getNome_fantaia();
		}
		}
		

		// Configurando estilos de células (Cores, alinhamento, formatação, etc..)
		HSSFDataFormat numberFormat = workbook.createDataFormat();

		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		// headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		//celula para texto alinhado ao centro
		CellStyle textStyle = workbook.createCellStyle();
	     textStyle.setAlignment(HorizontalAlignment.CENTER);
		textStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		//celula para numero alinhado ao centro
		CellStyle numberStyle = workbook.createCellStyle();
		numberStyle.setDataFormat(numberFormat.getFormat("#,##0.00"));
		numberStyle.setAlignment(HorizontalAlignment.CENTER);
		numberStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		//estilo para celula do tipo numero alinhado ao centro
		CellStyle valorStyle = workbook.createCellStyle();
		valorStyle.setDataFormat(numberFormat.getFormat("R$ #,##0.00"));
		valorStyle.setAlignment(HorizontalAlignment.CENTER);
		valorStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		
		//estilo para cabecalho fundo laranja
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
	     newFont.setFontHeight((short)(11*20));

		celula_fundo_laranja.setFont(newFont);
		
		//celula fundo branco em negritoasd
		CellStyle negrito = workbook.createCellStyle();
		negrito.setDataFormat(numberFormat.getFormat("R$ #,##0.00"));
		negrito.setAlignment(HorizontalAlignment.CENTER);
		negrito.setVerticalAlignment(VerticalAlignment.CENTER);
		
		HSSFFont newFontNegrito = workbook.createFont();
		newFontNegrito.setBold(true);
		newFontNegrito.setColor(IndexedColors.BLACK.getIndex());
		newFontNegrito.setFontName("Arial");
		newFontNegrito.setItalic(false);
		newFontNegrito.setFontHeight((short)(11*18));

		negrito.setFont(newFontNegrito);
		
		//celula fundo branco em vermelho
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
				newFontVermelha.setFontHeight((short)(11*20));

				aviso.setFont(newFontVermelha);
				
		//celula_number_amarelo_texto_preto
		//estilo para cabecalho fundo laranja
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
		newFont_blabk.setFontHeight((short)(11*20));

		celula_number_amarelo_texto_preto.setFont(newFont_blabk);

		
		//estilo para cabecalho fundo laranja
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
		newFont_branca.setFontHeight((short)(11*20));

		celula_fundo_laranja_texto_branco.setFont(newFont_branca);

		//estilo para cabecalho fundo verde
		CellStyle celula_fundo_verde_texto_branco = workbook.createCellStyle();
		celula_fundo_verde_texto_branco.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		celula_fundo_verde_texto_branco.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		celula_fundo_verde_texto_branco.setAlignment(HorizontalAlignment.CENTER);
		celula_fundo_verde_texto_branco.setVerticalAlignment(VerticalAlignment.CENTER);
		

		celula_fundo_verde_texto_branco.setFont(newFont_branca);
		
		
		//estilo para cabecalho fundo azul
				CellStyle celula_fundo_azul_texto_branco = workbook.createCellStyle();
				celula_fundo_azul_texto_branco.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				celula_fundo_azul_texto_branco.setFillForegroundColor(IndexedColors.BLUE.getIndex());
				celula_fundo_azul_texto_branco.setAlignment(HorizontalAlignment.CENTER);
				celula_fundo_azul_texto_branco.setVerticalAlignment(VerticalAlignment.CENTER);
				

				celula_fundo_azul_texto_branco.setFont(newFont_branca);
		
		double quantidade_total_kgs_recebido = 0;
		double quantidade_total_kgs_nf_venda = 0;
		double quantidade_kg = 0;
		double quantidade_sacos = 0;
		Locale ptBr = new Locale("pt", "BR");
		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();

		if (interno) {
			if (incluir_sub_contratos) {
				ArrayList<CadastroContrato> lista_sub_contratos = new GerenciarBancoContratos()
						.getSubContratos(contrato_local.getId());

				if (lista_sub_contratos.size() > 0) {
					
					HSSFSheet sheet5 = workbook.createSheet("Dados Iniciais");

					// Definindo alguns padroes de layout
					sheet5.setDefaultColumnWidth(15);
					sheet5.setDefaultRowHeight((short) 400);

					

					
					// Configurando as informacoes
					row = sheet5.createRow(rownum++);
					cell = row.createCell(cellnum);
					cell.setCellStyle(celula_fundo_azul_texto_branco);
					cell.setCellValue("Contrato Original");
					sheet5.addMergedRegion(new CellRangeAddress(0, 0, 0,5));
					
					rownum+=1;
					cellnum = 0;
					
					row = sheet5.createRow(rownum++);
					cell = row.createCell(cellnum);
					cell.setCellStyle(celula_fundo_laranja);
					cell.setCellValue(nome_compradores.toUpperCase() + " X " + nome_vendedores + " CTR " + contrato_local.getCodigo() + " IE.: " + vendedores[0].getIe() );
					sheet5.addMergedRegion(new CellRangeAddress(2, 2, cellnum,5));

					//linha quantidade, safra, sacos, etc
					row = sheet5.createRow(rownum++);
					cellnum = 0;
					
					cell = row.createCell(cellnum++);
					cell.setCellStyle(celula_fundo_laranja);
					
					
					
					if(contrato_local.getMedida().equalsIgnoreCase("KG")) {
						quantidade_kg = contrato_local.getQuantidade();
						quantidade_sacos = quantidade_kg / 60;
					}else if(contrato_local.getMedida().equalsIgnoreCase("Sacos")){
						quantidade_sacos = contrato_local.getQuantidade();
						quantidade_kg = quantidade_sacos * 60;
					}
					
					cell.setCellValue("Quantidade: " + quantidade_kg + " KGS | " + quantidade_sacos + " Sacos no valor de " +
							 NumberFormat.getCurrencyInstance(ptBr)
					.format(contrato_local.getValor_produto())+ " por " + contrato_local.getMedida() + " no valor total de: "
						+  NumberFormat.getCurrencyInstance(ptBr)
						.format(contrato_local.getValor_a_pagar() )
							);
					//cell.setCellValue("Quantidade: " + quantidade_kg + " KGS | " + quantidade_sacos + " Sacos" );
					sheet5.addMergedRegion(new CellRangeAddress(3, 3, 0,5));

					row = sheet5.createRow(rownum++);
					cellnum = 0;
					
					cell = row.createCell(cellnum++);
					cell.setCellStyle(celula_fundo_laranja);
					cell.setCellValue(contrato_local.getModelo_safra().getProduto().getNome_produto() + " " + contrato_local.getModelo_safra().getProduto().getTransgenia() + " " + contrato_local.getModelo_safra().getAno_plantio() + "/" + contrato_local.getModelo_safra().getAno_colheita());
					sheet5.addMergedRegion(new CellRangeAddress(4, 4, 0,5));

					rownum+=1;
					cellnum = 0;
					// Configurando as informacoes
					row = sheet5.createRow(rownum++);
					cell = row.createCell(cellnum);
					cell.setCellStyle(celula_fundo_azul_texto_branco);
					cell.setCellValue("Sub-Contratos");
					sheet5.addMergedRegion(new CellRangeAddress(rownum-1,rownum-1, 0,5));
					
					rownum+=1;

					for(CadastroContrato sub : lista_sub_contratos) {
						cellnum = 0;


						CadastroCliente compradores_sub [] = sub.getCompradores();
						CadastroCliente vendedores_sub [] = sub.getVendedores();

						String nome_compradores_sub;
						String nome_vendedores_sub;
						if(compradores_sub[0].getTipo_pessoa() == 0) {
							nome_compradores_sub = compradores_sub[0].getNome_empresarial();
						}else {
							nome_compradores_sub = compradores_sub[0].getNome_fantaia();
						}
						
						if(compradores_sub[1] != null) {
						if(compradores[1].getTipo_pessoa() == 0) {
							nome_compradores_sub = nome_compradores_sub + ", " + compradores_sub[1].getNome_empresarial();
						}else {
							nome_compradores_sub = nome_compradores_sub + ", " +  compradores_sub[1].getNome_fantaia();
						}
						}
						
						
						if(vendedores_sub[0].getTipo_pessoa() == 0) {
							nome_vendedores_sub = vendedores_sub[0].getNome_empresarial();
						}else {
							nome_vendedores_sub = vendedores_sub[0].getNome_fantaia();
						}
						
						if(vendedores_sub[1] != null) {
						if(vendedores_sub[1].getTipo_pessoa() == 0) {
							nome_vendedores_sub = nome_vendedores_sub + ", " + vendedores_sub[1].getNome_empresarial();
						}else {
							nome_vendedores_sub = nome_vendedores_sub + ", " +  vendedores_sub[1].getNome_fantaia();
						}
						}
						
						// Configurando as informacoes
						row = sheet5.createRow(rownum++);

						
						cell = row.createCell(cellnum);
						cell.setCellStyle(textStyle);
						cell.setCellValue(nome_compradores_sub.toUpperCase() + " X " + nome_vendedores_sub + " CTR " + sub.getCodigo() + " IE.: " + vendedores_sub[0].getIe() );
						sheet5.addMergedRegion(new CellRangeAddress(rownum-1, rownum-1, 0,5));

						//linha quantidade, safra, sacos, etc
						row = sheet5.createRow(rownum++);
						cellnum = 0;
						
						cell = row.createCell(cellnum++);
						cell.setCellStyle(textStyle);
						
						
						
						if(sub.getMedida().equalsIgnoreCase("KG")) {
							quantidade_kg = sub.getQuantidade();
							quantidade_sacos = quantidade_kg / 60;
						}else if(sub.getMedida().equalsIgnoreCase("Sacos")){
							quantidade_sacos = sub.getQuantidade();
							quantidade_kg = quantidade_sacos * 60;
						}
						
						cell.setCellValue("Quantidade: " + quantidade_kg + " KGS | " + quantidade_sacos + " Sacos no valor de " +
								 NumberFormat.getCurrencyInstance(ptBr)
						.format(sub.getValor_produto())+ " por " + sub.getMedida() + " no valor total de: "
							+  NumberFormat.getCurrencyInstance(ptBr)
							.format(sub.getValor_a_pagar() )
								);
						//cell.setCellValue("Quantidade: " + quantidade_kg + " KGS | " + quantidade_sacos + " Sacos" );
						sheet5.addMergedRegion(new CellRangeAddress(rownum-1, rownum-1, 0,5));

						row = sheet5.createRow(rownum++);
						cellnum = 0;
						
						cell = row.createCell(cellnum++);
						cell.setCellStyle(textStyle);
						cell.setCellValue(sub.getModelo_safra().getProduto().getNome_produto() + " " + sub.getModelo_safra().getProduto().getTransgenia() + " " + sub.getModelo_safra().getAno_plantio() + "/" + sub.getModelo_safra().getAno_colheita());
						sheet5.addMergedRegion(new CellRangeAddress(rownum-1, rownum-1, 0,5));

						rownum+=1;
						
						
					}
					
					cellnum = 0;

					if (incluir_ganhos_potenciais) {
						
						sheet5.setDefaultColumnWidth(20);
						sheet5.setDefaultRowHeight((short) 400);
						
						// Configurando as informacoes
						row = sheet5.createRow(rownum++);
						cell = row.createCell(cellnum);
						cell.setCellStyle(celula_fundo_azul_texto_branco);
						cell.setCellValue("Ganhos Potenciais");
						sheet5.addMergedRegion(new CellRangeAddress(rownum-1,rownum-1, 0,5));
						
						
						
						//criar tabela de ganhos potenciais
						double valor_total_contrato_original = Double.parseDouble(contrato_local.getValor_a_pagar().toPlainString());

						String string_valor_total_contrato_original = NumberFormat.getCurrencyInstance(ptBr)
								.format(valor_total_contrato_original);

						double valor_total_sub_contratos = 0;

						for (CadastroContrato sub : lista_sub_contratos) {

							double valor_local = Double.parseDouble(sub.getValor_a_pagar().toPlainString());
							valor_total_sub_contratos += valor_local;
						}

						String string_valor_total_sub_contratos = NumberFormat.getCurrencyInstance(ptBr)
								.format(valor_total_sub_contratos);

						double valor_total_diferenca_contratos = valor_total_contrato_original - valor_total_sub_contratos;

						String string_valor_total_diferenca_contratos = NumberFormat.getCurrencyInstance(ptBr)
								.format(valor_total_diferenca_contratos);

						double valor_total_comissoes = 0;

						if (contrato_local.getComissao() == 1) {
							double valor_local = Double.parseDouble(contrato_local.getValor_comissao().toPlainString());
							valor_total_comissoes += valor_local;
						}

						for (CadastroContrato sub : lista_sub_contratos) {

							if (sub.getComissao() == 1) {
								double valor_local = Double.parseDouble(sub.getValor_comissao().toPlainString());
								valor_total_comissoes += valor_local;
							}

						}

						String string_valor_total_comissoes = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_comissoes);

						double valor_total_ganhos_potenciais = valor_total_diferenca_contratos + valor_total_comissoes;

						String string_valor_total_ganhos_potenciais = NumberFormat.getCurrencyInstance(ptBr)
								.format(valor_total_ganhos_potenciais);

						NumberFormat z = NumberFormat.getNumberInstance();
						cellnum = 0;
						row = sheet5.createRow(rownum);
						
						cell = row.createCell(cellnum++);
						cell.setCellStyle(celula_fundo_laranja_texto_branco);
						cell.setCellValue("      VALOR CONTRATOS       ");
						
						
						cell = row.createCell(cellnum++);
						cell.setCellStyle(celula_fundo_laranja_texto_branco);
						cell.setCellValue("      VALOR SUB-CONTRATOS       ");
						
						cell = row.createCell(cellnum++);
						cell.setCellStyle(celula_fundo_laranja_texto_branco);
						cell.setCellValue("      VALOR DIFERENÇA      ");
						
						cell = row.createCell(cellnum++);
						cell.setCellStyle(celula_fundo_laranja_texto_branco);
						cell.setCellValue("     VALOR COMISSÕES      ");
						
						cell = row.createCell(cellnum++);
						cell.setCellStyle(celula_fundo_laranja_texto_branco);
						cell.setCellValue("    POTENCIAIS GANHOS     ");

	                    row = sheet5.createRow(rownum+=1);
	                	cellnum = 0;
						cell = row.createCell(cellnum++);
						cell.setCellStyle(textStyle);
						cell.setCellValue(string_valor_total_contrato_original);
						
						cell = row.createCell(cellnum++);
						cell.setCellStyle(textStyle);
						cell.setCellValue(string_valor_total_sub_contratos);
						
						cell = row.createCell(cellnum++);
						cell.setCellStyle(textStyle);
						cell.setCellValue(string_valor_total_diferenca_contratos);
						
						cell = row.createCell(cellnum++);
						cell.setCellStyle(textStyle);
						cell.setCellValue(string_valor_total_comissoes);
						
						cell = row.createCell(cellnum++);
						cell.setCellStyle(textStyle);
						cell.setCellValue(string_valor_total_ganhos_potenciais);
						
						sheet5.setAutoFilter(CellRangeAddress.valueOf("A" + (rownum) +":E" + (rownum))); 

					}

				}
			}
			
			

		}

		 rownum = 0;
		 cellnum = 0;
		//incluir_recebimentos
		if(incluir_recebimentos) {
		
		HSSFSheet sheet = workbook.createSheet("Recebimentos");

		// Definindo alguns padroes de layout
		sheet.setDefaultColumnWidth(15);
		sheet.setDefaultRowHeight((short) 400);

		

		
		// Configurando as informacoes
		row = sheet.createRow(rownum++);

		
		
		cell = row.createCell(cellnum);
		cell.setCellStyle(celula_fundo_laranja);
		cell.setCellValue(nome_compradores.toUpperCase() + " X " + nome_vendedores + " CTR " + contrato_local.getCodigo() + " IE.: " + vendedores[0].getIe() );
		sheet.addMergedRegion(new CellRangeAddress(0, 0, cellnum,5));

		//linha quantidade, safra, sacos, etc
		row = sheet.createRow(rownum++);
		cellnum = 0;
		
		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja);
		
		
		
		if(contrato_local.getMedida().equalsIgnoreCase("KG")) {
			quantidade_kg = contrato_local.getQuantidade();
			quantidade_sacos = quantidade_kg / 60;
		}else if(contrato_local.getMedida().equalsIgnoreCase("Sacos")){
			quantidade_sacos = contrato_local.getQuantidade();
			quantidade_kg = quantidade_sacos * 60;
		}
		
		cell.setCellValue("Quantidade: " + quantidade_kg + " KGS | " + quantidade_sacos + " Sacos no valor de " +
				 NumberFormat.getCurrencyInstance(ptBr)
		.format(contrato_local.getValor_produto())+ " por " + contrato_local.getMedida() + " no valor total de: "
			+  NumberFormat.getCurrencyInstance(ptBr)
			.format(contrato_local.getValor_a_pagar() )
				);
		//cell.setCellValue("Quantidade: " + quantidade_kg + " KGS | " + quantidade_sacos + " Sacos" );
		sheet.addMergedRegion(new CellRangeAddress(rownum-1, rownum-1, cellnum-1,5));

		
		
		
		row = sheet.createRow(rownum++);
		cellnum = 0;
		
		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja);
		cell.setCellValue(contrato_local.getModelo_safra().getProduto().getNome_produto() + " " + contrato_local.getModelo_safra().getProduto().getTransgenia() + " " + contrato_local.getModelo_safra().getAno_plantio() + "/" + contrato_local.getModelo_safra().getAno_colheita());
		sheet.addMergedRegion(new CellRangeAddress(rownum-1, rownum-1, cellnum-1,5));

		
		
		// Configurando Header
		row = sheet.createRow(rownum++);
		cellnum = 0;
		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("DATA");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("Romaneio".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("Peso Romaneio".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("NF Venda".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("Peso NF Venda".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("NF Remessa".toUpperCase());


		
		ArrayList<CadastroContrato.Recebimento> lista_recebimentos = gerenciar.getRecebimentos(contrato_local.getId());
		
		for (CadastroContrato.Recebimento cadastro : lista_recebimentos) {
			
			
			
			row = sheet.createRow(rownum++);
			cellnum = 0;
			/*
			 * codigo compradores vendedores status quantidade medida produto transgenia
			 * safra valor_produto valor_total data_contrato local_retirada
			 */
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(cadastro.getData_recebimento());

			
		
			
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(cadastro.getCodigo_romaneio());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(numberStyle);
			cell.setCellValue(cadastro.getPeso_romaneio());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(cadastro.getCodigo_nf_venda());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(numberStyle);
			cell.setCellValue(cadastro.getPeso_nf_venda());
			
		
			
			cell = row.createCell(cellnum++);
			cell.setCellStyle(numberStyle);
			cell.setCellValue(cadastro.getCodigo_nf_remessa());
			
			 quantidade_total_kgs_recebido = quantidade_total_kgs_recebido + cadastro.getPeso_romaneio();
			 quantidade_total_kgs_nf_venda = quantidade_total_kgs_nf_venda + cadastro.getPeso_nf_venda();
			

		}
		sheet.setAutoFilter(CellRangeAddress.valueOf("A4:F4")); 
		
		row = sheet.createRow(rownum+=2);
		cellnum = 0;
		
		cell = row.createCell(cellnum++);
		cell.setCellStyle(textStyle);
		cell.setCellValue("");
		
		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("Total Recebido: ");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_number_amarelo_texto_preto);
		cell.setCellValue(quantidade_total_kgs_recebido);
		
		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("Total NF Venda: ");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_number_amarelo_texto_preto);
		cell.setCellValue(quantidade_total_kgs_nf_venda);
		
		row = sheet.createRow(rownum+=2);
		cellnum = 0;
		
		cell = row.createCell(cellnum++);
		cell.setCellStyle(textStyle);
		cell.setCellValue("");
		
		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("Total a Receber: ");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_number_amarelo_texto_preto);
		cell.setCellValue(quantidade_kg - quantidade_total_kgs_recebido);
		
		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_laranja_texto_branco);
		cell.setCellValue("Total a emitir: ");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_number_amarelo_texto_preto);
		cell.setCellValue(quantidade_kg -  quantidade_total_kgs_nf_venda);
		
}
		
		//incluir carregamentos
		if(incluir_carregamento) {
		HSSFSheet sheet2 = workbook.createSheet("Carregamentos");

		// Definindo alguns padroes de layout
		sheet2.setDefaultColumnWidth(15);
		sheet2.setDefaultRowHeight((short) 400);

		 rownum = 0;
		 cellnum = 0;
	
		

		celula_fundo_laranja.setFont(newFont);
	

		celula_fundo_laranja_texto_branco.setFont(newFont_branca);
		
	

		// Configurando as informacoes
		row = sheet2.createRow(rownum++);

		
	
		
		cell = row.createCell(cellnum);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue(nome_compradores.toUpperCase() + " X " + nome_vendedores + " CTR " + contrato_local.getCodigo() + " IE.: " + vendedores[0].getIe() );
		sheet2.addMergedRegion(new CellRangeAddress(0, 0, cellnum,5));

		//linha quantidade, safra, sacos, etc
		row = sheet2.createRow(rownum++);
		cellnum = 0;
		
		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		 quantidade_kg = 0;
		 quantidade_sacos = 0;
		
		if(contrato_local.getMedida().equalsIgnoreCase("KG")) {
			quantidade_kg = contrato_local.getQuantidade();
			quantidade_sacos = quantidade_kg / 60;
		}else if(contrato_local.getMedida().equalsIgnoreCase("Sacos")){
			quantidade_sacos = contrato_local.getQuantidade();
			quantidade_kg = quantidade_sacos * 60;
		}

		cell.setCellValue("Quantidade: " + quantidade_kg + " KGS | " + quantidade_sacos + " Sacos no valor de " +
				 NumberFormat.getCurrencyInstance(ptBr)
		.format(contrato_local.getValor_produto())+ " por " + contrato_local.getMedida() + " no valor total de: "
			+  NumberFormat.getCurrencyInstance(ptBr)
			.format(contrato_local.getValor_a_pagar() )
				);
		sheet2.addMergedRegion(new CellRangeAddress(rownum-1, rownum-1, cellnum-1,5));

		row = sheet2.createRow(rownum++);
		cellnum = 0;
		
		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue(contrato_local.getModelo_safra().getProduto().getNome_produto() + " " + contrato_local.getModelo_safra().getProduto().getTransgenia() + " " + contrato_local.getModelo_safra().getAno_plantio() + "/" + contrato_local.getModelo_safra().getAno_colheita());
		sheet2.addMergedRegion(new CellRangeAddress(rownum-1, rownum-1, cellnum-1,5));

		
		
		// Configurando Header
		row = sheet2.createRow(rownum++);
		cellnum = 0;
		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("DATA");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("ROMANEIO".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("PLACA".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("MOTORISTA".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("PESO".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("CLIENTE".toUpperCase());
		
		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("NF".toUpperCase());
		
		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("PESO NF".toUpperCase());
		
		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("DIFERENÇA".toUpperCase());
		
		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("NF COMPLEMENTO".toUpperCase());
		
		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("PESO NF 2".toUpperCase());

	
		double quantidade_total_kgs_carregados = 0;
		double quantidade_total_kgs_nf_venda1 = 0;
		double quantidade_total_kgs_nf_complemento = 0;
		double quantidade_total_kgs_diferenca = 0;


		ArrayList<CadastroContrato.Carregamento> lista_carregamentos = gerenciar.getCarregamentos(contrato_local.getId());
		for (CadastroContrato.Carregamento carregamento : lista_carregamentos) {
			
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
				}catch(Exception t) {
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
                            }catch(Exception u) {
                            	valor_nf_venda1 = BigDecimal.ZERO;
                            }
				        	
			        	}else {
			        		 codigo_nf_venda1 = carregamento.getCodigo_nf_venda1();
	                            peso_nf_venda1 = carregamento.getPeso_nf_venda1();
	                            valor_nf_venda1 = carregamento.getValor_nf_venda1();
					        	

			        	}
			        
			        	
			        }
					}catch(Exception g) {
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
			                                }catch(Exception l) {
			                                	valor_nf_complemento = BigDecimal.ZERO;
			                                }
			                            
					        	}else {
					        		codigo_nf_complemento = carregamento.getCodigo_nf_complemento();
		                            peso_nf_complemento= carregamento.getPeso_nf_complemento();
		                            valor_nf_complemento = carregamento.getValor_nf_complemento();

					        	}
					        
					        
					        	
					        }
							}catch(Exception y) {
								//JOptionPane.showMessageDialog(isto, "Nota Fiscal de remessa não Localizado");

								codigo_nf_complemento = carregamento.getCodigo_nf_complemento();
	                            peso_nf_complemento= carregamento.getPeso_nf_complemento();
	                            valor_nf_complemento = carregamento.getValor_nf_complemento();

							}
				
				

			
			
			row = sheet2.createRow(rownum++);
			cellnum = 0;
			/*
			 * codigo compradores vendedores status quantidade medida produto transgenia
			 * safra valor_produto valor_total data_contrato local_retirada
			 */
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(carregamento.getData());

			
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(codigo_romaneio);

			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(placa_trator);

			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(nome_transportador);

			cell = row.createCell(cellnum++);
			cell.setCellStyle(numberStyle);
			cell.setCellValue(peso_romaneio);

			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(nome_cliente);
			
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(codigo_nf_venda1);
			
			cell = row.createCell(cellnum++);
			cell.setCellStyle(numberStyle);
			cell.setCellValue(peso_nf_venda1);
			
			cell = row.createCell(cellnum++);
			cell.setCellStyle(numberStyle);
			cell.setCellValue(peso_romaneio - (peso_nf_venda1 + peso_nf_complemento));
			
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(codigo_nf_complemento);
			
			cell = row.createCell(cellnum++);
			cell.setCellStyle(numberStyle);
			cell.setCellValue(peso_nf_complemento);

			 quantidade_total_kgs_carregados += peso_romaneio;
			 quantidade_total_kgs_nf_venda1 += peso_nf_venda1;
			 quantidade_total_kgs_nf_complemento += peso_nf_complemento;
			 quantidade_total_kgs_diferenca += (peso_romaneio - (peso_nf_venda1 + peso_nf_complemento));
		

		}
		sheet2.setAutoFilter(CellRangeAddress.valueOf("A4:K4")); 
		
		row = sheet2.createRow(rownum+=2);
		cellnum = 0;
		
		cell = row.createCell(3);
		cell.setCellStyle(textStyle);
		cell.setCellValue("Totais(Kg's):");
		
		cell = row.createCell(4);
		cell.setCellStyle(numberStyle);
		cell.setCellValue(quantidade_total_kgs_carregados);
		
		
		cell = row.createCell(7);
		cell.setCellStyle(numberStyle);
		cell.setCellValue(quantidade_total_kgs_nf_venda1);
		
		cell = row.createCell(8);
		cell.setCellStyle(numberStyle);
		cell.setCellValue(quantidade_total_kgs_diferenca);
		
		cell = row.createCell(10);
		cell.setCellStyle(numberStyle);
		cell.setCellValue(quantidade_total_kgs_nf_complemento);
		
		row = sheet2.createRow(rownum+=1);
		
		cell = row.createCell(3);
		cell.setCellStyle(textStyle);
		cell.setCellValue("Totais(sacos):");
		
		cell = row.createCell(4);
		cell.setCellStyle(numberStyle);
		cell.setCellValue(quantidade_total_kgs_carregados / 60);
		
		cell = row.createCell(7);
		cell.setCellStyle(numberStyle);
		cell.setCellValue(quantidade_total_kgs_nf_venda1 / 60);
		
		cell = row.createCell(8);
		cell.setCellStyle(numberStyle);
		cell.setCellValue(quantidade_total_kgs_diferenca / 60);
		
		cell = row.createCell(10);
		cell.setCellStyle(numberStyle);
		cell.setCellValue(quantidade_total_kgs_nf_complemento / 60);
		
	}
		//incluir_pagamento
		if(incluir_pagamento) {
		HSSFSheet sheet3 = workbook.createSheet("Pagamentos");

		// Definindo alguns padroes de layout
		sheet3.setDefaultColumnWidth(25);
		sheet3.setDefaultRowHeight((short) 400);

		 rownum = 0;
		 cellnum = 0;
	

		// Configurando estilos de células (Cores, alinhamento, formatação, etc..)
	
		// Configurando as informacoes
		row = sheet3.createRow(rownum++);

		

		
		cell = row.createCell(cellnum);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue(nome_compradores.toUpperCase() + " X " + nome_vendedores + " CTR " + contrato_local.getCodigo() + " IE.: " + vendedores[0].getIe() );
		sheet3.addMergedRegion(new CellRangeAddress(0, 0, cellnum,5));

		//linha quantidade, safra, sacos, etc
		row = sheet3.createRow(rownum++);
		cellnum = 0;
		
		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		
		 quantidade_kg = 0;
		 quantidade_sacos = 0;
		
		if(contrato_local.getMedida().equalsIgnoreCase("KG")) {
			quantidade_kg = contrato_local.getQuantidade();
			quantidade_sacos = quantidade_kg / 60;
		}else if(contrato_local.getMedida().equalsIgnoreCase("Sacos")){
			quantidade_sacos = contrato_local.getQuantidade();
			quantidade_kg = quantidade_sacos * 60;
		}

		cell.setCellValue("Quantidade: " + quantidade_kg + " KGS | " + quantidade_sacos + " Sacos no valor de " +
				 NumberFormat.getCurrencyInstance(ptBr)
		.format(contrato_local.getValor_produto())+ " por " + contrato_local.getMedida() + " no valor total de: "
			+  NumberFormat.getCurrencyInstance(ptBr)
			.format(contrato_local.getValor_a_pagar() )
				);
		sheet3.addMergedRegion(new CellRangeAddress(rownum-1, rownum-1, cellnum-1,5));

		row = sheet3.createRow(rownum++);
		cellnum = 0;
		
		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue(contrato_local.getModelo_safra().getProduto().getNome_produto() + " " + contrato_local.getModelo_safra().getProduto().getTransgenia() + " " + contrato_local.getModelo_safra().getAno_plantio() + "/" + contrato_local.getModelo_safra().getAno_colheita());
		sheet3.addMergedRegion(new CellRangeAddress(rownum-1, rownum-1, cellnum-1,5));

/*
* modelo_pagamentos_contratuais.addColumn("Id Pagamento");
modelo_pagamentos_contratuais.addColumn("Descrição");

modelo_pagamentos_contratuais.addColumn("Data");

modelo_pagamentos_contratuais.addColumn("Valor");
modelo_pagamentos_contratuais.addColumn("Depositante");
modelo_pagamentos_contratuais.addColumn("Conta Depositante");
modelo_pagamentos_contratuais.addColumn("Favorecido");
modelo_pagamentos_contratuais.addColumn("Conta Favorecido");
		
*/
		// Configurando Header
		row = sheet3.createRow(rownum++);
		cellnum = 0;
		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("DATA");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("TIPO".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("VALOR".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("DEPOSITANTE".toUpperCase());

		cell = row.createCell(cellnum++);
		cell.setCellStyle(celula_fundo_verde_texto_branco);
		cell.setCellValue("VAFORECIDO".toUpperCase());

		double soma_total_pagamentos = 0.0;
		// pega os documentos
		GerenciarBancoDocumento gerenciar_docs = new GerenciarBancoDocumento();
		ArrayList<CadastroDocumento> docs = gerenciar_docs.getDocumentos(contrato_local.getId());

		int num_comprovantes = 0;

		for (CadastroDocumento doc : docs) {
			if (doc.getTipo() == 2) {
				// e um pagamento
				num_comprovantes++;
			}
		}

		ArrayList<CadastroPagamentoContratual> lista_pagamentos_contratuais = gerenciar.getPagamentosContratuais(contrato_local.getId());
		
		for (CadastroContrato.CadastroPagamentoContratual pagamento : lista_pagamentos_contratuais) {
			
			if(pagamento.getTipo() != 2 || pagamento.getTipo() == 2  && interno && incluir_comissao) {

			// pegar depositante
			GerenciarBancoClientes gerenciar_clientes = new GerenciarBancoClientes();
			CadastroCliente depositante = gerenciar_clientes.getCliente(pagamento.getId_depositante());
			String nome_depositante;
			if (depositante.getTipo_pessoa() == 0) {
				// pessoa fisica
				nome_depositante = depositante.getNome_empresarial();
			} else {
				nome_depositante = depositante.getNome_fantaia();
			}
			
			String nome_depositante_completo = nome_depositante;

         	String nome_depositante_quebrado[] = nome_depositante.split(" ");
			try {
		
			
			if(nome_depositante_quebrado.length > 3) {
			    if(nome_depositante_quebrado[2].length() > 1) {
			    	nome_depositante = nome_depositante_quebrado[0] + " " + nome_depositante_quebrado[2];
			    }else {
			    	if(nome_depositante_quebrado[3].length() > 1) {
			    		nome_depositante = nome_depositante_quebrado[0] + " "+ nome_depositante_quebrado[3];

			    	}else {
			    		nome_depositante = nome_depositante_quebrado[0] + " "+ nome_depositante_quebrado[1];

			    	}
			    }
			}
			
			}catch(Exception v) {
				nome_depositante = nome_depositante_completo;
			}
			

			// pegar favorecido
			CadastroCliente favorecido = gerenciar_clientes.getCliente(pagamento.getId_favorecido());
			String nome_favorecido;
			if (favorecido.getTipo_pessoa() == 0) {
				// pessoa fisica
				nome_favorecido = favorecido.getNome_empresarial();
			} else {
				nome_favorecido = favorecido.getNome_fantaia();
			}


			String nome_favorecido_completo = nome_favorecido;

         	String nome_favorecido_quebrado[] = nome_favorecido.split(" ");
			try {
		
			
			if(nome_favorecido_quebrado.length > 3) {
			    if(nome_favorecido_quebrado[2].length() > 1) {
			    	nome_favorecido = nome_favorecido_quebrado[0] + " " + nome_favorecido_quebrado[2];
			    }else {
			    	if(nome_favorecido_quebrado[3].length() > 1) {
			    		nome_favorecido = nome_favorecido_quebrado[0] + " "+ nome_favorecido_quebrado[3];

			    	}else {
			    		nome_favorecido = nome_favorecido_quebrado[0] + " "+ nome_favorecido_quebrado[1];

			    	}
			    }
			}
			
			}catch(Exception v) {
				nome_favorecido = nome_favorecido_completo;
			}
			
			
			row = sheet3.createRow(rownum++);
			cellnum = 0;
			/*
			 * codigo compradores vendedores status quantidade medida produto transgenia
			 * safra valor_produto valor_total data_contrato local_retirada
			 */
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(pagamento.getData_pagamento());

			
			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			int tipo = pagamento.getTipo();
			String s_tipo = "";
			if(tipo == 1) {
				s_tipo = "NORMAL";
			}else if(tipo == 2) {
				s_tipo = "COMISSÃO";
			}
			
			cell.setCellValue(s_tipo);

			cell = row.createCell(cellnum++);
			cell.setCellStyle(valorStyle);
			cell.setCellValue(pagamento.getValor_pagamento());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(nome_depositante);

			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(nome_favorecido);

			if(pagamento.getTipo() != 2)
			soma_total_pagamentos += pagamento.getValor_pagamento();
		}

		}
		
		
		ArrayList<CadastroContrato.CadastroTransferenciaPagamentoContratual> transferencias_remetente = null ;
		ArrayList<CadastroContrato.CadastroTransferenciaPagamentoContratual> transferencias_destinatario = null;
		
		if(incluir_transferencias) {
			
			GerenciarBancoTransferencias gerenciar_trans = new GerenciarBancoTransferencias();
			 transferencias_remetente = gerenciar_trans.getTransferenciasRemetente(contrato_local.getId());
			 transferencias_destinatario = gerenciar_trans.getTransferenciaDestinatario(contrato_local.getId());
			
			for (CadastroContrato.CadastroTransferenciaPagamentoContratual local : transferencias_remetente) {
				cellnum = 0;
			
				row = sheet3.createRow(rownum++);
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue(local.getData());

				
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				
				cell.setCellValue("(*)(-)Transferencia");

				String valorString = NumberFormat.getCurrencyInstance(ptBr).format(Double.parseDouble(local.getValor()));

				cell = row.createCell(cellnum++);
				cell.setCellStyle(valorStyle);
				cell.setCellValue(valorString);

				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue("Este contrato " + contrato_local.getCodigo());

				CadastroContrato favorecido = gerenciar.getContrato(local.getId_contrato_destinatario());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue(favorecido.getCodigo());
				soma_total_pagamentos -= Double.parseDouble(local.getValor());

		
			}

			for (CadastroContrato.CadastroTransferenciaPagamentoContratual local : transferencias_destinatario) {

				cellnum = 0;
				
				row = sheet3.createRow(rownum++);
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue(local.getData());

				
				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue("(*)(+)Transferencia");

				String valorString = NumberFormat.getCurrencyInstance(ptBr).format(Double.parseDouble(local.getValor()));

				cell = row.createCell(cellnum++);
				cell.setCellStyle(valorStyle);
				cell.setCellValue(valorString);
				
				CadastroContrato depositante = gerenciar.getContrato(local.getId_contrato_remetente());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue( depositante.getCodigo());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue("Este contrato " + contrato_local.getCodigo());
				
				soma_total_pagamentos += Double.parseDouble(local.getValor());

			}
			
			
			
		}
		
		
		
		sheet3.setAutoFilter(CellRangeAddress.valueOf("A4:E4")); 
		
		row = sheet3.createRow(rownum+=1);
		cellnum = 0;
		
		cell = row.createCell(1);
		
		cell.setCellStyle(textStyle);
		cell.setCellValue("Total:");
		cell = row.createCell(2);
		cell.setCellStyle(negrito);
		cell.setCellValue(soma_total_pagamentos);
		
		rownum+=1;
	
		if(incluir_transferencias && transferencias_remetente!= null || transferencias_destinatario != null) {
		//legendas de transferencias
			rownum+=1;
			cellnum = 0;
			
			if(transferencias_remetente != null) {
				for(CadastroContrato.CadastroTransferenciaPagamentoContratual transfer : transferencias_remetente) {

			String legenda_remetene = incluir_legenda_transferencias_negativas(transfer);
			row = sheet3.createRow(rownum);
			cellnum = 0;
			
			CellStyle alinha_a_esquerada = textStyle;
			alinha_a_esquerada.setAlignment(HorizontalAlignment.LEFT);
			cell = row.createCell(cellnum);
			cell.setCellStyle(alinha_a_esquerada);
			cell.setCellValue(legenda_remetene);
			sheet3.addMergedRegion(new CellRangeAddress(rownum, rownum, 0,10));
			rownum+=1;

				}
			}

			if(transferencias_destinatario != null) {
				for(CadastroContrato.CadastroTransferenciaPagamentoContratual transfer : transferencias_destinatario) {
				String legenda_destinatario = incluir_legenda_transferencias_positivas(transfer);
				row = sheet3.createRow(rownum);
				cellnum = 0;
				CellStyle alinha_a_esquerada = textStyle;
				alinha_a_esquerada.setAlignment(HorizontalAlignment.LEFT);
				cell = row.createCell(cellnum);
				cell.setCellStyle(alinha_a_esquerada);
				cell.setCellValue(legenda_destinatario);
				sheet3.addMergedRegion(new CellRangeAddress(rownum, rownum, 0,10));
				rownum+=1;
				}
			}
			
			
			
		}
		if(incluir_comissao) {
			row = sheet3.createRow(rownum++);
			cellnum = 0;
			cell = row.createCell(1);
			cell.setCellStyle(aviso);
			cell.setCellValue("*Valores de comissão não são somados ao valor total");
			sheet3.addMergedRegion(new CellRangeAddress(rownum, rownum, 0,6));

		}
		rownum+=2;
		int column = 0;
		int contador_comprovantes = 0;
		
		//adicionar comprovantes
		if(incluir_comprovantes_pagamentos) {
		for (CadastroContrato.CadastroPagamentoContratual pag : lista_pagamentos_contratuais) {
			// verifica se há algum documento do tipo 2(comprovante de pagamento) para este
			// id de pagamento

			boolean tem_comprovante = false;
			CadastroDocumento comprovante = new CadastroDocumento();

			for (CadastroDocumento doc : docs) {
				if (doc.getId_pai() == pag.getId_pagamento()) {
					comprovante = doc;
					tem_comprovante = true;
					break;
				}
			}

			if (tem_comprovante) {
				// verifica a extensao do arquivo
				String extensaoDoArquivo = FilenameUtils.getExtension(comprovante.getNome_arquivo());
				if (extensaoDoArquivo.equalsIgnoreCase("png")) {

					String unidade_base_dados = configs_globais.getServidorUnidade();
					String caminho_salvar = unidade_base_dados + "\\" + contrato_local.getCaminho_diretorio_contrato();
					String caminho_completo = caminho_salvar + "\\comprovantes\\" + comprovante.getNome_arquivo();

					 try {
						 
						   if(contador_comprovantes <= 3) {
							   rownum = rownum;
							}else if(contador_comprovantes <= 7) {
								rownum = 13;

							}
							else if(contador_comprovantes <= 11) {
								rownum = 26;

							}
						InputStream inputStream =  new FileInputStream(caminho_completo);
						
						byte[] imageBytes = IOUtils.toByteArray(inputStream);

						   int pictureureIdx = workbook.addPicture(imageBytes, Workbook.PICTURE_TYPE_PNG);

						   inputStream.close();

						   CreationHelper helper = workbook.getCreationHelper();

						   Drawing drawing = sheet3.createDrawingPatriarch();

						   ClientAnchor anchor = helper.createClientAnchor();
						   
						   anchor.setCol1(column);
						   anchor.setCol2(column+3);
						   anchor.setRow1(rownum);
						   anchor.setRow2(rownum+12);

						   drawing.createPicture(anchor, pictureureIdx);


						   column += 3;
						   
						   if(column > 15)
							   column = 0;
						   
						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "Erro ao anexar imagem no xlsx");
						e1.printStackTrace();
					}



				}

			}

		}
	}
}
		
		try {

			new JFXPanel();
			Platform.runLater(() -> {

				// pegar ultima pasta
				ManipularTxt manipular_ultima_pasta = new ManipularTxt();
				String ultima_pasta = manipular_ultima_pasta
						.lerArquivo(new File("C:\\ProgramData\\E-Contract\\configs\\ultima_pasta.txt"));
				
					FileChooser d = new FileChooser();
				
				d.setInitialDirectory(new File(ultima_pasta));
				 d.getExtensionFilters().addAll(
			              
			                new FileChooser.ExtensionFilter("Excel", "*.xls")
			            );
				File file = d.showSaveDialog(new Stage());
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
						
						Runtime.getRuntime()
						.exec("explorer " + file.getAbsolutePath());
			
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



	
	public String incluir_legenda_transferencias_negativas(CadastroContrato.CadastroTransferenciaPagamentoContratual transferencia) {
		String texto = "";

		
		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
			CadastroContrato contrato = gerenciar.getContrato(transferencia.getId_contrato_destinatario());

			CadastroCliente compradores [] = contrato.getCompradores();
		
			String nome_comprador = "";
			if(compradores[0].getTipo_pessoa() == 0)
				nome_comprador = compradores[0].getNome_empresarial().toUpperCase();
			else
				nome_comprador = compradores[0].getNome_fantaia().toUpperCase();
			
			CadastroCliente vendedores [] = contrato.getVendedores();
			
			String nome_vendedor = "";
			if(vendedores[0].getTipo_pessoa() == 0)
				nome_vendedor = vendedores[0].getNome_empresarial().toUpperCase();
			else
				nome_vendedor = vendedores[0].getNome_fantaia().toUpperCase();
			
			texto = "(*) Valor transferido para o contrato " + contrato.getCodigo()
			+ " entre " + nome_comprador + " x " + nome_vendedor
			+ " " + contrato.getQuantidade() + " " + contrato.getMedida() + " de "
			+ contrato.getModelo_produto().getNome_produto() + " da safra " + contrato.getModelo_safra().getAno_plantio() + "/" + contrato.getModelo_safra().getAno_colheita()
			
			;

			texto = texto + "\n";

		
		return texto;

	}
	
public String incluir_legenda_transferencias_positivas(CadastroTransferenciaPagamentoContratual transferencia) {
		
	String texto = "";

		
		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
			CadastroContrato contrato = gerenciar.getContrato(transferencia.getId_contrato_remetente());

			CadastroCliente compradores [] = contrato.getCompradores();
		
			String nome_comprador = "";
			if(compradores[0].getTipo_pessoa() == 0)
				nome_comprador = compradores[0].getNome_empresarial().toUpperCase();
			else
				nome_comprador = compradores[0].getNome_fantaia().toUpperCase();
			
			CadastroCliente vendedores [] = contrato.getVendedores();
			
			String nome_vendedor = "";
			if(vendedores[0].getTipo_pessoa() == 0)
				nome_vendedor = vendedores[0].getNome_empresarial().toUpperCase();
			else
				nome_vendedor = vendedores[0].getNome_fantaia().toUpperCase();
			
			texto = "(*) Valor recebido do contrato " + contrato.getCodigo()
			+ " entre " + nome_comprador + " x " + nome_vendedor
			+ " " + contrato.getQuantidade() + " " + contrato.getMedida() + " de "
			+ contrato.getModelo_produto().getNome_produto() + " da safra " + contrato.getModelo_safra().getAno_plantio() + "/" + contrato.getModelo_safra().getAno_colheita()
			
			;
			
			
		
		return texto;

	}

}
