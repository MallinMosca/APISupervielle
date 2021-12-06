package com.mallinmosca.supervielleAPI.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name= "relacion_personas")
public class RelacionPersonasModel {

	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long idRelacion;
    @ManyToOne
    @JoinColumn(name = "id_hijo", referencedColumnName = "id")
	private PersonaModel hijo;
    
    @ManyToOne
    @JoinColumn(name = "id_padre", referencedColumnName = "id")	
    private PersonaModel padre;

	public Long getIdRelacion() {
		return idRelacion;
	}

	public void setIdRelacion(Long idRelacion) {
		this.idRelacion = idRelacion;
	}

	public PersonaModel getHijo() {
		return hijo;
	}

	public void setHijo(PersonaModel hijo) {
		this.hijo = hijo;
	}

	public PersonaModel getPadre() {
		return padre;
	}

	public void setPadre(PersonaModel padre) {
		this.padre = padre;
	}
}
