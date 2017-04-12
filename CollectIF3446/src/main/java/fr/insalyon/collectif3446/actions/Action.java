/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.collectif3446.actions;

import javax.servlet.http.HttpServletRequest;
import metier.service.ServiceMetier;

/**
 *
 * @author jcharlesni
 */
public abstract class Action {
    
    ServiceMetier service = new ServiceMetier();
    
    public abstract void execute(HttpServletRequest request);
    
    
}
