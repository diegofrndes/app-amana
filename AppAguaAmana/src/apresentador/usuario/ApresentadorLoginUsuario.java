package apresentador.usuario;

import dao.FabricaDao;
import modelo.Usuario;
import espectador.usuario.EspectadorLoginUsuario;

public class ApresentadorLoginUsuario {
    private EspectadorLoginUsuario espectador;
    
    public ApresentadorLoginUsuario(EspectadorLoginUsuario espectador){
        this.espectador = espectador;
    }
    
    public Usuario fazerLogin(){
        return FabricaDao.getUsuarioDao().fazerLogin(espectador.getUsuario(), espectador.getSenha());
    }
}
