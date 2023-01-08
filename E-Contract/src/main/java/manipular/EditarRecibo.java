package main.java.manipular;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.text.MaskFormatter;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroModelo;
import main.java.cadastros.CondicaoPagamento;
import main.java.cadastros.FinanceiroPagamento;
import main.java.cadastros.InstituicaoBancaria;
import main.java.cadastros.Lancamento;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoCondicaoPagamentos;
import main.java.conexaoBanco.GerenciarBancoInstituicaoBancaria;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.manipular.ConverterPdf;
import main.java.manipular.PorExtenso;
import main.java.outros.DadosGlobais;
import main.java.outros.GetData;
import main.java.tratamento_proprio.Log;
import main.java.views_personalizadas.TelaEmEspera;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.IBody;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;

public class EditarRecibo {
    private CadastroModelo modelo;
    private String path;
    FinanceiroPagamento pagamento;
    private TelaEmEspera telaInformacoes;
    private CadastroCliente[] compradores;
    private CadastroCliente[] vendedores;
    private CadastroCliente[] corretores;
    private Log GerenciadorLog;
    private CadastroLogin login;
    private ConfiguracoesGlobais configs_globais;
    private String servidor_unidade;
    private XWPFDocument document_global;
    private XWPFParagraph paragrafo_atual;
    private String texto;
    private String data_recibo;
    private ArrayList<CadastroCliente> recebedores_intermediarios;
    private Lancamento lancamento_global;

    public EditarRecibo(FinanceiroPagamento _pagamento, String _texto, String _data_recibo, ArrayList<CadastroCliente> clientes, Lancamento lancamento) {
        this.getDadosGlobais();
        this.servidor_unidade = this.configs_globais.getServidorUnidade();
        this.pagamento = _pagamento;
        this.data_recibo = _data_recibo;
        this.texto = _texto;
        this.recebedores_intermediarios = clientes;
        this.lancamento_global = lancamento;
        this.criarDocumento();
    }

    public void criarDocumento() {
        this.document_global = new XWPFDocument();
        CTSectPr sectPr = this.document_global.getDocument().getBody().addNewSectPr();
        CTPageMar pageMar = sectPr.addNewPgMar();
        pageMar.setLeft(BigInteger.valueOf(720L));
        pageMar.setTop(BigInteger.valueOf(1440L));
        pageMar.setRight(BigInteger.valueOf(720L));
        pageMar.setBottom(BigInteger.valueOf(1440L));
        this.document_global.createStyles();
    }

