package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.freixas.jcalendar.DateEvent;
import org.freixas.jcalendar.DateListener;
import org.freixas.jcalendar.JCalendar;
import org.freixas.jcalendar.JCalendarCombo;
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
import main.java.cadastros.CadastroAditivo;
import main.java.cadastros.CadastroAviso;
import main.java.cadastros.CadastroBaseArquivos;
import main.java.cadastros.CadastroBaseDados;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroContrato.CadastroPagamento;
import main.java.cadastros.CadastroContrato.CadastroPagamentoContratual;
import main.java.cadastros.CadastroContrato.CadastroTarefa;
import main.java.cadastros.CadastroContrato.Recebimento;
import main.java.cadastros.CadastroDocumento;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroModelo;
import main.java.cadastros.CadastroNFe;
import main.java.cadastros.CadastroNota;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroRomaneio;
import main.java.cadastros.CadastroSafra;
import main.java.cadastros.ContaBancaria;
import main.java.cadastros.Contato;
import main.java.cadastros.DadosCarregamento;
import main.java.cadastros.DadosContratos;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.classesExtras.Endereco;
import main.java.conexaoBanco.GerenciarBancoAditivos;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoDocumento;
import main.java.conexaoBanco.GerenciarBancoNotasFiscais;
import main.java.conexaoBanco.GerenciarBancoPadrao;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoRomaneios;
import main.java.conexaoBanco.GerenciarBancoSafras;
import main.java.conexaoBanco.GerenciarBancoTransferencias;
import main.java.conexaoBanco.GerenciarBancoTransferenciasCarga;
import main.java.conexoes.TesteConexao;
import main.java.graficos.GraficoLinha;
import main.java.graficos.JPanelGrafico;
import main.java.graficos.JPanelGraficoCarregamento;
import main.java.gui.TelaCadastroCliente;
import main.java.gui.TelaMain;
import main.java.gui.TelaRomaneios;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.manipular.ConverterPdf;
import main.java.manipular.CopiarArquivo;
import main.java.manipular.EditarAditivo;
import main.java.manipular.GetDadosGlobais;
import main.java.manipular.ManipularArquivoTerceiros;
import main.java.manipular.ManipularNotasFiscais;
import main.java.manipular.ManipularRomaneios;
import main.java.manipular.ManipularTxt;
import main.java.outros.BaixarNotasFiscais;
import main.java.outros.BuscarCep;
import main.java.outros.DadosGlobais;
import main.java.outros.GetData;
import main.java.outros.GetHttp;
import main.java.outros.GetSintegra;
import main.java.outros.JPanelBackground;
import main.java.outros.JPanelTransparent;
import main.java.outros.JTextFieldPersonalizado;
import main.java.outros.MyFileVisitor;
import main.java.outros.ReproduzirAudio;
import main.java.outros.TratarDados;
import main.java.relatoria.RelatorioContratoComprador;
import main.java.relatoria.RelatorioContratoRecebimentoSimplificado;
import main.java.relatoria.RelatorioContratos;
import main.java.tratamento_proprio.Log;
import main.java.views_personalizadas.TelaEmEspera;
import main.java.views_personalizadas.TelaNotificacao;
import main.java.views_personalizadas.TelaNotificacaoSuperior;
import main.java.views_personalizadas.TelaNotificacaoSuperiorModoBusca;
import outros.ValidaCNPJ;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroNuvem;
import main.java.cadastros.CadastroZapMessenger;
import main.java.outros.DadosGlobais;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroGrupo;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoGrupos;
import main.java.conexaoBanco.GerenciarBancoNotas;
import main.java.cadastros.CadastroProduto;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroSafra;
import main.java.classesExtras.CBProdutoPersonalizado;
import main.java.classesExtras.CBProdutoRenderPersonalizado;
import main.java.classesExtras.ComboBoxPersonalizado;
import main.java.classesExtras.ComboBoxRenderPersonalizado;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoSafras;
import javax.swing.border.LineBorder;



public class TelaDefinirTempo extends JDialog {

	private final JPanel painelPrincipal = new JPanel();
    private TelaDefinirTempo isto;
    private JInternalFrame telaPai;
    private JLabel lblDataSelecionada;
    private Calendar data_selecionada;

	public TelaDefinirTempo(Window janela_pai, Calendar tempo) {
		 setModal(true);

		 isto = this;
		
		setResizable(true);
	
		Locale.setDefault(new Locale("pt", "BR"));
		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 562, 391);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);
	
		
		
		

		 MyDateListener listener = new MyDateListener();

		    // Display date and time using the default calendar and locale.
		    // Display today's date at the bottom.

		
		 
		 JCalendar calendar1 =
			new JCalendar( Calendar.getInstance(new Locale("pt", "BR")),
				    new Locale("pt", "BR"),
			    JCalendar.DISPLAY_DATE | JCalendar.DISPLAY_TIME,
			    true, "HH:mm");
		    calendar1.setBounds(0, 11, 555, 333);
		    calendar1.addDateListener(listener);

		    // Set fonts rather than using defaults

		    calendar1.setTitleFont(new Font("Serif", Font.BOLD|Font.ITALIC, 24));
		    calendar1.setDayOfWeekFont(new Font("SansSerif", Font.ITALIC, 12));
		    calendar1.setDayFont(new Font("SansSerif", Font.BOLD, 16));
		    calendar1.setTimeFont(new Font("DialogInput", Font.PLAIN, 10));
		    calendar1.setTodayFont(new Font("Dialog", Font.PLAIN, 14));
		    
		    if(tempo != null) {
		    	calendar1.setDisplayDate(tempo.getTime());	
		    }

		   
		    JPanel painelLembrete = new JPanel();
			painelLembrete.setBounds(0, 0, 555, 344);
			painelLembrete.setLayout(null);
			painelLembrete.add(calendar1);

			
			painelPrincipal.add(painelLembrete);
			
			JButton btnNewButton = new JButton("Ok");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					((TelaCriarNota) telaPai).setData(data_selecionada);
					isto.dispose();
				}
			});
			btnNewButton.setBounds(466, 355, 89, 23);
			painelPrincipal.add(btnNewButton);
			
			JButton btnCancelar = new JButton("Cancelar");
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					isto.dispose();
				}
			});
			btnCancelar.setBounds(367, 355, 89, 23);
			painelPrincipal.add(btnCancelar);
			
			 lblDataSelecionada = new JLabel("Data");
			 lblDataSelecionada.setFont(new Font("Tahoma", Font.PLAIN, 18));
			 lblDataSelecionada.setForeground(new Color(0, 0, 128));
			lblDataSelecionada.setBounds(10, 355, 347, 22);
			painelPrincipal.add(lblDataSelecionada);
		   
        this.setUndecorated(true);
		    
		this.setLocationRelativeTo(janela_pai);

		
		
		
	}
	private class MyDateListener implements DateListener {

		public void dateChanged(DateEvent e) {
			Calendar c = e.getSelectedDate();
			if (c != null) {
				System.out.println(c.getTime());
				data_selecionada = c;
				String strDateLembrete = "";
				
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

				 strDateLembrete = dateFormat.format(c.getTime());
				 lblDataSelecionada.setText(strDateLembrete);

			} else {
				System.out.println("No time selected.");
				lblDataSelecionada.setText("Nenhum data selecioanda");

			}
		}
		
		
	}
	public void setTelaPai(JInternalFrame _tela_pai) {
		this.telaPai = _tela_pai;
	}
	
	
}
