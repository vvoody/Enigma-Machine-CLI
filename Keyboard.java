//---------------------------------
// Keyboard.Java
// Written By: Russell Schwager
//             russells@jhu.edu
// April 22, 1997
//---------------------------------

import java.awt.Graphics;
import java.awt.Color;

public class Keyboard 
{
	String rows[] = new String[3];
	boolean keyDown;
	char downKey, lightKey;
	EnigmaPanel panel;

	public Keyboard(EnigmaPanel panel)
	{ // Constructor

		rows[0] = new String("QWERTZUIO");
		rows[1] = new String("ASDFGHJK");
		rows[2] = new String("PYXCVBNML");
		keyDown = false;
		downKey = '0';
		lightKey = '0';
		this.panel = panel;
	}

	public void setLight(char ch)
	{ // set the light bulb to be displayed

		lightKey = ch;
	}
	
	public void drawKeypad(Graphics g, int startx, int starty)
	{
		int height, width, spacing = 2, i;

		height = width = 30;

		drawPadRow(g, startx, starty, height, width, rows[0].length(), 
				spacing, rows[0]);
		
		starty += height + spacing;
		startx += width / 2;
		drawPadRow(g, startx, starty, height, width, rows[1].length(), 
				spacing, rows[1]);
		
		starty += height + spacing;
		startx -= width / 2;
		drawPadRow(g, startx, starty, height, width, rows[2].length(), 
				spacing, rows[2]);

	}

	private void drawPadRow(Graphics g, int x, int y, int height, 
							int width, int numKeys, int spacing, 
							String keys)
	{ // Draws a row of keys to the graphics context

		for (int i = 0; i < numKeys; i++)
		{
			g.setColor(Color.lightGray);

			if (keyDown && keys.charAt(i) == downKey)
				g.fill3DRect(x + (i * (width + spacing)), y, width, 
					height, false);
			else
				g.fill3DRect(x + (i * (width + spacing)), y, width, 
					height, true);

			g.setColor(Color.black);

			g.drawString(keys.substring(i, i + 1), 
				x + i * (width + spacing) + (width + spacing) / 2 - 4, 
				y + height - (height / 4));
		}

	}

	public void drawKeyDisplay(Graphics g, int startx, int starty)
	{  // Draw the light display

		int height, width, spacing = 2, i;

		height = width = 30;

		drawDisplayRow(g, startx, starty, height, width, 
				rows[0].length(), spacing, rows[0]);
		
		starty += height + spacing;
		startx += width / 2;
		drawDisplayRow(g, startx, starty, height, width, 
				rows[1].length(), spacing, rows[1]);
		
		starty += height + spacing;
		startx -= width / 2;
		drawDisplayRow(g, startx, starty, height, width, 
				rows[2].length(), spacing, rows[2]);

	}

	private void drawDisplayRow(Graphics g, int x, int y, int height, 
							int width, int numKeys, int spacing, 
							String keys)
	{ // Draws a row of keys to the graphics context

		for (int i = 0; i < numKeys; i++)
		{

			if (keyDown && keys.charAt(i) == lightKey)
			{
				g.setColor(Color.yellow);
				g.fillArc(x + i * (width + spacing), y, width, 
					height, 0, 360);
				g.setColor(Color.black);
			}
			else
			{
				g.setColor(Color.black);
				g.fillArc(x + i * (width + spacing), y, width, 
					height, 0, 360);
				g.setColor(Color.white);
			}

			g.drawString(keys.substring(i, i + 1), 
				x + i * (width + spacing) + (width + spacing) / 2 - 4, 
				y + height - (height / 4));
		}

	}

	private boolean findKey(int x, int y, int xpos, int ypos, 
						int height, int width, int numKeys, int padding,
						String keys)
	{ // returns true if x and y are over a key
	
		int newx;

		for (int i = 0; i < numKeys; i++)
		{
			newx = xpos + i * (width + padding);

			if (x >= newx && x <= newx + width && y >= ypos &&
				y <= ypos + height)
			{
				keyDown = true;
				downKey = keys.charAt(i);
				return true;	
			}

		}

		return false;
	}

    public boolean mouseDown(int x, int y, int xpos, int ypos)
	{ // handles when the mouse is clicked down

		int height, width, spacing = 2, i;
		boolean foundkey = false;

		height = width = 30;

		foundkey |= findKey(x, y, xpos, ypos, height, width, 
				rows[0].length(), spacing, rows[0]);
		
		ypos += height + spacing;
		xpos += width / 2;
		foundkey |= findKey(x, y, xpos, ypos, height, width, 
				rows[1].length(), spacing, rows[1]);
		
		ypos += height + spacing;
		xpos -= width / 2;
		foundkey |= findKey(x, y, xpos, ypos, height, width, 
				rows[2].length(), spacing, rows[2]);

		if (!foundkey)
		{
			downKey = '0';
			keyDown = false;
		}
		else
			panel.processChar(downKey, false);

		return foundkey;
	}

    public boolean mouseDrag(int x, int y, int xpos, int ypos)
	{ // handles when the mouse is dragged

		int height, width, spacing = 2, i;
		boolean foundkey = false;

		height = width = 30;

		foundkey |= findKey(x, y, xpos, ypos, height, width, 
				rows[0].length(), spacing, rows[0]);
		
		ypos += height + spacing;
		xpos += width / 2;
		foundkey |= findKey(x, y, xpos, ypos, height, width, 
				rows[1].length(), spacing, rows[1]);
		
		ypos += height + spacing;
		xpos -= width / 2;
		foundkey |= findKey(x, y, xpos, ypos, height, width, 
				rows[2].length(), spacing, rows[2]);

		if (!foundkey)
		{
			downKey = '0';
			keyDown = false;
		}
		else
			panel.processChar(downKey, false);

		return foundkey;
	}

    public boolean mouseUp(int x, int y, int xpos, int ypos)
	{ // handles when the mouse is released

		int height, width, spacing = 2, i;
		boolean foundkey = false;

		height = width = 30;

		foundkey |= findKey(x, y, xpos, ypos, height, width, 
				rows[0].length(), spacing, rows[0]);
		
		ypos += height + spacing;
		xpos += width / 2;
		foundkey |= findKey(x, y, xpos, ypos, height, width, 
				rows[1].length(), spacing, rows[1]);
		
		ypos += height + spacing;
		xpos -= width / 2;
		foundkey |= findKey(x, y, xpos, ypos, height, width, 
				rows[2].length(), spacing, rows[2]);

		// reset the keys
		if (foundkey)
		{
			panel.processChar(downKey, true);
			downKey = '0';
			keyDown = false;
		}

		return foundkey;
	}

}