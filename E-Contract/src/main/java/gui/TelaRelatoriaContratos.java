package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
import main.java.relatoria.RelatorioContratoCarregamentoSimplificado;
import main.java.relatoria.RelatorioContratoComprador;
import main.java.relatoria.RelatorioContratoRecebimentoSimplificado;
import main.java.relatoria.RelatorioContratos;
import main.java.tratamento_proprio.Log;
import main.java.views_personalizadas.TelaEmEspera;
import main.java.views_personalizadas.TelaEmEsperaRelatoria;
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

import javax.swing.border.LineBorder;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.border.BevelBorder;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JRadioButton;

public class TelaRelatoriaContratos extends JFrame {

	protected static final AbstractButton chkboxValorComissao = null;
	private final JPanel painelPrincipal = new JPanel();
	private ComboBoxRenderPersonalizado cBSafraPersonalizado;
	private ComboBoxPersonalizado modelSafra = new ComboBoxPersonalizado();
	private JComboBox cBSafra;
	private CadastroGrupo grupo_alvo = null;
	private CadastroCliente cliente_alvo = null;
	private CadastroCliente cliente_alvo2 = null;
	private JRadioButton rdbtnFormatoPdf, rdbtnFormatoWord;
	private FileChooser fileChooser;

	private CadastroCliente contra_parte1;

	private JComboBox cBAlvo;
	private JCheckBox chkBoxContratos, chkBoxContratosComoComprador, chkBoxContratosComoVendedor,
			chckbxIncluirSubContratos, chkBoxContratosComoCorretor, chckbxPagamentos, chkBoxComoDepositante,
			chkBoxComoFavorecido, chckbxCarregamentos, chckbxControleNfVendaEntrada, chckbxCarregamentpComoVendedor,
			chckbxCarregamentoComoComprador, chckbxIncluirTransfrenciasPagamentos, chckbxUnirPagamentos,
			chkbxIxibirContratosSemPagamentos, chkBoxUnirContratos, chckbxIncluirComissao;

	private static ArrayList<CadastroSafra> safras = new ArrayList<>();
	private JCheckBox chckbxTodasAsSafras;
	private JCheckBox chckbxIncluirValorComissao;
	private JCheckBox chckbxIncluirGanhosPotenciais;
	private JLabel lblNewLabel_3;
	private JCheckBox chckbxInterno, chckbxExternoComprador;
	private JCheckBox chckbxSomarSubContratos;
	private JLabel lblNewLabel_4;
	private JPanel painelOpcaosInternas;
	private JLabel lblNewLabel_5;
	private TelaEmEsperaRelatoria telaEmEsperaRelatoria;

