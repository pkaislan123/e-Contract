/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.miginfocom.swing.MigLayout
 */
package main.java.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import main.java.cadastros.CadastroAcessoTemporario;
import main.java.cadastros.CadastroFuncionario;
import main.java.cadastros.CadastroFuncionarioDepartamentos;
import main.java.cadastros.CadastroLogin;
import main.java.conexaoBanco.GerenciarBancoAcessoTemporario;
import main.java.conexaoBanco.GerenciarBancoFuncionarios;
import main.java.conexaoBanco.GerenciarBancoFuncionariosDepartamentos;
import main.java.gui.TelaCadastroClassificadores;
import main.java.gui.TelaCliente;
import main.java.gui.TelaFuncionariosCadastroFuncionario;
import main.java.gui.TelaGerenciarFuncionario;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.outros.DadosGlobais;
import main.java.tratamento_proprio.Log;
import net.miginfocom.swing.MigLayout;

public class TelaFuncionarios
extends JFrame {
    private Window telaPai;
    private JPanel contentPane;
    private Log GerenciadorLog;
    private CadastroLogin login;
    private ConfiguracoesGlobais configs_globais;
    private FuncionarioTableModel modelos_funcionarios = new FuncionarioTableModel();
    private TableRowSorter<FuncionarioTableModel> sorter;
    private static ArrayList<CadastroFuncionario> lista_funcionarios = new ArrayList();
    private JTextField entNome;
    private CadastroFuncionario funcionarioSelecionado;
    private TelaFuncionarios isto;
    private JTable tabela;
    private JTextField entCpfCnpj;
    private JComboBox cBDepartamento;
    private JComboBox cBStatus;
    private JLabel lblContratosAtivos;
    private JLabel lblContratosInativos;

    public TelaFuncionarios(final int flag_tipo_tela, final Window janela_pai) {
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(TelaCliente.class.getResource("/imagens/equipe.png")));
        this.getDadosGlobais();
        this.isto = this;
        this.setTitle("E-Contract - Colaboradores");
        this.setResizable(false);
        this.setBackground(new Color(255, 255, 255));
        this.setDefaultCloseOperation(2);
        this.setBounds(100, 100, 1061, 719);
        this.contentPane = new JPanel();
        this.contentPane.setBackground(new Color(255, 255, 255));
        this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setContentPane(this.contentPane);
        JPanel painelClientes = new JPanel();
        painelClientes.setBackground(Color.WHITE);
        painelClientes.setBounds(10, 11, 739, 446);
        painelClientes.setLayout((LayoutManager)new MigLayout("", "[61px][][][2px][278px,grow][13px][71px][4px][77px,grow][][12px][89px][10px][131px]", "[][][37px][][grow][::50px][][][]"));
        JButton btnLimparCampos = new JButton("Limpar Campos");
        btnLimparCampos.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFuncionarios.this.entNome.setText("");
                TelaFuncionarios.this.entCpfCnpj.setText("");
                TelaFuncionarios.this.cBDepartamento.setSelectedIndex(0);
            }
        });
        JLabel lblDepartamento = new JLabel("Departamento:");
        lblDepartamento.setFont(new Font("Arial", 0, 16));
        painelClientes.add((Component)lblDepartamento, "cell 0 1 3 1");
        this.cBDepartamento = new JComboBox();
        this.cBDepartamento.setFont(new Font("SansSerif", 0, 16));
        painelClientes.add((Component)this.cBDepartamento, "cell 4 1,growx");
        JLabel lblStatus = new JLabel("Status:");
        lblStatus.setFont(new Font("Arial", 0, 16));
        painelClientes.add((Component)lblStatus, "cell 6 1,alignx right");
        this.cBStatus = new JComboBox();
        this.cBStatus.setFont(new Font("SansSerif", 0, 16));
        this.cBStatus.addItem("TODOS");
        this.cBStatus.addItem("ATIVO");
        this.cBStatus.addItem("DESATIVADO");
        painelClientes.add((Component)this.cBStatus, "cell 8 1 2 1,growx");
        btnLimparCampos.setForeground(Color.WHITE);
        btnLimparCampos.setFont(new Font("SansSerif", 1, 14));
        btnLimparCampos.setBackground(Color.RED);
        painelClientes.add((Component)btnLimparCampos, "cell 9 3");
        FuncionarioContratoCellREnder renderer = new FuncionarioContratoCellREnder();
        this.sorter = new TableRowSorter<FuncionarioTableModel>(this.modelos_funcionarios);
        this.tabela = new JTable(this.modelos_funcionarios);
        this.tabela.setRowSorter(this.sorter);
        this.tabela.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent arg0) {
                if (arg0.getKeyCode() == 10) {
                    TelaFuncionarios.this.processarSelecao(flag_tipo_tela, TelaFuncionarios.this.tabela, janela_pai);
                }
            }
        });
        this.tabela.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    TelaFuncionarios.this.processarSelecao(flag_tipo_tela, TelaFuncionarios.this.tabela, janela_pai);
                }
            }
        });
        this.tabela.setDefaultRenderer(Object.class, renderer);
        this.tabela.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(this.tabela);
        painelClientes.add((Component)scrollPane, "cell 0 4 14 1,grow");
        scrollPane.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent arg0) {
            }
        });
        scrollPane.setAutoscrolls(true);
        scrollPane.setBackground(new Color(255, 255, 255));
        URL url = this.getClass().getResource("/imagens/pesquisar.png");
        ImageIcon img_botao = new ImageIcon(url);
        this.entNome = new JTextField();
        this.entNome.setFont(new Font("Arial", 1, 16));
        this.entNome.addKeyListener(new KeyAdapter(){

            @Override
            public void keyTyped(KeyEvent e) {
            }
        });
        painelClientes.add((Component)this.entNome, "cell 4 0,grow");
        this.entNome.setColumns(10);
        JTabbedPane painelPrincipal = new JTabbedPane();
        painelPrincipal.setBackground(new Color(255, 255, 255));
        painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
        painelPrincipal.addTab("Clientes", painelClientes);
        JLabel lblNewLabel = new JLabel("Nome:");
        lblNewLabel.setFont(new Font("Arial", 0, 16));
        painelClientes.add((Component)lblNewLabel, "cell 0 0 3 1,alignx right,aligny center");
        JLabel lblCpfcnpj = new JLabel("CPF/CNPJ:");
        lblCpfcnpj.setFont(new Font("Arial", 0, 16));
        painelClientes.add((Component)lblCpfcnpj, "cell 6 0,growx,aligny center");
        this.entCpfCnpj = new JTextField();
        this.entCpfCnpj.setFont(new Font("Arial", 1, 16));
        this.entCpfCnpj.addKeyListener(new KeyAdapter(){

            @Override
            public void keyTyped(KeyEvent e) {
            }
        });
        this.entCpfCnpj.setColumns(10);
        painelClientes.add((Component)this.entCpfCnpj, "cell 8 0 6 1,grow");
        JButton btnFiltrar = new JButton("Filtrar");
        btnFiltrar.setBackground(new Color(0, 51, 0));
        btnFiltrar.setForeground(Color.WHITE);
        btnFiltrar.setFont(new Font("SansSerif", 1, 14));
        btnFiltrar.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFuncionarios.this.filtrar();
            }
        });
        painelClientes.add((Component)btnFiltrar, "cell 13 3,alignx left,growy");
        JButton btnLimpar = new JButton("Limpar Pesquisa");
        btnLimpar.setBackground(new Color(204, 51, 0));
        btnLimpar.setForeground(Color.WHITE);
        btnLimpar.setFont(new Font("SansSerif", 1, 14));
        btnLimpar.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFuncionarios.this.sorter.setRowFilter(RowFilter.regexFilter("", new int[0]));
            }
        });
        painelClientes.add((Component)btnLimpar, "cell 11 3,grow");
        JButton btnRefazerPesquisa = new JButton("Refazer Pesquisa");
        btnRefazerPesquisa.setBackground(Color.BLUE);
        btnRefazerPesquisa.setForeground(Color.WHITE);
        btnRefazerPesquisa.setFont(new Font("SansSerif", 1, 14));
        btnRefazerPesquisa.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFuncionarios.this.pesquisar();
            }
        });
        painelClientes.add((Component)btnRefazerPesquisa, "cell 6 3 3 1,alignx right,growy");
        JLabel lblNewLabel_1 = new JLabel("Legenda:");
        lblNewLabel_1.setFont(new Font("SansSerif", 0, 16));
        painelClientes.add((Component)lblNewLabel_1, "cell 0 6");
        JButton btnEditar = new JButton("Gerenciar");
        painelClientes.add((Component)btnEditar, "cell 9 6");
        btnEditar.setBackground(Color.WHITE);
        btnEditar.setIcon(new ImageIcon(TelaCliente.class.getResource("/imagens/editar.png")));
        btnEditar.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (TelaFuncionarios.this.login != null) {
                    if (TelaFuncionarios.this.login.getConfigs_privilegios().getNivel_privilegios() <= 2) {
                        int rowSel = TelaFuncionarios.this.tabela.getSelectedRow();
                        int indexRowModel = TelaFuncionarios.this.tabela.getRowSorter().convertRowIndexToModel(rowSel);
                        TelaGerenciarFuncionario telagerenciar = new TelaGerenciarFuncionario(new GerenciarBancoFuncionarios().getfuncionario(lista_funcionarios.get(indexRowModel).getId_funcionario()), TelaFuncionarios.this.isto);
                        telagerenciar.setVisible(true);
                    } else {
                        GerenciarBancoAcessoTemporario gerenciar = new GerenciarBancoAcessoTemporario();
                        ArrayList<CadastroAcessoTemporario> acessos = gerenciar.getAcessosTemporariosPorExecutor(TelaFuncionarios.this.login.getId());
                        boolean tem_acesso = false;
                        for (CadastroAcessoTemporario acesso : acessos) {
                            int modulo = acesso.getModulo();
                            if (modulo != 0) continue;
                            LocalDateTime inicio = LocalDateTime.parse(String.valueOf(acesso.getData_inicial()) + " " + acesso.getHora_inicial(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                            LocalDateTime fim = LocalDateTime.parse(String.valueOf(acesso.getData_final()) + " " + acesso.getHora_final(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                            LocalDateTime agora = LocalDateTime.now();
                            if (!agora.isAfter(inicio) || !agora.isBefore(fim)) continue;
                            tem_acesso = true;
                            break;
                        }
                        if (!tem_acesso) {
                            JOptionPane.showMessageDialog(TelaFuncionarios.this.isto, "Requer Eleva\u00e7\u00e3o de Direitos");
                        } else {
                            int rowSel = TelaFuncionarios.this.tabela.getSelectedRow();
                            int indexRowModel = TelaFuncionarios.this.tabela.getRowSorter().convertRowIndexToModel(rowSel);
                            TelaGerenciarFuncionario telagerenciar = new TelaGerenciarFuncionario(new GerenciarBancoFuncionarios().getfuncionario(lista_funcionarios.get(indexRowModel).getId_funcionario()), TelaFuncionarios.this.isto);
                            telagerenciar.setVisible(true);
                        }
                    }
                }
            }
        });
        JButton btnSelecionar = new JButton("Selecionar");
        painelClientes.add((Component)btnSelecionar, "cell 11 6");
        btnSelecionar.setIcon(new ImageIcon(TelaCliente.class.getResource("/imagens/lista.png")));
        btnSelecionar.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFuncionarios.this.processarSelecao(flag_tipo_tela, TelaFuncionarios.this.tabela, janela_pai);
            }
        });
        JButton btnUsurio = new JButton("Novo Colaborador");
        painelClientes.add((Component)btnUsurio, "cell 13 6");
        btnUsurio.setIcon(new ImageIcon(TelaCliente.class.getResource("/imagens/add_cliente.png")));
        btnUsurio.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFuncionariosCadastroFuncionario funcionario = new TelaFuncionariosCadastroFuncionario(0, null, TelaFuncionarios.this.isto);
                funcionario.setVisible(true);
            }
        });
        JLabel lblNewLabel_2 = new JLabel("Ativo:");
        lblNewLabel_2.setFont(new Font("SansSerif", 1, 16));
        painelClientes.add((Component)lblNewLabel_2, "cell 0 7,alignx right");
        JLabel lblNewLabel_3 = new JLabel("___");
        lblNewLabel_3.setOpaque(true);
        lblNewLabel_3.setForeground(new Color(0, 51, 0));
        lblNewLabel_3.setBackground(new Color(0, 51, 0));
        painelClientes.add((Component)lblNewLabel_3, "cell 1 7");
        this.lblContratosAtivos = new JLabel("0");
        this.lblContratosAtivos.setFont(new Font("SansSerif", 1, 16));
        painelClientes.add((Component)this.lblContratosAtivos, "cell 2 7,alignx center");
        JLabel lblNewLabel_2_1 = new JLabel("Desativado:");
        lblNewLabel_2_1.setFont(new Font("SansSerif", 1, 16));
        painelClientes.add((Component)lblNewLabel_2_1, "cell 0 8,alignx right");
        JLabel lblNewLabel_3_1 = new JLabel("___");
        lblNewLabel_3_1.setOpaque(true);
        lblNewLabel_3_1.setForeground(new Color(204, 0, 0));
        lblNewLabel_3_1.setBackground(new Color(204, 0, 0));
        painelClientes.add((Component)lblNewLabel_3_1, "cell 1 8");
        this.lblContratosInativos = new JLabel("0");
        this.lblContratosInativos.setFont(new Font("SansSerif", 1, 16));
        painelClientes.add((Component)this.lblContratosInativos, "cell 2 8,alignx center");
        this.contentPane.setLayout((LayoutManager)new MigLayout("", "[grow]", "[grow]"));
        this.contentPane.add((Component)painelPrincipal, "cell 0 0,grow");
        this.pesquisarDepartamentos();
        this.pesquisar();
        this.setLocationRelativeTo(janela_pai);
    }

    public void pesquisar() {
        this.modelos_funcionarios.onRemoveAll();
        GerenciarBancoFuncionarios gerenciar = new GerenciarBancoFuncionarios();
        lista_funcionarios = gerenciar.getfuncionarios();
        for (CadastroFuncionario funcionario : lista_funcionarios) {
            this.modelos_funcionarios.onAdd(funcionario);
        }
        this.calcular();
    }

    public void calcular() {
        int num_ativos = 0;
        int num_inativos = 0;
        int row = 0;
        while (row < this.tabela.getRowCount()) {
            int index = this.tabela.convertRowIndexToModel(row);
            CadastroFuncionario func = this.modelos_funcionarios.getValue(index);
            int sts = func.getStatus();
            if (sts == 1) {
                ++num_ativos;
            } else if (sts == 0) {
                ++num_inativos;
            }
            ++row;
        }
        this.lblContratosAtivos.setText(String.valueOf(num_ativos));
        this.lblContratosInativos.setText(String.valueOf(num_inativos));
    }

    public void filtrar() {
        ArrayList filters = new ArrayList(2);
        String nome = this.entNome.getText().toUpperCase();
        String cpnf_cnpj = this.entCpfCnpj.getText().toUpperCase();
        if (this.checkString(cpnf_cnpj)) {
            filters.add(RowFilter.regexFilter(cpnf_cnpj, 1));
        }
        if (this.checkString(nome)) {
            filters.add(RowFilter.regexFilter(nome, 2));
        }
        if (this.cBDepartamento.getSelectedItem().toString() != null) {
            String s_dep = "";
            if (this.checkString(this.cBDepartamento.getSelectedItem().toString()) && !(s_dep = this.cBDepartamento.getSelectedItem().toString()).equalsIgnoreCase("TODOS")) {
                filters.add(RowFilter.regexFilter(s_dep, 3));
            }
        }
        if (this.cBStatus.getSelectedItem().toString() != null) {
            String sts = "";
            if (this.checkString(this.cBStatus.getSelectedItem().toString()) && !(sts = this.cBStatus.getSelectedItem().toString()).equalsIgnoreCase("TODOS")) {
                filters.add(RowFilter.regexFilter(sts, 4));
            }
        }
        this.sorter.setRowFilter(RowFilter.andFilter(filters));
        this.calcular();
    }

    public boolean checkString(String txt) {
        return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ");
    }

    public void getDadosGlobais() {
        DadosGlobais dados = DadosGlobais.getInstance();
        this.GerenciadorLog = dados.getGerenciadorLog();
        this.configs_globais = dados.getConfigs_globais();
        this.login = dados.getLogin();
    }

    public void pesquisarDepartamentos() {
        GerenciarBancoFuncionariosDepartamentos gerenciar = new GerenciarBancoFuncionariosDepartamentos();
        ArrayList<CadastroFuncionarioDepartamentos> deps = gerenciar.getDepartamentos();
        this.cBDepartamento.addItem("TODOS");
        for (CadastroFuncionarioDepartamentos dep : deps) {
            this.cBDepartamento.addItem(dep.getNome());
        }
    }

    public void processarSelecao(int flag_tipo_tela, JTable tabela, Window janela_pai) {
        int rowSel = tabela.getSelectedRow();
        int indiceDaLinha = tabela.getRowSorter().convertRowIndexToModel(rowSel);
        this.funcionarioSelecionado = lista_funcionarios.get(indiceDaLinha);
        if (flag_tipo_tela == 0) {
            ((TelaCadastroClassificadores)janela_pai).setColaborador(this.funcionarioSelecionado);
            this.isto.dispose();
        }
    }

    class FuncionarioContratoCellREnder
    implements TableCellRenderer {
        public final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

        FuncionarioContratoCellREnder() {
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component renderer = this.DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            ((JLabel)renderer).setOpaque(true);
            String dados = (String)table.getValueAt(row, 4);
            if (isSelected) {
                renderer.setBackground(new Color(139, 69, 19));
            } else if (dados.equalsIgnoreCase("ATIVO")) {
                renderer.setForeground(Color.WHITE);
                renderer.setBackground(new Color(0, 51, 0));
            } else if (dados.equalsIgnoreCase("DESATIVADO")) {
                renderer.setBackground(new Color(204, 0, 0));
                renderer.setForeground(Color.WHITE);
            }
            return renderer;
        }
    }

    public static class FuncionarioTableModel
    extends AbstractTableModel {
        private final int id = 0;
        private final int cpf = 1;
        private final int nome = 2;
        private final int departamento = 3;
        private final int status = 4;
        private final String[] colunas = new String[]{"ID:", "CPF:", "Nome:", "Departamento:", "Status:"};
        private final ArrayList<CadastroFuncionario> dados = new ArrayList();

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
            CadastroFuncionario nota = this.dados.get(rowIndex);
            switch (columnIndex) {
                case 0: {
                    return nota.getId_funcionario();
                }
                case 1: {
                    return nota.getCpf();
                }
                case 2: {
                    return String.valueOf(nota.getNome().toUpperCase()) + " " + nota.getSobrenome().toUpperCase();
                }
                case 3: {
                    return nota.getNome_departamento();
                }
                case 4: {
                    int sts = nota.getStatus();
                    if (sts == 1) {
                        return "ATIVO";
                    }
                    if (sts != 0) break;
                    return "DESATIVADO";
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
            CadastroFuncionario nota = this.dados.get(rowIndex);
        }

        public CadastroFuncionario getValue(int rowIndex) {
            return this.dados.get(rowIndex);
        }

        public int indexOf(CadastroFuncionario nota) {
            return this.dados.indexOf(nota);
        }

        public void onAdd(CadastroFuncionario nota) {
            this.dados.add(nota);
            this.fireTableRowsInserted(this.indexOf(nota), this.indexOf(nota));
        }

        public void onAddAll(ArrayList<CadastroFuncionario> dadosIn) {
            this.dados.addAll(dadosIn);
            this.fireTableDataChanged();
        }

        public void onRemove(int rowIndex) {
            this.dados.remove(rowIndex);
            this.fireTableRowsDeleted(rowIndex, rowIndex);
        }

        public void onRemove(CadastroFuncionario nota) {
            int indexBefore = this.indexOf(nota);
            this.dados.remove(nota);
            this.fireTableRowsDeleted(indexBefore, indexBefore);
        }

        public void onRemoveAll() {
            this.dados.clear();
            this.fireTableDataChanged();
        }
    }
}

