package barberodormilon;
//importamos la libreria sleep contenida en java.lang.thread
import static java.lang.Thread.getAllStackTraces;
import static java.lang.Thread.sleep;


public class Barberia {
    //numero total de sillas de espera
    private final int TOTAL_SILLAS=5; 
    //numero de sillas ocupadas
    public static int sillasOcupadas=0;
    /*número de clientes que van a entrar en la barberia, podria ahorrarme este int y que entraran constantemente,
    pero queria que tuviera un fin para ver que el barbero se duerme de nuevo al terminar el trabajo*/
    public static int numClientes=20;
    
    /**
     * Método sincronizado para que no puedan entrar dos hilos al mismo tiempo
     * @throws InterruptedException pasa la excepción al metodo que lo llama
     */
    
    public synchronized void llegaCliente() throws InterruptedException{
        
        System.out.println(getAllStackTraces().toString());
        //mostramos el log por consola y el entorno grafico
        System.out.println("Nuevo cliente");
        Barberia_grafico.logTextArea1.append("Nuevo cliente ");
        
        if (numClientes>0){
            //actualizamos el número de clientes
            Barberia.numClientes--;
        }
        
        //Mostramos el numero de clientes en el contador del entorno gráfico
        Barberia_grafico.contadorLabel.setText(Integer.toString(Barberia.numClientes));
        
        //Si hay sillas libres entra a ejecutar el código que contiene la sentencia if
        if (sillasOcupadas<=TOTAL_SILLAS){
            //si el sillón del barbero está vacio entra a ejecutar el código que contiene la sentencia if
            if (sillasOcupadas==0){
                //mostramos el log por consola y el entorno grafico
                System.out.println("Silla asignada: SILLÓN DEL BARBERO");
                Barberia_grafico.logTextArea1.append("Silla asignada: SILLÓN DEL BARBERO");
                Barberia_grafico.logTextArea1.append(System.getProperty("line.separator")); // Esto para el salto de línea              
            }
            //si no se cumple la condición anterior mostramos el número de silla asignada para esperar
            else{
            //mostramos el log por consola y el entorno grafico
            System.out.println("Silla asignada: Nº "+ sillasOcupadas);
            Barberia_grafico.logTextArea1.append("Silla asignada: Nº "+ sillasOcupadas);
            Barberia_grafico.logTextArea1.append(System.getProperty("line.separator")); // Esto para el salto de línea
            }
            //sumamos 1 a la variable sillasOcupadas
            sillasOcupadas++;
        }
        //si no hay sillas libres informamos en el log por consola y entorno gráfico
        else{
                System.err.println("¡NO hay sillas disponibles, el cliente se va!");
                Barberia_grafico.logTextArea1.append("¡NO hay sillas disponibles, el cliente se va!");
                Barberia_grafico.logTextArea1.append(System.getProperty("line.separator")); // Esto para el salto de línea
                //Mostromos todos los muñecos menos el barbero dormido, este codigo lo he puesto aquí para que me muestre bien el icono del hombre que se va de la barberia
                Barberia_grafico.hombre1label.setVisible(true);
                Barberia_grafico.hombre2label.setVisible(true);
                Barberia_grafico.hombre3label.setVisible(true);
                Barberia_grafico.hombre4label.setVisible(true);
                Barberia_grafico.hombre5label.setVisible(true);
                Barberia_grafico.hombreatendidolabel.setVisible(true);
                Barberia_grafico.dormido.setVisible(false); 
                Barberia_grafico.salirLabel.setVisible(true);
                //dormimos el hilo 1 segundo para que de tiempo a mostrar al hombre que sale
                sleep(1000);
        }  
        //llamamos al metodo que refresca la pantalla del programa 
        sillasVisibles(); 
        //dormimos el hilo 1 segundo
        sleep(1000);
        //notificamos a los hilos en espera que liberamos el procesador
        notify();  
    }
    
