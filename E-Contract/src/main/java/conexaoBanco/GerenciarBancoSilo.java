package main.java.conexaoBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroSilo;
import main.java.cadastros.FinanceiroConta;
import main.java.cadastros.CadastroSilo;


public class GerenciarBancoSilo {

	
		
	/*
	 * select * from safra sf
	inner join produto pd
	where sf.id_produto = pd.id_produto;
	 */
		
		  public int inserir_silo(CadastroSilo silo) {
			  if( silo != null) {
		            Connection conn = null;
		            try {
		                conn = ConexaoBanco.getConexao();
		                String sql = "insert into silo\r\n" + 
		                		"(id_armazem , nome_silo, identificador, capacidade, descricao) values ('"
		    	    			+ silo.getId_armazem()
		    	    			+ "','"
		    	    			+ silo.getNome_silo()
		    	    			+ "','"
		    	    			+ silo.getIdentificador()
		    	    			+ "','"
		    	    			+ silo.getCapacidade()
		    	    			+ "','"
		    	    			+ silo.getDescricao()
		    	    			+ "')";
		    	       
		    	        PreparedStatement grava = (PreparedStatement) conn.prepareStatement(sql); 
		    	        grava.execute();    
		                JOptionPane.showMessageDialog(null, "Silo Cadastrado");
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

		  
		  public ArrayList<CadastroSilo> getSilos() {
				
			  
		        Connection conn = null;
		        PreparedStatement pstm = null;
		        ResultSet rs = null;
		        ArrayList<CadastroSilo> listaSilos = new ArrayList<CadastroSilo>();
		        try {
		            conn = ConexaoBanco.getConexao();
		            pstm = conn.prepareStatement("select * from silo");
		            rs = pstm.executeQuery();
		            while (rs.next()) {
		            	CadastroSilo silo = new CadastroSilo();
		            	silo.setId_silo(rs.getInt("id_silo"));
		            	silo.setId_armazem(rs.getInt("id_armazem"));
		            	silo.setNome_silo(rs.getString("nome_silo"));
		            	silo.setIdentificador(rs.getString("identificador"));
		            	silo.setCapacidade(rs.getDouble("capacidade"));
		            	silo.setDescricao(rs.getString("descricao"));


		          
		            	listaSilos.add(silo);
		            }
		            ConexaoBanco.fechaConexao(conn, pstm, rs);
		        } catch (Exception e) {
		        	  // JOptionPane.showMessageDialog(null, "Erro ao listar safras"  );
		        }
		        return listaSilos;
		    }
		  
		  
		  public ArrayList<CadastroSilo> busca_silos() {
				
			  
		        Connection conn = null;
		        PreparedStatement pstm = null;
		        ResultSet rs = null;
		        ArrayList<CadastroSilo> listaSilos = new ArrayList<CadastroSilo>();
		        try {
		            conn = ConexaoBanco.getConexao();
		            pstm = conn.prepareStatement("select sl.*,\r\n"
		            		+ "case\r\n"
		            		+ "when armazem.tipo_cliente = '0' then armazem.nome_empresarial \r\n"
		            		+ " when armazem.tipo_cliente = '1' then armazem.nome_fantasia\r\n"
		            		+ "end  as nome_armazem\r\n"
		            		+ " from silo sl\r\n"
		            		+ "left join cliente armazem on armazem.id_cliente = sl.id_armazem");
		            rs = pstm.executeQuery();
		            
		            while (rs.next()) {
		            	CadastroSilo silo = new CadastroSilo();
		            	silo.setId_silo(rs.getInt("id_silo"));
		            	silo.setId_armazem(rs.getInt("id_armazem"));
		            	silo.setNome_silo(rs.getString("nome_silo"));
		            	silo.setIdentificador(rs.getString("identificador"));
		            	silo.setCapacidade(rs.getDouble("capacidade"));
		            	silo.setDescricao(rs.getString("descricao"));
		            	silo.setNome_armazem(rs.getString("nome_armazem"));


		          
		            	listaSilos.add(silo);
		            }
		            ConexaoBanco.fechaConexao(conn, pstm, rs);
		        } catch (Exception e) {
		        	  // JOptionPane.showMessageDialog(null, "Erro ao listar safras"  );
		        }
		        return listaSilos;
		    }
		  
		  
		  public CadastroSilo getSilo(int id) {
				 String selectSilo = "select * from silo where id_silo = ?";

			   Connection conn = null;
		        PreparedStatement pstm = null;
		        ResultSet rs = null;	        
		        CadastroSilo silo = new CadastroSilo();

		        try {
		            conn = ConexaoBanco.getConexao();
		            pstm = conn.prepareStatement(selectSilo);
		            pstm.setInt(1, id);
		            rs = pstm.executeQuery();
		            rs.next();

	            	silo.setId_silo(rs.getInt("id_silo"));
	            	silo.setId_armazem(rs.getInt("id_armazem"));
	            	silo.setNome_silo(rs.getString("nome_silo"));
	            	silo.setIdentificador(rs.getString("identificador"));
	            	silo.setCapacidade(rs.getDouble("capacidade"));
	            	silo.setDescricao(rs.getString("descricao"));
		            	
	            	return silo;
			          
		            
		        } catch (Exception e) {
		        	  //    JOptionPane.showMessageDialog(null, "Erro ao buscar por safra id: " + id + " erro: "  );
		            return null;
		        }
			  
		  }

	
	
public boolean atualizarSilo(CadastroSilo silo) {
	if (silo != null) {
		try {
			Connection conn = null;
			String atualizar = null;
			PreparedStatement pstm;

			atualizar = "update silo set id_armazem = ?, nome_silo = ?, identificador = ?,  capacidade = ?, descricao = ? where id_silo = ? ";
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(atualizar);

			pstm.setInt(1, silo.getId_armazem());
			pstm.setString(2, silo.getNome_silo());
			pstm.setString(3, silo.getIdentificador());
			pstm.setDouble(4, silo.getCapacidade());
			pstm.setString(5, silo.getDescricao());
			
			pstm.setInt(6, silo.getId_silo());

			pstm.execute();
			// JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
			System.out.println("Silo Atualizada com sucesso");
			ConexaoBanco.fechaConexao(conn);
			return true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao atualizaro Silo no banco de dados\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
			return false;
		}
	} else {
		JOptionPane.showMessageDialog(null, "Os parametros estão vazios");
		return false;
	}
}

public boolean removerSilo(int id) {
	String delete = "DELETE FROM silo WHERE id_silo = ?";
	Connection conn = null;
	ResultSet rs = null;
	try {
		conn = ConexaoBanco.getConexao();
		PreparedStatement pstm;
		pstm = conn.prepareStatement(delete);

		pstm.setInt(1, id);

		pstm.execute();
		ConexaoBanco.fechaConexao(conn, pstm);
		JOptionPane.showMessageDialog(null, "Silo Excluído, banco normalizado ");
		return true;

	} catch (Exception f) {
		JOptionPane.showMessageDialog(null,
				"Erro ao excluir o silo do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
						+ "dados " + f.getMessage());
		return false;
	}

}

}


