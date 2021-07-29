package main.java.cadastros;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DreAgrupado {

	int flag ;
	int mes;
	String nome_grupo_contas, nome_conta;
	double valor_despesas_janeiro,
	valor_despesas_fevereiro,valor_despesas_marco,valor_despesas_abril,valor_despesas_maio,valor_despesas_junho,
	valor_despesas_julho,valor_despesas_agosto,valor_despesas_setembro,valor_despesas_outubro,valor_despesas_novembro,
	valor_despesas_dezembro;
	
	double valor_receitas_janeiro,
	valor_receitas_fevereiro,valor_receitas_marco,valor_receitas_abril,valor_receitas_maio,valor_receitas_junho,
	valor_receitas_julho,valor_receitas_agosto,valor_receitas_setembro,valor_receitas_outubro,valor_receitas_novembro,
	valor_receitas_dezembro;
}
