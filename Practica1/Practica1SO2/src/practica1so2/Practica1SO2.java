package practica1so2;

// @author lizama

public class Practica1SO2 
{
    public static void main(String[] args) 
    {
        Ventana v = new Ventana();
    }
    
}

/*
T1(P1(R1|S), P2(R2|S), P3(R2,R3|S))

T1(P1(R1|S), P2(R2,R1|S))
T2(P3(R3,R2|S))
T3(P1(R3|S))
T4(P4(R4|S))
T5(P5(R3|S))

T1(P1(R1|S))
T2(P2(R2,R1|S))
T3(P2(f))
T4(P3(R3,R2|S))
T5(P1(R3|S))

*/