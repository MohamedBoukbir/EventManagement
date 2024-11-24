package emp.event_management_platform.serviceImpl;

import emp.event_management_platform.entities.AppPayment;
import emp.event_management_platform.repo.PaymentRepository;
import emp.event_management_platform.service.IPaymentService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class IPaymentServiceImpl implements IPaymentService {
    private PaymentRepository paymentRepository;
    @Override
    public void createPayment(AppPayment payment) {
        paymentRepository.save(payment);
    }

    @Override
    public List<AppPayment> getAllPayments() {
        return paymentRepository.findAll();
    }
}
