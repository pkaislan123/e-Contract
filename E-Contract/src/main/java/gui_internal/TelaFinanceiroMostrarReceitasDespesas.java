/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.miginfocom.swing.MigLayout
 */
package main.java.gui_internal;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.beans.PropertyVetoException;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import main.java.gui_internal.TelaFinanceiroLancamentoInternal;
import main.java.gui_internal.TelaFinanceiroPagamentoInternal;
import main.java.gui_internal.TelaFinanceiroParcelaInternal;
import net.miginfocom.swing.MigLayout;

public class TelaFinanceiroMostrarReceitasDespesas
extends JFrame {
    private final JPanel painelPrincipal = new JPanel();
    private TelaFinanceiroMostrarReceitasDespesas isto;
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice[] gds = this.ge.getScreenDevices();
    private JDialog telaPai;

    public TelaFinanceiroMostrarReceitasDespesas(int flag_tipo_tela, int cc, int ib, int mes, int ano, Window janela_anotacoes) {
        this.setResizable(true);
        this.setTitle("E-Contract -  Nova Anota\u00e7\u00e3o");
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dim = tk.getScreenSize();
        System.out.println("Screen width = " + dim.width);
        System.out.println("Screen height = " + dim.height);
        Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        int taskBarHeight = scrnSize.height - winSize.height;
        System.out.printf("Altura: %d\n", taskBarHeight);
        DisplayMode display = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
        int display_x = display.getWidth();
        int display_y = display.getHeight();
        this.setBounds(0, 0, dim.width, dim.height - taskBarHeight);
        this.isto = this;
        this.setBackground(new Color(255, 255, 255));
        this.setDefaultCloseOperation(2);
        this.getContentPane().setLayout((LayoutManager)new MigLayout("", "[grow][16px][grow]", "[grow]"));
        JDesktopPane desktopPane_1 = new JDesktopPane();
        this.getContentPane().add((Component)desktopPane_1, "cell 0 0,grow");
        JLabel lblNewLabel = new JLabel("||||");
        lblNewLabel.setOpaque(true);
        lblNewLabel.setBackground(Color.BLACK);
        this.getContentPane().add((Component)lblNewLabel, "cell 1 0,grow");
        JDesktopPane desktopPane = new JDesktopPane();
        this.getContentPane().add((Component)desktopPane, "cell 2 0,grow");
        if (flag_tipo_tela == 0) {
            TelaFinanceiroLancamentoInternal despesas = new TelaFinanceiroLancamentoInternal(1, janela_anotacoes);
            despesas.setLocation(22, 31);
            despesas.pesquisar(cc, mes, ano, 1);
            despesas.setVisible(true);
            desktopPane_1.add(despesas);
            try {
                despesas.setMaximum(true);
            }
            catch (PropertyVetoException e) {
                e.printStackTrace();
            }
            TelaFinanceiroLancamentoInternal receitas = new TelaFinanceiroLancamentoInternal(0, janela_anotacoes);
            receitas.pesquisar(cc, mes, ano, 0);
            receitas.setVisible(true);
            desktopPane.add(receitas);
            try {
                receitas.setMaximum(true);
            }
            catch (PropertyVetoException e) {
                e.printStackTrace();
            }
        } else if (flag_tipo_tela == 1) {
            TelaFinanceiroPagamentoInternal despesas = new TelaFinanceiroPagamentoInternal(1, janela_anotacoes);
            despesas.setLocation(22, 31);
            despesas.pesquisar(cc, ib, mes, ano, 1);
            despesas.setVisible(true);
            desktopPane_1.add(despesas);
            try {
                despesas.setMaximum(true);
            }
            catch (PropertyVetoException e) {
                e.printStackTrace();
            }
            TelaFinanceiroPagamentoInternal receitas = new TelaFinanceiroPagamentoInternal(0, janela_anotacoes);
            receitas.pesquisar(cc, ib, mes, ano, 0);
            receitas.setVisible(true);
            desktopPane.add(receitas);
            try {
                receitas.setMaximum(true);
            }
            catch (PropertyVetoException e) {
                e.printStackTrace();
            }
        } else if (flag_tipo_tela == 2) {
            TelaFinanceiroParcelaInternal despesas = new TelaFinanceiroParcelaInternal(1, janela_anotacoes);
            despesas.setLocation(22, 31);
            despesas.pesquisar(cc, mes, ano, 1);
            despesas.setVisible(true);
            desktopPane_1.add(despesas);
            try {
                despesas.setMaximum(true);
            }
            catch (PropertyVetoException e) {
                e.printStackTrace();
            }
            TelaFinanceiroParcelaInternal receitas = new TelaFinanceiroParcelaInternal(0, janela_anotacoes);
            receitas.pesquisar(cc, mes, ano, 0);
            receitas.setVisible(true);
            desktopPane.add(receitas);
            try {
                receitas.setMaximum(true);
            }
            catch (PropertyVetoException e) {
                e.printStackTrace();
            }
        }
        this.setExtendedState(6);
        this.setLocationRelativeTo(janela_anotacoes);
        this.setLocationRelativeTo(janela_anotacoes);
        this.setVisible(true);
    }
}

