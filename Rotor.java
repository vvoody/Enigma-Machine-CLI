//---------------------------------
// Rotor.Java
// Written By: Russell Schwager
//             russells@jhu.edu
// April 22, 1997
//---------------------------------

import java.awt.Graphics;
import java.awt.Color;

public class Rotor 
{
	public final static int ROTORSIZE = 26;
	int		initialRotation, currentRotation;
	int		rotateUp1, rotateUp2;
	int		rotor[] = new int[ROTORSIZE];
	int		inverseRotor[] = new int[ROTORSIZE];
	boolean	buttonUp1, buttonUp2;
	Rotor	nextRotor;

	public Rotor(int whichRotor, int initialSpot, Rotor next)
	{  // Constructor

		getRotor(whichRotor);
		initialRotation = currentRotation = initialSpot;

		buttonUp1 = buttonUp2 = true;
		nextRotor = next;
	}

	private void getRotor(int whichOne)
	{  // Initialize the settings of the rotor

		switch (whichOne) {
			case 1:
				rotor = I;
				rotateUp1 = IPEG;
				rotateUp2 = -1;
			break;
			case 2:
				rotor = II;
				rotateUp1 = IIPEG;
				rotateUp2 = -1;
			break;
			case 3:
				rotor = III;
				rotateUp1 = IIIPEG;
				rotateUp2 = -1;
			break;
			case 4:
				rotor = IV;
				rotateUp1 = IVPEG;
				rotateUp2 = -1;
			break;
			case 5:
				rotor = V;
				rotateUp1 = VPEG;
				rotateUp2 = -1;
			break;
			case 6:
				rotor = VI;
				rotateUp1 = VIPEG1;
				rotateUp2 = VIPEG2;
			break;
			case 7:
				rotor = VII;
				rotateUp1 = VIIPEG1;
				rotateUp2 = VIIPEG2;
			break;
			case 8:
				rotor = VIII;
				rotateUp1 = VIIIPEG1;
				rotateUp2 = VIIIPEG2;
			break;
		}

		for (int i = 0; i < this.ROTORSIZE; i++)
	        inverseRotor[rotor[i]] = i;

	}

	public void rotate(boolean up)
	{ // Rotate the rotor returning true if a full rotation has been reached

		if (up)
			currentRotation = (currentRotation + 1) % this.ROTORSIZE;
		else
		{
			currentRotation -= 1;
			if (currentRotation < 0)
				currentRotation = this.ROTORSIZE - 1;
		}

		if (up && nextRotor != null && 
			(currentRotation == rotateUp1 || 
			currentRotation == rotateUp2))
			nextRotor.rotate(true);
	}

	public void setInitialPosition(int position)
	{ // change the initial Position of the rotor

		initialRotation = position;
	}

	public void reset()
	{ // Reset the position of the rotor

		currentRotation = initialRotation;
	}

	public int send(int offset, boolean left)
	{ // send the character through the rotor

	    offset = (offset - currentRotation + this.ROTORSIZE)
					% this.ROTORSIZE;

		if (left)
			return (rotor[offset] + currentRotation) % this.ROTORSIZE;

		return (inverseRotor[offset] + currentRotation) 
					% this.ROTORSIZE;
	}

	public void draw(Graphics g, int x, int y)
	{ // paint the rotors

		int height = 20, width = 15;
		Integer display = new Integer(currentRotation);

		g.setColor(Color.white);
		g.fillRect(x, y, width, height);

		g.setColor(Color.blue);
		g.drawString(display.toString(), x + 1, y + height - 3);

		drawButtons(g, x + width + 5, y - 5);

	}

	private void drawButtons(Graphics g, int x, int y)
	{ // draw "fake" scroll bar buttons

		g.setColor(Color.lightGray);
		g.fill3DRect(x, y, 10, 10, buttonUp1);
	
		g.setColor(Color.black);
		int x1[] = { x + 2, x + 5, x + 8 };
		int y1[] = { y + 8, y + 2, y + 8 };
		g.fillPolygon(x1, y1, 3);
		
		g.setColor(Color.lightGray);
		g.fill3DRect(x, y + 20, 10, 10, buttonUp2);

		g.setColor(Color.black);
		int x2[] = { x + 2, x + 5, x + 8 };
		int y2[] = { y + 22, y + 28, y + 22 };
		g.fillPolygon(x2, y2, 3);
	}

