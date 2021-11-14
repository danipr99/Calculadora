/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.senoracalculadora;

/**
 *
 * @author DPDAN
 */

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import static java.awt.Font.PLAIN;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;


public class main extends JFrame {

    JPanel panelP = new JPanel();// LO HE HECHO YO, PERO NO ESTA INTRODUCIDO
    Cientifica c = new Cientifica();
    //Display para mostrar los números
    JLabel display;
    //Cantidad de botones de calculadora
    int numBotones = 18;
    //Array de botones para números y operaciones
    Boton botones[] = new Boton[numBotones];
    Boton botonCientifico;
    //Array de strings para las etiquetas de los botones
    String textoBotones[] = {"=", "CT", "7", "8", "9", "/", "4", "5", "6", "*", "1", "2", "3", "-", "C", "0", ".", "+"};
    //Array de posiciones en X de cada botón
    int xBotones[] = {15, 145, 15, 80, 145, 210, 15, 80, 145, 210, 15, 80, 145, 210, 15, 80, 145, 210};
    //Array de posiciones en Y de cada botón
    int yBotones[] = {90, 90, 155, 155, 155, 155, 220, 220, 220, 220, 285, 285, 285, 285, 350, 350, 350, 350};
    //Array de índices del array de botones que corresponden a números (en el órden en el que se pintarán)
    int numerosBotones[] = {15, 10, 11, 12, 6, 7, 8, 2, 3, 4};
    //Array de índices del array de botones que corresponden a operaciones (en el órden en el que se pintarán)
    int[] operacionesBotones = {17, 13, 9, 5};
    //Alto y ancho de cada botón
    int anchoBoton = 50;
    int altoBoton = 50;
    //Para indicar que he terminado de escribir dígitos un número y que voy a añadir el siguiente
    boolean nuevoNumero = true;
    //Para indicar si ya he utilizado el punto decimal en ese número (solo puede haber uno)
    boolean puntoDecimal = false;
    //Para almacenas los resultados parciales y totales de las operaciones realizadas
    double operando1 = 0;
    double operando2 = 0;
    double resultado = 0;
    //Para almacenar el string de la operación realizada (+, -, *, /)
    String operacion = "";


 

    public main() {

        initDisplay(); //Display de la calculadora
        initBotones(); //Botones de la calculadora
        initPantalla(); //Opciones del JFrame
        eventosNumeros(); //Eventos asociados a los botones de números de la calculadora
        eventosBotones(); //Eventos asociados a todos los botones de la calculadora
        eventoDecimal(); //Eventos asociados al botón decimal "." de la calculadora
        eventosOperaciones(); //Eventos asociados a los botones de operaciones (+,-,*,/) de la calculadora
        eventoResultado();  //Eventos asociados al botón resultado de la calculadora
        eventoLimpiar();  //Eventos asociados al botón de limpiar "C" de la calculadora
        eventosCientifica();
        
        
        
        
        
    }

    private void initDisplay() {

        display = new JLabel("0"); //Inicio JLabel
        display.setBounds(15, 15, 245, 60); //Posición y dimensiones
        display.setOpaque(true); //Para poder darle un color de fondo
        display.setBackground(Color.BLACK); //Color de fondo
        display.setForeground(Color.GREEN); //Color de fuente
        display.setBorder(new LineBorder(Color.DARK_GRAY)); //Borde
        display.setFont(new Font("MONOSPACED", PLAIN, 24)); //Fuente
        display.setHorizontalAlignment(SwingConstants.RIGHT); //Alineamiento horizontal derecha
        add(display); //Añado el JLabel al JFrame
    }

    private void initBotones() {
           
        for (int i = 0; i < numBotones; i++) {
            botones[i] = new Boton(textoBotones[i]); //Inicializo el boton
            int size = (i == 0) || (i == 1) ? 24 : 16; //EL botón de Resultado tendrá un tamaño de fuente menor que todos los demás
            int ancho = (i == 0)|| (i == 1) ? 245/2 : anchoBoton; //EL botón de Resultado será más ancho que todos los demás
            /*
	            La línea anterior es el OPERADOR TERNARIO equivalente a la siguiente estructura if-else
	            if (i == 0){
	                int ancho = 245;
	            }
	            else{
	                int ancho = anchoBoton;
	            }
             */
            botones[i].setBounds(xBotones[i], yBotones[i], ancho, altoBoton); //Posición y dimensiones
            botones[i].setFont(new Font("MONOSPACED", PLAIN, size)); //Fuente
            botones[i].setOpaque(true); //Para poder darle un color de fondo
            botones[i].setFocusPainted(false); //Para que no salga una recuadro azul cuando tenga el foco
            botones[i].setBackground(Color.DARK_GRAY); //Color de fondo
            botones[i].setForeground(Color.WHITE); //Color de fuente
            botones[i].setBorder(new LineBorder(Color.DARK_GRAY)); //Borde
            add(botones[i]); //Añado el JButton al JFrame
            
        }
      
    }

