package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JList;

import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;

import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import cadastros.CadastroCliente;
import cadastros.CadastroContrato;
import cadastros.CadastroNFe;
import cadastros.CadastroPontuacao;
import cadastros.CadastroProduto;
import cadastros.ContaBancaria;
import cadastros.Registros;
import classesExtras.RenderizadorPontuacao;
import classesExtras.RenderizadorSubContrato;
import conexaoBanco.GerenciarBancoClientes;
import conexaoBanco.GerenciarBancoContratos;
import conexaoBanco.GerenciarBancoPontuacao;
import conexaoBanco.GerenciarBancoProdutos;
import conexaoBanco.GerenciarBancoTransferencias;
import manipular.ManipularNotasFiscais;

import javax.swing.border.LineBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;



public class TelaVisaoGeralSubContrato extends JDialog {

	private final JPanel painelPrincipal = new JPanel();
    private JLabel lblTotalContratosConcluidos, lblTotalContratos, lblTotalContratosAbertos;
    private TelaVisaoGeralSubContrato isto;
    private JDialog telaPai;
    private CadastroContrato contrato_local;
    private JLabel lblTotalPagamentos,lblTotalPagamentosEfetuados, lblTotalTransferenciasRetiradas,lblTotalTransferenciasRecebidas,lblTotalPagamentosRestantes;
	private JLabel lblPesoTotal, lblPesoTotalRealCargas, lblPesoTotalRealRestante,lblPesoTotalNotasFiscais, lblPesoTotalNotasFiscaisRestante;

    
	public TelaVisaoGeralSubContrato(CadastroContrato contrato, JFrame janela_pai) {
		setModal(true);

		this.contrato_local = contrato;
		
		 isto = this;
		
		setResizable(true);
		setTitle("E-Contract - Visão Geral do contrato");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1191, 620);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);
		
		JLabel lblTipoDoContrato = new JLabel("Sub-Contrato");
		lblTipoDoContrato.setBounds(10, 21, 93, 16);
		painelPrincipal.add(lblTipoDoContrato);
		
		
		 DefaultListModel<CadastroContrato>   listModelGlobal = new DefaultListModel<CadastroContrato>();
		 
		   GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
			 ArrayList<CadastroContrato>  sub_contratos = gerenciar.getSubContratos(contrato.getId());
		 
		 for(CadastroContrato sub : sub_contratos) {
				
			 
		     listModelGlobal.addElement(sub);
		}
		 
		 RenderizadorSubContrato render = new RenderizadorSubContrato();
		
		JTextPane tPContratoOriginal = new JTextPane();
		tPContratoOriginal.setEditable(false);
		tPContratoOriginal.setFont(new Font("Arial", Font.PLAIN, 14));
		tPContratoOriginal.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		tPContratoOriginal.setBounds(20, 49, 1131, 65);
		tPContratoOriginal.setContentType("text/html");

		NumberFormat z = NumberFormat.getNumberInstance();
		Locale ptBr = new Locale("pt", "BR");

		//nome comprador
				String nome_comprador = "";
				String nome_vendedor1 = "";
				String nome_vendedor2  = "";
				
				CadastroCliente compradores[] = contrato.getCompradores();
				CadastroCliente vendedores[] = contrato.getVendedores();

				
				
				if(compradores[0].getTipo_pessoa() == 0)
				    nome_comprador = compradores[0].getNome_empresarial();
				else
					nome_comprador = compradores[0].getNome_fantaia();
				
				if(vendedores[0].getTipo_pessoa() == 0)
					nome_vendedor1 = vendedores[0].getNome_empresarial();
				else
					nome_vendedor1 = vendedores[0].getNome_fantaia();
				
				if(vendedores[1] != null) {
					if(vendedores[1].getTipo_pessoa() == 0)
						nome_vendedor2 = vendedores[1].getNome_empresarial();
					else
						nome_vendedor2 = vendedores[1].getNome_fantaia();
				}
				

				double quantidade_sacos = 0;
				double quantidade_kg = 0;
				if(contrato.getMedida().equalsIgnoreCase("Sacos")) {
					quantidade_sacos = contrato.getQuantidade();
					quantidade_kg = quantidade_sacos * 60;
				}else if(contrato.getMedida().equalsIgnoreCase("KG")) {
					quantidade_kg = contrato.getQuantidade();
					quantidade_sacos = quantidade_kg / 60;
				}
				  
					String quantidade = z.format(quantidade_sacos);
					
				String valor_por_saco = 	NumberFormat.getCurrencyInstance(ptBr).format(contrato.getValor_produto());
		         String valor_a_pagar = NumberFormat.getCurrencyInstance(ptBr).format(contrato.getValor_a_pagar());
				
				
				String produto = contrato.getModelo_safra().getProduto().getNome_produto();
				
				String safra = contrato.getModelo_safra().getAno_plantio() + "/" + contrato.getModelo_safra().getAno_plantio();
				
				
				if(vendedores[1] == null) {
					tPContratoOriginal.setText("<html> " + contrato.getCodigo() + " <b>" + quantidade +"</b> sacos de " + produto +" da safra <b>" + safra + "</b> no valor de <b>" + valor_por_saco + "</b> por saca,\r\n   "
						+ " do Vendedor <b>" + nome_vendedor1 + "</b> ao Comprador <b>" + nome_comprador + "</b>\r\n Valor Total: <b>" + valor_a_pagar + "</b></html>");
				}else {
					tPContratoOriginal.setText("<html> " + contrato.getCodigo() + " <b>" + quantidade +"</b> sacos de " + produto +" da safra <b>" + safra + "</b> no valor de <b>" + valor_por_saco + "</b> por saca,\r\n   "
							+ " do Vendedor <b>" + nome_vendedor1 + "</b> com o Vendedor <b>" + nome_vendedor2 + "</b> ao Comprador <b>" + nome_comprador + "</b>\r\n Valor Total: <b>" + valor_a_pagar + "</b></html>");
					
				}
		
		
		
		
		
		
		
		painelPrincipal.add(tPContratoOriginal);
		
		JButton btnNewButton = new JButton("Sair");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isto.dispose();
			}
		});
		btnNewButton.setBounds(1061, 528, 90, 28);
		painelPrincipal.add(btnNewButton);
		
		JLabel lblNewLabel_14 = new JLabel("Total:");
		lblNewLabel_14.setBounds(66, 403, 45, 14);
		painelPrincipal.add(lblNewLabel_14);
		

		
		 lblTotalPagamentos = new JLabel("");
		lblTotalPagamentos.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblTotalPagamentos.setBounds(117, 394, 143, 23);
		painelPrincipal.add(lblTotalPagamentos);
		
		JLabel lblNewLabel_14_1 = new JLabel("Efetuados:");
		lblNewLabel_14_1.setBounds(43, 434, 68, 14);
		painelPrincipal.add(lblNewLabel_14_1);
		
		 lblTotalPagamentosEfetuados = new JLabel("");
		lblTotalPagamentosEfetuados.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblTotalPagamentosEfetuados.setBounds(117, 428, 143, 23);
		painelPrincipal.add(lblTotalPagamentosEfetuados);
		
		JLabel lblNewLabel_14_1_1 = new JLabel("Transferencias:(-)");
		lblNewLabel_14_1_1.setBounds(10, 466, 101, 14);
		painelPrincipal.add(lblNewLabel_14_1_1);
		
		 lblTotalTransferenciasRetiradas = new JLabel("");
		lblTotalTransferenciasRetiradas.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblTotalTransferenciasRetiradas.setBounds(117, 460, 143, 23);
		painelPrincipal.add(lblTotalTransferenciasRetiradas);
		
		JLabel lblNewLabel_14_1_1_1 = new JLabel("Transferencias:(+)");
		lblNewLabel_14_1_1_1.setBounds(10, 500, 101, 14);
		painelPrincipal.add(lblNewLabel_14_1_1_1);
		
		lblTotalTransferenciasRecebidas = new JLabel("");
		lblTotalTransferenciasRecebidas.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblTotalTransferenciasRecebidas.setBounds(117, 494, 143, 23);
		painelPrincipal.add(lblTotalTransferenciasRecebidas);
		
		JLabel lblNewLabel_14_2 = new JLabel("Restante:");
		lblNewLabel_14_2.setBounds(53, 544, 68, 14);
		painelPrincipal.add(lblNewLabel_14_2);
		
		 lblTotalPagamentosRestantes = new JLabel("");
		lblTotalPagamentosRestantes.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblTotalPagamentosRestantes.setBounds(117, 535, 143, 23);
		painelPrincipal.add(lblTotalPagamentosRestantes);
		
		JLabel lblNewLabel_14_3 = new JLabel("Pagamentos");
		lblNewLabel_14_3.setBounds(106, 368, 71, 16);
		painelPrincipal.add(lblNewLabel_14_3);
		
		JLabel lblNewLabel_3 = new JLabel("Carregamento de Saída");
		lblNewLabel_3.setBounds(388, 365, 132, 16);
		painelPrincipal.add(lblNewLabel_3);
		
		JLabel lblNewLabel_12 = new JLabel("Total:");
		lblNewLabel_12.setBounds(343, 400, 46, 14);
		painelPrincipal.add(lblNewLabel_12);
		
		
		 lblPesoTotal = new JLabel("0.0 KG");
		lblPesoTotal.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPesoTotal.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblPesoTotal.setBounds(401, 394, 193, 23);
		painelPrincipal.add(lblPesoTotal);
		
		JLabel lblNewLabel_13 = new JLabel("Total Carregado:");
		lblNewLabel_13.setBounds(300, 435, 101, 14);
		painelPrincipal.add(lblNewLabel_13);
		
		 lblPesoTotalRealCargas = new JLabel("");
		lblPesoTotalRealCargas.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPesoTotalRealCargas.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblPesoTotalRealCargas.setBounds(401, 429, 193, 23);
		painelPrincipal.add(lblPesoTotalRealCargas);
		
		JLabel lblNewLabel_13_1 = new JLabel("Restante:");
		lblNewLabel_13_1.setBounds(336, 471, 65, 14);
		painelPrincipal.add(lblNewLabel_13_1);
		
		 lblPesoTotalRealRestante = new JLabel("0.0 Kg");
		lblPesoTotalRealRestante.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPesoTotalRealRestante.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblPesoTotalRealRestante.setBounds(401, 465, 193, 23);
		painelPrincipal.add(lblPesoTotalRealRestante);
		
		JLabel lblNewLabel_3_1 = new JLabel("Notas Fiscais:");
		lblNewLabel_3_1.setBounds(730, 365, 79, 16);
		painelPrincipal.add(lblNewLabel_3_1);
		
		JLabel lblNewLabel_13_2 = new JLabel("Total Carregado:");
		lblNewLabel_13_2.setBounds(628, 396, 92, 16);
		painelPrincipal.add(lblNewLabel_13_2);
		
		
		
		 lblPesoTotalNotasFiscais = new JLabel("");
		lblPesoTotalNotasFiscais.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPesoTotalNotasFiscais.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblPesoTotalNotasFiscais.setBounds(723, 394, 185, 23);
		painelPrincipal.add(lblPesoTotalNotasFiscais);
		
		JLabel lblNewLabel_13_2_1 = new JLabel("Restante:");
		lblNewLabel_13_2_1.setBounds(667, 429, 53, 16);
		painelPrincipal.add(lblNewLabel_13_2_1);
		
		 lblPesoTotalNotasFiscaisRestante = new JLabel("0 Kg | 0 Sacos");
		lblPesoTotalNotasFiscaisRestante.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPesoTotalNotasFiscaisRestante.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblPesoTotalNotasFiscaisRestante.setBounds(723, 428, 185, 23);
		painelPrincipal.add(lblPesoTotalNotasFiscaisRestante);
	
		
		//		textArea.setText("4446.6.5.8888  - 8.000 sacos de sorgo da safra 2020/2021 no valor de R$ 30,00 por saca,\r\n                                                                                                                        do Vendedor LD Armazens Gerais ao Comprador Marcos Alexandre andrea de carvalho\r\n                                                                                                                         Valor Total: R$ 240.000,00");

		
		pesquisar_pagamentos();
		pesquisar_carregamentos();
		this.setLocationRelativeTo(janela_pai);

		
		
		
	}
	
	
	public void pesquisar_pagamentos() {

		ArrayList<CadastroContrato.CadastroPagamentoContratual> lista_pagamentos_contratuais = lista_pagamentos_contratuais = new ArrayList<>();;
		
		ArrayList<CadastroContrato.CadastroTransferenciaPagamentoContratual> lista_transferencias_contratuais_remetente = null;
		ArrayList<CadastroContrato.CadastroTransferenciaPagamentoContratual> lista_transferencias_contratuais_destinatario = null;

		double valor_total_pagamentos = Double.parseDouble(contrato_local.getValor_a_pagar().toPlainString());
		double valor_total_pagamentos_efetuados = 0;
		double valor_total_transferencias_retiradas = 0;
		double valor_total_transferencias_recebidas = 0;
		double valor_total_pagamentos_restantes = 0;

	
			lista_transferencias_contratuais_remetente = new ArrayList<>();
		

		
			lista_transferencias_contratuais_destinatario = new ArrayList<>();
		

		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
		lista_pagamentos_contratuais = gerenciar.getPagamentosContratuais(contrato_local.getId());

		GerenciarBancoTransferencias gerenciar_transferencias = new GerenciarBancoTransferencias();
		lista_transferencias_contratuais_remetente = gerenciar_transferencias
				.getTransferenciasRemetente(contrato_local.getId());
		lista_transferencias_contratuais_destinatario = gerenciar_transferencias
				.getTransferenciaDestinatario(contrato_local.getId());

		/*
		 * 
		 * modelo_pagamentos_contratuais.addColumn("Id Pagamento");
		 * modelo_pagamentos_contratuais.addColumn("Descrição");
		 * modelo_pagamentos_contratuais.addColumn("Data");
		 * modelo_pagamentos_contratuais.addColumn("Valor");
		 * modelo_pagamentos_contratuais.addColumn("Depositante");
		 * modelo_pagamentos_contratuais.addColumn("Conta Depositante");
		 * modelo_pagamentos_contratuais.addColumn("Favorecido");
		 * modelo_pagamentos_contratuais.addColumn("Conta Favorecido");
		 */
		for (CadastroContrato.CadastroPagamentoContratual pagamento : lista_pagamentos_contratuais) {

			// pegar data do pagmento
			String data = pagamento.getData_pagamento();
			double valor_pagamento = pagamento.getValor_pagamento();

			// pegar depositante
			GerenciarBancoClientes gerenciar_clientes = new GerenciarBancoClientes();
			CadastroCliente depositante = gerenciar_clientes.getCliente(pagamento.getId_depositante());
			String nome_depositante = "";
			if (depositante.getTipo_pessoa() == 0) {
				// pessoa fisica
				nome_depositante = depositante.getNome_empresarial();
			} else {
				nome_depositante = depositante.getNome_fantaia();
			}

			// pegar conta do depositante
			ContaBancaria conta_depositante = gerenciar_clientes.getConta(pagamento.getId_conta_depositante());

			// pegar favorecido
			CadastroCliente favorecido = gerenciar_clientes.getCliente(pagamento.getId_favorecido());
			String nome_favorecido = "";
			if (favorecido.getTipo_pessoa() == 0) {
				// pessoa fisica
				nome_favorecido = favorecido.getNome_empresarial();
			} else {
				nome_favorecido = favorecido.getNome_fantaia();
			}

			// pegar conta do favorecido
			ContaBancaria conta_favorecido = gerenciar_clientes.getConta(pagamento.getId_conta_favorecido());

			Locale ptBr = new Locale("pt", "BR");
			String valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor_pagamento);

		
			if(pagamento.getTipo() == 1)
			valor_total_pagamentos_efetuados += valor_pagamento;

		}

		for (CadastroContrato.CadastroTransferenciaPagamentoContratual transferencia : lista_transferencias_contratuais_remetente) {

			GerenciarBancoContratos gerenciar_contratos = new GerenciarBancoContratos();


			// pegar o pagamento

			// pegar data do pagmento
			String data = transferencia.getData();
			double valor_pagamento =Double.parseDouble(transferencia.getValor());

			// pegar o destinatario
			CadastroContrato destinatario = gerenciar_contratos
					.getContrato(transferencia.getId_contrato_destinatario());

			Locale ptBr = new Locale("pt", "BR");
			String valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor_pagamento);

		

			valor_total_transferencias_retiradas += valor_pagamento;
			// valor_total_pagamentos_efetuados -= valor_pagamento;

		}

		for (CadastroContrato.CadastroTransferenciaPagamentoContratual transferencia : lista_transferencias_contratuais_destinatario) {
			GerenciarBancoContratos gerenciar_contratos = new GerenciarBancoContratos();


			// pegar o pagamento

			// pegar data do pagmento
			String data = transferencia.getData();
			double valor_pagamento = Double.parseDouble(transferencia.getValor());

			// pegar o destinatario
			CadastroContrato remetente = gerenciar_contratos.getContrato(transferencia.getId_contrato_remetente());

			Locale ptBr = new Locale("pt", "BR");
			String valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor_pagamento);

			
			valor_total_transferencias_recebidas += valor_pagamento;
			// valor_total_pagamentos_efetuados += valor_pagamento;

		}

		Locale ptBr = new Locale("pt", "BR");
		String valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos);

		lblTotalPagamentos.setText(valor);

		valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos_efetuados);

		lblTotalPagamentosEfetuados.setText(valor);

		valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_transferencias_retiradas);

		lblTotalTransferenciasRetiradas.setText(valor);

		valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_transferencias_recebidas);

		lblTotalTransferenciasRecebidas.setText(valor);

		valor_total_pagamentos_restantes = valor_total_pagamentos - valor_total_pagamentos_efetuados
				+ valor_total_transferencias_retiradas - valor_total_transferencias_recebidas;
		valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos_restantes);

		lblTotalPagamentosRestantes.setText(valor);

	

	}
	
	public void pesquisar_carregamentos() {

		ArrayList<CadastroContrato.Carregamento> lista_carregamentos = new ArrayList<>(); 
		ArrayList<CadastroNFe> notas_fiscais_carregamentos = new ArrayList<>();
		double peso_total_cargas_nfe = 0.0;
		double peso_total_cargas = 0.0;

		

		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
		lista_carregamentos = gerenciar.getCarregamentos(contrato_local.getId());

		/*
		 * modelo_carregamentos.addColumn("Id Carregamento");
		 * modelo_carregamentos.addColumn("Data");
		 * modelo.addColumn("Contrato Destinado"); modelo.addColumn("Cliente");
		 * modelo.addColumn("Transportador"); modelo.addColumn("Veiculo");
		 * modelo.addColumn("Produto"); modelo.addColumn("Peso Real Carga");
		 * modelo.addColumn("Nota Fiscal");
		 * 
		 */
		for (CadastroContrato.Carregamento carregamento : lista_carregamentos) {

			// pegar dados do contrato
			CadastroContrato contrato_destinatario = gerenciar.getContrato(carregamento.getId_contrato());

			// pegar cliente
			GerenciarBancoClientes gerenciar_clientes = new GerenciarBancoClientes();
			CadastroCliente cliente_carregamento = gerenciar_clientes.getCliente(carregamento.getId_cliente());
			String nome_cliente;
			if (cliente_carregamento.getTipo_pessoa() == 0) {
				// pessoa fisica
				nome_cliente = cliente_carregamento.getNome_empresarial();
			} else {
				nome_cliente = cliente_carregamento.getNome_fantaia();
			}

			// pegar vendedor
			CadastroCliente vendedor_carregamento = gerenciar_clientes.getCliente(carregamento.getId_vendedor());
			String nome_vendedor;
			if (vendedor_carregamento.getTipo_pessoa() == 0) {
				// pessoa fisica
				nome_vendedor = vendedor_carregamento.getNome_empresarial();
			} else {
				nome_vendedor = vendedor_carregamento.getNome_fantaia();
			}

			// pegar transportador e veiculo
			CadastroCliente transportador_carregamento = gerenciar_clientes
					.getCliente(carregamento.getId_transportador());
			CadastroCliente.Veiculo veiculo_carregamento = null;
			for (CadastroCliente.Veiculo veiculo : transportador_carregamento.getVeiculos()) {
				if (veiculo.getId_veiculo() == carregamento.getId_veiculo()) {
					veiculo_carregamento = veiculo;
					break;
				}
			}

			// pegar o produto
			GerenciarBancoProdutos gerenciar_produtos = new GerenciarBancoProdutos();
			CadastroProduto produto_carregamento = gerenciar_produtos.getProduto(carregamento.getId_produto());

			NumberFormat z = NumberFormat.getNumberInstance();
			// pegar a nota
			ManipularNotasFiscais manipular = new ManipularNotasFiscais("");
			CadastroNFe nota = manipular.getNotaFiscal(carregamento.getCodigo_nota_fiscal());

			// definir peso carregamento
			double peso_carregado = carregamento.getPeso_real_carga();
			// definir peso da nota
			double peso_nota = Double.parseDouble(nota.getQuantidade());
			// definir peso restante para nota
			double peso_nota_restante = peso_carregado - peso_nota;

			

			notas_fiscais_carregamentos.add(nota);

			peso_total_cargas += carregamento.getPeso_real_carga();

		}

		// faz a soma dos pesos das notas fiscais
		for (CadastroNFe nfe : notas_fiscais_carregamentos) {

			if (nfe != null) {
				peso_total_cargas_nfe += Double.parseDouble(nfe.getQuantidade().replace(".", "").replace(",", "."));
			}

		}

		double peso_total_kg = 0, peso_total_sacos = 0, peso_carregado_kg = 0, peso_carregado_sacos = 0,
				peso_restante_kg = 0, peso_restante_sacos = 0;

		NumberFormat z = NumberFormat.getNumberInstance();

		if (contrato_local.getMedida().equals("KG")) {
			peso_total_kg = contrato_local.getQuantidade();
			peso_total_sacos = peso_total_kg / 60;

			peso_carregado_kg = peso_total_cargas;
			peso_carregado_sacos = peso_total_cargas / 60;

			peso_restante_kg = peso_total_kg - peso_carregado_kg;
			peso_restante_sacos = peso_total_sacos - peso_carregado_sacos;

		} else if (contrato_local.getMedida().equalsIgnoreCase("Sacos")) {
			peso_total_sacos = contrato_local.getQuantidade();
			peso_total_kg = peso_total_sacos * 60;

			peso_carregado_sacos = peso_total_cargas / 60;
			peso_carregado_kg = peso_total_cargas;

			peso_restante_sacos = peso_total_sacos - peso_carregado_sacos;
			peso_restante_kg = peso_total_kg - peso_carregado_kg;

		}

		lblPesoTotal.setText(z.format(peso_total_kg) + " Kg " + " | " + z.format(peso_total_sacos) + " Sacos");

		// peso total das cargas
		lblPesoTotalRealCargas
				.setText(z.format(peso_carregado_kg) + " Kg" + " | " + z.format(peso_carregado_sacos) + " Sacos");
		// peso restante
		lblPesoTotalRealRestante
				.setText(z.format(peso_restante_kg) + " Kg" + " | " + z.format(peso_restante_sacos) + " Sacos");

		double peso_total_nf_kg = peso_total_cargas_nfe;
		double peso_total_nf_sacos = peso_total_nf_kg / 60;

		double peso_total_nf_kg_restante = peso_carregado_kg - peso_total_nf_kg;
		double peso_total_nf_sacos_restante = peso_carregado_sacos - peso_total_nf_sacos;

		lblPesoTotalNotasFiscais
				.setText(z.format(peso_total_nf_kg) + " Kg" + " | " + z.format(peso_total_nf_sacos) + " Sacos");

		lblPesoTotalNotasFiscaisRestante.setText(z.format(peso_total_nf_kg_restante) + " Kg" + " | "
				+ z.format(peso_total_nf_sacos_restante) + " Sacos");

		

	}
	
	public void setTelaPai(JDialog _tela_pai) {
		this.telaPai = _tela_pai;
	}
}
