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
public class CadastroRecibo {
/*
 * CREATE TABLE `recibo` (
  `id_recibo` int(10) NOT NULL AUTO_INCREMENT,
  `tipo_recibo` int(3) NOT NULL ,
  `status_recibo` int(3) DEFAULT NULL,
  `data_recibo` varchar(100) DEFAULT NULL,
  `nome_arquivo` varchar(100) DEFAULT NULL,
  `id_pagamento` int(5) DEFAULT NULL,
  PRIMARY KEY (`id_recibo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
 */
	private int id_recibo, status_recibo, tipo_recibo, id_pai, id_lancamento_pai;
	private String data_recibo, nome_arquivo;
	
}
