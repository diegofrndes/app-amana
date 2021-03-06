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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import modelo.Endereco;

import apresentador.usuario.ApresentadorCadastrarUsuario;

import espectador.usuario.EspectadorCadastrarUsuario;
import gui.swing.componentes.ADialogSenha;
import gui.swing.componentes.APanelEndereco;
import gui.swing.componentes.APanelDadosPessoaFisica;
import gui.swing.componentes.APanelTopo;
import gui.swing.componentes.ATextFieldLogin;
import gui.swing.componentes.ATextFieldSenha;
import dao.FabricaConexao;
public class ADialogCadastrarUsuario extends JDialog implements EspectadorCadastrarUsuario {
	
	private static final long serialVersionUID = 1L;
	private APanelTopo painelTopo;
	private APanelDadosPessoaFisica painelDados;
	private APanelEndereco painelEndereco;
	private ButtonGroup grupo;
	private JCheckBox jAdministradorCheckBox;
	private JCheckBox jEscritorioCheckBox;
	private JCheckBox jProducaoCheckBox;
	private JPanel painelTipo;
	private JPanel painelInformacoes;
	private JLabel lblUsuario;
	private ATextFieldLogin jUsuarioTextField;
	private JLabel lblSenha;
	private ATextFieldSenha jSenhaTextField;
	private JButton btnCadastrar;
	private JButton btnCancelar;
	private boolean novoUsuario;
	
	private ApresentadorCadastrarUsuario apresentador;
	
	public static ADialogCadastrarUsuario getADialogCadastrarUsuario() {
		return new ADialogCadastrarUsuario();
    }
    
	private ADialogCadastrarUsuario(){
		setTitle("Cadastrar Usu�rio");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("imagens/icone.png")));
		initComponents();
		apresentador = new ApresentadorCadastrarUsuario(this);
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
        setSize(326, 541);
        Dimension ds = Toolkit.getDefaultToolkit().getScreenSize();  
        Dimension dw = getSize();  
        setLocation((ds.width - dw.width) / 2, (ds.height - dw.height) / 2);  
        setModal(true);
        setVisible(true);
	}
	
	private void initComponents(){
		novoUsuario = false;
		JPanel painel = new JPanel();
		painel.setLayout(null);
		painel.setBorder(null);
		setLayout(new GridLayout());
		
		painelTopo = new APanelTopo("Usu�rio", "Cadastrar", "imagens/32x32/usuario_add.png");
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
		jAdministradorCheckBox = new JCheckBox("Administra��o", true);
		jAdministradorCheckBox.setBounds(20, 15, 90, 20);
		jEscritorioCheckBox = new JCheckBox("Escrit�rio", false);
		jEscritorioCheckBox.setBounds(140, 15, 80, 20);
		jProducaoCheckBox = new JCheckBox("Produ��o", false);
		jProducaoCheckBox.setBounds(235, 15, 70, 20);
		grupo.add(jAdministradorCheckBox);
		grupo.add(jEscritorioCheckBox);
		grupo.add(jProducaoCheckBox);
		
		painelTipo.add(jAdministradorCheckBox);
		painelTipo.add(jEscritorioCheckBox);
		painelTipo.add(jProducaoCheckBox);
		painel.add(painelTipo);
		
		painelInformacoes = new JPanel();
		painelInformacoes.setLayout(null);
		painelInformacoes.setBorder(BorderFactory.createTitledBorder("Conta"));
		painelInformacoes.setBounds(0, 407, 320, 75);
		
		lblUsuario = new JLabel("Usu�rio (Min: 4 caracteres)");
		lblUsuario.setBounds(20, 15, 200, 20);
		jUsuarioTextField = new ATextFieldLogin();
		jUsuarioTextField.setBounds(20, 35, 130, 20);
		lblSenha = new JLabel("Senha (Min: 4 caracteres)");
		lblSenha.setBounds(170, 15, 150, 20);
		jSenhaTextField = new ATextFieldSenha();
		jSenhaTextField.setBounds(170, 35, 130, 20);		
		
		painelInformacoes.add(lblUsuario);	
		painelInformacoes.add(jUsuarioTextField);	
		painelInformacoes.add(lblSenha);	
		painelInformacoes.add(jSenhaTextField);	
		
		btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setBounds(154, 485, 80, 20);
		btnCadastrar.addActionListener(new ActionListener() {
				
			@Override
			public void actionPerformed(ActionEvent e) {
				if(painelDados.isValido() && painelEndereco.isValido() 
				&& jUsuarioTextField.isValido() && jSenhaTextField.isValido()){
					try{
						if(apresentador.existeUsuario()){
                        	JOptionPane.showMessageDialog(null, "N�o foi poss�vel cadastrar.\nExiste um usu�rio com o mesmo login.\n", "Cadastrar Usu�rio", JOptionPane.ERROR_MESSAGE );
                    	}
                    	else{
                    		ADialogSenha customDialog = new ADialogSenha(null,new String(jSenhaTextField.getPassword()), null);
							customDialog.setSize(250, 150);
							customDialog.setVisible(true);
							String s = customDialog.getValidatedText();
	                		if (s != null) {
	                			if(apresentador.cadastrar()){
                            		novoUsuario = true;
	                				JOptionPane.showMessageDialog(null, "Usu�rio cadastrado com sucesso.", "Cadastrar Usu�rio", JOptionPane.INFORMATION_MESSAGE);      
                            		setVisible(false);
                    				apresentador = null;
                            		removeAll();
                    				dispose();
	                			}
                        		else{
                            		JOptionPane.showMessageDialog(null, "Erro ao tentar cadastrar usu�rio.\n", "Cadastrar Usu�rio", JOptionPane.ERROR_MESSAGE );    
                        		}
	                		}
	                		customDialog.dispose();
	                		customDialog = null;
                    	}
					} catch (NullPointerException ex) {
	            		JOptionPane.showMessageDialog(null, "N�o foi poss�vel conectar ao servidor.\nO programa ser� finalizado.", FabricaConexao.getApelido(), JOptionPane.ERROR_MESSAGE);
	            		System.exit(0);
					} 
				}
				else{
					JOptionPane.showMessageDialog(null, "Preencha corretamente todos os campos obrigat�rios.", "Cadastrar Usu�rio", JOptionPane.ERROR_MESSAGE);
                }
			}
		});
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(239, 485, 80, 20);
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
	
	public boolean isNovoUsuario() {
		return novoUsuario;
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
	public String getLogin() {
		return jUsuarioTextField.getText();
	}

	@Override
	public String getSenha() {
		return new String(jSenhaTextField.getPassword());
	}

	@Override
	public int getTipoUsuario() {
		if(jAdministradorCheckBox.isSelected()){
			return 1;
		}
		else if(jEscritorioCheckBox.isSelected()){
			return 2;
		}
		else if(jProducaoCheckBox.isSelected()){
			return 3;
		}
		else return 3;
	}

}
