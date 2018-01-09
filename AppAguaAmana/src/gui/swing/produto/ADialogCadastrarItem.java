package gui.swing.produto;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import apresentador.produto.ApresentadorCadastrarItem;

import modelo.Pessoa;

import espectador.produto.EspectadorCadastrarItem;
import gui.swing.componentes.APanelDadosItem;
import gui.swing.componentes.APanelTopo;
import dao.FabricaConexao;
public class ADialogCadastrarItem extends JDialog implements EspectadorCadastrarItem {
	
	private static final long serialVersionUID = 1L;
	private APanelTopo painelTopo;
	private APanelDadosItem painelItem;
	
	private JButton btnCadastrar;
	private JButton btnCancelar;
	private boolean novoItem;
	
	private ApresentadorCadastrarItem apresentador;
	
	public ADialogCadastrarItem()
	{
		setTitle("Cadastrar Item de Produto");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("imagens/icone.png")));
		initComponents();
		apresentador = new ApresentadorCadastrarItem(this);
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
    	setSize(326, 225);
    	Dimension ds = Toolkit.getDefaultToolkit().getScreenSize();  
    	Dimension dw = getSize();  
    	setLocation((ds.width - dw.width) / 2, (ds.height - dw.height) / 2);  
    	setModal(true);
    	setVisible(true);
    }

	private void initComponents()
	{
		novoItem = false;
		JPanel painel = new JPanel();
		painel.setLayout(null);	
		painel.setBorder(null);
		setLayout(new GridLayout());
		
		painelTopo = new APanelTopo("Item", "Cadastrar", "imagens/32x32/item_produto_add.png");
		painelTopo.setBounds(0,0,320,52);
		painel.add(painelTopo);
		
		painelItem = new APanelDadosItem();
		painelItem.setBounds(0,52,320,115);
		painel.add(painelItem);
		
		
		btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setBounds(154, 170, 80, 20);
		btnCadastrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(painelItem.isValido()){
					try{
						if(apresentador.existeItem()){
                        	JOptionPane.showMessageDialog(null, "Não foi possível cadastrar.\nExiste um item com o mesmo nome.\n", "Cadastrar Item de Produto", JOptionPane.ERROR_MESSAGE );
                    	}
                    	else{
                    		if(apresentador.cadastrar()){
                    			novoItem = true;
	                			JOptionPane.showMessageDialog(null, "Item cadastrado com sucesso.", "Cadastrar Item de Produto", JOptionPane.INFORMATION_MESSAGE);      
                            	setVisible(false);
                    			apresentador = null;
                            	removeAll();
                    			dispose();
	                		}
                        	else{
                        		JOptionPane.showMessageDialog(null, "Erro ao tentar cadastrar item.\n", "Cadastrar Item de Produto", JOptionPane.ERROR_MESSAGE );    
                        	}
	                	}
					} catch (NullPointerException ex) {
						ex.printStackTrace();
	            		JOptionPane.showMessageDialog(null, "Não foi possível conectar ao servidor.\nO programa será finalizado.", FabricaConexao.getApelido(), JOptionPane.ERROR_MESSAGE);
	            		System.exit(0);
					} 
				}
				else{
					JOptionPane.showMessageDialog(null, "Preencha corretamente todos os campos obrigatórios.", "Cadastrar Item de Produto", JOptionPane.ERROR_MESSAGE);
                }
			}
		});
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(239, 170, 80, 20);
		btnCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				apresentador = null;
				removeAll();
				dispose();
			}
		});
		
		painel.add(btnCadastrar);
		painel.add(btnCancelar);
		
		add(painel);
		pack();

	}
	
	public boolean isNovoItem()
	{
		return novoItem;
	}

	@Override
	public int getLimiteCritico() {
		return painelItem.getLimiteCritico();
	}

	@Override
	public String getNome() {
		return painelItem.getNome();
	}

	@Override
	public Pessoa getFornecedor() {
		return painelItem.getFornecedor();
	}

}
