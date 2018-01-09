package gui.swing.equipamento;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import modelo.ManutencaoEquipamento;

public class ModeloTabelaManutencao extends AbstractTableModel {
    
	private static final long serialVersionUID = 1L;
	private List<ManutencaoEquipamento> manutencao;  
    
    public ModeloTabelaManutencao(List<ManutencaoEquipamento> manutencao) {  
        this.manutencao = manutencao;   
    }  

    public Object getValueAt(int rowIndex, int columnIndex) {  
        ManutencaoEquipamento man = (ManutencaoEquipamento) manutencao.get(rowIndex);  
        if (manutencao != null) {  
            switch (columnIndex) {  
                case 0:
                    return man.getData();
                case 1:
                	if(man.getTipo())
                		return "Corretiva";
                    else return "Preventiva";
                case 2:
                    return man.getObs();
            }  
        }  
        return null;  
    }  
 
    public int getRowCount() {  
        if (manutencao != null) {  
            return this.manutencao.size();  
        }  
          
        return 0;  
    }  
  
    public int getColumnCount() {  
        return 3;  
    }  
      
    public ManutencaoEquipamento getManutencao(int row) {  
        if (row >= 0) {  
            return (ManutencaoEquipamento) this.manutencao.get(row);  
        }  
        return null;  
    }  

}
