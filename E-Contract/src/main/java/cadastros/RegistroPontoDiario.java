package main.java.cadastros;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RegistroPontoDiario {

	String data, entrada1, saida1, entrada2, saida2, entrada3, saida3;
	
}
