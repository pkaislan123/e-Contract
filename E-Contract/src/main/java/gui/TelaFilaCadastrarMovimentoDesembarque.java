package main.java.gui;

import java.awt.Window;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.border.MatteBorder;

import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroFilaMovimento;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroMensagem;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.Contato;
import main.java.classesExtras.CBProdutoPersonalizado;
import main.java.classesExtras.CBProdutoRenderPersonalizado;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoFilaMovimento;
import main.java.conexaoBanco.GerenciarBancoMensagem;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.manipular.Whatsapp;
import main.java.outros.DadosGlobais;
import main.java.tratamento_proprio.Log;

import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.text.Normalizer;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import javax.swing.JTextArea;
import javax.swing.JCheckBox;

public class TelaFilaCadastrarMovimentoDesembarque extends JFrame {

	private JPanel painelPrincipal;
	private JTextField entUmidade;
	private JTextField entImpureza;
	private JTextField entArdidos;
	private JComboBox cBProduto;
	private CBProdutoPersonalizado modelProduto = new CBProdutoPersonalizado();
	private CBProdutoRenderPersonalizado cBProdutoPersonalizado;
	private TelaFilaCadastrarMovimentoDesembarque isto;
	private CadastroCliente produtor;
	private CadastroProduto produto;
	CadastroCliente.Veiculo veiculo;
	private JLabel lblProdutor;
	private CadastroCliente motorista;
	private CadastroCliente transportadora;
	private JLabel lblMotorista;
	private JComboBox cbVeiculo;
	private JTextField entOrigem;
	private JTextField entDestino;
	private JCheckBox chckbxTemAutorizao, chBoxTemNF;
	private JTextArea entObservacoes;
	private int tipo_movimentacao_global = -1;
	private JLabel lblTransportadora;
	private JComboBox cbStatus;

	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;

	private JPanel painelPai;
	private JTextField entPesoBruto;
	private JTextField entPesoTara;
	private JTextField entPesoLiquido;

	private int flagEdicaoGlobal = -2;

	public TelaFilaCadastrarMovimentoDesembarque(int flagEdicao, CadastroFilaMovimento unidade, Window window) {

		painelPrincipal = new JPanel();
		painelPrincipal.setBackground(Color.WHITE);

		painelPai = new JPanel();
		painelPai.setBackground(Color.WHITE);
		flagEdicaoGlobal = flagEdicao;
		isto = this;
		this.setContentPane(painelPai);
		painelPai.setLayout(new MigLayout("", "[grow]", "[grow]"));

		JScrollPane scrollMaster = new JScrollPane(painelPrincipal);

		painelPai.add(scrollMaster, "cell 0 0,grow");

		getDadosGlobais();

		tipo_movimentacao_global = 0;

		setBounds(0, 0, 721, 702);
		painelPrincipal.setLayout(new MigLayout("", "[][::200px,grow][][::1px][][::200px,grow]",
				"[][][][][][][][][][][][][][][][::1px][][][][::1px][][][]"));

		JLabel lblNewLabel_3 = new JLabel("Movimentação:");
		lblNewLabel_3.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelPrincipal.add(lblNewLabel_3, "cell 0 0,alignx right");

		JLabel lblMovimentacao = new JLabel("New label");
		lblMovimentacao.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelPrincipal.add(lblMovimentacao, "cell 1 0,alignx center");

		JLabel lblStatusInicial = new JLabel("Status:");
		lblStatusInicial.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblStatusInicial, "cell 0 1,alignx trailing");

		cbStatus = new JComboBox();
		cbStatus.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelPrincipal.add(cbStatus, "cell 1 1 4 1,growx");
		cbStatus.addItem("Em Fila");
		cbStatus.addItem("Entrada");
		cbStatus.addItem("Saída");

