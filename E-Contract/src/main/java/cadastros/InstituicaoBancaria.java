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
/*
 *  {
  ----- Caixas e Bancos
  *id -> (inteiro, poucos cadastros, Short)
  *caixa ou banco -> (String, ate 200 caracteres)
  *saldo inicial -> (decimal, precisão, String para bigdecimal, ou decimal (12,2) para double)
  *taxa de intermediacao -> (decimal, precisão, String para bigdecimal, ou decimal (4,2) para double)
  *taxa de parcelamento ao mes -> (decimal, precisão, String para bigdecimal, ou decimal (4,2) para double)
  *tarifa fixa por transacao -> (decimal, precisão, String para bigdecimal, ou decimal (4,2) para double)
 }
 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 200, nullable = false, unique = true)
	private String caixa_banco;
	
	@Column(length = 50, precision = 14, scale = 2)
	private BigDecimal saldo_inicial;
	
	@Column(length = 50, precision = 6, scale =3)
	private BigDecimal taxa_intermediacao;
	
	@Column(length = 50, precision = 6, scale = 3)
	private BigDecimal taxa_parcelamento_mensal;
	
	@Column(length = 50, precision = 6, scale = 2)
	private BigDecimal taxa_fixa_transacao;
	
	
	
}
