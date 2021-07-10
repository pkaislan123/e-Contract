package main.java.cadastros;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class InstituicaoBancaria {


	private int id_instituicao_bancaria, id_cliente, id_conta;
	

	private String nome_instituicao_bancaria, observacao, descricao;
	
	private BigDecimal saldo_inicial;
	private BigDecimal taxa_intermediacao;
	private BigDecimal taxa_parcelamento_mensal;
	private BigDecimal taxa_fixa_transacao;
	
	private String nome_cliente;
	private String cc_banco, cc_agencia, cc_codigo, cc_conta;
	
}
