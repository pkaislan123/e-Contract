package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
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
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.Window;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import main.java.cadastros.CadastroAcessoTemporario;
import main.java.cadastros.CadastroAditivo;
import main.java.cadastros.CadastroAviso;
import main.java.cadastros.CadastroBaseArquivos;
import main.java.cadastros.CadastroBaseDados;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroContrato.CadastroPagamento;
import main.java.cadastros.CadastroContrato.CadastroPagamentoContratual;
import main.java.cadastros.CadastroContrato.CadastroTarefa;
import main.java.cadastros.CadastroContrato.Recebimento;
import main.java.cadastros.CadastroDocumento;
import main.java.cadastros.CadastroFuncionario;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroLogin.Mensagem;
import main.java.cadastros.CadastroModelo;
import main.java.cadastros.CadastroNFe;
import main.java.cadastros.CadastroNota;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroRomaneio;
import main.java.cadastros.CadastroSafra;
import main.java.cadastros.CadastroSilo;
import main.java.cadastros.ContaBancaria;
import main.java.cadastros.Contato;
import main.java.cadastros.DadosCarregamento;
import main.java.cadastros.DadosContratos;
import main.java.cadastros.DadosRecebimento;
import main.java.cadastros.FinanceiroConta;
import main.java.cadastros.FinanceiroGrupoContas;
import main.java.cadastros.FinanceiroConta;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.classesExtras.Endereco;
import main.java.classesExtras.RenderizadorContato;
import main.java.conexaoBanco.GerenciarBancoAditivos;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoDocumento;
import main.java.conexaoBanco.GerenciarBancoNotasFiscais;
import main.java.conexaoBanco.GerenciarBancoPadrao;
import main.java.conexaoBanco.GerenciarBancoPontuacao;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoRomaneios;
import main.java.conexaoBanco.GerenciarBancoSafras;
import main.java.conexaoBanco.GerenciarBancoSilo;
import main.java.conexaoBanco.GerenciarBancoTransferencias;
import main.java.conexaoBanco.GerenciarBancoTransferenciasCarga;
import main.java.conexoes.TesteConexao;
import main.java.graficos.GraficoLinha;
import main.java.graficos.JPanelGrafico;
import main.java.graficos.JPanelGraficoCarregamento;
import main.java.graficos.JPanelGraficoRecebimento;
import main.java.gui.TelaCadastroCliente;
import main.java.gui.TelaMain;
import main.java.gui.TelaRomaneios;
import main.java.manipular.ArquivoConfiguracoes;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.manipular.ConverterPdf;
import main.java.manipular.CopiarArquivo;
import main.java.manipular.EditarAditivo;
import main.java.manipular.EditarExcel;
import main.java.manipular.EditarWord;
import main.java.manipular.Email;
import main.java.manipular.GetDadosGlobais;
import main.java.manipular.ManipularArquivoTerceiros;
import main.java.manipular.ManipularNotasFiscais;
import main.java.manipular.ManipularRomaneios;
import main.java.manipular.ManipularTxt;
import main.java.manipular.MonitorarRomaneios;
import main.java.manipular.Nuvem;
import main.java.manipular.PorExtenso;
import main.java.manipular.Whatsapp;
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
import net.miginfocom.swing.MigLayout;
import outros.ValidaCNPj;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroNuvem;
import main.java.cadastros.CadastroPontuacao;
import main.java.cadastros.CadastroZapMessenger;
import main.java.outros.DadosGlobais;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroGrupo;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoGrupos;
import main.java.conexaoBanco.GerenciarBancoFinanceiroConta;
import main.java.conexaoBanco.GerenciarBancoFinanceiroGrupoContas;
import main.java.conexaoBanco.GerenciarBancoFuncionarios;
import main.java.conexaoBanco.GerenciarBancoLogin;
import main.java.conexaoBanco.GerenciarBancoNotas;
import main.java.cadastros.CadastroProduto;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroSafra;
import main.java.classesExtras.CBLocalRetiradaPersonalizado;
import main.java.classesExtras.CBLocalRetiradaRenderPersonalizado;
import main.java.classesExtras.CBProdutoPersonalizado;
import main.java.classesExtras.CBProdutoRenderPersonalizado;
import main.java.classesExtras.ComboBoxContato;
import main.java.classesExtras.ComboBoxPersonalizado;
import main.java.classesExtras.ComboBoxPersonalizadoContaBancaria;
import main.java.classesExtras.ComboBoxPersonalizadoFuncionario;
import main.java.classesExtras.ComboBoxPersonalizadoUsuario;
import main.java.classesExtras.ComboBoxRenderPersonalizado;
import main.java.classesExtras.ComboBoxRenderPersonalizadoContaBancaria;
import main.java.classesExtras.ComboBoxRenderPersonalizadoFuncionario;
import main.java.classesExtras.ComboBoxRenderPersonalizadoUsuario;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoSafras;

