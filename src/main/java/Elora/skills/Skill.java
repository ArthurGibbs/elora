package Elora.skills;

import Elora.Response;
import Elora.nlp.ParsedMessageModel;

import java.util.Optional;

public interface Skill {
    Optional<Response> perform(ParsedMessageModel message);
}
