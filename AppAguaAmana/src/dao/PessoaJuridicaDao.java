package dao;

import java.util.List;

import modelo.PessoaJuridica;

/**
 *
 * @author Diego Fernandes Carlos da Costa <diego@engi42.com.br>
 */
public interface PessoaJuridicaDao {

    public int salvar(PessoaJuridica pessoa);
    public boolean editar(PessoaJuridica pessoa);
    public List<PessoaJuridica> procurarTodos(String busca, String filtro, int pagina, int limite);
    public List<PessoaJuridica> procurarClientes(String busca, String filtro, int pagina, int limite);
    public List<PessoaJuridica> procurarFornecedores(String busca, String filtro, int pagina, int limite);
    public List<PessoaJuridica> procurarTransportadores(String busca, String filtro, int pagina, int limite);

}
