package main.java.cadastros;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CartaoPontoCompleto extends CartaoPonto {

	
	 int id_funcionario;
	 String nome_funcionario;
}
