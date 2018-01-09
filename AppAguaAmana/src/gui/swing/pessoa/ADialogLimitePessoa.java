package gui.swing.pessoa;

import gui.swing.componentes.APanelTopo;
import gui.swing.componentes.ATextFieldValor;
import gui.swing.documentos.AmanaFieldDocument;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import dao.FabricaDao;
import modelo.EntradaSaidaItem;
import modelo.Item;
import modelo.Pessoa;
import modelo.PessoaFisica;
import dao.FabricaConexao;

public class ADialogLimitePessoa extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblMotivo;
	private ATextFieldValor txtMotivo;
	private JButton btnAdicionar, btnCancelar;
	private boolean novoLimite;
	Pessoa pessoa;
	
	public ADialogLimitePessoa(Pessoa pessoa){
		this.pessoa = pessoa;
		novoLimite = false;
		setTitle("Limite de " + pessoa.getNome());
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("imagens/icone.png")));
		initComponents();
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    
		addWindowListener(new WindowAdapter() {
            	public void windowClosing(WindowEvent we) {
            		setVisible(false);
		    		removeAll();
					dispose();
            	}
    	});
    	setResizable(false);
    	setSize(326, 140);
    	Dimension ds = Toolkit.getDefaultToolkit().getScreenSize();  
    	Dimension dw = getSize();  
    	setLocation((ds.width - dw.width) / 2, (ds.height - dw.height) / 2);  
    	setModal(true);
    	setVisible(true);
    }
	
	public void initComponents(){
		
		JPanel painel = new JPanel();
		painel.setLayout(null);
		painel.setBorder(null);
		setLayout(new GridLayout());
		
		JPanel temp = new JPanel();
		temp.setBounds(0, 0, 320, 80);
		temp.setBorder(BorderFactory.createTitledBorder(pessoa.getNome()));
		temp.setLayout(null);
		lblMotivo = new JLabel("Limite (R$)");
		lblMotivo.setBounds(20,15,150,20);
		txtMotivo = new ATextFieldValor();
		//txtMotivo.setDocument(new AmanaFieldDocument(60));
		txtMotivo.setBounds(20, 35, 280, 20);
		temp.add(lblMotivo);
		temp.add(txtMotivo);
		
		btnAdicionar = new JButton("Alterar");
		btnAdicionar.setBounds(154, 85, 80, 20);
		btnAdicionar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String aux = txtMotivo.getText();
				aux = aux.replace(".", "");
				aux = aux.replace(",", ".");
				BigDecimal valorRecebido = new BigDecimal(aux);
				valorRecebido.setScale(2, BigDecimal.ROUND_HALF_DOWN);
				pessoa.setLimite(valorRecebido);
				
				try{
					if(FabricaDao.getPessoaDao().alterarLimite(pessoa) != false){
						novoLimite = true;
                		JOptionPane.showMessageDialog(null, "Limite alterado com sucesso.", "Alterar Limite", JOptionPane.INFORMATION_MESSAGE);      
                       	setVisible(false);
                       	removeAll();
                		dispose();
                	}
                    else{
                    	JOptionPane.showMessageDialog(null, "Erro ao tentar alterar limite.\n", "Alterar Limite", JOptionPane.ERROR_MESSAGE );    
                    }
                	
				} catch (NullPointerException ex) {
					JOptionPane.showMessageDialog(null, "Não foi possível conectar ao servidor.\nO programa será finalizado.", FabricaConexao.getApelido(), JOptionPane.ERROR_MESSAGE);
            		System.exit(0);
				} 
	
				
			}
		});
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(239, 85, 80, 20);
		btnCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				removeAll();
				dispose();
			}
		});
		
		painel.add(temp);
		painel.add(btnAdicionar);
		painel.add(btnCancelar);
		
		add(painel);
		
		pack();
	}

	public boolean isNovoLimite()
	{
		return novoLimite;
	}
}
