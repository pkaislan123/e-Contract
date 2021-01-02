package graficos;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class JPanelGrafico extends JPanel{

    private JLabel lblassinados = new JLabel("Assinados: ");
    private JLabel lblnao_assinados = new JLabel("Não Assinados: ");
   
	private int assinados = 0;
	private int total_global = 0 ;
	
	public void setDados(int total, int assinados)
	{
	 this.total_global =total;	
	 this.assinados = assinados;
	 
	 //1 assinado
	 //2 nao assinados
	}
	 

	public void paintComponent( Graphics g ) {
	    super.paintComponent( g );
	    
	    
	    int percentualassinados = 0; 
	    int percentualnao_assinados = 0; 
	    int circulo_nao_assinado = 0,  circulo_assinado =0;
	    
	 
	    if(assinados == 0 && total_global == 0)
	    {
	    	
	    	percentualnao_assinados = 100;
	    	percentualassinados = 0;
	    	

	    	lblassinados.setText("Assinados: "+ 0 + "%");
	    	lblnao_assinados.setText("Assinar: "+ 0 + "%");
	    	
	    	circulo_nao_assinado = 0; 
	     	circulo_assinado = 0; 
	    	
	    }else {
	    	percentualassinados = (assinados * 100)  / total_global;
	    	percentualnao_assinados = ((total_global - assinados) * 100)  / total_global;
	    	
	    	
	    	lblassinados.setText("Assinados: "+ percentualassinados  + "%");
	    	lblnao_assinados.setText("Assinar: "+ percentualnao_assinados + "%");
	  	    
	    	 if(percentualnao_assinados + percentualassinados != 100) {
	 	    	int diferenca = 100 - (percentualnao_assinados + percentualassinados);
	 	    	System.out.println("falta para os 100%: " + diferenca );

	 	    	percentualassinados = percentualassinados + diferenca;
	 	    }
	    	 
	  	    circulo_nao_assinado = percentualnao_assinados  * 360 / 100; 
	     	circulo_assinado = percentualassinados  * 360  / 100 ;
	     	
	     	/*
	     	 *    percentualUsado = usado * 360 / total; 
	    percentualDisponivel = disponivel * 360 / total; 
	     	 */
	    }
	 
	   
	   
	    g.setColor(Color.red);
        g.fillArc(200,50,200,200, 90,circulo_nao_assinado );
        		    
        g.setColor(Color.green);
        g.fillArc(200,50,200,200, 90,circulo_assinado * (-1) );

        g.setColor(Color.white);
        g.fillOval(250, 100, 100, 100);
        //g.fillOval(10,50, 180, 180);	
	    
	}
	
	/*
	 *    
	 */
	
	public JPanelGrafico(int total, int assinados)
	{
		this.assinados = assinados;
		this.total_global = total;
		
		 
		
	    lblassinados.setBounds(350, 27, 150, 14);
	    this.add(lblassinados);

	    lblnao_assinados.setBounds(100, 224, 150, 25);
	    this.add(lblnao_assinados);
	}
	
}
