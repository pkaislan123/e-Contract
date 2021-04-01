package main.java.cadastros;

public class CadastroLogin {

	private int id;
	private String nome, sobrenome, login, email, senha , senhaEmail, cargo, celular, genero, tratamento, ip_ativo, email2, senhaEmail2;
	
	public String getIp_ativo() {
		return ip_ativo;
	}
	public void setIp_ativo(String ip_ativo) {
		this.ip_ativo = ip_ativo;
	}


	public String getEmail2() {
		return email2;
	}
	public void setEmail2(String email2) {
		this.email2 = email2;
	}
	public String getSenhaEmail2() {
		return senhaEmail2;
	}
	public void setSenhaEmail2(String senhaEmail2) {
		this.senhaEmail2 = senhaEmail2;
	}


	private Preferencias configs_preferencias;
	private Privilegios configs_privilegios;
	
	
	
	
	public Privilegios getConfigs_privilegios() {
		return configs_privilegios;
	}
	public void setConfigs_privilegios(Privilegios configs_privilegios) {
		this.configs_privilegios = configs_privilegios;
	}
	public Preferencias getConfigs_preferencias() {
		return configs_preferencias;
	}
	public void setConfigs_preferencias(Preferencias configs_preferencias) {
		this.configs_preferencias = configs_preferencias;
	}
	public String getSobrenome() {
		return sobrenome;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public String getTratamento() {
		return tratamento;
	}
	public void setTratamento(String tratamento) {
		this.tratamento = tratamento;
	}
	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	public String getSenhaEmail() {
		return senhaEmail;
	}
	public void setSenhaEmail(String senhaEmail) {
		this.senhaEmail = senhaEmail;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	
	
	public static class Privilegios {
		int id_privilegios;
		
		int nivel_privilegios;
		int privilegio_alterar_apis;
		
		
		
		public int getId_privilegios() {
			return id_privilegios;
		}


		public void setId_privilegios(int id_privilegios) {
			this.id_privilegios = id_privilegios;
		}


		public int getNivel_privilegios() {
			return nivel_privilegios;
		}
		
		
		public void setNivel_privilegios(int nivel_privilegios) {
			this.nivel_privilegios = nivel_privilegios;
		}
		public int getPrivilegio_alterar_apis() {
			return privilegio_alterar_apis;
		}
		public void setPrivilegio_alterar_apis(int privilegio_alterar_apis) {
			this.privilegio_alterar_apis = privilegio_alterar_apis;
		}
		
	}
	
	
	public static class Preferencias{
		
		int id_preferencias;
		int api_exato, api_whatsapp, api_sintegra;
		public int getId_preferencias() {
			return id_preferencias;
		}
		public void setId_preferencias(int id_preferencias) {
			this.id_preferencias = id_preferencias;
		}
		public int getApi_exato() {
			return api_exato;
		}
		public void setApi_exato(int api_exato) {
			this.api_exato = api_exato;
		}
		public int getApi_whatsapp() {
			return api_whatsapp;
		}
		public void setApi_whatsapp(int api_whatsapp) {
			this.api_whatsapp = api_whatsapp;
		}
		public int getApi_sintegra() {
			return api_sintegra;
		}
		public void setApi_sintegra(int api_sintegra) {
			this.api_sintegra = api_sintegra;
		}
		
		
		
		
		
	}
	
	
	public static class Mensagem{
		
		int id_mensagem, id_remetente, id_destinatario;
		String conteudo, data, hora;
		public int getId_mensagem() {
			return id_mensagem;
		}
		public void setId_mensagem(int id_mensagem) {
			this.id_mensagem = id_mensagem;
		}
		public int getId_remetente() {
			return id_remetente;
		}
		public void setId_remetente(int id_remetente) {
			this.id_remetente = id_remetente;
		}
		public int getId_destinatario() {
			return id_destinatario;
		}
		public void setId_destinatario(int id_destinatario) {
			this.id_destinatario = id_destinatario;
		}
		public String getConteudo() {
			return conteudo;
		}
		public void setConteudo(String conteudo) {
			this.conteudo = conteudo;
		}
		public String getData() {
			return data;
		}
		public void setData(String data) {
			this.data = data;
		}
		public String getHora() {
			return hora;
		}
		public void setHora(String hora) {
			this.hora = hora;
		}
		
		@Override
		public String toString() {
		    return conteudo;
		}
		
		
		
	}
	
	
}
