package manipular;

import java.io.File;
import java.util.ArrayList;

import cadastros.CadastroCliente;
import cadastros.CadastroLogin;
import cadastros.CadastroRomaneio;
import conexaoBanco.GerenciarBancoClientes;
import outros.DadosGlobais;
import tratamento_proprio.Log;
import views_personalizadas.TelaNotificacaoSuperior;

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
						String sub_pasta = "E-Contract\\arquivos\\romaneios";
						String pasta_final = unidade_base_dados + "\\" + sub_pasta;

						ManipularRomaneios manipular = new ManipularRomaneios(pasta_final);
						ArrayList<CadastroRomaneio> romaneios = manipular.tratar();

					  return romaneios;

					} catch (Exception e) {
						// JOptionPane.showMessageDialog(null, "Erro ao ler romaneios");
                       return null;
					}
					
				
	}
	
	
	public void vigiarRomaneios() {
		new Thread() {

			@Override
			public void run() {

				TelaNotificacaoSuperior avisos = new TelaNotificacaoSuperior();

				new Thread() {
					@Override
					public void run() {
						avisos.setMensagem("Modo de Busca");
						avisos.setVisible(true);
					}
				}.start();

				while (true) {

					try {
						Thread.sleep(10000);
					} catch (InterruptedException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}

					try {

						avisos.setMensagem("Modo de Busca");

						String unidade_base_dados = configs_globais.getServidorUnidade();
						String sub_pasta = "E-Contract\\arquivos\\romaneios";
						String pasta_final = unidade_base_dados + "\\" + sub_pasta;

						ManipularRomaneios manipular = new ManipularRomaneios(pasta_final);
						ArrayList<CadastroRomaneio> romaneios = manipular.tratar();

						GerenciarBancoClientes gerenciar_clientes = new GerenciarBancoClientes();
						ArrayList<CadastroCliente> clientes_cadastrados = gerenciar_clientes.getClientes(0, 0, "");

						for (CadastroRomaneio roms : romaneios) {

							avisos.setMensagem("Novo Romaneio Encontrado");

							Thread.sleep(3000);

							CadastroCliente remetente = roms.getRemetente();
							CadastroCliente destinatario = roms.getDestinatario();

							boolean remetente_cadastrado = true;
							boolean destinatario_cadastrado = true;
							// verifica se o remetente ja esta cadastrado

							for (CadastroCliente cliente : clientes_cadastrados) {
								if (cliente.getIe().trim().equals(remetente.getIe().trim())) {
									remetente_cadastrado = true;
									break;
								}
							}

							for (CadastroCliente cliente : clientes_cadastrados) {
								if (cliente.getIe().trim().equals(destinatario.getIe().trim())) {
									destinatario_cadastrado = true;
									break;
								}
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
									avisos.setMensagem("Romaneio já lido");
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
									avisos.setMensagem("Romaneio já lido");
									new File(roms.getCaminho_arquivo()).delete();
								}
							} else if (remetente_cadastrado && destinatario_cadastrado) {

								if (remetente.getIe().trim().equals(destinatario.getIe().trim())) {
									// mover para o remetente
									// copiar para o remetente
									ManipularTxt manipular_txt = new ManipularTxt();
									String nome_pasta;

									if (destinatario.getTipo_pessoa() == 0) {
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
										avisos.setMensagem("Romaneio já lido");
										new File(roms.getCaminho_arquivo()).delete();
									}
								} else {
									// copiar para o remetente
									ManipularTxt manipular_txt = new ManipularTxt();
									String nome_pasta;

									if (destinatario.getTipo_pessoa() == 0) {
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

									avisos.setMensagem("Copiando...");

									// JOptionPane.showMessageDialog(null, "Copiando de :\n" +
									// roms.getCaminho_arquivo()+ "\nPara:\n" + caminho_completo_nf);
									
									//primeiro veririca se nao existe um arquivo com esse nome
									File file = new File(caminho_completo_nf);
									
									if(!file.exists() ) {
									boolean copiar = manipular_arq.copiarNFe(roms.getCaminho_arquivo(),
											caminho_completo_nf);
									if (copiar) {

										// JOptionPane.showMessageDialog(null, "Romaneio copiado para a pasta do
										// remetente");
										avisos.setMensagem("Romaneio copiado para a pasta do remetente");

										// mover para a pasta do destinatario

										if (destinatario.getTipo_pessoa() == 0) {
											nome_pasta = destinatario.getNome_empresarial().toUpperCase();
										} else {
											nome_pasta = destinatario.getNome_fantaia().toUpperCase();
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
											avisos.setMensagem("Romaneio movido para a pasta do destinatario");

										} else {
											// JOptionPane.showMessageDialog(null, "Erro ao mover o romaneio para a
											// pasta do destinatario");
											avisos.setMensagem("Erro ao mover o romaneio para a pasta do destinatario");

										}

									} else {
										// JOptionPane.showMessageDialog(null, "Erro ao copiar o romaneio para a pasta
										// do remetente");
										avisos.setMensagem("Erro ao copiar o romaneio para a pasta do remetente");
									}
									}else {
										avisos.setMensagem("Romaneio já lido");
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
						avisos.setMensagem("Erro ao ler romaneios");

					}

					try {
						Thread.sleep(10000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
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
