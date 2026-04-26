package ch.scout.warehouse.server.db.mig;

import org.eclipse.scout.rt.platform.ApplicationScoped;

@ApplicationScoped
public interface IMigratable {

  Long getId();
  void check();
  void migrate();
  void verify();
}
