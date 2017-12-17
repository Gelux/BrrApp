package dam.sanpri.jesus.hermanosapp;

/**
 * Created by Jes on 05/12/2017.
 */

public class Hermano {
    private int id;
    private String nombre;
    private String pass;
    private String status;

    public Hermano(String nombre, String pass, String status) {
        this.nombre = nombre;
        this.pass = pass;
        this.status = status;
    }

    public Hermano(String nombre, String status){
        this.nombre = nombre;
        this.status = status;
    }

    public int getId(){ return id;};

    public void setId(int id) {this.id = id;}

    public String getNombre() {
        return nombre;
    }

    public String getPass() {
        return pass;
    }

    public String getStatus() {
        return status;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
