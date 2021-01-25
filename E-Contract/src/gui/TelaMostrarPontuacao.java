package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
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
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;

import java.awt.GridLayout;
import java.awt.Point;
import java.awt.SystemColor;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import cadastros.CadastroCliente;
import cadastros.CadastroContrato;
import cadastros.CadastroLogin;
import cadastros.CadastroPontuacao;
import classesExtras.RenderizadorChat;
import classesExtras.RenderizadorPontuacao;
import conexaoBanco.GerenciarBancoContratos;
import conexaoBanco.GerenciarBancoPontuacao;
import keeptoo.KGradientPanel;

import javax.swing.border.LineBorder;
import javax.swing.JScrollPane;



public class TelaMostrarPontuacao extends JDialog {

	private final KGradientPanel painelPrincipal = new KGradientPanel();
    private JLabel lblTotalContratosConcluidos, lblTotalContratos, lblTotalContratosAbertos;
    private TelaMostrarPontuacao isto;
    private JDialog telaPai;
    private  JList<CadastroPontuacao> lista_pontuacao ;
    
    private JLabel lblNumeroAvaliacoes, lblCalculo,lblEstrelas;

	public TelaMostrarPontuacao(CadastroCliente cliente_local) {
		setModal(true);

		 isto = this;
		
		setResizable(true);
		setTitle("E-Contract - Pontuação");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 902, 507);
		painelPrincipal.kStartColor = Color.WHITE;
		painelPrincipal.kEndColor = Color.WHITE;
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);
		
		 lblNumeroAvaliacoes = new JLabel("Pontuação com base em X avaliações");
		lblNumeroAvaliacoes.setForeground(Color.BLACK);
		lblNumeroAvaliacoes.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNumeroAvaliacoes.setBounds(26, 43, 371, 14);
		painelPrincipal.add(lblNumeroAvaliacoes);
		
		JButton btnNewButton = new JButton("Sair");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isto.dispose();
			}
		});
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(new Color(102, 0, 0));
		btnNewButton.setBounds(819, 434, 50, 28);
		painelPrincipal.add(btnNewButton);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(204, 255, 153));
		panel.setBounds(26, 100, 843, 322);
		painelPrincipal.add(panel);
		panel.setLayout(null);
		
		 DefaultListModel<CadastroPontuacao>   listModelGlobal = new DefaultListModel<CadastroPontuacao>();
		 ArrayList<CadastroPontuacao>  avaliacoes = new  ArrayList<>();
		   GerenciarBancoPontuacao gerenciar = new GerenciarBancoPontuacao();
		  avaliacoes = gerenciar.getPontuacaoPorCliente(cliente_local.getId());
		 
		 for(CadastroPontuacao pontuacao : avaliacoes) {
				
			 
		     listModelGlobal.addElement(pontuacao);
		}
		 
		 RenderizadorPontuacao render = new RenderizadorPontuacao();

		 
		 lista_pontuacao = new JList<>();
			lista_pontuacao.setBackground(new Color(255, 255, 255));
			
			 lista_pontuacao.setCellRenderer(render);
			 lista_pontuacao.setModel(listModelGlobal);
			lista_pontuacao.addMouseListener(new MouseAdapter()
			    {
			      @Override
			      public void mouseClicked(MouseEvent event)
			      {
			        clickButtonAt(event.getPoint());
			      }
			    });

		  lblNumeroAvaliacoes.setText("Pontuação com base em " + avaliacoes.size() + " avaliações");
			
		
		JScrollPane scrollPane = new JScrollPane(lista_pontuacao);
		scrollPane.setBounds(6, 53, 831, 263);
		panel.add(scrollPane);
		
		
		  JPanel painel_msg = new JPanel();
		  painel_msg.setBounds(6, 6, 831, 47);
		  panel.add(painel_msg);
		  painel_msg.setBorder(new LineBorder(new Color(0, 0, 0)));
		  
		  painel_msg.setBackground(new Color(255, 255, 204));
		  painel_msg.setLayout(new MigLayout("", "[][][][][][grow][][][][][][][][][][]", "[grow]"));
		  
		  	
		  	
		  	
		  	JLabel lblCodigoContrato = new JLabel("    Contrato   ");
		  	lblCodigoContrato.setBackground(new Color(153, 204, 0));
		  	lblCodigoContrato.setFont(new Font("Tahoma", Font.BOLD, 14));
		  	painel_msg.add(lblCodigoContrato, "cell 0 0");
		  	
		  	JLabel lblCliente = new JLabel("      Cliente      ");
		  	lblCliente.setFont(new Font("Tahoma", Font.BOLD, 14));
		  	painel_msg.add(lblCliente, "cell 1 0");
		  	
		  	JLabel lblPontos = new JLabel("  Pontuação   ");
		  	lblPontos.setFont(new Font("Arial", Font.BOLD, 14));
		  	painel_msg.add(lblPontos, "cell 2 0");
		  	
		  	JLabel lblMotivo = new JLabel("                                Motivo                   ");
		  	lblMotivo.setFont(new Font("Arial", Font.BOLD, 14));
		  	painel_msg.add(lblMotivo, "cell 3 0");
		  	
		  	JLabel lblNewLabel = new JLabel("                Comentario      ");
		  	lblNewLabel.setFont(new Font("Arial", Font.BOLD, 14));
		  	painel_msg.add(lblNewLabel, "cell 4 0,aligny center");
		  	
		  	 lblEstrelas = new JLabel("");
		  	lblEstrelas.setBounds(563, 41, 170, 30);
		  	painelPrincipal.add(lblEstrelas);
		  	
		  	 lblCalculo = new JLabel("Total de Avaliações: X | Pontuação Total: X | Média: X");
		  	lblCalculo.setFont(new Font("SansSerif", Font.BOLD, 10));
		  	lblCalculo.setBounds(537, 72, 282, 16);
		  	painelPrincipal.add(lblCalculo);

		
      
		this.setLocationRelativeTo(null);

		
		
		
	}
	
	 private void clickButtonAt(Point point)
	  {
	    int index = lista_pontuacao.locationToIndex(point);
	    CadastroPontuacao item = (CadastroPontuacao) lista_pontuacao.getModel().getElementAt(index);
	    CadastroContrato contrato = new GerenciarBancoContratos().getContrato(item.getId_contrato());
	    
	    if(contrato.getSub_contrato() == 1) {
		    TelaVisaoGeralSubContrato tela = new TelaVisaoGeralSubContrato(contrato);
		    tela.setVisible(true);
	    }else {
	    TelaVisaoGeralContrato tela = new TelaVisaoGeralContrato(contrato);
	    tela.setVisible(true);
	    }

	  }
	 
	
	 
	public void setTelaPai(JDialog _tela_pai) {
		this.telaPai = _tela_pai;
	}
	
	
	 public void setInfoPontuacao(int id_cliente) {
		   
		  GerenciarBancoPontuacao gerenciar = new GerenciarBancoPontuacao();
		    ArrayList<CadastroPontuacao> pontos_global = gerenciar.getPontuacaoPorCliente(id_cliente);
		   
		   
		   int pontuacao_total = 0;
		   int num_pontuacao = pontos_global.size();
		   int num_estrelas;
		   
		   if(pontos_global.size() > 0) {
			   for(CadastroPontuacao ponto : pontos_global) {
				   pontuacao_total  = pontuacao_total +  ponto.getPontos();
			   }
			
			    num_estrelas = pontuacao_total / num_pontuacao;
			    
			    
			    URL url = getClass().getResource("/imagens/icone_" + num_estrelas + "_estrelas.png");
				ImageIcon img = new ImageIcon(url);
				lblEstrelas.setIcon(img);
				
				
				lblCalculo.setText("Total de avaliações: " + num_pontuacao + "| Pontuação Total: " + pontuacao_total + " | Média: " + (pontuacao_total / num_pontuacao));
			   
		   }else {
			   lblEstrelas.setText("Sem pontuação");
			   lblCalculo.setText("Sem pontuação");
		   }
		   
		   
	   }
}
