package gui.swing.patrimonio;

import espectador.patrimonio.EspectadorEstimativaPatrimonio;
import gui.swing.componentes.APanelTopo;
import gui.swing.inicial.AFramePrincipal;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import apresentador.patrimonio.ApresentadorEstimativaPatrimonio;
import dao.FabricaConexao;

public class ADialogEstimativaPatrimonio extends JDialog implements EspectadorEstimativaPatrimonio {
	
	private static final long serialVersionUID = 1L;
	private JLabel valor;
	private APanelTopo painelTopo;
	@SuppressWarnings("unused")
	private ApresentadorEstimativaPatrimonio apresentador;
	private JPanel painel;	
	public static ADialogEstimativaPatrimonio getADialogEstimativaPatrimonio(){
		return new ADialogEstimativaPatrimonio();
	}
	
	private ADialogEstimativaPatrimonio(){
		setTitle("Estimativa do Patrimônio");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("imagens/icone.png")));
		initComponents();
		try
		{
			apresentador = new ApresentadorEstimativaPatrimonio(this);
			
		} catch (NullPointerException ex) 
		{
			JOptionPane.showMessageDialog(null, "Não foi possível conectar ao servidor.\nO programa será finalizado.", FabricaConexao.getApelido(), JOptionPane.ERROR_MESSAGE);
    		AFramePrincipal.closeAFramePrincipal();
        	System.exit(0);
		}
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
		addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent we) {
                	setVisible(false);
    				apresentador = null;
                	removeAll();
    				dispose();
                }
        });
		
        setResizable(false);
        setSize(320, 202);
        Dimension ds = Toolkit.getDefaultToolkit().getScreenSize();  
        Dimension dw = getSize();  
        setLocation((ds.width - dw.width) / 2, (ds.height - dw.height) / 2);  
        setModal(true);
        setVisible(true);
	
	}
	
	private void initComponents(){
		
		valor = new JLabel("", SwingConstants.CENTER);
		valor.setFont(new Font("TAHOMA",1, 20));
		
		painel = new JPanel();
		painel.setLayout(null);
		painel.setBorder(null);
		setLayout(new GridLayout());
		
		painelTopo = new APanelTopo("Patrimônio", "Estimativa", "imagens/32x32/estimativa.png");
		painelTopo.setBounds(0,0,320,52);
		painel.add(painelTopo);

		painel.setBounds(0, 0, 320, 202);
		add(painel);
	}

	@Override
	public void setEstimativa(BigDecimal estimativa) {
		if(estimativa == null){
			JPanel pan = new JPanel();
			pan.setLayout(new BorderLayout());
			valor.setText("R$ 0,00");
			pan.setBounds(0, 52, 320, 120);
			pan.add(valor, BorderLayout.CENTER);
			painel.add(pan);
		}
		else {
			JPanel pan = new JPanel();
			pan.setLayout(new BorderLayout());
			NumberFormat moneyFormat1 = NumberFormat.getCurrencyInstance(new Locale("pt","BR")); //para formatar os numeros na moeda do Brasil.   
        	moneyFormat1.setMinimumFractionDigits(2); //** pelo q entendi, aki seria para definir a quandidade de casas decimais   
        	String valor1 = moneyFormat1.format(estimativa);   
			valor.setText(valor1);
			pan.setBounds(0, 52, 320, 120);
			pan.add(valor, BorderLayout.CENTER);
			painel.add(pan);
		}
	}
}
