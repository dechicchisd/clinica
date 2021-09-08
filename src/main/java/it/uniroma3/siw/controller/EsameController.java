package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Esame;
import it.uniroma3.siw.model.Tipo;
import it.uniroma3.siw.service.TipoService;

@Controller
public class EsameController {

	@Autowired
	private TipoService tipoService;

	@RequestMapping(value="/prenotaEsameForm", method=RequestMethod.GET)
	public String getPrenotaEsameForm(Model model) {
		model.addAttribute("esame", new Esame());
		model.addAttribute("tipologie", this.tipoService.tutti());
		return "prenotaEsameForm";
	}
	
	@RequestMapping(value="/prenotaEsame", method=RequestMethod.POST)
	public String prenotaEsame(Model model, 
							   @ModelAttribute("esame") Esame esame,
							   @RequestParam("tipoID") Long id) {
		
		Tipo tipo = tipoService.tipoPerId(id);
		esame.setTipo(tipo);
		
		
		
		return "index";
	}
}
