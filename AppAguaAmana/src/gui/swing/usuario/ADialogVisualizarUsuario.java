package gui.swing.usuario;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;

import modelo.Endereco;
import modelo.Usuario;
import apresentador.usuario.ApresentadorVisualizarUsuario;
import espectador.usuario.EspectadorVisualizarUsuario;
import gui.swing.componentes.APanelEndereco;
import gui.swing.componentes.APanelDadosPessoaFisica;
import gui.swing.componentes.APanelTopo;

public class ADialogVisualizarUsuario extends JDialog implements EspectadorVisualizarUsuario {
	
	private static final long serialVersionUID = 1L;
	private APanelTopo painelTopo;
	private APanelDadosPessoaFisica painelDados;
	private APanelEndereco painelEndereco;
	private ButtonGroup grupo;
	private JCheckBox jAdministradorCheckBox;
	private JCheckBox jEscritorioCheckBox;
	private JCheckBox jProducaoCheckBox;
	private JPanel painelTipo;
	private JButton btnCancelar;
	
	ApresentadorVisualizarUsuario apresentador;
	
	public static ADialogVisualizarUsuario getADialogVisualizarUsuario(Usuario usuario) {
		return new ADialogVisualizarUsuario(usuario);
    }
    
	private ADialogVisualizarUsuario(Usuario usuario){
		setTitle("Visualizar Usuário (" + usuario.getLogin() + ")");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("imagens/icone.png")));
		initComponents();
		apresentador = new ApresentadorVisualizarUsuario(this, usuario);
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
        setSize(326, 464);
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
		
		painelTopo = new APanelTopo("Usuário", "Visualizar", "imagens/32x32/usuario_visualizar.png");
		painelTopo.setBounds(0,0,320,52);
		painel.add(painelTopo);
		
		painelDados = new APanelDadosPessoaFisica();
		painelDados.setBounds(0,52,320,115);
		painel.add(painelDados);
		
		painelEndereco = new APanelEndereco();
		painelEndereco.setBounds(0,167,320,195);
		painel.add(painelEndereco);
		
		painelTipo = new JPanel();
		painelTipo.setLayout(null);
		painelTipo.setBorder(BorderFactory.createTitledBorder("Tipo"));
		painelTipo.setBounds(0, 362, 320, 45);
		grupo = new ButtonGroup();
		jAdministradorCheckBox = new JCheckBox("Administração", true);
		jAdministradorCheckBox.setBounds(20, 15, 90, 20);
		jEscritorioCheckBox = new JCheckBox("Escritório", false);
		jEscritorioCheckBox.setBounds(140, 15, 80, 20);
		jProducaoCheckBox = new JCheckBox("Produção", false);
		jProducaoCheckBox.setBounds(235, 15, 70, 20);
		grupo.add(jAdministradorCheckBox);
		grupo.add(jEscritorioCheckBox);
		grupo.add(jProducaoCheckBox);
		
		painelTipo.add(jAdministradorCheckBox);
		painelTipo.add(jEscritorioCheckBox);
		painelTipo.add(jProducaoCheckBox);
		painel.add(painelTipo);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(239, 410, 80, 20);
		btnCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				apresentador = null;
				removeAll();
				dispose();
			}
		});
		btnCancelar.requestFocus();
		painel.add(btnCancelar);
		
		add(painel);
		pack();
	}

	@Override
	public void setNome(String nome) {
		painelDados.setVisualizarNome(nome);
	}

	@Override
	public void setEndereco(Endereco endereco) {
		this.painelEndereco.setVisualizarEndereco(endereco);
	}

	@Override
	public void setRg(String rg) {
		painelDados.setVisualizarIdentidade(rg);
	}

	@Override
	public void setUfRg(String ufrg) {
		painelDados.setVisualizarUfRg(ufrg);
	}

	@Override
	public void setCpf(String cpf) {
		painelDados.setVisualizarCpf(cpf);
	}

	@Override
	public void setTipoUsuario(int tipo) {
		if(tipo == 1){
			jAdministradorCheckBox.setSelected(true);
			jEscritorioCheckBox.setEnabled(false);
			jProducaoCheckBox.setEnabled(false);
		}
		else if(tipo == 2){
			jEscritorioCheckBox.setSelected(true);
			jAdministradorCheckBox.setEnabled(false);
			jProducaoCheckBox.setEnabled(false);
		}
		else if(tipo == 3){
			jProducaoCheckBox.setSelected(true);
			jAdministradorCheckBox.setEnabled(false);
			jEscritorioCheckBox.setEnabled(false);
		}
	}
	
}