	private JCheckBox chckbxRecebimentos, chckbxRecebimentoComoComprador, chckbxRecebimentoComoVendedor;
	private JCheckBox chckbxUnirRecebimentos;
	private JPanel panel;
	private JPanel panel_1;
	private JCheckBox chckbxControleNfVendaSaida;
	private JCheckBox chckbxUnirCarregamentos, chckbxIncluirTransfrenciasCarregamentos;
	private JPanel panel_2;
	private JCheckBox chkbxIxibirContratosSemCarregamentos;
	private JCheckBox chkbxIxibirContratosSemRecebimentos;
	private JComboBox cBContraParte1;
	private JButton btnGerarRelatorioCarregamento;
	private JCheckBox chckbxExternoVendedor;
	private JLabel lblNewLabel_6;
	private JComboBox cBAlvo2;
	private JButton btnNewButton_2;
	private JLabel lblNewLabel_1_2;
	private JComboBox cBParticipacao;
	private JLabel lblNewLabel_7;

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
		setBounds(100, 100, 1346, 684);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);

		cBSafraPersonalizado = new ComboBoxRenderPersonalizado();
		painelPrincipal.setLayout(new MigLayout("", "[][::5px][][::5px][][::5px][]", "[69px][grow][][213px][36px]"));

		JButton btnGerarRelatorioSimplificado = new JButton("Gerar Relatorio Recebimento Simplificado");
		btnGerarRelatorioSimplificado.setForeground(Color.WHITE);
		btnGerarRelatorioSimplificado.setBackground(new Color(0, 51, 0));
		btnGerarRelatorioSimplificado.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelPrincipal.add(btnGerarRelatorioSimplificado, "cell 2 2,alignx center,aligny bottom");
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
				CadastroCliente contra_parte = new CadastroCliente();

				if (cliente_alvo != null) {
					// um clinete apneas adicionado
					clientes.add(cliente_alvo);
					if (contra_parte1 != null) {
						contra_parte = contra_parte1;
					}

				} else if (cBAlvo.getSelectedItem().equals("TODOS")) {
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

					telaEmEsperaRelatoria = new TelaEmEsperaRelatoria(isto);

					new Thread() {
						@Override
						public void run() {
							telaEmEsperaRelatoria.setVisible(true);
						}
					}.start();

					RelatorioContratoRecebimentoSimplificado relatar = new RelatorioContratoRecebimentoSimplificado();
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
					relatar.setContra_parte(contra_parte);

						relatarRecebimentoSimplificado(isto, relatar);

						

				}
			}
		});

		btnGerarRelatorioCarregamento = new JButton("Gerar Relatorio Carregamento Simplificado");
		btnGerarRelatorioCarregamento.addActionListener(new ActionListener() {
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
				boolean unir_carregamentos = false, carregamento = false, carregamento_como_comprador = false,
						carregamento_como_vendedor = false;
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

				if (chckbxCarregamentos.isSelected()) {
					carregamento = true;

					if (chckbxCarregamentoComoComprador.isSelected()) {
						carregamento_como_comprador = true;
					}

					if (chckbxCarregamentpComoVendedor.isSelected()) {
						carregamento_como_vendedor = true;
					}

					if (chckbxUnirCarregamentos.isSelected()) {
						unir_carregamentos = true;
					} else {
						unir_carregamentos = false;

					}

				}

				ArrayList<CadastroCliente> clientes = new ArrayList<>();
				CadastroCliente contra_parte = new CadastroCliente();

				if (cliente_alvo != null) {
					// um clinete apneas adicionado
					clientes.add(cliente_alvo);
					if (contra_parte1 != null) {
						contra_parte = contra_parte1;
					}

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

					telaEmEsperaRelatoria = new TelaEmEsperaRelatoria(isto);

					new Thread() {
						@Override
						public void run() {
							telaEmEsperaRelatoria.setVisible(true);
						}
					}.start();

					RelatorioContratoCarregamentoSimplificado relatar = new RelatorioContratoCarregamentoSimplificado();
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
					relatar.setContra_parte(contra_parte);

				

							relatarCargaSimplificado(isto, relatar);


				}
			}

		});
		btnGerarRelatorioCarregamento.setForeground(Color.WHITE);
		btnGerarRelatorioCarregamento.setFont(new Font("SansSerif", Font.PLAIN, 16));
		btnGerarRelatorioCarregamento.setBackground(new Color(0, 0, 102));
		painelPrincipal.add(btnGerarRelatorioCarregamento, "cell 4 2");

		painelOpcaosInternas = new JPanel();
		painelOpcaosInternas.setBackground(new Color(51, 0, 0));
		painelPrincipal.add(painelOpcaosInternas, "cell 0 3 5 1,grow");
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

		panel = new JPanel();
		panel.setBackground(new Color(0, 51, 0));
		painelPrincipal.add(panel, "cell 2 1,grow");
		panel.setLayout(new MigLayout("", "[grow]", "[19px][19px][19px][23px][23px][57px]"));

		chckbxRecebimentos = new JCheckBox("Recebimentos");
		chckbxRecebimentos.setFont(new Font("SansSerif", Font.BOLD, 14));
		chckbxRecebimentos.setForeground(Color.WHITE);
		panel.add(chckbxRecebimentos, "cell 0 0,alignx left,aligny top");

		chckbxRecebimentoComoComprador = new JCheckBox("Alvo como Comprador");
		chckbxRecebimentoComoComprador.setFont(new Font("SansSerif", Font.BOLD, 14));
		chckbxRecebimentoComoComprador.setForeground(Color.WHITE);
		panel.add(chckbxRecebimentoComoComprador, "cell 0 1,alignx center,aligny top");
		chckbxRecebimentoComoComprador.setEnabled(false);

		chckbxRecebimentoComoVendedor = new JCheckBox("Alvo como Vendedor");
		chckbxRecebimentoComoVendedor.setFont(new Font("SansSerif", Font.BOLD, 14));
		chckbxRecebimentoComoVendedor.setForeground(Color.WHITE);
		panel.add(chckbxRecebimentoComoVendedor, "cell 0 2,alignx center,aligny top");
		chckbxRecebimentoComoVendedor.setEnabled(false);

		chckbxUnirRecebimentos = new JCheckBox("Unir");
		chckbxUnirRecebimentos.setFont(new Font("SansSerif", Font.BOLD, 14));
		chckbxUnirRecebimentos.setForeground(Color.WHITE);
		panel.add(chckbxUnirRecebimentos, "cell 0 4,alignx center,growy");
		chckbxUnirRecebimentos.setEnabled(false);

		chckbxControleNfVendaEntrada = new JCheckBox("Controle NF Venda");
		chckbxControleNfVendaEntrada.setForeground(Color.WHITE);
		chckbxControleNfVendaEntrada.setFont(new Font("SansSerif", Font.BOLD, 14));
		chckbxControleNfVendaEntrada.setEnabled(false);
		panel.add(chckbxControleNfVendaEntrada, "cell 0 3,alignx center,growy");

		chkbxIxibirContratosSemRecebimentos = new JCheckBox("<html>Incluir Contratos<br/>Sem Recebimentos</html>");
		chkbxIxibirContratosSemRecebimentos.setForeground(Color.WHITE);
		chkbxIxibirContratosSemRecebimentos.setFont(new Font("SansSerif", Font.BOLD, 14));
		chkbxIxibirContratosSemRecebimentos.setEnabled(false);
		panel.add(chkbxIxibirContratosSemRecebimentos, "cell 0 5,alignx center,growy");

		panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 0, 102));
		painelPrincipal.add(panel_1, "cell 4 1,grow");
		panel_1.setLayout(new MigLayout("", "[235px]", "[19px][19px][19px][19px][19px][19px][57px]"));

		chckbxCarregamentos = new JCheckBox("Carregamentos");
		chckbxCarregamentos.setFont(new Font("SansSerif", Font.BOLD, 14));
		chckbxCarregamentos.setForeground(Color.WHITE);
		panel_1.add(chckbxCarregamentos, "cell 0 0,alignx left,aligny top");

		chckbxCarregamentoComoComprador = new JCheckBox("Alvo como Comprador");
		chckbxCarregamentoComoComprador.setFont(new Font("SansSerif", Font.BOLD, 14));
		chckbxCarregamentoComoComprador.setForeground(Color.WHITE);
		panel_1.add(chckbxCarregamentoComoComprador, "cell 0 1,alignx center,aligny top");
		chckbxCarregamentoComoComprador.setEnabled(false);

		chckbxCarregamentpComoVendedor = new JCheckBox("Alvo como Vendedor");
		chckbxCarregamentpComoVendedor.setFont(new Font("SansSerif", Font.BOLD, 14));
		chckbxCarregamentpComoVendedor.setForeground(Color.WHITE);
		panel_1.add(chckbxCarregamentpComoVendedor, "cell 0 2,alignx center,aligny top");
		chckbxCarregamentpComoVendedor.setEnabled(false);

		chckbxControleNfVendaSaida = new JCheckBox("Controle NF Venda");
		chckbxControleNfVendaSaida.setFont(new Font("SansSerif", Font.BOLD, 14));
		chckbxControleNfVendaSaida.setForeground(Color.WHITE);
		chckbxControleNfVendaSaida.setEnabled(false);
		panel_1.add(chckbxControleNfVendaSaida, "cell 0 3,alignx center,aligny top");

		chckbxUnirCarregamentos = new JCheckBox("Unir");
		chckbxUnirCarregamentos.setFont(new Font("SansSerif", Font.BOLD, 14));
		chckbxUnirCarregamentos.setForeground(Color.WHITE);
		chckbxUnirCarregamentos.setEnabled(false);
		panel_1.add(chckbxUnirCarregamentos, "cell 0 5,alignx center,aligny top");

		chckbxIncluirTransfrenciasCarregamentos = new JCheckBox("Incluir Transfêrencias");
		chckbxIncluirTransfrenciasCarregamentos.setForeground(Color.WHITE);
		chckbxIncluirTransfrenciasCarregamentos.setFont(new Font("SansSerif", Font.BOLD, 14));
		chckbxIncluirTransfrenciasCarregamentos.setEnabled(false);
		panel_1.add(chckbxIncluirTransfrenciasCarregamentos, "cell 0 4,alignx right,aligny top");

		chkbxIxibirContratosSemCarregamentos = new JCheckBox("<html>Incluir Contratos<br/>Sem Carregamentos</html>");
		chkbxIxibirContratosSemCarregamentos.setForeground(Color.WHITE);
		chkbxIxibirContratosSemCarregamentos.setFont(new Font("SansSerif", Font.BOLD, 14));
		chkbxIxibirContratosSemCarregamentos.setEnabled(false);
		panel_1.add(chkbxIxibirContratosSemCarregamentos, "cell 0 6,alignx right,growy");

		panel_2 = new JPanel();
		panel_2.setBackground(new Color(0, 153, 0));
		painelPrincipal.add(panel_2, "cell 6 1,grow");
		panel_2.setLayout(new MigLayout("", "[grow]", "[19px][grow][grow][grow][grow][grow][grow]"));

		chckbxPagamentos = new JCheckBox("Pagamentos");
		chckbxPagamentos.setFont(new Font("SansSerif", Font.BOLD, 14));
		chckbxPagamentos.setForeground(Color.WHITE);
		panel_2.add(chckbxPagamentos, "cell 0 0,alignx left,aligny top");

		chkBoxComoDepositante = new JCheckBox("Alvo como Comprador");
		chkBoxComoDepositante.setFont(new Font("SansSerif", Font.BOLD, 14));
		chkBoxComoDepositante.setForeground(Color.WHITE);
		panel_2.add(chkBoxComoDepositante, "cell 0 1,alignx center,aligny top");
		chkBoxComoDepositante.setEnabled(false);

		chkBoxComoFavorecido = new JCheckBox("Alvo como Vendedor");
		chkBoxComoFavorecido.setFont(new Font("SansSerif", Font.BOLD, 14));
		chkBoxComoFavorecido.setForeground(Color.WHITE);
		panel_2.add(chkBoxComoFavorecido, "cell 0 2,alignx center,aligny top");
		chkBoxComoFavorecido.setEnabled(false);

		chckbxIncluirTransfrenciasPagamentos = new JCheckBox("Incluir Transfêrencias");
		chckbxIncluirTransfrenciasPagamentos.setForeground(Color.WHITE);
		chckbxIncluirTransfrenciasPagamentos.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_2.add(chckbxIncluirTransfrenciasPagamentos, "cell 0 3,alignx center,aligny top");

		chckbxUnirPagamentos = new JCheckBox("Unir");
		chckbxUnirPagamentos.setForeground(Color.WHITE);
		chckbxUnirPagamentos.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_2.add(chckbxUnirPagamentos, "cell 0 5,alignx center,aligny top");

		chckbxIncluirComissao = new JCheckBox("Incluir Comissão");
		chckbxIncluirComissao.setForeground(Color.WHITE);
		chckbxIncluirComissao.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_2.add(chckbxIncluirComissao, "cell 0 4,alignx center,aligny top");

		chkbxIxibirContratosSemPagamentos = new JCheckBox("");
		chkbxIxibirContratosSemPagamentos.setEnabled(false);
		chkbxIxibirContratosSemPagamentos.setForeground(Color.WHITE);
		chkbxIxibirContratosSemPagamentos.setFont(new Font("SansSerif", Font.BOLD, 14));
		chkbxIxibirContratosSemPagamentos.setText("<html>Incluir Contratos<br/>Sem Pagamentos</html>");
		panel_2.add(chkbxIxibirContratosSemPagamentos, "cell 0 6,alignx center,aligny top");

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(51, 51, 0));
		painelPrincipal.add(panel_3, "cell 0 1 1 2,grow");
		panel_3.setLayout(
				new MigLayout("", "[grow][][grow][grow]", "[23px][19px][19px][23px][19px][19px][19px][18px][]"));

		lblNewLabel_3 = new JLabel("Tipo:");
		lblNewLabel_3.setForeground(Color.WHITE);
		panel_3.add(lblNewLabel_3, "cell 0 0 4 1,alignx left,growy");
		lblNewLabel_3.setFont(new Font("SansSerif", Font.BOLD, 16));

		chckbxInterno = new JCheckBox("Interno(Análise)");
		chckbxInterno.setFont(new Font("SansSerif", Font.BOLD, 14));
		chckbxInterno.setForeground(Color.WHITE);
		panel_3.add(chckbxInterno, "cell 0 1 4 1,alignx center,aligny top");
		chckbxInterno.setSelected(true);

		chckbxExternoVendedor = new JCheckBox("Externo(Vendedor)");
		chckbxExternoVendedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				chckbxExternoComprador.setSelected(false);
				chckbxInterno.setSelected(false);
				chckbxExternoVendedor.setSelected(true);
				painelOpcaosInternas.setEnabled(false);
				painelOpcaosInternas.setVisible(false);
			}

		});
		chckbxExternoVendedor.setForeground(Color.WHITE);
		chckbxExternoVendedor.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_3.add(chckbxExternoVendedor, "cell 0 2");

		chckbxExternoComprador = new JCheckBox("Externo(Comprador)");
		chckbxExternoComprador.setFont(new Font("SansSerif", Font.BOLD, 14));
		chckbxExternoComprador.setForeground(Color.WHITE);
		panel_3.add(chckbxExternoComprador, "cell 3 2,alignx left,aligny top");
		chckbxExternoComprador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				chckbxExternoComprador.setSelected(true);
				chckbxInterno.setSelected(false);
				chckbxExternoVendedor.setSelected(false);
				painelOpcaosInternas.setEnabled(false);
				painelOpcaosInternas.setVisible(false);

			}
		});

		chkBoxContratos = new JCheckBox("Contratos");
		chkBoxContratos.setFont(new Font("SansSerif", Font.BOLD, 14));
		chkBoxContratos.setForeground(Color.WHITE);
		panel_3.add(chkBoxContratos, "cell 0 3 4 1,alignx left,growy");

		chkBoxContratosComoComprador = new JCheckBox("Alvo como Comprador");
		chkBoxContratosComoComprador.setFont(new Font("SansSerif", Font.BOLD, 14));
		chkBoxContratosComoComprador.setForeground(Color.WHITE);
		panel_3.add(chkBoxContratosComoComprador, "cell 0 4 4 1,alignx center,aligny top");
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
		panel_3.add(chkBoxContratosComoVendedor, "cell 0 5 4 1,alignx center,aligny top");
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
		chkBoxContratosComoCorretor.setVisible(false);
		chkBoxContratosComoCorretor.setFont(new Font("SansSerif", Font.BOLD, 14));
		chkBoxContratosComoCorretor.setForeground(Color.WHITE);
		panel_3.add(chkBoxContratosComoCorretor, "cell 0 6 4 1,alignx center,aligny top");
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

		JLabel lblNewLabel_2 = new JLabel("Opções:");
		lblNewLabel_2.setForeground(Color.WHITE);
		panel_3.add(lblNewLabel_2, "cell 0 7,alignx left,aligny top");
		lblNewLabel_2.setFont(new Font("SansSerif", Font.BOLD, 16));

		chkBoxUnirContratos = new JCheckBox("Unir");
		chkBoxUnirContratos.setEnabled(false);
		chkBoxUnirContratos.setForeground(Color.WHITE);
		chkBoxUnirContratos.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_3.add(chkBoxUnirContratos, "cell 0 8 4 1,alignx center,aligny center");

		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_4.setBackground(Color.WHITE);
		painelPrincipal.add(panel_4, "cell 0 0 7 1,grow");
		panel_4.setLayout(new MigLayout("", "[][400px,grow][]", "[][][][][][][]"));

		JLabel lblNewLabel = new JLabel("Safra:");
		panel_4.add(lblNewLabel, "cell 0 0,alignx right");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));

		cBSafra = new JComboBox();
		panel_4.add(cBSafra, "cell 1 0,growx");
		cBSafra.setEnabled(false);
		cBSafra.setModel(modelSafra);
		cBSafra.setRenderer(cBSafraPersonalizado);

		chckbxTodasAsSafras = new JCheckBox("Todas as Safras");
		panel_4.add(chckbxTodasAsSafras, "cell 2 0");
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

		JLabel lblNewLabel_1 = new JLabel("Alvo 1:");
		panel_4.add(lblNewLabel_1, "cell 0 1,alignx right");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));

		cBAlvo = new JComboBox();
		cBAlvo.addItem("TODOS");
		cBAlvo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent evt) {
				if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
					if (evt.getItem().equals("TODOS")) {

						setClienteAlvo(null);

					}
				}
			}
		});
		panel_4.add(cBAlvo, "cell 1 1,growx");

		JButton btnNewButton_1 = new JButton("Selecionar");
		panel_4.add(btnNewButton_1, "cell 2 1,growx");

		lblNewLabel_6 = new JLabel("Alvo 2:");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_4.add(lblNewLabel_6, "cell 0 2,alignx trailing");

		cBAlvo2 = new JComboBox();
		cBAlvo2.addItem("TODOS");
		cBAlvo2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent evt) {
				if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
					if (evt.getItem().equals("TODOS")) {

						setClienteAlvo2(null);

					}
				}
			}
		});
		panel_4.add(cBAlvo2, "cell 1 2,growx");

		btnNewButton_2 = new JButton("Selecionar");

		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaCliente cliente = new TelaCliente(0, 32, null);
				cliente.setTelaPai(isto);
				cliente.setVisible(true);
			}
		});
		panel_4.add(btnNewButton_2, "cell 2 2,growx");

		JLabel lblNewLabel_1_1 = new JLabel("Contra Parte:");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_4.add(lblNewLabel_1_1, "cell 0 3,alignx right");

		cBContraParte1 = new JComboBox();
		cBContraParte1.addItem("TODOS");
		cBContraParte1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent evt) {
				if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
					if (evt.getItem().equals("TODOS")) {

						setClienteContraParte1(null);

					}
				}
			}

		});
		panel_4.add(cBContraParte1, "cell 1 3,growx");

		JButton btnNewButton_1_1 = new JButton("Selecionar");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCliente cliente = new TelaCliente(0, 30, null);
				cliente.setTelaPai(isto);
				cliente.setVisible(true);

			}
		});
		panel_4.add(btnNewButton_1_1, "cell 2 3,growx");

		lblNewLabel_1_2 = new JLabel("Participação:");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_4.add(lblNewLabel_1_2, "cell 0 4,alignx trailing");

		cBParticipacao = new JComboBox();
		panel_4.add(cBParticipacao, "cell 1 4,growx");
		cBParticipacao.addItem("TODOS");
		cBParticipacao.addItem("GRUPO");
		cBParticipacao.addItem("PARTICULAR");

		lblNewLabel_7 = new JLabel("Formato da Saída:");
		lblNewLabel_7.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_4.add(lblNewLabel_7, "cell 0 5");

		rdbtnFormatoPdf = new JRadioButton("PDF");
		panel_4.add(rdbtnFormatoPdf, "flowx,cell 1 5");
		rdbtnFormatoPdf.setSelected(true);
		rdbtnFormatoPdf.setFont(new Font("SansSerif", Font.BOLD, 16));

		rdbtnFormatoWord = new JRadioButton("WORD");
		panel_4.add(rdbtnFormatoWord, "cell 1 5");
		rdbtnFormatoWord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				rdbtnFormatoWord.setSelected(true);
				rdbtnFormatoPdf.setSelected(false);

			}
		});
		rdbtnFormatoWord.setFont(new Font("SansSerif", Font.BOLD, 16));

		rdbtnFormatoPdf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				rdbtnFormatoWord.setSelected(false);
				rdbtnFormatoPdf.setSelected(true);

			}
		});

		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.WHITE);
		painelPrincipal.add(panel_5, "cell 0 4 7 1,grow");
		panel_5.setLayout(new MigLayout("", "[239px,grow][203px]", "[][grow]"));

		JButton btnNewButton = new JButton("Gerar Relatorio Completo - Impressão");
		btnNewButton.setBackground(new Color(0, 51, 0));
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_5.add(btnNewButton, "cell 0 1 2 1,alignx right,aligny top");
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
				boolean incluir_transferencia_pagamentos = false;
				boolean unir_pagamentos = false;
				boolean incluir_comissao_pagamento = false;
				int participacao = -1;
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
						} else if (chckbxExternoVendedor.isSelected()) {
							sub_contratos = false;
						}
					}

					if (chkBoxContratosComoComprador.isSelected() || chkBoxContratosComoVendedor.isSelected()) {
						if (chckbxInterno.isSelected()) {

							if (chckbxIncluirValorComissao.isSelected()) {
								incluir_comissao = true;
							}
						} else if (chckbxExternoVendedor.isSelected()) {
							incluir_comissao = false;
						}
					}

					if (chkBoxContratosComoComprador.isSelected() || chkBoxContratosComoVendedor.isSelected()) {
						if (chckbxInterno.isSelected()) {

							if (chckbxIncluirSubContratos.isSelected()) {
								if (chckbxIncluirGanhosPotenciais.isSelected()) {
									incluir_ganhos_potencias = true;
								}
							}
						} else if (chckbxExternoVendedor.isSelected()) {
							incluir_ganhos_potencias = false;

						}
					}

					if (chkBoxContratosComoComprador.isSelected() || chkBoxContratosComoVendedor.isSelected()) {
						if (chckbxInterno.isSelected()) {

							if (chckbxSomarSubContratos.isSelected()) {
								somar_sub_contratos = true;

							}
						} else if (chckbxExternoVendedor.isSelected()) {
							somar_sub_contratos = false;
						}
					}

					if (chkBoxUnirContratos.isSelected()) {
						unir_contratos = true;
					} else {
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

					if (chckbxIncluirTransfrenciasPagamentos.isSelected()) {
						incluir_transferencia_pagamentos = true;
					} else {
						incluir_transferencia_pagamentos = false;

					}

					if (chckbxUnirPagamentos.isSelected()) {
						unir_pagamentos = true;
					} else {
						unir_pagamentos = false;

					}

					if (chckbxIncluirComissao.isSelected()) {
						incluir_comissao_pagamento = true;
					} else {
						incluir_comissao_pagamento = false;
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
				CadastroCliente contra_parte = new CadastroCliente();
				CadastroCliente cliente_alvo2_relatorio = new CadastroCliente();

				CadastroCliente todos = new CadastroCliente();
				todos.setTipo_pessoa(1);
				todos.setNome_fantaia("TODOS");
				todos.setId(0);

				if (cBAlvo.getSelectedItem().equals("TODOS")) {

					clientes.add(todos);

					if (cBAlvo2.getSelectedItem().equals("TODOS")) {
						cliente_alvo2_relatorio = todos;
					} else {
						if (cliente_alvo2 != null) {
							cliente_alvo2_relatorio = cliente_alvo2;

						}
					}

					if (cBContraParte1.getSelectedItem().equals("TODOS")) {
						contra_parte = todos;
					} else {
						if (contra_parte1 != null) {
							contra_parte = contra_parte1;
						}

					}

				} else if (cliente_alvo != null || grupo_alvo == null) {
					// um clinete apneas adicionado
					if (cliente_alvo != null) {
						clientes.add(cliente_alvo);

					}

					if (cliente_alvo2 != null && cliente_alvo == null) {
						// cliente_alvo2_relatorio = cliente_alvo2;
						clientes.add(cliente_alvo2);
					} else if (cliente_alvo2 != null && cliente_alvo != null) {
						cliente_alvo2_relatorio = cliente_alvo2;
					}
					if (contra_parte1 != null) {
						contra_parte = contra_parte1;
					}

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
					if (cliente_alvo2 != null) {
						cliente_alvo2_relatorio = cliente_alvo2;
					}
					if (contra_parte1 != null) {
						contra_parte = contra_parte1;
					}

				}
				// participacao
				participacao = cBParticipacao.getSelectedIndex() - 1;

				if (chckbxInterno.isSelected()) {
					tipo_contrato = 1;
				} else if (chckbxExternoComprador.isSelected()) {
					tipo_contrato = 2;
				} else if (chckbxExternoVendedor.isSelected()) {
					tipo_contrato = 1;
				}
				if (gerar && chkBoxContratosComoComprador.isSelected()) {
					telaEmEsperaRelatoria = new TelaEmEsperaRelatoria(isto);

					new Thread() {
						@Override
						public void run() {
							telaEmEsperaRelatoria.setVisible(true);
						}
					}.start();
					RelatorioContratos relatar = new RelatorioContratos(tipo_contrato, contrato, unir_contratos, true,
							pagamento, pagamento_como_despositante, pagamento_como_favorecido, incluir_sem_pagamentos,
							incluir_transferencia_pagamentos, unir_pagamentos, incluir_comissao_pagamento, carregamento,
							carregamento_como_comprador, carregamento_como_vendedor, unir_carregamentos,
							controle_nf_venda_carregamentos, incluir_transferencia_carregamentos,
							incluir_sem_carregamentos, recebimento, recebimento_como_comprador,
							recebimento_como_vendedor, unir_recebimentos, controle_nf_venda_recebimentos,
							incluir_sem_recebimentos, id_safra, sub_contratos, incluir_comissao,
							incluir_ganhos_potencias, somar_sub_contratos, clientes, contra_parte,
							cliente_alvo2_relatorio, grupo_alvo, participacao);

					new Thread() {
						@Override
						public void run() {

							relatar.setTelaEmEsperaRelatoria(telaEmEsperaRelatoria);

							relatar(isto, relatar);
						}
					}.start();

				} else if (gerar && chkBoxContratosComoVendedor.isSelected()) {
					telaEmEsperaRelatoria = new TelaEmEsperaRelatoria(isto);

					new Thread() {
						@Override
						public void run() {
							telaEmEsperaRelatoria.setVisible(true);
						}
					}.start();
					RelatorioContratos relatar = new RelatorioContratos(tipo_contrato, contrato, unir_contratos, false,
							pagamento, pagamento_como_despositante, pagamento_como_favorecido, incluir_sem_pagamentos,
							incluir_transferencia_pagamentos, unir_pagamentos, incluir_comissao_pagamento, carregamento,
							carregamento_como_comprador, carregamento_como_vendedor, unir_carregamentos,
							controle_nf_venda_carregamentos, incluir_transferencia_carregamentos,
							incluir_sem_carregamentos, recebimento, recebimento_como_comprador,
							recebimento_como_vendedor, unir_recebimentos, controle_nf_venda_recebimentos,
							incluir_sem_recebimentos, id_safra, sub_contratos, incluir_comissao,
							incluir_ganhos_potencias, somar_sub_contratos, clientes, contra_parte,
							cliente_alvo2_relatorio, grupo_alvo, participacao);

					new Thread() {
						@Override
						public void run() {

							relatar.setTelaEmEsperaRelatoria(telaEmEsperaRelatoria);

							relatar(isto, relatar);
						}
					}.start();

				} else if (gerar && !chkBoxContratosComoVendedor.isSelected()
						&& !chkBoxContratosComoComprador.isSelected()) {

					telaEmEsperaRelatoria = new TelaEmEsperaRelatoria(isto);

					new Thread() {
						@Override
						public void run() {
							telaEmEsperaRelatoria.setVisible(true);
						}
					}.start();
					RelatorioContratos relatar = new RelatorioContratos(tipo_contrato, contrato, unir_contratos, false,
							pagamento, pagamento_como_despositante, pagamento_como_favorecido, incluir_sem_pagamentos,
							incluir_transferencia_pagamentos, unir_pagamentos, incluir_comissao_pagamento, carregamento,
							carregamento_como_comprador, carregamento_como_vendedor, unir_carregamentos,
							controle_nf_venda_carregamentos, incluir_transferencia_carregamentos,
							incluir_sem_carregamentos, recebimento, recebimento_como_comprador,
							recebimento_como_vendedor, unir_recebimentos, controle_nf_venda_recebimentos,
							incluir_sem_recebimentos, id_safra, sub_contratos, incluir_comissao,
							incluir_ganhos_potencias, somar_sub_contratos, clientes, contra_parte,
							cliente_alvo2_relatorio, grupo_alvo, participacao);

					new Thread() {
						@Override
						public void run() {

							relatar.setTelaEmEsperaRelatoria(telaEmEsperaRelatoria);

							relatar(isto, relatar);
						}
					}.start();

				}

			}
		});
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaCliente cliente = new TelaCliente(0, 11, null);
				cliente.setTelaPai(isto);
				cliente.setVisible(true);

			}
		});

		cBSafra.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CadastroSafra produto = (CadastroSafra) modelSafra.getSelectedItem();

			}

		});
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
		chckbxInterno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				chckbxInterno.setSelected(true);
				chckbxExternoComprador.setSelected(false);
				chckbxExternoVendedor.setSelected(false);

				painelOpcaosInternas.setEnabled(true);
				painelOpcaosInternas.setVisible(true);

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

				if (cliente != null) {

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
					cBAlvo.addItem("TODOS");

					cBAlvo.updateUI();
					cBAlvo.repaint();

					grupo_alvo = null;
				}

			}
		});
	}

	public void setClienteAlvo2(CadastroCliente cliente2) {
		this.cliente_alvo2 = cliente2;

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {

				if (cliente2 != null) {
					cBAlvo2.removeAllItems();
					cBAlvo2.updateUI();
					cBAlvo2.repaint();

					String nome = "";

					if (cliente_alvo2.getTipo_pessoa() == 0) {
						nome = cliente_alvo2.getNome_empresarial();
					} else {
						nome = cliente_alvo2.getNome_fantaia();

					}

					cBAlvo2.addItem(cliente_alvo2.getId() + " " + nome);
					cBAlvo2.addItem("TODOS");
					cBAlvo2.updateUI();
					cBAlvo2.repaint();

				}
			}
		});
	}

	public void setClienteContraParte1(CadastroCliente cliente2) {
		this.contra_parte1 = cliente2;

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {

				if (cliente2 != null) {
					cBContraParte1.removeAllItems();
					cBContraParte1.updateUI();
					cBContraParte1.repaint();

					String nome = "";

					if (contra_parte1.getTipo_pessoa() == 0) {
						nome = contra_parte1.getNome_empresarial();
					} else {
						nome = contra_parte1.getNome_fantaia();

					}

					cBContraParte1.addItem(contra_parte1.getId() + " " + nome);
					cBContraParte1.addItem("TODOS");
					cBContraParte1.updateUI();
					cBContraParte1.repaint();
				}
			}
		});
	}

	public void relatar(Window isto, RelatorioContratos relatar) {

		String contrato_alterado = relatar.preparar();
		if (rdbtnFormatoPdf.isSelected()) {
			ConverterPdf converter_pdf = new ConverterPdf();
			String pdf_alterado = converter_pdf.word_pdf_file2(contrato_alterado);
			telaEmEsperaRelatoria.setInfo("Arquivo PDF Criado, abrindo...", 100);
			telaEmEsperaRelatoria.dispose();
			TelaVizualizarPdf vizualizar = new TelaVizualizarPdf(null, isto, null, pdf_alterado, null, isto);
		} else if (rdbtnFormatoWord.isSelected()) {
			telaEmEsperaRelatoria.setInfo("Arquivo Word Criado, abrindo...", 100);
			telaEmEsperaRelatoria.dispose();
			gerarWord(contrato_alterado);
		}

	}

	public void relatarRecebimentoSimplificado(Window isto, RelatorioContratoRecebimentoSimplificado relatar) {

		String contrato_alterado = relatar.preparar();
		if (rdbtnFormatoPdf.isSelected()) {
			ConverterPdf converter_pdf = new ConverterPdf();
			String pdf_alterado = converter_pdf.word_pdf_file2(contrato_alterado);
			telaEmEsperaRelatoria.setInfo("Arquivo PDF Criado, abrindo...", 100);
			telaEmEsperaRelatoria.dispose();
			TelaVizualizarPdf vizualizar = new TelaVizualizarPdf(null, isto, null, pdf_alterado, null, isto);
		} else if (rdbtnFormatoWord.isSelected()) {
			telaEmEsperaRelatoria.setInfo("Arquivo Excel Criado, abrindo...", 100);
			telaEmEsperaRelatoria.dispose();
			gerarWord(contrato_alterado);
		}

	}

	public void relatarCargaSimplificado(Window isto, RelatorioContratoCarregamentoSimplificado relatar) {

		String contrato_alterado = relatar.preparar();
		if (rdbtnFormatoPdf.isSelected()) {
			ConverterPdf converter_pdf = new ConverterPdf();
			String pdf_alterado = converter_pdf.word_pdf_file2(contrato_alterado);
			telaEmEsperaRelatoria.setInfo("Arquivo PDF Criado, abrindo...", 100);
			telaEmEsperaRelatoria.dispose();
			TelaVizualizarPdf vizualizar = new TelaVizualizarPdf(null, isto, null, pdf_alterado, null, isto);
		} else if (rdbtnFormatoWord.isSelected()) {
			telaEmEsperaRelatoria.setInfo("Arquivo Excel Criado, abrindo...", 100);
			telaEmEsperaRelatoria.dispose();
			gerarWord(contrato_alterado);
		}

	}

	public void gerarWord(String url_origem) {
		try {

			new JFXPanel();
			Platform.runLater(() -> {

				// pegar ultima pasta
				ManipularTxt manipular_ultima_pasta = new ManipularTxt();
				String ultima_pasta = manipular_ultima_pasta
						.lerArquivo(new File("C:\\ProgramData\\E-Contract\\configs\\ultima_pasta.txt"));
				if (fileChooser == null) {
					fileChooser = new FileChooser();
				}
				fileChooser.setInitialDirectory(new File(ultima_pasta));
				fileChooser.getExtensionFilters().addAll(

						new FileChooser.ExtensionFilter("Word", "*.docx"));
				File file = fileChooser.showSaveDialog(new Stage());
				String caminho_arquivo = "";
				if (file != null) {
					caminho_arquivo = file.getAbsolutePath();

					manipular_ultima_pasta.rescreverArquivo(
							new File("C:\\ProgramData\\E-Contract\\configs\\ultima_pasta.txt"), file.getParent());
					// Escrevendo o arquivo em disco
					FileOutputStream out;
					try {
						manipular_ultima_pasta.copiarNFe(url_origem, caminho_arquivo);
						Runtime.getRuntime().exec("explorer " + caminho_arquivo);

						System.out.println("Success!!");
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

			});

		} catch (Exception k) {
			k.printStackTrace();
		}
	}

	public void gerarExcel(String url_origem) {
		try {

			new JFXPanel();
			Platform.runLater(() -> {

				// pegar ultima pasta
				ManipularTxt manipular_ultima_pasta = new ManipularTxt();
				String ultima_pasta = manipular_ultima_pasta
						.lerArquivo(new File("C:\\ProgramData\\E-Contract\\configs\\ultima_pasta.txt"));
				if (fileChooser == null) {
					fileChooser = new FileChooser();
				}
				fileChooser.setInitialDirectory(new File(ultima_pasta));
				fileChooser.getExtensionFilters().addAll(

						new FileChooser.ExtensionFilter("Excel", "*.xlsx"));
				File file = fileChooser.showSaveDialog(new Stage());
				String caminho_arquivo = "";
				if (file != null) {
					caminho_arquivo = file.getAbsolutePath();

					manipular_ultima_pasta.rescreverArquivo(
							new File("C:\\ProgramData\\E-Contract\\configs\\ultima_pasta.txt"), file.getParent());
					// Escrevendo o arquivo em disco
					FileOutputStream out;
					try {
						manipular_ultima_pasta.copiarNFe(url_origem, caminho_arquivo);
						Runtime.getRuntime().exec("explorer " + caminho_arquivo);

						System.out.println("Success!!");
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

			});

		} catch (Exception k) {
			k.printStackTrace();
		}
	}

}
