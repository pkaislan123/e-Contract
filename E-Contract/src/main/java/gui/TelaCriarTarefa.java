package main.java.gui;

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



import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

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




import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import java.awt.Component;
import java.awt.DisplayMode;

import javax.swing.Box;

import main.java.cadastros.CadastroAditivo;
import main.java.cadastros.CadastroAviso;
import main.java.cadastros.CadastroBaseArquivos;
import main.java.cadastros.CadastroBaseDados;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroContrato.CadastroPagamento;
import main.java.cadastros.CadastroContrato.CadastroPagamentoContratual;
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
import outros.ValidaCNPj;
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

public class TelaCriarTarefa extends JDialog {
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private final JPanel painelPrincipal = new JPanel();
	private TelaCriarTarefa isto;
	private JTextField entNomeTarefa;
	private JTextField entDescricaoTarefa;
	private JTextField entData;
	private JTextField entHora;
    private JComboBox cBPrioridade, cBExecutor;
    private CadastroLogin executor;
    private JDialog telaPai;
    private JTextArea textAreaMensagem;
	private CadastroContrato contrato_local;
	 private JFrame telaPaiJFrame;
	 GraphicsEnvironment ge = GraphicsEnvironment .getLocalGraphicsEnvironment();
	 GraphicsDevice[] gds = ge.getScreenDevices();
	 
	 
    public TelaCriarTarefa(CadastroContrato contrato, Window janela_pai ) {
		//setModal(true);

		
		contrato_local = contrato;
		 isto = this;
		 getDadosGlobais();
		setTitle("E-Contract - Cria Nova Tarefa");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 687, 508);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nome:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(48, 31, 63, 17);
		painelPrincipal.add(lblNewLabel);
		
		JLabel lblDescrio = new JLabel("Descrição:");
		lblDescrio.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDescrio.setBounds(26, 77, 85, 26);
		painelPrincipal.add(lblDescrio);
		
		JLabel lblTarefa = new JLabel("Mensagem:");
		lblTarefa.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTarefa.setBounds(26, 144, 85, 26);
		painelPrincipal.add(lblTarefa);
		
		JLabel lblExecutor = new JLabel("Executor:");
		lblExecutor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblExecutor.setBounds(293, 251, 85, 26);
		painelPrincipal.add(lblExecutor);
		
		JLabel lblHora = new JLabel("Hora:");
		lblHora.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblHora.setBounds(304, 341, 74, 26);
		painelPrincipal.add(lblHora);
		
		JLabel lblData = new JLabel("Data:");
		lblData.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblData.setBounds(53, 341, 74, 26);
		painelPrincipal.add(lblData);
		
		JLabel lblPrioridade = new JLabel("Prioridade:");
		lblPrioridade.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPrioridade.setBounds(26, 251, 85, 26);
		painelPrincipal.add(lblPrioridade);
		
		entNomeTarefa = new JTextField();
		entNomeTarefa.setBounds(100, 23, 441, 36);
		painelPrincipal.add(entNomeTarefa);
		entNomeTarefa.setColumns(10);
		
		entDescricaoTarefa = new JTextField();
		entDescricaoTarefa.setColumns(10);
		entDescricaoTarefa.setBounds(100, 74, 441, 36);
		painelPrincipal.add(entDescricaoTarefa);
		
		 textAreaMensagem = new JTextArea();
		 textAreaMensagem.setLineWrap(true);
		 textAreaMensagem.setWrapStyleWord(true);
		textAreaMensagem.setBorder(new LineBorder(new Color(0, 0, 0)));
		textAreaMensagem.setBounds(100, 133, 441, 100);
		painelPrincipal.add(textAreaMensagem);
		
		 cBPrioridade = new JComboBox();

		cBPrioridade.setBounds(100, 248, 182, 36);
		cBPrioridade.addItem("Imediata - Neste Momento");
		cBPrioridade.addItem("Urgente - Nesta Hora");
		cBPrioridade.addItem("Quanto Antes - Ainda Hoje");
		cBPrioridade.addItem("Média - Ainda essa semana");
		cBPrioridade.addItem("Leve - Ainda esse mês");



		painelPrincipal.add(cBPrioridade);
		
