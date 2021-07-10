package main.java.cadastros;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class EventoGlobal {

	/*
	 * CREATE TABLE `evento_global` (
  `id_evento` int(5) NOT NULL AUTO_INCREMENT,
  `tipo_evento` int(5) DEFAULT NULL,
  `data_evento` varchar(40) DEFAULT NULL,
  `hora_evento` varchar(40) DEFAULT NULL,
  `descricao` text DEFAULT NULL,
  
  PRIMARY KEY (`id_evento`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8
	 */
	
	private int id_evento, tipo_evento;
	private String data_evento, hora_evento, descricao;
	
}
