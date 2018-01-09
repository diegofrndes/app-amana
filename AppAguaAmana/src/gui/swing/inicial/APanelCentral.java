package gui.swing.inicial;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import gui.swing.cheque.APanelCheque;
import gui.swing.componentes.ALabelMenu;
import gui.swing.equipamento.APanelEquipamento;
import gui.swing.patrimonio.APanelPatrimonio;
import gui.swing.pessoa.APanelPessoa;
import gui.swing.produto.APanelItemProduto;
import gui.swing.usuario.APanelUsuario;
import gui.swing.venda.APanelVenda;
import amana.Amana;

public class APanelCentral extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private JSplitPane jSplitPane;
	private JPanel painelCima;
	private JPanel painelBaixo;
	private ALabelMenu[] menu;
	private String[] labels;
	private String[] icones;
	
	
	public APanelCentral(){
		
		painelCima = new JPanel();
		painelCima.setPreferredSize(new Dimension(0, 25));
		painelCima.setBackground(new Color(58,58,58));
		painelCima.setLayout(null);
		if(Amana.getUsuario().getTipoUsuario() == 1){
			labels = new String[]{"Pessoa", "Produto", "Venda", "Equipamento", "Patrimônio", "Usuário"};
			icones = new String[]{"pessoa.png", 
				"produto.png", "vendas.png", "equipamento.png", 
				"patrimonio.png", "usuario.png"};
		}
		else if(Amana.getUsuario().getTipoUsuario() == 2){
			labels = new String[]{"Pessoa", "Produto", "Venda", "Equipamento"};
			icones = new String[]{"pessoa.png", "produto.png", "vendas.png", "equipamento.png"};				
		}
		else if(Amana.getUsuario().getTipoUsuario() == 3){
			labels = new String[]{"Produto", "Venda", "Equipamento"};
			icones = new String[]{"produto.png", "vendas.png", "equipamento.png"};				
		}
		menu = new ALabelMenu[labels.length];
		int posicao = 25;
		for(int i = 0; i < menu.length; i++){
			menu[i] = new ALabelMenu(labels[i], JLabel.CENTER);
			menu[i].setOpaque(true);
			menu[i].setBounds(posicao, 0, 110, 28);
			menu[i].setIcon(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/" + icones[i])));
			posicao = posicao + menu[i].getWidth() + 2;
		    menu[i].addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseClicked(MouseEvent arg0) {
					for(int j = 0; j < menu.length; j++){
						if(((JLabel) arg0.getSource()).equals(menu[j])){
								menu[j].setSelected(true);
								menu[j].repaint();
								if(menu[j].getText().equals("Pessoa")){
				     				remove(painelBaixo);
									painelBaixo.removeAll();
									painelBaixo = null;
									painelBaixo = new APanelPessoa();
									add(painelBaixo, BorderLayout.CENTER);
				     			}
								else if(menu[j].getText().equals("Produto")){
				     				remove(painelBaixo);
									painelBaixo.removeAll();
									painelBaixo = null;
									painelBaixo = new APanelItemProduto();
									add(painelBaixo, BorderLayout.CENTER);
				     			}
								else if(menu[j].getText().equals("Venda")){
				     				remove(painelBaixo);
									painelBaixo.removeAll();
									painelBaixo = null;
									painelBaixo = new APanelVenda();
									add(painelBaixo, BorderLayout.CENTER);
				     			}
								else if(menu[j].getText().equals("Usuário")){
									remove(painelBaixo);
									painelBaixo.removeAll();
									painelBaixo = null;
									painelBaixo = new APanelUsuario();
									add(painelBaixo, BorderLayout.CENTER);
								}
				     			else if(menu[j].getText().equals("Equipamento")){
									remove(painelBaixo);
									painelBaixo.removeAll();
									painelBaixo = null;
									painelBaixo = new APanelEquipamento();
									add(painelBaixo, BorderLayout.CENTER);
								}
				     			else if(menu[j].getText().equals("Patrimônio")){
				     	            remove(painelBaixo);
									painelBaixo.removeAll();
									painelBaixo = null;
									painelBaixo = new APanelPatrimonio();
									add(painelBaixo, BorderLayout.CENTER);
								}
						}				
						else{
							menu[j].setSelected(false);
							menu[j].repaint();
						}
						
					}
					validate();
				}
			});
			painelCima.add(menu[i]);
		}
		
		painelBaixo = new JPanel();
		painelBaixo.setBackground(new Color(243,243,243));
        JLabel lbImagem = new JLabel("", SwingConstants.CENTER);
        lbImagem.setIcon(new javax.swing.ImageIcon(ClassLoader.getSystemResource("imagens/logomarca.png")));
        lbImagem.setBounds(16, 10, 164, 108);
        painelBaixo.setLayout(new BorderLayout());
        painelBaixo.add(lbImagem, BorderLayout.CENTER);
        /*
		jSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		jSplitPane.setBorder(null);
		jSplitPane.setDividerLocation(25);
		jSplitPane.setDividerSize(0);
		jSplitPane.setLeftComponent(painelCima);
		jSplitPane.setRightComponent(painelBaixo);
		*/
		setLayout(new BorderLayout());
		add(painelCima, BorderLayout.NORTH);
		add(painelBaixo, BorderLayout.CENTER);
		//add(jSplitPane);
	
	}
}
