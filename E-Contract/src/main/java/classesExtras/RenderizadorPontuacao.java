package main.java.classesExtras;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.LineBorder;

import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroPontuacao;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoContratos;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextArea;

public class RenderizadorPontuacao implements ListCellRenderer<CadastroPontuacao> {
	
	@Override
	public Component getListCellRendererComponent(JList<? extends CadastroPontuacao> list, CadastroPontuacao value,
			int index, boolean isSelected, boolean cellHasFocus) {

		   JPanel painel_msg = new JPanel();
			
			painel_msg.setBackground(Color.WHITE);
			painel_msg.setBounds(0, 0, 800, 72);
			painel_msg.setLayout(new MigLayout("", "[][][][][grow][][][][][][][][][][]", "[grow]"));
		
			CadastroContrato contrato_pontuado = new GerenciarBancoContratos().getContrato(value.getId_contrato());
			
			
			JLabel lblCodigoContrato = new JLabel(contrato_pontuado.getCodigo());
			lblCodigoContrato.setBackground(new Color(153, 204, 0));
			lblCodigoContrato.setFont(new Font("Tahoma", Font.PLAIN, 14));
			painel_msg.add(lblCodigoContrato, "cell 0 0");
			
			CadastroCliente cliente_pontuado = new GerenciarBancoClientes().getCliente(value.getId_cliente());
			String nome_cliente = "";
			if(cliente_pontuado.getTipo_pessoa() == 0) {
				nome_cliente = cliente_pontuado.getNome_empresarial();
			}else {
				nome_cliente = cliente_pontuado.getNome_fantaia();
			}
			
			JLabel lblCliente = new JLabel(nome_cliente);
			lblCliente.setFont(new Font("Tahoma", Font.BOLD, 11));
			painel_msg.add(lblCliente, "cell 1 0");
			
			JLabel lblPontos = new JLabel(value.getPontos() + "");
			lblPontos.setFont(new Font("Tahoma", Font.PLAIN, 18));
			painel_msg.add(lblPontos, "cell 2 0");
			
			/*
			cBMotivoVendedor2.addItem("Não poderá arcar com a quantidade de sacos do contrato");
			cBMotivoVendedor2.addItem("Desistiu do contrato, sem renegociar");
			cBMotivoVendedor2.addItem("Renegociou o contrato com a parte ou intermediario");
			cBMotivoVendedor2.addItem("Renegociou o contrato através de aditivo contratual");
			*/
			String motivo = "";
			if(value.getMotivo() == 0) {
				motivo = "Conclui o contrato corretamente!";
			}else if(value.getMotivo() == 1){
				motivo = "Não poderá arcar com a quantidade de sacos do contrato";

			}else if(value.getMotivo() == 3){
				motivo = "Renegociou o contrato com a parte ou intermediario";

			}else if(value.getMotivo() == 4){
				motivo = "Renegociou o contrato através de aditivo contratual";

			}
			
			JLabel lblMotivo = new JLabel(motivo);
			painel_msg.add(lblMotivo, "cell 3 0");
			
			JTextArea textAreaDescricao = new JTextArea();
			textAreaDescricao.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 11));
			textAreaDescricao.setText(value.getDescricao());
			textAreaDescricao.setLineWrap(true);
			textAreaDescricao.setWrapStyleWord(true);
			painel_msg.add(textAreaDescricao, "cell 4 0,grow");
		
		
			
		
		return painel_msg;
	}
	


}
