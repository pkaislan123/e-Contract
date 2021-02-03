package classesExtras;
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

import cadastros.CadastroCliente;
import cadastros.CadastroContrato;
import cadastros.CadastroLogin;
import cadastros.CadastroLogin.Mensagem;
import conexaoBanco.GerenciarBancoClientes;
import conexaoBanco.GerenciarBancoContratos;
import gui.TelaVisaoGeralContrato;
import cadastros.CadastroPontuacao;
import cadastros.CadastroRomaneio;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextArea;

public class RenderizadorRomaneio implements ListCellRenderer<CadastroRomaneio> {
	
	@Override
	public Component getListCellRendererComponent(JList<? extends CadastroRomaneio> list, CadastroRomaneio value,
			int index, boolean isSelected, boolean cellHasFocus) {
		
		
		
		
				return list;

	}
	


}
