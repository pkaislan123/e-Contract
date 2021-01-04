package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.border.LineBorder;



public class TelaPadrao extends JDialog {

	private final JPanel painelPrincipal = new JPanel();


	public TelaPadrao() {
		setModal(true);

		TelaPadrao isto = this;
		
		setResizable(true);
		setTitle("E-Contract - Tela Padrao");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 430, 508);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);
		
		JPanel painel_msg = new JPanel();
		painel_msg.setBackground(Color.WHITE);
		painel_msg.setBounds(10, 0, 414, 80);
		painelPrincipal.add(painel_msg);
		painel_msg.setLayout(new MigLayout("", "[][][][][][][][][][][][][][grow]", "[grow]"));
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBackground(Color.WHITE);
		painel_msg.add(panel, "flowx,cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[grow][][][][][][][]", "[grow][]"));
		
		JTextArea mostrar_conteudo = new JTextArea();
		mostrar_conteudo.setWrapStyleWord(true);
		panel.add(mostrar_conteudo, "cell 0 0 8 1,grow");
		
		JLabel mostrar_data_hora = new JLabel("00/00/00 as 10:00");
		mostrar_data_hora.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel.add(mostrar_data_hora, "cell 7 1");
		
		JLabel lblNewLabel = new JLabel("teste de espaçamento");
		lblNewLabel.setVisible(false);
		painel_msg.add(lblNewLabel, "cell 2 0 11 1");
		
		
		

		this.setLocationRelativeTo(null);

		this.setVisible(true);
		
		
		
	}

}
