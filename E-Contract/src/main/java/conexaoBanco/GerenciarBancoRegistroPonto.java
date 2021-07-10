
package main.java.conexaoBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.java.cadastros.CadastroProduto;
import main.java.cadastros.RegistroPonto;
import main.java.cadastros.RegistroPontoDiario;
import main.java.cadastros.RegistroPontoDiarioCompleto;
import main.java.cadastros.FinanceiroConta;
import main.java.cadastros.Lancamento;
import main.java.cadastros.RegistroPonto;


public class GerenciarBancoRegistroPonto {

	
		
	/*
	CREATE TABLE `registro_ponto` (
  `id_registro_ponto` int(100) NOT NULL AUTO_INCREMENT,
  `id_colaborador` int(11) DEFAULT NULL,
  `data` varchar(100) DEFAULT NULL,
  `hora` varchar(100) DEFAULT NULL,
  `movimentacao` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id_registro_ponto`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8
	 */
	
	public int inserir_registro_ponto(RegistroPonto registro_ponto) {
		int result = -1;
		int id_cliente = -1;
		if (registro_ponto != null) {
			Connection conn = null;
			try {

				conn = ConexaoBanco.getConexao();

				String query = sql_rp(registro_ponto);
				Statement stmt = (Statement) conn.createStatement();
				int numero = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					result = rs.getInt(1);
					System.out.println("Id do RP inserido: " + result);
				}
				rs.close();
				stmt.close();

				return result;

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao inserir o Registro de Ponto no banco de dados, Erro: " + e.getMessage() + "\nCausa: " + e.getCause()) ;

				return -1;
			}
		} else {
			System.out.println("O parametro enviado por parametro esta vazio");
			return -1;
		}
	}
	
	
	public String sql_rp(RegistroPonto registro_ponto) {
		return "insert into registro_ponto\r\n" + 
         		"(id_colaborador , data, hora, movimentacao, motivo) values ('"
	    			+ registro_ponto.getId_funcionario()
	    			+ "','"
	    			+ registro_ponto.getData()
	    			+ "','"
	    			+ registro_ponto.getHora()
	    			+ "','"
	    			+ registro_ponto.getMovimentacao()
	    			+ "','"
	    			+ registro_ponto.getMotivo()
	    			+ "')";
	}
	
		/*  public int inserir_registro_ponto(RegistroPonto registro_ponto) {
			  if( registro_ponto != null) {
		            Connection conn = null;
		            try {
		                conn = ConexaoBanco.getConexao();
		                String sql = "insert into registro_ponto\r\n" + 
		                		"(id_colaborador , data, hora, movimentacao) values ('"
		    	    			+ registro_ponto.getId_funcionario()
		    	    			+ "','"
		    	    			+ registro_ponto.getData()
		    	    			+ "','"
		    	    			+ registro_ponto.getHora()
		    	    			+ "','"
		    	    			+ registro_ponto.getMovimentacao()
		    	    			+ "')";
		    	       
		    	        PreparedStatement grava = (PreparedStatement) conn.prepareStatement(sql); 
		    	        grava.execute();    
		                JOptionPane.showMessageDialog(null, "Registro Ponto Cadastrado");
		                ConexaoBanco.fechaConexao(conn, grava);
		                return 1;
		 
		            } catch (Exception e) {
		            	  //JOptionPane.showMessageDialog(null, "Erro ao inserir a safra no banco de"
		                 //         + "dados "  );
		                return 0;
		            }
		        } else {
		            System.out.println("Dados enviado por parametro esta vazia");
		            return 0;
		        }
		  }*/

		  
		  public ArrayList<RegistroPonto> getRegistrosPontos() {
				
			  
		        Connection conn = null;
		        PreparedStatement pstm = null;
		        ResultSet rs = null;
		        ArrayList<RegistroPonto> listaSalarioMinimos = new ArrayList<RegistroPonto>();
		        try {
		            conn = ConexaoBanco.getConexao();
		            pstm = conn.prepareStatement("select * from registro_ponto");
		            rs = pstm.executeQuery();
		            while (rs.next()) {
		            	RegistroPonto registro_ponto = new RegistroPonto();
		            	registro_ponto.setId_registro_ponto(rs.getInt("id_registro_ponto"));
		            	registro_ponto.setId_funcionario(rs.getInt("id_colaborador"));
		            	registro_ponto.setData(rs.getString("data"));
		            	registro_ponto.setHora(rs.getString("hora"));
		            	registro_ponto.setMovimentacao(rs.getInt("movimentacao"));
		            	registro_ponto.setMotivo(rs.getString("motivo"));

		          
		            	listaSalarioMinimos.add(registro_ponto);
		            }
		            ConexaoBanco.fechaConexao(conn, pstm, rs);
		        } catch (Exception e) {
		        	  // JOptionPane.showMessageDialog(null, "Erro ao listar safras"  );
		        }
		        return listaSalarioMinimos;
		    }
		  
		  
		  
		  public RegistroPonto getRegistroPonto(int id) {
				 String selectSalarioMinimo = "select * from registro_ponto where id_registro_ponto = ?";

			   Connection conn = null;
		        PreparedStatement pstm = null;
		        ResultSet rs = null;	        
		        RegistroPonto registro_ponto = new RegistroPonto();

		        try {
		            conn = ConexaoBanco.getConexao();
		            pstm = conn.prepareStatement(selectSalarioMinimo);
		            pstm.setInt(1, id);
		            rs = pstm.executeQuery();
		            rs.next();

	            	registro_ponto.setId_registro_ponto(rs.getInt("id_registro_ponto"));
	            	registro_ponto.setId_funcionario(rs.getInt("id_colaborador"));
	            	registro_ponto.setData(rs.getString("data"));
	            	registro_ponto.setHora(rs.getString("hora"));
	            	registro_ponto.setMovimentacao(rs.getInt("movimentacao"));
	            	registro_ponto.setMotivo(rs.getString("motivo"));

		            	
	            	return registro_ponto;
			          
		            
		        } catch (Exception e) {
		        	  //    JOptionPane.showMessageDialog(null, "Erro ao buscar por safra id: " + id + " erro: "  );
		            return null;
		        }
			  
		  }

	
	
