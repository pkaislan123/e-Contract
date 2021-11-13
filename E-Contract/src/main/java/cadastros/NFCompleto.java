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
public class NFCompleto {

	private String data, produto, codigo_contrato, valor, codigo_romaneio, codigo_nf_venda, codigo_nf_remessa, nome_emitende, nome_destinatario;
	Double peso, peso_romaneio;
	
	
	
}
