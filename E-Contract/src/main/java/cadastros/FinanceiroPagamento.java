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
public class FinanceiroPagamento {
	
	int id_pagamento, fluxo_caixa, tipo_pagador, tipo_recebedor, extrato, id_documento, id_condicao_pagamento, id_lancamento, status_pagamento, id_pagador, id_recebedor;
	String identificador, data_pagamento, observacao, descricao, caminho_arquivo;
	BigDecimal valor;
	 int tipo_pagamento;
	 
	 
	 String nome_pagador, nome_recebedor, nome_forma_pagamento;
	
}
