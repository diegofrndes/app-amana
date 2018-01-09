package gui.swing.temas;

import com.jgoodies.looks.FontSet;
import com.jgoodies.looks.FontSets;
import com.jgoodies.looks.plastic.theme.LightGray;
import java.awt.Font;
import javax.swing.plaf.ColorUIResource;

public class TemaAmana extends LightGray {
	@Override
    protected FontSet getFontSet() {
        FontSet fontSet = FontSets.createDefaultFontSet(
                new Font("Tahoma", Font.PLAIN, 11), // control font
                new Font("Tahoma", Font.PLAIN, 12), // menu font
                new Font("Tahoma", Font.BOLD, 11));     // title font
        return fontSet;
    }
	
	@Override
    protected ColorUIResource getPrimary1() {
            return new ColorUIResource(0, 148, 219);
    }
}
