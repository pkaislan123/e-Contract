package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import br.com.caelum.stella.ValidationMessage;
import br.com.caelum.stella.validation.CPFValidator;

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
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JComboBox;
import java.awt.CardLayout;

public class TelaNovoCliente extends JDialog {

	private JTabbedPane painelPrincipal = new JTabbedPane();
	private JPanel painelDadosIniciais = new JPanel();
	private JPanel painelDadosPessoaFisica = new JPanel();
	private JPanel painelFinalizar = new JPanel();
	private JPanel panelPessoaFisica = new JPanel();
	private JPanel panelPessoaJuridica =new JPanel();
    private JPanel panelDinamico = new JPanel(new CardLayout());;
	
	private JPanel painelEmpresa = new JPanel();
	private JPanel painelDadosBancarios = new JPanel();
	private JTextField entLogradouro;
	private JTextField entNum;
	private JTextField entBairro;

	
	private String logradouro, bairro, cidade, estado;
	private int num, cep;
	private JTextField entCep;
	private JTextField entCidade;
	private JTextField entEstado;
	private JTextField entIE;
	private JTextField entCnpj;
	private JTextField entBanco;
	private JTextField entCodBanco;
	private JTextField entConta;
	private JTextField entAgencia;
	private JTextField entCpfTitular;
	private JTextField entNome;
	private JTextField entSobrenome;
	private JTextField entNascimento;
	private JTextField entCpf;
	private JTextField entRg;
	private JTextField entOcupacao;
	

