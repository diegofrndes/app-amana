package gui.swing.produto;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumnModel;

import amana.Amana;
import apresentador.produto.ApresentadorPanelProduto;
import espectador.produto.EspectadorPanelProduto;
import gui.swing.componentes.ATable;
import gui.swing.inicial.AFramePrincipal;
import gui.swing.pessoa.ADialogRelatorioPessoa;
import gui.swing.pessoa.ModeloTabelaPessoaFisica;
import dao.FabricaConexao;
public class APanelProduto extends JPanel implements EspectadorPanelProduto {

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
	private JToggleButton botaoCodigo;
	private JToggleButton botaoNome;
	private ButtonGroup group;
	private JList list;
	private JLabel menuSelecionado;
	private JSplitPane painelDividirBotaoTabela;
	private JPanel painelBotoes;
	private ATable painelTabela;
	//private PainelEstatisticaUsuario painelEstatistica;
	private JSplitPane painelDividirTopoPrincipal;
	private JSplitPane painelDividirTopoBotoes;
	
	private JButton botaoCadastrar;
	private JButton botaoEditar;
	private JButton botaoVisualizar;	
	private JButton botaoRelatorio;	
	private JButton botaoRelatorioRanking;	
	private JButton botaoRelatorioCidade;
	private JButton botaoRelatorioCliente;
	private JButton botaoAtualizar;
	
	private JTextField busca;
	private ApresentadorPanelProduto apresentador;
	private int mouseOver = -1;
	
	public APanelProduto()
	{
		initComponents();
		apresentador = new ApresentadorPanelProduto(this);
		painelTabela.getTabela().setDefaultRenderer(Object.class, new ProdutoCellRenderer());
		buscar();
	
	}
	
	public void buscar(){
		try{
			botaoEditar.setEnabled(false);
			botaoVisualizar.setEnabled(false);
			botaoRelatorio.setEnabled(false);
			botaoRelatorioRanking.setEnabled(false);
			if(list.getSelectedValue().toString().equals("Todos")){
				
				if(painelDividirBotaoTabela.getRightComponent() != painelTabela){
					painelDividirBotaoTabela.setRightComponent(painelTabela);
					painelDividirBotaoTabela.setDividerLocation(40);
					painelDividirBotaoTabela.setDividerSize(0);
				}
				apresentador.procurarTodos();		
			}
			
		} catch(NullPointerException ex){
			JOptionPane.showMessageDialog(null, "Não foi possível conectar ao servidor.\nO programa será finalizado.", FabricaConexao.getApelido(), JOptionPane.ERROR_MESSAGE);
			AFramePrincipal.closeAFramePrincipal();
			System.exit(0);
		}
	}
	
