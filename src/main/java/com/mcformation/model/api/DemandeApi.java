package com.mcformation.model.api;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DemandeApi {
    private String dateDemande;
    private List<DomaineApi> domaines;
    private String sujet;
    private String detail;
}