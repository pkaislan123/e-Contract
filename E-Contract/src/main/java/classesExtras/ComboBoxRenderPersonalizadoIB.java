/*
 * Decompiled with CFR 0.151.
 */
package main.java.classesExtras;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import main.java.cadastros.InstituicaoBancaria;

public class ComboBoxRenderPersonalizadoIB
extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<? extends Object> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof InstituicaoBancaria) {
            InstituicaoBancaria cc = (InstituicaoBancaria)value;
            this.setText(cc.getNome_instituicao_bancaria());
        }
        return this;
    }
}

