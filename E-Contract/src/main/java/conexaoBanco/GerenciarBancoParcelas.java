package main.java.conexaoBanco;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.java.cadastros.Parcela;


public class GerenciarBancoParcelas {

	public String sql_dado (Parcela dado) {
		/*
		 * CREATE TABLE `parcela` (
  `id_parcela` int(5) NOT NULL AUTO_INCREMENT,
  id_lancamento_pai int (5),
  `valor` varchar(40) DEFAULT NULL,
  `data_vencimento` varchar(40) DEFAULT NULL,
  `status` int(3) DEFAULT NULL,
  `observacao` text DEFAULT NULL,
  `descricao` text DEFAULT NULL,
  caminho_arquivo text,
  PRIMARY KEY (`id_parcela`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
		 */
		return "insert into parcela (id_lancamento_pai, identificador, valor, data_vencimento,fluxo_caixa,"
				+ " status, observacao, descricao,caminho_arquivo ) values ('"
				+ dado.getId_lancamento_pai() + "','"
				+ dado.getIdentificador() + "','"
				+ dado.getValor() + "','"
				+ dado.getData_vencimento() + "','" 
				+ dado.getFluxo_caixa() + "','" 
				+ dado.getStatus() + "','" 
			    + dado.getObservacao() + "','" 
			    + dado.getDescricao() + "','" 
			    + dado.getCaminho_arquivo() + "')";
	}
	


