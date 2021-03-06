package com.gft.gerenciador.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.gft.gerenciador.domain.Casa;
import com.gft.gerenciador.repository.CasaRepository;
import com.gft.gerenciador.service.exceptions.casa.CasaExistenteException;
import com.gft.gerenciador.service.exceptions.casa.CasaNaoEncontradaException;


@Service
public class CasaService {

	@Autowired
	private CasaRepository casas;
	
	public Casa salvar(Casa casa) {
		
		Casa casaNome = casas.findByNome(casa.getNome());
		if(casaNome != null) {
			throw new CasaExistenteException("A casa já existe");
		}
		
		if (casa.getId() != null) {
			Optional<Casa> a = casas.findById(casa.getId());
			
			if (a.isPresent()) {
				throw new CasaExistenteException("A casa já existe");
			}
			
			
		}
		return casas.save(casa);
	}
	
	public void excluir(Long id) {
		casas.deleteById(id);
	}
	
	public List<Casa> listar(){
		return casas.findAll();
	}
	
	public Optional<Casa> buscar(Long id) {
		Optional<Casa> casa = casas.findById(id);
		
		if (casa.isEmpty()) {
			throw new CasaNaoEncontradaException("A casa não foi encontrado.");
		}
		casa.get();
		return casa;
	}
	
	public void deletar(Long id) {
		try {
			casas.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new CasaNaoEncontradaException("A casa não pôde ser encontrado.");
		}
		
	}
	
	public void atualizar(Casa casa) {
		verificarExistencia(casa);
		casas.save(casa);
	}
	
	private void verificarExistencia(Casa casa) {
		buscar(casa.getId());
	}
	
	public List<Casa> listarCrescente(){
		return casas.findAll(Sort.by(Sort.Direction.ASC,"nome"));
	}

	public List<Casa> listarDecrecente(){
		return casas.findAll(Sort.by(Sort.Direction.DESC,"nome"));
	}
	
	public Casa  buscarPorNome(String nome){
		 Casa casa = casas.findByNome(nome);
		return casa;
	}
}
