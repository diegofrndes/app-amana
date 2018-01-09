package gui.swing.venda;

import gui.swing.componentes.APanelTopo;
import gui.swing.componentes.ATextFieldValor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import dao.FabricaDao;

import modelo.Venda;
import dao.FabricaConexao;

public class ADialogDesconto extends JDialog {

	private static final long serialVersionUID = 1L;
	private APanelTopo painelTopo;
	private JLabel lblInicial;
	private ATextFieldValor porc;
	private JLabel lblFinal;
	private ATextFieldValor valor;
	private JButton botaoRelatorio;
	private Venda venda;
	private boolean novoDesconto;
	
	public ADialogDesconto(Venda ven){
		this.venda = ven;
		novoDesconto = false;
		setTitle("Desconto da venda " + Integer.toString(venda.getId()));
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("imagens/icone.png")));
		initComponents();
    	setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    
		addWindowListener(new WindowAdapter() {
            	public void windowClosing(WindowEvent we) {
            		setVisible(false);
            		removeAll();
            		dispose();
            		venda = null;
            	}
    	});
		
    	setResizable(false);
    	setSize(316, 150);
    	Dimension ds = Toolkit.getDefaultToolkit().getScreenSize();  
    	Dimension dw = getSize();  
    	setLocation((ds.width - dw.width) / 2, (ds.height - dw.height) / 2);  
    	setModal(true);
    	setVisible(true);

	}
	
	void initComponents()
	{
		JPanel painel = new JPanel();
		painel.setLayout(null);
		painel.setBorder(null);
		setLayout(new GridLayout());
		
		JPanel pBlue = new JPanel();
		pBlue.setBorder(null);
		pBlue.setBackground(new Color(0,148,219));
		JSplitPane painelDividirTopoBotoes = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		painelDividirTopoBotoes.setBounds(0 , 52, 600, 37);
		painelDividirTopoBotoes.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(243,243,243)));
		painelDividirTopoBotoes.setLeftComponent(pBlue);
		painelDividirTopoBotoes.setDividerLocation(5);
		painelDividirTopoBotoes.setDividerSize(0);
		
		JPanel painelBotoes = new JPanel();
		painelBotoes.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(180,180,180)));
		lblInicial = new JLabel("Porc. (%): ");
		lblInicial.setBounds(5, 5, 60, 20);
		porc = new ATextFieldValor();
		porc.setBounds(65, 5, 90, 20);
		
		lblFinal = new JLabel("Valor (R$): ");
		lblFinal.setBounds(160, 5, 60, 20);
		valor = new ATextFieldValor();
		valor.setBounds(215, 5, 90, 20);
		
		valor.setText(venda.getDesconto().toString());
		atualizarPorcentagem();
