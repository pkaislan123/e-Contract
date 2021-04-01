package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.Window;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import main.java.cadastros.CadastroAviso;
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
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.classesExtras.Endereco;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoDocumento;
import main.java.conexaoBanco.GerenciarBancoNotasFiscais;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoRomaneios;
import main.java.conexaoBanco.GerenciarBancoSafras;
import main.java.gui.TelaCadastroCliente;
import main.java.gui.TelaMain;
import main.java.gui.TelaRomaneios;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.manipular.ConverterPdf;
import main.java.manipular.CopiarArquivo;
import main.java.manipular.ManipularNotasFiscais;
import main.java.manipular.ManipularRomaneios;
import main.java.manipular.ManipularTxt;
import main.java.outros.BuscarCep;
import main.java.outros.DadosGlobais;
import main.java.outros.GetData;
import main.java.outros.GetHttp;
import main.java.outros.GetSintegra;
import main.java.outros.JPanelBackground;
import main.java.outros.JPanelTransparent;
import main.java.outros.JTextFieldPersonalizado;
import main.java.outros.MyFileVisitor;
import main.java.outros.TratarDados;
import main.java.relatoria.RelatorioContratoComprador;
import main.java.relatoria.RelatorioContratoSimplificado;
import main.java.relatoria.RelatorioContratos;
import main.java.tratamento_proprio.Log;
import main.java.views_personalizadas.TelaEmEspera;
import main.java.views_personalizadas.TelaNotificacaoSuperiorModoBusca;
import outros.ValidaCNPJ;
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

import javax.swing.border.LineBorder;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;

public class TelaRelatoriaContratos extends JDialog {

	protected static final AbstractButton chkboxValorComissao = null;
	private final JPanel painelPrincipal = new JPanel();
	private ComboBoxRenderPersonalizado cBSafraPersonalizado;
	private ComboBoxPersonalizado modelSafra = new ComboBoxPersonalizado();
	private JComboBox cBSafra;
	private CadastroGrupo grupo_alvo;
	private CadastroCliente cliente_alvo;
	private JComboBox cBAlvo;
	private JCheckBox chkBoxContratos, chkBoxContratosComoComprador, chkBoxContratosComoVendedor,
			chckbxIncluirSubContratos, chkBoxContratosComoCorretor, chckbxPagamentos, chkBoxComoDepositante,
			chkBoxComoFavorecido, chckbxCarregamentos, chckbxControleNfVendaEntrada, chckbxCarregamentpComoVendedor,
			chckbxCarregamentoComoComprador, chckbxIncluirTransfrenciasPagamentos, chckbxUnirPagamentos,
			chkbxIxibirContratosSemPagamentos,chkBoxUnirContratos;

	private static ArrayList<CadastroSafra> safras = new ArrayList<>();
	private JCheckBox chckbxTodasAsSafras;
	private JCheckBox chckbxIncluirValorComissao;
	private JCheckBox chckbxIncluirGanhosPotenciais;
	private JLabel lblNewLabel_3;
	private JCheckBox chckbxInterno, chckbxExterno;
	private JCheckBox chckbxSomarSubContratos;
	private JLabel lblNewLabel_4;
	private JPanel painelOpcaosInternas;
	private JLabel lblNewLabel_5;

	private JCheckBox chckbxRecebimentos, chckbxRecebimentoComoComprador, chckbxRecebimentoComoVendedor;
	private JCheckBox chckbxUnirRecebimentos;
	private JPanel panel;
	private JPanel panel_1;
	private JCheckBox chckbxControleNfVendaSaida;
	private JCheckBox chckbxUnirCarregamentos, chckbxIncluirTransfrenciasCarregamentos;
	private JPanel panel_2;
	private JCheckBox chkbxIxibirContratosSemCarregamentos;
	private JCheckBox chkbxIxibirContratosSemRecebimentos;

	public static void pesquisarSafras() {
		GerenciarBancoSafras listaSafras = new GerenciarBancoSafras();
		safras = listaSafras.getSafras();
	}

