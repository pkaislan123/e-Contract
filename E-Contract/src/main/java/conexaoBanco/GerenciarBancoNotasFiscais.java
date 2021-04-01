package main.java.conexaoBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import main.java.cadastros.CadastroNFe;




public class GerenciarBancoNotasFiscais {

	
	 public boolean getConexao() {
		 try {
			 
	            Connection conn = ConexaoBanco.getConexao();
                ConexaoBanco.encerrar(conn);
                
	            return true;
	            

		 }catch(Exception e) {
			 return false;
		 }
		 
		 
	 }
	
	 public String sql_nf(CadastroNFe nota) {
		 String data = "";
         SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");

         data = f.format(nota.getData());
         
		    String sql = "insert into nota_fiscal\r\n" + 
 		"(nfe, serie, natureza, protocolo, data_nfe, nome_remetente, inscricao_remetente, nome_destinatario,"
 		+ "inscricao_destinatario, produto, medida, quantidade, valor, caminho_arquivo) values ('"
 			+ nota.getNfe()
 			+ "','"
			+ nota.getSerie()
			+ "','"
			+ nota.getNatureza()
			+ "','"
			+ nota.getProtocolo()
			+ "','"
			+ data
			+ "','"
			+ nota.getNome_remetente()
			+ "','"
			+ nota.getInscricao_remetente()
			+ "','"
			+ nota.getNome_destinatario()
			+ "','"
			+ nota.getInscricao_destinatario()
			+ "','"
			+ nota.getProduto()
			+ "','"
			+ nota.getMedida()
			+ "','"
			+ nota.getQuantidade()
			+ "','"
			+ nota.getValor()
			+ "','"
			+ nota.getCaminho_arquivo()
			+ "')";
		    
		    return sql;

	 }
	
	  public int inserir_nf(CadastroNFe nota) {
		  if( nota != null) {
	            Connection conn = null;
	            try {
	                conn = ConexaoBanco.getConexao();
	                String sql = sql_nf(nota);
	              
	    	        PreparedStatement grava = (PreparedStatement) conn.prepareStatement(sql); 
	    	        grava.execute();    
	                ConexaoBanco.fechaConexao(conn, grava);
	                return 1;
	 
	            } catch (Exception e) {
	                JOptionPane.showMessageDialog(null, "Erro ao inserir a nota fiscal no banco de"
	                        + "dados \nErro: " + e.getMessage() + "\nCausa: " + e.getCause()  );
	                return 0;
	            }
	        } else {
	            System.out.println("A nota fiscal enviada por parametro esta vazia");
	            return 0;
	        }
	  }
	
	  
	  public ArrayList<CadastroNFe> listarNFs(){
		  Connection conn = null;
	        PreparedStatement pstm = null;
	        ResultSet rs = null;
	        String selectNfs = "select * from nota_fiscal";
	        ArrayList<CadastroNFe> lista_nfs = new ArrayList<CadastroNFe>();
	        try {
	            conn = ConexaoBanco.getConexao();
	            pstm = conn.prepareStatement(selectNfs);
	            rs = pstm.executeQuery();
	            while (rs.next()) {
	            	CadastroNFe nota = new CadastroNFe();
	 
	              nota.setId(rs.getInt("id_nf"));
	              nota.setNfe(rs.getString("nfe"));
	              nota.setSerie(rs.getString("serie"));
	              nota.setNatureza(rs.getString("natureza"));
	              nota.setProtocolo(rs.getString("protocolo"));

	          	String data = rs.getString("data_nfe");
            	Date date = new SimpleDateFormat("dd/MM/yyyy").parse(data);
            	nota.setData(date);
	              
	                nota.setNome_remetente(rs.getString("nome_remetente"));
	                nota.setInscricao_remetente(rs.getString("inscricao_remetente"));
	                nota.setNome_destinatario(rs.getString("nome_destinatario"));
	                nota.setInscricao_destinatario(rs.getString("inscricao_destinatario"));
	                nota.setProduto(rs.getString("produto"));
	                nota.setMedida(rs.getString("medida"));
	                nota.setQuantidade(rs.getString("quantidade"));
	                nota.setValor(rs.getString("valor"));
	                nota.setCaminho_arquivo(rs.getString("caminho_arquivo"));
	          
	                lista_nfs.add(nota);
	            }
	            ConexaoBanco.fechaConexao(conn, pstm, rs);
	        } catch (Exception e) {
	          // JOptionPane.showMessageDialog(null, "Erro ao listar nfs!\nErro: " + e.getMessage() + "\nCausa: " + e.getCause()  );
	        }
	        return lista_nfs;
	  }
	  
	  
	  public ArrayList<CadastroNFe> listarNFsPorCliente(String ie){
		  Connection conn = null;
	        PreparedStatement pstm = null;
	        ResultSet rs = null;
	        String selectNfs = "select * from nota_fiscal where inscricao_remetente = ? "
	        		+ "or inscricao_destinatario = ?";
	        ArrayList<CadastroNFe> lista_nfs = new ArrayList<CadastroNFe>();
	        try {
	            conn = ConexaoBanco.getConexao();
	            pstm = conn.prepareStatement(selectNfs);
	            pstm.setString(1,  ie);
	            pstm.setString(2,  ie);

	            rs = pstm.executeQuery();
	            while (rs.next()) {
	            	CadastroNFe nota = new CadastroNFe();
	 
	              nota.setId(rs.getInt("id_nf"));
	              nota.setNfe(rs.getString("nfe"));
	              nota.setSerie(rs.getString("serie"));
	              nota.setNatureza(rs.getString("natureza"));
	              nota.setProtocolo(rs.getString("protocolo"));

	          	String data = rs.getString("data_nfe");
            	Date date = new SimpleDateFormat("dd/MM/yyyy").parse(data);
            	nota.setData(date);
	              
	                nota.setNome_remetente(rs.getString("nome_remetente"));
	                nota.setInscricao_remetente(rs.getString("inscricao_remetente"));
	                nota.setNome_destinatario(rs.getString("nome_destinatario"));
	                nota.setInscricao_destinatario(rs.getString("inscricao_destinatario"));
	                nota.setProduto(rs.getString("produto"));
	                nota.setMedida(rs.getString("medida"));
	                nota.setQuantidade(rs.getString("quantidade"));
	                nota.setValor(rs.getString("valor"));
	                nota.setCaminho_arquivo(rs.getString("caminho_arquivo"));
	          
	                lista_nfs.add(nota);
	            }
	            ConexaoBanco.fechaConexao(conn, pstm, rs);
	        } catch (Exception e) {
	          // JOptionPane.showMessageDialog(null, "Erro ao listar nfs!\nErro: " + e.getMessage() + "\nCausa: " + e.getCause()  );
	        }
	        return lista_nfs;
	  }
	  
	  
	  public CadastroNFe getNota(int id_nota) {
		  Connection conn = null;
	        PreparedStatement pstm = null;
	        ResultSet rs = null;
	        String selectNfs = "select * fro nota_fiscal where id_nf = ?";
	        CadastroNFe nota = new CadastroNFe();
	        try {
	            conn = ConexaoBanco.getConexao();
	            pstm = conn.prepareStatement(selectNfs);
	            pstm.setInt(1,  id_nota);
	            rs = pstm.executeQuery();
	             rs.next();
	 
	 
	              nota.setId(rs.getInt("id_nf"));
	              nota.setNfe(rs.getString("nfe"));
	              nota.setSerie(rs.getString("serie"));
	              nota.setNatureza(rs.getString("natureza"));
	              nota.setProtocolo(rs.getString("protocolo"));

		          	String data = rs.getString("data_nfe");
	            	Date date = new SimpleDateFormat("dd/MM/yyyy").parse(data);
	            	nota.setData(date);

	                nota.setNome_destinatario(rs.getString("nome_remetente"));
	                nota.setInscricao_remetente(rs.getString("inscricao_remetente"));
	                nota.setNome_destinatario(rs.getString("nome_destinatario"));
	                nota.setInscricao_destinatario(rs.getString("inscricao_destinatario"));
	                nota.setProduto(rs.getString("produto"));
	                nota.setMedida(rs.getString("medida"));
	                nota.setQuantidade(rs.getString("quantidade"));
	                nota.setValor(rs.getString("valor"));
	                nota.setCaminho_arquivo(rs.getString("caminho_arquivo"));
	          
	            
	            ConexaoBanco.fechaConexao(conn, pstm, rs);
	            return nota;
	        } catch (Exception e) {
	          //  JOptionPane.showMessageDialog(null, "Erro ao listar produtos"  )
	        	return null;
	        }
	  }
	  
