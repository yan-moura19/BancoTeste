package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.banco.banco.Pessoa;

import repository.PessoaRepository;

@Controller
public class PessoaController {
	
	@Autowired
	PessoaRepository pessoaRepository;
	
	@RequestMapping("/")
	public String listaPessoa(Model model) {
		model.addAttribute("contas", pessoaRepository.findAll());
		return "list";
	}
	@GetMapping("/add")
	public String pessoaForm(Model model) {
		model.addAttribute("pessoa", new Pessoa());
		return "pessoaForm";
	}
	@PostMapping("/process")
	public String processForm(@Validated Pessoa pessoa, BindingResult result) {
		if(result.hasErrors()) {
			return "pessoaForm";
		}
		pessoaRepository.save(pessoa);
		
		return "redirect:/";
		
	}

}
