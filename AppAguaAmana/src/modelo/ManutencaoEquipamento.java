package modelo;

public class ManutencaoEquipamento {
	private String data;
	private String obs;
	// 0 - Manuten��o preventiva
	// 1 - Manuten��o corretiva
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
