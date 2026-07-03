package duoc.hotelpro.consumos;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsumoRepository extends JpaRepository<Consumo, Long> {

    List<Consumo> findByReservaId(Long reservaId);
}