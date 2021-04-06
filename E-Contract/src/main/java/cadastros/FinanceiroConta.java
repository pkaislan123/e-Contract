package main.java.cadastros;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FinanceiroConta {

	private int id, id_grupo_contas, tipo_conta;
	private String nome, descricao, observacao;
	
}
