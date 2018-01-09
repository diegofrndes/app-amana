package gui.swing.patrimonio;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import apresentador.patrimonio.ApresentadorCadastrarPatrimonio;

import espectador.patrimonio.EspectadorCadastrarPatrimonio;
import gui.swing.componentes.APanelDadosPatrimonio;
import gui.swing.componentes.APanelTopo;
import dao.FabricaConexao;

public class ADialogCadastrarPatrimonio extends JDialog implements EspectadorCadastrarPatrimonio {
	
	private static final long serialVersionUID = 1L;
	private APanelTopo painelTopo;
	
    private APanelDadosPatrimonio painelDadosPatrimonio;

    private JButton btnCadastrar;
    private JButton btnCancelar;
    
    private boolean novoPatrimonio = false;
	private ApresentadorCadastrarPatrimonio apresentador; 

	public static ADialogCadastrarPatrimonio getADialogCadastrarPatrimonio(){
		return new ADialogCadastrarPatrimonio();
	}
	
	private ADialogCadastrarPatrimonio(){
		setTitle("Cadastrar Bem Patrimonial");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("imagens/icone.png")));
		initComponents();
		apresentador = new ApresentadorCadastrarPatrimonio(this);
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
        setSize(326, 306);
        Dimension ds = Toolkit.getDefaultToolkit().getScreenSize();  
        Dimension dw = getSize();  
        setLocation((ds.width - dw.width) / 2, (ds.height - dw.height) / 2);  
        setModal(true);
        setVisible(true);
	}
	
	private void initComponents(){
		novoPatrimonio = false;
		JPanel painel = new JPanel();
		painel.setLayout(null);
		painel.setBorder(null);
		setLayout(new GridLayout());
		
		painelTopo = new APanelTopo("Patrimônio", "Cadastrar", "imagens/32x32/patrimonio_add.png");
		painelTopo.setBounds(0,0,320,52);
		painel.add(painelTopo);
		
		painelDadosPatrimonio = new APanelDadosPatrimonio();

		painelDadosPatrimonio.setBounds(0, 52, 320, 195);
        painel.add(painelDadosPatrimonio);
        
        btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setBounds(154, 250, 80, 20);
		btnCadastrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(painelDadosPatrimonio.isValido()){
					try{
                    		if(apresentador.cadastrar()){
                    			novoPatrimonio = true;
	                			JOptionPane.showMessageDialog(null, "Bem Patrimonial cadastrado com sucesso.", "Cadastrar Bem Patrimonial", JOptionPane.INFORMATION_MESSAGE);      
                            	setVisible(false);
                    			apresentador = null;
                            	removeAll();
                    			dispose();
	                		}
                        	else{
                        		JOptionPane.showMessageDialog(null, "Erro ao tentar cadastrar bem patrimonial.\n", "Cadastrar Bem Patrimonial", JOptionPane.ERROR_MESSAGE );    
                        	}
	                	
	                } catch (NullPointerException ex) {
	            		JOptionPane.showMessageDialog(null, "Não foi possível conectar ao servidor.\nO programa será finalizado.", FabricaConexao.getApelido(), JOptionPane.ERROR_MESSAGE);
	            		System.exit(0);
					}  
				}
				else{
					JOptionPane.showMessageDialog(null, "Preencha corretamente todos os campos obrigatórios.", "Cadastrar Bem Patrimonial", JOptionPane.ERROR_MESSAGE);
		        }
			}
		});
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(239, 250, 80, 20);
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

	@Override
	public String getNome() {
		return painelDadosPatrimonio.getNome();
	}

	@Override
	public String getDescricao() {
		return painelDadosPatrimonio.getDescricao();
	}

	@Override
	public String getFabricante() {
		return painelDadosPatrimonio.getFabricante();
	}

	@Override
	public String getModelo() {
		return painelDadosPatrimonio.getModelo();
	}
	
	public boolean isNovoPatrimonio(){
		return novoPatrimonio;
	}

	@Override
	public int getQuantidade() {
		return painelDadosPatrimonio.getQuantidade();
	}

	@Override
	public BigDecimal getValor() {
		return painelDadosPatrimonio.getValor();
	}

}
