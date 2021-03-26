package conexaoBanco;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import cadastros.CadastroCliente;
import cadastros.CadastroNFe;
import cadastros.CadastroProduto;
import cadastros.CadastroRomaneio;
import cadastros.CadastroSafra;


public class GerenciarBancoRomaneios {

	
	 public boolean getConexao() {
		 try {
			 
	            Connection conn = ConexaoBanco.getConexao();
                ConexaoBanco.encerrar(conn);
                
	            return true;
	            

		 }catch(Exception e) {
			 return false;
		 }
		 
		 
	 }
	
	 public String sql_romaneio(CadastroRomaneio rom) {
		 

			double umidade = rom.getUmidade();
			double impureza = rom.getInpureza();
			double ardidos  = rom.getArdidos();
			double avariados = rom.getAvariados();
			
			double peso_bruto = rom.getPeso_bruto();
			double tara = rom.getTara();
			double peso_liquido = rom.getPeso_liquido();
			
			
			 String cfop  = rom.getCfop();
			 String descricao_cfop  = rom.getDescricao_cfop();
			 String operacao = rom.getOperacao();
			 String data = "";
	            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");

	            data = f.format(rom.getData());
			
		     int numero_romaneio = rom.getNumero_romaneio();
		    		 
			 CadastroSafra safra = rom.getSafra();
			 CadastroProduto produto = rom.getProduto();
			 CadastroCliente remetente = rom.getRemetente();
			 CadastroCliente destinatario = rom.getDestinatario();
			 CadastroCliente motorista = rom.getMotorista();

			 String caminho_arquivo = rom.getCaminho_arquivo();
		 
			 int id_produto, id_safra, id_remetente = 0, id_destinatario = 0, id_motorista = 0;
			 
			 id_produto = produto.getId_produto();
			 id_safra = safra.getId_safra();
			 
			 if(remetente != null) {
				 id_remetente = remetente.getId();
			 }
			 if(destinatario != null) {
				 id_destinatario = destinatario.getId();
			 }
			 if(motorista != null) {
				 id_motorista = motorista.getId();
			 }
			 
		 
		    String sql = "insert into romaneio\r\n" + 
 		"(codigo, operacao, cfop, descricao_cfop, data_romaneio, id_produto, id_safra, id_remetente,"
 		+ "id_destinatario, id_motorista, umidade, impureza, ardidos, avariados, peso_bruto, tara, "
 		+ "peso_liquido, caminho_arquivo) values ('"
 		+ numero_romaneio
		+ "','"
 		+ operacao
		+ "','"
			+ cfop
			+ "','"
			+ descricao_cfop
			+ "','"
			+ data
			+ "','"
			+ id_produto
			+ "','"
			+ id_safra
			+ "','"
			+ id_remetente
			+ "','"
			+ id_destinatario
			+ "','"
			+ id_motorista
			+ "','"
			+ umidade
			+ "','"
			+ impureza
			+ "','"
			+ ardidos
			+ "','"
			+ avariados
			+ "','"
			+ peso_bruto
			+ "','"
			+ tara
			+ "','"
			+ peso_liquido
			+ "','"
			+ caminho_arquivo
			+ "')";
		    
		    return sql;

	 }
	
