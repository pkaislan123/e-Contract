package main.java.conexaoBanco;

import main.java.cadastros.CadastroAditivo;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CentroCusto;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

public class GerenciarBancoCentroCustos {

	public String sql_dado (CentroCusto dado) {
		return "insert into centro_custo (nome_centro_custo, observacao, descricao, id_cliente) values ('"
				+ dado.getNome_centro_custo() + "','" 
				+ dado.getObservacao() + "','"
				+ dado.getDescricao() + "','"
				+ dado.getId_cliente()+ "')";
	}

	public int inserirCentroCusto(CentroCusto dado) {
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
					System.out.println("Id centro custo inserido: " + result);
				}
				rs.close();
				stmt.close();

				return result;

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao inserir o centro de custo no banco de dados, Erro: " + e.getMessage() + "\nCausa: " + e.getCause()) ;

				return -1;
			}
		} else {
			System.out.println("O parametro enviado por parametro esta vazio");
			return -1;
		}
	}

	public ArrayList<CentroCusto> getCentroCustos() {
		String select = "select * from centro_custo";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CentroCusto> lista = new ArrayList<>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(select);

			rs = pstm.executeQuery();
			while (rs.next()) {
				CentroCusto dado = new CentroCusto();

				dado.setId_centro_custo(rs.getInt("id_centro_custo"));
				dado.setNome_centro_custo(rs.getString("nome_centro_custo"));
				dado.setObservacao(rs.getString("observacao"));
				dado.setDescricao(rs.getString("descricao"));
				dado.setId_cliente(rs.getInt("id_cliente"));
			

				lista.add(dado);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar centro custo");// );
		}
		return lista;

	}

	public CentroCusto getCentroCusto(int id) {

		String select = "select * from centro_custo where id_centro_custo = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		CentroCusto dado = new CentroCusto();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(select);
			pstm.setInt(1, id);

			rs = pstm.executeQuery();
			rs.next();

			dado.setId_centro_custo(rs.getInt("id_centro_custo"));
			dado.setNome_centro_custo(rs.getString("nome_centro_custo"));
			dado.setObservacao(rs.getString("observacao"));
			dado.setDescricao(rs.getString("descricao"));
			dado.setId_cliente(rs.getInt("id_cliente"));
			
			return dado;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar centro de custo id: " + id);// );
			System.out.println("Erro ao listar centro de custo id: " + id + " erro: " + "\ncausa: ");
			return null;
		}

	}

	public boolean removerCentroCusto(int id_centro_custo) {
		String delete = "DELETE FROM centro_custo WHERE id_centro_custo = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(delete);

			pstm.setInt(1, id_centro_custo);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			JOptionPane.showMessageDialog(null, "Centro de Custo excluido, banco normalizado ");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao exlcuir o Centro de Custo do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
							+ "dados " + f.getMessage());
			return false;
		}

	}

	public boolean atualizarCentroCusto(CentroCusto dado) {
		if (dado != null) {
			try {
				Connection conn = null;
				String atualizar = null;
				PreparedStatement pstm;

				atualizar = "update centro_custo set nome_centro_custo = ?, observacao = ?,descricao = ? , id_cliente = ? where id_centro_custo = ? ";
				conn = ConexaoBanco.getConexao();
				pstm = conn.prepareStatement(atualizar);

				pstm.setString(1, dado.getNome_centro_custo());
				pstm.setString(2, dado.getObservacao());
				pstm.setString(3, dado.getDescricao());
				pstm.setInt(4, dado.getId_cliente());
			
				pstm.setInt(5, dado.getId_centro_custo());

				pstm.execute();
				// JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
				System.out.println("Centro de Custo Atualizado com sucesso");
				ConexaoBanco.fechaConexao(conn);
				return true;
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao atualizar o Centro de Custo no banco de" + "dados ");
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(null, "Os dados da Centro de Custo estão vazios");
			return false;
		}
	}

}
