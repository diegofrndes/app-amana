package gui.swing.venda;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import dao.FabricaDao;
import modelo.Pessoa;
import modelo.Produto;
import espectador.venda.EspectadorCadastrarVenda;
import amana.Amana;
import apresentador.venda.ApresentadorCadastrarVenda;
import gui.swing.componentes.APanelDadosVenda;
import gui.swing.componentes.APanelTopo;
import dao.FabricaConexao;
public class ADialogCadastrarVenda extends JDialog implements EspectadorCadastrarVenda {
	
	private static final long serialVersionUID = 1L;
	private APanelTopo painelTopo;
	private APanelDadosVenda painelVenda;
	private JButton btnCadastrar;
	private JButton btnCancelar;
	private JLabel lblCliente, lblTransportador, lblProduto;
	private boolean novaVenda;
	private ApresentadorCadastrarVenda apresentador;
	
	public static ADialogCadastrarVenda getADialogCadastrarVenda(){
		return new ADialogCadastrarVenda();
	}
	
	private ADialogCadastrarVenda(){
		setTitle("Cadastrar Venda");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("imagens/icone.png")));
		initComponents();
		apresentador = new ApresentadorCadastrarVenda(this);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
		addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent we) {
                	setVisible(false);
    				apresentador = null;
                	removeAll();
    				dispose();
                }
        });
		/*
		for (int i = 0; i < this.getComponentCount(); i++){
			this.getComponent(i).addKeyListener(new KeyAdapter() {
				
				@Override
				public void keyReleased(KeyEvent e) {
					System.out.println("Z");
				}
				
			});
		}
		*/
		setResizable(false);
        setSize(502, 485);
        Dimension ds = Toolkit.getDefaultToolkit().getScreenSize();  
        Dimension dw = getSize();  
        setLocation((ds.width - dw.width) / 2, (ds.height - dw.height) / 2);  
        setModal(true);
        setVisible(true);	
	}
	
	private void initComponents(){
		novaVenda = false;
		JPanel painel = new JPanel();
		painel.setLayout(null);
		painel.setBorder(null);
		setLayout(new GridLayout());
		
		painelTopo = new APanelTopo("Venda", "Cadastrar", "imagens/32x32/venda_add.png");
		painelTopo.setBounds(0,0,499,52);
		painel.add(painelTopo);
		
		painelVenda = new APanelDadosVenda();
		painelVenda.setBounds(0,52, 495, 375);
		painel.add(painelVenda);
		
		btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setBounds(329, 430, 80, 20);
		btnCadastrar.addActionListener(new ActionListener() {
				
			@Override
			public void actionPerformed(ActionEvent e) {
				if(painelVenda.isValido()){
					try{
						//Pessoa pessoa = painelVenda.getCliente();
						//BigDecimal debito = FabricaDao.getPessoaDao().debito(pessoa);
						//BigDecimal sub = pessoa.getLimite().subtract(debito);
						//if(sub.signum() == 1){
							int cad = apresentador.cadastrar();
							if(cad != -1){
								novaVenda = true;
	                			JOptionPane.showMessageDialog(null, "Venda número " + Integer.toString(cad) + " cadastrada com sucesso.", "Cadastrar Venda", JOptionPane.INFORMATION_MESSAGE);      
                            	setVisible(false);
                    			apresentador = null;
                            	removeAll();
                    			dispose();
	                		}
                        	else{
                        		JOptionPane.showMessageDialog(null, "Erro ao tentar cadastrar venda.\n", "Cadastrar Venda", JOptionPane.ERROR_MESSAGE );    
                        	}
						//} else {
						//	JOptionPane.showMessageDialog(null, "Não foi possível cadastrar venda.\nDébito: " + debito.toString() + "\nLimite " + pessoa.getLimite() + "\n Contacte o Adminstrador.\n", "Cadastrar Venda", JOptionPane.ERROR_MESSAGE );    
						//}
					} catch (NullPointerException ex) {
						ex.printStackTrace();
	            		JOptionPane.showMessageDialog(null, "Não foi possível conectar ao servidor.\nO programa será finalizado.", FabricaConexao.getApelido(), JOptionPane.ERROR_MESSAGE);
	            		System.exit(0);
					} 
				}
				else{
					JOptionPane.showMessageDialog(null, "Preencha corretamente todos os campos obrigatórios.", "Cadastrar Venda", JOptionPane.ERROR_MESSAGE);
                }
			}
		});
		
		btnCadastrar.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyReleased(KeyEvent e){
				if(e.getKeyCode() == KeyEvent.VK_F1){
					painelVenda.escolherClienteCodigo();
				}
				else if(e.getKeyCode() == KeyEvent.VK_F2){
					painelVenda.escolherTransportadorCodigo();
				}
				else if(e.getKeyCode() == KeyEvent.VK_F3){
					painelVenda.escolherProdutoCodigo();
				}
			}
			
		});
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(414, 430, 80, 20);
		btnCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				apresentador = null;
				removeAll();
				dispose();
			}
		});
		btnCancelar.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyReleased(KeyEvent e){
				if(e.getKeyCode() == KeyEvent.VK_F1){
					painelVenda.escolherClienteCodigo();
				}
				else if(e.getKeyCode() == KeyEvent.VK_F2){
					painelVenda.escolherTransportadorCodigo();
				}
				else if(e.getKeyCode() == KeyEvent.VK_F3){
					painelVenda.escolherProdutoCodigo();
				}
			}
			
		});
		
		painel.add(btnCadastrar);
		painel.add(btnCancelar);
		
		lblCliente = new JLabel("F1 - Cliente");
		lblCliente.setBounds(7, 430, 80, 20);
		lblCliente.setIcon(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/cliente.png")));
		
		lblTransportador = new JLabel("F2 - Transportador");
		lblTransportador.setBounds(95, 430, 120, 20);
		lblTransportador.setIcon(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/transportador.png")));
		
		lblProduto = new JLabel("F3 - Produto");
		lblProduto.setBounds(220, 430, 80, 20);
		lblProduto.setIcon(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/produto.png")));
		
		painel.add(lblCliente);
		painel.add(lblTransportador);
		painel.add(lblProduto);
		add(painel);
		pack();
	
	}

	public boolean isNovaVenda(){
		return novaVenda;
	}
	
	@Override
	public Pessoa getCliente() {
		return painelVenda.getCliente();
	}

	@Override
	public Pessoa getTransportador() {
		return painelVenda.getTransportador();
	}

	@Override
	public List<Produto> getProdutos() {
		return painelVenda.getProdutos();
	}

	@Override
	public List<Integer> getQuantidades() {
		return painelVenda.getQuantidades();
	}

	@Override
	public String getTransporte() {
		return painelVenda.getTransporte();
	}

	@Override
	public BigDecimal getValor() {
		return painelVenda.getValor();
	}

	@Override
	public BigDecimal getValorRecebido() {
		return painelVenda.getValorRecebido();
	}

	@Override
	public BigDecimal getDesconto() {
		return painelVenda.getDesconto();
	}

	@Override
	public String getFormaPagamento() {
		return painelVenda.getFormaPagamento();
	}

	@Override
	public String getObs() {
		return painelVenda.getObs();
	}
	
	@Override
	public String getUsuario() {
		return Amana.getUsuario().getLogin();
	}
}
