package amana;

import gui.swing.usuario.AFrameLogin;

import java.io.IOException;
import javax.swing.JOptionPane;

import dao.FabricaConexao;

import modelo.Usuario;


public class Amana {
	private static Usuario usuario;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
            FabricaConexao.carregarUrl();
            //AFrameLogin.getAFrameLogin();
            //PrincipalTela.getPrincipalTela();
        } catch (IOException ex) {
        	ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Não foi possível ler o arquivo de configuração.", FabricaConexao.getApelido(), JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        } catch (NullPointerException ex) {
        	ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Não foi possível encontrar arquivos de inicialização.", FabricaConexao.getApelido(), JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
	}
	
	public static Usuario getUsuario() {
		return usuario;
	}
	
	public static void setUsuario(Usuario usuario) {
		Amana.usuario = usuario;
	}

}
