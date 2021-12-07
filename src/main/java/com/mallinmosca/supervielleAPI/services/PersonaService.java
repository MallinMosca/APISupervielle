package com.mallinmosca.supervielleAPI.services;

import java.time.LocalDate;
import java.time.Period;
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
	
	public String savePersonaWithFilters(PersonaModel persona) {
		boolean grabar = true;
		String msg = "";
		String doc = persona.getNroDoc();
		
		if(persona.getId() == null) {//SI TIENE ID DIRECTAMENTE SE MODIFICA LA PERSONA
			
			//COMPRUEBA QUE EL DNI INGRESADO NO SE ENCUENTRE ASIGNADO A OTRA PERSONA
			ArrayList <PersonaModel> aPersonas = this.getAllPersonas();
			for (int i = 0; i < aPersonas.size(); i++) {
				PersonaModel per = aPersonas.get(i);
				if(doc.equals(per.getNroDoc())) {
					grabar = false;
					msg = "La persona que intenta grabar ya existe";
				}
			}
		}
		
		
		//COMPRUEBA QUE LA PERSONA A GRABAR SEA MAYOR DE 18 AÑOS
		if(persona.getFechaNacimiento() != null) { 		
			Period edad = Period.between(persona.getFechaNacimiento(), LocalDate.now());
			if(edad.getYears() < 18) { 
				msg = "No se pueden grabar personas menores de 18 años"; grabar = false; 
			} 
		}
		
		//COMPRUEBA QUE TENGA AL MENOS UN DATO DE CONTACTO
		if(persona.getEmail() == null && persona.getTelefono() == null) {
			grabar = false;
			msg = "Debe indicar al menos un dato de contacto";
		}
		
		if(grabar) {
			this.savePersona(persona);
			Long id = persona.getId();
			msg = "Persona guardada con id: " + id;
		}
		
		return msg;
		
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


