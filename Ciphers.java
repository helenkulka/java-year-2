

public class Ciphers {

	public String message;
	public int lengthOfMessage;

	public Ciphers(String m) {
		message = m;
		lengthOfMessage = m.length();
		this.makeValid();
	}

	public Ciphers(String m, boolean b) {
		message = m;
		lengthOfMessage = m.length();
	}

	public static void main(String[] args) {
	}

	/**
	 * makeValid modifies message to remove any character that is not a letter
	 * and turn Upper Case into Lower Case
	 */
	public void makeValid() {
		// here I initialize 4 different variables relating to the different
		// possibilities for characters i need to remove
		String toLowerCase;
		String ch;
		String grammar;
		String ch2;
		// I split the message into a String array so I can call various helpful
		// methods on it
		String[] messageX = this.message.split("");
		for (int i = 0; i < this.message.length(); i++) {
			// if it's a lowercase I will utilize the variables I previously
			// defined to replace it using the toLowerCase method
			if ((this.message.charAt(i) >= 'A') && (this.message.charAt(i) <= 'Z')) {
				ch = "" + this.message.charAt(i);
				toLowerCase = ch.toLowerCase();
				messageX[i] = toLowerCase;
				// I replace the message array with the new lower case letter
			} else if (!((this.message.charAt(i) >= 'a') && (this.message.charAt(i) <= 'z'))) {
				// this loop covers all cases that it is not a lower case
				// I will replace it with an empty string and then replace
				// it in the array
				ch2 = "" + this.message.charAt(i);
				grammar = ch2.replace(ch2, "");
				messageX[i] = grammar;
			}
		}
		// i make the string empty so that I can repopulate it with the elements
		// of the array
		this.message = "";
		for (int i = 0; i < messageX.length; i++) {
			this.message = this.message.concat(messageX[i]);
		}
		// I call the concat method to concatonate the elements of the array
		// and then I update the length of the array
		this.lengthOfMessage = this.message.length();

	}

	/**
	 * prints the string message
	 */
	public void print() {
		System.out.println(message);
	}

	/**
	 * tests if two Messages are equal
	 */
	public boolean equals(Ciphers m) {
		if (message.equals(m.message) && lengthOfMessage == m.lengthOfMessage) {
			return true;
		}
		return false;
	}

	/**
	 * caesarCipher implements the Caesar cipher : it shifts all letter by the
	 * number 'key' given as a parameter.
	 * 
	 * @param key
	 */
	public void caesarCipher(int key) {
		String newMessage[] = this.message.split("");
		for (int i = 0; i < this.message.length(); i++) {
			if (key >= 0) {
				// if the key is greater than 0 I will push all the characters
				// to the right

				if (('z' - this.message.charAt(i)) >= key) {
					char newLetter = (char) ((int) this.message.charAt(i) + key);
					String aNewString = "" + newLetter;
					newMessage[i] = aNewString;
					// if the key would push the character outside of the
					// lowercase alphabet, I account for this
					// by adding it to a
				} else {
					int otherCase = 'z' - this.message.charAt(i);
					char otherCaseLetter = (char) ((int) 'a' + (key - otherCase - 1));
					String otherCaseString = "" + otherCaseLetter;
					newMessage[i] = otherCaseString;
				}
			}
			// if the key is negative I will shift the characters to the left,
			// using similar logic
			if (key <= 0) {
				if ((this.message.charAt(i) + key) >= 'a') {
					char newLetter = (char) ((int) this.message.charAt(i) + key);
					String aNewString = "" + newLetter;
					newMessage[i] = aNewString;
				} else {
					// here I account for the case that when you subtract the
					// key, the message pushes past 'a' by adding it to 'z'
					int otherCase = 'a' - this.message.charAt(i);
					char newLetter = (char) ((int) 'z' + (key - (otherCase - 1)));
					String otherCaseString = "" + newLetter;
					newMessage[i] = otherCaseString;
				}
			}
		}
		this.message = "";
		for (int i = 0; i < newMessage.length; i++) {
			this.message = this.message.concat(newMessage[i]);
		}
		// i don'e need to update the range because i only shifted the
		// characters
	}

