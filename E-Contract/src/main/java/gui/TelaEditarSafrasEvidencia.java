package main.java.gui;
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

import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroGrupo;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroSafra;
import main.java.cadastros.CadastroSafrasEvidencias;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoGrupos;
import main.java.conexaoBanco.GerenciarBancoSafras;
import main.java.conexaoBanco.GerenciarBancoSafrasEvidencias;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.outros.DadosGlobais;
import main.java.tratamento_proprio.Log;

import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.JOptionPane;
import java.awt.Insets;


public class TelaEditarSafrasEvidencia extends JDialog {

	private final JPanel painelPrincipal = new JPanel();
	private JTable table;
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais = new ConfiguracoesGlobais();
	private JTable table_1;
	private ArrayList<CadastroSafra> integrantes = new ArrayList<>();
	private JDialog telaPai;
	 DefaultTableModel modelo = new DefaultTableModel(){
         public boolean isCellEditable(int linha, int coluna) {  
             return false;
         }  
     };
     private CadastroSafrasEvidencias safra_global ;
	public TelaEditarSafrasEvidencia( Window janela_pai) {
		//setModal(true);

		TelaEditarSafrasEvidencia isto = this;
		
		setResizable(true);
		
		getDadosGlobais();
		 setTitle("E-Contract - Edição de Safras em Evidência");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 758, 641);
		painelPrincipal.setBackground(new Color(0, 51, 51));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		 painelPrincipal.setLayout(new MigLayout("", "[80px][5px][497px][5px][109px]", "[][][][][326px][31px][31px]"));
		 
		 JLabel lblIntegrantes = new JLabel("Safras");
		 lblIntegrantes.setForeground(Color.WHITE);
		 lblIntegrantes.setFont(new Font("Tahoma", Font.PLAIN, 20));
		 painelPrincipal.add(lblIntegrantes, "cell 0 0 5 4,grow");
		 
		 
		 table = new JTable(modelo);
		 table.setBackground(Color.WHITE);
		 JScrollPane scrollPane = new JScrollPane(table);
		 scrollPane.setBackground(Color.WHITE);
		 painelPrincipal.add(scrollPane, "cell 0 4 5 1,grow");
		 
		 table.setRowHeight(30);
		 
		 JButton btnExcluirIntegrante = new JButton("Excluir");
		 btnExcluirIntegrante.setForeground(Color.WHITE);
		 btnExcluirIntegrante.setFont(new Font("SansSerif", Font.BOLD, 14));
		 btnExcluirIntegrante.setOpaque(false);
		 btnExcluirIntegrante.setBackground(new Color(153, 51, 0));
		 painelPrincipal.add(btnExcluirIntegrante, "cell 2 5,alignx right,aligny top");
		 btnExcluirIntegrante.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		
		 		int indiceDaLinha = table.getSelectedRow();
		 		CadastroSafra safra_selecionada = integrantes.get(indiceDaLinha);
		 		integrantes.remove(safra_selecionada);
		 		modelo.removeRow(indiceDaLinha);
		 	}
		 });
		 
		 JButton btnConcluir = new JButton("Concluir");
		 btnConcluir.setBackground(new Color(0, 51, 0));
		 btnConcluir.setForeground(Color.WHITE);
		 btnConcluir.setFont(new Font("SansSerif", Font.BOLD, 14));
		 btnConcluir.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		
		 	
		 		String ids = "";
		 		for(CadastroSafra safra : integrantes) {
		 			ids += safra.getId_safra();
		 			
		 			ids += ",";
		 		}
		 	
		 		ids = ids.replaceFirst(".$", "");
		 		
		 		safra_global.setIds_safras(ids);
		 		GerenciarBancoSafrasEvidencias gerenciar = new GerenciarBancoSafrasEvidencias();
		 		boolean atualizou = gerenciar.atualizarSafrEnvidencia(safra_global);
		 		if(atualizou) {
		 			JOptionPane.showMessageDialog(isto, "Atualizado");
		 			((TelaMain) janela_pai).pesquisarSafrasEvidencias();
		 			isto.dispose();
		 		}else {
		 			JOptionPane.showMessageDialog(isto, "Erro\nConsulte o Administrador");

		 		}

		 	}
		 });
		 
		 
		 
		 JButton btnAdicionarIntegrante = new JButton("+Safra");
		 btnAdicionarIntegrante.setBackground(new Color(153, 102, 51));
		 btnAdicionarIntegrante.setFont(new Font("SansSerif", Font.BOLD, 14));
		 btnAdicionarIntegrante.setForeground(Color.WHITE);
		 painelPrincipal.add(btnAdicionarIntegrante, "cell 4 5,growx,aligny top");
		 btnAdicionarIntegrante.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		TelaSafra tela = new TelaSafra(isto);
		 		tela.setVisible(true);
		 	}
		 });
		 painelPrincipal.add(btnConcluir, "cell 4 6,growx,aligny top");
		
		modelo.addColumn("Id");
        modelo.addColumn("Produto");
        modelo.addColumn("Transgenia");
        modelo.addColumn("Ano Plantio");
        modelo.addColumn("Ano Colheita");
		
        pesquisarSafrasEvidencias();
		
		
		this.setLocationRelativeTo(janela_pai);

		
		
		
	}
	
	
	
	public void adicionarSafra(CadastroSafra safra) {
		integrantes.add(safra);
        modelo.addRow(new Object[]{safra.getId_safra(),safra.getProduto().getNome_produto(), safra.getProduto().getTransgenia(), safra.getAno_plantio(), safra.getAno_colheita() });

	}
	

	public void setTelaPai(JDialog tela_pai) {
		this.telaPai = tela_pai;
	}
	
	public void pesquisarSafrasEvidencias() {

		GerenciarBancoSafrasEvidencias gerenciar = new GerenciarBancoSafrasEvidencias();
		ArrayList<CadastroSafrasEvidencias> safras_envidencia = gerenciar.getSafrasEvidenciaPorUsuario(login.getId());

		GerenciarBancoSafras gerenciar_safras = new GerenciarBancoSafras();

		
		
		
		if (safras_envidencia != null) {
			if (safras_envidencia.size() > 0) {

				CadastroSafrasEvidencias cad = safras_envidencia.get(0);
				
				safra_global = cad;
				safra_global.setId_usuario(login.getId());
				
					String ids_safras = cad.getIds_safras();
					if (ids_safras.length() > 0) {

						String ids_separados[] = ids_safras.split(",");
						for (String id : ids_separados) {

							try {

								int int_id = Integer.parseInt(id);
								CadastroSafra safra = gerenciar_safras.getSafra(int_id);
								if (safra != null) {
									integrantes.add(safra);
							        modelo.addRow(new Object[]{safra.getId_safra(),safra.getProduto().getNome_produto(), safra.getProduto().getTransgenia(), safra.getAno_plantio(), safra.getAno_colheita() });

									
								}

							} catch (Exception e) {

							}

						}

					}
				

			}
		}
		
		
	}


	public void getDadosGlobais() {
		// gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		GerenciadorLog = dados.getGerenciadorLog();
		configs_globais = dados.getConfigs_globais();

		// usuario logado
		login = dados.getLogin();

	}
	
}
