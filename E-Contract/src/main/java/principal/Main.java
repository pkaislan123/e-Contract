package main.java.principal;


import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication; 
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import main.java.gui.TelaEntrada;
import main.java.manipular.ArquivoConfiguracoes;
import main.java.manipular.MonitorarRomaneios;

import java.net.URL;
import javax.swing.*;
import javax.sound.sampled.*;


@SpringBootApplication
public class Main {

 public static void main(String[] args) {
	
	 
	  String start = "";
	
	    if(args.length > 0   ) {
	    	if(args[0].equalsIgnoreCase("busca")) {
	    		ArquivoConfiguracoes ler = new ArquivoConfiguracoes();

		        boolean leitura = false;
				
				leitura = ler.testeConfiguragoes();
				
				if(leitura) {

					MonitorarRomaneios monitorar = new MonitorarRomaneios();
		        	monitorar.vigiarRomaneios();

				}else {
					
				}
	        }
	    	
	    }else if(start.equals("busca")) {
	    	ArquivoConfiguracoes ler = new ArquivoConfiguracoes();

	        boolean leitura = false;
			
			leitura = ler.testeConfiguragoes();
			
			if(leitura) {

				MonitorarRomaneios monitorar = new MonitorarRomaneios();
	        	monitorar.vigiarRomaneios();

			}else {
				
			}
	    	
	    }
	    else {
		try {
			for ( LookAndFeelInfo info : UIManager.getInstalledLookAndFeels() ) {
			if ( "Nimbus".equals( info.getName() ) ) {
			UIManager.setLookAndFeel( info.getClassName() );
			break;
			}
			}
			} catch ( UnsupportedLookAndFeelException exc ) {
			exc.printStackTrace();
			} catch ( ClassNotFoundException exc ) {
			exc.printStackTrace();
			} catch ( InstantiationException exc ) {
			exc.printStackTrace();
			} catch ( IllegalAccessException exc ) {
			exc.printStackTrace();
			}
		
		//TelaPadrao padrao = new TelaPadrao();
		
	   //Abre a tela de login
		//TelaLogin login = new TelaLogin();
		TelaEntrada entrada = new TelaEntrada();
		new Thread() {
			@Override
			public void run() {
				entrada.setVisible(true);

			}
		}.start();
		
		entrada.realizarTeste();
	
    
	
	
	
	    }
	   
 }
		
}

 


