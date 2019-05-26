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

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public String addPayment(String id, String userId, long project, double amount) {

        //controlla che l'id della transazione non sia già presente nel DB
        for (Payment p : paymentFacade.findAll()) {
            if (p.getId().equals(id)) {
                return "Errore: Donazione già effettuata";
            }
        }

        //chiamata al servizio REST di SnaporazSpring per controllo di errori e aggiornamento del progetto
        Client client = ClientBuilder.newClient();
        final String url = "http://localhost:8080/SnaporazSpring/donate";
        WebTarget target = client.target(url);
        Form form = new Form();
        form.param("transactionId", id);
        form.param("project", "" + project);
        form.param("sum", "" + amount);
        form.param("idTokenString", userId);
        JsonObject response = target.request(MediaType.APPLICATION_JSON).post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), JsonObject.class);

        //creazione della transazione e inserimento nel DB in caso di successo, segnalazione dell'errore altrimenti
        if (response.getBoolean("success", false)) {
            Payment p = new Payment();
            p.setId(id);
            p.setUserId(userId);
            p.setProject(project);
            p.setAmount(amount);
            paymentFacade.create(p);
            return "Transazione avvenuta con successo";
        } else {
            return response.getString("response", "Donazione fallita");
        }
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
