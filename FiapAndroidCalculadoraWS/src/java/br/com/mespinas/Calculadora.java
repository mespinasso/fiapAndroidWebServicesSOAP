/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mespinas;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;

/**
 *
 * @author logonrm
 */
@WebService(serviceName = "Calculadora")
@Stateless()
public class Calculadora {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "Soma")
    public int Soma(@WebParam(name = "numero1") int numero1, @WebParam(name = "numero2") int numero2) {//TODO write your implementation code here:
        return numero1 + numero2;
    }
}
