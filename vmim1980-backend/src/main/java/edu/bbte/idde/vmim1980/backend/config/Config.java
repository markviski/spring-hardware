package edu.bbte.idde.vmim1980.backend.config;

import lombok.*;

@Data
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Config {
    private String daoType;
    private String daoUrl;
    private String daoUser;
    private String daoPass;
    private Integer daoPoolsize;
    private String daoDriver;
}
