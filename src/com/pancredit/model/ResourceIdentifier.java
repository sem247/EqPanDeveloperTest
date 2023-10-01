package com.pancredit.model;


import javax.validation.constraints.Pattern;

public class ResourceIdentifier {

    @Pattern(regexp="^[a-f0-9]{8}-[a-f0-9]{4}-4[a-f0-9]{3}-[89aAbB][a-f0-9]{3}-[a-f0-9]{12}$")
    private final String uuid;

    public ResourceIdentifier(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }
}
