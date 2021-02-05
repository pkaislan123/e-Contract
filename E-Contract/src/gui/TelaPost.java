package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import cadastros.CadastroLogin;
import cadastros.CadastroNota;
import conexaoBanco.GerenciarBancoNotas;
import manipular.ConfiguracoesGlobais;
import outros.DadosGlobais;
import tratamento_proprio.Log;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TelaPost extends JDialog {

	private JTabbedPane painelPrincipal ;
	private JPanel abaMeus = new JPanel();
	private JPanel abaGlobais = new JPanel();
	private TelaPost isto;
	private final JButton btnMudar = new JButton("Próximo");
	private final JLabel lblNomeNota = new JLabel("");
	private final JTextArea textAreaTexto;
	private final JButton btnVizualizarNota = new JButton("Vizualizar");
	private final JLabel lblNomeNotaGlobal = new JLabel("Anotação 1");
	private final JTextArea textAreaTextoGlobal = new JTextArea();
	private final JButton btnVizualizarNotaGlobal = new JButton("Vizualizar");
	private final JButton btnMudarGlobal = new JButton("Próximo");
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	
	
	
	public TelaPost() {

		 isto = this;
		
		setResizable(false);
	
		getDadosGlobais();

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("E-Contract - Topicos");
		setBounds(0, 0, 370, 239);
		painelPrincipal = new JTabbedPane();
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		//contentPanel.setBackground(new Color(255, 255, 255));
		//contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		//setContentPane(contentPanel);
		//contentPanel.setLayout(null);
		
		abaMeus.setBackground(new Color(255, 255, 153));
		abaGlobais.setBackground(new Color(255, 255, 153));

		//adiciona novos paines e suas abas
		painelPrincipal.addTab("Meus", abaMeus);
		abaMeus.setLayout(null);
		btnMudar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnMudar.setBounds(281, 141, 73, 28);
		
		abaMeus.add(btnMudar);
		lblNomeNota.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblNomeNota.setBounds(118, 6, 141, 16);
		
		abaMeus.add(lblNomeNota);
		textAreaTexto = new JTextArea();
		textAreaTexto.setLocation(6, 34);
		textAreaTexto.setSize(342, 100);
		textAreaTexto.setForeground(new Color(0, 0, 102));
		textAreaTexto.setFont(new Font("SansSerif", Font.PLAIN, 20));
		textAreaTexto.setOpaque(true);
		textAreaTexto.setBackground(new Color(0,0,0,0));
		textAreaTexto.setBorder(null);
		textAreaTexto.setEditable(false);
		textAreaTexto.setWrapStyleWord(true);
		textAreaTexto.setLineWrap(true);
		
		
		abaMeus.add(textAreaTexto);
		
		btnVizualizarNota.setBounds(181, 141, 90, 28);
		
		abaMeus.add(btnVizualizarNota);
		painelPrincipal.addTab("Globais", abaGlobais);
		abaGlobais.setLayout(null);
		lblNomeNotaGlobal.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblNomeNotaGlobal.setBounds(6, 6, 344, 16);
		
		abaGlobais.add(lblNomeNotaGlobal);
		textAreaTextoGlobal.setEditable(false);
		textAreaTextoGlobal.setWrapStyleWord(true);
		textAreaTextoGlobal.setLineWrap(true);
		textAreaTextoGlobal.setBounds(9, 31, 338, 91);
		
		abaGlobais.add(textAreaTextoGlobal);
		btnVizualizarNotaGlobal.setBounds(177, 136, 90, 28);
		
		abaGlobais.add(btnVizualizarNotaGlobal);
		btnMudarGlobal.setBounds(277, 136, 73, 28);
		
		abaGlobais.add(btnMudarGlobal);
		
		getContentPane().add(painelPrincipal, BorderLayout.CENTER);

		   Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
	        java.awt.Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
	        int taskBarHeight = scrnSize.height - winSize.height;
	        System.out.printf("Altura: %d\n", taskBarHeight);
	        
	        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
	        java.awt.Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
	        int x = (int) rect.getMaxX() - getWidth( ) ;
	        int y = 10 ;
		
			setAlwaysOnTop(true);

			
		
			    GraphicsDevice[] gd = ge.getScreenDevices();
			    if(gd.length > 1) {
				        rect = gd[1].getDefaultConfiguration().getBounds();
				         x = (int) rect.getMaxX() - getWidth( ) ;
				         y = 80 ;
			           this.setLocation(x, y);

			    }else {
			           this.setLocation(x, y);

			    }
			
			

          


			
		

	}
	
	

	public void procurarNotas() {
		new Thread() {
			@Override
			public void run() {
				
				while(true) {
					
					//pegar minhas anotacoes
					ArrayList<CadastroNota> minhas_notas = new ArrayList<>();
					GerenciarBancoNotas gerenciar = new GerenciarBancoNotas();
					
					minhas_notas = gerenciar.getnotas(login.getId());
					
					//verifica quais notas sao de topico
					ArrayList<CadastroNota> minhas_notas_topico = new ArrayList<>();
					for(CadastroNota not: minhas_notas) {
						if(not.getTipo() == 2) {
							//se e uma nota do tipo topico
							minhas_notas_topico.add(not);
						}
					}
					
					if(minhas_notas_topico.size() > 0) {
						for(CadastroNota not: minhas_notas_topico) {

							java.awt.EventQueue.invokeLater(new Runnable() { 
							    public void run() { 
							    	lblNomeNota.setText(not.getNome());
							    	
							    	
									textAreaTexto.setText(not.getTexto());
								
                                      abaMeus.repaint();
                                      abaMeus.updateUI();
											   
							    } 
							}); 
					    
						
						try {
							Thread.sleep(10000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						}
						
						
					}
					
					
				}
				
			}
		}.start();
	
	}
	
	
	
	public void getDadosGlobais() {
		//gerenciador de log
				DadosGlobais dados = DadosGlobais.getInstance();
				 GerenciadorLog = dados.getGerenciadorLog();
				 configs_globais = dados.getConfigs_globais();
				 
				 //usuario logado
				  login = dados.getLogin();
		
	}
	 
}
