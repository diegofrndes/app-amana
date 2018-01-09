package dao;

import java.util.List;

import modelo.Equipamento;
import modelo.ManutencaoEquipamento;

public interface EquipamentoDao {
	public int salvar(Equipamento equipamento);
	public boolean editar(Equipamento equipamento);
	public int adicionarManutencao(Equipamento equipamento, ManutencaoEquipamento manutencao);
	//public boolean existeEquipamento(String numeroserie);
    public List<Equipamento> procurarTodos(String busca, String filtro, int pagina, int limite);
    public List<Equipamento> procurarManutencaoDia(String busca, String filtro, int pagina, int limite);
    public List<Equipamento> procurarManutencao30Dias(String busca, String filtro, int pagina, int limite);
    public List<Equipamento> procurarManutencaoAtrasada(String busca, String filtro, int pagina, int limite);
    public List<ManutencaoEquipamento> procurarManutencao(Equipamento equipamento, int pagina, int limite);
    public boolean desativar(Equipamento equipamento);
    public List<Equipamento> procurarLixeira(String busca, String filtro, int pagina, int limite);
    public boolean removerManutencao(ManutencaoEquipamento manutencao);
    
}
