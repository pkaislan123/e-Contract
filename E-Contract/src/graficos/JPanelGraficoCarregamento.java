package graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class JPanelGraficoCarregamento extends JPanel{

    private JLabel lblCarregados = new JLabel("carregados: ");
    private JLabel lblNaoCarregados = new JLabel("Não carregados: ");
   
	private int carregados = 0;
	private int total_global = 0 ;
	
	public JPanelGraficoCarregamento(int total, int carregados)
	{
		this.carregados = carregados;
		this.total_global = total;
		
		 

	    lblCarregados.setBounds(350, 27, 150, 40);
	    lblCarregados.setFont(new Font("Tahoma", Font.BOLD, 14));
    	lblCarregados.setForeground(new Color(0,100,0));

	    this.add(lblCarregados);

	    lblNaoCarregados.setBounds(100, 224, 150, 40);
	    lblNaoCarregados.setFont(new Font("Tahoma", Font.BOLD, 14));
    	lblNaoCarregados.setForeground(Color.red);

	    this.add(lblNaoCarregados);
	}
	
	
	public void setDados(int total, int carregados)
	{
	 this.total_global =total;	
	 this.carregados = carregados;
	 
	 //1 assinado
	 //2 nao carregados
	}
	 

	public void paintComponent( Graphics g ) {
	    super.paintComponent( g );
	    this.setBackground(Color.white);
	    
	    int percentualcarregados = 0; 
	    int percentualnao_carregados = 0; 
	    int circulo_nao_carregado = 0, 
	    		circulo_carregado =0;
	    
	 
	    if(carregados == 0 && total_global == 0)
	    {
	    	
	    	percentualnao_carregados = 100;
	    	percentualcarregados = 0;
	    	

	    	lblCarregados.setText("Carregado: "+ 0 + "%");
	    	lblNaoCarregados.setText("a Carregar: "+ 100 + "%");
	    	
	    	circulo_nao_carregado = 360; 
	    	circulo_carregado = 0 ;
	    	
	    }else {
	    	percentualcarregados = (carregados * 100)  / total_global;
	    	percentualnao_carregados = ((total_global - carregados) * 100)  / total_global;
	    	
	    	
	    	lblCarregados.setText("Carregado: "+ percentualcarregados  + "%");
	    	lblNaoCarregados.setText("a Carregar: "+ percentualnao_carregados + "%");

	    	 if(percentualnao_carregados + percentualcarregados != 100) {
	 	    	int diferenca = 100 - (percentualnao_carregados + percentualcarregados);
	 	    //	System.out.println("falta para os 100%: " + diferenca );

	 	    	percentualcarregados = percentualcarregados + diferenca;
	 	    }
	    	 
	    	 circulo_nao_carregado = percentualnao_carregados  * 360 / 100; 
	    	 circulo_carregado = percentualcarregados  * 360  / 100 ;
	     	
	     	/*
	     	 *    percentualUsado = usado * 360 / total; 
	    percentualDisponivel = disponivel * 360 / total; 
	     	 */
	    }
	 
	   
	   
	    g.setColor(new Color(255,69,0));
        g.fillArc(200,50,200,200, 90,circulo_nao_carregado );
        		    
        g.setColor(new Color(0,255,127));
        g.fillArc(200,50,200,200, 90,circulo_carregado * (-1) );

       
        g.setColor(new Color( 255, 250 ,205));
        g.fillOval(250, 100, 100, 100);
        //g.fillOval(10,50, 180, 180);	
	    
	}
	
	/*
	 *    
	 */
	

}
