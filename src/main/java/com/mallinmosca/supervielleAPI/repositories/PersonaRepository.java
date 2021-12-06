package com.mallinmosca.supervielleAPI.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mallinmosca.supervielleAPI.models.PersonaModel;

@Repository
public interface PersonaRepository extends CrudRepository<PersonaModel, Long> {
	
	public abstract PersonaModel findByNroDoc(String nroDoc);
}
