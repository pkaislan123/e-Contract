package main.java.conexaoBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroContrato.CadastroPagamentoContratual;
import main.java.cadastros.CadastroDocumento;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroModelo;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroSafra;
import main.java.cadastros.CadastroTarefaGeral;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.outros.DadosGlobais;
import main.java.tratamento_proprio.Log;

public class GerenciarBancoDocumento {

	public int inserir_documento_padrao(CadastroDocumento doc) {
		System.out.println("Inserir Documento foi chamado!");
		int result = -1;
		int id_cliente = -1;

		String sql_cadastro_documento = "insert into documento (nome, descricao, tipo, id_pai, nome_arquivo, id_contrato_pai) values ('"

				+ doc.getNome() + "','" + 
				doc.getDescricao() + "','" +
				doc.getTipo() + "','" +
				doc.getId_pai() + "','" +
				doc.getNome_arquivo() + "','"
				+ doc.getId_contrato_pai() 
				
				+ "')";

		// cria os strings para cadastro o cliente

		try {
			Connection conn = ConexaoBanco.getConexao();
			Statement stmt = (Statement) conn.createStatement();
			int numero = stmt.executeUpdate(sql_cadastro_documento, Statement.RETURN_GENERATED_KEYS);

			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				result = rs.getInt(1);
				System.out.println("Id Cliente inserido: " + result);
			}
			rs.close();
			stmt.close();
			return result;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao inserir o documento no banco de" + "dados\n Erro: "  );
			return -1;
		}

	}
	
	public int inserir_documento_padrao_cliente(CadastroDocumento doc) {
		System.out.println("Inserir Documento foi chamado!");
		int result = -1;
		int id_cliente = -1;

		String sql_cadastro_documento = "insert into documento (nome, descricao, tipo, id_pai, nome_arquivo, id_cliente) values ('"

				+ doc.getNome() + "','" + 
				doc.getDescricao() + "','" +
				doc.getTipo() + "','" +
				doc.getId_pai() + "','" +
				doc.getNome_arquivo() + "','"
				+ doc.getId_cliente()
				
				+ "')";

		// cria os strings para cadastro o cliente

		try {
			Connection conn = ConexaoBanco.getConexao();
			Statement stmt = (Statement) conn.createStatement();
			int numero = stmt.executeUpdate(sql_cadastro_documento, Statement.RETURN_GENERATED_KEYS);

			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				result = rs.getInt(1);
				System.out.println("Id Cliente inserido: " + result);
			}
			rs.close();
			stmt.close();
			return result;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao inserir o documento no banco de" + "dados\n Erro: "  );
			return -1;
		}

	}
	

	
	public int inserirDocumentoLancamento(CadastroDocumento doc) {
		System.out.println("Inserir Documento foi chamado!");
		int result = -1;
		int id_cliente = -1;

		String sql_cadastro_documento = "insert into documento (nome, descricao, tipo, id_pai, nome_arquivo, id_lancamento) values ('"

				+ doc.getNome() + "','" + 
				doc.getDescricao() + "','" +
				doc.getTipo() + "','" +
				doc.getId_pai() + "','" +
				doc.getNome_arquivo() + "','"
				+ doc.getId_lancamento()
				
				+ "')";

		// cria os strings para cadastro o cliente

		try {
			Connection conn = ConexaoBanco.getConexao();
			Statement stmt = (Statement) conn.createStatement();
			int numero = stmt.executeUpdate(sql_cadastro_documento, Statement.RETURN_GENERATED_KEYS);

			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				result = rs.getInt(1);
			}
			//rs.close();
			//stmt.close();
			
			
			
			
			return result;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao inserir o documento no banco de" + "dados\n Erro: "  );
			return -1;
		}

	}
	
	public ArrayList<CadastroDocumento> getDocumentos(int id_contrato){
		Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
  	    String select_documentos = "select * from documento where id_contrato_pai = ?";

        ArrayList<CadastroDocumento> listaDocs = new ArrayList<CadastroDocumento>();
        try {
            conn = ConexaoBanco.getConexao();
            pstm = conn.prepareStatement(select_documentos);
            pstm.setInt(1,  id_contrato);
            rs = pstm.executeQuery();
            while (rs.next()) {
            	CadastroDocumento doc = new CadastroDocumento();
            
            	doc.setId_documento(rs.getInt("id_documento"));

            	doc.setNome(rs.getString("nome"));

            	doc.setDescricao(rs.getString("descricao"));
            	doc.setTipo(rs.getInt("tipo"));
            	doc.setId_pai(rs.getInt("id_pai"));
            	doc.setNome_arquivo(rs.getString("nome_arquivo"));
            	doc.setId_contrato_pai(rs.getInt("id_contrato_pai"));

                
          
            	listaDocs.add(doc);
            }
            ConexaoBanco.fechaConexao(conn, pstm, rs);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar documentos"  );
        }
        return listaDocs;
	}
	
	public ArrayList<CadastroDocumento> getDocumentosPorPai(int id_pai){
		Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
  	    String select_documentos = "select * from documento where id_pai = ?";

        ArrayList<CadastroDocumento> listaDocs = new ArrayList<CadastroDocumento>();
        try {
            conn = ConexaoBanco.getConexao();
            pstm = conn.prepareStatement(select_documentos);
            pstm.setInt(1,  id_pai);
            rs = pstm.executeQuery();
            while (rs.next()) {
            	CadastroDocumento doc = new CadastroDocumento();
            
            	doc.setId_documento(rs.getInt("id_documento"));

            	doc.setNome(rs.getString("nome"));

            	doc.setDescricao(rs.getString("descricao"));
            	doc.setTipo(rs.getInt("tipo"));
            	doc.setId_pai(rs.getInt("id_pai"));
            	doc.setNome_arquivo(rs.getString("nome_arquivo"));
            	doc.setId_contrato_pai(rs.getInt("id_contrato_pai"));

                
          
            	listaDocs.add(doc);
            }
            ConexaoBanco.fechaConexao(conn, pstm, rs);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar documentos anexados ao id_pai: " + id_pai + " "  );
        }
        return listaDocs;
	}
	
	public ArrayList<CadastroDocumento> getDocumentosCliente(int id_cliente){
		Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
  	    String select_documentos = "select * from documento where id_cliente = ?";

        ArrayList<CadastroDocumento> listaDocs = new ArrayList<CadastroDocumento>();
        try {
            conn = ConexaoBanco.getConexao();
            pstm = conn.prepareStatement(select_documentos);
            pstm.setInt(1,  id_cliente);
            rs = pstm.executeQuery();
            while (rs.next()) {
            	CadastroDocumento doc = new CadastroDocumento();
            
            	doc.setId_documento(rs.getInt("id_documento"));

            	doc.setNome(rs.getString("nome"));

            	doc.setDescricao(rs.getString("descricao"));
            	doc.setTipo(rs.getInt("tipo"));
            	doc.setId_pai(rs.getInt("id_pai"));
            	doc.setNome_arquivo(rs.getString("nome_arquivo"));
            	doc.setId_cliente(rs.getInt("id_cliente"));

                
          
            	listaDocs.add(doc);
            }
            ConexaoBanco.fechaConexao(conn, pstm, rs);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar documentos"  );
        }
        return listaDocs;
	}
	
	public ArrayList<CadastroDocumento> getDocumentosLancamento(int id_cliente){
		Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
  	    String select_documentos = "select * from documento where id_lancamento = ?";

        ArrayList<CadastroDocumento> listaDocs = new ArrayList<CadastroDocumento>();
        try {
            conn = ConexaoBanco.getConexao();
            pstm = conn.prepareStatement(select_documentos);
            pstm.setInt(1,  id_cliente);
            rs = pstm.executeQuery();
            while (rs.next()) {
            	CadastroDocumento doc = new CadastroDocumento();
            
            	doc.setId_documento(rs.getInt("id_documento"));

            	doc.setNome(rs.getString("nome"));

            	doc.setDescricao(rs.getString("descricao"));
            	doc.setTipo(rs.getInt("tipo"));
            	doc.setId_pai(rs.getInt("id_pai"));
            	doc.setNome_arquivo(rs.getString("nome_arquivo"));
            	doc.setId_lancamento(rs.getInt("id_lancamento"));

                
          
            	listaDocs.add(doc);
            }
            ConexaoBanco.fechaConexao(conn, pstm, rs);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar documentos"  );
        }
        return listaDocs;
	}
	
	public boolean removerDocumento(int id_documento)
	{
		  String sql_delete_documento = "DELETE FROM documento WHERE id_documento = ?";
		  Connection conn = null;
	        ResultSet rs = null;	      
	        try {
	            conn = ConexaoBanco.getConexao();
	            PreparedStatement pstm;
	            pstm = conn.prepareStatement(sql_delete_documento);
	 
	            pstm.setInt(1, id_documento);
	 
	            pstm.execute();
	            ConexaoBanco.fechaConexao(conn, pstm);
	            JOptionPane.showMessageDialog(null, "Documento Excluido, banco normalizado ");
	           return true;
	            
	 
	        } catch (Exception f) {
	            JOptionPane.showMessageDialog(null, "Erro ao excluir o documento do banco de"
	                    + "dados " + f.getMessage());
	           return false;
	        }
	}
	

}
