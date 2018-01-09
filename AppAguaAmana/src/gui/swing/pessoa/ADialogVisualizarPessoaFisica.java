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
import modelo.PessoaFisica;
import apresentador.pessoa.ApresentadorVisualizarPessoaFisica;

import espectador.pessoa.EspectadorVisualizarPessoaFisica;
import gui.swing.componentes.APanelDadosPessoaFisica;
import gui.swing.componentes.APanelEndereco;
import gui.swing.componentes.APanelInformacoes;
import gui.swing.componentes.APanelTopo;

public class ADialogVisualizarPessoaFisica extends JDialog implements EspectadorVisualizarPessoaFisica{
	private static final long serialVersionUID = 1L;
	private APanelTopo painelTopo;
	private APanelDadosPessoaFisica painelDados;
	private APanelEndereco painelEndereco;
	private APanelInformacoes painelInformacoes;
	
	private JButton btnCancelar;
	
	@SuppressWarnings("unused")
	private ApresentadorVisualizarPessoaFisica apresentador;
	
	public static ADialogVisualizarPessoaFisica getADialogVisualizarPessoaFisica(PessoaFisica pessoa) {
		return new ADialogVisualizarPessoaFisica(pessoa);
    }
    
	private ADialogVisualizarPessoaFisica(PessoaFisica pessoa){
		setTitle("Visualizar Pessoa Física");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("imagens/icone.png")));
		initComponents();
		apresentador = new ApresentadorVisualizarPessoaFisica(this, pessoa);
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
		
		painelTopo = new APanelTopo("Pessoa", "Visualizar", "imagens/32x32/pessoafisica_visualizar.png");
		painelTopo.setBounds(0,0,320,52);
		painel.add(painelTopo);
		
		painelDados = new APanelDadosPessoaFisica();
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
	public void setCpf(String cpf) {
		painelDados.setVisualizarCpf(cpf);
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
	
}
