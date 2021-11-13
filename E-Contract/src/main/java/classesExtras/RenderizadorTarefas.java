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
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.DreSimples;
import main.java.cadastros.FinanceiroPagamentoCompleto;
import net.miginfocom.swing.MigLayout;

public class RenderizadorTarefas implements ListCellRenderer<CadastroContrato.CadastroTarefa> {

	@Override
	public Component getListCellRendererComponent(JList<? extends CadastroContrato.CadastroTarefa> arg0, CadastroContrato.CadastroTarefa tarefa, int arg2,
			boolean arg3, boolean arg4) {

		

		JPanel painelPrincipal = new JPanel();
		painelPrincipal.setBorder(new MatteBorder(0, 0, 20, 0, (Color) new Color(255, 255, 255)));

		painelPrincipal.setLayout(new MigLayout("", "[48px][grow]", "[19px][][][][][50px][][50px]"));
		
		JLabel lblNewLabel = new JLabel("Status:");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		painelPrincipal.add(lblNewLabel, "cell 0 0,alignx right,aligny center");
		
		JLabel lblStatus = new JLabel("Concluída");
		lblStatus.setForeground(Color.WHITE);
		lblStatus.setFont(new Font("Arial", Font.BOLD, 16));
		painelPrincipal.add(lblStatus, "flowx,cell 1 0");
		
		JLabel lblNewLabel_2_1 = new JLabel("Criador:");
		lblNewLabel_2_1.setForeground(Color.WHITE);
		lblNewLabel_2_1.setFont(new Font("SansSerif", Font.PLAIN, 14));
		painelPrincipal.add(lblNewLabel_2_1, "cell 0 1,alignx right");
		
		JLabel lblPrioridadeasd = new JLabel("Prioridade:");
		lblPrioridadeasd.setForeground(Color.WHITE);
		lblPrioridadeasd.setFont(new Font("SansSerif", Font.PLAIN, 14));
		painelPrincipal.add(lblPrioridadeasd, "cell 0 2,alignx right");
		
		JLabel lblPrioridade = new JLabel("Imediato");
		lblPrioridade.setForeground(Color.WHITE);
		lblPrioridade.setFont(new Font("Arial", Font.BOLD, 16));
		painelPrincipal.add(lblPrioridade, "cell 1 2,alignx left");
		
		JLabel lblNomed = new JLabel("Nome:");
		lblNomed.setForeground(Color.WHITE);
		lblNomed.setFont(new Font("SansSerif", Font.PLAIN, 14));
		painelPrincipal.add(lblNomed, "cell 0 3,alignx right");
		
		JLabel lblNome = new JLabel("procurar registros");
		lblNome.setForeground(Color.WHITE);
		lblNome.setFont(new Font("Arial", Font.BOLD, 16));
		painelPrincipal.add(lblNome, "cell 1 3");
		
		JLabel lblDesc = new JLabel("Descrição:");
		lblDesc.setForeground(Color.WHITE);
		lblDesc.setFont(new Font("SansSerif", Font.PLAIN, 14));
		painelPrincipal.add(lblDesc, "cell 0 4,alignx right");
		
		JLabel lblDescricao = new JLabel("descrição");
		lblDescricao.setForeground(Color.WHITE);
		lblDescricao.setFont(new Font("Arial", Font.BOLD, 16));
		painelPrincipal.add(lblDescricao, "cell 1 4");
		
		JLabel lblMsg = new JLabel("Mensagem:");
		lblMsg.setForeground(Color.WHITE);
		lblMsg.setFont(new Font("SansSerif", Font.PLAIN, 14));
		painelPrincipal.add(lblMsg, "cell 0 5,alignx right");
		
	
		JTextArea lblMensagem = new JTextArea();
		lblMensagem.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblMensagem.setEditable(false);
		lblMensagem.setLineWrap(true);
		lblMensagem.setWrapStyleWord(true);
		JScrollPane scrollPane = new JScrollPane(lblMensagem);
		painelPrincipal.add(scrollPane, "cell 1 5,grow");
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setBackground(Color.WHITE);
		painelPrincipal.add(panel, "cell 1 0,growx");
		panel.setLayout(new MigLayout("", "[][][][][][][]", "[][][]"));
		
		JLabel lblDataTarefa = new JLabel("25/08/2021");
		lblDataTarefa.setForeground(Color.WHITE);
		lblDataTarefa.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel.add(lblDataTarefa, "cell 5 0");
		
		JLabel lblHoraTarefa = new JLabel("14:30");
		lblHoraTarefa.setForeground(Color.WHITE);
		lblHoraTarefa.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel.add(lblHoraTarefa, "cell 6 0,alignx center");
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new MatteBorder(1, 0, 0, 0, (Color) new Color(0, 0, 0)));
		panel_1.setBackground(new Color(204, 204, 153));
		painelPrincipal.add(panel_1, "cell 0 6 2 1,grow");
		panel_1.setLayout(new MigLayout("", "[][][][][][][][][][][][][][][][][]", "[][]"));
		