    public boolean mouseDown(int x, int y, int locationX, int locationY)
	{ // handles when the mouse is clicked down

		if (x >= locationX + 20 && x <= locationX + 30 &&
			y >= locationY - 5 && y <= locationY + 5)
		{
			buttonUp1 = false;
			return true;
		}
		else if (x >= locationX + 20 && x <= locationX + 30 &&
				y >= locationY + 15 && y <= locationY + 25)
		{
			buttonUp2 = false;
			return true;
		}

		buttonUp1 = buttonUp2 = true;
		return false;
	}

    public boolean mouseDrag(int x, int y, int locationX, int locationY)
	{ // handles when the mouse is dragged

		if (x >= locationX + 20 && x <= locationX + 30 &&
			y >= locationY - 5 && y <= locationY + 5)
		{
			buttonUp1 = false;
			return true;
		}
		else if (x >= locationX + 20 && x <= locationX + 30 &&
				y >= locationY + 15 && y <= locationY + 25)
		{
			buttonUp2 = false;
			return true;
		}

		buttonUp1 = buttonUp2 = true;
		return true;
	}

    public boolean mouseUp(int x, int y, int locationX, int locationY)
	{ // handles when the mouse is released

		if (x >= locationX + 20 && x <= locationX + 30 &&
			y >= locationY - 5 && y <= locationY + 5)
		{
			buttonUp1 = true;
			rotate(true);
			return true;
		}
		else if (x >= locationX + 20 && x <= locationX + 30 &&
				y >= locationY + 15 && y <= locationY + 25)
		{
			buttonUp2 = true;
			rotate(false);
			return true;
		}

		buttonUp1 = buttonUp2 = true;
		return true;

	}

	// Rotor Settings
	public static final int IPEG = 24;    // Y 
	public static final int I[] = { 4, 10, 12, 5, 11, 6, 3, 16, 
									21, 25, 13, 19, 14, 22, 24, 
									7, 23, 20, 18, 15, 0, 8, 1, 
									17, 2, 9 }; 

	public static final int IIPEG = 12;   // M 
	public static final int II[] = { 0, 9, 3, 10, 18, 8, 17, 
									 20, 23, 1, 11, 7, 22, 19, 
									 12, 2, 16, 6, 25, 13, 15, 
									 24, 5, 21, 14, 4 }; 

	public static final int IIIPEG = 3;   // D 
	public static final int III[] = { 1, 3, 5, 7, 9, 11, 2, 
									 15, 17, 19, 23, 21, 25,
									 13, 24, 4, 8, 22, 6, 0,
									 10, 12, 20, 18, 16, 14 }; 


	public static final int IVPEG = 17;   // R 
	public static final int IV[] = { 4, 18, 14, 21, 15, 25, 9, 0,
									24, 16, 20, 8, 17, 7, 23, 11,
									13, 5, 19, 6, 10, 3, 2, 12, 22, 1 }; 


	public static final int VPEG = 7;     // H 
	public static final int V[] = { 21, 25, 1, 17, 6, 8, 19, 24, 20,
									15, 18, 3, 13, 7, 11, 23, 0, 22,
									12, 9, 16, 14, 5, 4, 2, 10 }; 


	public static final int VIPEG1 = 7;   // H 
	public static final int VIPEG2 = 20;  // U 
	public static final int VI[] = { 9, 15, 6, 21, 14, 20, 12, 5,
									 24, 16, 1, 4, 13, 7, 25, 17,
									 3, 10, 0, 18, 23, 11, 8, 2,
									 19, 22 }; 


	public static final int VIIPEG1 = 7;   // H 
	public static final int VIIPEG2 = 20;  // U 
	public static final int VII[] = { 13, 25, 9, 7, 6, 17, 2, 23, 
									  12, 24, 18, 22, 1, 14, 20, 5,
									  0, 8, 21, 11, 15, 4, 10, 16, 
									  3, 19 }; 


	public static final int VIIIPEG1 = 7;  // H 
	public static final int VIIIPEG2 = 20; // U 
	public static final int VIII[] =  { 5, 10, 16, 7, 19, 11, 23, 
										14, 2, 1, 9, 18, 15, 3, 
										25, 17, 0, 12, 4, 22, 13, 8,
										20, 24, 6, 21 }; 


}