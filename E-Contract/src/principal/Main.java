package principal;

import java.io.File;
import java.util.ArrayList;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import cadastros.CadastroLogin;
import gui.TelaBaixarNotas;
import gui.TelaCadastroCliente;
import gui.TelaCliente;
import gui.TelaContratos;
import gui.TelaEntrada;
import gui.TelaLogin;
import gui.TelaNovoContratoInformal;
import gui.TelaPadrao;
import gui.TelaPlanilhaNFe;
import gui.TelaPlanilhaNFeInternas;
import gui.TelaPrincipal;
import manipular.ArquivoConfiguracoes;
import manipular.ConfiguracoesGlobais;
import manipular.Email;
import manipular.ManipularTxt;
import outros.DadosGlobais;
import outros.GetData;
import tratamento_proprio.Log;
import views_personalizadas.TelaEmEspera;

public class Main {

	
	private Log GerenciadorLog;
	private CadastroLogin login;
	private static ConfiguracoesGlobais configs_globais;
	
	
	public static void main(String[] args) {
	   
		
		try {
			for ( LookAndFeelInfo info : UIManager.getInstalledLookAndFeels() ) {
			if ( "Nimbus".equals( info.getName() ) ) {
			UIManager.setLookAndFeel( info.getClassName() );
			break;
			}
			}
			} catch ( UnsupportedLookAndFeelException exc ) {
			exc.printStackTrace();
			} catch ( ClassNotFoundException exc ) {
			exc.printStackTrace();
			} catch ( InstantiationException exc ) {
			exc.printStackTrace();
			} catch ( IllegalAccessException exc ) {
			exc.printStackTrace();
			}
		
		//TelaPadrao padrao = new TelaPadrao();
		
	   //Abre a tela de login
		//TelaLogin login = new TelaLogin();
		TelaEntrada entrada = new TelaEntrada();
		new Thread() {
			@Override
			public void run() {
				entrada.setVisible(true);

			}
		}.start();
		
		entrada.realizarTeste();
		
	
		
	
		
		
     // TelaPrincipal principal = new TelaPrincipal();
 	   //TelaNovoContrato contrato = new TelaNovoContrato("C:\\Users\\Aislan\\Documents\\modelo_informal_padrao.xlsx");
		//TelaContratos tela = new TelaContratos();
		//TelaCadastroCliente novoCliente = new TelaCadastroCliente(1, null);
	   //  TelaBaixarNotas notas= new TelaBaixarNotas();
		//TelaPlanilhaNFe planilha = new TelaPlanilhaNFe();
		//TelaPlanilhaNFeInternas planilha = new TelaPlanilhaNFeInternas();
		//TelaCliente tela = new TelaCliente();
// 	   GetData tempo  = new GetData();
		/*
		ManipularTxt anexo = new ManipularTxt();
		ArrayList<File> anexos = new ArrayList<>();
		File anexo1 = anexo.criarArquivoRetorno("C:\\ProgramData\\E-Contract\\logs", "teste1", "txt");
		File anexo2 = anexo.criarArquivoRetorno("C:\\ProgramData\\E-Contract\\logs", "teste1", "txt");
		File anexo3 = anexo.criarArquivoRetorno("C:\\ProgramData\\E-Contract\\logs", "teste1", "txt");

		anexos.add(anexo1);
		anexos.add(anexo2);
		anexos.add(anexo3);
		
           Email email = new Email();
           email.logar("comprasaislan@gmail.com", "titaniwm2014");
          email.enviarAnexo("comprasaislan@gmail.com", "comprasaislan@gmail.com", "Teste Envio de Anexos", "Olá, tudo bem?\n", "Envio em Anexo os arquivos\n",
        		        "atenciosamente;\n Aislan Silva Costa\n LD Armazens Gerais", anexos);
*/

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