	public TelaRelatoriaContratos(Window janela_pai) {
		// setModal(true);

		TelaRelatoriaContratos isto = this;

		setResizable(true);
		setTitle("E-Contract - Relatoria");

		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1205, 715);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);

		JLabel lblNewLabel = new JLabel("Safra:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(24, 66, 46, 14);
		painelPrincipal.add(lblNewLabel);

		cBSafraPersonalizado = new ComboBoxRenderPersonalizado();

		cBSafra = new JComboBox();
		cBSafra.setEnabled(false);
		cBSafra.setModel(modelSafra);
		cBSafra.setRenderer(cBSafraPersonalizado);
		cBSafra.setBounds(92, 58, 316, 28);
		painelPrincipal.add(cBSafra);

		cBSafra.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CadastroSafra produto = (CadastroSafra) modelSafra.getSelectedItem();

			}

		});

		JLabel lblNewLabel_1 = new JLabel("Cliente:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(24, 102, 46, 14);
		painelPrincipal.add(lblNewLabel_1);

		cBAlvo = new JComboBox();
		cBAlvo.setBounds(92, 97, 217, 28);
		painelPrincipal.add(cBAlvo);

		JButton btnNewButton = new JButton("Gerar Relatorio Completo");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int tipo_contrato = -1;
				boolean gerar = false;
				boolean somar_sub_contratos = false;
				boolean incluir_comissao = false;
				boolean incluir_ganhos_potencias = false;
				boolean sub_contratos = false;
				boolean contrato = false, contrato_como_comprador = false, contrato_como_vendedor = false,
						contrato_como_corretor = false, unir_contratos = false;
				boolean pagamento = false, pagamento_como_despositante = false, pagamento_como_favorecido = false;
				boolean carregamento = false, carregamento_como_comprador = false, carregamento_como_vendedor = false;
				boolean recebimento = false, recebimento_como_comprador = false, recebimento_como_vendedor = false,
						unir_recebimentos = false;
				boolean unir_carregamentos = false;
				boolean controle_nf_venda_recebimentos = false;
				boolean controle_nf_venda_carregamentos = false;
				boolean incluir_transferencia_carregamentos = false;
				boolean incluir_sem_pagamentos = false;
				boolean incluir_sem_carregamentos = false;
				boolean incluir_sem_recebimentos = false;
				Date hoje = new Date();
				SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

				int id_safra = -1;

				if (chckbxTodasAsSafras.isSelected()) {
					// todas as safras selecionadas
					id_safra = 0;
					gerar = true;
				} else {
					CadastroSafra safra = (CadastroSafra) modelSafra.getSelectedItem();
					if (safra == null) {
						JOptionPane.showMessageDialog(isto, "Marque a caixa Todas as safras ou\nSelecione uma safra");
						gerar = false;
					} else {
						id_safra = safra.getId_safra();
						gerar = true;
					}

				}

				if (chkBoxContratos.isSelected()) {
					contrato = true;

					if (chkBoxContratosComoComprador.isSelected()) {
						contrato_como_comprador = true;
					}

					if (chkBoxContratosComoVendedor.isSelected()) {
						contrato_como_vendedor = true;
					}

					if (chkBoxContratosComoComprador.isSelected() || chkBoxContratosComoVendedor.isSelected()) {
						if (chckbxInterno.isSelected()) {
							if (chckbxIncluirSubContratos.isSelected())
								sub_contratos = true;
						}
					}

					if (chkBoxContratosComoComprador.isSelected() || chkBoxContratosComoVendedor.isSelected()) {
						if (chckbxInterno.isSelected()) {

							if (chckbxIncluirValorComissao.isSelected()) {
								incluir_comissao = true;
							}
						}
					}

					if (chkBoxContratosComoComprador.isSelected() || chkBoxContratosComoVendedor.isSelected()) {
						if (chckbxInterno.isSelected()) {

							if (chckbxIncluirSubContratos.isSelected()) {
								if (chckbxIncluirGanhosPotenciais.isSelected()) {
									incluir_ganhos_potencias = true;
								}
							}
						}
					}

					if (chkBoxContratosComoComprador.isSelected() || chkBoxContratosComoVendedor.isSelected()) {
						if (chckbxInterno.isSelected()) {

							if (chckbxSomarSubContratos.isSelected()) {
								somar_sub_contratos = true;

							}
						}
					}

					if(chkBoxUnirContratos.isSelected()) {
						unir_contratos = true;
					}else {
						unir_contratos = false;

					}
					
				} else {

				}

				if (chckbxPagamentos.isSelected()) {
					pagamento = true;

					if (chkBoxComoDepositante.isSelected()) {
						pagamento_como_despositante = true;
					}

					if (chkBoxComoFavorecido.isSelected()) {
						pagamento_como_favorecido = true;
					}

					if (chkbxIxibirContratosSemPagamentos.isSelected()) {
						incluir_sem_pagamentos = true;
					} else {
						incluir_sem_pagamentos = false;

					}
				}

				if (chckbxCarregamentos.isSelected()) {
					carregamento = true;

					if (chckbxCarregamentoComoComprador.isSelected()) {
						carregamento_como_comprador = true;
					}

					if (chckbxCarregamentpComoVendedor.isSelected()) {
						carregamento_como_vendedor = true;
					}

					if (chckbxControleNfVendaSaida.isSelected()) {
						controle_nf_venda_carregamentos = true;
					}

					if (chckbxUnirCarregamentos.isSelected()) {
						unir_carregamentos = true;
					} else {
						unir_carregamentos = false;

					}

					if (chckbxIncluirTransfrenciasCarregamentos.isSelected()) {
						incluir_transferencia_carregamentos = true;
					} else {
						incluir_transferencia_carregamentos = false;

					}

					if (chkbxIxibirContratosSemCarregamentos.isSelected()) {
						incluir_sem_carregamentos = true;
					} else {
						incluir_sem_carregamentos = false;

					}

				}

				if (chckbxRecebimentos.isSelected()) {
					recebimento = true;

					if (chckbxRecebimentoComoComprador.isSelected()) {
						recebimento_como_comprador = true;
					}

					if (chckbxRecebimentoComoVendedor.isSelected()) {
						recebimento_como_vendedor = true;
					}

					if (chckbxControleNfVendaEntrada.isSelected()) {
						controle_nf_venda_recebimentos = true;
					}

					if (chckbxUnirRecebimentos.isSelected()) {
						unir_recebimentos = true;
					} else {
						unir_recebimentos = false;

					}

					if (chkbxIxibirContratosSemRecebimentos.isSelected()) {
						incluir_sem_recebimentos = true;
					} else {
						incluir_sem_recebimentos = false;

					}

				}

				ArrayList<CadastroCliente> clientes = new ArrayList<>();

				if (cliente_alvo != null) {
					// um clinete apneas adicionado
					clientes.add(cliente_alvo);
				} else {
					JOptionPane.showMessageDialog(null, "Grupo selecionado");
					// e um grupo de cliente
					String membros = grupo_alvo.getIntegrantes();
					String membros_quebrado[] = membros.split(";");
					for (String s_id : membros_quebrado) {
						int id = Integer.parseInt(s_id);
						GerenciarBancoClientes gerenciar_clientes = new GerenciarBancoClientes();
						CadastroCliente cliente = gerenciar_clientes.getCliente(id);
						clientes.add(cliente);

					}
					grupo_alvo.setClientes(clientes);

				}

				if (chckbxInterno.isSelected()) {
					tipo_contrato = 1;
				} else if (chckbxExterno.isSelected()) {
					tipo_contrato = 2;
				}

				if (gerar && chkBoxContratosComoComprador.isSelected()) {
					RelatorioContratos relatar = new RelatorioContratos(tipo_contrato, contrato, unir_contratos, true, pagamento,
							pagamento_como_despositante, pagamento_como_favorecido, incluir_sem_pagamentos,
							carregamento, carregamento_como_comprador, carregamento_como_vendedor, unir_carregamentos,
							controle_nf_venda_carregamentos, incluir_transferencia_carregamentos,
							incluir_sem_carregamentos, recebimento, recebimento_como_comprador,
							recebimento_como_vendedor, unir_recebimentos, controle_nf_venda_recebimentos,
							incluir_sem_recebimentos, id_safra, sub_contratos, incluir_comissao,
							incluir_ganhos_potencias, somar_sub_contratos, clientes, grupo_alvo);
					ByteArrayOutputStream contrato_alterado = relatar.preparar();

					ConverterPdf converter_pdf = new ConverterPdf();
					String pdf_alterado = converter_pdf.word_pdf_stream(contrato_alterado);
					TelaVizualizarPdf vizualizar = new TelaVizualizarPdf(null, isto, null, pdf_alterado, null, isto);

				} else if (gerar && chkBoxContratosComoVendedor.isSelected()) {
					RelatorioContratos relatar = new RelatorioContratos(tipo_contrato, contrato,unir_contratos, false, pagamento,
							pagamento_como_despositante, pagamento_como_favorecido, incluir_sem_pagamentos,
							carregamento, carregamento_como_comprador, carregamento_como_vendedor, unir_carregamentos,
							controle_nf_venda_carregamentos, incluir_transferencia_carregamentos,
							incluir_sem_carregamentos, recebimento, recebimento_como_comprador,
							recebimento_como_vendedor, unir_recebimentos, controle_nf_venda_recebimentos,
							incluir_sem_recebimentos, id_safra, sub_contratos, incluir_comissao,
							incluir_ganhos_potencias, somar_sub_contratos, clientes, grupo_alvo);
					ByteArrayOutputStream contrato_alterado = relatar.preparar();

					ConverterPdf converter_pdf = new ConverterPdf();
					String pdf_alterado = converter_pdf.word_pdf_stream(contrato_alterado);
					TelaVizualizarPdf vizualizar = new TelaVizualizarPdf(null, isto, null, pdf_alterado, null, isto);

				} else if (gerar && !chkBoxContratosComoVendedor.isSelected()
						&& !chkBoxContratosComoComprador.isSelected()) {
					RelatorioContratos relatar = new RelatorioContratos(tipo_contrato, contrato,unir_contratos, false, pagamento,
							pagamento_como_despositante, pagamento_como_favorecido, incluir_sem_pagamentos,
							carregamento, carregamento_como_comprador, carregamento_como_vendedor, unir_carregamentos,
							controle_nf_venda_carregamentos, incluir_transferencia_carregamentos,
							incluir_sem_carregamentos, recebimento, recebimento_como_comprador,
							recebimento_como_vendedor, unir_recebimentos, controle_nf_venda_recebimentos,
							incluir_sem_recebimentos, id_safra, sub_contratos, incluir_comissao,
							incluir_ganhos_potencias, somar_sub_contratos, clientes, grupo_alvo);
					ByteArrayOutputStream contrato_alterado = relatar.preparar();

					ConverterPdf converter_pdf = new ConverterPdf();
					String pdf_alterado = converter_pdf.word_pdf_stream(contrato_alterado);
					TelaVizualizarPdf vizualizar = new TelaVizualizarPdf(null, isto, null, pdf_alterado, null, isto);
				}

			}
		});
		btnNewButton.setBounds(1002, 630, 169, 28);
		painelPrincipal.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Selecionar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaCliente cliente = new TelaCliente(0, 11, null);
				cliente.setTelaPai(isto);
				cliente.setVisible(true);

			}
		});
		btnNewButton_1.setBounds(319, 100, 89, 23);
		painelPrincipal.add(btnNewButton_1);

		chckbxTodasAsSafras = new JCheckBox("Todas as Safras");
		chckbxTodasAsSafras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (chckbxTodasAsSafras.isSelected()) {
					chckbxTodasAsSafras.setSelected(true);
					cBSafra.setEnabled(false);
				} else {
					chckbxTodasAsSafras.setSelected(false);
					cBSafra.setEnabled(true);
				}

			}
		});
		chckbxTodasAsSafras.setSelected(true);
		chckbxTodasAsSafras.setBounds(424, 61, 120, 23);
		painelPrincipal.add(chckbxTodasAsSafras);

		painelOpcaosInternas = new JPanel();
		painelOpcaosInternas.setBackground(new Color(51, 0, 0));
		painelOpcaosInternas.setBounds(24, 459, 694, 211);
		painelPrincipal.add(painelOpcaosInternas);
		painelOpcaosInternas.setLayout(null);

		chckbxSomarSubContratos = new JCheckBox("Somar Sub-Contratos");
		chckbxSomarSubContratos.setFont(new Font("SansSerif", Font.BOLD, 14));
		chckbxSomarSubContratos.setForeground(Color.WHITE);
		chckbxSomarSubContratos.setBounds(6, 35, 171, 19);
		painelOpcaosInternas.add(chckbxSomarSubContratos);
		chckbxSomarSubContratos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxSomarSubContratos.isSelected()) {
					chckbxSomarSubContratos.setSelected(true);
				} else {
					chckbxSomarSubContratos.setSelected(false);

				}
			}
		});
		chckbxSomarSubContratos.setEnabled(false);

		lblNewLabel_4 = new JLabel(
				"*Opção para gerar relatorio aonde o alvo possuir contratos e sub-contratos como comprador");
		lblNewLabel_4.setBounds(7, 65, 651, 19);
		painelOpcaosInternas.add(lblNewLabel_4);
		lblNewLabel_4.setForeground(Color.RED);
		lblNewLabel_4.setFont(new Font("SansSerif", Font.BOLD, 14));

		chckbxIncluirValorComissao = new JCheckBox("Incluir Comissão");
		chckbxIncluirValorComissao.setFont(new Font("SansSerif", Font.BOLD, 14));
		chckbxIncluirValorComissao.setForeground(Color.WHITE);
		chckbxIncluirValorComissao.setBounds(7, 86, 168, 23);
		painelOpcaosInternas.add(chckbxIncluirValorComissao);
		chckbxIncluirValorComissao.setEnabled(false);

		chckbxIncluirSubContratos = new JCheckBox("Incluir Sub-Contratos");
		chckbxIncluirSubContratos.setFont(new Font("SansSerif", Font.BOLD, 14));
		chckbxIncluirSubContratos.setForeground(Color.WHITE);
		chckbxIncluirSubContratos.setBounds(7, 112, 171, 19);
		painelOpcaosInternas.add(chckbxIncluirSubContratos);
		chckbxIncluirSubContratos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxIncluirSubContratos.isSelected()) {

					if (chkBoxContratos.isSelected()) {
						if (chkBoxContratosComoComprador.isSelected() || chkBoxContratosComoVendedor.isSelected()
								|| chkBoxContratosComoCorretor.isSelected()) {
							chckbxIncluirSubContratos.setSelected(true);
							chckbxIncluirGanhosPotenciais.setEnabled(true);

						}

					}

				} else {
					chckbxIncluirSubContratos.setSelected(false);
					chckbxIncluirGanhosPotenciais.setEnabled(false);

				}
			}
		});
		chckbxIncluirSubContratos.setEnabled(false);

		chckbxIncluirGanhosPotenciais = new JCheckBox("Incluir Ganhos Potenciais");
		chckbxIncluirGanhosPotenciais.setFont(new Font("SansSerif", Font.BOLD, 14));
		chckbxIncluirGanhosPotenciais.setForeground(Color.WHITE);
		chckbxIncluirGanhosPotenciais.setBounds(39, 141, 201, 19);
		painelOpcaosInternas.add(chckbxIncluirGanhosPotenciais);
		chckbxIncluirGanhosPotenciais.setEnabled(false);

		lblNewLabel_5 = new JLabel("Opções para relatorio interno:");
		lblNewLabel_5.setForeground(Color.WHITE);
		lblNewLabel_5.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblNewLabel_5.setBounds(10, 11, 212, 19);
		painelOpcaosInternas.add(lblNewLabel_5);

		JButton btnGerarRelatorioSimplificado = new JButton("Gerar Relatorio Simplificado");
		btnGerarRelatorioSimplificado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int tipo_contrato = -1;
				boolean gerar = false;
				boolean somar_sub_contratos = false;
				boolean incluir_comissao = false;
				boolean incluir_ganhos_potencias = false;
				boolean sub_contratos = false;
				boolean contrato = false, contrato_como_comprador = false, contrato_como_vendedor = false,
						contrato_como_corretor = false;
				boolean pagamento = false, pagamento_como_despositante = false, pagamento_como_favorecido = false;
				boolean carregamento = false, carregamento_como_comprador = false, carregamento_como_vendedor = false;
				boolean recebimento = false, recebimento_como_comprador = false, recebimento_como_vendedor = false,
						unir_recebimentos = false;

				Date hoje = new Date();
				SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

				int id_safra = -1;

				if (chckbxTodasAsSafras.isSelected()) {
					// todas as safras selecionadas
					id_safra = 0;
					gerar = true;
				} else {
					CadastroSafra safra = (CadastroSafra) modelSafra.getSelectedItem();
					if (safra == null) {
						JOptionPane.showMessageDialog(isto, "Marque a caixa Todas as safras ou\nSelecione uma safra");
						gerar = false;
					} else {
						id_safra = safra.getId_safra();
						gerar = true;
					}

				}

				if (chckbxRecebimentos.isSelected()) {
					recebimento = true;

					if (chckbxRecebimentoComoComprador.isSelected()) {
						recebimento_como_comprador = true;
					}

					if (chckbxRecebimentoComoVendedor.isSelected()) {
						recebimento_como_vendedor = true;
					}

					if (chckbxUnirRecebimentos.isSelected()) {
						unir_recebimentos = true;
					} else {
						unir_recebimentos = false;

					}

				}

				ArrayList<CadastroCliente> clientes = new ArrayList<>();

				if (cliente_alvo != null) {
					// um clinete apneas adicionado
					clientes.add(cliente_alvo);
				} else {
					// e um grupo de cliente
					String membros = grupo_alvo.getIntegrantes();
					String membros_quebrado[] = membros.split(";");
					for (String s_id : membros_quebrado) {
						int id = Integer.parseInt(s_id);
						GerenciarBancoClientes gerenciar_clientes = new GerenciarBancoClientes();
						CadastroCliente cliente = gerenciar_clientes.getCliente(id);
						clientes.add(cliente);

					}
					grupo_alvo.setClientes(clientes);

				}

				if (gerar) {
					RelatorioContratoSimplificado relatar = new RelatorioContratoSimplificado();
					relatar.setId_safra(id_safra);
					relatar.setContrato(contrato);
					relatar.setContrato_como_comprador(true);
					relatar.setTipo_contrato(tipo_contrato);
					relatar.setSub_contratos(sub_contratos);
					relatar.setIncluir_comissao(incluir_comissao);
					relatar.setIncluir_ganhos_potencias(incluir_ganhos_potencias);
					relatar.setSomar_sub_contratos(somar_sub_contratos);
					relatar.setClientes_globais(clientes);
					relatar.setCarregamento(carregamento);
					relatar.setCarregamento_como_comprador(carregamento_como_comprador);
					relatar.setCarregamento_como_vendedor(carregamento_como_vendedor);
					relatar.setPagamento(pagamento);
					relatar.setPagamento_como_depositante(pagamento_como_despositante);
					relatar.setPagamento_como_favorecido(pagamento_como_favorecido);
					relatar.setRecebimento(recebimento);
					relatar.setRecebimento_como_comprador(recebimento_como_comprador);
					relatar.setRecebimento_como_vendedor(recebimento_como_vendedor);
					relatar.setUnir_recebimentos(unir_recebimentos);

					ByteArrayOutputStream contrato_alterado = relatar.preparar();

					ConverterPdf converter_pdf = new ConverterPdf();
					String pdf_alterado = converter_pdf.word_pdf_stream(contrato_alterado);
					TelaVizualizarPdf vizualizar = new TelaVizualizarPdf(null, isto, null, pdf_alterado, null, isto);

				}
			}
		});
		btnGerarRelatorioSimplificado.setBounds(988, 488, 183, 28);
		painelPrincipal.add(btnGerarRelatorioSimplificado);

		panel = new JPanel();
		panel.setBackground(new Color(0, 51, 0));
		panel.setBounds(424, 161, 242, 286);
		painelPrincipal.add(panel);
		panel.setLayout(null);

		chckbxRecebimentos = new JCheckBox("Recebimentos");
		chckbxRecebimentos.setFont(new Font("SansSerif", Font.BOLD, 14));
		chckbxRecebimentos.setForeground(Color.WHITE);
		chckbxRecebimentos.setBounds(6, 6, 123, 19);
		panel.add(chckbxRecebimentos);

		chckbxRecebimentoComoComprador = new JCheckBox("Alvo como Comprador");
		chckbxRecebimentoComoComprador.setFont(new Font("SansSerif", Font.BOLD, 14));
		chckbxRecebimentoComoComprador.setForeground(Color.WHITE);
		chckbxRecebimentoComoComprador.setBounds(30, 39, 177, 19);
		panel.add(chckbxRecebimentoComoComprador);
		chckbxRecebimentoComoComprador.setEnabled(false);

		chckbxRecebimentoComoVendedor = new JCheckBox("Alvo como Vendedor");
		chckbxRecebimentoComoVendedor.setFont(new Font("SansSerif", Font.BOLD, 14));
		chckbxRecebimentoComoVendedor.setForeground(Color.WHITE);
		chckbxRecebimentoComoVendedor.setBounds(30, 75, 168, 19);
		panel.add(chckbxRecebimentoComoVendedor);
		chckbxRecebimentoComoVendedor.setEnabled(false);

		chckbxUnirRecebimentos = new JCheckBox("Unir");
		chckbxUnirRecebimentos.setFont(new Font("SansSerif", Font.BOLD, 14));
		chckbxUnirRecebimentos.setForeground(Color.WHITE);
		chckbxUnirRecebimentos.setBounds(55, 164, 168, 23);
		panel.add(chckbxUnirRecebimentos);
		chckbxUnirRecebimentos.setEnabled(false);

		chckbxControleNfVendaEntrada = new JCheckBox("Controle NF Venda");
		chckbxControleNfVendaEntrada.setForeground(Color.WHITE);
		chckbxControleNfVendaEntrada.setFont(new Font("SansSerif", Font.BOLD, 14));
		chckbxControleNfVendaEntrada.setEnabled(false);
		chckbxControleNfVendaEntrada.setBounds(55, 118, 168, 23);
		panel.add(chckbxControleNfVendaEntrada);

		chkbxIxibirContratosSemRecebimentos = new JCheckBox("<html>Incluir Contratos<br/>Sem Recebimentos</html>");
		chkbxIxibirContratosSemRecebimentos.setForeground(Color.WHITE);
		chkbxIxibirContratosSemRecebimentos.setFont(new Font("SansSerif", Font.BOLD, 14));
		chkbxIxibirContratosSemRecebimentos.setEnabled(false);
		chkbxIxibirContratosSemRecebimentos.setBounds(55, 199, 157, 57);
		panel.add(chkbxIxibirContratosSemRecebimentos);

		panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 0, 102));
		panel_1.setBounds(678, 161, 251, 286);
		painelPrincipal.add(panel_1);
		panel_1.setLayout(null);

		chckbxCarregamentos = new JCheckBox("Carregamentos");
		chckbxCarregamentos.setFont(new Font("SansSerif", Font.BOLD, 14));
		chckbxCarregamentos.setForeground(Color.WHITE);
		chckbxCarregamentos.setBounds(6, 6, 130, 19);
		panel_1.add(chckbxCarregamentos);

		chckbxCarregamentoComoComprador = new JCheckBox("Alvo como Comprador");
		chckbxCarregamentoComoComprador.setFont(new Font("SansSerif", Font.BOLD, 14));
		chckbxCarregamentoComoComprador.setForeground(Color.WHITE);
		chckbxCarregamentoComoComprador.setBounds(30, 39, 177, 19);
		panel_1.add(chckbxCarregamentoComoComprador);
		chckbxCarregamentoComoComprador.setEnabled(false);

		chckbxCarregamentpComoVendedor = new JCheckBox("Alvo como Vendedor");
		chckbxCarregamentpComoVendedor.setFont(new Font("SansSerif", Font.BOLD, 14));
		chckbxCarregamentpComoVendedor.setForeground(Color.WHITE);
		chckbxCarregamentpComoVendedor.setBounds(30, 75, 168, 19);
		panel_1.add(chckbxCarregamentpComoVendedor);
		chckbxCarregamentpComoVendedor.setEnabled(false);

		chckbxControleNfVendaSaida = new JCheckBox("Controle NF Venda");
		chckbxControleNfVendaSaida.setFont(new Font("SansSerif", Font.BOLD, 14));
		chckbxControleNfVendaSaida.setForeground(Color.WHITE);
		chckbxControleNfVendaSaida.setEnabled(false);
		chckbxControleNfVendaSaida.setBounds(66, 119, 154, 19);
		panel_1.add(chckbxControleNfVendaSaida);

		chckbxUnirCarregamentos = new JCheckBox("Unir");
		chckbxUnirCarregamentos.setFont(new Font("SansSerif", Font.BOLD, 14));
		chckbxUnirCarregamentos.setForeground(Color.WHITE);
		chckbxUnirCarregamentos.setEnabled(false);
		chckbxUnirCarregamentos.setBounds(66, 181, 51, 19);
		panel_1.add(chckbxUnirCarregamentos);

		chckbxIncluirTransfrenciasCarregamentos = new JCheckBox("Incluir Transfêrencias");
		chckbxIncluirTransfrenciasCarregamentos.setForeground(Color.WHITE);
		chckbxIncluirTransfrenciasCarregamentos.setFont(new Font("SansSerif", Font.BOLD, 14));
		chckbxIncluirTransfrenciasCarregamentos.setEnabled(false);
		chckbxIncluirTransfrenciasCarregamentos.setBounds(66, 150, 175, 19);
		panel_1.add(chckbxIncluirTransfrenciasCarregamentos);

		chkbxIxibirContratosSemCarregamentos = new JCheckBox("<html>Incluir Contratos<br/>Sem Carregamentos</html>");
		chkbxIxibirContratosSemCarregamentos.setForeground(Color.WHITE);
		chkbxIxibirContratosSemCarregamentos.setFont(new Font("SansSerif", Font.BOLD, 14));
		chkbxIxibirContratosSemCarregamentos.setEnabled(false);
		chkbxIxibirContratosSemCarregamentos.setBounds(66, 204, 164, 57);
		panel_1.add(chkbxIxibirContratosSemCarregamentos);

		panel_2 = new JPanel();
		panel_2.setBackground(new Color(0, 153, 0));
		panel_2.setBounds(941, 161, 251, 286);
		painelPrincipal.add(panel_2);
		panel_2.setLayout(null);

		chckbxPagamentos = new JCheckBox("Pagamentos");
		chckbxPagamentos.setFont(new Font("SansSerif", Font.BOLD, 14));
		chckbxPagamentos.setForeground(Color.WHITE);
		chckbxPagamentos.setBounds(19, 22, 108, 19);
		panel_2.add(chckbxPagamentos);

		chkBoxComoDepositante = new JCheckBox("Alvo como Comprador");
		chkBoxComoDepositante.setFont(new Font("SansSerif", Font.BOLD, 14));
		chkBoxComoDepositante.setForeground(Color.WHITE);
		chkBoxComoDepositante.setBounds(43, 55, 184, 19);
		panel_2.add(chkBoxComoDepositante);
		chkBoxComoDepositante.setEnabled(false);

		chkBoxComoFavorecido = new JCheckBox("Alvo como Vendedor");
		chkBoxComoFavorecido.setFont(new Font("SansSerif", Font.BOLD, 14));
		chkBoxComoFavorecido.setForeground(Color.WHITE);
		chkBoxComoFavorecido.setBounds(43, 91, 179, 19);
		panel_2.add(chkBoxComoFavorecido);
		chkBoxComoFavorecido.setEnabled(false);

		chckbxIncluirTransfrenciasPagamentos = new JCheckBox("Incluir Transfêrencias");
		chckbxIncluirTransfrenciasPagamentos.setForeground(Color.WHITE);
		chckbxIncluirTransfrenciasPagamentos.setFont(new Font("SansSerif", Font.BOLD, 14));
		chckbxIncluirTransfrenciasPagamentos.setEnabled(false);
		chckbxIncluirTransfrenciasPagamentos.setBounds(70, 122, 175, 19);
		panel_2.add(chckbxIncluirTransfrenciasPagamentos);

		chckbxUnirPagamentos = new JCheckBox("Unir");
		chckbxUnirPagamentos.setForeground(Color.WHITE);
		chckbxUnirPagamentos.setFont(new Font("SansSerif", Font.BOLD, 14));
		chckbxUnirPagamentos.setEnabled(false);
		chckbxUnirPagamentos.setBounds(70, 153, 51, 19);
		panel_2.add(chckbxUnirPagamentos);

		chkbxIxibirContratosSemPagamentos = new JCheckBox("");
		chkbxIxibirContratosSemPagamentos.setEnabled(false);
		chkbxIxibirContratosSemPagamentos.setForeground(Color.WHITE);
		chkbxIxibirContratosSemPagamentos.setFont(new Font("SansSerif", Font.BOLD, 14));
		chkbxIxibirContratosSemPagamentos.setBounds(70, 165, 142, 76);
		chkbxIxibirContratosSemPagamentos.setText("<html>Incluir Contratos<br/>Sem Pagamentos</html>");
		panel_2.add(chkbxIxibirContratosSemPagamentos);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(51, 51, 0));
		panel_3.setBounds(24, 161, 362, 296);
		painelPrincipal.add(panel_3);
		panel_3.setLayout(null);

		lblNewLabel_3 = new JLabel("Tipo:");
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setBounds(16, 16, 51, 23);
		panel_3.add(lblNewLabel_3);
		lblNewLabel_3.setFont(new Font("SansSerif", Font.BOLD, 14));

		chckbxInterno = new JCheckBox("Interno");
		chckbxInterno.setFont(new Font("SansSerif", Font.BOLD, 14));
		chckbxInterno.setForeground(Color.WHITE);
		chckbxInterno.setBounds(26, 47, 73, 19);
		panel_3.add(chckbxInterno);
		chckbxInterno.setSelected(true);

		chckbxExterno = new JCheckBox("Externo");
		chckbxExterno.setFont(new Font("SansSerif", Font.BOLD, 14));
		chckbxExterno.setForeground(Color.WHITE);
		chckbxExterno.setBounds(125, 47, 77, 19);
		panel_3.add(chckbxExterno);

		JLabel lblNewLabel_2 = new JLabel("Opções:");
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setBounds(16, 73, 58, 19);
		panel_3.add(lblNewLabel_2);
		lblNewLabel_2.setFont(new Font("SansSerif", Font.BOLD, 14));

		chkBoxContratos = new JCheckBox("Contratos");
		chkBoxContratos.setFont(new Font("SansSerif", Font.BOLD, 14));
		chkBoxContratos.setForeground(Color.WHITE);
		chkBoxContratos.setBounds(16, 108, 110, 23);
		panel_3.add(chkBoxContratos);

		chkBoxContratosComoComprador = new JCheckBox("Alvo como Comprador");
		chkBoxContratosComoComprador.setFont(new Font("SansSerif", Font.BOLD, 14));
		chkBoxContratosComoComprador.setForeground(Color.WHITE);
		chkBoxContratosComoComprador.setBounds(39, 141, 177, 19);
		panel_3.add(chkBoxContratosComoComprador);
		chkBoxContratosComoComprador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (chkBoxContratosComoComprador.isSelected()) {

					chkBoxContratosComoComprador.setSelected(true);
					chckbxIncluirSubContratos.setEnabled(true);
					chckbxIncluirValorComissao.setEnabled(true);

					chkBoxContratosComoVendedor.setSelected(false);
					chckbxSomarSubContratos.setEnabled(true);

				} else {

					if (!chkBoxContratosComoVendedor.isSelected() && !chkBoxContratosComoCorretor.isSelected()) {
						chckbxIncluirSubContratos.setEnabled(false);
						chckbxIncluirValorComissao.setEnabled(false);
						chckbxSomarSubContratos.setEnabled(false);

					}

					chkBoxContratosComoComprador.setSelected(false);
				}
			}
		});
		chkBoxContratosComoComprador.setEnabled(false);

		chkBoxContratosComoVendedor = new JCheckBox("Alvo como Vendedor");
		chkBoxContratosComoVendedor.setFont(new Font("SansSerif", Font.BOLD, 14));
		chkBoxContratosComoVendedor.setForeground(Color.WHITE);
		chkBoxContratosComoVendedor.setBounds(39, 177, 168, 19);
		panel_3.add(chkBoxContratosComoVendedor);
		chkBoxContratosComoVendedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (chkBoxContratosComoVendedor.isSelected()) {

					chkBoxContratosComoVendedor.setSelected(true);
					chckbxIncluirSubContratos.setEnabled(true);
					chckbxIncluirValorComissao.setEnabled(true);
					chkBoxContratosComoComprador.setSelected(false);
					chckbxSomarSubContratos.setEnabled(true);

				} else {

					if (!chkBoxContratosComoComprador.isSelected() && !chkBoxContratosComoCorretor.isSelected()) {
						chckbxIncluirSubContratos.setEnabled(false);
						chckbxIncluirValorComissao.setEnabled(false);
						chckbxSomarSubContratos.setEnabled(false);

					}

					chkBoxContratosComoVendedor.setSelected(false);
				}

			}
		});
		chkBoxContratosComoVendedor.setEnabled(false);

		chkBoxContratosComoCorretor = new JCheckBox("Alvo como Corretor");
		chkBoxContratosComoCorretor.setFont(new Font("SansSerif", Font.BOLD, 14));
		chkBoxContratosComoCorretor.setForeground(Color.WHITE);
		chkBoxContratosComoCorretor.setBounds(39, 215, 159, 19);
		panel_3.add(chkBoxContratosComoCorretor);
		chkBoxContratosComoCorretor.setVisible(false);
		chkBoxContratosComoCorretor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (chkBoxContratosComoCorretor.isSelected()) {

					chkBoxContratosComoCorretor.setSelected(true);
					chckbxIncluirSubContratos.setEnabled(true);
					chckbxIncluirValorComissao.setEnabled(true);

				} else {

					if (!chkBoxContratosComoComprador.isSelected() && !chkBoxContratosComoVendedor.isSelected()) {
						chckbxIncluirSubContratos.setEnabled(false);
						chckbxIncluirValorComissao.setEnabled(false);

					}

					chkBoxContratosComoCorretor.setSelected(false);
				}

			}
		});
		chkBoxContratosComoCorretor.setEnabled(false);
		
		 chkBoxUnirContratos = new JCheckBox("Unir");
		chkBoxUnirContratos.setEnabled(false);
		chkBoxUnirContratos.setForeground(Color.WHITE);
		chkBoxUnirContratos.setFont(new Font("SansSerif", Font.BOLD, 14));
		chkBoxUnirContratos.setBounds(39, 257, 104, 18);
		panel_3.add(chkBoxUnirContratos);
		chkBoxContratos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (chkBoxContratos.isSelected()) {
					chkBoxContratos.setSelected(true);
					chkBoxContratosComoComprador.setEnabled(true);
					chkBoxContratosComoVendedor.setEnabled(true);
					chkBoxContratosComoCorretor.setEnabled(true);
					chkBoxUnirContratos.setEnabled(true);

				} else {
					chkBoxContratos.setSelected(false);
					chkBoxContratosComoComprador.setEnabled(false);
					chkBoxContratosComoVendedor.setEnabled(false);
					chkBoxContratosComoCorretor.setEnabled(false);
					chckbxIncluirValorComissao.setEnabled(false);
					chckbxIncluirSubContratos.setEnabled(false);
					chckbxIncluirGanhosPotenciais.setEnabled(false);
					chckbxSomarSubContratos.setEnabled(false);
					chkBoxUnirContratos.setEnabled(false);
				}

			}
		});
		chckbxExterno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxExterno.isSelected()) {
					chckbxExterno.setSelected(true);
					chckbxInterno.setSelected(false);
					painelOpcaosInternas.setEnabled(false);
					painelOpcaosInternas.setVisible(false);

				} else {
					chckbxInterno.setSelected(false);
					chckbxExterno.setSelected(true);
					painelOpcaosInternas.setEnabled(true);
					painelOpcaosInternas.setVisible(true);
				}

			}
		});
		chckbxInterno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (chckbxInterno.isSelected()) {
					chckbxInterno.setSelected(true);
					chckbxExterno.setSelected(false);
					painelOpcaosInternas.setEnabled(true);
					painelOpcaosInternas.setVisible(true);

				} else {
					chckbxInterno.setSelected(false);
					chckbxExterno.setSelected(true);
					painelOpcaosInternas.setEnabled(false);
					painelOpcaosInternas.setVisible(false);
				}

			}
		});
		chckbxPagamentos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (chckbxPagamentos.isSelected()) {
					chckbxPagamentos.setSelected(true);
					chkBoxComoDepositante.setEnabled(true);
					chkBoxComoFavorecido.setEnabled(true);
					chkbxIxibirContratosSemPagamentos.setEnabled(true);
				} else {
					chckbxPagamentos.setSelected(false);
					chkBoxComoDepositante.setEnabled(false);
					chkBoxComoFavorecido.setEnabled(false);
					chkbxIxibirContratosSemPagamentos.setEnabled(false);

				}

			}
		});
		chckbxCarregamentos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (chckbxCarregamentos.isSelected()) {
					chckbxCarregamentos.setSelected(true);
					chckbxCarregamentoComoComprador.setEnabled(true);
					chckbxCarregamentpComoVendedor.setEnabled(true);
					chckbxControleNfVendaSaida.setEnabled(true);
					chckbxUnirCarregamentos.setEnabled(true);
					chckbxIncluirTransfrenciasCarregamentos.setEnabled(true);
					chkbxIxibirContratosSemCarregamentos.setEnabled(true);
				} else {
					chckbxCarregamentos.setSelected(false);
					chckbxCarregamentoComoComprador.setEnabled(false);
					chckbxCarregamentpComoVendedor.setEnabled(false);
					chckbxControleNfVendaSaida.setEnabled(false);
					chckbxUnirCarregamentos.setEnabled(false);
					chckbxIncluirTransfrenciasCarregamentos.setEnabled(false);
					chkbxIxibirContratosSemCarregamentos.setEnabled(false);

				}
			}
		});
		chckbxRecebimentos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxRecebimentos.isSelected()) {
					chckbxRecebimentos.setSelected(true);
					chckbxRecebimentoComoComprador.setEnabled(true);
					chckbxRecebimentoComoVendedor.setEnabled(true);
					chckbxUnirRecebimentos.setEnabled(true);
					chkbxIxibirContratosSemRecebimentos.setEnabled(true);
					chckbxControleNfVendaEntrada.setEnabled(true);

				} else {
					chckbxRecebimentos.setSelected(false);
					chckbxRecebimentoComoComprador.setEnabled(false);
					chckbxRecebimentoComoVendedor.setEnabled(false);
					chckbxUnirRecebimentos.setEnabled(false);
					chckbxControleNfVendaEntrada.setEnabled(false);
					chkbxIxibirContratosSemRecebimentos.setEnabled(false);

				}
			}
		});

		pesquisarSafras();

		for (CadastroSafra safra : safras) {

			// cBSafra.addItem(safra.getProduto().getNome_produto() + " " +
			// safra.getAno_plantio() + "/" + safra.getAno_colheita());
			// cBSafra.addItem(safra);
			modelSafra.addSafra(safra);

		}

		this.setLocationRelativeTo(janela_pai);

		this.setVisible(true);

	}

	public void setGrupoAlvo(CadastroGrupo grupo) {
		this.grupo_alvo = grupo;
		this.cliente_alvo = null;

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {

				cBAlvo.removeAllItems();
				cBAlvo.updateUI();
				cBAlvo.repaint();

				cBAlvo.addItem(grupo.getId_grupo() + " " + grupo.getNome_grupo());
				cBAlvo.updateUI();
				cBAlvo.repaint();

			}
		});
	}

	public void setClienteAlvo(CadastroCliente cliente) {
		this.cliente_alvo = cliente;

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {

				cBAlvo.removeAllItems();
				cBAlvo.updateUI();
				cBAlvo.repaint();

				String nome = "";

				if (cliente_alvo.getTipo_pessoa() == 0) {
					nome = cliente_alvo.getNome_empresarial();
				} else {
					nome = cliente_alvo.getNome_fantaia();

				}

				cBAlvo.addItem(cliente_alvo.getId() + " " + nome);
				cBAlvo.updateUI();
				cBAlvo.repaint();

			}
		});
	}
}
