package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import javax.swing.JComboBox;

public class TelaEditarContato extends JFrame {

	private TelaEditarContato isto;
	private JTextField entNome;
	private JTextField entFixo;
	private JTextField entEmail;
	private JComboBox cBCargo;
	private JTextFieldPersonalizado entCelularContato;
	private JTextArea entDescricao, entObservacao;

	public TelaEditarContato(Contato contato, Window janela_pai) {

		isto = this;

		setResizable(true);
		setTitle("E-Contract - Edição de Contato");

		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 489, 391);

		JPanel painelPrincipal = new JPanel();
		painelPrincipal.setBorder(null);
		painelPrincipal.setBackground(Color.WHITE);


		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(new MigLayout("", "[][grow][grow][grow][grow][grow][grow][][][][][][][]", "[][][][][][][][100px,grow][100px,grow][][]"));
		
		JLabel lblNewLabel = new JLabel("Nome:");
		lblNewLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelPrincipal.add(lblNewLabel, "cell 0 2,alignx trailing");
		
		entNome = new JTextField();
		entNome.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelPrincipal.add(entNome, "cell 1 2 13 1,growx");
		entNome.setColumns(10);
		
		JLabel lblConta = new JLabel("Cargo:");
		lblConta.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelPrincipal.add(lblConta, "cell 0 3,alignx right");
		
		 cBCargo = new JComboBox();
		 cBCargo.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelPrincipal.add(cBCargo, "cell 1 3 13 1,growx");
		cBCargo.addItem("Secretaria");
		cBCargo.addItem("Auxiliar de Escritorio");
		cBCargo.addItem("Contato Particular");
		
		
		JLabel lblBanco = new JLabel("Celular:");
		lblBanco.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelPrincipal.add(lblBanco, "cell 0 4,alignx right");
		
		entCelularContato = new JTextFieldPersonalizado();
		entCelularContato.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				String caracteres = "0987654321\b";// lista de caracters que não devem ser aceitos
				String texto = entCelularContato.getText();
				if (!caracteres.contains(evt.getKeyChar() + "")) {
					evt.consume();// aciona esse propriedade para eliminar a ação do evento
				} else {
					if (texto.length() == 1 && evt.getKeyChar() != '\b') {
						entCelularContato.setText("(" + entCelularContato.getText());
					}
					if (texto.length() == 3 && evt.getKeyChar() != '\b') {
						entCelularContato.setText(entCelularContato.getText().concat(") "));
					}

					if (texto.length() == 6 && evt.getKeyChar() != '\b') {
						entCelularContato.setText(entCelularContato.getText().concat(" "));
					}

					if (texto.length() == 11 && evt.getKeyChar() != '\b') {
						entCelularContato.setText(entCelularContato.getText().concat("-"));
					}

					if (entCelularContato.getText().length() >= 16) {
						// if para saber se precisa verificar também o tamanho da string do campo
						// maior ou igual ao tamanho máximo, cancela e nao deixa inserir mais
						evt.consume();
						entCelularContato.setText(entCelularContato.getText().substring(0, 16));
					}

				}
			}
		});

		entCelularContato.setForeground(Color.BLACK);
		entCelularContato.setColumns(10);
		painelPrincipal.add(entCelularContato, "cell 1 4 13 1,growx");
		
		JLabel lblCdigo = new JLabel("Fixo:");
		lblCdigo.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelPrincipal.add(lblCdigo, "cell 0 5,alignx right");
		
		entFixo = new JTextField();
		entFixo.setFont(new Font("SansSerif", Font.BOLD, 16));
		entFixo.setColumns(10);
		painelPrincipal.add(entFixo, "cell 1 5 13 1,growx");
		
		JLabel lblAgncia = new JLabel("E-mail:");
		lblAgncia.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelPrincipal.add(lblAgncia, "cell 0 6,alignx right");
		
		entEmail = new JTextField();
		entEmail.setFont(new Font("SansSerif", Font.BOLD, 16));
		entEmail.setColumns(10);
		painelPrincipal.add(entEmail, "cell 1 6 13 1,growx");
		
		JLabel lblConta_1 = new JLabel("Descrição:");
		lblConta_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelPrincipal.add(lblConta_1, "cell 0 7,alignx right,aligny top");
		
		JButton btnNewButton = new JButton("Atualizar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Contato novo_contato = getContato(contato);
				if(novo_contato != null) {
				GerenciarBancoClientes gerenciar = new GerenciarBancoClientes();
				if(gerenciar.atualizarContato(novo_contato)) {
					JOptionPane.showMessageDialog(isto,"Contato Atualizado!") ;
					((TelaContato) janela_pai).pesquisar_contatos();
					isto.dispose();

				}else {
					JOptionPane.showMessageDialog(null, "Erro ao atualizar o Contato no banco de dados\nConsulte o administrador!");
					isto.dispose();

				}
				}
			}
		});
		
		 entDescricao = new JTextArea();
		painelPrincipal.add(entDescricao, "cell 1 7 13 1,grow");
		
		JLabel lblConta_1_1 = new JLabel("Observação:");
		lblConta_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelPrincipal.add(lblConta_1_1, "cell 0 8");
		
		
		 entObservacao = new JTextArea();
		painelPrincipal.add(entObservacao, "cell 1 8 13 1,grow");
		btnNewButton.setBackground(new Color(0, 51, 153));
		btnNewButton.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnNewButton.setForeground(Color.WHITE);
		painelPrincipal.add(btnNewButton, "cell 11 10");
	
		rotinasEdicao(contato);

		this.setLocationRelativeTo(janela_pai);

	}
	
	public Contato getContato(Contato contato_antigo) {
		String nome, cargo, celular, fixo, email, descricao, observacao;
		Contato contato_novo = new Contato();
		
		contato_novo.setId(contato_antigo.getId());
		
		
		nome = entNome.getText();
		cargo = cBCargo.getSelectedItem().toString();
		celular = entCelularContato.getText();
		fixo = entFixo.getText();
		email = entEmail.getText();
		descricao = entDescricao.getText();
		observacao = entObservacao.getText();
	
		celular = celular.replace("(", "").replace(")", "").replace(" ", "").replace("-", "");
		if (celular.length() != 11) {
			JOptionPane.showMessageDialog(isto, "Contato com número de celular incorreto");
			return null;
		} 
		
		contato_novo.setNome(nome);
		contato_novo.setCargo(cargo);
		contato_novo.setCelular(celular);
		contato_novo.setFixo(fixo);
		contato_novo.setE_mail(email);
		contato_novo.setDescricao(descricao);
		contato_novo.setObservacao(observacao);
		
	
		
		return contato_novo;
		
	}
	
	public void rotinasEdicao(Contato contato) {
		
		
		entNome.setText(contato.getNome());
		cBCargo.setSelectedItem(contato.getCargo());
		
		String celular = contato.getCelular();
		
		String formatar_celular = "(" + celular.substring(0, 2) + ") " + celular.substring(2,7) + "-" + celular.substring(7,11);
		
		entCelularContato.setText(formatar_celular);
		entFixo.setText(contato.getFixo());
		entEmail.setText(contato.getE_mail());
		entDescricao.setText(contato.getDescricao());
		entObservacao.setText(contato.getObservacao());

		
		
	}


}
