package it.uniroma3.siw.controller;

import java.util.Formatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import Utils.SiwUtils;
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
		Formatter formatter = new Formatter();
		Tipo tipo = this.tipoService.tipoPerId(id);
		model.addAttribute("tipo", tipo);
		model.addAttribute("prezzo", formatter.format("%.2f", tipo.getPrezzo()).toString());
		formatter.close();
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
								@RequestParam("prezzo") String prezzo,
								@ModelAttribute("tipo") Tipo tipo) {
		
		Tipo tipoDB = this.tipoService.tipoPerId(id);
		
		Float prezzoNew = tipo.getPrezzo();
		String nome = tipo.getNome();
		String desc = tipo.getDescrizione();
		String pre = tipo.getPrerequisiti();
		String ind = tipo.getIndicatori();
		
		
		System.out.println(nome + "\n" + desc + "\n" + prezzo + "\n" + ind + "\n");
		if(!nome.equals("") && !desc.equals("") && !ind.equals("") && !prezzo.equals("")) {
			tipoDB.setNome(nome);
			tipoDB.setDescrizione(desc);
			tipoDB.setPrerequisiti(pre);
			tipoDB.setIndicatori(ind);
			
			prezzoNew = Float.parseFloat(prezzo);
			tipoDB.setPrezzo(prezzoNew);
			
			this.tipoService.inserisci(tipoDB);
			
			Formatter formatter = new Formatter();

			model.addAttribute("prezzo", formatter.format("%.2f", prezzoNew).toString());
			model.addAttribute("tipo", this.tipoService.tipoPerId(id));
			
			formatter.close();
			
			return "tipologiaEsame";
		}
		
		Formatter formatter = new Formatter();
		
		model.addAttribute("prezzo", formatter.format("%.2f", tipoDB.getPrezzo()).toString());
		model.addAttribute("tipo", tipoDB);
		
		formatter.close();
		
		return "tipologiaEsame";
	}
	
	
}
