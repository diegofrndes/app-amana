package gui.swing.produto;

import gui.swing.componentes.CompositeIcon;
import gui.swing.componentes.VTextIcon;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import amana.Amana;


public class APanelItemProduto extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedPane;
	public APanelItemProduto(){
		setLayout(new BorderLayout());
		tabbedPane = new JTabbedPane();
		//tabbedPane.setTabPlacement(JTabbedPane.LEFT);
		//tabbedPane.setBackground(Color.RED);
		VTextIcon textIcon = new VTextIcon(tabbedPane, "Produto Para Venda");
		VTextIcon textIcon2 = new VTextIcon(tabbedPane, "Item De Produto");
		//VTextIcon textIcon3 = new VTextIcon(tabbedPane, "Produto Para Devolução");
		Icon graphicIcon = new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/produto_rot.png"));
		Icon graphicIcon2 = new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/item_produto_rot.png"));
		//Icon graphicIcon3 = new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/devolucao_rot.png"));
		CompositeIcon icon = new CompositeIcon(graphicIcon, textIcon);	
		CompositeIcon icon2 = new CompositeIcon(graphicIcon2, textIcon2);	
		//CompositeIcon icon3 = new CompositeIcon(graphicIcon3, textIcon3);	
		tabbedPane.setBorder(null);
		tabbedPane.setFont(new Font("Tahoma", Font.BOLD, 11));
		tabbedPane.setTabPlacement(JTabbedPane.RIGHT);
		tabbedPane.addTab(null, icon2, new APanelItem());
		if(Amana.getUsuario().getTipoUsuario() != 3)
			tabbedPane.addTab(null, icon, new APanelProduto());
		//tabbedPane.addTab(null, icon3, new PainelProdutoDevolucao());
		
		//tabbedPane.add("Pessoa Jurídica", new PainelClienteJuridica());
		add(tabbedPane, BorderLayout.CENTER);
		setBorder(null);
		
	}
}
