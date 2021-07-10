package main.java.conexaoBanco;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.java.cadastros.FinanceiroPagamento;
import main.java.cadastros.FinanceiroPagamentoCompleto;
import main.java.cadastros.FinanceiroPagamentoEmprestimo;
import main.java.cadastros.FinanceiroPagamentoEmprestimoCompleto;
import main.java.cadastros.Lancamento;


public class GerenciarBancoFinanceiroPagamentoEmprestimo {

	public String sql_dado (FinanceiroPagamentoEmprestimo dado) {
		/*
		 * CREATE TABLE `financeiro_pagamento` (
	  `id_pagamento` int(5) NOT NULL AUTO_INCREMENT,
	  `identificador` text DEFAULT NULL, 
	  `id_forma_pagamento` text DEFAULT NULL,
	  `valor` varchar(40) DEFAULT NULL,
	  `data_pagamento` varchar(40) DEFAULT NULL,
	  `observacao` text DEFAULT NULL,
	  `descricao` text DEFAULT NULL,
	  `caminho_arquivo` text DEFAULT NULL,
	  PRIMARY KEY (`id_pagamento`)
	) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8
		 */
		return "insert into financeiro_pagamento_emprestimo (id_lancamento_pai, identificador,tipo_pagador, id_pagador,tipo_recebedor, id_recebedor, id_forma_pagamento, "
				+ "status_condicao_pagamento, "
				+ " objeto, especie, quantidade, unidade_medida, valor_unitario,"
				+ "valor_total, data_pagamento,fluxo_caixa,"
				+ "observacao, descricao,caminho_arquivo, extrato_bancario ) values ('"
				+ dado.getId_lancamento() + "','"
				+ dado.getIdentificador() + "','"
			    + dado.getTipo_pagador() + "','"
				+ dado.getId_pagador() + "','"
					    + dado.getTipo_recebedor() + "','"

				+ dado.getId_recebedor() + "','"
				+ dado.getId_condicao_pagamento() + "','"
				+ dado.getStatus_pagamento() + "','"
						+ dado.getObjeto() + "','"
						+ dado.getEspecie() + "','"
						+ dado.getQuantidade() + "','"
						+ dado.getUnidade_medida() + "','"
						+ dado.getValor_unitario() + "','"
				+ dado.getValor() + "','"
				+ dado.getData_pagamento() + "','"
				  + dado.getFluxo_caixa() + "','"
			    + dado.getObservacao() + "','" 
			    + dado.getDescricao() + "','" 
			    +  dado.getCaminho_arquivo()  + "','" 
			    + dado.getExtrato() + "')";
	}
	


