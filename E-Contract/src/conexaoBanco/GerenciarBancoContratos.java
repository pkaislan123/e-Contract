package conexaoBanco;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.mysql.cj.exceptions.RSAException;

import cadastros.CadastroCliente;
import cadastros.CadastroContrato;
import cadastros.CadastroLogin;
import cadastros.CadastroModelo;
import cadastros.CadastroProduto;
import cadastros.CadastroSafra;
import cadastros.ContaBancaria;
import outros.DadosGlobais;
import tratamento_proprio.Log;

public class GerenciarBancoContratos {

	private Log GerenciadorLog;
	private CadastroLogin loginGlobal;
	
	private Registro registro_geral_relacao_contrato_corretor = new Registro();
	private Registro registro_geral_relacao_contrato_comprador = new Registro();
	private Registro registro_geral_relacao_contrato_vendedor = new Registro();
	private Registro registro_geral_relacao_contrato_modelo_pagamento = new Registro();
	private Registro registro_geral_relacao_contrato_sub_contrato = new Registro();


	
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
	  
	  
	  public int inserirContrato(CadastroContrato contrato, CadastroContrato contrato_pai) {
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
						   if(inserirModelosPagamentos(retorno_contrato_inserido, contrato.getPagamentos())) {
							   
							   
							   if(contrato.getSub_contrato() == 0) {
								   //e um contrato pai, nao fazer mais nada
								   reverter = false;

							   }else {
								   //e um contrato filho, criar relacao;
								   if( inserir_contrato_sub_contrato(contrato_pai.getId(), retorno_contrato_inserido)) {
									   //inseriu corretamente
									   
								   }else {
									   //houve alguma excessao ao criar as tabelas relacao contrato_sub_contrato, devera ser feito o processo de
										  //exclusao dessas tabelas
										  /*fazer processo de exclusao das tabelas*/
									   reverter = true;
								   }
							   }

						   }else {
							   //houve alguma excessao ao criar as tabelas relacao_contrato_pagamentos, devera ser feito o processo de
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
				  //houve alguma excessao ao criar as tabelas relacação, devera ser feito o processo de
				  //exclusao dessas tabelas
				  /*fazer processo de exclusao das tabelas*/
				  reverter = true;
			       }
			  
		  }else {
			  JOptionPane.showMessageDialog(null, "Erro ao inserir o Contrato no banco de "
                      + "dados\nNão houve corrupção na base de dados\nConsulte o administrador"
                      + "\nPossivéis erros relacionados a falta de conexão com o banco de dados " );
                
		  }
		  
