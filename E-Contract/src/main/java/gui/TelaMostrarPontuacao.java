package main.java.gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import net.miginfocom.swing.MigLayout;



import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GraphicsEnvironment;

import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;

import java.awt.GridLayout;
import java.awt.Point;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;


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
import main.java.cadastros.CadastroLogin.Mensagem;
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
import main.java.cadastros.DadosRecebimento;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.classesExtras.Endereco;
import main.java.classesExtras.RenderizadorContato;
import main.java.conexaoBanco.GerenciarBancoAditivos;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoDocumento;
import main.java.conexaoBanco.GerenciarBancoNotasFiscais;
import main.java.conexaoBanco.GerenciarBancoPadrao;
import main.java.conexaoBanco.GerenciarBancoPontuacao;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoRomaneios;
import main.java.conexaoBanco.GerenciarBancoSafras;
import main.java.conexaoBanco.GerenciarBancoTransferencias;
import main.java.conexaoBanco.GerenciarBancoTransferenciasCarga;
import main.java.conexoes.TesteConexao;
import main.java.graficos.GraficoLinha;
import main.java.graficos.JPanelGrafico;
import main.java.graficos.JPanelGraficoCarregamento;
import main.java.graficos.JPanelGraficoRecebimento;
import main.java.gui.TelaCadastroCliente;
import main.java.gui.TelaMain;
import main.java.gui.TelaRomaneios;
import main.java.manipular.ArquivoConfiguracoes;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.manipular.ConverterPdf;
import main.java.manipular.CopiarArquivo;
import main.java.manipular.EditarAditivo;
import main.java.manipular.EditarExcel;
import main.java.manipular.EditarWord;
import main.java.manipular.Email;
import main.java.manipular.GetDadosGlobais;
import main.java.manipular.ManipularArquivoTerceiros;
import main.java.manipular.ManipularNotasFiscais;
import main.java.manipular.ManipularRomaneios;
import main.java.manipular.ManipularTxt;
import main.java.manipular.MonitorarRomaneios;
import main.java.manipular.Nuvem;
import main.java.manipular.PorExtenso;
import main.java.manipular.Whatsapp;
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
import net.miginfocom.swing.MigLayout;
import outros.ValidaCNPj;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroNuvem;
import main.java.cadastros.CadastroPontuacao;
import main.java.cadastros.CadastroZapMessenger;
import main.java.outros.DadosGlobais;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroGrupo;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoGrupos;
import main.java.conexaoBanco.GerenciarBancoLogin;
import main.java.conexaoBanco.GerenciarBancoNotas;
import main.java.cadastros.CadastroProduto;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroSafra;
import main.java.classesExtras.CBLocalRetiradaPersonalizado;
import main.java.classesExtras.CBLocalRetiradaRenderPersonalizado;
import main.java.classesExtras.CBProdutoPersonalizado;
import main.java.classesExtras.CBProdutoRenderPersonalizado;
import main.java.classesExtras.ComboBoxContato;
import main.java.classesExtras.ComboBoxPersonalizado;
import main.java.classesExtras.ComboBoxRenderPersonalizado;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoSafras;
import keeptoo.KGradientPanel;

import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;



public class TelaMostrarPontuacao extends JDialog {

	private final KGradientPanel painelPrincipal = new KGradientPanel();
	private final KGradientPanel painelOdin = new KGradientPanel();

    private JLabel lblTotalContratosConcluidos, lblTotalContratos, lblTotalContratosAbertos;
    private TelaMostrarPontuacao isto;
    private JDialog telaPai;
    private CadastroCliente cliente_global;
	 ArrayList<CadastroPontuacao>  lista_pontuacao = new ArrayList<CadastroPontuacao>() ;

    private PontuacaoTableModel modelo_pontuacao = new PontuacaoTableModel();
    private JLabel lblNumeroAvaliacoes, lblCalculo,lblEstrelas;
    private JTable table;
    private JPanel panel;
    private JPanel panel_1;
    private JPanel panel_2;

