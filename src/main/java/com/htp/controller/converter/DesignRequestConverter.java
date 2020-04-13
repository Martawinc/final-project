package com.htp.controller.converter;

import com.htp.config.PriceListConfig;
import com.htp.controller.exceptions.NotEnoughShirtsException;
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
  private PriceListConfig priceList;

  @Autowired
  private void setShirtRepo(BlankShirtRepository shirtRepo) {
    this.shirtRepo = shirtRepo;
  }

  @Autowired
  private void setPriceList(PriceListConfig priceList) {
    this.priceList = priceList;
  }

  protected DesignShirt convertToDesignShirt(DesignShirt designShirt, DesignCreateRequest request) {

    BlankShirt shirt =
        shirtRepo.findById(request.getShirtId()).orElseThrow(EntityNotFoundException::new);

    if (shirt.getQuantity() == 0) {
      throw new NotEnoughShirtsException("Shirts with id '" + shirt.getId() +"' not enough for making design");
    }
    shirtRepo.updateQuantity(
        designShirt.getShirt().getQuantity() + 1, designShirt.getShirt().getId());
    designShirt.setShirt(shirt);
    shirtRepo.updateQuantity(shirt.getQuantity() - 1, shirt.getId());

    designShirt.setText(request.getText());
    designShirt.setTotalPrice(calculatePrice(designShirt));
    return designShirt;
  }

  private float calculatePrice(DesignShirt designShirt) {
    float price = designShirt.getShirt().getPrice();
    if (designShirt.getText() != null) price += priceList.getTextPrice();
    if (designShirt.getImageLink() != null) price += priceList.getImagePrice();
    return price;
  }
}
