package classesExtras;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.LineBorder;

import cadastros.CadastroLogin;
import cadastros.CadastroLogin.Mensagem;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextArea;

public class RenderizadorChat implements ListCellRenderer<CadastroLogin.Mensagem> {
	

	int id_servidor = -1;
	
	public void setServidor(int id_usuario_servidor) {
		this.id_servidor = id_usuario_servidor;
	}
	
	
	@Override
	public Component getListCellRendererComponent(JList<? extends Mensagem> arg0, Mensagem msg, int arg2, boolean arg3,
			boolean arg4) {
		
		String conteudo = msg.getConteudo();
		String data_hora = msg.getData() + " as " + msg.getHora();
	
		JPanel painel_msg = new JPanel();
		
		//painel mensagem destinatario a remetente
		if(msg.getId_remetente() == id_servidor ) {
		
		painel_msg.setBackground(Color.WHITE);
		painel_msg.setBounds(0, 0, 414, 80);
		painel_msg.setLayout(new MigLayout("", "[][][][][][][][][][][][][grow]", "[grow]"));
		
		
		JLabel lblNewLabel = new JLabel("teste de espaçamento");
		lblNewLabel.setBackground(Color.BLUE);
		lblNewLabel.setVisible(false);
		painel_msg.add(lblNewLabel, "cell 0 0 4 1");
		
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GREEN);
		painel_msg.add(panel, "cell 4 0 9 1,grow");
		panel.setLayout(new MigLayout("", "[grow][][][][][][][]", "[grow][]"));
		
		JTextArea mostrar_conteudo = new JTextArea(conteudo);
		mostrar_conteudo.setBackground(new Color(0, 0, 0, 0));
		mostrar_conteudo.setBorder(null);
		mostrar_conteudo.setOpaque(false);
		mostrar_conteudo.setLineWrap(true);
		mostrar_conteudo.setWrapStyleWord(true);


		panel.add(mostrar_conteudo, "cell 0 0 8 1,grow");

		JLabel mostrar_data_hora = new JLabel(data_hora);
		mostrar_data_hora.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel.add(mostrar_data_hora, "cell 7 1");
		}else {
		
		//painel remetente a destinatario
		painel_msg.setBackground(Color.WHITE);
		painel_msg.setBounds(10, 0, 414, 80);
		painel_msg.setLayout(new MigLayout("", "[][][][][][][][][][][][][][grow]", "[grow]"));
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBackground(Color.WHITE);
		painel_msg.add(panel, "flowx,cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[grow][][][][][][][]", "[grow][]"));
		
		JTextArea mostrar_conteudo = new JTextArea(conteudo);
		mostrar_conteudo.setWrapStyleWord(true);
		mostrar_conteudo.setBackground(new Color(0, 0, 0, 0));
		mostrar_conteudo.setBorder(null);
		mostrar_conteudo.setOpaque(false);
		mostrar_conteudo.setLineWrap(true);
		panel.add(mostrar_conteudo, "cell 0 0 8 1,grow");
		
		JLabel mostrar_data_hora = new JLabel(data_hora);
		mostrar_data_hora.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel.add(mostrar_data_hora, "cell 7 1");
		
		JLabel lblNewLabel = new JLabel("teste de espaçamento");
		lblNewLabel.setVisible(false);
		painel_msg.add(lblNewLabel, "cell 2 0 11 1");
		
		}
		return painel_msg;

	
	}
	


}
