package main.java.cadastros;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RegistroPontoDiarioCompleto extends RegistroPontoDiario {

	private String nome_colaborador;
	private String hora_diaria;
}