	public TelaNovoCliente() {
	
		setModal(true);

		TelaNovoCliente isto = this;
		
		setResizable(false);
	
		
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("E-Contract - Novo Cliente");
		setBounds(100, 100, 1114, 632);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		painelPrincipal = new JTabbedPane();
		//contentPanel.setBackground(new Color(255, 255, 255));
		//contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		//setContentPane(contentPanel);
		//contentPanel.setLayout(null);
		
		painelDadosIniciais.setBackground(new Color(255, 255, 255));
		painelEmpresa.setBackground(new Color(255, 255, 255));

		painelPrincipal.addTab("Dados Iniciais", painelDadosIniciais);
		painelDadosIniciais.setLayout(null);
		
		
		
		JComboBox cBPessoa = new JComboBox();
		
		cBPessoa.setBounds(137, 25, 126, 35);
		cBPessoa.addItem("Física");
		cBPessoa.addItem("Jurídica");
		painelDadosIniciais.add(cBPessoa);
		
		JLabel lblPessoa = new JLabel("Pessoa:");
		lblPessoa.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPessoa.setForeground(Color.BLACK);
		lblPessoa.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblPessoa.setBackground(Color.ORANGE);
		lblPessoa.setBounds(63, 24, 64, 33);
		painelDadosIniciais.add(lblPessoa);
		

		panelPessoaJuridica.setBackground(Color.WHITE);
		panelPessoaJuridica.setBounds(20, 70, 924, 355);
		panelPessoaJuridica.setLayout(null);
		
		panelDinamico.setBackground(Color.WHITE);
		panelDinamico.setBounds(20, 70, 1039, 494);
		panelDinamico.setLayout(null);
		
		panelPessoaFisica.setBackground(Color.WHITE);
		panelPessoaFisica.setBounds(20, 70, 944, 370);
		panelPessoaFisica.setLayout(null);
		
		entNome = new JTextField();
		entNome.setColumns(10);
		entNome.setBounds(121, 11, 126, 33);
		panelPessoaFisica.add(entNome);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNome.setForeground(Color.BLACK);
		lblNome.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblNome.setBackground(Color.ORANGE);
		lblNome.setBounds(47, 9, 64, 33);
		panelPessoaFisica.add(lblNome);
		
		JLabel lblSobrenome = new JLabel("Sobrenome:");
		lblSobrenome.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSobrenome.setForeground(Color.BLACK);
		lblSobrenome.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblSobrenome.setBackground(Color.ORANGE);
		lblSobrenome.setBounds(257, 11, 94, 33);
		panelPessoaFisica.add(lblSobrenome);
		
		entSobrenome = new JTextField();
		entSobrenome.setColumns(10);
		entSobrenome.setBounds(361, 11, 220, 33);
		panelPessoaFisica.add(entSobrenome);
		
		entNascimento = new JTextField();
		entNascimento.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				String caracteres="0987654321\b";// lista de caracters que não devem ser aceitos
				String texto = entNascimento.getText();
				if(!caracteres.contains(evt.getKeyChar()+"")){
				evt.consume();//aciona esse propriedade para eliminar a ação do evento
				}else {
				if(texto.length()==2 && evt.getKeyChar() != '\b'){
					entNascimento.setText(entNascimento.getText().concat("/"));
				}
				if(texto.length()==5  && evt.getKeyChar() != '\b'){
					entNascimento.setText(entNascimento.getText().concat("/"));
				}
				
				if(entNascimento.getText().length()>=10){
					//if para saber se precisa verificar também o tamanho da string do campo
					// maior ou igual ao tamanho máximo, cancela e nao deixa inserir mais
					evt.consume();
					entNascimento.setText(entNascimento.getText().substring(0,10));
				}
			
			}
				
			}
		});
		entNascimento.setToolTipText("Data de Nascimento, somente números");
		entNascimento.setColumns(10);
		entNascimento.setBounds(121, 55, 126, 33);
		panelPessoaFisica.add(entNascimento);
		
		JLabel lblDataNascimento = new JLabel("Nascimento:");
		lblDataNascimento.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDataNascimento.setForeground(Color.BLACK);
		lblDataNascimento.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblDataNascimento.setBackground(Color.ORANGE);
		lblDataNascimento.setBounds(0, 61, 111, 33);
		panelPessoaFisica.add(lblDataNascimento);
		
		JLabel lblCpf = new JLabel("CPF:");
		lblCpf.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCpf.setForeground(Color.BLACK);
		lblCpf.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblCpf.setBackground(Color.ORANGE);
		lblCpf.setBounds(47, 114, 64, 33);
		panelPessoaFisica.add(lblCpf);
		
		entCpf = new JTextField();
		entCpf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				//120.927.987-00
				String caracteres="0987654321\b";// lista de caracters que não devem ser aceitos
				String texto = entCpf.getText();
				if(!caracteres.contains(evt.getKeyChar()+"")){
				evt.consume();//aciona esse propriedade para eliminar a ação do evento
				}else {
				if(texto.length()==3 && evt.getKeyChar() != '\b'){
					entCpf.setText(entCpf.getText().concat("."));
				}
				if(texto.length()==7  && evt.getKeyChar() != '\b'){
					entCpf.setText(entCpf.getText().concat("."));
				}
				
				if(texto.length()==11  && evt.getKeyChar() != '\b'){
					entCpf.setText(entCpf.getText().concat("-"));
				}
				
				if(entCpf.getText().length()>=14){
					//if para saber se precisa verificar também o tamanho da string do campo
					// maior ou igual ao tamanho máximo, cancela e nao deixa inserir mais
					evt.consume();
					entCpf.setText(entCpf.getText().substring(0,14));
				}
			
			}
				
			}
		});
		entCpf.setColumns(10);
		entCpf.setBounds(121, 116, 220, 33);
		panelPessoaFisica.add(entCpf);
		
		entRg = new JTextField();
		entRg.setColumns(10);
		entRg.setBounds(121, 179, 220, 33);
		panelPessoaFisica.add(entRg);
		
		JLabel lblRg = new JLabel("RG:");
		lblRg.setHorizontalAlignment(SwingConstants.TRAILING);
		lblRg.setForeground(Color.BLACK);
		lblRg.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblRg.setBackground(Color.ORANGE);
		lblRg.setBounds(47, 177, 64, 33);
		panelPessoaFisica.add(lblRg);
		
		JLabel lblOcupao = new JLabel("Ocupação:");
		lblOcupao.setHorizontalAlignment(SwingConstants.TRAILING);
		lblOcupao.setForeground(Color.BLACK);
		lblOcupao.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblOcupao.setBackground(Color.ORANGE);
		lblOcupao.setBounds(20, 251, 94, 33);
		panelPessoaFisica.add(lblOcupao);
		
		entOcupacao = new JTextField();
		entOcupacao.setColumns(10);
		entOcupacao.setBounds(121, 253, 220, 33);
		panelPessoaFisica.add(entOcupacao);
		
		panelDinamico.add(panelPessoaJuridica,"PessoaJuridica");
		
		JLabel lblTeste = new JLabel("teste");
		lblTeste.setBounds(112, 83, 46, 14);
		panelPessoaJuridica.add(lblTeste);

		panelDinamico.add(panelPessoaFisica, "PessoaFisica");
		
		painelDadosIniciais.add(panelDinamico);
		
		
		painelPrincipal.addTab("Endereço", painelEmpresa);
		painelEmpresa.setLayout(null);
		
	
		
		entLogradouro = new JTextField();
		entLogradouro.setColumns(10);
		entLogradouro.setBounds(142, 291, 220, 33);
		painelEmpresa.add(entLogradouro);
		
		JLabel lblLogradouro = new JLabel("Logradouro:");
		lblLogradouro.setHorizontalAlignment(SwingConstants.TRAILING);
		lblLogradouro.setForeground(Color.BLACK);
		lblLogradouro.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblLogradouro.setBackground(Color.ORANGE);
		lblLogradouro.setBounds(23, 291, 110, 33);
		painelEmpresa.add(lblLogradouro);
		
		JLabel lblN = new JLabel("N.\u00BA:");
		lblN.setHorizontalAlignment(SwingConstants.TRAILING);
		lblN.setForeground(Color.BLACK);
		lblN.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblN.setBackground(Color.ORANGE);
		lblN.setBounds(68, 335, 64, 33);
		painelEmpresa.add(lblN);
		
		entNum = new JTextField();
		entNum.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				
				String caracteres="0987654321";// lista de caracters que não devem ser aceitos
				if(!caracteres.contains(evt.getKeyChar()+"")){
				evt.consume();//aciona esse propriedade para eliminar a ação do evento
				}
				if(entNum.getText().length()>=6){
					//if para saber se precisa verificar também o tamanho da string do campo
					// maior ou igual ao tamanho máximo, cancela e nao deixa inserir mais
					evt.consume();
					entNum.setText(entNum.getText().substring(0,8));
			
			}
			}
		});
		entNum.setColumns(10);
		entNum.setBounds(142, 335, 220, 33);
		painelEmpresa.add(entNum);
		
		JLabel lblBairro = new JLabel("Bairro:");
		lblBairro.setHorizontalAlignment(SwingConstants.TRAILING);
		lblBairro.setForeground(Color.BLACK);
		lblBairro.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblBairro.setBackground(Color.ORANGE);
		lblBairro.setBounds(68, 377, 64, 33);
		painelEmpresa.add(lblBairro);
		
		entBairro = new JTextField();
		entBairro.setColumns(10);
		entBairro.setBounds(142, 379, 220, 33);
		painelEmpresa.add(entBairro);
		
		JLabel lblCidade = new JLabel("Cidade:");
		lblCidade.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCidade.setForeground(Color.BLACK);
		lblCidade.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblCidade.setBackground(Color.ORANGE);
		lblCidade.setBounds(394, 335, 64, 33);
		painelEmpresa.add(lblCidade);
		
		JLabel lblBairro_1_1 = new JLabel("Estado:");
		lblBairro_1_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblBairro_1_1.setForeground(Color.BLACK);
		lblBairro_1_1.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblBairro_1_1.setBackground(Color.ORANGE);
		lblBairro_1_1.setBounds(394, 377, 64, 33);
		painelEmpresa.add(lblBairro_1_1);
		
		JLabel lblBairro_1_2 = new JLabel("CEP:");
		lblBairro_1_2.setHorizontalAlignment(SwingConstants.TRAILING);
		lblBairro_1_2.setForeground(Color.BLACK);
		lblBairro_1_2.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblBairro_1_2.setBackground(Color.ORANGE);
		lblBairro_1_2.setBounds(394, 291, 64, 33);
		painelEmpresa.add(lblBairro_1_2);
		
		entCep = new JTextField();
		entCep.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				
				String caracteres="0987654321";// lista de caracters que não devem ser aceitos
				if(!caracteres.contains(evt.getKeyChar()+"")){
				evt.consume();//aciona esse propriedade para eliminar a ação do evento
				}
				if(entCep.getText().length()>=8){
					//if para saber se precisa verificar também o tamanho da string do campo
					// maior ou igual ao tamanho máximo, cancela e nao deixa inserir mais
					evt.consume();
					entCep.setText(entCep.getText().substring(0,8));
			
			}
			}
		});
		entCep.setColumns(10);
		entCep.setBounds(468, 291, 220, 33);
		painelEmpresa.add(entCep);
		
		JButton btnLocalizar = new JButton("Localizar");
		btnLocalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
				int cep = Integer.parseInt(entCep.getText().toString());
				if(entCep.getText().toString().length() != 8)
				{
		            JOptionPane.showMessageDialog(null, "Cep Invalido!");

				}
				else
				{
					BuscarCep busca  = new BuscarCep(cep);
					Endereco endereco = busca.buscar();
					entLogradouro.setText(endereco.getLogradouro());
					entBairro.setText(endereco.getBairro());
					entCidade.setText(endereco.getCidade());
					entEstado.setText(endereco.getUf());
					
					
				}
				
				
				}catch(Exception e1)
				{
					
				}
				
				
			}
		});
		btnLocalizar.setToolTipText("Buscar Cep");
		btnLocalizar.setIcon(new ImageIcon(TelaNovoCliente.class.getResource("/imagens/procurar.png")));
		btnLocalizar.setBounds(698, 296, 101, 23);
		painelEmpresa.add(btnLocalizar);
		
		entCidade = new JTextField();
		entCidade.setColumns(10);
		entCidade.setBounds(468, 337, 220, 33);
		painelEmpresa.add(entCidade);
		
		entEstado = new JTextField();
		entEstado.setColumns(10);
		entEstado.setBounds(468, 379, 220, 33);
		painelEmpresa.add(entEstado);
		
		JLabel lblIe = new JLabel("IE:");
		lblIe.setHorizontalAlignment(SwingConstants.TRAILING);
		lblIe.setForeground(Color.BLACK);
		lblIe.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblIe.setBackground(Color.ORANGE);
		lblIe.setBounds(69, 37, 64, 33);
		painelEmpresa.add(lblIe);
		
		entIE = new JTextField();
		entIE.setColumns(10);
		entIE.setBounds(142, 37, 220, 33);
		painelEmpresa.add(entIE);
		
		JLabel lblCnpj = new JLabel("CNPJ:");
		lblCnpj.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCnpj.setForeground(Color.BLACK);
		lblCnpj.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblCnpj.setBackground(Color.ORANGE);
		lblCnpj.setBounds(69, 81, 64, 33);
		painelEmpresa.add(lblCnpj);
		
		entCnpj = new JTextField();
		entCnpj.setColumns(10);
		entCnpj.setBounds(142, 81, 220, 33);
		painelEmpresa.add(entCnpj);

		cBPessoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(cBPessoa.getSelectedItem()=="Física")
				{
					CardLayout cardLayout = (CardLayout) panelDinamico.getLayout();
                    cardLayout.show(panelDinamico, "PessoaFisica");
				
					
				}
				if(cBPessoa.getSelectedItem()=="Jurídica")
				{
					CardLayout cardLayout = (CardLayout) panelDinamico.getLayout();
					cardLayout.show(panelDinamico, "PessoaJuridica");
				
				}
				
				
			}
		});
		
		//adiciona a tab no getContentPane
		 getContentPane().add(painelPrincipal, BorderLayout.CENTER);
		 painelDadosBancarios.setBackground(new Color(255, 255, 255));
		 
		 painelPrincipal.addTab("Dados Bancarios", painelDadosBancarios);
		 painelDadosBancarios.setLayout(null);
		 
		 JLabel lblBanco = new JLabel("Banco:");
		 lblBanco.setHorizontalAlignment(SwingConstants.TRAILING);
		 lblBanco.setForeground(Color.BLACK);
		 lblBanco.setFont(new Font("Arial Black", Font.PLAIN, 14));
		 lblBanco.setBackground(Color.ORANGE);
		 lblBanco.setBounds(68, 87, 64, 33);
		 painelDadosBancarios.add(lblBanco);
		 
		 entBanco = new JTextField();
		 entBanco.setColumns(10);
		 entBanco.setBounds(142, 89, 220, 33);
		 painelDadosBancarios.add(entBanco);
		 
		 JLabel lblCdigo = new JLabel("Código:");
		 lblCdigo.setHorizontalAlignment(SwingConstants.TRAILING);
		 lblCdigo.setForeground(Color.BLACK);
		 lblCdigo.setFont(new Font("Arial Black", Font.PLAIN, 14));
		 lblCdigo.setBackground(Color.ORANGE);
		 lblCdigo.setBounds(68, 131, 64, 33);
		 painelDadosBancarios.add(lblCdigo);
		 
		 JLabel lblConta = new JLabel("Conta:");
		 lblConta.setHorizontalAlignment(SwingConstants.TRAILING);
		 lblConta.setForeground(Color.BLACK);
		 lblConta.setFont(new Font("Arial Black", Font.PLAIN, 14));
		 lblConta.setBackground(Color.ORANGE);
		 lblConta.setBounds(68, 175, 64, 33);
		 painelDadosBancarios.add(lblConta);
		 
		 JLabel lblAgncia = new JLabel("Agência:");
		 lblAgncia.setHorizontalAlignment(SwingConstants.TRAILING);
		 lblAgncia.setForeground(Color.BLACK);
		 lblAgncia.setFont(new Font("Arial Black", Font.PLAIN, 14));
		 lblAgncia.setBackground(Color.ORANGE);
		 lblAgncia.setBounds(58, 219, 80, 33);
		 painelDadosBancarios.add(lblAgncia);
		 
		 entCodBanco = new JTextField();
		 entCodBanco.setColumns(10);
		 entCodBanco.setBounds(142, 133, 220, 33);
		 painelDadosBancarios.add(entCodBanco);
		 
		 entConta = new JTextField();
		 entConta.setColumns(10);
		 entConta.setBounds(142, 177, 220, 33);
		 painelDadosBancarios.add(entConta);
		 
		 entAgencia = new JTextField();
		 entAgencia.setColumns(10);
		 entAgencia.setBounds(142, 221, 220, 33);
		 painelDadosBancarios.add(entAgencia);
		 
		 JLabel lblCpfTitular = new JLabel("CPF Titular:");
		 lblCpfTitular.setHorizontalAlignment(SwingConstants.TRAILING);
		 lblCpfTitular.setForeground(Color.BLACK);
		 lblCpfTitular.setFont(new Font("Arial Black", Font.PLAIN, 14));
		 lblCpfTitular.setBackground(Color.ORANGE);
		 lblCpfTitular.setBounds(42, 43, 90, 33);
		 painelDadosBancarios.add(lblCpfTitular);
		 
		 entCpfTitular = new JTextField();
		 entCpfTitular.setColumns(10);
		 entCpfTitular.setBounds(142, 41, 220, 33);
		 painelDadosBancarios.add(entCpfTitular);
		 
		 
		 
		 
		 painelFinalizar.setBackground(new Color(255, 255, 255));
		 painelPrincipal.addTab("Finalizar Cadastro", painelFinalizar);
		 painelFinalizar.setLayout(null);
		 
		 JButton btnFinalizarNovoCliente = new JButton("Salvar");
		 btnFinalizarNovoCliente.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent arg0) {
		 		
		 		String cpf = entCpf.getText().toString();
		 		CPFValidator cpfValidator = new CPFValidator(); 
		 		List<ValidationMessage> erros = cpfValidator.invalidMessagesFor(cpf); 
		 		if(erros.size() > 0)
		            JOptionPane.showMessageDialog(null, "CPF Inválido!");

		 		else
		            JOptionPane.showMessageDialog(null, "CPF Válido!");

		 		
		 	}
		 });
		 btnFinalizarNovoCliente.setBounds(854, 386, 89, 23);
		 painelFinalizar.add(btnFinalizarNovoCliente);
		
		 this.setLocationRelativeTo(null);

		this.setVisible(true);
	}
}
