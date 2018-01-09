package apresentador.usuario;

import espectador.usuario.EspectadorVisualizarUsuario;
import modelo.Usuario;

public class ApresentadorVisualizarUsuario {
	
	public ApresentadorVisualizarUsuario(EspectadorVisualizarUsuario espectador, Usuario modelo){
		espectador.setNome(modelo.getNome());
		espectador.setCpf(modelo.getCpf());
		espectador.setRg(modelo.getRg());
		espectador.setUfRg(modelo.getUfRg());
		espectador.setEndereco(modelo.getEndereco());
		espectador.setTipoUsuario(modelo.getTipoUsuario());
	}

}