	public int inserirParcela(Parcela dado) {
		int result = -1;
		int id_cliente = -1;
		if (dado != null) {
			Connection conn = null;
			try {

				conn = ConexaoBanco.getConexao();

				String query = sql_dado(dado);
				Statement stmt = (Statement) conn.createStatement();
				int numero = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					result = rs.getInt(1);
					System.out.println("Id da parcela inserido: " + result);
				}
				rs.close();
				stmt.close();

				return result;

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao inserir a parcela no banco de dados, Erro: " + e.getMessage() + "\nCausa: " + e.getCause()) ;

				return -1;
			}
		} else {
			System.out.println("O parametro enviado por parametro esta vazio");
			return -1;
		}
	}

	public ArrayList<Parcela> getParcelas() {
		String select = "select * from parcela";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<Parcela> lista = new ArrayList<>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(select);

			rs = pstm.executeQuery();
			while (rs.next()) {
				Parcela dado = new Parcela();
				
				dado.setId_parcela(rs.getInt("id_parcela"));
				dado.setId_lancamento_pai(rs.getInt("id_lancamento_pai"));
				dado.setIdentificador(rs.getString("identificador"));
				dado.setFluxo_caixa(rs.getInt("fluxo_caixa"));
				try{
					dado.setValor(new BigDecimal(rs.getString("valor")));
				}catch(Exception e) {
					dado.setValor(BigDecimal.ZERO);
				}
				dado.setStatus(rs.getInt("status"));
				dado.setData_vencimento(rs.getString("data_vencimento"));
				dado.setDescricao(rs.getString("descricao"));
				dado.setObservacao(rs.getString("observacao"));
				dado.setCaminho_arquivo(rs.getString("caminho_arquivo"));
			

				lista.add(dado);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar a parcela \nErro: " + e.getMessage() + "\nCausa: " + e.getCause());// );
		}
		return lista;

	}
	
	public ArrayList<Parcela> getParcelasPorLancamento(int id_lancamento) {
		String select = "select * from parcela where id_lancamento_pai = ? ";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<Parcela> lista = new ArrayList<>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(select);
			pstm.setInt(1, id_lancamento);

			rs = pstm.executeQuery();
			while (rs.next()) {
				Parcela dado = new Parcela();
				
				dado.setId_parcela(rs.getInt("id_parcela"));
				dado.setId_lancamento_pai(rs.getInt("id_lancamento_pai"));
				dado.setIdentificador(rs.getString("identificador"));
				dado.setFluxo_caixa(rs.getInt("fluxo_caixa"));

				try{
					dado.setValor(new BigDecimal(rs.getString("valor")));
				}catch(Exception e) {
					dado.setValor(BigDecimal.ZERO);
				}
				dado.setStatus(rs.getInt("status"));
				dado.setData_vencimento(rs.getString("data_vencimento"));
				dado.setDescricao(rs.getString("descricao"));
				dado.setObservacao(rs.getString("observacao"));
				dado.setCaminho_arquivo(rs.getString("caminho_arquivo"));
			

				lista.add(dado);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar a parcela \nErro: " + e.getMessage() + "\nCausa: " + e.getCause());// );
		}
		return lista;

	}


	public Parcela getParcela(int id) {

		String select = "select * from parcela where id_parcela = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Parcela dado = new Parcela();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(select);
			pstm.setInt(1, id);

			rs = pstm.executeQuery();
			rs.next();

			dado.setId_parcela(rs.getInt("id_parcela"));
			dado.setId_lancamento_pai(rs.getInt("id_lancamento_pai"));
			dado.setIdentificador(rs.getString("identificador"));
			dado.setFluxo_caixa(rs.getInt("fluxo_caixa"));


			try{
				dado.setValor(new BigDecimal(rs.getString("valor")));
			}catch(Exception e) {
				dado.setValor(BigDecimal.ZERO);
			}
			dado.setStatus(rs.getInt("status"));
			dado.setData_vencimento(rs.getString("data_vencimento"));
			dado.setDescricao(rs.getString("descricao"));
			dado.setObservacao(rs.getString("observacao"));
			dado.setCaminho_arquivo(rs.getString("caminho_arquivo"));
			
			
			return dado;

		} catch (Exception e) {
			//JOptionPane.showMessageDialog(null, "Erro ao listar a Conta id: " + id);// );
			JOptionPane.showMessageDialog(null, "Erro ao listar a parcela id: " + id + " erro: " + e.getCause() + "\ncausa: " + e.getMessage());
			return null;
		}

	}

	public boolean removerParcela(int id) {
		String delete = "DELETE FROM Parcela WHERE id_parcela = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(delete);

			pstm.setInt(1, id);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			JOptionPane.showMessageDialog(null, "Parcela Excluída, banco normalizado ");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao excluir a parcela do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
							+ "dados " + f.getMessage());
			return false;
		}

	}

	public boolean atualizarParcela(Parcela dado) {
		if (dado != null) {
			try {
				Connection conn = null;
				String atualizar = null;
				PreparedStatement pstm;

				//atualizar = "update financeiro_conta set nome_conta = ?, id_grupo_contas = ?,  tipo_conta = ?, observacao = ?,descricao = ? where id_conta = ? ";
				atualizar = "update parcela set identificador = ? , valor = ?, "
						+ "data_vencimento = ?, fluxo_caixa = ?, status = ?,"
						+ " observacao = ?, descricao = ?  where id_parcela = ?";
				
				conn = ConexaoBanco.getConexao();
				pstm = conn.prepareStatement(atualizar);
				pstm.setString(1, dado.getIdentificador());
				pstm.setString(2, dado.getValor().toString());
				pstm.setString(3, dado.getData_vencimento());
				pstm.setInt(4, dado.getFluxo_caixa());

				pstm.setInt(5, dado.getStatus());
				pstm.setString(6, dado.getObservacao());
				pstm.setString(7, dado.getDescricao());
				pstm.setInt(8, dado.getId_parcela());

			

				pstm.execute();
				// JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
				System.out.println("Parcela Atualizada com sucesso");
				ConexaoBanco.fechaConexao(conn);
				return true;
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao atualizar a parcela no banco de dados\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(null, "Os parametros estão vazios");
			return false;
		}
	}

	public boolean atualizarArquivoDaParcela(String caminho_arquivo, int id_parcela) {

			try {
				Connection conn = null;
				String atualizar = null;
				PreparedStatement pstm;

				//atualizar = "update financeiro_conta set nome_conta = ?, id_grupo_contas = ?,  tipo_conta = ?, observacao = ?,descricao = ? where id_conta = ? ";
				atualizar = "update parcela set caminho_arquivo = ?  where id_parcela = ?";
				
				conn = ConexaoBanco.getConexao();
				pstm = conn.prepareStatement(atualizar);
				pstm.setString(1, caminho_arquivo);
				pstm.setInt(2, id_parcela);

			

				pstm.execute();
				// JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
				ConexaoBanco.fechaConexao(conn);
				return true;
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao atualizar a parcela no banco de dados\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
				return false;
			}
		
	}
	
	public boolean atualizarStatusParcela(int status, int id_parcela) {
		
			try {
				Connection conn = null;
				String atualizar = null;
				PreparedStatement pstm;

				//atualizar = "update financeiro_conta set nome_conta = ?, id_grupo_contas = ?,  tipo_conta = ?, observacao = ?,descricao = ? where id_conta = ? ";
				atualizar = "update parcela set status = ?  where id_parcela = ?";
				
				conn = ConexaoBanco.getConexao();
				pstm = conn.prepareStatement(atualizar);
				pstm.setInt(1, status);
				pstm.setInt(2, id_parcela);

			

				pstm.execute();
				// JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
				System.out.println("Parcela Atualizada com sucesso");
				ConexaoBanco.fechaConexao(conn);
				return true;
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao atualizar a parcela no banco de dados\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
				return false;
			}
		
	}
	
	
}
