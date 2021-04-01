package main.java.manipular;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel {

	private String path;
	private Workbook workbook = null;
	private FileInputStream file;

	public Excel(String path)
	{
		this.path = path;
		
	}
	
	public void abrir()
	{
		System.out.println("abrir chamdao");              

		try {
			file = new FileInputStream(new File(""));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("erro");
			e.printStackTrace();
		}
		 try {
			workbook = new XSSFWorkbook(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("erro");

			e.printStackTrace();
		}

	
			
			

	}
	
	private void ler() {
		Sheet sheet = workbook.getSheetAt(0);
		 
		Map<Integer, List<String>> data = new HashMap<>();
		int i = 0;
		for (Row row : sheet) {
		    data.put(i, new ArrayList<String>());
            System.out.println("Dados lidos: " + data.toString());

		    for (Cell cell : row) {
		        switch (cell.getCellTypeEnum()) {
		            case STRING: {
		            	data.get(new Integer(i)).add(cell.getRichStringCellValue().getString());
                         System.out.println("Dados lidos: " + data.toString());
		            
		            }
		        	
		            default: data.get(new Integer(i)).add(" ");

		        }
		    }
		    i++;
		}
	}
	
}
