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

	/*
	 * CREATE TABLE `financeiro_pagamento` (
  `id_pagamento` int(5) NOT NULL AUTO_INCREMENT,
  `identificador` text DEFAULT NULL, 
  `id_forma_pagamento` text DEFAULT NULL,
  `valor` varchar(40) DEFAULT NULL,
  `data_pagamento` varchar(40) DEFAULT NULL,
  `observacao` text DEFAULT NULL,
  `descricao` text DEFAULT NULL,
  `caminho_arquivo` text DEFAULT NULL,
  PRIMARY KEY (`id_pagamento`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8
	 */
	int id_pagamento, id_condicao_pagamento, id_lancamento, status_pagamento, id_pagador;
	String identificador, data_pagamento, observacao, descricao, caminho_arquivo;
	BigDecimal valor;
	
}
