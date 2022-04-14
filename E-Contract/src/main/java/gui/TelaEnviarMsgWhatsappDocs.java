
package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.commons.io.FilenameUtils;
import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;

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

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import java.awt.GridLayout;
import java.awt.Window;

import javax.swing.BoxLayout;
import net.miginfocom.swing.MigLayout;
import java.awt.Component;
import javax.swing.border.LineBorder;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JRadioButton;

public class TelaEnviarMsgWhatsappDocs extends JFrame {

	JPanel painelPrincipal = new JPanel();
	JPanelBackground rodape = new JPanelBackground();
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private JTextField entNumero;
	private JLabel lblNaoinclua;
	private String mensagem_notificacao_global, mensagem_anexo_global, mensagem_anexo_comprador,
			mensagem_notificacao_comprador, mensagem_anexo_vendedor1, mensagem_notificacao_vendedor1,
			mensagem_anexo_vendedor2, mensagem_notificacao_vendedor2, nome_vendedores_global, nome_compradores_global;
	private CadastroContrato contrato_local;
	private JTextArea textArea;
	private TelaEnviarMsgWhatsappDocs isto;
	private JLabel lblNaoAcentue;

	private ComboBoxContato modelContato = new ComboBoxContato();
	private RenderizadorContato renderContato;

	String mensagem_envio_documento = "";
	String mensagem_envio_relatorio = "";

	public TelaEnviarMsgWhatsappDocs(File doc, Window janela_pai) {

		getDadosGlobais();
		isto = this;

		setResizable(true);
		setTitle("E-Contract - Enviar Documento Via Whatsapp");

		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 534, 639);

