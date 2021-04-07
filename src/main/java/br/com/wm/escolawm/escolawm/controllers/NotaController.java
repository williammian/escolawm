package br.com.wm.escolawm.escolawm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.wm.escolawm.escolawm.models.Aluno;
import br.com.wm.escolawm.escolawm.models.Nota;
import br.com.wm.escolawm.escolawm.repositorys.AlunoRepository;

@Controller
public class NotaController {

	@Autowired
	private AlunoRepository repository;

	@GetMapping("/nota/cadastrar/{id}")
	public String cadastrar(@PathVariable String id, Model model) {
		Aluno aluno = repository.obterAlunoPor(id);
	    model.addAttribute("aluno", aluno);
	    model.addAttribute("nota", new Nota());

	    return "/nota/cadastrar";
	}
	
	@PostMapping("/nota/salvar/{id}")
	public String salvar(@PathVariable String id, @ModelAttribute Nota nota) {
		Aluno aluno = repository.obterAlunoPor(id);
		repository.salvar(aluno.adicionar(aluno, nota));
		return "redirect:/aluno/listar";
	}
	
}
