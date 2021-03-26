package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cadastros.CadastroContrato;
import cadastros.CadastroLogin;
import cadastros.CadastroModelo;
import conexaoBanco.GerenciarBancoContratos;
import conexaoBanco.GerenciarBancoSafras;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Window;

import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

import gui.TelaElaborarNovoContrato;
import manipular.ConfiguracoesGlobais;
import outros.DadosGlobais;
import tratamento_proprio.Log;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TelaEscolhaTipoNovoContrato extends JDialog {

	private final JPanel painelPrincipal = new JPanel();
	ArrayList<CadastroModelo> modelos = new ArrayList<>();
	private TelaGerenciarContrato telaGerenciarCadastroPai;
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	
	public void pesquisarModelos()
	{
		 GerenciarBancoContratos listaModelos = new GerenciarBancoContratos();
		 modelos = listaModelos.getModelos();
		    
	}

	public TelaEscolhaTipoNovoContrato(int tipoContrato, CadastroContrato contrato_pai, int flag_edicao,Window janela_pai) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				if(flag_edicao == 1 || tipoContrato == 1) {
					//esta no modo edicao
					DadosGlobais dados = DadosGlobais.getInstance();
					 dados.getTeraGerenciarContratoPai().atualizarContratoLocal(true);
					
				}
			}
		});
    //		setAlwaysOnTop(true);

		//setModal(true);
		getDadosGlobais();

		TelaEscolhaTipoNovoContrato isto = this;
		
		setResizable(false);
		setTitle("E-Contract - Tipo Contrato");

	
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 459, 209);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);
		
		JLabel lblTipo = new JLabel("Tipo:");
		lblTipo.setHorizontalAlignment(SwingConstants.TRAILING);
		lblTipo.setForeground(Color.BLACK);
		lblTipo.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblTipo.setBackground(Color.ORANGE);
		lblTipo.setBounds(51, 50, 64, 33);
		painelPrincipal.add(lblTipo);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(153, 52, 266, 33);
		
		comboBox.addItem("Padrão - Formal");
		comboBox.addItem("Padrão - Informal");

		painelPrincipal.add(comboBox);
		
		JButton btnCriarContrato = new JButton("OK");
		btnCriarContrato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                   String opcao = comboBox.getSelectedItem().toString();
  
                   if(opcao.equals("Padrão - Formal"))
                   {
                	   System.out.println("padrao escolhido: " + modelos.get(1).getNome_modelo());
                	   TelaElaborarNovoContrato contrato = new TelaElaborarNovoContrato(modelos.get(1), tipoContrato, contrato_pai, flag_edicao, isto);
                 	  
                 	  // TelaNovoContrato contrato = new TelaNovoContrato("C:\\Users\\Aislan\\Documents\\modelo_informal_padrao.xlsx");
                 	   isto.dispose();  
                   }
                   if(opcao.equals("Padrão - Informal"))
                   {
                	  TelaElaborarNovoContrato contrato = new TelaElaborarNovoContrato(modelos.get(0), tipoContrato, contrato_pai, flag_edicao,isto);
                	  
                	  // TelaNovoContrato contrato = new TelaNovoContrato("C:\\Users\\Aislan\\Documents\\modelo_informal_padrao.xlsx");
                	   isto.dispose();
                   }
                   
			}
		});
		btnCriarContrato.setBounds(220, 123, 89, 23);
		painelPrincipal.add(btnCriarContrato);
		
		JButton btnCancelarCriarContrato = new JButton("Cancelar");
		btnCancelarCriarContrato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isto.dispose();
			}
		});
		btnCancelarCriarContrato.setBounds(330, 123, 89, 23);
		painelPrincipal.add(btnCancelarCriarContrato);
		
		pesquisarModelos();
		this.setLocationRelativeTo(janela_pai);

		this.setVisible(true);
		
		
	}
	

	public void getDadosGlobais() {
		//gerenciador de log
				DadosGlobais dados = DadosGlobais.getInstance();
				 GerenciadorLog = dados.getGerenciadorLog();
				 configs_globais = dados.getConfigs_globais();
				 
				 //usuario logado
				  login = dados.getLogin();
		
	}
	
}
