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
public class CadastroFuncionario {

	
	
	
	private int id_funcionario;
	
	//dados de pessoa
	private String nome, sobrenome, cpf, apelido, rg, estado, nacionalidade, naturalidade, nascimento, genero, estado_civil;
	private String nome_mae, nome_pai;
	
	
	//dados de endereco
	private String rua, bairro, numero, cidade, estado_endereco, cep;
	
	//obrigacoes civicas
	private String titulo_eleitor, titulo_secao, titulo_zona;
	private String certida_militar, certiao_serie, certidao_categoria;
	
	//ctps
	private String pis, nis, ctps_serie, ctps_estado;
	
	//cnh
	private String cnh_categoria, cnh_validade, cnh_num_registro;
	
	//escolaridade
	private String grau_escolaridade, cursos, habilidades;

	//dados bancarios
	private ArrayList<ContaBancaria> contas = new ArrayList<>();

		
	//array de cliente
	private ArrayList<Contato> contatos = new ArrayList<>();
	

	
	
}
