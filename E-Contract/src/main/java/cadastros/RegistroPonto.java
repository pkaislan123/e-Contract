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
public class RegistroPonto {

	
	private int id_registro_ponto, movimentacao, id_funcionario;
	private String data, hora, motivo;
	
}
