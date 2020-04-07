package com.htp.controller.converter;

import com.htp.controller.requests.DesignCreateRequest;
import com.htp.domain.BlankShirt;
import com.htp.domain.DesignShirt;
import com.htp.repository.BlankShirtRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityNotFoundException;

@NoArgsConstructor
public abstract class DesignRequestConverter<S, T> extends EntityConverter<S, T> {

  private BlankShirtRepository shirtRepo;

  @Autowired
  private void setShirtRepo(BlankShirtRepository shirtRepo) {
    this.shirtRepo = shirtRepo;
  }

  protected DesignShirt convertToDesignShirt(DesignShirt designShirt, DesignCreateRequest request) {

    BlankShirt shirt =
        shirtRepo.findById(request.getShirtId()).orElseThrow(() -> new EntityNotFoundException());

    designShirt.setShirt(shirt);
    designShirt.setText(request.getText());

    return designShirt;
  }
}
