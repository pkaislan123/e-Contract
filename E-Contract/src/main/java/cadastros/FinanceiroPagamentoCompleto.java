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
public class FinanceiroPagamentoCompleto  {

	FinanceiroPagamento fpag = new FinanceiroPagamento();
	Lancamento lancamento = new Lancamento();
	
}
