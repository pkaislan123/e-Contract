package main.java.cadastros;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CadastroFuncionarioDemissao {

	CadastroFuncionarioAdmissao contrato_trabalho;
	CadastroFuncionarioEvento evento_demissao;
	
}
