package conexaoBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import cadastros.CadastroContrato;
import cadastros.CadastroInformativo;
import cadastros.CadastroContrato.CadastroTransferenciaCarga;


public class GerenciarBancoInformativo {

	
	 public boolean getConexao() {
		 try {
			 
	            Connection conn = ConexaoBanco.getConexao();
                ConexaoBanco.encerrar(conn);
                
	            return true;
	            

		 }catch(Exception e) {
			 return false;
		 }
		 
		 
	 }
	
	
	  public boolean inserirInformativo(CadastroInformativo dados) {
		  if( dados != null) {
	            Connection conn = null;
	            try {
	                conn = ConexaoBanco.getConexao();
	                String sql = "insert into informativo\r\n" + 
	                		"(id_contrato, id_usuario, mensagem, hora_completa) values ('"
	    	    			+ dados.getId_contrato()
	    	    			+ "','"
	    	    			+ dados.getId_usuario()
	    	    			+ "','"
	    	    			+ dados.getMensagem()
	    	    			+ "','"
	    	    			+ dados.getHora_completa()
	    	    			+ "')";
	    	       
	    	        PreparedStatement grava = (PreparedStatement) conn.prepareStatement(sql); 
	    	        grava.execute();    
	             //   JOptionPane.showMessageDialog(null, "Informativo  inserido com sucesso");
	                ConexaoBanco.fechaConexao(conn, grava);
	                return true;
	 
	            } catch (Exception e) {
	               // JOptionPane.showMessageDialog(null, "Erro ao inserir a dado no banco de"
	                 //       + "dados "  );
	                return false;
	            }
	        } else {
	            System.out.println("O dado  enviado por parametro esta vazia");
	            return false;
	        }
	  }
	  
		public ArrayList<CadastroInformativo> getInformacoes() {
			String selectInformativos = "select * from informativo";
			Connection conn = null;
			PreparedStatement pstm = null;
			ResultSet rs = null;
			ArrayList<CadastroInformativo> lista_informativos = new ArrayList<>();
			try {
				conn = ConexaoBanco.getConexao();
				pstm = conn.prepareStatement(selectInformativos);
				// pstm.setString(1, chave);

				rs = pstm.executeQuery();
				while (rs.next()) {
					CadastroInformativo informacao = new CadastroInformativo();
				
					informacao.setId_informativo(rs.getInt("id_informativo"));
					informacao.setId_contrato(rs.getInt("id_contrato"));
					informacao.setId_usuario(rs.getInt("id_usuario"));
					informacao.setMensagem(rs.getString("mensagem"));
					informacao.setHora_completa(rs.getString("hora_completa"));
					

					lista_informativos.add(informacao);

				}
				ConexaoBanco.fechaConexao(conn, pstm, rs);
			} catch (Exception e) {
				//JOptionPane.showMessageDialog(null, "Erro ao listar informativos "  );
			}
			return lista_informativos;

		}
		
	
}
