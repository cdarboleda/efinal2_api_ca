package com.example.demo.service;

import com.example.demo.repository.modelo.Estudiante;

public interface IEstudianteService {
	public Estudiante buscarPorId(Integer id);
	public void insertar(Estudiante estudiante);
}
