package conexaoBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import cadastros.CadastroLogin;
import outros.DadosGlobais;
import tratamento_proprio.Log;

public class GerenciarBancoLogin {

	private Log GerenciadorLog;
	private CadastroLogin loginGlobal;
	private String selectUsuarios = "select * from usuarios";
	
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

	            Login.setLogin(rs.getString("login"));
	            Login.setEmail(rs.getString("email"));
	            Login.setSenha(rs.getString("senha"));
	            Login.setSenhaEmail(rs.getString("senha_email"));
	            Login.setGenero(rs.getString("genero"));
	            Login.setDireitos(rs.getInt("direitos"));
	           
                
                listaUsuarios.add(Login);
            }
            ConexaoBanco.fechaConexao(conn, pstm, rs);
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, "Erro ao listar usuarios" + e.getMessage());
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
 String busca = "select * from usuarios where login = ?";
     
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

	            Login.setLogin(rs.getString("login"));
	            Login.setEmail(rs.getString("email"));
	            Login.setSenha(rs.getString("senha"));
	            Login.setSenhaEmail(rs.getString("senha_email"));
	            Login.setGenero(rs.getString("genero"));
	            Login.setDireitos(rs.getInt("direitos"));
	            
	            ConexaoBanco.fechaConexao(conn, pstm, rs);
	        } catch (Exception e) {
	           // JOptionPane.showMessageDialog(null, "Erro ao buscar login ="+login+" no bando de dados" + e.getMessage());
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

	            Login.setLogin(rs.getString("login"));
	            Login.setEmail(rs.getString("email"));
	            Login.setSenha(rs.getString("senha"));
	            Login.setSenhaEmail(rs.getString("senha_email"));
	            Login.setGenero(rs.getString("genero"));
	            Login.setDireitos(rs.getInt("direitos"));
	           
	            
	            ConexaoBanco.fechaConexao(conn, pstm, rs);
	        } catch (Exception e) {
	            //JOptionPane.showMessageDialog(null, "Erro ao buscar id ="+id+" no bando de dados" + e.getMessage());
	        }
	        return Login;
		 
		
	 }
	 
	 
	 
	  public int inserir(CadastroLogin login) {
		  
		   CadastroLogin busca = buscaLogin(login.getLogin());
		   boolean ja_existe = false;
		   
		   try {
            if(busca != null)
			 ja_existe = busca.getLogin().equals(login.getLogin());
		   }catch( Exception y) {
		     ja_existe = false;
		   }
		   
		   
			if(busca == null || ja_existe == false) { 
		  if( login != null) {
	            Connection conn = null;
	            try {
	                conn = ConexaoBanco.getConexao();
	                String sql = "insert into usuarios\r\n" + 
	                		"(nome, sobrenome, celular, cargo, login, email, senha, senha_email, genero, direitos) values ('"
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
	    	    			+ login.getDireitos()
	    	    			+ "')";
	                
	          
					
	    	       
	    	        PreparedStatement grava = (PreparedStatement) conn.prepareStatement(sql); 
	    	        grava.execute();    
	                ConexaoBanco.fechaConexao(conn, grava);
	                return 1;
	 
	            } catch (Exception e) {
	                //JOptionPane.showMessageDialog(null, "Erro ao inserir a dado no banco de"
	                  //      + "dados " + e.getMessage());
	            	GerenciadorLog.registrarLogDiario("falha", "erro ao inserir dados no banco de dados: " + e.getMessage());
	                return 0;
	            }
	        } else {
	            System.out.println("O dado  enviado por parametro esta vazia");
	            return 0;
	        }
			}else {
				return -1;
			}
	  }
	
	  public void getDadosGlobais() {
			//gerenciador de log
			DadosGlobais dados = DadosGlobais.getInstance();
			 GerenciadorLog = dados.getGerenciadorLog();
			 
			 //usuario logado
			 loginGlobal = dados.getLogin();
		}
	
}
