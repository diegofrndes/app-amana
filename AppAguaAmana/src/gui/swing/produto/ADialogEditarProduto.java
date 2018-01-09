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

import modelo.Item;
import modelo.Produto;
import apresentador.produto.ApresentadorEditarProduto;
import espectador.produto.EspectadorEditarProduto;
import gui.swing.componentes.APanelDadosProduto;
import gui.swing.componentes.APanelTopo;
import dao.FabricaConexao;
public class ADialogEditarProduto extends JDialog implements EspectadorEditarProduto {
	
	private static final long serialVersionUID = 1L;
	private APanelTopo painelTopo;
	private APanelDadosProduto painelProdutoVenda;
	
	private ApresentadorEditarProduto apresentador;
	
	private boolean novoProduto;
	
	private JButton btnEditar, btnCancelar;
	
	public static ADialogEditarProduto getADialogEditarProduto(Produto produto){
		return new ADialogEditarProduto(produto);
	}
	
	private ADialogEditarProduto(Produto produto){
		setTitle("Editar Produto");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("imagens/icone.png")));
		initComponents();
		apresentador = new ApresentadorEditarProduto(this, produto);
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
		
		painelTopo = new APanelTopo("Produto", "Editar", "imagens/32x32/produtovenda_editar.png");
		painelTopo.setBounds(0,0,320,52);
		painel.add(painelTopo);
		
		painelProdutoVenda = new APanelDadosProduto();
		painelProdutoVenda.setBounds(0, 52, 320, 209);
		painel.add(painelProdutoVenda);
		
		btnEditar = new JButton("Editar");
		btnEditar.setBounds(154, 264, 80, 20);
		btnEditar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(painelProdutoVenda.isValido()){
					try{
						if(apresentador.editar()){
                    		novoProduto = true;
	                		JOptionPane.showMessageDialog(null, "Produto editado com sucesso.", "Editar Produto", JOptionPane.INFORMATION_MESSAGE);      
                            setVisible(false);
                    		apresentador = null;
                            removeAll();
                    		dispose();
	                	}
                        else{
                        	JOptionPane.showMessageDialog(null, "Erro ao tentar editar produto.\n", "Editar Produto", JOptionPane.ERROR_MESSAGE );    
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
		painel.add(btnEditar);
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
	public BigDecimal getValor() {
		return painelProdutoVenda.getValor();
	}
	
	@Override
	public void setNome(String nome) {
		painelProdutoVenda.setNome(nome);
	}

	@Override
	public void setItens(List<Item> itens, List<Integer> quantidades) {
		painelProdutoVenda.setVisualizarItens(itens, quantidades);
	}

	@Override
	public void setValor(BigDecimal valor) {
		painelProdutoVenda.setValor(valor);
	}
	
}
