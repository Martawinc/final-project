package com.htp.controller.converter;

import com.htp.controller.requests.ShirtCreateRequest;
import com.htp.domain.BlankShirt;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class ShirtCreateRequestConverter
    extends ShirtRequestConverter<ShirtCreateRequest, BlankShirt> {

  @Override
  public BlankShirt convert(ShirtCreateRequest request) {
    BlankShirt blankShirt = new BlankShirt();
    return convertToShirt(blankShirt, request);
  }
}
