
package main.java.classesExtras;

import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import main.java.cadastros.CadastroSafra;
import main.java.cadastros.CentroCusto;
import main.java.cadastros.CadastroClassificador;
import main.java.cadastros.CadastroFuncionario;




public class ComboBoxPersonalizadoFuncionario extends AbstractListModel<CadastroFuncionario> implements ComboBoxModel<CadastroFuncionario> {
    private ArrayList<CadastroFuncionario> lista_cc = new ArrayList<>();

    
    /* Seleciona um objeto na caixa de seleção */
    private CadastroFuncionario cc_selecionado;
    
@Override
public int getSize() {
     return lista_cc.size();
}

@Override
public CadastroFuncionario getElementAt(int indice) {
	// TODO Auto-generated method stub
	CadastroFuncionario t = lista_cc.get(indice);
     return t;
     }

@Override
public Object getSelectedItem() {
	// TODO Auto-generated method stub
	return cc_selecionado;
}

@Override
public void setSelectedItem(Object item) {
	cc_selecionado = (CadastroFuncionario) item;	
}


public void addCC(CadastroFuncionario cc) {
	lista_cc.add(cc);
    //fireIntervalAdded(this, getSize() - 1, getSize() - 1);
    //setSelectedItem(lista_safras.get(getSize() - 1));
}

public void resetar() {
	lista_cc.clear();
}


}
