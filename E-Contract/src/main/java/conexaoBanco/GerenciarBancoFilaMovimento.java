package main.java.conexaoBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import main.java.cadastros.CadastroAcessoTemporario;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroFilaMovimento;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroRomaneio;
import main.java.cadastros.CadastroSafra;

public class GerenciarBancoFilaMovimento {

	public int inserirCaminhaoNaFila(CadastroFilaMovimento unidade) {
		int result = -1;
		String sql = "insert into fila_embarque_desembarque\r\n"
				+ "(tipo_movimentacao, id_transportadora , id_motorista, id_veiculo, id_produtor, id_produto, status, notificacao_em_fila,notificado_entrada,notificado_saida"
				+ ",  umidade,impureza , ardidos,observacao ,origem ,destino ,tem_nf ,autorizacao_movimentacao ,data_hora_fila, id_login, peso_bruto, peso_tara, peso_liquido ) values ('"
				+ unidade.getTipo_movimentacao() + "','" + unidade.getTransportadora().getId() + "','"

				+ unidade.getMotorista().getId() + "','" + unidade.getVeiculo().getId_veiculo() + "','"

				+ unidade.getProdutor().getId() + "','" + unidade.getProduto().getId_produto() + "','"
				+ unidade.getStatus() + "','"

				+ unidade.getNotificado_em_fila() + "','" + unidade.getNotificado_entrada() + "','"
				+ unidade.getNotificado_saida() + "','"

				+ unidade.getUmidade() + "','" + unidade.getImpureza() + "','" + unidade.getArdidos() + "','"

				+ unidade.getObservacao() + "','" + unidade.getOrigem() + "','" + unidade.getDestino() + "','"

				+ unidade.getTem_nf() + "','" + unidade.getAutorizacao_movimentacao() + "','"

				+ LocalDateTime.now() + "','"
				

				+ unidade.getLogin().getId() + "','"
				
				+ unidade.getPeso_bruto() + "','"
				
				+ unidade.getPeso_tara() + "','"

				+ unidade.getPeso_liquido() + "')";

		try {
			Connection conn = ConexaoBanco.getConexao();
			Statement stmt = (Statement) conn.createStatement();
			int numero = stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);

			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				result = rs.getInt(1);
				System.out.println("Id Cliente inserido: " + result);
			}
			rs.close();
			stmt.close();
			return result;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao Marcar Caminhão para Embarque/Desembarque\nConsulte o Administrador\nErro: "
							+ e.getMessage() + "\nCausa: " + e.getCause());
			return -1;
		}

	}
	
	public int inserirCaminhaoNaFilaEmbarque(CadastroFilaMovimento unidade) {
		int result = -1;
		String sql = "insert into fila_embarque_desembarque\r\n"
				+ "(tipo_movimentacao, id_transportadora , id_produtor, id_motorista, id_veiculo, id_produto, status, notificacao_em_fila,notificado_entrada,notificado_saida"
				+ ",  umidade,impureza , ardidos,observacao ,origem ,destino ,tem_nf ,autorizacao_movimentacao ,data_hora_fila, id_login, peso_bruto, peso_tara, peso_liquido ) values ('"
				+ unidade.getTipo_movimentacao() + "','"
				
				+ unidade.getTransportadora().getId() + "','"
			
				+ unidade.getProdutor().getId() + "','"

				+ unidade.getMotorista().getId() + "','" + unidade.getVeiculo().getId_veiculo() + "','"
				
				+ unidade.getProduto().getId_produto() + "','" +

				+ unidade.getStatus() + "','"

				+ unidade.getNotificado_em_fila() + "','" + unidade.getNotificado_entrada() + "','"
				+ unidade.getNotificado_saida() + "','"

				+ unidade.getUmidade() + "','" + unidade.getImpureza() + "','" + unidade.getArdidos() + "','"

				+ unidade.getObservacao() + "','" + unidade.getOrigem() + "','" + unidade.getDestino() + "','"

				+ unidade.getTem_nf() + "','" + unidade.getAutorizacao_movimentacao() + "','"

				+ LocalDateTime.now() + "','"
				

				+ unidade.getLogin().getId() + "','"
				
				+ unidade.getPeso_bruto() + "','"
				
				+ unidade.getPeso_tara() + "','"

				+ unidade.getPeso_liquido() + "')";

		try {
			Connection conn = ConexaoBanco.getConexao();
			Statement stmt = (Statement) conn.createStatement();
			int numero = stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);

			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				result = rs.getInt(1);
				System.out.println("Id Cliente inserido: " + result);
			}
			rs.close();
			stmt.close();
			return result;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao Marcar Caminhão para Embarque/Desembarque\nConsulte o Administrador\nErro: "
							+ e.getMessage() + "\nCausa: " + e.getCause());
			return -1;
		}

	}

	public int getMaxId() {

		int maxid = -6;
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroFilaMovimento> fila = new ArrayList<CadastroFilaMovimento>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement("select max(id) as id_maximo from fila_embarque_desembarque");
			rs = pstm.executeQuery();
			rs.next();

			maxid = rs.getInt("id_maximo");

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			maxid+=5;
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao recuperar maximo id, erro: " + e.getMessage() + "\nCausa: " + e.getCause());
		}
		return maxid;
	}

	public ArrayList<CadastroFilaMovimento> getFila(int tipo, String data) {

		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroFilaMovimento> fila = new ArrayList<CadastroFilaMovimento>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement("call busca_fila_embarque_desembarque(?,?)");
			
			pstm.setInt(1, tipo);
			pstm.setString(2, data);

			rs = pstm.executeQuery();
					
			
			while (rs.next()) {
				CadastroFilaMovimento unidade = new CadastroFilaMovimento();

				unidade.setId(rs.getInt("id"));
				unidade.setTipo_movimentacao(rs.getInt("tipo_movimentacao"));

				// transportadora
				CadastroCliente transportadora = new CadastroCliente();
				transportadora.setNome_empresarial(rs.getString("nome_transportadora"));
				transportadora.setId(rs.getInt("id_transportadora"));
				transportadora.setTipo_pessoa(rs.getInt("tipo_transportadora"));
				unidade.setTransportadora(transportadora);

				// motorista
				CadastroCliente motorista = new CadastroCliente();
				motorista.setNome_empresarial(rs.getString("nome_motorista"));
				motorista.setId(rs.getInt("id_motorista"));
				motorista.setTipo_pessoa(rs.getInt("tipo_motorista"));
				unidade.setMotorista(motorista);

				// veiculo
				CadastroCliente.Veiculo veiculo = new CadastroCliente.Veiculo();
				veiculo.setPlaca_trator(rs.getString("placa"));
				veiculo.setId_veiculo(rs.getInt("id_veiculo"));
				unidade.setVeiculo(veiculo);

				// produtor
				CadastroCliente produtor = new CadastroCliente();
				produtor.setId(rs.getInt("id_produtor"));
				produtor.setNome_empresarial(rs.getString("nome_produtor"));
				produtor.setTipo_pessoa(rs.getInt("tipo_produtor"));
				unidade.setProdutor(produtor);

				// produto
				CadastroProduto produto = new CadastroProduto();
				produto.setNome_produto(rs.getString("nome_produto"));
				produto.setId_produto(rs.getInt("id_produto"));
				produto.setTransgenia(rs.getString("transgenia"));
				unidade.setProduto(produto);

				unidade.setNotificado_em_fila(rs.getInt("notificacao_em_fila"));
				unidade.setNotificado_entrada(rs.getInt("notificado_entrada"));
				unidade.setNotificado_saida(rs.getInt("notificado_saida"));

				unidade.setUmidade(rs.getDouble("umidade"));
				unidade.setImpureza(rs.getDouble("impureza"));
				unidade.setArdidos(rs.getDouble("ardidos"));

				unidade.setObservacao(rs.getString("observacao"));

				unidade.setOrigem(rs.getString("origem"));
				unidade.setDestino(rs.getString("destino"));

				unidade.setTem_nf(rs.getInt("tem_nf"));
				
				unidade.setMotivo(rs.getString("motivo"));

				unidade.setAutorizacao_movimentacao(rs.getInt("autorizacao_movimentacao"));

				unidade.setStatus(rs.getInt("status"));

				// login
				CadastroLogin login = new CadastroLogin();
				login.setNome(rs.getString("nome_usuario"));

				unidade.setLogin(login);

				// hora marcacao fila
				String hora_fila = rs.getString("data_hora_fila");
				Date data_hora_fila = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(hora_fila);
				unidade.setData_hora_fila(data_hora_fila);

				try {
					// hora entrada
					String hora_entrada = rs.getString("data_hora_entrada");
					Date data_hora_entrada = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(hora_entrada);
					unidade.setData_hora_entrada(data_hora_entrada);
				} catch (Exception e) {

				}
				try {
					// hora saida
					String hora_saida = rs.getString("data_hora_saida");
					Date data_hora_saida = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(hora_saida);
					unidade.setData_hora_saida(data_hora_saida);
				} catch (Exception e) {

				}
				
				
				//pesos
				try {
					unidade.setPeso_bruto(rs.getDouble("peso_bruto"));
					unidade.setPeso_tara(rs.getDouble("peso_tara"));
					unidade.setPeso_liquido(rs.getDouble("peso_liquido"));

				}catch(Exception e) {
					
				}
				

				fila.add(unidade);
			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao listar embarques/desembarques\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
		}
		return fila;
	}
	
	public ArrayList<CadastroFilaMovimento> getFila(int tipo ) {

		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroFilaMovimento> fila = new ArrayList<CadastroFilaMovimento>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement("call busca_toda_fila_embarque_desembarque(?)");
			pstm.setInt(1, tipo);

			rs = pstm.executeQuery();
					
			
			while (rs.next()) {
				CadastroFilaMovimento unidade = new CadastroFilaMovimento();

				unidade.setId(rs.getInt("id"));
				unidade.setTipo_movimentacao(rs.getInt("tipo_movimentacao"));

				// transportadora
				CadastroCliente transportadora = new CadastroCliente();
				transportadora.setNome_empresarial(rs.getString("nome_transportadora"));
				transportadora.setId(rs.getInt("id_transportadora"));
				transportadora.setTipo_pessoa(rs.getInt("tipo_transportadora"));
				unidade.setTransportadora(transportadora);

				// motorista
				CadastroCliente motorista = new CadastroCliente();
				motorista.setNome_empresarial(rs.getString("nome_motorista"));
				motorista.setId(rs.getInt("id_motorista"));
				motorista.setTipo_pessoa(rs.getInt("tipo_motorista"));
				unidade.setMotorista(motorista);

				// veiculo
				CadastroCliente.Veiculo veiculo = new CadastroCliente.Veiculo();
				veiculo.setPlaca_trator(rs.getString("placa"));
				veiculo.setId_veiculo(rs.getInt("id_veiculo"));
				unidade.setVeiculo(veiculo);

				// produtor
				CadastroCliente produtor = new CadastroCliente();
				produtor.setId(rs.getInt("id_produtor"));
				produtor.setNome_empresarial(rs.getString("nome_produtor"));
				produtor.setTipo_pessoa(rs.getInt("tipo_produtor"));
				produtor.setIe(rs.getString("ie_produtor"));
				produtor.setNome(rs.getString("nome"));
				produtor.setSobrenome(rs.getString("sobrenome"));
				unidade.setProdutor(produtor);

				// produto
				CadastroProduto produto = new CadastroProduto();
				produto.setNome_produto(rs.getString("nome_produto"));
				produto.setId_produto(rs.getInt("id_produto"));
				produto.setTransgenia(rs.getString("transgenia"));
				unidade.setProduto(produto);

				unidade.setNotificado_em_fila(rs.getInt("notificacao_em_fila"));
				unidade.setNotificado_entrada(rs.getInt("notificado_entrada"));
				unidade.setNotificado_saida(rs.getInt("notificado_saida"));

				unidade.setUmidade(rs.getDouble("umidade"));
				unidade.setImpureza(rs.getDouble("impureza"));
				unidade.setArdidos(rs.getDouble("ardidos"));

				unidade.setObservacao(rs.getString("observacao"));

				unidade.setOrigem(rs.getString("origem"));
				unidade.setDestino(rs.getString("destino"));

				unidade.setTem_nf(rs.getInt("tem_nf"));
				
				unidade.setMotivo(rs.getString("motivo"));

				unidade.setAutorizacao_movimentacao(rs.getInt("autorizacao_movimentacao"));

				unidade.setStatus(rs.getInt("status"));

				// login
				CadastroLogin login = new CadastroLogin();
				login.setNome(rs.getString("nome_usuario"));

				unidade.setLogin(login);

				// hora marcacao fila
				String hora_fila = rs.getString("data_hora_fila");
				Date data_hora_fila = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(hora_fila);
				unidade.setData_hora_fila(data_hora_fila);
				
				DateFormat outputFormatter = new SimpleDateFormat("dd/MM/yyyy");
				String output = outputFormatter.format(unidade.getData_hora_fila());

			
				Date date;
				try {
					date = new SimpleDateFormat("dd/MM/yyyy").parse(output);
					unidade.setSomente_data_fila(date);
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	
				
				try {
					// hora entrada
					String hora_entrada = rs.getString("data_hora_entrada");
					Date data_hora_entrada = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(hora_entrada);
					unidade.setData_hora_entrada(data_hora_entrada);
				} catch (Exception e) {

				}
				try {
					// hora saida
					String hora_saida = rs.getString("data_hora_saida");
					Date data_hora_saida = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(hora_saida);
					unidade.setData_hora_saida(data_hora_saida);
				} catch (Exception e) {

				}
				
				
				//pesos
				try {
					unidade.setPeso_bruto(rs.getDouble("peso_bruto"));
					unidade.setPeso_tara(rs.getDouble("peso_tara"));
					unidade.setPeso_liquido(rs.getDouble("peso_liquido"));

				}catch(Exception e) {
					
				}
				

				fila.add(unidade);
			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao listar embarques/desembarques\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
		}
		return fila;
	}
	
	
	public ArrayList<CadastroFilaMovimento> getFilaMaisRapido(int tipo ) {

		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroFilaMovimento> fila = new ArrayList<CadastroFilaMovimento>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement("call busca_toda_fila_embarque_desembarque_mais_rapido(?)");
			pstm.setInt(1, tipo);

			rs = pstm.executeQuery();
					
			
			while (rs.next()) {
				CadastroFilaMovimento unidade = new CadastroFilaMovimento();

				unidade.setId(rs.getInt("id"));
				unidade.setTipo_movimentacao(rs.getInt("tipo_movimentacao"));

				// transportadora
				CadastroCliente transportadora = new CadastroCliente();
				transportadora.setNome_empresarial(rs.getString("nome_transportadora"));
				transportadora.setId(rs.getInt("id_transportadora"));
				transportadora.setTipo_pessoa(rs.getInt("tipo_transportadora"));
				unidade.setTransportadora(transportadora);

				// motorista
				CadastroCliente motorista = new CadastroCliente();
				motorista.setNome_empresarial(rs.getString("nome_motorista"));
				motorista.setId(rs.getInt("id_motorista"));
				motorista.setTipo_pessoa(rs.getInt("tipo_motorista"));
				unidade.setMotorista(motorista);

				// veiculo
				CadastroCliente.Veiculo veiculo = new CadastroCliente.Veiculo();
				veiculo.setPlaca_trator(rs.getString("veiculo_placa"));
				veiculo.setId_veiculo(rs.getInt("veiculo_id"));
				unidade.setVeiculo(veiculo);

				// produtor
				CadastroCliente produtor = new CadastroCliente();
				produtor.setId(rs.getInt("id_produtor"));
				produtor.setNome_empresarial(rs.getString("nome_produtor"));
				produtor.setTipo_pessoa(rs.getInt("tipo_produtor"));
				produtor.setIe(rs.getString("ie_produtor"));
				produtor.setNome(rs.getString("nome"));
				produtor.setSobrenome(rs.getString("sobrenome"));
				unidade.setProdutor(produtor);

				// produto
				CadastroProduto produto = new CadastroProduto();
				produto.setNome_produto(rs.getString("nome_produto"));
				produto.setId_produto(rs.getInt("id_produto"));
				produto.setTransgenia(rs.getString("transgenia"));
				unidade.setProduto(produto);

				unidade.setNotificado_em_fila(rs.getInt("notificacao_em_fila"));
				unidade.setNotificado_entrada(rs.getInt("notificado_entrada"));
				unidade.setNotificado_saida(rs.getInt("notificado_saida"));

				unidade.setUmidade(rs.getDouble("umidade"));
				unidade.setImpureza(rs.getDouble("impureza"));
				unidade.setArdidos(rs.getDouble("ardidos"));

				unidade.setObservacao(rs.getString("observacao"));

				unidade.setOrigem(rs.getString("origem"));
				unidade.setDestino(rs.getString("destino"));

				unidade.setTem_nf(rs.getInt("tem_nf"));
				
				unidade.setMotivo(rs.getString("motivo"));

				unidade.setAutorizacao_movimentacao(rs.getInt("autorizacao_movimentacao"));

				unidade.setStatus(rs.getInt("status"));

				// login
				CadastroLogin login = new CadastroLogin();
				login.setNome(rs.getString("nome_usuario"));

				unidade.setLogin(login);

				// hora marcacao fila
				String hora_fila = rs.getString("data_hora_fila");
				Date data_hora_fila = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(hora_fila);
				unidade.setData_hora_fila(data_hora_fila);
				
				DateFormat outputFormatter = new SimpleDateFormat("dd/MM/yyyy");
				String output = outputFormatter.format(unidade.getData_hora_fila());

			
				Date date;
				try {
					date = new SimpleDateFormat("dd/MM/yyyy").parse(output);
					unidade.setSomente_data_fila(date);
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	
				
				try {
					// hora entrada
					String hora_entrada = rs.getString("data_hora_entrada");
					Date data_hora_entrada = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(hora_entrada);
					unidade.setData_hora_entrada(data_hora_entrada);
				} catch (Exception e) {

				}
				try {
					// hora saida
					String hora_saida = rs.getString("data_hora_saida");
					Date data_hora_saida = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(hora_saida);
					unidade.setData_hora_saida(data_hora_saida);
				} catch (Exception e) {

				}
				
				
				//pesos
				try {
					unidade.setPeso_bruto(rs.getDouble("peso_bruto"));
					unidade.setPeso_tara(rs.getDouble("peso_tara"));
					unidade.setPeso_liquido(rs.getDouble("peso_liquido"));

				}catch(Exception e) {
					
				}
				
				//romaneio
				CadastroRomaneio rom = new CadastroRomaneio();
				 
	        	rom.setId_romaneio(rs.getInt("id_romaneio"));
	        	rom.setNumero_romaneio(rs.getInt("codigo"));
	        	
	        	rom.setCaminho_arquivo(rs.getString("caminho_arquivo"));
				
	        	unidade.setRomaneio(rom);

				fila.add(unidade);
			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao listar embarques/desembarques\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
		}
		return fila;
	}
	
	
	
	public CadastroFilaMovimento getUnidadeDesembarque(int id) {

		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement("select * from fila_embarque_desembarque where id = ?");
			
			pstm.setInt(1,  id);

			rs = pstm.executeQuery();
			GerenciarBancoClientes gerente = new GerenciarBancoClientes();
			GerenciarBancoProdutos gerente_prod = new GerenciarBancoProdutos();
			CadastroFilaMovimento unidade = new CadastroFilaMovimento();
			GerenciarBancoLogin gerenteLogin = new GerenciarBancoLogin();

			rs.next();

				unidade.setId(rs.getInt("id"));
				unidade.setTipo_movimentacao(rs.getInt("tipo_movimentacao"));


				// motorista
				CadastroCliente motorista = new CadastroCliente();
				motorista = gerente.getCliente(rs.getInt("id_motorista"));
				unidade.setMotorista(motorista);

				// veiculo
				CadastroCliente.Veiculo veiculo = new CadastroCliente.Veiculo();
				veiculo = gerente.getVeiculo(rs.getInt("id_veiculo"));
				unidade.setVeiculo(veiculo);

				// produtor
				CadastroCliente produtor = new CadastroCliente();
				produtor = gerente.getCliente(rs.getInt("id_produtor"));
				unidade.setProdutor(produtor);

				// produto
				CadastroProduto produto = new CadastroProduto();
				produto = gerente_prod.getProduto(rs.getInt("id_produto"));
				unidade.setProduto(produto);

				unidade.setNotificado_em_fila(rs.getInt("notificacao_em_fila"));
				unidade.setNotificado_entrada(rs.getInt("notificado_entrada"));
				unidade.setNotificado_saida(rs.getInt("notificado_saida"));

				unidade.setUmidade(rs.getDouble("umidade"));
				unidade.setImpureza(rs.getDouble("impureza"));
				unidade.setArdidos(rs.getDouble("ardidos"));

				unidade.setObservacao(rs.getString("observacao"));

				unidade.setOrigem(rs.getString("origem"));
				unidade.setDestino(rs.getString("destino"));

				unidade.setTem_nf(rs.getInt("tem_nf"));
				
				unidade.setMotivo(rs.getString("motivo"));

				unidade.setAutorizacao_movimentacao(rs.getInt("autorizacao_movimentacao"));

				unidade.setStatus(rs.getInt("status"));

				// login
				CadastroLogin login = new CadastroLogin();
				login = gerenteLogin.getLogin(rs.getInt("id_login"));

				unidade.setLogin(login);

				// hora marcacao fila
				String hora_fila = rs.getString("data_hora_fila");
				Date data_hora_fila = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(hora_fila);
				unidade.setData_hora_fila(data_hora_fila);

				try {
					// hora entrada
					String hora_entrada = rs.getString("data_hora_entrada");
					Date data_hora_entrada = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(hora_entrada);
					unidade.setData_hora_entrada(data_hora_entrada);
				} catch (Exception e) {

				}
				try {
					// hora saida
					String hora_saida = rs.getString("data_hora_saida");
					Date data_hora_saida = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(hora_saida);
					unidade.setData_hora_saida(data_hora_saida);
				} catch (Exception e) {

				}
				
				
				//pesos
				try {
					unidade.setPeso_bruto(rs.getDouble("peso_bruto"));
					unidade.setPeso_tara(rs.getDouble("peso_tara"));
					unidade.setPeso_liquido(rs.getDouble("peso_liquido"));

				}catch(Exception e) {
					
				}
				
				ConexaoBanco.fechaConexao(conn, pstm, rs);

				return unidade;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao listar embarques/desembarques\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
			return null;
		}
	}
	
	public CadastroFilaMovimento getUnidadeEmbarque(int id) {

		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement("select * from fila_embarque_desembarque where id = ?");
			
			pstm.setInt(1,  id);

			rs = pstm.executeQuery();
			GerenciarBancoClientes gerente = new GerenciarBancoClientes();
			GerenciarBancoProdutos gerente_prod = new GerenciarBancoProdutos();
			CadastroFilaMovimento unidade = new CadastroFilaMovimento();
			GerenciarBancoLogin gerenteLogin = new GerenciarBancoLogin();

			rs.next();

				unidade.setId(rs.getInt("id"));
				unidade.setTipo_movimentacao(rs.getInt("tipo_movimentacao"));

				// transportadora
				CadastroCliente transportadora = new CadastroCliente();
				transportadora = gerente.getCliente(rs.getInt("id_transportadora"));
				unidade.setTransportadora(transportadora);

				
				// cliente
				CadastroCliente cliente = new CadastroCliente();
				cliente = gerente.getCliente(rs.getInt("id_produtor"));
				unidade.setProdutor(cliente);
				
				// motorista
				CadastroCliente motorista = new CadastroCliente();
				motorista = gerente.getCliente(rs.getInt("id_motorista"));
				unidade.setMotorista(motorista);

				// veiculo
				CadastroCliente.Veiculo veiculo = new CadastroCliente.Veiculo();
				veiculo = gerente.getVeiculo(rs.getInt("id_veiculo"));
				unidade.setVeiculo(veiculo);

			
				// produto
				CadastroProduto produto = new CadastroProduto();
				produto = gerente_prod.getProduto(rs.getInt("id_produto"));
				unidade.setProduto(produto);

				unidade.setNotificado_em_fila(rs.getInt("notificacao_em_fila"));
				unidade.setNotificado_entrada(rs.getInt("notificado_entrada"));
				unidade.setNotificado_saida(rs.getInt("notificado_saida"));

				unidade.setUmidade(rs.getDouble("umidade"));
				unidade.setImpureza(rs.getDouble("impureza"));
				unidade.setArdidos(rs.getDouble("ardidos"));

				unidade.setObservacao(rs.getString("observacao"));

				unidade.setOrigem(rs.getString("origem"));
				unidade.setDestino(rs.getString("destino"));

				unidade.setTem_nf(rs.getInt("tem_nf"));
				
				unidade.setMotivo(rs.getString("motivo"));

				unidade.setAutorizacao_movimentacao(rs.getInt("autorizacao_movimentacao"));

				unidade.setStatus(rs.getInt("status"));

				// login
				CadastroLogin login = new CadastroLogin();
				login = gerenteLogin.getLogin(rs.getInt("id_login"));

				unidade.setLogin(login);

				// hora marcacao fila
				String hora_fila = rs.getString("data_hora_fila");
				Date data_hora_fila = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(hora_fila);
				unidade.setData_hora_fila(data_hora_fila);

				try {
					// hora entrada
					String hora_entrada = rs.getString("data_hora_entrada");
					Date data_hora_entrada = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(hora_entrada);
					unidade.setData_hora_entrada(data_hora_entrada);
				} catch (Exception e) {

				}
				try {
					// hora saida
					String hora_saida = rs.getString("data_hora_saida");
					Date data_hora_saida = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(hora_saida);
					unidade.setData_hora_saida(data_hora_saida);
				} catch (Exception e) {

				}
				
				
				//pesos
				try {
					unidade.setPeso_bruto(rs.getDouble("peso_bruto"));
					unidade.setPeso_tara(rs.getDouble("peso_tara"));
					unidade.setPeso_liquido(rs.getDouble("peso_liquido"));

				}catch(Exception e) {
					
				}
				
				ConexaoBanco.fechaConexao(conn, pstm, rs);

				return unidade;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao listar embarques\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
			return null;
		}
	}
	
	
	

	public CadastroRomaneio getRomaneio(CadastroFilaMovimento unidade) {

		//`busca_ticket`(in data_saida_completa varchar(100), in id_cliente int(10) , in prod_id int(3), in nome_motor varchar(200), in placa_veic varchar(40))
		
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		
		
		String strDate = dateFormat.format(unidade.getData_hora_saida());  
	    System.out.println(strDate);
	    
		String nome_motorista = "";
		CadastroCliente motorista = unidade.getMotorista();
		if (motorista.getTipo_pessoa() == 0) {
			// pessoa fisica
			nome_motorista = motorista.getNome_empresarial().toUpperCase().trim();

		} else {
			nome_motorista= motorista.getNome_fantaia().toUpperCase().trim();

		}

		
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement("call busca_ticket(?,?,?,?,?)");
			
			System.out.println(strDate);
			System.out.println(unidade.getProdutor().getId());
			System.out.println(unidade.getProduto().getId_produto());
			System.out.println(nome_motorista + "%");
			System.out.println(unidade.getVeiculo().getPlaca_trator());

			pstm.setString(1,  strDate);
			pstm.setInt(2, unidade.getProdutor().getId());
			pstm.setInt(3, unidade.getProduto().getId_produto());
			pstm.setString(4, nome_motorista + "%");
			pstm.setString(5,  unidade.getVeiculo().getPlaca_trator());

			rs = pstm.executeQuery();
		

			rs.next();
			
			CadastroRomaneio rom = new CadastroRomaneio();
			 
        	rom.setId_romaneio(rs.getInt("id_romaneio"));
        	rom.setNumero_romaneio(rs.getInt("codigo"));
        	
        	rom.setCaminho_arquivo(rs.getString("caminho_arquivo"));

        	return rom;

		} catch (Exception e) {
			return null;
		}
	}

	public boolean emFilaNotificado(int id_unidade) {

		String sql_update_aditivo = "update fila_embarque_desembarque set notificacao_em_fila = 1 where id = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_update_aditivo);

			pstm.setInt(1, id_unidade);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			return true;

		} catch (Exception f) {
			f.printStackTrace();
			return false;
		}
	}

	public boolean saidaNotificado(int id_unidade) {

		String sql_update_aditivo = "update fila_embarque_desembarque set notificado_saida = 1, status = 2, data_hora_saida = ? where id = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_update_aditivo);

			LocalDateTime currentDateTime = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

			pstm.setString(1, currentDateTime.format(formatter));
			pstm.setInt(2, id_unidade);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			return true;

		} catch (Exception f) {
			f.printStackTrace();
			return false;
		}
	}
	
	public boolean saidaAtualizada(int id_unidade) {

		String sql_update_aditivo = "update fila_embarque_desembarque set status = 2, data_hora_saida = ? where id = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_update_aditivo);

			LocalDateTime currentDateTime = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

			pstm.setString(1, currentDateTime.format(formatter));
			pstm.setInt(2, id_unidade);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			return true;

		} catch (Exception f) {
			f.printStackTrace();
			return false;
		}
	}

	public boolean entradaNotificada(int id_unidade) {

		String sql_update_aditivo = "update fila_embarque_desembarque set notificado_entrada = 1, status = 1 , data_hora_entrada = ? where id = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_update_aditivo);

			LocalDateTime currentDateTime = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

			pstm.setString(1, currentDateTime.format(formatter));
			pstm.setInt(2, id_unidade);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			return true;

		} catch (Exception f) {
			f.printStackTrace();
			return false;
		}
	}
	
	public boolean entradaAtualizada(int id_unidade) {

		String sql_update_aditivo = "update fila_embarque_desembarque set status = 1 , data_hora_entrada = ? where id = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_update_aditivo);

			LocalDateTime currentDateTime = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

			pstm.setString(1, currentDateTime.format(formatter));
			pstm.setInt(2, id_unidade);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			return true;

		} catch (Exception f) {
			f.printStackTrace();
			return false;
		}
	}


	public boolean atualizarId(int novo_id, int id_antigo) {

		String sql_update_aditivo = "update fila_embarque_desembarque set id = ? where id = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_update_aditivo);

			pstm.setInt(1, novo_id);
			pstm.setInt(2, id_antigo);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null, "Erro ao mudar id da unidade da fila, erro: " + f.getMessage() + "\nCausa: " + f.getCause() );
			f.printStackTrace();
			return false;
		}
	}
	
	
	public boolean atualizarStatus(int id, int status) {

		String sql_update_aditivo = "update fila_embarque_desembarque set status = ? where id = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_update_aditivo);

			pstm.setInt(1, status);
			pstm.setInt(2, id);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			return true;

		} catch (Exception f) {
			f.printStackTrace();
			return false;
		}
	}
	
	public boolean cancelar(int id, String motivo) {

		String sql_update_aditivo = "update fila_embarque_desembarque set status = -1, motivo = ? where id = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_update_aditivo);

			pstm.setString(1, motivo);
			pstm.setInt(2, id);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			return true;

		} catch (Exception f) {
			f.printStackTrace();
			return false;
		}
	}
	
	
	public boolean atualizarUnidade(CadastroFilaMovimento unidade) {

		String sql_update_aditivo = "update fila_embarque_desembarque set id_transportadora = ?,"
				+ " id_motorista = ?, id_veiculo = ?,"
				+ " id_produtor = ?, id_produto = ?,"
				+ " umidade = ?, impureza = ?, ardidos = ?,"
				+ " observacao = ?, "
				+ "origem = ?, destino = ?,"
				+ " tem_nf = ?, autorizacao_movimentacao = ?,"
				+ " peso_bruto = ?, peso_tara = ?, peso_liquido = ? where id = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_update_aditivo);

			pstm.setInt(1, unidade.getTransportadora().getId());
			pstm.setInt(2, unidade.getMotorista().getId());
			pstm.setInt(3, unidade.getVeiculo().getId_veiculo());
			pstm.setInt(4, unidade.getProdutor().getId());
			pstm.setInt(5, unidade.getProduto().getId_produto());
		
			pstm.setDouble(6, unidade.getUmidade());
			pstm.setDouble(7, unidade.getImpureza());
			pstm.setDouble(8, unidade.getArdidos());

			pstm.setString(9, unidade.getObservacao());

			pstm.setString(10, unidade.getOrigem());
			pstm.setString(11, unidade.getDestino());

			pstm.setInt(12, unidade.getTem_nf());
			pstm.setInt(13, unidade.getAutorizacao_movimentacao());

			pstm.setDouble(14, unidade.getPeso_bruto());
			pstm.setDouble(15, unidade.getPeso_tara());
			pstm.setDouble(16, unidade.getPeso_liquido());
			
			pstm.setDouble(17, unidade.getId());



			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			return true;

		} catch (Exception f) {
			f.printStackTrace();
			return false;
		}
	}
	
	
	public boolean atualizarUnidadeEmbarque(CadastroFilaMovimento unidade) {

		String sql_update_aditivo = "update fila_embarque_desembarque set id_transportadora = ?,"
				+ " id_motorista = ?, id_veiculo = ?,"
				+ " id_produtor = ?,"
				+ " id_produto = ?,"
				+ " umidade = ?, impureza = ?, ardidos = ?,"
				+ " observacao = ?, "
				+ "origem = ?, destino = ?,"
				+ " tem_nf = ?, autorizacao_movimentacao = ?,"
				+ " peso_bruto = ?, peso_tara = ?, peso_liquido = ? where id = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_update_aditivo);

			pstm.setInt(1, unidade.getTransportadora().getId());
			pstm.setInt(2, unidade.getMotorista().getId());
			pstm.setInt(3, unidade.getVeiculo().getId_veiculo());
			pstm.setInt(4, unidade.getProdutor().getId());

			pstm.setInt(5, unidade.getProduto().getId_produto());
		
			pstm.setDouble(6, unidade.getUmidade());
			pstm.setDouble(7, unidade.getImpureza());
			pstm.setDouble(8, unidade.getArdidos());

			pstm.setString(9, unidade.getObservacao());

			pstm.setString(10, unidade.getOrigem());
			pstm.setString(11, unidade.getDestino());

			pstm.setInt(12, unidade.getTem_nf());
			pstm.setInt(13, unidade.getAutorizacao_movimentacao());

			pstm.setDouble(14, unidade.getPeso_bruto());
			pstm.setDouble(15, unidade.getPeso_tara());
			pstm.setDouble(16, unidade.getPeso_liquido());
			
			pstm.setDouble(17, unidade.getId());



			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			return true;

		} catch (Exception f) {
			f.printStackTrace();
			return false;
		}
	}




}
