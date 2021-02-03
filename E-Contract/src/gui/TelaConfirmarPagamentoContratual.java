
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
import cadastros.ContaBancaria;
import classesExtras.Carregamento;
import conexaoBanco.GerenciarBancoClientes;
import conexaoBanco.GerenciarBancoContratos;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class TelaConfirmarPagamentoContratual extends JDialog {

	private JTabbedPane abas = new JTabbedPane();
	private JPanel painelConfirmar = new JPanel();
	private JPanel painelSelecionar = new JPanel();

	private TelaConfirmarPagamentoContratual isto;
	private JDialog telaPai;
	private JFrame  telaPaiJFrame;
	private JTextField entDataPagamento;
	private JComboBox cBDepositante, cBContaDepositante;
	private JComboBox cBFavorecido, cBContaFavorecido, cBTipo;
	private JTextArea textAreaDescricao ;

	private CadastroContrato contrato_local;

	private CadastroCliente favorecido;
	private ContaBancaria conta_favorecido;
	private CadastroCliente depositante;
	private ContaBancaria conta_depositante;
	
	private JTextField entValorPagamento;

	private JLabel lblValorPagamento, lblFavorecidoPagamento, lblDataPagamento, lblDepositantePagamento  ;

	private JTextArea lblContaDepositantePagamento, lblContaFavorecidoPagamento, lblDescricaoPagamento;
	private JLabel lblTipoPagamento;
	
	public TelaConfirmarPagamentoContratual(CadastroContrato _contrato_local, JFrame janela_pai) {
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
		panel_1.setBackground(Color.WHITE);
		panel_1.setLayout(null);
		panel_1.setBounds(21, 11, 492, 335);
		painelSelecionar.add(panel_1);

		JLabel lblNewLabel_3 = new JLabel("Data:");
		lblNewLabel_3.setBounds(79, 11, 46, 23);
		panel_1.add(lblNewLabel_3);

		entDataPagamento = new JTextField();
		entDataPagamento.setText("26/12/2020");
		entDataPagamento.setEditable(false);
		entDataPagamento.setColumns(10);
		entDataPagamento.setBounds(135, 8, 116, 30);
		panel_1.add(entDataPagamento);

		JCheckBox chkBoxDataHoje = new JCheckBox("Data Atual");
		chkBoxDataHoje.setSelected(true);
		chkBoxDataHoje.setBounds(257, 7, 88, 23);
		panel_1.add(chkBoxDataHoje);

		JLabel lblNewLabel_5 = new JLabel("Conta Depositante:");
		lblNewLabel_5.setBounds(27, 135, 117, 14);
		panel_1.add(lblNewLabel_5);

		cBContaDepositante = new JComboBox();
		cBContaDepositante.setBounds(135, 129, 248, 30);
		panel_1.add(cBContaDepositante);

		JButton btnAdicionarCarregamento = new JButton("Adicionar");
		btnAdicionarCarregamento.setBounds(885, 180, 83, 23);
		panel_1.add(btnAdicionarCarregamento);

		JLabel lblNewLabel_8 = new JLabel("Valor:");
		lblNewLabel_8.setBounds(69, 59, 56, 14);
		panel_1.add(lblNewLabel_8);

		JLabel lblNewLabel_8_1 = new JLabel("Depositante:");
		lblNewLabel_8_1.setBounds(42, 96, 83, 14);
		panel_1.add(lblNewLabel_8_1);

		cBDepositante = new JComboBox();
		cBDepositante.setBounds(135, 90, 248, 30);
		panel_1.add(cBDepositante);

		JButton btnSelecionarDepositante = new JButton("Selecionar");
		btnSelecionarDepositante.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCliente tela = new TelaCliente(0, 8, isto);
				tela.setTelaPai(isto);
				tela.setVisible(true);
			}
		});
		btnSelecionarDepositante.setBounds(393, 88, 89, 30);
		panel_1.add(btnSelecionarDepositante);
		
		entValorPagamento = new JTextField();
		entValorPagamento.setBounds(134, 49, 140, 35);
		panel_1.add(entValorPagamento);
		entValorPagamento.setColumns(10);
		
		JLabel lblNewLabel_8_1_1 = new JLabel("Favorecido:");
		lblNewLabel_8_1_1.setBounds(42, 198, 83, 14);
		panel_1.add(lblNewLabel_8_1_1);
		
		JLabel lblNewLabel_5_1 = new JLabel("Conta Favorecido:");
		lblNewLabel_5_1.setBounds(27, 237, 117, 14);
		panel_1.add(lblNewLabel_5_1);
		
		 cBFavorecido = new JComboBox();
		cBFavorecido.setBounds(135, 192, 248, 30);
		panel_1.add(cBFavorecido);
		
		 cBContaFavorecido = new JComboBox();
		cBContaFavorecido.setBounds(135, 231, 248, 30);
		panel_1.add(cBContaFavorecido);
		
		JButton btnSelecionarFavorecido = new JButton("Selecionar");
		btnSelecionarFavorecido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaCliente tela = new TelaCliente(0, 9, isto);
				tela.setTelaPai(isto);
				tela.setVisible(true);
			}
		});
		btnSelecionarFavorecido.setBounds(393, 190, 89, 30);
		panel_1.add(btnSelecionarFavorecido);
		
		JLabel lblNewLabel_5_1_1 = new JLabel("Tipo:");
		lblNewLabel_5_1_1.setBounds(98, 279, 27, 16);
		panel_1.add(lblNewLabel_5_1_1);
		
		 cBTipo = new JComboBox();
		cBTipo.setBounds(135, 272, 248, 30);
		panel_1.add(cBTipo);
		cBTipo.addItem("Normal");
		cBTipo.addItem("Comissão");

		
		JLabel lblNewLabel_5_1_1_1 = new JLabel("Descrição:");
		lblNewLabel_5_1_1_1.setBounds(66, 319, 59, 16);
		panel_1.add(lblNewLabel_5_1_1_1);
		
		 textAreaDescricao = new JTextArea();
		textAreaDescricao.setLineWrap(true);
		textAreaDescricao.setWrapStyleWord(true);
		textAreaDescricao.setBounds(135, 313, 347, 77);
		panel_1.add(textAreaDescricao);
		
		JButton btnCancelar_2 = new JButton("Cancelar");
		btnCancelar_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isto.dispose();

			}
		});
		btnCancelar_2.setBounds(424, 415, 89, 23);
		painelSelecionar.add(btnCancelar_2);

		getContentPane().add(abas, BorderLayout.CENTER);

		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 572, 518);
		painelConfirmar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				atualizarInformacoesPagamentos();
			}
		});
		
		painelConfirmar.setBackground(new Color(255, 255, 255));

		// adiciona novos paines e suas abas
		abas.addTab("Confirmar", painelConfirmar);
		painelConfirmar.setLayout(null);

		JLabel lblNewLabel = new JLabel("Data:");
		lblNewLabel.setBounds(59, 53, 36, 14);
		painelConfirmar.add(lblNewLabel);

		lblDataPagamento = new JLabel("");
		lblDataPagamento.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblDataPagamento.setBounds(105, 42, 184, 32);
		painelConfirmar.add(lblDataPagamento);

		JButton btnSalvar = new JButton("Confirmar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
				CadastroContrato.CadastroPagamentoContratual pagamento_a_inserir = new CadastroContrato.CadastroPagamentoContratual();
				
				
				String s_tipo = cBTipo.getSelectedItem().toString();
				
				int tipo = -1;
				
				if(s_tipo.equalsIgnoreCase("Normal")) {
					tipo = 1;
				}else if(s_tipo.equalsIgnoreCase("Comissão")) {
					tipo = 2;
				}
				
				pagamento_a_inserir.setDescricao(lblDescricaoPagamento.getText());
				pagamento_a_inserir.setTipo(tipo);
				pagamento_a_inserir.setData_pagamento(lblDataPagamento.getText());
				pagamento_a_inserir.setValor_pagamento(Double.parseDouble(lblValorPagamento.getText()));
				pagamento_a_inserir.setId_depositante(depositante.getId());
				pagamento_a_inserir.setId_conta_depositante(conta_depositante.getId_conta());
				pagamento_a_inserir.setId_favorecido(favorecido.getId());
				pagamento_a_inserir.setId_conta_favorecido(conta_favorecido.getId_conta());
			
				boolean retorno = gerenciar.inserirPagamento(contrato_local.getId(), pagamento_a_inserir
						);
				if (retorno) {
					JOptionPane.showMessageDialog(isto, "Pagamento Cadastrado!");
					//((TelaGerenciarContrato) telaPai).pesquisar_pagamentos();
					((TelaGerenciarContrato) telaPaiJFrame).pesquisar_pagamentos();

					isto.dispose();

				} else {
					JOptionPane.showMessageDialog(isto,
							"Erro ao inserir o pagamento\nConsulte o administrador do sistema!");
					isto.dispose();
				}

				
			}
		});
		btnSalvar.setBounds(319, 420, 89, 23);
		painelConfirmar.add(btnSalvar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				isto.dispose();
			}
		});
		btnCancelar.setBounds(423, 420, 89, 23);
		painelConfirmar.add(btnCancelar);
		
		JLabel lblDepositante = new JLabel("Depositante:");
		lblDepositante.setBounds(36, 97, 73, 14);
		painelConfirmar.add(lblDepositante);
		
		
		
		lblDepositantePagamento = new JLabel("");
		lblDepositantePagamento.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblDepositantePagamento.setBounds(105, 86, 426, 32);
		painelConfirmar.add(lblDepositantePagamento);
		
		JLabel lblConta = new JLabel("Conta:");
		lblConta.setBounds(65, 140, 44, 14);
		painelConfirmar.add(lblConta);
		
		 lblContaDepositantePagamento = new JTextArea();
		 lblContaDepositantePagamento.setEditable(false);
		lblContaDepositantePagamento.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblContaDepositantePagamento.setBounds(105, 129, 426, 57);
		lblContaDepositantePagamento.setLineWrap(true);

		painelConfirmar.add(lblContaDepositantePagamento);
		
		JLabel lblConta_1 = new JLabel("Conta:");
		lblConta_1.setBounds(65, 251, 44, 14);
		painelConfirmar.add(lblConta_1);
		
		 lblContaFavorecidoPagamento = new JTextArea();
		 lblContaFavorecidoPagamento.setEditable(false);
		lblContaFavorecidoPagamento.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblContaFavorecidoPagamento.setBounds(105, 240, 426, 58);
		lblContaFavorecidoPagamento.setLineWrap(true);

		painelConfirmar.add(lblContaFavorecidoPagamento);
		
		
		 lblFavorecidoPagamento = new JLabel("");
		lblFavorecidoPagamento.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblFavorecidoPagamento.setBounds(105, 197, 426, 32);
		painelConfirmar.add(lblFavorecidoPagamento);
		
		JLabel lblFavorecido = new JLabel("Favorecido:");
		lblFavorecido.setBounds(36, 208, 73, 14);
		painelConfirmar.add(lblFavorecido);
		
		 lblValorPagamento = new JLabel("");
		lblValorPagamento.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblValorPagamento.setBounds(347, 42, 184, 32);
		painelConfirmar.add(lblValorPagamento);
		
		JLabel lblValor = new JLabel("Valor:");
		lblValor.setBounds(301, 53, 36, 14);
		painelConfirmar.add(lblValor);
		
		JLabel lblConta_1_1 = new JLabel("Tipo:");
		lblConta_1_1.setBounds(65, 310, 44, 14);
		painelConfirmar.add(lblConta_1_1);
		
		lblTipoPagamento = new JLabel("");
		lblTipoPagamento.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblTipoPagamento.setBounds(105, 310, 426, 32);
		painelConfirmar.add(lblTipoPagamento);
		
		JLabel lblConta_1_1_1 = new JLabel("Descrição:");
		lblConta_1_1_1.setBounds(36, 355, 59, 16);
		painelConfirmar.add(lblConta_1_1_1);
		
		 lblDescricaoPagamento = new JTextArea();
		lblDescricaoPagamento.setLineWrap(true);
		lblDescricaoPagamento.setEditable(false);
		lblDescricaoPagamento.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblDescricaoPagamento.setBounds(105, 354, 426, 58);
		painelConfirmar.add(lblDescricaoPagamento);

	    //this.setUndecorated(true);
		this.setLocationRelativeTo(janela_pai);

	}

	

	public void setDepositante(CadastroCliente _depositante) {

		this.depositante = _depositante;

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				cBDepositante.removeAllItems();
				cBDepositante.repaint();
				cBDepositante.updateUI();

				if (_depositante.getTipo_pessoa() == 0) {
					// pessoa fisica
					cBDepositante.addItem(_depositante.getNome_empresarial());

				} else {
					cBDepositante.addItem(_depositante.getNome_fantaia());

				}

				cBContaDepositante.removeAllItems();
				cBContaDepositante.repaint();
				cBContaDepositante.updateUI();

				GerenciarBancoClientes gerenciar_contas = new GerenciarBancoClientes();
				ArrayList<ContaBancaria> contas_depositante = gerenciar_contas.getContas(_depositante.getId());
				
				for (ContaBancaria conta : contas_depositante) {
					cBContaDepositante.addItem(conta.getId_conta() + "-" + conta.getNome());
				}

				cBContaDepositante.repaint();
				cBContaDepositante.updateUI();
				
			

			}
		});
	}
	
	
	public void setFavorecido(CadastroCliente _favorecido) {

		this.favorecido = _favorecido;

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				cBFavorecido.removeAllItems();
				cBFavorecido.repaint();
				cBFavorecido.updateUI();

				if (_favorecido.getTipo_pessoa() == 0) {
					// pessoa fisica
					cBFavorecido.addItem(_favorecido.getNome_empresarial());

				} else {
					cBFavorecido.addItem(_favorecido.getNome_fantaia());

				}
				cBFavorecido.repaint();
				cBFavorecido.updateUI();
				
				cBContaFavorecido.removeAllItems();
				cBContaFavorecido.repaint();
				cBContaFavorecido.updateUI();
				
				GerenciarBancoClientes gerenciar_contas = new GerenciarBancoClientes();
				ArrayList<ContaBancaria> contas_depositante = gerenciar_contas.getContas(_favorecido.getId());
				
				for (ContaBancaria conta : contas_depositante) {
					cBContaFavorecido.addItem(conta.getId_conta() + "-" + conta.getNome());
				}

				cBContaFavorecido.repaint();
				cBContaFavorecido.updateUI();
				
			
			}
		});
	}

	public void atualizarInformacoesPagamentos() {
		lblDataPagamento.setText(entDataPagamento.getText());
		lblValorPagamento.setText(entValorPagamento.getText());
		
		lblDepositantePagamento.setText(cBDepositante.getSelectedItem().toString());
		
		String s_conta_depositante = cBContaDepositante.getSelectedItem().toString();
		String separados[] = s_conta_depositante.split("-");
		int id_conta_depositante = Integer.parseInt(separados[0]);
		
		GerenciarBancoClientes gerenciar_contas = new GerenciarBancoClientes();
		ArrayList<ContaBancaria> contas_depositante = gerenciar_contas.getContas(depositante.getId());
		
		for(ContaBancaria conta : contas_depositante) {
			if(conta.getId_conta() == id_conta_depositante) {
				conta_depositante = conta;
				lblContaDepositantePagamento.setText("Id: " + conta.getId_conta() + " Nome: " + conta.getNome().toUpperCase() + " CPF/CNPJ: " + conta.getCpf_titular()
				+ "\n" + "Banco: " + conta.getBanco().toUpperCase() + " Codigo: " + conta.getCodigo()
				+"\n" +  "Agencia: " + conta.getAgencia() + " Conta: "+  conta.getConta() 
				);
		       break;			
			}
			
		}
		
		
        lblFavorecidoPagamento.setText(cBFavorecido.getSelectedItem().toString());
		
		String s_conta_favorecido = cBContaFavorecido.getSelectedItem().toString();
		String separados_favorecido[] = s_conta_favorecido.split("-");
		int id_conta_favorecido = Integer.parseInt(separados_favorecido[0]);
		
		GerenciarBancoClientes gerenciar_contas_favorecidos = new GerenciarBancoClientes();
		ArrayList<ContaBancaria> contas_favorecidos = gerenciar_contas_favorecidos.getContas(favorecido.getId());
		
		for(ContaBancaria conta : contas_favorecidos) {
			if(conta.getId_conta() == id_conta_favorecido) {
				conta_favorecido = conta;
				lblContaFavorecidoPagamento.setText("Id: " + conta.getId_conta() + " Nome: " + conta.getNome().toUpperCase() + " CPF/CNPJ: " + conta.getCpf_titular()
				+ "\n" + "Banco: " + conta.getBanco().toUpperCase() + " Codigo: " + conta.getCodigo()
				+"\n" +  "Agencia: " + conta.getAgencia() + " Conta: "+  conta.getConta() 
				);
				
				break;
			}
			
		}
		
		lblTipoPagamento.setText(cBTipo.getSelectedItem().toString());
		lblDescricaoPagamento.setText(textAreaDescricao.getText());
		
	}
	

	public void setTelaPai(JDialog dialog) {
		this.telaPai = dialog;
	}
	
	public void setTelaPai(JFrame dialog) {
		this.telaPaiJFrame = dialog;
	}
}
