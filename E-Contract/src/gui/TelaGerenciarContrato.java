package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.icepdf.ri.util.PropertiesManager;

import cadastros.CadastroContrato;
import manipular.ConverterPdf;
import views_personalizadas.TelaEscolha;

import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaGerenciarContrato extends JDialog {

	private JTabbedPane painelPrincipal = new JTabbedPane();
	private JPanel painelDadosIniciais = new JPanel();
	private JPanel painelPagamentos = new JPanel();
	private JPanel painelCarregamento = new JPanel();
	private final JLabel lblStatusContrato = new JLabel("Status do Contrato:");

	InputStream stream = null;
	private final JButton btnNewButton = new JButton("Editar");
	private JPanel painel_vizualizar;
	private final JButton btnEnviarMsg = new JButton("Enviar");

	 
	public TelaGerenciarContrato(CadastroContrato contrato) {
		setModal(true);

		TelaGerenciarContrato isto = this;
		
		setResizable(false);
		
		System.out.println("Caminho do arquivo: " + contrato.getCaminho_arquivo());
		try {
			stream = new FileInputStream(contrato.getCaminho_arquivo());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("E-Contract - Gerenciar Contrato");
		setBounds(100, 100, 1083, 626);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		painelPrincipal = new JTabbedPane();
		//contentPanel.setBackground(new Color(255, 255, 255));
		//contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		//setContentPane(contentPanel);
		//contentPanel.setLayout(null);
		
		painelDadosIniciais.setBackground(new Color(255, 255, 255));
		painelPagamentos.setBackground(new Color(255, 255, 255));
		painelCarregamento.setBackground(new Color(255, 255, 255));


		//adiciona novos paines e suas abas
		painelPrincipal.addTab("Contrato", painelDadosIniciais);
		painelDadosIniciais.setLayout(null);
		lblStatusContrato.setBounds(554, 76, 300, 35);
		
		painelDadosIniciais.add(lblStatusContrato);
		
		btnNewButton.setBounds(429, 497, 89, 23);
		
		painelDadosIniciais.add(btnNewButton);
		
		int status = contrato.getStatus_contrato();
		if(status== 1) {
			lblStatusContrato.setText("Status do Contrato: " + "Recolher Assinaturas");

		}
		else if(status == 2) {
			lblStatusContrato.setText("Status do Contrato: " + "Assinado");

		}
		else if(status == 3) {
			lblStatusContrato.setText("Status do Contrato: " + "Cumprindo");

		}
		
		
		painelPrincipal.addTab("Pagamentos", painelPagamentos);
		painelPagamentos.setLayout(null);
		
		painelPrincipal.addTab("Carregamento", painelCarregamento);
		painelCarregamento.setLayout(null);
		
		getContentPane().add(painelPrincipal, BorderLayout.CENTER);

		
				// build a controller
				SwingController controller = new SwingController();

				 PropertiesManager propriedades =  new PropertiesManager (System.getProperties (),ResourceBundle.getBundle (PropertiesManager.DEFAULT_MESSAGE_BUNDLE));
				// Build a SwingViewFactory configured with the controller
				
				 propriedades.setBoolean (PropertiesManager.PROPERTY_SHOW_TOOLBAR_ANNOTATION,
				         Boolean.FALSE);
				 propriedades.setBoolean (PropertiesManager.PROPERTY_VIEWPREF_HIDEMENUBAR,
				         Boolean.TRUE);
				 propriedades.setBoolean (PropertiesManager.PROPERTY_VIEWPREF_HIDETOOLBAR,
				         Boolean.TRUE);
				 propriedades.setBoolean (PropertiesManager.PROPERTY_SHOW_TOOLBAR_ANNOTATION,
				         Boolean.FALSE);
				 propriedades.setBoolean (PropertiesManager.PROPERTY_SHOW_TOOLBAR_PAGENAV,
				         Boolean.FALSE);
				 propriedades.setBoolean (PropertiesManager.PROPERTY_SHOW_STATUSBAR,
				         Boolean.FALSE);
				 propriedades.setBoolean (PropertiesManager.PROPERTY_SHOW_TOOLBAR_FIT,
				         Boolean.FALSE);
				 
				 propriedades.setFloat(PropertiesManager.PROPERTY_DEFAULT_ZOOM_LEVEL, 0.85f );

				 SwingViewBuilder factory = new SwingViewBuilder(controller, propriedades);
				// Use the factory to build a JPanel that is pre-configured
				//with a complete, active Viewer UI.
				controller.getDocumentViewController().setAnnotationCallback(
					     new org.icepdf.ri.common.MyAnnotationCallback(
					            controller.getDocumentViewController()));


				painel_vizualizar = new JPanel();
			
			    painel_vizualizar = factory.buildViewerPanel();
				//JPanel viewerComponentPanel = factory.buildViewerPanel();

				// add copy keyboard command
				//ComponentKeyBinding.install(controller, viewerComponentPanel);

				// add interactive mouse link annotation support via callback
				
				//controller.openDocument(arquivo);
				

				
				controller.openDocument(contrato.getCaminho_arquivo());
				//viewerComponentPanel.setPreferredSize(new Dimension(400, 370));
				//viewerComponentPanel.setMaximumSize(new Dimension(400, 370));
				painel_vizualizar.setBounds(10, 25, 508, 461);							
				
				painelDadosIniciais.add(painel_vizualizar);
				btnEnviarMsg.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						TelaEscolha escolher = new TelaEscolha(contrato);
					}
				});
				
				btnEnviarMsg.setBounds(330, 497, 89, 23);
				
				painelDadosIniciais.add(btnEnviarMsg);
				
		
		
		
	
				
    			

		
		this.setLocationRelativeTo(null);

		this.setVisible(true);
		
		
	}

	 
}
