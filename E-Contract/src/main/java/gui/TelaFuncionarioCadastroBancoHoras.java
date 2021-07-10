
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
import main.java.cadastros.CadastroFuncionarioBancoHoras;
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
import main.java.conexaoBanco.GerenciarBancoCartaoPonto;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoFuncionarioBancoHoras;
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
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

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
import javax.swing.JRadioButton;

public class TelaFuncionarioCadastroBancoHoras extends JFrame {

	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private final JPanelBackground contentPanel = new JPanelBackground();
	private JRadioButton rdbtnHorasNegativas, rdbtnHorasPositivas;

	// painel pai

	// painel filho1JPanelTransparent
	private JPanelBackground painelDadosIniciais = new JPanelBackground();
	private JComboBox cbMes;

	private JDialog telaPai;

	private JPanel painelCentral = new JPanel();
	private TelaFuncionarioCadastroBancoHoras isto;

	private CadastroFuncionario funcionario_local;
	private JTextField entQuantidadeHoras;
	private JTextField entQuantidadeMinutos;

	public TelaFuncionarioCadastroBancoHoras(int modo_operacao, CadastroFuncionario funcionario,
			CadastroFuncionarioBancoHoras banco, Window janela_pai) {

		funcionario_local = funcionario;
		this.setContentPane(painelDadosIniciais);

		setForeground(new Color(255, 255, 255));

		getDadosGlobais();
		// setAlwaysOnTop(true);

		// setModal(true);
		isto = this;

		setResizable(false);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setBounds(100, 100, 515, 208);

		// configuracao de paineis
		// painel pai

		painelDadosIniciais.setBackground(Color.WHITE);

		// adiciona o painel filho1 no painel principal

		painelDadosIniciais.setLayout(new BorderLayout(0, 0));

		JPanel panel_3 = new JPanel();
		painelDadosIniciais.add(panel_3);
		panel_3.setBackground(Color.WHITE);
		panel_3.setLayout(new MigLayout("", "[204px][][36px][4px][7px][4px][45px][8px][95px][4px][76px]", "[26px][28px][][33px][][]"));

		JLabel lblNewLabel = new JLabel("Mês:");
		lblNewLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_3.add(lblNewLabel, "cell 0 0,alignx right,aligny top");

		cbMes = new JComboBox();
		panel_3.add(cbMes, "cell 2 0 9 1,growx,aligny top");
		cbMes.addItem("JANEIRO");
		cbMes.addItem("FEVEREIRO");
		cbMes.addItem("MARÇO");
		cbMes.addItem("ABRIL");
		cbMes.addItem("MAIO");
		cbMes.addItem("JUNHO");
		cbMes.addItem("JULHO");
		cbMes.addItem("AGOSTO");
		cbMes.addItem("SETEMBRO");
		cbMes.addItem("OUTUBRO");
		cbMes.addItem("NOVEMBRO");
		cbMes.addItem("DEZEMBRO");
		JLabel lblQuantidade = new JLabel("Quantidade Horas(HHH:MM):");
		lblQuantidade.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_3.add(lblQuantidade, "cell 0 1,alignx left,aligny center");

		entQuantidadeHoras = new JTextField();
		panel_3.add(entQuantidadeHoras, "cell 2 1 4 1,growx,aligny top");
		entQuantidadeHoras.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel(":");
		lblNewLabel_1.setFont(new Font("SansSerif", Font.BOLD, 18));
		panel_3.add(lblNewLabel_1, "cell 6 1,alignx center,aligny top");

		entQuantidadeMinutos = new JTextField();
		entQuantidadeMinutos.setColumns(10);
		panel_3.add(entQuantidadeMinutos, "cell 7 1 2 1,growx,aligny top");
		
		
		 rdbtnHorasNegativas = new JRadioButton("Negativas");
		rdbtnHorasNegativas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				rdbtnHorasNegativas.setSelected(true);
				rdbtnHorasPositivas.setSelected(false);
				
			}
		});
		rdbtnHorasNegativas.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_3.add(rdbtnHorasNegativas, "flowx,cell 1 2 10 1");

		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				GerenciarBancoFuncionarioBancoHoras gerenciar = new GerenciarBancoFuncionarioBancoHoras();
				CadastroFuncionarioBancoHoras banco_novo = getDadosAtualizar(banco);
				if (banco_novo != null) {
					boolean inserir = gerenciar.atualizarBancoHoras(banco_novo);
					if (inserir) {
						JOptionPane.showMessageDialog(isto, "Banco de Horas Atualizado");
						((TelaGerenciarFuncionario) janela_pai).pesquisar_banco_horas();
						isto.dispose();
					} else {
						JOptionPane.showMessageDialog(isto,
								"Erro ao Atualizar Banco de Horas\nConsulte o administrador");

					}
				}
				
			}
		});
		btnAtualizar.setForeground(Color.WHITE);
		btnAtualizar.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnAtualizar.setBackground(new Color(0, 0, 102));
		panel_3.add(btnAtualizar, "cell 8 3,alignx left,aligny top");

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				GerenciarBancoFuncionarioBancoHoras gerenciar = new GerenciarBancoFuncionarioBancoHoras();
				CadastroFuncionarioBancoHoras banco = getDadosSalvar();
				if (banco != null) {
					int inserir = gerenciar.inserirbanco_horas(banco);
					if (inserir > 0) {
						JOptionPane.showMessageDialog(isto, "Banco de Horas Registrado");
						((TelaGerenciarFuncionario) janela_pai).pesquisar_banco_horas();
						isto.dispose();
					} else {
						JOptionPane.showMessageDialog(isto,
								"Erro ao Cadastrar Banco de Horas\nConsulte o administrador");

					}
				}
			}
		});
		btnSalvar.setForeground(Color.WHITE);
		btnSalvar.setBackground(new Color(0, 51, 0));
		btnSalvar.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_3.add(btnSalvar, "cell 10 3,alignx left,aligny top");
		
		 rdbtnHorasPositivas = new JRadioButton("Positivas");
		 rdbtnHorasPositivas.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		
		 		rdbtnHorasNegativas.setSelected(false);
				rdbtnHorasPositivas.setSelected(true);
				
		 		
		 	}
		 });
		rdbtnHorasPositivas.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_3.add(rdbtnHorasPositivas, "cell 8 2");
		painelCentral.setLayout(new BorderLayout(0, 0));

		// configura widgets no painel finalizar

		if (modo_operacao == 0) {
			// adicionar
			this.setTitle("Cadastrar Banco Horas");
			btnAtualizar.setEnabled(false);
			btnAtualizar.setVisible(false);
		} else if (modo_operacao == 1) {
			// editar
			this.setTitle("Editar Banco Horas");
			rotinasEdicao(banco);

			btnSalvar.setEnabled(false);
			btnSalvar.setVisible(false);
		}

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

	public CadastroFuncionarioBancoHoras getDadosSalvar() {

		CadastroFuncionarioBancoHoras banco = new CadastroFuncionarioBancoHoras();

		banco.setId_funcionario(funcionario_local.getId_funcionario());
		banco.setMes_referencia(cbMes.getSelectedIndex() );
		
		if(rdbtnHorasNegativas.isSelected()) {
			banco.setTipo_banco(0);
		}else if(rdbtnHorasPositivas.isSelected()) {
			banco.setTipo_banco(1);

		}
		
		String horas = entQuantidadeHoras.getText();
		String minutos = entQuantidadeMinutos.getText();

		try {

			Duration d = Duration.parse("PT" + horas + "H" + minutos + "M");
			long quantidade = d.toMinutes();

			banco.setQuantidade_horas(Long.toString(quantidade));
			return banco;
		} catch (Exception y) {
			JOptionPane.showMessageDialog(isto, "Hora Inválida!");
			return null;
		}

	}

	
	public CadastroFuncionarioBancoHoras getDadosAtualizar(CadastroFuncionarioBancoHoras banco_antigo) {

		CadastroFuncionarioBancoHoras banco = new CadastroFuncionarioBancoHoras();
		banco.setId_banco(banco_antigo.getId_banco());
		
		banco.setId_funcionario(funcionario_local.getId_funcionario());
		banco.setMes_referencia(cbMes.getSelectedIndex() );
		
		if(rdbtnHorasNegativas.isSelected()) {
			banco.setTipo_banco(0);
		}else if(rdbtnHorasPositivas.isSelected()) {
			banco.setTipo_banco(1);

		}
		
		String horas = entQuantidadeHoras.getText();
		String minutos = entQuantidadeMinutos.getText();

		try {

			Duration d = Duration.parse("PT" + horas + "H" + minutos + "M");
			long quantidade = d.toMinutes();

			banco.setQuantidade_horas(Long.toString(quantidade));
			return banco;
		} catch (Exception y) {
			JOptionPane.showMessageDialog(isto, "Hora Inválida!");
			return null;
		}

	}

	
	public void rotinasEdicao(CadastroFuncionarioBancoHoras dado) {
		String quanti = dado.getQuantidade_horas();
		long long_quanti = Long.parseLong(quanti);
		Duration duracao_total = Duration.ofMinutes(long_quanti);
		
		entQuantidadeHoras.setText(duracao_total.toHours() + "");
		entQuantidadeMinutos.setText(duracao_total.toMinutesPart() + "");

		cbMes.setSelectedIndex(dado.getMes_referencia() );
		
		
		if(dado.getTipo_banco() == 0) {
			rdbtnHorasNegativas.setSelected(true);
			rdbtnHorasPositivas.setSelected(false);
		}else if(dado.getTipo_banco() == 1) {
			rdbtnHorasNegativas.setSelected(false);
			rdbtnHorasPositivas.setSelected(true);
		}

	}

	public String formatHora(long minutos) {
		Duration duracao_total_horas_normais = Duration.ofMinutes(minutos);
		return String.format("%d:%02d", duracao_total_horas_normais.toHours(),
				duracao_total_horas_normais.toMinutesPart());

	}
}
