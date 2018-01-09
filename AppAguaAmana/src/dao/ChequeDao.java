package dao;

import java.util.List;

import modelo.Cheque;

public interface ChequeDao {
	public int salvar(Cheque cheque);
    public List<Cheque> procurarTodos(String busca, String filtro, int pagina, int limite);
    public List<Cheque> procurarPendentes(String busca, String filtro, int pagina, int limite);
    public List<Cheque> procurarVencidos(String busca, String filtro, int pagina, int limite);
    public List<Cheque> procurarFinalizados(String busca, String filtro, int pagina, int limite);


}
