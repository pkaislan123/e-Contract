package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.ByteArrayOutputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.JOptionPane;
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
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;



public class TelaCriarAditivo extends JFrame {

	private final JPanel painelPrincipal = new JPanel();
	private JTextField entData;
	private JTextArea textAreaTextoFinal;
	private JDialog telaPai;
	private JFrame telaPaiJrame;
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private JCheckBox chkBoxDataAtual;
	private CadastroContrato contrato_local;
	private TelaCriarAditivo isto;
	EditarAditivo editarWord ;
	private  JTextArea textAreaDadosContrato;
	
	public TelaCriarAditivo(CadastroContrato contrato, Window janela_pai) {
		getDadosGlobais();
		
		contrato_local = contrato;

		 isto = this;
		
		setResizable(true);
		setTitle("E-Contract - Criar Aditivo");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1003, 705);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Data:");
		lblNewLabel_2.setBounds(12, 12, 72, 17);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelPrincipal.add(lblNewLabel_2);
		
		entData = new JTextField();
		entData.setBounds(102, 12, 185, 29);
		entData.setEditable(false);
		entData.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelPrincipal.add(entData);
		entData.setColumns(10);
		
		entData.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				String caracteres = "0987654321\b";// lista de caracters que não devem ser aceitos
				String texto = entData.getText();
				if (!caracteres.contains(evt.getKeyChar() + "")) {
					evt.consume();// aciona esse propriedade para eliminar a ação do evento
				} else {
					if (texto.length() == 2 && evt.getKeyChar() != '\b') {
						entData.setText(entData.getText().concat("/"));
					}
					if (texto.length() == 5 && evt.getKeyChar() != '\b') {
						entData.setText(entData.getText().concat("/"));
					}

					if (entData.getText().length() >= 10) {
						// if para saber se precisa verificar também o tamanho da string do campo
						// maior ou igual ao tamanho máximo, cancela e nao deixa inserir mais
						evt.consume();
						entData.setText(entData.getText().substring(0, 10));
					}

				}
			}
		});
		entData.setColumns(10);

		
		entData.setText(pegarData());

		
		
		
		 chkBoxDataAtual = new JCheckBox("Data Atual");
		 chkBoxDataAtual.setBounds(303, 12, 97, 18);
		chkBoxDataAtual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(chkBoxDataAtual.isSelected()) {
					chkBoxDataAtual.setSelected(true);
					pegarData();
					entData.setEditable(false);

				}else {
					chkBoxDataAtual.setSelected(false);
					pegarData();
					entData.setEditable(true);
				}
			}
		});
		chkBoxDataAtual.setSelected(true);
		painelPrincipal.add(chkBoxDataAtual);
		
		JButton btnCriarAditivo = new JButton("Gerar");
		btnCriarAditivo.setBounds(892, 627, 89, 33);
		btnCriarAditivo.setBackground(new Color(0, 51, 51));
		btnCriarAditivo.setForeground(Color.WHITE);
		btnCriarAditivo.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnCriarAditivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ByteArrayOutputStream contrato_alterado = null;

				System.out.println("Preparando para elaborar novo aditivo");
				String text = textAreaTextoFinal.getText();
				 contrato_local.setData_contrato(entData.getText());
				 editarWord = new EditarAditivo(contrato_local,text );
				 
				contrato_alterado = editarWord.preparar();

				ConverterPdf converter_pdf = new ConverterPdf();
				String pdf_alterado = converter_pdf.word_pdf_stream(contrato_alterado);
				TelaVizualizarPdf vizualizar = new TelaVizualizarPdf(null, isto, null,
						pdf_alterado, null, isto);
			}
		});
		painelPrincipal.add(btnCriarAditivo);
		
		JLabel lblNewLabel_3 = new JLabel("Texto do Aditivo:");
		lblNewLabel_3.setBounds(12, 169, 173, 17);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 14));
		painelPrincipal.add(lblNewLabel_3);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(12, 200, 969, 415);
		painelPrincipal.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel_1.add(scrollPane);
		
		 textAreaTextoFinal = new JTextArea();
		 textAreaTextoFinal.setFont(new Font("SansSerif", Font.PLAIN, 14));
		 textAreaTextoFinal.setText("Pelo presente instrumento, como (qualificação da parte como consta no contrato originário), e como (qualificação da outra parte como consta no contrato originário), ajustam o seguinte:\r\n\r\n[1.] [Prazo de Entrega]\r\n As partes acima qualificadas firmaram em 02 de julho de 2020 o CONTRATO 0002858-300 no qual ajustaram Prazo de entrega do dia 01/02/2021 a 28/02/2021.\r\n\r\n[2.] [Modalidade de Entrega]\r\n Considerando ter havido interesse recíproco, entre os contratantes, de alterar o prazo de entrega, passa, a partir desta data, a prevalecer o seguinte:\r\nA Mercadoria deverá ser entregue pelo Vendedor ao Comprador na condição posto sobre rodas, no prazo de entrega (retificado) de 01/02/2021 a 20/03/2021.\r\n\r\n[3.] [Final]\r\nFicam ratificadas todas as demais cláusulas e condições do CONTRATO mantem-se inalteradas. E por estarem, assim, justas e contratadas, assinam o presente em 2 vias de igual teor e forma, juntamente com as testemunhas abaixo.\r\n\r\n");
		 scrollPane.setViewportView(textAreaTextoFinal);
		 textAreaTextoFinal.setLineWrap(true);
		 textAreaTextoFinal.setWrapStyleWord(true);
		 textAreaTextoFinal.setBorder(new LineBorder(new Color(0, 0, 0)));
		 
		 JPanel painelDadosDoContrato = new JPanel();
		 painelDadosDoContrato.setBorder(new LineBorder(new Color(0, 0, 0)));
		 painelDadosDoContrato.setBackground(Color.WHITE);
		 painelDadosDoContrato.setBounds(12, 62, 969, 102);
		 painelPrincipal.add(painelDadosDoContrato);
		 painelDadosDoContrato.setLayout(new MigLayout("", "[grow]", "[grow]"));
		 
		  textAreaDadosContrato = new JTextArea();
		 painelDadosDoContrato.add(textAreaDadosContrato, "cell 0 0,grow");
		
		
	
		
		
		 setInfoContrato(contrato);

		this.setLocationRelativeTo(janela_pai);

		
		
		
	}
	
	public void setTelaPai(JDialog tela_pai) {
		this.telaPai = tela_pai;
	}
	
	
	
	public void getDadosGlobais() {
		//gerenciador de log
				DadosGlobais dados = DadosGlobais.getInstance();
				 GerenciadorLog = dados.getGerenciadorLog();
				 configs_globais = dados.getConfigs_globais();
				 
				 //usuario logado
				  login = dados.getLogin();
		
	}
	
	public String pegarData() {

		Date hoje = new Date();
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return df.format(hoje);
	}
	
	public void salvar(String caminho_arquivo_temp) {
        ManipularTxt manipular = new ManipularTxt();

		
		GetData hj = new GetData();
		String hora =  hj.getHora().replace(":", " ");
		
	     //criar o documento
		String unidade_base_dados = configs_globais.getServidorUnidade();
        String nome_arquivo = "aditivo_contrato_" + contrato_local.getCodigo() +  "_" + hora;
		String caminho_salvar = unidade_base_dados + "\\" + contrato_local.getCaminho_diretorio_contrato();
		
		manipular.criarDiretorio(caminho_salvar + "\\documentos\\"  );
        String caminho_completo = caminho_salvar + "\\documentos\\" + nome_arquivo;
		boolean salvar = editarWord.criarArquivo(caminho_completo);

		if(salvar) {

			CadastroAditivo aditivo = new CadastroAditivo();
			aditivo.setTexto(textAreaTextoFinal.getText());
			aditivo.setData(contrato_local.getData_contrato());
			aditivo.setStatus(1);
			aditivo.setId_contrato_pai(contrato_local.getId());
			aditivo.setNome_arquivo(nome_arquivo + ".pdf");
			
			GerenciarBancoAditivos gerenciar = new GerenciarBancoAditivos();
			int guardar = gerenciar.inserirAditivo(aditivo);
			
			if(guardar > 0) {
				
                boolean deletar_arquivo = manipular.apagarArquivo(caminho_completo + ".docx");
				JOptionPane.showMessageDialog(isto, "Aditivo Criado com sucesso");
				//((TelaGerenciarContrato) telaPai).setInformacoesAditivos();
				((TelaGerenciarContrato) telaPaiJrame).setInformacoesAditivos();

				isto.dispose();

			}else {
				JOptionPane.showMessageDialog(isto, "Erro ao guardar o aditivo na base de dados!\nConsulte o administrador");
                boolean deletar_arquivo = manipular.apagarArquivo(caminho_completo + ".docx");
                boolean deletar_pdf = manipular.apagarArquivo(caminho_completo + ".pdf");

			}
			
			
			
			
		}else {
			JOptionPane.showMessageDialog(isto, "Erro ao salvar o arquivo fisico!\nConsulte o administrador");

		}
		
		
	}
	
	public void setInfoContrato(CadastroContrato contrato) {
		
		String dados = "";
		Locale ptBr = new Locale("pt", "BR");
		NumberFormat z = NumberFormat.getNumberInstance();

		String quantidade = z.format( contrato.getQuantidade());
		String valor_total = NumberFormat.getCurrencyInstance(ptBr).format(contrato.getValor_a_pagar());
		String valor_por_unidade = NumberFormat.getCurrencyInstance(ptBr).format(contrato.getValor_produto());
		//String valor_comissao = NumberFormat.getCurrencyInstance(ptBr).format(contrato.getValor_comissao());
		String unidade = contrato.getMedida();


		dados = quantidade + " " + unidade + " de " + contrato.getModelo_safra().getProduto().getNome_produto() +
				" da " + " safra " + contrato.getModelo_safra().getAno_plantio() + "/" + contrato.getModelo_safra().getAno_colheita() + ", sendo " +
				valor_por_unidade + " por " + unidade + " perfazendo um total de " + valor_total;
		
		
		textAreaDadosContrato.setText(dados);
		
		
	}
	
	
	public void setTelaPai(JFrame tela_pai) {
		this.telaPaiJrame = tela_pai;
	}	
}