	private void initComponents(){
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		painel = new JPanel();
		painel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.LIGHT_GRAY));
		menuUsuario = new JPanel();
		menuUsuario.setPreferredSize(new Dimension(224, 0));
		menuUsuario.setLayout(new BorderLayout());
		menuUsuario.setBorder(BorderFactory.createMatteBorder(10, 13, 0, 13, new Color(243,243,243)));
		painelTopo = new JPanel();
		painelTopo.setLayout(null);
		painelTopo.setPreferredSize(new Dimension(0, 60));
		painelBusca = new JPanel();
		painelBusca.setBorder(new LineBorder(Color.LIGHT_GRAY));
		painelBusca.setLayout(null);
		painelBusca.setBackground(Color.white);
		painelBusca.setBounds(0,0,197,25);
		busca = new JTextField();
		busca.setBounds(2,1,174,23);
		busca.setFont(new Font("Tahoma", Font.PLAIN, 12));
		busca.setBorder(null);
		labelBusca = new JLabel(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/pesquisar.png")));
		labelBusca.setBounds(176, 4, 16, 16);
		labelBusca.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		painelBusca.add(busca);
		painelBusca.add(labelBusca);
		botaoNome = new JToggleButton("Nome");
		botaoNome.setBounds(101,27,97,22);
		botaoNome.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				buscar();
			}
		});
		botaoCodigo = new JToggleButton("Código");
		botaoCodigo.setBounds(0,27,97,22);
		botaoCodigo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				buscar();
			}
		});
		painelTopo.add(painelBusca);
		group = new ButtonGroup();
		group.add(botaoNome);
		group.add(botaoCodigo);
		botaoNome.setSelected(true);
		painelTopo.add(botaoNome);
		painelTopo.add(botaoCodigo);
		menuUsuario.add(painelTopo, BorderLayout.NORTH);
		painelLista = new JPanel(new BorderLayout(0,5));
		labelMenu = new JLabel("Menu Produto");
		labelMenu.setFont(new Font("Tahoma", Font.BOLD, 11));
		painelLista.add(labelMenu, BorderLayout.NORTH);
		DefaultListModel model = new DefaultListModel();
		model.addElement("Todos");
		
		list = new JList(model);
		list.addMouseMotionListener(new MouseMotionListener() {
		    @Override
		    public void mouseMoved(MouseEvent e) {
		        final int x = e.getX();
		        final int y = e.getY();
		        // only display a hand if the cursor is over the items
		        final Rectangle cellBounds = list.getCellBounds(0, list.getModel().getSize() - 1);
		        if (cellBounds != null && cellBounds.contains(x, y)) {
		        	mouseOver = list.locationToIndex(new Point(x,y));
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
			public void mouseExited(MouseEvent e){
				mouseOver = -1;
				list.repaint();
			}
		});
		
		list.setBackground(new Color(243, 243, 243));	
		list.setCellRenderer(new ImagensProduto());
		list.setSelectedIndex(0);
		list.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
		painelLista.setBorder(null);
		painelLista.add(Box.createRigidArea(new Dimension(7,0)), BorderLayout.WEST);
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
		splitPrincipal.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 0, Color.LIGHT_GRAY));
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
		painelDividirTopoBotoes.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(243,243,243)));
		JPanel pBlue = new JPanel();
		pBlue.setBorder(null);
		pBlue.setBackground(new Color(0,148,219));
		painelDividirTopoBotoes.setLeftComponent(pBlue);
		painelDividirTopoBotoes.setDividerLocation(5);
		painelDividirTopoBotoes.setDividerSize(0);
		painelBotoes = new JPanel();
		painelBotoes.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(180,180,180)));
		botaoCadastrar = new JButton("Cadastrar");
		botaoCadastrar.setIcon(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/add.png")));
		botaoCadastrar.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ADialogCadastrarProduto tela = new ADialogCadastrarProduto();
				boolean novo = tela.isNovoProduto();
				if(novo){
					buscar();
				}
			}
		});
		botaoAtualizar = new JButton("Atualizar");
		botaoAtualizar.setIcon(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/atualizar.png")));
		botaoAtualizar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				buscar();
			}
		});
		
		botaoEditar = new JButton("Editar");
		botaoEditar.setIcon(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/editar.png")));
		botaoEditar.setEnabled(false);
		botaoEditar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(botaoEditar.isEnabled()){
					ModeloTabelaProduto modelo = (ModeloTabelaProduto) painelTabela.getTabela().getModel();
	        		ADialogEditarProduto tela = ADialogEditarProduto.getADialogEditarProduto(modelo.getProduto(painelTabela.getTabela().getSelectedRow()));				
	        		boolean novo = tela.isNovoProduto();
					if(novo){
						buscar();
					}	
				}
			}
		});
		
		botaoRelatorio = new JButton("Relatório Quantidade");
		botaoRelatorio.setIcon(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/relatorio.png")));
		botaoRelatorio.setEnabled(false);
		botaoRelatorio.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (botaoRelatorio.isEnabled()) {
					ModeloTabelaProduto modelo = (ModeloTabelaProduto) painelTabela
							.getTabela().getModel();
					new ADialogRelatorioProduto(modelo.getProduto(painelTabela
							.getTabela().getSelectedRow()));
				}
			}
		});
		
		botaoRelatorioRanking = new JButton("Relatório Ranking");
		botaoRelatorioRanking.setIcon(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/relatorio.png")));
		botaoRelatorioRanking.setEnabled(false);
		botaoRelatorioRanking.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (botaoRelatorioRanking.isEnabled()) {
					ModeloTabelaProduto modelo = (ModeloTabelaProduto) painelTabela
							.getTabela().getModel();
					new ADialogRelatorioProdutoRanking(modelo.getProduto(painelTabela
							.getTabela().getSelectedRow()));
				}
			}
		});
		
		botaoRelatorioCidade = new JButton("Relatório Cidade");
		botaoRelatorioCidade.setIcon(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/relatorio.png")));
		botaoRelatorioCidade.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (botaoRelatorioCidade.isEnabled()) {
					new ADialogRelatorioProdutoCidade();
				}
			}
		});
		
		botaoRelatorioCliente = new JButton("Relatório Cliente");
		botaoRelatorioCliente.setIcon(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/relatorio.png")));
		botaoRelatorioCliente.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (botaoRelatorioCliente.isEnabled()) {
					new ADialogRelatorioProdutoCliente();
				}
			}
		});
		
		botaoVisualizar = new JButton("Visualizar");
		botaoVisualizar.setIcon(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/visualizar.png")));
		botaoVisualizar.setEnabled(false);
		botaoVisualizar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(botaoVisualizar.isEnabled()){
					ModeloTabelaProduto modelo = (ModeloTabelaProduto) painelTabela.getTabela().getModel();
	        		ADialogVisualizarProduto.getADialogVisualizarProduto(modelo.getProduto(painelTabela.getTabela().getSelectedRow()));				

				}
			}
		});
		
		painelBotoes.setLayout(new BoxLayout(painelBotoes, BoxLayout.X_AXIS));
		painelBotoes.add(Box.createRigidArea(new Dimension(5,0)));
		painelBotoes.add(botaoCadastrar);
		painelBotoes.add(Box.createRigidArea(new Dimension(5,0)));
		painelBotoes.add(botaoEditar);
		painelBotoes.add(Box.createRigidArea(new Dimension(5,0)));
		painelBotoes.add(botaoVisualizar);
		painelBotoes.add(Box.createRigidArea(new Dimension(5,0)));
		if(Amana.getUsuario().getTipoUsuario() == 1){
			painelBotoes.add(botaoRelatorio);
			painelBotoes.add(botaoRelatorioRanking);
			painelBotoes.add(Box.createRigidArea(new Dimension(5,0)));
			painelBotoes.add(botaoRelatorioCidade);
			painelBotoes.add(botaoRelatorioCliente);
		}
		painelBotoes.add(Box.createRigidArea(new Dimension(5,0)));
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

		add(Box.createRigidArea(new Dimension(24, 0)));
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
		
		//painelEstatistica = null;
		
		list.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				painelTabela.setPag(0);
				buscar();
			}
		});
		
		painelTabela.getTabela().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(painelTabela.getTabela().getSelectedRow() != -1
					&& !list.getSelectedValue().toString().equals("Lixeira")){
					botaoEditar.setEnabled(true);
					botaoVisualizar.setEnabled(true);
					botaoRelatorio.setEnabled(true);
					botaoRelatorioRanking.setEnabled(true);
				}
			}
		});
	}
	
	public void setModelo(ModeloTabelaProduto model) {
        painelTabela.setModeloTabela(model);
        TableColumnModel columnModel = painelTabela.getTabela().getColumnModel();
        painelTabela.getTabela().getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 11));
        painelTabela.getTabela().getTableHeader().setBorder(null);
        
        columnModel.getColumn(0).setHeaderValue("Código");
        columnModel.getColumn(0).setPreferredWidth(50);

        columnModel.getColumn(1).setHeaderValue("Nome");
        columnModel.getColumn(1).setPreferredWidth(150);

        columnModel.getColumn(2).setHeaderValue("Valor");
        columnModel.getColumn(2).setPreferredWidth(150);
        
        columnModel.getColumn(3).setHeaderValue("Itens");
        columnModel.getColumn(3).setPreferredWidth(150);
        
    }
	
	
	private class ImagensProduto extends JLabel implements ListCellRenderer{
	    
		private static final long serialVersionUID = 1L;

		public ImagensProduto(){
	      setOpaque(true);
	      setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(243, 243, 243)));
	    }
	 
	    public Component getListCellRendererComponent(
	      JList list, Object value, int index, boolean 
	      isSelected,boolean cellHasFocus){
	     
	      setText(value.toString());
	      if(value.toString() == "Todos"){
	    	  setIcon(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/produtovenda.png")));
	      }
	         
	      if(isSelected){
	    	  menuSelecionado.setText(value.toString());
	    	  setFont(new Font("Tahoma", Font.BOLD, 11));
	    	  setForeground(Color.white);
	    	  setBackground(new Color(0, 155, 225));
	      }
	      else{
	    	  if(index == mouseOver){
	    		  setFont(new Font("Tahoma", Font.PLAIN, 11));
	    		  setForeground(Color.white);
	    	      setBackground(new Color(0, 155, 225));
		    	    
	    	  }
	    	  else{
	    		setFont(new Font("Tahoma", Font.PLAIN, 11));
	    	  	setForeground(Color.black);
	    	  	setBackground(new Color(243,243,243));
	    	  }
	      }
	      return this;
	    }  
	}

	@Override
	public String getBusca() {
		return busca.getText();
	}

	@Override
	public String getFiltro() {
		if(botaoCodigo.isSelected()){
			return "produto.id";
		}
		else return "produto.nome";
		
	}

	@Override
	public int getStart() {
		return painelTabela.getPag();
	}

	@Override
	public int getLimite() {
		return painelTabela.getLimite();
	}

	@Override
	public ModeloTabelaProduto getModeloTabelaProduto() {
		return (ModeloTabelaProduto) painelTabela.getTabela().getModel();
	}

	@Override
	public void setModeloTabelaProduto(ModeloTabelaProduto modelo) {
		setModelo(modelo);
	}

}
