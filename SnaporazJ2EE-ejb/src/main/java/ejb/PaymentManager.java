/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import com.mycompany.PaymentFacadeLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author giovanna
 */
@Stateless
public class PaymentManager implements PaymentManagerLocal {

    @EJB
    private PaymentFacadeLocal paymentFacade;

    private final String key = "SnaporazKey";

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public String addPayment(String key, String id, String userId, long project, double amount) {

        //controlla che l'id della transazione non sia già presente nel DB
        if (!this.key.equals(key)) {
            return "Chiave errata";
        }

        //controlla che l'id della transazione non sia già presente nel DB
        for (Payment p : paymentFacade.findAll()) {
            if (p.getId().equals(id)) {
                return "Donazione già effettuata";
            }
        }

        //creazione della transazione e inserimento nel DB
        Payment p = new Payment();
        p.setId(id);
        p.setUserId(userId);
        p.setProject(project);
        p.setAmount(amount);
        paymentFacade.create(p);
        return "Donazione avvenuta con successo";
    }

    @Override
    public List<Payment> getPayments() {
        return paymentFacade.findAll();
    }

    @Override
    public void clearPayments() {
        for (Payment p : paymentFacade.findAll()) {
            paymentFacade.remove(p);
        }
    }

}
