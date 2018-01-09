package gui.swing.venda;

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
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import dao.FabricaDao;
import modelo.Pessoa;
import modelo.Venda;
import modelo.VendaExcluida;
import amana.Amana;
import apresentador.venda.ApresentadorPanelVenda;

import espectador.venda.EspectadorPanelVenda;
import gui.swing.componentes.ATable;
import gui.swing.documentos.AmanaFieldDocument;
import gui.swing.inicial.AFramePrincipal;
import gui.swing.pessoa.ADialogVisualizarPessoaFisica;
import dao.FabricaConexao;
public class APanelVenda extends JPanel implements EspectadorPanelVenda {

	private static final long serialVersionUID = 1L;

	private JPanel painel;
	private JPanel menuVenda;
	private JLabel labelMenu;
	private JLabel labelBusca;
	private JPanel painelTopo;
	private JPanel painelBusca;
	private JPanel painelLista;
	private JSplitPane painelBox;
	private JSplitPane painelPrincipal;
	private JSplitPane splitPrincipal;
	private JToggleButton botaoNumero;
	private JToggleButton botaoCliente;
	private JToggleButton botaoData;
	private ButtonGroup group;
	private JList list;
	private JLabel menuSelecionado;
	private JSplitPane painelDividirBotaoTabela;
	private JPanel painelBotoes;
	private ATable painelTabela;
	
	private JSplitPane painelDividirTopoPrincipal;
	private JSplitPane painelDividirTopoBotoes;
	
	private JButton botaoCadastrar;
	private JButton botaoPagamento;
	private JButton botaoCancelarPagamento;
	private JButton botaoDesconto;
	private JButton botaoNota;
	private JButton botaoAtualizar;
	private JButton botaoExcluir;
	private JButton botaoRelatorio;
	private JButton botaoMotivo;
	
	private JTextField busca;
	private ApresentadorPanelVenda apresentador;
	private int mouseOver = -1;
	
	public APanelVenda() {
		initComponents();
		apresentador = new ApresentadorPanelVenda(this);
		painelTabela.getTabela().setDefaultRenderer(Object.class, new VendaCellRenderer());
		buscar();
	}
	
	public void buscar(){
		try{
			botaoPagamento.setEnabled(false);
			botaoDesconto.setEnabled(false);
			botaoCancelarPagamento.setEnabled(false);
			botaoNota.setEnabled(false);
			botaoExcluir.setEnabled(false);
			botaoMotivo.setEnabled(false);
			
			if(list.getSelectedValue().toString().equals("Todas")){
				if(Amana.getUsuario().getTipoUsuario() == 1){
					botaoExcluir.setVisible(true);
					botaoMotivo.setVisible(false);
				}
				if(painelDividirBotaoTabela.getRightComponent() != painelTabela){
					painelDividirBotaoTabela.setRightComponent(painelTabela);
					painelDividirBotaoTabela.setDividerLocation(40);
					painelDividirBotaoTabela.setDividerSize(0);
				}
				apresentador.procurarTodas();		
			}
			else if(list.getSelectedValue().toString().equals("Em Aberto")){
				if(Amana.getUsuario().getTipoUsuario() == 1){
					botaoExcluir.setVisible(true);
					botaoMotivo.setVisible(false);
				}
				if(painelDividirBotaoTabela.getRightComponent() != painelTabela){
					painelDividirBotaoTabela.setRightComponent(painelTabela);
					painelDividirBotaoTabela.setDividerLocation(40);
					painelDividirBotaoTabela.setDividerSize(0);
				}
				apresentador.procurarEmAberto();		
			}
			else if(list.getSelectedValue().toString().equals("Finalizadas")){
				if(Amana.getUsuario().getTipoUsuario() == 1){
					botaoExcluir.setVisible(true);
					botaoMotivo.setVisible(false);
				}
				if(painelDividirBotaoTabela.getRightComponent() != painelTabela){
					painelDividirBotaoTabela.setRightComponent(painelTabela);
					painelDividirBotaoTabela.setDividerLocation(40);
					painelDividirBotaoTabela.setDividerSize(0);
				}
				apresentador.procurarFinalizadas();		
			}
			else if(list.getSelectedValue().toString().equals("Lixeira")){
				if(Amana.getUsuario().getTipoUsuario() == 1){
					botaoExcluir.setVisible(false);
					botaoMotivo.setVisible(true);
				}
				if(painelDividirBotaoTabela.getRightComponent() != painelTabela){
					painelDividirBotaoTabela.setRightComponent(painelTabela);
					painelDividirBotaoTabela.setDividerLocation(40);
					painelDividirBotaoTabela.setDividerSize(0);
				}
				apresentador.procurarLixeira();		
			}
			
		} catch(NullPointerException ex){
			//ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Não foi possível conectar ao servidor.\nO programa será finalizado.", FabricaConexao.getApelido(), JOptionPane.ERROR_MESSAGE);
			AFramePrincipal.closeAFramePrincipal();
			System.exit(0);
		}
	}
	
