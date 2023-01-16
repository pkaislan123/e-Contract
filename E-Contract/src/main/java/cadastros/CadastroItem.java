package main.java.cadastros;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CadastroItem {

	int id_item;
	String nome;
	String descricao;
	Tipo tipo = new Tipo();

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Tipo {

		int id_tipo_item;
		String nome;
		String descricao;

	}

}
