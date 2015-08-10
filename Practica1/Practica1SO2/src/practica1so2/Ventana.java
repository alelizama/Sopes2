package practica1so2;
 
//@author lizama 

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.BorderFactory; 
import javax.swing.ButtonGroup;
import javax.swing.JButton; 
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel; 
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea; 
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
 
public class Ventana extends JFrame implements ItemListener, ActionListener
{
    private JTextArea comando;
    private JTextArea log;
    private JButton empezar, pausar;
    private JRadioButton negacion, deteccion, def; 
    private ArrayList<Proceso> listaProcesos;
    private ArrayList<Recurso> listaRecursos;    
    private ArrayList<Proceso> listaCiclo;    
    private boolean ciclo;
    private boolean banderaNegacion, banderaDeteccion; 
    
    public Ventana()
    {     
        ciclo = false;
        banderaNegacion = false;
        banderaDeteccion = false;
        listaCiclo = new ArrayList();
        listaProcesos = new ArrayList();
        listaRecursos = new ArrayList();
        JLabel comandoLabel = new JLabel("Comandos");                
        comando = new JTextArea(); 
        log = new JTextArea(); 
        log.setEditable(false);
        
        empezar = new JButton("Empezar");
        pausar = new JButton("Reset");
        negacion = new JRadioButton("Negación");
        deteccion = new JRadioButton("Detección");
        def = new JRadioButton("Default");  
                
        this.setTitle("Practica 1");
        this.setLayout(null);
        this.setSize(410,505);
        
        JPanel panelLog = new JPanel();
        panelLog.setLayout(null);        
        panelLog.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        
        JPanel panel = new JPanel();
        panel.setLayout(null);        
        panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        
        JScrollPane comandoScroll = new JScrollPane(comando); 
        comandoScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        comandoScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        JScrollPane logScroll = new JScrollPane(log); 
        logScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        logScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        this.add(panel);
        panel.setBounds(0,0, 410,200);
        panel.add(comandoLabel);
        comandoLabel.setBounds(10, 10, 100, 20);
        panel.add(comandoScroll);
        comandoScroll.setBounds(10, 35, 390, 155);
        
        this.add(empezar);
        empezar.setBounds(5, 205, 100, 50);
        this.add(pausar);
        pausar.setBounds(110, 205, 100, 50);
        this.add(negacion);
        negacion.setBounds(215, 200, 100,30);
        this.add(deteccion);
        deteccion.setBounds(315, 200, 100,30);
        this.add(def);
        def.setBounds(215, 230, 100,30);
        def.setSelected(true);
        
        this.add(panelLog);
        panelLog.setBounds(0, 260, 410, 210);
        panelLog.add(logScroll);
        logScroll.setBounds(0,0,410,210);
        log.append("_____Log");
        
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        this.setLocationRelativeTo(null);
                
        empezar.addActionListener(this);
        pausar.addActionListener(this);    
        
        ButtonGroup grupo = new ButtonGroup();
        grupo.add(negacion);
        grupo.add(deteccion);
        grupo.add(def);
        negacion.addItemListener(this);
        deteccion.addItemListener(this);
        def.addItemListener(this);
    }    

    public void tiempos()
    {
        String texto = comando.getText();  
        String[] tiempos = texto.split("\n");
        for(int i = 0; i < tiempos.length; i++)
        {
            String nombreTiempo = tiempos[i].substring(0, 2);
            tiempos[i] = tiempos[i].substring(2);
            tiempos[i] = tiempos[i].substring(1, tiempos[i].length() - 1);
            
            log.append("\n" + "_____"+ nombreTiempo); 
            String[] procesos = tiempos[i].split(", ");
            procesos(procesos);
            log.append("\n" + "_____Fin " + nombreTiempo);
        }
    }
    
