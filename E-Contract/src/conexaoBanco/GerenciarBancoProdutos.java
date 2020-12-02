package conexaoBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import cadastros.CadastroProduto;

public class GerenciarBancoProdutos {
	
	  private final String selectProdutos = "select * from produto";

	
	  public int inserir_produto(CadastroProduto produto) {
		  if( produto != null) {
	            Connection conn = null;
	            try {
	                conn = ConexaoBanco.getConexao();
	                String sql = "insert into produto\r\n" + 
	                		"(nome_produto, descricao_produto, codigo_produto) values ('"
	    	    			+ produto.getNome_produto()
	    	    			+ "','"
	    	    			+ produto.getDescricao_produto()	
	    	    			+ "','"
	    	    			+ produto.getCodigo()	
	    	    			+ "')";
	    	       
	    	        PreparedStatement grava = (PreparedStatement) conn.prepareStatement(sql); 
	    	        grava.execute();    
	                JOptionPane.showMessageDialog(null, "Produto  cadastrado com sucesso");
	                ConexaoBanco.fechaConexao(conn, grava);
	                return 1;
	 
	            } catch (Exception e) {
	                JOptionPane.showMessageDialog(null, "Erro ao inserir o produto no banco de"
	                        + "dados " + e.getMessage());
	                return 0;
	            }
	        } else {
	            System.out.println("O produto  enviado por parametro esta vazio");
	            return 0;
	        }
	  }

	  
	  public ArrayList<CadastroProduto> getProdutos() {
			
		  
	        Connection conn = null;
	        PreparedStatement pstm = null;
	        ResultSet rs = null;
	        ArrayList<CadastroProduto> listaProdutos = new ArrayList<CadastroProduto>();
	        try {
	            conn = ConexaoBanco.getConexao();
	            pstm = conn.prepareStatement(selectProdutos);
	            rs = pstm.executeQuery();
	            while (rs.next()) {
	                CadastroProduto produto = new CadastroProduto();
	 
	                produto.setId_produto(rs.getInt("id_produto"));
	                produto.setNome_produto(rs.getString("nome_produto"));
	                produto.setDescricao_produto(rs.getString("descricao_produto"));
	                produto.setCodigo(rs.getInt("codigo_produto"));
	                
	          
	                listaProdutos.add(produto);
	            }
	            ConexaoBanco.fechaConexao(conn, pstm, rs);
	        } catch (Exception e) {
	            JOptionPane.showMessageDialog(null, "Erro ao listar produtos" + e.getMessage());
	        }
	        return listaProdutos;
	    }
	
	  
}