	  public int inserir_romaneio(CadastroRomaneio rom) {
		  if( rom != null) {
	            Connection conn = null;
	            try {
	                conn = ConexaoBanco.getConexao();
	                String sql = sql_romaneio(rom);
	              
	    	        PreparedStatement grava = (PreparedStatement) conn.prepareStatement(sql); 
	    	        grava.execute();    
	                ConexaoBanco.fechaConexao(conn, grava);
	                return 1;
	 
	            } catch (Exception e) {
	                JOptionPane.showMessageDialog(null, "Erro ao inserir o romaneio no banco de"
	                        + "dados \nErro: " + e.getMessage() + "\nCausa: " + e.getCause() + "\nCodigo: " + rom.getNumero_romaneio() );
	                return 0;
	            }
	        } else {
	            System.out.println("O romaneio enviada por parametro esta vazia");
	            return 0;
	        }
	  }
	
	  
	  public ArrayList<CadastroRomaneio> listarRomaneios(){
		  Connection conn = null;
		  
	        PreparedStatement pstm = null;
	        ResultSet rs = null;
	        String selectRoms = "select * from romaneio";
	        ArrayList<CadastroRomaneio> lista_roms = new ArrayList<CadastroRomaneio>();
	        GerenciarBancoClientes gerenciar = new GerenciarBancoClientes();
	        ArrayList<CadastroCliente> clientes = gerenciar.getClientes(-1, -1, "");
	        
	        try {
	            conn = ConexaoBanco.getConexao();
	            pstm = conn.prepareStatement(selectRoms);
	            rs = pstm.executeQuery();
	            while (rs.next()) {
	            	CadastroRomaneio rom = new CadastroRomaneio();
	 
	            	rom.setId_romaneio(rs.getInt("id_romaneio"));
	            	rom.setNumero_romaneio(rs.getInt("codigo"));
	            	rom.setOperacao(rs.getString("operacao"));
	            	rom.setCfop(rs.getString("cfop"));
	            	rom.setDescricao_cfop(rs.getString("descricao_cfop"));
	            	//rom.setData(rs.getString("descricao_cfop"));
	            	
	            	int id_produto = rs.getInt("id_produto");
	            	rom.setProduto(new GerenciarBancoProdutos().getProduto(id_produto));
	            	int id_safra = rs.getInt("id_safra");
	            	rom.setSafra(new GerenciarBancoSafras().getSafra(id_safra));
	            	
	            	String data = rs.getString("data_romaneio");
	            	Date date = new SimpleDateFormat("dd/MM/yyyy").parse(data);
	        		rom.setData(date);
	        		
	        		
	        		
	            	int id_remetente = rs.getInt("id_remetente");
	            	for(CadastroCliente cliente : clientes) {
	            		if(cliente.getId() == id_remetente) {
	            			rom.setRemetente(cliente);
	            			break;
	            		}
	            	}
	            	
	            	int id_destinatario = rs.getInt("id_destinatario");
	            	for(CadastroCliente cliente : clientes) {
	            		if(cliente.getId() == id_destinatario) {
	            			rom.setDestinatario(cliente);
	            			break;
	            		}
	            	}
	            	
	            	int id_motorista = rs.getInt("id_motorista");
	               	for(CadastroCliente cliente : clientes) {
	            		if(cliente.getId() == id_motorista) {
	            			rom.setMotorista(cliente);
	            			break;
	            		}
	            	}
	            	
	        		
	            	rom.setUmidade(rs.getDouble("umidade"));
	            	rom.setInpureza(rs.getDouble("impureza"));
	            	rom.setArdidos(rs.getDouble("ardidos"));
	            	rom.setAvariados(rs.getDouble("avariados"));
	            	rom.setPeso_bruto(rs.getDouble("peso_bruto"));
	            	rom.setTara(rs.getDouble("tara"));
	            	rom.setPeso_liquido(rs.getDouble("peso_liquido"));
	            	

	            	rom.setCaminho_arquivo(rs.getString("caminho_arquivo"));
	             
	            	lista_roms.add(rom);
	            }
	            ConexaoBanco.fechaConexao(conn, pstm, rs);
	            return lista_roms;
	        } catch (Exception e) {
	         //   JOptionPane.showMessageDialog(null, "Erro ao listar romaneios\nErro: " + e.getMessage() + "\nCausa: " + e.getCause()   );
	        	return null;
	        }
	  }
	  
