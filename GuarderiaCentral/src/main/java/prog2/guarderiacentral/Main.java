
package prog2.guarderiacentral;


import guarderiaCentral.Conections.BBDDConector;
import guarderiaCentral.frontend.Menu;

public class Main {

    public static void main(String[] args) {
        BBDDConector.Conectar();
        Menu menu = new Menu();
        menu.IniciarMenu();
    }
    
}
