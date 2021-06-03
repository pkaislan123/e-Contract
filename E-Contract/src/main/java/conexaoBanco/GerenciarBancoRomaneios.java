package main.java.conexaoBanco;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroContrato.CadastroPagamentoContratual;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroModelo;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroRomaneio;
import main.java.cadastros.CadastroSafra;
import main.java.cadastros.Parcela;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.outros.DadosGlobais;
import main.java.tratamento_proprio.Log;


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
			double peso_liquido_sem_desconto = rom.getPeso_liquido_sem_descontos();
			double peso_recepcao = rom.getDespesa_recepcao();
			
			double peso_desconto_umidade = rom.getPeso_desconto_umidade();
			double peso_desconto_impureza = rom.getPeso_desconto_impureza();
			double peso_desconto_avariado = rom.getPeso_desconto_avariados();
			double peso_desconto_total = rom.getPeso_desconto_total();

			
			 String cfop  = rom.getCfop();
			 String descricao_cfop  = rom.getDescricao_cfop();
			 String operacao = rom.getOperacao();
			 String data = "";
			 String doc_entrada = rom.getDoc_entrada();
			 String amostra = rom.getAmostra();
			 String silo = rom.getSilo();
			 String transgenese = rom.getTransgenia();
			 
			 
	            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");

	            data = f.format(rom.getData());
			
		     int numero_romaneio = rom.getNumero_romaneio();
		    		 
			 CadastroSafra safra = rom.getSafra();
			 CadastroProduto produto = rom.getProduto();
			 CadastroCliente remetente = rom.getRemetente();
			 CadastroCliente destinatario = rom.getDestinatario();

			 String caminho_arquivo = rom.getCaminho_arquivo();
		 
			 int id_produto, id_safra, id_remetente = 0, id_destinatario = 0;
			 
			 id_produto = produto.getId_produto();
			 id_safra = safra.getId_safra();
			 
			 if(remetente != null) {
				 id_remetente = remetente.getId();
			 }
			 if(destinatario != null) {
				 id_destinatario = destinatario.getId();
			 }
			
			 
		 
		    String sql = "insert into romaneio\r\n" + 
 		"(codigo, operacao, cfop, descricao_cfop, data_romaneio, id_produto, id_safra, id_remetente,"
 		+ "id_destinatario, nome_motorista, cpf_motorista, placa, umidade, impureza, ardidos, avariados, peso_bruto, tara, "
 		+ "peso_liquido, peso_liquido_sem_desconto, peso_desconto_umidade, peso_desconto_impureza,"
 		+ "peso_desconto_avariado, peso_desconto_total,peso_recepcao, caminho_arquivo, doc_entrada, amostra) values ('"
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
			+ rom.getNome_motorista()
			+ "','"
			+ rom.getCpf_motorista()
			+ "','"
			+ rom.getPlaca()
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
			+ peso_liquido_sem_desconto
			+ "','"
			+ peso_desconto_umidade
			+ "','"
			+ peso_desconto_impureza
			+ "','"
			+ peso_desconto_avariado
			+ "','"
			+ peso_desconto_total
			+ "','"
			+ peso_recepcao
			+ "','"
			+ caminho_arquivo
			+ "','"
			+ doc_entrada
			+ "','"
			+ amostra
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
	        String selectRoms = "select * from romaneio order by data_romaneio";
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
	            	
	        		
	            	rom.setUmidade(rs.getDouble("umidade"));
	            	rom.setInpureza(rs.getDouble("impureza"));
	            	rom.setUmidade2(rs.getDouble("umidade2"));
	            	rom.setImpureza2(rs.getDouble("impureza2"));
	            	rom.setArdidos(rs.getDouble("ardidos"));
	            	rom.setAvariados(rs.getDouble("avariados"));
	            	rom.setPeso_bruto(rs.getDouble("peso_bruto"));
	            	rom.setTara(rs.getDouble("tara"));
	            	rom.setPeso_liquido(rs.getDouble("peso_liquido"));
	            	
	            	rom.setPeso_liquido_sem_descontos(rs.getDouble("peso_liquido_sem_desconto"));
	            	rom.setPeso_desconto_umidade(rs.getDouble("peso_desconto_umidade"));
	            	rom.setPeso_desconto_impureza(rs.getDouble("peso_desconto_impureza"));
	            	rom.setPeso_desconto_avariados(rs.getDouble("peso_desconto_avariado"));
	            	rom.setPeso_desconto_total(rs.getDouble("peso_desconto_total"));
	            	rom.setDespesa_recepcao(rs.getDouble("peso_recepcao"));

	            	
	            	
	            	rom.setNome_motorista(rs.getString("nome_motorista"));
	            	rom.setCpf_motorista(rs.getString("cpf_motorista"));
	            	rom.setPlaca(rs.getString("placa"));
	            	rom.setCaminho_arquivo(rs.getString("caminho_arquivo"));
	            	rom.setDoc_entrada(rs.getString("doc_entrada"));
	            	rom.setAmostra(rs.getString("amostra"));
	            	rom.setId_silo(rs.getInt("id_silo"));
	            	rom.setId_transgenese(rs.getInt("id_transgenia"));
	            	rom.setId_classificador(rs.getInt("id_classificador"));
	            	rom.setStatus_monsanto(rs.getInt("status_monsanto"));
	            	rom.setObservacao(rs.getString("observacao"));

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
	        String selectRoms = "call busca_romaneios_mais_rapido()";
	        ArrayList<CadastroRomaneio> lista_roms = new ArrayList<CadastroRomaneio>();
	        
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
	            	safra.setId_safra(rs.getInt("id_safra"));

	            	rom.setSafra(safra);
	            	
	            	String data = rs.getString("data_romaneio");
	            	Date date = new SimpleDateFormat("dd/MM/yyyy").parse(data);
	        		rom.setData(date);
	        		
	        		
	        		
	        		CadastroCliente	remetente = new CadastroCliente();
	        		CadastroCliente destinatario = new CadastroCliente();
	        		

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
	            	
	        		
	            	rom.setUmidade(rs.getDouble("umidade"));
	            	rom.setInpureza(rs.getDouble("impureza"));
	            	
	            	rom.setUmidade2(rs.getDouble("umidade2"));
	            	rom.setImpureza2(rs.getDouble("impureza2"));
	            	
	            	rom.setArdidos(rs.getDouble("ardidos"));
	            	rom.setAvariados(rs.getDouble("avariados"));
	            	rom.setPeso_bruto(rs.getDouble("peso_bruto"));
	            	rom.setTara(rs.getDouble("tara"));
	            	rom.setPeso_liquido(rs.getDouble("peso_liquido"));
	            	
	            	rom.setPeso_liquido_sem_descontos(rs.getDouble("peso_liquido_sem_desconto"));
	            	rom.setPeso_desconto_umidade(rs.getDouble("peso_desconto_umidade"));
	            	rom.setPeso_desconto_impureza(rs.getDouble("peso_desconto_impureza"));
	            	rom.setPeso_desconto_avariados(rs.getDouble("peso_desconto_avariado"));
	            	rom.setPeso_desconto_total(rs.getDouble("peso_desconto_total"));
	            	rom.setDespesa_recepcao(rs.getDouble("peso_recepcao"));
	            	
	            	rom.setNome_motorista(rs.getString("nome_motorista"));
	            	rom.setCpf_motorista(rs.getString("cpf_motorista"));
	            	rom.setPlaca(rs.getString("placa"));

	            	rom.setCaminho_arquivo(rs.getString("caminho_arquivo"));
	            	rom.setDoc_entrada(rs.getString("doc_entrada"));
	            	rom.setAmostra(rs.getString("amostra"));
	            	rom.setSilo(rs.getString("nome_silo"));
	            	rom.setId_silo(rs.getInt("id_silo"));
	            	rom.setId_transgenese(rs.getInt("id_transgenia"));
	            	rom.setTransgenia(rs.getString("nome_transgenia"));
	            	rom.setClassificador(rs.getString("nome_classificador"));
	            	rom.setId_classificador(rs.getInt("id_classificador"));
	            	rom.setStatus_monsanto(rs.getInt("status_monsanto"));
	            	rom.setObservacao(rs.getString("observacao"));
	            	rom.setRoyalties(rs.getInt("royalties"));
	            	
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
	        String selectRoms = "call busca_romaneios_mais_rapido_por_cliente(?)";
	        
	        ArrayList<CadastroRomaneio> lista_roms = new ArrayList<CadastroRomaneio>();
	        GerenciarBancoClientes gerenciar = new GerenciarBancoClientes();
	        ArrayList<CadastroCliente> clientes = gerenciar.getClientes(-1, -1, "");
	        
	        try {
	            conn = ConexaoBanco.getConexao();
	            pstm = conn.prepareStatement(selectRoms);
	            pstm.setInt(1, id_cliente);

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
	        		
	        		
	        		

	        		CadastroCliente	remetente = new CadastroCliente();
	        		CadastroCliente destinatario = new CadastroCliente();

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
	            	
	        		
	            	rom.setUmidade(rs.getDouble("umidade"));
	            	rom.setInpureza(rs.getDouble("impureza"));
	            	
	            	rom.setUmidade2(rs.getDouble("umidade2"));
	            	rom.setImpureza2(rs.getDouble("impureza2"));
	            	
	            	
	            	rom.setArdidos(rs.getDouble("ardidos"));
	            	rom.setAvariados(rs.getDouble("avariados"));
	            	rom.setPeso_bruto(rs.getDouble("peso_bruto"));
	            	rom.setTara(rs.getDouble("tara"));
	            	rom.setPeso_liquido(rs.getDouble("peso_liquido"));
	            	
	            	rom.setPeso_liquido_sem_descontos(rs.getDouble("peso_liquido_sem_desconto"));
	            	rom.setPeso_desconto_umidade(rs.getDouble("peso_desconto_umidade"));
	            	rom.setPeso_desconto_impureza(rs.getDouble("peso_desconto_impureza"));
	            	rom.setPeso_desconto_avariados(rs.getDouble("peso_desconto_avariado"));
	            	rom.setPeso_desconto_total(rs.getDouble("peso_desconto_total"));
	            	rom.setDespesa_recepcao(rs.getDouble("peso_recepcao"));
	            	
	            	rom.setNome_motorista(rs.getString("nome_motorista"));
	            	rom.setCpf_motorista(rs.getString("cpf_motorista"));
	            	rom.setPlaca(rs.getString("placa"));

	            	rom.setCaminho_arquivo(rs.getString("caminho_arquivo"));
	            	rom.setDoc_entrada(rs.getString("doc_entrada"));
	            	rom.setAmostra(rs.getString("amostra"));
	            	rom.setSilo(rs.getString("nome_silo"));
	            	rom.setId_silo(rs.getInt("id_silo"));
	            	rom.setId_transgenese(rs.getInt("id_transgenia"));
	            	rom.setTransgenia(rs.getString("nome_transgenia"));
	            	rom.setClassificador(rs.getString("nome_classificador"));
	            	rom.setId_classificador(rs.getInt("id_classificador"));
	            	rom.setStatus_monsanto(rs.getInt("status_monsanto"));
	            	rom.setObservacao(rs.getString("observacao"));
	            	rom.setRoyalties(rs.getInt("royalties"));

	            	
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
						"Erro ao excluir o romaneio do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
								+ "dados " + f.getMessage());
				return false;
			}

	  }
	  

	  public boolean atualizarRomaneio(CadastroRomaneio dado) {
			if (dado != null) {
				try {
					Connection conn = null;
					String atualizar = null;
					PreparedStatement pstm;

					//atualizar = "update financeiro_conta set nome_conta = ?, id_grupo_contas = ?,  tipo_conta = ?, observacao = ?,descricao = ? where id_conta = ? ";
					atualizar = "update romaneio set umidade2 = ? , impureza2 = ?, "
							+ "id_classificador = ?, id_silo = ?, id_transgenese = ?, status_monsanto = ?,"
							+ "observacao = ? where id_romaneio = ?";
					conn = ConexaoBanco.getConexao();
					pstm = conn.prepareStatement(atualizar);
					
					pstm.setDouble(1, dado.getUmidade2());
					pstm.setDouble(2, dado.getImpureza2());
					pstm.setInt(3, dado.getId_classificador());
					pstm.setInt(4, dado.getId_silo());

					pstm.setInt(5, dado.getId_transgenese());
					pstm.setInt(6, dado.getStatus_monsanto());
					pstm.setString(7, dado.getObservacao());
					pstm.setInt(8, dado.getId_romaneio());

				

					pstm.execute();
					// JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
					System.out.println("Romaneio Atualizada com sucesso");
					ConexaoBanco.fechaConexao(conn);
					return true;
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Erro ao atualizar o romaneio no banco de dados\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
					return false;
				}
			} else {
				JOptionPane.showMessageDialog(null, "Os parametros estão vazios");
				return false;
			}
		}
	  
	  
}
