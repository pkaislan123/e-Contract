package conexaoBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;


import cadastros.CadastroCliente;
import cadastros.CadastroLogin;
import cadastros.ContaBancaria;
import cadastros.Contato;

public class GerenciarBancoClientes {

	
	  public boolean inserir(CadastroCliente cliente) {
    	boolean deletar_contatos = false;
    	boolean deletar_contas = false;
    	boolean reprovar_cliente = false;

	        if (cliente != null) {
	        	
	            Connection conn = null;
	                ArrayList<Contato> contatos = new ArrayList<>();
	                contatos = cliente.getContatos();
	                int id_cliente = 0;
	                RegistroAdicionarContato adicionar_contatos = null;
	                
	                ArrayList<ContaBancaria> contas_bancarias = new ArrayList<>();
	                contas_bancarias = cliente.getContas();
	                RegistroAdicionarContaBancaria adicionar_contas = null;
	                
	                 try { 
	                	  id_cliente = inserir_cliente(cliente);
	 	                    if( id_cliente > 0) 
	                            {
	                                
	                                 System.out.println("Número de contatos para cadastrar: " + contatos.size());
	                                 System.out.println("Número de contas para cadastrar: " + contas_bancarias.size());
                                if(contas_bancarias.size() > 0 || contatos.size() > 0) {
	                                 if(contatos.size() > 0) {
	                                	adicionar_contatos = adicionarContato(contatos, id_cliente);	 
	                                 if(adicionar_contatos.isResposta() == true && adicionar_contatos.ids_contatos.size() > 0) {
	                                	 deletar_contatos = false;
	                                	 reprovar_cliente = true;
		                              
	                                 }
	                                  else {
	                                	  deletar_contatos = true;
	                                	  reprovar_cliente = false;
	                                  }
	                                 }
	                                 if(contas_bancarias.size() > 0) {
                                         
  	                                    	 adicionar_contas = adicionarContaBancaria(contas_bancarias, id_cliente);
                                              if(adicionar_contas.isResposta() == true && adicionar_contas.ids_contas.size() > 0) {
	                                		     deletar_contas = false;
	                                		     reprovar_cliente = true;
			                                  }
			                                  else {
			                                	  deletar_contas = true;
			                                	  reprovar_cliente = false;
			                                  }
  	                                     
		                                 
	                                 }
	                                
	  	                } else
                        {
                       	 reprovar_cliente = true; 
                        }
	                            }
                                else
	  	                {
	  	                	//erro ao inserir cliente
	  	                	remover_cliente(id_cliente);
	  	                	reprovar_cliente = false;
	  	                }
	                     
	                    
	                }catch(Exception e)
	                 {
	                	 JOptionPane.showMessageDialog(null, "Erro ao inserir o Cliente no banco de dados " + e.getMessage());
	                	  if(deletar_contatos)
	       	           {
	       	        	  
	       	        	   //deletar contatos apartir das ids geradas
	       	        	  for (int id : adicionar_contatos.ids_contatos )
	       	        	  {
	       		        	   deleteContato(id, id_cliente);

	       	        	  }
	       	           }
	       	           if(deletar_contas) {
	       	        	   
	       	        	   //deletar contas apartir das ids geradas
	       	        	  for (int id :  adicionar_contas.ids_contas )
	       	        	  {
	       		        	   deleteConta(id, id_cliente);

	       	        	  }
	       	           }
	       	        reprovar_cliente = false;
	                 }   
			            
	      }
	        else
	        {
	        	 JOptionPane.showMessageDialog(null, "Cliente Invalido");
	        	 reprovar_cliente = false;
 	        }
			return reprovar_cliente;
	      
	  
}

public boolean remover_cliente(int id_cliente)
{
	  String sql_dele_cliente = "DELETE FROM cliente WHERE id_cliente = ?";
	  Connection conn = null;
        ResultSet rs = null;	      
        try {
            conn = ConexaoBanco.getConexao();
            PreparedStatement pstm;
            pstm = conn.prepareStatement(sql_dele_cliente);
 
            pstm.setInt(1, id_cliente);
 
            pstm.execute();
            ConexaoBanco.fechaConexao(conn, pstm);
            JOptionPane.showMessageDialog(null, "Cliente Excluido, banco normalizado ");
           return true;
            
 
        } catch (Exception f) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir o cliente do banco de"
                    + "dados " + f.getMessage());
           return false;
        }
}
	  
