/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientCRC;

import java.awt.*;
import javax.swing.ImageIcon;

/**
 *
 * @author alessandro
 */
public class Progress extends javax.swing.JFrame {
    private Font myFont = new Font("Tahoma", Font.PLAIN,11);
    Frame A = this;
    public Progress(String user, int admin) {
        initComponents();
        this.setAlwaysOnTop(true);
        // Prendo le dimensioni del desktop
        Dimension dim_schermo = Toolkit.getDefaultToolkit().getScreenSize();

        // Calcolo la posizione orizzontale della finestra
        int posX = (int) (dim_schermo.width - getWidth())/2;

        // Calcolo la posizione verticale della finestra
        int posY = (int) (dim_schermo.height - getHeight())/2;

        // Posiziono la finestra
        setLocation(posX, posY);
        this.getContentPane().setBackground(Color.DARK_GRAY);
        this.setIconImage(new ImageIcon("C:\\SOAP\\images\\icon.gif").getImage());
        this.setFont(myFont);
        jUser.setText(user);
        jProgressBar1.setStringPainted(true);
        jProgressBar1.setIndeterminate(true);
        jUser.setVisible(false);
        jAdmin.setDisplayedMnemonic(admin);
        MyThread1 t1 = new MyThread1(); 
        t1.start(); 
        this.dispose();
        Frame A = this;
 // o anche: new MyThread1().start(); 
        
            
            
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    class MyThread1 extends Thread { 
        
        public void run(){ 
            int admin = jAdmin.getDisplayedMnemonic();
            String user = jUser.getText();
            new VisualizzaTab(user, A ,admin).setVisible(true);
    }} 
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jUser = new javax.swing.JLabel();
        jAdmin = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 0));
        jLabel1.setText("Caricamento in corso...");

        jProgressBar1.setBackground(new java.awt.Color(255, 255, 153));
        jProgressBar1.setForeground(new java.awt.Color(51, 255, 0));
        jProgressBar1.setString("");

        jAdmin.setText("jLabel2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jUser)
                .addGap(30, 30, 30))
            .addGroup(layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(90, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jAdmin)
                .addGap(58, 58, 58))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jAdmin))
                .addGap(42, 42, 42)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jUser)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jAdmin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JLabel jUser;
    // End of variables declaration//GEN-END:variables
}