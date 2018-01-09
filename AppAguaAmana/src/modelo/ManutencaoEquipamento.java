package modelo;

public class ManutencaoEquipamento {
	private String data;
	private String obs;
	// 0 - Manutenção preventiva
	// 1 - Manutenção corretiva
	private boolean tipo;
	private int id;
	
	public ManutencaoEquipamento(int id, boolean tipo){
		this.id = id;
		this.tipo = tipo;
	}
	
	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public int getId() {
		return id;
	}

	public boolean getTipo(){
		return this.tipo;
	}

}
