package duoc.hotelpro.habitaciones;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {
    boolean existsByNumero(String numero);
}