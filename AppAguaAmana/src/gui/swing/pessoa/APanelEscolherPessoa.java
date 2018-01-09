package gui.swing.pessoa;

import gui.swing.componentes.CompositeIcon;
import gui.swing.componentes.VTextIcon;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class APanelEscolherPessoa extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedPane;
	public APanelEscolherPessoa(ADialogEscolherPessoa tela, boolean chequeLimite){
		setLayout(new BorderLayout());
		tabbedPane = new JTabbedPane();
		//tabbedPane.setTabPlacement(JTabbedPane.LEFT);
		//tabbedPane.setBackground(Color.RED);
		VTextIcon textIcon = new VTextIcon(tabbedPane, "Pessoa Física");
		VTextIcon textIcon2 = new VTextIcon(tabbedPane, "Pessoa Jurídica");
		Icon graphicIcon = new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/pessoafisica_rot.png"));
		Icon graphicIcon2 = new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/pessoajuridica_rot.png"));
		CompositeIcon icon = new CompositeIcon(graphicIcon, textIcon);	
		CompositeIcon icon2 = new CompositeIcon(graphicIcon2, textIcon2);	
		tabbedPane.setBorder(null);
		tabbedPane.setFont(new Font("Tahoma", Font.BOLD, 11));
		tabbedPane.setTabPlacement(JTabbedPane.RIGHT);
		tabbedPane.addTab(null, icon, new APanelEscolherPessoaFisica(tela, chequeLimite));
		tabbedPane.addTab(null, icon2, new APanelEscolherPessoaJuridica(tela, chequeLimite));
		//tabbedPane.add("Pessoa Jurídica", new PainelClienteJuridica());
		add(tabbedPane, BorderLayout.CENTER);
		setBorder(null);
		
	}
}
