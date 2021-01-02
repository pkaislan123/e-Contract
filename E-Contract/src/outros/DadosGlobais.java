package outros;

import cadastros.CadastroBaseDados;
import cadastros.CadastroLogin;
import gui.TelaGerenciarContrato;
import gui.TelaElaborarNovoContrato;
import gui.TelaPrincipal;
import manipular.ConfiguracoesGlobais;
import tratamento_proprio.Log;


public class DadosGlobais {

	private static DadosGlobais INSTANCE;
	private CadastroLogin Login;
	private TelaPrincipal telaPrincipal;
	private Log gerenciador_logs;
	private  ConfiguracoesGlobais configs_globais;
	private TelaGerenciarContrato teraGerenciarContratoPai;
	
	
	
	
	

	public TelaGerenciarContrato getTeraGerenciarContratoPai() {
		return teraGerenciarContratoPai;
	}

	public void setTeraGerenciarContratoPai(TelaGerenciarContrato teraGerenciarContratoPai) {
		this.teraGerenciarContratoPai = teraGerenciarContratoPai;
	}

	public ConfiguracoesGlobais getConfigs_globais() {
		return configs_globais;
	}

	public void setConfigs_globais(ConfiguracoesGlobais configs_globais) {
		this.configs_globais = configs_globais;
	}

	public void setLogin(CadastroLogin login)
	{
		this.Login = login;
	}
	
	public void setGerenciadorLog (Log log) {
		this.gerenciador_logs = log;
	}
	
	protected DadosGlobais()
	{
		
	}
	
	public void setTelaPrincipal(TelaPrincipal isto)
	{
		
		this.telaPrincipal = isto;
	}
	
	
	public TelaPrincipal getTelaPrincipal()
	{
		return telaPrincipal;
	}
	
	
	public void atualizarGraficosTelaPrincipal() {
		this.telaPrincipal.getDadosContratos();
		this.telaPrincipal.atualizarGraficoContratos();
	}
	
	public CadastroLogin getLogin()
	{
		return this.Login;
	}
	
	public Log getGerenciadorLog() {
		return this.gerenciador_logs;
	}
	
	
	public static DadosGlobais getInstance()
	{
		if(INSTANCE == null)
		{
			INSTANCE = new DadosGlobais();
		
		}
		return INSTANCE;
	}
	
	
	
	
	
}
