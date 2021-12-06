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
	
	@GetMapping( path = "/relaciones/{id1}/{id2}")
	public String getRelacion(@PathVariable("id1") Long id1, @PathVariable("id2") Long id2) {
		String relacion;
		Optional<PersonaModel> persona1 = this.personaService.getPersonaById(id1);
		Optional<PersonaModel> persona2 = this.personaService.getPersonaById(id2);
		PersonaModel p1 = persona1.get();
		PersonaModel p2 = persona2.get();
		Long idAbuelo1 = Long.valueOf(0);
		Long idAbuelo2= Long.valueOf(0);
		boolean ok = true;
		if(p1.getIdPadre() != null) {
			Optional<PersonaModel> padre = this.personaService.getPersonaById(p1.getIdPadre());
			PersonaModel padre1 = padre.get();
			idAbuelo1 = padre1.getIdPadre();
		}else {
			ok = false;
		}
		if(p2.getIdPadre() != null) {
			Optional<PersonaModel> padre2 = this.personaService.getPersonaById(p2.getIdPadre());
			PersonaModel oPadre2 = padre2.get();
			idAbuelo2 = oPadre2.getIdPadre();
		}else {
			ok = false;
		}
		
		if(ok) {
			if(p1.getIdPadre() == p2.getIdPadre()) {//SI SON HIJOS DEL MISMO PADRE
				relacion = "HERMAN@";
			}else if(idAbuelo1 == p2.getIdPadre()) {//SI EL ABUELO DE PERSONA1 ES EL PADRE DE PERSONA2
				relacion = "TI@";
			}else if(idAbuelo1 == idAbuelo2) {
				relacion = "PRIM@";
			}else {
				relacion = "Las personas indicadas no guardan relación entre si.";
			}
		}else {
			relacion = "Las personas indicadas no guardan relación entre si.";
		}
		
		
		return relacion;
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
	
	@PostMapping(path = "/personas/{id1}/padre/{id2}")
	public boolean saveRelacion(@PathVariable("id1") Long id1, @PathVariable("id2") Long id2) {
		try {
			Optional<PersonaModel> persona2 = this.personaService.getPersonaById(id2);
			Optional<PersonaModel> persona1 = this.personaService.getPersonaById(id1);
			PersonaModel padre = persona1.get();
			PersonaModel hijo = persona2.get();
			
			this.personaService.saveIdPadre(hijo, padre);
			return true;
		}catch(Exception err) {
			System.out.print(err);
			return false;
		}
		
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
