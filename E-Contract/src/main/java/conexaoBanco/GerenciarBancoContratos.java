package main.java.conexaoBanco;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import com.mysql.cj.exceptions.RSAException;

import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroContrato.CadastroPagamentoContratual;
import main.java.cadastros.CadastroContrato.Recebimento;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroModelo;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroSafra;
import main.java.cadastros.CarregamentoCompleto;
import main.java.cadastros.ContaBancaria;
import main.java.cadastros.PagamentoCompleto;
import main.java.cadastros.RecebimentoCompleto;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.outros.DadosGlobais;
import main.java.tratamento_proprio.Log;

public class GerenciarBancoContratos {

	private Log GerenciadorLog;
	private CadastroLogin loginGlobal;

	private Registro registro_geral_relacao_contrato_corretor = new Registro();
	private Registro registro_geral_relacao_contrato_comprador = new Registro();
	private Registro registro_geral_relacao_contrato_vendedor = new Registro();
	private Registro registro_geral_relacao_contrato_modelo_pagamento = new Registro();
	private Registro registro_geral_relacao_contrato_sub_contrato = new Registro();
	private Registro registro_geral_relacao_contrato_tarefa = new Registro();

	public GerenciarBancoContratos() {
		getDadosGlobais();

	}

	public int inserir_modelo(CadastroModelo modelo) {
		if (modelo != null) {
			Connection conn = null;
			try {
				conn = ConexaoBanco.getConexao();
				String sql = "insert into modelo (id_modelo, nome_modelo, descricao_modelo , arquivo) values (0, ?, ?, ?)";

				PreparedStatement grava = (PreparedStatement) conn.prepareStatement(sql);
				grava.setString(1, modelo.getNome_modelo());
				grava.setString(2, modelo.getDescricao_modelo());
				grava.setBytes(3, modelo.getArquivo());
				grava.execute();
				JOptionPane.showMessageDialog(null, "Modelo  cadastrado com sucesso");
				ConexaoBanco.fechaConexao(conn, grava);
				return 1;

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao inserir o modelo no banco de" + "dados ");
				return 0;
			}
		} else {
			System.out.println("O modelo enviado por parametro esta vazia");
			return 0;
		}
	}

	public ArrayList<CadastroModelo> getModelos() {

		String selectModelos = "select * from modelo";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroModelo> listaModelos = new ArrayList<CadastroModelo>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectModelos);
			rs = pstm.executeQuery();

