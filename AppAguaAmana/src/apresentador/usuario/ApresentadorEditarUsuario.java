package apresentador.usuario;

import java.security.NoSuchAlgorithmException;

import modelo.Usuario;
import dao.FabricaDao;
import espectador.usuario.EspectadorEditarUsuario;

public class ApresentadorEditarUsuario {
    private Usuario modelo;
    private EspectadorEditarUsuario espectador;
    
    public ApresentadorEditarUsuario(EspectadorEditarUsuario espectador, Usuario modelo){
        this.espectador = espectador;
        this.modelo = modelo;
        espectador.setNome(modelo.getNome());
		espectador.setCpf(modelo.getCpf());
		espectador.setRg(modelo.getRg());
		espectador.setUfRg(modelo.getUfRg());
		espectador.setEndereco(modelo.getEndereco());
		espectador.setTipoUsuario(modelo.getTipoUsuario());
	}
    
    public boolean editar(){
        try {
        	modelo.setNome(this.espectador.getNome());
            modelo.setCpf(this.espectador.getCpf());
            modelo.setEndereco(this.espectador.getEndereco());
            modelo.setRg(this.espectador.getRg());
            modelo.setUfRg(this.espectador.getUfRg());     
            modelo.setTipoUsuario(this.espectador.getTipoUsuario());
            modelo.setSenha(this.espectador.getSenha());
            return (FabricaDao.getUsuarioDao().editar(modelo));
        } catch (NoSuchAlgorithmException e) {
			return false;
		}
    }
    
}
