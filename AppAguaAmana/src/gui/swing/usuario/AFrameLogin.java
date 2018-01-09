package gui.swing.usuario;

import java.awt.Color;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.commons.codec.binary.Base64;

import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;

import modelo.Usuario;

import amana.Amana;
import apresentador.usuario.ApresentadorLoginUsuario;

import dao.FabricaConexao;

import espectador.usuario.EspectadorLoginUsuario;
import gui.swing.componentes.APanelTopo;
import gui.swing.config.ConfigConexaoTela;
import gui.swing.documentos.AmanaFieldDocument;
import gui.swing.inicial.AFramePrincipal;
//import gui.swing.temas.TemaAmana;
import gui.swing.temas.TemaAmana;

/**
 * 
 * 
 * @author Diego Fernandes Carlos da Costa
 * @version 1.0
 * @since AMANA 1.0
 */
public class AFrameLogin extends JFrame implements EspectadorLoginUsuario {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static volatile AFrameLogin loginTela;
	private ApresentadorLoginUsuario apresentador;
	private Image icon;
	private JButton btnCancelar, btnConectar;
	private JLabel lbUsuario, lbSenha, lbImagem, lbUsImag, lbSenhaImag;
	private JPanel pnlBack, pnlLogin, pnlUsuario, pnlSenha;
	private JTextField txtUsuario;
	private JPasswordField pswSenha;
	private APanelTopo painelLoginTopo;

	public static AFrameLogin getAFrameLogin() {
		if (loginTela == null) {
			synchronized (AFrameLogin.class) {
				if (loginTela == null) {
					loginTela = new AFrameLogin();
				}
			}
		}
		return loginTela;
	}

