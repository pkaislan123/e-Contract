/*
 * Decompiled with CFR 0.151.
 */
package main.java.conexaoBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import main.java.cadastros.CadastroDocumento;
import main.java.conexaoBanco.ConexaoBanco;

public class GerenciarBancoDocumento {
    public int inserir_documento_padrao(CadastroDocumento doc) {
        System.out.println("Inserir Documento foi chamado!");
        int result = -1;
        int id_cliente = -1;
        String sql_cadastro_documento = "insert into documento (nome, descricao, tipo, id_pai, nome_arquivo, id_contrato_pai) values ('" + doc.getNome() + "','" + doc.getDescricao() + "','" + doc.getTipo() + "','" + doc.getId_pai() + "','" + doc.getNome_arquivo() + "','" + doc.getId_contrato_pai() + "')";
        try {
            Connection conn = ConexaoBanco.getConexao();
            Statement stmt = conn.createStatement();
            int numero = stmt.executeUpdate(sql_cadastro_documento, 1);
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                result = rs.getInt(1);
                System.out.println("Id Cliente inserido: " + result);
            }
            rs.close();
            stmt.close();
            return result;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir o documento no banco dedados\n Erro: ");
            return -1;
        }
    }

    public int inserir_documento_padrao_cliente(CadastroDocumento doc) {
        System.out.println("Inserir Documento foi chamado!");
        int result = -1;
        int id_cliente = -1;
        String sql_cadastro_documento = "insert into documento (nome, descricao, tipo, id_pai, nome_arquivo, id_cliente) values ('" + doc.getNome() + "','" + doc.getDescricao() + "','" + doc.getTipo() + "','" + doc.getId_pai() + "','" + doc.getNome_arquivo() + "','" + doc.getId_cliente() + "')";
        try {
            Connection conn = ConexaoBanco.getConexao();
            Statement stmt = conn.createStatement();
            int numero = stmt.executeUpdate(sql_cadastro_documento, 1);
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                result = rs.getInt(1);
                System.out.println("Id Cliente inserido: " + result);
            }
            rs.close();
            stmt.close();
            return result;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir o documento no banco dedados\n Erro: ");
            return -1;
        }
    }

    public int inserir_documento_padrao_anotacao(CadastroDocumento doc) {
        System.out.println("Inserir Documento foi chamado!");
        int result = -1;
        int id_cliente = -1;
        String sql_cadastro_documento = "insert into documento (nome, descricao, tipo, id_pai, nome_arquivo, id_cliente, sub_pasta) values ('" + doc.getNome() + "','" + doc.getDescricao() + "','" + doc.getTipo() + "','" + doc.getId_pai() + "','" + doc.getNome_arquivo() + "','" + doc.getId_cliente() + "','" + doc.getSub_pasta() + "')";
        try {
            Connection conn = ConexaoBanco.getConexao();
            Statement stmt = conn.createStatement();
            int numero = stmt.executeUpdate(sql_cadastro_documento, 1);
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                result = rs.getInt(1);
                System.out.println("Id Cliente inserido: " + result);
            }
            rs.close();
            stmt.close();
            return result;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir o documento no banco dedados\n Erro: ");
            return -1;
        }
    }

    public int inserir_documento_padrao_ib(CadastroDocumento doc) {
        System.out.println("Inserir Documento foi chamado!");
        int result = -1;
        int id_cliente = -1;
        String sql_cadastro_documento = "insert into documento (nome, descricao, tipo, id_pai, nome_arquivo, id_ib) values ('" + doc.getNome() + "','" + doc.getDescricao() + "','" + doc.getTipo() + "','" + doc.getId_pai() + "','" + doc.getNome_arquivo() + "','" + doc.getId_ib() + "')";
        try {
            Connection conn = ConexaoBanco.getConexao();
            Statement stmt = conn.createStatement();
            int numero = stmt.executeUpdate(sql_cadastro_documento, 1);
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                result = rs.getInt(1);
                System.out.println("Id Cliente inserido: " + result);
            }
            rs.close();
            stmt.close();
            return result;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir o documento no banco dados\n Erro: " + e.getMessage() + "\nCausa: " + e.getCause());
            return -1;
        }
    }

    public int inserir_documento_padrao_funcionario(CadastroDocumento doc) {
        System.out.println("Inserir Documento foi chamado!");
        int result = -1;
        int id_cliente = -1;
        String sql_cadastro_documento = "insert into documento (nome, descricao, tipo, id_pai, nome_arquivo, id_funcionario) values ('" + doc.getNome() + "','" + doc.getDescricao() + "','" + doc.getTipo() + "','" + doc.getId_pai() + "','" + doc.getNome_arquivo() + "','" + doc.getId_funcionario() + "')";
        try {
            Connection conn = ConexaoBanco.getConexao();
            Statement stmt = conn.createStatement();
            int numero = stmt.executeUpdate(sql_cadastro_documento, 1);
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                result = rs.getInt(1);
                System.out.println("Id Cliente inserido: " + result);
            }
            rs.close();
            stmt.close();
            return result;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir o documento no banco dados\n Erro: " + e.getMessage() + "\nCausa: " + e.getCause());
            return -1;
        }
    }

    public int inserirDocumentoLancamento(CadastroDocumento doc) {
        System.out.println("Inserir Documento foi chamado!");
        int result = -1;
        int id_cliente = -1;
        String sql_cadastro_documento = "insert into documento (nome, descricao, tipo, id_pai, nome_arquivo, id_lancamento) values ('" + doc.getNome() + "','" + doc.getDescricao() + "','" + doc.getTipo() + "','" + doc.getId_pai() + "','" + doc.getNome_arquivo() + "','" + doc.getId_lancamento() + "')";
        try {
            Connection conn = ConexaoBanco.getConexao();
            Statement stmt = conn.createStatement();
            int numero = stmt.executeUpdate(sql_cadastro_documento, 1);
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                result = rs.getInt(1);
            }
            return result;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir o documento no banco dedados\n Erro: ");
            return -1;
        }
    }

    public ArrayList<CadastroDocumento> getDocumentos(int id_contrato) {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String select_documentos = "select * from documento where id_contrato_pai = ?";
        ArrayList<CadastroDocumento> listaDocs = new ArrayList<CadastroDocumento>();
        try {
            conn = ConexaoBanco.getConexao();
            pstm = conn.prepareStatement(select_documentos);
            pstm.setInt(1, id_contrato);
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
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar documentos");
        }
        return listaDocs;
    }

    public ArrayList<CadastroDocumento> getDocumentosPorPai(int id_pai, int id_contrato_pai) {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String select_documentos = "select * from documento where id_pai = ? and id_contrato_pai = ?";
        ArrayList<CadastroDocumento> listaDocs = new ArrayList<CadastroDocumento>();
        try {
            conn = ConexaoBanco.getConexao();
            pstm = conn.prepareStatement(select_documentos);
            pstm.setInt(1, id_pai);
            pstm.setInt(2, id_contrato_pai);
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
            return listaDocs;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar documentos anexados ao id_pai: " + id_pai + " ");
            return null;
        }
    }

    public ArrayList<CadastroDocumento> getDocumentosPorPagamento(int id_pai, int id_lancamento) {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String select_documentos = "select * from documento where id_pai = ? and id_lancamento = ?";
        ArrayList<CadastroDocumento> listaDocs = new ArrayList<CadastroDocumento>();
        try {
            conn = ConexaoBanco.getConexao();
            pstm = conn.prepareStatement(select_documentos);
            pstm.setInt(1, id_pai);
            pstm.setInt(2, id_lancamento);
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
            return listaDocs;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar documentos anexados ao id_pai: " + id_pai + " ");
            return null;
        }
    }

    public ArrayList<CadastroDocumento> getDocumentosCliente(int id_cliente) {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String select_documentos = "select * from documento where id_cliente = ?";
        ArrayList<CadastroDocumento> listaDocs = new ArrayList<CadastroDocumento>();
        try {
            conn = ConexaoBanco.getConexao();
            pstm = conn.prepareStatement(select_documentos);
            pstm.setInt(1, id_cliente);
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
                doc.setSub_pasta(rs.getString("sub_pasta"));
                doc.setBloqueado(rs.getInt("bloqueado"));
                listaDocs.add(doc);
            }
            ConexaoBanco.fechaConexao(conn, pstm, rs);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar documentos");
        }
        return listaDocs;
    }

    public CadastroDocumento getDocumentoPorId(int id_doc) {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String select_documentos = "select * from documento where id_documento = ?";
        CadastroDocumento doc = new CadastroDocumento();
        try {
            conn = ConexaoBanco.getConexao();
            pstm = conn.prepareStatement(select_documentos);
            pstm.setInt(1, id_doc);
            rs = pstm.executeQuery();
            if (rs.next()) {
                doc.setId_documento(rs.getInt("id_documento"));
                doc.setNome(rs.getString("nome"));
                doc.setDescricao(rs.getString("descricao"));
                doc.setTipo(rs.getInt("tipo"));
                doc.setId_pai(rs.getInt("id_pai"));
                doc.setNome_arquivo(rs.getString("nome_arquivo"));
                doc.setId_cliente(rs.getInt("id_cliente"));
                doc.setSub_pasta(rs.getString("sub_pasta"));
                doc.setBloqueado(rs.getInt("bloqueado"));
            }
            ConexaoBanco.fechaConexao(conn, pstm, rs);
            return doc;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar documentos");
            return null;
        }
    }

    public ArrayList<CadastroDocumento> getDocumentosPorFuncionario(int id_funcionario) {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String select_documentos = "select * from documento where id_funcionario = ?";
        ArrayList<CadastroDocumento> listaDocs = new ArrayList<CadastroDocumento>();
        try {
            conn = ConexaoBanco.getConexao();
            pstm = conn.prepareStatement(select_documentos);
            pstm.setInt(1, id_funcionario);
            rs = pstm.executeQuery();
            while (rs.next()) {
                CadastroDocumento doc = new CadastroDocumento();
                doc.setId_documento(rs.getInt("id_documento"));
                doc.setNome(rs.getString("nome"));
                doc.setDescricao(rs.getString("descricao"));
                doc.setTipo(rs.getInt("tipo"));
                doc.setId_pai(rs.getInt("id_pai"));
                doc.setNome_arquivo(rs.getString("nome_arquivo"));
                doc.setId_funcionario(rs.getInt("id_funcionario"));
                listaDocs.add(doc);
            }
            ConexaoBanco.fechaConexao(conn, pstm, rs);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar documentos");
        }
        return listaDocs;
    }

    public ArrayList<CadastroDocumento> getDocumentosPorIb(int id_ib) {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String select_documentos = "select * from documento where id_ib = ?";
        ArrayList<CadastroDocumento> listaDocs = new ArrayList<CadastroDocumento>();
        try {
            conn = ConexaoBanco.getConexao();
            pstm = conn.prepareStatement(select_documentos);
            pstm.setInt(1, id_ib);
            rs = pstm.executeQuery();
            while (rs.next()) {
                CadastroDocumento doc = new CadastroDocumento();
                doc.setId_documento(rs.getInt("id_documento"));
                doc.setNome(rs.getString("nome"));
                doc.setDescricao(rs.getString("descricao"));
                doc.setTipo(rs.getInt("tipo"));
                doc.setId_pai(rs.getInt("id_pai"));
                doc.setNome_arquivo(rs.getString("nome_arquivo"));
                doc.setId_ib(rs.getInt("id_ib"));
                listaDocs.add(doc);
            }
            ConexaoBanco.fechaConexao(conn, pstm, rs);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar documentos");
        }
        return listaDocs;
    }

    public ArrayList<CadastroDocumento> getDocumentosLancamento(int id_cliente) {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String select_documentos = "select * from documento where id_lancamento = ?";
        ArrayList<CadastroDocumento> listaDocs = new ArrayList<CadastroDocumento>();
        try {
            conn = ConexaoBanco.getConexao();
            pstm = conn.prepareStatement(select_documentos);
            pstm.setInt(1, id_cliente);
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
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar documentos");
        }
        return listaDocs;
    }

    public boolean removerDocumento(int id_documento) {
        String sql_delete_documento = "DELETE FROM documento WHERE id_documento = ?";
        Connection conn = null;
        Object rs = null;
        try {
            conn = ConexaoBanco.getConexao();
            PreparedStatement pstm = conn.prepareStatement(sql_delete_documento);
            pstm.setInt(1, id_documento);
            pstm.execute();
            ConexaoBanco.fechaConexao(conn, pstm);
            JOptionPane.showMessageDialog(null, "Documento Excluido, banco normalizado ");
            return true;
        }
        catch (Exception f) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir o documento do banco dedados " + f.getMessage());
            return false;
        }
    }

    public boolean bloquearDocumento(int id_documento) {
        Connection conn = null;
        String atualizar = null;
        try {
            atualizar = "update documento set bloqueado = ? where id_documento = ?";
            conn = ConexaoBanco.getConexao();
            PreparedStatement pstm = conn.prepareStatement(atualizar);
            pstm.setInt(1, 1);
            pstm.setInt(2, id_documento);
            pstm.execute();
            System.out.println("Documento Atualizado com sucesso");
            ConexaoBanco.fechaConexao(conn);
            return true;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao bloquear documento no banco de dados");
            return false;
        }
    }

    public boolean desbloquearDocumento(int id_documento) {
        Connection conn = null;
        String atualizar = null;
        try {
            atualizar = "update documento set bloqueado = ? where id_documento = ?";
            conn = ConexaoBanco.getConexao();
            PreparedStatement pstm = conn.prepareStatement(atualizar);
            pstm.setInt(1, 0);
            pstm.setInt(2, id_documento);
            pstm.execute();
            System.out.println("Documento Desbloqueado com sucesso");
            ConexaoBanco.fechaConexao(conn);
            return true;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao desbloquear documento no banco de dados");
            return false;
        }
    }
}

