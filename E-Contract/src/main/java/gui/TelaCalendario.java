package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.freixas.jcalendar.DateEvent;
import org.freixas.jcalendar.DateListener;
import org.freixas.jcalendar.JCalendar;
import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GraphicsEnvironment;

import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import main.java.cadastros.CadastroFuncionarioEvento;
import main.java.cadastros.EventoGlobal;
import main.java.conexaoBanco.GerenciarBancoEventoGlobal;

import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class TelaCalendario extends JFrame {

	private final JPanel painelPrincipal = new JPanel();
	private TelaCalendario isto;
	private JDialog telaPai;
	private JLabel lblDataSelecionada;
	private JTable table;
	private LocalDateTime data_selecionada;
	private EventoGlobalTableModel modeloEventos = new EventoGlobalTableModel();
	
	public TelaCalendario(Window janela_pai) {

		isto = this;

		setTitle("E-Contract - Tela Padrãos");

		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		System.out.println("Screen width = " + dim.width);
		System.out.println("Screen height = " + dim.height);

		// pega o tamanho da barra de tarefas
		Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
		java.awt.Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		int taskBarHeight = scrnSize.height - winSize.height;
		System.out.printf("Altura: %d\n", taskBarHeight);

		DisplayMode display = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDisplayMode();

		int display_x = display.getWidth();
		int display_y = display.getHeight();
		setBounds(0, 0, dim.width, dim.height - taskBarHeight);

		setResizable(true);

		painelPrincipal.setBackground(new Color(0, 102, 153));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);

		MyDateListener listener = new MyDateListener();

		// Display date and time using the default calendar and locale.
		// Display today's date at the bottom.

		JCalendar calendar1 = new JCalendar(Calendar.getInstance(new Locale("pt", "BR")), new Locale("pt", "BR"),
				JCalendar.DISPLAY_DATE | JCalendar.DISPLAY_TIME, true, "HH:mm");
		calendar1.addDateListener(listener);
		painelPrincipal.setLayout(new MigLayout("", "[1000px,grow][grow]", "[grow]"));

		// Set fonts rather than using defaults

		calendar1.setTitleFont(new Font("Serif", Font.BOLD | Font.ITALIC, 24));
		calendar1.setDayOfWeekFont(new Font("SansSerif", Font.ITALIC, 12));
		calendar1.setDayFont(new Font("SansSerif", Font.BOLD, 16));
		calendar1.setTimeFont(new Font("DialogInput", Font.PLAIN, 10));
		calendar1.setTodayFont(new Font("Dialog", Font.PLAIN, 14));

		JPanel painelLembrete = new JPanel();
		painelLembrete.setLayout(new MigLayout("", "[grow]", "[grow]"));
		painelLembrete.add(calendar1, "cell 0 0,grow");

		painelPrincipal.add(painelLembrete, "cell 0 0,grow");

		JPanel panel = new JPanel();
		panel.setBackground(new Color(51, 51, 51));
		painelPrincipal.add(panel, "cell 1 0,grow");
		panel.setLayout(new MigLayout("", "[grow]", "[][][grow][][][][][][][][][][][][][][][][][][][][]"));

		JLabel lblNewLabel = new JLabel("Eventos");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
		panel.add(lblNewLabel, "cell 0 0,alignx center");

		lblDataSelecionada = new JLabel("Data");
		panel.add(lblDataSelecionada, "cell 0 1");
		lblDataSelecionada.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblDataSelecionada.setForeground(Color.WHITE);

		table = new JTable(modeloEventos);
		table.setRowHeight(30);

		JScrollPane scrollEventos = new JScrollPane(table);
		scrollEventos.getViewport().setBackground(Color.white);
		panel.add(scrollEventos, "cell 0 2 1 20,grow");

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.setForeground(Color.WHITE);
		btnExcluir.setFont(new Font("SansSerif", Font.ITALIC, 16));
		btnExcluir.setBackground(new Color(102, 0, 0));
		panel.add(btnExcluir, "flowx,cell 0 22,alignx right");

		JButton btnNewButton = new JButton("Novo");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaCriarEventoGlobal tela = new TelaCriarEventoGlobal(isto);
				tela.setData(data_selecionada);
				tela.setVisible(true);

			}
		});
		btnNewButton.setBackground(new Color(0, 0, 102));
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setFont(new Font("SansSerif", Font.ITALIC, 16));
		panel.add(btnNewButton, "cell 0 22,alignx right");

		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		this.setLocationRelativeTo(janela_pai);

	}

	private class MyDateListener implements DateListener {

		public void dateChanged(DateEvent e) {
			Calendar c = e.getSelectedDate();
			if (c != null) {

				data_selecionada = toLocalDateTime(c);
				lblDataSelecionada.setText(data_selecionada.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));

				pesquisar_eventos(data_selecionada.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

			} else {
				System.out.println("No time selected.");
				lblDataSelecionada.setText("Nenhum data selecioanda");

			}
		}

	}

	public void pesquisar_eventos(String data_evento) {
		modeloEventos.onRemoveAll();
		GerenciarBancoEventoGlobal gerenciar = new GerenciarBancoEventoGlobal();

		for(EventoGlobal evt : gerenciar.getEventosPorData(data_evento)) {
			modeloEventos.onAdd(evt);
		}
		
	}

	public class EventoGlobalTableModel extends AbstractTableModel {

		// constantes p/identificar colunas

		private final int id_evento = 0;
		private final int tipo = 1;
		private final int data_evento = 2;
		private final int hora_evento = 3;
		private final int descricao = 4;

		private final String colunas[] = { "ID", "TIPO", "DATA", "HORA", "DESCRIÇÃO" };

		private final ArrayList<EventoGlobal> dados = new ArrayList<>();// usamos como dados uma lista
		// genérica de
		// nfs

		public EventoGlobalTableModel() {

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

			case id_evento:
				return Integer.class;
			case tipo:
				return String.class;
			case data_evento:
				return String.class;
			case hora_evento:
				return String.class;
			case descricao:
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

			// pega o dados corrente da linha
			EventoGlobal rp = dados.get(rowIndex);

			// retorna o valor da coluna
			switch (columnIndex) {

			case id_evento:
				return rp.getId_evento();

			case tipo: {
				int tipo_event = rp.getTipo_evento();

				if (tipo_event == 0) {
					
					return "EVENTO TRABALHISTA-FERIADO";
				}

			}
			case data_evento:
				return rp.getData_evento();
			case hora_evento: {
				return rp.getHora_evento();
			}
			case descricao: {
			    return rp.getDescricao();
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
			EventoGlobal recebimento = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public EventoGlobal getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(EventoGlobal nota) {
			return dados.indexOf(nota);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(EventoGlobal nota) {
			dados.add(nota);
			fireTableRowsInserted(indexOf(nota), indexOf(nota));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<EventoGlobal> dadosIn) {
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
		public void onRemove(EventoGlobal nota) {
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

	public static LocalDateTime toLocalDateTime(Calendar calendar) {
		if (calendar == null) {
			return null;
		}
		TimeZone tz = calendar.getTimeZone();
		ZoneId zid = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
		return LocalDateTime.ofInstant(calendar.toInstant(), zid);
	}

}