	  public ArrayList<CadastroRomaneio> listarRomaneiosMaisRapido(){
		  Connection conn = null;
		  
	        PreparedStatement pstm = null;
	        ResultSet rs = null;
	        String selectRoms = "select romaneio.*, \r\n"
	        		+ "remetente.tipo_cliente as tipo_remetente, remetente.cpf as cpf_remetente, remetente.cnpj as cnpj_remetente, remetente.nome_empresarial as remetente_nome_empresarial, remetente.nome_fantasia as remetente_nome_fantasia,\r\n"
	        		+ "destinatario.tipo_cliente as tipo_destinatario,  destinatario.cpf as cpf_destinatario, destinatario.cnpj as cnpj_destinatario, destinatario.nome_empresarial as destinatario_nome_empresarial, destinatario.nome_fantasia as destinatario_nome_fantasia,\r\n"
	        		+ "motorista.tipo_cliente as tipo_motorista, motorista.cpf as cpf_motorista, motorista.cnpj as cnpj_motorista, motorista.nome_empresarial as motorista_nome_empresarial, motorista.nome_fantasia as motorista_nome_fantasia,\r\n"
	        		+ "pd.nome_produto as nome_produto, pd.transgenia as transgenia,\r\n"
	        		+ "sf.ano_plantio as ano_plantio, sf.ano_colheita as ano_colheita\r\n"
	        		+ " from romaneio\r\n"
	        		+ "LEFT JOIN  safra sf on sf.id_safra = romaneio.id_safra\r\n"
	        		+ "LEFT JOIN  produto pd on pd.id_produto = romaneio.id_produto\r\n"
	        		+ "LEFT JOIN cliente remetente on remetente.id_cliente = romaneio.id_remetente\r\n"
	        		+ "LEFT JOIN cliente destinatario on destinatario.id_cliente = romaneio.id_destinatario\r\n"
	        		+ "LEFT JOIN cliente motorista on motorista.id_cliente = romaneio.id_motorista\r\n"
	        		+ "\r\n"
	        		+ "";
	        ArrayList<CadastroRomaneio> lista_roms = new ArrayList<CadastroRomaneio>();
	        GerenciarBancoClientes gerenciar = new GerenciarBancoClientes();
	        ArrayList<CadastroCliente> clientes = gerenciar.getClientes(-1, -1, "");
	        
	        try {
	            conn = ConexaoBanco.getConexao();
	            pstm = conn.prepareStatement(selectRoms);
	            rs = pstm.executeQuery();
	            while (rs.next()) {
	            	CadastroRomaneio rom = new CadastroRomaneio();
	 
	            	rom.setId_romaneio(rs.getInt("id_romaneio"));
	            	rom.setNumero_romaneio(rs.getInt("codigo"));
	            	rom.setOperacao(rs.getString("operacao"));
	            	rom.setCfop(rs.getString("cfop"));
	            	rom.setDescricao_cfop(rs.getString("descricao_cfop"));
	            	CadastroProduto prod = new CadastroProduto();
	            	prod.setNome_produto(rs.getString("nome_produto"));
	            	prod.setTransgenia(rs.getString("transgenia"));
	            	rom.setProduto(prod);
	            	CadastroSafra safra = new CadastroSafra();
	            	safra.setAno_plantio(rs.getInt("ano_plantio"));
	            	safra.setAno_colheita(rs.getInt("ano_colheita"));

	            	rom.setSafra(safra);
	            	
	            	String data = rs.getString("data_romaneio");
	            	Date date = new SimpleDateFormat("dd/MM/yyyy").parse(data);
	        		rom.setData(date);
	        		
	        		
	        		
	        		CadastroCliente motorista = new CadastroCliente();
	        		CadastroCliente	remetente = new CadastroCliente();
	        		CadastroCliente destinatario = new CadastroCliente();
	        		motorista.setTipo_pessoa(rs.getInt("tipo_motorista"));
	        		motorista.setCpf(rs.getString("cpf_motorista"));
	        		motorista.setCnpj(rs.getString("cnpj_motorista"));
	        		motorista.setNome_empresarial(rs.getString("motorista_nome_empresarial"));
	        		motorista.setNome_fantaia(rs.getString("motorista_nome_fantasia"));

	        		remetente.setTipo_pessoa(rs.getInt("tipo_remetente"));
	        		remetente.setCpf(rs.getString("cpf_remetente"));
	        		remetente.setCnpj(rs.getString("cnpj_remetente"));
	        		remetente.setNome_empresarial(rs.getString("remetente_nome_empresarial"));
	        		remetente.setNome_fantaia(rs.getString("remetente_nome_fantasia"));

	        		destinatario.setTipo_pessoa(rs.getInt("tipo_destinatario"));
	        		destinatario.setCpf(rs.getString("cpf_destinatario"));
	        		destinatario.setCnpj(rs.getString("cnpj_destinatario"));
	        		destinatario.setNome_empresarial(rs.getString("destinatario_nome_empresarial"));
	        		destinatario.setNome_fantaia(rs.getString("destinatario_nome_fantasia"));
 
	        		rom.setRemetente(remetente);
	        		rom.setDestinatario(destinatario);
	        		rom.setMotorista(motorista);
	            	
	        		
	            	rom.setUmidade(rs.getDouble("umidade"));
	            	rom.setInpureza(rs.getDouble("impureza"));
	            	rom.setArdidos(rs.getDouble("ardidos"));
	            	rom.setAvariados(rs.getDouble("avariados"));
	            	rom.setPeso_bruto(rs.getDouble("peso_bruto"));
	            	rom.setTara(rs.getDouble("tara"));
	            	rom.setPeso_liquido(rs.getDouble("peso_liquido"));
	            	

	            	rom.setCaminho_arquivo(rs.getString("caminho_arquivo"));
	             
	            	lista_roms.add(rom);
	            }
	            ConexaoBanco.fechaConexao(conn, pstm, rs);
	            return lista_roms;
	        } catch (Exception e) {
	           JOptionPane.showMessageDialog(null, "Erro ao listar romaneios\nErro: " + e.getMessage() + "\nCausa: " + e.getCause()   );
	        	return null;
	        }
	  }
	  
