package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.icepdf.ri.util.PropertiesManager;

import views_personalizadas.TelaEmEspera;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;



public class TelaVizualizarPdf extends JDialog {


	private TelaEmEspera telaInformacoes;

	//public TelaVizualizarPdf(String arquivo) {
	public TelaVizualizarPdf(InputStream stream, TelaElaborarNovoContrato pai, TelaEmEspera telaBack, String file) {
		setModal(true);

		TelaVizualizarPdf isto = this;
		this.telaInformacoes = telaBack;
		
		setResizable(true);
		setTitle("Pré-Vizualização");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 679, 508);
		
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
		 propriedades.setBoolean (PropertiesManager.PROPERTY_SHOW_TOOLBAR_FIT,
		         Boolean.FALSE);
		 
		 propriedades.setFloat(PropertiesManager.PROPERTY_DEFAULT_ZOOM_LEVEL, 2.0f );

		 SwingViewBuilder factory = new SwingViewBuilder(controller, propriedades);
		// Use the factory to build a JPanel that is pre-configured
		//with a complete, active Viewer UI.
		/*controller.getDocumentViewController().setAnnotationCallback(
			     new org.icepdf.ri.common.MyAnnotationCallback(
			            controller.getDocumentViewController()));


		*/
		JPanel viewerComponentPanel = factory.buildViewerPanel();

		// add copy keyboard command
		//ComponentKeyBinding.install(controller, viewerComponentPanel);

		// add interactive mouse link annotation support via callback
		
		//controller.openDocument(arquivo);
		if(stream != null)
		controller.openDocument(stream , "", "");
		else
			controller.openDocument(file);
		
		setContentPane(viewerComponentPanel);
		
			addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					if (JOptionPane.showConfirmDialog(isto, 
				            "Salvar", "Deseja Salvar o contrato?", 
				            JOptionPane.YES_NO_OPTION,
				            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
						      pai.salvarArquivo();
								telaInformacoes.setMsg("Contrato Salvo");
								telaInformacoes.fechar();

				        }
					else
					{
						isto.dispose();
						telaInformacoes.fechar();
						
					}
				}
			});
		
		
		
		this.setLocationRelativeTo(null);

		this.setUndecorated (false);
		this.setBounds (GraphicsEnvironment.getLocalGraphicsEnvironment (). getMaximumWindowBounds ());
		
		this.setVisible(true);
		

		
	}

}
