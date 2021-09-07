package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Tipo;
import it.uniroma3.siw.service.TipoService;

@Controller
public class TipoController {

	@Autowired
	private TipoService tipoService;

	@RequestMapping(value="/addTipoForm", method=RequestMethod.GET)
	public String getAddTipoForm(Model model) {
		model.addAttribute("tipo", new Tipo());
		
		return "addTipoForm.html";
	}

	@RequestMapping(value="/addTipo", method=RequestMethod.POST)
	public String addTipo(Model model, @ModelAttribute("tipo") Tipo tipo, @RequestParam("prezzo") String prezzo) {

		Float prezzo_float = Float.parseFloat(prezzo);
		tipo.setPrezzo(prezzo_float);
		
		tipoService.inserisci(tipo);
		
		return "index.html";
	}
}
