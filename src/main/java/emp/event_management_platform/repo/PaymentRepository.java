package emp.event_management_platform.repo;

import emp.event_management_platform.entities.AppPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<AppPayment, Long> {
}
