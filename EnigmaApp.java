//---------------------------------
// EnigmaApp.Java
// Written By: Russell Schwager
//             russells@jhu.edu
// April 22, 1997
//---------------------------------

import java.awt.*;
import java.applet.*;

public class EnigmaApp extends Applet 
{
	AppControls controls;
    Image offscreenImage;
    Graphics offscreenGraphics;

    public void init() 
	{
        setLayout(new BorderLayout());
        offscreenImage = createImage(this.size().width, this.size().height);
        offscreenGraphics = offscreenImage.getGraphics();
        EnigmaPanel panel = new EnigmaPanel(offscreenImage, offscreenGraphics);

		add("Center", panel);
        add("South", controls = new AppControls(panel));
    }

    public void start() 
	{
        controls.enable();
    }

    public void stop() 
	{
        controls.disable();
    }

    public boolean handleEvent(Event e) 
	{
        if (e.id == Event.WINDOW_DESTROY) 
		{
            System.exit(0);
        }
    
		return false;
    }

    public static void main(String args[]) 
	{

        Frame f = new Frame("Enigma Machine");
        EnigmaApp enigmaApp = new EnigmaApp();

        enigmaApp.init();
        enigmaApp.start();

        f.add("Center", enigmaApp);
        f.resize(300, 300);
        f.show();

    }
}