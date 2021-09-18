package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.controller.validator.PazienteValidator;
import it.uniroma3.siw.model.Paziente;
import it.uniroma3.siw.service.PazienteService;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PazienteController {

	@Autowired
	private PazienteService pazienteService;
	
	@Autowired
	private PazienteValidator pazienteValidator;

	@RequestMapping(value="/addPaziente", method=RequestMethod.GET)
	public String addPaziente(Model model) {
		model.addAttribute("paziente", new Paziente());
		return "/admin/addPazienteForm";
	}

	@RequestMapping(value="/addPaziente", method=RequestMethod.POST)
	public String addPaziente(Model model, @ModelAttribute("paziente") Paziente paziente, BindingResult bindingResult) {
		
		this.pazienteValidator.validate(paziente, bindingResult);
		
		if(!bindingResult.hasErrors()) {
			this.pazienteService.inserisci(paziente);
			return "index";
		}
		
		model.addAttribute("paziente", new Paziente());
		return "/admin/addPazienteForm";		
	}

	@RequestMapping(value="/deletePazienteForm", method=RequestMethod.GET)
	public String deletePazienteForm(Model model) {
		return "/admin/deletePaziente";
	}

	@RequestMapping(value="/deletePaziente", method=RequestMethod.GET)
	public String deletePaziente(Model model,
							     @RequestParam("pazienteID") String id,
							     @RequestParam("nome") String nome,
							     @RequestParam("cognome") String cognome) {
		
		if(!id.equals("") && !nome.equals("") && !cognome.equals("")) {
			Paziente pazienteByNC = this.pazienteService.pazientePerNomeCognome(nome, cognome);
			Paziente pazienteById = this.pazienteService.pazientePerId(Long.parseLong(id));
			
			if(pazienteByNC == null || pazienteById == null) {
				model.addAttribute("frase", "Non è stato possibile eliminare il paziente, ricontrolli i dati immessi.");
				return "risultatiNonDisponibili";
			}
			if(pazienteById.equals(pazienteByNC)) {
				
				this.pazienteService.delete(pazienteById);
				return "index";
			}
		}
		
		return "/admin/deletePaziente";
	}

}