		entData = new JTextField();
		entData.setEditable(false);
		entData.setColumns(10);
		entData.setBounds(100, 338, 182, 36);
		painelPrincipal.add(entData);
		entData.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				String caracteres = "0987654321\b";// lista de caracters que não devem ser aceitos
				String texto = entData.getText();
				if (!caracteres.contains(evt.getKeyChar() + "")) {
					evt.consume();// aciona esse propriedade para eliminar a ação do evento
				} else {
					if (texto.length() == 2 && evt.getKeyChar() != '\b') {
						entData.setText(entData.getText().concat("/"));
					}
					if (texto.length() == 5 && evt.getKeyChar() != '\b') {
						entData.setText(entData.getText().concat("/"));
					}

					if (entData.getText().length() >= 10) {
						// if para saber se precisa verificar também o tamanho da string do campo
						// maior ou igual ao tamanho máximo, cancela e nao deixa inserir mais
						evt.consume();
						entData.setText(entData.getText().substring(0, 10));
					}

				}
			}
		});
		entData.setColumns(10);
		 cBExecutor = new JComboBox();
		cBExecutor.setBounds(359, 251, 182, 36);
		painelPrincipal.add(cBExecutor);
		
		entHora = new JTextField();
		entHora.setEditable(false);
		entHora.setColumns(10);
		entHora.setBounds(359, 336, 182, 36);
		painelPrincipal.add(entHora);
		
		entHora.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				String caracteres = "0987654321\b";// lista de caracters que não devem ser aceitos
				String texto = entHora.getText();
				if (!caracteres.contains(evt.getKeyChar() + "")) {
					evt.consume();// aciona esse propriedade para eliminar a ação do evento
				} else {
					if (texto.length() == 2 && evt.getKeyChar() != '\b') {
						entHora.setText(entHora.getText().concat(":"));
					}
					

					if (entHora.getText().length() >= 5) {
						// if para saber se precisa verificar também o tamanho da string do campo
						// maior ou igual ao tamanho máximo, cancela e nao deixa inserir mais
						evt.consume();
						entHora.setText(entHora.getText().substring(0, 5));
					}

				}
			}
		});
		
		JButton btnCriarTarefa = new JButton("Criar");
		btnCriarTarefa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String nome, descricao, mensagem, hora, data, prioridade;
				boolean criar = false;
				
				LocalTime localTime4  = LocalTime.now();
				 String hora_criacao =  localTime4.format(DateTimeFormatter.ofPattern("HH:mm"));
				 
				 String data_criacao   = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
       	  
				
				nome = entNomeTarefa.getText();
				descricao = entDescricaoTarefa.getText();
				mensagem = textAreaMensagem.getText();
				hora = entHora.getText();
				data = entData.getText();
				 prioridade = cBPrioridade.getSelectedItem().toString();
				int priority = -1;
				 
				 if(prioridade.equalsIgnoreCase("Imediata - Neste Momento")) {
					 priority = 1;
				 }else if(prioridade.equalsIgnoreCase("Urgente - Nesta Hora")) {
					 priority = 2;
				 }else if(prioridade.equalsIgnoreCase("Quanto Antes - Ainda Hoje")) {
					 priority = 3;
				 }else if(prioridade.equalsIgnoreCase("Média - Ainda essa semana")) {
					 priority = 4;
				 }else if(prioridade.equalsIgnoreCase("Leve - Ainda esse mês")) {
					 priority = 5;
				 }
				 
				 if(checkString(nome))
					 criar = true;
				 else
					 JOptionPane.showMessageDialog(isto, "Nome da Tarefa Invalido");
				
				 if(!criar) {
				 if(executor == null) {
					 criar = false;
					 JOptionPane.showMessageDialog(isto, "Selecione o usuario que ira executar a tarefa");
				 }
				 else {
					 criar = true;
				 }
				 }
				 
				  
		
				 
				 
				 
				 
				 if(criar) {
					 
					 CadastroContrato.CadastroTarefa nova_tarefa = new CadastroContrato.CadastroTarefa();
					 nova_tarefa.setNome_tarefa(nome);
					 nova_tarefa.setDescricao_tarefa(descricao);
					 nova_tarefa.setMensagem(mensagem);
					 nova_tarefa.setPrioridade(priority);
					 nova_tarefa.setHora(hora_criacao);
					 nova_tarefa.setData(data_criacao);
					 nova_tarefa.setCriador(login);
					 nova_tarefa.setExecutor(executor);
					 nova_tarefa.setStatus_tarefa(2);
					 nova_tarefa.setData_agendada(entData.getText());
					 nova_tarefa.setHora_agendada(entHora.getText());
					 
					 
					 
					 GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
					 ArrayList< CadastroContrato.CadastroTarefa > tarefas = new ArrayList<>();
					 tarefas.add(nova_tarefa);
					 boolean criado = gerenciar.inserirTarefas(contrato_local.getId(), tarefas);
					 if(criado) {
						 JOptionPane.showMessageDialog(isto, "Tarefa Criada com Sucesso");
						// ((TelaGerenciarContrato) telaPai).atualizarListaTarefas();
						 ((TelaGerenciarContrato) telaPaiJFrame).atualizarListaTarefas();
						 ((TelaGerenciarContrato) telaPaiJFrame).informar_atualizou();

						 
						 isto.dispose();
					 }else {
						 JOptionPane.showMessageDialog(isto, "Erro ao inserir tarefa no banco de dado\nConsulte o administrador");

					 }
					 
				
				 }else {
					 
				 }
				 
			
				 
				 
				
				
				
			}
		});
		btnCriarTarefa.setBounds(470, 414, 89, 23);
		painelPrincipal.add(btnCriarTarefa);
		
		JButton btnSelecionarExecutor = new JButton("Selecionar");
		btnSelecionarExecutor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaUsuarios tela = new TelaUsuarios(1,isto);
				tela.setTelaPai(isto);
				tela.setVisible(true);
			}
		});
		btnSelecionarExecutor.setBounds(551, 255, 89, 23);
		painelPrincipal.add(btnSelecionarExecutor);
		
		JCheckBox chckbxTempoManual = new JCheckBox("Tempo Manual");
		chckbxTempoManual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxTempoManual.isSelected()) {
					chckbxTempoManual.setSelected(true);
					entData.setEditable(true);
					entHora.setEditable(true);

				}else {
					chckbxTempoManual.setSelected(false);
					entData.setEditable(false);
					entHora.setEditable(false);
				}
			}
		});
		chckbxTempoManual.setBounds(133, 305, 128, 23);
		painelPrincipal.add(chckbxTempoManual);
		
		
		 cBPrioridade.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		           String itemSelecionado = cBPrioridade.getSelectedItem().toString();
		        
		           
		           if(itemSelecionado.equalsIgnoreCase("Imediata - Neste Momento")) {
		        	   LocalTime localTime4  = LocalTime.now().plusMinutes(15);
						 String hora_formatada =  localTime4.format(DateTimeFormatter.ofPattern("HH:mm"));
						 entHora.setText(hora_formatada);
						 
						 String strLocalDate2   = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                       entData.setText(strLocalDate2);
		        	  
		        	   
					 }else if(itemSelecionado.equalsIgnoreCase("Urgente - Nesta Hora")) {
						 LocalTime localTime4       = LocalTime.now().plusHours(1);
						 String hora_formatada=  localTime4.format(DateTimeFormatter.ofPattern("HH:mm"));
						 entHora.setText(hora_formatada);
						 
						 String strLocalDate2   = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                         entData.setText(strLocalDate2);
						 
					 }else if(itemSelecionado.equalsIgnoreCase("Quanto Antes - Ainda Hoje")) {
						 LocalTime localTime4       = LocalTime.now().plusHours(9);
						 String hora_formatada=  localTime4.format(DateTimeFormatter.ofPattern("HH:mm"));
						 entHora.setText(hora_formatada);
						 
						 String strLocalDate2   = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                         entData.setText(strLocalDate2);
						 
					 }else if(itemSelecionado.equalsIgnoreCase("Média - Ainda essa semana")) {
						 LocalTime localTime4   = LocalTime.now();
						 String hora_formatada =  localTime4.format(DateTimeFormatter.ofPattern("HH:mm"));
						 entHora.setText(hora_formatada);
						 
						 LocalDateTime localDateTime3 = LocalDateTime.now().plusDays(7);

						 String strLocalDate2  = localDateTime3.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                         entData.setText(strLocalDate2);
						 
					 }else if(itemSelecionado.equalsIgnoreCase("Leve - Ainda esse mês")) {
						 LocalTime localTime4       = LocalTime.now().plusHours(9);
						 String hora_formatada=  localTime4.format(DateTimeFormatter.ofPattern("HH:mm"));
						 entHora.setText(hora_formatada);
						 
						 LocalDateTime localDateTime3 = LocalDateTime.now().plusDays(30);

						 String strLocalDate2   = localDateTime3.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                         entData.setText(strLocalDate2);
						 
					 }
		 	}
		 });
		  LocalTime localTime4  = LocalTime.now().plusMinutes(15);
			 String hora_formatada =  localTime4.format(DateTimeFormatter.ofPattern("HH:mm"));
			 entHora.setText(hora_formatada);
			 
			 String strLocalDate2   = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        entData.setText(strLocalDate2);
		
		
	
		  
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
	
	public void setExecutor(CadastroLogin login) {
		this.executor = login;
		
		java.awt.EventQueue.invokeLater(new Runnable() { 
		    public void run() { 
		    	
		    	cBExecutor.removeAllItems();
		    	cBExecutor.updateUI();
		    	cBExecutor.repaint();
		    	
		    	String nome = "";
		    	
		  
		    	
		    	cBExecutor.addItem(executor.getNome() + " " + executor.getSobrenome());
		    	cBExecutor.updateUI();
		    	cBExecutor.repaint();
				   
			    } 
			}); 
	}
	
	public void getDadosGlobais() {
		//gerenciador de log
				DadosGlobais dados = DadosGlobais.getInstance();
				 GerenciadorLog = dados.getGerenciadorLog();
				 configs_globais = dados.getConfigs_globais();
				 
				 //usuario logado
				  login = dados.getLogin();
		
	}
	public void setTelaPai(JFrame tela_pai) {
		this.telaPaiJFrame = tela_pai;
	}
	public void setTelaPai(JDialog tela_pai) {
		this.telaPai = tela_pai;
	}	
}
