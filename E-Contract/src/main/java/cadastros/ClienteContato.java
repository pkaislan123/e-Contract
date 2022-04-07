package main.java.cadastros;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteContato {

	
	private CadastroCliente cliente;
	private Contato contato;
	
}
