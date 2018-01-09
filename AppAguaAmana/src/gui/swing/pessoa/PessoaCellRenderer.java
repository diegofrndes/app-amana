package gui.swing.pessoa;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class PessoaCellRenderer extends JLabel implements TableCellRenderer {

	private static final long serialVersionUID = 1L;

	public PessoaCellRenderer() {
		setOpaque(true); // MUST do this for background to show up.
		setHorizontalAlignment(JLabel.CENTER);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		setText(value.toString());
		setBackground(Color.WHITE);

		if (table.isRowSelected(row)) {
			setBackground(new Color(0, 148, 219));
			setForeground(Color.WHITE);
			setFont(new Font("Tahoma", Font.BOLD, 11));
		} else {
			if (row % 2 == 0)
				setBackground(new Color(243, 243, 243));
			else
				setBackground(new Color(255, 255, 255));
			setForeground(Color.BLACK);
			setFont(new Font("Tahoma", Font.PLAIN, 11));
		}

		if (column == 6) {
			BigDecimal debito = (BigDecimal) value;
			NumberFormat moneyFormat1 = NumberFormat
					.getCurrencyInstance(new Locale("pt", "BR")); // para
																	// formatar
																	// os
																	// numeros
																	// na moeda
																	// do
																	// Brasil.
			moneyFormat1.setMinimumFractionDigits(2); // ** pelo q entendi, aki
														// seria para definir a
														// quandidade de casas
														// decimais
			setText(moneyFormat1.format(debito));
		}

		if (column == 7) {
			BigDecimal debito = (BigDecimal) value;
			debito = debito.multiply(new BigDecimal(-1));
			NumberFormat moneyFormat1 = NumberFormat
					.getCurrencyInstance(new Locale("pt", "BR")); // para
																	// formatar
																	// os
																	// numeros
																	// na moeda
																	// do
																	// Brasil.
			moneyFormat1.setMinimumFractionDigits(2); // ** pelo q entendi, aki
														// seria para definir a
														// quandidade de casas
														// decimais
			setText(moneyFormat1.format(debito));
			if (debito.signum() == 1) {
				setBackground(new Color(255, 215, 215));
				setForeground(Color.BLACK);
			}
		}

		if (column == 8) {
			BigDecimal debito = (BigDecimal) value;
			NumberFormat moneyFormat1 = NumberFormat
					.getCurrencyInstance(new Locale("pt", "BR")); // para
																	// formatar
																	// os
																	// numeros
																	// na moeda
																	// do
																	// Brasil.
			moneyFormat1.setMinimumFractionDigits(2); // ** pelo q entendi, aki
														// seria para definir a
														// quandidade de casas
														// decimais
			setText(moneyFormat1.format(debito));
			if (debito.signum() == 1) {
				setBackground(new Color(215, 255, 215));
				setForeground(Color.BLACK);
			}

		}

		return this;
	}
}