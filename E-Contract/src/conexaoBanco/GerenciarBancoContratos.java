package conexaoBanco;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import cadastros.CadastroCliente;
import cadastros.CadastroContrato;
import cadastros.CadastroLogin;
import cadastros.CadastroModelo;
import cadastros.CadastroProduto;
import cadastros.CadastroSafra;
import outros.DadosGlobais;
import tratamento_proprio.Log;

public class GerenciarBancoContratos {

	private Log GerenciadorLog;
	private CadastroLogin loginGlobal;
	
	private Registro registro_geral_relacao_contrato_corretor = new Registro();
	private Registro registro_geral_relacao_contrato_comprador = new Registro();
	private Registro registro_geral_relacao_contrato_vendedor = new Registro();

	
	public GerenciarBancoContratos() {
		getDadosGlobais();

	}
	
	 public int inserir_modelo(CadastroModelo modelo) {
		  if( modelo != null) {
	            Connection conn = null;
	            try {
	                conn = ConexaoBanco.getConexao();
	                String sql = "insert into modelo (id_modelo, nome_modelo, descricao_modelo , arquivo) values (0, ?, ?, ?)";
	    	       
	    	        PreparedStatement grava = (PreparedStatement) conn.prepareStatement(sql); 
	    	        grava.setString(1,  modelo.getNome_modelo());
	    	        grava.setString(2,  modelo.getDescricao_modelo());
	    	        grava.setBytes(3,  modelo.getArquivo());
	    	        grava.execute();    
	                JOptionPane.showMessageDialog(null, "Modelo  cadastrado com sucesso");
	                ConexaoBanco.fechaConexao(conn, grava);
	                return 1;
	 
	            } catch (Exception e) {
	                JOptionPane.showMessageDialog(null, "Erro ao inserir o modelo no banco de"
	                        + "dados " + e.getMessage());
	                return 0;
	            }
	        } else {
	            System.out.println("O modelo enviado por parametro esta vazia");
	            return 0;
	        }
	  }

	
	  public ArrayList<CadastroModelo> getModelos() {
			
		  String selectModelos = "select * from modelo";
	        Connection conn = null;
	        PreparedStatement pstm = null;
	        ResultSet rs = null;
	        ArrayList<CadastroModelo> listaModelos = new ArrayList<CadastroModelo>();
	        try {
	            conn = ConexaoBanco.getConexao();
	            pstm = conn.prepareStatement(selectModelos);
	            rs = pstm.executeQuery();
	            
	         
	              while (rs.next()) {
	            	CadastroModelo modelo = new CadastroModelo();

	            	modelo.setId_modelo(rs.getInt("id_modelo"));;
	            	modelo.setDescricao_modelo(rs.getString("descricao_modelo"));
	            	modelo.setNome_modelo(rs.getString("nome_modelo"));
	            	modelo.setArquivo(rs.getBytes("arquivo"));

		            
	          
	            	listaModelos.add(modelo);
	            }
	            ConexaoBanco.fechaConexao(conn, pstm, rs);
	        } catch (Exception e) {
	            JOptionPane.showMessageDialog(null, "Erro ao listar modelos de contratos" + e.getMessage());
	        }
	        return listaModelos;
	    }
	  
	  
	  public int inserirContrato(CadastroContrato contrato) {
		  boolean reverter = false;
		  int retorno_contrato_inserido = inserir_contrato_retorno(contrato);
		  
		  if(retorno_contrato_inserido != -1) {
			  
			  //inserir registro na tabela de cliente_corretor
			  if(inserir_cliente_corretor(contrato,retorno_contrato_inserido)){
				  //tabelas de relacoes de cliente_corretor inseridos com sucesso, retorne um pra quem chamou
				  //prosseguir para inserir as tabelas clientes compradores
				  
				  //inserir registro na tabela de cliente_comprador

				  if(inserir_cliente_comprador(contrato,retorno_contrato_inserido )) {
					//tabelas de relacoes de cliente_corretor inseridos com sucesso, retorne um pra quem chamou
					  //prosseguir para inserir as tabelas clientes compradores
					  
					  //inserir registro na tabela de cliente_vendedor

					   if(inserir_cliente_vendedor(contrato, retorno_contrato_inserido))
					   {
						   
						   reverter = false;
					   }else {
						   //houve alguma excessao ao criar as tabelas relacação, devera ser feito o processo de
							  //exclusao dessas tabelas
							  /*fazer processo de exclusao das tabelas*/
						   reverter = true;
					   }
					  
					  
					  
					  
				  }else {
					  //houve alguma excessao ao criar as tabelas relacação, devera ser feito o processo de
					  //exclusao dessas tabelas
					  /*fazer processo de exclusao das tabelas*/
					  reverter = true;
					  
					  
				  }
				  
			  }else {
				  //houve alguma excessao ao criar as tabelas relacação, devera ser feito o processo de
				  //exclusao dessas tabelas
				  /*fazer processo de exclusao das tabelas*/
				  reverter = true;
			       }
			  
		  }else {
			  JOptionPane.showMessageDialog(null, "Erro ao inserir o Contrato no banco de"
                      + "dados\nNão houve corrupção na base de dados\nConsulte o administrador"
                      + "\nPossivéis erros relacionados a falta de conexão com o banco de dados " );
                
		  }
		  
		  if(reverter) {
			  //remover tabelas contrato_corretor
			  boolean v_remover_tabelas_relacao_contrato_corretor = remover_tabelas_contrato_corretor();
			  boolean v_remover_tabelas_relacao_contrato_comprador = remover_tabelas_contrato_comprador();
			  boolean v_remover_tabelas_relacao_contrato_vendedor = remover_tabelas_contrato_vendedor();

			  
			  if(v_remover_tabelas_relacao_contrato_corretor && v_remover_tabelas_relacao_contrato_comprador && v_remover_tabelas_relacao_contrato_vendedor) {
				  
				  JOptionPane.showMessageDialog(null, "Erro ao inserir o Contrato no banco de"
	                      + "dados\nBanco de Dados foi Normalizado!\nConsulte o administrador");
				  return 0;
			  }else {
				  JOptionPane.showMessageDialog(null, "Erro ao inserir o Contrato no banco de"
	                      + "dados\nHouve corrupção na base de dados\nConsulte o administrador");
	                return -1;
			  }
		  }else {
			  return 1;
		  }
		
		 
		  
	  }
	  
