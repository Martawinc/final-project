package com.htp.controller.converter;

import com.htp.controller.requests.ShirtUpdateRequest;
import com.htp.domain.BlankShirt;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

import static java.util.Optional.ofNullable;

@Component
public class ShirtFullUpdateRequestConverter
    extends ShirtRequestConverter<ShirtUpdateRequest, BlankShirt> {

  @Override
  public BlankShirt convert(ShirtUpdateRequest request) {
    BlankShirt blankShirt =
        ofNullable(entityManager.find(BlankShirt.class, request.getId()))
            .orElseThrow(EntityNotFoundException::new);
    return convertToShirt(blankShirt, request);
  }
}
