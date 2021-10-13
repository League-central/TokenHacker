import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class AESCipher {
	static String salt = "leagueOfAmazing";
	static Icon iconToken = loadImage("token.png");

	public static void encrypt(String token, String password) throws Exception {
		// Generate a key based on the password
		SecretKey key = generateKey(password);

		// Generate an IV based on the password
		IvParameterSpec iv = generateIV(password);

		// Creating a Cipher object
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

		// Initializing a Cipher object to encrypt
		cipher.init(Cipher.ENCRYPT_MODE, key, iv);

		// Encrypting the token
		byte[] encryptedToken = cipher.doFinal(token.getBytes());

		// Writing the encrypted token to file
		FileWriter fw = new FileWriter("encrypted_token.txt");
		fw.write(Base64.getEncoder().encodeToString(encryptedToken));
		fw.close();

		JOptionPane.showMessageDialog(null, "Success! You encrypted your token into \"encrypted_token.txt\"",
				"Succesful Hacking", JOptionPane.INFORMATION_MESSAGE, iconToken);
	}

	public static void decrypt(String password) throws Exception {
		// Read the encrypted token from file
		BufferedReader br = new BufferedReader(new FileReader("encrypted_token.txt"));
		String encryptedToken = br.readLine();
		br.close();

		// Generate a key based on the password
		SecretKey key = generateKey(password);

		// Generate an IV based on the password
		IvParameterSpec iv = generateIV(password);

		// Creating a Cipher object
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

		// Initializing a Cipher object to decrypt
		cipher.init(Cipher.DECRYPT_MODE, key, iv);

		// Decrypting the token
		byte[] decryptedToken = cipher.doFinal(Base64.getDecoder().decode(encryptedToken.getBytes()));

		// Print out token
		System.out.print(new String(decryptedToken));
		copyToClipboard(new String(decryptedToken));
		JOptionPane.showMessageDialog(null, "Success! Your token was COPIED to your CLIPBOARD.", "Succesful Hacking",
				JOptionPane.INFORMATION_MESSAGE, iconToken);
	}

	// Generates a key based on a password
	public static SecretKey generateKey(String password) throws Exception {
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
		return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
	}

	// Generates an Initialization Vector based on a password
	public static IvParameterSpec generateIV(String password) {
		byte[] ivArray = new byte[16];
		int length = 16;
		if (password.length() < 16) {
			length = password.length();
		}
		for (int i = 0; i < length; i++) {
			ivArray[i] = (byte) password.charAt(i);
		}
		return new IvParameterSpec(ivArray);
	}
	
	public static void copyToClipboard(String token) {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable transferable = new StringSelection(token);
		clipboard.setContents(transferable, null);
	}

	public static ImageIcon loadImage(String file) {
		try {
			return new ImageIcon(ImageIO.read(new TokenHacker().getClass().getResourceAsStream(file)));
		} catch (IOException e) {

			return null;
		}
	}
}
