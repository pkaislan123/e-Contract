package main.java.conexaoBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import main.java.cadastros.CadastroSilo;
import main.java.cadastros.CadastroStatusArmazem;

public class GerenciarBancoStatusArmazem {

	public CadastroStatusArmazem getStatusArmazem() {
		String selectStatus = "select * from status_gerais_armazem";

		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		CadastroStatusArmazem status = new CadastroStatusArmazem();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectStatus);
			rs = pstm.executeQuery();
			rs.next();

			status.setId_status(rs.getInt("id_status"));
			status.setStatus_armazem(rs.getString("status_armazem"));
			status.setStatus_embarque(rs.getString("status_embarque"));
			status.setStatus_desembarque(rs.getString("status_desembarque"));
			status.setHora_encerramento(rs.getString("horario_encerramento"));
			
			return status;

		} catch (Exception e) {
			 JOptionPane.showMessageDialog(null, "Erro ao buscar por status gerais do armazém!\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
			return null;
		}

	}

	public boolean atualizarStatus(CadastroStatusArmazem status) {
			try {
				Connection conn = null;
				String atualizar = null;
				PreparedStatement pstm;

				atualizar = "update status_gerais_armazem set status_armazem = ?, status_embarque = ?, status_desembarque = ?,  horario_encerramento = ? where id_status = ? ";
				conn = ConexaoBanco.getConexao();
				pstm = conn.prepareStatement(atualizar);

				pstm.setString(1, status.getStatus_armazem());
				pstm.setString(2, status.getStatus_embarque());
				pstm.setString(3, status.getStatus_desembarque());
				pstm.setString(4, status.getHora_encerramento());
				pstm.setInt(5, status.getId_status());


				pstm.execute();
				ConexaoBanco.fechaConexao(conn);
				return true;
			} catch (Exception e) {
				//JOptionPane.showMessageDialog(null, "Erro ao atualizaro Silo no banco de dados\nErro: " + e.getMessage()
						//+ "\nCausa: " + e.getCause());
				return false;
			}
		} 

}
