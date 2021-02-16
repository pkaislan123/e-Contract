package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import outros.DadosGlobais;
import tratamento_proprio.Log;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

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
import cadastros.CadastroContrato.CadastroTarefa;
import cadastros.CadastroLogin;
import conexaoBanco.GerenciarBancoContratos;
import conexaoBanco.GerenciarBancoLogin;
import manipular.ConfiguracoesGlobais;

import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;



public class TelaTarefas extends JDialog {

	private final JPanel painelPrincipal = new JPanel();
    private JLabel lblTotalContratosConcluidos, lblTotalContratos, lblTotalContratosAbertos;
    private TelaTarefas isto;
    private JDialog telaPai;
    private JTable table_tarefas_designadas;
    
	private DefaultTableModel modelo_minhas_tarefas = new DefaultTableModel();
	private DefaultTableModel modelo_tarefas_designadas = new DefaultTableModel();
	private JTable table_minhas_tarefas;

    private ArrayList<CadastroContrato.CadastroTarefa> minhas_tarefas = new ArrayList<>();
    private ArrayList<CadastroContrato.CadastroTarefa> tarefas_designadas = new ArrayList<>();

    private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	
	
	public TelaTarefas(Window janela_pai) {
		//setModal(true);

		getDadosGlobais();
		 isto = this;
		setResizable(false);
		setTitle("E-Contract - Minhas Tarefas");

		
		setBackground(new Color(255, 255, 255));
		setBounds(100, 100, 1300, 698);
		painelPrincipal.setBackground(Color.WHITE);
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(51, 51, 0));
		panel.setBounds(0, 0, 1294, 94);
		painelPrincipal.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Tarefas");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 40));
		lblNewLabel.setBounds(890, 36, 141, 52);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(TelaTarefas.class.getResource("/imagens/icone_tarefa.png")));
		lblNewLabel_2.setBounds(1024, 24, 64, 64);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_1 = new JLabel("Minhas Tarefas:");
		lblNewLabel_1.setForeground(new Color(0, 51, 0));
		lblNewLabel_1.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblNewLabel_1.setBounds(61, 119, 146, 26);
		painelPrincipal.add(lblNewLabel_1);
		
		JPanel painelMinhasTarefas = new JPanel();
		painelMinhasTarefas.setBackground(Color.WHITE);
		painelMinhasTarefas.setBounds(20, 157, 1252, 174);
		painelPrincipal.add(painelMinhasTarefas);
		painelMinhasTarefas.setLayout(null);
		
		
		modelo_minhas_tarefas.addColumn("Id Tarefas");
		modelo_minhas_tarefas.addColumn("Status");

		modelo_minhas_tarefas.addColumn("Nome");
		modelo_minhas_tarefas.addColumn("Descrição");
		modelo_minhas_tarefas.addColumn("Mensagem");
		modelo_minhas_tarefas.addColumn("Resposta");

		modelo_minhas_tarefas.addColumn("Data");

		modelo_minhas_tarefas.addColumn("Hora");
		modelo_minhas_tarefas.addColumn("Criador");

		modelo_minhas_tarefas.addColumn("Executor");

		modelo_minhas_tarefas.addColumn("Hora Agendada");
		modelo_minhas_tarefas.addColumn("Data Agendada");
		modelo_minhas_tarefas.addColumn("Prioridade");
		
		table_minhas_tarefas = new JTable(modelo_minhas_tarefas);
		table_minhas_tarefas.setBackground(new Color(0, 153, 102));
		
		table_minhas_tarefas.getColumnModel().getColumn(0).setPreferredWidth(40);
		table_minhas_tarefas.getColumnModel().getColumn(1).setPreferredWidth(90);
		table_minhas_tarefas.getColumnModel().getColumn(2).setPreferredWidth(170);
		table_minhas_tarefas.getColumnModel().getColumn(3).setPreferredWidth(80);
		table_minhas_tarefas.getColumnModel().getColumn(4).setPreferredWidth(80);
		table_minhas_tarefas.getColumnModel().getColumn(5).setPreferredWidth(70);
		table_minhas_tarefas.getColumnModel().getColumn(6).setPreferredWidth(70);
		table_minhas_tarefas.getColumnModel().getColumn(7).setPreferredWidth(90);

		
		table_minhas_tarefas.setRowHeight(30);
		JScrollPane scrollPaneMinhasTarefas = new JScrollPane(table_minhas_tarefas);
		scrollPaneMinhasTarefas.setBounds(0, 6, 1252, 162);
		painelMinhasTarefas.add(scrollPaneMinhasTarefas);
		
		
		modelo_tarefas_designadas.addColumn("Id Tarefas");
		modelo_tarefas_designadas.addColumn("Status");

		modelo_tarefas_designadas.addColumn("Nome");
		modelo_tarefas_designadas.addColumn("Descrição");
		modelo_tarefas_designadas.addColumn("Mensagem");
		modelo_tarefas_designadas.addColumn("Resposta");

		modelo_tarefas_designadas.addColumn("Data");

		modelo_tarefas_designadas.addColumn("Hora");
		modelo_tarefas_designadas.addColumn("Criador");

		modelo_tarefas_designadas.addColumn("Executor");

		modelo_tarefas_designadas.addColumn("Hora Agendada");
		modelo_tarefas_designadas.addColumn("Data Agendada");
		modelo_tarefas_designadas.addColumn("Prioridade");
		
		
		table_tarefas_designadas = new JTable(modelo_tarefas_designadas);
		table_tarefas_designadas.setBackground(new Color(255, 153, 0));
		

		table_tarefas_designadas.getColumnModel().getColumn(0).setPreferredWidth(40);
		table_tarefas_designadas.getColumnModel().getColumn(1).setPreferredWidth(90);
		table_tarefas_designadas.getColumnModel().getColumn(2).setPreferredWidth(170);
		table_tarefas_designadas.getColumnModel().getColumn(3).setPreferredWidth(80);
		table_tarefas_designadas.getColumnModel().getColumn(4).setPreferredWidth(80);
		table_tarefas_designadas.getColumnModel().getColumn(5).setPreferredWidth(70);
		table_tarefas_designadas.getColumnModel().getColumn(6).setPreferredWidth(70);
		table_tarefas_designadas.getColumnModel().getColumn(7).setPreferredWidth(90);

		table_tarefas_designadas.setRowHeight(30);
		
		JScrollPane scrollPaneTarefasDesignadas = new JScrollPane(table_tarefas_designadas);
		scrollPaneTarefasDesignadas.setBounds(20, 387, 1252, 216);
		painelPrincipal.add(scrollPaneTarefasDesignadas);
		JLabel lblNewLabel_1_1 = new JLabel("Tarefas designadas a mim");
		lblNewLabel_1_1.setBackground(new Color(0, 51, 204));
		lblNewLabel_1_1.setForeground(new Color(255, 153, 51));
		lblNewLabel_1_1.setFont(new Font("SansSerif", Font.BOLD, 20));
		lblNewLabel_1_1.setBounds(61, 353, 248, 26);
		painelPrincipal.add(lblNewLabel_1_1);
		
		JButton btnVerTarefa = new JButton("Ver Tarefa");
		btnVerTarefa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int indiceDaLinha = 0;
				indiceDaLinha = table_tarefas_designadas.getSelectedRow();
				
				CadastroContrato.CadastroTarefa tarefa = tarefas_designadas.get(indiceDaLinha);
				
				GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
				int cont = gerenciar.getContratoPorTarefa(tarefa.getId_tarefa());
				
				
				
				CadastroContrato contrato = gerenciar.getContrato(cont);
				
				
				if(contrato.getSub_contrato() == 1) {
					// e um subcontrato
					//pegar contrato pai
					CadastroContrato contratopai = gerenciar.getContratoPai(contrato.getId());
					TelaGerenciarContrato telagerenciar  = new TelaGerenciarContrato(contrato, isto);
					telagerenciar.abrirAbaTarefasSubContrato(contrato);
					telagerenciar.setVisible(true);
					
				}else {
				
				TelaGerenciarContrato telagerenciar  = new TelaGerenciarContrato(contrato, isto);
				telagerenciar.abrirAbaTarefasContrato();
				telagerenciar.setVisible(true);
				}
				
			}
		});
		btnVerTarefa.setBounds(1182, 615, 90, 28);
		painelPrincipal.add(btnVerTarefa);
	
		

		isto.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		//Adicionando uma nova ação
		isto.addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent we) {
		       isto.setVisible(false);
		    }
		});
		

		this.setLocationRelativeTo(janela_pai);

		
		
		
	}
	
	
	
	public void getTarefas() {
		
		
		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
		ArrayList<CadastroTarefa> todas_tarefas = gerenciar.getTodasTarefas();
		
		
		for(CadastroTarefa tarefa : todas_tarefas) {
			if(tarefa.getCriador().getId() == login.getId()) {
				//eu sou o criador desta tarefa
				minhas_tarefas.add(tarefa);
				
			}else {
				
				if(tarefa.getExecutor().getId() == login.getId()) {
					//eu fui designado a esta tarefa
					tarefas_designadas.add(tarefa);
				}
				
			}
		}
		modelo_minhas_tarefas.setNumRows(0);
		modelo_tarefas_designadas.setNumRows(0);

		
		//adiciona minhas tarefas na tabela
		for(CadastroTarefa tarefa : minhas_tarefas) {

			String status_tarefa = "";
			String prioridade = "";
			String resposta = "";

			if (tarefa.getStatus_tarefa() == 1) {
				status_tarefa = "Concluida";
				resposta = tarefa.getResposta();
			} else if (tarefa.getStatus_tarefa() == 2) {
				status_tarefa = "Em Andamento";
				resposta = "Executor da tarefa ainda não respondeu";

			}

			if (tarefa.getPrioridade() == 1) {
				prioridade = "Imediata - Neste Momento";
			} else if (tarefa.getPrioridade() == 2) {
				prioridade = "Urgente - Nesta Hora";
			} else if (tarefa.getPrioridade() == 3) {
				prioridade = "Quanto Antes - Ainda Hoje";
			} else if (tarefa.getPrioridade() == 4) {
				prioridade = "Média - Ainda essa semana";
			} else if (tarefa.getPrioridade() == 5) {
				prioridade = "Leve - Ainda esse mês";
			}

			GerenciarBancoLogin gerenciarUsuarios = new GerenciarBancoLogin();
			CadastroLogin criador = gerenciarUsuarios.getLogin(tarefa.getCriador().getId());
			CadastroLogin executor = gerenciarUsuarios.getLogin(tarefa.getExecutor().getId());

			modelo_minhas_tarefas.addRow(new Object[] { tarefa.getId_tarefa(), status_tarefa, tarefa.getNome_tarefa(),
					tarefa.getDescricao_tarefa(), tarefa.getMensagem(), resposta, tarefa.getData(), tarefa.getHora(),
					criador.getNome(), executor.getNome(), tarefa.getHora_agendada(), tarefa.getData_agendada(),
					prioridade

			});
		
		}
		
		

		for(CadastroTarefa tarefa : tarefas_designadas) {

			String status_tarefa = "";
			String prioridade = "";
			String resposta = "";

			if (tarefa.getStatus_tarefa() == 1) {
				status_tarefa = "Concluida";
				resposta = tarefa.getResposta();
			} else if (tarefa.getStatus_tarefa() == 2) {
				status_tarefa = "Em Andamento";
				resposta = "Executor da tarefa ainda não respondeu";

			}

			if (tarefa.getPrioridade() == 1) {
				prioridade = "Imediata - Neste Momento";
			} else if (tarefa.getPrioridade() == 2) {
				prioridade = "Urgente - Nesta Hora";
			} else if (tarefa.getPrioridade() == 3) {
				prioridade = "Quanto Antes - Ainda Hoje";
			} else if (tarefa.getPrioridade() == 4) {
				prioridade = "Média - Ainda essa semana";
			} else if (tarefa.getPrioridade() == 5) {
				prioridade = "Leve - Ainda esse mês";
			}

			GerenciarBancoLogin gerenciarUsuarios = new GerenciarBancoLogin();
			CadastroLogin criador = gerenciarUsuarios.getLogin(tarefa.getCriador().getId());
			CadastroLogin executor = gerenciarUsuarios.getLogin(tarefa.getExecutor().getId());

			modelo_tarefas_designadas.addRow(new Object[] { tarefa.getId_tarefa(), status_tarefa, tarefa.getNome_tarefa(),
					tarefa.getDescricao_tarefa(), tarefa.getMensagem(), resposta, tarefa.getData(), tarefa.getHora(),
					criador.getNome(), executor.getNome(), tarefa.getHora_agendada(), tarefa.getData_agendada(),
					prioridade

			});
		
		}
		
	


	}
	
	
	public void getDadosGlobais() {
		//gerenciador de log
				DadosGlobais dados = DadosGlobais.getInstance();
				 GerenciadorLog = dados.getGerenciadorLog();
				 configs_globais = dados.getConfigs_globais();
				 
				 //usuario logado
				  login = dados.getLogin();
		
	}
	public void setTelaPai(JDialog _tela_pai) {
		this.telaPai = _tela_pai;
	}
}
