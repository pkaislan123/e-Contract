package main.java.conexaoBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import main.java.cadastros.CadastroProduto;
import main.java.cadastros.RegistroPonto;
import main.java.cadastros.CadastroCotacao;

public class GerenciarBancoCotacao {

	/*
	 * `id_cotacao` int(5) NOT NULL AUTO_INCREMENT, `id_produto` int(5) NOT NULL,
	 * `unidade` varchar(50), `quantidade` double DEFAULT NULL, `valor` double
	 * DEFAULT NULL, `data_hora` datetime DEFAULT NULL, `localidade` varchar(100),
	 * `indicador` varchar(100),
	 * 
	 */

	public boolean inserir_cotacao(CadastroCotacao cotacao) {
		Connection conn = null;
		try {
			conn = ConexaoBanco.getConexao();
			String sql = "insert into cotacao\r\n"
					+ "(id_produto , unidade, quantidade, valor, data_hora, localidade, indicador,medida) values ('"
					+ cotacao.getProduto().getId_produto() + "','" + cotacao.getUnidade() + "','"
					+ cotacao.getQuantidade() + "','" + cotacao.getValor() + "','" + LocalDateTime.now() + "','"
					+ cotacao.getLocalidade() + "','" + cotacao.getIndicador() + "','" + cotacao.getMedida() + "')";

			PreparedStatement grava = (PreparedStatement) conn.prepareStatement(sql);
			grava.execute();
			ConexaoBanco.fechaConexao(conn, grava);
			return true;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao inserir a cotacao no banco de dados, Erro" + e.getMessage() + "\nCausa:" + e.getCause());
			return false;
		}

	}

	public ArrayList<CadastroCotacao> getCotacoes() {

		String selectCotacoes = "select * from cotacao\r\n"
				+ "left join produto pd on pd.id_produto = cotacao.id_produto";

		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroCotacao> listacotacaos = new ArrayList<CadastroCotacao>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectCotacoes);
			rs = pstm.executeQuery();
			while (rs.next()) {
				CadastroCotacao cotacao = new CadastroCotacao();
				CadastroProduto produto = new CadastroProduto();

				produto.setId_produto(rs.getInt("id_produto"));
				produto.setNome_produto(rs.getString("nome_produto"));
				produto.setTransgenia(rs.getString("transgenia"));
				cotacao.setProduto(produto);

				cotacao.setUnidade(rs.getString("unidade"));
				cotacao.setId_cotacao(rs.getInt("id_cotacao"));
				;
				cotacao.setLocalidade(rs.getString("localidade"));
				cotacao.setQuantidade(rs.getDouble("quantidade"));
				cotacao.setValor(rs.getDouble("valor"));
				cotacao.setIndicador(rs.getString("indicador"));
				cotacao.setMedida(rs.getString("medida"));

				try {
					// hora saida
					String data_hora = rs.getString("data_hora");
					Date data_hora_saida = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(data_hora);
					cotacao.setData(data_hora_saida);
				} catch (Exception e) {

				}

				listacotacaos.add(cotacao);
			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			// JOptionPane.showMessageDialog(null, "Erro ao listar cotacaos" );
		}
		return listacotacaos;
	}

	public CadastroCotacao getcotacao(int id) {
		String selectcotacao = "select * from cotacao where id_cotacao = ?";

		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectcotacao);
			pstm.setInt(1, id);
			rs = pstm.executeQuery();
			rs.next();

			CadastroCotacao cotacao = new CadastroCotacao();
			CadastroProduto produto = new CadastroProduto();

			produto.setId_produto(rs.getInt("id_produto"));
			produto.setNome_produto(rs.getString("nome_produto"));
			produto.setTransgenia(rs.getString("transgenia"));
			cotacao.setProduto(produto);

			cotacao.setUnidade(rs.getString("unidade"));
			cotacao.setId_cotacao(rs.getInt("id_cotacao"));
			;
			cotacao.setLocalidade(rs.getString("localidade"));
			cotacao.setQuantidade(rs.getDouble("quantidade"));
			cotacao.setValor(rs.getDouble("valor"));
			cotacao.setIndicador(rs.getString("indicador"));
			cotacao.setMedida(rs.getString("medida"));

			try {
				// hora saida
				String data_hora = rs.getString("data_hora");
				Date data_hora_saida = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(data_hora);
				cotacao.setData(data_hora_saida);
			} catch (Exception e) {

			}

			return cotacao;

		} catch (Exception e) {
			// JOptionPane.showMessageDialog(null, "Erro ao buscar por cotacao id: " + id +
			// " erro: " );
			return null;
		}

	}

	public boolean excluirCotacao(int id_cotacao) {
		String excluirCotacao = "delete from cotacao where id_cotacao = ?";

		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(excluirCotacao);

			pstm.setInt(1, id_cotacao);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao excluir a cotação do banco de dados\nConsulte o administrador do sistema" + "dados "
							+ f.getMessage());
			return false;
		}

	}

	public boolean atualizar_cotacao(CadastroCotacao cotacao) {
		try {
			Connection conn = null;
			String atualizar = null;
			PreparedStatement pstm;

			atualizar = "update cotacao set indicador = ?, localidade = ?, medida = ?,  quantidade = ? , unidade = ?, valor = ?, id_produto = ? where id_cotacao = ? ";
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(atualizar);

			pstm.setString(1, cotacao.getIndicador());
			pstm.setString(2, cotacao.getLocalidade());
			pstm.setString(3, cotacao.getMedida());
			pstm.setDouble(4, cotacao.getQuantidade());
			pstm.setString(5, cotacao.getUnidade());
			pstm.setDouble(6, cotacao.getValor());
			pstm.setInt(7, cotacao.getProduto().getId_produto());

			pstm.setInt(8, cotacao.getId_cotacao());


			pstm.execute();
			// JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
			System.out.println("Registro Ponto Atualizada com sucesso");
			ConexaoBanco.fechaConexao(conn);
			return true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao atualizar a cotação no banco de dados\nErro: "
					+ e.getMessage() + "\nCausa: " + e.getCause());
			return false;
		}
	}

}