import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;
import javax.swing.JEditorPane;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TelaAlterarContrato extends JFrame {

	private final JPanel painelPrincipal = new JPanel();
	private final JPanel painelOdin = new JPanel();

	private JLabel lblTotalContratosConcluidos, lblTotalContratos, lblTotalContratosAbertos;
	private TelaAlterarContrato isto;
	private JDialog telaPai;
	private final JPanel panel = new JPanel();
	private final JLabel lblNewLabel = new JLabel("Alteração de Contrato Mediante Distrato");
	private FinanceiroGrupoContas financeiro_grupo_contas;
	private ComboBoxPersonalizadoUsuario modelUsuarios = new ComboBoxPersonalizadoUsuario();
	private ComboBoxRenderPersonalizadoUsuario cbUsuariosPersonalizados;
	private final JPanel panel_1 = new JPanel();
	private JTextField entQuantidade;
	private JTextField entPreco;
	private JRadioButton rQuanKG, rQuanS;
	private JLabel  lblPreco, lblQuant;
	private JLabel lblValorTotal;
	private BigDecimal valor_total;
	private CadastroContrato contrato_local;
	private JButton btnNewButton;

	public TelaAlterarContrato(CadastroContrato contrato,Window janela_pai) {

		isto = this;
		contrato_local = contrato;
		setTitle("E-Contract - Alterar Contrato");

		setContentPane(painelOdin);
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 472, 336);
		painelOdin.setLayout(new BorderLayout(0, 0));
		painelOdin.add(painelPrincipal);

		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		painelPrincipal.setLayout(
				new MigLayout("", "[73px,grow][379px][74px][8px][83px,grow][50px]", "[46px][16px][433px,grow][28px,grow]"));
		panel.setBackground(new Color(0, 51, 0));
		painelPrincipal.add(panel, "cell 0 0 5 1,grow");
		panel.setLayout(new MigLayout("", "[617px]", "[20px]"));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setForeground(Color.WHITE);

		panel.add(lblNewLabel, "cell 0 0,alignx left,aligny center");
		panel_1.setBackground(Color.WHITE);
		
		painelPrincipal.add(panel_1, "cell 0 2 6 1,grow");
		panel_1.setLayout(new MigLayout("", "[96px][58px][10px][106px][133px]", "[23px][42px][42px][42px][][]"));
		
		JLabel lblvalortotal = new JLabel("Valor Total:");
		lblvalortotal.setFont(new Font("Arial Black", Font.PLAIN, 14));
		panel_1.add(lblvalortotal, "cell 0 3,grow");
		
		JLabel lblPreo = new JLabel("Preço:");
		lblPreo.setFont(new Font("Arial Black", Font.PLAIN, 14));
		panel_1.add(lblPreo, "cell 0 2,alignx right,growy");
		
		JLabel lblQuantidade = new JLabel("Quantidade:");
		lblQuantidade.setFont(new Font("Arial Black", Font.PLAIN, 14));
		panel_1.add(lblQuantidade, "cell 0 1,grow");
		
		
		 rQuanKG = new JRadioButton("KG");
		rQuanKG.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				rQuanS.setSelected(false);

				lblQuant.setText("Quilos");
				lblPreco.setText("por Quilo");


			}
		});
		rQuanKG.setSelected(true);
		panel_1.add(rQuanKG, "cell 1 0,grow");
		
		 rQuanS = new JRadioButton("SC");
		 rQuanS.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		
		 		rQuanKG.setSelected(false);

				lblQuant.setText("Sacos");
				lblPreco.setText("por Saco");


				
		 	}
		 });
		panel_1.add(rQuanS, "cell 3 0,alignx left,growy");
		
		entQuantidade = new JTextField();
		entQuantidade.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent o) {
				String caracteres = ".0987654321\b";// lista de caracters que não devem ser aceitos
				String Spreco, quantidade;
				quantidade = entQuantidade.getText();

				if (!caracteres.contains(o.getKeyChar() + "")) {
					o.consume();// aciona esse propriedade para eliminar a ação do evento

					// quantidade = entQuantidade.getText();
				} else {
					quantidade = entQuantidade.getText() + o.getKeyChar();
					quantidade = quantidade.replaceAll("[^0-9]+", "");
				}

				Spreco = entPreco.getText();

				BigDecimal quant = null;
				BigDecimal valor = null;

				System.out.println("preco e " + Spreco);

				System.out.println("quantidade e " + quantidade);

				// if(quantidade != null && !(quantidade.length() <= 0) &&
				// !quantidade.equals("") && Spreco != null && !(Spreco.length() <= 0) &&
				// !Spreco.equals(""))
				{
					try {
						quant = new BigDecimal(quantidade);

						BigDecimal preco = new BigDecimal(Spreco);

						valor = quant.multiply(preco);
						valor_total = valor;
						// valor_atual = valor_total.subtract(valor_atual);

						String valorTotal = valor.toPlainString();
						lblValorTotal.setText("R$ " + valorTotal);

					} catch (NumberFormatException n) {
						System.out.println("preco e nulo " + n.getCause());

						BigDecimal preco = new BigDecimal("1.0");
						try {
							valor = quant.multiply(preco);
							String valorTotal = valor.toPlainString();
							lblValorTotal.setText("R$ " + valorTotal);
							valor_total = valor;

							// valor_atual = valor_total.subtract(valor_atual);

						} catch (NullPointerException l) {
							System.out.println("quant e nulo " + l.getCause());
							lblValorTotal.setText("");

						}

					}
				}

			}
		});
		entQuantidade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
			}
		});
		entQuantidade.setFont(new Font("SansSerif", Font.BOLD, 16));
		entQuantidade.setColumns(10);
		panel_1.add(entQuantidade, "cell 1 1 3 1,growx,aligny bottom");
		
		entPreco = new JTextField();
		entPreco.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent p) {
				
				String caracteres = ".0987654321\b";// lista de caracters que não devem ser aceitos
				String Spreco, quantidade;
				Spreco = entPreco.getText();

				if (!caracteres.contains(p.getKeyChar() + "")) {
					p.consume();// aciona esse propriedade para eliminar a ação do evento

					// quantidade = entQuantidade.getText();
				} else {
					Spreco = entPreco.getText() + p.getKeyChar();
					Spreco = Spreco.replaceAll("[^0-9.]", "");
				}

				quantidade = entQuantidade.getText();

				BigDecimal preco = null;
				BigDecimal valor = null;

				System.out.println("preco e " + Spreco);

				System.out.println("quantidade e " + quantidade);

				// if(quantidade != null && !(quantidade.length() <= 0) &&
				// !quantidade.equals("") && Spreco != null && !(Spreco.length() <= 0) &&
				// !Spreco.equals(""))
				{
					try {
						preco = new BigDecimal(Spreco);

						BigDecimal quant = new BigDecimal(quantidade);

						valor = preco.multiply(quant);

						String valorTotal = valor.toPlainString();
						lblValorTotal.setText("R$ " + valorTotal);
						valor_total = valor;
						// valor_atual = valor_total.subtract(valor_atual);

					} catch (NumberFormatException q) {
						System.out.println("preco e nulo " + q.getCause());

						BigDecimal quant = new BigDecimal("1.0");
						try {
							valor = preco.multiply(quant);
							String valorTotal = valor.toPlainString();
							lblValorTotal.setText("R$ " + valorTotal);
							valor_total = valor;
							// valor_atual = valor_total.subtract(valor_atual);

						} catch (NullPointerException r) {
							System.out.println("quant e nulo " + r.getCause());
							lblValorTotal.setText("");

						}

					}
				}
				
			}
		});
		entPreco.setFont(new Font("SansSerif", Font.BOLD, 16));
		entPreco.setColumns(10);
		panel_1.add(entPreco, "cell 1 2 3 1,growx,aligny bottom");
		
		 lblPreco = new JLabel("Quilo");
		lblPreco.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_1.add(lblPreco, "cell 4 2,growx,aligny center");
		
		 lblQuant = new JLabel("Quilos");
		lblQuant.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_1.add(lblQuant, "cell 4 1,alignx left,aligny center");
		
		lblValorTotal = new JLabel("");
		lblValorTotal.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_1.add(lblValorTotal, "cell 1 3 3 1");
		
		btnNewButton = new JButton("Confirmar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
				CadastroContrato contrato_atualizar = getDadosSalvar();
				if(contrato_atualizar != null) {
				boolean atualizado = gerenciar.atualizarContratoComDistrato(contrato_atualizar);
				if(atualizado) {
					JOptionPane.showMessageDialog(isto, "Contrato Atualizado!");
					 ((TelaGerenciarContrato) janela_pai).atualizarContratoLocal(true);

					isto.dispose();
				}else {
					JOptionPane.showMessageDialog(isto, "Erro ao Atualizar o Contrato!\nConsulte o administrador");
				}
				}
			}
		});
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(new Color(0, 51, 0));
		btnNewButton.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_1.add(btnNewButton, "cell 4 5,growx");

		
		
		this.setResizable(false);
		this.setLocationRelativeTo(janela_pai);

	}
	
	
	public CadastroContrato getDadosSalvar() {
		
		CadastroContrato contrato = new CadastroContrato();
		String quantidade = entQuantidade.getText();
		String valor_produto = entPreco.getText();
		String medida = "";
		contrato.setId(contrato_local.getId());
		if (rQuanKG.isSelected())
			medida = "KG";
		else if (rQuanS.isSelected())
			medida = "Sacos";
		
		contrato.setMedida(medida);
		
		try {
			
			double quant = Double.parseDouble(quantidade);
			contrato.setQuantidade(quant);
			
		}catch(Exception e) {
			JOptionPane.showMessageDialog(isto, "Quantidade Inválida");
			return null;
		}
		
		
	try {
			
			double valor = Double.parseDouble(valor_produto);
			contrato.setValor_produto(valor);
			
		}catch(Exception e) {
			JOptionPane.showMessageDialog(isto, "Valor Inválido");
			return null;
		}
		
	
	try {
		
		double pagar = contrato.getValor_produto() * contrato.getQuantidade();
		BigDecimal valor_a_pagar = new BigDecimal(pagar);
		contrato.setValor_a_pagar(valor_a_pagar);
		return contrato;
		
	}catch(Exception e) {
		JOptionPane.showMessageDialog(isto, "Erro no Valor Total");
		return null;
	}
	
		
		
		
		
	}
	
	public void pesquisar_usuarios() {

		modelUsuarios.resetar();
		GerenciarBancoLogin gerenciar = new GerenciarBancoLogin();

		for (CadastroLogin usuario : gerenciar.getUsuarios()) {

			modelUsuarios.addCC(usuario);

		}
	}


	public void setTelaPai(JDialog _tela_pai) {
		this.telaPai = _tela_pai;
	}


	public boolean checkString(String txt) {
		return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ") && txt.length() > 0;
	}
	
	
}
