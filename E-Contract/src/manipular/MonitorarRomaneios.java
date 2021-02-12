package manipular;

import java.io.File;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import cadastros.CadastroCliente;
import cadastros.CadastroLogin;
import cadastros.CadastroNFe;
import cadastros.CadastroRomaneio;
import conexaoBanco.GerenciarBancoClientes;
import outros.DadosGlobais;
import tratamento_proprio.Log;
import views_personalizadas.TelaNotificacaoSuperior;
import views_personalizadas.TelaNotificacaoSuperiorModoBusca;

public class MonitorarRomaneios {

	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	
	public MonitorarRomaneios() {
		getDadosGlobais();
	}
	
	
	public ArrayList<CadastroRomaneio> vigiarTodosRomaneios() {
		
                 try {
						String unidade_base_dados = configs_globais.getServidorUnidade();
						String sub_pasta = "E-Contract\\arquivos\\clientes";
						String pasta_final = unidade_base_dados + "\\" + sub_pasta;

						ManipularRomaneios manipular = new ManipularRomaneios(pasta_final);
						ArrayList<CadastroRomaneio> romaneios = manipular.tratar();

					  return romaneios;

					} catch (Exception e) {
						 JOptionPane.showMessageDialog(null, "Erro ao ler romaneios");
                       return null;
					}
					
				
	}
	
	public ArrayList<CadastroRomaneio> vigiarTodosRomaneiosMaisRapido() {
		
        try {
				String unidade_base_dados = configs_globais.getServidorUnidade();
				String sub_pasta = "E-Contract\\arquivos\\clientes";
				String pasta_final = unidade_base_dados + "\\" + sub_pasta;

				ManipularRomaneios manipular = new ManipularRomaneios(pasta_final);
				ArrayList<CadastroRomaneio> romaneios = manipular.tratarMaisRapido();

			  return romaneios;

			} catch (Exception e) {
				 JOptionPane.showMessageDialog(null, "Erro ao ler romaneios");
              return null;
			}
			
		
}
	
