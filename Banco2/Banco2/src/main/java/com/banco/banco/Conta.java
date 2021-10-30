package com.banco.banco;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.BatchSize;
import org.springframework.http.HttpStatus;

import lombok.NonNull;

@Entity
public class Conta {
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private long idConta;
	
	@NonNull
	
	private double saldo = 0;
	private double limiteSaqueDiario= 2000.0;
	private boolean flagAtivo = false;
	@NonNull
	@Column(length = 4)
	private long tipoConta;
	@NonNull
	private LocalDate dataCriacao = LocalDate.now();
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Pessoa idPessoa;
	
	
	public Conta(long idConta, @NonNull long idPessoa, double saldo, double limiteSaqueDiario, boolean flagAtivo,
			@NonNull long tipoConta,@NonNull LocalDate dataCriacao) {
		super();
		this.idConta = idConta;
		this.idPessoa = idPessoa;
		this.saldo = saldo;
		this.limiteSaqueDiario = limiteSaqueDiario;
		this.flagAtivo = flagAtivo;
		this.tipoConta = tipoConta;
		this.dataCriacao = dataCriacao;
	}
	public Conta(Conta conta) {
		
	}
	public Conta() {
		
	}
	public long getIdConta() {
		return idConta;
	}
	public void setIdConta(long idConta) {
		this.idConta = idConta;
	}
	public long getIdPessoa() {
		return idPessoa;
	}
	public void setIdPessoa(long idPessoa) {
		this.idPessoa = idPessoa;
	}
	public double getSaldo() {
		return saldo;
	}
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	public double getLimiteSaqueDiario() {
		return limiteSaqueDiario;
	}
	public void setLimiteSaqueDiario(double limiteSaqueDiario) {
		this.limiteSaqueDiario = limiteSaqueDiario;
	}
	public boolean isFlagAtivo() {
		return flagAtivo;
	}
	public void setFlagAtivo(boolean flagAtivo) {
		this.flagAtivo = flagAtivo;
	}
	public long getTipoConta() {
		return tipoConta;
	}
	public void setTipoConta(long tipoConta) {
		this.tipoConta = tipoConta;
	}
	public @NonNull LocalDate getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(@NonNull LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	} 
	
	
}
