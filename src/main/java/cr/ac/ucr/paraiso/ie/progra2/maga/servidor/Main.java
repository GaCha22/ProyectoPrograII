package cr.ac.ucr.paraiso.ie.progra2.maga.servidor;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

class MiObservable {
    private String mensaje;
    private PropertyChangeSupport propertyChangeSupport;

    public MiObservable() {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public void agregarPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removerPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public void setMensaje(String nuevoMensaje) {
        String viejoMensaje = mensaje;
        mensaje = nuevoMensaje;
        propertyChangeSupport.firePropertyChange("mensaje", viejoMensaje, nuevoMensaje);
    }
}

class MiObservador implements PropertyChangeListener {
    @Override
    public void propertyChange(java.beans.PropertyChangeEvent evt) {
        System.out.println("Se ha producido un cambio en el mensaje: " + evt.getNewValue());
    }
}

public class Main {
    public static void main(String[] args) {
        MiObservable observable = new MiObservable();
        MiObservador observador = new MiObservador();

        observable.agregarPropertyChangeListener(observador);
        observable.setMensaje("Hola, mundo!");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        observable.setMensaje("Prueba");

    }

}
