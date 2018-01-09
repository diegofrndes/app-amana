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

import espectador.patrimonio.EspectadorEditarPatrimonio;
import gui.swing.componentes.APanelDadosPatrimonio;
import gui.swing.componentes.APanelTopo;

import apresentador.patrimonio.ApresentadorEditarPatrimonio;

import modelo.Patrimonio;
import dao.FabricaConexao;

public class ADialogEditarPatrimonio extends JDialog implements EspectadorEditarPatrimonio {
	private static final long serialVersionUID = 1L;
	private APanelTopo painelTopo;
	
    private APanelDadosPatrimonio painelDadosPatrimonio;

    private JButton btnCadastrar;
    private JButton btnCancelar;
    
    private boolean novoPatrimonio = false;
	private ApresentadorEditarPatrimonio apresentador; 

	public static ADialogEditarPatrimonio getADialogEditarPatrimonio(Patrimonio patrimonio){
		return new ADialogEditarPatrimonio(patrimonio);
	}
	
	private ADialogEditarPatrimonio(Patrimonio patrimonio){
		setTitle("Editar Bem Patrimonial");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("imagens/icone.png")));
		initComponents();
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        apresentador = new ApresentadorEditarPatrimonio(this, patrimonio);
		addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent we) {
                	setVisible(false);
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
        btnCancelar.requestFocus();
        setVisible(true);
	}
	
	private void initComponents(){
		JPanel painel = new JPanel();
		painel.setLayout(null);
		painel.setBorder(null);
		setLayout(new GridLayout());
		
		painelTopo = new APanelTopo("Patrimônio", "Editar", "imagens/32x32/patrimonio_editar.png");
		painelTopo.setBounds(0,0,320,52);
		painel.add(painelTopo);
		
		painelDadosPatrimonio = new APanelDadosPatrimonio();

		painelDadosPatrimonio.setBounds(0, 52, 320, 195);
        painel.add(painelDadosPatrimonio);
        
        btnCadastrar = new JButton("Editar");
		btnCadastrar.setBounds(154, 250, 80, 20);
		btnCadastrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(painelDadosPatrimonio.isValido()){
					try{
						
                    		if(apresentador.editar()){
                    			novoPatrimonio = true;
	                			JOptionPane.showMessageDialog(null, "Bem Patrimonial editado com sucesso.", "Editar Bem Patrimonial", JOptionPane.INFORMATION_MESSAGE);      
                            	setVisible(false);
                    			apresentador = null;
                            	removeAll();
                    			dispose();
	                		}
                        	else{
                        		JOptionPane.showMessageDialog(null, "Erro ao tentar editar bem patrimonial.\n", "Editar Bem Patrimonial", JOptionPane.ERROR_MESSAGE );    
                        	}
	                	
	                } catch (NullPointerException ex) {
	            		JOptionPane.showMessageDialog(null, "Não foi possível conectar ao servidor.\nO programa será finalizado.", FabricaConexao.getApelido(), JOptionPane.ERROR_MESSAGE);
	            		System.exit(0);
					}  
				}
				else{
					JOptionPane.showMessageDialog(null, "Preencha corretamente todos os campos obrigatórios.", "Editar Bem Patrimonial", JOptionPane.ERROR_MESSAGE);
		        }
			}
		});
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(239, 250, 80, 20);
		btnCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				removeAll();
				dispose();
			}
		});
		
		painel.add(btnCancelar);
		painel.add(btnCadastrar);
		
		
        add(painel);
		
        pack();

	}
	
	public boolean isNovoPatrimonio(){
		return novoPatrimonio;
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

	@Override
	public int getQuantidade() {
		return painelDadosPatrimonio.getQuantidade();
	}
	
	@Override
	public void setNome(String nome) {
		painelDadosPatrimonio.setNome(nome);
	}

	@Override
	public void setDescricao(String descricao) {
		painelDadosPatrimonio.setDescricao(descricao);
	}

	@Override
	public void setModelo(String modelo) {
		painelDadosPatrimonio.setModelo(modelo);
	}

	@Override
	public void setFabricante(String fabricante) {
		painelDadosPatrimonio.setFabricante(fabricante);
	}

	@Override
	public void setQuantidade(int quantidade) {
		painelDadosPatrimonio.setQuantidade(quantidade);
	}

	@Override
	public void setValor(BigDecimal valor) {
		painelDadosPatrimonio.setValor(valor.toString());
	}

	@Override
	public BigDecimal getValor() {
		return painelDadosPatrimonio.getValor();
	}

}