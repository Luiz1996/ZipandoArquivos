/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import controller.ZipController;
import java.io.IOException;

/**
 *
 * @author Luiz Flávio
 */
public class Main {
    public static void main(String args[]){
       try {
           ZipController.compactarParaZip();
       } catch (IOException e) {
           System.out.println("Erro no processo:\n\n".concat(e.getMessage().trim()));
       }
   }
}
