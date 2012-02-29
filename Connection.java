//---------------------------------
// Connection.Java
// Written By: Russell Schwager
//             russells@jhu.edu
// April 22, 1997
//---------------------------------

public class Connection
{
	char start, end;

	public Connection()
	{ // default constructor

		start = end = '0';
	}

	public Connection(char start, char end)
	{ 

		this.start = start;
		this.end = end;
	}

	public char getLetter(int index)
	{
		if (index == 1)
			return start;
		
		return end;
	}

	public boolean verify(Connection pair)
	{ // Verify if the connection is a good one

		if (start == pair.getLetter(1) || start == pair.getLetter(2) ||
			end == pair.getLetter(1) || end == pair.getLetter(2) || 
			start == end || start == '0' || end == '0')
			return false;

		return true;
	}

}
