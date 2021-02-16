package conexaoBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JOptionPane;


public class GerenciarBancoPadrao {

	
	 public boolean getConexao() {
		 try {
			 
	            Connection conn = ConexaoBanco.getConexao();
                ConexaoBanco.encerrar(conn);
                
	            return true;
	            

		 }catch(Exception e) {
			 return false;
		 }
		 
		 
	 }
	
	
	  public int insert(String dados) {
		  if( dados != null) {
	            Connection conn = null;
	            try {
	                conn = ConexaoBanco.getConexao();
	                String sql = null;
	                /*String sql = "insert into compras\r\n" + 
	                		"(nome, id_comprador, tipo, id_estoque, descricao, data_compra, valor) values ('"
	    	    			+ dado.getNome()
	    	    			+ "','"
	    	    			+ compra.getId_comprador()
	    	    			+ "','"
	    	    			+ compra.getTipo()
	    	    			+ "','"
	    	    			+ compra.getId_estoque()
	    	    			+ "','"
	    	    			+ compra.getDescricao()
	    	    			+ "','"
	    	    			+ compra.getData_compra()
	    	    			+ "','"
	    	    			+ compra.getValor()
	    	    			+ "')";
	    	       */
	    	        PreparedStatement grava = (PreparedStatement) conn.prepareStatement(sql); 
	    	        grava.execute();    
	                JOptionPane.showMessageDialog(null, "Dado  inserido com sucesso");
	                ConexaoBanco.fechaConexao(conn, grava);
	                return 1;
	 
	            } catch (Exception e) {
	                JOptionPane.showMessageDialog(null, "Erro ao inserir a dado no banco de"
	                        + "dados "  );
	                return 0;
	            }
	        } else {
	            System.out.println("O dado  enviado por parametro esta vazia");
	            return 0;
	        }
	  }
	
}
