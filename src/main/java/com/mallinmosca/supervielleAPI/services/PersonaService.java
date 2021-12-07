package com.mallinmosca.supervielleAPI.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mallinmosca.supervielleAPI.models.PersonaModel;
import com.mallinmosca.supervielleAPI.repositories.PersonaRepository;

@Service	
public class PersonaService {
	@Autowired
	PersonaRepository personaRepository;
	@Autowired
	ObjectMapper mapper;
	
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
	
	public ObjectNode getEstadisticas() {
		ObjectNode json = mapper.createObjectNode();
		int cantidadMujeres = 0;
		int cantidadHombres = 0;
		ArrayList <PersonaModel> aPersonas = this.getAllPersonas();
		int total = aPersonas.size();
		int cantArgentinos = 0;
		
		for (int i = 0; i < aPersonas.size(); i++) {
			PersonaModel per = aPersonas.get(i);
			String sexo = per.getSexo();
			
			if(sexo.equals("femenino")) {
				cantidadMujeres++;
			}else if(sexo.equals("masculino")) {
				cantidadHombres++;
			}
			
			if(per.getPais().equals("Argentina")) {
				cantArgentinos++;
			}
			
		}
		
		int porcentaje = cantArgentinos * 100 / total;

		
		json.put("cantidad_mujeres", cantidadMujeres);
		json.put("cantidad_hombres", cantidadHombres);
		json.put("porcentaje_argentinos", porcentaje);
		
		return json;
	}
}