    public ByteArrayOutputStream preparar() {
        CondicaoPagamento condicao;
        CadastroCliente cli;
        CadastroCliente cli2;
        XWPFParagraph rodape = this.document_global.createParagraph();
        rodape.setAlignment(ParagraphAlignment.LEFT);
        XWPFParagraph title = this.document_global.createParagraph();
        title.setAlignment(ParagraphAlignment.CENTER);
        Locale ptBr = new Locale("pt", "BR");
        String valorString = NumberFormat.getCurrencyInstance(ptBr).format(this.pagamento.getValor());
        XWPFRun titleRun = title.createRun();
        titleRun.setText("RECIBO: " + new GetData().getAnoAtual() + "/" + this.pagamento.getId_pagamento() + " - " + valorString);
        titleRun.setColor("000000");
        titleRun.setBold(true);
        titleRun.setUnderline(UnderlinePatterns.SINGLE);
        titleRun.setFontFamily("Arial");
        titleRun.setFontSize(14);
        XWPFParagraph num_contrato = this.document_global.createParagraph();
        num_contrato.setAlignment(ParagraphAlignment.CENTER);
        String assinatura_recebedor = "";
        CadastroCliente recebedor = null;
        this.adicionarTraco(true, 2);
        if (this.pagamento.getTipo_recebedor() == 0) {
            CadastroCliente cli3;
            InstituicaoBancaria ib;
            if (this.pagamento.getId_recebedor() > 0 && (ib = new GerenciarBancoInstituicaoBancaria().getInstituicaoBancaria(this.pagamento.getId_recebedor())) != null && ib.getId_cliente() > 0 && (cli3 = new GerenciarBancoClientes().getCliente(ib.getId_cliente())) != null) {
                this.adicionarParte(0, cli3, this.lancamento_global.getTipo_lancamento());
                recebedor = cli3;
            }
        } else if (this.pagamento.getTipo_recebedor() == 1 && this.pagamento.getId_recebedor() > 0 && (cli2 = new GerenciarBancoClientes().getCliente(this.pagamento.getId_recebedor())) != null) {
            this.adicionarParte(0, cli2, this.lancamento_global.getTipo_lancamento());
            recebedor = cli2;
        }
        this.adicionarTraco(true, 2);
        String assinatura_pagador = "";
        String nome_pagador = "";
        if (this.pagamento.getTipo_pagador() == 0) {
            InstituicaoBancaria ib;
            if (this.pagamento.getId_pagador() > 0 && (ib = new GerenciarBancoInstituicaoBancaria().getInstituicaoBancaria(this.pagamento.getId_pagador())) != null) {
                assinatura_pagador = ib.getNome_instituicao_bancaria();
                CadastroCliente cli4 = new GerenciarBancoClientes().getCliente(ib.getId_cliente());
                if (cli4 != null) {
                    this.adicionarParte(1, cli4, this.lancamento_global.getTipo_lancamento());
                   
                }
            }
        } else if (this.pagamento.getTipo_pagador() == 1 && this.pagamento.getId_pagador() > 0 && (cli = new GerenciarBancoClientes().getCliente(this.pagamento.getId_pagador())) != null) {
            this.adicionarParte(1, cli, this.lancamento_global.getTipo_lancamento());
           
        }
        this.adicionarTraco(true, 2);
        String forma_pagamento = "";
        GerenciarBancoCondicaoPagamentos gerenciar = new GerenciarBancoCondicaoPagamentos();
        if (this.pagamento.getId_condicao_pagamento() > 0 && (condicao = gerenciar.getCondicaoPagamento(this.pagamento.getId_condicao_pagamento())) != null) {
            forma_pagamento = condicao.getNome_condicao_pagamento();
        }
        this.texto = this.texto.replace("[forma_pagamento]", forma_pagamento);
        this.texto = this.texto.replace("[data_pagamento]", this.pagamento.getData_pagamento());
        this.texto = this.texto.replace("[descricao_pagamento]", this.pagamento.getDescricao());
        if (this.recebedores_intermediarios != null && this.recebedores_intermediarios.size() > 0) {
            if (this.recebedores_intermediarios.size() == 1) {
                this.texto = String.valueOf(this.texto) + "\nA import\u00e2ncia desse recibo foi paga ao [RECEBEDOR] atrav\u00e9s de um [RECEBEDOR INTERMEDI\u00c1RIO], descrito a seguir:\n";
                this.texto = String.valueOf(this.texto) + "[_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ __ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _]\n";
                for (CadastroCliente recebedor_inter : this.recebedores_intermediarios) {
                    this.texto = String.valueOf(this.texto) + this.adicionarRecebedorIntermediario(recebedor_inter);
                }
                this.texto = String.valueOf(this.texto) + "[_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ __ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _]\n";
            }
            if (this.recebedores_intermediarios.size() > 1) {
                this.texto = String.valueOf(this.texto) + "A import\u00e2ncia desse recibo foi paga ao [RECEBEDOR] atrav\u00e9s de [RECEBEDORES INTERMEDI\u00c1RIOS], descritos a seguir:\n";
                this.texto = String.valueOf(this.texto) + "[_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ __ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _]\n";
                for (CadastroCliente recebedor_inter : this.recebedores_intermediarios) {
                    this.texto = String.valueOf(this.texto) + this.adicionarRecebedorIntermediario(recebedor_inter) + "\n";
                    this.texto = String.valueOf(this.texto) + "[_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ __ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _]\n";
                }
            }
        }
        this.substituirTexto(this.texto);
        this.adicionarData();
        if (recebedor.getTipo_pessoa() == 0) {
            this.adicionarCamposAssinaturas(recebedor.getNome_empresarial(), 0, recebedor.getCpf());
        } else if (recebedor.getTipo_pessoa() == 1) {
            this.adicionarCamposAssinaturas(recebedor.getNome_fantaia(), 1, recebedor.getCnpj());
        }
        if (this.recebedores_intermediarios != null && this.recebedores_intermediarios.size() > 0) {
            for (CadastroCliente recebedor_inter : this.recebedores_intermediarios) {
                if (recebedor_inter.getTipo_pessoa() == 0) {
                    this.adicionarCamposAssinaturas(recebedor_inter.getNome_empresarial(), 0, recebedor_inter.getCpf());
                    continue;
                }
                if (recebedor_inter.getTipo_pessoa() != 1) continue;
                this.adicionarCamposAssinaturas(recebedor_inter.getNome_fantaia(), 1, recebedor_inter.getCnpj());
            }
        }
        try {
            CTP ctP = CTP.Factory.newInstance();
            CTText t = ctP.addNewR().addNewT();
            XWPFParagraph cabecalho = new XWPFParagraph(ctP, (IBody)this.document_global);
            XWPFRun cabecalhoRun = cabecalho.createRun();
            cabecalhoRun.setFontSize(16);
            cabecalhoRun.setFontFamily("Arial Black");
            cabecalhoRun.setText("LD ARMAZ\u00c9NS GERAIS");
            cabecalhoRun.setUnderline(UnderlinePatterns.SINGLE);
            cabecalhoRun.setColor("00A000");
            XWPFParagraph[] pars = new XWPFParagraph[]{cabecalho};
            pars[0].setAlignment(ParagraphAlignment.LEFT);
            XWPFHeaderFooterPolicy hfPolicy = this.document_global.createHeaderFooterPolicy();
            hfPolicy.createHeader(XWPFHeaderFooterPolicy.DEFAULT, pars);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao criar cabecalho e rodape do contrato!\nConsulte o administrador do sistema!");
            e.printStackTrace();
        }
        ByteArrayOutputStream saida_apos_edicao = new ByteArrayOutputStream();
        try {
            this.document_global.write((OutputStream)saida_apos_edicao);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return saida_apos_edicao;
    }

    public void adicionarTraco(boolean negrito, int flag) {
        XWPFParagraph traco = this.document_global.createParagraph();
        traco.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun tracotitleRun = traco.createRun();
        if (flag == 1) {
            tracotitleRun.setText("________________________________________________________________________________");
        } else {
            tracotitleRun.setText("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ __ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ ");
        }
        tracotitleRun.setColor("000000");
        tracotitleRun.setBold(negrito);
        tracotitleRun.setFontFamily("Arial");
        tracotitleRun.setFontSize(12);
    }

    public void substituirTexto(String text_amostra) {
        String[] separador_paragrafo;
        String[] arrstring = separador_paragrafo = text_amostra.split("\n");
        int n = separador_paragrafo.length;
        for (int i = 0; i < n; ++i) {
            String[] separador_palabras;
            String paragrafo = arrstring[i];
            this.criarParagrafo(2);
            paragrafo = paragrafo.replaceAll(" ", "&");
            String[] arrstring2 = separador_palabras = paragrafo.split("&");
            int n2 = separador_palabras.length;
            for (int j = 0; j < n2; ++j) {
                String palavra = arrstring2[j];
                if (palavra.contains("[") || palavra.contains("]")) {
                    this.adicionarTextoParagrafoAtual(String.valueOf(palavra.replaceAll("[\\[\\]]", "")) + " ", true);
                    continue;
                }
                if (palavra.equals("valor_pagamento_decimal")) {
                    Locale ptBr = new Locale("pt", "BR");
                    String valorString = NumberFormat.getCurrencyInstance(ptBr).format(this.pagamento.getValor());
                    System.out.println(valorString);
                    this.adicionarTextoParagrafoAtual(String.valueOf(valorString) + " ", true);
                    continue;
                }
                if (palavra.equals("valor_pagamento_extenso")) {
                    String valor_extenso = new PorExtenso(this.pagamento.getValor()).toString();
                    this.adicionarTextoParagrafoAtual(palavra.replace("valor_pagamento_extenso", String.valueOf(valor_extenso.toLowerCase())), false);
                    continue;
                }
                this.adicionarTextoParagrafoAtual(String.valueOf(palavra) + " ", false);
            }
        }
    }

    public void criarParagrafo(int alinhamento) {
        XWPFParagraph paragrafo = this.document_global.createParagraph();
        if (alinhamento == 0) {
            paragrafo.setAlignment(ParagraphAlignment.CENTER);
        } else if (alinhamento == 1) {
            paragrafo.setAlignment(ParagraphAlignment.RIGHT);
        } else if (alinhamento == -1) {
            paragrafo.setAlignment(ParagraphAlignment.LEFT);
        } else if (alinhamento == 2) {
            paragrafo.setAlignment(ParagraphAlignment.BOTH);
        }
        this.paragrafo_atual = paragrafo;
    }

    public void adicionarTextoParagrafoAtual(String texto, boolean negrito) {
        XWPFRun run = this.paragrafo_atual.createRun();
        run.setText(texto);
        run.setColor("000000");
        run.setBold(negrito);
        run.setFontFamily("Times New Roman");
        run.setFontSize(10);
    }

    public void saltarLinhaParagrafo() {
        XWPFRun corretortitleRun = this.paragrafo_atual.createRun();
        corretortitleRun.addBreak();
    }

    public void getDadosGlobais() {
        DadosGlobais dados = DadosGlobais.getInstance();
        this.GerenciadorLog = dados.getGerenciadorLog();
        this.configs_globais = dados.getConfigs_globais();
        this.login = dados.getLogin();
    }

    public void adicionarData() {
        String data_extenso = "";
        SimpleDateFormat formato_data = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date data_formatada = formato_data.parse(this.data_recibo);
            Date data = new Date();
            Locale local = new Locale("pt", "BR");
            SimpleDateFormat formato = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", local);
            LocalDate data_local = data_formatada.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            DayOfWeek dia_da_semana = data_local.getDayOfWeek();
            data_extenso = String.valueOf(dia_da_semana.getDisplayName(TextStyle.FULL, local)) + ", " + formato.format(data_formatada);
        }
        catch (ParseException e) {
            System.out.println("Nao foi possivel converter a data");
            e.printStackTrace();
        }
        this.substituirTexto("\n\nPor ser verdade, firma-se o presente.");
        this.substituirTexto("Guarda-Mor MG " + data_extenso);
    }

