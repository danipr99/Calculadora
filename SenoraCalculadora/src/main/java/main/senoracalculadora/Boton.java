/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.senoracalculadora;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;

/**
 *
 * @author DPDAN
 */
    public class Boton extends JButton {

        private MouseListener ms;

        Boton(String s) {
            super(s);
            ms = new MouseListener() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setBackground(Color.GREEN);
                    setForeground(Color.DARK_GRAY);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setBackground(Color.DARK_GRAY);
                    setForeground(Color.WHITE);
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
        }

        public MouseListener getMouseListener() {
            return ms;
        }
    }
