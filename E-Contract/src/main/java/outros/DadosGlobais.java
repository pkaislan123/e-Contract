package main.java.outros;

import main.java.cadastros.CadastroLogin;
import main.java.gui.TelaGerenciarContrato;
import main.java.gui.TelaMain;
import main.java.gui.TelaRomaneios;
import main.java.gui.TelaTodasNotasFiscais;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.tratamento_proprio.Log;

public class DadosGlobais {

	private static DadosGlobais INSTANCE;
	private CadastroLogin Login;
	private TelaMain telaPrincipal;
	private Log gerenciador_logs;
	private  ConfiguracoesGlobais configs_globais;
	private TelaGerenciarContrato teraGerenciarContratoPai;
	private TelaRomaneios telaRomaneios;
	private String pasta_romaneios;
	
	private int status_relogio ;
	
	
	
	
	
	
	public int getStatus_relogio() {
		return status_relogio;
	}

	public void setStatus_relogio(int status_relogio) {
		this.status_relogio = status_relogio;
	}

	public String getPasta_romaneios() {
		return pasta_romaneios;
	}

	public void setPasta_romaneios(String pasta_romaneios) {
		this.pasta_romaneios = pasta_romaneios;
	}


	private TelaTodasNotasFiscais telaTodasNotasFiscais;
	
	
	
	
	

	public TelaTodasNotasFiscais getTelaTodasNotasFiscais() {
		return telaTodasNotasFiscais;
	}

	public void setTelaTodasNotasFiscais(TelaTodasNotasFiscais telaTodasNotasFiscais) {
		this.telaTodasNotasFiscais = telaTodasNotasFiscais;
	}

	public TelaRomaneios getTelaRomaneios() {
		return telaRomaneios;
	}

	public void setTelaRomaneios(TelaRomaneios telaRomaneios) {
		this.telaRomaneios = telaRomaneios;
	}

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
	
	public void setTelaPrincipal(TelaMain isto)
	{
		
		this.telaPrincipal = isto;
	}
	
	
	public TelaMain getTelaPrincipal()
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
