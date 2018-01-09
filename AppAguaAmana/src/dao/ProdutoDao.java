package dao;

import java.util.List;

import modelo.Produto;

public interface ProdutoDao {
	public int salvar(Produto produto);
	public boolean editar(Produto produto);
	public List<Produto> procurarTodos(String busca, String filtro, int pagina, int limite);
	public Produto procurarProduto(String id);

}
