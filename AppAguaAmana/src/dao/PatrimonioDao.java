package dao;

import java.math.BigDecimal;
import java.util.List;

import modelo.Patrimonio;

public interface PatrimonioDao {
	public int salvar(Patrimonio patrimonio);
	public boolean editar(Patrimonio patrimonio);
	public boolean desativar(Patrimonio patrimonio);
    public List<Patrimonio> procurarTodos(String busca, String filtro, int pagina, int limite);
    public List<Patrimonio> procurarLixeira(String busca, String filtro, int pagina, int limite);
    public BigDecimal estimativaPatrimonio();
    
}
