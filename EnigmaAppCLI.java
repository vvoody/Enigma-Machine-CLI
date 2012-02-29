import java.awt.Choice;
import java.util.Vector;

//----------------------------------------------------------------
// EnigmaAppCLI.Java
// Written By: vvoody
//             vvoody.wang@gmail.com
// Feb 29, 2012
//
// This is a program which encrypt/decrypt using Enigma algorithm
// at command line, not as an Applet in the browser.
//----------------------------------------------------------------

public class EnigmaAppCLI
{
	static Rotor		rotors[] = new Rotor[3];
	static PlugBoard	plugboard;
	static Reflector	reflector;
	static char			lastLetter, lastELetter;
	static Vector		input, output;

    public void init()
	{
    	;
    }

    // This method is just copied from class EnigmaPanel, but muted
    // keyboard.setLight()
	public void processChar(char ch, boolean saveLetter)
	{ // Encrypt one character
		int	i, offset = 0;

		if (ch == lastLetter)
		{
//			keyboard.setLight(lastELetter);

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

//		keyboard.setLight((char)(offset + 'A'));
	}

    public static void main(String args[])
	{
        EnigmaAppCLI enigmaApp = new EnigmaAppCLI();
        enigmaApp.init();

        String text = "ITWASTHANKSTOULTRATHATWEWONTHEWAR";

        // rotor types: 3,4,1
        rotors[2] = new Rotor(3, 7, null);       // rotor init pos: h
        rotors[1] = new Rotor(4, 22, rotors[2]);   //                 w
        rotors[0] = new Rotor(1, 7, rotors[1]);  //                 h

        plugboard = new PlugBoard();
        reflector = new Reflector(1);

        input = new Vector();
        output = new Vector();

        System.out.println("text: " + text + ", rotor init pos: hwh, rotor types: 3,4,1.");

        for(int i=0; i<text.length(); i++)
            enigmaApp.processChar(text.charAt(i), true);

        for(int j=0; j<output.size(); j++)
            System.out.print(output.elementAt(j));

        System.out.println();
    }
}
