package gui;

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
import outros.DadosGlobais;
import outros.GetData;
import tratamento_proprio.Log;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import cadastros.CadastroAditivo;
import cadastros.CadastroCliente;
import cadastros.CadastroContrato;
import cadastros.CadastroLogin;
import conexaoBanco.GerenciarBancoAditivos;
import manipular.ConfiguracoesGlobais;
import manipular.ConverterPdf;
import manipular.EditarAditivo;
import manipular.EditarWord;
import manipular.ManipularTxt;

import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;



public class TelaCriarAditivo extends JDialog {

	private final JPanel painelPrincipal = new JPanel();
	private JTextField entData;
	private JTextArea textAreaTextoFinal;
	private JDialog telaPai;
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private JCheckBox chkBoxDataAtual;
	private CadastroContrato contrato_local;
	private TelaCriarAditivo isto;
	EditarAditivo editarWord ;
	
	public TelaCriarAditivo(CadastroContrato contrato) {
		getDadosGlobais();
		//setModal(true);
		
		contrato_local = contrato;

		 isto = this;
		
		setResizable(true);
		setTitle("E-Contract - Criar Aditivo");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 661, 467);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Data:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_2.setBounds(75, 31, 72, 14);
		painelPrincipal.add(lblNewLabel_2);
		
		entData = new JTextField();
		entData.setEditable(false);
		entData.setFont(new Font("Tahoma", Font.PLAIN, 14));
		entData.setBounds(157, 30, 185, 25);
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
		chkBoxDataAtual.setBounds(350, 29, 97, 23);
		painelPrincipal.add(chkBoxDataAtual);
		
		JButton btnCriarAditivo = new JButton("Salvar");
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
						pdf_alterado);
			}
		});
		btnCriarAditivo.setBounds(513, 381, 89, 23);
		painelPrincipal.add(btnCriarAditivo);
		
		JLabel lblNewLabel_3 = new JLabel("Texto do Aditivo:");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_3.setBounds(75, 89, 106, 14);
		painelPrincipal.add(lblNewLabel_3);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(75, 140, 527, 198);
		painelPrincipal.add(panel_1);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(0, 0, 517, 198);
		panel_1.add(scrollPane);
		
		 textAreaTextoFinal = new JTextArea();
		 textAreaTextoFinal.setText("Pelo presente instrumento, como (qualificação da parte como consta no contrato originário), e como (qualificação da outra parte como consta no contrato originário), ajustam o seguinte:\r\n\r\n[1.] [Prazo de Entrega]\r\n As partes acima qualificadas firmaram em 02 de julho de 2020 o CONTRATO 0002858-300 no qual ajustaram Prazo de entrega do dia 01/02/2021 a 28/02/2021.\r\n\r\n[2.] [Modalidade de Entrega]\r\n Considerando ter havido interesse recíproco, entre os contratantes, de alterar o prazo de entrega, passa, a partir desta data, a prevalecer o seguinte:\r\nA Mercadoria deverá ser entregue pelo Vendedor ao Comprador na condição posto sobre rodas, no prazo de entrega (retificado) de 01/02/2021 a 20/03/2021.\r\n\r\n[3.] [Final]\r\nFicam ratificadas todas as demais cláusulas e condições do CONTRATO mantem-se inalteradas. E por estarem, assim, justas e contratadas, assinam o presente em 2 vias de igual teor e forma, juntamente com as testemunhas abaixo.\r\n\r\n");
		 scrollPane.setViewportView(textAreaTextoFinal);
		 textAreaTextoFinal.setLineWrap(true);
		 textAreaTextoFinal.setWrapStyleWord(true);
		 textAreaTextoFinal.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		
	
		
		
		

		this.setLocationRelativeTo(null);

		
		
		
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
		
		
		GetData hj = new GetData();
		String hora =  hj.getHora().replace(":", " ");
		
	     //criar o documento
		String unidade_base_dados = configs_globais.getServidorUnidade();
        String nome_arquivo = "aditivo_contrato_" + contrato_local.getCodigo() +  "_" + hora;
		String caminho_salvar = unidade_base_dados + "\\" + contrato_local.getCaminho_diretorio_contrato();
		
		String caminho_completo = caminho_salvar + "\\comprovantes\\" + nome_arquivo;
		boolean salvar = editarWord.criarArquivo(caminho_completo);
        ManipularTxt manipular = new ManipularTxt();

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
				JOptionPane.showMessageDialog(null, "Aditivo Criado com sucesso");
				((TelaGerenciarContrato) telaPai).setInformacoesAditivos();
				isto.dispose();

			}else {
				JOptionPane.showMessageDialog(null, "Erro ao guardar o aditivo na base de dados!\nConsulte o administrador");
                boolean deletar_arquivo = manipular.apagarArquivo(caminho_completo + ".docx");
                boolean deletar_pdf = manipular.apagarArquivo(caminho_completo + ".pdf");

			}
			
			
			
			
		}else {
			JOptionPane.showMessageDialog(null, "Erro ao salvar o arquivo fisico!\nConsulte o administrador");

		}
		
		
	}
	
}
