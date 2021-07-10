package main.java.cadastros;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RegistroPontoMensalCompleto {

	private RegistroPontoDiario rp;
	private String nome_colaborador;
	private String hora_diaria;
	private String hora_trabalhada;
	private String hora_extra;
	private String hora_atrazo;
	
}
