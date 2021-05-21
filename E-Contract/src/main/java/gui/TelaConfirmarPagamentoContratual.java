
package main.java.gui;

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

import main.java.cadastros.CadastroAviso;
import main.java.cadastros.CadastroBaseArquivos;
import main.java.cadastros.CadastroBaseDados;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroContrato.CadastroPagamento;
import main.java.cadastros.CadastroContrato.CadastroPagamentoContratual;
import main.java.cadastros.CadastroContrato.Recebimento;
import main.java.cadastros.CadastroDocumento;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroModelo;
import main.java.cadastros.CadastroNFe;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroRomaneio;
import main.java.cadastros.CadastroSafra;
import main.java.cadastros.ContaBancaria;
import main.java.cadastros.Contato;
import main.java.cadastros.DadosCarregamento;
import main.java.cadastros.DadosContratos;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.classesExtras.Endereco;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoDocumento;
import main.java.conexaoBanco.GerenciarBancoNotasFiscais;
import main.java.conexaoBanco.GerenciarBancoPadrao;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoRomaneios;
import main.java.conexaoBanco.GerenciarBancoSafras;
import main.java.conexoes.TesteConexao;
import main.java.graficos.GraficoLinha;
import main.java.graficos.JPanelGrafico;
import main.java.graficos.JPanelGraficoCarregamento;
import main.java.gui.TelaCadastroCliente;
import main.java.gui.TelaMain;
import main.java.gui.TelaRomaneios;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.manipular.ConverterPdf;
import main.java.manipular.CopiarArquivo;
import main.java.manipular.GetDadosGlobais;
import main.java.manipular.ManipularNotasFiscais;
import main.java.manipular.ManipularRomaneios;
import main.java.manipular.ManipularTxt;
import main.java.outros.BaixarNotasFiscais;
import main.java.outros.BuscarCep;
import main.java.outros.DadosGlobais;
import main.java.outros.GetData;
import main.java.outros.GetHttp;
import main.java.outros.GetSintegra;
import main.java.outros.JPanelBackground;
import main.java.outros.JPanelTransparent;
import main.java.outros.JTextFieldPersonalizado;
import main.java.outros.MyFileVisitor;
import main.java.outros.ReproduzirAudio;
import main.java.outros.TratarDados;
import main.java.relatoria.RelatorioContratoComprador;
import main.java.relatoria.RelatorioContratoRecebimentoSimplificado;
import main.java.relatoria.RelatorioContratos;
import main.java.tratamento_proprio.Log;
import main.java.views_personalizadas.TelaEmEspera;
import main.java.views_personalizadas.TelaNotificacao;
import main.java.views_personalizadas.TelaNotificacaoSuperior;
import main.java.views_personalizadas.TelaNotificacaoSuperiorModoBusca;
import outros.ValidaCNPj;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroNuvem;
import main.java.cadastros.CadastroZapMessenger;
import main.java.outros.DadosGlobais;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroGrupo;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoGrupos;
import main.java.cadastros.CadastroProduto;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroSafra;
import main.java.classesExtras.CBProdutoPersonalizado;
import main.java.classesExtras.CBProdutoRenderPersonalizado;
import main.java.classesExtras.ComboBoxPersonalizado;
import main.java.classesExtras.ComboBoxRenderPersonalizado;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoSafras;
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
import java.awt.Font;

public class TelaConfirmarPagamentoContratual extends JDialog {

	private JTabbedPane abas = new JTabbedPane();
	private JPanel painelConfirmar = new JPanel();
	private JPanel painelSelecionar = new JPanel();

	private TelaConfirmarPagamentoContratual isto;
	private JDialog telaPai;
	private JFrame telaPaiJFrame;
	private JTextField entDataPagamento;
	private JComboBox cBDepositante, cBContaDepositante;
	private JComboBox cBFavorecido, cBContaFavorecido, cBTipo;
	private JTextArea textAreaDescricao;

	private CadastroContrato contrato_local;

	private CadastroCliente favorecido;
	private ContaBancaria conta_favorecido;
	private CadastroCliente depositante;
	private ContaBancaria conta_depositante;

	private JTextField entValorPagamento;
	private JCheckBox chkBoxDataHoje;

	private JLabel lblValorPagamento, lblFavorecidoPagamento, lblDataPagamento, lblDepositantePagamento;

