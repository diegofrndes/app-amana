package gui.swing.inicial;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import dao.FabricaDao;

import modelo.Equipamento;
import modelo.Item;

import amana.Amana;
import dao.FabricaConexao;

public class AStatusBar extends JPanel {
    
	private static final long serialVersionUID = 1L;
	private JLabel statusLabelEquipamento;
    private JLabel statusLabelItem;
    private JLabel statusLabelUser;
    private JLabel statusData;
    private JLabel statusHora; 
	private JLabel separador;
	private JLabel separador2;
	private JLabel separador3;

    
    public AStatusBar() {
        super();
        setPreferredSize(new Dimension(0, 25));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(null);
        statusLabelItem = new JLabel("Limite Crítico");
        statusLabelItem.setFont(new Font("Tahoma", 1, 11));
        statusLabelItem.setForeground(Color.white);
        atualizarItem();
        
        statusLabelEquipamento = new JLabel("Manutenção De Equipamento");
        statusLabelEquipamento.setFont(new Font("Tahoma", 1, 11));
        statusLabelEquipamento.setForeground(Color.white);
        atualizarEquipamento();
        
        statusLabelUser = new JLabel(Amana.getUsuario().getLogin());
        statusLabelUser.setIcon(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/usuario.png")));
        statusLabelUser.setFont(new Font("Tahoma", 1, 11));
        statusLabelUser.setForeground(Color.white);
        
        statusData = new JLabel("00/00/0000");
        statusData.setFont(new Font("Tahoma", 1, 11));
        statusData.setForeground(Color.white);
        statusData.setIcon(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/calendario.png")));
		statusHora = new JLabel("00:00:00");
        statusHora.setFont(new Font("Tahoma", 1, 11));
        statusHora.setForeground(Color.white);
        statusHora.setIcon(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/relogio.png")));
		atualizarData();
        
        separador = new JLabel(" | ");
	    separador.setFont(new Font("Tahoma", Font.BOLD, 11));
	    separador.setForeground(new Color(170, 170, 170));
	 
	    separador2 = new JLabel(" | ");
	    separador2.setFont(new Font("Tahoma", Font.BOLD, 11));
	    separador2.setForeground(new Color(170, 170, 170));
	    
	    separador3 = new JLabel(" | ");
	    separador3.setFont(new Font("Tahoma", Font.BOLD, 11));
	    separador3.setForeground(new Color(170, 170, 170));
	    
        add(Box.createRigidArea(new Dimension(22,0)));
        add(statusLabelUser);
        add(separador);
		add(statusData);
		add(separador3);
		add(statusHora);
		
        add(Box.createHorizontalGlue());
        add(statusLabelItem);
        add(separador2);
		add(statusLabelEquipamento);
		add(Box.createRigidArea(new Dimension(10, 0)));

        
    }
    
    public void atualizarData(){
    	try{
    		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            DateFormat timeFormat = new SimpleDateFormat("kk:mm");
            Date date = new Date();
            statusData.setText(dateFormat.format(date));
		    statusHora.setText(timeFormat.format(date));
		} catch(Exception e) {
    		
    	}
    }
    
    public void atualizarEquipamento(){
    	try{
    		List<Equipamento> lista = FabricaDao.getEquipamentoDao().procurarManutencaoAtrasada("", "nome", 0, 1);
    		//System.out.println(lista.size());
    		if(lista != null){
    			if(lista.size() > 0){
    				statusLabelEquipamento.setIcon(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/atrasado.png")));
    			} else {
    				statusLabelEquipamento.setIcon(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/ok.png")));	
        		}
    		}
    	} catch(NullPointerException e) {
    		e.printStackTrace();
    		JOptionPane.showMessageDialog(null, "Não foi possível conectar ao servidor.\nO programa será finalizado.", FabricaConexao.getApelido(), JOptionPane.ERROR_MESSAGE);
    		AFramePrincipal.closeAFramePrincipal();
    		System.exit(0);
    	}
    }
    
        
    public void atualizarItem(){
    	try{
    		List<Item> lista = FabricaDao.getItemDao().procurarLimiteCriticoUltrapassado("", "item.nome", 0, 1);
    		if(lista != null){
    			if(lista.size() > 0){
    				statusLabelItem.setIcon(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/limitecriticonaook.png")));
    			} else {
    				statusLabelItem.setIcon(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/limitecriticook.png")));	
        		}
    		}
    	} catch(NullPointerException e) {
    		e.printStackTrace();
    		JOptionPane.showMessageDialog(null, "Não foi possível conectar ao servidor.\nO programa será finalizado.", FabricaConexao.getApelido(), JOptionPane.ERROR_MESSAGE);
    		AFramePrincipal.closeAFramePrincipal();
    		System.exit(0);
    	}
    }
    
    /*
    public void atualizarEquipamento(){
    	try{
    		List<Equipamento> lista = FabricaDao.getEquipamentoDao().procurarManutencaoAtrasada("", "nome", 0, 1);
    		//System.out.println(lista.size());
    		if(lista != null){
    			if(lista.size() > 0){
    				statusLabelEquipamento.setIcon(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/atrasado.png")));
    				//statusLabelEquipamento.setToolTipText("Equipamentos com manutenção atrasada");
    			} else {
    				statusLabelEquipamento.setIcon(new ImageIcon(ClassLoader.getSystemResource("imagens/16x16/ok.png")));	
    				//statusLabelEquipamento.setToolTipText("Equipamentos em dia com manutenção");
        		}
    		}
    	} catch(NullPointerException e) {
    		JOptionPane.showMessageDialog(null, "Não foi possível conectar ao servidor.\nO programa será finalizado.", FabricaConexao.getApelido(), JOptionPane.ERROR_MESSAGE);
    		PrincipalTela.FecharPrincipalTela();
        	System.exit(0);
    	}
    }
    */
    
    public void paint(Graphics g) {
		//super.paint(g);
		Graphics2D g2d = (Graphics2D)g;

	    Color startColor = new Color(86, 86, 86);
	    Color endColor = new Color(58, 58, 58);

	    // A non-cyclic gradient
	    GradientPaint gradient = new GradientPaint(0, 0, startColor, 0, this.getHeight(), endColor);
	    g2d.setPaint(gradient);
	    g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
	    this.paintComponents(g);
	}
    
}
