package main.java.conexaoBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.java.cadastros.CadastroSafrasEvidencias;
import main.java.cadastros.CadastroSilo;

public class GerenciarBancoSafrasEvidencias {

	 public int inserir_safra_evidencia(CadastroSafrasEvidencias cadastro) {
	            Connection conn = null;
	            try {
	                conn = ConexaoBanco.getConexao();
	                String sql = "insert into safras_evidencia\r\n" + 
	                		"(id_usuario, ids_safras) values ('"
	    	    			+ cadastro.getId_usuario()
	    	    			+ "','"
	    	    			+ cadastro.getIds_safras()
	    	    			+ "')";
	    	       
	    	        PreparedStatement grava = (PreparedStatement) conn.prepareStatement(sql); 
	    	        grava.execute();    
	                JOptionPane.showMessageDialog(null, "Safra em Evidência Cadastrada");
	                ConexaoBanco.fechaConexao(conn, grava);
	                return 1;
	 
	            } catch (Exception e) {
	            	  //JOptionPane.showMessageDialog(null, "Erro ao inserir a safra no banco de"
	                 //         + "dados "  );
	                return 0;
	            }
	       
	  }

	  
	  public ArrayList<CadastroSafrasEvidencias> getSafrasEvidenciaPorUsuario(int id_usuario) {
			
		  
	        Connection conn = null;
	        PreparedStatement pstm = null;
	        ResultSet rs = null;
	        ArrayList<CadastroSafrasEvidencias> lista_safras_evidencia = new ArrayList<CadastroSafrasEvidencias>();
	        try {
	            conn = ConexaoBanco.getConexao();
	            pstm = conn.prepareStatement("select * from safras_evidencia where id_usuario = ?");
	            pstm.setInt(1, id_usuario);
	            rs = pstm.executeQuery();
	            while (rs.next()) {
	            	CadastroSafrasEvidencias cad = new CadastroSafrasEvidencias();
	            	cad.setId(rs.getInt("id"));
	            	cad.setId_usuario(rs.getInt("id_usuario"));
	            	cad.setIds_safras(rs.getString("ids_safras"));


	          
	            	lista_safras_evidencia.add(cad);
	            }
	            ConexaoBanco.fechaConexao(conn, pstm, rs);
	        } catch (Exception e) {
	        	  // JOptionPane.showMessageDialog(null, "Erro ao listar safras em evidencia\nErro: " + e.getMessage() + "\nCausa:" + e.getCause()  );
	        }
	        return lista_safras_evidencia;
	    }
	  
	
	  public boolean atualizarSafrEnvidencia(CadastroSafrasEvidencias cad) {
				try {
					Connection conn = null;
					String atualizar = null;
					PreparedStatement pstm;

					atualizar = "update safras_evidencia set id_usuario = ?, ids_safras = ? where id = ? ";
					conn = ConexaoBanco.getConexao();
					pstm = conn.prepareStatement(atualizar);

					pstm.setInt(1, cad.getId_usuario());
					pstm.setString(2, cad.getIds_safras());
					pstm.setInt(3, cad.getId());

					pstm.execute();
					// JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
					System.out.println("Safras em evidencia Atualizada com sucesso");
					ConexaoBanco.fechaConexao(conn);
					return true;
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Erro ao atualizaro as safras em evidencia no banco de dados\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
					return false;
				}
			
		}

	

	
}
