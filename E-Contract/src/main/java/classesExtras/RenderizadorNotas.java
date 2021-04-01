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

import main.java.cadastros.CadastroNota;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextArea;

public class RenderizadorNotas implements ListCellRenderer<CadastroNota> {
	
	@Override
	public Component getListCellRendererComponent(JList<? extends CadastroNota> list, CadastroNota value, int index,
			boolean isSelected, boolean cellHasFocus) {

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 250, 240));
		panel.setBounds(9, 9, 283, 93);
		panel.setLayout(new MigLayout("", "[][][]", "[][]"));

		JLabel lblNewLabel = new JLabel(value.getNome());
		lblNewLabel.setForeground(new Color(0, 0, 128));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel.add(lblNewLabel, "cell 0 0");

		JLabel lblNewLabel_1 = new JLabel("");
		
		int tipo_nota = value.getTipo();
		
		if(tipo_nota == 1) {
			lblNewLabel_1.setIcon(new ImageIcon(this.getClass().getResource("/imagens/icone_nota_comum.png")));

		}else if(tipo_nota == 2) {
			lblNewLabel_1.setIcon(new ImageIcon(this.getClass().getResource("/imagens/icone_topico_comum.png")));

		}else if(tipo_nota == 3) {
			lblNewLabel_1.setIcon(new ImageIcon(this.getClass().getResource("/imagens/icone_topico_broadcast.png")));

		}
		
		panel.add(lblNewLabel_1, "cell 2 0");

		JLabel lblDescrioDaAnotao = new JLabel(value.getDescricao());

		lblDescrioDaAnotao.setForeground(new Color(0, 153, 51));
		lblDescrioDaAnotao.setFont(new Font("Tahoma", Font.ITALIC, 18));
		panel.add(lblDescrioDaAnotao, "cell 0 1 2 1");
		
		if(isSelected) {
			panel.setBackground(Color.RED);

		}
		
	   
		return panel;	
	}
	
	
}










