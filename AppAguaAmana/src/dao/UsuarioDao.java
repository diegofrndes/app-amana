package dao;

import java.util.List;

import modelo.Usuario;

/**
 * Interface DAO para relacionar o modelo Usuario com a tabela usuario
 *
 * @author Diego Fernandes Carlos da Costa
 * @version 1.0
 * @since AMANA 1.0
 */
public interface UsuarioDao {
	
	/**
	 * Metodo para salvar usuario no banco de dados
	 * 
	 * @param usuario O Usuario que sera salvo
	 * @return id O id do usuario salvo (-1 se nao conseguir salvar)
	 */
	public int salvar(Usuario usuario);
    public boolean desativar(Usuario usuario);
    public boolean editar(Usuario usuario);
    public boolean existeUsuario(String usuario);
    public List<Usuario> procurarTodos(String busca, String filtro, int pagina, int limite);
    public List<Usuario> procurarAdministracao(String busca, String filtro, int pagina, int limite);
    public List<Usuario> procurarEscritorio(String busca, String filtro, int pagina, int limite);
    public List<Usuario> procurarProducao(String busca, String filtro, int pagina, int limite);
    public List<Usuario> procurarLixeira(String busca, String filtro, int pagina, int limite);
    public Usuario fazerLogin(String login, String senha);
    public int[] estatistica();
}
