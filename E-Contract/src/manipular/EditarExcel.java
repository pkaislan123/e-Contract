package manipular;

import java.awt.Desktop;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;

import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Worksheet;
import com.itextpdf.text.DocumentException;

import cadastros.CadastroCliente;
import cadastros.CadastroContrato;
import cadastros.CadastroLogin;
import cadastros.CadastroContrato.CadastroPagamento;
import cadastros.CadastroModelo;
import gui.TelaVizualizarPdf;
import outros.DadosGlobais;
import outros.GetData;
import tratamento_proprio.Log;
import views_personalizadas.TelaEmEspera;

import com.spire.*;


public class EditarExcel {

	private Workbook workbook = null;
	private FileInputStream file;
	private FileOutputStream salvar;
	private File arquivo_a_ser_salvo;
	private Sheet sheet;
	private CadastroModelo modelo;
	private String path;
	private int ultima_linha = 0;
	private int contador = 0;
	private String[] assinaturas = new String[5];
	private int contador_assinaturas = 0;
	String nome_comprador_arquivo, nome_vendedor_arquivo;
	CadastroContrato novo_contrato;
	private TelaEmEspera telaInformacoes;
	private CadastroCliente compradores [], vendedores[], corretores[];

	CadastroCliente comprador, vendedor;
	Workbook workbook_aberto;
	
	
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
    

	
	public EditarExcel(CadastroModelo modelo, TelaEmEspera background)
	{
		getDadosGlobais();
		this.modelo = modelo;
		this.telaInformacoes = background;
		
	}
	
	public void abrir()
	{
		System.out.println("abrir chamdao");              

		try {
			//file = new FileInputStream(new File(path));


	            //converte o array de bytes em file
			 path = "C:\\temp\\" + modelo.getNome_modelo() + ".xlsx";
	            arquivo_a_ser_salvo = new File( path) ;
	            FileOutputStream criar_arquivo = new FileOutputStream( arquivo_a_ser_salvo);
	            try {
					criar_arquivo.write( modelo.getArquivo() );
		            criar_arquivo.close();

		            file = new FileInputStream(new File(path));
		            
				} catch (IOException e) {
					System.out.println("Erro ao tentar criar o arquivo apartir do arry de bytes");
					e.printStackTrace();
				}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("erro");
			e.printStackTrace();
		}
		 
			

	}

	
	public void atualizarCelula(CellStyle estilo, int linha, int coluna, String texto) {
		System.out.println("Linha: " + linha + "| Coluna: " + coluna);
		Cell celula = sheet.getRow(linha-1).getCell(coluna-1);
		celula.setCellValue(texto);
		if(estilo != null)
		{
			celula.setCellStyle(estilo);
		}
		
	}
	
	public void adicionarCliente(CadastroCliente cliente, int flag_tipo_cliente)
	{
      /*
       * 6 1 Comprador:        
7 1 CNPJ/CPF:            7 6 Insc. Est.:
8 1 Endereço:            8 7 Bairro:
9 1 Município:           9 7 Estado:   
       */
		
	if(contador < 1) {	
		for (int i = 1; i< 10; i++)
		{
			atualizarCelula(formatar(false, 'C', false, true, false, false), ultima_linha-1,i, "");

		}
	}
	else
	{
		for (int i = 1; i< 10; i++)
		{
			atualizarCelula(formatar(false, 'C', true, true, false, false), ultima_linha-1,i, "");

		}
	}
	
		if(flag_tipo_cliente == 1)
			//nome comprador
	    	atualizarCelula(formatar(false, 'C', true, false, true, false), ultima_linha, 1, "Comprador:");
		else if(flag_tipo_cliente == 2)
			//nome vendedor
	    	atualizarCelula(formatar(false, 'C', true, false, true, false), ultima_linha, 1, "Vendedor:");
		else
	    	atualizarCelula(formatar(false, 'C', true, false, true, false), ultima_linha, 1, "Corretor:");

		
		if(cliente.getTipo_pessoa() == 0) //0 e pessoa fisica
		{
			//nome_comprador_arquivo = comprador.getNome().toUpperCase() + " " + comprador.getSobrenome().toUpperCase();
			String nome = cliente.getNome().toUpperCase() + " " + cliente.getSobrenome().toUpperCase();
			atualizarCelula(formatar(false, 'T', true, false, false, false),ultima_linha, 2, nome);
			assinaturas[contador_assinaturas] = nome;
			contador_assinaturas++;
		}
		else
		{
			//nome_comprador_arquivo = comprador.getRazao_social().toUpperCase();
			String nome = cliente.getRazao_social().toUpperCase();
			atualizarCelula(formatar(false, 'T', true, false, false, false), ultima_linha, 2, nome );
			assinaturas[contador_assinaturas] = nome;
			contador_assinaturas++;
			
		}
		
		//definir borda direita coluna 8 para ultima_linha 
		atualizarCelula(formatar(false, 'C', false, false, false, true), ultima_linha,9, "");

		
		ultima_linha++;
		atualizarCelula(formatar(false, 'C', false, false, true, false), ultima_linha, 1, "CNPJ/CPF:");
		
	    if(cliente.getTipo_pessoa() == 0) //0 e pessoa fisica
		{
			atualizarCelula(formatar(false, 'T', false, false, false, false), ultima_linha, 2, cliente.getCpf().toUpperCase());

		}
		else
		{
			atualizarCelula(formatar(false, 'T', false, false, false, false), ultima_linha, 2, cliente.getCnpj().toUpperCase());

		}
	
		
		atualizarCelula(formatar(false, 'C', false, false, false, false), ultima_linha, 6, "Insc. Est.:");
		 //ie comprador
	   
		atualizarCelula(formatar(false, 'T', false, false, false, false), ultima_linha, 7, cliente.getIe().toUpperCase());
		
		
		atualizarCelula(formatar(false, 'C', false, false, false, true), ultima_linha,9, "");

		ultima_linha++;

		atualizarCelula(formatar(false, 'C', false, false, true, false),ultima_linha, 1, "Endereço:");
		//logradouro comprador
	    atualizarCelula(formatar(false, 'T', false, false, false, false),ultima_linha, 2, cliente.getRua().toUpperCase());
	   

		
		
		atualizarCelula(formatar(false, 'C', false, false, false, false), ultima_linha, 6, "Bairro:");
	
		//bairro
		atualizarCelula(formatar(false, 'T', false, false, false, false), ultima_linha, 7, cliente.getBairro().toUpperCase());
		
		atualizarCelula(formatar(false, 'C', false, false, false, true), ultima_linha,9, "");



		ultima_linha++;

        atualizarCelula(formatar(false, 'C', false, true, true, false), ultima_linha, 1, "Município:");
      //cidade
       
		atualizarCelula(formatar(false, 'T', false, false, false, false), ultima_linha, 2, cliente.getCidade().toUpperCase());
		

	
		atualizarCelula(formatar(false, 'C', false, false, false, false), ultima_linha, 6, "Estado:");

		  //estado
		atualizarCelula(formatar(false, 'C', false, false, false, false), ultima_linha, 7, cliente.getUf().toUpperCase());

		atualizarCelula(formatar(false, 'T', false, false, false, true), ultima_linha,9, "");

	
		
		ultima_linha++;
		
	
		for (int i = 1; i< 10; i++)
		{
			atualizarCelula(formatar(false, 'C', true, false, false, false), ultima_linha,i, "");

		}
	
		
		contador++;
		
			
	
		
		
	}
	