	public TelaMostrarPontuacao(CadastroCliente cliente_local, Window janela_pai) {

		 isto = this;
		 cliente_global = cliente_local;
		setResizable(true);
		setTitle("E-Contract - Pontuação");


		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		System.out.println("Screen width = " + d.width);
		System.out.println("Screen height = " + d.height);
		
		// pega o tamanho da barra de tarefas
		Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
		java.awt.Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		int taskBarHeight = scrnSize.height - winSize.height;
		System.out.printf("Altura: %d\n", taskBarHeight);
		
		isto = this;
		setResizable(true);
		DadosGlobais dados = DadosGlobais.getInstance();
		
		DisplayMode display =  GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
		
		int display_x = display.getWidth();
		int display_y = display.getHeight();

		setBounds(0, 0, d.width, d.height - taskBarHeight);

		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		painelOdin.kStartColor = Color.WHITE;
		painelOdin.kEndColor = Color.WHITE;
		painelOdin.setBackground(new Color(255, 255, 255));
		painelOdin.setBorder(new EmptyBorder(5, 5, 5, 5));
		painelOdin.setLayout(new BorderLayout(0, 0));

		
		painelPrincipal.kStartColor = Color.WHITE;
		painelPrincipal.kEndColor = Color.WHITE;
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
			painelOdin.add(painelPrincipal);
		 painelPrincipal.setLayout(new MigLayout("", "[371px,grow][148px][282px][435px][80px,grow]", "[grow][30px,grow][16px][557px,grow][23px]"));
		 
		 panel = new JPanel();
		 painelPrincipal.add(panel, "cell 0 0 1 3,alignx center,aligny center");
		 panel.setLayout(new BorderLayout(0, 0));

		 lblNumeroAvaliacoes = new JLabel("Pontuação com base em X avaliações");
		 panel.add(lblNumeroAvaliacoes);
		lblNumeroAvaliacoes.setForeground(Color.BLACK);
		lblNumeroAvaliacoes.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		  	 lblEstrelas = new JLabel("");
		  	painelPrincipal.add(lblEstrelas, "cell 2 1,grow");
		  	 
		  	 panel_1 = new JPanel();
		  	 painelPrincipal.add(panel_1, "cell 3 1 2 1,alignx center,aligny center");
		  	 panel_1.setLayout(new BorderLayout(0, 0));
		  	
		  	 lblCalculo = new JLabel("Total de Avaliações: X | Pontuação Total: X | Média: X");
		  	 panel_1.add(lblCalculo);
		  	lblCalculo.setFont(new Font("SansSerif", Font.BOLD, 14));
		  	
		  	JButton btnNewButton = new JButton("Sair");
		  	btnNewButton.addActionListener(new ActionListener() {
		  		public void actionPerformed(ActionEvent e) {
		  			isto.dispose();
		  		}
		  	});
		  	
		  	panel_2 = new JPanel();
		  	painelPrincipal.add(panel_2, "cell 0 3 5 1,grow");
		  	panel_2.setLayout(new BorderLayout(0, 0));
		  	
		  	
		  	table = new JTable(modelo_pontuacao);
		  	table.addMouseListener(new MouseAdapter() {
		  		@Override
		  		public void mouseClicked(MouseEvent e) {
		  		  if(e.getClickCount() == 2){
						int indiceDaLinha = table.getSelectedRow();
		  		    CadastroPontuacao item = (CadastroPontuacao) lista_pontuacao.get(indiceDaLinha);
		  		    CadastroContrato contrato = new GerenciarBancoContratos().getContrato(item.getId_contrato());
		  		    
		  		    if(contrato.getSub_contrato() == 1) {
		  			    TelaVisaoGeralSubContrato tela = new TelaVisaoGeralSubContrato(contrato, null);
		  			    tela.setVisible(true);
		  		    }else {
		  		    TelaVisaoGeralContrato tela = new TelaVisaoGeralContrato(contrato, null);
		  		    tela.setVisible(true);
		  		    }

		  			 }
		  		}
		  	});

		  	table.getColumnModel().getColumn(0).setPreferredWidth(20);
		  	table.getColumnModel().getColumn(1).setPreferredWidth(100);
		  	table.getColumnModel().getColumn(2).setPreferredWidth(120);
		  	table.getColumnModel().getColumn(3).setPreferredWidth(50);
		  	table.getColumnModel().getColumn(4).setPreferredWidth(500);
		  	table.getColumnModel().getColumn(5).setPreferredWidth(500);
			

		table.setRowHeight(30);
		
				JScrollPane scrollPane = new JScrollPane(table);
				panel_2.add(scrollPane);
		  	btnNewButton.setForeground(Color.WHITE);
		  	btnNewButton.setBackground(new Color(102, 0, 0));
		  	painelPrincipal.add(btnNewButton, "cell 4 4,grow");

		  	setContentPane(painelOdin);
			this.setResizable(true);
			this.setLocationRelativeTo(janela_pai);
		
	}
	
	
	public void pesquisar_pontuacao() {
		  GerenciarBancoPontuacao gerenciar = new GerenciarBancoPontuacao();
			 ArrayList<CadastroPontuacao>  avaliacoes = gerenciar.getPontuacaoPorCliente(cliente_global.getId());
		 
		 for(CadastroPontuacao pontuacao : avaliacoes) {
				modelo_pontuacao.onAdd(pontuacao);
				lista_pontuacao.add(pontuacao);
			 
		}
		 
		  lblNumeroAvaliacoes.setText("Pontuação com base em " + avaliacoes.size() + " avaliações");

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
	 
	 
	 public static class PontuacaoTableModel extends AbstractTableModel {

			// constantes p/identificar colunas
			private final int id_pontuacao = 0;
			private final int contrato = 1;

			private final int cliente = 2;
			private final int pontos = 3;
			private final int motivo = 4;

			private final int comentario = 5;
		

			private final String colunas[] = { "ID", "CONTRATO", "CLIENTE:", "PONTOS:", "MOTIVO:", "COMENTARIO:" };
			private final ArrayList<CadastroPontuacao> dados = new ArrayList<>();// usamos como dados uma lista genérica de
																				// nfs

			public PontuacaoTableModel() {

			}

			@Override
			public int getColumnCount() {
				// retorna o total de colunas
				return colunas.length;
			}

			@Override
			public int getRowCount() {
				// retorna o total de linhas na tabela
				return dados.size();
			}

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				// retorna o tipo de dado, para cada coluna
				switch (columnIndex) {
				case id_pontuacao:
					return int.class;
				case contrato:
					return String.class;
				case cliente:
					return String.class;
				case pontos:
					return String.class;
				case motivo:
					return String.class;
				case comentario:
					return String.class;
			

				default:
					throw new IndexOutOfBoundsException("Coluna Inválida!!!");
				}
			}

			@Override
			public String getColumnName(int columnIndex) {
				return colunas[columnIndex];
			}

			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				// retorna o valor conforme a coluna e linha

				// pega o dados corrente da linha
				CadastroPontuacao pontuacao = dados.get(rowIndex);
			

				// retorna o valor da coluna
				switch (columnIndex) {
				case id_pontuacao:
					return pontuacao.getId_pontuacao();
				case contrato:{
					CadastroContrato contrato = new GerenciarBancoContratos().getContrato(pontuacao.getId_contrato());
			        return contrato.getCodigo();
				} 
				case cliente:
					{
						CadastroCliente cliente = new GerenciarBancoClientes().getCliente(pontuacao.getId_cliente());
						if(cliente.getTipo_pessoa() == 0)
							return cliente.getNome_empresarial();
						else
							return cliente.getNome_fantaia();
					}
				case pontos:
					return pontuacao.getPontos();
				case motivo:{
					String motivo = "";
					int value = pontuacao.getMotivo();
					if(value == 0) {
						motivo = "Conclui o contrato corretamente!";
					}else if(value == 1){
						motivo = "Não poderá arcar com a quantidade de sacos do contrato";

					}else if(value == 3){
						motivo = "Renegociou o contrato com a parte ou intermediario";

					}else if(value == 4){
						motivo = "Renegociou o contrato através de aditivo contratual";

					}
					return motivo;

				}
				case comentario:
					return pontuacao.getDescricao();
			
				default:
					throw new IndexOutOfBoundsException("Coluna Inválida!!!");
				}
			}

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				// metodo identifica qual coluna é editavel

