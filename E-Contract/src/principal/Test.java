package principal;

import java.applet.Applet;
import graficos.GraficoLinha;
import graficos.JPanelGrafico;
import graficos.JPanelGraficoCarregamento;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;

import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicMenuBarUI;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import com.sun.javafx.application.PlatformImpl;

import cadastros.CadastroBaseArquivos;
import cadastros.CadastroBaseDados;
import cadastros.CadastroCliente;
import cadastros.CadastroContrato;
import cadastros.CadastroLogin;
import cadastros.CadastroModelo;
import cadastros.CadastroNota;
import cadastros.CadastroProduto;
import cadastros.CadastroRomaneio;
import cadastros.CadastroSafra;
import cadastros.Contato;
import cadastros.DadosContratos;
import cadastros.DadosCarregamento;


import classesExtras.ComboBoxPersonalizado;
import classesExtras.ComboBoxRenderPersonalizado;
import classesExtras.RenderizadorChat;
import conexaoBanco.GerenciarBancoClientes;
import conexaoBanco.GerenciarBancoContratos;
import conexaoBanco.GerenciarBancoLogin;
import conexaoBanco.GerenciarBancoNotas;
import conexaoBanco.GerenciarBancoPadrao;
import conexaoBanco.GerenciarBancoSafras;
import conexoes.TesteConexao;
import manipular.ConfiguracoesGlobais;
import manipular.EditarWord;
import manipular.GetDadosGlobais;
import manipular.ManipularRomaneios;
import manipular.ManipularTxt;
import outros.BaixarNotasFiscais;
import outros.DadosGlobais;
import outros.GetData;
import outros.JPanelBackground;
import graficos.JPanelGrafico;
import outros.ReproduzirAudio;
import tratamento_proprio.Log;
import views_personalizadas.TelaMensagens;
import views_personalizadas.TelaNotificacao;
import views_personalizadas.TelaNotificacaoSuperior;
import views_personalizadas.TelaNotificacaoSuperiorModoBusca;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Point;
import java.awt.Insets;
import java.awt.LayoutManager;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import net.miginfocom.swing.MigLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTabbedPane;
import javax.swing.JComboBox;
import javax.swing.border.LineBorder;
import java.awt.Component;
import java.awt.Desktop;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class Test {

    public static void main(String[] args) {
    

    	
    }
}