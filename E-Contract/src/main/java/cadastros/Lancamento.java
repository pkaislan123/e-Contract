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
public class Lancamento {

	int id_lancamento, id_conta, id_condicao_pagamento, id_instituicao_bancaria, id_centro_custo, id_cliente_fornecedor;
	String data_lancamento , data_vencimento, data_pagamento, status, observacao, descricao;
	BigDecimal valor;
}
