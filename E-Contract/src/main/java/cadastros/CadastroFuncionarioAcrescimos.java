

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
public class CadastroFuncionarioAcrescimos {

	
	int id_acrescimo;
	String descricao,referencia;
	double porcentagem, valor;
}
