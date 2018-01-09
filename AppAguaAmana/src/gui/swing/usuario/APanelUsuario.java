package gui.swing.usuario;

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
import apresentador.usuario.ApresentadorPanelUsuario;
import espectador.usuario.EspectadorPanelUsuario;
import gui.swing.componentes.ATable;
import gui.swing.documentos.AmanaFieldDocument;
import gui.swing.inicial.AFramePrincipal;
import dao.FabricaConexao;
public class APanelUsuario extends JPanel implements EspectadorPanelUsuario {
	
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
	private JToggleButton botaoCpf;
	private JToggleButton botaoNome;
	private JToggleButton botaoUsuario;
	private ButtonGroup group;
	private JList list;
	private JLabel menuSelecionado;
	private JSplitPane painelDividirBotaoTabela;
	private JPanel painelBotoes;
	private ATable painelTabela;
	private APanelEstatisticaUsuario painelEstatistica;
	private JSplitPane painelDividirTopoPrincipal;
	private JSplitPane painelDividirTopoBotoes;
	
	private JButton botaoCadastrar;
	private JButton botaoExcluir;
	private JButton botaoEditar;
	private JButton botaoVisualizar;
	private JButton botaoAtualizar;
	
	private JTextField busca;
	private ApresentadorPanelUsuario apresentador;
	private int mouseOver = -1;
	
	public APanelUsuario(){
		initComponents();
		apresentador = new ApresentadorPanelUsuario(this);
		painelTabela.getTabela().setDefaultRenderer(Object.class, new UsuarioCellRenderer());
		buscar();
	}
	
