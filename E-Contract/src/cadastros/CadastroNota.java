package cadastros;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class CadastroNota {

	
	int id, id_usuario_pai, notificar, tempo_notificacao, uni_tempo, tipo, lembrar;
	public int getId_usuario_pai() {
		return id_usuario_pai;
	}
	public void setId_usuario_pai(int id_usuario_pai) {
		this.id_usuario_pai = id_usuario_pai;
	}
	public int getTempo_notificacao() {
		return tempo_notificacao;
	}
	public void setTempo_notificacao(int tempo_notificacao) {
		this.tempo_notificacao = tempo_notificacao;
	}
	public int getUni_tempo() {
		return uni_tempo;
	}
	public void setUni_tempo(int uni_tempo) {
		this.uni_tempo = uni_tempo;
	}
	public int getLembrar() {
		return lembrar;
	}
	public void setLembrar(int lembrar) {
		this.lembrar = lembrar;
	}
	public Date getData_nota() {
		return data_nota;
	}
	public void setData_nota(Date data_nota) {
		this.data_nota = data_nota;
	}
	
	

	String nome, descricao, texto ;
	Date data_nota;
    Date data_lembrete;
    LocalTime hora_lembrete;
    
    

	public Date getData_lembrete() {
		return data_lembrete;
	}
	public void setData_lembrete(Date data_lembrete) {
		this.data_lembrete = data_lembrete;
	}
	public LocalTime getHora_lembrete() {
		return hora_lembrete;
	}
	public void setHora_lembrete(LocalTime hora_lembrete) {
		this.hora_lembrete = hora_lembrete;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getNotificar() {
		return notificar;
	}
	public void setNotificar(int notificar) {
		this.notificar = notificar;
	}
	
	
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	
	
	
	
	
}
