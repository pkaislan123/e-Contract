package main.java.cadastros;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CadastroCotacao {

	private int id_cotacao;
	private CadastroProduto produto;
	private String medida;
	private String unidade;
	private double quantidade;
	private double valor;
	
	private Date data;
	private String localidade;
	private String indicador;
	
}
