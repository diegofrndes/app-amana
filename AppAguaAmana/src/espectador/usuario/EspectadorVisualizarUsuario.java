package espectador.usuario;

import modelo.Endereco;

public interface EspectadorVisualizarUsuario {

	public void setNome(String nome);

    public void setEndereco(Endereco endereco);

    public void setRg(String rg);

    public void setUfRg(String ufrg);
    
    public void setCpf(String cpf);
    
    public void setTipoUsuario(int tipo);
    
}
