package conexaoBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import cadastros.CadastroCliente;
import cadastros.CadastroContrato;
import cadastros.CadastroLogin;
import conexaoBanco.GerenciarBancoContratos.Registro;
import outros.DadosGlobais;
import tratamento_proprio.Log;

public class GerenciarBancoLogin {

	private Log GerenciadorLog;
	private CadastroLogin loginGlobal;
	private String selectUsuarios = "select * from usuarios";
	
	private Registro registro_geral_relacao_usuarios_privilegios = new Registro();
	private Registro registro_geral_relacao_usuarios_preferencias = new Registro();

	
	public GerenciarBancoLogin() {
		getDadosGlobais();

	}
	
	public ArrayList<CadastroLogin> getUsuarios() {
		

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        ArrayList<CadastroLogin> listaUsuarios = new ArrayList<CadastroLogin>();
        try {
            conn = ConexaoBanco.getConexao();
            pstm = conn.prepareStatement(selectUsuarios);
            rs = pstm.executeQuery();
            while (rs.next()) {
                CadastroLogin Login = new CadastroLogin();
 
                Login.setId(rs.getInt("id_usuario"));
	            Login.setNome(rs.getString("nome"));
	            Login.setSobrenome(rs.getString("sobrenome"));
	            Login.setCelular(rs.getString("celular"));
	            Login.setCargo(rs.getString("cargo"));
                Login.setIp_ativo(rs.getString("ip_ativo"));
	            Login.setLogin(rs.getString("login"));
	            Login.setEmail(rs.getString("email"));
	            Login.setSenha(rs.getString("senha"));
	            Login.setSenhaEmail(rs.getString("senha_email"));
	            Login.setEmail2(rs.getString("email2"));
	            Login.setSenhaEmail2(rs.getString("senha_email2"));
	            Login.setGenero(rs.getString("genero"));
	               
	            
                
                listaUsuarios.add(Login);
            }
            ConexaoBanco.fechaConexao(conn, pstm, rs);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar usuarios"  );
        }
        return listaUsuarios;
    }


	
	 public CadastroLogin buscaLogin(String login)
	 {
		 Connection conn = null;
	     PreparedStatement pstm = null;
	     ResultSet rs = null;
         CadastroLogin Login = new CadastroLogin();

	     
	        try {
                //String busca = "select * from estoque_filamentos where id in (?)  or quantidade in (?) or filamento in (?) or filamentos in (?)";
          	//  String selectEstoqueFilamentosFiltro =" select estoque_filamentos.id, estoque_filamentos.quantidade, estoque_filamentos.filamento, filamentos.nome from estoque_filamentos join filamentos on estoque_filamentos.filamento = filamentos.id";
 String busca = "call buscar_usuario(?);";
     
	            conn = ConexaoBanco.getConexao();
	            pstm = conn.prepareStatement(busca);
	            pstm.setString(1, login);
	            
	            
	            
	            rs = pstm.executeQuery();
	            rs.next();
	 
	            Login.setId(rs.getInt("id_usuario"));
	            Login.setNome(rs.getString("nome"));
	            Login.setSobrenome(rs.getString("sobrenome"));
	            Login.setCelular(rs.getString("celular"));
	            Login.setCargo(rs.getString("cargo"));
	            Login.setIp_ativo(rs.getString("ip_ativo"));

	            Login.setLogin(rs.getString("login"));
	            Login.setEmail(rs.getString("email"));
	            Login.setSenha(rs.getString("senha"));
	            Login.setSenhaEmail(rs.getString("senha_email"));
	            Login.setGenero(rs.getString("genero"));
	            Login.setEmail2(rs.getString("email2"));
	            Login.setSenhaEmail2(rs.getString("senha_email2"));
	            
	            //get dados de privilegios
	            CadastroLogin.Privilegios privilegios = new CadastroLogin.Privilegios();
	            privilegios.setNivel_privilegios(rs.getInt("nivel_privilegio"));
	            privilegios.setPrivilegio_alterar_apis(rs.getInt("alterar_apis"));
	            privilegios.setId_privilegios(rs.getInt("id_privilegios"));
	            
	            CadastroLogin.Preferencias preferencias = new CadastroLogin.Preferencias();
	            preferencias.setApi_exato(rs.getInt("api_exato"));
	            preferencias.setApi_sintegra(rs.getInt("api_sintegra"));
	            preferencias.setApi_whatsapp(rs.getInt("api_whatsapp"));
	            preferencias.setId_preferencias(rs.getInt("id_preferencias"));

	            Login.setConfigs_preferencias(preferencias);
	            Login.setConfigs_privilegios(privilegios);	
	            
	            ConexaoBanco.fechaConexao(conn, pstm, rs);
	        } catch (Exception e) {
	           // JOptionPane.showMessageDialog(null, "Erro ao buscar login ="+login+" no bando de dados"  );
	        }
	        return Login;
		 
		
	 }
	 
	
	 
	 
	 
	 
	 public CadastroLogin getLogin(int id)
	 {
		 
		 
		 Connection conn = null;
	     PreparedStatement pstm = null;
	     ResultSet rs = null;
         CadastroLogin Login = new CadastroLogin();

	     
	        try {
                //String busca = "select * from estoque_filamentos where id in (?)  or quantidade in (?) or filamento in (?) or filamentos in (?)";
          	//  String selectEstoqueFilamentosFiltro =" select estoque_filamentos.id, estoque_filamentos.quantidade, estoque_filamentos.filamento, filamentos.nome from estoque_filamentos join filamentos on estoque_filamentos.filamento = filamentos.id";
 String busca = "select * from usuarios where id_usuario = ?";
     
	            conn = ConexaoBanco.getConexao();
	            pstm = conn.prepareStatement(busca);
	            pstm.setInt(1, id);
	            
	            
	            
	            rs = pstm.executeQuery();
	            rs.next();
	 
	            Login.setId(rs.getInt("id_usuario"));
	            Login.setNome(rs.getString("nome"));
	            Login.setSobrenome(rs.getString("sobrenome"));
	            Login.setCelular(rs.getString("celular"));
	            Login.setCargo(rs.getString("cargo"));
                Login.setIp_ativo(rs.getString("ip_ativo"));
	            Login.setLogin(rs.getString("login"));
	            Login.setEmail(rs.getString("email"));
	            Login.setSenha(rs.getString("senha"));
	            Login.setSenhaEmail(rs.getString("senha_email"));
	            Login.setGenero(rs.getString("genero"));
	           
	            Login.setEmail2(rs.getString("email2"));
	            Login.setSenhaEmail2(rs.getString("senha_email2"));
	            ConexaoBanco.fechaConexao(conn, pstm, rs);
	        } catch (Exception e) {
	            //JOptionPane.showMessageDialog(null, "Erro ao buscar id ="+id+" no bando de dados"  );
	        }
	        return Login;
		 
		
	 }
	 
	 
	 
