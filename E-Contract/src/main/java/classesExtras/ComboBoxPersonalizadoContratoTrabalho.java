
package main.java.classesExtras;

import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import main.java.cadastros.CadastroFuncionarioAdmissao;
import main.java.cadastros.CadastroSafra;
import main.java.cadastros.ContaBancaria;
import main.java.gui.TelaFuncionariosCadastroContratoTrabalho;




public class ComboBoxPersonalizadoContratoTrabalho extends AbstractListModel<CadastroFuncionarioAdmissao> implements ComboBoxModel<CadastroFuncionarioAdmissao> {
    private ArrayList<CadastroFuncionarioAdmissao> lista_cc = new ArrayList<>();

    
    /* Seleciona um objeto na caixa de seleção */
    private CadastroFuncionarioAdmissao cc_selecionado;
    
@Override
public int getSize() {
     return lista_cc.size();
}

@Override
public CadastroFuncionarioAdmissao getElementAt(int indice) {
	// TODO Auto-generated method stub
	CadastroFuncionarioAdmissao t = lista_cc.get(indice);
     return t;
     }

@Override
public Object getSelectedItem() {
	// TODO Auto-generated method stub
	return cc_selecionado;
}

@Override
public void setSelectedItem(Object item) {
	cc_selecionado = (CadastroFuncionarioAdmissao) item;	
}


public void addCC(CadastroFuncionarioAdmissao cc) {
	lista_cc.add(cc);
    //fireIntervalAdded(this, getSize() - 1, getSize() - 1);
    //setSelectedItem(lista_safras.get(getSize() - 1));
}

public void resetar() {
	lista_cc.clear();
}


}