	  public int inserir_contrato_retorno(CadastroContrato contrato) {
		   int result = -1;
		   int id_cliente = -1;
		  if( contrato != null) {
	            Connection conn = null;
	            try {
	            	
	                conn = ConexaoBanco.getConexao();
	    	       
	                String query = sql_contrato(contrato);
                   Statement stmt = (Statement) conn.createStatement();
                   int numero = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

                   ResultSet rs = stmt.getGeneratedKeys();
                   if (rs.next()){
  	                                result=rs.getInt(1);
  	                                System.out.println("Id contrato inserido: "+ result);
                                 }
                    rs.close();
                    stmt.close();
                    
                    return result;
	 
	            } catch (Exception e) {
	               JOptionPane.showMessageDialog(null, "Erro ao inserir o Contrato no banco de"
	                      + "dados " + e.getMessage());
	            	GerenciadorLog.registrarLogDiario("falha", "falha ao adicionar novo contrato: " + e.getMessage() + " causa: " + e.getCause());
	                return -1;
	            }
	        } else {
	            System.out.println("O Contrato enviado por parametro esta vazio");
	            return -1;
	        }
	  }
	 
	  
	  public boolean inserir_cliente_corretor(CadastroContrato contrato, int id_contrato) {

		  boolean reverter = false;
		   
 		 CadastroCliente corretores [] = contrato.listaCorretores();
 		 for(int i = 0; i < corretores.length; i++) {
 			 if(corretores[i] != null) {
 		      if(inserir_corretor(corretores[i], id_contrato)) {
 		    	 //tabela criada com exito;
 		    	  System.out.println("Relação contrato corretor criado com sucesso");
 		    	 reverter = false;
 		      }else {
 		    	 //falha ao adicionar algum dos corretores, o processo deve ser desfeito
 		    	  System.out.println("falha ao adicionar a relação contrato corretor na base de dados");

 		    	 reverter = true;
 		    	  break;
 		      }
 			 }
 		 }
 		 
 		 if(reverter == true) {
 			 return false;
 			 //retorna fase, devera ser revertido o processo
 			 //reverter todo o processo
 		 }else {
 			 //todos os corretorres forama dicionados com sucesso, retorne true para continuar o processo
 			 return true;
 		 }
		  
	  }
	  
