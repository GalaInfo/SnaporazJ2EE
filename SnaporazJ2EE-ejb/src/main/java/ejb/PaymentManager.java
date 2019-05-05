/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import com.mycompany.PaymentFacadeLocal;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author giovanna
 */
@Stateless
public class PaymentManager implements PaymentManagerLocal {

    @EJB
    private PaymentFacadeLocal paymentFacade;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public String addPayment(String id, String userId, long project, double amount){
        Map<String, Payment> responseMap = new HashMap<>();
        for(Payment p : paymentFacade.findAll())
            if(p.getId().equals(id))
                return "Errore: Transazione già effettuata";
        Payment p = new Payment();
        p.setId(id);
        p.setUserId(userId);
        p.setProject(project);
        p.setAmount(amount);
        paymentFacade.create(p);
        return "Transazione avvenuta con successo";
    }
}
