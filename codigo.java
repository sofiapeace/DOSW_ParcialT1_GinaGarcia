import java.util.ArrayList;
import java.util.List;

interface Observer {
    void actualizar(String mensaje);
}

class Asistente implements Observer {
    private String nombre;
    private String email;

    public Asistente(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

    @Override
    public void actualizar(String mensaje) {
        System.out.println("[Email enviado a " + email + "]: " + mensaje);
    }
}

abstract class Evento {
    protected String titulo;
    protected String fechaHora;
    protected int cupoMaximo;
    protected List<Observer> inscritos = new ArrayList<>();

    public void inscribir(Observer asistente) {
        if (inscritos.size() < cupoMaximo) {
            inscritos.add(asistente);
        } else {
            System.out.println("Error: Cupo lleno para " + titulo);
        }
    }

    public void setFechaHora(String nuevaFecha) {
        this.fechaHora = nuevaFecha;
        notificar("El evento " + titulo + " ha cambiado su fecha a: " + nuevaFecha);
    }

    private void notificar(String mensaje) {
        for (Observer obs : inscritos) {
            obs.actualizar(mensaje);
        }
    }
}

class Conferencia extends Evento {
    public Conferencia(String titulo, int duracion, String rol) {
        if (!rol.equals("Profesor")) throw new RuntimeException("Solo profes crean conferencias");
        if (duracion > 180) throw new RuntimeException("Excede duración de 180 min");
        this.titulo = titulo;
        this.cupoMaximo = 50; 
    }
}


class EventoFactory {
    public static Evento crearEvento(String tipo, String titulo, int duracion, String rol) {
        if (tipo.equalsIgnoreCase("Conferencia")) {
            return new Conferencia(titulo, duracion, rol);
        }
        return null;
    }
}

public class Main {
    public static void main(String[] args) {
        try {
        
            Evento miConf = EventoFactory.crearEvento("Conferencia", "Arquitectura Limpia", 120, "Profesor");
            System.out.println("Evento creado exitosamente.");
            Asistente est1 = new Asistente("Pedro", "pedro@mail.escuelaing.edu.co");
            miConf.inscribir(est1);
            miConf.setFechaHora("20 de Octubre - 10:00 AM");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}