package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Locale;

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
import java.awt.SystemColor;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import cadastros.CadastroCliente;
import cadastros.CadastroContrato;

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
		setBounds(100, 100, 695, 508);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);
		
		
			
			CadastroContrato contrato_pai_local = null;
			
		
				CadastroCliente compradores[] = contrato_pai_local.getCompradores();
				CadastroCliente vendedores[] = contrato_pai_local.getVendedores();
				
			
				String info_safra = contrato_pai_local.getModelo_safra().getProduto().getNome_produto() + " "
						+ contrato_pai_local.getModelo_safra().getAno_plantio() + "/"
						+ contrato_pai_local.getModelo_safra().getAno_colheita();
				Locale ptBr = new Locale("pt", "BR");
				String valorString = NumberFormat.getCurrencyInstance(ptBr).format(contrato_pai_local.getValor_produto());
				valorString = NumberFormat.getCurrencyInstance(ptBr)
						.format(Double.parseDouble(contrato_pai_local.getValor_a_pagar().toPlainString()));
			

			
		
		
		

		this.setLocationRelativeTo(null);

		this.setVisible(true);
		
		
		
	}
}
