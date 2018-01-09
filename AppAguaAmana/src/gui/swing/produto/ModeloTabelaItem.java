package gui.swing.produto;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import modelo.Item;

public class ModeloTabelaItem extends AbstractTableModel {
    
	private static final long serialVersionUID = 1L;
	private List<Item> itens;  
    
    public ModeloTabelaItem(List<Item> itens) {  
        this.itens = itens;   
    }  
      
    public Object getValueAt(int rowIndex, int columnIndex) {  
        Item item = (Item) itens.get(rowIndex);  
        if (item != null) {  
            switch (columnIndex) {  
                case 0:
                    return item.getNome();
                case 1:
                	return item.getFornecedor().getNome();
                case 2:
                	return item.getLimiteCritico();
                case 3:
                	return item.getQuantidade();
            }  
        }  
        return null;  
    }  
 
    public int getRowCount() {  
        if (itens != null) {  
            return this.itens.size();  
        }  
          
        return 0;  
    }  
  
    public int getColumnCount() {  
        return 4;  
    }  
      
    public Item getItem(int row) {  
        if (row >= 0) {  
            return (Item) this.itens.get(row);  
        }  
        return null;  
    }  
    
}