		painelPrincipal.setBackground(new Color(0, 51, 102));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);

		JLabel lblNewLabel = new JLabel("Mensagem:");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel.setBounds(29, 226, 89, 37);
		painelPrincipal.add(lblNewLabel);

		textArea = new JTextArea();
		textArea.setForeground(Color.WHITE);
		textArea.setBackground(new Color(0, 0, 0, 0));
		textArea.setOpaque(false);
		textArea.setBounds(119, 158, 394, 354);
		textArea.setBorder(new LineBorder(new Color(0, 0, 0)));

		textArea.setLineWrap(true);
		painelPrincipal.add(textArea);

		JLabel lblContato = new JLabel("Contato:");
		lblContato.setForeground(Color.WHITE);
		lblContato.setFont(new Font("Arial", Font.BOLD, 14));
		lblContato.setBounds(50, 85, 68, 37);
		painelPrincipal.add(lblContato);

		renderContato = new RenderizadorContato();

		JLabel lblNewLabel_1 = new JLabel("       Enviar documento via whatsapp");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBackground(new Color(51, 0, 153));
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setBounds(0, 11, 259, 35);
		painelPrincipal.add(lblNewLabel_1);

		JPanel panel = new JPanel();
		panel.setForeground(Color.WHITE);
		panel.setBounds(171, 550, 341, 37);
		panel.setBackground(new Color(51, 0, 153));
		painelPrincipal.add(panel);

		JButton btnNewButton = new JButton("Cancelar");
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				isto.dispose();
			}
		});

		JButton btnNewButton_1 = new JButton("Enviar");
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (JOptionPane.showConfirmDialog(isto, "Enviar?", "Deseja enviar a mensagem?",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					enviar_Mensagem(doc.getAbsolutePath());

				} else {

				}

			}
		});
		panel.setLayout(new MigLayout("", "[grow][grow]", "[23px]"));
		panel.add(btnNewButton_1, "cell 0 0,grow");
		btnNewButton_1.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnNewButton_1.setBackground(new Color(0, 102, 102));
		panel.add(btnNewButton, "cell 1 0,grow");
		btnNewButton.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnNewButton.setOpaque(false);
		btnNewButton.setBackground(new Color(51, 0, 102));

		entNumero = new JTextField();
		entNumero.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {

				String caracteres = "0987654321\b";// lista de caracters que não devem ser aceitos
				String texto = entNumero.getText();
				if (!caracteres.contains(evt.getKeyChar() + "")) {
					evt.consume();// aciona esse propriedade para eliminar a ação do evento
				} else {
					if (texto.length() == 1 && evt.getKeyChar() != '\b') {
						entNumero.setText("(" + entNumero.getText());
					}
					if (texto.length() == 3 && evt.getKeyChar() != '\b') {
						entNumero.setText(entNumero.getText().concat(") "));
					}

					if (texto.length() == 6 && evt.getKeyChar() != '\b') {
						entNumero.setText(entNumero.getText().concat(" "));
					}

					if (texto.length() == 11 && evt.getKeyChar() != '\b') {
						entNumero.setText(entNumero.getText().concat("-"));
					}

					if (entNumero.getText().length() >= 16) {
						// if para saber se precisa verificar também o tamanho da string do campo
						// maior ou igual ao tamanho máximo, cancela e nao deixa inserir mais
						evt.consume();
						entNumero.setText(entNumero.getText().substring(0, 16));
					}

				}
			}
		});
		entNumero.setBounds(119, 87, 243, 33);
		painelPrincipal.add(entNumero);
		entNumero.setColumns(10);

		lblNaoinclua = new JLabel("Formato: (38) 9 XXXX-XXXX");
		lblNaoinclua.setVisible(false);
		lblNaoinclua.setForeground(Color.ORANGE);
		lblNaoinclua.setBounds(119, 132, 243, 14);
		painelPrincipal.add(lblNaoinclua);

		lblNaoAcentue = new JLabel("Não acentue as palavras.");
		lblNaoAcentue.setForeground(Color.ORANGE);
		lblNaoAcentue.setBounds(119, 524, 266, 14);
		painelPrincipal.add(lblNaoAcentue);

		JButton btnNewButton_2_1_1 = new JButton("buscar");
		btnNewButton_2_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaContato tela = new TelaContato(isto);
				tela.setVisible(true);
			}
		});
		btnNewButton_2_1_1.setForeground(Color.WHITE);
		btnNewButton_2_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		btnNewButton_2_1_1.setBackground(new Color(0, 0, 153));
		btnNewButton_2_1_1.setBounds(374, 89, 89, 23);
		painelPrincipal.add(btnNewButton_2_1_1);

		this.setLocationRelativeTo(null);
		this.setVisible(true);

	}

	public void getDadosGlobais() {
		// gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		GerenciadorLog = dados.getGerenciadorLog();
		configs_globais = dados.getConfigs_globais();

		// usuario logado
		login = dados.getLogin();

	}

	public void enviar_Mensagem(String url_arquivo) {

		// ZapMessenger zap = new ZapMessenger();
		// zap.logar();
		Nuvem nuvem = new Nuvem();
		Whatsapp zap = new Whatsapp();

		nuvem.abrir();
		nuvem.testar();
		nuvem.listar();
		
		System.out.println("url do arquivo para a nuvem: " + url_arquivo);
		
		String extensao = FilenameUtils.getExtension(url_arquivo);
		System.out.println("extensao: " + extensao);

		
		
		nuvem.deletarDoc("doc_relatorio_temporario." + extensao );

		boolean carregar_arquivo = nuvem.carregarDoc(url_arquivo, "doc_relatorio_temporario." + extensao);
		if (carregar_arquivo) {

			String url = nuvem.getUrlArquivo("/docs_temps/" + "doc_relatorio_temporario." + extensao);
			System.out.println("link do arquivo para enviar via zap: " + url);
			String mensagem_enviar = "";
			mensagem_enviar = textArea.getText().toString();
			mensagem_enviar = Normalizer.normalize(mensagem_enviar, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]",
					"");

			String mensagem_enviar_quebrada[] = mensagem_enviar.split("\n");
			String mensagem_enviar_final = "";
			for (int i = 0; i < mensagem_enviar_quebrada.length; i++) {
				mensagem_enviar_final = mensagem_enviar_final + mensagem_enviar_quebrada[i] + "\\n";
			}
			mensagem_enviar = mensagem_enviar_final;

			// checkBox esta selecionado
			String celular_neutro = entNumero.getText();
			String celular_reformado = celular_neutro.replaceAll("[^0-9]+", "");

			if (celular_reformado.length() == 11) {
				// celular aceito
				celular_reformado = celular_reformado;

				boolean retorno = zap.enviarArquivo("relatorio." + extensao, celular_reformado, url);
				boolean retorno2 = zap.enviarMensagem(celular_reformado, mensagem_enviar);

				// String retorno = enviar.enviarMensagem(mensagem, "38999280886");

				if (retorno && retorno2) {
					JOptionPane.showMessageDialog(null, "Mensagem Enviada!");
					isto.dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Erro, mensagem não enviada!");

				}

			} else {
				JOptionPane.showMessageDialog(null, "Número de celular incorreto!");
			}

			nuvem.deletarDoc("doc_relatorio_temporario." + extensao);
		} else {
			JOptionPane.showMessageDialog(null, "Erro ao fazer upload do arquivo!\nConsulte o administrador!");

		}
	}

	public void setNumero(String numero) {
		entNumero.setText(numero);
	}

}
