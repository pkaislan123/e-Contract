package main.java.cadastros;

import java.util.ArrayList;

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
	Tipo tipo;
	
	
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static
	 class Tipo{
		
		int id_tipo_item;
		String nome;
		String descricao;
		
		
		
	}
	
}