	public void buscar(){
		try{
			botaoExcluir.setEnabled(false);
			botaoEditar.setEnabled(false);
			botaoVisualizar.setEnabled(false);
			if(list.getSelectedValue().toString().equals("Todos")){
				if(painelEstatistica != null){
					painelEstatistica.removeAll();
					painelEstatistica = null;
				}
				
				if(painelDividirBotaoTabela.getRightComponent() != painelTabela){
					painelDividirBotaoTabela.setRightComponent(painelTabela);
					painelDividirBotaoTabela.setDividerLocation(40);
					painelDividirBotaoTabela.setDividerSize(0);
				}
				apresentador.procurarTodos();		
			}
			else if(list.getSelectedValue().toString().equals("Administração")){
				if(painelEstatistica != null){
					painelEstatistica.removeAll();
					painelEstatistica = null;
				}
				
				if(painelDividirBotaoTabela.getRightComponent() != painelTabela){
					painelDividirBotaoTabela.setRightComponent(painelTabela);
					painelDividirBotaoTabela.setDividerLocation(40);
					painelDividirBotaoTabela.setDividerSize(0);
				}
				apresentador.procurarAdministracao();
			}
			else if(list.getSelectedValue().toString().equals("Escritório")){
				if(painelEstatistica != null){
					painelEstatistica.removeAll();
					painelEstatistica = null;
				}
				
				if(painelDividirBotaoTabela.getRightComponent() != painelTabela){
					painelDividirBotaoTabela.setRightComponent(painelTabela);
					painelDividirBotaoTabela.setDividerLocation(40);
					painelDividirBotaoTabela.setDividerSize(0);
				}
				apresentador.procurarEscritorio();
			}
			else if(list.getSelectedValue().toString().equals("Produção")){
				if(painelEstatistica != null){
					painelEstatistica.removeAll();
					painelEstatistica = null;
				}
				
				if(painelDividirBotaoTabela.getRightComponent() != painelTabela){
					painelDividirBotaoTabela.setRightComponent(painelTabela);
					painelDividirBotaoTabela.setDividerLocation(40);
					painelDividirBotaoTabela.setDividerSize(0);
				}
				apresentador.procurarProducao();
			}
			else if(list.getSelectedValue().toString().equals("Lixeira")){
				if(painelEstatistica != null){
					painelEstatistica.removeAll();
					painelEstatistica = null;
				}
				
				if(painelDividirBotaoTabela.getRightComponent() != painelTabela){
					painelDividirBotaoTabela.setRightComponent(painelTabela);
					painelDividirBotaoTabela.setDividerLocation(40);
					painelDividirBotaoTabela.setDividerSize(0);
				}
				apresentador.procurarLixeira();
			}
			else if(list.getSelectedValue().toString().equals("Estatísticas")){
				if(painelEstatistica == null){
					painelEstatistica = new APanelEstatisticaUsuario();
					painelEstatistica.setChart(apresentador.estatistica());
					painelEstatistica.validate();
				}
				if(painelDividirBotaoTabela.getRightComponent() != painelEstatistica){
					painelDividirBotaoTabela.setRightComponent(painelEstatistica);
					painelDividirBotaoTabela.setDividerLocation(40);
					painelDividirBotaoTabela.setDividerSize(0);
				}
				else{
					painelEstatistica.setChart(apresentador.estatistica());
					painelEstatistica.validate();	
				}
			}
		} catch(NullPointerException ex){
			ex.printStackTrace();
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
		busca.setDocument(new AmanaFieldDocument(150));
		labelBusca = new JLabel(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/pesquisar.png")));
		labelBusca.setBounds(176, 4, 16, 16);
		labelBusca.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		painelBusca.add(busca);
		painelBusca.add(labelBusca);
		botaoCpf = new JToggleButton("CPF");
		botaoCpf.setBounds(134,27,64,22);
		botaoCpf.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				buscar();
			}
		});
		botaoNome = new JToggleButton("Nome");
		botaoNome.setBounds(0,27,64,22);
		botaoNome.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				buscar();
			}
		});
		botaoUsuario = new JToggleButton("Usuário");
		botaoUsuario.setBounds(67,27,64,22);
		botaoUsuario.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				buscar();
			}
		});
		painelTopo.add(painelBusca);
		group = new ButtonGroup();
		group.add(botaoCpf);
		group.add(botaoNome);
		group.add(botaoUsuario);
		botaoNome.setSelected(true);
		painelTopo.add(botaoCpf);
		painelTopo.add(botaoNome);
		painelTopo.add(botaoUsuario);
		menuUsuario.add(painelTopo, BorderLayout.NORTH);
		painelLista = new JPanel(new BorderLayout(0,5));
		labelMenu = new JLabel("Menu Usuário");
		labelMenu.setFont(new Font("Tahoma", Font.BOLD, 11));
		painelLista.add(labelMenu, BorderLayout.NORTH);
		DefaultListModel model = new DefaultListModel();
		model.addElement("Todos");
		model.addElement("Administração");
		model.addElement("Escritório");
		model.addElement("Produção");
		model.addElement("Estatísticas");
		model.addElement("Lixeira");
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
		list.setCellRenderer(new ImagensUsuario());
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
				ADialogCadastrarUsuario dialog = ADialogCadastrarUsuario.getADialogCadastrarUsuario();
				boolean novoUsuario = dialog.isNovoUsuario();
				if(novoUsuario){
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
		botaoExcluir = new JButton("Excluir");
		botaoExcluir.setEnabled(false);
		botaoExcluir.setIcon(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/excluir.png")));
		botaoExcluir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(botaoExcluir.isEnabled()){
					Object[] options = {"Sim", "Não"};
	                int j = JOptionPane.showOptionDialog(null, "Deseja excluir o usuário?", FabricaConexao.getApelido(), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
	                if (j == JOptionPane.YES_OPTION) {
	                	try{
	                		ModeloTabelaUsuario modelo = (ModeloTabelaUsuario) painelTabela.getTabela().getModel();
	                		if(!Amana.getUsuario().equals(modelo.getUsuario(painelTabela.getTabela().getSelectedRow()))
	                			&& apresentador.desativar(modelo.getUsuario(painelTabela.getTabela().getSelectedRow()))){
	                			JOptionPane.showMessageDialog(null, "Usuário excluído com sucesso.", "Excluir Usuário", JOptionPane.INFORMATION_MESSAGE);      
	                			buscar();
	                		}
	                		else{
	                			JOptionPane.showMessageDialog(null, "Não foi possível excluir o usuário.", "Excluir Usuário", JOptionPane.ERROR_MESSAGE );
	                		}
	                	}
	                	catch (Exception e) {
	                		JOptionPane.showMessageDialog(null, "Não foi possível excluir o usuário.", "Excluir Usuário", JOptionPane.ERROR_MESSAGE );
	                	}
	                }
				}
			}
		});
		botaoEditar = new JButton("Editar");
		botaoEditar.setIcon(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/editar.png")));
		botaoEditar.setEnabled(false);
		botaoEditar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(botaoEditar.isEnabled()){
					ModeloTabelaUsuario modelo = (ModeloTabelaUsuario) painelTabela.getTabela().getModel();
					ADialogEditarUsuario tela = ADialogEditarUsuario.getADialogEditarUsuario(modelo.getUsuario(painelTabela.getTabela().getSelectedRow())); 
    				boolean editarUsuario = tela.isNovoUsuario();
    				if(editarUsuario){
    					buscar();
    				}
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
					ModeloTabelaUsuario modelo = (ModeloTabelaUsuario) painelTabela.getTabela().getModel();
            		ADialogVisualizarUsuario.getADialogVisualizarUsuario(modelo.getUsuario(painelTabela.getTabela().getSelectedRow())); 
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
		painelBotoes.add(botaoExcluir);
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
		
		painelEstatistica = null;
		
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
					botaoExcluir.setEnabled(true);
					botaoEditar.setEnabled(true);
					botaoVisualizar.setEnabled(true);		
				}
			}
		});
	}
	
	public void setModelo(ModeloTabelaUsuario model) {
        painelTabela.setModeloTabela(model);
        TableColumnModel columnModel = painelTabela.getTabela().getColumnModel();
        painelTabela.getTabela().getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 11));
        painelTabela.getTabela().getTableHeader().setBorder(null);
                
        columnModel.getColumn(0).setHeaderValue("Nome");
        columnModel.getColumn(0).setPreferredWidth(150);

        columnModel.getColumn(1).setHeaderValue("CPF");
        columnModel.getColumn(1).setPreferredWidth(150);

        columnModel.getColumn(2).setHeaderValue("Telefone");
        columnModel.getColumn(2).setPreferredWidth(150);
        
        columnModel.getColumn(3).setHeaderValue("Usuário");
        columnModel.getColumn(3).setPreferredWidth(150);
        
        columnModel.getColumn(4).setHeaderValue("Tipo");
        columnModel.getColumn(4).setPreferredWidth(150);
        
    }

	
	private class ImagensUsuario extends JLabel implements ListCellRenderer{
	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public ImagensUsuario(){
	      setOpaque(true);
	      setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(243, 243, 243)));
	    }
	 
	    public Component getListCellRendererComponent(
	      JList list, Object value, int index, boolean 
	      isSelected,boolean cellHasFocus){
	     
	      setText(value.toString());
	      if(value.toString() == "Todos"){
	    	  setIcon(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/usuario.png")));
	      }
	      else if(value.toString() == "Administração"){
	    	  setIcon(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/administracao.png")));
	      }
	      else if(value.toString() == "Produção"){
	    	  setIcon(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/producao.png")));
	      }
	      else if(value.toString() == "Escritório"){
	    	  setIcon(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/escritorio.png")));
	      }
	      else if(value.toString() == "Estatísticas"){
	    	  setIcon(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/estatisticas.png")));
	      }
	      else if(value.toString() == "Lixeira"){
	    	  setIcon(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/lixeira.png")));
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
		if(botaoCpf.isSelected()){
			String b = busca.getText();
			b = b.replace(".", "");
			b = b.replace("-", "");
			return b;
		}
		else return busca.getText();
	}

	@Override
	public String getFiltro() {
		if(botaoNome.isSelected()){
			return "nome";
		}
		else if(botaoCpf.isSelected()){
			return "cpf";
		}
		else return "login";
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
	public ModeloTabelaUsuario getModeloTabelaUsuario() {
		return (ModeloTabelaUsuario) painelTabela.getTabela().getModel();
	}

	@Override
	public void setModeloTabelaUsuario(ModeloTabelaUsuario modelo) {
		setModelo(modelo);
	}
	
}
