//---------------------------------
// EnigmaPanel.Java
// Written By: Russell Schwager
//             russells@jhu.edu
// April 22, 1997
//---------------------------------

import java.awt.*;
import java.applet.*;
import java.util.Vector;

public class EnigmaPanel extends Panel
{
    Image 		offscreenImage;		// Used for smooth animation
    Graphics 	offscreenGraphics;	// Used for smooth animation
	Keyboard	keyboard;
	Rotor		rotors[] = new Rotor[3];
	PlugBoard	plugboard;
	Reflector	reflector;
	char		lastLetter, lastELetter;
	Vector		input, output;

	public EnigmaPanel(Image offscreenImage, Graphics offscreenGraphics)
    { // constructor
      
		this.offscreenImage = offscreenImage;
		this.offscreenGraphics = offscreenGraphics;

		this.setBackground(Color.white);

		keyboard = new Keyboard(this);
		plugboard = new PlugBoard();

		rotors[2] = new Rotor(3, 3, null);
		rotors[1] = new Rotor(4, 7, rotors[2]);
		rotors[0] = new Rotor(1, 6, rotors[1]);

		reflector = new Reflector(1);
		
		input = new Vector();
		output = new Vector();

	}

	public void setRotor(int index, int whichOne, int initialPos)
	{ // change the rotor

		if (index == 2)
			rotors[index] = new Rotor(whichOne, initialPos, null);
		else
			rotors[index] = new Rotor(whichOne, initialPos, 
					rotors[index + 1]);

		repaint();
	}
	
	public void setPos(int index, int position)
	{ // Set the initial Position of the rotor

		rotors[index].setInitialPosition(position);
	}

	public void reset()
	{ // reset the settings

		for (int i = 0; i < 3; i++)
			rotors[i].reset();

		input.removeAllElements();
		output.removeAllElements();

		plugboard.reset();

		repaint();
	}

    public void update(Graphics g)
    {
        paint(g);
    }

	public void paint(Graphics g)
	{ // draw the enigma machine 

        // figure out the dimension of the panel
        Rectangle border = bounds();

		// Clear the screen
        try { Thread.sleep(1); }
        catch (InterruptedException e) { }
        offscreenGraphics.clearRect(0, 0, this.size().width,
                                          this.size().height);

		keyboard.drawKeypad(offscreenGraphics, 10, 160);
		keyboard.drawKeyDisplay(offscreenGraphics, 10, 60);
		plugboard.draw(offscreenGraphics, 10, 260);
		offscreenGraphics.setColor(Color.black);
		offscreenGraphics.fillRect(10, 10, 287, 40);
		rotors[2].draw(offscreenGraphics, 60, 20);
		rotors[1].draw(offscreenGraphics, 150, 20);
		rotors[0].draw(offscreenGraphics, 240, 20);

		drawResults(offscreenGraphics, 325, 160);

		offscreenGraphics.setColor(Color.blue);
		offscreenGraphics.drawString("Keyboard", 310, 250);
		drawArrow(offscreenGraphics, 300, 250);
		offscreenGraphics.drawString("Display", 310, 115);
		drawArrow(offscreenGraphics, 300, 115);
		offscreenGraphics.drawString("Plugboard", 310, 315);
		drawArrow(offscreenGraphics, 300, 315);
		offscreenGraphics.drawString("Rotors/Reflector", 310, 35);
		drawArrow(offscreenGraphics, 300, 35);

		g.drawImage(offscreenImage, 0, 0, this);

	}

	private void drawArrow(Graphics g, int x, int y)
	{ // draws an arrow
		
		g.drawLine(x, y - 4, x + 7, y - 4);

		int x1[] = { x + 2, x, x + 2 };
		int y1[] = { y - 6, y - 4, y - 2 };
		g.fillPolygon(x1, y1, 3);

	}

