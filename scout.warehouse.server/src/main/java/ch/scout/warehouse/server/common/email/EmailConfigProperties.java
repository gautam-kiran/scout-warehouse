package ch.scout.warehouse.server.common.email;

import org.eclipse.scout.rt.platform.config.AbstractIntegerConfigProperty;
import org.eclipse.scout.rt.platform.config.AbstractStringConfigProperty;

public final class EmailConfigProperties {

  public static class EmailHostProperty extends AbstractStringConfigProperty{

    @Override
    public String getKey() {
      return "email.host";
    }

    @Override
    public String description() {
      return "Email host";
    }
  }
  public static class EmailPortProperty extends AbstractIntegerConfigProperty {

    @Override
    public String getKey() {
      return "email.port";
    }

    @Override
    public String description() {
      return "Email port";
    }
  }

  public static class EmailFromProperty extends AbstractStringConfigProperty{

    @Override
    public String getKey() {
      return "email.from";
    }

    @Override
    public String description() {
      return "Email from";
    }
  }

  public static class EmailPasswordProperty extends AbstractStringConfigProperty{

    @Override
    public String getKey() {
      return "email.password";
    }

    @Override
    public String description() {
      return "Email password";
    }
  }
}
