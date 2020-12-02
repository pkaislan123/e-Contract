package classesExtras;

import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import com.itextpdf.text.List;

import cadastros.CadastroSafra;

public class ComboBoxPersonalizado extends AbstractListModel<CadastroSafra> implements ComboBoxModel<CadastroSafra> {
    private ArrayList<CadastroSafra> lista_safras = new ArrayList<>();

    
    /* Seleciona um objeto na caixa de seleção */
    private CadastroSafra safra_selecionada;
    
@Override
public int getSize() {
     return lista_safras.size();
}

@Override
public CadastroSafra getElementAt(int indice) {
	// TODO Auto-generated method stub
	 CadastroSafra t = lista_safras.get(indice);
     return t;
     }

@Override
public Object getSelectedItem() {
	// TODO Auto-generated method stub
	return safra_selecionada;
}

@Override
public void setSelectedItem(Object item) {
	safra_selecionada = (CadastroSafra) item;	
}


public void addSafra(CadastroSafra safra) {
	lista_safras.add(safra);
    //fireIntervalAdded(this, getSize() - 1, getSize() - 1);
    //setSelectedItem(lista_safras.get(getSize() - 1));
}

}
