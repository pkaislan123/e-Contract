package manipular;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import gui.TelaRomaneios;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import cadastros.CadastroAviso;
import cadastros.CadastroCliente;
import cadastros.CadastroLogin;
import cadastros.CadastroNFe;
import cadastros.CadastroProduto;
import cadastros.CadastroRomaneio;
import cadastros.CadastroSafra;
import conexaoBanco.GerenciarBancoClientes;
import conexaoBanco.GerenciarBancoProdutos;
import conexaoBanco.GerenciarBancoSafras;
import gui.TelaMain;
import gui.TelaNotasFiscais;
import outros.DadosGlobais;
import outros.MyFileVisitor;
import outros.TratarDados;
import tratamento_proprio.Log;
import views_personalizadas.TelaNotificacaoSuperior;
import views_personalizadas.TelaNotificacaoSuperiorModoBusca;

public class ManipularRomaneios {
	private JFileChooser fileChooserGlobal;
	private ArrayList<String> listadeArquivos = new ArrayList<>();
    private TelaMain telaPrincipal;
	private ArrayList<CadastroRomaneio> romaneios = new ArrayList<>();
    boolean e_romaneio_global = true;
	private String caminho;
	private int sinal_global;
	private JTable table;
	private int countArquivos;
	private int countPDF;
	private int countNotas;
    private TelaNotificacaoSuperiorModoBusca avisos;
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private ArrayList<CadastroRomaneio> lista_global;

	
	public ManipularRomaneios(String _caminho) {
		getDadosGlobais();
		caminho = _caminho;

	}
	
	public ManipularRomaneios(int i) {
		

	}
	
	public ArrayList<CadastroRomaneio> tratar() {

		
		
		MyFileVisitor arquivos = new MyFileVisitor();
		Path source = Paths.get(caminho);
		try {

			Files.walkFileTree(source, arquivos);
			listadeArquivos = arquivos.getArquivos();

		} catch (IOException ex) {
			//ex.printStackTrace();
		}


		try {
		for (int i = 0; i < listadeArquivos.size(); i++) {
			 System.out.println(listadeArquivos.get(i).toString());
			countArquivos++;
		}

		for (int i = 0; i < listadeArquivos.size(); i++) {
			
			
			String extensaoDoArquivo = FilenameUtils.getExtension(listadeArquivos.get(i).toString());

			if (extensaoDoArquivo.equalsIgnoreCase("pdf")) {
				countPDF++;

				
				File file = new File(listadeArquivos.get(i).toString());
				boolean esta_na_lista = false;
				//verifica se esse arquivo ja nao esta na lista
				if(lista_global != null) {
					if(lista_global.size() > 0) {
						for(CadastroRomaneio elemento_da_lista : lista_global) {
						if(listadeArquivos.get(i).toString().equalsIgnoreCase(elemento_da_lista.getCaminho_arquivo())) {
							esta_na_lista = true;
							break;
						}
							
						}
					}
				}
				if(!esta_na_lista) {
				CadastroRomaneio romaneio = filtrar(file);

				if (romaneio != null) {

					romaneios.add(romaneio);

					countNotas++;
					
				}else {
					if(e_romaneio_global) {
					//JOptionPane.showMessageDialog(null, "erro ao filtrar romaneio: " + file.getAbsolutePath());
					CadastroAviso avisar = new CadastroAviso();
					avisar.setTipo("Erro");
					avisar.setSetor("Importação de Romaneio");
					avisar.setMensagem("erro ao filtrar romaneio: " + file.getAbsolutePath());
                    telaPrincipal.incluir_aviso(avisar);
					}
				}
				}
				// enviar nota para a tela pai
				// System.out.println("enviando nota para a tela pai");

				// System.out.print("Numero de arquivos: " + countArquivos);
				// System.out.print("Numero de PDF's: " + countPDF);

				// System.out.print("Numero de Notas: " + countNotas);

			} else {
				String nomeArquivo = listadeArquivos.get(i).toString();
				// System.out.println("O arquivo " + nomeArquivo + " não é um PDF");

			}
		}
		

		return romaneios;
		
		}catch(Exception e) {
			//JOptionPane.showMessageDialog(null, "erro: " + e.getMessage() + "\nCausa: " + e.getCause());
			return null;
		}
	}
	
