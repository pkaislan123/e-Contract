package gui;

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

import cadastros.CadastroLogin;
import conexaoBanco.GerenciarBancoLogin;
import manipular.GetDadosGlobais;
import manipular.ArquivoConfiguracoes;
import manipular.ManipularTxt;
import outros.DadosGlobais;
import outros.JButtonPersonalizado;
import outros.JPanelBackground;
import outros.JPanelTransparent;
import outros.JPasswordFieldPersonalizado;
import outros.JProgressBarPersonalizado;
import outros.JTextFieldPersonalizado;
import outros.TratarDados;
import tratamento_proprio.Log;
import views_personalizadas.TelaEmEspera;

import java.awt.SystemColor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JTextArea;

public class TelaEntrada extends JDialog implements GetDadosGlobais{


	private JPanelBackground contentPane;
	private char previo;
	private Log GerenciadorLog = new Log();
	private CadastroLogin login;
	private JProgressBar barra ;
	private JLabel lblStatus;
	private TelaEntrada isto; 
	
	public TelaEntrada() {
		
		getDadosGlobais();
		
        
		 isto = this;
		setModal(true);
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
			TelaEmEspera esperar_sair = new TelaEmEspera();
			
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