	private JTextArea lblContaDepositantePagamento, lblContaFavorecidoPagamento, lblDescricaoPagamento;
	private JLabel lblTipoPagamento;
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	
	public void getDadosGlobais() {
		//gerenciador de log
				DadosGlobais dados = DadosGlobais.getInstance();
				 GerenciadorLog = dados.getGerenciadorLog();
				 configs_globais = dados.getConfigs_globais();
				 
				 //usuario logado
				  login = dados.getLogin();
		
	}
	public TelaConfirmarPagamentoContratual(int modo_operacao, CadastroContrato.CadastroPagamentoContratual pagamento,
			CadastroContrato _contrato_local, JFrame janela_pai) {
		// setAlwaysOnTop(true);

		// setModal(true);
		 getDadosGlobais();

		isto = this;
		this.contrato_local = _contrato_local;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		if (modo_operacao == 0)
			setTitle("E-Contract - Novo Pagamento Contratual");
		else
			setTitle("E-Contract - Edição Pagamento Contratual");

		abas.setBackground(new Color(255, 255, 255));
		abas.setBorder(new EmptyBorder(5, 5, 5, 5));
		abas = new JTabbedPane();

		getContentPane().add(abas, BorderLayout.CENTER);

		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 572, 628);
		painelConfirmar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				atualizarInformacoesPagamentos();
			}
		});
		// contentPanel.setBackground(new Color(255, 255, 255));
		// contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		// setContentPane(contentPanel);
		// contentPanel.setLayout(null);

		painelSelecionar.setBackground(new Color(255, 255, 255));

		abas.addTab("Selecionar", painelSelecionar);
		painelSelecionar.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 11, 544, 467);
		panel_1.setBackground(Color.WHITE);
		panel_1.setLayout(null);
		painelSelecionar.add(panel_1);

		JLabel lblNewLabel_3 = new JLabel("Data:");
		lblNewLabel_3.setBounds(79, 11, 46, 23);
		panel_1.add(lblNewLabel_3);

		entDataPagamento = new JTextField();
		entDataPagamento.setText(new GetData().getData());
		entDataPagamento.setEditable(false);
		entDataPagamento.setColumns(10);
		entDataPagamento.setBounds(135, 8, 116, 30);
		panel_1.add(entDataPagamento);

		chkBoxDataHoje = new JCheckBox("Data Atual");
		chkBoxDataHoje.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chkBoxDataHoje.isSelected()) {
					entDataPagamento.setEditable(false);
					entDataPagamento.setEnabled(false);
					entDataPagamento.setText(new GetData().getData());

				} else {
					entDataPagamento.setEditable(true);
					entDataPagamento.setEnabled(true);

				}
			}
		});
		chkBoxDataHoje.setSelected(true);
		chkBoxDataHoje.setBounds(257, 7, 88, 23);
		panel_1.add(chkBoxDataHoje);

		JLabel lblNewLabel_5 = new JLabel("Conta Depositante:");
		lblNewLabel_5.setBounds(27, 135, 117, 14);
		panel_1.add(lblNewLabel_5);

		cBContaDepositante = new JComboBox();
		cBContaDepositante.setBounds(135, 129, 397, 30);
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
		cBDepositante.setBounds(135, 90, 300, 30);
		panel_1.add(cBDepositante);

		JButton btnSelecionarDepositante = new JButton("Selecionar");
		btnSelecionarDepositante.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCliente tela = new TelaCliente(0, 8, isto);
				tela.setTelaPai(isto);
				tela.setVisible(true);
			}
		});
		btnSelecionarDepositante.setBounds(443, 88, 89, 30);
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
		cBFavorecido.setBounds(135, 192, 300, 30);
		panel_1.add(cBFavorecido);

		cBContaFavorecido = new JComboBox();
		cBContaFavorecido.setBounds(135, 231, 397, 30);
		panel_1.add(cBContaFavorecido);

		JButton btnSelecionarFavorecido = new JButton("Selecionar");
		btnSelecionarFavorecido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaCliente tela = new TelaCliente(0, 9, isto);
				tela.setTelaPai(isto);
				tela.setVisible(true);
			}
		});
		btnSelecionarFavorecido.setBounds(443, 190, 89, 30);
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
		textAreaDescricao.setFont(new Font("SansSerif", Font.BOLD, 12));
		textAreaDescricao.setBorder(new LineBorder(new Color(0, 0, 0)));
		textAreaDescricao.setLineWrap(true);
		textAreaDescricao.setWrapStyleWord(true);
		textAreaDescricao.setBounds(135, 313, 347, 129);
		panel_1.add(textAreaDescricao);

		JButton btnCancelar_2 = new JButton("Cancelar");
		btnCancelar_2.setForeground(Color.WHITE);
		btnCancelar_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnCancelar_2.setBackground(new Color(204, 0, 0));
		btnCancelar_2.setBounds(457, 491, 87, 29);
		btnCancelar_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isto.dispose();

			}
		});
		painelSelecionar.add(btnCancelar_2);

		painelConfirmar.setBackground(new Color(255, 255, 255));

		// adiciona novos paines e suas abas
		abas.addTab("Confirmar", painelConfirmar);
		painelConfirmar.setLayout(null);

		JLabel lblNewLabel = new JLabel("Data:");
		lblNewLabel.setBounds(64, 52, 29, 16);
		painelConfirmar.add(lblNewLabel);

		lblDataPagamento = new JLabel("");
		lblDataPagamento.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblDataPagamento.setBounds(105, 42, 184, 32);
		painelConfirmar.add(lblDataPagamento);

		JButton btnSalvar = new JButton("Confirmar");
		btnSalvar.setBackground(new Color(0, 51, 0));
		btnSalvar.setForeground(Color.WHITE);
		btnSalvar.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();

				boolean retorno = gerenciar.inserirPagamento(contrato_local.getId(), getDadosSalvar());
				if (retorno) {
					JOptionPane.showMessageDialog(isto, "Pagamento Cadastrado!");
					// ((TelaGerenciarContrato) telaPai).pesquisar_pagamentos();
					/*//criar tarefa de pagamento cadastrado
					ArrayList<CadastroContrato.CadastroTarefa> lista_tarefas = new ArrayList<>();
					lista_tarefas.add(criarTarefa("Inserção de Pagamento Contratual", "Pagamento contratual inserido", ""));
					boolean criouTarefa = gerenciar.inserirTarefas(contrato_local.getId(), lista_tarefas);
					*/
					((TelaGerenciarContrato) telaPaiJFrame).pesquisar_pagamentos(true);

					isto.dispose();

				} else {
					JOptionPane.showMessageDialog(isto,
							"Erro ao inserir o pagamento\nConsulte o administrador do sistema!");
					isto.dispose();
				}

			}
		});
		btnSalvar.setBounds(314, 508, 97, 31);
		painelConfirmar.add(btnSalvar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBackground(new Color(204, 0, 0));
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				isto.dispose();
			}
		});
		btnCancelar.setBounds(441, 508, 90, 31);
		painelConfirmar.add(btnCancelar);

		JLabel lblDepositante = new JLabel("Depositante:");
		lblDepositante.setBounds(25, 86, 70, 16);
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
		lblContaDepositantePagamento.setBounds(105, 129, 426, 83);
		lblContaDepositantePagamento.setLineWrap(true);

		painelConfirmar.add(lblContaDepositantePagamento);

		JLabel lblConta_1 = new JLabel("Conta:");
		lblConta_1.setBounds(59, 296, 36, 16);
		painelConfirmar.add(lblConta_1);

		lblContaFavorecidoPagamento = new JTextArea();
		lblContaFavorecidoPagamento.setEditable(false);
		lblContaFavorecidoPagamento.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblContaFavorecidoPagamento.setBounds(105, 268, 426, 79);
		lblContaFavorecidoPagamento.setLineWrap(true);

		painelConfirmar.add(lblContaFavorecidoPagamento);

		lblFavorecidoPagamento = new JLabel("");
		lblFavorecidoPagamento.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblFavorecidoPagamento.setBounds(106, 224, 426, 32);
		painelConfirmar.add(lblFavorecidoPagamento);

		JLabel lblFavorecido = new JLabel("Favorecido:");
		lblFavorecido.setBounds(32, 235, 63, 16);
		painelConfirmar.add(lblFavorecido);

		lblValorPagamento = new JLabel("");
		lblValorPagamento.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblValorPagamento.setBounds(347, 42, 184, 32);
		painelConfirmar.add(lblValorPagamento);

		JLabel lblValor = new JLabel("Valor:");
		lblValor.setBounds(301, 53, 36, 14);
		painelConfirmar.add(lblValor);

		JLabel lblConta_1_1 = new JLabel("Tipo:");
		lblConta_1_1.setBounds(68, 359, 27, 16);
		painelConfirmar.add(lblConta_1_1);

		lblTipoPagamento = new JLabel("");
		lblTipoPagamento.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblTipoPagamento.setBounds(106, 359, 426, 32);
		painelConfirmar.add(lblTipoPagamento);

		JLabel lblConta_1_1_1 = new JLabel("Descrição:");
		lblConta_1_1_1.setBounds(34, 420, 59, 16);
		painelConfirmar.add(lblConta_1_1_1);

		lblDescricaoPagamento = new JTextArea();
		lblDescricaoPagamento.setLineWrap(true);
		lblDescricaoPagamento.setEditable(false);
		lblDescricaoPagamento.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblDescricaoPagamento.setBounds(105, 409, 426, 87);
		painelConfirmar.add(lblDescricaoPagamento);

		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();

				boolean retorno = gerenciar.atualizarPagamentoContratual(contrato_local.getId(), getDadosAtualizar(pagamento));
				if (retorno) {
					JOptionPane.showMessageDialog(isto, "Pagamento Atualizado!");
					// ((TelaGerenciarContrato) telaPai).pesquisar_pagamentos();
					((TelaGerenciarContrato) telaPaiJFrame).pesquisar_pagamentos(true);

					isto.dispose();

				} else {
					JOptionPane.showMessageDialog(isto,
							"Erro ao inserir o pagamento\nConsulte o administrador do sistema!");
					isto.dispose();
				}
			}
		});
		btnAtualizar.setBackground(new Color(0, 51, 0));
		btnAtualizar.setForeground(Color.WHITE);
		btnAtualizar.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnAtualizar.setBounds(205, 508, 97, 31);
		painelConfirmar.add(btnAtualizar);

		if (modo_operacao == 0) {

			btnAtualizar.setEnabled(false);
			btnAtualizar.setVisible(false);
		} else {

			btnSalvar.setEnabled(false);
			btnSalvar.setVisible(false);
			rotinasEdicao(pagamento);
		}

		// this.setUndecorated(true);
		this.setLocationRelativeTo(janela_pai);

	}

	public void setDepositante(CadastroCliente _depositante) {

		this.depositante = _depositante;

		
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

	public void setFavorecido(CadastroCliente _favorecido) {

		this.favorecido = _favorecido;

		
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

	public void atualizarInformacoesPagamentos() {
		lblDataPagamento.setText(entDataPagamento.getText());
		lblValorPagamento.setText(entValorPagamento.getText());

		lblDepositantePagamento.setText(cBDepositante.getSelectedItem().toString());

		String s_conta_depositante = cBContaDepositante.getSelectedItem().toString();
		String separados[] = s_conta_depositante.split("-");
		int id_conta_depositante = Integer.parseInt(separados[0]);

		GerenciarBancoClientes gerenciar_contas = new GerenciarBancoClientes();
		ArrayList<ContaBancaria> contas_depositante = gerenciar_contas.getContas(depositante.getId());

		for (ContaBancaria conta : contas_depositante) {
			if (conta.getId_conta() == id_conta_depositante) {
				conta_depositante = conta;
				lblContaDepositantePagamento.setText("Id: " + conta.getId_conta() + " Nome: "
						+ conta.getNome().toUpperCase() + " CPF/CNPJ: " + conta.getCpf_titular() + "\n" + "Banco: "
						+ conta.getBanco().toUpperCase() + " Codigo: " + conta.getCodigo() + "\n" + "Agencia: "
						+ conta.getAgencia() + " Conta: " + conta.getConta());
				break;
			}

		}

		lblFavorecidoPagamento.setText(cBFavorecido.getSelectedItem().toString());

		String s_conta_favorecido = cBContaFavorecido.getSelectedItem().toString();
		String separados_favorecido[] = s_conta_favorecido.split("-");
		int id_conta_favorecido = Integer.parseInt(separados_favorecido[0]);

		GerenciarBancoClientes gerenciar_contas_favorecidos = new GerenciarBancoClientes();
		ArrayList<ContaBancaria> contas_favorecidos = gerenciar_contas_favorecidos.getContas(favorecido.getId());

		for (ContaBancaria conta : contas_favorecidos) {
			if (conta.getId_conta() == id_conta_favorecido) {
				conta_favorecido = conta;
				lblContaFavorecidoPagamento.setText("Id: " + conta.getId_conta() + " Nome: "
						+ conta.getNome().toUpperCase() + " CPF/CNPJ: " + conta.getCpf_titular() + "\n" + "Banco: "
						+ conta.getBanco().toUpperCase() + " Codigo: " + conta.getCodigo() + "\n" + "Agencia: "
						+ conta.getAgencia() + " Conta: " + conta.getConta());

				break;
			}

		}

		lblTipoPagamento.setText(cBTipo.getSelectedItem().toString());
		lblDescricaoPagamento.setText(textAreaDescricao.getText());

	}

	public void rotinasEdicao(CadastroContrato.CadastroPagamentoContratual pagamento) {

		entValorPagamento.setText(pagamento.getValor_pagamento() + "");
		GerenciarBancoClientes gerenciar_clientes = new GerenciarBancoClientes();
		CadastroCliente depositante = gerenciar_clientes.getCliente(pagamento.getId_depositante());
		setDepositante(depositante);
		
		
		GerenciarBancoClientes gerenciar_contas = new GerenciarBancoClientes();
		if(pagamento.getId_conta_depositante() > 0) {
		
		ContaBancaria cb_depositante = gerenciar_contas.getConta(pagamento.getId_conta_depositante());
		if(cb_depositante != null) {

		conta_depositante = cb_depositante;
		for(int i = 0; i < cBContaDepositante.getItemCount(); i++) {
			if(cBContaDepositante.getItemAt(i).toString().contains(cb_depositante.getId_conta()+"")) {
				cBContaDepositante.setSelectedIndex(i);
				break;
			}
		}
		}
		}
		
		
		CadastroCliente favorecido = gerenciar_clientes.getCliente(pagamento.getId_favorecido());
		setFavorecido(favorecido);
		
		if(pagamento.getId_conta_favorecido() > 0) {
			
			ContaBancaria cb_favorecido = gerenciar_contas.getConta(pagamento.getId_conta_favorecido());
			if(cb_favorecido != null) {
			conta_favorecido = cb_favorecido;
			for(int i = 0; i < cBContaFavorecido.getItemCount(); i++) {
				if(cBContaFavorecido.getItemAt(i).toString().contains(cb_favorecido.getId_conta()+"")) {
					cBContaFavorecido.setSelectedIndex(i);
					break;
				}
			}

			}
			
			}

		chkBoxDataHoje.setSelected(false);
		entDataPagamento.setEnabled(true);
		entDataPagamento.setEditable(true);
		entDataPagamento.setText(pagamento.getData_pagamento());
		textAreaDescricao.setText(pagamento.getDescricao());

		if (pagamento.getTipo() == 1) {
			cBTipo.setSelectedItem("Normal");
		} else if (pagamento.getTipo() == 2) {
			cBTipo.setSelectedItem("Comissão");

		}

	}

	public CadastroContrato.CadastroPagamentoContratual getDadosAtualizar(CadastroContrato.CadastroPagamentoContratual pagamento_antigo) {
		CadastroContrato.CadastroPagamentoContratual pagamento_a_inserir = new CadastroContrato.CadastroPagamentoContratual();
		pagamento_a_inserir.setId_pagamento(pagamento_antigo.getId_pagamento());
		String s_tipo = cBTipo.getSelectedItem().toString();

		int tipo = -1;

		if (s_tipo.equalsIgnoreCase("Normal")) {
			tipo = 1;
		} else if (s_tipo.equalsIgnoreCase("Comissão")) {
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

		return pagamento_a_inserir;

	}
	
	public CadastroContrato.CadastroPagamentoContratual getDadosSalvar() {
		CadastroContrato.CadastroPagamentoContratual pagamento_a_inserir = new CadastroContrato.CadastroPagamentoContratual();

		String s_tipo = cBTipo.getSelectedItem().toString();

		int tipo = -1;

		if (s_tipo.equalsIgnoreCase("Normal")) {
			tipo = 1;
		} else if (s_tipo.equalsIgnoreCase("Comissão")) {
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

		return pagamento_a_inserir;

	}


	public void setTelaPai(JDialog dialog) {
		this.telaPai = dialog;
	}

	public void setTelaPai(JFrame dialog) {
		this.telaPaiJFrame = dialog;
	}
	
	public CadastroContrato.CadastroTarefa criarTarefa(String nome_tarefa, String descricao, String msg) {

		CadastroContrato.CadastroTarefa tarefa = new CadastroContrato.CadastroTarefa();

		
			tarefa.setNome_tarefa(nome_tarefa);
		
		// cria a tarefa de insercao de contrato
		tarefa.setId_tarefa(0);
		tarefa.setDescricao_tarefa(descricao);

		tarefa.setStatus_tarefa(1);
		tarefa.setMensagem(msg);

		GetData data = new GetData();
		tarefa.setHora(data.getHora());
		tarefa.setData(data.getData());
		tarefa.setHora_agendada(data.getHora());
		tarefa.setData_agendada(data.getData());

		tarefa.setCriador(login);
		tarefa.setExecutor(login);

		tarefa.setPrioridade(1);

		return tarefa;

	}

	
}