	public int inserirFinanceiroPagamento(FinanceiroPagamentoEmprestimo dado) {
		int result = -1;
		int id_cliente = -1;
		if (dado != null) {
			Connection conn = null;
			try {

				conn = ConexaoBanco.getConexao();

				String query = sql_dado(dado);
				Statement stmt = (Statement) conn.createStatement();
				int numero = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					result = rs.getInt(1);
					System.out.println("Id do Pagamento inserido: " + result);
				}
				rs.close();
				stmt.close();

				return result;

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao inserir o Pagamento Financeiro no banco de dados, Erro: " + e.getMessage() + "\nCausa: " + e.getCause()) ;

				return -1;
			}
		} else {
			System.out.println("O parametro enviado por parametro esta vazio");
			return -1;
		}
	}

	public ArrayList<FinanceiroPagamentoEmprestimo> getFinanceiroPagamentos() {
		String select = "select * from financeiro_pagamento_emprestimo";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<FinanceiroPagamentoEmprestimo> lista = new ArrayList<>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(select);

			rs = pstm.executeQuery();
			while (rs.next()) {
				FinanceiroPagamentoEmprestimo dado = new FinanceiroPagamentoEmprestimo();
				
				dado.setId_pagamento(rs.getInt("id_pagamento"));
				dado.setId_lancamento(rs.getInt("id_lancamento_pai"));
				dado.setIdentificador(rs.getString("identificador"));
				dado.setId_condicao_pagamento(rs.getInt("id_forma_pagamento"));
				dado.setStatus_pagamento(rs.getInt("status_condicao_pagamento"));
				dado.setId_recebedor(rs.getInt("id_recebedor"));
				dado.setFluxo_caixa(rs.getInt("fluxo_caixa"));
				dado.setTipo_pagador(rs.getInt("tipo_pagador"));
				dado.setId_pagador(rs.getInt("id_pagador"));
				dado.setObjeto(rs.getInt("objeto"));
				dado.setEspecie(rs.getString("especie"));
				dado.setQuantidade(rs.getDouble("quantidade"));
				dado.setUnidade_medida(rs.getString("unidade_medida"));
				dado.setId_documento(rs.getInt("id_documento"));
				dado.setTipo_recebedor(rs.getInt("tipo_recebedor"));
				dado.setExtrato(rs.getInt("extrato_bancario"));
				try{
					dado.setValor_unitario(new BigDecimal(rs.getString("valor_unitario")));
				}catch(Exception e) {
					dado.setValor_unitario(BigDecimal.ZERO);
				}
				try{
					dado.setValor(new BigDecimal(rs.getString("valor_total")));
				}catch(Exception e) {
					dado.setValor(BigDecimal.ZERO);
				}
				dado.setData_pagamento(rs.getString("data_pagamento"));
				dado.setDescricao(rs.getString("descricao"));
				dado.setObservacao(rs.getString("observacao"));
				dado.setCaminho_arquivo(rs.getString("caminho_arquivo"));
			

				lista.add(dado);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar a FinanceiroPagamento \nErro: " + e.getMessage() + "\nCausa: " + e.getCause());// );
		}
		return lista;

	}
	
	public ArrayList<FinanceiroPagamentoEmprestimo> getFinanceiroPagamentosPorLancamento(int id_lancamento) {
		String select = "select * from financeiro_pagamento_emprestimo where id_lancamento_pai = ? ";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<FinanceiroPagamentoEmprestimo> lista = new ArrayList<>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(select);
			pstm.setInt(1, id_lancamento);

			rs = pstm.executeQuery();
			while (rs.next()) {
				FinanceiroPagamentoEmprestimo dado = new FinanceiroPagamentoEmprestimo();
				
				dado.setId_pagamento(rs.getInt("id_pagamento"));
				dado.setId_lancamento(rs.getInt("id_lancamento_pai"));
				dado.setIdentificador(rs.getString("identificador"));
				dado.setId_condicao_pagamento(rs.getInt("id_forma_pagamento"));
				dado.setStatus_pagamento(rs.getInt("status_condicao_pagamento"));
				dado.setFluxo_caixa(rs.getInt("fluxo_caixa"));
				dado.setTipo_pagador(rs.getInt("tipo_pagador"));
				dado.setId_pagador(rs.getInt("id_pagador"));
				dado.setTipo_recebedor(rs.getInt("tipo_recebedor"));

				dado.setId_recebedor(rs.getInt("id_recebedor"));
				dado.setObjeto(rs.getInt("objeto"));
				dado.setEspecie(rs.getString("especie"));
				dado.setQuantidade(rs.getDouble("quantidade"));
				dado.setUnidade_medida(rs.getString("unidade_medida"));
				dado.setExtrato(rs.getInt("extrato_bancario"));

				try{
					dado.setValor_unitario(new BigDecimal(rs.getString("valor_unitario")));
				}catch(Exception e) {
					dado.setValor_unitario(BigDecimal.ZERO);
				}
				try{
					dado.setValor(new BigDecimal(rs.getString("valor_total")));
				}catch(Exception e) {
					dado.setValor(BigDecimal.ZERO);
				}
				dado.setData_pagamento(rs.getString("data_pagamento"));
				dado.setDescricao(rs.getString("descricao"));
				dado.setObservacao(rs.getString("observacao"));
				dado.setCaminho_arquivo(rs.getString("caminho_arquivo"));
				dado.setId_documento(rs.getInt("id_documento"));

			

				lista.add(dado);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar o Financeiro Pagamento \nErro: " + e.getMessage() + "\nCausa: " + e.getCause());// );
		}
		return lista;

	}
	
	
	public ArrayList<FinanceiroPagamentoEmprestimo> getFinanceiroPagamentosPorLancamentoMaisRapido(int id_lancamento) {
		String select = "select fpag.*,\r\n"
				+ "(case\r\n"
				+ "when fpag.tipo_pagador = 0\r\n"
				+ "then\r\n"
				+ "ib_pag.nome_instituicao_bancaria\r\n"
				+ "when fpag.tipo_pagador = 1\r\n"
				+ "then\r\n"
				+ "(\r\n"
				+ "case\r\n"
				+ "when cli_pag.tipo_cliente = '0' then cli_pag.nome_empresarial \r\n"
				+ " when cli_pag.tipo_cliente = '1' then cli_pag.nome_fantasia\r\n"
				+ "end\r\n"
				+ ")\r\n"
				+ "end) as nome_pagador,\r\n"
				+ "(\r\n"
				+ "case\r\n"
				+ "when fpag.tipo_recebedor = 0\r\n"
				+ "then\r\n"
				+ "ib_rec.nome_instituicao_bancaria\r\n"
				+ "when fpag.tipo_recebedor = 1\r\n"
				+ "then\r\n"
				+ "(\r\n"
				+ "case\r\n"
				+ "when cli_rec.tipo_cliente = '0' then cli_rec.nome_empresarial \r\n"
				+ " when cli_rec.tipo_cliente = '1' then cli_rec.nome_fantasia\r\n"
				+ "end\r\n"
				+ ")\r\n"
				+ "end) as nome_recebedor,\r\n"
				+ "cp.nome_condicao_pagamento\r\n"
				+ "from financeiro_pagamento_emprestimo fpag\r\n"
				+ "left join condicao_pagamento cp on cp.id_condicao_pagamento = fpag.id_forma_pagamento\r\n"
				+ "left join cliente cli_pag on cli_pag.id_cliente = fpag.id_pagador\r\n"
				+ "left join instituicao_bancaria ib_pag on ib_pag.id_instituicao_bancaria = fpag.id_pagador\r\n"
				+ "left join cliente cli_rec on cli_rec.id_cliente = fpag.id_recebedor\r\n"
				+ "left join instituicao_bancaria ib_rec on ib_rec.id_instituicao_bancaria = fpag.id_recebedor\r\n"
				+ "where id_lancamento_pai = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<FinanceiroPagamentoEmprestimo> lista = new ArrayList<>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(select);
			pstm.setInt(1, id_lancamento);

			rs = pstm.executeQuery();
			while (rs.next()) {
				FinanceiroPagamentoEmprestimo dado = new FinanceiroPagamentoEmprestimo();
				
				dado.setId_pagamento(rs.getInt("id_pagamento"));
				dado.setId_lancamento(rs.getInt("id_lancamento_pai"));
				dado.setIdentificador(rs.getString("identificador"));
				dado.setId_condicao_pagamento(rs.getInt("id_forma_pagamento"));
				dado.setStatus_pagamento(rs.getInt("status_condicao_pagamento"));
				dado.setFluxo_caixa(rs.getInt("fluxo_caixa"));
				dado.setTipo_pagador(rs.getInt("tipo_pagador"));
				dado.setId_pagador(rs.getInt("id_pagador"));
				dado.setTipo_recebedor(rs.getInt("tipo_recebedor"));
				dado.setNome_pagador(rs.getString("nome_pagador"));
				dado.setNome_recebedor(rs.getString("nome_recebedor"));
				dado.setNome_forma_pagamento(rs.getString("nome_condicao_pagamento"));
				dado.setId_recebedor(rs.getInt("id_recebedor"));
				dado.setObjeto(rs.getInt("objeto"));
				dado.setEspecie(rs.getString("especie"));
				dado.setQuantidade(rs.getDouble("quantidade"));
				dado.setUnidade_medida(rs.getString("unidade_medida"));
				dado.setExtrato(rs.getInt("extrato_bancario"));

				try{
					dado.setValor_unitario(new BigDecimal(rs.getString("valor_unitario")));
				}catch(Exception e) {
					dado.setValor_unitario(BigDecimal.ZERO);
				}
				try{
					dado.setValor(new BigDecimal(rs.getString("valor_total")));
				}catch(Exception e) {
					dado.setValor(BigDecimal.ZERO);
				}
				dado.setData_pagamento(rs.getString("data_pagamento"));
				dado.setDescricao(rs.getString("descricao"));
				dado.setObservacao(rs.getString("observacao"));
				dado.setCaminho_arquivo(rs.getString("caminho_arquivo"));
				dado.setId_documento(rs.getInt("id_documento"));

			

				lista.add(dado);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar o Financeiro Pagamento \nErro: " + e.getMessage() + "\nCausa: " + e.getCause());// );
		}
		return lista;

	}
	
	
	public ArrayList<FinanceiroPagamentoEmprestimoCompleto> getFinanceiroPagamentosPorLancamentoParaRelatorio(int id_lancamento) {
		/*
		 * select fpag.*,
(
case
when fpag.tipo_pagador = 0
then
ib_pag.nome_instituicao_bancaria
when fpag.tipo_pagador = 1
then
(
case
when cli_pag.tipo_cliente = '0' then cli_pag.nome_empresarial 
 when cli_pag.tipo_cliente = '1' then cli_pag.nome_fantasia
end
)
end) as nome_pagador,

(
case
when fpag.tipo_recebedor = 0
then
ib_rec.nome_instituicao_bancaria
when fpag.tipo_recebedor = 1
then
(
case
when cli_rec.tipo_cliente = '0' then cli_rec.nome_empresarial 
 when cli_rec.tipo_cliente = '1' then cli_rec.nome_fantasia
end
)
end) as nome_recebedor,
cp.nome_condicao_pagamento
 from financeiro_pagamento_emprestimo fpag
left join condicao_pagamento cp on cp.id_condicao_pagamento = fpag.id_forma_pagamento
left join cliente cli_pag on cli_pag.id_cliente = fpag.id_pagador
left join instituicao_bancaria ib_pag on ib_pag.id_instituicao_bancaria = fpag.id_pagador
left join cliente cli_rec on cli_rec.id_cliente = fpag.id_recebedor
left join instituicao_bancaria ib_rec on ib_rec.id_instituicao_bancaria = fpag.id_recebedor
 where id_lancamento_pai = 332
		 */
		String select = " select fpag.*,\r\n"
				+ "(\r\n"
				+ "case\r\n"
				+ "when fpag.tipo_pagador = 0\r\n"
				+ "then\r\n"
				+ "ib_pag.nome_instituicao_bancaria\r\n"
				+ "when fpag.tipo_pagador = 1\r\n"
				+ "then\r\n"
				+ "(\r\n"
				+ "case\r\n"
				+ "when cli_pag.tipo_cliente = '0' then cli_pag.nome_empresarial \r\n"
				+ " when cli_pag.tipo_cliente = '1' then cli_pag.nome_fantasia\r\n"
				+ "end\r\n"
				+ ")\r\n"
				+ "end) as nome_pagador,\r\n"
				+ "\r\n"
				+ "(\r\n"
				+ "case\r\n"
				+ "when fpag.tipo_recebedor = 0\r\n"
				+ "then\r\n"
				+ "ib_rec.nome_instituicao_bancaria\r\n"
				+ "when fpag.tipo_recebedor = 1\r\n"
				+ "then\r\n"
				+ "(\r\n"
				+ "case\r\n"
				+ "when cli_rec.tipo_cliente = '0' then cli_rec.nome_empresarial \r\n"
				+ " when cli_rec.tipo_cliente = '1' then cli_rec.nome_fantasia\r\n"
				+ "end\r\n"
				+ ")\r\n"
				+ "end) as nome_recebedor,\r\n"
				+ "cp.nome_condicao_pagamento\r\n"
				+ " from financeiro_pagamento_emprestimo fpag\r\n"
				+ "left join condicao_pagamento cp on cp.id_condicao_pagamento = fpag.id_forma_pagamento\r\n"
				+ "left join cliente cli_pag on cli_pag.id_cliente = fpag.id_pagador\r\n"
				+ "left join instituicao_bancaria ib_pag on ib_pag.id_instituicao_bancaria = fpag.id_pagador\r\n"
				+ "left join cliente cli_rec on cli_rec.id_cliente = fpag.id_recebedor\r\n"
				+ "left join instituicao_bancaria ib_rec on ib_rec.id_instituicao_bancaria = fpag.id_recebedor\r\n"
				+ " where id_lancamento_pai = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<FinanceiroPagamentoEmprestimoCompleto> lista = new ArrayList<>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(select);
			pstm.setInt(1, id_lancamento);

			rs = pstm.executeQuery();
			while (rs.next()) {
				FinanceiroPagamentoEmprestimo dado = new FinanceiroPagamentoEmprestimo();
				
				dado.setId_pagamento(rs.getInt("id_pagamento"));
				dado.setId_lancamento(rs.getInt("id_lancamento_pai"));
				dado.setIdentificador(rs.getString("identificador"));
				dado.setId_condicao_pagamento(rs.getInt("id_forma_pagamento"));
				dado.setStatus_pagamento(rs.getInt("status_condicao_pagamento"));
				dado.setFluxo_caixa(rs.getInt("fluxo_caixa"));
				dado.setTipo_pagador(rs.getInt("tipo_pagador"));
				dado.setId_pagador(rs.getInt("id_pagador"));
				dado.setTipo_recebedor(rs.getInt("tipo_recebedor"));
				dado.setExtrato(rs.getInt("extrato_bancario"));

				dado.setId_recebedor(rs.getInt("id_recebedor"));
				dado.setObjeto(rs.getInt("objeto"));
				dado.setEspecie(rs.getString("especie"));
				dado.setQuantidade(rs.getDouble("quantidade"));
				dado.setUnidade_medida(rs.getString("unidade_medida"));

				try{
					dado.setValor_unitario(new BigDecimal(rs.getString("valor_unitario")));
				}catch(Exception e) {
					dado.setValor_unitario(BigDecimal.ZERO);
				}
				try{
					dado.setValor(new BigDecimal(rs.getString("valor_total")));
				}catch(Exception e) {
					dado.setValor(BigDecimal.ZERO);
				}
				dado.setData_pagamento(rs.getString("data_pagamento"));
				dado.setDescricao(rs.getString("descricao"));
				dado.setObservacao(rs.getString("observacao"));
				dado.setCaminho_arquivo(rs.getString("caminho_arquivo"));
				dado.setId_documento(rs.getInt("id_documento"));
				
				FinanceiroPagamentoEmprestimoCompleto fpag = new FinanceiroPagamentoEmprestimoCompleto();
				fpag.setNome_pagador(rs.getString("nome_pagador"));
				fpag.setNome_recebedor(rs.getString("nome_recebedor"));
				fpag.setNome_forma_pagamento(rs.getString("nome_condicao_pagamento"));
				fpag.setFpag(dado);

				lista.add(fpag);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar o Financeiro Pagamento \nErro: " + e.getMessage() + "\nCausa: " + e.getCause());// );
		}
		return lista;

	}


	public FinanceiroPagamentoEmprestimo getFinanceiroPagamento(int id) {

		String select = "select * from financeiro_pagamento_emprestimo where id_pagamento = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		FinanceiroPagamentoEmprestimo dado = new FinanceiroPagamentoEmprestimo();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(select);
			pstm.setInt(1, id);

			rs = pstm.executeQuery();
			rs.next();

			dado.setId_pagamento(rs.getInt("id_pagamento"));
			dado.setId_lancamento(rs.getInt("id_lancamento_pai"));
			dado.setIdentificador(rs.getString("identificador"));
			dado.setId_condicao_pagamento(rs.getInt("id_forma_pagamento"));
			dado.setStatus_pagamento(rs.getInt("status_condicao_pagamento"));
			dado.setFluxo_caixa(rs.getInt("fluxo_caixa"));
			dado.setTipo_pagador(rs.getInt("tipo_pagador"));
			dado.setId_pagador(rs.getInt("id_pagador"));
			dado.setTipo_recebedor(rs.getInt("tipo_recebedor"));
			dado.setExtrato(rs.getInt("extrato_bancario"));

			dado.setId_recebedor(rs.getInt("id_recebedor"));
			dado.setObjeto(rs.getInt("objeto"));
			dado.setEspecie(rs.getString("especie"));
			dado.setQuantidade(rs.getDouble("quantidade"));
			dado.setUnidade_medida(rs.getString("unidade_medida"));

			try{
				dado.setValor_unitario(new BigDecimal(rs.getString("valor_unitario")));
			}catch(Exception e) {
				dado.setValor_unitario(BigDecimal.ZERO);
			}
			try{
				dado.setValor(new BigDecimal(rs.getString("valor_total")));
			}catch(Exception e) {
				dado.setValor(BigDecimal.ZERO);
			}
			dado.setData_pagamento(rs.getString("data_pagamento"));
			dado.setDescricao(rs.getString("descricao"));
			dado.setObservacao(rs.getString("observacao"));
			dado.setCaminho_arquivo(rs.getString("caminho_arquivo"));
		
			dado.setId_documento(rs.getInt("id_documento"));

			return dado;

		} catch (Exception e) {
			//JOptionPane.showMessageDialog(null, "Erro ao listar a Conta id: " + id);// );
			JOptionPane.showMessageDialog(null, "Erro ao listar o Financeiro Pagamento id: " + id + " erro: " + e.getCause() + "\ncausa: " + e.getMessage());
			return null;
		}

	}

	public boolean removerFinanceiroPagamento(int id) {
		String delete = "DELETE FROM financeiro_pagamento_emprestimo WHERE id_pagamento = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(delete);

			pstm.setInt(1, id);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			JOptionPane.showMessageDialog(null, "Financeiro Pagamento Excluída, banco normalizado ");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao excluir o Financeiro Pagamento do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
							+ "dados " + f.getMessage());
			return false;
		}

	}

	public boolean atualizarFinanceiroPagamento(FinanceiroPagamentoEmprestimo dado) {
		if (dado != null) {
			try {
				Connection conn = null;
				String atualizar = null;
				PreparedStatement pstm;

				//atualizar = "update financeiro_conta set nome_conta = ?, id_grupo_contas = ?,  tipo_conta = ?, observacao = ?,descricao = ? where id_conta = ? ";
				atualizar = "update financeiro_pagamento_emprestimo set identificador = ? ,tipo_pagador = ?, id_pagador = ?, tipo_recebedor = ?, id_recebedor = ?, id_forma_pagamento = ?, "
						+ "status_condicao_pagamento = ?, "
						+ "objeto = ?,"
						+ "especie = ?,"
						+ "quantidade = ?,"
						+ "unidade_medida = ?,"
						+ "valor_unitario = ?,"
						+ "valor_total = ?, data_pagamento = ?, fluxo_caixa = ?,"
						+ " observacao = ?, descricao = ? , extrato_bancario = ? where id_pagamento =?";				
				conn = ConexaoBanco.getConexao();
				pstm = conn.prepareStatement(atualizar);
				
				pstm.setString(1, dado.getIdentificador());
				pstm.setInt(2, dado.getTipo_pagador());

				pstm.setInt(3, dado.getId_pagador());
				pstm.setInt(4, dado.getTipo_recebedor());

				pstm.setInt(5, dado.getId_recebedor());

				pstm.setInt(6, dado.getId_condicao_pagamento());
				pstm.setInt(7, dado.getStatus_pagamento());
				
				pstm.setInt(8, dado.getObjeto());

				pstm.setString(9, dado.getEspecie());

				pstm.setDouble(10, dado.getQuantidade());

				pstm.setString(11, dado.getUnidade_medida());
				try {
				pstm.setString(12, dado.getValor_unitario().toString());
				}catch(Exception e) {
					pstm.setString(12, "0");

				}
				pstm.setString(13, dado.getValor().toString());

				pstm.setString(14, dado.getData_pagamento());
				pstm.setInt(15, dado.getFluxo_caixa());

				pstm.setString(16, dado.getObservacao());
				pstm.setString(17, dado.getDescricao());
				pstm.setInt(18, dado.getExtrato());

				pstm.setInt(19, dado.getId_pagamento());

			

				pstm.execute();
				// JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
				System.out.println("Financeiro Pagamento Atualizada com sucesso");
				ConexaoBanco.fechaConexao(conn);
				return true;
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao atualizar a Financeiro Pagamento no banco de dados\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(null, "Os parametros estão vazios");
			return false;
		}
	}

	
	public ArrayList<FinanceiroPagamentoCompleto> getFinanceiroPagamentosLancamentos() {
		String select = "select * from lancamento\r\n"
				+ "left join financeiro_pagamento_emprestimos fpag on fpag.id_lancamento_pai = lancamento.id_lancamento";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<FinanceiroPagamentoCompleto> lista = new ArrayList<>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(select);

			rs = pstm.executeQuery();
			while (rs.next()) {
				
				if(rs.getInt("id_lancamento") > 0) {
				
				FinanceiroPagamento dado = new FinanceiroPagamento();
				
				dado.setId_pagamento(rs.getInt("id_pagamento"));
				dado.setId_lancamento(rs.getInt("id_lancamento_pai"));
				dado.setIdentificador(rs.getString("identificador"));
				dado.setId_condicao_pagamento(rs.getInt("id_forma_pagamento"));
				dado.setStatus_pagamento(rs.getInt("status_condicao_pagamento"));
				dado.setFluxo_caixa(rs.getInt("fluxo_caixa"));
				dado.setTipo_pagador(rs.getInt("tipo_pagador"));
				dado.setId_pagador(rs.getInt("id_pagador"));
				dado.setTipo_recebedor(rs.getInt("tipo_recebedor"));
				dado.setExtrato(rs.getInt("extrato_bancario"));

				dado.setId_recebedor(rs.getInt("id_recebedor"));
				try{
					dado.setValor(new BigDecimal(rs.getString("valor")));
				}catch(Exception e) {
					dado.setValor(BigDecimal.ZERO);
				}
				dado.setData_pagamento(rs.getString("data_pagamento"));
				dado.setDescricao(rs.getString("descricao"));
				dado.setObservacao(rs.getString("observacao"));
				dado.setCaminho_arquivo(rs.getString("caminho_arquivo"));
			
				Lancamento lancamento = new Lancamento();
				lancamento.setId_lancamento(rs.getInt("id_lancamento"));
				lancamento.setPrioridade(rs.getInt("prioridade"));
				lancamento.setTipo_lancamento(rs.getInt("tipo_lancamento"));
				lancamento.setData_lancamento(rs.getString("data_lancamento"));
				lancamento.setId_instituicao_bancaria(rs.getInt("id_instituicao_bancaria"));
				lancamento.setId_conta(rs.getInt("id_conta"));
				lancamento.setId_centro_custo(rs.getInt("id_centro_custo"));
				lancamento.setId_cliente_fornecedor(rs.getInt("id_cliente_fornecedor"));
				lancamento.setGerar_parcelas(rs.getInt("gerar_parcelas"));
				lancamento.setIntervalo(rs.getInt("intervalo"));
				lancamento.setNumero_parcelas(rs.getInt("numero_parcelas"));
				
				FinanceiroPagamentoCompleto financeiro_pagamento_lancamento = new FinanceiroPagamentoCompleto();
				financeiro_pagamento_lancamento.setLancamento(lancamento);
				financeiro_pagamento_lancamento.setFpag(dado);
				
				lista.add(financeiro_pagamento_lancamento);
				}

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar a FinanceiroPagamento \nErro: " + e.getMessage() + "\nCausa: " + e.getCause());// );
		}
		return lista;

	}
	
	public boolean atualizarArquivoDoPagamento(String caminho_arquivo, int id_pagamento) {

		try {
			Connection conn = null;
			String atualizar = null;
			PreparedStatement pstm;

			//atualizar = "update financeiro_conta set nome_conta = ?, id_grupo_contas = ?,  tipo_conta = ?, observacao = ?,descricao = ? where id_conta = ? ";
			atualizar = "update financeiro_pagamento_emprestimo set caminho_arquivo = ?  where id_pagamento = ?";
			
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(atualizar);
			pstm.setString(1, caminho_arquivo);
			pstm.setInt(2, id_pagamento);

		

			pstm.execute();
			// JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
			JOptionPane.showMessageDialog(null,"Arquivo do Pagamento Atualizado");
			ConexaoBanco.fechaConexao(conn);
			return true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao atualizar o pagamento no banco de dados\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
			return false;
		}
	
}
	
	
	public boolean atualizarIdDocumento(int id_documento, int id_pagamento) {

		try {
			Connection conn = null;
			String atualizar = null;
			PreparedStatement pstm;

			//atualizar = "update financeiro_conta set nome_conta = ?, id_grupo_contas = ?,  tipo_conta = ?, observacao = ?,descricao = ? where id_conta = ? ";
			atualizar = "update financeiro_pagamento_emprestimo set id_documento = ?  where id_pagamento = ?";
			
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(atualizar);
			pstm.setInt(1, id_documento);
			pstm.setInt(2, id_pagamento);

		

			pstm.execute();
			// JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
			JOptionPane.showMessageDialog(null,"Arquivo do Pagamento Atualizado");
			ConexaoBanco.fechaConexao(conn);
			return true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao atualizar o pagamento no banco de dados\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
			return false;
		}
	
}

	public ArrayList<FinanceiroPagamentoCompleto> getTodosFinanceiroPagamentosEmprestimos() {
		
		String select = "call consulta_extrato_todos_caixa_emprestimo_todos_pagamentos()";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<FinanceiroPagamentoCompleto> lista = new ArrayList<>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(select);

			rs = pstm.executeQuery();
			while (rs.next()) {
				
				if(rs.getInt("id_lancamento") > 0) {
				
				FinanceiroPagamentoEmprestimo dado = new FinanceiroPagamentoEmprestimo();
				
				dado.setId_pagamento(rs.getInt("id_pagamento"));
				dado.setId_lancamento(rs.getInt("id_lancamento_pai"));
				dado.setIdentificador(rs.getString("identificador"));
				dado.setId_condicao_pagamento(rs.getInt("id_forma_pagamento"));
				dado.setStatus_pagamento(rs.getInt("status_condicao_pagamento"));
				dado.setFluxo_caixa(rs.getInt("fluxo_caixa"));
				dado.setTipo_pagador(rs.getInt("tipo_pagador"));
				dado.setId_pagador(rs.getInt("id_pagador"));
				dado.setTipo_recebedor(rs.getInt("tipo_recebedor"));
				dado.setId_recebedor(rs.getInt("id_recebedor"));
				dado.setExtrato(rs.getInt("extrato_bancario"));

				try{
					dado.setValor(new BigDecimal(rs.getString("valor_total")));
				}catch(Exception e) {
					dado.setValor(BigDecimal.ZERO);
				}
				dado.setData_pagamento(rs.getString("data_pagamento"));
				dado.setDescricao(rs.getString("descricao"));
				dado.setObservacao(rs.getString("observacao"));
				dado.setCaminho_arquivo(rs.getString("caminho_arquivo"));
			
				Lancamento lancamento = new Lancamento();
				lancamento.setId_lancamento(rs.getInt("id_lancamento"));
				lancamento.setPrioridade(rs.getInt("prioridade"));
				lancamento.setTipo_lancamento(rs.getInt("tipo_lancamento"));
				lancamento.setData_lancamento(rs.getString("data_lancamento"));
				lancamento.setId_instituicao_bancaria(rs.getInt("id_instituicao_bancaria"));
				lancamento.setId_conta(rs.getInt("id_conta"));
				lancamento.setId_centro_custo(rs.getInt("id_centro_custo"));
				lancamento.setId_cliente_fornecedor(rs.getInt("id_cliente_fornecedor"));
				lancamento.setGerar_parcelas(rs.getInt("gerar_parcelas"));
				lancamento.setIntervalo(rs.getInt("intervalo"));
				lancamento.setNumero_parcelas(rs.getInt("numero_parcelas"));
				
				
				
				FinanceiroPagamentoCompleto financeiro_pagamento_lancamento = new FinanceiroPagamentoCompleto();
				financeiro_pagamento_lancamento.setLancamento(lancamento);
				financeiro_pagamento_lancamento.setFpag(dado);
				financeiro_pagamento_lancamento.setNome_forma_pagamento(rs.getString("nome_condicao_pagamento"));
				financeiro_pagamento_lancamento.setNome_pagador(rs.getString("nome_pagador"));
				financeiro_pagamento_lancamento.setNome_recebedor(rs.getString("nome_recebedor"));

				lista.add(financeiro_pagamento_lancamento);
				
				
				}

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar a Financeiro Pagamento \nErro: " + e.getMessage() + "\nCausa: " + e.getCause());// );
		}
		return lista;
	}
		
		
public ArrayList<FinanceiroPagamentoCompleto> getTodosFinanceiroPagamentosEmprestimosFiltratos(int cc, int mes, int ano, int tipo) {
		
		String select = "call consulta_emprestimo_todos_pagamentos_filtrados(?,?,?,?)";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<FinanceiroPagamentoCompleto> lista = new ArrayList<>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(select);
			pstm.setInt(1, cc);
			pstm.setInt(2, mes);

			pstm.setInt(3, ano);
			pstm.setInt(4, tipo);
			rs = pstm.executeQuery();
			while (rs.next()) {
				
				if(rs.getInt("id_lancamento") > 0) {
				
				FinanceiroPagamentoEmprestimo dado = new FinanceiroPagamentoEmprestimo();
				
				dado.setId_pagamento(rs.getInt("id_pagamento"));
				dado.setId_lancamento(rs.getInt("id_lancamento_pai"));
				dado.setIdentificador(rs.getString("identificador"));
				dado.setId_condicao_pagamento(rs.getInt("id_forma_pagamento"));
				dado.setStatus_pagamento(rs.getInt("status_condicao_pagamento"));
				dado.setFluxo_caixa(rs.getInt("fluxo_caixa"));
				dado.setTipo_pagador(rs.getInt("tipo_pagador"));
				dado.setId_pagador(rs.getInt("id_pagador"));
				dado.setTipo_recebedor(rs.getInt("tipo_recebedor"));
				dado.setId_recebedor(rs.getInt("id_recebedor"));
				dado.setExtrato(rs.getInt("extrato_bancario"));

				try{
					dado.setValor(new BigDecimal(rs.getString("valor_total")));
				}catch(Exception e) {
					dado.setValor(BigDecimal.ZERO);
				}
				dado.setData_pagamento(rs.getString("data_pagamento"));
				dado.setDescricao(rs.getString("descricao"));
				dado.setObservacao(rs.getString("observacao"));
				dado.setCaminho_arquivo(rs.getString("caminho_arquivo"));
			
				Lancamento lancamento = new Lancamento();
				lancamento.setId_lancamento(rs.getInt("id_lancamento"));
				lancamento.setPrioridade(rs.getInt("prioridade"));
				lancamento.setTipo_lancamento(rs.getInt("tipo_lancamento"));
				lancamento.setData_lancamento(rs.getString("data_lancamento"));
				lancamento.setId_instituicao_bancaria(rs.getInt("id_instituicao_bancaria"));
				lancamento.setId_conta(rs.getInt("id_conta"));
				lancamento.setId_centro_custo(rs.getInt("id_centro_custo"));
				lancamento.setId_cliente_fornecedor(rs.getInt("id_cliente_fornecedor"));
				lancamento.setGerar_parcelas(rs.getInt("gerar_parcelas"));
				lancamento.setIntervalo(rs.getInt("intervalo"));
				lancamento.setNumero_parcelas(rs.getInt("numero_parcelas"));
				
				
				
				FinanceiroPagamentoCompleto financeiro_pagamento_lancamento = new FinanceiroPagamentoCompleto();
				financeiro_pagamento_lancamento.setLancamento(lancamento);
				financeiro_pagamento_lancamento.setFpag(dado);
				financeiro_pagamento_lancamento.setNome_forma_pagamento(rs.getString("nome_condicao_pagamento"));
				financeiro_pagamento_lancamento.setNome_pagador(rs.getString("nome_pagador"));
				financeiro_pagamento_lancamento.setNome_recebedor(rs.getString("nome_recebedor"));

				lista.add(financeiro_pagamento_lancamento);
				
				
				}

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar a Financeiro Pagamento \nErro: " + e.getMessage() + "\nCausa: " + e.getCause());// );
		}
		return lista;
	}
		
		
		
		
		
		public ArrayList<FinanceiroPagamentoCompleto> getFinanceiroPagamentosLancamentosPorCaixa( int id_instituicao_bancaria) {
			/*	String select = "select * from lancamento\r\n"
						+ "left join financeiro_pagamento fpag on fpag.id_lancamento_pai = lancamento.id_lancamento "
						+ " where  (tipo_pagador = 0 and id_pagador = ?) or (tipo_recebedor = 0 and id_recebedor = ? )";
				*/
			/*	String select = "select lancamento.*,fpag.*,\r\n"
						+ "date_format(str_to_date(fpag.data_pagamento, '%d/%m/%Y'), '%Y-%m-%d') as data_pg\r\n"
						+ " from lancamento\r\n"
						+ "left join financeiro_pagamento fpag on fpag.id_lancamento_pai = lancamento.id_lancamento \r\n"
						+ " where  (tipo_pagador = 0 and id_pagador = ?) or (tipo_recebedor = 0 and id_recebedor = ? )\r\n"
						+ " order BY STR_TO_DATE(data_pg, '%d/%m/%Y') ASC";
				*/
				/*String select  = "select lancamento.*,fpag.*,cp.*\r\n"
						+ " from lancamento\r\n"
						+ "left join financeiro_pagamento fpag on fpag.id_lancamento_pai = lancamento.id_lancamento \r\n"
						+ "left join condicao_pagamento cp on cp.id_condicao_pagamento = fpag.id_forma_pagamento\r\n"
						+ " where  (tipo_pagador = 0 and id_pagador = ?) or (tipo_recebedor = 0 and id_recebedor = ? )\r\n"
						+ " order BY STR_TO_DATE(fpag.data_pagamento, '%d/%m/%Y') ASC";
				*/
				String select = "call consulta_extrato_por_caixa_emprestimo(?)";
				Connection conn = null;
				PreparedStatement pstm = null;
				ResultSet rs = null;
				ArrayList<FinanceiroPagamentoCompleto> lista = new ArrayList<>();
				try {
					conn = ConexaoBanco.getConexao();
					pstm = conn.prepareStatement(select);
					pstm.setInt(1, id_instituicao_bancaria);

					rs = pstm.executeQuery();
					while (rs.next()) {
						
						if(rs.getInt("id_lancamento") > 0) {
						
						FinanceiroPagamento dado = new FinanceiroPagamento();
						
						dado.setId_pagamento(rs.getInt("id_pagamento"));
						dado.setId_lancamento(rs.getInt("id_lancamento_pai"));
						dado.setIdentificador(rs.getString("identificador"));
						dado.setId_condicao_pagamento(rs.getInt("id_forma_pagamento"));
						dado.setStatus_pagamento(rs.getInt("status_condicao_pagamento"));
						dado.setFluxo_caixa(rs.getInt("fluxo_caixa"));
						dado.setTipo_pagador(rs.getInt("tipo_pagador"));
						dado.setId_pagador(rs.getInt("id_pagador"));
						dado.setTipo_recebedor(rs.getInt("tipo_recebedor"));
						dado.setId_recebedor(rs.getInt("id_recebedor"));
						dado.setExtrato(rs.getInt("extrato_bancario"));
						

						try{
							dado.setValor(new BigDecimal(rs.getString("valor_total_emprestimo")));
						}catch(Exception e) {
							dado.setValor(BigDecimal.ZERO);
						}
						dado.setData_pagamento(rs.getString("data_pagamento"));
						dado.setDescricao(rs.getString("descricao"));
						dado.setObservacao(rs.getString("observacao"));
						dado.setCaminho_arquivo(rs.getString("caminho_arquivo"));
					
						Lancamento lancamento = new Lancamento();
						lancamento.setId_lancamento(rs.getInt("id_lancamento"));
						lancamento.setPrioridade(rs.getInt("prioridade"));
						lancamento.setTipo_lancamento(rs.getInt("tipo_lancamento"));
						lancamento.setData_lancamento(rs.getString("data_lancamento"));
						lancamento.setId_instituicao_bancaria(rs.getInt("id_instituicao_bancaria"));
						lancamento.setId_conta(rs.getInt("id_conta"));
						lancamento.setId_centro_custo(rs.getInt("id_centro_custo"));
						lancamento.setId_cliente_fornecedor(rs.getInt("id_cliente_fornecedor"));
						lancamento.setGerar_parcelas(rs.getInt("gerar_parcelas"));
						lancamento.setIntervalo(rs.getInt("intervalo"));
						lancamento.setNumero_parcelas(rs.getInt("numero_parcelas"));
						
						FinanceiroPagamentoCompleto financeiro_pagamento_lancamento = new FinanceiroPagamentoCompleto();
						financeiro_pagamento_lancamento.setLancamento(lancamento);
						financeiro_pagamento_lancamento.setFpag(dado);
						financeiro_pagamento_lancamento.setNome_forma_pagamento(rs.getString("nome_condicao_pagamento"));
						financeiro_pagamento_lancamento.setNome_pagador(rs.getString("nome_pagador"));
						financeiro_pagamento_lancamento.setNome_recebedor(rs.getString("nome_recebedor"));

						lista.add(financeiro_pagamento_lancamento);
						
						
						}

					}
					ConexaoBanco.fechaConexao(conn, pstm, rs);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Erro ao listar a FinanceiroPagamento \nErro: " + e.getMessage() + "\nCausa: " + e.getCause());// );
				}
				return lista;

			}
			
		
		

	
	
	

	
}
