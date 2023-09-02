package com.example.demo.repository;

import com.example.demo.repository.modelo.Estudiante;

public interface IEstudianteRepository {
	public Estudiante buscarPorId(Integer id);
	public void insertar(Estudiante estudiante);
}
