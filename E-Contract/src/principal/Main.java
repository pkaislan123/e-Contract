package principal;

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

import cadastros.CadastroLogin;
import cadastros.CadastroModelo;
import conexaoBanco.GerenciarBancoContratos;
import gui.TelaBaixarNotas;
import gui.TelaCadastroCliente;
import gui.TelaCliente;
import gui.TelaContratos;
import gui.TelaEntrada;
import gui.TelaLogin;
import gui.TelaMain;
import gui.TelaElaborarNovoContrato;
import gui.TelaPadrao;
import gui.TelaPlanilhaNFe;
import gui.TelaPlanilhaNFeInternas;
import gui.TelaPrincipal;
import manipular.ArquivoConfiguracoes;
import manipular.ConfiguracoesGlobais;
import manipular.EditarWord;
import manipular.Email;
import manipular.ManipularTxt;
import manipular.MonitorarRomaneios;
import manipular.Nuvem;
import manipular.Whatsapp;
import manipular.ZapMessenger;
import outros.DadosGlobais;
import outros.GetDadosTransportador;
import outros.GetData;
import outros.ReproduzirAudio;
import tratamento_proprio.Log;
import views_personalizadas.TelaEmEspera;
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

 