porc.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				atualizarValor();
			}
		});
		
		porc.addFocusListener(new FocusAdapter() {
			
			@Override
			public void focusLost(FocusEvent e) {
				atualizarValor();
			}
		});
		
		valor.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				atualizarPorcentagem();
			}
		});
		
		valor.addFocusListener(new FocusAdapter() {
			
			@Override
			public void focusLost(FocusEvent e) {
				atualizarPorcentagem();
			}
		});
		botaoRelatorio = new JButton("Atualizar Desconto");
		botaoRelatorio.setBounds(96, 93, 139, 20);
		botaoRelatorio.setIcon(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/atualizar.png")));
		botaoRelatorio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try{
					String auxDesconto = valor.getText();
		        	auxDesconto = auxDesconto.replace(".", "");
		        	auxDesconto = auxDesconto.replace(",", ".");
		        	BigDecimal oldDesconto = venda.getDesconto();
		        	venda.setDesconto(new BigDecimal(auxDesconto));
		        	BigDecimal v = venda.getValor().add(oldDesconto);
		        	venda.setValor( v.subtract(new BigDecimal(auxDesconto)));
		        	boolean atualizar = FabricaDao.getVendaDao().atualizarDesconto(venda);
					if(atualizar){
						novoDesconto = true;
            			JOptionPane.showMessageDialog(null, "Desconto atualizado com sucesso.", "Atualizar Desconto", JOptionPane.INFORMATION_MESSAGE);      
            			setVisible(false);
	            		removeAll();
						dispose();
						venda = null;
					}
					else {
						setVisible(false);
	            		removeAll();
						dispose();
						venda = null;
						JOptionPane.showMessageDialog(null, "Erro ao tentar atualizar desconto.", FabricaConexao.getApelido(), JOptionPane.ERROR_MESSAGE);
						novoDesconto = false;
					}
					//novoDesconto = true;
				} catch (Exception e){
					//e.printStackTrace();
            		setVisible(false);
            		removeAll();
					dispose();
					venda = null;
					JOptionPane.showMessageDialog(null, "Erro ao tentar atualizar desconto.", FabricaConexao.getApelido(), JOptionPane.ERROR_MESSAGE);
		    	}
			}
		});
		painelBotoes.setLayout(null);
		painelBotoes.add(lblInicial);
		painelBotoes.add(porc);
		painelBotoes.add(lblFinal);
		painelBotoes.add(valor);
		painel.add(botaoRelatorio);
		
		painelDividirTopoBotoes.setRightComponent(painelBotoes);
		
		painel.add(painelDividirTopoBotoes);
		
		painelTopo = new APanelTopo("Venda", "Desconto ", "imagens/32x32/desconto.png");
		painelTopo.setBounds(0,0,310,52);
		painel.add(painelTopo);
		add(painel);
	}
	
	public boolean getNovoDesconto(){
		return novoDesconto;
	}
	
	private void atualizarValor(){
		String auxPorc = porc.getText();
	    auxPorc = auxPorc.replace(".", "");
	    auxPorc = auxPorc.replace(",", ".");
	    BigDecimal porcentagem = new BigDecimal(auxPorc);

		String auxValor = valor.getText();
	    auxValor = auxValor.replace(".", "");
	    auxValor = auxValor.replace(",", ".");
	    //BigDecimal valorDesconto = new BigDecimal(auxValor);

	    if( porcentagem.compareTo(new BigDecimal(100.00f)) == 1 ){//|| valorDesconto.compareTo(venda.getValor().add(venda.getDesconto())) == 1  ){
	    	porc.setText("10000");
	    	valor.setText((venda.getValor().add(venda.getDesconto())).toString());
	    }
	    
	    else {
	    	double pc = porcentagem.doubleValue();
	    	double vadd = venda.getValor().add(venda.getDesconto()).doubleValue();
	    	double result = pc*vadd; 
	    	double fim = result / 100;    	
	    	NumberFormat moneyFormatDesc = NumberFormat.getCurrencyInstance(new Locale("pt","BR")); //para formatar os numeros na moeda do Brasil.   
        	moneyFormatDesc.setMinimumFractionDigits(2); //** pelo q entendi, aki seria para definir a quandidade de casas decimais   
        	valor.setText(moneyFormatDesc.format(fim));   
        }

	}
	
	private void atualizarPorcentagem(){
		String auxPorc = porc.getText();
	    auxPorc = auxPorc.replace(".", "");
	    auxPorc = auxPorc.replace(",", ".");
	    BigDecimal porcentagem = new BigDecimal(auxPorc);

		String auxValor = valor.getText();
	    auxValor = auxValor.replace(".", "");
	    auxValor = auxValor.replace(",", ".");
	    BigDecimal valorDesconto = new BigDecimal(auxValor);

	    if( porcentagem.compareTo(new BigDecimal(100.00f)) == 1 || valorDesconto.compareTo( venda.getValor().add(venda.getDesconto())) == 1  ){
	    	porc.setText("10000");
	    	valor.setText((venda.getValor().add(venda.getDesconto())).toString());
	    }
	    
	    else {
	    	double vd = valorDesconto.doubleValue();
	    	double vadd = venda.getValor().add(venda.getDesconto()).doubleValue();
	    	double result = vd/vadd; 
	    	double fim = result * 100;    	
	    	NumberFormat moneyFormatDesc = NumberFormat.getCurrencyInstance(new Locale("pt","BR")); //para formatar os numeros na moeda do Brasil.   
        	moneyFormatDesc.setMinimumFractionDigits(2); //** pelo q entendi, aki seria para definir a quandidade de casas decimais   
        	porc.setText(moneyFormatDesc.format(fim));   
	    }
		
	}
}
