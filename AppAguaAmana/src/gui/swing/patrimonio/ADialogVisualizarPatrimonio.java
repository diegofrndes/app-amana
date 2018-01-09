package gui.swing.patrimonio;

import espectador.patrimonio.EspectadorVisualizarPatrimonio;
import gui.swing.componentes.APanelDadosPatrimonio;
import gui.swing.componentes.APanelTopo;

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
import javax.swing.JPanel;

import apresentador.patrimonio.ApresentadorVisualizarPatrimonio;

import modelo.Patrimonio;

public class ADialogVisualizarPatrimonio extends JDialog implements EspectadorVisualizarPatrimonio {
	private static final long serialVersionUID = 1L;
	private APanelTopo painelTopo;
	
    private APanelDadosPatrimonio painelDadosPatrimonio;

    private JButton btnCancelar;
    
    @SuppressWarnings("unused")
	private ApresentadorVisualizarPatrimonio apresentador;

    public static ADialogVisualizarPatrimonio getADialogVisualizarPatrimonio(Patrimonio patrimonio){
    	return new ADialogVisualizarPatrimonio(patrimonio);
    }
    
	private ADialogVisualizarPatrimonio(Patrimonio patrimonio){
		setTitle("Visualizar Bem Patrimonial");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("imagens/icone.png")));
		initComponents();
		apresentador = new ApresentadorVisualizarPatrimonio(this, patrimonio);
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
        btnCancelar.requestFocus();
        setVisible(true);
	}
	
	private void initComponents(){
		JPanel painel = new JPanel();
		painel.setLayout(null);
		painel.setBorder(null);
		setLayout(new GridLayout());
		
		painelTopo = new APanelTopo("Patrimônio", "Visualizar", "imagens/32x32/patrimonio_visualizar.png");
		painelTopo.setBounds(0,0,320,52);
		painel.add(painelTopo);
		
		painelDadosPatrimonio = new APanelDadosPatrimonio();
		painelDadosPatrimonio.setBounds(0, 52, 320, 195);
        painel.add(painelDadosPatrimonio);
        
        
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

	}

	@Override
	public void setNome(String nome) {
		painelDadosPatrimonio.setVisualizarNome(nome);
	}

	@Override
	public void setDescricao(String descricao) {
		painelDadosPatrimonio.setVisualizarDescricao(descricao);
	}

	@Override
	public void setModelo(String modelo) {
		painelDadosPatrimonio.setVisualizarModelo(modelo);
	}

	@Override
	public void setFabricante(String fabricante) {
		painelDadosPatrimonio.setVisualizarFabricante(fabricante);
	}

	@Override
	public void setQuantidade(int quantidade) {
		painelDadosPatrimonio.setVisualizarQuantidade(quantidade);
	}

	@Override
	public void setValor(BigDecimal valor) {
		painelDadosPatrimonio.setVisualizarValor(valor.toString());
	}
}