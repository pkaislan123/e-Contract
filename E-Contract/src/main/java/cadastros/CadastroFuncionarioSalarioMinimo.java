package main.java.cadastros;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CadastroFuncionarioSalarioMinimo {

	
	private int id;
	private double valor;
	private String data_inicial, data_final, descricao;
	
}
