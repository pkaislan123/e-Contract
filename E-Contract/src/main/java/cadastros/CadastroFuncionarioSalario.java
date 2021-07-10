package main.java.cadastros;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CadastroFuncionarioSalario {

	private int id_salario, id_funcionario, id_ct_trabalho, mes, ano;
	double salario_base, total_descontos, total_acrescimos, total_hora_extras;
	
	
	
	
}
