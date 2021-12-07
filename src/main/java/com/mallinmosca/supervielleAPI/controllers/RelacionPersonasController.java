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

@RestController
@RequestMapping("/personas")
public class RelacionPersonasController {
	
	@Autowired
	RelacionPersonasService relacionPersonasService;
	
	@Autowired
	PersonaService personaService;
	
	@GetMapping( path = "/relaciones/{id1}/{id2}")
	public String getRelacion(@PathVariable("id1") Long id1, @PathVariable("id2") Long id2) {
		
		if(id1 != null && id2 != null) {
			try {
				String res = "";
				PersonaModel oPadre;
				PersonaModel oPadre2;
				Long idAbuelo1 = Long.valueOf(0);
				Long idAbuelo2= Long.valueOf(0);
				Long idPadre1 = Long.valueOf(0);
				Long idPadre2= Long.valueOf(0);
				Optional<PersonaModel> persona1 = this.personaService.getPersonaById(id1);
				Optional<PersonaModel> persona2 = this.personaService.getPersonaById(id2);
				PersonaModel p1 = persona1.get();
				PersonaModel p2 = persona2.get();
				RelacionPersonasModel oRelacionId1 = this.relacionPersonasService.getByHijo(p1);
				RelacionPersonasModel oRelacionId2 = this.relacionPersonasService.getByHijo(p2);
				
				if(oRelacionId1 != null) {
					oPadre = oRelacionId1.getPadre();
					if(oPadre != null) {
						idPadre1 = oPadre.getId();
						RelacionPersonasModel oRelacion1 = this.relacionPersonasService.getByHijo(oPadre);
						if(oRelacion1 != null) {
							idAbuelo1 = oRelacion1.getPadre().getId();
						}
					}
				} else {
					res = "No se encontró relacion";
				}
				if(oRelacionId2 != null) {
					oPadre2 = oRelacionId2.getPadre();
					if(oPadre2 != null) {
						idPadre2 = oPadre2.getId();
						RelacionPersonasModel oRelacion2 = this.relacionPersonasService.getByHijo(oPadre2);
						if(oRelacion2 != null) {
							idAbuelo2 = oRelacion2.getPadre().getId();
						}
					}
				} else {
					res = "No se encontró relacion";
				}
					
				if(res.equals("")) {
					if(idPadre1 == idPadre2) {//SI SON HIJOS DEL MISMO PADRE
						res = "HERMAN@";
					}else if(idPadre1 == idAbuelo2) {//SI EL PADRE DE PERSONA1 ES EL ABUELO DE PERSONA2
						res = "TI@";
					}else if(idAbuelo1 > 0 && idAbuelo2 > 0 && idAbuelo1 == idAbuelo2) {//SI AMBAS TIENEN ABUELO Y ES EL MISMO
						res = "PRIM@";
					}else {
						res = "Las personas indicadas no guardan relación entre si.";
					}
				}
					
				
				return res;
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
