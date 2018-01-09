package dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Classe para criar e gerenciar a conexao com o banco de dados
 * 
 * @author Diego Fernandes Carlos da Costa
 * @version 1.0
 * @since AMANA 1.0
 */
public class FabricaConexao {
	private static String URL;
	private static String ip;
	private static String porta;
	private static String banco;
	private static String nome;
	private static String apelido;

	private static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
	// 1 - administrador
	// 2 - escritorio
	// 3 - produção
	private static int estrategia = 0;

	/**
	 * Metodo para carregar a URL de conexao a partir do arquivo de configuracao
	 * 
	 * @throws IOException
	 */
	public static void carregarUrl() throws IOException {
		Properties config = new Properties();
		//para testar no eclipse
		InputStream inputStream = FabricaConexao.class.getClassLoader().getResourceAsStream("config/config.ini");
		config.load(inputStream);
		
		// Para colocar o sistema no ar
		//config.load(new FileInputStream("./config/config.ini"));
		setIp(config.getProperty("ip"));
		setPorta(config.getProperty("porta"));
		setBanco(config.getProperty("banco"));
		setNome(config.getProperty("nome"));
		setApelido(config.getProperty("apelido"));
		URL = "jdbc:mysql://" + getIp() + ":" + getPorta() + "/" + getBanco();
	}

	/**
	 * Retorna a conexão com o banco de acordo com as configurações da url.
	 * 
	 * @return A conexão com o banco.
	 * @throws ConexaoException
	 */
	public static Connection getConexao() {
		try {
			Class.forName(DRIVER_CLASS);
			switch (estrategia) {
			case 1:
				return DriverManager.getConnection(URL, "admin_amana",
						"adminamana@engi42");
			case 2:
				return DriverManager.getConnection(URL, "admin_amana",
						"adminamana@engi42");
			case 3:
				return DriverManager.getConnection(URL, "admin_amana",
						"adminamana@engi42");
			default:
				return DriverManager.getConnection(URL, "login_amana",
						"loginamana");
			}
		} catch (SQLException ex) {
			return null;
		} catch (ClassNotFoundException ex) {
			return null;
		}
	}

	public static String getIp() {
		return ip;
	}

	public static void setIp(String ip) {
		FabricaConexao.ip = ip;
	}

	public static String getPorta() {
		return porta;
	}

	public static void setPorta(String porta) {
		FabricaConexao.porta = porta;
	}

	public static void setEstrategia(int estrategia) {
		FabricaConexao.estrategia = estrategia;
	}

	public static String getUserBackup() {
		return "admin_amana";
	}

	
	public static String getPasswordBackup() {
		return "adminamana@engi42";
	}

	public static String getBanco() {
		return banco;
	}

	private static void setBanco(String banco) {
		FabricaConexao.banco = banco;
	}

	public static String getNome() {
		return nome;
	}

	public static void setNome(String nome) {
		FabricaConexao.nome = nome;
	}

	public static String getApelido() {
		return apelido;
	}

	public static void setApelido(String apelido) {
		FabricaConexao.apelido = apelido;
	}

}
