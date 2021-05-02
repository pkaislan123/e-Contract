package main.java.cadastros;

import java.math.BigDecimal;
import java.util.ArrayList;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RecebimentoCompleto extends CadastroContrato.Recebimento{

	CadastroContrato contrato = new CadastroContrato();

	
	
}
