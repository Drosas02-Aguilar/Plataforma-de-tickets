package com.tickets.jira.DAO;

import com.tickets.jira.Entity.Notificaciones;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface INotificaciones extends JpaRepository<Notificaciones, Integer>{
    
  List<Notificaciones> findByUsuarioidOrderByFechacreacionDesc(Integer usuarioid);

    List<Notificaciones> findByUsuarioidAndLeida(Integer usuarioid, int leida);
}
