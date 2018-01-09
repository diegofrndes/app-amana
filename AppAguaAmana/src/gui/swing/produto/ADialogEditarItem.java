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

import espectador.produto.EspectadorEditarItem;
import gui.swing.componentes.APanelDadosItem;
import gui.swing.componentes.APanelTopo;

import apresentador.produto.ApresentadorEditarItem;

import modelo.Item;
import modelo.Pessoa;
import dao.FabricaConexao;
public class ADialogEditarItem extends JDialog implements EspectadorEditarItem {

	private static final long serialVersionUID = 1L;
	private APanelTopo painelTopo;
	private APanelDadosItem painelItem;
	
	private JButton btnEditar;
	private JButton btnCancelar;
	private boolean novoItem;
	
	private ApresentadorEditarItem apresentador;
	
	public ADialogEditarItem(Item item)
	{
		setTitle("Editar Item de Produto");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("imagens/icone.png")));
		initComponents();
		apresentador = new ApresentadorEditarItem(this, item);
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
		
		painelTopo = new APanelTopo("Item", "Editar", "imagens/32x32/item_produto_editar.png");
		painelTopo.setBounds(0,0,320,52);
		painel.add(painelTopo);
		
		painelItem = new APanelDadosItem();
		painelItem.setBounds(0,52,320,115);
		painel.add(painelItem);
		
		
		btnEditar = new JButton("Editar");
		btnEditar.setBounds(154, 170, 80, 20);
		btnEditar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(painelItem.isValido()){
					try{
						if(apresentador.editar()){
							novoItem = true;
	                		JOptionPane.showMessageDialog(null, "Item editado com sucesso.", "Editar Item de Produto", JOptionPane.INFORMATION_MESSAGE);      
                            setVisible(false);
                    		apresentador = null;
                            removeAll();
                    		dispose();
	                	}
                        else{
                        	JOptionPane.showMessageDialog(null, "Erro ao tentar editar item.\n", "Editar Item de Produto", JOptionPane.ERROR_MESSAGE );    
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
		
		painel.add(btnEditar);
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

	@Override
	public void setLimiteCritico(int limite) {
		painelItem.setLimiteCritico(limite);
	}

	@Override
	public void setNome(String nome) {
		painelItem.setNome(nome);
	}

	@Override
	public void setFornecedor(Pessoa fornecedor) {
		painelItem.setFornecedor(fornecedor);
	}

}
