package gui.swing.inicial;

import gui.swing.componentes.ADialogBackup;
import gui.swing.config.ConfigAmanaTela;
import gui.swing.usuario.AFrameLogin;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

import dao.FabricaConexao;
import modelo.UsuarioTipo;
import amana.Amana;

public class APanelTopoPrincipal extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel logo;
	private JLabel tipo;
	private JLabel logout;
	private JLabel separador;
	private JLabel config;
	private JLabel separador2;
	private JLabel backup;

	public APanelTopoPrincipal() {
		setPreferredSize(new Dimension(0, 52));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		logo = new JLabel(FabricaConexao.getApelido());
		logo.setFont(new Font("Tahoma", 1, 20));
		logo.setForeground(Color.white);
		tipo = new JLabel(" | "
				+ UsuarioTipo.getTipoUsuario(Amana.getUsuario()
						.getTipoUsuario()));
		tipo.setFont(new Font("Tahoma", 1, 20));
		tipo.setForeground(new Color(253, 222, 144));
		logout = new JLabel("Logout");
		logout.setIcon(new ImageIcon(ClassLoader
				.getSystemResource("imagens/16x16/logout.png")));
		logout.setForeground(Color.white);
		logout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		logout.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				Object[] options = { "Sim", "Não" };
				int j = JOptionPane
						.showOptionDialog(null, "Deseja Fazer Logout?",
								FabricaConexao.getApelido(), JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, options,
								options[0]);
				if (j == JOptionPane.YES_OPTION) {
					AFramePrincipal.closeAFramePrincipal();
					AFrameLogin.getAFrameLogin();
				}
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				logout.setForeground(Color.white);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				logout.setForeground(new Color(0, 148, 219));
			}
		});
		separador = new JLabel(" | ");
		separador.setFont(new Font("Tahoma", Font.BOLD, 11));
		separador.setForeground(new Color(170, 170, 170));
		config = new JLabel("Configurações");
		config.setIcon(new ImageIcon(ClassLoader
				.getSystemResource("imagens/16x16/config.png")));
		config.setForeground(Color.white);
		config.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		config.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				new ConfigAmanaTela();
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				config.setForeground(Color.white);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				config.setForeground(new Color(0, 148, 219));
			}
		});

		separador2 = new JLabel(" | ");
		separador2.setFont(new Font("Tahoma", Font.BOLD, 11));
		separador2.setForeground(new Color(170, 170, 170));
		if (Amana.getUsuario().getTipoUsuario() == 1) {
			backup = new JLabel("Backup");
			backup.setIcon(new ImageIcon(ClassLoader
					.getSystemResource("imagens/16x16/backup.png")));
			backup.setForeground(Color.white);
			backup.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			backup.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseReleased(MouseEvent arg0) {
					JFileChooser c = new JFileChooser(".");
					FileFilter filter1 = new ExtensionFileFilter(
							"Backup Amana (SQL)", new String[] { "SQL" });
					c.setFileFilter(filter1);
					c.setSelectedFile(new File("backup_amana.sql"));
					int rVal = c.showSaveDialog(APanelTopoPrincipal.this);
					if (rVal == JFileChooser.APPROVE_OPTION) {
						if (c.getSelectedFile().exists()) {
							Object[] options = { "Sim", "Não" };
							int j = JOptionPane.showOptionDialog(null,
									"Deseja substituir o arquivo existente?",
									FabricaConexao.getApelido(), JOptionPane.YES_NO_OPTION,
									JOptionPane.QUESTION_MESSAGE, null,
									options, options[0]);
							if (j == JOptionPane.YES_OPTION) {
								ADialogBackup back = new ADialogBackup(c.getCurrentDirectory().toString(), c.getSelectedFile().getName());
								//back.dispose();
							}
						}
						else {
							ADialogBackup back =new ADialogBackup(c.getCurrentDirectory().toString(), c.getSelectedFile().getName());
							//back.dispose();
						}
					}
					if (rVal == JFileChooser.CANCEL_OPTION) {

					}
				}

				@Override
				public void mouseExited(MouseEvent arg0) {
					backup.setForeground(Color.white);
				}

				@Override
				public void mouseEntered(MouseEvent arg0) {
					backup.setForeground(new Color(0, 148, 219));
				}
			});
		}
		// centro.setOpaque(false);
		// logo.setIcon(new
		// ImageIcon(ClassLoader.getSystemResource("imagemtopo.png")));
		add(Box.createRigidArea(new Dimension(25, 0)));
		add(logo);
		add(tipo);
		add(Box.createHorizontalGlue());
		if (Amana.getUsuario().getTipoUsuario() == 1) {
			add(backup);
			add(separador2);
		}
		add(config);
		add(separador);
		add(logout);
		add(Box.createRigidArea(new Dimension(10, 0)));

	}

	public void paint(Graphics g) {
		// super.paint(g);
		Graphics2D g2d = (Graphics2D) g;

		Color startColor = new Color(86, 86, 86);
		Color endColor = new Color(58, 58, 58);

		// A non-cyclic gradient
		GradientPaint gradient = new GradientPaint(0, 0, startColor, 0,
				this.getHeight(), endColor);
		g2d.setPaint(gradient);
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
		this.paintComponents(g);
	}
}
