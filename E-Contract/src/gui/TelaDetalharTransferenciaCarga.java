package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
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
import java.awt.Window;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import cadastros.CadastroCliente;
import cadastros.CadastroContrato;
import conexaoBanco.GerenciarBancoContratos;
import conexaoBanco.GerenciarBancoTransferenciasCarga;

import javax.swing.border.LineBorder;
import javax.swing.JScrollPane;



public class TelaDetalharTransferenciaCarga extends JDialog {

	private final JPanel painelPrincipal = new JPanel();
    private JLabel lblTotalContratosConcluidos, lblTotalContratos, lblTotalContratosAbertos;
    private TelaDetalharTransferenciaCarga isto;
    private JDialog telaPai;

	public TelaDetalharTransferenciaCarga(int id_transferencia, String descricao, Window janela_pai) {

		 isto = this;
		
		setResizable(true);
		setTitle("E-Contract - Detalhes da transferencia");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 834, 338);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(new MigLayout("", "[grow]", "[][grow]"));
		
	
		
		JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		JScrollPane scrollPane = new JScrollPane(textArea);
		painelPrincipal.add(scrollPane, "cell 0 1,grow");
		
		String texto_detalhado = "";
		
		GerenciarBancoTransferenciasCarga gerenciar = new GerenciarBancoTransferenciasCarga();
			CadastroContrato.CadastroTransferenciaCarga transferencia = gerenciar.getTransferencia(id_transferencia);
			
	    GerenciarBancoContratos gerencia_contratos = new GerenciarBancoContratos();
	    CadastroContrato remetente = gerencia_contratos.getContrato(transferencia.getId_contrato_remetente());
	    CadastroContrato destinatario = gerencia_contratos.getContrato(transferencia.getId_contrato_destinatario());
	    CadastroContrato.Carregamento carga = gerencia_contratos.getCarregamento(transferencia.getId_carregamento_remetente());

		CadastroCliente compradores[] = destinatario.getCompradores();
		CadastroCliente vendedores[] = destinatario.getVendedores();
		CadastroCliente corretores[] = destinatario.getCorretores();

		String nome_corretores = "";
		String nome_vendedores = "";
		String nome_compradores = "";

		if (corretores[0] != null) {
			if (corretores[0].getTipo_pessoa() == 0) {
				// pessoa fisica
				nome_corretores = corretores[0].getNome_empresarial();
			} else {
				nome_corretores = corretores[0].getNome_fantaia();

			}
		}

		if (compradores[0] != null) {
			if (compradores[0].getTipo_pessoa() == 0) {
				// pessoa fisica
				nome_compradores = compradores[0].getNome_empresarial();
			} else {
				nome_compradores = compradores[0].getNome_fantaia();

			}
		}

		if (compradores[1] != null) {
			if (compradores[1].getTipo_pessoa() == 0) {
				// pessoa fisica
				nome_compradores = nome_compradores + ", " + compradores[1].getNome_empresarial();
			} else {
				nome_compradores = nome_compradores + ", " + compradores[1].getNome_fantaia();

			}
		}


		for (CadastroCliente vendedor : vendedores) {
			if (vendedor != null) {
				if (vendedor.getTipo_pessoa() == 0) {
					// pessoa fisica
					nome_vendedores += vendedor.getNome_empresarial();
				} else {
					nome_vendedores += vendedor.getNome_fantaia();

				}
				nome_vendedores += " ,";

			}
		}
		
	    double quantidade = Double.parseDouble(transferencia.getQuantidade());
	    
		NumberFormat z = NumberFormat.getNumberInstance();

		if(descricao.equalsIgnoreCase("-Transferencia")) {
			texto_detalhado = "Transferência de volume deste contrato para o contrato :\n\n"
					+ destinatario.getCodigo() + "\n"
					+ nome_compradores + " X " + nome_vendedores + "\n"
					+ z.format(destinatario.getQuantidade()) + " " + destinatario.getMedida() + " de " + destinatario.getModelo_safra().getProduto().getNome_produto() +
					" " + destinatario.getModelo_safra().getProduto().getTransgenia() + " da safra " + destinatario.getModelo_safra().getAno_plantio() + "/" + destinatario.getModelo_safra().getAno_colheita() + "\n\n";
			texto_detalhado  = texto_detalhado + "";
			          

				
		}
		
		textArea.setText(texto_detalhado);

		this.setLocationRelativeTo(janela_pai);

		
		
		
	}
	
	public void setTelaPai(JDialog _tela_pai) {
		this.telaPai = _tela_pai;
	}
	
	
}
