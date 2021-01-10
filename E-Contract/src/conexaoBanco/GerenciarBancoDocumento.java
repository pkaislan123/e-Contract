package conexaoBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import cadastros.CadastroCliente;
import cadastros.CadastroDocumento;
import cadastros.CadastroProduto;

public class GerenciarBancoDocumento {

	public int inserir_documento_padrao(CadastroDocumento doc) {
		System.out.println("Inserir Documento foi chamado!");
		int result = -1;
		int id_cliente = -1;

		String sql_cadastro_documento = "insert into documento (nome, descricao, tipo, id_pai, nome_arquivo, id_contrato_pai) values ('"

				+ doc.getNome() + "','" + 
				doc.getDescricao() + "','" +
				doc.getTipo() + "','" +
				doc.getId_pai() + "','" +
				doc.getNome_arquivo() + "','"
				+ doc.getId_contrato_pai() 
				
				+ "')";

		// cria os strings para cadastro o cliente

		try {
			Connection conn = ConexaoBanco.getConexao();
			Statement stmt = (Statement) conn.createStatement();
			int numero = stmt.executeUpdate(sql_cadastro_documento, Statement.RETURN_GENERATED_KEYS);

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
					"Erro ao inserir o documento no banco de" + "dados\n Erro: " + e.getMessage());
			return -1;
		}

	}
	
	
	public ArrayList<CadastroDocumento> getDocumentos(int id_contrato){
		Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
  	    String select_documentos = "select * from documento where id_contrato_pai = ?";

        ArrayList<CadastroDocumento> listaDocs = new ArrayList<CadastroDocumento>();
        try {
            conn = ConexaoBanco.getConexao();
            pstm = conn.prepareStatement(select_documentos);
            pstm.setInt(1,  id_contrato);
            rs = pstm.executeQuery();
            while (rs.next()) {
            	CadastroDocumento doc = new CadastroDocumento();
            
            	doc.setId_documento(rs.getInt("id_documento"));

            	doc.setNome(rs.getString("nome"));

            	doc.setDescricao(rs.getString("descricao"));
            	doc.setTipo(rs.getInt("tipo"));
            	doc.setId_pai(rs.getInt("id_pai"));
            	doc.setNome_arquivo(rs.getString("nome_arquivo"));
            	doc.setId_contrato_pai(rs.getInt("id_contrato_pai"));

                
          
            	listaDocs.add(doc);
            }
            ConexaoBanco.fechaConexao(conn, pstm, rs);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar documentos" + e.getMessage());
        }
        return listaDocs;
	}
	

}
