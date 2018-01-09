package apresentador.usuario;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

import modelo.Usuario;
import dao.FabricaDao;
import espectador.usuario.EspectadorCadastrarUsuario;

public class ApresentadorCadastrarUsuario {
	private EspectadorCadastrarUsuario espectador;
	
	public ApresentadorCadastrarUsuario(EspectadorCadastrarUsuario espectador){
		this.espectador = espectador;
	}
	
	public boolean cadastrar(){
		try {
    		String senha = this.espectador.getSenha();
        	MessageDigest digest;
    		digest = MessageDigest.getInstance("MD5");
    		digest.update(senha.getBytes());
            Base64 encoder = new Base64();
            senha = encoder.encodeAsString(digest.digest());
            Usuario usuario = new Usuario(-1, this.espectador.getLogin(), senha);
    		usuario.setNome(this.espectador.getNome());
    		usuario.setCpf(this.espectador.getCpf());
    		usuario.setEndereco(this.espectador.getEndereco());
    		usuario.setRg(this.espectador.getRg());
    		usuario.setUfRg(this.espectador.getUfRg());     
    		usuario.setTipoUsuario(this.espectador.getTipoUsuario());
            return (FabricaDao.getUsuarioDao().salvar(usuario) != -1);
    	} catch (NoSuchAlgorithmException e) {
			return false;
    	}
	}
	
	public boolean existeUsuario(){
        return (FabricaDao.getUsuarioDao().existeUsuario(this.espectador.getLogin()));
    }
}