	  public boolean inserir_cliente_comprador(CadastroContrato contrato, int id_contrato) {

		  boolean reverter = false;
		   
 		 CadastroCliente compradores [] = contrato.listaCompradores();
 		 for(int i = 0; i < compradores.length; i++) {
 			 if(compradores[i] != null) {
 		      if(inserir_comprador(compradores[i], id_contrato)) {
 		    	 //tabela criada com exito;
 		    	  System.out.println("Relação contrato_comprador criado com sucesso");
 		    	 reverter = false;
 		      }else {
 		    	 //falha ao adicionar algum dos corretores, o processo deve ser desfeito
 		    	  System.out.println("falha ao adicionar a relação contrato_comprador na base de dados");

 		    	 reverter = true;
 		    	  break;
 		      }
 			 }
 		 }
 		 
 		 if(reverter == true) {
 			 return false;
 			 //retorna fase, devera ser revertido o processo
 			 //reverter todo o processo
 		 }else {
 			 //todos os corretorres forama dicionados com sucesso, retorne true para continuar o processo
 			 return true;
 		 }
		  
	  }
	  
	  public boolean inserir_cliente_vendedor (CadastroContrato contrato, int id_contrato) {

		  boolean reverter = false;
		   
 		 CadastroCliente vendedores [] = contrato.listaVendedores();
 		 for(int i = 0; i < vendedores.length; i++) {
 			 if(vendedores[i] != null) {
 		      if(inserir_vendedor(vendedores[i], id_contrato)) {
 		    	 //tabela criada com exito;
 		    	  System.out.println("Relação contrato_vendedor criado com sucesso");
 		    	 reverter = false;
 		      }else {
 		    	 //falha ao adicionar algum dos corretores, o processo deve ser desfeito
 		    	  System.out.println("falha ao adicionar a relação contrato_vendedor na base de dados");

 		    	 reverter = true;
 		    	  break;
 		      }
 			 }
 		 }
 		 
 		 if(reverter == true) {
 			 return false;
 			 //retorna fase, devera ser revertido o processo
 			 //reverter todo o processo
 		 }else {
 			 //todos os corretorres forama dicionados com sucesso, retorne true para continuar o processo
 			 return true;
 		 }
		  
	  }
	  
	
	  public boolean inserir_corretor(CadastroCliente corretor, int id_contrato){
		  Connection conn = null;
          try {
              conn = ConexaoBanco.getConexao();
              String sql = "insert into contrato_corretor\r\n" + 
              		"(id_contrato, id_cliente) values ('"
  	    			+id_contrato
  	    			+ "','"
  	    			+ corretor.getId()	
  	    			+ "')";
  	       
  	        PreparedStatement grava = (PreparedStatement) conn.prepareStatement(sql); 
  	        grava.execute();    
              ConexaoBanco.fechaConexao(conn, grava);
              JOptionPane.showMessageDialog(null, "Relação contrato_corretor  cadastrado com sucesso");
                registro_geral_relacao_contrato_corretor.setIdContrato(id_contrato);
                 registro_geral_relacao_contrato_corretor.adicionar_id(corretor.getId());
               return true;

          } catch (Exception e) {
        	  JOptionPane.showMessageDialog(null, "Erro ao inserir a relação cliente_corretor no banco de"
                       + " dados, corretor: " + corretor.getId() + " contrado: " + id_contrato + e.getMessage());
               return false;
          }
 			  
 	         
	  }
	  
