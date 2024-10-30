package com.mi_proyecto.login_proyecto.repository;

import com.mi_proyecto.login_proyecto.model.Accion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccionRepository extends JpaRepository<Accion, Long> {
}