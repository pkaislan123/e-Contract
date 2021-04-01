package main.java.conexaoBanco;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroContrato.CadastroPagamentoContratual;
import main.java.cadastros.CadastroContrato.CadastroTransferenciaCarga;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroModelo;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroSafra;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.outros.DadosGlobais;
import main.java.tratamento_proprio.Log;

public class GerenciarBancoTransferenciasCarga {

	
	
	public String sql_transferencia(CadastroContrato.CadastroTransferenciaCarga transferencia) {
		return "insert into transferencia_carga ( id_contrato_remetente , id_contrato_destinatario , id_carregamento_remetente,quantidade , data_transferencia , descricao ) values ('"
				+ transferencia.getId_contrato_remetente()+ "','"
				+ transferencia.getId_contrato_destinatario() + "','"
				+ transferencia.getId_carregamento_remetente() + "','"
				+ transferencia.getQuantidade() + "','"
				+ transferencia.getData() + "','"

				+ transferencia.getDescricao() + "')";
	}
	
	public int inserirTransferencia(CadastroContrato.CadastroTransferenciaCarga transferencia) {
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
					System.out.println("Id da transferencia de carga inserido: " + result);
				}
				rs.close();
				stmt.close();

				return result;

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Erro ao inserir o transferencia de carga no banco de " + "dados "  );
				
				return -1;
			}
		} else {
			System.out.println("A transferencia de carga enviado por parametro esta vazio");
			return -1;
		}
	}
	
	
	public ArrayList<CadastroContrato.CadastroTransferenciaCarga> getTransferenciasRemetente(int id_contrato_remetente) {
		String selectTransferenciasRemetente = "select * from transferencia_carga where id_contrato_remetente  = ? order by id_transferencia ";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroContrato.CadastroTransferenciaCarga> lista_trasnferencias = new ArrayList<>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectTransferenciasRemetente);
			// pstm.setString(1, chave);
			pstm.setInt(1, id_contrato_remetente);

			rs = pstm.executeQuery();
			while (rs.next()) {
				CadastroContrato.CadastroTransferenciaCarga transferencia = new CadastroContrato.CadastroTransferenciaCarga();
			
				transferencia.setId_transferencia(rs.getInt("id_transferencia"));
				transferencia.setId_contrato_remetente(rs.getInt("id_contrato_remetente"));
				transferencia.setId_contrato_destinatario(rs.getInt("id_contrato_destinatario"));
				transferencia.setId_carregamento_remetente(rs.getInt("id_carregamento_remetente"));

				transferencia.setQuantidade(rs.getString("quantidade"));
				transferencia.setData(rs.getString("data_transferencia"));
				transferencia.setDescricao(rs.getString("descricao"));

				lista_trasnferencias.add(transferencia);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar transferencias de carga do remetente"  );
		}
		return lista_trasnferencias;

	}
	
	
	public ArrayList<CadastroContrato.CadastroTransferenciaCarga> getTransferenciaDestinatario(int id_contrato_destinatario) {
		String selectTransferenciasRemetente = "select * from transferencia_carga where id_contrato_destinatario  = ? order by id_transferencia";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroContrato.CadastroTransferenciaCarga> lista_trasnferencias = new ArrayList<>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectTransferenciasRemetente);
			// pstm.setString(1, chave);
			pstm.setInt(1, id_contrato_destinatario);

			rs = pstm.executeQuery();
			while (rs.next()) {
				CadastroContrato.CadastroTransferenciaCarga transferencia = new CadastroContrato.CadastroTransferenciaCarga();
			
				transferencia.setId_transferencia(rs.getInt("id_transferencia"));
				transferencia.setId_contrato_remetente(rs.getInt("id_contrato_remetente"));
				transferencia.setId_contrato_destinatario(rs.getInt("id_contrato_destinatario"));
				transferencia.setId_carregamento_remetente(rs.getInt("id_carregamento_remetente"));

				transferencia.setQuantidade(rs.getString("quantidade"));
				transferencia.setData(rs.getString("data_transferencia"));
				transferencia.setDescricao(rs.getString("descricao"));

				lista_trasnferencias.add(transferencia);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar transferencias de carga do remetente"  );
		}
		return lista_trasnferencias;

	}
	

	
	public boolean removerTransferencia(int id_transferencia) {
		String sql_delete_transferencia = "DELETE FROM transferencia_carga WHERE id_transferencia = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_delete_transferencia);

			pstm.setInt(1, id_transferencia);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			JOptionPane.showMessageDialog(null, "Transferencia de carga excluida, banco normalizado ");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao excluir a transferencia de carga do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
							+ "dados " + f.getMessage());
			return false;
		}

	}

	
	public ArrayList<CadastroContrato.CadastroTransferenciaCarga> getTransferencias() {
		String selectTransferenciasRemetente = "select * from transferencia_carga order by id_transferencia";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroContrato.CadastroTransferenciaCarga> lista_trasnferencias = new ArrayList<>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectTransferenciasRemetente);
			// pstm.setString(1, chave);

			rs = pstm.executeQuery();
			while (rs.next()) {
				CadastroTransferenciaCarga transferencia = new CadastroTransferenciaCarga();
			
				transferencia.setId_transferencia(rs.getInt("id_transferencia"));
				transferencia.setId_contrato_remetente(rs.getInt("id_contrato_remetente"));
				transferencia.setId_contrato_destinatario(rs.getInt("id_contrato_destinatario"));
				transferencia.setId_carregamento_remetente(rs.getInt("id_carregamento_remetente"));

				transferencia.setQuantidade(rs.getString("quantidade"));
				transferencia.setData(rs.getString("data_transferencia"));
				transferencia.setDescricao(rs.getString("descricao"));

				lista_trasnferencias.add(transferencia);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar transferencias de carga "  );
		}
		return lista_trasnferencias;

	}
	
	
	
	public CadastroContrato.CadastroTransferenciaCarga getTransferencia(int id_transferencia) {
		String selectTransferencias = "select * from transferencia_carga where id_transferencia  = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		CadastroContrato.CadastroTransferenciaCarga transferencia = new CadastroContrato.CadastroTransferenciaCarga();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectTransferencias);
			// pstm.setString(1, chave);
			pstm.setInt(1, id_transferencia);

			rs = pstm.executeQuery();
			rs.next();
			
				transferencia.setId_transferencia(rs.getInt("id_transferencia"));
				transferencia.setId_contrato_remetente(rs.getInt("id_contrato_remetente"));
				transferencia.setId_contrato_destinatario(rs.getInt("id_contrato_destinatario"));
				transferencia.setId_carregamento_remetente(rs.getInt("id_carregamento_remetente"));

				transferencia.setQuantidade(rs.getString("quantidade"));
				transferencia.setData(rs.getString("data_transferencia"));
				transferencia.setDescricao(rs.getString("descricao"));


			
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar a transferencia"  );
		}
		return transferencia;

	}
	
	
}
