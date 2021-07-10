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
public class CadastroFuncionarioEvento {

	
	private int id_evento, id_colaborador, id_contrato, tipo_evento, motivo_demissao;
	private String data_folga,data_evento, data_ferias_ida, data_ferias_volta;
	private double novo_valor_salarial;
	
}
