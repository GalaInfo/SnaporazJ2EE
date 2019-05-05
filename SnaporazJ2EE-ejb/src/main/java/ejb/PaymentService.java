/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.ejb.EJB;
import javax.jws.WebService;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author Marco
 */
@WebService(serviceName = "PaymentService")
@Stateless()
public class PaymentService {
    @EJB
    private PaymentManagerLocal ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Web Service > Add Operation"

    @WebMethod(operationName = "addPayment")
    public String addPayment(@WebParam(name = "id") String id, @WebParam(name = "userId") String userId, @WebParam(name = "project") long project, @WebParam(name = "amount") double amount) {
        return ejbRef.addPayment(id, userId, project, amount);
    }
}