    public void procesos(String[] procesos)
    {            
        for(int i = 0; i < procesos.length; i++)                 
        {    
            if(banderaNegacion == false || listaProcesos.isEmpty())
            {       
                String nombreProceso = procesos[i].substring(0, 2);
                Proceso proceso = buscarProceso(nombreProceso);
                if(proceso == null)
                {
                    proceso = new Proceso(nombreProceso);
                }
                procesos[i] = procesos[i].substring(2);
                procesos[i] = procesos[i].substring(1, procesos[i].length() - 1);

                String[] splitTemp = procesos[i].split("\\|");
                String[] recursos = splitTemp[0].split(",");

                if(splitTemp[0].equals("f") | splitTemp[0].equals("F"))
                {
                    log.append("\n       "+ nombreProceso + " finalizado"); 
                    log.append("\n       "+ nombreProceso + proceso.liberarTodosRecursos(listaRecursos));                        
                    listaProcesos.remove(proceso);
                    recursoLiberado();                
                }
                else
                {                
                    recursos(nombreProceso, proceso, splitTemp,recursos, false);
                }

                if(buscarProceso(nombreProceso) == null)
                {
                    listaProcesos.add(proceso);
                }
            }
            else
            {
                String nombreProceso = procesos[i].substring(0, 2);
                Proceso proceso = buscarProceso(nombreProceso);
                if(proceso == null)
                {
                    proceso = new Proceso(nombreProceso);
                } 
                procesos[i] = procesos[i].substring(2);
                procesos[i] = procesos[i].substring(1, procesos[i].length() - 1);
                
                String[] splitTemp = procesos[i].split("\\|");
                String[] recursos = splitTemp[0].split(",");

                if(splitTemp[0].equals("f") | splitTemp[0].equals("F"))
                {
                    //log.append("\n       "+ nombreProceso + proceso.liberarTodosRecursos(listaRecursos));
                }
                else
                {                
                    recursos(nombreProceso, proceso, splitTemp,recursos, true);
                }
                listaProcesos.add(proceso);
                
                ciclo = false;
                listaCiclo = new ArrayList();
                existeCiclo(proceso); 
                if(ciclo == true)
                {
                    log.append("\n        "+ nombreProceso + " se suspende porque creará interbloqueo");                     
                    listaProcesos.remove(proceso);
                }
                else
                {                       
                    listaProcesos.remove(proceso);
                    proceso = buscarProceso(nombreProceso);
                    if(proceso == null)
                    {
                        proceso = new Proceso(nombreProceso);
                    } 

                    if(splitTemp[0].equals("f") | splitTemp[0].equals("F"))
                    {                        
                        log.append("\n       "+ nombreProceso + " finalizado"); 
                        log.append("\n       "+ nombreProceso + proceso.liberarTodosRecursos(listaRecursos));                        
                        listaProcesos.remove(proceso);
                        recursoLiberado();                
                    }
                    else
                    {                
                        recursos(nombreProceso, proceso, splitTemp,recursos, false);
                    }

                    if(buscarProceso(nombreProceso) == null)
                    {
                        listaProcesos.add(proceso);
                    }                 
                }   
            }
        }
    }    
    
    private void recursos(String nombreProceso, Proceso proceso, String[] splitTemp, String[] recursos, boolean prueba) 
    {
        for(int i = 0; i < recursos.length; i++)         
        {                    
            String nombreRecurso = recursos[i];
            Recurso recurso = buscarRecurso(nombreRecurso);
            if(recurso == null)
            { 
                recurso = new Recurso(nombreRecurso);
            }
            if(proceso.existeRecurso(nombreRecurso) == false)
            {
                if(recurso.getEstado() == 0)
                {
                    if(splitTemp[1].equals("S"))
                    {   
                        if(proceso.getEstado() == 1)
                        {
                            proceso.asignarRecurso(recurso);       
                            if(prueba == false)
                            {
                                log.append("\n"+ "       Se asignó "+ nombreRecurso + " a " + nombreProceso);
                                log.append("\n"+ "       " + nombreProceso + " se está ejecutando");                                
                            }
                            else 
                            {
                                recurso.libre();
                            }
                            proceso.ejecutandose();
                        }
                        else
                        {
                            proceso.agregarNoAsignado(recurso);  
                            
                            ArrayList<Recurso> lista = proceso.getNecesarios();
                            for(int j = 0; j < lista.size(); j++ )
                            {
                                Recurso recursoTemp = lista.get(j);
                                if(prueba == false)
                                {
                                    log.append("\n"+ "       "+ nombreProceso+" en espera hasta que se libere "+recursoTemp.getNombre());
                                }
                            }
                        }
                    }
                }
                else
                {
                    proceso.esperando();                     
                    if(proceso.existeRecursoNecesario(nombreRecurso) == false)
                    {
                        proceso.agregarNecesario(recurso);
                    }
                    
                    ArrayList<Recurso> lista = proceso.getNecesarios();
                    for(int j = 0; j < lista.size(); j++ )
                    {
                        Recurso recursoTemp = lista.get(j);
                        if(prueba == false)
                        {
                            log.append("\n"+ "       "+ nombreProceso+" en espera hasta que se libere "+recursoTemp.getNombre());
                        }
                    }
                }
                if(splitTemp[1].equals("L"))
                {
                    if(proceso.existeRecurso(recurso.getNombre()) == true)
                    {                        
                        proceso.liberarRecurso(recurso);
                        if(prueba == false)
                        {
                            log.append("\n"+ "       "+ nombreProceso + " liberó a " + nombreRecurso);
                            recursoLiberado();
                        }
                    }
                    else
                    {
                        if(prueba == false)
                        {
                            log.append("\n"+ "       "+ nombreProceso + "no tiene asignado a " + nombreRecurso);
                        }
                    }
                        
                }
            }
            else
            {                
                if(splitTemp[1].equals("L"))
                {
                   if(proceso.existeRecurso(recurso.getNombre()) == true)
                    {                        
                        proceso.liberarRecurso(recurso);
                        if(prueba == false)
                        {
                            log.append("\n"+ "       "+ nombreProceso + " liberó a " + nombreRecurso);
                            recursoLiberado();
                        }
                    }
                    else
                    {
                        if(prueba == false)
                        {
                            log.append("\n"+ "       "+ nombreProceso + "no tiene asignado a " + nombreRecurso);
                        }
                    }
                }
                else
                {
                    if(prueba == false)
                    {
                        log.append("\n"+ "       Ya tiene asignado el recurso "+ nombreRecurso);
                    }
                }
            }
            if(buscarRecurso(nombreRecurso) == null)
            {
                listaRecursos.add(recurso);
            }            
        }
    }
    