	  public int inserirLogin(CadastroLogin login) {
		  
			  int retorno_login_inserido = inserir_login_retorno(login);
			  
			  if(retorno_login_inserido > 0 ) {
				  JOptionPane.showMessageDialog(null, "Usuario inserido na tabela de usuarios");
				  //inserir privilegios
				  int retorno_privilegio_inserido = inserir_privilegios_retorno(login.getConfigs_privilegios());
				  if(retorno_privilegio_inserido > 0) {
					  JOptionPane.showMessageDialog(null, "privilegios inserido na tabela de privigelios");

					  //privilegios inseridos, inserir tabela usuarios_privilegios
					  if(inserir_usuarios_privilegios(retorno_login_inserido, retorno_privilegio_inserido) ) {
						  JOptionPane.showMessageDialog(null, "relação usaario_privilegio inserido na tabela com sucesso");

						  //privilegios cadastrados com sucesso
						  //inserir preferencias
						  int retorno_preferencias_inserido = inserir_preferencias_retorno(login.getConfigs_preferencias());
						  if(retorno_preferencias_inserido > 0) {
							  JOptionPane.showMessageDialog(null, "preferencias inseridas na tabela de preferencias");

							  //preferencias inseridas, inserir tabela relacao usuario preferencias
							  if(inserir_usuarios_preferencias(retorno_login_inserido, retorno_preferencias_inserido)) {
								  JOptionPane.showMessageDialog(null, "relação usaario_preferencias inserido na tabela com sucesso");

								  System.out.println("Usuario Cadastrado com sucesso!");
								  //cadastro de usuario concluido, return 1;
								  return 1;
							  }else {
								  //erro ao cadastrar preferencias, deletar tabelas criadas
								  //remover tabela preferencias, usuario preferencias, remover tabela usuario e remover tabela de usuarios e usuarios_privilegios
								  boolean remover_privilegios = remover_privilegios(retorno_privilegio_inserido);
								  boolean remover_usuario = remover_usuario(retorno_login_inserido);
								  boolean remover_Usuarios_privilegios = remover_usuario_privilegios(retorno_login_inserido, retorno_privilegio_inserido);
								  boolean remover_preferencias = remover_preferencias(retorno_preferencias_inserido);
								  boolean remover_Usuarios_preferencias = remover_usuario_preferencias(retorno_login_inserido, retorno_preferencias_inserido);

								  
								  if(remover_privilegios && remover_usuario && remover_Usuarios_privilegios && remover_preferencias && remover_Usuarios_preferencias) {
									  System.out.println("Erro ao cadastrar tabela de usuario_preferencias para o usuario, banco de dados normalizado");

									  return 0;
								  }else {
									  System.out.println("Erro ao remover tabelas de usuario do banco de dados, banco de dados corrompido");
									  return -1;
								  }
							  }
							  
						  }else {
							  //erro ao cadastrar preferencias, deletar tabelas criadas
							  //remover tabela preferencias, usuario preferencias, remover tabela usuario e remover tabela de usuarios e usuarios_privilegios
							  boolean remover_privilegios = remover_privilegios(retorno_privilegio_inserido);
							  boolean remover_usuario = remover_usuario(retorno_login_inserido);
							  boolean remover_Usuarios_privilegios = remover_usuario_privilegios(retorno_login_inserido, retorno_privilegio_inserido);
							  boolean remover_preferencias = remover_preferencias(retorno_preferencias_inserido);

							  
							  if(remover_privilegios && remover_usuario && remover_Usuarios_privilegios && remover_preferencias) {
								  System.out.println("Erro ao cadastrar tabela de preferencias para o usuario, banco de dados normalizado");

								  return 0;
							  }else {
								  System.out.println("Erro ao remover tabela de usuario do banco de dados, banco de dados corrompido");
								  return -1;
							  }
						  }
						  
					  }else {
						  //erro ao cadastrar privilegios, deletar tabelas criadas
						  //remover tabela privilegios, remover tabela usuario e remover tabela de usuarios_privilegios
						  boolean remover_privilegios = remover_privilegios(retorno_privilegio_inserido);
						  boolean remover_usuario = remover_usuario(retorno_login_inserido);
						  boolean remover_Usuarios_privilegios = remover_usuario_privilegios(retorno_login_inserido, retorno_privilegio_inserido);

						  if(remover_privilegios && remover_usuario && remover_Usuarios_privilegios ) {
							  System.out.println("Erro ao cadastrar tabela de usuario_privilegios para o usuario, banco de dados normalizado");

							  return 0;
						  }else {
							  System.out.println("Erro ao remover tabela de privilegios e usuario do banco de dados, banco de dados corrompido");
							  return -1;
						  }
						  
					  }
					  
					  
					  
				  }else {
					  JOptionPane.showMessageDialog(null, "erro ao inserir privilegios na tabela de privigelios");

					  //erro ao inserir privilegios, deletar usuario criado
					  if(remover_usuario(retorno_login_inserido)) {
						  System.out.println("Erro ao cadastrar tabela de privilegios para o usuario, banco de dados normalizado");

						  return -1;
					  }else {
						  System.out.println("Erro ao remover tabela de privilegios e usuario do banco de dados, banco de dados corrompido");

						  //houve corrupcao no banco de dados, um usuario sem priviegios e direitos esta cadastrado
						  return -2;
					  }
					  
				  }

				  
				  
			  }else if(retorno_login_inserido == -2) {
				  System.out.println("Login já cadastrado!");
				  return 0;
			  }else {
				  System.out.println("Erro ao cadastrar usuario!");

				  return -1;
			  }
		 
		  
	  }
	
	  
	  public boolean atualizarUsuario(CadastroLogin login) {
		 return atualizar_usuario(login) && atualizarPreferencias(login) && atualizarPrivilegios(login);
		  
	  }
	  
