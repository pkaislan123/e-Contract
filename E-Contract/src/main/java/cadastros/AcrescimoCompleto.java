package main.java.cadastros;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AcrescimoCompleto extends CadastroFuncionarioAcrescimos{

	
	int id_contrato_trabalho;
	
}
