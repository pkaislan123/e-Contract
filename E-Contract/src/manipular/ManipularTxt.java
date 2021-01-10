package manipular;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static java.nio.file.StandardCopyOption.*;

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
		
		
	public ArrayList<String> getListaNomeArquivos(String caminho_diretorio){
		File diretorio = new File(caminho_diretorio);
		ArrayList<String> lista_nomes = new ArrayList<>();
		
		  File[] arquivos = diretorio.listFiles();

		    for (File arquivo : arquivos) {
		        lista_nomes.add(arquivo.getName());
		    }
		
		
		return lista_nomes;
		
		
	}
	
	public String  copiar(String entrada, String codigo) throws IOException {
		Path yourFile = Paths.get(entrada);

		  String caminho_saida = "C:\\ProgramData\\E-Contract\\temp_files\\" + codigo + ".pdf";

		 Files.copy(yourFile, yourFile.resolveSibling(caminho_saida));
		return caminho_saida;
	}
		
	
	public boolean copiarNFe(String entrada, String saida) throws IOException {
		
		try{
			Path yourFile = Paths.get(entrada);
		

			
		 

		 Files.copy(yourFile, yourFile.resolveSibling(saida));
		   return true;
		}catch(Exception e) {
			return false;
		}
	}
		
  
	   
	   public boolean limparDiretorio(File f) {
		
		   try {
		   
			        if (f.isDirectory()) {
			            File[] files = f.listFiles();
			            for (int i = 0; i < files.length; ++i) {
			            	limparDiretorio (files[i]);
			            }
			        }
			        f.delete();
			    return true;
		   }catch(Exception e) {
			   System.out.println("Não foi possivel realizar a operacao de excluir pastas e subpastas, erro: " + e.getMessage());
			   return false;
		   }
		   
	   }

		
	   public boolean moverArquivo(String caminho, String destino) {
		   File source = new File(caminho);
		    File target = new File(destino);
		    
		    System.out.println("Caminho de origem: " + caminho);
		    System.out.println("Caminho de destino: " + destino);

		    try {

		        FileUtils.moveFile(source, target);
		        
		        System.out.println("Arquivo renomeado!");
		        return true;

		    } catch (IOException e) {
		        e.printStackTrace();
		        System.out.println("erro ao renomear arquivo!");

		        return false;
		    }
		   
		   
	   }
	   
	   public boolean excluir(String caminho) {
		    File target = new File(caminho);
		    
		    System.out.println("Caminho de origem: " + caminho);

		    try {

		    	if(target.exists()) {
		    		
	
		    		return target.delete();
		    	}else {
		    		return true;
		    	}
		    

		    } catch (Exception e) {
		        e.printStackTrace();
		        System.out.println("erro ao excluir arquivo!");

		        return false;
		    }
		   
		   
	   }
	
	
   
}
