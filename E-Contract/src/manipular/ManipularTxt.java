package manipular;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ManipularTxt {

	
	
	
	public ManipularTxt() {
	
	   
		 //Verifica que a pasta existe
		    
		
		
          }
	
	
	public boolean criarDiretorio(String caminho) {
		File diretorio  = new File(caminho);
		if(diretorio.exists()) {
			System.out.println("A pasta ja existe!");
			return true;
		}else {
			if(diretorio.mkdirs()) {
				System.out.println("A pasta não existia, mas foi criada!");

				return true;
			}else{
				System.out.println("A pasta não existia, e não foi criada!");

				return false;
			}
		}
	}
	
	public File criarArquivoLog(String caminho, String nomeArquivo, String extensao) {
		  // Cria arquivo
        File file = new File(caminho + "\\" + nomeArquivo + "." + extensao);
         System.out.println("caminho completo: " + file.toString());
        // Se o arquivo nao existir, ele gera
        if(file.exists()) {
			System.out.println("O arquivo ja existe!");
			return file;
		}else {
			try {
				if(file.createNewFile()) {
					System.out.println("O arquivo não existia, mas foi criado!");

					return file;
				}else{
					System.out.println("O arquivo não existia, e não foi criado!");

					return null;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
        

	}
	
	
	public File abrirArquivo(String path) {
		return new File(path);
	}
	
	public boolean criarArquivo(String caminho, String nomeArquivo, String extensao) {
		  // Cria arquivo
      File file = new File(caminho + "\\" + nomeArquivo + "." + extensao);
       System.out.println("caminho completo: " + file.toString());
      // Se o arquivo nao existir, ele gera
      if(file.exists()) {
			System.out.println("O arquivo ja existe!");
			return true;
		}else {
			try {
				if(file.createNewFile()) {
					System.out.println("O arquivo não existia, mas foi criado!");

					return true;
				}else{
					System.out.println("O arquivo não existia, e não foi criado!");

					return false;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
      

	}
	
	
	
	public boolean escreverArquivo(File file, String mensagem) {
		   // Prepara para escrever no arquivo
        FileWriter fw;
		try {
			fw = new FileWriter(file.getAbsoluteFile(), true);
	        BufferedWriter bw = new BufferedWriter(fw);
	    	bw.append(mensagem);
	    	bw.newLine();
	        bw.close();
	        
	        return true;

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
			return false;
		}
        
	}
	
	public boolean rescreverArquivo(File file, String mensagem) {
		   // Prepara para escrever no arquivo
     FileWriter fw;
		try {
			fw = new FileWriter(file.getAbsoluteFile());
	        BufferedWriter bw = new BufferedWriter(fw);
	    	bw.write(mensagem);
	    	
	        bw.close();
	        
	        return true;

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
			return false;
		}
     
	}
	
	public String lerArquivo(File file) {
		 FileReader ler;
		try {
			ler = new FileReader(file);
			 BufferedReader reader = new BufferedReader(ler);  
			    
			    String linha;
			    String texto = "" ;
			    try {
					while( (linha = reader.readLine()) != null ){
						System.out.println("conteudo da linha: " + linha);
					    texto += linha;

					}
					reader.close();
					System.out.println("Leitura concluida");

				    return texto;


				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("Erro na leitura do arquivo: " + e.getMessage());
					e.printStackTrace();
					return null;
				}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Erro na leitura do arquivo: " + e.getMessage());

			return null;
		}
		   
			
	}
	
	public File criarArquivoRetorno(String caminho, String nomeArquivo, String extensao) {
		  // Cria arquivo
    File file = new File(caminho + "\\" + nomeArquivo + "." + extensao);
     System.out.println("caminho completo: " + file.toString());
    // Se o arquivo nao existir, ele gera
    if(file.exists()) {
			System.out.println("O arquivo ja existe!");
			return file;
		}else {
			try {
				if(file.createNewFile()) {
					System.out.println("O arquivo não existia, mas foi criado!");

					return file;
				}else{
					System.out.println("O arquivo não existia, e não foi criado!");

					return null;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
    

	}
	
	public boolean apagarArquivo(String caminho) {
		System.out.println("Funcao apagar arquivo foi chamada!");
		try {
		File arquivo_apagar = new File(caminho);
		if(arquivo_apagar.exists()) {
			System.out.println("o arquivo existe!");

			
			return arquivo_apagar.delete();
		}
			else {
				System.out.println("o arquivo para apagar nao existe");
				return false;

		}
		}catch(Exception e) {
			System.out.println("houve um erro ao tentar deletar o arquivo, erro: " + e.getMessage());
			return false;
		}
		}
		
		
	
	
   
}