public int inserir_cliente(CadastroCliente cliente)
{
	     System.out.println("Inserir Cliente foi chamado!");
		   int result = -1;
           int id_cliente = -1;
           
           String sql_cadastro_cliente = null;

           //cria os strings para cadastro o cliente
       	
           if(cliente.getTipo_pessoa() == 0)
           { //0 pessoa fisica
             sql_cadastro_cliente = string_pessoa_fisica(cliente);
           }
           else
           {
           	//cadastrar pessoa fisica
               sql_cadastro_cliente = string_pessoa_juridica(cliente);
              
           }
                try {       
                      Connection conn = ConexaoBanco.getConexao();
                      Statement stmt = (Statement) conn.createStatement();
                      int numero = stmt.executeUpdate(sql_cadastro_cliente, Statement.RETURN_GENERATED_KEYS);
      
                      ResultSet rs = stmt.getGeneratedKeys();
                      if (rs.next()){
     	                                result=rs.getInt(1);
     	                                System.out.println("Id Cliente inserido: "+ result);
                                    }
                       rs.close();
                       stmt.close();
                       return result;
                     }catch(Exception e) {
                    	  JOptionPane.showMessageDialog(null, "Erro ao inserir cliente no banco de"
          	                    + "dados\n Erro: " + e.getMessage());
                                  return -1;
                          }
		  
}
	  
	  public boolean remover_conta(int id_conta)
	  {
		  String sql_delete_contato = "DELETE FROM conta_bancaria WHERE id_conta = ?";
		  Connection conn = null;
	        ResultSet rs = null;	      
	        try {
	            conn = ConexaoBanco.getConexao();
	            PreparedStatement pstm;
	            pstm = conn.prepareStatement(sql_delete_contato);
	 
	            pstm.setInt(1, id_conta);
	 
	            pstm.execute();
	            ConexaoBanco.fechaConexao(conn, pstm);
	            JOptionPane.showMessageDialog(null, "Conta Bancaria Excluido, banco normalizado ");
	           return true;
	            
	 
	        } catch (Exception f) {
	            JOptionPane.showMessageDialog(null, "Erro ao excluir o conta bancaria do banco de"
	                    + "dados " + f.getMessage());
	           return false;
	        }
	  }
                 
	  public boolean remover_contato(int id_contato)
	  {
		  String sql_delete_contato = "DELETE FROM contato WHERE id_contato = ?";

		  Connection conn = null;
	        ResultSet rs = null;	
	        try {
	            conn = ConexaoBanco.getConexao();
	            PreparedStatement pstm;
	            pstm = conn.prepareStatement(sql_delete_contato);
	 
	            pstm.setInt(1, id_contato);
	 
	            pstm.execute();
	            ConexaoBanco.fechaConexao(conn, pstm);
	            JOptionPane.showMessageDialog(null, "Contato Excluido, banco normalizado ");
	           return true;
	            
	 
	        } catch (Exception f) {
	            JOptionPane.showMessageDialog(null, "Erro ao excluir o contato do banco de"
	                    + "dados " + f.getMessage());
	         return false;
	          
	        }
	  }
	  
	  public ArrayList<Contato> getContatos(int id_cliente)
	  {
		  String selectContatos = "select * from contato co\r\n" + 
          		"inner Join cliente_contato cc\r\n" + 
          		"on cc.id_contato = co.id_contato\r\n" + 
          		"where cc.id_cliente = ?";
	        ArrayList<Contato> contatos = new ArrayList<>();

		  Connection conn = null;
	        PreparedStatement pstm = null;
	        ResultSet rs = null;
	    

          try {
	            conn = ConexaoBanco.getConexao();
	            pstm = conn.prepareStatement(selectContatos);
	            pstm.setInt(1, id_cliente);
	            rs = pstm.executeQuery();
          while (rs.next()) {
                 Contato contato = new Contato();
                     
                contato.setId(rs.getInt("id_contato"));
                contato.setCargo(rs.getString("cargo_contato"));
                contato.setNome(rs.getString("nome_contato"));
                contato.setCelular(rs.getString("celular_contato"));
                contato.setFixo(rs.getString("fixo_contato"));
                contato.setE_mail(rs.getString("e_mail_contato"));
                contato.setDescricao(rs.getString("descricao_contato"));
                contato.setObservacao(rs.getString("observacao_contato"));

                
                contatos.add(contato);
          }
          }   catch (Exception e) {
	            JOptionPane.showMessageDialog(null, "Erro ao listar contatos " + e.getMessage());
	        }

          
            return contatos;
		}

	  
	  
	  public ArrayList<ContaBancaria> getContas(int id_cliente)
	  {
		  String selectContaBancaria = "select * from conta_bancaria co\r\n" + 
		  		"inner Join cliente_conta cc\r\n" + 
		  		"on cc.id_conta = co.id_conta\r\n" + 
		  		"where cc.id_cliente = ?";
	        ArrayList<ContaBancaria> contas = new ArrayList<>();

		  Connection conn = null;
	        PreparedStatement pstm = null;
	        ResultSet rs = null;
	    

          try {
	            conn = ConexaoBanco.getConexao();
	            pstm = conn.prepareStatement(selectContaBancaria);
	            pstm.setInt(1, id_cliente);
	            rs = pstm.executeQuery();
          while (rs.next()) {
                 ContaBancaria conta = new ContaBancaria();
                     
                 conta.setId_conta(rs.getInt("id_conta"));
                 conta.setNome(rs.getString("nome"));
                 conta.setCpf_titular(rs.getString("cpf"));
                 conta.setBanco(rs.getString("banco"));
                 conta.setCodigo(rs.getString("codigo"));
                 conta.setAgencia(rs.getString("agencia"));
                 conta.setConta(rs.getString("conta"));
                 
                
                contas.add(conta);
          }
          }   catch (Exception e) {
	            JOptionPane.showMessageDialog(null, "Erro ao listar contas bancarias " + e.getMessage());
	        }

          
            return contas;
		}

	  
	  public ArrayList<ContaBancaria> getContas()
	  {
		  String selectContaBancaria = "select * from conta_bancaria";
	        ArrayList<ContaBancaria> contas = new ArrayList<>();

		  Connection conn = null;
	        PreparedStatement pstm = null;
	        ResultSet rs = null;
	    

          try {
	            conn = ConexaoBanco.getConexao();
	            pstm = conn.prepareStatement(selectContaBancaria);
	          
	            rs = pstm.executeQuery();
          while (rs.next()) {
                 ContaBancaria conta = new ContaBancaria();
                     
                 conta.setId_conta(rs.getInt("id_conta"));
                 conta.setNome(rs.getString("nome"));
                 conta.setCpf_titular(rs.getString("cpf"));
                 conta.setBanco(rs.getString("banco"));
                 conta.setCodigo(rs.getString("codigo"));
                 conta.setAgencia(rs.getString("agencia"));
                 conta.setConta(rs.getString("conta"));
                 
                
                contas.add(conta);
          }
          }   catch (Exception e) {
	            JOptionPane.showMessageDialog(null, "Erro ao listar contas bancarias " + e.getMessage());
	        }

          
            return contas;
		}
	  
	  public boolean deleteContato(int id_contato, int id_cliente)
	  {
		  Connection conn = null;
	        PreparedStatement pstm = null;
	        ResultSet rs = null;
		  String sql_delete_relacao = "DELETE FROM cliente_contato WHERE id_contato = ? and id_cliente = ?";
      	  conn = null;
	        try {
	            conn = ConexaoBanco.getConexao();
	            pstm = conn.prepareStatement(sql_delete_relacao);
	 
	            pstm.setInt(1, id_contato);
	            pstm.setInt(2, id_cliente);

	 
	            pstm.execute();
	            ConexaoBanco.fechaConexao(conn, pstm);
	           // JOptionPane.showMessageDialog(null, "Relação cliente_contato excluido, banco normalizado ");
	           System.out.println("Relação cliente_contato excluido, banco normalizado ");
	            //deleta o contato
	            
	            String sql_delete_contato = "DELETE FROM contato WHERE id_contato = ?";
            	  conn = null;
   	        try {
   	            conn = ConexaoBanco.getConexao();
   	            pstm = conn.prepareStatement(sql_delete_contato);
   	 
   	            pstm.setInt(1, id_contato);
   	 
   	            pstm.execute();
   	            ConexaoBanco.fechaConexao(conn, pstm);
   	            //JOptionPane.showMessageDialog(null, "Contato Excluido, banco normalizado ");
 	           System.out.println("Contato Excluido, banco normalizado ");

   	           return true;
   	            
   	 
   	        } catch (Exception g) {
   	            JOptionPane.showMessageDialog(null, "Erro ao excluir o contato do banco de"
   	                    + "dados " + g.getMessage());
   	           return false;
   	          
   	        }
	 
	        } catch (Exception f) {
	            JOptionPane.showMessageDialog(null, "Erro a relação cliente_contato do banco de"
	                    + "dados " + f.getMessage());
	           return false;
	          
	        }
	  }
	  
	  public boolean deleteConta(int id_conta, int id_cliente)
	  {
		  
		  System.out.println("Inicio de exclusão de conta bancaria: ID da Conta: " + id_conta + " ID do Cliente: " + id_cliente);
		  Connection conn = null;
	        PreparedStatement pstm = null;
	        ResultSet rs = null;
		  String sql_delete_relacao = "DELETE FROM cliente_conta WHERE id_conta = ? and id_cliente = ?";
    	  conn = null;
	        try {
	            conn = ConexaoBanco.getConexao();
	            pstm = conn.prepareStatement(sql_delete_relacao);
	 
	            pstm.setInt(1, id_conta);
	            pstm.setInt(2, id_cliente);

	 
	            pstm.execute();
	            ConexaoBanco.fechaConexao(conn, pstm);
		  		  System.out.println("Relação cliente_conta excluido, banco normalizado");


	            //JOptionPane.showMessageDialog(null, "Relação cliente_conta excluido, banco normalizado ");
	           
	            //deleta o contato
		  		  System.out.println("Inicio de exclusão de conta bancaria: ID Conta: " + id_conta);


	            String sql_delete_conta = "DELETE FROM conta_bancaria WHERE id_conta = ?";
          	  conn = null;
 	        try {
 	            conn = ConexaoBanco.getConexao();
 	            pstm = conn.prepareStatement(sql_delete_conta);
 	 
 	            pstm.setInt(1, id_conta);
 	 
 	            pstm.execute();
 	            ConexaoBanco.fechaConexao(conn, pstm);
 	            //JOptionPane.showMessageDialog(null, "Conta bancaria excluida, banco normalizado ");
		  		  System.out.println("Conta Bancaria excluido, banco normalizado");

 	           return true;
 	            
 	 
 	        } catch (Exception g) {
 	            JOptionPane.showMessageDialog(null, "Erro ao excluir o conta bancaria do banco de"
 	                    + "dados " + g.getMessage());
 	           return false;
 	          
 	        }
	 
	        } catch (Exception f) {
	            JOptionPane.showMessageDialog(null, "Erro a relação cliente_conta do banco de"
	                    + "dados " + f.getMessage());
	           return false;
	          
	        }
	  }
	  
	  
	  public ArrayList<CadastroCliente> getClientes(int tipoBusca, int ordem, String campo_busca) {
		  String selectClientes;

		  if(tipoBusca == 1) {
			  //busca especifica ordenada

			  //selectClientes = "select * from cliente where id_cliente in (?)  or ie in (?) or apelido in (?) or cpf in (?) or cnpj in (?) or nome in (?)";  
			  selectClientes = "select * from cliente where id_cliente like (?)  or ie like (?) or apelido like (?) or cpf like (?) or cnpj like (?) or nome like (?)";  

			  if(ordem == 1) 
				  selectClientes += " order by id_cliente";
			  else if(ordem == 2)
				  selectClientes += " order by nome";
			  else if(ordem == 3)
				  selectClientes += " order by ie";
			  else if(ordem == 4)
				  selectClientes += " order by apelido";
			  else if(ordem == 5)
				  selectClientes += " order by cpf";
			  
				
		  }else {
			  //busca padrao
			    selectClientes = "SELECT * FROM cliente";

		  }
	        Connection conn = null;
	        PreparedStatement pstm = null;
	        ResultSet rs = null;
	        ArrayList<CadastroCliente> listaClientes = new ArrayList<CadastroCliente>();
	        try {
	            conn = ConexaoBanco.getConexao();
	            pstm = conn.prepareStatement(selectClientes);
	           // pstm.setString(1, chave);
	            if(tipoBusca == 1) {
	            for( int i  = 1; i <= 6; i++) {
	            	String camp_final = "%" + campo_busca + "%";
	            	pstm.setString(i, camp_final);
	            }
	            }
	            		
	            rs = pstm.executeQuery();
	            while (rs.next()) {
	                CadastroCliente cliente = new CadastroCliente();
	 

	                cliente.setTipo_pessoa(rs.getInt("tipo_cliente"));
	                if(cliente.getTipo_pessoa() == 1)
	                {
	                	//cnpj
	                	cliente.setCnpj(rs.getString("cnpj"));
	                	
	                }
	                else
	                {
	                	//cpf
	                	cliente.setCpf(rs.getString("cpf"));
	                	
	                }
	                
	                cliente.setId(rs.getInt("id_cliente"));
	                cliente.setIe(rs.getString("ie"));
	                cliente.setStatus_ie(rs.getString("status_ie"));
	                cliente.setStatus(rs.getString("status_empresa"));
                    cliente.setOcupacao(rs.getString("ocupacao"));
	                cliente.setAtividade(rs.getString("atividade"));
	                cliente.setNascimento(rs.getString("nascimento"));

	                cliente.setApelido(rs.getString("apelido"));
	                cliente.setNome(rs.getString("nome"));
	                cliente.setSobrenome(rs.getString("sobrenome"));
	                cliente.setRazao_social(rs.getString("razao_social"));
	                cliente.setNome_fantaia(rs.getString("nome_fantasia"));
	                cliente.setDescricao(rs.getString("descricao"));

	                cliente.setAt_primaria(rs.getString("at_primaria"));
	                cliente.setAt_secundaria(rs.getString("at_secundaria"));

	                cliente.setAtividade(rs.getString("atividade"));
	                cliente.setPorte(rs.getString("porte"));

	                
	                cliente.setNome_empresarial(rs.getString("nome_empresarial"));

	                
	                
	                cliente.setRua(rs.getString("rua"));
	                cliente.setNumero(rs.getString("numero"));
	                cliente.setBairro(rs.getString("bairro"));
	                cliente.setCep(rs.getString("cep"));
	                cliente.setCidade(rs.getString("cidade"));
	                cliente.setUf(rs.getString("uf"));
	                
	                cliente.setTipo_identificacao(rs.getString("tipo_identificacao"));
	                cliente.setIdentificacao_sefaz(rs.getString("identificacao"));
	                cliente.setCpf_responsavel(rs.getString("cpf_responsavel"));
	                cliente.setSenha(rs.getString("senha"));

	                cliente.setArmazem(rs.getInt("armazem"));


	               
	                
	                listaClientes.add(cliente);
	                
	            }
	            ConexaoBanco.fechaConexao(conn, pstm, rs);
	        } catch (Exception e) {
	            JOptionPane.showMessageDialog(null, "Erro ao listar clientes" + e.getMessage());
	        }
	        return listaClientes;
	
}
	  
	  
	  public class RegistroAdicionarContaBancaria
	  {
		  private boolean resposta;
          public ArrayList<Integer> ids_contas = new ArrayList<>();
		public boolean isResposta() {
			return resposta;
		}
		public void setResposta(boolean resposta) {
			this.resposta = resposta;
		}
		public ArrayList<Integer> getIds_contas() {
			return ids_contas;
		}
		public void setIds_contas(ArrayList<Integer> ids_contas) {
			this.ids_contas = ids_contas;
		}

          
	  }
	  
	  public RegistroAdicionarContaBancaria adicionarContaBancaria(ArrayList<ContaBancaria> contas, int id_cliente)
	  {
		  RegistroAdicionarContaBancaria registro = new RegistroAdicionarContaBancaria();
		  boolean resposta = false;
	        Connection conn = null;
	        PreparedStatement pstm = null;
	        ResultSet rs = null;
          ArrayList<Integer> ids_contas = new ArrayList<>();
		  System.out.println("Inicio de adição de conta bancaria: ID do Cliente: " + id_cliente);

		  for(ContaBancaria conta : contas) {
            String sql_cadastro_conta = "insert into conta_bancaria (cpf, nome, banco, codigo, agencia, conta) values ('"
            		
            		+ conta.getCpf_titular()
	    			+ "','"
	    			+ conta.getNome()
	    			+ "','"
	    			+ conta.getBanco()
	    			+ "','"
	    			+ conta.getCodigo()
                    + "','"
                    + conta.getAgencia()
                    + "','"
                    + conta.getConta()
	    			+ "')";	              
            
            
            try {
	                conn = ConexaoBanco.getConexao();

	       // PreparedStatement grava = (PreparedStatement) conn.prepareStatement(sql_cdastro_contato); 
	        //grava.execute(); 
            //JOptionPane.showMessageDialog(null, "Contato cadastrado com sucesso");
            //ConexaoBanco.fechaConexao(conn, grava);
              Statement stmt = (Statement) conn.createStatement();
              int numero = stmt.executeUpdate(sql_cadastro_conta, Statement.RETURN_GENERATED_KEYS);
              int result = -1;
              rs = stmt.getGeneratedKeys();
              
              if (rs.next()){
            	  result=rs.getInt(1);
        		  System.out.println("Conta Bancaria Inserida, Id da Conta Bancaria Inserida: " + result);
              }
              rs.close();

              stmt.close();
              
              if(result > 0)
              {
            	  ids_contas.add(result);
            	  resposta = true;
            	  registro.setIds_contas(ids_contas);
            	  registro.setResposta(resposta);
            	  
              }else {
                resposta = false; 
                registro.setIds_contas(ids_contas);
          	  registro.setResposta(resposta);
    		  System.out.println("Erro ao inserir a nova conta bancaria");

            	  break;
              }
           
              
                        } catch (Exception e) {
	                JOptionPane.showMessageDialog(null, "Erro ao inserir conta bancaria no banco de"
	                        + "dados " + e.getMessage());
	            }
	  }//fim do for
		    System.out.println("Número de contas para cadastrar: " + ids_contas.size());
          // for(int id_contato : ids_contatos )
           for(int i = 0; i < ids_contas.size(); i++)
           {
     		  System.out.println("Inicio de Adição de Relação Clinte-Conta Bancaria, ID Cliente: " + id_cliente + " ID Conta: " + ids_contas.get(i));

         	  String sql_cadastro_cliente_conta = "insert into cliente_conta (id_cliente , id_conta ) values ('"
	                		
         		+ id_cliente
	    			+ "','"
	    			+ ids_contas.get(i)
	    			+ "')";	
         	  
         	  try {
         		 conn = ConexaoBanco.getConexao();
	               
	    	       
	    	        PreparedStatement grava = (PreparedStatement) conn.prepareStatement(sql_cadastro_cliente_conta); 
	    	        grava.execute();    
	              //  JOptionPane.showMessageDialog(null, "Cliente Conta bancaria cadastrado com sucesso");
	                ConexaoBanco.fechaConexao(conn, grava);
	                resposta = true;
	                registro.setIds_contas(ids_contas);
	            	  registro.setResposta(resposta);
	         		  System.out.println("Relação Cliente-Conta Bancaria Inserida");

	               
	                } catch (Exception e) {
		                JOptionPane.showMessageDialog(null, "Erro ao crir a relação Cliente-Conta Bancaria"
		                        + " " + e.getMessage());
		                resposta = false;
		                registro.setIds_contas(ids_contas);
		            	  registro.setResposta(resposta);
		            }
           }
        	  return registro;
  
	  }

	  public class RegistroAdicionarContato
	  {
		  private boolean resposta;
          public ArrayList<Integer> ids_contatos = new ArrayList<>();
		public boolean isResposta() {
			return resposta;
		}
		public void setResposta(boolean resposta) {
			this.resposta = resposta;
		}
		public ArrayList<Integer> getIds_contatos() {
			return ids_contatos;
		}
		public void setIds_contatos(ArrayList<Integer> ids_contatos) {
			this.ids_contatos = ids_contatos;
		}

	  }
	  
	  public RegistroAdicionarContato adicionarContato(ArrayList<Contato> contatos, int id_cliente)
	  {

		  RegistroAdicionarContato registro = new RegistroAdicionarContato();
		  boolean resposta = false;
	        Connection conn = null;
	        PreparedStatement pstm = null;
	        ResultSet rs = null;
            ArrayList<Integer> ids_contatos = new ArrayList<>();
  		  System.out.println("Inicio de adição de Contato, ID do Cliente: " + id_cliente);

		  for(Contato contato : contatos) {
	          
              String sql_cdastro_contato = "insert into contato (nome_contato, cargo_contato, celular_contato, fixo_contato, e_mail_contato,descricao_contato, observacao_contato) values ('"
              		
              		+ contato.getNome()
  	    			+ "','"
  	    			+ contato.getCargo()
  	    			+ "','"
  	    			+ contato.getCelular()
  	    			+ "','"
  	    			+ contato.getFixo()
                      + "','"
                      + contato.getE_mail()
                      + "','"
                      + contato.getDescricao()
                      + "','"
                      + contato.getObservacao()
  	    			+ "')";	              
              
              
              try {
	                conn = ConexaoBanco.getConexao();

  	       // PreparedStatement grava = (PreparedStatement) conn.prepareStatement(sql_cdastro_contato); 
  	        //grava.execute(); 
              //JOptionPane.showMessageDialog(null, "Contato cadastrado com sucesso");
              //ConexaoBanco.fechaConexao(conn, grava);
                Statement stmt = (Statement) conn.createStatement();
                int numero = stmt.executeUpdate(sql_cdastro_contato, Statement.RETURN_GENERATED_KEYS);
                int result = -1;
                rs = stmt.getGeneratedKeys();
                
                if (rs.next()){
              	  result=rs.getInt(1);
        		  System.out.println("Contato Inserido, Id do Contato Inserido: " + result);

                }
                rs.close();

                stmt.close();
                
                if(result > 0)
                {
              	  ids_contatos.add(result);
              	  resposta = true;
              	registro.setIds_contatos(ids_contatos);
              	registro.setResposta(resposta);
              	  
                }else {
                  resposta = false; 
               	registro.setIds_contatos(ids_contatos);
              	registro.setResposta(resposta);
              	  break;
                }
             
                
                          } catch (Exception e) {
	                JOptionPane.showMessageDialog(null, "Erro ao inserir Contato no banco de"
	                        + "dados " + e.getMessage());
	            }
	  }//fim do for
		    System.out.println("Número de contatos para cadastrar: " + ids_contatos.size());
            // for(int id_contato : ids_contatos )
             for(int i = 0; i < ids_contatos.size(); i++)
             {
           	  String sql_cadastro_cliente_contato = "insert into cliente_contato (id_cliente , id_contato ) values ('"
	                		
           		+ id_cliente
	    			+ "','"
	    			+ ids_contatos.get(i)
	    			+ "')";	
           	  
           	  try {
           		 conn = ConexaoBanco.getConexao();
	               
	    	       
	    	        PreparedStatement grava = (PreparedStatement) conn.prepareStatement(sql_cadastro_cliente_contato); 
	    	        grava.execute();    
	            //    JOptionPane.showMessageDialog(null, "Cliente Contato cadastrado com sucesso");
	                ConexaoBanco.fechaConexao(conn, grava);
	                resposta = true;
	             	registro.setIds_contatos(ids_contatos);
	              	registro.setResposta(resposta);
	               
	                } catch (Exception e) {
		                JOptionPane.showMessageDialog(null, "Erro ao crir a tabela cliente_contato"
		                        + " " + e.getMessage());
		                resposta = false;
		             	registro.setIds_contatos(ids_contatos);
		              	registro.setResposta(resposta);
		            }
             }
          	  return registro;

	  }


	  public boolean atualizarCliente(CadastroCliente cliente) {
	        if (cliente != null) {
	            Connection conn = null;
	            String atualizar = null;
                PreparedStatement pstm;

	           
	            try {
	            if(cliente.getTipo_pessoa() == 0)
	            {//pessoa fisica
	            	 atualizar = "update cliente set nome = ?, sobrenome = ?,nascimento = ?, rg = ?, tipo_identificacao = ?, identificacao = ?, cpf_responsavel = ?, senha = ?, apelido = ? , nome_empresarial = ?, ocupacao = ?, porte = ?, atividade = ?, cpf = ?,"
	            	 		+ "ie = ?, status_ie = ?, rua = ?, bairro = ? ,cep = ? ,cidade = ?, numero = ? ,uf = ?  where id_cliente = ? ";
	            	 conn = ConexaoBanco.getConexao();
	            	 pstm = conn.prepareStatement(atualizar);

		             pstm.setString(1, cliente.getNome());
		             pstm.setString(2, cliente.getSobrenome());
		             pstm.setString(3, cliente.getNascimento());

		             pstm.setString(4, cliente.getRg());
		             pstm.setString(5, cliente.getTipo_identificacao());
		             pstm.setString(6, cliente.getIdentificacao_sefaz());
		             pstm.setString(7, cliente.getCpf_responsavel());
		             pstm.setString(8, cliente.getSenha());
		             pstm.setString(9, cliente.getApelido());
		             pstm.setString(10, cliente.getNome_empresarial());
		             pstm.setString(11, cliente.getOcupacao());
		             pstm.setString(12, cliente.getPorte());
		             pstm.setString(13, cliente.getAtividade());
		             pstm.setString(14, cliente.getCpf());


		             //dados de empresa
		             pstm.setString(15, cliente.getIe());
		             pstm.setString(16, cliente.getStatus_ie());
		             pstm.setString(17, cliente.getRua());
		             pstm.setString(18, cliente.getBairro());
		             pstm.setString(19, cliente.getCep());
		             pstm.setString(20, cliente.getCidade());
		             pstm.setString(21, cliente.getNumero());
		             pstm.setString(22, cliente.getUf());
		             
		             
		             pstm.setInt(23, cliente.getId());

		             
	            }
	            else
	            {
	            	 atualizar = "update cliente set tipo_identificacao = ?, identificacao = ?, cpf_responsavel = ?, senha = ?, apelido = ? where id_cliente = ? ";
	            	 conn = ConexaoBanco.getConexao();
	            	 pstm = conn.prepareStatement(atualizar);

	            	
		             pstm.setString(1, cliente.getTipo_identificacao());
		             pstm.setString(2, cliente.getIdentificacao_sefaz());
		             pstm.setString(3, cliente.getCpf_responsavel());
		             pstm.setString(4, cliente.getSenha());
		             pstm.setString(5, cliente.getApelido());
		             pstm.setInt(6, cliente.getId());	            	//pessoa juridica
	            }
	            	
	            	
	             
	                pstm.execute();
	                //JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
	                System.out.println("Cliente Atualizado com sucesso");
	                ConexaoBanco.fechaConexao(conn);
	              return true;
	            } catch (Exception e) {
	                JOptionPane.showMessageDialog(null, "Erro ao atualizar cliente no banco de"
	                        + "dados " + e.getMessage());
	                return false;
	            }
	        } else {
	            JOptionPane.showMessageDialog(null, "Os dados do cliente estão vazios");
	            return false;
	        }
	 
	 
	    }
	  
	

	  
	  public String string_pessoa_juridica(CadastroCliente cliente)
	  {
		  return "insert into cliente (tipo_cliente, apelido, razao_social, nome_fantasia, cnpj, descricao, at_primaria, at_secundaria, ie, status_empresa, status_ie, rua, bairro, cep, cidade, numero, uf, tipo_identificacao, identificacao, cpf_responsavel, senha, armazem) values ('"
          		
	                		+ cliente.getTipo_pessoa()
	    	    			+ "','"
	    	    			+ cliente.getApelido()
	    	    			+ "','"
	    	    			+ cliente.getRazao_social()
	    	    			+ "','"
	    	    			+ cliente.getNome_fantaia()
                            + "','"
                            + cliente.getCnpj()
                            + "','"
                             + cliente.getDescricao()
                            + "','"
                            + cliente.getAt_primaria()
                            + "','"
                            + cliente.getAt_secundaria()
                            + "','"
                            + cliente.getIe()
                            + "','"
                            + cliente.getStatus()
                            + "','"
                            + cliente.getStatus_ie()
                            + "','"
                            + cliente.getRua()
                            + "','"
                            + cliente.getBairro()
                            + "','"
                            + cliente.getCep()
                            + "','"
                            + cliente.getCidade()
                            + "','"
                            + cliente.getNumero()
                            + "','"
                            + cliente.getUf()
                            + "','"
                            + cliente.getTipo_identificacao()
                            + "','"
                            + cliente.getIdentificacao_sefaz()
                            + "','"
                            + cliente.getCpf_responsavel()
                            + "','"
                            + cliente.getSenha()
                            + "','"
                            + cliente.getArmazem()
	    	    			+ "')";	
	  }
	  
	  public String string_pessoa_fisica(CadastroCliente cliente)
	  {
		  return  "insert into cliente (tipo_cliente, apelido, cpf, nome_empresarial, nome, sobrenome, nascimento, rg, ocupacao, porte, atividade, ie, status_empresa, status_ie, rua, bairro, cep, cidade, numero, uf, tipo_identificacao, identificacao, cpf_responsavel, senha, armazem) values ('"
          		
      		+ cliente.getTipo_pessoa()
  			+ "','"
  			+ cliente.getApelido()
  			+ "','"
  			+ cliente.getCpf()
  			+ "','"
  			+ cliente.getNome_empresarial()
              + "','"
              + cliente.getNome()
              + "','"
               + cliente.getSobrenome()
              + "','"
              + cliente.getNascimento()
              + "','"
              + cliente.getRg()
              + "','"
              + cliente.getOcupacao()
              + "','"
              + cliente.getPorte()
              + "','"
              + cliente.getAtividade()
              + "','"
              + cliente.getIe()
              + "','"
              + cliente.getStatus()
              + "','"
              + cliente.getStatus_ie()
              + "','"
              + cliente.getRua()
              + "','"
              + cliente.getBairro()
              + "','"
              + cliente.getCep()
              + "','"
              + cliente.getCidade()
              + "','"
              + cliente.getNumero()
              + "','"
              + cliente.getUf()
              + "','"
              + cliente.getTipo_identificacao()
              + "','"
              + cliente.getIdentificacao_sefaz()
              + "','"
              + cliente.getCpf_responsavel()
              + "','"
              + cliente.getSenha()
              + "','"
              + cliente.getArmazem()
  			+ "')";		 
		  
	  }
	  
	 
		
	  
	  
}
	

