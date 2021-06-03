package main.java.cadastros;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CadastroFuncionarioAdmissao {

	
	int id_contrato, id_colaborador, status;
	String data_admissao, tipo_contrato, cargo, funcao;
	double salario;
	
}
