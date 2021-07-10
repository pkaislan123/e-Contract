package main.java.conexaoBanco;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import main.java.cadastros.CadastroBaseDados;
import main.java.cadastros.CadastroLogin;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.outros.DadosGlobais;
import main.java.tratamento_proprio.Log;

public class ConexaoBanco {

	private static Log GerenciadorLog;
	private static CadastroLogin login;
	private static ConfiguracoesGlobais configs_globais;

	// public static Connection connection;
	private static String url;

	private static CadastroBaseDados bd;

	public static Connection getConexao() throws SQLException, ClassNotFoundException {
		try {
			getDadosGlobais();

			bd = configs_globais.getBaseDados();

			url = "jdbc:mysql://" + bd.getHost() + ":" + bd.getPorta() + "/" + bd.getNome_banco()
					+ "?useTimezone=false";

			// base nuvem
			// Connection connection = (Connection)
			// DriverManager.getConnection("jdbc:mysql://4.tcp.ngrok.io:16461/bd_ldarmazens?useTimezone=true&serverTimezone=UTC",
			// "root", "1234");

			// base raspberry
			// Connection connection = (Connection)
			// DriverManager.getConnection("jdbc:mysql://DESKTOP-K8RASOB/bd_ldarmazens?useTimezone=true&serverTimezone=UTC",
			// "root", "1234");

			// base local

			Connection connection = (Connection) DriverManager.getConnection(url, bd.getNome_usuario(), bd.getSenha());

			return connection;

		} catch (SQLException e) {
			System.out.println("erro na conexao com o bd");
			/*
			 * JOptionPane.showMessageDialog(null,
			 * "Erro ao se conectar no banco de dados:\nErro: " + e.getMessage()+
			 * "\nCausa: " + e.getCause()
			 * 
			 * );
			 */
			throw new SQLException("Erro ao conectar " + "com a base de dados" + e.getMessage());
		}

	}

	public static void fechaConexao(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
				System.out.println("Fechada a conexao com o banco de dados");
			}

		} catch (Exception e) {
			System.out.println("Nao foi possivel fechar  a conexao com o banco de dados " + e.getMessage());
		}
	}

	public static void fechaConexao(Connection conn, PreparedStatement stmt) {

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

	public static void encerrar(Connection conn) {

		try {
			if (conn != null) {
				fechaConexao(conn);
			}

		} catch (Exception e) {
			System.out.println("nao foi possivel fechar o statement " + e.getMessage());
		}
	}

	public static void fechaConexao(Connection conn, PreparedStatement stmt, ResultSet rs) {

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

	public ConexaoBanco() {

	}

	public static void getDadosGlobais() {
		// gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		GerenciadorLog = dados.getGerenciadorLog();
		configs_globais = dados.getConfigs_globais();

		// usuario logado
		login = dados.getLogin();

	}

}
