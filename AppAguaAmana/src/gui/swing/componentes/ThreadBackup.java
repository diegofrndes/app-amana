package gui.swing.componentes;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import dao.FabricaConexao;

public class ThreadBackup extends Thread {
	private String diretorio;
	private String arquivo;
	private JDialog frame;
	private JProgressBar bar;

	public ThreadBackup(String diretorio, String arquivo, JDialog frame,
			JProgressBar bar) {
		this.diretorio = diretorio;
		this.arquivo = arquivo;
		this.bar = bar;
		this.frame = frame;
	}

	public void run() {
		String executeCmd = "C:\\Program Files\\MySQL\\MySQL Server 5.6\\bin\\mysqldump -u " + FabricaConexao.getUserBackup()
				+ " -p" + FabricaConexao.getPasswordBackup() + " -B "
				+ FabricaConexao.getBanco() + " -r " + diretorio + "\\"
				+ arquivo;
		Process runtimeProcess;

		try {
			runtimeProcess = Runtime.getRuntime().exec(executeCmd);
			int processComplete = runtimeProcess.waitFor();
			bar.setIndeterminate(false);
			if (processComplete == 0) {
				JOptionPane.showMessageDialog(null,
						"Backup salvo com sucesso.", "Backup Amana",
						JOptionPane.INFORMATION_MESSAGE);
				// return true;
			} else {
				JOptionPane.showMessageDialog(null,
						"Erro ao tentar salvar backup.\n", "Backup Amana",
						JOptionPane.ERROR_MESSAGE);
			}
			frame.dispose();
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"Erro ao tentar salvar backup.\n", "Backup Amana",
					JOptionPane.ERROR_MESSAGE);
		}
	}

}