	  public ArrayList<CadastroRomaneio> listarRomaneiosPorCliente(int id_cliente){
		  Connection conn = null;
		  
	        PreparedStatement pstm = null;
	        ResultSet rs = null;
	        String selectRoms = "select romaneio.*, \r\n"
	        		+ "remetente.tipo_cliente as tipo_remetente, remetente.cpf as cpf_remetente, remetente.cnpj as cnpj_remetente, remetente.nome_empresarial as remetente_nome_empresarial, remetente.nome_fantasia as remetente_nome_fantasia,\r\n"
	        		+ "destinatario.tipo_cliente as tipo_destinatario,  destinatario.cpf as cpf_destinatario, destinatario.cnpj as cnpj_destinatario, destinatario.nome_empresarial as destinatario_nome_empresarial, destinatario.nome_fantasia as destinatario_nome_fantasia,\r\n"
	        		+ "motorista.tipo_cliente as tipo_motorista, motorista.cpf as cpf_motorista, motorista.cnpj as cnpj_motorista, motorista.nome_empresarial as motorista_nome_empresarial, motorista.nome_fantasia as motorista_nome_fantasia,\r\n"
	        		+ "pd.nome_produto as nome_produto, pd.transgenia as transgenia,\r\n"
	        		+ "sf.ano_plantio as ano_plantio, sf.ano_colheita as ano_colheita\r\n"
	        		+ " from romaneio\r\n"
	        		+ "LEFT JOIN  safra sf on sf.id_safra = romaneio.id_safra\r\n"
	        		+ "LEFT JOIN  produto pd on pd.id_produto = romaneio.id_produto\r\n"
	        		+ "LEFT JOIN cliente remetente on remetente.id_cliente = romaneio.id_remetente\r\n"
	        		+ "LEFT JOIN cliente destinatario on destinatario.id_cliente = romaneio.id_destinatario\r\n"
	        		+ "LEFT JOIN cliente motorista on motorista.id_cliente = romaneio.id_motorista "
	        		+ "where romaneio.id_remetente = ? or romaneio.id_destinatario = ?";
	        
	        ArrayList<CadastroRomaneio> lista_roms = new ArrayList<CadastroRomaneio>();
	        GerenciarBancoClientes gerenciar = new GerenciarBancoClientes();
	        ArrayList<CadastroCliente> clientes = gerenciar.getClientes(-1, -1, "");
	        
	        try {
	            conn = ConexaoBanco.getConexao();
	            pstm = conn.prepareStatement(selectRoms);
	            pstm.setInt(1, id_cliente);
	            pstm.setInt(2, id_cliente);

	            rs = pstm.executeQuery();
	            while (rs.next()) {
	            	CadastroRomaneio rom = new CadastroRomaneio();
	 
	            	rom.setId_romaneio(rs.getInt("id_romaneio"));
	            	rom.setNumero_romaneio(rs.getInt("codigo"));
	            	rom.setOperacao(rs.getString("operacao"));
	            	rom.setCfop(rs.getString("cfop"));
	            	rom.setDescricao_cfop(rs.getString("descricao_cfop"));
	            	CadastroProduto prod = new CadastroProduto();
	            	prod.setNome_produto(rs.getString("nome_produto"));
	            	prod.setTransgenia(rs.getString("transgenia"));
	            	rom.setProduto(prod);
	            	CadastroSafra safra = new CadastroSafra();
	            	safra.setAno_plantio(rs.getInt("ano_plantio"));
	            	safra.setAno_colheita(rs.getInt("ano_colheita"));

	            	rom.setSafra(safra);
	            	
	            	String data = rs.getString("data_romaneio");
	            	Date date = new SimpleDateFormat("dd/MM/yyyy").parse(data);
	        		rom.setData(date);
	        		
	        		
	        		
	        		CadastroCliente motorista = new CadastroCliente();
	        		CadastroCliente	remetente = new CadastroCliente();
	        		CadastroCliente destinatario = new CadastroCliente();
	        		motorista.setTipo_pessoa(rs.getInt("tipo_motorista"));
	        		motorista.setCpf(rs.getString("cpf_motorista"));
	        		motorista.setCnpj(rs.getString("cnpj_motorista"));
	        		motorista.setNome_empresarial(rs.getString("motorista_nome_empresarial"));
	        		motorista.setNome_fantaia(rs.getString("motorista_nome_fantasia"));

	        		remetente.setTipo_pessoa(rs.getInt("tipo_remetente"));
	        		remetente.setCpf(rs.getString("cpf_remetente"));
	        		remetente.setCnpj(rs.getString("cnpj_remetente"));
	        		remetente.setNome_empresarial(rs.getString("remetente_nome_empresarial"));
	        		remetente.setNome_fantaia(rs.getString("remetente_nome_fantasia"));

	        		destinatario.setTipo_pessoa(rs.getInt("tipo_destinatario"));
	        		destinatario.setCpf(rs.getString("cpf_destinatario"));
	        		destinatario.setCnpj(rs.getString("cnpj_destinatario"));
	        		destinatario.setNome_empresarial(rs.getString("destinatario_nome_empresarial"));
	        		destinatario.setNome_fantaia(rs.getString("destinatario_nome_fantasia"));
 
	        		rom.setRemetente(remetente);
	        		rom.setDestinatario(destinatario);
	        		rom.setMotorista(motorista);
	            	
	        		
	            	rom.setUmidade(rs.getDouble("umidade"));
	            	rom.setInpureza(rs.getDouble("impureza"));
	            	rom.setArdidos(rs.getDouble("ardidos"));
	            	rom.setAvariados(rs.getDouble("avariados"));
	            	rom.setPeso_bruto(rs.getDouble("peso_bruto"));
	            	rom.setTara(rs.getDouble("tara"));
	            	rom.setPeso_liquido(rs.getDouble("peso_liquido"));
	            	

	            	rom.setCaminho_arquivo(rs.getString("caminho_arquivo"));
	             
	            	lista_roms.add(rom);
	            }
	            ConexaoBanco.fechaConexao(conn, pstm, rs);
	            return lista_roms;
	        } catch (Exception e) {
	           JOptionPane.showMessageDialog(null, "Erro ao listar romaneios\nErro: " + e.getMessage() + "\nCausa: " + e.getCause()   );
	        	return null;
	        }
	  }
	 
