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
public class PagamentoCompleto extends CadastroContrato.CadastroPagamentoContratual{

	

	CadastroContrato contrato_remetente = new CadastroContrato();
	CadastroContrato contrato_destinatario = new CadastroContrato();
	
	String conta_bancaria_depositante, conta_bancaria_favorecido;
	String depositante, favorecido, nome_comprovante;
}