	private void initComponents(){
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		painel = new JPanel();
		painel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.LIGHT_GRAY));
		menuVenda = new JPanel();
		menuVenda.setPreferredSize(new Dimension(224, 0));
		menuVenda.setLayout(new BorderLayout());
		menuVenda.setBorder(BorderFactory.createMatteBorder(10, 13, 0, 13, new Color(243,243,243)));
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
		botaoNumero = new JToggleButton("Número");
		botaoNumero.setBounds(0,27,64,22);
		botaoNumero.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				buscar();
			}
		});
		botaoCliente = new JToggleButton("Cliente");
		botaoCliente.setBounds(67,27,64,22);
		botaoCliente.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				buscar();
			}
		});
		botaoData = new JToggleButton("Data");
		botaoData.setBounds(134,27,64,22);
		botaoData.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				buscar();
			}
		});
		painelTopo.add(painelBusca);
		group = new ButtonGroup();
		group.add(botaoData);
		group.add(botaoCliente);
		group.add(botaoNumero);
		botaoCliente.setSelected(true);
		painelTopo.add(botaoData);
		painelTopo.add(botaoCliente);
		painelTopo.add(botaoNumero);
		menuVenda.add(painelTopo, BorderLayout.NORTH);
		painelLista = new JPanel(new BorderLayout(0,5));
		labelMenu = new JLabel("Menu Venda");
		labelMenu.setFont(new Font("Tahoma", Font.BOLD, 11));
		painelLista.add(labelMenu, BorderLayout.NORTH);
		DefaultListModel model = new DefaultListModel();
		model.addElement("Todas");
		model.addElement("Em Aberto");
		model.addElement("Finalizadas");
		if(Amana.getUsuario().getTipoUsuario() == 1)
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
		list.setCellRenderer(new ImagensVenda());
		list.setSelectedIndex(0);
		list.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
		painelLista.setBorder(null);
		painelLista.add(Box.createRigidArea(new Dimension(7,0)), BorderLayout.WEST);
		painelLista.add(list, BorderLayout.CENTER);
		menuVenda.add(painelLista, BorderLayout.CENTER);
				
		painelBox = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		painelBox.setBorder(null);
		menuSelecionado = new JLabel("Todas", SwingConstants.CENTER);
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
				ADialogCadastrarVenda tela = ADialogCadastrarVenda.getADialogCadastrarVenda();
				boolean isNova = tela.isNovaVenda();
				if(isNova){
					buscar();
				}
			}
		});
		botaoRelatorio = new JButton("Relatório");
		botaoRelatorio.setIcon(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/relatorio.png")));
		botaoRelatorio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new ADialogRelatorioAmana();
			}
		});
		
		botaoDesconto = new JButton("Desconto");
		botaoDesconto.setIcon(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/desconto.png")));
		botaoDesconto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ModeloTabelaVenda modelo = (ModeloTabelaVenda) painelTabela.getTabela().getModel();
				Venda venda = modelo.getVenda(painelTabela.getTabela().getSelectedRow());
				ADialogDesconto tela = new ADialogDesconto(venda);
				boolean novoDesconto = tela.getNovoDesconto();
				if(novoDesconto){
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
		botaoPagamento = new JButton("Efetuar Pagamento");
		botaoPagamento.setIcon(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/pagamento.png")));
		botaoPagamento.setEnabled(false);
		botaoPagamento.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(botaoPagamento.isEnabled()){
					ModeloTabelaVenda modelo = (ModeloTabelaVenda) painelTabela.getTabela().getModel();
					Venda venda = modelo.getVenda(painelTabela.getTabela().getSelectedRow());
					ADialogPagamento tela = new ADialogPagamento(venda.getId());
					boolean novoPagamento = tela.isNovoPagamento();
					if(novoPagamento){
						buscar();
					}
				}
			}
		});
		
		botaoCancelarPagamento = new JButton("Cancelar Pagamento");
		botaoCancelarPagamento.setIcon(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/cancelar_pagamento.png")));
		botaoCancelarPagamento.setEnabled(false);
		botaoCancelarPagamento.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(botaoCancelarPagamento.isEnabled()){
					ModeloTabelaVenda modelo = (ModeloTabelaVenda) painelTabela.getTabela().getModel();
					Venda venda = modelo.getVenda(painelTabela.getTabela().getSelectedRow());
					ADialogCancelarPagamento tela = new ADialogCancelarPagamento(venda);
					boolean novoPagamento = tela.isNovoPagamento();
					if(novoPagamento){
						buscar();
					}
				}
			}
		});
		
		botaoExcluir = new JButton("Excluir Venda");
		botaoExcluir.setIcon(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/excluir.png")));
		botaoExcluir.setEnabled(false);
		botaoExcluir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(botaoExcluir.isEnabled()){
					ModeloTabelaVenda modelo = (ModeloTabelaVenda) painelTabela.getTabela().getModel();
					Venda venda = modelo.getVenda(painelTabela.getTabela().getSelectedRow());					
					String motivo = (String)JOptionPane.showInputDialog(
		                    null,
		                    "Digite o motivo da exclusão:\n",
		                    "Excluir a venda " + Integer.toString(venda.getId()) + " do sistema?",
		                    JOptionPane.PLAIN_MESSAGE,
		                    null,
		                    null,
		                    "");

	                if (motivo != null) {
	                	if(motivo.length() > 99){
	                		JOptionPane.showMessageDialog(null, "Não foi possível excluir a venda. Motivo muito grande (máximo 100 caracteres).", FabricaConexao.getApelido(), JOptionPane.ERROR_MESSAGE);
	                	}
	                	else {
	                		try{
	                			VendaExcluida vendaExcluida = new VendaExcluida(venda, motivo, Amana.getUsuario().getLogin());
	                			boolean res = FabricaDao.getVendaDao().lixeira(vendaExcluida);
	                			System.out.println(res);
	                			if(res){
	                				JOptionPane.showMessageDialog(null, "Venda número " + Integer.toString(venda.getId()) + " excluída com sucesso.", "Excluir Venda", JOptionPane.INFORMATION_MESSAGE);      
	                				buscar();
	                			}
	                			else JOptionPane.showMessageDialog(null, "Não foi possível excluir a venda " + Integer.toString(venda.getId()) + ".", FabricaConexao.getApelido(), JOptionPane.ERROR_MESSAGE);
							
	                		} catch (Exception ex){
	                			ex.printStackTrace();
	                			JOptionPane.showMessageDialog(null, "Não foi possível excluir a venda.", FabricaConexao.getApelido(), JOptionPane.ERROR_MESSAGE);
	                		}
	                	}
	                }
				}
			}
		});
		
		botaoNota = new JButton("Nota de Conferência");
		botaoNota.setIcon(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/nota.png")));
		botaoNota.setEnabled(false);
		botaoNota.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(botaoNota.isEnabled()){
					try{
						ModeloTabelaVenda venda = (ModeloTabelaVenda) painelTabela.getTabela().getModel();
					    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					    Venda v = venda.getVenda(painelTabela.getTabela().getSelectedRow());
					    Object[] options = {"Sim", "Não"};
		                int j = JOptionPane.showOptionDialog(null, "Deseja Exibir o Débito Total do Cliente na Nota?", FabricaConexao.getApelido(), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		                if (j == JOptionPane.YES_OPTION) {
		                	BigDecimal debito = FabricaDao.getPessoaDao().debito(v.getCliente());
							if(debito.signum() == -1)
								new AFrameReportVenda(v.getId(), true, debito.abs());
							else{
								new AFrameReportVenda(v.getId(), true, new BigDecimal(0.00f));
							}
		                }
		                else {
							new AFrameReportVenda(v.getId(), false, new BigDecimal(0.00f));
		                }
					    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					    
					} catch (NullPointerException ex){
						JOptionPane.showMessageDialog(null, "Não foi possível conectar ao servidor.\nO programa será finalizado.", FabricaConexao.getApelido(), JOptionPane.ERROR_MESSAGE);
						AFramePrincipal.closeAFramePrincipal();
						System.exit(0);		
					}
				}
			}
		});
		
		botaoMotivo = new JButton("Motivo Exclusão");
		botaoMotivo.setIcon(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/motivo.png")));
		botaoMotivo.setEnabled(false);
		botaoMotivo.setVisible(false);
		botaoMotivo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(botaoMotivo.isEnabled()){
					ModeloTabelaVenda modelo = (ModeloTabelaVenda) painelTabela.getTabela().getModel();
					Venda venda = modelo.getVenda(painelTabela.getTabela().getSelectedRow());	
					VendaExcluida vendaExcluida = FabricaDao.getVendaDao().procurarMotivo(venda);
					if(vendaExcluida == null)
            			JOptionPane.showMessageDialog(null, "Nenhum motivo encontrado para exclusão dessa venda.", FabricaConexao.getApelido(), JOptionPane.ERROR_MESSAGE);
					else{
						ADialogVendaExcluida.getADialogVendaExcluida(vendaExcluida);
					}
				}
			}
		});
		
		painelBotoes.setLayout(new BoxLayout(painelBotoes, BoxLayout.X_AXIS));
		painelBotoes.add(Box.createRigidArea(new Dimension(5,0)));
		painelBotoes.add(botaoCadastrar);
		painelBotoes.add(Box.createRigidArea(new Dimension(5,0)));
		painelBotoes.add(botaoPagamento);
		if(Amana.getUsuario().getTipoUsuario() == 1){
			painelBotoes.add(Box.createRigidArea(new Dimension(5,0)));
			painelBotoes.add(botaoCancelarPagamento);
		}
		painelBotoes.add(Box.createRigidArea(new Dimension(5,0)));
		painelBotoes.add(botaoDesconto);
		painelBotoes.add(Box.createRigidArea(new Dimension(5,0)));
		painelBotoes.add(botaoNota);
		if(Amana.getUsuario().getTipoUsuario() == 1){
			painelBotoes.add(Box.createRigidArea(new Dimension(5,0)));
			painelBotoes.add(botaoRelatorio);
			painelBotoes.add(Box.createRigidArea(new Dimension(5,0)));
			painelBotoes.add(botaoExcluir);
			painelBotoes.add(botaoMotivo);
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
		painel.add(menuVenda, BorderLayout.WEST);
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
				if(painelTabela.getTabela().getSelectedRow() != -1){
					if(!list.getSelectedValue().toString().equals("Lixeira")){
						botaoExcluir.setEnabled(true);
						botaoNota.setEnabled(true);		
						botaoCancelarPagamento.setEnabled(true);		
					}
					else{
						botaoMotivo.setEnabled(true);
					}
					ModeloTabelaVenda modelo = (ModeloTabelaVenda) painelTabela.getTabela().getModel();
					Venda venda = modelo.getVenda(painelTabela.getTabela().getSelectedRow());
					BigDecimal a = venda.getValor();
                	BigDecimal b = venda.getValorRecebido();
                	BigDecimal c = a.subtract(b);
					if(c.signum() == 1 && !list.getSelectedValue().toString().equals("Lixeira")){
						botaoPagamento.setEnabled(true);
					}
					else{
						botaoPagamento.setEnabled(false);	
					}
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		            try {
		            	java.util.Date data = df.parse(venda.getData().substring(0,10));
		            	Calendar cal = Calendar.getInstance();
		                Calendar cal2 = Calendar.getInstance();
		                cal2.setTime(data);
		                int dia1 = cal.get(Calendar.DAY_OF_MONTH);
		                int dia2 = cal2.get(Calendar.DAY_OF_MONTH);
		                int mes1 = cal.get(Calendar.MONTH);
		                int mes2 = cal2.get(Calendar.MONTH);
		                int ano1 = cal.get(Calendar.YEAR);
		                int ano2 = cal2.get(Calendar.YEAR);
		                
		                if(((dia1 == dia2 && mes1 == mes2 && ano1 == ano2) || Amana.getUsuario().getTipoUsuario() == 1) && !list.getSelectedValue().toString().equals("Lixeira")){
		                	botaoDesconto.setEnabled(true);
		                }		            
		                else{
							botaoDesconto.setEnabled(false);
						}
		            } catch (ParseException ex) {
		                botaoDesconto.setEnabled(false);
		            }
				}
				
			}
		});
		
		if(Amana.getUsuario().getTipoUsuario() != 1){
			if(Amana.getUsuario().getTipoUsuario() == 2)
				painelTabela.setDisablePag(true);
			else painelTabela.setDisablePag(false); 
		}
	}	
	
	public void setModelo(ModeloTabelaVenda model) {
        painelTabela.setModeloTabela(model);
        TableColumnModel columnModel = painelTabela.getTabela().getColumnModel();
        painelTabela.getTabela().getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 11));
        painelTabela.getTabela().getTableHeader().setBorder(null);
        
        columnModel.getColumn(0).setHeaderValue("Número");
        columnModel.getColumn(0).setPreferredWidth(50);

        columnModel.getColumn(1).setHeaderValue("Cód. Cliente");
        columnModel.getColumn(1).setPreferredWidth(50);

        columnModel.getColumn(2).setHeaderValue("Cliente");
        columnModel.getColumn(2).setPreferredWidth(150);

        columnModel.getColumn(3).setHeaderValue("Data");
        columnModel.getColumn(3).setPreferredWidth(75);
        
        columnModel.getColumn(4).setHeaderValue("Hora");
        columnModel.getColumn(4).setPreferredWidth(75);
        
        columnModel.getColumn(5).setHeaderValue("SubTotal (R$)");
        columnModel.getColumn(5).setPreferredWidth(75);
        
        columnModel.getColumn(6).setHeaderValue("Desconto (R$)");
        columnModel.getColumn(6).setPreferredWidth(75);
        
        columnModel.getColumn(7).setHeaderValue("Total (R$)");
        columnModel.getColumn(7).setPreferredWidth(75);
           
        columnModel.getColumn(8).setHeaderValue("Valor Recebido(R$)");
        columnModel.getColumn(8).setPreferredWidth(75);
        
        columnModel.getColumn(9).setHeaderValue("Débito (R$)");
        columnModel.getColumn(9).setPreferredWidth(75);
        
    }

	
	private class ImagensVenda extends JLabel implements ListCellRenderer{
	    
		private static final long serialVersionUID = 1L;

		public ImagensVenda() {
		  setOpaque(true);
	      setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(243, 243, 243)));
	    }
	 
	    public Component getListCellRendererComponent(
	      JList list, Object value, int index, boolean 
	      isSelected,boolean cellHasFocus){
	     
	      setText(value.toString());
	      if(value.toString() == "Todas"){
	    	  setIcon(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/vendas.png")));
	      }
	      else if(value.toString() == "Em Aberto"){
	    	  setIcon(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/vendas_abertas.png")));
	      }
	      else if(value.toString() == "Finalizadas"){
	    	  setIcon(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/vendas_ok.png")));
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

	//@Override
	public String getBusca() {
		if(botaoData.isSelected()){
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");                	
			String ret = "";
			DateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
			try {
				ret = dateFormat.format(dateFormat2.parse(busca.getText()));
			} catch (ParseException e) {
			}
			return ret;
		}
			
		else return busca.getText();
	}

	//@Override
	public String getFiltro() {
		if(botaoNumero.isSelected()){
			return "venda.id";
		}
		else if(botaoCliente.isSelected()){
			return "pessoa.nome";
		}
		else return "venda.data";
	}

	//@Override
	public int getStart() {
		return painelTabela.getPag();
	}

	//@Override
	public int getLimite() {
		if(Amana.getUsuario().getTipoUsuario() != 1){
			return 20;
		}
		else return painelTabela.getLimite();
	}

	@Override
	public ModeloTabelaVenda getModeloTabelaVenda() {
		return (ModeloTabelaVenda) painelTabela.getTabela().getModel();
	}

	@Override
	public void setModeloTabelaVenda(ModeloTabelaVenda modelo) {
		setModelo(modelo);
	}

}

