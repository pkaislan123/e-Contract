package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.http.client.utils.DateUtils;
import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import net.miginfocom.swing.MigLayout;
import outros.DadosGlobais;
import tratamento_proprio.Log;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GraphicsConfiguration;

import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
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
import manipular.ConfiguracoesGlobais;

import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import java.awt.Component;
import javax.swing.Box;



public class TelaCriarTarefaResposta extends JDialog {
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private final JPanel painelPrincipal = new JPanel();
	private TelaCriarTarefaResposta isto;
	private JTextField entData;
	private JTextField entHora;
    private CadastroLogin executor;
    private Window telaPai;

    private JTextArea textAreaResposta;
    
    public TelaCriarTarefaResposta(CadastroTarefa tarefa, Window janela_pai) {
		//setModal(true);

		
		 isto = this;
		 getDadosGlobais();
		setResizable(true);
		setTitle("E-Contract - Concluir Tarefa");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 446, 261);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);
		
		JLabel lblTarefa = new JLabel("Resposta:");
		lblTarefa.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTarefa.setBounds(27, 74, 61, 17);
		painelPrincipal.add(lblTarefa);
		
		JLabel lblHora = new JLabel("Hora:");
		lblHora.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblHora.setBounds(232, 34, 34, 17);
		painelPrincipal.add(lblHora);
		
		JLabel lblData = new JLabel("Data:");
		lblData.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblData.setBounds(53, 34, 34, 17);
		painelPrincipal.add(lblData);
		
		 textAreaResposta = new JTextArea();
		 textAreaResposta.setLineWrap(true);
		 textAreaResposta.setWrapStyleWord(true);
		textAreaResposta.setBorder(new LineBorder(new Color(0, 0, 0)));
		textAreaResposta.setBounds(101, 74, 298, 100);
		painelPrincipal.add(textAreaResposta);
		
		entData = new JTextField();
		entData.setEditable(false);
		entData.setColumns(10);
		entData.setBounds(100, 34, 122, 28);
		painelPrincipal.add(entData);
	
		entData.setColumns(10);
		
		entHora = new JTextField();
		entHora.setEditable(false);
		entHora.setColumns(10);
		entHora.setBounds(276, 34, 122, 28);
		painelPrincipal.add(entHora);
		
		
		  LocalTime localTime4  = LocalTime.now();
			 String hora_formatada =  localTime4.format(DateTimeFormatter.ofPattern("HH:mm"));
			 entHora.setText(hora_formatada);
			 
			 String strLocalDate2   = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        entData.setText(strLocalDate2);
        
        
        JButton btnNewButton = new JButton("Concluir");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (JOptionPane.showConfirmDialog(isto, 
			            "Uma tarefa após respondida não pode ser editada nem excluida!", "Concluir a tarefa?", 
			            JOptionPane.YES_NO_OPTION,
			            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
        			
        			String resposta = textAreaResposta.getText();
            		
            		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
            		boolean atualizou = gerenciar.atualizarStatusTarefa(resposta, tarefa.getId_tarefa());
            		if(atualizou) {
            			
            			JOptionPane.showMessageDialog(isto, "Tarefa atualizada e status alterado para concluido!");
            			
            			//((TelaGerenciarContrato) telaPai).atualizarListaTarefas();
            			((TelaGerenciarContrato) telaPai).atualizarListaTarefas();
            			((TelaGerenciarContrato) telaPai).informar_atualizou();


            			isto.dispose();
            		}else {
            			JOptionPane.showMessageDialog(isto, "Erro ao atualizar a tarefa!\nConsulte o administrador");
            			isto.dispose();
            		}
        			
        			
        		}
        		
        		
        		
        	}
        });
        btnNewButton.setBounds(325, 186, 74, 28);
        painelPrincipal.add(btnNewButton);
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		isto.dispose();
        	}
        });
        btnCancelar.setBounds(237, 186, 78, 28);
        painelPrincipal.add(btnCancelar);
		
		
		  
		this.setLocationRelativeTo(janela_pai);

		
		
		
	}
	
	
	
	
	public boolean checkString(String check) {
		if( check != null && check.length() > 5 && !check.equals("") && !check.equals(" ")) {
			return true;
		}else {
			return false;
		}
	}
	
	public String pegarData() {

		Date hoje = new Date();
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return df.format(hoje);
	}
	
	public String pegarHora() {

		LocalTime localTime      = LocalTime.now();
		 return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
	}
	
	
	
	public void getDadosGlobais() {
		//gerenciador de log
				DadosGlobais dados = DadosGlobais.getInstance();
				 GerenciadorLog = dados.getGerenciadorLog();
				 configs_globais = dados.getConfigs_globais();
				 
				 //usuario logado
				  login = dados.getLogin();
		
	}
	
	public void setTelaPai(Window tela_pai) {
		this.telaPai = tela_pai;
	}	
	
	
}
