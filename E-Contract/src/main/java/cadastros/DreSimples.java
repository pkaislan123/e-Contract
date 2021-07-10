package main.java.cadastros;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DreSimples {

	
	private int mes, ano;
	private double receitas, despesas, total, lucro, lucratividade, saldo_inicial;
	
}