		JLabel lblTipoTransportadora = new JLabel("Transportadora:");
		lblTipoTransportadora.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblTipoTransportadora, "cell 0 2,alignx right");

		lblTransportadora = new JLabel(" ");
		lblTransportadora.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblTransportadora.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelPrincipal.add(lblTransportadora, "cell 1 2 4 1,growx");

		JButton btnSelecionarTransportadora = new JButton("Selecionar");
		btnSelecionarTransportadora.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaTransportadores selecionar_transportador = new TelaTransportadores(2, isto);
				selecionar_transportador.setFlag_motorista_transportadora_global(1);
				selecionar_transportador.setVisible(true);
			}
		});
		btnSelecionarTransportadora.setForeground(Color.WHITE);
		btnSelecionarTransportadora.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnSelecionarTransportadora.setBackground(Color.BLUE);
		painelPrincipal.add(btnSelecionarTransportadora, "cell 5 2");

		JLabel lblNewLabel = new JLabel("Motorista:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblNewLabel, "cell 0 3,alignx trailing");

		lblMotorista = new JLabel(" ");
		lblMotorista.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		lblMotorista.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelPrincipal.add(lblMotorista, "cell 1 3 4 1,growx");

		JButton btnSelecionarMotorista = new JButton("Selecionar");
		btnSelecionarMotorista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaTransportadores selecionar_transportador = new TelaTransportadores(2, isto);
				selecionar_transportador.setFlag_motorista_transportadora_global(0);
				selecionar_transportador.setVisible(true);
			}
		});
		btnSelecionarMotorista.setBackground(Color.BLUE);
		btnSelecionarMotorista.setForeground(Color.WHITE);
		btnSelecionarMotorista.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelPrincipal.add(btnSelecionarMotorista, "cell 5 3,aligny bottom");

		JLabel lblVeculo = new JLabel("Veículo:");
		lblVeculo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblVeculo, "cell 0 4,alignx trailing");

		cbVeiculo = new JComboBox();
		painelPrincipal.add(cbVeiculo, "cell 1 4 3 1,growx");

		JLabel lblProdutorTransportadora = new JLabel("Produtor:");
		lblProdutorTransportadora.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblProdutorTransportadora, "cell 0 6,alignx right");

		lblProdutor = new JLabel(" ");
		lblProdutor.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblProdutor.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelPrincipal.add(lblProdutor, "cell 1 6 4 1,growx");

		JButton btnSelecionarProdutor = new JButton("Selecionar");
		btnSelecionarProdutor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaCliente tela = new TelaCliente(0, 100, isto);
				tela.setVisible(true);

			}
		});
		btnSelecionarProdutor.setForeground(Color.WHITE);
		btnSelecionarProdutor.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnSelecionarProdutor.setBackground(Color.BLUE);
		painelPrincipal.add(btnSelecionarProdutor, "cell 5 6");

		JLabel lblNewLabel_1_1 = new JLabel("Produto:");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblNewLabel_1_1, "cell 0 8,alignx trailing");

		cBProduto = new JComboBox();
		painelPrincipal.add(cBProduto, "cell 1 8 4 1,growx");
		cBProdutoPersonalizado = new CBProdutoRenderPersonalizado();
		cBProduto.setModel(modelProduto);
		cBProduto.setRenderer(cBProdutoPersonalizado);

		GerenciarBancoProdutos listaProdutos = new GerenciarBancoProdutos();
		ArrayList<CadastroProduto> produtos = listaProdutos.getProdutos();

		for (CadastroProduto produto : produtos) {
			modelProduto.addProduto(produto);

		}

		JLabel lblNewLabel_1_1_1 = new JLabel("Classificação Prévia");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		painelPrincipal.add(lblNewLabel_1_1_1, "cell 0 10 3 1,alignx center");

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setBackground(Color.BLACK);
		painelPrincipal.add(lblNewLabel_1, "cell 3 10 1 5,grow");

		JLabel lblNewLabel_1_1_1_1 = new JLabel("Pesagem");
		lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		painelPrincipal.add(lblNewLabel_1_1_1_1, "cell 4 10 2 1,alignx center");

		JLabel lblNewLabel_2 = new JLabel("Umidade:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblNewLabel_2, "cell 0 11 1 2,alignx trailing");

		entUmidade = new JTextField();
		entUmidade.setText("0.0");
		entUmidade.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		entUmidade.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelPrincipal.add(entUmidade, "cell 1 11 2 2,growx");
		entUmidade.setColumns(10);

		JLabel lblNewLabel_2_2 = new JLabel("Peso Bruto Total:");
		lblNewLabel_2_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblNewLabel_2_2, "cell 4 12,alignx trailing");

		entPesoBruto = new JTextField();
		entPesoBruto.setText("0.0");
		entPesoBruto.setFont(new Font("SansSerif", Font.BOLD, 16));
		entPesoBruto.setColumns(10);
		entPesoBruto.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		painelPrincipal.add(entPesoBruto, "cell 5 12,growx");

		JLabel lblNewLabel_2_1 = new JLabel("Impureza:");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblNewLabel_2_1, "cell 0 13,alignx trailing");

		entImpureza = new JTextField();
		entImpureza.setText("0.0");
		entImpureza.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		entImpureza.setFont(new Font("SansSerif", Font.BOLD, 16));
		entImpureza.setColumns(10);
		painelPrincipal.add(entImpureza, "cell 1 13 2 1,growx");

		JLabel lblNewLabel_2_2_1 = new JLabel("Peso Tara:");
		lblNewLabel_2_2_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblNewLabel_2_2_1, "cell 4 13,alignx trailing");

		entPesoTara = new JTextField();
		entPesoTara.setText("0.0");
		entPesoTara.setFont(new Font("SansSerif", Font.BOLD, 16));
		entPesoTara.setColumns(10);
		entPesoTara.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		painelPrincipal.add(entPesoTara, "cell 5 13,growx");

		JLabel lblNewLabel_2_1_1 = new JLabel("Ardidos:");
		lblNewLabel_2_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblNewLabel_2_1_1, "cell 0 14,alignx trailing");

		entArdidos = new JTextField();
		entArdidos.setText("0.0");
		entArdidos.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		entArdidos.setFont(new Font("SansSerif", Font.BOLD, 16));
		entArdidos.setColumns(10);
		painelPrincipal.add(entArdidos, "cell 1 14 2 1,growx");

		JLabel lblNewLabel_2_2_1_1 = new JLabel("Peso Liquido:");
		lblNewLabel_2_2_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblNewLabel_2_2_1_1, "cell 4 14,alignx trailing");

		entPesoLiquido = new JTextField();
		entPesoLiquido.setText("0.0");
		entPesoLiquido.setFont(new Font("SansSerif", Font.BOLD, 16));
		entPesoLiquido.setColumns(10);
		entPesoLiquido.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		painelPrincipal.add(entPesoLiquido, "cell 5 14,growx");

		JLabel lblNewLabel_1_2 = new JLabel("");
		lblNewLabel_1_2.setOpaque(true);
		lblNewLabel_1_2.setBackground(Color.BLACK);
		painelPrincipal.add(lblNewLabel_1_2, "cell 0 15 6 1,grow");

		JLabel lblNewLabel_2_1_1_1_1_1_2 = new JLabel("Origem e Destino");
		lblNewLabel_2_1_1_1_1_1_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		painelPrincipal.add(lblNewLabel_2_1_1_1_1_1_2, "cell 1 16,alignx center");

		JLabel lblNewLabel_1_3 = new JLabel("");
		lblNewLabel_1_3.setOpaque(true);
		lblNewLabel_1_3.setBackground(Color.BLACK);
		painelPrincipal.add(lblNewLabel_1_3, "cell 3 16 1 3,grow");

		JLabel lblNewLabel_2_1_1_1_1_1 = new JLabel("Autorizações e Notas");
		lblNewLabel_2_1_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		painelPrincipal.add(lblNewLabel_2_1_1_1_1_1, "cell 4 16 2 1,alignx center");

		JLabel lblNewLabel_2_1_1_1 = new JLabel("Origem:");
		lblNewLabel_2_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblNewLabel_2_1_1_1, "cell 0 17,alignx trailing");

		entOrigem = new JTextField();
		entOrigem.setFont(new Font("SansSerif", Font.BOLD, 16));
		entOrigem.setColumns(10);
		entOrigem.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		painelPrincipal.add(entOrigem, "cell 1 17 2 1,growx");

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		painelPrincipal.add(panel, "cell 4 17 2 1,alignx center,growy");
		panel.setLayout(new MigLayout("", "[][][]", "[]"));

		chBoxTemNF = new JCheckBox("Tem Nota Fiscal");
		panel.add(chBoxTemNF, "cell 0 0");
		chBoxTemNF.setFont(new Font("SansSerif", Font.BOLD, 16));

		chckbxTemAutorizao = new JCheckBox("Tem Autorização");
		panel.add(chckbxTemAutorizao, "cell 2 0");
		chckbxTemAutorizao.setFont(new Font("SansSerif", Font.BOLD, 16));

		JLabel lblNewLabel_2_1_1_1_1 = new JLabel("Destino:");
		lblNewLabel_2_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblNewLabel_2_1_1_1_1, "cell 0 18,alignx trailing");

		entDestino = new JTextField();
		entDestino.setFont(new Font("SansSerif", Font.BOLD, 16));
		entDestino.setColumns(10);
		entDestino.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		painelPrincipal.add(entDestino, "cell 1 18 2 1,growx");

		JLabel lblNewLabel_1_2_1 = new JLabel("");
		lblNewLabel_1_2_1.setOpaque(true);
		lblNewLabel_1_2_1.setBackground(Color.BLACK);
		painelPrincipal.add(lblNewLabel_1_2_1, "cell 0 19 6 1,grow");

		JLabel lblNewLabel_2_1_1_1_1_1_1 = new JLabel("Observações:");
		lblNewLabel_2_1_1_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblNewLabel_2_1_1_1_1_1_1, "cell 0 20,alignx right");

		entObservacoes = new JTextArea();
		entObservacoes.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		entObservacoes.setWrapStyleWord(true);
		entObservacoes.setLineWrap(true);
		entObservacoes.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelPrincipal.add(entObservacoes, "cell 1 20 5 2,grow");

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// salvar

				CadastroFilaMovimento unidade = getDadosSalvar();
				if (unidade != null) {
					GerenciarBancoFilaMovimento gerenciar = new GerenciarBancoFilaMovimento();
					int marcado = gerenciar.inserirCaminhaoNaFila(unidade);
					if (marcado > -1) {
						JOptionPane.showMessageDialog(isto, "Vez Marcada!");

						if (unidade.getTipo_movimentacao() == 0) {
							((TelaFila) window).pesquisar_fila_desembarque();

						}

						// enviar notificacao
						Whatsapp zap = new Whatsapp();

						String nome_motorista = "";
						if (unidade.getMotorista().getTipo_pessoa() == 0) {
							// pessoa fisica
							nome_motorista = (unidade.getMotorista().getNome_empresarial().toUpperCase().trim());

						} else {
							nome_motorista = (unidade.getMotorista().getNome_fantaia().toUpperCase().trim());

						}

						String nome_produtor = "";
						if (unidade.getProdutor().getTipo_pessoa() == 0) {
							// pessoa fisica
							nome_produtor = (unidade.getProdutor().getNome_empresarial().toUpperCase().trim());

						} else {
							nome_produtor = (unidade.getProdutor().getNome_fantaia().toUpperCase().trim());

						}

						String placa = unidade.getVeiculo().getPlaca_trator().toUpperCase();

						String produto = unidade.getProduto().getNome_produto().toUpperCase();

						int num_veiculos_na_frente = ((TelaFila) window).unidadesNaFrenteDesembarque();
						long tempoMedioEspera = ((TelaFila) window).longTempoMedioEsperoDesembarque();

						long previsao = tempoMedioEspera;
						if (num_veiculos_na_frente > 0) {
							long num_veiculos_media = (long) num_veiculos_na_frente / 5;
							previsao = tempoMedioEspera * ((long) num_veiculos_media);

						}
						LocalDateTime agora = LocalDateTime.now();
						agora = agora.plusMinutes(previsao);
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

						String sPrevisao = agora.format(formatter);

						String sTempoMedioEspera = LocalTime.MIN.plus(Duration.ofMinutes(tempoMedioEspera)).toString()
								+ " horas por caminhao";

						String mensagem_enviar = "Mensagem Automatica dos Servidores da LD Armazens Gerais, nao e necessario responder! \\n \\n"
								+ nome_produtor + ", envio essa mensagem para notifica-lo que o motorista "
								+ nome_motorista + " no veiculo placa " + placa + " entrou na fila de desembarque de "
								+ produto + ". \\n \\n->Ha " + num_veiculos_na_frente
								+ " caminhoes na frente da fila \\n \\n->Tempo Medio de Espera: " + sTempoMedioEspera
								+ " \\n \\n ->Previsao de Desembarque: "
								+ (previsao == 0 ? " Sem dados disponiveis" : sPrevisao);

						ArrayList<Contato> lista_contratos = pesquisarContatos(unidade.getProdutor().getId());

						for (Contato contato : lista_contratos) {
							try {
								boolean retorno2 = zap.enviarMensagem(contato.getCelular(), mensagem_enviar);

								if (retorno2) { // mensagem enviada
									boolean notificado = gerenciar.emFilaNotificado(marcado); // notificacao mudada
									JOptionPane.showMessageDialog(isto,
											"Notificação de Entrada em Fila Enviada ao número: "
													+ contato.getCelular());
								} else {
									JOptionPane.showMessageDialog(isto,
											"Notificação não enviada, tente manualmente! Consulte a disponibilidade da Api Chat Pro");

								}
							} catch (Exception t) {
								t.printStackTrace();
							}
						}

						if (unidade.getTipo_movimentacao() == 0) {
							((TelaFila) window).pesquisar_fila_desembarque();

						}
						isto.dispose();
					}
				}

			}
		});

		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Whatsapp zap = new Whatsapp();

				GerenciarBancoFilaMovimento gerenciar = new GerenciarBancoFilaMovimento();
				CadastroFilaMovimento nova_unidade = getDadosAtualizar(unidade);
				boolean marcado = gerenciar.atualizarUnidade(nova_unidade);
				if (marcado) {

					JOptionPane.showMessageDialog(isto, "Atualizado!");

					String nome_motorista = "";
					String nome_produtor = "";

					if (nova_unidade.getProdutor().getTipo_pessoa() == 0) {
						nome_produtor = nova_unidade.getProdutor().getNome_empresarial().toUpperCase();

					} else {
						nome_produtor = nova_unidade.getProdutor().getNome_fantaia().toUpperCase();

					}

					if (nova_unidade.getProdutor().getTipo_pessoa() == 0) {
						nome_motorista = nova_unidade.getMotorista().getNome_empresarial().toUpperCase();

					} else {
						nome_motorista = nova_unidade.getMotorista().getNome_fantaia().toUpperCase();

					}

					String placa = nova_unidade.getVeiculo().getPlaca_trator().toUpperCase();

					String produto = nova_unidade.getProduto().getNome_produto().toUpperCase();
					ArrayList<Contato> lista_contratos = pesquisarContatos(nova_unidade.getProdutor().getId());

					if (flagEdicaoGlobal == 2) {

						gerenciar.entradaAtualizada(nova_unidade.getId());

						String mensagem_enviar = "Mensagem Automatica dos Servidores da LD Armazens Gerais, nao e necessario responder! \\n \\n"
								+ nome_produtor + ", envio essa mensagem para notifica-lo que o motorista "
								+ nome_motorista + " no veiculo placa " + placa + " entrou no armazém para desembarque "
								+ produto + ".";

						for (Contato contato : lista_contratos) {
							try {
								boolean retorno2 = zap.enviarMensagem(contato.getCelular(), mensagem_enviar);

								if (retorno2) { // mensagem enviada
									boolean notificado = gerenciar.entradaNotificada(nova_unidade.getId()); // notificacao
									// mudada
									JOptionPane.showMessageDialog(isto,
											"Notificação de entrada no armazém para Desembarque Enviada ao número: "
													+ contato.getCelular());
								} else {
									JOptionPane.showMessageDialog(isto,
											"Notificação não enviada, tente manualmente! Consulte a disponibilidade da Api Chat Pro");

								}
							} catch (Exception t) {
								t.printStackTrace();
							}
						}

					} else if (flagEdicaoGlobal == 3) {

						gerenciar.saidaAtualizada(nova_unidade.getId());

						String mensagem_enviar = "Mensagem Automatica dos Servidores da LD Armazens Gerais, nao e necessario responder! \\n \\n"
								+ nome_produtor + ", envio essa mensagem para notifica-lo que o motorista "
								+ nome_motorista + " no veiculo placa " + placa + " completou o desembarque de "
								+ produto + ".";
						
						
						mensagem_enviar = mensagemAdicional(mensagem_enviar);

						for (Contato contato : lista_contratos) {
							try {
								boolean retorno2 = zap.enviarMensagem(contato.getCelular(), mensagem_enviar);

								if (retorno2) { // mensagem enviada
									boolean notificado = gerenciar.saidaNotificado(unidade.getId()); // notificacao
																										// mudada
									JOptionPane.showMessageDialog(isto,
											"Notificação de Desembarque Completo Enviada ao número: "
													+ contato.getCelular());
								} else {
									JOptionPane.showMessageDialog(isto,
											"Notificação não enviada, tente manualmente! Consulte a disponibilidade da Api Chat Pro");

								}
							} catch (Exception t) {
								t.printStackTrace();
							}
						}

					}

					if (unidade.getTipo_movimentacao() == 0) {
						((TelaFila) window).pesquisar_fila_desembarque();

					}

					isto.dispose();
				}

			}
		});
		btnAtualizar.setForeground(Color.WHITE);
		btnAtualizar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnAtualizar.setBackground(new Color(0, 0, 51));
		painelPrincipal.add(btnAtualizar, "flowx,cell 5 22");
		btnSalvar.setBackground(new Color(0, 51, 51));
		btnSalvar.setForeground(Color.WHITE);
		btnSalvar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(btnSalvar, "cell 5 22,alignx right");

		lblMovimentacao.setText("Desembarque");
		chckbxTemAutorizao.setText("Tem Autorização de Desembarque");

		lblTipoTransportadora.setEnabled(false);
		lblTipoTransportadora.setVisible(false);

		btnSelecionarTransportadora.setEnabled(false);
		btnSelecionarTransportadora.setVisible(false);

		lblTransportadora.setEnabled(false);
		lblTransportadora.setVisible(false);

		if (flagEdicao == 0) {
			this.setTitle("Marcar Desembarque");
			cbStatus.setEnabled(false);

			btnAtualizar.setEnabled(false);
			btnAtualizar.setVisible(false);

		} else if (flagEdicao == 1) {
			this.setTitle("Editar Marcação de Desembarque");
			cbStatus.setEnabled(false);
			rotinasEdicao(unidade);

			btnSalvar.setEnabled(false);
			btnSalvar.setVisible(false);

		} else if (flagEdicao == 2) {
			this.setTitle("Avançar para Entrada de Desembarque");
			cbStatus.setEnabled(false);
			rotinasEdicao(unidade);
			btnSalvar.setEnabled(false);
			btnSalvar.setVisible(false);

		} else if (flagEdicao == 3) {
			this.setTitle("Avançar para Saída de Desembarque");
			cbStatus.setEnabled(false);
			rotinasEdicao(unidade);
			btnSalvar.setEnabled(false);
			btnSalvar.setVisible(false);
		}

		this.setLocationRelativeTo(window);

	}

	public void rotinasEdicao(CadastroFilaMovimento unidade) {

		cbStatus.setSelectedIndex(unidade.getStatus());

		setProdutor(unidade.getProdutor());
		pesquisarContatos(unidade.getProdutor().getId());
		setProduto(unidade.getProduto());

		setMotorista(unidade.getMotorista());

		setTransportadora(unidade.getTransportadora());

		if (unidade.getTem_nf() == 1) {
			chBoxTemNF.setSelected(true);
		}

		if (unidade.getAutorizacao_movimentacao() == 1) {
			chckbxTemAutorizao.setSelected(true);

		}

		entOrigem.setText(unidade.getOrigem());
		entDestino.setText(unidade.getDestino());
		entObservacoes.setText(unidade.getObservacao());

		// classificacao
		entUmidade.setText(Double.toString(unidade.getUmidade()));
		entImpureza.setText(Double.toString(unidade.getImpureza()));
		entArdidos.setText(Double.toString(unidade.getArdidos()));

		// pesagem

		entPesoBruto.setText(Double.toString(unidade.getPeso_bruto()));
		entPesoTara.setText(Double.toString(unidade.getPeso_tara()));
		entPesoLiquido.setText(Double.toString(unidade.getPeso_liquido()));

		// veiculo
		setVeiculo(unidade.getVeiculo());
	}

	public void setProduto(CadastroProduto _produto) {
		this.produto = _produto;

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				modelProduto.setSelectedItem(_produto);

			}
		});
	}

	public void setVeiculo(CadastroCliente.Veiculo _veiculo) {
		this.veiculo = _veiculo;

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {

				String sveiculo = veiculo.getId_veiculo() + "-" + veiculo.getPlaca_trator();
				cbVeiculo.setSelectedItem(sveiculo);

			}
		});
	}

	public void setProdutor(CadastroCliente cliente) {
		this.produtor = cliente;

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				if (cliente.getTipo_pessoa() == 0) {
					lblProdutor.setText(produtor.getNome_empresarial().toUpperCase().trim());
				} else {
					lblProdutor.setText(cliente.getRazao_social().toUpperCase());
				}

				lblProdutor.repaint();
				lblProdutor.updateUI();

				if (flagEdicaoGlobal == 0)
					entOrigem.setText(cliente.getRua() + ", nº " + cliente.getNumero() + ", Bairro: "
							+ cliente.getBairro() + ", Cidade/UF: " + cliente.getCidade() + "/" + cliente.getUf());

			}
		});

	}

	public ArrayList<Contato> pesquisarContatos(int id_cliente) {
		GerenciarBancoClientes gerenciar = new GerenciarBancoClientes();
		ArrayList<Contato> lista_contatos = gerenciar.getContatos(id_cliente);

		ArrayList<Contato> lista_contatos_aceita = new ArrayList<>();

		for (Contato contato : lista_contatos) {
			if (contato != null) {
				String celular = contato.getCelular();
				if (celular != null && celular.length() == 11) {
					// celular aceito
					System.out.println("Celular:" + celular);

					lista_contatos_aceita.add(contato);

				}
			}
		}

		return lista_contatos_aceita;
	}

	public void setMotorista(CadastroCliente _motorista) {

		this.motorista = _motorista;
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {

				if (_motorista.getTipo_pessoa() == 0) {
					// pessoa fisica
					lblMotorista.setText(_motorista.getNome_empresarial().toUpperCase().trim());

				} else {
					lblMotorista.setText(_motorista.getNome_fantaia().toUpperCase().trim());

				}

				cbVeiculo.removeAllItems();

				for (CadastroCliente.Veiculo veiculo : _motorista.getVeiculos()) {
					cbVeiculo.addItem(veiculo.getId_veiculo() + "-" + veiculo.getPlaca_trator());
				}

				cbVeiculo.repaint();
				cbVeiculo.updateUI();

			}
		});

	}

	public void setTransportadora(CadastroCliente _transportador) {

		this.transportadora = _transportador;
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {

				if (transportadora != null) {

					if (_transportador.getTipo_pessoa() == 0) {
						// pessoa fisica
						lblTransportadora.setText(_transportador.getNome_empresarial().toUpperCase().trim());

					} else {
						lblTransportadora.setText(_transportador.getNome_fantaia().toUpperCase().trim());

					}
				}

			}
		});

	}

	public CadastroFilaMovimento getDadosAtualizar(CadastroFilaMovimento antigo) {
		CadastroFilaMovimento unidade = new CadastroFilaMovimento();

		unidade.setId(antigo.getId());
		unidade.setStatus(antigo.getStatus());
		unidade.setTipo_movimentacao(tipo_movimentacao_global);

		if (tipo_movimentacao_global == 1) {
			if (transportadora == null) {
				JOptionPane.showMessageDialog(isto, "Informe a Transportadora");
				return null;
			}
		} else {
			transportadora = new CadastroCliente();
			transportadora.setId(0);

		}
		unidade.setTransportadora(transportadora);

		if (motorista == null) {
			JOptionPane.showMessageDialog(isto, "Informe o Motorista");
			return null;
		}
		unidade.setMotorista(motorista);

		String s_veiculo = cbVeiculo.getSelectedItem().toString();
		String separados[] = s_veiculo.split("-");
		int id_veiculo = Integer.parseInt(separados[0]);

		boolean veiculo_marcado = false;
		for (CadastroCliente.Veiculo veiculo : motorista.getVeiculos()) {
			if (veiculo.getId_veiculo() == id_veiculo) {
				unidade.setVeiculo(veiculo);
				veiculo_marcado = true;
				break;
			}

		}

		if (!veiculo_marcado) {
			JOptionPane.showMessageDialog(isto, "Informe o Veículo");
			return null;
		}

		if (produtor == null) {
			JOptionPane.showMessageDialog(isto, "Informe o Produtor/Cliente");
			return null;
		}
		unidade.setProdutor(produtor);

		CadastroProduto produto = (CadastroProduto) modelProduto.getSelectedItem();
		unidade.setProduto(produto);

		unidade.setStatus(cbStatus.getSelectedIndex());

		// classificacao previa
		double umidade = 0, impureza = 0, ardidos = 0;
		double peso_bruto = 0, peso_tara = 0, peso_liquido = 0;

		try {
			umidade = Double.parseDouble(entUmidade.getText());
			unidade.setUmidade(umidade);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(isto, "Umidade com valor Inválido, revise!");
			return null;
		}

		try {
			impureza = Double.parseDouble(entImpureza.getText());
			unidade.setImpureza(impureza);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(isto, "Impureza com valor Inválido, revise!");
			return null;
		}

		try {
			ardidos = Double.parseDouble(entArdidos.getText());
			unidade.setArdidos(ardidos);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(isto, "Ardidos com valor Inválido, revise!");
			return null;
		}

		// pesos
		try {
			peso_bruto = Double.parseDouble(entPesoBruto.getText());
			unidade.setPeso_bruto(peso_bruto);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(isto, "Peso Bruto Total com valor Inválido, revise!");
			return null;
		}

		try {
			peso_tara = Double.parseDouble(entPesoTara.getText());
			unidade.setPeso_tara(peso_tara);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(isto, "Peso Tara com valor Inválido, revise!");
			return null;
		}

		try {
			peso_liquido = Double.parseDouble(entPesoLiquido.getText());
			unidade.setPeso_liquido(peso_liquido);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(isto, "Peso Liquido com valor Inválido, revise!");
			return null;
		}

		unidade.setObservacao(entObservacoes.getText());
		unidade.setOrigem(entOrigem.getText());
		unidade.setDestino(entDestino.getText());

		if (chBoxTemNF.isSelected())
			unidade.setTem_nf(1);
		else
			unidade.setTem_nf(0);

		if (chckbxTemAutorizao.isSelected())
			unidade.setAutorizacao_movimentacao(1);
		else
			unidade.setAutorizacao_movimentacao(0);

		unidade.setLogin(login);

		return unidade;
	}

	public CadastroFilaMovimento getDadosSalvar() {
		CadastroFilaMovimento unidade = new CadastroFilaMovimento();

		unidade.setTipo_movimentacao(tipo_movimentacao_global);
		if (tipo_movimentacao_global == 1) {
			if (transportadora == null) {
				JOptionPane.showMessageDialog(isto, "Informe a Transportadora");
				return null;
			}
		} else {
			transportadora = new CadastroCliente();
			transportadora.setId(0);

		}
		unidade.setTransportadora(transportadora);

		if (motorista == null) {
			JOptionPane.showMessageDialog(isto, "Informe o Motorista");
			return null;
		}
		unidade.setMotorista(motorista);

		String s_veiculo = cbVeiculo.getSelectedItem().toString();
		String separados[] = s_veiculo.split("-");
		int id_veiculo = Integer.parseInt(separados[0]);

		boolean veiculo_marcado = false;
		for (CadastroCliente.Veiculo veiculo : motorista.getVeiculos()) {
			if (veiculo.getId_veiculo() == id_veiculo) {
				unidade.setVeiculo(veiculo);
				veiculo_marcado = true;
				break;
			}

		}

		if (!veiculo_marcado) {
			JOptionPane.showMessageDialog(isto, "Informe o Veículo");
			return null;
		}

		if (produtor == null) {
			JOptionPane.showMessageDialog(isto, "Informe o Produtor/Cliente");
			return null;
		}
		unidade.setProdutor(produtor);

		CadastroProduto produto = (CadastroProduto) modelProduto.getSelectedItem();
		unidade.setProduto(produto);

		unidade.setStatus(cbStatus.getSelectedIndex());

		// classificacao previa
		double umidade = 0, impureza = 0, ardidos = 0;
		double peso_bruto = 0, peso_tara = 0, peso_liquido = 0;

		try {
			umidade = Double.parseDouble(entUmidade.getText());
			unidade.setUmidade(umidade);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(isto, "Umidade com valor Inválido, revise!");
			return null;
		}

		try {
			impureza = Double.parseDouble(entImpureza.getText());
			unidade.setImpureza(impureza);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(isto, "Impureza com valor Inválido, revise!");
			return null;
		}

		try {
			ardidos = Double.parseDouble(entArdidos.getText());
			unidade.setArdidos(ardidos);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(isto, "Ardidos com valor Inválido, revise!");
			return null;
		}

		// pesos
		try {
			peso_bruto = Double.parseDouble(entPesoBruto.getText());
			unidade.setPeso_bruto(peso_bruto);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(isto, "Peso Bruto Total com valor Inválido, revise!");
			return null;
		}

		try {
			peso_tara = Double.parseDouble(entPesoTara.getText());
			unidade.setPeso_tara(peso_tara);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(isto, "Peso Tara com valor Inválido, revise!");
			return null;
		}

		try {
			peso_liquido = Double.parseDouble(entPesoLiquido.getText());
			unidade.setPeso_liquido(peso_liquido);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(isto, "Peso Liquido com valor Inválido, revise!");
			return null;
		}

		unidade.setObservacao(entObservacoes.getText());
		unidade.setOrigem(entOrigem.getText());
		unidade.setDestino(entDestino.getText());

		if (chBoxTemNF.isSelected())
			unidade.setTem_nf(1);
		else
			unidade.setTem_nf(0);

		if (chckbxTemAutorizao.isSelected())
			unidade.setAutorizacao_movimentacao(1);
		else
			unidade.setAutorizacao_movimentacao(0);

		unidade.setLogin(login);

		return unidade;
	}

	public void getDadosGlobais() {
		// gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		GerenciadorLog = dados.getGerenciadorLog();
		configs_globais = dados.getConfigs_globais();

		// usuario logado
		login = dados.getLogin();

	}
	
	