			while (rs.next()) {
				CadastroModelo modelo = new CadastroModelo();

				modelo.setId_modelo(rs.getInt("id_modelo"));
				;
				modelo.setDescricao_modelo(rs.getString("descricao_modelo"));
				modelo.setNome_modelo(rs.getString("nome_modelo"));
				modelo.setArquivo(rs.getBytes("arquivo"));

				listaModelos.add(modelo);
			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar modelos de contratos");
		}
		return listaModelos;
	}

	public int inserirContrato(CadastroContrato contrato, CadastroContrato contrato_pai) {
		boolean reverter = false;
		int retorno_contrato_inserido = inserir_contrato_retorno(contrato);

		if (retorno_contrato_inserido != -1) {
			// inserir tarefas
			if (inserirTarefas(retorno_contrato_inserido, contrato.getLista_tarefas())) {
				// tarefas foram adicionadas pode prosseguir

				// inserir registro na tabela de cliente_corretor
				if (inserir_contrato_corretor(contrato, retorno_contrato_inserido)) {
					// tabelas de relacoes de cliente_corretor inseridos com sucesso, retorne um pra
					// quem chamou
					// prosseguir para inserir as tabelas clientes compradores

					// inserir registro na tabela de cliente_comprador

					if (inserir_contrato_comprador(contrato, retorno_contrato_inserido)) {
						// tabelas de relacoes de cliente_corretor inseridos com sucesso, retorne um pra
						// quem chamou
						// prosseguir para inserir as tabelas clientes compradores

						// inserir registro na tabela de cliente_vendedor

						if (inserir_contrato_vendedor(contrato, retorno_contrato_inserido)) {
							if (inserirModelosPagamentos(retorno_contrato_inserido, contrato.getPagamentos())) {

								if (contrato.getSub_contrato() == 0 || contrato.getSub_contrato() == 3
										|| contrato.getSub_contrato() == 4 || contrato.getSub_contrato() == 5) {
									// e um contrato pai, nao fazer mais nada
									reverter = false;
									return 1;

								} else {
									// e um contrato filho, criar relacao;
									if (inserir_contrato_sub_contrato(contrato_pai.getId(),
											retorno_contrato_inserido)) {
										// inseriu corretamente
										return 1;

									} else {
										// houve alguma excessao ao criar as tabelas relacao contrato_sub_contrato,
										// devera ser feito o processo de
										// exclusao dessas tabelas
										/* fazer processo de exclusao das tabelas */
										reverter = true;
										// apagar tudo
										boolean v_remover_tabelas_relacao_contrato_corretor = remover_tabelas_contrato_corretor();
										boolean v_remover_tabelas_relacao_contrato_comprador = remover_tabelas_contrato_comprador();
										boolean v_remover_tabelas_relacao_contrato_vendedor = remover_tabelas_contrato_vendedor();
										boolean v_remover_tabelas_relacao_contrato_modelo_pagamentos = remover_tabelas_contrato_modelo_pagamento();
										boolean v_remover_tabelas_contrato_sub_contrato = remover_tabelas_contrato_sub_contrato();

										boolean v_remover_tabelas_relacao_contrato_tarefa = removerTarefas();

										boolean v_remover_contrato = remover_contrato(retorno_contrato_inserido);

										if (v_remover_tabelas_contrato_sub_contrato
												&& v_remover_tabelas_relacao_contrato_modelo_pagamentos
												&& v_remover_tabelas_relacao_contrato_vendedor
												&& v_remover_tabelas_relacao_contrato_comprador && v_remover_contrato
												&& v_remover_tabelas_relacao_contrato_tarefa
												&& v_remover_tabelas_relacao_contrato_corretor) {

											JOptionPane.showMessageDialog(null,
													"Erro ao inserir todas as informacoes do sub contrato no banco de"
															+ "dados\nBanco de Dados foi Normalizado!\nConsulte o administrador");
											return 0;
										} else {
											JOptionPane.showMessageDialog(null,
													"Erro ao inserir todas as informacoes do sub contrato ao banco de"
															+ "dados\nHouve corrupção na base de dados\nConsulte o administrador");
											return -1;
										}

									}
								}
							} else {
								// houve alguma excessao ao criar as tabelas relacao_contrato_pagamentos, devera
								// ser feito o processo de
								// exclusao dessas tabelas
								/* fazer processo de exclusao das tabelas */
								reverter = true;
								// apagar o contrato, as tarefas, a relacao contrato_corretor,
								// contrato_comprador e contrato_vendedor e os pagamentos
								boolean v_remover_tabelas_relacao_contrato_corretor = remover_tabelas_contrato_corretor();
								boolean v_remover_tabelas_relacao_contrato_comprador = remover_tabelas_contrato_comprador();
								boolean v_remover_tabelas_relacao_contrato_vendedor = remover_tabelas_contrato_vendedor();
								boolean v_remover_tabelas_relacao_contrato_modelo_pagamentos = remover_tabelas_contrato_modelo_pagamento();

								boolean v_remover_tabelas_relacao_contrato_tarefa = removerTarefas();

								boolean v_remover_contrato = remover_contrato(retorno_contrato_inserido);

								if (v_remover_tabelas_relacao_contrato_modelo_pagamentos
										&& v_remover_tabelas_relacao_contrato_vendedor
										&& v_remover_tabelas_relacao_contrato_comprador && v_remover_contrato
										&& v_remover_tabelas_relacao_contrato_tarefa
										&& v_remover_tabelas_relacao_contrato_corretor) {

									JOptionPane.showMessageDialog(null,
											"Erro ao inserir as tarefas, os compradores, vendedores e pagamentos ao contrato no banco de"
													+ "dados\nBanco de Dados foi Normalizado!\nConsulte o administrador");
									return 0;
								} else {
									JOptionPane.showMessageDialog(null,
											"Erro ao inserir as tarefas, os compradores, vendedores e pagamentos ao contrato no banco de"
													+ "dados\nHouve corrupção na base de dados\nConsulte o administrador");
									return -1;
								}

							}

						} else {
							// houve alguma excessao ao criar as tabelas relacação contrato_vendedor, devera
							// ser feito o processo de
							// exclusao dessas tabelas
							/* fazer processo de exclusao das tabelas */
							// apagar o contrato, as tarefas, a relacao contrato_corretor,
							// contrato_comprador e contrato_vendedor
							reverter = true;
							boolean v_remover_tabelas_relacao_contrato_corretor = remover_tabelas_contrato_corretor();
							boolean v_remover_tabelas_relacao_contrato_comprador = remover_tabelas_contrato_comprador();
							boolean v_remover_tabelas_relacao_contrato_vendedor = remover_tabelas_contrato_vendedor();

							boolean v_remover_tabelas_relacao_contrato_tarefa = removerTarefas();

							boolean v_remover_contrato = remover_contrato(retorno_contrato_inserido);

							if (v_remover_tabelas_relacao_contrato_vendedor
									&& v_remover_tabelas_relacao_contrato_comprador && v_remover_contrato
									&& v_remover_tabelas_relacao_contrato_tarefa
									&& v_remover_tabelas_relacao_contrato_corretor) {

								JOptionPane.showMessageDialog(null,
										"Erro ao inserir as tarefas, o corretor, os compradores e vendedores ao contrato no banco de"
												+ "dados\nBanco de Dados foi Normalizado!\nConsulte o administrador");
								return 0;
							} else {
								JOptionPane.showMessageDialog(null,
										"Erro ao inserir as tarefas, o corretor, os compradores e vendedores ao contrato no banco de"
												+ "dados\nHouve corrupção na base de dados\nConsulte o administrador");
								return -1;
							}

						}

					} else {
						// houve alguma excessao ao criar as tabelas relacação contrato_comprador,
						// devera ser feito o processo de
						// exclusao dessas tabelas
						/* fazer processo de exclusao das tabelas */
						reverter = true;
						// apagar o contrato, as tarefas, e a relacao contrato_corretor e a relacao,
						// contrato_comprador
						boolean v_remover_tabelas_relacao_contrato_corretor = remover_tabelas_contrato_corretor();
						boolean v_remover_tabelas_relacao_contrato_comprador = remover_tabelas_contrato_comprador();

						boolean v_remover_tabelas_relacao_contrato_tarefa = removerTarefas();

						boolean v_remover_contrato = remover_contrato(retorno_contrato_inserido);

						if (v_remover_tabelas_relacao_contrato_comprador && v_remover_contrato
								&& v_remover_tabelas_relacao_contrato_tarefa
								&& v_remover_tabelas_relacao_contrato_corretor) {

							JOptionPane.showMessageDialog(null,
									"Erro ao inserir as tarefas, o corretor e os compradores ao contrato no banco de"
											+ "dados\nBanco de Dados foi Normalizado!\nConsulte o administrador");
							return 0;
						} else {
							JOptionPane.showMessageDialog(null,
									"Erro ao inserir as tarefas, o corretor e os compradores ao contrato no banco de"
											+ "dados\nHouve corrupção na base de dados\nConsulte o administrador");
							return -1;
						}

					}

				} else {
					// houve alguma excessao ao criar as tabelas relacação contrato_corretor, devera
					// ser feito o processo de
					// exclusao dessas tabelas
					/* fazer processo de exclusao das tabelas */
					reverter = true;
					// apagar o contrato, as tarefas, e a relacao contrato corretor
					boolean v_remover_tabelas_relacao_contrato_corretor = remover_tabelas_contrato_corretor();
					boolean v_remover_tabelas_relacao_contrato_tarefa = removerTarefas();

					boolean v_remover_contrato = remover_contrato(retorno_contrato_inserido);

					if (v_remover_contrato && v_remover_tabelas_relacao_contrato_tarefa
							&& v_remover_tabelas_relacao_contrato_corretor) {

						JOptionPane.showMessageDialog(null, "Erro ao inserir tarefas e corretor ao contrato no banco de"
								+ "dados\nBanco de Dados foi Normalizado!\nConsulte o administrador");
						return 0;
					} else {
						JOptionPane.showMessageDialog(null,
								"Erro ao inserir tarefas e corretor ao contrato  no banco de"
										+ "dados\nHouve corrupção na base de dados\nConsulte o administrador");
						return -1;
					}

				}
			} else {
				// houve alguma excessao ao inserir as tarefas deve ser feito o processo de
				// exclusao dessas tabelas
				/* fazer processo de exclusao das tabelas */
				reverter = true;
				boolean v_remover_contrato = remover_contrato(retorno_contrato_inserido);

				boolean v_remover_tabelas_relacao_contrato_tarefa = removerTarefas();
				if (v_remover_contrato && v_remover_tabelas_relacao_contrato_tarefa) {

					JOptionPane.showMessageDialog(null, "Erro ao inserir as tarefas a este contrato no banco de"
							+ "dados\nBanco de Dados foi Normalizado!\nConsulte o administrador");
					return 0;
				} else {
					JOptionPane.showMessageDialog(null, "Erro ao inserir as tarefas a este contrato no banco de"
							+ "dados\nHouve corrupção na base de dados\nConsulte o administrador");
					return -1;
				}

			}

		} else {
			JOptionPane.showMessageDialog(null,
					"Erro ao inserir somente o contrato no banco de "
							+ "dados\nNão houve corrupção na base de dados\nConsulte o administrador"
							+ "\nPossivéis erros relacionados a falta de conexão com o banco de dados ");
			return 0;
		}

	}

	private int inserir_contrato_retorno(CadastroContrato contrato) {
		int result = -1;
		int id_cliente = -1;
		if (contrato != null) {
			Connection conn = null;
			try {

				conn = ConexaoBanco.getConexao();

				String query = sql_contrato(contrato);
				Statement stmt = (Statement) conn.createStatement();
				int numero = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					result = rs.getInt(1);
					System.out.println("Id contrato inserido: " + result);
				}
				rs.close();
				stmt.close();

				return result;

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
				"Erro ao inserir o Contrato no banco de dados, Erro: " + e.getMessage() + "\nCausa: " + e.getCause() );
				GerenciadorLog.registrarLogDiario("falha", "falha ao adicionar novo contrato: \nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
				return -1;
			}
		} else {
			System.out.println("O Contrato enviado por parametro esta vazio");
			return -1;
		}
	}

	public boolean atualizarInfoExtras(CadastroContrato contrato) {
		Connection conn = null;
		String atualizar = null;
		PreparedStatement pstm;
		/*
		 * localizacao text bruto_livre text fertilizante text penhor int(3)
		 * status_penhor text optante_folha int(3) status_optante_folha text descricao
		 * text observacao text
		 */
		String sql_update_contrato = "update contrato set  localizacao = ?, bruto_livre = ?,"
				+ "fertilizante = ?, status_penhor = ?, optante_folha = ?, status_optante_folha = ?,"
				+ "descricao = ?, observacao = ?, grupo_particular = ? where id = ?";

		try {
			String texto_clausulas = "";
			int num_clausulas = 1;
			for (String clausula_local : contrato.getClausulas()) {
				texto_clausulas += (clausula_local + ";");
				num_clausulas++;
			}

			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(sql_update_contrato);

			pstm.setString(1, contrato.getLocalizacao());
			pstm.setString(2, contrato.getBruto_livre());
			pstm.setString(3, contrato.getFertilizante());
			pstm.setString(4, contrato.getStatus_penhor());

			pstm.setInt(5, contrato.getOptante_folha());
			pstm.setString(6, contrato.getStatus_optante_folha());
			pstm.setString(7, contrato.getDescricao());
			pstm.setString(8, contrato.getObservacao());
			pstm.setInt(9, contrato.getGrupo_particular());
			pstm.setInt(10, contrato.getId());
			pstm.execute();

			ConexaoBanco.fechaConexao(conn);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	private boolean inserir_contrato_corretor(CadastroContrato contrato, int id_contrato) {

		boolean retorno = false;
		// verifica se a corretores a serem cadastrados
		boolean tem_corretores = false;

		CadastroCliente corretores[] = contrato.listaCorretores();
		for (CadastroCliente corretor : corretores) {
			if (corretor != null) {
				tem_corretores = true;
				break;
			} else {
				tem_corretores = false;
			}
		}

		if (tem_corretores) {
			for (int i = 0; i < corretores.length; i++) {
				if (corretores[i] != null) {
					if (inserir_corretor(corretores[i], id_contrato)) {
						// tabela criada com exito;
						System.out.println("Relação contrato corretor criado com sucesso");
						retorno = true;
					} else {
						// falha ao adicionar algum dos corretores, o processo deve ser desfeito
						System.out.println("falha ao adicionar a relação contrato corretor na base de dados");

						retorno = false;
						break;
					}
				}
			}

			return retorno;
		} else {
			return true;
		}
	}

	private boolean inserir_contrato_comprador(CadastroContrato contrato, int id_contrato) {

		boolean reverter = false;

		CadastroCliente compradores[] = contrato.listaCompradores();
		for (int i = 0; i < compradores.length; i++) {
			if (compradores[i] != null) {
				if (inserir_comprador(compradores[i], id_contrato)) {
					// tabela criada com exito;
					System.out.println("Relação contrato_comprador criado com sucesso");
					reverter = false;
				} else {
					// falha ao adicionar algum dos corretores, o processo deve ser desfeito
					System.out.println("falha ao adicionar a relação contrato_comprador na base de dados");

					reverter = true;
					break;
				}
			}
		}

		if (reverter == true) {
			return false;
			// retorna fase, devera ser revertido o processo
			// reverter todo o processo
		} else {
			// todos os corretorres forama dicionados com sucesso, retorne true para
			// continuar o processo
			return true;
		}

	}

	private boolean inserir_contrato_vendedor(CadastroContrato contrato, int id_contrato) {

		boolean reverter = false;

		CadastroCliente vendedores[] = contrato.listaVendedores();
		for (int i = 0; i < vendedores.length; i++) {
			if (vendedores[i] != null) {
				if (inserir_vendedor(vendedores[i], id_contrato)) {
					// tabela criada com exito;
					System.out.println("Relação contrato_vendedor criado com sucesso");
					reverter = false;
				} else {
					// falha ao adicionar algum dos corretores, o processo deve ser desfeito
					System.out.println("falha ao adicionar a relação contrato_vendedor na base de dados");

					reverter = true;
					break;
				}
			}
		}

		if (reverter == true) {
			return false;
			// retorna fase, devera ser revertido o processo
			// reverter todo o processo
		} else {
			// todos os corretorres forama dicionados com sucesso, retorne true para
			// continuar o processo
			return true;
		}

	}

	private boolean inserir_corretor(CadastroCliente corretor, int id_contrato) {
		Connection conn = null;
		try {
			conn = ConexaoBanco.getConexao();
			String sql = "insert into contrato_corretor\r\n" + "(id_contrato, id_cliente) values ('" + id_contrato
					+ "','" + corretor.getId() + "')";

			PreparedStatement grava = (PreparedStatement) conn.prepareStatement(sql);
			grava.execute();
			ConexaoBanco.fechaConexao(conn, grava);
			// JOptionPane.showMessageDialog(null, "Relação contrato_corretor cadastrado com
			// sucesso");
			registro_geral_relacao_contrato_corretor.setIdContrato(id_contrato);
			registro_geral_relacao_contrato_corretor.adicionar_id(corretor.getId());
			return true;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao inserir a relação cliente_corretor no banco de"
					+ " dados, corretor: " + corretor.getId() + " contrado: " + id_contrato);
			return false;
		}

	}

	private boolean inserir_comprador(CadastroCliente comprador, int id_contrato) {
		Connection conn = null;
		try {
			conn = ConexaoBanco.getConexao();
			String sql = "insert into contrato_comprador\r\n" + "(id_contrato, id_cliente) values ('" + id_contrato
					+ "','" + comprador.getId() + "')";

			PreparedStatement grava = (PreparedStatement) conn.prepareStatement(sql);
			grava.execute();
			ConexaoBanco.fechaConexao(conn, grava);
			// JOptionPane.showMessageDialog(null, "Relação contrato_comprador cadastrado
			// com sucesso");
			registro_geral_relacao_contrato_comprador.setIdContrato(id_contrato);
			registro_geral_relacao_contrato_comprador.adicionar_id(comprador.getId());
			return true;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao inserir a relação cliente_corretor no banco de"
					+ " dados, corretor: " + comprador.getId() + " contrado: " + id_contrato);
			return false;
		}

	}

	private boolean inserir_vendedor(CadastroCliente vendedor, int id_contrato) {
		Connection conn = null;
		try {
			conn = ConexaoBanco.getConexao();
			String sql = "insert into contrato_vendedor\r\n" + "(id_contrato, id_cliente) values ('" + id_contrato
					+ "','" + vendedor.getId() + "')";

			PreparedStatement grava = (PreparedStatement) conn.prepareStatement(sql);
			grava.execute();
			ConexaoBanco.fechaConexao(conn, grava);
			// JOptionPane.showMessageDialog(null, "Relação contrato_vendedor cadastrado com
			// sucesso");
			registro_geral_relacao_contrato_vendedor.setIdContrato(id_contrato);
			registro_geral_relacao_contrato_vendedor.adicionar_id(vendedor.getId());
			return true;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao inserir a relação cliente_vendedor no banco de"
					+ " dados, corretor: " + vendedor.getId() + " contrado: " + id_contrato);
			return false;
		}

	}

	public class Registro {
		// classe que registra durante o processo de cadastramento de contrato no banco
		// de dados
		// tanto o id_contrato como o id_cliente para que se houver falha o processo
		// seja desfeito.

		private int id_contrato;
		private ArrayList<Integer> ids_clientes;

		public Registro() {
			ids_clientes = new ArrayList<Integer>();

		}

		private void setIdContrato(int idContrato) {
			this.id_contrato = idContrato;
		}

		private int getIdContrato() {
			return this.id_contrato;
		}

		private void adicionar_id(int id_adicionar) {
			ids_clientes.add(id_adicionar);
		}

		private ArrayList<Integer> getIds() {
			return this.ids_clientes;
		}

	}

	public void getDadosGlobais() {
		// gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		GerenciadorLog = dados.getGerenciadorLog();

		// usuario logado
		loginGlobal = dados.getLogin();
	}

	private String sql_contrato(CadastroContrato contrato) {
		String texto_clausulas = "";
		for (String clausula_local : contrato.getClausulas()) {
			texto_clausulas += (clausula_local + ";");
		}

		String query = "insert into contrato (codigo, sub_contrato,id_safra, id_produto, medida, quantidade, valor_produto, valor_a_pagar,frete, clausula_frete,  armazenagem, clausula_armazenagem, fundo_rural, clausula_fundo_rural, comissao, clausula_comissao, valor_comissao, clausulas, id_local_retirada, tipo_entrega, data_contrato, data_entrega, status_contrato, caminho_diretorio, caminho_arquivo, nome_arquivo,caminho_diretorio2, caminho_arquivo2, nome_arquivo2, grupo_particular ) values ('"
				+ contrato.getCodigo() + "','" + contrato.getSub_contrato() + "','"
				+ contrato.getModelo_safra().getId_safra() + "','" + contrato.getModelo_produto().getId_produto()
				+ "','" + contrato.getMedida() + "','" + contrato.getQuantidade() + "','" + contrato.getValor_produto()
				+ "','" + contrato.getValor_a_pagar() + "','" + contrato.getFrete() + "','"
				+ contrato.getClausula_frete() + "','" + contrato.getArmazenagem() + "','"
				+ contrato.getClausula_armazenagem() + "','"
						+ contrato.getFundo_rural() + "','"
								+ contrato.getClausula_fundo_rural() + "','"
				+ contrato.getComissao() + "','"
				+ contrato.getClausula_comissao() + "','" + contrato.getValor_comissao() + "','" + texto_clausulas
				+ "','" + contrato.getId_local_retirada() + "','" + contrato.getTipo_entrega() + "','"
				+ contrato.getData_contrato() + "','" + contrato.getData_entrega() + "','"
				+ contrato.getStatus_contrato() + "','"

				+ contrato.getCaminho_diretorio_contrato() + "','" + contrato.getCaminho_arquivo() + "','"
				+ contrato.getNome_arquivo() + "','"

				+ contrato.getCaminho_diretorio_contrato2() + "','" + contrato.getCaminho_arquivo2() + "','"
				+ contrato.getNome_arquivo2()  + "','" + contrato.getGrupo_particular()

				+ "')";
		return query;

	}

	private boolean remover_contrato_corretor(int id_contrato, int id_cliente) {
		String sql_delete_relacao = "DELETE FROM contrato_corretor WHERE id_contrato = ? and id_cliente = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_delete_relacao);

			pstm.setInt(1, id_contrato);
			pstm.setInt(2, id_cliente);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			// JOptionPane.showMessageDialog(null, "Relação contrato_corretor excluido,
			// banco normalizado ");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao exlcuir relação contrato_corretor do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
							+ "dados " + f.getMessage());
			return false;
		}

	}

	private boolean remover_contrato_comprador(int id_contrato, int id_cliente) {
		String sql_delete_relacao = "DELETE FROM contrato_comprador WHERE id_contrato = ? and id_cliente = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_delete_relacao);

			pstm.setInt(1, id_contrato);
			pstm.setInt(2, id_cliente);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			// JOptionPane.showMessageDialog(null, "Relação contrato_comprador excluido,
			// banco normalizado ");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao exlcuir relação contrato_comprador do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
							+ "dados " + f.getMessage());
			return false;
		}

	}

	private boolean remover_contrato_vendedor(int id_contrato, int id_cliente) {
		String sql_delete_relacao = "DELETE FROM contrato_vendedor WHERE id_contrato = ? and id_cliente = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_delete_relacao);

			pstm.setInt(1, id_contrato);
			pstm.setInt(2, id_cliente);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			// JOptionPane.showMessageDialog(null, "Relação contrato_vendedor excluido,
			// banco normalizado ");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao exlcuir relação contrato_vendedor do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
							+ "dados " + f.getMessage());
			return false;
		}

	}

	private boolean remover_contrato(int id_contrato) {
		String sql_delete_relacao = "DELETE FROM contrato WHERE id = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_delete_relacao);

			pstm.setInt(1, id_contrato);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			JOptionPane.showMessageDialog(null, "Contrato excluido com sucesso!");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao excluir contrato da base de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
							+ "dados " + f.getMessage());
			return false;
		}
	}

	public boolean remover_contrato_rotina(int id_contrato) {
		String sql_delete_relacao = "call excluir_contrato(?)";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_delete_relacao);

			pstm.setInt(1, id_contrato);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			JOptionPane.showMessageDialog(null, "Toda as rotinas do contrato foram excluidas com sucesso!");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao excluir as rotinas do contrato da base de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
							+ "dados " + f.getMessage());
			return false;
		}
	}

	public boolean remover_sub_contrato_rotina(int id_contrato) {
		String sql_delete_relacao = "call excluir_sub_contrato(?)";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_delete_relacao);

			pstm.setInt(1, id_contrato);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			JOptionPane.showMessageDialog(null, "Toda as rotinas do contrato foram excluidas com sucesso!");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao excluir as rotinas do contrato da base de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
							+ "dados " + f.getMessage());
			return false;
		}
	}

	private boolean remover_tabelas_contrato_corretor() {
		boolean retorno = false;

		for (int codigo_relacao_contrato_corretor : registro_geral_relacao_contrato_corretor.getIds()) {
			if (remover_contrato_corretor(registro_geral_relacao_contrato_corretor.getIdContrato(),
					codigo_relacao_contrato_corretor)) {
				retorno = true;
			} else {
				retorno = false;
				break;
			}
		}

		return retorno;
	}

	private boolean remover_tabelas_contrato_comprador() {
		boolean retorno = false;

		for (int codigo_relacao_contrato_corretor : registro_geral_relacao_contrato_comprador.getIds()) {
			if (remover_contrato_corretor(registro_geral_relacao_contrato_comprador.getIdContrato(),
					codigo_relacao_contrato_corretor)) {
				retorno = true;
			} else {
				retorno = false;
				break;
			}
		}

		return retorno;
	}

	private boolean remover_tabelas_contrato_vendedor() {
		boolean retorno = false;

		for (int codigo_relacao_contrato_corretor : registro_geral_relacao_contrato_vendedor.getIds()) {
			if (remover_contrato_corretor(registro_geral_relacao_contrato_vendedor.getIdContrato(),
					codigo_relacao_contrato_corretor)) {
				retorno = true;
			} else {
				retorno = false;
				break;
			}
		}

		return retorno;
	}

	public String sql_modelo_pagamento(CadastroContrato.CadastroPagamento pagamento) {
		String query = "insert into modelo_pagamento (id_conta_bancaria, data_pagamento, valor, descricao_pagamento, antecipado) values ('"
				+ pagamento.getConta().getId_conta() + "','" + pagamento.getData_pagamento() + "','"
				+ pagamento.getValor() + "','" + pagamento.getDescricao_pagamento() + "','"
				+ pagamento.getPagamento_adiantado() + "')";
		return query;

	}

	public String sql_contrato_modelo_pagamento(int id_contrato, int id_pagamento) {
		String query = "insert into contrato_modelo_pagamento (id_contrato, id_pagamento) values ('" + id_contrato
				+ "','" + id_pagamento + "')";
		return query;

	}

	public boolean inserirModelosPagamentos(int id_contrato, ArrayList<CadastroContrato.CadastroPagamento> pagamentos) {
		boolean retorno = false;

		if (pagamentos != null && pagamentos.size() > 0) {
			for (CadastroContrato.CadastroPagamento pagamento : pagamentos) {
				if (pagamento.getId() == 0) {
					int retorno_positivo = inserir_modelo_pagamento(pagamento);
					if (retorno_positivo != -1) {
						boolean retorno_positivo_relacao = inserir_contrato_modelo_pagamento(id_contrato,
								retorno_positivo);
						if (retorno_positivo_relacao) {
							retorno = true;
						} else {
							retorno = false;
							break;
						}

					} else {
						retorno = false;
						// houve algum erro ao adicionar um pagamento, retorno falso para reverter todo
						// o processo
						break;
					}
				} else {
					// esse pagamento ja esta cadatrado
					retorno = true;
				}
			}
		}
		return retorno;

	}

	private int inserir_modelo_pagamento(CadastroContrato.CadastroPagamento pagamento) {
		int result = -1;
		int id_cliente = -1;
		if (pagamento != null) {
			Connection conn = null;
			try {

				conn = ConexaoBanco.getConexao();

				String query = sql_modelo_pagamento(pagamento);
				Statement stmt = (Statement) conn.createStatement();
				int numero = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					result = rs.getInt(1);
					System.out.println("Id pagamento inserido: " + result);
				}
				rs.close();
				stmt.close();

				return result;

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao inserir o pagamento no banco de" + "dados ");
				GerenciadorLog.registrarLogDiario("falha", "falha ao adicionar novo pagamento: " + " causa: ");
				return -1;
			}
		} else {
			System.out.println("O pagamento enviado por parametro esta vazio");
			return -1;
		}
	}

	private boolean inserir_contrato_modelo_pagamento(int id_contrato, int id_pagamento) {
		Connection conn = null;
		try {
			conn = ConexaoBanco.getConexao();
			String sql = "insert into contrato_modelo_pagamento\r\n" + "(id_contrato, id_pagamento) values ('"
					+ id_contrato + "','" + id_pagamento + "')";

			PreparedStatement grava = (PreparedStatement) conn.prepareStatement(sql);
			grava.execute();
			ConexaoBanco.fechaConexao(conn, grava);
			// JOptionPane.showMessageDialog(null, "Relação contrato_modelo_pagamento
			// cadastrado com sucesso");
			registro_geral_relacao_contrato_modelo_pagamento.setIdContrato(id_contrato);
			registro_geral_relacao_contrato_modelo_pagamento.adicionar_id(id_pagamento);
			return true;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao inserir a relação contrato_modelo_pagamento no banco de");
			return false;
		}

	}

	private boolean remover_tabelas_contrato_modelo_pagamento() {
		boolean retorno = false;

		for (int codigo_relacao_contrato_modelo_pagamento : registro_geral_relacao_contrato_modelo_pagamento.getIds()) {
			if (remover_contrato_modelo_pagamento(registro_geral_relacao_contrato_modelo_pagamento.getIdContrato(),
					codigo_relacao_contrato_modelo_pagamento)) {
				retorno = true;
			} else {
				retorno = false;
				break;
			}
		}

		return retorno;
	}

	private boolean remover_modelo_pagamento(int id_pagamento) {
		String sql_dele_cliente = "DELETE FROM modelo_pagamento WHERE id = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_dele_cliente);

			pstm.setInt(1, id_pagamento);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			JOptionPane.showMessageDialog(null, "modelo_pagamento Excluido, banco normalizado ");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao excluir o modelo_pagamento do banco de" + "dados " + f.getMessage());
			return false;
		}
	}

	private boolean remover_tabelas_modelo_pagamento() {
		boolean retorno = false;

		for (int codigo_relacao_contrato_modelo_pagamento : registro_geral_relacao_contrato_modelo_pagamento.getIds()) {
			if (remover_modelo_pagamento(codigo_relacao_contrato_modelo_pagamento)) {
				retorno = true;
			} else {
				retorno = false;
				break;
			}
		}

		return retorno;
	}

	private boolean remover_contrato_modelo_pagamento(int id_contrato, int id_pagamento) {
		String sql_delete_relacao = "DELETE FROM contrato_modelo_pagamento WHERE id_contrato = ? and id_pagamento = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_delete_relacao);

			pstm.setInt(1, id_contrato);
			pstm.setInt(2, id_pagamento);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			// JOptionPane.showMessageDialog(null, "Relação contrato_modelo_pagamento
			// excluido, banco normalizado ");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao exlcuir relação contrato_modelo_pagamento do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
							+ "dados " + f.getMessage());
			return false;
		}

	}

	public CadastroContrato getContrato(int id) {

		String selectContrato = "select * from contrato where id = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		CadastroContrato contrato = new CadastroContrato();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectContrato);
			pstm.setInt(1, id);

			rs = pstm.executeQuery();
			rs.next();

			contrato.setId(rs.getInt("id"));
			contrato.setCodigo(rs.getString("codigo"));
			contrato.setSub_contrato(rs.getInt("sub_contrato"));
			contrato.setTexto_clausulas(rs.getString("clausulas"));

			int id_safra = rs.getInt("id_safra");

			GerenciarBancoSafras gerenciar = new GerenciarBancoSafras();
			CadastroSafra safra = gerenciar.getSafra(id_safra);

			if (safra != null) {

				contrato.setQuantidade(Double.parseDouble(rs.getString("quantidade")));
				contrato.setMedida(rs.getString("medida"));

				contrato.setValor_produto(Double.parseDouble(rs.getString("valor_produto")));
				contrato.setValor_a_pagar(new BigDecimal(rs.getString("valor_a_pagar")));

				contrato.setModelo_safra(safra);

				// produto
				GerenciarBancoProdutos gerenciar_prod = new GerenciarBancoProdutos();
				CadastroProduto prod = gerenciar_prod.getProduto(safra.getProduto().getId_produto());
				contrato.setModelo_produto(prod);

				GerenciarBancoContratos gerenciar_corretores = new GerenciarBancoContratos();
				CadastroCliente corretores[] = gerenciar_corretores.getCorretores(id);
				contrato.setCorretores(corretores);

				GerenciarBancoContratos gerenciar_vendedores = new GerenciarBancoContratos();
				CadastroCliente vendedores[] = gerenciar_vendedores.getVendedores(id);
				contrato.setVendedores(vendedores);

				GerenciarBancoContratos gerenciar_compradores = new GerenciarBancoContratos();
				CadastroCliente compradores[] = gerenciar_compradores.getCompradores(id);
				contrato.setCompradores(compradores);

				GerenciarBancoContratos gerenciar_pagamentos = new GerenciarBancoContratos();
				ArrayList<CadastroContrato.CadastroPagamento> pagamentos_contrato = gerenciar_pagamentos
						.getPagamentos(id);
				contrato.setPagamentos(pagamentos_contrato);

				contrato.setStatus_contrato(rs.getInt("status_contrato"));
				contrato.setData_contrato(rs.getString("data_contrato"));
				contrato.setData_entrega(rs.getString("data_entrega"));

				contrato.setCaminho_diretorio_contrato(rs.getString("caminho_diretorio"));
				contrato.setCaminho_arquivo(rs.getString("caminho_arquivo"));
				contrato.setNome_arquivo(rs.getString("nome_arquivo"));

				contrato.setCaminho_diretorio_contrato2(rs.getString("caminho_diretorio2"));
				contrato.setCaminho_arquivo2(rs.getString("caminho_arquivo2"));
				contrato.setNome_arquivo2(rs.getString("nome_arquivo2"));

				// dados de comissao
				contrato.setComissao(rs.getInt("comissao"));
				if (contrato.getComissao() == 1) {
					contrato.setClausula_comissao(rs.getInt("clausula_comissao"));
					contrato.setValor_comissao(new BigDecimal(rs.getString("valor_comissao")));
				}

				// dados de frete
				contrato.setFrete(rs.getString("frete"));
				contrato.setClausula_frete(rs.getString("clausula_frete"));
				// dados de armazenagem
				contrato.setArmazenagem(rs.getString("armazenagem"));
				contrato.setClausula_armazenagem(rs.getString("clausula_armazenagem"));

				// dados de fundo rural
				contrato.setFundo_rural(rs.getString("fundo_rural"));
				contrato.setClausula_fundo_rural(rs.getString("clausula_fundo_rural"));

				
				// dados de retirada

				contrato.setId_local_retirada(rs.getInt("id_local_retirada"));
				contrato.setTipo_entrega(rs.getInt("tipo_entrega"));

				// informacoes extras

				contrato.setLocalizacao(rs.getString("localizacao"));
				contrato.setBruto_livre(rs.getString("bruto_livre"));
				contrato.setFertilizante(rs.getString("fertilizante"));
				contrato.setStatus_penhor(rs.getString("status_penhor"));
				contrato.setOptante_folha(rs.getInt("optante_folha"));
				contrato.setStatus_optante_folha(rs.getString("status_optante_folha"));
				contrato.setDescricao(rs.getString("descricao"));
				contrato.setObservacao(rs.getString("observacao"));
				contrato.setGrupo_particular(rs.getInt("grupo_particular"));

				ConexaoBanco.fechaConexao(conn, pstm, rs);

				return contrato;
			} else {

				return null;
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar contrato id: " + id + " erro: ");
			System.out.println("Erro ao listar contrato id: " + id + " erro: " + "\ncausa: ");
			return null;
		}

	}

	public CadastroContrato getContratoSimplificado(int id) {

		String selectContrato = "select * from contrato where id = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		CadastroContrato contrato = new CadastroContrato();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectContrato);
			pstm.setInt(1, id);

			rs = pstm.executeQuery();
			rs.next();

			contrato.setId(rs.getInt("id"));
			contrato.setCodigo(rs.getString("codigo"));
			contrato.setSub_contrato(rs.getInt("sub_contrato"));

			int id_safra = rs.getInt("id_safra");

			GerenciarBancoSafras gerenciar = new GerenciarBancoSafras();
			CadastroSafra safra = gerenciar.getSafra(id_safra);

			if (safra != null) {

				contrato.setQuantidade(Double.parseDouble(rs.getString("quantidade")));
				contrato.setMedida(rs.getString("medida"));

				contrato.setValor_produto(Double.parseDouble(rs.getString("valor_produto")));
				contrato.setValor_a_pagar(new BigDecimal(rs.getString("valor_a_pagar")));

				contrato.setModelo_safra(safra);

				// produto
				GerenciarBancoProdutos gerenciar_prod = new GerenciarBancoProdutos();
				CadastroProduto prod = gerenciar_prod.getProduto(safra.getProduto().getId_produto());
				contrato.setModelo_produto(prod);

				GerenciarBancoContratos gerenciar_vendedores = new GerenciarBancoContratos();
				CadastroCliente vendedores[] = gerenciar_vendedores.getVendedores(id);
				contrato.setVendedores(vendedores);

				GerenciarBancoContratos gerenciar_compradores = new GerenciarBancoContratos();
				CadastroCliente compradores[] = gerenciar_compradores.getCompradores(id);
				contrato.setCompradores(compradores);

				contrato.setStatus_contrato(rs.getInt("status_contrato"));
				contrato.setData_contrato(rs.getString("data_contrato"));
				contrato.setData_entrega(rs.getString("data_entrega"));

				contrato.setCaminho_diretorio_contrato(rs.getString("caminho_diretorio"));
				contrato.setCaminho_arquivo(rs.getString("caminho_arquivo"));
				contrato.setNome_arquivo(rs.getString("nome_arquivo"));

				contrato.setCaminho_diretorio_contrato2(rs.getString("caminho_diretorio2"));
				contrato.setCaminho_arquivo2(rs.getString("caminho_arquivo2"));
				contrato.setNome_arquivo2(rs.getString("nome_arquivo2"));

				ConexaoBanco.fechaConexao(conn, pstm, rs);

				return contrato;
			} else {

				return null;
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar contrato id: " + id + " erro: ");
			System.out.println("Erro ao listar contrato id: " + id + " erro: " + "\ncausa: ");
			return null;
		}

	}

	public CadastroContrato getContratoPorCodigo(String id_codigo) {

		String selectContrato = "select * from contrato where codigo = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		CadastroContrato contrato = new CadastroContrato();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectContrato);
			pstm.setString(1, id_codigo);

			rs = pstm.executeQuery();
			rs.next();

			contrato.setId(rs.getInt("id"));
			contrato.setCodigo(rs.getString("codigo"));
			contrato.setSub_contrato(rs.getInt("sub_contrato"));
			contrato.setTexto_clausulas(rs.getString("clausulas"));

			int id_safra = rs.getInt("id_safra");

			GerenciarBancoSafras gerenciar = new GerenciarBancoSafras();
			CadastroSafra safra = gerenciar.getSafra(id_safra);

			if (safra != null) {

				contrato.setQuantidade(Double.parseDouble(rs.getString("quantidade")));
				contrato.setMedida(rs.getString("medida"));

				contrato.setValor_produto(Double.parseDouble(rs.getString("valor_produto")));
				contrato.setValor_a_pagar(new BigDecimal(rs.getString("valor_a_pagar")));

				contrato.setModelo_safra(safra);

				// produto
				GerenciarBancoProdutos gerenciar_prod = new GerenciarBancoProdutos();
				CadastroProduto prod = gerenciar_prod.getProduto(safra.getProduto().getId_produto());
				contrato.setModelo_produto(prod);

				GerenciarBancoContratos gerenciar_corretores = new GerenciarBancoContratos();
				CadastroCliente corretores[] = gerenciar_corretores.getCorretores(contrato.getId());
				contrato.setCorretores(corretores);

				GerenciarBancoContratos gerenciar_vendedores = new GerenciarBancoContratos();
				CadastroCliente vendedores[] = gerenciar_vendedores.getVendedores(contrato.getId());
				contrato.setVendedores(vendedores);

				GerenciarBancoContratos gerenciar_compradores = new GerenciarBancoContratos();
				CadastroCliente compradores[] = gerenciar_compradores.getCompradores(contrato.getId());
				contrato.setCompradores(compradores);

				GerenciarBancoContratos gerenciar_pagamentos = new GerenciarBancoContratos();
				ArrayList<CadastroContrato.CadastroPagamento> pagamentos_contrato = gerenciar_pagamentos
						.getPagamentos(contrato.getId());
				contrato.setPagamentos(pagamentos_contrato);

				contrato.setStatus_contrato(rs.getInt("status_contrato"));
				contrato.setData_contrato(rs.getString("data_contrato"));
				contrato.setData_entrega(rs.getString("data_entrega"));
				contrato.setCaminho_diretorio_contrato(rs.getString("caminho_diretorio"));
				contrato.setCaminho_arquivo(rs.getString("caminho_arquivo"));
				contrato.setNome_arquivo(rs.getString("nome_arquivo"));

				contrato.setCaminho_diretorio_contrato2(rs.getString("caminho_diretorio2"));
				contrato.setCaminho_arquivo2(rs.getString("caminho_arquivo2"));
				contrato.setNome_arquivo2(rs.getString("nome_arquivo2"));

				// dados de comissao
				contrato.setComissao(rs.getInt("comissao"));
				if (contrato.getComissao() == 1) {
					contrato.setClausula_comissao(rs.getInt("clausula_comissao"));
					contrato.setValor_comissao(new BigDecimal(rs.getString("valor_comissao")));
				}

				// dados de frete
				contrato.setFrete(rs.getString("frete"));
				contrato.setClausula_frete(rs.getString("clausula_frete"));
				// dados de armazenagem
				contrato.setArmazenagem(rs.getString("armazenagem"));
				contrato.setClausula_armazenagem(rs.getString("clausula_armazenagem"));

				// dados de fundo rural
				contrato.setFundo_rural(rs.getString("fundo_rural"));
				contrato.setClausula_fundo_rural(rs.getString("clausula_fundo_rural"));

				
				// dados de retirada

				contrato.setId_local_retirada(rs.getInt("id_local_retirada"));
				contrato.setTipo_entrega(rs.getInt("tipo_entrega"));

//informacoes extras

				contrato.setLocalizacao(rs.getString("localizacao"));
				contrato.setBruto_livre(rs.getString("bruto_livre"));
				contrato.setFertilizante(rs.getString("fertilizante"));
				contrato.setStatus_penhor(rs.getString("status_penhor"));
				contrato.setOptante_folha(rs.getInt("optante_folha"));
				contrato.setStatus_optante_folha(rs.getString("status_optante_folha"));
				contrato.setDescricao(rs.getString("descricao"));
				contrato.setObservacao(rs.getString("observacao"));
				contrato.setGrupo_particular(rs.getInt("grupo_particular"));

				ConexaoBanco.fechaConexao(conn, pstm, rs);

				return contrato;
			} else {

				return null;
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar contrato codigo: " + id_codigo + " erro: ");
			System.out.println("Erro ao listar contrato codigo: " + id_codigo + " erro: " + "\ncausa: ");
			return null;
		}

	}

	public CadastroContrato getContratoPai(int id_sub_contrato) {

		String selectContrato = "select * from contrato\n"
				+ "left join contrato_sub_contrato on contrato_sub_contrato.id_contrato_pai = contrato.id\n"
				+ " where id_sub_contrato = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		CadastroContrato contrato = new CadastroContrato();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectContrato);
			pstm.setInt(1, id_sub_contrato);

			rs = pstm.executeQuery();
			rs.next();

			contrato.setId(rs.getInt("id"));
			contrato.setCodigo(rs.getString("codigo"));
			contrato.setSub_contrato(rs.getInt("sub_contrato"));
			contrato.setTexto_clausulas(rs.getString("clausulas"));

			int id_safra = rs.getInt("id_safra");

			GerenciarBancoSafras gerenciar = new GerenciarBancoSafras();
			CadastroSafra safra = gerenciar.getSafra(id_safra);

			if (safra != null) {

				contrato.setQuantidade(Double.parseDouble(rs.getString("quantidade")));
				contrato.setMedida(rs.getString("medida"));

				contrato.setValor_produto(Double.parseDouble(rs.getString("valor_produto")));
				contrato.setValor_a_pagar(new BigDecimal(rs.getString("valor_a_pagar")));

				contrato.setModelo_safra(safra);

				// produto
				GerenciarBancoProdutos gerenciar_prod = new GerenciarBancoProdutos();
				CadastroProduto prod = gerenciar_prod.getProduto(safra.getProduto().getId_produto());
				contrato.setModelo_produto(prod);

				GerenciarBancoContratos gerenciar_corretores = new GerenciarBancoContratos();
				CadastroCliente corretores[] = gerenciar_corretores.getCorretores(contrato.getId());
				contrato.setCorretores(corretores);

				GerenciarBancoContratos gerenciar_vendedores = new GerenciarBancoContratos();
				CadastroCliente vendedores[] = gerenciar_vendedores.getVendedores(contrato.getId());
				contrato.setVendedores(vendedores);

				GerenciarBancoContratos gerenciar_compradores = new GerenciarBancoContratos();
				CadastroCliente compradores[] = gerenciar_compradores.getCompradores(contrato.getId());
				contrato.setCompradores(compradores);

				GerenciarBancoContratos gerenciar_pagamentos = new GerenciarBancoContratos();
				ArrayList<CadastroContrato.CadastroPagamento> pagamentos_contrato = gerenciar_pagamentos
						.getPagamentos(contrato.getId());
				contrato.setPagamentos(pagamentos_contrato);

				contrato.setStatus_contrato(rs.getInt("status_contrato"));
				contrato.setData_contrato(rs.getString("data_contrato"));
				contrato.setData_entrega(rs.getString("data_entrega"));
				contrato.setCaminho_diretorio_contrato(rs.getString("caminho_diretorio"));
				contrato.setCaminho_arquivo(rs.getString("caminho_arquivo"));
				contrato.setNome_arquivo(rs.getString("nome_arquivo"));

				contrato.setCaminho_diretorio_contrato2(rs.getString("caminho_diretorio2"));
				contrato.setCaminho_arquivo2(rs.getString("caminho_arquivo2"));
				contrato.setNome_arquivo2(rs.getString("nome_arquivo2"));

				// dados de comissao
				contrato.setComissao(rs.getInt("comissao"));
				if (contrato.getComissao() == 1) {
					contrato.setClausula_comissao(rs.getInt("clausula_comissao"));
					contrato.setValor_comissao(new BigDecimal(rs.getString("valor_comissao")));
				}

				// dados de frete
				contrato.setFrete(rs.getString("frete"));
				contrato.setClausula_frete(rs.getString("clausula_frete"));
				// dados de armazenagem
				contrato.setArmazenagem(rs.getString("armazenagem"));
				contrato.setClausula_armazenagem(rs.getString("clausula_armazenagem"));

				// dados de fundo rural
				contrato.setFundo_rural(rs.getString("fundo_rural"));
				contrato.setClausula_fundo_rural(rs.getString("clausula_fundo_rural"));

				
				// dados de retirada

				contrato.setId_local_retirada(rs.getInt("id_local_retirada"));
				contrato.setTipo_entrega(rs.getInt("tipo_entrega"));

//informacoes extras

				contrato.setLocalizacao(rs.getString("localizacao"));
				contrato.setBruto_livre(rs.getString("bruto_livre"));
				contrato.setFertilizante(rs.getString("fertilizante"));
				contrato.setStatus_penhor(rs.getString("status_penhor"));
				contrato.setOptante_folha(rs.getInt("optante_folha"));
				contrato.setStatus_optante_folha(rs.getString("status_optante_folha"));
				contrato.setDescricao(rs.getString("descricao"));
				contrato.setObservacao(rs.getString("observacao"));
				contrato.setGrupo_particular(rs.getInt("grupo_particular"));

				ConexaoBanco.fechaConexao(conn, pstm, rs);

				return contrato;
			} else {

				return null;
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao listar contrato pai do subcontrato id: " + id_sub_contrato + " erro: ");
			System.out.println(
					"Erro ao listar contrato pai do subcontrato id: " + id_sub_contrato + " erro: " + "\ncausa: ");
			return null;
		}

	}

	public int atualizarContrato(CadastroContrato contrato, ArrayList<Integer> ids_pagamentos_a_excluir) {
		Connection conn = null;
		String atualizar = null;
		PreparedStatement pstm;
		String sql_update_contrato = "update contrato set  id_safra = ?, id_produto = ?, medida = ?, quantidade = ?,\r\n"
				+ "valor_produto = ?, valor_a_pagar = ?, frete = ?, clausula_frete = ?,  armazenagem = ?, clausula_armazenagem = ?, comissao = ?, clausula_comissao = ?, valor_comissao = ?, clausulas = ?, id_local_retirada = ?, tipo_entrega = ?,\r\n"
				+ "data_contrato = ?, data_entrega = ?, status_contrato = ?, caminho_diretorio = ?, caminho_arquivo = ?,\r\n"
				+ "nome_arquivo = ?, caminho_diretorio2 = ?, caminho_arquivo2 = ?, nome_arquivo2 = ?, codigo = ?, sub_contrato = ? where id = ?;";

		try {
			String texto_clausulas = "";
			int num_clausulas = 1;
			for (String clausula_local : contrato.getClausulas()) {
				texto_clausulas += (clausula_local + ";");
				num_clausulas++;
			}

			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(sql_update_contrato);

			pstm.setInt(1, contrato.getModelo_safra().getId_safra());
			pstm.setInt(2, contrato.getModelo_safra().getProduto().getId_produto());
			pstm.setString(3, contrato.getMedida());
			pstm.setString(4, Double.toString(contrato.getQuantidade()));
			pstm.setString(5, Double.toString(contrato.getValor_produto()));

			pstm.setString(6, contrato.getValor_a_pagar().toPlainString());
			pstm.setString(7, contrato.getFrete());
			pstm.setString(8, contrato.getClausula_frete());
			pstm.setString(9, contrato.getArmazenagem());
			pstm.setString(10, contrato.getClausula_armazenagem());

			pstm.setString(11, Integer.toString(contrato.getComissao()));
			pstm.setString(12, Integer.toString(contrato.getClausula_comissao()));
			pstm.setString(13, contrato.getValor_comissao().toPlainString());
			pstm.setString(14, texto_clausulas);

			pstm.setInt(15, contrato.getId_local_retirada());
			pstm.setInt(16, contrato.getTipo_entrega());

			pstm.setString(17, contrato.getData_contrato());
			pstm.setString(18, contrato.getData_entrega());
			pstm.setString(19, Integer.toString(contrato.getStatus_contrato()));
			pstm.setString(20, contrato.getCaminho_diretorio_contrato());
			pstm.setString(21, contrato.getCaminho_arquivo());
			pstm.setString(22, contrato.getNome_arquivo());

			pstm.setString(23, contrato.getCaminho_diretorio_contrato2());
			pstm.setString(24, contrato.getCaminho_arquivo2());
			pstm.setString(25, contrato.getNome_arquivo2());

			pstm.setString(26, contrato.getCodigo());
			pstm.setInt(27, contrato.getSub_contrato());

			pstm.setInt(28, contrato.getId());
			pstm.execute();
			// JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
			// System.out.println("Contrato Atualizado com sucesso");

			boolean pagamentos_apagados = false;
			boolean pagamentos_inseridos = false;

			// apaga os modelo de pagamentos
			if (ids_pagamentos_a_excluir != null) {

				System.out.println("Existem modelo_pagamentos a serem excluidos!");
				for (Integer id_pagamento : ids_pagamentos_a_excluir) {
					if (remover_modelo_pagamento(id_pagamento)) {
						pagamentos_apagados = true;

					} else {
						// nao foi possivel apagar esse modelo de pagamento
						pagamentos_apagados = false;
						break;
					}

				}

				// apaga os relacionamentos pagamentos
				for (Integer id_pagamento : ids_pagamentos_a_excluir) {

					if (remover_contrato_modelo_pagamento(contrato.getId(), id_pagamento)) {
						pagamentos_apagados = true;
						System.out.println("Relacionamentos contrato_modelo_pagamento excluidos!");

					} else {
						// nao foi possivel apagar esse modelo de pagamento
						pagamentos_apagados = false;
						System.out.println("Não foi possivel excluir o relacionamento contrato_modelo_pagamento!");

						break;
					}

				}
			} else {
				System.out.println("Não ha modelo_pagamentos a serem excluidos!");

				pagamentos_apagados = true;
			}

			// inseri os novos modelos de pagamentos
			boolean existe_novos_pagamento = false;
			for (CadastroContrato.CadastroPagamento pag : contrato.getPagamentos()) {
				if (pag.getId() == 0) {
					existe_novos_pagamento = false;
				}

			}

			if (contrato.getPagamentos() != null && contrato.getPagamentos().size() > 0) {
				System.out.println("Existem novos pagamentos a serem incluidos");
				if (inserirModelosPagamentos(contrato.getId(), contrato.getPagamentos())) {
					pagamentos_inseridos = true;
					System.out.println("Novos pagamentos inseridos");

				} else {
					pagamentos_inseridos = false;
					System.out.println("Erro ao inserir novos pagamentos inseridos");

				}
			} else {
				pagamentos_inseridos = true;
				System.out.println("Não ha novos pagamentos a serem incluidos");

			}
			GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
			CadastroContrato contrato_atual = gerenciar.getContrato(contrato.getId());

			boolean compradores_atualizados = false;
			// atualiza os compradores deste contrato
			// pega os compradores atuais
			CadastroCliente compradores_atuais[] = contrato_atual.getCompradores();

			// pega os vendedores novos
			CadastroCliente novos_compradores[] = contrato.getCompradores();

			// exclui os vendedores antigos
			boolean remover_comprador1 = remover_contrato_comprador(contrato.getId(), compradores_atuais[0].getId());

			if (compradores_atuais[1] != null) {
				boolean remover_comprador2 = remover_contrato_comprador(contrato.getId(),
						compradores_atuais[1].getId());
			}

			// inserir novos vendedores
			boolean inserir_novo_compradores = inserir_contrato_comprador(contrato, contrato.getId());
			if (inserir_novo_compradores)
				compradores_atualizados = true;

			/*******************************************************************/

			boolean vendedores_atualizados = false;

			// atualiza os vendedores deste contrato
			// pega os vendedores atuais
			// pega os vendedores atuais que estao cadastrados
			CadastroCliente vendedores_atuais[] = contrato_atual.getVendedores();

			// pega os vendedores novos
			CadastroCliente novos_vendedores[] = contrato.getVendedores();

			// exclui os vendedores antigos
			boolean remover_vend1 = remover_contrato_vendedor(contrato.getId(), vendedores_atuais[0].getId());

			if (vendedores_atuais[1] != null) {
				boolean remover_vend2 = remover_contrato_vendedor(contrato.getId(), vendedores_atuais[1].getId());
			}

			// inserir novos vendedores
			boolean inserir_novo_vendedores = inserir_contrato_vendedor(contrato, contrato.getId());
			if (inserir_novo_vendedores)
				vendedores_atualizados = true;

			boolean corretores_atualizados = false;

			// atualiza os vendedores deste contrato
			// pega os vendedores atuais
			// pega os vendedores atuais que estao cadastrados
			CadastroCliente corretores_atuais[] = contrato_atual.getCorretores();

			// pega os vendedores novos
			CadastroCliente novos_corrretores[] = contrato.getVendedores();

			// exclui os corretores antigos

			if (corretores_atuais[0] != null) {
				remover_contrato_corretor(contrato.getId(), corretores_atuais[0].getId());
			}

			// inserir novos vendedores
			if (novos_corrretores[0] != null) {
				boolean inserir_novo_corretor = inserir_contrato_corretor(contrato, contrato.getId());
				if (inserir_novo_corretor)
					corretores_atualizados = true;
				else
					corretores_atualizados = false;

			} else {
				corretores_atualizados = true;

			}

			ConexaoBanco.fechaConexao(conn);
			if (pagamentos_apagados && pagamentos_inseridos && vendedores_atualizados && corretores_atualizados
					&& compradores_atualizados) {
				System.out.println(
						"Pagamentos que tinham foram apagados, e novos adicionados, alem do que os vendedores foram atualizados!");

				return 5;
			} else {
				System.out.println("Houve um erro ao inserir ou apagar modelo_pagamentos!");

				return -2;
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao atualizar contrato no banco de" + "dados ");
			return -2;
		}

	}
	
	
	
	public boolean atualizarContratoComDistrato(CadastroContrato contrato) {
		Connection conn = null;
		String atualizar = null;
		PreparedStatement pstm;
		String sql_update_contrato = "update contrato set medida = ?, quantidade = ?,\r\n"
				+ "valor_produto = ?, valor_a_pagar = ? where id = ?";

		try {
			

			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(sql_update_contrato);

			pstm.setString(1, contrato.getMedida());
			pstm.setDouble(2, contrato.getQuantidade());
			pstm.setString(3, Double.toString(contrato.getValor_produto()));
			pstm.setString(4, contrato.getValor_a_pagar().toString());
			pstm.setInt(5, contrato.getId());

			pstm.execute();
			
			ConexaoBanco.fechaConexao(conn);
		
			return true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao atualizar contrato no banco de dados\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
			return false;
		}

	}

	public ArrayList<CadastroContrato> getContratos() {
		String selectContratos = "call consulta_contratos()";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroContrato> lsitaContratos = new ArrayList<CadastroContrato>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectContratos);
			// pstm.setString(1, chave);

			rs = pstm.executeQuery();
			while (rs.next()) {
				CadastroContrato contrato = new CadastroContrato();
				CadastroProduto produto = new CadastroProduto();
				CadastroSafra safra = new CadastroSafra();
				CadastroCliente compradores[] = null;
				CadastroCliente vendedores[] = null;
				CadastroCliente corretores[] = null;

				contrato.setId(rs.getInt("id"));
				contrato.setCodigo(rs.getString("codigo"));
				contrato.setSub_contrato(rs.getInt("sub_contrato"));
				contrato.setQuantidade(Double.parseDouble(rs.getString("quantidade")));
				contrato.setMedida(rs.getString("medida"));
				contrato.setProduto(rs.getString("nome_produto"));
				safra.setId_safra(rs.getInt("id_safra"));
				safra.setDescricao_safra(rs.getString("descricao_safra"));
				safra.setAno_plantio(Integer.parseInt(rs.getString("ano_plantio")));
				safra.setAno_colheita(Integer.parseInt(rs.getString("ano_colheita")));
				contrato.setValor_produto(Double.parseDouble(rs.getString("valor_produto")));
				contrato.setValor_a_pagar(new BigDecimal(rs.getString("valor_a_pagar")));
				contrato.setStatus_contrato(rs.getInt("status_contrato"));
				contrato.setData_contrato(rs.getString("data_contrato"));

				contrato.setNomes_compradores(rs.getString("compradores"));
				contrato.setNomes_vendedores(rs.getString("vendedores"));
				contrato.setNomes_corretores(rs.getString("corretores"));

				produto.setId_produto(rs.getInt("id_produto"));
				produto.setTransgenia(rs.getString("transgenia"));
				contrato.setId_local_retirada(rs.getInt("id_local_retirada"));

				// informacoes extras

				contrato.setLocalizacao(rs.getString("localizacao"));
				contrato.setBruto_livre(rs.getString("bruto_livre"));
				contrato.setFertilizante(rs.getString("fertilizante"));
				contrato.setStatus_penhor(rs.getString("status_penhor"));
				contrato.setOptante_folha(rs.getInt("optante_folha"));
				contrato.setStatus_optante_folha(rs.getString("status_optante_folha"));
				contrato.setDescricao(rs.getString("descricao"));
				contrato.setObservacao(rs.getString("observacao"));

				safra.setProduto(produto);
				contrato.setModelo_safra(safra);
				contrato.setModelo_produto(produto);
				
				contrato.setQuantidade_carregada(rs.getDouble("total_carregado"));
				contrato.setQuantidade_recebida(rs.getDouble("quantidade_recebida"));

				contrato.setTotal_pago(rs.getDouble("total_pago"));
				contrato.setTotal_comissao(rs.getDouble("quantidade_comissao_paga"));
				
				lsitaContratos.add(contrato);
				
				

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao listar contratos\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
		}
		return lsitaContratos;

	}

	public ArrayList<CadastroContrato> getInfoSubContratos(int id_contrato_pai) {
		String selectContratos = "call consulta_sub_contratos(?)";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroContrato> lsitaContratos = new ArrayList<CadastroContrato>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectContratos);
			pstm.setInt(1, id_contrato_pai);

			rs = pstm.executeQuery();
			while (rs.next()) {
				CadastroContrato contrato = new CadastroContrato();
				CadastroProduto produto = new CadastroProduto();
				CadastroSafra safra = new CadastroSafra();
				CadastroCliente compradores[] = null;
				CadastroCliente vendedores[] = null;
				CadastroCliente corretores[] = null;

				contrato.setId(rs.getInt("id"));
				contrato.setCodigo(rs.getString("codigo"));
				contrato.setSub_contrato(rs.getInt("sub_contrato"));
				contrato.setQuantidade(Double.parseDouble(rs.getString("quantidade")));
				contrato.setMedida(rs.getString("medida"));
				produto.setNome_produto(rs.getString("nome_produto"));
				produto.setTransgenia(rs.getString("transgenia"));
				contrato.setModelo_produto(produto);
				contrato.setProduto(produto.getNome_produto());

				safra.setDescricao_safra(rs.getString("descricao_safra"));
				safra.setAno_plantio(Integer.parseInt(rs.getString("ano_plantio")));
				safra.setAno_colheita(Integer.parseInt(rs.getString("ano_colheita")));
				contrato.setValor_produto(Double.parseDouble(rs.getString("valor_produto")));
				contrato.setValor_a_pagar(new BigDecimal(rs.getString("valor_a_pagar")));
				contrato.setStatus_contrato(rs.getInt("status_contrato"));
				contrato.setData_contrato(rs.getString("data_contrato"));

				contrato.setNomes_compradores(rs.getString("compradores"));
				contrato.setNomes_vendedores(rs.getString("vendedores"));
				contrato.setNomes_corretores(rs.getString("corretores"));

				safra.setProduto(produto);
				contrato.setModelo_safra(safra);
				lsitaContratos.add(contrato);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar contratos");
		}
		return lsitaContratos;

	}

	public ArrayList<CadastroContrato> getInfoSubContratos() {
		String selectContratos = "select id, sub_contrato, codigo, quantidade, medida, \n"
				+ "localizacao, bruto_livre, fertilizante, status_penhor,\n"
				+ "optante_folha, status_optante_folha, ct.descricao, observacao, pd.nome_produto, pd.transgenia,sf.descricao_safra, sf.ano_plantio,\n"
				+ "sf.ano_colheita, valor_produto, valor_a_pagar, \n" + "\n" + "GROUP_CONCAT(distinct\n" + "case\n"
				+ "when comprador.tipo_cliente = '0' then comprador.nome_empresarial \n"
				+ " when comprador.tipo_cliente = '1' then comprador.nome_fantasia\n" + "end\n"
				+ " separator ',') as compradores,\n" + "GROUP_CONCAT(distinct\n" + "case\n"
				+ "when vendedor.tipo_cliente = '0' then vendedor.nome_empresarial \n"
				+ " when vendedor.tipo_cliente = '1' then vendedor.nome_fantasia\n" + "end\n"
				+ " separator ',') as vendedores,\n" + " \n" + "  GROUP_CONCAT(distinct\n" + "case\n"
				+ "when corretor.tipo_cliente = '0' then corretor.nome_empresarial \n"
				+ " when corretor.tipo_cliente = '1' then corretor.nome_fantasia\n" + "end\n"
				+ " separator ',') as corretores,\n" + " ct.data_contrato, ct.status_contrato\n" + " \n" + " \n"
				+ "from contrato ct\n" + "LEFT JOIN  safra sf on sf.id_safra = ct.id_safra\n"
				+ "LEFT JOIN produto pd on pd.id_produto = sf.id_produto\n"
				+ "LEFT JOIN contrato_comprador on contrato_comprador.id_contrato = ct.id\n"
				+ "LEFT JOIN cliente comprador on comprador.id_cliente = contrato_comprador.id_cliente\n" + "\n"
				+ "LEFT JOIN contrato_vendedor on contrato_vendedor.id_contrato = ct.id\n"
				+ "LEFT JOIN cliente vendedor on vendedor.id_cliente = contrato_vendedor.id_cliente\n" + "\n"
				+ "LEFT JOIN contrato_corretor on contrato_corretor.id_contrato = ct.id\n"
				+ "LEFT JOIN cliente corretor on corretor.id_cliente = contrato_corretor.id_cliente\n" + "\n"
				+ "LEFT JOIN contrato_sub_contrato sub  on sub.id_sub_contrato = ct.id \n" + "group by ct.id  ;";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroContrato> lsitaContratos = new ArrayList<CadastroContrato>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectContratos);

			rs = pstm.executeQuery();
			while (rs.next()) {
				CadastroContrato contrato = new CadastroContrato();
				CadastroProduto produto = new CadastroProduto();
				CadastroSafra safra = new CadastroSafra();
				CadastroCliente compradores[] = null;
				CadastroCliente vendedores[] = null;
				CadastroCliente corretores[] = null;

				contrato.setId(rs.getInt("id"));
				contrato.setCodigo(rs.getString("codigo"));
				contrato.setSub_contrato(rs.getInt("sub_contrato"));
				contrato.setQuantidade(Double.parseDouble(rs.getString("quantidade")));
				contrato.setMedida(rs.getString("medida"));
				produto.setNome_produto(rs.getString("nome_produto"));
				produto.setTransgenia(rs.getString("transgenia"));
				contrato.setModelo_produto(produto);
				contrato.setProduto(produto.getNome_produto());

				safra.setDescricao_safra(rs.getString("descricao_safra"));
				safra.setAno_plantio(Integer.parseInt(rs.getString("ano_plantio")));
				safra.setAno_colheita(Integer.parseInt(rs.getString("ano_colheita")));
				contrato.setValor_produto(Double.parseDouble(rs.getString("valor_produto")));
				contrato.setValor_a_pagar(new BigDecimal(rs.getString("valor_a_pagar")));
				contrato.setStatus_contrato(rs.getInt("status_contrato"));
				contrato.setData_contrato(rs.getString("data_contrato"));

				contrato.setNomes_compradores(rs.getString("compradores"));
				contrato.setNomes_vendedores(rs.getString("vendedores"));
				contrato.setNomes_corretores(rs.getString("corretores"));

				safra.setProduto(produto);
				contrato.setModelo_safra(safra);
				lsitaContratos.add(contrato);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao listar contratos\nErro: " + e.getMessage() + "\nCausa:" + e.getCause());
		}
		return lsitaContratos;

	}

	public ArrayList<CadastroContrato> getContratosPorCliente(int flag_select, int id_busca_safra, int id_cliente) {

		String selectContratos = "";

		if (flag_select == 1) {
			// modo de busca de contratos que o cliente é o comprador

			selectContratos = "select * from contrato\n"
					+ "LEFT JOIN contrato_comprador on contrato_comprador.id_contrato = contrato.id\n"
					+ "where contrato_comprador.id_cliente = ?";

		} else if (flag_select == 2) {
			// modo de busca de contratos que o cliente é o vendedor
			selectContratos = "select * from contrato\n"
					+ "LEFT JOIN contrato_vendedor on contrato_vendedor.id_contrato = contrato.id\n"
					+ "where contrato_vendedor.id_cliente = ?";

		} else if (flag_select == 3) {
			// modo de busca de contratos que o cliente é o corretor
			selectContratos = "select * from contrato\n"
					+ "LEFT JOIN contrato_corretor on contrato_corretor.id_contrato = contrato.id\n"
					+ "where contrato_corretor.id_cliente = ? and (contrato.sub_contrato = 0 or contrato.sub_contrato = 3 or contrato.sub_contrato = 4 or contrato.sub_contrato = 5)";
		} else if (flag_select == 4) {
			selectContratos = "select * from contrato\n"
					+ "LEFT JOIN contrato_comprador on contrato_comprador.id_contrato = contrato.id\n"
					+ "where contrato_comprador.id_cliente = ? and (contrato.sub_contrato = 0 or contrato.sub_contrato = 3 or contrato.sub_contrato = 4 or contrato.sub_contrato = 5)";
		} else if (flag_select == 5) {
			// modo de busca de contratos que o cliente é o vendedor
			selectContratos = "select * from contrato\n"
					+ "LEFT JOIN contrato_vendedor on contrato_vendedor.id_contrato = contrato.id\n"
					+ "where contrato_vendedor.id_cliente = ? and (contrato.sub_contrato = 0 or contrato.sub_contrato = 3 or contrato.sub_contrato = 4 or contrato.sub_contrato = 5)";

		}

		if (id_busca_safra > 0)
			selectContratos = selectContratos + " and contrato.id_safra = ?";

		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroContrato> lsitaContratos = new ArrayList<CadastroContrato>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectContratos);

			pstm.setInt(1, id_cliente);
			if (id_busca_safra > 0)
				pstm.setInt(2, id_busca_safra);

			rs = pstm.executeQuery();
			while (rs.next()) {
				CadastroContrato contrato = new CadastroContrato();

				contrato.setId(rs.getInt("id"));
				int id = contrato.getId();

				contrato.setCodigo(rs.getString("codigo"));
				contrato.setSub_contrato(rs.getInt("sub_contrato"));
				contrato.setTexto_clausulas(rs.getString("clausulas"));

				int id_safra = rs.getInt("id_safra");

				GerenciarBancoSafras gerenciar = new GerenciarBancoSafras();
				CadastroSafra safra = gerenciar.getSafra(id_safra);

				if (safra != null) {

					contrato.setQuantidade(Double.parseDouble(rs.getString("quantidade")));
					contrato.setMedida(rs.getString("medida"));

					contrato.setValor_produto(Double.parseDouble(rs.getString("valor_produto")));
					contrato.setValor_a_pagar(new BigDecimal(rs.getString("valor_a_pagar")));

					contrato.setModelo_safra(safra);

					GerenciarBancoContratos gerenciar_corretores = new GerenciarBancoContratos();
					CadastroCliente corretores[] = gerenciar_corretores.getCorretores(id);
					contrato.setCorretores(corretores);

					GerenciarBancoContratos gerenciar_vendedores = new GerenciarBancoContratos();
					CadastroCliente vendedores[] = gerenciar_vendedores.getVendedores(id);
					contrato.setVendedores(vendedores);

					GerenciarBancoContratos gerenciar_compradores = new GerenciarBancoContratos();
					CadastroCliente compradores[] = gerenciar_compradores.getCompradores(id);
					contrato.setCompradores(compradores);

					GerenciarBancoContratos gerenciar_pagamentos = new GerenciarBancoContratos();
					ArrayList<CadastroContrato.CadastroPagamento> pagamentos_contrato = gerenciar_pagamentos
							.getPagamentos(id);
					contrato.setPagamentos(pagamentos_contrato);

					contrato.setStatus_contrato(rs.getInt("status_contrato"));
					contrato.setData_contrato(rs.getString("data_contrato"));
					contrato.setData_entrega(rs.getString("data_entrega"));
					contrato.setCaminho_diretorio_contrato(rs.getString("caminho_diretorio"));
					contrato.setCaminho_arquivo(rs.getString("caminho_arquivo"));
					contrato.setNome_arquivo(rs.getString("nome_arquivo"));

					contrato.setCaminho_diretorio_contrato2(rs.getString("caminho_diretorio2"));
					contrato.setCaminho_arquivo2(rs.getString("caminho_arquivo2"));
					contrato.setNome_arquivo2(rs.getString("nome_arquivo2"));

					// dados de comissao
					contrato.setComissao(rs.getInt("comissao"));
					if (contrato.getComissao() == 1) {
						contrato.setClausula_comissao(rs.getInt("clausula_comissao"));
						contrato.setValor_comissao(new BigDecimal(rs.getString("valor_comissao")));
					}

					// dados de frete
					contrato.setFrete(rs.getString("frete"));
					contrato.setClausula_frete(rs.getString("clausula_frete"));
					// dados de armazenagem
					contrato.setArmazenagem(rs.getString("armazenagem"));
					contrato.setClausula_armazenagem(rs.getString("clausula_armazenagem"));

					// dados de fundo rural
					contrato.setFundo_rural(rs.getString("fundo_rural"));
					contrato.setClausula_fundo_rural(rs.getString("clausula_fundo_rural"));

					
					// dados retirada
					contrato.setId_local_retirada(rs.getInt("id_local_retirada"));
					contrato.setTipo_entrega(rs.getInt("tipo_entrega"));

					// informacoes extras

					contrato.setLocalizacao(rs.getString("localizacao"));
					contrato.setBruto_livre(rs.getString("bruto_livre"));
					contrato.setFertilizante(rs.getString("fertilizante"));
					contrato.setStatus_penhor(rs.getString("status_penhor"));
					contrato.setOptante_folha(rs.getInt("optante_folha"));
					contrato.setStatus_optante_folha(rs.getString("status_optante_folha"));
					contrato.setDescricao(rs.getString("descricao"));
					contrato.setObservacao(rs.getString("observacao"));
					contrato.setGrupo_particular(rs.getInt("grupo_particular"));

					lsitaContratos.add(contrato);

				}
			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
			return lsitaContratos;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar contratos\nMensagem: " + "\nCausa: ");
			return null;
		}

	}

	public ArrayList<CadastroContrato> getContratosPorClienteParaRelatorio(int id_busca_safra,
			int id_cliente, int id_cliente2, int id_contra_parte, int participacao) {

		/*JOptionPane.showMessageDialog(null, 
		"A consulta sera: " +
				"call contratos_para_relatorio(" +id_cliente + ", "
				+ id_cliente2 + ", " + id_contra_parte + " , " + id_busca_safra
				
				
				
		);*/
		
		String selectContratos = "call contratos_para_relatorio(?,?,?,?, ?)";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroContrato> lsitaContratos = new ArrayList<CadastroContrato>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectContratos);
			
			
				//como comprador
			if(id_cliente > 0)
			  pstm.setInt(1, id_cliente);
			else
				pstm.setInt(1, 0);


			if (id_cliente2 > 0)
				pstm.setInt(2, id_cliente2);
			else
				pstm.setInt(2, 0);
			
			if (id_contra_parte > 0)
				pstm.setInt(3, id_contra_parte);
			else
				pstm.setInt(3, 0);

			
			if (id_busca_safra > 0) 
				pstm.setInt(4, id_busca_safra);
			else
				pstm.setInt(4, 0);
			
			pstm.setInt(5, participacao);

			
			
			/*}else if(flag_select == 5 || flag_select == 2) {
				//como vendedor
				if(id_contra_parte > 0)
					pstm.setInt(1, id_contra_parte);
					else
						pstm.setInt(1, 0);

					if (id_cliente > 0)
						pstm.setInt(2, id_cliente2);
					else
						pstm.setInt(2, 0);
					
					if (id_cliente > 0)
						pstm.setInt(3, id_cliente);
					else
						pstm.setInt(3, 0);

					
					if (id_busca_safra > 0) 
						pstm.setInt(4, id_busca_safra);
					else
						pstm.setInt(4, 0);
				
			}*/
			

			rs = pstm.executeQuery();
			while (rs.next()) {
				CadastroContrato contrato_recebimento = new CadastroContrato();

				contrato_recebimento.setId(rs.getInt("id"));
				contrato_recebimento.setSub_contrato(rs.getInt("sub_contrato"));
				contrato_recebimento.setCodigo(rs.getString("codigo"));
				contrato_recebimento.setQuantidade(rs.getDouble("quantidade"));
				contrato_recebimento.setMedida(rs.getString("medida"));
				contrato_recebimento.setValor_a_pagar(new BigDecimal(rs.getString("valor_a_pagar")));
				contrato_recebimento.setValor_produto(rs.getDouble("valor_produto"));
				contrato_recebimento.setNomes_compradores(rs.getString("compradores"));
				contrato_recebimento.setNomes_vendedores(rs.getString("vendedores"));
				contrato_recebimento.setStatus_contrato(rs.getInt("status_contrato"));
				contrato_recebimento.setGrupo_particular(rs.getInt("grupo_particular"));

				CadastroSafra safra = new CadastroSafra();
				safra.setId_safra(rs.getInt("id_safra"));
				safra.setAno_colheita(rs.getInt("ano_colheita"));
				safra.setAno_plantio(rs.getInt("ano_plantio"));

				CadastroProduto produto = new CadastroProduto();
				produto.setId_produto(rs.getInt("id_produto"));
				produto.setNome_produto(rs.getString("nome_produto"));
				produto.setTransgenia(rs.getString("transgenia"));

				safra.setProduto(produto);

				contrato_recebimento.setModelo_produto(produto);
				contrato_recebimento.setModelo_safra(safra);

				lsitaContratos.add(contrato_recebimento);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
			return lsitaContratos;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao listar contratos\nMensagem: " + e.getMessage() + "\nCausa: " + e.getCause());
			return null;
		}

	}

	public ArrayList<CadastroContrato> getContratosPorClienteParaRelatorioGrupo(int flag_select, int id_busca_safra,
			int id_cliente) {

		String selectContratos = "";

		if (flag_select == 1) {
			// modo de busca de contratos que o cliente é o comprador
			if (id_busca_safra > 0) {
				selectContratos = "select ct.*,\n" + "GROUP_CONCAT(distinct\n" + "case\n"
						+ "when comprador.tipo_cliente = '0' then comprador.nome_empresarial \n"
						+ " when comprador.tipo_cliente = '1' then comprador.nome_fantasia\n" + "end\n"
						+ " separator ',') as compradores,\n" + "GROUP_CONCAT(distinct\n" + "case\n"
						+ "when vendedor.tipo_cliente = '0' then vendedor.nome_empresarial \n"
						+ " when vendedor.tipo_cliente = '1' then vendedor.nome_fantasia\n" + "end\n"
						+ " separator ',') as vendedores,\n" + " sf.id_safra, sf.ano_colheita, sf.ano_plantio,\n"
						+ " pd.id_produto, pd.nome_produto, pd.transgenia \n" + " from contrato ct\n"
						+ "LEFT join safra sf on sf.id_safra = ct.id_safra\n"
						+ "LEFT join produto pd on pd.id_produto = sf.id_produto\n"
						+ "LEFT JOIN contrato_comprador on contrato_comprador.id_contrato = ct.id\n"
						+ "LEFT JOIN cliente comprador on comprador.id_cliente = contrato_comprador.id_cliente\n"
						+ "LEFT JOIN contrato_vendedor on contrato_vendedor.id_contrato = ct.id\n"
						+ "LEFT JOIN cliente vendedor on vendedor.id_cliente = contrato_vendedor.id_cliente\n"
						+ "where contrato_comprador.id_cliente = ? and sf.id_safra = ? and ct.grupo_particular = 0\n"
						+ "group by ct.id";
			} else {
				selectContratos = "select ct.*,\n" + "GROUP_CONCAT(distinct\n" + "case\n"
						+ "when comprador.tipo_cliente = '0' then comprador.nome_empresarial \n"
						+ " when comprador.tipo_cliente = '1' then comprador.nome_fantasia\n" + "end\n"
						+ " separator ',') as compradores,\n" + "GROUP_CONCAT(distinct\n" + "case\n"
						+ "when vendedor.tipo_cliente = '0' then vendedor.nome_empresarial \n"
						+ " when vendedor.tipo_cliente = '1' then vendedor.nome_fantasia\n" + "end\n"
						+ " separator ',') as vendedores,\n" + " sf.id_safra, sf.ano_colheita, sf.ano_plantio,\n"
						+ " pd.id_produto, pd.nome_produto, pd.transgenia \n" + " from contrato ct\n"
						+ "LEFT join safra sf on sf.id_safra = ct.id_safra\n"
						+ "LEFT join produto pd on pd.id_produto = sf.id_produto\n"
						+ "LEFT JOIN contrato_comprador on contrato_comprador.id_contrato = ct.id\n"
						+ "LEFT JOIN cliente comprador on comprador.id_cliente = contrato_comprador.id_cliente\n"
						+ "LEFT JOIN contrato_vendedor on contrato_vendedor.id_contrato = ct.id\n"
						+ "LEFT JOIN cliente vendedor on vendedor.id_cliente = contrato_vendedor.id_cliente\n"
						+ "where contrato_comprador.id_cliente = ?  and ct.grupo_particular = 0\n" + "group by ct.id";
			}

		} else if (flag_select == 2) {
			if (id_busca_safra > 0) {
				selectContratos = "select ct.*,\n" + "GROUP_CONCAT(distinct\n" + "case\n"
						+ "when comprador.tipo_cliente = '0' then comprador.nome_empresarial \n"
						+ " when comprador.tipo_cliente = '1' then comprador.nome_fantasia\n" + "end\n"
						+ " separator ',') as compradores,\n" + "GROUP_CONCAT(distinct\n" + "case\n"
						+ "when vendedor.tipo_cliente = '0' then vendedor.nome_empresarial \n"
						+ " when vendedor.tipo_cliente = '1' then vendedor.nome_fantasia\n" + "end\n"
						+ " separator ',') as vendedores,\n" + " sf.id_safra, sf.ano_colheita, sf.ano_plantio,\n"
						+ " pd.id_produto, pd.nome_produto, pd.transgenia \n" + " from contrato ct\n"
						+ "LEFT join safra sf on sf.id_safra = ct.id_safra\n"
						+ "LEFT join produto pd on pd.id_produto = sf.id_produto\n"
						+ "LEFT JOIN contrato_comprador on contrato_comprador.id_contrato = ct.id\n"
						+ "LEFT JOIN cliente comprador on comprador.id_cliente = contrato_comprador.id_cliente\n"
						+ "LEFT JOIN contrato_vendedor on contrato_vendedor.id_contrato = ct.id\n"
						+ "LEFT JOIN cliente vendedor on vendedor.id_cliente = contrato_vendedor.id_cliente\n"
						+ "where contrato_vendedor.id_cliente = ? and sf.id_safra = ? and ct.grupo_particular = 0\n"
						+ "group by ct.id";
			} else {
				selectContratos = "select ct.*,\n" + "GROUP_CONCAT(distinct\n" + "case\n"
						+ "when comprador.tipo_cliente = '0' then comprador.nome_empresarial \n"
						+ " when comprador.tipo_cliente = '1' then comprador.nome_fantasia\n" + "end\n"
						+ " separator ',') as compradores,\n" + "GROUP_CONCAT(distinct\n" + "case\n"
						+ "when vendedor.tipo_cliente = '0' then vendedor.nome_empresarial \n"
						+ " when vendedor.tipo_cliente = '1' then vendedor.nome_fantasia\n" + "end\n"
						+ " separator ',') as vendedores,\n" + " sf.id_safra, sf.ano_colheita, sf.ano_plantio,\n"
						+ " pd.id_produto, pd.nome_produto, pd.transgenia \n" + " from contrato ct\n"
						+ "LEFT join safra sf on sf.id_safra = ct.id_safra\n"
						+ "LEFT join produto pd on pd.id_produto = sf.id_produto\n"
						+ "LEFT JOIN contrato_comprador on contrato_comprador.id_contrato = ct.id\n"
						+ "LEFT JOIN cliente comprador on comprador.id_cliente = contrato_comprador.id_cliente\n"
						+ "LEFT JOIN contrato_vendedor on contrato_vendedor.id_contrato = ct.id\n"
						+ "LEFT JOIN cliente vendedor on vendedor.id_cliente = contrato_vendedor.id_cliente\n"
						+ "where contrato_vendedor.id_cliente = ? and ct.grupo_particular = 0\n" + "group by ct.id";
			}

		} else if (flag_select == 4) {
			if (id_busca_safra > 0) {
				selectContratos = "select ct.*,\n" + "GROUP_CONCAT(distinct\n" + "case\n"
						+ "when comprador.tipo_cliente = '0' then comprador.nome_empresarial \n"
						+ " when comprador.tipo_cliente = '1' then comprador.nome_fantasia\n" + "end\n"
						+ " separator ',') as compradores,\n" + "GROUP_CONCAT(distinct\n" + "case\n"
						+ "when vendedor.tipo_cliente = '0' then vendedor.nome_empresarial \n"
						+ " when vendedor.tipo_cliente = '1' then vendedor.nome_fantasia\n" + "end\n"
						+ " separator ',') as vendedores,\n" + " sf.id_safra, sf.ano_colheita, sf.ano_plantio,\n"
						+ " pd.id_produto, pd.nome_produto, pd.transgenia \n" + " from contrato ct\n"
						+ "LEFT join safra sf on sf.id_safra = ct.id_safra\n"
						+ "LEFT join produto pd on pd.id_produto = sf.id_produto\n"
						+ "LEFT JOIN contrato_comprador on contrato_comprador.id_contrato = ct.id\n"
						+ "LEFT JOIN cliente comprador on comprador.id_cliente = contrato_comprador.id_cliente\n"
						+ "LEFT JOIN contrato_vendedor on contrato_vendedor.id_contrato = ct.id\n"
						+ "LEFT JOIN cliente vendedor on vendedor.id_cliente = contrato_vendedor.id_cliente\n"
						+ "where contrato_comprador.id_cliente = ? and sf.id_safra = ? and (ct.sub_contrato = 0 or ct.sub_contrato = 3 or ct.sub_contrato = 4 or ct.sub_contrato = 5) and ct.grupo_particular = 0\n"
						+ "group by ct.id";
			} else {
				selectContratos = "select ct.*,\n" + "GROUP_CONCAT(distinct\n" + "case\n"
						+ "when comprador.tipo_cliente = '0' then comprador.nome_empresarial \n"
						+ " when comprador.tipo_cliente = '1' then comprador.nome_fantasia\n" + "end\n"
						+ " separator ',') as compradores,\n" + "GROUP_CONCAT(distinct\n" + "case\n"
						+ "when vendedor.tipo_cliente = '0' then vendedor.nome_empresarial \n"
						+ " when vendedor.tipo_cliente = '1' then vendedor.nome_fantasia\n" + "end\n"
						+ " separator ',') as vendedores,\n" + " sf.id_safra, sf.ano_colheita, sf.ano_plantio,\n"
						+ " pd.id_produto, pd.nome_produto, pd.transgenia \n" + " from contrato ct\n"
						+ "LEFT join safra sf on sf.id_safra = ct.id_safra\n"
						+ "LEFT join produto pd on pd.id_produto = sf.id_produto\n"
						+ "LEFT JOIN contrato_comprador on contrato_comprador.id_contrato = ct.id\n"
						+ "LEFT JOIN cliente comprador on comprador.id_cliente = contrato_comprador.id_cliente\n"
						+ "LEFT JOIN contrato_vendedor on contrato_vendedor.id_contrato = ct.id\n"
						+ "LEFT JOIN cliente vendedor on vendedor.id_cliente = contrato_vendedor.id_cliente\n"
						+ "where contrato_comprador.id_cliente = ? and (ct.sub_contrato = 0 or ct.sub_contrato = 3 or ct.sub_contrato = 4 or ct.sub_contrato = 5) and ct.grupo_particular = 0\n"
						+ "group by ct.id";
			}

		} else if (flag_select == 5) {
			if (id_busca_safra > 0) {
				selectContratos = "select ct.*,\n" + "GROUP_CONCAT(distinct\n" + "case\n"
						+ "when comprador.tipo_cliente = '0' then comprador.nome_empresarial \n"
						+ " when comprador.tipo_cliente = '1' then comprador.nome_fantasia\n" + "end\n"
						+ " separator ',') as compradores,\n" + "GROUP_CONCAT(distinct\n" + "case\n"
						+ "when vendedor.tipo_cliente = '0' then vendedor.nome_empresarial \n"
						+ " when vendedor.tipo_cliente = '1' then vendedor.nome_fantasia\n" + "end\n"
						+ " separator ',') as vendedores,\n" + " sf.id_safra, sf.ano_colheita, sf.ano_plantio,\n"
						+ " pd.id_produto, pd.nome_produto, pd.transgenia \n" + " from contrato ct\n"
						+ "LEFT join safra sf on sf.id_safra = ct.id_safra\n"
						+ "LEFT join produto pd on pd.id_produto = sf.id_produto\n"
						+ "LEFT JOIN contrato_comprador on contrato_comprador.id_contrato = ct.id\n"
						+ "LEFT JOIN cliente comprador on comprador.id_cliente = contrato_comprador.id_cliente\n"
						+ "LEFT JOIN contrato_vendedor on contrato_vendedor.id_contrato = ct.id\n"
						+ "LEFT JOIN cliente vendedor on vendedor.id_cliente = contrato_vendedor.id_cliente\n"
						+ "where contrato_vendedor.id_cliente = ? and sf.id_safra = ? and (ct.sub_contrato = 0 or ct.sub_contrato = 3 or ct.sub_contrato = 4 or ct.sub_contrato = 5) and ct.grupo_particular = 0\n"
						+ "group by ct.id";
			} else {
				selectContratos = "select ct.*,\n" + "GROUP_CONCAT(distinct\n" + "case\n"
						+ "when comprador.tipo_cliente = '0' then comprador.nome_empresarial \n"
						+ " when comprador.tipo_cliente = '1' then comprador.nome_fantasia\n" + "end\n"
						+ " separator ',') as compradores,\n" + "GROUP_CONCAT(distinct\n" + "case\n"
						+ "when vendedor.tipo_cliente = '0' then vendedor.nome_empresarial \n"
						+ " when vendedor.tipo_cliente = '1' then vendedor.nome_fantasia\n" + "end\n"
						+ " separator ',') as vendedores,\n" + " sf.id_safra, sf.ano_colheita, sf.ano_plantio,\n"
						+ " pd.id_produto, pd.nome_produto, pd.transgenia \n" + " from contrato ct\n"
						+ "LEFT join safra sf on sf.id_safra = ct.id_safra\n"
						+ "LEFT join produto pd on pd.id_produto = sf.id_produto\n"
						+ "LEFT JOIN contrato_comprador on contrato_comprador.id_contrato = ct.id\n"
						+ "LEFT JOIN cliente comprador on comprador.id_cliente = contrato_comprador.id_cliente\n"
						+ "LEFT JOIN contrato_vendedor on contrato_vendedor.id_contrato = ct.id\n"
						+ "LEFT JOIN cliente vendedor on vendedor.id_cliente = contrato_vendedor.id_cliente\n"
						+ "where contrato_vendedor.id_cliente = ? and (ct.sub_contrato = 0 or ct.sub_contrato = 3 or ct.sub_contrato = 4 or ct.sub_contrato = 5) and ct.grupo_particular = 0\n"
						+ "group by ct.id";
			}

		}
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroContrato> lsitaContratos = new ArrayList<CadastroContrato>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectContratos);

			pstm.setInt(1, id_cliente);
			if (id_busca_safra > 0)
				pstm.setInt(2, id_busca_safra);

			rs = pstm.executeQuery();
			while (rs.next()) {
				CadastroContrato contrato_recebimento = new CadastroContrato();

				contrato_recebimento.setId(rs.getInt("id"));
				contrato_recebimento.setSub_contrato(rs.getInt("sub_contrato"));
				contrato_recebimento.setCodigo(rs.getString("codigo"));
				contrato_recebimento.setQuantidade(rs.getDouble("quantidade"));
				contrato_recebimento.setMedida(rs.getString("medida"));
				contrato_recebimento.setValor_a_pagar(new BigDecimal(rs.getString("valor_a_pagar")));
				contrato_recebimento.setValor_produto(rs.getDouble("valor_produto"));
				contrato_recebimento.setNomes_compradores(rs.getString("compradores"));
				contrato_recebimento.setNomes_vendedores(rs.getString("vendedores"));
				contrato_recebimento.setGrupo_particular(rs.getInt("grupo_particular"));
				contrato_recebimento.setStatus_contrato(rs.getInt("status_contrato"));

				CadastroSafra safra = new CadastroSafra();
				safra.setId_safra(rs.getInt("id_safra"));
				safra.setAno_colheita(rs.getInt("ano_colheita"));
				safra.setAno_plantio(rs.getInt("ano_plantio"));

				CadastroProduto produto = new CadastroProduto();
				produto.setId_produto(rs.getInt("id_produto"));
				produto.setNome_produto(rs.getString("nome_produto"));
				produto.setTransgenia(rs.getString("transgenia"));

				safra.setProduto(produto);

				contrato_recebimento.setModelo_produto(produto);
				contrato_recebimento.setModelo_safra(safra);

				lsitaContratos.add(contrato_recebimento);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
			return lsitaContratos;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao listar contratos\nMensagem: " + e.getMessage() + "\nCausa: " + e.getCause());
			return null;
		}

	}

	public CadastroCliente[] getCorretores(int id_contrato) {

		String selectCorretores = "select c.id_cliente from contrato_corretor cc LEFT JOIN cliente c on c.id_cliente = cc.id_cliente  where cc.id_contrato = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		CadastroCliente lista_corretores[] = new CadastroCliente[5];

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectCorretores);
			pstm.setInt(1, id_contrato);

			rs = pstm.executeQuery();
			int i = 0;
			GerenciarBancoClientes gerenciar_clientes = new GerenciarBancoClientes();

			while (rs.next()) {
				int id_cliente = rs.getInt("id_cliente");
				System.out.println("id do cliente retornado: " + id_cliente);
				CadastroCliente corretor;
				try {
					corretor = gerenciar_clientes.getCliente(id_cliente);
					System.out.println("Nome do cliente com id " + id_cliente + ": " + corretor.getNome_empresarial()
							+ " outro nome: " + corretor.getNome_fantaia());
					if (corretor != null) {
						lista_corretores[i] = corretor;
						System.out.println("Corretor encontrado: " + corretor.getNome_empresarial() + "outro nome:"
								+ corretor.getNome_fantaia());
						i++;
					} else {
						System.out.println("O corretor é nulo!");
					}

				} catch (Exception y) {
					System.out.println("Erro ao procurar por cliente corretor, erro: " + y.getMessage());
				}
			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
			return lista_corretores;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao listar os corretores do contrato: " + id_contrato + " erro: " + "causa: ");
			return null;
		}

	}

	public CadastroCliente[] getCompradores(int id_contrato) {

		String selectCompradores = "select c.id_cliente from contrato_comprador cc LEFT JOIN cliente c on c.id_cliente = cc.id_cliente  where cc.id_contrato = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		CadastroCliente lista_compradores[] = new CadastroCliente[3];

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectCompradores);
			pstm.setInt(1, id_contrato);

			rs = pstm.executeQuery();
			int i = 0;
			GerenciarBancoClientes gerenciar_clientes = new GerenciarBancoClientes();

			while (rs.next()) {
				int id_cliente = rs.getInt("id_cliente");
				System.out.println("id do cliente retornado: " + id_cliente);
				CadastroCliente comprador;
				try {
					comprador = gerenciar_clientes.getCliente(id_cliente);
					System.out.println("Nome do cliente com id " + id_cliente + ": " + comprador.getNome_empresarial()
							+ " outro nome: " + comprador.getNome_fantaia());
					if (comprador != null) {
						lista_compradores[i] = comprador;
						System.out.println("Comprador encontrado: " + comprador.getNome_empresarial() + "outro nome:"
								+ comprador.getNome_fantaia());
						i++;
					} else
						System.out.println("O comprador é nulo!");

				} catch (Exception y) {
					System.out.println("Erro ao procurar por cliente comprador, erro: " + y.getMessage());
				}
			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
			return lista_compradores;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao listar os compradores do contrato: " + id_contrato + " erro: " + "causa: ");
			return null;
		}

	}

	public CadastroCliente[] getVendedores(int id_contrato) {

		String selectVendedores = "select c.id_cliente from contrato_vendedor cc LEFT JOIN cliente c on c.id_cliente = cc.id_cliente  where cc.id_contrato = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		CadastroCliente lista_vendedores[] = new CadastroCliente[3];

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectVendedores);
			pstm.setInt(1, id_contrato);

			rs = pstm.executeQuery();
			int i = 0;
			GerenciarBancoClientes gerenciar_clientes = new GerenciarBancoClientes();

			while (rs.next()) {
				int id_cliente = rs.getInt("id_cliente");
				System.out.println("id do cliente retornado: " + id_cliente);
				CadastroCliente vendedor;
				try {
					vendedor = gerenciar_clientes.getCliente(id_cliente);
					System.out.println("Nome do cliente com id " + id_cliente + ": " + vendedor.getNome_empresarial()
							+ " outro nome: " + vendedor.getNome_fantaia());
					if (vendedor != null) {
						lista_vendedores[i] = vendedor;
						System.out.println("Vendedor encontrado: " + vendedor.getNome_empresarial() + "outro nome:"
								+ vendedor.getNome_fantaia());
						i++;
					} else {
						System.out.println("O vendedor é nulo!");
					}

				} catch (Exception y) {
					System.out.println("Erro ao procurar por cliente vendedores, erro: " + y.getMessage());
				}
			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
			return lista_vendedores;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao listar os vendedores do contrato: " + id_contrato + " erro: " + "causa: ");
			return null;
		}

	}

	public ArrayList<CadastroContrato.CadastroPagamento> getPagamentos(int id_contrato) {
		String selectPagamentos = "select * from contrato_modelo_pagamento  LEFT JOIN modelo_pagamento  on modelo_pagamento.id = contrato_modelo_pagamento.id_pagamento  where contrato_modelo_pagamento.id_contrato = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroContrato.CadastroPagamento> lista_pagamentos = null;

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectPagamentos);
			pstm.setInt(1, id_contrato);

			rs = pstm.executeQuery();
			int i = 0;

			while (rs.next()) {

				if (rs != null) {
					System.out.print("rs pagamento nao e nulo!");

					CadastroContrato.CadastroPagamento pagamento = new CadastroContrato.CadastroPagamento();

					pagamento.setId(rs.getInt("id"));
					pagamento.setValor_string(rs.getString("valor"));
					pagamento.setValor(new BigDecimal(rs.getString("valor")));
					pagamento.setData_pagamento(rs.getString("data_pagamento"));
					pagamento.setPagamento_adiantado(rs.getInt("antecipado"));
					ContaBancaria conta = null;

					int id_cb = rs.getInt("id_conta_bancaria");
					if (id_cb == 0) {
						conta = new ContaBancaria();
						// nao foi informado conta bancaria ppara esse pagamento

						/*
						 * id = "00"; cpf = "Há Informar"; banco = "Há Informar"; nome = "Há Informar";
						 * codigo = "Há Informar"; agencia = "Há Informar"; conta = "Há Informar";
						 */
						conta.setId_conta(0);
						conta.setCpf_titular("Há Informar");
						conta.setBanco("Há Informar");
						conta.setCodigo("Há Informar");
						conta.setAgencia("Há Informar");
						conta.setConta("Há Informar");
						conta.setNome("Há Informar");

					} else {
						GerenciarBancoClientes gerenciar = new GerenciarBancoClientes();
						conta = gerenciar.getConta(id_cb);
					}

					if (conta != null)
						pagamento.setConta(conta);

					if (lista_pagamentos == null)
						lista_pagamentos = new ArrayList<>();

					lista_pagamentos.add(pagamento);
				}
			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
			return lista_pagamentos;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao listar os pagamentos do contrato: " + id_contrato + " erro: " + "causa: ");
			return null;
		}

	}

	private boolean inserir_contrato_sub_contrato(int id_contrato_pai, int id_sub_contrato) {
		Connection conn = null;
		try {
			conn = ConexaoBanco.getConexao();
			String sql = "insert into contrato_sub_contrato \r\n" + "(id_contrato_pai, id_sub_contrato) values ('"
					+ id_contrato_pai + "','" + id_sub_contrato + "')";

			PreparedStatement grava = (PreparedStatement) conn.prepareStatement(sql);
			grava.execute();
			ConexaoBanco.fechaConexao(conn, grava);
			// JOptionPane.showMessageDialog(null, "Relação contrato_sub_contrato cadastrado
			// com sucesso");
			registro_geral_relacao_contrato_sub_contrato.setIdContrato(id_contrato_pai);
			registro_geral_relacao_contrato_sub_contrato.adicionar_id(id_sub_contrato);
			return true;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao inserir a relação contrato_sub_contrato no banco de" + " dados");
			return false;
		}

	}

	private boolean remover_contrato_sub_contrato(int id_contrato_pai, int id_sub_contrato) {
		String sql_delete_relacao = "DELETE FROM contrato_sub_contrato WHERE id_contrato_pai = ? and id_sub_contrato = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_delete_relacao);

			pstm.setInt(1, id_contrato_pai);
			pstm.setInt(2, id_sub_contrato);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			// JOptionPane.showMessageDialog(null, "Relação contrato_sub_contrato excluido,
			// banco normalizado ");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao exlcuir relação contrato_sub_contrato do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
							+ "dados " + f.getMessage());
			return false;
		}

	}

	private boolean remover_tabelas_contrato_sub_contrato() {
		boolean retorno = false;

		for (int codigo_sub_contrato : registro_geral_relacao_contrato_sub_contrato.getIds()) {
			if (remover_contrato_sub_contrato(registro_geral_relacao_contrato_sub_contrato.getIdContrato(),
					codigo_sub_contrato)) {
				retorno = true;
			} else {
				retorno = false;
				break;
			}
		}

		return retorno;
	}

	public ArrayList<CadastroContrato> getSubContratos(int id_contrato_pai) {
		String selectContrato = "select * from contrato_sub_contrato sub_contrato LEFT JOIN contrato filho  on filho.id = sub_contrato.id_sub_contrato where (filho.sub_contrato = 1 or filho.sub_contrato = 6 or filho.sub_contrato = 7) and id_contrato_pai = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroContrato> lsitaContratos = new ArrayList<CadastroContrato>();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectContrato);
			pstm.setInt(1, id_contrato_pai);
			GerenciarBancoSafras gerenciar = new GerenciarBancoSafras();

			rs = pstm.executeQuery();
			while (rs.next()) {
				CadastroContrato contrato = new CadastroContrato();

				contrato.setId(rs.getInt("id"));
				contrato.setCodigo(rs.getString("codigo"));
				contrato.setSub_contrato(rs.getInt("sub_contrato"));
				int id_safra = rs.getInt("id_safra");

				CadastroSafra safra = gerenciar.getSafra(id_safra);

				int id = contrato.getId();

				if (safra != null) {

					contrato.setQuantidade(Double.parseDouble(rs.getString("quantidade")));
					contrato.setMedida(rs.getString("medida"));

					contrato.setValor_produto(Double.parseDouble(rs.getString("valor_produto")));
					contrato.setValor_a_pagar(new BigDecimal(rs.getString("valor_a_pagar")));

					contrato.setModelo_safra(safra);

					GerenciarBancoContratos gerenciar_corretores = new GerenciarBancoContratos();
					CadastroCliente corretores[] = gerenciar_corretores.getCorretores(id);
					contrato.setCorretores(corretores);

					GerenciarBancoContratos gerenciar_vendedores = new GerenciarBancoContratos();
					CadastroCliente vendedores[] = gerenciar_vendedores.getVendedores(id);
					contrato.setVendedores(vendedores);

					GerenciarBancoContratos gerenciar_compradores = new GerenciarBancoContratos();
					CadastroCliente compradores[] = gerenciar_compradores.getCompradores(id);
					contrato.setCompradores(compradores);

					GerenciarBancoContratos gerenciar_pagamentos = new GerenciarBancoContratos();
					ArrayList<CadastroContrato.CadastroPagamento> pagamentos_contrato = gerenciar_pagamentos
							.getPagamentos(id);
					contrato.setPagamentos(pagamentos_contrato);

					contrato.setStatus_contrato(rs.getInt("status_contrato"));
					contrato.setData_contrato(rs.getString("data_contrato"));
					contrato.setData_entrega(rs.getString("data_entrega"));

					contrato.setCaminho_arquivo(rs.getString("caminho_arquivo"));
					contrato.setNome_arquivo(rs.getString("nome_arquivo"));
					contrato.setCaminho_diretorio_contrato(rs.getString("caminho_diretorio"));

					contrato.setCaminho_diretorio_contrato2(rs.getString("caminho_diretorio2"));
					contrato.setCaminho_arquivo2(rs.getString("caminho_arquivo2"));
					contrato.setNome_arquivo2(rs.getString("nome_arquivo2"));

					// dados de comissao
					contrato.setComissao(rs.getInt("comissao"));
					contrato.setClausula_comissao(rs.getInt("clausula_comissao"));
					contrato.setValor_comissao(new BigDecimal(rs.getString("valor_comissao")));

					// dados de fundo rural
					contrato.setFundo_rural(rs.getString("fundo_rural"));
					contrato.setClausula_fundo_rural(rs.getString("clausula_fundo_rural"));

					
					// dados de retirada

					contrato.setId_local_retirada(rs.getInt("id_local_retirada"));
					contrato.setTipo_entrega(rs.getInt("tipo_entrega"));

					// informacoes extras

					contrato.setLocalizacao(rs.getString("localizacao"));
					contrato.setBruto_livre(rs.getString("bruto_livre"));
					contrato.setFertilizante(rs.getString("fertilizante"));
					contrato.setStatus_penhor(rs.getString("status_penhor"));
					contrato.setOptante_folha(rs.getInt("optante_folha"));
					contrato.setStatus_optante_folha(rs.getString("status_optante_folha"));
					contrato.setDescricao(rs.getString("descricao"));
					contrato.setObservacao(rs.getString("observacao"));

					lsitaContratos.add(contrato);

				}

			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao listar contrato filhos do contrato id: " + id_contrato_pai + " erro: ");
			return null;
		}
		return lsitaContratos;

	}
