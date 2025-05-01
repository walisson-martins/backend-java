package com.sistema.cadastro.sistema_backend.services.validation;

import java.util.ArrayList;
import java.util.List;

import com.sistema.cadastro.sistema_backend.dto.ClienteNewDTO;
import com.sistema.cadastro.sistema_backend.enums.TipoCliente;
import com.sistema.cadastro.sistema_backend.resource.exceptions.FieldMessage;
import com.sistema.cadastro.sistema_backend.utils.Validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ClientInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
    @Override
    public void initialize(ClienteInsert ann) {
    }

    @Override
    public boolean isValid(ClienteNewDTO objDTO, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        if (objDTO.getTipo().equals(TipoCliente.PESSOAFISICA.getCod())
                && !Validation.isValidCPF(objDTO.getCpfOuCnpj())) {

            list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
        }

        if (objDTO.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod())
                && !Validation.isValidCNPJ(objDTO.getCpfOuCnpj())) {

            list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}