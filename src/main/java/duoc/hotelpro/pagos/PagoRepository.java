package duoc.hotelpro.pagos;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, Long> {

    List<Pago> findByReservaId(Long reservaId);
}