/*
	public ArrayList<CadastroContrato> getSubContratosParaRelatorio(int id_contrato_pai) {
		// String selectContrato = "select * from contrato_sub_contrato sub_contrato
		// LEFT JOIN contrato filho on filho.id = sub_contrato.id_sub_contrato where
		// (filho.sub_contrato = 1 or filho.sub_contrato = 6 or filho.sub_contrato = 7)
		// and id_contrato_pai = ?";
		String selectContrato = "select sub_contrato.*,\n" + "filho.*,\n" + "GROUP_CONCAT(distinct\n" + "case\n"
				+ "when comprador.tipo_cliente = '0' then comprador.nome_empresarial \n"
				+ " when comprador.tipo_cliente = '1' then comprador.nome_fantasia\n" + "end\n"
				+ " separator ',') as compradores,\n" + "GROUP_CONCAT(distinct\n" + "case\n"
				+ "when vendedor.tipo_cliente = '0' then vendedor.nome_empresarial \n"
				+ " when vendedor.tipo_cliente = '1' then vendedor.nome_fantasia\n" + "end\n"
				+ " separator ',') as vendedores,\n" + " sf.id_safra, sf.ano_colheita, sf.ano_plantio,\n"
				+ " pd.id_produto, pd.nome_produto, pd.transgenia \n" + "from contrato_sub_contrato sub_contrato \n"
				+ "LEFT JOIN contrato filho  on filho.id = sub_contrato.id_sub_contrato \n"
				+ "LEFT join safra sf on sf.id_safra = filho.id_safra\n"
				+ "LEFT join produto pd on pd.id_produto = sf.id_produto\n"
				+ "LEFT JOIN contrato_comprador on contrato_comprador.id_contrato = filho.id\n"
				+ "LEFT JOIN cliente comprador on comprador.id_cliente = contrato_comprador.id_cliente\n"
				+ "LEFT JOIN contrato_vendedor on contrato_vendedor.id_contrato = filho.id\n"
				+ "LEFT JOIN cliente vendedor on vendedor.id_cliente = contrato_vendedor.id_cliente\n"
				+ "where (filho.sub_contrato = 1 or filho.sub_contrato = 6 or filho.sub_contrato = 7) and id_contrato_pai = ?";
		
		String selectContrato = "call consulta_sub_contratos(?)";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroContrato> lsitaContratos = new ArrayList<CadastroContrato>();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectContrato);
			pstm.setInt(1, id_contrato_pai);
			GerenciarBancoSafras gerenciar = new GerenciarBancoSafras();

			rs = pstm.executeQuery();
			while (rs.next()) {

				CadastroContrato contrato_recebimento = new CadastroContrato();

				if (rs.getInt("id") > 0) {
					contrato_recebimento.setId(rs.getInt("id"));

					contrato_recebimento.setSub_contrato(rs.getInt("sub_contrato"));
					contrato_recebimento.setCodigo(rs.getString("codigo"));
					contrato_recebimento.setQuantidade(rs.getDouble("quantidade"));
					contrato_recebimento.setMedida(rs.getString("medida"));
					contrato_recebimento.setValor_a_pagar(new BigDecimal(rs.getString("valor_a_pagar")));

					contrato_recebimento.setValor_produto(rs.getDouble("valor_produto"));

					contrato_recebimento.setNomes_compradores(rs.getString("compradores"));
					contrato_recebimento.setNomes_vendedores(rs.getString("vendedores"));

					CadastroSafra safra = new CadastroSafra();
					safra.setId_safra(rs.getInt("id_safra"));
					safra.setAno_colheita(rs.getInt("ano_colheita"));
					safra.setAno_plantio(rs.getInt("ano_plantio"));

					CadastroProduto produto = new CadastroProduto();
					produto.setId_produto(rs.getInt("id_produto"));
					produto.setNome_produto(rs.getString("nome_produto"));
					produto.setTransgenia(rs.getString("transgenia"));

					safra.setProduto(produto);

					contrato_recebimento.setModelo_produto(produto);
					contrato_recebimento.setModelo_safra(safra);

					lsitaContratos.add(contrato_recebimento);
				}
			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar contrato filhos do contrato id: " + id_contrato_pai
					+ " erro: " + e.getMessage() + "\nCausa: " + e.getCause());
			return null;
		}
		return lsitaContratos;

	}*/
	
	public ArrayList<CadastroContrato> getSubContratosParaRelatorio(int id_contrato_pai) {
		// String selectContrato = "select * from contrato_sub_contrato sub_contrato
		// LEFT JOIN contrato filho on filho.id = sub_contrato.id_sub_contrato where
		// (filho.sub_contrato = 1 or filho.sub_contrato = 6 or filho.sub_contrato = 7)
		// and id_contrato_pai = ?";
	/*	String selectContrato = "select sub_contrato.*,\n" + "filho.*,\n" + "GROUP_CONCAT(distinct\n" + "case\n"
				+ "when comprador.tipo_cliente = '0' then comprador.nome_empresarial \n"
				+ " when comprador.tipo_cliente = '1' then comprador.nome_fantasia\n" + "end\n"
				+ " separator ',') as compradores,\n" + "GROUP_CONCAT(distinct\n" + "case\n"
				+ "when vendedor.tipo_cliente = '0' then vendedor.nome_empresarial \n"
				+ " when vendedor.tipo_cliente = '1' then vendedor.nome_fantasia\n" + "end\n"
				+ " separator ',') as vendedores,\n" + " sf.id_safra, sf.ano_colheita, sf.ano_plantio,\n"
				+ " pd.id_produto, pd.nome_produto, pd.transgenia \n" + "from contrato_sub_contrato sub_contrato \n"
				+ "LEFT JOIN contrato filho  on filho.id = sub_contrato.id_sub_contrato \n"
				+ "LEFT join safra sf on sf.id_safra = filho.id_safra\n"
				+ "LEFT join produto pd on pd.id_produto = sf.id_produto\n"
				+ "LEFT JOIN contrato_comprador on contrato_comprador.id_contrato = filho.id\n"
				+ "LEFT JOIN cliente comprador on comprador.id_cliente = contrato_comprador.id_cliente\n"
				+ "LEFT JOIN contrato_vendedor on contrato_vendedor.id_contrato = filho.id\n"
				+ "LEFT JOIN cliente vendedor on vendedor.id_cliente = contrato_vendedor.id_cliente\n"
				+ "where (filho.sub_contrato = 1 or filho.sub_contrato = 6 or filho.sub_contrato = 7) and id_contrato_pai = ?";
		*/
		String selectContrato = "call consulta_sub_contratos(?)";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroContrato> lsitaContratos = new ArrayList<CadastroContrato>();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectContrato);
			pstm.setInt(1, id_contrato_pai);
			GerenciarBancoSafras gerenciar = new GerenciarBancoSafras();

			rs = pstm.executeQuery();
			while (rs.next()) {

				CadastroContrato contrato_recebimento = new CadastroContrato();

				if (rs.getInt("id") > 0) {
					contrato_recebimento.setId(rs.getInt("id"));

					contrato_recebimento.setSub_contrato(rs.getInt("sub_contrato"));
					contrato_recebimento.setCodigo(rs.getString("codigo"));
					contrato_recebimento.setQuantidade(rs.getDouble("quantidade"));
					contrato_recebimento.setMedida(rs.getString("medida"));
					contrato_recebimento.setValor_a_pagar(new BigDecimal(rs.getString("valor_a_pagar")));

					contrato_recebimento.setValor_produto(rs.getDouble("valor_produto"));

					contrato_recebimento.setNomes_compradores(rs.getString("compradores"));
					contrato_recebimento.setNomes_vendedores(rs.getString("vendedores"));

					CadastroSafra safra = new CadastroSafra();
					safra.setAno_colheita(rs.getInt("ano_colheita"));
					safra.setAno_plantio(rs.getInt("ano_plantio"));

					CadastroProduto produto = new CadastroProduto();
					produto.setNome_produto(rs.getString("nome_produto"));
					produto.setTransgenia(rs.getString("transgenia"));

					safra.setProduto(produto);

					contrato_recebimento.setModelo_produto(produto);
					contrato_recebimento.setModelo_safra(safra);

					lsitaContratos.add(contrato_recebimento);
				}
			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar contrato filhos do contrato id: " + id_contrato_pai
					+ " erro: " + e.getMessage() + "\nCausa: " + e.getCause());
			return null;
		}
		return lsitaContratos;

	}


	public boolean inserirTarefas(int id_contrato, ArrayList<CadastroContrato.CadastroTarefa> lista_tarefas) {
		boolean retorno = false;

		if (lista_tarefas != null && lista_tarefas.size() > 0) {
			System.out.println("tarefas não nulas recebidas para salvar no bando de dados");
			for (CadastroContrato.CadastroTarefa tarefa : lista_tarefas) {
				if (tarefa.getId_tarefa() == 0) {
					// tarefa tem id 00, entao ela ainda nao foir cadastrada, entao, cadastrar
					int retorno_id_cadastrado = inserir_tarefa_retorno(tarefa);
					if (retorno_id_cadastrado != -1) {
						// tarefa foi cadastrada e retornou o id cadastrado
						// criar relacao contrato_tarefa
						if (inserir_contrato_tarefa(id_contrato, retorno_id_cadastrado)) {
							retorno = true;

						} else {
							// para o loop, houve um erro
							retorno = false;
							break;
						}

					} else {
						// para o loop, houve um erro
						retorno = false;
						break;

					}

				}

			}

		} else {
			// nao havia tarefas para serem cadastradas
			System.out.println("não ha tarefas para serem cadastradas, o retorno sera false");
		}

		return retorno;

	}

	private int inserir_tarefa_retorno(CadastroContrato.CadastroTarefa tarefa) {
		int result = -1;
		if (tarefa != null) {
			Connection conn = null;
			try {

				conn = ConexaoBanco.getConexao();

				String query = sql_tarefa(tarefa);
				Statement stmt = (Statement) conn.createStatement();
				int numero = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					result = rs.getInt(1);
					System.out.println("Id tarefa inserida: " + result);
				}
				rs.close();
				stmt.close();

				return result;

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao inserir a tarefa no banco de " + "dados ");
				GerenciadorLog.registrarLogDiario("falha", "falha ao adicionar tarefa: " + " causa: ");
				return -1;
			}
		} else {
			System.out.println("A tarefa enviado por parametro esta vazio");
			return -1;
		}
	}

	
	
	public String sql_tarefa(CadastroContrato.CadastroTarefa tarefa) {

		String query = "insert into tarefa (status_tarefa, nome_tarefa, descricao_tarefa , mensagem, hora, data_tarefa, id_usuario_criador, id_usuario_executor, hora_agendada, data_agendada, prioridade) values ('"
				+ tarefa.getStatus_tarefa() + "','" + tarefa.getNome_tarefa() + "','" + tarefa.getDescricao_tarefa()
				+ "','" + tarefa.getMensagem() + "','" + tarefa.getHora() + "','" + tarefa.getData() + "','"
				+ tarefa.getCriador().getId()

				+ "','" + tarefa.getExecutor().getId() + "','" + tarefa.getHora_agendada() + "','"
				+ tarefa.getData_agendada() + "','" + tarefa.getPrioridade() + "')";
		return query;

	}

	private boolean inserir_contrato_tarefa(int id_contrato, int id_tarefa) {
		Connection conn = null;
		try {
			conn = ConexaoBanco.getConexao();
			String sql = "insert into contrato_tarefas\r\n" + "(id_contrato, id_tarefa) values ('" + id_contrato + "','"
					+ id_tarefa + "')";

			PreparedStatement grava = (PreparedStatement) conn.prepareStatement(sql);
			grava.execute();
			ConexaoBanco.fechaConexao(conn, grava);
			// JOptionPane.showMessageDialog(null, "Relação contrato_tarefas cadastrado com
			// sucesso");
			registro_geral_relacao_contrato_tarefa.setIdContrato(id_contrato);
			registro_geral_relacao_contrato_tarefa.adicionar_id(id_tarefa);

			return true;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao inserir a relação contrato_tarefa no banco de dados ");
			return false;
		}

	}

	public int getContratoPorTarefa(int id_tarefa) {
		System.out.println("Lista tarefas foi chamado!");
		String selectTarefas = "select id_contrato from contrato_tarefas \n"
				+ "LEFT JOIN tarefa  on tarefa.id_tarefa = contrato_tarefas.id_tarefa\n" + "where tarefa.id_tarefa = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		int id_contrato = -1;
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectTarefas);
			pstm.setInt(1, id_tarefa);

			rs = pstm.executeQuery();
			rs.next();
			id_contrato = rs.getInt("id_contrato");

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			return id_contrato;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao listar contrato por tarefa: " + id_tarefa + " erro: " + "causa: ");
			return -1;
		}

	}

	public ArrayList<CadastroContrato.CadastroTarefa> getTarefas(int id_contrato) {

		System.out.println("Lista tarefas foi chamado!");
		String selectTarefas = "select * from contrato_tarefas \r\n"
				+ "LEFT JOIN tarefa  on tarefa.id_tarefa = contrato_tarefas.id_tarefa \r\n"
				+ "where contrato_tarefas.id_contrato = ?;\r\n";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroContrato.CadastroTarefa> lista_tarefas = null;

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectTarefas);
			pstm.setInt(1, id_contrato);

			rs = pstm.executeQuery();
			int i = 0;

			while (rs.next()) {

				if (rs != null) {
					System.out.print("tarefa não e nula!");

					CadastroContrato.CadastroTarefa tarefa = new CadastroContrato.CadastroTarefa();

					tarefa.setId_tarefa(rs.getInt("id_tarefa"));
					tarefa.setStatus_tarefa(rs.getInt("status_tarefa"));
					tarefa.setNome_tarefa(rs.getString("nome_tarefa"));
					tarefa.setDescricao_tarefa(rs.getString("descricao_tarefa"));
					tarefa.setMensagem(rs.getString("mensagem"));
					tarefa.setHora(rs.getString("hora"));
					tarefa.setData(rs.getString("data_tarefa"));
					tarefa.setResposta(rs.getString("resposta"));

					CadastroLogin criador = new CadastroLogin();
					CadastroLogin executor = new CadastroLogin();

					criador.setId(rs.getInt("id_usuario_criador"));
					executor.setId(rs.getInt("id_usuario_executor"));

					tarefa.setCriador(criador);
					tarefa.setExecutor(executor);

					tarefa.setHora_agendada(rs.getString("hora_agendada"));
					tarefa.setData_agendada(rs.getString("data_agendada"));
					tarefa.setPrioridade(rs.getInt("prioridade"));

					if (lista_tarefas == null)
						lista_tarefas = new ArrayList<>();

					lista_tarefas.add(tarefa);

				}
			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			System.out.println("Tarefas foram listadas com sucesso!");
			return lista_tarefas;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao listar as tarefas do contrato: " + id_contrato + " erro: " + "causa: ");
			return null;
		}

	}

	public ArrayList<CadastroContrato.CadastroTarefa> getTodasTarefas() {
		System.out.println("Lista tarefas foi chamado!");
		String selectTarefas = "select * from contrato_tarefas \n"
				+ "LEFT JOIN tarefa  on tarefa.id_tarefa = contrato_tarefas.id_tarefa";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroContrato.CadastroTarefa> lista_tarefas = null;

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectTarefas);
			rs = pstm.executeQuery();
			int i = 0;

			while (rs.next()) {

				if (rs != null) {
					System.out.print("tarefa não e nula!");

					CadastroContrato.CadastroTarefa tarefa = new CadastroContrato.CadastroTarefa();

					tarefa.setId_tarefa(rs.getInt("id_tarefa"));
					tarefa.setStatus_tarefa(rs.getInt("status_tarefa"));
					tarefa.setNome_tarefa(rs.getString("nome_tarefa"));
					tarefa.setDescricao_tarefa(rs.getString("descricao_tarefa"));
					tarefa.setMensagem(rs.getString("mensagem"));
					tarefa.setHora(rs.getString("hora"));
					tarefa.setData(rs.getString("data_tarefa"));
					tarefa.setResposta(rs.getString("resposta"));

					CadastroLogin criador = new CadastroLogin();
					CadastroLogin executor = new CadastroLogin();

					criador.setId(rs.getInt("id_usuario_criador"));
					executor.setId(rs.getInt("id_usuario_executor"));

					tarefa.setCriador(criador);
					tarefa.setExecutor(executor);

					tarefa.setHora_agendada(rs.getString("hora_agendada"));
					tarefa.setData_agendada(rs.getString("data_agendada"));
					tarefa.setPrioridade(rs.getInt("prioridade"));

					if (lista_tarefas == null)
						lista_tarefas = new ArrayList<>();

					lista_tarefas.add(tarefa);

				}
			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			System.out.println("Tarefas foram listadas com sucesso!");
			return lista_tarefas;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar todas as tarefas \n erro: " + "causa: ");
			return null;
		}

	}
	
	
	public ArrayList<CadastroContrato.CadastroTarefa> getTarefasPorCriador(int id_criador) {
		System.out.println("Lista tarefas foi chamado!");
		String selectTarefas = "call busca_minhas_tarefas(?)";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroContrato.CadastroTarefa> lista_tarefas = null;

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectTarefas);
			pstm.setInt(1, id_criador);

			rs = pstm.executeQuery();
			int i = 0;

			while (rs.next()) {

				if (rs != null) {
					System.out.print("tarefa não e nula!");

					CadastroContrato.CadastroTarefa tarefa = new CadastroContrato.CadastroTarefa();

					tarefa.setId_tarefa(rs.getInt("id_tarefa"));
					tarefa.setStatus_tarefa(rs.getInt("status_tarefa"));
					tarefa.setNome_tarefa(rs.getString("nome_tarefa"));
					tarefa.setDescricao_tarefa(rs.getString("descricao_tarefa"));
					tarefa.setMensagem(rs.getString("mensagem"));
					tarefa.setHora(rs.getString("hora"));
					tarefa.setData(rs.getString("data_tarefa"));
					tarefa.setResposta(rs.getString("resposta"));

					CadastroLogin criador = new CadastroLogin();
					CadastroLogin executor = new CadastroLogin();

					criador.setId(rs.getInt("id_usuario_criador"));
					criador.setNome(rs.getString("nome_criador"));
					criador.setSobrenome(rs.getString("sobrenome_criador"));
					
					executor.setId(rs.getInt("id_usuario_executor"));
					executor.setNome(rs.getString("nome_executor"));
					executor.setSobrenome(rs.getString("sobrenome_executor"));
					
					tarefa.setCriador(criador);
					tarefa.setExecutor(executor);

					tarefa.setHora_agendada(rs.getString("hora_agendada"));
					tarefa.setData_agendada(rs.getString("data_agendada"));
					tarefa.setPrioridade(rs.getInt("prioridade"));

					if (lista_tarefas == null)
						lista_tarefas = new ArrayList<>();

					lista_tarefas.add(tarefa);

				}
			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			System.out.println("Tarefas foram listadas com sucesso!");
			return lista_tarefas;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar todas as tarefas \n erro: " + e.getMessage() + "\ncausa: " + e.getCause());
			return null;
		}

	}
	
	
	public ArrayList<CadastroContrato.CadastroTarefa> getTarefasComoExecutor(int id_executor) {
		System.out.println("Lista tarefas foi chamado!");
		String selectTarefas = "call busca_tarefas_designadas(?)";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroContrato.CadastroTarefa> lista_tarefas = null;

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectTarefas);
			pstm.setInt(1, id_executor);

			rs = pstm.executeQuery();
			int i = 0;

			while (rs.next()) {

				if (rs != null) {
					System.out.print("tarefa não e nula!");

					CadastroContrato.CadastroTarefa tarefa = new CadastroContrato.CadastroTarefa();

					tarefa.setId_tarefa(rs.getInt("id_tarefa"));
					tarefa.setStatus_tarefa(rs.getInt("status_tarefa"));
					tarefa.setNome_tarefa(rs.getString("nome_tarefa"));
					tarefa.setDescricao_tarefa(rs.getString("descricao_tarefa"));
					tarefa.setMensagem(rs.getString("mensagem"));
					tarefa.setHora(rs.getString("hora"));
					tarefa.setData(rs.getString("data_tarefa"));
					tarefa.setResposta(rs.getString("resposta"));

					CadastroLogin criador = new CadastroLogin();
					CadastroLogin executor = new CadastroLogin();

					criador.setId(rs.getInt("id_usuario_criador"));
					criador.setNome(rs.getString("nome_criador"));
					criador.setSobrenome(rs.getString("sobrenome_criador"));
					
					executor.setId(rs.getInt("id_usuario_executor"));
					executor.setNome(rs.getString("nome_executor"));
					executor.setSobrenome(rs.getString("sobrenome_executor"));
					
					tarefa.setCriador(criador);
					tarefa.setExecutor(executor);

					tarefa.setHora_agendada(rs.getString("hora_agendada"));
					tarefa.setData_agendada(rs.getString("data_agendada"));
					tarefa.setPrioridade(rs.getInt("prioridade"));

					if (lista_tarefas == null)
						lista_tarefas = new ArrayList<>();

					lista_tarefas.add(tarefa);

				}
			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			System.out.println("Tarefas foram listadas com sucesso!");
			return lista_tarefas;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar todas as tarefas \n erro: " + e.getMessage() + "\ncausa: " + e.getCause());
			return null;
		}

	}
	
	
	public boolean removerTarefas() {
		boolean retorno = false;

		for (int codigo_relacao_contrato_tarefa : registro_geral_relacao_contrato_tarefa.getIds()) {
			if (remover_contrato_tarefa(registro_geral_relacao_contrato_tarefa.getIdContrato(),
					codigo_relacao_contrato_tarefa)) {

				if (remover_tarefa(codigo_relacao_contrato_tarefa)) {
					retorno = true;

				} else {
					retorno = false;
					break;
				}

			} else {
				retorno = false;
				break;
			}
		}

		return retorno;

	}

	public boolean remover_contrato_tarefa(int id_contrato, int id_tarefa) {
		String sql_delete_contrato_tarefa = "DELETE FROM contrato_tarefas WHERE id_contrato = ? and id_tarefa = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_delete_contrato_tarefa);

			pstm.setInt(1, id_contrato);
			pstm.setInt(2, id_tarefa);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			JOptionPane.showMessageDialog(null, "Relacao contrato_tarefa excluida, banco normalizado ");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao excluir a relacao contrato_tarefa do banco de" + "dados " + f.getMessage());
			return false;
		}

	}

	public boolean remover_tarefa(int id_tarefa) {
		String sql_delete_tarefa = "DELETE FROM tarefa WHERE id_tarefa = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_delete_tarefa);

			pstm.setInt(1, id_tarefa);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			JOptionPane.showMessageDialog(null, "Tarefa excluida, banco normalizado ");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null, "Erro ao excluir a tarefa do banco de" + "dados " + f.getMessage());
			return false;
		}

	}

	public int inserirCarregamento(int id_contrato, CadastroContrato.Carregamento carregamento) {

		// inserir primeiro o carregamento
		int retorno_inserir_carregamento = inserir_carregamento_retorno(carregamento);
		if (retorno_inserir_carregamento > 0) {
			// inserir nova relacao contrato_carregamento
			boolean inserir_relacao_contrato_carregamento = inserir_contrato_carregamento(id_contrato,
					retorno_inserir_carregamento);
			if (inserir_relacao_contrato_carregamento) {
				System.out.println("Carregamento Cadastrado");
				return retorno_inserir_carregamento;

			} else {
				System.out.println("Erro ao inserir um novo carregamento!");
				return -1;

			}

		} else {
			System.out.println("Erro ao inserir um novo carregamento!");
			return -1;
		}

	}

	public int inserirRecebimento(int id_contrato, Recebimento recebimento) {

		// inserir primeiro o recebimento
		int retorno_inserir_recebimento = inserir_recebimento_retorno(recebimento);
		if (retorno_inserir_recebimento > 0) {
			// inserir nova relacao contrato_recebimento
			boolean inserir_relacao_contrato_recebimento = inserir_contrato_recebimento(id_contrato,
					retorno_inserir_recebimento);
			if (inserir_relacao_contrato_recebimento) {
				System.out.println("Recebimento Cadastrado");
				return retorno_inserir_recebimento;

			} else {
				System.out.println("Erro ao inserir um novo recebimento!");
				return -1;

			}

		} else {
			System.out.println("Erro ao inserir um novo recebimento!");
			return -1;
		}

	}

	public boolean atualizarStatusTarefa(String resposta, int id_tarefa) {

		Connection conn = null;
		String atualizar = "update tarefa set status_tarefa = 1, resposta = ? where id_tarefa = ?";
		PreparedStatement pstm;

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(atualizar);
			pstm.setString(1, resposta);
			pstm.setInt(2, id_tarefa);
			pstm.execute();
			// JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
			System.out.println("Tarefa Atualizada com sucesso");
			ConexaoBanco.fechaConexao(conn);
			return true;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao atualizar tarefa no banco de" + "dados ");
			return false;
		}

	}

	public int inserir_carregamento_retorno(CadastroContrato.Carregamento carregamento) {

		int result = -1;
		if (carregamento != null) {
			Connection conn = null;
			try {

				conn = ConexaoBanco.getConexao();

				String query = sql_carregamento(carregamento);
				Statement stmt = (Statement) conn.createStatement();
				int numero = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					result = rs.getInt(1);
					System.out.println("Id carregamento inserido: " + result);
				}
				rs.close();
				stmt.close();

				return result;

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Erro ao inserir o carregamento no banco de dados\nErro: " + e.getMessage());
				GerenciadorLog.registrarLogDiario("falha",
						"falha ao adicionar carregamento: " + " causa: " + e.getCause());
				return -1;
			}
		} else {
			System.out.println("O carregamento enviado por parametro esta vazio");
			return -1;
		}

	}

	public int inserir_recebimento_retorno(CadastroContrato.Recebimento recebimento) {

		int result = -1;
		if (recebimento != null) {
			Connection conn = null;
			try {

				conn = ConexaoBanco.getConexao();

				String query = sql_recebimento(recebimento);
				Statement stmt = (Statement) conn.createStatement();
				int numero = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					result = rs.getInt(1);
					System.out.println("Id recebimento inserido: " + result);
				}
				rs.close();
				stmt.close();

				return result;

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao inserir o recebimento no banco de " + "dados ");
				GerenciadorLog.registrarLogDiario("falha", "falha ao adicionar carregamento: " + " causa: ");
				return -1;
			}
		} else {
			System.out.println("O recebimento enviado por parametro esta vazio");
			return -1;
		}

	}

	private boolean inserir_contrato_carregamento(int id_contrato, int id_carregamento) {
		Connection conn = null;
		try {
			conn = ConexaoBanco.getConexao();
			String sql = "insert into contrato_carregamentos\r\n" + "(id_contrato, id_carregamento) values ('"
					+ id_contrato + "','" + id_carregamento + "')";

			PreparedStatement grava = (PreparedStatement) conn.prepareStatement(sql);
			grava.execute();
			ConexaoBanco.fechaConexao(conn, grava);
			// JOptionPane.showMessageDialog(null, "Relação contrato_carregamento cadastrado
			// com sucesso");

			return true;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao inserir a relação contrato_carregamento no banco de dados ");
			return false;
		}

	}

	private boolean inserir_contrato_recebimento(int id_contrato, int id_recebimento) {
		Connection conn = null;
		try {
			conn = ConexaoBanco.getConexao();
			String sql = "insert into contrato_recebimentos\r\n" + "(id_contrato, id_recebimento) values ('"
					+ id_contrato + "','" + id_recebimento + "')";

			PreparedStatement grava = (PreparedStatement) conn.prepareStatement(sql);
			grava.execute();
			ConexaoBanco.fechaConexao(conn, grava);
			// JOptionPane.showMessageDialog(null, "Relação contrato_carregamento cadastrado
			// com sucesso");

			return true;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao inserir a relação contrato_recebimentos no banco de dados ");
			return false;
		}

	}

	public ArrayList<CadastroContrato.Carregamento> getCarregamentos(int id_contrato) {

		System.out.println("Lista carregamento foi chamado!");
		/*String selectCarregamentos = "select * from contrato_carregamentos \n"
				+ "LEFT JOIN carregamento  on carregamento.id_carregamento = contrato_carregamentos.id_carregamento \n"
				+ "where contrato_carregamentos.id_contrato = ? order by carregamento.id_carregamento";
*/
		/*
		 select carregamento.*,
(case
when transportador.tipo_cliente = '0' then transportador.nome_empresarial 
 when transportador.tipo_cliente = '1' then transportador.nome_fantasia
end) as nome_transportador,
(case
when comprador.tipo_cliente = '0' then comprador.nome_empresarial 
 when comprador.tipo_cliente = '1' then comprador.nome_fantasia
end) as nome_comprador,
(case
when vendedor.tipo_cliente = '0' then vendedor.nome_empresarial 
 when vendedor.tipo_cliente = '1' then vendedor.nome_fantasia
end) as nome_vendedor,
ct.codigo as codigo_contrato,
veiculo.placa, pd.nome_produto
 from contrato_carregamentos
 
LEFT JOIN carregamento  on carregamento.id_carregamento = contrato_carregamentos.id_carregamento 
left join cliente transportador on transportador.id_cliente  = carregamento.id_transportador 
left join cliente comprador on comprador.id_cliente  = carregamento.id_cliente 
left join cliente vendedor on vendedor.id_cliente  = carregamento.id_vendedor 
left join veiculo on veiculo.id_veiculo = carregamento.id_veiculo
left join contrato ct on ct.id = carregamento.id_contrato_carregamento
left join produto pd on pd.id_produto = ct.id_produto
where contrato_carregamentos.id_contrato = ? order by carregamento.id_carregamento
		 */
		String selectCarregamentos = "select carregamento.*,\n"
				+ "(case\n"
				+ "when transportador.tipo_cliente = '0' then transportador.nome_empresarial \n"
				+ " when transportador.tipo_cliente = '1' then transportador.nome_fantasia\n"
				+ "end) as nome_transportador,\n"
				+ "(case\n"
				+ "when comprador.tipo_cliente = '0' then comprador.nome_empresarial \n"
				+ " when comprador.tipo_cliente = '1' then comprador.nome_fantasia\n"
				+ "end) as nome_comprador,\n"
				+ "(case\n"
				+ "when vendedor.tipo_cliente = '0' then vendedor.nome_empresarial \n"
				+ " when vendedor.tipo_cliente = '1' then vendedor.nome_fantasia\n"
				+ "end) as nome_vendedor,\n"
				+ "ct.codigo as codigo_contrato,\n"
				+ "veiculo.placa, pd.nome_produto \n"
				+ " from contrato_carregamentos\n"
				+ " \n"
				+ "LEFT JOIN carregamento  on carregamento.id_carregamento = contrato_carregamentos.id_carregamento \n"
				+ "left join cliente transportador on transportador.id_cliente  = carregamento.id_transportador \n"
				+ "left join cliente comprador on comprador.id_cliente  = carregamento.id_cliente \n"
				+ "left join cliente vendedor on vendedor.id_cliente  = carregamento.id_vendedor \n"
				+ "left join veiculo on veiculo.id_veiculo = carregamento.id_veiculo\n"
				+ "left join contrato ct on ct.id = carregamento.id_contrato_carregamento\n"
				+ "left join produto pd on pd.id_produto = ct.id_produto\n"
				+ "where contrato_carregamentos.id_contrato = ? order by carregamento.id_carregamento";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroContrato.Carregamento> lista_carregamentos = new ArrayList<>();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectCarregamentos);
			pstm.setInt(1, id_contrato);

			rs = pstm.executeQuery();

			while (rs.next()) {

				if (rs != null) {
					System.out.print("carregamento não e nulo!");

					CadastroContrato.Carregamento carga = new CadastroContrato.Carregamento();

					carga.setCodigo_contrato(rs.getString("codigo_contrato"));
					carga.setNome_comprador(rs.getString("nome_comprador"));
					carga.setNome_vendedor(rs.getString("nome_vendedor"));
					carga.setNome_transportador(rs.getString("nome_transportador"));
					carga.setNome_produto(rs.getString("nome_produto"));
					carga.setPlaca_veiculo(rs.getString("placa"));

					
					carga.setId_carregamento(rs.getInt("id_carregamento"));
					carga.setData(rs.getString("data_carregamento").toString());
					carga.setId_contrato(rs.getInt("id_contrato_carregamento"));
					carga.setId_cliente(rs.getInt("id_cliente"));
					carga.setId_vendedor(rs.getInt("id_vendedor"));
					carga.setId_transportador(rs.getInt("id_transportador"));
					carga.setId_veiculo(rs.getInt("id_veiculo"));
					carga.setId_produto(rs.getInt("id_produto"));

					carga.setCodigo_romaneio(rs.getString("codigo_romaneio"));
					carga.setPeso_romaneio(rs.getDouble("peso_romaneio"));
					carga.setCaminho_romaneio(rs.getString("caminho_romaneio"));

					carga.setNf_interna_aplicavel(rs.getInt("nf_interna_aplicavel"));
					carga.setCodigo_nf_interna(rs.getString("codigo_nf_interna"));
					carga.setPeso_nf_interna(rs.getDouble("peso_nf_interna"));
					carga.setCaminho_nf_interna(rs.getString("caminho_nf_interna"));

					carga.setNf_venda1_aplicavel(rs.getInt("nf_venda1_aplicavel"));
					carga.setCodigo_nf_venda1(rs.getString("codigo_nf_venda1"));
					carga.setPeso_nf_venda1(rs.getDouble("peso_nf_venda1"));
					try {
						carga.setValor_nf_venda1(new BigDecimal(rs.getString("valor_nf_venda1")));
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null,
								"Erro ao criar bigdecimal do valor de venda1\nErro: " + e.getMessage());
						carga.setValor_nf_venda1(BigDecimal.ZERO);

					}
					carga.setNome_remetente_nf_venda1(rs.getString("nome_remetente_nf_venda1"));
					carga.setNome_destinatario_nf_venda1(rs.getString("nome_destinatario_nf_venda1"));

					carga.setCaminho_nf_venda1(rs.getString("caminho_nf_venda1"));

					carga.setNf_complemento_aplicavel(rs.getInt("nf_complemento_aplicavel"));
					carga.setCodigo_nf_complemento(rs.getString("codigo_nf_complemento"));
					carga.setPeso_nf_complemento(rs.getDouble("peso_nf_complemento"));
					try {
						carga.setValor_nf_complemento(new BigDecimal(rs.getString("valor_nf_complemento")));
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null,
								"Erro ao criar bigdecimal do valor de nf complemento\nErro: " + e.getMessage());

						carga.setValor_nf_complemento(BigDecimal.ZERO);

					}
					carga.setNome_remetente_nf_complemento(rs.getString("nome_remetente_nf_complemento"));
					carga.setNome_destinatario_nf_complemento(rs.getString("nome_destinatario_nf_complemento"));
					carga.setCaminho_nf_complemento(rs.getString("caminho_nf_complemento"));

					carga.setObservacao(rs.getString("observacao"));

					lista_carregamentos.add(carga);

				}
			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			System.out.println("Carregamentos foram listadas com sucesso!");
			return lista_carregamentos;
		} catch (Exception e) {
		//	JOptionPane.showMessageDialog(null, "Erro ao listar os carregamentos do contrato: " + id_contrato
			//		+ " erro: " + e.getMessage() + "causa: " + e.getCause());
			return null;
		}

	}

	public ArrayList<CadastroContrato.Carregamento> getCarregamentos2() {

		System.out.println("Lista carregamento foi chamado!");
		String selectCarregamentos = "select * from contrato_carregamentos \n"
				+ "LEFT JOIN carregamento  on carregamento.id_carregamento = contrato_carregamentos.id_carregamento \n"
				+ "order by carregamento.id_carregamento";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroContrato.Carregamento> lista_carregamentos = new ArrayList<>();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectCarregamentos);

			rs = pstm.executeQuery();

			while (rs.next()) {

				if (rs != null) {
					System.out.print("carregamento não e nulo!");

					CadastroContrato.Carregamento carga = new CadastroContrato.Carregamento();

					carga.setId_carregamento(rs.getInt("id_carregamento"));
					carga.setData(rs.getString("data_carregamento").toString());
					carga.setId_contrato(rs.getInt("id_contrato_carregamento"));
					carga.setId_cliente(rs.getInt("id_cliente"));
					carga.setId_vendedor(rs.getInt("id_vendedor"));
					carga.setId_transportador(rs.getInt("id_transportador"));
					carga.setId_veiculo(rs.getInt("id_veiculo"));
					carga.setId_produto(rs.getInt("id_produto"));

					carga.setCodigo_romaneio(rs.getString("codigo_romaneio"));
					carga.setPeso_romaneio(rs.getDouble("peso_romaneio"));
					carga.setCaminho_romaneio(rs.getString("caminho_romaneio"));

					carga.setNf_interna_aplicavel(rs.getInt("nf_interna_aplicavel"));
					carga.setCodigo_nf_interna(rs.getString("codigo_nf_interna"));
					carga.setPeso_nf_interna(rs.getDouble("peso_nf_interna"));
					carga.setCaminho_nf_interna(rs.getString("caminho_nf_interna"));

					carga.setNf_venda1_aplicavel(rs.getInt("nf_venda1_aplicavel"));
					carga.setCodigo_nf_venda1(rs.getString("codigo_nf_venda1"));
					carga.setPeso_nf_venda1(rs.getDouble("peso_nf_venda1"));
					try {
						carga.setValor_nf_venda1(new BigDecimal(rs.getString("valor_nf_venda1")));
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null,
								"Erro ao criar bigdecimal do valor de venda1\nErro: " + e.getMessage());
						carga.setValor_nf_venda1(BigDecimal.ZERO);

					}
					carga.setNome_remetente_nf_venda1(rs.getString("nome_remetente_nf_venda1"));
					carga.setNome_destinatario_nf_venda1(rs.getString("nome_destinatario_nf_venda1"));

					carga.setCaminho_nf_venda1(rs.getString("caminho_nf_venda1"));

					carga.setNf_complemento_aplicavel(rs.getInt("nf_complemento_aplicavel"));
					carga.setCodigo_nf_complemento(rs.getString("codigo_nf_complemento"));
					carga.setPeso_nf_complemento(rs.getDouble("peso_nf_complemento"));
					try {
						carga.setValor_nf_complemento(new BigDecimal(rs.getString("valor_nf_complemento")));
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null,
								"Erro ao criar bigdecimal do valor de nf complemento\nErro: " + e.getMessage());

						carga.setValor_nf_complemento(BigDecimal.ZERO);

					}
					carga.setNome_remetente_nf_complemento(rs.getString("nome_remetente_nf_complemento"));
					carga.setNome_destinatario_nf_complemento(rs.getString("nome_destinatario_nf_complemento"));
					carga.setCaminho_nf_complemento(rs.getString("caminho_nf_complemento"));

					carga.setObservacao(rs.getString("observacao"));

					lista_carregamentos.add(carga);

				}
			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			System.out.println("Carregamentos foram listadas com sucesso!");
			return lista_carregamentos;
		} catch (Exception e) {
		//	JOptionPane.showMessageDialog(null, "Erro ao listar os carregamentos do contrato: " + " erro: "
			//		+ e.getMessage() + "causa: " + e.getCause());
			return null;
		}

	}

	public ArrayList<CadastroContrato.Recebimento> getRecebimentos(int id_contrato) {

		System.out.println("Lista recebimentos foi chamado!");
		String selectRecebimentos = "select * from contrato_recebimentos\n"
				+ "LEFT JOIN recebimento  on recebimento.id_recebimento = contrato_recebimentos.id_recebimento \n"
				+ "where contrato_recebimentos.id_contrato = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroContrato.Recebimento> lista_recebimentos = new ArrayList<>();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectRecebimentos);
			pstm.setInt(1, id_contrato);

			rs = pstm.executeQuery();

			while (rs.next()) {

				if (rs != null) {
					System.out.print("recebimento não e nulo!");

					CadastroContrato.Recebimento recebido = new CadastroContrato.Recebimento();

					recebido.setId_recebimento(rs.getInt("id_recebimento"));
					recebido.setData_recebimento(rs.getString("data_recebimento").toString());
					recebido.setId_contrato_recebimento(rs.getInt("id_contrato_recebimento"));
					recebido.setId_cliente(rs.getInt("id_cliente"));
					recebido.setId_vendedor(rs.getInt("id_vendedor"));
					recebido.setId_transportador(rs.getInt("id_transportador"));
					recebido.setId_veiculo(rs.getInt("id_veiculo"));
					recebido.setCodigo_romaneio(rs.getString("codigo_romaneio"));
					recebido.setPeso_romaneio(rs.getDouble("peso_romaneio"));
					recebido.setCaminho_romaneio(rs.getString("caminho_romaneio"));

					recebido.setNf_venda_aplicavel(rs.getInt("nf_venda_aplicavel"));
					recebido.setCodigo_nf_venda(rs.getString("codigo_nf_venda"));
					recebido.setPeso_nf_venda(rs.getDouble("peso_nf_venda"));
					recebido.setValor_nf_venda(new BigDecimal(rs.getString("valor_nf_venda")));
					recebido.setNome_remetente_nf_venda(rs.getString("nome_remetente_nf_venda"));
					recebido.setNome_destinatario_nf_venda(rs.getString("nome_destinatario_nf_venda"));
					recebido.setCaminho_nf_venda(rs.getString("caminho_nf_venda"));

					recebido.setNf_remessa_aplicavel(rs.getInt("nf_remessa_aplicavel"));
					recebido.setCodigo_nf_remessa(rs.getString("codigo_nf_remessa"));
					recebido.setPeso_nf_remessa(rs.getDouble("peso_nf_remessa"));
					recebido.setValor_nf_remessa(new BigDecimal(rs.getString("valor_nf_remessa")));
					recebido.setNome_remetente_nf_remessa(rs.getString("nome_remetente_nf_remessa"));
					recebido.setNome_destinatario_nf_remessa(rs.getString("nome_destinatario_nf_remessa"));
					recebido.setCaminho_nf_remessa(rs.getString("caminho_nf_remessa"));

					lista_recebimentos.add(recebido);

				}
			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			System.out.println("Recebimentos foram listadas com sucesso!");
			return lista_recebimentos;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar os recebimentos do contrato: " + id_contrato + " erro: "
					+ e.getMessage() + "causa: " + e.getCause());
			return null;
		}

	}

	public ArrayList<RecebimentoCompleto> getRecebimentosParaRelatorio(int id_contrato) {

		System.out.println("Lista recebimentos foi chamado!");
		String selectRecebimentos = "select re.*,\n"
				+ "ct.id as contrato_id, ct.codigo, ct.quantidade, ct.medida, ct.valor_a_pagar, ct.valor_produto,\n"
				+ "GROUP_CONCAT(distinct\n" + "case\n"
				+ "when comprador.tipo_cliente = '0' then comprador.nome_empresarial \n"
				+ " when comprador.tipo_cliente = '1' then comprador.nome_fantasia\n" + "end\n"
				+ " separator ',') as compradores,\n" + "GROUP_CONCAT(distinct\n" + "case\n"
				+ "when vendedor.tipo_cliente = '0' then vendedor.nome_empresarial \n"
				+ " when vendedor.tipo_cliente = '1' then vendedor.nome_fantasia\n" + "end\n"
				+ " separator ',') as vendedores,\n" + " sf.id_safra, sf.ano_colheita, sf.ano_plantio,\n"
				+ " pd.id_produto, pd.nome_produto, pd.transgenia \n" + " from contrato_recebimentos cr\n"
				+ "LEFT JOIN recebimento re  on re.id_recebimento = cr.id_recebimento \n"
				+ "LEFT JOIN contrato ct on ct.id = cr.id_contrato\n"
				+ "LEFT join safra sf on sf.id_safra = ct.id_safra\n"
				+ "LEFT join produto pd on pd.id_produto = sf.id_produto\n"
				+ "LEFT JOIN contrato_comprador on contrato_comprador.id_contrato = ct.id\n"
				+ "LEFT JOIN cliente comprador on comprador.id_cliente = contrato_comprador.id_cliente\n"
				+ "LEFT JOIN contrato_vendedor on contrato_vendedor.id_contrato = ct.id\n"
				+ "LEFT JOIN cliente vendedor on vendedor.id_cliente = contrato_vendedor.id_cliente\n"
				+ "where ct.id = ?\n" + "group by re.id_recebimento";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<RecebimentoCompleto> lista_recebimentos = new ArrayList<>();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectRecebimentos);
			pstm.setInt(1, id_contrato);

			rs = pstm.executeQuery();

			while (rs.next()) {

				if (rs != null) {
					System.out.print("recebimento não e nulo!");

					RecebimentoCompleto recebido = new RecebimentoCompleto();

					recebido.setId_recebimento(rs.getInt("id_recebimento"));
					recebido.setData_recebimento(rs.getString("data_recebimento").toString());
					recebido.setId_contrato_recebimento(rs.getInt("id_contrato_recebimento"));
					recebido.setId_cliente(rs.getInt("id_cliente"));
					recebido.setId_vendedor(rs.getInt("id_vendedor"));
					recebido.setId_transportador(rs.getInt("id_transportador"));
					recebido.setId_veiculo(rs.getInt("id_veiculo"));
					recebido.setCodigo_romaneio(rs.getString("codigo_romaneio"));
					recebido.setPeso_romaneio(rs.getDouble("peso_romaneio"));
					recebido.setCaminho_romaneio(rs.getString("caminho_romaneio"));

					recebido.setNf_venda_aplicavel(rs.getInt("nf_venda_aplicavel"));
					recebido.setCodigo_nf_venda(rs.getString("codigo_nf_venda"));
					recebido.setPeso_nf_venda(rs.getDouble("peso_nf_venda"));
					recebido.setValor_nf_venda(new BigDecimal(rs.getString("valor_nf_venda")));
					recebido.setNome_remetente_nf_venda(rs.getString("nome_remetente_nf_venda"));
					recebido.setNome_destinatario_nf_venda(rs.getString("nome_destinatario_nf_venda"));
					recebido.setCaminho_nf_venda(rs.getString("caminho_nf_venda"));

					recebido.setNf_remessa_aplicavel(rs.getInt("nf_remessa_aplicavel"));
					recebido.setCodigo_nf_remessa(rs.getString("codigo_nf_remessa"));
					recebido.setPeso_nf_remessa(rs.getDouble("peso_nf_remessa"));
					recebido.setValor_nf_remessa(new BigDecimal(rs.getString("valor_nf_remessa")));
					recebido.setNome_remetente_nf_remessa(rs.getString("nome_remetente_nf_remessa"));
					recebido.setNome_destinatario_nf_remessa(rs.getString("nome_destinatario_nf_remessa"));
					recebido.setCaminho_nf_remessa(rs.getString("caminho_nf_remessa"));

					CadastroContrato contrato_recebimento = new CadastroContrato();
					contrato_recebimento.setId(rs.getInt("contrato_id"));
					contrato_recebimento.setCodigo(rs.getString("codigo"));
					contrato_recebimento.setQuantidade(rs.getDouble("quantidade"));
					contrato_recebimento.setMedida(rs.getString("medida"));
					contrato_recebimento.setValor_a_pagar(new BigDecimal(rs.getString("valor_a_pagar")));
					contrato_recebimento.setValor_produto(rs.getDouble("valor_produto"));
					contrato_recebimento.setNomes_compradores(rs.getString("compradores"));
					contrato_recebimento.setNomes_vendedores(rs.getString("vendedores"));

					CadastroSafra safra = new CadastroSafra();
					safra.setId_safra(rs.getInt("id_safra"));
					safra.setAno_colheita(rs.getInt("ano_colheita"));
					safra.setAno_plantio(rs.getInt("ano_plantio"));

					CadastroProduto produto = new CadastroProduto();
					produto.setId_produto(rs.getInt("id_produto"));
					produto.setNome_produto(rs.getString("nome_produto"));
					produto.setTransgenia(rs.getString("transgenia"));

					safra.setProduto(produto);

					contrato_recebimento.setModelo_produto(produto);
					contrato_recebimento.setModelo_safra(safra);

					recebido.setContrato(contrato_recebimento);

					lista_recebimentos.add(recebido);

				}
			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			System.out.println("Recebimentos foram listadas com sucesso!");
			return lista_recebimentos;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar os recebimentos do contrato: " + id_contrato + " erro: "
					+ e.getMessage() + "causa: " + e.getCause());
			return null;
		}

	}

	public ArrayList<CarregamentoCompleto> getCarregamentoParaRelatorio(int id_contrato) {

		System.out.println("Lista carregamentos foi chamado!");
		String selectCarregamentos = "select re.*,\n"
				+ "ct.id as contrato_id, ct.codigo, ct.quantidade, ct.medida, ct.valor_a_pagar, ct.valor_produto,\n"
				+ "GROUP_CONCAT(distinct\n" + "case\n"
				+ "when comprador.tipo_cliente = '0' then comprador.nome_empresarial \n"
				+ " when comprador.tipo_cliente = '1' then comprador.nome_fantasia\n" + "end\n"
				+ " separator ',') as compradores,\n" + "GROUP_CONCAT(distinct\n" + "case\n"
				+ "when vendedor.tipo_cliente = '0' then vendedor.nome_empresarial \n"
				+ " when vendedor.tipo_cliente = '1' then vendedor.nome_fantasia\n" + "end\n"
				+ " separator ',') as vendedores,\n" + " GROUP_CONCAT(distinct\n" + "case\n"
				+ "when transportador.tipo_cliente = '0' then transportador.nome_empresarial \n"
				+ " when transportador.tipo_cliente = '1' then transportador.nome_fantasia\n" + "end\n"
				+ " separator ',') as motorista,\n" + " ve.placa,\n"
				+ " sf.id_safra, sf.ano_colheita, sf.ano_plantio,\n"
				+ " pd.id_produto, pd.nome_produto, pd.transgenia,\n" + " GROUP_CONCAT(distinct\n" + "case\n"
				+ "when cliente_carregamento.tipo_cliente = '0' then cliente_carregamento.nome_empresarial \n"
				+ " when cliente_carregamento.tipo_cliente = '1' then cliente_carregamento.nome_fantasia\n" + "end\n"
				+ " separator ',') as cliente_carregamento,\n" + " GROUP_CONCAT(distinct\n" + "case\n"
				+ "when vendedor_carregamento.tipo_cliente = '0' then vendedor_carregamento.nome_empresarial \n"
				+ " when vendedor_carregamento.tipo_cliente = '1' then vendedor_carregamento.nome_fantasia\n" + "end\n"
				+ " separator ',') as vendedor_carregamento\n" + " from contrato_carregamentos cr\n"
				+ "LEFT JOIN carregamento re  on re.id_carregamento = cr.id_carregamento \n"
				+ "LEFT JOIN contrato ct on ct.id = cr.id_contrato\n"
				+ "LEFT join safra sf on sf.id_safra = ct.id_safra\n"
				+ "LEFT join produto pd on pd.id_produto = sf.id_produto\n"
				+ "LEFT JOIN contrato_comprador on contrato_comprador.id_contrato = ct.id\n"
				+ "LEFT JOIN cliente comprador on comprador.id_cliente = contrato_comprador.id_cliente\n"
				+ "LEFT JOIN cliente cliente_carregamento on cliente_carregamento.id_cliente = re.id_cliente\n"
				+ "LEFT JOIN cliente vendedor_carregamento on vendedor_carregamento.id_cliente = re.id_vendedor\n"
				+ "LEFT JOIN contrato_vendedor on contrato_vendedor.id_contrato = ct.id\n"
				+ "LEFT JOIN cliente vendedor on vendedor.id_cliente = contrato_vendedor.id_cliente\n"
				+ "left join cliente transportador on re.id_transportador = transportador.id_cliente\n"
				+ "left join veiculo ve on ve.id_veiculo = re.id_veiculo\n" + "where ct.id = ?\n"
				+ "group by re.id_carregamento";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CarregamentoCompleto> lista_carregamentos = new ArrayList<>();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectCarregamentos);
			pstm.setInt(1, id_contrato);

			rs = pstm.executeQuery();

			while (rs.next()) {

				if (rs != null) {
					System.out.print("carregamento não e nulo!");

					CarregamentoCompleto carga = new CarregamentoCompleto();

					carga.setId_carregamento(rs.getInt("id_carregamento"));
					carga.setData(rs.getString("data_carregamento"));
					carga.setId_contrato(rs.getInt("id_contrato_carregamento"));
					carga.setId_cliente(rs.getInt("id_cliente"));
					carga.setCliente_carregamento(rs.getString("cliente_carregamento"));
					carga.setVendedor_carregamento(rs.getString("vendedor_carregamento"));
					carga.setId_vendedor(rs.getInt("id_vendedor"));
					carga.setId_transportador(rs.getInt("id_transportador"));
					carga.setId_veiculo(rs.getInt("id_veiculo"));
					carga.setId_produto(rs.getInt("id_produto"));

					carga.setCodigo_romaneio(rs.getString("codigo_romaneio"));
					carga.setPeso_romaneio(rs.getDouble("peso_romaneio"));
					carga.setCaminho_romaneio(rs.getString("caminho_romaneio"));

					carga.setNf_interna_aplicavel(rs.getInt("nf_interna_aplicavel"));
					carga.setCodigo_nf_interna(rs.getString("codigo_nf_interna"));
					carga.setPeso_nf_interna(rs.getDouble("peso_nf_interna"));
					carga.setCaminho_nf_interna(rs.getString("caminho_nf_interna"));

					carga.setNf_venda1_aplicavel(rs.getInt("nf_venda1_aplicavel"));
					carga.setCodigo_nf_venda1(rs.getString("codigo_nf_venda1"));
					carga.setPeso_nf_venda1(rs.getDouble("peso_nf_venda1"));
					carga.setValor_nf_venda1(new BigDecimal(rs.getString("valor_nf_venda1")));
					carga.setNome_remetente_nf_venda1(rs.getString("nome_remetente_nf_venda1"));
					carga.setNome_destinatario_nf_venda1(rs.getString("nome_destinatario_nf_venda1"));
					carga.setCaminho_nf_venda1(rs.getString("caminho_nf_venda1"));

					carga.setNf_complemento_aplicavel(rs.getInt("nf_complemento_aplicavel"));
					carga.setCodigo_nf_complemento(rs.getString("codigo_nf_complemento"));
					carga.setPeso_nf_complemento(rs.getDouble("peso_nf_complemento"));
					carga.setValor_nf_complemento(new BigDecimal(rs.getString("valor_nf_complemento")));
					carga.setNome_remetente_nf_complemento(rs.getString("nome_remetente_nf_complemento"));
					carga.setNome_destinatario_nf_complemento(rs.getString("nome_destinatario_nf_complemento"));
					carga.setCaminho_nf_complemento(rs.getString("caminho_nf_complemento"));

					carga.setObservacao(rs.getString("observacao"));

					CadastroContrato contrato_carregamento = new CadastroContrato();
					contrato_carregamento.setId(rs.getInt("contrato_id"));
					contrato_carregamento.setCodigo(rs.getString("codigo"));
					contrato_carregamento.setQuantidade(rs.getDouble("quantidade"));
					contrato_carregamento.setMedida(rs.getString("medida"));
					contrato_carregamento.setValor_a_pagar(new BigDecimal(rs.getString("valor_a_pagar")));
					contrato_carregamento.setValor_produto(rs.getDouble("valor_produto"));
					contrato_carregamento.setNomes_compradores(rs.getString("compradores"));
					contrato_carregamento.setNomes_vendedores(rs.getString("vendedores"));

					CadastroSafra safra = new CadastroSafra();
					safra.setId_safra(rs.getInt("id_safra"));
					safra.setAno_colheita(rs.getInt("ano_colheita"));
					safra.setAno_plantio(rs.getInt("ano_plantio"));

					CadastroProduto produto = new CadastroProduto();
					produto.setId_produto(rs.getInt("id_produto"));
					produto.setNome_produto(rs.getString("nome_produto"));
					produto.setTransgenia(rs.getString("transgenia"));

					carga.setNome_motorista(rs.getString("motorista"));
					carga.setPlaca(rs.getString("placa"));

					safra.setProduto(produto);

					contrato_carregamento.setModelo_produto(produto);
					contrato_carregamento.setModelo_safra(safra);

					carga.setContrato(contrato_carregamento);

					lista_carregamentos.add(carga);

				}
			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			System.out.println("Carregamentos foram listadas com sucesso!");
			return lista_carregamentos;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar os carregamentos do contrato: " + id_contrato
					+ " erro: " + e.getMessage() + "causa: " + e.getCause());
			return null;
		}

	}

	public ArrayList<RecebimentoCompleto> getRecebimentos() {
		int id = 0;
		System.out.println("Lista recebimentos foi chamado!");
		String selectRecebimentos = "select re.*,\n"
				+ "ct.id as contrato_id, ct.codigo, ct.quantidade, ct.medida, ct.valor_a_pagar, ct.valor_produto,\n"
				+ "GROUP_CONCAT(distinct\n" + "case\n"
				+ "when comprador.tipo_cliente = '0' then comprador.nome_empresarial \n"
				+ " when comprador.tipo_cliente = '1' then comprador.nome_fantasia\n" + "end\n"
				+ " separator ',') as compradores,\n" + "GROUP_CONCAT(distinct\n" + "case\n"
				+ "when vendedor.tipo_cliente = '0' then vendedor.nome_empresarial \n"
				+ " when vendedor.tipo_cliente = '1' then vendedor.nome_fantasia\n" + "end\n"
				+ " separator ',') as vendedores,\n" + " sf.id_safra, sf.ano_colheita, sf.ano_plantio,\n"
				+ " pd.id_produto, pd.nome_produto, pd.transgenia \n" + " from contrato_recebimentos cr\n"
				+ "LEFT JOIN recebimento re  on re.id_recebimento = cr.id_recebimento \n"
				+ "LEFT JOIN contrato ct on ct.id = cr.id_contrato\n"
				+ "LEFT join safra sf on sf.id_safra = ct.id_safra\n"
				+ "LEFT join produto pd on pd.id_produto = sf.id_produto\n"
				+ "LEFT JOIN contrato_comprador on contrato_comprador.id_contrato = ct.id\n"
				+ "LEFT JOIN cliente comprador on comprador.id_cliente = contrato_comprador.id_cliente\n"
				+ "LEFT JOIN contrato_vendedor on contrato_vendedor.id_contrato = ct.id\n"
				+ "LEFT JOIN cliente vendedor on vendedor.id_cliente = contrato_vendedor.id_cliente\n"
				+ "group by re.id_recebimento";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<RecebimentoCompleto> lista_recebimentos = new ArrayList<>();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectRecebimentos);

			rs = pstm.executeQuery();

			while (rs.next()) {

				if (rs != null) {
					if (rs.getInt("id_recebimento") > 0) {
						System.out.print("recebimento não e nulo!");

						RecebimentoCompleto recebido = new RecebimentoCompleto();

						recebido.setId_recebimento(rs.getInt("id_recebimento"));

						recebido.setData_recebimento(rs.getString("data_recebimento").toString());

						recebido.setId_contrato_recebimento(rs.getInt("id_contrato_recebimento"));
						recebido.setId_cliente(rs.getInt("id_cliente"));
						recebido.setId_vendedor(rs.getInt("id_vendedor"));
						recebido.setId_transportador(rs.getInt("id_transportador"));
						recebido.setId_veiculo(rs.getInt("id_veiculo"));

						recebido.setCodigo_romaneio(rs.getString("codigo_romaneio"));
						recebido.setPeso_romaneio(rs.getDouble("peso_romaneio"));
						recebido.setCaminho_romaneio(rs.getString("caminho_romaneio"));

						recebido.setNf_venda_aplicavel(rs.getInt("nf_venda_aplicavel"));
						recebido.setCodigo_nf_venda(rs.getString("codigo_nf_venda"));
						recebido.setPeso_nf_venda(rs.getDouble("peso_nf_venda"));
						recebido.setValor_nf_venda(new BigDecimal(rs.getString("valor_nf_venda")));
						recebido.setNome_remetente_nf_venda(rs.getString("nome_remetente_nf_venda"));
						recebido.setNome_destinatario_nf_venda(rs.getString("nome_destinatario_nf_venda"));
						recebido.setCaminho_nf_venda(rs.getString("caminho_nf_venda"));

						recebido.setNf_remessa_aplicavel(rs.getInt("nf_remessa_aplicavel"));
						recebido.setCodigo_nf_remessa(rs.getString("codigo_nf_remessa"));
						recebido.setPeso_nf_remessa(rs.getDouble("peso_nf_remessa"));
						recebido.setValor_nf_remessa(new BigDecimal(rs.getString("valor_nf_remessa")));
						recebido.setNome_remetente_nf_remessa(rs.getString("nome_remetente_nf_remessa"));
						recebido.setNome_destinatario_nf_remessa(rs.getString("nome_destinatario_nf_remessa"));
						recebido.setCaminho_nf_remessa(rs.getString("caminho_nf_remessa"));

						CadastroContrato contrato_recebimento = new CadastroContrato();
						contrato_recebimento.setId(rs.getInt("contrato_id"));
						contrato_recebimento.setCodigo(rs.getString("codigo"));
						contrato_recebimento.setQuantidade(rs.getDouble("quantidade"));
						contrato_recebimento.setMedida(rs.getString("medida"));
						contrato_recebimento.setValor_a_pagar(new BigDecimal(rs.getString("valor_a_pagar")));
						contrato_recebimento.setValor_produto(rs.getDouble("valor_produto"));
						contrato_recebimento.setNomes_compradores(rs.getString("compradores"));
						contrato_recebimento.setNomes_vendedores(rs.getString("vendedores"));

						CadastroSafra safra = new CadastroSafra();
						safra.setId_safra(rs.getInt("id_safra"));
						safra.setAno_colheita(rs.getInt("ano_colheita"));
						safra.setAno_plantio(rs.getInt("ano_plantio"));

						CadastroProduto produto = new CadastroProduto();
						produto.setId_produto(rs.getInt("id_produto"));
						produto.setNome_produto(rs.getString("nome_produto"));
						produto.setTransgenia(rs.getString("transgenia"));

						safra.setProduto(produto);

						contrato_recebimento.setModelo_produto(produto);
						contrato_recebimento.setModelo_safra(safra);

						recebido.setContrato(contrato_recebimento);

						id = recebido.getId_recebimento();

						lista_recebimentos.add(recebido);
					}
				}
			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			System.out.println("Recebimentos foram listadas com sucesso!");
			return lista_recebimentos;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar os recebimentos" + " erro: " + e.getMessage()
					+ " causa: " + e.getCause() + "\n Ultimo id processado: " + id);
			return null;
		}

	}

	
	public ArrayList<CarregamentoCompleto> getCarregamentos() {
		int id = 0;
		String selectCarregamentos = "select ca.*,\n"
				+ "ct.id as contrato_id, ct.codigo, ct.quantidade, ct.medida, ct.valor_a_pagar, ct.valor_produto,\n"
				+ "GROUP_CONCAT(distinct case\n"
				+ "when comprador.tipo_cliente = '0' then comprador.nome_empresarial \n"
				+ " when comprador.tipo_cliente = '1' then comprador.nome_fantasia end\n"
				+ " separator ',') as compradores, GROUP_CONCAT(distinct case\n"
				+ "when vendedor.tipo_cliente = '0' then vendedor.nome_empresarial \n"
				+ " when vendedor.tipo_cliente = '1' then vendedor.nome_fantasia end\n"
				+ " separator ',') as vendedores, sf.id_safra, sf.ano_colheita, sf.ano_plantio,\n"
				+ " pd.id_produto, pd.nome_produto, pd.transgenia  from contrato_carregamentos cc\n"
				+ "LEFT JOIN carregamento ca  on ca.id_carregamento = cc.id_carregamento\n"
				+ "LEFT JOIN contrato ct on ct.id = cc.id_contrato\n"
				+ "LEFT join safra sf on sf.id_safra = ct.id_safra\n"
				+ "LEFT join produto pd on pd.id_produto = sf.id_produto\n"
				+ "LEFT JOIN contrato_comprador on contrato_comprador.id_contrato = ct.id\n"
				+ "LEFT JOIN cliente comprador on comprador.id_cliente = contrato_comprador.id_cliente\n"
				+ "LEFT JOIN contrato_vendedor on contrato_vendedor.id_contrato = ct.id\n"
				+ "LEFT JOIN cliente vendedor on vendedor.id_cliente = contrato_vendedor.id_cliente\n"
				+ "group by ca.id_carregamento";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CarregamentoCompleto> lista_carregamentos = new ArrayList<>();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectCarregamentos);

			rs = pstm.executeQuery();

			while (rs.next()) {

				if (rs != null) {
					if (rs.getInt("id_carregamento") > 0) {

						CarregamentoCompleto carregamento = new CarregamentoCompleto();

						carregamento.setId_carregamento(rs.getInt("id_carregamento"));
						carregamento.setData(rs.getString("data_carregamento"));

						//carregamento.setData(rs.getDate("data_carregamento").toString());
						carregamento.setId_contrato(rs.getInt("id_contrato_carregamento"));
						carregamento.setId_cliente(rs.getInt("id_cliente"));
						carregamento.setId_vendedor(rs.getInt("id_vendedor"));
						carregamento.setId_transportador(rs.getInt("id_transportador"));
						carregamento.setId_veiculo(rs.getInt("id_veiculo"));
						carregamento.setId_produto(rs.getInt("id_produto"));

						carregamento.setCodigo_romaneio(rs.getString("codigo_romaneio"));
						carregamento.setPeso_romaneio(rs.getDouble("peso_romaneio"));
						carregamento.setCaminho_romaneio(rs.getString("caminho_romaneio"));

						carregamento.setNf_interna_aplicavel(rs.getInt("nf_interna_aplicavel"));
						carregamento.setCodigo_nf_interna(rs.getString("codigo_nf_interna"));
						carregamento.setPeso_nf_interna(rs.getDouble("peso_nf_interna"));
						carregamento.setCaminho_nf_interna(rs.getString("caminho_nf_interna"));

						carregamento.setNf_venda1_aplicavel(rs.getInt("nf_venda1_aplicavel"));
						carregamento.setCodigo_nf_venda1(rs.getString("codigo_nf_venda1"));
						carregamento.setPeso_nf_venda1(rs.getDouble("peso_nf_venda1"));
						carregamento.setValor_nf_venda1(new BigDecimal(rs.getString("valor_nf_venda1")));
						carregamento.setNome_remetente_nf_venda1(rs.getString("nome_remetente_nf_venda1"));
						carregamento.setNome_destinatario_nf_venda1(rs.getString("nome_destinatario_nf_venda1"));
						carregamento.setCaminho_nf_venda1(rs.getString("caminho_nf_venda1"));

						carregamento.setNf_complemento_aplicavel(rs.getInt("nf_complemento_aplicavel"));
						carregamento.setCodigo_nf_complemento(rs.getString("codigo_nf_complemento"));
						carregamento.setPeso_nf_complemento(rs.getDouble("peso_nf_complemento"));
						carregamento.setValor_nf_complemento(new BigDecimal(rs.getString("valor_nf_complemento")));
						carregamento.setNome_remetente_nf_complemento(rs.getString("nome_remetente_nf_complemento"));
						carregamento.setNome_destinatario_nf_complemento(rs.getString("nome_destinatario_nf_complemento"));
						carregamento.setCaminho_nf_complemento(rs.getString("caminho_nf_complemento"));

						carregamento.setObservacao(rs.getString("observacao"));
						
						CadastroContrato contrato_recebimento = new CadastroContrato();
						contrato_recebimento.setId(rs.getInt("contrato_id"));
						contrato_recebimento.setCodigo(rs.getString("codigo"));
						contrato_recebimento.setQuantidade(rs.getDouble("quantidade"));
						contrato_recebimento.setMedida(rs.getString("medida"));
						contrato_recebimento.setValor_a_pagar(new BigDecimal(rs.getString("valor_a_pagar")));
						contrato_recebimento.setValor_produto(rs.getDouble("valor_produto"));
						contrato_recebimento.setNomes_compradores(rs.getString("compradores"));
						contrato_recebimento.setNomes_vendedores(rs.getString("vendedores"));

						CadastroSafra safra = new CadastroSafra();
						safra.setId_safra(rs.getInt("id_safra"));
						safra.setAno_colheita(rs.getInt("ano_colheita"));
						safra.setAno_plantio(rs.getInt("ano_plantio"));

						CadastroProduto produto = new CadastroProduto();
						produto.setId_produto(rs.getInt("id_produto"));
						produto.setNome_produto(rs.getString("nome_produto"));
						produto.setTransgenia(rs.getString("transgenia"));

						safra.setProduto(produto);

						contrato_recebimento.setModelo_produto(produto);
						contrato_recebimento.setModelo_safra(safra);

						carregamento.setContrato(contrato_recebimento);

						id = carregamento.getId_carregamento();

						lista_carregamentos.add(carregamento);
					}
				}
			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			return lista_carregamentos;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar os carregamentos" + " erro: " + e.getMessage()
					+ " causa: " + e.getCause() + "\n Ultimo id processado: " + id);
			return null;
		}

	}
	
	public CadastroContrato.Recebimento procurarDuplicataRecebimento(String codigo) {

		System.out.println("Lista recebimentos foi chamado!");
		String selectRecebimentos = "select * from recebimento where codigo_romaneio = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectRecebimentos);
			pstm.setString(1, codigo);

			rs = pstm.executeQuery();

			rs.next();

			System.out.print("recebimento não e nulo!");

			CadastroContrato.Recebimento recebido = new CadastroContrato.Recebimento();

			recebido.setId_recebimento(rs.getInt("id_recebimento"));
			recebido.setData_recebimento(rs.getString("data_recebimento").toString());
			recebido.setId_contrato_recebimento(rs.getInt("id_contrato_recebimento"));
			recebido.setId_cliente(rs.getInt("id_cliente"));
			recebido.setId_vendedor(rs.getInt("id_vendedor"));
			recebido.setId_transportador(rs.getInt("id_transportador"));
			recebido.setId_veiculo(rs.getInt("id_veiculo"));
			recebido.setCodigo_romaneio(rs.getString("codigo_romaneio"));
			recebido.setPeso_romaneio(rs.getDouble("peso_romaneio"));
			recebido.setCaminho_romaneio(rs.getString("caminho_romaneio"));

			recebido.setNf_venda_aplicavel(rs.getInt("nf_venda_aplicavel"));
			recebido.setCodigo_nf_venda(rs.getString("codigo_nf_venda"));
			recebido.setPeso_nf_venda(rs.getDouble("peso_nf_venda"));
			recebido.setCaminho_nf_venda(rs.getString("caminho_nf_venda"));

			recebido.setNf_remessa_aplicavel(rs.getInt("nf_remessa_aplicavel"));
			recebido.setCodigo_nf_remessa(rs.getString("codigo_nf_remessa"));
			recebido.setCaminho_nf_remessa(rs.getString("caminho_nf_remessa"));

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			System.out.println("Recebimentos foram listadas com sucesso!");
			return recebido;
		} catch (Exception e) {
			// JOptionPane.showMessageDialog(null, "Erro ao listar os recebimentos do
			// contrato: " + id_contrato
			// + " erro: "+ e.getMessage() + "causa: " + e.getCause() );
			return null;
		}

	}
	
	
	public CadastroContrato.Carregamento procurarDuplicataCarregamento(String codigo) {

		System.out.println("Lista recebimentos foi chamado!");
		String selectRecebimentos = "select * from carregamento where codigo_romaneio = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectRecebimentos);
			pstm.setString(1, codigo);

			rs = pstm.executeQuery();

			rs.next();

			System.out.print("carregamento não e nulo!");

			CadastroContrato.Carregamento carregamento = new CadastroContrato.Carregamento();

			carregamento.setId_carregamento(rs.getInt("id_carregamento"));
			carregamento.setData(rs.getString("data_carregamento").toString());
			carregamento.setId_carregamento(rs.getInt("id_contrato_carregamento"));
			carregamento.setId_cliente(rs.getInt("id_cliente"));
			carregamento.setId_vendedor(rs.getInt("id_vendedor"));
			carregamento.setId_transportador(rs.getInt("id_transportador"));
			carregamento.setId_veiculo(rs.getInt("id_veiculo"));
			carregamento.setCodigo_romaneio(rs.getString("codigo_romaneio"));
			carregamento.setPeso_romaneio(rs.getDouble("peso_romaneio"));
			carregamento.setCaminho_romaneio(rs.getString("caminho_romaneio"));

	
			ConexaoBanco.fechaConexao(conn, pstm, rs);
			System.out.println("Carregamentos foram listadas com sucesso!");
			return carregamento;
		} catch (Exception e) {
			// JOptionPane.showMessageDialog(null, "Erro ao listar os recebimentos do
			// contrato: " + id_contrato
			// + " erro: "+ e.getMessage() + "causa: " + e.getCause() );
			return null;
		}

	}

	public CadastroContrato.Carregamento getCarregamento(int id_carregamento) {

		System.out.println("Lista carregamento foi chamado!");
		String selectCarregamentos = "select * from carregamento where id_carregamento = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectCarregamentos);
			pstm.setInt(1, id_carregamento);

			rs = pstm.executeQuery();

			rs.next();
			CadastroContrato.Carregamento carga = new CadastroContrato.Carregamento();

			if (rs != null) {
				System.out.print("carregamento não e nulo!");

				carga.setId_carregamento(rs.getInt("id_carregamento"));
				carga.setData(rs.getDate("data_carregamento").toString());
				carga.setId_contrato(rs.getInt("id_contrato_carregamento"));
				carga.setId_cliente(rs.getInt("id_cliente"));
				carga.setId_vendedor(rs.getInt("id_vendedor"));
				carga.setId_transportador(rs.getInt("id_transportador"));
				carga.setId_veiculo(rs.getInt("id_veiculo"));
				carga.setId_produto(rs.getInt("id_produto"));

				carga.setCodigo_romaneio(rs.getString("codigo_romaneio"));
				carga.setPeso_romaneio(rs.getDouble("peso_romaneio"));
				carga.setCaminho_romaneio(rs.getString("caminho_romaneio"));

				carga.setNf_interna_aplicavel(rs.getInt("nf_interna_aplicavel"));
				carga.setCodigo_nf_interna(rs.getString("codigo_nf_interna"));
				carga.setPeso_nf_interna(rs.getDouble("peso_nf_interna"));
				carga.setCaminho_nf_interna(rs.getString("caminho_nf_interna"));

				carga.setNf_venda1_aplicavel(rs.getInt("nf_venda1_aplicavel"));
				carga.setCodigo_nf_venda1(rs.getString("codigo_nf_venda1"));
				carga.setPeso_nf_venda1(rs.getDouble("peso_nf_venda1"));
				carga.setValor_nf_venda1(new BigDecimal(rs.getString("valor_nf_venda1")));
				carga.setNome_remetente_nf_venda1(rs.getString("nome_remetente_nf_venda1"));
				carga.setNome_destinatario_nf_venda1(rs.getString("nome_destinatario_nf_venda1"));
				carga.setCaminho_nf_venda1(rs.getString("caminho_nf_venda1"));

				carga.setNf_complemento_aplicavel(rs.getInt("nf_complemento_aplicavel"));
				carga.setCodigo_nf_complemento(rs.getString("codigo_nf_complemento"));
				carga.setPeso_nf_complemento(rs.getDouble("peso_nf_complemento"));
				carga.setValor_nf_complemento(new BigDecimal(rs.getString("valor_nf_complemento")));
				carga.setNome_remetente_nf_complemento(rs.getString("nome_remetente_nf_complemento"));
				carga.setNome_destinatario_nf_complemento(rs.getString("nome_destinatario_nf_complemento"));
				carga.setCaminho_nf_complemento(rs.getString("caminho_nf_complemento"));

				carga.setObservacao(rs.getString("observacao"));

			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			System.out.println("Carregamento listado com sucesso!");
			return carga;
		} catch (Exception e) {
		//	JOptionPane.showMessageDialog(null,
			//		"Erro ao listar o carregamento id: " + id_carregamento + " erro: " + "causa: ");
			return null;
		}

	}

	public ArrayList<CadastroContrato.Carregamento> getCarregamentosPorComprador(int id_comprador) {

		System.out.println("Lista carregamento foi chamado!");
		String selectCarregamentos = "select * from carregamento where id_cliente = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroContrato.Carregamento> lista_carregamentos = new ArrayList<>();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectCarregamentos);
			pstm.setInt(1, id_comprador);

			rs = pstm.executeQuery();

			while (rs.next()) {

				if (rs != null) {
					System.out.print("carregamento não e nulo!");

					CadastroContrato.Carregamento carga = new CadastroContrato.Carregamento();
					carga.setId_carregamento(rs.getInt("id_carregamento"));

					carga.setData(rs.getString("data_carregamento").toString());
					carga.setId_contrato(rs.getInt("id_contrato_carregamento"));
					carga.setId_cliente(rs.getInt("id_cliente"));
					carga.setId_vendedor(rs.getInt("id_vendedor"));
					carga.setId_transportador(rs.getInt("id_transportador"));
					carga.setId_veiculo(rs.getInt("id_veiculo"));
					carga.setId_produto(rs.getInt("id_produto"));

					carga.setCodigo_romaneio(rs.getString("codigo_romaneio"));
					carga.setPeso_romaneio(rs.getDouble("peso_romaneio"));
					carga.setCaminho_romaneio(rs.getString("caminho_romaneio"));

					carga.setCodigo_nf_interna(rs.getString("codigo_nf_interna"));
					carga.setPeso_nf_interna(rs.getDouble("peso_nf_interna"));
					carga.setCaminho_nf_interna(rs.getString("caminho_nf_interna"));

					carga.setCodigo_nf_venda1(rs.getString("codigo_nf_venda1"));
					carga.setPeso_nf_venda1(rs.getDouble("peso_nf_venda1"));
					carga.setValor_nf_venda1(new BigDecimal(rs.getString("valor_nf_venda1")));
					carga.setNome_remetente_nf_venda1(rs.getString("nome_remetente_nf_venda1"));
					carga.setNome_destinatario_nf_venda1(rs.getString("nome_destinatario_nf_venda1"));
					carga.setCaminho_nf_venda1(rs.getString("caminho_nf_venda1"));

					carga.setCodigo_nf_complemento(rs.getString("codigo_nf_complemento"));
					carga.setPeso_nf_complemento(rs.getDouble("peso_nf_complemento"));
					carga.setValor_nf_complemento(new BigDecimal(rs.getString("valor_nf_complemento")));
					carga.setNome_remetente_nf_complemento(rs.getString("nome_remetente_nf_complemento"));
					carga.setNome_destinatario_nf_complemento(rs.getString("nome_destinatario_nf_complemento"));

					carga.setCaminho_nf_complemento(rs.getString("caminho_nf_complemento"));

					carga.setObservacao(rs.getString("observacao"));

					carga.setNf_interna_aplicavel(rs.getInt("nf_interna_aplicavel"));

					carga.setNf_venda1_aplicavel(rs.getInt("nf_venda1_aplicavel"));

					carga.setNf_complemento_aplicavel(rs.getInt("nf_complemento_aplicavel"));

					lista_carregamentos.add(carga);

				}
			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			System.out.println("Carregamentos foram listadas com sucesso!");
			return lista_carregamentos;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar os carregamentos do comprador: " + id_comprador
					+ " erro: " + e.getMessage() + "causa: " + e.getCause());
			return null;
		}

	}

	public ArrayList<CadastroContrato.Carregamento> getCarregamentosPorVendedor(int id_vendedor) {

		System.out.println("Lista carregamento foi chamado!");
		String selectCarregamentos = "select * from carregamento where id_vendedor = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroContrato.Carregamento> lista_carregamentos = new ArrayList<>();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectCarregamentos);
			pstm.setInt(1, id_vendedor);

			rs = pstm.executeQuery();

			while (rs.next()) {

				if (rs != null) {
					System.out.print("carregamento não e nulo!");

					CadastroContrato.Carregamento carga = new CadastroContrato.Carregamento();

					carga.setId_carregamento(rs.getInt("id_carregamento"));
					carga.setData(rs.getString("data_carregamento"));
					carga.setId_contrato(rs.getInt("id_contrato_carregamento"));
					carga.setId_cliente(rs.getInt("id_cliente"));
					carga.setId_vendedor(rs.getInt("id_vendedor"));
					carga.setId_transportador(rs.getInt("id_transportador"));
					carga.setId_veiculo(rs.getInt("id_veiculo"));
					carga.setId_produto(rs.getInt("id_produto"));

					carga.setCodigo_romaneio(rs.getString("codigo_romaneio"));
					carga.setPeso_romaneio(rs.getDouble("peso_romaneio"));
					carga.setCaminho_romaneio(rs.getString("caminho_romaneio"));

					carga.setCodigo_nf_interna(rs.getString("codigo_nf_interna"));
					carga.setPeso_nf_interna(rs.getDouble("peso_nf_interna"));
					carga.setCaminho_nf_interna(rs.getString("caminho_nf_interna"));

					carga.setCodigo_nf_venda1(rs.getString("codigo_nf_venda1"));
					carga.setPeso_nf_venda1(rs.getDouble("peso_nf_venda1"));
					carga.setValor_nf_venda1(new BigDecimal(rs.getString("valor_nf_venda1")));
					carga.setNome_remetente_nf_venda1(rs.getString("nome_remetente_nf_venda1"));
					carga.setNome_destinatario_nf_venda1(rs.getString("nome_destinatario_nf_venda1"));
					carga.setCaminho_nf_venda1(rs.getString("caminho_nf_venda1"));

					carga.setCodigo_nf_complemento(rs.getString("codigo_nf_complemento"));
					carga.setPeso_nf_complemento(rs.getDouble("peso_nf_complemento"));
					carga.setValor_nf_complemento(new BigDecimal(rs.getString("valor_nf_complemento")));
					carga.setNome_remetente_nf_complemento(rs.getString("nome_remetente_nf_complemento"));
					carga.setNome_destinatario_nf_complemento(rs.getString("nome_destinatario_nf_complemento"));
					carga.setCaminho_nf_complemento(rs.getString("caminho_nf_complemento"));

					carga.setObservacao(rs.getString("observacao"));

					carga.setNf_interna_aplicavel(rs.getInt("nf_interna_aplicavel"));

					carga.setNf_venda1_aplicavel(rs.getInt("nf_venda1_aplicavel"));

					carga.setNf_complemento_aplicavel(rs.getInt("nf_complemento_aplicavel"));

					lista_carregamentos.add(carga);

				}
			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			System.out.println("Carregamentos  para vendedor foram listadas com sucesso!");
			return lista_carregamentos;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar os carregamentos do vendedor: " + id_vendedor
					+ " erro: " + e.getMessage() + "causa: " + e.getCause());
			return null;
		} finally {
			/*
			 * This block should be added to your code You need to release the resources
			 * like connections
			 */
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	}

	public ArrayList<CadastroContrato.Carregamento> getCarregamentosDirecionados(int id_comprador, int id_vendedor) {

		System.out.println("Lista carregamento foi chamado!");
		String selectCarregamentos = "select * from carregamento where id_cliente = ? and id_vendedor";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroContrato.Carregamento> lista_carregamentos = new ArrayList<>();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectCarregamentos);
			pstm.setInt(1, id_comprador);
			pstm.setInt(2, id_vendedor);

			rs = pstm.executeQuery();

			while (rs.next()) {

				if (rs != null) {
					System.out.print("carregamento não e nulo!");

					CadastroContrato.Carregamento carga = new CadastroContrato.Carregamento();

					carga.setId_carregamento(rs.getInt("id_carregamento"));
					carga.setData(rs.getDate("data_carregamento").toString());
					carga.setId_contrato(rs.getInt("id_contrato_carregamento"));
					carga.setId_cliente(rs.getInt("id_cliente"));
					carga.setId_vendedor(rs.getInt("id_vendedor"));
					carga.setId_transportador(rs.getInt("id_transportador"));
					carga.setId_veiculo(rs.getInt("id_veiculo"));
					carga.setId_produto(rs.getInt("id_produto"));

					carga.setCodigo_romaneio(rs.getString("codigo_romaneio"));
					carga.setPeso_romaneio(rs.getDouble("peso_romaneio"));
					carga.setCaminho_romaneio(rs.getString("caminho_romaneio"));

					carga.setCodigo_nf_interna(rs.getString("codigo_nf_interna"));
					carga.setPeso_nf_interna(rs.getDouble("peso_nf_interna"));
					carga.setCaminho_nf_interna(rs.getString("caminho_nf_interna"));

					carga.setCodigo_nf_venda1(rs.getString("codigo_nf_venda1"));
					carga.setPeso_nf_venda1(rs.getDouble("peso_nf_venda1"));
					carga.setValor_nf_venda1(new BigDecimal(rs.getString("valor_nf_venda1")));
					carga.setCaminho_nf_venda1(rs.getString("caminho_nf_venda1"));

					carga.setCodigo_nf_complemento(rs.getString("codigo_nf_complemento"));
					carga.setPeso_nf_complemento(rs.getDouble("peso_nf_complemento"));
					carga.setValor_nf_complemento(new BigDecimal(rs.getString("valor_nf_complemento")));

					carga.setCaminho_nf_complemento(rs.getString("caminho_nf_complemento"));

					carga.setObservacao(rs.getString("observacao"));
					carga.setNf_interna_aplicavel(rs.getInt("nf_interna_aplicavel"));

					carga.setNf_venda1_aplicavel(rs.getInt("nf_venda1_aplicavel"));

					carga.setNf_complemento_aplicavel(rs.getInt("nf_complemento_aplicavel"));

					carga.setNome_remetente_nf_complemento(rs.getString("nome_remetente_nf_complemento"));
					carga.setNome_destinatario_nf_complemento(rs.getString("nome_destinatario_nf_complemento"));
					carga.setNome_remetente_nf_venda1(rs.getString("nome_remetente_nf_venda1"));
					carga.setNome_destinatario_nf_venda1(rs.getString("nome_destinatario_nf_venda1"));
					lista_carregamentos.add(carga);

				}
			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			System.out.println("Carregamentos foram listadas com sucesso!");
			return lista_carregamentos;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao listar os carregamentos do vendedor: " + id_vendedor + " erro: " + "causa: ");
			return null;
		}

	}

	public boolean removerCarregamento(int id_contrato, int id_carregamento) {

		return remover_carregamento(id_carregamento) && remover_contrato_carregamento(id_contrato, id_carregamento);

	}

	public boolean removerRecebimento(int id_contrato, int id_recebimento) {

		return remover_recebimento(id_recebimento) && remover_contrato_recebimento(id_contrato, id_recebimento);

	}

	private boolean remover_recebimento(int id_recebimento) {

		String sql_delete_recebimento = "DELETE FROM recebimento WHERE id_recebimento = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_delete_recebimento);

			pstm.setInt(1, id_recebimento);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			// JOptionPane.showMessageDialog(null, "Recebimento excluido, banco normalizado
			// ");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao excluir o recebimento do banco de" + "dados " + f.getMessage());
			return false;
		}

	}

	private boolean remover_contrato_recebimento(int id_contrato, int id_recebimento) {

		String sql_delete_contrato_recebimento = "DELETE FROM contrato_recebimentos WHERE id_contrato = ? and id_recebimento = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_delete_contrato_recebimento);

			pstm.setInt(1, id_contrato);
			pstm.setInt(2, id_recebimento);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			// JOptionPane.showMessageDialog(null, "Relacao contrato_recebimento excluida,
			// banco normalizado ");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao excluir a relacao contrato_recebimento do banco de" + "dados " + f.getMessage());
			return false;
		}

	}

	public boolean atualizar_recebimento(CadastroContrato.Recebimento recebimento) {
		/*
		 * id_recebimento int(10) not null auto_increment, data_recebimento varchar(40),
		 * id_contrato_recebimento int(10), id_cliente int(10), id_vendedor int(10),
		 * id_transportador int(10), id_veiculo int(10), codigo_romaneio varchar(100),
		 * peso_romaneio double, caminho_romaneio text, codigo_nf_venda varchar (40),
		 * peso_nf_venda double, caminho_nf_venda text, codigo_nf_remessa varchar(40),
		 * peso_nf_remessa double, caminho_nf_remessa text,
		 */
		String sql_atualizar_recebimento = "update recebimento set data_recebimento = ?, id_cliente = ?, id_vendedor = ?, id_transportador = ?, id_veiculo = ?, codigo_romaneio = ?, peso_romaneio = ?, caminho_romaneio = ?, nf_venda_aplicavel = ?, codigo_nf_venda = ?, peso_nf_venda = ?, valor_nf_venda = ?, nome_remetente_nf_venda = ?,nome_destinatario_nf_venda = ?, caminho_nf_venda = ?, nf_remessa_aplicavel = ?, codigo_nf_remessa = ?, peso_nf_remessa = ?, valor_nf_remessa = ?,  nome_remetente_nf_remessa = ?,nome_destinatario_nf_remessa = ?,caminho_nf_remessa = ? where id_recebimento = ?";

		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_atualizar_recebimento);

			pstm.setString(1, recebimento.getData_recebimento());
			pstm.setInt(2, recebimento.getId_cliente());
			pstm.setInt(3, recebimento.getId_vendedor());
			pstm.setInt(4, recebimento.getId_transportador());
			pstm.setInt(5, recebimento.getId_veiculo());
			pstm.setString(6, recebimento.getCodigo_romaneio());
			pstm.setDouble(7, recebimento.getPeso_romaneio());
			pstm.setString(8, recebimento.getCaminho_romaneio());

			pstm.setInt(9, recebimento.getNf_venda_aplicavel());

			pstm.setString(10, recebimento.getCodigo_nf_venda());
			pstm.setDouble(11, recebimento.getPeso_nf_venda());
			pstm.setString(12, recebimento.getValor_nf_venda().toString());
			pstm.setString(13, recebimento.getNome_remetente_nf_venda().toString());
			pstm.setString(14, recebimento.getNome_destinatario_nf_venda().toString());

			pstm.setString(15, recebimento.getCaminho_nf_venda());

			pstm.setInt(16, recebimento.getNf_remessa_aplicavel());
			pstm.setString(17, recebimento.getCodigo_nf_remessa());
			pstm.setDouble(18, recebimento.getPeso_nf_remessa());
			pstm.setString(19, recebimento.getValor_nf_remessa().toString());
			pstm.setString(20, recebimento.getNome_remetente_nf_remessa().toString());
			pstm.setString(21, recebimento.getNome_destinatario_nf_remessa().toString());

			pstm.setString(22, recebimento.getCaminho_nf_remessa());

			pstm.setInt(23, recebimento.getId_recebimento());

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			// JOptionPane.showMessageDialog(null, "Recebimento Atualizado");
			return true;

		} catch (Exception f) {
			// JOptionPane.showMessageDialog(null,
			// "Erro ao atualizar o recebimento no banco de" + "dados " + f.getMessage());
			return false;
		}

	}

	public boolean atualizar_carregamento(CadastroContrato.Carregamento carregamento) {
		/*
		 * id_recebimento int(10) not null auto_increment, data_recebimento varchar(40),
		 * id_contrato_recebimento int(10), id_cliente int(10), id_vendedor int(10),
		 * id_transportador int(10), id_veiculo int(10), codigo_romaneio varchar(100),
		 * peso_romaneio double, caminho_romaneio text, codigo_nf_venda varchar (40),
		 * peso_nf_venda double, caminho_nf_venda text, codigo_nf_remessa varchar(40),
		 * peso_nf_remessa double, caminho_nf_remessa text,
		 */
		String sql_atualizar_carregamento = "update carregamento set data_carregamento = ?, id_cliente = ?, id_vendedor = ?, id_transportador = ?,\n"
				+ "id_veiculo = ?, codigo_romaneio = ?, peso_romaneio = ?, caminho_romaneio = ?, nf_venda1_aplicavel = ?, codigo_nf_venda1 = ?, peso_nf_venda1 = ?, valor_nf_venda1 = ?,\n"
				+ "nome_remetente_nf_venda1 = ?,nome_destinatario_nf_venda1 = ?,caminho_nf_venda1 = ?, nf_complemento_aplicavel = ?, codigo_nf_complemento = ?, peso_nf_complemento = ?, valor_nf_complemento = ?, nome_remetente_nf_complemento = ? , nome_destinatario_nf_complemento = ?, caminho_nf_complemento = ?, nf_interna_aplicavel = ?, codigo_nf_interna = ?,\n"
				+ "peso_nf_interna = ?, caminho_nf_interna = ?, observacao = ? where id_carregamento = ?;";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_atualizar_carregamento);

			pstm.setString(1, carregamento.getData());
			pstm.setInt(2, carregamento.getId_cliente());
			pstm.setInt(3, carregamento.getId_vendedor());
			pstm.setInt(4, carregamento.getId_transportador());
			pstm.setInt(5, carregamento.getId_veiculo());
			pstm.setString(6, carregamento.getCodigo_romaneio());
			pstm.setDouble(7, carregamento.getPeso_romaneio());
			pstm.setString(8, carregamento.getCaminho_romaneio());

			pstm.setInt(9, carregamento.getNf_venda1_aplicavel());
			pstm.setString(10, carregamento.getCodigo_nf_venda1());
			pstm.setDouble(11, carregamento.getPeso_nf_venda1());
			pstm.setString(12, carregamento.getValor_nf_venda1().toPlainString());
			pstm.setString(13, carregamento.getNome_remetente_nf_venda1());
			pstm.setString(14, carregamento.getNome_destinatario_nf_venda1());

			pstm.setString(15, carregamento.getCaminho_nf_venda1());

			pstm.setInt(16, carregamento.getNf_complemento_aplicavel());
			pstm.setString(17, carregamento.getCodigo_nf_complemento());
			pstm.setDouble(18, carregamento.getPeso_nf_complemento());
			pstm.setString(19, carregamento.getValor_nf_complemento().toPlainString());
			pstm.setString(20, carregamento.getNome_remetente_nf_complemento());
			pstm.setString(21, carregamento.getNome_destinatario_nf_complemento());
			pstm.setString(22, carregamento.getCaminho_nf_complemento());

			pstm.setInt(23, carregamento.getNf_interna_aplicavel());
			pstm.setString(24, carregamento.getCodigo_nf_interna());
			pstm.setDouble(25, carregamento.getPeso_nf_interna());
			pstm.setString(26, carregamento.getCaminho_nf_interna());

			pstm.setString(27, carregamento.getObservacao());

			pstm.setInt(28, carregamento.getId_carregamento());

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			// JOptionPane.showMessageDialog(null, "Recebimento Atualizado");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao atualizar o recebimento no banco de" + "dados " + f.getMessage());
			return false;
		}

	}

	private boolean remover_carregamento(int id_carregamento) {

		String sql_delete_carregamento = "DELETE FROM carregamento WHERE id_carregamento = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_delete_carregamento);

			pstm.setInt(1, id_carregamento);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			JOptionPane.showMessageDialog(null, "Carregamento excluido, banco normalizado ");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao excluir o carregamento do banco de" + "dados " + f.getMessage());
			return false;
		}

	}

	private boolean remover_contrato_carregamento(int id_contrato, int id_carregamento) {

		String sql_delete_contrato_carregamento = "DELETE FROM contrato_carregamentos WHERE id_contrato = ? and id_carregamento = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_delete_contrato_carregamento);

			pstm.setInt(1, id_contrato);
			pstm.setInt(2, id_carregamento);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			JOptionPane.showMessageDialog(null, "Relacao contrato_carregamento excluida, banco normalizado ");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao excluir a relacao contrato_carregamento do banco de" + "dados " + f.getMessage());
			return false;
		}

	}

	public String sql_carregamento(CadastroContrato.Carregamento carregamento) {

		String query = "insert into carregamento (data_carregamento, id_contrato_carregamento, id_cliente, id_vendedor , id_transportador, id_veiculo, id_produto, codigo_romaneio , peso_romaneio ,caminho_romaneio , nf_venda1_aplicavel, codigo_nf_venda1 ,peso_nf_venda1 , valor_nf_venda1,nome_remetente_nf_venda1, nome_destinatario_nf_venda1, caminho_nf_venda1, nf_complemento_aplicavel, codigo_nf_complemento ,peso_nf_complemento, valor_nf_complemento,nome_remetente_nf_complemento, nome_destinatario_nf_complemento,caminho_nf_complemento , nf_interna_aplicavel,codigo_nf_interna ,peso_nf_interna ,caminho_nf_interna ,observacao ) values ('"
				+ carregamento.getData() + "','" + carregamento.getId_contrato() + "','" + carregamento.getId_cliente()
				+ "','" + carregamento.getId_vendedor() + "','" + carregamento.getId_transportador() + "','"
				+ carregamento.getId_veiculo() + "','" + carregamento.getId_produto() + "','"
				+ carregamento.getCodigo_romaneio() + "','" + carregamento.getPeso_romaneio() + "','"
				+ carregamento.getCaminho_romaneio() + "','" + carregamento.getNf_venda1_aplicavel() + "','"
				+ carregamento.getCodigo_nf_venda1() + "','" + carregamento.getPeso_nf_venda1() + "','"
				+ carregamento.getValor_nf_venda1() + "','" + carregamento.getNome_remetente_nf_venda1() + "','"
				+ carregamento.getNome_destinatario_nf_venda1() + "','" + carregamento.getCaminho_nf_venda1() + "','"

				+ carregamento.getNf_complemento_aplicavel() + "','" + carregamento.getCodigo_nf_complemento() + "','"
				+ carregamento.getPeso_nf_complemento() + "','" + carregamento.getValor_nf_complemento() + "','"
				+ carregamento.getNome_remetente_nf_complemento() + "','"
				+ carregamento.getNome_destinatario_nf_complemento() + "','" + carregamento.getCaminho_nf_complemento()
				+ "','"

				+ carregamento.getNf_interna_aplicavel() + "','"

				+ carregamento.getCodigo_nf_interna() + "','" + carregamento.getPeso_nf_interna() + "','"
				+ carregamento.getCaminho_nf_interna() + "','"

				+ carregamento.getObservacao()

				+ "')";
		return query;

	}

	public String sql_recebimento(Recebimento recebimento) {

		String query = "insert into recebimento (data_recebimento, id_contrato_recebimento, id_cliente, id_vendedor , id_transportador, id_veiculo, codigo_romaneio, peso_romaneio, caminho_romaneio, nf_venda_aplicavel, codigo_nf_venda, peso_nf_venda, valor_nf_venda, nome_remetente_nf_venda, nome_destinatario_nf_venda, caminho_nf_venda, nf_remessa_aplicavel, codigo_nf_remessa ,peso_nf_remessa, valor_nf_remessa,nome_remetente_nf_remessa, nome_destinatario_nf_remessa ,caminho_nf_remessa) values ('"
				+ recebimento.getData_recebimento() + "','" + recebimento.getId_contrato_recebimento() + "','"
				+ recebimento.getId_cliente() + "','" + recebimento.getId_vendedor() + "','"
				+ recebimento.getId_transportador() + "','" + recebimento.getId_veiculo() + "','"
				+ recebimento.getCodigo_romaneio() + "','" + recebimento.getPeso_romaneio() + "','"
				+ recebimento.getCaminho_romaneio() + "','" + recebimento.getNf_venda_aplicavel() + "','"

				+ recebimento.getCodigo_nf_venda() + "','" + recebimento.getPeso_nf_venda() + "','"
				+ recebimento.getValor_nf_venda() + "','" + recebimento.getNome_remetente_nf_venda() + "','"
				+ recebimento.getNome_destinatario_nf_venda() + "','" + recebimento.getCaminho_nf_venda() + "','"

				+ recebimento.getNf_remessa_aplicavel() + "','"

				+ recebimento.getCodigo_nf_remessa() + "','" + recebimento.getPeso_nf_remessa() + "','"
				+ recebimento.getValor_nf_remessa() + "','" + recebimento.getNome_remetente_nf_remessa() + "','"
				+ recebimento.getNome_destinatario_nf_remessa() + "','"

				+ recebimento.getCaminho_nf_remessa()

				+ "')";
		return query;

	}

	public boolean inserirPagamento(int id_contrato, CadastroContrato.CadastroPagamentoContratual pagamento) {

		// inserir primeiro o carregamento
		int retorno_inserir_pagamento = inserir_pagamento_contratual_retorno(pagamento);
		if (retorno_inserir_pagamento > 0) {
			// inserir nova relacao contrato_pagamentos
			boolean inserir_relacao_contrato_pagamentos = inserir_contrato_pagamento_contratual(id_contrato,
					retorno_inserir_pagamento);
			if (inserir_relacao_contrato_pagamentos) {
				System.out.println("Pagamento Cadastrado");
				return true;

			} else {
				System.out.println("Erro ao inserir um novo pagamento!");
				return false;

			}

		} else {
			System.out.println("Erro ao inserir um novo pagamento!");
			return false;
		}

	}

	public boolean atualizarPagamentoContratual(int id_contrato, CadastroContrato.CadastroPagamentoContratual dado) {

		if (dado != null) {
			try {
				Connection conn = null;
				String atualizar = null;
				PreparedStatement pstm;

				// atualizar = "update financeiro_conta set nome_conta = ?, id_grupo_contas = ?,
				// tipo_conta = ?, observacao = ?,descricao = ? where id_conta = ? ";
				atualizar = "update pagamento set data_pagamento = ? , valor = ?, id_depositante = ?, id_conta_depositante = ?,"
						+ " id_favorecido = ?, id_conta_favorecido = ?,descricao = ?, tipo = ? where id_pagamento = ?";

				conn = ConexaoBanco.getConexao();
				pstm = conn.prepareStatement(atualizar);

				pstm.setString(1, dado.getData_pagamento());
				pstm.setDouble(2, dado.getValor_pagamento());
				pstm.setInt(3, dado.getId_depositante());

				pstm.setInt(4, dado.getId_conta_depositante());
				pstm.setInt(5, dado.getId_favorecido());
				pstm.setInt(6, dado.getId_conta_favorecido());
				pstm.setString(7, dado.getDescricao());
				pstm.setInt(8, dado.getTipo());

				pstm.setInt(9, dado.getId_pagamento());

				pstm.execute();
				// JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
				System.out.println("Pagamento Contratual atualizado com sucesso");
				ConexaoBanco.fechaConexao(conn);
				return true;
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao atualizar o Pagamento Contratual no banco de dados\nErro: "
						+ e.getMessage() + "\nCausa: " + e.getCause());
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(null, "Os parametros estão vazios");
			return false;
		}

	}

	public boolean atualizarTransferenciaContratual(int id_contrato,
			CadastroContrato.CadastroPagamentoContratual dado) {

		if (dado != null) {
			try {
				Connection conn = null;
				String atualizar = null;
				PreparedStatement pstm;

				// atualizar = "update financeiro_conta set nome_conta = ?, id_grupo_contas = ?,
				// tipo_conta = ?, observacao = ?,descricao = ? where id_conta = ? ";
				atualizar = "update pagamento set data_pagamento = ? , valor = ?, id_depositante = ?, id_conta_depositante = ?,"
						+ " id_favorecido = ?, id_conta_favorecido = ?,descricao = ?, tipo = ?, id_contrato_destinatario = ?, id_contrato_remetente = ? where id_pagamento = ?";

				conn = ConexaoBanco.getConexao();
				pstm = conn.prepareStatement(atualizar);

				pstm.setString(1, dado.getData_pagamento());
				pstm.setDouble(2, dado.getValor_pagamento());
				pstm.setInt(3, dado.getId_depositante());

				pstm.setInt(4, dado.getId_conta_depositante());
				pstm.setInt(5, dado.getId_favorecido());
				pstm.setInt(6, dado.getId_conta_favorecido());
				pstm.setString(7, dado.getDescricao());
				pstm.setInt(8, dado.getTipo());
				pstm.setInt(9, dado.getId_contrato_destinatario());
				pstm.setInt(10, dado.getId_contrato_remetente());

				pstm.setInt(11, dado.getId_pagamento());

				pstm.execute();
				// JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
				System.out.println("Transferencia de Pagamento Contratual atualizado com sucesso");
				ConexaoBanco.fechaConexao(conn);
				return true;
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Erro ao atualizar a Transferencia de Pagamento Contratual no banco de dados\nErro: "
								+ e.getMessage() + "\nCausa: " + e.getCause());
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(null, "Os parametros estão vazios");
			return false;
		}

	}

	public int inserir_pagamento_contratual_retorno(CadastroContrato.CadastroPagamentoContratual pagamento) {

		int result = -1;
		if (pagamento != null) {
			Connection conn = null;
			try {

				conn = ConexaoBanco.getConexao();

				String query = sql_pagamento_contratual(pagamento);
				Statement stmt = (Statement) conn.createStatement();
				int numero = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					result = rs.getInt(1);
					System.out.println("Id pagamento inserido: " + result);
				}
				rs.close();
				stmt.close();

				return result;

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao inserir o pagamento no banco de " + "dados ");
				GerenciadorLog.registrarLogDiario("falha", "falha ao adicionar pagamento contratual: " + " causa: ");
				return -1;
			}
		} else {
			System.out.println("O pagamento enviado por parametro esta vazio");
			return -1;
		}

	}

	public boolean inserir_contrato_pagamento_contratual(int id_contrato, int id_pagamento) {
		Connection conn = null;
		try {
			conn = ConexaoBanco.getConexao();
			String sql = "insert into contrato_pagamentos\r\n" + "(id_contrato, id_pagamento) values ('" + id_contrato
					+ "','" + id_pagamento + "')";

			PreparedStatement grava = (PreparedStatement) conn.prepareStatement(sql);
			grava.execute();
			ConexaoBanco.fechaConexao(conn, grava);
			// JOptionPane.showMessageDialog(null, "Relação contrato_pagamentos cadastrado
			// com sucesso");

			return true;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao inserir a relação contrato_carregamento no banco de dados ");
			return false;
		}

	}

	public ArrayList<CadastroContrato.CadastroPagamentoContratual> getPagamentosContratuais(int id_contrato) {

		System.out.println("Listar pagamentos foi chamado!");
		String selectPagamentos = "select * from contrato_pagamentos\r\n"
				+ "LEFT JOIN pagamento  on pagamento.id_pagamento = contrato_pagamentos.id_pagamento \r\n"
				+ "where contrato_pagamentos.id_contrato = ?;";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroContrato.CadastroPagamentoContratual> lista_pagamentos = new ArrayList<>();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectPagamentos);
			pstm.setInt(1, id_contrato);

			rs = pstm.executeQuery();

			while (rs.next()) {

				if (rs != null) {
					System.out.print("pagamento não e nulo!");

					CadastroContrato.CadastroPagamentoContratual pagamento = new CadastroContrato.CadastroPagamentoContratual();

					pagamento.setTipo(rs.getInt("tipo"));
					pagamento.setDescricao(rs.getString("descricao"));
					pagamento.setId_pagamento(rs.getInt("id_pagamento"));
					pagamento.setData_pagamento(rs.getString("data_pagamento"));
					pagamento.setValor_pagamento(rs.getDouble("valor"));
					pagamento.setId_depositante(rs.getInt("id_depositante"));
					pagamento.setId_conta_depositante(rs.getInt("id_conta_depositante"));
					pagamento.setId_favorecido(rs.getInt("id_favorecido"));
					pagamento.setId_conta_favorecido(rs.getInt("id_conta_favorecido"));

					lista_pagamentos.add(pagamento);

				}
			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			System.out.println("Pagamentos foram listadas com sucesso!");
			return lista_pagamentos;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao listar os pagamentos do contrato: " + id_contrato + " erro: " + "causa: ");
			return null;
		}

	}

	public ArrayList<PagamentoCompleto> getPagamentosContratuaisParaRelatorio() {

		System.out.println("Listar pagamentos foi chamado!");
		/*String selectPagamentos = "select ct_pg.id_contrato, ct_pg.id_pagamento,\n"
				+ "ct.id as contrato_id,ct.codigo, ct.quantidade, ct.medida, ct.valor_a_pagar, ct.valor_produto, ct.grupo_particular,\n"
				+ "GROUP_CONCAT(distinct\n" + "case\n"
				+ "when comprador.tipo_cliente = '0' then comprador.nome_empresarial \n"
				+ " when comprador.tipo_cliente = '1' then comprador.nome_fantasia\n" + "end\n"
				+ " separator ',') as compradores,\n" + "GROUP_CONCAT(distinct\n" + "case\n"
				+ "when vendedor.tipo_cliente = '0' then vendedor.nome_empresarial \n"
				+ " when vendedor.tipo_cliente = '1' then vendedor.nome_fantasia\n" + "end\n"
				+ " separator ',') as vendedores, \n"
				+ " pg.id_pagamento, pg.tipo, pg.descricao, pg.data_pagamento, pg.valor,\n"
				+ " pg.id_conta_depositante,\n" + " (\n" + " SELECT \n" + "  CONCAT(\n" + "    (nome),' Banco: ',\n"
				+ "    (banco),' Agência: ',\n" + "    (agencia),' Conta: ',\n" + "    (conta)\n"
				+ "  ) as conta_bancaria\n" + "from conta_bancaria where id_conta = id_conta_depositante\n"
				+ " ) as conta_bancaria_depositante,\n" + " pg.id_conta_favorecido,\n" + "  (\n" + " SELECT \n"
				+ "  CONCAT(\n" + "    (nome),' Banco: ',\n" + "    (banco),' Agência: ',\n"
				+ "    (agencia),' Conta: ',\n" + "    (conta)\n" + "  ) as conta_bancaria\n"
				+ "from conta_bancaria where id_conta = id_conta_favorecido\n" + " ) as conta_bancaria_favorecido,\n"
				+ " pg.id_contrato_remetente, pg.id_contrato_destinatario,\n" + " GROUP_CONCAT(distinct\n" + "case\n"
				+ "when depositante.tipo_cliente = '0' then depositante.nome_empresarial \n"
				+ " when depositante.tipo_cliente = '1' then depositante.nome_fantasia\n" + "end\n"
				+ " separator ',') as depositante,\n" + "  GROUP_CONCAT(distinct\n" + "case\n"
				+ "when favorecido.tipo_cliente = '0' then favorecido.nome_empresarial \n"
				+ " when favorecido.tipo_cliente = '1' then favorecido.nome_fantasia\n" + "end\n"
				+ " separator ',') as favorecido,\n" + " ct.codigo as contrato_remetente,\n"
				+ "ct_destinatario.codigo as contrato_destinatario,\n"
				+ " sf.id_safra, sf.ano_colheita, sf.ano_plantio,\n"
				+ " pd.id_produto, pd.nome_produto, pd.transgenia \n" + " from contrato_pagamentos ct_pg\n"
				+ "left join pagamento pg on pg.id_pagamento = ct_pg.id_pagamento\n"
				+ "left join contrato ct on ct.id = ct_pg.id_contrato \n"
				+ "left join contrato ct_destinatario on ct_destinatario.id = pg.id_contrato_destinatario \n"
				+ "LEFT JOIN contrato_comprador on contrato_comprador.id_contrato = ct.id\n"
				+ "LEFT JOIN cliente comprador on comprador.id_cliente = contrato_comprador.id_cliente\n"
				+ "LEFT JOIN contrato_vendedor on contrato_vendedor.id_contrato = ct.id\n"
				+ "LEFT JOIN cliente vendedor on vendedor.id_cliente = contrato_vendedor.id_cliente\n"
				+ "LEFT JOIN cliente depositante on depositante.id_cliente = id_depositante\n"
				+ "LEFT JOIN cliente favorecido on favorecido.id_cliente = id_favorecido\n"
				+ "LEFT join safra sf on sf.id_safra = ct.id_safra\n"
				+ "LEFT join produto pd on pd.id_produto = sf.id_produto \n"
				+ "left join conta_bancaria cb_depositante on cb_depositante.id_conta = id_conta_depositante\n"
				+ "left join conta_bancaria cb_favorecido on cb_favorecido.id_conta = id_conta_favorecido\n" + "\n"
				+ "group by  pg.id_pagamento";
				*/
		String selectPagamentos = "call busca_pagamentos_contrato_para_relatorio()";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<PagamentoCompleto> lista_pagamentos = new ArrayList<>();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectPagamentos);

			rs = pstm.executeQuery();

			while (rs.next()) {

				if (rs != null) {
					System.out.print("pagamento não e nulo!");

					PagamentoCompleto pagamento = new PagamentoCompleto();

					pagamento.setTipo(rs.getInt("tipo"));
					pagamento.setDescricao(rs.getString("descricao"));
					pagamento.setId_pagamento(rs.getInt("id_pagamento"));
					pagamento.setData_pagamento(rs.getString("data_pagamento"));
					pagamento.setValor_pagamento(rs.getDouble("valor"));
					pagamento.setDepositante(rs.getString("depositante"));
					pagamento.setId_conta_depositante(rs.getInt("id_conta_depositante"));
					pagamento.setFavorecido(rs.getString("favorecido"));
					pagamento.setId_conta_favorecido(rs.getInt("id_conta_favorecido"));
					pagamento.setId_contrato_destinatario(rs.getInt("id_contrato_destinatario"));
					pagamento.setId_contrato_remetente(rs.getInt("id_contrato_remetente"));
					pagamento.setConta_bancaria_depositante(rs.getString("conta_bancaria_depositante"));
					pagamento.setConta_bancaria_favorecido(rs.getString("conta_bancaria_favorecido"));
					pagamento.setNome_comprovante(rs.getString("nome_arquivo"));

					CadastroContrato contrato_recebimento = new CadastroContrato();
					contrato_recebimento.setId(rs.getInt("contrato_id"));
					contrato_recebimento.setCodigo(rs.getString("codigo"));
					contrato_recebimento.setQuantidade(rs.getDouble("quantidade"));
					contrato_recebimento.setMedida(rs.getString("medida"));
					contrato_recebimento.setValor_a_pagar(new BigDecimal(rs.getString("valor_a_pagar")));
					contrato_recebimento.setValor_produto(rs.getDouble("valor_produto"));
					contrato_recebimento.setNomes_compradores(rs.getString("compradores"));
					contrato_recebimento.setNomes_vendedores(rs.getString("vendedores"));
					contrato_recebimento.setMedida(rs.getString("medida"));
					contrato_recebimento.setGrupo_particular(rs.getInt("grupo_particular"));
					contrato_recebimento.setCaminho_diretorio_contrato(rs.getString("caminho_diretorio"));

					CadastroSafra safra = new CadastroSafra();
					safra.setId_safra(rs.getInt("id_safra"));
					safra.setAno_colheita(rs.getInt("ano_colheita"));
					safra.setAno_plantio(rs.getInt("ano_plantio"));

					CadastroProduto produto = new CadastroProduto();
					produto.setId_produto(rs.getInt("id_produto"));
					produto.setNome_produto(rs.getString("nome_produto"));
					produto.setTransgenia(rs.getString("transgenia"));

					safra.setProduto(produto);

					contrato_recebimento.setModelo_produto(produto);
					contrato_recebimento.setModelo_safra(safra);

					pagamento.setContrato_remetente(contrato_recebimento);

					CadastroContrato contrato_destinatario = new CadastroContrato();
					contrato_destinatario.setCodigo(rs.getString("contrato_destinatario"));
					pagamento.setContrato_destinatario(contrato_destinatario);

					lista_pagamentos.add(pagamento);

				}
			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			System.out.println("Pagamentos foram listadas com sucesso!");
			return lista_pagamentos;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar os pagamentos do contrato: \n erro: " + e.getMessage()
					+ "\ncausa: " + e.getCause());
			return null;
		}

	}

	public ArrayList<PagamentoCompleto> getPagamentosContratuaisParaRelatorio(int id_contrato_remetente) {

		System.out.println("Listar pagamentos foi chamado!");
		String selectPagamentos = "select ct_pg.id_contrato, ct_pg.id_pagamento,\n"
				+ "ct.id as contrato_id,ct.codigo, ct.quantidade, ct.medida, ct.valor_a_pagar, ct.valor_produto,\n"
				+ "GROUP_CONCAT(distinct\n" + "case\n"
				+ "when comprador.tipo_cliente = '0' then comprador.nome_empresarial \n"
				+ " when comprador.tipo_cliente = '1' then comprador.nome_fantasia\n" + "end\n"
				+ " separator ',') as compradores,\n" + "GROUP_CONCAT(distinct\n" + "case\n"
				+ "when vendedor.tipo_cliente = '0' then vendedor.nome_empresarial \n"
				+ " when vendedor.tipo_cliente = '1' then vendedor.nome_fantasia\n" + "end\n"
				+ " separator ',') as vendedores, \n"
				+ " pg.id_pagamento, pg.tipo, pg.descricao, pg.data_pagamento, pg.valor, pg.id_conta_depositante, pg.id_conta_favorecido, pg.id_contrato_remetente, pg.id_contrato_destinatario,\n"
				+ " GROUP_CONCAT(distinct\n" + "case\n"
				+ "when depositante.tipo_cliente = '0' then depositante.nome_empresarial \n"
				+ " when depositante.tipo_cliente = '1' then depositante.nome_fantasia\n" + "end\n"
				+ " separator ',') as depositante,\n" + "  GROUP_CONCAT(distinct\n" + "case\n"
				+ "when favorecido.tipo_cliente = '0' then favorecido.nome_empresarial \n"
				+ " when favorecido.tipo_cliente = '1' then favorecido.nome_fantasia\n" + "end\n"
				+ " separator ',') as favorecido,\n" + " ct.codigo as contrato_remetente,\n"
				+ "ct_destinatario.codigo as contrato_destinatario,\n"
				+ " sf.id_safra, sf.ano_colheita, sf.ano_plantio,\n"
				+ " pd.id_produto, pd.nome_produto, pd.transgenia,\n" + " (\n" + " SELECT \n" + "  CONCAT(\n"
				+ "    (nome),' Banco: ',\n" + "    (banco),' Agência: ',\n" + "    (agencia),' Conta: ',\n"
				+ "    (conta)\n" + "  ) as conta_bancaria\n"
				+ "from conta_bancaria where id_conta = id_conta_depositante\n" + " ) as conta_bancaria_depositante,\n"
				+ " pg.id_conta_favorecido,\n" + "  (\n" + " SELECT \n" + "  CONCAT(\n" + "    (nome),' Banco: ',\n"
				+ "    (banco),' Agência: ',\n" + "    (agencia),' Conta: ',\n" + "    (conta)\n"
				+ "  ) as conta_bancaria\n" + "from conta_bancaria where id_conta = id_conta_favorecido\n"
				+ " ) as conta_bancaria_favorecido\n" + " from contrato_pagamentos ct_pg\n"
				+ "left join pagamento pg on pg.id_pagamento = ct_pg.id_pagamento\n"
				+ "left join contrato ct on ct.id = ct_pg.id_contrato \n"
				+ "left join contrato ct_destinatario on ct_destinatario.id = pg.id_contrato_destinatario \n"
				+ "LEFT JOIN contrato_comprador on contrato_comprador.id_contrato = ct.id\n"
				+ "LEFT JOIN cliente comprador on comprador.id_cliente = contrato_comprador.id_cliente\n"
				+ "LEFT JOIN contrato_vendedor on contrato_vendedor.id_contrato = ct.id\n"
				+ "LEFT JOIN cliente vendedor on vendedor.id_cliente = contrato_vendedor.id_cliente\n"
				+ "LEFT JOIN cliente depositante on depositante.id_cliente = id_depositante\n"
				+ "LEFT JOIN cliente favorecido on favorecido.id_cliente = id_favorecido\n"
				+ "LEFT join safra sf on sf.id_safra = ct.id_safra\n"
				+ "LEFT join produto pd on pd.id_produto = sf.id_produto where ct.id = ? or ct_destinatario.id = ?\n"
				+ "group by  pg.id_pagamento";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<PagamentoCompleto> lista_pagamentos = new ArrayList<>();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectPagamentos);
			pstm.setInt(1, id_contrato_remetente);
			pstm.setInt(2, id_contrato_remetente);

			rs = pstm.executeQuery();

			while (rs.next()) {

				if (rs != null) {
					System.out.print("pagamento não e nulo!");

					PagamentoCompleto pagamento = new PagamentoCompleto();

					pagamento.setTipo(rs.getInt("tipo"));
					pagamento.setDescricao(rs.getString("descricao"));
					pagamento.setId_pagamento(rs.getInt("id_pagamento"));
					pagamento.setData_pagamento(rs.getString("data_pagamento"));
					pagamento.setValor_pagamento(rs.getDouble("valor"));
					pagamento.setDepositante(rs.getString("depositante"));
					pagamento.setId_conta_depositante(rs.getInt("id_conta_depositante"));
					pagamento.setFavorecido(rs.getString("favorecido"));
					pagamento.setId_conta_favorecido(rs.getInt("id_conta_favorecido"));
					pagamento.setId_contrato_remetente(rs.getInt("id_contrato_remetente"));
					pagamento.setId_contrato_destinatario(rs.getInt("id_contrato_destinatario"));
					pagamento.setConta_bancaria_depositante(rs.getString("conta_bancaria_depositante"));
					pagamento.setConta_bancaria_favorecido(rs.getString("conta_bancaria_favorecido"));

					CadastroContrato contrato_recebimento = new CadastroContrato();
					contrato_recebimento.setId(rs.getInt("contrato_id"));
					contrato_recebimento.setCodigo(rs.getString("codigo"));
					contrato_recebimento.setQuantidade(rs.getDouble("quantidade"));
					contrato_recebimento.setMedida(rs.getString("medida"));
					contrato_recebimento.setValor_a_pagar(new BigDecimal(rs.getString("valor_a_pagar")));
					contrato_recebimento.setValor_produto(rs.getDouble("valor_produto"));
					contrato_recebimento.setNomes_compradores(rs.getString("compradores"));
					contrato_recebimento.setNomes_vendedores(rs.getString("vendedores"));

					CadastroSafra safra = new CadastroSafra();
					safra.setId_safra(rs.getInt("id_safra"));
					safra.setAno_colheita(rs.getInt("ano_colheita"));
					safra.setAno_plantio(rs.getInt("ano_plantio"));

					CadastroProduto produto = new CadastroProduto();
					produto.setId_produto(rs.getInt("id_produto"));
					produto.setNome_produto(rs.getString("nome_produto"));
					produto.setTransgenia(rs.getString("transgenia"));

					safra.setProduto(produto);

					contrato_recebimento.setModelo_produto(produto);
					contrato_recebimento.setModelo_safra(safra);

					pagamento.setContrato_remetente(contrato_recebimento);

					CadastroContrato contrato_destinatario = new CadastroContrato();
					contrato_destinatario.setCodigo(rs.getString("contrato_destinatario"));
					pagamento.setContrato_destinatario(contrato_destinatario);

					lista_pagamentos.add(pagamento);

				}
			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			System.out.println("Pagamentos foram listadas com sucesso!");
			return lista_pagamentos;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar os pagamentos do contrato: \n erro: " + e.getMessage()
					+ "\ncausa: " + e.getCause());
			return null;
		}

	}

	public ArrayList<CadastroContrato.CadastroPagamentoContratual> getPagamentosContratual(int id_pagamento) {

		System.out.println("Listar pagamentos foi chamado!");
		String selectPagamentos = "select pag.*,\n" + "CONCAT(\n" + "    (cb_depositante.nome),' Banco: ',\n"
				+ "    (cb_depositante.banco),' Agência: ',\n" + "    (cb_depositante.agencia),' Conta: ',\n"
				+ "    (cb_depositante.conta)\n" + "  ) as conta_bancaria_depositante,\n" + "  CONCAT(\n"
				+ "    (cb_favorecido.nome),' Banco: ',\n" + "    (cb_favorecido.banco),' Agência: ',\n"
				+ "    (cb_favorecido.agencia),' Conta: ',\n" + "    (cb_favorecido.conta)\n"
				+ "  ) as conta_bancaria_favorecido\n" + "from pagamento as pag\n"
				+ "left join conta_bancaria cb_depositante on cb_depositante.id_conta = pag.id_conta_depositante\n"
				+ "left join conta_bancaria cb_favorecido on cb_favorecido.id_conta = pag.id_conta_favorecido\n" + "\n"
				+ "\n" + "";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroContrato.CadastroPagamentoContratual> lista_pagamentos = new ArrayList<>();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectPagamentos);
			pstm.setInt(1, id_pagamento);

			rs = pstm.executeQuery();

			while (rs.next()) {

				if (rs != null) {
					System.out.print("pagamento não e nulo!");

					CadastroContrato.CadastroPagamentoContratual pagamento = new CadastroContrato.CadastroPagamentoContratual();

					pagamento.setId_pagamento(rs.getInt("id_pagamento"));
					pagamento.setData_pagamento(rs.getString("data_pagamento"));
					pagamento.setValor_pagamento(rs.getDouble("valor"));
					pagamento.setId_depositante(rs.getInt("id_depositante"));
					pagamento.setId_conta_depositante(rs.getInt("id_conta_depositante"));
					pagamento.setId_favorecido(rs.getInt("id_favorecido"));
					pagamento.setId_conta_favorecido(rs.getInt("id_conta_favorecido"));
					pagamento.setConta_depositante(rs.getString("conta_bancaria_depositante"));
					pagamento.setConta_favorecido(rs.getString("conta_bancaria_favorecido"));

					lista_pagamentos.add(pagamento);

				}
			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			System.out.println("Pagamentos listado com sucesso!");
			return lista_pagamentos;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar o pagamento: " + id_pagamento + " erro: " + "causa: ");
			return null;
		}

	}

	public ArrayList<CadastroPagamentoContratual> getPagamentosPorDepositante(int id_depositante) {

		System.out.println("Listar pagamentos foi chamado!");
		String selectPagamentos = "select * from pagamento where id_depositante = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroContrato.CadastroPagamentoContratual> lista_pagamentos = new ArrayList<>();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectPagamentos);
			pstm.setInt(1, id_depositante);

			rs = pstm.executeQuery();

			while (rs.next()) {

				if (rs != null) {
					System.out.print("pagamento não e nulo!");

					CadastroContrato.CadastroPagamentoContratual pagamento = new CadastroContrato.CadastroPagamentoContratual();

					pagamento.setId_pagamento(rs.getInt("id_pagamento"));
					pagamento.setData_pagamento(rs.getString("data_pagamento"));
					pagamento.setValor_pagamento(rs.getDouble("valor"));
					pagamento.setId_depositante(rs.getInt("id_depositante"));
					pagamento.setId_conta_depositante(rs.getInt("id_conta_depositante"));
					pagamento.setId_favorecido(rs.getInt("id_favorecido"));
					pagamento.setId_conta_favorecido(rs.getInt("id_conta_favorecido"));

					lista_pagamentos.add(pagamento);

				}
			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			System.out.println("Pagamentos foram listadas com sucesso!");
			return lista_pagamentos;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao listar os pagamentos do depositante: " + id_depositante + " erro: " + "causa: ");
			return null;
		}

	}

	public ArrayList<CadastroPagamentoContratual> getPagamentosPorFavorecido(int id_favorecido) {

		System.out.println("Listar pagamentos foi chamado!");
		String selectPagamentos = "select * from pagamento where id_favorecido = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroContrato.CadastroPagamentoContratual> lista_pagamentos = new ArrayList<>();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectPagamentos);
			pstm.setInt(1, id_favorecido);

			rs = pstm.executeQuery();

			while (rs.next()) {

				if (rs != null) {
					System.out.print("pagamento não e nulo!");

					CadastroContrato.CadastroPagamentoContratual pagamento = new CadastroContrato.CadastroPagamentoContratual();

					pagamento.setId_pagamento(rs.getInt("id_pagamento"));
					pagamento.setData_pagamento(rs.getString("data_pagamento"));
					pagamento.setValor_pagamento(rs.getDouble("valor"));
					pagamento.setId_depositante(rs.getInt("id_depositante"));
					pagamento.setId_conta_depositante(rs.getInt("id_conta_depositante"));
					pagamento.setId_favorecido(rs.getInt("id_favorecido"));
					pagamento.setId_conta_favorecido(rs.getInt("id_conta_favorecido"));

					lista_pagamentos.add(pagamento);

				}
			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			System.out.println("Pagamentos foram listadas com sucesso!");
			return lista_pagamentos;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao listar os pagamentos do depositante: " + id_favorecido + " erro: " + "causa: ");
			return null;
		}

	}

	public ArrayList<CadastroPagamentoContratual> getPagamentosDirecionados(int id_depositante, int id_favorecido) {

		System.out.println("Listar pagamentos foi chamado!");
		String selectPagamentos = "select * from pagamento where id_depositante = ? and id_favoreicod = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroContrato.CadastroPagamentoContratual> lista_pagamentos = new ArrayList<>();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectPagamentos);
			pstm.setInt(1, id_depositante);
			pstm.setInt(2, id_favorecido);

			rs = pstm.executeQuery();

			while (rs.next()) {

				if (rs != null) {
					System.out.print("pagamento não e nulo!");

					CadastroContrato.CadastroPagamentoContratual pagamento = new CadastroContrato.CadastroPagamentoContratual();

					pagamento.setId_pagamento(rs.getInt("id_pagamento"));
					pagamento.setData_pagamento(rs.getString("data_pagamento"));
					pagamento.setValor_pagamento(rs.getDouble("valor"));
					pagamento.setId_depositante(rs.getInt("id_depositante"));
					pagamento.setId_conta_depositante(rs.getInt("id_conta_depositante"));
					pagamento.setId_favorecido(rs.getInt("id_favorecido"));
					pagamento.setId_conta_favorecido(rs.getInt("id_conta_favorecido"));

					lista_pagamentos.add(pagamento);

				}
			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			System.out.println("Pagamentos foram listadas com sucesso!");
			return lista_pagamentos;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao listar os pagamentos do depositante: " + id_favorecido + " erro: " + "causa: ");
			return null;
		}

	}

	public CadastroContrato.CadastroPagamentoContratual getPagamentoContratual(int id_pagamento) {

		System.out.println("Listar pagamentos foi chamado!");
		String selectPagamentos = "select * from pagamento where id_pagamento = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		CadastroContrato.CadastroPagamentoContratual pagamento = new CadastroContrato.CadastroPagamentoContratual();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectPagamentos);
			pstm.setInt(1, id_pagamento);

			rs = pstm.executeQuery();

			rs.next();

			pagamento.setTipo(rs.getInt("tipo"));
			pagamento.setDescricao(rs.getString("descricao"));
			pagamento.setId_pagamento(rs.getInt("id_pagamento"));
			pagamento.setData_pagamento(rs.getString("data_pagamento"));
			pagamento.setValor_pagamento(rs.getDouble("valor"));
			pagamento.setId_depositante(rs.getInt("id_depositante"));
			pagamento.setId_conta_depositante(rs.getInt("id_conta_depositante"));
			pagamento.setId_favorecido(rs.getInt("id_favorecido"));
			pagamento.setId_conta_favorecido(rs.getInt("id_conta_favorecido"));
			pagamento.setId_contrato_destinatario(rs.getInt("id_contrato_destinatario"));
			pagamento.setId_contrato_remetente(rs.getInt("id_contrato_remetente"));

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			System.out.println("Pagamentos foram listadas com sucesso!");
			return pagamento;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao listar o pagamento de id: " + id_pagamento + " erro: " + "causa: ");
			return null;
		}

	}

	public Map<Integer, Integer> getRelacaoReplica(int id_pag) {
		System.out.println("Listar pagamentos foi chamado!");
		String selectPagamentos = "select * from contrato_pagamentos where id_pagamento = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Map<Integer, Integer> relacao = new HashMap<Integer, Integer>();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectPagamentos);
			pstm.setInt(1, id_pag);

			rs = pstm.executeQuery();

			while (rs.next()) {

				if (rs != null) {
					System.out.print("relacao não e nulo!");
					int id_contrato = rs.getInt("id_contrato");
					int id_pagamento = rs.getInt("id_pagamento");

					JOptionPane.showMessageDialog(null,
							"Replica\nId do contrato: " + id_contrato + " id_pagamento: " + id_pagamento);
					relacao.put(id_contrato, id_pagamento);

				}
			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			System.out.println("Relações foram listadas com sucesso!");
			return relacao;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao listar as relacoes de pagamentos com id: " + id_pag + " erro: " + "causa: ");
			return null;
		}
	}

	public boolean removerPagamentoContratual(int id_contrato, int id_pagamento) {

		return remover_pagamento_contratual(id_pagamento)
				&& remover_contrato_pagamento_contratual(id_contrato, id_pagamento);

	}

	private boolean remover_pagamento_contratual(int id_pagamento) {

		String sql_delete_pagamento = "DELETE FROM pagamento WHERE id_pagamento = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_delete_pagamento);

			pstm.setInt(1, id_pagamento);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			// JOptionPane.showMessageDialog(null, "Pagamento excluido, banco normalizado
			// ");
			return true;

		} catch (Exception f) {
			// JOptionPane.showMessageDialog(null, "Erro ao excluir o pagamento do banco de"
			// + "dados " + f.getMessage());
			return false;
		}

	}

	public boolean remover_contrato_pagamento_contratual(int id_contrato, int id_pagamento) {

		String sql_delete_contrato_pagamento = "DELETE FROM contrato_pagamentos WHERE id_contrato = ? and id_pagamento = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_delete_contrato_pagamento);

			pstm.setInt(1, id_contrato);
			pstm.setInt(2, id_pagamento);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			// JOptionPane.showMessageDialog(null, "Relacao contrato_pagamentos excluida,
			// banco normalizado ");
			return true;

		} catch (Exception f) {
			// JOptionPane.showMessageDialog(null, "Erro ao excluir a relacao
			// contrato_pagamentos do banco de" + "dados " + f.getMessage());
			return false;
		}

	}

	public String sql_pagamento_contratual(CadastroContrato.CadastroPagamentoContratual pagamento) {

		String query = "insert into pagamento (data_pagamento, valor , id_depositante, id_conta_depositante, id_favorecido, id_conta_favorecido, descricao, tipo, id_contrato_remetente, id_contrato_destinatario) values ('"
				+ pagamento.getData_pagamento() + "','" + pagamento.getValor_pagamento() + "','"
				+ pagamento.getId_depositante() + "','" + pagamento.getId_conta_depositante() + "','"
				+ pagamento.getId_favorecido() + "','" + pagamento.getId_conta_favorecido() + "','"
				+ pagamento.getDescricao() + "','" + pagamento.getTipo() + "','" + pagamento.getId_contrato_remetente()
				+ "','" + pagamento.getId_contrato_destinatario() + "')";
		return query;

	}

	public boolean atualizarStatusContrato(int id_contrato, int status) {

		String sql_atualizar_status_contrato = "update contrato set status_contrato = ? where id = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_atualizar_status_contrato);

			pstm.setInt(1, status);
			pstm.setInt(2, id_contrato);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			// JOptionPane.showMessageDialog(null, "Status do contrato Atualizado");
			return true;

		} catch (Exception f) {
			// JOptionPane.showMessageDialog(null,
			// "Erro ao atualizar o status do contrato no banco de" + "dados " +
			// f.getMessage());
			return false;
		}
	}

	public int getNumeroTotalContratos() {

		String selectGetNumContratos = "SELECT (SELECT count(*) from contrato) + (SELECT count(*) from aditivo) + (select count(*) from distrato) as result";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectGetNumContratos);

			rs = pstm.executeQuery();
			rs.next();
			int i = rs.getInt("result");

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			return i;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar o numero total de contratos!" + " erro: " + "causa: ");
			return -1;
		}

	}

	public int getNumeroContratosParaAssinar() {

		String selectGetNumContratos = "SELECT (SELECT count(*) from contrato where status_contrato = 1 or status_contrato = 0) "
				+ " + (SELECT count(*) from aditivo where status_aditivo = 1) + (SELECT count(*) from distrato where status_distrato = 1) as result;";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectGetNumContratos);

			rs = pstm.executeQuery();
			rs.next();
			int i = rs.getInt("result");

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			return i;

		} catch (Exception e) {
			// JOptionPane.showMessageDialog(null, "Erro ao listar o numero de contratos sem
			// assinaturas: " + " erro: "
			// + "causa: " );
			return -1;
		}

	}

	public int consultaContratos(int select, int id_safra) {

		String selectGetNumContratos = "";
		if (select == 0) {
			// numero total de contratos
			selectGetNumContratos = "SELECT\n" + " (SELECT COUNT(*) FROM contrato where id_safra = ?)\n" + " + \n"
					+ " (SELECT  COUNT(*) from aditivo \n"
					+ "LEFT JOIN contrato on contrato.id = aditivo.id_contrato_pai\n"
					+ "LEFT JOIN safra on safra.id_safra = contrato.id_safra = ?\n"
					+ "where contrato.id = aditivo.id_contrato_pai and contrato.id_safra = ?\n" + ") \n" + " as result";
		} else if (select == 1) {
			// numero total de constratos para assinar
			selectGetNumContratos = "SELECT\n"
					+ " (SELECT COUNT(*) FROM contrato where status_contrato = 1 and  id_safra = ?)\n" + " + \n"
					+ " (SELECT  COUNT(*) from aditivo \n"
					+ "LEFT JOIN contrato on contrato.id = aditivo.id_contrato_pai\n"
					+ "LEFT JOIN safra on safra.id_safra = contrato.id_safra = ?\n"
					+ "where contrato.id = aditivo.id_contrato_pai and contrato.id_safra = ? and aditivo.status_aditivo = 1\n"
					+ ") \n" + " as result;";
		}

		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectGetNumContratos);

			pstm.setInt(1, id_safra);
			pstm.setInt(2, id_safra);
			pstm.setInt(3, id_safra);

			rs = pstm.executeQuery();
			rs.next();
			int i = rs.getInt("result");

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			return i;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao fazer a consulta de contratos: " + " erro: " + "causa: ");
			return -1;
		}

	}

	public int getNumTarefas(int id_executor) {

		String selectGetNumContratos = "select count(*)  as num_tarefas from tarefa where status_tarefa = 2 and id_usuario_executor = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectGetNumContratos);
			pstm.setInt(1, id_executor);
			rs = pstm.executeQuery();
			rs.next();
			int i = rs.getInt("num_tarefas");

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			return i;

		} catch (Exception e) {
			// JOptionPane.showMessageDialog(null, "Erro ao listar o numero total de
			// tarefas!" + " erro: "
			// + "causa: " );
			return -1;
		}
	}

	public double getQuantidadeSacos() {

		String selectGetQuantidadeTotalSacos = "select medida, quantidade from contrato where  sub_contrato = 0 or sub_contrato = 3 or sub_contrato = 4 or sub_contrato = 5";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		double quantidade_total = 0;

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectGetQuantidadeTotalSacos);
			rs = pstm.executeQuery();

			while (rs.next()) {

				if (rs != null) {

					String medida = rs.getString("medida");
					double quantidade_lida = Double.parseDouble(rs.getString("quantidade"));
					double quantidade_sacos = 0;

					if (medida.equalsIgnoreCase("Sacos")) {
						quantidade_sacos = quantidade_lida;
					} else if (medida.equalsIgnoreCase("KG")) {
						quantidade_sacos = quantidade_lida / 60;
					}

					quantidade_total += quantidade_sacos;
				}
			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			return quantidade_total;

		} catch (Exception e) {
			// JOptionPane.showMessageDialog(null, "Erro ao listar a quantidade total de
			// sacos" + " erro: "
			// + "causa: " );
			return -1;
		}

	}

	public double getQuantidadeSacosRecebidos() {
		String selectGetQuantidadeTotalSacosCarregados = "select sum(peso_romaneio) as quantidade_total_recebida from recebimento\n"
				+ "left join contrato on contrato.id = recebimento.id_contrato_recebimento\n"
				+ "where (contrato.sub_contrato = 0 or contrato.sub_contrato = 3 or contrato.sub_contrato = 4 or contrato.sub_contrato = 5)";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		double quantidade_total = 0;

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectGetQuantidadeTotalSacosCarregados);
			rs = pstm.executeQuery();
			rs.next();
			double i = rs.getInt("quantidade_total_recebida");

			ConexaoBanco.fechaConexao(conn, pstm, rs);

			return i / 60;

		} catch (Exception e) {
			// JOptionPane.showMessageDialog(null, "Erro ao listar a quantidade total de
			// sacos recebidos" + " erro: "
			// + "causa: " );
			return -1;
		}

	}

	public double getQuantidadeSacosCarregados() {

		String selectGetQuantidadeTotalSacosCarregados = "select sum(peso_romaneio) as quantidade_total_carregada from carregamento\n"
				+ "left join contrato on contrato.id = carregamento.id_contrato_carregamento\n"
				+ "where (contrato.sub_contrato = 0 or contrato.sub_contrato = 3 or contrato.sub_contrato = 4 or contrato.sub_contrato = 5)";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		double quantidade_total = 0;

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectGetQuantidadeTotalSacosCarregados);
			rs = pstm.executeQuery();
			rs.next();
			double i = rs.getInt("quantidade_total_carregada");

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			return i / 60;

		} catch (Exception e) {
			// JOptionPane.showMessageDialog(null, "Erro ao listar a quantidade total de
			// sacos carregados" + " erro: "
			// + "causa: " );
			return -1;
		}

	}

	public double getQuantidadeSacosCarregadosPorSafra(int id_safra) {

		String selectGetQuantidadeTotalSacosCarregados = "select sum(peso_romaneio) as quantidade_total_carregada from carregamento\n"
				+ "left join contrato on contrato.id = carregamento.id_contrato_carregamento\n"
				+ "where (contrato.sub_contrato = 0 or contrato.sub_contrato = 3 or contrato.sub_contrato = 4 or contrato.sub_contrato = 5) and contrato.id_safra = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		double quantidade_total = 0;

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectGetQuantidadeTotalSacosCarregados);
			pstm.setInt(1, id_safra);

			rs = pstm.executeQuery();
			rs.next();
			double i = rs.getInt("quantidade_total_carregada");

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			return i / 60;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao listar a quantidade total de sacos carregados da safra selecionada" + " erro: "
							+ "causa: ");
			return -1;
		}

	}

	public double getQuantidadeSacosRecebidosPorSafra(int id_safra) {

		String selectGetQuantidadeTotalSacosRecebidos = "select sum(peso_romaneio) as quantidade_total_recebida from recebimento\n"
				+ "left join contrato on contrato.id = recebimento.id_contrato_recebimento\n"
				+ "where (contrato.sub_contrato = 0 or contrato.sub_contrato = 3 or contrato.sub_contrato = 4 or contrato.sub_contrato = 5) and contrato.id_safra = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		double quantidade_total = 0;

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectGetQuantidadeTotalSacosRecebidos);
			pstm.setInt(1, id_safra);

			rs = pstm.executeQuery();
			rs.next();
			double i = rs.getInt("quantidade_total_recebida");

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			return i / 60;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao listar a quantidade total de sacos recebidos da safra selecionada" + " erro: "
							+ "causa: ");
			return -1;
		}

	}

	public double getQuantidadeSacosPorSafra(int id_safra) {

		String selectGetQuantidadeTotalSacosPorSafra = "select medida, quantidade from contrato\n"
				+ " where  (sub_contrato = 0 or sub_contrato = 3 or contrato.sub_contrato = 4 or contrato.sub_contrato = 5)and\n"
				+ "contrato.id_safra = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		double quantidade_total = 0;

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectGetQuantidadeTotalSacosPorSafra);
			pstm.setInt(1, id_safra);

			rs = pstm.executeQuery();
			while (rs.next()) {

				if (rs != null) {

					String medida = rs.getString("medida");
					double quantidade_lida = Double.parseDouble(rs.getString("quantidade"));
					double quantidade_sacos = 0;

					if (medida.equalsIgnoreCase("Sacos")) {
						quantidade_sacos = quantidade_lida;
					} else if (medida.equalsIgnoreCase("KG")) {
						quantidade_sacos = quantidade_lida / 60;
					}

					quantidade_total += quantidade_sacos;
				}
			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			return quantidade_total;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao listar a quantidade total de sacos para a safra selecionada" + " erro: " + "causa: ");
			return -1;
		}

	}

	public Map<String, Double> getCarregamentosPorData(String menor_data, String maior_data, int id_safra) {
		String selectCarregamentosPorData = "";

		if (id_safra > 0) {
			selectCarregamentosPorData = "select data_carregamento, sum(peso_romaneio) as total_carregado_dia from carregamento\n"
					+ "left join contrato on contrato.id = carregamento.id_contrato_carregamento\n"
					+ "where (contrato.sub_contrato = 0 or contrato.sub_contrato = 3 or contrato.sub_contrato = 4 or contrato.sub_contrato = 4)\n"
					+ " and data_carregamento BETWEEN (?) AND (?) and contrato.id_safra = ?\n" + "GROUP BY\n"
					+ "  day( data_carregamento )";
		} else {
			selectCarregamentosPorData = "select data_carregamento, sum(peso_romaneio) as total_carregado_dia from carregamento\n"
					+ "left join contrato on contrato.id = carregamento.id_contrato_carregamento\n"
					+ "where (contrato.sub_contrato = 0 or contrato.sub_contrato = 3 or contrato.sub_contrato = 4 or contrato.sub_contrato = 5)\n"
					+ " and data_carregamento BETWEEN (?) AND (?)\n" + "GROUP BY\n" + "  day( data_carregamento )";
		}

		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		double quantidade_total = 0;
		Map<String, Double> total_carregados_por_dia = new HashMap<String, Double>();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectCarregamentosPorData);
			pstm.setString(1, menor_data);
			pstm.setString(2, maior_data);

			if (id_safra > 0)
				pstm.setInt(3, id_safra);

			rs = pstm.executeQuery();
			while (rs.next()) {

				if (rs != null) {

					String data = rs.getDate("data_carregamento").toString();
					double quantidade_sacos = Double.parseDouble(rs.getString("total_carregado_dia")) / 60;
					total_carregados_por_dia.put(data, quantidade_sacos);

				}
			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			return total_carregados_por_dia;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao listar a quantidade total de sacos para a safra selecionada" + " erro: " + "causa: ");
			return null;
		}

	}

	public int getContratoDonoPagamento(int id_pagamento) {
		String query = "select id from contrato\n"
				+ "left join contrato_pagamentos on contrato_pagamentos.id_pagamento = ?\n"
				+ "where contrato.id = contrato_pagamentos.id_contrato";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		double quantidade_total = 0;

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(query);
			pstm.setInt(1, id_pagamento);

			rs = pstm.executeQuery();
			rs.next();
			int id = rs.getInt("id");
			return id;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao listar o dono do pagamento " + id_pagamento + " erro: " + "causa: ");
			return -1;
		}

	}

	public ArrayList<RegistroQuantidade> getQuantidades(int id_safra, int id_cliente, int id_contra_parte, int flag) {
		/*String selectQuantidadesPorSafraComprador = "select id_cliente, comprador, vendedor, sum(quantidade) as total\n"
				+ "from\n" + "(\n" + "select vendedor.id_cliente,\n" + "case\n"
				+ "when comprador.tipo_cliente = '0' then comprador.nome_empresarial \n"
				+ " when comprador.tipo_cliente = '1' then  comprador.nome_fantasia\n" + "end as comprador,\n"
				+ "case\n" + "when vendedor.tipo_cliente = '0' then LTRIM( vendedor.nome_empresarial )\n"
				+ " when vendedor.tipo_cliente = '1' then LTRIM( vendedor.nome_fantasia)\n" + "end  as vendedor, \n"
				+ "case\n" + "when ct.medida = 'Sacos' then quantidade\n"
				+ " when ct.medida = 'KG' then (quantidade / 60)\n" + "end  as quantidade\n" + "from contrato ct\n"
				+ "\n" + "LEFT JOIN contrato_comprador on contrato_comprador.id_contrato = ct.id\n"
				+ "LEFT JOIN cliente comprador on comprador.id_cliente = contrato_comprador.id_cliente\n" + "\n"
				+ "LEFT JOIN contrato_vendedor  on contrato_vendedor.id_contrato = ct.id\n"
				+ "LEFT JOIN cliente vendedor on vendedor.id_cliente = contrato_vendedor.id_cliente\n"
				+ "where (ct.sub_contrato = 0 or ct.sub_contrato = 3 or ct.sub_contrato = 4 or ct.sub_contrato = 5)\n"
				+ "and ct.id_safra = ?\n" + "and comprador.id_cliente = ?\n" + "\n" + ")\n" + "A\n"
				+ "group by  vendedor\n" + "order by vendedor\n" + "\n" + "";
		String selectQuantidadesTodasSafrasComprador = "select id_cliente, comprador, vendedor, sum(quantidade) as total\n"
				+ "from\n" + "(\n" + "select vendedor.id_cliente,\n" + "case\n"
				+ "when comprador.tipo_cliente = '0' then comprador.nome_empresarial \n"
				+ " when comprador.tipo_cliente = '1' then  comprador.nome_fantasia\n" + "end as comprador,\n"
				+ "case\n" + "when vendedor.tipo_cliente = '0' then LTRIM( vendedor.nome_empresarial )\n"
				+ " when vendedor.tipo_cliente = '1' then LTRIM( vendedor.nome_fantasia)\n" + "end  as vendedor, \n"
				+ "case\n" + "when ct.medida = 'Sacos' then quantidade\n"
				+ " when ct.medida = 'KG' then (quantidade / 60)\n" + "end  as quantidade\n" + "from contrato ct\n"
				+ "\n" + "LEFT JOIN contrato_comprador on contrato_comprador.id_contrato = ct.id\n"
				+ "LEFT JOIN cliente comprador on comprador.id_cliente = contrato_comprador.id_cliente\n" + "\n"
				+ "LEFT JOIN contrato_vendedor  on contrato_vendedor.id_contrato = ct.id\n"
				+ "LEFT JOIN cliente vendedor on vendedor.id_cliente = contrato_vendedor.id_cliente\n"
				+ "where (ct.sub_contrato = 0 or ct.sub_contrato = 3 or ct.sub_contrato = 4 or ct.sub_contrato = 5)\n"
				+ "and comprador.id_cliente = ?\n" + "\n" + ")\n" + "A\n" + "group by  vendedor\n"
				+ "order by vendedor\n" + "\n" + "";

		String selectQuantidadesPorSafraVendedor = "select id_cliente, comprador, vendedor, sum(quantidade) as total\n"
				+ "from\n" + "(\n" + "select vendedor.id_cliente,\n" + "case\n"
				+ "when comprador.tipo_cliente = '0' then comprador.nome_empresarial \n"
				+ " when comprador.tipo_cliente = '1' then  comprador.nome_fantasia\n" + "end as comprador,\n"
				+ "case\n" + "when vendedor.tipo_cliente = '0' then LTRIM( vendedor.nome_empresarial )\n"
				+ " when vendedor.tipo_cliente = '1' then LTRIM( vendedor.nome_fantasia)\n" + "end  as vendedor, \n"
				+ "case\n" + "when ct.medida = 'Sacos' then quantidade\n"
				+ " when ct.medida = 'KG' then (quantidade / 60)\n" + "end  as quantidade\n" + "from contrato ct\n"
				+ "\n" + "LEFT JOIN contrato_comprador on contrato_comprador.id_contrato = ct.id\n"
				+ "LEFT JOIN cliente comprador on comprador.id_cliente = contrato_comprador.id_cliente\n" + "\n"
				+ "LEFT JOIN contrato_vendedor  on contrato_vendedor.id_contrato = ct.id\n"
				+ "LEFT JOIN cliente vendedor on vendedor.id_cliente = contrato_vendedor.id_cliente\n"
				+ "where (ct.sub_contrato = 0 or ct.sub_contrato = 3 or ct.sub_contrato = 4 or ct.sub_contrato = 5)\n"
				+ "and ct.id_safra = ?\n" + "and vendedor.id_cliente = ?\n" + "\n" + ")\n" + "A\n"
				+ "group by  vendedor\n" + "order by vendedor\n" + "\n" + "";
		String selectQuantidadesTodasSafrasVendedor = "select id_cliente, comprador, vendedor, sum(quantidade) as total\n"
				+ "from\n" + "(\n" + "select vendedor.id_cliente,\n" + "case\n"
				+ "when comprador.tipo_cliente = '0' then comprador.nome_empresarial \n"
				+ " when comprador.tipo_cliente = '1' then  comprador.nome_fantasia\n" + "end as comprador,\n"
				+ "case\n" + "when vendedor.tipo_cliente = '0' then LTRIM( vendedor.nome_empresarial )\n"
				+ " when vendedor.tipo_cliente = '1' then LTRIM( vendedor.nome_fantasia)\n" + "end  as vendedor, \n"
				+ "case\n" + "when ct.medida = 'Sacos' then quantidade\n"
				+ " when ct.medida = 'KG' then (quantidade / 60)\n" + "end  as quantidade\n" + "from contrato ct\n"
				+ "\n" + "LEFT JOIN contrato_comprador on contrato_comprador.id_contrato = ct.id\n"
				+ "LEFT JOIN cliente comprador on comprador.id_cliente = contrato_comprador.id_cliente\n" + "\n"
				+ "LEFT JOIN contrato_vendedor  on contrato_vendedor.id_contrato = ct.id\n"
				+ "LEFT JOIN cliente vendedor on vendedor.id_cliente = contrato_vendedor.id_cliente\n"
				+ "where (ct.sub_contrato = 0 or ct.sub_contrato = 3 or ct.sub_contrato = 4 or ct.sub_contrato = 5)\n"
				+ "and vendedor.id_cliente = ?\n" + "\n" + ")\n" + "A\n" + "group by  vendedor\n"
				+ "order by vendedor\n" + "\n" + "";
				*/
		
		String selectQuantidades = "call getQuantidadesContratas(?,?,?)";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<RegistroQuantidade> registros = new ArrayList<>();
		try {

			conn = ConexaoBanco.getConexao();

			pstm = conn.prepareStatement(selectQuantidades);
			
			
			if(flag == 1 ) {
				//como comprador
			if(id_cliente > 0)
			  pstm.setInt(1, id_cliente);
			else
				pstm.setInt(1, 0);

			if (id_contra_parte > 0)
				pstm.setInt(2, id_contra_parte);
			else
				pstm.setInt(2, 0);

			
			if (id_safra > 0) 
				pstm.setInt(3, id_safra);
			else
				pstm.setInt(3, 0);
			
			
			}else if(flag == 2) {
				//como vendedor
				if(id_contra_parte > 0)
					pstm.setInt(1, id_contra_parte);
					else
						pstm.setInt(1, 0);

					if (id_cliente > 0)
						pstm.setInt(2, id_cliente);
					else
						pstm.setInt(2, 0);

					
					if (id_safra > 0) 
						pstm.setInt(3, id_safra);
					else
						pstm.setInt(3, 0);
				
			}
			
			rs = pstm.executeQuery();
			while (rs.next()) {
				RegistroQuantidade registro = new RegistroQuantidade();

				registro.setId_cliente(rs.getInt("id_cliente"));
				registro.setComprador(rs.getString("comprador"));
				registro.setVendedor(rs.getString("vendedor"));
				registro.setTotal(rs.getDouble("total"));

				registros.add(registro);

			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			return registros;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar os registros de quantidade total" + " erro: "
					+ e.getMessage() + "causa: " + e.getCause());
			return null;
		}

	}

	public ArrayList<RegistroQuantidade> getQuantidadesCarregadas(int id_safra, int id_cliente, int id_contra_parte, int flag) {
	    
		String selectCarregadas = "call getQuantidadesCarregadas(?,?,?)";
		
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<RegistroQuantidade> registros = new ArrayList<>();
		try {

			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectCarregadas);

			if(flag == 1 ) {
				//como comprador
			if(id_cliente > 0)
			  pstm.setInt(1, id_cliente);
			else
				pstm.setInt(1, 0);

			if (id_contra_parte > 0)
				pstm.setInt(2, id_contra_parte);
			else
				pstm.setInt(2, 0);

			
			if (id_safra > 0) 
				pstm.setInt(3, id_safra);
			else
				pstm.setInt(3, 0);
			
			
			}else if(flag == 2) {
				//como vendedor
				if(id_contra_parte > 0)
					pstm.setInt(1, id_contra_parte);
					else
						pstm.setInt(1, 0);

					if (id_cliente > 0)
						pstm.setInt(2, id_cliente);
					else
						pstm.setInt(2, 0);

					
					if (id_safra > 0) 
						pstm.setInt(3, id_safra);
					else
						pstm.setInt(3, 0);
				
			}
			
			rs = pstm.executeQuery();
			while (rs.next()) {
				RegistroQuantidade registro = new RegistroQuantidade();

				registro.setId_cliente(rs.getInt("id_cliente"));
				registro.setComprador(rs.getString("comprador"));
				registro.setVendedor(rs.getString("vendedor"));
				registro.setTotal(rs.getDouble("quantidade_carregada"));
				

				registros.add(registro);

			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			return registros;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar os registros de quantidade carregada" + " erro: "
					+ e.getMessage() + "causa: " + e.getCause());
			return null;
		}

	}
	
