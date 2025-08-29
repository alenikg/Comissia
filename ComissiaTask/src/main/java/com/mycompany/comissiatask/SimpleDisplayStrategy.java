
package com.mycompany.comissiatask;

/**
 *
 * @author elenagoncarova
 */
public class SimpleDisplayStrategy implements DisplayStrategy {
    @Override
    public String format(User user) {
        return user.getName() + " | " + user.getEmail();
    }    
    
}
