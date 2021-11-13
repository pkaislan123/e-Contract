package main.java.gui;

import java.awt.BorderLayout;
import javax.swing.JInternalFrame;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Calendar;
import java.util.Locale;
import org.freixas.jcalendar.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSpinner;
import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import net.miginfocom.swing.MigLayout;



import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GraphicsEnvironment;

import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;


import main.java.cadastros.CadastroAditivo;
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
import main.java.cadastros.CadastroAnotacaoGeral;
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
import main.java.conexaoBanco.GerenciarBancoAditivos;
import main.java.conexaoBanco.GerenciarBancoAnotacaoGerais;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoDocumento;
import main.java.conexaoBanco.GerenciarBancoNotasFiscais;
import main.java.conexaoBanco.GerenciarBancoPadrao;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoRomaneios;
import main.java.conexaoBanco.GerenciarBancoSafras;
import main.java.conexaoBanco.GerenciarBancoTransferencias;
import main.java.conexaoBanco.GerenciarBancoTransferenciasCarga;
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
import main.java.manipular.EditarAditivo;
import main.java.manipular.GetDadosGlobais;
import main.java.manipular.ManipularArquivoTerceiros;
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
import main.java.conexaoBanco.GerenciarBancoNotas;
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
import keeptoo.KGradientPanel;



import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SwingUtilities;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import java.awt.Insets;

public class TelaCriarNotaGeral extends JFrame {

	private final KGradientPanel painelPrincipal = new KGradientPanel();
	private JLabel lblTotalContratosConcluidos, lblTotalContratos, lblTotalContratosAbertos;
	private TelaCriarNotaGeral isto;
	private Calendar data_selecionada;
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private TelaCriarNotaGeral instance;
	private CadastroAnotacaoGeral nota_global;
	private JTextArea textAreaAnotacao;
	private int id_cliente_global = -1;

	public TelaCriarNotaGeral(int flag_modo_operacao, int id_cliente, CadastroAnotacaoGeral nota, Window janela_pai) {
		// setModal(true);
		getDadosGlobais();
		isto = this;
		instance = this;
		setResizable(true);
		nota_global = nota;
		if (flag_modo_operacao == 0) {
			setTitle("E-Contract - Criar Anotação");
		} else {
			setTitle("E-Contract - Editar Anotação");

		}
		
		id_cliente_global = id_cliente;
		
		
		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		System.out.println("Screen width = " + dim.width);
		System.out.println("Screen height = " + dim.height);

		// pega o tamanho da barra de tarefas
		Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
		java.awt.Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		int taskBarHeight = scrnSize.height - winSize.height;
		System.out.printf("Altura: %d\n", taskBarHeight);

		DisplayMode display = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDisplayMode();

		int display_x = display.getWidth();
		int display_y = display.getHeight();
		setBounds(0, 0, dim.width/2, dim.height - taskBarHeight);	
         painelPrincipal.kEndColor = Color.WHITE;
		painelPrincipal.kStartColor = Color.WHITE;
		painelPrincipal.setBackground(new Color(51, 153, 204));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
				painelPrincipal.setLayout(new MigLayout("", "[grow][5px][grow][grow][grow]", "[25px][grow][29px]"));
		
				JLabel lblDescrio_1 = new JLabel("Texto:");
				lblDescrio_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
				painelPrincipal.add(lblDescrio_1, "cell 0 0 5 1,growx,aligny top");

		textAreaAnotacao = new JTextArea();
		textAreaAnotacao.setBackground(new Color(51, 255, 204));
		textAreaAnotacao.setFont(new Font("SansSerif", Font.PLAIN, 18));
		textAreaAnotacao.setLineWrap(true);
		textAreaAnotacao.setWrapStyleWord(true);

		JScrollPane scrollPane = new JScrollPane(textAreaAnotacao);
		scrollPane.setBackground(Color.WHITE);
		painelPrincipal.add(scrollPane, "cell 0 1 5 1,grow");
		
				JButton btnSalvar = new JButton("Salvar");
				btnSalvar.setBackground(new Color(0, 51, 0));
				btnSalvar.setForeground(Color.WHITE);
				btnSalvar.setFont(new Font("SansSerif", Font.BOLD, 16));
				btnSalvar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						String texto;

						texto = textAreaAnotacao.getText();

						
						CadastroAnotacaoGeral nota = new CadastroAnotacaoGeral();
						nota.setTexto(texto);
						
						nota.setId_cliente(id_cliente_global);
						GerenciarBancoAnotacaoGerais gerenciar = new GerenciarBancoAnotacaoGerais();
						int salvou = gerenciar.inserirnota(nota);
						if (salvou > 0) {
							JOptionPane.showMessageDialog(isto, "Anotação criada com sucesso!");
							((TelaGerenciarCliente) janela_pai).pesquisar();
							isto.dispose();
						//	((TelaCriarAnotacaoDesktopVirtual) SwingUtilities.getAncestorOfClass (JFrame.class, isto)).dispose();
						} else {
							JOptionPane.showMessageDialog(isto, "Erro ao salvar anotação\nConsulte o administrador!");
							isto.dispose();
						}

					}
				});
				
						JButton btnAtualizar = new JButton("Atualizar");
						btnAtualizar.setBackground(new Color(0, 0, 153));
						btnAtualizar.setForeground(Color.WHITE);
						btnAtualizar.setFont(new Font("SansSerif", Font.BOLD, 16));
						btnAtualizar.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {

								String  texto;

								texto = textAreaAnotacao.getText();

								
							
								nota_global.setId_cliente(id_cliente_global);
								nota_global.setTexto(texto);

								GerenciarBancoAnotacaoGerais gerenciar = new GerenciarBancoAnotacaoGerais();
								boolean atualizou = gerenciar.atualizarNota(nota_global);
								if (atualizou) {
									JOptionPane.showMessageDialog(null, "Anotação atualizada");
									((TelaGerenciarCliente) janela_pai).pesquisar();
									isto.dispose();
								} else {
									JOptionPane.showMessageDialog(null, "Erro ao atualizar a anotação\nConsulte o administrado!");
									((TelaGerenciarCliente) janela_pai).pesquisar();

								}

							}
						});
						painelPrincipal.add(btnAtualizar, "flowx,cell 0 2,alignx right,aligny top");
				painelPrincipal.add(btnSalvar, "cell 0 2 5 1,alignx right,aligny top");

		
		if (flag_modo_operacao == 0) {
			btnAtualizar.setEnabled(false);
			btnAtualizar.setVisible(false);

		} else {
			btnSalvar.setEnabled(false);
			btnSalvar.setVisible(false);
			rotinasEdicao();
		}

		this.setVisible(true);
		this.setLocationRelativeTo(janela_pai);
	}

	public void rotinasEdicao() {
		textAreaAnotacao.setText(nota_global.getTexto());

		

	}

	
	public void getDadosGlobais() {
		// gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		GerenciadorLog = dados.getGerenciadorLog();
		configs_globais = dados.getConfigs_globais();

		// usuario logado
		login = dados.getLogin();

	}

	public static LocalDateTime toLocalDateTime(Calendar calendar) {
		if (calendar == null) {
			return null;
		}
		TimeZone tz = calendar.getTimeZone();
		ZoneId zid = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
		return LocalDateTime.ofInstant(calendar.toInstant(), zid);
	}

	

}
