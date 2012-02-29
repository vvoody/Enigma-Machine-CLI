//---------------------------------
// PlugBoard.Java
// Written By: Russell Schwager
//             russells@jhu.edu
// April 22, 1997
//---------------------------------

import java.awt.Graphics;
import java.awt.Color;

public class PlugBoard
{

	public final static int MAXPLUGS = 13;
	Connection board[] = new Connection[MAXPLUGS];
	int numPlugs, downx, downy, currx, curry;
	char newLetter1, newLetter2;
	boolean mousedown, inRange;

	public PlugBoard()
	{ // default constructor

		numPlugs = 0;

		for (int i = 0; i < MAXPLUGS; i++)
			board[i] = new Connection();

		mousedown = inRange = false;
		newLetter1 = '0';
		newLetter2 = '0';
		downx = downy = currx = curry = 0;
	}

	public void reset()
	{ // remove all plugs
		
		numPlugs = 0;		
	}
	
	public void addPlug(char start, char end)
	{ // add a plug to the board

		Connection newPlug = new Connection(start, end);

		for (int i = 0; i < numPlugs; i++)
			if (!newPlug.verify(board[i]))
				return;

		board[numPlugs++] = newPlug;
	}

	public int send(char ch)
	{ // returns the integer value of the output of going through
	  // the plugboard

	
		for (int i = 0; i < numPlugs; i++)
		{
			if (board[i].getLetter(1) == ch)
				return board[i].getLetter(2) - 'A';
			else if (board[i].getLetter(2) == ch)
					return board[i].getLetter(1) - 'A';
		}

		return ch - 'A';
	}

	public void draw(Graphics g, int x, int y)
	{ // draw the plugboard and the connection

		int radius = 7, spacing = 14, initialX, initialY;
		char letter = 'A';

		g.setColor(Color.black);

		g.fillRect(x, y, 287, 100);

		g.setColor(Color.white);
		
		initialX = x += 14;
		initialY = y += 10;

		for (int i = 0; i < 2; i++)
		{
			for (int j = 0; j < 13; j++)
			{
				if ((letter - 'A') % 2 == 1)
					g.fillArc(x + j * (radius + spacing), y, 
						radius, radius, 0, 360);
				else
					g.fillArc(x + j * (radius + spacing), y + 25, 
						radius, radius, 0, 360);

				Character aChar = new Character(letter++);
				g.drawString(aChar.toString(),
					x + j * (radius + spacing), y + 21);

			}

			y += 50;
		}

		drawConnections(g, initialX, initialY, radius + spacing, radius);

		if (mousedown && inRange)
		{
			g.setColor(Color.blue);
			g.fillArc(downx - 1, downy - 1, radius + 2, radius + 2, 
				0, 360);
			g.drawLine(downx, downy, currx, curry);
		}

	}

	private void drawConnections(Graphics g, int x, int y, int spacing, 
		int radius)
	{ // draw the current connections

		int x1, x2, y1, y2;

		for (int i = 0; i < numPlugs; i++)
		{
			g.setColor(Color.red);
			x1 = x + (board[i].getLetter(1) - 'A') % 13 * spacing;
			x2 = x + (board[i].getLetter(2) - 'A') % 13 * spacing;
			if ((board[i].getLetter(1) - 'A') % 2 == 1)
				y1 = y + (board[i].getLetter(1) - 'A') / 13 * 50;
			else 
				y1 = y + 25 + (board[i].getLetter(1) - 'A') / 13 * 50;

			if ((board[i].getLetter(2) - 'A') % 2 == 1)
				y2 = y + (board[i].getLetter(2) - 'A') / 13 * 50;
			else
				y2 = y + 25 + (board[i].getLetter(2) - 'A') / 13 * 50;

			g.drawLine(x1 + radius / 2, y1 + radius / 2, 
				x2 + radius / 2, y2 + radius / 2);

			g.fillArc(x1 - 1, y1 - 1, radius + 2, radius + 2, 0, 360);
			g.fillArc(x2 - 1, y2 - 1, radius + 2, radius + 2, 0, 360);

		}
	}

	private boolean findConnection(int x, int y, int xpos, int ypos, 
									int spacing, int radius)
	{ // returns true if x and y are over a connection point

		char letter = 'A';
		int newx;

		xpos += 14;
		ypos += 10;

		for (int i = 0; i < 2; i++)
		{
			for (int j = 0; j < 13; j++)
			{
				newx = xpos + j * (radius + spacing);

				if (x >= newx && x <= newx + radius &&
					y >= ypos && y <= ypos + radius &&
					(letter - 'A') % 2 == 1)
				{
					mousedown = inRange = true;
					if (newLetter1 == '0')
						newLetter1 = letter;
					else
						newLetter2 = letter;
					downx = newx;
					downy = ypos;
					currx = newx + radius / 2;
					curry = ypos + radius / 2;
					return true;
				}

				if (x >= newx && x <= newx + radius &&
					y >= ypos + 25 && y <= ypos + radius + 25 &&
					(letter - 'A') % 2 == 0)
				{
					mousedown = inRange = true;
					if (newLetter1 == '0')
						newLetter1 = letter;
					else
						newLetter2 = letter;
					downx = newx;
					downy = ypos + 25;
					currx = newx + radius / 2;
					curry = ypos + radius / 2 + 25;
					return true;
				}

				letter++;

			}

			ypos += 50;
		}

		return false;
	}


    public boolean mouseDown(int x, int y, int xpos, int ypos)
	{ // handles when the mouse is clicked down

		if (findConnection(x, y, xpos, ypos, 7, 14))
			return true;
		else
		{	
			downx = downy = 0;
			mousedown = inRange = false;
			newLetter1 = newLetter2 = 0;
		}

		return false;
	}

    public boolean mouseDrag(int x, int y, int xpos, int ypos)
	{ // handles when the mouse is dragged

		if (mousedown && x >= 0 && x <= 300 && y >= ypos && 
			y <= ypos + 100)
		{
			inRange = true;
			currx = x;
			curry = y;
			return true;
		}

		inRange = false;
		return false;
	}

    public boolean mouseUp(int x, int y, int xpos, int ypos)
	{ // handles when the mouse is released

		if (findConnection(x, y, xpos, ypos, 7, 14) && 
			newLetter1 != '0' && newLetter2 != '0')
			addPlug(newLetter1, newLetter2);

		mousedown = inRange = false;
		newLetter1 = '0';
		newLetter2 = '0';
		downx = downy = currx = curry = 0;
		return true;
	}

}