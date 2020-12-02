package conexaoBanco;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class ConexaoBanco {

	
	//public static Connection connection;
	static String url = "jdbc:mysql://localhost/filamentos?useTimezone=true&serverTimezone=UTC";
	static String usuario = "root";
	static String senha = "1234";
	private String texto;

 public static Connection getConexao() throws SQLException, ClassNotFoundException{
	try {
		
		//base nuvem
		//Connection connection =  (Connection) DriverManager.getConnection("jdbc:mysql://0.tcp.ngrok.io:12003/bd_titaniwm?useTimezone=true&serverTimezone=UTC", "root", "1234");		
		
		//base raspberry
		Connection connection =  (Connection) DriverManager.getConnection("jdbc:mysql://DESKTOP-K8RASOB/bd_ldarmazens?useTimezone=true&serverTimezone=UTC", "root", "1234");		
        
		//base local
		//Connection connection =  (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/bd_ldarmazens?useTimezone=true&serverTimezone=UTC", usuario, senha);		

	 return connection;
	 
 }catch (SQLException e) {
        throw new SQLException("Erro ao conectar "
                + "com a base de dados" + 
                e.getMessage());
    }
	
 }
 
 public static void fechaConexao(Connection conn)
 {
	 try {
         if (conn != null) {
             conn.close();
             System.out.println("Fechada a conexao com o banco de dados");
         }

     } catch (Exception e) {
         System.out.println("Nao foi possivel fechar  a conexao com o banco de dados " + e.getMessage());
     }
 }
 
 public static void fechaConexao(Connection conn, 
		    PreparedStatement stmt) {
		 
		        try {
		            if (conn != null) {
		                fechaConexao(conn);
		            }
		            if (stmt != null) {
		                stmt.close();
		                System.out.println("Statement fechado com sucesso");
		            }
		 
		 
		        } catch (Exception e) {
		            System.out.println("nao foi possivel fechar o statement " + e.getMessage());
		        }
		    }
		 
		    public static void fechaConexao(Connection conn, 
		    PreparedStatement stmt, ResultSet rs) {
		 
		        try {
		            if (conn != null || stmt != null) {
		                fechaConexao(conn, stmt);
		            }
		            if (rs != null) {
		                rs.close();
		                System.out.println("ResultSet fechado com sucesso");
		            }
		 
		 
		        } catch (Exception e) {
		            System.out.println("nao foi possivel fechar o ResultSet " + e.getMessage());
		        }
		    }
		
	
 
	public  ConexaoBanco() {
	
		
		
		
		
		
	}
	

	
	
  
	
	
}
