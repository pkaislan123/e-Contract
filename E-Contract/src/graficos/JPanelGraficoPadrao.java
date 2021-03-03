package graficos;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class JPanelGraficoPadrao extends JPanel{

    private JLabel lblassinados ;
    private JLabel lblnao_assinados ;
   
	private int carregado = 0;
	private int total_global = 0 ;
	
	private String texto1, texto2;
	
	public JPanelGraficoPadrao(int total, int _carregado, String _texto1, String _texto2)
	{
		
		
		this.total_global = total;
		this.carregado = carregado;

	
		lblassinados = new JLabel();
	    lblassinados.setBounds(200, 27, 150, 14);
	    this.add(lblassinados);

	    lblnao_assinados = new JLabel();
	    lblnao_assinados.setBounds(27, 224, 150, 25);
	    this.add(lblnao_assinados);
	    
	    
	    this.texto1 = _texto1;
	    this.texto2 = _texto2;
	}
	
	
	public void setDados(int total, int _carregado)
	{
	 this.total_global =total;	
	 this.carregado = _carregado;
	 
	 //1 assinado
	 //2 nao assinados
	}
	 

	public void paintComponent( Graphics g ) {
	    super.paintComponent( g );
	    
	    this.setBackground(new Color(0,0,0,0));
	    this.setOpaque(true);

	    int percentualassinados = 0; 
	    int percentualnao_assinados = 0; 
	    int circulo_nao_assinado = 0,  circulo_assinado =0;
	    
	 
	    if(carregado == 0 && total_global == 0)
	    {
	    	
	    	percentualnao_assinados = 100;
	    	percentualassinados = 0;
	    	

	    	lblassinados.setText(texto1 + 0 + "%");
	    	lblnao_assinados.setText(texto2 + 0 + "%");
	    	
	    	circulo_nao_assinado = 0; 
	     	circulo_assinado = 0; 
	    	
	    }else {
	    	percentualassinados = (carregado * 100)  / total_global;
	    	percentualnao_assinados = ((total_global - carregado) * 100)  / total_global;
	    	
	    	
	    	lblassinados.setText(texto1 + percentualassinados  + "%");
	    	lblnao_assinados.setText(texto2 + percentualnao_assinados + "%");
	  	    
	    	 if(percentualnao_assinados + percentualassinados != 100) {
	 	    	int diferenca = 100 - (percentualnao_assinados + percentualassinados);

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
        g.fillArc(80,50,150,150, 90,circulo_nao_assinado );
        		    
        g.setColor(Color.green);
        g.fillArc(80,50,150,150, 90,circulo_assinado * (-1) );

        g.setColor(new Color(240, 255, 255));
        g.fillOval(118, 85, 80, 80);
        //g.fillOval(10,50, 180, 180);	
	    
	}
	
	/*
	 *    
	 */
	

}
