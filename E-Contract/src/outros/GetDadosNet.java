package outros;

import java.awt.MediaTracker;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.swing.ImageIcon;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetDadosNet {
	ImageIcon imgIcon;
	
	public GetDadosNet() {
		
	}
	
	public ImageIcon getImagens()
	{
		try {
			Document doc = Jsoup.connect("https://www.aneel.gov.br/bandeiras-tarifarias").get();
			  Elements images = doc.select("img");
              for (Element image : images) {

                  System.out.println("\nsrc : " + image.attr("src"));
                  System.out.println("height : " + image.attr("height"));
                  System.out.println("width : " + image.attr("width"));
                  System.out.println("alt : " + image.attr("alt"));
                  
                  if(image.attr("alt").equals("Bandeira Tarifaria do Mes"))
                  {
                	  System.out.println("achado");
                	  URL img = new URL("https://www.aneel.gov.br"+image.attr("src"));
              		imgIcon = new ImageIcon(img);
                      while(imgIcon.getImageLoadStatus() == MediaTracker.LOADING); 
                	  
                  }

              }
	        
			
			
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return imgIcon;

	}
	
	public ImageIcon getDados() {
	
		try {
		Document doc = Jsoup.connect("https://www.aneel.gov.br/bandeiras-tarifarias").get();
		URL img = new URL("https://www.aneel.gov.br"+doc.getElementsByAttributeValue("alt", "Bandeira Tarifaria do M�s").attr("src"));
		imgIcon = new ImageIcon(img);
        while(imgIcon.getImageLoadStatus() == MediaTracker.LOADING); 
      
        
		
		
		} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
		return imgIcon;
		
	
	
	}
	
}
