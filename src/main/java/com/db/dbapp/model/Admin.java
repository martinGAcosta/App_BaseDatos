package com.db.dbapp.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends Persona {
    private static final long serialVersionUID = 1L;

}
