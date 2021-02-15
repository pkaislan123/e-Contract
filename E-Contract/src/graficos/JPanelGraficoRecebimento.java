package graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class JPanelGraficoRecebimento extends JPanel{

    private JLabel lblRecebidos = new JLabel("Recebidos: ");
    private JLabel lblReceber = new JLabel("a Receber: ");
   
	private int recebidos = 0;
	private int total_global = 0 ;
	
	public JPanelGraficoRecebimento(int total, int _recebidos)
	{
		this.recebidos = _recebidos;
		this.total_global = total;
		
		 

		lblRecebidos.setBounds(350, 27, 150, 40);
		lblRecebidos.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblRecebidos.setForeground(new Color(0,100,0));

	    this.add(lblRecebidos);

	    lblReceber.setBounds(100, 224, 150, 40);
	    lblReceber.setFont(new Font("Tahoma", Font.BOLD, 14));
	    lblReceber.setForeground(Color.red);

	    this.add(lblReceber);
	}
	
	
	public void setDados(int total, int _recebidos)
	{
	 this.total_global =total;	
	 this.recebidos = _recebidos;
	 
	 //1 assinado
	 //2 nao carregados
	}
	 

	public void paintComponent( Graphics g ) {
	    super.paintComponent( g );
	    this.setBackground(Color.white);
	    
	    int percentualrecebidos = 0; 
	    int percentualreceber = 0; 
	    int circulo_recebido = 0, 
	    		circulo_receber =0;
	    
	 
	    if(recebidos == 0 && total_global == 0)
	    {
	    	
	    	percentualreceber = 100;
	    	percentualrecebidos = 0;
	    	

	    	lblRecebidos.setText("Recebido: "+ 0 + "%");
	    	lblReceber.setText("a Receber: "+ 100 + "%");
	    	
	    	circulo_receber = 360; 
	    	circulo_recebido = 0 ;
	    	
	    }else {
	    	percentualrecebidos = (recebidos * 100)  / total_global;
	    	percentualreceber = ((total_global - recebidos) * 100)  / total_global;
	    	
	    	
	    	lblRecebidos.setText("Recebidos: "+ percentualrecebidos + "%");
	    	lblReceber.setText("a Receber: "+ percentualreceber + "%");

	    	 if(percentualreceber + percentualrecebidos != 100) {
	 	    	int diferenca = 100 - (percentualreceber + percentualrecebidos);
	 	    //	System.out.println("falta para os 100%: " + diferenca );

	 	    	percentualrecebidos = percentualrecebidos + diferenca;
	 	    }
	    	 
	    	 circulo_receber = percentualreceber  * 360 / 100; 
	    	 circulo_recebido = percentualrecebidos  * 360  / 100 ;
	     	
	     	/*
	     	 *    percentualUsado = usado * 360 / total; 
	    percentualDisponivel = disponivel * 360 / total; 
	     	 */
	    }
	 
	   
	   
	    g.setColor(new Color(255,69,0));
        g.fillArc(200,50,200,200, 90,circulo_receber );
        		    
        g.setColor(new Color(0,255,127));
        g.fillArc(200,50,200,200, 90,circulo_recebido * (-1) );

       
        g.setColor(new Color( 255, 250 ,205));
        g.fillOval(250, 100, 100, 100);
        //g.fillOval(10,50, 180, 180);	
	    
	}
	
	/*
	 *    
	 */
	

}