				// só iremos editar a coluna BENEFICIO,
				// que será um checkbox por ser boolean

				return false;
			}

			@Override
			public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
				CadastroPontuacao nota = dados.get(rowIndex);

			}

			// Métodos abaixo são para manipulação de dados

			/**
			 * retorna o valor da linha indicada
			 * 
			 * @param rowIndex
			 * @return
			 */
			public CadastroPontuacao getValue(int rowIndex) {
				return dados.get(rowIndex);
			}

			/**
			 * retorna o indice do objeto
			 * 
			 * @param empregado
			 * @return
			 */
			public int indexOf(CadastroPontuacao nota) {
				return dados.indexOf(nota);
			}

			/**
			 * add um empregado á lista
			 * 
			 * @param empregado
			 */
			public void onAdd(CadastroPontuacao nota) {
				dados.add(nota);
				fireTableRowsInserted(indexOf(nota), indexOf(nota));
			}

			/**
			 * add uma lista de empregados
			 * 
			 * @param dadosIn
			 */
			public void onAddAll(ArrayList<CadastroPontuacao> dadosIn) {
				dados.addAll(dadosIn);
				fireTableDataChanged();
			}

			/**
			 * remove um registro da lista, através do indice
			 * 
			 * @param rowIndex
			 */
			public void onRemove(int rowIndex) {
				dados.remove(rowIndex);
				fireTableRowsDeleted(rowIndex, rowIndex);
			}

			/**
			 * remove um registro da lista, através do objeto
			 * 
			 * @param empregado
			 */
			public void onRemove(CadastroPontuacao nota) {
				int indexBefore = indexOf(nota);// pega o indice antes de apagar
				dados.remove(nota);
				fireTableRowsDeleted(indexBefore, indexBefore);
			}

			/**
			 * remove todos registros da lista
			 */
			public void onRemoveAll() {
				dados.clear();
				fireTableDataChanged();
			}

		}
}