    private void initPantalla() {

        setLayout(null); //Layout absoluto
        setTitle("Calculadora"); //Título del JFrame
        setSize(290, 455); //Dimensiones del JFrame
        setResizable(true); //No redimensionable
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Cerrar proceso al cerrar ventana
        getContentPane().add(c);
        getContentPane().setBackground(Color.BLACK); //Color de fondo
        setVisible(true); //Mostrar JFrame
    }

    private void eventosBotones() {

        MouseListener on[] = new MouseListener[numBotones];
        for (int i = 0; i < numBotones; i++) {

            
            botones[i].addMouseListener(botones[i].getMouseListener());
        }
    }

    private void eventosNumeros() {
        for (int i = 0; i < 10; i++) {
            int numBoton = numerosBotones[i];
            botones[numBoton].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //Si es un nuevo número y no es 0, sustituyo el valor del display
                    if (nuevoNumero) {
                        if (!textoBotones[numBoton].equals("0")) {
                            display.setText(textoBotones[numBoton]);
                            nuevoNumero = false; //Ya no es un nuevo número
                        }
                    } //Si no, lo añado a los dígitos que ya hubiera
                    else {
                        display.setText(display.getText() + textoBotones[numBoton]);
                    }
                }
            });
        }
    }

    private void eventoDecimal() {
        botones[15].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Si todavía no he añadido el punto decimal al número actual
                if (!puntoDecimal) {
                    display.setText(display.getText() + textoBotones[15]);
                    puntoDecimal = true; //Ya no puedo añadir el punto decimal en este número
                    nuevoNumero = false; //Por si empiezo el número con punto decimal (por ejemplo, .537)
                }
            }
        });

    }

    private double resultado() {

        //recojo el valor del display
        operando1 = Double.parseDouble(display.getText());
        //Selecciono y realizo operación
        switch (operacion) {

            case "+":
                resultado = operando2 + operando1;
                break;
            case "-":
                resultado = operando2 - operando1;
                break;
            case "*":
                resultado = operando2 * operando1;
                break;
            case "/":
                resultado = operando2 / operando1;
                break;

        }
        //Formateo y muestro en el display
        Locale localeActual = Locale.GERMAN;
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols(localeActual);
        simbolos.setDecimalSeparator('.');
        DecimalFormat formatoResultado = new DecimalFormat("#.######", simbolos);
        display.setText(String.valueOf(formatoResultado.format(resultado)));

        //Limpio variables para poder continuar
        limpiar();

        //Devuelvo el valor del resultado
        return resultado;

    }

    private void limpiar() {

        operando1 = operando2 = 0;
        operacion = "";
        nuevoNumero = true;
        puntoDecimal = false;
    }

    private void eventosOperaciones() {
        for (int numBoton : operacionesBotones) { //Es la versión optimizada de for (int i = 0; i < operacionesBotones.length; i++){
            botones[numBoton].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //Si no tenía ninguna operación pendiente de realizar
                    if (operacion.equals("")) {
                        //Asocio la operación del botón a la variable
                        operacion = textoBotones[numBoton];
                        //Asigno a operando2 el valor del display (como double)
                        operando2 = Double.parseDouble(display.getText());
                        //Reseteo para poder introducir otro número y otro decimal
                        nuevoNumero = true;
                        puntoDecimal = false;
                        //Si tenía alguna pendiente, calculo el resultado de la anterior y luego me guardo la actual
                    } else {
                        operando2 = resultado(); //Se almacena en operando2 para poder encadenar operaciones posteriores
                        operacion = textoBotones[numBoton];
                    }
                    //SOUT para comprobar que estoy guardando los valores adecuados
                    System.out.println(operando2 + " " + operacion + " " + operando1);

                }
            });
        }
    }

    private void eventoResultado() {


    botones[0].addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            //Al pulsar el botón de resultado, directamente lo calculo y reseteo la calculadora,
            //sin necesidad de almacenar el resultado para futuras operaciones
            resultado();


        }
    });



    }

    private void eventoLimpiar() {
        botones[13].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Al pulsar el botón de limpiar, se resetean el display y las variables de la calculadora,
                display.setText("0");
                limpiar();
            }
        });

    }

    private void eventosCientifica() {
        botonCientifico.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                getContentPane().add(c);

            }
        });
    }

    public static void main(String[] args) {
        new main();
    }

}