	  public boolean verificarRegistroNF(String codigo) {
		  Connection conn = null;
	        PreparedStatement pstm = null;
	        ResultSet rs = null;
	        String selectNfs = "select * from nota_fiscal where nfe = ?";
	        CadastroNFe nota = new CadastroNFe();
	        try {
	            conn = ConexaoBanco.getConexao();
	            pstm = conn.prepareStatement(selectNfs);
	            pstm.setString(1,  codigo);
	            rs = pstm.executeQuery();
	             rs.next();
	 
	 
	              nota.setId(rs.getInt("id_nf"));
	              nota.setNfe(rs.getString("nfe"));
	              nota.setSerie(rs.getString("serie"));
	              nota.setNatureza(rs.getString("natureza"));
	              nota.setProtocolo(rs.getString("protocolo"));


		          	String data = rs.getString("data_nfe");
	            	Date date = new SimpleDateFormat("dd/MM/yyyy").parse(data);
	            	nota.setData(date);
	              
	                nota.setNome_destinatario(rs.getString("nome_remetente"));
	                nota.setInscricao_remetente(rs.getString("inscricao_remetente"));
	                nota.setNome_destinatario(rs.getString("nome_destinatario"));
	                nota.setInscricao_destinatario(rs.getString("inscricao_destinatario"));
	                nota.setProduto(rs.getString("produto"));
	                nota.setMedida(rs.getString("medida"));
	                nota.setQuantidade(rs.getString("quantidade"));
	                nota.setValor(rs.getString("valor"));
	                nota.setCaminho_arquivo(rs.getString("caminho_arquivo"));
	          
	            
	            ConexaoBanco.fechaConexao(conn, pstm, rs);
	            if(nota.getNfe().equals(codigo))
	            return true;
	            else
	            	return false;
	        } catch (Exception e) {
	          //  JOptionPane.showMessageDialog(null, "Erro ao listar produtos"  )
	        	return false;
	        }
	  }
	  
	  
}
