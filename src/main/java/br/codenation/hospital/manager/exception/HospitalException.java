package br.codenation.hospital.manager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class HospitalException extends RuntimeException {

    public HospitalException(String message){ super(message);
    }


}
