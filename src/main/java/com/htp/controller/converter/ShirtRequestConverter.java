package com.htp.controller.converter;

import com.htp.controller.requests.ShirtCreateRequest;
import com.htp.domain.BlankShirt;
import com.htp.domain.Color;
import com.htp.repository.ColorRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityNotFoundException;

@NoArgsConstructor
public abstract class ShirtRequestConverter<S, T> extends EntityConverter<S, T> {

  private ColorRepository colorRepo;

  @Autowired
  private void setColorRepo(ColorRepository colorRepo) {
    this.colorRepo = colorRepo;
  }

  protected BlankShirt convertToShirt(BlankShirt shirt, ShirtCreateRequest request) {

    Color color =
        colorRepo.findById(request.getColorId()).orElseThrow(() -> new EntityNotFoundException());

    shirt.setId(request.getId());
    shirt.setSize(BlankShirt.Size.valueOf(request.getSize())); // IllegalArgumentException
    shirt.setColor(color);
    shirt.setQuantity(request.getQuantity());
    shirt.setPrice(request.getPrice());

    return shirt;
  }
}