public ArrayList<RegistroQuantidade> getQuantidadesTransPositiva(int id_safra, int id_cliente, int id_contra_parte, int flag) {
	    
		String selectCarregadas = "call getQuantidadesTransRecebida(?,?,?)";
		
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<RegistroQuantidade> registros = new ArrayList<>();
		try {

			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectCarregadas);

			if(flag == 1 ) {
				//como comprador
			if(id_cliente > 0)
			  pstm.setInt(1, id_cliente);
			else
				pstm.setInt(1, 0);

			if (id_contra_parte > 0)
				pstm.setInt(2, id_contra_parte);
			else
				pstm.setInt(2, 0);

			
			if (id_safra > 0) 
				pstm.setInt(3, id_safra);
			else
				pstm.setInt(3, 0);
			
			
			}else if(flag == 2) {
				//como vendedor
				if(id_contra_parte > 0)
					pstm.setInt(1, id_contra_parte);
					else
						pstm.setInt(1, 0);

					if (id_cliente > 0)
						pstm.setInt(2, id_cliente);
					else
						pstm.setInt(2, 0);

					
					if (id_safra > 0) 
						pstm.setInt(3, id_safra);
					else
						pstm.setInt(3, 0);
				
			}
			
			rs = pstm.executeQuery();
			while (rs.next()) {
				RegistroQuantidade registro = new RegistroQuantidade();

				registro.setTotal(rs.getDouble("quantidade_trans_carga_recebida"));
				

				registros.add(registro);

			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			return registros;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar os registros de quantidade carregada" + " erro: "
					+ e.getMessage() + "causa: " + e.getCause());
			return null;
		}

	}

