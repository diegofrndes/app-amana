package gui.swing.pessoa;

import gui.swing.componentes.APanelTopo;
import gui.swing.componentes.JCalendar;
import gui.swing.venda.AFrameReportPagamentosAmana;
import gui.swing.venda.AFrameReportPagamentosAmanaResumido;
import gui.swing.venda.AFrameReportVendasAmana;
import gui.swing.venda.AFrameReportVendasAmanaResumido;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import amana.Amana;

import modelo.Pessoa;
import dao.FabricaConexao;
public class ADialogRelatorioPessoa extends JDialog {

	private static final long serialVersionUID = 1L;
	private APanelTopo painelTopo;
	private JLabel lblInicial;
	private gui.swing.componentes.JCalendar dataInicial;
	private JLabel lblFinal;
	private JCalendar dataFinal;
	private JButton botaoRelatorio;
	private JComboBox comboTipo;
	private JComboBox comboOpcao;
	
	private JLabel labelTipo;
	private JLabel labelOpcao;
	
	private Pessoa pessoa;

	public ADialogRelatorioPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
		setTitle("Relatório de " + pessoa.getNome());
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				ClassLoader.getSystemResource("imagens/icone.png")));
		initComponents();
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				setVisible(false);
				removeAll();
				dispose();
			}
		});

		setResizable(false);
		setSize(316, 178);
		Dimension ds = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension dw = getSize();
		setLocation((ds.width - dw.width) / 2, (ds.height - dw.height) / 2);
		setModal(true);
		setVisible(true);

	}

	void initComponents() {
		JPanel painel = new JPanel();
		painel.setLayout(null);
		painel.setBorder(null);
		setLayout(new GridLayout());

		JPanel pBlue = new JPanel();
		pBlue.setBorder(null);
		pBlue.setBackground(new Color(0, 148, 219));
		JSplitPane painelDividirTopoBotoes = new JSplitPane(
				JSplitPane.VERTICAL_SPLIT);
		painelDividirTopoBotoes.setBounds(0, 52, 600, 62);
		painelDividirTopoBotoes.setBorder(BorderFactory.createMatteBorder(1, 0,
				1, 0, new Color(243, 243, 243)));
		painelDividirTopoBotoes.setLeftComponent(pBlue);
		painelDividirTopoBotoes.setDividerLocation(5);
		painelDividirTopoBotoes.setDividerSize(0);

		JPanel painelBotoes = new JPanel();
		painelBotoes.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
				new Color(180, 180, 180)));
		lblInicial = new JLabel("Data Inicial: ");
		lblInicial.setBounds(5, 5, 60, 20);
		dataInicial = new JCalendar(true);
		dataInicial.setBounds(65, 5, 90, 20);
		lblFinal = new JLabel("Data Final: ");
		lblFinal.setBounds(160, 5, 60, 20);
		dataFinal = new JCalendar(true);
		dataFinal.setBounds(215, 5, 90, 20);

		labelTipo = new JLabel("Tipo:");
		labelTipo.setBounds(5, 30, 90, 20);
		labelOpcao = new JLabel("Opção:");
		labelOpcao.setBounds(160, 30, 90, 20);
		comboTipo = new JComboBox();
		if(Amana.getUsuario().getTipoUsuario() == 1){
			comboTipo.addItem("VENDAS");
			comboTipo.addItem("PAGAMENTOS");
			comboTipo.addItem("FINANCEIRO");
			comboTipo.addItem("PRODUTOS");
			comboTipo.addItem("EM_ABERTO");	
		}
		else if (Amana.getUsuario().getTipoUsuario() == 2){
			comboTipo.addItem("PRODUTOS");	
			//Amana 4.0: Alteração para permitir que o usuario do escritorio tenha permissao
			//ao relatorio em aberto dos clientes.
			comboTipo.addItem("EM_ABERTO");	
		}
		comboTipo.setBounds(65, 30, 90, 20);
		comboOpcao = new JComboBox();
		comboOpcao.addItem("DETALHADO");
		comboOpcao.setBounds(215, 30, 90, 20);
	
		// comboOpcao.setEnabled(false);
		botaoRelatorio = new JButton("Gerar Relatório");
		botaoRelatorio.setBounds(96, 120, 119, 20);
		botaoRelatorio.setIcon(new ImageIcon(ClassLoader
				.getSystemResource("imagens/16x16/relatorio.png")));
		botaoRelatorio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					if(comboTipo.getSelectedItem().equals("VENDAS")){
						new AFrameReportPessoaVendas(pessoa.getId(), dataInicial
								.getText() + " 00:00:01", dataFinal
								.getText() + " 23:59:59");
		
					}
					else if(comboTipo.getSelectedItem().equals("PAGAMENTOS")){
						new AFrameReportPessoaPagamentos(pessoa.getId(), dataInicial
								.getText() + " 00:00:01", dataFinal
								.getText() + " 23:59:59");
		
					}
					else if(comboTipo.getSelectedItem().equals("FINANCEIRO")){
						new AFrameReportPessoaFinanceiro(pessoa.getId(), dataInicial
								.getText() + " 00:00:01", dataFinal
								.getText() + " 23:59:59");
		
					}
					else if(comboTipo.getSelectedItem().equals("PRODUTOS")){
						new AFrameReportPessoaProdutos(pessoa.getId(), dataInicial
								.getText() + " 00:00:01", dataFinal
								.getText() + " 23:59:59");
		
					}
					else if(comboTipo.getSelectedItem().equals("EM_ABERTO")){
						new AFrameReportPessoaVendasAberto(pessoa.getId(), dataInicial
								.getText() + " 00:00:01", dataFinal
								.getText() + " 23:59:59");
		
					}
					setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					removeAll();
					dispose();
					setVisible(false);
				} catch (Exception e) {
					e.printStackTrace();
					setVisible(false);
					removeAll();
					dispose();
					JOptionPane.showMessageDialog(null,
							"Erro ao tentar gerar relatório.", FabricaConexao.getApelido(),
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		painelBotoes.setLayout(null);
		painelBotoes.add(lblInicial);
		painelBotoes.add(dataInicial);
		painelBotoes.add(lblFinal);
		painelBotoes.add(dataFinal);
		painelBotoes.add(labelTipo);
		painelBotoes.add(comboTipo);
		painelBotoes.add(labelOpcao);
		painelBotoes.add(comboOpcao);
		
		painel.add(botaoRelatorio);

		painelDividirTopoBotoes.setRightComponent(painelBotoes);

		painel.add(painelDividirTopoBotoes);

		painelTopo = new APanelTopo("Cliente", "Relatório ",
				"imagens/32x32/relatorio.png");
		painelTopo.setBounds(0, 0, 310, 52);
		painel.add(painelTopo);
		add(painel);
	}

}
