

package main.java.conexaoBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.java.cadastros.CadastroFuncionario;
import main.java.cadastros.ContaBancaria;
import main.java.cadastros.Contato;



public class GerenciarBancoFuncionarios {

	
	  public boolean inserir(CadastroFuncionario funcionario) {
    	boolean deletar_contatos = false;
    	boolean deletar_contas = false;
    	boolean deletar_veiculos = false;
    	boolean reverter_operacao = false;
    	boolean retorno = false;

	        if (funcionario != null) {
	        	
	            Connection conn = null;
	                ArrayList<Contato> contatos = new ArrayList<>();
	                contatos = funcionario.getContatos();
	                int id_funcionario = 0;
	                RegistroAdicionarContato adicionar_contatos = null;
	                
	                ArrayList<ContaBancaria> contas_bancarias = new ArrayList<>();
	                contas_bancarias = funcionario.getContas();
	                RegistroAdicionarContaBancaria adicionar_contas = null;
	                
	             	                
	                 try { 
	                	  id_funcionario = inserir_funcionario(funcionario);
	 	                    if( id_funcionario > 0)  {
	                                
	                                 System.out.println("Número de contatos para cadastrar: " + contatos.size());
	                                 System.out.println("Número de contas para cadastrar: " + contas_bancarias.size());

                                if(contas_bancarias.size() > 0 || contatos.size() > 0 ) {
                                	boolean prosseguir = false;
                                	
	                                 if(contatos.size() > 0) {
	                                	adicionar_contatos = adicionarContato(contatos, id_funcionario);	 
	                                 if(adicionar_contatos.isResposta() == true && adicionar_contatos.ids_contatos.size() > 0) {
	                                	//contatos adicionados com sucesso
	                                	 deletar_contatos = false;
	                                	 reverter_operacao = false;
	                                	 prosseguir = true;
	                                	
	                                 } else {
	                                	 System.out.println("Erro ao cadastrar contatos, operação cancelada!");
	                                	 //deletar contatos
	                                	  deletar_contatos = true;
		                                	 prosseguir = false;
		                                	 reverter_operacao = true;

	                                  }
	                                 }else {
	                                	 prosseguir = true;

	                                 }
	                                 

                                	 //adicionar contas bancarias
	                                 if(prosseguir) {
                                	 if(contas_bancarias.size() > 0) {
                                         
	                                    	 adicionar_contas = adicionarContaBancaria(contas_bancarias, id_funcionario);
                                          if(adicionar_contas.isResposta() == true && adicionar_contas.ids_contas.size() > 0) {
                                        	  //contas bancarias adicionadas
                                		     deletar_contas = false;
                                		     reverter_operacao = false;
                                		     prosseguir = true;
		                                  }
		                                  else {
			                                	 System.out.println("Erro ao cadastrar contas bancarias, operação cancelada!");

                                                  deletar_contas = true;
                                                  reverter_operacao = true;
                                                  prosseguir = false;
		                                  }
	                                     
	                                 
                                 }else {
                                	 prosseguir = true;

                                 }
	                            }
	                                	                                 
	                             
	                           }
	                                 
	                                 if(reverter_operacao) {
	                                	 
	                                	 JOptionPane.showMessageDialog(null, "Erro ao inserir o funcionario no banco de dados, revertendo operacao");
	           	                	  if(deletar_contatos)
	           	       	           {
	           	       	        	  
	           	       	        	   //deletar contatos apartir das ids geradas
	           	       	        	  for (int id : adicionar_contatos.ids_contatos )
	           	       	        	  {
	           	       		        	   deleteContato(id, id_funcionario);

	           	       	        	  }
	           	       	           }
	           	       	           if(deletar_contas) {
	           	       	        	   
	           	       	        	   //deletar contas apartir das ids geradas
	           	       	        	  for (int id :  adicionar_contas.ids_contas )
	           	       	        	  {
	           	       		        	   deleteConta(id, id_funcionario);

	           	       	        	  }
	           	       	           }
	           	       	         
	           	       	           
	           	       	           remover_funcionario(id_funcionario);
	           	       	        retorno = false;

	                                	 
	                                 }else {
	                                	 retorno = true;
	                                 }
	                                 
	                                
                               
	                      } else{
	  	                	         //erro ao inserir funcionario
           	                	 JOptionPane.showMessageDialog(null, "Erro ao inserir o funcionario no banco de dados\nBanco Normalizado! ");
	  	                	         retorno = false;
	  	                }
	                    
	                }catch(Exception e)
	                 {
	                	 JOptionPane.showMessageDialog(null, "Erro ao inserir o funcionario no banco de dados ");
	                	  if(deletar_contatos)
	       	           {
	       	        	  
	       	        	   //deletar contatos apartir das ids geradas
	       	        	  for (int id : adicionar_contatos.ids_contatos )
	       	        	  {
	       		        	   deleteContato(id, id_funcionario);

	       	        	  }
	       	           }
	       	           if(deletar_contas) {
	       	        	   
	       	        	   //deletar contas apartir das ids geradas
	       	        	  for (int id :  adicionar_contas.ids_contas )
	       	        	  {
	       		        	   deleteConta(id, id_funcionario);

	       	        	  }
	       	           }
	       	        
	       	           
	       	           remover_funcionario(id_funcionario);
	       	        retorno = false;
	                 }   
			            
	      } else {
	        	 JOptionPane.showMessageDialog(null, "funcionario com dados invalidos");
	        	 retorno = false;
 	        }
			return retorno;
	      
	  
}


public boolean remover_funcionario(int id_funcionario)
{
	  String sql_dele_funcionario = "DELETE FROM funcionario WHERE id_funcionario = ?";
	  Connection conn = null;
        ResultSet rs = null;	      
        try {
            conn = ConexaoBanco.getConexao();
            PreparedStatement pstm;
            pstm = conn.prepareStatement(sql_dele_funcionario);
 
            pstm.setInt(1, id_funcionario);
 
            pstm.execute();
            ConexaoBanco.fechaConexao(conn, pstm);
            JOptionPane.showMessageDialog(null, "funcionario Excluido, banco normalizado ");
           return true;
            
 
        } catch (Exception f) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir o funcionario do banco de"
                    + "dados " + f.getMessage());
           return false;
        }
}
	  