    public void recursoLiberado()
    {        
        boolean bandera;
        log.append("\n      ------------------Se liberó un recurso"); 
        for(int i = 0; i < listaProcesos.size(); i++)
        {            
            Proceso proceso = listaProcesos.get(i);
            if(proceso.getEstado() == 0)
            {
                for(int j = 0; j < proceso.getNecesarios().size(); j++)
                {
                    bandera = true;
                    Recurso necesario = proceso.getNecesarios().get(j);
                    for(int k = 0; k < listaRecursos.size(); k++)
                    {
                        Recurso recursoLista = listaRecursos.get(k);
                        if(bandera == false)
                        {
                            break;
                        }
                        if(necesario.equals(recursoLista))
                        {
                            if(recursoLista.getEstado() == 0)
                            {
                                proceso.asignarRecurso(recursoLista);
                                proceso.eliminarNecesario(recursoLista);
                                log.append("\n"+ "       Se asignó "+ recursoLista.getNombre() + " a " + proceso.getNombre());
                            }
                            else
                            {
                                bandera = false;
                                break;
                            }
                        }
                    }            
                }
                for(int j = 0; j < proceso.getNoAsignados().size(); j++)
                {
                    bandera = true;
                    Recurso necesario = proceso.getNoAsignados().get(j);
                    for(int k = 0; k < listaRecursos.size(); k++)
                    {
                        Recurso recursoLista = listaRecursos.get(k);
                        if(bandera == false)
                        {
                            break;
                        }
                        if(necesario.equals(recursoLista))
                        {
                            if(recursoLista.getEstado() == 0)
                            {
                                proceso.asignarRecurso(recursoLista);
                                proceso.eliminarNoAsignado(recursoLista);
                                log.append("\n"+ "       Se asignó "+ recursoLista.getNombre() + " a " + proceso.getNombre());
                            }
                            else
                            {
                                bandera = false;
                                break;
                            }
                        }
                    }            
                }
            }
        }
        for(int i = 0; i < listaProcesos.size(); i++)
        {
            Proceso proceso = listaProcesos.get(i);
            if(proceso.getNecesarios().isEmpty())
            {
                proceso.ejecutandose();
                log.append("\n"+ "       " + proceso.getNombre() + " se está ejecutando");
            }
            else
            {
                proceso.esperando();
                for(int j = 0; j < proceso.getNecesarios().size(); j++)
                {
                    Recurso recurso = proceso.getNecesarios().get(j);
                    log.append("\n"+ "       "+ proceso.getNombre()+" en espera hasta que se libere "+recurso.getNombre());
                }
            }
        }
        log.append("\n      ---------------------------------"); 
    }
      
    public Recurso buscarRecurso(String nombreRecurso)
    {
        for(int i = 0; i < listaRecursos.size(); i++)
        {
            Recurso temp = listaRecursos.get(i);
            if(temp.getNombre().equals(nombreRecurso))
            {
                return temp;
            }
        }
        return null;
    }
    
