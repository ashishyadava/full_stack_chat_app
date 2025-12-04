package com.ashish.UserService.model.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Getter
@Setter
public class MessageDTO {
    
    private String message;

    @JsonCreator
    public MessageDTO(@JsonProperty("message") String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof MessageDTO)) {
            return false;
        }

        MessageDTO other = (MessageDTO) obj;

        return (other.message.compareTo(this.message) == 0);
    }

}