	  public boolean verificarRegistroRomaneio(int codigo) {
		  Connection conn = null;
	        PreparedStatement pstm = null;
	        ResultSet rs = null;
	        String selectRoms = "select * from romaneio where codigo = ?";
	        CadastroRomaneio rom = new CadastroRomaneio();
	        
	        try {
	            conn = ConexaoBanco.getConexao();
	            pstm = conn.prepareStatement(selectRoms);
	            pstm.setInt(1,  codigo);
	            rs = pstm.executeQuery();
	             rs.next();
	 
	         	rom.setId_romaneio(rs.getInt("id_romaneio"));
            	rom.setNumero_romaneio(rs.getInt("codigo"));
            
	            ConexaoBanco.fechaConexao(conn, pstm, rs);
	            if(rom.getNumero_romaneio() == codigo)
	              return true;
	            else
	            	return false;
	        } catch (Exception e) {
	          //  JOptionPane.showMessageDialog(null, "Erro ao listar romaneio"  );
	        	return false;
	        }
	  }
	  
	  
	  public boolean removerRomaneio(int id_romaneio) {
			String sql_delete_romaneio = "DELETE FROM romaneio WHERE id_romaneio = ?";
			Connection conn = null;
			ResultSet rs = null;
			try {
				conn = ConexaoBanco.getConexao();
				PreparedStatement pstm;
				pstm = conn.prepareStatement(sql_delete_romaneio);

				pstm.setInt(1, id_romaneio);

				pstm.execute();
				ConexaoBanco.fechaConexao(conn, pstm);
				JOptionPane.showMessageDialog(null, "Romaneio excluido, banco normalizado ");
				return true;

			} catch (Exception f) {
				JOptionPane.showMessageDialog(null,
						"Erro ao exlcuir o romaneio do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
								+ "dados " + f.getMessage());
				return false;
			}

	  }
	  

	  
	  
}
