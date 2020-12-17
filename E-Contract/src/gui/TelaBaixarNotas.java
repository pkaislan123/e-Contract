package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;

import cadastros.CadastroLogin;
import manipular.ConfiguracoesGlobais;
import manipular.ManipularTxt;
import outros.DadosGlobais;
import outros.GetData;
import outros.TratarDados;
import tratamento_proprio.Log;
import views_personalizadas.TelaNotificacao;

import java.awt.event.ActionListener;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.awt.event.ActionEvent;

public class TelaBaixarNotas extends JDialog {

	private final JPanel painelPrincipal = new JPanel();
	private TelaNotificacao telaNot;
	
	private String num_registros_global;
	private String num_registros_cancelados_global;
	private boolean n_prosseguir = true;
	

	
	public TelaBaixarNotas() {
	
	
		
		setModal(true);

		TelaBaixarNotas isto = this;
		
		setResizable(false);
		setTitle("E-Contract - Notas - Download");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 679, 508);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);
		
		JButton btnBaixarNotas = new JButton("Baixar Notas");
		btnBaixarNotas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			   /*

		    	  PesquisaParalela pesquisar = null;

			    int ano = 2020;
			   
			      for(int mes = 1; mes <= 2; mes++) {	
			    	  for(int dia = 1; dia <= 21; dia += 10) {
			    		 
			    		 
			    		  if(dia == 21) {
			    			  
			    			  if(mes == 2) {
					    		   pesquisar = new PesquisaParalela(dia, mes ,ano, 28, mes , ano);


			    			  }
			    			  else {
			    				  //verifica se o mes e de 30 dias
			    				  //4 meses no ano com 30 dias (abril(4), junho(6), setembro(9) e novembro(11)).
			    				 if(mes == 4 || mes == 6 || mes == 9 || mes == 11) {
						    		   pesquisar = new PesquisaParalela(dia, mes ,ano, 30, mes , ano);

			    				 }else {
						    		   pesquisar = new PesquisaParalela(dia, mes ,ano, 31, mes , ano);

			    				 }
			    				  
			    			  }

			    		  }else {
				    		   pesquisar = new PesquisaParalela(dia, mes ,ano, dia+9, mes , ano);

			    		  }
			    		  Thread rodar = new Thread(pesquisar);
			    		    threads_ociosas.add(rodar);
			    		    contador_global_threads++;
			    		  
			     
			    	  }
		    		
			    	 
			      }
			      GerenciarThreads();
               */
			} 
			
			
		});
		btnBaixarNotas.setBounds(177, 75, 231, 122);
		painelPrincipal.add(btnBaixarNotas);
		
	
        
		this.setLocationRelativeTo(null);

		this.setVisible(true);
	
	}
	
	
}