	public void caesarDecipher(int key) {
		this.caesarCipher(-key);
	}

	/**
	 * caesarAnalysis breaks the Caesar cipher you will implement the following
	 * algorithm : - compute how often each letter appear in the message -
	 * compute a shift (key) such that the letter that happens the most was
	 * originally an 'e' - decipher the message using the key you have just
	 * computed
	 */
	public void caesarAnalysis() {
		/*
		int count = 1, tempCount;
		  char popular = this.message.charAt(0);
		  char temp;
		  for (int i = 0; i < (this.message.length() - 1); i++) {
		    temp = this.message.charAt(i);
		    tempCount = 0;
		    for (int j = 1; j < this.message.length(); j++) {
		      if (temp == this.message.charAt(j)) {
		    	  tempCount++;
		      }
		    }
		    if (tempCount > count)
		    {
		      popular = temp;
		      count = tempCount;
		    }
		  }
		char letter = popular;
		System.out.println(letter);
		int shiftKey = letter - 'e';
		System.out.println(shiftKey);
		System.out.println(letter);
		caesarDecipher(shiftKey);
		System.out.println(this.message);
		*/
		char mostChar = ' ';
		int mostOccurence = 0;
		int[] charCounter = new int[Character.MAX_VALUE + 1];
		
		for(int i = 0; i<this.message.length(); i++) {
			char c = message.charAt(i);
			charCounter[c]++;
				if(charCounter[c]>=mostOccurence) {
					mostOccurence = charCounter[c];
					mostChar = c;
				}
		}

		int key = (int)(mostChar -'e');
		caesarDecipher(key);

	}

	/**
	 * vigenereCipher implements the Vigenere Cipher : it shifts all letter from
	 * message by the corresponding shift in the 'key'
	 * 
	 * @param key
	 */
	public void vigenereCipher(int[] key) {
		String newMessage[] = this.message.split("");
		// if the key length is bigger than the message then I don't have to
		// worry about restarting it
		if (key.length >= this.message.length()) {
			for (int i = 0; i < this.message.length(); i++) {
				// I apply the same logic I did last time only this time
				// indexing the key array alongside the message
				if (key[i] >= 0) {
					if (('z' - this.message.charAt(i)) >= key[i]) {
						char newLetter = (char) ((int) this.message.charAt(i) + key[i]);
						String aNewString = "" + newLetter;
						newMessage[i] = aNewString;
					} else {
						int otherCase = 'z' - this.message.charAt(i);
						char otherCaseLetter = (char) ((int) 'a' + (key[i] - otherCase - 1));
						String otherCaseString = "" + otherCaseLetter;
						newMessage[i] = otherCaseString;
					}
				}
				if (key[i] <= 0) {
					if ((this.message.charAt(i) + key[i]) >= 'a') {
						char newLetter = (char) ((int) this.message.charAt(i) + key[i]);
						String aNewString = "" + newLetter;
						newMessage[i] = aNewString;
					} else {
						int otherCase = 'a' - this.message.charAt(i);
						char newLetter = (char) ((int) 'z' + (key[i] - (otherCase - 1)));
						String otherCaseString = "" + newLetter;
						newMessage[i] = otherCaseString;
					}
				}
			}
		}
		int j = 0;
		// this integer will account for when I have to restart the key because
		// it will be smaller than the message
		if (key.length < this.message.length()) {
			for (int i = 0; i < newMessage.length; i++) {
				if (i != 0 && i % key.length == 0) {
					j = 0;
				}
				// if the index % by the key length is 0, this means I have to
				// restart my key, so j is reset to zero
				if (key[j] >= 0) {
					if (('z' - this.message.charAt(i)) >= key[j]) {
						char newLetter = (char) ((int) this.message.charAt(i) + key[j]);
						String aNewString = "" + newLetter;
						newMessage[i] = aNewString;
					} else {
						int otherCase = 'z' - this.message.charAt(i);
						char otherCaseLetter = (char) ((int) 'a' + (key[j] - otherCase - 1));
						String otherCaseString = "" + otherCaseLetter;
						newMessage[i] = otherCaseString;
					}
				}
				if (key[j] <= 0) {
					if ((this.message.charAt(i) + key[j]) >= 'a') {
						char newLetter = (char) ((int) this.message.charAt(i) + key[j]);
						String aNewString = "" + newLetter;
						newMessage[i] = aNewString;
					} else {
						int otherCase = 'a' - this.message.charAt(i);
						char newLetter = (char) ((int) 'z' + (key[j] - (otherCase - 1)));
						String otherCaseString = "" + newLetter;
						newMessage[i] = otherCaseString;
					}
				}
				// this method is the same logic as the last only I use j to
				// index the key because it operates independent of the other
				// index
				j++;
			}
		}
		this.message = "";
		for (int i = 0; i < newMessage.length; i++) {
			this.message = this.message.concat(newMessage[i]);
		}
		// again I make the message empty so I can concatenate it with the
		// updated array with the letters shifted
		// I still don't have to update the length
	}

