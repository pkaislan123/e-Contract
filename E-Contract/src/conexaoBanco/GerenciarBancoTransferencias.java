package conexaoBanco;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import cadastros.CadastroAditivo;
import cadastros.CadastroCliente;
import cadastros.CadastroContrato;
import cadastros.CadastroProduto;
import cadastros.CadastroSafra;

public class GerenciarBancoTransferencias {

	
	
	public String sql_transferencia(CadastroContrato.CadastroTransferenciaPagamentoContratual transferencia) {
		return "insert into transferencia_pagamento_contratual ( id_contrato_remetente , id_contrato_destinatario ,valor , data_transferencia , descricao ) values ('"
				+ transferencia.getId_contrato_remetente()+ "','"
				+ transferencia.getId_contrato_destinatario() + "','"
				+ transferencia.getValor() + "','"
				+ transferencia.getData() + "','"

				+ transferencia.getDescricao() + "')";
	}
	
	public int inserirTransferencia(CadastroContrato.CadastroTransferenciaPagamentoContratual transferencia) {
		int result = -1;
		int id_cliente = -1;
		if (transferencia != null) {
			Connection conn = null;
			try {

				conn = ConexaoBanco.getConexao();

				String query = sql_transferencia(transferencia);
				Statement stmt = (Statement) conn.createStatement();
				int numero = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					result = rs.getInt(1);
					System.out.println("Id da transferencia inserido: " + result);
				}
				rs.close();
				stmt.close();

				return result;

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Erro ao inserir o transferencia no banco de " + "dados "  );
				
				return -1;
			}
		} else {
			System.out.println("A transferencia enviado por parametro esta vazio");
			return -1;
		}
	}
	
	
	public ArrayList<CadastroContrato.CadastroTransferenciaPagamentoContratual> getTransferenciasRemetente(int id_contrato_remetente) {
		String selectTransferenciasRemetente = "select * from transferencia_pagamento_contratual where id_contrato_remetente  = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroContrato.CadastroTransferenciaPagamentoContratual> lista_trasnferencias = new ArrayList<>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectTransferenciasRemetente);
			// pstm.setString(1, chave);
			pstm.setInt(1, id_contrato_remetente);

			rs = pstm.executeQuery();
			while (rs.next()) {
				CadastroContrato.CadastroTransferenciaPagamentoContratual transferencia = new CadastroContrato.CadastroTransferenciaPagamentoContratual();
			
				transferencia.setId_transferencia(rs.getInt("id_transferencia"));
				transferencia.setId_contrato_remetente(rs.getInt("id_contrato_remetente"));
				transferencia.setId_contrato_destinatario(rs.getInt("id_contrato_destinatario"));
				transferencia.setValor(rs.getString("valor"));
				transferencia.setData(rs.getString("data_transferencia"));
				transferencia.setDescricao(rs.getString("descricao"));

				lista_trasnferencias.add(transferencia);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar transferencias de remetente"  );
		}
		return lista_trasnferencias;

	}
	
	
	public ArrayList<CadastroContrato.CadastroTransferenciaPagamentoContratual> getTransferenciaDestinatario(int id_contrato_destinatario) {
		String selectTransferenciasRemetente = "select * from transferencia_pagamento_contratual where id_contrato_destinatario  = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroContrato.CadastroTransferenciaPagamentoContratual> lista_trasnferencias = new ArrayList<>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectTransferenciasRemetente);
			// pstm.setString(1, chave);
			pstm.setInt(1, id_contrato_destinatario);

			rs = pstm.executeQuery();
			while (rs.next()) {
				CadastroContrato.CadastroTransferenciaPagamentoContratual transferencia = new CadastroContrato.CadastroTransferenciaPagamentoContratual();
			
				transferencia.setId_transferencia(rs.getInt("id_transferencia"));
				transferencia.setId_contrato_remetente(rs.getInt("id_contrato_remetente"));
				transferencia.setId_contrato_destinatario(rs.getInt("id_contrato_destinatario"));
				transferencia.setValor(rs.getString("valor"));
				transferencia.setData(rs.getString("data_transferencia"));
				transferencia.setDescricao(rs.getString("descricao"));

				lista_trasnferencias.add(transferencia);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar transferencias de remetente"  );
		}
		return lista_trasnferencias;

	}
	

	
	public boolean removerTransferencia(int id_transferencia) {
		String sql_delete_transferencia = "DELETE FROM transferencia_pagamento_contratual WHERE id_transferencia = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_delete_transferencia);

			pstm.setInt(1, id_transferencia);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			JOptionPane.showMessageDialog(null, "Transferencia excluida, banco normalizado ");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao excluir a transferencia do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
							+ "dados " + f.getMessage());
			return false;
		}

	}

	
	public ArrayList<CadastroContrato.CadastroTransferenciaPagamentoContratual> getTransferencias() {
		String selectTransferenciasRemetente = "select * from transferencia_pagamento_contratual";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroContrato.CadastroTransferenciaPagamentoContratual> lista_trasnferencias = new ArrayList<>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectTransferenciasRemetente);
			// pstm.setString(1, chave);

			rs = pstm.executeQuery();
			while (rs.next()) {
				CadastroContrato.CadastroTransferenciaPagamentoContratual transferencia = new CadastroContrato.CadastroTransferenciaPagamentoContratual();
			
				transferencia.setId_transferencia(rs.getInt("id_transferencia"));
				transferencia.setId_contrato_remetente(rs.getInt("id_contrato_remetente"));
				transferencia.setId_contrato_destinatario(rs.getInt("id_contrato_destinatario"));
				transferencia.setValor(rs.getString("valor"));
				transferencia.setData(rs.getString("data_transferencia"));
				transferencia.setDescricao(rs.getString("descricao"));

				lista_trasnferencias.add(transferencia);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar transferencias "  );
		}
		return lista_trasnferencias;

	}
	
	
	
	
}
