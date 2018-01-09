package gui.swing.usuario;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class UsuarioCellRenderer extends JLabel implements TableCellRenderer {
    
	private static final long serialVersionUID = 1L;

	public UsuarioCellRenderer() {
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
        
        return this;
    }
}
