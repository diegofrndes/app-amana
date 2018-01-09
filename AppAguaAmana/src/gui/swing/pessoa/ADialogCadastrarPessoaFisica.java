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
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import modelo.Endereco;
import apresentador.pessoa.ApresentadorCadastrarPessoaFisica;
import espectador.pessoa.EspectadorCadastrarPessoaFisica;
import gui.swing.componentes.APanelDadosPessoaFisica;
import gui.swing.componentes.APanelEndereco;
import gui.swing.componentes.APanelInformacoes;
import gui.swing.componentes.APanelTopo;
import dao.FabricaConexao;
public class ADialogCadastrarPessoaFisica extends JDialog implements EspectadorCadastrarPessoaFisica {
	
	private static final long serialVersionUID = 1L;
	private APanelTopo painelTopo;
	private APanelDadosPessoaFisica painelDados;
	private APanelEndereco painelEndereco;
	private APanelInformacoes painelInformacoes;
	
	private JButton btnCadastrar;
	private JButton btnCancelar;
	private boolean novoCliente;
	
	private ApresentadorCadastrarPessoaFisica apresentador;
	
	public static ADialogCadastrarPessoaFisica getADialogCadastrarPessoaFisica() {
		return new ADialogCadastrarPessoaFisica();
    }
    
	private ADialogCadastrarPessoaFisica(){
		setTitle("Cadastrar Pessoa F�sica");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("imagens/icone.png")));
		initComponents();
		apresentador = new ApresentadorCadastrarPessoaFisica(this);
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
		novoCliente = false;
		JPanel painel = new JPanel();
		painel.setLayout(null);
		painel.setBorder(null);
		setLayout(new GridLayout());
		
		painelTopo = new APanelTopo("Pessoa", "Cadastrar", "imagens/32x32/pessoafisica_add.png");
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
		
		btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setBounds(154, 475, 80, 20);
		btnCadastrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(painelDados.isValido() && painelEndereco.isValido()){
					try{
						if(apresentador.cadastrar()){
							novoCliente = true;
	                		JOptionPane.showMessageDialog(null, "Pessoa F�sica cadastrada com sucesso.", "Cadastrar Pessoa F�sica", JOptionPane.INFORMATION_MESSAGE);      
                            setVisible(false);
                    		apresentador = null;
                            removeAll();
                    		dispose();
	                	}
                        else{
                        	JOptionPane.showMessageDialog(null, "Erro ao tentar cadastrar pessoa f�sica.\n", "Cadastrar Pessoa F�sica", JOptionPane.ERROR_MESSAGE );    
                        }
					} catch (NullPointerException ex) {
						ex.printStackTrace();
	            		JOptionPane.showMessageDialog(null, "N�o foi poss�vel conectar ao servidor.\nO programa ser� finalizado.", FabricaConexao.getApelido(), JOptionPane.ERROR_MESSAGE);
	            		System.exit(0);
					} 
				}
				else{
					JOptionPane.showMessageDialog(null, "Preencha corretamente todos os campos obrigat�rios.", "Cadastrar Pessoa F�sica", JOptionPane.ERROR_MESSAGE);
                }
			}
		});
		
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
		
		painel.add(btnCadastrar);
		painel.add(btnCancelar);
		
		painel.add(painelInformacoes);
		
		add(painel);
		pack();
	}
	
	public boolean isNovoCliente() {
		return novoCliente;
	}
	
	@Override
	public String getNome() {
		return painelDados.getNome();
	}

	@Override
	public Endereco getEndereco() {
		return painelEndereco.getEndereco();
	}

	@Override
	public String getRg() {
		return painelDados.getIdentidade();
	}

	@Override
	public String getUfRg() {
		return painelDados.getUfRg();
	}

	@Override
	public String getCpf() {
		return painelDados.getCpf();
	}

	@Override
	public String getEmail() {
		return painelInformacoes.getEmail();
	}

	@Override
	public String getObs() {
		return painelInformacoes.getObs();
	}

}