	/**
	 * vigenereDecipher deciphers the message given the 'key' according to the
	 * Vigenere Cipher
	 * 
	 * @param key
	 */
	public void vigenereDecipher(int[] key) {
		// the reverse key array is just taking the other key array but making
		// each element negative
		// --> doing the opposite operation
		int[] reverseKey = new int[key.length];
		for (int i = 0; i < key.length; i++) {
			reverseKey[i] = key[i] * (-1);
		}
		this.vigenereCipher(reverseKey);
	}

	/**
	 * transpositionCipher performs the transition cipher on the message by
	 * reorganizing the letters and eventually adding characters
	 * 
	 * @param key
	 */
	public void transpositionCipher(int key) {
		// m accounts for how many arrays will be created in the 2d array with
		// respect to the key
		int m;
		if (this.message.length() % key == 0) {
			m = this.message.length() / key;
		} else {
			m = this.message.length() / key + 1;
		}
		String[][] sentenceArray = new String[m][key];
		int z = 0;
		// z accounts for whats at the message which I declare
		// because the message works differently than the 2d array
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < key; j++) {
				if (z < this.message.length()) {
					String input = "" + this.message.charAt(z);
					sentenceArray[i][j] = input;
					z++;
				} else {
					sentenceArray[i][j] = "*";
				}
			}
		}
		this.message = "";
		for (int i = 0; i < key; i++) {
			for (int j = 0; j < m; j++) {
				this.message = this.message.concat(sentenceArray[j][i]);
			}
		}
		this.lengthOfMessage = this.message.length();
	}
	// update the message and the length of the message

	/**
	 * transpositionDecipher deciphers the message given the 'key' according to
	 * the transition cipher.
	 * 
	 * @param key
	 */
	public void transpositionDecipher(int key) {
		// m holds the same place as it did in my last method
		int m;
		if (this.message.length() % key == 0) {
			m = this.message.length() / key;
		} else {
			m = this.message.length() / key + 1;
		}
		String[][] sentenceArray = new String[m][key];
		int z = 0;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < key; j++) {
				String s = "" + this.message.charAt(z);
				if (z < this.message.length()) {
					sentenceArray[i][j] = s;
					z += m;
					// z will increase with respect to the key only if it is
					// less than the message length
				}
				if (s.equals("*")) {
					sentenceArray[i][j] = s.replace(s, "");
					// replace the * with an empty string
				}
			}
			z = i + 1;
			// now we have to move z over to the next place in the message and
			// continue given the key
		}
		this.message = "";
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < key; j++) {
				this.message = this.message.concat(sentenceArray[i][j]);
			}
		}
		this.lengthOfMessage = this.message.length();
		// update the message and the message length

	}
}
