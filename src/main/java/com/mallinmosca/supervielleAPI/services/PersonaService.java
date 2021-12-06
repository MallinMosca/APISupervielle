package com.mallinmosca.supervielleAPI.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mallinmosca.supervielleAPI.models.PersonaModel;
import com.mallinmosca.supervielleAPI.repositories.PersonaRepository;

@Service	
public class PersonaService {
	@Autowired
	PersonaRepository personaRepository;
	
	public ArrayList<PersonaModel> getAllPersonas(){
		return (ArrayList <PersonaModel>) personaRepository.findAll();
	}
	
	public Optional<PersonaModel> getPersonaById(Long id) {
		return personaRepository.findById(id);
	}
	
	public PersonaModel savePersona(PersonaModel persona) {
		return personaRepository.save(persona);
	}
	
	public boolean deletePersona(Long id) {
		try {
			personaRepository.deleteById(id);
			return true;
		}catch(Exception err) {
			return false;
		}
	}
}