	public void adicionarLinhaBranca()
	{
		ultima_linha++;

	}
	
	public void adicionarTermosProdutos()
	{
		telaInformacoes.setMsg("Adicionando Termos do Protudo");
		if(contador < 1) {	
			for (int i = 1; i< 10; i++)
			{
				atualizarCelula(formatar(false, 'C', false, true, false, false), ultima_linha-1,i, "");

			}
		}
		else
		{
			for (int i = 1; i< 10; i++)
			{
				atualizarCelula(formatar(false, 'C', true, true, false, false), ultima_linha-1,i, "");

			}
		}
		atualizarCelula(formatar(true, 'T', false, false, true, false), ultima_linha,1, "");

			atualizarCelula(formatar(true, 'T', false, false, false, false), ultima_linha,3, "PRODUTO / CLASSIFICAÇÃO/ TIPO EXPORTAÇÃO");
			
				atualizarCelula(formatar(true, 'T', false, false, false, true), ultima_linha,9, "");
             ultima_linha++;
             
             /*
              * //dados do produto e quantidade	
				//produto
				cell2Update = sheet.getRow(16).getCell(1);
				cell2Update.setCellValue(novo_contrato.getProduto().toUpperCase());
				
				//quantidade
				cell2Update = sheet.getRow(17).getCell(5);
				cell2Update.setCellValue(novo_contrato.getQuantidade());
				
				//tipo
				cell2Update = sheet.getRow(17).getCell(6);
				cell2Update.setCellValue(novo_contrato.getMedida().toUpperCase());
				
				//preço por medida
				cell2Update = sheet.getRow(22).getCell(2);
				cell2Update.setCellValue(novo_contrato.getValor_produto());
				
				//preco total
				cell2Update = sheet.getRow(22).getCell(7);
				cell2Update.setCellValue(novo_contrato.getValor_a_pagar().doubleValue());
				CellStyle moeda = cell2Update.getCellStyle();
				
				//safra
				cell2Update = sheet.getRow(17).getCell(1);
				cell2Update.setCellValue(novo_contrato.getSafra().toUpperCase());
              */
             
			 atualizarCelula(formatar(false, 'T', false, false, true, false), ultima_linha,1, "Produto:");
			 atualizarCelula(formatar(true, 'T', false, false, false, false), ultima_linha,2,  novo_contrato.getProduto().toUpperCase());

			
			 atualizarCelula(formatar(true, 'T', false, false, false, true), ultima_linha,9, "");
             ultima_linha++;

			 atualizarCelula(formatar(false, 'T', false, false, true, false), ultima_linha,1, "Safra:");
			 atualizarCelula(formatar(true, 'T', false, false, false, false), ultima_linha,2, novo_contrato.getSafra().toUpperCase());

			 
			 
			 atualizarCelula(formatar(false, 'T', false, false, false, false), ultima_linha,5, "Quantidade:");
			 atualizarCelula(formatar(true, 'D', false, false, false, false), ultima_linha,6, Double.toString(novo_contrato.getQuantidade()));
			 atualizarCelula(formatar(true, 'E', false, false, false, false), ultima_linha,7,novo_contrato.getMedida().toUpperCase());

			 
			 atualizarCelula(formatar(true, 'T', false, false, false, true), ultima_linha,9, "");
             ultima_linha++;
             
			 atualizarCelula(formatar(false, 'T', false, false, true, false), ultima_linha,1, "Umidade Max.:");
			 atualizarCelula(formatar(true, 'T', false, false, false, false), ultima_linha,3, "14%");

			 
			 atualizarCelula(formatar(false, 'T', false, false, false, false), ultima_linha,5, "Impureza:");
			 atualizarCelula(formatar(true, 'T', false, false, false, false), ultima_linha,6, "1%");

			 atualizarCelula(formatar(false, 'T', false, false, false, false), ultima_linha,8, "Quebra:");
			 atualizarCelula(formatar(true, 'T', false, false, false, true), ultima_linha,9, "0%");
             ultima_linha++;
             
             atualizarCelula(formatar(false, 'T', false, false, true, false), ultima_linha,1, "Ardido/Avariado Max.:");
             atualizarCelula(formatar(true, 'T', false, false, false, false), ultima_linha,3, "0%");

			 atualizarCelula(formatar(false, 'T', false, false, false, false), ultima_linha,5, "Frete:");
			 atualizarCelula(formatar(false, 'T', false, false, false, false), ultima_linha,8, "FunRural:");
			 atualizarCelula(formatar(true, 'T', false, false, false, true), ultima_linha,9, "");
             
             
             
             /*
              * ultima_linha 1 Umidade Max.:
ultima_linha 5 Impureza:
ultima_linha 8 Quebra:
ultima_linha++
ultima_linha 1 Ardido/Avariado Max.:
ultima_linha 5 Frete:
ultima_inha 8 FunRural:
              */


             
			 ultima_linha++;
				
				
				for (int i = 1; i< 10; i++)
				{
					atualizarCelula(formatar(false, 'C', true, false, false, false), ultima_linha,i, "");

				}
			
				
				contador++;

			
		
	}
	
	
	public void adicionarValores()
	{
		
		telaInformacoes.setMsg("Adicionando Valores");
		if(contador < 1) {	
			for (int i = 1; i< 10; i++)
			{
				atualizarCelula(formatar(false, 'C', false, true, false, false), ultima_linha-1,i, "");

			}
		}
		else
		{
			for (int i = 1; i< 10; i++)
			{
				atualizarCelula(formatar(false, 'C', true, true, false, false), ultima_linha-1,i, "");

			}
		}
		
		atualizarCelula(formatar(true, 'T', false, false, true, false), ultima_linha,1, "");
/*
		sheet.addMergedRegion(new CellRangeAddress(
				ultima_linha-1, //first row (0-based)
				ultima_linha-1, //last row  (0-based)
    	        3, //first column (0-based)
    	        6  //last column  (0-based)
    	));*/
		

		
		atualizarCelula(formatar(true, 'T', false, false, false, false), ultima_linha,3, "VALORES NEGOCIADOS / PAGAMENTO / RETIRADA:");
		
		atualizarCelula(formatar(true, 'T', false, false, false, true), ultima_linha,9, "");
         ultima_linha++;
		/*
		 * VALORES NEGOCIADOS / PAGAMENTO / RETIRADA:


ultima_linha 1 Valor do Produto:
ultima_linha 6 Valor Total a Pagar:
ultima_linha++
ultima_linha 1 Data Entrega:
ultima_linha ++
ultima_linha 1 Local da Retirada

		 */
 		atualizarCelula(formatar(false, 'T', false, false, true, false), ultima_linha,1, "Valor do Produto:");
 		atualizarCelula(formatar(true, 'T', false, false, false, false), ultima_linha,3, "R$ " + Double.toString(novo_contrato.getValor_produto()).replace(".", ",") );

 		
 		atualizarCelula(formatar(false, 'T', false, false, false, false), ultima_linha,6, "Valor Total a Pagar:");
 		atualizarCelula(formatar(true, 'T', false, false, false, false), ultima_linha,7, "R$ " + novo_contrato.getValor_a_pagar().toString().replace(".", ","));

		atualizarCelula(formatar(true, 'T', false, false, false, true), ultima_linha,9, "");
		ultima_linha++;
		
 		atualizarCelula(formatar(false, 'T', false, false, true, false), ultima_linha,1, "Data Entrega");
 		atualizarCelula(formatar(true, 'T', false, false, false, false), ultima_linha,3, novo_contrato.getData_entrega());

 		atualizarCelula(formatar(true, 'T', false, false, false, true), ultima_linha,9, "");
		ultima_linha++;
 		
 		atualizarCelula(formatar(false, 'T', false, false, true, false), ultima_linha,1, "Local Retirada");
 		atualizarCelula(formatar(false, 'T', false, false, false, false), ultima_linha,3, novo_contrato.getLocal_retirada().toUpperCase());
 		atualizarCelula(formatar(true, 'T', false, false, false, true), ultima_linha,9, "");

 		
 		
         
         
         ultima_linha++;
     	
			
			for (int i = 1; i< 10; i++)
			{
				atualizarCelula(formatar(false, 'C', true, false, false, false), ultima_linha,i, "");

			}
		
			
			contador++;

	}
	
