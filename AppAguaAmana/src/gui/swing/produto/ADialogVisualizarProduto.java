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
import javax.swing.JPanel;

import modelo.Item;
import modelo.Produto;
import apresentador.produto.ApresentadorVisualizarProduto;
import espectador.produto.EspectadorVisualizarProduto;
import gui.swing.componentes.APanelDadosProduto;
import gui.swing.componentes.APanelTopo;

public class ADialogVisualizarProduto extends JDialog implements EspectadorVisualizarProduto {
	
	private static final long serialVersionUID = 1L;
	private APanelTopo painelTopo;
	private APanelDadosProduto painelProdutoVenda;
	
	@SuppressWarnings("unused")
	private ApresentadorVisualizarProduto apresentador;
	
	private JButton btnCancelar;
	
	public static ADialogVisualizarProduto getADialogVisualizarProduto(Produto produto){
		return new ADialogVisualizarProduto(produto);
	}
	
	private ADialogVisualizarProduto(Produto produto){
		setTitle("Visualizar Produto");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("imagens/icone.png")));
		initComponents();
		apresentador = new ApresentadorVisualizarProduto(this, produto);
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
		JPanel painel = new JPanel();
		painel.setLayout(null);	
		painel.setBorder(null);
		setLayout(new GridLayout());
		
		painelTopo = new APanelTopo("Produto", "Visualizar", "imagens/32x32/produtovenda_visualizar.png");
		painelTopo.setBounds(0,0,320,52);
		painel.add(painelTopo);
		
		painelProdutoVenda = new APanelDadosProduto();
		painelProdutoVenda.setBounds(0, 52, 320, 209);
		painel.add(painelProdutoVenda);
		
		
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
		painel.add(btnCancelar);
		
		add(painel);
		
		pack();
		btnCancelar.requestFocus();
		
	}

	@Override
	public void setNome(String nome) {
		painelProdutoVenda.setVisualizarNome(nome);
	}

	@Override
	public void setItens(List<Item> itens, List<Integer> quantidades) {
		painelProdutoVenda.setVisualizarItens(itens, quantidades);
	}

	@Override
	public void setValor(BigDecimal valor) {
		painelProdutoVenda.setVisualizarValor(valor);
	}
	
}

