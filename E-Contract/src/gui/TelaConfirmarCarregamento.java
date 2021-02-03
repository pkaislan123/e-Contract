package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;

import cadastros.CadastroCliente;
import cadastros.CadastroContrato;
import cadastros.CadastroNFe;
import cadastros.CadastroProduto;
import classesExtras.Carregamento;
import conexaoBanco.GerenciarBancoContratos;
import outros.TratarDados;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import javax.swing.JTextArea;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JScrollPane;

public class TelaConfirmarCarregamento extends JDialog {

	private JTabbedPane abas = new JTabbedPane();
	private JPanel painelConfirmar = new JPanel();
	private JPanel painelSelecionar = new JPanel();
	private JFrame telaPaiJFrame;
	private TelaConfirmarCarregamento isto;
	private JDialog telaPai;
	private JTextField entDataCarregamento;
	private JTextField entPesoRealCarga;
	private JComboBox cBContrato, cBCliente, cBProduto, cBNotaFiscal, cbVeiculo, cBTransportador, cBVendedor;
	private CadastroContrato contrato_local;

	private CadastroCliente transportador_carregamento;
	private CadastroCliente cliente_carregamento;
	private CadastroProduto produto_carregamento;
	private CadastroNFe nota_fiscal_carregamento;
	private CadastroContrato contrato_carregamento;
	private CadastroCliente.Veiculo veiculo_carregamento;
	private CadastroCliente vendedor;
	private JLabel  lblPesoRealCarregamento, lblProdutoCarregamento, lblDataCarregamento,
			lblClienteCarregamento, lblContratoCarregamento, lblTransportadorCarregamento, lblVeiculoCarregamento, lblCaminhoNFa;

