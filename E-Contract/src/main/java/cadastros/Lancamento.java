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

	int id_lancamento, contador,intervalo, id_detinatario_nf, prioridade, numero_parcelas, gerar_parcelas, tipo_lancamento, id_conta,status, id_instituicao_bancaria, id_centro_custo, id_cliente_fornecedor, id_documento;
	String data_lancamento , data_vencimento, data_pagamento, observacao, descricao, identificacao, diretorio_lancamento, caminho_arquivo;
	BigDecimal valor, valor_ja_pago;
	BigDecimal valor_proximo_pagamento_a_vencer;
	
	
	String nome_conta, nome_grupo_contas, nome_centro_custo, nome_instituicao_bancaria, nome_cliente_fornecedor;
	String ids_forma_pagamento, status_forma_pagamento, nome_destinatario_nf;
}
