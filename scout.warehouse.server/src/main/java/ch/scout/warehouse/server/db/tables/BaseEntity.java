package ch.scout.warehouse.server.db.tables;

import ch.scout.warehouse.shared.common.StatusCodeType;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.scout.rt.platform.ApplicationScoped;

import java.util.Date;

@Setter
@Getter
@ApplicationScoped
@MappedSuperclass
public abstract class BaseEntity {

  @Column(name = NativeNames.STATUS_UID)
  public Long statusUid = StatusCodeType.ActiveCode.ID;

  @Column(name = NativeNames.EVT_DELTED)
  public Date evtDeleted;

  public static class NativeNames {
    public static final String STATUS_UID = "STATUS_UID";
    public static final String EVT_DELTED = "EVT_DELTED";
  }
}
