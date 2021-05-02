package main.java.cadastros;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CarregamentoCompleto extends CadastroContrato.Carregamento{
	
	
	CadastroContrato contrato = new CadastroContrato();
	String nome_motorista, placa;
	String cliente_carregamento, vendedor_carregamento;
}
