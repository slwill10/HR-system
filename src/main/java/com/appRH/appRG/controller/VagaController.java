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
	
	public String detalhesVagaPost(@PathVariable("codigo") long codigo, @Valid Candidato candidato, BindingResult result, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos!");
			return "redirect:/{codigo}";
		}
		// RG duplicado
		
		if(cr.findByRg(candidato.getRg()) != null) {
			attributes.addFlashAttribute("mensagem erro", "RG duplicado");
			return "redirect:/{codigo}";
		}
		
		Vaga vaga = vr.findByCodigo(codigo);
		candidato.setVaga(vaga);
		cr.save(candidato);
		attributes.addFlashAttribute("mensagem", "Candidato adicionado com sucesso!");
		return "redirect:/{codigo}";
	}
	
	// DELETA A VAGA 
	@RequestMapping(value="/deletarVaga")
	public String deletarVaga(long codigo) {
		Vaga vaga = vr.findByCodigo(codigo);
		vr.delete(vaga);
		return "redirect:/vagas";
	}
	
	// DELETA CANDIDATO pelo RG
	@RequestMapping(value="/deletarCandidato")
	public String deletarCandidato(String rg) {
		Candidato candidato = cr.findByRg(rg);
		Vaga vaga = candidato.getVaga();
		String codigo = "" + vaga.getCodigo();	
		cr.delete(candidato);	
		return "redirect:/" + codigo;	
	}
	
	// MÉTODOS QUE ATUALIZAM A VAGA
	//formulario de edição de vaga
	@RequestMapping(value="/editar-vaga", method = RequestMethod.GET)
	public ModelAndView editarVaga (long codigo) {
		Vaga vaga = vr.findByCodigo(codigo);
		ModelAndView mv = new ModelAndView("vaga/update-vaga");
		mv.addObject("vaga", vaga);
		return mv;
	}
	
	//UPDATE DA VAGA 
	@RequestMapping(value="/editarVaga", method = RequestMethod.POST)
	public String updateVaga(@Valid Vaga vaga, BindingResult result, RedirectAttributes attributes) {
		vr.save(vaga);
		attributes.addFlashAttribute("sucess", "Vaga alterada com sucesso!");
		long codigoLong = vaga.getCodigo();
		String codigo = "" + codigoLong;
		return "redirect:/" + codigo;
	}
	
	
	
	
	
	
	
	
	
	
}
