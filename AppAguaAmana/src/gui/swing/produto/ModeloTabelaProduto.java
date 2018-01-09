package gui.swing.produto;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import javax.swing.table.AbstractTableModel;

import modelo.Produto;

public class ModeloTabelaProduto extends AbstractTableModel {
    
	private static final long serialVersionUID = 1L;
	private List<Produto> produtosVenda;  
    
    public ModeloTabelaProduto(List<Produto> produtosVenda) {  
        this.produtosVenda = produtosVenda;   
        
    }  
      
    public Object getValueAt(int rowIndex, int columnIndex) {  
        Produto produtoVenda = (Produto) produtosVenda.get(rowIndex);  
        if (produtosVenda != null) {  
            switch (columnIndex) {  
                case 0:
                    return produtoVenda.getId();
                case 1:
                    return produtoVenda.getNome();
                case 2:
                	NumberFormat moneyFormat1 = NumberFormat.getCurrencyInstance(new Locale("pt","BR")); //para formatar os numeros na moeda do Brasil.   
                	moneyFormat1.setMinimumFractionDigits(2); //** pelo q entendi, aki seria para definir a quandidade de casas decimais   
                	String valor1 = moneyFormat1.format(produtoVenda.getValor());   
                	return valor1;
                case 3:
                	String lista = Integer.toString(produtoVenda.getItens().size());
                	return lista;
                
            }  
        }  
        return null;  
    }  
 
    public int getRowCount() {  
        if (produtosVenda != null) {  
            return this.produtosVenda.size();  
        }  
          
        return 0;  
    }  
  
    public int getColumnCount() {  
        return 4;  
    }  
      
    public Produto getProduto(int row) {  
        if (row >= 0) {  
            return (Produto) this.produtosVenda.get(row);  
        }  
        return null;  
    }  
    
}

