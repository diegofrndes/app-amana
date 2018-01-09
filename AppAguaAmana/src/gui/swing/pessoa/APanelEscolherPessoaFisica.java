package gui.swing.pessoa;

import java.awt.Color;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.math.BigDecimal;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumnModel;

import dao.FabricaDao;
import modelo.Pessoa;
import amana.Amana;
import apresentador.pessoa.ApresentadorPanelPessoaFisica;
import espectador.pessoa.EspectadorPanelPessoaFisica;
import gui.swing.componentes.ADialogAdmLogin;
import gui.swing.componentes.ADialogSenha;
import gui.swing.componentes.ATable;
import gui.swing.inicial.AFramePrincipal;
import dao.FabricaConexao;
public class APanelEscolherPessoaFisica extends JPanel implements
		EspectadorPanelPessoaFisica {

	private static final long serialVersionUID = 1L;
	private JPanel painel;
	private JPanel menuUsuario;
	private JLabel labelMenu;
	private JLabel labelBusca;
	private JPanel painelTopo;
	private JPanel painelBusca;
	private JPanel painelLista;
	private JSplitPane painelBox;
	private JSplitPane painelPrincipal;
	private JSplitPane splitPrincipal;
	private JToggleButton botaoNome;
	private JToggleButton botaoRg;
	private JToggleButton botaoCpf;
	private ButtonGroup group;
	private JList list;
	private JLabel menuSelecionado;
	private JSplitPane painelDividirBotaoTabela;
	private JPanel painelBotoes;
	private ATable painelTabela;
	// private PainelEstatisticaUsuario painelEstatistica;
	private JSplitPane painelDividirTopoPrincipal;
	private JSplitPane painelDividirTopoBotoes;

	private JButton botaoEscolher;
	private JButton botaoAtualizar;

	private JTextField busca;
	private ApresentadorPanelPessoaFisica apresentador;
	private int mouseOver = -1;

	private ADialogEscolherPessoa tela;
	private boolean chequeLimite;
	
	public APanelEscolherPessoaFisica(ADialogEscolherPessoa tela, boolean cl) {
		setChequeLimite(cl);
		this.tela = tela;
		initComponents();
		apresentador = new ApresentadorPanelPessoaFisica(this);
		painelTabela.getTabela().setDefaultRenderer(Object.class,
				new PessoaCellRenderer());
		buscar();
	}

	public void buscar() {
		try {
			botaoEscolher.setEnabled(false);
			if (list.getSelectedValue().toString().equals("Todos")) {

				if (painelDividirBotaoTabela.getRightComponent() != painelTabela) {
					painelDividirBotaoTabela.setRightComponent(painelTabela);
					painelDividirBotaoTabela.setDividerLocation(40);
					painelDividirBotaoTabela.setDividerSize(0);
				}
				apresentador.procurarTodos();
			} else if (list.getSelectedValue().toString().equals("Clientes")) {

				if (painelDividirBotaoTabela.getRightComponent() != painelTabela) {
					painelDividirBotaoTabela.setRightComponent(painelTabela);
					painelDividirBotaoTabela.setDividerLocation(40);
					painelDividirBotaoTabela.setDividerSize(0);
				}
				apresentador.procurarClientes();
			} else if (list.getSelectedValue().toString()
					.equals("Fornecedores")) {

				if (painelDividirBotaoTabela.getRightComponent() != painelTabela) {
					painelDividirBotaoTabela.setRightComponent(painelTabela);
					painelDividirBotaoTabela.setDividerLocation(40);
					painelDividirBotaoTabela.setDividerSize(0);
				}
				apresentador.procurarFornecedores();
			} else if (list.getSelectedValue().toString()
					.equals("Transportadores")) {

				if (painelDividirBotaoTabela.getRightComponent() != painelTabela) {
					painelDividirBotaoTabela.setRightComponent(painelTabela);
					painelDividirBotaoTabela.setDividerLocation(40);
					painelDividirBotaoTabela.setDividerSize(0);
				}
				apresentador.procurarTransportadores();
			}

		} catch (NullPointerException ex) {
			ex.printStackTrace();
			JOptionPane
					.showMessageDialog(
							null,
							"Não foi possível conectar ao servidor.\nO programa será finalizado.",
							FabricaConexao.getApelido(), JOptionPane.ERROR_MESSAGE);
			AFramePrincipal.closeAFramePrincipal();
			System.exit(0);
		}
	}

	private void initComponents() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		painel = new JPanel();
		painel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0,
				Color.LIGHT_GRAY));
		menuUsuario = new JPanel();
		menuUsuario.setPreferredSize(new Dimension(224, 0));
		menuUsuario.setLayout(new BorderLayout());
		menuUsuario.setBorder(BorderFactory.createMatteBorder(10, 13, 0, 13,
				new Color(243, 243, 243)));
		painelTopo = new JPanel();
		painelTopo.setLayout(null);
		painelTopo.setPreferredSize(new Dimension(0, 60));
		painelBusca = new JPanel();
		painelBusca.setBorder(new LineBorder(Color.LIGHT_GRAY));
		painelBusca.setLayout(null);
		painelBusca.setBackground(Color.white);
		painelBusca.setBounds(0, 0, 197, 25);
		busca = new JTextField();
		busca.setBounds(2, 1, 174, 23);
		busca.setFont(new Font("Tahoma", Font.PLAIN, 12));
		busca.setBorder(null);
		labelBusca = new JLabel(new ImageIcon(
				ClassLoader.getSystemResource("imagens/16x16/pesquisar.png")));
		labelBusca.setBounds(176, 4, 16, 16);
		labelBusca.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		painelBusca.add(busca);
		painelBusca.add(labelBusca);
		botaoNome = new JToggleButton("Nome");
		botaoNome.setBounds(0, 27, 64, 22);
		botaoNome.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				buscar();
			}
		});
		botaoRg = new JToggleButton("RG");
		botaoRg.setBounds(67, 27, 64, 22);
		botaoRg.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				buscar();
			}
		});
		botaoCpf = new JToggleButton("CPF");
		botaoCpf.setBounds(134, 27, 64, 22);
		botaoCpf.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				buscar();
			}
		});
		painelTopo.add(painelBusca);
		group = new ButtonGroup();
		group.add(botaoNome);
		group.add(botaoRg);
		group.add(botaoCpf);
		botaoNome.setSelected(true);
		painelTopo.add(botaoRg);
		painelTopo.add(botaoNome);
		painelTopo.add(botaoCpf);
		menuUsuario.add(painelTopo, BorderLayout.NORTH);
		painelLista = new JPanel(new BorderLayout(0, 5));
		labelMenu = new JLabel("Menu Pessoa Física");
		labelMenu.setFont(new Font("Tahoma", Font.BOLD, 11));
		painelLista.add(labelMenu, BorderLayout.NORTH);
		DefaultListModel model = new DefaultListModel();
		model.addElement("Todos");
		model.addElement("Clientes");
		model.addElement("Fornecedores");
		model.addElement("Transportadores");

		list = new JList(model);
		list.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
				final int x = e.getX();
				final int y = e.getY();
				// only display a hand if the cursor is over the items
				final Rectangle cellBounds = list.getCellBounds(0, list
						.getModel().getSize() - 1);
				if (cellBounds != null && cellBounds.contains(x, y)) {
					mouseOver = list.locationToIndex(new Point(x, y));
					list.setCursor(new Cursor(Cursor.HAND_CURSOR));
					list.repaint();
				} else {
					mouseOver = -1;
					list.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					list.repaint();
				}
			}

			@Override
			public void mouseDragged(MouseEvent e) {
			}
		});
		list.addMouseListener(new MouseAdapter() {
			public void mouseExited(MouseEvent e) {
				mouseOver = -1;
				list.repaint();
			}
		});

		list.setBackground(new Color(243, 243, 243));
		list.setCellRenderer(new ImagensClienteFisica());
		list.setSelectedIndex(0);
		list.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
		painelLista.setBorder(null);
		painelLista.add(Box.createRigidArea(new Dimension(7, 0)),
				BorderLayout.WEST);
		painelLista.add(list, BorderLayout.CENTER);
		menuUsuario.add(painelLista, BorderLayout.CENTER);

		painelBox = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		painelBox.setBorder(null);
		menuSelecionado = new JLabel("Todos", SwingConstants.CENTER);
		menuSelecionado.setFont(new Font("Tahoma", Font.BOLD, 18));
		splitPrincipal = new JSplitPane();
		JPanel s = new JPanel();
		s.setBackground(Color.white);
		splitPrincipal.setLeftComponent(s);
		splitPrincipal.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 0,
				Color.LIGHT_GRAY));
		painelPrincipal = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		painelPrincipal.setBackground(Color.white);
		painelPrincipal.setBorder(null);
		painelPrincipal.setDividerLocation(40);
		painelPrincipal.setDividerSize(0);
		painelPrincipal.setLeftComponent(menuSelecionado);
		painelDividirTopoPrincipal = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		JPanel p1 = new JPanel();
		p1.setBackground(Color.white);
		p1.setBorder(null);
		painelDividirTopoPrincipal.setBorder(null);
		painelDividirTopoPrincipal.setLeftComponent(p1);
		painelDividirTopoPrincipal.setRightComponent(painelPrincipal);
		painelDividirTopoPrincipal.setDividerLocation(5);
		painelDividirTopoPrincipal.setDividerSize(0);

		painelDividirTopoBotoes = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		painelDividirTopoBotoes.setBorder(BorderFactory.createMatteBorder(1, 0,
				1, 0, new Color(243, 243, 243)));
		JPanel pBlue = new JPanel();
		pBlue.setBorder(null);
		pBlue.setBackground(new Color(0, 148, 219));
		painelDividirTopoBotoes.setLeftComponent(pBlue);
		painelDividirTopoBotoes.setDividerLocation(5);
		painelDividirTopoBotoes.setDividerSize(0);
		painelBotoes = new JPanel();
		painelBotoes.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
				new Color(180, 180, 180)));

		botaoAtualizar = new JButton("Atualizar");
		botaoAtualizar.setIcon(new ImageIcon(ClassLoader
				.getSystemResource("imagens/16x16/atualizar.png")));
		botaoAtualizar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				buscar();
			}
		});
		botaoEscolher = new JButton("Escolher");
		botaoEscolher.setEnabled(false);
		botaoEscolher.setIcon(new ImageIcon(ClassLoader
				.getSystemResource("imagens/16x16/escolher.png")));
		botaoEscolher.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				ModeloTabelaPessoaFisica modelo = (ModeloTabelaPessoaFisica) painelTabela
						.getTabela().getModel();
				Pessoa pessoa = modelo.getPessoa(painelTabela.getTabela()
						.getSelectedRow());

				BigDecimal debito = FabricaDao.getPessoaDao().debito(pessoa);
				if (!chequeLimite || (Amana.getUsuario().getTipoUsuario() == 1) || (debito.signum() == 1 || debito.signum() == 0)) {
					tela.setEscolhida(pessoa);
					tela.setVisible(false);
					tela.removeAll();
					tela.dispose();
				} else {
					BigDecimal sub = pessoa.getLimite().add(debito);
					if (sub.signum() == -1 || sub.signum() == 0) {
						JOptionPane.showMessageDialog(null,
								"Limite de " + pessoa.getNome() + " ultrapassado.\nLimite: "
										+ pessoa.getLimite().toString()
										+ "\nDébito: " + debito.toString()
										+ "\n\n"
										+ "Para escolher o cliente com o limite ultrapassado\n"
										+ "digite, a seguir, o login e senha de administrador.\n", "Escolher Pessoa",
								JOptionPane.INFORMATION_MESSAGE);
						
						ADialogAdmLogin customDialog = new ADialogAdmLogin(null, null);
						customDialog.setSize(250, 150);
						customDialog.setVisible(true);
				
                		if (customDialog.getValidAdmin()) {
                			tela.setEscolhida(pessoa);
    						tela.setVisible(false);
    						tela.removeAll();
    						tela.dispose();
                		} 				
					} else {
						tela.setEscolhida(pessoa);
						tela.setVisible(false);
						tela.removeAll();
						tela.dispose();
					}
				}

			}
		});

		painelBotoes.setLayout(new BoxLayout(painelBotoes, BoxLayout.X_AXIS));
		painelBotoes.add(Box.createRigidArea(new Dimension(5, 0)));
		painelBotoes.add(botaoEscolher);
		painelBotoes.add(Box.createRigidArea(new Dimension(5, 0)));
		painelBotoes.add(botaoAtualizar);

		painelDividirTopoBotoes.setRightComponent(painelBotoes);
		painelTabela = new ATable();
		painelTabela.setBackground(Color.BLUE);
		painelDividirBotaoTabela = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		painelDividirBotaoTabela.setDividerLocation(40);
		painelDividirBotaoTabela.setDividerSize(0);
		painelDividirBotaoTabela.setLeftComponent(painelDividirTopoBotoes);
		painelDividirBotaoTabela.setRightComponent(painelTabela);
		painelDividirBotaoTabela.setBorder(null);
		painelPrincipal.setRightComponent(painelDividirBotaoTabela);
		splitPrincipal.setRightComponent(painelDividirTopoPrincipal);
		splitPrincipal.setDividerSize(0);
		splitPrincipal.setDividerLocation(0);

		JPanel p = new JPanel();
		p.setBorder(null);
		painelBox.setLeftComponent(p);
		painelBox.setRightComponent(splitPrincipal);
		painelBox.setDividerSize(0);
		painelBox.setDividerLocation(10);

		painel.setLayout(new BorderLayout());
		painel.add(menuUsuario, BorderLayout.WEST);
		painel.add(painelBox, BorderLayout.CENTER);

		// add(Box.createRigidArea(new Dimension(24, 0)));
		add(painel);

		busca.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				buscar();
			}
		});
		labelBusca.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				buscar();
			}
		});

		this.painelTabela.setPrimeiroActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				painelTabela.setPag(painelTabela.getPag() - 10);
				buscar();
			}
		});

		this.painelTabela.setAnteriorActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				painelTabela.setPag(painelTabela.getPag() - 1);
				buscar();
			}
		});

		this.painelTabela.setProximoActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				painelTabela.setPag(painelTabela.getPag() + 1);
				buscar();
			}
		});

		this.painelTabela.setUltimoActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				painelTabela.setPag(painelTabela.getPag() + 10);
				buscar();
			}
		});

		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				painelTabela.setPag(0);
				buscar();
			}
		});

		painelTabela.getTabela().getSelectionModel()
				.addListSelectionListener(new ListSelectionListener() {

					@Override
					public void valueChanged(ListSelectionEvent e) {
						if (painelTabela.getTabela().getSelectedRow() != -1
								&& !list.getSelectedValue().toString()
										.equals("Lixeira")) {
							botaoEscolher.setEnabled(true);
						}
					}
				});
	}

	public void setModelo(ModeloTabelaPessoaFisica model) {
		painelTabela.setModeloTabela(model);
		TableColumnModel columnModel = painelTabela.getTabela()
				.getColumnModel();
		painelTabela.getTabela().getTableHeader()
				.setFont(new Font("Tahoma", Font.BOLD, 11));
		painelTabela.getTabela().getTableHeader().setBorder(null);

		columnModel.getColumn(0).setHeaderValue("Código");
		columnModel.getColumn(0).setPreferredWidth(50);

		columnModel.getColumn(1).setHeaderValue("Nome");
		columnModel.getColumn(1).setPreferredWidth(150);

		columnModel.getColumn(2).setHeaderValue("RG");
		columnModel.getColumn(2).setPreferredWidth(150);

		columnModel.getColumn(3).setHeaderValue("CPF");
		columnModel.getColumn(3).setPreferredWidth(150);

		columnModel.getColumn(4).setHeaderValue("Telefone");
		columnModel.getColumn(4).setPreferredWidth(150);

		columnModel.getColumn(5).setHeaderValue("E-mail");
		columnModel.getColumn(5).setPreferredWidth(150);

		columnModel.getColumn(6).setHeaderValue("Limite (R$)");
		columnModel.getColumn(6).setPreferredWidth(150);
		
		columnModel.getColumn(7).setHeaderValue("Débito (R$)");
		columnModel.getColumn(7).setPreferredWidth(150);

		columnModel.getColumn(8).setHeaderValue("Adiantado (R$)");
		columnModel.getColumn(8).setPreferredWidth(150);

	}

	private class ImagensClienteFisica extends JLabel implements
			ListCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public ImagensClienteFisica() {
			setOpaque(true);
			setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(
					243, 243, 243)));
		}

		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {

			setText(value.toString());
			if (value.toString() == "Todos") {
				setIcon(new ImageIcon(
						ClassLoader.getSystemResource("imagens/16x16/pessoafisica.png")));
			} else if (value.toString() == "Clientes") {
				setIcon(new ImageIcon(
						ClassLoader.getSystemResource("imagens/16x16/cliente.png")));
			} else if (value.toString() == "Fornecedores") {
				setIcon(new ImageIcon(
						ClassLoader.getSystemResource("imagens/16x16/fornecedor.png")));
			}
			if (value.toString() == "Transportadores") {
				setIcon(new ImageIcon(
						ClassLoader
								.getSystemResource("imagens/16x16/transportador.png")));
			} else if (value.toString() == "Estatísticas") {
				setIcon(new ImageIcon(
						ClassLoader.getSystemResource("imagens/16x16/estatisticas.png")));
			}

			if (isSelected) {
				menuSelecionado.setText(value.toString());
				setFont(new Font("Tahoma", Font.BOLD, 11));
				setForeground(Color.white);
				setBackground(new Color(0, 155, 225));
			} else {
				if (index == mouseOver) {
					setFont(new Font("Tahoma", Font.PLAIN, 11));
					setForeground(Color.white);
					setBackground(new Color(0, 155, 225));

				} else {
					setFont(new Font("Tahoma", Font.PLAIN, 11));
					setForeground(Color.black);
					setBackground(new Color(243, 243, 243));
				}
			}
			return this;
		}
	}

	@Override
	public String getBusca() {
		if (botaoCpf.isSelected()) {
			String result = busca.getText();
			result = result.replace(".", "");
			result = result.replace("-", "");
			return result;
		} else
			return busca.getText();
	}

	@Override
	public String getFiltro() {
		if (botaoNome.isSelected()) {
			return "pessoa.nome";
		} else if (botaoRg.isSelected()) {
			return "rg";
		} else
			return "cpf";
	}

	@Override
	public int getStart() {
		return painelTabela.getPag();
	}

	@Override
	public ModeloTabelaPessoaFisica getModeloTabelaPessoaFisica() {
		return (ModeloTabelaPessoaFisica) painelTabela.getTabela().getModel();
	}

	@Override
	public void setModeloTabelaPessoaFisica(ModeloTabelaPessoaFisica modelo) {
		setModelo(modelo);
	}

	@Override
	public int getLimite() {
		return painelTabela.getLimite();
	}

	public boolean isChequeLimite() {
		return chequeLimite;
	}

	public void setChequeLimite(boolean chequeLimite) {
		this.chequeLimite = chequeLimite;
	}

}