private int inserir_funcionario(CadastroFuncionario funcionario)
{
	     System.out.println("Inserir funcionario foi chamado!");
		   int result = -1;
           int id_funcionario = -1;
           
           String sql_cadastro_funcionario = null;

           //cria os strings para cadastro o funcionario
       	
           
        	  
                 sql_cadastro_funcionario = string_pessoa_fisica(funcionario);
           
         
                try {       
                      Connection conn = ConexaoBanco.getConexao();
                      Statement stmt = (Statement) conn.createStatement();
                      int numero = stmt.executeUpdate(sql_cadastro_funcionario, Statement.RETURN_GENERATED_KEYS);
      
                      ResultSet rs = stmt.getGeneratedKeys();
                      if (rs.next()){
     	                                result=rs.getInt(1);
     	                                System.out.println("Id funcionario inserido: "+ result);
                                    }
                       rs.close();
                       stmt.close();
                       return result;
                     }catch(Exception e) {
                    	  JOptionPane.showMessageDialog(null, "Erro ao inserir funcionario no banco de dados\nErro: " + e.getMessage() + "\nCausa:" + e.getCause());
                                  return -1;
                          }
		  
}
	  
	  public boolean remover_conta(int id_conta)
	  {
		  String sql_delete_contato = "DELETE FROM conta_bancaria_funcionarios WHERE id_conta = ?";
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
		  String sql_delete_contato = "DELETE FROM contato_funcionarios WHERE id_contato = ?";

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
	  
	  public ArrayList<Contato> getContatos(int id_funcionario)
	  {
		  String selectContatos = "select * from contato_funcionarios cf\r\n" + 
          		"left Join funcionario_contato fc\r\n" + 
          		"on fc.id_contato = cf.id_contato\r\n" + 
          		"where fc.id_funcionario = ?";
	        ArrayList<Contato> contatos = new ArrayList<>();

		  Connection conn = null;
	        PreparedStatement pstm = null;
	        ResultSet rs = null;
	    

          try {
	            conn = ConexaoBanco.getConexao();
	            pstm = conn.prepareStatement(selectContatos);
	            pstm.setInt(1, id_funcionario);
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
	            JOptionPane.showMessageDialog(null, "Erro ao listar contatos ");
	        }

          
            return contatos;
		}

	  
	 
	  
	  public ArrayList<ContaBancaria> getContas(int id_funcionario)
	  {
		  String selectContaBancaria = "select * from conta_bancaria_funcionarios co\r\n" + 
		  		"inner Join funcionario_conta cc\r\n" + 
		  		"on cc.id_conta = co.id_conta\r\n" + 
		  		"where cc.id_funcionario = ?";
	        ArrayList<ContaBancaria> contas = new ArrayList<>();

		  Connection conn = null;
	        PreparedStatement pstm = null;
	        ResultSet rs = null;
	    

          try {
	            conn = ConexaoBanco.getConexao();
	            pstm = conn.prepareStatement(selectContaBancaria);
	            pstm.setInt(1, id_funcionario);
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
	            JOptionPane.showMessageDialog(null, "Erro ao listar contas bancarias ");
	        }

          
            return contas;
		}

	  
	  public ArrayList<ContaBancaria> getContas()
	  {
		  String selectContaBancaria = "select * from conta_bancaria_funcionarios";
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
	            JOptionPane.showMessageDialog(null, "Erro ao listar contas bancarias ");
	        }

          
            return contas;
		}
	  
	  public boolean deleteContato(int id_contato, int id_funcionario)
	  {
		  Connection conn = null;
	        PreparedStatement pstm = null;
	        ResultSet rs = null;
		  String sql_delete_relacao = "DELETE FROM funcionario_contato WHERE id_contato = ? and id_funcionario = ?";
      	  conn = null;
	        try {
	            conn = ConexaoBanco.getConexao();
	            pstm = conn.prepareStatement(sql_delete_relacao);
	 
	            pstm.setInt(1, id_contato);
	            pstm.setInt(2, id_funcionario);

	 
	            pstm.execute();
	            ConexaoBanco.fechaConexao(conn, pstm);
	           // JOptionPane.showMessageDialog(null, "Relação funcionario_contato excluido, banco normalizado ");
	           System.out.println("Relação funcionario_contato excluido, banco normalizado ");
	            //deleta o contato
	            
	            String sql_delete_contato = "DELETE FROM contato_funcionarios WHERE id_contato = ?";
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
	            JOptionPane.showMessageDialog(null, "Erro a relação funcionario_contato do banco de"
	                    + "dados " + f.getMessage());
	           return false;
	          
	        }
	  }
	  
	 
	  
	  
	  public boolean deleteConta(int id_conta, int id_funcionario)
	  {
		  
		  System.out.println("Inicio de exclusão de conta bancaria: ID da Conta: " + id_conta + " ID do funcionario: " + id_funcionario);
		  Connection conn = null;
	        PreparedStatement pstm = null;
	        ResultSet rs = null;
		  String sql_delete_relacao = "DELETE FROM funcionario_conta WHERE id_conta = ? and id_funcionario = ?";
    	  conn = null;
	        try {
	            conn = ConexaoBanco.getConexao();
	            pstm = conn.prepareStatement(sql_delete_relacao);
	 
	            pstm.setInt(1, id_conta);
	            pstm.setInt(2, id_funcionario);

	 
	            pstm.execute();
	            ConexaoBanco.fechaConexao(conn, pstm);
		  		  System.out.println("Relação funcionario_conta excluido, banco normalizado");


	            //JOptionPane.showMessageDialog(null, "Relação funcionario_conta excluido, banco normalizado ");
	           
	            //deleta o contato
		  		  System.out.println("Inicio de exclusão de conta bancaria: ID Conta: " + id_conta);


	            String sql_delete_conta = "DELETE FROM conta_bancaria_funcionarios WHERE id_conta = ?";
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
	            JOptionPane.showMessageDialog(null, "Erro a relação funcionario_conta do banco de"
	                    + "dados " + f.getMessage());
	           return false;
	          
	        }
	  }
	  
	  
	  public ArrayList<CadastroFuncionario> getfuncionarios() {
		  String selectfuncionarios;

		 
		  
			  //busca padrao
			    selectfuncionarios = "SELECT * FROM funcionario";

		  
	        Connection conn = null;
	        PreparedStatement pstm = null;
	        ResultSet rs = null;
	        ArrayList<CadastroFuncionario> listafuncionarios = new ArrayList<CadastroFuncionario>();
	        try {
	            conn = ConexaoBanco.getConexao();
	            pstm = conn.prepareStatement(selectfuncionarios);
	           
	            		
	            rs = pstm.executeQuery();
	            while (rs.next()) {
	                CadastroFuncionario funcionario = new CadastroFuncionario();
	 
	                funcionario.setId_funcionario(rs.getInt("id_funcionario"));
	                funcionario.setNome(rs.getString("nome"));
	                funcionario.setSobrenome(rs.getString("sobrenome"));
	                funcionario.setCpf(rs.getString("cpf"));
	                funcionario.setRg(rs.getString("rg"));
	                funcionario.setEstado(rs.getString("estado_pessoa"));
	                funcionario.setNacionalidade(rs.getString("nacionalidade"));
	                funcionario.setNaturalidade(rs.getString("naturalidade"));
	                funcionario.setNascimento(rs.getString("nascimento"));
	                funcionario.setGenero(rs.getString("genero"));
	                funcionario.setEstado_civil(rs.getString("estado_civil"));
	                funcionario.setNome_mae(rs.getString("nome_mae"));
	                funcionario.setNome_pai(rs.getString("nome_pai"));

	                //endereco
	                funcionario.setRua(rs.getString("rua"));
	                funcionario.setBairro(rs.getString("bairro"));
	                funcionario.setCep(rs.getString("cep"));
	                funcionario.setCidade(rs.getString("cidade"));
	                funcionario.setNumero(rs.getString("numero"));
	                funcionario.setEstado_endereco(rs.getString("uf_endereco"));

	                //titulo eleitor
	                funcionario.setTitulo_eleitor(rs.getString("titulo_eleitor"));
	                funcionario.setTitulo_secao(rs.getString("titulo_secao"));
	                funcionario.setTitulo_zona(rs.getString("titulo_zona"));
	                
	               //certidao militar
	                funcionario.setCertida_militar(rs.getString("certida_militar"));
	                funcionario.setCertiao_serie(rs.getString("certiao_serie"));
	                funcionario.setCertidao_categoria(rs.getString("certidao_categoria"));
	                
	                //ctps
	                funcionario.setPis(rs.getString("pis"));
	                funcionario.setNis(rs.getString("nis"));
	                funcionario.setCtps_serie(rs.getString("ctps_serie"));
	                funcionario.setCtps_estado(rs.getString("ctps_estado"));

	              //cnh
	                funcionario.setCnh_categoria(rs.getString("cnh_categoria"));
	                funcionario.setCnh_validade(rs.getString("cnh_validade"));
	                funcionario.setCnh_num_registro(rs.getString("cnh_num_registro"));
	                
	              //escolaridade
	                funcionario.setGrau_escolaridade(rs.getString("grau_escolaridade"));
	                funcionario.setCursos(rs.getString("cursos"));
	                funcionario.setHabilidades(rs.getString("habilidades"));
	             
	                funcionario.setApelido(rs.getString("apelido"));

	                listafuncionarios.add(funcionario);
	                
	            }
	            ConexaoBanco.fechaConexao(conn, pstm, rs);
	        } catch (Exception e) {
	        	  //
	        	  //
	        	  //JOptionPane.showMessageDialog(null, "Erro ao listar funcionarios");
	        }
	        return listafuncionarios;
	
}
	  
	  public CadastroFuncionario getfuncionario(int id_funcionario) {
		  String  selectfuncionarios = "select * from funcionario where id_funcionario = ?";

			 
	        Connection conn = null;
	        PreparedStatement pstm = null;
	        ResultSet rs = null;
	        try {
	            conn = ConexaoBanco.getConexao();
	            pstm = conn.prepareStatement(selectfuncionarios);
	           
	            	pstm.setInt(1, id_funcionario);
	           
	            		
	                rs = pstm.executeQuery();
	               rs.next();
	                CadastroFuncionario funcionario = new CadastroFuncionario();
	 

	           	 
	                funcionario.setId_funcionario(rs.getInt("id_funcionario"));
	                funcionario.setNome(rs.getString("nome"));
	                funcionario.setSobrenome(rs.getString("sobrenome"));
	                funcionario.setCpf(rs.getString("cpf"));
	                funcionario.setRg(rs.getString("rg"));
	                funcionario.setEstado(rs.getString("estado_pessoa"));
	                funcionario.setNacionalidade(rs.getString("nacionalidade"));
	                funcionario.setNaturalidade(rs.getString("naturalidade"));
	                funcionario.setNascimento(rs.getString("nascimento"));
	                funcionario.setGenero(rs.getString("genero"));
	                funcionario.setEstado_civil(rs.getString("estado_civil"));
	                funcionario.setNome_mae(rs.getString("nome_mae"));
	                funcionario.setNome_pai(rs.getString("nome_pai"));

	                //endereco
	                funcionario.setRua(rs.getString("rua"));
	                funcionario.setBairro(rs.getString("bairro"));
	                funcionario.setCep(rs.getString("cep"));
	                funcionario.setCidade(rs.getString("cidade"));
	                funcionario.setNumero(rs.getString("numero"));
	                funcionario.setEstado_endereco(rs.getString("uf_endereco"));

	                //titulo eleitor
	                funcionario.setTitulo_eleitor(rs.getString("titulo_eleitor"));
	                funcionario.setTitulo_secao(rs.getString("titulo_secao"));
	                funcionario.setTitulo_zona(rs.getString("titulo_zona"));
	                
	               //certidao militar
	                funcionario.setCertida_militar(rs.getString("certida_militar"));
	                funcionario.setCertiao_serie(rs.getString("certiao_serie"));
	                funcionario.setCertidao_categoria(rs.getString("certidao_categoria"));
	                
	                //ctps
	                funcionario.setPis(rs.getString("pis"));
	                funcionario.setNis(rs.getString("nis"));
	                funcionario.setCtps_serie(rs.getString("ctps_serie"));
	                funcionario.setCtps_estado(rs.getString("ctps_estado"));

	              //cnh
	                funcionario.setCnh_categoria(rs.getString("cnh_categoria"));
	                funcionario.setCnh_validade(rs.getString("cnh_validade"));
	                funcionario.setCnh_num_registro(rs.getString("cnh_num_registro"));
	                
	              //escolaridade
	                funcionario.setGrau_escolaridade(rs.getString("grau_escolaridade"));
	                funcionario.setCursos(rs.getString("cursos"));
	                funcionario.setHabilidades(rs.getString("habilidades"));
	             
	                funcionario.setApelido(rs.getString("apelido"));
	                

		            ConexaoBanco.fechaConexao(conn, pstm, rs);

	                return funcionario;
	                
	            
	        } catch (Exception e) {
	           // JOptionPane.showMessageDialog(null, "Erro ao listar funcionario(gbc) id: " + id_funcionario );
	            return null;
	        }
	     
	
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
	  
	
	  
	  public RegistroAdicionarContaBancaria adicionarContaBancaria(ArrayList<ContaBancaria> contas, int id_funcionario)
	  {
		  RegistroAdicionarContaBancaria registro = new RegistroAdicionarContaBancaria();
		  boolean resposta = false;
	        Connection conn = null;
	        PreparedStatement pstm = null;
	        ResultSet rs = null;
          ArrayList<Integer> ids_contas = new ArrayList<>();
		  System.out.println("Inicio de adição de conta bancaria: ID do funcionario: " + id_funcionario);

		  for(ContaBancaria conta : contas) {
            String sql_cadastro_conta = "insert into conta_bancaria_funcionarios (cpf, nome, banco, codigo, agencia, conta) values ('"
            		
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
	                JOptionPane.showMessageDialog(null, "Erro ao inserir conta bancaria no banco de dados");
	            }
	  }//fim do for
		    System.out.println("Número de contas para cadastrar: " + ids_contas.size());
          // for(int id_contato : ids_contatos )
           for(int i = 0; i < ids_contas.size(); i++)
           {
     		  System.out.println("Inicio de Adição de Relação Clinte-Conta Bancaria, ID funcionario: " + id_funcionario + " ID Conta: " + ids_contas.get(i));

         	  String sql_cadastro_funcionario_conta = "insert into funcionario_conta (id_funcionario , id_conta ) values ('"
	                		
         		+ id_funcionario
	    			+ "','"
	    			+ ids_contas.get(i)
	    			+ "')";	
         	  
         	  try {
         		 conn = ConexaoBanco.getConexao();
	               
	    	       
	    	        PreparedStatement grava = (PreparedStatement) conn.prepareStatement(sql_cadastro_funcionario_conta); 
	    	        grava.execute();    
	              //  JOptionPane.showMessageDialog(null, "funcionario Conta bancaria cadastrado com sucesso");
	                ConexaoBanco.fechaConexao(conn, grava);
	                resposta = true;
	                registro.setIds_contas(ids_contas);
	            	  registro.setResposta(resposta);
	         		  System.out.println("Relação funcionario-Conta Bancaria Inserida");

	               
	                } catch (Exception e) {
		                JOptionPane.showMessageDialog(null, "Erro ao crir a relação funcionario-Conta Bancaria");
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
	  
	  public RegistroAdicionarContato adicionarContato(ArrayList<Contato> contatos, int id_funcionario)
	  {

		  RegistroAdicionarContato registro = new RegistroAdicionarContato();
		  boolean resposta = false;
	        Connection conn = null;
	        PreparedStatement pstm = null;
	        ResultSet rs = null;
            ArrayList<Integer> ids_contatos = new ArrayList<>();
  		  System.out.println("Inicio de adição de Contato, ID do funcionario: " + id_funcionario);

		  for(Contato contato : contatos) {
	          
              String sql_cdastro_contato = "insert into contato_funcionarios (nome_contato, cargo_contato, celular_contato, fixo_contato, e_mail_contato,descricao_contato, observacao_contato) values ('"
              		
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
	                        + "dados "  );
	            }
	  }//fim do for
		    System.out.println("Número de contatos para cadastrar: " + ids_contatos.size());
            // for(int id_contato : ids_contatos )
             for(int i = 0; i < ids_contatos.size(); i++)
             {
           	  String sql_cadastro_funcionario_contato = "insert into funcionario_contato (id_funcionario , id_contato ) values ('"
	                		
           		+ id_funcionario
	    			+ "','"
	    			+ ids_contatos.get(i)
	    			+ "')";	
           	  
           	  try {
           		 conn = ConexaoBanco.getConexao();
	               
	    	       
	    	        PreparedStatement grava = (PreparedStatement) conn.prepareStatement(sql_cadastro_funcionario_contato); 
	    	        grava.execute();    
	            //    JOptionPane.showMessageDialog(null, "funcionario Contato cadastrado com sucesso");
	                ConexaoBanco.fechaConexao(conn, grava);
	                resposta = true;
	             	registro.setIds_contatos(ids_contatos);
	              	registro.setResposta(resposta);
	               
	                } catch (Exception e) {
		                JOptionPane.showMessageDialog(null, "Erro ao crir a tabela funcionario_contato"
		                        + " "  );
		                resposta = false;
		             	registro.setIds_contatos(ids_contatos);
		              	registro.setResposta(resposta);
		            }
             }
          	  return registro;

	  }


	  public boolean atualizarfuncionario(CadastroFuncionario funcionario) {
	        if (funcionario != null) {
	            Connection conn = null;
	            String atualizar = null;
                PreparedStatement pstm;

	           
	            try {
	        
	            	atualizar =  "update funcionario set nome = ?, sobrenome =?, rg = ?, estado_pessoa = ?, nacionalidade = ?,"
	            			+ " naturalidade = ?, nascimento =?,"
	          		  		+ " genero = ?, estado_civil = ?, nome_mae =?, nome_pai =?, apelido = ?,"
	          		  		+ " rua = ?, bairro = ?, cep = ?, cidade = ?, numero = ?, uf_endereco = ?,"
	          		  		+ " titulo_eleitor = ?, titulo_secao = ?, titulo_zona = ?,"
	          		  		+ " certida_militar = ?, certiao_serie = ?, certidao_categoria = ?,"
	          		  		+ "pis = ?, nis = ?, ctps_serie = ?, ctps_estado = ?,"
	          		  		+ "cnh_categoria = ?, cnh_validade = ?, cnh_num_registro = ?,"
	          		  		+ "grau_escolaridade = ?, cursos = ?, habilidades = ? where id_funcionario = ?";
	            	
	          		  		conn = ConexaoBanco.getConexao();
	            	 pstm = conn.prepareStatement(atualizar);

		             pstm.setString(1, funcionario.getNome());
		             pstm.setString(2, funcionario.getSobrenome());
		             pstm.setString(3, funcionario.getRg());
		             pstm.setString(4, funcionario.getEstado());
		             pstm.setString(5, funcionario.getNacionalidade());
		             pstm.setString(6, funcionario.getNaturalidade());
		             pstm.setString(7, funcionario.getNascimento());
		             pstm.setString(8, funcionario.getGenero());
		             pstm.setString(9, funcionario.getEstado_civil());
		             pstm.setString(10, funcionario.getNome_mae());
		             pstm.setString(11, funcionario.getNome_pai());
		             pstm.setString(12, funcionario.getApelido());
		             pstm.setString(13, funcionario.getRua());
		             pstm.setString(14, funcionario.getBairro());
		             pstm.setString(15, funcionario.getCep());
		             pstm.setString(16, funcionario.getCidade());
		             pstm.setString(17, funcionario.getNumero());
		             pstm.setString(18, funcionario.getEstado_endereco());
		             pstm.setString(19, funcionario.getTitulo_eleitor());
		             pstm.setString(20, funcionario.getTitulo_secao());
		             pstm.setString(21, funcionario.getTitulo_zona());
		             pstm.setString(22, funcionario.getCertida_militar());
		             pstm.setString(23, funcionario.getCertiao_serie());
		             pstm.setString(24, funcionario.getCertidao_categoria());
		             pstm.setString(25, funcionario.getPis());
		             pstm.setString(26, funcionario.getNis());
		             pstm.setString(27, funcionario.getCtps_serie());
		             pstm.setString(28, funcionario.getCtps_estado());
		             pstm.setString(29, funcionario.getCnh_categoria());
		             pstm.setString(30, funcionario.getCnh_validade());
		             pstm.setString(31, funcionario.getCnh_num_registro());

		             pstm.setString(32, funcionario.getGrau_escolaridade());
		             pstm.setString(33, funcionario.getCursos());
		             pstm.setString(34, funcionario.getHabilidades());

		             
		             pstm.setInt(35, funcionario.getId_funcionario());

		             
	             
	                pstm.execute();
	                //JOptionPane.showMessageDialog(null, "funcionario atualizado com sucesso");
	                System.out.println("funcionario Atualizado com sucesso");
	                ConexaoBanco.fechaConexao(conn);
	              return true;
	            } catch (Exception e) {
	                JOptionPane.showMessageDialog(null, "Erro ao atualizar funcionario no banco de"
	                        + "dados "  );
	                return false;
	            }
	        } else {
	            JOptionPane.showMessageDialog(null, "Os dados do funcionario estão vazios");
	            return false;
	        }
	 
	 
	    }
	  
	
	  public String string_pessoa_fisica(CadastroFuncionario funcionario)
	  {
		  return  "insert into funcionario (nome, sobrenome, cpf, rg, estado_pessoa, nacionalidade, naturalidade, nascimento,"
		  		+ " genero, estado_civil, nome_mae, nome_pai, apelido, rua, bairro, cep, cidade, numero, uf_endereco,"
		  		+ " titulo_eleitor, titulo_secao, titulo_zona,"
		  		+ " certida_militar, certiao_serie, certidao_categoria,"
		  		+ "pis, nis, ctps_serie, ctps_estado,"
		  		+ "cnh_categoria, cnh_validade, cnh_num_registro,"
		  		+ "grau_escolaridade, cursos, habilidades     ) values ('"
          		
      		+ funcionario.getNome()
  			+ "','"
  			+ funcionario.getSobrenome()
  			+ "','"
  			+ funcionario.getCpf()
  			+ "','"
  			+ funcionario.getRg()
              + "','"
              + funcionario.getEstado()
              + "','"
               + funcionario.getNacionalidade()
              + "','"
              + funcionario.getNaturalidade()
              + "','"
              + funcionario.getNascimento()
              + "','"
              + funcionario.getGenero()
              + "','"
              + funcionario.getEstado_civil()
              + "','"
              + funcionario.getNome_mae()
              + "','"
              + funcionario.getNome_pai()
              + "','"
              + funcionario.getApelido()
               + "','"
              + funcionario.getRua()
              + "','"
              + funcionario.getBairro()
              + "','"
              + funcionario.getCep()
              + "','"
              + funcionario.getCidade()
              + "','"
              + funcionario.getNumero()
              + "','"
              + funcionario.getEstado_endereco()
              + "','"
              + funcionario.getTitulo_eleitor()
              + "','"
              + funcionario.getTitulo_secao()
              + "','"
              + funcionario.getTitulo_zona()
              + "','"
              + funcionario.getCertida_militar()
              + "','"
              + funcionario.getCertiao_serie()
              + "','"
              + funcionario.getCertidao_categoria()
              + "','"
              + funcionario.getPis()
              + "','"
              + funcionario.getNis()
              + "','"
              + funcionario.getCtps_serie()
              + "','"
              + funcionario.getCtps_estado()
              + "','"
              + funcionario.getCnh_categoria()
              + "','"
              + funcionario.getCnh_validade()
              + "','"
              + funcionario.getCnh_num_registro()
              + "','"
              + funcionario.getGrau_escolaridade()
              + "','"
              + funcionario.getCursos()
              + "','"
              + funcionario.getHabilidades()
  			+ "')";		 
		  
	  }
	  
	 
	  
	  public ContaBancaria getConta(int id_conta)
	  {
		  String selectContaBancaria = "select * from conta_bancaria_funcionarios where id_conta = ?";

		  Connection conn = null;
	        PreparedStatement pstm = null;
	        ResultSet rs = null;
	    

          try {
	            conn = ConexaoBanco.getConexao();
	            pstm = conn.prepareStatement(selectContaBancaria);
	            pstm.setInt(1,  id_conta);
	            rs = pstm.executeQuery();
       
	             rs.next();
                 ContaBancaria conta = new ContaBancaria();
                     
                 conta.setId_conta(rs.getInt("id_conta"));
                 conta.setNome(rs.getString("nome"));
                 conta.setCpf_titular(rs.getString("cpf"));
                 conta.setBanco(rs.getString("banco"));
                 conta.setCodigo(rs.getString("codigo"));
                 conta.setAgencia(rs.getString("agencia"));
                 conta.setConta(rs.getString("conta"));
                 
                return conta;
                
          
          }   catch (Exception e) {
	            JOptionPane.showMessageDialog(null, "Erro ao listar conta bancaria id: " + id_conta + "erro: "  );
	            return null;
	        }

		}
	  


	
	  
}
	

