package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
import main.java.cadastros.InstituicaoBancaria;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.classesExtras.Endereco;
import main.java.classesExtras.RenderizadorContato;
import main.java.conexaoBanco.GerenciarBancoAditivos;
import main.java.conexaoBanco.GerenciarBancoCentroCustos;
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
import main.java.gui.TelaContratos.ContratoTableModel;
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
import main.java.relatoria.RelatorioContratoSimplificado;
import main.java.relatoria.RelatorioContratos;
import main.java.tratamento_proprio.Log;
import main.java.views_personalizadas.TelaEmEspera;
import main.java.views_personalizadas.TelaNotificacao;
import main.java.views_personalizadas.TelaNotificacaoSuperior;
import main.java.views_personalizadas.TelaNotificacaoSuperiorModoBusca;
import net.miginfocom.swing.MigLayout;
import outros.ValidaCNPJ;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroNuvem;
import main.java.cadastros.CadastroPontuacao;
import main.java.cadastros.CadastroZapMessenger;
import main.java.cadastros.CentroCusto;
import main.java.outros.DadosGlobais;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroGrupo;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoGrupos;
import main.java.conexaoBanco.GerenciarBancoInformativo;
import main.java.conexaoBanco.GerenciarBancoInstituicaoBancaria;
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

import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;



public class TelaFinanceiroCentroCusto extends JDialog {

	private final JPanel painelPrincipal = new JPanel();
    private JLabel lblTotalContratosConcluidos, lblTotalContratos, lblTotalContratosAbertos;
    private TelaFinanceiroCentroCusto isto;
    private JDialog telaPai;
    private JTable tabela_centros_custo;
    private ArrayList<CentroCusto> lista_centros_custo = new ArrayList<>();
	private IBTableModel modelo_centros_custo = new IBTableModel();

