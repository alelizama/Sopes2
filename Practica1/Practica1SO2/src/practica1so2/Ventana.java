package practica1so2;
 
//@author lizama 

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
 
public class Ventana 
{
    JTextField texto;
    
    public Ventana()
    {   
        
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        
        
        JFrame frame = new JFrame("Practica 1");
        frame.setVisible(true);
        frame.setLayout(null);
        frame.setSize(605,605);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        
        frame.add(panel);
        panel.setBounds(1,1,600,300);
    }    
     
}
