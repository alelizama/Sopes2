package practica1so2;

//@author lizama
 
import java.util.ArrayList;

public class Proceso 
{
    private String nombre;
    private ArrayList<Recurso> recursosNecesarios;
    private ArrayList<Recurso> recursos;
    private ArrayList<Recurso> recursosNoAsignados;
    private int estado;  //0 esperando - 1 ejecutandose
    
    public Proceso(String nombre)
    {
        this.nombre = nombre;
        recursos = new ArrayList();
        recursosNecesarios = new ArrayList();
        recursosNoAsignados = new ArrayList();
        estado = 1;
    }    
    
    public void agregarNecesario(Recurso recurso)
    {
        recursosNecesarios.add(recurso);
    }
    
    public void eliminarNecesario(Recurso recurso)
    {
        recursosNecesarios.remove(recurso);
    }
    
    public ArrayList<Recurso> getNecesarios()
    {
        return recursosNecesarios;
    }
    
    public ArrayList<Recurso> getNoAsignados()
    {
        return recursosNoAsignados;
    }
    
    public ArrayList<Recurso> getRecursos()
    {
        return recursos;
    }
    
    public void agregarNoAsignado(Recurso recurso)
    {
        recursosNoAsignados.add(recurso);
    }
    
    public void eliminarNoAsignado(Recurso recurso)
    {
        recursosNoAsignados.remove(recurso);
    }
    
    public void asignarRecurso(Recurso recurso)
    { 
        recurso.ocupado();
        recursos.add(recurso);
    }
    
    public void liberarRecurso(Recurso recurso)
    {   
        recurso.libre();
        recursos.remove(recurso);
    }
    
    public String liberarTodosRecursos(ArrayList<Recurso> listaRecursos)
    {   
        String temp = " liberó a ";
        for(int i = 0; i < listaRecursos.size(); i++)
        {
            Recurso recursoLista = listaRecursos.get(i);
            for(int j = 0; j < recursos.size(); j++)
            {
                Recurso recursoProceso = recursos.get(j);
                if(recursoProceso.getNombre().equals(recursoLista.getNombre()))
                {
                    recursos.remove(recursoProceso);
                    recursoLista.libre();
                    if(j == recursos.size())
                    {
                        temp = temp + recursoLista.getNombre()+ " ";
                    }
                    else
                    {
                        temp = temp + recursoLista.getNombre()+ " ,";
                    }
                }
            }
        }
        return temp;
    }
    
    public boolean existeRecurso(String recurso)
    {
        for(int i = 0; i < recursos.size(); i++)
        {
            String temp = recursos.get(i).getNombre();
            if(temp.equals(recurso))
            {
                return true;
            }                        
        }
        return false;
    }
    
    public boolean existeRecursoNecesario(String recurso)
    {
        for(int i = 0; i < recursosNecesarios.size(); i++)
        {
            String temp = recursosNecesarios.get(i).getNombre();
            if(temp.equals(recurso))
            {
                return true;
            }                        
        }
        return false;
    }
    
    public String getNombre()
    {
        return nombre;
    }
    
    public int getEstado()
    {
        return estado;
    }
    
    public void esperando()
    {
        estado = 0;
    }
    
    public void ejecutandose()
    {
        estado = 1;
    }
}
