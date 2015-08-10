package practica1so2;

//@author lizama

public class Recurso 
{
    private String nombre;
    private int estado; // 0 libre - 1 ocupado
    
    public Recurso(String nombre)
    {
        this.nombre = nombre;
        estado = 0;
    }
    
    public void ocupado()
    {
        estado = 1;
    }
    
    public void libre()
    {
        estado = 0;
    }
    
    public int getEstado()
    {
        return estado;
    }
    
    public String getNombre()
    {
        return nombre;
    }
}
