package com.mallinmosca.supervielleAPI.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class Welcome {
	
	@GetMapping
	public String welcome() {
		return "<h1>Bienvenidos a la API desarrollada por Mallin para la prueba técnica solicitada</h1>";
	}
}
