package com.mallinmosca.supervielleAPI.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mallinmosca.supervielleAPI.models.PersonaModel;
import com.mallinmosca.supervielleAPI.models.RelacionPersonasModel;

@Repository
public interface RelacionPersonasRepository extends CrudRepository<RelacionPersonasModel, Long> {

	
	public abstract RelacionPersonasModel findByHijo(PersonaModel idHijo);
}
