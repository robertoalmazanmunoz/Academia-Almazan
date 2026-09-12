package com.academia.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RutValidator implements ConstraintValidator<ValidRut, String> {

    @Override
    public boolean isValid(String rut, ConstraintValidatorContext context) {
    	if (rut == null || rut.isBlank()) {
            return true; // Se deja la validación de obligatoriedad a @NotBlank
        }
        
        //Lógica de limpieza (quitar puntos y guion).
    	rut = rut.trim()
                .replace(".", "")
                .replace("-", "")
                .toUpperCase();
    	//Valida formato rut 12345678-9
        if (!rut.matches("\\d{7,8}[0-9K]")) {
            return false;
        }
        
        //Algoritmo de Módulo 11
        String cuerpo = rut.substring(0, rut.length() - 1);
        char digitoVerificador = rut.charAt(rut.length() - 1);
        int suma = 0;
        int multiplicador = 2;
        for (int i = cuerpo.length() - 1; i >= 0; i--) {
            int numero = Character.getNumericValue(cuerpo.charAt(i));
            suma += numero * multiplicador;
            multiplicador++;
            if (multiplicador > 7) {
                multiplicador = 2;
            }
        }

        int resto = suma % 11;
        int resultado = 11 - resto;
        char dvCalculado;

        //Cálculo del DV
        if (resultado == 11) {
            dvCalculado = '0';
        } else if (resultado == 10) {
            dvCalculado = 'K';
        } else {
            dvCalculado = Character.forDigit(resultado,10);
        }

        return digitoVerificador == dvCalculado;
    }

}