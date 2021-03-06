package main.java.cadastros;

import java.math.BigDecimal;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SaldoInstituicaoBancaria {

	double total_receita, total_despesa, total_emprestimos, total_receita_transferencia, total_despesa_transferencia, total_despesa_emprestimo;
	
}