	private void drawResults(Graphics g, int x, int y)
	{ // draw the original text and the encoded text

		String in = new String(""), out = new String("");

		g.setColor(Color.blue);
		g.drawRect(x, y - 5, 250, 80);

		g.drawString("Original Text (last 20 characters):", 
				x + 5, y + 10);

		g.drawString("Encoded Text (last 20 characters): ",
				x + 5, y + 50);

		g.setColor(Color.red);
		
		if (input.size() > 0)
		{
			for (int i = input.size() - 1; i >= input.size() - 20 &&
											i >= 0; i--)
			{
				in = ((input.elementAt(i)).toString()).concat(in);
				out = ((output.elementAt(i)).toString()).concat(out);
			}

			g.drawString(in, x + 10, y + 25);
			g.drawString(out, x + 10, y + 65);
		}
	}

	public boolean mouseDown(Event evt, int x, int y)
    {  // handles when the mouse button goes down

		boolean redraw = false;

		if (x >= 0 && x < 300 && y >= 160 && y < 260)
			redraw = keyboard.mouseDown(x, y, 10, 160);
		else if (x >= 0 && x < 300 && y >= 260)
			redraw = plugboard.mouseDown(x, y, 10, 260);
		else if (x >= 0 && x < 150 && y >= 0 && y < 60)
			redraw = rotors[2].mouseDown(x, y, 60, 20);
		else if (x >= 150 && x < 240 && y >= 0 && y < 60)
			redraw = rotors[1].mouseDown(x, y, 150, 20);
		else if (x >= 240 && x < 300 && y >= 0 && y < 60)
			redraw = rotors[0].mouseDown(x, y, 240, 20);
		
		if (redraw)
			repaint();
        
		return true;
    }

    public boolean mouseUp(Event evt, int x, int y)
    {  // handles when the mouse button goes Up
		
		boolean redraw = false;

		if (x >= 0 && x < 300 && y >= 160 && y < 260)
			redraw = keyboard.mouseUp(x, y, 10, 160);
		else if (x >= 0 && x < 300 && y >= 260)
			redraw = plugboard.mouseUp(x, y, 10, 260);
		else if (x >= 0 && x < 150 && y >= 0 && y < 60)
			redraw = rotors[2].mouseUp(x, y, 60, 20);
		else if (x >= 150 && x < 240 && y >= 0 && y < 60)
			redraw = rotors[1].mouseUp(x, y, 150, 20);
		else if (x >= 240 && x < 300 && y >= 0 && y < 60)
			redraw = rotors[0].mouseUp(x, y, 240, 20);
		
		if (redraw)
			repaint();
        return true;
    }

    public boolean mouseDrag(Event evt, int x, int y)
    {  // handles when the mouse is being dragged

		boolean redraw = false;

		redraw |= keyboard.mouseDrag(x, y, 10, 160);
		redraw |= plugboard.mouseDrag(x, y, 10, 260);
		redraw |= rotors[2].mouseDrag(x, y, 60, 20);
		redraw |= rotors[1].mouseDrag(x, y, 150, 20);
		redraw |= rotors[0].mouseDrag(x, y, 240, 20);
		
		if (redraw)
			repaint();
		return true;
	}

	public void processChar(char ch, boolean saveLetter)
	{ // Encrypt one character

		int	i, offset = 0;

		if (ch == lastLetter)
		{
			keyboard.setLight(lastELetter);

			// rotate the rotors
			if (saveLetter)
			{
				rotors[0].rotate(true);
				input.addElement(new Character(ch));
				output.addElement(new Character(lastELetter));
				lastELetter = lastLetter = '0';
			}

			return;
		}
		else 
			lastLetter = ch;

		offset = ch - 'A';

		if(offset >= 0 && offset <= 25) 
		{ // encrypt only valid letters

			// send the character through the plug board
			offset = plugboard.send(ch); 

			// send the character through the rotors
			for(i = 0; i < 3; i++) 
				offset = rotors[i].send(offset, true);


			// send the character through the reflector
			offset = reflector.send(offset);

			// send the character back through the rotors
			for(i = 2; i >= 0; i--)
				offset = rotors[i].send(offset, false);
			
			// send the character back through the plugboard
			offset = plugboard.send((char)(offset + 'A')); 	

			lastELetter = (char)(offset + 'A');

			// rotate the rotors
			if (saveLetter)
			{
				rotors[0].rotate(true);
				input.addElement(new Character(ch));
				output.addElement(new Character((char)(offset + 'A')));
				lastELetter = lastLetter = '0';
			}
		}

		keyboard.setLight((char)(offset + 'A'));

	}
}