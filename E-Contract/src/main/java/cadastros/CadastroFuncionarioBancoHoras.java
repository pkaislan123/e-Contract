package main.java.cadastros;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CadastroFuncionarioBancoHoras {

	
	int id_banco, id_funcionario, mes_referencia, tipo_banco;
	String quantidade_horas;
}
