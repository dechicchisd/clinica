package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.model.Paziente;
import it.uniroma3.siw.service.PazienteService;

@Controller
public class PazienteController {

	@Autowired
	private PazienteService pazienteService;

	@RequestMapping(value="/addPaziente", method=RequestMethod.GET)
	public String addPaziente(Model model) {
		model.addAttribute("paziente", new Paziente());
		return "/admin/addPazienteForm";
	}

	@RequestMapping(value="/addPaziente", method=RequestMethod.POST)
	public String addPaziente(Model model, @ModelAttribute("paziente") Paziente paziente) {

		this.pazienteService.inserisci(paziente);
		return "index";
	}
}
