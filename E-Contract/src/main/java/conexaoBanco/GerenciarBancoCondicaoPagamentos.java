package main.java.conexaoBanco;

import main.java.cadastros.CadastroAditivo;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CondicaoPagamento;

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

public class GerenciarBancoCondicaoPagamentos {

	public String sql_dado (CondicaoPagamento dado) {
		return "insert into condicao_pagamento (nome_condicao_pagamento, numero_parcelas, intervalo, dia_recebimento, forma_pagamento, observacao, descricao) values ('"
				+ dado.getNome_condicao_pagamento() + "','" 
				+ dado.getNumero_parcelas() + "','"
				+ dado.getIntervalo() + "','"
				+ dado.getDia_recebimento() + "','"
				+ dado.getForma_pagamento() + "','"
			    + dado.getObservacao() + "','"
				+ dado.getDescricao() + "')";
	}

	public int inserirCondicaoPagamento(CondicaoPagamento dado) {
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
					System.out.println("Id condição do pagamento inserido: " + result);
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

	public ArrayList<CondicaoPagamento> getCondicaoPagamentos() {
		String select = "select * from condicao_pagamento";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CondicaoPagamento> lista = new ArrayList<>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(select);

			rs = pstm.executeQuery();
			while (rs.next()) {
				CondicaoPagamento dado = new CondicaoPagamento();

				dado.setId_condicao_pagamento(rs.getInt("id_condicao_pagamento"));
				dado.setNome_condicao_pagamento(rs.getString("nome_condicao_pagamento"));
				dado.setNumero_parcelas(rs.getInt("numero_parcelas"));
				dado.setIntervalo(rs.getInt("intervalo"));
				dado.setDia_recebimento(rs.getInt("dia_recebimento"));
				dado.setForma_pagamento(rs.getInt("forma_pagamento"));
				dado.setObservacao(rs.getString("observacao"));
				dado.setDescricao(rs.getString("descricao"));
				
			

				lista.add(dado);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar a condição de pagamento");// );
		}
		return lista;

	}

	public CondicaoPagamento getCondicaoPagamento(int id) {

		String select = "select * from condicao_pagamento where id_condicao_pagamento = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		CondicaoPagamento dado = new CondicaoPagamento();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(select);
			pstm.setInt(1, id);

			rs = pstm.executeQuery();
			rs.next();

			dado.setId_condicao_pagamento(rs.getInt("id_condicao_pagamento"));
			dado.setNome_condicao_pagamento(rs.getString("nome_condicao_pagamento"));
			dado.setNumero_parcelas(rs.getInt("numero_parcelas"));
			dado.setIntervalo(rs.getInt("intervalo"));
			dado.setDia_recebimento(rs.getInt("dia_recebimento"));
			dado.setForma_pagamento(rs.getInt("forma_pagamento"));
			dado.setObservacao(rs.getString("observacao"));
			dado.setDescricao(rs.getString("descricao"));
			
			return dado;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar centro de custo id: " + id);// );
			System.out.println("Erro ao listar centro de custo id: " + id + " erro: " + "\ncausa: ");
			return null;
		}

	}

	public boolean removerCondicaoPagamento(int id_condicao_pagamento) {
		String delete = "DELETE FROM condicao_pagamento WHERE id_condicao_pagamento = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(delete);

			pstm.setInt(1, id_condicao_pagamento);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			JOptionPane.showMessageDialog(null, "Condição de Pagamento excluido, banco normalizado ");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao exlcuir a Condição de Pagamento do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
							+ "dados " + f.getMessage());
			return false;
		}

	}

	public boolean atualizarCondicaoPagamento(CondicaoPagamento dado) {
		if (dado != null) {
			try {
				Connection conn = null;
				String atualizar = null;
				PreparedStatement pstm;

				atualizar = "update condicao_pagamento set nome_condicao_pagamento = ?, numero_parcelas = ?, intervalo = ?, dia_recebimento = ?, forma_pagamento = ?, observacao = ?,descricao = ? where id_condicao_pagamento = ? ";
				conn = ConexaoBanco.getConexao();
				pstm = conn.prepareStatement(atualizar);

				pstm.setString(1, dado.getNome_condicao_pagamento());
				pstm.setInt(2, dado.getNumero_parcelas());
				pstm.setInt(3, dado.getIntervalo());
				pstm.setInt(4, dado.getDia_recebimento());
				pstm.setInt(5, dado.getForma_pagamento());
				pstm.setString(6, dado.getObservacao());
				pstm.setString(7, dado.getDescricao());
			
				pstm.setInt(8, dado.getId_condicao_pagamento());

				pstm.execute();
				// JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
				System.out.println("Condição de Pagamento Atualizada com sucesso");
				ConexaoBanco.fechaConexao(conn);
				return true;
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao atualizar a Condição de Pagamento no banco de" + "dados ");
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(null, "Os parametros estão vazios");
			return false;
		}
	}

}
