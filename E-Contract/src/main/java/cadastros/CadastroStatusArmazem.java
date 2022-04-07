package main.java.cadastros;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CadastroStatusArmazem {

	private int id_status;
	private String status_armazem;
	private String status_embarque;
	private String status_desembarque;
	private String hora_encerramento;
	
}