	  public boolean inserir_comprador(CadastroCliente comprador, int id_contrato){
		  Connection conn = null;
          try {
              conn = ConexaoBanco.getConexao();
              String sql = "insert into contrato_comprador\r\n" + 
              		"(id_contrato, id_cliente) values ('"
  	    			+id_contrato
  	    			+ "','"
  	    			+ comprador.getId()	
  	    			+ "')";
  	       
  	        PreparedStatement grava = (PreparedStatement) conn.prepareStatement(sql); 
  	        grava.execute();    
              ConexaoBanco.fechaConexao(conn, grava);
              JOptionPane.showMessageDialog(null, "Relação contrato_comprador  cadastrado com sucesso");
                registro_geral_relacao_contrato_comprador.setIdContrato(id_contrato);
                 registro_geral_relacao_contrato_comprador.adicionar_id(comprador.getId());
               return true;

          } catch (Exception e) {
        	  JOptionPane.showMessageDialog(null, "Erro ao inserir a relação cliente_corretor no banco de"
                       + " dados, corretor: " + comprador.getId() + " contrado: " + id_contrato + e.getMessage());
               return false;
          }
 			  
 	         
	  }
	  
	  
	  public boolean inserir_vendedor(CadastroCliente vendedor, int id_contrato){
		  Connection conn = null;
          try {
              conn = ConexaoBanco.getConexao();
              String sql = "insert into contrato_vendedor\r\n" + 
              		"(id_contrato, id_cliente) values ('"
  	    			+id_contrato
  	    			+ "','"
  	    			+ vendedor.getId()	
  	    			+ "')";
  	       
  	        PreparedStatement grava = (PreparedStatement) conn.prepareStatement(sql); 
  	        grava.execute();    
              ConexaoBanco.fechaConexao(conn, grava);
              JOptionPane.showMessageDialog(null, "Relação contrato_vendedor cadastrado com sucesso");
                registro_geral_relacao_contrato_vendedor.setIdContrato(id_contrato);
                 registro_geral_relacao_contrato_vendedor.adicionar_id(vendedor.getId());
               return true;

          } catch (Exception e) {
        	  JOptionPane.showMessageDialog(null, "Erro ao inserir a relação cliente_vendedor no banco de"
                       + " dados, corretor: " + vendedor.getId() + " contrado: " + id_contrato + e.getMessage());
               return false;
          }
 			  
 	         
	  }
	  
	  
	  
	 
	  
	  public class Registro
	  {
		 //classe que registra durante o processo de cadastramento de contrato no banco de dados
		  //tanto o id_contrato como o id_cliente para que se houver falha o processo seja desfeito.
		  
		  private int id_contrato;
		  private ArrayList<Integer> ids_clientes;
		  public Registro() {
			  ids_clientes  = new ArrayList<Integer>();
			  
		  }
		 
		  private void setIdContrato(int idContrato) {
			  this.id_contrato = idContrato;
		  }
		  
		  private int getIdContrato() {
			  return this.id_contrato;
		  }
		  
		  private void adicionar_id(int id_adicionar) {
			  ids_clientes.add(id_adicionar);
		  }
		  
		  private ArrayList<Integer> getIds() {
			  return this.ids_clientes;
		  }
		  
	  }
	  
	  
	  


        
	  public void getDadosGlobais() {
			//gerenciador de log
			DadosGlobais dados = DadosGlobais.getInstance();
			 GerenciadorLog = dados.getGerenciadorLog();
			 
			 //usuario logado
			 loginGlobal = dados.getLogin();
		}
	
	  
	  public String sql_contrato(CadastroContrato contrato) {
          String query = "insert into contrato (codigo, id_safra, id_produto, medida, quantidade, valor_produto, valor_a_pagar, data_contrato, data_entrega, status_contrato) values ('"
        		 + contrato.getCodigo() 
                 + "','"
                + contrato.getModelo_safra().getId_safra()
                + "','"
                + contrato.getModelo_produto().getId_produto()
                + "','"
                + contrato.getMedida()
                + "','"
                + contrato.getQuantidade()
                + "','"
                + contrato.getValor_produto()
                + "','"
                + contrato.getValor_a_pagar()
                + "','"
                + contrato.getData_contrato()
                + "','"
                + contrato.getData_entrega()
                + "','"
                + contrato.getStatus_contrato()
                + "')";	
          return query;

	  }
	  
	  public boolean remover_contrato_corretor( int id_contrato, int id_cliente) {
		  String sql_delete_relacao = "DELETE FROM contrato_corretor WHERE id_contrato = ? and id_cliente = ?";
		  Connection conn = null;
	        ResultSet rs = null;	      
	        try {
	            conn = ConexaoBanco.getConexao();
	            PreparedStatement pstm;
	            pstm = conn.prepareStatement(sql_delete_relacao);
	 
	            pstm.setInt(1, id_contrato);
	            pstm.setInt(2,  id_cliente);
	 
	            pstm.execute();
	            ConexaoBanco.fechaConexao(conn, pstm);
	            JOptionPane.showMessageDialog(null, "Relação contrato_corretor excluido, banco normalizado ");
	           return true;
	            
	 
	        } catch (Exception f) {
	            JOptionPane.showMessageDialog(null, "Erro ao exlcuir relação contrato_corretor do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
	                    + "dados " + f.getMessage());
	           return false;
	        }
		  
		  
	  }
	  
