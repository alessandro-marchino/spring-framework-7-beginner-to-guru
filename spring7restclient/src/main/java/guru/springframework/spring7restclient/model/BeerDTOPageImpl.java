package guru.springframework.spring7restclient.model;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import tools.jackson.databind.JsonNode;

public class BeerDTOPageImpl extends PageImpl<BeerDTO> {

	@JsonCreator(mode = Mode.PROPERTIES)
	public BeerDTOPageImpl(
			@JsonProperty("content") List<BeerDTO> content,
			@JsonProperty("page") JsonNode page) {
		super(content, PageRequest.of(page.get("number").asInt(), page.get("size").asInt()), page.get("totalElements").asInt());
	}

}
