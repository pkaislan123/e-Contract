package main.java.manipular;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import com.aspose.cells.SaveFormat;
import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;
import com.spire.xls.*;

public class ConverterPdf {
	
	/*public void gerarPdf(String caminho_arquivo) {
       Document document = new Document();
       try {

           PdfWriter.getInstance(document, new FileOutputStream(caminho_arquivo));
           document.open();

           // adicionando um parágrafo no documento
           document.add(new Paragraph("Gerando PDF - Java"));
 }
       catch(DocumentException de) {
           System.err.println(de.getMessage());
       }
       catch(IOException ioe) {
           System.err.println(ioe.getMessage());
       }
       document.close();
   }*/
	
	/*
	
	public ByteArrayOutputStream excel_pdf_stream(ByteArrayOutputStream saida_apos_edicao)

	{
		
		
		 InputStream inputStream = new ByteArrayInputStream(saida_apos_edicao.toByteArray());
		 
		 
		 
		  Workbook workbook = new Workbook();
		  workbook.loadFromStream(inputStream);
	        //workbook.loadFromFile(caminho_completo + ".xlsx" );

		  ByteArrayOutputStream saida = new ByteArrayOutputStream();
	        //Fit to page
	        workbook.getConverterSetting().setSheetFitToPage(true);

	        //Save as PDF document
	        workbook.saveToStream(saida, FileFormat.PDF);
	        //workbook.saveToStream(stream);
	        //workbook.saveToStream(saida);
	      //  TelaVizualizarPdf vizualizar_pdf = new TelaVizualizarPdf(caminho_completo + ".pdf");
	        return saida;


	}
*/
	
	
	public String excel_pdf_stream(ByteArrayOutputStream saida_apos_edicao)

	{
		
		
		 InputStream inputStream = new ByteArrayInputStream(saida_apos_edicao.toByteArray());
		 
		 
		 
		 Workbook workbook ;
		 try {
			workbook=  WorkbookFactory.create(inputStream);
			
			FileOutputStream saida_excel = new FileOutputStream(new File("C:\\ProgramData\\E-Contract\\temp_files\\teste.xlsx"));

		    workbook.write(saida_excel);
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		 com.spire.xls.Workbook  workbook1 = new com.spire.xls.Workbook();
		 workbook1.loadFromFile("C:\\ProgramData\\E-Contract\\temp_files\\teste.xlsx");

	        //Get the second worksheet
		 workbook1.getConverterSetting().setSheetFitToPage(true);

	        //Save as PDF document
		 workbook1.saveToFile("C:\\ProgramData\\E-Contract\\temp_files\\teste.pdf",FileFormat.PDF);
	        //Save as PDF document
		
	    System.out.println("success");
		  return "C:\\ProgramData\\E-Contract\\temp_files\\teste.pdf";
	


	}
	
	public String word_pdf_stream(ByteArrayOutputStream saida_apos_edicao)

	{		 InputStream inputStream = new ByteArrayInputStream(saida_apos_edicao.toByteArray());

	
      XWPFDocument document = null;
	try {
		document = new XWPFDocument(inputStream);
	} catch (IOException e2) {
		// TODO Auto-generated catch block
		e2.printStackTrace();
	}

		
		  			 //salva o arquivo
		              
			try {
				
				document.write(new FileOutputStream("C:\\ProgramData\\E-Contract\\temp_files\\teste.docx"));

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}


		    try  {

			   
			    IConverter converter = LocalConverter.builder().build();   


			    //salvar a partir de arquivo
				FileOutputStream saida_arquivo_pdf2 = new FileOutputStream(new File("C:\\ProgramData\\E-Contract\\temp_files\\teste.pdf"));

			    converter.convert(new FileInputStream(new File("C:\\ProgramData\\E-Contract\\temp_files\\teste.docx"))).as(DocumentType.DOCX).to(saida_arquivo_pdf2).as(DocumentType.PDF).execute();

			    
			    //saida.close();
			    System.out.println("success");
				  return "C:\\ProgramData\\E-Contract\\temp_files\\teste.pdf";

		    }
		    catch(Exception e) {
		    	return null;
		    }
				 

	}
	

	
	public boolean excel_pdf_file(String caminho)

	{
		
		
	  try {

		  
		  com.spire.xls.Workbook  workbook1 = new com.spire.xls.Workbook();;
			 workbook1.loadFromFile(caminho + ".xlsx");

		        //Get the second worksheet
			 workbook1.getConverterSetting().setSheetFitToPage(true);

		        //Save as PDF document
			 workbook1.saveToFile(caminho + ".pdf",FileFormat.PDF);
		        //Save as PDF document
			
		    System.out.println("success");
			 
	        return true;
	  }catch(Exception t) {
		  System.out.println("erro ao salvar pdf " + t.getMessage() );
		  return false;
	  }


	}
	
	public String excel_pdf_file2(String caminho)

	{
		
		
	  try {

		  
		  com.spire.xls.Workbook  workbook1 = new com.spire.xls.Workbook();;
			 workbook1.loadFromFile(caminho + ".xls");

		        //Get the second worksheet
			 workbook1.getConverterSetting().setSheetFitToPage(true);

		        //Save as PDF document
			 workbook1.saveToFile(caminho + ".pdf",FileFormat.PDF);
		        //Save as PDF document
			
	
			 
	        return caminho + ".pdf";
	  }catch(Exception t) {
		  System.out.println("erro ao salvar pdf " + t.getMessage() );
		  return null;
	  }


	}
	
	
	public boolean word_pdf_file(String caminho)

	{
		
		
	  try {
		  
		  IConverter converter = LocalConverter.builder().build();   


		    //salvar a partir de arquivo
			FileOutputStream saida_arquivo_pdf2 = new FileOutputStream(new File(caminho + ".pdf"));

		    converter.convert(new FileInputStream(new File(caminho + ".docx"))).as(DocumentType.DOCX).to(saida_arquivo_pdf2).as(DocumentType.PDF).execute();

		    
		    //saida.close();
		    System.out.println("success");
		
		 

	        return true;
	  }catch(Exception t) {
		  System.out.println("erro ao salvar pdf " + t.getMessage() );
		  return false;
	  }


	}
	
	public String word_pdf_file2(String caminho)
	{
		
		
	  try {
		  
		  IConverter converter = LocalConverter.builder().build();   
		  caminho = caminho.replaceAll(".docx", "");

		    //salvar a partir de arquivo
			FileOutputStream saida_arquivo_pdf2 = new FileOutputStream(new File(caminho + ".pdf"));

		    converter.convert(new FileInputStream(new File(caminho + ".docx"))).as(DocumentType.DOCX).to(saida_arquivo_pdf2).as(DocumentType.PDF).execute();

		    
		    //saida.close();
		    System.out.println("success");
		
		 

	        return caminho + ".pdf";
	  }catch(Exception t) {
		  System.out.println("erro ao salvar pdf " + t.getMessage() );
		  return null;
	  }


	}
	
	
	
	
}
