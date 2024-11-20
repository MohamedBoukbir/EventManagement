package emp.event_management_platform.serviceImpl;

import emp.event_management_platform.entities.AppPayment;
import emp.event_management_platform.repo.PaymentRepository;
import emp.event_management_platform.service.IPaymentService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class IPaymentServiceImpl implements IPaymentService {
    private PaymentRepository paymentRepository;
    @Override
    public void createPayment(AppPayment payment) {
        paymentRepository.save(payment);
    }
}
