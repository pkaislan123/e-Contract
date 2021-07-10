package main.java.cadastros;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CadastroFuncionarioRotinaTrabalho {

	private int id_rotina, id_funcionario, dia_da_semana, folga = 0, id_ct_trabalho, tipo_rotina;
	private String data, hora_entrada1, hora_saida1, hora_entrada2, hora_saida2;
	
	
}
