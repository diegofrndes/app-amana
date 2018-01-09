package gui.swing.equipamento;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import modelo.Equipamento;

public class ModeloTabelaEquipamento extends AbstractTableModel {
    
	private static final long serialVersionUID = 1L;
	private List<Equipamento> equipamentos;  
    
    public ModeloTabelaEquipamento(List<Equipamento> equipamentos) {  
        this.equipamentos = equipamentos;   
    }  

    public Object getValueAt(int rowIndex, int columnIndex) {  
        Equipamento equipamento = (Equipamento) equipamentos.get(rowIndex);  
        if (equipamentos != null) {  
            switch (columnIndex) {  
                case 0:
                    return equipamento.getNome();
                case 1:
                    return equipamento.getFabricante();
                case 2:
                    return equipamento.getNumeroserie();
                case 3:
                    if(equipamento.getFreq() == 1)
                    	return "1 mês";
                    else return (Integer.toString(equipamento.getFreq()) + " meses");
                case 4:
                	return equipamento.getUltmanutencao();
                case 5:
                	return equipamento.getProxmanutencao();
            }  
        }  
        return null;  
    }  
 
    public int getRowCount() {  
        if (equipamentos != null) {  
            return this.equipamentos.size();  
        }  
        return 0;  
    }  
  
    public int getColumnCount() {  
        return 6;  
    }  
      
    public Equipamento getEquipamento(int row) {  
        if (row >= 0) {  
            return (Equipamento) this.equipamentos.get(row);  
        }  
        return null;  
    }  
}
