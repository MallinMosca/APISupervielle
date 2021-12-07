package com.mallinmosca.supervielleAPI.controllers;

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

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/personas")
public class PersonaController {
	@Autowired
	PersonaService personaService;

	@Autowired
	ObjectMapper mapper;
	
	
	@ApiOperation(value = "Obtiene todas las personas")
	@GetMapping()
	public ArrayList <PersonaModel> getAllPersonas(){
		return personaService.getAllPersonas();
	}
	
	
	@ApiOperation(value = "Obtiene la persona a partir de su id")
	@GetMapping(path = "/{id}")
	public Optional<PersonaModel> getPersonaById(@PathVariable("id") Long id) {
		return this.personaService.getPersonaById(id);
	}
	
	
	@ApiOperation(value = "Obtiene estadísticas totalizadoras")
	@GetMapping("/estadisticas")
	public ObjectNode getEstadisticas() {
		ObjectNode json = this.personaService.getEstadisticas();
		return json;
	}
	
	
	@ApiOperation(value = "Graba una persona teniendo en cuenta condiciones especificas")
	@PostMapping()
	public String savePersona(@RequestBody PersonaModel persona) {
		return this.personaService.savePersonaWithFilters(persona);
	}
	

	@ApiOperation(value = "Elimina el registro persona a partir de su id")
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
