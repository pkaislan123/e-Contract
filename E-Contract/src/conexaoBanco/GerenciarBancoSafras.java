package conexaoBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import cadastros.CadastroProduto;
import cadastros.CadastroSafra;

public class GerenciarBancoSafras {
	
	
	private final String selectSafras = "select * from safra sf inner join produto pd where sf.id_produto = pd.id_produto";
/*
 * select * from safra sf
inner join produto pd
where sf.id_produto = pd.id_produto;
 */
	
	  public int inserir_safra(CadastroSafra safra) {
		  if( safra != null) {
	            Connection conn = null;
	            try {
	                conn = ConexaoBanco.getConexao();
	                String sql = "insert into safra\r\n" + 
	                		"(descricao_safra , ano_colheita, ano_plantio, id_produto, codigo_safra) values ('"
	    	    			+ safra.getDescricao_safra()
	    	    			+ "','"
	    	    			+ safra.getAno_colheita()
	    	    			+ "','"
	    	    			+ safra.getAno_plantio()
	    	    			+ "','"
	    	    			+ safra.getProduto().getId_produto()
	    	    			+ "','"
	    	    			+ safra.getCodigo()
	    	    			+ "')";
	    	       
	    	        PreparedStatement grava = (PreparedStatement) conn.prepareStatement(sql); 
	    	        grava.execute();    
	                JOptionPane.showMessageDialog(null, "Safra  cadastrada com sucesso");
	                ConexaoBanco.fechaConexao(conn, grava);
	                return 1;
	 
	            } catch (Exception e) {
	                JOptionPane.showMessageDialog(null, "Erro ao inserir a safra no banco de"
	                        + "dados " + e.getMessage());
	                return 0;
	            }
	        } else {
	            System.out.println("A safra enviado por parametro esta vazia");
	            return 0;
	        }
	  }

	  
	  public ArrayList<CadastroSafra> getSafras() {
			
		  
	        Connection conn = null;
	        PreparedStatement pstm = null;
	        ResultSet rs = null;
	        ArrayList<CadastroSafra> listaSafras = new ArrayList<CadastroSafra>();
	        try {
	            conn = ConexaoBanco.getConexao();
	            pstm = conn.prepareStatement(selectSafras);
	            rs = pstm.executeQuery();
	            while (rs.next()) {
	            	CadastroSafra safra = new CadastroSafra();
	    	        CadastroProduto produto = new CadastroProduto();

	            	safra.setId_safra(rs.getInt("id_safra"));;
	            	safra.setDescricao_safra(rs.getString("descricao_safra"));
	            	safra.setAno_plantio(rs.getInt("ano_plantio"));
	            	safra.setAno_colheita(rs.getInt("ano_colheita"));
	            	safra.setCodigo(rs.getInt("codigo_safra"));
	            	produto.setId_produto(rs.getInt("id_produto"));
	            	produto.setDescricao_produto(rs.getString("descricao_produto"));
	            	produto.setNome_produto(rs.getString("nome_produto"));
	            	produto.setTransgenia(rs.getString("transgenia"));
	            	safra.setProduto(produto);

	          
	            	listaSafras.add(safra);
	            }
	            ConexaoBanco.fechaConexao(conn, pstm, rs);
	        } catch (Exception e) {
	            JOptionPane.showMessageDialog(null, "Erro ao listar safras" + e.getMessage());
	        }
	        return listaSafras;
	    }
	  
	  
	  public CadastroSafra getSafra(int id) {
			 String selectSafra = "select * from safra where id_safra = ?";

		   Connection conn = null;
	        PreparedStatement pstm = null;
	        ResultSet rs = null;	        
	        CadastroSafra safra = new CadastroSafra();

	        try {
	            conn = ConexaoBanco.getConexao();
	            pstm = conn.prepareStatement(selectSafra);
	            pstm.setInt(1, id);
	            rs = pstm.executeQuery();
	            rs.next();

	            	safra.setId_safra(rs.getInt("id_safra"));;
	            	safra.setDescricao_safra(rs.getString("descricao_safra"));
	            	safra.setAno_plantio(rs.getInt("ano_plantio"));
	            	safra.setAno_colheita(rs.getInt("ano_colheita"));
	            	safra.setCodigo(rs.getInt("codigo_safra"));
	            	
	            	int id_produto = rs.getInt("id_produto");
	            	
	            	GerenciarBancoProdutos gerenciar = new GerenciarBancoProdutos();
	            	CadastroProduto produto = gerenciar.getProduto(id_produto);
	            	
	            	if(produto != null) {
	            		safra.setProduto(produto);
	            	    ConexaoBanco.fechaConexao(conn, pstm, rs);
		    	        return safra;
	            	}
	            	else {
	            		return null;
	            	}
		          
	            
	        } catch (Exception e) {
	            JOptionPane.showMessageDialog(null, "Erro ao buscar por safra id: " + id + " erro: " + e.getMessage());
	            return null;
	        }
		  
	  }

}
