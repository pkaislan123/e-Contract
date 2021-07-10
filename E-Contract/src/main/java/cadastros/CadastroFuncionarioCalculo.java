package main.java.cadastros;

import java.util.ArrayList;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CadastroFuncionarioCalculo {

	
	int tipo_calculo, id_cadastro_salario, id_calculo, referencia_calculo, referencia_valor, quantidade;
	String nome, descricao;
	double valor, total;
}
