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
	BigDecimal saldo_atual = BigDecimal.ZERO;
	String nome_forma_pagamento;
	String nome_pagador;
	String nome_recebedor;
	int id_caixa;
}
