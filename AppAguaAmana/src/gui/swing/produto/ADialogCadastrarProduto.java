package gui.swing.produto;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import apresentador.produto.ApresentadorCadastrarProduto;

import espectador.produto.EspectadorCadastrarProduto;

import gui.swing.componentes.APanelDadosProduto;
import gui.swing.componentes.APanelTopo;

import modelo.Item;

import dao.FabricaConexao;
public class ADialogCadastrarProduto extends JDialog implements EspectadorCadastrarProduto {
	
	private static final long serialVersionUID = 1L;
	private APanelTopo painelTopo;
	private APanelDadosProduto painelProdutoVenda;
	
	private ApresentadorCadastrarProduto apresentador;
	
	private boolean novoProduto;
	
	private JButton btnCadastrar, btnCancelar;
	
	public ADialogCadastrarProduto(){
		setTitle("Cadastrar Produto");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("imagens/icone.png")));
		initComponents();
		apresentador = new ApresentadorCadastrarProduto(this);
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
    	setSize(326, 320);
    	Dimension ds = Toolkit.getDefaultToolkit().getScreenSize();  
    	Dimension dw = getSize();  
    	setLocation((ds.width - dw.width) / 2, (ds.height - dw.height) / 2);  
    	setModal(true);
    	setVisible(true);	
	}
	
	public void initComponents(){
		novoProduto = false;
		JPanel painel = new JPanel();
		painel.setLayout(null);	
		painel.setBorder(null);
		setLayout(new GridLayout());
		
		painelTopo = new APanelTopo("Produto", "Cadastrar", "imagens/32x32/produtovenda_add.png");
		painelTopo.setBounds(0,0,320,52);
		painel.add(painelTopo);
		
		painelProdutoVenda = new APanelDadosProduto();
		painelProdutoVenda.setBounds(0, 52, 320, 209);
		painel.add(painelProdutoVenda);
		
		btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setBounds(154, 264, 80, 20);
		btnCadastrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(painelProdutoVenda.isValido()){
					try{
						if(apresentador.cadastrar()){
                    		novoProduto = true;
	                		JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso.", "Cadastrar Produto", JOptionPane.INFORMATION_MESSAGE);      
                            setVisible(false);
                    		apresentador = null;
                            removeAll();
                    		dispose();
	                	}
                        else{
                        	JOptionPane.showMessageDialog(null, "Erro ao tentar cadastrar produto.\n", "Cadastrar Produto", JOptionPane.ERROR_MESSAGE );    
                        }
	                	
					} catch (NullPointerException ex) {
						ex.printStackTrace();
	            		JOptionPane.showMessageDialog(null, "Não foi possível conectar ao servidor.\nO programa será finalizado.", FabricaConexao.getApelido(), JOptionPane.ERROR_MESSAGE);
	            		System.exit(0);
					} 
				}
				else{
					JOptionPane.showMessageDialog(null, "Preencha corretamente todos os campos obrigatórios.", "Cadastrar Produto", JOptionPane.ERROR_MESSAGE);
                }
		
			}
		});
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(239, 264, 80, 20);
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
		
		
	}
	
	public boolean isNovoProduto(){
		return novoProduto;
	}
	
	@Override
	public String getNome() {
		return painelProdutoVenda.getNome();
	}

	@Override
	public List<Item> getItens() {
		return painelProdutoVenda.getItens();
	}

	@Override
	public List<Integer> getQuantidades() {
		return painelProdutoVenda.getQuantidades();
	}

	@Override
	public BigDecimal getValor() {
		return painelProdutoVenda.getValor();
	}

}