     /**
     * Método sincronizado para que no puedan entrar dos hilos al mismo tiempo
     * @throws InterruptedException pasa la excepción al metodo que lo llama
     */
    
    
    public synchronized void salirCliente() throws InterruptedException {
        try{
            //Si hay sillas ocupadas entra a ejecutar el código que contiene la sentencia if
            if (sillasOcupadas>0){
                //resta 1 silla ocupadas
                sillasOcupadas--;
                //mostramos el log por consola y el entorno grafico
                System.out.println("Cliente atendido,SALE DE LA BARBERÍA");
                Barberia_grafico.logTextArea1.append("Cliente atendido,SALE DE LA BARBERÍA");
                Barberia_grafico.logTextArea1.append(System.getProperty("line.separator")); // Esto para el salto de línea  
                //llamamos al metodo que refresca la pantalla del programa 
                sillasVisibles(); 
                //dormimos el hilo 1 segundo
                sleep(1000);
           }
        //si se interrumpe el hilo dormido lanza el error
        }catch(InterruptedException exception){        
                System.out.println("Error: El hilo dormido se ha interrumpido");
                Barberia_grafico.logTextArea1.append("Error: El hilo dormido se ha interrumpido");
                Barberia_grafico.logTextArea1.append(System.getProperty("line.separator")); // Esto para el salto de línea  
        }
        //Mientras no haya clientes el barbero se duerme
        while (sillasOcupadas==0){
            try{
                //mostramos el log por consola y el entorno grafico
                System.out.println("Sala vacia, el barbero se duerme");
                Barberia_grafico.logTextArea1.append("Sala vacia, el barbero se duerme");
                Barberia_grafico.logTextArea1.append(System.getProperty("line.separator")); // Esto para el salto de línea
                //dormimos el hilo 1 segundo
                sleep(1000);
                //duerme el hilo hasta que algún otro hilo lo despierta
                wait();  
                //si se interrumpe el hilo dormido lanza el error
            }catch(InterruptedException exception){
                System.out.println("Error: El hilo dormido se ha interrumpido");
                Barberia_grafico.logTextArea1.append("Error: El hilo dormido se ha interrumpido");
                Barberia_grafico.logTextArea1.append(System.getProperty("line.separator")); // Esto para el salto de línea  
            }
        }  
             //notificamos a los hilos en espera que liberamos el procesador
        notify();  
    }
    //metodo para refrescar la pantalla del entorno gráico
    public static void sillasVisibles(){  
        if (sillasOcupadas==0){
            Barberia_grafico.hombre1label.setVisible(false);
            Barberia_grafico.hombre2label.setVisible(false);
            Barberia_grafico.hombre3label.setVisible(false);
            Barberia_grafico.hombre4label.setVisible(false);
            Barberia_grafico.hombre5label.setVisible(false);
            Barberia_grafico.hombreatendidolabel.setVisible(false);
            Barberia_grafico.dormido.setVisible(true);
            Barberia_grafico.salirLabel.setVisible(false);
        }
        else{
            if (sillasOcupadas==1){
                Barberia_grafico.hombre1label.setVisible(false);
                Barberia_grafico.hombre2label.setVisible(false);
                Barberia_grafico.hombre3label.setVisible(false);
                Barberia_grafico.hombre4label.setVisible(false);
                Barberia_grafico.hombre5label.setVisible(false);
                Barberia_grafico.hombreatendidolabel.setVisible(true);
                Barberia_grafico.dormido.setVisible(false);
                Barberia_grafico.salirLabel.setVisible(false);
            }
            else{
                if (sillasOcupadas==2){
                    Barberia_grafico.hombre1label.setVisible(true);
                    Barberia_grafico.hombre2label.setVisible(false);
                    Barberia_grafico.hombre3label.setVisible(false);
                    Barberia_grafico.hombre4label.setVisible(false);
                    Barberia_grafico.hombre5label.setVisible(false);
                    Barberia_grafico.hombreatendidolabel.setVisible(true);
                    Barberia_grafico.dormido.setVisible(false);
                    Barberia_grafico.salirLabel.setVisible(false);
                }
                else{
                    if (sillasOcupadas==3){
                        Barberia_grafico.hombre1label.setVisible(true);
                        Barberia_grafico.hombre2label.setVisible(true);
                        Barberia_grafico.hombre3label.setVisible(false);
                        Barberia_grafico.hombre4label.setVisible(false);
                        Barberia_grafico.hombre5label.setVisible(false);
                        Barberia_grafico.hombreatendidolabel.setVisible(true);
                        Barberia_grafico.dormido.setVisible(false);
                        Barberia_grafico.salirLabel.setVisible(false);
                    }
                    else{
                        if (sillasOcupadas==4){
                            Barberia_grafico.hombre1label.setVisible(true);
                            Barberia_grafico.hombre2label.setVisible(true);
                            Barberia_grafico.hombre3label.setVisible(true);
                            Barberia_grafico.hombre4label.setVisible(false);
                            Barberia_grafico.hombre5label.setVisible(false);
                            Barberia_grafico.hombreatendidolabel.setVisible(true);
                            Barberia_grafico.dormido.setVisible(false);
                            Barberia_grafico.salirLabel.setVisible(false);
                        }   
                        else{
                            if (sillasOcupadas==5){
                                Barberia_grafico.hombre1label.setVisible(true);
                                Barberia_grafico.hombre2label.setVisible(true);
                                Barberia_grafico.hombre3label.setVisible(true);
                                Barberia_grafico.hombre4label.setVisible(true);
                                Barberia_grafico.hombre5label.setVisible(false);
                                Barberia_grafico.hombreatendidolabel.setVisible(true);
                                Barberia_grafico.dormido.setVisible(false);
                                Barberia_grafico.salirLabel.setVisible(false);
                            }
                            else{
                                if (sillasOcupadas==6){
                                    Barberia_grafico.hombre1label.setVisible(true);
                                    Barberia_grafico.hombre2label.setVisible(true);
                                    Barberia_grafico.hombre3label.setVisible(true);
                                    Barberia_grafico.hombre4label.setVisible(true);
                                    Barberia_grafico.hombre5label.setVisible(true);
                                    Barberia_grafico.hombreatendidolabel.setVisible(true);
                                    Barberia_grafico.dormido.setVisible(false);
                                    Barberia_grafico.salirLabel.setVisible(false);
                                }   
                            }                                 
                        }
                    }
                }
            }
        } 
    }      
}