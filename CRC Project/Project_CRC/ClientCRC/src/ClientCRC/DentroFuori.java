/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientCRC;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 *
 * @author alessandro
 */
public class DentroFuori extends javax.swing.JFrame {

    /**
     * Creates new form DentroFuori
     */
    public DentroFuori(int fuori, int dentro) {
        initComponents();
        initComponents();
        this.getContentPane().setBackground(Color.DARK_GRAY );
        this.setIconImage(new ImageIcon("C:\\SOAP\\images\\icon.gif").getImage());
        jDentro.setDisplayedMnemonic(dentro);
        jFuori.setDisplayedMnemonic(fuori);
        setContentPane(createDemoPanel());
        this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDentro = new javax.swing.JLabel();
        jFuori = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jDentro.setText("jLabel1");

        jFuori.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDentro)
                    .addComponent(jFuori))
                .addContainerGap(356, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jDentro)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFuori)
                .addContainerGap(255, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private PieDataset createDataset() {

        int prima = jDentro.getDisplayedMnemonic();

        int seconda = jFuori.getDisplayedMnemonic();



        int tot = prima + seconda;
        float primaperc = ((100 * prima)/tot);
        float secondaperc =((100 * seconda)/ tot);



        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("ricoveri dentro il valore soglia", new Double(primaperc));
        dataset.setValue("ricoveri fuori il valore soglia", new Double(secondaperc));

        return dataset;
    }


    private static JFreeChart createChart(PieDataset dataset) {

        JFreeChart chart = ChartFactory.createPieChart(
            "ricoveri che superano il valore soglia",  // chart title
            dataset,             // data
            true,               // include legend
            true,
            false
        );
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelFont(new Font("Verdana", Font.PLAIN, 12));
        plot.setNoDataMessage("No data available");
        plot.setCircular(false);
        plot.setLabelGap(0.02);
        return chart;

    }

     public  JPanel createDemoPanel() {
        JFreeChart chart = createChart(createDataset());
        return new ChartPanel(chart);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jDentro;
    private javax.swing.JLabel jFuori;
    // End of variables declaration//GEN-END:variables
}
