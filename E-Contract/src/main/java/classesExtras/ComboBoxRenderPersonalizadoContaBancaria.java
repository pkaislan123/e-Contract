package main.java.classesExtras;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import main.java.cadastros.CadastroCliente;
import main.java.cadastros.ContaBancaria;



public class ComboBoxRenderPersonalizadoContaBancaria extends DefaultListCellRenderer{
	 @Override
	    public Component getListCellRendererComponent(JList<? extends Object> list,
	            Object value, int index, boolean isSelected, boolean cellHasFocus) {
	        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	         
	        if (value instanceof ContaBancaria) {
	        	ContaBancaria cc = (ContaBancaria) value;
	            setText("Banco: " + cc.getBanco() + " Ag: " + cc.getAgencia() + " CC: " + cc.getConta());
	        }
	        return this;
	    }


}