	  private boolean atualizar_usuario(CadastroLogin login) {
		  if (login != null) {
	            Connection conn = null;
	            String atualizar = null;
            PreparedStatement pstm;

	           
	            try {
	          
	            	 atualizar = "update usuarios set nome = ?, sobrenome = ?,celular = ? , cargo = ?, login = ?, email = ?, senha = ?, senha_email = ?,  genero = ?, email2 = ?, senha_email2 = ? where id_usuario = ? ";
	            	 conn = ConexaoBanco.getConexao();
	            	 pstm = conn.prepareStatement(atualizar);

		             pstm.setString(1, login.getNome());
		             pstm.setString(2, login.getSobrenome());
		             pstm.setString(3, login.getCelular());
		             pstm.setString(4, login.getCargo());
		             pstm.setString(5, login.getLogin());
		             pstm.setString(6, login.getEmail());
		             pstm.setString(7, login.getSenha());
		             pstm.setString(8, login.getSenhaEmail());
		             pstm.setString(9, login.getGenero());
		             
		             pstm.setString(10, login.getEmail2());
		             pstm.setString(11, login.getSenhaEmail2());
		             

		             pstm.setInt(12, login.getId());

		            
	             
	                pstm.execute();
	                //JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
	                System.out.println("usuario atualizado com sucesso");
	                ConexaoBanco.fechaConexao(conn);
	              return true;
	            } catch (Exception e) {
	                JOptionPane.showMessageDialog(null, "Erro ao atualizar usuario no banco de"
	                        + "dados "  );
	                return false;
	            }
	        } else {
	            JOptionPane.showMessageDialog(null, "Os dados de usuario estão vazios");
	            return false;
	        }
		  
	  }
	  
