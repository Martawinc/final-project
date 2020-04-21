package com.htp.controller.converter;

import com.htp.controller.requests.DesignCreateRequest;
import com.htp.domain.DesignShirt;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@NoArgsConstructor
public class DesignCreateRequestConverter
		extends DesignRequestConverter<DesignCreateRequest, DesignShirt> {

	@Override
	public DesignShirt convert(DesignCreateRequest request) {
		DesignShirt designShirt = new DesignShirt();
		designShirt.setCreationDate(new Date());
		return convertToDesignShirt(designShirt, request);
	}
}
