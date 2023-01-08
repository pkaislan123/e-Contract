
package main.java.gui_internal;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import main.java.cadastros.CondicaoPagamento;
import main.java.cadastros.FinanceiroPagamentoCompleto;
import main.java.cadastros.Lancamento;
import main.java.conexaoBanco.GerenciarBancoCondicaoPagamentos;
import main.java.conexaoBanco.GerenciarBancoFinanceiroPagamento;
import main.java.conexaoBanco.GerenciarBancoFinanceiroPagamentoEmprestimo;
import main.java.conexaoBanco.GerenciarBancoLancamento;
import main.java.gui.TelaFinanceiroGerenciarLancamento;
import main.java.outros.DadosGlobais;
import main.java.outros.JTextFieldPersonalizado;
import main.java.views_personalizadas.TelaEscolhaRelatorioPagamentos;
import net.miginfocom.swing.MigLayout;

public class TelaFinanceiroPagamentoInternal
extends JInternalFrame {
    private final JPanel painelPrinciapl = new JPanel();
    private TelaFinanceiroPagamentoInternal isto;
    private JTable tabela_pagamento;
    private ArrayList<FinanceiroPagamentoCompleto> lista_FinanceiroPagamentoCompletos = new ArrayList();
    private PagamentoTableModel modelo_pagamento = new PagamentoTableModel();
    private JDialog telaPai;
    private TableRowSorter<PagamentoTableModel> sorter;
    private JLabel lblNumTotalPagamentos;
    private JLabel entValorTotalPagamentoDespesas;
    private JLabel entValorTotalPagamentoReceitas;
    private JLabel entBalanco;
    private JLabel entValorTotalPagamentoTransferencia;
    private JLabel entBalancoEmprestimo;
    private JLabel entValorTotalPagamentoEmprestimoDespesas;
    private JLabel entValorTotalPagamentoEmprestimoReceitas;
    private JLabel entBalancoFinal;

    public TelaFinanceiroPagamentoInternal(int flag_tipo_tela, Window janela_pai) {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();
        System.out.println("Screen width = " + d.width);
        System.out.println("Screen height = " + d.height);
        Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        int taskBarHeight = scrnSize.height - winSize.height;
        System.out.printf("Altura: %d\n", taskBarHeight);
        this.isto = this;
        this.setResizable(true);
        DadosGlobais dados = DadosGlobais.getInstance();
        DisplayMode display = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
        int display_x = display.getWidth();
        int display_y = display.getHeight();
        this.setBounds(0, 0, 924, 728);
        this.painelPrinciapl.setBackground(Color.WHITE);
        this.setContentPane(this.painelPrinciapl);
        this.painelPrinciapl.setLayout((LayoutManager)new MigLayout("", "[][grow][]", "[][100px][grow][][]"));
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 102, 255));
        this.painelPrinciapl.add((Component)panel, "cell 0 0 3 1,grow");
        panel.setLayout((LayoutManager)new MigLayout("", "[269px][]", "[49px]"));
        JLabel lblNewLabel = new JLabel();
        lblNewLabel.setForeground(Color.WHITE);
        lblNewLabel.setFont(new Font("Tahoma", 1, 40));
        if (flag_tipo_tela == 1) {
            lblNewLabel.setText("Pagamentos de Receita");
        } else {
            lblNewLabel.setText("Pagamentos de Despesa");
        }
        panel.add((Component)lblNewLabel, "cell 0 0,alignx left,aligny top");
        FinanceiroPagamentoCompletosRender renderer = new FinanceiroPagamentoCompletosRender();
        this.tabela_pagamento = new JTable(this.modelo_pagamento);
        this.tabela_pagamento.setDefaultRenderer(Object.class, renderer);
        this.tabela_pagamento.getColumnModel().getColumn(0).setPreferredWidth(40);
        this.tabela_pagamento.getColumnModel().getColumn(1).setPreferredWidth(40);
        this.sorter = new TableRowSorter<PagamentoTableModel>(this.modelo_pagamento);
        this.tabela_pagamento.setRowSorter(this.sorter);
        this.tabela_pagamento.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(this.tabela_pagamento);
        this.painelPrinciapl.add((Component)scrollPane, "cell 0 1 3 2,grow");
        JPanel panel_5 = new JPanel();
        panel_5.setBackground(Color.WHITE);
        this.painelPrinciapl.add((Component)panel_5, "cell 0 3 3 2,grow");
        panel_5.setLayout((LayoutManager)new MigLayout("", "[189px][39px][189px][][87.00px][][][][][][][][][][][][][][][][][][]", "[][][18px][][][][][][][][][][]"));
        JLabel lblNewLabel_1_3 = new JLabel("N\u00fam Total Pagamentos:");
        lblNewLabel_1_3.setFont(new Font("SansSerif", 0, 16));
        panel_5.add((Component)lblNewLabel_1_3, "cell 0 0,alignx right");
        this.lblNumTotalPagamentos = new JLabel("");
        this.lblNumTotalPagamentos.setFont(new Font("SansSerif", 1, 18));
        panel_5.add((Component)this.lblNumTotalPagamentos, "cell 1 0");
        JLabel lblNewLabel_1 = new JLabel("Valor Total Pagamento Despesas:");
        lblNewLabel_1.setFont(new Font("SansSerif", 0, 16));
        panel_5.add((Component)lblNewLabel_1, "cell 0 2 1 2,alignx right,growy");
        this.entValorTotalPagamentoDespesas = new JLabel("R$ 100.000.000,00");
        this.entValorTotalPagamentoDespesas.setForeground(new Color(153, 0, 0));
        this.entValorTotalPagamentoDespesas.setFont(new Font("SansSerif", 1, 18));
        panel_5.add((Component)this.entValorTotalPagamentoDespesas, "cell 1 2 1 2,alignx center,aligny bottom");
        JLabel lblNewLabel_1_2 = new JLabel("   Valor Total Pagamento Receitas:");
        lblNewLabel_1_2.setFont(new Font("SansSerif", 0, 16));
        panel_5.add((Component)lblNewLabel_1_2, "cell 0 4,alignx right,growy");
        this.entValorTotalPagamentoReceitas = new JLabel("R$ 100.000.000,00");
        this.entValorTotalPagamentoReceitas.setForeground(new Color(0, 51, 0));
        this.entValorTotalPagamentoReceitas.setFont(new Font("SansSerif", 1, 18));
        panel_5.add((Component)this.entValorTotalPagamentoReceitas, "cell 1 4");
        JLabel lblNewLabel_1_2_1 = new JLabel("Balan\u00e7o Normal:");
        lblNewLabel_1_2_1.setFont(new Font("SansSerif", 0, 16));
        panel_5.add((Component)lblNewLabel_1_2_1, "cell 0 5,alignx right,growy");
        this.entBalanco = new JLabel("R$ 100.000.000,00");
        this.entBalanco.setForeground(new Color(0, 153, 0));
        this.entBalanco.setFont(new Font("SansSerif", 1, 18));
        panel_5.add((Component)this.entBalanco, "cell 1 5");
        JLabel lblNewLabel_1_4 = new JLabel("Valor Total Pagamento Transfer\u00eancia:");
        lblNewLabel_1_4.setFont(new Font("SansSerif", 0, 16));
        panel_5.add((Component)lblNewLabel_1_4, "cell 0 6 1 2,alignx right");
        this.entValorTotalPagamentoTransferencia = new JLabel("R$\u00a00,00");
        this.entValorTotalPagamentoTransferencia.setForeground(new Color(0, 0, 153));
        this.entValorTotalPagamentoTransferencia.setFont(new Font("SansSerif", 1, 18));
        panel_5.add((Component)this.entValorTotalPagamentoTransferencia, "cell 1 6 1 2");
        JLabel lblNewLabel_1_4_1 = new JLabel("Valor Total Pagamento Empr\u00e9stimo(Despesas):");
        lblNewLabel_1_4_1.setFont(new Font("SansSerif", 0, 16));
        panel_5.add((Component)lblNewLabel_1_4_1, "cell 0 8,alignx right");
        this.entValorTotalPagamentoEmprestimoDespesas = new JLabel("R$\u00a00,00");
        this.entValorTotalPagamentoEmprestimoDespesas.setForeground(new Color(102, 0, 0));
        this.entValorTotalPagamentoEmprestimoDespesas.setFont(new Font("SansSerif", 1, 18));
        panel_5.add((Component)this.entValorTotalPagamentoEmprestimoDespesas, "cell 1 8");
        JButton btnNewButton_1 = new JButton("Exportar");
        btnNewButton_1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<FinanceiroPagamentoCompleto> pagamentos_selecionados = new ArrayList<FinanceiroPagamentoCompleto>();
                int[] linhas_selecionadas = TelaFinanceiroPagamentoInternal.this.tabela_pagamento.getSelectedRows();
                int i = 0;
                while (i < linhas_selecionadas.length) {
                    int indice = linhas_selecionadas[i];
                    int indexRowModel = TelaFinanceiroPagamentoInternal.this.tabela_pagamento.getRowSorter().convertRowIndexToModel(indice);
                    FinanceiroPagamentoCompleto pagamento = TelaFinanceiroPagamentoInternal.this.modelo_pagamento.getValue(indexRowModel);
                    pagamentos_selecionados.add(pagamento);
                    ++i;
                }
                TelaEscolhaRelatorioPagamentos escolha_opcoes = new TelaEscolhaRelatorioPagamentos(pagamentos_selecionados, TelaFinanceiroPagamentoInternal.this.isto);
                escolha_opcoes.setVisible(true);
            }
        });
        btnNewButton_1.setForeground(Color.WHITE);
        btnNewButton_1.setBackground(new Color(0, 0, 102));
        btnNewButton_1.setFont(new Font("SansSerif", 1, 16));
        panel_5.add((Component)btnNewButton_1, "cell 20 1,alignx right");
        JButton btnAbrirLancamento = new JButton("Abrir");
        btnAbrirLancamento.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Lancamento> lancamentos_selecionados = TelaFinanceiroPagamentoInternal.this.getLancamentosSelecionado();
                if (lancamentos_selecionados.size() == 1) {
                    Lancamento lancamento_gerenciar = lancamentos_selecionados.get(0);
                    TelaFinanceiroGerenciarLancamento tela = new TelaFinanceiroGerenciarLancamento(lancamento_gerenciar, null);
                    tela.setVisible(true);
                }
            }
        });
        btnAbrirLancamento.setForeground(Color.WHITE);
        btnAbrirLancamento.setFont(new Font("SansSerif", 1, 16));
        btnAbrirLancamento.setBackground(new Color(0, 51, 0));
        panel_5.add((Component)btnAbrirLancamento, "cell 22 1,alignx right");
        JLabel lblNewLabel_1_4_1_1 = new JLabel("Valor Total Pagamento Empr\u00e9stimo(Receita):");
        lblNewLabel_1_4_1_1.setFont(new Font("SansSerif", 0, 16));
        panel_5.add((Component)lblNewLabel_1_4_1_1, "cell 0 9");
        this.entValorTotalPagamentoEmprestimoReceitas = new JLabel("R$\u00a00,00");
        this.entValorTotalPagamentoEmprestimoReceitas.setForeground(new Color(0, 51, 0));
        this.entValorTotalPagamentoEmprestimoReceitas.setFont(new Font("SansSerif", 1, 18));
        panel_5.add((Component)this.entValorTotalPagamentoEmprestimoReceitas, "cell 1 9");
        JLabel lblNewLabel_1_2_1_1 = new JLabel("Balan\u00e7o Empr\u00e9stimo:");
        lblNewLabel_1_2_1_1.setFont(new Font("SansSerif", 0, 16));
        panel_5.add((Component)lblNewLabel_1_2_1_1, "cell 0 10,alignx right");
        this.entBalancoEmprestimo = new JLabel("R$\u00a00,00");
        this.entBalancoEmprestimo.setForeground(new Color(0, 153, 0));
        this.entBalancoEmprestimo.setFont(new Font("SansSerif", 1, 18));
        panel_5.add((Component)this.entBalancoEmprestimo, "cell 1 10");
        JLabel lblNewLabel_1_2_1_1_1 = new JLabel("Balan\u00e7o Final");
        lblNewLabel_1_2_1_1_1.setFont(new Font("SansSerif", 0, 16));
        panel_5.add((Component)lblNewLabel_1_2_1_1_1, "cell 0 12,alignx right");
        this.entBalancoFinal = new JLabel("R$\u00a00,00");
        this.entBalancoFinal.setForeground(new Color(0, 153, 0));
        this.entBalancoFinal.setFont(new Font("SansSerif", 1, 18));
        panel_5.add((Component)this.entBalancoFinal, "cell 1 12");
    }

    public void pesquisar(int cc, int ib, int mes, int ano, int tipo) {
        GerenciarBancoFinanceiroPagamento gerenciar = new GerenciarBancoFinanceiroPagamento();
        GerenciarBancoFinanceiroPagamentoEmprestimo gerenciar_emprestimos = new GerenciarBancoFinanceiroPagamentoEmprestimo();
        this.lista_FinanceiroPagamentoCompletos.clear();
        this.modelo_pagamento.onRemoveAll();
        this.lista_FinanceiroPagamentoCompletos = gerenciar.getTodosFinanceiroPagamentosFiltrados(cc, ib, mes, ano, tipo);
        for (FinanceiroPagamentoCompleto pa : this.lista_FinanceiroPagamentoCompletos) {
            this.modelo_pagamento.onAdd(pa);
        }
        if (tipo == 1) {
            ArrayList<FinanceiroPagamentoCompleto> lista_FinanceiroPagamentoCompletosEmprestimo = gerenciar_emprestimos.getTodosFinanceiroPagamentosEmprestimosFiltratos(cc, ib, mes, ano, tipo);
            System.out.println("Lista de Pagamentos de Emprestimo Pesquisada");
            this.modelo_pagamento.onAddAll(lista_FinanceiroPagamentoCompletosEmprestimo);
        }
        this.calcular(tipo);
    }

    public void calcular(int tipo) {
        BigDecimal valor_total_despesas = BigDecimal.ZERO;
        BigDecimal valor_total_receitas = BigDecimal.ZERO;
        BigDecimal balanco = BigDecimal.ZERO;
        BigDecimal valor_total_transferencias = BigDecimal.ZERO;
        BigDecimal balanco_emprestimo = BigDecimal.ZERO;
        BigDecimal valor_total_pagamentos_emprestimo_despesas = BigDecimal.ZERO;
        BigDecimal valor_total_pagamentos_emprestimo_receitas = BigDecimal.ZERO;
        BigDecimal balanco_final = BigDecimal.ZERO;
        int num_total_pagamentos = 0;
        int row = 0;
        while (row < this.tabela_pagamento.getRowCount()) {
            int index = this.tabela_pagamento.convertRowIndexToModel(row);
            FinanceiroPagamentoCompleto pag = this.modelo_pagamento.getValue(index);
            ++num_total_pagamentos;
            if (pag.getLancamento().getTipo_lancamento() == 0) {
                valor_total_despesas = valor_total_despesas.add(pag.getFpag().getValor());
            } else if (pag.getLancamento().getTipo_lancamento() == 1) {
                valor_total_receitas = valor_total_receitas.add(pag.getFpag().getValor());
            } else if (pag.getLancamento().getTipo_lancamento() == 2) {
                valor_total_transferencias = valor_total_transferencias.add(pag.getFpag().getValor());
            } else if (pag.getLancamento().getTipo_lancamento() == 3) {
                if (pag.getFpag().getTipo_pagamento() == 1) {
                    valor_total_pagamentos_emprestimo_despesas = valor_total_pagamentos_emprestimo_despesas.add(pag.getFpag().getValor());
                } else {
                    valor_total_pagamentos_emprestimo_receitas = valor_total_pagamentos_emprestimo_receitas.add(pag.getFpag().getValor());
                }
            }
            ++row;
        }
        balanco = valor_total_receitas.add(valor_total_despesas);
        balanco_emprestimo = valor_total_pagamentos_emprestimo_receitas.add(valor_total_pagamentos_emprestimo_despesas);
        balanco_final = balanco_final.add(balanco).add(balanco_emprestimo).add(valor_total_transferencias);
        Locale ptBr = new Locale("pt", "BR");
        this.lblNumTotalPagamentos.setText(String.valueOf(num_total_pagamentos));
        if (tipo == 1) {
            this.entValorTotalPagamentoReceitas.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_receitas));
            this.entValorTotalPagamentoEmprestimoReceitas.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos_emprestimo_receitas));
            this.entValorTotalPagamentoDespesas.setVisible(false);
            this.entValorTotalPagamentoEmprestimoDespesas.setVisible(false);
        } else {
            this.entValorTotalPagamentoDespesas.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_despesas));
            this.entValorTotalPagamentoEmprestimoDespesas.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos_emprestimo_despesas));
            this.entValorTotalPagamentoReceitas.setVisible(false);
            this.entValorTotalPagamentoEmprestimoReceitas.setVisible(false);
        }
        this.entBalanco.setVisible(false);
        this.entValorTotalPagamentoTransferencia.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_transferencias));
        this.entBalancoEmprestimo.setVisible(false);
        this.entBalancoFinal.setText(NumberFormat.getCurrencyInstance(ptBr).format(balanco_final));
    }

    public boolean checkString(String txt) {
        return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ");
    }

    public ArrayList<FinanceiroPagamentoCompleto> getFinanceiroPagamentoCompletosSelecionado() {
        ArrayList<FinanceiroPagamentoCompleto> FinanceiroPagamentoCompletos_selecionados = new ArrayList<FinanceiroPagamentoCompleto>();
        int[] linhas_selecionadas = this.tabela_pagamento.getSelectedRows();
        int i = 0;
        while (i < linhas_selecionadas.length) {
            int indice = this.tabela_pagamento.getRowSorter().convertRowIndexToModel(linhas_selecionadas[i]);
            FinanceiroPagamentoCompleto FinanceiroPagamentoCompleto_selecionado = this.lista_FinanceiroPagamentoCompletos.get(indice);
            FinanceiroPagamentoCompletos_selecionados.add(FinanceiroPagamentoCompleto_selecionado);
            ++i;
        }
        return FinanceiroPagamentoCompletos_selecionados;
    }

    public void setTelaPai(JDialog _telaPai) {
        this.telaPai = _telaPai;
    }

    public void adicionarFocus(Component[] components) {
        Component[] componentArray = components;
        int n = components.length;
        int n2 = 0;
        while (n2 < n) {
            Component c = componentArray[n2];
            if (c instanceof JTextFieldPersonalizado) {
                if (c instanceof JTextFieldPersonalizado) {
                    final JTextFieldPersonalizado caixa_texto = (JTextFieldPersonalizado)c;
                    caixa_texto.addFocusListener(new FocusAdapter(){

                        @Override
                        public void focusGained(FocusEvent e) {
                            System.out.println("Ganhou focu");
                            caixa_texto.setFocusGained();
                        }

                        @Override
                        public void focusLost(FocusEvent e) {
                            caixa_texto.setFocusLost();
                        }
                    });
                }
            } else {
                Container novo_container = (Container)c;
                this.adicionarFocus(novo_container.getComponents());
            }
            ++n2;
        }
    }

    public String pegarDataHoje() {
        LocalDate hoje = LocalDate.now();
        String df = hoje.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return df.replace(" ", "");
    }

    public String pegarDataMais(int anos) {
        LocalDate hoje = LocalDate.now().plusYears(1L);
        String df = hoje.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return df.replace(" ", "");
    }

    public String pegarDataMenos(int anos) {
        LocalDate hoje = LocalDate.now().minusYears(1L);
        String df = hoje.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return df.replace(" ", "");
    }

    public ArrayList<Lancamento> getLancamentosSelecionado() {
        GerenciarBancoLancamento gerenciar = new GerenciarBancoLancamento();
        ArrayList<Lancamento> lancamentos_selecionados = new ArrayList<Lancamento>();
        int[] linhas_selecionadas = this.tabela_pagamento.getSelectedRows();
        int i = 0;
        while (i < linhas_selecionadas.length) {
            int indice = this.tabela_pagamento.getRowSorter().convertRowIndexToModel(linhas_selecionadas[i]);
            Lancamento lancamento_selecionado = this.modelo_pagamento.getValue(indice).getLancamento();
            lancamentos_selecionados.add(lancamento_selecionado);
            ++i;
        }
        return lancamentos_selecionados;
    }

    class FinanceiroPagamentoCompletosRender
    extends DefaultTableCellRenderer {
        public final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

        FinanceiroPagamentoCompletosRender() {
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component renderer = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            ((JLabel)renderer).setOpaque(true);
            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
            if (value instanceof Date) {
                value = f.format(value);
            }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    public class PagamentoTableModel
    extends AbstractTableModel {
        private final int id = 0;
        private final int tipo_lancamento = 1;
        private final int pagador = 2;
        private final int recebedor = 3;
        private final int data_pagamento = 4;
        private final int valor = 5;
        private final int condicao_pagamento = 6;
        private final int status_condicao_pagamento = 7;
        List<Color> rowColours = Arrays.asList(Color.RED, Color.GREEN, Color.CYAN);
        private final String[] colunas = new String[]{"ID", "Tipo Lan\u00e7amento", "Pagador", "Recebedor", "Data Pagamento", "Valor", "Condi\u00e7\u00f5es de Pagamento", "Status Condi\u00e7\u00e3o de Pagamento"};
        private final ArrayList<FinanceiroPagamentoCompleto> dados = new ArrayList();
        private GerenciarBancoCondicaoPagamentos gerenciar = null;
        private ArrayList<CondicaoPagamento> lista_condicoes = null;
        Locale ptBr = new Locale("pt", "BR");

        public PagamentoTableModel() {
            this.gerenciar = new GerenciarBancoCondicaoPagamentos();
            this.lista_condicoes = this.gerenciar.getCondicaoPagamentos();
        }

        @Override
        public int getColumnCount() {
            return this.colunas.length;
        }

        @Override
        public int getRowCount() {
            return this.dados.size();
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 0: {
                    return Integer.class;
                }
                case 1: {
                    return String.class;
                }
                case 2: {
                    return String.class;
                }
                case 3: {
                    return String.class;
                }
                case 4: {
                    return Date.class;
                }
                case 5: {
                    return String.class;
                }
                case 6: {
                    return String.class;
                }
                case 7: {
                    return String.class;
                }
            }
            throw new IndexOutOfBoundsException("Coluna Inv\u00e1lida!!!");
        }

        @Override
        public String getColumnName(int columnIndex) {
            return this.colunas[columnIndex];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            FinanceiroPagamentoCompleto dado = this.dados.get(rowIndex);
            switch (columnIndex) {
                case 0: {
                    return dado.getFpag().getId_pagamento();
                }
                case 1: {
                    int tipo = dado.getLancamento().getTipo_lancamento();
                    if (tipo == 0) {
                        return "D";
                    }
                    if (tipo == 1) {
                        return "R";
                    }
                    if (tipo == 2) {
                        return "T";
                    }
                    if (tipo == 3) {
                        return "E";
                    }
                }
                case 2: {
                    try {
                        return dado.getNome_pagador().toUpperCase();
                    }
                    catch (Exception e) {
                        return "";
                    }
                }
                case 3: {
                    try {
                        return dado.getNome_recebedor().toUpperCase();
                    }
                    catch (Exception e) {
                        return "";
                    }
                }
                case 4: {
                    if (dado.getFpag().getData_pagamento() != null && !dado.getFpag().getData_pagamento().equalsIgnoreCase("")) {
                        try {
                            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                            Date data_pag = formato.parse(dado.getFpag().getData_pagamento());
                            return data_pag;
                        }
                        catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
                case 5: {
                    return NumberFormat.getCurrencyInstance(this.ptBr).format(dado.getFpag().getValor());
                }
                case 6: {
                    try {
                        String s_condicao = "";
                        int id_condicao_pagamento = dado.getFpag().getId_condicao_pagamento();
                        if (id_condicao_pagamento > 0) {
                            CondicaoPagamento condicao = null;
                            for (CondicaoPagamento cond : this.lista_condicoes) {
                                if (cond.getId_condicao_pagamento() != id_condicao_pagamento) continue;
                                condicao = cond;
                                break;
                            }
                            if (condicao != null) {
                                // empty if block
                            }
                            s_condicao = String.valueOf(s_condicao) + condicao.getNome_condicao_pagamento();
                        }
                        return s_condicao;
                    }
                    catch (Exception e) {
                        return "";
                    }
                }
                case 7: {
                    try {
                        String retorno = "";
                        int id_status = dado.getFpag().getStatus_pagamento();
                        if (id_status == 0) {
                            retorno = String.valueOf(retorno) + "A - Compensar|Realizar|Concluir;";
                        } else if (id_status == 1) {
                            retorno = String.valueOf(retorno) + "Compensado|Realizado|Conclu\u00eddo;";
                        }
                        return retorno;
                    }
                    catch (Exception e) {
                        return "";
                    }
                }
            }
            throw new IndexOutOfBoundsException("Coluna Inv\u00e1lida!!!");
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            FinanceiroPagamentoCompleto ib = this.dados.get(rowIndex);
        }

        public FinanceiroPagamentoCompleto getValue(int rowIndex) {
            return this.dados.get(rowIndex);
        }

        public int indexOf(FinanceiroPagamentoCompleto dado) {
            return this.dados.indexOf(dado);
        }

        public void onAdd(FinanceiroPagamentoCompleto dado) {
            this.dados.add(dado);
            this.fireTableRowsInserted(this.indexOf(dado), this.indexOf(dado));
        }

        public void onAddAll(ArrayList<FinanceiroPagamentoCompleto> dadosIn) {
            this.dados.addAll(dadosIn);
            this.fireTableDataChanged();
        }

        public void onRemove(int rowIndex) {
            this.dados.remove(rowIndex);
            this.fireTableRowsDeleted(rowIndex, rowIndex);
        }

        public void onRemove(FinanceiroPagamentoCompleto dado) {
            int indexBefore = this.indexOf(dado);
            this.dados.remove(dado);
            this.fireTableRowsDeleted(indexBefore, indexBefore);
        }

        public void onRemoveAll() {
            this.dados.clear();
            this.fireTableDataChanged();
        }

        public FinanceiroPagamentoCompleto onGet(int row) {
            return this.dados.get(row);
        }
    }
}

