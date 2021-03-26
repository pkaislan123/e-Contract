package gui;

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

import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import net.miginfocom.swing.MigLayout;
import relatoria.RelatorioContratoIndividual;
import relatoria.RelatorioContratoSimplificado;
import relatoria.RelatorioContratos;
import relatoria.RelatorioContratoComprador;
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
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import cadastros.CadastroCliente;
import cadastros.CadastroContrato;
import cadastros.CadastroGrupo;
import cadastros.CadastroSafra;
import classesExtras.ComboBoxPersonalizado;
import classesExtras.ComboBoxRenderPersonalizado;
import conexaoBanco.GerenciarBancoClientes;
import conexaoBanco.GerenciarBancoSafras;
import manipular.ConverterPdf;

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
			chkBoxComoFavorecido, chckbxCarregamentos, chckbxCarregamentpComoVendedor, chckbxCarregamentoComoComprador;

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

private  JCheckBox chckbxRecebimentos, chckbxRecebimentoComoComprador ,chckbxRecebimentoComoVendedor;
private JCheckBox chckbxUnirRecebimentos;


	public static void pesquisarSafras() {
		GerenciarBancoSafras listaSafras = new GerenciarBancoSafras();
		safras = listaSafras.getSafras();
	}

	public TelaRelatoriaContratos(Window janela_pai) {
		//setModal(true);

		TelaRelatoriaContratos isto = this;

		setResizable(true);
		setTitle("E-Contract - Relatoria");

		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1066, 618);
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
						contrato_como_corretor = false;
				boolean pagamento = false, pagamento_como_despositante = false, pagamento_como_favorecido = false;
				boolean carregamento = false, carregamento_como_comprador = false, carregamento_como_vendedor = false;
				boolean recebimento = false, recebimento_como_comprador = false, recebimento_como_vendedor = false, unir_recebimentos = false;

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

				}

				if (chckbxCarregamentos.isSelected()) {
					carregamento = true;

					if (chckbxCarregamentoComoComprador.isSelected()) {
						carregamento_como_comprador = true;
					}

					if (chckbxCarregamentpComoVendedor.isSelected()) {
						carregamento_como_vendedor = true;
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
					
					if(chckbxUnirRecebimentos.isSelected()) {
						unir_recebimentos = true;
					}else {
						unir_recebimentos = false;

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
					RelatorioContratoComprador relatar = new RelatorioContratoComprador();
					relatar.setId_safra(id_safra);
					relatar.setContrato(contrato);
					relatar.setContrato_como_comprador(true);
					relatar.setTipo_contrato(tipo_contrato);
					relatar.setSub_contratos(sub_contratos);
					relatar.setIncluir_comissao(incluir_comissao);
					relatar.setIncluir_ganhos_potencias(incluir_ganhos_potencias);
					relatar.setSomar_sub_contratos(somar_sub_contratos);
					relatar.setClientes_globais(clientes);
					relatar.setGrupo_alvo_global(grupo_alvo);
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

				} else if (gerar && chkBoxContratosComoVendedor.isSelected()) {
					RelatorioContratos relatar = new RelatorioContratos(tipo_contrato, contrato, false, pagamento,
							pagamento_como_despositante, pagamento_como_favorecido, carregamento,
							carregamento_como_comprador, carregamento_como_vendedor, recebimento, recebimento_como_comprador, recebimento_como_vendedor,unir_recebimentos, id_safra, sub_contratos,
							incluir_comissao, incluir_ganhos_potencias, somar_sub_contratos, clientes, grupo_alvo);
					ByteArrayOutputStream contrato_alterado = relatar.preparar();

					ConverterPdf converter_pdf = new ConverterPdf();
					String pdf_alterado = converter_pdf.word_pdf_stream(contrato_alterado);
					TelaVizualizarPdf vizualizar = new TelaVizualizarPdf(null, isto, null, pdf_alterado, null, isto);
					
				}else if (gerar && !chkBoxContratosComoVendedor.isSelected() && !chkBoxContratosComoComprador.isSelected()) {
					RelatorioContratos relatar = new RelatorioContratos(tipo_contrato, contrato, false, pagamento,
							pagamento_como_despositante, pagamento_como_favorecido, carregamento,
							carregamento_como_comprador, carregamento_como_vendedor, recebimento, recebimento_como_comprador, recebimento_como_vendedor,unir_recebimentos, id_safra, sub_contratos,
							incluir_comissao, incluir_ganhos_potencias, somar_sub_contratos, clientes, grupo_alvo);
					ByteArrayOutputStream contrato_alterado = relatar.preparar();

					ConverterPdf converter_pdf = new ConverterPdf();
					String pdf_alterado = converter_pdf.word_pdf_stream(contrato_alterado);
					TelaVizualizarPdf vizualizar = new TelaVizualizarPdf(null, isto, null, pdf_alterado, null, isto);
				}

			}
		});
		btnNewButton.setBounds(869, 525, 169, 28);
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

		JLabel lblNewLabel_2 = new JLabel("Opções:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_2.setBounds(24, 198, 51, 23);
		painelPrincipal.add(lblNewLabel_2);

		chkBoxContratos = new JCheckBox("Contratos");
		chkBoxContratos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (chkBoxContratos.isSelected()) {
					chkBoxContratos.setSelected(true);
					chkBoxContratosComoComprador.setEnabled(true);
					chkBoxContratosComoVendedor.setEnabled(true);
					chkBoxContratosComoCorretor.setEnabled(true);

				} else {
					chkBoxContratos.setSelected(false);
					chkBoxContratosComoComprador.setEnabled(false);
					chkBoxContratosComoVendedor.setEnabled(false);
					chkBoxContratosComoCorretor.setEnabled(false);
					chckbxIncluirValorComissao.setEnabled(false);
					chckbxIncluirSubContratos.setEnabled(false);
					chckbxIncluirGanhosPotenciais.setEnabled(false);
					chckbxSomarSubContratos.setEnabled(false);

				}

			}
		});
		chkBoxContratos.setBounds(34, 228, 110, 23);
		painelPrincipal.add(chkBoxContratos);

		chkBoxContratosComoComprador = new JCheckBox("Alvo como Comprador");
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
		chkBoxContratosComoComprador.setBounds(57, 261, 168, 23);
		painelPrincipal.add(chkBoxContratosComoComprador);

		chkBoxContratosComoVendedor = new JCheckBox("Alvo como Vendedor");
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
		chkBoxContratosComoVendedor.setBounds(57, 297, 168, 23);
		painelPrincipal.add(chkBoxContratosComoVendedor);

		chkBoxContratosComoCorretor = new JCheckBox("Alvo como Corretor");
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
		chkBoxContratosComoCorretor.setBounds(57, 335, 168, 23);
		painelPrincipal.add(chkBoxContratosComoCorretor);

		chckbxPagamentos = new JCheckBox("Pagamentos");
		chckbxPagamentos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (chckbxPagamentos.isSelected()) {
					chckbxPagamentos.setSelected(true);
					chkBoxComoDepositante.setEnabled(true);
					chkBoxComoFavorecido.setEnabled(true);
				} else {
					chckbxPagamentos.setSelected(false);
					chkBoxComoDepositante.setEnabled(false);
					chkBoxComoFavorecido.setEnabled(false);
				}

			}
		});
		chckbxPagamentos.setBounds(280, 172, 110, 23);
		painelPrincipal.add(chckbxPagamentos);

		chkBoxComoDepositante = new JCheckBox("Alvo como Depositante");
		chkBoxComoDepositante.setEnabled(false);
		chkBoxComoDepositante.setBounds(304, 205, 168, 23);
		painelPrincipal.add(chkBoxComoDepositante);

		chkBoxComoFavorecido = new JCheckBox("Alvo como Favorecido");
		chkBoxComoFavorecido.setEnabled(false);
		chkBoxComoFavorecido.setBounds(304, 241, 168, 23);
		painelPrincipal.add(chkBoxComoFavorecido);

		chckbxCarregamentos = new JCheckBox("Carregamentos");
		chckbxCarregamentos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (chckbxCarregamentos.isSelected()) {
					chckbxCarregamentos.setSelected(true);
					chckbxCarregamentoComoComprador.setEnabled(true);
					chckbxCarregamentpComoVendedor.setEnabled(true);
				} else {
					chckbxCarregamentos.setSelected(false);
					chckbxCarregamentoComoComprador.setEnabled(false);
					chckbxCarregamentpComoVendedor.setEnabled(false);
				}
			}
		});
		chckbxCarregamentos.setBounds(550, 172, 120, 23);
		painelPrincipal.add(chckbxCarregamentos);

		chckbxCarregamentoComoComprador = new JCheckBox("Alvo como Comprador");
		chckbxCarregamentoComoComprador.setEnabled(false);
		chckbxCarregamentoComoComprador.setBounds(574, 205, 168, 23);
		painelPrincipal.add(chckbxCarregamentoComoComprador);

		chckbxCarregamentpComoVendedor = new JCheckBox("Alvo como Vendedor");
		chckbxCarregamentpComoVendedor.setEnabled(false);
		chckbxCarregamentpComoVendedor.setBounds(574, 241, 168, 23);
		painelPrincipal.add(chckbxCarregamentpComoVendedor);

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

		lblNewLabel_3 = new JLabel("Tipo:");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_3.setBounds(24, 141, 51, 23);
		painelPrincipal.add(lblNewLabel_3);

		chckbxInterno = new JCheckBox("Interno");
		chckbxInterno.setSelected(true);
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
		chckbxInterno.setBounds(34, 172, 61, 23);
		painelPrincipal.add(chckbxInterno);

		chckbxExterno = new JCheckBox("Externo");
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
		chckbxExterno.setBounds(108, 172, 63, 23);
		painelPrincipal.add(chckbxExterno);

		painelOpcaosInternas = new JPanel();
		painelOpcaosInternas.setBackground(Color.WHITE);
		painelOpcaosInternas.setBounds(87, 365, 431, 188);
		painelPrincipal.add(painelOpcaosInternas);
		painelOpcaosInternas.setLayout(null);

		chckbxSomarSubContratos = new JCheckBox("Somar Sub-Contratos");
		chckbxSomarSubContratos.setBounds(6, 35, 168, 23);
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
		lblNewLabel_4.setBounds(7, 65, 405, 14);
		painelOpcaosInternas.add(lblNewLabel_4);
		lblNewLabel_4.setForeground(Color.RED);
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 9));

		chckbxIncluirValorComissao = new JCheckBox("Incluir Comissão");
		chckbxIncluirValorComissao.setBounds(7, 86, 168, 23);
		painelOpcaosInternas.add(chckbxIncluirValorComissao);
		chckbxIncluirValorComissao.setEnabled(false);

		chckbxIncluirSubContratos = new JCheckBox("Incluir Sub-Contratos");
		chckbxIncluirSubContratos.setBounds(7, 112, 168, 23);
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
		chckbxIncluirGanhosPotenciais.setBounds(39, 141, 168, 23);
		painelOpcaosInternas.add(chckbxIncluirGanhosPotenciais);
		chckbxIncluirGanhosPotenciais.setEnabled(false);

		lblNewLabel_5 = new JLabel("Opções para relatorio interno:");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_5.setBounds(10, 11, 182, 17);
		painelOpcaosInternas.add(lblNewLabel_5);
		
		 chckbxRecebimentos = new JCheckBox("Recebimentos");
		chckbxRecebimentos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxRecebimentos.isSelected()) {
					chckbxRecebimentos.setSelected(true);
					chckbxRecebimentoComoComprador .setEnabled(true);
					chckbxRecebimentoComoVendedor.setEnabled(true);
					chckbxUnirRecebimentos.setEnabled(true);
				} else {
					chckbxRecebimentos.setSelected(false);
					chckbxRecebimentoComoComprador .setEnabled(false);
					chckbxRecebimentoComoVendedor.setEnabled(false);
					chckbxUnirRecebimentos.setEnabled(false);
				}
			}
		});
		chckbxRecebimentos.setBounds(780, 172, 120, 23);
		painelPrincipal.add(chckbxRecebimentos);
		
		 chckbxRecebimentoComoComprador = new JCheckBox("Alvo como Comprador");
		chckbxRecebimentoComoComprador.setEnabled(false);
		chckbxRecebimentoComoComprador.setBounds(804, 205, 168, 23);
		painelPrincipal.add(chckbxRecebimentoComoComprador);
		
		 chckbxRecebimentoComoVendedor = new JCheckBox("Alvo como Vendedor");
		chckbxRecebimentoComoVendedor.setEnabled(false);
		chckbxRecebimentoComoVendedor.setBounds(804, 241, 168, 23);
		painelPrincipal.add(chckbxRecebimentoComoVendedor);
		
		chckbxUnirRecebimentos = new JCheckBox("Unir");
		chckbxUnirRecebimentos.setEnabled(false);
		chckbxUnirRecebimentos.setBounds(830, 277, 168, 23);
		painelPrincipal.add(chckbxUnirRecebimentos);
		
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
				boolean recebimento = false, recebimento_como_comprador = false, recebimento_como_vendedor = false, unir_recebimentos = false;

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
					
					if(chckbxUnirRecebimentos.isSelected()) {
						unir_recebimentos = true;
					}else {
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

			
				if (gerar ) {
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
		btnGerarRelatorioSimplificado.setBounds(804, 332, 183, 28);
		painelPrincipal.add(btnGerarRelatorioSimplificado);

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
