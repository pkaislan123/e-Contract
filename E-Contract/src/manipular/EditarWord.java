package manipular;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import cadastros.CadastroCliente;
import cadastros.CadastroContrato;
import cadastros.CadastroLogin;
import cadastros.CadastroModelo;
import outros.DadosGlobais;
import tratamento_proprio.Log;
import views_personalizadas.TelaEmEspera;

public class EditarWord {

	
	private CadastroModelo modelo;
	private String path;

	CadastroContrato novo_contrato;
	private TelaEmEspera telaInformacoes;
	private CadastroCliente compradores [], vendedores[], corretores[];
	
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
    private String servidor_unidade;

    
	public EditarWord(CadastroModelo modelo, TelaEmEspera background, int tipoContrato)
	{
		
		this.modelo = modelo;
		this.telaInformacoes = background;
		servidor_unidade = configs_globais.getServidorUnidade();
		
		
	}
	
	
	
	public ByteArrayOutputStream alterar(CadastroContrato contrato) {
		
		System.out.println("Alterando contrato formal!");
		 this.novo_contrato = novo_contrato;
		 
			
		  compradores = novo_contrato.listaCompradores();
		  vendedores = novo_contrato.listaVendedores();
		  corretores = novo_contrato.listaCorretores();

		
		
		try {

			
		    InputStream inputStream = new ByteArrayInputStream(modelo.getArquivo());
		   System.out.println("arquivo aberto!");
		
		   
		   XWPFDocument doc = new XWPFDocument(OPCPackage.open(inputStream));
		   for (XWPFParagraph p : doc.getParagraphs()) {
		       List<XWPFRun> runs = p.getRuns();
		       if (runs != null) {
		           for (XWPFRun r : runs) {
		               String text = r.getText(0);
		               if (text != null && text.contains("vendedor")) {
		                   text = text.replace("vendedor", "sucesso");
		                   r.setText(text, 0);
		               }
		           }
		       }
		   }
		   
		   
		   inputStream.close();

			ByteArrayOutputStream saida_apos_edicao = new ByteArrayOutputStream();

		
			doc.write(new FileOutputStream("c:\\arquivo.docx"));
			doc.write(saida_apos_edicao);
			//workbook.close();
			
		return saida_apos_edicao;
			//ConverterPdf conversor = new C
		   
		}catch(Exception e ) {
			System.out.println("houve um erro ao processar o arquivo .docx");
			return null;
		}
	}
	
	
	public String  trocaVendedor(String texto_completo) {
	
		String nome_vendedor1 = "";
		
		if(vendedores[0] != null) {
			if(vendedores[0].getTipo_pessoa() == 0) {
				//pessoa fisica
				nome_vendedor1 = vendedores[0].getNome_empresarial();
			}else {
				nome_vendedor1 = vendedores[0].getNome_fantaia();

			}
		}
		
		
		texto_completo = texto_completo.replace("[nome_vendedor]", nome_vendedor1);
         return texto_completo;
		
	}
	
	public void getDadosGlobais() {
		//gerenciador de log
				DadosGlobais dados = DadosGlobais.getInstance();
				 GerenciadorLog = dados.getGerenciadorLog();
				 configs_globais = dados.getConfigs_globais();
				 
				 //usuario logado
				  login = dados.getLogin();
		
	}
	
	
}
