
package main.java.classesExtras;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import main.java.cadastros.CadastroClassificador;
import main.java.cadastros.CadastroFuncionario;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CentroCusto;



public class ComboBoxRenderPersonalizadoUsuario extends DefaultListCellRenderer{
	 @Override
	    public Component getListCellRendererComponent(JList<? extends Object> list,
	            Object value, int index, boolean isSelected, boolean cellHasFocus) {
	        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	         
	        if (value instanceof CadastroLogin) {
	        	CadastroLogin cc = (CadastroLogin) value;
	            setText(cc.getNome() + " " + cc.getSobrenome());
	        }
	        return this;
	    }


}
