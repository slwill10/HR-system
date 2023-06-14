package com.appRH.appRG.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.appRH.appRG.models.Candidato;
import com.appRH.appRG.models.Vaga;
import com.appRH.appRG.repository.CandidatoRepository;
import com.appRH.appRG.repository.VagaRepository;

@Controller
public class VagaController {
	
	private VagaRepository vr;
	private CandidatoRepository cr;
	
	
	// CADASTRA VAGA
	@RequestMapping(value="/cadastrarVaga", method = RequestMethod.GET)
	public String form() {	
		return "vaga/formVaga";
	}
	@RequestMapping(value= "/cadastrarVaga)", method = RequestMethod.POST)
	public String form(@Valid Vaga vaga, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos...");
			return "redirect:/cadastrarVaga";
		}	
		vr.save(vaga);
		attributes.addFlashAttribute("mensagem", "Vaga cadastrada com sucesso!");
		return "redirect:/cadastrarVaga";	
	}
	
	// LISTA VAGAS
	@RequestMapping(value="/vagas")
	public ModelAndView listaVagas() {
		ModelAndView mv = new ModelAndView("vaga/listaVaga");
		Iterable<Vaga> vagas = vr.findAll();
		mv.addObject("vagas", vagas);
		return mv;
	}
	
	//
	@RequestMapping(value="/{codigo}", method = RequestMethod.GET)
	public ModelAndView detalhesDaVaga(@PathVariable("codigo") long codigo) {
		Vaga vaga = vr.findByCodigo(codigo);
		ModelAndView mv = new ModelAndView("vaga/detalhesVaga");
		mv.addObject("vaga", vaga);
		Iterable<Candidato> candidatos = cr.findByVaga(vaga);
		mv.addObject("candidatos", candidatos);
		return mv;
	}
	
	
	
}
