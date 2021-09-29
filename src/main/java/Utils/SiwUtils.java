package Utils;

import java.util.Formatter;

import org.springframework.ui.Model;

import it.uniroma3.siw.model.Tipo;

public class SiwUtils {

	public static void addPrezzoAndTipoToModel(Model model, Tipo tipoDB, Float prezzo) {

		Formatter formatter = new Formatter();
		
		model.addAttribute("prezzo", formatter.format("%.2f", prezzo).toString());
		model.addAttribute("tipo", tipoDB);
		
		formatter.close();
	}
	
	
}
