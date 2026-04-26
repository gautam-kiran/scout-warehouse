package ch.scout.warehouse.server.db.tables.user;

import ch.scout.warehouse.server.db.tables.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Table(name = User.NativeNames.APP_USER)
@Entity
@Getter
@Setter
public class User extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = NativeNames.USER_NR)
  public Long userNr;

  @Column(name = NativeNames.USERNAME)
  public String username;

  @Column(name = NativeNames.PASSWORD)
  public String password;

  @Column(name = NativeNames.SALT)
  public String salt;

  @Column(name = NativeNames.PERSON_NR)
  public Long personNr;

  @Column(name = NativeNames.OPTIONS)
  public String options;

  public static class NativeNames {
    public static final String APP_USER = "APP_USER";
    public static final String USER_NR = "USER_NR";
    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";
    public static final String SALT = "SALT";
    public static final String PERSON_NR = "PERSON_NR";
    public static final String OPTIONS = "OPTIONS";
  }
}
