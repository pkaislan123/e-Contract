package outros;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;

import cadastros.CadastroAviso;
import cadastros.CadastroCliente;
import cadastros.CadastroLogin;
import gui.TelaMain;
import gui.TelaPrincipal;
import manipular.ConfiguracoesGlobais;
import manipular.ManipularTxt;
import tratamento_proprio.Log;
import views_personalizadas.TelaNotificacao;

public class BaixarNotasFiscais {
	
	private  String diretorio_todas_as_notas = "";

	private Log GerenciadorLog;
    private TelaMain telaPrincipal;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private ArrayList<String> lista_nomes_arquivos = new ArrayList<>();
	private ArrayList<Thread> threads_ociosas =  new ArrayList<>();
	private ArrayList<Thread> threads_online =  new ArrayList<>();
	private int contador_global_threads = 0;
	private CadastroCliente cadastro;
	private String natureza;
	private String servidor_unidade;
	private ArrayList<WebDriver> drivers = new ArrayList<>();
	
	

	
	public BaixarNotasFiscais(CadastroCliente cliente_pesquisar, String s_natureza) {
		getDadosGlobais();
		this.natureza = s_natureza;
		this.cadastro = cliente_pesquisar;
		
		String nome_pasta = "";
		if(cliente_pesquisar.getTipo_pessoa() == 0) {
			//pessoa fisica
			nome_pasta = cliente_pesquisar.getNome_empresarial().toUpperCase().trim();
		}else {
			nome_pasta = cliente_pesquisar.getNome_fantaia().toUpperCase().trim();

		}
		
		diretorio_todas_as_notas = configs_globais.getServidorUnidade() + "E-Contract\\arquivos\\clientes\\" + nome_pasta + "\\NOTAS FISCAIS\\";
	    System.out.println("Diretorio todas as notas: " + diretorio_todas_as_notas);
	    
	    getListaNomeArquivos();
	    //já recupera a lista de nomes de arquivos dentro do diretorio principal
	 
	}

public void abrirPagina(String s_dataInicio, String s_dataFim, String natureza) {
		
		boolean continuar = false;
        System.setProperty("webdriver.gecko.driver", "C:\\geckodriver.exe");

		    ProfilesIni profileIni = new ProfilesIni();
	      //  FirefoxProfile profile = profileIni.getProfile("notas_siare");
	        FirefoxOptions options = new FirefoxOptions();
	       //options.setProfile(profile);
	           options.setHeadless(true);
	         options.addArguments("--headless");
	        
	        FirefoxProfile profile = new FirefoxProfile();
	         profile.setPreference("browser.download.folderList", 2);
	 	      profile.setPreference ("browser.download.dir", servidor_unidade + "\\E-Contract\\arquivos\\arquivos_comuns");
	 	     profile.setPreference("browser.download.useDownloadDir", true);
	 	    profile.setPreference("browser.download.viewableInternally.enabledTypes", "");
	 	   profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf");
	 	  profile.setPreference("pdfjs.disabled", true);  // disable the built-in PDF viewer
	  
	 	     WebDriver driver = null;
	 	     options.setProfile(profile);

         for(int i = 0; i < 10; i++) {
        	 //dez tentativas de conexao
        	
          try {	
  	        // driver = new FirefoxDriver(options);
        	  driver = new FirefoxDriver(options);
           	 System.out.println("teste de conexao: " + i);
	        driver.get("https://www2.fazenda.mg.gov.br/sol/");
	        Thread.sleep(2000);
	        continuar = true;
	        drivers.add(driver);

	        break;
          }catch(Exception e) {
           	System.out.println("erro, sem conexão com o siare!");

           	continuar = false;

           	String winHandleBefore = driver.getWindowHandle();

           	
           	for(String winHandle : driver.getWindowHandles()){
           	    driver.switchTo().window(winHandle);
           	}

           	
           	driver.close();

           	driver.switchTo().window(winHandleBefore);
           	driver.close();
               i++;
          }
         }
        
	        
         if(continuar) {

        	 //realizar login
        	  //verifica se tem erro de seguranca
  		   if(getErroSeguranca(driver) == true) {
  			   //tem erros de seguranca, tem que recarregar a pagina
  			   
  			   
  			   
  		   }else {
  			   boolean login_concluido = false;
           	   boolean prosseguir_pesquisa = false;
    
  				 fazerLogin(driver, "","",""); 
  				 //ir para a pagina de consulta
  				 try {
	      	        	Thread.sleep(1000);
	      	        } catch (InterruptedException e) {
	      	        	// TODO Auto-generated catch block
	      	        	e.printStackTrace();
	      	        }
  				 
  	            
  	              if (getErroSeguranca(driver) == true) {
  	            	  //tem erro na pagina de consulta, parar o processo e recarregar a pagina
  	            	  
  	            	  
  	              }else {
  	            	  //não ha erros, mas verificar se o login foi concluido
  	            	if(verificarLoginConcluido(driver)) {
  	            		//login efetuado
  	            		login_concluido = true;
  	            	}else {
  	            		//login nao efetuado;
  	            		//2 tentativa de login
  	            		 fazerLogin(driver, "","",""); 
  	            		 try {
  	  	      	        	Thread.sleep(1000);
  	  	      	        } catch (InterruptedException e) {
  	  	      	        	// TODO Auto-generated catch block
  	  	      	        	e.printStackTrace();
  	  	      	        }
  	            		if(verificarLoginConcluido(driver)) {
  	  	            		login_concluido = true;

  	            		}else {
  	  	            		login_concluido = false;

  	            		}
  	            	}
  	              }
  	              
  	              if(login_concluido) {
  	            	  //login concluido, prosseguir com pesquisa
  	            	  driver.get("https://www2.fazenda.mg.gov.br/sol/ctrl/SOL/NFAE/LISTA_013?ACAO=VISUALIZAR");
  	            	 try {
   	      	        	Thread.sleep(1000);
   	      	        } catch (InterruptedException e) {
   	      	        	// TODO Auto-generated catch block
   	      	        	e.printStackTrace();
   	      	        }
 	            	  driver.get("https://www2.fazenda.mg.gov.br/sol/ctrl/SOL/NFAE/LISTA_013?ACAO=VISUALIZAR");
 	            	 try {
    	      	        	Thread.sleep(1000);
    	      	        } catch (InterruptedException e) {
    	      	        	// TODO Auto-generated catch block
    	      	        	e.printStackTrace();
    	      	        }
  	      	        fazerPesquisa(driver, s_dataInicio, s_dataFim, natureza);
  	      	        
  	      	      if (getErroSeguranca(driver) == true) {
  	            	  //tem erro na pagina de consulta, parar o processo e recarregar a pagina
  	            	  
  	            	  
  	              }else {
  	                 //a pesquisa foi concluida, pegar numero de paginas
  	            	  int num_pags = pesquisarPorPaginas(driver); 
  	            	  if(num_pags > 0) {
  	            		  //numero de paginas maior que 0, significa que ha botoes
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
			          		        		
			          		        			renomearEmBackground thread_renomear = new renomearEmBackground(id_nota, diretorio_todas_as_notas);
                                                Thread rodar = new Thread(thread_renomear);
                                                rodar.start();
			          		        			
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
	          						driver.close();
	          					}
	          	               }
	          	               else {
	          	            	   break;
	          	               }
	          	     	
	          	             } //fim do for que percorre as paginas 
	          	      
  	            		  
  	            	  }else {
  	            		  //registros nao encontrado, tela ja foi fechada
  	            	  }
  	            	  
  	              }
  	          	        
  	              }//fim de login concluido
  	              else {
  	            	  //login nao foi concluido, encerrando
  	            	  driver.close();
  	              }
  	              
  	              
  		   }
  	            	 
        	 
         }else {
        	 //sem conexao com a internet, cancelar
          driver.close();
         }
         
         
         
}
/*
          if(continuar) {
		   
		   //verifica se tem erro de seguranca
		   if(getErroSeguranca(driver) == true) {
			   //tem erros de seguranca, tem que recarregar a pagina
			   
			   
			   
		   }else {
			   boolean login_concluido = false;
         	   boolean prosseguir_pesquisa = false;
  
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
	            	  
	              }
	            	 
	            	  //não possui erros, fazer a pesquisa
		   	  
	            if(login_concluido) {	
	            	 try {
		      	        	Thread.sleep(1000);
		      	        } catch (InterruptedException e) {
		      	        	// TODO Auto-generated catch block
		      	        	e.printStackTrace();
		      	        }
	            	 
	      	        fazerPesquisa(driver, s_dataInicio, s_dataFim, natureza);
	      	        
	      	      if (getErroSeguranca(driver) == true) {
	            	  //tem erro na pagina de consulta, parar o processo e recarregar a pagina
	            	  
	            	  
	              }else {
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
				            			  }else {
				            				  prosseguir_pesquisa = false;
				            				 	String pai = driver.getWindowHandle();

				            		           	
				            		           	for(String filho : driver.getWindowHandles()){
				            		           		if(!filho.equals(pai)) {
				            		           	     driver.switchTo().window(filho);
				            		           		 driver.close();
				            		           		}
				            		           	}

				            		           	
				            		           

				            		           	driver.switchTo().window(pai);
				            		           	driver.close();
				            				  
				            			  }
			            			  }
	            				  
	            		
	            			  }
	            			  } 

	          	        }else {
	          	        	prosseguir_pesquisa = true;
	          	        }
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
	          						driver.close();
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
	      
		   }
          }
          */


   public int pesquisarPorPaginas(WebDriver driver) {
	   int retorno = -1;
	   
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
				  retorno = num_pags;
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
    				  retorno = num_pags;
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
        				  retorno = num_pags;
         			  }else {
        				  retorno = -1;
         				 	String pai = driver.getWindowHandle();

         		           	
         		           	for(String filho : driver.getWindowHandles()){
         		           		if(!filho.equals(pai)) {
         		           	     driver.switchTo().window(filho);
         		           		 driver.close();
         		           		}
         		           	}

         		           	
         		           

         		           	driver.switchTo().window(pai);
         		           	driver.close();
         				  
         			  }
     			  }
				  
		
			  }
			  } 

       }else {
			  retorno = num_pags;
       }
		  
		  return retorno;
		  
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
	
	
	public boolean verificarLoginConcluido(WebDriver driver) {
        try {
        	WebElement elemento = driver.findElement(By.name("login")); 
    		if( elemento == null){
    			return true;
    		}else {
    			return false;
    		}

        }catch(Exception e) {
        	
        	return true;
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

        System.out.println("id do usuario: " + cadastro.getIdentificacao_sefaz());
        System.out.println("cpf do usuario " + cadastro.getCpf_responsavel());
        System.out.println("senha do usuario: " + cadastro.getSenha());

        
        id_usuario.sendKeys(cadastro.getIdentificacao_sefaz());
        cpf.sendKeys(cadastro.getCpf_responsavel());
        senha.sendKeys(cadastro.getSenha());

        WebElement botaoEntrar = driver.findElement(By.name("Entrar"));
        botaoEntrar.click();
 
        String url_pos_click = driver.getCurrentUrl();
        System.out.println("url pos click: " + url_pos_click);
		
	}

     
	public void fazerPesquisa(WebDriver driver, String s_dataInicio, String s_dataFim , String natureza){
     
		   //configuracoes de pesquisa
       /* WebElement comboboxTipoDoc = driver.findElement(By.name("cmbTipoDocumento"));
        Select comboboxTipoDc = new Select(comboboxTipoDoc);
        comboboxTipoDc.selectByVisibleText("Todos");
        */
		
		
		  WebElement comboboxSituacao = driver.findElement(By.name("situacao"));
        Select select_situacao = new Select(comboboxSituacao);
        select_situacao.selectByVisibleText("Impressa");
		
		 if(natureza.equals("VENDA")) {
		        WebElement comboboxNatureza = driver.findElement(By.name("tipoNatureza"));
		        Select select_c = new Select(comboboxNatureza);
		        select_c.selectByVisibleText("VENDA");
		        }
		 
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
				  servidor_unidade = configs_globais.getServidorUnidade();
					//telaprincipal
					telaPrincipal = dados.getTelaPrincipal();
		
	}
	
      public void getListaNomeArquivos() {
	   ManipularTxt manipular = new ManipularTxt();
	    ArrayList<String> lista_nomes_arquivos_completos = manipular.getListaNomeArquivos(diretorio_todas_as_notas);
	    
	    for(String nome_arquivos : lista_nomes_arquivos_completos) {
	    	lista_nomes_arquivos.add(nome_arquivos.replace("NFA-", "").replace(".pdf", ""));
	    }
	    
	    
	    for(String nome_arquivos : lista_nomes_arquivos) {
	    	   System.out.println("nome arquivo: " + nome_arquivos);
	    }
	
	    
      }
	
      
      
      class PesquisaParalela implements Runnable {

    	  private String sdia_inicio, smes_inicio, sano_inicio;
    	  private String sdia_fim, smes_fim, sano_fim;
   		  private String data_completa_inicio, data_completa_fim;
   		  private String s_natureza;

    	  
    	  public PesquisaParalela(int dia_inicio, int mes_inicio, int ano_inicio, int dia_fim, int mes_fim, int ano_fim, String natureza) {
    		 
    		  
    		  this.s_natureza = natureza;
    		  
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
            abrirPagina(data_completa_inicio,data_completa_fim, s_natureza);  

			
		}
    	  
    	  
      }
      
      
      public void GerenciarThreads() {
    	new Thread(){  
    		
    	@Override
    	public void run() {
    		boolean rodar = true;
    		
    	while(rodar) {	
    		
    		try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    		int count_ociosas = 0;
    	  int count_online = 0;
  		int contador_threads_offline = 0;
  		
  		 for(Thread thread : threads_ociosas) {
   		  //verificar quantas thread estao ociosas
              if(!thread.isAlive()) {
            	  count_ociosas++;
              }

   	  }
   	  

    	  for(Thread thread : threads_online) {
    		  //verificar quantas thread estao online
               if(thread.isAlive()) {
            	   count_online++;
               }

    	  }
    	  
    	  if(count_online < 3) {
    		  for(Thread thread : threads_ociosas) {
        		 //pega a proxima thread_ociosas e inicia ela
    			  if(thread.getState() != null) {
    				  if(thread.isAlive() == false) {
    					  //inicia a thread
    					  thread.start();
    					  threads_online.add(thread);
    					  threads_ociosas.remove(thread);
    					  break;
    				  }
    				  
    			  }
    			  
    			  
        	  }
    		  
    		  
    	  }
    	 
    	  for(Thread thread : threads_online) {
    		  if(!thread.isAlive()) {
    			  contador_threads_offline++;
    		  }
    	  }
    	  
    	  if(contador_threads_offline == contador_global_threads) {
  			novaNotificacao("Donwload de Notas Finalizado!", "/audio/beep_erro_net.wav", 2);
  			CadastroAviso avisar = new CadastroAviso();
			avisar.setTipo("Aviso");
			String nome = "";
			if(cadastro.getTipo_pessoa() == 0) {
				nome = cadastro.getNome_empresarial();

			}else {
				nome = cadastro.getNome_fantaia();
			}
			avisar.setSetor("Download de Notas");
			avisar.setMensagem("Download de notas Finalizado - Cliente: " + nome);
            telaPrincipal.incluir_aviso(avisar);
            
  			  for(WebDriver driver : drivers) {
  				  try{
  					  driver.close();
  	  				  driver.quit();

  				  }catch(Exception e) {
  					  
  				  }
  			  }
    		  rodar = false;
    	  }
    	
    	  System.out.println("numero de threads total: " + contador_global_threads);
    	  System.out.println("numero de threads ociosas: " + count_ociosas);
    	  System.out.println("numero de thread  Online: " + count_online );
    	  System.out.println("numero de thread ja encerradas : " + contador_threads_offline );
    	  
           
    	}
    	}
    	}.start();
    	
      }
      
      public void iniciarPesquisas(int mes_inicio, int mes_fim, int ano_inicio) {

			novaNotificacao("Donwload de Notas Iniciado!", "/audio/beep_notificacao.wav", 1);
			CadastroAviso avisar = new CadastroAviso();
			avisar.setTipo("Aviso");
			String nome = "";
			if(cadastro.getTipo_pessoa() == 0) {
				nome = cadastro.getNome_empresarial();

			}else {
				nome = cadastro.getNome_fantaia();
			}
			avisar.setSetor("Download de Notas");
			avisar.setMensagem("Download de notas Iniciado - Cliente: " + nome);
            telaPrincipal.incluir_aviso(avisar);
            
            
    	  PesquisaParalela pesquisar = null;

	     int ano = ano_inicio;
    	  for(int mes = mes_inicio; mes <= mes_fim; mes++) {	
	    	  for(int dia = 1; dia <= 21; dia += 10) {
	    		 
	    		 
	    		  if(dia == 21) {
	    			  
	    			  if(mes == 2) {
			    		   pesquisar = new PesquisaParalela(dia, mes ,ano, 28, mes , ano , natureza);


	    			  }
	    			  else {
	    				  //verifica se o mes e de 30 dias
	    				  //4 meses no ano com 30 dias (abril(4), junho(6), setembro(9) e novembro(11)).
	    				 if(mes == 4 || mes == 6 || mes == 9 || mes == 11) {
				    		   pesquisar = new PesquisaParalela(dia, mes ,ano, 30, mes , ano, natureza);

	    				 }else {
				    		   pesquisar = new PesquisaParalela(dia, mes ,ano, 31, mes , ano, natureza);

	    				 }
	    				  
	    			  }

	    		  }else {
		    		   pesquisar = new PesquisaParalela(dia, mes ,ano, dia+9, mes , ano, natureza);

	    		  }
	    		  Thread rodar = new Thread(pesquisar);
	    		    threads_ociosas.add(rodar);
	    		    contador_global_threads++;
	    		  
	     
	    	  }
    		
	    	 
	      }
	    
	      GerenciarThreads();
       
	} 
      
      
  	public void novaNotificacao(String texto, String song, int repeticao) {
		try {
	    	Thread.sleep(1000);
	    	 URL url = TelaPrincipal.class.getResource(song);
	    	   TelaNotificacao tela = new TelaNotificacao();
			  
				
	    		new Thread() {
					@Override 
					public void run() {
	            ReproduzirAudio player = new ReproduzirAudio();
	            for(int i = 0; i < repeticao; i++) {
	            player.play(url);
	            }
					}
				}.start();
			
		    	Thread.sleep(2000);

		      	tela.setVisible(true);
	             tela.setMensagem(texto );

	       
		

	        Thread.sleep(5000);
             tela.fechar();
	    	} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
  	
  	class renomearEmBackground implements Runnable {

  		private String s_id_nota, s_caminho_diretorio_proprio;
  		
  		public renomearEmBackground(String id_nota, String caminho_diretorio_proprio) {
  			
  	  		this.s_id_nota = id_nota;
  	  		this.s_caminho_diretorio_proprio = caminho_diretorio_proprio;
  	  		
  		}
  		
		@Override
		public void run() {
			try {
  				Thread.sleep(3000);
  			} catch (InterruptedException e) {
  				// TODO Auto-generated catch block
  				e.printStackTrace();
  			}
  	  		
  	  	//o nome da nota sera, NF-"codigo" + .pdf
  	  		String nome_nota = "NFA-" + s_id_nota + ".pdf";
  	  		//renomear essa nota
  	  		ManipularTxt manipular = new ManipularTxt();
  	  		String caminho_completo_origem = configs_globais.getServidorUnidade() + "E-Contract\\arquivos\\arquivos_comuns\\"  + nome_nota;
  	  		String caminho_completo_destino = s_caminho_diretorio_proprio + nome_nota; 
  	  		
  	  		
  	        manipular.moverArquivo(caminho_completo_origem, caminho_completo_destino);
			
		}
  		
  		
  	}
      
}
