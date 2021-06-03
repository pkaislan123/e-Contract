

package main.java.classesExtras;

import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import main.java.cadastros.CadastroSafra;
import main.java.cadastros.CadastroSilo;




public class ComboBoxPersonalizadoSilo extends AbstractListModel<CadastroSilo> implements ComboBoxModel<CadastroSilo> {
    private ArrayList<CadastroSilo> lista_cc = new ArrayList<>();

    
    /* Seleciona um objeto na caixa de seleção */
    private CadastroSilo cc_selecionado;
    
@Override
public int getSize() {
     return lista_cc.size();
}

@Override
public CadastroSilo getElementAt(int indice) {
	// TODO Auto-generated method stub
	CadastroSilo t = lista_cc.get(indice);
     return t;
     }

@Override
public Object getSelectedItem() {
	// TODO Auto-generated method stub
	return cc_selecionado;
}

@Override
public void setSelectedItem(Object item) {
	cc_selecionado = (CadastroSilo) item;	
}


public void addCC(CadastroSilo cc) {
	lista_cc.add(cc);
    //fireIntervalAdded(this, getSize() - 1, getSize() - 1);
    //setSelectedItem(lista_safras.get(getSize() - 1));
}

public void resetar() {
	lista_cc.clear();
}


}
