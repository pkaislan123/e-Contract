package main.java.classesExtras;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import main.java.cadastros.CadastroFuncionarioAdmissao;
import main.java.cadastros.CadastroFuncionarioDescontos;
import main.java.cadastros.ContaBancaria;

public class CombBoxRenderPersonalizadoContratoTrabalho extends DefaultListCellRenderer{
	 @Override
	    public Component getListCellRendererComponent(JList<? extends Object> list,
	            Object value, int index, boolean isSelected, boolean cellHasFocus) {
	        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	         
	        if (value instanceof CadastroFuncionarioAdmissao) {
	        	CadastroFuncionarioAdmissao cc = (CadastroFuncionarioAdmissao) value;
	            setText(cc.getId_contrato() + " - " + cc.getTipo_contrato() + " - " + cc.getId_colaborador());
	        }
	        return this;
	    }


}