    public void adicionarCamposAssinaturas(String assinatura_recebedor, int tipo_identificacao, String identificacao) {
        this.criarParagrafo(0);
        String[] separados = assinatura_recebedor.split(" ");
        String nome_assinatura_negrito = "";
        String[] arrstring = separados;
        int n = separados.length;
        for (int i = 0; i < n; ++i) {
            String palavra = arrstring[i];
            nome_assinatura_negrito = String.valueOf(nome_assinatura_negrito) + "[" + palavra + "]" + " ";
        }
        if (tipo_identificacao == 0) {
            String cpf = "";
            try {
                MaskFormatter formater_cpf = new MaskFormatter("###.###.###-##");
                formater_cpf.setValueContainsLiteralCharacters(false);
                cpf = formater_cpf.valueToString(identificacao);
            }
            catch (Exception formater_cpf) {
                // empty catch block
            }
            this.substituirTexto("[_______________________________________________________________]                                                                                         [" + nome_assinatura_negrito.toUpperCase() + "]\nCPF: [" + cpf + "]");
        } else if (tipo_identificacao == 1) {
            String cnpj = "";
            try {
                MaskFormatter formater_cnpj = new MaskFormatter("##.###.###/####-##");
                formater_cnpj.setValueContainsLiteralCharacters(false);
                cnpj = formater_cnpj.valueToString(identificacao);
            }
            catch (Exception exception) {
                // empty catch block
            }
            this.substituirTexto("[_______________________________________________________________]                                                                                         [" + nome_assinatura_negrito.toUpperCase() + "]\nCNPJ: [" + cnpj + "]");
        }
    }

