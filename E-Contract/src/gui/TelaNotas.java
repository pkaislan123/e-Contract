package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.DefaultListCellRenderer;
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
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;

import java.awt.GridLayout;
import java.awt.Point;
import java.awt.SystemColor;
import java.awt.Window;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import cadastros.CadastroCliente;
import cadastros.CadastroContrato;
import cadastros.CadastroLogin;
import cadastros.CadastroNota;
import cadastros.CadastroPontuacao;
import classesExtras.RenderizadorNotas;
import classesExtras.RenderizadorPontuacao;
import conexaoBanco.GerenciarBancoContratos;
import conexaoBanco.GerenciarBancoNotas;
import conexaoBanco.GerenciarBancoPontuacao;
import keeptoo.KGradientPanel;
import manipular.ConfiguracoesGlobais;

import javax.swing.border.LineBorder;
import javax.swing.JScrollPane;



public class TelaNotas extends JDialog {

	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private final KGradientPanel painelPrincipal = new KGradientPanel();
    private JLabel lblTotalContratosConcluidos, lblTotalContratos, lblTotalContratosAbertos;
    private TelaNotas isto;
    private JDialog telaPai;
   private JList lista_notas;
    private DefaultListModel<CadastroNota>   listModelGlobal;
    private JTextArea textAreaTexto;
    private int indice_nota_selecionada = -1;
    
