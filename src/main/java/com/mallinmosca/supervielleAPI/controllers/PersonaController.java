package com.mallinmosca.supervielleAPI.controllers;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mallinmosca.supervielleAPI.models.PersonaModel;
import com.mallinmosca.supervielleAPI.services.PersonaService;

@RestController
@RequestMapping("/personas")
public class PersonaController {
	@Autowired
	PersonaService personaService;

	@Autowired
	ObjectMapper mapper;
	
	@GetMapping()
	public ArrayList <PersonaModel> getAllPersonas(){
		return personaService.getAllPersonas();
	}
	
	@GetMapping(path = "/{id}")
	public Optional<PersonaModel> getPersonaById(@PathVariable("id") Long id) {
		return this.personaService.getPersonaById(id);
	}
	
	@GetMapping("/estadisticas")
	public ObjectNode getEstadisticas() {
		ObjectNode json = this.personaService.getEstadisticas();
		return json;
	}
	

	
	
	
	@PostMapping()
	public String savePersona(@RequestBody PersonaModel persona) {
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
			this.personaService.savePersona(persona);
			Long id = persona.getId();
			msg = "Persona guardada con id: " + id;
		}
		
		return msg;
		
	}
	

	
	@DeleteMapping(path= "/{id}")
	public String deletePersonaById(@PathVariable("id") Long id) {
		boolean ok = this.personaService.deletePersona(id);
		if(ok) {
			return "Se eliminó correctamente la persona con id: " + id;
		}else {
			return "No se pudo eliminar la persona con id: " + id;
		}
	}
	
	
}