public String mensagemAdicional(String mensagem_a_enviar) {
		
		CadastroMensagem msg_adicional = new GerenciarBancoMensagem().getMensagem();
		if(msg_adicional != null) {
			System.out.println("mensagem adicional nao é nula");
			if(msg_adicional.getConteudo() != null && msg_adicional.getConteudo().length() > 0 ) {
				System.out.println("conteudo da mensagem adicional nao é nula");

				 String mensagem_adicional = msg_adicional.getConteudo();
				 mensagem_adicional= Normalizer.normalize(mensagem_adicional, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
				  
				  String mensagem_adicional_quebrada[] = mensagem_adicional.split("\n");
				  String mensagem_adicional_final  = "";
				  for(int i = 0; i < mensagem_adicional_quebrada.length; i++) {
					  mensagem_adicional_final = mensagem_adicional_final + mensagem_adicional_quebrada[i] + "\\n";
				  }
				  mensagem_adicional = mensagem_adicional_final;
				  
				  mensagem_a_enviar += ("\\n" + mensagem_adicional);
				  return mensagem_a_enviar;
				  
				  
			}else {
				System.out.println("conteudo da mensagem adicional  é nula");

				return mensagem_a_enviar;

			}
		
	
		
		}else {
			System.out.println("mensagem adicional é nula");

			return mensagem_a_enviar;
		}

	}

}