	  private boolean atualizarPreferencias(CadastroLogin login) {
		  if (login != null) {
	            Connection conn = null;
	            String atualizar = null;
              PreparedStatement pstm;

	           
	            try {
	          
	            	 atualizar = "update preferencias set api_sintegra = ?, api_whatsapp = ?,api_exato = ? where id_preferencias = ? ";
	            	 conn = ConexaoBanco.getConexao();
	            	 pstm = conn.prepareStatement(atualizar);

		             pstm.setInt(1, login.getConfigs_preferencias().getApi_sintegra());
		             pstm.setInt(2, login.getConfigs_preferencias().getApi_whatsapp());
		             pstm.setInt(3, login.getConfigs_preferencias().getApi_exato());

		           
		             
		             pstm.setInt(4, login.getConfigs_preferencias().getId_preferencias());

		            
	             
	                pstm.execute();
	                //JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
	                System.out.println("preferencias atualizadas com sucesso");
	                ConexaoBanco.fechaConexao(conn);
	              return true;
	            } catch (Exception e) {
	                JOptionPane.showMessageDialog(null, "Erro ao atualizar preferencias no banco de"
	                        + "dados "  );
	                return false;
	            }
	        } else {
	            JOptionPane.showMessageDialog(null, "Os dados de preferencias estão vazios");
	            return false;
	        }
		  
	  }
	  
	  
	  private boolean atualizarPrivilegios(CadastroLogin login) {
		  if (login != null) {
	            Connection conn = null;
	            String atualizar = null;
              PreparedStatement pstm;

	           
	            try {
	          
	            	 atualizar = "update privilegios set nivel_privilegio = ?, alterar_apis = ? where id_privilegios = ? ";
	            	 conn = ConexaoBanco.getConexao();
	            	 pstm = conn.prepareStatement(atualizar);

		             pstm.setInt(1, login.getConfigs_privilegios().getNivel_privilegios());
		             pstm.setInt(2, login.getConfigs_privilegios().getPrivilegio_alterar_apis());

		           
		             
		             pstm.setInt(3, login.getConfigs_privilegios().getId_privilegios());

		            
	             
	                pstm.execute();
	                //JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
	                System.out.println("privilegios atualizadas com sucesso");
	                ConexaoBanco.fechaConexao(conn);
	              return true;
	            } catch (Exception e) {
	                JOptionPane.showMessageDialog(null, "Erro ao atualizar privilegios no banco de"
	                        + "dados "  );
	                return false;
	            }
	        } else {
	            JOptionPane.showMessageDialog(null, "Os dados de privilegios estão vazios");
	            return false;
	        }
		  
	  }
	  
	  
	  private int inserir_login_retorno(CadastroLogin login) {
		   int result = -1;
		   int id_cliente = -1;
		   
		   if(buscaLoginExiste(login)) {
			   //cadastro já existe, retonra -1;
			   return -2;
			   
		   }else {
			   
			   if( login != null) {
		            Connection conn = null;
		            try {
		            	
		                conn = ConexaoBanco.getConexao();
		    	       
		                String query = sql_login(login);
	                  Statement stmt = (Statement) conn.createStatement();
	                  int numero = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

	                  ResultSet rs = stmt.getGeneratedKeys();
	                  if (rs.next()){
	 	                                result=rs.getInt(1);
	 	                                System.out.println("Id login inserido: "+ result);
	                                }
	                   rs.close();
	                   stmt.close();
	                   
	                   return result;
		 
		            } catch (Exception e) {
		               JOptionPane.showMessageDialog(null, "Erro ao inserir o login no banco de"
		                      + "dados "  );
		            	GerenciadorLog.registrarLogDiario("falha", "falha ao adicionar novo login: "   + " causa: "  );
		                return -1;
		            }
		        } else {
		            System.out.println("O login enviado por parametro esta vazio");
		            return -1;
		        } 
			   
		   }
		   
		 
	  }
	  
