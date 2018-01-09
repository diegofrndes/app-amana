package dao.jdbc;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dao.FabricaConexao;
import dao.UsuarioDao;

import modelo.Endereco;
import modelo.Usuario;
import modelo.UsuarioTipo;

/**
 *
 * @author Diego Fernandes Carlos da Costa <diego@engi42.com.br>
 */
public class JDBCUsuarioDao implements UsuarioDao {

    @Override
    public int salvar(Usuario usuario) {
        String inserir = "INSERT INTO usuario"
                + "(nome,"
                + "uf,"
                + "cidade,"
                + "cep,"
                + "bairro,"
                + "rua,"
                + "numero,"
                + "ddd,"
                + "telefone,"
                + "ufrg,"
                + "rg,"
                + "cpf,"
                + "login,"
                + "senha,"
                + "tipo," +
                "flag)"
                + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,1)";
        Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int chave_gerada = -1;
        try {
            pstmt = conexao.prepareStatement(inserir, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, usuario.getNome());
            pstmt.setString(2, usuario.getEndereco().getUf());
            pstmt.setString(3, usuario.getEndereco().getCidade());
            pstmt.setString(4, usuario.getEndereco().getCep());
            pstmt.setString(5, usuario.getEndereco().getBairro());
            pstmt.setString(6, usuario.getEndereco().getRua());
            pstmt.setString(7, usuario.getEndereco().getNumero());
            pstmt.setString(8, usuario.getEndereco().getDdd());
            pstmt.setString(9, usuario.getEndereco().getTelefone());
            pstmt.setString(10, usuario.getUfRg());
            pstmt.setString(11, usuario.getRg());
            pstmt.setString(12, usuario.getCpf());
            pstmt.setString(13, usuario.getLogin());
            pstmt.setString(14, usuario.getSenha());
            pstmt.setInt(15, usuario.getTipoUsuario());
            pstmt.execute();
            rs = pstmt.getGeneratedKeys();
            rs.first();
            chave_gerada = rs.getInt(1);
            return chave_gerada;
        } catch (SQLException ex) {
            return -1;
        } finally {
        	try {
                if (rs != null){
                    rs.close();
                }
            } catch (SQLException e) { /* ignored */}
            finally {
                try {
                    if (pstmt != null){
                        pstmt.close();
                    }
                } catch (SQLException e) { /* ignored */}
                finally {
                	try{
                		if( conexao != null ){
                			conexao.close();
                		}
                	} catch (SQLException e) { /* ignored */}
                }
            }
        }
    }

    @Override
	public boolean existeUsuario(String usuario) {
		Connection conexao = FabricaConexao.getConexao();
	    String encontrar = "SELECT * FROM USUARIO WHERE login = ?";
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
        try {
	    	pstmt = conexao.prepareStatement(encontrar);
	    	pstmt.setString(1, usuario);
	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	            return true;
	        }
	        return false;
	    } catch (SQLException ex) {
	    	return false;
	    } finally {
        	try {
                if (rs != null){
                    rs.close();
                }
            } catch (SQLException e) { /* ignored */}
            finally {
                try {
                    if (pstmt != null){
                        pstmt.close();
                    }
                } catch (SQLException e) { /* ignored */}
                finally {
                	try{
                		if( conexao != null ){
                			conexao.close();
                		}
                	} catch (SQLException e) { /* ignored */}
                }
            }
        }
	}
    
    @Override
	public boolean editar(Usuario usuario) {
        String update = "UPDATE usuario SET "
                + "nome = ?,"
                + "uf = ?,"
                + "cidade = ?,"
                + "cep = ?,"
                + "bairro = ?,"
                + "rua = ?,"
                + "numero = ?,"
                + "ddd = ?,"
                + "telefone = ?,"
                + "ufrg = ?,"
                + "rg = ?,"
                + "cpf = ?,"
                + "senha = ?,"
                + "tipo = ?" 
                + " WHERE id = " + Integer.toString(usuario.getId());
        Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        int linhasAfetadas = 0;
        try {
            pstmt = conexao.prepareStatement(update);
            pstmt.setString(1, usuario.getNome());
            pstmt.setString(2, usuario.getEndereco().getUf());
            pstmt.setString(3, usuario.getEndereco().getCidade());
            pstmt.setString(4, usuario.getEndereco().getCep());
            pstmt.setString(5, usuario.getEndereco().getBairro());
            pstmt.setString(6, usuario.getEndereco().getRua());
            pstmt.setString(7, usuario.getEndereco().getNumero());
            pstmt.setString(8, usuario.getEndereco().getDdd());
            pstmt.setString(9, usuario.getEndereco().getTelefone());
            pstmt.setString(10, usuario.getUfRg());
            pstmt.setString(11, usuario.getRg());
            pstmt.setString(12, usuario.getCpf());
            pstmt.setString(13, usuario.getSenha());
            pstmt.setInt(14, usuario.getTipoUsuario());
            linhasAfetadas = pstmt.executeUpdate();
            return (linhasAfetadas == 1);
        } catch (SQLException ex) {
        	ex.printStackTrace();
            return (linhasAfetadas == 1);
        } finally {
        	try {
        		if (pstmt != null){
        			pstmt.close();
                }
            } catch (SQLException e) { /* ignored */}
            finally {
            	try{
            		if( conexao != null ){
            			conexao.close();
                 	}
            	} catch (SQLException e) { /* ignored */}
            }
        }
	}
    
    @Override
    public boolean desativar(Usuario usuario) {
        Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        try {
            String update = "UPDATE usuario set flag = '0' WHERE id = ?";
            pstmt = conexao.prepareStatement(update);
            pstmt.setInt(1, usuario.getId());
            int linhas = pstmt.executeUpdate();
            return (linhas == 1);
        } catch (SQLException ex) {
            return false;
        }finally {
        	try {
        		if (pstmt != null){
        			pstmt.close();
                }
            } catch (SQLException e) { /* ignored */}
            finally {
            	try{
            		if( conexao != null ){
            			conexao.close();
                 	}
            	} catch (SQLException e) { /* ignored */}
            }
        }
    }
    
    @Override
    public Usuario fazerLogin(String login, String senha) {
        String encontrar = "SELECT * FROM USUARIO WHERE login = ? AND senha = ? AND flag = '1'";
        Usuario usuario = null;
        Connection conexao = FabricaConexao.getConexao();        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
        	pstmt = conexao.prepareStatement(encontrar);
            pstmt.setString(1, login);
            pstmt.setString(2, senha);
            rs = pstmt.executeQuery();
            while (rs.next()) {
            	usuario = new Usuario(rs.getInt("id"), rs.getString("login"), rs.getString("senha"));
                usuario.setCpf(rs.getString("cpf"));
               //usuario.setId(rs.getInt("id"));
                Endereco endereco = 
                new Endereco(rs.getString("bairro"),
                			 rs.getString("rua"),
                			 rs.getString("numero"),
                			 rs.getString("ddd"),
                			 rs.getString("telefone"),
                			 rs.getString("cep"),
                			 rs.getString("uf"),
                			 rs.getString("cidade"));
                usuario.setEndereco(endereco);
                usuario.setNome(rs.getString("nome"));
                usuario.setRg(rs.getString("rg"));
                usuario.setUfRg(rs.getString("ufrg"));
                usuario.setTipoUsuario(rs.getInt("tipo"));
            }
            return usuario;
        } catch (Exception ex) {
            ex.printStackTrace();
        	return null;
        }  finally {
        	try {
                if (rs != null){
                    rs.close();
                }
            } catch (SQLException e) { /* ignored */}
            finally {
                try {
                    if (pstmt != null){
                        pstmt.close();
                    }
                } catch (SQLException e) { /* ignored */}
                finally {
                	try{
                		if( conexao != null ){
                			conexao.close();
                		}
                	} catch (SQLException e) { /* ignored */}
                }
            }
        }
    }

	@Override
	public List<Usuario> procurarTodos(String busca, String filtro, int pagina,
			int limite) {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		String select = "SELECT * FROM USUARIO WHERE flag = '1' AND " + filtro + " like '" + busca + "%' " + "ORDER BY " + filtro + " LIMIT " + Integer.toString(pagina*limite) 
				+ "," + Integer.toString(limite);
		Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conexao.prepareStatement(select);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Usuario usuario = new Usuario(rs.getInt("id"), rs.getString("login"), rs.getString("senha"));
                usuario.setCpf(rs.getString("cpf"));
               //usuario.setId(rs.getInt("id"));
                Endereco endereco = 
                new Endereco(rs.getString("bairro"),
                			 rs.getString("rua"),
                			 rs.getString("numero"),
                			 rs.getString("ddd"),
                			 rs.getString("telefone"),
                			 rs.getString("cep"),
                			 rs.getString("uf"),
                			 rs.getString("cidade"));
                usuario.setEndereco(endereco);
                usuario.setNome(rs.getString("nome"));
                usuario.setRg(rs.getString("rg"));
                usuario.setUfRg(rs.getString("ufrg"));
                usuario.setTipoUsuario(rs.getInt("tipo"));
                usuarios.add(usuario);
            }
            return usuarios;
        } catch (SQLException ex) {
            return usuarios;
        }  finally {
        	try {
                if (rs != null){
                    rs.close();
                }
            } catch (SQLException e) { /* ignored */}
            finally {
                try {
                    if (pstmt != null){
                        pstmt.close();
                    }
                } catch (SQLException e) { /* ignored */}
                finally {
                	try{
                		if( conexao != null ){
                			conexao.close();
                		}
                	} catch (SQLException e) { /* ignored */}
                }
            }
        }
	}

	@Override
	public List<Usuario> procurarAdministracao(String busca, String filtro, int pagina,
			int limite) {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		String select = "SELECT * FROM USUARIO WHERE tipo = '1' AND flag = '1' AND " + filtro + " like '" + busca + "%' " + "ORDER BY " + filtro + " LIMIT " + Integer.toString(pagina*limite) 
				+ "," + Integer.toString(limite);
		Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conexao.prepareStatement(select);
            rs = pstmt.executeQuery();
            while (rs.next()) {
            	Usuario usuario = new Usuario(rs.getInt("id"), rs.getString("login"), rs.getString("senha"));
                usuario.setCpf(rs.getString("cpf"));
                //usuario.setId(rs.getInt("id"));
                Endereco endereco = 
                new Endereco(rs.getString("bairro"),
                			 rs.getString("rua"),
                			 rs.getString("numero"),
                			 rs.getString("ddd"),
                			 rs.getString("telefone"),
                			 rs.getString("cep"),
                			 rs.getString("uf"),
                			 rs.getString("cidade"));
                usuario.setEndereco(endereco);
                usuario.setNome(rs.getString("nome"));
                usuario.setRg(rs.getString("rg"));
                usuario.setUfRg(rs.getString("ufrg"));
                //usuario.setSenha(rs.getString("senha"));
                usuario.setTipoUsuario(rs.getInt("tipo"));
                usuarios.add(usuario);
            }
            return usuarios;
        } catch (SQLException ex) {
            return usuarios;
        }  finally {
        	try {
                if (rs != null){
                    rs.close();
                }
            } catch (SQLException e) { /* ignored */}
            finally {
                try {
                    if (pstmt != null){
                        pstmt.close();
                    }
                } catch (SQLException e) { /* ignored */}
                finally {
                	try{
                		if( conexao != null ){
                			conexao.close();
                		}
                	} catch (SQLException e) { /* ignored */}
                }
            }
        }
	}

	@Override
	public List<Usuario> procurarEscritorio(String busca, String filtro, int pagina,
			int limite) {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		String select = "SELECT * FROM USUARIO WHERE tipo = '2' AND flag = '1' AND " + filtro + " like '" + busca + "%' " + "ORDER BY " + filtro + " LIMIT " + Integer.toString(pagina*limite) 
				+ "," + Integer.toString(limite);
		Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conexao.prepareStatement(select);
            rs = pstmt.executeQuery();
            while (rs.next()) {
            	Usuario usuario = new Usuario(rs.getInt("id"), rs.getString("login"), rs.getString("senha"));
                usuario.setCpf(rs.getString("cpf"));
                //usuario.setId(rs.getInt("id"));
                Endereco endereco = 
                new Endereco(rs.getString("bairro"),
                			 rs.getString("rua"),
                			 rs.getString("numero"),
                			 rs.getString("ddd"),
                			 rs.getString("telefone"),
                			 rs.getString("cep"),
                			 rs.getString("uf"),
                			 rs.getString("cidade"));
                usuario.setEndereco(endereco);
                usuario.setNome(rs.getString("nome"));
                usuario.setRg(rs.getString("rg"));
                usuario.setUfRg(rs.getString("ufrg"));
                //usuario.setSenha(rs.getString("senha"));
                usuario.setTipoUsuario(rs.getInt("tipo"));
                usuarios.add(usuario);
            }
            return usuarios;
        } catch (SQLException ex) {
            return usuarios;
        }  finally {
        	try {
                if (rs != null){
                    rs.close();
                }
            } catch (SQLException e) { /* ignored */}
            finally {
                try {
                    if (pstmt != null){
                        pstmt.close();
                    }
                } catch (SQLException e) { /* ignored */}
                finally {
                	try{
                		if( conexao != null ){
                			conexao.close();
                		}
                	} catch (SQLException e) { /* ignored */}
                }
            }
        }
	}

	@Override
	public List<Usuario> procurarProducao(String busca, String filtro, int pagina,
			int limite) {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		String select = "SELECT * FROM USUARIO WHERE tipo = '3' AND flag = '1' AND " + filtro + " like '" + busca + "%' " + "ORDER BY " + filtro + " LIMIT " + Integer.toString(pagina*limite) 
				+ "," + Integer.toString(limite);
		Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conexao.prepareStatement(select);
            rs = pstmt.executeQuery();
            while (rs.next()) {
            	Usuario usuario = new Usuario(rs.getInt("id"), rs.getString("login"), rs.getString("senha"));
                usuario.setCpf(rs.getString("cpf"));
                //usuario.setId(rs.getInt("id"));
                Endereco endereco = 
                new Endereco(rs.getString("bairro"),
                			 rs.getString("rua"),
                			 rs.getString("numero"),
                			 rs.getString("ddd"),
                			 rs.getString("telefone"),
                			 rs.getString("cep"),
                			 rs.getString("uf"),
                			 rs.getString("cidade"));
                usuario.setEndereco(endereco);
                usuario.setNome(rs.getString("nome"));
                usuario.setRg(rs.getString("rg"));
                usuario.setUfRg(rs.getString("ufrg"));
                //usuario.setSenha(rs.getString("senha"));
                usuario.setTipoUsuario(rs.getInt("tipo"));
                usuarios.add(usuario);
            }
            return usuarios;
        } catch (SQLException ex) {
            return usuarios;
        }  finally {
        	try {
                if (rs != null){
                    rs.close();
                }
            } catch (SQLException e) { /* ignored */}
            finally {
                try {
                    if (pstmt != null){
                        pstmt.close();
                    }
                } catch (SQLException e) { /* ignored */}
                finally {
                	try{
                		if( conexao != null ){
                			conexao.close();
                		}
                	} catch (SQLException e) { /* ignored */}
                }
            }
        }
	}

	@Override
	public List<Usuario> procurarLixeira(String busca, String filtro, int pagina,
			int limite) {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		String select = "SELECT * FROM USUARIO WHERE flag = '0' AND " + filtro + " like '" + busca + "%' " + " LIMIT " + Integer.toString(pagina*limite) 
				+ "," + Integer.toString(limite);
		Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conexao.prepareStatement(select);
            rs = pstmt.executeQuery();
            while (rs.next()) {
            	Usuario usuario = new Usuario(rs.getInt("id"), rs.getString("login"), rs.getString("senha"));
                usuario.setCpf(rs.getString("cpf"));
                //usuario.setId(rs.getInt("id"));
                Endereco endereco = 
                new Endereco(rs.getString("bairro"),
                			 rs.getString("rua"),
                			 rs.getString("numero"),
                			 rs.getString("ddd"),
                			 rs.getString("telefone"),
                			 rs.getString("cep"),
                			 rs.getString("uf"),
                			 rs.getString("cidade"));
                usuario.setEndereco(endereco);
                usuario.setNome(rs.getString("nome"));
                usuario.setRg(rs.getString("rg"));
                usuario.setUfRg(rs.getString("ufrg"));
                //usuario.setSenha(rs.getString("senha"));
                usuario.setTipoUsuario(rs.getInt("tipo"));
                usuarios.add(usuario);
            }
            return usuarios;
        } catch (SQLException ex) {
            return usuarios;
        }  finally {
        	try {
                if (rs != null){
                    rs.close();
                }
            } catch (SQLException e) { /* ignored */}
            finally {
                try {
                    if (pstmt != null){
                        pstmt.close();
                    }
                } catch (SQLException e) { /* ignored */}
                finally {
                	try{
                		if( conexao != null ){
                			conexao.close();
                		}
                	} catch (SQLException e) { /* ignored */}
                }
            }
        }
	}

	@Override
	public int [] estatistica() {
		//UsuarioEstatistica est = null;
		int estatisticas[] = null;
		String select = "SELECT tipo, count(*) FROM USUARIO WHERE flag = '1' GROUP BY tipo";
		Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conexao.prepareStatement(select);
            rs = pstmt.executeQuery();
            estatisticas = new int[UsuarioTipo.getQuantidade()];
            for(int i = 0; i < estatisticas.length; i++){
            	estatisticas[i] = 0;
            }
            while (rs.next()) {
            	int tipo = rs.getInt("tipo");
                int count = rs.getInt("count(*)");
                try{
                	estatisticas[tipo-1] = count;
                } catch(NullPointerException ex){
                	return null;
                }
            }
            return estatisticas;
        } catch (SQLException ex) {
            return estatisticas;
        }  finally {
        	try {
                if (rs != null){
                    rs.close();
                }
            } catch (SQLException e) { /* ignored */}
            finally {
                try {
                    if (pstmt != null){
                        pstmt.close();
                    }
                } catch (SQLException e) { /* ignored */}
                finally {
                	try{
                		if( conexao != null ){
                			conexao.close();
                		}
                	} catch (SQLException e) { /* ignored */}
                }
            }
        }
	}

}