public boolean atualizarRegistroPonto(RegistroPonto registro_ponto) {
	if (registro_ponto != null) {
		try {
			Connection conn = null;
			String atualizar = null;
			PreparedStatement pstm;

			atualizar = "update registro_ponto set id_colaborador = ?, data = ?, hora = ?,  movimentacao = ? , motivo = ? where id_registro_ponto = ? ";
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(atualizar);

			pstm.setInt(1, registro_ponto.getId_funcionario());
			pstm.setString(2, registro_ponto.getData());
			pstm.setString(3, registro_ponto.getHora());
			pstm.setInt(4, registro_ponto.getMovimentacao());
			pstm.setString(5, registro_ponto.getMotivo());

			pstm.setInt(6, registro_ponto.getId_registro_ponto());

			pstm.execute();
			// JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
			System.out.println("Registro Ponto Atualizada com sucesso");
			ConexaoBanco.fechaConexao(conn);
			return true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao atualizar o Registro Ponto no banco de dados\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
			return false;
		}
	} else {
		JOptionPane.showMessageDialog(null, "Os parametros estão vazios");
		return false;
	}
}

public boolean removerRegistroPonto(int id) {
	String delete = "DELETE FROM registro_ponto WHERE id_registro_ponto = ?";
	Connection conn = null;
	ResultSet rs = null;
	try {
		conn = ConexaoBanco.getConexao();
		PreparedStatement pstm;
		pstm = conn.prepareStatement(delete);

		pstm.setInt(1, id);

		pstm.execute();
		ConexaoBanco.fechaConexao(conn, pstm);
		JOptionPane.showMessageDialog(null, "Registro Ponto Excluído, banco normalizado ");
		return true;

	} catch (Exception f) {
		JOptionPane.showMessageDialog(null,
				"Erro ao excluir o registro_ponto do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
						+ "dados " + f.getMessage());
		return false;
	}

}

public ArrayList<RegistroPonto> getRegistrosPontosPorColaborador(int id) {
	
	  
    Connection conn = null;
    PreparedStatement pstm = null;
    ResultSet rs = null;
    ArrayList<RegistroPonto> listaRp = new ArrayList<RegistroPonto>();
    try {
        conn = ConexaoBanco.getConexao();
        pstm = conn.prepareStatement("select * from registro_ponto where id_colaborador = ?");
        pstm.setInt(1, id);
        
        rs = pstm.executeQuery();
        while (rs.next()) {
        	RegistroPonto registro_ponto = new RegistroPonto();
        	registro_ponto.setId_registro_ponto(rs.getInt("id_registro_ponto"));
        	registro_ponto.setId_funcionario(rs.getInt("id_colaborador"));
        	registro_ponto.setData(rs.getString("data"));
        	registro_ponto.setHora(rs.getString("hora"));
        	registro_ponto.setMovimentacao(rs.getInt("movimentacao"));
        	registro_ponto.setMotivo(rs.getString("motivo"));

      
        	listaRp.add(registro_ponto);
        }
        ConexaoBanco.fechaConexao(conn, pstm, rs);
    } catch (Exception e) {
    	  // JOptionPane.showMessageDialog(null, "Erro ao listar safras"  );
    }
    return listaRp;
}



