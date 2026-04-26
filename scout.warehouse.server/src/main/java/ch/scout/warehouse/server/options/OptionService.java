package ch.scout.warehouse.server.options;

import ch.scout.warehouse.server.db.tables.user.User;
import ch.scout.warehouse.server.db.tables.user.UserRepository;
import ch.scout.warehouse.shared.options.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.security.ACCESS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class OptionService implements IOptionService {
  ObjectMapper objectMapper = new ObjectMapper();
  Logger LOG = LoggerFactory.getLogger(OptionService.class);

  @Override
  public OptionFormData load(OptionFormData formData) {
    if (!ACCESS.check(new ReadOptionPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    Options options = getOptions();
    formData.getLanaguage().setValue(options.getLanguageId());
    formData.getTheme().setValue(options.getTheme());
    formData.getDenseMode().setValue(options.isDenseMode());
    return formData;
  }

  @Override
  public OptionFormData store(OptionFormData formData) {
    if (!ACCESS.check(new UpdateOptionPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    User user = BEANS.get(UserRepository.class).getCurrentUser();
    Options options = new Options();
    options.setLanguageId(formData.getLanaguage().getValue());
    options.setTheme(formData.getTheme().getValue());
    options.setDenseMode(formData.getDenseMode().getValue());
    try {
      user.setOptions(objectMapper.writeValueAsString(options));
      BEANS.get(UserRepository.class).save(user);
    } catch (JsonProcessingException e) {
      LOG.error("Error while writing options", e);
    }
    return formData;
  }

  @Override
  public Options getOptions() {
    User user = BEANS.get(UserRepository.class).getCurrentUser();
    try {
      return objectMapper.readValue(user.getOptions(), Options.class);
    } catch (Exception e) {
      LOG.error("Error while reading options", e);
      return new Options();
    }
  }
}
