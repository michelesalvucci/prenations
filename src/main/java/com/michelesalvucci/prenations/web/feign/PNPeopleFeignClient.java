package com.michelesalvucci.prenations.web.feign;

import com.michelesalvucci.prenations.web.feign.dto.PNPresidentDTO;
import com.michelesalvucci.prenations.web.feign.filter.PNPresidentFilterDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "presidents", url = "https://gateway/pre-people")
public interface PNPeopleFeignClient {
    @RequestMapping(method = RequestMethod.GET, value = "/presidents")
    Page<PNPresidentDTO> getAllPresidents(PNPresidentFilterDTO filters);

    @RequestMapping(method = RequestMethod.GET, value = "/presidents/{presidentId}", produces = "application/json")
    PNPresidentDTO getPresident(@PathVariable("presidentId") Long presidentId);
}
