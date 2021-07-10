package main.java.conexaoBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.java.cadastros.CadastroFuncionario;
import main.java.cadastros.CadastroFuncionarioCalculo;
import main.java.cadastros.CadastroFuncionarioCalculo;
import main.java.cadastros.CalculoCompleto;


public class GerenciarBancoFuncionariosCalculos {
/*

CREATE TABLE `tabela_auxiliar_calculo` (
  `id_calculo` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100),
  `descricao` text DEFAULT NULL,
  `referencia_calculo` int(3) DEFAULT NULL,
  `quantidade` int(5) DEFAULT NULL,
  `referencia_valor` int(3) DEFAULT NULL,
  `valor` double DEFAULT NULL,
`total` double DEFAULT NULL,

  PRIMARY KEY (`id_calculo`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8

 */
	

	public String sql_calculo(CadastroFuncionarioCalculo calculo) {
		return "insert into tabela_auxiliar_calculo (tipo_calculo, nome, descricao, referencia_calculo, quantidade,referencia_valor,valor, total) values ('"
				+ calculo.getTipo_calculo() + "','"
				+ calculo.getNome() + "','"
				+ calculo.getDescricao() + "','"
				+ calculo.getReferencia_calculo() + "','"
				+ calculo.getQuantidade() + "','"
				+ calculo.getReferencia_valor() + "','"
			    + calculo.getValor() + "','"
				+ calculo.getTotal() + "')";
	}
	
