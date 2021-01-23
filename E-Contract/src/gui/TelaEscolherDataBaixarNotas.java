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
import outros.BaixarNotasFiscais;
import outros.DadosGlobais;
import outros.GetData;
import tratamento_proprio.Log;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

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
import cadastros.CadastroLogin;
import conexaoBanco.GerenciarBancoContratos;
import manipular.ConfiguracoesGlobais;

import javax.swing.border.LineBorder;
import javax.swing.JComboBox;



public class TelaEscolherDataBaixarNotas extends JDialog {

	private final JPanel painelPrincipal = new JPanel();
    private JLabel lblTotalContratosConcluidos, lblTotalContratos, lblTotalContratosAbertos;
    private TelaEscolherDataBaixarNotas isto;
    private JDialog telaPai;
    private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;

	public TelaEscolherDataBaixarNotas(CadastroCliente cliente) {
		setModal(true);
        getDadosGlobais();
		 isto = this;
		
		setResizable(true);
		setTitle("E-Contract - Escolher Periodo");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 353, 202);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);
		
		JLabel M = new JLabel("Mês Inicial:");
		M.setFont(new Font("Tahoma", Font.PLAIN, 14));
		M.setBounds(33, 30, 64, 17);
		painelPrincipal.add(M);
		
		JLabel lblMsFinal = new JLabel("Mês Final:");
		lblMsFinal.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMsFinal.setBounds(39, 62, 58, 17);
		painelPrincipal.add(lblMsFinal);
		
		JComboBox cBMesInicio = new JComboBox();
		cBMesInicio.setBounds(107, 26, 201, 26);
		painelPrincipal.add(cBMesInicio);
		cBMesInicio.addItem("JANEIRO");
		cBMesInicio.addItem("FEVEREIRO");
		cBMesInicio.addItem("MARÇO");
		cBMesInicio.addItem("ABRIL");
		cBMesInicio.addItem("MAIO");
		cBMesInicio.addItem("JUNHO");
		cBMesInicio.addItem("JULHO");
		cBMesInicio.addItem("AGOSTO");
		cBMesInicio.addItem("SETEMBRO");
		cBMesInicio.addItem("OUTUBRO");
		cBMesInicio.addItem("NOVEMBRO");
		cBMesInicio.addItem("DEZEMBRO");

		
		
		JComboBox cBMesFim = new JComboBox();
		cBMesFim.setBounds(107, 58, 201, 26);
		painelPrincipal.add(cBMesFim);
		cBMesFim.addItem("JANEIRO");
		cBMesFim.addItem("FEVEREIRO");
		cBMesFim.addItem("MARÇO");
		cBMesFim.addItem("ABRIL");
		cBMesFim.addItem("MAIO");
		cBMesFim.addItem("JUNHO");
		cBMesFim.addItem("JULHO");
		cBMesFim.addItem("AGOSTO");
		cBMesFim.addItem("SETEMBRO");
		cBMesFim.addItem("OUTUBRO");
		cBMesFim.addItem("NOVEMBRO");
		cBMesFim.addItem("DEZEMBRO");
		cBMesFim.setSelectedItem("FEVEREIRO");
		
		GetData data = new GetData();
		JComboBox cBAno = new JComboBox();
		cBAno.setBounds(107, 95, 201, 26);
		painelPrincipal.add(cBAno);
		cBAno.addItem(data.getAnoAtual() -1);
		cBAno.addItem(data.getAnoAtual() );
		cBAno.setSelectedItem(data.getAnoAtual() );
		
		JButton btnNewButton = new JButton("Cancelar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isto.dispose();
			}
		});
		btnNewButton.setBounds(218, 132, 90, 28);
		painelPrincipal.add(btnNewButton);
		
		JButton btnConcluir = new JButton("Iniciar");
		btnConcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String mes_inicio = cBMesInicio.getSelectedItem().toString();
				String mes_fim = cBMesFim.getSelectedItem().toString();
				
				int i_mes_inicio = checkMes(mes_inicio);
				int i_mes_fim = checkMes(mes_fim);
				
				if(i_mes_inicio == i_mes_fim) {
					JOptionPane.showMessageDialog(null, "Intervalo mensal deve ser superior a um mês");
				}else {
					String ano = cBAno.getSelectedItem().toString();
					try {
					int i_ano = Integer.parseInt(ano);
					
					DadosGlobais dados = DadosGlobais.getInstance();

					
					  TelaPrincipal telaP = dados.getTelaPrincipal();
					  telaP.baixarNotasEmSegundoPlano(cliente, i_mes_inicio, i_mes_fim, i_ano);
					  
					  JOptionPane.showMessageDialog(null, "Download de nota foi iniciado em segundo plano");
					  isto.dispose();
					 
					}catch(Exception f) {
						JOptionPane.showMessageDialog(null, "Não foi possivel converter o ano\nConsulte o administrador");

					}
				}

				
				
			}
		});
		btnConcluir.setBounds(118, 132, 90, 28);
		painelPrincipal.add(btnConcluir);
		
		JLabel lblAno = new JLabel("Ano:");
		lblAno.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAno.setBounds(68, 100, 29, 17);
		painelPrincipal.add(lblAno);
		
	

		
		

		this.setLocationRelativeTo(null);

		
		
		
	}
	
	public int checkMes(String mes) {
		if(mes.equals("JANEIRO"))
			return 1;
		else if(mes.equals("FEVEREIRO"))
			return 2;
		else if(mes.equals("MARÇO"))
			return 3;
		else if(mes.equals("ABRIL"))
			return 4;
		else if(mes.equals("MAIO"))
			return 5;
		else if(mes.equals("JUNHO"))
			return 6;
		else if(mes.equals("JULHO"))
			return 7;
		else if(mes.equals("AGOSTO"))
			return 8;
		else if(mes.equals("SETEMBRO"))
			return 9;
		else if(mes.equals("OUTUBRO"))
			return 10;
		else if(mes.equals("NOVEMBRO"))
			return 11;
		
		else
			return 12;
		
		
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
