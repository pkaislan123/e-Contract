package main.java.conexaoBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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

public class GerenciarBancoFilaMovimento {

	public int inserirCaminhaoNaFila(CadastroFilaMovimento unidade) {
		int result = -1;
		String sql = "insert into fila_embarque_desembarque\r\n"
				+ "(tipo_movimentacao, id_transportadora , id_motorista, id_veiculo, id_produtor, id_produto, status, notificacao_em_fila,notificado_entrada,notificado_saida"
				+ ",  umidade,impureza , ardidos,observacao ,origem ,destino ,tem_nf ,autorizacao_movimentacao ,data_hora_fila, id_login ) values ('"
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

				+ unidade.getLogin().getId() + "')";

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

	public ArrayList<CadastroFilaMovimento> getFila(String data) {

		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroFilaMovimento> fila = new ArrayList<CadastroFilaMovimento>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement("call busca_fila_embarque_desembarque(?)");
			
			pstm.setString(1, data);

			rs = pstm.executeQuery();
					
			
			while (rs.next()) {
				CadastroFilaMovimento unidade = new CadastroFilaMovimento();

				unidade.setId(rs.getInt("id"));
				unidade.setTipo_movimentacao(rs.getInt("tipo_movimentacao"));

				// transportadora
				CadastroCliente transportadora = new CadastroCliente();
				transportadora.setNome_empresarial(rs.getString("nome_transportadora"));
				unidade.setTransportadora(transportadora);

				// motorista
				CadastroCliente motorista = new CadastroCliente();
				motorista.setNome_empresarial(rs.getString("nome_motorista"));
				unidade.setMotorista(motorista);

				// veiculo
				CadastroCliente.Veiculo veiculo = new CadastroCliente.Veiculo();
				veiculo.setPlaca_trator(rs.getString("placa"));
				unidade.setVeiculo(veiculo);

				// produtor
				CadastroCliente produtor = new CadastroCliente();
				produtor.setId(rs.getInt("id_produtor"));
				produtor.setNome_empresarial(rs.getString("nome_produtor"));
				unidade.setProdutor(produtor);

				// produto
				CadastroProduto produto = new CadastroProduto();
				produto.setNome_produto(rs.getString("nome_produto"));
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

				fila.add(unidade);
			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao listar embarques/desembarques\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
		}
		return fila;
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

}