		  if(reverter) {
			  //remover tabelas contrato_corretor
			  boolean v_remover_tabelas_relacao_contrato_corretor = remover_tabelas_contrato_corretor();
			  boolean v_remover_tabelas_relacao_contrato_comprador = remover_tabelas_contrato_comprador();
			  boolean v_remover_tabelas_relacao_contrato_vendedor = remover_tabelas_contrato_vendedor();
              boolean  v_remover_tabelas_relacao_contrato_modelo_pagamento = remover_tabelas_contrato_modelo_pagamento();
              boolean  v_remover_tabelas_modelo_pagamento = remover_tabelas_modelo_pagamento();
              boolean  v_remover_tabelas_contrato_sub_contrato = remover_tabelas_contrato_sub_contrato();
              boolean  v_remover_sub_contrato = false;

              
              if(contrato.getSub_contrato() == 1) {
            	  //remover o sub_contrato criado
            	  
            	  v_remover_sub_contrato = remover_contrato(retorno_contrato_inserido);
              }
              
			  if(v_remover_tabelas_relacao_contrato_corretor && v_remover_tabelas_relacao_contrato_comprador && v_remover_tabelas_relacao_contrato_vendedor && v_remover_tabelas_contrato_sub_contrato && v_remover_sub_contrato) {
				  
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
	  
	  private int inserir_contrato_retorno(CadastroContrato contrato) {
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
	 
	  
	  private boolean inserir_cliente_corretor(CadastroContrato contrato, int id_contrato) {

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
	  
	  private boolean inserir_cliente_comprador(CadastroContrato contrato, int id_contrato) {

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
	  
	  private boolean inserir_cliente_vendedor (CadastroContrato contrato, int id_contrato) {

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
	  
	
	  private boolean inserir_corretor(CadastroCliente corretor, int id_contrato){
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
	  
	  private boolean inserir_comprador(CadastroCliente comprador, int id_contrato){
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
	  
	  
	  private boolean inserir_vendedor(CadastroCliente vendedor, int id_contrato){
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
	
	  
	  private String sql_contrato(CadastroContrato contrato) {
		  String texto_clausulas = "";
		  for(String clausula_local : contrato.getClausulas()) {
			  texto_clausulas += (clausula_local + ";");
		  }
		  
		  
          String query = "insert into contrato (codigo, sub_contrato,id_safra, id_produto, medida, quantidade, valor_produto, valor_a_pagar,comissao, clausula_comissao, valor_comissao, clausulas, data_contrato, data_entrega, status_contrato, caminho_arquivo, nome_arquivo) values ('"
        		 + contrato.getCodigo() 
                 + "','"
                     + contrato.getSub_contrato()
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
                 + contrato.getComissao()
                + "','"
                 + contrato.getClausula_comissao()
                + "','"
                 + contrato.getValor_comissao()
                  + "','"
                 + texto_clausulas
                + "','"
                + contrato.getData_contrato()
                + "','"
                + contrato.getData_entrega()
                + "','"
                + contrato.getStatus_contrato()
                + "','"
                + contrato.getCaminho_arquivo()
                + "','"
                + contrato.getNome_arquivo()
                + "')";	
          return query;

	  }
	  
	
	  
	  
	  
	  
	  
	  private boolean remover_contrato_corretor( int id_contrato, int id_cliente) {
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
	  
	  private boolean remover_contrato_comprador( int id_contrato, int id_cliente) {
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
	  
	  private boolean remover_contrato_vendedor( int id_contrato, int id_cliente) {
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
	  
	  
	  private boolean remover_contrato(int id_contrato) {
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
	  
	  private boolean remover_tabelas_contrato_corretor() {
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
	  
	  private boolean remover_tabelas_contrato_comprador() {
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
	  
	  private boolean remover_tabelas_contrato_vendedor() {
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
	  
	  
	  
	  public String sql_modelo_pagamento(CadastroContrato.CadastroPagamento pagamento) {
          String query = "insert into modelo_pagamento (id_conta_bancaria, data_pagamento, valor, descricao_pagamento) values ('"
        		 + pagamento.getConta().getId_conta() 
                 + "','"
                + pagamento.getData_pagamento()
                + "','"
                + pagamento.getValor()
                + "','"
                + pagamento.getDescricao_pagamento()
                + "')";	
          return query;

	  }
	  
	  public String sql_contrato_modelo_pagamento( int id_contrato,int id_pagamento) {
          String query = "insert into contrato_modelo_pagamento (id_contrato, id_pagamento) values ('"
        		 + id_contrato
                 + "','"
                + id_pagamento
                + "')";	
          return query;

	  }
	  
	  
	  public boolean inserirModelosPagamentos(int id_contrato, ArrayList<CadastroContrato.CadastroPagamento> pagamentos) {
		  boolean retorno = false;
		  
		 if(pagamentos != null && pagamentos.size() > 0) { 
		  for(CadastroContrato.CadastroPagamento pagamento : pagamentos) {
			  if(pagamento.getId() == 0){
			  int retorno_positivo = inserir_modelo_pagamento(pagamento);
			  if(retorno_positivo != -1) {
				   boolean retorno_positivo_relacao = inserir_contrato_modelo_pagamento(id_contrato, retorno_positivo);
				   if(retorno_positivo_relacao) {
					   retorno = true;
				   }else {
					   retorno = false;
					   break;
				   }
				  
			  }else {
				  retorno = false;
				  //houve algum erro ao adicionar um pagamento, retorno falso para reverter todo o processo
				  break;
			  }
		  }else {
			  //esse pagamento ja esta cadatrado
			  retorno = true;
		  }
		  }
	     }
		  return retorno;
		  
	  }
	  
	  
	  private int inserir_modelo_pagamento(CadastroContrato.CadastroPagamento pagamento) {
		  int result = -1;
		   int id_cliente = -1;
		  if( pagamento != null) {
	            Connection conn = null;
	            try {
	            	
	                conn = ConexaoBanco.getConexao();
	    	       
	                String query = sql_modelo_pagamento(pagamento);
                  Statement stmt = (Statement) conn.createStatement();
                  int numero = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

                  ResultSet rs = stmt.getGeneratedKeys();
                  if (rs.next()){
 	                                result=rs.getInt(1);
 	                                System.out.println("Id pagamento inserido: "+ result);
                                }
                   rs.close();
                   stmt.close();
                   
                   return result;
	 
	            } catch (Exception e) {
	               JOptionPane.showMessageDialog(null, "Erro ao inserir o pagamento no banco de"
	                      + "dados " + e.getMessage());
	            	GerenciadorLog.registrarLogDiario("falha", "falha ao adicionar novo pagamento: " + e.getMessage() + " causa: " + e.getCause());
	                return -1;
	            }
	        } else {
	            System.out.println("O pagamento enviado por parametro esta vazio");
	            return -1;
	        }
	  }
	  
	  private boolean inserir_contrato_modelo_pagamento( int id_contrato, int id_pagamento){
		  Connection conn = null;
          try {
              conn = ConexaoBanco.getConexao();
              String sql = "insert into contrato_modelo_pagamento\r\n" + 
              		"(id_contrato, id_pagamento) values ('"
  	    			+id_contrato
  	    			+ "','"
  	    			+ id_pagamento
  	    			+ "')";
  	       
  	        PreparedStatement grava = (PreparedStatement) conn.prepareStatement(sql); 
  	        grava.execute();    
              ConexaoBanco.fechaConexao(conn, grava);
              JOptionPane.showMessageDialog(null, "Relação contrato_modelo_pagamento  cadastrado com sucesso");
                registro_geral_relacao_contrato_modelo_pagamento.setIdContrato(id_contrato);
                registro_geral_relacao_contrato_modelo_pagamento.adicionar_id(id_pagamento);
               return true;

          } catch (Exception e) {
        	  JOptionPane.showMessageDialog(null, "Erro ao inserir a relação contrato_modelo_pagamento no banco de"
                       +  e.getMessage());
               return false;
          }
 			  
 	         
	  }
	  
	  
	  private boolean remover_tabelas_contrato_modelo_pagamento() {
		  boolean retorno = false;
		  
		  for(int codigo_relacao_contrato_modelo_pagamento : registro_geral_relacao_contrato_modelo_pagamento.getIds()) {
				 if (remover_contrato_modelo_pagamento( registro_geral_relacao_contrato_modelo_pagamento.getIdContrato(), codigo_relacao_contrato_modelo_pagamento)) {
					 retorno = true;
				 }else {
					 retorno = false;
					 break;
				 }
			  }
		  
		  return retorno;
	  }
	  
	  private boolean remover_modelo_pagamento(int id_pagamento) {
		  String sql_dele_cliente = "DELETE FROM modelo_pagamento WHERE id = ?";
		  Connection conn = null;
	        ResultSet rs = null;	      
	        try {
	            conn = ConexaoBanco.getConexao();
	            PreparedStatement pstm;
	            pstm = conn.prepareStatement(sql_dele_cliente);
	 
	            pstm.setInt(1, id_pagamento);
	 
	            pstm.execute();
	            ConexaoBanco.fechaConexao(conn, pstm);
	            JOptionPane.showMessageDialog(null, "modelo_pagamento Excluido, banco normalizado ");
	           return true;
	            
	 
	        } catch (Exception f) {
	            JOptionPane.showMessageDialog(null, "Erro ao excluir o modelo_pagamento do banco de"
	                    + "dados " + f.getMessage());
	           return false;
	        }
	  }
	  
	  private boolean remover_tabelas_modelo_pagamento() {
  boolean retorno = false;
		  
		  for(int codigo_relacao_contrato_modelo_pagamento : registro_geral_relacao_contrato_modelo_pagamento.getIds()) {
				 if (remover_modelo_pagamento( codigo_relacao_contrato_modelo_pagamento)) {
					 retorno = true;
				 }else {
					 retorno = false;
					 break;
				 }
			  }
		  
		  return retorno;
	  }
	  
	  private boolean remover_contrato_modelo_pagamento( int id_contrato, int id_pagamento) {
		  String sql_delete_relacao = "DELETE FROM contrato_modelo_pagamento WHERE id_contrato = ? and id_pagamento = ?";
		  Connection conn = null;
	        ResultSet rs = null;	      
	        try {
	            conn = ConexaoBanco.getConexao();
	            PreparedStatement pstm;
	            pstm = conn.prepareStatement(sql_delete_relacao);
	 
	            pstm.setInt(1, id_contrato);
	            pstm.setInt(2,  id_pagamento);
	 
	            pstm.execute();
	            ConexaoBanco.fechaConexao(conn, pstm);
	            JOptionPane.showMessageDialog(null, "Relação contrato_modelo_pagamento excluido, banco normalizado ");
	           return true;
	            
	 
	        } catch (Exception f) {
	            JOptionPane.showMessageDialog(null, "Erro ao exlcuir relação contrato_modelo_pagamento do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
	                    + "dados " + f.getMessage());
	           return false;
	        }
		  
		  
	  }
	  
	  
	  public CadastroContrato getContrato(int id) {
		  
		  String selectContrato = "select * from contrato where id = ?";
		  Connection conn = null;
	        PreparedStatement pstm = null;
	        ResultSet rs = null;
        	CadastroContrato contrato = new CadastroContrato();

	        try {
	            conn = ConexaoBanco.getConexao();
	            pstm = conn.prepareStatement(selectContrato);
	            pstm.setInt(1, id);
	            		
	            rs = pstm.executeQuery();
	            rs.next();
	            

	                 contrato.setId(rs.getInt("id"));
	            	 contrato.setCodigo(rs.getString("codigo"));
	            	 contrato.setSub_contrato(rs.getInt("sub_contrato"));
	            	 contrato.setTexto_clausulas(rs.getString("clausulas"));
	            
	            	 int id_safra = rs.getInt("id_safra");
	            	 
	            	 GerenciarBancoSafras gerenciar = new GerenciarBancoSafras();
	            	 CadastroSafra safra = gerenciar.getSafra(id_safra);
	            	 
	            	 
	            	
	            	 if(safra != null) {
	            		 
	            		 contrato.setQuantidade(Double.parseDouble(rs.getString("quantidade")));
		            	 contrato.setMedida(rs.getString("medida"));
		            	
		            	 contrato.setValor_produto(Double.parseDouble(rs.getString("valor_produto")));
		            	 contrato.setValor_a_pagar(new BigDecimal(rs.getString("valor_a_pagar")));
		            
		            	 contrato.setModelo_safra(safra);
		            	 
		            	 GerenciarBancoContratos gerenciar_corretores = new GerenciarBancoContratos();
		            	 CadastroCliente corretores[] = gerenciar_corretores.getCorretores(id);
                         contrato.setCorretores(corretores);
		            	 
                         GerenciarBancoContratos gerenciar_vendedores = new GerenciarBancoContratos();
		            	 CadastroCliente vendedores[] = gerenciar_vendedores.getVendedores(id);
                         contrato.setVendedores(vendedores);
                         
                         GerenciarBancoContratos gerenciar_compradores = new GerenciarBancoContratos();
		            	 CadastroCliente compradores[] = gerenciar_compradores.getCompradores(id);
                         contrato.setCompradores(compradores);
                   
                         GerenciarBancoContratos gerenciar_pagamentos = new GerenciarBancoContratos();
		            	 ArrayList<CadastroContrato.CadastroPagamento> pagamentos_contrato = gerenciar_pagamentos.getPagamentos(id);
                         contrato.setPagamentos(pagamentos_contrato);
                         
                        
                         
                         contrato.setStatus_contrato(rs.getInt("status_contrato"));
    	            	 contrato.setData_contrato(rs.getString("data_contrato"));
    	            	 contrato.setData_entrega(rs.getString("data_entrega"));

                         contrato.setCaminho_arquivo(rs.getString("caminho_arquivo"));
                         contrato.setNome_arquivo(rs.getString("nome_arquivo"));
                        
		            	 

                         //dados de comissao
                         contrato.setComissao(rs.getInt("comissao"));
                         contrato.setClausula_comissao(rs.getInt("clausula_comissao"));
                         contrato.setValor_comissao(new BigDecimal(rs.getString("valor_comissao")));
                         
                       
	            		 
    	                 ConexaoBanco.fechaConexao(conn, pstm, rs);
	            			
	            		 return contrato;
	            	 }else {
	            		 
	            		 return null;
	            	 }
	            	 

	                
	            
	        } catch (Exception e) {
	            JOptionPane.showMessageDialog(null, "Erro ao listar contrato id: " + id + " erro: " + e.getMessage());
	            System.out.println("Erro ao listar contrato id: " + id + " erro: " + e.getMessage() + "\ncausa: " + e.getCause());
	            return null;
	        }		  
		  
		  
	  }
	  
	  public int atualizarContrato(CadastroContrato contrato, ArrayList<Integer> ids_pagamentos_a_excluir) {
		    Connection conn = null;
            String atualizar = null;
            PreparedStatement pstm;
            String sql_update_contrato ="update contrato set id_safra = ?, id_produto = ?, medida = ?, quantidade = ?,\r\n"
    		 		+ "valor_produto = ?, valor_a_pagar = ?, comissao = ?, clausula_comissao = ?, valor_comissao = ?, clausulas = ?,\r\n"
    		 		+ "data_contrato = ?, data_entrega = ?, status_contrato = ?, caminho_arquivo = ?,\r\n"
    		 		+ "nome_arquivo = ? where id = ?;";
            
          try {  
        	  String texto_clausulas = "";
    		  int num_clausulas = 1;
    		  for(String clausula_local : contrato.getClausulas()) {
    			  texto_clausulas += (clausula_local + ";");
    			  num_clausulas++;
    		  }
    		  
        	  conn = ConexaoBanco.getConexao();
         	 pstm = conn.prepareStatement(sql_update_contrato);
         	 
		     pstm.setInt(1, contrato.getModelo_safra().getId_safra());
		     pstm.setInt(2,  contrato.getModelo_safra().getProduto().getId_produto());
		     pstm.setString(3, contrato.getMedida());
		     pstm.setString(4, Double.toString(contrato.getQuantidade()));
		     pstm.setString(5, Double.toString(contrato.getValor_produto()));
		     
		     pstm.setString(6, contrato.getValor_a_pagar().toPlainString());
		     pstm.setString(7, Integer.toString(contrato.getComissao()));
		     pstm.setString(8, Integer.toString(contrato.getClausula_comissao()));
		     pstm.setString(9, contrato.getValor_comissao().toPlainString());
		     pstm.setString(10, texto_clausulas);

		     pstm.setString(11, contrato.getData_contrato());
		     pstm.setString(12, contrato.getData_entrega());
		     pstm.setString(13, Integer.toString(contrato.getStatus_contrato()));
		     pstm.setString(14, contrato.getCaminho_arquivo());
		     pstm.setString(15,  contrato.getNome_arquivo());
		     pstm.setInt(16, contrato.getId());
		     
		     
		  
		    pstm.execute();
            //JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
            //System.out.println("Contrato Atualizado com sucesso");
            
            boolean pagamentos_apagados = false;
            boolean pagamentos_inseridos = false;
            
            //apaga os modelo  de pagamentos 
            if(ids_pagamentos_a_excluir != null) {
            
            	System.out.println("Existem modelo_pagamentos a serem excluidos!");
            	for(Integer id_pagamento : ids_pagamentos_a_excluir) {
              if(remover_modelo_pagamento(id_pagamento)) {
            	  pagamentos_apagados = true;
            	 
              }else {
            	  //nao foi possivel apagar esse modelo de pagamento
            	  pagamentos_apagados = false;      
            	  break;
            	  }
            	
            }
            
            //apaga os relacionamentos pagamentos
            for(Integer id_pagamento : ids_pagamentos_a_excluir) {

                if(remover_contrato_modelo_pagamento(contrato.getId(), id_pagamento)) {
              	  pagamentos_apagados = true;
                	System.out.println("Relacionamentos contrato_modelo_pagamento excluidos!");

                }else {
              	  //nao foi possivel apagar esse modelo de pagamento
              	  pagamentos_apagados = false;     
                	System.out.println("Não foi possivel excluir o relacionamento contrato_modelo_pagamento!");

              	  break;
              	  }
              	
              }
            }else {
            	System.out.println("Não ha modelo_pagamentos a serem excluidos!");

            	pagamentos_apagados = true;
            }
            
            
            //inseri os novos modelos de pagamentos
            boolean existe_novos_pagamento = false;
            for(CadastroContrato.CadastroPagamento pag : contrato.getPagamentos()) {
            	if(pag.getId() == 0) {
            		existe_novos_pagamento = false;
            	}
            	
            }
            
            
            
            
            if( contrato.getPagamentos() != null && contrato.getPagamentos().size() > 0) {
            	System.out.println("Existem novos pagamentos a serem incluidos");
            	if(inserirModelosPagamentos(contrato.getId(),contrato.getPagamentos())){
                	pagamentos_inseridos = true;
                	System.out.println("Novos pagamentos inseridos");

            	}else {
                	pagamentos_inseridos = false;
                	System.out.println("Erro ao inserir novos pagamentos inseridos");

            	}
            }else {
            	pagamentos_inseridos = true;
            	System.out.println("Não ha novos pagamentos a serem incluidos");

            }
            
            ConexaoBanco.fechaConexao(conn);
            if(pagamentos_apagados && pagamentos_inseridos) {
            	System.out.println("Pagamentos que tinham foram apagados, e novos adicionados!");

                       return 5;
            }
             else {
                       	System.out.println("Houve um erro ao inserir ou apagar modelo_pagamentos!");

            	return -2;
                       }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar contrato no banco de"
                    + "dados " + e.getMessage());
            return -2;
        }
		  
		  
	  }
	  
	  
	  
	  
	  public ArrayList<CadastroContrato> getContratos() {
		  String selectContratos = "call consulta_contratos()";
	        Connection conn = null;
	        PreparedStatement pstm = null;
	        ResultSet rs = null;
	        ArrayList<CadastroContrato> lsitaContratos = new ArrayList<CadastroContrato>();
	        try {
	            conn = ConexaoBanco.getConexao();
	            pstm = conn.prepareStatement(selectContratos);
	           // pstm.setString(1, chave);
	            		
	            rs = pstm.executeQuery();
	            while (rs.next()) {
	            	CadastroContrato contrato = new CadastroContrato();
	                 CadastroProduto produto = new CadastroProduto();
	                 CadastroSafra safra = new CadastroSafra();
	            	 CadastroCliente compradores [] = null ;
	            	 CadastroCliente vendedores [] = null;
	            	 CadastroCliente corretores [] = null;

	                 contrato.setId(rs.getInt("id"));
	            	 contrato.setCodigo(rs.getString("codigo"));
	            	 contrato.setSub_contrato(rs.getInt("sub_contrato"));
	            	 contrato.setQuantidade(Double.parseDouble(rs.getString("quantidade")));
	            	 contrato.setMedida(rs.getString("medida"));
	            	 contrato.setProduto(rs.getString("nome_produto"));
	            	 safra.setDescricao_safra(rs.getString("descricao_safra"));
	            	 safra.setAno_plantio(Integer.parseInt(rs.getString("ano_plantio")));
	            	 safra.setAno_colheita(Integer.parseInt(rs.getString("ano_colheita")));
	            	 contrato.setValor_produto(Double.parseDouble(rs.getString("valor_produto")));
	            	 contrato.setValor_a_pagar(new BigDecimal(rs.getString("valor_a_pagar")));
	            	 contrato.setStatus_contrato(rs.getInt("status_contrato"));
	            	 contrato.setData_contrato(rs.getString("data_contrato"));
	            	 
	            	 
	            	 
	                 contrato.setNomes_compradores(rs.getString("compradores"));
	                 contrato.setNomes_vendedores(rs.getString("vendedores"));
	                 contrato.setNomes_corretores(rs.getString("corretores"));
	                 
	                 
	                 
	                 
	                 

	            	 

	            	 safra.setProduto(produto);
	            	 contrato.setModelo_safra(safra);
	            	lsitaContratos.add(contrato);
	                
	            }
	            ConexaoBanco.fechaConexao(conn, pstm, rs);
	        } catch (Exception e) {
	            JOptionPane.showMessageDialog(null, "Erro ao listar contratos" + e.getMessage());
	        }
	        return lsitaContratos;
	
}
	  
	  
 public CadastroCliente [] getCorretores(int id_contrato) {
		  
		  String selectCorretores = "select c.id_cliente from contrato_corretor cc LEFT JOIN cliente c on c.id_cliente = cc.id_cliente  where cc.id_contrato = ?";
		  Connection conn = null;
	        PreparedStatement pstm = null;
	        ResultSet rs = null;
	        CadastroCliente lista_corretores [] = new CadastroCliente [5];

	        try {
	            conn = ConexaoBanco.getConexao();
	            pstm = conn.prepareStatement(selectCorretores);
	            pstm.setInt(1, id_contrato);
	            		
	            rs = pstm.executeQuery();
	            int i = 0;
            	GerenciarBancoClientes gerenciar_clientes = new GerenciarBancoClientes();

	            while (rs.next()) {
	            	int id_cliente = rs.getInt("id_cliente");
	            	System.out.println("id do cliente retornado: " + id_cliente);
	            	CadastroCliente corretor;
	            	try
	            	{
	            		 corretor = gerenciar_clientes.getCliente(id_cliente);
	            		 System.out.println("Nome do cliente com id " + id_cliente + ": " + corretor.getNome_empresarial() + " outro nome: " + corretor.getNome_fantaia());
	            		 if(corretor != null) {
	 	            		lista_corretores[i] = corretor;
	 	            		System.out.println("Corretor encontrado: " + corretor.getNome_empresarial() + "outro nome:" + corretor.getNome_fantaia());
	 	            	   i++;
	 	            	}else {
	 	            		System.out.println("O corretor é nulo!");
	 	            	}
	            	     
	            	}catch(Exception y) {
	            		System.out.println("Erro ao procurar por cliente corretor, erro: " + y.getMessage());
	            	}
	             }
	            ConexaoBanco.fechaConexao(conn, pstm, rs);
                return lista_corretores;
	        } catch (Exception e) {
	            JOptionPane.showMessageDialog(null, "Erro ao listar os corretores do contrato: " + id_contrato + " erro: " + e.getMessage() + "causa: " + e.getCause());
	            return null;
	        }		  
		  
		  
	  }
	  
	  
 public CadastroCliente [] getCompradores(int id_contrato) {
	  
	  String selectCompradores = "select c.id_cliente from contrato_comprador cc LEFT JOIN cliente c on c.id_cliente = cc.id_cliente  where cc.id_contrato = ?";
	  Connection conn = null;
       PreparedStatement pstm = null;
       ResultSet rs = null;
       CadastroCliente lista_compradores [] = new CadastroCliente [3];

       try {
           conn = ConexaoBanco.getConexao();
           pstm = conn.prepareStatement(selectCompradores);
           pstm.setInt(1, id_contrato);
           		
           rs = pstm.executeQuery();
           int i = 0;
       	GerenciarBancoClientes gerenciar_clientes = new GerenciarBancoClientes();

           while (rs.next()) {
           	int id_cliente = rs.getInt("id_cliente");
           	System.out.println("id do cliente retornado: " + id_cliente);
           	CadastroCliente comprador;
           	try
           	{
           		comprador = gerenciar_clientes.getCliente(id_cliente);
           		 System.out.println("Nome do cliente com id " + id_cliente + ": " + comprador.getNome_empresarial() + " outro nome: " + comprador.getNome_fantaia());
           		 if(comprador != null) {
           			lista_compradores[i] = comprador;
	            		System.out.println("Comprador encontrado: " + comprador.getNome_empresarial() + "outro nome:" + comprador.getNome_fantaia());
	            	   i++;
	            	}else {
	            		System.out.println("O comprador é nulo!");
	            	}
           	     
           	}catch(Exception y) {
           		System.out.println("Erro ao procurar por cliente comprador, erro: " + y.getMessage());
           	}
            }
           ConexaoBanco.fechaConexao(conn, pstm, rs);
           return lista_compradores;
       } catch (Exception e) {
           JOptionPane.showMessageDialog(null, "Erro ao listar os compradores do contrato: " + id_contrato + " erro: " + e.getMessage() + "causa: " + e.getCause());
           return null;
       }		  
	  
	  
 }
 
 
 public CadastroCliente [] getVendedores(int id_contrato) {
	  
	  String selectVendedores = "select c.id_cliente from contrato_vendedor cc LEFT JOIN cliente c on c.id_cliente = cc.id_cliente  where cc.id_contrato = ?";
	  Connection conn = null;
      PreparedStatement pstm = null;
      ResultSet rs = null;
      CadastroCliente lista_vendedores [] = new CadastroCliente [3];

      try {
          conn = ConexaoBanco.getConexao();
          pstm = conn.prepareStatement(selectVendedores);
          pstm.setInt(1, id_contrato);
          		
          rs = pstm.executeQuery();
          int i = 0;
      	GerenciarBancoClientes gerenciar_clientes = new GerenciarBancoClientes();

          while (rs.next()) {
          	int id_cliente = rs.getInt("id_cliente");
          	System.out.println("id do cliente retornado: " + id_cliente);
          	CadastroCliente vendedor;
          	try
          	{
          		vendedor = gerenciar_clientes.getCliente(id_cliente);
          		 System.out.println("Nome do cliente com id " + id_cliente + ": " + vendedor.getNome_empresarial() + " outro nome: " + vendedor.getNome_fantaia());
          		 if(vendedor != null) {
          			lista_vendedores[i] = vendedor;
	            		System.out.println("Vendedor encontrado: " + vendedor.getNome_empresarial() + "outro nome:" + vendedor.getNome_fantaia());
	            	   i++;
	            	}else {
	            		System.out.println("O vendedor é nulo!");
	            	}
          	     
          	}catch(Exception y) {
          		System.out.println("Erro ao procurar por cliente vendedores, erro: " + y.getMessage());
          	}
           }
          ConexaoBanco.fechaConexao(conn, pstm, rs);
          return lista_vendedores;
      } catch (Exception e) {
          JOptionPane.showMessageDialog(null, "Erro ao listar os vendedores do contrato: " + id_contrato + " erro: " + e.getMessage() + "causa: " + e.getCause());
          return null;
      }		  
	  
	  
}
	 
 
 
 
   public ArrayList<CadastroContrato.CadastroPagamento> getPagamentos(int id_contrato){
	   String selectPagamentos = "select * from contrato_modelo_pagamento  LEFT JOIN modelo_pagamento  on modelo_pagamento.id = contrato_modelo_pagamento.id_pagamento  where contrato_modelo_pagamento.id_contrato = ?";
		  Connection conn = null;
	      PreparedStatement pstm = null;
	      ResultSet rs = null;
	      ArrayList<CadastroContrato.CadastroPagamento> lista_pagamentos = null;
	      
	      try {
	          conn = ConexaoBanco.getConexao();
	          pstm = conn.prepareStatement(selectPagamentos);
	          pstm.setInt(1, id_contrato);
	          		
	          rs = pstm.executeQuery();
	          int i = 0;

	          while (rs.next()) {
	        	  
	        	  
	        	 if(rs != null) {
	        		 System.out.print("rs pagamento nao e nulo!");
	        		 
	        	  CadastroContrato.CadastroPagamento pagamento = new CadastroContrato.CadastroPagamento();
	        	  
	        	  pagamento.setId(rs.getInt("id"));
	        	  pagamento.setValor_string(rs.getString("valor"));
	        	  pagamento.setValor(new BigDecimal(rs.getString("valor")));
	        	  pagamento.setData_pagamento(rs.getString("data_pagamento"));
	        	  ContaBancaria conta = null;
	        			  
	        	  int id_cb = rs.getInt("id_conta_bancaria");
	        	  if(id_cb == 0) {
	        		  conta = new ContaBancaria();
                 		//nao foi informado conta bancaria ppara esse pagamento
                 		
	        		  /*
	        		   *    id = "00";
		 		cpf = "Há Informar";
		 		banco = "Há Informar";
		 		nome = "Há Informar";
		 		codigo = "Há Informar";
		 		agencia = "Há Informar";
		 		conta = "Há Informar";
	        		   */
	        		  conta.setId_conta(0);
	        		  conta.setCpf_titular("Há Informar");
	        		  conta.setBanco("Há Informar");
	        		  conta.setCodigo("Há Informar");
	        		  conta.setAgencia("Há Informar");
	        		  conta.setConta("Há Informar");
	        		  conta.setNome("Há Informar");

                 	
	        		 
	        	  }else {
	        		  GerenciarBancoClientes gerenciar = new GerenciarBancoClientes();
		        	   conta = gerenciar.getConta(id_cb);
	        	  }
	        	  
	        	 
	        	  
	        	  
	        	  if(conta != null)
	        	   pagamento.setConta(conta);
	        	  
	        	 if(lista_pagamentos == null)
	        		 lista_pagamentos = new ArrayList<>();
	        	 
	        	  lista_pagamentos.add(pagamento);
	           }
	      }
	          ConexaoBanco.fechaConexao(conn, pstm, rs);
	          return lista_pagamentos;
	      } catch (Exception e) {
	          JOptionPane.showMessageDialog(null, "Erro ao listar os pagamentos do contrato: " + id_contrato + " erro: " + e.getMessage() + "causa: " + e.getCause());
	          return null;
	      }		  
	   
   }
	   
	   
   
   public int getNumeroContratosParaAssinar() {
	   
	   String selectGetNumContratos = "SELECT COUNT(*) FROM contrato where sub_contrato = 0 and status_contrato = 1";
		  Connection conn = null;
	      PreparedStatement pstm = null;
	      ResultSet rs = null;
	      
	      try {
	          conn = ConexaoBanco.getConexao();
	          pstm = conn.prepareStatement(selectGetNumContratos);
	          		
	          rs = pstm.executeQuery();
	          rs.next();
	          int i = rs.getInt("COUNT(*)");
	          
	          
	          
	          ConexaoBanco.fechaConexao(conn, pstm, rs);
              return i;
	        
	      
	      } catch (Exception e) {
	          JOptionPane.showMessageDialog(null, "Erro ao listar o numero de  contratos sem assinaturas: " +  " erro: " + e.getMessage() + "causa: " + e.getCause());
	         return -1;
	        }		  
	   
	   
   }
   
   
   
   private boolean inserir_contrato_sub_contrato( int id_contrato_pai, int id_sub_contrato){
		  Connection conn = null;
       try {
           conn = ConexaoBanco.getConexao();
           String sql = "insert into contrato_sub_contrato \r\n" + 
           		"(id_contrato_pai, id_sub_contrato) values ('"
	    			+id_contrato_pai
	    			+ "','"
	    			+ id_sub_contrato
	    			+ "')";
	       
	        PreparedStatement grava = (PreparedStatement) conn.prepareStatement(sql); 
	        grava.execute();    
           ConexaoBanco.fechaConexao(conn, grava);
           JOptionPane.showMessageDialog(null, "Relação contrato_sub_contrato cadastrado com sucesso");
             registro_geral_relacao_contrato_sub_contrato.setIdContrato(id_contrato_pai);
             registro_geral_relacao_contrato_sub_contrato.adicionar_id(id_sub_contrato);
            return true;

       } catch (Exception e) {
     	  JOptionPane.showMessageDialog(null, "Erro ao inserir a relação contrato_sub_contrato no banco de"
                    + " dados" + e.getMessage());
            return false;
       }
			  
	         
	  }
   
   
   
   
   private boolean remover_contrato_sub_contrato( int id_contrato_pai, int id_sub_contrato) {
		  String sql_delete_relacao = "DELETE FROM contrato_sub_contrato WHERE id_contrato_pai = ? and id_sub_contrato = ?";
		  Connection conn = null;
	        ResultSet rs = null;	      
	        try {
	            conn = ConexaoBanco.getConexao();
	            PreparedStatement pstm;
	            pstm = conn.prepareStatement(sql_delete_relacao);
	 
	            pstm.setInt(1, id_contrato_pai);
	            pstm.setInt(2,  id_sub_contrato);
	 
	            pstm.execute();
	            ConexaoBanco.fechaConexao(conn, pstm);
	            JOptionPane.showMessageDialog(null, "Relação contrato_sub_contrato excluido, banco normalizado ");
	           return true;
	            
	 
	        } catch (Exception f) {
	            JOptionPane.showMessageDialog(null, "Erro ao exlcuir relação contrato_sub_contrato do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
	                    + "dados " + f.getMessage());
	           return false;
	        }
		  
		  
	  }
	  
	   
   private boolean remover_tabelas_contrato_sub_contrato() {
	   boolean retorno = false;
	 		  
	 		  for(int codigo_sub_contrato : registro_geral_relacao_contrato_sub_contrato.getIds()) {
	 				 if (remover_contrato_sub_contrato( registro_geral_relacao_contrato_sub_contrato.getIdContrato(), codigo_sub_contrato)) {
	 					 retorno = true;
	 				 }else {
	 					 retorno = false;
	 					 break;
	 				 }
	 			  }
	 		  
	 		  return retorno;
	 	  }
	  
   public ArrayList<CadastroContrato> getSubContratos(int id_contrato_pai){
	   String selectContrato = "select * from contrato_sub_contrato sub_contrato LEFT JOIN contrato filho  on filho.id = sub_contrato.id_sub_contrato where filho.sub_contrato = 1 and id_contrato_pai = ?";
		  Connection conn = null;
	        PreparedStatement pstm = null;
	        ResultSet rs = null;
        ArrayList<CadastroContrato> lsitaContratos = new ArrayList<CadastroContrato>();

	        try {
	            conn = ConexaoBanco.getConexao();
	            pstm = conn.prepareStatement(selectContrato);
	            pstm.setInt(1, id_contrato_pai);
           	    GerenciarBancoSafras gerenciar = new GerenciarBancoSafras();

	            		
	            rs = pstm.executeQuery();
	            while (rs.next()) {
	             	CadastroContrato contrato = new CadastroContrato();

	                 contrato.setId(rs.getInt("id"));
	            	 contrato.setCodigo(rs.getString("codigo"));
	            	 contrato.setSub_contrato(rs.getInt("sub_contrato"));
	            	 int id_safra = rs.getInt("id_safra");
	            	 
	            	 CadastroSafra safra = gerenciar.getSafra(id_safra);
	            	 
	            	 int id = contrato.getId();
	            	 
	            	 if(safra != null) {
	            		 
	            		 contrato.setQuantidade(Double.parseDouble(rs.getString("quantidade")));
		            	 contrato.setMedida(rs.getString("medida"));
		            	
		            	 contrato.setValor_produto(Double.parseDouble(rs.getString("valor_produto")));
		            	 contrato.setValor_a_pagar(new BigDecimal(rs.getString("valor_a_pagar")));
		            
		            	 contrato.setModelo_safra(safra);
		            	 
		            	 GerenciarBancoContratos gerenciar_corretores = new GerenciarBancoContratos();
		            	 CadastroCliente corretores[] = gerenciar_corretores.getCorretores(id);
                         contrato.setCorretores(corretores);
		            	 
                          GerenciarBancoContratos gerenciar_vendedores = new GerenciarBancoContratos();
		              	 CadastroCliente vendedores[] = gerenciar_vendedores.getVendedores(id);
                         contrato.setVendedores(vendedores);
                      
                      GerenciarBancoContratos gerenciar_compradores = new GerenciarBancoContratos();
		            	 CadastroCliente compradores[] = gerenciar_compradores.getCompradores(id);
                      contrato.setCompradores(compradores);
                
                      GerenciarBancoContratos gerenciar_pagamentos = new GerenciarBancoContratos();
		            	 ArrayList<CadastroContrato.CadastroPagamento> pagamentos_contrato = gerenciar_pagamentos.getPagamentos(id);
                      contrato.setPagamentos(pagamentos_contrato);
                      
                     
                      
                      contrato.setStatus_contrato(rs.getInt("status_contrato"));
 	            	 contrato.setData_contrato(rs.getString("data_contrato"));
 	            	 contrato.setData_entrega(rs.getString("data_entrega"));

                      contrato.setCaminho_arquivo(rs.getString("caminho_arquivo"));
                      contrato.setNome_arquivo(rs.getString("nome_arquivo"));
                      
                      

                      //dados de comissao
                      contrato.setComissao(rs.getInt("comissao"));
                      contrato.setClausula_comissao(rs.getInt("clausula_comissao"));
                      contrato.setValor_comissao(new BigDecimal(rs.getString("valor_comissao")));
                     
  	            	     lsitaContratos.add(contrato);

	            	 }

                 }

	 	         ConexaoBanco.fechaConexao(conn, pstm, rs);

	            
	        } catch (Exception e) {
	            JOptionPane.showMessageDialog(null, "Erro ao listar contrato filhos do contrato id: " + id_contrato_pai + " erro: " + e.getMessage());
	            return null;
	        }		  
	        return lsitaContratos;

   }
   
}