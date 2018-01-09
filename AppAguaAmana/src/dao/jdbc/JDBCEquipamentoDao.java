package dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import modelo.Equipamento;
import modelo.ManutencaoEquipamento;
import dao.FabricaConexao;
import dao.EquipamentoDao;

public class JDBCEquipamentoDao implements EquipamentoDao {

	@Override
	public int salvar(Equipamento equipamento) {
		String inserir = "INSERT INTO EQUIPAMENTO"
                + "(nome, "
                + "descricao, "
                + "fabricante, "
                + "modelo, "
                + "numeroserie, "
                + "frequencia, " 
                + "flag) "
                + "VALUES(?, ?, ?, ?, ?, ?, 1)";
        Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int chave_gerada = -1;
        try {
            pstmt = conexao.prepareStatement(inserir, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, equipamento.getNome());
            pstmt.setString(2, equipamento.getDescricao());
            pstmt.setString(3, equipamento.getFabricante());
            pstmt.setString(4, equipamento.getModelo());
            pstmt.setString(5, equipamento.getNumeroserie());
            pstmt.setInt(6, equipamento.getFreq());
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
	public boolean editar(Equipamento equipamento) {
        String update = "UPDATE equipamento SET "
        		+ "nome = ?,"
                + "descricao = ?,"
                + "fabricante = ?,"
                + "modelo = ?,"
                + "numeroserie = ?,"
                + "frequencia = ?" 
                + " WHERE id = " + Integer.toString(equipamento.getId());
        Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        int linhasAfetadas = 0;
        try {
            pstmt = conexao.prepareStatement(update);
            pstmt = conexao.prepareStatement(update, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, equipamento.getNome());
            pstmt.setString(2, equipamento.getDescricao());
            pstmt.setString(3, equipamento.getFabricante());
            pstmt.setString(4, equipamento.getModelo());
            pstmt.setString(5, equipamento.getNumeroserie());
            pstmt.setInt(6, equipamento.getFreq());
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
    /*
	@Override
	public boolean existeEquipamento(String numeroserie) {
		Connection conexao = FabricaConexao.getConexao();
	    String encontrar = "SELECT * FROM EQUIPAMENTO WHERE numeroserie = ?";
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
        try {
	    	pstmt = conexao.prepareStatement(encontrar);
	    	pstmt.setString(1, numeroserie);
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
            } catch (SQLException e) { }
            finally {
                try {
                    if (pstmt != null){
                        pstmt.close();
                    }
                } catch (SQLException e) { }
                finally {
                	try{
                		if( conexao != null ){
                			conexao.close();
                		}
                	} catch (SQLException e) { }
                }
            }
        }
	}
	*/

	@Override
	public List<Equipamento> procurarTodos(String busca, String filtro,
			int pagina, int limite) {
		List<Equipamento> equipamentos = new ArrayList<Equipamento>();
		String select = "SELECT * FROM EQUIPAMENTO WHERE flag = '1' AND " + filtro + " like '" + busca + "%' " + "ORDER BY " + filtro + " LIMIT " + Integer.toString(pagina*limite) 
				+ "," + Integer.toString(limite);
		Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            pstmt = conexao.prepareStatement(select);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Equipamento equipamento = new Equipamento(rs.getInt("id"), rs.getString("numeroserie"));
                equipamento.setDescricao(rs.getString("descricao"));
                equipamento.setFabricante(rs.getString("fabricante"));
                equipamento.setFreq(rs.getInt("frequencia"));
                equipamento.setModelo(rs.getString("modelo"));
                equipamento.setNome(rs.getString("nome"));
                String select2 = "SELECT MAX(manutencao) FROM MANUTENCAOEQUIPAMENTO WHERE idequipamento = ? AND tipo = '0'";
                PreparedStatement stmt2 = conexao.prepareStatement(select2);
                stmt2.setInt(1, equipamento.getId());
                ResultSet rs2 = stmt2.executeQuery();
                if (rs2.next()) {
                    Date data = rs2.getDate("max(manutencao)");
                    if( data != null){
                    	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");  
                    	String text = df.format(data);
                        Calendar Cal = Calendar.getInstance();
                        Cal.setTime(data);
                        Cal.add(Calendar.DAY_OF_MONTH, equipamento.getFreq()*30);
                    	String text2 = df.format(Cal.getTime());
                    	equipamento.setUltmanutencao(text);
                    	equipamento.setProxmanutencao(text2);
                    }
                }
                if(rs2 != null)
                	rs2.close();
                if(stmt2 != null)
                	stmt2.close();
                equipamentos.add(equipamento);
            }
            return equipamentos;
        } catch (SQLException ex) {
        	return equipamentos;
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
	public List<Equipamento> procurarManutencaoDia(String busca, String filtro,
			int pagina, int limite) {
		List<Equipamento> equipamentos = new ArrayList<Equipamento>();
		String select = "SELECT * FROM equipamento, manutencaoequipamento WHERE flag = '1' AND idequipamento = equipamento.id AND tipo = '0' AND manutencao = " 
						+ "(SELECT MAX(manutencao) FROM manutencaoequipamento WHERE idequipamento = equipamento.id AND tipo = '0') AND " 
				+"CURDATE() < DATE_ADD(manutencao,INTERVAL frequencia*30 DAY) AND " + filtro + " like '" + busca + "%' " + "ORDER BY " + filtro + " LIMIT " + Integer.toString(pagina*limite) 
				+ "," + Integer.toString(limite); 
		Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            pstmt = conexao.prepareStatement(select);
            rs = pstmt.executeQuery();
            while (rs.next()) {
            	Equipamento equipamento = new Equipamento(rs.getInt("id"), rs.getString("numeroserie"));
                equipamento.setDescricao(rs.getString("descricao"));
                equipamento.setFabricante(rs.getString("fabricante"));
                equipamento.setFreq(rs.getInt("frequencia"));
                equipamento.setModelo(rs.getString("modelo"));
                equipamento.setNome(rs.getString("nome"));
                Date data = rs.getDate("manutencao");
                if( data != null){
                	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");  
                	String text = df.format(data);
                    Calendar Cal = Calendar.getInstance();
                    Cal.setTime(data);
                    Cal.add(Calendar.DAY_OF_MONTH, equipamento.getFreq()*30);
                	String text2 = df.format(Cal.getTime());
                	equipamento.setUltmanutencao(text);
                	equipamento.setProxmanutencao(text2);
                }
               equipamentos.add(equipamento);
            }
            return equipamentos;
        } catch (SQLException ex) {
        	return equipamentos;
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
	public List<Equipamento> procurarManutencaoAtrasada(String busca, String filtro,
			int pagina, int limite) {
		List<Equipamento> equipamentos = new ArrayList<Equipamento>();
		String select = "SELECT * FROM equipamento, manutencaoequipamento WHERE flag = '1' AND idequipamento = equipamento.id AND tipo = '0' AND manutencao = " 
						+ "(SELECT MAX(manutencao) FROM manutencaoequipamento WHERE idequipamento = equipamento.id AND tipo = '0') AND " 
				+"CURDATE() >= DATE_ADD(manutencao,INTERVAL frequencia*30 DAY) AND " + filtro + " like '" + busca + "%' " + "ORDER BY " + filtro + " LIMIT " + Integer.toString(pagina*limite) 
				+ "," + Integer.toString(limite); 
		Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            pstmt = conexao.prepareStatement(select);
            rs = pstmt.executeQuery();
            while (rs.next()) {
            	Equipamento equipamento = new Equipamento(rs.getInt("id"), rs.getString("numeroserie"));
                equipamento.setDescricao(rs.getString("descricao"));
                equipamento.setFabricante(rs.getString("fabricante"));
                equipamento.setFreq(rs.getInt("frequencia"));
                equipamento.setModelo(rs.getString("modelo"));
                equipamento.setNome(rs.getString("nome"));
                Date data = rs.getDate("manutencao");
                if( data != null){
                	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");  
                	String text = df.format(data);
                    Calendar Cal = Calendar.getInstance();
                    Cal.setTime(data);
                    Cal.add(Calendar.DAY_OF_MONTH, equipamento.getFreq()*30);
                	String text2 = df.format(Cal.getTime());
                	equipamento.setUltmanutencao(text);
                	equipamento.setProxmanutencao(text2);
                }
               equipamentos.add(equipamento);
            }
            return equipamentos;
        } catch (SQLException ex) {
        	return equipamentos;
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
	public List<Equipamento> procurarManutencao30Dias(String busca, String filtro,
			int pagina, int limite) {
		List<Equipamento> equipamentos = new ArrayList<Equipamento>();
		String select = "SELECT * FROM equipamento, manutencaoequipamento WHERE flag = '1' AND idequipamento = equipamento.id AND tipo = '0' AND manutencao = " 
						+ "(SELECT MAX(manutencao) FROM manutencaoequipamento WHERE idequipamento = equipamento.id AND tipo = '0') AND " 
				+"CURDATE() < DATE_ADD(manutencao,INTERVAL frequencia*30 DAY) AND " 
				+"DATE_ADD(CURDATE(), INTERVAL 30 DAY) >= DATE_ADD(manutencao,INTERVAL frequencia*30 DAY) AND " + filtro + " like '" + busca + "%' " + "ORDER BY " + filtro + " LIMIT " + Integer.toString(pagina*limite) 
				+ "," + Integer.toString(limite); 
		Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            pstmt = conexao.prepareStatement(select);
            rs = pstmt.executeQuery();
            while (rs.next()) {
            	Equipamento equipamento = new Equipamento(rs.getInt("id"), rs.getString("numeroserie"));
                equipamento.setDescricao(rs.getString("descricao"));
                equipamento.setFabricante(rs.getString("fabricante"));
                equipamento.setFreq(rs.getInt("frequencia"));
                equipamento.setModelo(rs.getString("modelo"));
                equipamento.setNome(rs.getString("nome"));
                Date data = rs.getDate("manutencao");
                if( data != null){
                	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");  
                	String text = df.format(data);
                    Calendar Cal = Calendar.getInstance();
                    Cal.setTime(data);
                    Cal.add(Calendar.DAY_OF_MONTH, equipamento.getFreq()*30);
                	String text2 = df.format(Cal.getTime());
                	equipamento.setUltmanutencao(text);
                	equipamento.setProxmanutencao(text2);
                }
               equipamentos.add(equipamento);
            }
            return equipamentos;
        } catch (SQLException ex) {
            return equipamentos;
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
	public List<Equipamento> procurarLixeira(String busca, String filtro,
			int pagina, int limite) {
		List<Equipamento> equipamentos = new ArrayList<Equipamento>();
		String select = "SELECT * FROM EQUIPAMENTO WHERE flag = '0' AND " + filtro + " like '" + busca + "%' " + "ORDER BY " + filtro + " LIMIT " + Integer.toString(pagina*limite) 
				+ "," + Integer.toString(limite);
		Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conexao.prepareStatement(select);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                
            	Equipamento equipamento = new Equipamento(rs.getInt("id"), rs.getString("numeroserie"));
                equipamento.setDescricao(rs.getString("descricao"));
                equipamento.setFabricante(rs.getString("fabricante"));
                equipamento.setFreq(rs.getInt("frequencia"));
                equipamento.setModelo(rs.getString("modelo"));
                equipamento.setNome(rs.getString("nome"));
                equipamentos.add(equipamento);
            
            }
            return equipamentos;
        } catch (SQLException ex) {
            return equipamentos;
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
	public List<ManutencaoEquipamento> procurarManutencao(Equipamento equipamento, int pagina, int limite) {
		List<ManutencaoEquipamento> manutencoes = new ArrayList<ManutencaoEquipamento>();
		String select = "SELECT * FROM manutencaoequipamento where idequipamento = ? ORDER BY manutencao DESC" + " LIMIT " + Integer.toString(pagina*limite) 
				+ "," + Integer.toString(limite);
		Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            pstmt = conexao.prepareStatement(select);
            pstmt.setInt(1, equipamento.getId());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ManutencaoEquipamento manutencao = new ManutencaoEquipamento(rs.getInt("id"), rs.getBoolean("tipo"));
                Date data = rs.getDate("manutencao");
                if( data != null){
                	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");  
                	String text = df.format(data);
                	manutencao.setData(text);
                }
                manutencao.setObs(rs.getString("obs"));
                manutencoes.add(manutencao);
            }
            return manutencoes;
        } catch (SQLException ex) {
        	return manutencoes;
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
    public boolean desativar(Equipamento equipamento) {
        Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        try {
            String update = "UPDATE equipamento set flag = '0' WHERE id = ?";
            pstmt = conexao.prepareStatement(update);
            pstmt.setInt(1, equipamento.getId());
            int linhas = pstmt.executeUpdate();
            pstmt.close();
            if (conexao != null) {
                conexao.close();
            }
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
	public int adicionarManutencao(Equipamento equipamento,
			ManutencaoEquipamento manutencao) {
		String inserir = "INSERT INTO manutencaoequipamento"
                + "(idequipamento, "
                + "manutencao, "
                + "obs," +
                "tipo) "
                + "VALUES(?, ?, ?, ?)";
        Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int chave_gerada = -1;
        try {
            pstmt = conexao.prepareStatement(inserir, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, equipamento.getId());
            pstmt.setString(2, manutencao.getData());
            pstmt.setString(3, manutencao.getObs());
            pstmt.setBoolean(4, manutencao.getTipo());
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
	public boolean removerManutencao(ManutencaoEquipamento manutencao) {
        Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        try {
            String update = "DELETE from manutencaoequipamento WHERE id = ?";
            pstmt = conexao.prepareStatement(update);
            pstmt.setInt(1, manutencao.getId());
            int linhas = pstmt.executeUpdate();
            pstmt.close();
            if (conexao != null) {
                conexao.close();
            }
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
	
}
