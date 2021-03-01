package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.border.CompoundBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

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
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import graficos.JPanelGraficoPadrao;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.MatteBorder;



public class TelaVisaoGeralContrato extends JDialog {

	private final JPanel painelPai = new JPanel();
	private final JPanel painelPrincipal = new JPanel();
	private final JPanel painelGraficos = new JPanel();

	private final JPanel painelGanhosPotenciais;

    private JLabel lblTotalContratosConcluidos, lblTotalContratos, lblTotalContratosAbertos;
    private TelaVisaoGeralContrato isto;
    private JDialog telaPai;
    private CadastroContrato contrato_local;
	private JTabbedPane painelAbas ;
	private JPanelGraficoPadrao painelGraficoCarregamento ;
	private JPanelGraficoPadrao painelGraficoPagamentos, painelGraficoNFs;
	 
	
	   private JLabel lblTotalPagamentos,lblTotalPagamentosEfetuados, lblTotalTransferenciasRetiradas,lblTotalTransferenciasRecebidas,lblTotalPagamentosRestantes;
		private JLabel lblPesoTotal, lblPesoTotalRealCargas, lblPesoTotalRealRestante,lblPesoTotalNotasFiscais, lblPesoTotalNotasFiscaisRestante;

		private ArrayList<CadastroContrato>  sub_contratos;
	public TelaVisaoGeralContrato(CadastroContrato contrato, JFrame janela_pai) {
		setModal(true);

		 isto = this;
		 contrato_local = contrato;
		setResizable(true);
		setTitle("E-Contract - Tela Padrãos");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1060, 614);
		painelPai.setBackground(new Color(255, 255, 255));
		painelPai.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPai);
		painelPai.setLayout(null);
	
		painelAbas = new JTabbedPane();
		painelAbas.setBounds(0, 0, 1087, 581);
		painelAbas.setBackground(new Color(255, 255, 255));
		painelAbas.setBorder(new EmptyBorder(5, 5, 5, 5));
		painelPai.add(painelAbas);
		
		painelAbas.addTab("Informações Gerais", painelPrincipal);
		painelGraficos.setBackground(new Color(0, 0, 0));
		painelAbas.addTab("Dashboard", painelGraficos);
		painelGraficos.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(51, 153, 51));
		panel_1.setBounds(6, 6, 341, 497);
		painelGraficos.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_14_2 = new JLabel("Restante:");
		lblNewLabel_14_2.setBounds(84, 196, 68, 14);
		panel_1.add(lblNewLabel_14_2);
		
		JLabel lblNewLabel_14_1_1_1 = new JLabel("Transferencias:(+)");
		lblNewLabel_14_1_1_1.setBounds(41, 152, 101, 14);
		panel_1.add(lblNewLabel_14_1_1_1);
		
		JLabel lblNewLabel_14_1_1 = new JLabel("Transferencias:(-)");
		lblNewLabel_14_1_1.setBounds(41, 118, 101, 14);
		panel_1.add(lblNewLabel_14_1_1);
		
		JLabel lblNewLabel_14_1 = new JLabel("Efetuados:");
		lblNewLabel_14_1.setBounds(74, 86, 68, 14);
		panel_1.add(lblNewLabel_14_1);
		
		JLabel lblNewLabel_14 = new JLabel("Total:");
		lblNewLabel_14.setBounds(97, 55, 45, 14);
		panel_1.add(lblNewLabel_14);
		
		JLabel lblNewLabel_14_3 = new JLabel("Pagamentos");
		lblNewLabel_14_3.setBounds(137, 20, 71, 16);
		panel_1.add(lblNewLabel_14_3);
		
		 lblTotalPagamentos = new JLabel("R$ 0,00");
		 lblTotalPagamentos.setBounds(148, 46, 143, 23);
		 panel_1.add(lblTotalPagamentos);
		 lblTotalPagamentos.setBorder(new LineBorder(new Color(0, 0, 0)));
		 
		  lblTotalPagamentosEfetuados = new JLabel("R$ 0,00");
		  lblTotalPagamentosEfetuados.setBounds(148, 80, 143, 23);
		  panel_1.add(lblTotalPagamentosEfetuados);
		  lblTotalPagamentosEfetuados.setBorder(new LineBorder(new Color(0, 0, 0)));
		  
		   lblTotalTransferenciasRetiradas = new JLabel("R$ 0,00");
		   lblTotalTransferenciasRetiradas.setBounds(148, 112, 143, 23);
		   panel_1.add(lblTotalTransferenciasRetiradas);
		   lblTotalTransferenciasRetiradas.setBorder(new LineBorder(new Color(0, 0, 0)));
		   
		    lblTotalTransferenciasRecebidas = new JLabel("R$ 0,00");
		    lblTotalTransferenciasRecebidas.setBounds(148, 146, 143, 23);
		    panel_1.add(lblTotalTransferenciasRecebidas);
		    lblTotalTransferenciasRecebidas.setBorder(new LineBorder(new Color(0, 0, 0)));
		    
		     lblTotalPagamentosRestantes = new JLabel("R$ 0,00");
		     lblTotalPagamentosRestantes.setBounds(148, 187, 143, 23);
		     panel_1.add(lblTotalPagamentosRestantes);
		     lblTotalPagamentosRestantes.setBorder(new LineBorder(new Color(0, 0, 0)));
		     
		      painelGraficoPagamentos = new JPanelGraficoPadrao(0, 0, "Pago: ", "a Pagar: ");
		      painelGraficoPagamentos.setBounds(21, 222, 300, 250);
		      panel_1.add(painelGraficoPagamentos);
		      painelGraficoPagamentos.setLayout(null);
		      
		      JLabel lblNewLabel = new JLabel("");
		      lblNewLabel.setBounds(6, 6, 64, 64);
		      panel_1.add(lblNewLabel);
		      lblNewLabel.setIcon(new ImageIcon(TelaVisaoGeralContrato.class.getResource("/imagens/icone_dinheiro.png")));
		      
		      JPanel panel_2 = new JPanel();
		      panel_2.setBounds(349, 6, 341, 497);
		      painelGraficos.add(panel_2);
		      panel_2.setBackground(new Color(102, 102, 102));
		      panel_2.setLayout(null);
		      
		      JLabel lblNewLabel_3 = new JLabel("Carregamento de Saída");
		      lblNewLabel_3.setBounds(106, 56, 132, 16);
		      panel_2.add(lblNewLabel_3);
		      
		      JLabel lblNewLabel_12 = new JLabel("Total:");
		      lblNewLabel_12.setBounds(61, 91, 46, 14);
		      panel_2.add(lblNewLabel_12);
		      
		      JLabel lblNewLabel_13 = new JLabel("Total Carregado:");
		      lblNewLabel_13.setBounds(18, 126, 101, 14);
		      panel_2.add(lblNewLabel_13);
		      
		      JLabel lblNewLabel_13_1 = new JLabel("Restante:");
		      lblNewLabel_13_1.setBounds(54, 162, 65, 14);
		      panel_2.add(lblNewLabel_13_1);
		      
		       lblPesoTotalRealRestante = new JLabel("0 Kg | 0 Sacos");
		       lblPesoTotalRealRestante.setBounds(119, 156, 193, 23);
		       panel_2.add(lblPesoTotalRealRestante);
		       lblPesoTotalRealRestante.setFont(new Font("Tahoma", Font.BOLD, 11));
		       lblPesoTotalRealRestante.setBorder(new LineBorder(new Color(0, 0, 0)));
		       
		        lblPesoTotalRealCargas = new JLabel("0 Kg | 0 Sacos");
		        lblPesoTotalRealCargas.setBounds(119, 120, 193, 23);
		        panel_2.add(lblPesoTotalRealCargas);
		        lblPesoTotalRealCargas.setFont(new Font("Tahoma", Font.BOLD, 11));
		        lblPesoTotalRealCargas.setBorder(new LineBorder(new Color(0, 0, 0)));
		        
		         lblPesoTotal = new JLabel("0 Kg  | 0 Sacos");
		         lblPesoTotal.setBounds(119, 85, 193, 23);
		         panel_2.add(lblPesoTotal);
		         lblPesoTotal.setFont(new Font("Tahoma", Font.BOLD, 11));
		         lblPesoTotal.setBorder(new LineBorder(new Color(0, 0, 0)));
		         
		          painelGraficoCarregamento = new JPanelGraficoPadrao(0, 0, "Carregado:", "a Carregar:");
		          painelGraficoCarregamento.setBounds(21, 222, 300, 250);
		          panel_2.add(painelGraficoCarregamento);
		          painelGraficoCarregamento.setLayout(null);
		          
		          JLabel lblNewLabel_1 = new JLabel("");
		          lblNewLabel_1.setBounds(6, 6, 64, 64);
		          panel_2.add(lblNewLabel_1);
		          lblNewLabel_1.setIcon(new ImageIcon(TelaVisaoGeralContrato.class.getResource("/imagens/icone_caminhao.png")));
		          
		          JPanel panel_3 = new JPanel();
		          panel_3.setBounds(694, 6, 341, 497);
		          painelGraficos.add(panel_3);
		          panel_3.setBackground(new Color(0, 102, 255));
		          panel_3.setLayout(null);
		          
		          JLabel lblNewLabel_13_2 = new JLabel("Total Carregado:");
		          lblNewLabel_13_2.setBounds(26, 108, 92, 16);
		          panel_3.add(lblNewLabel_13_2);
		          
		           lblPesoTotalNotasFiscais = new JLabel("0 Kg | 0 Sacos");
		           lblPesoTotalNotasFiscais.setBounds(121, 106, 185, 23);
		           panel_3.add(lblPesoTotalNotasFiscais);
		           lblPesoTotalNotasFiscais.setFont(new Font("Tahoma", Font.BOLD, 11));
		           lblPesoTotalNotasFiscais.setBorder(new LineBorder(new Color(0, 0, 0)));
		           
		           JLabel lblNewLabel_3_1 = new JLabel("Notas Fiscais:");
		           lblNewLabel_3_1.setBounds(128, 77, 79, 16);
		           panel_3.add(lblNewLabel_3_1);
		           
		            lblPesoTotalNotasFiscaisRestante = new JLabel("0 Kg | 0 Sacos");
		            lblPesoTotalNotasFiscaisRestante.setBounds(121, 140, 185, 23);
		            panel_3.add(lblPesoTotalNotasFiscaisRestante);
		            lblPesoTotalNotasFiscaisRestante.setFont(new Font("Tahoma", Font.BOLD, 11));
		            lblPesoTotalNotasFiscaisRestante.setBorder(new LineBorder(new Color(0, 0, 0)));
		            
		            JLabel lblNewLabel_13_2_1 = new JLabel("Restante:");
		            lblNewLabel_13_2_1.setBounds(65, 141, 53, 16);
		            panel_3.add(lblNewLabel_13_2_1);
		            
		             painelGraficoNFs = new JPanelGraficoPadrao(0, 0, "Total NF:", "Falta Tirar:");
		             painelGraficoNFs.setBounds(21, 222, 300, 250);
		             panel_3.add(painelGraficoNFs);
		             painelGraficoNFs.setLayout(null);
		             
		             JLabel lblNewLabel_2 = new JLabel("New label");
		             lblNewLabel_2.setBounds(6, 6, 64, 64);
		             panel_3.add(lblNewLabel_2);
		             lblNewLabel_2.setIcon(new ImageIcon(TelaVisaoGeralContrato.class.getResource("/imagens/icone_nf.png")));
		
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		painelPrincipal.setLayout(null);
		
		JLabel lblTipoDoContrato = new JLabel("Contrato Original");
		lblTipoDoContrato.setBounds(10, 21, 93, 16);
		painelPrincipal.add(lblTipoDoContrato);
		
		JLabel lblSubcontratos = new JLabel("Sub-Contratos");
		lblSubcontratos.setBounds(10, 126, 93, 16);
		painelPrincipal.add(lblSubcontratos);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(10, 153, 1051, 134);
		painelPrincipal.add(panel);
		panel.setLayout(null);
		
		
		 DefaultListModel<CadastroContrato>   listModelGlobal = new DefaultListModel<CadastroContrato>();
		 
		   GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
			 sub_contratos = gerenciar.getSubContratos(contrato.getId());
		 
		 for(CadastroContrato sub : sub_contratos) {
				
			 
		     listModelGlobal.addElement(sub);
		}
		 
		 RenderizadorSubContrato render = new RenderizadorSubContrato();

		 
	
			JList<CadastroContrato> lista_sub_contratos = new JList<>();
			lista_sub_contratos.setBorder(null);
	
			lista_sub_contratos.setBackground(new Color(255, 255, 255));
			
			lista_sub_contratos.setCellRenderer(render);
			 lista_sub_contratos.setModel(listModelGlobal);
		
		JScrollPane scrollPane = new JScrollPane(lista_sub_contratos);
		scrollPane.setBorder(new MatteBorder(2, 2, 0, 3, (Color) new Color(0, 0, 153)));
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(6, 6, 1016, 122);
		scrollPane.setAutoscrolls(false);

		panel.add(scrollPane);
		
		JLabel tPContratoOriginal = new JLabel();
		tPContratoOriginal.setFont(new Font("Arial", Font.PLAIN, 14));
		tPContratoOriginal.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		tPContratoOriginal.setBounds(20, 49, 1012, 65);

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
				
				String comissao = "";
				if(contrato.getComissao() == 1)
					comissao = NumberFormat.getCurrencyInstance(ptBr).format(contrato.getValor_comissao());
				else
					comissao = "NÃO";
				
				if(vendedores[1] == null) {
					tPContratoOriginal.setText("<html> " + contrato.getCodigo() + " <b>" + quantidade +"</b> sacos de " + produto +" da safra <b>" + safra + "</b> no valor de <b>" + valor_por_saco + "</b> por saca,\r\n   "
						+ "<br> do Vendedor <b>" + nome_vendedor1 + "</b> ao Comprador <b>" + nome_comprador + "</b>\r\n Valor Total: <b>" + valor_a_pagar + "</b>  Comissão: <b>" + comissao +"</b></html>");
				}else {
					tPContratoOriginal.setText("<html> " + contrato.getCodigo() + " <b>" + quantidade +"</b> sacos de " + produto +" da safra <b>" + safra + "</b> no valor de <b>" + valor_por_saco + "</b> por saca,\r\n   "
							+ "<br> do Vendedor <b>" + nome_vendedor1 + "</b> com o Vendedor <b>" + nome_vendedor2 + "</b> ao Comprador <b>" + nome_comprador + "</b>\r\n Valor Total: <b>" + valor_a_pagar  + "</b>  Comissão: <b>" + comissao +"</b></html>");
					
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
		
		 painelGanhosPotenciais = new JPanel();
		painelGanhosPotenciais.setBackground(Color.WHITE);
		painelGanhosPotenciais.setBounds(407, 299, 351, 164);
		painelPrincipal.add(painelGanhosPotenciais);
		painelGanhosPotenciais.setLayout(null);
		
		
	
		
		//		textArea.setText("4446.6.5.8888  - 8.000 sacos de sorgo da safra 2020/2021 no valor de R$ 30,00 por saca,\r\n                                                                                                                        do Vendedor LD Armazens Gerais ao Comprador Marcos Alexandre andrea de carvalho\r\n                                                                                                                         Valor Total: R$ 240.000,00");

		setarPainelGanhosPotenciais();
		pesquisar_pagamentos();
		pesquisar_carregamentos();
		
		criarGrafico();
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
			double valor_pagamento = Double.parseDouble(transferencia.getValor());

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

			CadastroContrato.CadastroPagamentoContratual pag_transferencia = null;


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

		int n1 = (int) valor_total_pagamentos;
		int n2 = n1 - ((int) valor_total_pagamentos_restantes);
		atualizarGraficoPagamentos(n1, n2);

		

	}	

	
	public void atualizarGraficoPagamentos(int num_total, int num_ja_pago) {

		new Thread() {

			@Override
			public void run() {

				int i = 0;
				while (i <= num_ja_pago) {

					// System.out.printf("Disponivel e %d\n ", disponivel);
					// System.out.printf("Usado e %d\n", usado);

					painelGraficoPagamentos.setDados(num_total, i);
					painelGraficoPagamentos.repaint();

					i++;
				}

			}
		}.start();

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
			Number number = null;
			try {
				number = z.parse(nota.getQuantidade());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			double peso_nota = number.doubleValue();
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

		int n1 = (int) peso_total_sacos;
		int n2 = ((int) peso_carregado_sacos);

		atualizarGraficoContratos(n1, n2);

		int n3 = (int) peso_total_nf_sacos;
		int n4 = n3 - ((int) peso_total_nf_sacos_restante);

		atualizarGraficoNFs(n3, n4);

	}
	
	public void atualizarGraficoContratos(int num_total, int num_carregado) {

		new Thread() {

			@Override
			public void run() {

				int i = 0;
				while (i <= num_carregado) {

					// System.out.printf("Disponivel e %d\n ", disponivel);
					// System.out.printf("Usado e %d\n", usado);

					painelGraficoCarregamento.setDados(num_total, i);
					painelGraficoCarregamento.repaint();

					i++;
				}

			}
		}.start();

	}

	public void atualizarGraficoNFs(int num_total, int num_tirado) {

		new Thread() {

			@Override
			public void run() {

				int i = 0;
				while (i <= num_tirado) {

					// System.out.printf("Disponivel e %d\n ", disponivel);
					// System.out.printf("Usado e %d\n", usado);

					painelGraficoNFs.setDados(num_total, i);
					painelGraficoNFs.repaint();

					i++;
				}

			}
		}.start();

	}
	
	public void setarPainelGanhosPotenciais() {

		ArrayList<CadastroContrato> lista_sub_contratos = new GerenciarBancoContratos().getSubContratos(contrato_local.getId());
		
		double valor_total_contrato_original = Double.parseDouble(contrato_local.getValor_a_pagar().toPlainString());
		double valor_total_sub_contratos = 0;

		for (CadastroContrato sub : lista_sub_contratos) {

			double valor_local = Double.parseDouble(sub.getValor_a_pagar().toPlainString());
			valor_total_sub_contratos += valor_local;
		}
		double valor_total_diferenca_contratos = 0;
		if (valor_total_sub_contratos == 0) {
			valor_total_diferenca_contratos = 0;

		} else {
			valor_total_diferenca_contratos = valor_total_contrato_original - valor_total_sub_contratos;

		}

		double valor_total_comissoes = 0;

		if (contrato_local.getComissao() == 1) {
			double valor_local = Double.parseDouble(contrato_local.getValor_comissao().toPlainString());
			valor_total_comissoes += valor_local;
		}

		for (CadastroContrato sub : lista_sub_contratos) {

			if (sub.getComissao() == 1) {
				double valor_local = Double.parseDouble(sub.getValor_comissao().toPlainString());
				valor_total_comissoes += valor_local;
			}

		}

		double valor_total_ganhos_potenciais = valor_total_diferenca_contratos + valor_total_comissoes;

		Locale ptBr = new Locale("pt", "BR");

		JPanel painelInfoGanhos = new JPanel();
		painelInfoGanhos.setBackground(Color.WHITE);
		painelInfoGanhos.setForeground(Color.WHITE);
		painelInfoGanhos.setBounds(0, 0, 316, 165);
		painelGanhosPotenciais.add(painelInfoGanhos);
		painelInfoGanhos.setLayout(new MigLayout("", "[][]", "[][][][][][]"));

		JLabel lblNewLabel_20 = new JLabel("Valor Total Contrato Original:");
		lblNewLabel_20.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelInfoGanhos.add(lblNewLabel_20, "cell 0 0");

		JLabel lblValorTotalContrato = new JLabel("R$ 1.000.000.000.00");
		lblValorTotalContrato.setFont(new Font("Tahoma", Font.PLAIN, 14));
		String valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_contrato_original);
		lblValorTotalContrato.setText(valorString);
		painelInfoGanhos.add(lblValorTotalContrato, "cell 1 0");

		JLabel lblNewLabel_22 = new JLabel("Valor Total Sub-Contratos:");
		lblNewLabel_22.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelInfoGanhos.add(lblNewLabel_22, "cell 0 1");

		JLabel lblValorTotalSubContratos = new JLabel("R$ 1.000.000.000,00");
		lblValorTotalSubContratos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelInfoGanhos.add(lblValorTotalSubContratos, "cell 1 1");
		valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_sub_contratos);
		lblValorTotalSubContratos.setText(valorString);

		JLabel lblNewLabel_21 = new JLabel("Diferença entre Contratos:");
		lblNewLabel_21.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelInfoGanhos.add(lblNewLabel_21, "cell 0 2,alignx left");

		JLabel lblValorTotalDiferencaContratos = new JLabel("R$ 1.000.000.000,00");
		lblValorTotalDiferencaContratos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelInfoGanhos.add(lblValorTotalDiferencaContratos, "cell 1 2");
		valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_diferenca_contratos);
		lblValorTotalDiferencaContratos.setText(valorString);

		JLabel lblNewLabel_23 = new JLabel("Valor Total das Comissões:");
		lblNewLabel_23.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelInfoGanhos.add(lblNewLabel_23, "cell 0 3");

		JLabel lblValorTotalComissoes = new JLabel("R$ 1.000.000.000,00");
		lblValorTotalComissoes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelInfoGanhos.add(lblValorTotalComissoes, "cell 1 3,alignx left");
		valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_comissoes);
		lblValorTotalComissoes.setText(valorString);

		JLabel lblNewLabel_24 = new JLabel("Ganhos em potenciais:");
		lblNewLabel_24.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelInfoGanhos.add(lblNewLabel_24, "cell 0 5");

		JLabel lblValorTotalGanhosPotenciais = new JLabel("R$ 1.000.000.000,00");
		lblValorTotalGanhosPotenciais.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelInfoGanhos.add(lblValorTotalGanhosPotenciais, "cell 1 5");
		valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_ganhos_potenciais);
		lblValorTotalGanhosPotenciais.setText(valorString);
	}
	
	public void criarGrafico() {
		
		JPanel painelGraficoPizza1 = new JPanel();
		painelGraficoPizza1.setBorder(null);
		painelGraficoPizza1.setBackground(Color.WHITE);
		painelGraficoPizza1.setBounds(20, 313, 328, 222);
		painelPrincipal.add(painelGraficoPizza1);
		painelGraficoPizza1.setLayout(null);

		ArrayList<CadastroContrato> lista_sub_contratos = new GerenciarBancoContratos().getSubContratos(contrato_local.getId());


		DefaultPieDataset pizza = new DefaultPieDataset();
		

		
		for (CadastroContrato sub : lista_sub_contratos) {

			double quantidade_sacos = 0;
			double quantidade_quilogramas = 0;

			if (sub.getMedida().equalsIgnoreCase("Sacos")) {
				quantidade_sacos = sub.getQuantidade();
				quantidade_quilogramas = sub.getQuantidade() * 60;
			} else if (sub.getMedida().equalsIgnoreCase("KG")) {
				quantidade_quilogramas = sub.getQuantidade();
				quantidade_sacos = sub.getQuantidade() / 60;

			}
			pizza.setValue(sub.getCodigo(), quantidade_sacos);
		}

		JFreeChart grafico = ChartFactory.createPieChart("", pizza, true, true, false);
		grafico.removeLegend();
		grafico.getPlot().setBackgroundPaint( Color.WHITE );
		grafico.getPlot().setOutlinePaint(null);
		
		ChartPanel painel = new ChartPanel(grafico);
		painel.setBackground(Color.white);
		painel.setBounds(0, 0, 328, 222);



		painelGraficoPizza1.add(painel);
	}
	
	public void setTelaPai(JDialog _tela_pai) {
		this.telaPai = _tela_pai;
	}
}