	private JTextArea lblNotaFiscalCarregamento;
	public TelaConfirmarCarregamento(CadastroContrato _contrato_local, JFrame janela_pai) {
		//setAlwaysOnTop(true);

		setModal(true);

		isto = this;
		this.contrato_local = _contrato_local;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("E-Contract - Carregamento");

		abas.setBackground(new Color(255, 255, 255));
		abas.setBorder(new EmptyBorder(5, 5, 5, 5));
		abas = new JTabbedPane();
		// contentPanel.setBackground(new Color(255, 255, 255));
		// contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		// setContentPane(contentPanel);
		// contentPanel.setLayout(null);

		painelSelecionar.setBackground(new Color(255, 255, 255));

		abas.addTab("Selecionar", painelSelecionar);
		painelSelecionar.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBounds(21, 11, 492, 383);
		painelSelecionar.add(panel_1);

		JLabel lblNewLabel_3 = new JLabel("Data:");
		lblNewLabel_3.setBounds(79, 11, 46, 23);
		panel_1.add(lblNewLabel_3);

		entDataCarregamento = new JTextField();
		entDataCarregamento.setEnabled(false);

		 String strLocalDate2   = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		 entDataCarregamento.setText(strLocalDate2);
		
		entDataCarregamento.setEditable(false);
		entDataCarregamento.setColumns(10);
		entDataCarregamento.setBounds(135, 8, 116, 30);
		panel_1.add(entDataCarregamento);

		JCheckBox chkBoxDataHoje = new JCheckBox("Data Atual");
		chkBoxDataHoje.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
	        	  
				if(chkBoxDataHoje.isSelected()) {
					chkBoxDataHoje.setSelected(true);
					entDataCarregamento.setEditable(false);
					 String strLocalDate2   = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
					 entDataCarregamento.setText(strLocalDate2);
					
				}else {
					chkBoxDataHoje.setSelected(false);
					entDataCarregamento.setEnabled(true);
					entDataCarregamento.setEditable(true);
				}
			}
		});
		chkBoxDataHoje.setSelected(true);
		chkBoxDataHoje.setBounds(257, 7, 88, 23);
		panel_1.add(chkBoxDataHoje);

		JLabel lblNewLabel_5 = new JLabel("Transportador:");
		lblNewLabel_5.setBounds(56, 172, 88, 14);
		panel_1.add(lblNewLabel_5);

		cBTransportador = new JComboBox();
		cBTransportador.setBounds(135, 166, 248, 30);
		panel_1.add(cBTransportador);

		JButton btnSelecionarTransportador = new JButton("Selecionar");
		btnSelecionarTransportador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaTransportadores selecionar_transportador = new TelaTransportadores(1,isto);
				selecionar_transportador.setTelaPai(isto);
				selecionar_transportador.setVisible(true);
			}
		});
		btnSelecionarTransportador.setBounds(393, 166, 89, 30);
		panel_1.add(btnSelecionarTransportador);

		cbVeiculo = new JComboBox();
		cbVeiculo.setEditable(false);
		cbVeiculo.setBounds(136, 198, 186, 30);
		panel_1.add(cbVeiculo);

		JLabel lblNewLabel_5_1 = new JLabel("Veiculo:");
		lblNewLabel_5_1.setBounds(89, 197, 56, 14);
		panel_1.add(lblNewLabel_5_1);

		JLabel lblNewLabel_6 = new JLabel("Nota Fiscal:");
		lblNewLabel_6.setBounds(59, 323, 65, 26);
		panel_1.add(lblNewLabel_6);

		cBNotaFiscal = new JComboBox();
		cBNotaFiscal.setBounds(134, 321, 209, 30);
		panel_1.add(cBNotaFiscal);

		JButton btnSelecionarNF = new JButton("Selecionar");
		btnSelecionarNF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
				TelaNotasFiscais tela = new TelaNotasFiscais(0,vendedor,isto);
				tela.setPai(isto);
				
				String destinatario = "";
				
				if(cliente_carregamento.getTipo_pessoa() == 0) {
					destinatario = cliente_carregamento.getNome_empresarial();
				}else {
					destinatario = cliente_carregamento.getNome_fantaia();
				}
				
					String remetente = "";
				
				if(vendedor.getTipo_pessoa() == 0) {
					remetente = vendedor.getNome_empresarial();
				}else {
					remetente = vendedor.getNome_fantaia();
				}
				
				
				String produto = produto_carregamento.getNome_produto();
				
				
				tela.setDadosPesquisa(destinatario, remetente, "Venda", produto);
				tela.setVisible(true);
			}
		});
		btnSelecionarNF.setBounds(352, 321, 89, 30);
		panel_1.add(btnSelecionarNF);

		JButton btnAdicionarCarregamento = new JButton("Adicionar");
		btnAdicionarCarregamento.setBounds(885, 180, 83, 23);
		panel_1.add(btnAdicionarCarregamento);

		JLabel lblNewLabel_8 = new JLabel("Contrato:");
		lblNewLabel_8.setBounds(69, 59, 56, 14);
		panel_1.add(lblNewLabel_8);

		cBContrato = new JComboBox();
		cBContrato.setEnabled(false);
		cBContrato.setBounds(135, 49, 248, 30);
		panel_1.add(cBContrato);

		JButton btnSelecionarContrato = new JButton("Selecionar");
		btnSelecionarContrato.setEnabled(false);
		btnSelecionarContrato.setVisible(false);
		btnSelecionarContrato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaContratos contrato = new TelaContratos(1, isto);
				contrato.setTelaPai(isto);
				contrato.setVisible(true);
			}
		});
		btnSelecionarContrato.setBounds(393, 47, 89, 30);
		panel_1.add(btnSelecionarContrato);

		JLabel lblNewLabel_8_1 = new JLabel("Cliente:");
		lblNewLabel_8_1.setBounds(69, 96, 56, 14);
		panel_1.add(lblNewLabel_8_1);

		cBCliente = new JComboBox();
		cBCliente.setBounds(135, 90, 248, 30);
		panel_1.add(cBCliente);

		JButton btnSelecionarCliente = new JButton("Selecionar");
		btnSelecionarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCliente tela = new TelaCliente(0, 5, isto);
				tela.setTelaPai(isto);
				tela.setVisible(true);
			}
		});
		CadastroCliente compradores[] = contrato_local.getCompradores();
		setClienteCarregamento(compradores[0]);
		
		
		btnSelecionarCliente.setBounds(393, 88, 89, 30);
		panel_1.add(btnSelecionarCliente);

		JLabel lblNewLabel_9 = new JLabel("Peso Real da Carga:");
		lblNewLabel_9.setBounds(27, 290, 106, 14);
		panel_1.add(lblNewLabel_9);

		entPesoRealCarga = new JTextField();
		entPesoRealCarga.setColumns(10);
		entPesoRealCarga.setBounds(135, 283, 168, 27);
		panel_1.add(entPesoRealCarga);

		JLabel lblNewLabel_10 = new JLabel("KG");
		lblNewLabel_10.setBounds(313, 280, 26, 24);
		panel_1.add(lblNewLabel_10);

		JLabel lblNewLabel_9_1 = new JLabel("Produto:");
		lblNewLabel_9_1.setBounds(54, 249, 70, 14);
		panel_1.add(lblNewLabel_9_1);

		cBProduto = new JComboBox();
		cBProduto.setBounds(134, 239, 209, 30);
		panel_1.add(cBProduto);

		
		JButton btnSelecionarProduto = new JButton("Selecionar");
		btnSelecionarProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaProdutos produto = new TelaProdutos(1,isto);
				produto.setTelaPai(isto);
				produto.setVisible(true);
			}
		});
		btnSelecionarProduto.setBounds(352, 239, 89, 30);
		panel_1.add(btnSelecionarProduto);
		
		CadastroProduto prod = contrato_local.getModelo_produto();
		setProduto(prod);
		
		JLabel lblNewLabel_8_1_1 = new JLabel("Vendedor:");
		lblNewLabel_8_1_1.setBounds(69, 136, 56, 14);
		panel_1.add(lblNewLabel_8_1_1);
		
		 cBVendedor = new JComboBox();
		cBVendedor.setBounds(135, 131, 248, 30);
		panel_1.add(cBVendedor);
		
		JButton btnSelecionarVendedor = new JButton("Selecionar");
		btnSelecionarVendedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCliente tela = new TelaCliente(0, 6, isto);
				tela.setTelaPai(isto);
				tela.setVisible(true);
			}
		});
		btnSelecionarVendedor.setBounds(393, 129, 89, 30);
		panel_1.add(btnSelecionarVendedor);
		
		CadastroCliente vendedores[] = contrato_local.getVendedores();
		setVendedor(vendedores[0]);
		
		JButton btnCancelar_1 = new JButton("Revisar");
		btnCancelar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				isto.dispose();
			}
		});
		btnCancelar_1.setBounds(424, 405, 89, 23);
		painelSelecionar.add(btnCancelar_1);

		getContentPane().add(abas, BorderLayout.CENTER);

		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 544, 506);
		painelConfirmar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				atualizarDadosConfirmar();
			}
		});

		painelConfirmar.setBackground(new Color(255, 255, 255));

		// adiciona novos paines e suas abas
		abas.addTab("Confirmar", painelConfirmar);
		painelConfirmar.setLayout(null);

		JLabel lblNewLabel = new JLabel("Data:");
		lblNewLabel.setBounds(36, 39, 36, 14);
		painelConfirmar.add(lblNewLabel);

		lblDataCarregamento = new JLabel("");
		lblDataCarregamento.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblDataCarregamento.setBounds(92, 21, 184, 32);
		painelConfirmar.add(lblDataCarregamento);

		lblClienteCarregamento = new JLabel("");
		lblClienteCarregamento.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblClienteCarregamento.setBounds(92, 64, 389, 32);

		painelConfirmar.add(lblClienteCarregamento);

		JLabel lblCliente = new JLabel("Cliente:");
		lblCliente.setBounds(36, 76, 46, 14);
		painelConfirmar.add(lblCliente);

		JButton btnSalvar = new JButton("Confirmar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
				CadastroContrato.Carregamento carregamento_a_inserir = new CadastroContrato.Carregamento();
				carregamento_a_inserir.setData(lblDataCarregamento.getText());
				carregamento_a_inserir.setId_cliente(cliente_carregamento.getId());
				carregamento_a_inserir.setId_vendedor(vendedor.getId());
				carregamento_a_inserir.setId_contrato(contrato_local.getId());
				carregamento_a_inserir.setId_transportador(transportador_carregamento.getId());
				carregamento_a_inserir.setId_veiculo(veiculo_carregamento.getId_veiculo());
				carregamento_a_inserir.setId_produto(produto_carregamento.getId_produto());
				carregamento_a_inserir.setPeso_real_carga(Double.parseDouble(lblPesoRealCarregamento.getText()));
				carregamento_a_inserir.setCodigo_nota_fiscal(nota_fiscal_carregamento.getNfe());
				
				String caminho_completo = nota_fiscal_carregamento.getCaminho_arquivo();
				TratarDados tratar = new TratarDados(caminho_completo);
					String caminho_normalizado = tratar.tratar("E-Contract", "pdf")	;
					String caminho_completo_normalizado = "E-Contract" +  caminho_normalizado + "pdf";
					String conteudo [] = caminho_completo_normalizado.split("\\\\");
					String url_final = "";
					for(String str : conteudo) {
						
						url_final = url_final + str + "\\\\";
					}
				carregamento_a_inserir.setCaminho_nota_fiscal(url_final);

				
				boolean retorno = gerenciar.inserirCarregamento(contrato_local.getId(),
						carregamento_a_inserir);
				if (retorno) {
					JOptionPane.showMessageDialog(null, "Carregamento Cadastrado!");
					//((TelaGerenciarContrato) telaPai).pesquisar_carregamentos();
					((TelaGerenciarContrato) telaPaiJFrame).pesquisar_carregamentos();
					
					
					/*if (JOptionPane.showConfirmDialog(isto, 
				            "Replicar este carregamento nos sub-contratos deste contrato?", "Replicar", 
				            JOptionPane.YES_NO_OPTION,
				            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
						
						    TelaReplicarCarregamento replicar = new TelaReplicarCarregamento(contrato_local, carregamento_a_inserir);
						    replicar.setTelaPai(isto);
						    replicar.setVisible(true);
						    isto.dispose();
						
					}else {
						isto.dispose();

					}
					*/
					
					isto.dispose();

				} else {
					JOptionPane.showMessageDialog(null,
							"Erro ao inserir o carregamento\nConsulte o administrador do sistema!");
					isto.dispose();
				}

			}
		});
		btnSalvar.setBounds(288, 358, 89, 23);
		painelConfirmar.add(btnSalvar);

		JButton btnCancelar = new JButton("Revisar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				isto.dispose();
			}
		});
		btnCancelar.setBounds(392, 358, 89, 23);
		painelConfirmar.add(btnCancelar);

		JLabel lblNewLabel_1 = new JLabel("Contrato:");
		lblNewLabel_1.setBounds(33, 119, 66, 14);
		painelConfirmar.add(lblNewLabel_1);

		lblContratoCarregamento = new JLabel("");
		lblContratoCarregamento.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblContratoCarregamento.setBounds(92, 107, 389, 32);

		painelConfirmar.add(lblContratoCarregamento);

		JLabel lblNewLabel_1_1 = new JLabel("Transportador:");
		lblNewLabel_1_1.setBounds(10, 159, 89, 14);
		painelConfirmar.add(lblNewLabel_1_1);

		lblTransportadorCarregamento = new JLabel("");
		lblTransportadorCarregamento.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblTransportadorCarregamento.setBounds(92, 152, 184, 32);

		painelConfirmar.add(lblTransportadorCarregamento);

		JLabel lblNewLabel_1_1_1 = new JLabel("Veiculo:");
		lblNewLabel_1_1_1.setBounds(296, 155, 46, 14);
		painelConfirmar.add(lblNewLabel_1_1_1);

		lblVeiculoCarregamento = new JLabel("");
		lblVeiculoCarregamento.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblVeiculoCarregamento.setBounds(352, 150, 129, 32);
		painelConfirmar.add(lblVeiculoCarregamento);

		JLabel lblNewLabel_1_1_1_1 = new JLabel("Produto:");
		lblNewLabel_1_1_1_1.setBounds(36, 207, 46, 14);
		painelConfirmar.add(lblNewLabel_1_1_1_1);

		lblProdutoCarregamento = new JLabel("");
		lblProdutoCarregamento.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblProdutoCarregamento.setBounds(92, 195, 184, 32);
		painelConfirmar.add(lblProdutoCarregamento);

		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("Peso Real:");
		lblNewLabel_1_1_1_1_1.setBounds(286, 207, 75, 14);
		painelConfirmar.add(lblNewLabel_1_1_1_1_1);

		lblPesoRealCarregamento = new JLabel("");
		lblPesoRealCarregamento.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblPesoRealCarregamento.setBounds(352, 193, 129, 32);
		painelConfirmar.add(lblPesoRealCarregamento);

		JLabel lblNewLabel_2 = new JLabel("Nota Fiscal:");
		lblNewLabel_2.setBounds(10, 249, 72, 14);
		painelConfirmar.add(lblNewLabel_2);
		
		lblNotaFiscalCarregamento = new JTextArea("");
		lblNotaFiscalCarregamento.setLineWrap(true);
		lblNotaFiscalCarregamento.setWrapStyleWord(true);
		lblNotaFiscalCarregamento.setBorder(new LineBorder(new Color(0, 0, 0)));

		
		JScrollPane scrollPane = new JScrollPane(lblNotaFiscalCarregamento);
		scrollPane.setBounds(88, 237, 403, 95);
		painelConfirmar.add(scrollPane);
		
		 lblCaminhoNFa = new JLabel("");
		lblCaminhoNFa.setBounds(92, 336, 399, 14);
		painelConfirmar.add(lblCaminhoNFa);
		
			
	    //this.setUndecorated(true);
		this.setLocationRelativeTo(janela_pai);

	}

	public void setNotaFiscal(CadastroNFe _nfe) {

		this.nota_fiscal_carregamento = _nfe;
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				cBNotaFiscal.removeAllItems();
				cBNotaFiscal.repaint();
				cBNotaFiscal.updateUI();

				cBNotaFiscal.addItem(_nfe.getNfe());

				cBNotaFiscal.repaint();
				cBNotaFiscal.updateUI();

			}
		});
	}

	public void setClienteCarregamento(CadastroCliente cliente) {

		this.cliente_carregamento = cliente;

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				cBCliente.removeAllItems();
				cBCliente.repaint();
				cBCliente.updateUI();

				if (cliente.getTipo_pessoa() == 0) {
					// pessoa fisica
					cBCliente.addItem(cliente.getNome_empresarial());

				} else {
					cBCliente.addItem(cliente.getNome_fantaia());

				}

				cBCliente.repaint();
				cBCliente.updateUI();

			}
		});
	}

	public void setContratoCarregamento(CadastroContrato contrato) {

		this.contrato_carregamento = contrato;

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				cBContrato.removeAllItems();
				cBContrato.repaint();
				cBContrato.updateUI();
				cBContrato.addItem(contrato.getCodigo());

				cBContrato.repaint();
				cBContrato.updateUI();

			}
		});
	}

	public void setProduto(CadastroProduto prod) {

		this.produto_carregamento = prod;
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				cBProduto.removeAllItems();
				cBProduto.repaint();
				cBProduto.updateUI();
				cBProduto.addItem(prod.getNome_produto());

				cBProduto.repaint();
				cBProduto.updateUI();

			}
		});

	}

	public void setTransportador(CadastroCliente _transportador) {

		this.transportador_carregamento = _transportador;
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				cBTransportador.removeAll();
				cBTransportador.removeAllItems();
				cBTransportador.repaint();
				cBTransportador.updateUI();

				cBTransportador.addItem(_transportador.getNome() + _transportador.getSobrenome());

				cBTransportador.repaint();
				cBTransportador.updateUI();

				cbVeiculo.removeAllItems();

				for (CadastroCliente.Veiculo veiculo : _transportador.getVeiculos()) {
					cbVeiculo.addItem(veiculo.getId_veiculo() + "-" + veiculo.getPlaca_trator());
				}

				cbVeiculo.repaint();
				cbVeiculo.updateUI();

				transportador_carregamento = _transportador;
			}
		});

	}

	public void atualizarDadosConfirmar() {
		lblDataCarregamento.setText(entDataCarregamento.getText());
		String nome_cliente;
		if (cliente_carregamento.getTipo_pessoa() == 0) {
			// pessoa fisica
			nome_cliente = cliente_carregamento.getNome_empresarial();
		} else {
			nome_cliente = cliente_carregamento.getNome_fantaia();
		}
		lblClienteCarregamento.setText(nome_cliente);

		lblContratoCarregamento.setText(contrato_local.getCodigo());
		lblTransportadorCarregamento
				.setText(transportador_carregamento.getNome() + " " + transportador_carregamento.getSobrenome());

		String s_veiculo = cbVeiculo.getSelectedItem().toString();
		String separados[] = s_veiculo.split("-");
		int id_veiculo = Integer.parseInt(separados[0]);
		
		for(CadastroCliente.Veiculo veiculo : transportador_carregamento.getVeiculos()) {
			if(veiculo.getId_veiculo() == id_veiculo) {
				veiculo_carregamento = veiculo;
				lblVeiculoCarregamento.setText(veiculo_carregamento.getId_veiculo() + "-" + veiculo_carregamento.getPlaca_trator());
				break;
			}
			
		}
		
		lblPesoRealCarregamento.setText(entPesoRealCarga.getText());
		lblProdutoCarregamento.setText(produto_carregamento.getNome_produto());
		
		String codigo = nota_fiscal_carregamento.getNfe();
		String peso_nf = nota_fiscal_carregamento.getQuantidade();
		String destinatario = nota_fiscal_carregamento.getNome_destinatario();
		String remetente = nota_fiscal_carregamento.getNome_remetente();
		String natureza = nota_fiscal_carregamento.getNatureza();
		String data = nota_fiscal_carregamento.getData().toString();
		String produto = nota_fiscal_carregamento.getProduto();
		String valor = nota_fiscal_carregamento.getValor();
		
		String texto_revisao_nfs = "";
		
		texto_revisao_nfs = "NFe: " + codigo +
				"\nRemetente: " + remetente +
				"\nDestinatario: " + destinatario +
				"\nNatureza: " + natureza +
				"\nData: " + data +
				"\nProduto: " + produto +
				"\nPeso: " + peso_nf +
				"\nValor: " + valor
				
				;
		lblNotaFiscalCarregamento.setText(texto_revisao_nfs);
		String caminho_completo = nota_fiscal_carregamento.getCaminho_arquivo();
		TratarDados tratar = new TratarDados(caminho_completo);
			String caminho_normalizado = tratar.tratar("E-Contract", "pdf")	;
			String caminho_completo_normalizado = "E-Contract" +  caminho_normalizado + "pdf".replace("\"", "\\");
		lblCaminhoNFa.setText(caminho_completo_normalizado);

	}

	public void setTelaPai(JDialog dialog) {
		this.telaPai = dialog;
	}
	
	public void setVendedor(CadastroCliente _vendedor) {
		this.vendedor = _vendedor;
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				//cBVendedor.removeAll();
				cBVendedor.removeAllItems();
				cBVendedor.repaint();
				cBVendedor.updateUI();
				
				String nome;
				if(vendedor.getTipo_pessoa() == 0) {
					//pessoa fisica
					nome = vendedor.getNome_empresarial();
				}else {
					nome = vendedor.getNome_fantaia();

				}

				cBVendedor.addItem(nome);

				cBVendedor.repaint();
				cBVendedor.updateUI();

				
			}
		});
	}
	
	public void setTelaPai(JFrame tela_pai) {
		this.telaPaiJFrame = tela_pai;
	}	
       
}
