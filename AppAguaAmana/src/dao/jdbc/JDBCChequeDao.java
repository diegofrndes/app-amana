package dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.ChequeDao;
import dao.FabricaConexao;
import modelo.Cheque;
import modelo.Pessoa;

public class JDBCChequeDao implements ChequeDao {

	@Override
    public int salvar(Cheque cheque) {
        String inserir = "INSERT INTO cheque"
                + "(idcliente,"
                + "numero,"
                + "titular,"
                + "vencimento,"
                + "data,"
                + "valor,"
                + "estado)"
                + " VALUES(?,?,?,?,?,?,0)";
        Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int chave_gerada = -1;
        try {
            pstmt = conexao.prepareStatement(inserir, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, cheque.getCliente().getId());
            pstmt.setString(2, cheque.getNumero());
            pstmt.setString(3, cheque.getTitular());
            pstmt.setString(4, cheque.getVencimento());
            pstmt.setString(5, cheque.getData());
            pstmt.setBigDecimal(6, cheque.getValor());
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
	public List<Cheque> procurarTodos(String busca, String filtro, int pagina, int limite) {
		List<Cheque> cheques = new ArrayList<Cheque>();
		String select = "SELECT * FROM CHEQUE INNER JOIN PESSOA ON PESSOA.ID = CHEQUE.IDCLIENTE WHERE " + filtro + " like '" + busca + "%' " + "ORDER BY " + filtro + " LIMIT " + Integer.toString(pagina*limite) 
				+ "," + Integer.toString(limite);
		Connection conexao = FabricaConexao.getConexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conexao.prepareStatement(select);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Cheque cheque = new Cheque();
                cheque.setId(rs.getInt("cheque.id"));
                Pessoa pessoa = new Pessoa(rs.getInt("idcliente"));
                pessoa.setNome(rs.getString("pessoa.nome"));
                cheque.setCliente(pessoa);
                cheque.setNumero(rs.getString("numero"));
                cheque.setValor(rs.getBigDecimal("valor"));
                cheque.setData(rs.getString("data"));
                cheque.setVencimento(rs.getString("vencimento"));
                cheque.setTitular(rs.getString("titular"));
                cheque.setObs(rs.getString("obs"));
                cheques.add(cheque);
            }
            return cheques;
        } catch (SQLException ex) {
            return cheques;
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
	public List<Cheque> procurarPendentes(String busca, String filtro, int pagina, int limite) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cheque> procurarFinalizados(String busca, String filtro, int pagina, int limite) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cheque> procurarVencidos(String busca, String filtro, int pagina, int limite) {
		// TODO Auto-generated method stub
		return null;
	}
}