	  private boolean remover_usuario(int id_usuario) {
		  String sql_delete_relacao = "DELETE FROM usuarios WHERE id_usuario = ?";
		  Connection conn = null;
	        ResultSet rs = null;	      
	        try {
	            conn = ConexaoBanco.getConexao();
	            PreparedStatement pstm;
	            pstm = conn.prepareStatement(sql_delete_relacao);
	 
	            pstm.setInt(1, id_usuario);
	 
	            pstm.execute();
	            ConexaoBanco.fechaConexao(conn, pstm);
	            JOptionPane.showMessageDialog(null, "Usuario excluido com sucesso!");
	           return true;
	            
	 
	        } catch (Exception f) {
	            JOptionPane.showMessageDialog(null, "Erro ao excluir usuario da base de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
	                    + "dados " + f.getMessage());
	           return false;
	        }
		  
		  
	  }
	  
	
	  
	  private int inserir_privilegios_retorno(CadastroLogin.Privilegios privilegios) {
		   int result = -1;
		   int id_cliente = -1;
		   
		 
			   
			   if( privilegios != null) {
		            Connection conn = null;
		            try {
		            	
		                conn = ConexaoBanco.getConexao();
		    	       
		                String query = sql_privilegios(privilegios);
	                  Statement stmt = (Statement) conn.createStatement();
	                  int numero = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

	                  ResultSet rs = stmt.getGeneratedKeys();
	                  if (rs.next()){
	 	                                result=rs.getInt(1);
	 	                                System.out.println("Id privilegios inserido: "+ result);
	                                }
	                   rs.close();
	                   stmt.close();
	                   
	                   return result;
		 
		            } catch (Exception e) {
		               JOptionPane.showMessageDialog(null, "Erro ao inserir o prvilegios no banco de"
		                      + "dados "  );
		            	GerenciadorLog.registrarLogDiario("falha", "falha ao adicionar novo privilegio: "   + " causa: "  );
		                return -1;
		            }
		        } else {
		            System.out.println("O privilegio enviado por parametro esta vazio");
		            return -1;
		        } 
			   
		   
		   
		 
	  }
	  
	  private boolean inserir_usuarios_privilegios(int id_login, int id_privilegios){
		  Connection conn = null;
          try {
              conn = ConexaoBanco.getConexao();
              String sql = "insert into usuario_privilegios\r\n" + 
              		"(id_usuario , id_privilegios ) values ('"
  	    			+ id_login
  	    			+  "','"
  	    			+ id_privilegios	
  	    			+ "')";
  	       
  	        PreparedStatement grava = (PreparedStatement) conn.prepareStatement(sql); 
  	        grava.execute();    
              ConexaoBanco.fechaConexao(conn, grava);
              JOptionPane.showMessageDialog(null, "Relação usuario_privilegios cadastrado com sucesso");
              registro_geral_relacao_usuarios_privilegios.setIdUsuario(id_login);
              registro_geral_relacao_usuarios_privilegios.adicionar_id(id_privilegios);
               return true;

          } catch (Exception e) {
        	  JOptionPane.showMessageDialog(null, "Erro ao inserir a relação usuario_privilegios no banco de"
                       + " dados, usuario: " + id_login + " contrado: " + id_privilegios  );
               return false;
          }
 			  
 	         
	  }
	  
	  
	  private boolean remover_privilegios(int id_privilegios) {
		  String sql_delete_relacao = "DELETE FROM privilegios WHERE id = ?";
		  Connection conn = null;
	        ResultSet rs = null;	      
	        try {
	            conn = ConexaoBanco.getConexao();
	            PreparedStatement pstm;
	            pstm = conn.prepareStatement(sql_delete_relacao);
	 
	            pstm.setInt(1, id_privilegios);
	 
	            pstm.execute();
	            ConexaoBanco.fechaConexao(conn, pstm);
	            JOptionPane.showMessageDialog(null, "Privilegio excluido com sucesso!");
	           return true;
	            
	 
	        } catch (Exception f) {
	            JOptionPane.showMessageDialog(null, "Erro ao excluir privilegio da base de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
	                    + "dados " + f.getMessage());
	           return false;
	        }
		  
		  
	  }
	 
	  
	  
	  private boolean remover_usuario_privilegios( int id_usuario, int id_privilegios) {
		  String sql_delete_relacao = "DELETE FROM usuario_privilegios WHERE id_usuario  = ? and id_privilegios  = ?";
		  Connection conn = null;
	        ResultSet rs = null;	      
	        try {
	            conn = ConexaoBanco.getConexao();
	            PreparedStatement pstm;
	            pstm = conn.prepareStatement(sql_delete_relacao);
	 
	            pstm.setInt(1, id_usuario);
	            pstm.setInt(2,  id_privilegios);
	 
	            pstm.execute();
	            ConexaoBanco.fechaConexao(conn, pstm);
	            JOptionPane.showMessageDialog(null, "Relação usuarios_privilegios excluido, banco normalizado ");
	           return true;
	            
	 
	        } catch (Exception f) {
	            JOptionPane.showMessageDialog(null, "Erro ao exlcuir relação usuarios_privilegios do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
	                    + "dados " + f.getMessage());
	           return false;
	        }
		  
		  
	  }
	 
