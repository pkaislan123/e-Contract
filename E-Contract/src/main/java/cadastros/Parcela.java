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
public class Parcela {

	/*
	 * CREATE TABLE `parcela` (
  `id_parcela` int(5) NOT NULL AUTO_INCREMENT,
  id_lancamento_pai int (5),
  `valor` varchar(40) DEFAULT NULL,
  `data_vencimento` varchar(40) DEFAULT NULL,
  `status` int(3) DEFAULT NULL,
  `observacao` text DEFAULT NULL,
  `descricao` text DEFAULT NULL,
  caminho_arquivo text,
  PRIMARY KEY (`id_parcela`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
	 */
	int id_parcela, id_lancamento_pai, status;
	String identificador,data_vencimento, arquivo, descricao, observacao, caminho_arquivo;
	BigDecimal valor;
	
}
