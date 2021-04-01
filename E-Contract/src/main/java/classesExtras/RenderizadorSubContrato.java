package main.java.classesExtras;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

public class RenderizadorSubContrato implements ListCellRenderer<CadastroContrato> {

	@Override
	public Component getListCellRendererComponent(JList<? extends CadastroContrato> list, CadastroContrato contrato,
			int index, boolean isSelected, boolean cellHasFocus) {

		NumberFormat z = NumberFormat.getNumberInstance();
		Locale ptBr = new Locale("pt", "BR");

		

		JLabel tPContratoOriginal = new JLabel();
		tPContratoOriginal.setFont(new Font("Arial", Font.PLAIN, 14));
		tPContratoOriginal.setBorder(new MatteBorder(0, 0, 3, 0, (Color) new Color(0, 0, 102)));

		 Border border = tPContratoOriginal.getBorder();
		 Border margin = new EmptyBorder(10,10,10,10);
		 tPContratoOriginal.setBorder(new CompoundBorder(border, margin));
		 tPContratoOriginal.setBounds(20, 49, 1131, 65);
		
		//nome comprador
		String nome_comprador = "";
		String nome_vendedor1 = "";
		String nome_vendedor2  = "";
		
		CadastroCliente compradores[] = contrato.getCompradores();
		CadastroCliente vendedores[] = contrato.getVendedores();

		
		
		if(compradores[0].getTipo_pessoa() == 0)
		    nome_comprador = compradores[0].getNome_empresarial();
		else
			nome_comprador = compradores[0].getNome_fantaia();
		
		if(vendedores[0].getTipo_pessoa() == 0)
			nome_vendedor1 = vendedores[0].getNome_empresarial();
		else
			nome_vendedor1 = vendedores[0].getNome_fantaia();
		
		if(vendedores[1] != null) {
			if(vendedores[1].getTipo_pessoa() == 0)
				nome_vendedor2 = vendedores[1].getNome_empresarial();
			else
				nome_vendedor2 = vendedores[1].getNome_fantaia();
		}
		

		double quantidade_sacos = 0;
		double quantidade_kg = 0;
		if(contrato.getMedida().equalsIgnoreCase("Sacos")) {
			quantidade_sacos = contrato.getQuantidade();
			quantidade_kg = quantidade_sacos * 60;
		}else if(contrato.getMedida().equalsIgnoreCase("KG")) {
			quantidade_kg = contrato.getQuantidade();
			quantidade_sacos = quantidade_kg / 60;
		}
		  
			String quantidade = z.format(quantidade_sacos);
			
		String valor_por_saco = 	NumberFormat.getCurrencyInstance(ptBr).format(contrato.getValor_produto());
         String valor_a_pagar = NumberFormat.getCurrencyInstance(ptBr).format(contrato.getValor_a_pagar());
		
		
		String produto = contrato.getModelo_safra().getProduto().getNome_produto();
		
		String safra = contrato.getModelo_safra().getAno_plantio() + "/" + contrato.getModelo_safra().getAno_plantio();
		
		
		if(vendedores[1] == null) {
		tPContratoOriginal.setText("<html> " + contrato.getCodigo() + " <b>" + quantidade +"</b> sacos de " + produto +" da safra <b>" + safra + "</b> no valor de <b>" + valor_por_saco + "</b> por saca,\r\n   "
				+ "<br> do Vendedor <b>" + nome_vendedor1 + "</b> ao Comprador <b>" + nome_comprador + "</b>\r\n Valor Total: <b>" + valor_a_pagar + "</b></html>");
		}else {
			tPContratoOriginal.setText("<html> " + contrato.getCodigo() + " <b>" + quantidade +"</b> sacos de " + produto +" da safra <b>" + safra + "</b> no valor de <b>" + valor_por_saco + "</b> por saca,\r\n   "
					+ "<br> do Vendedor <b>" + nome_vendedor1 + "</b> com o Vendedor <b>" + nome_vendedor2 + "</b> ao Comprador <b>" + nome_comprador + "</b>\r\n Valor Total: <b>" + valor_a_pagar + "</b></html>");
			
		}
		
		
		return tPContratoOriginal;
	}
	
	
	


}
