package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.banco.banco.Transacao;

import repository.TransacaoRepository;

@Controller
public class TransacaoController {
	
	@Autowired
	TransacaoRepository transacaoRepository;
	
	@RequestMapping("/")
	public String listaTransacao(Model model) {
		model.addAttribute("transacoes", transacaoRepository.findAll());
		return "list";
	}
	@GetMapping("/add")
	public String transacaoForm(Model model) {
		model.addAttribute("transacao", new Transacao());
		return "transacaoForm";
	}
	@PostMapping("/process")
	public String processForm(@Validated Transacao transacao, BindingResult result) {
		if(result.hasErrors()) {
			return "pessoaForm";
		}
		transacaoRepository.save(transacao);
		
		return "redirect:/";
		
	}
	@GetMapping("/transacoes/{id}")
	public List<Transacao> extratoConta(@PathVariable(value = "id") Conta idConta) {
		Iterable<Transacao> model = transacaoRepository.findAll();
		List<Transacao> transacoesDaConta = new ArrayList<Transacao>();
		for (Transacao transacao : model) {
			if(transacao.getIdConta() == idConta) {
				transacoesDaConta.add(transacao);
			}
		}
		
		return transacoesDaConta;
	}
	@GetMapping("/transacoes/{id}")
	public List<Transacao> extratoContaPeriodo(@PathVariable(value = "id") Conta idConta, LocalDate inicio, LocalDate fim) {
		Iterable<Transacao> model = transacaoRepository.findAll();
		List<Transacao> transacoesDaConta = new ArrayList<Transacao>();
		List<Transacao> transacoesDaContaPorPeriodo = new ArrayList<Transacao>();
		for (Transacao transacao : model) {
			if(transacao.getIdConta() == idConta) {
				transacoesDaConta.add(transacao);
			}
		}
		for (Transacao transacao2  : transacoesDaConta) {
			if(transacao2.getDataTransacao().isAfter(inicio) & transacao2.getDataTransacao().isBefore(fim)) {
				transacoesDaContaPorPeriodo.add(transacao2);
			}
		}
		
		return transacoesDaContaPorPeriodo;
	}
	
	

}
