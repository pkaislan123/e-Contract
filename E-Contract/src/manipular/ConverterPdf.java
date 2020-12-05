package manipular;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.spire.xls.*;

import gui.TelaVizualizarPdf;

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

	public ByteArrayOutputStream excel2pdf(InputStream stream)

	{
		
		
		  Workbook workbook = new Workbook();
		  workbook.loadFromStream(stream);
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

	
	public String excel_pdf_file(ByteArrayOutputStream saida_apos_edicao)

	{
		
		
		 InputStream inputStream = new ByteArrayInputStream(saida_apos_edicao.toByteArray());
		  Workbook workbook = new Workbook();
		  workbook.loadFromStream(inputStream);
	        //workbook.loadFromFile(caminho_completo + ".xlsx" );

		  ByteArrayOutputStream saida = new ByteArrayOutputStream();
	        //Fit to page
	        workbook.getConverterSetting().setSheetFitToPage(true);

	        //Save as PDF document
	        workbook.saveToFile("C:\\temp\\temp.pdf",FileFormat.PDF);
	        //workbook.saveToStream(stream);
	        //workbook.saveToStream(saida);
	      //  TelaVizualizarPdf vizualizar_pdf = new TelaVizualizarPdf(caminho_completo + ".pdf");
	        return "C:\\temp\\temp.pdf";


	}
	
	public boolean excel_pdf_file(String caminho)

	{
		
		
	  try {

			  Workbook workbook = new Workbook();
			  //workbook.loadFromStream(inputStream);
		       workbook.loadFromFile(caminho + ".xlsx");

			  ByteArrayOutputStream saida = new ByteArrayOutputStream();
		        //Fit to page
		        workbook.getConverterSetting().setSheetFitToPage(true);

		        //Save as PDF document
		        workbook.saveToFile(caminho + ".pdf",FileFormat.PDF);
		        //workbook.saveToStream(stream);
		        //workbook.saveToStream(saida);
		      //  TelaVizualizarPdf vizualizar_pdf = new TelaVizualizarPdf(caminho_completo + ".pdf");
		 

	        return true;
	  }catch(Exception t) {
		  System.out.println("erro ao salvar pdf " + t.getMessage() );
		  return false;
	  }


	}
	
	
}
