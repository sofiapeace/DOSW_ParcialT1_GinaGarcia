import java.util.ArrayList;
import java.util.List;
import java.util.*;


interface Notificable {
    void recibirAlerta(String mensaje);
}

class Asistente implements Notificable {
    private String nombre;
    private String correo;
    private String rol; // Estudiante o Profesor

    public Asistente(String nombre, String correo, String rol) {
        // Validación RNF: Dominio institucional
        if (!correo.endsWith("@escuelaing.edu.co") && !correo.endsWith("@mail.escuelaing.edu.co")) {
            throw new IllegalArgumentException("Correo no institucional: " + correo);
        }
        this.nombre = nombre;
        this.correo = correo;
        this.rol = rol;
    }

    @Override
    public void recibirAlerta(String mensaje) {
        System.out.println("[NOTIFICACIÓN ENVIADA A: " + correo + "]: Hola " + nombre + ", " + mensaje);
    }

    public String getRol() { return rol; }
}
abstract class Evento {
    protected String titulo;
    protected String fechaHora;
    protected int duracion;
    protected int cupoMaximo;
    protected String creadorRol;
    protected List<Notificable> inscritos = new ArrayList<>();

    public void inscribir(Asistente asistente) {
        if (inscritos.size() >= cupoMaximo) {
            System.out.println("❌ Error: Cupo lleno para el evento " + titulo);
            return;
        }
        inscritos.add(asistente);
        System.out.println("✅ Inscripción exitosa: " + asistente.getRol() + " registrado en " + titulo);
    }

    public void setFechaHora(String nuevaFechaHora) {
        this.fechaHora = nuevaFechaHora;
        notificarInscritos("La fecha/hora del evento '" + titulo + "' ha cambiado a: " + nuevaFechaHora);
    }

    private void notificarInscritos(String mensaje) {
        for (Notificable inscrito : inscritos) {
            inscrito.recibirAlerta(mensaje);
        }
    }

    public abstract void mostrarInfo();
}

class Conferencia extends Evento {
    public Conferencia(String titulo, String fecha, int duracion, int cupo, String rolCreador) {
        if (!rolCreador.equals("Profesor")) throw new SecurityException("Solo profesores crean Conferencias");
        if (duracion > 180) throw new IllegalArgumentException("Conferencia excede 180 min");
        
        this.titulo = titulo;
        this.fechaHora = fecha;
        this.duracion = duracion;
        this.cupoMaximo = cupo;
        this.creadorRol = rolCreador;
    }

    @Override
    public void mostrarInfo() {
        System.out.println("[TIPO: Conferencia] " + titulo + " | Duración: " + duracion + "min");
    }
}

class Taller extends Evento {
    public Taller(String titulo, String fecha, int duracion, int cupo, String rolCreador) {
        if (!rolCreador.equals("Profesor") && !rolCreador.equals("Administrativo")) 
            throw new SecurityException("Rol no autorizado para crear Talleres");
        if (duracion > 240) throw new IllegalArgumentException("Taller excede 240 min");

        this.titulo = titulo;
        this.fechaHora = fecha;
        this.duracion = duracion;
        this.cupoMaximo = cupo;
    }

    @Override
    public void mostrarInfo() {
        System.out.println("[TIPO: Taller] " + titulo + " | Cupo: " + cupoMaximo);
    }
}

class EventoFactory {
    public static Evento crearEvento(String tipo, String titulo, String fecha, int duracion, int cupo, String rolCreador) {
        switch (tipo.toUpperCase()) {
            case "CONFERENCIA":
                return new Conferencia(titulo, fecha, duracion, cupo, rolCreador);
            case "TALLER":
                return new Taller(titulo, fecha, duracion, cupo, rolCreador);
            default:
                throw new IllegalArgumentException("Tipo de evento desconocido");
        }
    }
}

public class EventSyncApp {
    public static void main(String[] args) {
        System.out.println("--- INICIO DE PRUEBAS EVENTSYNC ---\n");

        try {
            // 1. Prueba Factory: Crear una Conferencia válida (Creada por Profesor)
            Evento miConf = EventoFactory.crearEvento("CONFERENCIA", "Clean Code", "2023-11-10 08:00", 120, 2, "Profesor");
            miConf.mostrarInfo();

            // 2. Prueba de Inscripción
            Asistente est1 = new Asistente("Juan Perez", "juan.perez@mail.escuelaing.edu.co", "Estudiante");
            Asistente prof1 = new Asistente("Dr. Smith", "smith@escuelaing.edu.co", "Profesor");
            
            miConf.inscribir(est1);
            miConf.inscribir(prof1);

            // 3. Prueba de Cupo Máximo (Intentar inscribir un tercero con cupo 2)
            Asistente est2 = new Asistente("Maria G", "maria@mail.escuelaing.edu.co", "Estudiante");
            miConf.inscribir(est2);

            // 4. Prueba de Observer: Cambio de fecha notifica a los inscritos
            System.out.println("\n--- ACTUALIZACIÓN DE CRONOGRAMA ---");
            miConf.setFechaHora("2023-11-12 10:00 AM");

            // 5. Prueba Regla de Negocio: Error por rol inválido
            System.out.println("\n--- PRUEBA ERROR DE ROL ---");
            Evento fail = EventoFactory.crearEvento("CONFERENCIA", "Hack ilegal", "2023-12-01", 60, 10, "Estudiante");

        } catch (Exception e) {
            System.out.println("⚠️ ERROR VALIDADO: " + e.getMessage());
        }

        System.out.println("\n--- FIN DE PRUEBAS ---");
    }
}