package cadastros;

public class CadastroProduto {
	
	
	private int id_produto;
	private int codigo;
	private String transgenia;
	
	
	
	public String getTransgenia() {
		return transgenia;
	}
	public void setTransgenia(String transgenia) {
		this.transgenia = transgenia;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	private String nome_produto;
	private String descricao_produto;
	
	
	public String getNome_produto() {
		return nome_produto;
	}
	public void setNome_produto(String nome_produto) {
		this.nome_produto = nome_produto;
	}
	public String getDescricao_produto() {
		return descricao_produto;
	}
	public void setDescricao_produto(String descricao_produto) {
		this.descricao_produto = descricao_produto;
	}
	public int getId_produto() {
		return id_produto;
	}
	public void setId_produto(int id_produto) {
		this.id_produto = id_produto;
	}
	
	

}
