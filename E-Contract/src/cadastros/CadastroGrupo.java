package cadastros;

import java.util.ArrayList;

public class CadastroGrupo {

	
	private int id_grupo;
	private ArrayList<CadastroCliente> clientes = new ArrayList<>();
	private String nome_grupo, descricao_grupo, integrantes;
	public int getId_grupo() {
		return id_grupo;
	}
	public void setId_grupo(int id_grupo) {
		this.id_grupo = id_grupo;
	}
	public String getNome_grupo() {
		return nome_grupo;
	}
	public ArrayList<CadastroCliente> getClientes() {
		return clientes;
	}
	public void setClientes(ArrayList<CadastroCliente> clientes) {
		this.clientes = clientes;
	}
	public void setNome_grupo(String nome_grupo) {
		this.nome_grupo = nome_grupo;
	}
	public String getDescricao_grupo() {
		return descricao_grupo;
	}
	public void setDescricao_grupo(String descricao_grupo) {
		this.descricao_grupo = descricao_grupo;
	}
	public String getIntegrantes() {
		return integrantes;
	}
	public void setIntegrantes(String integrantes) {
		this.integrantes = integrantes;
	}
	
	
	
}
