
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CalFrame.java
 *
 * Created on 3 Jan, 2010, 11:05:27 AM
 */

/**
 *
 * @author Suhas Bharadwaj
 */
public class CalFrame extends javax.swing.JFrame implements CalInterface {

    /** Creates new form CalFrame */
    
    public CalFrame()
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        initComponents();
        Dimension screenSize =
        Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        setLocation(new Point((screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2));
        
        datePanel.setLayout(new GridLayout(7,5));
        weekPanel.setLayout(new GridLayout(7,7));
        setDates();
        setWeekDates();

        for(int i=startYear;i<=endYear;i++)
        {
            comboCalYear.addItem(i);
        }

        GregorianCalendar cal = new GregorianCalendar();
	cal.setTime(new Date());
        int stryear = cal.get(Calendar.YEAR);
        comboCalYear.setSelectedIndex(stryear-startYear);
    }


    public void Find(int yr)
    {
        int stryear = yr;
        vWeekDays.clear();
        for(int monthNum=1;monthNum<=12;monthNum++)
        {
            String tempDate = stryear + "/" + monthNum + "/" + 1;
            Date date = null;
            try
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                date = format.parse(tempDate);
            }
            catch (ParseException e)
            {
                System.out.println(e.toString());
                e.printStackTrace();
            }
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(date);
            int DayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            //System.out.println(date);
            //System.out.println(getMonthNameFromNumber(monthNum) + " " + getWeekDayFromNumber(DayOfWeek));
            vWeekDays.add(getWeekDayFromNumber(DayOfWeek));
        }
        populateYearPanel();
    }

    /* This is how the panels will be panned out
     *
     * 1  2  3  4  5  6  7
     * 8  9  10 11 12 13 14
     * 15 16 17 18 19 20 21
     *
     */
    public void populateYearPanel()
    {
        yearPanel.removeAll();
        yearPanel.setLayout(new GridLayout(3,7));
        JLabel[] l;
        l = new JLabel[21];
        for(int i=1;i<=21;i++)
        {
            JPanel p = new JPanel();
            l[i-1]= new JLabel(" ");
            l[i-1].setFont(new Font("Courier New", Font.BOLD, 12));
            p.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
            p.add(l[i-1]);
            yearPanel.add(p);
        }

        for(int i=0;i<vWeekDays.size();i++)
        {
            int n = getWeekNumberFromWeekDay((String) vWeekDays.get(i));
            if(l[n].getText().equals(" "))
            {
                l[n].setText(getMonthNameFromNumber(i+1));
            }
            else if(l[n+7].getText().equals(" "))
            {
                l[n+7].setText(getMonthNameFromNumber(i+1));
            }
            else if(l[n+14].getText().equals(" "))
            {
                l[n+14].setText(getMonthNameFromNumber(i+1));
            }
            else
            {
                System.out.println("ERROR: 3 Rows not possible requires more");
                 JOptionPane.showMessageDialog(null,
                    "ERROR: Exception occured!!!!\n3 Rows not possible requires more\n" +
                    "Please report to "+ AUTHORandEMAILID);
            }
        }
    }

    /*
     * This is how the dates will be shown
     *
     * 1 8  15 22 29        0*7+1 1*7+1 .. 4*7+1
     * 2 9  16 23 30        0*7+2 1*7+2 .. 4*7+2
     * 3 10 17 24 31
     * 4 11 18 25 *
     * 5 12 19 26 *
     * 6 13 20 27 *
     * 7 14 21 28 *
     *
     * This is how the panels will be panned out
     *
     * 1  2  3  4  5
     * 6  7  8  9  10
     * 11 12 13 14 15
     * 16 17 18 19 20
     * 21 22 23 24 25
     * 26 27 28 29 30
     * 31 32 33 34 35
     *
     */
    public void setDates()
    {
        int rowcount = 1;
        int colcount = 0;
        for(int i=1;i<=35;i++)
        {
            JPanel p = new JPanel();
            JLabel l = new JLabel("*");
            l.setFont(new Font("Courier New", Font.BOLD, 12));
            p.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
            p.add(l);

            int date = (colcount*7)+rowcount;

            if(date<=31)
                l.setText(Integer.toString(date));
            if(i%5 == 0)
            {
                rowcount++;
                colcount = 0;
            }
            else
            {
                colcount++;
            }
            datePanel.add(p);
        }
    }


    /*
     * This is how the dates will be shown
     *
     * Sun Mon Tue Wed Thu Fri Sat
     * Mon Tue Wed Thu Fri Sat Sun
     * Tue Wed Thu Fri Sat Sun Mon
     * Wed Thu Fri Sat Sun Mon Tue
     * Thu Fri Sat Sun Mon Tue Wed
     * Fri Sat Sun Mon Tue Wed Thu
     * Sat Sun Mon Tue Wed Thu Fri
     *
     * This is how the panels will be panned out
     *
     * 1  2  3  4  5  6  7
     * 8  9  10 11 12 13 14
     * 15 16 17 18 19 20 21
     * 22 23 24 25 26 27 28
     * 29 30 31 32 33 34 35
     * 36 37 38 39 40 41 42
     * 43 44 45 46 47 48 49
     */
    public void setWeekDates()
    {
        int WeekDayNum = 1;
        int rowcount = 1;
        for(int i=1;i<=49;i++)
        {
            JPanel p = new JPanel();
            JLabel l = new JLabel("*");
            l.setFont(new Font("Courier New", Font.BOLD, 12));
            p.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
            p.add(l);

            l.setText(getWeekDayFromNumber(WeekDayNum));
            WeekDayNum++;
            if(i%7 == 0)
            {
                rowcount++;
                WeekDayNum = rowcount;
            }
            if(WeekDayNum == 8)
            {
                WeekDayNum = 1;
            }
            weekPanel.add(p);
        }
    }

    public String getWeekDayFromNumber(int num)
    {
        switch(num)
        {
            case 1: return "Sun";
            case 2: return "Mon";
            case 3: return "Tue";
            case 4: return "Wed";
            case 5: return "Thu";
            case 6: return "Fri";
            case 7: return "Sat";
        }
        return "ERROR";
    }

    public int getWeekNumberFromWeekDay(String week)
    {
        if(week.equals("Sun"))
            return 0;
        else if(week.equals("Mon"))
            return 1;
        else if(week.equals("Tue"))
            return 2;
        else if(week.equals("Wed"))
            return 3;
        else if(week.equals("Thu"))
            return 4;
        else if(week.equals("Fri"))
            return 5;
        else if(week.equals("Sat"))
            return 6;
        else
            return -1;
    }

    public String getMonthNameFromNumber(int num)
    {
        switch(num)
        {
            case 1: return "Jan";
            case 2: return "Feb";
            case 3: return "Mar";
            case 4: return "Apr";
            case 5: return "May";
            case 6: return "Jun";
            case 7: return "Jul";
            case 8: return "Aug";
            case 9: return "Sep";
            case 10: return "Oct";
            case 11: return "Nov";
            case 12: return "Dec";
        }
        return "ERROR";
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        printPanel = new javax.swing.JPanel();
        labelYear = new javax.swing.JLabel();
        yearPanel = new javax.swing.JPanel();
        datePanel = new javax.swing.JPanel();
        weekPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        comboCalYear = new javax.swing.JComboBox();
        buttonPrint = new javax.swing.JButton();
        buttonAbout = new javax.swing.JButton();
        buttonExit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle(TITLE);
        setIconImages(null);
        setResizable(false);

        printPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        labelYear.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        labelYear.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelYear.setText("Year: 2009");

        yearPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        javax.swing.GroupLayout yearPanelLayout = new javax.swing.GroupLayout(yearPanel);
        yearPanel.setLayout(yearPanelLayout);
        yearPanelLayout.setHorizontalGroup(
            yearPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 187, Short.MAX_VALUE)
        );
        yearPanelLayout.setVerticalGroup(
            yearPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        datePanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        javax.swing.GroupLayout datePanelLayout = new javax.swing.GroupLayout(datePanel);
        datePanel.setLayout(datePanelLayout);
        datePanelLayout.setHorizontalGroup(
            datePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        datePanelLayout.setVerticalGroup(
            datePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 186, Short.MAX_VALUE)
        );

        weekPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        javax.swing.GroupLayout weekPanelLayout = new javax.swing.GroupLayout(weekPanel);
        weekPanel.setLayout(weekPanelLayout);
        weekPanelLayout.setHorizontalGroup(
            weekPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        weekPanelLayout.setVerticalGroup(
            weekPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 188, Short.MAX_VALUE)
        );

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel3.setText("Designed and developed by " + AUTHORandEMAILID);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("NOTE: Column indicates month, row indicates date");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("and their intersection gives the week of that day");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Calender");

        javax.swing.GroupLayout printPanelLayout = new javax.swing.GroupLayout(printPanel);
        printPanel.setLayout(printPanelLayout);
        printPanelLayout.setHorizontalGroup(
            printPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(printPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(printPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(printPanelLayout.createSequentialGroup()
                        .addGroup(printPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(yearPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(weekPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(printPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(datePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelYear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(printPanelLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        printPanelLayout.setVerticalGroup(
            printPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, printPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(printPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, printPanelLayout.createSequentialGroup()
                        .addComponent(yearPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, printPanelLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelYear)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addGap(16, 16, 16)))
                .addGroup(printPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(weekPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(datePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3))
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Select Year : ");

        comboCalYear.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        comboCalYear.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboCalYearItemStateChanged(evt);
            }
        });

        buttonPrint.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        buttonPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/printer.png"))); // NOI18N
        buttonPrint.setMnemonic('P');
        buttonPrint.setToolTipText("Print");
        buttonPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPrintActionPerformed(evt);
            }
        });

        buttonAbout.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        buttonAbout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/lightbulb.png"))); // NOI18N
        buttonAbout.setMnemonic('A');
        buttonAbout.setToolTipText("About");
        buttonAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAboutActionPerformed(evt);
            }
        });

        buttonExit.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        buttonExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/quit.png"))); // NOI18N
        buttonExit.setMnemonic('Q');
        buttonExit.setToolTipText("Exit");
        buttonExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonExitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboCalYear, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonPrint)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonAbout)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonExit)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonExit)
                    .addComponent(buttonAbout)
                    .addComponent(buttonPrint)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(comboCalYear, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(printPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(printPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void comboCalYearItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboCalYearItemStateChanged
        // TODO add your handling code here:
        int stryear = startYear+comboCalYear.getSelectedIndex();
        labelYear.setText("Year: "+stryear);
        Find(stryear);
    }//GEN-LAST:event_comboCalYearItemStateChanged

    private void buttonPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPrintActionPerformed
        // TODO add your handling code here:
       CalPrintUtilities.printComponent(printPanel);
    }//GEN-LAST:event_buttonPrintActionPerformed

    private void buttonAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAboutActionPerformed
        // TODO add your handling code here:
        new CalAboutBox(this,true).setVisible(true);
    }//GEN-LAST:event_buttonAboutActionPerformed

    private void buttonExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonExitActionPerformed
        // TODO add your handling code here:
        System.exit(1);
    }//GEN-LAST:event_buttonExitActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CalFrame().setVisible(true);
            }
        });
    }
    int startYear = 1900;
    int endYear= 2199;
    Vector vWeekDays = new Vector();


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonAbout;
    private javax.swing.JButton buttonExit;
    private javax.swing.JButton buttonPrint;
    private javax.swing.JComboBox comboCalYear;
    private javax.swing.JPanel datePanel;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelYear;
    private javax.swing.JPanel printPanel;
    private javax.swing.JPanel weekPanel;
    private javax.swing.JPanel yearPanel;
    // End of variables declaration//GEN-END:variables

}
