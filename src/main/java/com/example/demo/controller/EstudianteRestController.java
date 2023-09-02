package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.repository.modelo.Estudiante;
import com.example.demo.security.AuthTokenFilter;
import com.example.demo.security.JwtUtils;
import com.example.demo.service.EstudianteTO;
import com.example.demo.service.IEstudianteService;

@RestController
@RequestMapping("/estudiantes")
@CrossOrigin
public class EstudianteRestController {
	private static final Logger LOG = LoggerFactory.getLogger(AuthTokenFilter.class);

	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private IEstudianteService estudianteService;
	
	@GetMapping()
	public ResponseEntity<Estudiante> consultar(@PathVariable Integer id) {
		Estudiante e = this.estudianteService.buscarPorId(id);
		return new ResponseEntity<>(e, null, 200);
	}
	
	@PostMapping()
	public void guardar(@RequestBody EstudianteTO estudianteTO) {
		//LOG.warn(estudia);
		this.jwtUtils.validateJwtToken(estudianteTO.getToken());
		Estudiante e = new Estudiante();
		e.setNombre(estudianteTO.getNombre());
		e.setApellido(estudianteTO.getApellido());
		e.setCedula(estudianteTO.getCedula());
		this.estudianteService.insertar(e);
	}
}
