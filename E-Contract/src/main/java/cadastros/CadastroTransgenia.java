package main.java.cadastros;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class CadastroTransgenia {

	
	private int id_transgenia, teste, resultado,  royalties;
	private String nome, descricao;
	
}
