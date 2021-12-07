package com.mallinmosca.supervielleAPI.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mallinmosca.supervielleAPI.models.PersonaModel;
import com.mallinmosca.supervielleAPI.models.RelacionPersonasModel;
import com.mallinmosca.supervielleAPI.services.PersonaService;
import com.mallinmosca.supervielleAPI.services.RelacionPersonasService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/personas")
public class RelacionPersonasController {
	
	@Autowired
	RelacionPersonasService relacionPersonasService;
	
	@Autowired
	PersonaService personaService;
	
    @ApiOperation(value = "Obtiene el parentezco entre id1 e id2")
	@GetMapping( path = "/relaciones/{id1}/{id2}")
	public String getRelacion(@PathVariable("id1") Long id1, @PathVariable("id2") Long id2) {
		
		if(id1 != null && id2 != null) {
			try {
				return this.relacionPersonasService.getRelacionByIdsPersonas(id1, id2);
			}catch (Exception err) {
				System.out.println(err);
				return "Ocurrio un error al intentar obtener la relacion entre ambas personas";
			}
		}else {
			return "Faltan uno o mas parametros";
		}
		
	}

	@PostMapping(path = "/{id1}/padre/{id2}")
	public boolean saveRelacion(@PathVariable("id1") Long id1, @PathVariable("id2") Long id2) {
		try {
			Optional<PersonaModel> persona2 = this.personaService.getPersonaById(id2);
			Optional<PersonaModel> persona1 = this.personaService.getPersonaById(id1);
			PersonaModel padre = persona1.get();
			PersonaModel hijo = persona2.get();
			
			this.relacionPersonasService.saveRelacion(hijo, padre);
			return true;
		}catch(Exception err) {
			System.out.print(err);
			return false;
		}
		
	}
}
