package main.java.principal;


import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.text.MaskFormatter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication; 
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.formdev.flatlaf.FlatLightLaf;

import main.java.cadastros.CadastroFuncionariosHorarios;
import main.java.gui.TelaEntrada;
import main.java.gui.TelaFinanceiro;
import main.java.manipular.ArquivoConfiguracoes;
import main.java.manipular.Email2;
import main.java.manipular.MonitorarRomaneios;

import java.net.URL;
import javax.swing.*;
import javax.sound.sampled.*;

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

 



