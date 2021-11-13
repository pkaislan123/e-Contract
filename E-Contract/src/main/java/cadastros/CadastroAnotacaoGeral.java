package main.java.cadastros;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CadastroAnotacaoGeral {
	
	String texto;
	int id_cliente, id_anotacao;
	
	
	
	

}
