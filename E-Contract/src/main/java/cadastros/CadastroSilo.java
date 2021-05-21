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
public class CadastroSilo {
/*
 * CREATE TABLE `silo` (
  `id_silo` int(3) NOT NULL AUTO_INCREMENT,
  `id_armazem` int(3),
  `nome_silo` varchar(30) DEFAULT NULL,
  `identificador` varchar(30) DEFAULT NULL,
  `capacidade` double,
  `descricao` varchar(30) DEFAULT NULL,

  PRIMARY KEY (`id_silo`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8
 */
	
	private int id_silo, id_armazem;
	private String nome_silo, identificador,descricao, nome_armazem;
	private double capacidade;
	
}
