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



public class TelaFinanceiroInstituicaoBancaria extends JDialog {

	private final JPanel painelPrincipal = new JPanel();
    private JLabel lblTotalContratosConcluidos, lblTotalContratos, lblTotalContratosAbertos;
    private TelaFinanceiroInstituicaoBancaria isto;
    private JDialog telaPai;
    private JTable tabela_ibs;
    private ArrayList<InstituicaoBancaria> lista_ibs = new ArrayList<>();
	private IBTableModel modelo_ibs = new IBTableModel();

	public TelaFinanceiroInstituicaoBancaria(int modo_operacao, int retorno_tela, Window janela_pai) {

		 isto = this;
		
		setResizable(true);
		setTitle("E-Contract - Instituições Bancárias");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1018, 708);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(new MigLayout("", "[89px,grow][][][][][][][][][][][][][][][][][][][][][][][][][grow][]", "[23px,grow][][][grow][55.00][][grow][][][][][][][][][][][][][][grow]"));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 102, 255));
		painelPrincipal.add(panel, "cell 0 0 27 3,grow");
		panel.setLayout(new MigLayout("", "[46px]", "[14px]"));
		
		JLabel lblNewLabel = new JLabel("Instituições Bancárias");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setForeground(Color.WHITE);
		panel.add(lblNewLabel, "cell 0 0,alignx left,aligny center");
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBackground(Color.WHITE);
		painelPrincipal.add(panel_1, "cell 0 3 27 2,alignx right,aligny center");
		
		JButton btnNewButton_1 = new JButton("Refazer Pesquisa");
		btnNewButton_1.setBackground(new Color(0, 0, 51));
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisar();
			}
		});
		panel_1.setLayout(new MigLayout("", "[126px]", "[28px]"));
		panel_1.add(btnNewButton_1, "cell 0 0,alignx left,aligny top");
		
		
		
		tabela_ibs = new JTable(modelo_ibs);
		tabela_ibs.setOpaque(false);
		tabela_ibs.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		tabela_ibs.setRowHeight(30);
		
		JScrollPane scrollPane = new JScrollPane(tabela_ibs);
		scrollPane.getViewport().setBackground(Color.WHITE);
		scrollPane.setOpaque(true);
		scrollPane.setBackground(Color.WHITE);
		painelPrincipal.add(scrollPane, "cell 0 6 27 14,grow");
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		painelPrincipal.add(panel_2, "cell 19 20 8 1,alignx right,growy");
		panel_2.setLayout(new MigLayout("", "[][][][][][][][][][][][]", "[]"));
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.setBackground(new Color(153, 0, 0));
		btnExcluir.setForeground(Color.WHITE);
		btnExcluir.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_2.add(btnExcluir, "cell 8 0");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (JOptionPane.showConfirmDialog(isto, 
			            "Deseja excluir a Instituição Bancaria?", "Excluir", 
			            JOptionPane.YES_NO_OPTION,
			            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					
						boolean exclusao = new GerenciarBancoInstituicaoBancaria().removerInstituicaoBancaria(getIBSelecionado().getId_instituicao_bancaria());
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
		btnSelecionar.setBackground(new Color(0, 0, 51));
		btnSelecionar.setForeground(Color.WHITE);
		btnSelecionar.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_2.add(btnSelecionar, "cell 9 0");
		btnSelecionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(modo_operacao == 0) {
					if(retorno_tela == 1) {
						((TelaFinanceiroCadastroPagamento) janela_pai).setInstituicaoBancaria(getIBSelecionado());
						isto.dispose();
					}else if(retorno_tela == 2) {
						if(janela_pai instanceof TelaFinanceiroCadastroPagamento) {
							((TelaFinanceiroCadastroPagamento) janela_pai).setInstituicaoBancariaRecebedora(getIBSelecionado());

						}else if(janela_pai instanceof TelaFinanceiroCadastroPagamentoEmprestimo) {
							((TelaFinanceiroCadastroPagamentoEmprestimo) janela_pai).setInstituicaoBancariaRecebedora(getIBSelecionado());

						}
						isto.dispose();
					}
				}
			}
		});
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.setBackground(new Color(255, 153, 0));
		btnEditar.setForeground(Color.WHITE);
		btnEditar.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_2.add(btnEditar, "cell 10 0");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				TelaFinanceiroCadastroInstituicaoBancaria tela = new TelaFinanceiroCadastroInstituicaoBancaria(1, getIBSelecionado(), isto);
				tela.setVisible(true);


						
						
			}
		});
		
		JButton btnNewButton = new JButton("Cadastrar");
		btnNewButton.setBackground(new Color(0, 51, 0));
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_2.add(btnNewButton, "cell 11 0");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaFinanceiroCadastroInstituicaoBancaria tela = new TelaFinanceiroCadastroInstituicaoBancaria(0, null,isto);
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
		GerenciarBancoInstituicaoBancaria gerenciar = new GerenciarBancoInstituicaoBancaria();
		lista_ibs.clear();
		modelo_ibs.onRemoveAll();
		
		lista_ibs = gerenciar.getInstituicoesBancarias();
		for(InstituicaoBancaria ib : lista_ibs) {
			modelo_ibs.onAdd(ib);
		}
	}
	
	
	public class IBTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int id = 0;
		private final int nome = 1;
		private final int cliente = 2;
		private final int conta = 3;
	

		List<Color> rowColours = Arrays.asList(Color.RED, Color.GREEN, Color.CYAN);

		private final String colunas[] = { "ID", "Nome", "Cliente", "Conta" };
		private final ArrayList<InstituicaoBancaria> dados = new ArrayList<>();// usamos como dados uma lista genérica de
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
			case conta:
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
			Locale ptBr = new Locale("pt", "BR");
			NumberFormat z = NumberFormat.getNumberInstance();
			CadastroCliente cliente_selecionado = null;
			 ContaBancaria cc = null;
			// pega o dados corrente da linha
			InstituicaoBancaria ib = dados.get(rowIndex);
			if(ib.getId_cliente() > 0) {
				cliente_selecionado = new GerenciarBancoClientes().getCliente(ib.getId_cliente());
				if(cliente_selecionado != null) {
					
					
				if(ib.getId_conta() > 0) {
					    cc =  new GerenciarBancoClientes().getConta(ib.getId_conta());
				 	
					
				}
			  }
			}

			// retorna o valor da coluna
			switch (columnIndex) {
			case id:
				return ib.getId_instituicao_bancaria();
			case nome: {
				return ib.getNome_instituicao_bancaria().toUpperCase();

			}
			case cliente: {
				if(cliente_selecionado != null) {
				if (cliente_selecionado.getTipo_pessoa() == 0) // pessoa fisica
				 return cliente_selecionado.getNome_empresarial();
				else
					return cliente_selecionado.getNome_fantaia();
				}
			}
			case conta: {
				 if(cc != null) {
					return "Banco: " + cc.getBanco() + " Ag: " + cc.getAgencia() + " CC: " + cc.getConta();
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
			InstituicaoBancaria ib = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public InstituicaoBancaria getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(InstituicaoBancaria dado) {
			return dados.indexOf(dado);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(InstituicaoBancaria dado) {
			dados.add(dado);
			fireTableRowsInserted(indexOf(dado), indexOf(dado));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<InstituicaoBancaria> dadosIn) {
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
		public void onRemove(InstituicaoBancaria dado) {
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

		public InstituicaoBancaria onGet(int row) {
			return dados.get(row);
		}
	}
	
	public InstituicaoBancaria getIBSelecionado() {
		int indiceDaLinha = tabela_ibs.getSelectedRow();

		int id_ib_selecionada = Integer.parseInt(tabela_ibs.getValueAt(indiceDaLinha, 0).toString());
		GerenciarBancoInstituicaoBancaria gerenciar_cont = new GerenciarBancoInstituicaoBancaria();
		return gerenciar_cont.getInstituicaoBancaria(id_ib_selecionada);
		
	}
}
