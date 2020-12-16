package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;

import cadastros.CadastroLogin;
import manipular.ConfiguracoesGlobais;
import manipular.ManipularTxt;
import outros.DadosGlobais;
import outros.GetData;
import outros.TratarDados;
import tratamento_proprio.Log;
import views_personalizadas.TelaNotificacao;

import java.awt.event.ActionListener;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.awt.event.ActionEvent;

public class TelaBaixarNotas extends JDialog {

	private final JPanel painelPrincipal = new JPanel();
	private TelaNotificacao telaNot;
	
	private String num_registros_global;
	private String num_registros_cancelados_global;
	private boolean n_prosseguir = true;
	
	private  String diretorio_todas_as_notas = "";

	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private ArrayList<String> lista_nomes_arquivos = new ArrayList<>();

	
	public TelaBaixarNotas() {
	
		getDadosGlobais();
	
		diretorio_todas_as_notas = configs_globais.getServidorUnidade() + "E-Contract\\arquivos\\notas_baixadas";
	    System.out.println("Diretorio todas as notas: " + diretorio_todas_as_notas);
	    
	    getListaNomeArquivos();
	    //já recupera a lista de nomes de arquivos dentro do diretorio principal
	 
		
		setModal(true);

		TelaBaixarNotas isto = this;
		
		setResizable(false);
		setTitle("E-Contract - Notas - Download");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 679, 508);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);
		
		JButton btnBaixarNotas = new JButton("Baixar Notas");
		btnBaixarNotas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			    getListaNomeArquivos();

			    
			    int ano = 2020;
			   
			      for(int mes = 1; mes <= 12; mes++) {	
			    	  for(int dia = 1; dia <= 21; dia += 10) {
			    		
			    		 
			    		  if(dia == 21) {
			    			  
			    			  if(mes == 2) {
					    		  PesquisaParalela pesquisar = new PesquisaParalela(dia, mes ,ano, 28, mes , ano);

			    			  }
			    			  else {
			    				  //verifica se o mes e de 30 dias
			    				  //4 meses no ano com 30 dias (abril(4), junho(6), setembro(9) e novembro(11)).
			    				 if(mes == 4 || mes == 6 || mes == 9 || mes == 11) {
						    		  PesquisaParalela pesquisar = new PesquisaParalela(dia, mes ,ano, 30, mes , ano);

			    				 }else {
						    		  PesquisaParalela pesquisar = new PesquisaParalela(dia, mes ,ano, 31, mes , ano);

			    				 }
			    				  
			    			  }

			    		  }else {
				    		  PesquisaParalela pesquisar = new PesquisaParalela(dia, mes ,ano, dia+9, mes , ano);

			    		  }
			    		  
			     
			    	  }

			    }
			    
                  
			
			} 
			
		});
		btnBaixarNotas.setBounds(177, 75, 231, 122);
		painelPrincipal.add(btnBaixarNotas);
		
	
        
		this.setLocationRelativeTo(null);

		this.setVisible(true);
	
	}
	
	public void abrirPagina(String s_dataInicio, String s_dataFim) {
		
		boolean continuar = false;
		    ProfilesIni profileIni = new ProfilesIni();
	        FirefoxProfile profile = profileIni.getProfile("notas_siare");
	        FirefoxOptions options = new FirefoxOptions();
	        options.setProfile(profile);
	        WebDriver driver = null;

         for(int i = 0; i < 10; i++) {
        	 //dez tentativas de conexao
        	 System.out.println("teste de conexao: " + i);
          try {	
  	         driver = new FirefoxDriver(options);

	        driver.get("https://www2.fazenda.mg.gov.br/sol/");

	        continuar = true;
	        break;
          }catch(Exception e) {
           	System.out.println("erro, sem conexão com o siare!");
           	continuar = false;
	        driver.close();

          }
         }
          
          if(continuar) {
		   continuar = false;
		   
		   //verifica se tem erro de seguranca
		   if(getErroSeguranca(driver) == true) {
			   //tem erros de seguranca, tem que recarregar a pagina
			   
			   
			   
		   }else {
				 fazerLogin(driver, "","",""); 
				 //ir para a pagina de consulta
				 try {
	      	        	Thread.sleep(1000);
	      	        } catch (InterruptedException e) {
	      	        	// TODO Auto-generated catch block
	      	        	e.printStackTrace();
	      	        }
				 
	              driver.get("https://www2.fazenda.mg.gov.br/sol/ctrl/SOL/NFAE/LISTA_013?ACAO=VISUALIZAR");
	              if (getErroSeguranca(driver) == true) {
	            	  //tem erro na pagina de consulta, parar o processo e recarregar a pagina
	            	  
	            	  
	              }else {
	            	  try {
	      	        	Thread.sleep(1000);
	      	        } catch (InterruptedException e) {
	      	        	// TODO Auto-generated catch block
	      	        	e.printStackTrace();
	      	        }
	            	  //não possui erros, fazer a pesquisa
	      	        fazerPesquisa(driver, s_dataInicio, s_dataFim);
	      	        
	      	      if (getErroSeguranca(driver) == true) {
	            	  //tem erro na pagina de consulta, parar o processo e recarregar a pagina
	            	  
	            	  
	              }else {
	            	  boolean prosseguir_pesquisa = false;
	            	  //pesquisa foi concluida, pegar numero de paginas
	            		int num_pags = getNumeroPaginas(driver);
	            		  if(num_pags == 0) {
	            			  if(getErroSeguranca(driver) == true) {
	            			 
	            			  }else {
	            				  
	            			  try {
								Thread.sleep(3000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	            			  System.out.println("2 tentativa de busca de numero de paginas");
	            			  num_pags = getNumeroPaginas(driver);
	            			  if(num_pags > 0) {
	            				  prosseguir_pesquisa = true;
	            			  }else {
	            				  
	            				  try {
										Thread.sleep(3000);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
		            			  System.out.println("3 tentativa de busca de numero de paginas");

			            			  num_pags = getNumeroPaginas(driver);
			            			  if(num_pags > 0) {
			            				  prosseguir_pesquisa = true;
			            			  }
			            			  else {
			            				  
			            				  System.out.println("4 tentativa de busca de numero de paginas");
				            			  try {
												Thread.sleep(3000);
											} catch (InterruptedException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
				            			  num_pags = getNumeroPaginas(driver);
				            			  if(num_pags > 0) {
				            				  prosseguir_pesquisa = true;
				            			  }
			            			  }
	            				  
	            		
	            			  }
	            			  } 

	          	        }else {
	          	        	prosseguir_pesquisa = true;
	          	        }
	          	        	
	          	          if(prosseguir_pesquisa) {	
	          	        	//pegar os botoes
	          	        	int pagina_atual = -1;
	          	        	  
	          	        	 for(int i = 1; i <= num_pags; i++) {
	          	        		pagina_atual = verificarPaginaAtual(driver);
	          	            	System.out.print("pagina atual: " + pagina_atual);
	          	        		
	          		        	//pega os botoes de abrir para salvar
	          		        	List<WebElement> botoes = driver.findElements(By.name("lnkImprimir"));
	          	               if(botoes != null && botoes.size() > 0) { 
	          		        	for(WebElement botaosalvar : botoes) {
	          		        		
	          		        		//pega o elemento pai desse componente, que é um tr
	          		        		WebElement pai_botao = (WebElement) ((JavascriptExecutor) driver).executeScript(
	                                        "return arguments[0].parentNode;", botaosalvar);
	          		        		
	          		        		WebElement avo_botao = (WebElement) ((JavascriptExecutor) driver).executeScript(
	                                        "return arguments[0].parentNode;", pai_botao);
	          		        		
	          		        		System.out.println("O tipo é:" + avo_botao.getTagName());
	          		        		// pegar todos os td dentro de tr
		          		        	List<WebElement> td_s = avo_botao.findElements(By.tagName("td"));
		          		        	//imprime todos o conteudo de td
		          		        	String id_nota = "";
		          		        	for(WebElement td : td_s) {
		          		        		//System.out.println("Conteudo do td e: " + td.getText());
		          		        		if(td.getText().length() == 11) {
		          		        			//é o número da nota fiscal
		          		        			id_nota = td.getText();
		          		        			break;
		          		        			
		          		        		}
		          		        		
		          		        	}
		          		        	boolean esperar = true;
		          		        	boolean autorizarBaixar = false;
		          		        	System.out.println("id da nota fiscal e: " + id_nota);
		          		        	
		          		        	if(!lista_nomes_arquivos.isEmpty()) {
		          		        		for(String nome_arquivo : lista_nomes_arquivos) {
		          		        		if(nome_arquivo.equals(id_nota)){
		          		        			//nota ja foi baixada
		          		        			System.out.println("Essa nota fiscal ja foi baixa!");
		          		        			autorizarBaixar = false;
		          		        			esperar = false;
		          		        			break;
		          		        			
		          		        		}else {
		          		        			//nota nao foi baixada, baixar;
		          		        			autorizarBaixar = true;
		          		        		}
		          		        		
		          		        	}
		          		        		if(autorizarBaixar)
			          		        	{
			          		        		botaosalvar.click();

			          		        	}
		          		        	}else {
		          		        		botaosalvar.click();

		          		        	}
	          		        	
		          		        	
	          		        
	          		        	if(esperar) {
	          		        		try {
	          		        			Thread.sleep(5000);
	          		        			} catch (InterruptedException e) {
	          		        				// TODO Auto-generated catch block
	          		        				e.printStackTrace();
	          		        			}
	          		            }else {
	          		            	//prosseguir sem esperar
	          		            }
	          			        
	          		        	}//fim do for que percorre os pdfs
	          		        
	          		        		//ir para a proxima pagina
	          		        boolean prosseguir_proxima_pagina = false;
	          	              if (i < num_pags) {
	          	            	if(irProximaPagina(driver, pagina_atual)) {
	          	            		
	          	            	}else
	          	            	{
	          	            		break;
	          	            	}
	          	            	
	          	              }else {
	          	            	//fim da ultima pagina, pode encerrar a pagina
	          						
	          					}
	          	               }
	          	               else {
	          	            	   break;
	          	               }
	          	     	
	          	             } //fim do for que percorre as paginas 
	          	          }//fim prosseguir_pesquisa
	          		        
	          	        	
	          	        }
	              }
	        
	        
	       
	              }
	      
		   }
          }
          
         
	
	public boolean irProximaPagina(WebDriver driver, int pagina_atual) {
		  //vai para a proxima pagina
    	boolean retorno = false;  
		
    	  WebElement btnProximaPagina = driver.findElement(By.name("linkProximo"));
        	btnProximaPagina.click();
        	
        	//espera enquanto a pagina carrega 
        	
        	try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	
        	 if(getErroSeguranca(driver) == true) {
        		 //a proxima pagina falhou
  			 
        		 //recarrege a pagina
        		recarregarPagina(driver);
        		//espera um segundo
        		try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		
        		if(verificarPaginaAtual(driver) > pagina_atual + 1) {
        			//apos recarregar a pagina, sabemos que estamos na pagina certa, pode prosseguir 
        			retorno  = true;
        		}else {
        			//ainda estamos na pagina errado, pela ultima vez, verificar se tem erros, se nao, tentar ir pra proxima pagina novamente
        			 if(getErroSeguranca(driver) == true) {
        			   //tem erros, pare a operacao
        			 }else {
        				 irProximaPagina(driver, pagina_atual);
        			 }
        		}
        		
        		 
        		 
			  }else {
				  //a pagina avancou
				  if(verificarPaginaAtual(driver) == pagina_atual + 1)
				    retorno = true;
				  
			  }
        	 
      return retorno;
		
	}
	
	public int verificarPaginaAtual(WebDriver driver) {
		  WebElement num_registros = driver.findElement(By.xpath("//*[@class='currentPage']"));
	        
	        System.out.println("pagina atual:  " + num_registros.getText());
	        try {
	        return Integer.parseInt(num_registros.getText());
	        }catch(NumberFormatException e ) {
	        	return -1;
	        }
		
		
	}
	
	public void recarregarPagina(WebDriver driver) {
		driver.navigate().refresh();		
	}
     
	
	public boolean getErroSeguranca(WebDriver driver) {
		try {
		   WebElement procura_caixa_erro = driver.findElement(By.id("errorShortDescText"));
	     if(procura_caixa_erro != null ) {
	    	 //a pagina nao carregou
	    	 return true;
	     }else {
	    	 return false;
	     }
		}catch(NoSuchElementException f) {
			return false;
		}
	}
	
	public void fazerLogin(WebDriver driver, String s_id, String s_cpf, String s_senha) {
		
	    String url_ante_click = driver.getCurrentUrl();
        System.out.println("url pos click: " + url_ante_click);
 
        WebElement comboboxElement = driver.findElement(By.name("cmbDominio"));
        Select combobox = new Select(comboboxElement);
        combobox.selectByVisibleText("Produtor Rural");


        WebElement id_usuario = driver.findElement(By.name("dominio"));
	
        WebElement cpf = driver.findElement(By.name("login"));
        WebElement senha = driver.findElement(By.name("senhaAtual"));

        id_usuario.sendKeys("0026877020090");
        cpf.sendKeys("54271177849");
        senha.sendKeys("65857994");

        WebElement botaoEntrar = driver.findElement(By.name("Entrar"));
        botaoEntrar.click();
 
        String url_pos_click = driver.getCurrentUrl();
        System.out.println("url pos click: " + url_pos_click);
		
	}

     
	public void fazerPesquisa(WebDriver driver, String s_dataInicio, String s_dataFim ){
     
		   //configuracoes de pesquisa
       /* WebElement comboboxTipoDoc = driver.findElement(By.name("cmbTipoDocumento"));
        Select comboboxTipoDc = new Select(comboboxTipoDoc);
        comboboxTipoDc.selectByVisibleText("Todos");
        */
        WebElement botaoTipoNFA = driver.findElement(By.xpath("//input[@value='3']"));
        botaoTipoNFA.click();
        

        WebElement dataInicio = driver.findElement(By.name("dataInicio"));
        dataInicio.sendKeys(s_dataInicio);
 
 

        WebElement dataFim = driver.findElement(By.name("dataFim"));
       
        dataFim.sendKeys(s_dataFim);
 

       WebElement botaoPesquisar = driver.findElement(By.name("Pesquisar"));
       botaoPesquisar.click();
		
	}
	
	public int getNumeroRegistros(WebDriver driver) {
		
		 WebElement num_registros = driver.findElement(By.xpath("//*[@class='col-md-5 text-right']"));
	        TratarDados tratar = new TratarDados(num_registros.getText());
	         String num_registros_ = tratar.tratar("", " Registros");
	         System.out.println("numero de notas encontradas: " + num_registros_ );
		     try {
		    	 
		    	 return Integer.parseInt(num_registros_);
		     }catch(Exception e) {
		    	 return -1;
		     }
	}
	
	public int getNumeroPaginas(WebDriver driver) {
		
		  WebElement num_registros = driver.findElement(By.xpath("//*[@class='col-md-5 text-right']"));
	        TratarDados tratar = new TratarDados(num_registros.getText());
	         String num_pags = tratar.tratar("de", "Ir").replaceAll("[^0-9]+", "");
	        System.out.println("numero de paginas: " + num_pags);
	        try {
	        return Integer.parseInt(num_pags);
	        }catch(NumberFormatException e ) {
	        	return -1;
	        }
	}
	
	
	public WebElement fluentWait(final By locator, WebDriver driver, int tempo1, int tempo2 ) {
	    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
	            .withTimeout(tempo1, TimeUnit.SECONDS)
	            .pollingEvery(tempo2, TimeUnit.SECONDS)
	            .ignoring(NoSuchElementException.class);

	    WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
	        public WebElement apply(WebDriver driver) {
	            return driver.findElement(locator);
	        }
	    });

	    return  foo;
	};
	

	public void getDadosGlobais() {
		//gerenciador de log
				DadosGlobais dados = DadosGlobais.getInstance();
				 GerenciadorLog = dados.getGerenciadorLog();
				 configs_globais = dados.getConfigs_globais();
				 //usuario logado
				  login = dados.getLogin();
		
	}
	
      public void getListaNomeArquivos() {
	   ManipularTxt manipular = new ManipularTxt();
	    ArrayList<String> lista_nomes_arquivos_completos = manipular.getListaNomeArquivos(diretorio_todas_as_notas);
	    
	    for(String nome_arquivos : lista_nomes_arquivos_completos) {
	    	lista_nomes_arquivos.add(nome_arquivos.replace("NFA-", "").replace(".pdf", ""));
	    }
	    
	   
	    
      }
	
      
      
      class PesquisaParalela implements Runnable {

    	  private String sdia_inicio, smes_inicio, sano_inicio;
    	  private String sdia_fim, smes_fim, sano_fim;
   		  private String data_completa_inicio, data_completa_fim;

    	  
    	  public PesquisaParalela(int dia_inicio, int mes_inicio, int ano_inicio, int dia_fim, int mes_fim, int ano_fim) {
    		 
    		  if(dia_inicio <= 9) {
    			  sdia_inicio = "0" + dia_inicio;
    		  }else
    			  sdia_inicio = Integer.toString(dia_inicio);
    		  
    		  if(mes_inicio <= 9) {
    			  smes_inicio = "0" + mes_inicio;
    		  }else
    			  smes_inicio = Integer.toString(mes_inicio);
    		  
    		  
    		  sano_inicio = Integer.toString(ano_inicio);
    		  
    		   data_completa_inicio = sdia_inicio + "/" + smes_inicio + "/" + sano_inicio;
    		 // System.out.println("data formatada de inicio recebida: " + data_completa_inicio);
    		  
    		  
    		  if(dia_fim <= 9) {
    			  sdia_fim = "0" + dia_fim;
    		  }else
    			  sdia_fim = Integer.toString(dia_fim);
    		  
    		  if(mes_fim <= 9) {
    			  smes_fim = "0" + mes_fim;
    		  }else
    			  smes_fim= Integer.toString(mes_fim);
    		  
    		  
    		  sano_fim = Integer.toString(ano_fim);
    		  
     		
    		   data_completa_fim = sdia_fim + "/" + smes_fim+ "/" + sano_fim;
    		//  System.out.println("data formatada de fim recebida: " + data_completa_fim);
    	  
    		  System.out.println("Pesquisa de :" + data_completa_inicio + " até " + data_completa_fim);
    		  
      }

		@Override
		public void run() {
            abrirPagina("","");  

			
		}
    	  
    	  
      }
      
}
