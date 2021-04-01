package main.java.gui;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

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
import main.java.manipular.GetDadosGlobais;
import main.java.manipular.ManipularArquivoTerceiros;
import main.java.manipular.ManipularNotasFiscais;
import main.java.manipular.ManipularRomaneios;
import main.java.manipular.ManipularTxt;
import main.java.manipular.PorExtenso;
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
import main.java.relatoria.RelatorioContratoSimplificado;
import main.java.relatoria.RelatorioContratos;
import main.java.tratamento_proprio.Log;
import main.java.views_personalizadas.TelaEmEspera;
import main.java.views_personalizadas.TelaNotificacao;
import main.java.views_personalizadas.TelaNotificacaoSuperior;
import main.java.views_personalizadas.TelaNotificacaoSuperiorModoBusca;
import net.miginfocom.swing.MigLayout;
import outros.ValidaCNPJ;
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
import main.java.classesExtras.ComboBoxPersonalizado;
import main.java.classesExtras.ComboBoxRenderPersonalizado;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoSafras;

import java.awt.SystemColor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JTextArea;
import java.awt.Toolkit;

public class TelaEntrada extends JFrame implements GetDadosGlobais{


	private JPanelBackground contentPane;
	private char previo;
	private Log GerenciadorLog = new Log();
	private CadastroLogin login;
	private JProgressBar barra ;
	private JLabel lblStatus;
	private TelaEntrada isto; 
	
	public TelaEntrada() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(TelaEntrada.class.getResource("/imagens/logo_icone4.png")));
		
		getDadosGlobais();
		
        
		 isto = this;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 399, 273);
		
		contentPane = new JPanelBackground();
		contentPane.setBackground(new Color(139, 0, 139));
		 URL url = getClass().getResource("/imagens/contrato"+1+".jpg");
 		ImageIcon img = new ImageIcon(url);
		contentPane.setImg(img);
		contentPane.repaint();
		/* new Thread() {
            
            @Override
            public void run() {
             int i = 1;
            	while(i <= 5)
             {
            	 
                URL url = getClass().getResource("/imagens/contrato"+i+".jpg");
         		ImageIcon img = new ImageIcon(url);
        		contentPane.setImg(img);
        		contentPane.repaint();
        		try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		i++;
        		if( i == 6)
        		{
        			i = 1;
        		}

             }
               
            }
          }.start();*/
        
		//contentPane.setBackground(new Color(0, 206, 209));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		//URL url2 = getClass().getResource("fundo.jpg");
        //ImageIcon img2 = new ImageIcon(url2);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("*Copyright Todos os Direitos Reservados E-Contract Titaniwm 2020 ");
		lblNewLabel.setForeground(new Color(0, 0, 128));
		lblNewLabel.setBounds(21, 232, 357, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblEcontract = new JLabel("e-Contract");
		lblEcontract.setForeground(new Color(255, 255, 255));
		lblEcontract.setFont(new Font("Arial", Font.BOLD, 30));
		lblEcontract.setBounds(10, 11, 182, 39);
		contentPane.add(lblEcontract);
		
		JLabel lblEficincia = new JLabel("Eficiência na gestão de contratos");
		lblEficincia.setForeground(new Color(255, 255, 255));
		lblEficincia.setFont(new Font("Arial", Font.BOLD, 18));
		lblEficincia.setBounds(49, 50, 305, 29);
		contentPane.add(lblEficincia);
		
		 lblStatus = new JLabel("Carregando...");
		lblStatus.setForeground(Color.WHITE);
		lblStatus.setFont(new Font("Arial", Font.BOLD, 14));
		lblStatus.setBounds(21, 128, 305, 29);
		contentPane.add(lblStatus);
		
		 barra = new JProgressBar();
		 barra.setBackground( new Color(102, 51, 204));

		 barra.setBackground(Color.GREEN);
		 barra.setBounds(21, 179, 357, 14);
		 barra.setBorder(null);
		 contentPane.add(barra);
		
		
	
		

		
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		
		
		
		
		 
	}
	
	
	@Override
	public void getDadosGlobais() {
		DadosGlobais userGlobal = DadosGlobais.getInstance();
        userGlobal.setGerenciadorLog(GerenciadorLog);
        login = userGlobal.getLogin();
	}
	
	
	
	public void realizarTeste() {
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		lblStatus.setText("Lendo arquivo de configurações...");

		ArquivoConfiguracoes ler = new ArquivoConfiguracoes();
        barra.setValue(10);		

        boolean leitura = false;
		
		leitura = ler.testeConfiguragoes();
		
		if(leitura) {
		    isto.dispose();

			TelaLogin telaLogin = new TelaLogin();

		}else {
			TelaEmEspera esperar_sair = new TelaEmEspera(0);
			
			new Thread() {
				@Override
				public void run() {
					
					esperar_sair.setVisible(true);
				}
				
			}.start();
			
			
			for(int i = 5; i >= 0; i--) {
				
				
				esperar_sair.setMsg2("Erro fatal!\nConsulte o administrador!\nSaindo em " + i);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			    esperar_sair.fechar();
			    isto.dispose();
			    System.exit(0);
		}
		
		
	}
	
	
}
