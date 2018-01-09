package dao;

import java.util.List;

import modelo.PessoaFisica;

/**
 *
 * @author Diego Fernandes Carlos da Costa <diego@engi42.com.br>
 */
public interface PessoaFisicaDao {

    public int salvar(PessoaFisica pessoa);
    public boolean editar(PessoaFisica pessoa);
    public List<PessoaFisica> procurarTodos(String busca, String filtro, int pagina, int limite);
    public List<PessoaFisica> procurarClientes(String busca, String filtro, int pagina, int limite);
    public List<PessoaFisica> procurarFornecedores(String busca, String filtro, int pagina, int limite);
    public List<PessoaFisica> procurarTransportadores(String busca, String filtro, int pagina, int limite);
}