	public ArrayList<CadastroRomaneio> tratarMaisRapido() {

		MyFileVisitor arquivos = new MyFileVisitor();
		Path source = Paths.get(caminho);
		try {
			Files.walkFileTree(source, arquivos);
			listadeArquivos = arquivos.getArquivos();

		} catch (IOException ex) {
			//ex.printStackTrace();
		}

		for (int i = 0; i < listadeArquivos.size(); i++) {
			// System.out.println(listadeArquivos.get(i).toString());
			//////JOptionPane.showMessageDialog(null, listadeArquivos.get(i).toString());
			countArquivos++;
		}

		for (int i = 0; i < listadeArquivos.size(); i++) {
			
			
			//////JOptionPane.showMessageDialog(null, "existem arquivos para serem processados");
          if(listadeArquivos.get(i).contains("romaneio")) {

			String extensaoDoArquivo = FilenameUtils.getExtension(listadeArquivos.get(i).toString());

			if (extensaoDoArquivo.equalsIgnoreCase("pdf")) {
				countPDF++;
				
				File file = new File(listadeArquivos.get(i).toString());
				//////JOptionPane.showMessageDialog(null, "filtrando arquivo: " + file.getAbsolutePath());
				boolean esta_na_lista = false;
				//verifica se esse arquivo ja nao esta na lista
				if(lista_global != null) {
					if(lista_global.size() > 0) {
						for(CadastroRomaneio elemento_da_lista : lista_global) {
						if(listadeArquivos.get(i).toString().equalsIgnoreCase(elemento_da_lista.getCaminho_arquivo())) {
							esta_na_lista = true;
							break;
						}
							
						}
					}
				}
				if(!esta_na_lista) {

				CadastroRomaneio romaneio = filtrar(file);

				if (romaneio != null) {

					romaneios.add(romaneio);

					countNotas++;
					
				}else {
					//////JOptionPane.showMessageDialog(null, "erro ao filtrar romaneio: " + file.getAbsolutePath());

				}

				}

			} else {
				String nomeArquivo = listadeArquivos.get(i).toString();
				// System.out.println("O arquivo " + nomeArquivo + " não é um PDF");
				//////JOptionPane.showMessageDialog(null, "O arquivo  : " + nomeArquivo +" não é um PDF" );

			}
	    	}
		}
		

		return romaneios;
	}
	
	
	public void setListaAtual(ArrayList<CadastroRomaneio> _lista_atual) {
		this.lista_global = _lista_atual;
	}
	
	
	public CadastroRomaneio filtrar(File file) {
		CadastroRomaneio romaneio = null ;

		 //////JOptionPane.showMessageDialog(null, "caminho do arquivo sendo filtrado: " + file.getAbsolutePath());
	
		try (PDDocument document = PDDocument.load(file)) {

			if (!document.isEncrypted()) {

				PDFTextStripperByArea stripper = new PDFTextStripperByArea();
				stripper.setSortByPosition(true);

				PDFTextStripper tStripper = new PDFTextStripper();

				String pdfFileInText = tStripper.getText(document);

				String lines[] = pdfFileInText.split("\r\n");
				
				boolean e_romaneio = false;

				   String tratar = Arrays.toString(lines);
				//	System.out.println("Linhas: " + tratar);

					TratarDados tratamentoDados = new TratarDados(tratar);
				  String busca_numero_romaneio =  tratamentoDados.tratar("ROMANEIO DE PESO : ", ",");
				   
				 try {
				 int numero_romaneio = Integer.parseInt(busca_numero_romaneio);
				 e_romaneio = true;
				 }catch(Exception n) {
					 e_romaneio = false;
				 }
				    
               
				
                if(e_romaneio) {
                	
                	//////JOptionPane.showMessageDialog(null, "Tratando Romaneio");

                  romaneio = tratar_romaneio(lines, file);
                }else {
                	e_romaneio = false;
                	e_romaneio_global = false;
                	//////JOptionPane.showMessageDialog(null, "Arquivo nao e Romaneio");

                }
				

			}
			return romaneio;

		} catch (Exception e1) {
			//////JOptionPane.showMessageDialog(null, "Erro ao ler romaneio\nErro:  " + e1.getMessage() + "\nConsulte o Administrador");
			return null;
		}
		
	}
	
	
	/*
	public CadastroRomaneio tratar_romaneio (String lines[], File file) {
		
		CadastroRomaneio romaneio = new CadastroRomaneio();

		try {
		romaneio.setCaminho_arquivo(file.getAbsolutePath());

	   String tratar = Arrays.toString(lines);
      // tratar = tratar.replaceAll("\n", "*");
       //////JOptionPane.showMessageDialog(null, tratar);
      // tratar = tratar.replaceAll(" ", "");
       System.out.println(tratar);
		TratarDados tratamentoDados = new TratarDados(tratar);

       //operacao
		String operacao = "";
		if(tratar.contains("ENTRADA EM:")) {
			operacao = "ENTRADA";
		}else if(tratar.contains("SAIDA EM:")) {
			operacao = "SAÍDA";

		}
		
		////JOptionPane.showMessageDialog(null, "Operação: " + operacao);
         romaneio.setOperacao(operacao);
         String cfop ="";
 		if(tratar.contains("5906")) {
 			cfop = "5906";
 		}else if(tratar.contains("5907")) {
 			cfop = "5907";

 		}else if(tratar.contains("1905")) {
 			cfop = "1905";

 		}else if(tratar.contains("1934")) {
 			cfop = "1934";

 		}else if(tratar.contains("1949")) {
 			cfop = "1949";
 		}
 		//////JOptionPane.showMessageDialog(null, "CFOP: " + cfop);

 		romaneio.setCfop(cfop);
 		
 		String descricao_cfop = tratamentoDados.tratar(cfop + " ", ",");
 		//////JOptionPane.showMessageDialog(null, "descricao cfop: " + tratamentoDados.tratar(cfop + " ", ","));

 		romaneio.setDescricao_cfop(descricao_cfop);
       
 		

		String umidade = "";
		String busca_umidade = tratamentoDados.tratar("UMIDADE", "IMPUREZA");

		
		 String busca_umidade_quebrado[] = busca_umidade.split(" ");
		    ArrayList<String> busca_quebrados = new ArrayList<>();
		    
		    for(int i = 0; i < busca_umidade_quebrado.length; i++) {
		    	busca_umidade_quebrado[i] = busca_umidade_quebrado[i].replaceAll(" ", "");
		    	if(busca_umidade_quebrado[i].length() > 2 ) {
		    		//////JOptionPane.showMessageDialog(null, "Essa linha contem numeros, sera adicionado" + linha_destinatario_remetente_quebrada[i]);
		    		busca_quebrados.add(busca_umidade_quebrado[i]);
		    	}else {
		    		//////JOptionPane.showMessageDialog(null, "Essa linha não contem numeros, sera excluido" + linha_destinatario_remetente_quebrada[i]);
		
		    	}
		    }
		 umidade = busca_quebrados.get(0);
		
				
		romaneio.setUmidade(Double.parseDouble(umidade.replaceAll(",", ".")));
		//////JOptionPane.showMessageDialog(null, "umidade: " + umidade);

		  
		 
		 
		 
		String impureza = "";
		String busca_impureza = tratamentoDados.tratar("IMPUREZA", "AVARIADO");

		
		 String busca_impureza_quebrados[] = busca_impureza.split(" ");
		    ArrayList<String> impureza_quebrados = new ArrayList<>();
		    
		    for(int i = 0; i < busca_impureza_quebrados.length; i++) {
		    	busca_impureza_quebrados[i] = busca_impureza_quebrados[i].replaceAll(" ", "");
		    	if(busca_impureza_quebrados[i].length() > 2 ) {
		    		//////JOptionPane.showMessageDialog(null, "Essa linha contem numeros, sera adicionado" + linha_destinatario_remetente_quebrada[i]);
		    		impureza_quebrados.add(busca_impureza_quebrados[i]);
		    	}else {
		    		//////JOptionPane.showMessageDialog(null, "Essa linha não contem numeros, sera excluido" + linha_destinatario_remetente_quebrada[i]);
		
		    	}
		    }
		 impureza = impureza_quebrados.get(0);
		 //  ////JOptionPane.showMessageDialog(null, "impureza: " + impureza);

         romaneio.setInpureza(Double.parseDouble(impureza.replaceAll(",", ".")));


         String avariado = "";
 		String busca_avariado = tratamentoDados.tratar("AVARIADO", "Assinatura");

 		
 		 String busca_avariados_quebrados[] = busca_avariado.split(" ");
 		    ArrayList<String> avariados_quebrados = new ArrayList<>();
 		    
 		    for(int i = 0; i < busca_avariados_quebrados.length; i++) {
 		    	busca_avariados_quebrados[i] = busca_avariados_quebrados[i].replaceAll(" ", "");
 		    	if(busca_avariados_quebrados[i].length() > 2 ) {
 		    		//////JOptionPane.showMessageDialog(null, "Essa linha contem numeros, sera adicionado" + linha_destinatario_remetente_quebrada[i]);
 		    		avariados_quebrados.add(busca_avariados_quebrados[i]);
 		    	}else {
 		    		//////JOptionPane.showMessageDialog(null, "Essa linha não contem numeros, sera excluido" + linha_destinatario_remetente_quebrada[i]);
 		
 		    	}
 		    }
 		 avariado = avariados_quebrados.get(0);
         
         
         romaneio.setAvariados(Double.parseDouble(avariado.replaceAll(",", ".")));
         //  ////JOptionPane.showMessageDialog(null, "avariado" + avariado);


         //procurar por pesos
         String linha_com_pesos = tratamentoDados.tratar("Tara Observações", ", Inclusão:");
         linha_com_pesos = linha_com_pesos.replaceAll("[^0-9., ]", "");
         String linha_pesos_quebrada[] =  linha_com_pesos.split(" ");
         
      
         
         NumberFormat z = NumberFormat.getNumberInstance();

        
         
         
		   Number numero_peso_bruto = null;
			try {
				numero_peso_bruto = z.parse(linha_pesos_quebrada[0]);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			double peso_bruto = numero_peso_bruto.doubleValue();
			 //////JOptionPane.showMessageDialog(null, "peso bruto: " + peso_bruto);
			romaneio.setPeso_bruto(peso_bruto);
			

			   Number numero_peso_tara = null;
				try {
					numero_peso_tara = z.parse(linha_pesos_quebrada[1]);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				double peso_tara = numero_peso_tara.doubleValue();
				 //////JOptionPane.showMessageDialog(null, "peso tara: " + peso_tara);

				romaneio.setTara(peso_tara);

				   Number numero_peso_liquido = null;
					try {
						numero_peso_liquido = z.parse(linha_pesos_quebrada[8]);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					double peso_liquido = numero_peso_liquido.doubleValue();
			         romaneio.setPeso_liquido(peso_liquido);
			         //	////JOptionPane.showMessageDialog(null, "peso liquido: " + peso_liquido);

        
        String busca_numero_romaneio =  tratamentoDados.tratar(" , Romaneio", "Produto");
	    int numero_romaneio = Integer.parseInt(busca_numero_romaneio);
	    romaneio.setNumero_romaneio(numero_romaneio);
	    
    String s_produto = tratamentoDados.tratar("Romaneio" + numero_romaneio + "Produto", ",");
    String produto_busca = s_produto.replaceAll("EM GRÃOS", "").replaceAll(" ", "");
    //  ////JOptionPane.showMessageDialog(null, "produto: " + s_produto);
	    
	    String transgenia = tratamentoDados.tratar("Transgenia", ",").trim();
	    //////JOptionPane.showMessageDialog(null, "transgenia: " + transgenia);
	    
	    String transgenia_proxima = "";
	    
	    if(transgenia.contains("Nao Informado")) {
	    	 //////JOptionPane.showMessageDialog(null, "nao informado encontrado");
	    	transgenia_proxima =  "Transgenico(GMO)";
	    	produto_busca = produto_busca += " GMO";
	    }else if(transgenia.contains("Intacta")) {
	    	 //////JOptionPane.showMessageDialog(null, "intacta encontrado");
	    	produto_busca = produto_busca += " GMO";

	    	transgenia_proxima =  "Transgenico(GMO)";
	    }else if(transgenia.contains("RR")) {
	    	 //////JOptionPane.showMessageDialog(null, "RR encontrado");
	    	produto_busca = produto_busca += " GMO";

	    	transgenia_proxima =  "Transgenico(GMO)";
	    }else if(transgenia.contains("RR2")) {
	    	 //	////JOptionPane.showMessageDialog(null, "RR2 encontrado");
	    	produto_busca = produto_busca += " GMO";

	    	transgenia_proxima =  "Transgenico(GMO)";
	    }else if(transgenia.contains("Transgênico")) {
	    	 //////JOptionPane.showMessageDialog(null, "Transgenico encontrado");
	    	produto_busca = produto_busca += " GMO";

	    	transgenia_proxima =  "Transgenico(GMO)";
	    }else if(transgenia.contains("rr")) {
	    	 //////JOptionPane.showMessageDialog(null, "rr encontrado");
	    	produto_busca = produto_busca += " GMO";

	    	transgenia_proxima =  "Transgenico(GMO)";
	    }else {
	    	 //////JOptionPane.showMessageDialog(null, "Convencional encontrado");
	    	produto_busca = produto_busca += " NON-GMO";

	    	transgenia_proxima =  "Convencional(NON-GMO)";

	    }
	    //  ////JOptionPane.showMessageDialog(null, "Produto do romaneio: " + produto_busca);

	    //procura por produto
	    GerenciarBancoProdutos gerenciar_produtos = new GerenciarBancoProdutos();
	    ArrayList<CadastroProduto> produtos_cadastrado = gerenciar_produtos.getProdutos();
	    CadastroProduto produto = new CadastroProduto();
	    
	  

	    for(CadastroProduto prod : produtos_cadastrado ) {
		  
	    	if(prod.getNome_produto().equalsIgnoreCase(produto_busca)) {
               if(prod.getTransgenia().trim().equalsIgnoreCase(transgenia_proxima)) {
            	   //////JOptionPane.showMessageDialog(null, "Produto encontrado: " + prod.getNome_produto() + " Transgenia: " + prod.getTransgenia());

            	   produto = prod;
            	   break;
               }
	    	}
	    }
	    
	    String s_safra = tratamentoDados.tratar("Safra", ",");
        //procurar por safra plantio/colheita no format yyyy/yyyy
	    
	    String s_ano_colheita = "";
	    String s_ano_plantio = "";
	    
	    if(s_safra.equals("20/21")) {
	    	s_ano_plantio = "2020";
	    	s_ano_colheita = "2021";
	    }else if(s_safra.equals("21/21")) {
	    	s_ano_plantio = "2021";
	    	   s_ano_colheita = "2021";
	    }else if(s_safra.equals("21/22")) {
	    	s_ano_plantio = "2021";
	    	   s_ano_colheita = "2022";
	    }else if(s_safra.equals("20/20")) {
	    	s_ano_plantio = "2020";
	    	   s_ano_colheita = "2020";
	    }else if(s_safra.equals("22/22")) {
	    	s_ano_plantio = "2022";
	    	   s_ano_colheita = "2022";
	    }else if(s_safra.equals("22/23")) {
	    	s_ano_plantio = "2022";
	    	   s_ano_colheita = "2023";
	    }
	    else if(s_safra.equals("23/23")) {
	    	s_ano_plantio = "2023";
	    	   s_ano_colheita = "2023";
	    }else if(s_safra.equals("23/24")) {
	    	s_ano_plantio = "2023";
	    	   s_ano_colheita = "2024";
	    }else if(s_safra.equals("24/24")) {
	    	s_ano_plantio = "2024";
	    	   s_ano_colheita = "2024";
	    }else if(s_safra.equals("24/25")) {
	    	s_ano_plantio = "2024";
	    	s_ano_colheita = "2025";
	    }else if(s_safra.equals("25/25")) {
	    	s_ano_plantio = "2025";
	    	   s_ano_colheita = "2025";
	    }
	    
	    //procurar safra
	    int ano_plantio = Integer.parseInt(s_ano_plantio);
	    int ano_colheita = Integer.parseInt(s_ano_colheita);
	    
	    GerenciarBancoSafras gerenciar_safras = new GerenciarBancoSafras();
	    ArrayList<CadastroSafra> lista_safras = gerenciar_safras.getSafras();
	    CadastroSafra safra = new CadastroSafra();
	    
	    for(CadastroSafra busca : lista_safras) {
	    	if(busca.getAno_plantio() == ano_plantio && busca.getAno_colheita() == ano_colheita) {
	    		CadastroProduto prod_safra = new GerenciarBancoProdutos().getProduto(busca.getProduto().getId_produto());
	    		 //////JOptionPane.showMessageDialog(null, "safra da lista: " + prod_safra.getNome_produto() + " Transgenia: " + prod_safra.getTransgenia() +
	    		 //"\nsafra que quero: " + produto.getNome_produto() + " Transgenia: " + produto.getTransgenia());

    			 
	    		if(prod_safra.getId_produto() == produto.getId_produto() ) {
	    			//////JOptionPane.showMessageDialog(null, "Safra e produto encontrada");
	    			safra = busca;
	    			  
	    		    romaneio.setSafra(safra);
	    		    romaneio.setProduto(prod_safra);
	    			break;
	    		}else {
	    			 //	////JOptionPane.showMessageDialog(null, "o produto desta safra nao e o produto do romaneio\n id produto da safra da lista: " + prod_safra.getId_produto() + "\n id do produto que quero: " + produto.getId_produto());
 
	    			
	    			
	    		}
	    	}
	    }
	  
	    
	    String data = tratamentoDados.tratar(s_produto + ", ", " ");
		try {
			
			
			Date date = new SimpleDateFormat("dd/MM/yyyy").parse(data);
			
		romaneio.setData(date);
		}catch(Exception t) {
			 // ////JOptionPane.showMessageDialog(null, "Erro ao listar data do romaneio\nErro:  " + t.getMessage() + "\nConsulte o Administrador");
		}	   
		
		
	    //motorista
	    String nome_motorista = tratamentoDados.tratar(operacao + "Motorista", ",");
	    // ////JOptionPane.showMessageDialog(null, "Motorista: " + nome_motorista);
	    String placa = tratamentoDados.tratar("Placa", ",");
	    // ////JOptionPane.showMessageDialog(null, "placa: " + placa);
	    
	   
	     CadastroCliente motorista = new CadastroCliente();
	     motorista.setTransportador(1);
	     motorista.setNome_empresarial(nome_motorista);
	     motorista.setNome_fantaia(nome_motorista);
         motorista.setTipo_pessoa(0);
         
         CadastroCliente.Veiculo veiculo = new CadastroCliente.Veiculo();
         veiculo.setPlaca_trator(placa);
         ArrayList<CadastroCliente.Veiculo> veiculos = new ArrayList<>();
         veiculos.add(veiculo); 
         motorista.setVeiculos(veiculos);
         
         romaneio.setMotorista(motorista);
		
	    //procurar remetente destinatario
         //////JOptionPane.showMessageDialog(null, "linha pesos quebradas 8: " + linha_pesos_quebrada[8]);
	    String linha_destinatario_remetente = tratamentoDados.tratar(linha_pesos_quebrada[8] + ", Cpf/Cnpj: ", ", Desconto");
	    // ////JOptionPane.showMessageDialog(null, "linha destinatario remetnte quebradas 8: " + linha_destinatario_remetente);

	    String linha_destinatario_remetente_quebrada[] = linha_destinatario_remetente.split(" ");
	    ArrayList<String> quebrados = new ArrayList<>();
	    
	    for(int i = 0; i < linha_destinatario_remetente_quebrada.length; i++) {
	    	linha_destinatario_remetente_quebrada[i] = linha_destinatario_remetente_quebrada[i].replaceAll(" ", "");
	    	if(linha_destinatario_remetente_quebrada[i].length() > 10  ) {
	    		 //////JOptionPane.showMessageDialog(null, "Essa linha contem numeros, sera adicionado" + linha_destinatario_remetente_quebrada[i]);
	    		quebrados.add(linha_destinatario_remetente_quebrada[i]);
	    	}else {
	    		//////JOptionPane.showMessageDialog(null, "Essa linha não contem numeros, sera excluido" + linha_destinatario_remetente_quebrada[i]);
	
	    	}
	    }
	    
	    
	    
	    
	    String identificacao_produtor = quebrados.get(0).replaceAll("[^0-9]", "");
	    //  ////JOptionPane.showMessageDialog(null, "Id do destinatario: " + identificacao_produtor);
	    String ie_produtor = quebrados.get(1).replaceAll("[^0-9]", "");
	    //////JOptionPane.showMessageDialog(null, "IE do destinatario: " + ie_produtor);
	    //
	    String identificacao_destino = quebrados.get(2).replaceAll("[^0-9]", "");
	    // ////JOptionPane.showMessageDialog(null, "Id do remetente: " + identificacao_destino);

	    String ie_destino  = quebrados.get(3).replaceAll("[^0-9]", "");
  // ////JOptionPane.showMessageDialog(null, "IE do remetente: " + ie_destino);

	    
	    GerenciarBancoClientes gerente_clientes = new GerenciarBancoClientes();
	    ArrayList<CadastroCliente> lista_clientes = gerente_clientes.getClientes(0, 0, "");
	    
	    CadastroCliente produtor = new CadastroCliente();
	    boolean identificacao_produtor_encontrato = false;
	    boolean ie_produtor_encontrato = false;
	    
	  //procurar prdutor
	    for(CadastroCliente busca : lista_clientes) {
	    	if(busca.getTipo_pessoa() == 0) {
	    		if(busca.getCpf().equals(identificacao_produtor)) {
	    			//identificacao encontrada, verifica a IE
	    			identificacao_produtor_encontrato = true;
	    			if(busca.getIe() != null) {
	    			if(busca.getIe().equals(ie_produtor)) {
	    				produtor = busca;
	    				ie_produtor_encontrato = true;

	    				break;
	    			}else {
	    				
	    			}
	    			}
	    		}else {

	    		}
	    	}else {
	    		if(busca.getCnpj().equals(identificacao_produtor)) {
	    			//identificacao encontrada, verifica a IE
	    			identificacao_produtor_encontrato = true;
	    			if(busca.getIe() != null) {
	    			if(busca.getIe().equals(ie_produtor)) {

	    			    produtor = busca;
	    			    ie_produtor_encontrato = true;

	    				break;
	    			}else {
	    				
	    			}
	    			}
	    		}
	    	}
	    }
	    
        
         
		  //procurar remetente
	    CadastroCliente destino = new CadastroCliente();
	    boolean identificacao_destino_encontrado = false;
	    boolean ie_destino_encontrado = false;
	    
	    for(CadastroCliente busca : lista_clientes) {
	    	if(busca.getTipo_pessoa() == 0) {
	    		
	    		if(busca.getCpf().equals(identificacao_destino)) {
	    			//identificacao encontrada, verifica a IE
	    			identificacao_destino_encontrado = true;
	    			if(busca.getIe() != null) {
	    			if(busca.getIe().equals(ie_destino)) {
	    			    destino = busca;
	    			    ie_destino_encontrado = true;
	    				break;
	    			}else {
	    				
	    			}
	    		}
	    		}
	    	}else {
	    		if(busca.getCnpj().equals(identificacao_destino)) {
	    			//identificacao encontrada, verifica a IE
	    			identificacao_destino_encontrado = true;
	    			if(busca.getIe() != null) {
	    			if(busca.getIe().equals(ie_destino)) {
	    			    destino = busca;
	    			    ie_destino_encontrado = true;

	    				break;
	    			}else {
	    				
	    			}
	    			}
	    		}
	    	}
	    }

	    boolean prosseguir_produtor = false;
	    
	    if(identificacao_produtor_encontrato && ie_destino_encontrado) {
	    	prosseguir_produtor = true;
	    	if(avisos != null) {
	    	   avisos.setMensagem("Produtor Cadastrado");
		        Thread.sleep(1000);
	    	}
	    }else if(identificacao_produtor_encontrato && !ie_destino_encontrado) {
	        if(avisos != null) {
            avisos.setMensagem("Ha cadastro para este produtor, mas com I.E diferente, cadastre a nova I.E\n"
            		+ "\nProdutor: " + identificacao_produtor + "\nNova I.E: " + ie_produtor);
	        Thread.sleep(10000);
	        }

	    }else if(!identificacao_produtor_encontrato && !ie_destino_encontrado){
	        if(avisos != null) {
	    	  avisos.setMensagem("Cadastre o Produtor: " + identificacao_produtor + "\nI.E: " + ie_produtor);
	        Thread.sleep(10000);
	    	prosseguir_produtor = false;
	        }

	    }else {
	    	prosseguir_produtor = false;
	        if(avisos != null) {
		    	  avisos.setMensagem("Cadastre o Produtor: " + identificacao_produtor + "\nI.E: " + ie_produtor);
	        Thread.sleep(10000);
	        }
	    }
	    
	    boolean prosseguir_destino = false;

	    
	    if(identificacao_destino_encontrado && ie_destino_encontrado) {
	    	prosseguir_destino = true;
	    	if(avisos != null) {
	    	   avisos.setMensagem("Destinatario Cadastrado");
		        Thread.sleep(1000);
	    	}

	    }else if(identificacao_destino_encontrado && !ie_destino_encontrado) {
	    	prosseguir_destino = false;
	        if(avisos != null) {
	    	avisos.setMensagem("Ha cadastro para este destinatario, mas com I.E diferente, cadastre a nova I.E\n"
            		+ "Destinatario: " + identificacao_destino + "\nNova I.E: " + ie_destino);
	        Thread.sleep(10000);
	        }



	    }else if(!identificacao_destino_encontrado && !ie_destino_encontrado){
	    	prosseguir_destino = false;
	        if(avisos != null) {
	    	  avisos.setMensagem("Cadastre o Destinatario: " + identificacao_destino + "\nI.E: " + ie_destino);
	        Thread.sleep(10000);
	        }


	    }else {
	    	prosseguir_destino = false;
	    		if(avisos != null) {
	  	    	  avisos.setMensagem("Cadastre o Destinatario: " + identificacao_destino + "\nI.E: " + ie_destino);
	  	        Thread.sleep(10000);
	    		}
	    }

	    
	    if(prosseguir_destino && prosseguir_produtor) {

	    	romaneio.setDestinatario(destino);
			
			


	    	romaneio.setRemetente(produtor);
	    	

	    	
	    	return romaneio;
	    }else {
			return null;

	    }
		}catch(Exception e) {
			//////JOptionPane.showMessageDialog(null, "Excessao ao tratar romaneio\nErro: " + e.getMessage() + "\nCausa: " + e.getCause() );
		      if(avisos != null) {
		    	  avisos.setMensagem("Erro ao tratar romaneio\nConsulte o administrador\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
		  	        try {
						Thread.sleep(10000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

		      }
			return null;
		}
	    
	    
		
	}
	*/
	