	  public boolean remover_contrato_comprador( int id_contrato, int id_cliente) {
		  String sql_delete_relacao = "DELETE FROM contrato_comprador WHERE id_contrato = ? and id_cliente = ?";
		  Connection conn = null;
	        ResultSet rs = null;	      
	        try {
	            conn = ConexaoBanco.getConexao();
	            PreparedStatement pstm;
	            pstm = conn.prepareStatement(sql_delete_relacao);
	 
	            pstm.setInt(1, id_contrato);
	            pstm.setInt(2,  id_cliente);
	 
	            pstm.execute();
	            ConexaoBanco.fechaConexao(conn, pstm);
	            JOptionPane.showMessageDialog(null, "Relação contrato_comprador excluido, banco normalizado ");
	           return true;
	            
	 
	        } catch (Exception f) {
	            JOptionPane.showMessageDialog(null, "Erro ao exlcuir relação contrato_comprador do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
	                    + "dados " + f.getMessage());
	           return false;
	        }
		  
		  
	  }
	  
	  public boolean remover_contrato_vendedor( int id_contrato, int id_cliente) {
		  String sql_delete_relacao = "DELETE FROM contrato_vendedor WHERE id_contrato = ? and id_cliente = ?";
		  Connection conn = null;
	        ResultSet rs = null;	      
	        try {
	            conn = ConexaoBanco.getConexao();
	            PreparedStatement pstm;
	            pstm = conn.prepareStatement(sql_delete_relacao);
	 
	            pstm.setInt(1, id_contrato);
	            pstm.setInt(2,  id_cliente);
	 
	            pstm.execute();
	            ConexaoBanco.fechaConexao(conn, pstm);
	            JOptionPane.showMessageDialog(null, "Relação contrato_vendedor excluido, banco normalizado ");
	           return true;
	            
	 
	        } catch (Exception f) {
	            JOptionPane.showMessageDialog(null, "Erro ao exlcuir relação contrato_vendedor do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
	                    + "dados " + f.getMessage());
	           return false;
	        }
		  
		  
	  }
	  
	  
	  public boolean remover_contrato(int id_contrato) {
		  String sql_delete_relacao = "DELETE FROM contrato WHERE id = ?";
		  Connection conn = null;
	        ResultSet rs = null;	      
	        try {
	            conn = ConexaoBanco.getConexao();
	            PreparedStatement pstm;
	            pstm = conn.prepareStatement(sql_delete_relacao);
	 
	            pstm.setInt(1, id_contrato);
	 
	            pstm.execute();
	            ConexaoBanco.fechaConexao(conn, pstm);
	            JOptionPane.showMessageDialog(null, "Contrato excluido com sucesso!");
	           return true;
	            
	 
	        } catch (Exception f) {
	            JOptionPane.showMessageDialog(null, "Erro ao excluir contrato da base de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
	                    + "dados " + f.getMessage());
	           return false;
	        }
	  }
	  
	  public boolean remover_tabelas_contrato_corretor() {
		  boolean retorno = false;
		  
		  for(int codigo_relacao_contrato_corretor : registro_geral_relacao_contrato_corretor.getIds()) {
				 if (remover_contrato_corretor( registro_geral_relacao_contrato_corretor.getIdContrato(), codigo_relacao_contrato_corretor)) {
					 retorno = true;
				 }else {
					 retorno = false;
					 break;
				 }
			  }
		  
		  return retorno;
	  }
	  
	  public boolean remover_tabelas_contrato_comprador() {
		  boolean retorno = false;
		  
		  for(int codigo_relacao_contrato_corretor : registro_geral_relacao_contrato_comprador.getIds()) {
				 if (remover_contrato_corretor( registro_geral_relacao_contrato_comprador.getIdContrato(), codigo_relacao_contrato_corretor)) {
					 retorno = true;
				 }else {
					 retorno = false;
					 break;
				 }
			  }
		  
		  return retorno;
	  }
	  
	  public boolean remover_tabelas_contrato_vendedor() {
		  boolean retorno = false;
		  
		  for(int codigo_relacao_contrato_corretor : registro_geral_relacao_contrato_vendedor.getIds()) {
				 if (remover_contrato_corretor( registro_geral_relacao_contrato_vendedor.getIdContrato(), codigo_relacao_contrato_corretor)) {
					 retorno = true;
				 }else {
					 retorno = false;
					 break;
				 }
			  }
		  
		  return retorno;
	  }
	  
	  
	  
}