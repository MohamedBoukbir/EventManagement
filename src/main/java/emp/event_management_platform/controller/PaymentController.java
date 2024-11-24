package emp.event_management_platform.controller;


import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import emp.event_management_platform.entities.AppPayment;
import emp.event_management_platform.service.IPaymentService;
import emp.event_management_platform.service.IPaypalService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class PaymentController {
    private final IPaypalService paypalService;
    private IPaymentService paymentService;
    @GetMapping("/payment")
    public  String index(){

        return "/payments/payment";
    }

    @GetMapping("/admin/payments")
    public String getAllpayment(Model model){
        List<AppPayment> payments =paymentService.getAllPayments();
        model.addAttribute("payments", payments);
        return "/admin/payments";
    }

//    @GetMapping("/payment/create")
//    public RedirectView  createPayment(
//            @RequestParam("method") String method,
//            @RequestParam("amount")  String amount,
//            @RequestParam("acurrency") String acurrency,
//            @RequestParam("description") String description
//    ){
//        try {
//            String cancelUrl="http://localhost:8089/payment/cancel";
//            String successUrl="http://localhost:8089/payment/success";
//            Payment payment = paypalService.createPayment(Double.valueOf(amount),acurrency,
//                    method,"sale",description
//                    ,cancelUrl,successUrl);
//
//     for (Links links: payment.getLinks()){
//         if (links.getRel().equals("approval_url")){
//             return  new RedirectView(links.getHref());
//
//         }
//     }
//
//        }catch (PayPalRESTException p){
//          log.error("Error occurred ",p);
//        }
//        return  new RedirectView("/payment/error");
//    }
//
//    @GetMapping("/payment/success")
//    public String paymentSuccess(
//            @RequestParam("paymentId") String paymentId,
//            @RequestParam("PayerID" ) String  payerId
//            ){
//
//        try{
//            Payment payment =paypalService.executePayment(paymentId,payerId);
//            if(payment.getState().equals("approved")){
//
//                return "/payments/paymentSuccess";
//            }
//
//        }catch (PayPalRESTException p){
//            log.error("Error occurred ",p);
//        }
//        return "/payments/paymentSuccess";
//    }
//
//    @GetMapping("/payment/cancel")
//    public String paymentCancel(){
//        return "/payments/paymentCancel";
//    }
//
//    @GetMapping("/payment/error")
//    public String paymentError(){
//        return "/payments/paymentError";
//    }

}
