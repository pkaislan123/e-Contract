package main.java.conexaoBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroSilo;
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
		  
		  
		  public CadastroSilo getSafra(int id) {
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

	}

	
	


