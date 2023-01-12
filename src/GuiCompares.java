import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GuiCompares extends JFrame
{
	Dimension Schermo = Toolkit.getDefaultToolkit().getScreenSize();

	MonteCarlo pannelloMonteCarlo2, pannelloMonteCarlo3;
	JPanel pannelloConsole;
	JLabel labelComparazioneHit, labelComparazioneSample, labelComparazioneAV;
 	JPanel pannelloSettaggi;
	JComboBox comboBoxSceltaFunzione, comboBoxIntervallo;
 	JLabel intervallo;
	JButton calcolaComparazione, indietro;
	float maxFunction;
	int funzioneScelta;
	public GuiCompares()
	{
		setVisible(false);
		setSize(Schermo.width*90/100, Schermo.height*75/100);
		setLayout(null);
		setLocation(Schermo.width/2 - ((Schermo.width*90/100)/2), Schermo.height/8);
		setResizable(false);
		
		Dimension screen = new Dimension();
		screen.height = Schermo.height*75/100;
		screen.width = Schermo.width*90/100;
				
		pannelloMonteCarlo2 = new MonteCarlo(1, screen.width*80/100/2, screen.height*65/100);
		add(pannelloMonteCarlo2);
		pannelloMonteCarlo2.setLocation(5, 5);
		pannelloMonteCarlo2.setSize(screen.width*80/100/2, screen.height*65/100);
		pannelloMonteCarlo2.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		pannelloMonteCarlo2.setLayout(null);

		pannelloMonteCarlo3= new MonteCarlo(1, screen.width*80/100/2,screen.height*65/100);
		add(pannelloMonteCarlo3);
		pannelloMonteCarlo3.setLocation(pannelloMonteCarlo2.getWidth()+5, 5);
		pannelloMonteCarlo3.setSize(screen.width*80/100/2, screen.height*65/100);
		pannelloMonteCarlo3.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		pannelloMonteCarlo3.setLayout(null);

		pannelloConsole = new JPanel();
		add(pannelloConsole);
		pannelloConsole.setLocation(pannelloMonteCarlo2.getX(), pannelloMonteCarlo2.getHeight() + 10);
		pannelloConsole.setSize(pannelloMonteCarlo2.getWidth()+pannelloMonteCarlo3.getWidth() , screen.height*28/100);
		pannelloConsole.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		pannelloConsole.setLayout(null);
		{
			labelComparazioneHit = new JLabel();
			labelComparazioneHit.setLocation(5, 5);
			labelComparazioneHit.setSize(pannelloConsole.getWidth()/3, pannelloConsole.getHeight() -10);
			labelComparazioneHit.setBorder(BorderFactory.createLineBorder(Color.black, 1));
			pannelloConsole.add(labelComparazioneHit);
			
			labelComparazioneSample = new JLabel();
			labelComparazioneSample.setLocation(labelComparazioneHit.getWidth()+10, 5);
			labelComparazioneSample.setSize(labelComparazioneHit.getWidth()-5, pannelloConsole.getHeight() -10);
			labelComparazioneSample.setBorder(BorderFactory.createLineBorder(Color.black, 1));
			pannelloConsole.add(labelComparazioneSample);
			
			labelComparazioneAV = new JLabel();
			labelComparazioneAV.setLocation(labelComparazioneHit.getWidth()+labelComparazioneHit.getWidth()+10, 5);
			labelComparazioneAV.setSize(labelComparazioneHit.getWidth()-15, pannelloConsole.getHeight() -10);
			labelComparazioneAV.setBorder(BorderFactory.createLineBorder(Color.black, 1));
			pannelloConsole.add(labelComparazioneAV);
			
		}
		
		pannelloSettaggi = new JPanel();
		add(pannelloSettaggi);
		pannelloSettaggi.setLocation( pannelloMonteCarlo3.getWidth()+10+pannelloMonteCarlo3.getWidth(), pannelloMonteCarlo3.getY() );
		pannelloSettaggi.setSize(screen.width*18/100, pannelloMonteCarlo3.getHeight());
		pannelloSettaggi.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		pannelloSettaggi.setLayout(null);
		{
			comboBoxSceltaFunzione = new JComboBox(new String[]{"Logarithm Function", "Square Function", "Linear", "PiGreco Function"});
			comboBoxSceltaFunzione.setLocation(10, 30);
			comboBoxSceltaFunzione.setSize(pannelloSettaggi.getWidth() -20, 30);
			comboBoxSceltaFunzione.addItemListener(new ItemChangeListener());
			pannelloSettaggi.add(comboBoxSceltaFunzione);		
			
			intervallo = new JLabel("Confidence Interval");
			intervallo.setLocation(comboBoxSceltaFunzione.getX(), comboBoxSceltaFunzione.getY()+comboBoxSceltaFunzione.getHeight()+20);
			intervallo.setSize( (pannelloSettaggi.getWidth()/2)-20, comboBoxSceltaFunzione.getHeight());
			intervallo.setBorder(BorderFactory.createLineBorder(Color.black, 1));
			intervallo.setHorizontalAlignment(SwingConstants.CENTER);
			pannelloSettaggi.add(intervallo);
		
			comboBoxIntervallo = new JComboBox(new String[]{"0.01", "0.05", "0.1"});
			comboBoxIntervallo.setLocation( (pannelloSettaggi.getWidth()/2)+10,  comboBoxSceltaFunzione.getY() + comboBoxSceltaFunzione.getHeight()+20);
			comboBoxIntervallo.setSize( (pannelloSettaggi.getWidth()/2)-20, comboBoxSceltaFunzione.getHeight());
			pannelloSettaggi.add(comboBoxIntervallo);
		}
							// BOTTONI
		calcolaComparazione = new JButton("Compares");
		add(calcolaComparazione); 
		calcolaComparazione.setLocation(pannelloConsole.getWidth() +10, pannelloSettaggi.getHeight()+pannelloConsole.getHeight()/4);
		calcolaComparazione.setSize(pannelloSettaggi.getWidth() , 50);
		
		indietro = new JButton("Back");
		add(indietro);
		indietro.setLocation(pannelloConsole.getWidth() +10, calcolaComparazione.getY()+calcolaComparazione.getHeight()+20);
		indietro.setSize(pannelloSettaggi.getWidth() , 50);
		
		BottonEvent e = new BottonEvent();
		indietro.addActionListener(e);
		calcolaComparazione.addActionListener(e);
		
		
		funzioneScelta =1;
		maxFunction = (float)(Math.log(100));
	}
	class ItemChangeListener implements ItemListener
	{
	    public void itemStateChanged(ItemEvent event)
	    {
	       if (event.getStateChange() == ItemEvent.SELECTED)
	       {
	    	   Object item = event.getItem();
	    	   if(item.toString().equals("Logarithm Function") )
	    	   {
    			   maxFunction = (float)(Math.log(100));
    			   pannelloMonteCarlo2.funzioneScelta = 1;
    			   pannelloMonteCarlo3.funzioneScelta = 1;
    			   pannelloMonteCarlo2.xMax = 100;
	    		   pannelloMonteCarlo2.xMin = 0;
	    		   pannelloMonteCarlo2.yMax = (int) maxFunction;
	    		   pannelloMonteCarlo2.yMin = 0;
	    		   pannelloMonteCarlo3.xMax = 100;
	    		   pannelloMonteCarlo3.xMin = 0;
	    		   pannelloMonteCarlo3.yMax = (int) maxFunction;
	    		   pannelloMonteCarlo3.yMin = 0;
	    	   }
	    	   else if(item.toString().equals("Square Function"))
	    	   {
	    		   maxFunction = (float)Math.sqrt(100);
	    		   pannelloMonteCarlo2.funzioneScelta = 2;
    			   pannelloMonteCarlo3.funzioneScelta = 2;
    			   pannelloMonteCarlo2.xMax = 100;
	    		   pannelloMonteCarlo2.xMin = 0;
	    		   pannelloMonteCarlo2.yMax = (int) maxFunction;
	    		   pannelloMonteCarlo2.yMin = 0;
	    		   pannelloMonteCarlo3.xMax = 100;
	    		   pannelloMonteCarlo3.xMin = 0;
	    		   pannelloMonteCarlo3.yMax = (int) maxFunction;
	    		   pannelloMonteCarlo3.yMin = 0;
	    	   }
	    	   else if(item.toString().equals("Linear"))
	    	   { 
    			   maxFunction = 50;
    			   pannelloMonteCarlo2.funzioneScelta = 3;
    			   pannelloMonteCarlo3.funzioneScelta = 3;
    			   pannelloMonteCarlo2.xMax = 50;
	    		   pannelloMonteCarlo2.xMin = 1;
	    		   pannelloMonteCarlo2.yMax = 50;
	    		   pannelloMonteCarlo2.yMin = 1;
	    		   pannelloMonteCarlo3.xMax = 50;
	    		   pannelloMonteCarlo3.xMin = 1;
	    		   pannelloMonteCarlo3.yMax = 50;
	    		   pannelloMonteCarlo3.yMin = 1;
	    	   }
	    	   else if(item.toString().equals("PiGreco Function"))
	    	   {
	    		   maxFunction = 1;
	    		   pannelloMonteCarlo2.funzioneScelta = 4;
	    		   pannelloMonteCarlo3.funzioneScelta = 4;
	    		   pannelloMonteCarlo2.xMax = 1;
	    		   pannelloMonteCarlo2.xMin = 0;
	    		   pannelloMonteCarlo2.yMax = 1;
	    		   pannelloMonteCarlo2.yMin = 0;
	    		   pannelloMonteCarlo3.xMax = 1;
	    		   pannelloMonteCarlo3.xMin = 0;
	    		   pannelloMonteCarlo3.yMax = 1;
	    		   pannelloMonteCarlo3.yMin = 0;
	    	   }
	       }
	    }       
	}
	
	public class BottonEvent implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String operation = e.getActionCommand();
			if(operation.equals("Back"))
			{
				pannelloMonteCarlo2.compares = false;
				pannelloMonteCarlo3.compares = false;
				pannelloMonteCarlo3.pannelloDevSt = true;
				pannelloMonteCarlo2.pannelloTheta = true;
				dispose();
			}
			else if(operation.equals("Compares")) 
			{
				colora();
				String [] testo = pannelloMonteCarlo2.comparaAlgoritmi( maxFunction, comboBoxIntervallo.getSelectedIndex()+1, pannelloMonteCarlo2.getGraphics(), pannelloMonteCarlo3);
				labelComparazioneHit.setText(testo[0]);
				labelComparazioneSample.setText(testo[1]);
				labelComparazioneAV.setText(testo[2]);
			}
		}
	}
	
	public void colora()
	{
		pannelloMonteCarlo2.paintComponent(pannelloMonteCarlo2.getGraphics(), false, 1);
		pannelloMonteCarlo3.paintComponent(pannelloMonteCarlo3.getGraphics(), false, 1);
	}
}
