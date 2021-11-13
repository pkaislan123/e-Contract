package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
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
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.Window;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import keeptoo.KGradientPanel;
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
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroLogin.Mensagem;
import main.java.cadastros.CadastroModelo;
import main.java.cadastros.CadastroNFe;
import main.java.cadastros.CadastroNota;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroRomaneio;
import main.java.cadastros.CadastroSafra;
import main.java.cadastros.ContaBancaria;
import main.java.cadastros.Contato;
import main.java.cadastros.DadosCarregamento;
import main.java.cadastros.DadosContratos;
import main.java.cadastros.DadosRecebimento;
import main.java.cadastros.FinanceiroPagamentoCompleto;
import main.java.cadastros.Lancamento;
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
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroNuvem;
import main.java.cadastros.CadastroPontuacao;
import main.java.cadastros.CadastroZapMessenger;
import main.java.outros.DadosGlobais;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroGrupo;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoGrupos;
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
import main.java.classesExtras.ComboBoxRenderPersonalizado;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoSafras;

import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.JScrollPane;
import java.awt.Component;
import javax.swing.JTextField;

public class TelaEditarContaBancaria extends JFrame {

	private TelaEditarContaBancaria isto;
	private JTextField entNomeTitular;
	private JTextField entCpfTitular;
	private JTextField entBanco;
	private JTextField entCodigo;
	private JTextField entAgencia;
	private JTextField entConta;

	public TelaEditarContaBancaria(ContaBancaria conta, Window janela_pai) {

		isto = this;

		setResizable(true);
		setTitle("E-Contract - Edição de Conta Báncaria");

		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 489, 391);

		JPanel painelPrincipal = new JPanel();
		painelPrincipal.setBorder(null);
		painelPrincipal.setBackground(Color.WHITE);


		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(new MigLayout("", "[][grow][grow][grow][][][][][][][][][][]", "[][][][][][][][][][][]"));
		
		JLabel lblNewLabel = new JLabel("Nome Titular:");
		lblNewLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelPrincipal.add(lblNewLabel, "cell 0 2,alignx trailing");
		
		entNomeTitular = new JTextField();
		entNomeTitular.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelPrincipal.add(entNomeTitular, "cell 1 2 13 1,growx");
		entNomeTitular.setColumns(10);
		
		JLabel lblConta = new JLabel("CPF Titular:");
		lblConta.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelPrincipal.add(lblConta, "cell 0 3,alignx right");
		
		entCpfTitular = new JTextField();
		entCpfTitular.setFont(new Font("SansSerif", Font.BOLD, 16));
		entCpfTitular.setColumns(10);
		painelPrincipal.add(entCpfTitular, "cell 1 3 13 1,growx");
		
		JLabel lblBanco = new JLabel("Banco:");
		lblBanco.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelPrincipal.add(lblBanco, "cell 0 4,alignx right");
		
		entBanco = new JTextField();
		entBanco.setFont(new Font("SansSerif", Font.BOLD, 16));
		entBanco.setColumns(10);
		painelPrincipal.add(entBanco, "cell 1 4 13 1,growx");
		
		JLabel lblCdigo = new JLabel("Código:");
		lblCdigo.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelPrincipal.add(lblCdigo, "cell 0 5,alignx right");
		
		entCodigo = new JTextField();
		entCodigo.setFont(new Font("SansSerif", Font.BOLD, 16));
		entCodigo.setColumns(10);
		painelPrincipal.add(entCodigo, "cell 1 5 13 1,growx");
		
		JLabel lblAgncia = new JLabel("Agência:");
		lblAgncia.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelPrincipal.add(lblAgncia, "cell 0 6,alignx right");
		
		entAgencia = new JTextField();
		entAgencia.setFont(new Font("SansSerif", Font.BOLD, 16));
		entAgencia.setColumns(10);
		painelPrincipal.add(entAgencia, "cell 1 6 13 1,growx");
		
		JLabel lblConta_1 = new JLabel("Conta:");
		lblConta_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelPrincipal.add(lblConta_1, "cell 0 7,alignx right");
		
		entConta = new JTextField();
		entConta.setFont(new Font("SansSerif", Font.BOLD, 16));
		entConta.setColumns(10);
		painelPrincipal.add(entConta, "cell 1 7 13 1,growx");
		
		JButton btnNewButton = new JButton("Atualizar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ContaBancaria cb = getContaBancaria(conta);
				GerenciarBancoClientes gerenciar = new GerenciarBancoClientes();
				if(gerenciar.atualizarContaBancaria(cb)) {
					JOptionPane.showMessageDialog(isto,"Conta Bancária Atualizara!") ;
					((TelaContaBancaria) janela_pai).pesquisar_contas();
					isto.dispose();

				}else {
					JOptionPane.showMessageDialog(null, "Erro ao atualizar a Conta no banco de dados\nConsulte o administrador!");
					isto.dispose();

				}
				
			}
		});
		btnNewButton.setBackground(new Color(0, 51, 153));
		btnNewButton.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnNewButton.setForeground(Color.WHITE);
		painelPrincipal.add(btnNewButton, "cell 11 10");
	
		rotinasEdicao(conta);

		this.setLocationRelativeTo(janela_pai);

	}
	
	public ContaBancaria getContaBancaria(ContaBancaria conta_antiga) {
		String nome, cpf, banco, codigo, agencia, conta;
		ContaBancaria cb = new ContaBancaria();
		
		
		nome = entNomeTitular.getText();
		cpf = entCpfTitular.getText();
		banco = entBanco.getText();
		codigo = entCodigo.getText();
		agencia = entAgencia.getText();
		conta = entConta.getText();
		
		cb.setId_conta(conta_antiga.getId_conta());
		cb.setNome(nome);
		cb.setCpf_titular(cpf);
		cb.setBanco(banco);
		cb.setCodigo(codigo);
		cb.setAgencia(agencia);
		cb.setConta(conta);
		
		
		return cb;
		
	}
	
	public void rotinasEdicao(ContaBancaria conta) {
		entNomeTitular.setText(conta.getNome());
		entCpfTitular.setText(conta.getCpf_titular());
		entBanco.setText(conta.getBanco());
		entCodigo.setText(conta.getCodigo());
		entAgencia.setText(conta.getAgencia());
		entConta.setText(conta.getConta());
	}


}
