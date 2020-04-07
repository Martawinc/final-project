package com.htp.controller.converter;

import com.htp.controller.requests.DesignUpdateRequest;
import com.htp.domain.DesignShirt;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

import static java.util.Optional.ofNullable;

@Component
public class DesignFullUpdateRequestConverter
    extends DesignRequestConverter<DesignUpdateRequest, DesignShirt> {

  @Override
  public DesignShirt convert(DesignUpdateRequest request) {
    DesignShirt designShirt =
        ofNullable(entityManager.find(DesignShirt.class, request.getId()))
            .orElseThrow(() -> new EntityNotFoundException());
    return convertToDesignShirt(designShirt, request);
  }
}
