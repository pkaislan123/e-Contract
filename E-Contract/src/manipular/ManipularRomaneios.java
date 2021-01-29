package manipular;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import cadastros.CadastroLogin;
import cadastros.CadastroNFe;
import cadastros.CadastroRomaneio;
import gui.TelaNotasFiscais;
import outros.DadosGlobais;
import outros.MyFileVisitor;
import outros.TratarDados;
import tratamento_proprio.Log;

public class ManipularRomaneios {
	private JFileChooser fileChooserGlobal;
	private ArrayList<String> listadeArquivos = new ArrayList<>();

	private ArrayList<CadastroRomaneio> romaneios = new ArrayList<>();

	private String caminho;
	private JTable table;
	private int countArquivos;
	private int countPDF;
	private int countNotas;

	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;

	private JDialog tela_pai;
	
	public ManipularRomaneios(String _caminho) {
		getDadosGlobais();
		caminho = _caminho;

	}
	
	
	public ArrayList<CadastroRomaneio> tratar() {

		MyFileVisitor arquivos = new MyFileVisitor();
		Path source = Paths.get(caminho);
		try {
			Files.walkFileTree(source, arquivos);
			listadeArquivos = arquivos.getArquivos();

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		for (int i = 0; i < listadeArquivos.size(); i++) {
			// System.out.println(listadeArquivos.get(i).toString());
			countArquivos++;
		}

		for (int i = 0; i < listadeArquivos.size(); i++) {
			if (listadeArquivos.get(i).endsWith(".pdf") || listadeArquivos.get(i).endsWith(".Pdf")) {
				countPDF++;

				File file = new File(listadeArquivos.get(i).toString());
				CadastroRomaneio romaneio = filtrar(file);

				if (romaneio != null) {
					romaneios.add(romaneio);
					countNotas++;

				}

				// enviar nota para a tela pai
				// System.out.println("enviando nota para a tela pai");

				// System.out.print("Numero de arquivos: " + countArquivos);
				// System.out.print("Numero de PDF's: " + countPDF);

				// System.out.print("Numero de Notas: " + countNotas);

			} else {
				String nomeArquivo = listadeArquivos.get(i).toString();
				// System.out.println("O arquivo " + nomeArquivo + " não é um PDF");
			}
		}

		return romaneios;
	}
	
	
	public CadastroRomaneio filtrar(File file) {
		CadastroRomaneio romaneio = new CadastroRomaneio();

		 System.out.println("caminho do arquivo: " + file.getAbsolutePath());

		try (PDDocument document = PDDocument.load(file)) {

			if (!document.isEncrypted()) {

				PDFTextStripperByArea stripper = new PDFTextStripperByArea();
				stripper.setSortByPosition(true);

				PDFTextStripper tStripper = new PDFTextStripper();

				String pdfFileInText = tStripper.getText(document);

				String lines[] = pdfFileInText.split("\r\n");
				// String linhas = Arrays.toString(lines);
				//String demais = lines[118];
				// System.out.println("Demais: " + demais);
				//String separados[] = demais.split(" ");
				/*
				 * for (String line : separados) { System.out.println(line); }
				 */
				
				 // for (String line : lines) { System.out.println(line); }
				 

				String tratar = Arrays.toString(lines);
				// tratar = tratar.replaceAll("\n", "*");
				//System.out.println(tratar);
				TratarDados tratamentoDados = new TratarDados(tratar);

				for(String linha : lines) {
					
					romaneio = tratar_romaneio(lines ,file);

				}

				

			}
			return romaneio;

		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "Erro ao ler romaneio\nErro:  " + e1.getMessage() + "\nConsulte o Administrador");
			return null;
		}
	}
	
	public CadastroRomaneio tratar_romaneio (String lines[], File file) {
		
		CadastroRomaneio romaneio = new CadastroRomaneio();

		romaneio.setCaminho_arquivo(file.getAbsolutePath());

	   String tratar = Arrays.toString(lines);
      // tratar = tratar.replaceAll("\n", "*");
       System.out.println(tratar);
		
		return null;
		
	}
	
	public void setPai(JDialog _pai) {
		this.tela_pai = _pai;

	}

	private void getDadosGlobais() {
		// gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		GerenciadorLog = dados.getGerenciadorLog();
		configs_globais = dados.getConfigs_globais();

		// usuario logado
		login = dados.getLogin();

	}
	
	
	
}
