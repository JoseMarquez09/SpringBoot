package com.api.hateoas;

import com.api.hateoas.model.Cuenta;
import com.api.hateoas.repository.CuentaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@Rollback(value = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CuentaRepositoryTest {
    @Autowired
    private CuentaRepository cuentaRepository;

    @Test
    void testAgregarCuenta(){
        Cuenta cuenta = new Cuenta(-12224,"2434544");
        Cuenta cuentaGuardadda = cuentaRepository.save(cuenta);


    }


}
