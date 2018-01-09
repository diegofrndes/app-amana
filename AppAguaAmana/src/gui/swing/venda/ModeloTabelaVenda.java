package gui.swing.venda;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import javax.swing.table.AbstractTableModel;

import modelo.Venda;

public class ModeloTabelaVenda extends AbstractTableModel {  
    
	private static final long serialVersionUID = 1L;
	private List<Venda> vendas;  
      
    public ModeloTabelaVenda(List<Venda> vendas) {  
        this.vendas = vendas;   
    }  
      
    public Object getValueAt(int rowIndex, int columnIndex) {  
        Venda venda = (Venda) vendas.get(rowIndex);  
        if (venda != null) {  
            DateFormat formato = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
            //statusData.setText(dateFormat.format(date));
		    //statusHora.setText(timeFormat.format(date));

            switch (columnIndex) {  
            	//Numero, Cliente, Data, Hora, Valor, ValorRecebido, Debito   
            	case 0:
                    return Integer.toString(venda.getId());
            	case 1:
                	return Integer.toString(venda.getCliente().getId());
                case 2:
                	return venda.getCliente().getNome();
                case 3:
            		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");                	
            		try {
            			return dateFormat.format(formato.parse(venda.getData()));
            		} catch (ParseException e) {
            			return "";
            		}
                case 4:
                    DateFormat timeFormat = new SimpleDateFormat("kk:mm:ss");
                    try {
            			return timeFormat.format(formato.parse(venda.getData()));
            		} catch (ParseException e) {
            			return "";
            		}
                case 5:
                	BigDecimal a1 = venda.getValor();
                	BigDecimal b1 = venda.getDesconto();
                	BigDecimal c1 = a1.add(b1);
                	return c1;
                case 6:
                	NumberFormat moneyFormatDesc = NumberFormat.getCurrencyInstance(new Locale("pt","BR")); //para formatar os numeros na moeda do Brasil.   
                	moneyFormatDesc.setMinimumFractionDigits(2); //** pelo q entendi, aki seria para definir a quandidade de casas decimais   
                	String valorDesc = moneyFormatDesc.format(venda.getDesconto());   
                	return valorDesc; 	
                case 7:
                	NumberFormat moneyFormat1 = NumberFormat.getCurrencyInstance(new Locale("pt","BR")); //para formatar os numeros na moeda do Brasil.   
                	moneyFormat1.setMinimumFractionDigits(2); //** pelo q entendi, aki seria para definir a quandidade de casas decimais   
                	String valor1 = moneyFormat1.format(venda.getValor());   
                	return valor1;
                case 8:
                	NumberFormat moneyFormat2 = NumberFormat.getCurrencyInstance(new Locale("pt","BR")); //para formatar os numeros na moeda do Brasil.   
                	moneyFormat2.setMinimumFractionDigits(2); //** pelo q entendi, aki seria para definir a quandidade de casas decimais   
                	String valor2 = moneyFormat2.format(venda.getValorRecebido());   
                	return valor2;
                case 9:
                	BigDecimal a = venda.getValor();
                	BigDecimal b = venda.getValorRecebido();
                	BigDecimal c = a.subtract(b);
                	return c;
            }  
        }  
        return null;  
    }  
 
    public int getRowCount() {  
        if (vendas != null) {  
            return this.vendas.size();  
        }  
          
        return 0;  
    }  
  
    public int getColumnCount() {  
        return 10;  
    }  
      
    public Venda getVenda(int row) {  
        if (row >= 0) {  
            return (Venda) this.vendas.get(row);  
        }  
        return null;  
    }  
    
}  
