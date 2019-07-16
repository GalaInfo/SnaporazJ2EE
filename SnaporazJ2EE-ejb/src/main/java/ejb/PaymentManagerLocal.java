/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author giovanna
 */
@Local
public interface PaymentManagerLocal {

    String addPayment(String key, String id, String userId, long project, double amount);
    
    List<Payment> getPayments();
    
    void clearPayments();
}
