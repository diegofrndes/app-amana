package gui.swing.produto;

import gui.swing.componentes.APanelTopo;
import gui.swing.documentos.AmanaFieldDocument;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
import dao.FabricaConexao;

public class ADialogEstoqueItem extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	EntradaSaidaItem estoque;
	private JLabel lblMotivo;
	private JTextField txtMotivo;
	private JLabel lblEstoque;
	private JComboBox jComboBoxEstoque;
	private JLabel lblQuantidade;
	private JSpinner jSpinnerQuantidade;
	private JButton btnAdicionar, btnCancelar;
	private boolean novoEstoque;
	
	private APanelTopo painelTopo;
	
	public ADialogEstoqueItem(Item item){
		novoEstoque = false;
		estoque = new EntradaSaidaItem();
		estoque.setItem(item);
		setTitle("Estoque De " + item.getNome());
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
    	setSize(326, 225);
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
		
		painelTopo = new APanelTopo("Item", "Estoque", "imagens/32x32/estoque.png");
		painelTopo.setBounds(0,0,320,52);
		painel.add(painelTopo);
		
		JPanel temp = new JPanel();
		temp.setBounds(0, 52, 320, 115);
		temp.setBorder(BorderFactory.createTitledBorder("Entrada/Saída do Estoque"));
		temp.setLayout(null);
		lblMotivo = new JLabel("Descrição");
		lblMotivo.setBounds(20,15,50,20);
		txtMotivo = new JTextField();
		txtMotivo.setDocument(new AmanaFieldDocument(60));
		txtMotivo.setBounds(20, 35, 280, 20);
		temp.add(lblMotivo);
		temp.add(txtMotivo);
		
		lblQuantidade = new JLabel("Quantidade");
		lblQuantidade.setBounds(20,55,135,20);
		jSpinnerQuantidade = new JSpinner(new SpinnerNumberModel(1,0,10000000,1));
		jSpinnerQuantidade.setBounds(20, 75, 135, 20);
		temp.add(lblQuantidade);
		temp.add(jSpinnerQuantidade);
		
		lblEstoque = new JLabel("Entrada/Saída");
		lblEstoque.setBounds(165,55,135,20);
		jComboBoxEstoque = new JComboBox();
		jComboBoxEstoque.addItem("Entrada");
		jComboBoxEstoque.addItem("Saída");
		jComboBoxEstoque.setBounds(165, 75, 135, 20);
		temp.add(lblEstoque);
		temp.add(jComboBoxEstoque);
		
		btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setBounds(154, 170, 80, 20);
		btnAdicionar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				estoque.setMotivo(txtMotivo.getText());
				if(jComboBoxEstoque.getSelectedItem().toString() == "Entrada")
					estoque.setQuantidade(Integer.parseInt(jSpinnerQuantidade.getValue().toString()));
				else if(jComboBoxEstoque.getSelectedItem().toString() == "Saída")
					estoque.setQuantidade(-Integer.parseInt(jSpinnerQuantidade.getValue().toString()));
				
				try{
					if(FabricaDao.getItemDao().entradaSaida(estoque) != -1){
						novoEstoque = true;
                		JOptionPane.showMessageDialog(null, "Adicionado ao estoque com sucesso.", "Adicionar Estoque de Item", JOptionPane.INFORMATION_MESSAGE);      
                       	setVisible(false);
                       	removeAll();
                		dispose();
                	}
                    else{
                    	JOptionPane.showMessageDialog(null, "Erro ao tentar adicionar no estoque.\n", "Adicionar Estoque de Item", JOptionPane.ERROR_MESSAGE );    
                    }
                	
				} catch (NullPointerException ex) {
					JOptionPane.showMessageDialog(null, "Não foi possível conectar ao servidor.\nO programa será finalizado.", FabricaConexao.getApelido(), JOptionPane.ERROR_MESSAGE);
            		System.exit(0);
				} 
	
				
			}
		});
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(239, 170, 80, 20);
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

	public boolean isNovoEstoque()
	{
		return novoEstoque;
	}
}
