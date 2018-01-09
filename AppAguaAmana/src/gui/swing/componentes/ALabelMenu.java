package gui.swing.componentes;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

public class ALabelMenu extends JLabel {
	
	private static final long serialVersionUID = 1L;
	private boolean selected = false;
	private boolean mouseOver = false;
	
	public ALabelMenu(){
		super();
	}
	
	public ALabelMenu(String text){
		super(text);
	}
	
	public ALabelMenu(String text, int align){
		super(text, align);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setFont(new Font("Tahoma", Font.BOLD, 11));
		addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				mouseOver = true;
				//setFont(new Font("Tahoma", Font.BOLD, 11));
				repaint();
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				mouseOver = false;
				repaint();
			}
		});
	}
	
	public void setSelected(boolean selected){
		this.selected = selected;
	}
	
	public boolean getSelected(){
		return this.selected;
	}

	@Override
	public void paint(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setColor(this.getParent().getBackground());
		g2d.fillRect(0, 0, getWidth(), getHeight());
		if(this.selected)
			g2d.setColor(new Color(243, 243, 243));
		else {
			if(this.mouseOver){
				g2d.setColor(new Color(0, 170, 240));
			}
			else g2d.setColor(new Color(0, 148, 219));
		}
			
		// Yellow is the clipped area.
	    //g2d.setColor(Color.yellow);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
		getIcon().paintIcon(this, g2d, 4, 5);	
		if(this.selected)
			g2d.setColor(Color.BLACK);
		else g2d.setColor(Color.WHITE);
		g2d.setFont(getFont());
		g2d.drawString(getText(), 23, 17);
		
	}
}
