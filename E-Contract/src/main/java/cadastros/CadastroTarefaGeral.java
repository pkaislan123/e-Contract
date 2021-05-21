package main.java.cadastros;

public class CadastroTarefaGeral {

	
		
		private int id_tarefa;
		private int tipo, id_lancamento_pai;
		private int status_tarefa;
		private String descricao_tarefa;
		private String mensagem;
		private String hora;
		private String data;
		private String nome_tarefa;
		private CadastroLogin criador;
		private String resposta;
		private String nome_criador, nome_executor;
		
		
		
		public int getId_lancamento_pai() {
			return id_lancamento_pai;
		}
		public void setId_lancamento_pai(int id_lancamento_pai) {
			this.id_lancamento_pai = id_lancamento_pai;
		}
		public int getTipo() {
			return tipo;
		}
		public void setTipo(int tipo) {
			this.tipo = tipo;
		}
		public String getNome_criador() {
			return nome_criador;
		}
		public void setNome_criador(String nome_criador) {
			this.nome_criador = nome_criador;
		}
		public String getNome_executor() {
			return nome_executor;
		}
		public void setNome_executor(String nome_executor) {
			this.nome_executor = nome_executor;
		}
		public String getResposta() {
			return resposta;
		}
		public void setResposta(String resposta) {
			this.resposta = resposta;
		}
		private String hora_agendada;
		private String data_agendada;
		private int prioridade;
		
		
		
		public String getHora_agendada() {
			return hora_agendada;
		}
		public void setHora_agendada(String hora_agendada) {
			this.hora_agendada = hora_agendada;
		}
		public String getData_agendada() {
			return data_agendada;
		}
		public void setData_agendada(String data_agendada) {
			this.data_agendada = data_agendada;
		}
		public int getPrioridade() {
			return prioridade;
		}
		public void setPrioridade(int prioridade) {
			this.prioridade = prioridade;
		}
		public CadastroLogin getCriador() {
			return criador;
		}
		public void setCriador(CadastroLogin criador) {
			this.criador = criador;
		}
		public CadastroLogin getExecutor() {
			return executor;
		}
		public void setExecutor(CadastroLogin executor) {
			this.executor = executor;
		}
		private CadastroLogin executor;
		
		
		
		public int getId_tarefa() {
			return id_tarefa;
		}
		public void setId_tarefa(int id_tarefa) {
			this.id_tarefa = id_tarefa;
		}
		
		
		public int getStatus_tarefa() {
			return status_tarefa;
		}
		public void setStatus_tarefa(int status_tarefa) {
			this.status_tarefa = status_tarefa;
		}
		public String getDescricao_tarefa() {
			return descricao_tarefa;
		}
		public void setDescricao_tarefa(String descricao_tarefa) {
			this.descricao_tarefa = descricao_tarefa;
		}
		public String getMensagem() {
			return mensagem;
		}
		public void setMensagem(String mensagem) {
			this.mensagem = mensagem;
		}
		public String getHora() {
			return hora;
		}
		public void setHora(String hora) {
			this.hora = hora;
		}
		public String getData() {
			return data;
		}
		public void setData(String data) {
			this.data = data;
		}
		public String getNome_tarefa() {
			return nome_tarefa;
		}
		public void setNome_tarefa(String nome_tarefa) {
			this.nome_tarefa = nome_tarefa;
		}
		
		
	}
