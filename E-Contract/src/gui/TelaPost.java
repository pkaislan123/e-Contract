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
import java.awt.Window;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

public class TelaPost extends JDialog {

	private JPanel abaMeus = new JPanel();
	private TelaPost isto;
	private final JLabel lblNomeNota = new JLabel("");
	private final JTextArea textAreaTexto;
	private final JLabel lblNomeNotaGlobal = new JLabel("Anotação 1");
	private final JTextArea textAreaTextoGlobal = new JTextArea();
	private final JButton btnVizualizarNotaGlobal = new JButton("Vizualizar");
	private final JButton btnMudarGlobal = new JButton("Próximo");
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	public boolean ativo = false;
	
	
	public TelaPost(Window janela_pai) {

		 isto = this;
		setResizable(false);
	
		getDadosGlobais();

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("E-Contract - Topicos");
		setBounds(0, 0, 215, 170);
		getContentPane().setLayout(null);
		abaMeus.setBounds(0, 0, 215, 170);
		
		//contentPanel.setBackground(new Color(255, 255, 255));
		//contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		//setContentPane(contentPanel);
		//contentPanel.setLayout(null);
		
		abaMeus.setBackground(new Color(255, 255, 153));

		//adiciona novos paines e suas abas
		abaMeus.setLayout(null);
		lblNomeNota.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblNomeNota.setBounds(23, 6, 141, 16);
		
		abaMeus.add(lblNomeNota);
		textAreaTexto = new JTextArea();
		textAreaTexto.setLocation(6, 34);
		textAreaTexto.setSize(203, 130);
		textAreaTexto.setForeground(new Color(0, 0, 102));
		textAreaTexto.setFont(new Font("SansSerif", Font.PLAIN, 20));
		textAreaTexto.setOpaque(true);
		textAreaTexto.setBackground(new Color(0,0,0,0));
		textAreaTexto.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		textAreaTexto.setEditable(false);
		textAreaTexto.setWrapStyleWord(true);
		textAreaTexto.setLineWrap(true);
		
		
		abaMeus.add(textAreaTexto);
		getContentPane().add(abaMeus);
		
		JButton btnNewButton = new JButton("X");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isto.setVisible(false);
			}
		});
		btnNewButton.setBackground(Color.RED);
		btnNewButton.setFont(new Font("SansSerif", Font.PLAIN, 8));
		btnNewButton.setBounds(176, 6, 33, 22);
		abaMeus.add(btnNewButton);
		
 
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
			
			

          
              this.setUndecorated(true);

			
		

	}
	
	public void fechar() {
		this.ativo = false;
		isto.dispose();
	}

	public void procurarNotas(boolean _ativo) {
		this.ativo = _ativo;

		new Thread() {
			@Override
			public void run() {
				
				while(ativo) {
					
					//pegar minhas anotacoes
					ArrayList<CadastroNota> minhas_notas = new ArrayList<>();
					ArrayList<CadastroNota> todas_as_notas = new ArrayList<>();

					GerenciarBancoNotas gerenciar = new GerenciarBancoNotas();
					
					minhas_notas = gerenciar.getnotas(login.getId());
					todas_as_notas = gerenciar.getTodasNotas();
					//verifica quais notas sao de topico
					ArrayList<CadastroNota> minhas_notas_topico = new ArrayList<>();
					for(CadastroNota not: minhas_notas) {
						if(not.getTipo() == 2) {
							//se e uma nota do tipo topico
							minhas_notas_topico.add(not);
						}
					}
					
					//verifica quais notas sao de topico global
					ArrayList<CadastroNota> notas_topico_global = new ArrayList<>();
					for(CadastroNota not: minhas_notas) {
						if(not.getTipo() == 3) {
							//se e uma nota do tipo topico global
							notas_topico_global.add(not);
						}
					}
					
					ArrayList<CadastroNota> notas_mostrar = new ArrayList<>();
					notas_mostrar.addAll(notas_topico_global);
					notas_mostrar.addAll(minhas_notas_topico);

					
					if(notas_mostrar.size() > 0) {
						for(CadastroNota not: notas_mostrar) {

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
					
					try {
						Thread.sleep(20000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
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
