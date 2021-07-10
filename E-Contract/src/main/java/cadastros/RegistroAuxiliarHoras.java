package main.java.cadastros;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RegistroAuxiliarHoras {

	
	
	long tHExtras, tHAtrazo, tHTrabalhadas, tHMensal, tH50, tH100, tH60;
	double valor_total_hora_extras;
	double valor_total_hora50;
	double valor_total_hora60;
	double valor_total_hora100;
	
	double valor_hora;
	double valor_hora50;
	double valor_hora60;
	double valor_hora100;
	
	int opcao_banco, tipo_banco;
	String mes; long quantidade;
	
}
