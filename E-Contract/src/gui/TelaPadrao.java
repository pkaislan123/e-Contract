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
				
			
				
				JPanel panel = new JPanel();
				panel.setBorder(new LineBorder(new Color(0, 0, 0)));
				panel.setBackground(new Color(255, 255, 224));
				panel.setBounds(0, 36, 349, 271);
				painelPrincipal.add(panel);
				panel.setLayout(new MigLayout("", "[][][]", "[][][][][][][][][][][][][][]"));
				
				JLabel lblInformaesDoContrato = new JLabel("Informações do Contrato Original");
				lblInformaesDoContrato.setFont(new Font("Dialog", Font.BOLD, 10));
				panel.add(lblInformaesDoContrato, "cell 1 0,alignx left");
				
				JLabel lblNewLabel_2_1_1_2_1_2_1_1 = new JLabel("Data Contrato:");
				lblNewLabel_2_1_1_2_1_2_1_1.setFont(new Font("Dialog", Font.PLAIN, 12));
				panel.add(lblNewLabel_2_1_1_2_1_2_1_1, "cell 0 1,alignx left");
				
				JLabel lblInfoDataContrato = new JLabel("01/01/01");
				lblInfoDataContrato.setFont(new Font("Dialog", Font.PLAIN, 12));
				panel.add(lblInfoDataContrato, "cell 1 1,alignx left");
				
				JLabel lblNewLabel_2 = new JLabel("Comprador:");
				lblNewLabel_2.setFont(new Font("Dialog", Font.PLAIN, 12));
				panel.add(lblNewLabel_2, "cell 0 2,alignx left");
				
				JLabel lblInfoComprador = new JLabel("Marcos Alexandre Andrade de Carvalho e Outros");
				lblInfoComprador.setFont(new Font("Dialog", Font.PLAIN, 10));
				panel.add(lblInfoComprador, "cell 1 2,alignx left");
				
				JLabel lblNewLabel_2_1 = new JLabel("Vendedor:");
				lblNewLabel_2_1.setFont(new Font("Dialog", Font.PLAIN, 12));
				panel.add(lblNewLabel_2_1, "cell 0 3,alignx left");
				
				JLabel lblInfoVendedor1 = new JLabel("Vendedor1");
				lblInfoVendedor1.setFont(new Font("Dialog", Font.PLAIN, 10));
				panel.add(lblInfoVendedor1, "cell 1 3,alignx left");
				
				JLabel lblNewLabel_2_1_1 = new JLabel("Vendedor:");
				lblNewLabel_2_1_1.setFont(new Font("Dialog", Font.PLAIN, 12));
				panel.add(lblNewLabel_2_1_1, "cell 0 4,alignx left");
				
				JLabel lblInfoVendedor2 = new JLabel("Vendedor2");
				lblInfoVendedor2.setFont(new Font("Dialog", Font.PLAIN, 10));
				panel.add(lblInfoVendedor2, "cell 1 4,alignx left");
				
				JLabel lblNewLabel_2_1_1_1 = new JLabel("Safra:");
				lblNewLabel_2_1_1_1.setFont(new Font("Dialog", Font.PLAIN, 12));
				panel.add(lblNewLabel_2_1_1_1, "cell 0 5,alignx left");
				
				JLabel lblInfoSafra = new JLabel("<dynamic> 0/0");
				lblInfoSafra.setFont(new Font("Dialog", Font.PLAIN, 12));
				panel.add(lblInfoSafra, "cell 1 5,alignx left");
				
				JLabel lblNewLabel_2_1_1_2 = new JLabel("Medida:");
				lblNewLabel_2_1_1_2.setFont(new Font("Dialog", Font.PLAIN, 12));
				panel.add(lblNewLabel_2_1_1_2, "cell 0 6,alignx left");
				
				JLabel lblInfoMedida = new JLabel("Sacos");
				lblInfoMedida.setFont(new Font("Dialog", Font.PLAIN, 12));
				panel.add(lblInfoMedida, "cell 1 6,alignx left");
				
				JLabel lblNewLabel_2_1_1_2_1 = new JLabel("Quantidade:");
				lblNewLabel_2_1_1_2_1.setFont(new Font("Dialog", Font.PLAIN, 12));
				panel.add(lblNewLabel_2_1_1_2_1, "cell 0 7,alignx left");
				
				JLabel lblInfoQuantidade = new JLabel("0.0 <dynamic>");
				lblInfoQuantidade.setFont(new Font("Dialog", Font.PLAIN, 12));
				panel.add(lblInfoQuantidade, "cell 1 7,alignx left");
				
				JLabel lblNewLabel_2_1_1_2_1_1 = new JLabel("Valor por Medida:");
				lblNewLabel_2_1_1_2_1_1.setFont(new Font("Dialog", Font.PLAIN, 12));
				panel.add(lblNewLabel_2_1_1_2_1_1, "cell 0 8,alignx left");
				
				JLabel lblInfoValorMedida = new JLabel("R$ 0,00");
				lblInfoValorMedida.setFont(new Font("Dialog", Font.PLAIN, 12));
				panel.add(lblInfoValorMedida, "cell 1 8,alignx left");
				
				JLabel lblNewLabel_2_1_1_2_1_2 = new JLabel("Valor Total:");
				lblNewLabel_2_1_1_2_1_2.setFont(new Font("Dialog", Font.PLAIN, 12));
				panel.add(lblNewLabel_2_1_1_2_1_2, "cell 0 9,alignx left");
				
				JLabel lblInfoValorTotal = new JLabel("R$ 0,00");
				lblInfoValorTotal.setFont(new Font("Dialog", Font.PLAIN, 12));
				panel.add(lblInfoValorTotal, "cell 1 9,alignx left");
				
				JLabel lblNewLabel_2_1_1_2_1_2_1 = new JLabel("Data Entrega:");
				lblNewLabel_2_1_1_2_1_2_1.setFont(new Font("Dialog", Font.PLAIN, 12));
				panel.add(lblNewLabel_2_1_1_2_1_2_1, "cell 0 10,alignx left");
				
				JLabel lblInfoDataEntrega = new JLabel("R$ 0,00");
				lblInfoDataEntrega.setFont(new Font("Dialog", Font.PLAIN, 12));
				panel.add(lblInfoDataEntrega, "cell 1 10,alignx left");
				
				JLabel lblNewComissao = new JLabel("Comissão:");
				lblNewComissao.setFont(new Font("Dialog", Font.PLAIN, 12));
				panel.add(lblNewComissao, "cell 0 11,alignx left");
				
				JLabel lblInfoComissao = new JLabel("Sim");
				lblInfoComissao.setFont(new Font("Dialog", Font.PLAIN, 12));
				panel.add(lblInfoComissao, "cell 1 11,alignx left");
				
				JLabel lblValorComisso1 = new JLabel("Valor Comissão:");
				lblValorComisso1.setFont(new Font("Dialog", Font.PLAIN, 12));
				panel.add(lblValorComisso1, "cell 0 12,alignx left");
				
				JLabel lblValorComissao = new JLabel("R$ 0,00");
				lblValorComissao.setFont(new Font("Dialog", Font.PLAIN, 12));
				lblValorComissao.setToolTipText("");
				panel.add(lblValorComissao, "cell 1 12,alignx left");
			

			
		
		
		

		this.setLocationRelativeTo(null);

		this.setVisible(true);
		
		
		
	}
}