  public void setTelaMensagem(TelaNotificacaoSuperiorModoBusca tela) {
	  this.avisos = tela;
  }
	

	private void getDadosGlobais() {
		// gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		GerenciadorLog = dados.getGerenciadorLog();
		configs_globais = dados.getConfigs_globais();

		// usuario logado
		login = dados.getLogin();

		//telaprincipal
		telaPrincipal = dados.getTelaPrincipal();
	}
	
	public CadastroRomaneio getRomaneio(String codigo) {

		ArrayList<String> listaLocal = new ArrayList<>();
		File file = null;
		MyFileVisitor arquivos = new MyFileVisitor();
		Path source = Paths.get(configs_globais.getServidorUnidade() + "E-Contract\\arquivos\\");
		System.out.println("Pasta procura romaneios: " + source);
		try {
			Files.walkFileTree(source, arquivos);
			listaLocal = arquivos.getArquivos();

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		System.out.println("numero de arquivos encontratos: " + listaLocal.size());

		for (int i = 0; i < listaLocal.size(); i++) {
			if (listaLocal.get(i).endsWith(".pdf") || listaLocal.get(i).endsWith(".Pdf")) {
				TratarDados tratar = new TratarDados(listaLocal.get(i));

				try {
					String result = tratar.tratar("romaneio-", ".pdf").replaceAll("[^0-9]", "");
					System.out.println("Result: " + result);
					System.out.println("Codigo: " + codigo);

						if (result.trim().equals(codigo.trim())) {
							System.out.println("encontrato");
							file = new File(listaLocal.get(i).toString());

							break;
						}

					
				} catch (Exception e) {

				}

			}
		}

		return filtrar(file);

	}
	
	
public CadastroRomaneio tratar_romaneio (String lines[], File file) {
		
		CadastroRomaneio romaneio = new CadastroRomaneio();

		try {
		romaneio.setCaminho_arquivo(file.getAbsolutePath());
	
		 
		 
	   String tratar = Arrays.toString(lines);
      // tratar = tratar.replaceAll("\n", "*");
       //////JOptionPane.showMessageDialog(null, tratar);
      // tratar = tratar.replaceAll(" ", "");
        System.out.println(tratar);
		TratarDados tratamentoDados = new TratarDados(tratar);

		String busca_numero_romaneio =  tratamentoDados.tratar("ROMANEIO DE PESO : ", ",");
		 int numero_romaneio = Integer.parseInt(busca_numero_romaneio);
		 
		 romaneio.setNumero_romaneio(numero_romaneio);
		 
       //operacao
		String operacao = "";
		if(tratar.contains("ENTRADA EM:")) {
			operacao = "ENTRADA";
		}else if(tratar.contains("SAIDA EM:")) {
			operacao = "SAÍDA";

		}
		
		//JOptionPane.showMessageDialog(null, "Operação: " + operacao);
         romaneio.setOperacao(operacao);
        

		String busca_umidade = tratamentoDados.tratar("UMIDADE ", " %");

		  //JOptionPane.showMessageDialog(null, "Umidade: " + busca_umidade);
		try {
		romaneio.setUmidade(Double.parseDouble(busca_umidade.replaceAll(",", ".")));
		  //JOptionPane.showMessageDialog(null, "umidade em double: " + romaneio.getUmidade());
		}catch(Exception u) {
			//JOptionPane.showMessageDialog(null, "Umidade não encontrada, possivel romaneio de transferencia");
			operacao = "SAÍDA TRANSFERENCIA";
	         romaneio.setOperacao(operacao);

		}
		
		 String busca_impureza = tratamentoDados.tratar("MATERIAS ESTRANHAS E IMPUREZA ", " %");

		 //JOptionPane.showMessageDialog(null, "Impureza: " + busca_impureza);	
			try {
         romaneio.setInpureza(Double.parseDouble(busca_impureza.replaceAll(",", ".")));
         //JOptionPane.showMessageDialog(null, "Impureza em double: " + romaneio.getInpureza());
			}catch(Exception t) {
				//JOptionPane.showMessageDialog(null, "Impureza não encontrada, possivel romaneio de transferencia");

			}

 		String busca_avariado = tratamentoDados.tratar("AVARIADOS TOTAIS ", " %");

 		//JOptionPane.showMessageDialog(null, "Avariados: " + busca_avariado);	
		try {
         romaneio.setAvariados(Double.parseDouble(busca_avariado.replaceAll(",", ".")));
         //JOptionPane.showMessageDialog(null, "avariados em double: " + romaneio.getAvariados());
		}catch(Exception l) {
		//JOptionPane.showMessageDialog(null, "avariados não encontrado, possivel romaneio de transferencia");
	
		}
		
        NumberFormat z = NumberFormat.getNumberInstance();

         //procurar por pesos
         String s_peso_bruto = tratamentoDados.tratar("BRUTO: ", ",");
         
		   Number numero_peso_bruto = null;
			try {
				numero_peso_bruto = z.parse(s_peso_bruto);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			double peso_bruto = numero_peso_bruto.doubleValue();
			//JOptionPane.showMessageDialog(null, "peso bruto: " + peso_bruto);
			romaneio.setPeso_bruto(peso_bruto);
			
			try {
	         String s_peso_tara = tratamentoDados.tratar("TARA: ", ",");

			   Number numero_peso_tara = null;
				try {
					numero_peso_tara = z.parse(s_peso_tara);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				double peso_tara = numero_peso_tara.doubleValue();
				 //JOptionPane.showMessageDialog(null, "peso tara: " + peso_tara);

				romaneio.setTara(peso_tara);
			}catch(Exception e) {
				romaneio.setTara(0);

			}

		         String s_peso_liquido =  tratamentoDados.tratar("LIQ.FINAL: ", ",");

				   Number numero_peso_liquido = null;
					try {
						numero_peso_liquido = z.parse(s_peso_liquido);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					double peso_liquido = numero_peso_liquido.doubleValue();
			         romaneio.setPeso_liquido(peso_liquido);
			          //JOptionPane.showMessageDialog(null, "peso liquido: " + peso_liquido);

        
      
	    
	    String produto_busca = "";
	    
	    if(tratar.contains("SOJA")) {
	    	produto_busca = "SOJA";
	    }else if(tratar.contains("SORGO")) {
	    	produto_busca = "SORGO";

	    }else if(tratar.contains("MILHO")) {
	    	produto_busca = "MILHO";

	    }
	    
	   
	    String transgenia_proxima = "";
	    
	    if(tratar.contains("Nao Informado")) {
	    	//JOptionPane.showMessageDialog(null, "nao informado encontrado");
	    	transgenia_proxima =  "Não Informar";
	    	produto_busca = produto_busca;
	    }else if(tratar.contains("TRANG.")){
	    	//JOptionPane.showMessageDialog(null, "transgenico encontrado");
		    	transgenia_proxima =  "Transgenico(GMO)";
		    	produto_busca = produto_busca += " GMO";
	    }else if(tratar.contains("CONV.")){
	    	 //JOptionPane.showMessageDialog(null, "Convencional encontrado");
	    	produto_busca = produto_busca += " NON-GMO";

	    	transgenia_proxima =  "Convencional(NON-GMO)";

	    }else {
	    	transgenia_proxima =  "Não Informar";
	    	produto_busca = produto_busca;
	    }
	     //JOptionPane.showMessageDialog(null, "Produto do romaneio: " + produto_busca);

	    //procura por produto
	    GerenciarBancoProdutos gerenciar_produtos = new GerenciarBancoProdutos();
	    ArrayList<CadastroProduto> produtos_cadastrado = gerenciar_produtos.getProdutos();
	    CadastroProduto produto = new CadastroProduto();
	    
	  

	    for(CadastroProduto prod : produtos_cadastrado ) {
		  
	    	if(prod.getNome_produto().trim().equalsIgnoreCase(produto_busca)) {
               if(prod.getTransgenia().trim().equalsIgnoreCase(transgenia_proxima)) {
            	   //JOptionPane.showMessageDialog(null, "Produto encontrado: " + prod.getNome_produto() + " Transgenia: " + prod.getTransgenia());

            	   produto = prod;
            	   break;
               }
	    	}
	    }
	    
	  
	    
	    String s_ano_colheita = "";
	    String s_ano_plantio = "";
	    
	    if(tratar.contains("20/21")) {
	    	s_ano_plantio = "2020";
	    	s_ano_colheita = "2021";
	    }else if(tratar.contains("21/21")) {
	    	s_ano_plantio = "2021";
	    	   s_ano_colheita = "2021";
	    }else if(tratar.contains("21/22")) {
	    	s_ano_plantio = "2021";
	    	   s_ano_colheita = "2022";
	    }else if(tratar.contains("20/20")) {
	    	s_ano_plantio = "2020";
	    	   s_ano_colheita = "2020";
	    }else if(tratar.contains("22/22")) {
	    	s_ano_plantio = "2022";
	    	   s_ano_colheita = "2022";
	    }else if(tratar.contains("22/23")) {
	    	s_ano_plantio = "2022";
	    	   s_ano_colheita = "2023";
	    }
	    else if(tratar.contains("23/23")) {
	    	s_ano_plantio = "2023";
	    	   s_ano_colheita = "2023";
	    }else if(tratar.contains("23/24")) {
	    	s_ano_plantio = "2023";
	    	   s_ano_colheita = "2024";
	    }else if(tratar.contains("24/24")) {
	    	s_ano_plantio = "2024";
	    	   s_ano_colheita = "2024";
	    }else if(tratar.contains("24/25")) {
	    	s_ano_plantio = "2024";
	    	s_ano_colheita = "2025";
	    }else if(tratar.contains("25/25")) {
	    	s_ano_plantio = "2025";
	    	   s_ano_colheita = "2025";
	    }
	    
	    //procurar safra
	    int ano_plantio = Integer.parseInt(s_ano_plantio);
	    int ano_colheita = Integer.parseInt(s_ano_colheita);
	    
	    GerenciarBancoSafras gerenciar_safras = new GerenciarBancoSafras();
	    ArrayList<CadastroSafra> lista_safras = gerenciar_safras.getSafras();
	    CadastroSafra safra = new CadastroSafra();
	    boolean safra_encontrada = false;
	    
	    for(CadastroSafra busca : lista_safras) {
	    	if(busca.getAno_plantio() == ano_plantio && busca.getAno_colheita() == ano_colheita) {
	    		CadastroProduto prod_safra = new GerenciarBancoProdutos().getProduto(busca.getProduto().getId_produto());
	    		    //JOptionPane.showMessageDialog(null, "safra da lista: " + prod_safra.getNome_produto() + " Transgenia: " + prod_safra.getTransgenia() +
	    		  // "\nsafra que quero: " + produto.getNome_produto() + " Transgenia: " + produto.getTransgenia());

    			 
	    		if(prod_safra.getId_produto() == produto.getId_produto() ) {
	    			   //JOptionPane.showMessageDialog(null, "Safra e produto encontrada");
	    			safra = busca;
	    			  
	    		    romaneio.setSafra(safra);
	    		    romaneio.setProduto(prod_safra);
	    		    safra_encontrada = true;
	    			break;
	    		}else {
	    			    //JOptionPane.showMessageDialog(null, "o produto desta safra nao e o produto do romaneio\n id produto da safra da lista: " + prod_safra.getId_produto() + "\n id do produto que quero: " + produto.getId_produto());
 
	    			
	    			
	    		}
	    	}
	    }
	  
	    if(!safra_encontrada) {
	    	CadastroAviso avisar = new CadastroAviso();
	    	avisar.setTipo("Aviso");
	    	avisar.setSetor("Importação de Romaneios");
	    	avisar.setMensagem("Safra não encontrada! Adicione a safra " + produto.getNome_produto() +  
		    		  " " + produto.getTransgenia() + " " + ano_plantio + "/" + ano_colheita);
		      telaPrincipal.incluir_aviso(avisar);
              return null;
	    }else {
	    	
		String data = tratamentoDados.tratar("SAÍDA: ", " ");
		try {
			
			
			Date date = new SimpleDateFormat("dd/MM/yyyy").parse(data);
			
		romaneio.setData(date);
		}catch(Exception t) {
			    //JOptionPane.showMessageDialog(null, "Erro ao listar data do romaneio\nErro:  " + t.getMessage() + "\nConsulte o Administrador");
		}	   
		
		   //JOptionPane.showMessageDialog(null, "Data: " + data);
		
	    //motorista
	    String nome_motorista = tratamentoDados.tratar("MOTORISTA: ", "PRODUTO");
	     //JOptionPane.showMessageDialog(null, "Motorista: " + nome_motorista);
	    String placa = tratamentoDados.tratar("PLACA: ", ", MOTORISTA");
	    //JOptionPane.showMessageDialog(null, "placa: " + placa);
	    
	     String cpf_motorista = tratamentoDados.tratar(" CPF: ", ",");
	      //JOptionPane.showMessageDialog(null, "Cpf motorista: " + cpf_motorista);

	     CadastroCliente motorista = new CadastroCliente();
	     motorista.setTransportador(1);
	     motorista.setNome_empresarial(nome_motorista);
	     motorista.setNome_fantaia(nome_motorista);
         motorista.setTipo_pessoa(0);
         motorista.setCpf(cpf_motorista.replaceAll("[^0-9]", ""));
         
         CadastroCliente.Veiculo veiculo = new CadastroCliente.Veiculo();
         veiculo.setPlaca_trator(placa);
         ArrayList<CadastroCliente.Veiculo> veiculos = new ArrayList<>();
         veiculos.add(veiculo); 
         motorista.setVeiculos(veiculos);
         
         romaneio.setMotorista(motorista);
		
	  
	    String s_depositante = tratamentoDados.tratar("CNPJ/CPF: ", ",").replaceAll("[^0-9]", "");
	    String s_insc_depositante = tratamentoDados.tratar("INSCR.EST., :, ", ",").replaceAll("[^0-9]", "");
	    
	    GerenciarBancoClientes gerente_clientes = new GerenciarBancoClientes();
	    ArrayList<CadastroCliente> lista_clientes = gerente_clientes.getClientes(0, 0, "");
	    
	    CadastroCliente depositante = new CadastroCliente();
	    boolean identificacao_depositante_encontrato = false;
	    boolean ie_depositante_encontrado = false;
	    
	  //procurar depositante
	    for(CadastroCliente busca : lista_clientes) {
	    	if(busca.getTipo_pessoa() == 0) {
	    		if(busca.getCpf().equals(s_depositante)) {
	    			//identificacao encontrada, verifica a IE
	    			identificacao_depositante_encontrato = true;
	    			if(busca.getIe() != null) {
	    			if(busca.getIe().equals(s_insc_depositante)) {
	    				depositante = busca;
	    				ie_depositante_encontrado = true;

	    				break;
	    			}else {
	    				
	    			}
	    			}
	    		}else {

	    		}
	    	}else {
	    		if(busca.getCnpj().equals(s_depositante)) {
	    			//identificacao encontrada, verifica a IE
	    			identificacao_depositante_encontrato = true;
	    			if(busca.getIe() != null) {
	    			if(busca.getIe().equals(s_insc_depositante)) {

	    				depositante = busca;
	    				ie_depositante_encontrado = true;

	    				break;
	    			}else {
	    				
	    			}
	    			}
	    		}
	    	}
	    }
	    
  boolean prosseguir_depositante = false;
	    
	    if(identificacao_depositante_encontrato && ie_depositante_encontrado) {
	    	prosseguir_depositante = true;
	    	if(avisos != null) {
	    	   avisos.setMensagem("Depositante Cadastrado");
		        Thread.sleep(1000);
		       
	    	}
	    }else if(identificacao_depositante_encontrato && !ie_depositante_encontrado) {
	        if(avisos != null) {
            avisos.setMensagem("Ha cadastro para este depositante, mas com I.E diferente, cadastre a nova I.E\n"
            		+ "\nDepositante: " + s_depositante + "\nNova I.E: " + s_insc_depositante);
	        Thread.sleep(10000);
	        }
	        //JOptionPane.showMessageDialog(null, "Ha cadastro para este depositante, mas com I.E diferente, cadastre a nova I.E\n"
	      	//+ "\nDepositante: " + s_depositante + "\nNova I.E: " + s_insc_depositante);
	    	CadastroAviso avisar = new CadastroAviso();
	    	avisar.setTipo("Aviso");
	    	avisar.setSetor("Importação de Romaneio");
	    	avisar.setMensagem("Ha cadastro para este depositante, mas com I.E diferente, cadastre a nova I.E\n"
            		+ "\nDepositante: " + s_depositante + "\nNova I.E: " + s_insc_depositante);
	    	
	    	telaPrincipal.incluir_aviso(avisar);

	    }else if(!identificacao_depositante_encontrato && !ie_depositante_encontrado){
	        if(avisos != null) {
	    	  avisos.setMensagem("Cadastre o Depositante: " + s_depositante + "\nI.E: " + s_insc_depositante);
	        Thread.sleep(10000);
	        prosseguir_depositante = false;
	        }
	        //JOptionPane.showMessageDialog(null,"Cadastre o Depositante: " + s_depositante + "\nI.E: " + s_insc_depositante );
	    	CadastroAviso avisar = new CadastroAviso();

	    	avisar.setTipo("Aviso");
	    	avisar.setSetor("Importação de Romaneio");
	    	avisar.setMensagem("Cadastre o Depositante: " + s_depositante + "\nI.E: " + s_insc_depositante );
	    	
	    	telaPrincipal.incluir_aviso(avisar);

	    }else {
	    	prosseguir_depositante = false;
	        if(avisos != null) {
		    	  avisos.setMensagem("Cadastre o Depositante: " + s_depositante+ "\nI.E: " + s_insc_depositante);
	        Thread.sleep(10000);
	        }
	        //JOptionPane.showMessageDialog(null,"Cadastre o Depositante: " + s_depositante + "\nI.E: " + s_insc_depositante );
	    	CadastroAviso avisar = new CadastroAviso();

	    	avisar.setTipo("Aviso");
	    	avisar.setSetor("Importação de Romaneio");
	    	avisar.setMensagem("Cadastre o Depositante: " + s_depositante + "\nI.E: " + s_insc_depositante );
	    	
	    	telaPrincipal.incluir_aviso(avisar);
	    }
	    
	    
	    String s_insc_destino = tratamentoDados.tratar(" INSC:", ",").replaceAll("[^0-9]", "");
	    boolean ie_destino_encontrado = false;
	    CadastroCliente destino = new CadastroCliente();

        if(s_insc_destino.length() > 9 ) { 
		  //procurar remetente/destinatariop
	    
	    for(CadastroCliente busca : lista_clientes) {
	    	
	    			if(busca.getIe() != null) {
	    			if(busca.getIe().equals(s_insc_destino)) {
	    			    destino = busca;
	    			    ie_destino_encontrado = true;
	    				break;
	    			}else {
	    				
	    			}
	    		}
	    		
	   }
        }else {
        	ie_destino_encontrado = false;
        	destino = null;
        }
	  
	    
	    boolean prosseguir_destino = false;

	    
	    if(ie_destino_encontrado) {
	    	prosseguir_destino = true;
	    	if(avisos != null) {
	    	   avisos.setMensagem("Destinatario Cadastrado");
		        Thread.sleep(1000);
	    	}
	    	   //JOptionPane.showMessageDialog(null, "Destinatario cadastrado");

	    }else if(!ie_destino_encontrado) {
	    	prosseguir_destino = false;
	        if(avisos != null) {
	    	avisos.setMensagem("Destinatario nao encontrado: " + s_insc_destino) ;
	        Thread.sleep(10000);
	        }
	        //JOptionPane.showMessageDialog(null, "Destinatario nao encontrado: " + s_insc_destino);
	        CadastroAviso avisar = new CadastroAviso();

	        if(sinal_global == 2) {
	        	//está consultando romaneios
	        }else {
	    	avisar.setTipo("Aviso");
	    	avisar.setSetor("Importação de Romaneio");
	    	String nome_destinatario = tratamentoDados.tratar("REMET./DESTIN, ., ", " INSC");
	    	avisar.setMensagem("Cadastre o Destinatario: " + nome_destinatario + " I.E: " + s_insc_destino );
	    	telaPrincipal.incluir_aviso(avisar);

	        }


	    }

	    
	    if(prosseguir_depositante || prosseguir_destino) {

	    	romaneio.setDestinatario(destino);
			
			


	    	romaneio.setRemetente(depositante);
	    	
	    	   //JOptionPane.showMessageDialog(null, "retornando o romaneio nao nulo: " + s_insc_destino);

	    	
	    	return romaneio;
	    }else {
			return null;

	    }
	    }
		}catch(Exception e) {
			//JOptionPane.showMessageDialog(null, "Excessao ao tratar romaneio\nErro: " + e.getMessage() + "\nCausa: " + e.getCause() );
		      if(avisos != null) {
		    	  avisos.setMensagem("Erro ao tratar romaneio\nConsulte o administrador\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
		  	        try {
						Thread.sleep(10000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

		      }
			return null;
		}
	    
	    
		
	}


  public void sinalizar(int sinal) {
	  sinal_global = sinal;
  }

}
