package manipular;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;

import javax.swing.JOptionPane;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxDownloader;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.DeleteResult;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.GetTemporaryLinkErrorException;
import com.dropbox.core.v2.files.GetTemporaryLinkResult;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.sharing.CreateSharedLinkWithSettingsErrorException;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;
import com.dropbox.core.v2.users.FullAccount;

import cadastros.CadastroLogin;
import cadastros.CadastroNuvem;
import cadastros.CadastroZapMessenger;
import outros.DadosGlobais;

public class Nuvem {

	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	
    private CadastroNuvem nuvem;

    DbxClientV2 client;
    
	public Nuvem() {
		getDadosGlobais();
	}
	
	
	public boolean carregar(String url, String nomearquivo) {
		try (InputStream in = new FileInputStream(url)) {
		    FileMetadata metadata = client.files().uploadBuilder("/contratos/" + nomearquivo )
		        .uploadAndFinish(in);
		    System.out.println("sucesso");
		    return true;
		}catch(Exception e) {
		    System.out.println("erro ao carregar o arquivo: " + e.getMessage());
            return false;
		}
	}
	
	public void abrir() {
		 // Criar cliente Dropbox 
		// Create Dropbox client
        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
        client = new DbxClientV2(config, nuvem.getToken());
		
		
	}
	
	public void testar() {
		FullAccount account = null;
		try {
			account = client.users().getCurrentAccount();
		} catch (DbxApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DbxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(account.getName().getDisplayName());
		
		
	}
	
	
	public void listar() {
		
		try{
			ListFolderResult result = client.files().listFolder("/contratos/");
			while (true) {
			    for (Metadata metadata : result.getEntries()) {
			        System.out.println(metadata.getPathLower());
			    }

			    if (!result.getHasMore()) {
			        break;
			    }

			    result = client.files().listFolderContinue(result.getCursor());
			}
		}catch(Exception e) {
			
		}
	}
	
	public String getUrlArquivo(String nome_arquivo_nuvem) {
		try {
			GetTemporaryLinkResult link = client.files().getTemporaryLink( nome_arquivo_nuvem);
			return link.getLink();
		} catch (GetTemporaryLinkErrorException e) {
			System.out.println("erro get link : " + e.getMessage());
			e.printStackTrace();
			return null;

		} catch (DbxException e) {
			System.out.println("erro get link : " + e.getMessage());

			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;

		}
		
	}
	
	public String getUrlShare(String nome_arquivo_nuvem) {
		try {
		    SharedLinkMetadata sharedLinkMetadata = client.sharing().createSharedLinkWithSettings(nome_arquivo_nuvem);
		   // System.out.println(sharedLinkMetadata.getUrl());
		    return (sharedLinkMetadata.getUrl());
		} catch (CreateSharedLinkWithSettingsErrorException ex) {
		    System.out.println("erro ao pegar link compartilhado" + ex);
		    
		    return null;
		} catch (DbxException ex) {
		    System.out.println("erro ao pegar link compartilhado" + ex);
		    return null;

		}
	}
	
	public boolean deletarArquivo(String nome_arquivo) {
		try {
		DeleteResult  apagar = client.files().deleteV2("/contratos/" + nome_arquivo);
		System.out.println("resposta ao apagar o arquivo: " + apagar.toString());
		
		return true;
		}catch(Exception e) {
			System.out.println("falha ao deleter o arquivo da nuvem, erro: " + e.getMessage());
			return false;
		}
	}
	
	public void getDadosGlobais() {
		//gerenciador de log
				DadosGlobais dados = DadosGlobais.getInstance();
				 configs_globais = dados.getConfigs_globais();
				 CadastroZapMessenger zap = configs_globais.getZap_zap();
				 nuvem = configs_globais.getNuvem();
		
	}
	
}
