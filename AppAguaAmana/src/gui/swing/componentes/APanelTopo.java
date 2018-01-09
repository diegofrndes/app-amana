package gui.swing.componentes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class APanelTopo extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JLabel logo;
	private JLabel tipo;
	private JLabel img;
	
	public APanelTopo(String dep, String nome){
		setPreferredSize(new Dimension(0,52));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		logo = new JLabel(dep);
		logo.setFont(new Font("Tahoma", 1, 20));
		logo.setForeground(Color.white);
		//logo.setForeground(new Color(253,222,144));
	    tipo = new JLabel(" | " + nome);
		tipo.setFont(new Font("Tahoma", 1, 20));
		//tipo.setForeground(Color.white);
		tipo.setForeground(new Color(253,222,144));
	    //centro.setOpaque(false);
		//logo.setIcon(new ImageIcon(ClassLoader.getSystemResource("imagemtopo.png")));
		add(Box.createRigidArea(new Dimension(12, 0)));
		add(logo);
		add(tipo);
		
	}

	public APanelTopo(String dep, String nome, String caminho){
		setPreferredSize(new Dimension(0,52));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		logo = new JLabel(dep);
		logo.setFont(new Font("Tahoma", 1, 20));
		logo.setForeground(Color.white);
		tipo = new JLabel(" | " + nome);
		tipo.setFont(new Font("Tahoma", 1, 20));
		tipo.setForeground(new Color(253,222,144));
		img = new JLabel();
	    img.setIcon(new ImageIcon(ClassLoader.getSystemResource(caminho)));
	    
	    //centro.setOpaque(false);
		//logo.setIcon(new ImageIcon(ClassLoader.getSystemResource("imagemtopo.png")));
		add(Box.createRigidArea(new Dimension(12, 0)));
		add(logo);
		add(tipo);
		add(Box.createHorizontalGlue());
		add(img);
		add(Box.createRigidArea(new Dimension(10, 0)));
		
	}

	public void paint(Graphics g) {
		//super.paint(g);
		Graphics2D g2d = (Graphics2D)g;

	    Color startColor = new Color(86, 86, 86);
	    Color endColor = new Color(58, 58, 58);

	    // A non-cyclic gradient
	    GradientPaint gradient = new GradientPaint(0, 0, startColor, 0, this.getHeight(), endColor);
	    g2d.setPaint(gradient);
	    g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
	    this.paintComponents(g);
	}
}
