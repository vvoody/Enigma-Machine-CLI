//---------------------------------
// AppControls.Java
// Written By: Russell Schwager
//             russells@jhu.edu
// April 22, 1997
//---------------------------------

import java.awt.*;
import java.applet.*;

class AppControls extends Panel 
{
	EnigmaPanel panel;
	Choice rotors[] = new Choice[3];
	Choice initialPos[] = new Choice[3];
    Button resetButton;

	public AppControls(EnigmaPanel panel) 
	{ // Constructor

        setBackground(Color.gray);
		this.panel = panel;

		// create a layout for the controls
		setLayout(new GridLayout(3, 4, 5, 2));

		rotors[0] = new Choice();
		rotors[0].addItem("1");
		rotors[0].addItem("2");
		rotors[0].addItem("3");
		rotors[0].addItem("4");
		rotors[0].addItem("5");
		rotors[0].addItem("6");
		rotors[0].addItem("7");
		rotors[0].addItem("8");
		rotors[0].addItem("9");

		rotors[1] = new Choice();
		rotors[1].addItem("1");
		rotors[1].addItem("2");
		rotors[1].addItem("3");
		rotors[1].addItem("4");
		rotors[1].addItem("5");
		rotors[1].addItem("6");
		rotors[1].addItem("7");
		rotors[1].addItem("8");
		rotors[1].addItem("9");

		rotors[2] = new Choice();
		rotors[2].addItem("1");
		rotors[2].addItem("2");
		rotors[2].addItem("3");
		rotors[2].addItem("4");
		rotors[2].addItem("5");
		rotors[2].addItem("6");
		rotors[2].addItem("7");
		rotors[2].addItem("8");
		rotors[2].addItem("9");

		initialPos[0] = new Choice();
		initialPos[0].addItem("0"); initialPos[0].addItem("1");
		initialPos[0].addItem("2"); initialPos[0].addItem("3");
		initialPos[0].addItem("4"); initialPos[0].addItem("5");
		initialPos[0].addItem("6"); initialPos[0].addItem("7");
		initialPos[0].addItem("8"); initialPos[0].addItem("9");
		initialPos[0].addItem("10"); initialPos[0].addItem("11");
		initialPos[0].addItem("12"); initialPos[0].addItem("13");
		initialPos[0].addItem("14"); initialPos[0].addItem("15");
		initialPos[0].addItem("16"); initialPos[0].addItem("17");
		initialPos[0].addItem("18"); initialPos[0].addItem("19");
		initialPos[0].addItem("20"); initialPos[0].addItem("21");
		initialPos[0].addItem("22"); initialPos[0].addItem("23");
		initialPos[0].addItem("24"); initialPos[0].addItem("25");

		initialPos[1] = new Choice();
		initialPos[1].addItem("0"); initialPos[1].addItem("1");
		initialPos[1].addItem("2"); initialPos[1].addItem("3");
		initialPos[1].addItem("4"); initialPos[1].addItem("5");
		initialPos[1].addItem("6"); initialPos[1].addItem("7");
		initialPos[1].addItem("8"); initialPos[1].addItem("9");
		initialPos[1].addItem("10"); initialPos[1].addItem("11");
		initialPos[1].addItem("12"); initialPos[1].addItem("13");
		initialPos[1].addItem("14"); initialPos[1].addItem("15");
		initialPos[1].addItem("16"); initialPos[1].addItem("17");
		initialPos[1].addItem("18"); initialPos[1].addItem("19");
		initialPos[1].addItem("20"); initialPos[1].addItem("21");
		initialPos[1].addItem("22"); initialPos[1].addItem("23");
		initialPos[1].addItem("24"); initialPos[1].addItem("25");

		initialPos[2] = new Choice();
		initialPos[2].addItem("0"); initialPos[2].addItem("1");
		initialPos[2].addItem("2"); initialPos[2].addItem("3");
		initialPos[2].addItem("4"); initialPos[2].addItem("5");
		initialPos[2].addItem("6"); initialPos[2].addItem("7");
		initialPos[2].addItem("8"); initialPos[2].addItem("9");
		initialPos[2].addItem("10"); initialPos[2].addItem("11");
		initialPos[2].addItem("12"); initialPos[2].addItem("13");
		initialPos[2].addItem("14"); initialPos[2].addItem("15");
		initialPos[2].addItem("16"); initialPos[2].addItem("17");
		initialPos[2].addItem("18"); initialPos[2].addItem("19");
		initialPos[2].addItem("20"); initialPos[2].addItem("21");
		initialPos[2].addItem("22"); initialPos[2].addItem("23");
		initialPos[2].addItem("24"); initialPos[2].addItem("25");

		// Reset button
		resetButton = new Button("Reset machine");

		// set the initial positions of the choice boxes
		rotors[2].select(2);
		rotors[1].select(3);
		rotors[0].select(0);

		initialPos[2].select(3);
		initialPos[1].select(7);
		initialPos[0].select(6);

		// Add components to the panel
		add(resetButton);
		add(new Label("Rotor I:"));
		add(new Label("Rotor II:"));
		add(new Label("Rotor III:"));
		add(new Label("Rotor Type:", Label.RIGHT));
		add(rotors[2]);
		add(rotors[1]);
		add(rotors[0]);
		add(new Label("Initial Position:", Label.RIGHT));
		add(initialPos[2]);
		add(initialPos[1]);
		add(initialPos[0]);

		for (int i = 0; i < 3; i++)
		{
			initialPos[i].setBackground(Color.white);
			rotors[i].setBackground(Color.white);
		}
    }

    public boolean action(Event ev, Object arg) 
	{ // Event handler

		if (ev.target instanceof Button) 
		{ // a button was pushed

			panel.reset();
            return true;
        }
		else if (ev.target instanceof Choice)
		{ // one the choice boxes was changed

			if (ev.target == rotors[0])
				panel.setRotor(0, rotors[0].getSelectedIndex() + 1, 
						initialPos[0].getSelectedIndex());
			else if (ev.target == rotors[1])
				panel.setRotor(1, rotors[1].getSelectedIndex() + 1,
						initialPos[1].getSelectedIndex());
			else if (ev.target == rotors[2])
				panel.setRotor(2, rotors[2].getSelectedIndex() + 1,
						initialPos[2].getSelectedIndex());
			else if (ev.target == initialPos[0])
				panel.setPos(0, initialPos[0].getSelectedIndex());
			else if (ev.target == initialPos[1])
				panel.setPos(1, initialPos[1].getSelectedIndex());
			else if (ev.target == initialPos[2])
				panel.setPos(2, initialPos[2].getSelectedIndex());
				
			return true;
		}

		return false;
	}

}