	public void vigiarRomaneios() {
		TelaNotificacaoSuperiorModoBusca avisos = new TelaNotificacaoSuperiorModoBusca();


		new Thread() {
			@Override
			public void run() {
				String unidade_base_dados = configs_globais.getServidorUnidade();
				String sub_pasta = "E-Contract\\arquivos\\arquivos_comuns";
				String pasta_final = unidade_base_dados + "\\" + sub_pasta;
				
				new Thread() {
					@Override
					public void run() {

						avisos.setMensagem("Modo de Busca");

						avisos.setVisible(true);
					}
				}.start();
				

				while (true) {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					try {
						avisos.setMensagem("Modo de Busca");
						
						ManipularRomaneios manipular = new ManipularRomaneios(pasta_final);
						manipular.setTelaMensagem(avisos);
						ArrayList<CadastroRomaneio> romaneios = manipular.tratar();
						GerenciarBancoClientes gerenciar_clientes = new GerenciarBancoClientes();
						ArrayList<CadastroCliente> clientes_cadastrados = gerenciar_clientes.getClientes(0, 0, "");
						for (CadastroRomaneio roms : romaneios) {
							avisos.setMensagem("Novo Romaneio Encontrado");
							Thread.sleep(3000);
							CadastroCliente remetente = roms.getRemetente();
							CadastroCliente destinatario = roms.getDestinatario();
							boolean remetente_cadastrado = false;
							boolean destinatario_cadastrado = false;
							// verifica se o remetente ja esta cadastrado
							for (CadastroCliente cliente : clientes_cadastrados) {
								if (cliente.getIe().trim().equals(remetente.getIe().trim())) {
									remetente_cadastrado = true;
								//	JOptionPane.showMessageDialog(null, "Remetente Cadastrado");
									break;
								}
							}
							for (CadastroCliente cliente : clientes_cadastrados) {
								if (cliente.getIe().trim().equals(destinatario.getIe().trim())) {
									destinatario_cadastrado = true;
								//	JOptionPane.showMessageDialog(null, "Destinatario Cadastrado");

									break;
								}
							}
							
							if (destinatario.getTipo_pessoa() == 0) {
								//JOptionPane.showMessageDialog(null, "Nome destinatario: " + destinatario.getNome_empresarial());
							} else {
								//JOptionPane.showMessageDialog(null, "Nome destinatario: " + destinatario.getNome_fantaia());
							}
							
							if (remetente.getTipo_pessoa() == 0) {
								//JOptionPane.showMessageDialog(null, "Nome remetente: " + remetente.getNome_empresarial());
							} else {
								//JOptionPane.showMessageDialog(null, "Nome remetente: " + remetente.getNome_fantaia());
							}
							
							
							
							
							
							if (remetente_cadastrado && !destinatario_cadastrado) {
								// mover para a pasta do remetente
								ManipularTxt manipular_txt = new ManipularTxt();
								String nome_pasta;
								if (remetente.getTipo_pessoa() == 0) {
									nome_pasta = remetente.getNome_empresarial().toUpperCase();
								} else {

									nome_pasta = remetente.getNome_fantaia().toUpperCase();
								}
								unidade_base_dados = configs_globais.getServidorUnidade();
								sub_pasta = "E-Contract\\arquivos\\clientes";
								ManipularTxt manipular_arq = new ManipularTxt();
								nome_pasta = nome_pasta.trim();
								String caminho_completo_nf = unidade_base_dados + "\\" + sub_pasta + "\\"
										+ nome_pasta.toUpperCase() + "\\" + "ROMANEIOS" + "\\romaneio-"
										+ roms.getNumero_romaneio() + ".pdf";
								// JOptionPane.showMessageDialog(null, "Movendo de :\n" +
								// roms.getCaminho_arquivo()+ "\nPara:\n" + caminho_completo_nf);
								avisos.setMensagem("Movendo...");
								// primeiro veririca se nao existe um arquivo com esse nome
								File file = new File(caminho_completo_nf);
								if (!file.exists()) {
									boolean mover = manipular_arq.moverArquivo(roms.getCaminho_arquivo(),
											caminho_completo_nf);
									if (mover) {
										avisos.setMensagem("Romaneio movido para a pasta do remetente");
										// JOptionPane.showMessageDialog(null, "Romaneio movido para a pasta do
										// remetente");
									} else {
										// JOptionPane.showMessageDialog(null, "Erro ao mover o romaneio");
										avisos.setMensagem("Erro ao mover o romaneio para pasta do remetente");
									}
								} else {
									avisos.setMensagem("Romaneio ja lido");
									new File(roms.getCaminho_arquivo()).delete();
								}
							} else if (!remetente_cadastrado && destinatario_cadastrado) {
								// mover para a pasta do destinatario
								ManipularTxt manipular_txt = new ManipularTxt();
								String nome_pasta;
								if (destinatario.getTipo_pessoa() == 0) {
									nome_pasta = destinatario.getNome_empresarial().toUpperCase();
								} else {

									nome_pasta = destinatario.getNome_fantaia().toUpperCase();
								}
								unidade_base_dados = configs_globais.getServidorUnidade();
								sub_pasta = "E-Contract\\arquivos\\clientes";
								ManipularTxt manipular_arq = new ManipularTxt();
								nome_pasta = nome_pasta.trim();
								String caminho_completo_nf = unidade_base_dados + "\\" + sub_pasta + "\\"
										+ nome_pasta.toUpperCase() + "\\" + "ROMANEIOS" + "\\romaneio-"
										+ roms.getNumero_romaneio() + ".pdf";
								// JOptionPane.showMessageDialog(null, "Movendo de :\n" +
								// roms.getCaminho_arquivo()+ "\nPara:\n" + caminho_completo_nf);
								avisos.setMensagem("Movendo...");
								// primeiro veririca se nao existe um arquivo com esse nome
								File file = new File(caminho_completo_nf);
								if (!file.exists()) {
									boolean mover = manipular_arq.moverArquivo(roms.getCaminho_arquivo(),
											caminho_completo_nf);
									if (mover) {
										// JOptionPane.showMessageDialog(null, "Romaneio movido para a pasta do
										// remetente");
										avisos.setMensagem("Romaneio movido para a pasta do remetente");
									} else {
										// JOptionPane.showMessageDialog(null, "Erro ao mover o romaneio");
										avisos.setMensagem("Erro ao mover romaneio para a pasta do remetente");
									}
								} else {
									avisos.setMensagem("Romaneio ja lido");
									new File(roms.getCaminho_arquivo()).delete();
								}
							} else if (remetente_cadastrado && destinatario_cadastrado) {
								if (remetente.getIe().trim().equals(destinatario.getIe().trim())) {
									// mover para o remetente
									// copiar para o remetente
									ManipularTxt manipular_txt = new ManipularTxt();
									String nome_pasta;
									if (remetente.getTipo_pessoa() == 0) {

										nome_pasta = remetente.getNome_empresarial();
									} else {

										nome_pasta = remetente.getNome_fantaia();
									}
									unidade_base_dados = configs_globais.getServidorUnidade();
									sub_pasta = "E-Contract\\arquivos\\clientes";
									ManipularTxt manipular_arq = new ManipularTxt();
									nome_pasta = nome_pasta.trim();
									String caminho_completo_nf = unidade_base_dados + "\\" + sub_pasta + "\\"
											+ nome_pasta + "\\" + "ROMANEIOS" + "\\romaneio-"
											+ roms.getNumero_romaneio() + ".pdf";
									// JOptionPane.showMessageDialog(null, "Copiando de :\n" +
									// roms.getCaminho_arquivo()+ "\nPara:\n" + caminho_completo_nf);
									avisos.setMensagem("Copiando...");
									// primeiro veririca se nao existe um arquivo com esse nome
									File file = new File(caminho_completo_nf);
									if (!file.exists()) {
										boolean copiar = manipular_arq.moverArquivo(roms.getCaminho_arquivo(),
												caminho_completo_nf);
										if (copiar) {
											// JOptionPane.showMessageDialog(null, "Romaneio movido para a pasta do
											// remetente");
											avisos.setMensagem("Romaneio movido para a pasta do remetente");
											// mover para a pasta do destinatario
										} else {
											// JOptionPane.showMessageDialog(null, "Romaneio não pode ser movido para a
											// pasta do remetente");
											avisos.setMensagem(
													"Romaneio não pode ser movido para a pasta do remetente");
										}
									} else {
										avisos.setMensagem("Romaneio ja lido");
										new File(roms.getCaminho_arquivo()).delete();
									}
								} else {
									//Romaneio com destinatario e remetente diferente
									// copiar para o destinatario
									ManipularTxt manipular_txt = new ManipularTxt();
									String nome_pasta;
									
									if (destinatario.getTipo_pessoa() == 0) {
										nome_pasta = destinatario.getNome_empresarial();
									} else {
										nome_pasta = destinatario.getNome_fantaia();
									}
									
									unidade_base_dados = configs_globais.getServidorUnidade();
									sub_pasta = "E-Contract\\arquivos\\clientes";
									ManipularTxt manipular_arq = new ManipularTxt();
									nome_pasta = nome_pasta.trim();
									String caminho_completo_nf = unidade_base_dados + "\\" + sub_pasta + "\\"
											+ nome_pasta + "\\" + "ROMANEIOS" + "\\romaneio-"
											+ roms.getNumero_romaneio() + ".pdf";
									avisos.setMensagem("Copiando...");
									// JOptionPane.showMessageDialog(null, "Copiando de :\n" +
									// roms.getCaminho_arquivo()+ "\nPara:\n" + caminho_completo_nf);
									// primeiro veririca se nao existe um arquivo com esse nome
									File file = new File(caminho_completo_nf);
									if (!file.exists()) {
										boolean copiar = manipular_arq.copiarNFe(roms.getCaminho_arquivo(),
												caminho_completo_nf);
										if (copiar) {
											// JOptionPane.showMessageDialog(null, "Romaneio copiado para a pasta do
											// destinatario");
											avisos.setMensagem("Romaneio copiado para a pasta do destinatario");
											// mover para a pasta do remetente
											
											
											if (remetente.getTipo_pessoa() == 0) {
												nome_pasta = remetente.getNome_empresarial().toUpperCase();
											} else {

												nome_pasta = remetente.getNome_fantaia().toUpperCase();
											}
											unidade_base_dados = configs_globais.getServidorUnidade();
											sub_pasta = "E-Contract\\arquivos\\clientes";
											nome_pasta = nome_pasta.trim();
											caminho_completo_nf = unidade_base_dados + "\\" + sub_pasta + "\\"
													+ nome_pasta.toUpperCase() + "\\" + "ROMANEIOS" + "\\romaneio-"
													+ roms.getNumero_romaneio() + ".pdf";
											// JOptionPane.showMessageDialog(null, "Movendo de :\n" +
											// roms.getCaminho_arquivo()+ "\nPara:\n" + caminho_completo_nf);
											avisos.setMensagem("Movendo...");
											boolean mover = manipular_arq.moverArquivo(roms.getCaminho_arquivo(),
													caminho_completo_nf);
											if (mover) {
												// JOptionPane.showMessageDialog(null, "Romaneio movido para a pasta do
												// destinatario");
												avisos.setMensagem("Romaneio movido para a pasta do remetente");
											} else {
												// JOptionPane.showMessageDialog(null, "Erro ao mover o romaneio para a
												// pasta do destinatario");
												avisos.setMensagem(
														"Erro ao mover o romaneio para a pasta do remetente");
											}
										} else {
											// JOptionPane.showMessageDialog(null, "Erro ao copiar o romaneio para a
											// pasta
											// do remetente");
											avisos.setMensagem("Erro ao copiar o romaneio para a pasta do remetente");
										}
									} else {
										avisos.setMensagem("Romaneio ja lido");
										new File(roms.getCaminho_arquivo()).delete();
									}
								}
							} else {
								// JOptionPane.showMessageDialog(null, "Romaneio lido mas nem o cliente
								// remetente e nem o cliente destinatario estão cadastrado");
								avisos.setMensagem(
										"Romaneio lido mas nem o cliente remetente e nem o cliente destinatario estão cadastrado");
							}
						}
					} catch (Exception e) {
						// JOptionPane.showMessageDialog(null, "Erro ao ler romaneios");
						avisos.setMensagem("Erro ao ler romaneios\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
						JOptionPane.showMessageDialog(null, "Erro ao ler romaneios\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					//busca por nfs
					try {
						ManipularNotasFiscais manipularnf = new ManipularNotasFiscais(pasta_final);
						ArrayList<CadastroNFe> notas_fiscais = manipularnf.tratar();
						for (CadastroNFe cadastro : notas_fiscais) {
							avisos.setMensagem("Nova NF Encontrada");
							Thread.sleep(3000);
							//verifica se essa nota ja existe
							
							//JOptionPane.showMessageDialog(null, "Inscricao remetente: " + cadastro.getInscricao_remetente() + "\nInscricao Destinatario: " + cadastro.getInscricao_destinatario());

								boolean remetente_cadastrado = false;
								CadastroCliente remetente = null;
								
								boolean destinatario_cadastrado = false;
								CadastroCliente destinatario =null;
								GerenciarBancoClientes gerenciar_clientes = new GerenciarBancoClientes();
								ArrayList<CadastroCliente> clientes = gerenciar_clientes.getClientes(0, 0, "");
								
							    //verifica-se  o remetente esta cadastrao
							   for(CadastroCliente rem : clientes) {
								   if(cadastro.getInscricao_remetente() != null && !cadastro.getInscricao_remetente().equals(" ") && !cadastro.getInscricao_remetente().equals("")) {
								   if(rem.getIe().equals(cadastro.getInscricao_remetente())) {
									   // JOptionPane.showMessageDialog(null, "remetente cadastrado");
									   remetente_cadastrado = true;
									   remetente = rem;
									   break;
								   }
								   }
							   }
							   
							 //verifica-se  o destinatario esta cadastrao
							   for(CadastroCliente dest : clientes) {
								   if(cadastro.getInscricao_destinatario() != null && !cadastro.getInscricao_destinatario().equals(" ") && !cadastro.getInscricao_destinatario().equals("")) {

								   if(dest.getIe().equals(cadastro.getInscricao_destinatario())) {
									   //JOptionPane.showMessageDialog(null, "destinatario cadastrado");
									   destinatario_cadastrado = true;
									   destinatario = dest;
									   break;
								   }
							   }
							   }
							   
							   
							   if (remetente_cadastrado && !destinatario_cadastrado) {
									//copiar para pasta do remetente
									ManipularTxt manipular_txt = new ManipularTxt();
									String nome_pasta;
									if (remetente.getTipo_pessoa() == 0) {
										nome_pasta = remetente.getNome_empresarial().toUpperCase();
									} else {

										nome_pasta = remetente.getNome_fantaia().toUpperCase();
									}
									unidade_base_dados = configs_globais.getServidorUnidade();
									sub_pasta = "E-Contract\\arquivos\\clientes";
									ManipularTxt manipular_arq = new ManipularTxt();
									nome_pasta = nome_pasta.trim();
									String caminho_completo_nf = unidade_base_dados + "\\" + sub_pasta + "\\" + nome_pasta.toUpperCase() + "\\"
											+ "NOTAS FISCAIS" + "\\NFA-" + cadastro.getNfe().trim() + ".pdf";
									
									// JOptionPane.showMessageDialog(null, "Movendo de :\n" +
									// roms.getCaminho_arquivo()+ "\nPara:\n" + caminho_completo_nf);
								   
									File file = new File(caminho_completo_nf);
									if (!file.exists()) {
										boolean mover = manipular_arq.copiarNFe(cadastro.getCaminho_arquivo(),
												caminho_completo_nf);
										if (mover) {
											
											//JOptionPane.showMessageDialog(null, "NF copiada para a pasta do remetente");
										} else {
											// JOptionPane.showMessageDialog(null, "Erro ao mover a nf para a pasta do remetente");
											
										}
									} else {
										boolean apagar = manipular_arq.apagarArquivo(cadastro.getCaminho_arquivo());
										//JOptionPane.showMessageDialog(null, "NF já importada");
									}
							   }else if(!remetente_cadastrado && destinatario_cadastrado) {
								 //copiar para pasta do destinatario
									ManipularTxt manipular_txt = new ManipularTxt();
									String nome_pasta;
									if (remetente.getTipo_pessoa() == 0) {
										nome_pasta = destinatario.getNome_empresarial().toUpperCase();
									} else {

										nome_pasta = destinatario.getNome_fantaia().toUpperCase();
									}
									unidade_base_dados = configs_globais.getServidorUnidade();
									sub_pasta = "E-Contract\\arquivos\\clientes";
									ManipularTxt manipular_arq = new ManipularTxt();
									nome_pasta = nome_pasta.trim();
									String caminho_completo_nf = unidade_base_dados + "\\" + sub_pasta + "\\" + nome_pasta.toUpperCase() + "\\"
											+ "NOTAS FISCAIS" + "\\NFA-" + cadastro.getNfe().trim() + ".pdf";
									
									// JOptionPane.showMessageDialog(null, "Movendo de :\n" +
									// roms.getCaminho_arquivo()+ "\nPara:\n" + caminho_completo_nf);
								   
									File file = new File(caminho_completo_nf);
									if (!file.exists()) {
										boolean mover = manipular_arq.copiarNFe(cadastro.getCaminho_arquivo(),
												caminho_completo_nf);
										if (mover) {
											
											// JOptionPane.showMessageDialog(null, "NF copiada para a pasta do destinatario");
										} else {
											// JOptionPane.showMessageDialog(null, "Erro ao mover a nf para a pasta do destinatario");
											
										}
									} else {
										boolean apagar = manipular_arq.apagarArquivo(cadastro.getCaminho_arquivo());

										//JOptionPane.showMessageDialog(null, "NF já importada");
									}
							   }else if(remetente_cadastrado && destinatario_cadastrado) {
								   if(remetente.getId() == destinatario.getId()) {
									   //move o arquivo para a pasta do remetente
									 //copiar para pasta do remetente
										ManipularTxt manipular_txt = new ManipularTxt();
										String nome_pasta;
										if (remetente.getTipo_pessoa() == 0) {
											nome_pasta = remetente.getNome_empresarial().toUpperCase();
										} else {

											nome_pasta = remetente.getNome_fantaia().toUpperCase();
										}
										//JOptionPane.showMessageDialog(null, "nome da pasta remetente: " + nome_pasta);

										unidade_base_dados = configs_globais.getServidorUnidade();
										sub_pasta = "E-Contract\\arquivos\\clientes";
										ManipularTxt manipular_arq = new ManipularTxt();
										nome_pasta = nome_pasta.trim();
										String caminho_completo_nf = unidade_base_dados + "\\" + sub_pasta + "\\" + nome_pasta.toUpperCase() + "\\"
												+ "NOTAS FISCAIS" + "\\NFA-" + cadastro.getNfe().trim() + ".pdf";
										
										// JOptionPane.showMessageDialog(null, "Movendo de :\n" +
										// roms.getCaminho_arquivo()+ "\nPara:\n" + caminho_completo_nf);
									   
										File file = new File(caminho_completo_nf);
										if (!file.exists()) {
											boolean mover = manipular_arq.moverArquivo(cadastro.getCaminho_arquivo(),
													caminho_completo_nf);
											if (mover) {
												avisos.setMensagem("NF copiada para a pasta do remetente");

												// JOptionPane.showMessageDialog(null, "NF copiada para a pasta do remetente");
											} else {
												// JOptionPane.showMessageDialog(null, "Erro ao mover a nf para a pasta do remetente");
												
											}
										} else {
											//JOptionPane.showMessageDialog(null, "NF já importada");
										}
									   
									   
								   }else {
									   //copiar para a pasta do remetente e mover para a pasta do destinatario
										//copiar para pasta do remetente
										ManipularTxt manipular_txt = new ManipularTxt();
										String nome_pasta;
										if (remetente.getTipo_pessoa() == 0) {
											nome_pasta = remetente.getNome_empresarial().toUpperCase();
										} else {

											nome_pasta = remetente.getNome_fantaia().toUpperCase();
										}
										//JOptionPane.showMessageDialog(null, "nome da pasta remetente: " + nome_pasta);

										unidade_base_dados = configs_globais.getServidorUnidade();
										sub_pasta = "E-Contract\\arquivos\\clientes";
										ManipularTxt manipular_arq = new ManipularTxt();
										nome_pasta = nome_pasta.trim();
										String caminho_completo_nf = unidade_base_dados + "\\" + sub_pasta + "\\" + nome_pasta.toUpperCase() + "\\"
												+ "NOTAS FISCAIS" + "\\NFA-" + cadastro.getNfe().trim() + ".pdf";
										
										// JOptionPane.showMessageDialog(null, "Movendo de :\n" +
										// roms.getCaminho_arquivo()+ "\nPara:\n" + caminho_completo_nf);
									   
										File file = new File(caminho_completo_nf);
										if (!file.exists()) {
											boolean mover = manipular_arq.copiarNFe(cadastro.getCaminho_arquivo(),
													caminho_completo_nf);
											if (mover) {
												//mover para pasta do destinatario
												if (destinatario.getTipo_pessoa() == 0) {
													nome_pasta = destinatario.getNome_empresarial().toUpperCase();
												} else {

													nome_pasta = destinatario.getNome_fantaia().toUpperCase();
												}
												//JOptionPane.showMessageDialog(null, "nome da pasta destinatario: " + nome_pasta);

												unidade_base_dados = configs_globais.getServidorUnidade();
												sub_pasta = "E-Contract\\arquivos\\clientes";
												nome_pasta = nome_pasta.trim();
												 caminho_completo_nf = unidade_base_dados + "\\" + sub_pasta + "\\" + nome_pasta.toUpperCase() + "\\"
														+ "NOTAS FISCAIS" + "\\NFA-" + cadastro.getNfe().trim() + ".pdf";
												
												// JOptionPane.showMessageDialog(null, "Movendo de :\n" +
												// roms.getCaminho_arquivo()+ "\nPara:\n" + caminho_completo_nf);
											   
												 file = new File(caminho_completo_nf);
												if (!file.exists()) {
													 mover = manipular_arq.moverArquivo(cadastro.getCaminho_arquivo(),
															caminho_completo_nf);
													if (mover) {
														
														//	 JOptionPane.showMessageDialog(null, "NF copiada para a pasta do destinatario");
													} else {
														//	 JOptionPane.showMessageDialog(null, "Erro ao mover a nf para a pasta do destinatario");
														
													}
												} else {
													boolean apagar = manipular_arq.apagarArquivo(cadastro.getCaminho_arquivo());

													//JOptionPane.showMessageDialog(null, "NF já importada");
												}
												// JOptionPane.showMessageDialog(null, "NF copiada para a pasta do remetente");
											} else {
												// JOptionPane.showMessageDialog(null, "Erro ao mover a nf para a pasta do remetente");
												//mover para pasta do destinatario
												if (destinatario.getTipo_pessoa() == 0) {
													nome_pasta = destinatario.getNome_empresarial().toUpperCase();
												} else {

													nome_pasta = destinatario.getNome_fantaia().toUpperCase();
												}
												//JOptionPane.showMessageDialog(null, "nome da pasta destinatario: " + nome_pasta);

												unidade_base_dados = configs_globais.getServidorUnidade();
												sub_pasta = "E-Contract\\arquivos\\clientes";
												nome_pasta = nome_pasta.trim();
												 caminho_completo_nf = unidade_base_dados + "\\" + sub_pasta + "\\" + nome_pasta.toUpperCase() + "\\"
														+ "NOTAS FISCAIS" + "\\NFA-" + cadastro.getNfe().trim() + ".pdf";
												
												// JOptionPane.showMessageDialog(null, "Movendo de :\n" +
												// roms.getCaminho_arquivo()+ "\nPara:\n" + caminho_completo_nf);
											   
												 file = new File(caminho_completo_nf);
												if (!file.exists()) {
													 mover = manipular_arq.moverArquivo(cadastro.getCaminho_arquivo(),
															caminho_completo_nf);
													if (mover) {
														
														//	 JOptionPane.showMessageDialog(null, "NF copiada para a pasta do destinatario");
													} else {
														//	 JOptionPane.showMessageDialog(null, "Erro ao mover a nf para a pasta do destinatario");
														
													}
												} else {
													//JOptionPane.showMessageDialog(null, "NF já importada");
												}
											}
										} else {
											//arquivo ja esta na pasta do remetente
											//mover para pasta do destinatario
											if (destinatario.getTipo_pessoa() == 0) {
												nome_pasta = destinatario.getNome_empresarial().toUpperCase();
											} else {

												nome_pasta = destinatario.getNome_fantaia().toUpperCase();
											}
											//JOptionPane.showMessageDialog(null, "nome da pasta destinatario: " + nome_pasta);

											unidade_base_dados = configs_globais.getServidorUnidade();
											sub_pasta = "E-Contract\\arquivos\\clientes";
											nome_pasta = nome_pasta.trim();
											 caminho_completo_nf = unidade_base_dados + "\\" + sub_pasta + "\\" + nome_pasta.toUpperCase() + "\\"
													+ "NOTAS FISCAIS" + "\\NFA-" + cadastro.getNfe().trim() + ".pdf";
											
											// JOptionPane.showMessageDialog(null, "Movendo de :\n" +
											// roms.getCaminho_arquivo()+ "\nPara:\n" + caminho_completo_nf);
										   
											 file = new File(caminho_completo_nf);
											if (!file.exists()) {
												boolean  mover = manipular_arq.moverArquivo(cadastro.getCaminho_arquivo(),
														caminho_completo_nf);
												if (mover) {
													
													//	 JOptionPane.showMessageDialog(null, "NF copiada para a pasta do destinatario");
												} else {
													//	 JOptionPane.showMessageDialog(null, "Erro ao mover a nf para a pasta do destinatario");
													
												}
											} else {
												boolean apagar = manipular_arq.apagarArquivo(cadastro.getCaminho_arquivo());

												//JOptionPane.showMessageDialog(null, "NF já importada");
											}
											
											//JOptionPane.showMessageDialog(null, "NF já importada");
										}
										
										
								   }
								
									
									
							   }else {
								   //JOptionPane.showMessageDialog(null, "NF lida mas nem o remetente nem o destinatario esta cadastrado");
									 
							   }
								
							
						}
						
						
						
					}catch(Exception e) {
						avisos.setMensagem("Erro ao ler NF\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
						//	JOptionPane.showMessageDialog(null, "Erro ao ler NF\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
					
					}
					
					
				}
			}
		}.start();
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
