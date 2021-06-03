

package main.java.conexaoBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroTransgenia;
import main.java.cadastros.FinanceiroConta;
import main.java.cadastros.CadastroTransgenia;


public class GerenciarBancoTransgenia {

	
		
	/*
	 *CREATE TABLE `transgenia` (
  `id_transgenia` int(3) NOT NULL AUTO_INCREMENT,
  `nome` text DEFAULT NULL,
  `teste` int(3) DEFAULT NULL,
  `resultado` int(3) DEFAULT NULL,
  `descricao` text DEFAULT NULL,
  PRIMARY KEY (`id_transgenia`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8
	 */
		
		  public int inserir_transgenia(CadastroTransgenia transgenia) {
			  if( transgenia != null) {
		            Connection conn = null;
		            try {
		                conn = ConexaoBanco.getConexao();
		                String sql = "insert into transgenia\r\n" + 
		                		"(nome , teste, resultado, descricao, royalties) values ('"
		    	    			+ transgenia.getNome()
		    	    			+ "','"
		    	    				+ transgenia.getTeste()
		    	    			+ "','"
		    	    				+ transgenia.getResultado()
		    	    			+ "','"
		    	    			+ transgenia.getDescricao()
		    	    			+ "','"
		    	    			+ transgenia.getRoyalties()
		    	    			+ "')";
		    	       
		    	        PreparedStatement grava = (PreparedStatement) conn.prepareStatement(sql); 
		    	        grava.execute();    
		                JOptionPane.showMessageDialog(null, "transgenia Cadastrado");
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

		  
		  public ArrayList<CadastroTransgenia> gettransgenia() {
				
			  
		        Connection conn = null;
		        PreparedStatement pstm = null;
		        ResultSet rs = null;
		        ArrayList<CadastroTransgenia> listatransgenia = new ArrayList<CadastroTransgenia>();
		        try {
		            conn = ConexaoBanco.getConexao();
		            pstm = conn.prepareStatement("select * from transgenia");
		            rs = pstm.executeQuery();
		            while (rs.next()) {
		            	CadastroTransgenia transgenia = new CadastroTransgenia();
		            
		            	transgenia.setId_transgenia(rs.getInt("id_transgenia"));
		            	transgenia.setNome(rs.getString("nome"));
		            	transgenia.setDescricao(rs.getString("descricao"));
		            	transgenia.setTeste(rs.getInt("teste"));
		            	transgenia.setResultado(rs.getInt("resultado"));
		            	transgenia.setRoyalties(rs.getInt("royalties"));

		          
		            	listatransgenia.add(transgenia);
		            }
		            ConexaoBanco.fechaConexao(conn, pstm, rs);
		        } catch (Exception e) {
		        	  // JOptionPane.showMessageDialog(null, "Erro ao listar safras"  );
		        }
		        return listatransgenia;
		    }
		  
		  

		  
		  
		  public CadastroTransgenia gettransgenia(int id) {
				 String selectSilo = "select * from transgenia where id_transgenia = ?";

			   Connection conn = null;
		        PreparedStatement pstm = null;
		        ResultSet rs = null;	        
		        CadastroTransgenia transgenia = new CadastroTransgenia();

		        try {
		            conn = ConexaoBanco.getConexao();
		            pstm = conn.prepareStatement(selectSilo);
		            pstm.setInt(1, id);
		            rs = pstm.executeQuery();
		            rs.next();

		        	transgenia.setId_transgenia(rs.getInt("id_transgenia"));
	            	transgenia.setNome(rs.getString("nome"));
	            	transgenia.setDescricao(rs.getString("descricao"));
	            	transgenia.setTeste(rs.getInt("teste"));
	            	transgenia.setResultado(rs.getInt("resultado"));
	            	transgenia.setRoyalties(rs.getInt("royalties"));

	            	
		            
	            	return transgenia;
			          
		            
		        } catch (Exception e) {
		        	  //    JOptionPane.showMessageDialog(null, "Erro ao buscar por safra id: " + id + " erro: "  );
		            return null;
		        }
			  
		  }

	
	
public boolean atualizartransgenia(CadastroTransgenia transgenia) {
	if (transgenia != null) {
		try {
			Connection conn = null;
			String atualizar = null;
			PreparedStatement pstm;

			atualizar = "update transgenia set nome = ?, teste = ?, resultado = ?, descricao = ? , royalties = ? where id_transgenia = ? ";
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(atualizar);

			pstm.setString(1, transgenia.getNome());
			pstm.setInt(2, transgenia.getTeste());
			pstm.setInt(3, transgenia.getResultado());
			pstm.setString(4, transgenia.getDescricao());
			pstm.setInt(5,  transgenia.getRoyalties());

			pstm.setInt(6, transgenia.getId_transgenia());
			
			

			pstm.execute();
			// JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
			System.out.println("Transgenia Atualizada com sucesso");
			ConexaoBanco.fechaConexao(conn);
			return true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao atualizaro Transgenia no banco de dados\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
			return false;
		}
	} else {
		JOptionPane.showMessageDialog(null, "Os parametros estão vazios");
		return false;
	}
}

public boolean removertransgenia(int id) {
	String delete = "DELETE FROM transgenia WHERE id_transgenia = ?";
	Connection conn = null;
	ResultSet rs = null;
	try {
		conn = ConexaoBanco.getConexao();
		PreparedStatement pstm;
		pstm = conn.prepareStatement(delete);

		pstm.setInt(1, id);

		pstm.execute();
		ConexaoBanco.fechaConexao(conn, pstm);
		JOptionPane.showMessageDialog(null, "Transgenia Excluído, banco normalizado ");
		return true;

	} catch (Exception f) {
		JOptionPane.showMessageDialog(null,
				"Erro ao excluir o transgenia do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
						+ "dados " + f.getMessage());
		return false;
	}

}

}


