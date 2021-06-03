package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JFileChooser;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.Window;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import main.java.cadastros.CadastroContrato;
import main.java.manipular.ManipularTxt;

import javax.swing.border.LineBorder;



public class TelaSalvarEnviarRelatorio extends JDialog {

	private final JPanel painelPrincipal = new JPanel();
	private TelaSalvarEnviarRelatorio isto;
    private Window telaPai;
    private String caminho;
    
	public TelaSalvarEnviarRelatorio(int flag,CadastroContrato contrato, String file) {
		setModal(true);
		caminho = file;

		isto = this;
		
		setResizable(true);
		setTitle("E-Contract - Escolha uma opção");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 448, 123);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		
		JButton btnNewButton = new JButton("Salvar");
		btnNewButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(new Color(0, 0, 51));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					
				JFileChooser fc = new JFileChooser(new File(file));
				    
				    // Mostra a dialog de save file
				    fc.showSaveDialog(isto);
				    File  selFile = fc.getSelectedFile();
				    //selFile
				    
				    ManipularTxt manipular = new ManipularTxt();
				    boolean salvar = manipular.moverArquivo(file, selFile.toString() + ".pdf");
				    
				    if(salvar) {
				    	JOptionPane.showMessageDialog(null, "Arquivo Salvo na pasta selecioada");
				    }else {
				    	JOptionPane.showMessageDialog(null, "Erro ao salvar o arquivo\nConsulte o administrador");

				    }
				    
				    ((TelaVizualizarPdf) telaPai).fecharTela();
				    isto.dispose();
				    
			}
		});
		painelPrincipal.setLayout(new MigLayout("", "[111px][118px][140px]", "[grow]"));
		painelPrincipal.add(btnNewButton, "cell 0 0,growx,aligny center");
		
		JButton btnNewButton_1 = new JButton("Enviar via E-mail");
		btnNewButton_1.setBackground(new Color(0, 51, 0));
		btnNewButton_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				TelaEnviarMsgMail tela = new TelaEnviarMsgMail(flag, contrato,new File(file), null);
				tela.setVisible(true);
				isto.dispose();
			
			}
		});
		painelPrincipal.add(btnNewButton_1, "cell 1 0,alignx center,aligny center");
		
		JButton btnNewButton_1_1 = new JButton("Enviar Via Whatsapp");
		btnNewButton_1_1.setBackground(new Color(102, 0, 0));
		btnNewButton_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		btnNewButton_1_1.setForeground(Color.WHITE);
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaEnviarMsgWhatsappDocs enviar = new TelaEnviarMsgWhatsappDocs(contrato,new File(file), null);
                isto.dispose();
				
			}
		});
		painelPrincipal.add(btnNewButton_1_1, "cell 2 0,growx,aligny center");
		
		
			


			
		
		
		

		this.setLocationRelativeTo(null);

		
		
		
	}
	
	
	public void setTelaPai(Window tela_pai) {
		this.telaPai = tela_pai;
		
	}
	
}
