package com.mallinmosca.supervielleAPI.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mallinmosca.supervielleAPI.models.PersonaModel;
import com.mallinmosca.supervielleAPI.models.RelacionPersonasModel;
import com.mallinmosca.supervielleAPI.repositories.RelacionPersonasRepository;

@Service
public class RelacionPersonasService {

	@Autowired
	RelacionPersonasRepository relacionPersonasRepository;
	@Autowired
	PersonaService personaService;
		
	public RelacionPersonasModel saveRelacion(PersonaModel oHijo, PersonaModel oPadre) {
		RelacionPersonasModel relacion = new RelacionPersonasModel();
		relacion.setHijo(oHijo);
		relacion.setPadre(oPadre);
		relacionPersonasRepository.save(relacion);
		
		return relacion;
	}
	
	public RelacionPersonasModel getByHijo(PersonaModel idHijo) {
		return this.relacionPersonasRepository.findByHijo(idHijo);
	}
	
	public String getRelacionByIdsPersonas(Long id1, Long id2) {
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
		RelacionPersonasModel oRelacionId1 = this.getByHijo(p1);
		RelacionPersonasModel oRelacionId2 = this.getByHijo(p2);
		
		if(oRelacionId1 != null) {
			oPadre = oRelacionId1.getPadre();
			if(oPadre != null) {
				idPadre1 = oPadre.getId();
				RelacionPersonasModel oRelacion1 = this.getByHijo(oPadre);
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
				RelacionPersonasModel oRelacion2 = this.getByHijo(oPadre2);
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
				res = "Las personas indicadas no guardan poseen ninguna relación de las indicadas entre si.";
			}
		}
			
		return res;
	}
}
