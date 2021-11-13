package main.java.classesExtras;



import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import keeptoo.KGradientPanel;
import main.java.cadastros.CadastroAnotacaoGeral;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.DreSimples;
import main.java.cadastros.FinanceiroPagamentoCompleto;
import net.miginfocom.swing.MigLayout;

public class RedenrizadorAnotacoes implements ListCellRenderer<CadastroAnotacaoGeral> {

	@Override
	public Component getListCellRendererComponent(JList<? extends CadastroAnotacaoGeral> arg0, CadastroAnotacaoGeral anotacao, int arg2,
			boolean isSelected, boolean cellHasFocus) {

		
		
		JPanel painelPrincipal = new JPanel();
		painelPrincipal.setBorder(new MatteBorder(3, 3, 3, 3, (Color) new Color(0, 0, 0)));
		painelPrincipal.setBackground(new Color(0, 51, 0));
		painelPrincipal.setLayout(new MigLayout("", "[][grow]", "[][][grow]"));
		
		JLabel asdasd = new JLabel("ID:");
		asdasd.setForeground(Color.WHITE);
		asdasd.setFont(new Font("SansSerif", Font.PLAIN, 16));
		asdasd.setBackground(Color.WHITE);
		painelPrincipal.add(asdasd, "cell 0 0,alignx right");
		
		JLabel lblId = new JLabel("");
		lblId.setForeground(Color.WHITE);
		lblId.setFont(new Font("SansSerif", Font.BOLD, 18));
		painelPrincipal.add(lblId, "cell 1 0,alignx left");
		
		JTextArea textAreaTexto = new JTextArea();
		textAreaTexto.setBackground(Color.WHITE);
		textAreaTexto.setFont(new Font("SansSerif", Font.PLAIN, 18));
		textAreaTexto.setWrapStyleWord(true);
		textAreaTexto.setLineWrap(true);
		painelPrincipal.add(textAreaTexto, "cell 0 1 2 2,grow");
		
	
		textAreaTexto.setText(anotacao.getTexto());
		lblId.setText(anotacao.getId_anotacao() + "");
		
		if(isSelected) {
			painelPrincipal.setBorder(new MatteBorder(3, 3, 3, 3, (Color) new Color(255, 255, 0)));
		}else {
			painelPrincipal.setBorder(new MatteBorder(3, 3, 3, 3, (Color) new Color(0, 0, 0)));
		}
	

		return painelPrincipal;
	}
}