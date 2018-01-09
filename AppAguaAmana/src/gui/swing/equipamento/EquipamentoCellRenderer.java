package gui.swing.equipamento;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class EquipamentoCellRenderer extends JLabel implements TableCellRenderer {
	    
	private static final long serialVersionUID = 1L;

	public EquipamentoCellRenderer() {
		setOpaque(true); //MUST do this for background to show up.
	    setHorizontalAlignment(JLabel.CENTER);
	}
	 
	@Override
	public Component getTableCellRendererComponent(
	                            JTable table, Object value,
	                            boolean isSelected, boolean hasFocus,
	                            int row, int column) {
	        setText(value.toString());
	        setBackground(Color.WHITE);
	        
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
	        
	        if(column == 5){
	        	if(!value.toString().equals("")){
	        		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		            try {
		                java.util.Date data = df.parse(table.getValueAt(row, column).toString());
		                Calendar cal = Calendar.getInstance();
		                
		                if(cal.getTime().after(data)){
		                	setBackground(new Color(255, 215, 215));
			        		setForeground(Color.BLACK);
			        	}
		                else{
		                	Calendar calAfter = Calendar.getInstance();
		                    calAfter.setTime(data);
		                	calAfter.add(Calendar.DAY_OF_MONTH, -30);
		                    //data.setDate(data.getDate() - 30);
		                    if(cal.getTime().after(calAfter.getTime())){
		                        setBackground(new Color(255, 255, 150));
				        		setForeground(Color.BLACK);
		                    }
		                    //else setBackground(new Color(150, 225, 150));    
		                }
		                    
		            } catch (ParseException ex) {
		            }
		        }
	        }
	        return this;
	}
}
