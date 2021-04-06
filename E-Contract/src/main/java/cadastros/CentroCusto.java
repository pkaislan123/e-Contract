package main.java.cadastros;

import java.math.BigDecimal;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CentroCusto {

	int id_centro_custo, id_cliente;
	String nome_centro_custo, observacao, descricao;
	
	
	
	
}
