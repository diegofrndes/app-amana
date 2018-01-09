package dao;

import java.util.List;

import modelo.Pagamento;
import modelo.Venda;
import modelo.VendaExcluida;

public interface VendaDao {
	public int salvar(Venda venda);
	public boolean excluir(Venda venda);
	public boolean lixeira(VendaExcluida vendaExcluida);
	public boolean atualizarDesconto(Venda venda);
	public int efetuarPagamento(Venda venda);
	public boolean cancelarPagamento(Pagamento pagamento);
	public List<Pagamento> procurarPagamentos(Venda venda);
	public List<Venda> procurarTodas(String busca, String filtro, int pagina, int limite);
	public List<Venda> procurarEmAberto(String busca, String filtro, int pagina, int limite);
	public List<Venda> procurarFinalizadas(String busca, String filtro, int pagina, int limite);
	public List<Venda> procurarLixeira(String busca, String filtro, int pagina, int limite);
	public VendaExcluida procurarMotivo(Venda venda);
	
}
