package main.java.cadastros;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CadastroFuncionariosHorarios {

	String data_inicial_trabalho, hora_entrada1, hora_saida1, hora_entrada2, hora_saida2;
	int tempo_intervalo;
	boolean folga;
	int dia_semana;
	
	
	
	
}
