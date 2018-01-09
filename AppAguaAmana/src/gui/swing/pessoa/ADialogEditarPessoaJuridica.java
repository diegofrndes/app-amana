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
import modelo.PessoaJuridica;
import apresentador.pessoa.ApresentadorEditarPessoaJuridica;
import espectador.pessoa.EspectadorEditarPessoaJuridica;
import gui.swing.componentes.APanelDadosPessoaJuridica;
import gui.swing.componentes.APanelEndereco;
import gui.swing.componentes.APanelInformacoes;
import gui.swing.componentes.APanelTopo;
import dao.FabricaConexao;
public class ADialogEditarPessoaJuridica extends JDialog implements EspectadorEditarPessoaJuridica {
	
	private static final long serialVersionUID = 1L;
	private APanelTopo painelTopo;
	private APanelDadosPessoaJuridica painelDados;
	private APanelEndereco painelEndereco;
	private APanelInformacoes painelInformacoes;
	
	private JButton btnCadastrar;
	private JButton btnCancelar;
	private boolean novoCliente;
	
	private ApresentadorEditarPessoaJuridica apresentador;
	
	public static ADialogEditarPessoaJuridica getADialogEditarPessoaJuridica(PessoaJuridica pessoa) {
		return new ADialogEditarPessoaJuridica(pessoa);
    }
    
	private ADialogEditarPessoaJuridica(PessoaJuridica pessoa){
		setTitle("Editar Pessoa Jurídica");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("imagens/icone.png")));
		initComponents();
		apresentador = new ApresentadorEditarPessoaJuridica(this, pessoa);
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
		
		painelTopo = new APanelTopo("Pessoa", "Editar", "imagens/32x32/pessoajuridica_add.png");
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
		
		btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setBounds(154, 475, 80, 20);
		btnCadastrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(painelDados.isValido() && painelEndereco.isValido()){
					try{
						if(apresentador.editar()){
							novoCliente = true;
	                		JOptionPane.showMessageDialog(null, "Cliente editado com sucesso.", "Editar Pessoa Jurídica", JOptionPane.INFORMATION_MESSAGE);      
                            setVisible(false);
                    		apresentador = null;
                            removeAll();
                    		dispose();
	                	}
                        else{
                        	JOptionPane.showMessageDialog(null, "Erro ao tentar editar cliente.\n", "Editar Pessoa Jurídica", JOptionPane.ERROR_MESSAGE );    
                        }
	                } catch (NullPointerException ex) {
						ex.printStackTrace();
	            		JOptionPane.showMessageDialog(null, "Não foi possível conectar ao servidor.\nO programa será finalizado.", FabricaConexao.getApelido(), JOptionPane.ERROR_MESSAGE);
	            		System.exit(0);
					} 
				}
				else{
					JOptionPane.showMessageDialog(null, "Preencha corretamente todos os campos obrigatórios.", "Editar Pessoa Jurídica", JOptionPane.ERROR_MESSAGE);
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
	public String getEmail() {
		return painelInformacoes.getEmail();
	}

	@Override
	public String getObs() {
		return painelInformacoes.getObs();
	}

	@Override
	public String getIe() {
		return painelDados.getIe();
	}

	@Override
	public String getCnpj() {
		return painelDados.getCnpj();
	}

	@Override
	public void setNome(String nome) {
		painelDados.setNome(nome);
		
	}

	@Override
	public void setCnpj(String cnpj) {
		painelDados.setCnpj(cnpj);
	}

	@Override
	public void setIe(String ie) {
		painelDados.setIe(ie);
	}

	@Override
	public void setEndereco(Endereco endereco) {
		painelEndereco.setEndereco(endereco);
	}

	@Override
	public void setEmail(String email) {
		painelInformacoes.setEmail(email);
	}

	@Override
	public void setObs(String obs) {
		painelInformacoes.setObs(obs);
	}
}
