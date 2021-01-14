package outros;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JDialog;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;

import gui.TelaElaborarNovoContrato;
import gui.TelaRecortarImagem2;


public class Gerador_Desenho extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener{
	 
    Dimension Dimensao = Toolkit.getDefaultToolkit().getScreenSize();  
    private  JLabel lblImagem;

    // Criando local onde ficará armazenadas as imagens.
   
    private BufferedImage Buffered_da_Imagem = new BufferedImage((int)Dimensao.getWidth(),
            (int)Dimensao.getHeight(), BufferedImage.TYPE_INT_RGB);
   
    private BufferedImage Buffered_da_Reta = new BufferedImage((int)Dimensao.getWidth(),
            (int)Dimensao.getHeight(), BufferedImage.TYPE_INT_RGB);
	 private int altura, largura;
    private int valor;
    private Color Ultima_Cor;
    private int x;
    private int y;  
    private static JDialog telaPai;
    private ImageIcon img;

    private int zoom = 100;
    private static boolean controlStatus = false;

    private int x_inicio;
    private int y_inicio;
    ImageIcon icon_global;
    BufferedImage imagem_global;
    
    
    private int x_antes_recortar , y_antes_recortar, x_inicio_antes_recortar, y_inicio_antes_recortar, altura_antes_recortar, largura_antes_recortar;
    private BufferedImage imagem_antes_recortar;
    
   
    public Gerador_Desenho(BufferedImage imagem, int _altura, int _largura){
       

        Graphics g_Imagem = Buffered_da_Imagem.createGraphics();
        g_Imagem.setColor(Color.WHITE);
        g_Imagem.fillRect(0, 0, Buffered_da_Imagem.getWidth(), Buffered_da_Imagem.getHeight());
        g_Imagem.dispose();
       
        Graphics g_Reta = Buffered_da_Reta.createGraphics();  
        g_Reta.setColor(Color.WHITE);  
        g_Reta.fillRect(0, 0, Buffered_da_Reta.getWidth(), Buffered_da_Reta.getHeight());
        g_Reta.dispose();
       
        if (Ultima_Cor == null)
            Ultima_Cor = Color.RED;
        
         this.imagem_global = imagem;
		  this.icon_global = new ImageIcon(imagem_global);
           this.altura = _altura;
           this.largura = _largura;

           Buffered_da_Imagem  = imagem_global;
           

  		 KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

  	         @Override
  	         public boolean dispatchKeyEvent(KeyEvent ke) {
  	             synchronized (IsKeyPressed.class) {
  	                 switch (ke.getID()) {
  	                 case KeyEvent.KEY_PRESSED:
  	                     if (ke.getKeyCode() == KeyEvent.VK_CONTROL) {

  	                    	 ((TelaRecortarImagem2) telaPai).setCtrl("CTRL pressionado");

  	                    	 controlStatus = true;
  	                    	 
  	                     }
  	                     break;

  	                 case KeyEvent.KEY_RELEASED:
  	                     if (ke.getKeyCode() == KeyEvent.VK_CONTROL) {
  	                    	 ((TelaRecortarImagem2) telaPai).setCtrl("CTRL solto");

  	                    	 controlStatus = false;

  	                     }
  	                     break;
  	                 }
  	                 return false;
  	             }
  	         }
  	     });
  			
  		 
  		
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
    }  
   
  
       
   
    public void paintComponent(Graphics g) {  
        super.paintComponent(g);  
       
        
	    g.drawImage(Buffered_da_Imagem, 0, 0, altura, largura, this);

       g.drawRect(x_inicio,y_inicio,x,y);
        
    }
   
                   
         
    public void setForma(int newValor, Color newCor){
        valor = newValor;
        Ultima_Cor = newCor;
    }
   
   
   
    public void paint_retangulo(int x2, int y2){
       
        Graphics2D g_retangulo = Buffered_da_Reta.createGraphics();  
        g_retangulo.drawImage(Buffered_da_Imagem, 0, 0, null);
        g_retangulo.setColor(Ultima_Cor);
       
        g_retangulo.setStroke(new BasicStroke(2.0f));
       
        if (x2>x && y2>y)
            g_retangulo.drawRect(x,y,x2-x,y2-y);
        if (x2>x && y>y2)
            g_retangulo.drawRect(x,y2,x2-x,y-y2);
        if (x>x2 && y>y2)
            g_retangulo.drawRect(x2,y2,x-x2,y-y2);
        if (x>x2 && y2>y)
            g_retangulo.drawRect(x2,y,x-x2,y2-y);  
           
        g_retangulo.dispose();
    }
           
           
           
     
   
   
   
    // Capturando os Eventos com o mouse
    public void mousePressed(MouseEvent e) { 
  
        // Obtendo as coordenadas do mouse
        x_inicio = e.getX();  
        y_inicio = e.getY();  
      
        repaint(); 
    }    
   
   
    public void mouseReleased(MouseEvent e) {
   
        /*Forma(e.getX(), e.getY());
       
        Graphics g_Imagem = Buffered_da_Imagem.createGraphics();
        g_Imagem.drawImage(Buffered_da_Reta, 0, 0, null);
        g_Imagem.dispose();  */
    	 x = e.getX();  
         y = e.getY();
    

        repaint(); 
    }          
   
   
    public void mouseClicked(MouseEvent e) {
       
       
    
    }
   
   
    public void mouseEntered(MouseEvent e) {
    }
   
   
    public void mouseExited(MouseEvent e) {
    }
   
   
    public void mouseDragged(MouseEvent e) {
    	 x = e.getX();  
         y = e.getY();
    

        repaint(); 
   	 
       
    }
   
   
    public void mouseMoved(MouseEvent e) {
    }
   
   
   

	public static class IsKeyPressed {
	
	    public static boolean isWPressed() {
	        synchronized (IsKeyPressed.class) {
	            return controlStatus;
	        }
	    }
	
	
	}
  
    public void mouseWheelMoved(MouseWheelEvent e) {
		 if (IsKeyPressed.isWPressed()) {
		
		  if (e.getWheelRotation() > 0) {
			 
			  
			  if(zoom >=0 )
				  zoom--;

           } else {
           	  zoom++;
				
		}
		 
       	 ((TelaRecortarImagem2) telaPai).setPorcentagem(zoom + " %");

			  zoom(imagem_global,imagem_global.getWidth(), imagem_global.getHeight(),zoom, this );

			}

	}
	
	public void zoom(BufferedImage originalImage, int imageWidth, int imageHeight,  int zoomLevel, Gerador_Desenho lblImagem) {
		java.awt.EventQueue.invokeLater(new Runnable() { 
		    public void run() { 
		    	int newImageWidth = imageWidth * zoomLevel/100;
		int newImageHeight = imageHeight * zoomLevel/100;
		
		BufferedImage resizedImage = new BufferedImage(newImageWidth , newImageHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, newImageWidth , newImageHeight , null);
		g.dispose();
		ImageIcon img = new ImageIcon(resizedImage);

		setDimensao(newImageWidth,newImageHeight);
		Buffered_da_Imagem = resizedImage;
		
		lblImagem.repaint();

		
		
	  } 
			}); 
	}
    
	  public void setDimensao(int _altura, int _largura){
	    	this.altura = _altura;
	    	this.largura = _largura;
	    }
	  
	  public void setImg(ImageIcon img2){
		  icon_global = img2;
	    }
    
	  
	  public void setTelaPai(JDialog _tela_pai) {
		  this.telaPai = _tela_pai;
	  }
	  
	  
	  public void recortar() {
		  try {
			  
			    //antes de recordar
			   imagem_antes_recortar = Buffered_da_Imagem;
			   x_inicio_antes_recortar = x_inicio;
			   y_inicio_antes_recortar = y_inicio;
			   altura_antes_recortar = altura;
			   largura_antes_recortar = largura;

			   x_antes_recortar = x;
			   y_antes_recortar = y;
			  
				BufferedImage originalImgage = Buffered_da_Imagem;
				
				System.out.println("Original Image Dimension: "+originalImgage.getWidth()+"x"+originalImgage.getHeight());

				BufferedImage SubImgage = originalImgage.getSubimage(x_inicio, y_inicio, x, y);
				Buffered_da_Imagem = SubImgage;
				imagem_global = Buffered_da_Imagem;
				setDimensao(x,y);
				repaint();
				System.out.println("Cropped Image Dimension: "+SubImgage.getWidth()+"x"+SubImgage.getHeight());

				


			} catch (Exception e) {
				e.printStackTrace();
			}
	  }
	  
	  public void desfazer() {
		  
		  Buffered_da_Imagem= imagem_antes_recortar;
		  x_inicio = x_inicio_antes_recortar ;
		  y_inicio= y_inicio_antes_recortar ;
		   x = x_antes_recortar ;
		   y = y_antes_recortar ;
		   setDimensao(altura_antes_recortar, largura_antes_recortar);
		  
		  
		  repaint();
	  }
	  
	  
	  public String retornarImagem() {
		  
			try {
				File outputfile = new File("C:\\ProgramData\\E-Contract\\temp_files\\recortado");

				ImageIO.write(this.Buffered_da_Imagem, "png", outputfile);
				System.out.println("Image cropped successfully: "+outputfile.getPath());
				
				  return outputfile.getAbsolutePath();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
                
				return null;
			}

			
		  
	  }
}

