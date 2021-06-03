

package main.java.conexaoBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroClassificador;
import main.java.cadastros.FinanceiroConta;
import main.java.cadastros.CadastroClassificador;


public class GerenciarBancoClassificadores {

	
		
	/*
	 * select * from safra sf
	inner join produto pd
	where sf.id_produto = pd.id_produto;
	 */
		
		  public int inserir_classificador(CadastroClassificador classificador) {
			  if( classificador != null) {
		            Connection conn = null;
		            try {
		                conn = ConexaoBanco.getConexao();
		                String sql = "insert into classificador\r\n" + 
		                		"(id_colaborador , descricao) values ('"
		    	    			+ classificador.getId_colaborador()
		    	    			+ "','"
		    	    			+ classificador.getDescricao()
		    	    			
		    	    			+ "')";
		    	       
		    	        PreparedStatement grava = (PreparedStatement) conn.prepareStatement(sql); 
		    	        grava.execute();    
		                JOptionPane.showMessageDialog(null, "Classificador Cadastrado");
		                ConexaoBanco.fechaConexao(conn, grava);
		                return 1;
		 
		            } catch (Exception e) {
		            	  //JOptionPane.showMessageDialog(null, "Erro ao inserir a safra no banco de"
		                 //         + "dados "  );
		                return 0;
		            }
		        } else {
		            System.out.println("Dados enviado por parametro esta vazia");
		            return 0;
		        }
		  }

		  
		  public ArrayList<CadastroClassificador> getClassificadores() {
				
			  
		        Connection conn = null;
		        PreparedStatement pstm = null;
		        ResultSet rs = null;
		        ArrayList<CadastroClassificador> listaClassificadores = new ArrayList<CadastroClassificador>();
		        try {
		            conn = ConexaoBanco.getConexao();
		            pstm = conn.prepareStatement("select * from classificador");
		            rs = pstm.executeQuery();
		            while (rs.next()) {
		            	CadastroClassificador classificador = new CadastroClassificador();
		            
		            	classificador.setId(rs.getInt("id"));
		            	classificador.setId_colaborador(rs.getInt("id_colaborador"));
		            	classificador.setDescricao(rs.getString("descricao"));


		          
		            	listaClassificadores.add(classificador);
		            }
		            ConexaoBanco.fechaConexao(conn, pstm, rs);
		        } catch (Exception e) {
		        	  // JOptionPane.showMessageDialog(null, "Erro ao listar safras"  );
		        }
		        return listaClassificadores;
		    }
		  
		  
		  public ArrayList<CadastroClassificador> busca_classificadores() {
				
			  
		        Connection conn = null;
		        PreparedStatement pstm = null;
		        ResultSet rs = null;
		        ArrayList<CadastroClassificador> listaClassificadores = new ArrayList<CadastroClassificador>();
		        try {
		            conn = ConexaoBanco.getConexao();
		            pstm = conn.prepareStatement("select cl.*, concat(fc.nome,\" \", fc.sobrenome) as nome_classificador\r\n"
		            		+ " from classificador cl\r\n"
		            		+ "left join funcionario fc on fc.id_funcionario = cl.id_colaborador");
		            rs = pstm.executeQuery();
		            
		            while (rs.next()) {
		            	CadastroClassificador classificador = new CadastroClassificador();

		            	classificador.setId(rs.getInt("id"));
		            	classificador.setId_colaborador(rs.getInt("id_colaborador"));
		            	
		            	classificador.setDescricao(rs.getString("descricao"));
		            	classificador.setNome_colaborador(rs.getString("nome_classificador"));

		          
		            	listaClassificadores.add(classificador);
		            }
		            ConexaoBanco.fechaConexao(conn, pstm, rs);
		        } catch (Exception e) {
		        	  // JOptionPane.showMessageDialog(null, "Erro ao listar safras"  );
		        }
		        return listaClassificadores;
		    }
		  
		  
		  public CadastroClassificador getClassificador(int id) {
				 String selectSilo = "select classificador.*,\r\n"
				 		+ "CONCAT(fc.nome, \" \", fc.sobrenome)\r\n"
				 		+ "as nome_classificador\r\n"
				 		+ " from classificador \r\n"
				 		+ "left join funcionario fc on fc.id_funcionario = classificador.id_colaborador\r\n"
				 		+ " where id = ?\r\n"
				 		+ "";

			   Connection conn = null;
		        PreparedStatement pstm = null;
		        ResultSet rs = null;	        
		        CadastroClassificador classificador = new CadastroClassificador();

		        try {
		            conn = ConexaoBanco.getConexao();
		            pstm = conn.prepareStatement(selectSilo);
		            pstm.setInt(1, id);
		            
		            rs = pstm.executeQuery();
		            rs.next();

		        	classificador.setId(rs.getInt("id"));
	            	classificador.setId_colaborador(rs.getInt("id_colaborador"));
	            	classificador.setNome_colaborador(rs.getString("nome_classificador"));
	            	classificador.setDescricao(rs.getString("descricao"));
	            	
		            
	            	return classificador;
			          
		            
		        } catch (Exception e) {
		        	  //    JOptionPane.showMessageDialog(null, "Erro ao buscar por safra id: " + id + " erro: "  );
		            return null;
		        }
			  
		  }

	
	
public boolean atualizarClassificador(CadastroClassificador classificador) {
	if (classificador != null) {
		try {
			Connection conn = null;
			String atualizar = null;
			PreparedStatement pstm;

			atualizar = "update classificador set id_colaborador = ?, descricao = ?  where id = ? ";
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(atualizar);

			pstm.setInt(1, classificador.getId_colaborador());
			pstm.setString(2, classificador.getDescricao());
			pstm.setInt(3, classificador.getId());
			
			

			pstm.execute();
			// JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
			System.out.println("Classificador Atualizada com sucesso");
			ConexaoBanco.fechaConexao(conn);
			return true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao atualizaro Classificador no banco de dados\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
			return false;
		}
	} else {
		JOptionPane.showMessageDialog(null, "Os parametros estão vazios");
		return false;
	}
}

public boolean removerClassificador(int id) {
	String delete = "DELETE FROM classificador WHERE id = ?";
	Connection conn = null;
	ResultSet rs = null;
	try {
		conn = ConexaoBanco.getConexao();
		PreparedStatement pstm;
		pstm = conn.prepareStatement(delete);

		pstm.setInt(1, id);

		pstm.execute();
		ConexaoBanco.fechaConexao(conn, pstm);
		JOptionPane.showMessageDialog(null, "Classificador Excluído, banco normalizado ");
		return true;

	} catch (Exception f) {
		JOptionPane.showMessageDialog(null,
				"Erro ao excluir o classificador do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
						+ "dados " + f.getMessage());
		return false;
	}

}

}