	private AFrameLogin() {
		super(FabricaConexao.getNome());
		try {
			Plastic3DLookAndFeel.setPlasticTheme(new TemaAmana());
			UIManager.setLookAndFeel(new com.jgoodies.looks.plastic.Plastic3DLookAndFeel());
			UIManager.getDefaults().put("TabbedPane.contentBorderInsets", new Insets(0, 0, 0, 1));
			UIManager.getDefaults().put("TabbedPane.tabsOverlapBorder", true);

			// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			// e.printStackTrace();
		}
		/*
		 * try { for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		 * System.out.println(info.getName()); if
		 * ("Windows Classic".equals(info.getName())) {
		 * UIManager.setLookAndFeel(info.getClassName()); break; } } } catch
		 * (UnsupportedLookAndFeelException e) { // handle exception } catch
		 * (ClassNotFoundException e) { // handle exception } catch
		 * (InstantiationException e) { // handle exception } catch
		 * (IllegalAccessException e) { // handle exception }
		 */
		initComponents();
		apresentador = new ApresentadorLoginUsuario(this);
		this.setVisible(true);
		//txtUsuario.setText("administrador");
		//pswSenha.setText("adminamana@engi42");
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					JMenuItem item1 = new JMenuItem("Configurações do Servidor");
					item1.setIcon(new javax.swing.ImageIcon(ClassLoader.getSystemResource("imagens/16x16/config.png")));
					item1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							new ConfigConexaoTela();
						}
					});
					// cria o menu popup
					JPopupMenu popup = new JPopupMenu();
					popup.add(item1);

					// mostra na tela
					popup.show(e.getComponent(), e.getX(), e.getY());

				}
			}
		});

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				dispose();
				loginTela = null;
				System.exit(0);
			}
		});
	}

	private void initComponents() {
		setLayout(null);

		pnlBack = new JPanel();
		// pnlBack.setBorder(javax.swing.BorderFactory.createLineBorder(new
		// java.awt.Color(0, 148, 219), 2));
		pnlBack.setBounds(0, 0, 382, 271);
		// pnlBack.setBackground(new Color(0, 148, 219));

		pnlBack.setLayout(null);

		painelLoginTopo = new APanelTopo(FabricaConexao.getApelido(), "Login", "imagens/32x32/login.png");
		painelLoginTopo.setBounds(0, 0, 382, 52);

		lbImagem = new JLabel("");
		lbImagem.setIcon(new javax.swing.ImageIcon(ClassLoader.getSystemResource("imagens/logomarca.png")));
		lbImagem.setBounds(16, 10, 164, 108);

		pnlLogin = new JPanel();
		pnlLogin.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(155, 155, 155)));
		// pnlLogin.setBackground(new Color(0, 148, 219));
		pnlLogin.setBounds(2, 54, 372, 126);
		pnlLogin.setLayout(null);
		JSeparator separator = new JSeparator(JSeparator.VERTICAL);
		separator.setBounds(190, 6, 2, 112);
		pnlLogin.add(separator);
		lbUsuario = new JLabel("Usuário");
		// lbUsuario.setFont(new java.awt.Font("Tahoma", 0, 12));
		// lbUsuario.setForeground(Color.WHITE);
		lbUsuario.setBounds(204, 4, 80, 25);
		pnlUsuario = new JPanel(null);
		pnlUsuario.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
		pnlUsuario.setBounds(204, 25, 160, 20);
		pnlUsuario.setBackground(Color.WHITE);
		lbUsImag = new JLabel(new javax.swing.ImageIcon(ClassLoader.getSystemResource("imagens/16x16/usuario.png")));
		lbUsImag.setBounds(1, 2, 16, 16);
		txtUsuario = new JTextField();
		txtUsuario.setDocument(new AmanaFieldDocument(30));
		txtUsuario.setBounds(18, 1, 141, 18);
		txtUsuario.setBorder(null);
		pnlUsuario.add(lbUsImag);
		pnlUsuario.add(txtUsuario);

		lbSenha = new JLabel("Senha");
		lbSenha.setBounds(204, 51, 60, 10);
		pnlSenha = new JPanel(null);
		pnlSenha.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
		pnlSenha.setBounds(204, 64, 160, 20);
		pnlSenha.setBackground(Color.WHITE);
		lbSenhaImag = new JLabel(new javax.swing.ImageIcon(ClassLoader.getSystemResource("imagens/16x16/senha.png")));
		lbSenhaImag.setBounds(1, 2, 16, 16);
		pswSenha = new JPasswordField();
		pswSenha.setDocument(new AmanaFieldDocument(20));
		pswSenha.setBounds(18, 1, 141, 18);
		pswSenha.setBorder(null);
		pnlSenha.add(lbSenhaImag);
		pnlSenha.add(pswSenha);

		btnConectar = new JButton("Conectar");
		btnConectar.setBounds(204, 94, 78, 22);
		// btnConectar.setIcon(new
		// javax.swing.ImageIcon(ClassLoader.getSystemResource("imagens/16x16/conectar.png")));
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(287, 94, 78, 22);
		// btnCancelar.setIcon(new
		// javax.swing.ImageIcon(ClassLoader.getSystemResource("imagens/16x16/cancelar.png")));

		pnlLogin.add(lbUsuario);
		pnlLogin.add(lbSenha);
		pnlLogin.add(pnlUsuario);
		pnlLogin.add(pnlSenha);
		pnlLogin.add(btnConectar);
		pnlLogin.add(btnCancelar);
		pnlLogin.add(lbImagem);
		pnlBack.add(painelLoginTopo);
		// pnlBack.add(pnlBackImg);
		pnlBack.add(pnlLogin);

		getContentPane().add(pnlBack);

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setIconImages(null);
		setResizable(false);
		// setUndecorated(true);
		icon = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("imagens/icone.png"));
		this.setIconImage(icon);
		txtUsuario.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				pswSenha.requestFocus();
			}
		});

		pswSenha.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					FabricaConexao.setEstrategia(0);
					Amana.setUsuario(null);
					Usuario login = apresentador.fazerLogin();
					if (login != null) {
						removeAll();
						dispose();
						Amana.setUsuario(login);
						FabricaConexao.setEstrategia(Amana.getUsuario().getTipoUsuario());
						loginTela = null;
						AFramePrincipal.getAFramePrincipal();

					} else {
						JOptionPane.showMessageDialog(null, "Usuário e/ou senha incorreto(s).", FabricaConexao.getApelido(),
								JOptionPane.ERROR_MESSAGE);
					}
				} catch (NullPointerException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null,
							"Não foi possível fazer o login.\nVerifique a conexão com o servidor e caso o erro persista contacte a manutenção.",
							FabricaConexao.getApelido(), JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnConectar.registerKeyboardAction(
				btnConectar.getActionForKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false)),
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_FOCUSED);
		btnConectar.registerKeyboardAction(
				btnConectar.getActionForKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true)),
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), JComponent.WHEN_FOCUSED);
		btnCancelar.registerKeyboardAction(
				btnConectar.getActionForKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false)),
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_FOCUSED);
		btnCancelar.registerKeyboardAction(
				btnConectar.getActionForKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true)),
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), JComponent.WHEN_FOCUSED);

		btnConectar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					FabricaConexao.setEstrategia(0);
					Amana.setUsuario(null);
					Usuario login = apresentador.fazerLogin();
					if (login != null) {
						removeAll();
						dispose();
						Amana.setUsuario(login);
						FabricaConexao.setEstrategia(Amana.getUsuario().getTipoUsuario());
						loginTela = null;
						AFramePrincipal.getAFramePrincipal();

					} else {
						JOptionPane.showMessageDialog(null, "Usuário e/ou senha incorreto(s).", FabricaConexao.getApelido(),
								JOptionPane.ERROR_MESSAGE);
					}
				} catch (NullPointerException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null,
							"Não foi possível fazer o login.\nVerifique a conexão com o servidor e caso o erro persista contacte a manutenção.",
							FabricaConexao.getApelido(), JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		btnCancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				loginTela = null;
				System.exit(0);
			}
		});

		setSize(382, 214);
		this.setLocationRelativeTo(null);
	}

	@Override
	public String getUsuario() {
		return this.txtUsuario.getText();
	}

	@Override
	public String getSenha() {
		try {
			String senha = new String(this.pswSenha.getPassword());
			MessageDigest digest;
			digest = MessageDigest.getInstance("MD5");
			digest.update(senha.getBytes());
			Base64 encoder = new Base64();
			senha = encoder.encodeAsString(digest.digest());
			return senha;

		} catch (NoSuchAlgorithmException e) {
			JOptionPane.showMessageDialog(null, "Não foi possível criptografar a senha.", FabricaConexao.getApelido(),
					JOptionPane.ERROR_MESSAGE);
			return "";
		}
	}

}
