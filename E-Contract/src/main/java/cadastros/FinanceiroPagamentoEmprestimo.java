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
public class FinanceiroPagamentoEmprestimo extends FinanceiroPagamento {

	int objeto;
	String especie, unidade_medida;
	double quantidade;
	BigDecimal valor_unitario;
	
	
}
