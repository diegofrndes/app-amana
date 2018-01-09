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
import javax.swing.JPanel;

import apresentador.equipamento.ApresentadorVisualizarEquipamento;

import modelo.Equipamento;
import espectador.equipamento.EspectadorVisualizarEquipamento;
import gui.swing.componentes.APanelDadosEquipamento;
import gui.swing.componentes.APanelTopo;

public class ADialogVisualizarEquipamento extends JDialog implements EspectadorVisualizarEquipamento {
	
	private static final long serialVersionUID = 1L;
	private APanelTopo painelTopo;
	
    private APanelDadosEquipamento painelDadosEquipamento;

    private JButton btnCancelar;
    
    private boolean novoEquipamento = false;
    @SuppressWarnings("unused")
	private ApresentadorVisualizarEquipamento apresentador;

    public static ADialogVisualizarEquipamento getADialogVisualizarEquipamento(Equipamento equipamento){
    	return new ADialogVisualizarEquipamento(equipamento);
    }
    
	private ADialogVisualizarEquipamento(Equipamento equipamento){
		setTitle("Visualizar Equipamento");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("imagens/icone.png")));
		initComponents();
		apresentador = new ApresentadorVisualizarEquipamento(this, equipamento);
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
		
		painelTopo = new APanelTopo("Equipamento", "Visualizar", "imagens/32x32/equipamento_visualizar.png");
		painelTopo.setBounds(0,0,320,52);
		painel.add(painelTopo);
		
		painelDadosEquipamento = new APanelDadosEquipamento();

		painelDadosEquipamento.setBounds(0, 52, 320, 195);
        painel.add(painelDadosEquipamento);
        
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
		
		painel.add(btnCancelar);
		
        add(painel);
        pack();
        btnCancelar.requestFocus();
        
	}

	@Override
	public void setNome(String nome) {
		painelDadosEquipamento.setVisualizarNome(nome);
	}

	@Override
	public void setDescricao(String descricao) {
		painelDadosEquipamento.setVisualizarDescricao(descricao);
		
	}

	@Override
	public void setModelo(String modelo) {
		painelDadosEquipamento.setVisualizarModelo(modelo);
	}

	@Override
	public void setFabricante(String fabricante) {
		painelDadosEquipamento.setVisualizarFabricante(fabricante);
	}

	@Override
	public void setFreq(int freq) {
		painelDadosEquipamento.setVisualizarFreq(freq);
	}

	@Override
	public void setNumeroserie(String numeroserie) {
		painelDadosEquipamento.setVisualizarNumeroSerie(numeroserie);
	}
	
	public boolean isEditarEquipamento(){
		return novoEquipamento;
	}
	
}
