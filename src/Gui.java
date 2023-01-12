import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Gui extends JFrame
{
	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	
					//		GUI PRINCIPALE
	JPanel pannelloSettaggi, pannelloConsole;
	MonteCarlo pannelloMonteCarlo1 ;
	JButton reset, calcola, confronta, flushConsole;
	JComboBox comboBoxSceltaAlgoritmo, comboBoxSceltaFunzione, comboBoxN, comboBoxIntervallo;
	JScrollPane scrollConsole;
	JLabel N, intervallo;
	JTextArea console;
					//Funzione Logaritmica 1000 - 7
					//Funzione Radice 1000 - 33
					//Funzione Linear 1000 - 1000
					//Funzione PiGreco 1 - 1
	
	int funzioneScelta, algoritmoScelto;
	float maxFunction;
	
	static GuiCompares GuiCompares;
	
	public Gui() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
	{
		setVisible(true);
		setSize(screen.width-100,screen.height-100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(50, 50);	
		setLayout(null);
		setResizable(false);
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		GuiCompares = new GuiCompares();
		pannelloMonteCarlo1 = new MonteCarlo(1, (int)(screen.width*0.75), (int)(screen.height*0.65));
		add(pannelloMonteCarlo1);
		pannelloMonteCarlo1.setLocation(10,10);
		pannelloMonteCarlo1.setSize((int)(screen.width*0.75),(int)(screen.height*0.65));
		pannelloMonteCarlo1.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		pannelloMonteCarlo1.setLayout(null);
		

		pannelloConsole = new JPanel();
		add(pannelloConsole);
		pannelloConsole.setLocation(pannelloMonteCarlo1.getX(), pannelloMonteCarlo1.getHeight() + 20);
		pannelloConsole.setSize(pannelloMonteCarlo1.getWidth(), screen.height - pannelloMonteCarlo1.getHeight() -170 );
		pannelloConsole.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		pannelloConsole.setLayout(null);
		{
			console = new JTextArea();
			console.setSize(pannelloConsole.getWidth()-20, pannelloConsole.getHeight()-40);
			console.setLocation(10, 10);
			console.setBorder(BorderFactory.createLineBorder(Color.black, 1));
			console.setForeground(Color.black);
			console.setBackground(Color.white);
			scrollConsole = new JScrollPane(console);
			scrollConsole.setLocation(10,10);
			scrollConsole.setSize(pannelloConsole.getWidth()-20, pannelloConsole.getHeight()-40);
			scrollConsole.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		    pannelloConsole.add(scrollConsole);
		    
			flushConsole = new JButton("Click here for flush console");
			pannelloConsole.add(flushConsole);
			flushConsole.setLocation(10, console.getHeight()+10);
			flushConsole.setSize(console.getWidth(), 20);
		}
		

		pannelloSettaggi = new JPanel();
		add(pannelloSettaggi);
		pannelloSettaggi.setLocation(pannelloMonteCarlo1.getWidth() + 20, pannelloMonteCarlo1.getY() + 100);
		pannelloSettaggi.setSize(screen.width - pannelloMonteCarlo1.getWidth() - 150, pannelloMonteCarlo1.getHeight() - 100);
		pannelloSettaggi.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		pannelloSettaggi.setLayout(null);
		{
			N = new JLabel("N");
			N.setLocation(20, 40);
			N.setSize( (pannelloSettaggi.getWidth()/2)-20, 30);
			N.setBorder(BorderFactory.createLineBorder(Color.black, 1));
			N.setHorizontalAlignment(SwingConstants.CENTER);
			pannelloSettaggi.add(N);
		
			comboBoxN = new JComboBox(new String[]{"100", "500", "1000", "5000", "10000","50000"});
			comboBoxN.setLocation( (pannelloSettaggi.getWidth()/2)+10, 40);
			comboBoxN.setSize( (pannelloSettaggi.getWidth()/2)-20, 30);
			pannelloSettaggi.add(comboBoxN);
			
			intervallo = new JLabel("Level of Confidence");
			intervallo.setLocation(N.getX(), N.getHeight()+70);
			intervallo.setSize( (pannelloSettaggi.getWidth()/2)-20, 30);
			intervallo.setBorder(BorderFactory.createLineBorder(Color.black, 1));
			intervallo.setHorizontalAlignment(SwingConstants.CENTER);
			pannelloSettaggi.add(intervallo);
		
			comboBoxIntervallo = new JComboBox(new String[]{"0.01", "0.05", "0.1"});
			comboBoxIntervallo.setLocation( (pannelloSettaggi.getWidth()/2)+10, comboBoxN.getHeight()+70);
			comboBoxIntervallo.setSize( (pannelloSettaggi.getWidth()/2)-20, 30);
			pannelloSettaggi.add(comboBoxIntervallo);
		}
		
							// COMBO BOX
		comboBoxSceltaFunzione = new JComboBox(new String[]{"Logarithm Function", "Square Function", "Linear", "PiGreco Function"});
		add(comboBoxSceltaFunzione);		
		comboBoxSceltaFunzione.setLocation(pannelloMonteCarlo1.getWidth() + 20, pannelloSettaggi.getY() - 100);
		comboBoxSceltaFunzione.setSize(pannelloSettaggi.getWidth(), 30);
		comboBoxSceltaFunzione.addItemListener(new ItemChangeListener());
		
		comboBoxSceltaAlgoritmo = new JComboBox(new String[]{"Hit or Miss", "Sample-Mean", "Antithetic Variates"});
		add(comboBoxSceltaAlgoritmo);
		comboBoxSceltaAlgoritmo.setLocation(pannelloMonteCarlo1.getWidth() + 20, pannelloSettaggi.getY() -50);
		comboBoxSceltaAlgoritmo.setSize(pannelloSettaggi.getWidth(), 30);
		comboBoxSceltaAlgoritmo.addItemListener(new ItemChangeListener());
		

							// BOTTONI
		reset = new JButton("Reset");
		add(reset);
		reset.setLocation(pannelloConsole.getWidth() +20, pannelloConsole.getY() + 40);
		reset.setSize(pannelloSettaggi.getWidth()/2 -5 ,pannelloConsole.getHeight()/4);
		
		calcola = new JButton("Calculate");
		add(calcola);
		calcola.setLocation(reset.getX() + reset.getWidth() +10, pannelloConsole.getY() + 40);
		calcola.setSize(pannelloSettaggi.getWidth()/2 -5 ,pannelloConsole.getHeight()/4);
		
		confronta = new JButton("Compares Algorithms");
		add(confronta);
		confronta.setLocation(pannelloConsole.getWidth()+20, pannelloConsole.getY() + (pannelloConsole.getHeight()/2)+20);
		confronta.setSize(pannelloSettaggi.getWidth(), reset.getHeight());

		
		BottonEvent e = new BottonEvent();
		calcola.addActionListener(e);
		reset.addActionListener(e);
		confronta.addActionListener(e);
		flushConsole.addActionListener(e);
		
						// VARIABILI UTILITA'
		funzioneScelta = 1;
		algoritmoScelto = 1;
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
    			   funzioneScelta = 1;
    			   maxFunction = (float)(Math.log(100));
	    		   if(pannelloMonteCarlo1!= null)
						pannelloMonteCarlo1.paintComponent(pannelloMonteCarlo1.getGraphics(), true, funzioneScelta);
	    	   }
	    	   else if(item.toString().equals("Square Function"))
	    	   {
    			   funzioneScelta = 2;
	    		   maxFunction = (float)Math.sqrt(100);
	    		   if(pannelloMonteCarlo1!= null)
						pannelloMonteCarlo1.paintComponent(pannelloMonteCarlo1.getGraphics(), true, funzioneScelta);
	    	   }
	    	   else if(item.toString().equals("Linear"))
	    	   {
	    		   funzioneScelta = 3;
	    		   maxFunction = 50;
	    		   if(pannelloMonteCarlo1!= null)
						pannelloMonteCarlo1.paintComponent(pannelloMonteCarlo1.getGraphics(), true, funzioneScelta);
	    	   }
	    	   else if(item.toString().equals("PiGreco Function"))
	    	   {
		    		   funzioneScelta = 4;
		    		   maxFunction = 1;
		    		   if(pannelloMonteCarlo1!= null)
							pannelloMonteCarlo1.paintComponent(pannelloMonteCarlo1.getGraphics(), true, 4);
	    	   }
	    	   else if(item.toString().equals("Hit or Miss"))
	    	   {
	    		   algoritmoScelto = 1;
	    	   }
	    	   else if(item.toString().equals("Sample-Mean"))
	    	   {
	    		   algoritmoScelto = 2;
	    	   }
	    	   else if(item.toString().equals("Antithetic Variates"))
	    	   {
	    		   algoritmoScelto = 3;
	    	   }
	       }
	    }       
	}
	
	public class BottonEvent implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String operation = e.getActionCommand();
			float dati[];
			if(operation.equals("Calculate"))
			{
				if(pannelloMonteCarlo1!= null)
				{	
					pannelloMonteCarlo1.paintComponent(pannelloMonteCarlo1.getGraphics(), true, funzioneScelta);
				}
				switch (algoritmoScelto)
				{
					case 1:
						dati = pannelloMonteCarlo1.HitOrMiss(Integer.parseInt(comboBoxN.getSelectedItem().toString()), funzioneScelta, maxFunction, comboBoxIntervallo.getSelectedIndex(), false, pannelloMonteCarlo1.getGraphics());
						console.setText(console.getText()+"	Theta: "+dati[0]+"		Dev Standard: "+dati[1]+"		Intervall: ["+dati[2]+", "+dati[3]+"]"+"\n");
						break;
					case 2:
						dati = pannelloMonteCarlo1.SampleMean(Integer.parseInt(comboBoxN.getSelectedItem().toString()), funzioneScelta, maxFunction, comboBoxIntervallo.getSelectedIndex(), false, pannelloMonteCarlo1.getGraphics());
						console.setText(console.getText()+"	Theta: "+dati[0]+"		Dev Standard: "+dati[1]+"		Intervall: ["+dati[2]+", "+dati[3]+"]"+"\n");
						break;
					case 3:
						dati = pannelloMonteCarlo1.AntitheticVariates(Integer.parseInt(comboBoxN.getSelectedItem().toString()),funzioneScelta, maxFunction, comboBoxIntervallo.getSelectedIndex(), false, pannelloMonteCarlo1.getGraphics());
						console.setText(console.getText()+"	Theta: "+dati[0]+"		Dev Standard: "+dati[1]+"		Intervall: ["+dati[2]+", "+dati[3]+"]"+"\n");
						break;
					default:
						pannelloMonteCarlo1.HitOrMiss(Integer.parseInt(comboBoxN.getSelectedItem().toString()),funzioneScelta, maxFunction, comboBoxIntervallo.getSelectedIndex(), false, pannelloMonteCarlo1.getGraphics());
						break;
				}
				pannelloMonteCarlo1.calcoloMS();
			}
			else if(operation.equals("Reset"))
			{
				pannelloMonteCarlo1.compares = true;
				pannelloMonteCarlo1.pannelloDevSt = false;
				pannelloMonteCarlo1.pannelloTheta = false;
				pannelloMonteCarlo1.paintComponent(pannelloMonteCarlo1.getGraphics(), true, funzioneScelta);
			} 
			else if(operation.equals("Compares Algorithms"))
			{
				GuiCompares.setVisible(true);
				GuiCompares.repaint();
				GuiCompares.labelComparazioneAV.setText("");
				GuiCompares.labelComparazioneSample.setText("");
				GuiCompares.labelComparazioneHit.setText("");
				GuiCompares.pannelloMonteCarlo2.theta = 0;
				GuiCompares.pannelloMonteCarlo2.devSt = 0;
				GuiCompares.pannelloMonteCarlo3.theta = 0;
				GuiCompares.pannelloMonteCarlo3.devSt = 0;
				GuiCompares.pannelloMonteCarlo2.compares = false;
				GuiCompares.pannelloMonteCarlo3.compares = false;
				GuiCompares.pannelloMonteCarlo3.pannelloDevSt = true;
				GuiCompares.pannelloMonteCarlo2.pannelloTheta = true;
				GuiCompares.pannelloMonteCarlo3.paint(GuiCompares.pannelloMonteCarlo3.getGraphics());
				GuiCompares.pannelloMonteCarlo2.paint(GuiCompares.pannelloMonteCarlo2.getGraphics());
			}
			else if(operation.equals("Click here for flush console"))
			{
				console.setText("");
			}
		}
	}
	
	public void Dispose()
	{
		this.dispose();
	}
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
	{
		Gui Gui = new Gui();
		Gui.paint(Gui.getGraphics());
		Gui.paintComponents(Gui.getGraphics());
		Gui.pannelloMonteCarlo1.paintComponent(Gui.pannelloMonteCarlo1.getGraphics(), true, 1);
	}
}