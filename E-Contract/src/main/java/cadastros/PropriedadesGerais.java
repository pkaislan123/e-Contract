/*
 * Decompiled with CFR 0.151.
 */
package main.java.cadastros;

public class PropriedadesGerais {
    private String pasta_instalacao;

    public String getPasta_instalacao() {
        return this.pasta_instalacao;
    }

    public void setPasta_instalacao(String pasta_instalacao) {
        this.pasta_instalacao = pasta_instalacao;
    }

  

    public String toString() {
        return "PropriedadesGerais(pasta_instalacao=" + this.getPasta_instalacao() + ")";
    }

    public PropriedadesGerais(String pasta_instalacao) {
        this.pasta_instalacao = pasta_instalacao;
    }

    public PropriedadesGerais() {
    }
}

