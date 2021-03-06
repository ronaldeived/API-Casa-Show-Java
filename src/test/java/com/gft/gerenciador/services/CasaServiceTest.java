package com.gft.gerenciador.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

import org.hibernate.query.criteria.internal.expression.function.AggregationFunction.COUNT;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.gft.gerenciador.domain.Casa;
import com.gft.gerenciador.repository.CasaRepository;
import com.gft.gerenciador.service.CasaService;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CasaServiceTest {
	
	private Casa casa;
	 
	@Autowired
	private CasaRepository repository;
	
	@InjectMocks
	private CasaService service;
	
	@Before
	public void setup() {
		casa = new Casa("Casa de show", "Endereço");
	}
	
	@Test
	public void deveSalvarCasaDeShow() {
		repository.save(casa);
		assertThat(casa.getId()).isNotNull();
		assertThat(casa.getNome()).isEqualTo("Casa de show");
	}
	
	@Test
	public void deveDeletarCasa() {
		repository.save(casa);
		repository.delete(casa);
		assertThat(repository.findById(casa.getId()).isEmpty());
	}
	
	@Test
	public void deveEditarCasa() {
		repository.save(casa);
		casa.setNome("Casa Euphoria");
		casa.setEndereco("Visconde");
		repository.save(casa);
		assertThat(casa.getNome()).isEqualTo("Casa Euphoria");
		assertEquals(casa.getEndereco(), "Visconde");
		//System.out.println(casa.getNome());
	}
	
	@Test
	public void deveReceberQuantidadeDeCasasCadastradas() {
		casa = new Casa("Casa de show", "Endereço");
		Casa casa2 = new Casa("Casa de show", "Endereço");
		repository.save(casa);
		repository.save(casa2);
		
		System.out.println("Quantidade de casas: " + repository.count());
	}
	
	@Test
	public void deveBuscarCasaPeloId() {
		casa = new Casa("Casa de show", "Endereço");
	
		repository.save(casa);
		
		//3 maneiras de se fazer o teste
		assertThat(casa.getId());//(casa.getId(), casa.getId());//(casa.getId().toString(), true);
	}
}
