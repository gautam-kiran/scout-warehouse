package ch.scout.warehouse.server.security;


import ch.scout.warehouse.server.db.tables.user.User;
import ch.scout.warehouse.server.db.tables.user.UserRepository;
import ch.scout.warehouse.shared.security.IPasswordService;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Optional;

public class PasswordService implements IPasswordService {

  Logger LOG = LoggerFactory.getLogger(PasswordService.class);

  private final int SALT_LENGHT = 16;

  public User hashPassword(User user, String password) {
    byte[] salt = generateSalt();
    user.setPassword(hashPassword(password.toCharArray(),salt));
    user.setSalt(toHex(salt));
    return user;
  }

  private byte[] generateSalt() {
    SecureRandom random = new SecureRandom();
    byte[] salt = new byte[SALT_LENGHT];
    random.nextBytes(salt);
    return salt;
  }

  private String hashPassword(char[] password, byte[] salt)  {
    KeySpec spec = new PBEKeySpec(password, salt, 65536, 128);
    try {
      SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
      return toHex(factory.generateSecret(spec).getEncoded());
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      LOG.error("Error hashing password", e);
      throw new VetoException(e.getMessage());
    }
  }

  private static String toHex(byte[] array) {
    BigInteger i = new BigInteger(1, array);
    String hex = i.toString(16);
    int paddingLength = (array.length * 2) - hex.length();
    if (paddingLength > 0) {
      return String.format("%0" + paddingLength + "d", 0) + hex;
    } else {
      return hex;
    }
  }

  private static byte[] fromHex(String hex) {
    byte[] binary = new byte[hex.length() / 2];
    for (int i = 0; i < binary.length; i++) {
      binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
    }
    return binary;
  }


  public boolean verifyPassword(String username, char[] password){
    Optional<User> optionalUser = BEANS.get(UserRepository.class).findByUsername(username);
    if (optionalUser.isEmpty()) {
      return false;
    }
    User user = optionalUser.get();
    String hashedPw = hashPassword(password, fromHex(user.getSalt()));
    return user.getPassword().equals(hashedPw);
  }

  @Override
  public void changePassword(String username, String newPassword) {
    User user = BEANS.get(UserRepository.class).findByUsername(username)
      .orElseThrow(() -> new VetoException("User not found"));
    user.setPassword(hashPassword(newPassword.toCharArray(),fromHex(user.getPassword())));
    BEANS.get(UserRepository.class).save(user);
  }

}
