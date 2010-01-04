
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.swing.RepaintManager;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Class Name - CalPrintUtilities
 * Description - 
 *
 * @author Suhas Bharadwaj
 */




/* A simple utility class that lets you very simply print
   an arbitrary component. Just pass the component to the
   CalPrintUtilities.printComponent. The component you want to
   print doesn't need a print method and doesn't have to
   implement any interface or do anything special at all.
   <P>
   If you are going to be printing many times, it is marginally more
   efficient to first do the following:
   <PRE>
     CalPrintUtilities printHelper = new CalPrintUtilities(theComponent);
   </pre>
   then later do printHelper.print(). But this is a very tiny
   difference, so in most cases just do the simpler
   CalPrintUtilities.printComponent(componentToBePrinted).
 */



public class CalPrintUtilities implements Printable
{
  private Component componentToBePrinted;

  public static void printComponent(Component c) {
    new CalPrintUtilities(c).print();
  }

  public CalPrintUtilities(Component componentToBePrinted) {
    this.componentToBePrinted = componentToBePrinted;
  }

  public void print() {
    PrinterJob printJob = PrinterJob.getPrinterJob();
    printJob.setPrintable(this);
    if (printJob.printDialog())
      try {
        printJob.print();
      } catch(PrinterException pe) {
        System.out.println("Error printing: " + pe);
      }
  }

  public int print(Graphics g, PageFormat pageFormat, int pageIndex) {
    if (pageIndex > 0) {
      return(NO_SUCH_PAGE);
    } else {
      Graphics2D g2d = (Graphics2D)g;
      g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
      disableDoubleBuffering(componentToBePrinted);
      componentToBePrinted.paint(g2d);
      enableDoubleBuffering(componentToBePrinted);
      return(PAGE_EXISTS);
    }
  }

  /* The speed and quality of printing suffers dramatically if
     any of the containers have double buffering turned on.
     So this turns if off globally.
     @see enableDoubleBuffering
   */
  public static void disableDoubleBuffering(Component c) {
    RepaintManager currentManager = RepaintManager.currentManager(c);
    currentManager.setDoubleBufferingEnabled(false);
  }

  /* Re-enables double buffering globally. */
  public static void enableDoubleBuffering(Component c) {
    RepaintManager currentManager = RepaintManager.currentManager(c);
    currentManager.setDoubleBufferingEnabled(true);
  }
}
