package gui.swing.venda;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import javax.swing.table.AbstractTableModel;

import modelo.Pagamento;

public class ModeloTabelaPagamento extends AbstractTableModel {  
	    
		private static final long serialVersionUID = 1L;
		private List<Pagamento> pagamentos;  
	      
	    public ModeloTabelaPagamento(List<Pagamento> pagamentos) {  
	        this.pagamentos = pagamentos;   
	    }  
	      
	    public Object getValueAt(int rowIndex, int columnIndex) {  
	        Pagamento pag = (Pagamento) pagamentos.get(rowIndex);  
	        if (pag != null) {  
	            DateFormat formato = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
	            
	            switch (columnIndex) {  
	            	case 0:
                    	return pag.getForma();
	            	case 1:
                    	return pag.getUsuario();
            		case 2:
	            		NumberFormat moneyFormat1 = NumberFormat.getCurrencyInstance(new Locale("pt","BR")); //para formatar os numeros na moeda do Brasil.   
	            		moneyFormat1.setMinimumFractionDigits(2); //** pelo q entendi, aki seria para definir a quandidade de casas decimais   
	            		String valor1 = moneyFormat1.format(pag.getValor());   
	            		return valor1;
                    case 3:
	            		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");                	
	            		try {
	            			return dateFormat.format(formato.parse(pag.getData()));
	            		} catch (ParseException e) {
	            			return "";
	            		}
	                case 4:
	                    DateFormat timeFormat = new SimpleDateFormat("kk:mm:ss");
	                    try {
	            			return timeFormat.format(formato.parse(pag.getData()));
	            		} catch (ParseException e) {
	            			return "";
	            		}
	           }  
	        }  
	        return null;  
	    }  
	 
	    public int getRowCount() {  
	        if (pagamentos != null) {  
	            return this.pagamentos.size();  
	        }  
	          
	        return 0;  
	    }  
	  
	    public int getColumnCount() {  
	        return 5;  
	    }  
	      
	    public Pagamento getPagamento(int row) {  
	        if (row >= 0) {  
	            return (Pagamento) this.pagamentos.get(row);  
	        }  
	        return null;  
	    }  
	    
}
