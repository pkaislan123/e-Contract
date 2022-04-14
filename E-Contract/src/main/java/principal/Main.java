
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

import com.dropbox.core.DbxException;

import main.java.cadastros.CadastroFuncionariosHorarios;
import main.java.cadastros.CadastroNuvem;
import main.java.gui.TelaEntrada;
import main.java.gui.TelaFinanceiro;
import main.java.manipular.ArquivoConfiguracoes;
import main.java.manipular.Email2;
import main.java.manipular.MonitorarRomaneios;
import main.java.manipular.Nuvem;
import main.java.manipular.NuvemV2;
import main.java.manipular.Whatsapp;

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
		
		Whatsapp zap = new Whatsapp("t");
		zap.setChave("9p4zinrpdfhlahloi4irsgowfzab0v");
		zap.setSenha("chatpro-1vcv2pfwqj");
		zap.enviarArquivo("teste79", "38999416698", "https://media-cdn.tripadvisor.com/media/photo-s/15/a4/9b/77/legacy-hotel-at-img-academy.jpg");
		//zap.enviarMensagem("5538999416698", "teste78");
		
		CadastroNuvem cdnuvem = new CadastroNuvem();
		cdnuvem.setToken("sl.BFl51752TViimcrhaoG509AxTrwTAeic_V8dOB0vrqQUHa5Ng8jJOGWj86DtTT4yJHgnjOBTPW66KULAGRkkGRax6Xg1NTuF2v-QhDEEGdhNhy4LyLEZy3tZuKfUtBrJ1T-i8KV1dmpa");
		cdnuvem.setApp_key("emfrs8dnamy2akw");
		cdnuvem.setApp_secret("emk2292wuo58xfa");
		
		Nuvem nuvem = new Nuvem(cdnuvem);
		nuvem.abrir();
		nuvem.listar();
		nuvem.testar();
*/
		
		
		/*try {
			NuvemV2 nuvem = new NuvemV2();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DbxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		CadastroNuvem cdnuvem = new CadastroNuvem();
		cdnuvem.setToken("sl.BFrsPQIPdre8yl0MafHBI6Ua4yN8ALLpmIcOQ-wI8APyNttFJxzfUVxO7AC9nwC7ZLh-TwTTiDeoUd0cm2KPy_8_1zap0XzS3EomFOFm8Eb7VO1o2Lag84-YW90_nqCIYnKtZXYNCeh3");
		cdnuvem.setApp_key("emfrs8dnamy2akw");
		cdnuvem.setApp_secret("emk2292wuo58xfa");
		cdnuvem.setRefresh_token("FP6XTszHPDgAAAAAAAAAAf8fSQADhTAKbS0KS31mcA8HBR5e-PowwVwMscCxucNv");
		Nuvem nuvem = new Nuvem(cdnuvem);
		nuvem.abrir();
		nuvem.listar();
		nuvem.testar();
		*/
	}

}
