package main.java.manipular;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import javax.swing.JOptionPane;

import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroContrato.CadastroPagamento;
import main.java.cadastros.CadastroContrato.CadastroPagamentoContratual;
import main.java.cadastros.CadastroContrato.Recebimento;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroModelo;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroSafra;
import main.java.cadastros.ContaBancaria;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoSafras;
import main.java.gui.TelaCadastroCliente;
import main.java.outros.DadosGlobais;
import main.java.outros.GetData;
import main.java.outros.TratarDados;
import main.java.tratamento_proprio.Log;
import main.java.views_personalizadas.TelaEmEspera;


public class ManipularArquivoTerceiros {

	
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	String lines[];
	File file;
	TratarDados tratamentoDados;
	
	public CadastroContrato filtrar(File _file) {
		 getDadosGlobais();
		 
		 file = _file;

		CadastroContrato contrato_local = new CadastroContrato() ;
		
		
		try (PDDocument document = PDDocument.load(file)) {

			if (!document.isEncrypted()) {

				PDFTextStripperByArea stripper = new PDFTextStripperByArea();
				stripper.setSortByPosition(true);

				PDFTextStripper tStripper = new PDFTextStripper();

				String pdfFileInText = tStripper.getText(document);

				 lines = pdfFileInText.split("\r\n");
				String compradora = "";
				
				boolean contrato_cj = false;
				boolean contrato_gavilon = false;
				boolean contrato_fortunato = false;
				boolean contrato_bungue = false;
				
				//imprime as linhas
				for(int i = 0; i < lines.length; i++) {
					System.out.println(lines[i]);
					if(lines[i].contains("Gavilon")) {
						contrato_gavilon = true;
						
						
						
					}else if(lines[i].contains("CJ SELECTA")) {
						//chama a funcao da cj
						
						contrato_cj = true;
					
					}
					else if(lines[i].contains("COMPRADOR: FORTUNATO")) {
						contrato_fortunato = true;
						
					}else if(lines[i].contains("BUNGE")) {
						contrato_bungue = true;
					}else {
						
					}
						
				}
             
            	String tratar = Arrays.toString(lines);
    			
    			tratamentoDados = new TratarDados(tratar);
    			 

                  if(contrato_cj)
    			    contrato_local = contratoCJSelecta(contrato_local);
                  else if(contrato_gavilon)
      			    contrato_local = contratoGavilon(contrato_local);
                  else if(contrato_fortunato)
                	  contrato_local = contratoFortunato(contrato_local);
                  else if(contrato_bungue)
                	  contrato_local = contratoBungue(contrato_local);
                  else
                	  contrato_local = null;
                  
    			
    			
    			
			}
			
			
		}catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Não foi possivel importar este contrato\nConsulte o administrador!"
					+ "\nTente a Importação Manual");
			
		}
		
		return contrato_local;
	}
	
	
	public void getDadosGlobais() {
		//gerenciador de log
				DadosGlobais dados = DadosGlobais.getInstance();
				 configs_globais = dados.getConfigs_globais();
				 
				 //usuario logado
				  login = dados.getLogin();
		
	}
	
	
	public CadastroContrato contratoCJSelecta(CadastroContrato contrato_local) {

		
		try {
		String codigo_contrato = tratamentoDados.tratar("Nº ", " ");
		contrato_local.setCodigo(codigo_contrato);
		contrato_local.setSub_contrato(3);
		

		String armazenagem = tratamentoDados.tratar("Armazenagem: ", "Frete").replace(" ", "");
		//JOptionPane.showMessageDialog(null, "Armazenagem por conta do: " + armazenagem);
		contrato_local.setArmazenagem(armazenagem);
		
		String frete = tratamentoDados.tratar("Frete ", "CIF").replace("(", "").replaceAll(" ", "");
		//JOptionPane.showMessageDialog(null, "Frete por conta do: " + frete);
		contrato_local.setFrete(frete);

		CadastroCliente comprador_contrato = new CadastroCliente();
		String cnpj_comprador = tratamentoDados.tratar(" CNPJ: ", " ");
		cnpj_comprador = cnpj_comprador.replaceAll("[^0-9]+", "");
		//procura nos clientes se existe este comprador
	     GerenciarBancoClientes gerenciar = new GerenciarBancoClientes();
	     ArrayList<CadastroCliente> clientes = gerenciar.getClientes(0, 0, "");
	     
		for(CadastroCliente cliente : clientes) {
			if(cliente.getTipo_pessoa() == 1) {

			if(cliente.getCnpj().equals(cnpj_comprador)) {
				comprador_contrato = cliente;
				break;
			}
			}
		}
		
		
		
		CadastroCliente vendedor_contrato = new CadastroCliente();

		String identificacao_vendedor = tratamentoDados.tratar("CPF/CNPJ: ", " ");
		identificacao_vendedor = identificacao_vendedor.replaceAll("[^0-9]+", "");
        int tipo_vendedor = -1;
		

        int linha_nome_vendedor = - 1;
		for(int i = 0; i < lines.length; i++) {
			if(lines[i].contains("Vendedor:")) {
				linha_nome_vendedor = i;  
               break;
			}
		}
		String nome_vendedor_busca = "";
		JOptionPane.showMessageDialog(null, "Linha de nome de: " + lines[linha_nome_vendedor]);
	
		if(lines[linha_nome_vendedor].length() < 11 ) {
			//nome grande
			//JOptionPane.showMessageDialog(null, "nome grande");
			
			 nome_vendedor_busca = lines[linha_nome_vendedor] + lines[linha_nome_vendedor+1] +  lines[linha_nome_vendedor+2] + ";";
			 linha_nome_vendedor +=  2;

		}else{
			//JOptionPane.showMessageDialog(null, "nome pequeno");

			 nome_vendedor_busca = lines[linha_nome_vendedor] + ";";

		}
		
		TratarDados tratar = new TratarDados(nome_vendedor_busca);
		System.out.println("nome vendedor da busca: " + nome_vendedor_busca);
		String nome_vendedor = tratar.tratar("Vendedor: ", ";");
		//JOptionPane.showMessageDialog(null, "Nome vendedor: " + nome_vendedor);
		
		String nome_vendedor_quebrado [] = nome_vendedor.split(" ");
		vendedor_contrato.setNome(nome_vendedor_quebrado[0]);
		String sobre_nome = "";
		for(int i = 1; i <nome_vendedor_quebrado.length; i++) {
			sobre_nome = sobre_nome + " " + nome_vendedor_quebrado[i];
		}
		vendedor_contrato.setSobrenome(sobre_nome);
        
        if(identificacao_vendedor.length() == 11) {
        	tipo_vendedor = 0;
        	vendedor_contrato.setTipo_pessoa(0);
        	vendedor_contrato.setCpf(identificacao_vendedor);
        	vendedor_contrato.setNome_empresarial(nome_vendedor);
        }
        else if(identificacao_vendedor.length() == 14) {
        	tipo_vendedor = 1;
        	vendedor_contrato.setTipo_pessoa(1);
        	vendedor_contrato.setCnpj(identificacao_vendedor);
        	vendedor_contrato.setNome_fantaia(nome_vendedor);

        }
		
        boolean vendedor_cadastrado = false;
		for(CadastroCliente cliente : clientes) {
			
			if(tipo_vendedor == 0) {
				if(cliente.getTipo_pessoa() == 0) {
					if(cliente.getCpf().equals(identificacao_vendedor)) {
						vendedor_contrato = cliente;
						vendedor_cadastrado  = true;
					break;
					}
				}
				
			}else {
				if(cliente.getTipo_pessoa() == 1) {
					if(cliente.getCnpj().equals(identificacao_vendedor)) {
						vendedor_contrato = cliente;
						vendedor_cadastrado  = true;

					break;
					}
				}
			}
			
		}
		
	
		
		//seta os vendedores do contrato
		
		if(vendedor_cadastrado) {
			
			if(vendedor_contrato.getTipo_pessoa() == 0) {
				//pessoa fisica
				nome_vendedor = vendedor_contrato.getNome_empresarial();
			}else {
				nome_vendedor = vendedor_contrato.getNome_fantaia();

			}
			
			
			
		}
		
	
		
		//safra
		String safra =  "";
		int linha_safra = -1;
		for(int i = 0; i < lines.length; i++) {
			if(lines[i].contains("2020/2021")) {
               safra = "2020/2021";  
               linha_safra = i;
               break;
			}else if(lines[i].contains("2021/2021")) {
                safra = "2021/2021";  
                linha_safra = i;
                break;
			}else if(lines[i].contains("2021/2022")) {
                safra = "2021/2022"; 
                linha_safra = i;
                break;

			}else if(lines[i].contains("2022/2022")) {
                safra = "2022/2022"; 
                linha_safra = i;
                break;

			}else if(lines[i].contains("2022/2023")) {
                safra = "2022/2023";   
                linha_safra = i;
                break;

			}else if(lines[i].contains("2023/2023")) {
                safra = "2023/2023";      				 	

                linha_safra = i;
                break;
			}else if(lines[i].contains("2023/2024")) {
                safra = "2023/2024";   
                linha_safra = i;
                break;

			}
		}
		//JOptionPane.showMessageDialog(null, "safra: " + safra);

	
		
		int linha_faz_lavoura = linha_safra + 1;
		int linha_inscricao_contrato = linha_faz_lavoura + 1;
		 String uf_inscricao_contrato  = "";
		 String inscricao_contrato = "";
		 String linha_endereco = "";
		
		String fazenda_lavoura = lines[linha_faz_lavoura];
		if(fazenda_lavoura.length() > 1 && fazenda_lavoura != null) {
		
			//JOptionPane.showMessageDialog(null, "fazenda lavour: " + fazenda_lavoura);

		  uf_inscricao_contrato = fazenda_lavoura.substring(fazenda_lavoura.length() - 3,fazenda_lavoura.length()-1);
		 
		  //  JOptionPane.showMessageDialog(null, "uf_inscricao_contrato: " + uf_inscricao_contrato);

		  inscricao_contrato = lines[linha_inscricao_contrato].replaceAll("[^0-9+]", "");
		 
		   linha_endereco = lines[linha_nome_vendedor + 1] + lines[linha_nome_vendedor + 2];
		
		  
		   // JOptionPane.showMessageDialog(null, "Endereço: " + linha_endereco);
           System.out.println("Endereço da faz: " + linha_endereco);
           TratarDados tratar_endereco = new TratarDados(linha_endereco);
           String rua = tratar_endereco.tratar("Endereço: ", ",");
           String cidade = tratar_endereco.tratar("Município: ", "CEP").trim();
           cidade = cidade.replaceAll("MG", "");
           cidade = cidade.replaceAll(" - ", "");
           
           
           String estado = tratar_endereco.tratar("Município: " + cidade, "CEP");
           estado = estado.replaceAll("-", "").trim();
           estado = estado.replaceAll(" ", "").trim();
           
           // JOptionPane.showMessageDialog(null, "Rua: " + rua + "\ncidade: " + cidade + "\nestado: " + estado);
   		
           vendedor_contrato.setRua(rua);
           vendedor_contrato.setCidade(cidade);
           vendedor_contrato.setUf(estado);
           
		}else {
			  inscricao_contrato = lines[linha_faz_lavoura + 1].replaceAll("[^0-9]", "");

			  
			
			
		}
           
		//dados do produto
		 String produto = "";
		String transgenia_proxima =  "";
		//percorre as linhas do array procurando a palavra soja
		for(int i = 0; i < lines.length; i++) {
			if(lines[i].contains("Soja GMO")) {
				produto = "SOJA GMO";
               transgenia_proxima =  "Transgenico(GMO)";
               break;
			}else if(lines[i].contains("Soja Non-GMO")) {
				produto = "SOJA NON-GMO";

				transgenia_proxima =  "Convencional(NON-GMO)";
	               break;
			}
		}
		
		//JOptionPane.showMessageDialog(null, "produto: " + produto);
		//JOptionPane.showMessageDialog(null, "transgenia: " + transgenia_proxima);

		//procura pelo produto no  banco de dados
		GerenciarBancoProdutos gerenciar_produtos = new GerenciarBancoProdutos();
		ArrayList<CadastroProduto> produtos = gerenciar_produtos.getProdutos();
		for(CadastroProduto prod : produtos) {
			if(prod.getNome_produto().equalsIgnoreCase(produto)) {
				if(prod.getTransgenia().equalsIgnoreCase(transgenia_proxima)) {
					contrato_local.setModelo_produto(prod);
					//JOptionPane.showMessageDialog(null, "Produto cadastrado");
					break;
				}
				
				
			}
			
		}
		
		//procura pela safra com este produto no banco de dados
		
		  GerenciarBancoSafras gerenciar_safra = new GerenciarBancoSafras();
		 
		ArrayList<CadastroSafra> safras = gerenciar_safra.getSafras();
		for(CadastroSafra saf : safras) {
			if(saf.getProduto().getId_produto() == contrato_local.getModelo_produto().getId_produto()) {
				//encontrado a safra que possui o produto deste contrato
				contrato_local.setModelo_safra(saf);
				//JOptionPane.showMessageDialog(null, "safra cadastrada");

				break;
				
			}
				
		}
		
		
		//quantidade do produto
		String quantidade = "";
		if(transgenia_proxima.equals("Transgenico(GMO)")) {
		    quantidade = tratamentoDados.tratar("modificados", " Kg");
		}else {
			quantidade = tratamentoDados.tratar("geneticamente", " Kg");

		}
		System.out.println("quaasdntidade: " + quantidade);
		quantidade = quantidade.replace("Quantidade:", "");
		quantidade = quantidade.replace(")", "");
		quantidade = quantidade.replaceFirst(",", "");
		quantidade = quantidade.replace(" ", "");

		quantidade = quantidade.trim();
		quantidade = quantidade.replace(".", "");
		double quantidade_double = new Double(quantidade);
		double quantidade_double_sacos = quantidade_double / 60;
		
		
		//seta a quantidade no contrato
		contrato_local.setQuantidade(quantidade_double_sacos);
		//seta a medida no contrato
		contrato_local.setMedida("Sacos");
		
		
		//valor
		String valor =  tratamentoDados.tratar("entre as partes em", "saca").replaceAll("[^0-9]+", "");
		//JOptionPane.showMessageDialog(null, "tamanho da string valor: " + valor.length());
		if(valor.length() == 4) {
			valor = valor.substring(0, 2).concat(".").concat( valor.substring(2 , 4));

		}else if (valor.length() > 4) {
			valor = valor.substring(0, 3).concat(".").concat( valor.substring(3 , 5));

		}
		//JOptionPane.showMessageDialog(null, "Valor encontrato: " + valor);
		double valor_double = Double.parseDouble(valor);
		
		//seta o valor por saco no contrato
		contrato_local.setValor_produto(valor_double);
		
		double valor_total = quantidade_double_sacos * valor_double;
		
		//seta o valor total no contrato
		contrato_local.setValor_a_pagar(new BigDecimal(Double.toString(valor_total)));
		
	
		
		//local retirada
		String local_retirada =  tratamentoDados.tratar("Local de Retirada: ", "Prazo").replaceFirst(",", "").trim(); 
		//JOptionPane.showMessageDialog(null, "Local retirada: " + local_retirada);
		
        CadastroCliente armazem = gerenciar.getCliente(local_retirada);
		
        if(armazem != null) {
        	contrato_local.setId_local_retirada(armazem.getId());
        }
		
		
		//prazo final da entrada
		String data_entrega = tratamentoDados.tratar("Prazo Final de Entrega: ", " ");
		contrato_local.setData_entrega(data_entrega);
		
		
		//data do contrato
		String mes_extenso = "";
		String mes_decimal = "";
		int linha_data = -1;
		for(int i = 0; i < lines.length; i++) {
			if(lines[i].contains("de janeiro de")) {
				mes_extenso = "janeiro"; 
				mes_decimal = "01";
				linha_data = i;
				break;
			}else if(lines[i].contains("de fevereiro de")) {
				mes_extenso = "fevereiro";   
				mes_decimal = "02";
				linha_data = i;

				break;

			}else if(lines[i].contains("de março de")) {
				mes_extenso = "março";      				 	
				mes_decimal = "03";
				linha_data = i;

				break;

			}else if(lines[i].contains("de abril de")) {
				mes_extenso = "abril";      				 	
				mes_decimal = "04";
				linha_data = i;

				break;

			}else if(lines[i].contains("de maio de")) {
				mes_extenso = "maio";      				 	
				mes_decimal = "05";
				linha_data = i;

				break;

			}else if(lines[i].contains("de junho de")) {
				mes_extenso = "junho";      				 	
				mes_decimal = "06";
				linha_data = i;

				break;

			}else if(lines[i].contains("de julho de")) {
				mes_extenso = "julho";      				 	
				mes_decimal = "07";
				linha_data = i;

				break;

			}else if(lines[i].contains("de agosto de")) {
				mes_extenso = "agosto";      				 	
				mes_decimal = "08";
				linha_data = i;
				break;


			}else if(lines[i].contains("de setembro de")) {
				mes_extenso = "setembro";      				 	
				mes_decimal = "09";
				linha_data = i;

				break;

			}else if(lines[i].contains("de outubro de")) {
				mes_extenso = "outubro";      				 	
				mes_decimal = "10";
				linha_data = i;

				break;

			}else if(lines[i].contains("de novembro de")) {
				mes_extenso = "novembro";      				 	
				mes_decimal = "11";
				linha_data = i;

				break;

			}else if(lines[i].contains("de dezembro de")) {
				mes_extenso = "dezembro"; 
				mes_decimal = "12";
				linha_data = i;

				break;


			}
		}
		
		
	
		
		
		 String local_data = lines[linha_data];
		 local_data = local_data.replace(",", "");
		 String conteudo_data[] = local_data.split(" ");
		 String local_assinatura = conteudo_data[0];
		 String dia_contrato = conteudo_data[1];
		 String ano_contrato = conteudo_data[conteudo_data.length - 1];
		 ano_contrato = ano_contrato.replace(".", "");
		 String data_completa = dia_contrato + "/" + mes_decimal + "/" + ano_contrato;
		 data_completa = data_completa.replace(".", "");
		 //seta a data do contrato
		 contrato_local.setData_contrato(data_completa);
		 
		 
		 //linha com informacoes do pagamento do contrato
			int linha_pagamento = -1;
			for(int i = 0; i < lines.length; i++) {
				if(lines[i].contains("3.5.")) {
					linha_pagamento = i;
					break;
				}
			}//quebra a linha de informacoes de pagamento
			String conteudo_pagamento []= lines[linha_pagamento + 1].split(" ");
			
		 //conta bancaria do pagagamento
		 String banco = tratamentoDados.tratar("crédito no ", " ");
		 String agencia =  tratamentoDados.tratar("Agência: ", "Conta").replace(",", "").trim();
		 String conta_corrente =   tratamentoDados.tratar("Conta", "em").replaceAll("[^0-9-]", "");
		 //  JOptionPane.showMessageDialog(null, "CC: " + conta_corrente);
		 String data_pagamento =  conteudo_pagamento[conteudo_pagamento.length - 1];
		 data_pagamento = data_pagamento.replace(".", "");
		 
		 ContaBancaria conta_pagamento = new ContaBancaria();
		 conta_pagamento.setBanco(banco);
		 conta_pagamento.setAgencia(agencia);
		 conta_pagamento.setConta(conta_corrente);
		 conta_pagamento.setCpf_titular(identificacao_vendedor);
		 conta_pagamento.setNome(nome_vendedor);
		
		 boolean cadastrar_contrato = false;
		 int id_conta_pagamento = -1;
		if(vendedor_cadastrado) {
			
			//o vendedor esta cadastrador, agora verifica se a instricao estadual do contrato e a mesma do cadastro
			if(vendedor_contrato.getIe().equals(inscricao_contrato)) {
				
				// o vendedor tem a mesma inscricao estadual, verifica se tem a conta bancaria
				 //verifica se o vendedor possui a conta bancaria do cadastro
				boolean tem_conta = false;
				
				GerenciarBancoClientes gerenciar_procurar = new GerenciarBancoClientes();
				   ArrayList<ContaBancaria> contas = gerenciar_procurar.getContas(vendedor_contrato.getId());
				   for(ContaBancaria conta : contas) {
					   if(conta.getAgencia().equals(agencia)) {
						   if(conta.getConta().equals(conta_corrente)) {
							   //conta do contrato encontrada
							   tem_conta = true;
							   id_conta_pagamento = conta.getId_conta();
						   }
					   }
				   }
				   
				if(tem_conta) {
						JOptionPane.showMessageDialog(null, 
						"Codigo do contrato: " + codigo_contrato + 
						"\nComprador: " + comprador_contrato.getNome_fantaia()+
						"\nVendedor: " + nome_vendedor+
						"\nIE do Vendedor: " + vendedor_contrato.getIe() +
						"\nProduto: " + produto + 
						"\nSafra: " + safra +
						"\nQuantidade(KG): " + quantidade_double + 
    					"\nnQuantidade(Sacos): " + quantidade_double_sacos +
    					"\nValor por saco: " + valor + 
    					"\nValor total: " + valor_total +
    					
    					"\nFazenda Contrato: " + fazenda_lavoura+
    					"\nInscricao Contrato: " + inscricao_contrato +
    					"\nArmazenagem: " + armazenagem + 
    					"\nFrete: " + frete + 
    					"\nLocal Retirada: " + local_retirada +
    					"\nData entrega: " + data_entrega +
    			        "\nData do contrato: " + data_completa + "\nLocal: " + local_assinatura+
    			        "\nBanco: " + banco + 
    			        "\nAgencia: " + agencia + 
    			        "\nCC: " + conta_corrente + 
    			        "\nData Pagamento: " + data_pagamento);
				
				cadastrar_contrato = true;
				
				//abre a tela de cadastro de contrato
				}else {
					//cadastra a conta bancaria
					ContaBancaria conta_cadastrar = new ContaBancaria();
					conta_cadastrar.setAgencia(agencia);
					conta_cadastrar.setBanco(banco);
					conta_cadastrar.setConta(conta_corrente);
					if(vendedor_contrato.getTipo_pessoa() == 0) {
						//pessoa fisica
						conta_cadastrar.setNome(vendedor_contrato.getNome_empresarial());
						conta_cadastrar.setCpf_titular(vendedor_contrato.getCpf());

					}else {
						//pessoa juridica
						conta_cadastrar.setNome(vendedor_contrato.getNome_fantaia());
						conta_cadastrar.setCpf_titular(vendedor_contrato.getCnpj());
						
					}
					
					//cadastrar a conta
					GerenciarBancoClientes gerenciar_cadastrar = new GerenciarBancoClientes();
					ArrayList<ContaBancaria> contas_cadastrar = new ArrayList<>();
					contas_cadastrar.add(conta_cadastrar);
                    gerenciar_cadastrar.adicionarContaBancaria(contas_cadastrar, vendedor_contrato.getId());
                    
                    //verifica se a nota foi conta foi cadastrada
                    boolean tem_conta_replier = false;
					
					GerenciarBancoClientes gerenciar_procurar_novamente = new GerenciarBancoClientes();
					   ArrayList<ContaBancaria> contas_2_pesquisa = gerenciar_procurar_novamente.getContas(vendedor_contrato.getId());
					   for(ContaBancaria conta : contas_2_pesquisa) {
						   if(conta.getAgencia().equals(agencia)) {
							   if(conta.getConta().equals(conta_corrente)) {
								   //conta do contrato encontrada
								   tem_conta_replier = true;
								   id_conta_pagamento = conta.getId_conta();

							   }
						   }
					   }
					   
					   if(tem_conta_replier) {
						   //a conta foi cadastradaasdas
						   cadastrar_contrato = true;
	    					

						   
					   }else {
						   //a conta nao foi cadastrada
							JOptionPane.showMessageDialog(null, "O vendedor esta cadastrado, mas não possui a conta bancaria do pagamento deste contrato\n"
		    							+ " Tente adicionar manualmente!");
						   cadastrar_contrato = false;
					   }
					
					
				}
			}else {
				
				//informa ao usuario que o vendedor possui o cadastro, mas a inscricao estadual nao e a mesma, 
				//e pergunta se ele deseja cadastrar
				JOptionPane.showMessageDialog(null, 
						"Codigo do contrato: " + codigo_contrato + 
						"\nComprador: " + comprador_contrato.getNome_fantaia()+
						"\nVendedor: " + nome_vendedor+
						"\nIE do Vendedor: " + vendedor_contrato.getIe() +
						"\nProduto: " + produto + 
						"\nSafra: " + safra +
						"\nQuantidade(KG): " + quantidade_double + 
    					"\nnQuantidade(Sacos): " + quantidade_double_sacos +
    					"\nValor por saco: " + valor + 
    					"\nValor total: " + valor_total +
    					
    					"\nFazenda Contrato: " + fazenda_lavoura+
    					"\nInscricao Contrato: " + inscricao_contrato +
    					"\nArmazenagem: " + armazenagem + 
    					"\nFrete: " + frete + 
    					"\nLocal Retirada: " + local_retirada +
    					"\nData entrega: " + data_entrega +
    			        "\nData do contrato: " + data_completa + "\nLocal: " + local_assinatura+
    			        "\nBanco: " + banco + 
    			        "\nAgencia: " + agencia + 
    			        "\nCC: " + conta_corrente + 
    			        "\nData Pagamento: " + data_pagamento);
				
				
				if (JOptionPane.showConfirmDialog(null, 
			            "Apesar do Vendedor do Contrato já possur um cadastro"
			            + " sua I.E não é a mesma do contrato."
			            + "\nDeseja cadastrar o vendedor do contrato com  a nova Inscrição Estadual?", "Cadastrar Vendedor Com Nova IE", 
			            JOptionPane.YES_NO_OPTION,
			            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					    
					TelaCadastroCliente tela = new TelaCadastroCliente(1, null,null);
					vendedor_contrato.setIe(inscricao_contrato);
					tela.setInformacoesNovoCliente(vendedor_contrato, uf_inscricao_contrato, conta_pagamento);

					tela.setVisible(true);

					
					//verifica se o vendedor foi cadastrado:
					
					   GerenciarBancoClientes gerenciar_vendedores = new GerenciarBancoClientes();
		    		     ArrayList<CadastroCliente> vendedores = gerenciar.getClientes(0, 0, "");
		    		     
		    		     boolean vendedor_foi_cadastrado  = false;
		    		     boolean conta_bancaria_cadastrada = false;
		    			for(CadastroCliente vend : vendedores) {
		    			   if(vendedor_contrato.getTipo_pessoa() == 0) {
		    				   //o vendedor do contrato e uma pessoa fisica
		    				   if(vend.getTipo_pessoa() == 0) {
		    					   if(vend.getCpf().equals(vendedor_contrato.getCpf())) {

		    						   if(vend.getIe().equals(inscricao_contrato)) {

		    						   //vendedor foi encontrado
		    						   vendedor_contrato = vend;
		    						   
		    						   //verifica se o vendedor possui a conta bancaria do cadastro
		    						   ArrayList<ContaBancaria> contas = gerenciar_vendedores.getContas(vend.getId());
		    						   for(ContaBancaria conta : contas) {
		    							   if(conta.getAgencia().equals(agencia)) {
		    								   if(conta.getConta().equals(conta_corrente)) {
		    									   //conta do contrato encontrada
		    									   conta_bancaria_cadastrada = true;
		    									   id_conta_pagamento = conta.getId_conta();

		    								   }
		    							   }
		    						   }
		    						   
		    						   vendedor_foi_cadastrado = true;
		    						   break;
		    						   }
		    					   }
		    				   }
		    				   
		    			   }else {
		    				   //o vendedor do contrato e uma pessoa juridica
                                   if(vend.getTipo_pessoa() == 1) {
                                	   if(vend.getCnpj().equals(vendedor_contrato.getCnpj())) {
    		    						   if(vend.getIe().equals(inscricao_contrato)) {
                                		   //vendedor foi encontrado
    		    						   vendedor_contrato = vend;
    		    						   
    		    						   //verifica se o vendedor possui a conta bancaria do cadastro
    		    						   ArrayList<ContaBancaria> contas = gerenciar_vendedores.getContas(vend.getId());
    		    						   for(ContaBancaria conta : contas) {
    		    							   if(conta.getAgencia().equals(agencia)) {
    		    								   if(conta.getConta().equals(conta_corrente)) {
    		    									   //conta do contrato encontrada
    		    									   conta_bancaria_cadastrada = true;
    		    									   id_conta_pagamento = conta.getId_conta();

    		    								   }
    		    							   }
    		    						   }
    		    						   
    		    						   vendedor_foi_cadastrado = true;
    		    						   break;
                                	   }
    		    					   }
		    				   }
		    			   }
		    			
		    			}
		    			
		    			if(vendedor_foi_cadastrado) {
		    				if(conta_bancaria_cadastrada) {
		    					JOptionPane.showMessageDialog(null, "O vendedor que nao estava cadastrado para a IE do contrato, agora esta cadastrado!");
    		    				
		    					
		    					cadastrar_contrato = true;
		    				}else {
		    					JOptionPane.showMessageDialog(null, "O vendedor que nao estava cadastrado para a IE do contrato, agora esta cadastrado, porem a conta bancaria nao foi encontrada!");
    		    				cadastrar_contrato = true;
		    				}
		    			
		    			}else {
		    				JOptionPane.showMessageDialog(null, "O vendedor que nao estava cadastrado, não foi cadastrado ainda, tente novamente!");
		    				cadastrar_contrato = false;
		    			}
			}
			}

		}else {
			JOptionPane.showMessageDialog(null, 
					"Codigo do contrato: " + codigo_contrato + 
					"\nComprador: " + comprador_contrato.getNome_fantaia()+
					"\nProduto: " + produto + 
					"\nSafra: " + safra +
					"\nQuantidade(KG): " + quantidade_double + 
					"\nnQuantidade(Sacos): " + quantidade_double_sacos +
					"\nValor por saco: " + valor + 
					"\nValor total: " + valor_total +
					
					"\nFazenda Contrato: " + fazenda_lavoura+
					"\nInscricao Contrato: " + inscricao_contrato +
					"\nArmazenagem: " + armazenagem + 
					"\nFrete: " + frete + 
					"\nLocal Retirada: " + local_retirada +
					"\nData entrega: " + data_entrega +
			        "\nData do contrato: " + data_completa + "\nLocal: " + local_assinatura+
			        "\nBanco: " + banco + 
			        "\nAgencia: " + agencia + 
			        "\nCC: " + conta_corrente + 
			        "\nData Pagamento: " + data_pagamento+
			        "\nVendedor do contrato não esta cadastrado: "	+
			        "\nTipo do Vendedor: " + tipo_vendedor +
			        "\nCPF/CNPJ: " + identificacao_vendedor);
			
			
			if (JOptionPane.showConfirmDialog(null, 
		            "Deseja cadastrar o vendedor do contrato?", "Cadastrar Vendedor", 
		            JOptionPane.YES_NO_OPTION,
		            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
				    
				TelaCadastroCliente tela = new TelaCadastroCliente(1, null,null);
				vendedor_contrato.setIe(inscricao_contrato);

				tela.setInformacoesNovoCliente(vendedor_contrato, uf_inscricao_contrato, conta_pagamento);

				tela.setVisible(true);

				
				//verifica se o vendedor foi cadastrado:
				
				   GerenciarBancoClientes gerenciar_vendedores = new GerenciarBancoClientes();
	    		     ArrayList<CadastroCliente> vendedores = gerenciar.getClientes(0, 0, "");
	    		     
	    		     boolean vendedor_foi_cadastrado  = false;
	    		     boolean conta_bancaria_cadastrada = false;
	    		     
	    			for(CadastroCliente vend : vendedores) {
	    			   if(vendedor_contrato.getTipo_pessoa() == 0) {
	    				   //o vendedor do contrato e uma pessoa fisica
	    				   if(vend.getTipo_pessoa() == 0) {
	    					   if(vend.getCpf().equals(vendedor_contrato.getCpf())) {
	    						   //verifica a inscricao estadual
	    						   if(vend.getIe().equals(inscricao_contrato)) {
	    							   //vendedor foi encontrado
		    						   vendedor_contrato = vend;
		    						   
		    						   //verifica se o vendedor possui a conta bancaria do cadastro
		    						   ArrayList<ContaBancaria> contas = gerenciar_vendedores.getContas(vend.getId());
		    						   for(ContaBancaria conta : contas) {
		    							   if(conta.getAgencia().equals(agencia)) {
		    								   if(conta.getConta().equals(conta_corrente)) {
		    									   //conta do contrato encontrada
		    									   conta_bancaria_cadastrada = true;
		    									   id_conta_pagamento = conta.getId_conta();

		    								   }
		    							   }
		    						   }
		    						   vendedor_foi_cadastrado = true;
		    						   break;
	    						   }
	    						  
	    					   }
	    				   }
	    				   
	    			   }else {
	    				   //o vendedor do contrato e uma pessoa juridica
                               if(vend.getTipo_pessoa() == 1) {
                            	   if(vend.getCnpj().equals(vendedor_contrato.getCnpj())) {
		    						   //vendedor foi encontrado
                            		   if(vend.getIe().equals(inscricao_contrato)) {
		    						   vendedor_contrato = vend;
		    						   //verifica se o vendedor possui a conta bancaria do cadastro
		    						   ArrayList<ContaBancaria> contas = gerenciar_vendedores.getContas(vend.getId());
		    						   for(ContaBancaria conta : contas) {
		    							   if(conta.getAgencia().equals(agencia)) {
		    								   if(conta.getConta().equals(conta_corrente)) {
		    									   //conta do contrato encontrada
		    									   conta_bancaria_cadastrada = true;
		    									   id_conta_pagamento = conta.getId_conta();

		    								   }
		    							   }
		    						   }
		    						   vendedor_foi_cadastrado = true;
		    						   break;
                            		   }
		    					   }
	    				   }
	    			   }
	    			
	    			}
	    			
	    			if(vendedor_foi_cadastrado) {
	    				if(conta_bancaria_cadastrada) {
	    					JOptionPane.showMessageDialog(null, "O vendedor que nao estava cadastrado, agora esta cadastrado!");
		    				cadastrar_contrato = true;
	    				}else {
	    					JOptionPane.showMessageDialog(null, "O vendedor que nao estava cadastrado, foi cadastrado, mas a conta bancaria para o contrato nao foi encontrada!");
		    				cadastrar_contrato = false;
	    				}
	    				
	    			}else {
	    				JOptionPane.showMessageDialog(null, "O vendedor que nao estava cadastrado, não foi cadastrado ainda, tente novamente!");
	    				cadastrar_contrato = false;
	    			}
	    			
	    			
			}  
			else
			{
				
				cadastrar_contrato = false;
			}
			
		
			
		}
    
		

		if(cadastrar_contrato) {
			boolean ja_existe = false;
			
			//verifica pelo codigo do contrato se ele ja nao esta cadastrado
			GerenciarBancoContratos gerenciar_cts = new GerenciarBancoContratos();
			ArrayList<CadastroContrato> contratos = gerenciar_cts.getContratos();
			
			for(CadastroContrato ctr : contratos) {
				if(ctr.getCodigo().equals(codigo_contrato)) {
					ja_existe  = true;
					break;
				}
			}
			
			if(!ja_existe) {
				//contrato nao existe, cadastrar
				JOptionPane.showMessageDialog(null, "Hapto para realizar o cadastro do contrato!");
				
				//seta os compradores do contrato
    			CadastroCliente compradores [] = new CadastroCliente[3];
    			compradores[0] = comprador_contrato;
    			
    			contrato_local.setCompradores(compradores);
    			
    			//seta os vendedores do contrato
    			
    		
    				CadastroCliente vendedores [] = new CadastroCliente[3];
    				vendedores[0] = vendedor_contrato;
        			
    			
        			contrato_local.setVendedores(vendedores);
        			
        			
        			
    			
				//adicionar pagamento ao contrato
        		ArrayList<CadastroContrato.CadastroPagamento> pagamentos = new ArrayList<>();
        		
        		CadastroContrato.CadastroPagamento modeloPag = new CadastroContrato.CadastroPagamento();
				modeloPag.setConta(gerenciar.getConta(id_conta_pagamento));
				modeloPag.setData_pagamento(data_pagamento);
				modeloPag.setValor(new BigDecimal(Double.toString(valor_total)));
				
				pagamentos.add(modeloPag);
				contrato_local.setPagamentos(pagamentos);
				
				//adicionar a tarefa
				
				ArrayList<CadastroContrato.CadastroTarefa> tarefas = new ArrayList<>();
				CadastroContrato.CadastroTarefa tarefa = new CadastroContrato.CadastroTarefa();

			
					tarefa.setNome_tarefa("Contrato Importado");
			
				// cria a tarefa de insercao de contrato
				tarefa.setId_tarefa(0);
				tarefa.setDescricao_tarefa("Criação de tarefa");

				tarefa.setStatus_tarefa(1);
				tarefa.setMensagem("tarefa criada");

				GetData data = new GetData();
				tarefa.setHora(data.getHora());
				tarefa.setData(data.getData());
				tarefa.setHora_agendada(data.getHora());
				tarefa.setData_agendada(data.getData());

				tarefa.setCriador(login);
				tarefa.setExecutor(login);

				tarefa.setPrioridade(1);
				
				tarefas.add(tarefa);
				
				contrato_local.setLista_tarefas(tarefas);
                contrato_local.setStatus_contrato(1);
				
			
				
					//copiar o arquivo para a pasta do comprador
					
					String nome_comprador_arquivo ;
					String nome_vendedor_arquivo;
				
					
					if(compradores[0].getTipo_pessoa() == 0)
						nome_comprador_arquivo = compradores[0].getNome_empresarial();
					else
						nome_comprador_arquivo = compradores[0].getNome_fantaia();
					
					if(vendedores[0].getTipo_pessoa() == 0)
						nome_vendedor_arquivo = vendedores[0].getNome_empresarial();
					else
						nome_vendedor_arquivo = vendedores[0].getNome_fantaia();

					String servidor_unidade = configs_globais.getServidorUnidade();

					   //é um comprato pai, salvar na pasta do comprador

						 String caminho_salvar_contrato__no_hd = servidor_unidade + "E-contract\\arquivos\\clientes\\" + nome_comprador_arquivo + "\\contratos" + "\\compra\\"  + ano_contrato + "\\" + contrato_local.getModelo_safra().getProduto().getNome_produto() + "\\";
						System.out.println("caminho para salvar o contrato do comprador no hd: " + caminho_salvar_contrato__no_hd);	
						String caminho_salvar_contrato_no_banco_dados = "E-contract\\\\arquivos\\\\clientes\\\\" + nome_comprador_arquivo + "\\\\contratos" + "\\\\compra\\\\"  + ano_contrato + "\\\\" + contrato_local.getModelo_safra().getProduto().getNome_produto() + "\\\\";

					      String nome_pasta_arquivo = contrato_local.getCodigo();

					      String nome_arquivo = contrato_local.getCodigo() + " "  + nome_comprador_arquivo + " x " + nome_vendedor_arquivo ; 
					     
							
							String caminho_completo_salvar_contrato_no_hd = caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\" + nome_arquivo ;
							
						    String caminho_completo_salvar_contrato_no_bando_dados = caminho_salvar_contrato_no_banco_dados + nome_pasta_arquivo +"\\\\" + nome_arquivo;
						    String nome_diretorio_arquivo_contrato = caminho_salvar_contrato_no_banco_dados + nome_pasta_arquivo;

							//criar o diretorio
							ManipularTxt manipular = new ManipularTxt();
							if(manipular.criarDiretorio(caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\"))
							{
								System.out.println("diretorio criado para o novo contrato");

								//criar pastas documentos e comprovantes
           						if(manipular.criarDiretorio(caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\comprovantes"))
    							{
    					    		  //criar diretorio documentos
    								  if(manipular.criarDiretorio(caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\documentos"))
    									{

    									}else {

    									}	

    							}else {

    							}
           						
								 //copiar arquivo para o novo diretorio
								boolean copiar = manipular.copiarNFe(file.getAbsolutePath(), caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\" + nome_arquivo + ".pdf" );
                                   if(copiar) {
               						JOptionPane.showMessageDialog(null, "Arquivo copiado para a nova pasta!");
               						
               						//criar pastas documentos e comprovantes
               						if(manipular.criarDiretorio(caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\comprovantes"))
        							{
        					    		  //criar diretorio documentos
        								  if(manipular.criarDiretorio(caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\documentos"))
        									{

        									}else {

        									}	

        							}else {

        							}
               						
               						
               						
               						//inserir o contrato no banco de dados
               						contrato_local.setCaminho_diretorio_contrato(nome_diretorio_arquivo_contrato);
               						contrato_local.setCaminho_arquivo(caminho_salvar_contrato_no_banco_dados + nome_pasta_arquivo + "\\\\" + nome_arquivo + ".pdf");
               						contrato_local.setNome_arquivo(nome_arquivo + ".pdf");
               						
                					GerenciarBancoContratos gerenciarContratos = new GerenciarBancoContratos();
                					
                					int insercao = gerenciarContratos.inserirContrato(contrato_local, null);
                					if(insercao == 1) {
                  						JOptionPane.showMessageDialog(null, "O Contrato foi salvo no banco de dados");
                  						
                  						//inserir o contrato na nuvem
                  						 Nuvem nuvem = new Nuvem();
                  		            	 nuvem.abrir();
                  		                 nuvem.testar();
                  		                
                  		                boolean retorno = nuvem.carregar(caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\" + nome_arquivo + ".pdf", nome_arquivo + ".pdf");
                  		                 if(retorno) {
                       						JOptionPane.showMessageDialog(null, "O Contrato  foi salvo na nuvem");
                  		                 }else {
                       						JOptionPane.showMessageDialog(null, "O Contrato não foi salvo na nuvem");

                  		                 }

                					}else {
                  						JOptionPane.showMessageDialog(null, "O Contrato não foi salvo no banco de dados");

                					}

                                   }else {
                  						JOptionPane.showMessageDialog(null, "O arquivo não foi copiado para  nova pasta, tente novamente");

                                   }
								
							}else {
								System.out.println("erro ao criar diretorio para o contrato ");
          						JOptionPane.showMessageDialog(null, "O arquivo não foi copiado para  nova pasta, tente novamente");

								
							}
					
				
				
				
				
				
				
				
				
			}else {
				JOptionPane.showMessageDialog(null, "O Contrato de codigo " + contrato_local.getCodigo() + " já esta cadastrado!");

			}
			
			
			
		}else {
			JOptionPane.showMessageDialog(null, "Não hapto para realizar o cadastro do contrato!");

		}
		
		
		return contrato_local;
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao importar contrato cj selecta: \nTente a Importação Manual\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
			return null;
		}
		
		
	}
	
	
	public CadastroContrato contratoGavilon(CadastroContrato contrato_local) {

		try {
		String codigo_contrato = tratamentoDados.tratar("Nº ", " ");
		//JOptionPane.showMessageDialog(null, "Codigo do contrato: " + codigo_contrato);
		contrato_local.setCodigo(codigo_contrato);
		contrato_local.setSub_contrato(3);
		
        contrato_local.setFrete("Não Especificado");
        contrato_local.setArmazenagem("Não Especificado");


		CadastroCliente comprador_contrato = new CadastroCliente();
		int linha_cnpj = - 1;
		String cnpj_comprador = "";
		for(int i = 0; i < lines.length; i++) {
			if(lines[i].contains("CNPJ/MF (matriz):")) {
				linha_cnpj = i;  
               break;
			}
		}
		 cnpj_comprador = lines[linha_cnpj].replaceAll("[^0-9]+", "").trim();

		//procura nos clientes se existe este comprador
	     GerenciarBancoClientes gerenciar = new GerenciarBancoClientes();
	     ArrayList<CadastroCliente> clientes = gerenciar.getClientes(0, 0, "");
	     
		for(CadastroCliente cliente : clientes) {
			if(cliente.getTipo_pessoa() == 1) {

			if(cliente.getCnpj().equals(cnpj_comprador)) {
				comprador_contrato = cliente;
				break;
			}
			}
		}
		
		
		
		CadastroCliente vendedor_contrato = new CadastroCliente();

		String identificacao_vendedor = tratamentoDados.tratar("CNPJ/MF/CPF: ", " ");
		identificacao_vendedor = identificacao_vendedor.replaceAll("[^0-9]+", "");
		
		
		int linha_nome_vendedor = - 1;
		for(int i = 0; i < lines.length; i++) {
			if(lines[i].contains("Vendedor:")) {
				linha_nome_vendedor = i;  
               break;
			}
		}
		
		String nome_vendedor_busca = lines[linha_nome_vendedor] + ";";
		TratarDados tratar = new TratarDados(nome_vendedor_busca);
		String nome_vendedor = tratar.tratar("Vendedor: ", ";");
		
		String nome_vendedor_quebrado [] = nome_vendedor.split(" ");
		vendedor_contrato.setNome(nome_vendedor_quebrado[0]);
		String sobre_nome = "";
		for(int i = 1; i <nome_vendedor_quebrado.length; i++) {
			sobre_nome = sobre_nome + " " + nome_vendedor_quebrado[i];
		}
		vendedor_contrato.setSobrenome(sobre_nome);
		
        int tipo_vendedor = -1;
		

        if(identificacao_vendedor.length() == 11) {
        	tipo_vendedor = 0;
        	vendedor_contrato.setTipo_pessoa(0);
        	vendedor_contrato.setCpf(identificacao_vendedor);
        	vendedor_contrato.setNome_empresarial(nome_vendedor);
        }
        else if(identificacao_vendedor.length() == 14) {
        	tipo_vendedor = 1;
        	vendedor_contrato.setTipo_pessoa(1);
        	vendedor_contrato.setCnpj(identificacao_vendedor);
        	vendedor_contrato.setNome_fantaia(nome_vendedor);

        }
		
        boolean vendedor_cadastrado = false;
		for(CadastroCliente cliente : clientes) {
			
			if(tipo_vendedor == 0) {
				if(cliente.getTipo_pessoa() == 0) {
					if(cliente.getCpf().equals(identificacao_vendedor)) {
						vendedor_contrato = cliente;
						vendedor_cadastrado  = true;
					break;
					}
				}
				
			}else {
				if(cliente.getTipo_pessoa() == 1) {
					if(cliente.getCnpj().equals(identificacao_vendedor)) {
						vendedor_contrato = cliente;
						vendedor_cadastrado  = true;

					break;
					}
				}
			}
			
		}
		
	
		
		//seta os vendedores do contrato
		
		if(vendedor_cadastrado) {
			
			if(vendedor_contrato.getTipo_pessoa() == 0) {
				//pessoa fisica
				nome_vendedor = vendedor_contrato.getNome_empresarial();
			}else {
				nome_vendedor = vendedor_contrato.getNome_fantaia();

			}
			
			
			
		}
		
	
		
		//safra
		String safra = "";
		for(int i = 0; i < lines.length; i++) {
			if(lines[i].contains("2020/2021")) {
               safra = "2020/2021";  
               break;
			}else if(lines[i].contains("2021/2021")) {
                safra = "2021/2021";  
                break;
			}else if(lines[i].contains("2021/2022")) {
                safra = "2021/2022"; 
                break;

			}else if(lines[i].contains("2022/2022")) {
                safra = "2022/2022"; 
                break;

			}else if(lines[i].contains("2022/2023")) {
                safra = "2022/2023";   
                break;

			}else if(lines[i].contains("2023/2023")) {
                safra = "2023/2023";      				 	

                break;
			}else if(lines[i].contains("2023/2024")) {
                safra = "2023/2024";   
                break;

			}
		}

	
		//encontrar a linha que contem o endereco da fazenda
		 int linha_endereco = -1;
		for(int i = 0; i < lines.length; i++) {
			if(lines[i].contains("Endereço: ")) {
				linha_endereco = i; 
               break;
			}
		}
		
		
		String fazenda_lavoura = tratamentoDados.tratar("Endereço: ", " ");
		String fazenda_lavoura_linha = lines[linha_endereco];
		 String uf_inscricao_contrato = fazenda_lavoura_linha.substring(fazenda_lavoura_linha.length() - 3,fazenda_lavoura_linha.length()-1);
		
		 String inscricao_contrato = tratamentoDados.tratar("Inscrição Estadual: ", " ");
		
		 ////JOptionPane.showMessageDialog(null, "Enderço: " + fazenda_lavoura_linha);
			System.out.println("Endereço: " + fazenda_lavoura_linha);
			
		String endereco = fazenda_lavoura_linha.replaceAll("Endereço: ", "");
		String endereco_quebrado[] = endereco.split(",");
		String rua = endereco_quebrado[0]; 
		String cidade = endereco_quebrado[2].trim(); 
		String estado = endereco_quebrado[3].trim(); 

		//JOptionPane.showMessageDialog(null, "Rua: " + rua + "\ncidade: " + cidade + "\nestado: " + estado);
		
        vendedor_contrato.setRua(rua);
        vendedor_contrato.setCidade(cidade);
        vendedor_contrato.setUf(estado);
        
		//dados do produto
		 String produto = "";
		String transgenia_proxima =  "";
		//percorre as linhas do array procurando a palavra soja
		for(int i = 0; i < lines.length; i++) {
			if(lines[i].contains("Soja GMO")) {
				produto = "SOJA GMO";
               transgenia_proxima =  "Transgenico(GMO)";
               break;
			}else if((lines[i].contains("soja"))){
				produto = "SOJA GMO";

				transgenia_proxima =  "Transgenico(GMO)";
	               break;
			}
		}
		
		
		//procura pelo produto no  banco de dados
		GerenciarBancoProdutos gerenciar_produtos = new GerenciarBancoProdutos();
		ArrayList<CadastroProduto> produtos = gerenciar_produtos.getProdutos();
		for(CadastroProduto prod : produtos) {
			if(prod.getNome_produto().equalsIgnoreCase(produto)) {
				if(prod.getTransgenia().equalsIgnoreCase(transgenia_proxima)) {
					contrato_local.setModelo_produto(prod);
					break;
				}
				
				
			}
			
		}
		
		//procura pela safra com este produto no banco de dados
		
		  GerenciarBancoSafras gerenciar_safra = new GerenciarBancoSafras();
		 
		ArrayList<CadastroSafra> safras = gerenciar_safra.getSafras();
		for(CadastroSafra saf : safras) {
			if(saf.getProduto().getId_produto() == contrato_local.getModelo_produto().getId_produto()) {
				//encontrado a safra que possui o produto deste contrato
				contrato_local.setModelo_safra(saf);
				break;
				
			}
				
		}
		
		
		//quantidade do produto
		String quantidade = tratamentoDados.tratar("venda de ", " ");
		System.out.println("quaasdntidade: " + quantidade);
		quantidade = quantidade.replace("Quantidade:", "");
		quantidade = quantidade.replace(")", "");
		quantidade = quantidade.replaceFirst(",", "");
		quantidade = quantidade.replace(".", "");

		quantidade = quantidade.trim();
		//quantidade = quantidade.replace(".", "");
		double quantidade_double = Double.parseDouble(quantidade);
		double quantidade_double_sacos = quantidade_double / 60;
		
		
		//seta a quantidade no contrato
		contrato_local.setQuantidade(quantidade_double_sacos);
		//seta a medida no contrato
		contrato_local.setMedida("Sacos");
		
		
		//valor
		String valor =  tratamentoDados.tratar("irreajustável", "saca").replaceAll("[^0-9]+", "");
		valor = valor.substring(0, 2).concat(".").concat( valor.substring(2 , 4));
		double valor_double = Double.parseDouble(valor);
		
		//seta o valor por saco no contrato
		contrato_local.setValor_produto(valor_double);
		
		double valor_total = quantidade_double_sacos * valor_double;
		
		//seta o valor total no contrato
		contrato_local.setValor_a_pagar(new BigDecimal(Double.toString(valor_total)));
		
		
		
		
		//prazo final da entrada

		int linha_data_entrega = -1;
		for(int i = 0; i < lines.length; i++) {
			if(lines[i].contains("3.1.")) {
				linha_data_entrega = i+1;
				break;
			}
		}
		
		String linha_busca = lines[linha_data_entrega];
		String data_entrega =  linha_busca.substring(linha_busca.length() - 12,  linha_busca.length() - 1);
		contrato_local.setData_entrega(data_entrega);
		
		
		//local retirada
		String local_retirada = tratamentoDados.tratar("localizado no ", " em");
		
		
		//data do contrato
		String mes_extenso = "";
		String mes_decimal = "";
		int linha_data = -1;
		for(int i = 0; i < lines.length; i++) {
			if(lines[i].contains("de Janeiro de")) {
				mes_extenso = "janeiro"; 
				mes_decimal = "01";
				linha_data = i;
				break;
			}else if(lines[i].contains("de Fevereiro de")) {
				mes_extenso = "fevereiro";   
				mes_decimal = "02";
				linha_data = i;

				break;

			}else if(lines[i].contains("de Março de")) {
				mes_extenso = "março";      				 	
				mes_decimal = "03";
				linha_data = i;

				break;

			}else if(lines[i].contains("de Abril de")) {
				mes_extenso = "abril";      				 	
				mes_decimal = "04";
				linha_data = i;

				break;

			}else if(lines[i].contains("de Maio de")) {
				mes_extenso = "maio";      				 	
				mes_decimal = "05";
				linha_data = i;

				break;

			}else if(lines[i].contains("de Junho de")) {
				mes_extenso = "junho";      				 	
				mes_decimal = "06";
				linha_data = i;

				break;

			}else if(lines[i].contains("de Julho de")) {
				mes_extenso = "julho";      				 	
				mes_decimal = "07";
				linha_data = i;

				break;

			}else if(lines[i].contains("de Agosto de")) {
				mes_extenso = "agosto";      				 	
				mes_decimal = "08";
				linha_data = i;
				break;


			}else if(lines[i].contains("de Setembro de")) {
				mes_extenso = "setembro";      				 	
				mes_decimal = "09";
				linha_data = i;

				break;

			}else if(lines[i].contains("de Outubro de")) {
				mes_extenso = "outubro";      				 	
				mes_decimal = "10";
				linha_data = i;

				break;

			}else if(lines[i].contains("de Novembro de")) {
				mes_extenso = "novembro";      				 	
				mes_decimal = "11";
				linha_data = i;

				break;

			}else if(lines[i].contains("de Dezembro de")) {
				mes_extenso = "dezembro"; 
				mes_decimal = "12";
				linha_data = i;

				break;


			}
		}
		
		
	
		
		
		 String local_data = lines[linha_data];
		 local_data = local_data.replace(",", "");
		 String conteudo_data[] = local_data.split(" ");
		 String local_assinatura = conteudo_data[0];
	
		 String dia_contrato = conteudo_data[conteudo_data.length - 5];
		 String ano_contrato = conteudo_data[conteudo_data.length - 1];
		 ano_contrato = ano_contrato.replace(".", "");
		 String data_completa = dia_contrato + "/" + mes_decimal + "/" + ano_contrato;
		 data_completa = data_completa.replace(".", "");
		 //seta a data do contrato
		 contrato_local.setData_contrato(data_completa);
		 
		 
		 
		 //linha com informacoes do pagamento do contrato
			int linha_pagamento = -1;
			for(int i = 0; i < lines.length; i++) {
				if(lines[i].contains("3.5.")) {
					linha_pagamento = i;
					break;
				}
			}//quebra a linha de informacoes de pagamento
			String conteudo_pagamento []= lines[linha_pagamento + 1].split(" ");
			
		 //conta bancaria do pagagamento
		 String banco = tratamentoDados.tratar("do Banco ", ",");
		 String agencia =  tratamentoDados.tratar("agência n.º ", "do").trim();
		 String conta_corrente =   tratamentoDados.tratar("corrente n.º ", "da").replaceAll("[^0-9-]", "");
		 String data_pagamento =  tratamentoDados.tratar("Vendedor até ", ",");
		
	
		ContaBancaria conta_pagamento = new ContaBancaria();
		 conta_pagamento.setBanco(banco);
		 conta_pagamento.setAgencia(agencia);
		 conta_pagamento.setConta(conta_corrente);
		 conta_pagamento.setCpf_titular(identificacao_vendedor);
		 conta_pagamento.setNome(nome_vendedor);
		
		 boolean cadastrar_contrato = false;
		 int id_conta_pagamento = -1;
		if(vendedor_cadastrado) {
			
			//o vendedor esta cadastrador, agora verifica se a instricao estadual do contrato e a mesma do cadastro
			if(vendedor_contrato.getIe().equals(inscricao_contrato)) {
				
				// o vendedor tem a mesma inscricao estadual, verifica se tem a conta bancaria
				 //verifica se o vendedor possui a conta bancaria do cadastro
				boolean tem_conta = false;
				
				GerenciarBancoClientes gerenciar_procurar = new GerenciarBancoClientes();
				   ArrayList<ContaBancaria> contas = gerenciar_procurar.getContas(vendedor_contrato.getId());
				   for(ContaBancaria conta : contas) {
					   if(conta.getAgencia().equals(agencia)) {
						   if(conta.getConta().equals(conta_corrente)) {
							   //conta do contrato encontrada
							   tem_conta = true;
							   id_conta_pagamento = conta.getId_conta();
						   }
					   }
				   }
				   
				if(tem_conta) {
				JOptionPane.showMessageDialog(null, 
						"Codigo do contrato: " + codigo_contrato + 
						"\nComprador: " + comprador_contrato.getNome_fantaia()+
						"\nVendedor: " + nome_vendedor+
						"\nIE do Vendedor: " + vendedor_contrato.getIe() +
						"\nProduto: " + produto + 
						"\nSafra: " + safra +
						"\nQuantidade(KG): " + quantidade_double + 
    					"\nnQuantidade(Sacos): " + quantidade_double_sacos +
    					"\nValor por saco: " + valor + 
    					"\nValor total: " + valor_total +
    					
    					"\nFazenda Contrato: " + fazenda_lavoura+
    					"\nInscricao Contrato: " + inscricao_contrato +
    					
    					"\nLocal Retirada: " + local_retirada +
    					"\nData entrega: " + data_entrega +
    			        "\nData do contrato: " + data_completa + "\nLocal: " + local_assinatura+
    			        "\nBanco: " + banco + 
    			        "\nAgencia: " + agencia + 
    			        "\nCC: " + conta_corrente + 
    			        "\nData Pagamento: " + data_pagamento);
				
				cadastrar_contrato = true;
				
				//abre a tela de cadastro de contrato
				}else {
					//cadastra a conta bancaria
					ContaBancaria conta_cadastrar = new ContaBancaria();
					conta_cadastrar.setAgencia(agencia);
					conta_cadastrar.setBanco(banco);
					conta_cadastrar.setConta(conta_corrente);
					if(vendedor_contrato.getTipo_pessoa() == 0) {
						//pessoa fisica
						conta_cadastrar.setNome(vendedor_contrato.getNome_empresarial());
						conta_cadastrar.setCpf_titular(vendedor_contrato.getCpf());

					}else {
						//pessoa juridica
						conta_cadastrar.setNome(vendedor_contrato.getNome_fantaia());
						conta_cadastrar.setCpf_titular(vendedor_contrato.getCnpj());
						
					}
					
					//cadastrar a conta
					GerenciarBancoClientes gerenciar_cadastrar = new GerenciarBancoClientes();
					ArrayList<ContaBancaria> contas_cadastrar = new ArrayList<>();
					contas_cadastrar.add(conta_cadastrar);
                    gerenciar_cadastrar.adicionarContaBancaria(contas_cadastrar, vendedor_contrato.getId());
                    
                    //verifica se a nota foi conta foi cadastrada
                    boolean tem_conta_replier = false;
					
					GerenciarBancoClientes gerenciar_procurar_novamente = new GerenciarBancoClientes();
					   ArrayList<ContaBancaria> contas_2_pesquisa = gerenciar_procurar_novamente.getContas(vendedor_contrato.getId());
					   for(ContaBancaria conta : contas_2_pesquisa) {
						   if(conta.getAgencia().equals(agencia)) {
							   if(conta.getConta().equals(conta_corrente)) {
								   //conta do contrato encontrada
								   tem_conta_replier = true;
								   id_conta_pagamento = conta.getId_conta();
							   }
						   }
					   }
					   
					   if(tem_conta_replier) {
						   //a conta foi cadastradaasdas
						   cadastrar_contrato = true;
	    					

						   
					   }else {
						   //a conta nao foi cadastrada
							JOptionPane.showMessageDialog(null, "O vendedor esta cadastrado, mas não possui a conta bancaria do pagamento deste contrato\n"
		    							+ " Tente adicionar manualmente!");
						   cadastrar_contrato = false;
					   }
					
					
				}
			}else {
				
				//informa ao usuario que o vendedor possui o cadastro, mas a inscricao estadual nao e a mesma, 
				//e pergunta se ele deseja cadastrar
				JOptionPane.showMessageDialog(null, 
						"Codigo do contrato: " + codigo_contrato + 
						"\nComprador: " + comprador_contrato.getNome_fantaia()+
						"\nVendedor: " + nome_vendedor+
						"\nIE do Vendedor: " + vendedor_contrato.getIe() +
						"\nProduto: " + produto + 
						"\nSafra: " + safra +
						"\nQuantidade(KG): " + quantidade_double + 
    					"\nnQuantidade(Sacos): " + quantidade_double_sacos +
    					"\nValor por saco: " + valor + 
    					"\nValor total: " + valor_total +
    					
    					"\nFazenda Contrato: " + fazenda_lavoura+
    					"\nInscricao Contrato: " + inscricao_contrato +
    				
    					"\nLocal Retirada: " + local_retirada +
    					"\nData entrega: " + data_entrega +
    			        "\nData do contrato: " + data_completa + "\nLocal: " + local_assinatura+
    			        "\nBanco: " + banco + 
    			        "\nAgencia: " + agencia + 
    			        "\nCC: " + conta_corrente + 
    			        "\nData Pagamento: " + data_pagamento);
				
				
				if (JOptionPane.showConfirmDialog(null, 
			            "Apesar do Vendedor do Contrato já possur um cadastro"
			            + " sua I.E não é a mesma do contrato."
			            + "\nDeseja cadastrar o vendedor do contrato com  a nova Inscrição Estadual?", "Cadastrar Vendedor Com Nova IE", 
			            JOptionPane.YES_NO_OPTION,
			            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					    
					TelaCadastroCliente tela = new TelaCadastroCliente(1, null,null);
					vendedor_contrato.setIe(inscricao_contrato);
					tela.setInformacoesNovoCliente(vendedor_contrato, uf_inscricao_contrato, conta_pagamento);

					tela.setVisible(true);

					
					//verifica se o vendedor foi cadastrado:
					
					   GerenciarBancoClientes gerenciar_vendedores = new GerenciarBancoClientes();
		    		     ArrayList<CadastroCliente> vendedores = gerenciar.getClientes(0, 0, "");
		    		     
		    		     boolean vendedor_foi_cadastrado  = false;
		    		     boolean conta_bancaria_cadastrada = false;
		    			for(CadastroCliente vend : vendedores) {
		    			   if(vendedor_contrato.getTipo_pessoa() == 0) {
		    				   //o vendedor do contrato e uma pessoa fisica
		    				   if(vend.getTipo_pessoa() == 0) {
		    					   if(vend.getCpf().equals(vendedor_contrato.getCpf())) {

		    						   if(vend.getIe().equals(inscricao_contrato)) {

		    						   //vendedor foi encontrado
		    						   vendedor_contrato = vend;
		    						   
		    						   //verifica se o vendedor possui a conta bancaria do cadastro
		    						   ArrayList<ContaBancaria> contas = gerenciar_vendedores.getContas(vend.getId());
		    						   for(ContaBancaria conta : contas) {
		    							   if(conta.getAgencia().equals(agencia)) {
		    								   if(conta.getConta().equals(conta_corrente)) {
		    									   //conta do contrato encontrada
		    									   conta_bancaria_cadastrada = true;
		    									   id_conta_pagamento = conta.getId_conta();

		    								   }
		    							   }
		    						   }
		    						   
		    						   vendedor_foi_cadastrado = true;
		    						   break;
		    						   }
		    					   }
		    				   }
		    				   
		    			   }else {
		    				   //o vendedor do contrato e uma pessoa juridica
                                   if(vend.getTipo_pessoa() == 1) {
                                	   if(vend.getCnpj().equals(vendedor_contrato.getCnpj())) {
    		    						   if(vend.getIe().equals(inscricao_contrato)) {
                                		   //vendedor foi encontrado
    		    						   vendedor_contrato = vend;
    		    						   
    		    						   //verifica se o vendedor possui a conta bancaria do cadastro
    		    						   ArrayList<ContaBancaria> contas = gerenciar_vendedores.getContas(vend.getId());
    		    						   for(ContaBancaria conta : contas) {
    		    							   if(conta.getAgencia().equals(agencia)) {
    		    								   if(conta.getConta().equals(conta_corrente)) {
    		    									   //conta do contrato encontrada
    		    									   conta_bancaria_cadastrada = true;
    		    									   id_conta_pagamento = conta.getId_conta();

    		    								   }
    		    							   }
    		    						   }
    		    						   
    		    						   vendedor_foi_cadastrado = true;
    		    						   break;
                                	   }
    		    					   }
		    				   }
		    			   }
		    			
		    			}
		    			
		    			if(vendedor_foi_cadastrado) {
		    				if(conta_bancaria_cadastrada) {
		    					JOptionPane.showMessageDialog(null, "O vendedor que nao estava cadastrado para a IE do contrato, agora esta cadastrado!");
    		    				
		    					
		    					cadastrar_contrato = true;
		    				}else {
		    					JOptionPane.showMessageDialog(null, "O vendedor que nao estava cadastrado para a IE do contrato, agora esta cadastrado, porem a conta bancaria nao foi encontrada!");
    		    				cadastrar_contrato = true;
		    				}
		    			
		    			}else {
		    				JOptionPane.showMessageDialog(null, "O vendedor que nao estava cadastrado, não foi cadastrado ainda, tente novamente!");
		    				cadastrar_contrato = false;
		    			}
			}
			}

		}else {
			JOptionPane.showMessageDialog(null, 
					"Codigo do contrato: " + codigo_contrato + 
					"\nComprador: " + comprador_contrato.getNome_fantaia()+
					"\nProduto: " + produto + 
					"\nSafra: " + safra +
					"\nQuantidade(KG): " + quantidade_double + 
					"\nnQuantidade(Sacos): " + quantidade_double_sacos +
					"\nValor por saco: " + valor + 
					"\nValor total: " + valor_total +
					
					"\nFazenda Contrato: " + fazenda_lavoura+
					"\nInscricao Contrato: " + inscricao_contrato +
				
					"\nLocal Retirada: " + local_retirada +
					"\nData entrega: " + data_entrega +
			        "\nData do contrato: " + data_completa + "\nLocal: " + local_assinatura+
			        "\nBanco: " + banco + 
			        "\nAgencia: " + agencia + 
			        "\nCC: " + conta_corrente + 
			        "\nData Pagamento: " + data_pagamento+
			        "\nVendedor do contrato não esta cadastrado: "	+
			        "\nTipo do Vendedor: " + tipo_vendedor +
			        "\nCPF/CNPJ: " + identificacao_vendedor);
			
			
			if (JOptionPane.showConfirmDialog(null, 
		            "Deseja cadastrar o vendedor do contrato?", "Cadastrar Vendedor", 
		            JOptionPane.YES_NO_OPTION,
		            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
				    
				TelaCadastroCliente tela = new TelaCadastroCliente(1, null,null);
				vendedor_contrato.setIe(inscricao_contrato);

				tela.setInformacoesNovoCliente(vendedor_contrato, uf_inscricao_contrato, conta_pagamento);

				tela.setVisible(true);

				
				//verifica se o vendedor foi cadastrado:
				
				   GerenciarBancoClientes gerenciar_vendedores = new GerenciarBancoClientes();
	    		     ArrayList<CadastroCliente> vendedores = gerenciar.getClientes(0, 0, "");
	    		     
	    		     boolean vendedor_foi_cadastrado  = false;
	    		     boolean conta_bancaria_cadastrada = false;
	    		     
	    			for(CadastroCliente vend : vendedores) {
	    			   if(vendedor_contrato.getTipo_pessoa() == 0) {
	    				   //o vendedor do contrato e uma pessoa fisica
	    				   if(vend.getTipo_pessoa() == 0) {
	    					   if(vend.getCpf().equals(vendedor_contrato.getCpf())) {
	    						   //verifica a inscricao estadual
	    						   if(vend.getIe().equals(inscricao_contrato)) {
	    							   //vendedor foi encontrado
		    						   vendedor_contrato = vend;
		    						   
		    						   //verifica se o vendedor possui a conta bancaria do cadastro
		    						   ArrayList<ContaBancaria> contas = gerenciar_vendedores.getContas(vend.getId());
		    						   for(ContaBancaria conta : contas) {
		    							   if(conta.getAgencia().equals(agencia)) {
		    								   if(conta.getConta().equals(conta_corrente)) {
		    									   //conta do contrato encontrada
		    									   conta_bancaria_cadastrada = true;
		    									   id_conta_pagamento = conta.getId_conta();

		    								   }
		    							   }
		    						   }
		    						   vendedor_foi_cadastrado = true;
		    						   break;
	    						   }
	    						  
	    					   }
	    				   }
	    				   
	    			   }else {
	    				   //o vendedor do contrato e uma pessoa juridica
                               if(vend.getTipo_pessoa() == 1) {
                            	   if(vend.getCnpj().equals(vendedor_contrato.getCnpj())) {
		    						   //vendedor foi encontrado
                            		   if(vend.getIe().equals(inscricao_contrato)) {
		    						   vendedor_contrato = vend;
		    						   //verifica se o vendedor possui a conta bancaria do cadastro
		    						   ArrayList<ContaBancaria> contas = gerenciar_vendedores.getContas(vend.getId());
		    						   for(ContaBancaria conta : contas) {
		    							   if(conta.getAgencia().equals(agencia)) {
		    								   if(conta.getConta().equals(conta_corrente)) {
		    									   //conta do contrato encontrada
		    									   conta_bancaria_cadastrada = true;
		    									   id_conta_pagamento = conta.getId_conta();

		    								   }
		    							   }
		    						   }
		    						   vendedor_foi_cadastrado = true;
		    						   break;
                            		   }
		    					   }
	    				   }
	    			   }
	    			
	    			}
	    			
	    			if(vendedor_foi_cadastrado) {
	    				if(conta_bancaria_cadastrada) {
	    					JOptionPane.showMessageDialog(null, "O vendedor que nao estava cadastrado, agora esta cadastrado!");
		    				cadastrar_contrato = true;
	    				}else {
	    					JOptionPane.showMessageDialog(null, "O vendedor que nao estava cadastrado, foi cadastrado, mas a conta bancaria para o contrato nao foi encontrada!");
		    				cadastrar_contrato = false;
	    				}
	    				
	    			}else {
	    				JOptionPane.showMessageDialog(null, "O vendedor que nao estava cadastrado, não foi cadastrado ainda, tente novamente!");
	    				cadastrar_contrato = false;
	    			}
	    			
	    			
			}  
			else
			{
				
				cadastrar_contrato = false;
			}
			
		
			
		}
    
		

		if(cadastrar_contrato) {
			boolean ja_existe = false;
			
			//verifica pelo codigo do contrato se ele ja nao esta cadastrado
			GerenciarBancoContratos gerenciar_cts = new GerenciarBancoContratos();
			ArrayList<CadastroContrato> contratos = gerenciar_cts.getContratos();
			
			for(CadastroContrato ctr : contratos) {
				if(ctr.getCodigo().equals(codigo_contrato)) {
					ja_existe  = true;
					break;
				}
			}
			
			if(!ja_existe) {
				//contrato nao existe, cadastrar
				JOptionPane.showMessageDialog(null, "Hapto para realizar o cadastro do contrato!");
				
				//seta os compradores do contrato
    			CadastroCliente compradores [] = new CadastroCliente[3];
    			compradores[0] = comprador_contrato;
    			
    			contrato_local.setCompradores(compradores);
    			
    			//seta os vendedores do contrato
    			
    		
    				CadastroCliente vendedores [] = new CadastroCliente[3];
    				vendedores[0] = vendedor_contrato;
        			
    			
        			contrato_local.setVendedores(vendedores);
        			
        			
        			
    			
				//adicionar pagamento ao contrato
        		ArrayList<CadastroContrato.CadastroPagamento> pagamentos = new ArrayList<>();
        		
        		CadastroContrato.CadastroPagamento modeloPag = new CadastroContrato.CadastroPagamento();
				modeloPag.setConta(gerenciar.getConta(id_conta_pagamento));
				modeloPag.setData_pagamento(data_pagamento);
				modeloPag.setValor(new BigDecimal(Double.toString(valor_total)));
				
				pagamentos.add(modeloPag);
				contrato_local.setPagamentos(pagamentos);
				
				//adicionar a tarefa
				
				ArrayList<CadastroContrato.CadastroTarefa> tarefas = new ArrayList<>();
				CadastroContrato.CadastroTarefa tarefa = new CadastroContrato.CadastroTarefa();

			
					tarefa.setNome_tarefa("Contrato Importado");
			
				// cria a tarefa de insercao de contrato
				tarefa.setId_tarefa(0);
				tarefa.setDescricao_tarefa("Criação de tarefa");

				tarefa.setStatus_tarefa(1);
				tarefa.setMensagem("tarefa criada");

				GetData data = new GetData();
				tarefa.setHora(data.getHora());
				tarefa.setData(data.getData());
				tarefa.setHora_agendada(data.getHora());
				tarefa.setData_agendada(data.getData());

				tarefa.setCriador(login);
				tarefa.setExecutor(login);

				tarefa.setPrioridade(1);
				
				tarefas.add(tarefa);
				
				contrato_local.setLista_tarefas(tarefas);
                contrato_local.setStatus_contrato(1);
				
			
				
					//copiar o arquivo para a pasta do comprador
					
					String nome_comprador_arquivo ;
					String nome_vendedor_arquivo;
				
					
					if(compradores[0].getTipo_pessoa() == 0)
						nome_comprador_arquivo = compradores[0].getNome_empresarial();
					else
						nome_comprador_arquivo = compradores[0].getNome_fantaia();
					
					if(vendedores[0].getTipo_pessoa() == 0)
						nome_vendedor_arquivo = vendedores[0].getNome_empresarial();
					else
						nome_vendedor_arquivo = vendedores[0].getNome_fantaia();

					String servidor_unidade = configs_globais.getServidorUnidade();

					   //é um comprato pai, salvar na pasta do comprador

						 String caminho_salvar_contrato__no_hd = servidor_unidade + "E-contract\\arquivos\\clientes\\" + nome_comprador_arquivo + "\\contratos" + "\\compra\\"  + ano_contrato + "\\" + contrato_local.getModelo_safra().getProduto().getNome_produto() + "\\";
						System.out.println("caminho para salvar o contrato do comprador no hd: " + caminho_salvar_contrato__no_hd);	
						String caminho_salvar_contrato_no_banco_dados = "E-contract\\\\arquivos\\\\clientes\\\\" + nome_comprador_arquivo + "\\\\contratos" + "\\\\compra\\\\"  + ano_contrato + "\\\\" + contrato_local.getModelo_safra().getProduto().getNome_produto() + "\\\\";

					      String nome_pasta_arquivo = contrato_local.getCodigo();

					      String nome_arquivo = contrato_local.getCodigo() + " "  + nome_comprador_arquivo + " x " + nome_vendedor_arquivo ; 
					     
							
							String caminho_completo_salvar_contrato_no_hd = caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\" + nome_arquivo ;
							
						    String caminho_completo_salvar_contrato_no_bando_dados = caminho_salvar_contrato_no_banco_dados + nome_pasta_arquivo +"\\\\" + nome_arquivo;
						    String nome_diretorio_arquivo_contrato = caminho_salvar_contrato_no_banco_dados + nome_pasta_arquivo;

							//criar o diretorio
							ManipularTxt manipular = new ManipularTxt();
							if(manipular.criarDiretorio(caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\"))
							{
								System.out.println("diretorio criado para o novo contrato");
								//criar pastas documentos e comprovantes
           						if(manipular.criarDiretorio(caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\comprovantes"))
    							{
    					    		  //criar diretorio documentos
    								  if(manipular.criarDiretorio(caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\documentos"))
    									{

    									}else {

    									}	

    							}else {

    							}
           						
								
								 //copiar arquivo para o novo diretorio
								boolean copiar = manipular.copiarNFe(file.getAbsolutePath(), caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\" + nome_arquivo + ".pdf" );
                                   if(copiar) {
               						JOptionPane.showMessageDialog(null, "Arquivo copiado para a nova pasta!");
               						//inserir o contrato no banco de dados
               						contrato_local.setCaminho_diretorio_contrato(nome_diretorio_arquivo_contrato);
               						contrato_local.setCaminho_arquivo(caminho_salvar_contrato_no_banco_dados + nome_pasta_arquivo + "\\\\" + nome_arquivo + ".pdf");
               						contrato_local.setNome_arquivo(nome_arquivo + ".pdf");
               						
                					GerenciarBancoContratos gerenciarContratos = new GerenciarBancoContratos();
                					
                					int insercao = gerenciarContratos.inserirContrato(contrato_local, null);
                					if(insercao == 1) {
                  						JOptionPane.showMessageDialog(null, "O Contrato foi salvo no banco de dados");
                  						
                  						//inserir o contrato na nuvem
                  						 Nuvem nuvem = new Nuvem();
                  		            	 nuvem.abrir();
                  		                 nuvem.testar();
                  		                
                  		                boolean retorno = nuvem.carregar(caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\" + nome_arquivo + ".pdf", nome_arquivo + ".pdf");
                  		                 if(retorno) {
                       						JOptionPane.showMessageDialog(null, "O Contrato  foi salvo na nuvem");
                  		                 }else {
                       						JOptionPane.showMessageDialog(null, "O Contrato não foi salvo na nuvem");

                  		                 }

                					}else {
                  						JOptionPane.showMessageDialog(null, "O Contrato não foi salvo no banco de dados");

                					}

                                   }else {
                  						JOptionPane.showMessageDialog(null, "O arquivo não foi copiado para  nova pasta, tente novamente");
                  						JOptionPane.showMessageDialog(null, "Origem: " + file.getAbsolutePath() + "\ndestino: " +  caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\" + nome_arquivo + ".pdf");

                                   }
								
							}else {
								System.out.println("erro ao criar diretorio para o contrato ");
          						JOptionPane.showMessageDialog(null, "O arquivo não foi copiado para  nova pasta, tente novamente");
          						JOptionPane.showMessageDialog(null, "Origem: " + file.getAbsolutePath() + "\ndestino: " +  caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\" + nome_arquivo + ".pdf");

								
							}
					
				
				
				
				
				
				
				
				
			}else {
				JOptionPane.showMessageDialog(null, "O Contrato de codigo " + contrato_local.getCodigo() + " já esta cadastrado!");

			}
			
			
			
		}else {
			JOptionPane.showMessageDialog(null, "Não hapto para realizar o cadastro do contrato!");

		}
		
		
		return contrato_local;
		}catch(Exception e) {
			
			JOptionPane.showMessageDialog(null, "Erro ao importar contrato da Gavilon\nTente a Importação Manual");
			return null;
		}
		
		
	}
	
	
	public CadastroContrato contratoFortunato(CadastroContrato contrato_local) {

		try {
		String codigo_contrato = "";
		int linha_codigo = - 1;
		for(int i = 0; i < lines.length; i++) {
			if(lines[i].contains("CONTRATO PARTICULAR")) {
				linha_codigo = i + 1;

               break;
			}
		}
		
		codigo_contrato = lines[linha_codigo].replaceAll("[^0-9]", "");
		//JOptionPane.showMessageDialog(null, "Codigo do contrato: " + codigo_contrato);
		contrato_local.setCodigo(codigo_contrato);
		contrato_local.setSub_contrato(3);
		
         

		CadastroCliente comprador_contrato = new CadastroCliente();
		int linha_cnpj = - 1;
		int linha_ie_comprador = -1;
		String cnpj_comprador = "";
		String ie_comprador = "";
		for(int i = 0; i < lines.length; i++) {
			if(lines[i].contains("A) COMPRADOR: ")) {
				linha_cnpj = i + 3;
				linha_ie_comprador = i + 4;  

               break;
			}
		}

		cnpj_comprador = lines[linha_cnpj].replaceAll("[^0-9]", "");
		ie_comprador = lines[linha_ie_comprador].replaceAll("[^0-9]", "");
		//JOptionPane.showMessageDialog(null, "CNPJ do comprador: " + cnpj_comprador + "\nIE do comprador: " + ie_comprador);
		//procura nos clientes se existe este comprador
	     GerenciarBancoClientes gerenciar = new GerenciarBancoClientes();
	     ArrayList<CadastroCliente> clientes = gerenciar.getClientes(0, 0, "");
	     
		for(CadastroCliente cliente : clientes) {
			if(cliente.getTipo_pessoa() == 1) {

			if(cliente.getCnpj().equals(cnpj_comprador)) {
				
				comprador_contrato = cliente;
				break;
			}
			}
		}
		
		
		
		CadastroCliente vendedor_contrato = new CadastroCliente();
		int linha_identificacao_vendedor = - 1;
		int linha_ie_vendedor = -1;
		int linha_nome_vendedor = -1;
		String identificacao_vendedor = "";
		String ie_vendedor = "";
		for(int i = 0; i < lines.length; i++) {
			if(lines[i].contains("B) VENDEDOR: ")) {
				linha_nome_vendedor = i;
				//linha_identificacao_vendedor = i + 3;  
				//linha_ie_vendedor = i + 4;
               break;
			}
		}
		
		int forma_producao = 3;
		for(int i = 0; i < lines.length; i++) {
			if(lines[i].contains("ENDEREÇO PRODUÇÃO:"))   {
				linha_identificacao_vendedor = i + 2;  
				linha_ie_vendedor = i + 3;
				forma_producao = 1;
               break;
			}else if(lines[i].contains("ENDEREÇO DE PRODUÇÃO:")) {
				linha_identificacao_vendedor = i + 2;  
				linha_ie_vendedor = i + 3;
				forma_producao = 2;
               break;
			}else {
				linha_identificacao_vendedor = linha_nome_vendedor + 3; 
				linha_ie_vendedor = linha_nome_vendedor + 4;
				forma_producao = 3;
			}
		}
		//JOptionPane.showMessageDialog(null, "Forma de produção: " + forma_producao);
		
		identificacao_vendedor = lines[linha_identificacao_vendedor];
		TratarDados tratar_iden_vendedor = new TratarDados(identificacao_vendedor);
		identificacao_vendedor = tratar_iden_vendedor.tratar("CPF: ", " ");
		identificacao_vendedor = identificacao_vendedor.replaceAll("[^0-9]", "");
		ie_vendedor = lines[linha_ie_vendedor].replaceAll("[^0-9]", "");
		
		//JOptionPane.showMessageDialog(null, "Cpf do vendedor: " + identificacao_vendedor + "\n IE do vendedor: "+ie_vendedor);
		
		//seta os vendedores do contrato
				String nome_vendedor = "";
				
					nome_vendedor = lines[linha_nome_vendedor];
					nome_vendedor = nome_vendedor.replaceAll("B\\) VENDEDOR: ", "");
				
					//JOptionPane.showMessageDialog(null, "Vendedor do contrato: " + nome_vendedor);
				String nome_quebrado[] = nome_vendedor.split(" ");
				
				    String sobrenome = "";
					vendedor_contrato.setNome(nome_quebrado[0]);
					for(int i = 1 ; i < nome_quebrado.length; i++) {
						sobrenome = sobrenome + " " + nome_quebrado[i];
					}
					vendedor_contrato.setSobrenome(sobrenome);
				
			
		int tipo_vendedor = -1;

        if(identificacao_vendedor.length() == 11) {
        	tipo_vendedor = 0;
        	vendedor_contrato.setTipo_pessoa(0);
        	vendedor_contrato.setCpf(identificacao_vendedor);
        	vendedor_contrato.setIe(ie_vendedor);
        	vendedor_contrato.setNome_empresarial(nome_vendedor);
        }
        else if(identificacao_vendedor.length() == 14) {
        	tipo_vendedor = 1;
        	vendedor_contrato.setTipo_pessoa(1);
        	vendedor_contrato.setCnpj(identificacao_vendedor);
        	vendedor_contrato.setIe(ie_vendedor);
        	vendedor_contrato.setNome_fantaia(nome_vendedor);

        }
		
        boolean vendedor_cadastrado = false;
		for(CadastroCliente cliente : clientes) {
			
			if(tipo_vendedor == 0) {
				if(cliente.getTipo_pessoa() == 0) {
					if(cliente.getCpf().equals(identificacao_vendedor)) {
						vendedor_contrato = cliente;
						vendedor_cadastrado  = true;
					break;
					}
				}
				
			}else {
				if(cliente.getTipo_pessoa() == 1) {
					if(cliente.getCnpj().equals(identificacao_vendedor)) {
						vendedor_contrato = cliente;
						vendedor_cadastrado  = true;

					break;
					}
				}
			}
			
		}
		
		if(vendedor_cadastrado) {
			
			if(vendedor_contrato.getTipo_pessoa() == 0) {
				//pessoa fisica
				nome_vendedor = vendedor_contrato.getNome_empresarial();
			}else {
				nome_vendedor = vendedor_contrato.getNome_fantaia();

			}
			
			
			
		}
		
		
		
		//safra
		String safra = "";
		for(int i = 0; i < lines.length; i++) {
			if(lines[i].contains("2020")) {
               safra = "2019/2020";  
               break;
			}else if(lines[i].contains("2021")) {
                safra = "2020/2021";  
                break;
			}else if(lines[i].contains("2022")) {
                safra = "2021/2022"; 
                break;

			}else if(lines[i].contains("2023")) {
                safra = "2022/2023"; 
                break;

			}else if(lines[i].contains("2024")) {
                safra = "2023/2024";   
                break;

			}else if(lines[i].contains("2023/2023")) {
                safra = "2023/2023";      				 	

                break;
			}else if(lines[i].contains("2023/2024")) {
                safra = "2023/2024";   
                break;

			}
		}

		//JOptionPane.showMessageDialog(null, "safra: " + safra);
	
		//encontrar a linha que contem o endereco da fazenda
		 String linha_endereco ="";
		
		 if(forma_producao == 1 || forma_producao == 2) {
			  linha_endereco  = lines[linha_identificacao_vendedor - 2] + lines[linha_identificacao_vendedor - 1];

		 }else {
			 linha_endereco = lines[linha_nome_vendedor + 1] + lines[linha_nome_vendedor + 2];
		 }
		 //JOptionPane.showMessageDialog(null, "endereco da fazenda: " + linha_endereco);
		  System.out.println("Linha endereço : " + linha_endereco);
		  linha_endereco = linha_endereco + ";";
         TratarDados tratar_endereco = new TratarDados(linha_endereco);
 		TratarDados tratar_endereco_quebrado = new TratarDados(linha_endereco);

         String fazenda_lavoura = "";
         String uf_inscricao_contrato = "";
		if(forma_producao == 1) {
			 fazenda_lavoura = tratar_endereco.tratar("ENDEREÇO PRODUÇÃO: ", "-").trim();

		}else if(forma_producao == 2) {
			 fazenda_lavoura = tratar_endereco.tratar("ENDEREÇO DE PRODUÇÃO: ", "-").trim();

		}else {
		
			fazenda_lavoura = tratar_endereco_quebrado.tratar("ENDEREÇO: ", "-").trim();
		}
		
		//JOptionPane.showMessageDialog(null, "Fazenda lavoura: " + fazenda_lavoura);

		if(linha_endereco.contains("MINAS GERAIS ")) {
			uf_inscricao_contrato = "MG";
		}else if(linha_endereco.contains("SÃO PAULO")) {
			uf_inscricao_contrato = "SP";
		}
		
	
		String rua = "";
		
		if(forma_producao == 1) {
			 rua = tratar_endereco_quebrado.tratar("ENDEREÇO PRODUÇÃO: ", "-").trim();

		}else if (forma_producao == 2){
			 rua = tratar_endereco_quebrado.tratar("ENDEREÇO DE PRODUÇÃO: ", "-").trim();

		}else {
			 rua = tratar_endereco_quebrado.tratar("ENDEREÇO: ", "-").trim();

		}
		
		String bairro = tratar_endereco_quebrado.tratar("BAIRRO ", "MUNICÍPIO").replaceAll("-", "");
		String cidade = "";
		
		String cidade_busca = tratar_endereco_quebrado.tratar("MUNICÍPIO/ES", ";");
		//JOptionPane.showMessageDialog(null, "Cidade da busca: " + cidade_busca );
		cidade_busca = cidade_busca.replaceAll("/", ",");
		//JOptionPane.showMessageDialog(null, "Cidade da busca sem /:" + cidade_busca );
		System.out.println("Cidade da busca sem /: " + cidade_busca);
        TratarDados tratar_cidade_busca = new TratarDados(cidade_busca);
		 cidade = tratar_cidade_busca.tratar("TADO: ", ",").trim();
		
		
		
		
		String estado = uf_inscricao_contrato;
		
		vendedor_contrato.setRua(rua);
		vendedor_contrato.setBairro(bairro);
		vendedor_contrato.setCidade(cidade);
		vendedor_contrato.setUf(estado);
		
		
		
		//JOptionPane.showMessageDialog(null, "Informacoes de endereço: \nRua: " + rua +
		//		"\nBairro: " + bairro + "\nCidade: " + cidade + "\nEstado: " + estado ); 
		
		//JOptionPane.showMessageDialog(null, "uf da inscricao do contrato: " + uf_inscricao_contrato);
		 String inscricao_contrato = ie_vendedor;
		 
		
		
		//dados do produto
		 String produto = "";
		String transgenia_proxima =  "";
		//percorre as linhas do array procurando a palavra soja
		for(int i = 0; i < lines.length; i++) {
			if(lines[i].contains("Soja GMO")) {
				produto = "SOJA GMO";
               transgenia_proxima =  "Transgenico(GMO)";
               break;
			}else if((lines[i].contains("soja"))){
				produto = "SOJA GMO";

				transgenia_proxima =  "Transgenico(GMO)";
	               break;
			}else if((lines[i].contains("SOJA"))){
				produto = "SOJA GMO";

				transgenia_proxima =  "Transgenico(GMO)";
	               break;
			}
			
		}
		
		
		//procura pelo produto no  banco de dados
		GerenciarBancoProdutos gerenciar_produtos = new GerenciarBancoProdutos();
		ArrayList<CadastroProduto> produtos = gerenciar_produtos.getProdutos();
		for(CadastroProduto prod : produtos) {
			if(prod.getNome_produto().equalsIgnoreCase(produto)) {
				if(prod.getTransgenia().equalsIgnoreCase(transgenia_proxima)) {
					contrato_local.setModelo_produto(prod);
					break;
				}
				
				
			}
			
		}
		
		//procura pela safra com este produto no banco de dados
		
		  GerenciarBancoSafras gerenciar_safra = new GerenciarBancoSafras();
		 
		ArrayList<CadastroSafra> safras = gerenciar_safra.getSafras();
		for(CadastroSafra saf : safras) {
			if(saf.getProduto().getId_produto() == contrato_local.getModelo_produto().getId_produto()) {
				//encontrado a safra que possui o produto deste contrato
				contrato_local.setModelo_safra(saf);
				break;
				
			}
				
		}
		
		//frete e armazenagem
		String armazenagem = tratamentoDados.tratar("armazenagem por conta e risco do ", " até"); 
		//JOptionPane.showMessageDialog(null, "Armazenagem por conta do: " + armazenagem);
		 contrato_local.setArmazenagem(armazenagem);
		
		 String frete = tratamentoDados.tratar("FRETE ATÉ LOCAL DE ENTREGA POR CONTA DO: ", "DESPESA").replaceAll(" ", ""); 
		 //JOptionPane.showMessageDialog(null, "frete por conta do: " + frete);
			 contrato_local.setFrete(frete);
			 
		//quantidade do produto
		String quantidade = tratamentoDados.tratar("QUANTIDADE EM KGS: ", " ");
		quantidade = quantidade.trim();
		quantidade = quantidade.replace(".", "");
		double quantidade_double = Double.parseDouble(quantidade);
		double quantidade_double_sacos = quantidade_double / 60;
		
		
		//seta a quantidade no contrato
		contrato_local.setQuantidade(quantidade_double_sacos);
		//seta a medida no contrato
		contrato_local.setMedida("Sacos");
		//JOptionPane.showMessageDialog(null, "Quatidade(kgs): " + quantidade_double);
		//JOptionPane.showMessageDialog(null, "Quatidade(sacos): " + quantidade_double_sacos);

		
		int linha_valor = - 1;
	
		for(int i = 0; i < lines.length; i++) {
			if(lines[i].contains("PREÇO POR SACA DE 60")) {
				linha_valor= i;
               break;
			}
		}
		
		//valor
		String valor = lines[linha_valor];
		String valor_quebrado[] = valor.split(" ");
		valor = valor_quebrado[8];
		valor = valor.replaceAll(",", ".");
		double valor_double = Double.parseDouble(valor);
		//JOptionPane.showMessageDialog(null, "Valor por saco: " + valor_double);

		//seta o valor por saco no contrato
		contrato_local.setValor_produto(valor_double);
		
		double valor_total = quantidade_double_sacos * valor_double;
		
		//seta o valor total no contrato
		contrato_local.setValor_a_pagar(new BigDecimal(Double.toString(valor_total)));
		
		//JOptionPane.showMessageDialog(null, "Valor total: " + valor_total);

		
		
		//prazo final da entrada

		int linha_data_entrega = -1;
		for(int i = 0; i < lines.length; i++) {
			if(lines[i].contains("PRAZO ENTREGA")) {
				linha_data_entrega = i;
				break;
			}
		}
		
		String linha_busca = lines[linha_data_entrega];
		//JOptionPane.showMessageDialog(null, "conteudo da linha da busca: " + linha_busca);
		String data_entrega =  linha_busca.substring(linha_busca.length()-12, linha_busca.length() - 2);
		contrato_local.setData_entrega(data_entrega);
		//JOptionPane.showMessageDialog(null, "Data da entrega: " + data_entrega);
		
		//local retirada
		
		int linha_local_retirada = -1;
		for(int i = 0; i < lines.length; i++) {
			if(lines[i].contains("Volume a retirar")) {
				linha_local_retirada = i + 1;
				break;
			}
		}
		String local_retirada = lines[linha_local_retirada].replaceAll("[^0-9]", " ").replaceAll(" ", "");
		//JOptionPane.showMessageDialog(null, "Identificacao do local da retirada: " + local_retirada);
		
		//data do contrato
		String mes_extenso = "";
		String mes_decimal = "";
		int linha_data = -1;
		for(int i = 0; i < lines.length; i++) {
			if(lines[i].contains("de Janeiro de")) {
				mes_extenso = "janeiro"; 
				mes_decimal = "01";
				linha_data = i;
				break;
			}else if(lines[i].contains("de Fevereiro de")) {
				mes_extenso = "fevereiro";   
				mes_decimal = "02";
				linha_data = i;

				break;

			}else if(lines[i].contains("de Março de")) {
				mes_extenso = "março";      				 	
				mes_decimal = "03";
				linha_data = i;

				break;

			}else if(lines[i].contains("de Abril de")) {
				mes_extenso = "abril";      				 	
				mes_decimal = "04";
				linha_data = i;

				break;

			}else if(lines[i].contains("de Maio de")) {
				mes_extenso = "maio";      				 	
				mes_decimal = "05";
				linha_data = i;

				break;

			}else if(lines[i].contains("de Junho de")) {
				mes_extenso = "junho";      				 	
				mes_decimal = "06";
				linha_data = i;

				break;

			}else if(lines[i].contains("de Julho de")) {
				mes_extenso = "julho";      				 	
				mes_decimal = "07";
				linha_data = i;

				break;

			}else if(lines[i].contains("de Agosto de")) {
				mes_extenso = "agosto";      				 	
				mes_decimal = "08";
				linha_data = i;
				break;


			}else if(lines[i].contains("de Setembro de")) {
				mes_extenso = "setembro";      				 	
				mes_decimal = "09";
				linha_data = i;

				break;

			}else if(lines[i].contains("de Outubro de")) {
				mes_extenso = "outubro";      				 	
				mes_decimal = "10";
				linha_data = i;

				break;

			}else if(lines[i].contains("de Novembro de")) {
				mes_extenso = "novembro";      				 	
				mes_decimal = "11";
				linha_data = i;

				break;

			}else if(lines[i].contains("de Dezembro de")) {
				mes_extenso = "dezembro"; 
				mes_decimal = "12";
				linha_data = i;

				break;


			}
		}
		
		
	
		
		
		 String local_data = lines[linha_data];
		 local_data = local_data.replace(",", "");
		 String conteudo_data[] = local_data.split(" ");
		 String local_assinatura = conteudo_data[0];
	
		 String dia_contrato = conteudo_data[conteudo_data.length - 5];
		 String ano_contrato = conteudo_data[conteudo_data.length - 1];
		 ano_contrato = ano_contrato.replace(".", "");
		 String data_completa = dia_contrato + "/" + mes_decimal + "/" + ano_contrato;
		 data_completa = data_completa.replace(".", "");
		 //seta a data do contrato
		 contrato_local.setData_contrato(data_completa);
		 
		 
		 
		 //linha com informacoes do pagamento do contrato
			int linha_pagamento = -1;
			for(int i = 0; i < lines.length; i++) {
				if(lines[i].contains("DATA/FORMA")) {
					linha_pagamento = i;
					break;
				}
			}//quebra a linha de informacoes de pagamento
			
			String pagamento = lines[linha_pagamento] + lines[linha_pagamento + 1] + lines[linha_pagamento + 2] + lines[linha_pagamento + 3];
			//JOptionPane.showMessageDialog(null, "linha com info pagamento: " + pagamento);
			System.out.println("linha com info pagamento: " + pagamento);
			TratarDados tratar_pagamento = new TratarDados(pagamento + ",");
			
		 //conta bancaria do pagagamento
		 String banco = tratar_pagamento.tratar("junto ao ", ",");
		 String agencia =  tratar_pagamento.tratar("agência", ",").replaceAll("[^0-9]", "");
		 String conta_corrente =   tratar_pagamento.tratar("conta número ", ",");
		
		 if(!conta_corrente.equals("") && !conta_corrente.equals(" ") && conta_corrente.length() > 3) {
			 
		 }else {
			  conta_corrente =   tratar_pagamento.tratar("conta corrente número ", ",");
 
		 }
		 
		 
		 
		 String data_pagamento =  tratar_pagamento.tratar("realizado em ", ",");
		
	
		ContaBancaria conta_pagamento = new ContaBancaria();
		 conta_pagamento.setBanco(banco);
		 conta_pagamento.setAgencia(agencia);
		 conta_pagamento.setConta(conta_corrente);
		 conta_pagamento.setCpf_titular(identificacao_vendedor);
		 conta_pagamento.setNome(vendedor_contrato.getNome() + " " +  vendedor_contrato.getSobrenome());
		
		 boolean cadastrar_contrato = false;
		if(vendedor_cadastrado) {
			
			//o vendedor esta cadastrador, agora verifica se a instricao estadual do contrato e a mesma do cadastro
			if(vendedor_contrato.getIe().equals(inscricao_contrato)) {
				
				// o vendedor tem a mesma inscricao estadual, verifica se tem a conta bancaria
				 //verifica se o vendedor possui a conta bancaria do cadastro
				boolean tem_conta = false;
				
				GerenciarBancoClientes gerenciar_procurar = new GerenciarBancoClientes();
				   ArrayList<ContaBancaria> contas = gerenciar_procurar.getContas(vendedor_contrato.getId());
				   for(ContaBancaria conta : contas) {
					   if(conta.getAgencia().equals(agencia)) {
						   if(conta.getConta().equals(conta_corrente)) {
							   //conta do contrato encontrada
							   tem_conta = true;
						   }
					   }
				   }
				   
				if(tem_conta) {
				JOptionPane.showMessageDialog(null, 
						"Codigo do contrato: " + codigo_contrato + 
						"\nComprador: " + comprador_contrato.getNome_fantaia()+
						"\nVendedor: " + nome_vendedor+
						"\nIE do Vendedor: " + vendedor_contrato.getIe() +
						"\nProduto: " + produto + 
						"\nSafra: " + safra +
						"\nQuantidade(KG): " + quantidade_double + 
    					"\nnQuantidade(Sacos): " + quantidade_double_sacos +
    					"\nValor por saco: " + valor + 
    					"\nValor total: " + valor_total +
    					
    					"\nFazenda Contrato: " + fazenda_lavoura+
    					"\nInscricao Contrato: " + inscricao_contrato +
    					
    					"\nLocal Retirada: " + local_retirada +
    					"\nData entrega: " + data_entrega +
    			        "\nData do contrato: " + data_completa + "\nLocal: " + local_assinatura+
    			        "\nBanco: " + banco + 
    			        "\nAgencia: " + agencia + 
    			        "\nCC: " + conta_corrente + 
    			        "\nData Pagamento: " + data_pagamento);
				
				cadastrar_contrato = true;
				
				//abre a tela de cadastro de contrato
				}else {
					//cadastra a conta bancaria
					ContaBancaria conta_cadastrar = new ContaBancaria();
					conta_cadastrar.setAgencia(agencia);
					conta_cadastrar.setBanco(banco);
					conta_cadastrar.setConta(conta_corrente);
					if(vendedor_contrato.getTipo_pessoa() == 0) {
						//pessoa fisica
						conta_cadastrar.setNome(vendedor_contrato.getNome_empresarial());
						conta_cadastrar.setCpf_titular(vendedor_contrato.getCpf());

					}else {
						//pessoa juridica
						conta_cadastrar.setNome(vendedor_contrato.getNome_fantaia());
						conta_cadastrar.setCpf_titular(vendedor_contrato.getCnpj());
						
					}
					
					//cadastrar a conta
					GerenciarBancoClientes gerenciar_cadastrar = new GerenciarBancoClientes();
					ArrayList<ContaBancaria> contas_cadastrar = new ArrayList<>();
					contas_cadastrar.add(conta_cadastrar);
                    gerenciar_cadastrar.adicionarContaBancaria(contas_cadastrar, vendedor_contrato.getId());
                    
                    //verifica se a nota foi conta foi cadastrada
                    boolean tem_conta_replier = false;
					
					GerenciarBancoClientes gerenciar_procurar_novamente = new GerenciarBancoClientes();
					   ArrayList<ContaBancaria> contas_2_pesquisa = gerenciar_procurar_novamente.getContas(vendedor_contrato.getId());
					   for(ContaBancaria conta : contas_2_pesquisa) {
						   if(conta.getAgencia().equals(agencia)) {
							   if(conta.getConta().equals(conta_corrente)) {
								   //conta do contrato encontrada
								   tem_conta_replier = true;
							   }
						   }
					   }
					   
					   if(tem_conta_replier) {
						   //a conta foi cadastradaasdas
						   cadastrar_contrato = true;
	    					

						   
					   }else {
						   //a conta nao foi cadastrada
							JOptionPane.showMessageDialog(null, "O vendedor esta cadastrado, mas não possui a conta bancaria do pagamento deste contrato\n"
		    							+ " Tente adicionar manualmente!");
						   cadastrar_contrato = false;
					   }
					
					
				}
			}else {
				
				//informa ao usuario que o vendedor possui o cadastro, mas a inscricao estadual nao e a mesma, 
				//e pergunta se ele deseja cadastrar
				JOptionPane.showMessageDialog(null, 
						"Codigo do contrato: " + codigo_contrato + 
						"\nComprador: " + comprador_contrato.getNome_fantaia()+
						"\nVendedor: " + nome_vendedor+
						"\nIE do Vendedor: " + vendedor_contrato.getIe() +
						"\nProduto: " + produto + 
						"\nSafra: " + safra +
						"\nQuantidade(KG): " + quantidade_double + 
    					"\nnQuantidade(Sacos): " + quantidade_double_sacos +
    					"\nValor por saco: " + valor + 
    					"\nValor total: " + valor_total +
    					
    					"\nFazenda Contrato: " + fazenda_lavoura+
    					"\nInscricao Contrato: " + inscricao_contrato +
    				
    					"\nLocal Retirada: " + local_retirada +
    					"\nData entrega: " + data_entrega +
    			        "\nData do contrato: " + data_completa + "\nLocal: " + local_assinatura+
    			        "\nBanco: " + banco + 
    			        "\nAgencia: " + agencia + 
    			        "\nCC: " + conta_corrente + 
    			        "\nData Pagamento: " + data_pagamento);
				
				
				if (JOptionPane.showConfirmDialog(null, 
			            "Apesar do Vendedor do Contrato já possur um cadastro"
			            + " sua I.E não é a mesma do contrato."
			            + "\nDeseja cadastrar o vendedor do contrato com  a nova Inscrição Estadual?", "Cadastrar Vendedor Com Nova IE", 
			            JOptionPane.YES_NO_OPTION,
			            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					    
					TelaCadastroCliente tela = new TelaCadastroCliente(1, null,null);
					vendedor_contrato.setIe(inscricao_contrato);
					tela.setInformacoesNovoCliente(vendedor_contrato, uf_inscricao_contrato, conta_pagamento);

					tela.setVisible(true);

					
					//verifica se o vendedor foi cadastrado:
					
					   GerenciarBancoClientes gerenciar_vendedores = new GerenciarBancoClientes();
		    		     ArrayList<CadastroCliente> vendedores = gerenciar.getClientes(0, 0, "");
		    		     
		    		     boolean vendedor_foi_cadastrado  = false;
		    		     boolean conta_bancaria_cadastrada = false;
		    			for(CadastroCliente vend : vendedores) {
		    			   if(vendedor_contrato.getTipo_pessoa() == 0) {
		    				   //o vendedor do contrato e uma pessoa fisica
		    				   if(vend.getTipo_pessoa() == 0) {
		    					   if(vend.getCpf().equals(vendedor_contrato.getCpf())) {

		    						   if(vend.getIe().equals(inscricao_contrato)) {

		    						   //vendedor foi encontrado
		    						   vendedor_contrato = vend;
		    						   
		    						   //verifica se o vendedor possui a conta bancaria do cadastro
		    						   ArrayList<ContaBancaria> contas = gerenciar_vendedores.getContas(vend.getId());
		    						   for(ContaBancaria conta : contas) {
		    							   if(conta.getAgencia().equals(agencia)) {
		    								   if(conta.getConta().equals(conta_corrente)) {
		    									   //conta do contrato encontrada
		    									   conta_bancaria_cadastrada = true;
		    								   }
		    							   }
		    						   }
		    						   
		    						   vendedor_foi_cadastrado = true;
		    						   break;
		    						   }
		    					   }
		    				   }
		    				   
		    			   }else {
		    				   //o vendedor do contrato e uma pessoa juridica
                                   if(vend.getTipo_pessoa() == 1) {
                                	   if(vend.getCnpj().equals(vendedor_contrato.getCnpj())) {
    		    						   if(vend.getIe().equals(inscricao_contrato)) {
                                		   //vendedor foi encontrado
    		    						   vendedor_contrato = vend;
    		    						   
    		    						   //verifica se o vendedor possui a conta bancaria do cadastro
    		    						   ArrayList<ContaBancaria> contas = gerenciar_vendedores.getContas(vend.getId());
    		    						   for(ContaBancaria conta : contas) {
    		    							   if(conta.getAgencia().equals(agencia)) {
    		    								   if(conta.getConta().equals(conta_corrente)) {
    		    									   //conta do contrato encontrada
    		    									   conta_bancaria_cadastrada = true;
    		    								   }
    		    							   }
    		    						   }
    		    						   
    		    						   vendedor_foi_cadastrado = true;
    		    						   break;
                                	   }
    		    					   }
		    				   }
		    			   }
		    			
		    			}
		    			
		    			if(vendedor_foi_cadastrado) {
		    				if(conta_bancaria_cadastrada) {
		    					JOptionPane.showMessageDialog(null, "O vendedor que nao estava cadastrado para a IE do contrato, agora esta cadastrado!");
    		    				
		    					
		    					cadastrar_contrato = true;
		    				}else {
		    					JOptionPane.showMessageDialog(null, "O vendedor que nao estava cadastrado para a IE do contrato, agora esta cadastrado, porem a conta bancaria nao foi encontrada!");
    		    				cadastrar_contrato = true;
		    				}
		    			
		    			}else {
		    				JOptionPane.showMessageDialog(null, "O vendedor que nao estava cadastrado, não foi cadastrado ainda, tente novamente!");
		    				cadastrar_contrato = false;
		    			}
			}
			}

		}else {
			JOptionPane.showMessageDialog(null, 
					"Codigo do contrato: " + codigo_contrato + 
					"\nComprador: " + comprador_contrato.getNome_fantaia()+
					"\nProduto: " + produto + 
					"\nSafra: " + safra +
					"\nQuantidade(KG): " + quantidade_double + 
					"\nnQuantidade(Sacos): " + quantidade_double_sacos +
					"\nValor por saco: " + valor + 
					"\nValor total: " + valor_total +
					
					"\nFazenda Contrato: " + fazenda_lavoura+
					"\nInscricao Contrato: " + inscricao_contrato +
				
					"\nLocal Retirada: " + local_retirada +
					"\nData entrega: " + data_entrega +
			        "\nData do contrato: " + data_completa + "\nLocal: " + local_assinatura+
			        "\nBanco: " + banco + 
			        "\nAgencia: " + agencia + 
			        "\nCC: " + conta_corrente + 
			        "\nData Pagamento: " + data_pagamento+
			        "\nVendedor do contrato não esta cadastrado: "	+ nome_vendedor +
			        "\nTipo do Vendedor: " + tipo_vendedor +
			        "\nCPF/CNPJ: " + identificacao_vendedor);
			
			
			if (JOptionPane.showConfirmDialog(null, 
		            "Deseja cadastrar o vendedor do contrato?", "Cadastrar Vendedor", 
		            JOptionPane.YES_NO_OPTION,
		            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
				    
				TelaCadastroCliente tela = new TelaCadastroCliente(1, null,null);
				//tela.setModal(true);
				vendedor_contrato.setIe(inscricao_contrato);

				tela.setInformacoesNovoCliente(vendedor_contrato, uf_inscricao_contrato, conta_pagamento);

				tela.setVisible(true);

				
				//verifica se o vendedor foi cadastrado:
				
				   GerenciarBancoClientes gerenciar_vendedores = new GerenciarBancoClientes();
	    		     ArrayList<CadastroCliente> vendedores = gerenciar.getClientes(0, 0, "");
	    		     
	    		     boolean vendedor_foi_cadastrado  = false;
	    		     boolean conta_bancaria_cadastrada = false;
	    		     
	    			for(CadastroCliente vend : vendedores) {
	    			   if(vendedor_contrato.getTipo_pessoa() == 0) {
	    				   //o vendedor do contrato e uma pessoa fisica
	    				   if(vend.getTipo_pessoa() == 0) {
	    					   if(vend.getCpf().equals(vendedor_contrato.getCpf())) {
	    						   //verifica a inscricao estadual
	    						   if(vend.getIe().equals(inscricao_contrato)) {
	    							   //vendedor foi encontrado
		    						   vendedor_contrato = vend;
		    						   
		    						   //verifica se o vendedor possui a conta bancaria do cadastro
		    						   ArrayList<ContaBancaria> contas = gerenciar_vendedores.getContas(vend.getId());
		    						   for(ContaBancaria conta : contas) {
		    							   if(conta.getAgencia().equals(agencia)) {
		    								   if(conta.getConta().equals(conta_corrente)) {
		    									   //conta do contrato encontrada
		    									   conta_bancaria_cadastrada = true;
		    								   }
		    							   }
		    						   }
		    						   vendedor_foi_cadastrado = true;
		    						   break;
	    						   }
	    						  
	    					   }
	    				   }
	    				   
	    			   }else {
	    				   //o vendedor do contrato e uma pessoa juridica
                               if(vend.getTipo_pessoa() == 1) {
                            	   if(vend.getCnpj().equals(vendedor_contrato.getCnpj())) {
		    						   //vendedor foi encontrado
                            		   if(vend.getIe().equals(inscricao_contrato)) {
		    						   vendedor_contrato = vend;
		    						   //verifica se o vendedor possui a conta bancaria do cadastro
		    						   ArrayList<ContaBancaria> contas = gerenciar_vendedores.getContas(vend.getId());
		    						   for(ContaBancaria conta : contas) {
		    							   if(conta.getAgencia().equals(agencia)) {
		    								   if(conta.getConta().equals(conta_corrente)) {
		    									   //conta do contrato encontrada
		    									   conta_bancaria_cadastrada = true;
		    								   }
		    							   }
		    						   }
		    						   vendedor_foi_cadastrado = true;
		    						   break;
                            		   }
		    					   }
	    				   }
	    			   }
	    			
	    			}
	    			
	    			if(vendedor_foi_cadastrado) {
	    				if(conta_bancaria_cadastrada) {
	    					JOptionPane.showMessageDialog(null, "O vendedor que nao estava cadastrado, agora esta cadastrado!");
		    				cadastrar_contrato = true;
	    				}else {
	    					JOptionPane.showMessageDialog(null, "O vendedor que nao estava cadastrado, foi cadastrado, mas a conta bancaria para o contrato nao foi encontrada!");
		    				cadastrar_contrato = false;
	    				}
	    				
	    			}else {
	    				JOptionPane.showMessageDialog(null, "O vendedor que nao estava cadastrado, não foi cadastrado ainda, tente novamente!");
	    				cadastrar_contrato = false;
	    			}
	    			
	    			
			}  
			else
			{
				
				cadastrar_contrato = false;
			}
			
		
			
		}
    
		

		if(cadastrar_contrato) {
			boolean ja_existe = false;
			
			//verifica pelo codigo do contrato se ele ja nao esta cadastrado
			GerenciarBancoContratos gerenciar_cts = new GerenciarBancoContratos();
			ArrayList<CadastroContrato> contratos = gerenciar_cts.getContratos();
			
			for(CadastroContrato ctr : contratos) {
				if(ctr.getCodigo().equals(codigo_contrato)) {
					ja_existe  = true;
					break;
				}
			}
			
			if(!ja_existe) {
				//contrato nao existe, cadastrar
				JOptionPane.showMessageDialog(null, "Hapto para realizar o cadastro do contrato!");
				
				//seta os compradores do contrato
    			CadastroCliente compradores [] = new CadastroCliente[3];
    			compradores[0] = comprador_contrato;
    			
    			contrato_local.setCompradores(compradores);
    			
    			//seta os vendedores do contrato
    			
    		
    				CadastroCliente vendedores [] = new CadastroCliente[3];
    				vendedores[0] = vendedor_contrato;
        			
    			
        			contrato_local.setVendedores(vendedores);
        			
        			
        			
    			
				//adicionar pagamento ao contrato
        		ArrayList<CadastroContrato.CadastroPagamento> pagamentos = new ArrayList<>();
        		
        		CadastroContrato.CadastroPagamento modeloPag = new CadastroContrato.CadastroPagamento();
				modeloPag.setConta(conta_pagamento);
				modeloPag.setData_pagamento(data_pagamento);
				modeloPag.setValor(new BigDecimal(Double.toString(valor_total)));
				
				pagamentos.add(modeloPag);
				contrato_local.setPagamentos(pagamentos);
				
				//adicionar a tarefa
				
				ArrayList<CadastroContrato.CadastroTarefa> tarefas = new ArrayList<>();
				CadastroContrato.CadastroTarefa tarefa = new CadastroContrato.CadastroTarefa();

			
					tarefa.setNome_tarefa("Contrato Importado");
			
				// cria a tarefa de insercao de contrato
				tarefa.setId_tarefa(0);
				tarefa.setDescricao_tarefa("Criação de tarefa");

				tarefa.setStatus_tarefa(1);
				tarefa.setMensagem("tarefa criada");

				GetData data = new GetData();
				tarefa.setHora(data.getHora());
				tarefa.setData(data.getData());
				tarefa.setHora_agendada(data.getHora());
				tarefa.setData_agendada(data.getData());

				tarefa.setCriador(login);
				tarefa.setExecutor(login);

				tarefa.setPrioridade(1);
				
				tarefas.add(tarefa);
				
				contrato_local.setLista_tarefas(tarefas);
                contrato_local.setStatus_contrato(1);
				
			
				
					//copiar o arquivo para a pasta do comprador
					
					String nome_comprador_arquivo ;
					String nome_vendedor_arquivo;
				
					
					if(compradores[0].getTipo_pessoa() == 0)
						nome_comprador_arquivo = compradores[0].getNome_empresarial();
					else
						nome_comprador_arquivo = compradores[0].getNome_fantaia();
					
					if(vendedores[0].getTipo_pessoa() == 0)
						nome_vendedor_arquivo = vendedores[0].getNome_empresarial();
					else
						nome_vendedor_arquivo = vendedores[0].getNome_fantaia();

					String servidor_unidade = configs_globais.getServidorUnidade();

					   //é um comprato pai, salvar na pasta do comprador

					
						 String caminho_salvar_contrato__no_hd = servidor_unidade + "E-contract\\arquivos\\clientes\\" + nome_comprador_arquivo + "\\contratos" + "\\compra\\"  + ano_contrato + "\\" + contrato_local.getModelo_safra().getProduto().getNome_produto() + "\\";
						System.out.println("caminho para salvar o contrato do comprador no hd: " + caminho_salvar_contrato__no_hd);	
						String caminho_salvar_contrato_no_banco_dados = "E-contract\\\\arquivos\\\\clientes\\\\" + nome_comprador_arquivo + "\\\\contratos" + "\\\\compra\\\\"  + ano_contrato + "\\\\" + contrato_local.getModelo_safra().getProduto().getNome_produto() + "\\\\";

					      String nome_pasta_arquivo = contrato_local.getCodigo();

					      String nome_arquivo = contrato_local.getCodigo() + " "  + nome_comprador_arquivo + " x " + nome_vendedor_arquivo ; 
					     
							
							String caminho_completo_salvar_contrato_no_hd = caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\" + nome_arquivo ;
							
						    String caminho_completo_salvar_contrato_no_bando_dados = caminho_salvar_contrato_no_banco_dados + nome_pasta_arquivo +"\\\\" + nome_arquivo;
						    String nome_diretorio_arquivo_contrato = caminho_salvar_contrato_no_banco_dados + nome_pasta_arquivo;

							//criar o diretorio
							ManipularTxt manipular = new ManipularTxt();
							if(manipular.criarDiretorio(caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\"))
							{
								System.out.println("diretorio criado para o novo contrato");
								
								//criar pastas documentos e comprovantes
           						if(manipular.criarDiretorio(caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\comprovantes"))
    							{
    					    		  //criar diretorio documentos
    								  if(manipular.criarDiretorio(caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\documentos"))
    									{

    									}else {

    									}	

    							}else {

    							}
           						
								 //copiar arquivo para o novo diretorio
								
								boolean copiar = manipular.copiarNFe(file.getAbsolutePath(), caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\" + nome_arquivo + ".pdf" );
                                   if(copiar) {
               						JOptionPane.showMessageDialog(null, "Arquivo copiado para a nova pasta!");
               						//inserir o contrato no banco de dados
               						contrato_local.setCaminho_diretorio_contrato(nome_diretorio_arquivo_contrato);
               						contrato_local.setCaminho_arquivo(caminho_salvar_contrato_no_banco_dados + nome_pasta_arquivo + "\\\\" + nome_arquivo + ".pdf");
               						contrato_local.setNome_arquivo(nome_arquivo + ".pdf");
               						
                					GerenciarBancoContratos gerenciarContratos = new GerenciarBancoContratos();
                					
                					int insercao = gerenciarContratos.inserirContrato(contrato_local, null);
                					if(insercao == 1) {
                  						JOptionPane.showMessageDialog(null, "O Contrato foi salvo no banco de dados");
                  						
                  						//inserir o contrato na nuvem
                  						 Nuvem nuvem = new Nuvem();
                  		            	 nuvem.abrir();
                  		                 nuvem.testar();
                  		                
                  		                boolean retorno = nuvem.carregar(caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\" + nome_arquivo + ".pdf", nome_arquivo + ".pdf");
                  		                 if(retorno) {
                       						JOptionPane.showMessageDialog(null, "O Contrato  foi salvo na nuvem");
                  		                 }else {
                       						JOptionPane.showMessageDialog(null, "O Contrato não foi salvo na nuvem");

                  		                 }

                					}else {
                  						JOptionPane.showMessageDialog(null, "O Contrato não foi salvo no banco de dados");

                					}

                                   }else {
                  						JOptionPane.showMessageDialog(null, "O arquivo não foi copiado para  nova pasta, tente novamente");
                  						JOptionPane.showMessageDialog(null, "origem: " + file.getAbsolutePath() + "\ndestino: " + caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\" + nome_arquivo + ".pdf");

                                   }
								
							}else {
								System.out.println("erro ao criar diretorio para o contrato ");
          						JOptionPane.showMessageDialog(null, "O arquivo não foi copiado para  nova pasta, tente novamente");
          						JOptionPane.showMessageDialog(null, "origem: " + file.getAbsolutePath() + "\ndestino: " + caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\" + nome_arquivo + ".pdf");

								
							}
					
				
				
				
				
				
				
				
				
			}else {
				JOptionPane.showMessageDialog(null, "O Contrato de codigo " + contrato_local.getCodigo() + " já esta cadastrado!");

			}
			
			
			
		}else {
			JOptionPane.showMessageDialog(null, "Não hapto para realizar o cadastro do contrato!");

		}
		
		
		return contrato_local;
		}catch(Exception e) {
			
			JOptionPane.showMessageDialog(null, "Erro ao importar contrato da Fortunato\nTente a imporação manual");
			return null;
		}
		
		
	}
	
	public CadastroContrato contratoBungue(CadastroContrato contrato_local) {

		try {
		String codigo_contrato = tratamentoDados.tratar("N.º ", "DADOS").replaceAll("[^0-9]", "");
		//JOptionPane.showMessageDialog(null, "Codigo do contrato: " + codigo_contrato);
		contrato_local.setCodigo(codigo_contrato);
		contrato_local.setSub_contrato(3);
		
		contrato_local.setFrete("Não Especificado");
		contrato_local.setArmazenagem("Não Especificado");


		CadastroCliente comprador_contrato = new CadastroCliente();
		String cnpj_comprador = tratamentoDados.tratar("CNPJ: ", "ENDEREÇO").replaceAll("[^0-9]", "");
		
		//JOptionPane.showMessageDialog(null, "cnpj do comprador: " +cnpj_comprador);

		//procura nos clientes se existe este comprador
	     GerenciarBancoClientes gerenciar = new GerenciarBancoClientes();
	     ArrayList<CadastroCliente> clientes = gerenciar.getClientes(0, 0, "");
	     
		for(CadastroCliente cliente : clientes) {
			if(cliente.getTipo_pessoa() == 1) {

			if(cliente.getCnpj().equals(cnpj_comprador)) {
				comprador_contrato = cliente;
				break;
			}
			}
		}
		
		
		
	
		
	
		
		//safra
		String safra =  tratamentoDados.tratar("Safra: ", "Origem");
		
		int ano_colheita_safra = Integer.parseInt(safra.replaceAll("[^0-9]", ""));
		int ano_plantio_safra = ano_colheita_safra - 1;
		
		safra = Integer.toString(ano_plantio_safra) + "/" + Integer.toString(ano_colheita_safra);
		//JOptionPane.showMessageDialog(null, "safra: " + safra);
	
		//encontrar a linha que contem a string "OBJETO"
		 int linha_objeto = -1;
		for(int i = 0; i < lines.length; i++) {
			if(lines[i].contains("I. OBJETO")) {
				linha_objeto = i; 
               break;
			}
		}
		
		
		String fazenda_lavoura = lines[linha_objeto - 5];
		 String uf_inscricao_contrato =  lines[linha_objeto - 3].replaceAll("ESTADO: ", "").trim();
		 String inscricao_contrato = tratamentoDados.tratar("INSCRIÇÃO ESTADUAL: ", "I.").replaceAll(",", "");
		 
		 
		// JOptionPane.showMessageDialog(null, "Inscricao do contrato: " + inscricao_contrato);
		 //JOptionPane.showMessageDialog(null, "UF da inscrição: " + uf_inscricao_contrato);
		 //JOptionPane.showMessageDialog(null, "Endereço: " + fazenda_lavoura);
          
			CadastroCliente vendedor_contrato = new CadastroCliente();

			String identificacao_vendedor = tratamentoDados.tratar("CNPJ/CPF: ", "ENDEREÇO:");
			identificacao_vendedor = identificacao_vendedor.replaceAll("[^0-9]+", "");
	        int tipo_vendedor = -1;
	        vendedor_contrato.setIe(inscricao_contrato);
	        
	        //JOptionPane.showMessageDialog(null, "Id do vendedor: " + identificacao_vendedor);
			

	        if(identificacao_vendedor.length() == 11) {
	        	tipo_vendedor = 0;
	        	vendedor_contrato.setTipo_pessoa(0);
	        	vendedor_contrato.setCpf(identificacao_vendedor);
	        }
	        else if(identificacao_vendedor.length() == 14) {
	        	tipo_vendedor = 1;
	        	vendedor_contrato.setTipo_pessoa(1);
	        	vendedor_contrato.setCnpj(identificacao_vendedor);
	        }
			
	        boolean vendedor_cadastrado = false;
			for(CadastroCliente cliente : clientes) {
				
				if(tipo_vendedor == 0) {
					if(cliente.getTipo_pessoa() == 0) {
						if(cliente.getCpf().equals(identificacao_vendedor)) {
							vendedor_contrato = cliente;
							vendedor_cadastrado  = true;
						break;
						}
					}
					
				}else {
					if(cliente.getTipo_pessoa() == 1) {
						if(cliente.getCnpj().equals(identificacao_vendedor)) {
							vendedor_contrato = cliente;
							vendedor_cadastrado  = true;

						break;
						}
					}
				}
				
			}
			
		
			
			//seta os vendedores do contrato
			String nome_vendedor = "";
			
			if(vendedor_cadastrado) {
				
				if(vendedor_contrato.getTipo_pessoa() == 0) {
					//pessoa fisica
					nome_vendedor = vendedor_contrato.getNome_empresarial();
				}else {
					nome_vendedor = vendedor_contrato.getNome_fantaia();

				}
				
				
				
			}
		
		//dados do produto
		 String produto = "";
		String transgenia_proxima =  "";
		//percorre as linhas do array procurando a palavra soja
		for(int i = 0; i < lines.length; i++) {
			if(lines[i].contains("Soja GMO")) {
				produto = "SOJA GMO";
              transgenia_proxima =  "Transgenico(GMO)";
              break;
			}else if((lines[i].contains("soja"))){
				produto = "SOJA GMO";

				transgenia_proxima =  "Transgenico(GMO)";
	               break;
			}
		}
		
		
		//procura pelo produto no  banco de dados
		GerenciarBancoProdutos gerenciar_produtos = new GerenciarBancoProdutos();
		ArrayList<CadastroProduto> produtos = gerenciar_produtos.getProdutos();
		for(CadastroProduto prod : produtos) {
			if(prod.getNome_produto().equalsIgnoreCase(produto)) {
				if(prod.getTransgenia().equalsIgnoreCase(transgenia_proxima)) {
					contrato_local.setModelo_produto(prod);
					break;
				}
				
				
			}
			
		}
		
		//procura pela safra com este produto no banco de dados
		
		  GerenciarBancoSafras gerenciar_safra = new GerenciarBancoSafras();
		 
		ArrayList<CadastroSafra> safras = gerenciar_safra.getSafras();
		for(CadastroSafra saf : safras) {
			if(saf.getProduto().getId_produto() == contrato_local.getModelo_produto().getId_produto()) {
				//encontrado a safra que possui o produto deste contrato
				contrato_local.setModelo_safra(saf);
				break;
				
			}
				
		}
		
		
		//quantidade do produto
		String quantidade = tratamentoDados.tratar("Quantidade: ", "Toneladas");
		quantidade = quantidade.replaceAll("[^0-9,]", "");
		quantidade = quantidade.replace(",", ".");

		quantidade = quantidade.trim();
		//quantidade = quantidade.replace(".", "");
		double quantidade_double = Double.parseDouble(quantidade) * 1000;
		double quantidade_double_sacos = quantidade_double / 60;
		
		
		//seta a quantidade no contrato
		contrato_local.setQuantidade(quantidade_double_sacos);
		//seta a medida no contrato
		contrato_local.setMedida("Sacos");
		//JOptionPane.showMessageDialog(null, "quantidade em sacos: " + quantidade_double_sacos);
		//JOptionPane.showMessageDialog(null, "quantidade em quilogramas: " + quantidade_double);

		//valor
		
		int linha_objeto_preco = 1;
		for(int i = 0; i < lines.length; i++) {
			if(lines[i].contains("III. PREÇO")) {
				linha_objeto_preco = i;  
               break;
			}
		}
		
		int linha_valor = linha_objeto_preco++;
		String valor =  tratamentoDados.tratar("III. PREÇO", "saca").replaceAll("[^0-9]+", "");
		valor = valor.substring(0, 2).concat(".").concat( valor.substring(2 , 4));
		double valor_double = Double.parseDouble(valor);
		
		//seta o valor por saco no contrato
		contrato_local.setValor_produto(valor_double);
		
		double valor_total = quantidade_double_sacos * valor_double;
		
		//seta o valor total no contrato
		
		

		
		contrato_local.setValor_a_pagar(new BigDecimal(Double.toString(valor_total)));
		
		//JOptionPane.showMessageDialog(null, "Valor por saco: " + valor);
		//JOptionPane.showMessageDialog(null, "Valor total: " + valor_total);

		valor = tratamentoDados.tratar("Valor: ", "reais");
		valor = valor.replaceAll("[^0-9.,]", "");
		
		//JOptionPane.showMessageDialog(null, "Valor do pagamento no contrato: " + valor);
		//prazo final da entrada

	
		int linha_data_entrega = -1;
		
		for(int i = 0; i < lines.length; i++) {
			if(lines[i].contains("apurados")) {
				
				linha_data_entrega = i+1;
				break;
			}
		}
		

		String conteudo_data_entrega[] = lines[linha_data_entrega].split(" ");

		String data_entrega = conteudo_data_entrega[4];

		data_entrega = data_entrega.replaceAll("[^0-9]", "/");

		contrato_local.setData_entrega(data_entrega);
		//JOptionPane.showMessageDialog(null, "Data da entrega:" + data_entrega);

		
		//local retirada
		String local_retirada = "LD ARMAZENS GERAIS LTDA";
		
		//JOptionPane.showMessageDialog(null, "local retirada: " + local_retirada);
		
		//data do contrato
		
		int linha_data = -1;
		for(int i = 0; i < lines.length; i++) {
			if(lines[i].contains("Comarca")) {
			
				linha_data = i;
				break;
			}
		}
		
		
	
		
		linha_data = linha_data +  1;
		 String local_data = lines[linha_data];
		 // JOptionPane.showMessageDialog(null, "linha para busca de data do contrato: " + local_data) ;

		 local_data = local_data.replace(",", "");
		 String conteudo_data_contrato[] = local_data.split(" ");
		 String local_assinatura = conteudo_data_contrato[0] + " " + conteudo_data_contrato[2];
	
		 String dia_contrato = conteudo_data_contrato[3];
		 String mes_extenso = conteudo_data_contrato[5];
			String mes_decimal = "";
		 
			if(mes_extenso.equals("Janeiro")) {
				mes_decimal = "01";
			}else if (mes_extenso.equals("Fevereiro")) {
				mes_decimal = "02";
			}else if (mes_extenso.equals("Março")) {
				mes_decimal = "03";
			}else if (mes_extenso.equals("Abril")) {
				mes_decimal = "04";
			}else if (mes_extenso.equals("Maio")) {
				mes_decimal = "05";
			}else if (mes_extenso.equals("Junho")) {
				mes_decimal = "06";
			}else if (mes_extenso.equals("Julho")) {
				mes_decimal = "07";
			}else if (mes_extenso.equals("Agosto")) {
				mes_decimal = "08";
			}else if (mes_extenso.equals("Setembro")) {
				mes_decimal = "09";
			}else if (mes_extenso.equals("Outubro")) {
				mes_decimal = "10";
			}else if (mes_extenso.equals("Novembro")) {
				mes_decimal = "12";
			}else if (mes_extenso.equals("Dezembro")) {
				mes_decimal = "12";
			}
		 
		 
		 String ano_contrato = conteudo_data_contrato[conteudo_data_contrato.length - 1];
		 ano_contrato = ano_contrato.replace(".", "");
		 String data_completa = dia_contrato + "/" + mes_decimal + "/" + ano_contrato;
		 data_completa = data_completa.replace(".", "");
		 //seta a data do contrato
		 contrato_local.setData_contrato(data_completa);
		 
		 //JOptionPane.showMessageDialog(null, "Local do contrato: " + local_assinatura) ;

		 //JOptionPane.showMessageDialog(null, "Data do contrato: " + data_completa) ;
		 
		 
		 //linha com informacoes do pagamento do contrato
			int linha_pagamento = -1;
			for(int i = 0; i < lines.length; i++) {
				if(lines[i].contains("3.5.")) {
					linha_pagamento = i;
					break;
				}
			}//quebra a linha de informacoes de pagamento
			String conteudo_pagamento []= lines[linha_pagamento + 1].split(" ");
			
		 //conta bancaria do pagagamento
			
			int linha_banco = -1;
			for(int i = 0; i < lines.length; i++) {
				if(lines[i].contains("Banco:")) {
					linha_banco = i;
						break;
				}
			}
			
			
			linha_banco++;
			// JOptionPane.showMessageDialog(null, "Linha banco: " + lines[linha_banco]);

			 String conteudo_linha_banco []= lines[linha_banco].split("-");
			 
			
		 String banco = conteudo_linha_banco[0];
		 String agencia =  tratamentoDados.tratar("Agência:", "Conta").replaceAll(" ", "").replace(",", "");
		 String conta_corrente =   tratamentoDados.tratar("Conta: ", "V.").replaceAll("[^0-9-]", "").replace(",", "");
		 String data_pagamento =  tratamentoDados.tratar("Data de Pagamento: ", ",");
		 data_pagamento = data_pagamento.replaceAll("[^0-9]", "/");
         String codigo_banco = tratamentoDados.tratar("Cód. Banco: ", "Banco:").replace(",", "");
	
		 
         //JOptionPane.showMessageDialog(null, "Banco: " + banco);
         // JOptionPane.showMessageDialog(null, "Cod Banco: " + codigo_banco);
         //  //
         // JOptionPane.showMessageDialog(null, "Agencia: " + agencia);
		 // JOptionPane.showMessageDialog(null, "cc: " + conta_corrente);
         //JOptionPane.showMessageDialog(null, "data pagmento: " + data_pagamento);

		ContaBancaria conta_pagamento = new ContaBancaria();
		 conta_pagamento.setBanco(banco);
		 conta_pagamento.setCodigo(codigo_banco);
		 conta_pagamento.setAgencia(agencia);
		 conta_pagamento.setConta(conta_corrente);
		 conta_pagamento.setCpf_titular(identificacao_vendedor);
		
		 int id_conta_pagamento = -1;
		 boolean cadastrar_contrato = false;
		if(vendedor_cadastrado) {
			
			//o vendedor esta cadastrador, agora verifica se a instricao estadual do contrato e a mesma do cadastro
			if(vendedor_contrato.getIe().replaceAll(" ", "").equals(inscricao_contrato.replaceAll(" ", ""))) {
				
				// o vendedor tem a mesma inscricao estadual, verifica se tem a conta bancaria
				 //verifica se o vendedor possui a conta bancaria do cadastro
				boolean tem_conta = false;
				
				GerenciarBancoClientes gerenciar_procurar = new GerenciarBancoClientes();
				   ArrayList<ContaBancaria> contas = gerenciar_procurar.getContas(vendedor_contrato.getId());
				   for(ContaBancaria conta : contas) {
					   if(conta.getAgencia().equals(agencia)) {
						   if(conta.getConta().equals(conta_corrente)) {
							   //conta do contrato encontrada
							   id_conta_pagamento  = conta.getId_conta();
							   tem_conta = true;
						   }
					   }
				   }
				   
				if(tem_conta) {
				JOptionPane.showMessageDialog(null, 
						"Codigo do contrato: " + codigo_contrato + 
						"\nComprador: " + comprador_contrato.getNome_fantaia()+
						"\nVendedor: " + nome_vendedor+
						"\nIE do Vendedor: " + vendedor_contrato.getIe() +
						"\nProduto: " + produto + 
						"\nSafra: " + safra +
						"\nQuantidade(KG): " + quantidade_double + 
    					"\nnQuantidade(Sacos): " + quantidade_double_sacos +
    					"\nValor por saco: " + valor + 
    					"\nValor total: " + valor_total +
    					
    					"\nFazenda Contrato: " + fazenda_lavoura+
    					"\nInscricao Contrato: " + inscricao_contrato +
    					
    					"\nLocal Retirada: " + local_retirada +
    					"\nData entrega: " + data_entrega +
    			        "\nData do contrato: " + data_completa + "\nLocal: " + local_assinatura+
    			        "\nBanco: " + banco + 
    			        "\nAgencia: " + agencia + 
    			        "\nCC: " + conta_corrente + 
    			        "\nData Pagamento: " + data_pagamento);
				
				cadastrar_contrato = true;
				
				//abre a tela de cadastro de contrato
				}else {
					//cadastra a conta bancaria
					ContaBancaria conta_cadastrar = new ContaBancaria();
					conta_cadastrar.setAgencia(agencia);
					conta_cadastrar.setBanco(banco);
					conta_cadastrar.setConta(conta_corrente);
					if(vendedor_contrato.getTipo_pessoa() == 0) {
						//pessoa fisica
						conta_cadastrar.setNome(vendedor_contrato.getNome_empresarial());
						conta_cadastrar.setCpf_titular(vendedor_contrato.getCpf());

					}else {
						//pessoa juridica
						conta_cadastrar.setNome(vendedor_contrato.getNome_fantaia());
						conta_cadastrar.setCpf_titular(vendedor_contrato.getCnpj());
						
					}
					
					//cadastrar a conta
					GerenciarBancoClientes gerenciar_cadastrar = new GerenciarBancoClientes();
					ArrayList<ContaBancaria> contas_cadastrar = new ArrayList<>();
					contas_cadastrar.add(conta_cadastrar);
                    gerenciar_cadastrar.adicionarContaBancaria(contas_cadastrar, vendedor_contrato.getId());
                    
                    //verifica se a nota foi conta foi cadastrada
                    boolean tem_conta_replier = false;
					
					GerenciarBancoClientes gerenciar_procurar_novamente = new GerenciarBancoClientes();
					   ArrayList<ContaBancaria> contas_2_pesquisa = gerenciar_procurar_novamente.getContas(vendedor_contrato.getId());
					   for(ContaBancaria conta : contas_2_pesquisa) {
						   if(conta.getAgencia().equals(agencia)) {
							   if(conta.getConta().equals(conta_corrente)) {
								   //conta do contrato encontrada
								   id_conta_pagamento  = conta.getId_conta();

								   tem_conta_replier = true;
							   }
						   }
					   }
					   
					   if(tem_conta_replier) {
						   //a conta foi cadastradaasdas
						   cadastrar_contrato = true;
	    					

						   
					   }else {
						   //a conta nao foi cadastrada
							JOptionPane.showMessageDialog(null, "O vendedor esta cadastrado, mas não possui a conta bancaria do pagamento deste contrato\n"
		    							+ " Tente adicionar manualmente!");
						   cadastrar_contrato = false;
					   }
					
					
				}
			}else {
				
				//informa ao usuario que o vendedor possui o cadastro, mas a inscricao estadual nao e a mesma, 
				//e pergunta se ele deseja cadastrar
				JOptionPane.showMessageDialog(null, 
						"Codigo do contrato: " + codigo_contrato + 
						"\nComprador: " + comprador_contrato.getNome_fantaia()+
						"\nVendedor: " + nome_vendedor+
						"\nIE do Vendedor: " + vendedor_contrato.getIe() +
						"\nProduto: " + produto + 
						"\nSafra: " + safra +
						"\nQuantidade(KG): " + quantidade_double + 
    					"\nnQuantidade(Sacos): " + quantidade_double_sacos +
    					"\nValor por saco: " + valor + 
    					"\nValor total: " + valor_total +
    					
    					"\nFazenda Contrato: " + fazenda_lavoura+
    					"\nInscricao Contrato: " + inscricao_contrato +
    				
    					"\nLocal Retirada: " + local_retirada +
    					"\nData entrega: " + data_entrega +
    			        "\nData do contrato: " + data_completa + "\nLocal: " + local_assinatura+
    			        "\nBanco: " + banco + 
    			        "\nAgencia: " + agencia + 
    			        "\nCC: " + conta_corrente + 
    			        "\nData Pagamento: " + data_pagamento
    			        );
			
				
				if (JOptionPane.showConfirmDialog(null, 
			            "Apesar do Vendedor do Contrato já possur um cadastro"
			            + " sua I.E não é a mesma do contrato."
			            + "\nDeseja cadastrar o vendedor do contrato com  a nova Inscrição Estadual?", "Cadastrar Vendedor Com Nova IE", 
			            JOptionPane.YES_NO_OPTION,
			            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					    
					TelaCadastroCliente tela = new TelaCadastroCliente(1, null, null);
					vendedor_contrato.setIe(inscricao_contrato);
					tela.setInformacoesNovoCliente(vendedor_contrato, uf_inscricao_contrato, conta_pagamento);

					tela.setVisible(true);

					
					//verifica se o vendedor foi cadastrado:
					
					   GerenciarBancoClientes gerenciar_vendedores = new GerenciarBancoClientes();
		    		     ArrayList<CadastroCliente> vendedores = gerenciar.getClientes(0, 0, "");
		    		     
		    		     boolean vendedor_foi_cadastrado  = false;
		    		     boolean conta_bancaria_cadastrada = false;
		    			for(CadastroCliente vend : vendedores) {
		    			   if(vendedor_contrato.getTipo_pessoa() == 0) {
		    				   //o vendedor do contrato e uma pessoa fisica
		    				   if(vend.getTipo_pessoa() == 0) {
		    					   if(vend.getCpf().equals(vendedor_contrato.getCpf())) {

		    						   if(vend.getIe().equals(inscricao_contrato)) {

		    						   //vendedor foi encontrado
		    						   vendedor_contrato = vend;
		    						   
		    						   //verifica se o vendedor possui a conta bancaria do cadastro
		    						   ArrayList<ContaBancaria> contas = gerenciar_vendedores.getContas(vend.getId());
		    						   for(ContaBancaria conta : contas) {
		    							   if(conta.getAgencia().equals(agencia)) {
		    								   if(conta.getConta().equals(conta_corrente)) {
		    									   //conta do contrato encontrada
		    									   id_conta_pagamento  = conta.getId_conta();

		    									   conta_bancaria_cadastrada = true;
		    								   }
		    							   }
		    						   }
		    						   
		    						   vendedor_foi_cadastrado = true;
		    						   break;
		    						   }
		    					   }
		    				   }
		    				   
		    			   }else {
		    				   //o vendedor do contrato e uma pessoa juridica
                                   if(vend.getTipo_pessoa() == 1) {
                                	   if(vend.getCnpj().equals(vendedor_contrato.getCnpj())) {
    		    						   if(vend.getIe().equals(inscricao_contrato)) {
                                		   //vendedor foi encontrado
    		    						   vendedor_contrato = vend;
    		    						   
    		    						   //verifica se o vendedor possui a conta bancaria do cadastro
    		    						   ArrayList<ContaBancaria> contas = gerenciar_vendedores.getContas(vend.getId());
    		    						   for(ContaBancaria conta : contas) {
    		    							   if(conta.getAgencia().equals(agencia)) {
    		    								   if(conta.getConta().equals(conta_corrente)) {
    		    									   //conta do contrato encontrada
    		    									   id_conta_pagamento  = conta.getId_conta();

    		    									   conta_bancaria_cadastrada = true;
    		    								   }
    		    							   }
    		    						   }
    		    						   
    		    						   vendedor_foi_cadastrado = true;
    		    						   break;
                                	   }
    		    					   }
		    				   }
		    			   }
		    			
		    			}
		    			
		    			if(vendedor_foi_cadastrado) {
		    				if(conta_bancaria_cadastrada) {
		    					JOptionPane.showMessageDialog(null, "O vendedor que nao estava cadastrado para a IE do contrato, agora esta cadastrado!");
    		    				
		    					
		    					cadastrar_contrato = true;
		    				}else {
		    					JOptionPane.showMessageDialog(null, "O vendedor que nao estava cadastrado para a IE do contrato, agora esta cadastrado, porem a conta bancaria nao foi encontrada!");
    		    				cadastrar_contrato = true;
		    				}
		    			
		    			}else {
		    				JOptionPane.showMessageDialog(null, "O vendedor que nao estava cadastrado, não foi cadastrado ainda, tente novamente!");
		    				cadastrar_contrato = false;
		    			}
			}
			}

		}else {
			JOptionPane.showMessageDialog(null, 
					"Codigo do contrato: " + codigo_contrato + 
					"\nComprador: " + comprador_contrato.getNome_fantaia()+
					"\nProduto: " + produto + 
					"\nSafra: " + safra +
					"\nQuantidade(KG): " + quantidade_double + 
					"\nnQuantidade(Sacos): " + quantidade_double_sacos +
					"\nValor por saco: " + valor + 
					"\nValor total: " + valor_total +
					
					"\nFazenda Contrato: " + fazenda_lavoura+
					"\nInscricao Contrato: " + inscricao_contrato +
				
					"\nLocal Retirada: " + local_retirada +
					"\nData entrega: " + data_entrega +
			        "\nData do contrato: " + data_completa + "\nLocal: " + local_assinatura+
			        "\nBanco: " + banco + 
			        "\nAgencia: " + agencia + 
			        "\nCC: " + conta_corrente + 
			        "\nData Pagamento: " + data_pagamento+
			        "\nVendedor do contrato não esta cadastrado: "	+
			        "\nTipo do Vendedor: " + tipo_vendedor +
			        "\nCPF/CNPJ: " + identificacao_vendedor);
			
			
			if (JOptionPane.showConfirmDialog(null, 
		            "Deseja cadastrar o vendedor do contrato?", "Cadastrar Vendedor", 
		            JOptionPane.YES_NO_OPTION,
		            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
				    
				TelaCadastroCliente tela = new TelaCadastroCliente(1, null, null);
				vendedor_contrato.setIe(inscricao_contrato);

				tela.setInformacoesNovoCliente(vendedor_contrato, uf_inscricao_contrato, conta_pagamento);

				tela.setVisible(true);

				
				//verifica se o vendedor foi cadastrado:
				
				   GerenciarBancoClientes gerenciar_vendedores = new GerenciarBancoClientes();
	    		     ArrayList<CadastroCliente> vendedores = gerenciar.getClientes(0, 0, "");
	    		     
	    		     boolean vendedor_foi_cadastrado  = false;
	    		     boolean conta_bancaria_cadastrada = false;
	    		     
	    			for(CadastroCliente vend : vendedores) {
	    			   if(vendedor_contrato.getTipo_pessoa() == 0) {
	    				   //o vendedor do contrato e uma pessoa fisica
	    				   if(vend.getTipo_pessoa() == 0) {
	    					   if(vend.getCpf().equals(vendedor_contrato.getCpf())) {
	    						   //verifica a inscricao estadual
	    						   if(vend.getIe().equals(inscricao_contrato)) {
	    							   //vendedor foi encontrado
		    						   vendedor_contrato = vend;
		    						   
		    						   //verifica se o vendedor possui a conta bancaria do cadastro
		    						   ArrayList<ContaBancaria> contas = gerenciar_vendedores.getContas(vend.getId());
		    						   for(ContaBancaria conta : contas) {
		    							   if(conta.getAgencia().equals(agencia)) {
		    								   if(conta.getConta().equals(conta_corrente)) {
		    									   //conta do contrato encontrada
		    									   id_conta_pagamento  = conta.getId_conta();

		    									   conta_bancaria_cadastrada = true;
		    								   }
		    							   }
		    						   }
		    						   vendedor_foi_cadastrado = true;
		    						   break;
	    						   }
	    						  
	    					   }
	    				   }
	    				   
	    			   }else {
	    				   //o vendedor do contrato e uma pessoa juridica
                               if(vend.getTipo_pessoa() == 1) {
                            	   if(vend.getCnpj().equals(vendedor_contrato.getCnpj())) {
		    						   //vendedor foi encontrado
                            		   if(vend.getIe().equals(inscricao_contrato)) {
		    						   vendedor_contrato = vend;
		    						   //verifica se o vendedor possui a conta bancaria do cadastro
		    						   ArrayList<ContaBancaria> contas = gerenciar_vendedores.getContas(vend.getId());
		    						   for(ContaBancaria conta : contas) {
		    							   if(conta.getAgencia().equals(agencia)) {
		    								   if(conta.getConta().equals(conta_corrente)) {
		    									   //conta do contrato encontrada
		    									   conta_bancaria_cadastrada = true;
		    								   }
		    							   }
		    						   }
		    						   vendedor_foi_cadastrado = true;
		    						   break;
                            		   }
		    					   }
	    				   }
	    			   }
	    			
	    			}
	    			
	    			if(vendedor_foi_cadastrado) {
	    				if(conta_bancaria_cadastrada) {
	    					JOptionPane.showMessageDialog(null, "O vendedor que nao estava cadastrado, agora esta cadastrado!");
		    				cadastrar_contrato = true;
	    				}else {
	    					JOptionPane.showMessageDialog(null, "O vendedor que nao estava cadastrado, foi cadastrado, mas a conta bancaria para o contrato nao foi encontrada!");
		    				cadastrar_contrato = false;
	    				}
	    				
	    			}else {
	    				JOptionPane.showMessageDialog(null, "O vendedor que nao estava cadastrado, não foi cadastrado ainda, tente novamente!");
	    				cadastrar_contrato = false;
	    			}
	    			
	    			
			}  
			else
			{
				
				cadastrar_contrato = false;
			}
			
		
			
		}
    
		

		if(cadastrar_contrato) {
			boolean ja_existe = false;
			
			//verifica pelo codigo do contrato se ele ja nao esta cadastrado
			GerenciarBancoContratos gerenciar_cts = new GerenciarBancoContratos();
			ArrayList<CadastroContrato> contratos = gerenciar_cts.getContratos();
			
			for(CadastroContrato ctr : contratos) {
				if(ctr.getCodigo().equals(codigo_contrato)) {
					ja_existe  = true;
					break;
				}
			}
			
			if(!ja_existe) {
				//contrato nao existe, cadastrar
				JOptionPane.showMessageDialog(null, "Hapto para realizar o cadastro do contrato!");
				
				//seta os compradores do contrato
    			CadastroCliente compradores [] = new CadastroCliente[3];
    			compradores[0] = comprador_contrato;
    			
    			contrato_local.setCompradores(compradores);
    			
    			//seta os vendedores do contrato
    			
    		
    				CadastroCliente vendedores [] = new CadastroCliente[3];
    				vendedores[0] = vendedor_contrato;
        			
    			
        			contrato_local.setVendedores(vendedores);
        			
        			
        			
    			
				//adicionar pagamento ao contrato
        		ArrayList<CadastroContrato.CadastroPagamento> pagamentos = new ArrayList<>();
        		
        		CadastroContrato.CadastroPagamento modeloPag = new CadastroContrato.CadastroPagamento();
        		
				modeloPag.setConta(gerenciar.getConta(id_conta_pagamento));
				modeloPag.setId(0);
				modeloPag.setData_pagamento(data_pagamento);
				modeloPag.setValor(new BigDecimal(Double.toString(valor_total)));
				
				pagamentos.add(modeloPag);
				contrato_local.setPagamentos(pagamentos);
				
				//adicionar a tarefa
				
				ArrayList<CadastroContrato.CadastroTarefa> tarefas = new ArrayList<>();
				CadastroContrato.CadastroTarefa tarefa = new CadastroContrato.CadastroTarefa();

			
					tarefa.setNome_tarefa("Contrato Importado");
			
				// cria a tarefa de insercao de contrato
				tarefa.setId_tarefa(0);
				tarefa.setDescricao_tarefa("Criação de tarefa");

				tarefa.setStatus_tarefa(1);
				tarefa.setMensagem("tarefa criada");

				GetData data = new GetData();
				tarefa.setHora(data.getHora());
				tarefa.setData(data.getData());
				tarefa.setHora_agendada(data.getHora());
				tarefa.setData_agendada(data.getData());

				tarefa.setCriador(login);
				tarefa.setExecutor(login);

				tarefa.setPrioridade(1);
				
				tarefas.add(tarefa);
				
				contrato_local.setLista_tarefas(tarefas);
                contrato_local.setStatus_contrato(1);
				
			
				
					//copiar o arquivo para a pasta do comprador
					
					String nome_comprador_arquivo ;
					String nome_vendedor_arquivo;
				
					
					if(compradores[0].getTipo_pessoa() == 0)
						nome_comprador_arquivo = compradores[0].getNome_empresarial();
					else
						nome_comprador_arquivo = compradores[0].getNome_fantaia();
					
					if(vendedores[0].getTipo_pessoa() == 0)
						nome_vendedor_arquivo = vendedores[0].getNome_empresarial();
					else
						nome_vendedor_arquivo = vendedores[0].getNome_fantaia();

					String servidor_unidade = configs_globais.getServidorUnidade();

					   //é um comprato pai, salvar na pasta do comprador

						 String caminho_salvar_contrato__no_hd = servidor_unidade + "E-contract\\arquivos\\clientes\\" + nome_comprador_arquivo + "\\contratos" + "\\compra\\"  + ano_contrato + "\\" + contrato_local.getModelo_safra().getProduto().getNome_produto() + "\\";
						System.out.println("caminho para salvar o contrato do comprador no hd: " + caminho_salvar_contrato__no_hd);	
						String caminho_salvar_contrato_no_banco_dados = "E-contract\\\\arquivos\\\\clientes\\\\" + nome_comprador_arquivo + "\\\\contratos" + "\\\\compra\\\\"  + ano_contrato + "\\\\" + contrato_local.getModelo_safra().getProduto().getNome_produto() + "\\\\";

					      String nome_pasta_arquivo = contrato_local.getCodigo();

					      String nome_arquivo = contrato_local.getCodigo() + " "  + nome_comprador_arquivo + " x " + nome_vendedor_arquivo ; 
					     
							
							String caminho_completo_salvar_contrato_no_hd = caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\" + nome_arquivo ;
							
						    String caminho_completo_salvar_contrato_no_bando_dados = caminho_salvar_contrato_no_banco_dados + nome_pasta_arquivo +"\\\\" + nome_arquivo;
						    String nome_diretorio_arquivo_contrato = caminho_salvar_contrato_no_banco_dados + nome_pasta_arquivo;

							//criar o diretorio
							ManipularTxt manipular = new ManipularTxt();
							if(manipular.criarDiretorio(caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\"))
							{
								System.out.println("diretorio criado para o novo contrato");
								
								//criar pastas documentos e comprovantes
           						if(manipular.criarDiretorio(caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\comprovantes"))
    							{
    					    		  //criar diretorio documentos
    								  if(manipular.criarDiretorio(caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\documentos"))
    									{

    									}else {

    									}	

    							}else {

    							}
           														
								
								 //copiar arquivo para o novo diretorio
								boolean copiar = manipular.copiarNFe(file.getAbsolutePath(), caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\" + nome_arquivo + ".pdf" );
                                   if(copiar) {
               						JOptionPane.showMessageDialog(null, "Arquivo copiado para a nova pasta!");
               						//inserir o contrato no banco de dados
               						contrato_local.setCaminho_diretorio_contrato(nome_diretorio_arquivo_contrato);
               						contrato_local.setCaminho_arquivo(caminho_salvar_contrato_no_banco_dados + nome_pasta_arquivo + "\\\\" + nome_arquivo + ".pdf");
               						contrato_local.setNome_arquivo(nome_arquivo + ".pdf");
               						
                					GerenciarBancoContratos gerenciarContratos = new GerenciarBancoContratos();
                					
                					int insercao = gerenciarContratos.inserirContrato(contrato_local, null);
                					if(insercao == 1) {
                  						JOptionPane.showMessageDialog(null, "O Contrato foi salvo no banco de dados");
                  						
                  						//inserir o contrato na nuvem
                  						 Nuvem nuvem = new Nuvem();
                  		            	 nuvem.abrir();
                  		                 nuvem.testar();
                  		                
                  		                boolean retorno = nuvem.carregar(caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\" + nome_arquivo + ".pdf", nome_arquivo + ".pdf");
                  		                 if(retorno) {
                       						JOptionPane.showMessageDialog(null, "O Contrato  foi salvo na nuvem");
                  		                 }else {
                       						JOptionPane.showMessageDialog(null, "O Contrato não foi salvo na nuvem");

                  		                 }

                					}else {
                  						JOptionPane.showMessageDialog(null, "O Contrato não foi salvo no banco de dados");

                					}

                                   }else {
                  						JOptionPane.showMessageDialog(null, "O arquivo não foi copiado para  nova pasta, tente novamente");

                                   }
								
							}else {
								System.out.println("erro ao criar diretorio para o contrato ");
          						JOptionPane.showMessageDialog(null, "O arquivo não foi copiado para  nova pasta, tente novamente");

								
							}
					
				
				
				
				
				
				
				
				
			}else {
				JOptionPane.showMessageDialog(null, "O Contrato de codigo " + contrato_local.getCodigo() + " já esta cadastrado!");

			}
			
			
			
		}else {
			JOptionPane.showMessageDialog(null, "Não hapto para realizar o cadastro do contrato!");

		}
		
		
		return contrato_local;
		}catch(Exception e) {
			
			JOptionPane.showMessageDialog(null, "Erro ao importar contrato da Gavilon\nTente a Importação Manual");
			return null;
		}
		
		
	}
	
	
}
