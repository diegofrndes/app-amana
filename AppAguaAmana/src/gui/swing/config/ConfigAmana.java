package gui.swing.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigAmana {
	private static int limiteLinhas = 20;
	private static int tempoAtualizacao = 60;
	
	public static void carregarPropriedades() {
        Properties config = new Properties();
        String arquivo = "config/amana.properties";
        try {
			config.load(new FileInputStream(arquivo));
		    setLimiteLinhas(Integer.parseInt(config.getProperty("linhas")));
		    setTempoAtualizacao(Integer.parseInt(config.getProperty("tempo")));
	    } catch (FileNotFoundException e) {
        } catch (IOException e) {
		}
    }

	public static void setLimiteLinhas(int lim){
		ConfigAmana.limiteLinhas = lim;	
	}
	
	public static int getLimiteLinhas(){
		return limiteLinhas;
	}
	
	public static int getTempoAtualizacao() {
		return tempoAtualizacao;
	}

	public static void setTempoAtualizacao(int tempoAtualizacao) {
		ConfigAmana.tempoAtualizacao = tempoAtualizacao;
	}   
}