	public int inserircalculo(CadastroFuncionarioCalculo calculo) {
		int result = -1;
		int id_cliente = -1;
		if (calculo != null) {
			Connection conn = null;
			try {

				conn = ConexaoBanco.getConexao();

				String query = sql_calculo(calculo);
				Statement stmt = (Statement) conn.createStatement();
				int numero = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					result = rs.getInt(1);
					System.out.println("Id calculo inserido: " + result);
				}
				rs.close();
				stmt.close();

				return result;

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Erro ao inserir o calculo no banco de dados");
				
				return -1;
			}
		} else {
			System.out.println("O calculo enviado por parametro esta vazio");
			return -1;
		}
	}
	
	
	public ArrayList<CadastroFuncionarioCalculo> getcalculos() {
		String selectAdivitos = "select * from tabela_auxiliar_calculo";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroFuncionarioCalculo> lista_calculos = new ArrayList<CadastroFuncionarioCalculo>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectAdivitos);
			// pstm.setString(1, chave);

			rs = pstm.executeQuery();
			while (rs.next()) {
				CadastroFuncionarioCalculo calculo = new CadastroFuncionarioCalculo();
			
				calculo.setTipo_calculo(rs.getInt("tipo_calculo"));
				calculo.setId_calculo(rs.getInt("id_calculo"));
				calculo.setNome(rs.getString("nome"));
				calculo.setDescricao(rs.getString("descricao"));
				calculo.setReferencia_calculo(rs.getInt("referencia_calculo"));
				calculo.setQuantidade(rs.getInt("quantidade"));
				calculo.setReferencia_valor(rs.getInt("referencia_valor"));

				calculo.setValor(rs.getDouble("valor"));
				calculo.setTotal(rs.getDouble("total"));
				
				lista_calculos.add(calculo);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar calculos");//  );
		}
		return lista_calculos;

	}
	
	
	public ArrayList<CadastroFuncionarioCalculo> getCalculosAcrescimo() {
		String selectAdivitos = "select * from tabela_auxiliar_calculo where tipo_calculo = 1";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroFuncionarioCalculo> lista_calculos = new ArrayList<CadastroFuncionarioCalculo>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectAdivitos);
			// pstm.setString(1, chave);

			rs = pstm.executeQuery();
			while (rs.next()) {
				CadastroFuncionarioCalculo calculo = new CadastroFuncionarioCalculo();
			
				calculo.setTipo_calculo(rs.getInt("tipo_calculo"));
				calculo.setId_calculo(rs.getInt("id_calculo"));
				calculo.setNome(rs.getString("nome"));
				calculo.setDescricao(rs.getString("descricao"));
				calculo.setReferencia_calculo(rs.getInt("referencia_calculo"));
				calculo.setQuantidade(rs.getInt("quantidade"));
				calculo.setReferencia_valor(rs.getInt("referencia_valor"));

				calculo.setValor(rs.getDouble("valor"));
				calculo.setTotal(rs.getDouble("total"));
				
				lista_calculos.add(calculo);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar calculos");//  );
		}
		return lista_calculos;

	}
	
	public ArrayList<CadastroFuncionarioCalculo> getCalculosDesconto() {
		String selectAdivitos = "select * from tabela_auxiliar_calculo where tipo_calculo = 0";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroFuncionarioCalculo> lista_calculos = new ArrayList<CadastroFuncionarioCalculo>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectAdivitos);
			// pstm.setString(1, chave);

			rs = pstm.executeQuery();
			while (rs.next()) {
				CadastroFuncionarioCalculo calculo = new CadastroFuncionarioCalculo();
			
				calculo.setTipo_calculo(rs.getInt("tipo_calculo"));
				calculo.setId_calculo(rs.getInt("id_calculo"));
				calculo.setNome(rs.getString("nome"));
				calculo.setDescricao(rs.getString("descricao"));
				calculo.setReferencia_calculo(rs.getInt("referencia_calculo"));
				calculo.setQuantidade(rs.getInt("quantidade"));
				calculo.setReferencia_valor(rs.getInt("referencia_valor"));

				calculo.setValor(rs.getDouble("valor"));
				calculo.setTotal(rs.getDouble("total"));
				
				lista_calculos.add(calculo);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar calculos");//  );
		}
		return lista_calculos;

	}
	
	
	
	
	public CadastroFuncionarioCalculo getcalculo(int id) {

		String selectcalculo = "select * from tabela_auxiliar_calculo where id_calculo = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		CadastroFuncionarioCalculo calculo = new CadastroFuncionarioCalculo();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectcalculo);
			pstm.setInt(1, id);

			rs = pstm.executeQuery();
			rs.next();

			calculo.setTipo_calculo(rs.getInt("tipo_calculo"));
			calculo.setId_calculo(rs.getInt("id_calculo"));
			calculo.setNome(rs.getString("nome"));
			calculo.setDescricao(rs.getString("descricao"));
			calculo.setReferencia_calculo(rs.getInt("referencia_calculo"));
			calculo.setQuantidade(rs.getInt("quantidade"));
			calculo.setReferencia_valor(rs.getInt("referencia_valor"));

			calculo.setValor(rs.getDouble("valor"));
			calculo.setTotal(rs.getDouble("total"));
		    return calculo;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar o calculo id: " + id );// );
			System.out.println(
					"Erro ao listar calculo id: " + id + " erro: "   + "\ncausa: "  );
			return null;
		}

	}
	
	
	public boolean removercalculo( int id_calculo) {
		String sql_delete_calculo = "DELETE FROM tabela_auxiliar_calculo WHERE id_calculo = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_delete_calculo);

			pstm.setInt(1, id_calculo);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			JOptionPane.showMessageDialog(null, "calculo excluido, banco normalizado ");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao exlcuir o calculo do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
							+ "dados " + f.getMessage());
			return false;
		}

	}
	
	
	public boolean removerRelacaoContratoDesconto(CalculoCompleto desc) {
		String sql_delete_calculo = "DELETE FROM funcionario_contrato_trabalho_calculos WHERE id_contrato = ? and id_calculo = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_delete_calculo);

			pstm.setInt(1, desc.getId_contrato_trabalho());
			pstm.setInt(2, desc.getId_calculo());

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			JOptionPane.showMessageDialog(null, "calculo excluido, banco normalizado ");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao exlcuir o calculo do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
							+ "dados " + f.getMessage());
			return false;
		}

	}
	

	  public boolean atualizarDesconto(CadastroFuncionarioCalculo calculo) {
	        if (calculo != null) {
	            Connection conn = null;
	            String atualizar = null;
              PreparedStatement pstm;

	           
	            try {
	        
	            	atualizar =  "update tabela_auxiliar_calculo set nome = ?,  descricao = ?, referencia_calculo = ?, quantidade = ?,"
	            			+ "referencia_valor = ?, valor = ? , total = ? where id_calculo = ?";
	            	
	          		  		conn = ConexaoBanco.getConexao();
	            	 pstm = conn.prepareStatement(atualizar);

		             pstm.setString(1, calculo.getNome());
		             pstm.setString(2, calculo.getDescricao());
		             pstm.setInt(3, calculo.getReferencia_calculo());
		             pstm.setInt(4, calculo.getQuantidade());
		             pstm.setInt(5, calculo.getReferencia_valor());
		             pstm.setDouble(6, calculo.getValor());
		             pstm.setDouble(7, calculo.getTotal());

		             pstm.setInt(8, calculo.getId_calculo());
		          
		             
	             
	                pstm.execute();
	                //JOptionPane.showMessageDialog(null, "funcionario atualizado com sucesso");
	                System.out.println("Desconto Atualizado com sucesso");
	                ConexaoBanco.fechaConexao(conn);
	              return true;
	            } catch (Exception e) {
	                JOptionPane.showMessageDialog(null, "Erro ao atualizar o calculo no banco de"
	                        + "dados "  );
	                return false;
	            }
	        } else {
	            JOptionPane.showMessageDialog(null, "Os dados do calculo estão vazios");
	            return false;
	        }
	 
	 
	    }

	
	
	  public String sql_relacao_contrato_calculo(CalculoCompleto desc) {
			return "insert into funcionario_contrato_trabalho_calculos (id_contrato, id_calculo) values ('"
					+ desc.getId_contrato_trabalho() + "','"
					+ desc.getId_calculo()  + "')";
		}
		
		public boolean inserirRelacaoContratoDesconto(CalculoCompleto desc) {
			if (desc != null) {
				try {

					   Connection conn = null;
		              PreparedStatement pstm;
        		  		conn = ConexaoBanco.getConexao();

					String query = sql_relacao_contrato_calculo(desc);
					Statement stmt = (Statement) conn.createStatement();

	            	 pstm = conn.prepareStatement(query);

	                pstm.execute();

					
	                ConexaoBanco.fechaConexao(conn);

					return true;

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null,
							"Erro ao inserir a relacao contrato calculo no banco de dados, erro: " + e.getMessage() + "\nCausa: " + e.getCause());
					
					return false;
				}
			} else {
				System.out.println("O calculo enviado por parametro esta vazio");
				return false;
			}
		}
		
		
		public ArrayList<CalculoCompleto> getcalculosPorContratoTrabalho(int id_contrato_trabalho) {
			String selectAdivitos = "select * from tabela_auxiliar_calculo\r\n"
					+ "left join funcionario_contrato_trabalho_calculos fctd on fctd.id_calculo = tabela_auxiliar_calculo.id_calculo\r\n"
					+ "where fctd.id_contrato = ?";
			Connection conn = null;
			PreparedStatement pstm = null;
			ResultSet rs = null;
			ArrayList<CalculoCompleto> lista_calculos = new ArrayList<CalculoCompleto>();
			try {
				conn = ConexaoBanco.getConexao();
				pstm = conn.prepareStatement(selectAdivitos);
				pstm.setInt(1, id_contrato_trabalho);

				rs = pstm.executeQuery();
				while (rs.next()) {
					CalculoCompleto calculo = new CalculoCompleto();
				

					calculo.setTipo_calculo(rs.getInt("tipo_calculo"));
					calculo.setId_calculo(rs.getInt("id_calculo"));
					calculo.setNome(rs.getString("nome"));
					calculo.setDescricao(rs.getString("descricao"));
					calculo.setReferencia_calculo(rs.getInt("referencia_calculo"));
					calculo.setQuantidade(rs.getInt("quantidade"));
					calculo.setReferencia_valor(rs.getInt("referencia_valor"));

					calculo.setValor(rs.getDouble("valor"));
					calculo.setTotal(rs.getDouble("total"));
					calculo.setId_contrato_trabalho(id_contrato_trabalho);
					
					lista_calculos.add(calculo);

				}
				ConexaoBanco.fechaConexao(conn, pstm, rs);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao listar calculos");//  );
			}
			return lista_calculos;

		}
		
		

}
