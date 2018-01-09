package apresentador.usuario;

import modelo.Usuario;

import espectador.usuario.EspectadorPanelUsuario;

import dao.FabricaDao;

import gui.swing.usuario.ModeloTabelaUsuario;

public class ApresentadorPanelUsuario {
	
	private EspectadorPanelUsuario espectador;
	
	public ApresentadorPanelUsuario(EspectadorPanelUsuario espectador)
	{
		this.espectador = espectador;
	}
	
    public void procurarTodos(){
    	espectador.setModeloTabelaUsuario(
    			new ModeloTabelaUsuario(FabricaDao.getUsuarioDao().procurarTodos(
    					espectador.getBusca(), espectador.getFiltro(), espectador.getStart(), espectador.getLimite())));
    }
    
    public void procurarAdministracao(){
     	espectador.setModeloTabelaUsuario(
    			new ModeloTabelaUsuario(FabricaDao.getUsuarioDao().procurarAdministracao(
    					espectador.getBusca(), espectador.getFiltro(), espectador.getStart(), espectador.getLimite())));
    }
    
    public void procurarEscritorio(){
     	espectador.setModeloTabelaUsuario(
    			new ModeloTabelaUsuario(FabricaDao.getUsuarioDao().procurarEscritorio(
    					espectador.getBusca(), espectador.getFiltro(), espectador.getStart(), espectador.getLimite())));
   
    }
    
    public void procurarProducao(){
     	espectador.setModeloTabelaUsuario(
    			new ModeloTabelaUsuario(FabricaDao.getUsuarioDao().procurarProducao(
    					espectador.getBusca(), espectador.getFiltro(), espectador.getStart(), espectador.getLimite())));
    }
    
    public void procurarLixeira(){
     	espectador.setModeloTabelaUsuario(
     			new ModeloTabelaUsuario(FabricaDao.getUsuarioDao().procurarLixeira(
    					espectador.getBusca(), espectador.getFiltro(), espectador.getStart(), espectador.getLimite())));
    }
    
    public boolean desativar(Usuario usuario){
    	return FabricaDao.getUsuarioDao().desativar(usuario);
    }
    
    public int[] estatistica(){
    	return FabricaDao.getUsuarioDao().estatistica();
    }
    
}