	public void adicionarPagamento(CadastroContrato.CadastroPagamento pagamento)
	{
		telaInformacoes.setMsg("Adicionando Pagamentos");

		if(contador < 1) {	
			for (int i = 1; i< 10; i++)
			{
				atualizarCelula(formatar(false, 'C', false, true, false, false), ultima_linha-1,i, "");

			}
		}
		else
		{
			for (int i = 1; i< 10; i++)
			{
				atualizarCelula(formatar(false, 'C', true, true, false, false), ultima_linha-1,i, "");

			}
		}
		
		atualizarCelula(formatar(true, 'T', false, false, true, false), ultima_linha,1, "");

		sheet.addMergedRegion(new CellRangeAddress(
				ultima_linha-1, //first row (0-based)
				ultima_linha-1, //last row  (0-based)
    	        3, //first column (0-based)
    	        6  //last column  (0-based)
    	));
		atualizarCelula(formatar(true, 'T', false, false, false, false), ultima_linha,4, "DADOS BANCÁRIOS");
		
		atualizarCelula(formatar(true, 'T', false, false, false, true), ultima_linha,9, "");
         ultima_linha++;
		
         atualizarCelula(formatar(false, 'T', false, false, true, false), ultima_linha,1, "Favorecido:");
         atualizarCelula(formatar(true, 'T', false, false, false, false), ultima_linha,2, pagamento.getConta().getNome());

         atualizarCelula(formatar(false, 'T', false, false, false, false), ultima_linha,6, "CPF:");
         atualizarCelula(formatar(true, 'T', false, false, false, false), ultima_linha,7, pagamento.getConta().getCpf_titular());
         atualizarCelula(formatar(true, 'T', false, false, false, true), ultima_linha,9, "");
         ultima_linha++;
		
         atualizarCelula(formatar(false, 'T', false, false, true, false), ultima_linha,1, "Banco:");
         atualizarCelula(formatar(true, 'T', false, false, false, false), ultima_linha,2, pagamento.getConta().getBanco() );
         
         atualizarCelula(formatar(false, 'T', false, false, false, false), ultima_linha,4, "Agência:");
         atualizarCelula(formatar(true, 'T', false, false, false, false), ultima_linha,5, pagamento.getConta().getAgencia());
         
         atualizarCelula(formatar(false, 'T', false, false, false, false), ultima_linha,6, "Conta/Corre.:");
         atualizarCelula(formatar(true, 'T', false, false, false, false), ultima_linha,7, pagamento.getConta().getAgencia());
         atualizarCelula(formatar(true, 'T', false, false, false, true), ultima_linha,9, "");
         ultima_linha++;
         
         
         atualizarCelula(formatar(false, 'T', false, false, true, false), ultima_linha,1, "Data:");
         atualizarCelula(formatar(true, 'T', false, false, false, false), ultima_linha,2, pagamento.getData_pagamento());
         
         atualizarCelula(formatar(false, 'T', false, false, false, false), ultima_linha,4, "Valor:");
         atualizarCelula(formatar(true, 'T', false, false, false, false), ultima_linha,5,"R$ "+ pagamento.getValor().toPlainString().replace(".", ","));
      
         atualizarCelula(formatar(false, 'T', false, false, false, false), ultima_linha,6, "Descrição:");
         atualizarCelula(formatar(true, 'T', false, false, false, true), ultima_linha,9, "");
		
         ultima_linha++;
	
			
			for (int i = 1; i< 10; i++)
			{
				atualizarCelula(formatar(false, 'C', true, false, false, false), ultima_linha,i, "");

			}
		
			
			contador++;

		
	}
	
	
    public void adicionarClausulas(String [] clausulas)
    {
		telaInformacoes.setMsg("Adicionando Clausulas");

    	if(contador < 1) {	
			for (int i = 1; i< 10; i++)
			{
				atualizarCelula(formatar(false, 'C', false, true, false, false), ultima_linha-1,i, "");

			}
		}
		else
		{
			for (int i = 1; i< 10; i++)
			{
				atualizarCelula(formatar(false, 'C', true, true, false, false), ultima_linha-1,i, "");

			}
		}
    	
    	sheet.addMergedRegion(new CellRangeAddress(
				ultima_linha-1, //first row (0-based)
				ultima_linha-1, //last row  (0-based)
    	        3, //first column (0-based)
    	        6  //last column  (0-based)
    	));
		atualizarCelula(formatar(true, 'T', false, false, true, false), ultima_linha,1, "");
		atualizarCelula(formatar(true, 'T', false, false, false, true), ultima_linha,9, "");

		atualizarCelula(formatar(true, 'T', false, false, false, false), ultima_linha,4, "Claúsulas Contratuais");
		ultima_linha++;

    	for(int i = 0; i < clausulas.length; i++)
    	{
    		if(clausulas[i] != null) {
    		atualizarCelula(formatar(true, 'T', false, false, true, false), ultima_linha,1, "");

    		atualizarCelula(formatar(true, 'T', false, false, false, false), ultima_linha,2,i+1 + ". " + clausulas[i]);
    		atualizarCelula(formatar(true, 'T', false, false, false, true), ultima_linha,9, "");
          	 ultima_linha++;

    		}



    	}
    	    		
			
			for (int i = 1; i< 10; i++)
			{
				atualizarCelula(formatar(false, 'C', true, false, false, false), ultima_linha,i, "");

			}
		
			
			contador++;
    	
    }
	
    
    public void adicionarAssinaturas(String [] assinaturas)
    {
    	
		telaInformacoes.setMsg("Adicionando Assinaturas");

    	if(contador < 1) {	
			for (int i = 1; i< 10; i++)
			{
				atualizarCelula(formatar(false, 'C', false, true, false, false), ultima_linha-1,i, "");

			}
		}
		else
		{
			for (int i = 1; i< 10; i++)
			{
				atualizarCelula(formatar(false, 'C', true, true, false, false), ultima_linha-1,i, "");

			}
		}
    	
    	sheet.addMergedRegion(new CellRangeAddress(
				ultima_linha-1, //first row (0-based)
				ultima_linha-1, //last row  (0-based)
    	        3, //first column (0-based)
    	        6  //last column  (0-based)
    	));
		atualizarCelula(formatar(true, 'T', false, false, true, false), ultima_linha,1, "");
		atualizarCelula(formatar(true, 'T', false, false, false, true), ultima_linha,9, "");

		atualizarCelula(formatar(true, 'T', false, false, false, false), ultima_linha,4, "Assinaturas");
		ultima_linha++;
		atualizarCelula(formatar(true, 'T', false, false, true, false), ultima_linha,1, "");
		atualizarCelula(formatar(true, 'T', false, false, false, true), ultima_linha,9, "");
		ultima_linha++;
		atualizarCelula(formatar(true, 'T', false, false, true, false), ultima_linha,1, "");
		atualizarCelula(formatar(true, 'T', false, false, false, true), ultima_linha,9, "");
		ultima_linha++;

		
		
		int i = 0; {
			if(assinaturas[i] != null)
			{
				
               //fecha a borda da celula1
				atualizarCelula(formatar(true, 'T', false, false, true, false), ultima_linha,1, "");
				
				//cria o traço
				for (int j = 2; j<= 4; j++)
				{
					atualizarCelula(formatar(false, 'D', false, true, false, false), ultima_linha-1,j, "");

				}
				
		
				//coloca o nome do assinante
				atualizarCelula(formatar(true, 'E', false, false, false, false), ultima_linha,2, assinaturas[i]);

				
				
				//cria o traço
				for (int j = 6; j<= 8; j++)
				{
					atualizarCelula(formatar(false, 'D', false, true, false, false), ultima_linha-1,j, "");

				}
				
				
				//adiciona o nome do segundo assinante
				atualizarCelula(formatar(true, 'E', false, false, false, false), ultima_linha,6, assinaturas[1]);

				
				//fecha a borda da ultima celular
				atualizarCelula(formatar(true, 'T', false, false, false, true), ultima_linha,9, "");
				
				//vai para a linha de baixo
				ultima_linha++;
				
				//nessa linha coloca as bordas laterias
				atualizarCelula(formatar(true, 'T', false, false, true, false), ultima_linha,1, "");
				atualizarCelula(formatar(true, 'T', false, false, false, true), ultima_linha,9, "");
				
				//salta mais uma linha
				ultima_linha++;
				
				//adiciona bordas laterais
				atualizarCelula(formatar(true, 'T', false, false, true, false), ultima_linha,1, "");
				atualizarCelula(formatar(true, 'T', false, false, false, true), ultima_linha,9, "");
				
				//salta mais uma linha
				ultima_linha++;
				
				if(assinaturas[2] != null) {
					if(assinaturas[3] != null) {
						   //fecha a borda da celula1
						atualizarCelula(formatar(true, 'T', false, false, true, false), ultima_linha,1, "");
						
						//cria o traço
						for (int j = 2; j<= 4; j++)
						{
							atualizarCelula(formatar(false, 'D', false, true, false, false), ultima_linha-1,j, "");

						}
						
					
						//coloca o nome do assinante
						atualizarCelula(formatar(true, 'E', false, false, false, false), ultima_linha,2, assinaturas[2]);

						
						
						//cria o traço
						for (int j = 6; j<= 8; j++)
						{
							atualizarCelula(formatar(false, 'D', false, true, false, false), ultima_linha-1,j, "");

						}
					
						
						//adiciona o nome do segundo assinante
						atualizarCelula(formatar(true, 'E', false, false, false, false), ultima_linha,6, assinaturas[3]);

						
						//fecha a borda da ultima celular
						atualizarCelula(formatar(true, 'T', false, false, false, true), ultima_linha,9, "");
						
						//vai para a linha de baixo
						ultima_linha++;
						
						//nessa linha coloca as bordas laterias
						atualizarCelula(formatar(true, 'T', false, false, true, false), ultima_linha,1, "");
						atualizarCelula(formatar(true, 'T', false, false, false, true), ultima_linha,9, "");
						
						//salta mais uma linha
						ultima_linha++;
						
						//adiciona bordas laterais
						atualizarCelula(formatar(true, 'T', false, false, true, false), ultima_linha,1, "");
						atualizarCelula(formatar(true, 'T', false, false, false, true), ultima_linha,9, "");
						
						//salta mais uma linha
						ultima_linha++;
						
						
						
					}else {
						 //fecha a borda da celula1
						atualizarCelula(formatar(true, 'T', false, false, true, false), ultima_linha,1, "");
						
						//cria o traço
						for (int j = 4; j<= 6; j++)
						{
							atualizarCelula(formatar(false, 'D', false, true, false, false), ultima_linha-1,j, "");

						}
						
						//mesca o campo que tera o nome do assinante
						sheet.addMergedRegion(new CellRangeAddress(
								ultima_linha, //first row (0-based)
								ultima_linha, //last row  (0-based)
				    	        5, //first column (0-based)
				    	        6  //last column  (0-based)
				    	));
						//coloca o nome do assinante
						atualizarCelula(formatar(true, 'E', false, false, false, false), ultima_linha,5, assinaturas[2]);

						//vai para a linha de baixo
						ultima_linha++;
						
						//nessa linha coloca as bordas laterias
						atualizarCelula(formatar(true, 'T', false, false, true, false), ultima_linha,1, "");
						atualizarCelula(formatar(true, 'T', false, false, false, true), ultima_linha,9, "");
						
						//salta mais uma linha
						ultima_linha++;
						
						//adiciona bordas laterais
						atualizarCelula(formatar(true, 'T', false, false, true, false), ultima_linha,1, "");
						atualizarCelula(formatar(true, 'T', false, false, false, true), ultima_linha,9, "");
						
						//salta mais uma linha
						ultima_linha++;
					}
				}else {
				
				}
				

			}
		}
    
			
				
				
			
				
		
		
		

		for (int p = 1; p< 10; p++)
		{
			atualizarCelula(formatar(false, 'C', true, false, false, false), ultima_linha,p, "");

		}
	
		
		contador++;
    }
    
   
    
    
public ByteArrayOutputStream alterar(CadastroContrato novo_contrato)
{
	
	
	
	
	 this.novo_contrato = novo_contrato;
	 
	
	  compradores = novo_contrato.listaCompradores();
	  vendedores = novo_contrato.listaVendedores();
	  corretores = novo_contrato.listaCorretores();

	
	try {
		
		
	    InputStream inputStream = new ByteArrayInputStream(modelo.getArquivo());
	    
		//FileInputStream inputStream = new FileInputStream(new File(path));
		 workbook = WorkbookFactory.create(inputStream);

		 sheet = workbook.getSheetAt(0);
		 
		
		//codigo contrato
		Cell cell2Update = sheet.getRow(3).getCell(2);
		cell2Update.setCellValue(novo_contrato.getCodigo());

		
		//data contrato
		String data_extenso = "";
		
		SimpleDateFormat formato_data = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date data_formatada = formato_data.parse(novo_contrato.getData_contrato());
		    Date data = new Date();
		    Locale local = new Locale("pt", "BR");
		    DateFormat formato = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", local);
		    
		    LocalDate data_local = data_formatada.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		    DayOfWeek dia_da_semana = data_local.getDayOfWeek();
		    
		    //dia da semna
		    data_extenso = dia_da_semana.getDisplayName(TextStyle.FULL, local) + ", " + formato.format(data_formatada);
		
		} catch (ParseException e) {
			System.out.println("Nao foi possivel converter a data");
			e.printStackTrace();
		}
		
		
		 cell2Update = sheet.getRow(3).getCell(5);
		cell2Update.setCellValue(data_extenso);

		//data entrega
		cell2Update = sheet.getRow(23).getCell(2);
		cell2Update.setCellValue(novo_contrato.getData_entrega());
		
		ultima_linha = 6;
		
		
		 for(int i = 0; i < corretores.length; i++)
	     {
	    	 if(corretores[i] != null) {
	    	 adicionarCliente(corretores[i], 0);
			 adicionarLinhaBranca();
	    	 }
	     }
		
	     for(int i = 0; i < compradores.length; i++)
	     {
	    	 if(compradores[i] != null) {
	    	 adicionarCliente(compradores[i], 1);
			 adicionarLinhaBranca();
	    	 }
	     }
	     
	     
		  
	     for(int i = 0; i < vendedores.length; i++)
	     {
	    	 if(vendedores[i] != null) {
	    	 adicionarCliente(vendedores[i], 2);
			 adicionarLinhaBranca();
	    	 }
	     }
		 
	     adicionarTermosProdutos();
		 adicionarLinhaBranca();

	     adicionarValores();
		 adicionarLinhaBranca();


	     for( CadastroPagamento pagamento : novo_contrato.getPagamentos())
	     {
	    	 adicionarPagamento(pagamento);
			 adicionarLinhaBranca();


	     }
	    
	     String clausulas [] = novo_contrato.listaClausulasAdicionais();
	     adicionarClausulas(clausulas);
		 adicionarLinhaBranca();
	     
	     //adicionar assinaturas
		 adicionarAssinaturas(assinaturas);
		
		 
			telaInformacoes.setMsg("Contrato Pronto!");

	/*
				//pagamentos
				int i = 0;
				int ultimo = 30;
			   for(CadastroContrato.CadastroPagamento pagamento: novo_contrato.getPagamentos())
			   {
				   if(i >= 1)
				   {
					   
					   
					    CellStyle style = formatar(workbook, true, 'H');
						//cell2Update.setCellStyle(style);

						//favorecido
						
						  cell2Update = sheet.getRow(ultimo+1).getCell(0);
						   cell2Update.setCellValue("Favorecido:");
							cell2Update.setCellStyle(style);
							
							 cell2Update = sheet.getRow(ultimo+1).getCell(1);
							   cell2Update.setCellValue(pagamento.getConta().getNome().toUpperCase());

							   //cpf
							   cell2Update = sheet.getRow(ultimo+1).getCell(5);
							   cell2Update.setCellValue("CPF:");
								cell2Update.setCellStyle(style);
								
								  cell2Update = sheet.getRow(ultimo+1).getCell(6);
								   cell2Update.setCellValue(pagamento.getConta().getCpf_titular().toUpperCase());	
								   
								//banco   
								   cell2Update = sheet.getRow(ultimo+2).getCell(0);
								   cell2Update.setCellValue("Banco:");
									cell2Update.setCellStyle(style);
									
									   cell2Update = sheet.getRow(ultimo+2).getCell(1);
									   cell2Update.setCellValue(pagamento.getConta().getBanco().toUpperCase());
							
									   
								 //agencia
									   cell2Update = sheet.getRow(ultimo+2).getCell(3);
									   cell2Update.setCellValue("AG:");
										cell2Update.setCellStyle(style);
									   cell2Update = sheet.getRow(ultimo +2).getCell(4);
									   cell2Update.setCellValue(pagamento.getConta().getAgencia().toUpperCase());
									   
								 //conta
									   cell2Update = sheet.getRow(ultimo+2).getCell(5);
									   cell2Update.setCellValue("C/C:");
										cell2Update.setCellStyle(style);
									   cell2Update = sheet.getRow(ultimo+2).getCell(6);
									   cell2Update.setCellValue(pagamento.getConta().getConta().toUpperCase());	   
									
								//data
									   cell2Update = sheet.getRow(ultimo+3).getCell(0);
									   cell2Update.setCellValue("Data:");
										cell2Update.setCellStyle(style);
									   cell2Update = sheet.getRow(ultimo+3).getCell(1);
									   cell2Update.setCellValue(pagamento.getData_pagamento().toUpperCase());	  
									   
								//valor	   
									   cell2Update = sheet.getRow(ultimo+3).getCell(3);
									   cell2Update.setCellValue("Valor:");
										cell2Update.setCellStyle(style);
									   cell2Update = sheet.getRow(ultimo+3).getCell(4);
									   cell2Update.setCellStyle(moeda);

									   cell2Update.setCellValue(pagamento.getValor().doubleValue());

									   ultimo = ultimo + 4;
					   
				   }
				   else {
				   //favorecido
				   cell2Update = sheet.getRow(27).getCell(1);
				   cell2Update.setCellValue(pagamento.getConta().getNome().toUpperCase());
				   
				   //cpf
				   cell2Update = sheet.getRow(27).getCell(6);
				   cell2Update.setCellValue(pagamento.getConta().getCpf_titular().toUpperCase());
				   
				   //banco
				   cell2Update = sheet.getRow(28).getCell(1);
				   cell2Update.setCellValue(pagamento.getConta().getBanco().toUpperCase());
				   
				   //agencia
				   cell2Update = sheet.getRow(28).getCell(4);
				   cell2Update.setCellValue(pagamento.getConta().getAgencia().toUpperCase());
				   
				   //conta
				   
				   cell2Update = sheet.getRow(28).getCell(6);
				   cell2Update.setCellValue(pagamento.getConta().getConta().toUpperCase());
				   
				    //data
					
				   cell2Update = sheet.getRow(29).getCell(1);
				   cell2Update.setCellValue(pagamento.getData_pagamento().toUpperCase());	  
				   
		         	//valor	   
				 			  
				   cell2Update = sheet.getRow(29).getCell(4);
				  
				   cell2Update.setCellValue(pagamento.getValor().doubleValue());
				  
				   //cell2Update.setCellStyle(moeda);
				   }
					
				   i++;
			   }
			   
			   //dados adicionais
			   
			    CellStyle style = formatar(workbook, true, 'H');
			   
			   int x = ultimo + 1;
			   cell2Update = sheet.getRow(x).getCell(0);
				cell2Update.setCellStyle(style);

			   cell2Update.setCellValue("OBS:");

			   
			   cell2Update = sheet.getRow(x).getCell(1);
				cell2Update.setCellStyle(style);
               cell2Update.setCellValue("I. A quantidade de sacos que exceder será negociado com o preço do dia.");
               sheet.addMergedRegion(new CellRangeAddress(
            	        x, //first row (0-based)
            	        x, //last row  (0-based)
            	        1, //first column (0-based)
            	        6  //last column  (0-based)
            	));

			   
			   cell2Update = sheet.getRow(x+1).getCell(1);
				cell2Update.setCellStyle(style);

			   cell2Update.setCellValue("II. " + novo_contrato.getProduto().toUpperCase() + " acima de 14% de umidade será cobrado uma taxa de despesas para a ");

			   cell2Update = sheet.getRow(x+2).getCell(1);
				cell2Update.setCellStyle(style);
           cell2Update.setCellValue("secagem.");

			 
			  
			   //assinaturas
			   cell2Update = sheet.getRow(x+5).getCell(0);
				cell2Update.setCellStyle(style);

			   cell2Update.setCellValue("_____________________________________");

			   cell2Update = sheet.getRow(x+6).getCell(1);
				cell2Update.setCellStyle(style);

			   cell2Update.setCellValue("COMPRADOR");

			   
			   cell2Update = sheet.getRow(x+5).getCell(5);
				cell2Update.setCellStyle(style);

			   cell2Update.setCellValue("_____________________________________");

			   
			   cell2Update = sheet.getRow(x+6).getCell(6);
				cell2Update.setCellStyle(style);

			   cell2Update.setCellValue("VENDEDOR");

*/
			   
			   
			   inputStream.close();
       /* String pasta_padrao = "C:\\Users\\Aislan\\Documents\\" ;
		String nome_arquivo = "CTC " + novo_contrato.getCtc() + " " + nome_comprador_arquivo + " " + "CTV " + novo_contrato.getCtv() + " " + nome_vendedor_arquivo; 
        String extensao_arquivo = ".xlsx";
		String caminho_completo = pasta_padrao + nome_arquivo ;
		
		FileOutputStream outputStream = new FileOutputStream (caminho_completo + ".xlsx");
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();*/
			  
		ByteArrayOutputStream saida_apos_edicao = new ByteArrayOutputStream();
		workbook_aberto = workbook;

		workbook.write(saida_apos_edicao);
		//workbook.close();
		
		return saida_apos_edicao;
		//ConverterPdf conversor = new ConverterPdf();
		//ByteArrayOutputStream convertido_pdf = conversor.excel_pdf("caminho_completo", "nome_arquivo", saida_apos_edicao);
		
		  // TelaVizualizarPdf vizualizar_pdf = new TelaVizualizarPdf("", new ByteArrayInputStream(convertido_pdf.toByteArray()));

	} catch (IOException | EncryptedDocumentException
			| InvalidFormatException ex) {
		ex.printStackTrace();
		return null;
	}
}
	
	
	
	public boolean salvar( ) 
	{
		
		String nome_comprador_arquivo ;
		String nome_vendedor1_arquivo ;
		String nome_vendedor2_arquivo;
		
		if(compradores[0].getTipo_pessoa() == 0)
			nome_comprador_arquivo = compradores[0].getNome_empresarial();
		else
			nome_comprador_arquivo = compradores[0].getNome_fantaia();

		if(vendedores[0].getTipo_pessoa() == 0)
			nome_vendedor1_arquivo = vendedores[0].getNome_empresarial();
		else
			nome_vendedor1_arquivo = vendedores[0].getNome_fantaia();
		
		if(vendedores[1] != null) {
		if(vendedores[1].getTipo_pessoa() == 0)
			nome_vendedor2_arquivo = vendedores[1].getNome_empresarial();
		else
			nome_vendedor2_arquivo = vendedores[1].getNome_fantaia();
		}else
			nome_vendedor2_arquivo = null;
		
		//E:\E-Contract\arquivos\clientes\Daniel Alves de Almeida\contratos\compra\2020\milho
		GetData data = new GetData();
		 String pasta_padrao = configs_globais.getRaiz() + "\\E-contract\\arquivos\\clientes\\" + nome_comprador_arquivo + "\\contratos" + "\\compra\\"  + data.getAnoAtual() + "\\" + novo_contrato.getModelo_safra().getProduto().getNome_produto() + "\\";
		System.out.println("caminho para salvar o arquivo: " + pasta_padrao);	
		String pasta_padrao_salvar = configs_globais.getRaiz() + "\\\\E-contract\\\\arquivos\\\\clientes\\\\" + nome_comprador_arquivo + "\\\\contratos" + "\\\\compra\\\\"  + data.getAnoAtual() + "\\\\" + novo_contrato.getModelo_safra().getProduto().getNome_produto() + "\\\\";

				
	String nome_arquivo = novo_contrato.getCodigo() + " "  + nome_comprador_arquivo + " x " + nome_vendedor1_arquivo ; 
	       
			if(nome_vendedor2_arquivo != null)
				nome_arquivo +=  (" " + nome_vendedor2_arquivo);
			String extensao_arquivo = ".xlsx";
			String caminho_completo = pasta_padrao + nome_arquivo ;
			
			String caminho_completo_salvar = pasta_padrao_salvar + nome_arquivo;
			
			FileOutputStream outputStream;
			try {
				outputStream = new FileOutputStream (caminho_completo + ".xlsx");
				
				//novo_contrato.setCaminho_arquivo(url.toString());
				
				workbook_aberto.write(outputStream);
				   workbook_aberto.close();
	  				outputStream.close();

				
				


 				  

 				
 				//workbook.close();
				//Converter e salvar em pdf
                 //criar pdf
                 ConverterPdf converter_pdf = new ConverterPdf();
               //  String url = converter_pdf.excel_pdf_file(contrato_alterado);
               //TelaVizualizarPdf  vizualizar =  new TelaVizualizarPdf(url);
                 if (converter_pdf.excel_pdf_file(caminho_completo)) {
                	 System.out.println("Arquivo pdf convertido e salvo!");
                	 System.out.println("caminho para salvar: " + caminho_completo_salvar);
                	 novo_contrato.setCaminho_arquivo(caminho_completo_salvar + ".pdf") ;
                 }
                 
           
              
				
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				return false;
			} 
		
	}
	
	
	
	public CellStyle formatar( boolean negrito,  char alinhamento, boolean borda_topo,boolean borda_baixo,boolean borda_esquerda,boolean borda_direita)
	{
		    CellStyle estilo = workbook.createCellStyle();//Create style
		    Font font = workbook.createFont();//Create font
		    if(negrito)
		     font.setBold(true);//Make font bold
		    
		    estilo.setFont(font);//set it to bold
		    
		    if(borda_topo)
		    {
		    	estilo.setBorderTop(BorderStyle.THIN);
		    	estilo.setTopBorderColor(IndexedColors.BLACK.getIndex());
		    }
		    
		    if(borda_baixo)
		    {
		    	estilo.setBorderBottom(BorderStyle.THIN);
		    	estilo.setBottomBorderColor(IndexedColors.BLACK.getIndex());

		    }
		    
		    if(borda_esquerda)
		    {
		    	estilo.setBorderLeft(BorderStyle.THIN);
		    	estilo.setLeftBorderColor(IndexedColors.BLACK.getIndex());

		    }
		    
		    if(borda_direita)
		    {
		    	estilo.setBorderRight(BorderStyle.THIN);
		    	estilo.setRightBorderColor(IndexedColors.BLACK.getIndex());

		    }
		    
		    if(alinhamento == 'C')
		    {
		    	estilo.setAlignment(HorizontalAlignment.CENTER);

		    }
		    else if(alinhamento == 'E')
		    {
		    	estilo.setAlignment(HorizontalAlignment.LEFT);

		    }
		    
		    else if(alinhamento == 'D')
		    {
		    	estilo.setAlignment(HorizontalAlignment.RIGHT);

		    }
		    else
		    {
		    	
		    }
		 
		    
		 //   style.setBorderBottom(BorderStyle.THIN);
		    
		    return estilo;
	}
	

	public void getDadosGlobais() {
		//gerenciador de log
				DadosGlobais dados = DadosGlobais.getInstance();
				 GerenciadorLog = dados.getGerenciadorLog();
				 configs_globais = dados.getConfigs_globais();
				 
				 //usuario logado
				  login = dados.getLogin();
		
	}
	
	
}
