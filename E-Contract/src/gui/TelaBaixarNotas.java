package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.awt.event.ActionEvent;

public class TelaBaixarNotas extends JDialog {

	private final JPanel painelPrincipal = new JPanel();

	
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
				System.setProperty("webdriver.gecko.driver", "C:\\geckodriver.exe");
				WebDriver driver = new FirefoxDriver();

			
		       driver.get("https://www2.fazenda.mg.gov.br/sol/");
				
				
		        String url_ante_click = driver.getCurrentUrl();
		        System.out.println("url pos click: " + url_ante_click);
		        
		        WebElement comboboxElement = driver.findElement(By.name("cmbDominio"));
		        Select combobox = new Select(comboboxElement);
		        combobox.selectByVisibleText("Produtor Rural");


		        WebElement id_usuario = driver.findElement(By.name("dominio"));

		        WebElement cpf = driver.findElement(By.name("login"));
		        WebElement senha = driver.findElement(By.name("senhaAtual"));
		       
		        id_usuario.sendKeys("0026877020090");
		        cpf.sendKeys("54271177849");
		        senha.sendKeys("65857994");

		        WebElement botaoEntrar = driver.findElement(By.name("Entrar"));
		        botaoEntrar.click();
		        
		        String url_pos_click = driver.getCurrentUrl();
		        System.out.println("url pos click: " + url_pos_click);

		        WebElement botaoConsulta = fluentWait(By.linkText("Consulta"), driver);
		        botaoConsulta.click();
		        
		        WebElement menuConsultaNFA = driver.findElement(By.linkText("Nota Fiscal Avulsa"));
		        menuConsultaNFA.click();
		        
		        WebElement botaoConsultaNFA = fluentWait(By.linkText("Consultar NFA por Parâmetros"), driver);
		        botaoConsultaNFA.click();
		      
		       
                  //configuracoes de pesquisa
		        WebElement comboboxTipoDoc = driver.findElement(By.name("cmbTipoDocumento"));
		        Select comboboxTipoDc = new Select(comboboxTipoDoc);
		        comboboxTipoDc.selectByVisibleText("Todos");
		        
		        WebElement botaoTipoNFA = driver.findElement(By.xpath("//input[@value='3']"));
		        botaoTipoNFA.click();
		        
		        WebElement dataInicio = driver.findElement(By.name("dataInicio"));
		        dataInicio.sendKeys("01/01/2020");

		        WebElement dataFim = driver.findElement(By.name("dataFim"));
		        dataFim.sendKeys("26/06/2020");
		        

		        WebElement botaoPesquisar = driver.findElement(By.name("Pesquisar"));
		        botaoPesquisar.click();
		        
		        */
		        
		        
		       // driver.get("https://www2.fazenda.mg.gov.br/sol/ctrl/SOL/NFAE/LISTA_013?ACAO=VISUALIZAR");
		        
			}
		});
		btnBaixarNotas.setBounds(177, 75, 231, 122);
		painelPrincipal.add(btnBaixarNotas);
		
	
        
		this.setLocationRelativeTo(null);

		this.setVisible(true);
	
	}
	
	/*
	public WebElement fluentWait(final By locator, WebDriver driver ) {
	    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
	            .withTimeout(30, TimeUnit.SECONDS)
	            .pollingEvery(5, TimeUnit.SECONDS)
	            .ignoring(NoSuchElementException.class);

	    WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
	        public WebElement apply(WebDriver driver) {
	            return driver.findElement(locator);
	        }
	    });

	    return  foo;
	};
	*/
}
