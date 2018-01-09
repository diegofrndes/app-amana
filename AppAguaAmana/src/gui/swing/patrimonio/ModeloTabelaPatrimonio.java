package gui.swing.patrimonio;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import javax.swing.table.AbstractTableModel;

import modelo.Patrimonio;

public class ModeloTabelaPatrimonio extends AbstractTableModel {
    
	private static final long serialVersionUID = 1L;
	private List<Patrimonio> patrimonios;  
    
    public ModeloTabelaPatrimonio(List<Patrimonio> patrimonios) {  
        this.patrimonios = patrimonios;   
    }  

    public Object getValueAt(int rowIndex, int columnIndex) {  
        Patrimonio patrimonio = (Patrimonio) patrimonios.get(rowIndex);  
        if (patrimonios != null) {  
            switch (columnIndex) {  
                case 0:
                    return patrimonio.getNome();
                case 1:
                    return patrimonio.getDescricao();
                case 2:
                	NumberFormat moneyFormat = NumberFormat.getNumberInstance(new Locale("pt","BR")); //para formatar os numeros na moeda do Brasil.   
                	//moneyFormat.setMinimumFractionDigits(2); //** pelo q entendi, aki seria para definir a quandidade de casas decimais   
                	String valor = moneyFormat.format(patrimonio.getQuantidade());   
                	return valor;
                	
                case 3:
                	NumberFormat moneyFormat1 = NumberFormat.getCurrencyInstance(new Locale("pt","BR")); //para formatar os numeros na moeda do Brasil.   
                	moneyFormat1.setMinimumFractionDigits(2); //** pelo q entendi, aki seria para definir a quandidade de casas decimais   
                	String valor1 = moneyFormat1.format(patrimonio.getValor());   
                	return valor1;
                case 4:
                	BigDecimal a = patrimonio.getValor();
                	BigDecimal b = new BigDecimal(patrimonio.getQuantidade());
                	BigDecimal c = a.multiply(b);
                	NumberFormat moneyFormat2 = NumberFormat.getCurrencyInstance(new Locale("pt","BR")); //para formatar os numeros na moeda do Brasil.   
                	moneyFormat2.setMinimumFractionDigits(2); //** pelo q entendi, aki seria para definir a quandidade de casas decimais   
                	String valor2 = moneyFormat2.format(c);   
                	return valor2;                	
            }  
        }  
        return null;  
    }  
 
    public int getRowCount() {  
        if (patrimonios != null) {  
            return this.patrimonios.size();  
        }  
          
        return 0;  
    }  
  
    public int getColumnCount() {  
        return 5;  
    }  
      
    public Patrimonio getPatrimonio(int row) {  
        if (row >= 0) {  
            return (Patrimonio) this.patrimonios.get(row);  
        }  
        return null;  
    }  

}