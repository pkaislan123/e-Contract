
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

import main.java.cadastros.CadastroFuncionariosHorarios;
import main.java.cadastros.CadastroNuvem;
import main.java.gui.TelaEntrada;
import main.java.gui.TelaFinanceiro;
import main.java.manipular.ArquivoConfiguracoes;
import main.java.manipular.Email2;
import main.java.manipular.MonitorarRomaneios;
import main.java.manipular.Nuvem;

import java.net.URL;
import javax.swing.*;
import javax.sound.sampled.*;

public class Main {

	public static void main(String[] args) {

		try {

			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (UnsupportedLookAndFeelException exc) {
			exc.printStackTrace();
		} catch (ClassNotFoundException exc) {
			exc.printStackTrace();
		} catch (InstantiationException exc) {
			exc.printStackTrace();
		} catch (IllegalAccessException exc) {
			exc.printStackTrace();
		}

		new Thread() {
			@Override
			public void run() {

				TelaEntrada entrada = null;

				try {
					entrada = new TelaEntrada(args[0]);

				} catch (ArrayIndexOutOfBoundsException f) {
					entrada = new TelaEntrada("");

				}

				entrada.setVisible(true);

				entrada.realizarTeste();
			
				
				

			}
		}.start();

		/*
		CadastroNuvem cdnuvem = new CadastroNuvem();
		cdnuvem.setToken("sl.BCNQOmujnFC_o18dHjiWEwl5m6-ghA7VJbQ1uLtTqSGvBVxHWTNV8BvpFSjw6BKr7I94Lb9g4elUbLHAJdvPK56ylZIVrlwV1MDwkStDhYRM9VwKvQh1L8TM7TGOx9ussytQaX4MrSuD");
		cdnuvem.setApp_key("44it55pqgew0s29");
		cdnuvem.setApp_secret("uthy5srxkrj6ev9");
		
		Nuvem nuvem = new Nuvem(cdnuvem);
		nuvem.abrir();
		nuvem.testar();*/
		
		
	}

}
