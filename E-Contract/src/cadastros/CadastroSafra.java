package cadastros;

public class CadastroSafra {
	
	private int id_safra;
	private int ano_plantio;
	private int ano_colheita;
	CadastroProduto produto;
	private int codigo;
	
	
	
	private String descricao_safra;
	
	public String getDescricao_safra() {
		return descricao_safra;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public void setDescricao_safra(String descricao_safra) {
		this.descricao_safra = descricao_safra;
	}
	public int getAno_plantio() {
		return ano_plantio;
	}
	public void setAno_plantio(int ano_plantio) {
		this.ano_plantio = ano_plantio;
	}
	public int getAno_colheita() {
		return ano_colheita;
	}
	public void setAno_colheita(int ano_colheita) {
		this.ano_colheita = ano_colheita;
	}
	public CadastroProduto getProduto() {
		return produto;
	}
	public void setProduto(CadastroProduto produto) {
		this.produto = produto;
	}
	public int getId_safra() {
		return id_safra;
	}
	public void setId_safra(int id_safra) {
		this.id_safra = id_safra;
	}
	
	

}
