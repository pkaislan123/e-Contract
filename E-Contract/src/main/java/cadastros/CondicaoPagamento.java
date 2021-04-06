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
public class CondicaoPagamento {

	int id_condicao_pagamento, numero_parcelas, intervalo, dia_recebimento, forma_pagamento;
	String nome_condicao_pagamento, observacao, descricao;
	
	
}