	public TelaFinanceiroCentroCusto(int modo_operacao, int tela_retorno, Window janela_pai) {

		 isto = this;
		
		setResizable(true);
		setTitle("E-Contract - Centros de Custo");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1018, 708);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(new MigLayout("", "[89px,grow][][][][][][][][][][][][][][][][][][][][][][][][][grow]", "[23px][][][grow][55.00][][grow][][][][][][][][][][][][][][grow]"));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 102, 255));
		painelPrincipal.add(panel, "cell 0 0 26 2,grow");
		panel.setLayout(new MigLayout("", "[30px][46px][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]", "[14px]"));
		
		JLabel lblNewLabel = new JLabel("Centros de Custo");
		lblNewLabel.setBackground(new Color(0, 102, 255));
		lblNewLabel.setAlignmentY(1.0f);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 40));
		lblNewLabel.setForeground(Color.WHITE);
		panel.add(lblNewLabel, "cell 1 0 26 1,alignx left,aligny bottom");
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(TelaFinanceiroCentroCusto.class.getResource("/imagens/centro_custo_128_128.png")));
		panel.add(lblNewLabel_1, "cell 28 0 3 1,alignx right");
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBackground(Color.WHITE);
		painelPrincipal.add(panel_1, "cell 0 3 26 2,growx,aligny center");
		panel_1.setLayout(new MigLayout("", "[][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]", "[]"));
		
		JButton btnNewButton_1 = new JButton("Refazer Pesquisa");
		btnNewButton_1.setBackground(new Color(0, 0, 51));
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisar();
			}
		});
		panel_1.add(btnNewButton_1, "cell 30 0,alignx right");
		
		
		
		tabela_centros_custo = new JTable(modelo_centros_custo);
		tabela_centros_custo.setOpaque(false);
		tabela_centros_custo.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		tabela_centros_custo.setRowHeight(30);
		
		JScrollPane scrollPane = new JScrollPane(tabela_centros_custo);
		scrollPane.getViewport().setBackground(Color.WHITE);
		scrollPane.setOpaque(true);
		scrollPane.setBackground(Color.WHITE);
		painelPrincipal.add(scrollPane, "cell 0 6 26 14,grow");
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		painelPrincipal.add(panel_2, "cell 0 20 26 1,alignx right,growy");
		panel_2.setLayout(new MigLayout("", "[][][][][][][][]", "[]"));
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.setBackground(new Color(204, 0, 0));
		btnExcluir.setForeground(Color.WHITE);
		btnExcluir.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_2.add(btnExcluir, "cell 4 0,alignx right");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (JOptionPane.showConfirmDialog(isto, 
			            "Deseja excluir o Centro de Custo?", "Excluir", 
			            JOptionPane.YES_NO_OPTION,
			            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					
						boolean exclusao = new GerenciarBancoCentroCustos().removerCentroCusto(getCentroCustoSelecionado().getId_centro_custo());
						if(exclusao) {
							JOptionPane.showMessageDialog(isto, "Cadastro Excluído");
						}else {
							JOptionPane.showMessageDialog(isto, "Erro ao excluir\nConsulte o administrador");

						}

						 pesquisar();
			        }
				
			}
		});
		
		JButton btnSelecionar = new JButton("Selecionar");
		btnSelecionar.setBackground(new Color(0, 0, 102));
		btnSelecionar.setForeground(Color.WHITE);
		btnSelecionar.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnSelecionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(modo_operacao == 0) {
					if(tela_retorno == 1) {
						((TelaFinanceiroCadastroLancamento) janela_pai).setCentroCusto(getCentroCustoSelecionado());
						isto.dispose();
					}
				}
			}
		});
		panel_2.add(btnSelecionar, "cell 5 0,alignx right");
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.setBackground(new Color(255, 153, 0));
		btnEditar.setForeground(Color.WHITE);
		btnEditar.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_2.add(btnEditar, "cell 6 0");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				TelaFinanceiroCadastroCentroCusto tela = new TelaFinanceiroCadastroCentroCusto(1, getCentroCustoSelecionado(), isto);
				tela.setVisible(true);


						
						
			}
		});
		
		JButton btnNewButton = new JButton("Cadastrar");
		btnNewButton.setBackground(new Color(0, 51, 0));
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_2.add(btnNewButton, "cell 7 0");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				TelaFinanceiroCadastroCentroCusto tela = new TelaFinanceiroCadastroCentroCusto(0, null, isto);
				tela.setVisible(true);
			
			}
		});
	
		
		
		
		pesquisar();
		this.setLocationRelativeTo(janela_pai);

		
		
		
	}
	
	public void setTelaPai(JDialog _tela_pai) {
		this.telaPai = _tela_pai;
	}
	
	public void pesquisar() {
		GerenciarBancoCentroCustos gerenciar = new GerenciarBancoCentroCustos();
		lista_centros_custo.clear();
		modelo_centros_custo.onRemoveAll();
		
		lista_centros_custo = gerenciar.getCentroCustos();
		for(CentroCusto cc : lista_centros_custo) {
			modelo_centros_custo.onAdd(cc);
		}
	}
	
	
	public class IBTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int id = 0;
		private final int nome = 1;
		private final int cliente = 2;
	


		List<Color> rowColours = Arrays.asList(Color.RED, Color.GREEN, Color.CYAN);

		private final String colunas[] = { "ID", "Nome", "Cliente" };
		private final ArrayList<CentroCusto> dados = new ArrayList<>();// usamos como dados uma lista genérica de
																			// nfs

		public IBTableModel() {

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
			case id:
				return Integer.class;
			case nome:
				return String.class;
			case cliente:
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
			CadastroCliente cliente_selecionado = null;
			// pega o dados corrente da linha
			 CentroCusto dado = dados.get(rowIndex);
			if(dado.getId_cliente() > 0) {
				cliente_selecionado = new GerenciarBancoClientes().getCliente(dado.getId_cliente());
				if(cliente_selecionado != null) {
					
					
			
			  }
			}
			switch (columnIndex) {
			case id:
				return dado.getId_centro_custo();
			case nome: {
				return dado.getNome_centro_custo();

			}
			case cliente: {
				if(cliente_selecionado != null) {
				if (cliente_selecionado.getTipo_pessoa() == 0) // pessoa fisica
				 return cliente_selecionado.getNome_empresarial();
				else
					return cliente_selecionado.getNome_fantaia();
				}
			}
			
			
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
			CentroCusto ib = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public CentroCusto getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(CentroCusto dado) {
			return dados.indexOf(dado);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(CentroCusto dado) {
			dados.add(dado);
			fireTableRowsInserted(indexOf(dado), indexOf(dado));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<CentroCusto> dadosIn) {
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
		public void onRemove(CentroCusto dado) {
			int indexBefore = indexOf(dado);// pega o indice antes de apagar
			dados.remove(dado);
			fireTableRowsDeleted(indexBefore, indexBefore);
		}

		/**
		 * remove todos registros da lista
		 */
		public void onRemoveAll() {
			dados.clear();
			fireTableDataChanged();
		}

		public CentroCusto onGet(int row) {
			return dados.get(row);
		}
	}
	
	public CentroCusto getCentroCustoSelecionado() {
		int indiceDaLinha = tabela_centros_custo.getSelectedRow();

		int id_cc_selecionado = Integer.parseInt(tabela_centros_custo.getValueAt(indiceDaLinha, 0).toString());
		GerenciarBancoCentroCustos gerenciar_cont = new GerenciarBancoCentroCustos();
		return gerenciar_cont.getCentroCusto(id_cc_selecionado);
		
	}
}
