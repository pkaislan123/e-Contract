package main.java.cadastros;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CadastroFilaMovimento {

	private int id;
	private int tipo_movimentacao;
	private CadastroCliente transportadora;
	private CadastroCliente motorista;
	private CadastroCliente.Veiculo veiculo;
	private CadastroCliente produtor;

	
	private CadastroProduto produto;

	private int status;
	private int notificado_em_fila;
	private int notificado_entrada;
	private int notificado_saida;

	private double umidade;
	private double impureza;
	private double ardidos;

	private String observacao;
	private String origem;
	private String destino;

	private int autorizacao_movimentacao;
	private int tem_nf;

	private Date data_hora_fila;
	private Date data_hora_entrada;
	private Date data_hora_saida;

	private CadastroLogin login;

}
