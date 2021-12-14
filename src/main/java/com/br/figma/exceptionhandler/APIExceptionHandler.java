package com.br.figma.exceptionhandler;


import com.br.figma.exceptions.*;
import com.br.figma.utils.ErrorInfo;
import com.br.figma.utils.HttpResponse;
import com.br.figma.utils.HttpResponseBinding;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@ControllerAdvice
public class APIExceptionHandler  {

    @ExceptionHandler(SetorEmUsoException.class)
    public ResponseEntity handleSetorEmUsoException (SetorEmUsoException setorEmUsoException) {
       return createHttpResponse(HttpStatus.BAD_REQUEST, setorEmUsoException.getMessage());
    }


    @ExceptionHandler(SetorNaoEncontradoException.class)
    public ResponseEntity handleSetorNaoEncontradoException (SetorNaoEncontradoException setorNaoEncontradoException) {
        return createHttpResponse(HttpStatus.BAD_REQUEST, setorNaoEncontradoException.getMessage());
    }

    @ExceptionHandler(CargoEmUsoException.class)
    public ResponseEntity handleCargoEmUsoException (CargoEmUsoException cargoEmUsoException) {
        return createHttpResponse(HttpStatus.BAD_REQUEST, cargoEmUsoException.getMessage());
    }

    @ExceptionHandler(CargoNaoEncontradoException.class)
    public ResponseEntity handleCargoNaoEncontradoException (CargoNaoEncontradoException cargoNaoEncontradoException) {
        return createHttpResponse(HttpStatus.BAD_REQUEST, cargoNaoEncontradoException.getMessage());
    }

    @ExceptionHandler(CpfEmUsoException.class)
    public ResponseEntity handleCpfEmUsoException (CpfEmUsoException cpfEmUsoException) {
        return createHttpResponse(HttpStatus.BAD_REQUEST, cpfEmUsoException.getMessage());
    }

    @ExceptionHandler(TrabalhadorNaoEncontradoException.class)
    public ResponseEntity handleUsuarioNaoEncontradoException (TrabalhadorNaoEncontradoException unee) {
        return createHttpResponse(HttpStatus.BAD_REQUEST, unee.getMessage());
    }

    /* Generica */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpResponse> internalServerErrorException(Exception exception) {
        return createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Um erro ocorreu, entre em contato com o administrador");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HttpResponseBinding> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<ErrorInfo> errorList = new ArrayList<>();

        for(int i =0; i < e.getBindingResult().getFieldErrors().size(); i++){
            ErrorInfo errorsInfo = new ErrorInfo();
            errorsInfo.setField(fieldErrors.get(i).getField());
            errorsInfo.setFieldMessage(fieldErrors.get(i).getDefaultMessage());
            errorList.add(errorsInfo);
        }

        return createHttpResponseWithBindingResults(HttpStatus.BAD_REQUEST, e.getMessage(), errorList);
    }



    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message){
        HttpResponse httpResponseBody = new HttpResponse(new Date(), httpStatus.value(), httpStatus,
                httpStatus.getReasonPhrase(), message) {
        };
        return new ResponseEntity<>(httpResponseBody, httpStatus);
    }


    private ResponseEntity<HttpResponseBinding> createHttpResponseWithBindingResults(HttpStatus httpStatus, String message,
                                                                                     List<ErrorInfo> errorsInfoList){

        HttpResponseBinding httpResponseBody = new HttpResponseBinding(new Date(), httpStatus.value(), httpStatus,
                httpStatus.getReasonPhrase(), message, errorsInfoList);

        return new ResponseEntity<>(httpResponseBody, httpStatus);
    }


}
