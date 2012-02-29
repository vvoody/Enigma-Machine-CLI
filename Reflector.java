//---------------------------------
// Reflector.Java
// Written By: Russell Schwager
//             russells@jhu.edu
// April 22, 1997
//---------------------------------

public class Reflector 
{
	public final static int REFLECTSIZE = 26;
	int		reflect[] = new int[REFLECTSIZE];

	public Reflector(int whichReflect)
	{  // Constructor

		getReflect(whichReflect);
	}

	private void getReflect(int whichOne)
	{  // Initialize the settings of the reflector

		switch (whichOne) {
			case 1:
				reflect = I;
			break;
			case 2:
				reflect = II;
			break;
		}
	}

	public int send(int offset)
	{ // send the character through the reflector

		return reflect[offset];

	}

	// Reflector Settings
	public static final int I[] = { 24, 17, 20, 7, 16, 18, 11, 3, 15,
									23, 13, 6, 14, 10, 12, 8, 4, 1,
									5, 25, 2, 22, 21, 9, 0, 19 }; 

	public static final int II[] = { 5, 21, 15, 9, 8, 0, 14, 24, 4,
									 3, 17, 25, 23, 22, 6, 2, 19, 10,
									 20, 16, 18, 1, 13, 12, 7, 11 }; 

}