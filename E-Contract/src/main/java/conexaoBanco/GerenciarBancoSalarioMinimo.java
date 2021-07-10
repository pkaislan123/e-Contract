

package main.java.conexaoBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroFuncionarioSalarioMinimo;
import main.java.cadastros.FinanceiroConta;
import main.java.cadastros.CadastroFuncionarioSalarioMinimo;


public class GerenciarBancoSalarioMinimo {

	
		
	/*
	 * CREATE TABLE `salario_minimo` (
	  `id_salario_minimo` int(3) NOT NULL AUTO_INCREMENT,
	  `valor` double DEFAULT NULL,
	  `data_inicial` varchar(40) DEFAULT NULL,
	  `data_final` varchar(40) DEFAULT NULL,
	  `descricao` text DEFAULT NULL,
	  PRIMARY KEY (`id_salario_minimo`)
	) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8
	 */
		
		  public int inserir_salario_minimo(CadastroFuncionarioSalarioMinimo salario_minimo) {
			  if( salario_minimo != null) {
		            Connection conn = null;
		            try {
		                conn = ConexaoBanco.getConexao();
		                String sql = "insert into salario_minimo\r\n" + 
		                		"(id_salario_minimo , valor, data_inicial, data_final, descricao) values ('"
		    	    			+ salario_minimo.getId()
		    	    			+ "','"
		    	    			+ salario_minimo.getValor()
		    	    			+ "','"
		    	    			+ salario_minimo.getData_inicial()
		    	    			+ "','"
		    	    			+ salario_minimo.getData_final()
		    	    			+ "','"
		    	    			+ salario_minimo.getDescricao()
		    	    			+ "')";
		    	       
		    	        PreparedStatement grava = (PreparedStatement) conn.prepareStatement(sql); 
		    	        grava.execute();    
		                JOptionPane.showMessageDialog(null, "Salario Minimo Cadastrado");
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

		  
		  public ArrayList<CadastroFuncionarioSalarioMinimo> getSalarioMinimos() {
				
			  
		        Connection conn = null;
		        PreparedStatement pstm = null;
		        ResultSet rs = null;
		        ArrayList<CadastroFuncionarioSalarioMinimo> listaSalarioMinimos = new ArrayList<CadastroFuncionarioSalarioMinimo>();
		        try {
		            conn = ConexaoBanco.getConexao();
		            pstm = conn.prepareStatement("select * from salario_minimo");
		            rs = pstm.executeQuery();
		            while (rs.next()) {
		            	CadastroFuncionarioSalarioMinimo salario_minimo = new CadastroFuncionarioSalarioMinimo();
		            	salario_minimo.setId(rs.getInt("id_salario_minimo"));
		            	salario_minimo.setValor(rs.getInt("valor"));
		            	salario_minimo.setData_inicial(rs.getString("data_inicial"));
		            	salario_minimo.setData_final(rs.getString("data_final"));
		            	salario_minimo.setDescricao(rs.getString("descricao"));


		          
		            	listaSalarioMinimos.add(salario_minimo);
		            }
		            ConexaoBanco.fechaConexao(conn, pstm, rs);
		        } catch (Exception e) {
		        	  // JOptionPane.showMessageDialog(null, "Erro ao listar safras"  );
		        }
		        return listaSalarioMinimos;
		    }
		  
		  
		  
		  public CadastroFuncionarioSalarioMinimo getSalarioMinimo(int id) {
				 String selectSalarioMinimo = "select * from salario_minimo where id_salario_minimo = ?";

			   Connection conn = null;
		        PreparedStatement pstm = null;
		        ResultSet rs = null;	        
		        CadastroFuncionarioSalarioMinimo salario_minimo = new CadastroFuncionarioSalarioMinimo();

		        try {
		            conn = ConexaoBanco.getConexao();
		            pstm = conn.prepareStatement(selectSalarioMinimo);
		            pstm.setInt(1, id);
		            rs = pstm.executeQuery();
		            rs.next();

		            salario_minimo.setId(rs.getInt("id_salario_minimo"));
	            	salario_minimo.setValor(rs.getInt("valor"));
	            	salario_minimo.setData_inicial(rs.getString("data_inicial"));
	            	salario_minimo.setData_final(rs.getString("data_final"));
	            	salario_minimo.setDescricao(rs.getString("descricao"));

		            	
	            	return salario_minimo;
			          
		            
		        } catch (Exception e) {
		        	  //    JOptionPane.showMessageDialog(null, "Erro ao buscar por safra id: " + id + " erro: "  );
		            return null;
		        }
			  
		  }

	
	
public boolean atualizarSalarioMinimo(CadastroFuncionarioSalarioMinimo salario_minimo) {
	if (salario_minimo != null) {
		try {
			Connection conn = null;
			String atualizar = null;
			PreparedStatement pstm;

			atualizar = "update salario_minimo set id_salario_minimo = ?, valor = ?, data_inicial = ?,  data_final = ?, descricao = ? where id_salario_minimo = ? ";
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(atualizar);

			pstm.setDouble(1, salario_minimo.getValor());
			pstm.setString(2, salario_minimo.getData_inicial());
			pstm.setString(3, salario_minimo.getData_final());
			pstm.setString(4, salario_minimo.getDescricao());
			
			pstm.setInt(5, salario_minimo.getId());

			pstm.execute();
			// JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
			System.out.println("Salario Minimo Atualizada com sucesso");
			ConexaoBanco.fechaConexao(conn);
			return true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao atualizar o Salario Minimo no banco de dados\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
			return false;
		}
	} else {
		JOptionPane.showMessageDialog(null, "Os parametros estão vazios");
		return false;
	}
}

public boolean removerSalarioMinimo(int id) {
	String delete = "DELETE FROM salario_minimo WHERE id_salario_minimo = ?";
	Connection conn = null;
	ResultSet rs = null;
	try {
		conn = ConexaoBanco.getConexao();
		PreparedStatement pstm;
		pstm = conn.prepareStatement(delete);

		pstm.setInt(1, id);

		pstm.execute();
		ConexaoBanco.fechaConexao(conn, pstm);
		JOptionPane.showMessageDialog(null, "Salario Minimo Excluído, banco normalizado ");
		return true;

	} catch (Exception f) {
		JOptionPane.showMessageDialog(null,
				"Erro ao excluir o salario_minimo do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
						+ "dados " + f.getMessage());
		return false;
	}

}


public double getSalarioMinimoVigente(String data_atual) {
	 String selectSalarioMinimo = "select valor from\r\n"
	 		+ "salario_minimo sm\r\n"
	 		+ "where STR_TO_DATE(?, '%d/%m/%Y') > STR_TO_DATE(sm.data_inicial, '%d/%m/%Y')\r\n"
	 		+ "and\r\n"
	 		+ " STR_TO_DATE(?, '%d/%m/%Y') < STR_TO_DATE(sm.data_final, '%d/%m/%Y')";

  Connection conn = null;
   PreparedStatement pstm = null;
   ResultSet rs = null;	        
   CadastroFuncionarioSalarioMinimo salario_minimo = new CadastroFuncionarioSalarioMinimo();

   try {
       conn = ConexaoBanco.getConexao();
       pstm = conn.prepareStatement(selectSalarioMinimo);
       pstm.setString(1, data_atual);
       pstm.setString(2, data_atual);

       rs = pstm.executeQuery();
       rs.next();

       double valor = rs.getDouble("valor");
       	
   	return valor;
         
       
   } catch (Exception e) {
   	  //    JOptionPane.showMessageDialog(null, "Erro ao buscar por safra id: " + id + " erro: "  );
       return 0;
   }
 
}


}


