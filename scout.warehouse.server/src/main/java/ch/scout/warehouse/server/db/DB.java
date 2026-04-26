package ch.scout.warehouse.server.db;

import com.querydsl.core.types.dsl.BooleanExpression;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.FlushModeType;
import jakarta.persistence.Persistence;
import lombok.Getter;

import java.util.Properties;
import java.util.function.Function;


public class DB {

  @Getter
  private static EntityManager entityManager;

  public static void init() {
    Properties properties = new Properties();
    properties.put("javax.persistence.jdbc.url", System.getenv("DB_URL"));
    properties.put("javax.persistence.jdbc.user", System.getenv("DB_USERNAME"));
    properties.put("javax.persistence.jdbc.password", System.getenv("DB_PASSWORD"));
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("scout-warehouse", properties);
    entityManager = entityManagerFactory.createEntityManager();
    entityManager.setFlushMode(FlushModeType.COMMIT);
  }

  public static <T> BooleanExpression optional(Function<T, BooleanExpression> condition, T value) {
    if (value != null) {
      return condition.apply(value);
    }
    return null;
  }
}