	  private int inserir_preferencias_retorno(CadastroLogin.Preferencias preferencias) {
		   int result = -1;
		   int id_cliente = -1;
		   
		 
			   
			   if( preferencias != null) {
		            Connection conn = null;
		            try {
		            	
		                conn = ConexaoBanco.getConexao();
		    	       
		                String query = sql_preferencias(preferencias);
	                  Statement stmt = (Statement) conn.createStatement();
	                  int numero = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

	                  ResultSet rs = stmt.getGeneratedKeys();
	                  if (rs.next()){
	 	                                result=rs.getInt(1);
	 	                                System.out.println("Id preferencias inserido: "+ result);
	                                }
	                   rs.close();
	                   stmt.close();
	                   
	                   return result;
		 
		            } catch (Exception e) {
		               JOptionPane.showMessageDialog(null, "Erro ao inserir o preferencias no banco de"
		                      + "dados "  );
		            	GerenciadorLog.registrarLogDiario("falha", "falha ao adicionar novas preferencias: "   + " causa: "  );
		                return -1;
		            }
		        } else {
		            System.out.println("As preferencias enviado por parametro esta vazio");
		            return -1;
		        } 
			   
		   
		   
		 
	  }
	  
	  private boolean inserir_usuarios_preferencias(int id_login, int id_preferencias){
		  Connection conn = null;
          try {
              conn = ConexaoBanco.getConexao();
              String sql = "insert into usuario_preferencias\r\n" + 
              		"(id_usuario , id_preferencias  ) values ('"
  	    			+ id_login
  	    			+  "','"
  	    			+ id_preferencias	
  	    			+ "')";
  	       
  	        PreparedStatement grava = (PreparedStatement) conn.prepareStatement(sql); 
  	        grava.execute();    
              ConexaoBanco.fechaConexao(conn, grava);
              JOptionPane.showMessageDialog(null, "Relação usuario_preferencias cadastrado com sucesso");
              registro_geral_relacao_usuarios_preferencias.setIdUsuario(id_login);
              registro_geral_relacao_usuarios_preferencias.adicionar_id(id_preferencias);
               return true;

          } catch (Exception e) {
        	  JOptionPane.showMessageDialog(null, "Erro ao inserir a relação usuario_privilegios no banco de"
                       + " dados, usuario: " + id_login + " preferencias: " + id_preferencias  );
               return false;
          }
 			  
 	         
	  }
	  
	  
	  private boolean remover_preferencias(int id_preferencias) {
		  String sql_delete_relacao = "DELETE FROM preferencias WHERE id = ?";
		  Connection conn = null;
	        ResultSet rs = null;	      
	        try {
	            conn = ConexaoBanco.getConexao();
	            PreparedStatement pstm;
	            pstm = conn.prepareStatement(sql_delete_relacao);
	 
	            pstm.setInt(1, id_preferencias);
	 
	            pstm.execute();
	            ConexaoBanco.fechaConexao(conn, pstm);
	            JOptionPane.showMessageDialog(null, "Preferencias excluido com sucesso!");
	           return true;
	            
	 
	        } catch (Exception f) {
	            JOptionPane.showMessageDialog(null, "Erro ao excluir Preferencias da base de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
	                    + "dados " + f.getMessage());
	           return false;
	        }
		  
		  
	  }
	 
	  
	  
	  private boolean remover_usuario_preferencias( int id_usuario, int id_preferencias) {
		  String sql_delete_relacao = "DELETE FROM usuario_preferencias WHERE id_usuario  = ? and id_preferencias   = ?";
		  Connection conn = null;
	        ResultSet rs = null;	      
	        try {
	            conn = ConexaoBanco.getConexao();
	            PreparedStatement pstm;
	            pstm = conn.prepareStatement(sql_delete_relacao);
	 
	            pstm.setInt(1, id_usuario);
	            pstm.setInt(2,  id_preferencias);
	 
	            pstm.execute();
	            ConexaoBanco.fechaConexao(conn, pstm);
	            JOptionPane.showMessageDialog(null, "Relação usuario_preferencias excluido, banco normalizado ");
	           return true;
	            
	 
	        } catch (Exception f) {
	            JOptionPane.showMessageDialog(null, "Erro ao exlcuir relação usuario_preferencias do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
	                    + "dados " + f.getMessage());
	           return false;
	        }
		  
		  
	  }
	  
