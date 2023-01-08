/*
 * Decompiled with CFR 0.151.
 */
package main.java.classesExtras;

import java.util.ArrayList;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import main.java.cadastros.InstituicaoBancaria;

public class ComboBoxPersonalizadoIB
extends AbstractListModel<InstituicaoBancaria>
implements ComboBoxModel<InstituicaoBancaria> {
    private ArrayList<InstituicaoBancaria> lista_cc = new ArrayList();
    private InstituicaoBancaria cc_selecionado;

    @Override
    public int getSize() {
        return this.lista_cc.size();
    }

    @Override
    public InstituicaoBancaria getElementAt(int indice) {
        InstituicaoBancaria t = this.lista_cc.get(indice);
        return t;
    }

    @Override
    public Object getSelectedItem() {
        return this.cc_selecionado;
    }

    @Override
    public void setSelectedItem(Object item) {
        this.cc_selecionado = (InstituicaoBancaria)item;
    }

    public void addCC(InstituicaoBancaria cc) {
        this.lista_cc.add(cc);
    }

    public void resetar() {
        this.lista_cc.clear();
    }
}

