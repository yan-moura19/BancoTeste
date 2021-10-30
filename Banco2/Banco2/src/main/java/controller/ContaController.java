package controller;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.banco.banco.Conta;

import repository.ContaRepository;
import repository.TransacaoRepository;

@Controller
public class ContaController {
	
	@Autowired
	ContaRepository contaRepository;
	TransacaoRepository transacaoRepository;
	
	
	@RequestMapping("/")
	public String listaConta(Model model) {
		model.addAttribute("contas",contaRepository.findAll());
		return "list";
		
	}
	@GetMapping("/add")
	public String contaForm(Model model) {
		model.addAttribute("conta", new Conta());
		return "contaForm";
	}
	@PostMapping("/process")
	public String processForm(@Validated Conta conta, BindingResult result) {
		if(result.hasErrors()) {
			return "contaForm";
		}
		contaRepository.save(conta);
		
		return "redirect:/";
	}
	@PutMapping(value ="/deposito/{id}")
	public Conta deposito  (@PathVariable(value = "id") Long idConta,@RequestBody Conta conta,double valor) {
		Optional<Conta> model = contaRepository.findById(idConta);
		
		if(model.isPresent()) {
			Conta _conta =model.get();
			_conta.setSaldo(_conta.getSaldo()+valor);
			
			transacaoRepository.save(idConta,valor, LocalDate.now());
			return new Conta(contaRepository.save(_conta));
			
		}
		return conta ;
		
	}
	@GetMapping("/saldo/{id}")
	public String saldoConta(@PathVariable(value = "id") Long idConta) {
		Optional<Conta> model = contaRepository.findById(idConta);
		
		if(model.isPresent()) {
			Conta _conta =model.get();
			return String.valueOf(_conta.getSaldo()) ;
		}else {
		
			return "não existe";
		}
	}
	@PutMapping(value ="/saque/{id}")
	public Conta saque  (@PathVariable(value = "id") Long idConta,@RequestBody Conta conta,double valor) {
		Optional<Conta> model = contaRepository.findById(idConta);
		
		if(model.isPresent()) {
			Conta _conta =model.get();
			
			if(_conta.isFlagAtivo()) {
				if(_conta.getSaldo() > valor) {
					_conta.setSaldo(_conta.getSaldo()-valor);
					transacaoRepository.save(idConta,valor, LocalDate.now());
					return new Conta(contaRepository.save(_conta));
				}else {
					return conta ;// sem saldo
				}
			}
			else {
				//bandeira não ativa
			}
				
		}
		return null;
	}
	
	@PutMapping(value ="/bloqueio/{id}")
	public Conta bloqueioConta  (@PathVariable(value = "id") Long idConta,@RequestBody Conta conta,double valor) {
		Optional<Conta> model = contaRepository.findById(idConta);
		
		if(model.isPresent()) {
			Conta _conta =model.get();
			_conta.setFlagAtivo(false);
			return new Conta(contaRepository.save(_conta));
			
		}
		return null;
	}
	@PutMapping(value ="/desbloqueio/{id}")
	public Conta desbloqueioConta  (@PathVariable(value = "id") Long idConta,@RequestBody Conta conta,double valor) {
		Optional<Conta> model = contaRepository.findById(idConta);
		
		if(model.isPresent()) {
			Conta _conta =model.get();
			
			_conta.setFlagAtivo(true);
			return new Conta(contaRepository.save(_conta));
		}
		return null;
	}

}
