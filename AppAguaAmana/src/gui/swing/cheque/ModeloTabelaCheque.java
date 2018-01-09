package gui.swing.cheque;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import javax.swing.table.AbstractTableModel;

import modelo.Cheque;

public class ModeloTabelaCheque extends AbstractTableModel {  
    
	private static final long serialVersionUID = 1L;
	private List<Cheque> cheques;  
      
    public ModeloTabelaCheque(List<Cheque> cheques) {  
        this.cheques = cheques;   
    }  
      
    public Object getValueAt(int rowIndex, int columnIndex) {  
        Cheque cheque = cheques.get(rowIndex);
    	if (cheque != null) {  
            DateFormat formato = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
            switch (columnIndex) {  
            	//Nome, Cpf, Telefone, Login, Tipo  
            	case 0:
                    return cheque.getCliente().getId();
            	case 1:
                    return cheque.getCliente().getNome();
            	case 2:
            	    return cheque.getTitular();
            	case 3:
            		DateFormat timeFormat = new SimpleDateFormat("dd/mm/yyyy");
                    try {
             			return timeFormat.format(formato.parse(cheque.getData()));
             		} catch (ParseException e) {
             			return "";
             		}
            	case 4:
            		DateFormat timeFormat2 = new SimpleDateFormat("dd/mm/yyyy");
                    try {
             			return timeFormat2.format(formato.parse(cheque.getVencimento()));
             		} catch (ParseException e) {
             			return "";
             		}
                case 5:
                	NumberFormat moneyFormat1 = NumberFormat.getCurrencyInstance(new Locale("pt","BR")); //para formatar os numeros na moeda do Brasil.   
                	moneyFormat1.setMinimumFractionDigits(2); //** pelo q entendi, aki seria para definir a quandidade de casas decimais   
                	String valor1 = moneyFormat1.format(cheque.getValor());   
                	return valor1;
                case 6:
                	if(cheque.getEstado() == 0){
                		return "Pendente";
                	}
                	else if (cheque.getEstado() == 1){
                		return "Finalizado";
                	}
                	else return "Desconhecido";
                case 7:
                	return cheque.getObs();
            }  
        }  
        return null;  
    }  
 
    public int getRowCount() {  
        if (cheques != null) {  
            return this.cheques.size();  
        }  
          
        return 0;  
    }  
  
    public int getColumnCount() {  
        return 8;  
    }  
      
    public Cheque getCheque(int row) {  
        if (row >= 0) {  
            return (Cheque) this.cheques.get(row);  
        }  
        return null;  
    }  
    
}  