    public static TelaNotas instance = null;  

    
    private JLabel lblNomeNota, lblTempoNotificacao,lblNotificar, lblLembrar , lblLembrarMeHora, lblLembrarMeData;
	public TelaNotas(Window janela_pai) {
		//setModal(true);
		instance = this;
		 isto = this;
		getDadosGlobais();
		setResizable(true);
		setTitle("E-Contract - Notas");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1087, 620);
		painelPrincipal.kStartColor = new Color(0, 102, 255);
		painelPrincipal.kEndColor = new Color(0, 153, 204);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);
		
		KGradientPanel panel = new KGradientPanel();
		panel.kStartColor = new Color(102, 0, 204);
		panel.kEndColor = new Color(0, 102, 102);
		panel.setBounds(0, 0, 1071, 150);
		painelPrincipal.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(TelaNotas.class.getResource("/imagens/icone_anotacoes.png")));
		lblNewLabel.setBounds(21, 11, 124, 111);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Anotações");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 36));
		lblNewLabel_1.setBounds(197, 42, 165, 44);
		panel.add(lblNewLabel_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 224));
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		panel_1.setBounds(22, 177, 301, 357);
		painelPrincipal.add(panel_1);
		panel_1.setLayout(null);
		
		
		  listModelGlobal = new DefaultListModel<CadastroNota>();
		 ArrayList<CadastroNota>  notas = new  ArrayList<>();
		   GerenciarBancoNotas gerenciar = new GerenciarBancoNotas();
		   notas = gerenciar.getnotas(login.getId());
		 
		 for(CadastroNota not : notas) {
				
			 
		     listModelGlobal.addElement(not);
		}
		 
		 RenderizadorNotas render = new RenderizadorNotas();

		 
		  lista_notas = new JList<>();
		 lista_notas.setBackground(new Color(255, 248, 220));
			
			lista_notas.setCellRenderer(render);
			lista_notas.setModel(listModelGlobal);
			lista_notas.addMouseListener(new MouseAdapter()
			    {
			      @Override
			      public void mouseClicked(MouseEvent event)
			      {
			        clickButtonAt(event.getPoint());
			      }
			    });

			

		
		JScrollPane scrollPane_1 = new JScrollPane(lista_notas);
		scrollPane_1.setBounds(0, 0, 301, 357);
		panel_1.add(scrollPane_1);
		
		JButton btnNewButton = new JButton("+Anotação");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCriarAnotacaoDesktopVirtual tela = new TelaCriarAnotacaoDesktopVirtual(0, isto);
				tela.setVisible(true);
			}
		});
		btnNewButton.setBounds(233, 546, 90, 28);
		painelPrincipal.add(btnNewButton);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(0, 102, 204));
		panel_2.setBounds(335, 177, 699, 42);
		painelPrincipal.add(panel_2);
		panel_2.setLayout(new MigLayout("", "[]", "[]"));
		
		 lblNomeNota = new JLabel("Nome");
		lblNomeNota.setForeground(Color.WHITE);
		lblNomeNota.setBackground(new Color(51, 153, 153));
		panel_2.add(lblNomeNota, "cell 0 0");
		lblNomeNota.setFont(new Font("Arial", Font.PLAIN, 24));
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			    CadastroNota item = (CadastroNota) lista_notas.getModel().getElementAt(indice_nota_selecionada);
                TelaCriarNota telaeditar = new TelaCriarNota(2, item, isto);
                telaeditar.setTelaPai(isto);
                telaeditar.setVisible(true);
			    
			}
		});
		btnEditar.setBounds(734, 506, 90, 28);
		painelPrincipal.add(btnEditar);
		
		
		 textAreaTexto = new JTextArea();
		 textAreaTexto.setLineWrap(true);
		 textAreaTexto.setWrapStyleWord(true);
		 textAreaTexto.setBackground(new Color(255, 239, 213));

		
		JScrollPane scrollPane3 = new JScrollPane(textAreaTexto);
		scrollPane3.setBounds(335, 231, 489, 265);
		painelPrincipal.add(scrollPane3);
		
		JPanel panel_3 = new JPanel();
		panel_3.setForeground(new Color(153, 204, 0));
		panel_3.setBackground(new Color(153, 204, 0));
		panel_3.setBounds(836, 231, 198, 265);
		painelPrincipal.add(panel_3);
		panel_3.setLayout(null);
		
		 lblNotificar = new JLabel("Não Notificar");
		 lblNotificar.setVisible(false);
		lblNotificar.setForeground(Color.WHITE);
		lblNotificar.setFont(new Font("SansSerif", Font.BOLD, 24));
		lblNotificar.setBackground(Color.WHITE);
		lblNotificar.setBounds(6, 34, 149, 32);
		panel_3.add(lblNotificar);
		
		 lblTempoNotificacao = new JLabel("a Cada 10 minutos");
		 lblTempoNotificacao.setVisible(false);
		 lblTempoNotificacao.setForeground(new Color(0, 51, 153));
		 lblTempoNotificacao.setBackground(new Color(0, 0, 255));
		lblTempoNotificacao.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblTempoNotificacao.setBounds(48, 68, 144, 19);
		panel_3.add(lblTempoNotificacao);
		
		 lblLembrarMeData = new JLabel("em 30/01/2020");
		 lblLembrarMeData.setVisible(false);
		 lblLembrarMeData.setForeground(new Color(0, 0, 204));
		lblLembrarMeData.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblLembrarMeData.setBounds(58, 163, 97, 19);
		panel_3.add(lblLembrarMeData);
		
		 lblLembrar = new JLabel("Não Lembrar");
		 lblLembrar.setVisible(false);
		lblLembrar.setForeground(Color.WHITE);
		lblLembrar.setFont(new Font("SansSerif", Font.BOLD, 24));
		lblLembrar.setBackground(Color.WHITE);
		lblLembrar.setBounds(6, 121, 147, 32);
		panel_3.add(lblLembrar);
		
		 lblLembrarMeHora = new JLabel("as 14:25");
		 lblLembrarMeHora.setVisible(false);
		 lblLembrarMeHora.setForeground(new Color(0, 0, 255));
		lblLembrarMeHora.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblLembrarMeHora.setBounds(119, 182, 56, 19);
		panel_3.add(lblLembrarMeHora);
		
		JButton btnExcluirNota = new JButton("Excluir");
		btnExcluirNota.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(isto, 
			            "Deseja excluir a nota selecionada?", "Exclusão", 
			            JOptionPane.YES_NO_OPTION,
			            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					
				    CadastroNota item = (CadastroNota) lista_notas.getModel().getElementAt(indice_nota_selecionada);

					GerenciarBancoNotas gerenciar = new GerenciarBancoNotas();
					boolean excluir = gerenciar.removernota(login.getId(), item.getId());
					
					if(excluir) {
						DefaultListModel model = (DefaultListModel) lista_notas.getModel();
						if (indice_nota_selecionada != -1) {
						model.remove(indice_nota_selecionada);
						}
						  
				        
					}else {
						JOptionPane.showMessageDialog(isto, "Erro ao excluir a nota selecionada\nConsulte o administrado!");
					}
		          	}
				else
				{
					
					
				}
			}
		});
		btnExcluirNota.setBounds(131, 546, 90, 28);
		painelPrincipal.add(btnExcluirNota);
		
	
		
		
		

		this.setLocationRelativeTo(janela_pai);

		
		
		
	}
	
	public void getDadosGlobais() {
		//gerenciador de log
				DadosGlobais dados = DadosGlobais.getInstance();
				 GerenciadorLog = dados.getGerenciadorLog();
				 configs_globais = dados.getConfigs_globais();
				 
				 //usuario logado
				  login = dados.getLogin();
		
	}

	public void atualizarLista() {
		listModelGlobal.clear();
		  GerenciarBancoNotas gerenciar = new GerenciarBancoNotas();
		   ArrayList<CadastroNota> notas = gerenciar.getnotas(login.getId());
		 
		 for(CadastroNota not : notas) {
				
			 
		     listModelGlobal.addElement(not);
		}
	}
	
	 private void clickButtonAt(Point point)
	  {
	    int index = lista_notas.locationToIndex(point);
	    indice_nota_selecionada = index;
	    CadastroNota item = (CadastroNota) lista_notas.getModel().getElementAt(index);
	    
	    
	    
	    //lblNomeNota, lblTempoNotificacao,lblNotificar, lblLembrar , lblLembrarMeHora, lblLembrarMeData;
        lblNomeNota.setText(item.getNome());
        textAreaTexto.setText(item.getTexto());
        
        if(item.getNotificar() == 1) {
        	lblTempoNotificacao.setEnabled(true);
            lblTempoNotificacao.setVisible(true);
        	lblNotificar.setText("Notificar");
        	lblNotificar.setVisible(true);

        	String uni_tempo = "";
        	if(item.getUni_tempo() == 1) {
        		uni_tempo = "minutos";
        	}else if(item.getUni_tempo() == 2) {
        		uni_tempo = "horas";
        	}else {
        		uni_tempo = "dias";
        	}
        	
        	lblTempoNotificacao.setText("a cada " + item.getTempo_notificacao() + " " + uni_tempo);
        	
        	
        }
        else {
        	lblNotificar.setText("Não notificar");
        	lblNotificar.setVisible(true);
            lblTempoNotificacao.setEnabled(false);
            lblTempoNotificacao.setVisible(false);
            lblTempoNotificacao.setText("");
        }
        
        
        
        if(item.getLembrar() == 1) {
        	lblLembrarMeHora.setVisible(true);
        	lblLembrarMeData.setVisible(true);
        	lblLembrar.setVisible(true);
        	lblLembrar.setText("Lembrar-me");

        	DateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");

    		String strDateLembrete = "";
    		String strHoraLembrete = "";

    		

    			strDateLembrete = dateFormat1.format(item.getData_lembrete());
    			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    			strHoraLembrete  = item.getHora_lembrete().format(DateTimeFormatter.ofPattern("HH:mm"));
    		
            	lblLembrarMeData.setText("em " + strDateLembrete);
            	lblLembrarMeHora.setText("ás " + strHoraLembrete);


        	
        	
        }else {
        	lblLembrar.setText("Não Lembrar");
        	lblLembrar.setVisible(true);
        	lblLembrarMeHora.setVisible(false);
        	lblLembrarMeHora.setText("");
        	
        	lblLembrarMeData.setVisible(false);
        	lblLembrarMeData.setText("");

        }
	     
	   

	  }
	 
	 
	
	// During the JList initialisation...
	 
	public void setTelaPai(JDialog _tela_pai) {
		this.telaPai = _tela_pai;
	}
}
