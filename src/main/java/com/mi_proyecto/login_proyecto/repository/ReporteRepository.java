package com.mi_proyecto.login_proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mi_proyecto.login_proyecto.model.Reporte;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long>{

}