	  private boolean buscaLoginExiste(CadastroLogin login) {
		  CadastroLogin busca = buscaLogin(login.getLogin());
		   boolean ja_existe = false;
		   
		   try {
           if(busca != null)
			 ja_existe = busca.getLogin().equals(login.getLogin());
		   }catch( Exception y) {
		     ja_existe = false;
		   }
		   
		   return ja_existe;
	  }
	  
	  
	 
	  private String sql_login(CadastroLogin login) {
		  String sql = "insert into usuarios\r\n" + 
          		"(nome, sobrenome, celular, cargo, login, email, senha, senha_email, genero, email2, senha_email2) values ('"
	    			+ login.getNome()
	    			+ "','"
	    			+ login.getSobrenome()
	    			+ "','"
	    			+ login.getCelular()
	    			+ "','"
	    			+ login.getCargo()
	    			+ "','"
	    			+ login.getLogin()
	    			+ "','"
	    			+ login.getEmail()
	    			+ "','"
	    			+ login.getSenha()
	    			+ "','"
	    			+ login.getSenhaEmail()
	    			+ "','"
	    			+ login.getGenero()
	    			+ "','"
	    			+ login.getEmail2()
	    			+ "','"
	    			+ login.getSenhaEmail2()
	    			+ "')";
          
		  return sql;
		  
	  }
	  
	  private String sql_privilegios(CadastroLogin.Privilegios privilegios) {
		  String sql = "insert into privilegios\r\n" + 
          		"(nivel_privilegio, alterar_apis) values ('"
	    			+ privilegios.getNivel_privilegios()
	    			+ "','"
	    			+ privilegios.getPrivilegio_alterar_apis()
	    			+ "')";
          
		  return sql;
		  
	  }
	  
	  private String sql_preferencias(CadastroLogin.Preferencias preferencias) {
		  String sql = "insert into preferencias\r\n" + 
          		"(api_sintegra, api_whatsapp, api_exato) values ('"
	    			+ preferencias.getApi_sintegra()
	    			+ "','"
	    			+ preferencias.getApi_whatsapp()
	    			+ "','"
	    			+ preferencias.getApi_exato()
	    			+ "')";
          
		  return sql;
		  
	  }
	  
	  public class Registro
	  {
		 //classe que registra durante o processo de cadastramento de contrato no banco de dados
		  //tanto o id_usuario como o id_tabelas para que se houver falha o processo seja desfeito.
		  
		  private int id_usuario;
		  private ArrayList<Integer> ids_tabelas;
		  public Registro() {
			  ids_tabelas  = new ArrayList<Integer>();
			  
		  }
		 
		  private void setIdUsuario(int id_usuario) {
			  this.id_usuario = id_usuario;
		  }
		  
		  private int getIdUsuario() {
			  return this.id_usuario;
		  }
		  
		  private void adicionar_id(int id_adicionar) {
			  ids_tabelas.add(id_adicionar);
		  }
		  
		  private ArrayList<Integer> getIds() {
			  return this.ids_tabelas;
		  }
		  
	  }
	  
	  public boolean informarLogado(String ip, int id_usuario) {
		  Connection conn = null;
          PreparedStatement pstm;
		  String sql = "update usuarios set ip_ativo = ? where id_usuario = ?";
		  
		  try {
			 conn = ConexaoBanco.getConexao();
        	 pstm = conn.prepareStatement(sql);
             pstm.setString(1, ip);
             pstm.setInt(2, id_usuario);


        	  pstm.execute();

        	  System.out.println("Ip Atualizado com sucesso");
              ConexaoBanco.fechaConexao(conn);
        	 return true;
		  }catch(Exception e) {
        	  System.out.println("Erro ao atualizar ip, erro: "  );

			  return false;
		  }
        	 
		  
	  }
	  
	  
	  public void getDadosGlobais() {
			//gerenciador de log
			DadosGlobais dados = DadosGlobais.getInstance();
			 GerenciadorLog = dados.getGerenciadorLog();
			 
			 //usuario logado
			 loginGlobal = dados.getLogin();
		}
	
	  
	  
