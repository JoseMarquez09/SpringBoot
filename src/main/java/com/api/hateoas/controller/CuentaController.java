package com.api.hateoas.controller;

import com.api.hateoas.model.Cuenta;
import com.api.hateoas.model.Monto;
import com.api.hateoas.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @GetMapping
    public ResponseEntity<List<Cuenta>> listarCuentas(){
        List<Cuenta> cuentas = cuentaService.listAll();

        if (cuentas.isEmpty()){
            return  ResponseEntity.noContent().build();
        }

        for (Cuenta cuenta:cuentas){
            cuenta.add(linkTo(methodOn(CuentaController.class).listarCuenta(cuenta.getId())).withRel("Actual"));
            cuenta.add(linkTo(methodOn(CuentaController.class).listarCuentas()).withRel("Mostrar Todos"));
            cuenta.add(linkTo(methodOn(CuentaController.class).depositarDinero(cuenta.getId(),null)).withRel("depositos"));
            cuenta.add(linkTo(methodOn(CuentaController.class).retirarDinero(cuenta.getId(),null)).withRel("retiros"));
        }

        CollectionModel<Cuenta> modelo = CollectionModel.of(cuentas);
        modelo.add(linkTo(methodOn(CuentaController.class).listarCuentas()).withSelfRel());

        return  new ResponseEntity<>(cuentas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> listarCuenta(@PathVariable Integer id){
        try {
            Cuenta cuenta = cuentaService.findByID(id);

            cuenta.add(linkTo(methodOn(CuentaController.class).listarCuenta(cuenta.getId())).withRel("Actual"));
            cuenta.add(linkTo(methodOn(CuentaController.class).listarCuentas()).withRel("Mostrar Todos"));
            cuenta.add(linkTo(methodOn(CuentaController.class).depositarDinero(cuenta.getId(),null)).withRel("depositos"));
            cuenta.add(linkTo(methodOn(CuentaController.class).retirarDinero(cuenta.getId(),null)).withRel("retiros"));

            return new ResponseEntity<>(cuenta, HttpStatus.OK);
        }catch (Exception exception){
            return ResponseEntity.noContent().build();
        }

    }

    @PostMapping
    public ResponseEntity<Cuenta> guardarCuenta(@RequestBody Cuenta cuenta){
        Cuenta cuentaBBDD = cuentaService.save(cuenta);

        cuentaBBDD.add(linkTo(methodOn(CuentaController.class).listarCuenta(cuentaBBDD.getId())).withRel("Actual"));
        cuentaBBDD.add(linkTo(methodOn(CuentaController.class).listarCuentas()).withRel("Mostrar Todos"));
        cuentaBBDD.add(linkTo(methodOn(CuentaController.class).depositarDinero(cuentaBBDD.getId(),null)).withRel("depositos"));
        cuentaBBDD.add(linkTo(methodOn(CuentaController.class).retirarDinero(cuentaBBDD.getId(),null)).withRel("retiros"));


        return  ResponseEntity.created(linkTo(methodOn(CuentaController.class).listarCuenta(cuentaBBDD.getId())).toUri()).body(cuentaBBDD);
    }

    @PutMapping
    public ResponseEntity<Cuenta> editarCuenta(@RequestBody Cuenta cuenta){

        Cuenta cuentaBBDD = cuentaService.save(cuenta);

        cuentaBBDD.add(linkTo(methodOn(CuentaController.class).listarCuenta(cuentaBBDD.getId())).withRel("Actual"));
        cuentaBBDD.add(linkTo(methodOn(CuentaController.class).listarCuentas()).withRel("Mostrar Todos"));
        cuentaBBDD.add(linkTo(methodOn(CuentaController.class).depositarDinero(cuentaBBDD.getId(),null)).withRel("depositos"));
        cuentaBBDD.add(linkTo(methodOn(CuentaController.class).retirarDinero(cuentaBBDD.getId(),null)).withRel("retiros"));

        return new ResponseEntity<>(cuentaBBDD,HttpStatus.OK);
    }

    @PatchMapping("/{id}/deposito")
    public  ResponseEntity<Cuenta> depositarDinero(@PathVariable Integer id,@RequestBody Monto monto){

        Cuenta cuentaBBDD = cuentaService.depositar(monto.getMonto(),id);

        cuentaBBDD.add(linkTo(methodOn(CuentaController.class).listarCuenta(cuentaBBDD.getId())).withRel("Actual"));
        cuentaBBDD.add(linkTo(methodOn(CuentaController.class).listarCuentas()).withRel("Mostrar Todos"));
        cuentaBBDD.add(linkTo(methodOn(CuentaController.class).depositarDinero(cuentaBBDD.getId(),null)).withRel("depositos"));
        cuentaBBDD.add(linkTo(methodOn(CuentaController.class).retirarDinero(cuentaBBDD.getId(),null)).withRel("retiros"));

        return new ResponseEntity<>(cuentaBBDD,HttpStatus.OK);
    }

    @PatchMapping("/{id}/retirar")
    public  ResponseEntity<Cuenta> retirarDinero(@PathVariable Integer id,@RequestBody Monto monto){

        Cuenta cuentaBBDD = cuentaService.retirar(monto.getMonto(),id);

        cuentaBBDD.add(linkTo(methodOn(CuentaController.class).listarCuenta(cuentaBBDD.getId())).withRel("Actual"));
        cuentaBBDD.add(linkTo(methodOn(CuentaController.class).listarCuentas()).withRel("Mostrar Todos"));
        cuentaBBDD.add(linkTo(methodOn(CuentaController.class).depositarDinero(cuentaBBDD.getId(),null)).withRel("depositos"));
        cuentaBBDD.add(linkTo(methodOn(CuentaController.class).retirarDinero(cuentaBBDD.getId(),null)).withRel("retiros"));

        return new ResponseEntity<>(cuentaBBDD,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> elminarCuenta(@PathVariable Integer id){
        try {
            cuentaService.delete(id);
            return ResponseEntity.noContent().build() ;
        }catch (Exception exception){
            return ResponseEntity.noContent().build();
        }
    }
}
