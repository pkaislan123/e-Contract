package main.java.views_personalizadas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import main.java.outros.JPanelTransparent;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;




public class TelaEmEspera extends JDialog {
	
	
	private TelaEmEspera isto;
	private JTextArea textArea;
	private JLabel status_msg;
	private JButton btnOk;
	private boolean rodar = true;
	public TelaEmEspera(int flag) {
		getContentPane().setBackground(new Color(240, 255, 240));
		
		setBounds(100, 100, 332, 151);
		 isto = this;
		getContentPane().setLayout(null);
		
		
		JLabel status_msg_1 = new JLabel("   Operação em andamento");
		status_msg_1.setBounds(0, 0, 326, 26);
		getContentPane().add(status_msg_1);
		status_msg_1.setOpaque(true);
		status_msg_1.setBackground(new Color(75, 0, 130));
		status_msg_1.setForeground(new Color(255, 255, 255));
		status_msg_1.setFont(new Font("Cambria", Font.BOLD, 14));
		
		 	
		 	 status_msg = new JLabel("Aguarde2...");
		 	 status_msg.setBounds(10, 70, 238, 26);
		 	 getContentPane().add(status_msg);
		 	 status_msg.setForeground(Color.BLACK);
		 	 status_msg.setFont(new Font("Cambria", Font.BOLD, 14));
		 	 
		 	 	status_msg.setText("");
		
		JPanelTransparent panel_1 = new JPanelTransparent();
		panel_1.setBounds(260,48, 48, 48);
		getContentPane().add(panel_1);
		URL url2 = getClass().getResource("/imagens/infinite.gif");
		ImageIcon img2 = new ImageIcon(url2);
		panel_1.setImg(img2);
		
		 textArea = new JTextArea();
		 textArea.setLineWrap(true);
		 textArea.setWrapStyleWord(true);
		 textArea.setText("Teste");
		 textArea.setOpaque(false);
		 textArea.setBounds(10, 37, 228, 99);
		 textArea.setBorder(null);
		 textArea.setEditable(false);

		getContentPane().add(textArea);
		
		btnOk = new JButton("Sair");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isto.fechar();
			}
		});
		btnOk.setEnabled(false);
		btnOk.setVisible(false);
		btnOk.setForeground(Color.BLACK);
		btnOk.setBackground(Color.RED);
		btnOk.setBounds(258, 108, 50, 28);
		getContentPane().add(btnOk);
		
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		//setModal(true);
		setResizable(false);
		setUndecorated(true);
		
		if(flag == 1) {
		MonitorTempo contar_tempo = new MonitorTempo();
		contar_tempo.start();
		}
	}
	
	
	
	public void setMsg(String texto) {
		textArea.setEditable(false);
		textArea.setEnabled(false);
		textArea.setVisible(false);
		status_msg.setText(texto);
		status_msg.repaint();
		status_msg.updateUI();
	}
	
	public void setMsg2(String texto) {
		
		java.awt.EventQueue.invokeLater(new Runnable() { 
		    public void run() { 
		    	status_msg.setEnabled(false);
				status_msg.setVisible(false);
				textArea.setText(texto);

				textArea.repaint();
				textArea.updateUI();
			  } 
		}); 
		
		


	}
	
	class MonitorTempo extends Thread{
		  private boolean contagemFinalizada = false;
		  public void run(){
		    try 
		    {
			
		    	int tempo_decorrido = 0;
		    	btnOk.setEnabled(true);
				btnOk.setVisible(true);
		    	while(rodar)
		    	{

		    		if(tempo_decorrido > 38000) {
			    		setMsg2("Site do sintegra aws offline: \nPesquisa será interrompida\nAcrescente os campos manualmente ou tente novamente mais tarde\nTempo decorrido: " + tempo_decorrido/1000 + "s");

			    	
		    		}else
		    		setMsg2("Tempo Decorrido da Pesquisa: " + tempo_decorrido/1000 + "s");
		    		Thread.sleep(1000);
		    		tempo_decorrido += 1000;
		    	}
			    	
		    		
		    	
		    
				
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
		
		}
	
	
	
	public void fechar() {
		rodar = false;
		isto.dispose();
	}
}