public ArrayList<RegistroPontoDiario> getDemonstrativoFuncionarioMes(int id_func, int mes, int ano) {
	
	  
    Connection conn = null;
    PreparedStatement pstm = null;
    ResultSet rs = null;
    ArrayList<RegistroPontoDiario> listaRp = new ArrayList<>();
    try {
        conn = ConexaoBanco.getConexao();
        pstm = conn.prepareStatement("call busca_demonstrativo_rp(?, ?, ?)");
        pstm.setInt(1, id_func);
        pstm.setInt(2, mes);
        pstm.setInt(3, ano);

        rs = pstm.executeQuery();
        while (rs.next()) {
        	RegistroPontoDiario registro_ponto = new RegistroPontoDiario();
        	registro_ponto.setData(rs.getString("data"));
        	registro_ponto.setEntrada1(rs.getString("entrada1"));
        	registro_ponto.setSaida1(rs.getString("saida1"));

        	registro_ponto.setEntrada2(rs.getString("entrada2"));
        	registro_ponto.setSaida2(rs.getString("saida2"));
        	
        	registro_ponto.setEntrada3(rs.getString("entrada3"));
        	registro_ponto.setSaida3(rs.getString("saida3"));

      
        	listaRp.add(registro_ponto);
        }
        ConexaoBanco.fechaConexao(conn, pstm, rs);
    } catch (Exception e) {
    	  // JOptionPane.showMessageDialog(null, "Erro ao listar safras"  );
    }
    return listaRp;
}


public ArrayList<RegistroPontoDiario> getDemonstrativoFuncionarioMesCompleto(int id_func, int dia_final, int mes, int ano) {
	
	  
    Connection conn = null;
    PreparedStatement pstm = null;
    ResultSet rs = null;
    ArrayList<RegistroPontoDiario> listaRp = new ArrayList<>();
    try {
        conn = ConexaoBanco.getConexao();
        pstm = conn.prepareStatement("call busca_demostrativo_rp_mensal(?, ?, ?, ?)");
        pstm.setInt(1, id_func);
        pstm.setInt(2, dia_final);
        pstm.setInt(3, mes);
        pstm.setInt(4, ano);

        rs = pstm.executeQuery();
        while (rs.next()) {
        	RegistroPontoDiario registro_ponto = new RegistroPontoDiario();
        	registro_ponto.setData(rs.getString("data_rp"));
        	registro_ponto.setEntrada1(rs.getString("entrada1"));
        	registro_ponto.setSaida1(rs.getString("saida1"));

        	registro_ponto.setEntrada2(rs.getString("entrada2"));
        	registro_ponto.setSaida2(rs.getString("saida2"));
        	
        	registro_ponto.setEntrada3(rs.getString("entrada3"));
        	registro_ponto.setSaida3(rs.getString("saida3"));

      
        	listaRp.add(registro_ponto);
        }
        ConexaoBanco.fechaConexao(conn, pstm, rs);
    } catch (Exception e) {
    	  JOptionPane.showMessageDialog(null, "Erro ao listar dados de registro de ponto mensal, erro: " + e.getMessage() + "\nCausa: " + e.getCause()  );
    }
    return listaRp;
}



public RegistroPontoDiarioCompleto getDemonstrativoFuncionarioData(int id_func, String data) {
	
	  
    Connection conn = null;
    PreparedStatement pstm = null;
    ResultSet rs = null;
	RegistroPontoDiarioCompleto registro_ponto = new RegistroPontoDiarioCompleto();

    try {
        conn = ConexaoBanco.getConexao();
        pstm = conn.prepareStatement("call busca_demonstrativo_rp_diario(?, ?)");
        pstm.setInt(1, id_func);
        pstm.setString(2, data);

        rs = pstm.executeQuery();
        rs.next();
        	registro_ponto.setData(rs.getString("data"));
        	registro_ponto.setEntrada1(rs.getString("entrada1"));
        	registro_ponto.setSaida1(rs.getString("saida1"));

        	registro_ponto.setEntrada2(rs.getString("entrada2"));
        	registro_ponto.setSaida2(rs.getString("saida2"));
        	
        	registro_ponto.setEntrada3(rs.getString("entrada3"));
        	registro_ponto.setSaida3(rs.getString("saida3"));
        	registro_ponto.setNome_colaborador(rs.getString("nome"));
        	
         
      
            ConexaoBanco.fechaConexao(conn, pstm, rs);

        	return registro_ponto;
        
    } catch (Exception e) {
    	  // JOptionPane.showMessageDialog(null, "Erro ao listar safras"  );
    	return null;
    }
}


}


