package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.controller.validator.TipoValidator;
import it.uniroma3.siw.model.Tipo;
import it.uniroma3.siw.service.TipoService;

@Controller
public class TipoController {

	@Autowired
	private TipoService tipoService;
	
	@Autowired
	private TipoValidator tipoValidator;

	@RequestMapping(value="/addTipoForm", method=RequestMethod.GET)
	public String getAddTipoForm(Model model) {
		model.addAttribute("tipo", new Tipo());
		
		return "/admin/addTipoForm";
	}

	@RequestMapping(value="/addTipo", method=RequestMethod.POST)
	public String addTipo(Model model, 
						  @ModelAttribute("tipo") Tipo tipo,
						  BindingResult bindingResult,
			              @RequestParam("prezzo") String prezzo) {
		
		if(!prezzo.equals("")) {
			Float prezzo_float = Float.parseFloat(prezzo);
			tipo.setPrezzo(prezzo_float);
		}
		
		this.tipoValidator.validate(tipo, bindingResult);
		
		if(!bindingResult.hasErrors()) {
			tipoService.inserisci(tipo);
		
			return "index";
		}

		model.addAttribute("tipo", new Tipo());

		return "/admin/addTipoForm";
	}
	
	@RequestMapping(value="/visualizzaTipologie", method=RequestMethod.GET)
	public String visualizzaTipologie(Model model) {
		
		model.addAttribute("tipologie", this.tipoService.tutti());
		return "tipologie";
	}
	
	@RequestMapping(value="/getTipoEsame/{id}", method=RequestMethod.GET)
	public String getTipoEsame(Model model, @PathVariable("id") Long id) {
		
		model.addAttribute("tipo", this.tipoService.tipoPerId(id));
		return "tipologiaEsame";
	}
	
	@RequestMapping(value="/deleteTipo/{id}", method=RequestMethod.GET)
	public String deleteTipoEsame(Model model, @PathVariable("id") Long id) {
		
		this.tipoService.deleteTipo(this.tipoService.tipoPerId(id));
		
		model.addAttribute("tipologie", this.tipoService.tutti());

		return "tipologie";
	}
	
	@RequestMapping(value="/editTipo/{id}", method=RequestMethod.GET)
	public String editTipoEsame(Model model, @PathVariable("id") Long id) {
		
		model.addAttribute("tipo", this.tipoService.tipoPerId(id));
		
		return "/admin/editTipoForm";
	}

	@RequestMapping(value="/editTipo/{id}", method=RequestMethod.POST)
	public String editTipoEsame(Model model, 
								@PathVariable("id") Long id,
								@RequestParam("prezzo") Float prezzo,
								@ModelAttribute("tipo") Tipo tipo) {
		
		Tipo tipoDB = this.tipoService.tipoPerId(id);
		
		tipoDB.setNome(tipo.getNome());
		tipoDB.setDescrizione(tipo.getDescrizione());
		tipoDB.setPrezzo(prezzo);
		tipoDB.setPrerequisiti(tipo.getPrerequisiti());
		tipoDB.setIndicatori(tipo.getIndicatori());
		
		this.tipoService.inserisci(tipoDB);
		
		model.addAttribute("tipo", this.tipoService.tipoPerId(id));
		
		return "tipologiaEsame";
	}
	
	
}