    public boolean criarArquivo(String caminho_completo) {
        try {
            FileOutputStream outputStream = new FileOutputStream(String.valueOf(caminho_completo) + ".docx");
            this.document_global.write((OutputStream)outputStream);
            outputStream.close();
            ConverterPdf converter_pdf = new ConverterPdf();
            if (converter_pdf.word_pdf_file(caminho_completo)) {
                System.out.println("Arquivo pdf convertido e salvo!");
                return true;
            }
            return false;
        }
        catch (IOException e) {
            System.out.println("erro ao criar o arquivo fisico, erro: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public void adicionarParte(int flag, CadastroCliente cliente, int tipo_lancamento) {
        XWPFParagraph parte = this.document_global.createParagraph();
        parte.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun titlerun = parte.createRun();
        titlerun.setColor("000000");
        titlerun.setBold(true);
        titlerun.setFontFamily("Times New Roman");
        titlerun.setFontSize(10);
        if (flag == 0) {
            titlerun.setText("RECEBEDOR:");
        } else if (flag == 1) {
            if (tipo_lancamento == 3 || tipo_lancamento == 4) {
                titlerun.setText("PAGADOR:");
            } else {
                titlerun.setText("DEVEDOR:");
            }
        }
        titlerun.addBreak();
        String nome_corretor = "";
        nome_corretor = cliente.getTipo_pessoa() == 0 ? cliente.getNome_empresarial().toUpperCase().trim() : cliente.getNome_fantaia().toUpperCase().trim();
        XWPFRun corretorNomerun = parte.createRun();
        corretorNomerun.setText(nome_corretor);
        corretorNomerun.setColor("000000");
        corretorNomerun.setFontFamily("Times New Roman");
        corretorNomerun.setFontSize(10);
        corretorNomerun.setBold(true);
        XWPFRun adicional1run = parte.createRun();
        adicional1run.setColor("000000");
        adicional1run.setFontFamily("Times New Roman");
        adicional1run.setFontSize(10);
        adicional1run.setBold(true);
        try {
            String ie = "";
            if (cliente.getIe().length() > 9) {
                MaskFormatter formater_ie = new MaskFormatter("#########.##-##");
                formater_ie.setValueContainsLiteralCharacters(false);
                ie = formater_ie.valueToString(cliente.getIe());
            } else {
                try {
                    MaskFormatter formater_ie_go = new MaskFormatter("##.###.###-#");
                    formater_ie_go.setValueContainsLiteralCharacters(false);
                    ie = formater_ie_go.valueToString(cliente.getIe());
                }
                catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }
            adicional1run.setText(", Inscri\u00e7\u00e3o Estadual: " + ie + " ");
        }
        catch (Exception ie) {
            // empty catch block
        }
        XWPFRun ocupacaoRun = parte.createRun();
        ocupacaoRun.setColor("000000");
        ocupacaoRun.setFontFamily("Times New Roman");
        ocupacaoRun.setFontSize(10);
        ocupacaoRun.setBold(false);
        try {
            ocupacaoRun.setText(", " + cliente.getOcupacao().toUpperCase().trim());
        }
        catch (Exception formater_ie_go) {
            // empty catch block
        }
        XWPFRun enderecoCorretorrun = parte.createRun();
        enderecoCorretorrun.setColor("000000");
        enderecoCorretorrun.setFontFamily("Times New Roman");
        enderecoCorretorrun.setFontSize(10);
        enderecoCorretorrun.setBold(true);
        enderecoCorretorrun.setText(" , residente no endere\u00e7o " + cliente.getRua().toUpperCase().trim() + ", n\u00ba " + cliente.getNumero() + ", Bairro: " + cliente.getBairro().toUpperCase().trim());
        XWPFRun adicional2run = parte.createRun();
        adicional2run.setColor("000000");
        adicional2run.setFontFamily("Times New Roman");
        adicional2run.setFontSize(10);
        adicional2run.setBold(false);
        adicional2run.setText(", na Cidade de ");
        XWPFRun cidadeRun = parte.createRun();
        cidadeRun.setColor("000000");
        cidadeRun.setFontFamily("Times New Roman");
        cidadeRun.setFontSize(10);
        cidadeRun.setBold(true);
        cidadeRun.setText(cliente.getCidade().toUpperCase().trim());
        XWPFRun infoEstadoRun = parte.createRun();
        infoEstadoRun.setColor("000000");
        infoEstadoRun.setFontFamily("Times New Roman");
        infoEstadoRun.setFontSize(10);
        infoEstadoRun.setBold(false);
        infoEstadoRun.setText(" - Estado de ");
        XWPFRun estadoRUn = parte.createRun();
        estadoRUn.setColor("000000");
        estadoRUn.setFontFamily("Times New Roman");
        estadoRUn.setFontSize(10);
        estadoRUn.setBold(true);
        estadoRUn.setText(cliente.getUf().toUpperCase());
        XWPFRun adicional3ceprun = parte.createRun();
        adicional3ceprun.setColor("000000");
        adicional3ceprun.setFontFamily("Times New Roman");
        adicional3ceprun.setFontSize(10);
        adicional3ceprun.setBold(false);
        adicional3ceprun.setText(" CEP: ");
        XWPFRun cepRun = parte.createRun();
        cepRun.setColor("000000");
        cepRun.setFontFamily("Times New Roman");
        cepRun.setFontSize(10);
        cepRun.setBold(true);
        String cep = "";
        try {
            MaskFormatter formater_cep = new MaskFormatter("#####-###");
            formater_cep.setValueContainsLiteralCharacters(false);
            cep = formater_cep.valueToString(cliente.getCep().replaceAll("[^0-9]", ""));
        }
        catch (Exception formater_cep) {
            // empty catch block
        }
        cepRun.setText(cep);
        XWPFRun adicionarCpfRun = parte.createRun();
        adicionarCpfRun.setColor("000000");
        adicionarCpfRun.setFontFamily("Times New Roman");
        adicionarCpfRun.setFontSize(10);
        adicionarCpfRun.setBold(false);
        XWPFRun cpfRun = parte.createRun();
        cpfRun.setColor("000000");
        cpfRun.setFontFamily("Times New Roman");
        cpfRun.setFontSize(10);
        cpfRun.setBold(true);
        if (cliente.getTipo_pessoa() == 0) {
            adicionarCpfRun.setText(", inscrito no CPF sob n\u00ba ");
            String cpf = "";
            try {
                MaskFormatter formater_cpf = new MaskFormatter("###.###.###-##");
                formater_cpf.setValueContainsLiteralCharacters(false);
                cpf = formater_cpf.valueToString(cliente.getCpf());
            }
            catch (Exception formater_cpf) {
                // empty catch block
            }
            cpfRun.setText(cpf);
        } else {
            adicionarCpfRun.setText(", inscrito no CNPJ sob n\u00ba ");
            String cnpj = "";
            try {
                MaskFormatter formater_cnpj = new MaskFormatter("##.###.###/####-##");
                formater_cnpj.setValueContainsLiteralCharacters(false);
                cnpj = formater_cnpj.valueToString(cliente.getCnpj());
            }
            catch (Exception exception) {
                // empty catch block
            }
            cpfRun.setText(cnpj);
        }
        XWPFRun finalRun = parte.createRun();
        finalRun.setColor("000000");
        finalRun.setFontFamily("Times New Roman");
        finalRun.setFontSize(10);
        finalRun.setBold(false);
        if (flag == 0) {
            finalRun.setText(".  A pessoa supra indicada ser\u00e1 doravante denominada \u201cRECEBEDOR\u201d.");
        } else if (flag == 1) {
            finalRun.setText(".  A pessoa supra indicada ser\u00e1 doravante denominada \u201cDEVEDOR\u201d.");
        }
    }

    public String adicionarRecebedorIntermediario(CadastroCliente cliente) {
        String texto = "[";
        texto = cliente.getTipo_pessoa() == 0 ? String.valueOf(cliente.getNome_empresarial().toUpperCase().trim()) + "]" : String.valueOf(cliente.getNome_fantaia().toUpperCase().trim()) + "]";
        try {
            String ie = "";
            MaskFormatter formater_ie = new MaskFormatter("#########.##-##");
            formater_ie.setValueContainsLiteralCharacters(false);
            ie = formater_ie.valueToString(cliente.getIe());
            texto = String.valueOf(texto) + ", Inscri\u00e7\u00e3o Estadual: [" + ie + "] ";
        }
        catch (Exception ie) {
            // empty catch block
        }
        try {
            texto = String.valueOf(texto) + ", " + cliente.getOcupacao().toUpperCase().trim();
        }
        catch (Exception ie) {
            // empty catch block
        }
        texto = String.valueOf(texto) + " , residente no endere\u00e7o [" + cliente.getRua().toUpperCase().trim() + "], n\u00ba [" + cliente.getNumero() + "], Bairro: [" + cliente.getBairro().toUpperCase().trim();
        texto = String.valueOf(texto) + "], na Cidade de [";
        texto = String.valueOf(texto) + cliente.getCidade().toUpperCase().trim() + "]";
        texto = String.valueOf(texto) + " - Estado de [";
        texto = String.valueOf(texto) + cliente.getUf().toUpperCase() + "]";
        texto = String.valueOf(texto) + " CEP: [";
        String cep = "";
        try {
            MaskFormatter formater_cep = new MaskFormatter("#####-###");
            formater_cep.setValueContainsLiteralCharacters(false);
            cep = formater_cep.valueToString(cliente.getCep().replaceAll("[^0-9]", ""));
        }
        catch (Exception formater_cep) {
            // empty catch block
        }
        texto = String.valueOf(texto) + cep + "]";
        if (cliente.getTipo_pessoa() == 0) {
            texto = String.valueOf(texto) + ", inscrito no CPF sob n\u00ba [";
            String cpf = "";
            try {
                MaskFormatter formater_cpf = new MaskFormatter("###.###.###-##");
                formater_cpf.setValueContainsLiteralCharacters(false);
                cpf = formater_cpf.valueToString(cliente.getCpf());
            }
            catch (Exception formater_cpf) {
                // empty catch block
            }
            texto = String.valueOf(texto) + cpf + "]";
        } else {
            texto = String.valueOf(texto) + ", inscrito no CNPJ sob n\u00ba [";
            String cnpj = "";
            try {
                MaskFormatter formater_cnpj = new MaskFormatter("##.###.###/####-##");
                formater_cnpj.setValueContainsLiteralCharacters(false);
                cnpj = formater_cnpj.valueToString(cliente.getCnpj());
            }
            catch (Exception exception) {
                // empty catch block
            }
            texto = String.valueOf(texto) + cnpj + "]";
        }
        return texto;
    }
}