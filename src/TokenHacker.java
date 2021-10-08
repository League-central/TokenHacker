import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class TokenHacker {
	static String token = "";
	static String password = "";
	static String password2 = "";
	static Icon iconHack = loadImage("hacker.jpeg");
	static Icon iconToken = loadImage("token.png");
	static Icon iconPassword = loadImage("password.png");
	static Icon iconError = loadImage("error.jpeg");
	static Icon iconIncorrect = loadImage("incorrect_password.jpeg");

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
			try {
				password = ((String) JOptionPane.showInputDialog(null, "Please enter your GitHub password.",
						"GitHub Password", JOptionPane.QUESTION_MESSAGE, iconPassword, null, null)).trim();
				password2 = ((String) JOptionPane.showInputDialog(null, "Please enter your GitHub password again.",
						"GitHub Password", JOptionPane.QUESTION_MESSAGE, iconPassword, null, null)).trim();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Unsuccessful. Goodbye :)", "Hacking Error",
						JOptionPane.ERROR_MESSAGE, iconError);
				System.exit(1);
			}

			if (password.equals(password2)) {
				isCorrectPassword = true;
			} else {
				JOptionPane.showMessageDialog(null, "Passwords did not match. Please re-enter your password.",
						"Incorrect Password", JOptionPane.ERROR_MESSAGE, iconIncorrect);
			}
		}
		AESCipher.encrypt(token, password);
	}

	public static void decrypt() {
		boolean isCorrectPassword = false;
		while (!isCorrectPassword) {
			try {
				password = ((String) JOptionPane.showInputDialog(null, "Please enter your GitHub password.",
						"GitHub Password", JOptionPane.QUESTION_MESSAGE, iconPassword, null, null)).trim();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Unsuccessful. Goodbye :)", "Hacking Error",
						JOptionPane.ERROR_MESSAGE, iconError);
				System.exit(1);
			}
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

	public static ImageIcon loadImage(String file) {
		try {
			return new ImageIcon(ImageIO.read(new TokenHacker().getClass().getResourceAsStream(file)));
		} catch (IOException e) {

			return null;
		}
	}
}
