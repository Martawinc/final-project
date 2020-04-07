package com.htp.controller.converter;

import com.htp.controller.requests.DesignCreateRequest;
import com.htp.domain.DesignShirt;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class DesignCreateRequestConverter
    extends DesignRequestConverter<DesignCreateRequest, DesignShirt> {

  @Override
  public DesignShirt convert(DesignCreateRequest request) {
    DesignShirt designShirt = new DesignShirt();
    return convertToDesignShirt(designShirt, request);
  }
}