    public Proceso buscarProceso(String nombreProceso)
    {
        for(int i = 0; i < listaProcesos.size(); i++)
        {
            Proceso temp = listaProcesos.get(i);
            if(temp.getNombre().equals(nombreProceso))
            {
                return temp;
            }
        }
        return null;
    }
    
    public void existeCiclo(Proceso proceso)
    {
        if(proceso != null)
        {
            if(listaCiclo.contains(proceso))
            {
                ciclo = true; 
            }
            else
            {
                listaCiclo.add(proceso);                
                for(int i = 0; i < proceso.getRecursos().size(); i++)
                {
                    Recurso recurso = proceso.getRecursos().get(i);
                    existeCiclo(buscarSigProceso(recurso));
                }
            }
        }
    } 
    
    public Proceso buscarSigProceso(Recurso recurso)
    {
        for(int i = 0; i < listaProcesos.size(); i++)
        {
            Proceso proceso = listaProcesos.get(i);
            if(proceso.getEstado() == 0)
            {
                for(int j = 0; j < proceso.getNecesarios().size(); j++)
                {
                    Recurso r = proceso.getNecesarios().get(j);
                    if(r.equals(recurso))
                    {
                        return proceso;
                    }
                }
            }
        }
        return null;
    }
    
    public void imprimir()
    {
        for(int i = 0; i < listaProcesos.size(); i++)
        {
            Proceso p = listaProcesos.get(i);
            String nombreP = p.getNombre();
            System.out.println("///////////////////////////////////");
            System.out.println("///////////////////////////////////");
            System.out.println("Nombre Procesos: "+ nombreP );            
            System.out.println("Recursos");
            for(int j = 0; j < p.getRecursos().size(); j++)
            {
                Recurso r = p.getRecursos().get(j);
                System.out.println("Recurso: " + r.getNombre());
            }
            System.out.println("----------------------------------");
            System.out.println("Recurso Necesarios");
            for(int j = 0; j < p.getNecesarios().size(); j++)
            {                
                Recurso r = p.getNecesarios().get(j);
                System.out.println("Recurso: " + r.getNombre());
            }
            System.out.println("----------------------------------");
            System.out.println("Recurso No Asignados ");
            for(int j = 0; j < p.getNoAsignados().size(); j++)
            {                
                Recurso r = p.getNoAsignados().get(j);
                System.out.println("Recurso: " + r.getNombre());
            }            
        }
    }    
    
    @Override
    public void itemStateChanged(ItemEvent e) 
    {
        if(e.getItemSelectable() == def)
        {            
            banderaDeteccion = false;
            banderaNegacion = false;
        }
        else if(e.getItemSelectable() == negacion)
        {            
            banderaDeteccion = false;
            banderaNegacion = true;               
                        
        }
        else if(e.getItemSelectable() == deteccion)
        {            
            banderaDeteccion = true;
            banderaNegacion = false;        
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getActionCommand().equals("Empezar"))
        {
            ciclo = false;
            tiempos();
            System.out.println("\n//////////////////------------------/////////////////////\n");
            imprimir(); 
            ArrayList<Proceso> listaTemp = new ArrayList();
            ArrayList<Proceso> listaRepetidos = new ArrayList();
            for (Proceso listaProceso : listaProcesos) 
            {            
                if(banderaDeteccion == true)
                {
                    ciclo = false;
                    listaCiclo = new ArrayList();
                    existeCiclo(listaProceso); 
                    for(int j = 0; j < listaCiclo.size(); j++)
                    {
                        Proceso p = listaCiclo.get(j);
                        if(listaTemp.contains(p))
                        {
                            if(!listaRepetidos.contains(p))
                            {
                                listaRepetidos.add(p);
                            }
                        }
                        else
                        {
                            listaTemp.add(p);
                        }
                    }                    
                }
            }
            if(banderaDeteccion == true)
            {
                log.append("\nSe debe finalizar el(los) proceso(s) para evitar el interbloqueo: "); 
                for(int i = 0; i < listaRepetidos.size(); i++)
                {
                    log.append("\n      " + listaRepetidos.get(i).getNombre()); 
                }
            }
        }
        else if (e.getActionCommand().equals("Reset"))
        {            
            listaProcesos = new ArrayList();    
            listaRecursos = new ArrayList();
            listaCiclo = new ArrayList();
            log.setText("_____Log");
            ciclo = false;           
            banderaDeteccion = false; 
            banderaNegacion = false;
        }
    }     
}