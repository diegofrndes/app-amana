package dao;

import java.util.List;

import modelo.EntradaSaidaItem;
import modelo.Item;

public interface ItemDao {
	public boolean existeItem(String nome);
	public int salvar(Item item);
	public boolean editar(Item item);
	public int entradaSaida(EntradaSaidaItem entradasaida);
	public List<Item> procurarTodos(String busca, String filtro, int pagina, int limite);
	public List<Item> procurarLimiteCriticoOk(String busca, String filtro, int pagina, int limite);
	public List<Item> procurarLimiteCriticoUltrapassado(String busca, String filtro, int pagina, int limite);
	public List<EntradaSaidaItem> procurarMovimentacaoEstoque(Item item, String dataInicial, String dataFinal);
	public int quantidade(Item item, String data);
	public String motivo(Item item, String data);
	
}
