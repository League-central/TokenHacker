import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class TokenHacker {
	static String token = "";
	static String password = "";
	static String password2 = "";
	static Icon iconHack = loadImage("hacker.jpeg");
	static Icon iconToken = loadImage("token.png");
	static Icon iconPassword = loadImage("password.png");
	static Icon iconError = loadImage("error.jpeg");
	static Icon iconIncorrect = loadImage("incorrect_password.jpeg");

	// Calls encrypt() or decrypt()
	public void hack() throws Exception {
		String options[] = { "Decrypt", "Encrypt" };
		int cipherChoice = JOptionPane.showOptionDialog(null, "Would you like to encrypt\nor decrypt your token?",
				"Hacker Time", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, iconHack, options, null);

		if (cipherChoice == 1) {
			encrypt();
		} else {
			decrypt();
		}
	}

	// Encrypts the token when the user provides their token and password
	public static void encrypt() throws Exception {
		try {
			token = ((String) JOptionPane.showInputDialog(null, "Please enter your GitHub token.", "GitHub Token",
					JOptionPane.QUESTION_MESSAGE, iconToken, null, null)).trim();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Unsuccessful. Goodbye :)", "Hacking Error", JOptionPane.ERROR_MESSAGE,
					iconError);
			System.exit(1);
		}

		boolean isCorrectPassword = false;
		while (!isCorrectPassword) {
			password = inputPassword("Please enter your GitHub password.");
			password2 = inputPassword("Please enter your GitHub password again.");

			if (password.equals(password2)) {
				isCorrectPassword = true;
			} else {
				JOptionPane.showMessageDialog(null, "Passwords did not match. Please re-enter your password.",
						"Incorrect Password", JOptionPane.ERROR_MESSAGE, iconIncorrect);
			}
		}
		AESCipher.encrypt(token, password);
	}

	// Decrypts the token when the user provides their password
	public static void decrypt() {
		boolean isCorrectPassword = false;
		while (!isCorrectPassword) {
			password = inputPassword("Please enter your GitHub password.");

			try {
				AESCipher.decrypt(password);
				isCorrectPassword = true;
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"You did not enter the correct password. Please re-enter your password.", "Incorrect Password",
						JOptionPane.ERROR_MESSAGE, iconIncorrect);
			}
		}
	}

	// Custom password input that hides the text
	public static String inputPassword(String prompt) {
		Box box = Box.createVerticalBox();
		JLabel label = new JLabel(prompt);
		JPasswordField text = new JPasswordField(6);

		box.add(label);
		box.add(text);

		int button = JOptionPane.showConfirmDialog(null, box, "GitHub Password", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE, iconPassword);

		if (button == JOptionPane.OK_OPTION) {
			return new String(text.getPassword()).trim();

		} else {
			JOptionPane.showMessageDialog(null, "Unsuccessful. Goodbye :)", "Hacking Error", JOptionPane.ERROR_MESSAGE,
					iconError);
			System.exit(1);
		}
		return null;

	}

	public static ImageIcon loadImage(String file) {
		try {
			return new ImageIcon(ImageIO.read(new TokenHacker().getClass().getResourceAsStream(file)));
		} catch (IOException e) {

			return null;
		}
	}
}