public ArrayList<RegistroQuantidade> getQuantidadesTransNegativa(int id_safra, int id_cliente, int id_contra_parte, int flag) {
    
	String selectCarregadas = "call getQuantidadesTransEnviada(?,?,?)";
	
	Connection conn = null;
	PreparedStatement pstm = null;
	ResultSet rs = null;
	ArrayList<RegistroQuantidade> registros = new ArrayList<>();
	try {

		conn = ConexaoBanco.getConexao();
		pstm = conn.prepareStatement(selectCarregadas);

		if(flag == 1 ) {
			//como comprador
		if(id_cliente > 0)
		  pstm.setInt(1, id_cliente);
		else
			pstm.setInt(1, 0);

		if (id_contra_parte > 0)
			pstm.setInt(2, id_contra_parte);
		else
			pstm.setInt(2, 0);

		
		if (id_safra > 0) 
			pstm.setInt(3, id_safra);
		else
			pstm.setInt(3, 0);
		
		
		}else if(flag == 2) {
			//como vendedor
			if(id_contra_parte > 0)
				pstm.setInt(1, id_contra_parte);
				else
					pstm.setInt(1, 0);

				if (id_cliente > 0)
					pstm.setInt(2, id_cliente);
				else
					pstm.setInt(2, 0);

				
				if (id_safra > 0) 
					pstm.setInt(3, id_safra);
				else
					pstm.setInt(3, 0);
			
		}
		
		rs = pstm.executeQuery();
		while (rs.next()) {
			RegistroQuantidade registro = new RegistroQuantidade();

			
			registro.setTotal(rs.getDouble("quantidade_trans_carga_enviada"));
			

			registros.add(registro);

		}

		ConexaoBanco.fechaConexao(conn, pstm, rs);
		return registros;

	} catch (Exception e) {
		JOptionPane.showMessageDialog(null, "Erro ao listar os registros de quantidade carregada" + " erro: "
				+ e.getMessage() + "causa: " + e.getCause());
		return null;
	}

}


	public double getQuantidadeRecebida(int id_contrato) {
		// String selectGetQuantidadeTotalSacos = "select sum(peso_romaneio) as
		// quantidade_recebida from recebimento where id_contrato_recebimento = ?";
		String selectGetQuantidadeTotalSacos = "select sum(quantidade_recebida) as quantidade_recebida from\n" + "(\n"
				+ "select sum(peso_romaneio) as quantidade_recebida from recebimento where id_contrato_recebimento = ?\n"
				+ "union\n"
				+ "select sum(quantidade) as quantidade_recebida from transferencia_recebimento where id_contrato_destinatario = ?\n"
				+ ")as quantidade_recebida";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		double quantidade_total = 0;

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectGetQuantidadeTotalSacos);
			pstm.setInt(1, id_contrato);
			pstm.setInt(2, id_contrato);

			rs = pstm.executeQuery();

			rs.next();

			if (rs != null) {

				double quantidade_kgs = rs.getDouble("quantidade_recebida");
				return quantidade_kgs;

			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			return quantidade_total;

		} catch (Exception e) {
			// JOptionPane.showMessageDialog(null, "Erro ao listar a quantidade total de
			// sacos" + " erro: "
			// + "causa: " );
			return -1;
		}

	}

	public ArrayList<RegistroRecebimento> getRecebidas(int id_safra, int id_cliente, int id_contra_parte, int flag) {
		/*String selectRecebidasPorSafraComprador = "select id_cliente, comprador, vendedor, sum(quantidade_recebida) as quantidade_recebida\n"
				+ "from\n" + "(\n" + "select vendedor.id_cliente,\n" + "case\n"
				+ "when comprador.tipo_cliente = '0' then comprador.nome_empresarial \n"
				+ " when comprador.tipo_cliente = '1' then comprador.nome_fantasia\n" + "end as comprador,\n" + "case\n"
				+ "when vendedor.tipo_cliente = '0' then LTRIM(vendedor.nome_empresarial )\n"
				+ " when vendedor.tipo_cliente = '1' then LTRIM( vendedor.nome_fantasia)\n" + "end  as vendedor, \n"
				+ "ct.id as id_contrato,\n"
				+ "recebido.id_recebimento,( recebido.peso_romaneio / 60 ) as quantidade_recebida\n"
				+ "from contrato ct\n" + "\n"
				+ "LEFT JOIN contrato_comprador on contrato_comprador.id_contrato = ct.id\n"
				+ "LEFT JOIN cliente comprador on comprador.id_cliente = contrato_comprador.id_cliente\n" + "\n"
				+ "LEFT JOIN contrato_vendedor  on contrato_vendedor.id_contrato = ct.id\n"
				+ "LEFT JOIN cliente vendedor on vendedor.id_cliente = contrato_vendedor.id_cliente\n" + "\n"
				+ "LEFT JOIN contrato_recebimentos  on contrato_recebimentos.id_contrato = ct.id\n"
				+ "LEFT JOIN recebimento recebido on recebido.id_recebimento = contrato_recebimentos.id_recebimento\n"
				+ "where (ct.sub_contrato = 0 or ct.sub_contrato = 3 or ct.sub_contrato = 4 or ct.sub_contrato = 5)\n"
				+ "and ct.id_safra = ?\n" + "and comprador.id_cliente = ?\n" + ")\n" + "A \n" + "group by vendedor\n"
				+ "order by vendedor\n" + "";
		String selectRecebidasTodasSafrasComprador = "select id_cliente, comprador, vendedor, sum(quantidade_recebida) as quantidade_recebida\n"
				+ "from\n" + "(\n" + "select vendedor.id_cliente,\n" + "case\n"
				+ "when comprador.tipo_cliente = '0' then comprador.nome_empresarial \n"
				+ " when comprador.tipo_cliente = '1' then comprador.nome_fantasia\n" + "end as comprador,\n" + "case\n"
				+ "when vendedor.tipo_cliente = '0' then LTRIM(vendedor.nome_empresarial )\n"
				+ " when vendedor.tipo_cliente = '1' then LTRIM( vendedor.nome_fantasia)\n" + "end  as vendedor, \n"
				+ "ct.id as id_contrato,\n"
				+ "recebido.id_recebimento,( recebido.peso_romaneio / 60 ) as quantidade_recebida\n"
				+ "from contrato ct\n" + "\n"
				+ "LEFT JOIN contrato_comprador on contrato_comprador.id_contrato = ct.id\n"
				+ "LEFT JOIN cliente comprador on comprador.id_cliente = contrato_comprador.id_cliente\n" + "\n"
				+ "LEFT JOIN contrato_vendedor  on contrato_vendedor.id_contrato = ct.id\n"
				+ "LEFT JOIN cliente vendedor on vendedor.id_cliente = contrato_vendedor.id_cliente\n" + "\n"
				+ "LEFT JOIN contrato_recebimentos  on contrato_recebimentos.id_contrato = ct.id\n"
				+ "LEFT JOIN recebimento recebido on recebido.id_recebimento = contrato_recebimentos.id_recebimento\n"
				+ "where (ct.sub_contrato = 0 or ct.sub_contrato = 3 or ct.sub_contrato = 4 or ct.sub_contrato = 5)\n"
				+ "and comprador.id_cliente = ?\n" + ")\n" + "A \n" + "group by vendedor\n" + "order by vendedor\n"
				+ "";

		String selectRecebidasPorSafraVendedor = "select id_cliente, comprador, vendedor, sum(quantidade_recebida) as quantidade_recebida\n"
				+ "from\n" + "(\n" + "select vendedor.id_cliente,\n" + "case\n"
				+ "when comprador.tipo_cliente = '0' then comprador.nome_empresarial \n"
				+ " when comprador.tipo_cliente = '1' then comprador.nome_fantasia\n" + "end as comprador,\n" + "case\n"
				+ "when vendedor.tipo_cliente = '0' then LTRIM(vendedor.nome_empresarial )\n"
				+ " when vendedor.tipo_cliente = '1' then LTRIM( vendedor.nome_fantasia)\n" + "end  as vendedor, \n"
				+ "ct.id as id_contrato,\n"
				+ "recebido.id_recebimento,( recebido.peso_romaneio / 60 ) as quantidade_recebida\n"
				+ "from contrato ct\n" + "\n"
				+ "LEFT JOIN contrato_comprador on contrato_comprador.id_contrato = ct.id\n"
				+ "LEFT JOIN cliente comprador on comprador.id_cliente = contrato_comprador.id_cliente\n" + "\n"
				+ "LEFT JOIN contrato_vendedor  on contrato_vendedor.id_contrato = ct.id\n"
				+ "LEFT JOIN cliente vendedor on vendedor.id_cliente = contrato_vendedor.id_cliente\n" + "\n"
				+ "LEFT JOIN contrato_recebimentos  on contrato_recebimentos.id_contrato = ct.id\n"
				+ "LEFT JOIN recebimento recebido on recebido.id_recebimento = contrato_recebimentos.id_recebimento\n"
				+ "where (ct.sub_contrato = 0 or ct.sub_contrato = 3 or ct.sub_contrato = 4 or ct.sub_contrato = 5)\n"
				+ "and ct.id_safra = ?\n" + "and vendedor.id_cliente = ?\n" + ")\n" + "A \n" + "group by vendedor\n"
				+ "order by vendedor\n" + "";
		String selectRecebidasTodasSafrasVendedor = "select id_cliente, comprador, vendedor, sum(quantidade_recebida) as quantidade_recebida\n"
				+ "from\n" + "(\n" + "select vendedor.id_cliente,\n" + "case\n"
				+ "when comprador.tipo_cliente = '0' then comprador.nome_empresarial \n"
				+ " when comprador.tipo_cliente = '1' then comprador.nome_fantasia\n" + "end as comprador,\n" + "case\n"
				+ "when vendedor.tipo_cliente = '0' then LTRIM(vendedor.nome_empresarial )\n"
				+ " when vendedor.tipo_cliente = '1' then LTRIM( vendedor.nome_fantasia)\n" + "end  as vendedor, \n"
				+ "ct.id as id_contrato,\n"
				+ "recebido.id_recebimento,( recebido.peso_romaneio / 60 ) as quantidade_recebida\n"
				+ "from contrato ct\n" + "\n"
				+ "LEFT JOIN contrato_comprador on contrato_comprador.id_contrato = ct.id\n"
				+ "LEFT JOIN cliente comprador on comprador.id_cliente = contrato_comprador.id_cliente\n" + "\n"
				+ "LEFT JOIN contrato_vendedor  on contrato_vendedor.id_contrato = ct.id\n"
				+ "LEFT JOIN cliente vendedor on vendedor.id_cliente = contrato_vendedor.id_cliente\n" + "\n"
				+ "LEFT JOIN contrato_recebimentos  on contrato_recebimentos.id_contrato = ct.id\n"
				+ "LEFT JOIN recebimento recebido on recebido.id_recebimento = contrato_recebimentos.id_recebimento\n"
				+ "where (ct.sub_contrato = 0 or ct.sub_contrato = 3 or ct.sub_contrato = 4 or ct.sub_contrato = 5)\n"
				+ "and vendedor.id_cliente = ?\n" + ")\n" + "A \n" + "group by vendedor\n" + "order by vendedor\n" + "";

*/
		
		String selectRecebidas = "call getQuantidadesRecebidas(?,?,?)";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<RegistroRecebimento> registros = new ArrayList<>();
		try {

			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectRecebidas);

			if(flag == 1 ) {
				//como comprador
			if(id_cliente > 0)
			  pstm.setInt(1, id_cliente);
			else
				pstm.setInt(1, 0);

			if (id_contra_parte > 0)
				pstm.setInt(2, id_contra_parte);
			else
				pstm.setInt(2, 0);

			
			if (id_safra > 0) 
				pstm.setInt(3, id_safra);
			else
				pstm.setInt(3, 0);
			
			
			}else if(flag == 2) {
				//como vendedor
				if(id_contra_parte > 0)
					pstm.setInt(1, id_contra_parte);
					else
						pstm.setInt(1, 0);

					if (id_cliente > 0)
						pstm.setInt(2, id_cliente);
					else
						pstm.setInt(2, 0);

					
					if (id_safra > 0) 
						pstm.setInt(3, id_safra);
					else
						pstm.setInt(3, 0);
				
			}

			rs = pstm.executeQuery();
			while (rs.next()) {
				RegistroRecebimento registro = new RegistroRecebimento();

				registro.setId_cliente(rs.getInt("id_cliente"));
				registro.setComprador(rs.getString("comprador"));
				registro.setVendedor(rs.getString("vendedor"));
				registro.setQuantidade_recebida(rs.getDouble("quantidade_recebida"));

				registros.add(registro);

			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			return registros;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar os registros de quantidade recebida" + " erro: "
					+ e.getMessage() + "causa: " + e.getCause());
			return null;
		}

	}

}