		JLabel lblNewLabel_2 = new JLabel("Executor:");
		lblNewLabel_2.setFont(new Font("SansSerif", Font.PLAIN, 14));
		panel_1.add(lblNewLabel_2, "cell 9 0");
		
		JLabel lblNomeExecutor = new JLabel("Aislan");
		lblNomeExecutor.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_1.add(lblNomeExecutor, "cell 10 0 2 1");
		
		JLabel lblNewLabel_1_2 = new JLabel("Data Agendada:");
		panel_1.add(lblNewLabel_1_2, "cell 15 0,alignx right");
		
		JLabel lblDataAgendadaTarefa = new JLabel("25/08/2021");
		lblDataAgendadaTarefa.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_1.add(lblDataAgendadaTarefa, "cell 16 0");
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Hora Agendada:");
		panel_1.add(lblNewLabel_1_1_1, "cell 15 1,alignx right");
		
		JLabel lblHoraAgendadaTarefa = new JLabel("14:30");
		lblHoraAgendadaTarefa.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_1.add(lblHoraAgendadaTarefa, "cell 16 1,alignx center");
		
		JLabel lblResposta123 = new JLabel("Resposta:");
		lblResposta123.setForeground(Color.WHITE);
		lblResposta123.setBackground(new Color(0, 102, 153));
		lblResposta123.setFont(new Font("SansSerif", Font.PLAIN, 14));
		painelPrincipal.add(lblResposta123, "cell 0 7,alignx right");
		
		JTextArea lblResposta = new JTextArea();
		lblResposta.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblResposta.setEditable(false);
		lblResposta.setWrapStyleWord(true);
		lblResposta.setLineWrap(true);
		JScrollPane scrollPane_1 = new JScrollPane(lblResposta);
		painelPrincipal.add(scrollPane_1, "cell 1 7,grow");
		
		JLabel lblNomeCriador = new JLabel("Aislan");
		lblNomeCriador.setForeground(Color.WHITE);
		lblNomeCriador.setFont(new Font("Arial", Font.BOLD, 16));
		painelPrincipal.add(lblNomeCriador, "cell 1 1,alignx left");
		
		
		
		/**********************************************************************************************/
		
		if(tarefa.getStatus_tarefa() == 1) {
			lblStatus.setText("Concluída");
			painelPrincipal.setBackground(new Color(0, 51, 0));

		}else if(tarefa.getStatus_tarefa() == 2) {
			lblStatus.setText("Em Andamento");
			painelPrincipal.setBackground(new Color(0, 51, 0));
			painelPrincipal.setBackground(new Color(255, 102, 0));

		}
		
		lblDescricao.setText(tarefa.getDescricao_tarefa());
		lblNomeCriador.setText(tarefa.getCriador().getNome() + " "+ tarefa.getCriador().getSobrenome());
		lblNome.setText(tarefa.getNome_tarefa());
		lblDataTarefa.setText(tarefa.getData());
		lblHoraTarefa.setText(tarefa.getHora());
		lblMensagem.setText(tarefa.getMensagem());
		
		
		lblNomeExecutor.setText(tarefa.getExecutor().getNome() + " "+ tarefa.getExecutor().getSobrenome());
		lblHoraAgendadaTarefa.setText(tarefa.getHora_agendada());
		lblDataAgendadaTarefa.setText(tarefa.getData_agendada());
		lblResposta.setText(tarefa.getResposta());

		return painelPrincipal;
	}
}
