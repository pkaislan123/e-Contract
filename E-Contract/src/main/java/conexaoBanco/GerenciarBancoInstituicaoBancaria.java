package main.java.conexaoBanco;

import main.java.cadastros.CadastroAditivo;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.InstituicaoBancaria;
import main.java.repositories.InstituicaoBancariaRepository;

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

public class GerenciarBancoInstituicaoBancaria {

	public String sql_ib(InstituicaoBancaria ib) {
		return "insert into instituicao_bancaria (nome_instituicao_bancaria, saldo_inicial, taxa_intermediacao,taxa_parcelamento_mensal, tarifa_fixa_transacao, observacao, descricao, id_cliente, id_conta) values ('"
				+ ib.getNome_instituicao_bancaria() + "','" 
				+ ib.getSaldo_inicial() + "','"
				+ ib.getTaxa_intermediacao() + "','" 
				+ ib.getTaxa_parcelamento_mensal() + "','" 
				+ ib.getTaxa_fixa_transacao() + "','"
				+ ib.getObservacao() + "','"
				+ ib.getDescricao() + "','"
				+ ib.getId_cliente() + "','"
				
				+ ib.getId_conta() + "')";
	}

	public int inserirIB(InstituicaoBancaria ib) {
		int result = -1;
		int id_cliente = -1;
		if (ib != null) {
			Connection conn = null;
			try {

				conn = ConexaoBanco.getConexao();

				String query = sql_ib(ib);
				Statement stmt = (Statement) conn.createStatement();
				int numero = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					result = rs.getInt(1);
					System.out.println("Id instituicao bancaria inserido: " + result);
				}
				rs.close();
				stmt.close();

				return result;

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao inserir a instituicao bancaria no banco de dados, Erro: " + e.getMessage() + "\nCausa: " + e.getCause()) ;

				return -1;
			}
		} else {
			System.out.println("O parametro enviado por parametro esta vazio");
			return -1;
		}
	}

	public ArrayList<InstituicaoBancaria> getInstituicoesBancarias() {
		String selectIb = "select * from instituicao_bancaria";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<InstituicaoBancaria> lista_instituicoes_bancarias = new ArrayList<>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectIb);

			rs = pstm.executeQuery();
			while (rs.next()) {
				InstituicaoBancaria ib = new InstituicaoBancaria();

				ib.setId_instituicao_bancaria(rs.getInt("id_instituicao_bancaria"));
				ib.setNome_instituicao_bancaria(rs.getString("nome_instituicao_bancaria"));
				ib.setSaldo_inicial(new BigDecimal(rs.getString("saldo_inicial")));
				ib.setTaxa_intermediacao(new BigDecimal(rs.getString("taxa_intermediacao")));
				ib.setTaxa_parcelamento_mensal(new BigDecimal(rs.getString("taxa_parcelamento_mensal")));
				ib.setTaxa_fixa_transacao(new BigDecimal(rs.getString("tarifa_fixa_transacao")));
				ib.setObservacao(rs.getString("observacao"));
				ib.setDescricao(rs.getString("descricao"));
				ib.setId_cliente(rs.getInt("id_cliente"));
				ib.setId_conta(rs.getInt("id_conta"));

				lista_instituicoes_bancarias.add(ib);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar instituicoes bancarias");// );
		}
		return lista_instituicoes_bancarias;

	}

	public InstituicaoBancaria getInstituicaoBancaria(int id) {

		String select_instituicao_bancaria = "select * from instituicao_bancaria where id_instituicao_bancaria = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		InstituicaoBancaria ib = new InstituicaoBancaria();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(select_instituicao_bancaria);
			pstm.setInt(1, id);

			rs = pstm.executeQuery();
			rs.next();

			ib.setId_instituicao_bancaria(rs.getInt("id_instituicao_bancaria"));
			ib.setNome_instituicao_bancaria(rs.getString("nome_instituicao_bancaria"));
			ib.setSaldo_inicial(new BigDecimal(rs.getString("saldo_inicial")));
			ib.setTaxa_intermediacao(new BigDecimal(rs.getString("taxa_intermediacao")));
			ib.setTaxa_parcelamento_mensal(new BigDecimal(rs.getString("taxa_parcelamento_mensal")));
			ib.setTaxa_fixa_transacao(new BigDecimal(rs.getString("tarifa_fixa_transacao")));
			ib.setObservacao(rs.getString("observacao"));
			ib.setDescricao(rs.getString("descricao"));
			ib.setId_cliente(rs.getInt("id_cliente"));
			ib.setId_conta(rs.getInt("id_conta"));
			
			return ib;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar a instituicao bancaria id: " + id);// );
			System.out.println("Erro ao listar instituicao bancaria id: " + id + " erro: " + "\ncausa: ");
			return null;
		}

	}

	public boolean removerInstituicaoBancaria(int id_instituicao_bancaria) {
		String sql_delete_aditivo = "DELETE FROM instituicao_bancaria WHERE id_instituicao_bancaria = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_delete_aditivo);

			pstm.setInt(1, id_instituicao_bancaria);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			JOptionPane.showMessageDialog(null, "Instituicao Bancaria excluida, banco normalizado ");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao exlcuir a Instituicao Bancaria do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
							+ "dados " + f.getMessage());
			return false;
		}

	}

	public boolean atualizarIB(InstituicaoBancaria ib) {
		if (ib != null) {
			try {
				Connection conn = null;
				String atualizar = null;
				PreparedStatement pstm;

				atualizar = "update instituicao_bancaria set nome_instituicao_bancaria = ?, saldo_inicial = ?, taxa_intermediacao = ?, taxa_parcelamento_mensal = ?, tarifa_fixa_transacao = ?, observacao = ?,descricao = ? , id_cliente = ?, id_conta = ? where id_instituicao_bancaria = ? ";
				conn = ConexaoBanco.getConexao();
				pstm = conn.prepareStatement(atualizar);

				pstm.setString(1, ib.getNome_instituicao_bancaria());
				pstm.setString(2, ib.getSaldo_inicial().toString());
				pstm.setString(3, ib.getTaxa_intermediacao().toString());
				pstm.setString(4, ib.getTaxa_parcelamento_mensal().toString());
				pstm.setString(5, ib.getTaxa_fixa_transacao().toString());
				pstm.setString(6, ib.getObservacao());
				pstm.setString(7, ib.getDescricao());
				pstm.setInt(8, ib.getId_cliente());
				pstm.setInt(9, ib.getId_conta());

				pstm.setInt(10, ib.getId_instituicao_bancaria());

				pstm.execute();
				// JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
				System.out.println("Instituicao Bancaria Atualizada com sucesso");
				ConexaoBanco.fechaConexao(conn);
				return true;
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao atualizar a Instituicao Bancaria no banco de" + "dados ");
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(null, "Os dados da IB estão vazios");
			return false;
		}
	}

}
