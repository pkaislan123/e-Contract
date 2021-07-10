package main.java.cadastros;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CadastroAcessoTemporario {

	
	private int id_acesso, id_usuario_executor, id_usuario_criador, modulo;
	private String data_inicial, hora_inicial, data_final, hora_final, nome_criador, nome_executor;
	
}
