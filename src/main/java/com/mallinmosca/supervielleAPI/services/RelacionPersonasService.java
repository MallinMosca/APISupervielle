package com.mallinmosca.supervielleAPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mallinmosca.supervielleAPI.models.PersonaModel;
import com.mallinmosca.supervielleAPI.models.RelacionPersonasModel;
import com.mallinmosca.supervielleAPI.repositories.RelacionPersonasRepository;

@Service
public class RelacionPersonasService {

	@Autowired
	RelacionPersonasRepository relacionPersonasRepository;
		
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
}
