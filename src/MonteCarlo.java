import javax.swing.JPanel;
import java.awt.*;

public class MonteCarlo extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int xMin, xMax, yMin, yMax;
	int larghezza, altezza;
	float fattoreScalaX, fattoreScalaY;
	
	long startHitorMiss, startSampleMean, startAntitheticVariates;
	long endHitorMiss, endSampleMean, endAntitheticVariates;
	int funzioneScelta;
	boolean compares = true, pannelloTheta = false, pannelloDevSt = false;
	
	int [] posizioneN;
	
	int [] puntiY = new int[18];
	int [] puntiYDev = new int[18];
	float theta, devSt;
	
	//Funzione Logaritmica 100 - 5
	//Funzione Radice 100 - 10
	//Funzione Linear 50 - 50
	//Funzione PiGreco 1 - 1
	
	
	public MonteCarlo(int funzioneScelta, int larghezza, int altezza)
	{
		this.funzioneScelta = funzioneScelta;
		this.larghezza = larghezza-70;
		this.altezza = altezza-35;
		switch (funzioneScelta)
		{
			case 1:
				this.xMin=1;
				this.xMax=100;
				this.yMin=1;
				this.yMax=5;
				break;
			case 2:
				this.xMin=1;
				this.xMax=100;
				this.yMin=1;
				this.yMax=10;
				break;
			case 3:
				this.xMin=1;
				this.xMax=50;
				this.yMin=1;
				this.yMax=50;
				break;
			case 4:
				this.xMin=0;
				this.xMax=1;
				this.yMin=0;
				this.yMax=1;
				break;
			default:
				this.xMin=1;
				this.xMax=100;
				this.yMin=1;
				this.yMax=100;
				break;
		}
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		if(compares==false)
		{
			//Disegno Asse X
			int i = 0;
			posizioneN = new int[6];
			g.drawString("100", i+=(larghezza-60)/6, altezza+15);
			posizioneN[0] = i;
			g.drawString("500", i+=(larghezza-60)/6, altezza+15);
			posizioneN[1] = i;
			g.drawString("1000", i+=(larghezza-60)/6, altezza+15);
			posizioneN[2] = i;
			g.drawString("5000", i+=(larghezza-60)/6, altezza+15);
			posizioneN[3] = i;
			g.drawString("10000", i+=(larghezza-60)/6, altezza+15);
			posizioneN[4] = i;
			g.drawString("50000", i+=(larghezza-60)/6, altezza+15);
			posizioneN[5] = i;
			//Disegno Asse Y
			if(pannelloTheta == true)
			{
				g.drawString("CONFIDENCE INTERVALL GRAPH", larghezza/2-50 , 20);
				
				g.setColor(Color.blue);
				g.drawLine(larghezza-100, 15, larghezza-75, 15);
				g.drawLine(larghezza-100, 16, larghezza-75, 16);
				g.drawLine(larghezza-100, 17, larghezza-75, 17);
				g.setColor(Color.black);
				g.drawString("Hit Or Miss", larghezza-70 , 20);
				
				g.setColor(Color.red);
				g.drawLine(larghezza-100, 25, larghezza-75, 25);
				g.drawLine(larghezza-100, 26, larghezza-75, 26);
				g.drawLine(larghezza-100, 27, larghezza-75, 27);
				g.setColor(Color.black);
				g.drawString("Sample Mean", larghezza-70 , 30);
				
				g.setColor(Color.green);
				g.drawLine(larghezza-100, 35, larghezza-75, 35);
				g.drawLine(larghezza-100, 36, larghezza-75, 36);
				g.drawLine(larghezza-100, 37, larghezza-75, 37);
				g.setColor(Color.black);
				g.drawString("Antithetic Variates", larghezza-70 , 40);
				
				if(theta<1)
				{
					for(int w=altezza-((altezza)/10)+30, j=1; w>0 && j<10; w-=(altezza)/10, j++)
					{
						g.drawString("0,"+j, 5, w );
						g.drawLine(35, w-4, 28, w-4);
						if(j==9)
						{
							g.drawString("1", 20, w-(altezza)/10);
							g.drawLine(35, w-4-(altezza)/10, 28, w-4-(altezza)/10);
							g.drawString("F(x)", 10, w-4-(altezza)/10-10 ); 
						}
					}
				}
				else
				{
					for(int w=altezza+5, j=(int)theta/9; w>0 && j<=theta+(theta/2); w-=(altezza-70)/17)
					{
						g.drawString(""+j, 10 , w-=(altezza-70)/17);
						j+=theta/9;
					}
				}
			}
			else if(pannelloDevSt == true)
			{
				g.drawString("DEVIATION STANDARD GRAPH", larghezza/2-25 , 20 );
				
				g.setColor(Color.blue);
				g.drawLine(larghezza-100, 15, larghezza-75, 15);
				g.drawLine(larghezza-100, 16, larghezza-75, 16);
				g.drawLine(larghezza-100, 17, larghezza-75, 17);
				g.setColor(Color.black);
				g.drawString("Hit Or Miss", larghezza-70 , 20);
				
				g.setColor(Color.red);
				g.drawLine(larghezza-100, 25, larghezza-75, 25);
				g.drawLine(larghezza-100, 26, larghezza-75, 26);
				g.drawLine(larghezza-100, 27, larghezza-75, 27);
				g.setColor(Color.black);
				g.drawString("Sample Mean", larghezza-70 , 30);
				
				g.setColor(Color.green);
				g.drawLine(larghezza-100, 35, larghezza-75, 35);
				g.drawLine(larghezza-100, 36, larghezza-75, 36);
				g.drawLine(larghezza-100, 37, larghezza-75, 37);
				g.setColor(Color.black);
				g.drawString("Antithetic Variates", larghezza-70 , 40);
				
				if(devSt<2)
				{
					for(int w=altezza-((altezza)/10)+30, j=1; w>0 && j<10; w-=(altezza)/10, j++)
					{
						g.drawString("0,"+j, 5, w );
						g.drawLine(35, w-4, 28, w-4);
						if(j==9)
						{
							g.drawString("1", 20, w-(altezza)/10);
							g.drawLine(35, w-4-(altezza)/10, 28, w-4-(altezza)/10);
							g.drawString("F(x)", 10, w-4-(altezza)/10-10 ); 
						}
					}
				}
				else
				{
					int w, j;
					for(w=altezza-((altezza)/7)+30, j=1; w>0 && j<8; w-=(altezza)/7, j++)
					{
						g.drawString(""+j, 5, w );
						g.drawLine(35, w-4, 28, w-4);
					}
					g.drawString("F(x)", 10, w-4-(altezza)/7-10 ); 
				}
			}
		}
		g.setColor(Color.black);
		//Asse Y
		g.drawLine(35, 35, 35, altezza);
		//Asse X
		g.drawLine(35, altezza, larghezza, altezza);
		//Punto Origine
		g.drawString("0", 30, altezza+15);

	}
	public void paintComponent(Graphics g, boolean ciao, int funzione)
	{
		super.paintComponent(g);
		paint(g);
		compares = ciao;
		
		if(ciao)
		{
			if(funzione==1)    /////// LOGARITMO ///////////
			{
				xMax=100;
				xMin=0;
				yMax=5;
				yMin=0;
				funzioneScelta=1;
				fattoreScalaX=(larghezza-70)/((float)xMax-xMin);
				fattoreScalaY=(altezza-35)/((float)yMax-yMin);
				g.drawString("LOGARITHM FUNCTION GRAPH", larghezza/2-50 , 20); 
				//Disegno Asse X
				for(int i=36, j=10; i<larghezza && j<101; i+=(larghezza-35)/10, j+=10)
				{
					g.drawString(""+j, i+(larghezza-350)/10, altezza+15);
					if(j>=100)
					{
						g.drawString("X", i+(larghezza-350)/10+50, altezza+25);
					}
				}
				//Disegno Asse Y
				for(int i=altezza-((altezza-70)/5), j=1; i>0 && j<6; i-=(altezza-35)/5, j++)
				{
					g.drawString(""+j, 15, i ); 
					g.drawLine(35, i-4, 28, i-4);
					if(j>=5)
					{
						g.drawString("F(x)", 10, i-20 ); 
					}
				}
				// Disegno i Pixel
				for (int ix=1; ix<larghezza; ix++)
				{
					float x = xMin+((float)ix)/fattoreScalaX; 
					setPixel(g,x,funzioneLogaritmica(x), 1);
				}
			}
			else if(funzione==2)   /////// RADICE ///////////
			{
				xMax=100;
				xMin=0;
				yMax=10;
				yMin=0;
				funzioneScelta=2;
				fattoreScalaX=(larghezza-70)/((float)xMax-xMin);
				fattoreScalaY=(altezza-35)/((float)yMax-yMin);
				g.drawString("SQUARE FUNCTION SQUARE", larghezza/2-50 , 20); 
				//Disegno Asse X
				for(int i=36, j=10; i<larghezza && j<101; i+=(larghezza-35)/10, j+=10)
				{
					g.drawString(""+j, i+(larghezza-350)/10, altezza+15);
					if(j>=100)
					{
						g.drawString("X", i+(larghezza-350)/10+50, altezza+25);
					}
				}
				//Disegno Asse Y
				for(int i=altezza-35, j=1; i>0 && j<11; i-=(altezza-20)/10, j++)
				{
					g.drawString(""+j, 15, i ); 
					g.drawLine(35, i-4, 28, i-4);
					if(j>=10)
					{
						g.drawString("F(x)", 10, i-20 ); 
					}
				}
				// Disegno i Pixel
				for (int ix=1; ix<larghezza; ix++)
				{
					float x = xMin+((float)ix)/fattoreScalaX;
					setPixel(g,x,funzioneRadice(x), 1);
				}
			}
			else if(funzione==3)     /////// LINEARE ///////////
			{
				xMax=50;
				xMin=1;
				yMax=50;
				yMin=1;
				funzioneScelta=3;
				fattoreScalaX=(larghezza-70)/((float)xMax-xMin);
				fattoreScalaY=(altezza-35)/((float)yMax-yMin);
				g.drawString("LINEAR FUNCTION GRAPH", larghezza/2-50 , 20); 
				//Disegno Asse X
				for(int i=36, j=5; i<larghezza && j<51; i+=(larghezza-35)/10, j+=5)
				{
					g.drawString(""+j, i+(larghezza-350)/10, altezza+15);
					if(j>=50)
					{
						g.drawString("X", i+(larghezza-350)/10+50, altezza+25);
					}
				}
				//Disegno Asse Y
				for(int i=altezza-35, j=5; i>0 && j<51; i-=(altezza-35)/10, j+=5)
				{
					g.drawString(""+j, 10, i );
					g.drawLine(35, i-4, 28, i-4);
					if(j>=50)
					{
						g.drawString("F(x)", 10, i-20 ); 
					}
				}
				// Disegno i Pixel
				for (int ix=1; ix<larghezza; ix++)
				{
					float x = xMin+((float)ix)/fattoreScalaX;
					setPixel(g,x, funzioneLineare(x), 1);
				}
			}
			else if(funzione==4) 		/////// PiGreco ///////////
			{
				xMax=1;
				xMin=0;
				yMax=1;
				yMin=0;
				funzioneScelta=4;
				fattoreScalaX=(larghezza-70)/((float)xMax-xMin);
				fattoreScalaY=(altezza-35)/((float)yMax-yMin);
				g.drawString("PI-GRECO FUNCTION GRAPH ( √(1-x^2) )", larghezza/2-50 , 20); 
				//Disegno Asse X
				for(int i=(larghezza-35)/10, j=1; i<larghezza && j<10; i+=(larghezza-35)/10, j++)
				{
					g.drawString("0,"+j, i, altezza+15);
					if(j==9)
					{
						g.drawString("1", i+(larghezza-35)/10, altezza+15);
						g.drawString("X", i+(larghezza-35)/10+50, altezza+25);
					}
				}
				//Disegno Asse Y
				for(int i=altezza-((altezza)/10)+30, j=1; i>0 && j<10; i-=(altezza)/10, j++)
				{
					g.drawString("0,"+j, 5, i );
					g.drawLine(35, i-4, 28, i-4);
					if(j==9)
					{
						g.drawString("1", 20, i-(altezza)/10);
						g.drawLine(35, i-4-(altezza)/10, 28, i-4-(altezza)/10);
						g.drawString("F(x)", 10, i-4-(altezza)/10-10 ); 
					}
				}
				// Disegno i Pixel
				for (int ix=1; ix<larghezza; ix++)
				{
					float x = xMin+((float)ix)/fattoreScalaX;
					setPixel(g,x,funzionePiGreco(x), 1);
				}
			}
		}
	}
	
	public void paintGraphs(Graphics g, int indice, float y, boolean graphs, int tipodraw)
	{
		if(graphs)
		{
			if(tipodraw==0)
			{
				g.setColor(Color.blue);
				g.fillOval(posizioneN[indice], (int)y, 6, 6);
			}
			else if(tipodraw==1)
			{
				g.setColor(Color.red);
				g.fillOval(posizioneN[indice], (int)y, 6, 6);
			}
			else
			{
				g.setColor(Color.green);
				g.fillOval(posizioneN[indice], (int)y, 6, 6);
			}
		}
		else
		{
			if(tipodraw==0)
			{
				g.setColor(Color.blue);
				g.fillOval(posizioneN[indice], (int)y, 6, 6);
			}
			else if(tipodraw==1)
			{
				g.setColor(Color.red);
				g.fillOval(posizioneN[indice], (int)y, 6, 6);
			}
			else
			{
				g.setColor(Color.green);
				g.fillOval(posizioneN[indice], (int)y, 6, 6);
			}
		}
	}
	
	public void paintLine(Graphics g, boolean graphs)
	{
		if(graphs)
		{
			for(int i=0, j=0; i<5 && j<5; i++, j++)
			{
				g.setColor(Color.blue);
				g.drawLine(posizioneN[j],puntiY[i], posizioneN[j+1], puntiY[i+1]);
				g.setColor(Color.red);
				g.drawLine(posizioneN[j],puntiY[i+6], posizioneN[j+1], puntiY[i+7]);
				g.setColor(Color.green);
				g.drawLine(posizioneN[j],puntiY[i+12], posizioneN[j+1], puntiY[i+13]);
			}
		}
		else
		{
			for(int i=0, j=0; i<5 && j<5; i++, j++)
			{
				g.setColor(Color.blue);
				g.drawLine(posizioneN[j],puntiYDev[i], posizioneN[j+1], puntiYDev[i+1]);
				g.setColor(Color.red);
				g.drawLine(posizioneN[j],puntiYDev[i+6], posizioneN[j+1], puntiYDev[i+7]);
				g.setColor(Color.green);
				g.drawLine(posizioneN[j],puntiYDev[i+12], posizioneN[j+1], puntiYDev[i+13]);
			}
		}

	}
	public static float funzioneLogaritmica(float x)
	{
		return (float)(Math.log(x));
	}
	public static float funzioneRadice(float x)
	{
		return (float)Math.sqrt(x);
	}
	public static float funzioneLineare(float x)
	{
		return (float)(x);
	}
	public static float funzionePiGreco(float x)
	{
		return (float)(Math.sqrt(1-Math.pow(x, 2)));
	}
	public void setPixel(Graphics g, float x, float y, int TipoDraw)
	{
		if ( x<xMin || x>xMax || y<yMin || y>yMax )
		{	
			return;
		}
		int ix = Math.round((x-xMin)*fattoreScalaX);
		int iy = altezza - Math.round((y-yMin) * fattoreScalaY)-5;
		
		if(TipoDraw==1)
		{
			g.drawLine(ix+35,iy,ix+35,iy); 
		}
		else if(TipoDraw==2)
		{
			switch (funzioneScelta)
			{
				case 1:
					//ix = ix*(larghezza-70)/1000;
					break;
				case 2:
					//ix = ix*(larghezza-70)/1000;
					break;
				case 3:
					//ix = ix*100;
					//ix = ix*(larghezza-70)/90;
					break;
				case 4:
					break;
				default :
					ix = ix*(larghezza-70)/1000;
					break;
			}
			g.fillOval(ix+35, iy, 7, 7);
		}
		else if(TipoDraw==3)
		{
			switch (funzioneScelta)
			{
				case 1:
					
					g.fillOval(ix+35, iy, 7, 7);
					break;
				case 2:
					g.fillOval(ix+35, iy, 7, 7);
					break;
				case 3:
					g.fillOval(ix+35, iy, 7, 7);
					break;
				case 4:
					
					break;
				default :
					g.fillOval(ix+35, iy, 7, 7);
					break;
			}
		}
	}
	
										//////   COMMENTI DEBUG
	/*System.out.println("Nh ="+ Nh);
	System.out.println("N ="+N);
	System.out.println("c ="+c);
	System.out.println("xMax ="+xMax);
	System.out.println("xMin ="+xMin);
	System.out.println("Nh/N ="+parametroP);
	System.out.println("xMax-xMin="+(xMax - xMin));
	
	System.out.println(x+"   "+y);
	System.out.println(ix+"   "+iy);

	System.out.println(Ifiducia);
	System.out.println((float)Math.pow(((parametroP*(1-parametroP))/N), 0.5f)); //deviazione Standard
	System.out.println(intervallo1);
	System.out.println(intervallo2);*/
	
	public float[] HitOrMiss(int N, int	 funzioneScelta, float c, int percentuale, boolean compares, Graphics g)
	{
		startHitorMiss = System.currentTimeMillis();
		if(g != null)
		{
			super.paintComponents(g);
		}
		
		float random1, random2 = 0, xi, yi = 0, fXi;
		int Nh = 0;
		float [] dati = new float[4];
		
		//Trovo i punti che sono sotto la funzione
		for (int i=0; i<N; i++)
		{
			switch (funzioneScelta)
			{
				case 1:
					random1 = (float) (Math.random());
					random2 = (float) (Math.random());
					xi = (float) (xMin + (random1 *(xMax - xMin)));
					yi = (float) random2*c;
					fXi = funzioneLogaritmica(xi);
					break;
				case 2:
					random1 = (float) (Math.random());
					random2 = (float) (Math.random());
					xi = (float) (xMin + (random1 *(xMax - xMin)));
					yi = (float) random2*c;
					fXi = funzioneRadice(xi);
					break;
				case 3:
					random1 = (float) (Math.random());
					random2 = (float) (Math.random());
					xi = (float) (xMin + (random1 *(xMax - xMin)));
					yi = (float) random2*c;
					fXi = funzioneLineare(xi);
					break;
				case 4:
					random1 = (float) (Math.random());
					random2 = (float) (Math.random());
					xi = (float) (xMin + (random1 *(xMax - xMin)));
					yi = (float) random2*c;
					fXi = funzionePiGreco(xi);
					break;
				default :
					random1 = (float) (Math.random());
					random2 = (float) (Math.random());
					xi = (float) (xMin + (random1 *(xMax - xMin)));
					yi = (float) random2*c;
					fXi = funzioneLogaritmica(xi);
					break;
			}
			
			
			if(!compares)
			{
				if(fXi >= c*random2)
				{				
					Nh++;
					g.setColor(Color.blue);
					setPixel(g, xi, yi, 2);
				}
				else
				{
					g.setColor(Color.red);
					setPixel(g, xi, yi, 2);
				}
			}
			else
			{
				if(fXi >= c*random2)
				{
					Nh++;
				}
			}
		}
		
		//Calcolo L'integrale di fiducia
		float parametroP = (float) Nh/N;
		float theta = (float)(c * (xMax - xMin) * parametroP);

		// Deviazione standard
		float devStandard = (float) Math.pow(((parametroP*(1-parametroP))/N), 0.5f);


		//Intervallo di confidenza
		float intervallo1 =0;
		float intervallo2 =0;
		switch (percentuale)
		{
			case 0:
				intervallo1 = (float) (theta - (1.64 * devStandard * c * (xMax - xMin)));
				intervallo2 = (float) (theta + (1.64 * devStandard * c * (xMax - xMin)));
				break;
			case 1:
				intervallo1 = (float) (theta - (1.96 * devStandard * c * (xMax - xMin)));
				intervallo2 = (float) (theta + (1.96 * devStandard * c * (xMax - xMin)));	
				break;
			case 2:
				intervallo1 = (float) (theta - (2.57 * devStandard * c * (xMax - xMin)));
				intervallo2 = (float) (theta + (2.57 * devStandard * c * (xMax - xMin)));
				break;
			default:
				intervallo1 = (float) (theta - (1.64 * devStandard * c * (xMax - xMin)));
				intervallo2 = (float) (theta + (1.64 * devStandard * c * (xMax - xMin)));
				break;
		}
		dati[0] = theta;
		dati[1] = devStandard;
		dati[2] = intervallo1;
		dati[3] = intervallo2;

		
		endHitorMiss = System.currentTimeMillis();
		return dati;
	}
	
	public float[] SampleMean(int N, int funzioneScelta, float c, int percentuale, boolean compares, Graphics g)
	{
		startSampleMean = System.currentTimeMillis();
		if(g != null)
		{
			super.paintComponents(g);
		}
		
		float U, xi, fXi[] = new float[N];
		float StimaIntegrale = 0;
		float [] dati = new float[4];
				
		for(int i=0; i<N; i++)
		{
            switch (funzioneScelta)
			{
				case 1:
		            U = (float)Math.random();
					xi = (float) (xMin + (U *(xMax - xMin)));
					fXi[i] = funzioneLogaritmica(xi);
					StimaIntegrale += fXi[i];
					break;
				case 2:
		            U = (float) Math.random();
					xi = (float) (xMin + (U *(xMax - xMin)));
					fXi[i] = funzioneRadice(xi);
					StimaIntegrale += fXi[i];
					break;
				case 3:
		            U = (float) Math.random();
					xi = (float) (xMin + (U *(xMax - xMin)));
		 			fXi[i] = funzioneLineare(xi);
					StimaIntegrale += fXi[i];
					break;
				case 4:
		            U = (float) Math.random();
					xi = (float) (xMin + (U *(xMax - xMin)));
					fXi[i] = funzionePiGreco(xi);
					StimaIntegrale += fXi[i];
					break;
				default :
					U = (float)(Math.random());
					xi = (float) (xMin + (U *(xMax - xMin)));
					fXi[i] = xi;
					StimaIntegrale += fXi[i];
					break;
			}
            
            if(!compares)
			{
	            if(fXi[i] >= xi)
				{
					g.setColor(Color.blue);
					setPixel(g, xi, fXi[i], 2);
				}
				else
				{
					g.setColor(Color.red);
					setPixel(g, xi, fXi[i], 2);
				}
			}
		}
		
		//Calcolo theta
		float theta = (StimaIntegrale * (xMax - xMin))/N;
		
		//Calcolo Media
		float media = Media(fXi);
		
		//Calcolo Varianza
		float varianza = Varianza(fXi, media);
		
		float xMaxMinDifference = (float) Math.pow(xMax - xMin, 2);
		float NSquare = (float) Math.pow(N,2);
		float DevStandard = (float) Math.pow( (xMaxMinDifference / NSquare)* varianza, 0.5);
		
		//Intervallo di confidenza
		float intervallo1 = 0;
		float intervallo2 = 0;
		switch (percentuale)
		{
			case 0:
				intervallo1 = (float) (media - (1.64 * DevStandard * Math.pow(N, 0.5)));
				intervallo2 = (float) (media + (1.64 * DevStandard * Math.pow(N, 0.5)));
				break;
			case 1:
				intervallo1 = (float) (media - (1.96 * DevStandard * Math.pow(N, 0.5)));
				intervallo2 = (float) (media + (1.96 * DevStandard * Math.pow(N, 0.5)));	
				break;
			case 2:
				intervallo1 = (float) (media - (2.57 * DevStandard * Math.pow(N, 0.5)));
				intervallo2 = (float) (media + (2.57 * DevStandard * Math.pow(N, 0.5)));
				break;
			default:
				intervallo1 = (float) (media - (1.64 * DevStandard * Math.pow(N, 0.5)));
				intervallo2 = (float) (media + (1.64 * DevStandard * Math.pow(N, 0.5)));
				break;
		}
		
		dati[0] = theta;
		dati[1] = DevStandard;
		dati[2] = intervallo1;
		dati[3] = intervallo2;
		
		endSampleMean = System.currentTimeMillis();
		return dati;
	}
	
	public float[] AntitheticVariates(int N, int funzioneScelta, float c, int percentuale, boolean compares, Graphics g)
	{
		startAntitheticVariates = System.currentTimeMillis();
		if(g != null)
		{
			super.paintComponents(g);
		}
		
		float U, xi, fXi[] = new float[N];
		float StimaIntegrale = 0;
		float [] dati = new float[4];
				
		for(int i=0; i<N; i++)
		{
            switch (funzioneScelta)
			{
				case 1:
		            U = (float) Math.random();
					xi = (float) (xMin + (U *(xMax - xMin)));
					fXi[i] = funzioneLogaritmica(xi);
					StimaIntegrale += fXi[i];
					break;
				case 2:
		            U = (float) Math.random();
					xi = (float) (xMin + (U *(xMax - xMin)));
					fXi[i] = funzioneRadice(xi);
					StimaIntegrale += fXi[i];
					break;
				case 3:
		            U = (float) Math.random();
					xi = (float) (xMin + (U *(xMax - xMin)));
		 			fXi[i] = funzioneLineare(xi);
					StimaIntegrale += fXi[i];
					break;
				case 4:
		            U = (float) Math.random();
					xi = (float) (xMin + (U *(xMax - xMin)));
					fXi[i] = funzionePiGreco(xi);
					StimaIntegrale += fXi[i];
					break;
				default :
					U = (float)(Math.random()*(xMax));
					xi = (float) (xMin + (U *(xMax - xMin)));
					fXi[i] = xi;
					StimaIntegrale += fXi[i];
					break;
			}
            
            if(!compares)
			{
	            if(fXi[i] >= xi)
				{
					g.setColor(Color.blue);
					setPixel(g, xi, fXi[i], 2);
				}
				else
				{
					g.setColor(Color.red);
					setPixel(g, xi, fXi[i], 2);
				}
			}
		}
		
		//Calcolo theta
		float theta = (StimaIntegrale * (xMax - xMin))/N;
		
		//Calcolo Media
		float media = Media(fXi);
		
		//Calcolo Varianza
		float varianza = Varianza(fXi, media);
		
		float xMaxMinDifference = (float) Math.pow(xMax - xMin, 2);
		float NSquare = (float) Math.pow(N,2);
		float DevStandard = (float) Math.pow( (xMaxMinDifference / NSquare)* varianza, 0.5);
		
		//Intervallo di confidenza
		float intervallo1 = 0;
		float intervallo2 = 0;
		switch (percentuale)
		{
			case 0:
				intervallo1 = (float) (media - (1.64 * DevStandard * Math.pow(N, 0.5)));
				intervallo2 = (float) (media + (1.64 * DevStandard * Math.pow(N, 0.5)));
				break;
			case 1:
				intervallo1 = (float) (media - (1.96 * DevStandard * Math.pow(N, 0.5)));
				intervallo2 = (float) (media + (1.96 * DevStandard * Math.pow(N, 0.5)));	
				break;
			case 2:
				intervallo1 = (float) (media - (2.57 * DevStandard * Math.pow(N, 0.5)));
				intervallo2 = (float) (media + (2.57 * DevStandard * Math.pow(N, 0.5)));
				break;
			default:
				intervallo1 = (float) (media - (1.64 * DevStandard * Math.pow(N, 0.5)));
				intervallo2 = (float) (media + (1.64 * DevStandard * Math.pow(N, 0.5)));
				break;
		}
		dati[0] = theta;
		dati[1] = DevStandard;
		dati[2] = intervallo1;
		dati[3] = intervallo2;
		
		endAntitheticVariates = System.currentTimeMillis();
		return dati;
	}
	
	private float Media(float x[])
	{
		float media=0f;
		float totale = 1f/(float)x.length;
		for(int i=0;i<x.length;i++)
			media+=x[i]*totale;
	     return media;
	}
	
	private float Varianza(float x[], float media)
	{
		float varianza = 0f;
		float prob = 1f/(float)x.length;
		for(int i=0;i<x.length;i++)
			varianza+=(float)(((x[i]-media)*(x[i]-media))*prob);
		return varianza;
    }
	
	public String[] comparaAlgoritmi(float maxFunction, int percentuale, Graphics g1, MonteCarlo g2)
	{		
		String[] stringa = new String[3];
				
				
								//////		VARIABILI DI RETURN		///////
		float [] datiFinaliHit = new float[24];
		float [] datiFinaliSample = new float[24];
		float [] datiFinaliAv = new float[24];
		float [] dati = new float[4];		
		
		dati = HitOrMiss(100, funzioneScelta, maxFunction, percentuale, true, null);
		datiFinaliHit[0] = dati[0];
		datiFinaliHit[1] = dati[1];
		datiFinaliHit[2] = dati[2];
		datiFinaliHit[3] = dati[3];
		dati = HitOrMiss(500, funzioneScelta, maxFunction, percentuale, true, null);
		datiFinaliHit[4] = dati[0];
		datiFinaliHit[5] = dati[1];
		datiFinaliHit[6] = dati[2];
		datiFinaliHit[7] = dati[3];
		dati = HitOrMiss(1000, funzioneScelta, maxFunction, percentuale, true, null);
		datiFinaliHit[8] = dati[0];
		datiFinaliHit[9] = dati[1];
		datiFinaliHit[10] = dati[2];
		datiFinaliHit[11] = dati[3];
		theta = Math.max(datiFinaliHit[0], Math.max(datiFinaliHit[4], datiFinaliHit[8]));
		devSt = Math.max(datiFinaliHit[1], Math.max(datiFinaliHit[5], datiFinaliHit[9]));
		dati = HitOrMiss(5000, funzioneScelta, maxFunction, percentuale, true, null);
		datiFinaliHit[12] = dati[0];
		datiFinaliHit[13] = dati[1];
		datiFinaliHit[14] = dati[2];
		datiFinaliHit[15] = dati[3];
		dati = HitOrMiss(10000, funzioneScelta, maxFunction, percentuale, true, null);
		datiFinaliHit[16] = dati[0];
		datiFinaliHit[17] = dati[1];
		datiFinaliHit[18] = dati[2];
		datiFinaliHit[19] = dati[3];
		theta = Math.max(theta, Math.max(datiFinaliHit[12], datiFinaliHit[16]));
		devSt = Math.max(devSt, Math.max(datiFinaliHit[13], datiFinaliHit[17]));
		dati = HitOrMiss(50000, funzioneScelta, maxFunction, percentuale, true, null);
		datiFinaliHit[20] = dati[0];
		datiFinaliHit[21] = dati[1];
		datiFinaliHit[22] = dati[2];
		datiFinaliHit[23] = dati[3];
		
		dati = SampleMean(100, funzioneScelta, maxFunction, percentuale, true, null);
		datiFinaliSample[0] = dati[0];
		datiFinaliSample[1] = dati[1];
		datiFinaliSample[2] = dati[2];
		datiFinaliSample[3] = dati[3];
		theta = Math.max(theta, Math.max(datiFinaliHit[20], datiFinaliSample[0]));
		devSt = Math.max(devSt, Math.max(datiFinaliHit[21], datiFinaliSample[1]));
		dati = SampleMean(500, funzioneScelta, maxFunction, percentuale, true, null);
		datiFinaliSample[4] = dati[0];
		datiFinaliSample[5] = dati[1];
		datiFinaliSample[6] = dati[2];
		datiFinaliSample[7] = dati[3];
		dati = SampleMean(1000, funzioneScelta, maxFunction, percentuale, true, null);
		datiFinaliSample[8] = dati[0];
		datiFinaliSample[9] = dati[1];
		datiFinaliSample[10] = dati[2];
		datiFinaliSample[11] = dati[3];
		theta = Math.max(theta, Math.max(datiFinaliSample[4], datiFinaliSample[8]));
		devSt = Math.max(devSt, Math.max(datiFinaliSample[5], datiFinaliSample[9]));
		dati = SampleMean(5000, funzioneScelta, maxFunction, percentuale, true, null);
		datiFinaliSample[12] = dati[0];
		datiFinaliSample[13] = dati[1];
		datiFinaliSample[14] = dati[2];
		datiFinaliSample[15] = dati[3];
		dati = SampleMean(10000, funzioneScelta, maxFunction, percentuale, true, null);
		datiFinaliSample[16] = dati[0];
		datiFinaliSample[17] = dati[1];
		datiFinaliSample[18] = dati[2];
		datiFinaliSample[19] = dati[3];
		theta = Math.max(theta, Math.max(datiFinaliSample[12], datiFinaliSample[16]));
		devSt = Math.max(devSt, Math.max(datiFinaliSample[13], datiFinaliSample[17]));
		dati = SampleMean(50000, funzioneScelta, maxFunction, percentuale, true, null);
		datiFinaliSample[20] = dati[0];
		datiFinaliSample[21] = dati[1];
		datiFinaliSample[22] = dati[2];
		datiFinaliSample[23] = dati[3];
		
		dati = AntitheticVariates(100, funzioneScelta, maxFunction, percentuale, true, null);
		datiFinaliAv[0] = dati[0];
		datiFinaliAv[1] = dati[1];
		datiFinaliAv[2] = dati[2];
		datiFinaliAv[3] = dati[3];
		theta = Math.max(theta, Math.max(datiFinaliSample[20], datiFinaliAv[0]));
		devSt = Math.max(devSt, Math.max(datiFinaliAv[1], datiFinaliSample[21]));
		dati = AntitheticVariates(500, funzioneScelta, maxFunction, percentuale, true, null);
		datiFinaliAv[4] = dati[0];
		datiFinaliAv[5] = dati[1];
		datiFinaliAv[6] = dati[2];
		datiFinaliAv[7] = dati[3];
		dati = AntitheticVariates(1000, funzioneScelta, maxFunction, percentuale, true, null);
		datiFinaliAv[8] = dati[0];
		datiFinaliAv[9] = dati[1];
		datiFinaliAv[10] = dati[2];
		datiFinaliAv[11] = dati[3];
		theta = Math.max(theta, Math.max(datiFinaliAv[4], datiFinaliAv[8]));
		devSt = Math.max(devSt, Math.max(datiFinaliAv[5], datiFinaliAv[9]));
		dati = AntitheticVariates(5000, funzioneScelta, maxFunction, percentuale, true, null);
		datiFinaliAv[12] = dati[0];
		datiFinaliAv[13] = dati[1];
		datiFinaliAv[14] = dati[2];
		datiFinaliAv[15] = dati[3];
		dati = AntitheticVariates(10000, funzioneScelta, maxFunction, percentuale, true, null);
		datiFinaliAv[16] = dati[0];
		datiFinaliAv[17] = dati[1];
		datiFinaliAv[18] = dati[2];
		datiFinaliAv[19] = dati[3];
		dati = AntitheticVariates(50000, funzioneScelta, maxFunction, percentuale, true, null);
		datiFinaliAv[20] = dati[0];
		datiFinaliAv[21] = dati[1];
		datiFinaliAv[22] = dati[2];
		datiFinaliAv[23] = dati[3];
		theta = Math.max(Math.max(theta, datiFinaliAv[20]), Math.max(datiFinaliAv[12], datiFinaliAv[16]));
		devSt = Math.max(Math.max(devSt, datiFinaliAv[21]), Math.max(datiFinaliAv[13], datiFinaliAv[17]));
		g2.theta = theta;
		g2.devSt = devSt;
		
		if(g1!=null && g2!=null)
		{
			pannelloTheta = true;
			pannelloDevSt = false;
			paintComponent(g1, false, 1);
			pannelloTheta = false;
			pannelloDevSt = true;
			paintComponent(g2.getGraphics(), false, 1);
			pannelloDevSt = false;
		}
		
		fattoreScalaY = (altezza-35)/((float)devSt);
		
		float y;
		float iy;
		for(int i=0, j=0; i<24; i+=4, j++)
		{
			y = (float) ((altezza)/((float)datiFinaliHit[i]/(theta/3)));
			iy = altezza - Math.round((datiFinaliHit[i+1]) * fattoreScalaY);
			puntiY[j] = (int) y;
			puntiYDev[j] = (int) iy;
			paintGraphs(getGraphics(), j, y, true, 0);
			g2.paintGraphs(g2.getGraphics(), j, iy, true, 0);			
			
			y=(float) ((altezza)/((float)datiFinaliSample[i]/(theta/3)));
			iy = altezza - Math.round((datiFinaliSample[i+1]) * fattoreScalaY);
			puntiY[j+6] = (int) y;
			puntiYDev[j+6] = (int) iy;
			g2.paintGraphs(g2.getGraphics(), j, iy, true, 1);
			paintGraphs(getGraphics(), j, y, true, 1);
			
			y=(float) ((altezza)/((float)datiFinaliAv[i]/(theta/3)));
			iy = altezza - Math.round((datiFinaliAv[i+1]) * fattoreScalaY);
			puntiY[j+12] = (int)y;
			puntiYDev[j+12] =  (int) iy;
			g2.paintGraphs(g2.getGraphics(), j, iy, true, 2);
			paintGraphs(getGraphics(), j, y, true, 2);
		}
		
		paintLine(getGraphics(), true);
		paintLine(g2.getGraphics(), false);

		stringa[0] = "<html><pre> Hit Or Miss<br><br>"+" N	Theta		DevStandard	Confidence Interval<br><br> 100	"+datiFinaliHit[0]+"	"+datiFinaliHit[1]+"	["+datiFinaliHit[2]+","+datiFinaliHit[3]+"]"+"<br>"+
				 	" 500	"+datiFinaliHit[4]+"	"+datiFinaliHit[5]+"	["+datiFinaliHit[6]+","+datiFinaliHit[7]+"]"+"<br>"+
				 	" 1000	"+datiFinaliHit[8]+"	"+datiFinaliHit[9]+"	["+datiFinaliHit[10]+","+datiFinaliHit[11]+"]"+"<br>"+
				 	" 5000	"+datiFinaliHit[12]+"	"+datiFinaliHit[13]+"	["+datiFinaliHit[14]+","+datiFinaliHit[15]+"]"+"<br>"+
				 	" 10000	"+datiFinaliHit[16]+"	"+datiFinaliHit[17]+"	["+datiFinaliHit[18]+","+datiFinaliHit[19]+"]"+"<br>"+
				 	" 50000	"+datiFinaliHit[20]+"	"+datiFinaliHit[21]+"	["+datiFinaliHit[22]+","+datiFinaliHit[23]+"]";
		stringa[0] += "</pre></html>";
		
		stringa[1] = "<html><pre> Sample Mean<br><br>"+" N	Theta		DevStandard	Confidence Interval<br><br> 100	"+datiFinaliSample[0]+"	"+datiFinaliSample[1]+"	["+datiFinaliSample[2]+","+datiFinaliSample[3]+"]"+"<br>"+
			 	" 500	"+datiFinaliSample[4]+"	"+datiFinaliSample[5]+"	["+datiFinaliSample[6]+","+datiFinaliSample[7]+"]"+"<br>"+
			 	" 1000	"+datiFinaliSample[8]+"	"+datiFinaliSample[9]+"	["+datiFinaliSample[10]+","+datiFinaliSample[11]+"]"+"<br>"+
			 	" 5000	"+datiFinaliSample[12]+"	"+datiFinaliSample[13]+"	["+datiFinaliSample[14]+","+datiFinaliSample[15]+"]"+"<br>"+
			 	" 10000	"+datiFinaliSample[16]+"	"+datiFinaliSample[17]+"	["+datiFinaliSample[18]+","+datiFinaliSample[19]+"]"+"<br>"+
			 	" 50000	"+datiFinaliSample[20]+"	"+datiFinaliSample[21]+"	["+datiFinaliSample[22]+","+datiFinaliSample[23]+"]";
		stringa[1] += "</pre></html>";

		stringa[2] = "<html><pre> Antithetic Variates<br><br>"+" N	Theta		DevStandard	Confidence Interval<br><br> 100	"+datiFinaliAv[0]+"	"+datiFinaliAv[1]+"	"+datiFinaliAv[2]+","+datiFinaliAv[3]+"]"+"<br>"+
			 	" 500	"+datiFinaliAv[4]+"	"+datiFinaliAv[5]+"	["+datiFinaliAv[6]+","+datiFinaliAv[7]+"]"+"<br>"+
			 	" 1000	"+datiFinaliAv[8]+"	"+datiFinaliAv[9]+"	["+datiFinaliAv[10]+","+datiFinaliAv[11]+"]"+"<br>"+
			 	" 5000	"+datiFinaliAv[12]+"	"+datiFinaliAv[13]+"	["+datiFinaliAv[14]+","+datiFinaliAv[15]+"]"+"<br>"+
			 	" 10000	"+datiFinaliAv[16]+"	"+datiFinaliAv[17]+"	["+datiFinaliAv[18]+","+datiFinaliAv[19]+"]"+"<br>"+
			 	" 50000	"+datiFinaliAv[20]+"	"+datiFinaliAv[21]+"	["+datiFinaliAv[22]+","+datiFinaliAv[23]+"]";
		stringa[2] += "</pre></html>";
		
		return stringa;
	}
	
	public void calcoloMS()
	{
		long totalHitorMiss = endHitorMiss - startHitorMiss;
		long totalSampleMean = endSampleMean - startSampleMean; 
		long totalAntitheticVariates = endAntitheticVariates - startAntitheticVariates;
		System.out.println(endHitorMiss+"   "+startHitorMiss);
		System.out.println(endSampleMean+"   "+startSampleMean);
		System.out.println(endAntitheticVariates+"   "+startAntitheticVariates);
		System.out.println(totalHitorMiss+"  "+totalSampleMean+"  "+totalAntitheticVariates);
	}
	
}