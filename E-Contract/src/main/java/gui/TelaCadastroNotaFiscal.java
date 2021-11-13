package main.java.gui;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.java.cadastros.CadastroAviso;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroNFe;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroSafra;
import main.java.classesExtras.CBProdutoPersonalizado;
import main.java.classesExtras.CBProdutoRenderPersonalizado;
import main.java.conexaoBanco.GerenciarBancoNotasFiscais;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.manipular.ManipularTxt;
import main.java.outros.DadosGlobais;
import main.java.outros.GetData;
import main.java.outros.TratarDados;
import main.java.tratamento_proprio.Log;

import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TelaCadastroNotaFiscal extends JFrame {

	private TelaCadastroNotaFiscal isto;
	private JPanel painelPrincipal;
	private JTextField entCodigo;
	private JTextField entSerie;
	private JComboBox cbNatureza;
	private JTextField entProtocolo;
	private JTextField entDataNF;
	private JComboBox cbMedida;
	private JTextField entCaminhoArquivo;
	private JTextField entValor;
	private CadastroCliente remetente, destinatario;
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private CBProdutoPersonalizado modelProduto = new CBProdutoPersonalizado();

	private CBProdutoRenderPersonalizado cBProdutoPersonalizado;
	private JComboBox cBProduto;
	private JComboBox cbRemetente, cbDestinatario;
	private JTextField entQuantidade;

	public TelaCadastroNotaFiscal(int flag, CadastroNFe nota_fiscal, Window window) {

		isto = this;

		if (flag == 0) {
			setTitle("Cadastro Nota Fiscal");

		} else if (flag == 1) {
			setTitle("Editar Nota Fiscal");

		}
		getDadosGlobais();

		setResizable(false);

		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 884, 578);

		painelPrincipal = new JPanel();
		painelPrincipal.setBackground(Color.WHITE);

		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(new MigLayout("", "[][grow][]", "[][][][][][][][][][][][][][][][]"));

		JLabel lblNewLabel = new JLabel("Código:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblNewLabel, "cell 0 0,alignx trailing");

		entCodigo = new JTextField();
		entCodigo.setFont(new Font("Tahoma", Font.BOLD, 16));
		painelPrincipal.add(entCodigo, "cell 1 0,growx");
		entCodigo.setColumns(10);

		JLabel lblSrie = new JLabel("Série:");
		lblSrie.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblSrie, "cell 0 1,alignx trailing");

		entSerie = new JTextField();
		entSerie.setFont(new Font("Tahoma", Font.BOLD, 16));
		entSerie.setColumns(10);
		painelPrincipal.add(entSerie, "cell 1 1,growx");

		JLabel lblNatureza = new JLabel("Natureza:");
		lblNatureza.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblNatureza, "cell 0 2,alignx trailing");

		cbNatureza = new JComboBox();
		cbNatureza.setFont(new Font("SansSerif", Font.BOLD, 14));
		painelPrincipal.add(cbNatureza, "cell 1 2,growx");
		cbNatureza.addItem("RETORNO SIMBOLICO DE MERCADORIA DEPOSITADA EM DEPOSITO FECHADO");
		cbNatureza.addItem("COMPRA");
		cbNatureza.addItem("VENDA");
		cbNatureza.addItem("RETORNO DE MERCADORIA DEPOSITADA EM DEPOSITO FECHADO OU ARMA");
		cbNatureza.addItem("REMESSA");
		cbNatureza.addItem("Merc.Receb. P/ Deposito");
		cbNatureza.addItem("RETORNO MERC.DEP.ARMAZEM GERAL");

		JLabel lblProtocolo = new JLabel("Protocolo:");
		lblProtocolo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblProtocolo, "cell 0 3,alignx trailing");

		entProtocolo = new JTextField();
		entProtocolo.setFont(new Font("Tahoma", Font.BOLD, 16));
		entProtocolo.setColumns(10);
		painelPrincipal.add(entProtocolo, "cell 1 3,growx");

		JLabel lblDataDaNf = new JLabel("Data da NF:");
		lblDataDaNf.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblDataDaNf, "cell 0 4,alignx trailing");

		entDataNF = new JTextField();
		entDataNF.setFont(new Font("Tahoma", Font.BOLD, 16));
		entDataNF.setColumns(10);
		painelPrincipal.add(entDataNF, "cell 1 4,growx");
		entDataNF.setText(new GetData().getData());
		JLabel lblRemetente = new JLabel("Remetente:");
		lblRemetente.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblRemetente, "cell 0 5,alignx trailing");

		cbRemetente = new JComboBox();
		cbRemetente.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelPrincipal.add(cbRemetente, "cell 1 5,growx");

		JButton btnSelecionarRemetente = new JButton("Selecionar");
		btnSelecionarRemetente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaCliente tela = new TelaCliente(0, 90, isto);
				tela.setVisible(true);

			}
		});
		btnSelecionarRemetente.setBackground(new Color(0, 0, 153));
		btnSelecionarRemetente.setForeground(Color.WHITE);
		btnSelecionarRemetente.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelPrincipal.add(btnSelecionarRemetente, "cell 2 5");

		JLabel lblDestinatrio = new JLabel("Destinatário:");
		lblDestinatrio.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblDestinatrio, "cell 0 6,alignx trailing");

		cbDestinatario = new JComboBox();
		cbDestinatario.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelPrincipal.add(cbDestinatario, "cell 1 6,growx");

		JButton btnSelecionarDestinatario = new JButton("Selecionar");
		btnSelecionarDestinatario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaCliente tela = new TelaCliente(0, 91, isto);
				tela.setVisible(true);

			}
		});
		btnSelecionarDestinatario.setForeground(Color.WHITE);
		btnSelecionarDestinatario.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnSelecionarDestinatario.setBackground(new Color(0, 0, 153));
		painelPrincipal.add(btnSelecionarDestinatario, "cell 2 6");

		JLabel lblProduto = new JLabel("Produto:");
		lblProduto.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblProduto, "cell 0 7,alignx trailing");

		cBProdutoPersonalizado = new CBProdutoRenderPersonalizado();
		cBProduto = new JComboBox();
		cBProduto.setModel(modelProduto);
		cBProduto.setRenderer(cBProdutoPersonalizado);
		cBProduto.setBounds(214, 195, 305, 33);
		painelPrincipal.add(cBProduto, "cell 1 7,growx");

		GerenciarBancoProdutos listaProdutos = new GerenciarBancoProdutos();
		ArrayList<CadastroProduto> produtos = listaProdutos.getProdutos();

		for (CadastroProduto produto : produtos) {
			modelProduto.addProduto(produto);

		}

		JLabel lblMedida = new JLabel("Medida:");
		lblMedida.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblMedida, "cell 0 8,alignx trailing");

		cbMedida = new JComboBox();
		cbMedida.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelPrincipal.add(cbMedida, "cell 1 8,growx");
		cbMedida.addItem("KG");
		cbMedida.addItem("SC");

		JLabel lblQuantidade = new JLabel("Quantidade:");
		lblQuantidade.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblQuantidade, "cell 0 9,alignx trailing");

		entQuantidade = new JTextField();
		entQuantidade.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = ".0987654321\b";// lista de caracters que não devem ser aceitos
				String s_valor = "";

				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();// aciona esse propriedade para eliminar a ação do evento

				} 
			}
		});
		entQuantidade.setFont(new Font("Tahoma", Font.BOLD, 16));
		entQuantidade.setColumns(10);
		painelPrincipal.add(entQuantidade, "cell 1 9,growx");
		
		JLabel lblNewLabel_1 = new JLabel("somente ponto, ex: 32000.56 ; 45000.33, 28652.2");
		lblNewLabel_1.setForeground(Color.RED);
		painelPrincipal.add(lblNewLabel_1, "cell 1 10");

		JLabel lblValor = new JLabel("Valor:");
		lblValor.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblValor, "cell 0 11,alignx trailing");

		entValor = new JTextField();
		entValor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = ",.0987654321\b";// lista de caracters que não devem ser aceitos
				String s_valor = "";

				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();// aciona esse propriedade para eliminar a ação do evento

				} 
			}
		});
		entValor.setFont(new Font("Tahoma", Font.BOLD, 16));
		entValor.setColumns(10);
		painelPrincipal.add(entValor, "cell 1 11,growx");
		
		JLabel lblNewLabel_1_1 = new JLabel("vírgula e ponto, ex: 45.125,33 ; 58.947,56 ; 66.478,45");
		lblNewLabel_1_1.setForeground(Color.RED);
		painelPrincipal.add(lblNewLabel_1_1, "cell 1 12");

		JLabel lblCaminhoDoArquivo = new JLabel("Caminho do Arquivo:");
		lblCaminhoDoArquivo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblCaminhoDoArquivo, "cell 0 13,alignx trailing");

		entCaminhoArquivo = new JTextField();
		entCaminhoArquivo.setFont(new Font("Tahoma", Font.BOLD, 16));
		entCaminhoArquivo.setColumns(10);
		painelPrincipal.add(entCaminhoArquivo, "cell 1 13,growx");

		JButton btnSelecionarRemetente_1 = new JButton("Selecionar");
		btnSelecionarRemetente_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selecionarArquivo();
			}
		});
		btnSelecionarRemetente_1.setForeground(Color.WHITE);
		btnSelecionarRemetente_1.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnSelecionarRemetente_1.setBackground(new Color(0, 0, 153));
		painelPrincipal.add(btnSelecionarRemetente_1, "cell 2 13");

		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.setForeground(Color.WHITE);
		btnAtualizar.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnAtualizar.setBackground(new Color(0, 0, 51));
		painelPrincipal.add(btnAtualizar, "cell 1 15,alignx right");

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				try {
					CadastroNFe nota = getDadosSalvar();
					if(nota != null) {

						try {
							salvar(nota);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						
					}
					
					
				} catch (ParseException e1) {
					
				}
				
				
			}
		});
		btnSalvar.setForeground(Color.WHITE);
		btnSalvar.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnSalvar.setBackground(new Color(0, 51, 0));
		painelPrincipal.add(btnSalvar, "cell 2 15");

		if (flag == 0) {
			btnAtualizar.setEnabled(false);
			btnAtualizar.setVisible(false);
		} else if (flag == 1) {
			btnSalvar.setEnabled(false);
			btnSalvar.setVisible(false);

			rotinasEdicao(nota_fiscal);
		}

		this.setLocationRelativeTo(window);

	}

	public void rotinasEdicao(CadastroNFe nota) {

	}

	public CadastroNFe getDadosSalvar() throws ParseException {
		CadastroNFe nota = new CadastroNFe();

		String nfe, serie, natureza, protocolo, data_nfe, nome_remetente, inscricao_remetente, nome_destinatario,
				inscricao_destinatario, produto, medida, quantidade, valor, caminho_arquivo;

		nfe = entCodigo.getText();
		serie = entSerie.getText();
		natureza = cbNatureza.getSelectedItem().toString();
		protocolo = entProtocolo.getText();
		data_nfe = entDataNF.getText();

		if (remetente == null) {

			JOptionPane.showMessageDialog(isto, "Selecione o Remetente");

			return null;
		}
		
		
		if (remetente.getTipo_pessoa() == 0) {
			nome_remetente = (remetente.getNome_empresarial());

		} else {
			nome_remetente = (remetente.getNome_fantaia());

		}

		inscricao_remetente = remetente.getIe();

		if (destinatario == null) {

			JOptionPane.showMessageDialog(isto, "Selecione o Destinatáario");

			return null;
		}
		if (destinatario.getTipo_pessoa() == 0) {
			nome_destinatario = (destinatario.getNome_empresarial());

		} else {
			nome_destinatario = (destinatario.getNome_fantaia());

		}

		inscricao_destinatario = destinatario.getIe();

		CadastroProduto prod = (CadastroProduto) modelProduto.getSelectedItem();

		produto = prod.getNome_produto();

		medida = cbMedida.getSelectedItem().toString();
		quantidade = entQuantidade.getText();
		valor = entValor.getText();
		caminho_arquivo = entCaminhoArquivo.getText();

		if (checkString(nfe)) {

		} else {
			JOptionPane.showMessageDialog(isto, "Código da NF Inválido");

			return null;
		}

		if (checkString(serie)) {

		} else {
			JOptionPane.showMessageDialog(isto, "Série Inválida");

			return null;
		}

		if (checkString(protocolo)) {

		} else {
			JOptionPane.showMessageDialog(isto, "Protocolo Inválido");

			return null;
		}

		if (isDateValid(data_nfe)) {

		} else {
			JOptionPane.showMessageDialog(isto, "Data Inválida");

			return null;
		}

		try {

			double d_quantidade = Double.parseDouble(quantidade);
			nota.setQuantidade(quantidade);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(isto, "Quantidade Inválida");

			return null;
		}

		try {

			double d_valor = Double.parseDouble(valor);
			nota.setValor(valor);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(isto, "Valor Inválido");

			return null;
		}

		if (checkString(caminho_arquivo)) {

		} else {
			JOptionPane.showMessageDialog(isto, "Caminho Arquivo Inválido");

			return null;
		}

		nota.setNfe(nfe);
		nota.setSerie(serie);
		nota.setNatureza(natureza);
		nota.setProtocolo(protocolo);
		Date date = new SimpleDateFormat("dd/MM/yyyy").parse(data_nfe);
		nota.setData(date);
		nota.setNome_remetente(nome_remetente);
		nota.setInscricao_remetente(inscricao_remetente);
		nota.setNome_destinatario(nome_destinatario);
		nota.setInscricao_destinatario(inscricao_destinatario);
		nota.setProduto(produto);
		nota.setMedida(medida);
		nota.setQuantidade(quantidade);
		nota.setValor(valor);
		nota.setCaminho_arquivo(caminho_arquivo);
		
		return nota;
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

	public CadastroNFe getDadosAtualizar(CadastroNFe nota_antiga) {
		CadastroNFe nota = new CadastroNFe();
		nota.setId(nota_antiga.getId());

		return nota;
	}

	public void setRemetente(CadastroCliente cliente) {
		remetente = cliente;

		cbRemetente.removeAllItems();
		if (cliente.getTipo_pessoa() == 0) {
			cbRemetente.addItem(cliente.getNome_empresarial());

		} else {
			cbRemetente.addItem(cliente.getNome_fantaia());

		}

	}

	public void setDestinatario(CadastroCliente cliente) {
		destinatario = cliente;

		cbDestinatario.removeAllItems();
		if (cliente.getTipo_pessoa() == 0) {
			cbDestinatario.addItem(cliente.getNome_empresarial());

		} else {
			cbDestinatario.addItem(cliente.getNome_fantaia());

		}

	}

	public void selecionarArquivo() {
		JOptionPane.showMessageDialog(isto, "Na próxima tela, selecione o arquivo da nota fiscal");

		new JFXPanel();
		Platform.runLater(() -> {

			// pegar ultima pasta
			ManipularTxt manipular_ultima_pasta = new ManipularTxt();
			String ultima_pasta = manipular_ultima_pasta
					.lerArquivo(new File("C:\\ProgramData\\E-Contract\\configs\\ultima_pasta.txt"));
			FileChooser d = new FileChooser();
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF (*.pdf)", "*.pdf");
			d.getExtensionFilters().add(extFilter);

			d.setInitialDirectory(new File(ultima_pasta));
			File file = d.showOpenDialog(new Stage());
			String caminho_arquivo = "";
			if (file != null) {
				caminho_arquivo = file.getAbsolutePath();

				manipular_ultima_pasta.rescreverArquivo(
						new File("C:\\ProgramData\\E-Contract\\configs\\ultima_pasta.txt"), file.getParent());
				entCaminhoArquivo.setText(caminho_arquivo);

			}

		});

	}
	
	public void salvar(CadastroNFe cadastro) throws IOException {
		
		
		String unidade_base_dados = configs_globais.getServidorUnidade();
		String sub_pasta = "E-Contract\\arquivos\\arquivos_comuns";
		String pasta_final = unidade_base_dados + "\\" + sub_pasta;
		
		if (remetente.getId() == destinatario.getId()) {
			// move o arquivo para a pasta do remetente
			// copiar para pasta do remetente
			ManipularTxt manipular_txt = new ManipularTxt();
			String nome_pasta;
			if (remetente.getTipo_pessoa() == 0) {
				nome_pasta = remetente.getNome_empresarial().toUpperCase();
			} else {

				nome_pasta = remetente.getNome_fantaia().toUpperCase();
			}
			//JOptionPane.showMessageDialog(null, "nome da pasta remetente: " + nome_pasta);

			unidade_base_dados = configs_globais.getServidorUnidade();
			sub_pasta = "E-Contract\\arquivos\\clientes";
			ManipularTxt manipular_arq = new ManipularTxt();
			nome_pasta = nome_pasta.trim();
			String caminho_completo_nf = unidade_base_dados + "\\" + sub_pasta + "\\"
					+ nome_pasta.toUpperCase() + "\\" + "NOTAS FISCAIS" + "\\NFA-"
					+ cadastro.getNfe().trim() + ".pdf";

			//JOptionPane.showMessageDialog(null, "Movendo de :\n" + cadastro.getCaminho_arquivo()+ "\nPara:\n" + caminho_completo_nf);

			File file = new File(caminho_completo_nf);
			if (!file.exists()) {
				boolean mover = manipular_arq.moverArquivo(cadastro.getCaminho_arquivo(),
						caminho_completo_nf);
				if (mover) {

					cadastrarNFe(cadastro, caminho_completo_nf);

					JOptionPane.showMessageDialog(null, "NF copiada para a pasta do remetente");
				} else {
					JOptionPane.showMessageDialog(null, "Erro ao mover a nf para a pasta do  remetente");
					

				}
			} else {
				boolean apagar = manipular_arq.apagarArquivo(cadastro.getCaminho_arquivo());

			
				JOptionPane.showMessageDialog(null, "NF já importada");
			
			}

		} else {
			// copiar para a pasta do remetente e mover para a pasta do destinatario
			// copiar para pasta do remetente
			ManipularTxt manipular_txt = new ManipularTxt();
			String nome_pasta;
			if (remetente.getTipo_pessoa() == 0) {
				nome_pasta = remetente.getNome_empresarial().toUpperCase();
			} else {

				nome_pasta = remetente.getNome_fantaia().toUpperCase();
			}
			//JOptionPane.showMessageDialog(null, "nome da pasta remetente: " + nome_pasta);

			unidade_base_dados = configs_globais.getServidorUnidade();
			sub_pasta = "E-Contract\\arquivos\\clientes";
			ManipularTxt manipular_arq = new ManipularTxt();
			nome_pasta = nome_pasta.trim();
			String caminho_completo_nf = unidade_base_dados + "\\" + sub_pasta + "\\"
					+ nome_pasta.toUpperCase() + "\\" + "NOTAS FISCAIS" + "\\NFA-"
					+ cadastro.getNfe().trim() + ".pdf";

			//JOptionPane.showMessageDialog(null, "Movendo de :\n" + cadastro.getCaminho_arquivo()+ "\nPara:\n" + caminho_completo_nf);

			File file = new File(caminho_completo_nf);
			if (!file.exists()) {
				boolean mover = manipular_arq.copiarNFe(cadastro.getCaminho_arquivo(),
						caminho_completo_nf);
				if (mover) {
					cadastrarNFe(cadastro, caminho_completo_nf);

					// mover para pasta do destinatario
					if (destinatario.getTipo_pessoa() == 0) {
						nome_pasta = destinatario.getNome_empresarial().toUpperCase();
					} else {

						nome_pasta = destinatario.getNome_fantaia().toUpperCase();
					}
					//JOptionPane.showMessageDialog(null, "nome da pasta destinatario: " +  nome_pasta);

					unidade_base_dados = configs_globais.getServidorUnidade();
					sub_pasta = "E-Contract\\arquivos\\clientes";
					nome_pasta = nome_pasta.trim();
					caminho_completo_nf = unidade_base_dados + "\\" + sub_pasta + "\\"
							+ nome_pasta.toUpperCase() + "\\" + "NOTAS FISCAIS" + "\\NFA-"
							+ cadastro.getNfe().trim() + ".pdf";

					//JOptionPane.showMessageDialog(null, "Movendo de :\n" + cadastro.getCaminho_arquivo()+ "\nPara:\n" + caminho_completo_nf);

					file = new File(caminho_completo_nf);
					if (!file.exists()) {
						mover = manipular_arq.moverArquivo(cadastro.getCaminho_arquivo(),
								caminho_completo_nf);
						if (mover) {
							cadastrarNFe(cadastro, caminho_completo_nf);

							JOptionPane.showMessageDialog(null, "NF copiada para a pasta do destinatário");
						} else {
							//JOptionPane.showMessageDialog(null, "Erro ao mover a nf para a pasta do destinatário");
						

						}
					} else {
						boolean apagar = manipular_arq
								.apagarArquivo(cadastro.getCaminho_arquivo());
						
						//JOptionPane.showMessageDialog(null, "NF já importada");
						//JOptionPane.showMessageDialog(null, "Erro ao mover a nf para a pasta do destinatario");
					
					}
				} else {
					//JOptionPane.showMessageDialog(null, "Erro ao mover a nf para a pasta do remetente");
					// mover para pasta do destinatario
					if (destinatario.getTipo_pessoa() == 0) {
						nome_pasta = destinatario.getNome_empresarial().toUpperCase();
					} else {

						nome_pasta = destinatario.getNome_fantaia().toUpperCase();
					}
					//JOptionPane.showMessageDialog(null, "nome da pasta destinatario: " +  nome_pasta);

					unidade_base_dados = configs_globais.getServidorUnidade();
					sub_pasta = "E-Contract\\arquivos\\clientes";
					nome_pasta = nome_pasta.trim();
					caminho_completo_nf = unidade_base_dados + "\\" + sub_pasta + "\\"
							+ nome_pasta.toUpperCase() + "\\" + "NOTAS FISCAIS" + "\\NFA-"
							+ cadastro.getNfe().trim() + ".pdf";

					//JOptionPane.showMessageDialog(null, "Movendo de :\n" + cadastro.getCaminho_arquivo()+ "\nPara:\n" + caminho_completo_nf);

					file = new File(caminho_completo_nf);
					if (!file.exists()) {
						mover = manipular_arq.moverArquivo(cadastro.getCaminho_arquivo(),
								caminho_completo_nf);
						if (mover) {
							cadastrarNFe(cadastro, caminho_completo_nf);

							JOptionPane.showMessageDialog(null, "NF copiada para a pasta do destinatário");
						} else {
							//JOptionPane.showMessageDialog(null, "Erro ao mover a nf para a  pasta do destinatário");

						}
					} else {
						boolean apagar = manipular_arq
								.apagarArquivo(cadastro.getCaminho_arquivo());
						
					
						JOptionPane.showMessageDialog(null, "NF já importada");
						
					}
				}
			} else {
				// arquivo ja esta na pasta do remetente
				// mover para pasta do destinatario
				if (destinatario.getTipo_pessoa() == 0) {
					nome_pasta = destinatario.getNome_empresarial().toUpperCase();
				} else {

					nome_pasta = destinatario.getNome_fantaia().toUpperCase();
				}
				//JOptionPane.showMessageDialog(null, "nome da pasta destinatario: " + nome_pasta);

				unidade_base_dados = configs_globais.getServidorUnidade();
				sub_pasta = "E-Contract\\arquivos\\clientes";
				nome_pasta = nome_pasta.trim();
				caminho_completo_nf = unidade_base_dados + "\\" + sub_pasta + "\\"
						+ nome_pasta.toUpperCase() + "\\" + "NOTAS FISCAIS" + "\\NFA-"
						+ cadastro.getNfe().trim() + ".pdf";

				//JOptionPane.showMessageDialog(null, "Movendo de :\n" + 	cadastro.getCaminho_arquivo()+ "\nPara:\n" + caminho_completo_nf);

				file = new File(caminho_completo_nf);
				if (!file.exists()) {
					boolean mover = manipular_arq.moverArquivo(cadastro.getCaminho_arquivo(),
							caminho_completo_nf);
					if (mover) {
						cadastrarNFe(cadastro, caminho_completo_nf);

						//JOptionPane.showMessageDialog(null, "NF copiada para a pasta do  destinatario");
					} else {
						//JOptionPane.showMessageDialog(null, "Erro ao mover a nota fiscal para a pasta do destinatário\nConsulte o Administrador");
						

					}
				} else {
					boolean apagar = manipular_arq.apagarArquivo(cadastro.getCaminho_arquivo());

					JOptionPane.showMessageDialog(null, "NF já importada");
					
				}

				//JOptionPane.showMessageDialog(null, "NF já importada");
			}

		}
		
		
	}
	
	public void getDadosGlobais() {
		// gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		GerenciadorLog = dados.getGerenciadorLog();
		configs_globais = dados.getConfigs_globais();

		// usuario logado
		login = dados.getLogin();

	}

	public boolean checkString(String txt) {
		return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ");
	}

	
	public void cadastrarNFe(CadastroNFe nf, String caminho_completo_nf) {
		// o arquivo ja existe, cadastrar no banco de dados
		String caminho_completo = caminho_completo_nf;
		TratarDados tratar = new TratarDados(caminho_completo);
		String caminho_normalizado = tratar.tratar("E-Contract", "pdf");
		String caminho_completo_normalizado = "E-Contract" + caminho_normalizado + "pdf";
		String conteudo[] = caminho_completo_normalizado.split("\\\\");
		String url_final = "";
		for (String str : conteudo) {

			url_final = url_final + str + "\\\\";
		}
		nf.setCaminho_arquivo(url_final);

		GerenciarBancoNotasFiscais gerenciar = new GerenciarBancoNotasFiscais();

		if (!gerenciar.verificarRegistroNF(nf.getNfe())) {

			int inserir = gerenciar.inserir_nf(nf);
			if (inserir > 0) {
				JOptionPane.showMessageDialog(isto, "NF Cadastrada");
				isto.dispose();
			} else {
				JOptionPane.showMessageDialog(isto, "Erro ao cadastrar a NF\nConsulte o Administrador");

			}
		} else {
		 JOptionPane.showMessageDialog(isto, "Código " + nf.getNfe() + " ja cadastrado");
		}
	}

	
}
