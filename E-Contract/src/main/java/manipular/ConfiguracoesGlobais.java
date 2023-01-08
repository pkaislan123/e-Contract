/*
 * Decompiled with CFR 0.151.
 */
package main.java.manipular;

import main.java.cadastros.CadastroBaseArquivos;
import main.java.cadastros.CadastroBaseDados;
import main.java.cadastros.CadastroNuvem;
import main.java.cadastros.CadastroZapMessenger;
import main.java.cadastros.PropriedadesGerais;

public class ConfiguracoesGlobais {
    private CadastroBaseArquivos servidor_arquivos;
    private int codigoSequencial;
    private CadastroBaseDados baseDados;
    private CadastroZapMessenger zap_zap;
    private CadastroNuvem nuvem;
    private String pasta_romaneios;
    private String ip_relogio;
    private PropriedadesGerais props;

    public PropriedadesGerais getProps() {
        return this.props;
    }

    public void setPropriedadesGerais(PropriedadesGerais props) {
        this.props = props;
    }

    public String getIp_relogio() {
        return this.ip_relogio;
    }

    public void setIp_relogio(String ip_relogio) {
        this.ip_relogio = ip_relogio;
    }

    public String getPasta_romaneios() {
        return this.pasta_romaneios;
    }

    public void setPasta_romaneios(String pasta_romaneios) {
        this.pasta_romaneios = pasta_romaneios;
    }

    public CadastroNuvem getNuvem() {
        return this.nuvem;
    }

    public void setNuvem(CadastroNuvem nuvem) {
        this.nuvem = nuvem;
    }

    public String getServidorUnidade() {
        return "\\\\" + this.getServidor_arquivos().getServidor() + "\\" + this.getServidor_arquivos().getUnidade() + "\\";
    }

    public CadastroBaseDados getBaseDados() {
        return this.baseDados;
    }

    public void setBaseDados(CadastroBaseDados baseDados) {
        this.baseDados = baseDados;
    }

    public void setCodigoSequencial(int codigo) {
        this.codigoSequencial = codigo;
    }

    public int getCodigoSequencial() {
        return this.codigoSequencial;
    }

    public CadastroBaseArquivos getServidor_arquivos() {
        return this.servidor_arquivos;
    }

    public CadastroZapMessenger getZap_zap() {
        return this.zap_zap;
    }

    public void setZap_zap(CadastroZapMessenger zap_zap) {
        this.zap_zap = zap_zap;
    }

    public void setServidor_arquivos(CadastroBaseArquivos servidor_arquivos) {
        this.servidor_arquivos = servidor_arquivos;
    }
}

