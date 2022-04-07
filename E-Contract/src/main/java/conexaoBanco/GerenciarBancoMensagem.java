

package main.java.conexaoBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import main.java.cadastros.CadastroMensagem;
import main.java.cadastros.CadastroSilo;
import main.java.cadastros.CadastroStatusArmazem;

public class GerenciarBancoMensagem {

	public CadastroMensagem getMensagem() {
		String selectStatus = "select * from mensagem_armazem";

		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		CadastroMensagem msg = new CadastroMensagem();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectStatus);
			rs = pstm.executeQuery();
			rs.next();

			msg.setId_mensagem(rs.getInt("id_mensagem"));
			msg.setConteudo(rs.getString("conteudo"));

			return msg;

		} catch (Exception e) {
			 JOptionPane.showMessageDialog(null, "Erro ao buscar por Mensagem!\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
			return null;
		}

	}

	public boolean atualizarMensagem(CadastroMensagem msg) {
			try {
				Connection conn = null;
				String atualizar = null;
				PreparedStatement pstm;

				atualizar = "update mensagem_armazem set conteudo = ? where id_mensagem = ? ";
				conn = ConexaoBanco.getConexao();
				pstm = conn.prepareStatement(atualizar);

				pstm.setString(1, msg.getConteudo());
				pstm.setInt(2, msg.getId_mensagem());


				pstm.execute();
				ConexaoBanco.fechaConexao(conn);
				return true;
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao atualizaro Mensagem no banco de dados\nErro: " + e.getMessage()
						+ "\nCausa: " + e.getCause());
				return false;
			}
		} 

}
