
package main.java.conexaoBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroAcessoTemporario;
import main.java.cadastros.FinanceiroConta;
import main.java.cadastros.CadastroAcessoTemporario;


public class GerenciarBancoAcessoTemporario {

	
		
	/*
	CREATE TABLE `acesso_temporario` (
  `id_acesso` int(5) NOT NULL AUTO_INCREMENT,
  `id_usuario` int(5) NOT NULL,
  `data_inicial` varchar(40) DEFAULT NULL,
  `hora_inicial` varchar(40) DEFAULT NULL,
  `data_final` varchar(40) DEFAULT NULL,
  `hora_final` varchar(40) NOT NULL,
  `modulo` int(3) NOT NULL,
 
  PRIMARY KEY (`id_acesso`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8
	 */
		
		  public int inserir_acesso(CadastroAcessoTemporario acesso) {
			  if( acesso != null) {
		            Connection conn = null;
		            try {
		                conn = ConexaoBanco.getConexao();
		                String sql = "insert into acesso_temporario\r\n" + 
		                		"(id_usuario_criador, id_usuario_executor , data_inicial, hora_inicial, data_final, hora_final, modulo) values ('"
		    	    			+ acesso.getId_usuario_criador()
		    	    			+ "','"
		    	    			+ acesso.getId_usuario_executor()
		    	    			+ "','"
		    	    			+ acesso.getData_inicial()
		    	    			+ "','"
		    	    			+ acesso.getHora_inicial()
		    	    			+ "','"
		    	    			+ acesso.getData_final()
		    	    			+ "','"
		    	    			+ acesso.getHora_final()
		    	    			+ "','"
		    	    			+ acesso.getModulo()
		    	    			+ "')";
		    	       
		    	        PreparedStatement grava = (PreparedStatement) conn.prepareStatement(sql); 
		    	        grava.execute();    
		                JOptionPane.showMessageDialog(null, "Acesso Temporário Cadastrado");
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

		  
		  public ArrayList<CadastroAcessoTemporario> getAcessosTemporarios() {
				
			  
		        Connection conn = null;
		        PreparedStatement pstm = null;
		        ResultSet rs = null;
		        ArrayList<CadastroAcessoTemporario> listaSilos = new ArrayList<CadastroAcessoTemporario>();
		        try {
		            conn = ConexaoBanco.getConexao();
		            pstm = conn.prepareStatement("select at.*,\r\n"
		            		+ "CONCAT(criador.nome, \" \", criador.sobrenome) as nome_criador,\r\n"
		            		+ "CONCAT(executor.nome, \" \", executor.sobrenome) as nome_executor\r\n"
		            		+ " from acesso_temporario at\r\n"
		            		+ "left join usuarios criador on criador.id_usuario = at.id_usuario_criador\r\n"
		            		+ "left join usuarios executor on executor.id_usuario = at.id_usuario_executor");
		            rs = pstm.executeQuery();
		            while (rs.next()) {
		            	CadastroAcessoTemporario acesso = new CadastroAcessoTemporario();
		            	acesso.setId_acesso(rs.getInt("id_acesso"));
		            	acesso.setId_usuario_criador(rs.getInt("id_usuario_criador"));
		            	acesso.setId_usuario_executor(rs.getInt("id_usuario_executor"));
		            	acesso.setData_inicial(rs.getString("data_inicial"));
		            	acesso.setHora_inicial(rs.getString("hora_inicial"));
		            	acesso.setNome_criador(rs.getString("nome_criador"));
		            	acesso.setNome_executor(rs.getString("nome_executor"));

		            	
		            	acesso.setData_final(rs.getString("data_final"));
		            	acesso.setHora_final(rs.getString("hora_final"));
		            	
		            	acesso.setModulo(rs.getInt("modulo"));

		          
		            	listaSilos.add(acesso);
		            }
		            ConexaoBanco.fechaConexao(conn, pstm, rs);
		        } catch (Exception e) {
		        	  // JOptionPane.showMessageDialog(null, "Erro ao listar safras"  );
		        }
		        return listaSilos;
		    }
		  
		  
		  public ArrayList<CadastroAcessoTemporario> getAcessosTemporariosPorExecutor(int id_executor) {
				
			  
		        Connection conn = null;
		        PreparedStatement pstm = null;
		        ResultSet rs = null;
		        ArrayList<CadastroAcessoTemporario> listaSilos = new ArrayList<CadastroAcessoTemporario>();
		        try {
		            conn = ConexaoBanco.getConexao();
		            pstm = conn.prepareStatement("select at.*,\r\n"
		            		+ "CONCAT(criador.nome, \" \", criador.sobrenome) as nome_criador,\r\n"
		            		+ "CONCAT(executor.nome, \" \", executor.sobrenome) as nome_executor\r\n"
		            		+ " from acesso_temporario at\r\n"
		            		+ "left join usuarios criador on criador.id_usuario = at.id_usuario_criador\r\n"
		            		+ "left join usuarios executor on executor.id_usuario = at.id_usuario_executor where id_usuario_executor = ?");
		            pstm.setInt(1, id_executor);
		            rs = pstm.executeQuery();
		            while (rs.next()) {
		            	CadastroAcessoTemporario acesso = new CadastroAcessoTemporario();
		            	acesso.setId_acesso(rs.getInt("id_acesso"));
		            	acesso.setId_usuario_criador(rs.getInt("id_usuario_criador"));
		            	acesso.setId_usuario_executor(rs.getInt("id_usuario_executor"));
		            	acesso.setData_inicial(rs.getString("data_inicial"));
		            	acesso.setHora_inicial(rs.getString("hora_inicial"));
		            	acesso.setNome_criador(rs.getString("nome_criador"));
		            	acesso.setNome_executor(rs.getString("nome_executor"));

		            	acesso.setData_final(rs.getString("data_final"));
		            	acesso.setHora_final(rs.getString("hora_final"));
		            	
		            	acesso.setModulo(rs.getInt("modulo"));

		          
		            	listaSilos.add(acesso);
		            }
		            ConexaoBanco.fechaConexao(conn, pstm, rs);
		        } catch (Exception e) {
		        	  // JOptionPane.showMessageDialog(null, "Erro ao listar safras"  );
		        }
		        return listaSilos;
		    }
		  
		  
		 
public boolean removerAcessoTemporario(int id) {
	String delete = "DELETE FROM acesso_temporario WHERE id_acesso = ?";
	Connection conn = null;
	ResultSet rs = null;
	try {
		conn = ConexaoBanco.getConexao();
		PreparedStatement pstm;
		pstm = conn.prepareStatement(delete);

		pstm.setInt(1, id);

		pstm.execute();
		ConexaoBanco.fechaConexao(conn, pstm);
		JOptionPane.showMessageDialog(null, "Acesso Temporário Excluído, banco normalizado ");
		return true;

	} catch (Exception f) {
		JOptionPane.showMessageDialog(null,
				"Erro ao excluir o acesso do banco de dados!\nConsulte o administrador do sistema"
						+ "dados " + f.getMessage());
		return false;
	}

}

}


