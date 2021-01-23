package manipular;

import java.io.IOException;

import javax.swing.JOptionPane;

import org.apache.commons.io.FilenameUtils;

import cadastros.CadastroContrato;
import cadastros.CadastroDocumento;
import cadastros.CadastroLogin;
import conexaoBanco.GerenciarBancoContratos;
import outros.DadosGlobais;
import tratamento_proprio.Log;

public class CopiarArquivo {

	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private String nome_official;
	private CadastroDocumento doc_local_original;
	private CadastroContrato sub_local;
	private int id_pai_local;
	
	public CopiarArquivo(CadastroDocumento doc_original, int id_pai, CadastroContrato sub) {
		getDadosGlobais();
		this.id_pai_local = id_pai;
		this.sub_local = sub;
		this.doc_local_original = doc_original;
	}
	
	
	public String copiar_pagamento(){

	GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();	
	CadastroContrato contrato_local = gerenciar.getContrato(doc_local_original.getId_contrato_pai());
	
	String diretorio_original = contrato_local.getCaminho_diretorio_contrato();
	String nome_arquivo_original =  doc_local_original.getNome_arquivo();
	
	String extensaoDoArquivo = FilenameUtils.getExtension(nome_arquivo_original);

	
	String unidade_base_dados = configs_globais.getServidorUnidade();
	String caminho_completo_origem = unidade_base_dados + "\\" + diretorio_original + "\\" + "comprovantes\\" + nome_arquivo_original;

	//relializar a copia
	String nome_arquivo_destino = "comprovante_pagamento_" + id_pai_local + "_" + sub_local.getCodigo() + "." + extensaoDoArquivo;
	String caminho_completo_destino = unidade_base_dados + "\\" + sub_local.getCaminho_diretorio_contrato() + "\\" + "comprovantes\\" + nome_arquivo_destino;
	
	JOptionPane.showMessageDialog(null, "origem: "  + caminho_completo_origem + "\nDestino: " + caminho_completo_destino);
	ManipularTxt manipular = new ManipularTxt();
	try {
		boolean copiado = manipular.copiarNFe(caminho_completo_origem, caminho_completo_destino);
	    if(copiado) {
	    	JOptionPane.showMessageDialog(null, "Arquivo fisico replicado");
	    	return  nome_arquivo_destino;
	    	
	    }else {
	    	JOptionPane.showMessageDialog(null, "Não foi possivel copiar o arquivo fisico\nApesar de não ter corrompido o banco de dados\nproblemas de arquivos duplicados podem ser encontrados\nConsulte o administrador");
            return null;
	    }
	  
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
	}
	
	
	
	
	
	
	}
	
	
	
	
	public String copiar_carregamento(){

		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();	
		CadastroContrato contrato_local = gerenciar.getContrato(doc_local_original.getId_contrato_pai());
		
		String diretorio_original = contrato_local.getCaminho_diretorio_contrato();
		String nome_arquivo_original =  doc_local_original.getNome_arquivo();
		
		String extensaoDoArquivo = FilenameUtils.getExtension(nome_arquivo_original);

		
		String unidade_base_dados = configs_globais.getServidorUnidade();
		String caminho_completo_origem = unidade_base_dados + "\\" + diretorio_original + "\\" + "comprovantes\\" + nome_arquivo_original;

		//relializar a copia
		String nome_arquivo_destino = "comprovante_carregamento_" + id_pai_local + "_" + sub_local.getCodigo() + "." + extensaoDoArquivo;
		String caminho_completo_destino = unidade_base_dados + "\\" + sub_local.getCaminho_diretorio_contrato() + "\\" + "comprovantes\\" + nome_arquivo_destino;
		
		JOptionPane.showMessageDialog(null, "origem: "  + caminho_completo_origem + "\nDestino: " + caminho_completo_destino);
		ManipularTxt manipular = new ManipularTxt();
		try {
			boolean copiado = manipular.copiarNFe(caminho_completo_origem, caminho_completo_destino);
		    if(copiado) {
		    	JOptionPane.showMessageDialog(null, "Arquivo fisico replicado");
		    	return  nome_arquivo_destino;
		    	
		    }else {
		    	JOptionPane.showMessageDialog(null, "Não foi possivel copiar o arquivo fisico\nApesar de não ter corrompido o banco de dados\nproblemas de arquivos duplicados podem ser encontrados\nConsulte o administrador");
	            return null;
		    }
		  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
		
		
		
		
		}
		
		
		
	
	
	
	
	
	
	
	
	public void getDadosGlobais() {
		//gerenciador de log
				DadosGlobais dados = DadosGlobais.getInstance();
				 GerenciadorLog = dados.getGerenciadorLog();
				 configs_globais = dados.getConfigs_globais();
				 
				 //usuario logado
				  login = dados.getLogin();
		
	}
	
}
