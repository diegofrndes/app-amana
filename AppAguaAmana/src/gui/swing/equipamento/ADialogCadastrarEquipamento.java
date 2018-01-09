package gui.swing.equipamento;

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

import apresentador.equipamento.ApresentadorCadastrarEquipamento;

import espectador.equipamento.EspectadorCadastrarEquipamento;
import gui.swing.componentes.APanelDadosEquipamento;
import gui.swing.componentes.APanelTopo;
import dao.FabricaConexao;

public class ADialogCadastrarEquipamento extends JDialog implements EspectadorCadastrarEquipamento {
	
	private static final long serialVersionUID = 1L;
	private APanelTopo painelTopo;
	
    private APanelDadosEquipamento painelDadosEquipamento;

    private JButton btnCadastrar;
    private JButton btnCancelar;
    
    private boolean novoEquipamento = false;
	private ApresentadorCadastrarEquipamento apresentador; 
	
	public static ADialogCadastrarEquipamento getADialogCadastrarEquipamento() {
		return new ADialogCadastrarEquipamento();
    }
    
	private ADialogCadastrarEquipamento(){
		setTitle("Cadastrar Equipamento");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("imagens/icone.png")));
		initComponents();
		apresentador = new ApresentadorCadastrarEquipamento(this);
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
		novoEquipamento = false;
		JPanel painel = new JPanel();
		painel.setLayout(null);
		painel.setBorder(null);
		setLayout(new GridLayout());
		
		painelTopo = new APanelTopo("Equipamento", "Cadastrar", "imagens/32x32/equipamento_add.png");
		painelTopo.setBounds(0,0,320,52);
		painel.add(painelTopo);
		
		painelDadosEquipamento = new APanelDadosEquipamento();

		painelDadosEquipamento.setBounds(0, 52, 320, 195);
        painel.add(painelDadosEquipamento);
        
        btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setBounds(154, 250, 80, 20);
		btnCadastrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(painelDadosEquipamento.isValido()){
					try{
						//if(apresentador.existeEquipamento()){
                        //	JOptionPane.showMessageDialog(null, "Não foi possível cadastrar.\nExiste um equipamento com o mesmo número de série.\n", "Cadastrar Equipamento", JOptionPane.ERROR_MESSAGE );
                    	//}
                    	//else{
                    		if(apresentador.cadastrar()){
                    			novoEquipamento = true;
	                			JOptionPane.showMessageDialog(null, "Equipamento cadastrado com sucesso.", "Cadastrar Usuário", JOptionPane.INFORMATION_MESSAGE);      
                            	setVisible(false);
                    			apresentador = null;
                            	removeAll();
                    			dispose();
	                		}
                        	else{
                        		JOptionPane.showMessageDialog(null, "Erro ao tentar cadastrar equipamento.\n", "Cadastrar Equipamento", JOptionPane.ERROR_MESSAGE );    
                        	}
	                	//}
	                } catch (NullPointerException ex) {
	            		JOptionPane.showMessageDialog(null, "Não foi possível conectar ao servidor.\nO programa será finalizado.", FabricaConexao.getApelido(), JOptionPane.ERROR_MESSAGE);
	            		System.exit(0);
					}  
				}
				else{
					JOptionPane.showMessageDialog(null, "Preencha corretamente todos os campos obrigatórios.", "Cadastrar Equipamento", JOptionPane.ERROR_MESSAGE);
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
		return painelDadosEquipamento.getNome();
	}

	@Override
	public String getDescricao() {
		return painelDadosEquipamento.getDescricao();
	}

	@Override
	public String getFabricante() {
		return painelDadosEquipamento.getFabricante();
	}

	@Override
	public String getModelo() {
		return painelDadosEquipamento.getModelo();
	}

	@Override
	public String getNumeroSerie() {
		return painelDadosEquipamento.getNumeroSerie();
	}

	@Override
	public int getFreq() {
		return painelDadosEquipamento.getFreq();
	}
	
	public boolean isNovoEquipamento(){
		return novoEquipamento;
	}
}
