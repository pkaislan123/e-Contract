
package main.java.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
//import javax.swing.JTextFieldPersonalizado;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import br.com.caelum.stella.ValidationMessage;
import br.com.caelum.stella.validation.CPFValidator;

import main.java.cadastros.CadastroAviso;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroFuncionario;
import main.java.cadastros.CadastroFuncionarioAdmissao;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroContrato.CadastroPagamento;
import main.java.cadastros.CadastroContrato.CadastroPagamentoContratual;
import main.java.cadastros.CadastroContrato.Recebimento;
import main.java.cadastros.CadastroFuncionario;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroModelo;
import main.java.cadastros.CadastroNFe;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroRomaneio;
import main.java.cadastros.CadastroSafra;
import main.java.cadastros.ContaBancaria;
import main.java.cadastros.Contato;
import main.java.cadastros.CalculoCompleto;
import main.java.cadastros.CadastroFuncionarioEvento;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.classesExtras.CombBoxRenderPersonalizadoContratoTrabalho;
import main.java.classesExtras.ComboBoxPersonalizado;
import main.java.classesExtras.ComboBoxPersonalizadoContratoTrabalho;
import main.java.classesExtras.ComboBoxRenderPersonalizado;
import main.java.classesExtras.Endereco;
import main.java.conexaoBanco.GerenciarBancoFuncionarios;
import main.java.conexaoBanco.GerenciarBancoFuncionariosContratoTrabalho;
import main.java.conexaoBanco.GerenciarBancoFuncionariosCalculos;
import main.java.conexaoBanco.GerenciarBancoFuncionariosEventos;
import main.java.conexaoBanco.GerenciarBancoCartaoPonto;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoFuncionarios;
import main.java.conexaoBanco.GerenciarBancoNotasFiscais;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoRomaneios;
import main.java.conexaoBanco.GerenciarBancoSafras;
import main.java.gui.TelaMain;
import main.java.gui.TelaRomaneios;
import main.java.manipular.ConfiguracoesGlobais;
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
import main.java.tratamento_proprio.Log;
import main.java.views_personalizadas.TelaEmEspera;
import main.java.views_personalizadas.TelaNotificacaoSuperiorModoBusca;
import outros.ValidaCNPj;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroNuvem;
import main.java.cadastros.CadastroZapMessenger;
import main.java.cadastros.CartaoPonto;
import main.java.cadastros.CartaoPontoCompleto;
import main.java.outros.DadosGlobais;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import javax.swing.JTable;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import net.miginfocom.swing.MigLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JTextArea;
import javax.swing.border.MatteBorder;
import javax.swing.JTextField;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class TelaFuncionariosCadastroEvento extends JFrame {

	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private CartaoPonto cartao_ponto;
	private final JPanelBackground contentPanel = new JPanelBackground();
	private JPanel painelPaiEvento;
	// painel pai
	private JComboBox cBMotivoDemissao ;
	// painel filho1JPanelTransparent
	private JPanelBackground painelDadosIniciais = new JPanelBackground();

	ArrayList<Integer> descontos_a_excluir = new ArrayList<>();
	ArrayList<Integer> acrescimos_a_excluir = new ArrayList<>();

	CadastroFuncionario funcionario_cadastrar = new CadastroFuncionario();
	CadastroFuncionario funcionario_atualizar = new CadastroFuncionario();
	private JDialog telaPai;

	private JPanel painelCentral = new JPanel();
	private TelaFuncionariosCadastroEvento isto;
	private JComboBox cBEvento;
	private JComboBox cbContratoTrabalho;
	private CombBoxRenderPersonalizadoContratoTrabalho cbContratosPersonalizados;

	private ComboBoxPersonalizadoContratoTrabalho modelContratos = new ComboBoxPersonalizadoContratoTrabalho();
	private CadastroFuncionario funcionario_local;
	private JTextField entDataFolga;
	private JTextField entDataDemissao;
	private JTextField entDataIdaFerias;
	private JTextField entDataVoltaFerias;
	private JTextField entNovoValorAlteracaoSalarial;
	private JTextField entDataInicioInsencaoPonto;
	private JTextField entDataFimInsencaoPonto;
	private JTextField entDataInicioLicenca;
	private JTextField entDataFimLicenca;

	public TelaFuncionariosCadastroEvento(CadastroFuncionario funcionario, Window janela_pai) {

		funcionario_local = funcionario;
		this.setContentPane(painelDadosIniciais);

		setForeground(new Color(255, 255, 255));
		this.setTitle("Novo Evento");

		getDadosGlobais();
		// setAlwaysOnTop(true);

		// setModal(true);
		isto = this;

		setResizable(false);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setBounds(100, 100, 665, 280);

		// configuracao de paineis
		// painel pai

		painelDadosIniciais.setBackground(Color.WHITE);

		// adiciona o painel filho1 no painel principal

		painelDadosIniciais.setLayout(new BorderLayout(0, 0));

		JPanel panel_3 = new JPanel();
		painelDadosIniciais.add(panel_3);
		panel_3.setBackground(Color.WHITE);
		panel_3.setLayout(new MigLayout("", "[62px,grow][2px][57px][1px][50px][10px][415px,grow][][][][]", "[27px][][][grow][33px,grow][][33px][33px][30px][][36px][][][][]"));

		JLabel lblCpf_1 = new JLabel("Contrato de Trabalho:");
		lblCpf_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCpf_1.setForeground(Color.BLACK);
		lblCpf_1.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCpf_1.setBackground(Color.ORANGE);
		panel_3.add(lblCpf_1, "cell 0 1,alignx right");

		cbContratoTrabalho = new JComboBox();
		cbContratosPersonalizados = new CombBoxRenderPersonalizadoContratoTrabalho();

		cbContratoTrabalho.setModel(modelContratos);
		cbContratoTrabalho.setRenderer(cbContratosPersonalizados);
		panel_3.add(cbContratoTrabalho, "cell 1 1 10 1,grow");

		JLabel lblCpf = new JLabel("Tipo do Evento:");
		panel_3.add(lblCpf, "cell 0 2,alignx right,aligny center");
		lblCpf.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCpf.setForeground(Color.BLACK);
		lblCpf.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCpf.setBackground(Color.ORANGE);
		lblCpf.setHorizontalAlignment(JLabel.RIGHT);

		cBEvento = new JComboBox();
		cBEvento.addItem("FOLGA".toUpperCase());
		cBEvento.addItem("ALTERAÇÃO SALARIAL".toUpperCase());
		cBEvento.addItem("DEMISSÃO".toUpperCase());
		cBEvento.addItem("FÉRIAS".toUpperCase());
		cBEvento.addItem("ISENÇÃO DE PONTO".toUpperCase());
		cBEvento.addItem("LICENÇA".toUpperCase());

		cBEvento.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent evt) {

				painelPaiEvento.removeAll();
				painelPaiEvento.repaint();
				painelPaiEvento.updateUI();

				if (evt.getItem().equals("FOLGA")) {
					setPainelEventoFolga();

				} else if (evt.getItem().equals("DEMISSÃO")) {
					setPainelEventoDemissao();
				} else if (evt.getItem().equals("FÉRIAS")) {
					setPainelEventoFerias();
				} else if (evt.getItem().equals("ALTERAÇÃO SALARIAL")) {
					setPainelEventoAlteracaoSalarial();
				} else if (evt.getItem().equals("ISENÇÃO DE PONTO")) {
					setPainelInsencaoPonto();
				} else if (evt.getItem().equals("LICENÇA")) {
					setPainelLicenca();
				}

				painelPaiEvento.repaint();
				painelPaiEvento.updateUI();
			}
		});

		panel_3.add(cBEvento, "cell 1 2 10 1,grow");
		cBEvento.setFont(new Font("Arial", Font.BOLD, 14));

		painelPaiEvento = new JPanel();
		painelPaiEvento.setBackground(Color.WHITE);
		panel_3.add(painelPaiEvento, "cell 1 3 10 8,grow");
		painelPaiEvento.setLayout(new MigLayout("", "[450px,grow]", "[grow][10px]"));

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				GerenciarBancoFuncionariosEventos gerenciar = new GerenciarBancoFuncionariosEventos();
				CadastroFuncionarioEvento evento = getDadosSalvar();
				if (evento != null) {
					int inserir = gerenciar.inserirevento(evento);
					if (inserir > 0) {
						
						if(evento.getTipo_evento() == 2) {
							//alteracao salarial
							GerenciarBancoFuncionariosContratoTrabalho ger = new GerenciarBancoFuncionariosContratoTrabalho();
							boolean ct_atualizado = ger.atualizarStatusContrato(evento.getId_contrato());
							if(ct_atualizado) {
								JOptionPane.showMessageDialog(isto, "Evento Registrado e status do contrato atualizado!");

							}else {
								JOptionPane.showMessageDialog(isto, "Evento Registrado, mas status do contrato não atualizado!\nConsulte o administrador");
								
								}
						}else {
							JOptionPane.showMessageDialog(isto, "Evento Registrado");

						}
						
						((TelaGerenciarFuncionario) janela_pai).pesquisar_eventos();
						
						isto.dispose();
					} else {
						JOptionPane.showMessageDialog(isto, "Erro ao Registrar Evento\nConsulte o Administrador!");

					}

				}

			}
		});

		btnSalvar.setForeground(Color.WHITE);
		btnSalvar.setBackground(new Color(0, 51, 0));
		btnSalvar.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_3.add(btnSalvar, "cell 6 11,alignx right");
		painelCentral.setLayout(new BorderLayout(0, 0));

		// configura widgets no painel finalizar

	    setPainelEventoFolga();
		pesquisar_contratos();
		this.setLocationRelativeTo(janela_pai);

	}

	public void getDadosGlobais() {
		// gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		GerenciadorLog = dados.getGerenciadorLog();
		configs_globais = dados.getConfigs_globais();

		// usuario logado
		login = dados.getLogin();

	}

	public void setPainelEventoFolga() {
		JPanel painelEventoFolga = new JPanel();
		painelEventoFolga.setBackground(Color.WHITE);
		painelEventoFolga.setLayout(new MigLayout("", "[][grow]", "[][]"));

		JLabel lblNewLabel = new JLabel("Data:");
		lblNewLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelEventoFolga.add(lblNewLabel, "cell 0 0,alignx trailing");

		entDataFolga = new JTextField();
		painelEventoFolga.add(entDataFolga, "cell 1 0,growx");
		entDataFolga.setColumns(10);

		painelPaiEvento.add(painelEventoFolga, "cell 0 0,growx");

	}

	public void setPainelEventoDemissao() {

		JPanel painelEventoDemissao = new JPanel();
		painelEventoDemissao.setBackground(Color.WHITE);
		painelEventoDemissao.setLayout(new MigLayout("", "[][grow]", "[][][]"));

		JLabel lblDataDemisso = new JLabel("Data Demissão:");
		lblDataDemisso.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDataDemisso.setForeground(Color.BLACK);
		lblDataDemisso.setFont(new Font("Arial", Font.PLAIN, 16));
		lblDataDemisso.setBackground(Color.ORANGE);
		painelEventoDemissao.add(lblDataDemisso, "cell 0 0,alignx trailing");

		entDataDemissao = new JTextField();
		painelEventoDemissao.add(entDataDemissao, "cell 1 0,growx");
		entDataDemissao.setColumns(10);

		JLabel lblMotivo = new JLabel("Motivo:");
		lblMotivo.setHorizontalAlignment(SwingConstants.TRAILING);
		lblMotivo.setForeground(Color.BLACK);
		lblMotivo.setFont(new Font("Arial", Font.PLAIN, 16));
		lblMotivo.setBackground(Color.ORANGE);
		painelEventoDemissao.add(lblMotivo, "cell 0 1,alignx trailing");

		 cBMotivoDemissao = new JComboBox();
		cBMotivoDemissao.setFont(new Font("Arial", Font.BOLD, 14));
		painelEventoDemissao.add(cBMotivoDemissao, "cell 1 1,growx");
		cBMotivoDemissao.addItem("ENCERRAMENTO DE CONTRATO");
		cBMotivoDemissao.addItem("JUSTA CAUSA");
		cBMotivoDemissao.addItem("SEM JUSTA CAUSA");
		cBMotivoDemissao.addItem("PEDIDO DE DISPENSA PELO COLABORADOR");

		painelPaiEvento.add(painelEventoDemissao, "cell 0 0,growx,aligny top");

	}

	public void setPainelEventoFerias() {

		JPanel painelEventoFerias = new JPanel();
		painelEventoFerias.setBackground(Color.WHITE);
		painelEventoFerias.setLayout(new MigLayout("", "[][grow][][grow]", "[][]"));

		JLabel lblDataIda = new JLabel("Data Ida:");
		lblDataIda.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDataIda.setForeground(Color.BLACK);
		lblDataIda.setFont(new Font("Arial", Font.PLAIN, 16));
		lblDataIda.setBackground(Color.ORANGE);
		painelEventoFerias.add(lblDataIda, "cell 0 0,alignx trailing");

		entDataIdaFerias = new JTextField();
		painelEventoFerias.add(entDataIdaFerias, "cell 1 0,growx");
		entDataIdaFerias.setColumns(10);

		JLabel lblDataVolta = new JLabel("Data Volta:");
		lblDataVolta.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDataVolta.setForeground(Color.BLACK);
		lblDataVolta.setFont(new Font("Arial", Font.PLAIN, 16));
		lblDataVolta.setBackground(Color.ORANGE);
		painelEventoFerias.add(lblDataVolta, "cell 2 0,alignx trailing");

		entDataVoltaFerias = new JTextField();
		entDataVoltaFerias.setColumns(10);
		painelEventoFerias.add(entDataVoltaFerias, "cell 3 0,growx");
		painelPaiEvento.add(painelEventoFerias, "cell 0 0,grow");

	}

	public CadastroFuncionarioEvento getDadosSalvar() {

		CadastroFuncionarioEvento evento = new CadastroFuncionarioEvento();
		evento.setData_evento(new GetData().getData());
		CadastroFuncionarioAdmissao ct = (CadastroFuncionarioAdmissao) modelContratos.getSelectedItem();

		evento.setId_contrato(ct.getId_contrato());
		evento.setId_colaborador(ct.getId_colaborador());

		if (cBEvento.getSelectedIndex() == 0) {
			// evento de folga
			evento.setTipo_evento(0);
			try {
				if (isDateValid(entDataFolga.getText())) {
					evento.setData_folga(entDataFolga.getText());
					return evento;
				} else {
					JOptionPane.showMessageDialog(isto, "Data da Folga Inválida!");
					return null;
				}

			} catch (Exception e) {
				JOptionPane.showMessageDialog(isto, "Data da Folga Inválida!");
				return null;
			}

		} else if (cBEvento.getSelectedIndex() == 1) {
			// ALTERAÇÃO SALARIAL
			evento.setTipo_evento(1);
			
			try {
				double valor =Double.parseDouble(entNovoValorAlteracaoSalarial.getText()); 
				evento.setNovo_valor_salarial(valor);

				return evento;
			}catch(Exception e) {
				JOptionPane.showMessageDialog(isto, "Valor Salarial Incorreto!");
				return null;
			}
			
			
		} else if (cBEvento.getSelectedIndex() == 2) {
			// encerramento de contrato
			evento.setTipo_evento(2);
			String data_demissao = entDataDemissao.getText();
			
			try {
				if (isDateValid(data_demissao)) {
					evento.setData_folga(data_demissao);
					evento.setMotivo_demissao(cBMotivoDemissao.getSelectedIndex());
					return evento;


				} else {
					JOptionPane.showMessageDialog(isto, "Data da Demissão Inválida!");
					return null;
				}

			} catch (Exception e) {
				JOptionPane.showMessageDialog(isto, "Data da Demissão Inválida!");
				return null;
			}
		} else if (cBEvento.getSelectedIndex() == 3) {
			// FÉRIAS
			evento.setTipo_evento(3);
			String data_ida = entDataIdaFerias.getText();
			String data_volta = entDataVoltaFerias.getText();

			try {
				if (isDateValid(data_ida)) {
					evento.setData_ferias_ida(data_ida);

					if (isDateValid(data_volta)) {
						evento.setData_ferias_volta(data_volta);

						return evento;

					} else {
						JOptionPane.showMessageDialog(isto, "Data da Volta Inválida!");
						return null;
					}

				} else {
					JOptionPane.showMessageDialog(isto, "Data da Ida Inválida!");
					return null;
				}

			} catch (Exception e) {
				JOptionPane.showMessageDialog(isto, "Data da Ida Inválida!");
				return null;
			}

		} else if (cBEvento.getSelectedIndex() == 4) {
			// insencao de ponto
			evento.setTipo_evento(4);
			String data_inicio = entDataInicioInsencaoPonto.getText();
			String data_fim = entDataFimInsencaoPonto.getText();

			try {
				if (isDateValid(data_inicio)) {
					evento.setData_ferias_ida(data_inicio);

					if (isDateValid(data_fim)) {
						evento.setData_ferias_volta(data_fim);

						return evento;

					} else {
						JOptionPane.showMessageDialog(isto, "Data Final Inválida!");
						return null;
					}

				} else {
					JOptionPane.showMessageDialog(isto, "Data Inicial Inválida!");
					return null;
				}

			} catch (Exception e) {
				JOptionPane.showMessageDialog(isto, "Data da Inicial Inválida!");
				return null;
			}

		} else if (cBEvento.getSelectedIndex() == 5) {
			// insencao de lancamento
			evento.setTipo_evento(5);
			String data_inicio = entDataInicioLicenca.getText();
			String data_fim = entDataFimLicenca.getText();

			try {
				if (isDateValid(data_inicio)) {
					evento.setData_ferias_ida(data_inicio);

					if (isDateValid(data_fim)) {
						evento.setData_ferias_volta(data_fim);

						return evento;

					} else {
						JOptionPane.showMessageDialog(isto, "Data Final Inválida!");
						return null;
					}

				} else {
					JOptionPane.showMessageDialog(isto, "Data Inicial Inválida!");
					return null;
				}

			} catch (Exception e) {
				JOptionPane.showMessageDialog(isto, "Data da Inicial Inválida!");
				return null;
			}
		}

		else {
			return null;

		}

	}

	public void setPainelInsencaoPonto() {
		JPanel painelEventoInsecaoPonto = new JPanel();
		painelEventoInsecaoPonto.setBackground(Color.WHITE);
		painelEventoInsecaoPonto.setLayout(new MigLayout("", "[][grow]", "[][]"));

		JLabel lblNewLabel_1 = new JLabel("Data Início da Vigência:");
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 16));
		painelEventoInsecaoPonto.add(lblNewLabel_1, "cell 0 0,alignx trailing");

		entDataInicioInsencaoPonto = new JTextField();
		entDataInicioInsencaoPonto.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelEventoInsecaoPonto.add(entDataInicioInsencaoPonto, "cell 1 0,growx");
		entDataInicioInsencaoPonto.setColumns(10);

		JLabel lblNewLabel_1_1 = new JLabel("Data Fim Vigência:");
		lblNewLabel_1_1.setFont(new Font("Arial", Font.PLAIN, 16));
		painelEventoInsecaoPonto.add(lblNewLabel_1_1, "cell 0 1,alignx trailing");

		entDataFimInsencaoPonto = new JTextField();
		entDataFimInsencaoPonto.setFont(new Font("SansSerif", Font.BOLD, 16));
		entDataFimInsencaoPonto.setColumns(10);
		painelEventoInsecaoPonto.add(entDataFimInsencaoPonto, "cell 1 1,growx");

		painelPaiEvento.add(painelEventoInsecaoPonto, "cell 0 0,grow");

	}

	public void setPainelEventoAlteracaoSalarial() {
		JPanel painelEventoAlteracaoSalarial = new JPanel();
		painelEventoAlteracaoSalarial.setBackground(Color.WHITE);
		painelEventoAlteracaoSalarial.setLayout(new MigLayout("", "[][grow]", "[][]"));

		JLabel lblNovoValor = new JLabel("Novo Valor:");
		lblNovoValor.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNovoValor.setForeground(Color.BLACK);
		lblNovoValor.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNovoValor.setBackground(Color.ORANGE);
		painelEventoAlteracaoSalarial.add(lblNovoValor, "cell 0 0,alignx trailing");

		entNovoValorAlteracaoSalarial = new JTextField();
		painelEventoAlteracaoSalarial.add(entNovoValorAlteracaoSalarial, "cell 1 0,growx");
		entNovoValorAlteracaoSalarial.setColumns(10);

		painelPaiEvento.add(painelEventoAlteracaoSalarial, "cell 0 0,grow");

	}

	public static boolean isDateValid(String strDate) {
		String dateFormat = "dd/MM/uuuu";

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat)
				.withResolverStyle(ResolverStyle.STRICT);
		try {
			LocalDate date = LocalDate.parse(strDate, dateTimeFormatter);
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}
	}

	public void setPainelLicenca() {

		JPanel painelLicenca = new JPanel();
		painelLicenca.setBackground(Color.WHITE);
		painelLicenca.setLayout(new MigLayout("", "[][grow]", "[][]"));

		JLabel lblNewLabel_2 = new JLabel("Data Início Licença:");
		lblNewLabel_2.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelLicenca.add(lblNewLabel_2, "cell 0 0,alignx trailing");

		entDataInicioLicenca = new JTextField();
		entDataInicioLicenca.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelLicenca.add(entDataInicioLicenca, "cell 1 0,growx");
		entDataInicioLicenca.setColumns(10);

		JLabel lblNewLabel_2_1 = new JLabel("Data Fim Licença:");
		lblNewLabel_2_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelLicenca.add(lblNewLabel_2_1, "cell 0 1,alignx trailing");

		entDataFimLicenca = new JTextField();
		entDataFimLicenca.setFont(new Font("SansSerif", Font.BOLD, 16));
		entDataFimLicenca.setColumns(10);
		painelLicenca.add(entDataFimLicenca, "cell 1 1,growx");

		painelPaiEvento.add(painelLicenca, "cell 0 0,grow");

	}

	public void pesquisar_contratos() {

		GerenciarBancoFuncionariosContratoTrabalho gerenciar = new GerenciarBancoFuncionariosContratoTrabalho();

		CadastroFuncionarioAdmissao ct = gerenciar
				.getcontratoAtivoPorFuncionario(funcionario_local.getId_funcionario());

			modelContratos.addCC(ct);
		

	}

}
