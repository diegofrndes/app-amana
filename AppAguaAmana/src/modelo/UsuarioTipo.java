package modelo;

public class UsuarioTipo {
	private final static int tipo[] = {1,2,3};
	private final static String label[] = {"Administração",	"Escritório", "Produção"};
	
	public static String getTipoUsuario(int tp){
		int i;
		for(i = 0; i < tipo.length; i++){
			if(tipo[i] == tp)
				break;
		}
		return label[i];
	}
	
	public static int getQuantidade(){
		return tipo.length;
	}
}
