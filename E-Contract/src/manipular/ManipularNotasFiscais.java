package manipular;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import javax.swing.JDialog;

import cadastros.CadastroLogin;
import cadastros.CadastroNFe;
import gui.TelaGerenciarContrato;
import gui.TelaNotasFiscais;
import gui.TelaTodasNotasFiscais;

import outros.DadosGlobais;
import outros.MyFileVisitor;
import outros.TratarDados;
import tratamento_proprio.Log;

public class ManipularNotasFiscais {
	private JFileChooser fileChooserGlobal;
	private ArrayList<String> listadeArquivos = new ArrayList<>();

	private ArrayList<CadastroNFe> notas_fiscais = new ArrayList<>();

	private String caminho;
	private JTable table;
	private int countArquivos;
	private int countPDF;
	private int countNotas;

	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;

	private JDialog tela_pai;

	public ManipularNotasFiscais(String _caminho) {
		getDadosGlobais();
		caminho = _caminho;

	}

	public ArrayList<CadastroNFe> tratar() {

		MyFileVisitor arquivos = new MyFileVisitor();
		Path source = Paths.get(caminho);
		try {
			Files.walkFileTree(source, arquivos);
			listadeArquivos = arquivos.getArquivos();

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		for (int i = 0; i < listadeArquivos.size(); i++) {
			 System.out.println(listadeArquivos.get(i).toString());
			countArquivos++;
		}

		for (int i = 0; i < listadeArquivos.size(); i++) {
			if (listadeArquivos.get(i).endsWith(".pdf") || listadeArquivos.get(i).endsWith(".Pdf")) {
				countPDF++;

				File file = new File(listadeArquivos.get(i).toString());
				CadastroNFe cadastro = filtrar(file);

				if (cadastro != null) {
					notas_fiscais.add(cadastro);
					countNotas++;
					
					if(tela_pai instanceof TelaNotasFiscais)
					((TelaNotasFiscais) tela_pai).addNota(cadastro);
					else
					((TelaTodasNotasFiscais) tela_pai).addNota(cadastro);

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

		return notas_fiscais;
	}

	public CadastroNFe filtrar(File file) {
		CadastroNFe cadastro = new CadastroNFe();

		 System.out.println("caminho do arquivo: " + file.getAbsolutePath());

		try (PDDocument document = PDDocument.load(file)) {

			if (!document.isEncrypted()) {

				PDFTextStripperByArea stripper = new PDFTextStripperByArea();
				stripper.setSortByPosition(true);

				PDFTextStripper tStripper = new PDFTextStripper();

				String pdfFileInText = tStripper.getText(document);

				String lines[] = pdfFileInText.split("\r\n");
				// String linhas = Arrays.toString(lines);
				//String demais = lines[118];
				// System.out.println("Demais: " + demais);
				//String separados[] = demais.split(" ");
				/*
				 * for (String line : separados) { System.out.println(line); }
				 */
				
				 // for (String line : lines) { System.out.println(line); }
				 

				String tratar = Arrays.toString(lines);
				// tratar = tratar.replaceAll("\n", "*");
				//System.out.println(tratar);
				TratarDados tratamentoDados = new TratarDados(tratar);

				for(String linha : lines) {
					if(linha.contains("EPEC")) {
						//nfe interna
						
						cadastro = tratar_nfe_interna(lines ,file);

					}else if(linha.contains("NFA-e")) {
						//nfa siare
						cadastro = tratar_nfa_siare(lines, file);
					}else if(linha.contains("Oobj")) {
						JOptionPane.showMessageDialog(null, "Iniciando tratamento de nfe siacon");
						//nfe siacon
						cadastro = tratar_nfe_interna_siacon(lines, file);
					}
					
				}

				

			}
			return cadastro;

		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "Erro ao ler nota fiscal\nErro:  " + e1.getMessage() + "\nConsulte o Administrador");
			return null;
		}
	}

		
	
		public CadastroNFe tratar_nfa_siare (String lines[], File file) {
			
			CadastroNFe cadastro = new CadastroNFe();
      		cadastro.setCaminho_arquivo(file.getAbsolutePath());

			String tratar = Arrays.toString(lines);
			// tratar = tratar.replaceAll("\n", "*");
			// System.out.println(tratar);
			TratarDados tratamentoDados = new TratarDados(tratar);
			
			String procura_nfe = tratamentoDados.tratar("NFA-e", "VENDA");
			String nfe = null;
			String natureza = null;
			if (procura_nfe.equals("") || procura_nfe == null) {
				procura_nfe = tratamentoDados.tratar("NFA-e", "REMESSA");
				if (procura_nfe.equals("") || procura_nfe == null) {
					// procurar por outro tipo de nota
					procura_nfe = tratamentoDados.tratar("NFA-e", "OUTRAS SAÍDAS");
					if (procura_nfe.equals("") || procura_nfe == null) {
						// System.out.println("Número NFA não encontrado");
					} else {
						nfe = procura_nfe;

						natureza = "OUTRAS SAÍDAS";

					}

				} else {
					nfe = procura_nfe;
					natureza = "REMESSA";

				}
			} else {
				nfe = procura_nfe;
				natureza = "VENDA";
			}
			nfe = nfe.replaceAll(",", "");

			// String nfe = lines[27].toString().replaceAll("[^0-9]+", "");
			// System.out.println("Numero nfe: " + nfe);

			String serie = tratamentoDados.tratar("SÉRIE", "C");
			serie = serie.replaceAll("[^0-9]+", "");
			// String serie = lines[90].toString().replaceAll("[^0-9]+", "");
			// System.out.println("Serie: " + serie);

			// String nome_remetente = lines[29].toString().replaceAll("[0-9]+", "");
			// nome_remetente = nome_remetente.replaceAll("[^a-zA-Z ]", "");
			// int linha_natureza = tratar.indexOf(tratar);

			String nome_remetente = null;
			if (natureza.equals("VENDA")) {
				nome_remetente = tratamentoDados.tratar("VENDA,", ",");
				nome_remetente = nome_remetente.replaceAll("[^a-zA-Z ]", "");

			}
			if (natureza.equals("REMESSA")) {
				nome_remetente = tratamentoDados.tratar("REMESSA,", ",");
				nome_remetente = nome_remetente.replaceAll("[^a-zA-Z ]", "");

			}
			if (natureza.equals("OUTRAS SAÍDAS")) {
				nome_remetente = tratamentoDados.tratar("OUTRAS SAÍDAS,", ",");
				nome_remetente = nome_remetente.replaceAll("[^a-zA-Z ]", "");

			}

			// System.out.println("Remetente: " + nome_remetente);
			/*
			 * String inscricao_remetente = lines[31].toString().replaceAll("[^0-9]+", "");
			 * inscricao_remetente = inscricao_remetente.substring(4,
			 * inscricao_remetente.length()); System.out.println("Inscricao: " +
			 * inscricao_remetente);
			 */

			// String procura_inscricao_remetente = tratamentoDados.tratar("BRASIL", ",");

			String teste_inscricao_remetente = tratar.replaceFirst("BRASIL", "XZYK");
			TratarDados tratamentoDadosInscricaoRemetente = new TratarDados(teste_inscricao_remetente);
			String inscricao_remetente = tratamentoDadosInscricaoRemetente.tratar("XZYK", ",");

			// System.out.println(teste_inscricao_remetente);
			teste_inscricao_remetente = teste_inscricao_remetente.replaceFirst(inscricao_remetente, "");
			// System.out.println(teste_inscricao_remetente);
			TratarDados tratamentoDadosNomeDestinatario = new TratarDados(teste_inscricao_remetente);
			String nome_destinatario = tratamentoDadosNomeDestinatario.tratar("XZYK,", ",");
			nome_destinatario = nome_destinatario.replaceAll("[^a-zA-Z ]", "");

			// teste_inscricao_remetente = tratar.replaceFirst(inscricao_remetente, "");
			// System.out.println(teste_inscricao_remetente);
			/*
			 * String procura_nome_destinatario = tratamentoDadosInscricao.tratar("XZYK",
			 * ","); procura_nome_destinatario =
			 * procura_nome_destinatario.replaceAll("[^a-zA-Z ]", ""); String
			 * nome_destinatario = procura_nome_destinatario;
			 */
			String inscricao_destinatario = tratamentoDados.tratar("BRASIL", ",");
			// System.out.println("Inscricao: " + inscricao_remetente);

			// String array_protocolo[] = lines[95].split(" ");
			String protocolo = tratamentoDados.tratar("USO", "-");
			protocolo = protocolo.replaceAll("[^0-9]+", "");
			// String protocolo = array_protocolo[0].toString();

			// System.out.println("Protocolo: " + protocolo);

			// String data = lines[33].toString();
			teste_inscricao_remetente = teste_inscricao_remetente.replaceFirst(protocolo, "DATA");
			TratarDados tratarDadosData = new TratarDados(teste_inscricao_remetente);
			String data = tratarDadosData.tratar("DATA", ",");
			data = data.replace("-", "");
			// System.out.println("Data: " + data);

			// String natureza = lines[28].toString();
			// System.out.println("Natureza: " + natureza);

			// String nome_destinatario = lines[32].toString().replaceAll("[0-9]+", "");
			// nome_destinatario = nome_destinatario.replaceAll("[^a-zA-Z ]", "");

			// System.out.println("Destinatario: " + nome_destinatario);

			/*
			 * String inscricao_destinatario = lines[36].toString().replaceAll("[^0-9]+",
			 * ""); inscricao_destinatario = inscricao_destinatario.substring(4,
			 * inscricao_destinatario.length()); System.out.println("Inscricao: " +
			 * inscricao_destinatario);
			 */

			// String produto = separados[0].toString();
			String produto = tratamentoDados.tratar("BC", "-");
			produto = produto.replaceAll("[^a-zA-Z ]", "");
			produto = produto.substring(5, produto.length());
			// System.out.println("Produto: " + produto);

			String dados_valorados = tratamentoDados.tratar("BC", "DADOS");
			dados_valorados = dados_valorados.replaceAll(" ", "-");
			// dados_valorados = dados_valorados.replaceAll(",", "*");
			// dados_valorados = dados_valorados.replaceAll(" ", ",");

			// System.out.println(dados_valorados);
			TratarDados tratamentoDadosQuantidade = new TratarDados(dados_valorados);
			// String dados_valorados_dividos[] = dados_valorados.split(" ");

			// String quantidade = separados[5].toString();
			// String quantidade = dados_valorados_dividos[7];
			String teste_procura_quantidade = tratamentoDadosQuantidade.tratar("-SC-", "-");
			String quantidade = null;
			String quantidade_sem_formatacao = null;
			String unidade = null;
			// System.out.println("Procuradando: " + teste_procura_quantidade + "fim");
			if (teste_procura_quantidade.length() < 2 || teste_procura_quantidade.equals("")
					|| teste_procura_quantidade.equals(" ") || teste_procura_quantidade == null) {
				teste_procura_quantidade = tratamentoDadosQuantidade.tratar("-KG-", "-");
				// System.out.println("Procuradando: " + teste_procura_quantidade + "fim");

				if (teste_procura_quantidade.length() < 2 || teste_procura_quantidade.equals("")
						|| teste_procura_quantidade.equals(" ") || teste_procura_quantidade == null) {
					teste_procura_quantidade = tratamentoDadosQuantidade.tratar(" TON", "  ");
					// System.out.println("Procuradando: " + teste_procura_quantidade + "fim");

					if (teste_procura_quantidade.equals("") || teste_procura_quantidade.equals(" ")
							|| teste_procura_quantidade == null) {
						// System.out.print("Nenhum especie de unidade encontrada");
						teste_procura_quantidade = tratamentoDadosQuantidade.tratar(" CB", "  ");
						if (teste_procura_quantidade.equals("") || teste_procura_quantidade.equals(" ")
								|| teste_procura_quantidade == null) {
							// System.out.print("Nenhum especie de unidade encontrada");

						} else {
							quantidade = teste_procura_quantidade;
							quantidade_sem_formatacao = quantidade;
							unidade = "CB";
						}

					} else {
						quantidade = teste_procura_quantidade;
						quantidade_sem_formatacao = quantidade;

						unidade = "TON";
					}

				} else {
					quantidade = teste_procura_quantidade;
					quantidade_sem_formatacao = quantidade;

					unidade = "KQ";
				}

			} else {
				quantidade = teste_procura_quantidade;
				quantidade_sem_formatacao = quantidade;

				unidade = "SC";

			}

			String unidade_quantidade[] = quantidade.split(" ");
			// quantidade = unidade_quantidade[1];
			// quantidade_sem_formatacao = quantidade;

			// System.out.println("Quantidade: " + quantidade);

			// String unidade = separados[4].toString();
			// String unidade = dados_valorados_dividos[6].toString();
			// String unidade = unidade_quantidade[0];
			// System.out.println(unidade);
			if (unidade.equals("SC") || unidade.equals("sc") || unidade.equals(" SC ")) {
				// System.out.println("Tentando formatar quantidade em SC para KG");
				quantidade = quantidade.replaceAll("\\.", "");
				quantidade = quantidade.replaceAll(",", ".");
				// System.out.println("Quantidade formatada: " + quantidade);
				try {

					double quant = Double.parseDouble(quantidade);
					// System.out.println("quantidade inteira: " + quant);
					quant = quant * 60;
					quantidade = Double.toString(quant);
				} catch (Exception ev) {
					// System.out.println("Erro ao converter quantidade para inteiros " +
					// ev.getMessage());

				}

			}

			if (unidade.equals("TON") || unidade.equals("ton") || unidade.equals(" TON ")) {
				try {
					// quantidade = quantidade.replaceAll(".", "");
					quantidade = quantidade.replace(".", "");
					quantidade = quantidade.replace(",", ".");
					// double quant = Double.parseDouble(quantidade);
					BigDecimal quant = new BigDecimal(quantidade);
					// System.out.println("quantidade inteira: " + quant);
					quant.multiply(new BigDecimal("100.00"));
					quantidade = quant.toPlainString();
				} catch (Exception ev) {
					// System.out.println("Erro ao converter ton para double " + ev.getMessage());

				}

			}

			// System.out.println("Quantidade: " + quantidade);

			// String valor = lines[82].toString();
			// String valor = dados_valorados_dividos[8];
			// int linha = Arrays.toString(lines).indexOf("R$");
			teste_inscricao_remetente = teste_inscricao_remetente.replaceAll("12019000", "&");
			teste_inscricao_remetente = teste_inscricao_remetente.replaceAll("23021000", "&");
			teste_inscricao_remetente = teste_inscricao_remetente.replaceAll("14049010", "&");
			teste_inscricao_remetente = teste_inscricao_remetente.replaceAll("11042300", "&");
			teste_inscricao_remetente = teste_inscricao_remetente.replaceAll("12081000", "&");
			teste_inscricao_remetente = teste_inscricao_remetente.replaceAll("11031900", "&");

			teste_inscricao_remetente = teste_inscricao_remetente.replaceAll("KG " + quantidade_sem_formatacao,
					"INFOPRECO");
			teste_inscricao_remetente = teste_inscricao_remetente.replaceAll("SC " + quantidade_sem_formatacao,
					"INFOPRECO");
			teste_inscricao_remetente = teste_inscricao_remetente.replaceAll("TON " + quantidade_sem_formatacao,
					"INFOPRECO");

			// System.out.println(teste_inscricao_remetente);
			TratarDados tratarDadosValor = new TratarDados(teste_inscricao_remetente);
			String valor = tratarDadosValor.tratar("INFOPRECO", "&");

			// String valor = tratamentoDados.tratar("$" , "5");
			// String valor = Arrays.toString(lines).substring(linha, linha + 13);
			// valor = valor.replaceAll(",", "&");
			// System.out.println("Valor: " + valor);

			// String valor_split[] = valor.split(" ");
			// valor = valor_split[4];
			// valor = valor.substring(0, valor.indexOf(","));
			// System.out.println("Valor: " + valor);

			// System.out.println("Fim do processo");

			cadastro.setNfe(nfe);
			cadastro.setSerie(serie);
			cadastro.setNome_remetente(nome_remetente);
			cadastro.setInscricao_remetente(inscricao_remetente);
			cadastro.setProtocolo(protocolo);
			
			try {
				
				
				Date date = new SimpleDateFormat("dd/MM/yyyy").parse(data);
				
			cadastro.setData(date);
			}catch(Exception t) {
				JOptionPane.showMessageDialog(null, "Erro ao listar NFA do siare\nErro:  " + t.getMessage() + "\nConsulte o Administrador");
				return null;
			}
			cadastro.setNatureza(natureza);
			cadastro.setNome_destinatario(nome_destinatario);
			cadastro.setInscricao_destinatario(inscricao_destinatario);
			cadastro.setProduto(produto);
			cadastro.setQuantidade(quantidade);
			cadastro.setValor(valor);
			
			 return cadastro;

		}
		  
		
       public CadastroNFe tratar_nfe_interna (String lines[], File file) {
			CadastroNFe cadastro = new CadastroNFe();

      		cadastro.setCaminho_arquivo(file.getAbsolutePath());

    	   String tratar = Arrays.toString(lines);
           // tratar = tratar.replaceAll("\n", "*");
            System.out.println(tratar);
              TratarDados tratamentoDados  = new TratarDados(tratar );

              String nfe = tratamentoDados.tratar("Nº",",");
              //String nfe = null;
              String natureza = tratamentoDados.tratar("NATUREZA DA OPERAÇÃO, ", ",");
            /*
              if(procura_nfe.equals("") || procura_nfe == null)
              {
              	procura_nfe = tratamentoDados.tratar("NFA-e","REMESSA");
              	  if(procura_nfe.equals("") || procura_nfe == null)
		                {
              		  //procurar por outro tipo de nota
		                	procura_nfe = tratamentoDados.tratar("NFA-e","OUTRAS SAÍDAS");
		                	 if(procura_nfe.equals("") || procura_nfe == null)
				                {
              		  System.out.println("Número NFA não encontrado");
				                }
		                	 else
		                	 {
		                		 nfe = procura_nfe;
		                		 
		                		 
		                		 
		                		  natureza = "OUTRAS SAÍDAS";
		                		 
		                	 }
              		  
		                }
              	  else
              	  {
              		  nfe = procura_nfe;
              		  natureza = "REMESSA";

              	  }
              }
              else
              {
              	nfe = procura_nfe;
              	natureza = "VENDA";
              }
              nfe = nfe.replaceAll(",", "");
              		*/
             // String nfe = lines[27].toString().replaceAll("[^0-9]+", "");
              
              
             
             
              
              String serie = tratamentoDados.tratar("DANFE,", ",");
              //serie = serie.replaceAll("[^0-9]+", "");
              //String serie = lines[90].toString().replaceAll("[^0-9]+", "");
	               
             // String nome_remetente = lines[29].toString().replaceAll("[0-9]+", "");
              // nome_remetente = nome_remetente.replaceAll("[^a-zA-Z ]", "");
             // int linha_natureza = tratar.indexOf(tratar);
             
             /* String nome_remetente = null;
              if(natureza.equals("VENDA"))
              {
                  nome_remetente = tratamentoDados.tratar("VENDA,", ",");
                  nome_remetente = nome_remetente.replaceAll("[^a-zA-Z ]", "");

              }
              if(natureza.equals("REMESSA"))
              {
                  nome_remetente = tratamentoDados.tratar("REMESSA,", ",");
                  nome_remetente = nome_remetente.replaceAll("[^a-zA-Z ]", "");

              }
              if(natureza.equals("OUTRAS SAÍDAS"))
              {
                  nome_remetente = tratamentoDados.tratar("OUTRAS SAÍDAS,", ",");
                  nome_remetente = nome_remetente.replaceAll("[^a-zA-Z ]", "");

              }
              
              
              */
             
              
             String nome_remetente = tratamentoDados.tratar("SERIE ,", ",");
            
             /* 
              String inscricao_remetente  = lines[31].toString().replaceAll("[^0-9]+", "");
              inscricao_remetente =  inscricao_remetente.substring(4, inscricao_remetente.length());
              System.out.println("Inscricao: " + inscricao_remetente);
               */
              
              //String procura_inscricao_remetente = tratamentoDados.tratar("BRASIL", ",");
            
             
          
              //String array_protocolo[] = lines[95].split(" ");
              String procura_protocolo = tratamentoDados.tratar("CNPJ/CPF,", "DATA DE RECEBIMENTO");
              String array_protocolo[] = procura_protocolo.split(" ");
             // System.out.println("Possivel protocolo: "+array_protocolo[0] );

            //  System.out.println("Possivel protocolo: "+array_protocolo[1] );
             // System.out.println("Possivel protocolo: "+array_protocolo[2] );
              //System.out.println("Possivel protocolo: "+array_protocolo[3] );
              //System.out.println("Possivel protocolo: "+array_protocolo[4] );
              //System.out.println("Possivel protocolo: "+array_protocolo[5] );
              //System.out.println("Possivel protocolo: "+array_protocolo[6] );
              //System.out.println("Possivel protocolo: "+array_protocolo[7] );



              String protocolo = array_protocolo[2];
              String inscricao_remetente = array_protocolo[7] ;
              inscricao_remetente = inscricao_remetente.replaceAll(",", "");
             
              
              String nome_destinatario = tratamentoDados.tratar("Destinatário", "Valor");
           
              //String data = lines[33].toString();
             
              String data = tratamentoDados.tratar("Emissão", "Dest");
              data = data.replaceAll(" ", "");
             
              

              String procura = nome_destinatario + data + ",";
              //System.out.println("String de procura:" + procura);
              String procura_inscriao_destinatario = tratamentoDados.tratar(procura, ",");
            //  System.out.println("Procura inscricao: " + procura_inscriao_destinatario);
             String inscricao_destinatario = procura_inscriao_destinatario;
           //  String natureza = lines[28].toString();
          
              String valor = tratamentoDados.tratar("Total,", ", NÚMERO");
              valor = valor.replace(".", "");
             
              boolean procura_produto = tratar.contains("SOJA");
              String produto = null;
              if(procura_produto)
              {
              	produto = "SOJA";
              }
              else
              {
              	procura_produto = tratar.contains("SORGO");
              	if(procura_produto) {
              	produto = "SORGO";
              	}
              	else
              	{
              		procura_produto = tratar.contains("MILHO");
	                	if(procura_produto) {
	                	produto = "MILHO";
	                	}
	                	else
	                	{
	                		System.out.println("Nenhum Produto Escontrado");
	                	}
              		
              	}
              	
              }
              
              boolean procura_unidade = tratar.contains("KG");
              String unidade = null;
              if(procura_unidade)
              {
              	unidade = "KG";
              }
              else
              {
              	procura_unidade = tratar.contains("SC");
              	if(procura_unidade) {
              		unidade = "SC";
              	}
              	else
              	{
              		procura_unidade = tratar.contains("TON");
	                	if(procura_unidade) {
	                		unidade = "TON";
	                	}
	                	else
	                	{
	                		System.out.println("Nenhum Produto Escontrado");
	                	}
              		
              	}
              	
              }
              
              String quantidade = tratamentoDados.tratar("ISS, ", " ");
              quantidade = quantidade.replace(".", "");
              
              System.out.println("Numero nfe: " + nfe);

              System.out.println("Serie: " + serie );
              System.out.println("Remetente: " + nome_remetente);
              System.out.println("Inscrição do remetente: " + inscricao_remetente);
              
              System.out.println("Protocolo: " + protocolo);
              System.out.println("Data: " + data );
              System.out.println("Natureza: " + natureza);

              System.out.println("Nome do Destinatario: " + nome_destinatario);
              
             System.out.println("Inscricao Destinatario: "+ inscricao_destinatario);
          
             System.out.println("Produto: "+ produto);
             System.out.println("Unidade: "+ unidade);
             System.out.println("Quantidade: " + quantidade);
             System.out.println("Valor: " + valor);
             

  		
             
  			cadastro.setNfe(nfe);
  			cadastro.setSerie(serie);
  			cadastro.setNome_remetente(nome_remetente);
  			cadastro.setInscricao_remetente(inscricao_remetente);
  			cadastro.setProtocolo(protocolo);
  			try {
					
					
					Date date = new SimpleDateFormat("dd/MM/yyyy").parse(data);
					
				cadastro.setData(date);
				}catch(Exception t) {
					JOptionPane.showMessageDialog(null, "Erro ao listar NF-e interna sucess\nErro:  " + t.getMessage() + "\nConsulte o Administrador");
					
				}
  			cadastro.setNatureza(natureza);
  			cadastro.setNome_destinatario(nome_destinatario);
  			cadastro.setInscricao_destinatario(inscricao_destinatario);
  			cadastro.setProduto(produto);
  			cadastro.setQuantidade(quantidade);
  			cadastro.setValor(valor);
  			
			 return cadastro;

		}
       
       
       
       public CadastroNFe tratar_nfe_interna_siacon (String lines[], File file) {
			CadastroNFe cadastro = new CadastroNFe();

     		cadastro.setCaminho_arquivo(file.getAbsolutePath());

   	   String tratar = Arrays.toString(lines);
          // tratar = tratar.replaceAll("\n", "*");
           System.out.println(tratar);
             TratarDados tratamentoDados  = new TratarDados(tratar );

             String nfe = tratamentoDados.tratar("Nº "," ").replaceAll("[^0-9]", "");
             //String nfe = null;
             String natureza = tratamentoDados.tratar("NATUREZA DA OPERAÇÃO, ", ",");
             
             String serie = tratamentoDados.tratar("SÉRIE ", ",");
           
            String nome_remetente = tratamentoDados.tratar("RECEBEMOS DE ", "-");
           
             String protocolo = tratamentoDados.tratar("PROT. DE AUTORIZAÇÃO ", " ");
            
             
             
            
          
             
             
             String inscricao_remetente = tratamentoDados.tratar("Simples Nacional,", "SUBST").replaceAll("[^0-9]", "");

             
             String nome_destinatario = tratamentoDados.tratar("DESTINATÁRIO/REMETENTE, ", ",");

             //String data = lines[33].toString();
            
             String data = tratamentoDados.tratar("DATA DE EMISSÃO: ", ",");
             

            //inscricao do destinatario
             
             String linha_com_info_destinatario = tratamentoDados.tratar("DESTINATÁRIO/REMETENTE, ", "HORA");
             
             //quebrar string
             String string_quebrada[] = linha_com_info_destinatario.split(",");
             
             
         
             String inscricao_destinatario = string_quebrada[5].replaceAll("[^0-9]", "");

             
             String valor = tratamentoDados.tratar("VALOR TOTAL DA NOTA, ", "TRANSPORTADOR");
             valor = valor.trim();
             valor = valor.substring(0, valor.length() - 1);

             
             boolean procura_produto = tratar.contains("SOJA");
             String produto = null;
             if(procura_produto)
             {
             	produto = "SOJA";
             }
             else
             {
             	procura_produto = tratar.contains("SORGO");
             	if(procura_produto) {
             	produto = "SORGO";
             	}
             	else
             	{
             		procura_produto = tratar.contains("MILHO");
	                	if(procura_produto) {
	                	produto = "MILHO";
	                	}
	                	else
	                	{
	                		System.out.println("Nenhum Produto Escontrado");
	                	}
             		
             	}
             	
             }
             
             boolean procura_unidade = tratar.contains("KG");
             String unidade = null;
             if(procura_unidade)
             {
             	unidade = "KG";
             }
             else
             {
             	procura_unidade = tratar.contains("SC");
             	if(procura_unidade) {
             		unidade = "SC";
             	}
             	else
             	{
             		procura_unidade = tratar.contains("TON");
	                	if(procura_unidade) {
	                		unidade = "TON";
	                	}
	                	else
	                	{
	                		System.out.println("Nenhum Produto Escontrado");
	                	}
             		
             	}
             	
             }
             
             String quantidade = tratamentoDados.tratar(unidade + " ", " ");

             
             System.out.println("Numero nfe: " + nfe);

             System.out.println("Serie: " + serie );
             System.out.println("Remetente: " + nome_remetente);
             System.out.println("Inscrição do remetente: " + inscricao_remetente);
             
             System.out.println("Protocolo: " + protocolo);
             System.out.println("Data: " + data );
             System.out.println("Natureza: " + natureza);

             System.out.println("Nome do Destinatario: " + nome_destinatario);
             
            System.out.println("Inscricao Destinatario: "+ inscricao_destinatario);
         
            System.out.println("Produto: "+ produto);
            System.out.println("Unidade: "+ unidade);
            System.out.println("Quantidade: " + quantidade);
            System.out.println("Valor: " + valor);
            

 		
            
 			cadastro.setNfe(nfe);
 			cadastro.setSerie(serie);
 			cadastro.setNome_remetente(nome_remetente);
 			cadastro.setInscricao_remetente(inscricao_remetente);
 			cadastro.setProtocolo(protocolo);
 			try {
					
					
					Date date = new SimpleDateFormat("dd/MM/yyyy").parse(data);
					
				cadastro.setData(date);
				}catch(Exception t) {
					JOptionPane.showMessageDialog(null, "Erro ao ler nota fiscal interna siacon\nErro:  " + t.getMessage() + "\nConsulte o Administrador");
					
				}
 			cadastro.setNatureza(natureza);
 			cadastro.setNome_destinatario(nome_destinatario);
 			cadastro.setInscricao_destinatario(inscricao_destinatario);
 			cadastro.setProduto(produto);
 			cadastro.setQuantidade(quantidade);
 			cadastro.setValor(valor);
 			
			 return cadastro;

		}
		  		

	public CadastroNFe getNotaFiscal(String codigo) {

		ArrayList<String> listaLocal = new ArrayList<>();
		File file = null;
		MyFileVisitor arquivos = new MyFileVisitor();
		Path source = Paths.get(configs_globais.getServidorUnidade() + "E-Contract\\arquivos\\");
		System.out.println("Pasta procura notas_fiscais: " + source);
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
					String result = tratar.tratar("NFA-", ".pdf");
					System.out.println("Result: " + result);
					System.out.println("Codigo: " + codigo);

					if (result != null && result.length() > 8) {
						if (result.trim().equals(codigo.trim())) {
							System.out.println("encontrato");
							file = new File(listaLocal.get(i).toString());

							break;
						}

					}
				} catch (Exception e) {

				}

			}
		}

		return filtrar(file);

	}

	public void setPai(JDialog _pai) {
		this.tela_pai = _pai;

	}

	private void getDadosGlobais() {
		// gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		GerenciadorLog = dados.getGerenciadorLog();
		configs_globais = dados.getConfigs_globais();

		// usuario logado
		login = dados.getLogin();

	}
}
