package gui.swing.venda;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class VendaCellRenderer extends JLabel implements TableCellRenderer {
    
	private static final long serialVersionUID = 1L;

	public VendaCellRenderer() {
        setOpaque(true); //MUST do this for background to show up.
        setHorizontalAlignment(JLabel.CENTER);
    }
 
    @Override
    public Component getTableCellRendererComponent(
                            JTable table, Object value,
                            boolean isSelected, boolean hasFocus,
                            int row, int column) {
        
    	//setBackground(Color.WHITE);
    	if(table.isRowSelected(row)){
            setBackground(new Color(0, 148, 219));
            setForeground(Color.WHITE);
        	setFont(new Font("Tahoma", Font.BOLD, 11));
        }
        else{
        	if( row % 2 == 0)
                setBackground(new Color(243, 243, 243));
            else setBackground(new Color(255, 255, 255));
        	setForeground(Color.BLACK);
        	setFont(new Font("Tahoma", Font.PLAIN, 11));
        }
    	
    	if(column != 9){
    		setText(value.toString());
    	}
    	else{
    		BigDecimal debito = (BigDecimal) value;
    		if(debito.signum() == 1){
    			setBackground(new Color(255, 215, 215));
            	setForeground(Color.BLACK);
    		}
    		NumberFormat moneyFormat1 = NumberFormat.getCurrencyInstance(new Locale("pt","BR")); //para formatar os numeros na moeda do Brasil.   
        	moneyFormat1.setMinimumFractionDigits(2); //** pelo q entendi, aki seria para definir a quandidade de casas decimais      
    		setText(moneyFormat1.format(debito));
    	}
    	
        
        return this;
    }
}
