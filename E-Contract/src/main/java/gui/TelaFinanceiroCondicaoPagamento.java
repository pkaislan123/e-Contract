package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import main.java.conexaoBanco.GerenciarBancoCondicaoPagamentos;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoCondicaoPagamentos;
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
import main.java.cadastros.CondicaoPagamento;
import main.java.cadastros.CondicaoPagamento;
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



public class TelaFinanceiroCondicaoPagamento extends JDialog {

	private final JPanel painelPrincipal = new JPanel();
    private JLabel lblTotalContratosConcluidos, lblTotalContratos, lblTotalContratosAbertos;
    private TelaFinanceiroCondicaoPagamento isto;
    private JDialog telaPai;
    private JTable tabela_condicoes_pagamento;
    private ArrayList<CondicaoPagamento> lista_condicoes_pagamento = new ArrayList<>();
	private CondicaoPagamentoTableModel modelo_condicoes_pagamento = new CondicaoPagamentoTableModel();

	public TelaFinanceiroCondicaoPagamento(int modo_operacao, int tela_retorno, Window janela_pai) {

		 isto = this;
		
		setResizable(true);
		setTitle("E-Contract - Condições de Pagamento");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1018, 708);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(new MigLayout("", "[89px,grow][][][][][][][][][][][][][][][][][][][][][][][][][]", "[23px][][][grow][55.00][][grow][][][][][][][][][][][][][][]"));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 102, 255));
		painelPrincipal.add(panel, "cell 0 0 26 3,grow");
		panel.setLayout(new MigLayout("", "[30px][46px][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]", "[14px]"));
		
		JLabel lblNewLabel = new JLabel("Condições de Pagamento");
		lblNewLabel.setAlignmentY(1.0f);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 32));
		lblNewLabel.setForeground(Color.WHITE);
		panel.add(lblNewLabel, "cell 1 0 26 1,alignx left,aligny bottom");
		
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
		
		
		
		tabela_condicoes_pagamento = new JTable(modelo_condicoes_pagamento);
		tabela_condicoes_pagamento.setOpaque(false);
		tabela_condicoes_pagamento.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		tabela_condicoes_pagamento.setRowHeight(30);
		
		JScrollPane scrollPane = new JScrollPane(tabela_condicoes_pagamento);
		scrollPane.getViewport().setBackground(Color.WHITE);
		scrollPane.setOpaque(true);
		scrollPane.setBackground(Color.WHITE);
		painelPrincipal.add(scrollPane, "cell 0 6 26 11,grow");
		
		JButton btnNewButton = new JButton("Cadastrar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				TelaFinanceiroCadastroCondicaoPagamento tela = new TelaFinanceiroCadastroCondicaoPagamento(0, null, isto);
				tela.setVisible(true);
			
			}
		});
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				TelaFinanceiroCadastroCondicaoPagamento tela = new TelaFinanceiroCadastroCondicaoPagamento(1, getCondicaoPagamentoSelecionado(), isto);
				tela.setVisible(true);


						
						
			}
		});
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (JOptionPane.showConfirmDialog(isto, 
			            "Deseja excluir a Condição de Pagamento?", "Excluir", 
			            JOptionPane.YES_NO_OPTION,
			            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					
						boolean exclusao = new GerenciarBancoCondicaoPagamentos().removerCondicaoPagamento(getCondicaoPagamentoSelecionado().getId_condicao_pagamento());
						if(exclusao) {
							JOptionPane.showMessageDialog(isto, "Cadastro Excluído");
						}else {
							JOptionPane.showMessageDialog(isto, "Erro ao excluir\nConsulte o administrador");

						}

						 pesquisar();
			        }
				
			}
		});
		painelPrincipal.add(btnExcluir, "cell 17 18");
		
		JButton btnSelecionar = new JButton("Selecionar");
		btnSelecionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(modo_operacao == 0) {
					if(tela_retorno == 1) {
						((TelaFinanceiroCadastroLancamento) janela_pai).setCondicaoPagamento(getCondicaoPagamentoSelecionado());
						isto.dispose();
					}
				}
			}
		});
		painelPrincipal.add(btnSelecionar, "cell 19 18");
		painelPrincipal.add(btnEditar, "cell 21 18");
		painelPrincipal.add(btnNewButton, "cell 23 18,growx,aligny top");
	
		
		
		
		pesquisar();
		this.setLocationRelativeTo(janela_pai);

		
		
		
	}
	
	public void setTelaPai(JDialog _tela_pai) {
		this.telaPai = _tela_pai;
	}
	
	public void pesquisar() {
		GerenciarBancoCondicaoPagamentos gerenciar = new GerenciarBancoCondicaoPagamentos();
		lista_condicoes_pagamento.clear();
		modelo_condicoes_pagamento.onRemoveAll();
	
		lista_condicoes_pagamento = gerenciar.getCondicaoPagamentos();
		for(CondicaoPagamento cc : lista_condicoes_pagamento) {
			modelo_condicoes_pagamento.onAdd(cc);
		}
	}
	
	
	public class CondicaoPagamentoTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int id = 0;
		private final int nome = 1;
		private final int numero_parcelas = 2;
		private final int intervalo = 3;
		private final int dia_recebimento = 4;
		private final int forma_pagamento = 5;

		List<Color> rowColours = Arrays.asList(Color.RED, Color.GREEN, Color.CYAN);

		private final String colunas[] = { "ID", "Nome", "Número de Parcelas", "Intervalo",
				"Dia Recebimento", "Forma de Pagamento"};
		private final ArrayList<CondicaoPagamento> dados = new ArrayList<>();// usamos como dados uma lista genérica de
																			// nfs

		public CondicaoPagamentoTableModel() {

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
			case numero_parcelas:
				return String.class;
			case intervalo:
				return String.class;
			case dia_recebimento:
				return String.class;
			case forma_pagamento:
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
			 CondicaoPagamento dado = dados.get(rowIndex);
			
			switch (columnIndex) {
			case id:
				return dado.getId_condicao_pagamento();
			case nome: {
				return dado.getNome_condicao_pagamento() + "";

			}
			case numero_parcelas:
				return dado.getNumero_parcelas() + "";
			case intervalo:
				return dado.getIntervalo() + "";
			case dia_recebimento:
				return dado.getDia_recebimento() + "";
			case forma_pagamento:{

				/*1 -> Dinheiro
				2 -> Cheque
				3 -> Cheque Pré
				4 -> Cartão Crédito
				5 -> Cartão Debito
				6 -> Prazo
				7 -> Vale Crédito
				8 -> Outro*/

				if(dado.getForma_pagamento() == 1) {
					return "Dinheiro";
				}else if(dado.getForma_pagamento() == 2) {
					return "Cheque";
				}
				else if(dado.getForma_pagamento() == 3) {
					return "Cheque Pré";
				}
				else if(dado.getForma_pagamento() == 4) {
					return "Cartão Crédito";
				}
				else if(dado.getForma_pagamento() == 5) {
					return "Cartão Debito";
				}
				else if(dado.getForma_pagamento() == 6) {
					return "Prazo";
				}
				else if(dado.getForma_pagamento() == 7) {
					return "Vale Crédito";
				}
				else if(dado.getForma_pagamento() == 8) {
					return "Outro";
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
			CondicaoPagamento ib = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public CondicaoPagamento getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(CondicaoPagamento dado) {
			return dados.indexOf(dado);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(CondicaoPagamento dado) {
			dados.add(dado);
			fireTableRowsInserted(indexOf(dado), indexOf(dado));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<CondicaoPagamento> dadosIn) {
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
		public void onRemove(CondicaoPagamento dado) {
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

		public CondicaoPagamento onGet(int row) {
			return dados.get(row);
		}
	}
	
	public CondicaoPagamento getCondicaoPagamentoSelecionado() {
		int indiceDaLinha = tabela_condicoes_pagamento.getSelectedRow();

		int id_cc_selecionado = Integer.parseInt(tabela_condicoes_pagamento.getValueAt(indiceDaLinha, 0).toString());
		GerenciarBancoCondicaoPagamentos gerenciar_cont = new GerenciarBancoCondicaoPagamentos();
		return gerenciar_cont.getCondicaoPagamento(id_cc_selecionado);
		
	}
}
