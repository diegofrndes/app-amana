package gui.swing.pessoa;

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

import modelo.Endereco;
import modelo.PessoaJuridica;
import apresentador.pessoa.ApresentadorVisualizarPessoaJuridica;

import espectador.pessoa.EspectadorVisualizarPessoaJuridica;
import gui.swing.componentes.APanelDadosPessoaJuridica;
import gui.swing.componentes.APanelEndereco;
import gui.swing.componentes.APanelInformacoes;
import gui.swing.componentes.APanelTopo;

public class ADialogVisualizarPessoaJuridica extends JDialog implements EspectadorVisualizarPessoaJuridica{
	private static final long serialVersionUID = 1L;
	private APanelTopo painelTopo;
	private APanelDadosPessoaJuridica painelDados;
	private APanelEndereco painelEndereco;
	private APanelInformacoes painelInformacoes;
	
	private JButton btnCancelar;
	
	@SuppressWarnings("unused")
	private ApresentadorVisualizarPessoaJuridica apresentador;
	
	public static ADialogVisualizarPessoaJuridica getADialogVisualizarPessoaJuridica(PessoaJuridica pessoa) {
		return new ADialogVisualizarPessoaJuridica(pessoa);
    }
    
	private ADialogVisualizarPessoaJuridica(PessoaJuridica pessoa){
		setTitle("Visualizar Pessoa Jurídica");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("imagens/icone.png")));
		initComponents();
		apresentador = new ApresentadorVisualizarPessoaJuridica(this, pessoa);
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
        setSize(326, 531);
        Dimension ds = Toolkit.getDefaultToolkit().getScreenSize();  
        Dimension dw = getSize();  
        setLocation((ds.width - dw.width) / 2, (ds.height - dw.height) / 2);  
        setModal(true);
        setVisible(true);
	}
	
	private void initComponents(){
		JPanel painel = new JPanel();
		painel.setLayout(null);
		painel.setBorder(null);
		setLayout(new GridLayout());
		
		painelTopo = new APanelTopo("Pessoa", "Visualizar", "imagens/32x32/pessoajuridica_visualizar.png");
		painelTopo.setBounds(0,0,320,52);
		painel.add(painelTopo);
		
		painelDados = new APanelDadosPessoaJuridica();
		painelDados.setBounds(0,52,320,115);
		painel.add(painelDados);
		
		painelEndereco = new APanelEndereco();
		painelEndereco.setBounds(0,167,320,195);
		painel.add(painelEndereco);
		
		painelInformacoes = new APanelInformacoes();
		painelInformacoes.setLayout(null);
		painelInformacoes.setBounds(0, 362, 320, 110);
	
		painel.add(painelInformacoes);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(239, 475, 80, 20);
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
		
		painel.add(painelInformacoes);
		
		add(painel);
		pack();
		
		btnCancelar.requestFocus();		
	
	}

	@Override
	public void setNome(String nome) {
		painelDados.setVisualizarNome(nome);
	}

	@Override
	public void setEndereco(Endereco endereco) {
		painelEndereco.setVisualizarEndereco(endereco);
	}

	@Override
	public void setEmail(String email) {
		painelInformacoes.setVisualizarEmail(email);
	}

	@Override
	public void setObs(String obs) {
		painelInformacoes.setVisualizarObs(obs);
	}

	@Override
	public void setCnpj(String cnpj) {
		painelDados.setVisualizarCnpj(cnpj);
	}

	@Override
	public void setIe(String ie) {
		painelDados.setVisualizarIe(ie);
	}
	
}