	  public ArrayList<CadastroLogin.Mensagem> getMensagens(int id_remetente, int id_destinatario) {
		  

	        Connection conn = null;
	        PreparedStatement pstm = null;
	        ResultSet rs = null;
	        String selectMensagens = "select * from mensagem where id_remetente = ? and id_destinatario = ? \r\n"
	        		+ "union select * from mensagem where id_remetente = ? and id_destinatario = ? order by id_mensagem";
	        
	        ArrayList<CadastroLogin.Mensagem> listaMensagens = new ArrayList<>();
	        try {
	            conn = ConexaoBanco.getConexao();
	            pstm = conn.prepareStatement(selectMensagens);
	            pstm.setInt(1, id_remetente);
	            pstm.setInt(2, id_destinatario);
	            pstm.setInt(3, id_destinatario);
	            pstm.setInt(4, id_remetente);

	            
	            rs = pstm.executeQuery();
	            while (rs.next()) {
	            	CadastroLogin.Mensagem msg = new CadastroLogin.Mensagem();
	                msg.setId_mensagem(rs.getInt("id_mensagem"));
	                msg.setData(rs.getString("data_mensagem"));
	                msg.setHora(rs.getString("hora_mensagem"));
	                msg.setConteudo(rs.getString("conteudo"));
	                msg.setId_remetente(rs.getInt("id_remetente"));
	                msg.setId_destinatario(rs.getInt("id_destinatario"));
	            
	            	listaMensagens.add(msg);
	            }
	            ConexaoBanco.fechaConexao(conn, pstm, rs);
	        } catch (Exception e) {
	            JOptionPane.showMessageDialog(null, "Erro ao listar mensagens do usuario"  );
	        }
	        
	        return listaMensagens;
		  
		  
		  
	  }
	  
	  
	  public boolean enviarMensagem(CadastroLogin.Mensagem msg) {
		  
		  //inseria a mensagem
		  int retorno = inserir_mensagem_retorno(msg);
		 if( retorno >0 ) {
			 
			/* if(inserir_usuario_mensagens(id_usuario, retorno)) {
				 //JOptionPane.showMessageDialog(null, "Mensagem Enviada");

				 return true;

			 }else {
				 System.out.println("Erro ao enviar a mensagem");
                     return false;
			 }*/
			 return true;
			 
		 }else {
			 System.out.println("Erro ao enviar a mensagem");
			 return false;
		 }
		  
		  
	  }
	  
	  
	  private int inserir_mensagem_retorno(CadastroLogin.Mensagem msg) {
		   int result = -1;
		   int id_cliente = -1;
		   
		 
			   
			   if( msg != null) {
		            Connection conn = null;
		            try {
		            	
		                conn = ConexaoBanco.getConexao();
		    	       
		                String query = sql_msg(msg);
	                  Statement stmt = (Statement) conn.createStatement();
	                  int numero = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

	                  ResultSet rs = stmt.getGeneratedKeys();
	                  if (rs.next()){
	 	                                result=rs.getInt(1);
	 	                                System.out.println("Id mensagem inserida: "+ result);
	                                }
	                   rs.close();
	                   stmt.close();
	                   
	                   return result;
		 
		            } catch (Exception e) {
		               JOptionPane.showMessageDialog(null, "Erro ao inserir a mensagem no banco de"
		                      + "dados "  );
		            	GerenciadorLog.registrarLogDiario("falha", "falha ao inserir mensagem ao banco de dados: "   + " causa: "  );
		                return -1;
		            }
		        } else {
		            System.out.println("a mensagem enviado por parametro esta vazio");
		            return -1;
		        } 
			   
		   
		   
		 
	  }
	  
	 /* private boolean inserir_usuario_mensagens(int id_usuario, int id_msg){
		  Connection conn = null;
          try {
              conn = ConexaoBanco.getConexao();
              String sql = "insert into usuario_mensagens\r\n" + 
              		"(id_usuario , id_mensagem ) values ('"
  	    			+ id_usuario
  	    			+  "','"
  	    			+ id_msg	
  	    			+ "')";
  	       
  	        PreparedStatement grava = (PreparedStatement) conn.prepareStatement(sql); 
  	        grava.execute();    
              ConexaoBanco.fechaConexao(conn, grava);
              JOptionPane.showMessageDialog(null, "Relação usuario_mensagem cadastrado com sucesso");
        
               return true;

          } catch (Exception e) {
        	  JOptionPane.showMessageDialog(null, "Erro ao inserir a relação usuario_mensagens no banco de"
                       + " dados, usuario: " + id_usuario + " id mensagem: " + id_msg  );
               return false;
          }
 			  
 	         
	  }*/
	  
	  public String sql_msg(CadastroLogin.Mensagem msg) {
		  
		  return "insert into mensagem (data_mensagem , hora_mensagem, conteudo, id_remetente, id_destinatario  )  values ('"
	    			+ msg.getData()
	    			+ "','"
	    			+ msg.getHora()
	    			+ "','"
	    			+ msg.getConteudo()
	    			+ "','"
	    			+ msg.getId_remetente()
	    			+ "','"
	    			+ msg.getId_destinatario()
	    			+ "')";
		  
	  }
	  
	  
	
}
